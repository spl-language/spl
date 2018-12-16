// Generated from /home/george/Projects/spl/language/src/main/java/com/spl/parser/Spl.g4 by ANTLR 4.7
package com.spl.parser;

// DO NOT MODIFY - generated from Spl.g4 using "mx create-tone-parser"

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.RootCallTarget;
import com.spl.SplLanguage;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplExpressionNode;
import com.spl.nodes.SplStatementNode;
import com.spl.parser.ToneParseError;
import com.spl.parser.SplNodeFactory;
import com.spl.parser.SplLexer;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SplParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, WS=31, COMMENT=32, 
		LINE_COMMENT=33, IDENTIFIER=34, STRING_LITERAL=35, NUMERIC_LITERAL=36;
	public static final int
		RULE_spl = 0, RULE_dfunc = 1, RULE_block = 2, RULE_statement = 3, RULE_while_statement = 4, 
		RULE_while_block = 5, RULE_if_statement = 6, RULE_if_block = 7, RULE_return_statement = 8, 
		RULE_expression = 9, RULE_logic_term = 10, RULE_logic_factor = 11, RULE_arithmetic = 12, 
		RULE_term = 13, RULE_factor = 14, RULE_member_expression = 15;
	public static final String[] ruleNames = {
		"spl", "dfunc", "block", "statement", "while_statement", "while_block", 
		"if_statement", "if_block", "return_statement", "expression", "logic_term", 
		"logic_factor", "arithmetic", "term", "factor", "member_expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "','", "')'", "'begin'", "';'", "'end'", "'debugger'", "'int'", 
		"'const'", "'='", "'print'", "'read'", "'while'", "'do'", "'if'", "'then'", 
		"'return'", "'||'", "'&&'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", 
		"'+'", "'-'", "'*'", "'/'", "'%'"
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
	public String getGrammarFileName() { return "Spl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class SplContext extends ParserRuleContext {
		public List<DfuncContext> dfunc() {
			return getRuleContexts(DfuncContext.class);
		}
		public DfuncContext dfunc(int i) {
			return getRuleContext(DfuncContext.class,i);
		}
		public TerminalNode EOF() { return getToken(SplParser.EOF, 0); }
		public SplContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spl; }
	}

	public final SplContext spl() throws RecognitionException {
		SplContext _localctx = new SplContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_spl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			dfunc();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(33);
				dfunc();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DfuncContext extends ParserRuleContext {
		public Token IDENTIFIER;
		public Token s;
		public BlockContext body;
		public List<TerminalNode> IDENTIFIER() { return getTokens(SplParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SplParser.IDENTIFIER, i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public DfuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dfunc; }
	}

	public final DfuncContext dfunc() throws RecognitionException {
		DfuncContext _localctx = new DfuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dfunc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			((DfuncContext)_localctx).IDENTIFIER = match(IDENTIFIER);
			setState(42);
			((DfuncContext)_localctx).s = match(T__0);
			 factory.startFunction(((DfuncContext)_localctx).IDENTIFIER, ((DfuncContext)_localctx).s); 
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(44);
				((DfuncContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 factory.addFormalParameter(((DfuncContext)_localctx).IDENTIFIER); 
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(46);
					match(T__1);
					setState(47);
					((DfuncContext)_localctx).IDENTIFIER = match(IDENTIFIER);
					 factory.addFormalParameter(((DfuncContext)_localctx).IDENTIFIER); 
					}
					}
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(56);
			match(T__2);
			setState(57);
			((DfuncContext)_localctx).body = block(false);
			 factory.finishFunction(((DfuncContext)_localctx).body.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public boolean inLoop;
		public SplStatementNode result;
		public Token s;
		public StatementContext statement;
		public Token e;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public BlockContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block(boolean inLoop) throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 4, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(61);
			((BlockContext)_localctx).s = match(T__3);
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(62);
					((BlockContext)_localctx).statement = statement(inLoop);
					setState(63);
					match(T__4);
					 body.add(((BlockContext)_localctx).statement.result); 
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(71);
			((BlockContext)_localctx).statement = statement(inLoop);
			 body.add(((BlockContext)_localctx).statement.result); 
			setState(73);
			((BlockContext)_localctx).e = match(T__5);
			 ((BlockContext)_localctx).result =  factory.finishBlock(body, ((BlockContext)_localctx).s.getStartIndex(), ((BlockContext)_localctx).e.getStopIndex() - ((BlockContext)_localctx).s.getStartIndex() + 1); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public boolean inLoop;
		public SplStatementNode result;
		public While_statementContext while_statement;
		public If_statementContext if_statement;
		public Return_statementContext return_statement;
		public ExpressionContext expression;
		public Token d;
		public Token id;
		public Token IDENTIFIER;
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public Return_statementContext return_statement() {
			return getRuleContext(Return_statementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(SplParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(SplParser.IDENTIFIER, i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public StatementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement(boolean inLoop) throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 6, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
				{
				setState(76);
				((StatementContext)_localctx).while_statement = while_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).while_statement.result; 
				}
				break;
			case T__14:
				{
				setState(79);
				((StatementContext)_localctx).if_statement = if_statement(inLoop);
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).if_statement.result; 
				}
				break;
			case T__16:
				{
				setState(82);
				((StatementContext)_localctx).return_statement = return_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).return_statement.result; 
				}
				break;
			case T__0:
			case T__25:
			case T__26:
			case IDENTIFIER:
			case STRING_LITERAL:
			case NUMERIC_LITERAL:
				{
				setState(85);
				((StatementContext)_localctx).expression = expression();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).expression.result; 
				}
				break;
			case T__6:
				{
				setState(88);
				((StatementContext)_localctx).d = match(T__6);
				 ((StatementContext)_localctx).result =  factory.createDebugger(((StatementContext)_localctx).d); 
				}
				break;
			case T__7:
				{
				setState(90);
				((StatementContext)_localctx).id = match(T__7);
				 List<Token> variables = new ArrayList<>(); 
				setState(92);
				((StatementContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 variables.add(((StatementContext)_localctx).IDENTIFIER); 
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(94);
					match(T__1);
					setState(95);
					((StatementContext)_localctx).IDENTIFIER = match(IDENTIFIER);
					 variables.add(((StatementContext)_localctx).IDENTIFIER); 
					}
					}
					setState(101);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				 ((StatementContext)_localctx).result =  factory.declareIntVariables(((StatementContext)_localctx).id, variables); 
				}
				break;
			case T__8:
				{
				setState(103);
				((StatementContext)_localctx).id = match(T__8);
				 List<TokenAndValue> tokenValues = new ArrayList<>(); 
				setState(105);
				((StatementContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 TokenAndValue current = new TokenAndValue(((StatementContext)_localctx).IDENTIFIER); 
				setState(107);
				match(T__9);
				setState(108);
				((StatementContext)_localctx).expression = expression();
				 current.setSplExpressionNode(((StatementContext)_localctx).expression.result);
				                                                  tokenValues.add(current); 
				{
				setState(110);
				match(T__1);
				setState(111);
				((StatementContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 current = new TokenAndValue(((StatementContext)_localctx).IDENTIFIER); 
				setState(113);
				match(T__9);
				setState(114);
				((StatementContext)_localctx).expression = expression();
				 current.setSplExpressionNode(((StatementContext)_localctx).expression.result);
				                                                  tokenValues.add(current); 
				}
				 ((StatementContext)_localctx).result =  factory.declareConstVariable(((StatementContext)_localctx).id, tokenValues); 
				}
				break;
			case T__10:
				{
				setState(119);
				((StatementContext)_localctx).id = match(T__10);
				setState(120);
				((StatementContext)_localctx).expression = expression();
				 ((StatementContext)_localctx).result =  factory.createPrint(((StatementContext)_localctx).id, ((StatementContext)_localctx).expression.result); 
				}
				break;
			case T__11:
				{
				setState(123);
				((StatementContext)_localctx).id = match(T__11);
				setState(124);
				((StatementContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 ((StatementContext)_localctx).result =  factory.createRead(((StatementContext)_localctx).id, ((StatementContext)_localctx).IDENTIFIER); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class While_statementContext extends ParserRuleContext {
		public SplStatementNode result;
		public Token w;
		public ExpressionContext condition;
		public While_blockContext body;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public While_blockContext while_block() {
			return getRuleContext(While_blockContext.class,0);
		}
		public While_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_statement; }
	}

	public final While_statementContext while_statement() throws RecognitionException {
		While_statementContext _localctx = new While_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			((While_statementContext)_localctx).w = match(T__12);
			setState(129);
			((While_statementContext)_localctx).condition = expression();
			setState(130);
			((While_statementContext)_localctx).body = while_block();
			 ((While_statementContext)_localctx).result =  factory.createWhile(((While_statementContext)_localctx).w, ((While_statementContext)_localctx).condition.result, ((While_statementContext)_localctx).body.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class While_blockContext extends ParserRuleContext {
		public SplStatementNode result;
		public Token s;
		public StatementContext statement;
		public Token e;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public While_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_block; }
	}

	public final While_blockContext while_block() throws RecognitionException {
		While_blockContext _localctx = new While_blockContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_while_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(134);
			((While_blockContext)_localctx).s = match(T__13);
			setState(141);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(135);
					((While_blockContext)_localctx).statement = statement(true);
					setState(136);
					match(T__4);
					 body.add(((While_blockContext)_localctx).statement.result); 
					}
					} 
				}
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			setState(144);
			((While_blockContext)_localctx).statement = statement(true);
			 body.add(((While_blockContext)_localctx).statement.result); 
			setState(146);
			((While_blockContext)_localctx).e = match(T__5);
			 ((While_blockContext)_localctx).result =  factory.finishBlock(body, ((While_blockContext)_localctx).s.getStartIndex(), ((While_blockContext)_localctx).e.getStopIndex() - ((While_blockContext)_localctx).s.getStartIndex() + 1); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_statementContext extends ParserRuleContext {
		public boolean inLoop;
		public SplStatementNode result;
		public Token i;
		public ExpressionContext condition;
		public If_blockContext then;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public If_blockContext if_block() {
			return getRuleContext(If_blockContext.class,0);
		}
		public If_statementContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public If_statementContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_if_statement; }
	}

	public final If_statementContext if_statement(boolean inLoop) throws RecognitionException {
		If_statementContext _localctx = new If_statementContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 12, RULE_if_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			((If_statementContext)_localctx).i = match(T__14);
			setState(150);
			((If_statementContext)_localctx).condition = expression();
			setState(151);
			((If_statementContext)_localctx).then = if_block(inLoop);
			 ((If_statementContext)_localctx).result =  factory.createIf(((If_statementContext)_localctx).i, ((If_statementContext)_localctx).condition.result, ((If_statementContext)_localctx).then.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_blockContext extends ParserRuleContext {
		public boolean inLoop;
		public SplStatementNode result;
		public Token s;
		public StatementContext statement;
		public Token e;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_blockContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public If_blockContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_if_block; }
	}

	public final If_blockContext if_block(boolean inLoop) throws RecognitionException {
		If_blockContext _localctx = new If_blockContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 14, RULE_if_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(155);
			((If_blockContext)_localctx).s = match(T__15);
			setState(162);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(156);
					((If_blockContext)_localctx).statement = statement(false);
					setState(157);
					match(T__4);
					 body.add(((If_blockContext)_localctx).statement.result); 
					}
					} 
				}
				setState(164);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(165);
			((If_blockContext)_localctx).statement = statement(false);
			 body.add(((If_blockContext)_localctx).statement.result); 
			setState(167);
			((If_blockContext)_localctx).e = match(T__5);
			 ((If_blockContext)_localctx).result =  factory.finishBlock(body, ((If_blockContext)_localctx).s.getStartIndex(), ((If_blockContext)_localctx).e.getStopIndex() - ((If_blockContext)_localctx).s.getStartIndex() + 1); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Return_statementContext extends ParserRuleContext {
		public SplStatementNode result;
		public Token r;
		public ExpressionContext expression;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_statement; }
	}

	public final Return_statementContext return_statement() throws RecognitionException {
		Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_return_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			((Return_statementContext)_localctx).r = match(T__16);
			 SplExpressionNode value = null; 
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__25) | (1L << T__26) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
				{
				setState(172);
				((Return_statementContext)_localctx).expression = expression();
				 value = ((Return_statementContext)_localctx).expression.result; 
				}
			}

			 ((Return_statementContext)_localctx).result =  factory.createReturn(((Return_statementContext)_localctx).r, value); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public SplExpressionNode result;
		public Logic_termContext logic_term;
		public Token op;
		public List<Logic_termContext> logic_term() {
			return getRuleContexts(Logic_termContext.class);
		}
		public Logic_termContext logic_term(int i) {
			return getRuleContext(Logic_termContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			((ExpressionContext)_localctx).logic_term = logic_term();
			 ((ExpressionContext)_localctx).result =  ((ExpressionContext)_localctx).logic_term.result; 
			setState(187);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(181);
					((ExpressionContext)_localctx).op = match(T__17);
					setState(182);
					((ExpressionContext)_localctx).logic_term = logic_term();
					 ((ExpressionContext)_localctx).result =  factory.createBinary(((ExpressionContext)_localctx).op, _localctx.result, ((ExpressionContext)_localctx).logic_term.result); 
					}
					} 
				}
				setState(189);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logic_termContext extends ParserRuleContext {
		public SplExpressionNode result;
		public Logic_factorContext logic_factor;
		public Token op;
		public List<Logic_factorContext> logic_factor() {
			return getRuleContexts(Logic_factorContext.class);
		}
		public Logic_factorContext logic_factor(int i) {
			return getRuleContext(Logic_factorContext.class,i);
		}
		public Logic_termContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_term; }
	}

	public final Logic_termContext logic_term() throws RecognitionException {
		Logic_termContext _localctx = new Logic_termContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_logic_term);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			((Logic_termContext)_localctx).logic_factor = logic_factor();
			 ((Logic_termContext)_localctx).result =  ((Logic_termContext)_localctx).logic_factor.result; 
			setState(198);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(192);
					((Logic_termContext)_localctx).op = match(T__18);
					setState(193);
					((Logic_termContext)_localctx).logic_factor = logic_factor();
					 ((Logic_termContext)_localctx).result =  factory.createBinary(((Logic_termContext)_localctx).op, _localctx.result, ((Logic_termContext)_localctx).logic_factor.result); 
					}
					} 
				}
				setState(200);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logic_factorContext extends ParserRuleContext {
		public SplExpressionNode result;
		public ArithmeticContext arithmetic;
		public Token op;
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public Logic_factorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_factor; }
	}

	public final Logic_factorContext logic_factor() throws RecognitionException {
		Logic_factorContext _localctx = new Logic_factorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_logic_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			((Logic_factorContext)_localctx).arithmetic = arithmetic();
			 ((Logic_factorContext)_localctx).result =  ((Logic_factorContext)_localctx).arithmetic.result; 
			setState(207);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(203);
				((Logic_factorContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24))) != 0)) ) {
					((Logic_factorContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(204);
				((Logic_factorContext)_localctx).arithmetic = arithmetic();
				 ((Logic_factorContext)_localctx).result =  factory.createBinary(((Logic_factorContext)_localctx).op, _localctx.result, ((Logic_factorContext)_localctx).arithmetic.result); 
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticContext extends ParserRuleContext {
		public SplExpressionNode result;
		public TermContext term;
		public Token op;
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_arithmetic);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			((ArithmeticContext)_localctx).term = term();
			 ((ArithmeticContext)_localctx).result =  ((ArithmeticContext)_localctx).term.result; 
			setState(217);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(211);
					((ArithmeticContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__25 || _la==T__26) ) {
						((ArithmeticContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(212);
					((ArithmeticContext)_localctx).term = term();
					 ((ArithmeticContext)_localctx).result =  factory.createBinary(((ArithmeticContext)_localctx).op, _localctx.result, ((ArithmeticContext)_localctx).term.result); 
					}
					} 
				}
				setState(219);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public SplExpressionNode result;
		public FactorContext factor;
		public Token op;
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_term);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			((TermContext)_localctx).factor = factor();
			 ((TermContext)_localctx).result =  ((TermContext)_localctx).factor.result; 
			setState(228);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(222);
					((TermContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__27) | (1L << T__28) | (1L << T__29))) != 0)) ) {
						((TermContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(223);
					((TermContext)_localctx).factor = factor();
					 ((TermContext)_localctx).result =  factory.createBinary(((TermContext)_localctx).op, _localctx.result, ((TermContext)_localctx).factor.result); 
					}
					} 
				}
				setState(230);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactorContext extends ParserRuleContext {
		public SplExpressionNode result;
		public Token IDENTIFIER;
		public Member_expressionContext member_expression;
		public Token STRING_LITERAL;
		public Token sign;
		public Token NUMERIC_LITERAL;
		public Token s;
		public ExpressionContext expr;
		public Token e;
		public TerminalNode IDENTIFIER() { return getToken(SplParser.IDENTIFIER, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(SplParser.STRING_LITERAL, 0); }
		public TerminalNode NUMERIC_LITERAL() { return getToken(SplParser.NUMERIC_LITERAL, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Member_expressionContext member_expression() {
			return getRuleContext(Member_expressionContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(231);
				((FactorContext)_localctx).IDENTIFIER = match(IDENTIFIER);
				 SplExpressionNode assignmentName = factory.createStringLiteral(((FactorContext)_localctx).IDENTIFIER, false); 
				setState(237);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(233);
					((FactorContext)_localctx).member_expression = member_expression(null, null, assignmentName);
					 ((FactorContext)_localctx).result =  ((FactorContext)_localctx).member_expression.result; 
					}
					break;
				case 2:
					{
					 ((FactorContext)_localctx).result =  factory.createRead(assignmentName); 
					}
					break;
				}
				}
				break;
			case STRING_LITERAL:
				{
				setState(239);
				((FactorContext)_localctx).STRING_LITERAL = match(STRING_LITERAL);
				 ((FactorContext)_localctx).result =  factory.createStringLiteral(((FactorContext)_localctx).STRING_LITERAL, true); 
				}
				break;
			case T__25:
			case T__26:
			case NUMERIC_LITERAL:
				{
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__25 || _la==T__26) {
					{
					setState(241);
					((FactorContext)_localctx).sign = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__25 || _la==T__26) ) {
						((FactorContext)_localctx).sign = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(244);
				((FactorContext)_localctx).NUMERIC_LITERAL = match(NUMERIC_LITERAL);
				 ((FactorContext)_localctx).result =  factory.createNumericLiteral(((FactorContext)_localctx).sign, ((FactorContext)_localctx).NUMERIC_LITERAL); 
				}
				break;
			case T__0:
				{
				setState(246);
				((FactorContext)_localctx).s = match(T__0);
				setState(247);
				((FactorContext)_localctx).expr = expression();
				setState(248);
				((FactorContext)_localctx).e = match(T__2);
				 ((FactorContext)_localctx).result =  factory.createParenExpression(((FactorContext)_localctx).expr.result, ((FactorContext)_localctx).s.getStartIndex(), ((FactorContext)_localctx).e.getStopIndex() - ((FactorContext)_localctx).s.getStartIndex() + 1); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Member_expressionContext extends ParserRuleContext {
		public SplExpressionNode r;
		public SplExpressionNode assignmentReceiver;
		public SplExpressionNode assignmentName;
		public SplExpressionNode result;
		public ExpressionContext expression;
		public Token e;
		public Member_expressionContext member_expression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Member_expressionContext member_expression() {
			return getRuleContext(Member_expressionContext.class,0);
		}
		public Member_expressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Member_expressionContext(ParserRuleContext parent, int invokingState, SplExpressionNode r, SplExpressionNode assignmentReceiver, SplExpressionNode assignmentName) {
			super(parent, invokingState);
			this.r = r;
			this.assignmentReceiver = assignmentReceiver;
			this.assignmentName = assignmentName;
		}
		@Override public int getRuleIndex() { return RULE_member_expression; }
	}

	public final Member_expressionContext member_expression(SplExpressionNode r,SplExpressionNode assignmentReceiver,SplExpressionNode assignmentName) throws RecognitionException {
		Member_expressionContext _localctx = new Member_expressionContext(_ctx, getState(), r, assignmentReceiver, assignmentName);
		enterRule(_localctx, 30, RULE_member_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 SplExpressionNode receiver = r;
			                                                  SplExpressionNode nestedAssignmentName = null; 
			setState(275);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				{
				setState(254);
				match(T__0);
				 List<SplExpressionNode> parameters = new ArrayList<>();
				                                                  if (receiver == null) {
				                                                      receiver = factory.createRead(assignmentName);
				                                                  } 
				setState(267);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__25) | (1L << T__26) | (1L << IDENTIFIER) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
					{
					setState(256);
					((Member_expressionContext)_localctx).expression = expression();
					 parameters.add(((Member_expressionContext)_localctx).expression.result); 
					setState(264);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(258);
						match(T__1);
						setState(259);
						((Member_expressionContext)_localctx).expression = expression();
						 parameters.add(((Member_expressionContext)_localctx).expression.result); 
						}
						}
						setState(266);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(269);
				((Member_expressionContext)_localctx).e = match(T__2);
				 ((Member_expressionContext)_localctx).result =  factory.createCall(receiver, parameters, ((Member_expressionContext)_localctx).e); 
				}
				break;
			case T__9:
				{
				setState(271);
				match(T__9);
				setState(272);
				((Member_expressionContext)_localctx).expression = expression();
				 if (assignmentName == null) {
				                                                      SemErr((((Member_expressionContext)_localctx).expression!=null?(((Member_expressionContext)_localctx).expression.start):null), "invalid assignment target");
				                                                  } else if (assignmentReceiver == null) {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createAssignment(assignmentName, ((Member_expressionContext)_localctx).expression.result);
				                                                  } else {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createWriteProperty(assignmentReceiver, assignmentName, ((Member_expressionContext)_localctx).expression.result);
				                                                  } 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(277);
				((Member_expressionContext)_localctx).member_expression = member_expression(_localctx.result, receiver, nestedAssignmentName);
				 ((Member_expressionContext)_localctx).result =  ((Member_expressionContext)_localctx).member_expression.result; 
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&\u011d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\7"+
		"\2%\n\2\f\2\16\2(\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\64"+
		"\n\3\f\3\16\3\67\13\3\5\39\n\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\7\4E\n\4\f\4\16\4H\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5d\n\5\f"+
		"\5\16\5g\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0081\n\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u008e\n\7\f\7\16\7\u0091\13\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00a3\n\t"+
		"\f\t\16\t\u00a6\13\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\5\n\u00b2"+
		"\n\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00bc\n\13\f\13\16\13"+
		"\u00bf\13\13\3\f\3\f\3\f\3\f\3\f\3\f\7\f\u00c7\n\f\f\f\16\f\u00ca\13\f"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00d2\n\r\3\16\3\16\3\16\3\16\3\16\3\16\7"+
		"\16\u00da\n\16\f\16\16\16\u00dd\13\16\3\17\3\17\3\17\3\17\3\17\3\17\7"+
		"\17\u00e5\n\17\f\17\16\17\u00e8\13\17\3\20\3\20\3\20\3\20\3\20\3\20\5"+
		"\20\u00f0\n\20\3\20\3\20\3\20\5\20\u00f5\n\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\5\20\u00fe\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\7\21\u0109\n\21\f\21\16\21\u010c\13\21\5\21\u010e\n\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\5\21\u0116\n\21\3\21\3\21\3\21\5\21\u011b\n\21\3\21\2"+
		"\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\5\3\2\26\33\3\2\34\35"+
		"\3\2\36 \2\u012a\2\"\3\2\2\2\4+\3\2\2\2\6>\3\2\2\2\b\u0080\3\2\2\2\n\u0082"+
		"\3\2\2\2\f\u0087\3\2\2\2\16\u0097\3\2\2\2\20\u009c\3\2\2\2\22\u00ac\3"+
		"\2\2\2\24\u00b5\3\2\2\2\26\u00c0\3\2\2\2\30\u00cb\3\2\2\2\32\u00d3\3\2"+
		"\2\2\34\u00de\3\2\2\2\36\u00fd\3\2\2\2 \u00ff\3\2\2\2\"&\5\4\3\2#%\5\4"+
		"\3\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\7"+
		"\2\2\3*\3\3\2\2\2+,\7$\2\2,-\7\3\2\2-8\b\3\1\2./\7$\2\2/\65\b\3\1\2\60"+
		"\61\7\4\2\2\61\62\7$\2\2\62\64\b\3\1\2\63\60\3\2\2\2\64\67\3\2\2\2\65"+
		"\63\3\2\2\2\65\66\3\2\2\2\669\3\2\2\2\67\65\3\2\2\28.\3\2\2\289\3\2\2"+
		"\29:\3\2\2\2:;\7\5\2\2;<\5\6\4\2<=\b\3\1\2=\5\3\2\2\2>?\b\4\1\2?F\7\6"+
		"\2\2@A\5\b\5\2AB\7\7\2\2BC\b\4\1\2CE\3\2\2\2D@\3\2\2\2EH\3\2\2\2FD\3\2"+
		"\2\2FG\3\2\2\2GI\3\2\2\2HF\3\2\2\2IJ\5\b\5\2JK\b\4\1\2KL\7\b\2\2LM\b\4"+
		"\1\2M\7\3\2\2\2NO\5\n\6\2OP\b\5\1\2P\u0081\3\2\2\2QR\5\16\b\2RS\b\5\1"+
		"\2S\u0081\3\2\2\2TU\5\22\n\2UV\b\5\1\2V\u0081\3\2\2\2WX\5\24\13\2XY\b"+
		"\5\1\2Y\u0081\3\2\2\2Z[\7\t\2\2[\u0081\b\5\1\2\\]\7\n\2\2]^\b\5\1\2^_"+
		"\7$\2\2_e\b\5\1\2`a\7\4\2\2ab\7$\2\2bd\b\5\1\2c`\3\2\2\2dg\3\2\2\2ec\3"+
		"\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2h\u0081\b\5\1\2ij\7\13\2\2jk\b\5\1"+
		"\2kl\7$\2\2lm\b\5\1\2mn\7\f\2\2no\5\24\13\2op\b\5\1\2pq\7\4\2\2qr\7$\2"+
		"\2rs\b\5\1\2st\7\f\2\2tu\5\24\13\2uv\b\5\1\2vw\3\2\2\2wx\b\5\1\2x\u0081"+
		"\3\2\2\2yz\7\r\2\2z{\5\24\13\2{|\b\5\1\2|\u0081\3\2\2\2}~\7\16\2\2~\177"+
		"\7$\2\2\177\u0081\b\5\1\2\u0080N\3\2\2\2\u0080Q\3\2\2\2\u0080T\3\2\2\2"+
		"\u0080W\3\2\2\2\u0080Z\3\2\2\2\u0080\\\3\2\2\2\u0080i\3\2\2\2\u0080y\3"+
		"\2\2\2\u0080}\3\2\2\2\u0081\t\3\2\2\2\u0082\u0083\7\17\2\2\u0083\u0084"+
		"\5\24\13\2\u0084\u0085\5\f\7\2\u0085\u0086\b\6\1\2\u0086\13\3\2\2\2\u0087"+
		"\u0088\b\7\1\2\u0088\u008f\7\20\2\2\u0089\u008a\5\b\5\2\u008a\u008b\7"+
		"\7\2\2\u008b\u008c\b\7\1\2\u008c\u008e\3\2\2\2\u008d\u0089\3\2\2\2\u008e"+
		"\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0092\3\2"+
		"\2\2\u0091\u008f\3\2\2\2\u0092\u0093\5\b\5\2\u0093\u0094\b\7\1\2\u0094"+
		"\u0095\7\b\2\2\u0095\u0096\b\7\1\2\u0096\r\3\2\2\2\u0097\u0098\7\21\2"+
		"\2\u0098\u0099\5\24\13\2\u0099\u009a\5\20\t\2\u009a\u009b\b\b\1\2\u009b"+
		"\17\3\2\2\2\u009c\u009d\b\t\1\2\u009d\u00a4\7\22\2\2\u009e\u009f\5\b\5"+
		"\2\u009f\u00a0\7\7\2\2\u00a0\u00a1\b\t\1\2\u00a1\u00a3\3\2\2\2\u00a2\u009e"+
		"\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5"+
		"\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\5\b\5\2\u00a8\u00a9\b\t"+
		"\1\2\u00a9\u00aa\7\b\2\2\u00aa\u00ab\b\t\1\2\u00ab\21\3\2\2\2\u00ac\u00ad"+
		"\7\23\2\2\u00ad\u00b1\b\n\1\2\u00ae\u00af\5\24\13\2\u00af\u00b0\b\n\1"+
		"\2\u00b0\u00b2\3\2\2\2\u00b1\u00ae\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3"+
		"\3\2\2\2\u00b3\u00b4\b\n\1\2\u00b4\23\3\2\2\2\u00b5\u00b6\5\26\f\2\u00b6"+
		"\u00bd\b\13\1\2\u00b7\u00b8\7\24\2\2\u00b8\u00b9\5\26\f\2\u00b9\u00ba"+
		"\b\13\1\2\u00ba\u00bc\3\2\2\2\u00bb\u00b7\3\2\2\2\u00bc\u00bf\3\2\2\2"+
		"\u00bd\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\25\3\2\2\2\u00bf\u00bd"+
		"\3\2\2\2\u00c0\u00c1\5\30\r\2\u00c1\u00c8\b\f\1\2\u00c2\u00c3\7\25\2\2"+
		"\u00c3\u00c4\5\30\r\2\u00c4\u00c5\b\f\1\2\u00c5\u00c7\3\2\2\2\u00c6\u00c2"+
		"\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9"+
		"\27\3\2\2\2\u00ca\u00c8\3\2\2\2\u00cb\u00cc\5\32\16\2\u00cc\u00d1\b\r"+
		"\1\2\u00cd\u00ce\t\2\2\2\u00ce\u00cf\5\32\16\2\u00cf\u00d0\b\r\1\2\u00d0"+
		"\u00d2\3\2\2\2\u00d1\u00cd\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\31\3\2\2"+
		"\2\u00d3\u00d4\5\34\17\2\u00d4\u00db\b\16\1\2\u00d5\u00d6\t\3\2\2\u00d6"+
		"\u00d7\5\34\17\2\u00d7\u00d8\b\16\1\2\u00d8\u00da\3\2\2\2\u00d9\u00d5"+
		"\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"\33\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00df\5\36\20\2\u00df\u00e6\b\17"+
		"\1\2\u00e0\u00e1\t\4\2\2\u00e1\u00e2\5\36\20\2\u00e2\u00e3\b\17\1\2\u00e3"+
		"\u00e5\3\2\2\2\u00e4\u00e0\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2"+
		"\2\2\u00e6\u00e7\3\2\2\2\u00e7\35\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00ea"+
		"\7$\2\2\u00ea\u00ef\b\20\1\2\u00eb\u00ec\5 \21\2\u00ec\u00ed\b\20\1\2"+
		"\u00ed\u00f0\3\2\2\2\u00ee\u00f0\b\20\1\2\u00ef\u00eb\3\2\2\2\u00ef\u00ee"+
		"\3\2\2\2\u00f0\u00fe\3\2\2\2\u00f1\u00f2\7%\2\2\u00f2\u00fe\b\20\1\2\u00f3"+
		"\u00f5\t\3\2\2\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6\3\2"+
		"\2\2\u00f6\u00f7\7&\2\2\u00f7\u00fe\b\20\1\2\u00f8\u00f9\7\3\2\2\u00f9"+
		"\u00fa\5\24\13\2\u00fa\u00fb\7\5\2\2\u00fb\u00fc\b\20\1\2\u00fc\u00fe"+
		"\3\2\2\2\u00fd\u00e9\3\2\2\2\u00fd\u00f1\3\2\2\2\u00fd\u00f4\3\2\2\2\u00fd"+
		"\u00f8\3\2\2\2\u00fe\37\3\2\2\2\u00ff\u0115\b\21\1\2\u0100\u0101\7\3\2"+
		"\2\u0101\u010d\b\21\1\2\u0102\u0103\5\24\13\2\u0103\u010a\b\21\1\2\u0104"+
		"\u0105\7\4\2\2\u0105\u0106\5\24\13\2\u0106\u0107\b\21\1\2\u0107\u0109"+
		"\3\2\2\2\u0108\u0104\3\2\2\2\u0109\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a"+
		"\u010b\3\2\2\2\u010b\u010e\3\2\2\2\u010c\u010a\3\2\2\2\u010d\u0102\3\2"+
		"\2\2\u010d\u010e\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\7\5\2\2\u0110"+
		"\u0116\b\21\1\2\u0111\u0112\7\f\2\2\u0112\u0113\5\24\13\2\u0113\u0114"+
		"\b\21\1\2\u0114\u0116\3\2\2\2\u0115\u0100\3\2\2\2\u0115\u0111\3\2\2\2"+
		"\u0116\u011a\3\2\2\2\u0117\u0118\5 \21\2\u0118\u0119\b\21\1\2\u0119\u011b"+
		"\3\2\2\2\u011a\u0117\3\2\2\2\u011a\u011b\3\2\2\2\u011b!\3\2\2\2\27&\65"+
		"8Fe\u0080\u008f\u00a4\u00b1\u00bd\u00c8\u00d1\u00db\u00e6\u00ef\u00f4"+
		"\u00fd\u010a\u010d\u0115\u011a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}