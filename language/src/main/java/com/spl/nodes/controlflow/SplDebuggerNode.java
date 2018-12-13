package com.spl.nodes.controlflow;

import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplStatementNode;

/**
 * Implementation of the Tone debugger statement. When under the debugger, execution suspends here.
 */
@NodeInfo(shortName = "debugger", description = "The node implementing a debugger statement")
public class SplDebuggerNode extends SplStatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
        // No op.
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == DebuggerTags.AlwaysHalt.class) {
            return true;
        }
        return super.hasTag(tag);
    }

}
