package com.spl.parser;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.spl.SplLanguage;
import com.spl.builtins.SplPrintBuiltin;
import com.spl.builtins.SplPrintBuiltinNodeGen;
import com.spl.builtins.SplReadBuiltin;
import com.spl.builtins.SplReadBuiltinNodeGen;
import com.spl.common.SplType;
import com.spl.exceptions.SplException;
import com.spl.exceptions.SplTypeErrorException;
import com.spl.nodes.SlotInfo;
import com.spl.nodes.SplBinaryNode;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplRootNode;
import com.spl.nodes.SplStatementNode;
import com.spl.nodes.access.SplReadPropertyNode;
import com.spl.nodes.access.SplReadPropertyNodeGen;
import com.spl.nodes.access.SplWritePropertyNode;
import com.spl.nodes.access.SplWritePropertyNodeGen;
import com.spl.nodes.call.SplInvokeNode;
import com.spl.nodes.controlflow.SplBlockNode;
import com.spl.nodes.controlflow.SplDebuggerNode;
import com.spl.nodes.controlflow.SplFunctionBodyNode;
import com.spl.nodes.controlflow.SplIfNode;
import com.spl.nodes.controlflow.SplListOfNodes;
import com.spl.nodes.controlflow.SplReturnNode;
import com.spl.nodes.controlflow.SplWhileNode;
import com.spl.nodes.expression.SplAddNodeGen;
import com.spl.nodes.expression.SplBigIntegerLiteralNode;
import com.spl.nodes.expression.SplDivNodeGen;
import com.spl.nodes.expression.SplEqualNodeGen;
import com.spl.nodes.expression.SplFunctionLiteralNode;
import com.spl.nodes.expression.SplLessOrEqThanNodeGen;
import com.spl.nodes.expression.SplLessThanNodeGen;
import com.spl.nodes.expression.SplLogicalAndNode;
import com.spl.nodes.expression.SplLogicalNotNodeGen;
import com.spl.nodes.expression.SplLogicalOrNode;
import com.spl.nodes.expression.SplModuloNodeGen;
import com.spl.nodes.expression.SplMulNodeGen;
import com.spl.nodes.expression.SplParenExpressionNode;
import com.spl.nodes.expression.SplStringLiteralNode;
import com.spl.nodes.expression.SplSubNodeGen;
import com.spl.nodes.expression.SplUnboxNodeGen;
import com.spl.nodes.literal.SplBooleanLiteralNode;
import com.spl.nodes.literal.SplLongLiteralNode;
import com.spl.nodes.local.SplReadArgumentNode;
import com.spl.nodes.local.SplReadLocalVariableNode;
import com.spl.nodes.local.SplReadLocalVariableNodeGen;
import com.spl.nodes.local.SplWriteLocalVariableNode;
import com.spl.nodes.local.SplWriteLocalVariableNodeGen;
import org.antlr.v4.runtime.Token;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SplNodeFactory {

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
    private List<SplStatementNode> methodNodes;

    /* State while parsing a block. */
    private LexicalScope lexicalScope;
    private final SplLanguage language;

    public SplNodeFactory(SplLanguage language, Source source) {
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
        final SplReadArgumentNode readArg = new SplReadArgumentNode(parameterCount);
        SplExpressionNode assignment = createAssignment(createStringLiteral(nameToken, false), readArg, parameterCount, true);
        methodNodes.add(assignment);
        parameterCount++;
    }

    public void finishFunction(SplStatementNode bodyNode) {
        if (bodyNode == null) {
            // a state update that would otherwise be performed by finishBlock
            lexicalScope = lexicalScope.outer;
        } else {
            methodNodes.add(bodyNode);
            final int bodyEndPos = bodyNode.getSourceEndIndex();
            final SourceSection functionSrc = source.createSection(functionStartPos, bodyEndPos - functionStartPos);
            final SplStatementNode methodBlock = finishBlock(methodNodes, functionBodyStartPos, bodyEndPos - functionBodyStartPos);
            assert lexicalScope == null : "Wrong scoping of blocks in parser";

            final SplFunctionBodyNode functionBodyNode = new SplFunctionBodyNode(methodBlock);
            functionBodyNode.setSourceSection(functionSrc.getCharIndex(), functionSrc.getCharLength());

            final SplRootNode rootNode = new SplRootNode(language, frameDescriptor, functionBodyNode, functionSrc, functionName);
            allFunctions.put(functionName, Truffle.getRuntime().createCallTarget(rootNode));
        }

        functionStartPos = 0;
        functionName = null;
        functionBodyStartPos = 0;
        parameterCount = 0;
        frameDescriptor = null;
        lexicalScope = null;
    }

    public void finishStatement(SplStatementNode statementNode) {
        final int statementNodeSourceEndIndex = statementNode.getSourceEndIndex();
        final SourceSection functionSrc = source.createSection(functionStartPos, statementNodeSourceEndIndex - functionStartPos);
        final SplRootNode rootNode = new SplRootNode(language, frameDescriptor, null, functionSrc, functionName);
        statements.add(Truffle.getRuntime().createCallTarget(rootNode));
    }

    public void startBlock() {
        lexicalScope = new LexicalScope(lexicalScope);
    }

    public SplStatementNode finishBlock(List<SplStatementNode> bodyNodes, int startPos, int length) {
        lexicalScope = lexicalScope.outer;

        if (containsNull(bodyNodes)) {
            return null;
        }

        List<SplStatementNode> flattenedNodes = new ArrayList<>(bodyNodes.size());
        flattenBlocks(bodyNodes, flattenedNodes);
        for (SplStatementNode statement : flattenedNodes) {
            if (statement.hasSource() && !isHaltInCondition(statement)) {
                statement.addStatementTag();
            }
        }
        SplBlockNode blockNode = new SplBlockNode(flattenedNodes.toArray(new SplStatementNode[flattenedNodes.size()]));
        blockNode.setSourceSection(startPos, length);
        return blockNode;
    }

    private static boolean isHaltInCondition(SplStatementNode statement) {
        return (statement instanceof SplIfNode) || (statement instanceof SplWhileNode);
    }

    private void flattenBlocks(Iterable<? extends SplStatementNode> bodyNodes, List<SplStatementNode> flattenedNodes) {
        for (SplStatementNode n : bodyNodes) {
            if (n instanceof SplBlockNode) {
                flattenBlocks(((SplBlockNode) n).getStatements(), flattenedNodes);
            } else {
                flattenedNodes.add(n);
            }
        }
    }

    /**
     * Returns an {@link SplDebuggerNode} for the given token.
     *
     * @param debuggerToken The token containing the debugger node's info.
     * @return A SplDebuggerNode for the given token.
     */
    public SplStatementNode createDebugger(Token debuggerToken) {
        final SplDebuggerNode debuggerNode = new SplDebuggerNode();
        srcFromToken(debuggerNode, debuggerToken);
        return debuggerNode;
    }

    /**
     * Returns an {@link SplWhileNode} for the given parameters.
     *
     * @param whileToken    The token containing the while node's info
     * @param conditionNode The conditional node for this while loop
     * @param bodyNode      The body of the while loop
     * @return A SplWhileNode built using the given parameters. null if either conditionNode or
     * bodyNode is null.
     */
    public SplStatementNode createWhile(Token whileToken, SplExpressionNode conditionNode, SplStatementNode bodyNode) {
        if (conditionNode == null || bodyNode == null) {
            return null;
        }

        conditionNode.addStatementTag();
        final int start = whileToken.getStartIndex();
        final int end = bodyNode.getSourceEndIndex();
        final SplWhileNode whileNode = new SplWhileNode(conditionNode, bodyNode);
        whileNode.setSourceSection(start, end - start);
        return whileNode;
    }

    /**
     * Returns an {@link SplIfNode} for the given parameters.
     *
     * @param ifToken       The token containing the if node's info
     * @param conditionNode The condition node of this if statement
     * @param thenPartNode  The then part of the if
     * @return An SplIfNode for the given parameters. null if either conditionNode or thenPartNode is
     * null.
     */
    public SplStatementNode createIf(Token ifToken, SplExpressionNode conditionNode, SplStatementNode thenPartNode) {
        if (conditionNode == null || thenPartNode == null) {
            return null;
        }

        conditionNode.addStatementTag();
        final int start = ifToken.getStartIndex();
        final int end = thenPartNode.getSourceEndIndex();
        final SplIfNode ifNode = new SplIfNode(conditionNode, thenPartNode);
        ifNode.setSourceSection(start, end - start);
        return ifNode;
    }

    /**
     * Returns an {@link SplReturnNode} for the given parameters.
     *
     * @param t         The token containing the return node's info
     * @param valueNode The value of the return (null if not returning a value)
     * @return An SplReturnNode for the given parameters.
     */
    public SplStatementNode createReturn(Token t, SplExpressionNode valueNode) {
        final int start = t.getStartIndex();
        final int length = valueNode == null ? t.getText().length() : valueNode.getSourceEndIndex() - start;
        final SplReturnNode returnNode = new SplReturnNode(valueNode);
        returnNode.setSourceSection(start, length);
        return returnNode;
    }

    /**
     * Returns the corresponding subclass of {@link SplExpressionNode} for binary expressions. </br>
     * These nodes are currently not instrumented.
     *
     * @param opToken   The operator of the binary expression
     * @param leftNode  The left node of the expression
     * @param rightNode The right node of the expression
     * @return A subclass of SplExpressionNode using the given parameters based on the given opToken.
     * null if either leftNode or rightNode is null.
     */
    public SplExpressionNode createBinary(Token opToken, SplExpressionNode leftNode, SplExpressionNode rightNode) {
        if (leftNode == null || rightNode == null) {
            return null;
        }
        final SplExpressionNode leftUnboxed;
        if (leftNode instanceof SplBinaryNode) {  // SplBinaryNode never returns boxed value
            leftUnboxed = leftNode;
        } else {
            leftUnboxed = SplUnboxNodeGen.create(leftNode);
        }
        final SplExpressionNode rightUnboxed;
        if (rightNode instanceof SplBinaryNode) {  // SplBinaryNode never returns boxed value
            rightUnboxed = rightNode;
        } else {
            rightUnboxed = SplUnboxNodeGen.create(rightNode);
        }

        final SplExpressionNode result;
        switch (opToken.getText()) {
            case "+":
                result = SplAddNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "*":
                result = SplMulNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "/":
                result = SplDivNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "%":
                result = SplModuloNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "-":
                result = SplSubNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "<":
                result = SplLessThanNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "<=":
                result = SplLessOrEqThanNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case ">":
                result = SplLogicalNotNodeGen.create(SplLessOrEqThanNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case ">=":
                result = SplLogicalNotNodeGen.create(SplLessThanNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case "==":
                result = SplEqualNodeGen.create(leftUnboxed, rightUnboxed);
                break;
            case "!=":
                result = SplLogicalNotNodeGen.create(SplEqualNodeGen.create(leftUnboxed, rightUnboxed));
                break;
            case "&&":
                result = new SplLogicalAndNode(leftUnboxed, rightUnboxed);
                break;
            case "||":
                result = new SplLogicalOrNode(leftUnboxed, rightUnboxed);
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
     * Returns an {@link SplInvokeNode} for the given parameters.
     *
     * @param functionNode   The function being called
     * @param parameterNodes The parameters of the function call
     * @param finalToken     A token used to determine the end of the sourceSelection for this call
     * @return An SplInvokeNode for the given parameters. null if functionNode or any of the
     * parameterNodes are null.
     */
    public SplExpressionNode createCall(SplExpressionNode functionNode, List<SplExpressionNode> parameterNodes, Token finalToken) {
        if (functionNode == null || containsNull(parameterNodes)) {
            return null;
        }

        final SplExpressionNode result = new SplInvokeNode(functionNode, parameterNodes.toArray(new SplExpressionNode[parameterNodes.size()]));

        final int startPos = functionNode.getSourceCharIndex();
        final int endPos = finalToken.getStartIndex() + finalToken.getText().length();
        result.setSourceSection(startPos, endPos - startPos);
        result.addExpressionTag();

        return result;
    }

    /**
     * Write value
     *
     * @param printToken name of method
     * @param parameter  parameter to write out
     * @return statement to write
     */
    public SplStatementNode createPrint(Token printToken, SplExpressionNode parameter) {
        final SplPrintBuiltin result = SplPrintBuiltinNodeGen.create(parameter);
        final int startPos = printToken.getStartIndex();
        result.setSourceSection(startPos, parameter.getSourceEndIndex() - startPos);
        result.addExpressionTag();
        return result;
    }

    /**
     * Read value
     *
     * @param readToken      name of method
     * @param nameOfVariable save read value to this variable name
     * @return expression to write local variable
     */
    public SplStatementNode createRead(Token readToken, Token nameOfVariable) {
        FrameSlot frameSlot = frameDescriptor.findFrameSlot(nameOfVariable.getText());
        if (frameSlot == null) {
            throw new SplException("Variable " + nameOfVariable.getText() + " is not declared.\nDeclare it before use it.\nint " + nameOfVariable.getText() + ";\" ");
        } else {
            Object info = frameSlot.getInfo();
            if (info != null && !((SlotInfo) info).getType().isUpdatable()) {
                throw new SplException("You can't update const value.");
            }
        }
        lexicalScope.locals.put(nameOfVariable.getText(), frameSlot);
        SplReadBuiltin splReadBuiltin = SplReadBuiltinNodeGen.create(((SlotInfo) frameSlot.getInfo()).getType());
        final SplWriteLocalVariableNode result = SplWriteLocalVariableNodeGen.create(splReadBuiltin, frameSlot);

        int length = nameOfVariable.getStopIndex() - readToken.getStartIndex() + 1;
        result.setSourceSection(readToken.getStartIndex(), length);
        result.addExpressionTag();
        return result;
    }

    public SplStatementNode declareIntVariables(Token nameOfType, List<Token> nameOfVariables) {
        SplType splType = SplType.getByHumaneType(nameOfType.getText());

        SplListOfNodes result = new SplListOfNodes(nameOfVariables.stream().map(Token::getText)
                .map(variableName -> frameDescriptor.addFrameSlot(variableName, new SlotInfo(splType), FrameSlotKind.Illegal))
                .map(frameSlot -> {
                    SplLongLiteralNode toneLongLiteralNode = new SplLongLiteralNode(0);
                    return SplWriteLocalVariableNodeGen.create(toneLongLiteralNode, frameSlot);
                }).toArray(SplStatementNode[]::new));

        result.setSourceSection(nameOfType.getStartIndex(), nameOfVariables.get(nameOfVariables.size() - 1).getStopIndex() - nameOfType.getStartIndex() + 1);
        result.addExpressionTag();
        return result;
    }

    public SplStatementNode declareConstVariable(Token nameOfType, List<TokenAndValue> tokenAndValues) {
        SplStatementNode[] splStatementNodes = tokenAndValues.stream()
                .map(tokenAndValue -> {
                    String variableName = tokenAndValue.getToken().getText();
                    FrameSlot frameSlot = frameDescriptor.addFrameSlot(variableName, new SlotInfo(SplType.CONST), FrameSlotKind.Illegal);
                    lexicalScope.locals.put(variableName, frameSlot);
                    return SplWriteLocalVariableNodeGen.create(tokenAndValue.getSplExpressionNode(), frameSlot);
                })
                .toArray(SplStatementNode[]::new);

        SplListOfNodes result = new SplListOfNodes(splStatementNodes);

        result.setSourceSection(nameOfType.getStartIndex(), tokenAndValues.get(tokenAndValues.size() - 1).getSplExpressionNode().getSourceEndIndex() - nameOfType.getStartIndex());
        result.addExpressionTag();
        return result;
    }

    /**
     * Returns an {@link SplWriteLocalVariableNode} for the given parameters.
     *
     * @param nameNode  The name of the variable being assigned
     * @param valueNode The value to be assigned
     * @return An SplExpressionNode for the given parameters. null if nameNode or valueNode is null.
     */
    public SplExpressionNode createAssignment(SplExpressionNode nameNode, SplExpressionNode valueNode) {
        return createAssignment(nameNode, valueNode, null, false);
    }

    /**
     * Returns an {@link SplWriteLocalVariableNode} for the given parameters.
     *
     * @param nameNode      The name of the variable being assigned
     * @param valueNode     The value to be assigned
     * @param argumentIndex null or index of the argument the assignment is assigning
     * @return An SplExpressionNode for the given parameters. null if nameNode or valueNode is null.
     */
    public SplExpressionNode createAssignment(SplExpressionNode nameNode, SplExpressionNode valueNode, Integer argumentIndex, boolean create) {
        if (nameNode == null || valueNode == null) {
            return null;
        }

        String name = ((SplStringLiteralNode) nameNode).executeGeneric(null);
        FrameSlot frameSlot;
        if (create) {
            frameSlot = frameDescriptor.addFrameSlot(name, new SlotInfo(SplType.OBJECT), FrameSlotKind.Illegal);
        } else {
            frameSlot = frameDescriptor.findFrameSlot(name);
            if (frameSlot == null) {
                throw new SplException("Variable \"" + name + "\" is not declared.\nDeclare it before assign something.");
            } else {
                Object info = frameSlot.getInfo();
                if (info != null && !((SlotInfo) info).getType().isUpdatable()) {
                    throw new SplException("Variable \"" + name + "\" is const. You can't update const value.");
                }
            }
        }
        lexicalScope.locals.put(name, frameSlot);
//        if (((SlotInfo)frameSlot.getInfo()).getType().isInt()) {
//            if (!(valueNode instanceof SplLongLiteralNode ||
//                    valueNode instanceof SplFunctionLiteralNode ||
//                    valueNode instanceof SplInvokeNode ||
//                    valueNode instanceof SplReadLocalVariableNode)) {
//                throw new SplException("Incompatible types.");
//            }
//        }
        final SplExpressionNode result = SplWriteLocalVariableNodeGen.create(valueNode, frameSlot);

        if (valueNode.hasSource()) {
            final int start = nameNode.getSourceCharIndex();
            final int length = valueNode.getSourceEndIndex() - start;
            result.setSourceSection(start, length);
        }
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns a {@link SplReadLocalVariableNode} if this read is a local variable or a
     * {@link SplFunctionLiteralNode} if this read is global. In Tone, the only global names are
     * functions.
     *
     * @param nameNode The name of the variable/function being read
     * @return either:
     * <ul>
     * <li>A SplReadLocalVariableNode representing the local variable being read.</li>
     * <li>A SplFunctionLiteralNode representing the function definition.</li>
     * <li>null if nameNode is null.</li>
     * </ul>
     */
    public SplExpressionNode createRead(SplExpressionNode nameNode) {
        if (nameNode == null) {
            return null;
        }

        if (nameNode instanceof SplBooleanLiteralNode) {
            return nameNode;
        }

        String name = ((SplStringLiteralNode) nameNode).executeGeneric(null);
        final SplExpressionNode result;
        final FrameSlot frameSlot = lexicalScope == null ? null : lexicalScope.locals.get(name);
        if (frameSlot != null) {
            /* Read of a local variable. */
            result = SplReadLocalVariableNodeGen.create(frameSlot);
        } else {
            /* Read of a global name. In our language, the only global names are functions. */
            result = new SplFunctionLiteralNode(language, name);
        }
        result.setSourceSection(nameNode.getSourceCharIndex(), nameNode.getSourceLength());
        result.addExpressionTag();
        return result;
    }

    public SplExpressionNode createStringLiteral(Token literalToken, boolean removeQuotes) {
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

        final SplStringLiteralNode result = new SplStringLiteralNode(literal.intern());
        srcFromToken(result, literalToken);
        result.addExpressionTag();
        return result;
    }

    public SplExpressionNode createNumericLiteral(Token sign, Token literalToken) {
        SplExpressionNode result;
        String digits = literalToken.getText();
        String number = (sign == null ? "" : sign.getText()) + digits;
        try {
            /* Try if the literal is small enough to fit into a long value. */
            result = new SplLongLiteralNode(Long.parseLong(number));
        } catch (NumberFormatException ex) {
            /* Overflow of long value, so fall back to BigInteger. */
            result = new SplBigIntegerLiteralNode(new BigInteger(number));
        }
        srcFromToken(result, literalToken);
        result.addExpressionTag();
        return result;
    }

    public SplExpressionNode createBooleanLiteral(boolean value) {
        return new SplBooleanLiteralNode(value);
    }

    public SplExpressionNode createParenExpression(SplExpressionNode expressionNode, int start, int length) {
        if (expressionNode == null) {
            return null;
        }

        final SplParenExpressionNode result = new SplParenExpressionNode(expressionNode);
        result.setSourceSection(start, length);
        return result;
    }

    /**
     * Returns an {@link SplReadPropertyNode} for the given parameters.
     *
     * @param receiverNode The receiver of the property access
     * @param nameNode     The name of the property being accessed
     * @return An SplExpressionNode for the given parameters. null if receiverNode or nameNode is
     * null.
     */
    public SplExpressionNode createReadProperty(SplExpressionNode receiverNode, SplExpressionNode nameNode) {
        if (receiverNode == null || nameNode == null) {
            return null;
        }

        final SplExpressionNode result = SplReadPropertyNodeGen.create(receiverNode, nameNode);

        final int startPos = receiverNode.getSourceCharIndex();
        final int endPos = nameNode.getSourceEndIndex();
        result.setSourceSection(startPos, endPos - startPos);
        result.addExpressionTag();

        return result;
    }

    /**
     * Returns an {@link SplWritePropertyNode} for the given parameters.
     *
     * @param receiverNode The receiver object of the property assignment
     * @param nameNode     The name of the property being assigned
     * @param valueNode    The value to be assigned
     * @return An SplExpressionNode for the given parameters. null if receiverNode, nameNode or
     * valueNode is null.
     */
    public SplExpressionNode createWriteProperty(SplExpressionNode receiverNode, SplExpressionNode nameNode, SplExpressionNode valueNode) {
        if (receiverNode == null || nameNode == null || valueNode == null) {
            return null;
        }

        final SplExpressionNode result = SplWritePropertyNodeGen.create(receiverNode, nameNode, valueNode);

        final int start = receiverNode.getSourceCharIndex();
        final int length = valueNode.getSourceEndIndex() - start;
        result.setSourceSection(start, length);
        result.addExpressionTag();

        return result;
    }

    /**
     * Creates source description of a single token.
     */
    private static void srcFromToken(SplStatementNode node, Token token) {
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
