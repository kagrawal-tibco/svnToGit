// $ANTLR 3.2 Sep 23, 2009 12:02:23 JXPath.g 2011-11-06 16:04:33

	package com.tibco.jxpath.compiler;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class JXPathLexer extends Lexer {
    public static final int NCName=47;
    public static final int STAR=42;
    public static final int AXIS_SPECIFIER=13;
    public static final int MOD=44;
    public static final int PATHSEP=16;
    public static final int LESS_THAN_EQUALS=38;
    public static final int DOTDOT=28;
    public static final int FILTER_PREDICATE_EXPR=5;
    public static final int AND=33;
    public static final int EOF=-1;
    public static final int VAR_START=46;
    public static final int AT=20;
    public static final int NAMED_AXIS_STEP=10;
    public static final int Literal=24;
    public static final int T__52=52;
    public static final int Number=29;
    public static final int LPAR=22;
    public static final int Digits=48;
    public static final int COMMA=30;
    public static final int NOT_EQUAL=35;
    public static final int RBRAC=26;
    public static final int EQUAL=34;
    public static final int AxisName=18;
    public static final int PLUS=40;
    public static final int GREATER_THAN_EQUALS=39;
    public static final int NodeType=21;
    public static final int DOT=27;
    public static final int Whitespace=49;
    public static final int QUALIFIED_NAME=6;
    public static final int NCNameStartChar=50;
    public static final int CONSTANT_EXPR=12;
    public static final int NCNameChar=51;
    public static final int ARGUMENTS=15;
    public static final int GREATER_THAN=37;
    public static final int ABBREVIATED_STEP=9;
    public static final int VAR_REF=7;
    public static final int ABRPATH=17;
    public static final int MINUS=41;
    public static final int SIMPLE_AXIS_STEP=8;
    public static final int LBRAC=25;
    public static final int UNION=31;
    public static final int COLON=45;
    public static final int PREDICATES=14;
    public static final int PATH_EXPR=11;
    public static final int OR=32;
    public static final int LESS_THAN=36;
    public static final int RPAR=23;
    public static final int CC=19;
    public static final int DIV=43;
    public static final int FUNCTION_CALL=4;

    // delegates
    // delegators

    public JXPathLexer() {;} 
    public JXPathLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public JXPathLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "JXPath.g"; }

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:9:7: ( 'processing-instruction' )
            // JXPath.g:9:9: 'processing-instruction'
            {
            match("processing-instruction"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "UNION"
    public final void mUNION() throws RecognitionException {
        try {
            int _type = UNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:183:7: ( '|' )
            // JXPath.g:183:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNION"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:184:4: ( 'or' )
            // JXPath.g:184:6: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:185:5: ( 'and' )
            // JXPath.g:185:7: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:186:7: ( '=' )
            // JXPath.g:186:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "NOT_EQUAL"
    public final void mNOT_EQUAL() throws RecognitionException {
        try {
            int _type = NOT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:187:11: ( '!=' )
            // JXPath.g:187:13: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUAL"

    // $ANTLR start "LESS_THAN"
    public final void mLESS_THAN() throws RecognitionException {
        try {
            int _type = LESS_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:188:11: ( '<' )
            // JXPath.g:188:13: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN"

    // $ANTLR start "GREATER_THAN"
    public final void mGREATER_THAN() throws RecognitionException {
        try {
            int _type = GREATER_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:189:14: ( '>' )
            // JXPath.g:189:16: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN"

    // $ANTLR start "LESS_THAN_EQUALS"
    public final void mLESS_THAN_EQUALS() throws RecognitionException {
        try {
            int _type = LESS_THAN_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:190:18: ( '<=' )
            // JXPath.g:190:20: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN_EQUALS"

    // $ANTLR start "GREATER_THAN_EQUALS"
    public final void mGREATER_THAN_EQUALS() throws RecognitionException {
        try {
            int _type = GREATER_THAN_EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:191:21: ( '>=' )
            // JXPath.g:191:23: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN_EQUALS"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:192:6: ( '+' )
            // JXPath.g:192:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:193:7: ( '-' )
            // JXPath.g:193:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:194:6: ( '*' )
            // JXPath.g:194:8: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:195:5: ( 'div' )
            // JXPath.g:195:7: 'div'
            {
            match("div"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:196:5: ( 'mod' )
            // JXPath.g:196:7: 'mod'
            {
            match("mod"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:197:7: ( ':' )
            // JXPath.g:197:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "VAR_START"
    public final void mVAR_START() throws RecognitionException {
        try {
            int _type = VAR_START;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:198:11: ( '$' )
            // JXPath.g:198:13: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAR_START"

    // $ANTLR start "PATHSEP"
    public final void mPATHSEP() throws RecognitionException {
        try {
            int _type = PATHSEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:199:10: ( '/' )
            // JXPath.g:199:13: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PATHSEP"

    // $ANTLR start "ABRPATH"
    public final void mABRPATH() throws RecognitionException {
        try {
            int _type = ABRPATH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:200:10: ( '//' )
            // JXPath.g:200:13: '//'
            {
            match("//"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ABRPATH"

    // $ANTLR start "LPAR"
    public final void mLPAR() throws RecognitionException {
        try {
            int _type = LPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:201:7: ( '(' )
            // JXPath.g:201:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAR"

    // $ANTLR start "RPAR"
    public final void mRPAR() throws RecognitionException {
        try {
            int _type = RPAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:202:7: ( ')' )
            // JXPath.g:202:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAR"

    // $ANTLR start "LBRAC"
    public final void mLBRAC() throws RecognitionException {
        try {
            int _type = LBRAC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:203:8: ( '[' )
            // JXPath.g:203:11: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRAC"

    // $ANTLR start "RBRAC"
    public final void mRBRAC() throws RecognitionException {
        try {
            int _type = RBRAC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:204:8: ( ']' )
            // JXPath.g:204:11: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRAC"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:205:6: ( '.' )
            // JXPath.g:205:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DOTDOT"
    public final void mDOTDOT() throws RecognitionException {
        try {
            int _type = DOTDOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:206:9: ( '..' )
            // JXPath.g:206:12: '..'
            {
            match(".."); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOTDOT"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:207:5: ( '@' )
            // JXPath.g:207:8: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:208:8: ( ',' )
            // JXPath.g:208:11: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "CC"
    public final void mCC() throws RecognitionException {
        try {
            int _type = CC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:209:5: ( '::' )
            // JXPath.g:209:8: '::'
            {
            match("::"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CC"

    // $ANTLR start "NodeType"
    public final void mNodeType() throws RecognitionException {
        try {
            int _type = NodeType;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:211:9: ( 'comment' | 'text' | 'processing-instruction' | 'node' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 'c':
                {
                alt1=1;
                }
                break;
            case 't':
                {
                alt1=2;
                }
                break;
            case 'p':
                {
                alt1=3;
                }
                break;
            case 'n':
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // JXPath.g:211:12: 'comment'
                    {
                    match("comment"); 


                    }
                    break;
                case 2 :
                    // JXPath.g:212:6: 'text'
                    {
                    match("text"); 


                    }
                    break;
                case 3 :
                    // JXPath.g:213:6: 'processing-instruction'
                    {
                    match("processing-instruction"); 


                    }
                    break;
                case 4 :
                    // JXPath.g:214:6: 'node'
                    {
                    match("node"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NodeType"

    // $ANTLR start "Number"
    public final void mNumber() throws RecognitionException {
        try {
            int _type = Number;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:217:9: ( Digits ( '.' ( Digits )? )? | '.' Digits )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                alt4=1;
            }
            else if ( (LA4_0=='.') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // JXPath.g:217:12: Digits ( '.' ( Digits )? )?
                    {
                    mDigits(); 
                    // JXPath.g:217:19: ( '.' ( Digits )? )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='.') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // JXPath.g:217:20: '.' ( Digits )?
                            {
                            match('.'); 
                            // JXPath.g:217:24: ( Digits )?
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                                alt2=1;
                            }
                            switch (alt2) {
                                case 1 :
                                    // JXPath.g:217:24: Digits
                                    {
                                    mDigits(); 

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // JXPath.g:218:6: '.' Digits
                    {
                    match('.'); 
                    mDigits(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Number"

    // $ANTLR start "Digits"
    public final void mDigits() throws RecognitionException {
        try {
            // JXPath.g:222:9: ( ( '0' .. '9' )+ )
            // JXPath.g:222:12: ( '0' .. '9' )+
            {
            // JXPath.g:222:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // JXPath.g:222:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Digits"

    // $ANTLR start "AxisName"
    public final void mAxisName() throws RecognitionException {
        try {
            int _type = AxisName;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:225:9: ( 'ancestor' | 'ancestor-or-self' | 'attribute' | 'child' | 'descendant' | 'descendant-or-self' | 'following' | 'following-sibling' | 'namespace' | 'parent' | 'preceding' | 'preceding-sibling' | 'self' )
            int alt6=13;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // JXPath.g:225:12: 'ancestor'
                    {
                    match("ancestor"); 


                    }
                    break;
                case 2 :
                    // JXPath.g:226:6: 'ancestor-or-self'
                    {
                    match("ancestor-or-self"); 


                    }
                    break;
                case 3 :
                    // JXPath.g:227:6: 'attribute'
                    {
                    match("attribute"); 


                    }
                    break;
                case 4 :
                    // JXPath.g:228:6: 'child'
                    {
                    match("child"); 


                    }
                    break;
                case 5 :
                    // JXPath.g:229:6: 'descendant'
                    {
                    match("descendant"); 


                    }
                    break;
                case 6 :
                    // JXPath.g:230:6: 'descendant-or-self'
                    {
                    match("descendant-or-self"); 


                    }
                    break;
                case 7 :
                    // JXPath.g:231:6: 'following'
                    {
                    match("following"); 


                    }
                    break;
                case 8 :
                    // JXPath.g:232:6: 'following-sibling'
                    {
                    match("following-sibling"); 


                    }
                    break;
                case 9 :
                    // JXPath.g:233:6: 'namespace'
                    {
                    match("namespace"); 


                    }
                    break;
                case 10 :
                    // JXPath.g:234:6: 'parent'
                    {
                    match("parent"); 


                    }
                    break;
                case 11 :
                    // JXPath.g:235:6: 'preceding'
                    {
                    match("preceding"); 


                    }
                    break;
                case 12 :
                    // JXPath.g:236:6: 'preceding-sibling'
                    {
                    match("preceding-sibling"); 


                    }
                    break;
                case 13 :
                    // JXPath.g:237:6: 'self'
                    {
                    match("self"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AxisName"

    // $ANTLR start "Literal"
    public final void mLiteral() throws RecognitionException {
        try {
            int _type = Literal;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:240:10: ( '\"' (~ '\"' )* '\"' | '\\'' (~ '\\'' )* '\\'' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\"') ) {
                alt9=1;
            }
            else if ( (LA9_0=='\'') ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // JXPath.g:240:13: '\"' (~ '\"' )* '\"'
                    {
                    match('\"'); 
                    // JXPath.g:240:17: (~ '\"' )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( ((LA7_0>='\u0000' && LA7_0<='!')||(LA7_0>='#' && LA7_0<='\uFFFF')) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // JXPath.g:240:17: ~ '\"'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 2 :
                    // JXPath.g:241:6: '\\'' (~ '\\'' )* '\\''
                    {
                    match('\''); 
                    // JXPath.g:241:11: (~ '\\'' )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='\u0000' && LA8_0<='&')||(LA8_0>='(' && LA8_0<='\uFFFF')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // JXPath.g:241:11: ~ '\\''
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Literal"

    // $ANTLR start "Whitespace"
    public final void mWhitespace() throws RecognitionException {
        try {
            int _type = Whitespace;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:245:3: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // JXPath.g:245:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // JXPath.g:245:6: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\t' && LA10_0<='\n')||LA10_0=='\r'||LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // JXPath.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Whitespace"

    // $ANTLR start "NCName"
    public final void mNCName() throws RecognitionException {
        try {
            int _type = NCName;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JXPath.g:248:9: ( NCNameStartChar ( NCNameChar )* )
            // JXPath.g:248:12: NCNameStartChar ( NCNameChar )*
            {
            mNCNameStartChar(); 
            // JXPath.g:248:28: ( NCNameChar )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='-' && LA11_0<='.')||(LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')||LA11_0=='\u00B7'||(LA11_0>='\u00C0' && LA11_0<='\u00D6')||(LA11_0>='\u00D8' && LA11_0<='\u00F6')||(LA11_0>='\u00F8' && LA11_0<='\u037D')||(LA11_0>='\u037F' && LA11_0<='\u1FFF')||(LA11_0>='\u200C' && LA11_0<='\u200D')||(LA11_0>='\u203F' && LA11_0<='\u2040')||(LA11_0>='\u2070' && LA11_0<='\u218F')||(LA11_0>='\u2C00' && LA11_0<='\u2FEF')||(LA11_0>='\u3001' && LA11_0<='\uD7FF')||(LA11_0>='\uF900' && LA11_0<='\uFDCF')||(LA11_0>='\uFDF0' && LA11_0<='\uFFFD')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // JXPath.g:248:28: NCNameChar
            	    {
            	    mNCNameChar(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NCName"

    // $ANTLR start "NCNameStartChar"
    public final void mNCNameStartChar() throws RecognitionException {
        try {
            // JXPath.g:253:3: ( 'A' .. 'Z' | '_' | 'a' .. 'z' | '\\u00C0' .. '\\u00D6' | '\\u00D8' .. '\\u00F6' | '\\u00F8' .. '\\u02FF' | '\\u0370' .. '\\u037D' | '\\u037F' .. '\\u1FFF' | '\\u200C' .. '\\u200D' | '\\u2070' .. '\\u218F' | '\\u2C00' .. '\\u2FEF' | '\\u3001' .. '\\uD7FF' | '\\uF900' .. '\\uFDCF' | '\\uFDF0' .. '\\uFFFD' )
            // JXPath.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u02FF')||(input.LA(1)>='\u0370' && input.LA(1)<='\u037D')||(input.LA(1)>='\u037F' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u200C' && input.LA(1)<='\u200D')||(input.LA(1)>='\u2070' && input.LA(1)<='\u218F')||(input.LA(1)>='\u2C00' && input.LA(1)<='\u2FEF')||(input.LA(1)>='\u3001' && input.LA(1)<='\uD7FF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFDCF')||(input.LA(1)>='\uFDF0' && input.LA(1)<='\uFFFD') ) {
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
    // $ANTLR end "NCNameStartChar"

    // $ANTLR start "NCNameChar"
    public final void mNCNameChar() throws RecognitionException {
        try {
            // JXPath.g:274:3: ( NCNameStartChar | '-' | '.' | '0' .. '9' | '\\u00B7' | '\\u0300' .. '\\u036F' | '\\u203F' .. '\\u2040' )
            // JXPath.g:
            {
            if ( (input.LA(1)>='-' && input.LA(1)<='.')||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='\u00B7'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u037D')||(input.LA(1)>='\u037F' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u200C' && input.LA(1)<='\u200D')||(input.LA(1)>='\u203F' && input.LA(1)<='\u2040')||(input.LA(1)>='\u2070' && input.LA(1)<='\u218F')||(input.LA(1)>='\u2C00' && input.LA(1)<='\u2FEF')||(input.LA(1)>='\u3001' && input.LA(1)<='\uD7FF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFDCF')||(input.LA(1)>='\uFDF0' && input.LA(1)<='\uFFFD') ) {
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
    // $ANTLR end "NCNameChar"

    public void mTokens() throws RecognitionException {
        // JXPath.g:1:8: ( T__52 | UNION | OR | AND | EQUAL | NOT_EQUAL | LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS | PLUS | MINUS | STAR | DIV | MOD | COLON | VAR_START | PATHSEP | ABRPATH | LPAR | RPAR | LBRAC | RBRAC | DOT | DOTDOT | AT | COMMA | CC | NodeType | Number | AxisName | Literal | Whitespace | NCName )
        int alt12=34;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // JXPath.g:1:10: T__52
                {
                mT__52(); 

                }
                break;
            case 2 :
                // JXPath.g:1:16: UNION
                {
                mUNION(); 

                }
                break;
            case 3 :
                // JXPath.g:1:22: OR
                {
                mOR(); 

                }
                break;
            case 4 :
                // JXPath.g:1:25: AND
                {
                mAND(); 

                }
                break;
            case 5 :
                // JXPath.g:1:29: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 6 :
                // JXPath.g:1:35: NOT_EQUAL
                {
                mNOT_EQUAL(); 

                }
                break;
            case 7 :
                // JXPath.g:1:45: LESS_THAN
                {
                mLESS_THAN(); 

                }
                break;
            case 8 :
                // JXPath.g:1:55: GREATER_THAN
                {
                mGREATER_THAN(); 

                }
                break;
            case 9 :
                // JXPath.g:1:68: LESS_THAN_EQUALS
                {
                mLESS_THAN_EQUALS(); 

                }
                break;
            case 10 :
                // JXPath.g:1:85: GREATER_THAN_EQUALS
                {
                mGREATER_THAN_EQUALS(); 

                }
                break;
            case 11 :
                // JXPath.g:1:105: PLUS
                {
                mPLUS(); 

                }
                break;
            case 12 :
                // JXPath.g:1:110: MINUS
                {
                mMINUS(); 

                }
                break;
            case 13 :
                // JXPath.g:1:116: STAR
                {
                mSTAR(); 

                }
                break;
            case 14 :
                // JXPath.g:1:121: DIV
                {
                mDIV(); 

                }
                break;
            case 15 :
                // JXPath.g:1:125: MOD
                {
                mMOD(); 

                }
                break;
            case 16 :
                // JXPath.g:1:129: COLON
                {
                mCOLON(); 

                }
                break;
            case 17 :
                // JXPath.g:1:135: VAR_START
                {
                mVAR_START(); 

                }
                break;
            case 18 :
                // JXPath.g:1:145: PATHSEP
                {
                mPATHSEP(); 

                }
                break;
            case 19 :
                // JXPath.g:1:153: ABRPATH
                {
                mABRPATH(); 

                }
                break;
            case 20 :
                // JXPath.g:1:161: LPAR
                {
                mLPAR(); 

                }
                break;
            case 21 :
                // JXPath.g:1:166: RPAR
                {
                mRPAR(); 

                }
                break;
            case 22 :
                // JXPath.g:1:171: LBRAC
                {
                mLBRAC(); 

                }
                break;
            case 23 :
                // JXPath.g:1:177: RBRAC
                {
                mRBRAC(); 

                }
                break;
            case 24 :
                // JXPath.g:1:183: DOT
                {
                mDOT(); 

                }
                break;
            case 25 :
                // JXPath.g:1:187: DOTDOT
                {
                mDOTDOT(); 

                }
                break;
            case 26 :
                // JXPath.g:1:194: AT
                {
                mAT(); 

                }
                break;
            case 27 :
                // JXPath.g:1:197: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 28 :
                // JXPath.g:1:203: CC
                {
                mCC(); 

                }
                break;
            case 29 :
                // JXPath.g:1:206: NodeType
                {
                mNodeType(); 

                }
                break;
            case 30 :
                // JXPath.g:1:215: Number
                {
                mNumber(); 

                }
                break;
            case 31 :
                // JXPath.g:1:222: AxisName
                {
                mAxisName(); 

                }
                break;
            case 32 :
                // JXPath.g:1:231: Literal
                {
                mLiteral(); 

                }
                break;
            case 33 :
                // JXPath.g:1:239: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 34 :
                // JXPath.g:1:250: NCName
                {
                mNCName(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA6_eotS =
        "\42\uffff\1\47\6\uffff\1\55\1\57\1\61\6\uffff";
    static final String DFA6_eofS =
        "\62\uffff";
    static final String DFA6_minS =
        "\1\141\1\156\1\uffff\1\145\1\157\1\uffff\1\141\1\uffff\1\143\1"+
        "\uffff\1\163\1\154\1\uffff\2\145\1\143\1\154\1\143\1\163\1\145\1"+
        "\157\1\145\1\164\1\156\1\167\1\144\1\157\1\144\2\151\1\162\1\141"+
        "\2\156\1\55\1\156\2\147\2\uffff\1\164\3\55\6\uffff";
    static final String DFA6_maxS =
        "\1\163\1\164\1\uffff\1\145\1\157\1\uffff\1\162\1\uffff\1\143\1"+
        "\uffff\1\163\1\154\1\uffff\2\145\1\143\1\154\1\143\1\163\1\145\1"+
        "\157\1\145\1\164\1\156\1\167\1\144\1\157\1\144\2\151\1\162\1\141"+
        "\2\156\1\55\1\156\2\147\2\uffff\1\164\3\55\6\uffff";
    static final String DFA6_acceptS =
        "\2\uffff\1\4\2\uffff\1\11\1\uffff\1\15\1\uffff\1\3\2\uffff\1\12"+
        "\31\uffff\1\2\1\1\4\uffff\1\10\1\7\1\14\1\13\1\6\1\5";
    static final String DFA6_specialS =
        "\62\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1\1\uffff\1\2\1\3\1\uffff\1\4\7\uffff\1\5\1\uffff\1\6\2"+
            "\uffff\1\7",
            "\1\10\5\uffff\1\11",
            "",
            "\1\12",
            "\1\13",
            "",
            "\1\14\20\uffff\1\15",
            "",
            "\1\16",
            "",
            "\1\17",
            "\1\20",
            "",
            "\1\21",
            "\1\22",
            "\1\23",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\50",
            "\1\51",
            "\1\52",
            "",
            "",
            "\1\53",
            "\1\54",
            "\1\56",
            "\1\60",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "225:1: AxisName : ( 'ancestor' | 'ancestor-or-self' | 'attribute' | 'child' | 'descendant' | 'descendant-or-self' | 'following' | 'following-sibling' | 'namespace' | 'parent' | 'preceding' | 'preceding-sibling' | 'self' );";
        }
    }
    static final String DFA12_eotS =
        "\1\uffff\1\40\1\uffff\2\40\2\uffff\1\47\1\51\3\uffff\2\40\1\56"+
        "\1\uffff\1\60\4\uffff\1\62\2\uffff\3\40\1\uffff\2\40\3\uffff\2\40"+
        "\1\75\2\40\4\uffff\3\40\6\uffff\12\40\1\uffff\1\116\2\40\1\121\1"+
        "\40\1\123\12\40\1\uffff\2\40\1\uffff\1\40\1\uffff\2\40\2\143\2\40"+
        "\1\146\7\40\1\146\1\uffff\2\40\1\uffff\2\40\1\146\13\40\1\143\4"+
        "\40\1\146\5\40\1\146\1\40\1\146\1\40\2\146\3\40\1\146\34\40\1\146"+
        "\3\40\1\146\1\40\1\146\1\40\1\146\3\40\1\u00b3\1\uffff";
    static final String DFA12_eofS =
        "\u00b4\uffff";
    static final String DFA12_minS =
        "\1\11\1\141\1\uffff\1\162\1\156\2\uffff\2\75\3\uffff\1\145\1\157"+
        "\1\72\1\uffff\1\57\4\uffff\1\56\2\uffff\1\150\1\145\1\141\1\uffff"+
        "\1\157\1\145\3\uffff\1\145\1\162\1\55\1\143\1\164\4\uffff\1\166"+
        "\1\163\1\144\6\uffff\1\155\1\151\1\170\1\144\1\155\2\154\2\143\1"+
        "\145\1\uffff\1\55\1\145\1\162\1\55\1\143\1\55\1\155\1\154\1\164"+
        "\2\145\1\154\1\146\2\145\1\156\1\uffff\1\163\1\151\1\uffff\1\145"+
        "\1\uffff\1\145\1\144\2\55\1\163\1\157\1\55\1\163\1\144\2\164\1\142"+
        "\2\156\1\55\1\uffff\1\160\1\167\1\uffff\1\163\1\151\1\55\1\157\1"+
        "\165\1\144\1\164\1\141\2\151\1\156\1\162\1\164\1\141\1\55\1\143"+
        "\2\156\1\147\1\55\1\145\1\156\1\145\2\147\1\55\1\157\1\55\1\164"+
        "\3\55\1\163\1\162\1\55\1\163\2\151\1\55\1\157\1\151\1\156\1\142"+
        "\1\163\1\162\1\142\1\163\1\154\1\145\1\55\1\154\1\164\1\151\1\154"+
        "\1\163\1\151\1\162\1\156\1\146\1\145\1\156\1\165\1\147\1\55\1\154"+
        "\1\147\1\143\1\55\1\146\1\55\1\164\1\55\1\151\1\157\1\156\1\55\1"+
        "\uffff";
    static final String DFA12_maxS =
        "\1\ufffd\1\162\1\uffff\1\162\1\164\2\uffff\2\75\3\uffff\1\151\1"+
        "\157\1\72\1\uffff\1\57\4\uffff\1\71\2\uffff\1\157\1\145\1\157\1"+
        "\uffff\1\157\1\145\3\uffff\1\157\1\162\1\ufffd\1\144\1\164\4\uffff"+
        "\1\166\1\163\1\144\6\uffff\1\155\1\151\1\170\1\144\1\155\2\154\2"+
        "\143\1\145\1\uffff\1\ufffd\1\145\1\162\1\ufffd\1\143\1\ufffd\1\155"+
        "\1\154\1\164\2\145\1\154\1\146\2\145\1\156\1\uffff\1\163\1\151\1"+
        "\uffff\1\145\1\uffff\1\145\1\144\2\ufffd\1\163\1\157\1\ufffd\1\163"+
        "\1\144\2\164\1\142\2\156\1\ufffd\1\uffff\1\160\1\167\1\uffff\1\163"+
        "\1\151\1\ufffd\1\157\1\165\1\144\1\164\1\141\2\151\1\156\1\162\1"+
        "\164\1\141\1\ufffd\1\143\2\156\1\147\1\ufffd\1\145\1\156\1\145\2"+
        "\147\1\ufffd\1\157\1\ufffd\1\164\2\ufffd\1\55\1\163\1\162\1\ufffd"+
        "\1\163\2\151\1\55\1\157\1\151\1\156\1\142\1\163\1\162\1\142\1\163"+
        "\1\154\1\145\1\55\1\154\1\164\1\151\1\154\1\163\1\151\1\162\1\156"+
        "\1\146\1\145\1\156\1\165\1\147\1\ufffd\1\154\1\147\1\143\1\ufffd"+
        "\1\146\1\ufffd\1\164\1\ufffd\1\151\1\157\1\156\1\ufffd\1\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\2\uffff\1\5\1\6\2\uffff\1\13\1\14\1\15\3\uffff\1\21"+
        "\1\uffff\1\24\1\25\1\26\1\27\1\uffff\1\32\1\33\3\uffff\1\36\2\uffff"+
        "\1\40\1\41\1\42\5\uffff\1\11\1\7\1\12\1\10\3\uffff\1\34\1\20\1\23"+
        "\1\22\1\31\1\30\12\uffff\1\3\20\uffff\1\4\2\uffff\1\16\1\uffff\1"+
        "\17\17\uffff\1\35\2\uffff\1\37\114\uffff\1\1";
    static final String DFA12_specialS =
        "\u00b4\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\37\2\uffff\1\37\22\uffff\1\37\1\6\1\36\1\uffff\1\17\2\uffff"+
            "\1\36\1\21\1\22\1\13\1\11\1\27\1\12\1\25\1\20\12\33\1\16\1\uffff"+
            "\1\7\1\5\1\10\1\uffff\1\26\32\40\1\23\1\uffff\1\24\1\uffff\1"+
            "\40\1\uffff\1\4\1\40\1\30\1\14\1\40\1\34\6\40\1\15\1\32\1\3"+
            "\1\1\2\40\1\35\1\31\6\40\1\uffff\1\2\103\uffff\27\40\1\uffff"+
            "\37\40\1\uffff\u0208\40\160\uffff\16\40\1\uffff\u1c81\40\14"+
            "\uffff\2\40\142\uffff\u0120\40\u0a70\uffff\u03f0\40\21\uffff"+
            "\ua7ff\40\u2100\uffff\u04d0\40\40\uffff\u020e\40",
            "\1\42\20\uffff\1\41",
            "",
            "\1\43",
            "\1\44\5\uffff\1\45",
            "",
            "",
            "\1\46",
            "\1\50",
            "",
            "",
            "",
            "\1\53\3\uffff\1\52",
            "\1\54",
            "\1\55",
            "",
            "\1\57",
            "",
            "",
            "",
            "",
            "\1\61\1\uffff\12\33",
            "",
            "",
            "\1\64\6\uffff\1\63",
            "\1\65",
            "\1\67\15\uffff\1\66",
            "",
            "\1\70",
            "\1\71",
            "",
            "",
            "",
            "\1\73\11\uffff\1\72",
            "\1\74",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\77\1\76",
            "\1\100",
            "",
            "",
            "",
            "",
            "\1\101",
            "\1\102",
            "\1\103",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\117",
            "\1\120",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\122",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "",
            "\1\136",
            "\1\137",
            "",
            "\1\140",
            "",
            "\1\141",
            "\1\142",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\144",
            "\1\145",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "",
            "\1\156",
            "\1\157",
            "",
            "\1\160",
            "\1\161",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081\1\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff"+
            "\32\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087\1\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff"+
            "\32\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u0088",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u0089",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u008a\1\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff"+
            "\32\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e\1\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff"+
            "\32\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u008f",
            "\1\u0090",
            "\1\u0091",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u00ae",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u00af",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\2\40\1\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40\74\uffff\1\40\10\uffff\27\40\1\uffff\37\40\1\uffff\u0286"+
            "\40\1\uffff\u1c81\40\14\uffff\2\40\61\uffff\2\40\57\uffff\u0120"+
            "\40\u0a70\uffff\u03f0\40\21\uffff\ua7ff\40\u2100\uffff\u04d0"+
            "\40\40\uffff\u020e\40",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__52 | UNION | OR | AND | EQUAL | NOT_EQUAL | LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS | PLUS | MINUS | STAR | DIV | MOD | COLON | VAR_START | PATHSEP | ABRPATH | LPAR | RPAR | LBRAC | RBRAC | DOT | DOTDOT | AT | COMMA | CC | NodeType | Number | AxisName | Literal | Whitespace | NCName );";
        }
    }
 

}