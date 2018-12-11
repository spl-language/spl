package com.tone.nodes.expression;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;

@NodeInfo(shortName = "||")
public final class ToneLogicalOrNode extends ToneShortCircuitNode {

    public ToneLogicalOrNode(ToneExpressionNode left, ToneExpressionNode right) {
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
