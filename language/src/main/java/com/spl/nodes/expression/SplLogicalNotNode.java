package com.spl.nodes.expression;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplExpressionNode;

@NodeChild("valueNode")
@NodeInfo(shortName = "!")
public abstract class SplLogicalNotNode extends SplExpressionNode {

    @Specialization
    protected boolean doBoolean(boolean value) {
        return !value;
    }

    @Fallback
    protected Object typeError(Object value) {
        throw SplTypeErrorException.typeError(this, value);
    }
}
