// $ANTLR 3.1.1 StateMachine.g 2009-02-10 10:13:02
 
package com.tibco.cep.studio.core.statemachine; 


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEmptyStreamException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class StateMachineParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE", "RULE_FUNCTION", "RULE_DECL", "NAME", "RULE_BLOCK", "ATTRIBUTE_BLOCK", "STATEMENTS", "DECLARE_BLOCK", "RULE_FUNC_DECL", "SCOPE_BLOCK", "WHEN_BLOCK", "PREDICATES", "BLOCKS", "THEN_BLOCK", "BODY_BLOCK", "SCOPE_DECLARATOR", "TYPE", "DECLARATOR", "VIRTUAL", "PRIMITIVE_TYPE", "ANNOTATION", "STATE_MACHINE", "TRANSITION_DEFINITION", "START_STATE", "END_STATE", "CONCURRENT_STATE_DEFINITION", "REGIONS", "REGION_DEFINITION", "DEFINITIONS", "PSEUDO_END_STATE_DEF", "PSEUDO_START_STATE_DEF", "RULES", "SIMPLE_STATE_DEF", "TIMEOUT_RULE", "EXIT_RULE", "ENTRY_RULE", "COMPOSITE_STATE_DEFINITION", "Identifier", "ASSIGN", "SEMICOLON", "StringLiteral", "LBRACE", "RBRACE", "EQ", "NE", "LT", "GT", "LE", "GE", "FloatingPointLiteral", "NullLiteral", "DecimalLiteral", "HexLiteral", "WS", "ANNOTATE", "HexDigit", "IntegerTypeSuffix", "OctalLiteral", "Exponent", "FloatTypeSuffix", "EscapeSequence", "CharacterLiteral", "UnicodeEscape", "OctalEscape", "Letter", "JavaIDDigit", "COMMENT", "LINE_COMMENT", "'statemachine'", "'transition'", "'('", "','", "')'", "'concurrent'", "'state'", "'region'", "'pseudo-end'", "'pseudo-start'", "'simple'", "'onTimeOut'", "'onExit'", "'onEntry'", "'composite'", "'\"\"xslt://'", "'\"'", "'rule'", "'virtual'", "'rulefunction'", "'attribute'", "'priority'", "'requeue'", "'$lastmod'", "'declare'", "'scope'", "'when'", "'||'", "'&&'", "'instanceof'", "'['", "']'", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "'.'", "'true'", "'false'", "'then'", "'body'", "'return'", "'break'", "'continue'", "'throw'", "'if'", "'else'", "'while'", "'for'", "'try'", "'catch'", "'finally'", "'boolean'", "'int'", "'long'", "'double'"
    };
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
    public static final int LE=51;
    public static final int T__96=96;
    public static final int SCOPE_BLOCK=13;
    public static final int T__119=119;
    public static final int RULE_FUNC_DECL=12;
    public static final int T__108=108;
    public static final int T__112=112;
    public static final int FloatTypeSuffix=63;
    public static final int RULES=35;
    public static final int T__118=118;
    public static final int T__113=113;
    public static final int REGIONS=30;
    public static final int EXIT_RULE=38;
    public static final int IntegerTypeSuffix=60;
    public static final int END_STATE=28;
    public static final int Identifier=41;
    public static final int T__89=89;
    public static final int WS=57;
    public static final int NullLiteral=54;
    public static final int EQ=47;
    public static final int T__79=79;
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
    public static final int T__127=127;
    public static final int TRANSITION_DEFINITION=26;
    public static final int FloatingPointLiteral=53;
    public static final int T__120=120;
    public static final int T__98=98;
    public static final int Exponent=62;
    public static final int RULE_BLOCK=8;
    public static final int T__78=78;
    public static final int T__117=117;
    public static final int BODY_BLOCK=18;
    public static final int CharacterLiteral=65;
    public static final int GT=50;
    public static final int NE=48;
    public static final int COMMENT=70;
    public static final int T__99=99;
    public static final int StringLiteral=44;
    public static final int RULE_DECL=6;
    public static final int T__77=77;
    public static final int T__129=129;
    public static final int REGION_DEFINITION=31;
    public static final int CONCURRENT_STATE_DEFINITION=29;
    public static final int T__103=103;
    public static final int T__121=121;
    public static final int JavaIDDigit=69;
    public static final int ENTRY_RULE=39;
    public static final int T__84=84;
    public static final int T__97=97;
    public static final int T__111=111;
    public static final int T__105=105;
    public static final int T__75=75;
    public static final int BLOCKS=16;
    public static final int EOF=-1;
    public static final int T__116=116;
    public static final int T__76=76;
    public static final int T__82=82;
    public static final int OctalEscape=67;
    public static final int T__81=81;
    public static final int VIRTUAL=22;
    public static final int T__83=83;
    public static final int T__102=102;

    // delegates
    // delegators


        public StateMachineParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public StateMachineParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return StateMachineParser.tokenNames; }
    public String getGrammarFileName() { return "StateMachine.g"; }


    private boolean virtual = false;


    public static class startRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "startRule"
    // StateMachine.g:42:1: startRule : ( stateMachineDefinition | ruleDeclaration | ruleFunctionDeclaration ) EOF ;
    public final StateMachineParser.startRule_return startRule() throws RecognitionException {
        StateMachineParser.startRule_return retval = new StateMachineParser.startRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token EOF4=null;
        StateMachineParser.stateMachineDefinition_return stateMachineDefinition1 = null;

        StateMachineParser.ruleDeclaration_return ruleDeclaration2 = null;

        StateMachineParser.ruleFunctionDeclaration_return ruleFunctionDeclaration3 = null;


        Object EOF4_tree=null;

        try {
            // StateMachine.g:51:2: ( ( stateMachineDefinition | ruleDeclaration | ruleFunctionDeclaration ) EOF )
            // StateMachine.g:51:4: ( stateMachineDefinition | ruleDeclaration | ruleFunctionDeclaration ) EOF
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:51:4: ( stateMachineDefinition | ruleDeclaration | ruleFunctionDeclaration )
            int alt1=3;
            switch ( input.LA(1) ) {
            case 72:
                {
                alt1=1;
                }
                break;
            case 89:
                {
                alt1=2;
                }
                break;
            case 90:
            case 91:
                {
                alt1=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // StateMachine.g:51:5: stateMachineDefinition
                    {
                    pushFollow(FOLLOW_stateMachineDefinition_in_startRule188);
                    stateMachineDefinition1=stateMachineDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, stateMachineDefinition1.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:52:4: ruleDeclaration
                    {
                    pushFollow(FOLLOW_ruleDeclaration_in_startRule194);
                    ruleDeclaration2=ruleDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleDeclaration2.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:53:4: ruleFunctionDeclaration
                    {
                    pushFollow(FOLLOW_ruleFunctionDeclaration_in_startRule200);
                    ruleFunctionDeclaration3=ruleFunctionDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ruleFunctionDeclaration3.getTree());

                    }
                    break;

            }

            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_startRule203); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF4_tree = (Object)adaptor.create(EOF4);
            adaptor.addChild(root_0, EOF4_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "startRule"

    public static class stateMachineDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateMachineDefinition"
    // StateMachine.g:56:1: stateMachineDefinition : 'statemachine' name '{' ( stateMachineBlock )* '}' -> ^( STATE_MACHINE ^( NAME name ) ^( BLOCKS ( stateMachineBlock )* ) ) ;
    public final StateMachineParser.stateMachineDefinition_return stateMachineDefinition() throws RecognitionException {
        StateMachineParser.stateMachineDefinition_return retval = new StateMachineParser.stateMachineDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal5=null;
        Token char_literal7=null;
        Token char_literal9=null;
        StateMachineParser.name_return name6 = null;

        StateMachineParser.stateMachineBlock_return stateMachineBlock8 = null;


        Object string_literal5_tree=null;
        Object char_literal7_tree=null;
        Object char_literal9_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_72=new RewriteRuleTokenStream(adaptor,"token 72");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_stateMachineBlock=new RewriteRuleSubtreeStream(adaptor,"rule stateMachineBlock");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:57:2: ( 'statemachine' name '{' ( stateMachineBlock )* '}' -> ^( STATE_MACHINE ^( NAME name ) ^( BLOCKS ( stateMachineBlock )* ) ) )
            // StateMachine.g:57:4: 'statemachine' name '{' ( stateMachineBlock )* '}'
            {
            string_literal5=(Token)match(input,72,FOLLOW_72_in_stateMachineDefinition215); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_72.add(string_literal5);

            pushFollow(FOLLOW_name_in_stateMachineDefinition217);
            name6=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name6.getTree());
            char_literal7=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_stateMachineDefinition219); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal7);

            // StateMachine.g:57:28: ( stateMachineBlock )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==73||LA2_0==77||LA2_0==86) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // StateMachine.g:57:28: stateMachineBlock
            	    {
            	    pushFollow(FOLLOW_stateMachineBlock_in_stateMachineDefinition221);
            	    stateMachineBlock8=stateMachineBlock();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateMachineBlock.add(stateMachineBlock8.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            char_literal9=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_stateMachineDefinition224); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal9);



            // AST REWRITE
            // elements: stateMachineBlock, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 58:2: -> ^( STATE_MACHINE ^( NAME name ) ^( BLOCKS ( stateMachineBlock )* ) )
            {
                // StateMachine.g:58:5: ^( STATE_MACHINE ^( NAME name ) ^( BLOCKS ( stateMachineBlock )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATE_MACHINE, "STATE_MACHINE"), root_1);

                // StateMachine.g:58:21: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:58:34: ^( BLOCKS ( stateMachineBlock )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                // StateMachine.g:58:43: ( stateMachineBlock )*
                while ( stream_stateMachineBlock.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateMachineBlock.nextTree());

                }
                stream_stateMachineBlock.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stateMachineDefinition"

    public static class stateMachineBlock_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateMachineBlock"
    // StateMachine.g:61:1: stateMachineBlock : ( compositeStateDefinition | concurrentStateDefinition | transitionDefinition ) ;
    public final StateMachineParser.stateMachineBlock_return stateMachineBlock() throws RecognitionException {
        StateMachineParser.stateMachineBlock_return retval = new StateMachineParser.stateMachineBlock_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.compositeStateDefinition_return compositeStateDefinition10 = null;

        StateMachineParser.concurrentStateDefinition_return concurrentStateDefinition11 = null;

        StateMachineParser.transitionDefinition_return transitionDefinition12 = null;



        try {
            // StateMachine.g:62:2: ( ( compositeStateDefinition | concurrentStateDefinition | transitionDefinition ) )
            // StateMachine.g:62:4: ( compositeStateDefinition | concurrentStateDefinition | transitionDefinition )
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:62:4: ( compositeStateDefinition | concurrentStateDefinition | transitionDefinition )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 86:
                {
                alt3=1;
                }
                break;
            case 77:
                {
                alt3=2;
                }
                break;
            case 73:
                {
                alt3=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // StateMachine.g:62:5: compositeStateDefinition
                    {
                    pushFollow(FOLLOW_compositeStateDefinition_in_stateMachineBlock256);
                    compositeStateDefinition10=compositeStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compositeStateDefinition10.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:63:5: concurrentStateDefinition
                    {
                    pushFollow(FOLLOW_concurrentStateDefinition_in_stateMachineBlock262);
                    concurrentStateDefinition11=concurrentStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, concurrentStateDefinition11.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:64:5: transitionDefinition
                    {
                    pushFollow(FOLLOW_transitionDefinition_in_stateMachineBlock268);
                    transitionDefinition12=transitionDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, transitionDefinition12.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stateMachineBlock"

    public static class transitionDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transitionDefinition"
    // StateMachine.g:67:1: transitionDefinition : 'transition' name '(' startState= name ',' endState= name ')' '{' ( transitionBlock )* '}' -> ^( TRANSITION_DEFINITION ^( NAME name ) ^( START_STATE $startState) ^( END_STATE $endState) ^( BLOCKS ( transitionBlock )* ) ) ;
    public final StateMachineParser.transitionDefinition_return transitionDefinition() throws RecognitionException {
        StateMachineParser.transitionDefinition_return retval = new StateMachineParser.transitionDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal13=null;
        Token char_literal15=null;
        Token char_literal16=null;
        Token char_literal17=null;
        Token char_literal18=null;
        Token char_literal20=null;
        StateMachineParser.name_return startState = null;

        StateMachineParser.name_return endState = null;

        StateMachineParser.name_return name14 = null;

        StateMachineParser.transitionBlock_return transitionBlock19 = null;


        Object string_literal13_tree=null;
        Object char_literal15_tree=null;
        Object char_literal16_tree=null;
        Object char_literal17_tree=null;
        Object char_literal18_tree=null;
        Object char_literal20_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_75=new RewriteRuleTokenStream(adaptor,"token 75");
        RewriteRuleTokenStream stream_73=new RewriteRuleTokenStream(adaptor,"token 73");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_transitionBlock=new RewriteRuleSubtreeStream(adaptor,"rule transitionBlock");
        try {
            // StateMachine.g:68:2: ( 'transition' name '(' startState= name ',' endState= name ')' '{' ( transitionBlock )* '}' -> ^( TRANSITION_DEFINITION ^( NAME name ) ^( START_STATE $startState) ^( END_STATE $endState) ^( BLOCKS ( transitionBlock )* ) ) )
            // StateMachine.g:68:4: 'transition' name '(' startState= name ',' endState= name ')' '{' ( transitionBlock )* '}'
            {
            string_literal13=(Token)match(input,73,FOLLOW_73_in_transitionDefinition280); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_73.add(string_literal13);

            pushFollow(FOLLOW_name_in_transitionDefinition282);
            name14=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name14.getTree());
            char_literal15=(Token)match(input,74,FOLLOW_74_in_transitionDefinition284); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_74.add(char_literal15);

            pushFollow(FOLLOW_name_in_transitionDefinition288);
            startState=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(startState.getTree());
            char_literal16=(Token)match(input,75,FOLLOW_75_in_transitionDefinition290); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_75.add(char_literal16);

            pushFollow(FOLLOW_name_in_transitionDefinition294);
            endState=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(endState.getTree());
            char_literal17=(Token)match(input,76,FOLLOW_76_in_transitionDefinition296); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_76.add(char_literal17);

            char_literal18=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_transitionDefinition298); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal18);

            // StateMachine.g:69:3: ( transitionBlock )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==96||LA4_0==98||LA4_0==113) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // StateMachine.g:69:3: transitionBlock
            	    {
            	    pushFollow(FOLLOW_transitionBlock_in_transitionDefinition302);
            	    transitionBlock19=transitionBlock();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_transitionBlock.add(transitionBlock19.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            char_literal20=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_transitionDefinition306); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal20);



            // AST REWRITE
            // elements: startState, transitionBlock, endState, name
            // token labels: 
            // rule labels: startState, endState, retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_startState=new RewriteRuleSubtreeStream(adaptor,"token startState",startState!=null?startState.tree:null);
            RewriteRuleSubtreeStream stream_endState=new RewriteRuleSubtreeStream(adaptor,"token endState",endState!=null?endState.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 71:2: -> ^( TRANSITION_DEFINITION ^( NAME name ) ^( START_STATE $startState) ^( END_STATE $endState) ^( BLOCKS ( transitionBlock )* ) )
            {
                // StateMachine.g:71:5: ^( TRANSITION_DEFINITION ^( NAME name ) ^( START_STATE $startState) ^( END_STATE $endState) ^( BLOCKS ( transitionBlock )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TRANSITION_DEFINITION, "TRANSITION_DEFINITION"), root_1);

                // StateMachine.g:71:29: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:71:42: ^( START_STATE $startState)
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(START_STATE, "START_STATE"), root_2);

                adaptor.addChild(root_2, stream_startState.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:72:3: ^( END_STATE $endState)
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(END_STATE, "END_STATE"), root_2);

                adaptor.addChild(root_2, stream_endState.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:72:26: ^( BLOCKS ( transitionBlock )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                // StateMachine.g:72:35: ( transitionBlock )*
                while ( stream_transitionBlock.hasNext() ) {
                    adaptor.addChild(root_2, stream_transitionBlock.nextTree());

                }
                stream_transitionBlock.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "transitionDefinition"

    public static class transitionBlock_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "transitionBlock"
    // StateMachine.g:75:1: transitionBlock : ( declareNT | whenNT | thenNT );
    public final StateMachineParser.transitionBlock_return transitionBlock() throws RecognitionException {
        StateMachineParser.transitionBlock_return retval = new StateMachineParser.transitionBlock_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.declareNT_return declareNT21 = null;

        StateMachineParser.whenNT_return whenNT22 = null;

        StateMachineParser.thenNT_return thenNT23 = null;



        try {
            // StateMachine.g:76:2: ( declareNT | whenNT | thenNT )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 96:
                {
                alt5=1;
                }
                break;
            case 98:
                {
                alt5=2;
                }
                break;
            case 113:
                {
                alt5=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // StateMachine.g:76:4: declareNT
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_declareNT_in_transitionBlock353);
                    declareNT21=declareNT();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, declareNT21.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:76:16: whenNT
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_whenNT_in_transitionBlock357);
                    whenNT22=whenNT();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, whenNT22.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:76:25: thenNT
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_thenNT_in_transitionBlock361);
                    thenNT23=thenNT();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenNT23.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "transitionBlock"

    public static class concurrentStateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "concurrentStateDefinition"
    // StateMachine.g:79:1: concurrentStateDefinition : 'concurrent' 'state' name '{' ( regionDefinition )* '}' -> ^( CONCURRENT_STATE_DEFINITION ^( NAME name ) ^( REGIONS regionDefinition ) ) ;
    public final StateMachineParser.concurrentStateDefinition_return concurrentStateDefinition() throws RecognitionException {
        StateMachineParser.concurrentStateDefinition_return retval = new StateMachineParser.concurrentStateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal24=null;
        Token string_literal25=null;
        Token char_literal27=null;
        Token char_literal29=null;
        StateMachineParser.name_return name26 = null;

        StateMachineParser.regionDefinition_return regionDefinition28 = null;


        Object string_literal24_tree=null;
        Object string_literal25_tree=null;
        Object char_literal27_tree=null;
        Object char_literal29_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_77=new RewriteRuleTokenStream(adaptor,"token 77");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_regionDefinition=new RewriteRuleSubtreeStream(adaptor,"rule regionDefinition");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:80:2: ( 'concurrent' 'state' name '{' ( regionDefinition )* '}' -> ^( CONCURRENT_STATE_DEFINITION ^( NAME name ) ^( REGIONS regionDefinition ) ) )
            // StateMachine.g:80:4: 'concurrent' 'state' name '{' ( regionDefinition )* '}'
            {
            string_literal24=(Token)match(input,77,FOLLOW_77_in_concurrentStateDefinition372); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_77.add(string_literal24);

            string_literal25=(Token)match(input,78,FOLLOW_78_in_concurrentStateDefinition374); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal25);

            pushFollow(FOLLOW_name_in_concurrentStateDefinition376);
            name26=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name26.getTree());
            char_literal27=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_concurrentStateDefinition378); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal27);

            // StateMachine.g:80:34: ( regionDefinition )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==79) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // StateMachine.g:80:34: regionDefinition
            	    {
            	    pushFollow(FOLLOW_regionDefinition_in_concurrentStateDefinition380);
            	    regionDefinition28=regionDefinition();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_regionDefinition.add(regionDefinition28.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            char_literal29=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_concurrentStateDefinition383); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal29);



            // AST REWRITE
            // elements: regionDefinition, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 81:2: -> ^( CONCURRENT_STATE_DEFINITION ^( NAME name ) ^( REGIONS regionDefinition ) )
            {
                // StateMachine.g:81:5: ^( CONCURRENT_STATE_DEFINITION ^( NAME name ) ^( REGIONS regionDefinition ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONCURRENT_STATE_DEFINITION, "CONCURRENT_STATE_DEFINITION"), root_1);

                // StateMachine.g:81:35: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:81:48: ^( REGIONS regionDefinition )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(REGIONS, "REGIONS"), root_2);

                adaptor.addChild(root_2, stream_regionDefinition.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "concurrentStateDefinition"

    public static class regionDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "regionDefinition"
    // StateMachine.g:84:1: regionDefinition : 'region' 'state' name '{' ( stateDefinition | stateRule )* '}' -> ^( REGION_DEFINITION ^( NAME name ) ^( DEFINITIONS ( stateDefinition )* ) ^( RULES ( stateRule )* ) ) ;
    public final StateMachineParser.regionDefinition_return regionDefinition() throws RecognitionException {
        StateMachineParser.regionDefinition_return retval = new StateMachineParser.regionDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal30=null;
        Token string_literal31=null;
        Token char_literal33=null;
        Token char_literal36=null;
        StateMachineParser.name_return name32 = null;

        StateMachineParser.stateDefinition_return stateDefinition34 = null;

        StateMachineParser.stateRule_return stateRule35 = null;


        Object string_literal30_tree=null;
        Object string_literal31_tree=null;
        Object char_literal33_tree=null;
        Object char_literal36_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_79=new RewriteRuleTokenStream(adaptor,"token 79");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_stateDefinition=new RewriteRuleSubtreeStream(adaptor,"rule stateDefinition");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_stateRule=new RewriteRuleSubtreeStream(adaptor,"rule stateRule");
        try {
            // StateMachine.g:85:2: ( 'region' 'state' name '{' ( stateDefinition | stateRule )* '}' -> ^( REGION_DEFINITION ^( NAME name ) ^( DEFINITIONS ( stateDefinition )* ) ^( RULES ( stateRule )* ) ) )
            // StateMachine.g:85:4: 'region' 'state' name '{' ( stateDefinition | stateRule )* '}'
            {
            string_literal30=(Token)match(input,79,FOLLOW_79_in_regionDefinition413); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_79.add(string_literal30);

            string_literal31=(Token)match(input,78,FOLLOW_78_in_regionDefinition415); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal31);

            pushFollow(FOLLOW_name_in_regionDefinition417);
            name32=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name32.getTree());
            char_literal33=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_regionDefinition419); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal33);

            // StateMachine.g:85:30: ( stateDefinition | stateRule )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=80 && LA7_0<=82)) ) {
                    alt7=1;
                }
                else if ( ((LA7_0>=83 && LA7_0<=85)) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // StateMachine.g:85:31: stateDefinition
            	    {
            	    pushFollow(FOLLOW_stateDefinition_in_regionDefinition422);
            	    stateDefinition34=stateDefinition();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateDefinition.add(stateDefinition34.getTree());

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:85:49: stateRule
            	    {
            	    pushFollow(FOLLOW_stateRule_in_regionDefinition426);
            	    stateRule35=stateRule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateRule.add(stateRule35.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            char_literal36=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_regionDefinition430); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal36);



            // AST REWRITE
            // elements: stateDefinition, name, stateRule
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 86:2: -> ^( REGION_DEFINITION ^( NAME name ) ^( DEFINITIONS ( stateDefinition )* ) ^( RULES ( stateRule )* ) )
            {
                // StateMachine.g:86:5: ^( REGION_DEFINITION ^( NAME name ) ^( DEFINITIONS ( stateDefinition )* ) ^( RULES ( stateRule )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(REGION_DEFINITION, "REGION_DEFINITION"), root_1);

                // StateMachine.g:86:25: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:86:38: ^( DEFINITIONS ( stateDefinition )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINITIONS, "DEFINITIONS"), root_2);

                // StateMachine.g:86:52: ( stateDefinition )*
                while ( stream_stateDefinition.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateDefinition.nextTree());

                }
                stream_stateDefinition.reset();

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:87:3: ^( RULES ( stateRule )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULES, "RULES"), root_2);

                // StateMachine.g:87:11: ( stateRule )*
                while ( stream_stateRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateRule.nextTree());

                }
                stream_stateRule.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "regionDefinition"

    public static class stateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateDefinition"
    // StateMachine.g:90:1: stateDefinition : ( simpleStateDefinition | pseudoStartStateDefinition | pseudoEndStateDefinition );
    public final StateMachineParser.stateDefinition_return stateDefinition() throws RecognitionException {
        StateMachineParser.stateDefinition_return retval = new StateMachineParser.stateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.simpleStateDefinition_return simpleStateDefinition37 = null;

        StateMachineParser.pseudoStartStateDefinition_return pseudoStartStateDefinition38 = null;

        StateMachineParser.pseudoEndStateDefinition_return pseudoEndStateDefinition39 = null;



        try {
            // StateMachine.g:91:2: ( simpleStateDefinition | pseudoStartStateDefinition | pseudoEndStateDefinition )
            int alt8=3;
            switch ( input.LA(1) ) {
            case 82:
                {
                alt8=1;
                }
                break;
            case 81:
                {
                alt8=2;
                }
                break;
            case 80:
                {
                alt8=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // StateMachine.g:91:4: simpleStateDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_simpleStateDefinition_in_stateDefinition471);
                    simpleStateDefinition37=simpleStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleStateDefinition37.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:91:28: pseudoStartStateDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pseudoStartStateDefinition_in_stateDefinition475);
                    pseudoStartStateDefinition38=pseudoStartStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pseudoStartStateDefinition38.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:91:57: pseudoEndStateDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pseudoEndStateDefinition_in_stateDefinition479);
                    pseudoEndStateDefinition39=pseudoEndStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pseudoEndStateDefinition39.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stateDefinition"

    public static class pseudoEndStateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pseudoEndStateDefinition"
    // StateMachine.g:94:1: pseudoEndStateDefinition : 'pseudo-end' 'state' name '{' ( stateRule )* '}' -> ^( PSEUDO_END_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) ;
    public final StateMachineParser.pseudoEndStateDefinition_return pseudoEndStateDefinition() throws RecognitionException {
        StateMachineParser.pseudoEndStateDefinition_return retval = new StateMachineParser.pseudoEndStateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal40=null;
        Token string_literal41=null;
        Token char_literal43=null;
        Token char_literal45=null;
        StateMachineParser.name_return name42 = null;

        StateMachineParser.stateRule_return stateRule44 = null;


        Object string_literal40_tree=null;
        Object string_literal41_tree=null;
        Object char_literal43_tree=null;
        Object char_literal45_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleTokenStream stream_80=new RewriteRuleTokenStream(adaptor,"token 80");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_stateRule=new RewriteRuleSubtreeStream(adaptor,"rule stateRule");
        try {
            // StateMachine.g:95:2: ( 'pseudo-end' 'state' name '{' ( stateRule )* '}' -> ^( PSEUDO_END_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) )
            // StateMachine.g:95:4: 'pseudo-end' 'state' name '{' ( stateRule )* '}'
            {
            string_literal40=(Token)match(input,80,FOLLOW_80_in_pseudoEndStateDefinition490); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_80.add(string_literal40);

            string_literal41=(Token)match(input,78,FOLLOW_78_in_pseudoEndStateDefinition492); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal41);

            pushFollow(FOLLOW_name_in_pseudoEndStateDefinition494);
            name42=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name42.getTree());
            char_literal43=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_pseudoEndStateDefinition496); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal43);

            // StateMachine.g:95:34: ( stateRule )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>=83 && LA9_0<=85)) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // StateMachine.g:95:34: stateRule
            	    {
            	    pushFollow(FOLLOW_stateRule_in_pseudoEndStateDefinition498);
            	    stateRule44=stateRule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateRule.add(stateRule44.getTree());

            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            char_literal45=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_pseudoEndStateDefinition501); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal45);



            // AST REWRITE
            // elements: stateRule, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 96:2: -> ^( PSEUDO_END_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
            {
                // StateMachine.g:96:5: ^( PSEUDO_END_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PSEUDO_END_STATE_DEF, "PSEUDO_END_STATE_DEF"), root_1);

                // StateMachine.g:96:28: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:96:41: ^( RULES ( stateRule )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULES, "RULES"), root_2);

                // StateMachine.g:96:49: ( stateRule )*
                while ( stream_stateRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateRule.nextTree());

                }
                stream_stateRule.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pseudoEndStateDefinition"

    public static class pseudoStartStateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pseudoStartStateDefinition"
    // StateMachine.g:99:1: pseudoStartStateDefinition : 'pseudo-start' 'state' name '{' ( stateRule )* '}' -> ^( PSEUDO_START_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) ;
    public final StateMachineParser.pseudoStartStateDefinition_return pseudoStartStateDefinition() throws RecognitionException {
        StateMachineParser.pseudoStartStateDefinition_return retval = new StateMachineParser.pseudoStartStateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal46=null;
        Token string_literal47=null;
        Token char_literal49=null;
        Token char_literal51=null;
        StateMachineParser.name_return name48 = null;

        StateMachineParser.stateRule_return stateRule50 = null;


        Object string_literal46_tree=null;
        Object string_literal47_tree=null;
        Object char_literal49_tree=null;
        Object char_literal51_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_81=new RewriteRuleTokenStream(adaptor,"token 81");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_stateRule=new RewriteRuleSubtreeStream(adaptor,"rule stateRule");
        try {
            // StateMachine.g:100:2: ( 'pseudo-start' 'state' name '{' ( stateRule )* '}' -> ^( PSEUDO_START_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) )
            // StateMachine.g:100:4: 'pseudo-start' 'state' name '{' ( stateRule )* '}'
            {
            string_literal46=(Token)match(input,81,FOLLOW_81_in_pseudoStartStateDefinition532); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_81.add(string_literal46);

            string_literal47=(Token)match(input,78,FOLLOW_78_in_pseudoStartStateDefinition534); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal47);

            pushFollow(FOLLOW_name_in_pseudoStartStateDefinition536);
            name48=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name48.getTree());
            char_literal49=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_pseudoStartStateDefinition538); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal49);

            // StateMachine.g:100:36: ( stateRule )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>=83 && LA10_0<=85)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // StateMachine.g:100:36: stateRule
            	    {
            	    pushFollow(FOLLOW_stateRule_in_pseudoStartStateDefinition540);
            	    stateRule50=stateRule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateRule.add(stateRule50.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            char_literal51=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_pseudoStartStateDefinition543); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal51);



            // AST REWRITE
            // elements: stateRule, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 101:2: -> ^( PSEUDO_START_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
            {
                // StateMachine.g:101:5: ^( PSEUDO_START_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PSEUDO_START_STATE_DEF, "PSEUDO_START_STATE_DEF"), root_1);

                // StateMachine.g:101:30: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:101:43: ^( RULES ( stateRule )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULES, "RULES"), root_2);

                // StateMachine.g:101:51: ( stateRule )*
                while ( stream_stateRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateRule.nextTree());

                }
                stream_stateRule.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pseudoStartStateDefinition"

    public static class simpleStateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simpleStateDefinition"
    // StateMachine.g:104:1: simpleStateDefinition : 'simple' 'state' name '{' ( stateRule )* '}' -> ^( SIMPLE_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) ;
    public final StateMachineParser.simpleStateDefinition_return simpleStateDefinition() throws RecognitionException {
        StateMachineParser.simpleStateDefinition_return retval = new StateMachineParser.simpleStateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal52=null;
        Token string_literal53=null;
        Token char_literal55=null;
        Token char_literal57=null;
        StateMachineParser.name_return name54 = null;

        StateMachineParser.stateRule_return stateRule56 = null;


        Object string_literal52_tree=null;
        Object string_literal53_tree=null;
        Object char_literal55_tree=null;
        Object char_literal57_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_82=new RewriteRuleTokenStream(adaptor,"token 82");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_stateRule=new RewriteRuleSubtreeStream(adaptor,"rule stateRule");
        try {
            // StateMachine.g:105:2: ( 'simple' 'state' name '{' ( stateRule )* '}' -> ^( SIMPLE_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) ) )
            // StateMachine.g:105:4: 'simple' 'state' name '{' ( stateRule )* '}'
            {
            string_literal52=(Token)match(input,82,FOLLOW_82_in_simpleStateDefinition574); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_82.add(string_literal52);

            string_literal53=(Token)match(input,78,FOLLOW_78_in_simpleStateDefinition576); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal53);

            pushFollow(FOLLOW_name_in_simpleStateDefinition578);
            name54=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name54.getTree());
            char_literal55=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_simpleStateDefinition580); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal55);

            // StateMachine.g:105:30: ( stateRule )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=83 && LA11_0<=85)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // StateMachine.g:105:30: stateRule
            	    {
            	    pushFollow(FOLLOW_stateRule_in_simpleStateDefinition582);
            	    stateRule56=stateRule();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_stateRule.add(stateRule56.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            char_literal57=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_simpleStateDefinition585); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal57);



            // AST REWRITE
            // elements: stateRule, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 106:2: -> ^( SIMPLE_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
            {
                // StateMachine.g:106:5: ^( SIMPLE_STATE_DEF ^( NAME name ) ^( RULES ( stateRule )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SIMPLE_STATE_DEF, "SIMPLE_STATE_DEF"), root_1);

                // StateMachine.g:106:24: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:106:37: ^( RULES ( stateRule )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULES, "RULES"), root_2);

                // StateMachine.g:106:45: ( stateRule )*
                while ( stream_stateRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateRule.nextTree());

                }
                stream_stateRule.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "simpleStateDefinition"

    public static class stateRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateRule"
    // StateMachine.g:109:1: stateRule : ( onEntryRule | onExitRule | onTimeOutRule );
    public final StateMachineParser.stateRule_return stateRule() throws RecognitionException {
        StateMachineParser.stateRule_return retval = new StateMachineParser.stateRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.onEntryRule_return onEntryRule58 = null;

        StateMachineParser.onExitRule_return onExitRule59 = null;

        StateMachineParser.onTimeOutRule_return onTimeOutRule60 = null;



        try {
            // StateMachine.g:110:2: ( onEntryRule | onExitRule | onTimeOutRule )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 85:
                {
                alt12=1;
                }
                break;
            case 84:
                {
                alt12=2;
                }
                break;
            case 83:
                {
                alt12=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // StateMachine.g:110:4: onEntryRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_onEntryRule_in_stateRule616);
                    onEntryRule58=onEntryRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, onEntryRule58.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:110:18: onExitRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_onExitRule_in_stateRule620);
                    onExitRule59=onExitRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, onExitRule59.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:110:31: onTimeOutRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_onTimeOutRule_in_stateRule624);
                    onTimeOutRule60=onTimeOutRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, onTimeOutRule60.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stateRule"

    public static class onTimeOutRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "onTimeOutRule"
    // StateMachine.g:113:1: onTimeOutRule : 'onTimeOut' '(' ')' '{' '}' -> ^( TIMEOUT_RULE ) ;
    public final StateMachineParser.onTimeOutRule_return onTimeOutRule() throws RecognitionException {
        StateMachineParser.onTimeOutRule_return retval = new StateMachineParser.onTimeOutRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal61=null;
        Token char_literal62=null;
        Token char_literal63=null;
        Token char_literal64=null;
        Token char_literal65=null;

        Object string_literal61_tree=null;
        Object char_literal62_tree=null;
        Object char_literal63_tree=null;
        Object char_literal64_tree=null;
        Object char_literal65_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_83=new RewriteRuleTokenStream(adaptor,"token 83");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");

        try {
            // StateMachine.g:114:2: ( 'onTimeOut' '(' ')' '{' '}' -> ^( TIMEOUT_RULE ) )
            // StateMachine.g:114:4: 'onTimeOut' '(' ')' '{' '}'
            {
            string_literal61=(Token)match(input,83,FOLLOW_83_in_onTimeOutRule635); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_83.add(string_literal61);

            char_literal62=(Token)match(input,74,FOLLOW_74_in_onTimeOutRule637); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_74.add(char_literal62);

            char_literal63=(Token)match(input,76,FOLLOW_76_in_onTimeOutRule639); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_76.add(char_literal63);

            char_literal64=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_onTimeOutRule641); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal64);

            char_literal65=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_onTimeOutRule643); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal65);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 115:2: -> ^( TIMEOUT_RULE )
            {
                // StateMachine.g:115:5: ^( TIMEOUT_RULE )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TIMEOUT_RULE, "TIMEOUT_RULE"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "onTimeOutRule"

    public static class onExitRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "onExitRule"
    // StateMachine.g:118:1: onExitRule : 'onExit' '(' ')' '{' '}' -> ^( EXIT_RULE ) ;
    public final StateMachineParser.onExitRule_return onExitRule() throws RecognitionException {
        StateMachineParser.onExitRule_return retval = new StateMachineParser.onExitRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal66=null;
        Token char_literal67=null;
        Token char_literal68=null;
        Token char_literal69=null;
        Token char_literal70=null;

        Object string_literal66_tree=null;
        Object char_literal67_tree=null;
        Object char_literal68_tree=null;
        Object char_literal69_tree=null;
        Object char_literal70_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_84=new RewriteRuleTokenStream(adaptor,"token 84");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");

        try {
            // StateMachine.g:119:2: ( 'onExit' '(' ')' '{' '}' -> ^( EXIT_RULE ) )
            // StateMachine.g:119:4: 'onExit' '(' ')' '{' '}'
            {
            string_literal66=(Token)match(input,84,FOLLOW_84_in_onExitRule661); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_84.add(string_literal66);

            char_literal67=(Token)match(input,74,FOLLOW_74_in_onExitRule663); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_74.add(char_literal67);

            char_literal68=(Token)match(input,76,FOLLOW_76_in_onExitRule665); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_76.add(char_literal68);

            char_literal69=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_onExitRule667); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal69);

            char_literal70=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_onExitRule669); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal70);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 120:2: -> ^( EXIT_RULE )
            {
                // StateMachine.g:120:5: ^( EXIT_RULE )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(EXIT_RULE, "EXIT_RULE"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "onExitRule"

    public static class onEntryRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "onEntryRule"
    // StateMachine.g:123:1: onEntryRule : 'onEntry' '(' ')' '{' '}' -> ^( ENTRY_RULE ) ;
    public final StateMachineParser.onEntryRule_return onEntryRule() throws RecognitionException {
        StateMachineParser.onEntryRule_return retval = new StateMachineParser.onEntryRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal71=null;
        Token char_literal72=null;
        Token char_literal73=null;
        Token char_literal74=null;
        Token char_literal75=null;

        Object string_literal71_tree=null;
        Object char_literal72_tree=null;
        Object char_literal73_tree=null;
        Object char_literal74_tree=null;
        Object char_literal75_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_85=new RewriteRuleTokenStream(adaptor,"token 85");
        RewriteRuleTokenStream stream_76=new RewriteRuleTokenStream(adaptor,"token 76");
        RewriteRuleTokenStream stream_74=new RewriteRuleTokenStream(adaptor,"token 74");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");

        try {
            // StateMachine.g:124:2: ( 'onEntry' '(' ')' '{' '}' -> ^( ENTRY_RULE ) )
            // StateMachine.g:124:4: 'onEntry' '(' ')' '{' '}'
            {
            string_literal71=(Token)match(input,85,FOLLOW_85_in_onEntryRule687); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_85.add(string_literal71);

            char_literal72=(Token)match(input,74,FOLLOW_74_in_onEntryRule689); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_74.add(char_literal72);

            char_literal73=(Token)match(input,76,FOLLOW_76_in_onEntryRule691); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_76.add(char_literal73);

            char_literal74=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_onEntryRule693); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal74);

            char_literal75=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_onEntryRule695); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal75);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 125:2: -> ^( ENTRY_RULE )
            {
                // StateMachine.g:125:5: ^( ENTRY_RULE )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ENTRY_RULE, "ENTRY_RULE"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "onEntryRule"

    public static class compositeStateDefinition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compositeStateDefinition"
    // StateMachine.g:129:1: compositeStateDefinition : 'composite' 'state' name '{' ( compositeStateBlock )* '}' -> ^( COMPOSITE_STATE_DEFINITION ^( NAME name ) ^( DEFINITIONS ( compositeStateBlock )* ) ) ;
    public final StateMachineParser.compositeStateDefinition_return compositeStateDefinition() throws RecognitionException {
        StateMachineParser.compositeStateDefinition_return retval = new StateMachineParser.compositeStateDefinition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal76=null;
        Token string_literal77=null;
        Token char_literal79=null;
        Token char_literal81=null;
        StateMachineParser.name_return name78 = null;

        StateMachineParser.compositeStateBlock_return compositeStateBlock80 = null;


        Object string_literal76_tree=null;
        Object string_literal77_tree=null;
        Object char_literal79_tree=null;
        Object char_literal81_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_86=new RewriteRuleTokenStream(adaptor,"token 86");
        RewriteRuleTokenStream stream_78=new RewriteRuleTokenStream(adaptor,"token 78");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_compositeStateBlock=new RewriteRuleSubtreeStream(adaptor,"rule compositeStateBlock");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:130:2: ( 'composite' 'state' name '{' ( compositeStateBlock )* '}' -> ^( COMPOSITE_STATE_DEFINITION ^( NAME name ) ^( DEFINITIONS ( compositeStateBlock )* ) ) )
            // StateMachine.g:130:4: 'composite' 'state' name '{' ( compositeStateBlock )* '}'
            {
            string_literal76=(Token)match(input,86,FOLLOW_86_in_compositeStateDefinition714); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_86.add(string_literal76);

            string_literal77=(Token)match(input,78,FOLLOW_78_in_compositeStateDefinition716); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_78.add(string_literal77);

            pushFollow(FOLLOW_name_in_compositeStateDefinition718);
            name78=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name78.getTree());
            char_literal79=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_compositeStateDefinition720); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal79);

            // StateMachine.g:130:33: ( compositeStateBlock )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==77||LA13_0==86) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // StateMachine.g:130:33: compositeStateBlock
            	    {
            	    pushFollow(FOLLOW_compositeStateBlock_in_compositeStateDefinition722);
            	    compositeStateBlock80=compositeStateBlock();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_compositeStateBlock.add(compositeStateBlock80.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            char_literal81=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_compositeStateDefinition725); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal81);



            // AST REWRITE
            // elements: compositeStateBlock, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 131:2: -> ^( COMPOSITE_STATE_DEFINITION ^( NAME name ) ^( DEFINITIONS ( compositeStateBlock )* ) )
            {
                // StateMachine.g:131:5: ^( COMPOSITE_STATE_DEFINITION ^( NAME name ) ^( DEFINITIONS ( compositeStateBlock )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(COMPOSITE_STATE_DEFINITION, "COMPOSITE_STATE_DEFINITION"), root_1);

                // StateMachine.g:131:34: ^( NAME name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:131:47: ^( DEFINITIONS ( compositeStateBlock )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(DEFINITIONS, "DEFINITIONS"), root_2);

                // StateMachine.g:131:61: ( compositeStateBlock )*
                while ( stream_compositeStateBlock.hasNext() ) {
                    adaptor.addChild(root_2, stream_compositeStateBlock.nextTree());

                }
                stream_compositeStateBlock.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compositeStateDefinition"

    public static class compositeStateBlock_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compositeStateBlock"
    // StateMachine.g:134:1: compositeStateBlock : ( concurrentStateDefinition | compositeStateDefinition );
    public final StateMachineParser.compositeStateBlock_return compositeStateBlock() throws RecognitionException {
        StateMachineParser.compositeStateBlock_return retval = new StateMachineParser.compositeStateBlock_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.concurrentStateDefinition_return concurrentStateDefinition82 = null;

        StateMachineParser.compositeStateDefinition_return compositeStateDefinition83 = null;



        try {
            // StateMachine.g:135:2: ( concurrentStateDefinition | compositeStateDefinition )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==77) ) {
                alt14=1;
            }
            else if ( (LA14_0==86) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // StateMachine.g:135:4: concurrentStateDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_concurrentStateDefinition_in_compositeStateBlock756);
                    concurrentStateDefinition82=concurrentStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, concurrentStateDefinition82.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:135:32: compositeStateDefinition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_compositeStateDefinition_in_compositeStateBlock760);
                    compositeStateDefinition83=compositeStateDefinition();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compositeStateDefinition83.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compositeStateBlock"

    public static class identifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "identifier"
    // StateMachine.g:138:1: identifier : Identifier ;
    public final StateMachineParser.identifier_return identifier() throws RecognitionException {
        StateMachineParser.identifier_return retval = new StateMachineParser.identifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token Identifier84=null;

        Object Identifier84_tree=null;

        try {
            // StateMachine.g:139:2: ( Identifier )
            // StateMachine.g:139:4: Identifier
            {
            root_0 = (Object)adaptor.nil();

            Identifier84=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier772); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Identifier84_tree = (Object)adaptor.create(Identifier84);
            adaptor.addChild(root_0, Identifier84_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "identifier"

    public static class xsltLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "xsltLiteral"
    // StateMachine.g:142:1: xsltLiteral : '\"\"xslt://' '\"' '\"' ;
    public final StateMachineParser.xsltLiteral_return xsltLiteral() throws RecognitionException {
        StateMachineParser.xsltLiteral_return retval = new StateMachineParser.xsltLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal85=null;
        Token char_literal86=null;
        Token char_literal87=null;

        Object string_literal85_tree=null;
        Object char_literal86_tree=null;
        Object char_literal87_tree=null;

        try {
            // StateMachine.g:143:2: ( '\"\"xslt://' '\"' '\"' )
            // StateMachine.g:143:4: '\"\"xslt://' '\"' '\"'
            {
            root_0 = (Object)adaptor.nil();

            string_literal85=(Token)match(input,87,FOLLOW_87_in_xsltLiteral784); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal85_tree = (Object)adaptor.create(string_literal85);
            adaptor.addChild(root_0, string_literal85_tree);
            }
            char_literal86=(Token)match(input,88,FOLLOW_88_in_xsltLiteral786); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal86_tree = (Object)adaptor.create(char_literal86);
            adaptor.addChild(root_0, char_literal86_tree);
            }
            char_literal87=(Token)match(input,88,FOLLOW_88_in_xsltLiteral788); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal87_tree = (Object)adaptor.create(char_literal87);
            adaptor.addChild(root_0, char_literal87_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "xsltLiteral"

    public static class ruleDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleDeclaration"
    // StateMachine.g:146:1: ruleDeclaration : 'rule' name '{' ruleNT '}' -> ^( RULE_DECL[\"rule\"] name ^( BLOCKS ruleNT ) ) ;
    public final StateMachineParser.ruleDeclaration_return ruleDeclaration() throws RecognitionException {
        StateMachineParser.ruleDeclaration_return retval = new StateMachineParser.ruleDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal88=null;
        Token char_literal90=null;
        Token char_literal92=null;
        StateMachineParser.name_return name89 = null;

        StateMachineParser.ruleNT_return ruleNT91 = null;


        Object string_literal88_tree=null;
        Object char_literal90_tree=null;
        Object char_literal92_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleTokenStream stream_89=new RewriteRuleTokenStream(adaptor,"token 89");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_ruleNT=new RewriteRuleSubtreeStream(adaptor,"rule ruleNT");
        try {
            // StateMachine.g:147:2: ( 'rule' name '{' ruleNT '}' -> ^( RULE_DECL[\"rule\"] name ^( BLOCKS ruleNT ) ) )
            // StateMachine.g:147:4: 'rule' name '{' ruleNT '}'
            {
            string_literal88=(Token)match(input,89,FOLLOW_89_in_ruleDeclaration800); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_89.add(string_literal88);

            pushFollow(FOLLOW_name_in_ruleDeclaration802);
            name89=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name89.getTree());
            char_literal90=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleDeclaration804); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal90);

            pushFollow(FOLLOW_ruleNT_in_ruleDeclaration806);
            ruleNT91=ruleNT();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ruleNT.add(ruleNT91.getTree());
            char_literal92=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleDeclaration808); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal92);



            // AST REWRITE
            // elements: name, ruleNT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 148:2: -> ^( RULE_DECL[\"rule\"] name ^( BLOCKS ruleNT ) )
            {
                // StateMachine.g:148:5: ^( RULE_DECL[\"rule\"] name ^( BLOCKS ruleNT ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULE_DECL, "rule"), root_1);

                adaptor.addChild(root_1, stream_name.nextTree());
                // StateMachine.g:148:30: ^( BLOCKS ruleNT )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                adaptor.addChild(root_2, stream_ruleNT.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleDeclaration"

    public static class ruleFunctionDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleFunctionDeclaration"
    // StateMachine.g:151:1: ruleFunctionDeclaration : ( 'virtual' )? 'rulefunction' name '{' ruleFunctionNT '}' -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ( ^( VIRTUAL 'virtual' ) )? name ^( BLOCKS ruleFunctionNT ) ) ;
    public final StateMachineParser.ruleFunctionDeclaration_return ruleFunctionDeclaration() throws RecognitionException {
        StateMachineParser.ruleFunctionDeclaration_return retval = new StateMachineParser.ruleFunctionDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal93=null;
        Token string_literal94=null;
        Token char_literal96=null;
        Token char_literal98=null;
        StateMachineParser.name_return name95 = null;

        StateMachineParser.ruleFunctionNT_return ruleFunctionNT97 = null;


        Object string_literal93_tree=null;
        Object string_literal94_tree=null;
        Object char_literal96_tree=null;
        Object char_literal98_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_91=new RewriteRuleTokenStream(adaptor,"token 91");
        RewriteRuleTokenStream stream_90=new RewriteRuleTokenStream(adaptor,"token 90");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_ruleFunctionNT=new RewriteRuleSubtreeStream(adaptor,"rule ruleFunctionNT");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:152:2: ( ( 'virtual' )? 'rulefunction' name '{' ruleFunctionNT '}' -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ( ^( VIRTUAL 'virtual' ) )? name ^( BLOCKS ruleFunctionNT ) ) )
            // StateMachine.g:152:4: ( 'virtual' )? 'rulefunction' name '{' ruleFunctionNT '}'
            {
            // StateMachine.g:152:4: ( 'virtual' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==90) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // StateMachine.g:152:5: 'virtual'
                    {
                    string_literal93=(Token)match(input,90,FOLLOW_90_in_ruleFunctionDeclaration836); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_90.add(string_literal93);

                    if ( state.backtracking==0 ) {
                       virtual=true;
                    }

                    }
                    break;

            }

            string_literal94=(Token)match(input,91,FOLLOW_91_in_ruleFunctionDeclaration842); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_91.add(string_literal94);

            pushFollow(FOLLOW_name_in_ruleFunctionDeclaration844);
            name95=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name95.getTree());
            char_literal96=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleFunctionDeclaration846); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal96);

            pushFollow(FOLLOW_ruleFunctionNT_in_ruleFunctionDeclaration848);
            ruleFunctionNT97=ruleFunctionNT();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ruleFunctionNT.add(ruleFunctionNT97.getTree());
            char_literal98=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleFunctionDeclaration850); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal98);



            // AST REWRITE
            // elements: 90, ruleFunctionNT, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 153:2: -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ( ^( VIRTUAL 'virtual' ) )? name ^( BLOCKS ruleFunctionNT ) )
            {
                // StateMachine.g:153:5: ^( RULE_FUNC_DECL[\"ruleFunction\"] ( ^( VIRTUAL 'virtual' ) )? name ^( BLOCKS ruleFunctionNT ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(RULE_FUNC_DECL, "ruleFunction"), root_1);

                // StateMachine.g:153:38: ( ^( VIRTUAL 'virtual' ) )?
                if ( stream_90.hasNext() ) {
                    // StateMachine.g:153:38: ^( VIRTUAL 'virtual' )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(VIRTUAL, "VIRTUAL"), root_2);

                    adaptor.addChild(root_2, stream_90.nextNode());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_90.reset();
                adaptor.addChild(root_1, stream_name.nextTree());
                // StateMachine.g:153:65: ^( BLOCKS ruleFunctionNT )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                adaptor.addChild(root_2, stream_ruleFunctionNT.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleFunctionDeclaration"

    public static class ruleNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleNT"
    // StateMachine.g:155:1: ruleNT : ( attributeNT | declareNT | whenNT | thenNT )* ;
    public final StateMachineParser.ruleNT_return ruleNT() throws RecognitionException {
        StateMachineParser.ruleNT_return retval = new StateMachineParser.ruleNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.attributeNT_return attributeNT99 = null;

        StateMachineParser.declareNT_return declareNT100 = null;

        StateMachineParser.whenNT_return whenNT101 = null;

        StateMachineParser.thenNT_return thenNT102 = null;



        try {
            // StateMachine.g:163:8: ( ( attributeNT | declareNT | whenNT | thenNT )* )
            // StateMachine.g:164:2: ( attributeNT | declareNT | whenNT | thenNT )*
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:164:2: ( attributeNT | declareNT | whenNT | thenNT )*
            loop16:
            do {
                int alt16=5;
                switch ( input.LA(1) ) {
                case 92:
                    {
                    alt16=1;
                    }
                    break;
                case 96:
                    {
                    alt16=2;
                    }
                    break;
                case 98:
                    {
                    alt16=3;
                    }
                    break;
                case 113:
                    {
                    alt16=4;
                    }
                    break;

                }

                switch (alt16) {
            	case 1 :
            	    // StateMachine.g:164:3: attributeNT
            	    {
            	    pushFollow(FOLLOW_attributeNT_in_ruleNT889);
            	    attributeNT99=attributeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attributeNT99.getTree());

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:165:4: declareNT
            	    {
            	    pushFollow(FOLLOW_declareNT_in_ruleNT894);
            	    declareNT100=declareNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, declareNT100.getTree());

            	    }
            	    break;
            	case 3 :
            	    // StateMachine.g:166:4: whenNT
            	    {
            	    pushFollow(FOLLOW_whenNT_in_ruleNT899);
            	    whenNT101=whenNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, whenNT101.getTree());

            	    }
            	    break;
            	case 4 :
            	    // StateMachine.g:167:4: thenNT
            	    {
            	    pushFollow(FOLLOW_thenNT_in_ruleNT904);
            	    thenNT102=thenNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenNT102.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleNT"

    public static class ruleFunctionNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleFunctionNT"
    // StateMachine.g:170:1: ruleFunctionNT : ( attributeNT | scopeNT | bodyNT )* ;
    public final StateMachineParser.ruleFunctionNT_return ruleFunctionNT() throws RecognitionException {
        StateMachineParser.ruleFunctionNT_return retval = new StateMachineParser.ruleFunctionNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.attributeNT_return attributeNT103 = null;

        StateMachineParser.scopeNT_return scopeNT104 = null;

        StateMachineParser.bodyNT_return bodyNT105 = null;



        try {
            // StateMachine.g:170:16: ( ( attributeNT | scopeNT | bodyNT )* )
            // StateMachine.g:171:2: ( attributeNT | scopeNT | bodyNT )*
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:171:2: ( attributeNT | scopeNT | bodyNT )*
            loop17:
            do {
                int alt17=4;
                switch ( input.LA(1) ) {
                case 92:
                    {
                    alt17=1;
                    }
                    break;
                case 97:
                    {
                    alt17=2;
                    }
                    break;
                case 114:
                    {
                    alt17=3;
                    }
                    break;

                }

                switch (alt17) {
            	case 1 :
            	    // StateMachine.g:171:3: attributeNT
            	    {
            	    pushFollow(FOLLOW_attributeNT_in_ruleFunctionNT920);
            	    attributeNT103=attributeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attributeNT103.getTree());

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:172:4: scopeNT
            	    {
            	    pushFollow(FOLLOW_scopeNT_in_ruleFunctionNT925);
            	    scopeNT104=scopeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, scopeNT104.getTree());

            	    }
            	    break;
            	case 3 :
            	    // StateMachine.g:173:4: bodyNT
            	    {
            	    pushFollow(FOLLOW_bodyNT_in_ruleFunctionNT930);
            	    bodyNT105=bodyNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, bodyNT105.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ruleFunctionNT"

    public static class attributeNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeNT"
    // StateMachine.g:176:1: attributeNT : 'attribute' '{' ( attributeBodyDeclaration )* '}' -> ^( ATTRIBUTE_BLOCK[\"attribute\"] ( attributeBodyDeclaration )* ) ;
    public final StateMachineParser.attributeNT_return attributeNT() throws RecognitionException {
        StateMachineParser.attributeNT_return retval = new StateMachineParser.attributeNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal106=null;
        Token char_literal107=null;
        Token char_literal109=null;
        StateMachineParser.attributeBodyDeclaration_return attributeBodyDeclaration108 = null;


        Object string_literal106_tree=null;
        Object char_literal107_tree=null;
        Object char_literal109_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_92=new RewriteRuleTokenStream(adaptor,"token 92");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_attributeBodyDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule attributeBodyDeclaration");
        try {
            // StateMachine.g:177:2: ( 'attribute' '{' ( attributeBodyDeclaration )* '}' -> ^( ATTRIBUTE_BLOCK[\"attribute\"] ( attributeBodyDeclaration )* ) )
            // StateMachine.g:177:4: 'attribute' '{' ( attributeBodyDeclaration )* '}'
            {
            string_literal106=(Token)match(input,92,FOLLOW_92_in_attributeNT943); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_92.add(string_literal106);

            char_literal107=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributeNT945); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(char_literal107);

            // StateMachine.g:177:20: ( attributeBodyDeclaration )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=93 && LA18_0<=95)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // StateMachine.g:177:20: attributeBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_attributeBodyDeclaration_in_attributeNT947);
            	    attributeBodyDeclaration108=attributeBodyDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_attributeBodyDeclaration.add(attributeBodyDeclaration108.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            char_literal109=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_attributeNT950); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal109);



            // AST REWRITE
            // elements: attributeBodyDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 178:2: -> ^( ATTRIBUTE_BLOCK[\"attribute\"] ( attributeBodyDeclaration )* )
            {
                // StateMachine.g:178:5: ^( ATTRIBUTE_BLOCK[\"attribute\"] ( attributeBodyDeclaration )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ATTRIBUTE_BLOCK, "attribute"), root_1);

                // StateMachine.g:178:36: ( attributeBodyDeclaration )*
                while ( stream_attributeBodyDeclaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_attributeBodyDeclaration.nextTree());

                }
                stream_attributeBodyDeclaration.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attributeNT"

    public static class attributeBodyDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeBodyDeclaration"
    // StateMachine.g:181:1: attributeBodyDeclaration : ( 'priority' ASSIGN integerLiteral SEMICOLON | 'requeue' ASSIGN booleanLiteral SEMICOLON | '$lastmod' ASSIGN StringLiteral SEMICOLON ) ;
    public final StateMachineParser.attributeBodyDeclaration_return attributeBodyDeclaration() throws RecognitionException {
        StateMachineParser.attributeBodyDeclaration_return retval = new StateMachineParser.attributeBodyDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal110=null;
        Token ASSIGN111=null;
        Token SEMICOLON113=null;
        Token string_literal114=null;
        Token ASSIGN115=null;
        Token SEMICOLON117=null;
        Token string_literal118=null;
        Token ASSIGN119=null;
        Token StringLiteral120=null;
        Token SEMICOLON121=null;
        StateMachineParser.integerLiteral_return integerLiteral112 = null;

        StateMachineParser.booleanLiteral_return booleanLiteral116 = null;


        Object string_literal110_tree=null;
        Object ASSIGN111_tree=null;
        Object SEMICOLON113_tree=null;
        Object string_literal114_tree=null;
        Object ASSIGN115_tree=null;
        Object SEMICOLON117_tree=null;
        Object string_literal118_tree=null;
        Object ASSIGN119_tree=null;
        Object StringLiteral120_tree=null;
        Object SEMICOLON121_tree=null;

        try {
            // StateMachine.g:182:2: ( ( 'priority' ASSIGN integerLiteral SEMICOLON | 'requeue' ASSIGN booleanLiteral SEMICOLON | '$lastmod' ASSIGN StringLiteral SEMICOLON ) )
            // StateMachine.g:182:4: ( 'priority' ASSIGN integerLiteral SEMICOLON | 'requeue' ASSIGN booleanLiteral SEMICOLON | '$lastmod' ASSIGN StringLiteral SEMICOLON )
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:182:4: ( 'priority' ASSIGN integerLiteral SEMICOLON | 'requeue' ASSIGN booleanLiteral SEMICOLON | '$lastmod' ASSIGN StringLiteral SEMICOLON )
            int alt19=3;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt19=1;
                }
                break;
            case 94:
                {
                alt19=2;
                }
                break;
            case 95:
                {
                alt19=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // StateMachine.g:182:5: 'priority' ASSIGN integerLiteral SEMICOLON
                    {
                    string_literal110=(Token)match(input,93,FOLLOW_93_in_attributeBodyDeclaration974); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal110_tree = (Object)adaptor.create(string_literal110);
                    adaptor.addChild(root_0, string_literal110_tree);
                    }
                    ASSIGN111=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration976); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN111_tree = (Object)adaptor.create(ASSIGN111);
                    adaptor.addChild(root_0, ASSIGN111_tree);
                    }
                    pushFollow(FOLLOW_integerLiteral_in_attributeBodyDeclaration978);
                    integerLiteral112=integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, integerLiteral112.getTree());
                    SEMICOLON113=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration980); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON113_tree = (Object)adaptor.create(SEMICOLON113);
                    adaptor.addChild(root_0, SEMICOLON113_tree);
                    }

                    }
                    break;
                case 2 :
                    // StateMachine.g:183:4: 'requeue' ASSIGN booleanLiteral SEMICOLON
                    {
                    string_literal114=(Token)match(input,94,FOLLOW_94_in_attributeBodyDeclaration985); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal114_tree = (Object)adaptor.create(string_literal114);
                    adaptor.addChild(root_0, string_literal114_tree);
                    }
                    ASSIGN115=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration987); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN115_tree = (Object)adaptor.create(ASSIGN115);
                    adaptor.addChild(root_0, ASSIGN115_tree);
                    }
                    pushFollow(FOLLOW_booleanLiteral_in_attributeBodyDeclaration989);
                    booleanLiteral116=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booleanLiteral116.getTree());
                    SEMICOLON117=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration991); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON117_tree = (Object)adaptor.create(SEMICOLON117);
                    adaptor.addChild(root_0, SEMICOLON117_tree);
                    }

                    }
                    break;
                case 3 :
                    // StateMachine.g:184:4: '$lastmod' ASSIGN StringLiteral SEMICOLON
                    {
                    string_literal118=(Token)match(input,95,FOLLOW_95_in_attributeBodyDeclaration996); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal118_tree = (Object)adaptor.create(string_literal118);
                    adaptor.addChild(root_0, string_literal118_tree);
                    }
                    ASSIGN119=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration998); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN119_tree = (Object)adaptor.create(ASSIGN119);
                    adaptor.addChild(root_0, ASSIGN119_tree);
                    }
                    StringLiteral120=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_attributeBodyDeclaration1000); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    StringLiteral120_tree = (Object)adaptor.create(StringLiteral120);
                    adaptor.addChild(root_0, StringLiteral120_tree);
                    }
                    SEMICOLON121=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration1002); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON121_tree = (Object)adaptor.create(SEMICOLON121);
                    adaptor.addChild(root_0, SEMICOLON121_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attributeBodyDeclaration"

    public static class declareNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareNT"
    // StateMachine.g:187:1: declareNT : 'declare' LBRACE ( declarator )* RBRACE -> ^( DECLARE_BLOCK[\"declare\"] ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) ;
    public final StateMachineParser.declareNT_return declareNT() throws RecognitionException {
        StateMachineParser.declareNT_return retval = new StateMachineParser.declareNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal122=null;
        Token LBRACE123=null;
        Token RBRACE125=null;
        StateMachineParser.declarator_return declarator124 = null;


        Object string_literal122_tree=null;
        Object LBRACE123_tree=null;
        Object RBRACE125_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        try {
            // StateMachine.g:188:2: ( 'declare' LBRACE ( declarator )* RBRACE -> ^( DECLARE_BLOCK[\"declare\"] ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) )
            // StateMachine.g:188:4: 'declare' LBRACE ( declarator )* RBRACE
            {
            string_literal122=(Token)match(input,96,FOLLOW_96_in_declareNT1014); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_96.add(string_literal122);

            LBRACE123=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_declareNT1016); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE123);

            // StateMachine.g:188:21: ( declarator )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==Identifier) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // StateMachine.g:188:21: declarator
            	    {
            	    pushFollow(FOLLOW_declarator_in_declareNT1018);
            	    declarator124=declarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_declarator.add(declarator124.getTree());

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            RBRACE125=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_declareNT1021); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE125);



            // AST REWRITE
            // elements: declarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 189:2: -> ^( DECLARE_BLOCK[\"declare\"] ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
            {
                // StateMachine.g:189:5: ^( DECLARE_BLOCK[\"declare\"] ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DECLARE_BLOCK, "declare"), root_1);

                // StateMachine.g:189:32: ^( STATEMENTS[\"declarators\"] ( declarator )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "declarators"), root_2);

                // StateMachine.g:189:60: ( declarator )*
                while ( stream_declarator.hasNext() ) {
                    adaptor.addChild(root_2, stream_declarator.nextTree());

                }
                stream_declarator.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "declareNT"

    public static class scopeNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeNT"
    // StateMachine.g:192:1: scopeNT : 'scope' LBRACE ( scopeDeclarator )* RBRACE -> ^( SCOPE_BLOCK[\"scope\"] ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) ;
    public final StateMachineParser.scopeNT_return scopeNT() throws RecognitionException {
        StateMachineParser.scopeNT_return retval = new StateMachineParser.scopeNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal126=null;
        Token LBRACE127=null;
        Token RBRACE129=null;
        StateMachineParser.scopeDeclarator_return scopeDeclarator128 = null;


        Object string_literal126_tree=null;
        Object LBRACE127_tree=null;
        Object RBRACE129_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_97=new RewriteRuleTokenStream(adaptor,"token 97");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_scopeDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule scopeDeclarator");
        try {
            // StateMachine.g:193:2: ( 'scope' LBRACE ( scopeDeclarator )* RBRACE -> ^( SCOPE_BLOCK[\"scope\"] ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) )
            // StateMachine.g:193:4: 'scope' LBRACE ( scopeDeclarator )* RBRACE
            {
            string_literal126=(Token)match(input,97,FOLLOW_97_in_scopeNT1048); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_97.add(string_literal126);

            LBRACE127=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_scopeNT1050); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE127);

            // StateMachine.g:193:19: ( scopeDeclarator )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==Identifier||(LA21_0>=126 && LA21_0<=129)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // StateMachine.g:193:19: scopeDeclarator
            	    {
            	    pushFollow(FOLLOW_scopeDeclarator_in_scopeNT1052);
            	    scopeDeclarator128=scopeDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_scopeDeclarator.add(scopeDeclarator128.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            RBRACE129=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_scopeNT1055); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE129);



            // AST REWRITE
            // elements: scopeDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 194:2: -> ^( SCOPE_BLOCK[\"scope\"] ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
            {
                // StateMachine.g:194:5: ^( SCOPE_BLOCK[\"scope\"] ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SCOPE_BLOCK, "scope"), root_1);

                // StateMachine.g:194:28: ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "declarators"), root_2);

                // StateMachine.g:194:56: ( scopeDeclarator )*
                while ( stream_scopeDeclarator.hasNext() ) {
                    adaptor.addChild(root_2, stream_scopeDeclarator.nextTree());

                }
                stream_scopeDeclarator.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "scopeNT"

    public static class scopeDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeDeclarator"
    // StateMachine.g:197:1: scopeDeclarator : ( name | primitiveType ) identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] ( name )? ( primitiveType )? ) ^( NAME[\"name\"] identifier ) ) ;
    public final StateMachineParser.scopeDeclarator_return scopeDeclarator() throws RecognitionException {
        StateMachineParser.scopeDeclarator_return retval = new StateMachineParser.scopeDeclarator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON133=null;
        StateMachineParser.name_return name130 = null;

        StateMachineParser.primitiveType_return primitiveType131 = null;

        StateMachineParser.identifier_return identifier132 = null;


        Object SEMICOLON133_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        RewriteRuleSubtreeStream stream_primitiveType=new RewriteRuleSubtreeStream(adaptor,"rule primitiveType");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:198:2: ( ( name | primitiveType ) identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] ( name )? ( primitiveType )? ) ^( NAME[\"name\"] identifier ) ) )
            // StateMachine.g:198:4: ( name | primitiveType ) identifier SEMICOLON
            {
            // StateMachine.g:198:4: ( name | primitiveType )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==Identifier) ) {
                alt22=1;
            }
            else if ( ((LA22_0>=126 && LA22_0<=129)) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // StateMachine.g:198:5: name
                    {
                    pushFollow(FOLLOW_name_in_scopeDeclarator1083);
                    name130=name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(name130.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:198:12: primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_scopeDeclarator1087);
                    primitiveType131=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primitiveType.add(primitiveType131.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_identifier_in_scopeDeclarator1090);
            identifier132=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(identifier132.getTree());
            SEMICOLON133=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_scopeDeclarator1092); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON133);



            // AST REWRITE
            // elements: identifier, name, primitiveType
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 199:2: -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] ( name )? ( primitiveType )? ) ^( NAME[\"name\"] identifier ) )
            {
                // StateMachine.g:199:5: ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] ( name )? ( primitiveType )? ) ^( NAME[\"name\"] identifier ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SCOPE_DECLARATOR, "declarator"), root_1);

                // StateMachine.g:199:38: ^( TYPE[\"type\"] ( name )? ( primitiveType )? )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "type"), root_2);

                // StateMachine.g:199:53: ( name )?
                if ( stream_name.hasNext() ) {
                    adaptor.addChild(root_2, stream_name.nextTree());

                }
                stream_name.reset();
                // StateMachine.g:199:59: ( primitiveType )?
                if ( stream_primitiveType.hasNext() ) {
                    adaptor.addChild(root_2, stream_primitiveType.nextTree());

                }
                stream_primitiveType.reset();

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:200:3: ^( NAME[\"name\"] identifier )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_identifier.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "scopeDeclarator"

    public static class declarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declarator"
    // StateMachine.g:209:1: declarator : name identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] name ) ^( NAME[\"name\"] identifier ) ) ;
    public final StateMachineParser.declarator_return declarator() throws RecognitionException {
        StateMachineParser.declarator_return retval = new StateMachineParser.declarator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON136=null;
        StateMachineParser.name_return name134 = null;

        StateMachineParser.identifier_return identifier135 = null;


        Object SEMICOLON136_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // StateMachine.g:210:2: ( name identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] name ) ^( NAME[\"name\"] identifier ) ) )
            // StateMachine.g:210:4: name identifier SEMICOLON
            {
            pushFollow(FOLLOW_name_in_declarator1136);
            name134=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name134.getTree());
            pushFollow(FOLLOW_identifier_in_declarator1138);
            identifier135=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(identifier135.getTree());
            SEMICOLON136=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_declarator1140); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON136);



            // AST REWRITE
            // elements: identifier, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 211:2: -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] name ) ^( NAME[\"name\"] identifier ) )
            {
                // StateMachine.g:211:5: ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] name ) ^( NAME[\"name\"] identifier ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(DECLARATOR, "declarator"), root_1);

                // StateMachine.g:211:32: ^( TYPE[\"type\"] name )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // StateMachine.g:212:3: ^( NAME[\"name\"] identifier )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_identifier.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "declarator"

    public static class whenNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whenNT"
    // StateMachine.g:215:1: whenNT : 'when' LBRACE predicates RBRACE -> ^( WHEN_BLOCK[\"when\"] predicates ) ;
    public final StateMachineParser.whenNT_return whenNT() throws RecognitionException {
        StateMachineParser.whenNT_return retval = new StateMachineParser.whenNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal137=null;
        Token LBRACE138=null;
        Token RBRACE140=null;
        StateMachineParser.predicates_return predicates139 = null;


        Object string_literal137_tree=null;
        Object LBRACE138_tree=null;
        Object RBRACE140_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleSubtreeStream stream_predicates=new RewriteRuleSubtreeStream(adaptor,"rule predicates");
        try {
            // StateMachine.g:215:8: ( 'when' LBRACE predicates RBRACE -> ^( WHEN_BLOCK[\"when\"] predicates ) )
            // StateMachine.g:215:10: 'when' LBRACE predicates RBRACE
            {
            string_literal137=(Token)match(input,98,FOLLOW_98_in_whenNT1175); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_98.add(string_literal137);

            LBRACE138=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_whenNT1177); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE138);

            pushFollow(FOLLOW_predicates_in_whenNT1179);
            predicates139=predicates();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_predicates.add(predicates139.getTree());
            RBRACE140=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_whenNT1181); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE140);



            // AST REWRITE
            // elements: predicates
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 216:2: -> ^( WHEN_BLOCK[\"when\"] predicates )
            {
                // StateMachine.g:216:5: ^( WHEN_BLOCK[\"when\"] predicates )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(WHEN_BLOCK, "when"), root_1);

                adaptor.addChild(root_1, stream_predicates.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "whenNT"

    public static class predicates_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicates"
    // StateMachine.g:219:1: predicates : ( predicate SEMICOLON )* -> ^( PREDICATES ( predicate )* ) ;
    public final StateMachineParser.predicates_return predicates() throws RecognitionException {
        StateMachineParser.predicates_return retval = new StateMachineParser.predicates_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON142=null;
        StateMachineParser.predicate_return predicate141 = null;


        Object SEMICOLON142_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        try {
            // StateMachine.g:220:2: ( ( predicate SEMICOLON )* -> ^( PREDICATES ( predicate )* ) )
            // StateMachine.g:220:4: ( predicate SEMICOLON )*
            {
            // StateMachine.g:220:4: ( predicate SEMICOLON )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==Identifier||(LA23_0>=StringLiteral && LA23_0<=LBRACE)||(LA23_0>=EQ && LA23_0<=HexLiteral)||LA23_0==74||(LA23_0>=101 && LA23_0<=102)||(LA23_0>=104 && LA23_0<=105)||LA23_0==109||(LA23_0>=111 && LA23_0<=112)||(LA23_0>=126 && LA23_0<=129)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // StateMachine.g:220:5: predicate SEMICOLON
            	    {
            	    pushFollow(FOLLOW_predicate_in_predicates1204);
            	    predicate141=predicate();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_predicate.add(predicate141.getTree());
            	    SEMICOLON142=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_predicates1206); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON142);


            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);



            // AST REWRITE
            // elements: predicate
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 221:2: -> ^( PREDICATES ( predicate )* )
            {
                // StateMachine.g:221:5: ^( PREDICATES ( predicate )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDICATES, "PREDICATES"), root_1);

                // StateMachine.g:221:18: ( predicate )*
                while ( stream_predicate.hasNext() ) {
                    adaptor.addChild(root_1, stream_predicate.nextTree());

                }
                stream_predicate.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicates"

    public static class predicate_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicate"
    // StateMachine.g:224:1: predicate : expression ;
    public final StateMachineParser.predicate_return predicate() throws RecognitionException {
        StateMachineParser.predicate_return retval = new StateMachineParser.predicate_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.expression_return expression143 = null;



        try {
            // StateMachine.g:225:2: ( expression )
            // StateMachine.g:225:4: expression
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expression_in_predicate1229);
            expression143=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression143.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicate"

    public static class expression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // StateMachine.g:228:1: expression : conditionalAndExpression ( '||' conditionalAndExpression )* ;
    public final StateMachineParser.expression_return expression() throws RecognitionException {
        StateMachineParser.expression_return retval = new StateMachineParser.expression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal145=null;
        StateMachineParser.conditionalAndExpression_return conditionalAndExpression144 = null;

        StateMachineParser.conditionalAndExpression_return conditionalAndExpression146 = null;


        Object string_literal145_tree=null;

        try {
            // StateMachine.g:229:2: ( conditionalAndExpression ( '||' conditionalAndExpression )* )
            // StateMachine.g:229:4: conditionalAndExpression ( '||' conditionalAndExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_conditionalAndExpression_in_expression1241);
            conditionalAndExpression144=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression144.getTree());
            // StateMachine.g:230:2: ( '||' conditionalAndExpression )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==99) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // StateMachine.g:230:4: '||' conditionalAndExpression
            	    {
            	    string_literal145=(Token)match(input,99,FOLLOW_99_in_expression1246); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal145_tree = (Object)adaptor.create(string_literal145);
            	    adaptor.addChild(root_0, string_literal145_tree);
            	    }
            	    pushFollow(FOLLOW_conditionalAndExpression_in_expression1248);
            	    conditionalAndExpression146=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression146.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class conditionalAndExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionalAndExpression"
    // StateMachine.g:233:1: conditionalAndExpression : equalityExpression ( '&&' equalityExpression )* ;
    public final StateMachineParser.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        StateMachineParser.conditionalAndExpression_return retval = new StateMachineParser.conditionalAndExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal148=null;
        StateMachineParser.equalityExpression_return equalityExpression147 = null;

        StateMachineParser.equalityExpression_return equalityExpression149 = null;


        Object string_literal148_tree=null;

        try {
            // StateMachine.g:234:2: ( equalityExpression ( '&&' equalityExpression )* )
            // StateMachine.g:234:4: equalityExpression ( '&&' equalityExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1262);
            equalityExpression147=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression147.getTree());
            // StateMachine.g:235:2: ( '&&' equalityExpression )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==100) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // StateMachine.g:235:4: '&&' equalityExpression
            	    {
            	    string_literal148=(Token)match(input,100,FOLLOW_100_in_conditionalAndExpression1267); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal148_tree = (Object)adaptor.create(string_literal148);
            	    adaptor.addChild(root_0, string_literal148_tree);
            	    }
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1269);
            	    equalityExpression149=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression149.getTree());

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "conditionalAndExpression"

    public static class equalityExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpression"
    // StateMachine.g:238:1: equalityExpression : ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS ) )* ;
    public final StateMachineParser.equalityExpression_return equalityExpression() throws RecognitionException {
        StateMachineParser.equalityExpression_return retval = new StateMachineParser.equalityExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set153=null;
        StateMachineParser.relationalExpression_return relationalExpression150 = null;

        StateMachineParser.domainSpec_return domainSpec151 = null;

        StateMachineParser.comparisonNoLHS_return comparisonNoLHS152 = null;

        StateMachineParser.domainSpec_return domainSpec154 = null;

        StateMachineParser.relationalExpression_return relationalExpression155 = null;

        StateMachineParser.comparisonNoLHS_return comparisonNoLHS156 = null;


        Object set153_tree=null;

        try {
            // StateMachine.g:239:2: ( ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS ) )* )
            // StateMachine.g:239:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS ) )*
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:239:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )
            int alt26=3;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // StateMachine.g:239:5: relationalExpression
                    {
                    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1284);
                    relationalExpression150=relationalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression150.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:240:4: ( domainSpec )=> domainSpec
                    {
                    pushFollow(FOLLOW_domainSpec_in_equalityExpression1293);
                    domainSpec151=domainSpec();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec151.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:241:4: comparisonNoLHS
                    {
                    pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression1299);
                    comparisonNoLHS152=comparisonNoLHS();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS152.getTree());

                    }
                    break;

            }

            // StateMachine.g:242:2: ( ( EQ | NE ) ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS ) )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=EQ && LA28_0<=NE)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // StateMachine.g:243:3: ( EQ | NE ) ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS )
            	    {
            	    set153=(Token)input.LT(1);
            	    if ( (input.LA(1)>=EQ && input.LA(1)<=NE) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set153));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    // StateMachine.g:244:3: ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS )
            	    int alt27=3;
            	    alt27 = dfa27.predict(input);
            	    switch (alt27) {
            	        case 1 :
            	            // StateMachine.g:244:4: ( domainSpec )=> domainSpec
            	            {
            	            pushFollow(FOLLOW_domainSpec_in_equalityExpression1323);
            	            domainSpec154=domainSpec();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec154.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // StateMachine.g:245:5: relationalExpression
            	            {
            	            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1329);
            	            relationalExpression155=relationalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression155.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // StateMachine.g:246:5: comparisonNoLHS
            	            {
            	            pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression1335);
            	            comparisonNoLHS156=comparisonNoLHS();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS156.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "equalityExpression"

    public static class comparisonNoLHS_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comparisonNoLHS"
    // StateMachine.g:250:1: comparisonNoLHS : ( ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression ) | ( 'instanceof' type ) | ( ( LT | GT | LE | GE ) additiveExpression ) );
    public final StateMachineParser.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException {
        StateMachineParser.comparisonNoLHS_return retval = new StateMachineParser.comparisonNoLHS_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set157=null;
        Token string_literal160=null;
        Token set162=null;
        StateMachineParser.domainSpec_return domainSpec158 = null;

        StateMachineParser.unaryExpression_return unaryExpression159 = null;

        StateMachineParser.type_return type161 = null;

        StateMachineParser.additiveExpression_return additiveExpression163 = null;


        Object set157_tree=null;
        Object string_literal160_tree=null;
        Object set162_tree=null;

        try {
            // StateMachine.g:251:2: ( ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression ) | ( 'instanceof' type ) | ( ( LT | GT | LE | GE ) additiveExpression ) )
            int alt30=3;
            switch ( input.LA(1) ) {
            case Identifier:
            case StringLiteral:
            case EQ:
            case NE:
            case FloatingPointLiteral:
            case NullLiteral:
            case DecimalLiteral:
            case HexLiteral:
            case 74:
            case 104:
            case 105:
            case 109:
            case 111:
            case 112:
            case 126:
            case 127:
            case 128:
            case 129:
                {
                alt30=1;
                }
                break;
            case 101:
                {
                alt30=2;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt30=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // StateMachine.g:251:4: ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression )
                    {
                    root_0 = (Object)adaptor.nil();

                    // StateMachine.g:251:4: ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression )
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( ((LA29_0>=EQ && LA29_0<=NE)) ) {
                        alt29=1;
                    }
                    else if ( (LA29_0==Identifier||LA29_0==StringLiteral||(LA29_0>=FloatingPointLiteral && LA29_0<=HexLiteral)||LA29_0==74||(LA29_0>=104 && LA29_0<=105)||LA29_0==109||(LA29_0>=111 && LA29_0<=112)||(LA29_0>=126 && LA29_0<=129)) ) {
                        alt29=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 29, 0, input);

                        throw nvae;
                    }
                    switch (alt29) {
                        case 1 :
                            // StateMachine.g:252:3: ( EQ | NE ) ( domainSpec )=> domainSpec
                            {
                            set157=(Token)input.LT(1);
                            if ( (input.LA(1)>=EQ && input.LA(1)<=NE) ) {
                                input.consume();
                                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set157));
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            pushFollow(FOLLOW_domainSpec_in_comparisonNoLHS1370);
                            domainSpec158=domainSpec();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec158.getTree());

                            }
                            break;
                        case 2 :
                            // StateMachine.g:254:5: unaryExpression
                            {
                            pushFollow(FOLLOW_unaryExpression_in_comparisonNoLHS1376);
                            unaryExpression159=unaryExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression159.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // StateMachine.g:256:2: ( 'instanceof' type )
                    {
                    root_0 = (Object)adaptor.nil();

                    // StateMachine.g:256:2: ( 'instanceof' type )
                    // StateMachine.g:256:4: 'instanceof' type
                    {
                    string_literal160=(Token)match(input,101,FOLLOW_101_in_comparisonNoLHS1386); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal160_tree = (Object)adaptor.create(string_literal160);
                    adaptor.addChild(root_0, string_literal160_tree);
                    }
                    pushFollow(FOLLOW_type_in_comparisonNoLHS1388);
                    type161=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, type161.getTree());

                    }


                    }
                    break;
                case 3 :
                    // StateMachine.g:257:2: ( ( LT | GT | LE | GE ) additiveExpression )
                    {
                    root_0 = (Object)adaptor.nil();

                    // StateMachine.g:257:2: ( ( LT | GT | LE | GE ) additiveExpression )
                    // StateMachine.g:257:4: ( LT | GT | LE | GE ) additiveExpression
                    {
                    set162=(Token)input.LT(1);
                    if ( (input.LA(1)>=LT && input.LA(1)<=GE) ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set162));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_additiveExpression_in_comparisonNoLHS1413);
                    additiveExpression163=additiveExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression163.getTree());

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comparisonNoLHS"

    public static class domainSpec_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainSpec"
    // StateMachine.g:260:1: domainSpec : ( rangeExpression | setMembershipExpression );
    public final StateMachineParser.domainSpec_return domainSpec() throws RecognitionException {
        StateMachineParser.domainSpec_return retval = new StateMachineParser.domainSpec_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.rangeExpression_return rangeExpression164 = null;

        StateMachineParser.setMembershipExpression_return setMembershipExpression165 = null;



        try {
            // StateMachine.g:261:2: ( rangeExpression | setMembershipExpression )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==74||LA31_0==102) ) {
                alt31=1;
            }
            else if ( (LA31_0==LBRACE) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // StateMachine.g:261:4: rangeExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_rangeExpression_in_domainSpec1426);
                    rangeExpression164=rangeExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, rangeExpression164.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:261:22: setMembershipExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_setMembershipExpression_in_domainSpec1430);
                    setMembershipExpression165=setMembershipExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, setMembershipExpression165.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "domainSpec"

    public static class rangeExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeExpression"
    // StateMachine.g:264:1: rangeExpression : rangeStart ( expression )? ',' ( expression )? rangeEnd ;
    public final StateMachineParser.rangeExpression_return rangeExpression() throws RecognitionException {
        StateMachineParser.rangeExpression_return retval = new StateMachineParser.rangeExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal168=null;
        StateMachineParser.rangeStart_return rangeStart166 = null;

        StateMachineParser.expression_return expression167 = null;

        StateMachineParser.expression_return expression169 = null;

        StateMachineParser.rangeEnd_return rangeEnd170 = null;


        Object char_literal168_tree=null;

        try {
            // StateMachine.g:265:2: ( rangeStart ( expression )? ',' ( expression )? rangeEnd )
            // StateMachine.g:265:4: rangeStart ( expression )? ',' ( expression )? rangeEnd
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_rangeStart_in_rangeExpression1442);
            rangeStart166=rangeStart();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, rangeStart166.getTree());
            // StateMachine.g:265:15: ( expression )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==Identifier||(LA32_0>=StringLiteral && LA32_0<=LBRACE)||(LA32_0>=EQ && LA32_0<=HexLiteral)||LA32_0==74||(LA32_0>=101 && LA32_0<=102)||(LA32_0>=104 && LA32_0<=105)||LA32_0==109||(LA32_0>=111 && LA32_0<=112)||(LA32_0>=126 && LA32_0<=129)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // StateMachine.g:265:15: expression
                    {
                    pushFollow(FOLLOW_expression_in_rangeExpression1444);
                    expression167=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression167.getTree());

                    }
                    break;

            }

            char_literal168=(Token)match(input,75,FOLLOW_75_in_rangeExpression1447); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal168_tree = (Object)adaptor.create(char_literal168);
            adaptor.addChild(root_0, char_literal168_tree);
            }
            // StateMachine.g:265:31: ( expression )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==Identifier||(LA33_0>=StringLiteral && LA33_0<=LBRACE)||(LA33_0>=EQ && LA33_0<=HexLiteral)||LA33_0==74||(LA33_0>=101 && LA33_0<=102)||(LA33_0>=104 && LA33_0<=105)||LA33_0==109||(LA33_0>=111 && LA33_0<=112)||(LA33_0>=126 && LA33_0<=129)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // StateMachine.g:265:31: expression
                    {
                    pushFollow(FOLLOW_expression_in_rangeExpression1449);
                    expression169=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression169.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_rangeEnd_in_rangeExpression1452);
            rangeEnd170=rangeEnd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, rangeEnd170.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeExpression"

    public static class rangeStart_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeStart"
    // StateMachine.g:268:1: rangeStart : ( '(' | '[' );
    public final StateMachineParser.rangeStart_return rangeStart() throws RecognitionException {
        StateMachineParser.rangeStart_return retval = new StateMachineParser.rangeStart_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set171=null;

        Object set171_tree=null;

        try {
            // StateMachine.g:269:2: ( '(' | '[' )
            // StateMachine.g:
            {
            root_0 = (Object)adaptor.nil();

            set171=(Token)input.LT(1);
            if ( input.LA(1)==74||input.LA(1)==102 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set171));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeStart"

    public static class rangeEnd_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeEnd"
    // StateMachine.g:272:1: rangeEnd : ( ')' | ']' );
    public final StateMachineParser.rangeEnd_return rangeEnd() throws RecognitionException {
        StateMachineParser.rangeEnd_return retval = new StateMachineParser.rangeEnd_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set172=null;

        Object set172_tree=null;

        try {
            // StateMachine.g:273:2: ( ')' | ']' )
            // StateMachine.g:
            {
            root_0 = (Object)adaptor.nil();

            set172=(Token)input.LT(1);
            if ( input.LA(1)==76||input.LA(1)==103 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set172));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeEnd"

    public static class setMembershipExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "setMembershipExpression"
    // StateMachine.g:276:1: setMembershipExpression : '{' ( expression ( ',' expression )* )? '}' ;
    public final StateMachineParser.setMembershipExpression_return setMembershipExpression() throws RecognitionException {
        StateMachineParser.setMembershipExpression_return retval = new StateMachineParser.setMembershipExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal173=null;
        Token char_literal175=null;
        Token char_literal177=null;
        StateMachineParser.expression_return expression174 = null;

        StateMachineParser.expression_return expression176 = null;


        Object char_literal173_tree=null;
        Object char_literal175_tree=null;
        Object char_literal177_tree=null;

        try {
            // StateMachine.g:277:2: ( '{' ( expression ( ',' expression )* )? '}' )
            // StateMachine.g:277:4: '{' ( expression ( ',' expression )* )? '}'
            {
            root_0 = (Object)adaptor.nil();

            char_literal173=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_setMembershipExpression1494); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal173_tree = (Object)adaptor.create(char_literal173);
            adaptor.addChild(root_0, char_literal173_tree);
            }
            // StateMachine.g:277:8: ( expression ( ',' expression )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==Identifier||(LA35_0>=StringLiteral && LA35_0<=LBRACE)||(LA35_0>=EQ && LA35_0<=HexLiteral)||LA35_0==74||(LA35_0>=101 && LA35_0<=102)||(LA35_0>=104 && LA35_0<=105)||LA35_0==109||(LA35_0>=111 && LA35_0<=112)||(LA35_0>=126 && LA35_0<=129)) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // StateMachine.g:277:9: expression ( ',' expression )*
                    {
                    pushFollow(FOLLOW_expression_in_setMembershipExpression1497);
                    expression174=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression174.getTree());
                    // StateMachine.g:277:20: ( ',' expression )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==75) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // StateMachine.g:277:21: ',' expression
                    	    {
                    	    char_literal175=(Token)match(input,75,FOLLOW_75_in_setMembershipExpression1500); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal175_tree = (Object)adaptor.create(char_literal175);
                    	    adaptor.addChild(root_0, char_literal175_tree);
                    	    }
                    	    pushFollow(FOLLOW_expression_in_setMembershipExpression1502);
                    	    expression176=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression176.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal177=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_setMembershipExpression1508); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal177_tree = (Object)adaptor.create(char_literal177);
            adaptor.addChild(root_0, char_literal177_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "setMembershipExpression"

    public static class relationalExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpression"
    // StateMachine.g:280:1: relationalExpression : additiveExpression ( ( LT | GT | LE | GE ) additiveExpression | 'instanceof' type )* ;
    public final StateMachineParser.relationalExpression_return relationalExpression() throws RecognitionException {
        StateMachineParser.relationalExpression_return retval = new StateMachineParser.relationalExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set179=null;
        Token string_literal181=null;
        StateMachineParser.additiveExpression_return additiveExpression178 = null;

        StateMachineParser.additiveExpression_return additiveExpression180 = null;

        StateMachineParser.type_return type182 = null;


        Object set179_tree=null;
        Object string_literal181_tree=null;

        try {
            // StateMachine.g:281:2: ( additiveExpression ( ( LT | GT | LE | GE ) additiveExpression | 'instanceof' type )* )
            // StateMachine.g:281:4: additiveExpression ( ( LT | GT | LE | GE ) additiveExpression | 'instanceof' type )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1519);
            additiveExpression178=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression178.getTree());
            // StateMachine.g:282:2: ( ( LT | GT | LE | GE ) additiveExpression | 'instanceof' type )*
            loop36:
            do {
                int alt36=3;
                int LA36_0 = input.LA(1);

                if ( ((LA36_0>=LT && LA36_0<=GE)) ) {
                    alt36=1;
                }
                else if ( (LA36_0==101) ) {
                    alt36=2;
                }


                switch (alt36) {
            	case 1 :
            	    // StateMachine.g:283:3: ( LT | GT | LE | GE ) additiveExpression
            	    {
            	    set179=(Token)input.LT(1);
            	    if ( (input.LA(1)>=LT && input.LA(1)<=GE) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set179));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression1542);
            	    additiveExpression180=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression180.getTree());

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:285:3: 'instanceof' type
            	    {
            	    string_literal181=(Token)match(input,101,FOLLOW_101_in_relationalExpression1550); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    string_literal181_tree = (Object)adaptor.create(string_literal181);
            	    adaptor.addChild(root_0, string_literal181_tree);
            	    }
            	    pushFollow(FOLLOW_type_in_relationalExpression1552);
            	    type182=type();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, type182.getTree());

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalExpression"

    public static class additiveExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpression"
    // StateMachine.g:289:1: additiveExpression : multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* ;
    public final StateMachineParser.additiveExpression_return additiveExpression() throws RecognitionException {
        StateMachineParser.additiveExpression_return retval = new StateMachineParser.additiveExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set184=null;
        StateMachineParser.multiplicativeExpression_return multiplicativeExpression183 = null;

        StateMachineParser.multiplicativeExpression_return multiplicativeExpression185 = null;


        Object set184_tree=null;

        try {
            // StateMachine.g:290:2: ( multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )* )
            // StateMachine.g:290:4: multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1568);
            multiplicativeExpression183=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression183.getTree());
            // StateMachine.g:291:2: ( ( '+' | '-' ) multiplicativeExpression )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=104 && LA37_0<=105)) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // StateMachine.g:291:4: ( '+' | '-' ) multiplicativeExpression
            	    {
            	    set184=(Token)input.LT(1);
            	    if ( (input.LA(1)>=104 && input.LA(1)<=105) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set184));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1581);
            	    multiplicativeExpression185=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression185.getTree());

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "additiveExpression"

    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpression"
    // StateMachine.g:294:1: multiplicativeExpression : unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* ;
    public final StateMachineParser.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        StateMachineParser.multiplicativeExpression_return retval = new StateMachineParser.multiplicativeExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set187=null;
        StateMachineParser.unaryExpression_return unaryExpression186 = null;

        StateMachineParser.unaryExpression_return unaryExpression188 = null;


        Object set187_tree=null;

        try {
            // StateMachine.g:295:2: ( unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )* )
            // StateMachine.g:295:4: unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1595);
            unaryExpression186=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression186.getTree());
            // StateMachine.g:296:2: ( ( '*' | '/' | '%' ) unaryExpression )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=106 && LA38_0<=108)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // StateMachine.g:296:4: ( '*' | '/' | '%' ) unaryExpression
            	    {
            	    set187=(Token)input.LT(1);
            	    if ( (input.LA(1)>=106 && input.LA(1)<=108) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set187));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1612);
            	    unaryExpression188=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression188.getTree());

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpression"

    public static class unaryExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpression"
    // StateMachine.g:299:1: unaryExpression : ( ( '+' | '-' | '!' ) unaryExpression | primaryExpression );
    public final StateMachineParser.unaryExpression_return unaryExpression() throws RecognitionException {
        StateMachineParser.unaryExpression_return retval = new StateMachineParser.unaryExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set189=null;
        StateMachineParser.unaryExpression_return unaryExpression190 = null;

        StateMachineParser.primaryExpression_return primaryExpression191 = null;


        Object set189_tree=null;

        try {
            // StateMachine.g:300:2: ( ( '+' | '-' | '!' ) unaryExpression | primaryExpression )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( ((LA39_0>=104 && LA39_0<=105)||LA39_0==109) ) {
                alt39=1;
            }
            else if ( (LA39_0==Identifier||LA39_0==StringLiteral||(LA39_0>=FloatingPointLiteral && LA39_0<=HexLiteral)||LA39_0==74||(LA39_0>=111 && LA39_0<=112)||(LA39_0>=126 && LA39_0<=129)) ) {
                alt39=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // StateMachine.g:300:4: ( '+' | '-' | '!' ) unaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    set189=(Token)input.LT(1);
                    if ( (input.LA(1)>=104 && input.LA(1)<=105)||input.LA(1)==109 ) {
                        input.consume();
                        if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set189));
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1638);
                    unaryExpression190=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression190.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:300:40: primaryExpression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primaryExpression_in_unaryExpression1642);
                    primaryExpression191=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primaryExpression191.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryExpression"

    public static class primaryExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryExpression"
    // StateMachine.g:303:1: primaryExpression : primaryPrefix ( primarySuffix )* ;
    public final StateMachineParser.primaryExpression_return primaryExpression() throws RecognitionException {
        StateMachineParser.primaryExpression_return retval = new StateMachineParser.primaryExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.primaryPrefix_return primaryPrefix192 = null;

        StateMachineParser.primarySuffix_return primarySuffix193 = null;



        try {
            // StateMachine.g:304:2: ( primaryPrefix ( primarySuffix )* )
            // StateMachine.g:304:4: primaryPrefix ( primarySuffix )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_primaryPrefix_in_primaryExpression1654);
            primaryPrefix192=primaryPrefix();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, primaryPrefix192.getTree());
            // StateMachine.g:304:18: ( primarySuffix )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==ANNOTATE||LA40_0==102||LA40_0==110) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // StateMachine.g:304:19: primarySuffix
            	    {
            	    pushFollow(FOLLOW_primarySuffix_in_primaryExpression1657);
            	    primarySuffix193=primarySuffix();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, primarySuffix193.getTree());

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primaryExpression"

    public static class primaryPrefix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryPrefix"
    // StateMachine.g:307:1: primaryPrefix : ( literal | '(' expression ')' | ( type '{' )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=> methodName argumentsSuffix | expressionName );
    public final StateMachineParser.primaryPrefix_return primaryPrefix() throws RecognitionException {
        StateMachineParser.primaryPrefix_return retval = new StateMachineParser.primaryPrefix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal195=null;
        Token char_literal197=null;
        StateMachineParser.literal_return literal194 = null;

        StateMachineParser.expression_return expression196 = null;

        StateMachineParser.arrayLiteral_return arrayLiteral198 = null;

        StateMachineParser.arrayAllocator_return arrayAllocator199 = null;

        StateMachineParser.methodName_return methodName200 = null;

        StateMachineParser.argumentsSuffix_return argumentsSuffix201 = null;

        StateMachineParser.expressionName_return expressionName202 = null;


        Object char_literal195_tree=null;
        Object char_literal197_tree=null;

        try {
            // StateMachine.g:308:2: ( literal | '(' expression ')' | ( type '{' )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=> methodName argumentsSuffix | expressionName )
            int alt41=6;
            alt41 = dfa41.predict(input);
            switch (alt41) {
                case 1 :
                    // StateMachine.g:308:4: literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_primaryPrefix1671);
                    literal194=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal194.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:309:4: '(' expression ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal195=(Token)match(input,74,FOLLOW_74_in_primaryPrefix1676); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal195_tree = (Object)adaptor.create(char_literal195);
                    adaptor.addChild(root_0, char_literal195_tree);
                    }
                    pushFollow(FOLLOW_expression_in_primaryPrefix1678);
                    expression196=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression196.getTree());
                    char_literal197=(Token)match(input,76,FOLLOW_76_in_primaryPrefix1680); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal197_tree = (Object)adaptor.create(char_literal197);
                    adaptor.addChild(root_0, char_literal197_tree);
                    }

                    }
                    break;
                case 3 :
                    // StateMachine.g:310:4: ( type '{' )=> arrayLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_arrayLiteral_in_primaryPrefix1691);
                    arrayLiteral198=arrayLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayLiteral198.getTree());

                    }
                    break;
                case 4 :
                    // StateMachine.g:311:4: ( arrayAllocator )=> arrayAllocator
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_arrayAllocator_in_primaryPrefix1700);
                    arrayAllocator199=arrayAllocator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAllocator199.getTree());

                    }
                    break;
                case 5 :
                    // StateMachine.g:312:4: ( methodName '(' )=> methodName argumentsSuffix
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_methodName_in_primaryPrefix1711);
                    methodName200=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodName200.getTree());
                    pushFollow(FOLLOW_argumentsSuffix_in_primaryPrefix1713);
                    argumentsSuffix201=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argumentsSuffix201.getTree());

                    }
                    break;
                case 6 :
                    // StateMachine.g:313:4: expressionName
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expressionName_in_primaryPrefix1718);
                    expressionName202=expressionName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionName202.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primaryPrefix"

    public static class primarySuffix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primarySuffix"
    // StateMachine.g:316:1: primarySuffix : ( arrayAccessSuffix | fieldAccessSuffix );
    public final StateMachineParser.primarySuffix_return primarySuffix() throws RecognitionException {
        StateMachineParser.primarySuffix_return retval = new StateMachineParser.primarySuffix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.arrayAccessSuffix_return arrayAccessSuffix203 = null;

        StateMachineParser.fieldAccessSuffix_return fieldAccessSuffix204 = null;



        try {
            // StateMachine.g:317:2: ( arrayAccessSuffix | fieldAccessSuffix )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==102) ) {
                alt42=1;
            }
            else if ( (LA42_0==ANNOTATE||LA42_0==110) ) {
                alt42=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // StateMachine.g:317:4: arrayAccessSuffix
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_arrayAccessSuffix_in_primarySuffix1730);
                    arrayAccessSuffix203=arrayAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccessSuffix203.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:317:24: fieldAccessSuffix
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_fieldAccessSuffix_in_primarySuffix1734);
                    fieldAccessSuffix204=fieldAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fieldAccessSuffix204.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primarySuffix"

    public static class arrayAccessSuffix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAccessSuffix"
    // StateMachine.g:320:1: arrayAccessSuffix : '[' expression ']' ;
    public final StateMachineParser.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException {
        StateMachineParser.arrayAccessSuffix_return retval = new StateMachineParser.arrayAccessSuffix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal205=null;
        Token char_literal207=null;
        StateMachineParser.expression_return expression206 = null;


        Object char_literal205_tree=null;
        Object char_literal207_tree=null;

        try {
            // StateMachine.g:321:2: ( '[' expression ']' )
            // StateMachine.g:321:4: '[' expression ']'
            {
            root_0 = (Object)adaptor.nil();

            char_literal205=(Token)match(input,102,FOLLOW_102_in_arrayAccessSuffix1746); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal205_tree = (Object)adaptor.create(char_literal205);
            adaptor.addChild(root_0, char_literal205_tree);
            }
            pushFollow(FOLLOW_expression_in_arrayAccessSuffix1748);
            expression206=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression206.getTree());
            char_literal207=(Token)match(input,103,FOLLOW_103_in_arrayAccessSuffix1750); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal207_tree = (Object)adaptor.create(char_literal207);
            adaptor.addChild(root_0, char_literal207_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayAccessSuffix"

    public static class fieldAccessSuffix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldAccessSuffix"
    // StateMachine.g:324:1: fieldAccessSuffix : ( '.' | '@' ) identifier ;
    public final StateMachineParser.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException {
        StateMachineParser.fieldAccessSuffix_return retval = new StateMachineParser.fieldAccessSuffix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set208=null;
        StateMachineParser.identifier_return identifier209 = null;


        Object set208_tree=null;

        try {
            // StateMachine.g:325:2: ( ( '.' | '@' ) identifier )
            // StateMachine.g:325:4: ( '.' | '@' ) identifier
            {
            root_0 = (Object)adaptor.nil();

            set208=(Token)input.LT(1);
            if ( input.LA(1)==ANNOTATE||input.LA(1)==110 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set208));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_identifier_in_fieldAccessSuffix1770);
            identifier209=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier209.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fieldAccessSuffix"

    public static class argumentsSuffix_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentsSuffix"
    // StateMachine.g:328:1: argumentsSuffix : '(' ( argumentList )? ')' ;
    public final StateMachineParser.argumentsSuffix_return argumentsSuffix() throws RecognitionException {
        StateMachineParser.argumentsSuffix_return retval = new StateMachineParser.argumentsSuffix_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal210=null;
        Token char_literal212=null;
        StateMachineParser.argumentList_return argumentList211 = null;


        Object char_literal210_tree=null;
        Object char_literal212_tree=null;

        try {
            // StateMachine.g:329:2: ( '(' ( argumentList )? ')' )
            // StateMachine.g:329:4: '(' ( argumentList )? ')'
            {
            root_0 = (Object)adaptor.nil();

            char_literal210=(Token)match(input,74,FOLLOW_74_in_argumentsSuffix1782); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal210_tree = (Object)adaptor.create(char_literal210);
            adaptor.addChild(root_0, char_literal210_tree);
            }
            // StateMachine.g:329:8: ( argumentList )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==Identifier||(LA43_0>=StringLiteral && LA43_0<=LBRACE)||(LA43_0>=EQ && LA43_0<=HexLiteral)||LA43_0==74||LA43_0==87||(LA43_0>=101 && LA43_0<=102)||(LA43_0>=104 && LA43_0<=105)||LA43_0==109||(LA43_0>=111 && LA43_0<=112)||(LA43_0>=126 && LA43_0<=129)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // StateMachine.g:329:8: argumentList
                    {
                    pushFollow(FOLLOW_argumentList_in_argumentsSuffix1784);
                    argumentList211=argumentList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argumentList211.getTree());

                    }
                    break;

            }

            char_literal212=(Token)match(input,76,FOLLOW_76_in_argumentsSuffix1787); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal212_tree = (Object)adaptor.create(char_literal212);
            adaptor.addChild(root_0, char_literal212_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argumentsSuffix"

    public static class argumentList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentList"
    // StateMachine.g:332:1: argumentList : ( expression ( ',' expression )* | xsltLiteral );
    public final StateMachineParser.argumentList_return argumentList() throws RecognitionException {
        StateMachineParser.argumentList_return retval = new StateMachineParser.argumentList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal214=null;
        StateMachineParser.expression_return expression213 = null;

        StateMachineParser.expression_return expression215 = null;

        StateMachineParser.xsltLiteral_return xsltLiteral216 = null;


        Object char_literal214_tree=null;

        try {
            // StateMachine.g:333:2: ( expression ( ',' expression )* | xsltLiteral )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==Identifier||(LA45_0>=StringLiteral && LA45_0<=LBRACE)||(LA45_0>=EQ && LA45_0<=HexLiteral)||LA45_0==74||(LA45_0>=101 && LA45_0<=102)||(LA45_0>=104 && LA45_0<=105)||LA45_0==109||(LA45_0>=111 && LA45_0<=112)||(LA45_0>=126 && LA45_0<=129)) ) {
                alt45=1;
            }
            else if ( (LA45_0==87) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // StateMachine.g:333:4: expression ( ',' expression )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_argumentList1799);
                    expression213=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression213.getTree());
                    // StateMachine.g:333:15: ( ',' expression )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==75) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // StateMachine.g:333:16: ',' expression
                    	    {
                    	    char_literal214=(Token)match(input,75,FOLLOW_75_in_argumentList1802); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal214_tree = (Object)adaptor.create(char_literal214);
                    	    adaptor.addChild(root_0, char_literal214_tree);
                    	    }
                    	    pushFollow(FOLLOW_expression_in_argumentList1804);
                    	    expression215=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression215.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // StateMachine.g:334:4: xsltLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_xsltLiteral_in_argumentList1811);
                    xsltLiteral216=xsltLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, xsltLiteral216.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argumentList"

    public static class literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // StateMachine.g:337:1: literal : ( integerLiteral | FloatingPointLiteral | StringLiteral | booleanLiteral | NullLiteral );
    public final StateMachineParser.literal_return literal() throws RecognitionException {
        StateMachineParser.literal_return retval = new StateMachineParser.literal_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token FloatingPointLiteral218=null;
        Token StringLiteral219=null;
        Token NullLiteral221=null;
        StateMachineParser.integerLiteral_return integerLiteral217 = null;

        StateMachineParser.booleanLiteral_return booleanLiteral220 = null;


        Object FloatingPointLiteral218_tree=null;
        Object StringLiteral219_tree=null;
        Object NullLiteral221_tree=null;

        try {
            // StateMachine.g:338:2: ( integerLiteral | FloatingPointLiteral | StringLiteral | booleanLiteral | NullLiteral )
            int alt46=5;
            switch ( input.LA(1) ) {
            case DecimalLiteral:
            case HexLiteral:
                {
                alt46=1;
                }
                break;
            case FloatingPointLiteral:
                {
                alt46=2;
                }
                break;
            case StringLiteral:
                {
                alt46=3;
                }
                break;
            case 111:
            case 112:
                {
                alt46=4;
                }
                break;
            case NullLiteral:
                {
                alt46=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // StateMachine.g:338:4: integerLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_integerLiteral_in_literal1823);
                    integerLiteral217=integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, integerLiteral217.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:339:4: FloatingPointLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    FloatingPointLiteral218=(Token)match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_literal1828); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FloatingPointLiteral218_tree = (Object)adaptor.create(FloatingPointLiteral218);
                    adaptor.addChild(root_0, FloatingPointLiteral218_tree);
                    }

                    }
                    break;
                case 3 :
                    // StateMachine.g:340:4: StringLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    StringLiteral219=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_literal1833); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    StringLiteral219_tree = (Object)adaptor.create(StringLiteral219);
                    adaptor.addChild(root_0, StringLiteral219_tree);
                    }

                    }
                    break;
                case 4 :
                    // StateMachine.g:341:4: booleanLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_booleanLiteral_in_literal1838);
                    booleanLiteral220=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booleanLiteral220.getTree());

                    }
                    break;
                case 5 :
                    // StateMachine.g:342:4: NullLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    NullLiteral221=(Token)match(input,NullLiteral,FOLLOW_NullLiteral_in_literal1843); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NullLiteral221_tree = (Object)adaptor.create(NullLiteral221);
                    adaptor.addChild(root_0, NullLiteral221_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class integerLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "integerLiteral"
    // StateMachine.g:345:1: integerLiteral : ( DecimalLiteral | HexLiteral );
    public final StateMachineParser.integerLiteral_return integerLiteral() throws RecognitionException {
        StateMachineParser.integerLiteral_return retval = new StateMachineParser.integerLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set222=null;

        Object set222_tree=null;

        try {
            // StateMachine.g:346:2: ( DecimalLiteral | HexLiteral )
            // StateMachine.g:
            {
            root_0 = (Object)adaptor.nil();

            set222=(Token)input.LT(1);
            if ( (input.LA(1)>=DecimalLiteral && input.LA(1)<=HexLiteral) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set222));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "integerLiteral"

    public static class booleanLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanLiteral"
    // StateMachine.g:350:1: booleanLiteral : ( 'true' | 'false' );
    public final StateMachineParser.booleanLiteral_return booleanLiteral() throws RecognitionException {
        StateMachineParser.booleanLiteral_return retval = new StateMachineParser.booleanLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set223=null;

        Object set223_tree=null;

        try {
            // StateMachine.g:351:2: ( 'true' | 'false' )
            // StateMachine.g:
            {
            root_0 = (Object)adaptor.nil();

            set223=(Token)input.LT(1);
            if ( (input.LA(1)>=111 && input.LA(1)<=112) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set223));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "booleanLiteral"

    public static class thenNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenNT"
    // StateMachine.g:354:1: thenNT : 'then' LBRACE thenStatements RBRACE -> ^( THEN_BLOCK[\"then\"] ^( STATEMENTS ( thenStatements )* ) ) ;
    public final StateMachineParser.thenNT_return thenNT() throws RecognitionException {
        StateMachineParser.thenNT_return retval = new StateMachineParser.thenNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal224=null;
        Token LBRACE225=null;
        Token RBRACE227=null;
        StateMachineParser.thenStatements_return thenStatements226 = null;


        Object string_literal224_tree=null;
        Object LBRACE225_tree=null;
        Object RBRACE227_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleTokenStream stream_113=new RewriteRuleTokenStream(adaptor,"token 113");
        RewriteRuleSubtreeStream stream_thenStatements=new RewriteRuleSubtreeStream(adaptor,"rule thenStatements");
        try {
            // StateMachine.g:357:8: ( 'then' LBRACE thenStatements RBRACE -> ^( THEN_BLOCK[\"then\"] ^( STATEMENTS ( thenStatements )* ) ) )
            // StateMachine.g:357:10: 'then' LBRACE thenStatements RBRACE
            {
            string_literal224=(Token)match(input,113,FOLLOW_113_in_thenNT1887); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_113.add(string_literal224);

            LBRACE225=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_thenNT1889); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE225);

            pushFollow(FOLLOW_thenStatements_in_thenNT1891);
            thenStatements226=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenStatements.add(thenStatements226.getTree());
            RBRACE227=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_thenNT1893); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE227);



            // AST REWRITE
            // elements: thenStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 358:2: -> ^( THEN_BLOCK[\"then\"] ^( STATEMENTS ( thenStatements )* ) )
            {
                // StateMachine.g:358:5: ^( THEN_BLOCK[\"then\"] ^( STATEMENTS ( thenStatements )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(THEN_BLOCK, "then"), root_1);

                // StateMachine.g:358:26: ^( STATEMENTS ( thenStatements )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // StateMachine.g:358:39: ( thenStatements )*
                while ( stream_thenStatements.hasNext() ) {
                    adaptor.addChild(root_2, stream_thenStatements.nextTree());

                }
                stream_thenStatements.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "thenNT"

    public static class bodyNT_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bodyNT"
    // StateMachine.g:361:1: bodyNT : 'body' LBRACE thenStatements RBRACE -> ^( BODY_BLOCK[\"body\"] ^( STATEMENTS ( thenStatements )* ) ) ;
    public final StateMachineParser.bodyNT_return bodyNT() throws RecognitionException {
        StateMachineParser.bodyNT_return retval = new StateMachineParser.bodyNT_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal228=null;
        Token LBRACE229=null;
        Token RBRACE231=null;
        StateMachineParser.thenStatements_return thenStatements230 = null;


        Object string_literal228_tree=null;
        Object LBRACE229_tree=null;
        Object RBRACE231_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_114=new RewriteRuleTokenStream(adaptor,"token 114");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_thenStatements=new RewriteRuleSubtreeStream(adaptor,"rule thenStatements");
        try {
            // StateMachine.g:361:8: ( 'body' LBRACE thenStatements RBRACE -> ^( BODY_BLOCK[\"body\"] ^( STATEMENTS ( thenStatements )* ) ) )
            // StateMachine.g:361:10: 'body' LBRACE thenStatements RBRACE
            {
            string_literal228=(Token)match(input,114,FOLLOW_114_in_bodyNT1919); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_114.add(string_literal228);

            LBRACE229=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_bodyNT1921); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE229);

            pushFollow(FOLLOW_thenStatements_in_bodyNT1923);
            thenStatements230=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenStatements.add(thenStatements230.getTree());
            RBRACE231=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_bodyNT1925); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE231);



            // AST REWRITE
            // elements: thenStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 362:2: -> ^( BODY_BLOCK[\"body\"] ^( STATEMENTS ( thenStatements )* ) )
            {
                // StateMachine.g:362:5: ^( BODY_BLOCK[\"body\"] ^( STATEMENTS ( thenStatements )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BODY_BLOCK, "body"), root_1);

                // StateMachine.g:362:26: ^( STATEMENTS ( thenStatements )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // StateMachine.g:362:39: ( thenStatements )*
                while ( stream_thenStatements.hasNext() ) {
                    adaptor.addChild(root_2, stream_thenStatements.nextTree());

                }
                stream_thenStatements.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "bodyNT"

    public static class thenStatements_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatements"
    // StateMachine.g:365:1: thenStatements : ( thenStatement )* ;
    public final StateMachineParser.thenStatements_return thenStatements() throws RecognitionException {
        StateMachineParser.thenStatements_return retval = new StateMachineParser.thenStatements_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.thenStatement_return thenStatement232 = null;



        try {
            // StateMachine.g:366:2: ( ( thenStatement )* )
            // StateMachine.g:366:4: ( thenStatement )*
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:366:4: ( thenStatement )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==Identifier||(LA47_0>=SEMICOLON && LA47_0<=LBRACE)||(LA47_0>=FloatingPointLiteral && LA47_0<=WS)||LA47_0==74||(LA47_0>=111 && LA47_0<=112)||(LA47_0>=115 && LA47_0<=119)||(LA47_0>=121 && LA47_0<=123)||(LA47_0>=125 && LA47_0<=129)) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // StateMachine.g:366:4: thenStatement
            	    {
            	    pushFollow(FOLLOW_thenStatement_in_thenStatements1952);
            	    thenStatement232=thenStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement232.getTree());

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "thenStatements"

    public static class thenStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatement"
    // StateMachine.g:369:1: thenStatement : ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement | emptyBodyStatement );
    public final StateMachineParser.thenStatement_return thenStatement() throws RecognitionException {
        StateMachineParser.thenStatement_return retval = new StateMachineParser.thenStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON234=null;
        StateMachineParser.localVariableDeclaration_return localVariableDeclaration233 = null;

        StateMachineParser.statement_return statement235 = null;

        StateMachineParser.emptyBodyStatement_return emptyBodyStatement236 = null;


        Object SEMICOLON234_tree=null;

        try {
            // StateMachine.g:370:2: ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement | emptyBodyStatement )
            int alt48=3;
            alt48 = dfa48.predict(input);
            switch (alt48) {
                case 1 :
                    // StateMachine.g:370:4: ( type identifier )=> localVariableDeclaration SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_localVariableDeclaration_in_thenStatement1971);
                    localVariableDeclaration233=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, localVariableDeclaration233.getTree());
                    SEMICOLON234=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_thenStatement1973); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON234_tree = (Object)adaptor.create(SEMICOLON234);
                    adaptor.addChild(root_0, SEMICOLON234_tree);
                    }

                    }
                    break;
                case 2 :
                    // StateMachine.g:371:4: statement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_statement_in_thenStatement1978);
                    statement235=statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement235.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:371:16: emptyBodyStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_emptyBodyStatement_in_thenStatement1982);
                    emptyBodyStatement236=emptyBodyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyBodyStatement236.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "thenStatement"

    public static class statement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // StateMachine.g:374:1: statement : ( lineStatement | blockStatement );
    public final StateMachineParser.statement_return statement() throws RecognitionException {
        StateMachineParser.statement_return retval = new StateMachineParser.statement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.lineStatement_return lineStatement237 = null;

        StateMachineParser.blockStatement_return blockStatement238 = null;



        try {
            // StateMachine.g:375:2: ( lineStatement | blockStatement )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==Identifier||(LA49_0>=SEMICOLON && LA49_0<=StringLiteral)||(LA49_0>=FloatingPointLiteral && LA49_0<=HexLiteral)||LA49_0==74||(LA49_0>=111 && LA49_0<=112)||(LA49_0>=115 && LA49_0<=118)||(LA49_0>=126 && LA49_0<=129)) ) {
                alt49=1;
            }
            else if ( (LA49_0==LBRACE||LA49_0==119||(LA49_0>=121 && LA49_0<=123)||LA49_0==125) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // StateMachine.g:375:4: lineStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_lineStatement_in_statement1994);
                    lineStatement237=lineStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lineStatement237.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:375:20: blockStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_blockStatement_in_statement1998);
                    blockStatement238=blockStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, blockStatement238.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statement"

    public static class lineStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lineStatement"
    // StateMachine.g:378:1: lineStatement : ( emptyStatement | statementExpression SEMICOLON | returnStatement | breakStatement | continueStatement | throwStatement );
    public final StateMachineParser.lineStatement_return lineStatement() throws RecognitionException {
        StateMachineParser.lineStatement_return retval = new StateMachineParser.lineStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON241=null;
        StateMachineParser.emptyStatement_return emptyStatement239 = null;

        StateMachineParser.statementExpression_return statementExpression240 = null;

        StateMachineParser.returnStatement_return returnStatement242 = null;

        StateMachineParser.breakStatement_return breakStatement243 = null;

        StateMachineParser.continueStatement_return continueStatement244 = null;

        StateMachineParser.throwStatement_return throwStatement245 = null;


        Object SEMICOLON241_tree=null;

        try {
            // StateMachine.g:379:2: ( emptyStatement | statementExpression SEMICOLON | returnStatement | breakStatement | continueStatement | throwStatement )
            int alt50=6;
            switch ( input.LA(1) ) {
            case SEMICOLON:
                {
                alt50=1;
                }
                break;
            case Identifier:
            case StringLiteral:
            case FloatingPointLiteral:
            case NullLiteral:
            case DecimalLiteral:
            case HexLiteral:
            case 74:
            case 111:
            case 112:
            case 126:
            case 127:
            case 128:
            case 129:
                {
                alt50=2;
                }
                break;
            case 115:
                {
                alt50=3;
                }
                break;
            case 116:
                {
                alt50=4;
                }
                break;
            case 117:
                {
                alt50=5;
                }
                break;
            case 118:
                {
                alt50=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 50, 0, input);

                throw nvae;
            }

            switch (alt50) {
                case 1 :
                    // StateMachine.g:379:4: emptyStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_emptyStatement_in_lineStatement2010);
                    emptyStatement239=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyStatement239.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:380:4: statementExpression SEMICOLON
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_statementExpression_in_lineStatement2015);
                    statementExpression240=statementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpression240.getTree());
                    SEMICOLON241=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_lineStatement2017); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON241_tree = (Object)adaptor.create(SEMICOLON241);
                    adaptor.addChild(root_0, SEMICOLON241_tree);
                    }

                    }
                    break;
                case 3 :
                    // StateMachine.g:381:4: returnStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_lineStatement2022);
                    returnStatement242=returnStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnStatement242.getTree());

                    }
                    break;
                case 4 :
                    // StateMachine.g:382:4: breakStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_breakStatement_in_lineStatement2027);
                    breakStatement243=breakStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, breakStatement243.getTree());

                    }
                    break;
                case 5 :
                    // StateMachine.g:383:4: continueStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_continueStatement_in_lineStatement2032);
                    continueStatement244=continueStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continueStatement244.getTree());

                    }
                    break;
                case 6 :
                    // StateMachine.g:384:4: throwStatement
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_throwStatement_in_lineStatement2037);
                    throwStatement245=throwStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwStatement245.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "lineStatement"

    public static class blockStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "blockStatement"
    // StateMachine.g:387:1: blockStatement : ( ifRule | whileRule | forRule | block | tryCatchFinally );
    public final StateMachineParser.blockStatement_return blockStatement() throws RecognitionException {
        StateMachineParser.blockStatement_return retval = new StateMachineParser.blockStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.ifRule_return ifRule246 = null;

        StateMachineParser.whileRule_return whileRule247 = null;

        StateMachineParser.forRule_return forRule248 = null;

        StateMachineParser.block_return block249 = null;

        StateMachineParser.tryCatchFinally_return tryCatchFinally250 = null;



        try {
            // StateMachine.g:388:2: ( ifRule | whileRule | forRule | block | tryCatchFinally )
            int alt51=5;
            switch ( input.LA(1) ) {
            case 119:
                {
                alt51=1;
                }
                break;
            case 121:
                {
                alt51=2;
                }
                break;
            case 122:
                {
                alt51=3;
                }
                break;
            case LBRACE:
                {
                alt51=4;
                }
                break;
            case 123:
            case 125:
                {
                alt51=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }

            switch (alt51) {
                case 1 :
                    // StateMachine.g:388:4: ifRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_ifRule_in_blockStatement2049);
                    ifRule246=ifRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ifRule246.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:389:4: whileRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_whileRule_in_blockStatement2054);
                    whileRule247=whileRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, whileRule247.getTree());

                    }
                    break;
                case 3 :
                    // StateMachine.g:390:4: forRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_forRule_in_blockStatement2059);
                    forRule248=forRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, forRule248.getTree());

                    }
                    break;
                case 4 :
                    // StateMachine.g:391:4: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_blockStatement2064);
                    block249=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block249.getTree());

                    }
                    break;
                case 5 :
                    // StateMachine.g:392:4: tryCatchFinally
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_tryCatchFinally_in_blockStatement2069);
                    tryCatchFinally250=tryCatchFinally();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tryCatchFinally250.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "blockStatement"

    public static class emptyStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyStatement"
    // StateMachine.g:395:1: emptyStatement : SEMICOLON ;
    public final StateMachineParser.emptyStatement_return emptyStatement() throws RecognitionException {
        StateMachineParser.emptyStatement_return retval = new StateMachineParser.emptyStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON251=null;

        Object SEMICOLON251_tree=null;

        try {
            // StateMachine.g:396:2: ( SEMICOLON )
            // StateMachine.g:396:4: SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            SEMICOLON251=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_emptyStatement2081); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON251_tree = (Object)adaptor.create(SEMICOLON251);
            adaptor.addChild(root_0, SEMICOLON251_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "emptyStatement"

    public static class emptyBodyStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyBodyStatement"
    // StateMachine.g:399:1: emptyBodyStatement : WS ;
    public final StateMachineParser.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException {
        StateMachineParser.emptyBodyStatement_return retval = new StateMachineParser.emptyBodyStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WS252=null;

        Object WS252_tree=null;

        try {
            // StateMachine.g:400:2: ( WS )
            // StateMachine.g:400:4: WS
            {
            root_0 = (Object)adaptor.nil();

            WS252=(Token)match(input,WS,FOLLOW_WS_in_emptyBodyStatement2093); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WS252_tree = (Object)adaptor.create(WS252);
            adaptor.addChild(root_0, WS252_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "emptyBodyStatement"

    public static class statementExpression_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpression"
    // StateMachine.g:403:1: statementExpression : ( ( methodName '(' )=> methodName argumentsSuffix ( ( primarySuffix )* ASSIGN expression )? | primaryExpression ASSIGN expression );
    public final StateMachineParser.statementExpression_return statementExpression() throws RecognitionException {
        StateMachineParser.statementExpression_return retval = new StateMachineParser.statementExpression_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASSIGN256=null;
        Token ASSIGN259=null;
        StateMachineParser.methodName_return methodName253 = null;

        StateMachineParser.argumentsSuffix_return argumentsSuffix254 = null;

        StateMachineParser.primarySuffix_return primarySuffix255 = null;

        StateMachineParser.expression_return expression257 = null;

        StateMachineParser.primaryExpression_return primaryExpression258 = null;

        StateMachineParser.expression_return expression260 = null;


        Object ASSIGN256_tree=null;
        Object ASSIGN259_tree=null;

        try {
            // StateMachine.g:404:2: ( ( methodName '(' )=> methodName argumentsSuffix ( ( primarySuffix )* ASSIGN expression )? | primaryExpression ASSIGN expression )
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // StateMachine.g:404:4: ( methodName '(' )=> methodName argumentsSuffix ( ( primarySuffix )* ASSIGN expression )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_methodName_in_statementExpression2112);
                    methodName253=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, methodName253.getTree());
                    pushFollow(FOLLOW_argumentsSuffix_in_statementExpression2114);
                    argumentsSuffix254=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argumentsSuffix254.getTree());
                    // StateMachine.g:405:2: ( ( primarySuffix )* ASSIGN expression )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==ASSIGN||LA53_0==ANNOTATE||LA53_0==102||LA53_0==110) ) {
                        alt53=1;
                    }
                    switch (alt53) {
                        case 1 :
                            // StateMachine.g:405:4: ( primarySuffix )* ASSIGN expression
                            {
                            // StateMachine.g:405:4: ( primarySuffix )*
                            loop52:
                            do {
                                int alt52=2;
                                int LA52_0 = input.LA(1);

                                if ( (LA52_0==ANNOTATE||LA52_0==102||LA52_0==110) ) {
                                    alt52=1;
                                }


                                switch (alt52) {
                            	case 1 :
                            	    // StateMachine.g:405:4: primarySuffix
                            	    {
                            	    pushFollow(FOLLOW_primarySuffix_in_statementExpression2119);
                            	    primarySuffix255=primarySuffix();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, primarySuffix255.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop52;
                                }
                            } while (true);

                            ASSIGN256=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_statementExpression2122); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            ASSIGN256_tree = (Object)adaptor.create(ASSIGN256);
                            adaptor.addChild(root_0, ASSIGN256_tree);
                            }
                            pushFollow(FOLLOW_expression_in_statementExpression2124);
                            expression257=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression257.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // StateMachine.g:406:4: primaryExpression ASSIGN expression
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_primaryExpression_in_statementExpression2131);
                    primaryExpression258=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primaryExpression258.getTree());
                    ASSIGN259=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_statementExpression2133); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN259_tree = (Object)adaptor.create(ASSIGN259);
                    adaptor.addChild(root_0, ASSIGN259_tree);
                    }
                    pushFollow(FOLLOW_expression_in_statementExpression2135);
                    expression260=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression260.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementExpression"

    public static class returnStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "returnStatement"
    // StateMachine.g:409:1: returnStatement : 'return' ( expression SEMICOLON | SEMICOLON ) ;
    public final StateMachineParser.returnStatement_return returnStatement() throws RecognitionException {
        StateMachineParser.returnStatement_return retval = new StateMachineParser.returnStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal261=null;
        Token SEMICOLON263=null;
        Token SEMICOLON264=null;
        StateMachineParser.expression_return expression262 = null;


        Object string_literal261_tree=null;
        Object SEMICOLON263_tree=null;
        Object SEMICOLON264_tree=null;

        try {
            // StateMachine.g:410:2: ( 'return' ( expression SEMICOLON | SEMICOLON ) )
            // StateMachine.g:410:4: 'return' ( expression SEMICOLON | SEMICOLON )
            {
            root_0 = (Object)adaptor.nil();

            string_literal261=(Token)match(input,115,FOLLOW_115_in_returnStatement2147); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal261_tree = (Object)adaptor.create(string_literal261);
            adaptor.addChild(root_0, string_literal261_tree);
            }
            // StateMachine.g:410:13: ( expression SEMICOLON | SEMICOLON )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==Identifier||(LA55_0>=StringLiteral && LA55_0<=LBRACE)||(LA55_0>=EQ && LA55_0<=HexLiteral)||LA55_0==74||(LA55_0>=101 && LA55_0<=102)||(LA55_0>=104 && LA55_0<=105)||LA55_0==109||(LA55_0>=111 && LA55_0<=112)||(LA55_0>=126 && LA55_0<=129)) ) {
                alt55=1;
            }
            else if ( (LA55_0==SEMICOLON) ) {
                alt55=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // StateMachine.g:410:14: expression SEMICOLON
                    {
                    pushFollow(FOLLOW_expression_in_returnStatement2150);
                    expression262=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression262.getTree());
                    SEMICOLON263=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON263_tree = (Object)adaptor.create(SEMICOLON263);
                    adaptor.addChild(root_0, SEMICOLON263_tree);
                    }

                    }
                    break;
                case 2 :
                    // StateMachine.g:410:37: SEMICOLON
                    {
                    SEMICOLON264=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2156); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMICOLON264_tree = (Object)adaptor.create(SEMICOLON264);
                    adaptor.addChild(root_0, SEMICOLON264_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "returnStatement"

    public static class breakStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "breakStatement"
    // StateMachine.g:413:1: breakStatement : 'break' SEMICOLON ;
    public final StateMachineParser.breakStatement_return breakStatement() throws RecognitionException {
        StateMachineParser.breakStatement_return retval = new StateMachineParser.breakStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal265=null;
        Token SEMICOLON266=null;

        Object string_literal265_tree=null;
        Object SEMICOLON266_tree=null;

        try {
            // StateMachine.g:414:2: ( 'break' SEMICOLON )
            // StateMachine.g:414:4: 'break' SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            string_literal265=(Token)match(input,116,FOLLOW_116_in_breakStatement2169); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal265_tree = (Object)adaptor.create(string_literal265);
            adaptor.addChild(root_0, string_literal265_tree);
            }
            SEMICOLON266=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_breakStatement2171); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON266_tree = (Object)adaptor.create(SEMICOLON266);
            adaptor.addChild(root_0, SEMICOLON266_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "breakStatement"

    public static class continueStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continueStatement"
    // StateMachine.g:417:1: continueStatement : 'continue' SEMICOLON ;
    public final StateMachineParser.continueStatement_return continueStatement() throws RecognitionException {
        StateMachineParser.continueStatement_return retval = new StateMachineParser.continueStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal267=null;
        Token SEMICOLON268=null;

        Object string_literal267_tree=null;
        Object SEMICOLON268_tree=null;

        try {
            // StateMachine.g:418:2: ( 'continue' SEMICOLON )
            // StateMachine.g:418:4: 'continue' SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            string_literal267=(Token)match(input,117,FOLLOW_117_in_continueStatement2183); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal267_tree = (Object)adaptor.create(string_literal267);
            adaptor.addChild(root_0, string_literal267_tree);
            }
            SEMICOLON268=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_continueStatement2185); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON268_tree = (Object)adaptor.create(SEMICOLON268);
            adaptor.addChild(root_0, SEMICOLON268_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "continueStatement"

    public static class throwStatement_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "throwStatement"
    // StateMachine.g:421:1: throwStatement : 'throw' expression SEMICOLON ;
    public final StateMachineParser.throwStatement_return throwStatement() throws RecognitionException {
        StateMachineParser.throwStatement_return retval = new StateMachineParser.throwStatement_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal269=null;
        Token SEMICOLON271=null;
        StateMachineParser.expression_return expression270 = null;


        Object string_literal269_tree=null;
        Object SEMICOLON271_tree=null;

        try {
            // StateMachine.g:422:2: ( 'throw' expression SEMICOLON )
            // StateMachine.g:422:4: 'throw' expression SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            string_literal269=(Token)match(input,118,FOLLOW_118_in_throwStatement2197); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal269_tree = (Object)adaptor.create(string_literal269);
            adaptor.addChild(root_0, string_literal269_tree);
            }
            pushFollow(FOLLOW_expression_in_throwStatement2199);
            expression270=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression270.getTree());
            SEMICOLON271=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_throwStatement2201); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON271_tree = (Object)adaptor.create(SEMICOLON271);
            adaptor.addChild(root_0, SEMICOLON271_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "throwStatement"

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // StateMachine.g:425:1: block : LBRACE ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement )* RBRACE ;
    public final StateMachineParser.block_return block() throws RecognitionException {
        StateMachineParser.block_return retval = new StateMachineParser.block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRACE272=null;
        Token SEMICOLON274=null;
        Token RBRACE276=null;
        StateMachineParser.localVariableDeclaration_return localVariableDeclaration273 = null;

        StateMachineParser.statement_return statement275 = null;


        Object LBRACE272_tree=null;
        Object SEMICOLON274_tree=null;
        Object RBRACE276_tree=null;

        try {
            // StateMachine.g:425:7: ( LBRACE ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement )* RBRACE )
            // StateMachine.g:425:9: LBRACE ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement )* RBRACE
            {
            root_0 = (Object)adaptor.nil();

            LBRACE272=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_block2212); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LBRACE272_tree = (Object)adaptor.create(LBRACE272);
            adaptor.addChild(root_0, LBRACE272_tree);
            }
            // StateMachine.g:425:16: ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement )*
            loop56:
            do {
                int alt56=3;
                alt56 = dfa56.predict(input);
                switch (alt56) {
            	case 1 :
            	    // StateMachine.g:425:18: ( type identifier )=> localVariableDeclaration SEMICOLON
            	    {
            	    pushFollow(FOLLOW_localVariableDeclaration_in_block2223);
            	    localVariableDeclaration273=localVariableDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, localVariableDeclaration273.getTree());
            	    SEMICOLON274=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_block2225); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMICOLON274_tree = (Object)adaptor.create(SEMICOLON274);
            	    adaptor.addChild(root_0, SEMICOLON274_tree);
            	    }

            	    }
            	    break;
            	case 2 :
            	    // StateMachine.g:425:75: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block2229);
            	    statement275=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement275.getTree());

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            RBRACE276=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_block2233); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RBRACE276_tree = (Object)adaptor.create(RBRACE276);
            adaptor.addChild(root_0, RBRACE276_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "block"

    public static class statementExpressionList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpressionList"
    // StateMachine.g:428:1: statementExpressionList : statementExpression ( ',' statementExpression )* ;
    public final StateMachineParser.statementExpressionList_return statementExpressionList() throws RecognitionException {
        StateMachineParser.statementExpressionList_return retval = new StateMachineParser.statementExpressionList_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal278=null;
        StateMachineParser.statementExpression_return statementExpression277 = null;

        StateMachineParser.statementExpression_return statementExpression279 = null;


        Object char_literal278_tree=null;

        try {
            // StateMachine.g:429:2: ( statementExpression ( ',' statementExpression )* )
            // StateMachine.g:429:4: statementExpression ( ',' statementExpression )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_statementExpression_in_statementExpressionList2245);
            statementExpression277=statementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpression277.getTree());
            // StateMachine.g:429:24: ( ',' statementExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==75) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // StateMachine.g:429:25: ',' statementExpression
            	    {
            	    char_literal278=(Token)match(input,75,FOLLOW_75_in_statementExpressionList2248); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal278_tree = (Object)adaptor.create(char_literal278);
            	    adaptor.addChild(root_0, char_literal278_tree);
            	    }
            	    pushFollow(FOLLOW_statementExpression_in_statementExpressionList2250);
            	    statementExpression279=statementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpression279.getTree());

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementExpressionList"

    public static class ifRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ifRule"
    // StateMachine.g:432:1: ifRule : 'if' '(' expression ')' statement ( 'else' statement )? ;
    public final StateMachineParser.ifRule_return ifRule() throws RecognitionException {
        StateMachineParser.ifRule_return retval = new StateMachineParser.ifRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal280=null;
        Token char_literal281=null;
        Token char_literal283=null;
        Token string_literal285=null;
        StateMachineParser.expression_return expression282 = null;

        StateMachineParser.statement_return statement284 = null;

        StateMachineParser.statement_return statement286 = null;


        Object string_literal280_tree=null;
        Object char_literal281_tree=null;
        Object char_literal283_tree=null;
        Object string_literal285_tree=null;

        try {
            // StateMachine.g:432:8: ( 'if' '(' expression ')' statement ( 'else' statement )? )
            // StateMachine.g:432:10: 'if' '(' expression ')' statement ( 'else' statement )?
            {
            root_0 = (Object)adaptor.nil();

            string_literal280=(Token)match(input,119,FOLLOW_119_in_ifRule2263); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal280_tree = (Object)adaptor.create(string_literal280);
            adaptor.addChild(root_0, string_literal280_tree);
            }
            char_literal281=(Token)match(input,74,FOLLOW_74_in_ifRule2265); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal281_tree = (Object)adaptor.create(char_literal281);
            adaptor.addChild(root_0, char_literal281_tree);
            }
            pushFollow(FOLLOW_expression_in_ifRule2267);
            expression282=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression282.getTree());
            char_literal283=(Token)match(input,76,FOLLOW_76_in_ifRule2269); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal283_tree = (Object)adaptor.create(char_literal283);
            adaptor.addChild(root_0, char_literal283_tree);
            }
            pushFollow(FOLLOW_statement_in_ifRule2271);
            statement284=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statement284.getTree());
            // StateMachine.g:433:2: ( 'else' statement )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==120) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // StateMachine.g:433:3: 'else' statement
                    {
                    string_literal285=(Token)match(input,120,FOLLOW_120_in_ifRule2275); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    string_literal285_tree = (Object)adaptor.create(string_literal285);
                    adaptor.addChild(root_0, string_literal285_tree);
                    }
                    pushFollow(FOLLOW_statement_in_ifRule2277);
                    statement286=statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement286.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ifRule"

    public static class whileRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whileRule"
    // StateMachine.g:436:1: whileRule : 'while' '(' expression ')' statement ;
    public final StateMachineParser.whileRule_return whileRule() throws RecognitionException {
        StateMachineParser.whileRule_return retval = new StateMachineParser.whileRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal287=null;
        Token char_literal288=null;
        Token char_literal290=null;
        StateMachineParser.expression_return expression289 = null;

        StateMachineParser.statement_return statement291 = null;


        Object string_literal287_tree=null;
        Object char_literal288_tree=null;
        Object char_literal290_tree=null;

        try {
            // StateMachine.g:436:11: ( 'while' '(' expression ')' statement )
            // StateMachine.g:436:13: 'while' '(' expression ')' statement
            {
            root_0 = (Object)adaptor.nil();

            string_literal287=(Token)match(input,121,FOLLOW_121_in_whileRule2290); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal287_tree = (Object)adaptor.create(string_literal287);
            adaptor.addChild(root_0, string_literal287_tree);
            }
            char_literal288=(Token)match(input,74,FOLLOW_74_in_whileRule2292); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal288_tree = (Object)adaptor.create(char_literal288);
            adaptor.addChild(root_0, char_literal288_tree);
            }
            pushFollow(FOLLOW_expression_in_whileRule2294);
            expression289=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression289.getTree());
            char_literal290=(Token)match(input,76,FOLLOW_76_in_whileRule2296); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal290_tree = (Object)adaptor.create(char_literal290);
            adaptor.addChild(root_0, char_literal290_tree);
            }
            pushFollow(FOLLOW_statement_in_whileRule2298);
            statement291=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statement291.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "whileRule"

    public static class forRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "forRule"
    // StateMachine.g:439:1: forRule : 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList )? SEMICOLON ( expression )? SEMICOLON ( statementExpressionList )? ')' statement ;
    public final StateMachineParser.forRule_return forRule() throws RecognitionException {
        StateMachineParser.forRule_return retval = new StateMachineParser.forRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal292=null;
        Token char_literal293=null;
        Token SEMICOLON296=null;
        Token SEMICOLON298=null;
        Token char_literal300=null;
        StateMachineParser.localVariableDeclaration_return localVariableDeclaration294 = null;

        StateMachineParser.statementExpressionList_return statementExpressionList295 = null;

        StateMachineParser.expression_return expression297 = null;

        StateMachineParser.statementExpressionList_return statementExpressionList299 = null;

        StateMachineParser.statement_return statement301 = null;


        Object string_literal292_tree=null;
        Object char_literal293_tree=null;
        Object SEMICOLON296_tree=null;
        Object SEMICOLON298_tree=null;
        Object char_literal300_tree=null;

        try {
            // StateMachine.g:439:9: ( 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList )? SEMICOLON ( expression )? SEMICOLON ( statementExpressionList )? ')' statement )
            // StateMachine.g:439:11: 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList )? SEMICOLON ( expression )? SEMICOLON ( statementExpressionList )? ')' statement
            {
            root_0 = (Object)adaptor.nil();

            string_literal292=(Token)match(input,122,FOLLOW_122_in_forRule2309); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal292_tree = (Object)adaptor.create(string_literal292);
            adaptor.addChild(root_0, string_literal292_tree);
            }
            char_literal293=(Token)match(input,74,FOLLOW_74_in_forRule2311); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal293_tree = (Object)adaptor.create(char_literal293);
            adaptor.addChild(root_0, char_literal293_tree);
            }
            // StateMachine.g:440:2: ( ( type identifier )=> localVariableDeclaration | statementExpressionList )?
            int alt59=3;
            alt59 = dfa59.predict(input);
            switch (alt59) {
                case 1 :
                    // StateMachine.g:440:4: ( type identifier )=> localVariableDeclaration
                    {
                    pushFollow(FOLLOW_localVariableDeclaration_in_forRule2323);
                    localVariableDeclaration294=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, localVariableDeclaration294.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:440:51: statementExpressionList
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2327);
                    statementExpressionList295=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpressionList295.getTree());

                    }
                    break;

            }

            SEMICOLON296=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2331); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON296_tree = (Object)adaptor.create(SEMICOLON296);
            adaptor.addChild(root_0, SEMICOLON296_tree);
            }
            // StateMachine.g:441:2: ( expression )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==Identifier||(LA60_0>=StringLiteral && LA60_0<=LBRACE)||(LA60_0>=EQ && LA60_0<=HexLiteral)||LA60_0==74||(LA60_0>=101 && LA60_0<=102)||(LA60_0>=104 && LA60_0<=105)||LA60_0==109||(LA60_0>=111 && LA60_0<=112)||(LA60_0>=126 && LA60_0<=129)) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // StateMachine.g:441:3: expression
                    {
                    pushFollow(FOLLOW_expression_in_forRule2335);
                    expression297=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression297.getTree());

                    }
                    break;

            }

            SEMICOLON298=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2339); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON298_tree = (Object)adaptor.create(SEMICOLON298);
            adaptor.addChild(root_0, SEMICOLON298_tree);
            }
            // StateMachine.g:442:2: ( statementExpressionList )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==Identifier||LA61_0==StringLiteral||(LA61_0>=FloatingPointLiteral && LA61_0<=HexLiteral)||LA61_0==74||(LA61_0>=111 && LA61_0<=112)||(LA61_0>=126 && LA61_0<=129)) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // StateMachine.g:442:3: statementExpressionList
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2343);
                    statementExpressionList299=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpressionList299.getTree());

                    }
                    break;

            }

            char_literal300=(Token)match(input,76,FOLLOW_76_in_forRule2347); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal300_tree = (Object)adaptor.create(char_literal300);
            adaptor.addChild(root_0, char_literal300_tree);
            }
            pushFollow(FOLLOW_statement_in_forRule2350);
            statement301=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statement301.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "forRule"

    public static class tryCatchFinally_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tryCatchFinally"
    // StateMachine.g:446:1: tryCatchFinally : ( tryRule ( catchRule ( finallyRule )? ) | finallyRule );
    public final StateMachineParser.tryCatchFinally_return tryCatchFinally() throws RecognitionException {
        StateMachineParser.tryCatchFinally_return retval = new StateMachineParser.tryCatchFinally_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.tryRule_return tryRule302 = null;

        StateMachineParser.catchRule_return catchRule303 = null;

        StateMachineParser.finallyRule_return finallyRule304 = null;

        StateMachineParser.finallyRule_return finallyRule305 = null;



        try {
            // StateMachine.g:447:2: ( tryRule ( catchRule ( finallyRule )? ) | finallyRule )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==123) ) {
                alt63=1;
            }
            else if ( (LA63_0==125) ) {
                alt63=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // StateMachine.g:447:4: tryRule ( catchRule ( finallyRule )? )
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_tryRule_in_tryCatchFinally2362);
                    tryRule302=tryRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tryRule302.getTree());
                    // StateMachine.g:448:2: ( catchRule ( finallyRule )? )
                    // StateMachine.g:448:4: catchRule ( finallyRule )?
                    {
                    pushFollow(FOLLOW_catchRule_in_tryCatchFinally2368);
                    catchRule303=catchRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, catchRule303.getTree());
                    // StateMachine.g:448:14: ( finallyRule )?
                    int alt62=2;
                    alt62 = dfa62.predict(input);
                    switch (alt62) {
                        case 1 :
                            // StateMachine.g:448:14: finallyRule
                            {
                            pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2370);
                            finallyRule304=finallyRule();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, finallyRule304.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // StateMachine.g:449:4: finallyRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2378);
                    finallyRule305=finallyRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, finallyRule305.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "tryCatchFinally"

    public static class tryRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tryRule"
    // StateMachine.g:452:1: tryRule : 'try' block ;
    public final StateMachineParser.tryRule_return tryRule() throws RecognitionException {
        StateMachineParser.tryRule_return retval = new StateMachineParser.tryRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal306=null;
        StateMachineParser.block_return block307 = null;


        Object string_literal306_tree=null;

        try {
            // StateMachine.g:452:9: ( 'try' block )
            // StateMachine.g:452:11: 'try' block
            {
            root_0 = (Object)adaptor.nil();

            string_literal306=(Token)match(input,123,FOLLOW_123_in_tryRule2389); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal306_tree = (Object)adaptor.create(string_literal306);
            adaptor.addChild(root_0, string_literal306_tree);
            }
            pushFollow(FOLLOW_block_in_tryRule2391);
            block307=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, block307.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "tryRule"

    public static class catchRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "catchRule"
    // StateMachine.g:455:1: catchRule : 'catch' '(' type identifier ')' block ;
    public final StateMachineParser.catchRule_return catchRule() throws RecognitionException {
        StateMachineParser.catchRule_return retval = new StateMachineParser.catchRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal308=null;
        Token char_literal309=null;
        Token char_literal312=null;
        StateMachineParser.type_return type310 = null;

        StateMachineParser.identifier_return identifier311 = null;

        StateMachineParser.block_return block313 = null;


        Object string_literal308_tree=null;
        Object char_literal309_tree=null;
        Object char_literal312_tree=null;

        try {
            // StateMachine.g:455:10: ( 'catch' '(' type identifier ')' block )
            // StateMachine.g:455:12: 'catch' '(' type identifier ')' block
            {
            root_0 = (Object)adaptor.nil();

            string_literal308=(Token)match(input,124,FOLLOW_124_in_catchRule2401); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal308_tree = (Object)adaptor.create(string_literal308);
            adaptor.addChild(root_0, string_literal308_tree);
            }
            char_literal309=(Token)match(input,74,FOLLOW_74_in_catchRule2403); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal309_tree = (Object)adaptor.create(char_literal309);
            adaptor.addChild(root_0, char_literal309_tree);
            }
            pushFollow(FOLLOW_type_in_catchRule2405);
            type310=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type310.getTree());
            pushFollow(FOLLOW_identifier_in_catchRule2407);
            identifier311=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier311.getTree());
            char_literal312=(Token)match(input,76,FOLLOW_76_in_catchRule2409); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal312_tree = (Object)adaptor.create(char_literal312);
            adaptor.addChild(root_0, char_literal312_tree);
            }
            pushFollow(FOLLOW_block_in_catchRule2411);
            block313=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, block313.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "catchRule"

    public static class finallyRule_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "finallyRule"
    // StateMachine.g:458:1: finallyRule : 'finally' block ;
    public final StateMachineParser.finallyRule_return finallyRule() throws RecognitionException {
        StateMachineParser.finallyRule_return retval = new StateMachineParser.finallyRule_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal314=null;
        StateMachineParser.block_return block315 = null;


        Object string_literal314_tree=null;

        try {
            // StateMachine.g:458:13: ( 'finally' block )
            // StateMachine.g:458:15: 'finally' block
            {
            root_0 = (Object)adaptor.nil();

            string_literal314=(Token)match(input,125,FOLLOW_125_in_finallyRule2422); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal314_tree = (Object)adaptor.create(string_literal314);
            adaptor.addChild(root_0, string_literal314_tree);
            }
            pushFollow(FOLLOW_block_in_finallyRule2424);
            block315=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, block315.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "finallyRule"

    public static class semiColon_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "semiColon"
    // StateMachine.g:461:1: semiColon : SEMICOLON ;
    public final StateMachineParser.semiColon_return semiColon() throws RecognitionException {
        StateMachineParser.semiColon_return retval = new StateMachineParser.semiColon_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token SEMICOLON316=null;

        Object SEMICOLON316_tree=null;

        try {
            // StateMachine.g:462:2: ( SEMICOLON )
            // StateMachine.g:462:4: SEMICOLON
            {
            root_0 = (Object)adaptor.nil();

            SEMICOLON316=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_semiColon2435); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON316_tree = (Object)adaptor.create(SEMICOLON316);
            adaptor.addChild(root_0, SEMICOLON316_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "semiColon"

    public static class name_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // StateMachine.g:465:1: name : identifier ( '.' identifier )* -> ^( NAME[\"name\"] ( identifier )* ) ;
    public final StateMachineParser.name_return name() throws RecognitionException {
        StateMachineParser.name_return retval = new StateMachineParser.name_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal318=null;
        StateMachineParser.identifier_return identifier317 = null;

        StateMachineParser.identifier_return identifier319 = null;


        Object char_literal318_tree=null;
        RewriteRuleTokenStream stream_110=new RewriteRuleTokenStream(adaptor,"token 110");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // StateMachine.g:465:6: ( identifier ( '.' identifier )* -> ^( NAME[\"name\"] ( identifier )* ) )
            // StateMachine.g:465:8: identifier ( '.' identifier )*
            {
            pushFollow(FOLLOW_identifier_in_name2446);
            identifier317=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(identifier317.getTree());
            // StateMachine.g:465:19: ( '.' identifier )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==110) ) {
                    int LA64_2 = input.LA(2);

                    if ( (LA64_2==Identifier) ) {
                        alt64=1;
                    }


                }


                switch (alt64) {
            	case 1 :
            	    // StateMachine.g:465:20: '.' identifier
            	    {
            	    char_literal318=(Token)match(input,110,FOLLOW_110_in_name2449); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_110.add(char_literal318);

            	    pushFollow(FOLLOW_identifier_in_name2451);
            	    identifier319=identifier();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_identifier.add(identifier319.getTree());

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);



            // AST REWRITE
            // elements: identifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 466:2: -> ^( NAME[\"name\"] ( identifier )* )
            {
                // StateMachine.g:466:5: ^( NAME[\"name\"] ( identifier )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAME, "name"), root_1);

                // StateMachine.g:466:20: ( identifier )*
                while ( stream_identifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_identifier.nextTree());

                }
                stream_identifier.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "name"

    public static class dot_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dot"
    // StateMachine.g:469:1: dot : '.' ;
    public final StateMachineParser.dot_return dot() throws RecognitionException {
        StateMachineParser.dot_return retval = new StateMachineParser.dot_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal320=null;

        Object char_literal320_tree=null;

        try {
            // StateMachine.g:469:5: ( '.' )
            // StateMachine.g:469:7: '.'
            {
            root_0 = (Object)adaptor.nil();

            char_literal320=(Token)match(input,110,FOLLOW_110_in_dot2475); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal320_tree = (Object)adaptor.create(char_literal320);
            adaptor.addChild(root_0, char_literal320_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dot"

    public static class expressionName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressionName"
    // StateMachine.g:472:1: expressionName : name ;
    public final StateMachineParser.expressionName_return expressionName() throws RecognitionException {
        StateMachineParser.expressionName_return retval = new StateMachineParser.expressionName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.name_return name321 = null;



        try {
            // StateMachine.g:473:2: ( name )
            // StateMachine.g:473:4: name
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_name_in_expressionName2487);
            name321=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name321.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expressionName"

    public static class methodName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "methodName"
    // StateMachine.g:476:1: methodName : name ;
    public final StateMachineParser.methodName_return methodName() throws RecognitionException {
        StateMachineParser.methodName_return retval = new StateMachineParser.methodName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.name_return name322 = null;



        try {
            // StateMachine.g:477:2: ( name )
            // StateMachine.g:477:4: name
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_name_in_methodName2498);
            name322=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name322.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "methodName"

    public static class typeName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeName"
    // StateMachine.g:480:1: typeName : name ;
    public final StateMachineParser.typeName_return typeName() throws RecognitionException {
        StateMachineParser.typeName_return retval = new StateMachineParser.typeName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.name_return name323 = null;



        try {
            // StateMachine.g:481:2: ( name )
            // StateMachine.g:481:4: name
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_name_in_typeName2509);
            name323=name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name323.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeName"

    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // StateMachine.g:484:1: type : typeAdditionalArrayDim ;
    public final StateMachineParser.type_return type() throws RecognitionException {
        StateMachineParser.type_return retval = new StateMachineParser.type_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.typeAdditionalArrayDim_return typeAdditionalArrayDim324 = null;



        try {
            // StateMachine.g:484:6: ( typeAdditionalArrayDim )
            // StateMachine.g:484:8: typeAdditionalArrayDim
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeAdditionalArrayDim_in_type2519);
            typeAdditionalArrayDim324=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeAdditionalArrayDim324.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class typeAdditionalArrayDim_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAdditionalArrayDim"
    // StateMachine.g:487:1: typeAdditionalArrayDim : ( typeName | primitiveType ) ( '[' ']' )? ;
    public final StateMachineParser.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException {
        StateMachineParser.typeAdditionalArrayDim_return retval = new StateMachineParser.typeAdditionalArrayDim_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal327=null;
        Token char_literal328=null;
        StateMachineParser.typeName_return typeName325 = null;

        StateMachineParser.primitiveType_return primitiveType326 = null;


        Object char_literal327_tree=null;
        Object char_literal328_tree=null;

        try {
            // StateMachine.g:488:2: ( ( typeName | primitiveType ) ( '[' ']' )? )
            // StateMachine.g:488:4: ( typeName | primitiveType ) ( '[' ']' )?
            {
            root_0 = (Object)adaptor.nil();

            // StateMachine.g:488:4: ( typeName | primitiveType )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==Identifier) ) {
                alt65=1;
            }
            else if ( ((LA65_0>=126 && LA65_0<=129)) ) {
                alt65=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // StateMachine.g:488:5: typeName
                    {
                    pushFollow(FOLLOW_typeName_in_typeAdditionalArrayDim2532);
                    typeName325=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typeName325.getTree());

                    }
                    break;
                case 2 :
                    // StateMachine.g:488:16: primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_typeAdditionalArrayDim2536);
                    primitiveType326=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primitiveType326.getTree());

                    }
                    break;

            }

            // StateMachine.g:488:31: ( '[' ']' )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==102) ) {
                int LA66_1 = input.LA(2);

                if ( (LA66_1==103) ) {
                    alt66=1;
                }
            }
            switch (alt66) {
                case 1 :
                    // StateMachine.g:488:33: '[' ']'
                    {
                    char_literal327=(Token)match(input,102,FOLLOW_102_in_typeAdditionalArrayDim2541); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal327_tree = (Object)adaptor.create(char_literal327);
                    adaptor.addChild(root_0, char_literal327_tree);
                    }
                    char_literal328=(Token)match(input,103,FOLLOW_103_in_typeAdditionalArrayDim2543); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal328_tree = (Object)adaptor.create(char_literal328);
                    adaptor.addChild(root_0, char_literal328_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeAdditionalArrayDim"

    public static class primitiveType_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitiveType"
    // StateMachine.g:491:1: primitiveType : primitives -> ^( PRIMITIVE_TYPE[\"type\"] primitives ) ;
    public final StateMachineParser.primitiveType_return primitiveType() throws RecognitionException {
        StateMachineParser.primitiveType_return retval = new StateMachineParser.primitiveType_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.primitives_return primitives329 = null;


        RewriteRuleSubtreeStream stream_primitives=new RewriteRuleSubtreeStream(adaptor,"rule primitives");
        try {
            // StateMachine.g:492:2: ( primitives -> ^( PRIMITIVE_TYPE[\"type\"] primitives ) )
            // StateMachine.g:492:4: primitives
            {
            pushFollow(FOLLOW_primitives_in_primitiveType2558);
            primitives329=primitives();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_primitives.add(primitives329.getTree());


            // AST REWRITE
            // elements: primitives
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 493:2: -> ^( PRIMITIVE_TYPE[\"type\"] primitives )
            {
                // StateMachine.g:493:5: ^( PRIMITIVE_TYPE[\"type\"] primitives )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PRIMITIVE_TYPE, "type"), root_1);

                adaptor.addChild(root_1, stream_primitives.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primitiveType"

    public static class primitives_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitives"
    // StateMachine.g:496:1: primitives : ( 'boolean' | 'int' | 'long' | 'double' );
    public final StateMachineParser.primitives_return primitives() throws RecognitionException {
        StateMachineParser.primitives_return retval = new StateMachineParser.primitives_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set330=null;

        Object set330_tree=null;

        try {
            // StateMachine.g:497:2: ( 'boolean' | 'int' | 'long' | 'double' )
            // StateMachine.g:
            {
            root_0 = (Object)adaptor.nil();

            set330=(Token)input.LT(1);
            if ( (input.LA(1)>=126 && input.LA(1)<=129) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set330));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primitives"

    public static class localVariableDeclaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localVariableDeclaration"
    // StateMachine.g:501:1: localVariableDeclaration : type variableDeclarator ( ',' variableDeclarator )* ;
    public final StateMachineParser.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException {
        StateMachineParser.localVariableDeclaration_return retval = new StateMachineParser.localVariableDeclaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal333=null;
        StateMachineParser.type_return type331 = null;

        StateMachineParser.variableDeclarator_return variableDeclarator332 = null;

        StateMachineParser.variableDeclarator_return variableDeclarator334 = null;


        Object char_literal333_tree=null;

        try {
            // StateMachine.g:502:2: ( type variableDeclarator ( ',' variableDeclarator )* )
            // StateMachine.g:502:4: type variableDeclarator ( ',' variableDeclarator )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_type_in_localVariableDeclaration2604);
            type331=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type331.getTree());
            pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2606);
            variableDeclarator332=variableDeclarator();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, variableDeclarator332.getTree());
            // StateMachine.g:502:28: ( ',' variableDeclarator )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==75) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // StateMachine.g:502:29: ',' variableDeclarator
            	    {
            	    char_literal333=(Token)match(input,75,FOLLOW_75_in_localVariableDeclaration2609); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal333_tree = (Object)adaptor.create(char_literal333);
            	    adaptor.addChild(root_0, char_literal333_tree);
            	    }
            	    pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2611);
            	    variableDeclarator334=variableDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableDeclarator334.getTree());

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "localVariableDeclaration"

    public static class variableDeclarator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variableDeclarator"
    // StateMachine.g:505:1: variableDeclarator : identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? ;
    public final StateMachineParser.variableDeclarator_return variableDeclarator() throws RecognitionException {
        StateMachineParser.variableDeclarator_return retval = new StateMachineParser.variableDeclarator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ASSIGN336=null;
        StateMachineParser.identifier_return identifier335 = null;

        StateMachineParser.localInitializerArrayLiteral_return localInitializerArrayLiteral337 = null;

        StateMachineParser.expression_return expression338 = null;


        Object ASSIGN336_tree=null;

        try {
            // StateMachine.g:506:2: ( identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? )
            // StateMachine.g:506:4: identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_identifier_in_variableDeclarator2625);
            identifier335=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, identifier335.getTree());
            // StateMachine.g:506:15: ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==ASSIGN) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // StateMachine.g:506:16: ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    {
                    ASSIGN336=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_variableDeclarator2628); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN336_tree = (Object)adaptor.create(ASSIGN336);
                    adaptor.addChild(root_0, ASSIGN336_tree);
                    }
                    // StateMachine.g:507:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    int alt68=2;
                    alt68 = dfa68.predict(input);
                    switch (alt68) {
                        case 1 :
                            // StateMachine.g:507:3: ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral
                            {
                            pushFollow(FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2645);
                            localInitializerArrayLiteral337=localInitializerArrayLiteral();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, localInitializerArrayLiteral337.getTree());

                            }
                            break;
                        case 2 :
                            // StateMachine.g:508:4: expression
                            {
                            pushFollow(FOLLOW_expression_in_variableDeclarator2650);
                            expression338=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression338.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variableDeclarator"

    public static class localInitializerArrayLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localInitializerArrayLiteral"
    // StateMachine.g:511:1: localInitializerArrayLiteral : LBRACE ( expression ( ',' expression )* )? RBRACE ;
    public final StateMachineParser.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException {
        StateMachineParser.localInitializerArrayLiteral_return retval = new StateMachineParser.localInitializerArrayLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRACE339=null;
        Token char_literal341=null;
        Token RBRACE343=null;
        StateMachineParser.expression_return expression340 = null;

        StateMachineParser.expression_return expression342 = null;


        Object LBRACE339_tree=null;
        Object char_literal341_tree=null;
        Object RBRACE343_tree=null;

        try {
            // StateMachine.g:512:2: ( LBRACE ( expression ( ',' expression )* )? RBRACE )
            // StateMachine.g:512:4: LBRACE ( expression ( ',' expression )* )? RBRACE
            {
            root_0 = (Object)adaptor.nil();

            LBRACE339=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_localInitializerArrayLiteral2664); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LBRACE339_tree = (Object)adaptor.create(LBRACE339);
            adaptor.addChild(root_0, LBRACE339_tree);
            }
            // StateMachine.g:512:11: ( expression ( ',' expression )* )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==Identifier||(LA71_0>=StringLiteral && LA71_0<=LBRACE)||(LA71_0>=EQ && LA71_0<=HexLiteral)||LA71_0==74||(LA71_0>=101 && LA71_0<=102)||(LA71_0>=104 && LA71_0<=105)||LA71_0==109||(LA71_0>=111 && LA71_0<=112)||(LA71_0>=126 && LA71_0<=129)) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // StateMachine.g:512:12: expression ( ',' expression )*
                    {
                    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2667);
                    expression340=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression340.getTree());
                    // StateMachine.g:512:23: ( ',' expression )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==75) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // StateMachine.g:512:24: ',' expression
                    	    {
                    	    char_literal341=(Token)match(input,75,FOLLOW_75_in_localInitializerArrayLiteral2670); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal341_tree = (Object)adaptor.create(char_literal341);
                    	    adaptor.addChild(root_0, char_literal341_tree);
                    	    }
                    	    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2672);
                    	    expression342=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression342.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);


                    }
                    break;

            }

            RBRACE343=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_localInitializerArrayLiteral2678); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RBRACE343_tree = (Object)adaptor.create(RBRACE343);
            adaptor.addChild(root_0, RBRACE343_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "localInitializerArrayLiteral"

    public static class arrayLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayLiteral"
    // StateMachine.g:515:1: arrayLiteral : type localInitializerArrayLiteral ;
    public final StateMachineParser.arrayLiteral_return arrayLiteral() throws RecognitionException {
        StateMachineParser.arrayLiteral_return retval = new StateMachineParser.arrayLiteral_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        StateMachineParser.type_return type344 = null;

        StateMachineParser.localInitializerArrayLiteral_return localInitializerArrayLiteral345 = null;



        try {
            // StateMachine.g:516:2: ( type localInitializerArrayLiteral )
            // StateMachine.g:516:4: type localInitializerArrayLiteral
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_type_in_arrayLiteral2689);
            type344=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type344.getTree());
            pushFollow(FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2691);
            localInitializerArrayLiteral345=localInitializerArrayLiteral();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, localInitializerArrayLiteral345.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayLiteral"

    public static class arrayAllocator_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAllocator"
    // StateMachine.g:519:1: arrayAllocator : typeAdditionalArrayDim '[' expression ']' '{' '}' ;
    public final StateMachineParser.arrayAllocator_return arrayAllocator() throws RecognitionException {
        StateMachineParser.arrayAllocator_return retval = new StateMachineParser.arrayAllocator_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal347=null;
        Token char_literal349=null;
        Token char_literal350=null;
        Token char_literal351=null;
        StateMachineParser.typeAdditionalArrayDim_return typeAdditionalArrayDim346 = null;

        StateMachineParser.expression_return expression348 = null;


        Object char_literal347_tree=null;
        Object char_literal349_tree=null;
        Object char_literal350_tree=null;
        Object char_literal351_tree=null;

        try {
            // StateMachine.g:520:2: ( typeAdditionalArrayDim '[' expression ']' '{' '}' )
            // StateMachine.g:520:4: typeAdditionalArrayDim '[' expression ']' '{' '}'
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_typeAdditionalArrayDim_in_arrayAllocator2702);
            typeAdditionalArrayDim346=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeAdditionalArrayDim346.getTree());
            char_literal347=(Token)match(input,102,FOLLOW_102_in_arrayAllocator2704); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal347_tree = (Object)adaptor.create(char_literal347);
            adaptor.addChild(root_0, char_literal347_tree);
            }
            pushFollow(FOLLOW_expression_in_arrayAllocator2706);
            expression348=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression348.getTree());
            char_literal349=(Token)match(input,103,FOLLOW_103_in_arrayAllocator2708); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal349_tree = (Object)adaptor.create(char_literal349);
            adaptor.addChild(root_0, char_literal349_tree);
            }
            char_literal350=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_arrayAllocator2710); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal350_tree = (Object)adaptor.create(char_literal350);
            adaptor.addChild(root_0, char_literal350_tree);
            }
            char_literal351=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_arrayAllocator2712); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal351_tree = (Object)adaptor.create(char_literal351);
            adaptor.addChild(root_0, char_literal351_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        catch (RewriteEmptyStreamException e) {
        	// do nothing for now
        	System.err.println("##RewriteEmptyStreamException (can be ignored - stack trace for informational purposes only)");
        	e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayAllocator"

    // $ANTLR start synpred1_StateMachine
    public final void synpred1_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:240:4: ( domainSpec )
        // StateMachine.g:240:5: domainSpec
        {
        pushFollow(FOLLOW_domainSpec_in_synpred1_StateMachine1290);
        domainSpec();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_StateMachine

    // $ANTLR start synpred2_StateMachine
    public final void synpred2_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:244:4: ( domainSpec )
        // StateMachine.g:244:5: domainSpec
        {
        pushFollow(FOLLOW_domainSpec_in_synpred2_StateMachine1320);
        domainSpec();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_StateMachine

    // $ANTLR start synpred4_StateMachine
    public final void synpred4_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:310:4: ( type '{' )
        // StateMachine.g:310:5: type '{'
        {
        pushFollow(FOLLOW_type_in_synpred4_StateMachine1686);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred4_StateMachine1688); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_StateMachine

    // $ANTLR start synpred5_StateMachine
    public final void synpred5_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:311:4: ( arrayAllocator )
        // StateMachine.g:311:5: arrayAllocator
        {
        pushFollow(FOLLOW_arrayAllocator_in_synpred5_StateMachine1697);
        arrayAllocator();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_StateMachine

    // $ANTLR start synpred6_StateMachine
    public final void synpred6_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:312:4: ( methodName '(' )
        // StateMachine.g:312:5: methodName '('
        {
        pushFollow(FOLLOW_methodName_in_synpred6_StateMachine1706);
        methodName();

        state._fsp--;
        if (state.failed) return ;
        match(input,74,FOLLOW_74_in_synpred6_StateMachine1708); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_StateMachine

    // $ANTLR start synpred7_StateMachine
    public final void synpred7_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:370:4: ( type identifier )
        // StateMachine.g:370:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred7_StateMachine1966);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred7_StateMachine1968);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_StateMachine

    // $ANTLR start synpred8_StateMachine
    public final void synpred8_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:404:4: ( methodName '(' )
        // StateMachine.g:404:5: methodName '('
        {
        pushFollow(FOLLOW_methodName_in_synpred8_StateMachine2107);
        methodName();

        state._fsp--;
        if (state.failed) return ;
        match(input,74,FOLLOW_74_in_synpred8_StateMachine2109); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_StateMachine

    // $ANTLR start synpred9_StateMachine
    public final void synpred9_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:425:18: ( type identifier )
        // StateMachine.g:425:19: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred9_StateMachine2217);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred9_StateMachine2219);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_StateMachine

    // $ANTLR start synpred10_StateMachine
    public final void synpred10_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:440:4: ( type identifier )
        // StateMachine.g:440:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred10_StateMachine2317);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred10_StateMachine2319);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_StateMachine

    // $ANTLR start synpred11_StateMachine
    public final void synpred11_StateMachine_fragment() throws RecognitionException {   
        // StateMachine.g:507:3: ( localInitializerArrayLiteral ( ';' | ',' ) )
        // StateMachine.g:507:4: localInitializerArrayLiteral ( ';' | ',' )
        {
        pushFollow(FOLLOW_localInitializerArrayLiteral_in_synpred11_StateMachine2634);
        localInitializerArrayLiteral();

        state._fsp--;
        if (state.failed) return ;
        if ( input.LA(1)==SEMICOLON||input.LA(1)==75 ) {
            input.consume();
            state.errorRecovery=false;state.failed=false;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            MismatchedSetException mse = new MismatchedSetException(null,input);
            throw mse;
        }


        }
    }
    // $ANTLR end synpred11_StateMachine

    // Delegated rules

    public final boolean synpred9_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_StateMachine() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_StateMachine_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA26 dfa26 = new DFA26(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA48 dfa48 = new DFA48(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA68 dfa68 = new DFA68(this);
    static final String DFA26_eotS =
        "\17\uffff";
    static final String DFA26_eofS =
        "\17\uffff";
    static final String DFA26_minS =
        "\1\51\16\uffff";
    static final String DFA26_maxS =
        "\1\u0081\16\uffff";
    static final String DFA26_acceptS =
        "\1\uffff\11\1\2\2\1\3\2\uffff";
    static final String DFA26_specialS =
        "\1\0\16\uffff}>";
    static final String[] DFA26_transitionS = {
            "\1\10\2\uffff\1\4\1\13\1\uffff\6\14\1\3\1\6\2\2\21\uffff\1"+
            "\7\32\uffff\1\14\1\12\1\uffff\2\1\3\uffff\1\1\1\uffff\2\5\15"+
            "\uffff\4\11",
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
            ""
    };

    static final short[] DFA26_eot = DFA.unpackEncodedString(DFA26_eotS);
    static final short[] DFA26_eof = DFA.unpackEncodedString(DFA26_eofS);
    static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars(DFA26_minS);
    static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars(DFA26_maxS);
    static final short[] DFA26_accept = DFA.unpackEncodedString(DFA26_acceptS);
    static final short[] DFA26_special = DFA.unpackEncodedString(DFA26_specialS);
    static final short[][] DFA26_transition;

    static {
        int numStates = DFA26_transitionS.length;
        DFA26_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
        }
    }

    class DFA26 extends DFA {

        public DFA26(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 26;
            this.eot = DFA26_eot;
            this.eof = DFA26_eof;
            this.min = DFA26_min;
            this.max = DFA26_max;
            this.accept = DFA26_accept;
            this.special = DFA26_special;
            this.transition = DFA26_transition;
        }
        public String getDescription() {
            return "239:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA26_0 = input.LA(1);

                         
                        int index26_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA26_0>=104 && LA26_0<=105)||LA26_0==109) ) {s = 1;}

                        else if ( ((LA26_0>=DecimalLiteral && LA26_0<=HexLiteral)) ) {s = 2;}

                        else if ( (LA26_0==FloatingPointLiteral) ) {s = 3;}

                        else if ( (LA26_0==StringLiteral) ) {s = 4;}

                        else if ( ((LA26_0>=111 && LA26_0<=112)) ) {s = 5;}

                        else if ( (LA26_0==NullLiteral) ) {s = 6;}

                        else if ( (LA26_0==74) ) {s = 7;}

                        else if ( (LA26_0==Identifier) ) {s = 8;}

                        else if ( ((LA26_0>=126 && LA26_0<=129)) ) {s = 9;}

                        else if ( (LA26_0==102) && (synpred1_StateMachine())) {s = 10;}

                        else if ( (LA26_0==LBRACE) && (synpred1_StateMachine())) {s = 11;}

                        else if ( ((LA26_0>=EQ && LA26_0<=GE)||LA26_0==101) ) {s = 12;}

                         
                        input.seek(index26_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 26, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA27_eotS =
        "\17\uffff";
    static final String DFA27_eofS =
        "\17\uffff";
    static final String DFA27_minS =
        "\1\51\16\uffff";
    static final String DFA27_maxS =
        "\1\u0081\16\uffff";
    static final String DFA27_acceptS =
        "\1\uffff\2\1\6\2\1\1\2\2\1\3\2\uffff";
    static final String DFA27_specialS =
        "\1\0\16\uffff}>";
    static final String[] DFA27_transitionS = {
            "\1\12\2\uffff\1\6\1\2\1\uffff\6\14\1\5\1\10\2\4\21\uffff\1"+
            "\1\32\uffff\1\14\1\11\1\uffff\2\3\3\uffff\1\3\1\uffff\2\7\15"+
            "\uffff\4\13",
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
            ""
    };

    static final short[] DFA27_eot = DFA.unpackEncodedString(DFA27_eotS);
    static final short[] DFA27_eof = DFA.unpackEncodedString(DFA27_eofS);
    static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars(DFA27_minS);
    static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars(DFA27_maxS);
    static final short[] DFA27_accept = DFA.unpackEncodedString(DFA27_acceptS);
    static final short[] DFA27_special = DFA.unpackEncodedString(DFA27_specialS);
    static final short[][] DFA27_transition;

    static {
        int numStates = DFA27_transitionS.length;
        DFA27_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
        }
    }

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = DFA27_eot;
            this.eof = DFA27_eof;
            this.min = DFA27_min;
            this.max = DFA27_max;
            this.accept = DFA27_accept;
            this.special = DFA27_special;
            this.transition = DFA27_transition;
        }
        public String getDescription() {
            return "244:3: ( ( domainSpec )=> domainSpec | relationalExpression | comparisonNoLHS )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_0 = input.LA(1);

                         
                        int index27_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA27_0==74) ) {s = 1;}

                        else if ( (LA27_0==LBRACE) && (synpred2_StateMachine())) {s = 2;}

                        else if ( ((LA27_0>=104 && LA27_0<=105)||LA27_0==109) ) {s = 3;}

                        else if ( ((LA27_0>=DecimalLiteral && LA27_0<=HexLiteral)) ) {s = 4;}

                        else if ( (LA27_0==FloatingPointLiteral) ) {s = 5;}

                        else if ( (LA27_0==StringLiteral) ) {s = 6;}

                        else if ( ((LA27_0>=111 && LA27_0<=112)) ) {s = 7;}

                        else if ( (LA27_0==NullLiteral) ) {s = 8;}

                        else if ( (LA27_0==102) && (synpred2_StateMachine())) {s = 9;}

                        else if ( (LA27_0==Identifier) ) {s = 10;}

                        else if ( ((LA27_0>=126 && LA27_0<=129)) ) {s = 11;}

                        else if ( ((LA27_0>=EQ && LA27_0<=GE)||LA27_0==101) ) {s = 12;}

                         
                        input.seek(index27_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 27, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA41_eotS =
        "\15\uffff";
    static final String DFA41_eofS =
        "\15\uffff";
    static final String DFA41_minS =
        "\1\51\6\uffff\2\0\4\uffff";
    static final String DFA41_maxS =
        "\1\u0081\6\uffff\2\0\4\uffff";
    static final String DFA41_acceptS =
        "\1\uffff\1\1\4\uffff\1\2\2\uffff\1\3\1\4\1\5\1\6";
    static final String DFA41_specialS =
        "\7\uffff\1\0\1\1\4\uffff}>";
    static final String[] DFA41_transitionS = {
            "\1\7\2\uffff\1\1\10\uffff\4\1\21\uffff\1\6\44\uffff\2\1\15"+
            "\uffff\4\10",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA41_eot = DFA.unpackEncodedString(DFA41_eotS);
    static final short[] DFA41_eof = DFA.unpackEncodedString(DFA41_eofS);
    static final char[] DFA41_min = DFA.unpackEncodedStringToUnsignedChars(DFA41_minS);
    static final char[] DFA41_max = DFA.unpackEncodedStringToUnsignedChars(DFA41_maxS);
    static final short[] DFA41_accept = DFA.unpackEncodedString(DFA41_acceptS);
    static final short[] DFA41_special = DFA.unpackEncodedString(DFA41_specialS);
    static final short[][] DFA41_transition;

    static {
        int numStates = DFA41_transitionS.length;
        DFA41_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA41_transition[i] = DFA.unpackEncodedString(DFA41_transitionS[i]);
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = DFA41_eot;
            this.eof = DFA41_eof;
            this.min = DFA41_min;
            this.max = DFA41_max;
            this.accept = DFA41_accept;
            this.special = DFA41_special;
            this.transition = DFA41_transition;
        }
        public String getDescription() {
            return "307:1: primaryPrefix : ( literal | '(' expression ')' | ( type '{' )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=> methodName argumentsSuffix | expressionName );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_7 = input.LA(1);

                         
                        int index41_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_StateMachine()) ) {s = 9;}

                        else if ( (synpred5_StateMachine()) ) {s = 10;}

                        else if ( (synpred6_StateMachine()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index41_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA41_8 = input.LA(1);

                         
                        int index41_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_StateMachine()) ) {s = 9;}

                        else if ( (synpred5_StateMachine()) ) {s = 10;}

                         
                        input.seek(index41_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 41, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA48_eotS =
        "\13\uffff";
    static final String DFA48_eofS =
        "\13\uffff";
    static final String DFA48_minS =
        "\3\51\2\uffff\2\51\1\uffff\3\51";
    static final String DFA48_maxS =
        "\1\u0081\1\156\1\146\2\uffff\1\51\1\u0081\1\uffff\1\u0081\1\156"+
        "\1\146";
    static final String DFA48_acceptS =
        "\3\uffff\1\2\1\3\2\uffff\1\1\3\uffff";
    static final String DFA48_specialS =
        "\1\uffff\1\2\1\3\6\uffff\1\0\1\1}>";
    static final String[] DFA48_transitionS = {
            "\1\1\1\uffff\3\3\7\uffff\4\3\1\4\20\uffff\1\3\44\uffff\2\3"+
            "\2\uffff\5\3\1\uffff\3\3\1\uffff\1\3\4\2",
            "\1\7\1\3\2\uffff\1\3\14\uffff\1\3\17\uffff\1\3\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\3\70\uffff\1\10",
            "",
            "",
            "\1\11",
            "\1\3\2\uffff\2\3\1\uffff\12\3\21\uffff\1\3\32\uffff\2\3\1"+
            "\12\2\3\3\uffff\1\3\1\uffff\2\3\15\uffff\4\3",
            "",
            "\1\3\2\uffff\2\3\1\uffff\12\3\21\uffff\1\3\32\uffff\2\3\1"+
            "\12\2\3\3\uffff\1\3\1\uffff\2\3\15\uffff\4\3",
            "\1\7\1\3\2\uffff\1\3\14\uffff\1\3\17\uffff\1\3\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\3\70\uffff\1\3"
    };

    static final short[] DFA48_eot = DFA.unpackEncodedString(DFA48_eotS);
    static final short[] DFA48_eof = DFA.unpackEncodedString(DFA48_eofS);
    static final char[] DFA48_min = DFA.unpackEncodedStringToUnsignedChars(DFA48_minS);
    static final char[] DFA48_max = DFA.unpackEncodedStringToUnsignedChars(DFA48_maxS);
    static final short[] DFA48_accept = DFA.unpackEncodedString(DFA48_acceptS);
    static final short[] DFA48_special = DFA.unpackEncodedString(DFA48_specialS);
    static final short[][] DFA48_transition;

    static {
        int numStates = DFA48_transitionS.length;
        DFA48_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA48_transition[i] = DFA.unpackEncodedString(DFA48_transitionS[i]);
        }
    }

    class DFA48 extends DFA {

        public DFA48(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 48;
            this.eot = DFA48_eot;
            this.eof = DFA48_eof;
            this.min = DFA48_min;
            this.max = DFA48_max;
            this.accept = DFA48_accept;
            this.special = DFA48_special;
            this.transition = DFA48_transition;
        }
        public String getDescription() {
            return "369:1: thenStatement : ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement | emptyBodyStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA48_9 = input.LA(1);

                         
                        int index48_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA48_9==102) ) {s = 6;}

                        else if ( (LA48_9==ASSIGN||LA48_9==LBRACE||LA48_9==ANNOTATE||LA48_9==74) ) {s = 3;}

                        else if ( (LA48_9==110) ) {s = 5;}

                        else if ( (LA48_9==Identifier) && (synpred7_StateMachine())) {s = 7;}

                         
                        input.seek(index48_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA48_10 = input.LA(1);

                         
                        int index48_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA48_10==LBRACE||LA48_10==102) ) {s = 3;}

                        else if ( (LA48_10==Identifier) && (synpred7_StateMachine())) {s = 7;}

                         
                        input.seek(index48_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA48_1 = input.LA(1);

                         
                        int index48_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA48_1==110) ) {s = 5;}

                        else if ( (LA48_1==ASSIGN||LA48_1==LBRACE||LA48_1==ANNOTATE||LA48_1==74) ) {s = 3;}

                        else if ( (LA48_1==102) ) {s = 6;}

                        else if ( (LA48_1==Identifier) && (synpred7_StateMachine())) {s = 7;}

                         
                        input.seek(index48_1);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA48_2 = input.LA(1);

                         
                        int index48_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA48_2==102) ) {s = 8;}

                        else if ( (LA48_2==Identifier) && (synpred7_StateMachine())) {s = 7;}

                        else if ( (LA48_2==LBRACE) ) {s = 3;}

                         
                        input.seek(index48_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 48, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA54_eotS =
        "\12\uffff";
    static final String DFA54_eofS =
        "\12\uffff";
    static final String DFA54_minS =
        "\1\51\1\0\10\uffff";
    static final String DFA54_maxS =
        "\1\u0081\1\0\10\uffff";
    static final String DFA54_acceptS =
        "\2\uffff\1\2\6\uffff\1\1";
    static final String DFA54_specialS =
        "\1\uffff\1\0\10\uffff}>";
    static final String[] DFA54_transitionS = {
            "\1\1\2\uffff\1\2\10\uffff\4\2\21\uffff\1\2\44\uffff\2\2\15"+
            "\uffff\4\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA54_eot = DFA.unpackEncodedString(DFA54_eotS);
    static final short[] DFA54_eof = DFA.unpackEncodedString(DFA54_eofS);
    static final char[] DFA54_min = DFA.unpackEncodedStringToUnsignedChars(DFA54_minS);
    static final char[] DFA54_max = DFA.unpackEncodedStringToUnsignedChars(DFA54_maxS);
    static final short[] DFA54_accept = DFA.unpackEncodedString(DFA54_acceptS);
    static final short[] DFA54_special = DFA.unpackEncodedString(DFA54_specialS);
    static final short[][] DFA54_transition;

    static {
        int numStates = DFA54_transitionS.length;
        DFA54_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA54_transition[i] = DFA.unpackEncodedString(DFA54_transitionS[i]);
        }
    }

    class DFA54 extends DFA {

        public DFA54(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 54;
            this.eot = DFA54_eot;
            this.eof = DFA54_eof;
            this.min = DFA54_min;
            this.max = DFA54_max;
            this.accept = DFA54_accept;
            this.special = DFA54_special;
            this.transition = DFA54_transition;
        }
        public String getDescription() {
            return "403:1: statementExpression : ( ( methodName '(' )=> methodName argumentsSuffix ( ( primarySuffix )* ASSIGN expression )? | primaryExpression ASSIGN expression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA54_1 = input.LA(1);

                         
                        int index54_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_StateMachine()) ) {s = 9;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index54_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 54, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA56_eotS =
        "\13\uffff";
    static final String DFA56_eofS =
        "\13\uffff";
    static final String DFA56_minS =
        "\1\51\1\uffff\2\51\1\uffff\2\51\1\uffff\3\51";
    static final String DFA56_maxS =
        "\1\u0081\1\uffff\1\156\1\146\1\uffff\1\51\1\u0081\1\uffff\1\u0081"+
        "\1\156\1\146";
    static final String DFA56_acceptS =
        "\1\uffff\1\3\2\uffff\1\2\2\uffff\1\1\3\uffff";
    static final String DFA56_specialS =
        "\2\uffff\1\2\1\3\5\uffff\1\0\1\1}>";
    static final String[] DFA56_transitionS = {
            "\1\2\1\uffff\3\4\1\1\6\uffff\4\4\21\uffff\1\4\44\uffff\2\4"+
            "\2\uffff\5\4\1\uffff\3\4\1\uffff\1\4\4\3",
            "",
            "\1\7\1\4\2\uffff\1\4\14\uffff\1\4\17\uffff\1\4\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\4\70\uffff\1\10",
            "",
            "\1\11",
            "\1\4\2\uffff\2\4\1\uffff\12\4\21\uffff\1\4\32\uffff\2\4\1"+
            "\12\2\4\3\uffff\1\4\1\uffff\2\4\15\uffff\4\4",
            "",
            "\1\4\2\uffff\2\4\1\uffff\12\4\21\uffff\1\4\32\uffff\2\4\1"+
            "\12\2\4\3\uffff\1\4\1\uffff\2\4\15\uffff\4\4",
            "\1\7\1\4\2\uffff\1\4\14\uffff\1\4\17\uffff\1\4\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\4\70\uffff\1\4"
    };

    static final short[] DFA56_eot = DFA.unpackEncodedString(DFA56_eotS);
    static final short[] DFA56_eof = DFA.unpackEncodedString(DFA56_eofS);
    static final char[] DFA56_min = DFA.unpackEncodedStringToUnsignedChars(DFA56_minS);
    static final char[] DFA56_max = DFA.unpackEncodedStringToUnsignedChars(DFA56_maxS);
    static final short[] DFA56_accept = DFA.unpackEncodedString(DFA56_acceptS);
    static final short[] DFA56_special = DFA.unpackEncodedString(DFA56_specialS);
    static final short[][] DFA56_transition;

    static {
        int numStates = DFA56_transitionS.length;
        DFA56_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA56_transition[i] = DFA.unpackEncodedString(DFA56_transitionS[i]);
        }
    }

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = DFA56_eot;
            this.eof = DFA56_eof;
            this.min = DFA56_min;
            this.max = DFA56_max;
            this.accept = DFA56_accept;
            this.special = DFA56_special;
            this.transition = DFA56_transition;
        }
        public String getDescription() {
            return "()* loopback of 425:16: ( ( type identifier )=> localVariableDeclaration SEMICOLON | statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_9 = input.LA(1);

                         
                        int index56_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_9==ASSIGN||LA56_9==LBRACE||LA56_9==ANNOTATE||LA56_9==74) ) {s = 4;}

                        else if ( (LA56_9==110) ) {s = 5;}

                        else if ( (LA56_9==102) ) {s = 6;}

                        else if ( (LA56_9==Identifier) && (synpred9_StateMachine())) {s = 7;}

                         
                        input.seek(index56_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA56_10 = input.LA(1);

                         
                        int index56_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_10==LBRACE||LA56_10==102) ) {s = 4;}

                        else if ( (LA56_10==Identifier) && (synpred9_StateMachine())) {s = 7;}

                         
                        input.seek(index56_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA56_2 = input.LA(1);

                         
                        int index56_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_2==110) ) {s = 5;}

                        else if ( (LA56_2==102) ) {s = 6;}

                        else if ( (LA56_2==ASSIGN||LA56_2==LBRACE||LA56_2==ANNOTATE||LA56_2==74) ) {s = 4;}

                        else if ( (LA56_2==Identifier) && (synpred9_StateMachine())) {s = 7;}

                         
                        input.seek(index56_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA56_3 = input.LA(1);

                         
                        int index56_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_3==102) ) {s = 8;}

                        else if ( (LA56_3==Identifier) && (synpred9_StateMachine())) {s = 7;}

                        else if ( (LA56_3==LBRACE) ) {s = 4;}

                         
                        input.seek(index56_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 56, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA59_eotS =
        "\13\uffff";
    static final String DFA59_eofS =
        "\13\uffff";
    static final String DFA59_minS =
        "\3\51\2\uffff\2\51\1\uffff\3\51";
    static final String DFA59_maxS =
        "\1\u0081\1\156\1\146\2\uffff\1\51\1\u0081\1\uffff\1\u0081\1\156"+
        "\1\146";
    static final String DFA59_acceptS =
        "\3\uffff\1\2\1\3\2\uffff\1\1\3\uffff";
    static final String DFA59_specialS =
        "\1\uffff\1\2\1\3\6\uffff\1\0\1\1}>";
    static final String[] DFA59_transitionS = {
            "\1\1\1\uffff\1\4\1\3\10\uffff\4\3\21\uffff\1\3\44\uffff\2\3"+
            "\15\uffff\4\2",
            "\1\7\1\3\2\uffff\1\3\14\uffff\1\3\17\uffff\1\3\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\3\70\uffff\1\10",
            "",
            "",
            "\1\11",
            "\1\3\2\uffff\2\3\1\uffff\12\3\21\uffff\1\3\32\uffff\2\3\1"+
            "\12\2\3\3\uffff\1\3\1\uffff\2\3\15\uffff\4\3",
            "",
            "\1\3\2\uffff\2\3\1\uffff\12\3\21\uffff\1\3\32\uffff\2\3\1"+
            "\12\2\3\3\uffff\1\3\1\uffff\2\3\15\uffff\4\3",
            "\1\7\1\3\2\uffff\1\3\14\uffff\1\3\17\uffff\1\3\33\uffff\1"+
            "\6\7\uffff\1\5",
            "\1\7\3\uffff\1\3\70\uffff\1\3"
    };

    static final short[] DFA59_eot = DFA.unpackEncodedString(DFA59_eotS);
    static final short[] DFA59_eof = DFA.unpackEncodedString(DFA59_eofS);
    static final char[] DFA59_min = DFA.unpackEncodedStringToUnsignedChars(DFA59_minS);
    static final char[] DFA59_max = DFA.unpackEncodedStringToUnsignedChars(DFA59_maxS);
    static final short[] DFA59_accept = DFA.unpackEncodedString(DFA59_acceptS);
    static final short[] DFA59_special = DFA.unpackEncodedString(DFA59_specialS);
    static final short[][] DFA59_transition;

    static {
        int numStates = DFA59_transitionS.length;
        DFA59_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA59_transition[i] = DFA.unpackEncodedString(DFA59_transitionS[i]);
        }
    }

    class DFA59 extends DFA {

        public DFA59(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 59;
            this.eot = DFA59_eot;
            this.eof = DFA59_eof;
            this.min = DFA59_min;
            this.max = DFA59_max;
            this.accept = DFA59_accept;
            this.special = DFA59_special;
            this.transition = DFA59_transition;
        }
        public String getDescription() {
            return "440:2: ( ( type identifier )=> localVariableDeclaration | statementExpressionList )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA59_9 = input.LA(1);

                         
                        int index59_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_9==102) ) {s = 6;}

                        else if ( (LA59_9==110) ) {s = 5;}

                        else if ( (LA59_9==Identifier) && (synpred10_StateMachine())) {s = 7;}

                        else if ( (LA59_9==ASSIGN||LA59_9==LBRACE||LA59_9==ANNOTATE||LA59_9==74) ) {s = 3;}

                         
                        input.seek(index59_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA59_10 = input.LA(1);

                         
                        int index59_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_10==LBRACE||LA59_10==102) ) {s = 3;}

                        else if ( (LA59_10==Identifier) && (synpred10_StateMachine())) {s = 7;}

                         
                        input.seek(index59_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA59_1 = input.LA(1);

                         
                        int index59_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_1==110) ) {s = 5;}

                        else if ( (LA59_1==102) ) {s = 6;}

                        else if ( (LA59_1==ASSIGN||LA59_1==LBRACE||LA59_1==ANNOTATE||LA59_1==74) ) {s = 3;}

                        else if ( (LA59_1==Identifier) && (synpred10_StateMachine())) {s = 7;}

                         
                        input.seek(index59_1);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA59_2 = input.LA(1);

                         
                        int index59_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_2==102) ) {s = 8;}

                        else if ( (LA59_2==Identifier) && (synpred10_StateMachine())) {s = 7;}

                        else if ( (LA59_2==LBRACE) ) {s = 3;}

                         
                        input.seek(index59_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 59, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA62_eotS =
        "\27\uffff";
    static final String DFA62_eofS =
        "\27\uffff";
    static final String DFA62_minS =
        "\1\51\26\uffff";
    static final String DFA62_maxS =
        "\1\u0081\26\uffff";
    static final String DFA62_acceptS =
        "\1\uffff\1\1\1\2\24\uffff";
    static final String DFA62_specialS =
        "\27\uffff}>";
    static final String[] DFA62_transitionS = {
            "\1\2\1\uffff\4\2\6\uffff\5\2\20\uffff\1\2\44\uffff\2\2\2\uffff"+
            "\11\2\1\uffff\1\1\4\2",
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
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA62_eot = DFA.unpackEncodedString(DFA62_eotS);
    static final short[] DFA62_eof = DFA.unpackEncodedString(DFA62_eofS);
    static final char[] DFA62_min = DFA.unpackEncodedStringToUnsignedChars(DFA62_minS);
    static final char[] DFA62_max = DFA.unpackEncodedStringToUnsignedChars(DFA62_maxS);
    static final short[] DFA62_accept = DFA.unpackEncodedString(DFA62_acceptS);
    static final short[] DFA62_special = DFA.unpackEncodedString(DFA62_specialS);
    static final short[][] DFA62_transition;

    static {
        int numStates = DFA62_transitionS.length;
        DFA62_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA62_transition[i] = DFA.unpackEncodedString(DFA62_transitionS[i]);
        }
    }

    class DFA62 extends DFA {

        public DFA62(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 62;
            this.eot = DFA62_eot;
            this.eof = DFA62_eof;
            this.min = DFA62_min;
            this.max = DFA62_max;
            this.accept = DFA62_accept;
            this.special = DFA62_special;
            this.transition = DFA62_transition;
        }
        public String getDescription() {
            return "448:14: ( finallyRule )?";
        }
    }
    static final String DFA68_eotS =
        "\20\uffff";
    static final String DFA68_eofS =
        "\20\uffff";
    static final String DFA68_minS =
        "\1\51\1\0\16\uffff";
    static final String DFA68_maxS =
        "\1\u0081\1\0\16\uffff";
    static final String DFA68_acceptS =
        "\2\uffff\1\2\14\uffff\1\1";
    static final String DFA68_specialS =
        "\1\uffff\1\0\16\uffff}>";
    static final String[] DFA68_transitionS = {
            "\1\2\2\uffff\1\2\1\1\1\uffff\12\2\21\uffff\1\2\32\uffff\2\2"+
            "\1\uffff\2\2\3\uffff\1\2\1\uffff\2\2\15\uffff\4\2",
            "\1\uffff",
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
            ""
    };

    static final short[] DFA68_eot = DFA.unpackEncodedString(DFA68_eotS);
    static final short[] DFA68_eof = DFA.unpackEncodedString(DFA68_eofS);
    static final char[] DFA68_min = DFA.unpackEncodedStringToUnsignedChars(DFA68_minS);
    static final char[] DFA68_max = DFA.unpackEncodedStringToUnsignedChars(DFA68_maxS);
    static final short[] DFA68_accept = DFA.unpackEncodedString(DFA68_acceptS);
    static final short[] DFA68_special = DFA.unpackEncodedString(DFA68_specialS);
    static final short[][] DFA68_transition;

    static {
        int numStates = DFA68_transitionS.length;
        DFA68_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA68_transition[i] = DFA.unpackEncodedString(DFA68_transitionS[i]);
        }
    }

    class DFA68 extends DFA {

        public DFA68(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 68;
            this.eot = DFA68_eot;
            this.eof = DFA68_eof;
            this.min = DFA68_min;
            this.max = DFA68_max;
            this.accept = DFA68_accept;
            this.special = DFA68_special;
            this.transition = DFA68_transition;
        }
        public String getDescription() {
            return "507:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA68_1 = input.LA(1);

                         
                        int index68_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_StateMachine()) ) {s = 15;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index68_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 68, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stateMachineDefinition_in_startRule188 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_ruleDeclaration_in_startRule194 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_ruleFunctionDeclaration_in_startRule200 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_startRule203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_stateMachineDefinition215 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_stateMachineDefinition217 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_stateMachineDefinition219 = new BitSet(new long[]{0x0000400000000000L,0x0000000000402200L});
    public static final BitSet FOLLOW_stateMachineBlock_in_stateMachineDefinition221 = new BitSet(new long[]{0x0000400000000000L,0x0000000000402200L});
    public static final BitSet FOLLOW_RBRACE_in_stateMachineDefinition224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compositeStateDefinition_in_stateMachineBlock256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_concurrentStateDefinition_in_stateMachineBlock262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_transitionDefinition_in_stateMachineBlock268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_transitionDefinition280 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_transitionDefinition282 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_transitionDefinition284 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_transitionDefinition288 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_transitionDefinition290 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_transitionDefinition294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_transitionDefinition296 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_transitionDefinition298 = new BitSet(new long[]{0x0000400000000000L,0x0002000500000000L});
    public static final BitSet FOLLOW_transitionBlock_in_transitionDefinition302 = new BitSet(new long[]{0x0000400000000000L,0x0002000500000000L});
    public static final BitSet FOLLOW_RBRACE_in_transitionDefinition306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declareNT_in_transitionBlock353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whenNT_in_transitionBlock357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenNT_in_transitionBlock361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_concurrentStateDefinition372 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_concurrentStateDefinition374 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_concurrentStateDefinition376 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_concurrentStateDefinition378 = new BitSet(new long[]{0x0000400000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_regionDefinition_in_concurrentStateDefinition380 = new BitSet(new long[]{0x0000400000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_RBRACE_in_concurrentStateDefinition383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_regionDefinition413 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_regionDefinition415 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_regionDefinition417 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_regionDefinition419 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_stateDefinition_in_regionDefinition422 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_stateRule_in_regionDefinition426 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_RBRACE_in_regionDefinition430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleStateDefinition_in_stateDefinition471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pseudoStartStateDefinition_in_stateDefinition475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pseudoEndStateDefinition_in_stateDefinition479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_pseudoEndStateDefinition490 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_pseudoEndStateDefinition492 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_pseudoEndStateDefinition494 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_pseudoEndStateDefinition496 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_stateRule_in_pseudoEndStateDefinition498 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_RBRACE_in_pseudoEndStateDefinition501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_pseudoStartStateDefinition532 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_pseudoStartStateDefinition534 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_pseudoStartStateDefinition536 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_pseudoStartStateDefinition538 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_stateRule_in_pseudoStartStateDefinition540 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_RBRACE_in_pseudoStartStateDefinition543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_simpleStateDefinition574 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_simpleStateDefinition576 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_simpleStateDefinition578 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_simpleStateDefinition580 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_stateRule_in_simpleStateDefinition582 = new BitSet(new long[]{0x0000400000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_RBRACE_in_simpleStateDefinition585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onEntryRule_in_stateRule616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExitRule_in_stateRule620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onTimeOutRule_in_stateRule624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_onTimeOutRule635 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_onTimeOutRule637 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_onTimeOutRule639 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_onTimeOutRule641 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_onTimeOutRule643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_onExitRule661 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_onExitRule663 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_onExitRule665 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_onExitRule667 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_onExitRule669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_onEntryRule687 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_onEntryRule689 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_onEntryRule691 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_onEntryRule693 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_onEntryRule695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_compositeStateDefinition714 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_compositeStateDefinition716 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_compositeStateDefinition718 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_compositeStateDefinition720 = new BitSet(new long[]{0x0000400000000000L,0x0000000000402000L});
    public static final BitSet FOLLOW_compositeStateBlock_in_compositeStateDefinition722 = new BitSet(new long[]{0x0000400000000000L,0x0000000000402000L});
    public static final BitSet FOLLOW_RBRACE_in_compositeStateDefinition725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_concurrentStateDefinition_in_compositeStateBlock756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compositeStateDefinition_in_compositeStateBlock760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_xsltLiteral784 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_xsltLiteral786 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_xsltLiteral788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_ruleDeclaration800 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_ruleDeclaration802 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleDeclaration804 = new BitSet(new long[]{0x0000400000000000L,0x0002000510000000L});
    public static final BitSet FOLLOW_ruleNT_in_ruleDeclaration806 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleDeclaration808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_ruleFunctionDeclaration836 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_ruleFunctionDeclaration842 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_name_in_ruleFunctionDeclaration844 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleFunctionDeclaration846 = new BitSet(new long[]{0x0000400000000000L,0x0004000210000000L});
    public static final BitSet FOLLOW_ruleFunctionNT_in_ruleFunctionDeclaration848 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleFunctionDeclaration850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributeNT_in_ruleNT889 = new BitSet(new long[]{0x0000000000000002L,0x0002000510000000L});
    public static final BitSet FOLLOW_declareNT_in_ruleNT894 = new BitSet(new long[]{0x0000000000000002L,0x0002000510000000L});
    public static final BitSet FOLLOW_whenNT_in_ruleNT899 = new BitSet(new long[]{0x0000000000000002L,0x0002000510000000L});
    public static final BitSet FOLLOW_thenNT_in_ruleNT904 = new BitSet(new long[]{0x0000000000000002L,0x0002000510000000L});
    public static final BitSet FOLLOW_attributeNT_in_ruleFunctionNT920 = new BitSet(new long[]{0x0000000000000002L,0x0004000210000000L});
    public static final BitSet FOLLOW_scopeNT_in_ruleFunctionNT925 = new BitSet(new long[]{0x0000000000000002L,0x0004000210000000L});
    public static final BitSet FOLLOW_bodyNT_in_ruleFunctionNT930 = new BitSet(new long[]{0x0000000000000002L,0x0004000210000000L});
    public static final BitSet FOLLOW_92_in_attributeNT943 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_attributeNT945 = new BitSet(new long[]{0x0000400000000000L,0x00000000E0000000L});
    public static final BitSet FOLLOW_attributeBodyDeclaration_in_attributeNT947 = new BitSet(new long[]{0x0000400000000000L,0x00000000E0000000L});
    public static final BitSet FOLLOW_RBRACE_in_attributeNT950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_attributeBodyDeclaration974 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration976 = new BitSet(new long[]{0x0180000000000000L});
    public static final BitSet FOLLOW_integerLiteral_in_attributeBodyDeclaration978 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_attributeBodyDeclaration985 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration987 = new BitSet(new long[]{0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_booleanLiteral_in_attributeBodyDeclaration989 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_attributeBodyDeclaration996 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration998 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_StringLiteral_in_attributeBodyDeclaration1000 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration1002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_declareNT1014 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_declareNT1016 = new BitSet(new long[]{0x0000420000000000L});
    public static final BitSet FOLLOW_declarator_in_declareNT1018 = new BitSet(new long[]{0x0000420000000000L});
    public static final BitSet FOLLOW_RBRACE_in_declareNT1021 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_scopeNT1048 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_scopeNT1050 = new BitSet(new long[]{0x0000420000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_scopeDeclarator_in_scopeNT1052 = new BitSet(new long[]{0x0000420000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_RBRACE_in_scopeNT1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_scopeDeclarator1083 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_primitiveType_in_scopeDeclarator1087 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_scopeDeclarator1090 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_scopeDeclarator1092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_declarator1136 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_declarator1138 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_declarator1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_whenNT1175 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_whenNT1177 = new BitSet(new long[]{0x01FFF20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_predicates_in_whenNT1179 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_whenNT1181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_predicates1204 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_predicates1206 = new BitSet(new long[]{0x01FFB20000000002L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_predicate1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression1241 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_expression1246 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression1248 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1262 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_conditionalAndExpression1267 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1269 = new BitSet(new long[]{0x0000000000000002L,0x0000001000000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1284 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression1293 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression1299 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_set_in_equalityExpression1308 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression1323 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1329 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression1335 = new BitSet(new long[]{0x0001800000000002L});
    public static final BitSet FOLLOW_set_in_comparisonNoLHS1356 = new BitSet(new long[]{0x0000200000000000L,0x0000004000000400L});
    public static final BitSet FOLLOW_domainSpec_in_comparisonNoLHS1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_comparisonNoLHS1376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_comparisonNoLHS1386 = new BitSet(new long[]{0x0000020000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_type_in_comparisonNoLHS1388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_comparisonNoLHS1397 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_additiveExpression_in_comparisonNoLHS1413 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeExpression_in_domainSpec1426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setMembershipExpression_in_domainSpec1430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeStart_in_rangeExpression1442 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000C00L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_rangeExpression1444 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_rangeExpression1447 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A3E000001400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_rangeExpression1449 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A3E000001400L,0x0000000000000003L});
    public static final BitSet FOLLOW_rangeEnd_in_rangeExpression1452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_rangeStart0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_rangeEnd0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_setMembershipExpression1494 = new BitSet(new long[]{0x01FFF20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1497 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_setMembershipExpression1500 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1502 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RBRACE_in_setMembershipExpression1508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1519 = new BitSet(new long[]{0x001E000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_set_in_relationalExpression1526 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1542 = new BitSet(new long[]{0x001E000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_relationalExpression1550 = new BitSet(new long[]{0x0000020000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_type_in_relationalExpression1552 = new BitSet(new long[]{0x001E000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1568 = new BitSet(new long[]{0x0000000000000002L,0x0000030000000000L});
    public static final BitSet FOLLOW_set_in_additiveExpression1573 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1581 = new BitSet(new long[]{0x0000000000000002L,0x0000030000000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1595 = new BitSet(new long[]{0x0000000000000002L,0x00001C0000000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1600 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1612 = new BitSet(new long[]{0x0000000000000002L,0x00001C0000000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression1626 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryPrefix_in_primaryExpression1654 = new BitSet(new long[]{0x0400000000000002L,0x0000404000000000L});
    public static final BitSet FOLLOW_primarySuffix_in_primaryExpression1657 = new BitSet(new long[]{0x0400000000000002L,0x0000404000000000L});
    public static final BitSet FOLLOW_literal_in_primaryPrefix1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_primaryPrefix1676 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_primaryPrefix1678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_primaryPrefix1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayLiteral_in_primaryPrefix1691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAllocator_in_primaryPrefix1700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_primaryPrefix1711 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_argumentsSuffix_in_primaryPrefix1713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionName_in_primaryPrefix1718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccessSuffix_in_primarySuffix1730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldAccessSuffix_in_primarySuffix1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_arrayAccessSuffix1746 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_arrayAccessSuffix1748 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_arrayAccessSuffix1750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_fieldAccessSuffix1762 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_fieldAccessSuffix1770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_argumentsSuffix1782 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000801400L,0x0000000000000003L});
    public static final BitSet FOLLOW_argumentList_in_argumentsSuffix1784 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_argumentsSuffix1787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_argumentList1799 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_argumentList1802 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_argumentList1804 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_xsltLiteral_in_argumentList1811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integerLiteral_in_literal1823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FloatingPointLiteral_in_literal1828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_literal1833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal1838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NullLiteral_in_literal1843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_integerLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_thenNT1887 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_thenNT1889 = new BitSet(new long[]{0x03E07A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_thenStatements_in_thenNT1891 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_thenNT1893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_bodyNT1919 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_bodyNT1921 = new BitSet(new long[]{0x03E07A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_thenStatements_in_bodyNT1923 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_bodyNT1925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_thenStatements1952 = new BitSet(new long[]{0x03E03A0000000002L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_thenStatement1971 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_thenStatement1973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_thenStatement1978 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyBodyStatement_in_thenStatement1982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lineStatement_in_statement1994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blockStatement_in_statement1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStatement_in_lineStatement2010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_lineStatement2015 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_lineStatement2017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_lineStatement2022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakStatement_in_lineStatement2027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continueStatement_in_lineStatement2032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwStatement_in_lineStatement2037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifRule_in_blockStatement2049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileRule_in_blockStatement2054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forRule_in_blockStatement2059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_blockStatement2064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tryCatchFinally_in_blockStatement2069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_emptyStatement2081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_emptyBodyStatement2093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_statementExpression2112 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_argumentsSuffix_in_statementExpression2114 = new BitSet(new long[]{0x0400040000000002L,0x0000404000000000L});
    public static final BitSet FOLLOW_primarySuffix_in_statementExpression2119 = new BitSet(new long[]{0x0400040000000000L,0x0000404000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_statementExpression2122 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_statementExpression2124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_statementExpression2131 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_statementExpression2133 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_statementExpression2135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_returnStatement2147 = new BitSet(new long[]{0x01FFBA0000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_returnStatement2150 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_116_in_breakStatement2169 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_breakStatement2171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_continueStatement2183 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_continueStatement2185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_throwStatement2197 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_throwStatement2199 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_throwStatement2201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_block2212 = new BitSet(new long[]{0x01E07A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_block2223 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_block2225 = new BitSet(new long[]{0x01E07A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statement_in_block2229 = new BitSet(new long[]{0x01E07A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_RBRACE_in_block2233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_statementExpressionList2245 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_statementExpressionList2248 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statementExpression_in_statementExpressionList2250 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_119_in_ifRule2263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ifRule2265 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_ifRule2267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifRule2269 = new BitSet(new long[]{0x01E03A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statement_in_ifRule2271 = new BitSet(new long[]{0x0000000000000002L,0x0100000000000000L});
    public static final BitSet FOLLOW_120_in_ifRule2275 = new BitSet(new long[]{0x01E03A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statement_in_ifRule2277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_whileRule2290 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_whileRule2292 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_whileRule2294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_whileRule2296 = new BitSet(new long[]{0x01E03A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statement_in_whileRule2298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_forRule2309 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_forRule2311 = new BitSet(new long[]{0x01E01A0000000000L,0xC001A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_forRule2323 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2327 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2331 = new BitSet(new long[]{0x01FFBA0000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_forRule2335 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2339 = new BitSet(new long[]{0x01E0120000000000L,0xC001A30000001400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2343 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_forRule2347 = new BitSet(new long[]{0x01E03A0000000000L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_statement_in_forRule2350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tryRule_in_tryCatchFinally2362 = new BitSet(new long[]{0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_catchRule_in_tryCatchFinally2368 = new BitSet(new long[]{0x01E03A0000000002L,0xEEF9A30000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_tryRule2389 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_block_in_tryRule2391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_catchRule2401 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_catchRule2403 = new BitSet(new long[]{0x0000020000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_type_in_catchRule2405 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_catchRule2407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_catchRule2409 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_block_in_catchRule2411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_finallyRule2422 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_block_in_finallyRule2424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_semiColon2435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_name2446 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_name2449 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_name2451 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_110_in_dot2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_expressionName2487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_methodName2498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_typeName2509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_type2519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeName_in_typeAdditionalArrayDim2532 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_primitiveType_in_typeAdditionalArrayDim2536 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_typeAdditionalArrayDim2541 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_typeAdditionalArrayDim2543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitives_in_primitiveType2558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_primitives0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_localVariableDeclaration2604 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2606 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_localVariableDeclaration2609 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2611 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_identifier_in_variableDeclarator2625 = new BitSet(new long[]{0x0000040000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_variableDeclarator2628 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_variableDeclarator2650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_localInitializerArrayLiteral2664 = new BitSet(new long[]{0x01FFF20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2667 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_localInitializerArrayLiteral2670 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2672 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RBRACE_in_localInitializerArrayLiteral2678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_arrayLiteral2689 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_arrayAllocator2702 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_arrayAllocator2704 = new BitSet(new long[]{0x01FFB20000000000L,0xC001A36000000400L,0x0000000000000003L});
    public static final BitSet FOLLOW_expression_in_arrayAllocator2706 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_arrayAllocator2708 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_arrayAllocator2710 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_RBRACE_in_arrayAllocator2712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainSpec_in_synpred1_StateMachine1290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainSpec_in_synpred2_StateMachine1320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred4_StateMachine1686 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred4_StateMachine1688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAllocator_in_synpred5_StateMachine1697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_synpred6_StateMachine1706 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_synpred6_StateMachine1708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred7_StateMachine1966 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_synpred7_StateMachine1968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_synpred8_StateMachine2107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_synpred8_StateMachine2109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred9_StateMachine2217 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_synpred9_StateMachine2219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred10_StateMachine2317 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_identifier_in_synpred10_StateMachine2319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_synpred11_StateMachine2634 = new BitSet(new long[]{0x0000080000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_set_in_synpred11_StateMachine2636 = new BitSet(new long[]{0x0000000000000002L});

}