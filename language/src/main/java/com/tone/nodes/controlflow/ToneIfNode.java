package com.tone.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.tone.ToneException;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;
import com.tone.nodes.expression.ToneUnboxNodeGen;

@NodeInfo(shortName = "if", description = "The node implementing a condional statement")
public final class ToneIfNode extends ToneStatementNode {

    /**
     * The condition of the {@code if}. This in a {@link ToneExpressionNode} because we require a
     * result value. We do not have a node type that can only return a {@code boolean} value, so
     * {@link #evaluateCondition executing the condition} can lead to a type error.
     */
    @Node.Child
    private ToneExpressionNode conditionNode;

    /** Statement (or {@link ToneBlockNode block}) executed when the condition is true. */
    @Node.Child
    private ToneStatementNode thenPartNode;

    /** Statement (or {@link ToneBlockNode block}) executed when the condition is false. */
    @Node.Child
    private ToneStatementNode elsePartNode;

    private final ConditionProfile condition = ConditionProfile.createCountingProfile();

    public ToneIfNode(ToneExpressionNode conditionNode, ToneStatementNode thenPartNode, ToneStatementNode elsePartNode) {
        this.conditionNode = ToneUnboxNodeGen.create(conditionNode);
        this.thenPartNode = thenPartNode;
        this.elsePartNode = elsePartNode;
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
        } else {
            /* Execute the else-branch (which is optional according to the Tone syntax). */
            if (elsePartNode != null) {
                elsePartNode.executeVoid(frame);
            }
        }
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        try {
            /*
             * The condition must evaluate to a boolean value, so we call the boolean-specialized
             * execute method.
             */
            return conditionNode.executeBoolean(frame);
        } catch (UnexpectedResultException ex) {
            /*
             * The condition evaluated to a non-boolean result. This is a type error in the Tone
             * program.
             */
            throw ToneException.typeError(this, ex.getResult());
        }
    }
}
