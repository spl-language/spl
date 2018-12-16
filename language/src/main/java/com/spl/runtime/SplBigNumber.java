package com.spl.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;

import java.math.BigInteger;

@MessageResolution(receiverType = SplBigNumber.class)
public final class SplBigNumber implements TruffleObject, Comparable<SplBigNumber> {

    private final BigInteger value;

    public SplBigNumber(BigInteger value) {
        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }

    @TruffleBoundary
    public int compareTo(SplBigNumber o) {
        return value.compareTo(o.getValue());
    }

    @Override
    public ForeignAccess getForeignAccess() {
        return SplBigNumberForeign.ACCESS;
    }

    @Override
    @TruffleBoundary
    public String toString() {
        return value.toString();
    }

    @Override
    @TruffleBoundary
    public boolean equals(Object obj) {
        if (obj instanceof SplBigNumber) {
            return value.equals(((SplBigNumber) obj).getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    static boolean isInstance(TruffleObject obj) {
        return obj instanceof SplBigNumber;
    }

    @Resolve(message = "UNBOX")
    abstract static class UnboxBigNode extends Node {
        Object access(SplBigNumber obj) {
            return obj.value.doubleValue();
        }
    }

    @Resolve(message = "IS_BOXED")
    abstract static class IsBoxedBigNode extends Node {
        @SuppressWarnings("unused")
        Object access(SplBigNumber obj) {
            return true;
        }
    }
}
