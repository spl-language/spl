// Generated from Tone.g4 by ANTLR 4.7.1

package com.tone.parser;
// DO NOT MODIFY - generated from Tone.g4 using "mx create-tone-parser"

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.source.Source;
import com.tone.ToneLanguage;
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
public class ToneParser extends Parser {
    static {
        RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31,
            WS = 32, COMMENT = 33, LINE_COMMENT = 34, IDENTIFIER = 35, STRING_LITERAL = 36,
            NUMERIC_LITERAL = 37;
    public static final int
            RULE_tonelanguage = 0, RULE_def = 1, RULE_block = 2, RULE_statement = 3,
            RULE_while_statement = 4, RULE_for_statement = 5, RULE_if_statement = 6,
            RULE_return_statement = 7, RULE_expression = 8, RULE_logic_term = 9, RULE_logic_factor = 10,
            RULE_arithmetic = 11, RULE_term = 12, RULE_factor = 13, RULE_member_expression = 14;
    public static final String[] ruleNames = {
            "tonelanguage", "def", "block", "statement", "while_statement", "for_statement",
            "if_statement", "return_statement", "expression", "logic_term", "logic_factor",
            "arithmetic", "term", "factor", "member_expression"
    };

    private static final String[] _LITERAL_NAMES = {
            null, "'('", "','", "')'", "'{'", "'}'", "'break'", "';'", "'continue'",
            "'debugger'", "'while'", "'for'", "'if'", "'else'", "'return'", "'||'",
            "'&&'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", "'*'",
            "'/'", "'%'", "'='", "'.'", "'['", "']'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, "WS", "COMMENT", "LINE_COMMENT",
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
        return "Tone.g4";
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

    public static Map<String, RootCallTarget> parseTone(ToneLanguage language, Source source) {
        ToneLexer lexer = new ToneLexer(CharStreams.fromString(source.getCharacters().toString()));
        ToneParser parser = new ToneParser(new CommonTokenStream(lexer));
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        BailoutErrorListener listener = new BailoutErrorListener(source);
        lexer.addErrorListener(listener);
        parser.addErrorListener(listener);
        parser.factory = new ToneNodeFactory(language, source);
        parser.source = source;
        parser.tonelanguage();
        return parser.factory.getAllFunctions();
    }

    public ToneParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class TonelanguageContext extends ParserRuleContext {
        public List<DefContext> def() {
            return getRuleContexts(DefContext.class);
        }

        public DefContext def(int i) {
            return getRuleContext(DefContext.class, i);
        }

        public TerminalNode EOF() {
            return getToken(ToneParser.EOF, 0);
        }

        public TonelanguageContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tonelanguage;
        }
    }

    public final TonelanguageContext tonelanguage() throws RecognitionException {
        TonelanguageContext _localctx = new TonelanguageContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_tonelanguage);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(30);
                def();
                setState(34);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == IDENTIFIER) {
                    {
                        {
                            setState(31);
                            def();
                        }
                    }
                    setState(36);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(37);
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
            return getTokens(ToneParser.IDENTIFIER);
        }

        public TerminalNode IDENTIFIER(int i) {
            return getToken(ToneParser.IDENTIFIER, i);
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
                setState(39);
                ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                setState(40);
                ((DefContext) _localctx).s = match(T__0);
                factory.startFunction(((DefContext) _localctx).IDENTIFIER, ((DefContext) _localctx).s);
                setState(52);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == IDENTIFIER) {
                    {
                        setState(42);
                        ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        factory.addFormalParameter(((DefContext) _localctx).IDENTIFIER);
                        setState(49);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__1) {
                            {
                                {
                                    setState(44);
                                    match(T__1);
                                    setState(45);
                                    ((DefContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                                    factory.addFormalParameter(((DefContext) _localctx).IDENTIFIER);
                                }
                            }
                            setState(51);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(54);
                match(T__2);
                setState(55);
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
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                factory.startBlock();
                List<ToneStatementNode> body = new ArrayList<>();
                setState(59);
                ((BlockContext) _localctx).s = match(T__3);
                setState(65);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__13) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        {
                            setState(60);
                            ((BlockContext) _localctx).statement = statement(inLoop);
                            body.add(((BlockContext) _localctx).statement.result);
                        }
                    }
                    setState(67);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(68);
                ((BlockContext) _localctx).e = match(T__4);
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
        public For_statementContext for_statement;
        public Token b;
        public Token c;
        public If_statementContext if_statement;
        public Return_statementContext return_statement;
        public ExpressionContext expression;
        public Token d;

        public While_statementContext while_statement() {
            return getRuleContext(While_statementContext.class, 0);
        }

        public For_statementContext for_statement() {
            return getRuleContext(For_statementContext.class, 0);
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
                setState(96);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__9: {
                        setState(71);
                        ((StatementContext) _localctx).while_statement = while_statement();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).while_statement.result;
                    }
                    break;
                    case T__10: {
                        setState(74);
                        ((StatementContext) _localctx).for_statement = for_statement();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).for_statement.result;
                    }
                    break;
                    case T__5: {
                        setState(77);
                        ((StatementContext) _localctx).b = match(T__5);
                        if (inLoop) {
                            ((StatementContext) _localctx).result = factory.createBreak(((StatementContext) _localctx).b);
                        } else {
                            SemErr(((StatementContext) _localctx).b, "break used outside of loop");
                        }
                        setState(79);
                        match(T__6);
                    }
                    break;
                    case T__7: {
                        setState(80);
                        ((StatementContext) _localctx).c = match(T__7);
                        if (inLoop) {
                            ((StatementContext) _localctx).result = factory.createContinue(((StatementContext) _localctx).c);
                        } else {
                            SemErr(((StatementContext) _localctx).c, "continue used outside of loop");
                        }
                        setState(82);
                        match(T__6);
                    }
                    break;
                    case T__11: {
                        setState(83);
                        ((StatementContext) _localctx).if_statement = if_statement(inLoop);
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).if_statement.result;
                    }
                    break;
                    case T__13: {
                        setState(86);
                        ((StatementContext) _localctx).return_statement = return_statement();
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).return_statement.result;
                    }
                    break;
                    case T__0:
                    case IDENTIFIER:
                    case STRING_LITERAL:
                    case NUMERIC_LITERAL: {
                        setState(89);
                        ((StatementContext) _localctx).expression = expression();
                        setState(90);
                        match(T__6);
                        ((StatementContext) _localctx).result = ((StatementContext) _localctx).expression.result;
                    }
                    break;
                    case T__8: {
                        setState(93);
                        ((StatementContext) _localctx).d = match(T__8);
                        ((StatementContext) _localctx).result = factory.createDebugger(((StatementContext) _localctx).d);
                        setState(95);
                        match(T__6);
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
                setState(98);
                ((While_statementContext) _localctx).w = match(T__9);
                setState(99);
                match(T__0);
                setState(100);
                ((While_statementContext) _localctx).condition = expression();
                setState(101);
                match(T__2);
                setState(102);
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

    public static class For_statementContext extends ParserRuleContext {
        public ToneStatementNode result;
        public Token f;
        public ExpressionContext init;
        public ExpressionContext condition;
        public ExpressionContext increment;
        public BlockContext body;

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public For_statementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_for_statement;
        }
    }

    public final For_statementContext for_statement() throws RecognitionException {
        For_statementContext _localctx = new For_statementContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_for_statement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(105);
                ((For_statementContext) _localctx).f = match(T__10);
                setState(106);
                match(T__0);
                ToneExpressionNode initPart = null;
                ToneExpressionNode conditionPart = null;
                ToneExpressionNode incrementPart = null;
                setState(113);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        setState(110);
                        ((For_statementContext) _localctx).init = expression();
                        initPart = ((For_statementContext) _localctx).init.result;
                    }
                }

                setState(115);
                match(T__6);
                setState(119);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        setState(116);
                        ((For_statementContext) _localctx).condition = expression();
                        conditionPart = ((For_statementContext) _localctx).condition.result;
                    }
                }

                setState(121);
                match(T__6);
                setState(125);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        setState(122);
                        ((For_statementContext) _localctx).increment = expression();
                        incrementPart = ((For_statementContext) _localctx).increment.result;
                    }
                }

                setState(127);
                match(T__2);
                setState(128);
                ((For_statementContext) _localctx).body = block(true);
                ((For_statementContext) _localctx).result = factory.createFor(((For_statementContext) _localctx).f, initPart, conditionPart, incrementPart, ((For_statementContext) _localctx).body.result);
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
        public BlockContext block;

        public ExpressionContext expression() {
            return getRuleContext(ExpressionContext.class, 0);
        }

        public List<BlockContext> block() {
            return getRuleContexts(BlockContext.class);
        }

        public BlockContext block(int i) {
            return getRuleContext(BlockContext.class, i);
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
        enterRule(_localctx, 12, RULE_if_statement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(131);
                ((If_statementContext) _localctx).i = match(T__11);
                setState(132);
                match(T__0);
                setState(133);
                ((If_statementContext) _localctx).condition = expression();
                setState(134);
                match(T__2);
                setState(135);
                ((If_statementContext) _localctx).then = ((If_statementContext) _localctx).block = block(inLoop);
                ToneStatementNode elsePart = null;
                setState(141);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(137);
                        match(T__12);
                        setState(138);
                        ((If_statementContext) _localctx).block = block(inLoop);
                        elsePart = ((If_statementContext) _localctx).block.result;
                    }
                }

                ((If_statementContext) _localctx).result = factory.createIf(((If_statementContext) _localctx).i, ((If_statementContext) _localctx).condition.result, ((If_statementContext) _localctx).then.result, elsePart);
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
        enterRule(_localctx, 14, RULE_return_statement);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(145);
                ((Return_statementContext) _localctx).r = match(T__13);
                ToneExpressionNode value = null;
                setState(150);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                    {
                        setState(147);
                        ((Return_statementContext) _localctx).expression = expression();
                        value = ((Return_statementContext) _localctx).expression.result;
                    }
                }

                ((Return_statementContext) _localctx).result = factory.createReturn(((Return_statementContext) _localctx).r, value);
                setState(153);
                match(T__6);
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
        enterRule(_localctx, 16, RULE_expression);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(155);
                ((ExpressionContext) _localctx).logic_term = logic_term();
                ((ExpressionContext) _localctx).result = ((ExpressionContext) _localctx).logic_term.result;
                setState(163);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(157);
                                ((ExpressionContext) _localctx).op = match(T__14);
                                setState(158);
                                ((ExpressionContext) _localctx).logic_term = logic_term();
                                ((ExpressionContext) _localctx).result = factory.createBinary(((ExpressionContext) _localctx).op, _localctx.result, ((ExpressionContext) _localctx).logic_term.result);
                            }
                        }
                    }
                    setState(165);
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
        enterRule(_localctx, 18, RULE_logic_term);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(166);
                ((Logic_termContext) _localctx).logic_factor = logic_factor();
                ((Logic_termContext) _localctx).result = ((Logic_termContext) _localctx).logic_factor.result;
                setState(174);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(168);
                                ((Logic_termContext) _localctx).op = match(T__15);
                                setState(169);
                                ((Logic_termContext) _localctx).logic_factor = logic_factor();
                                ((Logic_termContext) _localctx).result = factory.createBinary(((Logic_termContext) _localctx).op, _localctx.result, ((Logic_termContext) _localctx).logic_factor.result);
                            }
                        }
                    }
                    setState(176);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
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
        enterRule(_localctx, 20, RULE_logic_factor);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(177);
                ((Logic_factorContext) _localctx).arithmetic = arithmetic();
                ((Logic_factorContext) _localctx).result = ((Logic_factorContext) _localctx).arithmetic.result;
                setState(183);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
                    case 1: {
                        setState(179);
                        ((Logic_factorContext) _localctx).op = _input.LT(1);
                        _la = _input.LA(1);
                        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21))) != 0))) {
                            ((Logic_factorContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                        } else {
                            if (_input.LA(1) == Token.EOF) matchedEOF = true;
                            _errHandler.reportMatch(this);
                            consume();
                        }
                        setState(180);
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
        enterRule(_localctx, 22, RULE_arithmetic);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(185);
                ((ArithmeticContext) _localctx).term = term();
                ((ArithmeticContext) _localctx).result = ((ArithmeticContext) _localctx).term.result;
                setState(193);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(187);
                                ((ArithmeticContext) _localctx).op = _input.LT(1);
                                _la = _input.LA(1);
                                if (!(_la == T__22 || _la == T__23)) {
                                    ((ArithmeticContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(188);
                                ((ArithmeticContext) _localctx).term = term();
                                ((ArithmeticContext) _localctx).result = factory.createBinary(((ArithmeticContext) _localctx).op, _localctx.result, ((ArithmeticContext) _localctx).term.result);
                            }
                        }
                    }
                    setState(195);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 13, _ctx);
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
        enterRule(_localctx, 24, RULE_term);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(196);
                ((TermContext) _localctx).factor = factor();
                ((TermContext) _localctx).result = ((TermContext) _localctx).factor.result;
                setState(204);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(198);
                                ((TermContext) _localctx).op = _input.LT(1);
                                _la = _input.LA(1);
                                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26))) != 0))) {
                                    ((TermContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                } else {
                                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                    _errHandler.reportMatch(this);
                                    consume();
                                }
                                setState(199);
                                ((TermContext) _localctx).factor = factor();
                                ((TermContext) _localctx).result = factory.createBinary(((TermContext) _localctx).op, _localctx.result, ((TermContext) _localctx).factor.result);
                            }
                        }
                    }
                    setState(206);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
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
            return getToken(ToneParser.IDENTIFIER, 0);
        }

        public TerminalNode STRING_LITERAL() {
            return getToken(ToneParser.STRING_LITERAL, 0);
        }

        public TerminalNode NUMERIC_LITERAL() {
            return getToken(ToneParser.NUMERIC_LITERAL, 0);
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
        enterRule(_localctx, 26, RULE_factor);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(224);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case IDENTIFIER: {
                        setState(207);
                        ((FactorContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        ToneExpressionNode assignmentName = factory.createStringLiteral(((FactorContext) _localctx).IDENTIFIER, false);
                        setState(213);
                        _errHandler.sync(this);
                        switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
                            case 1: {
                                setState(209);
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
                        setState(215);
                        ((FactorContext) _localctx).STRING_LITERAL = match(STRING_LITERAL);
                        ((FactorContext) _localctx).result = factory.createStringLiteral(((FactorContext) _localctx).STRING_LITERAL, true);
                    }
                    break;
                    case NUMERIC_LITERAL: {
                        setState(217);
                        ((FactorContext) _localctx).NUMERIC_LITERAL = match(NUMERIC_LITERAL);
                        ((FactorContext) _localctx).result = factory.createNumericLiteral(((FactorContext) _localctx).NUMERIC_LITERAL);
                    }
                    break;
                    case T__0: {
                        setState(219);
                        ((FactorContext) _localctx).s = match(T__0);
                        setState(220);
                        ((FactorContext) _localctx).expr = expression();
                        setState(221);
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
            return getToken(ToneParser.IDENTIFIER, 0);
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
        enterRule(_localctx, 28, RULE_member_expression);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                ToneExpressionNode receiver = r;
                ToneExpressionNode nestedAssignmentName = null;
                setState(258);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__0: {
                        setState(227);
                        match(T__0);
                        List<ToneExpressionNode> parameters = new ArrayList<>();
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(240);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
                            {
                                setState(229);
                                ((Member_expressionContext) _localctx).expression = expression();
                                parameters.add(((Member_expressionContext) _localctx).expression.result);
                                setState(237);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__1) {
                                    {
                                        {
                                            setState(231);
                                            match(T__1);
                                            setState(232);
                                            ((Member_expressionContext) _localctx).expression = expression();
                                            parameters.add(((Member_expressionContext) _localctx).expression.result);
                                        }
                                    }
                                    setState(239);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                            }
                        }

                        setState(242);
                        ((Member_expressionContext) _localctx).e = match(T__2);
                        ((Member_expressionContext) _localctx).result = factory.createCall(receiver, parameters, ((Member_expressionContext) _localctx).e);
                    }
                    break;
                    case T__27: {
                        setState(244);
                        match(T__27);
                        setState(245);
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
                    case T__28: {
                        setState(248);
                        match(T__28);
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(250);
                        ((Member_expressionContext) _localctx).IDENTIFIER = match(IDENTIFIER);
                        nestedAssignmentName = factory.createStringLiteral(((Member_expressionContext) _localctx).IDENTIFIER, false);
                        ((Member_expressionContext) _localctx).result = factory.createReadProperty(receiver, nestedAssignmentName);
                    }
                    break;
                    case T__29: {
                        setState(252);
                        match(T__29);
                        if (receiver == null) {
                            receiver = factory.createRead(assignmentName);
                        }
                        setState(254);
                        ((Member_expressionContext) _localctx).expression = expression();
                        nestedAssignmentName = ((Member_expressionContext) _localctx).expression.result;
                        ((Member_expressionContext) _localctx).result = factory.createReadProperty(receiver, nestedAssignmentName);
                        setState(256);
                        match(T__30);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(263);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
                    case 1: {
                        setState(260);
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
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\u010c\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\7\2#\n\2\f\2" +
                    "\16\2&\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\62\n\3\f\3\16" +
                    "\3\65\13\3\5\3\67\n\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4B\n\4\f\4" +
                    "\16\4E\13\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3" +
                    "\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5c\n\5\3\6\3" +
                    "\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7t\n\7\3\7\3" +
                    "\7\3\7\3\7\5\7z\n\7\3\7\3\7\3\7\3\7\5\7\u0080\n\7\3\7\3\7\3\7\3\7\3\b" +
                    "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0090\n\b\3\b\3\b\3\t\3\t\3\t" +
                    "\3\t\3\t\5\t\u0099\n\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00a4\n" +
                    "\n\f\n\16\n\u00a7\13\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00af\n\13\f" +
                    "\13\16\13\u00b2\13\13\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00ba\n\f\3\r\3\r\3" +
                    "\r\3\r\3\r\3\r\7\r\u00c2\n\r\f\r\16\r\u00c5\13\r\3\16\3\16\3\16\3\16\3" +
                    "\16\3\16\7\16\u00cd\n\16\f\16\16\16\u00d0\13\16\3\17\3\17\3\17\3\17\3" +
                    "\17\3\17\5\17\u00d8\n\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17" +
                    "\5\17\u00e3\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00ee" +
                    "\n\20\f\20\16\20\u00f1\13\20\5\20\u00f3\n\20\3\20\3\20\3\20\3\20\3\20" +
                    "\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0105\n\20" +
                    "\3\20\3\20\3\20\5\20\u010a\n\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26" +
                    "\30\32\34\36\2\5\3\2\23\30\3\2\31\32\3\2\33\35\2\u011b\2 \3\2\2\2\4)\3" +
                    "\2\2\2\6<\3\2\2\2\bb\3\2\2\2\nd\3\2\2\2\fk\3\2\2\2\16\u0085\3\2\2\2\20" +
                    "\u0093\3\2\2\2\22\u009d\3\2\2\2\24\u00a8\3\2\2\2\26\u00b3\3\2\2\2\30\u00bb" +
                    "\3\2\2\2\32\u00c6\3\2\2\2\34\u00e2\3\2\2\2\36\u00e4\3\2\2\2 $\5\4\3\2" +
                    "!#\5\4\3\2\"!\3\2\2\2#&\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%\'\3\2\2\2&$\3\2" +
                    "\2\2\'(\7\2\2\3(\3\3\2\2\2)*\7%\2\2*+\7\3\2\2+\66\b\3\1\2,-\7%\2\2-\63" +
                    "\b\3\1\2./\7\4\2\2/\60\7%\2\2\60\62\b\3\1\2\61.\3\2\2\2\62\65\3\2\2\2" +
                    "\63\61\3\2\2\2\63\64\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\66,\3\2\2\2\66" +
                    "\67\3\2\2\2\678\3\2\2\289\7\5\2\29:\5\6\4\2:;\b\3\1\2;\5\3\2\2\2<=\b\4" +
                    "\1\2=C\7\6\2\2>?\5\b\5\2?@\b\4\1\2@B\3\2\2\2A>\3\2\2\2BE\3\2\2\2CA\3\2" +
                    "\2\2CD\3\2\2\2DF\3\2\2\2EC\3\2\2\2FG\7\7\2\2GH\b\4\1\2H\7\3\2\2\2IJ\5" +
                    "\n\6\2JK\b\5\1\2Kc\3\2\2\2LM\5\f\7\2MN\b\5\1\2Nc\3\2\2\2OP\7\b\2\2PQ\b" +
                    "\5\1\2Qc\7\t\2\2RS\7\n\2\2ST\b\5\1\2Tc\7\t\2\2UV\5\16\b\2VW\b\5\1\2Wc" +
                    "\3\2\2\2XY\5\20\t\2YZ\b\5\1\2Zc\3\2\2\2[\\\5\22\n\2\\]\7\t\2\2]^\b\5\1" +
                    "\2^c\3\2\2\2_`\7\13\2\2`a\b\5\1\2ac\7\t\2\2bI\3\2\2\2bL\3\2\2\2bO\3\2" +
                    "\2\2bR\3\2\2\2bU\3\2\2\2bX\3\2\2\2b[\3\2\2\2b_\3\2\2\2c\t\3\2\2\2de\7" +
                    "\f\2\2ef\7\3\2\2fg\5\22\n\2gh\7\5\2\2hi\5\6\4\2ij\b\6\1\2j\13\3\2\2\2" +
                    "kl\7\r\2\2lm\7\3\2\2mn\b\7\1\2no\b\7\1\2os\b\7\1\2pq\5\22\n\2qr\b\7\1" +
                    "\2rt\3\2\2\2sp\3\2\2\2st\3\2\2\2tu\3\2\2\2uy\7\t\2\2vw\5\22\n\2wx\b\7" +
                    "\1\2xz\3\2\2\2yv\3\2\2\2yz\3\2\2\2z{\3\2\2\2{\177\7\t\2\2|}\5\22\n\2}" +
                    "~\b\7\1\2~\u0080\3\2\2\2\177|\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3" +
                    "\2\2\2\u0081\u0082\7\5\2\2\u0082\u0083\5\6\4\2\u0083\u0084\b\7\1\2\u0084" +
                    "\r\3\2\2\2\u0085\u0086\7\16\2\2\u0086\u0087\7\3\2\2\u0087\u0088\5\22\n" +
                    "\2\u0088\u0089\7\5\2\2\u0089\u008a\5\6\4\2\u008a\u008f\b\b\1\2\u008b\u008c" +
                    "\7\17\2\2\u008c\u008d\5\6\4\2\u008d\u008e\b\b\1\2\u008e\u0090\3\2\2\2" +
                    "\u008f\u008b\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092" +
                    "\b\b\1\2\u0092\17\3\2\2\2\u0093\u0094\7\20\2\2\u0094\u0098\b\t\1\2\u0095" +
                    "\u0096\5\22\n\2\u0096\u0097\b\t\1\2\u0097\u0099\3\2\2\2\u0098\u0095\3" +
                    "\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\b\t\1\2\u009b" +
                    "\u009c\7\t\2\2\u009c\21\3\2\2\2\u009d\u009e\5\24\13\2\u009e\u00a5\b\n" +
                    "\1\2\u009f\u00a0\7\21\2\2\u00a0\u00a1\5\24\13\2\u00a1\u00a2\b\n\1\2\u00a2" +
                    "\u00a4\3\2\2\2\u00a3\u009f\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5\u00a3\3\2" +
                    "\2\2\u00a5\u00a6\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8\u00a9" +
                    "\5\26\f\2\u00a9\u00b0\b\13\1\2\u00aa\u00ab\7\22\2\2\u00ab\u00ac\5\26\f" +
                    "\2\u00ac\u00ad\b\13\1\2\u00ad\u00af\3\2\2\2\u00ae\u00aa\3\2\2\2\u00af" +
                    "\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\25\3\2\2" +
                    "\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4\5\30\r\2\u00b4\u00b9\b\f\1\2\u00b5" +
                    "\u00b6\t\2\2\2\u00b6\u00b7\5\30\r\2\u00b7\u00b8\b\f\1\2\u00b8\u00ba\3" +
                    "\2\2\2\u00b9\u00b5\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\27\3\2\2\2\u00bb" +
                    "\u00bc\5\32\16\2\u00bc\u00c3\b\r\1\2\u00bd\u00be\t\3\2\2\u00be\u00bf\5" +
                    "\32\16\2\u00bf\u00c0\b\r\1\2\u00c0\u00c2\3\2\2\2\u00c1\u00bd\3\2\2\2\u00c2" +
                    "\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\31\3\2\2" +
                    "\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\5\34\17\2\u00c7\u00ce\b\16\1\2\u00c8" +
                    "\u00c9\t\4\2\2\u00c9\u00ca\5\34\17\2\u00ca\u00cb\b\16\1\2\u00cb\u00cd" +
                    "\3\2\2\2\u00cc\u00c8\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce" +
                    "\u00cf\3\2\2\2\u00cf\33\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d2\7%\2\2" +
                    "\u00d2\u00d7\b\17\1\2\u00d3\u00d4\5\36\20\2\u00d4\u00d5\b\17\1\2\u00d5" +
                    "\u00d8\3\2\2\2\u00d6\u00d8\b\17\1\2\u00d7\u00d3\3\2\2\2\u00d7\u00d6\3" +
                    "\2\2\2\u00d8\u00e3\3\2\2\2\u00d9\u00da\7&\2\2\u00da\u00e3\b\17\1\2\u00db" +
                    "\u00dc\7\'\2\2\u00dc\u00e3\b\17\1\2\u00dd\u00de\7\3\2\2\u00de\u00df\5" +
                    "\22\n\2\u00df\u00e0\7\5\2\2\u00e0\u00e1\b\17\1\2\u00e1\u00e3\3\2\2\2\u00e2" +
                    "\u00d1\3\2\2\2\u00e2\u00d9\3\2\2\2\u00e2\u00db\3\2\2\2\u00e2\u00dd\3\2" +
                    "\2\2\u00e3\35\3\2\2\2\u00e4\u0104\b\20\1\2\u00e5\u00e6\7\3\2\2\u00e6\u00f2" +
                    "\b\20\1\2\u00e7\u00e8\5\22\n\2\u00e8\u00ef\b\20\1\2\u00e9\u00ea\7\4\2" +
                    "\2\u00ea\u00eb\5\22\n\2\u00eb\u00ec\b\20\1\2\u00ec\u00ee\3\2\2\2\u00ed" +
                    "\u00e9\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2" +
                    "\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00e7\3\2\2\2\u00f2" +
                    "\u00f3\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\7\5\2\2\u00f5\u0105\b\20" +
                    "\1\2\u00f6\u00f7\7\36\2\2\u00f7\u00f8\5\22\n\2\u00f8\u00f9\b\20\1\2\u00f9" +
                    "\u0105\3\2\2\2\u00fa\u00fb\7\37\2\2\u00fb\u00fc\b\20\1\2\u00fc\u00fd\7" +
                    "%\2\2\u00fd\u0105\b\20\1\2\u00fe\u00ff\7 \2\2\u00ff\u0100\b\20\1\2\u0100" +
                    "\u0101\5\22\n\2\u0101\u0102\b\20\1\2\u0102\u0103\7!\2\2\u0103\u0105\3" +
                    "\2\2\2\u0104\u00e5\3\2\2\2\u0104\u00f6\3\2\2\2\u0104\u00fa\3\2\2\2\u0104" +
                    "\u00fe\3\2\2\2\u0105\u0109\3\2\2\2\u0106\u0107\5\36\20\2\u0107\u0108\b" +
                    "\20\1\2\u0108\u010a\3\2\2\2\u0109\u0106\3\2\2\2\u0109\u010a\3\2\2\2\u010a" +
                    "\37\3\2\2\2\27$\63\66Cbsy\177\u008f\u0098\u00a5\u00b0\u00b9\u00c3\u00ce" +
                    "\u00d7\u00e2\u00ef\u00f2\u0104\u0109";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}