package com.tone.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.tone.ToneException;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.interop.ToneForeignToToneTypeNode;
import com.tone.nodes.runtime.ToneNull;
import com.tone.runtime.ToneBigNumber;
import com.tone.runtime.ToneFunction;

@NodeChild("child")
public abstract class ToneUnboxNode extends ToneExpressionNode {

    @Specialization
    protected long unboxLong(long value) {
        return value;
    }

    @Specialization
    protected ToneBigNumber unboxBigNumber(ToneBigNumber value) {
        return value;
    }

    @Specialization
    protected boolean unboxBoolean(boolean value) {
        return value;
    }

    @Specialization
    protected String unboxString(String value) {
        return value;
    }

    @Specialization
    protected ToneFunction unboxFunction(ToneFunction value) {
        return value;
    }

    @Specialization
    protected ToneNull unboxNull(ToneNull value) {
        return value;
    }

    @Specialization(guards = "isBoxedPrimitive(value)")
    protected Object unboxBoxed(
                    Object value,
                    @Cached("create()") ToneForeignToToneTypeNode foreignToTone) {
        return foreignToTone.unbox((TruffleObject) value);
    }

    @Specialization(guards = "!isBoxedPrimitive(value)")
    protected Object unboxGeneric(Object value) {
        return value;
    }

    @Node.Child
    private Node isBoxed;

    protected boolean isBoxedPrimitive(Object value) {
        if (value instanceof TruffleObject) {
            if (isBoxed == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                isBoxed = insert(Message.IS_BOXED.createNode());
            }
            if (ForeignAccess.sendIsBoxed(isBoxed, (TruffleObject) value)) {
                return true;
            }
        }
        return false;
    }

    @Fallback
    protected Object typeError(Object value) {
        throw ToneException.typeError(this, value);
    }

}
