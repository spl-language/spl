package com.tone.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.tone.nodes.ToneExpressionNode;

/**
 * A {@link ToneExpressionNode} that represents a parenthesized expression; it simply returns the
 * value of the enclosed (child) expression. It is represented separately in the AST for the purpose
 * of correct source attribution; this preserves the lexical relationship between the two
 * parentheses and allows a tool to describe the expression as distinct from its contents.
 */
@NodeInfo(description = "A parenthesized expression")
public class ToneParenExpressionNode extends ToneExpressionNode {

    @Node.Child
    private ToneExpressionNode expression;

    public ToneParenExpressionNode(ToneExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return expression.executeGeneric(frame);
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return expression.executeLong(frame);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return expression.executeBoolean(frame);
    }
}
