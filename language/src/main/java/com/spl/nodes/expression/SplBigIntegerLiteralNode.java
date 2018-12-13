package com.spl.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.literal.SplLongLiteralNode;
import com.spl.runtime.ToneBigNumber;

import java.math.BigInteger;

/**
 * Constant literal for a arbitrary-precision number that exceeds the range of
 * {@link SplLongLiteralNode}.
 */
@NodeInfo(shortName = "const")
public final class SplBigIntegerLiteralNode extends SplExpressionNode {

    private final ToneBigNumber value;

    public SplBigIntegerLiteralNode(BigInteger value) {
        this.value = new ToneBigNumber(value);
    }

    @Override
    public ToneBigNumber executeGeneric(VirtualFrame frame) {
        return value;
    }
}
