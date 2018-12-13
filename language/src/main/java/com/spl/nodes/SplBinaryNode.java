package com.spl.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class SplBinaryNode extends SplExpressionNode {

}
