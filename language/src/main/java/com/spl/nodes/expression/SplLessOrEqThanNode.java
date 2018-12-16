package com.spl.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplBinaryNode;
import com.spl.runtime.SplBigNumber;

@NodeInfo(shortName = "<=")
public abstract class SplLessOrEqThanNode extends SplBinaryNode {

    @Specialization
    protected boolean lessThan(long left, long right) {
        return left <= right;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    protected boolean lessThan(SplBigNumber left, SplBigNumber right) {
        return left.compareTo(right) <= 0;
    }

    @Fallback
    protected Object typeError(Object left, Object right) {
        throw SplTypeErrorException.typeError(this, left, right);
    }
}
