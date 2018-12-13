package com.spl.nodes.runtime;

import com.oracle.truffle.api.interop.CanResolve;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;

/**
 * The class containing all message resolution implementations of {@link SplNull}.
 */
@MessageResolution(receiverType = SplNull.class)
public class SplNullMessageResolution {

    /*
     * An Tone function resolves the IS_NULL message.
     */
    @Resolve(message = "IS_NULL")
    public abstract static class ToneForeignIsNullNode extends Node {

        public Object access(Object receiver) {
            return SplNull.SINGLETON == receiver;
        }
    }

    @CanResolve
    public abstract static class CheckNull extends Node {

        protected static boolean test(TruffleObject receiver) {
            return receiver instanceof SplNull;
        }
    }
}
