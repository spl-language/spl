package com.tone.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.ToneException;
import com.tone.nodes.ToneBinaryNode;
import com.tone.runtime.ToneBigNumber;

@NodeInfo(shortName = "+")
public abstract class ToneAddNode extends ToneBinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long add(long left, long right) {
        return Math.addExact(left, right);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected ToneBigNumber add(ToneBigNumber left, ToneBigNumber right) {
        return new ToneBigNumber(left.getValue().add(right.getValue()));
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected String add(String left, String right) {
        return left + right;
    }

    @Fallback
    protected Object typeError(Object left, Object right) {
        throw ToneException.typeError(this, left, right);
    }
}
