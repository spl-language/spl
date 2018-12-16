// Generated from /home/george/Projects/spl/language/src/main/java/com/spl/parser/Spl.g4 by ANTLR 4.7
package com.spl.parser;

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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, WS=30, COMMENT=31, LINE_COMMENT=32, 
		IDEN=33, STRING_LITERAL=34, NUMERIC_LITERAL=35;
	public static final int
		RULE_spl = 0, RULE_dfunc = 1, RULE_body = 2, RULE_statement = 3, RULE_dvarb = 4, 
		RULE_dconst = 5, RULE_while_statement = 6, RULE_while_block = 7, RULE_if_statement = 8, 
		RULE_if_block = 9, RULE_return_statement = 10, RULE_expr = 11, RULE_logic_term = 12, 
		RULE_logic_factor = 13, RULE_arithmetic = 14, RULE_term = 15, RULE_fact = 16, 
		RULE_member_expression = 17;
	public static final String[] ruleNames = {
		"spl", "dfunc", "body", "statement", "dvarb", "dconst", "while_statement", 
		"while_block", "if_statement", "if_block", "return_statement", "expr", 
		"logic_term", "logic_factor", "arithmetic", "term", "fact", "member_expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "','", "')'", "'begin'", "';'", "'end'", "'print'", "'read'", 
		"'int'", "'const'", "'='", "'while'", "'do'", "'if'", "'then'", "'return'", 
		"'||'", "'&&'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'+'", "'-'", 
		"'*'", "'/'", "'%'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "WS", "COMMENT", "LINE_COMMENT", "IDEN", 
		"STRING_LITERAL", "NUMERIC_LITERAL"
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
			setState(36);
			dfunc();
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDEN) {
				{
				{
				setState(37);
				dfunc();
				}
				}
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(43);
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
		public Token IDEN;
		public Token s;
		public BodyContext block;
		public List<TerminalNode> IDEN() { return getTokens(SplParser.IDEN); }
		public TerminalNode IDEN(int i) {
			return getToken(SplParser.IDEN, i);
		}
		public BodyContext body() {
			return getRuleContext(BodyContext.class,0);
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
			setState(45);
			((DfuncContext)_localctx).IDEN = match(IDEN);
			setState(46);
			((DfuncContext)_localctx).s = match(T__0);
			 factory.startFunction(((DfuncContext)_localctx).IDEN, ((DfuncContext)_localctx).s); 
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDEN) {
				{
				setState(48);
				((DfuncContext)_localctx).IDEN = match(IDEN);
				 factory.addFormalParameter(((DfuncContext)_localctx).IDEN); 
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(50);
					match(T__1);
					setState(51);
					((DfuncContext)_localctx).IDEN = match(IDEN);
					 factory.addFormalParameter(((DfuncContext)_localctx).IDEN); 
					}
					}
					setState(57);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(60);
			match(T__2);
			setState(61);
			((DfuncContext)_localctx).block = body(false);
			 factory.finishFunction(((DfuncContext)_localctx).block.result); 
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

	public static class BodyContext extends ParserRuleContext {
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
		public BodyContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public BodyContext(ParserRuleContext parent, int invokingState, boolean inLoop) {
			super(parent, invokingState);
			this.inLoop = inLoop;
		}
		@Override public int getRuleIndex() { return RULE_body; }
	}

	public final BodyContext body(boolean inLoop) throws RecognitionException {
		BodyContext _localctx = new BodyContext(_ctx, getState(), inLoop);
		enterRule(_localctx, 4, RULE_body);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(65);
			((BodyContext)_localctx).s = match(T__3);
			setState(72);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(66);
					((BodyContext)_localctx).statement = statement(inLoop);
					setState(67);
					match(T__4);
					 body.add(((BodyContext)_localctx).statement.result); 
					}
					} 
				}
				setState(74);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(75);
			((BodyContext)_localctx).statement = statement(inLoop);
			 body.add(((BodyContext)_localctx).statement.result); 
			setState(77);
			((BodyContext)_localctx).e = match(T__5);
			 ((BodyContext)_localctx).result =  factory.finishBlock(body, ((BodyContext)_localctx).s.getStartIndex(), ((BodyContext)_localctx).e.getStopIndex() - ((BodyContext)_localctx).s.getStartIndex() + 1); 
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
		public ExprContext expr;
		public DvarbContext dvarb;
		public DconstContext dconst;
		public Token id;
		public Token IDEN;
		public While_statementContext while_statement() {
			return getRuleContext(While_statementContext.class,0);
		}
		public If_statementContext if_statement() {
			return getRuleContext(If_statementContext.class,0);
		}
		public Return_statementContext return_statement() {
			return getRuleContext(Return_statementContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DvarbContext dvarb() {
			return getRuleContext(DvarbContext.class,0);
		}
		public DconstContext dconst() {
			return getRuleContext(DconstContext.class,0);
		}
		public TerminalNode IDEN() { return getToken(SplParser.IDEN, 0); }
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				{
				setState(80);
				((StatementContext)_localctx).while_statement = while_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).while_statement.result; 
				}
				break;
			case T__13:
				{
				setState(83);
				((StatementContext)_localctx).if_statement = if_statement(inLoop);
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).if_statement.result; 
				}
				break;
			case T__15:
				{
				setState(86);
				((StatementContext)_localctx).return_statement = return_statement();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).return_statement.result; 
				}
				break;
			case T__0:
			case T__24:
			case T__25:
			case IDEN:
			case STRING_LITERAL:
			case NUMERIC_LITERAL:
				{
				setState(89);
				((StatementContext)_localctx).expr = expr();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).expr.result; 
				}
				break;
			case T__8:
				{
				setState(92);
				((StatementContext)_localctx).dvarb = dvarb();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).dvarb.result; 
				}
				break;
			case T__9:
				{
				setState(95);
				((StatementContext)_localctx).dconst = dconst();
				 ((StatementContext)_localctx).result =  ((StatementContext)_localctx).dconst.result; 
				}
				break;
			case T__6:
				{
				setState(98);
				((StatementContext)_localctx).id = match(T__6);
				setState(99);
				((StatementContext)_localctx).expr = expr();
				 ((StatementContext)_localctx).result =  factory.createPrint(((StatementContext)_localctx).id, ((StatementContext)_localctx).expr.result); 
				}
				break;
			case T__7:
				{
				setState(102);
				((StatementContext)_localctx).id = match(T__7);
				setState(103);
				((StatementContext)_localctx).IDEN = match(IDEN);
				 ((StatementContext)_localctx).result =  factory.createRead(((StatementContext)_localctx).id, ((StatementContext)_localctx).IDEN); 
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

	public static class DvarbContext extends ParserRuleContext {
		public SplStatementNode result;
		public Token id;
		public Token IDEN;
		public List<TerminalNode> IDEN() { return getTokens(SplParser.IDEN); }
		public TerminalNode IDEN(int i) {
			return getToken(SplParser.IDEN, i);
		}
		public DvarbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dvarb; }
	}

	public final DvarbContext dvarb() throws RecognitionException {
		DvarbContext _localctx = new DvarbContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dvarb);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			((DvarbContext)_localctx).id = match(T__8);
			 List<Token> variables = new ArrayList<>(); 
			setState(109);
			((DvarbContext)_localctx).IDEN = match(IDEN);
			 variables.add(((DvarbContext)_localctx).IDEN); 
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(111);
				match(T__1);
				setState(112);
				((DvarbContext)_localctx).IDEN = match(IDEN);
				 variables.add(((DvarbContext)_localctx).IDEN); 
				}
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((DvarbContext)_localctx).result =  factory.declareIntVariables(((DvarbContext)_localctx).id, variables); 
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

	public static class DconstContext extends ParserRuleContext {
		public SplStatementNode result;
		public Token id;
		public Token IDEN;
		public ExprContext expr;
		public List<TerminalNode> IDEN() { return getTokens(SplParser.IDEN); }
		public TerminalNode IDEN(int i) {
			return getToken(SplParser.IDEN, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DconstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dconst; }
	}

	public final DconstContext dconst() throws RecognitionException {
		DconstContext _localctx = new DconstContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_dconst);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			((DconstContext)_localctx).id = match(T__9);
			 List<TokenAndValue> tokenValues = new ArrayList<>(); 
			setState(123);
			((DconstContext)_localctx).IDEN = match(IDEN);
			 TokenAndValue current = new TokenAndValue(((DconstContext)_localctx).IDEN); 
			setState(125);
			match(T__10);
			setState(126);
			((DconstContext)_localctx).expr = expr();
			 current.setSplExpressionNode(((DconstContext)_localctx).expr.result);
			                                                  tokenValues.add(current); 
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(128);
				match(T__1);
				setState(129);
				((DconstContext)_localctx).IDEN = match(IDEN);
				 current = new TokenAndValue(((DconstContext)_localctx).IDEN); 
				setState(131);
				match(T__10);
				setState(132);
				((DconstContext)_localctx).expr = expr();
				 current.setSplExpressionNode(((DconstContext)_localctx).expr.result);
				                                                  tokenValues.add(current); 
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 ((DconstContext)_localctx).result =  factory.declareConstVariable(((DconstContext)_localctx).id, tokenValues); 
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
		public ExprContext condition;
		public While_blockContext block;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 12, RULE_while_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			((While_statementContext)_localctx).w = match(T__11);
			setState(143);
			((While_statementContext)_localctx).condition = expr();
			setState(144);
			((While_statementContext)_localctx).block = while_block();
			 ((While_statementContext)_localctx).result =  factory.createWhile(((While_statementContext)_localctx).w, ((While_statementContext)_localctx).condition.result, ((While_statementContext)_localctx).block.result); 
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
		enterRule(_localctx, 14, RULE_while_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(148);
			((While_blockContext)_localctx).s = match(T__12);
			setState(155);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(149);
					((While_blockContext)_localctx).statement = statement(true);
					setState(150);
					match(T__4);
					 body.add(((While_blockContext)_localctx).statement.result); 
					}
					} 
				}
				setState(157);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(158);
			((While_blockContext)_localctx).statement = statement(true);
			 body.add(((While_blockContext)_localctx).statement.result); 
			setState(160);
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
		public ExprContext condition;
		public If_blockContext then;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 16, RULE_if_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			((If_statementContext)_localctx).i = match(T__13);
			setState(164);
			((If_statementContext)_localctx).condition = expr();
			setState(165);
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
		enterRule(_localctx, 18, RULE_if_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			 factory.startBlock();
			                                                  List<SplStatementNode> body = new ArrayList<>(); 
			setState(169);
			((If_blockContext)_localctx).s = match(T__14);
			setState(176);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(170);
					((If_blockContext)_localctx).statement = statement(false);
					setState(171);
					match(T__4);
					 body.add(((If_blockContext)_localctx).statement.result); 
					}
					} 
				}
				setState(178);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(179);
			((If_blockContext)_localctx).statement = statement(false);
			 body.add(((If_blockContext)_localctx).statement.result); 
			setState(181);
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
		public ExprContext expr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Return_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_statement; }
	}

	public final Return_statementContext return_statement() throws RecognitionException {
		Return_statementContext _localctx = new Return_statementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_return_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			((Return_statementContext)_localctx).r = match(T__15);
			 SplExpressionNode value = null; 
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__24) | (1L << T__25) | (1L << IDEN) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
				{
				setState(186);
				((Return_statementContext)_localctx).expr = expr();
				 value = ((Return_statementContext)_localctx).expr.result; 
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

	public static class ExprContext extends ParserRuleContext {
		public SplExpressionNode result;
		public Logic_termContext logic_term;
		public Token op;
		public List<Logic_termContext> logic_term() {
			return getRuleContexts(Logic_termContext.class);
		}
		public Logic_termContext logic_term(int i) {
			return getRuleContext(Logic_termContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			((ExprContext)_localctx).logic_term = logic_term();
			 ((ExprContext)_localctx).result =  ((ExprContext)_localctx).logic_term.result; 
			setState(201);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(195);
					((ExprContext)_localctx).op = match(T__16);
					setState(196);
					((ExprContext)_localctx).logic_term = logic_term();
					 ((ExprContext)_localctx).result =  factory.createBinary(((ExprContext)_localctx).op, _localctx.result, ((ExprContext)_localctx).logic_term.result); 
					}
					} 
				}
				setState(203);
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
		enterRule(_localctx, 24, RULE_logic_term);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			((Logic_termContext)_localctx).logic_factor = logic_factor();
			 ((Logic_termContext)_localctx).result =  ((Logic_termContext)_localctx).logic_factor.result; 
			setState(212);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(206);
					((Logic_termContext)_localctx).op = match(T__17);
					setState(207);
					((Logic_termContext)_localctx).logic_factor = logic_factor();
					 ((Logic_termContext)_localctx).result =  factory.createBinary(((Logic_termContext)_localctx).op, _localctx.result, ((Logic_termContext)_localctx).logic_factor.result); 
					}
					} 
				}
				setState(214);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
		enterRule(_localctx, 26, RULE_logic_factor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			((Logic_factorContext)_localctx).arithmetic = arithmetic();
			 ((Logic_factorContext)_localctx).result =  ((Logic_factorContext)_localctx).arithmetic.result; 
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(217);
				((Logic_factorContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
					((Logic_factorContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(218);
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
		enterRule(_localctx, 28, RULE_arithmetic);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			((ArithmeticContext)_localctx).term = term();
			 ((ArithmeticContext)_localctx).result =  ((ArithmeticContext)_localctx).term.result; 
			setState(231);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(225);
					((ArithmeticContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__24 || _la==T__25) ) {
						((ArithmeticContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(226);
					((ArithmeticContext)_localctx).term = term();
					 ((ArithmeticContext)_localctx).result =  factory.createBinary(((ArithmeticContext)_localctx).op, _localctx.result, ((ArithmeticContext)_localctx).term.result); 
					}
					} 
				}
				setState(233);
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

	public static class TermContext extends ParserRuleContext {
		public SplExpressionNode result;
		public FactContext fact;
		public Token op;
		public List<FactContext> fact() {
			return getRuleContexts(FactContext.class);
		}
		public FactContext fact(int i) {
			return getRuleContext(FactContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			((TermContext)_localctx).fact = fact();
			 ((TermContext)_localctx).result =  ((TermContext)_localctx).fact.result; 
			setState(242);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(236);
					((TermContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__26) | (1L << T__27) | (1L << T__28))) != 0)) ) {
						((TermContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(237);
					((TermContext)_localctx).fact = fact();
					 ((TermContext)_localctx).result =  factory.createBinary(((TermContext)_localctx).op, _localctx.result, ((TermContext)_localctx).fact.result); 
					}
					} 
				}
				setState(244);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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

	public static class FactContext extends ParserRuleContext {
		public SplExpressionNode result;
		public Token IDEN;
		public Member_expressionContext member_expression;
		public Token STRING_LITERAL;
		public Token sign;
		public Token NUMERIC_LITERAL;
		public Token s;
		public ExprContext ex;
		public Token e;
		public TerminalNode IDEN() { return getToken(SplParser.IDEN, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(SplParser.STRING_LITERAL, 0); }
		public TerminalNode NUMERIC_LITERAL() { return getToken(SplParser.NUMERIC_LITERAL, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Member_expressionContext member_expression() {
			return getRuleContext(Member_expressionContext.class,0);
		}
		public FactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fact; }
	}

	public final FactContext fact() throws RecognitionException {
		FactContext _localctx = new FactContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_fact);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDEN:
				{
				setState(245);
				((FactContext)_localctx).IDEN = match(IDEN);
				 SplExpressionNode assignmentName = factory.createStringLiteral(((FactContext)_localctx).IDEN, false); 
				setState(251);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					{
					setState(247);
					((FactContext)_localctx).member_expression = member_expression(null, null, assignmentName);
					 ((FactContext)_localctx).result =  ((FactContext)_localctx).member_expression.result; 
					}
					break;
				case 2:
					{
					 ((FactContext)_localctx).result =  factory.createRead(assignmentName); 
					}
					break;
				}
				}
				break;
			case STRING_LITERAL:
				{
				setState(253);
				((FactContext)_localctx).STRING_LITERAL = match(STRING_LITERAL);
				 ((FactContext)_localctx).result =  factory.createStringLiteral(((FactContext)_localctx).STRING_LITERAL, true); 
				}
				break;
			case T__24:
			case T__25:
			case NUMERIC_LITERAL:
				{
				setState(256);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__24 || _la==T__25) {
					{
					setState(255);
					((FactContext)_localctx).sign = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==T__24 || _la==T__25) ) {
						((FactContext)_localctx).sign = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(258);
				((FactContext)_localctx).NUMERIC_LITERAL = match(NUMERIC_LITERAL);
				 ((FactContext)_localctx).result =  factory.createNumericLiteral(((FactContext)_localctx).sign, ((FactContext)_localctx).NUMERIC_LITERAL); 
				}
				break;
			case T__0:
				{
				setState(260);
				((FactContext)_localctx).s = match(T__0);
				setState(261);
				((FactContext)_localctx).ex = expr();
				setState(262);
				((FactContext)_localctx).e = match(T__2);
				 ((FactContext)_localctx).result =  factory.createParenExpression(((FactContext)_localctx).ex.result, ((FactContext)_localctx).s.getStartIndex(), ((FactContext)_localctx).e.getStopIndex() - ((FactContext)_localctx).s.getStartIndex() + 1); 
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
		public ExprContext expr;
		public Token e;
		public Member_expressionContext member_expression;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
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
		enterRule(_localctx, 34, RULE_member_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 SplExpressionNode receiver = r;
			                                                  SplExpressionNode nestedAssignmentName = null; 
			setState(289);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				{
				setState(268);
				match(T__0);
				 List<SplExpressionNode> parameters = new ArrayList<>();
				                                                  if (receiver == null) {
				                                                      receiver = factory.createRead(assignmentName);
				                                                  } 
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__24) | (1L << T__25) | (1L << IDEN) | (1L << STRING_LITERAL) | (1L << NUMERIC_LITERAL))) != 0)) {
					{
					setState(270);
					((Member_expressionContext)_localctx).expr = expr();
					 parameters.add(((Member_expressionContext)_localctx).expr.result); 
					setState(278);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__1) {
						{
						{
						setState(272);
						match(T__1);
						setState(273);
						((Member_expressionContext)_localctx).expr = expr();
						 parameters.add(((Member_expressionContext)_localctx).expr.result); 
						}
						}
						setState(280);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(283);
				((Member_expressionContext)_localctx).e = match(T__2);
				 ((Member_expressionContext)_localctx).result =  factory.createCall(receiver, parameters, ((Member_expressionContext)_localctx).e); 
				}
				break;
			case T__10:
				{
				setState(285);
				match(T__10);
				setState(286);
				((Member_expressionContext)_localctx).expr = expr();
				 if (assignmentName == null) {
				                                                      SemErr((((Member_expressionContext)_localctx).expr!=null?(((Member_expressionContext)_localctx).expr.start):null), "invalid assignment target");
				                                                } else if (assignmentReceiver == null) {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createAssignment(assignmentName, ((Member_expressionContext)_localctx).expr.result);
				                                                } else {
				                                                      ((Member_expressionContext)_localctx).result =  factory.createWriteProperty(assignmentReceiver, assignmentName, ((Member_expressionContext)_localctx).expr.result);
				                                                } 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(294);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(291);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3%\u012b\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\7\2)\n\2\f\2\16\2,\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\7\38\n\3\f\3\16\3;\13\3\5\3=\n\3\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\7\4I\n\4\f\4\16\4L\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\5\5l\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6u\n\6\f\6"+
		"\16\6x\13\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\7\7\u008a\n\7\f\7\16\7\u008d\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\7\t\u009c\n\t\f\t\16\t\u009f\13\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00b1\n"+
		"\13\f\13\16\13\u00b4\13\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3"+
		"\f\5\f\u00c0\n\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00ca\n\r\f\r\16"+
		"\r\u00cd\13\r\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00d5\n\16\f\16\16\16"+
		"\u00d8\13\16\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00e0\n\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\7\20\u00e8\n\20\f\20\16\20\u00eb\13\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\7\21\u00f3\n\21\f\21\16\21\u00f6\13\21\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\5\22\u00fe\n\22\3\22\3\22\3\22\5\22\u0103\n\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u010c\n\22\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\7\23\u0117\n\23\f\23\16\23\u011a\13\23\5\23\u011c"+
		"\n\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0124\n\23\3\23\3\23\3\23\5\23"+
		"\u0129\n\23\3\23\2\2\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\5"+
		"\3\2\25\32\3\2\33\34\3\2\35\37\2\u0136\2&\3\2\2\2\4/\3\2\2\2\6B\3\2\2"+
		"\2\bk\3\2\2\2\nm\3\2\2\2\f{\3\2\2\2\16\u0090\3\2\2\2\20\u0095\3\2\2\2"+
		"\22\u00a5\3\2\2\2\24\u00aa\3\2\2\2\26\u00ba\3\2\2\2\30\u00c3\3\2\2\2\32"+
		"\u00ce\3\2\2\2\34\u00d9\3\2\2\2\36\u00e1\3\2\2\2 \u00ec\3\2\2\2\"\u010b"+
		"\3\2\2\2$\u010d\3\2\2\2&*\5\4\3\2\')\5\4\3\2(\'\3\2\2\2),\3\2\2\2*(\3"+
		"\2\2\2*+\3\2\2\2+-\3\2\2\2,*\3\2\2\2-.\7\2\2\3.\3\3\2\2\2/\60\7#\2\2\60"+
		"\61\7\3\2\2\61<\b\3\1\2\62\63\7#\2\2\639\b\3\1\2\64\65\7\4\2\2\65\66\7"+
		"#\2\2\668\b\3\1\2\67\64\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:=\3\2"+
		"\2\2;9\3\2\2\2<\62\3\2\2\2<=\3\2\2\2=>\3\2\2\2>?\7\5\2\2?@\5\6\4\2@A\b"+
		"\3\1\2A\5\3\2\2\2BC\b\4\1\2CJ\7\6\2\2DE\5\b\5\2EF\7\7\2\2FG\b\4\1\2GI"+
		"\3\2\2\2HD\3\2\2\2IL\3\2\2\2JH\3\2\2\2JK\3\2\2\2KM\3\2\2\2LJ\3\2\2\2M"+
		"N\5\b\5\2NO\b\4\1\2OP\7\b\2\2PQ\b\4\1\2Q\7\3\2\2\2RS\5\16\b\2ST\b\5\1"+
		"\2Tl\3\2\2\2UV\5\22\n\2VW\b\5\1\2Wl\3\2\2\2XY\5\26\f\2YZ\b\5\1\2Zl\3\2"+
		"\2\2[\\\5\30\r\2\\]\b\5\1\2]l\3\2\2\2^_\5\n\6\2_`\b\5\1\2`l\3\2\2\2ab"+
		"\5\f\7\2bc\b\5\1\2cl\3\2\2\2de\7\t\2\2ef\5\30\r\2fg\b\5\1\2gl\3\2\2\2"+
		"hi\7\n\2\2ij\7#\2\2jl\b\5\1\2kR\3\2\2\2kU\3\2\2\2kX\3\2\2\2k[\3\2\2\2"+
		"k^\3\2\2\2ka\3\2\2\2kd\3\2\2\2kh\3\2\2\2l\t\3\2\2\2mn\7\13\2\2no\b\6\1"+
		"\2op\7#\2\2pv\b\6\1\2qr\7\4\2\2rs\7#\2\2su\b\6\1\2tq\3\2\2\2ux\3\2\2\2"+
		"vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\b\6\1\2z\13\3\2\2\2{|\7\f\2"+
		"\2|}\b\7\1\2}~\7#\2\2~\177\b\7\1\2\177\u0080\7\r\2\2\u0080\u0081\5\30"+
		"\r\2\u0081\u008b\b\7\1\2\u0082\u0083\7\4\2\2\u0083\u0084\7#\2\2\u0084"+
		"\u0085\b\7\1\2\u0085\u0086\7\r\2\2\u0086\u0087\5\30\r\2\u0087\u0088\b"+
		"\7\1\2\u0088\u008a\3\2\2\2\u0089\u0082\3\2\2\2\u008a\u008d\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b\3\2"+
		"\2\2\u008e\u008f\b\7\1\2\u008f\r\3\2\2\2\u0090\u0091\7\16\2\2\u0091\u0092"+
		"\5\30\r\2\u0092\u0093\5\20\t\2\u0093\u0094\b\b\1\2\u0094\17\3\2\2\2\u0095"+
		"\u0096\b\t\1\2\u0096\u009d\7\17\2\2\u0097\u0098\5\b\5\2\u0098\u0099\7"+
		"\7\2\2\u0099\u009a\b\t\1\2\u009a\u009c\3\2\2\2\u009b\u0097\3\2\2\2\u009c"+
		"\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2"+
		"\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\5\b\5\2\u00a1\u00a2\b\t\1\2\u00a2"+
		"\u00a3\7\b\2\2\u00a3\u00a4\b\t\1\2\u00a4\21\3\2\2\2\u00a5\u00a6\7\20\2"+
		"\2\u00a6\u00a7\5\30\r\2\u00a7\u00a8\5\24\13\2\u00a8\u00a9\b\n\1\2\u00a9"+
		"\23\3\2\2\2\u00aa\u00ab\b\13\1\2\u00ab\u00b2\7\21\2\2\u00ac\u00ad\5\b"+
		"\5\2\u00ad\u00ae\7\7\2\2\u00ae\u00af\b\13\1\2\u00af\u00b1\3\2\2\2\u00b0"+
		"\u00ac\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2"+
		"\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b6\5\b\5\2\u00b6"+
		"\u00b7\b\13\1\2\u00b7\u00b8\7\b\2\2\u00b8\u00b9\b\13\1\2\u00b9\25\3\2"+
		"\2\2\u00ba\u00bb\7\22\2\2\u00bb\u00bf\b\f\1\2\u00bc\u00bd\5\30\r\2\u00bd"+
		"\u00be\b\f\1\2\u00be\u00c0\3\2\2\2\u00bf\u00bc\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\b\f\1\2\u00c2\27\3\2\2\2\u00c3\u00c4"+
		"\5\32\16\2\u00c4\u00cb\b\r\1\2\u00c5\u00c6\7\23\2\2\u00c6\u00c7\5\32\16"+
		"\2\u00c7\u00c8\b\r\1\2\u00c8\u00ca\3\2\2\2\u00c9\u00c5\3\2\2\2\u00ca\u00cd"+
		"\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\31\3\2\2\2\u00cd"+
		"\u00cb\3\2\2\2\u00ce\u00cf\5\34\17\2\u00cf\u00d6\b\16\1\2\u00d0\u00d1"+
		"\7\24\2\2\u00d1\u00d2\5\34\17\2\u00d2\u00d3\b\16\1\2\u00d3\u00d5\3\2\2"+
		"\2\u00d4\u00d0\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7"+
		"\3\2\2\2\u00d7\33\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9\u00da\5\36\20\2\u00da"+
		"\u00df\b\17\1\2\u00db\u00dc\t\2\2\2\u00dc\u00dd\5\36\20\2\u00dd\u00de"+
		"\b\17\1\2\u00de\u00e0\3\2\2\2\u00df\u00db\3\2\2\2\u00df\u00e0\3\2\2\2"+
		"\u00e0\35\3\2\2\2\u00e1\u00e2\5 \21\2\u00e2\u00e9\b\20\1\2\u00e3\u00e4"+
		"\t\3\2\2\u00e4\u00e5\5 \21\2\u00e5\u00e6\b\20\1\2\u00e6\u00e8\3\2\2\2"+
		"\u00e7\u00e3\3\2\2\2\u00e8\u00eb\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea"+
		"\3\2\2\2\u00ea\37\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00ed\5\"\22\2\u00ed"+
		"\u00f4\b\21\1\2\u00ee\u00ef\t\4\2\2\u00ef\u00f0\5\"\22\2\u00f0\u00f1\b"+
		"\21\1\2\u00f1\u00f3\3\2\2\2\u00f2\u00ee\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4"+
		"\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5!\3\2\2\2\u00f6\u00f4\3\2\2\2"+
		"\u00f7\u00f8\7#\2\2\u00f8\u00fd\b\22\1\2\u00f9\u00fa\5$\23\2\u00fa\u00fb"+
		"\b\22\1\2\u00fb\u00fe\3\2\2\2\u00fc\u00fe\b\22\1\2\u00fd\u00f9\3\2\2\2"+
		"\u00fd\u00fc\3\2\2\2\u00fe\u010c\3\2\2\2\u00ff\u0100\7$\2\2\u0100\u010c"+
		"\b\22\1\2\u0101\u0103\t\3\2\2\u0102\u0101\3\2\2\2\u0102\u0103\3\2\2\2"+
		"\u0103\u0104\3\2\2\2\u0104\u0105\7%\2\2\u0105\u010c\b\22\1\2\u0106\u0107"+
		"\7\3\2\2\u0107\u0108\5\30\r\2\u0108\u0109\7\5\2\2\u0109\u010a\b\22\1\2"+
		"\u010a\u010c\3\2\2\2\u010b\u00f7\3\2\2\2\u010b\u00ff\3\2\2\2\u010b\u0102"+
		"\3\2\2\2\u010b\u0106\3\2\2\2\u010c#\3\2\2\2\u010d\u0123\b\23\1\2\u010e"+
		"\u010f\7\3\2\2\u010f\u011b\b\23\1\2\u0110\u0111\5\30\r\2\u0111\u0118\b"+
		"\23\1\2\u0112\u0113\7\4\2\2\u0113\u0114\5\30\r\2\u0114\u0115\b\23\1\2"+
		"\u0115\u0117\3\2\2\2\u0116\u0112\3\2\2\2\u0117\u011a\3\2\2\2\u0118\u0116"+
		"\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011b"+
		"\u0110\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e\7\5"+
		"\2\2\u011e\u0124\b\23\1\2\u011f\u0120\7\r\2\2\u0120\u0121\5\30\r\2\u0121"+
		"\u0122\b\23\1\2\u0122\u0124\3\2\2\2\u0123\u010e\3\2\2\2\u0123\u011f\3"+
		"\2\2\2\u0124\u0128\3\2\2\2\u0125\u0126\5$\23\2\u0126\u0127\b\23\1\2\u0127"+
		"\u0129\3\2\2\2\u0128\u0125\3\2\2\2\u0128\u0129\3\2\2\2\u0129%\3\2\2\2"+
		"\30*9<Jkv\u008b\u009d\u00b2\u00bf\u00cb\u00d6\u00df\u00e9\u00f4\u00fd"+
		"\u0102\u010b\u0118\u011b\u0123\u0128";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}