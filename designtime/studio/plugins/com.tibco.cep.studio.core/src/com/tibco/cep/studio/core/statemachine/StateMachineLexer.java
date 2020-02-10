// $ANTLR 3.1.1 StateMachine.g 2009-02-10 10:13:03
package com.tibco.cep.studio.core.statemachine;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class StateMachineLexer extends Lexer {
    public static final int COMPOSITE_STATE_DEFINITION=40;
    public static final int HexDigit=59;
    public static final int DEFINITIONS=32;
    public static final int TIMEOUT_RULE=37;
    public static final int T__109=109;
    public static final int T__122=122;
    public static final int STATE_MACHINE=25;
    public static final int T__73=73;
    public static final int T__115=115;
    public static final int T__123=123;
    public static final int T__72=72;
    public static final int STATEMENTS=10;
    public static final int Letter=68;
    public static final int T__128=128;
    public static final int RULE_FUNCTION=5;
    public static final int DECLARATOR=21;
    public static final int SCOPE_BLOCK=13;
    public static final int T__96=96;
    public static final int LE=51;
    public static final int RULE_FUNC_DECL=12;
    public static final int T__119=119;
    public static final int T__112=112;
    public static final int T__108=108;
    public static final int T__118=118;
    public static final int RULES=35;
    public static final int FloatTypeSuffix=63;
    public static final int T__113=113;
    public static final int REGIONS=30;
    public static final int EXIT_RULE=38;
    public static final int IntegerTypeSuffix=60;
    public static final int END_STATE=28;
    public static final int T__89=89;
    public static final int Identifier=41;
    public static final int WS=57;
    public static final int NullLiteral=54;
    public static final int T__79=79;
    public static final int EQ=47;
    public static final int PRIMITIVE_TYPE=23;
    public static final int LT=49;
    public static final int T__92=92;
    public static final int THEN_BLOCK=17;
    public static final int T__88=88;
    public static final int LINE_COMMENT=71;
    public static final int START_STATE=27;
    public static final int WHEN_BLOCK=14;
    public static final int GE=52;
    public static final int T__90=90;
    public static final int UnicodeEscape=66;
    public static final int HexLiteral=56;
    public static final int PREDICATES=15;
    public static final int T__114=114;
    public static final int T__110=110;
    public static final int T__125=125;
    public static final int SEMICOLON=43;
    public static final int T__91=91;
    public static final int PSEUDO_START_STATE_DEF=34;
    public static final int DecimalLiteral=55;
    public static final int T__85=85;
    public static final int ANNOTATION=24;
    public static final int T__124=124;
    public static final int ANNOTATE=58;
    public static final int NAME=7;
    public static final int PSEUDO_END_STATE_DEF=33;
    public static final int T__93=93;
    public static final int TYPE=20;
    public static final int T__86=86;
    public static final int OctalLiteral=61;
    public static final int T__94=94;
    public static final int SIMPLE_STATE_DEF=36;
    public static final int ATTRIBUTE_BLOCK=9;
    public static final int T__100=100;
    public static final int T__80=80;
    public static final int T__95=95;
    public static final int T__126=126;
    public static final int RULE=4;
    public static final int T__101=101;
    public static final int T__104=104;
    public static final int SCOPE_DECLARATOR=19;
    public static final int T__107=107;
    public static final int LBRACE=45;
    public static final int T__87=87;
    public static final int RBRACE=46;
    public static final int T__106=106;
    public static final int T__74=74;
    public static final int EscapeSequence=64;
    public static final int DECLARE_BLOCK=11;
    public static final int ASSIGN=42;
    public static final int TRANSITION_DEFINITION=26;
    public static final int T__127=127;
    public static final int FloatingPointLiteral=53;
    public static final int T__120=120;
    public static final int T__98=98;
    public static final int RULE_BLOCK=8;
    public static final int Exponent=62;
    public static final int BODY_BLOCK=18;
    public static final int T__117=117;
    public static final int T__78=78;
    public static final int CharacterLiteral=65;
    public static final int NE=48;
    public static final int GT=50;
    public static final int T__99=99;
    public static final int COMMENT=70;
    public static final int StringLiteral=44;
    public static final int RULE_DECL=6;
    public static final int T__77=77;
    public static final int T__129=129;
    public static final int REGION_DEFINITION=31;
    public static final int CONCURRENT_STATE_DEFINITION=29;
    public static final int T__121=121;
    public static final int T__103=103;
    public static final int T__84=84;
    public static final int ENTRY_RULE=39;
    public static final int JavaIDDigit=69;
    public static final int T__97=97;
    public static final int T__75=75;
    public static final int T__105=105;
    public static final int T__111=111;
    public static final int BLOCKS=16;
    public static final int T__116=116;
    public static final int EOF=-1;
    public static final int T__76=76;
    public static final int T__82=82;
    public static final int OctalEscape=67;
    public static final int T__81=81;
    public static final int VIRTUAL=22;
    public static final int T__83=83;
    public static final int T__102=102;

    // delegates
    // delegators

    public StateMachineLexer() {;} 
    public StateMachineLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public StateMachineLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "StateMachine.g"; }

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:9:7: ( 'statemachine' )
            // StateMachine.g:9:9: 'statemachine'
            {
            match("statemachine"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:10:7: ( 'transition' )
            // StateMachine.g:10:9: 'transition'
            {
            match("transition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:11:7: ( '(' )
            // StateMachine.g:11:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:12:7: ( ',' )
            // StateMachine.g:12:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:13:7: ( ')' )
            // StateMachine.g:13:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:14:7: ( 'concurrent' )
            // StateMachine.g:14:9: 'concurrent'
            {
            match("concurrent"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:15:7: ( 'state' )
            // StateMachine.g:15:9: 'state'
            {
            match("state"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:16:7: ( 'region' )
            // StateMachine.g:16:9: 'region'
            {
            match("region"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:17:7: ( 'pseudo-end' )
            // StateMachine.g:17:9: 'pseudo-end'
            {
            match("pseudo-end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:18:7: ( 'pseudo-start' )
            // StateMachine.g:18:9: 'pseudo-start'
            {
            match("pseudo-start"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:19:7: ( 'simple' )
            // StateMachine.g:19:9: 'simple'
            {
            match("simple"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:20:7: ( 'onTimeOut' )
            // StateMachine.g:20:9: 'onTimeOut'
            {
            match("onTimeOut"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:21:7: ( 'onExit' )
            // StateMachine.g:21:9: 'onExit'
            {
            match("onExit"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:22:7: ( 'onEntry' )
            // StateMachine.g:22:9: 'onEntry'
            {
            match("onEntry"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:23:7: ( 'composite' )
            // StateMachine.g:23:9: 'composite'
            {
            match("composite"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:24:7: ( '\"\"xslt://' )
            // StateMachine.g:24:9: '\"\"xslt://'
            {
            match("\"\"xslt://"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:25:7: ( '\"' )
            // StateMachine.g:25:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:26:7: ( 'rule' )
            // StateMachine.g:26:9: 'rule'
            {
            match("rule"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:27:7: ( 'virtual' )
            // StateMachine.g:27:9: 'virtual'
            {
            match("virtual"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:28:7: ( 'rulefunction' )
            // StateMachine.g:28:9: 'rulefunction'
            {
            match("rulefunction"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:29:7: ( 'attribute' )
            // StateMachine.g:29:9: 'attribute'
            {
            match("attribute"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:30:7: ( 'priority' )
            // StateMachine.g:30:9: 'priority'
            {
            match("priority"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:31:7: ( 'requeue' )
            // StateMachine.g:31:9: 'requeue'
            {
            match("requeue"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:32:7: ( '$lastmod' )
            // StateMachine.g:32:9: '$lastmod'
            {
            match("$lastmod"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:33:7: ( 'declare' )
            // StateMachine.g:33:9: 'declare'
            {
            match("declare"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:34:7: ( 'scope' )
            // StateMachine.g:34:9: 'scope'
            {
            match("scope"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:35:7: ( 'when' )
            // StateMachine.g:35:9: 'when'
            {
            match("when"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:36:7: ( '||' )
            // StateMachine.g:36:9: '||'
            {
            match("||"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:37:8: ( '&&' )
            // StateMachine.g:37:10: '&&'
            {
            match("&&"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:38:8: ( 'instanceof' )
            // StateMachine.g:38:10: 'instanceof'
            {
            match("instanceof"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:39:8: ( '[' )
            // StateMachine.g:39:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:40:8: ( ']' )
            // StateMachine.g:40:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:41:8: ( '+' )
            // StateMachine.g:41:10: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:42:8: ( '-' )
            // StateMachine.g:42:10: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:43:8: ( '*' )
            // StateMachine.g:43:10: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "T__107"
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:44:8: ( '/' )
            // StateMachine.g:44:10: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__107"

    // $ANTLR start "T__108"
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:45:8: ( '%' )
            // StateMachine.g:45:10: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__108"

    // $ANTLR start "T__109"
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:46:8: ( '!' )
            // StateMachine.g:46:10: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__109"

    // $ANTLR start "T__110"
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:47:8: ( '.' )
            // StateMachine.g:47:10: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__110"

    // $ANTLR start "T__111"
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:48:8: ( 'true' )
            // StateMachine.g:48:10: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__111"

    // $ANTLR start "T__112"
    public final void mT__112() throws RecognitionException {
        try {
            int _type = T__112;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:49:8: ( 'false' )
            // StateMachine.g:49:10: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__112"

    // $ANTLR start "T__113"
    public final void mT__113() throws RecognitionException {
        try {
            int _type = T__113;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:50:8: ( 'then' )
            // StateMachine.g:50:10: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__113"

    // $ANTLR start "T__114"
    public final void mT__114() throws RecognitionException {
        try {
            int _type = T__114;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:51:8: ( 'body' )
            // StateMachine.g:51:10: 'body'
            {
            match("body"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__114"

    // $ANTLR start "T__115"
    public final void mT__115() throws RecognitionException {
        try {
            int _type = T__115;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:52:8: ( 'return' )
            // StateMachine.g:52:10: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__115"

    // $ANTLR start "T__116"
    public final void mT__116() throws RecognitionException {
        try {
            int _type = T__116;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:53:8: ( 'break' )
            // StateMachine.g:53:10: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__116"

    // $ANTLR start "T__117"
    public final void mT__117() throws RecognitionException {
        try {
            int _type = T__117;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:54:8: ( 'continue' )
            // StateMachine.g:54:10: 'continue'
            {
            match("continue"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__117"

    // $ANTLR start "T__118"
    public final void mT__118() throws RecognitionException {
        try {
            int _type = T__118;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:55:8: ( 'throw' )
            // StateMachine.g:55:10: 'throw'
            {
            match("throw"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__118"

    // $ANTLR start "T__119"
    public final void mT__119() throws RecognitionException {
        try {
            int _type = T__119;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:56:8: ( 'if' )
            // StateMachine.g:56:10: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__119"

    // $ANTLR start "T__120"
    public final void mT__120() throws RecognitionException {
        try {
            int _type = T__120;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:57:8: ( 'else' )
            // StateMachine.g:57:10: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__120"

    // $ANTLR start "T__121"
    public final void mT__121() throws RecognitionException {
        try {
            int _type = T__121;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:58:8: ( 'while' )
            // StateMachine.g:58:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__121"

    // $ANTLR start "T__122"
    public final void mT__122() throws RecognitionException {
        try {
            int _type = T__122;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:59:8: ( 'for' )
            // StateMachine.g:59:10: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__122"

    // $ANTLR start "T__123"
    public final void mT__123() throws RecognitionException {
        try {
            int _type = T__123;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:60:8: ( 'try' )
            // StateMachine.g:60:10: 'try'
            {
            match("try"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__123"

    // $ANTLR start "T__124"
    public final void mT__124() throws RecognitionException {
        try {
            int _type = T__124;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:61:8: ( 'catch' )
            // StateMachine.g:61:10: 'catch'
            {
            match("catch"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__124"

    // $ANTLR start "T__125"
    public final void mT__125() throws RecognitionException {
        try {
            int _type = T__125;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:62:8: ( 'finally' )
            // StateMachine.g:62:10: 'finally'
            {
            match("finally"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__125"

    // $ANTLR start "T__126"
    public final void mT__126() throws RecognitionException {
        try {
            int _type = T__126;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:63:8: ( 'boolean' )
            // StateMachine.g:63:10: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__126"

    // $ANTLR start "T__127"
    public final void mT__127() throws RecognitionException {
        try {
            int _type = T__127;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:64:8: ( 'int' )
            // StateMachine.g:64:10: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__127"

    // $ANTLR start "T__128"
    public final void mT__128() throws RecognitionException {
        try {
            int _type = T__128;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:65:8: ( 'long' )
            // StateMachine.g:65:10: 'long'
            {
            match("long"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__128"

    // $ANTLR start "T__129"
    public final void mT__129() throws RecognitionException {
        try {
            int _type = T__129;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:66:8: ( 'double' )
            // StateMachine.g:66:10: 'double'
            {
            match("double"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__129"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:526:9: ( '=' )
            // StateMachine.g:526:11: '='
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

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:528:12: ( ';' )
            // StateMachine.g:528:14: ';'
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
            // StateMachine.g:529:8: ( '{' )
            // StateMachine.g:529:10: '{'
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
            // StateMachine.g:530:8: ( '}' )
            // StateMachine.g:530:10: '}'
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

    // $ANTLR start "ANNOTATE"
    public final void mANNOTATE() throws RecognitionException {
        try {
            int _type = ANNOTATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:531:10: ( '@' )
            // StateMachine.g:531:13: '@'
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

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:532:4: ( '==' )
            // StateMachine.g:532:6: '=='
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
            // StateMachine.g:533:4: ( '!=' | 'not' 'equal' )
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
                    // StateMachine.g:533:6: '!='
                    {
                    match("!="); 


                    }
                    break;
                case 2 :
                    // StateMachine.g:533:13: 'not' 'equal'
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
            // StateMachine.g:534:4: ( '<' | 'less' 'than' )
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
                    // StateMachine.g:534:6: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // StateMachine.g:534:12: 'less' 'than'
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
            // StateMachine.g:535:4: ( '>' | 'greater' 'than' )
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
                    // StateMachine.g:535:6: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 2 :
                    // StateMachine.g:535:12: 'greater' 'than'
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
            // StateMachine.g:536:4: ( '<=' | 'less' 'than' 'or' 'equal' )
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
                    // StateMachine.g:536:6: '<='
                    {
                    match("<="); 


                    }
                    break;
                case 2 :
                    // StateMachine.g:536:13: 'less' 'than' 'or' 'equal'
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
            // StateMachine.g:537:4: ( '>=' | 'greater' 'than' 'or' 'equal' )
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
                    // StateMachine.g:537:6: '>='
                    {
                    match(">="); 


                    }
                    break;
                case 2 :
                    // StateMachine.g:537:13: 'greater' 'than' 'or' 'equal'
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

    // $ANTLR start "NullLiteral"
    public final void mNullLiteral() throws RecognitionException {
        try {
            int _type = NullLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:538:13: ( 'null' )
            // StateMachine.g:538:15: 'null'
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
            // StateMachine.g:540:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // StateMachine.g:540:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // StateMachine.g:540:28: ( HexDigit )+
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
            	    // StateMachine.g:540:28: HexDigit
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

            // StateMachine.g:540:38: ( IntegerTypeSuffix )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='L'||LA7_0=='l') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // StateMachine.g:540:38: IntegerTypeSuffix
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
            // StateMachine.g:542:16: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )? )
            // StateMachine.g:542:18: ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )?
            {
            // StateMachine.g:542:18: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='0') ) {
                alt9=1;
            }
            else if ( ((LA9_0>='1' && LA9_0<='9')) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // StateMachine.g:542:19: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // StateMachine.g:542:25: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // StateMachine.g:542:34: ( '0' .. '9' )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // StateMachine.g:542:34: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            // StateMachine.g:542:45: ( IntegerTypeSuffix )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='L'||LA10_0=='l') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // StateMachine.g:542:45: IntegerTypeSuffix
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
            // StateMachine.g:544:14: ( '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )? )
            // StateMachine.g:544:16: '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            // StateMachine.g:544:20: ( '0' .. '7' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='7')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // StateMachine.g:544:21: '0' .. '7'
            	    {
            	    matchRange('0','7'); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);

            // StateMachine.g:544:32: ( IntegerTypeSuffix )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='L'||LA12_0=='l') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // StateMachine.g:544:32: IntegerTypeSuffix
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

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // StateMachine.g:547:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // StateMachine.g:547:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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
            // StateMachine.g:550:19: ( ( 'l' | 'L' ) )
            // StateMachine.g:550:21: ( 'l' | 'L' )
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
            // StateMachine.g:553:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix )
            int alt23=4;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // StateMachine.g:553:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )?
                    {
                    // StateMachine.g:553:9: ( '0' .. '9' )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // StateMachine.g:553:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

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

                    match('.'); 
                    // StateMachine.g:553:25: ( '0' .. '9' )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // StateMachine.g:553:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);

                    // StateMachine.g:553:37: ( Exponent )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='E'||LA15_0=='e') ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // StateMachine.g:553:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // StateMachine.g:553:47: ( FloatTypeSuffix )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='D'||LA16_0=='F'||LA16_0=='d'||LA16_0=='f') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // StateMachine.g:553:47: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // StateMachine.g:554:9: '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )?
                    {
                    match('.'); 
                    // StateMachine.g:554:13: ( '0' .. '9' )+
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
                    	    // StateMachine.g:554:14: '0' .. '9'
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

                    // StateMachine.g:554:25: ( Exponent )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0=='E'||LA18_0=='e') ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // StateMachine.g:554:25: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // StateMachine.g:554:35: ( FloatTypeSuffix )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='D'||LA19_0=='F'||LA19_0=='d'||LA19_0=='f') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // StateMachine.g:554:35: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // StateMachine.g:555:9: ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )?
                    {
                    // StateMachine.g:555:9: ( '0' .. '9' )+
                    int cnt20=0;
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // StateMachine.g:555:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt20 >= 1 ) break loop20;
                                EarlyExitException eee =
                                    new EarlyExitException(20, input);
                                throw eee;
                        }
                        cnt20++;
                    } while (true);

                    mExponent(); 
                    // StateMachine.g:555:30: ( FloatTypeSuffix )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0=='D'||LA21_0=='F'||LA21_0=='d'||LA21_0=='f') ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // StateMachine.g:555:30: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // StateMachine.g:556:9: ( '0' .. '9' )+ FloatTypeSuffix
                    {
                    // StateMachine.g:556:9: ( '0' .. '9' )+
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
                    	    // StateMachine.g:556:10: '0' .. '9'
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
            // StateMachine.g:560:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // StateMachine.g:560:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // StateMachine.g:560:22: ( '+' | '-' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='+'||LA24_0=='-') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // StateMachine.g:
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

            // StateMachine.g:560:33: ( '0' .. '9' )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>='0' && LA25_0<='9')) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // StateMachine.g:560:34: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
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
            // StateMachine.g:563:17: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // StateMachine.g:563:19: ( 'f' | 'F' | 'd' | 'D' )
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
            // StateMachine.g:566:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            // StateMachine.g:566:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 
            // StateMachine.g:566:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0=='\\') ) {
                alt26=1;
            }
            else if ( ((LA26_0>='\u0000' && LA26_0<='&')||(LA26_0>='(' && LA26_0<='[')||(LA26_0>=']' && LA26_0<='\uFFFF')) ) {
                alt26=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // StateMachine.g:566:16: EscapeSequence
                    {
                    mEscapeSequence(); 

                    }
                    break;
                case 2 :
                    // StateMachine.g:566:33: ~ ( '\\'' | '\\\\' )
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
            // StateMachine.g:570:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // StateMachine.g:570:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // StateMachine.g:570:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop27:
            do {
                int alt27=3;
                int LA27_0 = input.LA(1);

                if ( (LA27_0=='\\') ) {
                    alt27=1;
                }
                else if ( ((LA27_0>='\u0000' && LA27_0<='!')||(LA27_0>='#' && LA27_0<='[')||(LA27_0>=']' && LA27_0<='\uFFFF')) ) {
                    alt27=2;
                }


                switch (alt27) {
            	case 1 :
            	    // StateMachine.g:570:14: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:570:31: ~ ( '\\\\' | '\"' )
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
            	    break loop27;
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
            // StateMachine.g:575:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UnicodeEscape | OctalEscape )
            int alt28=3;
            int LA28_0 = input.LA(1);

            if ( (LA28_0=='\\') ) {
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
                    alt28=1;
                    }
                    break;
                case 'u':
                    {
                    alt28=2;
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
                    alt28=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // StateMachine.g:575:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
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
                    // StateMachine.g:576:9: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // StateMachine.g:577:9: OctalEscape
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
            // StateMachine.g:582:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt29=3;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='\\') ) {
                int LA29_1 = input.LA(2);

                if ( ((LA29_1>='0' && LA29_1<='3')) ) {
                    int LA29_2 = input.LA(3);

                    if ( ((LA29_2>='0' && LA29_2<='7')) ) {
                        int LA29_5 = input.LA(4);

                        if ( ((LA29_5>='0' && LA29_5<='7')) ) {
                            alt29=1;
                        }
                        else {
                            alt29=2;}
                    }
                    else {
                        alt29=3;}
                }
                else if ( ((LA29_1>='4' && LA29_1<='7')) ) {
                    int LA29_3 = input.LA(3);

                    if ( ((LA29_3>='0' && LA29_3<='7')) ) {
                        alt29=2;
                    }
                    else {
                        alt29=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // StateMachine.g:582:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // StateMachine.g:582:14: ( '0' .. '3' )
                    // StateMachine.g:582:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // StateMachine.g:582:25: ( '0' .. '7' )
                    // StateMachine.g:582:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // StateMachine.g:582:36: ( '0' .. '7' )
                    // StateMachine.g:582:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // StateMachine.g:583:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // StateMachine.g:583:14: ( '0' .. '7' )
                    // StateMachine.g:583:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // StateMachine.g:583:25: ( '0' .. '7' )
                    // StateMachine.g:583:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // StateMachine.g:584:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // StateMachine.g:584:14: ( '0' .. '7' )
                    // StateMachine.g:584:15: '0' .. '7'
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
            // StateMachine.g:589:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // StateMachine.g:589:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
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
            // StateMachine.g:600:5: ( Letter ( Letter | JavaIDDigit )* )
            // StateMachine.g:600:9: Letter ( Letter | JavaIDDigit )*
            {
            mLetter(); 
            // StateMachine.g:600:16: ( Letter | JavaIDDigit )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0=='$'||(LA30_0>='0' && LA30_0<='9')||(LA30_0>='A' && LA30_0<='Z')||LA30_0=='_'||(LA30_0>='a' && LA30_0<='z')||(LA30_0>='\u00C0' && LA30_0<='\u00D6')||(LA30_0>='\u00D8' && LA30_0<='\u00F6')||(LA30_0>='\u00F8' && LA30_0<='\u1FFF')||(LA30_0>='\u3040' && LA30_0<='\u318F')||(LA30_0>='\u3300' && LA30_0<='\u337F')||(LA30_0>='\u3400' && LA30_0<='\u3D2D')||(LA30_0>='\u4E00' && LA30_0<='\u9FFF')||(LA30_0>='\uF900' && LA30_0<='\uFAFF')) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // StateMachine.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop30;
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

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // StateMachine.g:608:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' )
            // StateMachine.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
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
            // StateMachine.g:625:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // StateMachine.g:
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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:642:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // StateMachine.g:642:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // StateMachine.g:646:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // StateMachine.g:646:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // StateMachine.g:646:14: ( options {greedy=false; } : . )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0=='*') ) {
                    int LA31_1 = input.LA(2);

                    if ( (LA31_1=='/') ) {
                        alt31=2;
                    }
                    else if ( ((LA31_1>='\u0000' && LA31_1<='.')||(LA31_1>='0' && LA31_1<='\uFFFF')) ) {
                        alt31=1;
                    }


                }
                else if ( ((LA31_0>='\u0000' && LA31_0<=')')||(LA31_0>='+' && LA31_0<='\uFFFF')) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // StateMachine.g:646:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop31;
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
            // StateMachine.g:650:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // StateMachine.g:650:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // StateMachine.g:650:12: (~ ( '\\n' | '\\r' ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>='\u0000' && LA32_0<='\t')||(LA32_0>='\u000B' && LA32_0<='\f')||(LA32_0>='\u000E' && LA32_0<='\uFFFF')) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // StateMachine.g:650:12: ~ ( '\\n' | '\\r' )
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
            	    break loop32;
                }
            } while (true);

            // StateMachine.g:650:26: ( '\\r' )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0=='\r') ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // StateMachine.g:650:26: '\\r'
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

    public void mTokens() throws RecognitionException {
        // StateMachine.g:1:8: ( T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | ASSIGN | SEMICOLON | LBRACE | RBRACE | ANNOTATE | EQ | NE | LT | GT | LE | GE | NullLiteral | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT )
        int alt34=80;
        alt34 = dfa34.predict(input);
        switch (alt34) {
            case 1 :
                // StateMachine.g:1:10: T__72
                {
                mT__72(); 

                }
                break;
            case 2 :
                // StateMachine.g:1:16: T__73
                {
                mT__73(); 

                }
                break;
            case 3 :
                // StateMachine.g:1:22: T__74
                {
                mT__74(); 

                }
                break;
            case 4 :
                // StateMachine.g:1:28: T__75
                {
                mT__75(); 

                }
                break;
            case 5 :
                // StateMachine.g:1:34: T__76
                {
                mT__76(); 

                }
                break;
            case 6 :
                // StateMachine.g:1:40: T__77
                {
                mT__77(); 

                }
                break;
            case 7 :
                // StateMachine.g:1:46: T__78
                {
                mT__78(); 

                }
                break;
            case 8 :
                // StateMachine.g:1:52: T__79
                {
                mT__79(); 

                }
                break;
            case 9 :
                // StateMachine.g:1:58: T__80
                {
                mT__80(); 

                }
                break;
            case 10 :
                // StateMachine.g:1:64: T__81
                {
                mT__81(); 

                }
                break;
            case 11 :
                // StateMachine.g:1:70: T__82
                {
                mT__82(); 

                }
                break;
            case 12 :
                // StateMachine.g:1:76: T__83
                {
                mT__83(); 

                }
                break;
            case 13 :
                // StateMachine.g:1:82: T__84
                {
                mT__84(); 

                }
                break;
            case 14 :
                // StateMachine.g:1:88: T__85
                {
                mT__85(); 

                }
                break;
            case 15 :
                // StateMachine.g:1:94: T__86
                {
                mT__86(); 

                }
                break;
            case 16 :
                // StateMachine.g:1:100: T__87
                {
                mT__87(); 

                }
                break;
            case 17 :
                // StateMachine.g:1:106: T__88
                {
                mT__88(); 

                }
                break;
            case 18 :
                // StateMachine.g:1:112: T__89
                {
                mT__89(); 

                }
                break;
            case 19 :
                // StateMachine.g:1:118: T__90
                {
                mT__90(); 

                }
                break;
            case 20 :
                // StateMachine.g:1:124: T__91
                {
                mT__91(); 

                }
                break;
            case 21 :
                // StateMachine.g:1:130: T__92
                {
                mT__92(); 

                }
                break;
            case 22 :
                // StateMachine.g:1:136: T__93
                {
                mT__93(); 

                }
                break;
            case 23 :
                // StateMachine.g:1:142: T__94
                {
                mT__94(); 

                }
                break;
            case 24 :
                // StateMachine.g:1:148: T__95
                {
                mT__95(); 

                }
                break;
            case 25 :
                // StateMachine.g:1:154: T__96
                {
                mT__96(); 

                }
                break;
            case 26 :
                // StateMachine.g:1:160: T__97
                {
                mT__97(); 

                }
                break;
            case 27 :
                // StateMachine.g:1:166: T__98
                {
                mT__98(); 

                }
                break;
            case 28 :
                // StateMachine.g:1:172: T__99
                {
                mT__99(); 

                }
                break;
            case 29 :
                // StateMachine.g:1:178: T__100
                {
                mT__100(); 

                }
                break;
            case 30 :
                // StateMachine.g:1:185: T__101
                {
                mT__101(); 

                }
                break;
            case 31 :
                // StateMachine.g:1:192: T__102
                {
                mT__102(); 

                }
                break;
            case 32 :
                // StateMachine.g:1:199: T__103
                {
                mT__103(); 

                }
                break;
            case 33 :
                // StateMachine.g:1:206: T__104
                {
                mT__104(); 

                }
                break;
            case 34 :
                // StateMachine.g:1:213: T__105
                {
                mT__105(); 

                }
                break;
            case 35 :
                // StateMachine.g:1:220: T__106
                {
                mT__106(); 

                }
                break;
            case 36 :
                // StateMachine.g:1:227: T__107
                {
                mT__107(); 

                }
                break;
            case 37 :
                // StateMachine.g:1:234: T__108
                {
                mT__108(); 

                }
                break;
            case 38 :
                // StateMachine.g:1:241: T__109
                {
                mT__109(); 

                }
                break;
            case 39 :
                // StateMachine.g:1:248: T__110
                {
                mT__110(); 

                }
                break;
            case 40 :
                // StateMachine.g:1:255: T__111
                {
                mT__111(); 

                }
                break;
            case 41 :
                // StateMachine.g:1:262: T__112
                {
                mT__112(); 

                }
                break;
            case 42 :
                // StateMachine.g:1:269: T__113
                {
                mT__113(); 

                }
                break;
            case 43 :
                // StateMachine.g:1:276: T__114
                {
                mT__114(); 

                }
                break;
            case 44 :
                // StateMachine.g:1:283: T__115
                {
                mT__115(); 

                }
                break;
            case 45 :
                // StateMachine.g:1:290: T__116
                {
                mT__116(); 

                }
                break;
            case 46 :
                // StateMachine.g:1:297: T__117
                {
                mT__117(); 

                }
                break;
            case 47 :
                // StateMachine.g:1:304: T__118
                {
                mT__118(); 

                }
                break;
            case 48 :
                // StateMachine.g:1:311: T__119
                {
                mT__119(); 

                }
                break;
            case 49 :
                // StateMachine.g:1:318: T__120
                {
                mT__120(); 

                }
                break;
            case 50 :
                // StateMachine.g:1:325: T__121
                {
                mT__121(); 

                }
                break;
            case 51 :
                // StateMachine.g:1:332: T__122
                {
                mT__122(); 

                }
                break;
            case 52 :
                // StateMachine.g:1:339: T__123
                {
                mT__123(); 

                }
                break;
            case 53 :
                // StateMachine.g:1:346: T__124
                {
                mT__124(); 

                }
                break;
            case 54 :
                // StateMachine.g:1:353: T__125
                {
                mT__125(); 

                }
                break;
            case 55 :
                // StateMachine.g:1:360: T__126
                {
                mT__126(); 

                }
                break;
            case 56 :
                // StateMachine.g:1:367: T__127
                {
                mT__127(); 

                }
                break;
            case 57 :
                // StateMachine.g:1:374: T__128
                {
                mT__128(); 

                }
                break;
            case 58 :
                // StateMachine.g:1:381: T__129
                {
                mT__129(); 

                }
                break;
            case 59 :
                // StateMachine.g:1:388: ASSIGN
                {
                mASSIGN(); 

                }
                break;
            case 60 :
                // StateMachine.g:1:395: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 61 :
                // StateMachine.g:1:405: LBRACE
                {
                mLBRACE(); 

                }
                break;
            case 62 :
                // StateMachine.g:1:412: RBRACE
                {
                mRBRACE(); 

                }
                break;
            case 63 :
                // StateMachine.g:1:419: ANNOTATE
                {
                mANNOTATE(); 

                }
                break;
            case 64 :
                // StateMachine.g:1:428: EQ
                {
                mEQ(); 

                }
                break;
            case 65 :
                // StateMachine.g:1:431: NE
                {
                mNE(); 

                }
                break;
            case 66 :
                // StateMachine.g:1:434: LT
                {
                mLT(); 

                }
                break;
            case 67 :
                // StateMachine.g:1:437: GT
                {
                mGT(); 

                }
                break;
            case 68 :
                // StateMachine.g:1:440: LE
                {
                mLE(); 

                }
                break;
            case 69 :
                // StateMachine.g:1:443: GE
                {
                mGE(); 

                }
                break;
            case 70 :
                // StateMachine.g:1:446: NullLiteral
                {
                mNullLiteral(); 

                }
                break;
            case 71 :
                // StateMachine.g:1:458: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 72 :
                // StateMachine.g:1:469: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 73 :
                // StateMachine.g:1:484: OctalLiteral
                {
                mOctalLiteral(); 

                }
                break;
            case 74 :
                // StateMachine.g:1:497: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 75 :
                // StateMachine.g:1:518: CharacterLiteral
                {
                mCharacterLiteral(); 

                }
                break;
            case 76 :
                // StateMachine.g:1:535: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 77 :
                // StateMachine.g:1:549: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 78 :
                // StateMachine.g:1:560: WS
                {
                mWS(); 

                }
                break;
            case 79 :
                // StateMachine.g:1:563: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 80 :
                // StateMachine.g:1:571: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA23 dfa23 = new DFA23(this);
    protected DFA34 dfa34 = new DFA34(this);
    static final String DFA23_eotS =
        "\6\uffff";
    static final String DFA23_eofS =
        "\6\uffff";
    static final String DFA23_minS =
        "\2\56\4\uffff";
    static final String DFA23_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA23_acceptS =
        "\2\uffff\1\2\1\4\1\3\1\1";
    static final String DFA23_specialS =
        "\6\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\5\1\uffff\12\1\12\uffff\1\3\1\4\1\3\35\uffff\1\3\1\4\1"+
            "\3",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "552:1: FloatingPointLiteral : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix );";
        }
    }
    static final String DFA34_eotS =
        "\1\uffff\2\54\3\uffff\4\54\1\74\5\54\2\uffff\1\54\5\uffff\1\107"+
        "\1\uffff\1\111\1\112\4\54\1\125\4\uffff\1\54\1\131\1\133\1\54\2"+
        "\137\3\uffff\14\54\1\73\2\uffff\7\54\1\176\7\uffff\10\54\2\uffff"+
        "\2\54\4\uffff\1\54\1\uffff\1\u008b\1\uffff\1\137\5\54\1\u0091\15"+
        "\54\1\uffff\10\54\1\u00a9\1\uffff\1\54\1\u00ab\12\54\1\uffff\4\54"+
        "\1\u00ba\1\uffff\1\u00bb\10\54\1\u00c5\12\54\1\u00d0\2\54\1\uffff"+
        "\1\54\1\uffff\1\54\1\u00d5\2\54\1\u00d8\1\u00d9\2\54\1\u00dc\1\54"+
        "\1\u00df\1\54\1\u00e1\1\54\2\uffff\1\u00e3\3\54\1\u00e7\4\54\1\uffff"+
        "\12\54\1\uffff\1\u00f6\1\54\1\u00f8\1\54\1\uffff\1\54\1\u00fb\2"+
        "\uffff\2\54\1\uffff\2\54\1\uffff\1\u0100\1\uffff\1\54\1\uffff\3"+
        "\54\1\uffff\1\u0105\1\54\1\u0107\4\54\1\u010c\5\54\1\u0112\1\uffff"+
        "\1\54\1\uffff\2\54\1\uffff\4\54\1\uffff\4\54\1\uffff\1\u011e\1\uffff"+
        "\1\54\1\uffff\2\54\1\uffff\1\u0124\1\u0125\2\54\1\u0128\1\uffff"+
        "\1\54\1\u012a\1\u012b\6\54\1\u0132\1\54\1\uffff\1\54\2\uffff\1\u0135"+
        "\1\54\2\uffff\1\54\1\u0138\1\uffff\1\54\2\uffff\1\131\1\110\4\54"+
        "\1\uffff\1\u013f\1\54\1\uffff\1\u0141\1\u0142\1\uffff\4\54\1\u0147"+
        "\1\u0148\1\uffff\1\54\2\uffff\1\u014a\3\54\2\uffff\1\54\1\uffff"+
        "\1\54\1\133\1\u0151\1\u0152\2\54\2\uffff\4\54\1\130\3\54\1\132";
    static final String DFA34_eofS =
        "\u015c\uffff";
    static final String DFA34_minS =
        "\1\11\1\143\1\150\3\uffff\1\141\1\145\1\162\1\156\1\0\1\151\1\164"+
        "\1\154\1\145\1\150\2\uffff\1\146\5\uffff\1\52\1\uffff\1\75\1\60"+
        "\1\141\1\157\1\154\1\145\1\75\4\uffff\1\157\2\75\1\162\2\56\3\uffff"+
        "\1\141\1\155\1\157\1\141\1\145\1\155\1\164\1\147\1\154\1\145\1\151"+
        "\1\105\1\170\2\uffff\1\162\1\164\1\141\1\143\1\165\1\145\1\163\1"+
        "\44\7\uffff\1\154\1\162\1\156\1\144\1\145\1\163\1\156\1\163\2\uffff"+
        "\1\164\1\154\4\uffff\1\145\1\uffff\1\56\1\uffff\1\56\1\164\2\160"+
        "\1\156\1\145\1\44\1\156\1\157\1\143\1\160\1\143\1\151\2\165\1\145"+
        "\1\165\1\157\1\151\1\156\1\uffff\1\164\1\162\1\163\1\154\1\142\1"+
        "\156\1\154\1\164\1\44\1\uffff\1\163\1\44\1\141\1\171\1\154\1\141"+
        "\1\145\1\147\1\163\1\145\1\154\1\141\1\uffff\1\145\1\154\1\145\1"+
        "\163\1\44\1\uffff\1\44\1\167\1\165\1\151\1\157\1\150\1\157\1\145"+
        "\1\162\1\44\1\144\1\162\1\155\1\151\1\164\1\165\1\151\1\164\1\141"+
        "\1\154\1\44\1\145\1\141\1\uffff\1\145\1\uffff\1\154\1\44\1\145\1"+
        "\153\2\44\1\164\1\161\1\44\1\164\1\44\1\145\1\44\1\151\2\uffff\1"+
        "\44\1\162\1\156\1\163\1\44\1\156\1\165\1\156\1\165\1\uffff\1\157"+
        "\1\151\1\145\1\164\1\162\1\141\1\142\1\155\1\162\1\145\1\uffff\1"+
        "\44\1\156\1\44\1\154\1\uffff\1\141\1\44\2\uffff\1\150\1\165\1\uffff"+
        "\1\145\1\141\1\uffff\1\44\1\uffff\1\164\1\uffff\1\162\1\165\1\151"+
        "\1\uffff\1\44\1\145\1\44\1\156\1\55\1\164\1\117\1\44\1\171\1\154"+
        "\1\165\1\157\1\145\1\44\1\uffff\1\143\1\uffff\1\171\1\156\1\uffff"+
        "\2\141\1\162\1\143\1\uffff\1\151\2\145\1\164\1\uffff\1\44\1\uffff"+
        "\1\143\1\145\1\171\1\165\1\uffff\2\44\1\164\1\144\1\44\1\uffff\1"+
        "\145\2\44\1\156\1\154\1\164\1\150\1\157\1\156\1\44\1\145\1\uffff"+
        "\1\164\2\uffff\1\44\1\164\2\uffff\1\145\1\44\1\uffff\1\157\2\uffff"+
        "\2\44\1\150\1\151\1\156\1\164\1\uffff\1\44\1\151\1\uffff\2\44\1"+
        "\uffff\1\146\1\162\1\141\1\156\2\44\1\uffff\1\157\2\uffff\1\44\1"+
        "\145\1\156\1\145\2\uffff\1\156\1\uffff\1\161\3\44\1\165\1\162\2"+
        "\uffff\1\141\1\145\1\154\1\161\1\44\1\165\1\141\1\154\1\44";
    static final String DFA34_maxS =
        "\1\ufaff\1\164\1\162\3\uffff\1\157\1\165\1\163\1\156\1\uffff\1"+
        "\151\1\164\1\154\1\157\1\150\2\uffff\1\156\5\uffff\1\57\1\uffff"+
        "\1\75\1\71\1\157\1\162\1\154\1\157\1\75\4\uffff\1\165\2\75\1\162"+
        "\1\170\1\146\3\uffff\1\141\1\155\1\157\1\171\1\162\1\156\2\164\1"+
        "\154\1\145\1\151\1\124\1\170\2\uffff\1\162\1\164\1\141\1\143\1\165"+
        "\1\151\1\164\1\ufaff\7\uffff\1\154\1\162\1\156\1\157\1\145\1\163"+
        "\1\156\1\163\2\uffff\1\164\1\154\4\uffff\1\145\1\uffff\1\146\1\uffff"+
        "\1\146\1\164\2\160\1\156\1\145\1\ufaff\1\156\1\157\1\164\1\160\1"+
        "\143\1\151\2\165\1\145\1\165\1\157\1\151\1\170\1\uffff\1\164\1\162"+
        "\1\163\1\154\1\142\1\156\1\154\1\164\1\ufaff\1\uffff\1\163\1\ufaff"+
        "\1\141\1\171\1\154\1\141\1\145\1\147\1\163\1\145\1\154\1\141\1\uffff"+
        "\1\145\1\154\1\145\1\163\1\ufaff\1\uffff\1\ufaff\1\167\1\165\1\151"+
        "\1\157\1\150\1\157\1\145\1\162\1\ufaff\1\144\1\162\1\155\1\151\1"+
        "\164\1\165\1\151\1\164\1\141\1\154\1\ufaff\1\145\1\141\1\uffff\1"+
        "\145\1\uffff\1\154\1\ufaff\1\145\1\153\2\ufaff\1\164\1\161\1\ufaff"+
        "\1\164\1\ufaff\1\145\1\ufaff\1\151\2\uffff\1\ufaff\1\162\1\156\1"+
        "\163\1\ufaff\1\156\1\165\1\156\1\165\1\uffff\1\157\1\151\1\145\1"+
        "\164\1\162\1\141\1\142\1\155\1\162\1\145\1\uffff\1\ufaff\1\156\1"+
        "\ufaff\1\154\1\uffff\1\141\1\ufaff\2\uffff\1\150\1\165\1\uffff\1"+
        "\145\1\141\1\uffff\1\ufaff\1\uffff\1\164\1\uffff\1\162\1\165\1\151"+
        "\1\uffff\1\ufaff\1\145\1\ufaff\1\156\1\55\1\164\1\117\1\ufaff\1"+
        "\171\1\154\1\165\1\157\1\145\1\ufaff\1\uffff\1\143\1\uffff\1\171"+
        "\1\156\1\uffff\2\141\1\162\1\143\1\uffff\1\151\2\145\1\164\1\uffff"+
        "\1\ufaff\1\uffff\1\143\1\163\1\171\1\165\1\uffff\2\ufaff\1\164\1"+
        "\144\1\ufaff\1\uffff\1\145\2\ufaff\1\156\1\154\1\164\1\150\1\157"+
        "\1\156\1\ufaff\1\145\1\uffff\1\164\2\uffff\1\ufaff\1\164\2\uffff"+
        "\1\145\1\ufaff\1\uffff\1\157\2\uffff\2\ufaff\1\150\1\151\1\156\1"+
        "\164\1\uffff\1\ufaff\1\151\1\uffff\2\ufaff\1\uffff\1\146\1\162\1"+
        "\141\1\156\2\ufaff\1\uffff\1\157\2\uffff\1\ufaff\1\145\1\156\1\145"+
        "\2\uffff\1\156\1\uffff\1\161\3\ufaff\1\165\1\162\2\uffff\1\141\1"+
        "\145\1\154\1\161\1\ufaff\1\165\1\141\1\154\1\ufaff";
    static final String DFA34_acceptS =
        "\3\uffff\1\3\1\4\1\5\12\uffff\1\34\1\35\1\uffff\1\37\1\40\1\41"+
        "\1\42\1\43\1\uffff\1\45\7\uffff\1\74\1\75\1\76\1\77\6\uffff\1\113"+
        "\1\115\1\116\15\uffff\1\114\1\21\10\uffff\1\117\1\120\1\44\1\101"+
        "\1\46\1\47\1\112\10\uffff\1\100\1\73\2\uffff\1\104\1\102\1\105\1"+
        "\103\1\uffff\1\107\1\uffff\1\110\24\uffff\1\20\11\uffff\1\60\14"+
        "\uffff\1\111\5\uffff\1\64\27\uffff\1\70\1\uffff\1\63\16\uffff\1"+
        "\50\1\52\11\uffff\1\22\12\uffff\1\33\4\uffff\1\53\2\uffff\1\61\1"+
        "\71\2\uffff\1\106\2\uffff\1\7\1\uffff\1\32\1\uffff\1\57\3\uffff"+
        "\1\65\16\uffff\1\62\1\uffff\1\51\2\uffff\1\55\4\uffff\1\13\4\uffff"+
        "\1\10\1\uffff\1\54\4\uffff\1\15\5\uffff\1\72\13\uffff\1\27\1\uffff"+
        "\1\11\1\12\2\uffff\1\16\1\23\2\uffff\1\31\1\uffff\1\66\1\67\6\uffff"+
        "\1\56\2\uffff\1\26\2\uffff\1\30\6\uffff\1\17\1\uffff\1\14\1\25\4"+
        "\uffff\1\2\1\6\1\uffff\1\36\6\uffff\1\1\1\24\11\uffff";
    static final String DFA34_specialS =
        "\12\uffff\1\0\u0151\uffff}>";
    static final String[] DFA34_transitionS = {
            "\2\55\1\uffff\2\55\22\uffff\1\55\1\32\1\12\1\uffff\1\15\1\31"+
            "\1\21\1\53\1\3\1\5\1\27\1\25\1\4\1\26\1\33\1\30\1\51\11\52\1"+
            "\uffff\1\41\1\46\1\40\1\47\1\uffff\1\44\32\54\1\23\1\uffff\1"+
            "\24\1\uffff\1\54\1\uffff\1\14\1\35\1\6\1\16\1\36\1\34\1\50\1"+
            "\54\1\22\2\54\1\37\1\54\1\45\1\11\1\10\1\54\1\7\1\1\1\2\1\54"+
            "\1\13\1\17\3\54\1\42\1\20\1\43\102\uffff\27\54\1\uffff\37\54"+
            "\1\uffff\u1f08\54\u1040\uffff\u0150\54\u0170\uffff\u0080\54"+
            "\u0080\uffff\u092e\54\u10d2\uffff\u5200\54\u5900\uffff\u0200"+
            "\54",
            "\1\60\5\uffff\1\57\12\uffff\1\56",
            "\1\62\11\uffff\1\61",
            "",
            "",
            "",
            "\1\64\15\uffff\1\63",
            "\1\65\17\uffff\1\66",
            "\1\70\1\67",
            "\1\71",
            "\42\73\1\72\uffdd\73",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100\11\uffff\1\101",
            "\1\102",
            "",
            "",
            "\1\104\7\uffff\1\103",
            "",
            "",
            "",
            "",
            "",
            "\1\105\4\uffff\1\106",
            "",
            "\1\110",
            "\12\113",
            "\1\114\7\uffff\1\116\5\uffff\1\115",
            "\1\117\2\uffff\1\120",
            "\1\121",
            "\1\123\11\uffff\1\122",
            "\1\124",
            "",
            "",
            "",
            "",
            "\1\126\5\uffff\1\127",
            "\1\130",
            "\1\132",
            "\1\134",
            "\1\113\1\uffff\10\136\2\113\12\uffff\3\113\21\uffff\1\135"+
            "\13\uffff\3\113\21\uffff\1\135",
            "\1\113\1\uffff\12\140\12\uffff\3\113\35\uffff\3\113",
            "",
            "",
            "",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144\23\uffff\1\145\3\uffff\1\146",
            "\1\147\14\uffff\1\150",
            "\1\152\1\151",
            "\1\153",
            "\1\154\11\uffff\1\155\2\uffff\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\163\16\uffff\1\162",
            "\1\164",
            "",
            "",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172\3\uffff\1\173",
            "\1\174\1\175",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082\12\uffff\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087",
            "",
            "",
            "\1\u0088",
            "\1\u0089",
            "",
            "",
            "",
            "",
            "\1\u008a",
            "",
            "\1\113\1\uffff\10\136\2\113\12\uffff\3\113\35\uffff\3\113",
            "",
            "\1\113\1\uffff\12\140\12\uffff\3\113\35\uffff\3\113",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094\20\uffff\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u00a0\11\uffff\1\u009f",
            "",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u00aa",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\5"+
            "\54\1\u00c4\24\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08"+
            "\54\u1040\uffff\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e"+
            "\54\u10d2\uffff\u5200\54\u5900\uffff\u0200\54",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\1\u00ce",
            "\1\u00cf",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00d1",
            "\1\u00d2",
            "",
            "\1\u00d3",
            "",
            "\1\u00d4",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00d6",
            "\1\u00d7",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00da",
            "\1\u00db",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00dd",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\14"+
            "\54\1\u00de\15\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08"+
            "\54\u1040\uffff\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e"+
            "\54\u10d2\uffff\u5200\54\u5900\uffff\u0200\54",
            "\1\u00e0",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00e2",
            "",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\1\u00f3",
            "\1\u00f4",
            "\1\u00f5",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00f7",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u00f9",
            "",
            "\1\u00fa",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "",
            "\1\u00fc",
            "\1\u00fd",
            "",
            "\1\u00fe",
            "\1\u00ff",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0101",
            "",
            "\1\u0102",
            "\1\u0103",
            "\1\u0104",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0106",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0108",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u010d",
            "\1\u010e",
            "\1\u010f",
            "\1\u0110",
            "\1\u0111",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0113",
            "",
            "\1\u0114",
            "\1\u0115",
            "",
            "\1\u0116",
            "\1\u0117",
            "\1\u0118",
            "\1\u0119",
            "",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\1\u011d",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u011f",
            "\1\u0120\15\uffff\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0126",
            "\1\u0127",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0129",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\u012f",
            "\1\u0130",
            "\1\u0131",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0133",
            "",
            "\1\u0134",
            "",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0136",
            "",
            "",
            "\1\u0137",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0139",
            "",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\16"+
            "\54\1\u013a\13\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08"+
            "\54\u1040\uffff\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e"+
            "\54\u10d2\uffff\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\1\u013e",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0140",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "\1\u0146",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "",
            "\1\u0149",
            "",
            "",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u014b",
            "\1\u014c",
            "\1\u014d",
            "",
            "",
            "\1\u014e",
            "",
            "\1\u014f",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\16"+
            "\54\1\u0150\13\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08"+
            "\54\u1040\uffff\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e"+
            "\54\u10d2\uffff\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0153",
            "\1\u0154",
            "",
            "",
            "\1\u0155",
            "\1\u0156",
            "\1\u0157",
            "\1\u0158",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54",
            "\1\u0159",
            "\1\u015a",
            "\1\u015b",
            "\1\54\13\uffff\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32"+
            "\54\105\uffff\27\54\1\uffff\37\54\1\uffff\u1f08\54\u1040\uffff"+
            "\u0150\54\u0170\uffff\u0080\54\u0080\uffff\u092e\54\u10d2\uffff"+
            "\u5200\54\u5900\uffff\u0200\54"
    };

    static final short[] DFA34_eot = DFA.unpackEncodedString(DFA34_eotS);
    static final short[] DFA34_eof = DFA.unpackEncodedString(DFA34_eofS);
    static final char[] DFA34_min = DFA.unpackEncodedStringToUnsignedChars(DFA34_minS);
    static final char[] DFA34_max = DFA.unpackEncodedStringToUnsignedChars(DFA34_maxS);
    static final short[] DFA34_accept = DFA.unpackEncodedString(DFA34_acceptS);
    static final short[] DFA34_special = DFA.unpackEncodedString(DFA34_specialS);
    static final short[][] DFA34_transition;

    static {
        int numStates = DFA34_transitionS.length;
        DFA34_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA34_transition[i] = DFA.unpackEncodedString(DFA34_transitionS[i]);
        }
    }

    class DFA34 extends DFA {

        public DFA34(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 34;
            this.eot = DFA34_eot;
            this.eof = DFA34_eof;
            this.min = DFA34_min;
            this.max = DFA34_max;
            this.accept = DFA34_accept;
            this.special = DFA34_special;
            this.transition = DFA34_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | ASSIGN | SEMICOLON | LBRACE | RBRACE | ANNOTATE | EQ | NE | LT | GT | LE | GE | NullLiteral | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | Identifier | WS | COMMENT | LINE_COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA34_10 = input.LA(1);

                        s = -1;
                        if ( (LA34_10=='\"') ) {s = 58;}

                        else if ( ((LA34_10>='\u0000' && LA34_10<='!')||(LA34_10>='#' && LA34_10<='\uFFFF')) ) {s = 59;}

                        else s = 60;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 34, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}