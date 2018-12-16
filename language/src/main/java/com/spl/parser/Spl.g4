grammar Spl;

// фрагмент коду який добавляється як заголовок парсера
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

// фрагмент коду який добавляється як заголовок lexer
@lexer::header
{
// DO NOT MODIFY - generated from Spl.g4
}

// фрагмент коду який добавляється як частина створеного класу для parser
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
// Файл мови spl повинен містити функції (не містить глобальних змінних), в яких точкою входу буде метод main
spl
:
dfunc dfunc* EOF
;


// Визначення функціх в spl
dfunc
:
IDEN // назва функції
s='('                                                                                  // відкриваються дужки для параметрів
                                                { factory.startFunction($IDEN, $s); }
(
    IDEN                                        { factory.addFormalParameter($IDEN); } // назва параметра
    (
        ','                                                                            // може містити декілька параметрів розділені комою
        IDEN                                    { factory.addFormalParameter($IDEN); } // назва параметра
    )*                                                                                 // кількість параметрів для функції необмежена
)?                                                                                     // функція може не мати параметри
')'
block=body[false]                               { factory.finishFunction($block.result); } // тіло функції
;


body [boolean inLoop] returns [SplStatementNode result] // описує тіло функції
:                                               { factory.startBlock();
                                                  List<SplStatementNode> body = new ArrayList<>(); }
s='begin' // початок функції
(
    statement[inLoop]';'                        { body.add($statement.result); }
)*
    statement[inLoop]                           { body.add($statement.result); }
e='end' // кінець функції
                                                { $result = factory.finishBlock(body, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
;


statement [boolean inLoop] returns [SplStatementNode result] // можливі значення які містить тіло функції
:
(
    while_statement                             { $result = $while_statement.result; }
|
    if_statement[inLoop]                        { $result = $if_statement.result; }
|
    return_statement                            { $result = $return_statement.result; }
|
    expr                                        { $result = $expr.result; }
|
    dvarb                                       { $result = $dvarb.result; }
|
    dconst                                      { $result = $dconst.result; }
|
    id='print'
    expr                                        { $result = factory.createPrint($id, $expr.result); }
|
    id='read'
    IDEN                                        { $result = factory.createRead($id, $IDEN); }
)
;


dvarb returns [SplStatementNode result] // опис змінних
:
    id='int'                                    { List<Token> variables = new ArrayList<>(); }
    IDEN                                        { variables.add($IDEN); }
    (
        ','
        IDEN                                    { variables.add($IDEN); }
    )*                                          { $result = factory.declareIntVariables($id, variables); }
;


dconst returns [SplStatementNode result] // опис констант та їх значення
:
    id='const'                                  { List<TokenAndValue> tokenValues = new ArrayList<>(); }
    IDEN                                        { TokenAndValue current = new TokenAndValue($IDEN); }
    '='
    expr                                        { current.setSplExpressionNode($expr.result);
                                                  tokenValues.add(current); }
    (
        ','
        IDEN                                    { current = new TokenAndValue($IDEN); }
        '='
        expr                                    { current.setSplExpressionNode($expr.result);
                                                  tokenValues.add(current); }
    )*                                          { $result = factory.declareConstVariable($id, tokenValues); }
;


while_statement returns [SplStatementNode result] // цикл while
:
w='while'
condition=expr
block=while_block                                { $result = factory.createWhile($w, $condition.result, $block.result); }
;


while_block returns [SplStatementNode result] // тіло циклу while
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



if_statement [boolean inLoop] returns [SplStatementNode result] // розгалуження if
:
i='if'
condition=expr
then=if_block[inLoop]                           { $result = factory.createIf($i, $condition.result, $then.result); }
;


if_block [boolean inLoop] returns [SplStatementNode result] // тіло if
:                                               { factory.startBlock();
                                                  List<SplStatementNode> body = new ArrayList<>(); }
s='then'
(
    statement[false]';'                         { body.add($statement.result); }
)*
    statement[false]                            { body.add($statement.result); }
e='end'
                                                { $result = factory.finishBlock(body, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
;


return_statement returns [SplStatementNode result] // поверненя значення з функції
:
r='return'                                      { SplExpressionNode value = null; }
(
    expr                                        { value = $expr.result; }
)?                                              { $result = factory.createReturn($r, value); }
;


expr returns [SplExpressionNode result] // логічне "або" для boolean перевірок
:
logic_term                                      { $result = $logic_term.result; }
(
    op='||'
    logic_term                                  { $result = factory.createBinary($op, $result, $logic_term.result); }
)*
;


logic_term returns [SplExpressionNode result] // логічне "i" для boolean перевірок
:
logic_factor                                    { $result = $logic_factor.result; }
(
    op='&&'
    logic_factor                                { $result = factory.createBinary($op, $result, $logic_factor.result); }
)*
;


logic_factor returns [SplExpressionNode result] // порівняння чисел
:
arithmetic                                      { $result = $arithmetic.result; }
(
    op=('<' | '<=' | '>' | '>=' | '==' | '!=' )
    arithmetic                                  { $result = factory.createBinary($op, $result, $arithmetic.result); }
)?
;


arithmetic returns [SplExpressionNode result] // оператор + та -
:
term                                            { $result = $term.result; }
(
    op=('+' | '-')
    term                                        { $result = factory.createBinary($op, $result, $term.result); }
)*
;


term returns [SplExpressionNode result] // оператор *, /, %
:
fact                                            { $result = $fact.result; }
(
    op=('*' | '/' | '%')
    fact                                        { $result = factory.createBinary($op, $result, $fact.result); }
)*
;


fact returns [SplExpressionNode result] // виклик функції, або зміних або чисел
:
(
    IDEN                                        { SplExpressionNode assignmentName = factory.createStringLiteral($IDEN, false); }
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
    ex=expr
    e=')'                                       { $result = factory.createParenExpression($ex.result, $s.getStartIndex(), $e.getStopIndex() - $s.getStartIndex() + 1); }
)
;


// описує параметри для виклику функцій
member_expression [SplExpressionNode r, SplExpressionNode assignmentReceiver, SplExpressionNode assignmentName] returns [SplExpressionNode result]
:                                               { SplExpressionNode receiver = r;
                                                  SplExpressionNode nestedAssignmentName = null; }
(
    '('                                         { List<SplExpressionNode> parameters = new ArrayList<>();
                                                  if (receiver == null) {
                                                      receiver = factory.createRead(assignmentName);
                                                  } }
    (
        expr                                    { parameters.add($expr.result); }
        (
            ','
            expr                                { parameters.add($expr.result); }
        )*
    )?
    e=')'
                                                { $result = factory.createCall(receiver, parameters, $e); }
|
    '='
    expr                                        { if (assignmentName == null) {
                                                      SemErr($expr.start, "invalid assignment target");
                                                } else if (assignmentReceiver == null) {
                                                      $result = factory.createAssignment(assignmentName, $expr.result);
                                                } else {
                                                      $result = factory.createWriteProperty(assignmentReceiver, assignmentName, $expr.result);
                                                } }
)
(
    member_expression[$result, receiver, nestedAssignmentName] { $result = $member_expression.result; }
)?
;

// lexer

WS : [ \t\r\n\u000C]+ -> skip; // пропускаєм таби, пробіли, новий рядок
COMMENT : '/*' .*? '*/' -> skip; // пропускаєм багаторядкові коментарі
LINE_COMMENT : '//' ~[\r\n]* -> skip; // пропускаєм однорядковий коментар

fragment LETTER : [A-Z] | [a-z] | '_' | '$';
fragment NON_ZERO_DIGIT : [1-9];
fragment DIGIT : [0-9];
fragment TAB : '\t';
fragment STRING_CHAR : ~('"' | '\\' | '\r' | '\n');

IDEN : LETTER (LETTER | DIGIT)*;
STRING_LITERAL : '"' STRING_CHAR* '"';
NUMERIC_LITERAL : '0' | NON_ZERO_DIGIT DIGIT*;
