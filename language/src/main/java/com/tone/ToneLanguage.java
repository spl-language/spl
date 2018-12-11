package com.tone;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Scope;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.tone.builtins.ToneBuiltinNode;
import com.tone.nodes.ToneEvalRootNode;
import com.tone.nodes.local.ToneLexicalScope;
import com.tone.nodes.runtime.ToneNull;
import com.tone.parser.ToneParser;
import com.tone.runtime.ToneBigNumber;
import com.tone.runtime.ToneContext;
import com.tone.runtime.ToneFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@TruffleLanguage.Registration(id = ToneLanguage.ID, name = "Tone", defaultMimeType = ToneLanguage.MIME_TYPE, characterMimeTypes = ToneLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class})
public final class ToneLanguage extends TruffleLanguage<ToneContext> {
    public static volatile int counter;

    public static final String EXTENSION = "tone";
    public static final String ID = "tone";
    public static final String MIME_TYPE = "application/x-tone";

    public ToneLanguage() {
        counter++;
    }

    @Override
    protected ToneContext createContext(Env env) {
        return new ToneContext(this, env, new ArrayList<>(EXTERNAL_BUILTINS));
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Source source = request.getSource();
        Map<String, RootCallTarget> functions;
        /*
         * Parse the provided source. At this point, we do not have a ToneContext yet. Registration of
         * the functions with the ToneContext happens lazily in ToneEvalRootNode.
         */
        if (request.getArgumentNames().isEmpty()) {
            functions = ToneParser.parseTone(this, source);
        } else {
            Source requestedSource = request.getSource();
            StringBuilder sb = new StringBuilder();
            sb.append("def main(");
            String sep = "";
            for (String argumentName : request.getArgumentNames()) {
                sb.append(sep);
                sb.append(argumentName);
                sep = ",";
            }
            sb.append(") { return ");
            sb.append(request.getSource().getCharacters());
            sb.append(";}");
            String language = requestedSource.getLanguage() == null ? ID : requestedSource.getLanguage();
            Source decoratedSource = Source.newBuilder(language, sb.toString(), request.getSource().getName()).build();
            functions = ToneParser.parseTone(this, decoratedSource);
        }

        RootCallTarget main = functions.get("main");
        RootNode evalMain;
        if (main != null) {
            /*
             * We have a main function, so "evaluating" the parsed source means invoking that main
             * function. However, we need to lazily register functions into the ToneContext first, so
             * we cannot use the original ToneRootNode for the main function. Instead, we create a new
             * ToneEvalRootNode that does everything we need.
             */
            evalMain = new ToneEvalRootNode(this, main, functions);
        } else {
            /*
             * Even without a main function, "evaluating" the parsed source needs to register the
             * functions into the ToneContext.
             */
            evalMain = new ToneEvalRootNode(this, null, functions);
        }
        return Truffle.getRuntime().createCallTarget(evalMain);
    }

    /*
     * Still necessary for the old Tone TCK to pass. We should remove with the old TCK. New language
     * should not override this.
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Object findExportedSymbol(ToneContext context, String globalName, boolean onlyExplicit) {
        return context.getFunctionRegistry().lookup(globalName, false);
    }

    @Override
    protected boolean isVisible(ToneContext context, Object value) {
        return value != ToneNull.SINGLETON;
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        if (!(object instanceof TruffleObject)) {
            return false;
        }
        TruffleObject truffleObject = (TruffleObject) object;
        return truffleObject instanceof ToneFunction || truffleObject instanceof ToneBigNumber || ToneContext.isToneObject(truffleObject);
    }

    @Override
    protected String toString(ToneContext context, Object value) {
        if (value == ToneNull.SINGLETON) {
            return "NULL";
        }
        if (value instanceof ToneBigNumber) {
            return super.toString(context, ((ToneBigNumber) value).getValue());
        }
        if (value instanceof Long) {
            return Long.toString((Long) value);
        }
        return super.toString(context, value);
    }

    @Override
    protected Object findMetaObject(ToneContext context, Object value) {
        if (value instanceof Number || value instanceof ToneBigNumber) {
            return "Number";
        }
        if (value instanceof Boolean) {
            return "Boolean";
        }
        if (value instanceof String) {
            return "String";
        }
        if (value == ToneNull.SINGLETON) {
            return "Null";
        }
        if (value instanceof ToneFunction) {
            return "Function";
        }
        return "Object";
    }

    @Override
    protected SourceSection findSourceLocation(ToneContext context, Object value) {
        if (value instanceof ToneFunction) {
            ToneFunction f = (ToneFunction) value;
            return f.getCallTarget().getRootNode().getSourceSection();
        }
        return null;
    }

    @Override
    public Iterable<Scope> findLocalScopes(ToneContext context, Node node, Frame frame) {
        final ToneLexicalScope scope = ToneLexicalScope.createScope(node);
        return new Iterable<Scope>() {
            @Override
            public Iterator<Scope> iterator() {
                return new Iterator<Scope>() {
                    private ToneLexicalScope previousScope;
                    private ToneLexicalScope nextScope = scope;

                    @Override
                    public boolean hasNext() {
                        if (nextScope == null) {
                            nextScope = previousScope.findParent();
                        }
                        return nextScope != null;
                    }

                    @Override
                    public Scope next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        Scope vscope = Scope.newBuilder(nextScope.getName(), nextScope.getVariables(frame)).node(nextScope.getNode()).arguments(nextScope.getArguments(frame)).build();
                        previousScope = nextScope;
                        nextScope = null;
                        return vscope;
                    }
                };
            }
        };
    }

    @Override
    protected Iterable<Scope> findTopScopes(ToneContext context) {
        return context.getTopScopes();
    }

    public static ToneContext getCurrentContext() {
        return getCurrentContext(ToneLanguage.class);
    }

    private static final List<NodeFactory<? extends ToneBuiltinNode>> EXTERNAL_BUILTINS = Collections.synchronizedList(new ArrayList<>());

    public static void installBuiltin(NodeFactory<? extends ToneBuiltinNode> builtin) {
        EXTERNAL_BUILTINS.add(builtin);
    }
}
