package com.spl.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.spl.exceptions.SplException;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;
import com.spl.nodes.expression.SplUnboxNodeGen;

@NodeInfo(shortName = "if", description = "The node implementing a condional statement")
public final class SplIfNode extends SplStatementNode {

    /**
     * The condition of the {@code if}. This in a {@link SplExpressionNode} because we require a
     * result value. We do not have a node type that can only return a {@code boolean} value, so
     * {@link #evaluateCondition executing the condition} can lead to a type error.
     */
    @Node.Child
    private SplExpressionNode conditionNode;

    /** Statement (or {@link SplBlockNode block}) executed when the condition is true. */
    @Node.Child
    private SplStatementNode thenPartNode;

    private final ConditionProfile condition = ConditionProfile.createCountingProfile();

    public SplIfNode(SplExpressionNode conditionNode, SplStatementNode thenPartNode) {
        this.conditionNode = SplUnboxNodeGen.create(conditionNode);
        this.thenPartNode = thenPartNode;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        /*
         * In the interpreter, record profiling information that the condition was executed and with
         * which outcome.
         */
        if (condition.profile(evaluateCondition(frame))) {
            /* Execute the then-branch. */
            thenPartNode.executeVoid(frame);
        }
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        boolean cond;
        /*
         * The condition must evaluate to a boolean value, so we call the boolean-specialized
         * execute method.
         */
        Object obj = conditionNode.executeGeneric(frame);
        if (obj instanceof Boolean) {
            cond = (boolean) obj;
        } else if (obj instanceof Long){
            cond = ((long) obj) > 0;
        } else {
            throw new SplException("Bad condition type for if statement");
        }
        return cond;
    }
}
