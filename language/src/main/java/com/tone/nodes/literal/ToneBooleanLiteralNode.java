package com.tone.nodes.literal;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

public class ToneBooleanLiteralNode extends ToneLiteralNode {

    private final boolean value;

    public ToneBooleanLiteralNode(boolean value) {
        this.value = value;
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return value;
    }
}
