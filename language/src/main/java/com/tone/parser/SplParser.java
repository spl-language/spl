// Generated from /home/george/Projects/spl/language/src/main/java/com/tone/parser/Spl.g4 by ANTLR 4.7
package com.tone.parser;

// DO NOT MODIFY - generated from Tone.g4 using "mx create-tone-parser"

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import com.tone.SplLanguage;
import com.tone.nodes.ToneExpressionNode;
import com.tone.nodes.ToneStatementNode;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SplParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, WS = 31, COMMENT = 32,
            LINE_COMMENT = 33, IDENTIFIER = 34, STRING_LITERAL = 35, NUMERIC_LITERAL = 36;
    public static final int
            RULE_spl = 0, RULE_def = 1, RULE_block = 2, RULE_statement = 3, RULE_while_statement = 4,
            RULE_if_statement = 5, RULE_return_statement = 6, RULE_expression = 7,
            RULE_logic_term = 8, RULE_logic_factor = 9, RULE_arithmetic = 10, RULE_term = 11,
            RULE_factor = 12, RULE_member_expression = 13;
    public static final String[] ruleNames = {
            "spl", "def", "block", "statement", "while_statement", "if_statement",
            "return_statement", "expression", "logic_term", "logic_factor", "arithmetic",
            "term", "factor", "member_expression"
    };

    private static final String[] _LITERAL_NAMES = {
            null, "'('", "','", "')'", "'begin'", "';'", "'end'", "'debugger'", "'print'",
            "'read'", "'while'", "'if'", "'then'", "'return'", "'||'", "'&&'", "'<'",
            "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'",
            "'='", "'.'", "'['", "']'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, "WS", "COMMENT", "LINE_COMMENT",
            "IDENTIFIER", "STRING_LITERAL", "NUMERIC_LITERAL"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "Spl.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }


    private ToneNodeFactory factory;
    private Source source;

    private static final class BailoutErrorListener extends BaseErrorListener {
        private final Source source;

        BailoutErrorListener(Source source) {
            this.source = source;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            String location = "-- line " + line + " col " + (charPositionInLine + 1) + ": ";
            throw new ToneParseError(source, line, charPositionInLine + 1, offendingSymbol == null ? 1 : ((Token) offendingSymbol).getText().length(), String.format("Error(s) parsing script:%n" + location + msg));
        }
    }

    public void SemErr(Token token, String message) {
        int col = token.getCharPositionInLine() + 1;
        String location = "-- line " + token.getLine() + " col " + col + ": ";
        throw new ToneParseError(source, token.getLine(), col, token.getText().length(), String.format("Error(s) parsing script:%n" + location + message));
    }

    public static Map<String, RootCallTarget> parseSpl(SplLanguage language, Source source) {
        SplLexer lexer = new SplLexer(CharStreams.fromString(source.getCharacters().toString()));
        SplParser parser = new SplParser(new CommonTokenStream(lexer));
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        BailoutErrorListener listener = new BailoutErrorListener(source);
        lexer.addErrorListener(listener);
        parser.addErrorListener(listener);
        parser.factory = new ToneNodeFactory(language, source);
        parser.source = source;
        parser.spl();
        return parser.factory.getAllFunctions();
    }

    public SplParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class SplContext extends ParserRuleContext {
        public List<DefContext> def() {
            return getRuleContexts(DefContext.class);
        }

        public DefContext def(int i) {
            return getRuleContext(DefContext.class, i);
        }

        public TerminalNode EOF() {
            return getToken(SplParser.EOF, 0);
        }

        public SplContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_spl;
        }
    }

    public final SplContext spl() throws RecognitionException {
        SplContext _localctx = new SplContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_spl);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(28);
                def();
                setState(32);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == IDENTIFIER) {
                    {
                        {
                            setState(29);
                            def();
                        }
                    }
                    setState(34);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(35);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class DefContext extends ParserRuleContext {
        public Token IDENTIFIER;
        public Token s;
        public BlockContext body;

        public List<TerminalNode> IDENTIFIER() {
            return getTokens(SplParser.IDENTIFIER);
        }

        public TerminalNode IDENTIFIER(int i) {
            return getToken(SplParser.IDENTIFIER, i);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public DefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_def;
        }
    }

    public final DefContext def() throws RecognitionException {
        DefContext _localctx = new DefContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_def);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(37);
                ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                setState(38);
                ((DefContext) _localctx).s = match(T__0);
                factory.startFunction(((DefContext) _localctx).IDENTIFIER, ((DefContext) _localctx).s);
                setState(50);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == IDENTIFIER) {
                    {
                        setState(40);
                        ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        factory.addFormalParameter(((DefContext) _localctx).IDENTIFIER);
                        setState(47);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__1) {
                            {
                                {
                                    setState(42);
                                    match(T__1);
                                    setState(43);
                                    ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                                    factory.addFormalParameter(((DefContext) _localctx).IDENTIFIER);
                                }
                            }
                            setState(49);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(52);
                match(T__2);
                setState(53);
                ((DefContext) _localctx).body = block(false);
                factory.finishFunction(((DefContext) _localctx).body.result);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class BlockContext extends ParserRuleContext {
        public boolean inLoop;
        public ToneStatementNode result;
        public Token s;
        public StatementContext statement;
        public Token e;

        public List<StatementContext> statement() {
            return getRuleContexts(StatementContext.class);
        }

        public StatementContext statement(int i) {
            return getRuleContext(StatementContext.class, i);
        }

        public BlockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public BlockContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
            super(parent, invokingState);
            this.inLoop = inLoop;
        }

        @Override
        public int getRuleIndex() {
            return RULE_block;
        }
    }

    public final BlockContext block(boolean inLoop) throws RecognitionException {
        BlockContext _localctx = new BlockContext(_ctx, getState(), inLoop);
        enterRule(_localctx, 4, RULE_block);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                factory.startBlock();
                List<ToneStatementNode> body = new ArrayList<>();
                setState(57);
                ((BlockContext) _localctx).s = match(T__3);
                setState(64);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(58);
                                ((BlockContext) _localctx).statement = statement(inLoop);
                                setState(59);
                                match(T__4);
                                body.add(((BlockContext) _localctx).statement.result);
                            }
                        }
                    }
                    setState(66);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
                }
                setState(67);
                ((BlockContext) _localctx).statement = statement(inLoop);
                body.add(((BlockContext) _localctx).statement.result);
                setState(69);
                ((BlockContext) _localctx).e = match(T__5);
                ((BlockContext) _localctx).result = factory.finishBlock(body, ((BlockContext) _localctx).s.getStartIndex(), ((BlockContext) _localctx).e.getStopIndex() - ((BlockContext) _localctx).s.getStartIndex() + 1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class StatementContext extends ParserRuleContext {
        public boolean inLoop;
        public ToneStatementNode result;
        public While_statementContext while_statement;
        public If_statementContext if_statement;
        public Return_statementContext return_statement;
        public ExpressionContext expression;
        public Token d;
        public Token id;
        public Token IDENTIFIER;

        public While_statementContext while_statement() {
            return getRuleContext(While_statementContext.class, 0);
        }

        public If_statementContext if_statement() {
            return getRuleContext(If_statementContext.class, 0);
        }

        public Return_statementContext return_statement() {
            return getRuleContext(Return_statementContext.class, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(SplParser.IDENTIFIER, 0);
        }

        public StatementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StatementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
            super(parent, invokingState);
            this.inLoop = inLoop;
        }

        @Override
        public int getRuleIndex() {
            return RULE_statement;
        }
    }

    public final StatementContext statement(boolean inLoop) throws RecognitionException {
        StatementContext _localctx = new StatementContext(_ctx, getState(), inLoop);
        enterRule(_localctx, 6, RULE_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(93);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__9: {
                        setState(72);
                        ((StatementContext) _localctx).while_statement = while_statement();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).while_statement.result;
                    }
                    break;
                    case T__10: {
                        setState(75);
                        ((StatementContext) _localctx).if_statement = if_statement(inLoop);
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).if_statement.result;
                    }
                    break;
                    case T__12: {
                        setState(78);
                        ((StatementContext) _localctx).return_statement = return_statement();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).return_statement.result;
                    }
                    break;
                    case T__0:
                    case IDENTIFIER:
                    case STRING_LITERAL:
                    case NUMERIC_LITERAL: {
                        setState(81);
                        ((StatementContext) _localctx).expression = expression();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).expression.result;
                    }
                    break;
                    case T__6: {
                        setState(84);
                        ((StatementContext) _localctx).d = match(T__6);
                        ((StatementContext) _localctx).result = factory.createDebugger(((StatementContext) _localctx).d);
                    }
                    break;
                    case T__7: {
                        setState(86);
                        ((StatementContext) _localctx).id = match(T__7);
                        setState(87);
                        ((StatementContext) _localctx).expression = expression();
                        ((StatementContext) _localctx).result = factory.createPrint(((StatementContext) _localctx).id, ((StatementContext) _localctx).expression.result);
                    }
                    break;
                    case T__8: {
                        setState(90);
                        ((StatementContext) _localctx).id = match(T__8);
                        setState(91);
                        ((StatementContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        ((StatementContext) _localctx).result = factory.createRead(((StatementContext) _localctx).id, ((StatementContext) _localctx).IDENTIFIER);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class While_statementContext extends ParserRuleContext {
        public ToneStatementNode result;
        public Token w;
        public ExpressionContext condition;
        public BlockContext body;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public While_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_while_statement;
        }
    }

    public final While_statementContext while_statement() throws RecognitionException {
        While_statementContext _localctx = new While_statementContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_while_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(95);
                ((While_statementContext) _localctx).w = match(T__9);
                setState(96);
                match(T__0);
                setState(97);
                ((While_statementContext) _localctx).condition = expression();
                setState(98);
                match(T__2);
                setState(99);
                ((While_statementContext) _localctx).body = block(true);
                ((While_statementContext) _localctx).result = factory.createWhile(((While_statementContext) _localctx).w, ((While_statementContext) _localctx).condition.result, ((While_statementContext) _localctx).body.result);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class If_statementContext extends ParserRuleContext {
        public boolean inLoop;
        public ToneStatementNode result;
        public Token i;
        public ExpressionContext condition;
        public BlockContext then;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public If_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public If_statementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
            super(parent, invokingState);
            this.inLoop = inLoop;
        }

        @Override
        public int getRuleIndex() {
            return RULE_if_statement;
        }
    }

    public final If_statementContext if_statement(boolean inLoop) throws RecognitionException {
        If_statementContext _localctx = new If_statementContext(_ctx, getState(), inLoop);
        enterRule(_localctx, 10, RULE_if_statement);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(102);
                ((If_statementContext) _localctx).i = match(T__10);
                setState(103);
                ((If_statementContext) _localctx).condition = expression();
                setState(104);
                match(T__11);
                setState(105);
                ((If_statementContext) _localctx).then = block(inLoop);
                ToneStatementNode elsePart = null;
                setState(107);
                match(T__5);
                ((If_statementContext) _localctx).result = factory.createIf(((If_statementContext) _localctx).i, ((If_statementContext) _localctx).condition.result, ((If_statementContext) _localctx).then.result);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Return_statementContext extends ParserRuleContext {
        public ToneStatementNode result;
        public Token r;
        public ExpressionContext expression;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public Return_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_return_statement;
        }
    }

    public final Return_statementContext return_statement() throws RecognitionException {
        Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_return_statement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(110);
                ((Return_statementContext) _localctx).r = match(T__12);
                ToneExpressionNode value = null;
                setState(115);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        setState(112);
                        ((Return_statementContext) _localctx).expression = expression();
                        value = ((Return_statementContext) _localctx).expression.result;
                    }
                }

                ((Return_statementContext) _localctx).result = factory.createReturn(((Return_statementContext) _localctx).r, value);
                setState(118);
                match(T__4);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ExpressionContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public Logic_termContext logic_term;
        public Token op;

        public List<Logic_termContext> logic_term() {
            return getRuleContexts(Logic_termContext.class);
        }

        public Logic_termContext logic_term(int i) {
            return getRuleContext(Logic_termContext.class, i);
        }

        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }
    }

    public final ExpressionContext expression() throws RecognitionException {
        ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_expression);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(120);
                ((ExpressionContext) _localctx).logic_term = logic_term();
                ((ExpressionContext) _localctx).result = ((ExpressionContext) _localctx).logic_term.result;
                setState(128);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(122);
                                ((ExpressionContext) _localctx).op = match(T__13);
                                setState(123);
                                ((ExpressionContext) _localctx).logic_term = logic_term();
                                ((ExpressionContext) _localctx).result = factory.createBinary(((ExpressionContext) _localctx).op, _localctx.result, ((ExpressionContext) _localctx).logic_term.result);
                            }
                        }
                    }
                    setState(130);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Logic_termContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public Logic_factorContext logic_factor;
        public Token op;

        public List<Logic_factorContext> logic_factor() {
            return getRuleContexts(Logic_factorContext.class);
        }

        public Logic_factorContext logic_factor(int i) {
            return getRuleContext(Logic_factorContext.class, i);
        }

        public Logic_termContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logic_term;
        }
    }

    public final Logic_termContext logic_term() throws RecognitionException {
        Logic_termContext _localctx = new Logic_termContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_logic_term);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(131);
                ((Logic_termContext) _localctx).logic_factor = logic_factor();
                ((Logic_termContext) _localctx).result = ((Logic_termContext) _localctx).logic_factor.result;
                setState(139);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(133);
                                ((Logic_termContext) _localctx).op = match(T__14);
                                setState(134);
                                ((Logic_termContext) _localctx).logic_factor = logic_factor();
                                ((Logic_termContext) _localctx).result = factory.createBinary(((Logic_termContext) _localctx).op, _localctx.result, ((Logic_termContext) _localctx).logic_factor.result);
                            }
                        }
                    }
                    setState(141);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Logic_factorContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public ArithmeticContext arithmetic;
        public Token op;

        public List<ArithmeticContext> arithmetic() {
            return getRuleContexts(ArithmeticContext.class);
        }

        public ArithmeticContext arithmetic(int i) {
            return getRuleContext(ArithmeticContext.class, i);
        }

        public Logic_factorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_logic_factor;
        }
    }

    public final Logic_factorContext logic_factor() throws RecognitionException {
        Logic_factorContext _localctx = new Logic_factorContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_logic_factor);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(142);
                ((Logic_factorContext) _localctx).arithmetic = arithmetic();
                ((Logic_factorContext) _localctx).result = ((Logic_factorContext) _localctx).arithmetic.result;
                setState(148);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 8, _ctx)) {
                    case 1: {
                        setState(144);
                        ((Logic_factorContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20))) != 0))) {
                            ((Logic_factorContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(145);
                        ((Logic_factorContext) _localctx).arithmetic = arithmetic();
                        ((Logic_factorContext) _localctx).result = factory.createBinary(((Logic_factorContext) _localctx).op, _localctx.result, ((Logic_factorContext) _localctx).arithmetic.result);
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ArithmeticContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public TermContext term;
        public Token op;

        public List<TermContext> term() {
            return getRuleContexts(TermContext.class);
        }

        public TermContext term(int i) {
            return getRuleContext(TermContext.class, i);
        }

        public ArithmeticContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_arithmetic;
        }
    }

    public final ArithmeticContext arithmetic() throws RecognitionException {
        ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_arithmetic);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(150);
                ((ArithmeticContext) _localctx).term = term();
                ((ArithmeticContext) _localctx).result = ((ArithmeticContext) _localctx).term.result;
                setState(158);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(152);
                                ((ArithmeticContext) _localctx).op = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == T__21 || _la == T__22)) {
                                    ((ArithmeticContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(153);
                                ((ArithmeticContext) _localctx).term = term();
                                ((ArithmeticContext) _localctx).result = factory.createBinary(((ArithmeticContext) _localctx).op, _localctx.result, ((ArithmeticContext) _localctx).term.result);
                            }
                        }
                    }
                    setState(160);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class TermContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public FactorContext factor;
        public Token op;

        public List<FactorContext> factor() {
            return getRuleContexts(FactorContext.class);
        }

        public FactorContext factor(int i) {
            return getRuleContext(FactorContext.class, i);
        }

        public TermContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_term;
        }
    }

    public final TermContext term() throws RecognitionException {
        TermContext _localctx = new TermContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_term);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(161);
                ((TermContext) _localctx).factor = factor();
                ((TermContext) _localctx).result = ((TermContext) _localctx).factor.result;
                setState(169);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(163);
                                ((TermContext) _localctx).op = _input.LT(1);
                                _la = _input.LA(1);
                                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__23) | (1L << T__24) | (1L << T__25))) != 0))) {
                                    ((TermContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(164);
                                ((TermContext) _localctx).factor = factor();
                                ((TermContext) _localctx).result = factory.createBinary(((TermContext) _localctx).op, _localctx.result, ((TermContext) _localctx).factor.result);
                            }
                        }
                    }
                    setState(171);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class FactorContext extends ParserRuleContext {
        public ToneExpressionNode result;
        public Token IDENTIFIER;
        public Member_expressionContext member_expression;
        public Token STRING_LITERAL;
        public Token NUMERIC_LITERAL;
        public Token s;
        public ExpressionContext expr;
        public Token e;

        public TerminalNode IDENTIFIER() {
            return getToken(SplParser.IDENTIFIER, 0);
        }

        public TerminalNode STRING_LITERAL() {
            return getToken(SplParser.STRING_LITERAL, 0);
        }

        public TerminalNode NUMERIC_LITERAL() {
            return getToken(SplParser.NUMERIC_LITERAL, 0);
        }

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public Member_expressionContext member_expression() {
            return getRuleContext(Member_expressionContext.class, 0);
        }

        public FactorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_factor;
        }
    }

    public final FactorContext factor() throws RecognitionException {
        FactorContext _localctx = new FactorContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_factor);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(189);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case IDENTIFIER: {
                        setState(172);
                        ((FactorContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        ToneExpressionNode assignmentName = factory.createStringLiteral(((FactorContext) _localctx).IDENTIFIER, false);
                        setState(178);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
                            case 1: {
                                setState(174);
                                ((FactorContext) _localctx).member_expression = member_expression(null, null, assignmentName);
                                ((FactorContext) _localctx).result = ((FactorContext) _localctx).member_expression.result;
                            }
                            break;
                            case 2: {
                                ((FactorContext) _localctx).result = factory.createRead(assignmentName);
                            }
                            break;
                        }
                    }
                    break;
                    case STRING_LITERAL: {
                        setState(180);
                        ((FactorContext) _localctx).STRING_LITERAL = match(STRING_LITERAL);
                        ((FactorContext) _localctx).result = factory.createStringLiteral(((FactorContext) _localctx).STRING_LITERAL, true);
                    }
                    break;
                    case NUMERIC_LITERAL: {
                        setState(182);
                        ((FactorContext) _localctx).NUMERIC_LITERAL = match(NUMERIC_LITERAL);
                        ((FactorContext) _localctx).result = factory.createNumericLiteral(((FactorContext) _localctx).NUMERIC_LITERAL);
                    }
                    break;
                    case T__0: {
                        setState(184);
                        ((FactorContext) _localctx).s = match(T__0);
                        setState(185);
                        ((FactorContext) _localctx).expr = expression();
                        setState(186);
                        ((FactorContext) _localctx).e = match(T__2);
                        ((FactorContext) _localctx).result = factory.createParenExpression(((FactorContext) _localctx).expr.result, ((FactorContext) _localctx).s.getStartIndex(), ((FactorContext) _localctx).e.getStopIndex() - ((FactorContext) _localctx).s.getStartIndex() + 1);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Member_expressionContext extends ParserRuleContext {
        public ToneExpressionNode r;
        public ToneExpressionNode assignmentReceiver;
        public ToneExpressionNode assignmentName;
        public ToneExpressionNode result;
        public ExpressionContext expression;
        public Token e;
        public Token IDENTIFIER;
        public Member_expressionContext member_expression;

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public TerminalNode IDENTIFIER() {
            return getToken(SplParser.IDENTIFIER, 0);
        }

        public Member_expressionContext member_expression() {
            return getRuleContext(Member_expressionContext.class, 0);
        }

        public Member_expressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public Member_expressionContext(ParserRuleContext parent, int invokingState, ToneExpressionNode r, ToneExpressionNode assignmentReceiver, ToneExpressionNode assignmentName) {
            super(parent, invokingState);
            this.r = r;
            this.assignmentReceiver = assignmentReceiver;
            this.assignmentName = assignmentName;
        }

        @Override
        public int getRuleIndex() {
            return RULE_member_expression;
        }
    }

    public final Member_expressionContext member_expression(ToneExpressionNode r, ToneExpressionNode assignmentReceiver, ToneExpressionNode assignmentName) throws RecognitionException {
        Member_expressionContext _localctx = new Member_expressionContext(_ctx, getState(), r, assignmentReceiver, assignmentName);
        enterRule(_localctx, 26, RULE_member_expression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                ToneExpressionNode receiver = r;
                ToneExpressionNode nestedAssignmentName = null;
                setState(223);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__0: {
                        setState(192);
                        match(T__0);
                        List<ToneExpressionNode> parameters = new ArrayList<>();
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(205);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                            {
                                setState(194);
                                ((Member_expressionContext) _localctx).expression = expression();
                                parameters.add(((Member_expressionContext) _localctx).expression.result);
                                setState(202);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__1) {
                                    {
                                        {
                                            setState(196);
                                            match(T__1);
                                            setState(197);
                                            ((Member_expressionContext) _localctx).expression = expression();
                                            parameters.add(((Member_expressionContext) _localctx).expression.result);
                                        }
                                    }
                                    setState(204);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                            }
                        }

                        setState(207);
                        ((Member_expressionContext) _localctx).e = match(T__2);
                        ((Member_expressionContext) _localctx).result = factory.createCall(receiver, parameters, ((Member_expressionContext) _localctx).e);
                    }
                    break;
                    case T__26: {
                        setState(209);
                        match(T__26);
                        setState(210);
                        ((Member_expressionContext) _localctx).expression = expression();
                        if (assignmentName == null) {
                            SemErr((((Member_expressionContext) _localctx).expression != null ? (((Member_expressionContext) _localctx).expression.start) : null), "invalid assignment target");
                        } else if (assignmentReceiver == null) {
                            ((Member_expressionContext) _localctx).result = factory.createAssignment(assignmentName, ((Member_expressionContext) _localctx).expression.result);
                        } else {
                            ((Member_expressionContext) _localctx).result = factory.createWriteProperty(assignmentReceiver, assignmentName, ((Member_expressionContext) _localctx).expression.result);
                        }
                    }
                    break;
                    case T__27: {
                        setState(213);
                        match(T__27);
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(215);
                        ((Member_expressionContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        nestedAssignmentName = factory.createStringLiteral(((Member_expressionContext) _localctx).IDENTIFIER, false);
                        ((Member_expressionContext) _localctx).result = factory.createReadProperty(receiver, nestedAssignmentName);
                    }
                    break;
                    case T__28: {
                        setState(217);
                        match(T__28);
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(219);
                        ((Member_expressionContext) _localctx).expression = expression();
                        nestedAssignmentName = ((Member_expressionContext) _localctx).expression.result;
                        ((Member_expressionContext) _localctx).result = factory.createReadProperty(receiver, nestedAssignmentName);
                        setState(221);
                        match(T__29);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(228);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
                    case 1: {
                        setState(225);
                        ((Member_expressionContext) _localctx).member_expression = member_expression(_localctx.result, receiver, nestedAssignmentName);
                        ((Member_expressionContext) _localctx).result = ((Member_expressionContext) _localctx).member_expression.result;
                    }
                    break;
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&\u00e9\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\7\2!\n\2\f\2\16\2$\13" +
                    "\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\60\n\3\f\3\16\3\63\13\3" +
                    "\5\3\65\n\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4A\n\4\f\4\16\4" +
                    "D\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5" +
                    "\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5`\n\5\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bv\n\b" +
                    "\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u0081\n\t\f\t\16\t\u0084\13\t" +
                    "\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u008c\n\n\f\n\16\n\u008f\13\n\3\13\3\13\3" +
                    "\13\3\13\3\13\3\13\5\13\u0097\n\13\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u009f\n" +
                    "\f\f\f\16\f\u00a2\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00aa\n\r\f\r\16\r" +
                    "\u00ad\13\r\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00b5\n\16\3\16\3\16\3" +
                    "\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00c0\n\16\3\17\3\17\3\17\3\17" +
                    "\3\17\3\17\3\17\3\17\3\17\7\17\u00cb\n\17\f\17\16\17\u00ce\13\17\5\17" +
                    "\u00d0\n\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17" +
                    "\3\17\3\17\3\17\3\17\5\17\u00e2\n\17\3\17\3\17\3\17\5\17\u00e7\n\17\3" +
                    "\17\2\2\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\5\3\2\22\27\3\2\30\31" +
                    "\3\2\32\34\2\u00f4\2\36\3\2\2\2\4\'\3\2\2\2\6:\3\2\2\2\b_\3\2\2\2\na\3" +
                    "\2\2\2\fh\3\2\2\2\16p\3\2\2\2\20z\3\2\2\2\22\u0085\3\2\2\2\24\u0090\3" +
                    "\2\2\2\26\u0098\3\2\2\2\30\u00a3\3\2\2\2\32\u00bf\3\2\2\2\34\u00c1\3\2" +
                    "\2\2\36\"\5\4\3\2\37!\5\4\3\2 \37\3\2\2\2!$\3\2\2\2\" \3\2\2\2\"#\3\2" +
                    "\2\2#%\3\2\2\2$\"\3\2\2\2%&\7\2\2\3&\3\3\2\2\2\'(\7$\2\2()\7\3\2\2)\64" +
                    "\b\3\1\2*+\7$\2\2+\61\b\3\1\2,-\7\4\2\2-.\7$\2\2.\60\b\3\1\2/,\3\2\2\2" +
                    "\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\64" +
                    "*\3\2\2\2\64\65\3\2\2\2\65\66\3\2\2\2\66\67\7\5\2\2\678\5\6\4\289\b\3" +
                    "\1\29\5\3\2\2\2:;\b\4\1\2;B\7\6\2\2<=\5\b\5\2=>\7\7\2\2>?\b\4\1\2?A\3" +
                    "\2\2\2@<\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DB\3\2\2\2EF\5" +
                    "\b\5\2FG\b\4\1\2GH\7\b\2\2HI\b\4\1\2I\7\3\2\2\2JK\5\n\6\2KL\b\5\1\2L`" +
                    "\3\2\2\2MN\5\f\7\2NO\b\5\1\2O`\3\2\2\2PQ\5\16\b\2QR\b\5\1\2R`\3\2\2\2" +
                    "ST\5\20\t\2TU\b\5\1\2U`\3\2\2\2VW\7\t\2\2W`\b\5\1\2XY\7\n\2\2YZ\5\20\t" +
                    "\2Z[\b\5\1\2[`\3\2\2\2\\]\7\13\2\2]^\7$\2\2^`\b\5\1\2_J\3\2\2\2_M\3\2" +
                    "\2\2_P\3\2\2\2_S\3\2\2\2_V\3\2\2\2_X\3\2\2\2_\\\3\2\2\2`\t\3\2\2\2ab\7" +
                    "\f\2\2bc\7\3\2\2cd\5\20\t\2de\7\5\2\2ef\5\6\4\2fg\b\6\1\2g\13\3\2\2\2" +
                    "hi\7\r\2\2ij\5\20\t\2jk\7\16\2\2kl\5\6\4\2lm\b\7\1\2mn\7\b\2\2no\b\7\1" +
                    "\2o\r\3\2\2\2pq\7\17\2\2qu\b\b\1\2rs\5\20\t\2st\b\b\1\2tv\3\2\2\2ur\3" +
                    "\2\2\2uv\3\2\2\2vw\3\2\2\2wx\b\b\1\2xy\7\7\2\2y\17\3\2\2\2z{\5\22\n\2" +
                    "{\u0082\b\t\1\2|}\7\20\2\2}~\5\22\n\2~\177\b\t\1\2\177\u0081\3\2\2\2\u0080" +
                    "|\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2" +
                    "\u0083\21\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\5\24\13\2\u0086\u008d" +
                    "\b\n\1\2\u0087\u0088\7\21\2\2\u0088\u0089\5\24\13\2\u0089\u008a\b\n\1" +
                    "\2\u008a\u008c\3\2\2\2\u008b\u0087\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b" +
                    "\3\2\2\2\u008d\u008e\3\2\2\2\u008e\23\3\2\2\2\u008f\u008d\3\2\2\2\u0090" +
                    "\u0091\5\26\f\2\u0091\u0096\b\13\1\2\u0092\u0093\t\2\2\2\u0093\u0094\5" +
                    "\26\f\2\u0094\u0095\b\13\1\2\u0095\u0097\3\2\2\2\u0096\u0092\3\2\2\2\u0096" +
                    "\u0097\3\2\2\2\u0097\25\3\2\2\2\u0098\u0099\5\30\r\2\u0099\u00a0\b\f\1" +
                    "\2\u009a\u009b\t\3\2\2\u009b\u009c\5\30\r\2\u009c\u009d\b\f\1\2\u009d" +
                    "\u009f\3\2\2\2\u009e\u009a\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2" +
                    "\2\2\u00a0\u00a1\3\2\2\2\u00a1\27\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a4" +
                    "\5\32\16\2\u00a4\u00ab\b\r\1\2\u00a5\u00a6\t\4\2\2\u00a6\u00a7\5\32\16" +
                    "\2\u00a7\u00a8\b\r\1\2\u00a8\u00aa\3\2\2\2\u00a9\u00a5\3\2\2\2\u00aa\u00ad" +
                    "\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\31\3\2\2\2\u00ad" +
                    "\u00ab\3\2\2\2\u00ae\u00af\7$\2\2\u00af\u00b4\b\16\1\2\u00b0\u00b1\5\34" +
                    "\17\2\u00b1\u00b2\b\16\1\2\u00b2\u00b5\3\2\2\2\u00b3\u00b5\b\16\1\2\u00b4" +
                    "\u00b0\3\2\2\2\u00b4\u00b3\3\2\2\2\u00b5\u00c0\3\2\2\2\u00b6\u00b7\7%" +
                    "\2\2\u00b7\u00c0\b\16\1\2\u00b8\u00b9\7&\2\2\u00b9\u00c0\b\16\1\2\u00ba" +
                    "\u00bb\7\3\2\2\u00bb\u00bc\5\20\t\2\u00bc\u00bd\7\5\2\2\u00bd\u00be\b" +
                    "\16\1\2\u00be\u00c0\3\2\2\2\u00bf\u00ae\3\2\2\2\u00bf\u00b6\3\2\2\2\u00bf" +
                    "\u00b8\3\2\2\2\u00bf\u00ba\3\2\2\2\u00c0\33\3\2\2\2\u00c1\u00e1\b\17\1" +
                    "\2\u00c2\u00c3\7\3\2\2\u00c3\u00cf\b\17\1\2\u00c4\u00c5\5\20\t\2\u00c5" +
                    "\u00cc\b\17\1\2\u00c6\u00c7\7\4\2\2\u00c7\u00c8\5\20\t\2\u00c8\u00c9\b" +
                    "\17\1\2\u00c9\u00cb\3\2\2\2\u00ca\u00c6\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc" +
                    "\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2" +
                    "\2\2\u00cf\u00c4\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1" +
                    "\u00d2\7\5\2\2\u00d2\u00e2\b\17\1\2\u00d3\u00d4\7\35\2\2\u00d4\u00d5\5" +
                    "\20\t\2\u00d5\u00d6\b\17\1\2\u00d6\u00e2\3\2\2\2\u00d7\u00d8\7\36\2\2" +
                    "\u00d8\u00d9\b\17\1\2\u00d9\u00da\7$\2\2\u00da\u00e2\b\17\1\2\u00db\u00dc" +
                    "\7\37\2\2\u00dc\u00dd\b\17\1\2\u00dd\u00de\5\20\t\2\u00de\u00df\b\17\1" +
                    "\2\u00df\u00e0\7 \2\2\u00e0\u00e2\3\2\2\2\u00e1\u00c2\3\2\2\2\u00e1\u00d3" +
                    "\3\2\2\2\u00e1\u00d7\3\2\2\2\u00e1\u00db\3\2\2\2\u00e2\u00e6\3\2\2\2\u00e3" +
                    "\u00e4\5\34\17\2\u00e4\u00e5\b\17\1\2\u00e5\u00e7\3\2\2\2\u00e6\u00e3" +
                    "\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\35\3\2\2\2\23\"\61\64B_u\u0082\u008d" +
                    "\u0096\u00a0\u00ab\u00b4\u00bf\u00cc\u00cf\u00e1\u00e6";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}