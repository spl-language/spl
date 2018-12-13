package com.spl.nodes.controlflow;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A statement node that just executes a list of other statements.
 */
@NodeInfo(shortName = "declare", description = "The node implementing a source code block")
public final class SplListOfNodes extends SplExpressionNode {

    /**
     * The array of child nodes. The annotation {@link Children
     * Children} informs Truffle that the field contains multiple children. It is a Truffle
     * requirement that the field is {@code final} and an array of nodes.
     */
    @Children
    private final SplStatementNode[] bodyNodes;

    public SplListOfNodes(SplStatementNode[] bodyNodes) {
        this.bodyNodes = bodyNodes;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return null;
    }

    /**
     * Execute all child statements. The annotation {@link ExplodeLoop} triggers full unrolling of
     * the loop during compilation. This allows the {@link SplStatementNode#executeVoid} method of
     * all children to be inlined.
     */
    @Override
    @ExplodeLoop
    public void executeVoid(VirtualFrame frame) {
        /*
         * This assertion illustrates that the array length is really a constant during compilation.
         */
        CompilerAsserts.compilationConstant(bodyNodes.length);

        for (SplStatementNode statement : bodyNodes) {
            statement.executeVoid(frame);
        }
    }

    public List<SplStatementNode> getStatements() {
        return Collections.unmodifiableList(Arrays.asList(bodyNodes));
    }
}
