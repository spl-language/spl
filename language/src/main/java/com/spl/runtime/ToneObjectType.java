package com.spl.runtime;

import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.ObjectType;
import com.spl.nodes.runtime.SplObjectMessageResolutionForeign;

public final class ToneObjectType extends ObjectType {

    public static final ObjectType SINGLETON = new ToneObjectType();

    private ToneObjectType() {
    }

    public static boolean isInstance(TruffleObject obj) {
        return SplContext.isToneObject(obj);
    }

    @Override
    public ForeignAccess getForeignAccessFactory(DynamicObject obj) {
        return SplObjectMessageResolutionForeign.ACCESS;
    }
}
