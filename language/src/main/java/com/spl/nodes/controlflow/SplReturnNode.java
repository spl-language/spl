package com.spl.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;
import com.spl.nodes.runtime.SplNull;

/**
 * Implementation of the Tone return statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link SplReturnNode} and the {@link SplFunctionBodyNode} of the
 * method we are exiting. This is done by throwing an {@link SplReturnException exception} that is
 * caught by the {@link SplFunctionBodyNode#executeGeneric function body}. The exception transports
 * the return value.
 */
@NodeInfo(shortName = "return", description = "The node implementing a return statement")
public final class SplReturnNode extends SplStatementNode {

    @Node.Child
    private SplExpressionNode valueNode;

    public SplReturnNode(SplExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        Object result;
        if (valueNode != null) {
            result = valueNode.executeGeneric(frame);
        } else {
            /*
             * Return statement that was not followed by an expression, so return the Tone null value.
             */
            result = SplNull.SINGLETON;
        }
        throw new SplReturnException(result);
    }
}
