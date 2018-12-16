package com.spl.nodes;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import com.spl.nodes.runtime.SplNull;
import com.spl.runtime.SplBigNumber;
import com.spl.runtime.SplFunction;

import java.math.BigInteger;

import static com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

@TypeSystem({
        boolean.class,
        long.class,
        SplBigNumber.class,
        double.class,
        String.class,
        SplNull.class,
        SplFunction.class})
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
    public static SplBigNumber castBigNumber(long value) {
        return new SplBigNumber(BigInteger.valueOf(value));
    }
}
