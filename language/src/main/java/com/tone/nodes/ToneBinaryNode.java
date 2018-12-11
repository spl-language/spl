package com.tone.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class ToneBinaryNode extends ToneExpressionNode {

}
