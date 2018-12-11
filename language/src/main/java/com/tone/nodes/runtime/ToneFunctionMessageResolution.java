package com.tone.nodes.runtime;

import com.oracle.truffle.api.interop.CanResolve;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.tone.nodes.call.ToneDispatchNode;
import com.tone.nodes.call.ToneDispatchNodeGen;
import com.tone.runtime.ToneFunction;

import static com.tone.runtime.ToneContext.fromForeignValue;

/**
 * The class containing all message resolution implementations of {@link ToneFunction}.
 */
@MessageResolution(receiverType = ToneFunction.class)
public class ToneFunctionMessageResolution {
    /*
     * An Tone function resolves an EXECUTE message.
     */
    @Resolve(message = "EXECUTE")
    public abstract static class ToneForeignFunctionExecuteNode extends Node {

        @Child
        private ToneDispatchNode dispatch = ToneDispatchNodeGen.create();

        public Object access(ToneFunction receiver, Object[] arguments) {
            Object[] arr = new Object[arguments.length];
            // Before the arguments can be used by the ToneFunction, they need to be converted to Tone
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
            return receiver instanceof ToneFunction;
        }
    }

    @CanResolve
    public abstract static class CheckFunction extends Node {

        protected static boolean test(TruffleObject receiver) {
            return receiver instanceof ToneFunction;
        }
    }
}
