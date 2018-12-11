package com.tone.nodes.controlflow;

import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;

/**
 * The loop body of a {@link ToneWhileNode while loop}. A Truffle framework {@link LoopNode} between
 * the {@link ToneWhileNode} and {@link ToneForRepeatingNode} allows Truffle to perform loop
 * optimizations, for example, compile just the loop body for long running loops.
 */
public final class ToneForRepeatingNode extends Node implements RepeatingNode {

    private boolean isFirstTime = true;

    @Child
    private ToneStatementNode initNode;

    @Child
    private ToneExpressionNode conditionNode;

    @Child
    private ToneStatementNode iteration;

    @Child
    private ToneStatementNode bodyNode;

    private final BranchProfile continueTaken = BranchProfile.create();
    private final BranchProfile breakTaken = BranchProfile.create();

    public ToneForRepeatingNode(ToneStatementNode initNode, ToneExpressionNode conditionNode, ToneStatementNode iteration, ToneStatementNode bodyNode) {
        this.initNode = initNode;
        this.conditionNode = conditionNode;
        this.iteration = iteration;
        this.bodyNode = bodyNode;
    }

    @Override
    public boolean executeRepeating(VirtualFrame frame) {
        if (isFirstTime) {
            if (initNode != null) {
                initNode.executeVoid(frame);
            }
            isFirstTime = false;
        } else {
            if (iteration != null) {
                iteration.executeVoid(frame);
            }
        }
        if (conditionNode != null && !evaluateCondition(frame)) {
            return false;
        }

        try {
            /* Execute the loop body. */
            bodyNode.executeVoid(frame);
            /* Continue with next loop iteration. */
            return true;

        } catch (ToneContinueException ex) {
            continueTaken.enter();
            return true;

        } catch (ToneBreakException ex) {
            /* In the interpreter, record profiling information that the loop uses break. */
            breakTaken.enter();
            /* Break out of the loop. */
            return false;
        }
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        try {
            return conditionNode.executeBoolean(frame);
        } catch (UnexpectedResultException ex) {
            throw new UnsupportedSpecializationException(this, new Node[]{conditionNode}, ex.getResult());
        }
    }

    @Override
    public String toString() {
        return ToneStatementNode.formatSourceSection(this);
    }
}
