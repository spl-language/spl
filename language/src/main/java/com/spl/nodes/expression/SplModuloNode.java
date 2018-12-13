package com.spl.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplBinaryNode;
import com.spl.runtime.ToneBigNumber;

@NodeInfo(shortName = "%")
public abstract class SplModuloNode extends SplBinaryNode {

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
        throw SplTypeErrorException.typeError(this, left, right);
    }
}
