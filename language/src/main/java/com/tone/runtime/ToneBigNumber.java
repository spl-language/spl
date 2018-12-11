package com.tone.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;

import java.math.BigInteger;

@MessageResolution(receiverType = ToneBigNumber.class)
public final class ToneBigNumber implements TruffleObject, Comparable<ToneBigNumber> {

    private final BigInteger value;

    public ToneBigNumber(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @TruffleBoundary
    public int compareTo(ToneBigNumber o) {
        return value.compareTo(o.getValue());
    }

    @Override
    public ForeignAccess getForeignAccess() {
        return ToneBigNumberForeign.ACCESS;
    }

    @Override
    @TruffleBoundary
    public String toString() {
        return value.toString();
    }

    @Override
    @TruffleBoundary
    public boolean equals(Object obj) {
        if (obj instanceof ToneBigNumber) {
            return value.equals(((ToneBigNumber) obj).getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    static boolean isInstance(TruffleObject obj) {
        return obj instanceof ToneBigNumber;
    }

    @Resolve(message = "UNBOX")
    abstract static class UnboxBigNode extends Node {
        Object access(ToneBigNumber obj) {
            return obj.value.doubleValue();
        }
    }

    @Resolve(message = "IS_BOXED")
    abstract static class IsBoxedBigNode extends Node {
        @SuppressWarnings("unused")
        Object access(ToneBigNumber obj) {
            return true;
        }
    }
}
