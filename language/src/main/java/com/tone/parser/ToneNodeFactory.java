package com.tone.parser;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.tone.ToneLanguage;
import com.tone.nodes.ToneBinaryNode;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneRootNode;
import com.tone.nodes.ToneStatementNode;
import com.tone.nodes.access.ToneReadPropertyNode;
import com.tone.nodes.access.ToneReadPropertyNodeGen;
import com.tone.nodes.access.ToneWritePropertyNode;
import com.tone.nodes.access.ToneWritePropertyNodeGen;
import com.tone.nodes.call.ToneInvokeNode;
import com.tone.nodes.controlflow.ToneBlockNode;
import com.tone.nodes.controlflow.ToneBreakNode;
import com.tone.nodes.controlflow.ToneContinueNode;
import com.tone.nodes.controlflow.ToneDebuggerNode;
import com.tone.nodes.controlflow.ToneForNode;
import com.tone.nodes.controlflow.ToneFunctionBodyNode;
import com.tone.nodes.controlflow.ToneIfNode;
import com.tone.nodes.controlflow.ToneReturnNode;
import com.tone.nodes.controlflow.ToneWhileNode;
import com.tone.nodes.expression.ToneAddNodeGen;
import com.tone.nodes.expression.ToneBigIntegerLiteralNode;
import com.tone.nodes.expression.ToneDivNodeGen;
import com.tone.nodes.expression.ToneEqualNodeGen;
import com.tone.nodes.expression.ToneFunctionLiteralNode;
import com.tone.nodes.expression.ToneLessOrEqThanNodeGen;
import com.tone.nodes.expression.ToneLessThanNodeGen;
import com.tone.nodes.expression.ToneLogicalAndNode;
import com.tone.nodes.expression.ToneLogicalNotNodeGen;
import com.tone.nodes.expression.ToneLogicalOrNode;
import com.tone.nodes.expression.ToneModuloNodeGen;
import com.tone.nodes.expression.ToneMulNodeGen;
import com.tone.nodes.expression.ToneParenExpressionNode;
import com.tone.nodes.expression.ToneStringLiteralNode;
import com.tone.nodes.expression.ToneSubNodeGen;
import com.tone.nodes.expression.ToneUnboxNodeGen;
import com.tone.nodes.literal.ToneBooleanLiteralNode;
import com.tone.nodes.literal.ToneLongLiteralNode;
import com.tone.nodes.local.ToneReadArgumentNode;
import com.tone.nodes.local.ToneReadLocalVariableNode;
import com.tone.nodes.local.ToneReadLocalVariableNodeGen;
import com.tone.nodes.local.ToneWriteLocalVariableNode;
import com.tone.nodes.local.ToneWriteLocalVariableNodeGen;
import org.antlr.v4.runtime.Token;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ToneNodeFactory {

    /**
     * Local variable names that are visible in the current block. Variables are not visible outside
     * of their defining block, to prevent the usage of undefined variables. Because of that, we can
     * decide during parsing if a name references a local variable or is a function name.
     */
    static class LexicalScope {
        protected final LexicalScope outer;
        protected final Map<String, FrameSlot> locals;

        LexicalScope(LexicalScope outer) {
            this.outer = outer;
            this.locals = new HashMap<>();
            if (outer != null) {
                locals.putAll(outer.locals);
            }
        }
    }

    /* State while parsing a source unit. */
    private final Source source;
    private final Map<String, RootCallTarget> allFunctions;
    private final List<RootCallTarget> statements;

    /* State while parsing a function. */
    private int functionStartPos;
    private String functionName;
    private int functionBodyStartPos; // includes parameter list
    private int parameterCount;
    private FrameDescriptor frameDescriptor;
    private List<ToneStatementNode> methodNodes;

    private List<ToneStatementNode> statementNodes;

    /* State while parsing a block. */
    private LexicalScope lexicalScope;
    private final ToneLanguage language;

    public ToneNodeFactory(ToneLanguage language, Source source) {
        this.language = language;
        this.source = source;
        this.allFunctions = new HashMap<>();
        this.statements = new LinkedList<>();
    }

    public Map<String, RootCallTarget> getAllFunctions() {
        return allFunctions;
    }

    public List<RootCallTarget> getAllStatements() {
        return statements;
    }

    public void startFunction(Token nameToken, Token bodyStartToken) {
        assert functionStartPos == 0;
        assert functionName == null;
        assert functionBodyStartPos == 0;
        assert parameterCount == 0;
        assert frameDescriptor == null;
        assert lexicalScope == null;

        functionStartPos = nameToken.getStartIndex();
        functionName = nameToken.getText();
        functionBodyStartPos = bodyStartToken.getStartIndex();
        frameDescriptor = new FrameDescriptor();
        methodNodes = new ArrayList<>();
        startBlock();
    }

    public void addFormalParameter(Token nameToken) {
        /*
         * Method parameters are assigned to local variables at the beginning of the method. This
         * ensures that accesses to parameters are specialized the same way as local variables are
         * specialized.
         */
        final ToneReadArgumentNode readArg = new ToneReadArgumentNode(parameterCount);
        ToneExpressionNode assignment = createAssignment(createStringLiteral(nameToken, false), readArg, parameterCount);
        methodNodes.add(assignment);
        parameterCount++;
    }

    public void finishFunction(ToneStatementNode bodyNode) {
        if (bodyNode == null) {
            // a state update that would otherwise be performed by finishBlock
            lexicalScope = lexicalScope.outer;
        } else {
            methodNodes.add(bodyNode);
            final int bodyEndPos = bodyNode.getSourceEndIndex();
            final SourceSection functionSrc = source.createSection(functionStartPos, bodyEndPos - functionStartPos);
            final ToneStatementNode methodBlock = finishBlock(methodNodes, functionBodyStartPos, bodyEndPos - functionBodyStartPos);
            assert lexicalScope == null : "Wrong scoping of blocks in parser";

            final ToneFunctionBodyNode functionBodyNode = new ToneFunctionBodyNode(methodBlock);
            functionBodyNode.setSourceSection(functionSrc.getCharIndex(), functionSrc.getCharLength());

            final ToneRootNode rootNode = new ToneRootNode(language, frameDescriptor, functionBodyNode, functionSrc, functionName);
            allFunctions.put(functionName, Truffle.getRuntime().createCallTarget(rootNode));
        }

        functionStartPos = 0;
        functionName = null;
        functionBodyStartPos = 0;
        parameterCount = 0;
        frameDescriptor = null;
        lexicalScope = null;
    }

    public void finishStatement(ToneStatementNode statementNode) {
        statementNodes.add(statementNode);
        final int statementNodeSourceEndIndex = statementNode.getSourceEndIndex();
        final SourceSection functionSrc = source.createSection(functionStartPos, statementNodeSourceEndIndex - functionStartPos);
        final ToneRootNode rootNode = new ToneRootNode(language, frameDescriptor, null, functionSrc, functionName);
        statements.add(Truffle.getRuntime().createCallTarget(rootNode));
    }

    public void startBlock() {
        lexicalScope = new LexicalScope(lexicalScope);
    }

    public ToneStatementNode finishBlock(List<ToneStatementNode> bodyNodes, int startPos, int length) {
        lexicalScope = lexicalScope.outer;

        if (containsNull(bodyNodes)) {
            return null;
        }

        List<ToneStatementNode> flattenedNodes = new ArrayList<>(bodyNodes.size());
        flattenBlocks(bodyNodes, flattenedNodes);
        for (ToneStatementNode statement : flattenedNodes) {
            if (statement.hasSource() && !isHaltInCondition(statement)) {
                statement.addStatementTag();
            }
        }
        ToneBlockNode blockNode = new ToneBlockNode(flattenedNodes.toArray(new ToneStatementNode[flattenedNodes.size()]));
        blockNode.setSourceSection(startPos, length);
        return blockNode;
    }

    private static boolean isHaltInCondition(ToneStatementNode statement) {
        return (statement instanceof ToneIfNode) || (statement instanceof ToneWhileNode);
    }

    private void flattenBlocks(Iterable<? extends ToneStatementNode> bodyNodes, List<ToneStatementNode> flattenedNodes) {
        for (ToneStatementNode n : bodyNodes) {
            if (n instanceof ToneBlockNode) {
                flattenBlocks(((ToneBlockNode) n).getStatements(), flattenedNodes);
            } else {
                flattenedNodes.add(n);
            }
        }
    }

    /**
     * Returns an {@link ToneDebuggerNode} for the given token.
     *
     * @param debuggerToken The token containing the debugger node's info.
     * @return A ToneDebuggerNode for the given token.
     */
    public ToneStatementNode createDebugger(Token debuggerToken) {
        final ToneDebuggerNode debuggerNode = new ToneDebuggerNode();
        srcFromToken(debuggerNode, debuggerToken);
        return debuggerNode;
    }

    /**
     * Returns an {@link ToneBreakNode} for the given token.
     *
     * @param breakToken The token containing the break node's info.
     * @return A ToneBreakNode for the given token.
     */
    public ToneStatementNode createBreak(Token breakToken) {
        final ToneBreakNode breakNode = new ToneBreakNode();
        srcFromToken(breakNode, breakToken);
        return breakNode;
    }

    /**
     * Returns an {@link ToneContinueNode} for the given token.
     *
     * @param continueToken The token containing the continue node's info.
     * @return A ToneContinueNode built using the given token.
     */
    public ToneStatementNode createContinue(Token continueToken) {
        final ToneContinueNode continueNode = new ToneContinueNode();
        srcFromToken(continueNode, continueToken);
        return continueNode;
    }

    /**
     * Returns an {@link ToneWhileNode} for the given parameters.
     *
     * @param whileToken The token containing the while node's info
     * @param conditionNode The conditional node for this while loop
     * @param bodyNode The body of the while loop
     * @return A ToneWhileNode built using the given parameters. null if either conditionNode or
     *         bodyNode is null.
     */
    public ToneStatementNode createWhile(Token whileToken, ToneExpressionNode conditionNode, ToneStatementNode bodyNode) {
        if (conditionNode == null || bodyNode == null) {
            return null;
        }

        conditionNode.addStatementTag();
        final int start = whileToken.getStartIndex();
        final int end = bodyNode.getSourceEndIndex();
        final ToneWhileNode whileNode = new ToneWhileNode(conditionNode, bodyNode);
        whileNode.setSourceSection(start, end - start);
        return whileNode;
    }

    public ToneStatementNode createFor(Token f, ToneStatementNode initNode, ToneExpressionNode conditionNode, ToneStatementNode iteration, ToneStatementNode bodyNode) {
        if (bodyNode == null) {
            return null;
        }
        if (initNode != null) {
            initNode.addStatementTag();
        }
        if (conditionNode != null) {
            conditionNode.addStatementTag();
        }
        if (iteration != null) {
            iteration.addStatementTag();
        }
        int startIndex = f.getStartIndex();
        int sourceEndIndex = bodyNode.getSourceEndIndex();
        ToneForNode toneForNode = new ToneForNode(initNode, conditionNode, iteration, bodyNode);
        toneForNode.setSourceSection(startIndex, sourceEndIndex - startIndex);
        return toneForNode;
    }

    /**
     * Returns an {@link ToneIfNode} for the given parameters.
     *
     * @param ifToken The token containing the if node's info
     * @param conditionNode The condition node of this if statement
     * @param thenPartNode The then part of the if
     * @param elsePartNode The else part of the if (null if no else part)
     * @return An ToneIfNode for the given parameters. null if either conditionNode or thenPartNode is
     *         null.
     */
    public ToneStatementNode createIf(Token ifToken, ToneExpressionNode conditionNode, ToneStatementNode thenPartNode, ToneStatementNode elsePartNode) {
        if (conditionNode == null || thenPartNode == null) {
            return null;
        }

        conditionNode.addStatementTag();
        final int start = ifToken.getStartIndex();
        final int end = elsePartNode == null ? thenPartNode.getSourceEndIndex() : elsePartNode.getSourceEndIndex();
        final ToneIfNode ifNode = new ToneIfNode(conditionNode, thenPartNode, elsePartNode);
        ifNode.setSourceSection(start, end - start);
        return ifNode;
    }

    /**
     * Returns an {@link ToneReturnNode} for the given parameters.
     *
     * @param t The token containing the return node's info
     * @param valueNode The value of the return (null if not returning a value)
     * @return An ToneReturnNode for the given parameters.
     */
    public ToneStatementNode createReturn(Token t, ToneExpressionNode valueNode) {
        final int start = t.getStartIndex();
        final int length = valueNode == null ? t.getText().length() : valueNode.getSourceEndIndex() - start;
        final ToneReturnNode returnNode = new ToneReturnNode(valueNode);
        returnNode.setSourceSection(start, length);
        return returnNode;
    }

    /**
     * Returns the corresponding subclass of {@link ToneExpressionNode} for binary expressions. </br>
     * These nodes are currently not instrumented.
     *
     * @param opToken The operator of the binary expression
     * @param leftNode The left node of the expression
     * @param rightNode The right node of the expression
     * @return A subclass of ToneExpressionNode using the given parameters based on the given opToken.
     *         null if either leftNode or rightNode is null.
     */
    public ToneExpressionNode createBinary(Token opToken, ToneExpressionNode leftNode, ToneExpressionNode rightNode) {
        if (leftNode == null || rightNode == null) {
            return null;
        }
        final ToneExpressionNode leftUnboxed;
        if (leftNode instanceof ToneBinaryNode) {  // ToneBinaryNode never returns boxed value
            leftUnboxed = leftNode;
        } else {
            leftUnboxed = ToneUnboxNodeGen.create(leftNode);
        }
        final ToneExpressionNode rightUnboxed;
        if (rightNode instanceof ToneBinaryNode) {  // ToneBinaryNode never returns boxed value
            rightUnboxed = rightNode;
        } else {
            rightUnboxed = ToneUnboxNodeGen.create(rightNode);
        }

        final ToneExpressionNode result;
        switch (opToken.getText()) {
            case "+":
                result = ToneAddNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "*":
                result = ToneMulNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "/":
                result = ToneDivNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "%":
                result = ToneModuloNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "-":
                result = ToneSubNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "<":
                result = ToneLessThanNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "<=":
                result = ToneLessOrEqThanNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case ">":
                result = ToneLogicalNotNodeGen.create(ToneLessOrEqThanNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case ">=":
                result = ToneLogicalNotNodeGen.create(ToneLessThanNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case "==":
                result = ToneEqualNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "!=":
                result = ToneLogicalNotNodeGen.create(ToneEqualNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case "&&":
                result = new ToneLogicalAndNode(leftUnboxed, rightUnboxed);
                break;
            case "||":
                result = new ToneLogicalOrNode(leftUnboxed, rightUnboxed);
                break;
            default:
                throw new RuntimeException("unexpected operation: " + opToken.getText());
        }

        int start = leftNode.getSourceCharIndex();
        int length = rightNode.getSourceEndIndex() - start;
        result.setSourceSection(start, length);
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns an {@link ToneInvokeNode} for the given parameters.
     *
     * @param functionNode The function being called
     * @param parameterNodes The parameters of the function call
     * @param finalToken A token used to determine the end of the sourceSelection for this call
     * @return An ToneInvokeNode for the given parameters. null if functionNode or any of the
     *         parameterNodes are null.
     */
    public ToneExpressionNode createCall(ToneExpressionNode functionNode, List<ToneExpressionNode> parameterNodes, Token finalToken) {
        if (functionNode == null || containsNull(parameterNodes)) {
            return null;
        }

        final ToneExpressionNode result = new ToneInvokeNode(functionNode, parameterNodes.toArray(new ToneExpressionNode[parameterNodes.size()]));

        final int startPos = functionNode.getSourceCharIndex();
        final int endPos = finalToken.getStartIndex() + finalToken.getText().length();
        result.setSourceSection(startPos, endPos - startPos);
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns an {@link ToneWriteLocalVariableNode} for the given parameters.
     *
     * @param nameNode The name of the variable being assigned
     * @param valueNode The value to be assigned
     * @return An ToneExpressionNode for the given parameters. null if nameNode or valueNode is null.
     */
    public ToneExpressionNode createAssignment(ToneExpressionNode nameNode, ToneExpressionNode valueNode) {
        return createAssignment(nameNode, valueNode, null);
    }

    /**
     * Returns an {@link ToneWriteLocalVariableNode} for the given parameters.
     *
     * @param nameNode The name of the variable being assigned
     * @param valueNode The value to be assigned
     * @param argumentIndex null or index of the argument the assignment is assigning
     * @return An ToneExpressionNode for the given parameters. null if nameNode or valueNode is null.
     */
    public ToneExpressionNode createAssignment(ToneExpressionNode nameNode, ToneExpressionNode valueNode, Integer argumentIndex) {
        if (nameNode == null || valueNode == null) {
            return null;
        }

        String name = ((ToneStringLiteralNode) nameNode).executeGeneric(null);
        FrameSlot frameSlot = frameDescriptor.findOrAddFrameSlot(
                name,
                argumentIndex,
                FrameSlotKind.Illegal);
        lexicalScope.locals.put(name, frameSlot);
        final ToneExpressionNode result = ToneWriteLocalVariableNodeGen.create(valueNode, frameSlot);

        if (valueNode.hasSource()) {
            final int start = nameNode.getSourceCharIndex();
            final int length = valueNode.getSourceEndIndex() - start;
            result.setSourceSection(start, length);
        }
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns a {@link ToneReadLocalVariableNode} if this read is a local variable or a
     * {@link ToneFunctionLiteralNode} if this read is global. In Tone, the only global names are
     * functions.
     *
     * @param nameNode The name of the variable/function being read
     * @return either:
     *         <ul>
     *         <li>A ToneReadLocalVariableNode representing the local variable being read.</li>
     *         <li>A ToneFunctionLiteralNode representing the function definition.</li>
     *         <li>null if nameNode is null.</li>
     *         </ul>
     */
    public ToneExpressionNode createRead(ToneExpressionNode nameNode) {
        if (nameNode == null) {
            return null;
        }

        if (nameNode instanceof ToneBooleanLiteralNode) {
            return nameNode;
        }

        String name = ((ToneStringLiteralNode) nameNode).executeGeneric(null);
        final ToneExpressionNode result;
        final FrameSlot frameSlot = lexicalScope == null ? null : lexicalScope.locals.get(name);
        if (frameSlot != null) {
            /* Read of a local variable. */
            result = ToneReadLocalVariableNodeGen.create(frameSlot);
        } else {
            /* Read of a global name. In our language, the only global names are functions. */
            result = new ToneFunctionLiteralNode(language, name);
        }
        result.setSourceSection(nameNode.getSourceCharIndex(), nameNode.getSourceLength());
        result.addExpressionTag();
        return result;
    }

    public ToneExpressionNode createStringLiteral(Token literalToken, boolean removeQuotes) {
        /* Remove the trailing and ending " */
        String literal = literalToken.getText();
        if (removeQuotes) {
            assert literal.length() >= 2 && literal.startsWith("\"") && literal.endsWith("\"");
            literal = literal.substring(1, literal.length() - 1);
        } else {
            if (literal.equals(Boolean.TRUE.toString())) {
                return createBooleanLiteral(true);
            } else if (literal.equals(Boolean.FALSE.toString())) {
                return createBooleanLiteral(false);
            }
        }

        final ToneStringLiteralNode result = new ToneStringLiteralNode(literal.intern());
        srcFromToken(result, literalToken);
        result.addExpressionTag();
        return result;
    }

    public ToneExpressionNode createNumericLiteral(Token literalToken) {
        ToneExpressionNode result;
        try {
            /* Try if the literal is small enough to fit into a long value. */
            result = new ToneLongLiteralNode(Long.parseLong(literalToken.getText()));
        } catch (NumberFormatException ex) {
            /* Overflow of long value, so fall back to BigInteger. */
            result = new ToneBigIntegerLiteralNode(new BigInteger(literalToken.getText()));
        }
        srcFromToken(result, literalToken);
        result.addExpressionTag();
        return result;
    }

    public ToneExpressionNode createBooleanLiteral(boolean value) {
        return new ToneBooleanLiteralNode(value);
    }

    public ToneExpressionNode createParenExpression(ToneExpressionNode expressionNode, int start, int length) {
        if (expressionNode == null) {
            return null;
        }

        final ToneParenExpressionNode result = new ToneParenExpressionNode(expressionNode);
        result.setSourceSection(start, length);
        return result;
    }

    /**
     * Returns an {@link ToneReadPropertyNode} for the given parameters.
     *
     * @param receiverNode The receiver of the property access
     * @param nameNode The name of the property being accessed
     * @return An ToneExpressionNode for the given parameters. null if receiverNode or nameNode is
     *         null.
     */
    public ToneExpressionNode createReadProperty(ToneExpressionNode receiverNode, ToneExpressionNode nameNode) {
        if (receiverNode == null || nameNode == null) {
            return null;
        }

        final ToneExpressionNode result = ToneReadPropertyNodeGen.create(receiverNode, nameNode);

        final int startPos = receiverNode.getSourceCharIndex();
        final int endPos = nameNode.getSourceEndIndex();
        result.setSourceSection(startPos, endPos - startPos);
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns an {@link ToneWritePropertyNode} for the given parameters.
     *
     * @param receiverNode The receiver object of the property assignment
     * @param nameNode The name of the property being assigned
     * @param valueNode The value to be assigned
     * @return An ToneExpressionNode for the given parameters. null if receiverNode, nameNode or
     *         valueNode is null.
     */
    public ToneExpressionNode createWriteProperty(ToneExpressionNode receiverNode, ToneExpressionNode nameNode, ToneExpressionNode valueNode) {
        if (receiverNode == null || nameNode == null || valueNode == null) {
            return null;
        }

        final ToneExpressionNode result = ToneWritePropertyNodeGen.create(receiverNode, nameNode, valueNode);

        final int start = receiverNode.getSourceCharIndex();
        final int length = valueNode.getSourceEndIndex() - start;
        result.setSourceSection(start, length);
        result.addExpressionTag();

        return result;
    }

    /**
     * Creates source description of a single token.
     */
    private static void srcFromToken(ToneStatementNode node, Token token) {
        node.setSourceSection(token.getStartIndex(), token.getText().length());
    }

    /**
     * Checks whether a list contains a null.
     */
    private static boolean containsNull(List<?> list) {
        for (Object e : list) {
            if (e == null) {
                return true;
            }
        }
        return false;
    }

}
