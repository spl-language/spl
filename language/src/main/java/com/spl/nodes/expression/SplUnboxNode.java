package com.spl.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.interop.ToneForeignToToneTypeNode;
import com.spl.nodes.runtime.SplNull;
import com.spl.runtime.SplBigNumber;
import com.spl.runtime.SplFunction;

@NodeChild("child")
public abstract class SplUnboxNode extends SplExpressionNode {

    @Specialization
    protected long unboxLong(long value) {
        return value;
    }

    @Specialization
    protected SplBigNumber unboxBigNumber(SplBigNumber value) {
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
    protected SplFunction unboxFunction(SplFunction value) {
        return value;
    }

    @Specialization
    protected SplNull unboxNull(SplNull value) {
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
        throw SplTypeErrorException.typeError(this, value);
    }

}
