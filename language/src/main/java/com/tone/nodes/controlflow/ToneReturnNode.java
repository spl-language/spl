package com.tone.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;
import com.tone.nodes.runtime.ToneNull;

/**
 * Implementation of the Tone return statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link ToneReturnNode} and the {@link ToneFunctionBodyNode} of the
 * method we are exiting. This is done by throwing an {@link ToneReturnException exception} that is
 * caught by the {@link ToneFunctionBodyNode#executeGeneric function body}. The exception transports
 * the return value.
 */
@NodeInfo(shortName = "return", description = "The node implementing a return statement")
public final class ToneReturnNode extends ToneStatementNode {

    @Node.Child
    private ToneExpressionNode valueNode;

    public ToneReturnNode(ToneExpressionNode valueNode) {
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
            result = ToneNull.SINGLETON;
        }
        throw new ToneReturnException(result);
    }
}
