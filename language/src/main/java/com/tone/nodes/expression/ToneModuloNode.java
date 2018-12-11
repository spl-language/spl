package com.tone.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.ToneException;
import com.tone.nodes.ToneBinaryNode;
import com.tone.runtime.ToneBigNumber;

@NodeInfo(shortName = "%")
public abstract class ToneModuloNode extends ToneBinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long mod(long left, long right) {
        return left % right;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected ToneBigNumber mod(ToneBigNumber left, ToneBigNumber right) {
        return new ToneBigNumber(left.getValue().mod(right.getValue()));
    }

    @Fallback
    protected Object typeError(Object left, Object right) {
        throw ToneException.typeError(this, left, right);
    }
}
