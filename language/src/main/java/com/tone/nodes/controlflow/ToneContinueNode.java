package com.tone.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneStatementNode;

/**
 * Implementation of the Tone continue statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link ToneContinueNode} and the {@link ToneWhileNode} of the loop we
 * are continuing. This is done by throwing an {@link ToneStatementNode exception} that is caught
 * by the {@link ToneWhileNode#executeVoid loop node}.
 */
@NodeInfo(shortName = "continue", description = "The node implementing a continue statement")
public final class ToneContinueNode extends ToneStatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
        throw ToneContinueException.SINGLETON;
    }
}
