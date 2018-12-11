package com.tone.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function that queries if the foreign object has a size. See
 * <link>Messages.HAS_SIZE</link>.
 */
@NodeInfo(shortName = "hasSize")
public abstract class ToneHasSizeBuiltin extends ToneBuiltinNode {

    @Node.Child
    private Node hasSize = Message.HAS_SIZE.createNode();

    @Specialization
    public Object hasSize(TruffleObject obj) {
        return ForeignAccess.sendHasSize(hasSize, obj);
    }
}
