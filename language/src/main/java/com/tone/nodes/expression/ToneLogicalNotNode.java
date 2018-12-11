package com.tone.nodes.expression;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.ToneException;
import com.tone.nodes.ToneExpressionNode;

@NodeChild("valueNode")
@NodeInfo(shortName = "!")
public abstract class ToneLogicalNotNode extends ToneExpressionNode {

    @Specialization
    protected boolean doBoolean(boolean value) {
        return !value;
    }

    @Fallback
    protected Object typeError(Object value) {
        throw ToneException.typeError(this, value);
    }
}
