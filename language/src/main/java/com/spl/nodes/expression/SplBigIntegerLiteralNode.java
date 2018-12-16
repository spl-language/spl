package com.spl.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.literal.SplLongLiteralNode;
import com.spl.runtime.SplBigNumber;

import java.math.BigInteger;

/**
 * Constant literal for a arbitrary-precision number that exceeds the range of
 * {@link SplLongLiteralNode}.
 */
@NodeInfo(shortName = "const")
public final class SplBigIntegerLiteralNode extends SplExpressionNode {

    private final SplBigNumber value;

    public SplBigIntegerLiteralNode(BigInteger value) {
        this.value = new SplBigNumber(value);
    }

    @Override
    public SplBigNumber executeGeneric(VirtualFrame frame) {
        return value;
    }
}
