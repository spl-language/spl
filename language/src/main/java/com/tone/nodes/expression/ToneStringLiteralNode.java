package com.tone.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;

/**
 * Constant literal for a String value.
 */
@NodeInfo(shortName = "const")
public final class ToneStringLiteralNode extends ToneExpressionNode {

    private final String value;

    public ToneStringLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public String executeGeneric(VirtualFrame frame) {
        return value;
    }
}
