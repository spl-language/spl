package com.tone.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneRootNode;
import com.tone.nodes.ToneStatementNode;
import com.tone.nodes.runtime.ToneNull;

/**
 * The body of a user-defined Tone function. This is the node referenced by a {@link ToneRootNode} for
 * user-defined functions. It handles the return value of a function: the {@link ToneReturnNode return
 * statement} throws an {@link ToneReturnException exception} with the return value. This node catches
 * the exception. If the method ends without an explicit {@code return}, return the
 * {@link ToneNull#SINGLETON default null value}.
 */
@NodeInfo(shortName = "body")
public final class ToneFunctionBodyNode extends ToneExpressionNode {

    /** The body of the function. */
    @Node.Child
    private ToneStatementNode bodyNode;

    /**
     * Profiling information, collected by the interpreter, capturing whether the function had an
     * {@link ToneReturnNode explicit return statement}. This allows the compiler to generate better
     * code.
     */
    private final BranchProfile exceptionTaken = BranchProfile.create();
    private final BranchProfile nullTaken = BranchProfile.create();

    public ToneFunctionBodyNode(ToneStatementNode bodyNode) {
        this.bodyNode = bodyNode;
        addRootTag();
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        try {
            /* Execute the function body. */
            bodyNode.executeVoid(frame);

        } catch (ToneReturnException ex) {
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
        return ToneNull.SINGLETON;
    }
}
