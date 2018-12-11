package com.tone.nodes.local;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.runtime.ToneNull;

public class ToneReadArgumentNode extends ToneExpressionNode {

    /** The argument number, i.e., the index into the array of arguments. */
    private final int index;

    /**
     * Profiling information, collected by the interpreter, capturing whether the function was
     * called with fewer actual arguments than formal arguments.
     */
    private final BranchProfile outOfBoundsTaken = BranchProfile.create();

    public ToneReadArgumentNode(int index) {
        this.index = index;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        if (index < args.length) {
            return args[index];
        } else {
            /* In the interpreter, record profiling information that the branch was used. */
            outOfBoundsTaken.enter();
            /* Use the default null value. */
            return ToneNull.SINGLETON;
        }
    }
}
