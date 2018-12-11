package com.tone.nodes.runtime;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.TruffleObject;

/**
 * The Tone type for a {@code null} (i.e., undefined) value. In Truffle, it is generally discouraged
 * to use the Java {@code null} value to represent the guest language {@code null} value. It is not
 * possible to specialize on Java {@code null} (since you cannot ask it for the Java class), and
 * there is always the danger of a spurious {@link NullPointerException}. Representing the guest
 * language {@code null} as a singleton, as in {@link #SINGLETON this class}, is the recommended
 * practice.
 */
public final class ToneNull implements TruffleObject {

    /**
     * The canonical value to represent {@code null} in Tone.
     */
    public static final ToneNull SINGLETON = new ToneNull();

    /**
     * Disallow instantiation from outside to ensure that the {@link #SINGLETON} is the only
     * instance.
     */
    private ToneNull() {
    }

    /**
     * This method is, e.g., called when using the {@code null} value in a string concatenation. So
     * changing it has an effect on Tone programs.
     */
    @Override
    public String toString() {
        return "null";
    }

    /**
     * In case you want some of your objects to co-operate with other languages, you need to make
     * them implement {@link TruffleObject} and provide additional {@link ToneNullMessageResolution
     * foreign access implementation}.
     */
    @Override
    public ForeignAccess getForeignAccess() {
        return ToneNullMessageResolutionForeign.ACCESS;
    }
}
