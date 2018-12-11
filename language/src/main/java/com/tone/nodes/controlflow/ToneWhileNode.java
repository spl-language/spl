package com.tone.nodes.controlflow;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;

@NodeInfo(shortName = "while", description = "The node implementing a while loop")
public final class ToneWhileNode extends ToneStatementNode {

    @Node.Child
    private LoopNode loopNode;

    public ToneWhileNode(ToneExpressionNode conditionNode, ToneStatementNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new ToneWhileRepeatingNode(conditionNode, bodyNode));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        loopNode.executeLoop(frame);
    }
}
