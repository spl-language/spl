package com.tone.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function that queries if the foreign object is a null value. See
 * <link>Messages.IS_NULL</link>.
 */
@NodeInfo(shortName = "isNull")
public abstract class ToneIsNullBuiltin extends ToneBuiltinNode {

    @Node.Child
    private Node isNull = Message.IS_NULL.createNode();

    @Specialization
    public Object isNull(TruffleObject obj) {
        return ForeignAccess.sendIsNull(isNull, obj);
    }
}
