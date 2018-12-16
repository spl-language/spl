/*
 * The parser and lexer need to be generated using "mx create-spl-parser".
 */

grammar Spl;

@parser::header
{
// DO NOT MODIFY - generated from Spl.g4

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.RootCallTarget;
import com.spl.SplLanguage;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;
import com.spl.parser.SplParseError;
import com.spl.parser.SplNodeFactory;
import com.spl.parser.SplLexer;
}

@lexer::header
{
// DO NOT MODIFY - generated from Spl.g4
}

@parser::members
{
private SplNodeFactory factory;
private Source source;

private static final class BailoutErrorListener extends BaseErrorListener {
    private final Source source;
    BailoutErrorListener(Source source) {
        this.source = source;
    }
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String location = "-- line " + line + " col " + (charPositionInLine + 1) + ": ";
        throw new SplParseError(source, line, charPositionInLine + 1, offendingSymbol == null ? 1 : ((Token) offendingSymbol).getText().length(), String.format("Error(s) parsing script:%n" + location + msg));
    }
}

public void SemErr(Token token, String message) {
    int col = token.getCharPositionInLine() + 1;
    String location = "-- line " + token.getLine() + " col " + col + ": ";
    throw new SplParseError(source, token.getLine(), col, token.getText().length(), String.format("Error(s) parsing script:%n" + location + message));
}

public static Map<String, RootCallTarget> parseSpl(SplLanguage language, Source source) {
    SplLexer lexer = new SplLexer(CharStreams.fromString(source.getCharacters().toString()));
    SplParser parser = new SplParser(new CommonTokenStream(lexer));
    lexer.removeErrorListeners();
    parser.removeErrorListeners();
    BailoutErrorListener listener = new BailoutErrorListener(source);
    lexer.addErrorListener(listener);
    parser.addErrorListener(listener);
    parser.factory = new SplNodeFactory(language, source);
    parser.source = source;
    parser.spl();
    return parser.factory.getAllFunctions();
}
}

// parser

spl
:
dfunc dfunc* EOF
;

// define functioin
dfunc
:
IDENTIFIER
s='('
                                                { factory.startFunction($IDENTIFIER, $s); }
(
    IDENTIFIER                                  { factory.addFormalParameter($IDENTIFIER); }
    (
        ','
        IDENTIFIER                              { factory.addFormalParameter($IDENTIFIER); }
    )*
)?
')'
body=block[false]                               { factory.finishFunction($body.result); }
;



block [boolean inLoop] returns [SplStatementNode result]
:                                               { factory.startBlock();
                                                  List<SplStatementNode> body = new ArrayList<>(); }
s='begin'
(
    statement[inLoop]';'                        { body.add($statement.result); }
)*
    statement[inLoop]                           { body.add($statement.result); }
e='end'
                                                { $result = factory.finishBlock(body, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
;


statement [boolean inLoop] returns [SplStatementNode result]
:
(
    while_statement                             { $result = $while_statement.result; }
|
    if_statement[inLoop]                        { $result = $if_statement.result; }
|
    return_statement                            { $result = $return_statement.result; }
|
    expression                                  { $result = $expression.result; }
|
    d='debugger'                                { $result = factory.createDebugger($d); }
|
    id='int'                                    { List<Token> variables = new ArrayList<>(); }
    IDENTIFIER                                  { variables.add($IDENTIFIER); }
    (
        ','
        IDENTIFIER                              { variables.add($IDENTIFIER); }
    )*                                          { $result = factory.declareIntVariables($id, variables); }
|
    id='const'                                  { List<TokenAndValue> tokenValues = new ArrayList<>(); }
    IDENTIFIER                                  { TokenAndValue current = new TokenAndValue($IDENTIFIER); }
    '='
    expression                                  { current.setSplExpressionNode($expression.result);
                                                  tokenValues.add(current); }
    (
        ','
        IDENTIFIER                              { current = new TokenAndValue($IDENTIFIER); }
        '='
        expression                              { current.setSplExpressionNode($expression.result);
                                                  tokenValues.add(current); }
    )*                                           { $result = factory.declareConstVariable($id, tokenValues); }
|
    id='print'
    expression                                  { $result = factory.createPrint($id, $expression.result); }
|
    id='read'
    IDENTIFIER                                  { $result = factory.createRead($id, $IDENTIFIER); }
)
;


while_statement returns [SplStatementNode result]
:
w='while'
condition=expression
body=while_block                                { $result = factory.createWhile($w, $condition.result, $body.result); }
;


while_block returns [SplStatementNode result]
:                                               { factory.startBlock();
                                                  List<SplStatementNode> body = new ArrayList<>(); }
s='do'
(
    statement[true]';'                          { body.add($statement.result); }
)*
    statement[true]                             { body.add($statement.result); }
e='end'
                                                { $result = factory.finishBlock(body, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
;



if_statement [boolean inLoop] returns [SplStatementNode result]
:
i='if'
condition=expression
then=if_block[inLoop]                           { $result = factory.createIf($i, $condition.result, $then.result); }
;


if_block [boolean inLoop] returns [SplStatementNode result]
:                                               { factory.startBlock();
                                                  List<SplStatementNode> body = new ArrayList<>(); }
s='then'
(
    statement[false]';'                        { body.add($statement.result); }
)*
    statement[false]                           { body.add($statement.result); }
e='end'
                                                { $result = factory.finishBlock(body, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
;


return_statement returns [SplStatementNode result]
:
r='return'                                      { SplExpressionNode value = null; }
(
    expression                                  { value = $expression.result; }
)?                                              { $result = factory.createReturn($r, value); }
;


expression returns [SplExpressionNode result]
:
logic_term                                      { $result = $logic_term.result; }
(
    op='||'
    logic_term                                  { $result = factory.createBinary($op, $result, $logic_term.result); }
)*
;


logic_term returns [SplExpressionNode result]
:
logic_factor                                    { $result = $logic_factor.result; }
(
    op='&&'
    logic_factor                                { $result = factory.createBinary($op, $result, $logic_factor.result); }
)*
;


logic_factor returns [SplExpressionNode result]
:
arithmetic                                      { $result = $arithmetic.result; }
(
    op=('<' | '<=' | '>' | '>=' | '==' | '!=' )
    arithmetic                                  { $result = factory.createBinary($op, $result, $arithmetic.result); }
)?
;


arithmetic returns [SplExpressionNode result]
:
term                                            { $result = $term.result; }
(
    op=('+' | '-')
    term                                        { $result = factory.createBinary($op, $result, $term.result); }
)*
;


term returns [SplExpressionNode result]
:
factor                                          { $result = $factor.result; }
(
    op=('*' | '/' | '%')
    factor                                      { $result = factory.createBinary($op, $result, $factor.result); }
)*
;


factor returns [SplExpressionNode result]
:
(
    IDENTIFIER                                  { SplExpressionNode assignmentName = factory.createStringLiteral($IDENTIFIER, false); }
    (
        member_expression[null, null, assignmentName] { $result = $member_expression.result; }
    |
                                                { $result = factory.createRead(assignmentName); }
    )
|
    STRING_LITERAL                              { $result = factory.createStringLiteral($STRING_LITERAL, true); }
|
    sign=('+'| '-')?
    NUMERIC_LITERAL                             { $result = factory.createNumericLiteral($sign, $NUMERIC_LITERAL); }
|
    s='('
    expr=expression
    e=')'                                       { $result = factory.createParenExpression($expr.result, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
)
;


member_expression [SplExpressionNode r, SplExpressionNode assignmentReceiver, SplExpressionNode assignmentName] returns [SplExpressionNode result]
:                                               { SplExpressionNode receiver = r;
                                                  SplExpressionNode nestedAssignmentName = null; }
(
    '('                                         { List<SplExpressionNode> parameters = new ArrayList<>();
                                                  if (receiver == null) {
                                                      receiver = factory.createRead(assignmentName);
                                                  } }
    (
        expression                              { parameters.add($expression.result); }
        (
            ','
            expression                          { parameters.add($expression.result); }
        )*
    )?
    e=')'
                                                { $result = factory.createCall(receiver, parameters, $e); }
|
    '='
    expression                                  { if (assignmentName == null) {
                                                      SemErr($expression.start, "invalid assignment target");
                                                  } else if (assignmentReceiver == null) {
                                                      $result = factory.createAssignment(assignmentName, $expression.result);
                                                  } else {
                                                      $result = factory.createWriteProperty(assignmentReceiver, assignmentName, $expression.result);
                                                  } }
)
(
    member_expression[$result, receiver, nestedAssignmentName] { $result = $member_expression.result; }
)?
;

// lexer

WS : [ \t\r\n\u000C]+ -> skip;
COMMENT : '/*' .*? '*/' -> skip;
LINE_COMMENT : '//' ~[\r\n]* -> skip;

fragment LETTER : [A-Z] | [a-z] | '_' | '$';
fragment NON_ZERO_DIGIT : [1-9];
fragment DIGIT : [0-9];
fragment TAB : '\t';
fragment STRING_CHAR : ~('"' | '\\' | '\r' | '\n');

IDENTIFIER : LETTER (LETTER | DIGIT)*;
STRING_LITERAL : '"' STRING_CHAR* '"';
NUMERIC_LITERAL : '0' | NON_ZERO_DIGIT DIGIT*;
