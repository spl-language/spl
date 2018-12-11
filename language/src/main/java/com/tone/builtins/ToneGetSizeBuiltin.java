package com.tone.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.ToneException;

/**
 * Built-in function that queries the size property of a foreign object. See
 * <link>Messages.GET_SIZE</link>.
 */
@NodeInfo(shortName = "getSize")
public abstract class ToneGetSizeBuiltin extends ToneBuiltinNode {

    @Node.Child
    private Node getSize = Message.GET_SIZE.createNode();

    @Specialization
    public Object getSize(TruffleObject obj) {
        try {
            return ForeignAccess.sendGetSize(getSize, obj);
        } catch (UnsupportedMessageException e) {
            throw new ToneException(e.getMessage(), this);
        }
    }
}
