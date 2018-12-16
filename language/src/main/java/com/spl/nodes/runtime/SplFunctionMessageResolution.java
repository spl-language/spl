package com.spl.nodes.runtime;

import com.oracle.truffle.api.interop.CanResolve;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.spl.nodes.call.SplDispatchNode;
import com.spl.nodes.call.SplDispatchNodeGen;
import com.spl.runtime.SplFunction;

import static com.spl.runtime.SplContext.fromForeignValue;

/**
 * The class containing all message resolution implementations of {@link SplFunction}.
 */
@MessageResolution(receiverType = SplFunction.class)
public class SplFunctionMessageResolution {
    /*
     * An Tone function resolves an EXECUTE message.
     */
    @Resolve(message = "EXECUTE")
    public abstract static class ToneForeignFunctionExecuteNode extends Node {

        @Child
        private SplDispatchNode dispatch = SplDispatchNodeGen.create();

        public Object access(SplFunction receiver, Object[] arguments) {
            Object[] arr = new Object[arguments.length];
            // Before the arguments can be used by the SplFunction, they need to be converted to Tone
            // values.
            for (int i = 0; i < arr.length; i++) {
                arr[i] = fromForeignValue(arguments[i]);
            }
            Object result = dispatch.executeDispatch(receiver, arr);
            return result;
        }
    }

    /*
     * An Tone function should respond to an IS_EXECUTABLE message with true.
     */
    @Resolve(message = "IS_EXECUTABLE")
    public abstract static class ToneForeignIsExecutableNode extends Node {
        public Object access(Object receiver) {
            return receiver instanceof SplFunction;
        }
    }

    @CanResolve
    public abstract static class CheckFunction extends Node {

        protected static boolean test(TruffleObject receiver) {
            return receiver instanceof SplFunction;
        }
    }
}
