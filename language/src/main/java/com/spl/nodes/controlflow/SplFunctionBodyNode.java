package com.spl.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplRootNode;
import com.spl.nodes.SplStatementNode;
import com.spl.nodes.runtime.SplNull;

/**
 * The body of a user-defined Tone function. This is the node referenced by a {@link SplRootNode} for
 * user-defined functions. It handles the return value of a function: the {@link SplReturnNode return
 * statement} throws an {@link SplReturnException exception} with the return value. This node catches
 * the exception. If the method ends without an explicit {@code return}, return the
 * {@link SplNull#SINGLETON default null value}.
 */
@NodeInfo(shortName = "body")
public final class SplFunctionBodyNode extends SplExpressionNode {

    /** The body of the function. */
    @Node.Child
    private SplStatementNode bodyNode;

    /**
     * Profiling information, collected by the interpreter, capturing whether the function had an
     * {@link SplReturnNode explicit return statement}. This allows the compiler to generate better
     * code.
     */
    private final BranchProfile exceptionTaken = BranchProfile.create();
    private final BranchProfile nullTaken = BranchProfile.create();

    public SplFunctionBodyNode(SplStatementNode bodyNode) {
        this.bodyNode = bodyNode;
        addRootTag();
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        try {
            /* Execute the function body. */
            bodyNode.executeVoid(frame);

        } catch (SplReturnException ex) {
            /*
             * In the interpreter, record profiling information that the function has an explicit
             * return.
             */
            exceptionTaken.enter();
            /* The exception transports the actual return value. */
            return ex.getResult();
        }

        /*
         * In the interpreter, record profiling information that the function ends without an
         * explicit return.
         */
        nullTaken.enter();
        /* Return the default null value. */
        return SplNull.SINGLETON;
    }
}
