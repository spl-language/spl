package com.tone.runtime;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.ObjectType;
import com.tone.nodes.runtime.ToneObjectMessageResolutionForeign;

public final class ToneObjectType extends ObjectType {

    public static final ObjectType SINGLETON = new ToneObjectType();

    private ToneObjectType() {
    }

    public static boolean isInstance(TruffleObject obj) {
        return ToneContext.isToneObject(obj);
    }

    @Override
    public ForeignAccess getForeignAccessFactory(DynamicObject obj) {
        return ToneObjectMessageResolutionForeign.ACCESS;
    }
}
