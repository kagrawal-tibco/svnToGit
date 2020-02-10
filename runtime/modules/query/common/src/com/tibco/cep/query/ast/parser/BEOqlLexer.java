// $ANTLR 3.2 Sep 23, 2009 12:02:23 Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g 2012-05-07 17:57:20
package com.tibco.cep.query.ast.parser;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class BEOqlLexer extends Lexer {
    public static final int TOK_CONCEPT=142;
    public static final int TOK_AMPERSAND=95;
    public static final int TOK_NE=89;
    public static final int TOK_DISTINCT=100;
    public static final int EXISTS_CLAUSE=25;
    public static final int TOK_CONCAT=81;
    public static final int TOK_TIME=154;
    public static final int SORT_CRITERION=61;
    public static final int TOK_TIME_HOURS=170;
    public static final int EOF=-1;
    public static final int TOK_NOT=125;
    public static final int TOK_TIME_MILLISECONDS=167;
    public static final int NewLine=198;
    public static final int STRING_LITERAL=68;
    public static final int BLOCK_EXPRESSION=35;
    public static final int TOK_EXACT_NUMERIC_LITERAL=195;
    public static final int CharLiteral=192;
    public static final int TOK_DEFINED=138;
    public static final int TOK_SEMIC=75;
    public static final int TOK_POLICY=159;
    public static final int TOK_STAR=86;
    public static final int TOK_FROM=101;
    public static final int TOK_LPAREN=73;
    public static final int TOK_HASH=96;
    public static final int TOK_OFFSET=164;
    public static final int ITERATOR_DEF=9;
    public static final int DateTimeLiteral=191;
    public static final int ASTERISK_CLAUSE=23;
    public static final int TOK_ALL=116;
    public static final int IntegerTypeSuffix=178;
    public static final int NameFirstCharacter=173;
    public static final int CAST_OP=19;
    public static final int FIELD_LIST=18;
    public static final int OR_EXPRESSION=30;
    public static final int TOK_BETWEEN=119;
    public static final int TOK_EXISTS=117;
    public static final int TOK_APPROXIMATE_NUMERIC_LITERAL=194;
    public static final int TOK_DOUBLE_QUOTE=97;
    public static final int UNARY_MINUS=70;
    public static final int NULL_LITERAL=65;
    public static final int TOK_ORDER=110;
    public static final int TOK_WHERE=106;
    public static final int FOR_ALL_CLAUSE=24;
    public static final int WS=197;
    public static final int TIME_WINDOW=52;
    public static final int TOK_PLUS=83;
    public static final int OCTALDIGIT=186;
    public static final int TOK_COMMA=74;
    public static final int TOK_INTERSECT=122;
    public static final int ALIAS=21;
    public static final int TOK_UNION=120;
    public static final int TOK_SELECT=102;
    public static final int BIND_VARIABLE_EXPRESSION=34;
    public static final int TOK_OR=113;
    public static final int TOK_LIKE=118;
    public static final int LIMIT_CLAUSE=59;
    public static final int TOK_MAX=133;
    public static final int TOK_CHAR=149;
    public static final int SLIDING_WINDOW=53;
    public static final int CAST_EXPRESSION=29;
    public static final int TUMBLING_WINDOW=54;
    public static final int PATHFUNCTION_EXPRESSION=32;
    public static final int TOK_DOUBLE=148;
    public static final int TOK_DELETE=103;
    public static final int TOK_DATETIME=152;
    public static final int HexDigit=172;
    public static final int AND_EXPRESSION=31;
    public static final int UNARY_EXPRESSION=37;
    public static final int TOK_AS=105;
    public static final int WHERE_CLAUSE=8;
    public static final int TOK_AT=80;
    public static final int TOK_TIMESTAMP=155;
    public static final int PROJECTION_ATTRIBUTES=10;
    public static final int HAVING_CLAUSE=14;
    public static final int TOK_EMIT=158;
    public static final int TOK_TRUE=140;
    public static final int BETWEEN_CLAUSE=27;
    public static final int ARRAY_INDEX=16;
    public static final int TOK_AVG=134;
    public static final int TOK_MOD=123;
    public static final int TOK_EVENT=144;
    public static final int ACCEPT_NEW=45;
    public static final int SCOPE_IDENTIFIER=63;
    public static final int ACCEPT_CLAUSE=44;
    public static final int MultiLineComment=201;
    public static final int HexLiteral=181;
    public static final int TOK_BY=108;
    public static final int TOK_SUM=131;
    public static final int TOK_RBRACK=93;
    public static final int TOK_UNIQUE=130;
    public static final int TOK_ACCEPT=157;
    public static final int DIGIT_OCTAL_RANGE=183;
    public static final int TOK_FALSE=141;
    public static final int StringLiteral=193;
    public static final int BOOLEAN_LITERAL=66;
    public static final int TOK_NEW=165;
    public static final int CHAR_LITERAL=67;
    public static final int TOK_UNDEFINED=137;
    public static final int POLICY_CLAUSE=50;
    public static final int DIGIT_ZERO=182;
    public static final int PROJECTION=12;
    public static final int IN_CLAUSE=22;
    public static final int TOK_EXCEPT=121;
    public static final int DELETE_EXPR=6;
    public static final int OctalEscape=175;
    public static final int UNARY_OP=38;
    public static final int STREAM_DEF=42;
    public static final int TOK_MAINTAIN=160;
    public static final int FloatTypeSuffix=180;
    public static final int OctalLiteral=190;
    public static final int ORDER_CLAUSE=7;
    public static final int TOK_INDIRECT=79;
    public static final int POLICY_BY_CLAUSE=51;
    public static final int TOK_PURGE=162;
    public static final int ACCEPT_ALL=46;
    public static final int TOK_NULL=139;
    public static final int TOK_ENTITY=143;
    public static final int TOK_GROUP=107;
    public static final int LITERALS=60;
    public static final int TOK_FIRST=126;
    public static final int PATH_EXPRESSION=28;
    public static final int EMIT_CLAUSE=47;
    public static final int Identifier=196;
    public static final int DIGIT_FULL_RANGE=185;
    public static final int TOK_EQ=82;
    public static final int TOK_DATE=153;
    public static final int PURGE_CLAUSE=56;
    public static final int WINDOW_DEF=43;
    public static final int INDEX_SINGLE=39;
    public static final int TOK_WHEN=163;
    public static final int SORT_DIRECTION=62;
    public static final int CommentLine=199;
    public static final int INDEX_MULTIPLE=40;
    public static final int TOK_SLASH=85;
    public static final int TOK_ASC=111;
    public static final int LIKE_CLAUSE=26;
    public static final int ARG_LIST=17;
    public static final int GROUP_COLUMN=15;
    public static final int TOK_COUNT=135;
    public static final int DIGIT_DECIMAL_RANGE=184;
    public static final int TOK_DESC=112;
    public static final int TOK_USING=161;
    public static final int TOK_MIN=132;
    public static final int RANGE_EXPRESSION=41;
    public static final int TOK_GT=91;
    public static final int TOK_ABS=124;
    public static final int TOK_STRING=150;
    public static final int FUNCTION_EXPRESSION=33;
    public static final int SELECT_EXPR=4;
    public static final int TOK_GE=88;
    public static final int TOK_TIME_MINUTES=169;
    public static final int NameCharacter=174;
    public static final int EscapeSequence=177;
    public static final int EMIT_DEAD=49;
    public static final int TOK_FOR=115;
    public static final int EMIT_NEW=48;
    public static final int DIGITS=188;
    public static final int Exponent=179;
    public static final int TOK_RCURLY=99;
    public static final int USING_CLAUSE=55;
    public static final int FROM_CLAUSE=5;
    public static final int TOK_DOT=76;
    public static final int TOK_IN=104;
    public static final int TOK_INT=146;
    public static final int ALIAS_CLAUSE=20;
    public static final int TOK_AND=114;
    public static final int TOK_PENDING_COUNT=136;
    public static final int TOK_COLON=78;
    public static final int IDENTIFIER=71;
    public static final int TOK_LBRACK=92;
    public static final int TOK_TIME_DAYS=171;
    public static final int DIGIT=187;
    public static final int NUMBER_LITERAL=64;
    public static final int PROJECTION_LIST=11;
    public static final int TOK_SLIDING=128;
    public static final int TOK_DOTDOT=77;
    public static final int TOK_HAVING=109;
    public static final int GROUP_CLAUSE=13;
    public static final int TOK_LT=90;
    public static final int PURGE_WHEN_CLAUSE=58;
    public static final int TOK_RPAREN=72;
    public static final int SUBSELECT_EXPRESSION=36;
    public static final int TOK_TIME_SECONDS=168;
    public static final int TOK_LCURLY=98;
    public static final int TOK_LONG=147;
    public static final int DATETIME_LITERAL=69;
    public static final int TOK_BOOLEAN=151;
    public static final int DecimalLiteral=189;
    public static final int TOK_LE=87;
    public static final int TOK_DOLLAR=94;
    public static final int TOK_OBJECT=145;
    public static final int TOK_LIMIT=156;
    public static final int TOK_LAST=127;
    public static final int UnicodeEscape=176;
    public static final int SLComment=200;
    public static final int TOK_MINUS=84;
    public static final int TOK_DEAD=166;
    public static final int PURGE_FIRST_CLAUSE=57;
    public static final int TOK_TUMBLING=129;


              public String getErrorMessage(RecognitionException e, String[] tokenNames) {
                  return super.getErrorMessage(e, BEOqlParser.tokenNames);
              }

              protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException {
                  throw new MismatchedTokenException(ttype, input);
              }

              public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
                 throw e;
              }

    	  @Override
    	  public void reportError(RecognitionException e) {
    	    throw new RuntimeException(e);
    	  }

    	  public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
                throw new RuntimeException(e);
              }

    	  public void recover(org.antlr.runtime.RecognitionException re) {
      	    throw new RuntimeException(re);
    	  }

    	  /**
    	   * Refer to http://www.antlr.org/wiki/pages/viewpage.action?pageId=5341217 for more information.
    	   */
    	  public Token nextToken() {
    		while (true) {
    			state.token = null;
    			state.channel = Token.DEFAULT_CHANNEL;
    			state.tokenStartCharIndex = input.index();
    			state.tokenStartCharPositionInLine = input.getCharPositionInLine();
    			state.tokenStartLine = input.getLine();
    			state.text = null;
    			if ( input.LA(1)==CharStream.EOF ) {
    				return Token.EOF_TOKEN;
    			}
    			try {
    				mTokens();
    				if ( state.token==null ) {
    					emit();
    				}
    				else if ( state.token==Token.SKIP_TOKEN ) {
    					continue;
    				}
    				return state.token;
    			}
    			catch (RecognitionException re) {
    				throw new RuntimeException(re); // or throw Error
    			}
    		}
    	  }

    	

    // delegates
    // delegators

    public BEOqlLexer() {;} 
    public BEOqlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public BEOqlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g"; }

    // $ANTLR start "TOK_RPAREN"
    public final void mTOK_RPAREN() throws RecognitionException {
        try {
            int _type = TOK_RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:250:27: ( ')' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:250:36: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_RPAREN"

    // $ANTLR start "TOK_LPAREN"
    public final void mTOK_LPAREN() throws RecognitionException {
        try {
            int _type = TOK_LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:251:27: ( '(' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:251:36: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LPAREN"

    // $ANTLR start "TOK_COMMA"
    public final void mTOK_COMMA() throws RecognitionException {
        try {
            int _type = TOK_COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:252:27: ( ',' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:252:36: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_COMMA"

    // $ANTLR start "TOK_SEMIC"
    public final void mTOK_SEMIC() throws RecognitionException {
        try {
            int _type = TOK_SEMIC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:253:27: ( ';' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:253:36: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SEMIC"

    // $ANTLR start "TOK_DOT"
    public final void mTOK_DOT() throws RecognitionException {
        try {
            int _type = TOK_DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:254:18: ( '.' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:254:27: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOT"

    // $ANTLR start "TOK_DOTDOT"
    public final void mTOK_DOTDOT() throws RecognitionException {
        try {
            int _type = TOK_DOTDOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:255:17: ( TOK_DOT TOK_DOT )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:255:33: TOK_DOT TOK_DOT
            {
            mTOK_DOT(); 
            mTOK_DOT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOTDOT"

    // $ANTLR start "TOK_COLON"
    public final void mTOK_COLON() throws RecognitionException {
        try {
            int _type = TOK_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:256:27: ( ':' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:256:36: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_COLON"

    // $ANTLR start "TOK_INDIRECT"
    public final void mTOK_INDIRECT() throws RecognitionException {
        try {
            int _type = TOK_INDIRECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:257:27: ( '-' '>' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:257:36: '-' '>'
            {
            match('-'); 
            match('>'); 
             _type = TOK_DOT;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_INDIRECT"

    // $ANTLR start "TOK_AT"
    public final void mTOK_AT() throws RecognitionException {
        try {
            int _type = TOK_AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:258:10: ( '@' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:258:19: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AT"

    // $ANTLR start "TOK_CONCAT"
    public final void mTOK_CONCAT() throws RecognitionException {
        try {
            int _type = TOK_CONCAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:259:27: ( '|' '|' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:259:36: '|' '|'
            {
            match('|'); 
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_CONCAT"

    // $ANTLR start "TOK_EQ"
    public final void mTOK_EQ() throws RecognitionException {
        try {
            int _type = TOK_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:260:17: ( '=' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:260:26: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EQ"

    // $ANTLR start "TOK_PLUS"
    public final void mTOK_PLUS() throws RecognitionException {
        try {
            int _type = TOK_PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:261:27: ( '+' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:261:36: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_PLUS"

    // $ANTLR start "TOK_MINUS"
    public final void mTOK_MINUS() throws RecognitionException {
        try {
            int _type = TOK_MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:262:27: ( '-' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:262:36: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MINUS"

    // $ANTLR start "TOK_SLASH"
    public final void mTOK_SLASH() throws RecognitionException {
        try {
            int _type = TOK_SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:263:27: ( '/' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:263:36: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SLASH"

    // $ANTLR start "TOK_STAR"
    public final void mTOK_STAR() throws RecognitionException {
        try {
            int _type = TOK_STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:264:27: ( '*' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:264:36: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_STAR"

    // $ANTLR start "TOK_LE"
    public final void mTOK_LE() throws RecognitionException {
        try {
            int _type = TOK_LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:265:27: ( '<=' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:265:36: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LE"

    // $ANTLR start "TOK_GE"
    public final void mTOK_GE() throws RecognitionException {
        try {
            int _type = TOK_GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:266:27: ( '>=' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:266:36: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_GE"

    // $ANTLR start "TOK_NE"
    public final void mTOK_NE() throws RecognitionException {
        try {
            int _type = TOK_NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:267:27: ( '<>' | '!=' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='<') ) {
                alt1=1;
            }
            else if ( (LA1_0=='!') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:267:36: '<>'
                    {
                    match("<>"); 


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:267:41: '!='
                    {
                    match("!="); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NE"

    // $ANTLR start "TOK_LT"
    public final void mTOK_LT() throws RecognitionException {
        try {
            int _type = TOK_LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:269:27: ( '<' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:269:36: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LT"

    // $ANTLR start "TOK_GT"
    public final void mTOK_GT() throws RecognitionException {
        try {
            int _type = TOK_GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:270:27: ( '>' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:270:36: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_GT"

    // $ANTLR start "TOK_LBRACK"
    public final void mTOK_LBRACK() throws RecognitionException {
        try {
            int _type = TOK_LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:271:27: ( '[' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:271:36: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LBRACK"

    // $ANTLR start "TOK_RBRACK"
    public final void mTOK_RBRACK() throws RecognitionException {
        try {
            int _type = TOK_RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:272:27: ( ']' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:272:36: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_RBRACK"

    // $ANTLR start "TOK_DOLLAR"
    public final void mTOK_DOLLAR() throws RecognitionException {
        try {
            int _type = TOK_DOLLAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:273:13: ( '$' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:273:22: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOLLAR"

    // $ANTLR start "TOK_AMPERSAND"
    public final void mTOK_AMPERSAND() throws RecognitionException {
        try {
            int _type = TOK_AMPERSAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:274:27: ( '&' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:274:43: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AMPERSAND"

    // $ANTLR start "TOK_HASH"
    public final void mTOK_HASH() throws RecognitionException {
        try {
            int _type = TOK_HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:275:27: ( '#' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:275:43: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_HASH"

    // $ANTLR start "TOK_DOUBLE_QUOTE"
    public final void mTOK_DOUBLE_QUOTE() throws RecognitionException {
        try {
            int _type = TOK_DOUBLE_QUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:276:19: ( '\"' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:276:22: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOUBLE_QUOTE"

    // $ANTLR start "TOK_LCURLY"
    public final void mTOK_LCURLY() throws RecognitionException {
        try {
            int _type = TOK_LCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:277:13: ( '{' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:277:29: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LCURLY"

    // $ANTLR start "TOK_RCURLY"
    public final void mTOK_RCURLY() throws RecognitionException {
        try {
            int _type = TOK_RCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:278:13: ( '}' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:278:29: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_RCURLY"

    // $ANTLR start "TOK_DISTINCT"
    public final void mTOK_DISTINCT() throws RecognitionException {
        try {
            int _type = TOK_DISTINCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:279:15: ( ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:279:18: ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DISTINCT"

    // $ANTLR start "TOK_FROM"
    public final void mTOK_FROM() throws RecognitionException {
        try {
            int _type = TOK_FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:280:12: ( ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:280:15: ( 'F' | 'f' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'M' | 'm' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_FROM"

    // $ANTLR start "TOK_SELECT"
    public final void mTOK_SELECT() throws RecognitionException {
        try {
            int _type = TOK_SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:281:13: ( ( 'S' | 's' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:281:16: ( 'S' | 's' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SELECT"

    // $ANTLR start "TOK_DELETE"
    public final void mTOK_DELETE() throws RecognitionException {
        try {
            int _type = TOK_DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:282:13: ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:282:16: ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DELETE"

    // $ANTLR start "TOK_IN"
    public final void mTOK_IN() throws RecognitionException {
        try {
            int _type = TOK_IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:283:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:283:13: ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_IN"

    // $ANTLR start "TOK_AS"
    public final void mTOK_AS() throws RecognitionException {
        try {
            int _type = TOK_AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:284:10: ( ( 'A' | 'a' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:284:13: ( 'A' | 'a' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AS"

    // $ANTLR start "TOK_WHERE"
    public final void mTOK_WHERE() throws RecognitionException {
        try {
            int _type = TOK_WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:285:13: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:285:16: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_WHERE"

    // $ANTLR start "TOK_GROUP"
    public final void mTOK_GROUP() throws RecognitionException {
        try {
            int _type = TOK_GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:286:13: ( ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'P' | 'p' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:286:16: ( 'G' | 'g' ) ( 'R' | 'r' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_GROUP"

    // $ANTLR start "TOK_BY"
    public final void mTOK_BY() throws RecognitionException {
        try {
            int _type = TOK_BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:287:10: ( ( 'B' | 'b' ) ( 'Y' | 'y' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:287:13: ( 'B' | 'b' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_BY"

    // $ANTLR start "TOK_HAVING"
    public final void mTOK_HAVING() throws RecognitionException {
        try {
            int _type = TOK_HAVING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:288:13: ( ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'V' | 'v' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:288:16: ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'V' | 'v' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_HAVING"

    // $ANTLR start "TOK_ORDER"
    public final void mTOK_ORDER() throws RecognitionException {
        try {
            int _type = TOK_ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:289:13: ( ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'R' | 'r' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:289:16: ( 'O' | 'o' ) ( 'R' | 'r' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ORDER"

    // $ANTLR start "TOK_ASC"
    public final void mTOK_ASC() throws RecognitionException {
        try {
            int _type = TOK_ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:290:11: ( ( 'A' | 'a' ) ( 'S' | 's' ) ( 'C' | 'c' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:290:14: ( 'A' | 'a' ) ( 'S' | 's' ) ( 'C' | 'c' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ASC"

    // $ANTLR start "TOK_DESC"
    public final void mTOK_DESC() throws RecognitionException {
        try {
            int _type = TOK_DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:291:12: ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:291:15: ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'C' | 'c' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DESC"

    // $ANTLR start "TOK_OR"
    public final void mTOK_OR() throws RecognitionException {
        try {
            int _type = TOK_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:292:10: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:292:13: ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_OR"

    // $ANTLR start "TOK_AND"
    public final void mTOK_AND() throws RecognitionException {
        try {
            int _type = TOK_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:293:11: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:293:14: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AND"

    // $ANTLR start "TOK_FOR"
    public final void mTOK_FOR() throws RecognitionException {
        try {
            int _type = TOK_FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:294:11: ( ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:294:14: ( 'F' | 'f' ) ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_FOR"

    // $ANTLR start "TOK_ALL"
    public final void mTOK_ALL() throws RecognitionException {
        try {
            int _type = TOK_ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:295:11: ( ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:295:14: ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ALL"

    // $ANTLR start "TOK_EXISTS"
    public final void mTOK_EXISTS() throws RecognitionException {
        try {
            int _type = TOK_EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:296:13: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:296:16: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EXISTS"

    // $ANTLR start "TOK_LIKE"
    public final void mTOK_LIKE() throws RecognitionException {
        try {
            int _type = TOK_LIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:297:12: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'K' | 'k' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:297:15: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'K' | 'k' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LIKE"

    // $ANTLR start "TOK_BETWEEN"
    public final void mTOK_BETWEEN() throws RecognitionException {
        try {
            int _type = TOK_BETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:298:14: ( ( 'B' | 'b' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'W' | 'w' ) ( 'E' | 'e' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:298:17: ( 'B' | 'b' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'W' | 'w' ) ( 'E' | 'e' ) ( 'E' | 'e' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_BETWEEN"

    // $ANTLR start "TOK_UNION"
    public final void mTOK_UNION() throws RecognitionException {
        try {
            int _type = TOK_UNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:299:13: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:299:16: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'O' | 'o' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_UNION"

    // $ANTLR start "TOK_EXCEPT"
    public final void mTOK_EXCEPT() throws RecognitionException {
        try {
            int _type = TOK_EXCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:300:13: ( ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:300:16: ( 'E' | 'e' ) ( 'X' | 'x' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EXCEPT"

    // $ANTLR start "TOK_INTERSECT"
    public final void mTOK_INTERSECT() throws RecognitionException {
        try {
            int _type = TOK_INTERSECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:301:16: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:301:19: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'R' | 'r' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_INTERSECT"

    // $ANTLR start "TOK_MOD"
    public final void mTOK_MOD() throws RecognitionException {
        try {
            int _type = TOK_MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:302:11: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:302:14: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MOD"

    // $ANTLR start "TOK_ABS"
    public final void mTOK_ABS() throws RecognitionException {
        try {
            int _type = TOK_ABS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:303:11: ( ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:303:14: ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ABS"

    // $ANTLR start "TOK_NOT"
    public final void mTOK_NOT() throws RecognitionException {
        try {
            int _type = TOK_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:304:11: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:304:14: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOT"

    // $ANTLR start "TOK_FIRST"
    public final void mTOK_FIRST() throws RecognitionException {
        try {
            int _type = TOK_FIRST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:305:13: ( ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'R' | 'r' ) ( 'S' | 's' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:305:16: ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'R' | 'r' ) ( 'S' | 's' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_FIRST"

    // $ANTLR start "TOK_LAST"
    public final void mTOK_LAST() throws RecognitionException {
        try {
            int _type = TOK_LAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:306:12: ( ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:306:15: ( 'L' | 'l' ) ( 'A' | 'a' ) ( 'S' | 's' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LAST"

    // $ANTLR start "TOK_SLIDING"
    public final void mTOK_SLIDING() throws RecognitionException {
        try {
            int _type = TOK_SLIDING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:307:14: ( ( 'S' | 's' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:307:17: ( 'S' | 's' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SLIDING"

    // $ANTLR start "TOK_TUMBLING"
    public final void mTOK_TUMBLING() throws RecognitionException {
        try {
            int _type = TOK_TUMBLING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:308:15: ( ( 'T' | 't' ) ( 'U' | 'u' ) ( 'M' | 'm' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:308:18: ( 'T' | 't' ) ( 'U' | 'u' ) ( 'M' | 'm' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TUMBLING"

    // $ANTLR start "TOK_UNIQUE"
    public final void mTOK_UNIQUE() throws RecognitionException {
        try {
            int _type = TOK_UNIQUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:309:13: ( ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'Q' | 'q' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:309:16: ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'I' | 'i' ) ( 'Q' | 'q' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_UNIQUE"

    // $ANTLR start "TOK_SUM"
    public final void mTOK_SUM() throws RecognitionException {
        try {
            int _type = TOK_SUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:310:11: ( ( 'S' | 's' ) ( 'U' | 'u' ) ( 'M' | 'm' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:310:14: ( 'S' | 's' ) ( 'U' | 'u' ) ( 'M' | 'm' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SUM"

    // $ANTLR start "TOK_MIN"
    public final void mTOK_MIN() throws RecognitionException {
        try {
            int _type = TOK_MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:311:11: ( ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:311:14: ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MIN"

    // $ANTLR start "TOK_MAX"
    public final void mTOK_MAX() throws RecognitionException {
        try {
            int _type = TOK_MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:312:11: ( ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'X' | 'x' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:312:14: ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'X' | 'x' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MAX"

    // $ANTLR start "TOK_AVG"
    public final void mTOK_AVG() throws RecognitionException {
        try {
            int _type = TOK_AVG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:313:11: ( ( 'A' | 'a' ) ( 'V' | 'v' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:313:14: ( 'A' | 'a' ) ( 'V' | 'v' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AVG"

    // $ANTLR start "TOK_COUNT"
    public final void mTOK_COUNT() throws RecognitionException {
        try {
            int _type = TOK_COUNT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:314:13: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:314:16: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_COUNT"

    // $ANTLR start "TOK_PENDING_COUNT"
    public final void mTOK_PENDING_COUNT() throws RecognitionException {
        try {
            int _type = TOK_PENDING_COUNT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:315:20: ( ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( '_' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:315:23: ( 'P' | 'p' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) ( '_' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:315:86: ( '_' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:315:87: '_'
            {
            match('_'); 

            }

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_PENDING_COUNT"

    // $ANTLR start "TOK_UNDEFINED"
    public final void mTOK_UNDEFINED() throws RecognitionException {
        try {
            int _type = TOK_UNDEFINED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:316:16: ( ( 'I' | 'i' ) ( 'S' | 's' ) ( '_' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:316:19: ( 'I' | 'i' ) ( 'S' | 's' ) ( '_' ) ( 'U' | 'u' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:316:37: ( '_' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:316:38: '_'
            {
            match('_'); 

            }

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_UNDEFINED"

    // $ANTLR start "TOK_DEFINED"
    public final void mTOK_DEFINED() throws RecognitionException {
        try {
            int _type = TOK_DEFINED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:317:14: ( ( 'I' | 'i' ) ( 'S' | 's' ) ( '_' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'D' | 'd' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:317:17: ( 'I' | 'i' ) ( 'S' | 's' ) ( '_' ) ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'F' | 'f' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:317:35: ( '_' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:317:36: '_'
            {
            match('_'); 

            }

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DEFINED"

    // $ANTLR start "TOK_NULL"
    public final void mTOK_NULL() throws RecognitionException {
        try {
            int _type = TOK_NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:318:12: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:318:15: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NULL"

    // $ANTLR start "TOK_TRUE"
    public final void mTOK_TRUE() throws RecognitionException {
        try {
            int _type = TOK_TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:319:13: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:319:22: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             setText("TRUE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TRUE"

    // $ANTLR start "TOK_FALSE"
    public final void mTOK_FALSE() throws RecognitionException {
        try {
            int _type = TOK_FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:320:13: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:320:16: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             setText("FALSE"); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_FALSE"

    // $ANTLR start "TOK_CONCEPT"
    public final void mTOK_CONCEPT() throws RecognitionException {
        try {
            int _type = TOK_CONCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:321:14: ( ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:321:17: ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_CONCEPT"

    // $ANTLR start "TOK_ENTITY"
    public final void mTOK_ENTITY() throws RecognitionException {
        try {
            int _type = TOK_ENTITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:322:13: ( ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:322:16: ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'T' | 't' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ENTITY"

    // $ANTLR start "TOK_EVENT"
    public final void mTOK_EVENT() throws RecognitionException {
        try {
            int _type = TOK_EVENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:323:13: ( ( 'E' | 'e' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:323:16: ( 'E' | 'e' ) ( 'V' | 'v' ) ( 'E' | 'e' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EVENT"

    // $ANTLR start "TOK_OBJECT"
    public final void mTOK_OBJECT() throws RecognitionException {
        try {
            int _type = TOK_OBJECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:324:13: ( ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'J' | 'j' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:324:16: ( 'O' | 'o' ) ( 'B' | 'b' ) ( 'J' | 'j' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_OBJECT"

    // $ANTLR start "TOK_INT"
    public final void mTOK_INT() throws RecognitionException {
        try {
            int _type = TOK_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:325:11: ( ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:325:14: ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_INT"

    // $ANTLR start "TOK_LONG"
    public final void mTOK_LONG() throws RecognitionException {
        try {
            int _type = TOK_LONG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:326:12: ( ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:326:15: ( 'L' | 'l' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LONG"

    // $ANTLR start "TOK_DOUBLE"
    public final void mTOK_DOUBLE() throws RecognitionException {
        try {
            int _type = TOK_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:327:13: ( ( 'D' | 'd' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:327:16: ( 'D' | 'd' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOUBLE"

    // $ANTLR start "TOK_CHAR"
    public final void mTOK_CHAR() throws RecognitionException {
        try {
            int _type = TOK_CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:328:12: ( ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:328:15: ( 'C' | 'c' ) ( 'H' | 'h' ) ( 'A' | 'a' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_CHAR"

    // $ANTLR start "TOK_STRING"
    public final void mTOK_STRING() throws RecognitionException {
        try {
            int _type = TOK_STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:329:13: ( ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:329:16: ( 'S' | 's' ) ( 'T' | 't' ) ( 'R' | 'r' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_STRING"

    // $ANTLR start "TOK_BOOLEAN"
    public final void mTOK_BOOLEAN() throws RecognitionException {
        try {
            int _type = TOK_BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:330:14: ( ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:330:17: ( 'B' | 'b' ) ( 'O' | 'o' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_BOOLEAN"

    // $ANTLR start "TOK_DATETIME"
    public final void mTOK_DATETIME() throws RecognitionException {
        try {
            int _type = TOK_DATETIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:331:15: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:331:18: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DATETIME"

    // $ANTLR start "TOK_DATE"
    public final void mTOK_DATE() throws RecognitionException {
        try {
            int _type = TOK_DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:332:12: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:332:15: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'T' | 't' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DATE"

    // $ANTLR start "TOK_TIME"
    public final void mTOK_TIME() throws RecognitionException {
        try {
            int _type = TOK_TIME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:333:12: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:333:15: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME"

    // $ANTLR start "TOK_TIMESTAMP"
    public final void mTOK_TIMESTAMP() throws RecognitionException {
        try {
            int _type = TOK_TIMESTAMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:334:16: ( ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'P' | 'p' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:334:19: ( 'T' | 't' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'E' | 'e' ) ( 'S' | 's' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'M' | 'm' ) ( 'P' | 'p' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIMESTAMP"

    // $ANTLR start "TOK_LIMIT"
    public final void mTOK_LIMIT() throws RecognitionException {
        try {
            int _type = TOK_LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:336:13: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:336:16: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LIMIT"

    // $ANTLR start "TOK_ACCEPT"
    public final void mTOK_ACCEPT() throws RecognitionException {
        try {
            int _type = TOK_ACCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:337:13: ( ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:337:21: ( 'A' | 'a' ) ( 'C' | 'c' ) ( 'C' | 'c' ) ( 'E' | 'e' ) ( 'P' | 'p' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ACCEPT"

    // $ANTLR start "TOK_EMIT"
    public final void mTOK_EMIT() throws RecognitionException {
        try {
            int _type = TOK_EMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:338:12: ( ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:338:28: ( 'E' | 'e' ) ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EMIT"

    // $ANTLR start "TOK_POLICY"
    public final void mTOK_POLICY() throws RecognitionException {
        try {
            int _type = TOK_POLICY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:339:13: ( ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' ) ( 'Y' | 'y' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:339:29: ( 'P' | 'p' ) ( 'O' | 'o' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'C' | 'c' ) ( 'Y' | 'y' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_POLICY"

    // $ANTLR start "TOK_MAINTAIN"
    public final void mTOK_MAINTAIN() throws RecognitionException {
        try {
            int _type = TOK_MAINTAIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:340:15: ( ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:340:31: ( 'M' | 'm' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'T' | 't' ) ( 'A' | 'a' ) ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MAINTAIN"

    // $ANTLR start "TOK_USING"
    public final void mTOK_USING() throws RecognitionException {
        try {
            int _type = TOK_USING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:341:13: ( ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:341:29: ( 'U' | 'u' ) ( 'S' | 's' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'G' | 'g' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_USING"

    // $ANTLR start "TOK_PURGE"
    public final void mTOK_PURGE() throws RecognitionException {
        try {
            int _type = TOK_PURGE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:342:13: ( ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'G' | 'g' ) ( 'E' | 'e' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:342:29: ( 'P' | 'p' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'G' | 'g' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_PURGE"

    // $ANTLR start "TOK_WHEN"
    public final void mTOK_WHEN() throws RecognitionException {
        try {
            int _type = TOK_WHEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:343:12: ( ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:343:28: ( 'W' | 'w' ) ( 'H' | 'h' ) ( 'E' | 'e' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_WHEN"

    // $ANTLR start "TOK_OFFSET"
    public final void mTOK_OFFSET() throws RecognitionException {
        try {
            int _type = TOK_OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:344:13: ( ( 'O' | 'o' ) ( 'F' | 'f' ) ( 'F' | 'f' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'T' | 't' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:344:23: ( 'O' | 'o' ) ( 'F' | 'f' ) ( 'F' | 'f' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_OFFSET"

    // $ANTLR start "TOK_NEW"
    public final void mTOK_NEW() throws RecognitionException {
        try {
            int _type = TOK_NEW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:345:11: ( ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'W' | 'w' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:345:27: ( 'N' | 'n' ) ( 'E' | 'e' ) ( 'W' | 'w' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NEW"

    // $ANTLR start "TOK_DEAD"
    public final void mTOK_DEAD() throws RecognitionException {
        try {
            int _type = TOK_DEAD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:346:12: ( ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:346:28: ( 'D' | 'd' ) ( 'E' | 'e' ) ( 'A' | 'a' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DEAD"

    // $ANTLR start "TOK_TIME_MILLISECONDS"
    public final void mTOK_TIME_MILLISECONDS() throws RecognitionException {
        try {
            int _type = TOK_TIME_MILLISECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:347:23: ( ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:347:26: ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'L' | 'l' ) ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME_MILLISECONDS"

    // $ANTLR start "TOK_TIME_SECONDS"
    public final void mTOK_TIME_SECONDS() throws RecognitionException {
        try {
            int _type = TOK_TIME_SECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:348:19: ( ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:348:22: ( 'S' | 's' ) ( 'E' | 'e' ) ( 'C' | 'c' ) ( 'O' | 'o' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME_SECONDS"

    // $ANTLR start "TOK_TIME_MINUTES"
    public final void mTOK_TIME_MINUTES() throws RecognitionException {
        try {
            int _type = TOK_TIME_MINUTES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:349:19: ( ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:349:22: ( 'M' | 'm' ) ( 'I' | 'i' ) ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'T' | 't' ) ( 'E' | 'e' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME_MINUTES"

    // $ANTLR start "TOK_TIME_HOURS"
    public final void mTOK_TIME_HOURS() throws RecognitionException {
        try {
            int _type = TOK_TIME_HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:350:17: ( ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:350:20: ( 'H' | 'h' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'R' | 'r' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME_HOURS"

    // $ANTLR start "TOK_TIME_DAYS"
    public final void mTOK_TIME_DAYS() throws RecognitionException {
        try {
            int _type = TOK_TIME_DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:351:16: ( ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) ( 'S' | 's' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:351:19: ( 'D' | 'd' ) ( 'A' | 'a' ) ( 'Y' | 'y' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TIME_DAYS"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:357:16: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:357:18: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "NameFirstCharacter"
    public final void mNameFirstCharacter() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:361:25: ( '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' | '\\uff61' .. '\\uff9f' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFF9F') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NameFirstCharacter"

    // $ANTLR start "NameCharacter"
    public final void mNameCharacter() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:384:20: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' | TOK_DOLLAR )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NameCharacter"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:11: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\\') ) {
                int LA2_1 = input.LA(2);

                if ( ((LA2_1>='0' && LA2_1<='3')) ) {
                    int LA2_2 = input.LA(3);

                    if ( ((LA2_2>='0' && LA2_2<='7')) ) {
                        int LA2_5 = input.LA(4);

                        if ( ((LA2_5>='0' && LA2_5<='7')) ) {
                            alt2=1;
                        }
                        else {
                            alt2=2;}
                    }
                    else {
                        alt2=3;}
                }
                else if ( ((LA2_1>='4' && LA2_1<='7')) ) {
                    int LA2_3 = input.LA(3);

                    if ( ((LA2_3>='0' && LA2_3<='7')) ) {
                        alt2=2;
                    }
                    else {
                        alt2=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:15: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:20: ( '0' .. '3' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:21: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:31: ( '0' .. '7' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:32: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:42: ( '0' .. '7' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:409:43: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:410:15: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:410:20: ( '0' .. '7' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:410:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:410:31: ( '0' .. '7' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:410:32: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:411:15: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:411:20: ( '0' .. '7' )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:411:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:416:11: ( ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:416:15: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:416:15: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:416:16: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:421:11: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | '\\\\' 'Q' ( options {greedy=false; } : . )* '\\\\' 'E' | UnicodeEscape | OctalEscape )
            int alt4=4;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt4=1;
                    }
                    break;
                case 'Q':
                    {
                    alt4=2;
                    }
                    break;
                case 'u':
                    {
                    alt4=3;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt4=4;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:421:15: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:422:15: '\\\\' 'Q' ( options {greedy=false; } : . )* '\\\\' 'E'
                    {
                    match('\\'); 
                    match('Q'); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:422:24: ( options {greedy=false; } : . )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0=='\\') ) {
                            int LA3_1 = input.LA(2);

                            if ( (LA3_1=='E') ) {
                                alt3=2;
                            }
                            else if ( ((LA3_1>='\u0000' && LA3_1<='D')||(LA3_1>='F' && LA3_1<='\uFFFF')) ) {
                                alt3=1;
                            }


                        }
                        else if ( ((LA3_0>='\u0000' && LA3_0<='[')||(LA3_0>=']' && LA3_0<='\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:422:52: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    match('\\'); 
                    match('E'); 

                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:423:15: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:424:15: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:428:27: ( ( 'l' | 'L' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:428:29: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IntegerTypeSuffix"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:431:18: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:431:20: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:431:30: ( '+' | '-' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='+'||LA5_0=='-') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:431:41: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:431:42: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "FloatTypeSuffix"
    public final void mFloatTypeSuffix() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:434:25: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:434:27: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FloatTypeSuffix"

    // $ANTLR start "HexLiteral"
    public final void mHexLiteral() throws RecognitionException {
        try {
            int _type = HexLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:28: ( HexDigit )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='F')||(LA7_0>='a' && LA7_0<='f')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:28: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:38: ( IntegerTypeSuffix )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='L'||LA8_0=='l') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:443:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HexLiteral"

    // $ANTLR start "DIGIT_ZERO"
    public final void mDIGIT_ZERO() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:446:18: ( '0' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:446:20: '0'
            {
            match('0'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_ZERO"

    // $ANTLR start "DIGIT_OCTAL_RANGE"
    public final void mDIGIT_OCTAL_RANGE() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:449:25: ( '1' .. '7' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:449:27: '1' .. '7'
            {
            matchRange('1','7'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_OCTAL_RANGE"

    // $ANTLR start "DIGIT_DECIMAL_RANGE"
    public final void mDIGIT_DECIMAL_RANGE() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:452:27: ( DIGIT_OCTAL_RANGE | '8' .. '9' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( (input.LA(1)>='1' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_DECIMAL_RANGE"

    // $ANTLR start "DIGIT_FULL_RANGE"
    public final void mDIGIT_FULL_RANGE() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:455:24: ( DIGIT_ZERO | DIGIT_DECIMAL_RANGE )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_FULL_RANGE"

    // $ANTLR start "OCTALDIGIT"
    public final void mOCTALDIGIT() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:458:18: ( DIGIT_ZERO | DIGIT_OCTAL_RANGE )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='7') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "OCTALDIGIT"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:461:13: ( DIGIT_OCTAL_RANGE | '8' | '9' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            if ( (input.LA(1)>='1' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DIGITS"
    public final void mDIGITS() throws RecognitionException {
        try {
            int _type = DIGITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:463:8: ( DIGIT_ZERO | ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='0') ) {
                alt10=1;
            }
            else if ( ((LA10_0>='1' && LA10_0<='9')) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:463:10: DIGIT_ZERO
                    {
                    mDIGIT_ZERO(); 

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:464:19: ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* )
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:464:19: ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:464:20: DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )*
                    {
                    mDIGIT_DECIMAL_RANGE(); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:464:40: ( DIGIT_FULL_RANGE )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:464:40: DIGIT_FULL_RANGE
                    	    {
                    	    mDIGIT_FULL_RANGE(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIGITS"

    // $ANTLR start "DecimalLiteral"
    public final void mDecimalLiteral() throws RecognitionException {
        try {
            int _type = DecimalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:466:16: ( DIGIT ( '0' | DIGIT )* ( IntegerTypeSuffix )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:466:18: DIGIT ( '0' | DIGIT )* ( IntegerTypeSuffix )?
            {
            mDIGIT(); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:466:23: ( '0' | DIGIT )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:466:38: ( IntegerTypeSuffix )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='L'||LA12_0=='l') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:466:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DecimalLiteral"

    // $ANTLR start "OctalLiteral"
    public final void mOctalLiteral() throws RecognitionException {
        try {
            int _type = OctalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:14: ( '0' ( OCTALDIGIT )+ ( IntegerTypeSuffix )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:16: '0' ( OCTALDIGIT )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:20: ( OCTALDIGIT )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='7')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:20: OCTALDIGIT
            	    {
            	    mOCTALDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:32: ( IntegerTypeSuffix )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='L'||LA14_0=='l') ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:468:32: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OctalLiteral"

    // $ANTLR start "DateTimeLiteral"
    public final void mDateTimeLiteral() throws RecognitionException {
        try {
            int _type = DateTimeLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:470:17: ( DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE 'T' DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_DOT DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE ( TOK_PLUS | TOK_MINUS ) DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:471:10: DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE 'T' DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_DOT DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE ( TOK_PLUS | TOK_MINUS ) DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
            {
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_MINUS(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_MINUS(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            match('T'); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_COLON(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_COLON(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_DOT(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DateTimeLiteral"

    // $ANTLR start "CharLiteral"
    public final void mCharLiteral() throws RecognitionException {
        try {
            int _type = CharLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:489:9: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:489:13: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:489:18: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\\') ) {
                alt15=1;
            }
            else if ( ((LA15_0>='\u0000' && LA15_0<='&')||(LA15_0>='(' && LA15_0<='[')||(LA15_0>=']' && LA15_0<='\uFFFF')) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:489:20: EscapeSequence
                    {
                    mEscapeSequence(); 

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:489:37: ~ ( '\\'' | '\\\\' )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('\''); 
             setText(getText().substring(1, getText().length()-1)); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CharLiteral"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:493:9: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:493:12: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:493:16: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop16:
            do {
                int alt16=3;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='\\') ) {
                    alt16=1;
                }
                else if ( ((LA16_0>='\u0000' && LA16_0<='!')||(LA16_0>='#' && LA16_0<='[')||(LA16_0>=']' && LA16_0<='\uFFFF')) ) {
                    alt16=2;
                }


                switch (alt16) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:493:18: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:493:35: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            match('\"'); 
             setText(getText().substring(1, getText().length()-1)); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "TOK_APPROXIMATE_NUMERIC_LITERAL"
    public final void mTOK_APPROXIMATE_NUMERIC_LITERAL() throws RecognitionException {
        try {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:511:15: ( ( 'e' | 'E' ) ( '+' | '-' )? DIGITS )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:511:23: ( 'e' | 'E' ) ( '+' | '-' )? DIGITS
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:511:33: ( '+' | '-' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='+'||LA17_0=='-') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            mDIGITS(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "TOK_APPROXIMATE_NUMERIC_LITERAL"

    // $ANTLR start "TOK_EXACT_NUMERIC_LITERAL"
    public final void mTOK_EXACT_NUMERIC_LITERAL() throws RecognitionException {
        try {
            int _type = TOK_EXACT_NUMERIC_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:516:27: ( '.' DIGITS ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | DIGITS ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )? )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='.') ) {
                alt22=1;
            }
            else if ( ((LA22_0>='0' && LA22_0<='9')) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:518:19: '.' DIGITS ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    {
                    match('.'); 
                    mDIGITS(); 
                     _type = TOK_EXACT_NUMERIC_LITERAL; 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:522:19: ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='E'||LA18_0=='e') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:523:23: TOK_APPROXIMATE_NUMERIC_LITERAL
                            {
                            mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                             _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:527:19: DIGITS ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    {
                    mDIGITS(); 
                     _type = DIGIT; 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:531:19: ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    int alt21=3;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0=='.') ) {
                        alt21=1;
                    }
                    else if ( (LA21_0=='E'||LA21_0=='e') ) {
                        alt21=2;
                    }
                    switch (alt21) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:532:23: '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                            {
                            match('.'); 
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:533:23: ( DIGITS )*
                            loop19:
                            do {
                                int alt19=2;
                                int LA19_0 = input.LA(1);

                                if ( ((LA19_0>='0' && LA19_0<='9')) ) {
                                    alt19=1;
                                }


                                switch (alt19) {
                            	case 1 :
                            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:533:24: DIGITS
                            	    {
                            	    mDIGITS(); 

                            	    }
                            	    break;

                            	default :
                            	    break loop19;
                                }
                            } while (true);

                             _type = TOK_EXACT_NUMERIC_LITERAL; 
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:535:23: ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0=='E'||LA20_0=='e') ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:535:24: TOK_APPROXIMATE_NUMERIC_LITERAL
                                    {
                                    mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                                     _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:537:24: TOK_APPROXIMATE_NUMERIC_LITERAL
                            {
                            mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                             _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EXACT_NUMERIC_LITERAL"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:573:19: ( NameFirstCharacter ( NameFirstCharacter | NameCharacter )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:575:15: NameFirstCharacter ( NameFirstCharacter | NameCharacter )*
            {
            mNameFirstCharacter(); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:575:34: ( NameFirstCharacter | NameCharacter )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0=='$'||(LA23_0>='0' && LA23_0<='9')||(LA23_0>='A' && LA23_0<='Z')||LA23_0=='_'||(LA23_0>='a' && LA23_0<='z')||(LA23_0>='\u00C0' && LA23_0<='\u00D6')||(LA23_0>='\u00D8' && LA23_0<='\u00F6')||(LA23_0>='\u00F8' && LA23_0<='\u1FFF')||(LA23_0>='\u3040' && LA23_0<='\u318F')||(LA23_0>='\u3300' && LA23_0<='\u337F')||(LA23_0>='\u3400' && LA23_0<='\u3D2D')||(LA23_0>='\u4E00' && LA23_0<='\u9FFF')||(LA23_0>='\uF900' && LA23_0<='\uFAFF')||(LA23_0>='\uFF61' && LA23_0<='\uFF9F')) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFF9F') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:582:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:582:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NewLine"
    public final void mNewLine() throws RecognitionException {
        try {
            int _type = NewLine;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:586:9: ( ( '\\n' | '\\r\\n' | '\\r' ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:587:15: ( '\\n' | '\\r\\n' | '\\r' )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:587:15: ( '\\n' | '\\r\\n' | '\\r' )
            int alt24=3;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='\n') ) {
                alt24=1;
            }
            else if ( (LA24_0=='\r') ) {
                int LA24_2 = input.LA(2);

                if ( (LA24_2=='\n') ) {
                    alt24=2;
                }
                else {
                    alt24=3;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:588:16: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:589:19: '\\r\\n'
                    {
                    match("\r\n"); 


                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:590:19: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

             /* newline();*/ _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NewLine"

    // $ANTLR start "CommentLine"
    public final void mCommentLine() throws RecognitionException {
        try {
            int _type = CommentLine;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:597:13: ( '/' '/' (~ '\\n' )* ( '\\n' | EOF ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:598:15: '/' '/' (~ '\\n' )* ( '\\n' | EOF )
            {
            match('/'); 
            match('/'); 
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:599:15: (~ '\\n' )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>='\u0000' && LA25_0<='\t')||(LA25_0>='\u000B' && LA25_0<='\uFFFF')) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:600:19: ~ '\\n'
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:602:15: ( '\\n' | EOF )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0=='\n') ) {
                alt26=1;
            }
            else {
                alt26=2;}
            switch (alt26) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:602:16: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:602:23: EOF
                    {
                    match(EOF); 

                    }
                    break;

            }

             _channel=HIDDEN; setText(getText().substring(2, getText().length())); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CommentLine"

    // $ANTLR start "SLComment"
    public final void mSLComment() throws RecognitionException {
        try {
            int _type = SLComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:608:11: ( '--' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? | EOF ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:608:14: '--' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? | EOF )
            {
            match("--"); 

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:23: (~ ( '\\n' | '\\r' ) )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>='\u0000' && LA27_0<='\t')||(LA27_0>='\u000B' && LA27_0<='\f')||(LA27_0>='\u000E' && LA27_0<='\uFFFF')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:24: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:39: ( '\\n' | '\\r' ( '\\n' )? | EOF )
            int alt29=3;
            switch ( input.LA(1) ) {
            case '\n':
                {
                alt29=1;
                }
                break;
            case '\r':
                {
                alt29=2;
                }
                break;
            default:
                alt29=3;}

            switch (alt29) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:40: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:45: '\\r' ( '\\n' )?
                    {
                    match('\r'); 
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:49: ( '\\n' )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0=='\n') ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:50: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:609:57: EOF
                    {
                    match(EOF); 

                    }
                    break;

            }

             _channel=HIDDEN;setText(getText().substring(2, getText().length())); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLComment"

    // $ANTLR start "MultiLineComment"
    public final void mMultiLineComment() throws RecognitionException {
        try {
            int _type = MultiLineComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:619:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:619:15: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:619:20: ( options {greedy=false; } : . )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0=='*') ) {
                    int LA30_1 = input.LA(2);

                    if ( (LA30_1=='/') ) {
                        alt30=2;
                    }
                    else if ( ((LA30_1>='\u0000' && LA30_1<='.')||(LA30_1>='0' && LA30_1<='\uFFFF')) ) {
                        alt30=1;
                    }


                }
                else if ( ((LA30_0>='\u0000' && LA30_0<=')')||(LA30_0>='+' && LA30_0<='\uFFFF')) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:619:47: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MultiLineComment"

    public void mTokens() throws RecognitionException {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:8: ( TOK_RPAREN | TOK_LPAREN | TOK_COMMA | TOK_SEMIC | TOK_DOT | TOK_DOTDOT | TOK_COLON | TOK_INDIRECT | TOK_AT | TOK_CONCAT | TOK_EQ | TOK_PLUS | TOK_MINUS | TOK_SLASH | TOK_STAR | TOK_LE | TOK_GE | TOK_NE | TOK_LT | TOK_GT | TOK_LBRACK | TOK_RBRACK | TOK_DOLLAR | TOK_AMPERSAND | TOK_HASH | TOK_DOUBLE_QUOTE | TOK_LCURLY | TOK_RCURLY | TOK_DISTINCT | TOK_FROM | TOK_SELECT | TOK_DELETE | TOK_IN | TOK_AS | TOK_WHERE | TOK_GROUP | TOK_BY | TOK_HAVING | TOK_ORDER | TOK_ASC | TOK_DESC | TOK_OR | TOK_AND | TOK_FOR | TOK_ALL | TOK_EXISTS | TOK_LIKE | TOK_BETWEEN | TOK_UNION | TOK_EXCEPT | TOK_INTERSECT | TOK_MOD | TOK_ABS | TOK_NOT | TOK_FIRST | TOK_LAST | TOK_SLIDING | TOK_TUMBLING | TOK_UNIQUE | TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG | TOK_COUNT | TOK_PENDING_COUNT | TOK_UNDEFINED | TOK_DEFINED | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_CONCEPT | TOK_ENTITY | TOK_EVENT | TOK_OBJECT | TOK_INT | TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_DATE | TOK_TIME | TOK_TIMESTAMP | TOK_LIMIT | TOK_ACCEPT | TOK_EMIT | TOK_POLICY | TOK_MAINTAIN | TOK_USING | TOK_PURGE | TOK_WHEN | TOK_OFFSET | TOK_NEW | TOK_DEAD | TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS | HexLiteral | DIGITS | DecimalLiteral | OctalLiteral | DateTimeLiteral | CharLiteral | StringLiteral | TOK_EXACT_NUMERIC_LITERAL | Identifier | WS | NewLine | CommentLine | SLComment | MultiLineComment )
        int alt31=114;
        alt31 = dfa31.predict(input);
        switch (alt31) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:10: TOK_RPAREN
                {
                mTOK_RPAREN(); 

                }
                break;
            case 2 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:21: TOK_LPAREN
                {
                mTOK_LPAREN(); 

                }
                break;
            case 3 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:32: TOK_COMMA
                {
                mTOK_COMMA(); 

                }
                break;
            case 4 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:42: TOK_SEMIC
                {
                mTOK_SEMIC(); 

                }
                break;
            case 5 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:52: TOK_DOT
                {
                mTOK_DOT(); 

                }
                break;
            case 6 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:60: TOK_DOTDOT
                {
                mTOK_DOTDOT(); 

                }
                break;
            case 7 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:71: TOK_COLON
                {
                mTOK_COLON(); 

                }
                break;
            case 8 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:81: TOK_INDIRECT
                {
                mTOK_INDIRECT(); 

                }
                break;
            case 9 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:94: TOK_AT
                {
                mTOK_AT(); 

                }
                break;
            case 10 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:101: TOK_CONCAT
                {
                mTOK_CONCAT(); 

                }
                break;
            case 11 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:112: TOK_EQ
                {
                mTOK_EQ(); 

                }
                break;
            case 12 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:119: TOK_PLUS
                {
                mTOK_PLUS(); 

                }
                break;
            case 13 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:128: TOK_MINUS
                {
                mTOK_MINUS(); 

                }
                break;
            case 14 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:138: TOK_SLASH
                {
                mTOK_SLASH(); 

                }
                break;
            case 15 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:148: TOK_STAR
                {
                mTOK_STAR(); 

                }
                break;
            case 16 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:157: TOK_LE
                {
                mTOK_LE(); 

                }
                break;
            case 17 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:164: TOK_GE
                {
                mTOK_GE(); 

                }
                break;
            case 18 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:171: TOK_NE
                {
                mTOK_NE(); 

                }
                break;
            case 19 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:178: TOK_LT
                {
                mTOK_LT(); 

                }
                break;
            case 20 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:185: TOK_GT
                {
                mTOK_GT(); 

                }
                break;
            case 21 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:192: TOK_LBRACK
                {
                mTOK_LBRACK(); 

                }
                break;
            case 22 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:203: TOK_RBRACK
                {
                mTOK_RBRACK(); 

                }
                break;
            case 23 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:214: TOK_DOLLAR
                {
                mTOK_DOLLAR(); 

                }
                break;
            case 24 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:225: TOK_AMPERSAND
                {
                mTOK_AMPERSAND(); 

                }
                break;
            case 25 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:239: TOK_HASH
                {
                mTOK_HASH(); 

                }
                break;
            case 26 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:248: TOK_DOUBLE_QUOTE
                {
                mTOK_DOUBLE_QUOTE(); 

                }
                break;
            case 27 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:265: TOK_LCURLY
                {
                mTOK_LCURLY(); 

                }
                break;
            case 28 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:276: TOK_RCURLY
                {
                mTOK_RCURLY(); 

                }
                break;
            case 29 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:287: TOK_DISTINCT
                {
                mTOK_DISTINCT(); 

                }
                break;
            case 30 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:300: TOK_FROM
                {
                mTOK_FROM(); 

                }
                break;
            case 31 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:309: TOK_SELECT
                {
                mTOK_SELECT(); 

                }
                break;
            case 32 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:320: TOK_DELETE
                {
                mTOK_DELETE(); 

                }
                break;
            case 33 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:331: TOK_IN
                {
                mTOK_IN(); 

                }
                break;
            case 34 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:338: TOK_AS
                {
                mTOK_AS(); 

                }
                break;
            case 35 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:345: TOK_WHERE
                {
                mTOK_WHERE(); 

                }
                break;
            case 36 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:355: TOK_GROUP
                {
                mTOK_GROUP(); 

                }
                break;
            case 37 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:365: TOK_BY
                {
                mTOK_BY(); 

                }
                break;
            case 38 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:372: TOK_HAVING
                {
                mTOK_HAVING(); 

                }
                break;
            case 39 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:383: TOK_ORDER
                {
                mTOK_ORDER(); 

                }
                break;
            case 40 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:393: TOK_ASC
                {
                mTOK_ASC(); 

                }
                break;
            case 41 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:401: TOK_DESC
                {
                mTOK_DESC(); 

                }
                break;
            case 42 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:410: TOK_OR
                {
                mTOK_OR(); 

                }
                break;
            case 43 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:417: TOK_AND
                {
                mTOK_AND(); 

                }
                break;
            case 44 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:425: TOK_FOR
                {
                mTOK_FOR(); 

                }
                break;
            case 45 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:433: TOK_ALL
                {
                mTOK_ALL(); 

                }
                break;
            case 46 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:441: TOK_EXISTS
                {
                mTOK_EXISTS(); 

                }
                break;
            case 47 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:452: TOK_LIKE
                {
                mTOK_LIKE(); 

                }
                break;
            case 48 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:461: TOK_BETWEEN
                {
                mTOK_BETWEEN(); 

                }
                break;
            case 49 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:473: TOK_UNION
                {
                mTOK_UNION(); 

                }
                break;
            case 50 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:483: TOK_EXCEPT
                {
                mTOK_EXCEPT(); 

                }
                break;
            case 51 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:494: TOK_INTERSECT
                {
                mTOK_INTERSECT(); 

                }
                break;
            case 52 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:508: TOK_MOD
                {
                mTOK_MOD(); 

                }
                break;
            case 53 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:516: TOK_ABS
                {
                mTOK_ABS(); 

                }
                break;
            case 54 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:524: TOK_NOT
                {
                mTOK_NOT(); 

                }
                break;
            case 55 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:532: TOK_FIRST
                {
                mTOK_FIRST(); 

                }
                break;
            case 56 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:542: TOK_LAST
                {
                mTOK_LAST(); 

                }
                break;
            case 57 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:551: TOK_SLIDING
                {
                mTOK_SLIDING(); 

                }
                break;
            case 58 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:563: TOK_TUMBLING
                {
                mTOK_TUMBLING(); 

                }
                break;
            case 59 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:576: TOK_UNIQUE
                {
                mTOK_UNIQUE(); 

                }
                break;
            case 60 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:587: TOK_SUM
                {
                mTOK_SUM(); 

                }
                break;
            case 61 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:595: TOK_MIN
                {
                mTOK_MIN(); 

                }
                break;
            case 62 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:603: TOK_MAX
                {
                mTOK_MAX(); 

                }
                break;
            case 63 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:611: TOK_AVG
                {
                mTOK_AVG(); 

                }
                break;
            case 64 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:619: TOK_COUNT
                {
                mTOK_COUNT(); 

                }
                break;
            case 65 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:629: TOK_PENDING_COUNT
                {
                mTOK_PENDING_COUNT(); 

                }
                break;
            case 66 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:647: TOK_UNDEFINED
                {
                mTOK_UNDEFINED(); 

                }
                break;
            case 67 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:661: TOK_DEFINED
                {
                mTOK_DEFINED(); 

                }
                break;
            case 68 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:673: TOK_NULL
                {
                mTOK_NULL(); 

                }
                break;
            case 69 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:682: TOK_TRUE
                {
                mTOK_TRUE(); 

                }
                break;
            case 70 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:691: TOK_FALSE
                {
                mTOK_FALSE(); 

                }
                break;
            case 71 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:701: TOK_CONCEPT
                {
                mTOK_CONCEPT(); 

                }
                break;
            case 72 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:713: TOK_ENTITY
                {
                mTOK_ENTITY(); 

                }
                break;
            case 73 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:724: TOK_EVENT
                {
                mTOK_EVENT(); 

                }
                break;
            case 74 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:734: TOK_OBJECT
                {
                mTOK_OBJECT(); 

                }
                break;
            case 75 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:745: TOK_INT
                {
                mTOK_INT(); 

                }
                break;
            case 76 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:753: TOK_LONG
                {
                mTOK_LONG(); 

                }
                break;
            case 77 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:762: TOK_DOUBLE
                {
                mTOK_DOUBLE(); 

                }
                break;
            case 78 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:773: TOK_CHAR
                {
                mTOK_CHAR(); 

                }
                break;
            case 79 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:782: TOK_STRING
                {
                mTOK_STRING(); 

                }
                break;
            case 80 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:793: TOK_BOOLEAN
                {
                mTOK_BOOLEAN(); 

                }
                break;
            case 81 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:805: TOK_DATETIME
                {
                mTOK_DATETIME(); 

                }
                break;
            case 82 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:818: TOK_DATE
                {
                mTOK_DATE(); 

                }
                break;
            case 83 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:827: TOK_TIME
                {
                mTOK_TIME(); 

                }
                break;
            case 84 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:836: TOK_TIMESTAMP
                {
                mTOK_TIMESTAMP(); 

                }
                break;
            case 85 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:850: TOK_LIMIT
                {
                mTOK_LIMIT(); 

                }
                break;
            case 86 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:860: TOK_ACCEPT
                {
                mTOK_ACCEPT(); 

                }
                break;
            case 87 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:871: TOK_EMIT
                {
                mTOK_EMIT(); 

                }
                break;
            case 88 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:880: TOK_POLICY
                {
                mTOK_POLICY(); 

                }
                break;
            case 89 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:891: TOK_MAINTAIN
                {
                mTOK_MAINTAIN(); 

                }
                break;
            case 90 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:904: TOK_USING
                {
                mTOK_USING(); 

                }
                break;
            case 91 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:914: TOK_PURGE
                {
                mTOK_PURGE(); 

                }
                break;
            case 92 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:924: TOK_WHEN
                {
                mTOK_WHEN(); 

                }
                break;
            case 93 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:933: TOK_OFFSET
                {
                mTOK_OFFSET(); 

                }
                break;
            case 94 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:944: TOK_NEW
                {
                mTOK_NEW(); 

                }
                break;
            case 95 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:952: TOK_DEAD
                {
                mTOK_DEAD(); 

                }
                break;
            case 96 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:961: TOK_TIME_MILLISECONDS
                {
                mTOK_TIME_MILLISECONDS(); 

                }
                break;
            case 97 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:983: TOK_TIME_SECONDS
                {
                mTOK_TIME_SECONDS(); 

                }
                break;
            case 98 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1000: TOK_TIME_MINUTES
                {
                mTOK_TIME_MINUTES(); 

                }
                break;
            case 99 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1017: TOK_TIME_HOURS
                {
                mTOK_TIME_HOURS(); 

                }
                break;
            case 100 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1032: TOK_TIME_DAYS
                {
                mTOK_TIME_DAYS(); 

                }
                break;
            case 101 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1046: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 102 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1057: DIGITS
                {
                mDIGITS(); 

                }
                break;
            case 103 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1064: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 104 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1079: OctalLiteral
                {
                mOctalLiteral(); 

                }
                break;
            case 105 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1092: DateTimeLiteral
                {
                mDateTimeLiteral(); 

                }
                break;
            case 106 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1108: CharLiteral
                {
                mCharLiteral(); 

                }
                break;
            case 107 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1120: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 108 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1134: TOK_EXACT_NUMERIC_LITERAL
                {
                mTOK_EXACT_NUMERIC_LITERAL(); 

                }
                break;
            case 109 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1160: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 110 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1171: WS
                {
                mWS(); 

                }
                break;
            case 111 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1174: NewLine
                {
                mNewLine(); 

                }
                break;
            case 112 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1182: CommentLine
                {
                mCommentLine(); 

                }
                break;
            case 113 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1194: SLComment
                {
                mSLComment(); 

                }
                break;
            case 114 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1:1204: MultiLineComment
                {
                mMultiLineComment(); 

                }
                break;

        }

    }


    protected DFA31 dfa31 = new DFA31(this);
    static final String DFA31_eotS =
        "\5\uffff\1\62\1\uffff\1\67\4\uffff\1\72\1\uffff\1\74\1\76\6\uffff"+
        "\1\100\2\uffff\22\56\2\167\3\uffff\1\61\20\uffff\14\56\1\u008e\1"+
        "\56\1\u0091\7\56\1\u0099\4\56\1\u009f\31\56\2\uffff\1\u00be\1\uffff"+
        "\1\167\2\uffff\10\56\1\u00c9\5\56\1\u00cf\1\56\1\u00d2\1\uffff\1"+
        "\56\1\u00d5\1\uffff\1\u00d6\1\u00d7\1\u00d8\1\u00d9\3\56\1\uffff"+
        "\5\56\1\uffff\15\56\1\u00f1\1\u00f3\1\56\1\u00f5\1\56\1\u00f7\1"+
        "\56\1\u00f9\11\56\1\uffff\1\u00be\1\167\2\56\1\u0107\1\u0108\1\56"+
        "\1\u010b\1\u010c\1\u010d\1\uffff\5\56\1\uffff\2\56\1\uffff\2\56"+
        "\5\uffff\2\56\1\u0119\14\56\1\u0126\1\u0127\1\56\1\u0129\1\u012a"+
        "\3\56\1\uffff\1\56\1\uffff\1\56\1\uffff\1\56\1\uffff\1\u0131\1\uffff"+
        "\1\56\1\u0133\1\u0135\2\56\1\u0138\3\56\1\u00be\1\167\2\56\2\uffff"+
        "\2\56\3\uffff\1\u0141\1\u0142\10\56\1\u014b\1\uffff\1\u014c\3\56"+
        "\1\u0150\1\u0151\5\56\1\u0157\2\uffff\1\u0158\2\uffff\1\u0159\1"+
        "\56\1\u015b\3\56\1\uffff\1\56\1\uffff\1\56\1\uffff\1\u0161\1\56"+
        "\1\uffff\2\56\1\u0165\1\167\1\56\1\u0167\1\u0168\1\56\2\uffff\1"+
        "\u016a\2\56\1\u016d\3\56\1\u0171\2\uffff\2\56\1\u0174\2\uffff\1"+
        "\u0175\1\u0176\1\u0177\1\u0178\1\u0179\3\uffff\1\u017a\1\uffff\5"+
        "\56\1\uffff\2\56\1\u0182\1\uffff\1\56\2\uffff\1\56\1\uffff\1\u0185"+
        "\1\u0186\1\uffff\3\56\1\uffff\1\u018a\1\u018b\7\uffff\1\u018c\4"+
        "\56\1\u0191\1\56\1\uffff\1\u0193\1\u0194\2\uffff\3\56\3\uffff\1"+
        "\56\1\u0199\1\u019a\1\56\1\uffff\1\56\2\uffff\1\u019d\3\56\2\uffff"+
        "\1\u01a1\1\56\1\uffff\1\56\1\u01a4\1\56\1\uffff\2\56\1\uffff\2\56"+
        "\1\u01aa\1\u01ab\1\56\2\uffff\1\u01ad\1\uffff";
    static final String DFA31_eofS =
        "\u01ae\uffff";
    static final String DFA31_minS =
        "\1\11\4\uffff\1\56\1\uffff\1\55\4\uffff\1\52\1\uffff\2\75\6\uffff"+
        "\1\0\2\uffff\2\101\1\105\1\116\1\102\1\110\1\122\1\105\1\101\1\102"+
        "\1\115\1\101\1\116\1\101\1\105\1\111\1\110\1\105\2\56\3\uffff\1"+
        "\12\20\uffff\1\123\1\101\1\125\1\124\1\117\2\122\1\114\1\103\1\111"+
        "\1\115\1\122\1\44\1\137\1\44\1\104\1\114\1\123\1\107\1\103\1\105"+
        "\1\117\1\44\1\124\1\117\1\126\1\125\1\44\1\112\1\106\1\103\1\124"+
        "\1\105\1\111\1\113\1\123\1\116\2\111\1\104\1\114\1\111\1\124\1\114"+
        "\1\127\1\115\1\125\1\115\1\116\1\101\1\116\1\114\1\122\2\uffff\1"+
        "\60\1\uffff\1\56\2\uffff\1\124\1\105\1\103\1\104\1\102\1\105\1\123"+
        "\1\115\1\44\2\123\1\105\1\117\1\104\1\44\1\111\1\44\1\uffff\1\104"+
        "\1\44\1\uffff\4\44\1\105\1\116\1\125\1\uffff\1\127\1\114\1\111\1"+
        "\122\1\105\1\uffff\1\105\2\123\1\105\1\111\1\116\1\124\1\105\1\111"+
        "\1\124\1\107\1\117\1\116\2\44\1\114\1\44\1\116\1\44\1\114\1\44\1"+
        "\102\2\105\1\116\1\103\1\122\1\104\1\111\1\107\1\uffff\1\60\1\56"+
        "\1\111\1\124\2\44\1\114\3\44\1\uffff\1\124\1\105\1\103\1\116\1\111"+
        "\1\uffff\1\116\1\122\1\uffff\1\116\1\105\5\uffff\1\120\1\105\1\44"+
        "\1\120\2\105\1\116\1\123\1\122\1\103\1\105\1\124\1\120\2\124\2\44"+
        "\1\124\2\44\1\116\1\125\1\107\1\uffff\1\124\1\uffff\1\111\1\uffff"+
        "\1\124\1\uffff\1\44\1\uffff\1\114\2\44\1\124\1\105\1\44\1\111\1"+
        "\103\1\105\2\55\1\116\1\105\2\uffff\1\105\1\111\3\uffff\2\44\1\124"+
        "\1\104\1\116\1\107\1\123\1\104\1\106\1\124\1\44\1\uffff\1\44\1\105"+
        "\1\101\1\107\2\44\2\124\1\123\1\124\1\131\1\44\2\uffff\1\44\2\uffff"+
        "\1\44\1\105\1\44\1\105\1\123\1\101\1\uffff\1\111\1\uffff\1\124\1"+
        "\uffff\1\44\1\120\1\uffff\1\116\1\131\1\44\1\56\1\103\2\44\1\115"+
        "\2\uffff\1\44\1\123\1\107\1\44\2\105\1\111\1\44\2\uffff\2\116\1"+
        "\44\2\uffff\5\44\3\uffff\1\44\1\uffff\1\123\1\105\1\111\1\116\1"+
        "\101\1\uffff\1\124\1\107\1\44\1\uffff\1\124\2\uffff\1\105\1\uffff"+
        "\2\44\1\uffff\1\103\1\106\1\116\1\uffff\2\44\7\uffff\1\44\1\103"+
        "\1\116\1\107\1\115\1\44\1\137\1\uffff\2\44\2\uffff\1\124\1\111\1"+
        "\105\3\uffff\1\117\2\44\1\120\1\uffff\1\103\2\uffff\1\44\1\116\1"+
        "\104\1\116\2\uffff\1\44\1\117\1\uffff\1\105\1\44\1\104\1\uffff\1"+
        "\125\1\104\1\uffff\1\123\1\116\2\44\1\124\2\uffff\1\44\1\uffff";
    static final String DFA31_maxS =
        "\1\uff9f\4\uffff\1\71\1\uffff\1\76\4\uffff\1\57\1\uffff\1\76\1"+
        "\75\6\uffff\1\uffff\2\uffff\1\157\1\162\1\165\1\163\1\166\1\150"+
        "\1\162\1\171\1\157\1\162\1\170\1\157\1\163\1\157\2\165\1\157\1\165"+
        "\1\170\1\154\3\uffff\1\12\20\uffff\2\163\1\165\1\171\1\157\2\162"+
        "\2\154\1\151\1\155\1\162\1\uff9f\1\137\1\uff9f\1\144\1\154\1\163"+
        "\1\147\1\143\1\145\1\157\1\uff9f\1\164\1\157\1\166\1\165\1\uff9f"+
        "\1\152\1\146\1\151\1\164\1\145\1\151\1\155\1\163\1\156\2\151\1\144"+
        "\1\156\1\170\1\164\1\154\1\167\1\155\1\165\1\155\1\165\1\141\1\156"+
        "\1\154\1\162\2\uffff\1\71\1\uffff\1\154\2\uffff\1\164\1\145\1\143"+
        "\1\144\1\142\1\145\1\163\1\155\1\uff9f\2\163\1\145\1\157\1\144\1"+
        "\uff9f\1\151\1\uff9f\1\uffff\1\165\1\uff9f\1\uffff\4\uff9f\1\145"+
        "\1\162\1\165\1\uffff\1\167\1\154\1\151\1\162\1\145\1\uffff\1\145"+
        "\2\163\1\145\1\151\1\156\1\164\1\145\1\151\1\164\1\147\1\161\1\156"+
        "\2\uff9f\1\154\1\uff9f\1\156\1\uff9f\1\154\1\uff9f\1\142\2\145\1"+
        "\156\1\143\1\162\1\144\1\151\1\147\1\uffff\1\71\1\154\1\151\1\164"+
        "\2\uff9f\1\154\3\uff9f\1\uffff\1\164\1\145\1\143\1\156\1\151\1\uffff"+
        "\1\156\1\162\1\uffff\1\156\1\145\5\uffff\1\160\1\145\1\uff9f\1\160"+
        "\2\145\1\156\1\163\1\162\1\143\1\145\1\164\1\160\2\164\2\uff9f\1"+
        "\164\2\uff9f\1\156\1\165\1\147\1\uffff\1\164\1\uffff\1\151\1\uffff"+
        "\1\164\1\uffff\1\uff9f\1\uffff\1\154\2\uff9f\1\164\1\145\1\uff9f"+
        "\1\151\1\143\1\145\1\55\1\154\1\156\1\145\2\uffff\1\145\1\151\3"+
        "\uffff\2\uff9f\1\164\1\144\1\156\1\147\1\163\1\144\1\146\1\164\1"+
        "\uff9f\1\uffff\1\uff9f\1\145\1\141\1\147\2\uff9f\2\164\1\163\1\164"+
        "\1\171\1\uff9f\2\uffff\1\uff9f\2\uffff\1\uff9f\1\145\1\uff9f\1\145"+
        "\1\163\1\141\1\uffff\1\151\1\uffff\1\164\1\uffff\1\uff9f\1\160\1"+
        "\uffff\1\156\1\171\1\uff9f\1\154\1\143\2\uff9f\1\155\2\uffff\1\uff9f"+
        "\1\163\1\147\1\uff9f\2\145\1\151\1\uff9f\2\uffff\2\156\1\uff9f\2"+
        "\uffff\5\uff9f\3\uffff\1\uff9f\1\uffff\1\163\1\145\1\151\1\156\1"+
        "\141\1\uffff\1\164\1\147\1\uff9f\1\uffff\1\164\2\uffff\1\145\1\uffff"+
        "\2\uff9f\1\uffff\1\143\1\146\1\156\1\uffff\2\uff9f\7\uffff\1\uff9f"+
        "\1\143\1\156\1\147\1\155\1\uff9f\1\137\1\uffff\2\uff9f\2\uffff\1"+
        "\164\1\151\1\145\3\uffff\1\157\2\uff9f\1\160\1\uffff\1\143\2\uffff"+
        "\1\uff9f\1\156\1\144\1\156\2\uffff\1\uff9f\1\157\1\uffff\1\145\1"+
        "\uff9f\1\144\1\uffff\1\165\1\144\1\uffff\1\163\1\156\2\uff9f\1\164"+
        "\2\uffff\1\uff9f\1\uffff";
    static final String DFA31_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\uffff\1\7\1\uffff\1\11\1\12\1\13\1\14"+
        "\1\uffff\1\17\2\uffff\1\22\1\25\1\26\1\27\1\30\1\31\1\uffff\1\33"+
        "\1\34\24\uffff\1\152\1\155\1\156\1\uffff\1\156\1\5\1\6\1\154\1\10"+
        "\1\161\1\15\1\160\1\162\1\16\1\20\1\23\1\21\1\24\1\153\1\32\65\uffff"+
        "\1\145\1\146\1\uffff\1\151\1\uffff\1\147\1\157\21\uffff\1\41\2\uffff"+
        "\1\42\7\uffff\1\45\5\uffff\1\52\36\uffff\1\150\12\uffff\1\54\5\uffff"+
        "\1\74\2\uffff\1\113\2\uffff\1\50\1\53\1\55\1\65\1\77\27\uffff\1"+
        "\64\1\uffff\1\75\1\uffff\1\76\1\uffff\1\66\1\uffff\1\136\15\uffff"+
        "\1\51\1\137\2\uffff\1\122\1\144\1\36\13\uffff\1\134\14\uffff\1\127"+
        "\1\57\1\uffff\1\70\1\114\6\uffff\1\104\1\uffff\1\105\1\uffff\1\123"+
        "\2\uffff\1\116\10\uffff\1\67\1\106\10\uffff\1\43\1\44\3\uffff\1"+
        "\143\1\47\5\uffff\1\111\1\125\1\61\1\uffff\1\132\5\uffff\1\100\3"+
        "\uffff\1\133\1\uffff\1\40\1\115\1\uffff\1\37\2\uffff\1\117\3\uffff"+
        "\1\126\2\uffff\1\46\1\112\1\135\1\56\1\62\1\110\1\73\7\uffff\1\130"+
        "\2\uffff\1\141\1\71\3\uffff\1\60\1\120\1\142\4\uffff\1\107\1\uffff"+
        "\1\35\1\121\4\uffff\1\131\1\72\2\uffff\1\63\3\uffff\1\124\2\uffff"+
        "\1\103\5\uffff\1\102\1\140\1\uffff\1\101";
    static final String DFA31_specialS =
        "\26\uffff\1\0\u0197\uffff}>";
    static final String[] DFA31_transitionS = {
            "\1\61\1\57\1\uffff\1\61\1\60\22\uffff\1\61\1\20\1\26\1\25\1"+
            "\23\1\uffff\1\24\1\55\1\2\1\1\1\15\1\13\1\3\1\7\1\5\1\14\1\53"+
            "\11\54\1\6\1\4\1\16\1\12\1\17\1\uffff\1\10\1\35\1\40\1\51\1"+
            "\31\1\43\1\32\1\37\1\41\1\34\2\56\1\44\1\46\1\47\1\42\1\52\2"+
            "\56\1\33\1\50\1\45\1\56\1\36\3\56\1\21\1\uffff\1\22\1\uffff"+
            "\1\56\1\uffff\1\35\1\40\1\51\1\31\1\43\1\32\1\37\1\41\1\34\2"+
            "\56\1\44\1\46\1\47\1\42\1\52\2\56\1\33\1\50\1\45\1\56\1\36\3"+
            "\56\1\27\1\11\1\30\102\uffff\27\56\1\uffff\37\56\1\uffff\u1f08"+
            "\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e"+
            "\56\u10d2\uffff\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77"+
            "\56",
            "",
            "",
            "",
            "",
            "\1\63\1\uffff\12\64",
            "",
            "\1\66\20\uffff\1\65",
            "",
            "",
            "",
            "",
            "\1\71\4\uffff\1\70",
            "",
            "\1\73\1\20",
            "\1\75",
            "",
            "",
            "",
            "",
            "",
            "",
            "\0\77",
            "",
            "",
            "\1\104\3\uffff\1\102\3\uffff\1\101\5\uffff\1\103\21\uffff"+
            "\1\104\3\uffff\1\102\3\uffff\1\101\5\uffff\1\103",
            "\1\110\7\uffff\1\107\5\uffff\1\106\2\uffff\1\105\16\uffff"+
            "\1\110\7\uffff\1\107\5\uffff\1\106\2\uffff\1\105",
            "\1\111\6\uffff\1\112\7\uffff\1\114\1\113\17\uffff\1\111\6"+
            "\uffff\1\112\7\uffff\1\114\1\113",
            "\1\115\4\uffff\1\116\32\uffff\1\115\4\uffff\1\116",
            "\1\122\1\124\10\uffff\1\121\1\uffff\1\120\4\uffff\1\117\2"+
            "\uffff\1\123\13\uffff\1\122\1\124\10\uffff\1\121\1\uffff\1\120"+
            "\4\uffff\1\117\2\uffff\1\123",
            "\1\125\37\uffff\1\125",
            "\1\126\37\uffff\1\126",
            "\1\130\11\uffff\1\131\11\uffff\1\127\13\uffff\1\130\11\uffff"+
            "\1\131\11\uffff\1\127",
            "\1\132\15\uffff\1\133\21\uffff\1\132\15\uffff\1\133",
            "\1\135\3\uffff\1\136\13\uffff\1\134\17\uffff\1\135\3\uffff"+
            "\1\136\13\uffff\1\134",
            "\1\142\1\140\7\uffff\1\141\1\uffff\1\137\24\uffff\1\142\1"+
            "\140\7\uffff\1\141\1\uffff\1\137",
            "\1\144\7\uffff\1\143\5\uffff\1\145\21\uffff\1\144\7\uffff"+
            "\1\143\5\uffff\1\145",
            "\1\146\4\uffff\1\147\32\uffff\1\146\4\uffff\1\147",
            "\1\152\7\uffff\1\151\5\uffff\1\150\21\uffff\1\152\7\uffff"+
            "\1\151\5\uffff\1\150",
            "\1\155\11\uffff\1\153\5\uffff\1\154\17\uffff\1\155\11\uffff"+
            "\1\153\5\uffff\1\154",
            "\1\160\10\uffff\1\157\2\uffff\1\156\23\uffff\1\160\10\uffff"+
            "\1\157\2\uffff\1\156",
            "\1\162\6\uffff\1\161\30\uffff\1\162\6\uffff\1\161",
            "\1\163\11\uffff\1\164\5\uffff\1\165\17\uffff\1\163\11\uffff"+
            "\1\164\5\uffff\1\165",
            "\1\64\1\uffff\10\170\2\171\13\uffff\1\64\22\uffff\1\166\14"+
            "\uffff\1\64\22\uffff\1\166",
            "\1\64\1\uffff\12\172\13\uffff\1\64\6\uffff\1\173\30\uffff"+
            "\1\64\6\uffff\1\173",
            "",
            "",
            "",
            "\1\174",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\175\37\uffff\1\175",
            "\1\u0080\12\uffff\1\176\6\uffff\1\177\15\uffff\1\u0080\12"+
            "\uffff\1\176\6\uffff\1\177",
            "\1\u0081\37\uffff\1\u0081",
            "\1\u0082\4\uffff\1\u0083\32\uffff\1\u0082\4\uffff\1\u0083",
            "\1\u0084\37\uffff\1\u0084",
            "\1\u0085\37\uffff\1\u0085",
            "\1\u0086\37\uffff\1\u0086",
            "\1\u0087\37\uffff\1\u0087",
            "\1\u0089\10\uffff\1\u0088\26\uffff\1\u0089\10\uffff\1\u0088",
            "\1\u008a\37\uffff\1\u008a",
            "\1\u008b\37\uffff\1\u008b",
            "\1\u008c\37\uffff\1\u008c",
            "\1\56\13\uffff\12\56\7\uffff\23\56\1\u008d\6\56\4\uffff\1"+
            "\56\1\uffff\23\56\1\u008d\6\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\u008f",
            "\1\56\13\uffff\12\56\7\uffff\2\56\1\u0090\27\56\4\uffff\1"+
            "\56\1\uffff\2\56\1\u0090\27\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\u0092\37\uffff\1\u0092",
            "\1\u0093\37\uffff\1\u0093",
            "\1\u0094\37\uffff\1\u0094",
            "\1\u0095\37\uffff\1\u0095",
            "\1\u0096\37\uffff\1\u0096",
            "\1\u0097\37\uffff\1\u0097",
            "\1\u0098\37\uffff\1\u0098",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u009a\37\uffff\1\u009a",
            "\1\u009b\37\uffff\1\u009b",
            "\1\u009c\37\uffff\1\u009c",
            "\1\u009d\37\uffff\1\u009d",
            "\1\56\13\uffff\12\56\7\uffff\3\56\1\u009e\26\56\4\uffff\1"+
            "\56\1\uffff\3\56\1\u009e\26\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\u00a0\37\uffff\1\u00a0",
            "\1\u00a1\37\uffff\1\u00a1",
            "\1\u00a3\5\uffff\1\u00a2\31\uffff\1\u00a3\5\uffff\1\u00a2",
            "\1\u00a4\37\uffff\1\u00a4",
            "\1\u00a5\37\uffff\1\u00a5",
            "\1\u00a6\37\uffff\1\u00a6",
            "\1\u00a7\1\uffff\1\u00a8\35\uffff\1\u00a7\1\uffff\1\u00a8",
            "\1\u00a9\37\uffff\1\u00a9",
            "\1\u00aa\37\uffff\1\u00aa",
            "\1\u00ab\37\uffff\1\u00ab",
            "\1\u00ac\37\uffff\1\u00ac",
            "\1\u00ad\37\uffff\1\u00ad",
            "\1\u00af\1\uffff\1\u00ae\35\uffff\1\u00af\1\uffff\1\u00ae",
            "\1\u00b1\16\uffff\1\u00b0\20\uffff\1\u00b1\16\uffff\1\u00b0",
            "\1\u00b2\37\uffff\1\u00b2",
            "\1\u00b3\37\uffff\1\u00b3",
            "\1\u00b4\37\uffff\1\u00b4",
            "\1\u00b5\37\uffff\1\u00b5",
            "\1\u00b6\37\uffff\1\u00b6",
            "\1\u00b7\37\uffff\1\u00b7",
            "\1\u00b9\6\uffff\1\u00b8\30\uffff\1\u00b9\6\uffff\1\u00b8",
            "\1\u00ba\37\uffff\1\u00ba",
            "\1\u00bb\37\uffff\1\u00bb",
            "\1\u00bc\37\uffff\1\u00bc",
            "\1\u00bd\37\uffff\1\u00bd",
            "",
            "",
            "\10\u00bf\2\171",
            "",
            "\1\64\1\uffff\12\u00c0\13\uffff\1\64\6\uffff\1\173\30\uffff"+
            "\1\64\6\uffff\1\173",
            "",
            "",
            "\1\u00c1\37\uffff\1\u00c1",
            "\1\u00c2\37\uffff\1\u00c2",
            "\1\u00c3\37\uffff\1\u00c3",
            "\1\u00c4\37\uffff\1\u00c4",
            "\1\u00c5\37\uffff\1\u00c5",
            "\1\u00c6\37\uffff\1\u00c6",
            "\1\u00c7\37\uffff\1\u00c7",
            "\1\u00c8\37\uffff\1\u00c8",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00ca\37\uffff\1\u00ca",
            "\1\u00cb\37\uffff\1\u00cb",
            "\1\u00cc\37\uffff\1\u00cc",
            "\1\u00cd\37\uffff\1\u00cd",
            "\1\u00ce\37\uffff\1\u00ce",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00d0\37\uffff\1\u00d0",
            "\1\56\13\uffff\12\56\7\uffff\4\56\1\u00d1\25\56\4\uffff\1"+
            "\56\1\uffff\4\56\1\u00d1\25\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "",
            "\1\u00d4\20\uffff\1\u00d3\16\uffff\1\u00d4\20\uffff\1\u00d3",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00da\37\uffff\1\u00da",
            "\1\u00dc\3\uffff\1\u00db\33\uffff\1\u00dc\3\uffff\1\u00db",
            "\1\u00dd\37\uffff\1\u00dd",
            "",
            "\1\u00de\37\uffff\1\u00de",
            "\1\u00df\37\uffff\1\u00df",
            "\1\u00e0\37\uffff\1\u00e0",
            "\1\u00e1\37\uffff\1\u00e1",
            "\1\u00e2\37\uffff\1\u00e2",
            "",
            "\1\u00e3\37\uffff\1\u00e3",
            "\1\u00e4\37\uffff\1\u00e4",
            "\1\u00e5\37\uffff\1\u00e5",
            "\1\u00e6\37\uffff\1\u00e6",
            "\1\u00e7\37\uffff\1\u00e7",
            "\1\u00e8\37\uffff\1\u00e8",
            "\1\u00e9\37\uffff\1\u00e9",
            "\1\u00ea\37\uffff\1\u00ea",
            "\1\u00eb\37\uffff\1\u00eb",
            "\1\u00ec\37\uffff\1\u00ec",
            "\1\u00ed\37\uffff\1\u00ed",
            "\1\u00ee\1\uffff\1\u00ef\35\uffff\1\u00ee\1\uffff\1\u00ef",
            "\1\u00f0\37\uffff\1\u00f0",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\24\56\1\u00f2\5\56\4\uffff\1"+
            "\56\1\uffff\24\56\1\u00f2\5\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\u00f4\37\uffff\1\u00f4",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00f6\37\uffff\1\u00f6",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00f8\37\uffff\1\u00f8",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u00fa\37\uffff\1\u00fa",
            "\1\u00fb\37\uffff\1\u00fb",
            "\1\u00fc\37\uffff\1\u00fc",
            "\1\u00fd\37\uffff\1\u00fd",
            "\1\u00fe\37\uffff\1\u00fe",
            "\1\u00ff\37\uffff\1\u00ff",
            "\1\u0100\37\uffff\1\u0100",
            "\1\u0101\37\uffff\1\u0101",
            "\1\u0102\37\uffff\1\u0102",
            "",
            "\10\u0103\2\171",
            "\1\64\1\uffff\12\u0104\13\uffff\1\64\6\uffff\1\173\30\uffff"+
            "\1\64\6\uffff\1\173",
            "\1\u0105\37\uffff\1\u0105",
            "\1\u0106\37\uffff\1\u0106",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0109\37\uffff\1\u0109",
            "\1\56\13\uffff\12\56\7\uffff\23\56\1\u010a\6\56\4\uffff\1"+
            "\56\1\uffff\23\56\1\u010a\6\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\u010e\37\uffff\1\u010e",
            "\1\u010f\37\uffff\1\u010f",
            "\1\u0110\37\uffff\1\u0110",
            "\1\u0111\37\uffff\1\u0111",
            "\1\u0112\37\uffff\1\u0112",
            "",
            "\1\u0113\37\uffff\1\u0113",
            "\1\u0114\37\uffff\1\u0114",
            "",
            "\1\u0115\37\uffff\1\u0115",
            "\1\u0116\37\uffff\1\u0116",
            "",
            "",
            "",
            "",
            "",
            "\1\u0117\37\uffff\1\u0117",
            "\1\u0118\37\uffff\1\u0118",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u011a\37\uffff\1\u011a",
            "\1\u011b\37\uffff\1\u011b",
            "\1\u011c\37\uffff\1\u011c",
            "\1\u011d\37\uffff\1\u011d",
            "\1\u011e\37\uffff\1\u011e",
            "\1\u011f\37\uffff\1\u011f",
            "\1\u0120\37\uffff\1\u0120",
            "\1\u0121\37\uffff\1\u0121",
            "\1\u0122\37\uffff\1\u0122",
            "\1\u0123\37\uffff\1\u0123",
            "\1\u0124\37\uffff\1\u0124",
            "\1\u0125\37\uffff\1\u0125",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0128\37\uffff\1\u0128",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u012b\37\uffff\1\u012b",
            "\1\u012c\37\uffff\1\u012c",
            "\1\u012d\37\uffff\1\u012d",
            "",
            "\1\u012e\37\uffff\1\u012e",
            "",
            "\1\u012f\37\uffff\1\u012f",
            "",
            "\1\u0130\37\uffff\1\u0130",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\u0132\37\uffff\1\u0132",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\22\56\1\u0134\7\56\4\uffff\1"+
            "\56\1\uffff\22\56\1\u0134\7\56\105\uffff\27\56\1\uffff\37\56"+
            "\1\uffff\u1f08\56\u1040\uffff\u0150\56\u0170\uffff\u0080\56"+
            "\u0080\uffff\u092e\56\u10d2\uffff\u5200\56\u5900\uffff\u0200"+
            "\56\u0461\uffff\77\56",
            "\1\u0136\37\uffff\1\u0136",
            "\1\u0137\37\uffff\1\u0137",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0139\37\uffff\1\u0139",
            "\1\u013a\37\uffff\1\u013a",
            "\1\u013b\37\uffff\1\u013b",
            "\1\171",
            "\1\171\1\64\1\uffff\12\u013c\13\uffff\1\64\6\uffff\1\173\30"+
            "\uffff\1\64\6\uffff\1\173",
            "\1\u013d\37\uffff\1\u013d",
            "\1\u013e\37\uffff\1\u013e",
            "",
            "",
            "\1\u013f\37\uffff\1\u013f",
            "\1\u0140\37\uffff\1\u0140",
            "",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0143\37\uffff\1\u0143",
            "\1\u0144\37\uffff\1\u0144",
            "\1\u0145\37\uffff\1\u0145",
            "\1\u0146\37\uffff\1\u0146",
            "\1\u0147\37\uffff\1\u0147",
            "\1\u0148\37\uffff\1\u0148",
            "\1\u0149\37\uffff\1\u0149",
            "\1\u014a\37\uffff\1\u014a",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u014d\37\uffff\1\u014d",
            "\1\u014e\37\uffff\1\u014e",
            "\1\u014f\37\uffff\1\u014f",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0152\37\uffff\1\u0152",
            "\1\u0153\37\uffff\1\u0153",
            "\1\u0154\37\uffff\1\u0154",
            "\1\u0155\37\uffff\1\u0155",
            "\1\u0156\37\uffff\1\u0156",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u015a\37\uffff\1\u015a",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u015c\37\uffff\1\u015c",
            "\1\u015d\37\uffff\1\u015d",
            "\1\u015e\37\uffff\1\u015e",
            "",
            "\1\u015f\37\uffff\1\u015f",
            "",
            "\1\u0160\37\uffff\1\u0160",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0162\37\uffff\1\u0162",
            "",
            "\1\u0163\37\uffff\1\u0163",
            "\1\u0164\37\uffff\1\u0164",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\64\1\uffff\12\u013c\13\uffff\1\64\6\uffff\1\173\30\uffff"+
            "\1\64\6\uffff\1\173",
            "\1\u0166\37\uffff\1\u0166",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0169\37\uffff\1\u0169",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u016b\37\uffff\1\u016b",
            "\1\u016c\37\uffff\1\u016c",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u016e\37\uffff\1\u016e",
            "\1\u016f\37\uffff\1\u016f",
            "\1\u0170\37\uffff\1\u0170",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "\1\u0172\37\uffff\1\u0172",
            "\1\u0173\37\uffff\1\u0173",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\u017b\37\uffff\1\u017b",
            "\1\u017c\37\uffff\1\u017c",
            "\1\u017d\37\uffff\1\u017d",
            "\1\u017e\37\uffff\1\u017e",
            "\1\u017f\37\uffff\1\u017f",
            "",
            "\1\u0180\37\uffff\1\u0180",
            "\1\u0181\37\uffff\1\u0181",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\u0183\37\uffff\1\u0183",
            "",
            "",
            "\1\u0184\37\uffff\1\u0184",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "\1\u0187\37\uffff\1\u0187",
            "\1\u0188\37\uffff\1\u0188",
            "\1\u0189\37\uffff\1\u0189",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u018d\37\uffff\1\u018d",
            "\1\u018e\37\uffff\1\u018e",
            "\1\u018f\37\uffff\1\u018f",
            "\1\u0190\37\uffff\1\u0190",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u0192",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "",
            "",
            "\1\u0195\37\uffff\1\u0195",
            "\1\u0196\37\uffff\1\u0196",
            "\1\u0197\37\uffff\1\u0197",
            "",
            "",
            "",
            "\1\u0198\37\uffff\1\u0198",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u019b\37\uffff\1\u019b",
            "",
            "\1\u019c\37\uffff\1\u019c",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u019e\37\uffff\1\u019e",
            "\1\u019f\37\uffff\1\u019f",
            "\1\u01a0\37\uffff\1\u01a0",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u01a2\37\uffff\1\u01a2",
            "",
            "\1\u01a3\37\uffff\1\u01a3",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u01a5\37\uffff\1\u01a5",
            "",
            "\1\u01a6\37\uffff\1\u01a6",
            "\1\u01a7\37\uffff\1\u01a7",
            "",
            "\1\u01a8\37\uffff\1\u01a8",
            "\1\u01a9\37\uffff\1\u01a9",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            "\1\u01ac\37\uffff\1\u01ac",
            "",
            "",
            "\1\56\13\uffff\12\56\7\uffff\32\56\4\uffff\1\56\1\uffff\32"+
            "\56\105\uffff\27\56\1\uffff\37\56\1\uffff\u1f08\56\u1040\uffff"+
            "\u0150\56\u0170\uffff\u0080\56\u0080\uffff\u092e\56\u10d2\uffff"+
            "\u5200\56\u5900\uffff\u0200\56\u0461\uffff\77\56",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TOK_RPAREN | TOK_LPAREN | TOK_COMMA | TOK_SEMIC | TOK_DOT | TOK_DOTDOT | TOK_COLON | TOK_INDIRECT | TOK_AT | TOK_CONCAT | TOK_EQ | TOK_PLUS | TOK_MINUS | TOK_SLASH | TOK_STAR | TOK_LE | TOK_GE | TOK_NE | TOK_LT | TOK_GT | TOK_LBRACK | TOK_RBRACK | TOK_DOLLAR | TOK_AMPERSAND | TOK_HASH | TOK_DOUBLE_QUOTE | TOK_LCURLY | TOK_RCURLY | TOK_DISTINCT | TOK_FROM | TOK_SELECT | TOK_DELETE | TOK_IN | TOK_AS | TOK_WHERE | TOK_GROUP | TOK_BY | TOK_HAVING | TOK_ORDER | TOK_ASC | TOK_DESC | TOK_OR | TOK_AND | TOK_FOR | TOK_ALL | TOK_EXISTS | TOK_LIKE | TOK_BETWEEN | TOK_UNION | TOK_EXCEPT | TOK_INTERSECT | TOK_MOD | TOK_ABS | TOK_NOT | TOK_FIRST | TOK_LAST | TOK_SLIDING | TOK_TUMBLING | TOK_UNIQUE | TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG | TOK_COUNT | TOK_PENDING_COUNT | TOK_UNDEFINED | TOK_DEFINED | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_CONCEPT | TOK_ENTITY | TOK_EVENT | TOK_OBJECT | TOK_INT | TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_DATE | TOK_TIME | TOK_TIMESTAMP | TOK_LIMIT | TOK_ACCEPT | TOK_EMIT | TOK_POLICY | TOK_MAINTAIN | TOK_USING | TOK_PURGE | TOK_WHEN | TOK_OFFSET | TOK_NEW | TOK_DEAD | TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS | HexLiteral | DIGITS | DecimalLiteral | OctalLiteral | DateTimeLiteral | CharLiteral | StringLiteral | TOK_EXACT_NUMERIC_LITERAL | Identifier | WS | NewLine | CommentLine | SLComment | MultiLineComment );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_22 = input.LA(1);

                        s = -1;
                        if ( ((LA31_22>='\u0000' && LA31_22<='\uFFFF')) ) {s = 63;}

                        else s = 64;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 31, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}