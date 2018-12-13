package com.spl.nodes.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.KeyInfo;
import com.oracle.truffle.api.interop.MessageResolution;
import com.oracle.truffle.api.interop.Resolve;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.spl.nodes.access.SplReadPropertyCacheNode;
import com.spl.nodes.access.SplReadPropertyCacheNodeGen;
import com.spl.nodes.access.SplWritePropertyCacheNode;
import com.spl.nodes.access.SplWritePropertyCacheNodeGen;
import com.spl.nodes.call.SplDispatchNode;
import com.spl.nodes.call.SplDispatchNodeGen;
import com.spl.nodes.interop.ToneForeignToToneTypeNode;
import com.spl.nodes.interop.ToneForeignToToneTypeNodeGen;
import com.spl.runtime.SplContext;
import com.spl.runtime.ToneFunction;
import com.spl.runtime.ToneObjectType;

/**
 * The class containing all message resolution implementations of an Tone object.
 */
@MessageResolution(receiverType = ToneObjectType.class)
public class SplObjectMessageResolution {
    /*
     * An Tone object resolves the WRITE message and maps it to an object property write access.
     */
    @Resolve(message = "WRITE")
    public abstract static class ToneForeignWriteNode extends Node {

        @Child
        private SplWritePropertyCacheNode write = SplWritePropertyCacheNodeGen.create();
        @Child
        private ToneForeignToToneTypeNode nameToToneType = ToneForeignToToneTypeNodeGen.create();
        @Child
        private ToneForeignToToneTypeNode valueToToneType = ToneForeignToToneTypeNodeGen.create();

        public Object access(DynamicObject receiver, Object name, Object value) {
            Object convertedName = nameToToneType.executeConvert(name);
            Object convertedValue = valueToToneType.executeConvert(value);
            try {
                write.executeWrite(receiver, convertedName, convertedValue);
            } catch (SplUndefinedNameTypeErrorException undefinedName) {
                throw UnknownIdentifierException.raise(String.valueOf(convertedName));
            }
            return convertedValue;
        }
    }

    /*
     * An Tone object resolves the READ message and maps it to an object property read access.
     */
    @Resolve(message = "READ")
    public abstract static class ToneForeignReadNode extends Node {

        @Child
        private SplReadPropertyCacheNode read = SplReadPropertyCacheNodeGen.create();
        @Child
        private ToneForeignToToneTypeNode nameToToneType = ToneForeignToToneTypeNodeGen.create();

        public Object access(DynamicObject receiver, Object name) {
            Object convertedName = nameToToneType.executeConvert(name);
            Object result;
            try {
                result = read.executeRead(receiver, convertedName);
            } catch (SplUndefinedNameTypeErrorException undefinedName) {
                throw UnknownIdentifierException.raise(String.valueOf(convertedName));
            }
            return result;
        }
    }

    /*
     * An Tone object resolves the REMOVE message and maps it to an object property delete access.
     */
    @Resolve(message = "REMOVE")
    public abstract static class ToneForeignRemoveNode extends Node {

        @Child
        private ToneForeignToToneTypeNode nameToToneType = ToneForeignToToneTypeNodeGen.create();

        public Object access(DynamicObject receiver, Object name) {
            Object convertedName = nameToToneType.executeConvert(name);
            if (receiver.containsKey(convertedName)) {
                return receiver.delete(convertedName);
            } else {
                throw UnknownIdentifierException.raise(String.valueOf(convertedName));
            }
        }
    }

    /*
     * An Tone object resolves the INVOKE message and maps it to an object property read access
     * followed by an function invocation. The object property must be an Tone function object, which
     * is executed eventually.
     */
    @Resolve(message = "INVOKE")
    public abstract static class ToneForeignInvokeNode extends Node {

        @Child
        private SplDispatchNode dispatch = SplDispatchNodeGen.create();

        public Object access(DynamicObject receiver, String name, Object[] arguments) {
            Object property = receiver.get(name);
            if (property instanceof ToneFunction) {
                ToneFunction function = (ToneFunction) property;
                Object[] arr = new Object[arguments.length];
                // Before the arguments can be used by the ToneFunction, they need to be converted to
                // Tone
                // values.
                for (int i = 0; i < arguments.length; i++) {
                    arr[i] = SplContext.fromForeignValue(arguments[i]);
                }
                Object result = dispatch.executeDispatch(function, arr);
                return result;
            } else {
                throw UnknownIdentifierException.raise(name);
            }
        }
    }

    @Resolve(message = "HAS_KEYS")
    public abstract static class ToneForeignHasPropertiesNode extends Node {

        @SuppressWarnings("unused")
        public Object access(DynamicObject receiver) {
            return true;
        }
    }

    @Resolve(message = "KEY_INFO")
    public abstract static class ToneForeignPropertyInfoNode extends Node {

        public int access(DynamicObject receiver, Object name) {
            Object property = receiver.get(name);
            if (property == null) {
                return KeyInfo.INSERTABLE;
            } else if (property instanceof ToneFunction) {
                return KeyInfo.READABLE | KeyInfo.REMOVABLE | KeyInfo.MODIFIABLE | KeyInfo.INVOCABLE;
            } else {
                return KeyInfo.READABLE | KeyInfo.REMOVABLE | KeyInfo.MODIFIABLE;
            }
        }
    }

    @Resolve(message = "KEYS")
    public abstract static class ToneForeignPropertiesNode extends Node {
        public Object access(DynamicObject receiver) {
            return obtainKeys(receiver);
        }

        @CompilerDirectives.TruffleBoundary
        private static Object obtainKeys(DynamicObject receiver) {
            Object[] keys = receiver.getShape().getKeyList().toArray();
            return new KeysArray(keys);
        }
    }

    @MessageResolution(receiverType = KeysArray.class)
    static final class KeysArray implements TruffleObject {

        private final Object[] keys;

        KeysArray(Object[] keys) {
            this.keys = keys;
        }

        @Resolve(message = "HAS_SIZE")
        abstract static class HasSize extends Node {

            public Object access(@SuppressWarnings("unused") KeysArray receiver) {
                return true;
            }
        }

        @Resolve(message = "GET_SIZE")
        abstract static class GetSize extends Node {

            public Object access(KeysArray receiver) {
                return receiver.keys.length;
            }
        }

        @Resolve(message = "READ")
        abstract static class Read extends Node {

            public Object access(KeysArray receiver, int index) {
                try {
                    Object key = receiver.keys[index];
                    assert key instanceof String;
                    return key;
                } catch (IndexOutOfBoundsException e) {
                    CompilerDirectives.transferToInterpreter();
                    throw UnknownIdentifierException.raise(String.valueOf(index));
                }
            }
        }

        @Override
        public ForeignAccess getForeignAccess() {
            return KeysArrayForeign.ACCESS;
        }

        static boolean isInstance(TruffleObject array) {
            return array instanceof KeysArray;
        }

    }

}
