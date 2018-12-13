package com.spl.nodes.controlflow;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;

@NodeInfo(shortName = "while", description = "The node implementing a while loop")
public final class SplWhileNode extends SplStatementNode {

    @Node.Child
    private LoopNode loopNode;

    public SplWhileNode(SplExpressionNode conditionNode, SplStatementNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new SplWhileRepeatingNode(conditionNode, bodyNode));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        loopNode.executeLoop(frame);
    }
}
