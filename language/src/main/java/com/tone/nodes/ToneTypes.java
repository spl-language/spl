package com.tone.nodes;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import com.tone.nodes.runtime.ToneNull;
import com.tone.runtime.ToneBigNumber;
import com.tone.runtime.ToneFunction;

import java.math.BigInteger;

import static com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

@TypeSystem({
        boolean.class,
        long.class,
        ToneBigNumber.class,
        double.class,
        String.class,
        ToneNull.class,
        ToneFunction.class})
public class ToneTypes {

    @TypeCheck(ToneNull.class)
    public static boolean isSLNull(Object value) {
        return value == ToneNull.SINGLETON;
    }

    @TypeCast(ToneNull.class)
    public static ToneNull asSLNull(Object value) {
        assert isSLNull(value);
        return ToneNull.SINGLETON;
    }

    @ImplicitCast
    @TruffleBoundary
    public static ToneBigNumber castBigNumber(long value) {
        return new ToneBigNumber(BigInteger.valueOf(value));
    }
}
