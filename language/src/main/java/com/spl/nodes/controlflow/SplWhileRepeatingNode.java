package com.spl.nodes.controlflow;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;
import com.spl.nodes.expression.SplUnboxNodeGen;

/**
 * The loop body of a {@link SplWhileNode while loop}. A Truffle framework {@link LoopNode} between
 * the {@link SplWhileNode} and {@link SplWhileRepeatingNode} allows Truffle to perform loop
 * optimizations, for example, compile just the loop body for long running loops.
 */
public final class SplWhileRepeatingNode extends Node implements RepeatingNode {

    /**
     * The condition of the loop. This in a {@link SplExpressionNode} because we require a result
     * value. We do not have a node type that can only return a {@code boolean} value, so
     * {@link #evaluateCondition executing the condition} can lead to a type error.
     */
    @Child
    private SplExpressionNode conditionNode;

    /** Statement (or {@link SplBlockNode block}) executed as long as the condition is true. */
    @Child
    private SplStatementNode bodyNode;

    /**
     * Profiling information, collected by the interpreter, capturing whether a {@code continue}
     * statement was used in this loop. This allows the compiler to generate better code for loops
     * without a {@code continue}.
     */
    private final BranchProfile continueTaken = BranchProfile.create();
    private final BranchProfile breakTaken = BranchProfile.create();

    public SplWhileRepeatingNode(SplExpressionNode conditionNode, SplStatementNode bodyNode) {
        this.conditionNode = SplUnboxNodeGen.create(conditionNode);
        this.bodyNode = bodyNode;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
        if (!evaluateCondition(frame)) {
            /* Normal exit of the loop when loop condition is false. */
            return false;
        }

        /* Execute the loop body. */
        bodyNode.executeVoid(frame);

        /* Continue with next loop iteration. */
        return true;
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        try {
            /*
             * The condition must evaluate to a boolean value, so we call the boolean-specialized
             * execute method.
             */
            return conditionNode.executeLong(frame) > 0;
        } catch (UnexpectedResultException ex) {
            /*
             * The condition evaluated to a non-boolean result. This is a type error in the Tone
             * program. We report it with the same exception that Truffle DSL generated nodes use to
             * report type errors.
             */
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, ex.getResult());
        }
    }

    @Override
    public String toString() {
        return SplStatementNode.formatSourceSection(this);
    }

}
