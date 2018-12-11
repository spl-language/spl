package com.tone.nodes.runtime;

import com.oracle.truffle.api.interop.CanResolve;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;

/**
 * The class containing all message resolution implementations of {@link ToneNull}.
 */
@MessageResolution(receiverType = ToneNull.class)
public class ToneNullMessageResolution {

    /*
     * An Tone function resolves the IS_NULL message.
     */
    @Resolve(message = "IS_NULL")
    public abstract static class ToneForeignIsNullNode extends Node {

        public Object access(Object receiver) {
            return ToneNull.SINGLETON == receiver;
        }
    }

    @CanResolve
    public abstract static class CheckNull extends Node {

        protected static boolean test(TruffleObject receiver) {
            return receiver instanceof ToneNull;
        }
    }
}
