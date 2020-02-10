package com.tibco.cep.studio.core.rules.grammar;

// $ANTLR 3.2 Sep 23, 2009 12:02:23 BaseLexer.g 2015-06-26 10:31:24
 
//package com.tibco.cep.studio.core.grammar.concept; 

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.RulesSyntaxProblem;
import com.tibco.cep.studio.core.rules.ast.HeaderTreeAdaptor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class Rules_BaseLexer extends Lexer {
    public static final int MOD=123;
    public static final int T__167=167;
    public static final int TRY_STATEMENT=91;
    public static final int T__168=168;
    public static final int EOF=-1;
    public static final int T__165=165;
    public static final int T__166=166;
    public static final int STATEMENT=52;
    public static final int T__163=163;
    public static final int T__164=164;
    public static final int TYPE=38;
    public static final int ARRAY_ALLOCATOR=86;
    public static final int RPAREN=146;
    public static final int T__247=247;
    public static final int T__246=246;
    public static final int MappingEnd=105;
    public static final int T__248=248;
    public static final int INSTANCE_OF=112;
    public static final int MOD_EQUAL=142;
    public static final int LOCAL_VARIABLE_DECL=19;
    public static final int ARGS=43;
    public static final int EQ=110;
    public static final int VIRTUAL=40;
    public static final int DIVIDE=122;
    public static final int RBRACK=148;
    public static final int GE=116;
    public static final int RBRACE=118;
    public static final int IntegerTypeSuffix=150;
    public static final int MULT_EQUAL=140;
    public static final int RULE_BLOCK=26;
    public static final int SEMICOLON=107;
    public static final int HEADER_START=143;
    public static final int EXPRESSIONS=73;
    public static final int DISPLAY_PROPERTY=162;
    public static final int WS=134;
    public static final int FINALLY_RULE=81;
    public static final int ACTION_TYPE=15;
    public static final int FloatingPointLiteral=127;
    public static final int LHS=97;
    public static final int JavaIDDigit=158;
    public static final int STATEMENTS=28;
    public static final int GT=114;
    public static final int VALIDITY=60;
    public static final int ELSE_STATEMENT=51;
    public static final int DISPLAY_BLOCK=161;
    public static final int VOID_LITERAL=93;
    public static final int BLOCKS=34;
    public static final int BREAK_STATEMENT=74;
    public static final int T__215=215;
    public static final int T__216=216;
    public static final int T__213=213;
    public static final int T__214=214;
    public static final int T__219=219;
    public static final int LBRACK=147;
    public static final int T__217=217;
    public static final int TEMPLATE_DECLARATOR=6;
    public static final int T__218=218;
    public static final int DIV_EQUAL=141;
    public static final int ANNOTATION=42;
    public static final int LBRACE=117;
    public static final int SUFFIXES=102;
    public static final int HeaderSection=144;
    public static final int HexDigit=149;
    public static final int RANGE_START=70;
    public static final int DOMAIN_MODEL=11;
    public static final int T__223=223;
    public static final int T__222=222;
    public static final int LPAREN=145;
    public static final int T__221=221;
    public static final int MAPPING_BLOCK=56;
    public static final int T__220=220;
    public static final int T__202=202;
    public static final int WHEN_BLOCK=32;
    public static final int T__203=203;
    public static final int T__204=204;
    public static final int T__205=205;
    public static final int T__206=206;
    public static final int SUFFIX=46;
    public static final int T__207=207;
    public static final int T__208=208;
    public static final int IF_RULE=48;
    public static final int T__209=209;
    public static final int RANK_STATEMENT=61;
    public static final int PLUS=119;
    public static final int THEN_BLOCK=35;
    public static final int SUFFIX_EXPRESSION=104;
    public static final int HEADER=92;
    public static final int T__210=210;
    public static final int REQUEUE_STATEMENT=62;
    public static final int T__212=212;
    public static final int RETURN_TYPE=55;
    public static final int T__211=211;
    public static final int QUALIFIED_NAME=17;
    public static final int HexLiteral=131;
    public static final int T__239=239;
    public static final int T__237=237;
    public static final int T__238=238;
    public static final int T__235=235;
    public static final int T__236=236;
    public static final int QUALIFIER=16;
    public static final int CATCH_RULE=79;
    public static final int MINUS=120;
    public static final int T__245=245;
    public static final int Tokens=249;
    public static final int T__244=244;
    public static final int T__243=243;
    public static final int T__242=242;
    public static final int T__241=241;
    public static final int T__240=240;
    public static final int StringLiteral=128;
    public static final int T__228=228;
    public static final int FOR_LOOP=54;
    public static final int T__229=229;
    public static final int T__224=224;
    public static final int T__225=225;
    public static final int T__226=226;
    public static final int T__227=227;
    public static final int ACTION_CONTEXT_STATEMENT=14;
    public static final int BLOCK=78;
    public static final int T__232=232;
    public static final int T__231=231;
    public static final int T__234=234;
    public static final int OctalEscape=156;
    public static final int T__233=233;
    public static final int BINDING_VIEW_STATEMENT=8;
    public static final int THROW_STATEMENT=76;
    public static final int T__230=230;
    public static final int BINDINGS_BLOCK=7;
    public static final int PREFIX=101;
    public static final int LT=113;
    public static final int VALIDITY_STATEMENT=59;
    public static final int FloatTypeSuffix=152;
    public static final int MINUS_EQUAL=139;
    public static final int RULE_DECL=24;
    public static final int BODY_BLOCK=36;
    public static final int ARRAY_LITERAL=85;
    public static final int Identifier=106;
    public static final int PREDICATE_STATEMENT=68;
    public static final int NAME=25;
    public static final int PRIMARY_EXPRESSION=100;
    public static final int INCR=135;
    public static final int ACTION_CONTEXT_BLOCK=13;
    public static final int PRIMITIVE_TYPE=41;
    public static final int ASSIGNMENT_SUFFIX=103;
    public static final int BODY=88;
    public static final int COMMENT=159;
    public static final int FINALLY_CLAUSE=90;
    public static final int DECLARE_BLOCK=29;
    public static final int ANNOTATE=126;
    public static final int NE=111;
    public static final int UNARY_EXPRESSION_NODE=96;
    public static final int DECLARATORS=20;
    public static final int LOCAL_INITIALIZER=84;
    public static final int RULE=22;
    public static final int POUND=124;
    public static final int LINE_COMMENT=160;
    public static final int MULT=121;
    public static final int LASTMOD_STATEMENT=65;
    public static final int METHOD_CALL=44;
    public static final int RULE_TEMPLATE_DECL=5;
    public static final int RULE_FUNC_DECL=30;
    public static final int SCOPE_BLOCK=31;
    public static final int DECLARATOR=39;
    public static final int RANGE_EXPRESSION=69;
    public static final int SET_MEMBERSHIP_EXPRESSION=72;
    public static final int PREDICATES=33;
    public static final int T__200=200;
    public static final int BINDING_STATEMENT=10;
    public static final int ATTRIBUTE_BLOCK=27;
    public static final int PRIORITY=58;
    public static final int T__201=201;
    public static final int RULE_TEMPLATE=4;
    public static final int OR=108;
    public static final int BACKWARD_CHAIN_STATEMENT=64;
    public static final int BINARY_RELATION_NODE=95;
    public static final int PRIORITY_STATEMENT=57;
    public static final int CONTINUE_STATEMENT=75;
    public static final int FALSE=133;
    public static final int Letter=157;
    public static final int EscapeSequence=153;
    public static final int RHS=98;
    public static final int PRIMARY_ASSIGNMENT_EXPRESSION=45;
    public static final int NullLiteral=129;
    public static final int TRY_STATEMET=87;
    public static final int CharacterLiteral=154;
    public static final int WHILE_RULE=49;
    public static final int FOR_RULE=50;
    public static final int Exponent=151;
    public static final int AND=109;
    public static final int INITIALIZER=83;
    public static final int FORWARD_CHAIN_STATEMENT=63;
    public static final int RULE_FUNCTION=23;
    public static final int CATCH_CLAUSE=89;
    public static final int MODIFIERS=94;
    public static final int TRY_RULE=77;
    public static final int VARIABLE_DECLARATOR=21;
    public static final int T__199=199;
    public static final int T__198=198;
    public static final int T__197=197;
    public static final int RETURN_STATEMENT=53;
    public static final int T__196=196;
    public static final int T__195=195;
    public static final int T__194=194;
    public static final int T__193=193;
    public static final int T__192=192;
    public static final int T__191=191;
    public static final int IDENTIFIER=80;
    public static final int T__190=190;
    public static final int ARRAY_ACCESS_SUFFIX=82;
    public static final int PLUS_EQUAL=138;
    public static final int DOT=125;
    public static final int EXPRESSION=47;
    public static final int SCOPE_DECLARATOR=37;
    public static final int T__184=184;
    public static final int RANGE_END=71;
    public static final int T__183=183;
    public static final int T__186=186;
    public static final int T__185=185;
    public static final int T__188=188;
    public static final int T__187=187;
    public static final int T__189=189;
    public static final int OPERATOR=99;
    public static final int T__180=180;
    public static final int ALIAS_STATEMENT=66;
    public static final int T__182=182;
    public static final int T__181=181;
    public static final int LITERAL=67;
    public static final int DecimalLiteral=130;
    public static final int TRUE=132;
    public static final int T__175=175;
    public static final int T__174=174;
    public static final int T__173=173;
    public static final int SIMPLE_NAME=18;
    public static final int T__172=172;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int DOMAIN_MODEL_STATEMENT=12;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int UnicodeEscape=155;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int ASSIGN=137;
    public static final int DECR=136;
    public static final int BINDINGS_VIEWS_BLOCK=9;
    public static final int T__169=169;
    public static final int LE=115;

    private static final int HEADER_CHANNEL = 1;

    private RulesASTNode headerTree = null;
    private IProblemHandler fHandler = null;

    public RulesASTNode getHeaderNode() {
    	return headerTree;
    }

    public void setProblemHandler(IProblemHandler handler) {
    	this.fHandler = handler;
    }

    public IProblemHandler getProblemHandler() {
    	return fHandler;
    }

    @Override
    public void reportError(RecognitionException exception) {
    	if (exception instanceof MismatchedTokenException) {
    		if (exception.getUnexpectedType() == EOF) {
    			return;
    		}
    	}
    	super.reportError(exception);
    	RulesSyntaxProblem problem = new RulesSyntaxProblem(
    			getErrorMessage(exception, getTokenNames()),
    			exception);
    	if (fHandler != null) {
    		fHandler.handleProblem(problem);
    	}
    }



    // delegates
    // delegators
    public RulesLexer gRules;
    public RulesLexer gParent;

    public Rules_BaseLexer() {;} 
    public Rules_BaseLexer(CharStream input, RulesLexer gRules) {
        this(input, new RecognizerSharedState(), gRules);
    }
    public Rules_BaseLexer(CharStream input, RecognizerSharedState state, RulesLexer gRules) {
        super(input,state);

        this.gRules = gRules;
        gParent = gRules;
    }
    public String getGrammarFileName() { return "BaseLexer.g"; }

    // $ANTLR start "HeaderSection"
    public final void mHeaderSection() throws RecognitionException {
        try {
            int _type = HeaderSection;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:62:2: ( HEADER_START )
            // BaseLexer.g:62:4: HEADER_START
            {
            mHEADER_START(); 

            	   /*
            	   	create a separate lexer/parser for 'javadoc-like' comments,
            	   	so we can parse the description/author/etc for a rule, but
            	   	not have to worry about mixing keywords, identifiers, etc
            	   */
                        HeaderLexer j = new HeaderLexer(input);
                        CommonTokenStream tokens = new CommonTokenStream(j);
                        //tokens.discardTokenType(HeaderLexer.WS);
                        // tokens.discardTokenType(HeaderLexer.WS_STAR); // I turn these into NEWLINES for rewrite purposes
                        
                        HeaderParser p = new HeaderParser(tokens);
                        p.setTreeAdaptor(new HeaderTreeAdaptor());
                        HeaderParser.header_return header = p.header();
                        if (headerTree == null) {
                        	// the first 'Header' section wins
                        	headerTree = (RulesASTNode) header.getTree();
                        	headerTree.setHidden(true);
                        }
                        _channel = HEADER_CHANNEL;
                      

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HeaderSection"

    // $ANTLR start "MappingEnd"
    public final void mMappingEnd() throws RecognitionException {
        try {
            int _type = MappingEnd;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:87:2: ( '</#mapping>' )
            // BaseLexer.g:87:4: '</#mapping>'
            {
            match("</#mapping>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MappingEnd"

    // $ANTLR start "INSTANCE_OF"
    public final void mINSTANCE_OF() throws RecognitionException {
        try {
            int _type = INSTANCE_OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:91:13: ( 'instanceof' )
            // BaseLexer.g:91:15: 'instanceof'
            {
            match("instanceof"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSTANCE_OF"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:92:6: ( 'true' )
            // BaseLexer.g:92:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:93:7: ( 'false' )
            // BaseLexer.g:93:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:94:6: ( '+' )
            // BaseLexer.g:94:8: '+'
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
            // BaseLexer.g:95:7: ( '-' )
            // BaseLexer.g:95:9: '-'
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

    // $ANTLR start "MULT"
    public final void mMULT() throws RecognitionException {
        try {
            int _type = MULT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:96:6: ( '*' )
            // BaseLexer.g:96:8: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULT"

    // $ANTLR start "DIVIDE"
    public final void mDIVIDE() throws RecognitionException {
        try {
            int _type = DIVIDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:97:8: ( '/' )
            // BaseLexer.g:97:10: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIVIDE"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:98:6: ( '%' )
            // BaseLexer.g:98:8: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "POUND"
    public final void mPOUND() throws RecognitionException {
        try {
            int _type = POUND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:99:7: ( '!' )
            // BaseLexer.g:99:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "POUND"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:100:9: ( '=' )
            // BaseLexer.g:100:11: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSIGN"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:101:6: ( '.' )
            // BaseLexer.g:101:8: '.'
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

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:102:12: ( ';' )
            // BaseLexer.g:102:14: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:103:8: ( '{' )
            // BaseLexer.g:103:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:104:8: ( '}' )
            // BaseLexer.g:104:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:105:8: ( '(' )
            // BaseLexer.g:105:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:106:8: ( ')' )
            // BaseLexer.g:106:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LBRACK"
    public final void mLBRACK() throws RecognitionException {
        try {
            int _type = LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:107:8: ( '[' )
            // BaseLexer.g:107:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACK"

    // $ANTLR start "RBRACK"
    public final void mRBRACK() throws RecognitionException {
        try {
            int _type = RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:108:8: ( ']' )
            // BaseLexer.g:108:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACK"

    // $ANTLR start "ANNOTATE"
    public final void mANNOTATE() throws RecognitionException {
        try {
            int _type = ANNOTATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:109:10: ( '@' )
            // BaseLexer.g:109:13: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANNOTATE"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:110:6: ( '&&' )
            // BaseLexer.g:110:8: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:111:4: ( '||' )
            // BaseLexer.g:111:6: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:112:4: ( '==' )
            // BaseLexer.g:112:6: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "NE"
    public final void mNE() throws RecognitionException {
        try {
            int _type = NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:113:4: ( '!=' | 'not' 'equal' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='!') ) {
                alt1=1;
            }
            else if ( (LA1_0=='n') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // BaseLexer.g:113:6: '!='
                    {
                    match("!="); 


                    }
                    break;
                case 2 :
                    // BaseLexer.g:113:13: 'not' 'equal'
                    {
                    match("not"); 

                    match("equal"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NE"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:114:4: ( '<' | 'less' 'than' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='<') ) {
                alt2=1;
            }
            else if ( (LA2_0=='l') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // BaseLexer.g:114:6: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // BaseLexer.g:114:12: 'less' 'than'
                    {
                    match("less"); 

                    match("than"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:115:4: ( '>' | 'greater' 'than' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='>') ) {
                alt3=1;
            }
            else if ( (LA3_0=='g') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // BaseLexer.g:115:6: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 2 :
                    // BaseLexer.g:115:12: 'greater' 'than'
                    {
                    match("greater"); 

                    match("than"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:116:4: ( '<=' | 'less' 'than' 'or' 'equal' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='<') ) {
                alt4=1;
            }
            else if ( (LA4_0=='l') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // BaseLexer.g:116:6: '<='
                    {
                    match("<="); 


                    }
                    break;
                case 2 :
                    // BaseLexer.g:116:13: 'less' 'than' 'or' 'equal'
                    {
                    match("less"); 

                    match("than"); 

                    match("or"); 

                    match("equal"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LE"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:117:4: ( '>=' | 'greater' 'than' 'or' 'equal' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='>') ) {
                alt5=1;
            }
            else if ( (LA5_0=='g') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // BaseLexer.g:117:6: '>='
                    {
                    match(">="); 


                    }
                    break;
                case 2 :
                    // BaseLexer.g:117:13: 'greater' 'than' 'or' 'equal'
                    {
                    match("greater"); 

                    match("than"); 

                    match("or"); 

                    match("equal"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GE"

    // $ANTLR start "PLUS_EQUAL"
    public final void mPLUS_EQUAL() throws RecognitionException {
        try {
            int _type = PLUS_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:118:13: ( '+=' )
            // BaseLexer.g:118:15: '+='
            {
            match("+="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS_EQUAL"

    // $ANTLR start "MINUS_EQUAL"
    public final void mMINUS_EQUAL() throws RecognitionException {
        try {
            int _type = MINUS_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:119:14: ( '-=' )
            // BaseLexer.g:119:16: '-='
            {
            match("-="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS_EQUAL"

    // $ANTLR start "MULT_EQUAL"
    public final void mMULT_EQUAL() throws RecognitionException {
        try {
            int _type = MULT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:120:13: ( '*=' )
            // BaseLexer.g:120:15: '*='
            {
            match("*="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MULT_EQUAL"

    // $ANTLR start "DIV_EQUAL"
    public final void mDIV_EQUAL() throws RecognitionException {
        try {
            int _type = DIV_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:121:12: ( '/=' )
            // BaseLexer.g:121:14: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV_EQUAL"

    // $ANTLR start "MOD_EQUAL"
    public final void mMOD_EQUAL() throws RecognitionException {
        try {
            int _type = MOD_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:122:12: ( '%=' )
            // BaseLexer.g:122:14: '%='
            {
            match("%="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD_EQUAL"

    // $ANTLR start "INCR"
    public final void mINCR() throws RecognitionException {
        try {
            int _type = INCR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:123:7: ( '++' )
            // BaseLexer.g:123:9: '++'
            {
            match("++"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INCR"

    // $ANTLR start "DECR"
    public final void mDECR() throws RecognitionException {
        try {
            int _type = DECR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:124:6: ( '--' )
            // BaseLexer.g:124:8: '--'
            {
            match("--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DECR"

    // $ANTLR start "NullLiteral"
    public final void mNullLiteral() throws RecognitionException {
        try {
            int _type = NullLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:125:13: ( 'null' )
            // BaseLexer.g:125:15: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NullLiteral"

    // $ANTLR start "HexLiteral"
    public final void mHexLiteral() throws RecognitionException {
        try {
            int _type = HexLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:127:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // BaseLexer.g:127:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // BaseLexer.g:127:28: ( HexDigit )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')||(LA6_0>='A' && LA6_0<='F')||(LA6_0>='a' && LA6_0<='f')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // BaseLexer.g:127:28: HexDigit
            	    {
            	    mHexDigit(); 

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

            // BaseLexer.g:127:38: ( IntegerTypeSuffix )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='L'||LA7_0=='l') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // BaseLexer.g:127:38: IntegerTypeSuffix
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

    // $ANTLR start "DecimalLiteral"
    public final void mDecimalLiteral() throws RecognitionException {
        try {
            int _type = DecimalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:129:16: ( ( '0' .. '9' )+ ( IntegerTypeSuffix )? )
            // BaseLexer.g:129:18: ( '0' .. '9' )+ ( IntegerTypeSuffix )?
            {
            // BaseLexer.g:129:18: ( '0' .. '9' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // BaseLexer.g:129:19: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            // BaseLexer.g:129:30: ( IntegerTypeSuffix )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='L'||LA9_0=='l') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // BaseLexer.g:129:30: IntegerTypeSuffix
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

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // BaseLexer.g:134:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // BaseLexer.g:134:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // BaseLexer.g:137:19: ( ( 'l' | 'L' ) )
            // BaseLexer.g:137:21: ( 'l' | 'L' )
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

    // $ANTLR start "FloatingPointLiteral"
    public final void mFloatingPointLiteral() throws RecognitionException {
        try {
            int _type = FloatingPointLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:140:5: ( ( '0' .. '9' )+ DOT ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | DOT ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix )
            int alt20=4;
            alt20 = dfa20.predict(input);
            switch (alt20) {
                case 1 :
                    // BaseLexer.g:140:9: ( '0' .. '9' )+ DOT ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )?
                    {
                    // BaseLexer.g:140:9: ( '0' .. '9' )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // BaseLexer.g:140:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

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

                    mDOT(); 
                    // BaseLexer.g:140:25: ( '0' .. '9' )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // BaseLexer.g:140:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    // BaseLexer.g:140:37: ( Exponent )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='E'||LA12_0=='e') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // BaseLexer.g:140:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // BaseLexer.g:140:47: ( FloatTypeSuffix )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='D'||LA13_0=='F'||LA13_0=='d'||LA13_0=='f') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // BaseLexer.g:140:47: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // BaseLexer.g:141:9: DOT ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )?
                    {
                    mDOT(); 
                    // BaseLexer.g:141:13: ( '0' .. '9' )+
                    int cnt14=0;
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // BaseLexer.g:141:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt14 >= 1 ) break loop14;
                                EarlyExitException eee =
                                    new EarlyExitException(14, input);
                                throw eee;
                        }
                        cnt14++;
                    } while (true);

                    // BaseLexer.g:141:25: ( Exponent )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='E'||LA15_0=='e') ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // BaseLexer.g:141:25: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // BaseLexer.g:141:35: ( FloatTypeSuffix )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='D'||LA16_0=='F'||LA16_0=='d'||LA16_0=='f') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // BaseLexer.g:141:35: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // BaseLexer.g:142:9: ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )?
                    {
                    // BaseLexer.g:142:9: ( '0' .. '9' )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // BaseLexer.g:142:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);

                    mExponent(); 
                    // BaseLexer.g:142:30: ( FloatTypeSuffix )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='D'||LA18_0=='F'||LA18_0=='d'||LA18_0=='f') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // BaseLexer.g:142:30: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // BaseLexer.g:143:9: ( '0' .. '9' )+ FloatTypeSuffix
                    {
                    // BaseLexer.g:143:9: ( '0' .. '9' )+
                    int cnt19=0;
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( ((LA19_0>='0' && LA19_0<='9')) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // BaseLexer.g:143:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt19 >= 1 ) break loop19;
                                EarlyExitException eee =
                                    new EarlyExitException(19, input);
                                throw eee;
                        }
                        cnt19++;
                    } while (true);

                    mFloatTypeSuffix(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FloatingPointLiteral"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // BaseLexer.g:147:10: ( ( 'e' | 'E' ) ( PLUS | MINUS )? ( '0' .. '9' )+ )
            // BaseLexer.g:147:12: ( 'e' | 'E' ) ( PLUS | MINUS )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // BaseLexer.g:147:22: ( PLUS | MINUS )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='+'||LA21_0=='-') ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // BaseLexer.g:
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

            // BaseLexer.g:147:36: ( '0' .. '9' )+
            int cnt22=0;
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>='0' && LA22_0<='9')) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // BaseLexer.g:147:37: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                            new EarlyExitException(22, input);
                        throw eee;
                }
                cnt22++;
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
            // BaseLexer.g:150:17: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // BaseLexer.g:150:19: ( 'f' | 'F' | 'd' | 'D' )
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

    // $ANTLR start "CharacterLiteral"
    public final void mCharacterLiteral() throws RecognitionException {
        try {
            int _type = CharacterLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:153:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            // BaseLexer.g:153:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 
            // BaseLexer.g:153:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='\\') ) {
                alt23=1;
            }
            else if ( ((LA23_0>='\u0000' && LA23_0<='&')||(LA23_0>='(' && LA23_0<='[')||(LA23_0>=']' && LA23_0<='\uFFFF')) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // BaseLexer.g:153:16: EscapeSequence
                    {
                    mEscapeSequence(); 

                    }
                    break;
                case 2 :
                    // BaseLexer.g:153:33: ~ ( '\\'' | '\\\\' )
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

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CharacterLiteral"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:157:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' | '\\n' | '\\r' ) )* '\"' )
            // BaseLexer.g:157:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' | '\\n' | '\\r' ) )* '\"'
            {
            match('\"'); 
            // BaseLexer.g:157:12: ( EscapeSequence | ~ ( '\\\\' | '\"' | '\\n' | '\\r' ) )*
            loop24:
            do {
                int alt24=3;
                int LA24_0 = input.LA(1);

                if ( (LA24_0=='\\') ) {
                    alt24=1;
                }
                else if ( ((LA24_0>='\u0000' && LA24_0<='\t')||(LA24_0>='\u000B' && LA24_0<='\f')||(LA24_0>='\u000E' && LA24_0<='!')||(LA24_0>='#' && LA24_0<='[')||(LA24_0>=']' && LA24_0<='\uFFFF')) ) {
                    alt24=2;
                }


                switch (alt24) {
            	case 1 :
            	    // BaseLexer.g:157:14: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // BaseLexer.g:157:31: ~ ( '\\\\' | '\"' | '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // BaseLexer.g:162:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UnicodeEscape | OctalEscape )
            int alt25=3;
            int LA25_0 = input.LA(1);

            if ( (LA25_0=='\\') ) {
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
                    alt25=1;
                    }
                    break;
                case 'u':
                    {
                    alt25=2;
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
                    alt25=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 25, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // BaseLexer.g:162:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
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
                    // BaseLexer.g:163:9: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // BaseLexer.g:164:9: OctalEscape
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

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // BaseLexer.g:169:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt26=3;
            int LA26_0 = input.LA(1);

            if ( (LA26_0=='\\') ) {
                int LA26_1 = input.LA(2);

                if ( ((LA26_1>='0' && LA26_1<='3')) ) {
                    int LA26_2 = input.LA(3);

                    if ( ((LA26_2>='0' && LA26_2<='7')) ) {
                        int LA26_4 = input.LA(4);

                        if ( ((LA26_4>='0' && LA26_4<='7')) ) {
                            alt26=1;
                        }
                        else {
                            alt26=2;}
                    }
                    else {
                        alt26=3;}
                }
                else if ( ((LA26_1>='4' && LA26_1<='7')) ) {
                    int LA26_3 = input.LA(3);

                    if ( ((LA26_3>='0' && LA26_3<='7')) ) {
                        alt26=2;
                    }
                    else {
                        alt26=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // BaseLexer.g:169:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // BaseLexer.g:169:14: ( '0' .. '3' )
                    // BaseLexer.g:169:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // BaseLexer.g:169:25: ( '0' .. '7' )
                    // BaseLexer.g:169:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // BaseLexer.g:169:36: ( '0' .. '7' )
                    // BaseLexer.g:169:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // BaseLexer.g:170:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // BaseLexer.g:170:14: ( '0' .. '7' )
                    // BaseLexer.g:170:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // BaseLexer.g:170:25: ( '0' .. '7' )
                    // BaseLexer.g:170:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // BaseLexer.g:171:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // BaseLexer.g:171:14: ( '0' .. '7' )
                    // BaseLexer.g:171:15: '0' .. '7'
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
            // BaseLexer.g:176:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // BaseLexer.g:176:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:187:5: ( Letter ( Letter | JavaIDDigit )* )
            // BaseLexer.g:187:9: Letter ( Letter | JavaIDDigit )*
            {
            mLetter(); 
            // BaseLexer.g:187:16: ( Letter | JavaIDDigit )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0=='\u0000'||LA27_0=='$'||(LA27_0>='0' && LA27_0<='9')||(LA27_0>='A' && LA27_0<='Z')||LA27_0=='_'||(LA27_0>='a' && LA27_0<='z')||(LA27_0>='\u00A2' && LA27_0<='\u00A5')||LA27_0=='\u00AA'||LA27_0=='\u00B5'||LA27_0=='\u00BA'||(LA27_0>='\u00C0' && LA27_0<='\u00D6')||(LA27_0>='\u00D8' && LA27_0<='\u00F6')||(LA27_0>='\u00F8' && LA27_0<='\u1FFF')||(LA27_0>='\u203F' && LA27_0<='\u2040')||LA27_0=='\u2054'||LA27_0=='\u2071'||LA27_0=='\u207F'||(LA27_0>='\u2090' && LA27_0<='\u209C')||(LA27_0>='\u20A0' && LA27_0<='\u20B9')||LA27_0=='\u2102'||LA27_0=='\u2107'||(LA27_0>='\u210A' && LA27_0<='\u2113')||LA27_0=='\u2115'||(LA27_0>='\u2119' && LA27_0<='\u211D')||LA27_0=='\u2124'||LA27_0=='\u2126'||LA27_0=='\u2128'||(LA27_0>='\u212A' && LA27_0<='\u212D')||(LA27_0>='\u212F' && LA27_0<='\u2139')||(LA27_0>='\u213C' && LA27_0<='\u213F')||(LA27_0>='\u2145' && LA27_0<='\u2149')||LA27_0=='\u214E'||(LA27_0>='\u2160' && LA27_0<='\u2188')||(LA27_0>='\u2C00' && LA27_0<='\u2C2E')||(LA27_0>='\u2C30' && LA27_0<='\u2C5E')||(LA27_0>='\u2C60' && LA27_0<='\u2CE4')||(LA27_0>='\u2CEB' && LA27_0<='\u2CEE')||(LA27_0>='\u2D00' && LA27_0<='\u2D25')||(LA27_0>='\u2D30' && LA27_0<='\u2D65')||LA27_0=='\u2D6F'||(LA27_0>='\u2D80' && LA27_0<='\u2D96')||(LA27_0>='\u2DA0' && LA27_0<='\u2DA6')||(LA27_0>='\u2DA8' && LA27_0<='\u2DAE')||(LA27_0>='\u2DB0' && LA27_0<='\u2DB6')||(LA27_0>='\u2DB8' && LA27_0<='\u2DBE')||(LA27_0>='\u2DC0' && LA27_0<='\u2DC6')||(LA27_0>='\u2DC8' && LA27_0<='\u2DCE')||(LA27_0>='\u2DD0' && LA27_0<='\u2DD6')||(LA27_0>='\u2DD8' && LA27_0<='\u2DDE')||LA27_0=='\u2E2F'||(LA27_0>='\u3005' && LA27_0<='\u3007')||(LA27_0>='\u3021' && LA27_0<='\u3029')||(LA27_0>='\u3031' && LA27_0<='\u3035')||(LA27_0>='\u3038' && LA27_0<='\u303C')||(LA27_0>='\u3040' && LA27_0<='\u318F')||(LA27_0>='\u31A0' && LA27_0<='\u31BA')||(LA27_0>='\u31F0' && LA27_0<='\u31FF')||(LA27_0>='\u3300' && LA27_0<='\u337F')||(LA27_0>='\u3400' && LA27_0<='\u4DB5')||(LA27_0>='\u4E00' && LA27_0<='\uA48C')||(LA27_0>='\uA4D0' && LA27_0<='\uA4FD')||(LA27_0>='\uA500' && LA27_0<='\uA60C')||(LA27_0>='\uA610' && LA27_0<='\uA61F')||(LA27_0>='\uA62A' && LA27_0<='\uA62B')||(LA27_0>='\uA640' && LA27_0<='\uA66E')||(LA27_0>='\uA67F' && LA27_0<='\uA697')||(LA27_0>='\uA6A0' && LA27_0<='\uA6EF')||(LA27_0>='\uA717' && LA27_0<='\uA71F')||(LA27_0>='\uA722' && LA27_0<='\uA788')||(LA27_0>='\uA78B' && LA27_0<='\uA78E')||(LA27_0>='\uA790' && LA27_0<='\uA791')||(LA27_0>='\uA7A0' && LA27_0<='\uA7A9')||(LA27_0>='\uA7FA' && LA27_0<='\uA801')||(LA27_0>='\uA803' && LA27_0<='\uA805')||(LA27_0>='\uA807' && LA27_0<='\uA80A')||(LA27_0>='\uA80C' && LA27_0<='\uA822')||LA27_0=='\uA838'||(LA27_0>='\uA840' && LA27_0<='\uA873')||(LA27_0>='\uA882' && LA27_0<='\uA8B3')||(LA27_0>='\uA8F2' && LA27_0<='\uA8F7')||LA27_0=='\uA8FB'||(LA27_0>='\uA90A' && LA27_0<='\uA925')||(LA27_0>='\uA930' && LA27_0<='\uA946')||(LA27_0>='\uA960' && LA27_0<='\uA97C')||(LA27_0>='\uA984' && LA27_0<='\uA9B2')||LA27_0=='\uA9CF'||(LA27_0>='\uAA00' && LA27_0<='\uAA28')||(LA27_0>='\uAA40' && LA27_0<='\uAA42')||(LA27_0>='\uAA44' && LA27_0<='\uAA4B')||(LA27_0>='\uAA60' && LA27_0<='\uAA76')||LA27_0=='\uAA7A'||(LA27_0>='\uAA80' && LA27_0<='\uAAAF')||LA27_0=='\uAAB1'||(LA27_0>='\uAAB5' && LA27_0<='\uAAB6')||(LA27_0>='\uAAB9' && LA27_0<='\uAABD')||LA27_0=='\uAAC0'||LA27_0=='\uAAC2'||(LA27_0>='\uAADB' && LA27_0<='\uAADD')||(LA27_0>='\uAB01' && LA27_0<='\uAB06')||(LA27_0>='\uAB09' && LA27_0<='\uAB0E')||(LA27_0>='\uAB11' && LA27_0<='\uAB16')||(LA27_0>='\uAB20' && LA27_0<='\uAB26')||(LA27_0>='\uAB28' && LA27_0<='\uAB2E')||(LA27_0>='\uABC0' && LA27_0<='\uABE2')||(LA27_0>='\uAC00' && LA27_0<='\uD7A3')||(LA27_0>='\uD7B0' && LA27_0<='\uD7C6')||(LA27_0>='\uD7CB' && LA27_0<='\uD7FB')||(LA27_0>='\uF900' && LA27_0<='\uFB06')||(LA27_0>='\uFB13' && LA27_0<='\uFB17')||LA27_0=='\uFB1D'||(LA27_0>='\uFB1F' && LA27_0<='\uFB28')||(LA27_0>='\uFB2A' && LA27_0<='\uFB36')||(LA27_0>='\uFB38' && LA27_0<='\uFB3C')||LA27_0=='\uFB3E'||(LA27_0>='\uFB40' && LA27_0<='\uFB41')||(LA27_0>='\uFB43' && LA27_0<='\uFB44')||(LA27_0>='\uFB46' && LA27_0<='\uFBB1')||(LA27_0>='\uFBD3' && LA27_0<='\uFD3D')||(LA27_0>='\uFD50' && LA27_0<='\uFD8F')||(LA27_0>='\uFD92' && LA27_0<='\uFDC7')||(LA27_0>='\uFDF0' && LA27_0<='\uFDFC')||(LA27_0>='\uFE33' && LA27_0<='\uFE34')||(LA27_0>='\uFE4D' && LA27_0<='\uFE4F')||LA27_0=='\uFE69'||(LA27_0>='\uFE70' && LA27_0<='\uFE74')||(LA27_0>='\uFE76' && LA27_0<='\uFEFC')||LA27_0=='\uFF04'||(LA27_0>='\uFF21' && LA27_0<='\uFF3A')||LA27_0=='\uFF3F'||(LA27_0>='\uFF41' && LA27_0<='\uFF5A')||(LA27_0>='\uFF61' && LA27_0<='\uFFBE')||(LA27_0>='\uFFC2' && LA27_0<='\uFFC7')||(LA27_0>='\uFFCA' && LA27_0<='\uFFCF')||(LA27_0>='\uFFD2' && LA27_0<='\uFFD7')||(LA27_0>='\uFFDA' && LA27_0<='\uFFDC')||(LA27_0>='\uFFE0' && LA27_0<='\uFFE1')||(LA27_0>='\uFFE5' && LA27_0<='\uFFE6')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // BaseLexer.g:
            	    {
            	    if ( input.LA(1)=='\u0000'||input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00A2' && input.LA(1)<='\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u203F' && input.LA(1)<='\u2040')||input.LA(1)=='\u2054'||input.LA(1)=='\u2071'||input.LA(1)=='\u207F'||(input.LA(1)>='\u2090' && input.LA(1)<='\u209C')||(input.LA(1)>='\u20A0' && input.LA(1)<='\u20B9')||input.LA(1)=='\u2102'||input.LA(1)=='\u2107'||(input.LA(1)>='\u210A' && input.LA(1)<='\u2113')||input.LA(1)=='\u2115'||(input.LA(1)>='\u2119' && input.LA(1)<='\u211D')||input.LA(1)=='\u2124'||input.LA(1)=='\u2126'||input.LA(1)=='\u2128'||(input.LA(1)>='\u212A' && input.LA(1)<='\u212D')||(input.LA(1)>='\u212F' && input.LA(1)<='\u2139')||(input.LA(1)>='\u213C' && input.LA(1)<='\u213F')||(input.LA(1)>='\u2145' && input.LA(1)<='\u2149')||input.LA(1)=='\u214E'||(input.LA(1)>='\u2160' && input.LA(1)<='\u2188')||(input.LA(1)>='\u2C00' && input.LA(1)<='\u2C2E')||(input.LA(1)>='\u2C30' && input.LA(1)<='\u2C5E')||(input.LA(1)>='\u2C60' && input.LA(1)<='\u2CE4')||(input.LA(1)>='\u2CEB' && input.LA(1)<='\u2CEE')||(input.LA(1)>='\u2D00' && input.LA(1)<='\u2D25')||(input.LA(1)>='\u2D30' && input.LA(1)<='\u2D65')||input.LA(1)=='\u2D6F'||(input.LA(1)>='\u2D80' && input.LA(1)<='\u2D96')||(input.LA(1)>='\u2DA0' && input.LA(1)<='\u2DA6')||(input.LA(1)>='\u2DA8' && input.LA(1)<='\u2DAE')||(input.LA(1)>='\u2DB0' && input.LA(1)<='\u2DB6')||(input.LA(1)>='\u2DB8' && input.LA(1)<='\u2DBE')||(input.LA(1)>='\u2DC0' && input.LA(1)<='\u2DC6')||(input.LA(1)>='\u2DC8' && input.LA(1)<='\u2DCE')||(input.LA(1)>='\u2DD0' && input.LA(1)<='\u2DD6')||(input.LA(1)>='\u2DD8' && input.LA(1)<='\u2DDE')||input.LA(1)=='\u2E2F'||(input.LA(1)>='\u3005' && input.LA(1)<='\u3007')||(input.LA(1)>='\u3021' && input.LA(1)<='\u3029')||(input.LA(1)>='\u3031' && input.LA(1)<='\u3035')||(input.LA(1)>='\u3038' && input.LA(1)<='\u303C')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u31A0' && input.LA(1)<='\u31BA')||(input.LA(1)>='\u31F0' && input.LA(1)<='\u31FF')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u4DB5')||(input.LA(1)>='\u4E00' && input.LA(1)<='\uA48C')||(input.LA(1)>='\uA4D0' && input.LA(1)<='\uA4FD')||(input.LA(1)>='\uA500' && input.LA(1)<='\uA60C')||(input.LA(1)>='\uA610' && input.LA(1)<='\uA61F')||(input.LA(1)>='\uA62A' && input.LA(1)<='\uA62B')||(input.LA(1)>='\uA640' && input.LA(1)<='\uA66E')||(input.LA(1)>='\uA67F' && input.LA(1)<='\uA697')||(input.LA(1)>='\uA6A0' && input.LA(1)<='\uA6EF')||(input.LA(1)>='\uA717' && input.LA(1)<='\uA71F')||(input.LA(1)>='\uA722' && input.LA(1)<='\uA788')||(input.LA(1)>='\uA78B' && input.LA(1)<='\uA78E')||(input.LA(1)>='\uA790' && input.LA(1)<='\uA791')||(input.LA(1)>='\uA7A0' && input.LA(1)<='\uA7A9')||(input.LA(1)>='\uA7FA' && input.LA(1)<='\uA801')||(input.LA(1)>='\uA803' && input.LA(1)<='\uA805')||(input.LA(1)>='\uA807' && input.LA(1)<='\uA80A')||(input.LA(1)>='\uA80C' && input.LA(1)<='\uA822')||input.LA(1)=='\uA838'||(input.LA(1)>='\uA840' && input.LA(1)<='\uA873')||(input.LA(1)>='\uA882' && input.LA(1)<='\uA8B3')||(input.LA(1)>='\uA8F2' && input.LA(1)<='\uA8F7')||input.LA(1)=='\uA8FB'||(input.LA(1)>='\uA90A' && input.LA(1)<='\uA925')||(input.LA(1)>='\uA930' && input.LA(1)<='\uA946')||(input.LA(1)>='\uA960' && input.LA(1)<='\uA97C')||(input.LA(1)>='\uA984' && input.LA(1)<='\uA9B2')||input.LA(1)=='\uA9CF'||(input.LA(1)>='\uAA00' && input.LA(1)<='\uAA28')||(input.LA(1)>='\uAA40' && input.LA(1)<='\uAA42')||(input.LA(1)>='\uAA44' && input.LA(1)<='\uAA4B')||(input.LA(1)>='\uAA60' && input.LA(1)<='\uAA76')||input.LA(1)=='\uAA7A'||(input.LA(1)>='\uAA80' && input.LA(1)<='\uAAAF')||input.LA(1)=='\uAAB1'||(input.LA(1)>='\uAAB5' && input.LA(1)<='\uAAB6')||(input.LA(1)>='\uAAB9' && input.LA(1)<='\uAABD')||input.LA(1)=='\uAAC0'||input.LA(1)=='\uAAC2'||(input.LA(1)>='\uAADB' && input.LA(1)<='\uAADD')||(input.LA(1)>='\uAB01' && input.LA(1)<='\uAB06')||(input.LA(1)>='\uAB09' && input.LA(1)<='\uAB0E')||(input.LA(1)>='\uAB11' && input.LA(1)<='\uAB16')||(input.LA(1)>='\uAB20' && input.LA(1)<='\uAB26')||(input.LA(1)>='\uAB28' && input.LA(1)<='\uAB2E')||(input.LA(1)>='\uABC0' && input.LA(1)<='\uABE2')||(input.LA(1)>='\uAC00' && input.LA(1)<='\uD7A3')||(input.LA(1)>='\uD7B0' && input.LA(1)<='\uD7C6')||(input.LA(1)>='\uD7CB' && input.LA(1)<='\uD7FB')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFB06')||(input.LA(1)>='\uFB13' && input.LA(1)<='\uFB17')||input.LA(1)=='\uFB1D'||(input.LA(1)>='\uFB1F' && input.LA(1)<='\uFB28')||(input.LA(1)>='\uFB2A' && input.LA(1)<='\uFB36')||(input.LA(1)>='\uFB38' && input.LA(1)<='\uFB3C')||input.LA(1)=='\uFB3E'||(input.LA(1)>='\uFB40' && input.LA(1)<='\uFB41')||(input.LA(1)>='\uFB43' && input.LA(1)<='\uFB44')||(input.LA(1)>='\uFB46' && input.LA(1)<='\uFBB1')||(input.LA(1)>='\uFBD3' && input.LA(1)<='\uFD3D')||(input.LA(1)>='\uFD50' && input.LA(1)<='\uFD8F')||(input.LA(1)>='\uFD92' && input.LA(1)<='\uFDC7')||(input.LA(1)>='\uFDF0' && input.LA(1)<='\uFDFC')||(input.LA(1)>='\uFE33' && input.LA(1)<='\uFE34')||(input.LA(1)>='\uFE4D' && input.LA(1)<='\uFE4F')||input.LA(1)=='\uFE69'||(input.LA(1)>='\uFE70' && input.LA(1)<='\uFE74')||(input.LA(1)>='\uFE76' && input.LA(1)<='\uFEFC')||input.LA(1)=='\uFF04'||(input.LA(1)>='\uFF21' && input.LA(1)<='\uFF3A')||input.LA(1)=='\uFF3F'||(input.LA(1)>='\uFF41' && input.LA(1)<='\uFF5A')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFFBE')||(input.LA(1)>='\uFFC2' && input.LA(1)<='\uFFC7')||(input.LA(1)>='\uFFCA' && input.LA(1)<='\uFFCF')||(input.LA(1)>='\uFFD2' && input.LA(1)<='\uFFD7')||(input.LA(1)>='\uFFDA' && input.LA(1)<='\uFFDC')||(input.LA(1)>='\uFFE0' && input.LA(1)<='\uFFE1')||(input.LA(1)>='\uFFE5' && input.LA(1)<='\uFFE6') ) {
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
            // BaseLexer.g:190:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // BaseLexer.g:190:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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

    // $ANTLR start "HEADER_START"
    public final void mHEADER_START() throws RecognitionException {
        try {
            // BaseLexer.g:194:2: ( '/**' )
            // BaseLexer.g:194:4: '/**'
            {
            match("/**"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEADER_START"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:198:5: ( '/*' ~ '*' ( options {greedy=false; } : . )* '*/' )
            // BaseLexer.g:198:9: '/*' ~ '*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // BaseLexer.g:198:19: ( options {greedy=false; } : . )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0=='*') ) {
                    int LA28_1 = input.LA(2);

                    if ( (LA28_1=='/') ) {
                        alt28=2;
                    }
                    else if ( ((LA28_1>='\u0000' && LA28_1<='.')||(LA28_1>='0' && LA28_1<='\uFFFF')) ) {
                        alt28=1;
                    }


                }
                else if ( ((LA28_0>='\u0000' && LA28_0<=')')||(LA28_0>='+' && LA28_0<='\uFFFF')) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // BaseLexer.g:198:47: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop28;
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
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // BaseLexer.g:202:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // BaseLexer.g:202:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // BaseLexer.g:202:12: (~ ( '\\n' | '\\r' ) )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>='\u0000' && LA29_0<='\t')||(LA29_0>='\u000B' && LA29_0<='\f')||(LA29_0>='\u000E' && LA29_0<='\uFFFF')) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // BaseLexer.g:202:12: ~ ( '\\n' | '\\r' )
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
            	    break loop29;
                }
            } while (true);

            // BaseLexer.g:202:26: ( '\\r' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0=='\r') ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // BaseLexer.g:202:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // BaseLexer.g:210:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u4DB5' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' | '\\uff61' .. '\\uff9f' | '\\uAC00' .. '\\uD7A3' | '\\u0000' | '\\u00a2' .. '\\u00a5' | '\\u00aa' | '\\u00b5' | '\\u00ba' | '\\u203f' .. '\\u2040' | '\\u2054' | '\\u2071' | '\\u207f' | '\\u2090' .. '\\u209c' | '\\u20a0' .. '\\u20b9' | '\\u2102' | '\\u2107' | '\\u210a' .. '\\u2113' | '\\u2115' | '\\u2119' .. '\\u211d' | '\\u2124' | '\\u2126' | '\\u2128' | '\\u212a' .. '\\u212d' | '\\u212f' .. '\\u2139' | '\\u213c' .. '\\u213f' | '\\u2145' .. '\\u2149' | '\\u214e' | '\\u2160' .. '\\u2188' | '\\u2c00' .. '\\u2c2e' | '\\u2c30' .. '\\u2c5e' | '\\u2c60' .. '\\u2ce4' | '\\u2ceb' .. '\\u2cee' | '\\u2d00' .. '\\u2d25' | '\\u2d30' .. '\\u2d65' | '\\u2d6f' | '\\u2d80' .. '\\u2d96' | '\\u2da0' .. '\\u2da6' | '\\u2da8' .. '\\u2dae' | '\\u2db0' .. '\\u2db6' | '\\u2db8' .. '\\u2dbe' | '\\u2dc0' .. '\\u2dc6' | '\\u2dc8' .. '\\u2dce' | '\\u2dd0' .. '\\u2dd6' | '\\u2dd8' .. '\\u2dde' | '\\u2e2f' | '\\u3005' .. '\\u3007' | '\\u3021' .. '\\u3029' | '\\u3031' .. '\\u3035' | '\\u3038' .. '\\u303c' | '\\u31a0' .. '\\u31ba' | '\\u31f0' .. '\\u31ff' | '\\ua000' .. '\\ua48c' | '\\ua4d0' .. '\\ua4fd' | '\\ua500' .. '\\ua60c' | '\\ua610' .. '\\ua61f' | '\\ua62a' .. '\\ua62b' | '\\ua640' .. '\\ua66e' | '\\ua67f' .. '\\ua697' | '\\ua6a0' .. '\\ua6ef' | '\\ua717' .. '\\ua71f' | '\\ua722' .. '\\ua788' | '\\ua78b' .. '\\ua78e' | '\\ua790' .. '\\ua791' | '\\ua7a0' .. '\\ua7a9' | '\\ua7fa' .. '\\ua801' | '\\ua803' .. '\\ua805' | '\\ua807' .. '\\ua80a' | '\\ua80c' .. '\\ua822' | '\\ua838' | '\\ua840' .. '\\ua873' | '\\ua882' .. '\\ua8b3' | '\\ua8f2' .. '\\ua8f7' | '\\ua8fb' | '\\ua90a' .. '\\ua925' | '\\ua930' .. '\\ua946' | '\\ua960' .. '\\ua97c' | '\\ua984' .. '\\ua9b2' | '\\ua9cf' | '\\uaa00' .. '\\uaa28' | '\\uaa40' .. '\\uaa42' | '\\uaa44' .. '\\uaa4b' | '\\uaa60' .. '\\uaa76' | '\\uaa7a' | '\\uaa80' .. '\\uaaaf' | '\\uaab1' | '\\uaab5' .. '\\uaab6' | '\\uaab9' .. '\\uaabd' | '\\uaac0' | '\\uaac2' | '\\uaadb' .. '\\uaadd' | '\\uab01' .. '\\uab06' | '\\uab09' .. '\\uab0e' | '\\uab11' .. '\\uab16' | '\\uab20' .. '\\uab26' | '\\uab28' .. '\\uab2e' | '\\uabc0' .. '\\uabe2' | '\\ud7b0' .. '\\ud7c6' | '\\ud7cb' .. '\\ud7fb' | '\\ufb00' .. '\\ufb06' | '\\ufb13' .. '\\ufb17' | '\\ufb1d' | '\\ufb1f' .. '\\ufb28' | '\\ufb2a' .. '\\ufb36' | '\\ufb38' .. '\\ufb3c' | '\\ufb3e' | '\\ufb40' .. '\\ufb41' | '\\ufb43' .. '\\ufb44' | '\\ufb46' .. '\\ufbb1' | '\\ufbd3' .. '\\ufd3d' | '\\ufd50' .. '\\ufd8f' | '\\ufd92' .. '\\ufdc7' | '\\ufdf0' .. '\\ufdfc' | '\\ufe33' .. '\\ufe34' | '\\ufe4d' .. '\\ufe4f' | '\\ufe69' | '\\ufe70' .. '\\ufe74' | '\\ufe76' .. '\\ufefc' | '\\uff04' | '\\uff21' .. '\\uff3a' | '\\uff3f' | '\\uff41' .. '\\uff5a' | '\\uffa0' .. '\\uffbe' | '\\uffc2' .. '\\uffc7' | '\\uffca' .. '\\uffcf' | '\\uffd2' .. '\\uffd7' | '\\uffda' .. '\\uffdc' | '\\uffe0' .. '\\uffe1' | '\\uffe5' .. '\\uffe6' )
            // BaseLexer.g:
            {
            if ( input.LA(1)=='\u0000'||input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00A2' && input.LA(1)<='\u00A5')||input.LA(1)=='\u00AA'||input.LA(1)=='\u00B5'||input.LA(1)=='\u00BA'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u203F' && input.LA(1)<='\u2040')||input.LA(1)=='\u2054'||input.LA(1)=='\u2071'||input.LA(1)=='\u207F'||(input.LA(1)>='\u2090' && input.LA(1)<='\u209C')||(input.LA(1)>='\u20A0' && input.LA(1)<='\u20B9')||input.LA(1)=='\u2102'||input.LA(1)=='\u2107'||(input.LA(1)>='\u210A' && input.LA(1)<='\u2113')||input.LA(1)=='\u2115'||(input.LA(1)>='\u2119' && input.LA(1)<='\u211D')||input.LA(1)=='\u2124'||input.LA(1)=='\u2126'||input.LA(1)=='\u2128'||(input.LA(1)>='\u212A' && input.LA(1)<='\u212D')||(input.LA(1)>='\u212F' && input.LA(1)<='\u2139')||(input.LA(1)>='\u213C' && input.LA(1)<='\u213F')||(input.LA(1)>='\u2145' && input.LA(1)<='\u2149')||input.LA(1)=='\u214E'||(input.LA(1)>='\u2160' && input.LA(1)<='\u2188')||(input.LA(1)>='\u2C00' && input.LA(1)<='\u2C2E')||(input.LA(1)>='\u2C30' && input.LA(1)<='\u2C5E')||(input.LA(1)>='\u2C60' && input.LA(1)<='\u2CE4')||(input.LA(1)>='\u2CEB' && input.LA(1)<='\u2CEE')||(input.LA(1)>='\u2D00' && input.LA(1)<='\u2D25')||(input.LA(1)>='\u2D30' && input.LA(1)<='\u2D65')||input.LA(1)=='\u2D6F'||(input.LA(1)>='\u2D80' && input.LA(1)<='\u2D96')||(input.LA(1)>='\u2DA0' && input.LA(1)<='\u2DA6')||(input.LA(1)>='\u2DA8' && input.LA(1)<='\u2DAE')||(input.LA(1)>='\u2DB0' && input.LA(1)<='\u2DB6')||(input.LA(1)>='\u2DB8' && input.LA(1)<='\u2DBE')||(input.LA(1)>='\u2DC0' && input.LA(1)<='\u2DC6')||(input.LA(1)>='\u2DC8' && input.LA(1)<='\u2DCE')||(input.LA(1)>='\u2DD0' && input.LA(1)<='\u2DD6')||(input.LA(1)>='\u2DD8' && input.LA(1)<='\u2DDE')||input.LA(1)=='\u2E2F'||(input.LA(1)>='\u3005' && input.LA(1)<='\u3007')||(input.LA(1)>='\u3021' && input.LA(1)<='\u3029')||(input.LA(1)>='\u3031' && input.LA(1)<='\u3035')||(input.LA(1)>='\u3038' && input.LA(1)<='\u303C')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u31A0' && input.LA(1)<='\u31BA')||(input.LA(1)>='\u31F0' && input.LA(1)<='\u31FF')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u4DB5')||(input.LA(1)>='\u4E00' && input.LA(1)<='\uA48C')||(input.LA(1)>='\uA4D0' && input.LA(1)<='\uA4FD')||(input.LA(1)>='\uA500' && input.LA(1)<='\uA60C')||(input.LA(1)>='\uA610' && input.LA(1)<='\uA61F')||(input.LA(1)>='\uA62A' && input.LA(1)<='\uA62B')||(input.LA(1)>='\uA640' && input.LA(1)<='\uA66E')||(input.LA(1)>='\uA67F' && input.LA(1)<='\uA697')||(input.LA(1)>='\uA6A0' && input.LA(1)<='\uA6EF')||(input.LA(1)>='\uA717' && input.LA(1)<='\uA71F')||(input.LA(1)>='\uA722' && input.LA(1)<='\uA788')||(input.LA(1)>='\uA78B' && input.LA(1)<='\uA78E')||(input.LA(1)>='\uA790' && input.LA(1)<='\uA791')||(input.LA(1)>='\uA7A0' && input.LA(1)<='\uA7A9')||(input.LA(1)>='\uA7FA' && input.LA(1)<='\uA801')||(input.LA(1)>='\uA803' && input.LA(1)<='\uA805')||(input.LA(1)>='\uA807' && input.LA(1)<='\uA80A')||(input.LA(1)>='\uA80C' && input.LA(1)<='\uA822')||input.LA(1)=='\uA838'||(input.LA(1)>='\uA840' && input.LA(1)<='\uA873')||(input.LA(1)>='\uA882' && input.LA(1)<='\uA8B3')||(input.LA(1)>='\uA8F2' && input.LA(1)<='\uA8F7')||input.LA(1)=='\uA8FB'||(input.LA(1)>='\uA90A' && input.LA(1)<='\uA925')||(input.LA(1)>='\uA930' && input.LA(1)<='\uA946')||(input.LA(1)>='\uA960' && input.LA(1)<='\uA97C')||(input.LA(1)>='\uA984' && input.LA(1)<='\uA9B2')||input.LA(1)=='\uA9CF'||(input.LA(1)>='\uAA00' && input.LA(1)<='\uAA28')||(input.LA(1)>='\uAA40' && input.LA(1)<='\uAA42')||(input.LA(1)>='\uAA44' && input.LA(1)<='\uAA4B')||(input.LA(1)>='\uAA60' && input.LA(1)<='\uAA76')||input.LA(1)=='\uAA7A'||(input.LA(1)>='\uAA80' && input.LA(1)<='\uAAAF')||input.LA(1)=='\uAAB1'||(input.LA(1)>='\uAAB5' && input.LA(1)<='\uAAB6')||(input.LA(1)>='\uAAB9' && input.LA(1)<='\uAABD')||input.LA(1)=='\uAAC0'||input.LA(1)=='\uAAC2'||(input.LA(1)>='\uAADB' && input.LA(1)<='\uAADD')||(input.LA(1)>='\uAB01' && input.LA(1)<='\uAB06')||(input.LA(1)>='\uAB09' && input.LA(1)<='\uAB0E')||(input.LA(1)>='\uAB11' && input.LA(1)<='\uAB16')||(input.LA(1)>='\uAB20' && input.LA(1)<='\uAB26')||(input.LA(1)>='\uAB28' && input.LA(1)<='\uAB2E')||(input.LA(1)>='\uABC0' && input.LA(1)<='\uABE2')||(input.LA(1)>='\uAC00' && input.LA(1)<='\uD7A3')||(input.LA(1)>='\uD7B0' && input.LA(1)<='\uD7C6')||(input.LA(1)>='\uD7CB' && input.LA(1)<='\uD7FB')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFB06')||(input.LA(1)>='\uFB13' && input.LA(1)<='\uFB17')||input.LA(1)=='\uFB1D'||(input.LA(1)>='\uFB1F' && input.LA(1)<='\uFB28')||(input.LA(1)>='\uFB2A' && input.LA(1)<='\uFB36')||(input.LA(1)>='\uFB38' && input.LA(1)<='\uFB3C')||input.LA(1)=='\uFB3E'||(input.LA(1)>='\uFB40' && input.LA(1)<='\uFB41')||(input.LA(1)>='\uFB43' && input.LA(1)<='\uFB44')||(input.LA(1)>='\uFB46' && input.LA(1)<='\uFBB1')||(input.LA(1)>='\uFBD3' && input.LA(1)<='\uFD3D')||(input.LA(1)>='\uFD50' && input.LA(1)<='\uFD8F')||(input.LA(1)>='\uFD92' && input.LA(1)<='\uFDC7')||(input.LA(1)>='\uFDF0' && input.LA(1)<='\uFDFC')||(input.LA(1)>='\uFE33' && input.LA(1)<='\uFE34')||(input.LA(1)>='\uFE4D' && input.LA(1)<='\uFE4F')||input.LA(1)=='\uFE69'||(input.LA(1)>='\uFE70' && input.LA(1)<='\uFE74')||(input.LA(1)>='\uFE76' && input.LA(1)<='\uFEFC')||input.LA(1)=='\uFF04'||(input.LA(1)>='\uFF21' && input.LA(1)<='\uFF3A')||input.LA(1)=='\uFF3F'||(input.LA(1)>='\uFF41' && input.LA(1)<='\uFF5A')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFFBE')||(input.LA(1)>='\uFFC2' && input.LA(1)<='\uFFC7')||(input.LA(1)>='\uFFCA' && input.LA(1)<='\uFFCF')||(input.LA(1)>='\uFFD2' && input.LA(1)<='\uFFD7')||(input.LA(1)>='\uFFDA' && input.LA(1)<='\uFFDC')||(input.LA(1)>='\uFFE0' && input.LA(1)<='\uFFE1')||(input.LA(1)>='\uFFE5' && input.LA(1)<='\uFFE6') ) {
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
    // $ANTLR end "Letter"

    // $ANTLR start "JavaIDDigit"
    public final void mJavaIDDigit() throws RecognitionException {
        try {
            // BaseLexer.g:355:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // BaseLexer.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
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
    // $ANTLR end "JavaIDDigit"

    public void mTokens() throws RecognitionException {
        // BaseLexer.g:1:8: ( HeaderSection | MappingEnd | INSTANCE_OF | TRUE | FALSE | PLUS | MINUS | MULT | DIVIDE | MOD | POUND | ASSIGN | DOT | SEMICOLON | LBRACE | RBRACE | LPAREN | RPAREN | LBRACK | RBRACK | ANNOTATE | AND | OR | EQ | NE | LT | GT | LE | GE | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL | INCR | DECR | NullLiteral | HexLiteral | DecimalLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT )
        int alt31=46;
        alt31 = dfa31.predict(input);
        switch (alt31) {
            case 1 :
                // BaseLexer.g:1:10: HeaderSection
                {
                mHeaderSection(); 

                }
                break;
            case 2 :
                // BaseLexer.g:1:24: MappingEnd
                {
                mMappingEnd(); 

                }
                break;
            case 3 :
                // BaseLexer.g:1:35: INSTANCE_OF
                {
                mINSTANCE_OF(); 

                }
                break;
            case 4 :
                // BaseLexer.g:1:47: TRUE
                {
                mTRUE(); 

                }
                break;
            case 5 :
                // BaseLexer.g:1:52: FALSE
                {
                mFALSE(); 

                }
                break;
            case 6 :
                // BaseLexer.g:1:58: PLUS
                {
                mPLUS(); 

                }
                break;
            case 7 :
                // BaseLexer.g:1:63: MINUS
                {
                mMINUS(); 

                }
                break;
            case 8 :
                // BaseLexer.g:1:69: MULT
                {
                mMULT(); 

                }
                break;
            case 9 :
                // BaseLexer.g:1:74: DIVIDE
                {
                mDIVIDE(); 

                }
                break;
            case 10 :
                // BaseLexer.g:1:81: MOD
                {
                mMOD(); 

                }
                break;
            case 11 :
                // BaseLexer.g:1:85: POUND
                {
                mPOUND(); 

                }
                break;
            case 12 :
                // BaseLexer.g:1:91: ASSIGN
                {
                mASSIGN(); 

                }
                break;
            case 13 :
                // BaseLexer.g:1:98: DOT
                {
                mDOT(); 

                }
                break;
            case 14 :
                // BaseLexer.g:1:102: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 15 :
                // BaseLexer.g:1:112: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 16 :
                // BaseLexer.g:1:119: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 17 :
                // BaseLexer.g:1:126: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 18 :
                // BaseLexer.g:1:133: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 19 :
                // BaseLexer.g:1:140: LBRACK
                {
                mLBRACK(); 

                }
                break;
            case 20 :
                // BaseLexer.g:1:147: RBRACK
                {
                mRBRACK(); 

                }
                break;
            case 21 :
                // BaseLexer.g:1:154: ANNOTATE
                {
                mANNOTATE(); 

                }
                break;
            case 22 :
                // BaseLexer.g:1:163: AND
                {
                mAND(); 

                }
                break;
            case 23 :
                // BaseLexer.g:1:167: OR
                {
                mOR(); 

                }
                break;
            case 24 :
                // BaseLexer.g:1:170: EQ
                {
                mEQ(); 

                }
                break;
            case 25 :
                // BaseLexer.g:1:173: NE
                {
                mNE(); 

                }
                break;
            case 26 :
                // BaseLexer.g:1:176: LT
                {
                mLT(); 

                }
                break;
            case 27 :
                // BaseLexer.g:1:179: GT
                {
                mGT(); 

                }
                break;
            case 28 :
                // BaseLexer.g:1:182: LE
                {
                mLE(); 

                }
                break;
            case 29 :
                // BaseLexer.g:1:185: GE
                {
                mGE(); 

                }
                break;
            case 30 :
                // BaseLexer.g:1:188: PLUS_EQUAL
                {
                mPLUS_EQUAL(); 

                }
                break;
            case 31 :
                // BaseLexer.g:1:199: MINUS_EQUAL
                {
                mMINUS_EQUAL(); 

                }
                break;
            case 32 :
                // BaseLexer.g:1:211: MULT_EQUAL
                {
                mMULT_EQUAL(); 

                }
                break;
            case 33 :
                // BaseLexer.g:1:222: DIV_EQUAL
                {
                mDIV_EQUAL(); 

                }
                break;
            case 34 :
                // BaseLexer.g:1:232: MOD_EQUAL
                {
                mMOD_EQUAL(); 

                }
                break;
            case 35 :
                // BaseLexer.g:1:242: INCR
                {
                mINCR(); 

                }
                break;
            case 36 :
                // BaseLexer.g:1:247: DECR
                {
                mDECR(); 

                }
                break;
            case 37 :
                // BaseLexer.g:1:252: NullLiteral
                {
                mNullLiteral(); 

                }
                break;
            case 38 :
                // BaseLexer.g:1:264: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 39 :
                // BaseLexer.g:1:275: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 40 :
                // BaseLexer.g:1:290: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 41 :
                // BaseLexer.g:1:311: CharacterLiteral
                {
                mCharacterLiteral(); 

                }
                break;
            case 42 :
                // BaseLexer.g:1:328: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 43 :
                // BaseLexer.g:1:342: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 44 :
                // BaseLexer.g:1:353: WS
                {
                mWS(); 

                }
                break;
            case 45 :
                // BaseLexer.g:1:356: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 46 :
                // BaseLexer.g:1:364: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA20 dfa20 = new DFA20(this);
    protected DFA31 dfa31 = new DFA31(this);
    static final String DFA20_eotS =
        "\6\uffff";
    static final String DFA20_eofS =
        "\6\uffff";
    static final String DFA20_minS =
        "\2\56\4\uffff";
    static final String DFA20_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA20_acceptS =
        "\2\uffff\1\2\1\1\1\4\1\3";
    static final String DFA20_specialS =
        "\6\uffff}>";
    static final String[] DFA20_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\12\uffff\1\4\1\5\1\4\35\uffff\1\4\1\5\1"+
            "\4",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA20_eot = DFA.unpackEncodedString(DFA20_eotS);
    static final short[] DFA20_eof = DFA.unpackEncodedString(DFA20_eofS);
    static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars(DFA20_minS);
    static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars(DFA20_maxS);
    static final short[] DFA20_accept = DFA.unpackEncodedString(DFA20_acceptS);
    static final short[] DFA20_special = DFA.unpackEncodedString(DFA20_specialS);
    static final short[][] DFA20_transition;

    static {
        int numStates = DFA20_transitionS.length;
        DFA20_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA20_transition[i] = DFA.unpackEncodedString(DFA20_transitionS[i]);
        }
    }

    class DFA20 extends DFA {

        public DFA20(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 20;
            this.eot = DFA20_eot;
            this.eof = DFA20_eof;
            this.min = DFA20_min;
            this.max = DFA20_max;
            this.accept = DFA20_accept;
            this.special = DFA20_special;
            this.transition = DFA20_transition;
        }
        public String getDescription() {
            return "139:1: FloatingPointLiteral : ( ( '0' .. '9' )+ DOT ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | DOT ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix );";
        }
    }
    static final String DFA31_eotS =
        "\1\uffff\1\44\1\47\3\37\1\55\1\60\1\62\1\64\1\66\1\70\1\71\12\uffff"+
        "\2\37\1\77\1\37\2\102\13\uffff\3\37\20\uffff\3\37\2\uffff\1\37\4"+
        "\uffff\10\37\1\124\2\37\1\127\3\37\1\uffff\1\133\1\37\1\uffff\3"+
        "\37\1\uffff\10\37\1\65\1\47\4\37\1\155\2\37\1\uffff\1\37\1\77\6"+
        "\37\1\46\3\37\1\76";
    static final String DFA31_eofS =
        "\173\uffff";
    static final String DFA31_minS =
        "\1\0\1\52\1\57\1\156\1\162\1\141\1\53\1\55\4\75\1\60\12\uffff\1"+
        "\157\1\145\1\75\1\162\2\56\4\uffff\1\0\6\uffff\1\163\1\165\1\154"+
        "\20\uffff\1\164\1\154\1\163\2\uffff\1\145\4\uffff\1\164\1\145\1"+
        "\163\1\145\1\154\1\163\2\141\1\0\1\145\1\161\1\0\2\164\1\156\1\uffff"+
        "\1\0\1\165\1\uffff\1\150\1\145\1\143\1\uffff\2\141\1\162\1\145\1"+
        "\154\1\156\1\164\1\157\2\0\1\150\1\146\1\162\1\141\1\0\1\145\1\156"+
        "\1\uffff\1\161\1\0\1\165\1\162\1\141\1\145\1\154\1\161\1\0\1\165"+
        "\1\141\1\154\1\0";
    static final String DFA31_maxS =
        "\1\uffe6\2\75\1\156\1\162\1\141\6\75\1\71\12\uffff\1\165\1\145"+
        "\1\75\1\162\1\170\1\146\4\uffff\1\uffff\6\uffff\1\163\1\165\1\154"+
        "\20\uffff\1\164\1\154\1\163\2\uffff\1\145\4\uffff\1\164\1\145\1"+
        "\163\1\145\1\154\1\163\2\141\1\uffe6\1\145\1\161\1\uffe6\2\164\1"+
        "\156\1\uffff\1\uffe6\1\165\1\uffff\1\150\1\145\1\143\1\uffff\2\141"+
        "\1\162\1\145\1\154\1\156\1\164\1\157\2\uffe6\1\150\1\146\1\162\1"+
        "\141\1\uffe6\1\145\1\156\1\uffff\1\161\1\uffe6\1\165\1\162\1\141"+
        "\1\145\1\154\1\161\1\uffe6\1\165\1\141\1\154\1\uffe6";
    static final String DFA31_acceptS =
        "\15\uffff\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\6\uffff"+
        "\1\51\1\52\1\53\1\54\1\uffff\1\41\1\56\1\11\1\2\1\34\1\32\3\uffff"+
        "\1\36\1\43\1\6\1\37\1\44\1\7\1\40\1\10\1\42\1\12\1\31\1\13\1\30"+
        "\1\14\1\15\1\50\3\uffff\1\35\1\33\1\uffff\1\46\1\47\1\1\1\55\17"+
        "\uffff\1\4\2\uffff\1\45\3\uffff\1\5\21\uffff\1\3\15\uffff";
    static final String DFA31_specialS =
        "\41\uffff\1\0\131\uffff}>";
    static final String[] DFA31_transitionS = {
            "\1\37\10\uffff\2\40\1\uffff\2\40\22\uffff\1\40\1\12\1\36\1"+
            "\uffff\1\37\1\11\1\25\1\35\1\20\1\21\1\10\1\6\1\uffff\1\7\1"+
            "\14\1\1\1\33\11\34\1\uffff\1\15\1\2\1\13\1\31\1\uffff\1\24\32"+
            "\37\1\22\1\uffff\1\23\1\uffff\1\37\1\uffff\5\37\1\5\1\32\1\37"+
            "\1\3\2\37\1\30\1\37\1\27\5\37\1\4\6\37\1\16\1\26\1\17\44\uffff"+
            "\4\37\4\uffff\1\37\12\uffff\1\37\4\uffff\1\37\5\uffff\27\37"+
            "\1\uffff\37\37\1\uffff\u1f08\37\77\uffff\2\37\23\uffff\1\37"+
            "\34\uffff\1\37\15\uffff\1\37\20\uffff\15\37\3\uffff\32\37\110"+
            "\uffff\1\37\4\uffff\1\37\2\uffff\12\37\1\uffff\1\37\3\uffff"+
            "\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff\1\37\1\uffff\4\37\1"+
            "\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4\uffff\1\37\21\uffff"+
            "\51\37\u0a77\uffff\57\37\1\uffff\57\37\1\uffff\u0085\37\6\uffff"+
            "\4\37\21\uffff\46\37\12\uffff\66\37\11\uffff\1\37\20\uffff\27"+
            "\37\11\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\120\uffff\1\37"+
            "\u01d5\uffff\3\37\31\uffff\11\37\7\uffff\5\37\2\uffff\5\37\3"+
            "\uffff\u0150\37\20\uffff\33\37\65\uffff\20\37\u0100\uffff\u0080"+
            "\37\u0080\uffff\u19b6\37\112\uffff\u568d\37\103\uffff\56\37"+
            "\2\uffff\u010d\37\3\uffff\20\37\12\uffff\2\37\24\uffff\57\37"+
            "\20\uffff\31\37\10\uffff\120\37\47\uffff\11\37\2\uffff\147\37"+
            "\2\uffff\4\37\1\uffff\2\37\16\uffff\12\37\120\uffff\10\37\1"+
            "\uffff\3\37\1\uffff\4\37\1\uffff\27\37\25\uffff\1\37\7\uffff"+
            "\64\37\16\uffff\62\37\76\uffff\6\37\3\uffff\1\37\16\uffff\34"+
            "\37\12\uffff\27\37\31\uffff\35\37\7\uffff\57\37\34\uffff\1\37"+
            "\60\uffff\51\37\27\uffff\3\37\1\uffff\10\37\24\uffff\27\37\3"+
            "\uffff\1\37\5\uffff\60\37\1\uffff\1\37\3\uffff\2\37\2\uffff"+
            "\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff\3\37\43\uffff\6\37"+
            "\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37\1\uffff\7\37\u0091"+
            "\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27\37\4\uffff\61\37"+
            "\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff\1\37\1\uffff\12"+
            "\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37\1\uffff\2\37\1\uffff"+
            "\2\37\1\uffff\154\37\41\uffff\u016b\37\22\uffff\100\37\2\uffff"+
            "\66\37\50\uffff\15\37\66\uffff\2\37\30\uffff\3\37\31\uffff\1"+
            "\37\6\uffff\5\37\1\uffff\u0087\37\7\uffff\1\37\34\uffff\32\37"+
            "\4\uffff\1\37\1\uffff\32\37\6\uffff\136\37\3\uffff\6\37\2\uffff"+
            "\6\37\2\uffff\6\37\2\uffff\3\37\3\uffff\2\37\3\uffff\2\37",
            "\1\41\4\uffff\1\43\15\uffff\1\42",
            "\1\45\15\uffff\1\46",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\54\21\uffff\1\53",
            "\1\57\17\uffff\1\56",
            "\1\61",
            "\1\63",
            "\1\65",
            "\1\67",
            "\12\72",
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
            "\1\73\5\uffff\1\74",
            "\1\75",
            "\1\76",
            "\1\100",
            "\1\72\1\uffff\12\34\12\uffff\3\72\21\uffff\1\101\13\uffff"+
            "\3\72\21\uffff\1\101",
            "\1\72\1\uffff\12\34\12\uffff\3\72\35\uffff\3\72",
            "",
            "",
            "",
            "",
            "\52\104\1\103\uffd5\104",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\105",
            "\1\106",
            "\1\107",
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
            "\1\110",
            "\1\111",
            "\1\112",
            "",
            "",
            "\1\113",
            "",
            "",
            "",
            "",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\125",
            "\1\126",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\130",
            "\1\131",
            "\1\132",
            "",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\134",
            "",
            "\1\135",
            "\1\136",
            "\1\137",
            "",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\16\37\1\150\13\37\47\uffff\4\37\4\uffff\1\37\12"+
            "\uffff\1\37\4\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff"+
            "\u1f08\37\77\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff"+
            "\1\37\20\uffff\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1"+
            "\37\2\uffff\12\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5"+
            "\uffff\5\37\4\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1"+
            "\uffff\57\37\1\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12"+
            "\uffff\66\37\11\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\1\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31"+
            "\uffff\11\37\7\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff"+
            "\33\37\65\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6"+
            "\37\112\uffff\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff"+
            "\20\37\12\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff"+
            "\120\37\47\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2"+
            "\37\16\uffff\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37"+
            "\1\uffff\27\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76"+
            "\uffff\6\37\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff"+
            "\35\37\7\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3"+
            "\37\1\uffff\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37"+
            "\1\uffff\1\37\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff"+
            "\1\37\30\uffff\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37"+
            "\11\uffff\7\37\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4"+
            "\37\14\uffff\27\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff"+
            "\5\37\5\uffff\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37"+
            "\1\uffff\1\37\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff"+
            "\u016b\37\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff"+
            "\2\37\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087"+
            "\37\7\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6"+
            "\uffff\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff"+
            "\3\37\3\uffff\2\37\3\uffff\2\37",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\156",
            "\1\157",
            "",
            "\1\160",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\16\37\1\161\13\37\47\uffff\4\37\4\uffff\1\37\12"+
            "\uffff\1\37\4\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff"+
            "\u1f08\37\77\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff"+
            "\1\37\20\uffff\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1"+
            "\37\2\uffff\12\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5"+
            "\uffff\5\37\4\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1"+
            "\uffff\57\37\1\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12"+
            "\uffff\66\37\11\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\1\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31"+
            "\uffff\11\37\7\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff"+
            "\33\37\65\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6"+
            "\37\112\uffff\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff"+
            "\20\37\12\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff"+
            "\120\37\47\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2"+
            "\37\16\uffff\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37"+
            "\1\uffff\27\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76"+
            "\uffff\6\37\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff"+
            "\35\37\7\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3"+
            "\37\1\uffff\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37"+
            "\1\uffff\1\37\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff"+
            "\1\37\30\uffff\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37"+
            "\11\uffff\7\37\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4"+
            "\37\14\uffff\27\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff"+
            "\5\37\5\uffff\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37"+
            "\1\uffff\1\37\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff"+
            "\u016b\37\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff"+
            "\2\37\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087"+
            "\37\7\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6"+
            "\uffff\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff"+
            "\3\37\3\uffff\2\37\3\uffff\2\37",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\37\43\uffff\1\37\13\uffff\12\37\7\uffff\32\37\4\uffff\1"+
            "\37\1\uffff\32\37\47\uffff\4\37\4\uffff\1\37\12\uffff\1\37\4"+
            "\uffff\1\37\5\uffff\27\37\1\uffff\37\37\1\uffff\u1f08\37\77"+
            "\uffff\2\37\23\uffff\1\37\34\uffff\1\37\15\uffff\1\37\20\uffff"+
            "\15\37\3\uffff\32\37\110\uffff\1\37\4\uffff\1\37\2\uffff\12"+
            "\37\1\uffff\1\37\3\uffff\5\37\6\uffff\1\37\1\uffff\1\37\1\uffff"+
            "\1\37\1\uffff\4\37\1\uffff\13\37\2\uffff\4\37\5\uffff\5\37\4"+
            "\uffff\1\37\21\uffff\51\37\u0a77\uffff\57\37\1\uffff\57\37\1"+
            "\uffff\u0085\37\6\uffff\4\37\21\uffff\46\37\12\uffff\66\37\11"+
            "\uffff\1\37\20\uffff\27\37\11\uffff\7\37\1\uffff\7\37\1\uffff"+
            "\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1\uffff\7\37\1"+
            "\uffff\7\37\120\uffff\1\37\u01d5\uffff\3\37\31\uffff\11\37\7"+
            "\uffff\5\37\2\uffff\5\37\3\uffff\u0150\37\20\uffff\33\37\65"+
            "\uffff\20\37\u0100\uffff\u0080\37\u0080\uffff\u19b6\37\112\uffff"+
            "\u568d\37\103\uffff\56\37\2\uffff\u010d\37\3\uffff\20\37\12"+
            "\uffff\2\37\24\uffff\57\37\20\uffff\31\37\10\uffff\120\37\47"+
            "\uffff\11\37\2\uffff\147\37\2\uffff\4\37\1\uffff\2\37\16\uffff"+
            "\12\37\120\uffff\10\37\1\uffff\3\37\1\uffff\4\37\1\uffff\27"+
            "\37\25\uffff\1\37\7\uffff\64\37\16\uffff\62\37\76\uffff\6\37"+
            "\3\uffff\1\37\16\uffff\34\37\12\uffff\27\37\31\uffff\35\37\7"+
            "\uffff\57\37\34\uffff\1\37\60\uffff\51\37\27\uffff\3\37\1\uffff"+
            "\10\37\24\uffff\27\37\3\uffff\1\37\5\uffff\60\37\1\uffff\1\37"+
            "\3\uffff\2\37\2\uffff\5\37\2\uffff\1\37\1\uffff\1\37\30\uffff"+
            "\3\37\43\uffff\6\37\2\uffff\6\37\2\uffff\6\37\11\uffff\7\37"+
            "\1\uffff\7\37\u0091\uffff\43\37\35\uffff\u2ba4\37\14\uffff\27"+
            "\37\4\uffff\61\37\u2104\uffff\u0207\37\14\uffff\5\37\5\uffff"+
            "\1\37\1\uffff\12\37\1\uffff\15\37\1\uffff\5\37\1\uffff\1\37"+
            "\1\uffff\2\37\1\uffff\2\37\1\uffff\154\37\41\uffff\u016b\37"+
            "\22\uffff\100\37\2\uffff\66\37\50\uffff\15\37\66\uffff\2\37"+
            "\30\uffff\3\37\31\uffff\1\37\6\uffff\5\37\1\uffff\u0087\37\7"+
            "\uffff\1\37\34\uffff\32\37\4\uffff\1\37\1\uffff\32\37\6\uffff"+
            "\136\37\3\uffff\6\37\2\uffff\6\37\2\uffff\6\37\2\uffff\3\37"+
            "\3\uffff\2\37\3\uffff\2\37"
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
            return "1:1: Tokens : ( HeaderSection | MappingEnd | INSTANCE_OF | TRUE | FALSE | PLUS | MINUS | MULT | DIVIDE | MOD | POUND | ASSIGN | DOT | SEMICOLON | LBRACE | RBRACE | LPAREN | RPAREN | LBRACK | RBRACK | ANNOTATE | AND | OR | EQ | NE | LT | GT | LE | GE | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL | INCR | DECR | NullLiteral | HexLiteral | DecimalLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA31_33 = input.LA(1);

                        s = -1;
                        if ( (LA31_33=='*') ) {s = 67;}

                        else if ( ((LA31_33>='\u0000' && LA31_33<=')')||(LA31_33>='+' && LA31_33<='\uFFFF')) ) {s = 68;}

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