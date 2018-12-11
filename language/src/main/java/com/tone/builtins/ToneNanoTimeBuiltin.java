package com.tone.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Builtin function that returns the value of a high-resolution time, in nanoseconds.
 */
@NodeInfo(shortName = "nanoTime")
public abstract class ToneNanoTimeBuiltin extends ToneBuiltinNode {

    @Specialization
    public long nanoTime() {
        return System.nanoTime();
    }
}
