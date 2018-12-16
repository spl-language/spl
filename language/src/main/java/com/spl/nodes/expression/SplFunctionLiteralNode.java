package com.spl.nodes.expression;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.TruffleLanguage.ContextReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.SplLanguage;
import com.spl.nodes.SplExpressionNode;
import com.spl.runtime.SplContext;
import com.spl.runtime.SplFunction;
import com.spl.runtime.SplFunctionRegistry;

/**
 * Constant literal for a {@link SplFunction function} value, created when a function name occurs as
 * a literal in Tone source code. Note that function redefinition can change the {@link CallTarget
 * call target} that is executed when calling the function, but the {@link SplFunction} for a name
 * never changes. This is guaranteed by the {@link SplFunctionRegistry}.
 */
@NodeInfo(shortName = "func")
public final class SplFunctionLiteralNode extends SplExpressionNode {

    /** The name of the function. */
    private final String functionName;

    /**
     * The resolved function. During parsing (in the constructor of this node), we do not have the
     * {@link SplContext} available yet, so the lookup can only be done at {@link #executeGeneric
     * first execution}. The {@link CompilationFinal} annotation ensures that the function can still
     * be constant folded during compilation.
     */
    @CompilationFinal
    private SplFunction cachedFunction;

    private final ContextReference<SplContext> reference;

    public SplFunctionLiteralNode(SplLanguage language, String functionName) {
        this.functionName = functionName;
        this.reference = language.getContextReference();
    }

    @Override
    public SplFunction executeGeneric(VirtualFrame frame) {
        if (cachedFunction == null) {
            /* We are about to change a @CompilationFinal field. */
            CompilerDirectives.transferToInterpreterAndInvalidate();
            /* First execution of the node: lookup the function in the function registry. */
            cachedFunction = reference.get().getFunctionRegistry().lookup(functionName, true);
        }
        return cachedFunction;
    }

}
