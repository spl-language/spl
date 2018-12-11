package com.tone.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneStatementNode;

/**
 * Implementation of the Tone break statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link ToneBreakNode} and the {@link ToneWhileNode} of the loop we are
 * breaking out. This is done by throwing an {@link ToneBreakException exception} that is caught by
 * the {@link ToneWhileNode#executeVoid loop node}.
 */
@NodeInfo(shortName = "break", description = "The node implementing a break statement")
public final class ToneBreakNode extends ToneStatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
        throw ToneBreakException.SINGLETON;
    }
}
