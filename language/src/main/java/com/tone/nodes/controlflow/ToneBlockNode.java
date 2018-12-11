package com.tone.nodes.controlflow;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.tone.nodes.ToneStatementNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A statement node that just executes a list of other statements.
 */
@NodeInfo(shortName = "block", description = "The node implementing a source code block")
public final class ToneBlockNode extends ToneStatementNode {

    /**
     * The array of child nodes. The annotation {@link com.oracle.truffle.api.nodes.Node.Children
     * Children} informs Truffle that the field contains multiple children. It is a Truffle
     * requirement that the field is {@code final} and an array of nodes.
     */
    @Node.Children
    private final ToneStatementNode[] bodyNodes;

    public ToneBlockNode(ToneStatementNode[] bodyNodes) {
        this.bodyNodes = bodyNodes;
    }

    /**
     * Execute all child statements. The annotation {@link ExplodeLoop} triggers full unrolling of
     * the loop during compilation. This allows the {@link ToneStatementNode#executeVoid} method of
     * all children to be inlined.
     */
    @Override
    @ExplodeLoop
    public void executeVoid(VirtualFrame frame) {
        /*
         * This assertion illustrates that the array length is really a constant during compilation.
         */
        CompilerAsserts.compilationConstant(bodyNodes.length);

        for (ToneStatementNode statement : bodyNodes) {
            statement.executeVoid(frame);
        }
    }

    public List<ToneStatementNode> getStatements() {
        return Collections.unmodifiableList(Arrays.asList(bodyNodes));
    }
}