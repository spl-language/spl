// Generated from /home/george/Projects/spl/language/src/main/java/com/tone/parser/Spl.g4 by ANTLR 4.7
package com.spl.parser;

// DO NOT MODIFY - generated from Tone.g4 using "mx create-tone-parser"

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SplLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "WS", "COMMENT", "LINE_COMMENT", 
		"LETTER", "NON_ZERO_DIGIT", "DIGIT", "HEX_DIGIT", "OCT_DIGIT", "BINARY_DIGIT", 
		"TAB", "STRING_CHAR", "IDENTIFIER", "STRING_LITERAL", "NUMERIC_LITERAL"
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


	public SplLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Spl.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2&\u0112\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\31\3"+
		"\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3 \6 \u00c7\n \r \16 \u00c8\3 \3 \3!\3!\3!\3!\7!\u00d1\n!\f!\16!\u00d4"+
		"\13!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\7\"\u00df\n\"\f\"\16\"\u00e2\13\""+
		"\3\"\3\"\3#\5#\u00e7\n#\3$\3$\3%\3%\3&\5&\u00ee\n&\3\'\3\'\3(\3(\3)\3"+
		")\3*\3*\3+\3+\3+\7+\u00fb\n+\f+\16+\u00fe\13+\3,\3,\7,\u0102\n,\f,\16"+
		",\u0105\13,\3,\3,\3-\3-\3-\7-\u010c\n-\f-\16-\u010f\13-\5-\u0111\n-\3"+
		"\u00d2\2.\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33"+
		"\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67"+
		"\359\36;\37= ?!A\"C#E\2G\2I\2K\2M\2O\2Q\2S\2U$W%Y&\3\2\n\5\2\13\f\16\17"+
		"\"\"\4\2\f\f\17\17\6\2&&C\\aac|\3\2\63;\3\2\62;\5\2\62;CHch\3\2\629\6"+
		"\2\f\f\17\17$$^^\2\u0111\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\3[\3\2\2\2\5]\3\2\2\2\7_\3\2\2\2\t"+
		"a\3\2\2\2\13g\3\2\2\2\ri\3\2\2\2\17m\3\2\2\2\21v\3\2\2\2\23z\3\2\2\2\25"+
		"\u0080\3\2\2\2\27\u0082\3\2\2\2\31\u0088\3\2\2\2\33\u008d\3\2\2\2\35\u0093"+
		"\3\2\2\2\37\u0096\3\2\2\2!\u0099\3\2\2\2#\u009e\3\2\2\2%\u00a5\3\2\2\2"+
		"\'\u00a8\3\2\2\2)\u00ab\3\2\2\2+\u00ad\3\2\2\2-\u00b0\3\2\2\2/\u00b2\3"+
		"\2\2\2\61\u00b5\3\2\2\2\63\u00b8\3\2\2\2\65\u00bb\3\2\2\2\67\u00bd\3\2"+
		"\2\29\u00bf\3\2\2\2;\u00c1\3\2\2\2=\u00c3\3\2\2\2?\u00c6\3\2\2\2A\u00cc"+
		"\3\2\2\2C\u00da\3\2\2\2E\u00e6\3\2\2\2G\u00e8\3\2\2\2I\u00ea\3\2\2\2K"+
		"\u00ed\3\2\2\2M\u00ef\3\2\2\2O\u00f1\3\2\2\2Q\u00f3\3\2\2\2S\u00f5\3\2"+
		"\2\2U\u00f7\3\2\2\2W\u00ff\3\2\2\2Y\u0110\3\2\2\2[\\\7*\2\2\\\4\3\2\2"+
		"\2]^\7.\2\2^\6\3\2\2\2_`\7+\2\2`\b\3\2\2\2ab\7d\2\2bc\7g\2\2cd\7i\2\2"+
		"de\7k\2\2ef\7p\2\2f\n\3\2\2\2gh\7=\2\2h\f\3\2\2\2ij\7g\2\2jk\7p\2\2kl"+
		"\7f\2\2l\16\3\2\2\2mn\7f\2\2no\7g\2\2op\7d\2\2pq\7w\2\2qr\7i\2\2rs\7i"+
		"\2\2st\7g\2\2tu\7t\2\2u\20\3\2\2\2vw\7k\2\2wx\7p\2\2xy\7v\2\2y\22\3\2"+
		"\2\2z{\7e\2\2{|\7q\2\2|}\7p\2\2}~\7u\2\2~\177\7v\2\2\177\24\3\2\2\2\u0080"+
		"\u0081\7?\2\2\u0081\26\3\2\2\2\u0082\u0083\7r\2\2\u0083\u0084\7t\2\2\u0084"+
		"\u0085\7k\2\2\u0085\u0086\7p\2\2\u0086\u0087\7v\2\2\u0087\30\3\2\2\2\u0088"+
		"\u0089\7t\2\2\u0089\u008a\7g\2\2\u008a\u008b\7c\2\2\u008b\u008c\7f\2\2"+
		"\u008c\32\3\2\2\2\u008d\u008e\7y\2\2\u008e\u008f\7j\2\2\u008f\u0090\7"+
		"k\2\2\u0090\u0091\7n\2\2\u0091\u0092\7g\2\2\u0092\34\3\2\2\2\u0093\u0094"+
		"\7f\2\2\u0094\u0095\7q\2\2\u0095\36\3\2\2\2\u0096\u0097\7k\2\2\u0097\u0098"+
		"\7h\2\2\u0098 \3\2\2\2\u0099\u009a\7v\2\2\u009a\u009b\7j\2\2\u009b\u009c"+
		"\7g\2\2\u009c\u009d\7p\2\2\u009d\"\3\2\2\2\u009e\u009f\7t\2\2\u009f\u00a0"+
		"\7g\2\2\u00a0\u00a1\7v\2\2\u00a1\u00a2\7w\2\2\u00a2\u00a3\7t\2\2\u00a3"+
		"\u00a4\7p\2\2\u00a4$\3\2\2\2\u00a5\u00a6\7~\2\2\u00a6\u00a7\7~\2\2\u00a7"+
		"&\3\2\2\2\u00a8\u00a9\7(\2\2\u00a9\u00aa\7(\2\2\u00aa(\3\2\2\2\u00ab\u00ac"+
		"\7>\2\2\u00ac*\3\2\2\2\u00ad\u00ae\7>\2\2\u00ae\u00af\7?\2\2\u00af,\3"+
		"\2\2\2\u00b0\u00b1\7@\2\2\u00b1.\3\2\2\2\u00b2\u00b3\7@\2\2\u00b3\u00b4"+
		"\7?\2\2\u00b4\60\3\2\2\2\u00b5\u00b6\7?\2\2\u00b6\u00b7\7?\2\2\u00b7\62"+
		"\3\2\2\2\u00b8\u00b9\7#\2\2\u00b9\u00ba\7?\2\2\u00ba\64\3\2\2\2\u00bb"+
		"\u00bc\7-\2\2\u00bc\66\3\2\2\2\u00bd\u00be\7/\2\2\u00be8\3\2\2\2\u00bf"+
		"\u00c0\7,\2\2\u00c0:\3\2\2\2\u00c1\u00c2\7\61\2\2\u00c2<\3\2\2\2\u00c3"+
		"\u00c4\7\'\2\2\u00c4>\3\2\2\2\u00c5\u00c7\t\2\2\2\u00c6\u00c5\3\2\2\2"+
		"\u00c7\u00c8\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca"+
		"\3\2\2\2\u00ca\u00cb\b \2\2\u00cb@\3\2\2\2\u00cc\u00cd\7\61\2\2\u00cd"+
		"\u00ce\7,\2\2\u00ce\u00d2\3\2\2\2\u00cf\u00d1\13\2\2\2\u00d0\u00cf\3\2"+
		"\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00d5\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00d6\7,\2\2\u00d6\u00d7\7\61"+
		"\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\b!\2\2\u00d9B\3\2\2\2\u00da\u00db"+
		"\7\61\2\2\u00db\u00dc\7\61\2\2\u00dc\u00e0\3\2\2\2\u00dd\u00df\n\3\2\2"+
		"\u00de\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1"+
		"\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e4\b\"\2\2\u00e4"+
		"D\3\2\2\2\u00e5\u00e7\t\4\2\2\u00e6\u00e5\3\2\2\2\u00e7F\3\2\2\2\u00e8"+
		"\u00e9\t\5\2\2\u00e9H\3\2\2\2\u00ea\u00eb\t\6\2\2\u00ebJ\3\2\2\2\u00ec"+
		"\u00ee\t\7\2\2\u00ed\u00ec\3\2\2\2\u00eeL\3\2\2\2\u00ef\u00f0\t\b\2\2"+
		"\u00f0N\3\2\2\2\u00f1\u00f2\4\62\63\2\u00f2P\3\2\2\2\u00f3\u00f4\7\13"+
		"\2\2\u00f4R\3\2\2\2\u00f5\u00f6\n\t\2\2\u00f6T\3\2\2\2\u00f7\u00fc\5E"+
		"#\2\u00f8\u00fb\5E#\2\u00f9\u00fb\5I%\2\u00fa\u00f8\3\2\2\2\u00fa\u00f9"+
		"\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd"+
		"V\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0103\7$\2\2\u0100\u0102\5S*\2\u0101"+
		"\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0107\7$\2\2\u0107"+
		"X\3\2\2\2\u0108\u0111\7\62\2\2\u0109\u010d\5G$\2\u010a\u010c\5I%\2\u010b"+
		"\u010a\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2"+
		"\2\2\u010e\u0111\3\2\2\2\u010f\u010d\3\2\2\2\u0110\u0108\3\2\2\2\u0110"+
		"\u0109\3\2\2\2\u0111Z\3\2\2\2\r\2\u00c8\u00d2\u00e0\u00e6\u00ed\u00fa"+
		"\u00fc\u0103\u010d\u0110\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}