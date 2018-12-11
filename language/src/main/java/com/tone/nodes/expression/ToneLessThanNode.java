package com.tone.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.ToneException;
import com.tone.nodes.ToneBinaryNode;
import com.tone.runtime.ToneBigNumber;

@NodeInfo(shortName = "<")
public abstract class ToneLessThanNode extends ToneBinaryNode {

    @Specialization
    protected boolean lessThan(long left, long right) {
        return left < right;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected boolean lessThan(ToneBigNumber left, ToneBigNumber right) {
        return left.compareTo(right) < 0;
    }

    @Fallback
    protected Object typeError(Object left, Object right) {
        throw ToneException.typeError(this, left, right);
    }
}
