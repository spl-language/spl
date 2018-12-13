package com.spl.nodes;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import com.spl.nodes.runtime.SplNull;
import com.spl.runtime.ToneBigNumber;
import com.spl.runtime.ToneFunction;

import java.math.BigInteger;

import static com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

@TypeSystem({
        boolean.class,
        long.class,
        ToneBigNumber.class,
        double.class,
        String.class,
        SplNull.class,
        ToneFunction.class})
public class SplTypes {

    @TypeCheck(SplNull.class)
    public static boolean isSLNull(Object value) {
        return value == SplNull.SINGLETON;
    }

    @TypeCast(SplNull.class)
    public static SplNull asSLNull(Object value) {
        assert isSLNull(value);
        return SplNull.SINGLETON;
    }

    @ImplicitCast
    @TruffleBoundary
    public static ToneBigNumber castBigNumber(long value) {
        return new ToneBigNumber(BigInteger.valueOf(value));
    }
}
