package com.tone.nodes.controlflow;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;

@NodeInfo(shortName = "for", description = "The node implementing a while loop")
public final class ToneForNode extends ToneStatementNode {

    @Child
    private LoopNode loopNode;

    public ToneForNode(ToneStatementNode initNode, ToneExpressionNode conditionNode, ToneStatementNode iteration, ToneStatementNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new ToneForRepeatingNode(initNode, conditionNode, iteration, bodyNode));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        loopNode.executeLoop(frame);
    }
}
