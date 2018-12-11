package com.tone.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.literal.ToneLongLiteralNode;
import com.tone.runtime.ToneBigNumber;

import java.math.BigInteger;

/**
 * Constant literal for a arbitrary-precision number that exceeds the range of
 * {@link ToneLongLiteralNode}.
 */
@NodeInfo(shortName = "const")
public final class ToneBigIntegerLiteralNode extends ToneExpressionNode {

    private final ToneBigNumber value;

    public ToneBigIntegerLiteralNode(BigInteger value) {
        this.value = new ToneBigNumber(value);
    }

    @Override
    public ToneBigNumber executeGeneric(VirtualFrame frame) {
        return value;
    }
}
