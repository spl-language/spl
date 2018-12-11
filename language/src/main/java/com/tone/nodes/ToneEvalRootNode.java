package com.tone.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.RootNode;
import com.tone.ToneLanguage;
import com.tone.nodes.runtime.ToneNull;
import com.tone.runtime.ToneContext;

import java.util.Map;

/**
 * This class performs two additional tasks:
 *
 * <ul>
 * <li>Lazily registration of functions on first execution. This fulfills the semantics of
 * "evaluating" source code in Tone.</li>
 * <li>Conversion of arguments to types understood by Tone. The Tone source code can be evaluated from a
 * different language, i.e., the caller can be a node from a different language that uses types not
 * understood by Tone.</li>
 * </ul>
 */
public final class ToneEvalRootNode extends RootNode {

    private final Map<String, RootCallTarget> functions;
    @CompilationFinal
    private boolean registered;

    private final ContextReference<ToneContext> reference;

    @Child
    private DirectCallNode mainCallNode;

    public ToneEvalRootNode(ToneLanguage language, RootCallTarget rootFunction, Map<String, RootCallTarget> functions) {
        super(null); // internal frame
        this.functions = functions;
        this.mainCallNode = rootFunction != null ? DirectCallNode.create(rootFunction) : null;
        this.reference = language.getContextReference();
    }

    @Override
    protected boolean isInstrumentable() {
        return false;
    }

    @Override
    public String getName() {
        return "root eval";
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        /* Lazy registrations of functions on first execution. */
        if (!registered) {
            /* Function registration is a slow-path operation that must not be compiled. */
            CompilerDirectives.transferToInterpreterAndInvalidate();
            reference.get().getFunctionRegistry().register(functions);
            registered = true;
        }

        if (mainCallNode == null) {
            /* The source code did not have a "main" function, so nothing to execute. */
            return ToneNull.SINGLETON;
        } else {
            /* Conversion of arguments to types understood by Tone. */
            Object[] arguments = frame.getArguments();
            for (int i = 0; i < arguments.length; i++) {
                arguments[i] = ToneContext.fromForeignValue(arguments[i]);
            }
            return mainCallNode.call(arguments);
        }
    }
}
