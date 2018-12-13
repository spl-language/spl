package com.spl.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ForeignAccess;
import com.oracle.truffle.api.interop.Message;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function that queries if the foreign object is executable. See
 * <link>Messages.IS_EXECUTABLE</link>.
 */
@NodeInfo(shortName = "isExecutable")
public abstract class SplIsExecutableBuiltin extends SplBuiltinNode {

    @Node.Child
    private Node isExecutable = Message.IS_EXECUTABLE.createNode();

    @Specialization
    public Object isExecutable(TruffleObject obj) {
        return ForeignAccess.sendIsExecutable(isExecutable, obj);
    }
}
