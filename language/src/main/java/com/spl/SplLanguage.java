package com.spl;

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
import com.spl.builtins.SplBuiltinNode;
import com.spl.nodes.SplEvalRootNode;
import com.spl.nodes.local.SplLexicalScope;
import com.spl.nodes.runtime.SplNull;
import com.spl.parser.SplParser;
import com.spl.runtime.SplContext;
import com.spl.runtime.SplBigNumber;
import com.spl.runtime.SplFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@TruffleLanguage.Registration(id = SplLanguage.ID, name = "Spl", defaultMimeType = SplLanguage.MIME_TYPE, characterMimeTypes = SplLanguage.MIME_TYPE, contextPolicy = ContextPolicy.SHARED)
@ProvidedTags({StandardTags.CallTag.class, StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.ExpressionTag.class, DebuggerTags.AlwaysHalt.class})
public final class SplLanguage extends TruffleLanguage<SplContext> {
    public static volatile int counter;

    public static final String EXTENSION = "spl";
    public static final String ID = "spl";
    public static final String MIME_TYPE = "application/x-spl";

    public SplLanguage() {
        counter++;
    }

    @Override
    protected SplContext createContext(Env env) {
        return new SplContext(this, env, new ArrayList<>(EXTERNAL_BUILTINS));
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        Source source = request.getSource();
        Map<String, RootCallTarget> functions;

        /*
         * Проаналізуйте надане джерело. На даний момент у нас ще немає SplContext. Реєстрація
         * функції з SplContext відбуваєься "lazy" в SplEvalRootNode.
         */
        if (request.getArgumentNames().isEmpty()) {
            functions = SplParser.parseSpl(this, source);
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
            functions = SplParser.parseSpl(this, decoratedSource);
        }

        RootCallTarget main = functions.get("main");
        RootNode evalMain;
        if (main != null) {

            evalMain = new SplEvalRootNode(this, main, functions);
        } else {

            /* навіть якщо нема main реєстрємо всі функції */
            evalMain = new SplEvalRootNode(this, null, functions);
        }
        return Truffle.getRuntime().createCallTarget(evalMain);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Object findExportedSymbol(SplContext context, String globalName, boolean onlyExplicit) {
        return context.getFunctionRegistry().lookup(globalName, false);
    }

    @Override
    protected boolean isVisible(SplContext context, Object value) {
        return value != SplNull.SINGLETON;
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        if (!(object instanceof TruffleObject)) {
            return false;
        }
        TruffleObject truffleObject = (TruffleObject) object;
        return truffleObject instanceof SplFunction || truffleObject instanceof SplBigNumber || SplContext.isSplObject(truffleObject);
    }

    @Override
    protected String toString(SplContext context, Object value) {
        if (value == SplNull.SINGLETON) {
            return "NULL";
        }
        if (value instanceof SplBigNumber) {
            return super.toString(context, ((SplBigNumber) value).getValue());
        }
        if (value instanceof Long) {
            return Long.toString((Long) value);
        }
        return super.toString(context, value);
    }

    @Override
    protected Object findMetaObject(SplContext context, Object value) {
        if (value instanceof Number || value instanceof SplBigNumber) {
            return "Number";
        }
        if (value instanceof Boolean) {
            return "Boolean";
        }
        if (value instanceof String) {
            return "String";
        }
        if (value == SplNull.SINGLETON) {
            return "Null";
        }
        if (value instanceof SplFunction) {
            return "Function";
        }
        return "Object";
    }

    @Override
    protected SourceSection findSourceLocation(SplContext context, Object value) {
        if (value instanceof SplFunction) {
            SplFunction f = (SplFunction) value;
            return f.getCallTarget().getRootNode().getSourceSection();
        }
        return null;
    }

    @Override
    public Iterable<Scope> findLocalScopes(SplContext context, Node node, Frame frame) {
        final SplLexicalScope scope = SplLexicalScope.createScope(node);
        return new Iterable<Scope>() {
            @Override
            public Iterator<Scope> iterator() {
                return new Iterator<Scope>() {
                    private SplLexicalScope previousScope;
                    private SplLexicalScope nextScope = scope;

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
    protected Iterable<Scope> findTopScopes(SplContext context) {
        return context.getTopScopes();
    }

    public static SplContext getCurrentContext() {
        return getCurrentContext(SplLanguage.class);
    }

    private static final List<NodeFactory<? extends SplBuiltinNode>> EXTERNAL_BUILTINS = Collections.synchronizedList(new ArrayList<>());

    public static void installBuiltin(NodeFactory<? extends SplBuiltinNode> builtin) {
        EXTERNAL_BUILTINS.add(builtin);
    }
}
