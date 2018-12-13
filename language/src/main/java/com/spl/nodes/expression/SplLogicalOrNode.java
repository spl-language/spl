package com.spl.nodes.expression;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;

@NodeInfo(shortName = "||")
public final class SplLogicalOrNode extends SplShortCircuitNode {

    public SplLogicalOrNode(SplExpressionNode left, SplExpressionNode right) {
        super(left, right);
    }

    /**
     * The right value does not need to be evaluated if the left value is already <code>false</code>
     * .
     */
    @Override
    protected boolean isEvaluateRight(boolean left) {
        return !left;
    }

    @Override
    protected boolean execute(boolean left, boolean right) {
        return left || right;
    }
}
