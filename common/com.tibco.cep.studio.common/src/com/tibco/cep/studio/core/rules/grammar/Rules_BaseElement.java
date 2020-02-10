// $ANTLR 3.2 Sep 23, 2009 12:02:23 BaseElement.g 2015-06-26 10:31:23
 
package com.tibco.cep.studio.core.rules.grammar; 

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEmptyStreamException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class Rules_BaseElement extends BaseRulesParser {
    public static final int MOD=123;
    public static final int T__167=167;
    public static final int TRY_STATEMENT=91;
    public static final int T__168=168;
    public static final int EOF=-1;
    public static final int T__165=165;
    public static final int T__166=166;
    public static final int T__163=163;
    public static final int STATEMENT=52;
    public static final int T__164=164;
    public static final int TYPE=38;
    public static final int ARRAY_ALLOCATOR=86;
    public static final int RPAREN=146;
    public static final int T__247=247;
    public static final int MappingEnd=105;
    public static final int T__246=246;
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
    public static final int GT=114;
    public static final int STATEMENTS=28;
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
    public static final int LBRACK=147;
    public static final int T__219=219;
    public static final int T__217=217;
    public static final int DIV_EQUAL=141;
    public static final int T__218=218;
    public static final int TEMPLATE_DECLARATOR=6;
    public static final int ANNOTATION=42;
    public static final int LBRACE=117;
    public static final int HexDigit=149;
    public static final int HeaderSection=144;
    public static final int SUFFIXES=102;
    public static final int DOMAIN_MODEL=11;
    public static final int RANGE_START=70;
    public static final int T__223=223;
    public static final int LPAREN=145;
    public static final int T__222=222;
    public static final int T__221=221;
    public static final int T__220=220;
    public static final int MAPPING_BLOCK=56;
    public static final int T__202=202;
    public static final int T__203=203;
    public static final int WHEN_BLOCK=32;
    public static final int T__204=204;
    public static final int T__205=205;
    public static final int T__206=206;
    public static final int T__207=207;
    public static final int SUFFIX=46;
    public static final int T__208=208;
    public static final int T__209=209;
    public static final int IF_RULE=48;
    public static final int RANK_STATEMENT=61;
    public static final int PLUS=119;
    public static final int SUFFIX_EXPRESSION=104;
    public static final int THEN_BLOCK=35;
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
    public static final int RULE_FUNCTION=23;
    public static final int FORWARD_CHAIN_STATEMENT=63;
    public static final int INITIALIZER=83;
    public static final int CATCH_CLAUSE=89;
    public static final int TRY_RULE=77;
    public static final int MODIFIERS=94;
    public static final int VARIABLE_DECLARATOR=21;
    public static final int T__199=199;
    public static final int T__198=198;
    public static final int T__197=197;
    public static final int T__196=196;
    public static final int RETURN_STATEMENT=53;
    public static final int T__195=195;
    public static final int T__194=194;
    public static final int T__193=193;
    public static final int T__192=192;
    public static final int T__191=191;
    public static final int T__190=190;
    public static final int IDENTIFIER=80;
    public static final int ARRAY_ACCESS_SUFFIX=82;
    public static final int PLUS_EQUAL=138;
    public static final int DOT=125;
    public static final int EXPRESSION=47;
    public static final int T__184=184;
    public static final int SCOPE_DECLARATOR=37;
    public static final int T__183=183;
    public static final int RANGE_END=71;
    public static final int T__186=186;
    public static final int T__185=185;
    public static final int T__188=188;
    public static final int T__187=187;
    public static final int T__189=189;
    public static final int OPERATOR=99;
    public static final int T__180=180;
    public static final int T__182=182;
    public static final int ALIAS_STATEMENT=66;
    public static final int T__181=181;
    public static final int LITERAL=67;
    public static final int DecimalLiteral=130;
    public static final int TRUE=132;
    public static final int T__175=175;
    public static final int T__174=174;
    public static final int T__173=173;
    public static final int T__172=172;
    public static final int SIMPLE_NAME=18;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int T__177=177;
    public static final int DOMAIN_MODEL_STATEMENT=12;
    public static final int T__176=176;
    public static final int UnicodeEscape=155;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int ASSIGN=137;
    public static final int DECR=136;
    public static final int BINDINGS_VIEWS_BLOCK=9;
    public static final int T__169=169;
    public static final int LE=115;

    // delegates
    // delegators
    public RulesParser gRules;
    public RulesParser gParent;


        public Rules_BaseElement(TokenStream input, RulesParser gRules) {
            this(input, new RecognizerSharedState(), gRules);
        }
        public Rules_BaseElement(TokenStream input, RecognizerSharedState state, RulesParser gRules) {
            super(input, state);
            this.gRules = gRules;
             
            gParent = gRules;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return RulesParser.tokenNames; }
    public String getGrammarFileName() { return "BaseElement.g"; }


    	public RulesASTNode getHeaderNode() {
    		return null; // root grammars will return the proper value
    	}


    public static class predicateStatements_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicateStatements"
    // BaseElement.g:89:1: predicateStatements : ( predicateStatement )* ;
    public final Rules_BaseElement.predicateStatements_return predicateStatements() throws RecognitionException {
        Rules_BaseElement.predicateStatements_return retval = new Rules_BaseElement.predicateStatements_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.predicateStatement_return predicateStatement1 = null;



        try {
            // BaseElement.g:90:2: ( ( predicateStatement )* )
            // BaseElement.g:90:4: ( predicateStatement )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // BaseElement.g:90:4: ( predicateStatement )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=Identifier && LA4_0<=SEMICOLON)||(LA4_0>=EQ && LA4_0<=LBRACE)||(LA4_0>=PLUS && LA4_0<=MINUS)||LA4_0==POUND||(LA4_0>=FloatingPointLiteral && LA4_0<=FALSE)||LA4_0==LPAREN||LA4_0==LBRACK||(LA4_0>=164 && LA4_0<=212)||(LA4_0>=228 && LA4_0<=231)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // BaseElement.g:90:4: predicateStatement
            	    {
            	    pushFollow(FOLLOW_predicateStatement_in_predicateStatements714);
            	    predicateStatement1=predicateStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, predicateStatement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicateStatements"

    public static class predicateStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicateStatement"
    // BaseElement.g:93:1: predicateStatement : ( predicate SEMICOLON -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) ) | emptyStatement -> emptyStatement ) ;
    public final Rules_BaseElement.predicateStatement_return predicateStatement() throws RecognitionException {
        Rules_BaseElement.predicateStatement_return retval = new Rules_BaseElement.predicateStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token SEMICOLON3=null;
        Rules_BaseElement.predicate_return predicate2 = null;

        Rules_BaseElement.emptyStatement_return emptyStatement4 = null;


        RulesASTNode SEMICOLON3_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_emptyStatement=new RewriteRuleSubtreeStream(adaptor,"rule emptyStatement");
        try {
            // BaseElement.g:94:2: ( ( predicate SEMICOLON -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) ) | emptyStatement -> emptyStatement ) )
            // BaseElement.g:94:4: ( predicate SEMICOLON -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) ) | emptyStatement -> emptyStatement )
            {
            // BaseElement.g:94:4: ( predicate SEMICOLON -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) ) | emptyStatement -> emptyStatement )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==Identifier||(LA5_0>=EQ && LA5_0<=LBRACE)||(LA5_0>=PLUS && LA5_0<=MINUS)||LA5_0==POUND||(LA5_0>=FloatingPointLiteral && LA5_0<=FALSE)||LA5_0==LPAREN||LA5_0==LBRACK||(LA5_0>=164 && LA5_0<=212)||(LA5_0>=228 && LA5_0<=231)) ) {
                alt5=1;
            }
            else if ( (LA5_0==SEMICOLON) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // BaseElement.g:94:5: predicate SEMICOLON
                    {
                    pushFollow(FOLLOW_predicate_in_predicateStatement728);
                    predicate2=predicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_predicate.add(predicate2.getTree());
                    SEMICOLON3=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_predicateStatement730); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON3);



                    // AST REWRITE
                    // elements: predicate
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 94:25: -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) )
                    {
                        // BaseElement.g:94:28: ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PREDICATE_STATEMENT, "PREDICATE_STATEMENT"), root_1);

                        // BaseElement.g:94:50: ^( EXPRESSION predicate )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        adaptor.addChild(root_2, stream_predicate.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // BaseElement.g:95:4: emptyStatement
                    {
                    pushFollow(FOLLOW_emptyStatement_in_predicateStatement747);
                    emptyStatement4=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_emptyStatement.add(emptyStatement4.getTree());


                    // AST REWRITE
                    // elements: emptyStatement
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 95:19: -> emptyStatement
                    {
                        adaptor.addChild(root_0, stream_emptyStatement.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicateStatement"

    public static class predicate_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicate"
    // BaseElement.g:98:1: predicate : expression ;
    public final Rules_BaseElement.predicate_return predicate() throws RecognitionException {
        Rules_BaseElement.predicate_return retval = new Rules_BaseElement.predicate_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.expression_return expression5 = null;



        try {
            // BaseElement.g:99:2: ( expression )
            // BaseElement.g:99:4: expression
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_expression_in_predicate763);
            expression5=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression5.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicate"

    public static class expression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // BaseElement.g:102:1: expression : conditionalAndExpression ( OR conditionalAndExpression )* ;
    public final Rules_BaseElement.expression_return expression() throws RecognitionException {
        Rules_BaseElement.expression_return retval = new Rules_BaseElement.expression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token OR7=null;
        Rules_BaseElement.conditionalAndExpression_return conditionalAndExpression6 = null;

        Rules_BaseElement.conditionalAndExpression_return conditionalAndExpression8 = null;


        RulesASTNode OR7_tree=null;

        try {
            // BaseElement.g:103:2: ( conditionalAndExpression ( OR conditionalAndExpression )* )
            // BaseElement.g:103:4: conditionalAndExpression ( OR conditionalAndExpression )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_conditionalAndExpression_in_expression775);
            conditionalAndExpression6=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression6.getTree());
            // BaseElement.g:104:2: ( OR conditionalAndExpression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==OR) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // BaseElement.g:104:4: OR conditionalAndExpression
            	    {
            	    OR7=(Token)match(input,OR,FOLLOW_OR_in_expression780); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR7_tree = (RulesASTNode)adaptor.create(OR7);
            	    root_0 = (RulesASTNode)adaptor.becomeRoot(OR7_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_conditionalAndExpression_in_expression783);
            	    conditionalAndExpression8=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression8.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class conditionalAndExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionalAndExpression"
    // BaseElement.g:107:1: conditionalAndExpression : equalityExpression ( AND equalityExpression )* ;
    public final Rules_BaseElement.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        Rules_BaseElement.conditionalAndExpression_return retval = new Rules_BaseElement.conditionalAndExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token AND10=null;
        Rules_BaseElement.equalityExpression_return equalityExpression9 = null;

        Rules_BaseElement.equalityExpression_return equalityExpression11 = null;


        RulesASTNode AND10_tree=null;

        try {
            // BaseElement.g:108:2: ( equalityExpression ( AND equalityExpression )* )
            // BaseElement.g:108:4: equalityExpression ( AND equalityExpression )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression797);
            equalityExpression9=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression9.getTree());
            // BaseElement.g:109:2: ( AND equalityExpression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==AND) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // BaseElement.g:109:4: AND equalityExpression
            	    {
            	    AND10=(Token)match(input,AND,FOLLOW_AND_in_conditionalAndExpression802); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND10_tree = (RulesASTNode)adaptor.create(AND10);
            	    root_0 = (RulesASTNode)adaptor.becomeRoot(AND10_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression805);
            	    equalityExpression11=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression11.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "conditionalAndExpression"

    public static class equalityExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpression"
    // BaseElement.g:112:1: equalityExpression : ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )* ;
    public final Rules_BaseElement.equalityExpression_return equalityExpression() throws RecognitionException {
        Rules_BaseElement.equalityExpression_return retval = new Rules_BaseElement.equalityExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EQ15=null;
        Token NE16=null;
        Rules_BaseElement.relationalExpression_return relationalExpression12 = null;

        Rules_BaseElement.domainSpec_return domainSpec13 = null;

        Rules_BaseElement.comparisonNoLHS_return comparisonNoLHS14 = null;

        Rules_BaseElement.relationalExpression_return relationalExpression17 = null;

        Rules_BaseElement.domainSpec_return domainSpec18 = null;

        Rules_BaseElement.comparisonNoLHS_return comparisonNoLHS19 = null;


        RulesASTNode EQ15_tree=null;
        RulesASTNode NE16_tree=null;

        try {
            // BaseElement.g:113:2: ( ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )* )
            // BaseElement.g:113:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // BaseElement.g:113:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // BaseElement.g:113:5: relationalExpression
                    {
                    pushFollow(FOLLOW_relationalExpression_in_equalityExpression820);
                    relationalExpression12=relationalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression12.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:114:4: ( domainSpec )=> domainSpec
                    {
                    pushFollow(FOLLOW_domainSpec_in_equalityExpression829);
                    domainSpec13=domainSpec();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec13.getTree());

                    }
                    break;
                case 3 :
                    // BaseElement.g:115:4: comparisonNoLHS
                    {
                    pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression835);
                    comparisonNoLHS14=comparisonNoLHS();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS14.getTree());

                    }
                    break;

            }

            // BaseElement.g:116:2: ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=EQ && LA11_0<=NE)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // BaseElement.g:117:3: ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )
            	    {
            	    // BaseElement.g:117:3: ( EQ | NE )
            	    int alt9=2;
            	    int LA9_0 = input.LA(1);

            	    if ( (LA9_0==EQ) ) {
            	        alt9=1;
            	    }
            	    else if ( (LA9_0==NE) ) {
            	        alt9=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 9, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt9) {
            	        case 1 :
            	            // BaseElement.g:117:4: EQ
            	            {
            	            EQ15=(Token)match(input,EQ,FOLLOW_EQ_in_equalityExpression845); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            EQ15_tree = (RulesASTNode)adaptor.create(EQ15);
            	            root_0 = (RulesASTNode)adaptor.becomeRoot(EQ15_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // BaseElement.g:117:10: NE
            	            {
            	            NE16=(Token)match(input,NE,FOLLOW_NE_in_equalityExpression850); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NE16_tree = (RulesASTNode)adaptor.create(NE16);
            	            root_0 = (RulesASTNode)adaptor.becomeRoot(NE16_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    // BaseElement.g:118:3: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )
            	    int alt10=3;
            	    alt10 = dfa10.predict(input);
            	    switch (alt10) {
            	        case 1 :
            	            // BaseElement.g:118:4: relationalExpression
            	            {
            	            pushFollow(FOLLOW_relationalExpression_in_equalityExpression857);
            	            relationalExpression17=relationalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression17.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // BaseElement.g:119:5: ( domainSpec )=> domainSpec
            	            {
            	            pushFollow(FOLLOW_domainSpec_in_equalityExpression867);
            	            domainSpec18=domainSpec();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec18.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // BaseElement.g:120:4: comparisonNoLHS
            	            {
            	            pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression872);
            	            comparisonNoLHS19=comparisonNoLHS();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS19.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "equalityExpression"

    public static class comparisonNoLHS_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comparisonNoLHS"
    // BaseElement.g:124:1: comparisonNoLHS : ( ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression ) | ( INSTANCE_OF type ) | ( ( LT | GT | LE | GE ) additiveExpression ) );
    public final Rules_BaseElement.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException {
        Rules_BaseElement.comparisonNoLHS_return retval = new Rules_BaseElement.comparisonNoLHS_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EQ20=null;
        Token NE21=null;
        Token INSTANCE_OF24=null;
        Token LT26=null;
        Token GT27=null;
        Token LE28=null;
        Token GE29=null;
        Rules_BaseElement.domainSpec_return domainSpec22 = null;

        Rules_BaseElement.unaryExpression_return unaryExpression23 = null;

        Rules_BaseElement.type_return type25 = null;

        Rules_BaseElement.additiveExpression_return additiveExpression30 = null;


        RulesASTNode EQ20_tree=null;
        RulesASTNode NE21_tree=null;
        RulesASTNode INSTANCE_OF24_tree=null;
        RulesASTNode LT26_tree=null;
        RulesASTNode GT27_tree=null;
        RulesASTNode LE28_tree=null;
        RulesASTNode GE29_tree=null;

        try {
            // BaseElement.g:125:2: ( ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression ) | ( INSTANCE_OF type ) | ( ( LT | GT | LE | GE ) additiveExpression ) )
            int alt15=3;
            switch ( input.LA(1) ) {
            case Identifier:
            case EQ:
            case NE:
            case PLUS:
            case MINUS:
            case POUND:
            case FloatingPointLiteral:
            case StringLiteral:
            case NullLiteral:
            case DecimalLiteral:
            case HexLiteral:
            case TRUE:
            case FALSE:
            case LPAREN:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            case 198:
            case 199:
            case 200:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 228:
            case 229:
            case 230:
            case 231:
                {
                alt15=1;
                }
                break;
            case INSTANCE_OF:
                {
                alt15=2;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt15=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // BaseElement.g:125:4: ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression )
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    // BaseElement.g:125:4: ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( ((LA13_0>=EQ && LA13_0<=NE)) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==Identifier||(LA13_0>=PLUS && LA13_0<=MINUS)||LA13_0==POUND||(LA13_0>=FloatingPointLiteral && LA13_0<=FALSE)||LA13_0==LPAREN||(LA13_0>=164 && LA13_0<=212)||(LA13_0>=228 && LA13_0<=231)) ) {
                        alt13=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // BaseElement.g:126:3: ( EQ | NE ) ( domainSpec )=> domainSpec
                            {
                            // BaseElement.g:126:3: ( EQ | NE )
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( (LA12_0==EQ) ) {
                                alt12=1;
                            }
                            else if ( (LA12_0==NE) ) {
                                alt12=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 12, 0, input);

                                throw nvae;
                            }
                            switch (alt12) {
                                case 1 :
                                    // BaseElement.g:126:4: EQ
                                    {
                                    EQ20=(Token)match(input,EQ,FOLLOW_EQ_in_comparisonNoLHS894); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    EQ20_tree = (RulesASTNode)adaptor.create(EQ20);
                                    root_0 = (RulesASTNode)adaptor.becomeRoot(EQ20_tree, root_0);
                                    }

                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:126:10: NE
                                    {
                                    NE21=(Token)match(input,NE,FOLLOW_NE_in_comparisonNoLHS899); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    NE21_tree = (RulesASTNode)adaptor.create(NE21);
                                    root_0 = (RulesASTNode)adaptor.becomeRoot(NE21_tree, root_0);
                                    }

                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_domainSpec_in_comparisonNoLHS909);
                            domainSpec22=domainSpec();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec22.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:128:5: unaryExpression
                            {
                            pushFollow(FOLLOW_unaryExpression_in_comparisonNoLHS915);
                            unaryExpression23=unaryExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression23.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // BaseElement.g:130:2: ( INSTANCE_OF type )
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    // BaseElement.g:130:2: ( INSTANCE_OF type )
                    // BaseElement.g:130:4: INSTANCE_OF type
                    {
                    INSTANCE_OF24=(Token)match(input,INSTANCE_OF,FOLLOW_INSTANCE_OF_in_comparisonNoLHS925); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INSTANCE_OF24_tree = (RulesASTNode)adaptor.create(INSTANCE_OF24);
                    root_0 = (RulesASTNode)adaptor.becomeRoot(INSTANCE_OF24_tree, root_0);
                    }
                    pushFollow(FOLLOW_type_in_comparisonNoLHS928);
                    type25=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, type25.getTree());

                    }


                    }
                    break;
                case 3 :
                    // BaseElement.g:131:2: ( ( LT | GT | LE | GE ) additiveExpression )
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    // BaseElement.g:131:2: ( ( LT | GT | LE | GE ) additiveExpression )
                    // BaseElement.g:131:4: ( LT | GT | LE | GE ) additiveExpression
                    {
                    // BaseElement.g:131:4: ( LT | GT | LE | GE )
                    int alt14=4;
                    switch ( input.LA(1) ) {
                    case LT:
                        {
                        alt14=1;
                        }
                        break;
                    case GT:
                        {
                        alt14=2;
                        }
                        break;
                    case LE:
                        {
                        alt14=3;
                        }
                        break;
                    case GE:
                        {
                        alt14=4;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 0, input);

                        throw nvae;
                    }

                    switch (alt14) {
                        case 1 :
                            // BaseElement.g:131:5: LT
                            {
                            LT26=(Token)match(input,LT,FOLLOW_LT_in_comparisonNoLHS938); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LT26_tree = (RulesASTNode)adaptor.create(LT26);
                            root_0 = (RulesASTNode)adaptor.becomeRoot(LT26_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // BaseElement.g:131:11: GT
                            {
                            GT27=(Token)match(input,GT,FOLLOW_GT_in_comparisonNoLHS943); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            GT27_tree = (RulesASTNode)adaptor.create(GT27);
                            root_0 = (RulesASTNode)adaptor.becomeRoot(GT27_tree, root_0);
                            }

                            }
                            break;
                        case 3 :
                            // BaseElement.g:131:17: LE
                            {
                            LE28=(Token)match(input,LE,FOLLOW_LE_in_comparisonNoLHS948); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LE28_tree = (RulesASTNode)adaptor.create(LE28);
                            root_0 = (RulesASTNode)adaptor.becomeRoot(LE28_tree, root_0);
                            }

                            }
                            break;
                        case 4 :
                            // BaseElement.g:131:23: GE
                            {
                            GE29=(Token)match(input,GE,FOLLOW_GE_in_comparisonNoLHS953); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            GE29_tree = (RulesASTNode)adaptor.create(GE29);
                            root_0 = (RulesASTNode)adaptor.becomeRoot(GE29_tree, root_0);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_additiveExpression_in_comparisonNoLHS957);
                    additiveExpression30=additiveExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression30.getTree());

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comparisonNoLHS"

    public static class domainSpec_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainSpec"
    // BaseElement.g:134:1: domainSpec : ( rangeExpression | setMembershipExpression );
    public final Rules_BaseElement.domainSpec_return domainSpec() throws RecognitionException {
        Rules_BaseElement.domainSpec_return retval = new Rules_BaseElement.domainSpec_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.rangeExpression_return rangeExpression31 = null;

        Rules_BaseElement.setMembershipExpression_return setMembershipExpression32 = null;



        try {
            // BaseElement.g:135:2: ( rangeExpression | setMembershipExpression )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==LPAREN||LA16_0==LBRACK) ) {
                alt16=1;
            }
            else if ( (LA16_0==LBRACE) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // BaseElement.g:135:4: rangeExpression
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_rangeExpression_in_domainSpec970);
                    rangeExpression31=rangeExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, rangeExpression31.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:135:22: setMembershipExpression
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_setMembershipExpression_in_domainSpec974);
                    setMembershipExpression32=setMembershipExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, setMembershipExpression32.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "domainSpec"

    public static class rangeExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeExpression"
    // BaseElement.g:138:1: rangeExpression : rangeStart (start= expression )? ',' (end= expression )? rangeEnd -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) ) ;
    public final Rules_BaseElement.rangeExpression_return rangeExpression() throws RecognitionException {
        Rules_BaseElement.rangeExpression_return retval = new Rules_BaseElement.rangeExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal34=null;
        Rules_BaseElement.expression_return start = null;

        Rules_BaseElement.expression_return end = null;

        Rules_BaseElement.rangeStart_return rangeStart33 = null;

        Rules_BaseElement.rangeEnd_return rangeEnd35 = null;


        RulesASTNode char_literal34_tree=null;
        RewriteRuleTokenStream stream_216=new RewriteRuleTokenStream(adaptor,"token 216");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_rangeStart=new RewriteRuleSubtreeStream(adaptor,"rule rangeStart");
        RewriteRuleSubtreeStream stream_rangeEnd=new RewriteRuleSubtreeStream(adaptor,"rule rangeEnd");
        try {
            // BaseElement.g:139:2: ( rangeStart (start= expression )? ',' (end= expression )? rangeEnd -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) ) )
            // BaseElement.g:139:4: rangeStart (start= expression )? ',' (end= expression )? rangeEnd
            {
            pushFollow(FOLLOW_rangeStart_in_rangeExpression986);
            rangeStart33=rangeStart();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_rangeStart.add(rangeStart33.getTree());
            // BaseElement.g:139:20: (start= expression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==Identifier||(LA17_0>=EQ && LA17_0<=LBRACE)||(LA17_0>=PLUS && LA17_0<=MINUS)||LA17_0==POUND||(LA17_0>=FloatingPointLiteral && LA17_0<=FALSE)||LA17_0==LPAREN||LA17_0==LBRACK||(LA17_0>=164 && LA17_0<=212)||(LA17_0>=228 && LA17_0<=231)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // BaseElement.g:139:20: start= expression
                    {
                    pushFollow(FOLLOW_expression_in_rangeExpression990);
                    start=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(start.getTree());

                    }
                    break;

            }

            char_literal34=(Token)match(input,216,FOLLOW_216_in_rangeExpression993); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_216.add(char_literal34);

            // BaseElement.g:139:40: (end= expression )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==Identifier||(LA18_0>=EQ && LA18_0<=LBRACE)||(LA18_0>=PLUS && LA18_0<=MINUS)||LA18_0==POUND||(LA18_0>=FloatingPointLiteral && LA18_0<=FALSE)||LA18_0==LPAREN||LA18_0==LBRACK||(LA18_0>=164 && LA18_0<=212)||(LA18_0>=228 && LA18_0<=231)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // BaseElement.g:139:40: end= expression
                    {
                    pushFollow(FOLLOW_expression_in_rangeExpression997);
                    end=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(end.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_rangeEnd_in_rangeExpression1000);
            rangeEnd35=rangeEnd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_rangeEnd.add(rangeEnd35.getTree());


            // AST REWRITE
            // elements: start, end
            // token labels: 
            // rule labels: retval, start, end
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_start=new RewriteRuleSubtreeStream(adaptor,"rule start",start!=null?start.tree:null);
            RewriteRuleSubtreeStream stream_end=new RewriteRuleSubtreeStream(adaptor,"rule end",end!=null?end.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 140:3: -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) )
            {
                // BaseElement.g:140:6: ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RANGE_EXPRESSION, "RANGE_EXPRESSION"), root_1);

                // BaseElement.g:140:25: ^( RANGE_START ( $start)? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RANGE_START, "RANGE_START"), root_2);

                // BaseElement.g:140:39: ( $start)?
                if ( stream_start.hasNext() ) {
                    adaptor.addChild(root_2, stream_start.nextTree());

                }
                stream_start.reset();

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:140:48: ^( RANGE_END $end)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RANGE_END, "RANGE_END"), root_2);

                adaptor.addChild(root_2, stream_end.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeExpression"

    public static class rangeStart_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeStart"
    // BaseElement.g:143:1: rangeStart : ( '(' | '[' );
    public final Rules_BaseElement.rangeStart_return rangeStart() throws RecognitionException {
        Rules_BaseElement.rangeStart_return retval = new Rules_BaseElement.rangeStart_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal36=null;
        Token char_literal37=null;

        RulesASTNode char_literal36_tree=null;
        RulesASTNode char_literal37_tree=null;

        try {
            // BaseElement.g:144:2: ( '(' | '[' )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==LPAREN) ) {
                alt19=1;
            }
            else if ( (LA19_0==LBRACK) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // BaseElement.g:144:4: '('
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    char_literal36=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_rangeStart1034); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal36_tree = (RulesASTNode)adaptor.create(char_literal36);
                    adaptor.addChild(root_0, char_literal36_tree);
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:144:10: '['
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    char_literal37=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_rangeStart1038); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal37_tree = (RulesASTNode)adaptor.create(char_literal37);
                    adaptor.addChild(root_0, char_literal37_tree);
                    }
                    if ( state.backtracking==0 ) {
                       arrayDepth++; 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeStart"

    public static class rangeEnd_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeEnd"
    // BaseElement.g:147:1: rangeEnd : ( ')' | ']' );
    public final Rules_BaseElement.rangeEnd_return rangeEnd() throws RecognitionException {
        Rules_BaseElement.rangeEnd_return retval = new Rules_BaseElement.rangeEnd_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal38=null;
        Token char_literal39=null;

        RulesASTNode char_literal38_tree=null;
        RulesASTNode char_literal39_tree=null;

        try {
            // BaseElement.g:148:2: ( ')' | ']' )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==RPAREN) ) {
                alt20=1;
            }
            else if ( (LA20_0==RBRACK) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // BaseElement.g:148:4: ')'
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    char_literal38=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_rangeEnd1051); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal38_tree = (RulesASTNode)adaptor.create(char_literal38);
                    adaptor.addChild(root_0, char_literal38_tree);
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:148:10: ']'
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    char_literal39=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_rangeEnd1055); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal39_tree = (RulesASTNode)adaptor.create(char_literal39);
                    adaptor.addChild(root_0, char_literal39_tree);
                    }
                    if ( state.backtracking==0 ) {
                       arrayDepth--; 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rangeEnd"

    public static class setMembershipExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "setMembershipExpression"
    // BaseElement.g:151:1: setMembershipExpression : LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) ) ;
    public final Rules_BaseElement.setMembershipExpression_return setMembershipExpression() throws RecognitionException {
        Rules_BaseElement.setMembershipExpression_return retval = new Rules_BaseElement.setMembershipExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE40=null;
        Token char_literal41=null;
        Token RBRACE42=null;
        List list_exp=null;
        RuleReturnScope exp = null;
        RulesASTNode LBRACE40_tree=null;
        RulesASTNode char_literal41_tree=null;
        RulesASTNode RBRACE42_tree=null;
        RewriteRuleTokenStream stream_216=new RewriteRuleTokenStream(adaptor,"token 216");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:152:2: ( LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) ) )
            // BaseElement.g:152:4: LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE
            {
            LBRACE40=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_setMembershipExpression1069); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE40);

            // BaseElement.g:152:11: (exp+= expression ( ',' exp+= expression )* )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==Identifier||(LA22_0>=EQ && LA22_0<=LBRACE)||(LA22_0>=PLUS && LA22_0<=MINUS)||LA22_0==POUND||(LA22_0>=FloatingPointLiteral && LA22_0<=FALSE)||LA22_0==LPAREN||LA22_0==LBRACK||(LA22_0>=164 && LA22_0<=212)||(LA22_0>=228 && LA22_0<=231)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // BaseElement.g:152:12: exp+= expression ( ',' exp+= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_setMembershipExpression1074);
                    exp=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    if (list_exp==null) list_exp=new ArrayList();
                    list_exp.add(exp.getTree());

                    // BaseElement.g:152:28: ( ',' exp+= expression )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==216) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // BaseElement.g:152:29: ',' exp+= expression
                    	    {
                    	    char_literal41=(Token)match(input,216,FOLLOW_216_in_setMembershipExpression1077); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_216.add(char_literal41);

                    	    pushFollow(FOLLOW_expression_in_setMembershipExpression1081);
                    	    exp=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    	    if (list_exp==null) list_exp=new ArrayList();
                    	    list_exp.add(exp.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);


                    }
                    break;

            }

            RBRACE42=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_setMembershipExpression1087); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE42);



            // AST REWRITE
            // elements: exp
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: exp
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_exp=new RewriteRuleSubtreeStream(adaptor,"token exp",list_exp);
            root_0 = (RulesASTNode)adaptor.nil();
            // 153:3: -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) )
            {
                // BaseElement.g:153:6: ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SET_MEMBERSHIP_EXPRESSION, "SET_MEMBERSHIP_EXPRESSION"), root_1);

                // BaseElement.g:153:34: ^( EXPRESSIONS ( $exp)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

                // BaseElement.g:153:48: ( $exp)*
                while ( stream_exp.hasNext() ) {
                    adaptor.addChild(root_2, stream_exp.nextTree());

                }
                stream_exp.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "setMembershipExpression"

    public static class relationalExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpression"
    // BaseElement.g:156:1: relationalExpression : lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )* ;
    public final Rules_BaseElement.relationalExpression_return relationalExpression() throws RecognitionException {
        Rules_BaseElement.relationalExpression_return retval = new Rules_BaseElement.relationalExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set43=null;
        Token INSTANCE_OF44=null;
        Rules_BaseElement.additiveExpression_return lhs = null;

        Rules_BaseElement.additiveExpression_return rhs = null;

        Rules_BaseElement.type_return type45 = null;


        RulesASTNode set43_tree=null;
        RulesASTNode INSTANCE_OF44_tree=null;

        try {
            // BaseElement.g:157:2: (lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )* )
            // BaseElement.g:157:4: lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1116);
            lhs=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs.getTree());
            // BaseElement.g:158:2: ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=INSTANCE_OF && LA24_0<=GE)) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // BaseElement.g:159:3: ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type )
            	    {
            	    // BaseElement.g:159:3: ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type )
            	    int alt23=2;
            	    int LA23_0 = input.LA(1);

            	    if ( ((LA23_0>=LT && LA23_0<=GE)) ) {
            	        alt23=1;
            	    }
            	    else if ( (LA23_0==INSTANCE_OF) ) {
            	        alt23=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 23, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt23) {
            	        case 1 :
            	            // BaseElement.g:159:4: ( LT | GT | LE | GE ) rhs= additiveExpression
            	            {
            	            set43=(Token)input.LT(1);
            	            set43=(Token)input.LT(1);
            	            if ( (input.LA(1)>=LT && input.LA(1)<=GE) ) {
            	                input.consume();
            	                if ( state.backtracking==0 ) root_0 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(set43), root_0);
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                throw mse;
            	            }

            	            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1143);
            	            rhs=additiveExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, rhs.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // BaseElement.g:160:5: INSTANCE_OF type
            	            {
            	            INSTANCE_OF44=(Token)match(input,INSTANCE_OF,FOLLOW_INSTANCE_OF_in_relationalExpression1149); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            INSTANCE_OF44_tree = (RulesASTNode)adaptor.create(INSTANCE_OF44);
            	            root_0 = (RulesASTNode)adaptor.becomeRoot(INSTANCE_OF44_tree, root_0);
            	            }
            	            pushFollow(FOLLOW_type_in_relationalExpression1152);
            	            type45=type();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, type45.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalExpression"

    public static class additiveExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpression"
    // BaseElement.g:165:1: additiveExpression : multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )* ;
    public final Rules_BaseElement.additiveExpression_return additiveExpression() throws RecognitionException {
        Rules_BaseElement.additiveExpression_return retval = new Rules_BaseElement.additiveExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set47=null;
        Rules_BaseElement.multiplicativeExpression_return rhs = null;

        Rules_BaseElement.multiplicativeExpression_return multiplicativeExpression46 = null;


        RulesASTNode set47_tree=null;

        try {
            // BaseElement.g:166:2: ( multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )* )
            // BaseElement.g:166:4: multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1172);
            multiplicativeExpression46=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression46.getTree());
            // BaseElement.g:167:2: ( ( PLUS | MINUS ) rhs= multiplicativeExpression )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=PLUS && LA25_0<=MINUS)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // BaseElement.g:167:4: ( PLUS | MINUS ) rhs= multiplicativeExpression
            	    {
            	    set47=(Token)input.LT(1);
            	    set47=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) root_0 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(set47), root_0);
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1188);
            	    rhs=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, rhs.getTree());

            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "additiveExpression"

    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpression"
    // BaseElement.g:172:1: multiplicativeExpression : unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )* ;
    public final Rules_BaseElement.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        Rules_BaseElement.multiplicativeExpression_return retval = new Rules_BaseElement.multiplicativeExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set49=null;
        Rules_BaseElement.unaryExpression_return rhs = null;

        Rules_BaseElement.unaryExpression_return unaryExpression48 = null;


        RulesASTNode set49_tree=null;

        try {
            // BaseElement.g:173:2: ( unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )* )
            // BaseElement.g:173:4: unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1207);
            unaryExpression48=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression48.getTree());
            // BaseElement.g:174:2: ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=MULT && LA26_0<=MOD)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // BaseElement.g:174:4: ( MULT | DIVIDE | MOD ) rhs= unaryExpression
            	    {
            	    set49=(Token)input.LT(1);
            	    set49=(Token)input.LT(1);
            	    if ( (input.LA(1)>=MULT && input.LA(1)<=MOD) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) root_0 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(set49), root_0);
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1227);
            	    rhs=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, rhs.getTree());

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpression"

    public static class unaryExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpression"
    // BaseElement.g:179:1: unaryExpression : ( (op= PLUS | op= MINUS | op= POUND ) unaryExpression -> ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) ) | primaryExpression );
    public final Rules_BaseElement.unaryExpression_return unaryExpression() throws RecognitionException {
        Rules_BaseElement.unaryExpression_return retval = new Rules_BaseElement.unaryExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token op=null;
        Rules_BaseElement.unaryExpression_return unaryExpression50 = null;

        Rules_BaseElement.primaryExpression_return primaryExpression51 = null;


        RulesASTNode op_tree=null;
        RewriteRuleTokenStream stream_PLUS=new RewriteRuleTokenStream(adaptor,"token PLUS");
        RewriteRuleTokenStream stream_MINUS=new RewriteRuleTokenStream(adaptor,"token MINUS");
        RewriteRuleTokenStream stream_POUND=new RewriteRuleTokenStream(adaptor,"token POUND");
        RewriteRuleSubtreeStream stream_unaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpression");
        try {
            // BaseElement.g:180:2: ( (op= PLUS | op= MINUS | op= POUND ) unaryExpression -> ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) ) | primaryExpression )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=PLUS && LA28_0<=MINUS)||LA28_0==POUND) ) {
                alt28=1;
            }
            else if ( (LA28_0==Identifier||(LA28_0>=FloatingPointLiteral && LA28_0<=FALSE)||LA28_0==LPAREN||(LA28_0>=164 && LA28_0<=212)||(LA28_0>=228 && LA28_0<=231)) ) {
                alt28=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // BaseElement.g:180:4: (op= PLUS | op= MINUS | op= POUND ) unaryExpression
                    {
                    // BaseElement.g:180:4: (op= PLUS | op= MINUS | op= POUND )
                    int alt27=3;
                    switch ( input.LA(1) ) {
                    case PLUS:
                        {
                        alt27=1;
                        }
                        break;
                    case MINUS:
                        {
                        alt27=2;
                        }
                        break;
                    case POUND:
                        {
                        alt27=3;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 0, input);

                        throw nvae;
                    }

                    switch (alt27) {
                        case 1 :
                            // BaseElement.g:180:5: op= PLUS
                            {
                            op=(Token)match(input,PLUS,FOLLOW_PLUS_in_unaryExpression1249); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_PLUS.add(op);


                            }
                            break;
                        case 2 :
                            // BaseElement.g:180:15: op= MINUS
                            {
                            op=(Token)match(input,MINUS,FOLLOW_MINUS_in_unaryExpression1255); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_MINUS.add(op);


                            }
                            break;
                        case 3 :
                            // BaseElement.g:180:26: op= POUND
                            {
                            op=(Token)match(input,POUND,FOLLOW_POUND_in_unaryExpression1261); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_POUND.add(op);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1264);
                    unaryExpression50=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryExpression.add(unaryExpression50.getTree());


                    // AST REWRITE
                    // elements: op, unaryExpression
                    // token labels: op
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 181:3: -> ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) )
                    {
                        // BaseElement.g:181:6: ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(UNARY_EXPRESSION_NODE, "UNARY_EXPRESSION_NODE"), root_1);

                        // BaseElement.g:181:30: ^( EXPRESSION unaryExpression )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        adaptor.addChild(root_2, stream_unaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:181:60: ^( OPERATOR $op)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

                        adaptor.addChild(root_2, stream_op.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // BaseElement.g:182:4: primaryExpression
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_primaryExpression_in_unaryExpression1291);
                    primaryExpression51=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primaryExpression51.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryExpression"

    public static class primaryExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryExpression"
    // BaseElement.g:185:1: primaryExpression : p= primaryPrefix (s+= primarySuffix[(RulesASTNode) p.getTree()] )* -> ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) ) ;
    public final Rules_BaseElement.primaryExpression_return primaryExpression() throws RecognitionException {
        Rules_BaseElement.primaryExpression_return retval = new Rules_BaseElement.primaryExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        List list_s=null;
        Rules_BaseElement.primaryPrefix_return p = null;

        RuleReturnScope s = null;
        RewriteRuleSubtreeStream stream_primaryPrefix=new RewriteRuleSubtreeStream(adaptor,"rule primaryPrefix");
        RewriteRuleSubtreeStream stream_primarySuffix=new RewriteRuleSubtreeStream(adaptor,"rule primarySuffix");
        try {
            // BaseElement.g:186:2: (p= primaryPrefix (s+= primarySuffix[(RulesASTNode) p.getTree()] )* -> ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) ) )
            // BaseElement.g:186:4: p= primaryPrefix (s+= primarySuffix[(RulesASTNode) p.getTree()] )*
            {
            pushFollow(FOLLOW_primaryPrefix_in_primaryExpression1305);
            p=primaryPrefix();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_primaryPrefix.add(p.getTree());
            // BaseElement.g:186:20: (s+= primarySuffix[(RulesASTNode) p.getTree()] )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=DOT && LA29_0<=ANNOTATE)||LA29_0==LBRACK) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // BaseElement.g:186:21: s+= primarySuffix[(RulesASTNode) p.getTree()]
            	    {
            	    pushFollow(FOLLOW_primarySuffix_in_primaryExpression1310);
            	    s=primarySuffix((RulesASTNode) p.getTree());

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_primarySuffix.add(s.getTree());
            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s.getTree());


            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);



            // AST REWRITE
            // elements: s, p
            // token labels: 
            // rule labels: retval, p
            // token list labels: 
            // rule list labels: s
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_p=new RewriteRuleSubtreeStream(adaptor,"rule p",p!=null?p.tree:null);
            RewriteRuleSubtreeStream stream_s=new RewriteRuleSubtreeStream(adaptor,"token s",list_s);
            root_0 = (RulesASTNode)adaptor.nil();
            // 187:2: -> ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) )
            {
                // BaseElement.g:187:5: ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIMARY_EXPRESSION, "PRIMARY_EXPRESSION"), root_1);

                // BaseElement.g:187:26: ^( PREFIX $p)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PREFIX, "PREFIX"), root_2);

                adaptor.addChild(root_2, stream_p.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:187:39: ^( SUFFIXES ( $s)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SUFFIXES, "SUFFIXES"), root_2);

                // BaseElement.g:187:50: ( $s)*
                while ( stream_s.hasNext() ) {
                    adaptor.addChild(root_2, stream_s.nextTree());

                }
                stream_s.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primaryExpression"

    public static class primaryPrefix_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryPrefix"
    // BaseElement.g:190:1: primaryPrefix : ( literal | '(' expression ')' | ( type LBRACE )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=>m= methodName argumentsSuffix -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ) | expressionName );
    public final Rules_BaseElement.primaryPrefix_return primaryPrefix() throws RecognitionException {
        Rules_BaseElement.primaryPrefix_return retval = new Rules_BaseElement.primaryPrefix_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal53=null;
        Token char_literal55=null;
        Rules_BaseElement.methodName_return m = null;

        Rules_BaseElement.literal_return literal52 = null;

        Rules_BaseElement.expression_return expression54 = null;

        Rules_BaseElement.arrayLiteral_return arrayLiteral56 = null;

        Rules_BaseElement.arrayAllocator_return arrayAllocator57 = null;

        Rules_BaseElement.argumentsSuffix_return argumentsSuffix58 = null;

        Rules_BaseElement.expressionName_return expressionName59 = null;


        RulesASTNode char_literal53_tree=null;
        RulesASTNode char_literal55_tree=null;
        RewriteRuleSubtreeStream stream_argumentsSuffix=new RewriteRuleSubtreeStream(adaptor,"rule argumentsSuffix");
        RewriteRuleSubtreeStream stream_methodName=new RewriteRuleSubtreeStream(adaptor,"rule methodName");
        try {
            // BaseElement.g:191:2: ( literal | '(' expression ')' | ( type LBRACE )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=>m= methodName argumentsSuffix -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ) | expressionName )
            int alt30=6;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // BaseElement.g:191:4: literal
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_primaryPrefix1347);
                    literal52=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal52.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:192:4: '(' expression ')'
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    char_literal53=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_primaryPrefix1352); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_primaryPrefix1355);
                    expression54=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression54.getTree());
                    char_literal55=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_primaryPrefix1357); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // BaseElement.g:193:4: ( type LBRACE )=> arrayLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayLiteral_in_primaryPrefix1369);
                    arrayLiteral56=arrayLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayLiteral56.getTree());

                    }
                    break;
                case 4 :
                    // BaseElement.g:194:4: ( arrayAllocator )=> arrayAllocator
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayAllocator_in_primaryPrefix1378);
                    arrayAllocator57=arrayAllocator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAllocator57.getTree());

                    }
                    break;
                case 5 :
                    // BaseElement.g:195:4: ( methodName '(' )=>m= methodName argumentsSuffix
                    {
                    pushFollow(FOLLOW_methodName_in_primaryPrefix1391);
                    m=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodName.add(m.getTree());
                    pushFollow(FOLLOW_argumentsSuffix_in_primaryPrefix1393);
                    argumentsSuffix58=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix58.getTree());
                    if ( state.backtracking==0 ) {
                       markAsMethod((RulesASTNode) m.getTree()); 
                    }


                    // AST REWRITE
                    // elements: argumentsSuffix, m
                    // token labels: 
                    // rule labels: retval, m
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 196:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) )
                    {
                        // BaseElement.g:196:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:196:20: ^( NAME $m)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:196:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARGS, "ARGS"), root_2);

                        // BaseElement.g:196:38: ( argumentsSuffix )?
                        if ( stream_argumentsSuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_argumentsSuffix.nextTree());

                        }
                        stream_argumentsSuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // BaseElement.g:197:4: expressionName
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_expressionName_in_primaryPrefix1422);
                    expressionName59=expressionName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionName59.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primaryPrefix"

    public static class primarySuffix_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primarySuffix"
    // BaseElement.g:200:1: primarySuffix[RulesASTNode type] : ( arrayAccessSuffix | fieldAccessSuffix );
    public final Rules_BaseElement.primarySuffix_return primarySuffix(RulesASTNode type) throws RecognitionException {
        Rules_BaseElement.primarySuffix_return retval = new Rules_BaseElement.primarySuffix_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.arrayAccessSuffix_return arrayAccessSuffix60 = null;

        Rules_BaseElement.fieldAccessSuffix_return fieldAccessSuffix61 = null;



        try {
            // BaseElement.g:201:2: ( arrayAccessSuffix | fieldAccessSuffix )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==LBRACK) ) {
                alt31=1;
            }
            else if ( ((LA31_0>=DOT && LA31_0<=ANNOTATE)) ) {
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
                    // BaseElement.g:201:4: arrayAccessSuffix
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayAccessSuffix_in_primarySuffix1435);
                    arrayAccessSuffix60=arrayAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccessSuffix60.getTree());
                    if ( state.backtracking==0 ) {
                       markAsArray(type); 
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:201:47: fieldAccessSuffix
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_fieldAccessSuffix_in_primarySuffix1441);
                    fieldAccessSuffix61=fieldAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fieldAccessSuffix61.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primarySuffix"

    public static class arrayAccessSuffix_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAccessSuffix"
    // BaseElement.g:204:1: arrayAccessSuffix : '[' expression ']' -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) ) ;
    public final Rules_BaseElement.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException {
        Rules_BaseElement.arrayAccessSuffix_return retval = new Rules_BaseElement.arrayAccessSuffix_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal62=null;
        Token char_literal64=null;
        Rules_BaseElement.expression_return expression63 = null;


        RulesASTNode char_literal62_tree=null;
        RulesASTNode char_literal64_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:205:2: ( '[' expression ']' -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) ) )
            // BaseElement.g:205:4: '[' expression ']'
            {
            char_literal62=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_arrayAccessSuffix1453); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACK.add(char_literal62);

            if ( state.backtracking==0 ) {
               arrayDepth++; 
            }
            pushFollow(FOLLOW_expression_in_arrayAccessSuffix1457);
            expression63=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression63.getTree());
            char_literal64=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_arrayAccessSuffix1459); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACK.add(char_literal64);

            if ( state.backtracking==0 ) {
               arrayDepth--; 
            }


            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 206:3: -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) )
            {
                // BaseElement.g:206:6: ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARRAY_ACCESS_SUFFIX, "ARRAY_ACCESS_SUFFIX"), root_1);

                // BaseElement.g:206:28: ^( EXPRESSION expression )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayAccessSuffix"

    public static class fieldAccessSuffix_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldAccessSuffix"
    // BaseElement.g:209:1: fieldAccessSuffix : ( DOT | ANNOTATE ) id= identifier ;
    public final Rules_BaseElement.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException {
        Rules_BaseElement.fieldAccessSuffix_return retval = new Rules_BaseElement.fieldAccessSuffix_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token DOT65=null;
        Token ANNOTATE66=null;
        RulesParser.identifier_return id = null;


        RulesASTNode DOT65_tree=null;
        RulesASTNode ANNOTATE66_tree=null;

         
        boolean attributeRef = false; 

        try {
            // BaseElement.g:213:2: ( ( DOT | ANNOTATE ) id= identifier )
            // BaseElement.g:213:4: ( DOT | ANNOTATE ) id= identifier
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // BaseElement.g:213:4: ( DOT | ANNOTATE )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==DOT) ) {
                alt32=1;
            }
            else if ( (LA32_0==ANNOTATE) ) {
                alt32=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // BaseElement.g:213:5: DOT
                    {
                    DOT65=(Token)match(input,DOT,FOLLOW_DOT_in_fieldAccessSuffix1493); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // BaseElement.g:213:12: ANNOTATE
                    {
                    ANNOTATE66=(Token)match(input,ANNOTATE,FOLLOW_ANNOTATE_in_fieldAccessSuffix1498); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       attributeRef = true; 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_identifier_in_fieldAccessSuffix1506);
            id=gRules.identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
            if ( state.backtracking==0 ) {
               pushFieldAccessReference((RulesASTNode)id.getTree(), attributeRef); 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fieldAccessSuffix"

    public static class argumentsSuffix_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentsSuffix"
    // BaseElement.g:216:1: argumentsSuffix : '(' ( argumentList )? ')' ;
    public final Rules_BaseElement.argumentsSuffix_return argumentsSuffix() throws RecognitionException {
        Rules_BaseElement.argumentsSuffix_return retval = new Rules_BaseElement.argumentsSuffix_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal67=null;
        Token char_literal69=null;
        Rules_BaseElement.argumentList_return argumentList68 = null;


        RulesASTNode char_literal67_tree=null;
        RulesASTNode char_literal69_tree=null;

        try {
            // BaseElement.g:217:2: ( '(' ( argumentList )? ')' )
            // BaseElement.g:217:4: '(' ( argumentList )? ')'
            {
            root_0 = (RulesASTNode)adaptor.nil();

            char_literal67=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_argumentsSuffix1521); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               arrayDepth++; 
            }
            // BaseElement.g:217:27: ( argumentList )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==Identifier||(LA33_0>=EQ && LA33_0<=LBRACE)||(LA33_0>=PLUS && LA33_0<=MINUS)||LA33_0==POUND||(LA33_0>=FloatingPointLiteral && LA33_0<=FALSE)||LA33_0==LPAREN||LA33_0==LBRACK||(LA33_0>=164 && LA33_0<=213)||(LA33_0>=228 && LA33_0<=231)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // BaseElement.g:217:27: argumentList
                    {
                    pushFollow(FOLLOW_argumentList_in_argumentsSuffix1526);
                    argumentList68=argumentList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argumentList68.getTree());

                    }
                    break;

            }

            char_literal69=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_argumentsSuffix1529); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               arrayDepth--; 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argumentsSuffix"

    public static class argumentList_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentList"
    // BaseElement.g:220:1: argumentList : ( expression ( ',' expression )* | xsltLiteral );
    public final Rules_BaseElement.argumentList_return argumentList() throws RecognitionException {
        Rules_BaseElement.argumentList_return retval = new Rules_BaseElement.argumentList_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal71=null;
        Rules_BaseElement.expression_return expression70 = null;

        Rules_BaseElement.expression_return expression72 = null;

        RulesParser.xsltLiteral_return xsltLiteral73 = null;


        RulesASTNode char_literal71_tree=null;

        try {
            // BaseElement.g:221:2: ( expression ( ',' expression )* | xsltLiteral )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==Identifier||(LA35_0>=EQ && LA35_0<=LBRACE)||(LA35_0>=PLUS && LA35_0<=MINUS)||LA35_0==POUND||(LA35_0>=FloatingPointLiteral && LA35_0<=FALSE)||LA35_0==LPAREN||LA35_0==LBRACK||(LA35_0>=164 && LA35_0<=212)||(LA35_0>=228 && LA35_0<=231)) ) {
                alt35=1;
            }
            else if ( (LA35_0==213) ) {
                alt35=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // BaseElement.g:221:4: expression ( ',' expression )*
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_argumentList1545);
                    expression70=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression70.getTree());
                    // BaseElement.g:221:15: ( ',' expression )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==216) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // BaseElement.g:221:16: ',' expression
                    	    {
                    	    char_literal71=(Token)match(input,216,FOLLOW_216_in_argumentList1548); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_expression_in_argumentList1551);
                    	    expression72=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression72.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // BaseElement.g:222:4: xsltLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_xsltLiteral_in_argumentList1558);
                    xsltLiteral73=gRules.xsltLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, xsltLiteral73.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argumentList"

    public static class literal_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // BaseElement.g:225:1: literal : ( integerLiteral | FloatingPointLiteral | StringLiteral | booleanLiteral | NullLiteral );
    public final Rules_BaseElement.literal_return literal() throws RecognitionException {
        Rules_BaseElement.literal_return retval = new Rules_BaseElement.literal_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token FloatingPointLiteral75=null;
        Token StringLiteral76=null;
        Token NullLiteral78=null;
        Rules_BaseElement.integerLiteral_return integerLiteral74 = null;

        Rules_BaseElement.booleanLiteral_return booleanLiteral77 = null;


        RulesASTNode FloatingPointLiteral75_tree=null;
        RulesASTNode StringLiteral76_tree=null;
        RulesASTNode NullLiteral78_tree=null;

        try {
            // BaseElement.g:226:2: ( integerLiteral | FloatingPointLiteral | StringLiteral | booleanLiteral | NullLiteral )
            int alt36=5;
            switch ( input.LA(1) ) {
            case DecimalLiteral:
            case HexLiteral:
                {
                alt36=1;
                }
                break;
            case FloatingPointLiteral:
                {
                alt36=2;
                }
                break;
            case StringLiteral:
                {
                alt36=3;
                }
                break;
            case TRUE:
            case FALSE:
                {
                alt36=4;
                }
                break;
            case NullLiteral:
                {
                alt36=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // BaseElement.g:226:4: integerLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_integerLiteral_in_literal1570);
                    integerLiteral74=integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, integerLiteral74.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:227:4: FloatingPointLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    FloatingPointLiteral75=(Token)match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_literal1575); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FloatingPointLiteral75_tree = (RulesASTNode)adaptor.create(FloatingPointLiteral75);
                    adaptor.addChild(root_0, FloatingPointLiteral75_tree);
                    }

                    }
                    break;
                case 3 :
                    // BaseElement.g:228:4: StringLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    StringLiteral76=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_literal1580); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    StringLiteral76_tree = (RulesASTNode)adaptor.create(StringLiteral76);
                    adaptor.addChild(root_0, StringLiteral76_tree);
                    }

                    }
                    break;
                case 4 :
                    // BaseElement.g:229:4: booleanLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_booleanLiteral_in_literal1585);
                    booleanLiteral77=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booleanLiteral77.getTree());

                    }
                    break;
                case 5 :
                    // BaseElement.g:230:4: NullLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    NullLiteral78=(Token)match(input,NullLiteral,FOLLOW_NullLiteral_in_literal1590); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NullLiteral78_tree = (RulesASTNode)adaptor.create(NullLiteral78);
                    adaptor.addChild(root_0, NullLiteral78_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class integerLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "integerLiteral"
    // BaseElement.g:233:1: integerLiteral : ( DecimalLiteral | HexLiteral );
    public final Rules_BaseElement.integerLiteral_return integerLiteral() throws RecognitionException {
        Rules_BaseElement.integerLiteral_return retval = new Rules_BaseElement.integerLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set79=null;

        RulesASTNode set79_tree=null;

        try {
            // BaseElement.g:234:2: ( DecimalLiteral | HexLiteral )
            // BaseElement.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set79=(Token)input.LT(1);
            if ( (input.LA(1)>=DecimalLiteral && input.LA(1)<=HexLiteral) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set79));
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

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "integerLiteral"

    public static class booleanLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanLiteral"
    // BaseElement.g:239:1: booleanLiteral : ( TRUE | FALSE );
    public final Rules_BaseElement.booleanLiteral_return booleanLiteral() throws RecognitionException {
        Rules_BaseElement.booleanLiteral_return retval = new Rules_BaseElement.booleanLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set80=null;

        RulesASTNode set80_tree=null;

        try {
            // BaseElement.g:240:2: ( TRUE | FALSE )
            // BaseElement.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set80=(Token)input.LT(1);
            if ( (input.LA(1)>=TRUE && input.LA(1)<=FALSE) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set80));
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

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "booleanLiteral"

    public static class emptyStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyStatement"
    // BaseElement.g:243:1: emptyStatement : SEMICOLON ;
    public final Rules_BaseElement.emptyStatement_return emptyStatement() throws RecognitionException {
        Rules_BaseElement.emptyStatement_return retval = new Rules_BaseElement.emptyStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token SEMICOLON81=null;

        RulesASTNode SEMICOLON81_tree=null;

        try {
            // BaseElement.g:244:2: ( SEMICOLON )
            // BaseElement.g:244:4: SEMICOLON
            {
            root_0 = (RulesASTNode)adaptor.nil();

            SEMICOLON81=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_emptyStatement1635); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON81_tree = (RulesASTNode)adaptor.create(SEMICOLON81);
            adaptor.addChild(root_0, SEMICOLON81_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "emptyStatement"

    public static class emptyBodyStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyBodyStatement"
    // BaseElement.g:247:1: emptyBodyStatement : WS ;
    public final Rules_BaseElement.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException {
        Rules_BaseElement.emptyBodyStatement_return retval = new Rules_BaseElement.emptyBodyStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token WS82=null;

        RulesASTNode WS82_tree=null;

        try {
            // BaseElement.g:248:2: ( WS )
            // BaseElement.g:248:4: WS
            {
            root_0 = (RulesASTNode)adaptor.nil();

            WS82=(Token)match(input,WS,FOLLOW_WS_in_emptyBodyStatement1647); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WS82_tree = (RulesASTNode)adaptor.create(WS82);
            adaptor.addChild(root_0, WS82_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "emptyBodyStatement"

    public static class statementExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpression"
    // BaseElement.g:259:1: statementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? SEMICOLON -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) SEMICOLON -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );
    public final Rules_BaseElement.statementExpression_return statementExpression() throws RecognitionException {
        Rules_BaseElement.statementExpression_return retval = new Rules_BaseElement.statementExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token op=null;
        Token SEMICOLON86=null;
        Token SEMICOLON89=null;
        Rules_BaseElement.methodName_return m = null;

        Rules_BaseElement.assignmentOp_return assignOp = null;

        Rules_BaseElement.argumentsSuffix_return argumentsSuffix83 = null;

        Rules_BaseElement.primarySuffix_return primarySuffix84 = null;

        Rules_BaseElement.expression_return expression85 = null;

        Rules_BaseElement.primaryExpression_return primaryExpression87 = null;

        Rules_BaseElement.expression_return expression88 = null;


        RulesASTNode op_tree=null;
        RulesASTNode SEMICOLON86_tree=null;
        RulesASTNode SEMICOLON89_tree=null;
        RewriteRuleTokenStream stream_DECR=new RewriteRuleTokenStream(adaptor,"token DECR");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_INCR=new RewriteRuleTokenStream(adaptor,"token INCR");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_argumentsSuffix=new RewriteRuleSubtreeStream(adaptor,"rule argumentsSuffix");
        RewriteRuleSubtreeStream stream_primaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule primaryExpression");
        RewriteRuleSubtreeStream stream_methodName=new RewriteRuleSubtreeStream(adaptor,"rule methodName");
        RewriteRuleSubtreeStream stream_primarySuffix=new RewriteRuleSubtreeStream(adaptor,"rule primarySuffix");
        RewriteRuleSubtreeStream stream_assignmentOp=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOp");
        try {
            // BaseElement.g:260:2: ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? SEMICOLON -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) SEMICOLON -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) )
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // BaseElement.g:260:4: ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? SEMICOLON
                    {
                    pushFollow(FOLLOW_methodName_in_statementExpression1675);
                    m=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodName.add(m.getTree());
                    pushFollow(FOLLOW_argumentsSuffix_in_statementExpression1677);
                    argumentsSuffix83=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix83.getTree());
                    // BaseElement.g:261:2: ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( ((LA40_0>=DOT && LA40_0<=ANNOTATE)||(LA40_0>=INCR && LA40_0<=MOD_EQUAL)||LA40_0==LBRACK) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // BaseElement.g:261:4: ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                            {
                            // BaseElement.g:261:4: ( primarySuffix[(RulesASTNode) m.getTree()] )*
                            loop37:
                            do {
                                int alt37=2;
                                int LA37_0 = input.LA(1);

                                if ( ((LA37_0>=DOT && LA37_0<=ANNOTATE)||LA37_0==LBRACK) ) {
                                    alt37=1;
                                }


                                switch (alt37) {
                            	case 1 :
                            	    // BaseElement.g:261:4: primarySuffix[(RulesASTNode) m.getTree()]
                            	    {
                            	    pushFollow(FOLLOW_primarySuffix_in_statementExpression1682);
                            	    primarySuffix84=primarySuffix((RulesASTNode) m.getTree());

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_primarySuffix.add(primarySuffix84.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop37;
                                }
                            } while (true);

                            // BaseElement.g:261:47: (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                            int alt39=2;
                            int LA39_0 = input.LA(1);

                            if ( ((LA39_0>=ASSIGN && LA39_0<=MOD_EQUAL)) ) {
                                alt39=1;
                            }
                            else if ( ((LA39_0>=INCR && LA39_0<=DECR)) ) {
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
                                    // BaseElement.g:261:48: assignOp= assignmentOp expression
                                    {
                                    pushFollow(FOLLOW_assignmentOp_in_statementExpression1689);
                                    assignOp=assignmentOp();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_assignmentOp.add(assignOp.getTree());
                                    pushFollow(FOLLOW_expression_in_statementExpression1691);
                                    expression85=expression();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_expression.add(expression85.getTree());

                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:261:83: (op= INCR | op= DECR )
                                    {
                                    // BaseElement.g:261:83: (op= INCR | op= DECR )
                                    int alt38=2;
                                    int LA38_0 = input.LA(1);

                                    if ( (LA38_0==INCR) ) {
                                        alt38=1;
                                    }
                                    else if ( (LA38_0==DECR) ) {
                                        alt38=2;
                                    }
                                    else {
                                        if (state.backtracking>0) {state.failed=true; return retval;}
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 38, 0, input);

                                        throw nvae;
                                    }
                                    switch (alt38) {
                                        case 1 :
                                            // BaseElement.g:261:84: op= INCR
                                            {
                                            op=(Token)match(input,INCR,FOLLOW_INCR_in_statementExpression1698); if (state.failed) return retval; 
                                            if ( state.backtracking==0 ) stream_INCR.add(op);


                                            }
                                            break;
                                        case 2 :
                                            // BaseElement.g:261:94: op= DECR
                                            {
                                            op=(Token)match(input,DECR,FOLLOW_DECR_in_statementExpression1704); if (state.failed) return retval; 
                                            if ( state.backtracking==0 ) stream_DECR.add(op);


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    SEMICOLON86=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statementExpression1710); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON86);



                    // AST REWRITE
                    // elements: m, expression, primarySuffix, op, assignOp, argumentsSuffix
                    // token labels: op
                    // rule labels: retval, assignOp, m
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_assignOp=new RewriteRuleSubtreeStream(adaptor,"rule assignOp",assignOp!=null?assignOp.tree:null);
                    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 262:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:262:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:262:20: ^( NAME $m)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:262:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARGS, "ARGS"), root_2);

                        // BaseElement.g:262:38: ( argumentsSuffix )?
                        if ( stream_argumentsSuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_argumentsSuffix.nextTree());

                        }
                        stream_argumentsSuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:4: ^( SUFFIX ( primarySuffix )* )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SUFFIX, "SUFFIX"), root_2);

                        // BaseElement.g:263:13: ( primarySuffix )*
                        while ( stream_primarySuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_primarySuffix.nextTree());

                        }
                        stream_primarySuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:29: ^( EXPRESSION ( expression )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        // BaseElement.g:263:42: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:55: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

                        // BaseElement.g:263:66: ( $op)?
                        if ( stream_op.hasNext() ) {
                            adaptor.addChild(root_2, stream_op.nextNode());

                        }
                        stream_op.reset();
                        // BaseElement.g:263:71: ( $assignOp)?
                        if ( stream_assignOp.hasNext() ) {
                            adaptor.addChild(root_2, stream_assignOp.nextTree());

                        }
                        stream_assignOp.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // BaseElement.g:264:4: primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) SEMICOLON
                    {
                    pushFollow(FOLLOW_primaryExpression_in_statementExpression1766);
                    primaryExpression87=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primaryExpression.add(primaryExpression87.getTree());
                    // BaseElement.g:264:22: (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( ((LA42_0>=ASSIGN && LA42_0<=MOD_EQUAL)) ) {
                        alt42=1;
                    }
                    else if ( ((LA42_0>=INCR && LA42_0<=DECR)) ) {
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
                            // BaseElement.g:264:23: assignOp= assignmentOp expression
                            {
                            pushFollow(FOLLOW_assignmentOp_in_statementExpression1771);
                            assignOp=assignmentOp();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_assignmentOp.add(assignOp.getTree());
                            pushFollow(FOLLOW_expression_in_statementExpression1773);
                            expression88=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression88.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:264:58: (op= INCR | op= DECR )
                            {
                            // BaseElement.g:264:58: (op= INCR | op= DECR )
                            int alt41=2;
                            int LA41_0 = input.LA(1);

                            if ( (LA41_0==INCR) ) {
                                alt41=1;
                            }
                            else if ( (LA41_0==DECR) ) {
                                alt41=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 41, 0, input);

                                throw nvae;
                            }
                            switch (alt41) {
                                case 1 :
                                    // BaseElement.g:264:59: op= INCR
                                    {
                                    op=(Token)match(input,INCR,FOLLOW_INCR_in_statementExpression1780); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_INCR.add(op);


                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:264:69: op= DECR
                                    {
                                    op=(Token)match(input,DECR,FOLLOW_DECR_in_statementExpression1786); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_DECR.add(op);


                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    SEMICOLON89=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statementExpression1790); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON89);



                    // AST REWRITE
                    // elements: assignOp, expression, primaryExpression, op
                    // token labels: op
                    // rule labels: retval, assignOp
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_assignOp=new RewriteRuleSubtreeStream(adaptor,"rule assignOp",assignOp!=null?assignOp.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 265:3: -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:265:6: ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIMARY_ASSIGNMENT_EXPRESSION, "PRIMARY_ASSIGNMENT_EXPRESSION"), root_1);

                        // BaseElement.g:265:38: ^( LHS primaryExpression )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LHS, "LHS"), root_2);

                        adaptor.addChild(root_2, stream_primaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:265:63: ^( RHS ( expression )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RHS, "RHS"), root_2);

                        // BaseElement.g:265:69: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:265:82: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

                        // BaseElement.g:265:93: ( $op)?
                        if ( stream_op.hasNext() ) {
                            adaptor.addChild(root_2, stream_op.nextNode());

                        }
                        stream_op.reset();
                        // BaseElement.g:265:98: ( $assignOp)?
                        if ( stream_assignOp.hasNext() ) {
                            adaptor.addChild(root_2, stream_assignOp.nextTree());

                        }
                        stream_assignOp.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementExpression"

    public static class listStatementExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "listStatementExpression"
    // BaseElement.g:268:1: listStatementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );
    public final Rules_BaseElement.listStatementExpression_return listStatementExpression() throws RecognitionException {
        Rules_BaseElement.listStatementExpression_return retval = new Rules_BaseElement.listStatementExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token op=null;
        Rules_BaseElement.methodName_return m = null;

        Rules_BaseElement.assignmentOp_return assignOp = null;

        Rules_BaseElement.argumentsSuffix_return argumentsSuffix90 = null;

        Rules_BaseElement.primarySuffix_return primarySuffix91 = null;

        Rules_BaseElement.expression_return expression92 = null;

        Rules_BaseElement.primaryExpression_return primaryExpression93 = null;

        Rules_BaseElement.expression_return expression94 = null;


        RulesASTNode op_tree=null;
        RewriteRuleTokenStream stream_DECR=new RewriteRuleTokenStream(adaptor,"token DECR");
        RewriteRuleTokenStream stream_INCR=new RewriteRuleTokenStream(adaptor,"token INCR");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_argumentsSuffix=new RewriteRuleSubtreeStream(adaptor,"rule argumentsSuffix");
        RewriteRuleSubtreeStream stream_primaryExpression=new RewriteRuleSubtreeStream(adaptor,"rule primaryExpression");
        RewriteRuleSubtreeStream stream_methodName=new RewriteRuleSubtreeStream(adaptor,"rule methodName");
        RewriteRuleSubtreeStream stream_primarySuffix=new RewriteRuleSubtreeStream(adaptor,"rule primarySuffix");
        RewriteRuleSubtreeStream stream_assignmentOp=new RewriteRuleSubtreeStream(adaptor,"rule assignmentOp");
        try {
            // BaseElement.g:269:2: ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) )
            int alt50=2;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // BaseElement.g:269:4: ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )?
                    {
                    pushFollow(FOLLOW_methodName_in_listStatementExpression1843);
                    m=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodName.add(m.getTree());
                    pushFollow(FOLLOW_argumentsSuffix_in_listStatementExpression1845);
                    argumentsSuffix90=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix90.getTree());
                    // BaseElement.g:270:2: ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( ((LA47_0>=DOT && LA47_0<=ANNOTATE)||(LA47_0>=INCR && LA47_0<=MOD_EQUAL)||LA47_0==LBRACK) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // BaseElement.g:270:4: ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                            {
                            // BaseElement.g:270:4: ( primarySuffix[(RulesASTNode) m.getTree()] )*
                            loop44:
                            do {
                                int alt44=2;
                                int LA44_0 = input.LA(1);

                                if ( ((LA44_0>=DOT && LA44_0<=ANNOTATE)||LA44_0==LBRACK) ) {
                                    alt44=1;
                                }


                                switch (alt44) {
                            	case 1 :
                            	    // BaseElement.g:270:4: primarySuffix[(RulesASTNode) m.getTree()]
                            	    {
                            	    pushFollow(FOLLOW_primarySuffix_in_listStatementExpression1850);
                            	    primarySuffix91=primarySuffix((RulesASTNode) m.getTree());

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_primarySuffix.add(primarySuffix91.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop44;
                                }
                            } while (true);

                            // BaseElement.g:270:47: (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                            int alt46=2;
                            int LA46_0 = input.LA(1);

                            if ( ((LA46_0>=ASSIGN && LA46_0<=MOD_EQUAL)) ) {
                                alt46=1;
                            }
                            else if ( ((LA46_0>=INCR && LA46_0<=DECR)) ) {
                                alt46=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 46, 0, input);

                                throw nvae;
                            }
                            switch (alt46) {
                                case 1 :
                                    // BaseElement.g:270:48: assignOp= assignmentOp expression
                                    {
                                    pushFollow(FOLLOW_assignmentOp_in_listStatementExpression1857);
                                    assignOp=assignmentOp();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_assignmentOp.add(assignOp.getTree());
                                    pushFollow(FOLLOW_expression_in_listStatementExpression1859);
                                    expression92=expression();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_expression.add(expression92.getTree());

                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:270:83: (op= INCR | op= DECR )
                                    {
                                    // BaseElement.g:270:83: (op= INCR | op= DECR )
                                    int alt45=2;
                                    int LA45_0 = input.LA(1);

                                    if ( (LA45_0==INCR) ) {
                                        alt45=1;
                                    }
                                    else if ( (LA45_0==DECR) ) {
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
                                            // BaseElement.g:270:84: op= INCR
                                            {
                                            op=(Token)match(input,INCR,FOLLOW_INCR_in_listStatementExpression1866); if (state.failed) return retval; 
                                            if ( state.backtracking==0 ) stream_INCR.add(op);


                                            }
                                            break;
                                        case 2 :
                                            // BaseElement.g:270:94: op= DECR
                                            {
                                            op=(Token)match(input,DECR,FOLLOW_DECR_in_listStatementExpression1872); if (state.failed) return retval; 
                                            if ( state.backtracking==0 ) stream_DECR.add(op);


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: op, argumentsSuffix, assignOp, primarySuffix, expression, m
                    // token labels: op
                    // rule labels: retval, assignOp, m
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_assignOp=new RewriteRuleSubtreeStream(adaptor,"rule assignOp",assignOp!=null?assignOp.tree:null);
                    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 271:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:271:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:271:20: ^( NAME $m)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:271:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARGS, "ARGS"), root_2);

                        // BaseElement.g:271:38: ( argumentsSuffix )?
                        if ( stream_argumentsSuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_argumentsSuffix.nextTree());

                        }
                        stream_argumentsSuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:4: ^( SUFFIX ( primarySuffix )* )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SUFFIX, "SUFFIX"), root_2);

                        // BaseElement.g:272:13: ( primarySuffix )*
                        while ( stream_primarySuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_primarySuffix.nextTree());

                        }
                        stream_primarySuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:29: ^( EXPRESSION ( expression )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        // BaseElement.g:272:42: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:55: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

                        // BaseElement.g:272:66: ( $op)?
                        if ( stream_op.hasNext() ) {
                            adaptor.addChild(root_2, stream_op.nextNode());

                        }
                        stream_op.reset();
                        // BaseElement.g:272:71: ( $assignOp)?
                        if ( stream_assignOp.hasNext() ) {
                            adaptor.addChild(root_2, stream_assignOp.nextTree());

                        }
                        stream_assignOp.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // BaseElement.g:273:4: primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                    {
                    pushFollow(FOLLOW_primaryExpression_in_listStatementExpression1932);
                    primaryExpression93=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primaryExpression.add(primaryExpression93.getTree());
                    // BaseElement.g:273:22: (assignOp= assignmentOp expression | (op= INCR | op= DECR ) )
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( ((LA49_0>=ASSIGN && LA49_0<=MOD_EQUAL)) ) {
                        alt49=1;
                    }
                    else if ( ((LA49_0>=INCR && LA49_0<=DECR)) ) {
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
                            // BaseElement.g:273:23: assignOp= assignmentOp expression
                            {
                            pushFollow(FOLLOW_assignmentOp_in_listStatementExpression1937);
                            assignOp=assignmentOp();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_assignmentOp.add(assignOp.getTree());
                            pushFollow(FOLLOW_expression_in_listStatementExpression1939);
                            expression94=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression94.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:273:58: (op= INCR | op= DECR )
                            {
                            // BaseElement.g:273:58: (op= INCR | op= DECR )
                            int alt48=2;
                            int LA48_0 = input.LA(1);

                            if ( (LA48_0==INCR) ) {
                                alt48=1;
                            }
                            else if ( (LA48_0==DECR) ) {
                                alt48=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 48, 0, input);

                                throw nvae;
                            }
                            switch (alt48) {
                                case 1 :
                                    // BaseElement.g:273:59: op= INCR
                                    {
                                    op=(Token)match(input,INCR,FOLLOW_INCR_in_listStatementExpression1946); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_INCR.add(op);


                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:273:69: op= DECR
                                    {
                                    op=(Token)match(input,DECR,FOLLOW_DECR_in_listStatementExpression1952); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_DECR.add(op);


                                    }
                                    break;

                            }


                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: op, assignOp, primaryExpression, expression
                    // token labels: op
                    // rule labels: retval, assignOp
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_op=new RewriteRuleTokenStream(adaptor,"token op",op);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_assignOp=new RewriteRuleSubtreeStream(adaptor,"rule assignOp",assignOp!=null?assignOp.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 274:3: -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:274:6: ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIMARY_ASSIGNMENT_EXPRESSION, "PRIMARY_ASSIGNMENT_EXPRESSION"), root_1);

                        // BaseElement.g:274:38: ^( LHS primaryExpression )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LHS, "LHS"), root_2);

                        adaptor.addChild(root_2, stream_primaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:274:63: ^( RHS ( expression )? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RHS, "RHS"), root_2);

                        // BaseElement.g:274:69: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:274:82: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

                        // BaseElement.g:274:93: ( $op)?
                        if ( stream_op.hasNext() ) {
                            adaptor.addChild(root_2, stream_op.nextNode());

                        }
                        stream_op.reset();
                        // BaseElement.g:274:98: ( $assignOp)?
                        if ( stream_assignOp.hasNext() ) {
                            adaptor.addChild(root_2, stream_assignOp.nextTree());

                        }
                        stream_assignOp.reset();

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "listStatementExpression"

    public static class assignmentOp_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignmentOp"
    // BaseElement.g:277:1: assignmentOp : ( ASSIGN | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL );
    public final Rules_BaseElement.assignmentOp_return assignmentOp() throws RecognitionException {
        Rules_BaseElement.assignmentOp_return retval = new Rules_BaseElement.assignmentOp_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set95=null;

        RulesASTNode set95_tree=null;

        try {
            // BaseElement.g:278:2: ( ASSIGN | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL )
            // BaseElement.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set95=(Token)input.LT(1);
            if ( (input.LA(1)>=ASSIGN && input.LA(1)<=MOD_EQUAL) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set95));
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

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "assignmentOp"

    public static class returnStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "returnStatement"
    // BaseElement.g:281:1: returnStatement : 'return' ( expression SEMICOLON | SEMICOLON ) -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) ) ;
    public final Rules_BaseElement.returnStatement_return returnStatement() throws RecognitionException {
        Rules_BaseElement.returnStatement_return retval = new Rules_BaseElement.returnStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal96=null;
        Token SEMICOLON98=null;
        Token SEMICOLON99=null;
        Rules_BaseElement.expression_return expression97 = null;


        RulesASTNode string_literal96_tree=null;
        RulesASTNode SEMICOLON98_tree=null;
        RulesASTNode SEMICOLON99_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_217=new RewriteRuleTokenStream(adaptor,"token 217");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:282:2: ( 'return' ( expression SEMICOLON | SEMICOLON ) -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) ) )
            // BaseElement.g:282:4: 'return' ( expression SEMICOLON | SEMICOLON )
            {
            string_literal96=(Token)match(input,217,FOLLOW_217_in_returnStatement2032); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_217.add(string_literal96);

            // BaseElement.g:282:13: ( expression SEMICOLON | SEMICOLON )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==Identifier||(LA51_0>=EQ && LA51_0<=LBRACE)||(LA51_0>=PLUS && LA51_0<=MINUS)||LA51_0==POUND||(LA51_0>=FloatingPointLiteral && LA51_0<=FALSE)||LA51_0==LPAREN||LA51_0==LBRACK||(LA51_0>=164 && LA51_0<=212)||(LA51_0>=228 && LA51_0<=231)) ) {
                alt51=1;
            }
            else if ( (LA51_0==SEMICOLON) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // BaseElement.g:282:14: expression SEMICOLON
                    {
                    pushFollow(FOLLOW_expression_in_returnStatement2035);
                    expression97=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression97.getTree());
                    SEMICOLON98=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2037); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON98);


                    }
                    break;
                case 2 :
                    // BaseElement.g:282:37: SEMICOLON
                    {
                    SEMICOLON99=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2041); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON99);


                    }
                    break;

            }



            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 283:3: -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) )
            {
                // BaseElement.g:283:6: ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RETURN_STATEMENT, "RETURN_STATEMENT"), root_1);

                // BaseElement.g:283:25: ^( EXPRESSION ( expression )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                // BaseElement.g:283:38: ( expression )?
                if ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_2, stream_expression.nextTree());

                }
                stream_expression.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "returnStatement"

    public static class breakStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "breakStatement"
    // BaseElement.g:286:1: breakStatement : 'break' SEMICOLON -> ^( BREAK_STATEMENT 'break' ) ;
    public final Rules_BaseElement.breakStatement_return breakStatement() throws RecognitionException {
        Rules_BaseElement.breakStatement_return retval = new Rules_BaseElement.breakStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal100=null;
        Token SEMICOLON101=null;

        RulesASTNode string_literal100_tree=null;
        RulesASTNode SEMICOLON101_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_218=new RewriteRuleTokenStream(adaptor,"token 218");

        try {
            // BaseElement.g:287:2: ( 'break' SEMICOLON -> ^( BREAK_STATEMENT 'break' ) )
            // BaseElement.g:287:4: 'break' SEMICOLON
            {
            string_literal100=(Token)match(input,218,FOLLOW_218_in_breakStatement2069); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_218.add(string_literal100);

            SEMICOLON101=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_breakStatement2071); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON101);



            // AST REWRITE
            // elements: 218
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 288:3: -> ^( BREAK_STATEMENT 'break' )
            {
                // BaseElement.g:288:6: ^( BREAK_STATEMENT 'break' )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BREAK_STATEMENT, "BREAK_STATEMENT"), root_1);

                adaptor.addChild(root_1, stream_218.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "breakStatement"

    public static class continueStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continueStatement"
    // BaseElement.g:291:1: continueStatement : 'continue' SEMICOLON -> ^( CONTINUE_STATEMENT ) ;
    public final Rules_BaseElement.continueStatement_return continueStatement() throws RecognitionException {
        Rules_BaseElement.continueStatement_return retval = new Rules_BaseElement.continueStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal102=null;
        Token SEMICOLON103=null;

        RulesASTNode string_literal102_tree=null;
        RulesASTNode SEMICOLON103_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_219=new RewriteRuleTokenStream(adaptor,"token 219");

        try {
            // BaseElement.g:292:2: ( 'continue' SEMICOLON -> ^( CONTINUE_STATEMENT ) )
            // BaseElement.g:292:4: 'continue' SEMICOLON
            {
            string_literal102=(Token)match(input,219,FOLLOW_219_in_continueStatement2093); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_219.add(string_literal102);

            SEMICOLON103=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_continueStatement2095); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON103);



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 293:3: -> ^( CONTINUE_STATEMENT )
            {
                // BaseElement.g:293:6: ^( CONTINUE_STATEMENT )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(CONTINUE_STATEMENT, "CONTINUE_STATEMENT"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "continueStatement"

    public static class throwStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "throwStatement"
    // BaseElement.g:296:1: throwStatement : 'throw' expression SEMICOLON -> ^( THROW_STATEMENT ^( EXPRESSION expression ) ) ;
    public final Rules_BaseElement.throwStatement_return throwStatement() throws RecognitionException {
        Rules_BaseElement.throwStatement_return retval = new Rules_BaseElement.throwStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal104=null;
        Token SEMICOLON106=null;
        Rules_BaseElement.expression_return expression105 = null;


        RulesASTNode string_literal104_tree=null;
        RulesASTNode SEMICOLON106_tree=null;
        RewriteRuleTokenStream stream_220=new RewriteRuleTokenStream(adaptor,"token 220");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:297:2: ( 'throw' expression SEMICOLON -> ^( THROW_STATEMENT ^( EXPRESSION expression ) ) )
            // BaseElement.g:297:4: 'throw' expression SEMICOLON
            {
            string_literal104=(Token)match(input,220,FOLLOW_220_in_throwStatement2115); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_220.add(string_literal104);

            pushFollow(FOLLOW_expression_in_throwStatement2117);
            expression105=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression105.getTree());
            SEMICOLON106=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_throwStatement2119); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON106);



            // AST REWRITE
            // elements: expression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 298:3: -> ^( THROW_STATEMENT ^( EXPRESSION expression ) )
            {
                // BaseElement.g:298:6: ^( THROW_STATEMENT ^( EXPRESSION expression ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(THROW_STATEMENT, "THROW_STATEMENT"), root_1);

                // BaseElement.g:298:24: ^( EXPRESSION expression )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "throwStatement"

    public static class statementExpressionList_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpressionList"
    // BaseElement.g:308:1: statementExpressionList : listStatementExpression ( ',' listStatementExpression )* ;
    public final Rules_BaseElement.statementExpressionList_return statementExpressionList() throws RecognitionException {
        Rules_BaseElement.statementExpressionList_return retval = new Rules_BaseElement.statementExpressionList_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal108=null;
        Rules_BaseElement.listStatementExpression_return listStatementExpression107 = null;

        Rules_BaseElement.listStatementExpression_return listStatementExpression109 = null;


        RulesASTNode char_literal108_tree=null;

        try {
            // BaseElement.g:309:2: ( listStatementExpression ( ',' listStatementExpression )* )
            // BaseElement.g:309:4: listStatementExpression ( ',' listStatementExpression )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_listStatementExpression_in_statementExpressionList2148);
            listStatementExpression107=listStatementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, listStatementExpression107.getTree());
            // BaseElement.g:309:28: ( ',' listStatementExpression )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==216) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // BaseElement.g:309:29: ',' listStatementExpression
            	    {
            	    char_literal108=(Token)match(input,216,FOLLOW_216_in_statementExpressionList2151); if (state.failed) return retval;
            	    pushFollow(FOLLOW_listStatementExpression_in_statementExpressionList2154);
            	    listStatementExpression109=listStatementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, listStatementExpression109.getTree());

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "statementExpressionList"

    public static class ifRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ifRule"
    // BaseElement.g:312:1: ifRule : 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )? -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) ) ;
    public final Rules_BaseElement.ifRule_return ifRule() throws RecognitionException {
        Rules_BaseElement.ifRule_return retval = new Rules_BaseElement.ifRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal110=null;
        Token char_literal111=null;
        Token char_literal113=null;
        Token string_literal114=null;
        RulesParser.statement_return ifst = null;

        RulesParser.statement_return elst = null;

        Rules_BaseElement.expression_return expression112 = null;


        RulesASTNode string_literal110_tree=null;
        RulesASTNode char_literal111_tree=null;
        RulesASTNode char_literal113_tree=null;
        RulesASTNode string_literal114_tree=null;
        RewriteRuleTokenStream stream_221=new RewriteRuleTokenStream(adaptor,"token 221");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_222=new RewriteRuleTokenStream(adaptor,"token 222");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // BaseElement.g:312:8: ( 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )? -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) ) )
            // BaseElement.g:312:10: 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )?
            {
            string_literal110=(Token)match(input,221,FOLLOW_221_in_ifRule2167); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_221.add(string_literal110);

            char_literal111=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_ifRule2169); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal111);

            pushFollow(FOLLOW_expression_in_ifRule2171);
            expression112=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression112.getTree());
            char_literal113=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_ifRule2173); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal113);

            if ( state.backtracking==0 ) {
               pushScope(IF_SCOPE); 
            }
            pushFollow(FOLLOW_statement_in_ifRule2179);
            ifst=gRules.statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(ifst.getTree());
            if ( state.backtracking==0 ) {
               popScope(); 
            }
            // BaseElement.g:313:2: ( 'else' elst= statement )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==222) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // BaseElement.g:313:3: 'else' elst= statement
                    {
                    string_literal114=(Token)match(input,222,FOLLOW_222_in_ifRule2185); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_222.add(string_literal114);

                    if ( state.backtracking==0 ) {
                       pushScope(IF_SCOPE); 
                    }
                    pushFollow(FOLLOW_statement_in_ifRule2191);
                    elst=gRules.statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statement.add(elst.getTree());
                    if ( state.backtracking==0 ) {
                       popScope(); 
                    }

                    }
                    break;

            }



            // AST REWRITE
            // elements: ifst, elst, expression
            // token labels: 
            // rule labels: retval, elst, ifst
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_elst=new RewriteRuleSubtreeStream(adaptor,"rule elst",elst!=null?elst.tree:null);
            RewriteRuleSubtreeStream stream_ifst=new RewriteRuleSubtreeStream(adaptor,"rule ifst",ifst!=null?ifst.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 314:2: -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) )
            {
                // BaseElement.g:314:5: ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(IF_RULE, "IF_RULE"), root_1);

                // BaseElement.g:314:15: ^( EXPRESSION expression )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:314:40: ^( STATEMENT $ifst)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENT, "STATEMENT"), root_2);

                adaptor.addChild(root_2, stream_ifst.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:314:59: ^( ELSE_STATEMENT ( $elst)? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ELSE_STATEMENT, "ELSE_STATEMENT"), root_2);

                // BaseElement.g:314:76: ( $elst)?
                if ( stream_elst.hasNext() ) {
                    adaptor.addChild(root_2, stream_elst.nextTree());

                }
                stream_elst.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "ifRule"

    public static class whileRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whileRule"
    // BaseElement.g:317:1: whileRule : 'while' '(' expression ')' statement -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) ) ;
    public final Rules_BaseElement.whileRule_return whileRule() throws RecognitionException {
        Rules_BaseElement.whileRule_return retval = new Rules_BaseElement.whileRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal115=null;
        Token char_literal116=null;
        Token char_literal118=null;
        Rules_BaseElement.expression_return expression117 = null;

        RulesParser.statement_return statement119 = null;


        RulesASTNode string_literal115_tree=null;
        RulesASTNode char_literal116_tree=null;
        RulesASTNode char_literal118_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_223=new RewriteRuleTokenStream(adaptor,"token 223");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // BaseElement.g:317:11: ( 'while' '(' expression ')' statement -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) ) )
            // BaseElement.g:317:13: 'while' '(' expression ')' statement
            {
            string_literal115=(Token)match(input,223,FOLLOW_223_in_whileRule2234); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_223.add(string_literal115);

            char_literal116=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_whileRule2236); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal116);

            pushFollow(FOLLOW_expression_in_whileRule2238);
            expression117=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression117.getTree());
            char_literal118=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_whileRule2240); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal118);

            pushFollow(FOLLOW_statement_in_whileRule2242);
            statement119=gRules.statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(statement119.getTree());


            // AST REWRITE
            // elements: expression, statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 318:2: -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) )
            {
                // BaseElement.g:318:5: ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(WHILE_RULE, "WHILE_RULE"), root_1);

                // BaseElement.g:318:18: ^( EXPRESSION expression )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:318:43: ^( STATEMENT statement )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENT, "STATEMENT"), root_2);

                adaptor.addChild(root_2, stream_statement.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "whileRule"

    public static class forRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "forRule"
    // BaseElement.g:321:1: forRule : 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement ) ( expression )? SEMICOLON ( statementExpressionList )? ')' statement -> ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement ) ;
    public final Rules_BaseElement.forRule_return forRule() throws RecognitionException {
        Rules_BaseElement.forRule_return retval = new Rules_BaseElement.forRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal120=null;
        Token char_literal121=null;
        Token SEMICOLON124=null;
        Token SEMICOLON127=null;
        Token char_literal129=null;
        Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration122 = null;

        Rules_BaseElement.statementExpressionList_return statementExpressionList123 = null;

        Rules_BaseElement.emptyStatement_return emptyStatement125 = null;

        Rules_BaseElement.expression_return expression126 = null;

        Rules_BaseElement.statementExpressionList_return statementExpressionList128 = null;

        RulesParser.statement_return statement130 = null;


        RulesASTNode string_literal120_tree=null;
        RulesASTNode char_literal121_tree=null;
        RulesASTNode SEMICOLON124_tree=null;
        RulesASTNode SEMICOLON127_tree=null;
        RulesASTNode char_literal129_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_224=new RewriteRuleTokenStream(adaptor,"token 224");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_localVariableDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule localVariableDeclaration");
        RewriteRuleSubtreeStream stream_statementExpressionList=new RewriteRuleSubtreeStream(adaptor,"rule statementExpressionList");
        RewriteRuleSubtreeStream stream_emptyStatement=new RewriteRuleSubtreeStream(adaptor,"rule emptyStatement");
        try {
            // BaseElement.g:321:9: ( 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement ) ( expression )? SEMICOLON ( statementExpressionList )? ')' statement -> ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement ) )
            // BaseElement.g:321:11: 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement ) ( expression )? SEMICOLON ( statementExpressionList )? ')' statement
            {
            string_literal120=(Token)match(input,224,FOLLOW_224_in_forRule2272); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_224.add(string_literal120);

            char_literal121=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_forRule2274); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal121);

            if ( state.backtracking==0 ) {
               pushScope(FOR_SCOPE); 
            }
            // BaseElement.g:322:2: ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement )
            int alt54=3;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // BaseElement.g:322:4: ( type identifier )=> localVariableDeclaration
                    {
                    pushFollow(FOLLOW_localVariableDeclaration_in_forRule2288);
                    localVariableDeclaration122=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_localVariableDeclaration.add(localVariableDeclaration122.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:322:51: statementExpressionList SEMICOLON
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2292);
                    statementExpressionList123=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementExpressionList.add(statementExpressionList123.getTree());
                    SEMICOLON124=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2294); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON124);


                    }
                    break;
                case 3 :
                    // BaseElement.g:322:87: emptyStatement
                    {
                    pushFollow(FOLLOW_emptyStatement_in_forRule2298);
                    emptyStatement125=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_emptyStatement.add(emptyStatement125.getTree());

                    }
                    break;

            }

            // BaseElement.g:323:2: ( expression )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==Identifier||(LA55_0>=EQ && LA55_0<=LBRACE)||(LA55_0>=PLUS && LA55_0<=MINUS)||LA55_0==POUND||(LA55_0>=FloatingPointLiteral && LA55_0<=FALSE)||LA55_0==LPAREN||LA55_0==LBRACK||(LA55_0>=164 && LA55_0<=212)||(LA55_0>=228 && LA55_0<=231)) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // BaseElement.g:323:3: expression
                    {
                    pushFollow(FOLLOW_expression_in_forRule2303);
                    expression126=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression126.getTree());

                    }
                    break;

            }

            SEMICOLON127=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2307); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON127);

            // BaseElement.g:324:2: ( statementExpressionList )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==Identifier||(LA56_0>=FloatingPointLiteral && LA56_0<=FALSE)||LA56_0==LPAREN||(LA56_0>=164 && LA56_0<=212)||(LA56_0>=228 && LA56_0<=231)) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // BaseElement.g:324:3: statementExpressionList
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2311);
                    statementExpressionList128=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementExpressionList.add(statementExpressionList128.getTree());

                    }
                    break;

            }

            char_literal129=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_forRule2315); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal129);

            pushFollow(FOLLOW_statement_in_forRule2320);
            statement130=gRules.statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(statement130.getTree());
            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: expression, emptyStatement, statementExpressionList, localVariableDeclaration, statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 327:3: -> ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement )
            {
                // BaseElement.g:327:6: ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(FOR_LOOP, "FOR_LOOP"), root_1);

                // BaseElement.g:327:17: ( localVariableDeclaration )?
                if ( stream_localVariableDeclaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_localVariableDeclaration.nextTree());

                }
                stream_localVariableDeclaration.reset();
                // BaseElement.g:327:43: ( statementExpressionList )*
                while ( stream_statementExpressionList.hasNext() ) {
                    adaptor.addChild(root_1, stream_statementExpressionList.nextTree());

                }
                stream_statementExpressionList.reset();
                // BaseElement.g:328:3: ( emptyStatement )?
                if ( stream_emptyStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_emptyStatement.nextTree());

                }
                stream_emptyStatement.reset();
                // BaseElement.g:328:19: ( expression )?
                if ( stream_expression.hasNext() ) {
                    adaptor.addChild(root_1, stream_expression.nextTree());

                }
                stream_expression.reset();
                adaptor.addChild(root_1, stream_statement.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "forRule"

    public static class tryCatchFinally_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tryCatchFinally"
    // BaseElement.g:331:1: tryCatchFinally : 'try' block ( catchRule ( finallyRule )? | finallyRule ) -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) ) ;
    public final Rules_BaseElement.tryCatchFinally_return tryCatchFinally() throws RecognitionException {
        Rules_BaseElement.tryCatchFinally_return retval = new Rules_BaseElement.tryCatchFinally_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal131=null;
        RulesParser.block_return block132 = null;

        Rules_BaseElement.catchRule_return catchRule133 = null;

        Rules_BaseElement.finallyRule_return finallyRule134 = null;

        Rules_BaseElement.finallyRule_return finallyRule135 = null;


        RulesASTNode string_literal131_tree=null;
        RewriteRuleTokenStream stream_225=new RewriteRuleTokenStream(adaptor,"token 225");
        RewriteRuleSubtreeStream stream_catchRule=new RewriteRuleSubtreeStream(adaptor,"rule catchRule");
        RewriteRuleSubtreeStream stream_finallyRule=new RewriteRuleSubtreeStream(adaptor,"rule finallyRule");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // BaseElement.g:332:2: ( 'try' block ( catchRule ( finallyRule )? | finallyRule ) -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) ) )
            // BaseElement.g:332:4: 'try' block ( catchRule ( finallyRule )? | finallyRule )
            {
            string_literal131=(Token)match(input,225,FOLLOW_225_in_tryCatchFinally2360); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_225.add(string_literal131);

            pushFollow(FOLLOW_block_in_tryCatchFinally2362);
            block132=gRules.block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block132.getTree());
            // BaseElement.g:333:2: ( catchRule ( finallyRule )? | finallyRule )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==226) ) {
                alt58=1;
            }
            else if ( (LA58_0==227) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // BaseElement.g:333:4: catchRule ( finallyRule )?
                    {
                    pushFollow(FOLLOW_catchRule_in_tryCatchFinally2368);
                    catchRule133=catchRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_catchRule.add(catchRule133.getTree());
                    // BaseElement.g:333:14: ( finallyRule )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==227) ) {
                        alt57=1;
                    }
                    switch (alt57) {
                        case 1 :
                            // BaseElement.g:333:14: finallyRule
                            {
                            pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2370);
                            finallyRule134=finallyRule();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_finallyRule.add(finallyRule134.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // BaseElement.g:334:4: finallyRule
                    {
                    pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2377);
                    finallyRule135=finallyRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_finallyRule.add(finallyRule135.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: block, finallyRule, catchRule
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 335:3: -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) )
            {
                // BaseElement.g:335:6: ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TRY_STATEMENT, "TRY_STATEMENT"), root_1);

                // BaseElement.g:335:22: ^( BODY block )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BODY, "BODY"), root_2);

                adaptor.addChild(root_2, stream_block.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:335:36: ^( CATCH_CLAUSE ( catchRule )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(CATCH_CLAUSE, "CATCH_CLAUSE"), root_2);

                // BaseElement.g:335:51: ( catchRule )?
                if ( stream_catchRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_catchRule.nextTree());

                }
                stream_catchRule.reset();

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:335:63: ^( FINALLY_CLAUSE ( finallyRule )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(FINALLY_CLAUSE, "FINALLY_CLAUSE"), root_2);

                // BaseElement.g:335:80: ( finallyRule )?
                if ( stream_finallyRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_finallyRule.nextTree());

                }
                stream_finallyRule.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "tryCatchFinally"

    public static class catchRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "catchRule"
    // BaseElement.g:338:1: catchRule : 'catch' '(' t= type id= identifier ')' nonBraceBlock -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY nonBraceBlock ) ) ;
    public final Rules_BaseElement.catchRule_return catchRule() throws RecognitionException {
        Rules_BaseElement.catchRule_return retval = new Rules_BaseElement.catchRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal136=null;
        Token char_literal137=null;
        Token char_literal138=null;
        Rules_BaseElement.type_return t = null;

        RulesParser.identifier_return id = null;

        Rules_BaseElement.nonBraceBlock_return nonBraceBlock139 = null;


        RulesASTNode string_literal136_tree=null;
        RulesASTNode char_literal137_tree=null;
        RulesASTNode char_literal138_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_226=new RewriteRuleTokenStream(adaptor,"token 226");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_nonBraceBlock=new RewriteRuleSubtreeStream(adaptor,"rule nonBraceBlock");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:338:10: ( 'catch' '(' t= type id= identifier ')' nonBraceBlock -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY nonBraceBlock ) ) )
            // BaseElement.g:338:12: 'catch' '(' t= type id= identifier ')' nonBraceBlock
            {
            string_literal136=(Token)match(input,226,FOLLOW_226_in_catchRule2417); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_226.add(string_literal136);

            char_literal137=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_catchRule2419); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal137);

            if ( state.backtracking==0 ) {
               pushScope(BLOCK_SCOPE); 
            }
            pushFollow(FOLLOW_type_in_catchRule2428);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());
            pushFollow(FOLLOW_identifier_in_catchRule2432);
            id=gRules.identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) t.getTree()); 
            }
            char_literal138=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_catchRule2438); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal138);

            pushFollow(FOLLOW_nonBraceBlock_in_catchRule2447);
            nonBraceBlock139=nonBraceBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_nonBraceBlock.add(nonBraceBlock139.getTree());
            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: t, nonBraceBlock, id
            // token labels: 
            // rule labels: id, retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 344:3: -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY nonBraceBlock ) )
            {
                // BaseElement.g:344:6: ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY nonBraceBlock ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(CATCH_RULE, "CATCH_RULE"), root_1);

                // BaseElement.g:344:19: ^( TYPE $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:344:30: ^( IDENTIFIER $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(IDENTIFIER, "IDENTIFIER"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:344:48: ^( BODY nonBraceBlock )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BODY, "BODY"), root_2);

                adaptor.addChild(root_2, stream_nonBraceBlock.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "catchRule"

    public static class nonBraceBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nonBraceBlock"
    // BaseElement.g:347:1: nonBraceBlock : LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) ;
    public final Rules_BaseElement.nonBraceBlock_return nonBraceBlock() throws RecognitionException {
        Rules_BaseElement.nonBraceBlock_return retval = new Rules_BaseElement.nonBraceBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE140=null;
        Token RBRACE144=null;
        RulesParser.actionContextStatement_return actionContextStatement141 = null;

        Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration142 = null;

        RulesParser.statement_return statement143 = null;


        RulesASTNode LBRACE140_tree=null;
        RulesASTNode RBRACE144_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_actionContextStatement=new RewriteRuleSubtreeStream(adaptor,"rule actionContextStatement");
        RewriteRuleSubtreeStream stream_localVariableDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule localVariableDeclaration");
        try {
            // BaseElement.g:347:15: ( LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) )
            // BaseElement.g:347:17: LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE
            {
            LBRACE140=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_nonBraceBlock2491); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE140);

            // BaseElement.g:348:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*
            loop59:
            do {
                int alt59=4;
                alt59 = dfa59.predict(input);
                switch (alt59) {
            	case 1 :
            	    // BaseElement.g:348:5: ({...}? actionContextStatement )=> actionContextStatement
            	    {
            	    pushFollow(FOLLOW_actionContextStatement_in_nonBraceBlock2505);
            	    actionContextStatement141=gRules.actionContextStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_actionContextStatement.add(actionContextStatement141.getTree());

            	    }
            	    break;
            	case 2 :
            	    // BaseElement.g:348:87: ( type identifier )=> localVariableDeclaration
            	    {
            	    pushFollow(FOLLOW_localVariableDeclaration_in_nonBraceBlock2516);
            	    localVariableDeclaration142=localVariableDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_localVariableDeclaration.add(localVariableDeclaration142.getTree());

            	    }
            	    break;
            	case 3 :
            	    // BaseElement.g:348:134: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_nonBraceBlock2520);
            	    statement143=gRules.statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement143.getTree());

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

            RBRACE144=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_nonBraceBlock2527); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE144);



            // AST REWRITE
            // elements: localVariableDeclaration, actionContextStatement, statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 350:3: -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
            {
                // BaseElement.g:350:6: ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // BaseElement.g:350:14: ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // BaseElement.g:350:27: ( actionContextStatement )*
                while ( stream_actionContextStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionContextStatement.nextTree());

                }
                stream_actionContextStatement.reset();
                // BaseElement.g:350:51: ( localVariableDeclaration )*
                while ( stream_localVariableDeclaration.hasNext() ) {
                    adaptor.addChild(root_2, stream_localVariableDeclaration.nextTree());

                }
                stream_localVariableDeclaration.reset();
                // BaseElement.g:350:77: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_2, stream_statement.nextTree());

                }
                stream_statement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nonBraceBlock"

    public static class finallyRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "finallyRule"
    // BaseElement.g:353:1: finallyRule : 'finally' block -> ^( FINALLY_RULE ^( BODY block ) ) ;
    public final Rules_BaseElement.finallyRule_return finallyRule() throws RecognitionException {
        Rules_BaseElement.finallyRule_return retval = new Rules_BaseElement.finallyRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal145=null;
        RulesParser.block_return block146 = null;


        RulesASTNode string_literal145_tree=null;
        RewriteRuleTokenStream stream_227=new RewriteRuleTokenStream(adaptor,"token 227");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // BaseElement.g:353:13: ( 'finally' block -> ^( FINALLY_RULE ^( BODY block ) ) )
            // BaseElement.g:353:15: 'finally' block
            {
            string_literal145=(Token)match(input,227,FOLLOW_227_in_finallyRule2559); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_227.add(string_literal145);

            pushFollow(FOLLOW_block_in_finallyRule2561);
            block146=gRules.block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block146.getTree());


            // AST REWRITE
            // elements: block
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 354:3: -> ^( FINALLY_RULE ^( BODY block ) )
            {
                // BaseElement.g:354:6: ^( FINALLY_RULE ^( BODY block ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(FINALLY_RULE, "FINALLY_RULE"), root_1);

                // BaseElement.g:354:21: ^( BODY block )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BODY, "BODY"), root_2);

                adaptor.addChild(root_2, stream_block.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "finallyRule"

    public static class semiColon_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "semiColon"
    // BaseElement.g:357:1: semiColon : SEMICOLON ;
    public final Rules_BaseElement.semiColon_return semiColon() throws RecognitionException {
        Rules_BaseElement.semiColon_return retval = new Rules_BaseElement.semiColon_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token SEMICOLON147=null;

        RulesASTNode SEMICOLON147_tree=null;

        try {
            // BaseElement.g:358:2: ( SEMICOLON )
            // BaseElement.g:358:4: SEMICOLON
            {
            root_0 = (RulesASTNode)adaptor.nil();

            SEMICOLON147=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_semiColon2586); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON147_tree = (RulesASTNode)adaptor.create(SEMICOLON147);
            adaptor.addChild(root_0, SEMICOLON147_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "semiColon"

    public static class name_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // BaseElement.g:368:1: name[int nameType] : ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )* ;
    public final Rules_BaseElement.name_return name(int nameType) throws RecognitionException {
        Rules_BaseElement.name_return retval = new Rules_BaseElement.name_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token DOT149=null;
        RulesParser.identifier_return ident = null;

        RulesParser.identifier_return identifier148 = null;


        RulesASTNode DOT149_tree=null;
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:369:2: ( ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )* )
            // BaseElement.g:369:4: ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )*
            {
            // BaseElement.g:369:4: ( identifier -> identifier )
            // BaseElement.g:369:5: identifier
            {
            pushFollow(FOLLOW_identifier_in_name2602);
            identifier148=gRules.identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(identifier148.getTree());


            // AST REWRITE
            // elements: identifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 369:16: -> identifier
            {
                adaptor.addChild(root_0, stream_identifier.nextTree());

            }

            retval.tree = root_0;}
            }

            // BaseElement.g:370:3: ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==DOT) ) {
                    int LA60_2 = input.LA(2);

                    if ( (LA60_2==Identifier) ) {
                        alt60=1;
                    }
                    else if ( (LA60_2==NullLiteral||(LA60_2>=164 && LA60_2<=212)) ) {
                        alt60=1;
                    }


                }


                switch (alt60) {
            	case 1 :
            	    // BaseElement.g:370:4: DOT ident= identifier
            	    {
            	    DOT149=(Token)match(input,DOT,FOLLOW_DOT_in_name2612); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_DOT.add(DOT149);

            	    pushFollow(FOLLOW_identifier_in_name2616);
            	    ident=gRules.identifier();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_identifier.add(ident.getTree());


            	    // AST REWRITE
            	    // elements: ident, name
            	    // token labels: 
            	    // rule labels: retval, ident
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_ident=new RewriteRuleSubtreeStream(adaptor,"rule ident",ident!=null?ident.tree:null);

            	    root_0 = (RulesASTNode)adaptor.nil();
            	    // 371:4: -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident)
            	    {
            	        // BaseElement.g:371:7: ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident)
            	        {
            	        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
            	        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(QUALIFIED_NAME, "QUALIFIED_NAME"), root_1);

            	        // BaseElement.g:371:24: ^( QUALIFIER $name)
            	        {
            	        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
            	        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(QUALIFIER, "QUALIFIER"), root_2);

            	        adaptor.addChild(root_2, stream_retval.nextTree());

            	        adaptor.addChild(root_1, root_2);
            	        }
            	        adaptor.addChild(root_1, stream_ident.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               pushName(nameType, retval.tree); 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "name"

    public static class nameOrPrimitiveType_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nameOrPrimitiveType"
    // BaseElement.g:375:1: nameOrPrimitiveType : ( name[BaseRulesParser.TYPE_REF] | primitiveType );
    public final Rules_BaseElement.nameOrPrimitiveType_return nameOrPrimitiveType() throws RecognitionException {
        Rules_BaseElement.nameOrPrimitiveType_return retval = new Rules_BaseElement.nameOrPrimitiveType_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.name_return name150 = null;

        Rules_BaseElement.primitiveType_return primitiveType151 = null;



        try {
            // BaseElement.g:376:2: ( name[BaseRulesParser.TYPE_REF] | primitiveType )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==Identifier||LA61_0==NullLiteral||(LA61_0>=164 && LA61_0<=212)) ) {
                alt61=1;
            }
            else if ( ((LA61_0>=228 && LA61_0<=231)) ) {
                alt61=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // BaseElement.g:376:4: name[BaseRulesParser.TYPE_REF]
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_name_in_nameOrPrimitiveType2656);
                    name150=name(BaseRulesParser.TYPE_REF);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name150.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:376:37: primitiveType
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_primitiveType_in_nameOrPrimitiveType2661);
                    primitiveType151=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primitiveType151.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nameOrPrimitiveType"

    public static class expressionName_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressionName"
    // BaseElement.g:380:1: expressionName : name[TYPE_REF] ;
    public final Rules_BaseElement.expressionName_return expressionName() throws RecognitionException {
        Rules_BaseElement.expressionName_return retval = new Rules_BaseElement.expressionName_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.name_return name152 = null;



        try {
            // BaseElement.g:381:2: ( name[TYPE_REF] )
            // BaseElement.g:381:4: name[TYPE_REF]
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_expressionName2675);
            name152=name(TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name152.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expressionName"

    public static class methodName_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "methodName"
    // BaseElement.g:384:1: methodName : name[TYPE_METHOD_REF] ;
    public final Rules_BaseElement.methodName_return methodName() throws RecognitionException {
        Rules_BaseElement.methodName_return retval = new Rules_BaseElement.methodName_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.name_return name153 = null;



        try {
            // BaseElement.g:385:2: ( name[TYPE_METHOD_REF] )
            // BaseElement.g:385:4: name[TYPE_METHOD_REF]
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_methodName2687);
            name153=name(TYPE_METHOD_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name153.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "methodName"

    public static class typeName_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeName"
    // BaseElement.g:388:1: typeName : name[TYPE_REF] ;
    public final Rules_BaseElement.typeName_return typeName() throws RecognitionException {
        Rules_BaseElement.typeName_return retval = new Rules_BaseElement.typeName_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.name_return name154 = null;



        try {
            // BaseElement.g:389:2: ( name[TYPE_REF] )
            // BaseElement.g:389:4: name[TYPE_REF]
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_typeName2699);
            name154=name(TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name154.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeName"

    public static class type_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // BaseElement.g:392:1: type : typeAdditionalArrayDim ;
    public final Rules_BaseElement.type_return type() throws RecognitionException {
        Rules_BaseElement.type_return retval = new Rules_BaseElement.type_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim155 = null;



        try {
            // BaseElement.g:392:6: ( typeAdditionalArrayDim )
            // BaseElement.g:392:8: typeAdditionalArrayDim
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_typeAdditionalArrayDim_in_type2710);
            typeAdditionalArrayDim155=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeAdditionalArrayDim155.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class typeAdditionalArrayDim_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAdditionalArrayDim"
    // BaseElement.g:395:1: typeAdditionalArrayDim : (t= typeName | p= primitiveType ) ( '[' ']' )? ;
    public final Rules_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException {
        Rules_BaseElement.typeAdditionalArrayDim_return retval = new Rules_BaseElement.typeAdditionalArrayDim_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal156=null;
        Token char_literal157=null;
        Rules_BaseElement.typeName_return t = null;

        Rules_BaseElement.primitiveType_return p = null;


        RulesASTNode char_literal156_tree=null;
        RulesASTNode char_literal157_tree=null;

        try {
            // BaseElement.g:396:2: ( (t= typeName | p= primitiveType ) ( '[' ']' )? )
            // BaseElement.g:396:4: (t= typeName | p= primitiveType ) ( '[' ']' )?
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // BaseElement.g:396:4: (t= typeName | p= primitiveType )
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==Identifier||LA62_0==NullLiteral||(LA62_0>=164 && LA62_0<=212)) ) {
                alt62=1;
            }
            else if ( ((LA62_0>=228 && LA62_0<=231)) ) {
                alt62=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }
            switch (alt62) {
                case 1 :
                    // BaseElement.g:396:5: t= typeName
                    {
                    if ( state.backtracking==0 ) {
                       setTypeReference(true); 
                    }
                    pushFollow(FOLLOW_typeName_in_typeAdditionalArrayDim2727);
                    t=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if ( state.backtracking==0 ) {
                       setTypeReference(false); 
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:396:74: p= primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_typeAdditionalArrayDim2734);
                    p=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());

                    }
                    break;

            }

            // BaseElement.g:396:91: ( '[' ']' )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==LBRACK) ) {
                int LA63_1 = input.LA(2);

                if ( (LA63_1==RBRACK) ) {
                    alt63=1;
                }
            }
            switch (alt63) {
                case 1 :
                    // BaseElement.g:396:93: '[' ']'
                    {
                    char_literal156=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_typeAdditionalArrayDim2739); if (state.failed) return retval;
                    char_literal157=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_typeAdditionalArrayDim2742); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       if (t != null) markAsArray((RulesASTNode) t.getTree()); else markAsArray((RulesASTNode) p.getTree()); 
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "typeAdditionalArrayDim"

    public static class primitiveType_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitiveType"
    // BaseElement.g:400:1: primitiveType : primitive -> ^( PRIMITIVE_TYPE[\"type\"] primitive ) ;
    public final Rules_BaseElement.primitiveType_return primitiveType() throws RecognitionException {
        Rules_BaseElement.primitiveType_return retval = new Rules_BaseElement.primitiveType_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.primitive_return primitive158 = null;


        RewriteRuleSubtreeStream stream_primitive=new RewriteRuleSubtreeStream(adaptor,"rule primitive");
        try {
            // BaseElement.g:401:2: ( primitive -> ^( PRIMITIVE_TYPE[\"type\"] primitive ) )
            // BaseElement.g:401:4: primitive
            {
            pushFollow(FOLLOW_primitive_in_primitiveType2764);
            primitive158=primitive();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_primitive.add(primitive158.getTree());


            // AST REWRITE
            // elements: primitive
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 402:2: -> ^( PRIMITIVE_TYPE[\"type\"] primitive )
            {
                // BaseElement.g:402:5: ^( PRIMITIVE_TYPE[\"type\"] primitive )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIMITIVE_TYPE, "type"), root_1);

                adaptor.addChild(root_1, stream_primitive.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primitiveType"

    public static class primitive_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitive"
    // BaseElement.g:405:1: primitive : ( 'boolean' | 'int' | 'long' | 'double' );
    public final Rules_BaseElement.primitive_return primitive() throws RecognitionException {
        Rules_BaseElement.primitive_return retval = new Rules_BaseElement.primitive_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set159=null;

        RulesASTNode set159_tree=null;

        try {
            // BaseElement.g:406:2: ( 'boolean' | 'int' | 'long' | 'double' )
            // BaseElement.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set159=(Token)input.LT(1);
            if ( (input.LA(1)>=228 && input.LA(1)<=231) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set159));
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

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primitive"

    public static class localVariableDeclaration_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localVariableDeclaration"
    // BaseElement.g:410:1: localVariableDeclaration : t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) ) ;
    public final Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException {
        Rules_BaseElement.localVariableDeclaration_return retval = new Rules_BaseElement.localVariableDeclaration_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal161=null;
        Token SEMICOLON163=null;
        Rules_BaseElement.type_return t = null;

        Rules_BaseElement.variableDeclarator_return variableDeclarator160 = null;

        Rules_BaseElement.variableDeclarator_return variableDeclarator162 = null;


        RulesASTNode char_literal161_tree=null;
        RulesASTNode SEMICOLON163_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_216=new RewriteRuleTokenStream(adaptor,"token 216");
        RewriteRuleSubtreeStream stream_variableDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule variableDeclarator");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // BaseElement.g:411:2: (t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) ) )
            // BaseElement.g:411:4: t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON
            {
            pushFollow(FOLLOW_type_in_localVariableDeclaration2812);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());
            pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2814);
            variableDeclarator160=variableDeclarator((RulesASTNode) t.getTree());

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_variableDeclarator.add(variableDeclarator160.getTree());
            // BaseElement.g:412:3: ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==216) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // BaseElement.g:412:4: ',' variableDeclarator[(RulesASTNode) t.getTree()]
            	    {
            	    char_literal161=(Token)match(input,216,FOLLOW_216_in_localVariableDeclaration2821); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_216.add(char_literal161);

            	    pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2823);
            	    variableDeclarator162=variableDeclarator((RulesASTNode) t.getTree());

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_variableDeclarator.add(variableDeclarator162.getTree());

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);

            SEMICOLON163=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_localVariableDeclaration2828); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON163);



            // AST REWRITE
            // elements: variableDeclarator, t
            // token labels: 
            // rule labels: retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 413:3: -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) )
            {
                // BaseElement.g:413:6: ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LOCAL_VARIABLE_DECL, "LOCAL_VARIABLE_DECL"), root_1);

                // BaseElement.g:413:28: ^( TYPE $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:413:39: ^( DECLARATORS ( variableDeclarator )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DECLARATORS, "DECLARATORS"), root_2);

                // BaseElement.g:413:53: ( variableDeclarator )*
                while ( stream_variableDeclarator.hasNext() ) {
                    adaptor.addChild(root_2, stream_variableDeclarator.nextTree());

                }
                stream_variableDeclarator.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "localVariableDeclaration"

    public static class variableDeclarator_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variableDeclarator"
    // BaseElement.g:416:1: variableDeclarator[RulesASTNode type] : id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) ;
    public final Rules_BaseElement.variableDeclarator_return variableDeclarator(RulesASTNode type) throws RecognitionException {
        Rules_BaseElement.variableDeclarator_return retval = new Rules_BaseElement.variableDeclarator_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token ASSIGN164=null;
        RulesParser.identifier_return id = null;

        Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral165 = null;

        Rules_BaseElement.expression_return expression166 = null;


        RulesASTNode ASSIGN164_tree=null;
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:417:2: (id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) )
            // BaseElement.g:417:4: id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            {
            pushFollow(FOLLOW_identifier_in_variableDeclarator2865);
            id=gRules.identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushDefineName((RulesASTNode) id.getTree(), type); 
            }
            // BaseElement.g:417:73: ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==ASSIGN) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // BaseElement.g:417:74: ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    {
                    ASSIGN164=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_variableDeclarator2870); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN164);

                    // BaseElement.g:418:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    int alt65=2;
                    alt65 = dfa65.predict(input);
                    switch (alt65) {
                        case 1 :
                            // BaseElement.g:418:3: ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral
                            {
                            pushFollow(FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2887);
                            localInitializerArrayLiteral165=localInitializerArrayLiteral();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral165.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:419:4: expression
                            {
                            pushFollow(FOLLOW_expression_in_variableDeclarator2892);
                            expression166=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression166.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }



            // AST REWRITE
            // elements: expression, id, localInitializerArrayLiteral
            // token labels: 
            // rule labels: id, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 420:3: -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
            {
                // BaseElement.g:420:6: ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(VARIABLE_DECLARATOR, "VARIABLE_DECLARATOR"), root_1);

                // BaseElement.g:420:28: ^( NAME $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:421:3: ( ^( INITIALIZER localInitializerArrayLiteral ) )?
                if ( stream_localInitializerArrayLiteral.hasNext() ) {
                    // BaseElement.g:421:3: ^( INITIALIZER localInitializerArrayLiteral )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

                    adaptor.addChild(root_2, stream_localInitializerArrayLiteral.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_localInitializerArrayLiteral.reset();
                // BaseElement.g:422:3: ( ^( EXPRESSION expression ) )?
                if ( stream_expression.hasNext() ) {
                    // BaseElement.g:422:3: ^( EXPRESSION expression )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                    adaptor.addChild(root_2, stream_expression.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_expression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variableDeclarator"

    public static class localInitializerArrayLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localInitializerArrayLiteral"
    // BaseElement.g:425:1: localInitializerArrayLiteral : LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) ) ;
    public final Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException {
        Rules_BaseElement.localInitializerArrayLiteral_return retval = new Rules_BaseElement.localInitializerArrayLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE167=null;
        Token char_literal168=null;
        Token RBRACE169=null;
        List list_exp=null;
        RuleReturnScope exp = null;
        RulesASTNode LBRACE167_tree=null;
        RulesASTNode char_literal168_tree=null;
        RulesASTNode RBRACE169_tree=null;
        RewriteRuleTokenStream stream_216=new RewriteRuleTokenStream(adaptor,"token 216");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:426:2: ( LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) ) )
            // BaseElement.g:426:4: LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE
            {
            LBRACE167=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_localInitializerArrayLiteral2940); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE167);

            // BaseElement.g:426:11: (exp+= expression ( ',' exp+= expression )* )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==Identifier||(LA68_0>=EQ && LA68_0<=LBRACE)||(LA68_0>=PLUS && LA68_0<=MINUS)||LA68_0==POUND||(LA68_0>=FloatingPointLiteral && LA68_0<=FALSE)||LA68_0==LPAREN||LA68_0==LBRACK||(LA68_0>=164 && LA68_0<=212)||(LA68_0>=228 && LA68_0<=231)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // BaseElement.g:426:12: exp+= expression ( ',' exp+= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2945);
                    exp=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    if (list_exp==null) list_exp=new ArrayList();
                    list_exp.add(exp.getTree());

                    // BaseElement.g:426:28: ( ',' exp+= expression )*
                    loop67:
                    do {
                        int alt67=2;
                        int LA67_0 = input.LA(1);

                        if ( (LA67_0==216) ) {
                            alt67=1;
                        }


                        switch (alt67) {
                    	case 1 :
                    	    // BaseElement.g:426:29: ',' exp+= expression
                    	    {
                    	    char_literal168=(Token)match(input,216,FOLLOW_216_in_localInitializerArrayLiteral2948); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_216.add(char_literal168);

                    	    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2952);
                    	    exp=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    	    if (list_exp==null) list_exp=new ArrayList();
                    	    list_exp.add(exp.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop67;
                        }
                    } while (true);


                    }
                    break;

            }

            RBRACE169=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_localInitializerArrayLiteral2958); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE169);



            // AST REWRITE
            // elements: exp
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: exp
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_exp=new RewriteRuleSubtreeStream(adaptor,"token exp",list_exp);
            root_0 = (RulesASTNode)adaptor.nil();
            // 427:3: -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) )
            {
                // BaseElement.g:427:6: ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LOCAL_INITIALIZER, "LOCAL_INITIALIZER"), root_1);

                // BaseElement.g:427:26: ^( EXPRESSIONS ( $exp)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

                // BaseElement.g:427:40: ( $exp)*
                while ( stream_exp.hasNext() ) {
                    adaptor.addChild(root_2, stream_exp.nextTree());

                }
                stream_exp.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "localInitializerArrayLiteral"

    public static class arrayLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayLiteral"
    // BaseElement.g:430:1: arrayLiteral : type localInitializerArrayLiteral -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) ) ;
    public final Rules_BaseElement.arrayLiteral_return arrayLiteral() throws RecognitionException {
        Rules_BaseElement.arrayLiteral_return retval = new Rules_BaseElement.arrayLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.type_return type170 = null;

        Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral171 = null;


        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // BaseElement.g:431:2: ( type localInitializerArrayLiteral -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) ) )
            // BaseElement.g:431:4: type localInitializerArrayLiteral
            {
            pushFollow(FOLLOW_type_in_arrayLiteral2985);
            type170=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type170.getTree());
            pushFollow(FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2987);
            localInitializerArrayLiteral171=localInitializerArrayLiteral();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral171.getTree());


            // AST REWRITE
            // elements: type, localInitializerArrayLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 432:3: -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) )
            {
                // BaseElement.g:432:6: ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARRAY_LITERAL, "ARRAY_LITERAL"), root_1);

                // BaseElement.g:432:22: ^( TYPE type )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_type.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:432:35: ^( INITIALIZER localInitializerArrayLiteral )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

                adaptor.addChild(root_2, stream_localInitializerArrayLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayLiteral"

    public static class arrayAllocator_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAllocator"
    // BaseElement.g:435:1: arrayAllocator : t= typeAdditionalArrayDim '[' expression ']' LBRACE RBRACE -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) ) ;
    public final Rules_BaseElement.arrayAllocator_return arrayAllocator() throws RecognitionException {
        Rules_BaseElement.arrayAllocator_return retval = new Rules_BaseElement.arrayAllocator_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal172=null;
        Token char_literal174=null;
        Token LBRACE175=null;
        Token RBRACE176=null;
        Rules_BaseElement.typeAdditionalArrayDim_return t = null;

        Rules_BaseElement.expression_return expression173 = null;


        RulesASTNode char_literal172_tree=null;
        RulesASTNode char_literal174_tree=null;
        RulesASTNode LBRACE175_tree=null;
        RulesASTNode RBRACE176_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeAdditionalArrayDim=new RewriteRuleSubtreeStream(adaptor,"rule typeAdditionalArrayDim");
        try {
            // BaseElement.g:436:2: (t= typeAdditionalArrayDim '[' expression ']' LBRACE RBRACE -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) ) )
            // BaseElement.g:436:4: t= typeAdditionalArrayDim '[' expression ']' LBRACE RBRACE
            {
            pushFollow(FOLLOW_typeAdditionalArrayDim_in_arrayAllocator3020);
            t=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typeAdditionalArrayDim.add(t.getTree());
            char_literal172=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_arrayAllocator3025); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACK.add(char_literal172);

            if ( state.backtracking==0 ) {
               arrayDepth++;
            }
            pushFollow(FOLLOW_expression_in_arrayAllocator3029);
            expression173=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression173.getTree());
            char_literal174=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_arrayAllocator3031); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACK.add(char_literal174);

            if ( state.backtracking==0 ) {
               arrayDepth--; markAsArray((RulesASTNode) t.getTree()); 
            }
            LBRACE175=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_arrayAllocator3035); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE175);

            RBRACE176=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_arrayAllocator3037); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE176);



            // AST REWRITE
            // elements: t, expression
            // token labels: 
            // rule labels: retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 438:3: -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) )
            {
                // BaseElement.g:438:6: ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ARRAY_ALLOCATOR, "ARRAY_ALLOCATOR"), root_1);

                // BaseElement.g:438:24: ^( TYPE $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:438:35: ^( EXPRESSION expression )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
        	// e.printStackTrace();
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arrayAllocator"

    // $ANTLR start synpred1_BaseElement
    public final void synpred1_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:114:4: ( domainSpec )
        // BaseElement.g:114:5: domainSpec
        {
        pushFollow(FOLLOW_domainSpec_in_synpred1_BaseElement826);
        domainSpec();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_BaseElement

    // $ANTLR start synpred2_BaseElement
    public final void synpred2_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:119:5: ( domainSpec )
        // BaseElement.g:119:6: domainSpec
        {
        pushFollow(FOLLOW_domainSpec_in_synpred2_BaseElement864);
        domainSpec();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_BaseElement

    // $ANTLR start synpred4_BaseElement
    public final void synpred4_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:193:4: ( type LBRACE )
        // BaseElement.g:193:5: type LBRACE
        {
        pushFollow(FOLLOW_type_in_synpred4_BaseElement1364);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,LBRACE,FOLLOW_LBRACE_in_synpred4_BaseElement1366); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_BaseElement

    // $ANTLR start synpred5_BaseElement
    public final void synpred5_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:194:4: ( arrayAllocator )
        // BaseElement.g:194:5: arrayAllocator
        {
        pushFollow(FOLLOW_arrayAllocator_in_synpred5_BaseElement1375);
        arrayAllocator();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_BaseElement

    // $ANTLR start synpred6_BaseElement
    public final void synpred6_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:195:4: ( methodName '(' )
        // BaseElement.g:195:5: methodName '('
        {
        pushFollow(FOLLOW_methodName_in_synpred6_BaseElement1384);
        methodName();

        state._fsp--;
        if (state.failed) return ;
        match(input,LPAREN,FOLLOW_LPAREN_in_synpred6_BaseElement1386); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_BaseElement

    // $ANTLR start synpred7_BaseElement
    public final void synpred7_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:260:4: ( methodName '(' )
        // BaseElement.g:260:5: methodName '('
        {
        pushFollow(FOLLOW_methodName_in_synpred7_BaseElement1668);
        methodName();

        state._fsp--;
        if (state.failed) return ;
        match(input,LPAREN,FOLLOW_LPAREN_in_synpred7_BaseElement1670); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_BaseElement

    // $ANTLR start synpred8_BaseElement
    public final void synpred8_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:269:4: ( methodName '(' )
        // BaseElement.g:269:5: methodName '('
        {
        pushFollow(FOLLOW_methodName_in_synpred8_BaseElement1836);
        methodName();

        state._fsp--;
        if (state.failed) return ;
        match(input,LPAREN,FOLLOW_LPAREN_in_synpred8_BaseElement1838); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_BaseElement

    // $ANTLR start synpred9_BaseElement
    public final void synpred9_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:322:4: ( type identifier )
        // BaseElement.g:322:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred9_BaseElement2282);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred9_BaseElement2284);
        gRules.identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_BaseElement

    // $ANTLR start synpred10_BaseElement
    public final void synpred10_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:348:5: ({...}? actionContextStatement )
        // BaseElement.g:348:6: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred10_BaseElement", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred10_BaseElement2501);
        gRules.actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_BaseElement

    // $ANTLR start synpred11_BaseElement
    public final void synpred11_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:348:87: ( type identifier )
        // BaseElement.g:348:88: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred11_BaseElement2510);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred11_BaseElement2512);
        gRules.identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_BaseElement

    // $ANTLR start synpred12_BaseElement
    public final void synpred12_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:418:3: ( localInitializerArrayLiteral ( ';' | ',' ) )
        // BaseElement.g:418:4: localInitializerArrayLiteral ( ';' | ',' )
        {
        pushFollow(FOLLOW_localInitializerArrayLiteral_in_synpred12_BaseElement2876);
        localInitializerArrayLiteral();

        state._fsp--;
        if (state.failed) return ;
        if ( input.LA(1)==SEMICOLON||input.LA(1)==216 ) {
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
    // $ANTLR end synpred12_BaseElement

    // $ANTLR start synpred13_BaseElement
    public final void synpred13_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:496:4: ({...}? actionContextStatement )
        // BaseElement.g:496:5: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_BaseElement", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred13_BaseElement3395);
        gRules.actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_BaseElement

    // $ANTLR start synpred14_BaseElement
    public final void synpred14_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:497:4: ( type identifier )
        // BaseElement.g:497:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred14_BaseElement3405);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred14_BaseElement3407);
        gRules.identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_BaseElement

    // $ANTLR start synpred15_BaseElement
    public final void synpred15_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:526:5: ({...}? actionContextStatement )
        // BaseElement.g:526:6: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred15_BaseElement", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred15_BaseElement3539);
        gRules.actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_BaseElement

    // $ANTLR start synpred16_BaseElement
    public final void synpred16_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:526:87: ( type identifier )
        // BaseElement.g:526:88: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred16_BaseElement3548);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred16_BaseElement3550);
        gRules.identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_BaseElement

    // Delegated rules

    public final boolean synpred15_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_BaseElement() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_BaseElement_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA65 dfa65 = new DFA65(this);
    static final String DFA8_eotS =
        "\26\uffff";
    static final String DFA8_eofS =
        "\26\uffff";
    static final String DFA8_minS =
        "\1\152\25\uffff";
    static final String DFA8_maxS =
        "\1\u00e7\25\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\14\1\2\2\1\3\6\uffff";
    static final String DFA8_specialS =
        "\1\0\25\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\12\3\uffff\7\17\1\16\1\uffff\1\1\1\2\3\uffff\1\3\2\uffff"+
            "\1\5\1\6\1\10\2\4\2\7\13\uffff\1\11\1\uffff\1\15\20\uffff\61"+
            "\13\17\uffff\4\14",
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

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "113:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_0 = input.LA(1);

                         
                        int index8_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA8_0==PLUS) ) {s = 1;}

                        else if ( (LA8_0==MINUS) ) {s = 2;}

                        else if ( (LA8_0==POUND) ) {s = 3;}

                        else if ( ((LA8_0>=DecimalLiteral && LA8_0<=HexLiteral)) ) {s = 4;}

                        else if ( (LA8_0==FloatingPointLiteral) ) {s = 5;}

                        else if ( (LA8_0==StringLiteral) ) {s = 6;}

                        else if ( ((LA8_0>=TRUE && LA8_0<=FALSE)) ) {s = 7;}

                        else if ( (LA8_0==NullLiteral) ) {s = 8;}

                        else if ( (LA8_0==LPAREN) ) {s = 9;}

                        else if ( (LA8_0==Identifier) ) {s = 10;}

                        else if ( ((LA8_0>=164 && LA8_0<=212)) ) {s = 11;}

                        else if ( ((LA8_0>=228 && LA8_0<=231)) ) {s = 12;}

                        else if ( (LA8_0==LBRACK) && (synpred1_BaseElement())) {s = 13;}

                        else if ( (LA8_0==LBRACE) && (synpred1_BaseElement())) {s = 14;}

                        else if ( ((LA8_0>=EQ && LA8_0<=GE)) ) {s = 15;}

                         
                        input.seek(index8_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA10_eotS =
        "\26\uffff";
    static final String DFA10_eofS =
        "\26\uffff";
    static final String DFA10_minS =
        "\1\152\25\uffff";
    static final String DFA10_maxS =
        "\1\u00e7\25\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\14\1\2\2\1\3\6\uffff";
    static final String DFA10_specialS =
        "\1\0\25\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\12\3\uffff\7\17\1\16\1\uffff\1\1\1\2\3\uffff\1\3\2\uffff"+
            "\1\5\1\6\1\10\2\4\2\7\13\uffff\1\11\1\uffff\1\15\20\uffff\61"+
            "\13\17\uffff\4\14",
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

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "118:3: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_0 = input.LA(1);

                         
                        int index10_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA10_0==PLUS) ) {s = 1;}

                        else if ( (LA10_0==MINUS) ) {s = 2;}

                        else if ( (LA10_0==POUND) ) {s = 3;}

                        else if ( ((LA10_0>=DecimalLiteral && LA10_0<=HexLiteral)) ) {s = 4;}

                        else if ( (LA10_0==FloatingPointLiteral) ) {s = 5;}

                        else if ( (LA10_0==StringLiteral) ) {s = 6;}

                        else if ( ((LA10_0>=TRUE && LA10_0<=FALSE)) ) {s = 7;}

                        else if ( (LA10_0==NullLiteral) ) {s = 8;}

                        else if ( (LA10_0==LPAREN) ) {s = 9;}

                        else if ( (LA10_0==Identifier) ) {s = 10;}

                        else if ( ((LA10_0>=164 && LA10_0<=212)) ) {s = 11;}

                        else if ( ((LA10_0>=228 && LA10_0<=231)) ) {s = 12;}

                        else if ( (LA10_0==LBRACK) && (synpred2_BaseElement())) {s = 13;}

                        else if ( (LA10_0==LBRACE) && (synpred2_BaseElement())) {s = 14;}

                        else if ( ((LA10_0>=EQ && LA10_0<=GE)) ) {s = 15;}

                         
                        input.seek(index10_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA30_eotS =
        "\16\uffff";
    static final String DFA30_eofS =
        "\16\uffff";
    static final String DFA30_minS =
        "\1\152\6\uffff\3\0\4\uffff";
    static final String DFA30_maxS =
        "\1\u00e7\6\uffff\3\0\4\uffff";
    static final String DFA30_acceptS =
        "\1\uffff\1\1\3\uffff\1\1\1\2\3\uffff\1\3\1\4\1\5\1\6";
    static final String DFA30_specialS =
        "\7\uffff\1\0\1\1\1\2\4\uffff}>";
    static final String[] DFA30_transitionS = {
            "\1\7\24\uffff\2\1\1\5\4\1\13\uffff\1\6\22\uffff\61\10\17\uffff"+
            "\4\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "190:1: primaryPrefix : ( literal | '(' expression ')' | ( type LBRACE )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=>m= methodName argumentsSuffix -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ) | expressionName );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_7 = input.LA(1);

                         
                        int index30_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_BaseElement()) ) {s = 10;}

                        else if ( (synpred5_BaseElement()) ) {s = 11;}

                        else if ( (synpred6_BaseElement()) ) {s = 12;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index30_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_8 = input.LA(1);

                         
                        int index30_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_BaseElement()) ) {s = 10;}

                        else if ( (synpred5_BaseElement()) ) {s = 11;}

                        else if ( (synpred6_BaseElement()) ) {s = 12;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index30_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA30_9 = input.LA(1);

                         
                        int index30_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_BaseElement()) ) {s = 10;}

                        else if ( (synpred5_BaseElement()) ) {s = 11;}

                         
                        input.seek(index30_9);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA43_eotS =
        "\13\uffff";
    static final String DFA43_eofS =
        "\13\uffff";
    static final String DFA43_minS =
        "\1\152\2\0\4\uffff\1\0\3\uffff";
    static final String DFA43_maxS =
        "\1\u00e7\2\0\4\uffff\1\0\3\uffff";
    static final String DFA43_acceptS =
        "\3\uffff\1\2\6\uffff\1\1";
    static final String DFA43_specialS =
        "\1\uffff\1\0\1\1\4\uffff\1\2\3\uffff}>";
    static final String[] DFA43_transitionS = {
            "\1\1\24\uffff\2\3\1\2\4\3\13\uffff\1\3\22\uffff\61\7\17\uffff"+
            "\4\3",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA43_eot = DFA.unpackEncodedString(DFA43_eotS);
    static final short[] DFA43_eof = DFA.unpackEncodedString(DFA43_eofS);
    static final char[] DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
    static final char[] DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
    static final short[] DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
    static final short[] DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
    static final short[][] DFA43_transition;

    static {
        int numStates = DFA43_transitionS.length;
        DFA43_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = DFA43_eot;
            this.eof = DFA43_eof;
            this.min = DFA43_min;
            this.max = DFA43_max;
            this.accept = DFA43_accept;
            this.special = DFA43_special;
            this.transition = DFA43_transition;
        }
        public String getDescription() {
            return "259:1: statementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? SEMICOLON -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) SEMICOLON -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_1 = input.LA(1);

                         
                        int index43_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index43_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA43_2 = input.LA(1);

                         
                        int index43_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index43_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA43_7 = input.LA(1);

                         
                        int index43_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index43_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA50_eotS =
        "\13\uffff";
    static final String DFA50_eofS =
        "\13\uffff";
    static final String DFA50_minS =
        "\1\152\2\0\4\uffff\1\0\3\uffff";
    static final String DFA50_maxS =
        "\1\u00e7\2\0\4\uffff\1\0\3\uffff";
    static final String DFA50_acceptS =
        "\3\uffff\1\2\6\uffff\1\1";
    static final String DFA50_specialS =
        "\1\uffff\1\0\1\1\4\uffff\1\2\3\uffff}>";
    static final String[] DFA50_transitionS = {
            "\1\1\24\uffff\2\3\1\2\4\3\13\uffff\1\3\22\uffff\61\7\17\uffff"+
            "\4\3",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA50_eot = DFA.unpackEncodedString(DFA50_eotS);
    static final short[] DFA50_eof = DFA.unpackEncodedString(DFA50_eofS);
    static final char[] DFA50_min = DFA.unpackEncodedStringToUnsignedChars(DFA50_minS);
    static final char[] DFA50_max = DFA.unpackEncodedStringToUnsignedChars(DFA50_maxS);
    static final short[] DFA50_accept = DFA.unpackEncodedString(DFA50_acceptS);
    static final short[] DFA50_special = DFA.unpackEncodedString(DFA50_specialS);
    static final short[][] DFA50_transition;

    static {
        int numStates = DFA50_transitionS.length;
        DFA50_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA50_transition[i] = DFA.unpackEncodedString(DFA50_transitionS[i]);
        }
    }

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = DFA50_eot;
            this.eof = DFA50_eof;
            this.min = DFA50_min;
            this.max = DFA50_max;
            this.accept = DFA50_accept;
            this.special = DFA50_special;
            this.transition = DFA50_transition;
        }
        public String getDescription() {
            return "268:1: listStatementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA50_1 = input.LA(1);

                         
                        int index50_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index50_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA50_2 = input.LA(1);

                         
                        int index50_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index50_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA50_7 = input.LA(1);

                         
                        int index50_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_BaseElement()) ) {s = 10;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index50_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 50, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA54_eotS =
        "\17\uffff";
    static final String DFA54_eofS =
        "\17\uffff";
    static final String DFA54_minS =
        "\4\152\1\uffff\1\152\1\uffff\2\152\2\uffff\4\152";
    static final String DFA54_maxS =
        "\1\u00e7\3\u00d4\1\uffff\1\u00d4\1\uffff\1\u00d4\1\u00e7\2\uffff"+
        "\1\u00e7\3\u00d4";
    static final String DFA54_acceptS =
        "\4\uffff\1\2\1\uffff\1\3\2\uffff\2\1\4\uffff";
    static final String DFA54_specialS =
        "\1\uffff\1\2\1\6\1\1\1\uffff\1\0\6\uffff\1\3\1\5\1\4}>";
    static final String[] DFA54_transitionS = {
            "\1\1\1\6\23\uffff\2\4\1\2\4\4\13\uffff\1\4\22\uffff\61\5\17"+
            "\uffff\4\3",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\20\uffff\61\12",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\20\uffff\61\12",
            "\1\11\12\uffff\1\4\13\uffff\1\12\21\uffff\1\13\20\uffff\61"+
            "\12",
            "",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\20\uffff\61\12",
            "",
            "\1\14\26\uffff\1\15\42\uffff\61\15",
            "\1\4\3\uffff\10\4\1\uffff\2\4\3\uffff\1\4\2\uffff\7\4\13\uffff"+
            "\1\4\1\uffff\1\4\1\16\17\uffff\61\4\17\uffff\4\4",
            "",
            "",
            "\1\4\3\uffff\10\4\1\uffff\2\4\3\uffff\1\4\2\uffff\7\4\13\uffff"+
            "\1\4\1\uffff\1\4\1\16\17\uffff\61\4\17\uffff\4\4",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\20\uffff\61\12",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\20\uffff\61\12",
            "\1\11\12\uffff\1\4\13\uffff\1\12\21\uffff\1\4\20\uffff\61"+
            "\12"
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
            return "322:2: ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA54_5 = input.LA(1);

                         
                        int index54_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_5==DOT) ) {s = 7;}

                        else if ( (LA54_5==LBRACK) ) {s = 8;}

                        else if ( (LA54_5==LBRACE||LA54_5==ANNOTATE||(LA54_5>=INCR && LA54_5<=MOD_EQUAL)||LA54_5==LPAREN) ) {s = 4;}

                        else if ( (LA54_5==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_5==NullLiteral||(LA54_5>=164 && LA54_5<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA54_3 = input.LA(1);

                         
                        int index54_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_3==LBRACK) ) {s = 11;}

                        else if ( (LA54_3==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_3==NullLiteral||(LA54_3>=164 && LA54_3<=212)) && (synpred9_BaseElement())) {s = 10;}

                        else if ( (LA54_3==LBRACE) ) {s = 4;}

                         
                        input.seek(index54_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA54_1 = input.LA(1);

                         
                        int index54_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_1==DOT) ) {s = 7;}

                        else if ( (LA54_1==LBRACE||LA54_1==ANNOTATE||(LA54_1>=INCR && LA54_1<=MOD_EQUAL)||LA54_1==LPAREN) ) {s = 4;}

                        else if ( (LA54_1==LBRACK) ) {s = 8;}

                        else if ( (LA54_1==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_1==NullLiteral||(LA54_1>=164 && LA54_1<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_1);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA54_12 = input.LA(1);

                         
                        int index54_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_12==LBRACK) ) {s = 8;}

                        else if ( (LA54_12==LBRACE||LA54_12==ANNOTATE||(LA54_12>=INCR && LA54_12<=MOD_EQUAL)||LA54_12==LPAREN) ) {s = 4;}

                        else if ( (LA54_12==DOT) ) {s = 7;}

                        else if ( (LA54_12==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_12==NullLiteral||(LA54_12>=164 && LA54_12<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA54_14 = input.LA(1);

                         
                        int index54_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_14==LBRACE||LA54_14==LBRACK) ) {s = 4;}

                        else if ( (LA54_14==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_14==NullLiteral||(LA54_14>=164 && LA54_14<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_14);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA54_13 = input.LA(1);

                         
                        int index54_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_13==LBRACK) ) {s = 8;}

                        else if ( (LA54_13==LBRACE||LA54_13==ANNOTATE||(LA54_13>=INCR && LA54_13<=MOD_EQUAL)||LA54_13==LPAREN) ) {s = 4;}

                        else if ( (LA54_13==DOT) ) {s = 7;}

                        else if ( (LA54_13==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_13==NullLiteral||(LA54_13>=164 && LA54_13<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_13);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA54_2 = input.LA(1);

                         
                        int index54_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_2==DOT) ) {s = 7;}

                        else if ( (LA54_2==LBRACK) ) {s = 8;}

                        else if ( (LA54_2==LBRACE||LA54_2==ANNOTATE||(LA54_2>=INCR && LA54_2<=MOD_EQUAL)||LA54_2==LPAREN) ) {s = 4;}

                        else if ( (LA54_2==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_2==NullLiteral||(LA54_2>=164 && LA54_2<=212)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_2);
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
    static final String DFA59_eotS =
        "\50\uffff";
    static final String DFA59_eofS =
        "\50\uffff";
    static final String DFA59_minS =
        "\1\152\1\uffff\6\152\1\uffff\7\152\2\153\2\uffff\4\152\3\uffff"+
        "\1\152\2\153\1\152\2\153\1\0\2\152\1\0\2\152\1\uffff";
    static final String DFA59_maxS =
        "\1\u00e7\1\uffff\6\u00d4\1\uffff\2\u00d4\1\u00e7\6\u00d8\2\uffff"+
        "\1\u00e7\3\u00d4\3\uffff\1\u00d4\2\153\1\u00d4\2\153\1\0\2\u00d4"+
        "\1\0\2\u00d4\1\uffff";
    static final String DFA59_acceptS =
        "\1\uffff\1\4\6\uffff\1\3\11\uffff\2\2\4\uffff\3\2\14\uffff\1\1";
    static final String DFA59_specialS =
        "\2\uffff\1\23\1\11\1\12\1\20\1\4\1\14\1\uffff\1\7\2\uffff\1\33"+
        "\1\5\1\21\1\24\1\10\1\2\3\uffff\1\1\1\16\1\30\3\uffff\1\26\1\15"+
        "\1\13\1\25\1\17\1\27\1\6\1\32\1\3\1\22\1\31\1\0\1\uffff}>";
    static final String[] DFA59_transitionS = {
            "\1\5\1\10\11\uffff\1\10\1\1\10\uffff\2\10\1\6\4\10\13\uffff"+
            "\1\10\22\uffff\51\11\1\3\1\4\1\2\5\11\4\uffff\5\10\1\uffff\3"+
            "\10\2\uffff\4\7",
            "",
            "\1\14\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\15\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\15",
            "\1\16\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\17\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\17",
            "\1\20\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\21\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\21",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\10\13\uffff\1\23\21\uffff\1\24\20\uffff\61"+
            "\23",
            "",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\23",
            "\1\25\26\uffff\1\26\42\uffff\61\26",
            "\1\10\3\uffff\10\10\1\uffff\2\10\3\uffff\1\10\2\uffff\7\10"+
            "\13\uffff\1\10\1\uffff\1\10\1\27\17\uffff\61\10\17\uffff\4\10",
            "\1\34\1\32\21\uffff\1\33\3\uffff\1\35\7\uffff\1\30\32\uffff"+
            "\61\35\3\uffff\1\31",
            "\1\34\1\32\21\uffff\1\33\3\uffff\1\35\7\uffff\1\30\32\uffff"+
            "\61\35\3\uffff\1\31",
            "\1\37\1\32\21\uffff\1\36\3\uffff\1\40\7\uffff\1\30\32\uffff"+
            "\61\40\3\uffff\1\31",
            "\1\37\1\32\21\uffff\1\36\3\uffff\1\40\7\uffff\1\30\32\uffff"+
            "\61\40\3\uffff\1\31",
            "\1\41\35\uffff\1\30\116\uffff\1\31",
            "\1\41\35\uffff\1\30\116\uffff\1\31",
            "",
            "",
            "\1\10\3\uffff\10\10\1\uffff\2\10\3\uffff\1\10\2\uffff\7\10"+
            "\13\uffff\1\10\1\uffff\1\10\1\27\17\uffff\61\10\17\uffff\4\10",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\10\13\uffff\1\23\21\uffff\1\10\20\uffff\61"+
            "\23",
            "",
            "",
            "",
            "\1\42\26\uffff\1\43\42\uffff\61\43",
            "\1\44",
            "\1\44",
            "\1\45\26\uffff\1\46\42\uffff\61\46",
            "\1\44",
            "\1\44",
            "\1\uffff",
            "\1\34\22\uffff\1\33\3\uffff\1\35\42\uffff\61\35",
            "\1\34\22\uffff\1\33\3\uffff\1\35\42\uffff\61\35",
            "\1\uffff",
            "\1\37\22\uffff\1\36\3\uffff\1\40\42\uffff\61\40",
            "\1\37\22\uffff\1\36\3\uffff\1\40\42\uffff\61\40",
            ""
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
            return "()* loopback of 348:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA59_38 = input.LA(1);

                         
                        int index59_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_38==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA59_38==NullLiteral||(LA59_38>=164 && LA59_38<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA59_38==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index59_38);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA59_21 = input.LA(1);

                         
                        int index59_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_21==LBRACK) ) {s = 11;}

                        else if ( (LA59_21==DOT) ) {s = 10;}

                        else if ( (LA59_21==LBRACE||LA59_21==ANNOTATE||(LA59_21>=INCR && LA59_21<=MOD_EQUAL)||LA59_21==LPAREN) ) {s = 8;}

                        else if ( (LA59_21==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_21==NullLiteral||(LA59_21>=164 && LA59_21<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA59_17 = input.LA(1);

                         
                        int index59_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_17==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA59_17==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_17==216) && (synpred11_BaseElement())) {s = 25;}

                         
                        input.seek(index59_17);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA59_35 = input.LA(1);

                         
                        int index59_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_35==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA59_35==NullLiteral||(LA59_35>=164 && LA59_35<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA59_35==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                         
                        input.seek(index59_35);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA59_6 = input.LA(1);

                         
                        int index59_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_6==DOT) ) {s = 10;}

                        else if ( (LA59_6==LBRACK) ) {s = 11;}

                        else if ( (LA59_6==LBRACE||LA59_6==ANNOTATE||(LA59_6>=INCR && LA59_6<=MOD_EQUAL)||LA59_6==LPAREN) ) {s = 8;}

                        else if ( (LA59_6==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_6==NullLiteral||(LA59_6>=164 && LA59_6<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA59_13 = input.LA(1);

                         
                        int index59_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_13==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                        else if ( (LA59_13==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA59_13==NullLiteral||(LA59_13>=164 && LA59_13<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA59_13==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_13==216) && (synpred11_BaseElement())) {s = 25;}

                        else if ( (LA59_13==SEMICOLON) && (synpred11_BaseElement())) {s = 26;}

                         
                        input.seek(index59_13);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA59_33 = input.LA(1);

                         
                        int index59_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_BaseElement()) ) {s = 39;}

                        else if ( (synpred11_BaseElement()) ) {s = 26;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index59_33);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA59_9 = input.LA(1);

                         
                        int index59_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_9==DOT) ) {s = 10;}

                        else if ( (LA59_9==LBRACK) ) {s = 11;}

                        else if ( (LA59_9==LBRACE||LA59_9==ANNOTATE||(LA59_9>=INCR && LA59_9<=MOD_EQUAL)||LA59_9==LPAREN) ) {s = 8;}

                        else if ( (LA59_9==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_9==NullLiteral||(LA59_9>=164 && LA59_9<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA59_16 = input.LA(1);

                         
                        int index59_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_16==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_16==216) && (synpred11_BaseElement())) {s = 25;}

                        else if ( (LA59_16==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                         
                        input.seek(index59_16);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA59_3 = input.LA(1);

                         
                        int index59_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_3==Identifier) && (( isInActionContextBlock() ))) {s = 14;}

                        else if ( (LA59_3==NullLiteral||(LA59_3>=164 && LA59_3<=212)) && (( isInActionContextBlock() ))) {s = 15;}

                        else if ( (LA59_3==DOT) ) {s = 10;}

                        else if ( (LA59_3==LBRACK) ) {s = 11;}

                        else if ( (LA59_3==LBRACE||LA59_3==ANNOTATE||(LA59_3>=INCR && LA59_3<=MOD_EQUAL)||LA59_3==LPAREN) ) {s = 8;}

                         
                        input.seek(index59_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA59_4 = input.LA(1);

                         
                        int index59_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_4==DOT) ) {s = 10;}

                        else if ( (LA59_4==LBRACK) ) {s = 11;}

                        else if ( (LA59_4==LBRACE||LA59_4==ANNOTATE||(LA59_4>=INCR && LA59_4<=MOD_EQUAL)||LA59_4==LPAREN) ) {s = 8;}

                        else if ( (LA59_4==Identifier) && (( isInActionContextBlock() ))) {s = 16;}

                        else if ( (LA59_4==NullLiteral||(LA59_4>=164 && LA59_4<=212)) && (( isInActionContextBlock() ))) {s = 17;}

                         
                        input.seek(index59_4);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA59_29 = input.LA(1);

                         
                        int index59_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_29==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index59_29);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA59_7 = input.LA(1);

                         
                        int index59_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_7==LBRACK) ) {s = 20;}

                        else if ( (LA59_7==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_7==NullLiteral||(LA59_7>=164 && LA59_7<=212)) && (synpred11_BaseElement())) {s = 19;}

                        else if ( (LA59_7==LBRACE) ) {s = 8;}

                         
                        input.seek(index59_7);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA59_28 = input.LA(1);

                         
                        int index59_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_28==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index59_28);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA59_22 = input.LA(1);

                         
                        int index59_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_22==LBRACE||LA59_22==ANNOTATE||(LA59_22>=INCR && LA59_22<=MOD_EQUAL)||LA59_22==LPAREN) ) {s = 8;}

                        else if ( (LA59_22==DOT) ) {s = 10;}

                        else if ( (LA59_22==LBRACK) ) {s = 11;}

                        else if ( (LA59_22==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_22==NullLiteral||(LA59_22>=164 && LA59_22<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_22);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA59_31 = input.LA(1);

                         
                        int index59_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_31==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index59_31);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA59_5 = input.LA(1);

                         
                        int index59_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_5==DOT) ) {s = 10;}

                        else if ( (LA59_5==LBRACK) ) {s = 11;}

                        else if ( (LA59_5==LBRACE||LA59_5==ANNOTATE||(LA59_5>=INCR && LA59_5<=MOD_EQUAL)||LA59_5==LPAREN) ) {s = 8;}

                        else if ( (LA59_5==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_5==NullLiteral||(LA59_5>=164 && LA59_5<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_5);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA59_14 = input.LA(1);

                         
                        int index59_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_14==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA59_14==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA59_14==NullLiteral||(LA59_14>=164 && LA59_14<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA59_14==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_14==216) && (synpred11_BaseElement())) {s = 25;}

                        else if ( (LA59_14==SEMICOLON) && (synpred11_BaseElement())) {s = 26;}

                         
                        input.seek(index59_14);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA59_36 = input.LA(1);

                         
                        int index59_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_BaseElement()) ) {s = 39;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index59_36);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA59_2 = input.LA(1);

                         
                        int index59_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_2==DOT) ) {s = 10;}

                        else if ( (LA59_2==LBRACK) ) {s = 11;}

                        else if ( (LA59_2==LBRACE||LA59_2==ANNOTATE||(LA59_2>=INCR && LA59_2<=MOD_EQUAL)||LA59_2==LPAREN) ) {s = 8;}

                        else if ( (LA59_2==Identifier) && (( isInActionContextBlock() ))) {s = 12;}

                        else if ( (LA59_2==NullLiteral||(LA59_2>=164 && LA59_2<=212)) && (( isInActionContextBlock() ))) {s = 13;}

                         
                        input.seek(index59_2);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA59_15 = input.LA(1);

                         
                        int index59_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_15==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA59_15==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA59_15==NullLiteral||(LA59_15>=164 && LA59_15<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA59_15==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_15==216) && (synpred11_BaseElement())) {s = 25;}

                        else if ( (LA59_15==SEMICOLON) && (synpred11_BaseElement())) {s = 26;}

                         
                        input.seek(index59_15);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA59_30 = input.LA(1);

                         
                        int index59_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_30==Identifier) && (( isInActionContextBlock() ))) {s = 37;}

                        else if ( (LA59_30==NullLiteral||(LA59_30>=164 && LA59_30<=212)) && (( isInActionContextBlock() ))) {s = 38;}

                         
                        input.seek(index59_30);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA59_27 = input.LA(1);

                         
                        int index59_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_27==Identifier) && (( isInActionContextBlock() ))) {s = 34;}

                        else if ( (LA59_27==NullLiteral||(LA59_27>=164 && LA59_27<=212)) && (( isInActionContextBlock() ))) {s = 35;}

                         
                        input.seek(index59_27);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA59_32 = input.LA(1);

                         
                        int index59_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_32==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index59_32);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA59_23 = input.LA(1);

                         
                        int index59_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_23==LBRACE||LA59_23==LBRACK) ) {s = 8;}

                        else if ( (LA59_23==Identifier) && (synpred11_BaseElement())) {s = 18;}

                        else if ( (LA59_23==NullLiteral||(LA59_23>=164 && LA59_23<=212)) && (synpred11_BaseElement())) {s = 19;}

                         
                        input.seek(index59_23);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA59_37 = input.LA(1);

                         
                        int index59_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_37==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA59_37==NullLiteral||(LA59_37>=164 && LA59_37<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA59_37==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index59_37);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA59_34 = input.LA(1);

                         
                        int index59_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_34==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA59_34==NullLiteral||(LA59_34>=164 && LA59_34<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA59_34==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                         
                        input.seek(index59_34);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA59_12 = input.LA(1);

                         
                        int index59_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA59_12==ASSIGN) && (synpred11_BaseElement())) {s = 24;}

                        else if ( (LA59_12==216) && (synpred11_BaseElement())) {s = 25;}

                        else if ( (LA59_12==SEMICOLON) && (synpred11_BaseElement())) {s = 26;}

                        else if ( (LA59_12==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                        else if ( (LA59_12==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA59_12==NullLiteral||(LA59_12>=164 && LA59_12<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                         
                        input.seek(index59_12);
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
    static final String DFA65_eotS =
        "\27\uffff";
    static final String DFA65_eofS =
        "\27\uffff";
    static final String DFA65_minS =
        "\1\152\1\0\25\uffff";
    static final String DFA65_maxS =
        "\1\u00e7\1\0\25\uffff";
    static final String DFA65_acceptS =
        "\2\uffff\1\2\23\uffff\1\1";
    static final String DFA65_specialS =
        "\1\uffff\1\0\25\uffff}>";
    static final String[] DFA65_transitionS = {
            "\1\2\3\uffff\7\2\1\1\1\uffff\2\2\3\uffff\1\2\2\uffff\7\2\13"+
            "\uffff\1\2\1\uffff\1\2\20\uffff\61\2\17\uffff\4\2",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA65_eot = DFA.unpackEncodedString(DFA65_eotS);
    static final short[] DFA65_eof = DFA.unpackEncodedString(DFA65_eofS);
    static final char[] DFA65_min = DFA.unpackEncodedStringToUnsignedChars(DFA65_minS);
    static final char[] DFA65_max = DFA.unpackEncodedStringToUnsignedChars(DFA65_maxS);
    static final short[] DFA65_accept = DFA.unpackEncodedString(DFA65_acceptS);
    static final short[] DFA65_special = DFA.unpackEncodedString(DFA65_specialS);
    static final short[][] DFA65_transition;

    static {
        int numStates = DFA65_transitionS.length;
        DFA65_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA65_transition[i] = DFA.unpackEncodedString(DFA65_transitionS[i]);
        }
    }

    class DFA65 extends DFA {

        public DFA65(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 65;
            this.eot = DFA65_eot;
            this.eof = DFA65_eof;
            this.min = DFA65_min;
            this.max = DFA65_max;
            this.accept = DFA65_accept;
            this.special = DFA65_special;
            this.transition = DFA65_transition;
        }
        public String getDescription() {
            return "418:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA65_1 = input.LA(1);

                         
                        int index65_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred12_BaseElement()) ) {s = 22;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index65_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 65, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_predicateStatement_in_predicateStatements714 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_predicate_in_predicateStatement728 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_predicateStatement730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStatement_in_predicateStatement747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_predicate763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression775 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_OR_in_expression780 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression783 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression797 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_AND_in_conditionalAndExpression802 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression805 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression820 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression829 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression835 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_EQ_in_equalityExpression845 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_NE_in_equalityExpression850 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression857 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression867 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression872 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_EQ_in_comparisonNoLHS894 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x00000000000A0000L});
    public static final BitSet FOLLOW_NE_in_comparisonNoLHS899 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x00000000000A0000L});
    public static final BitSet FOLLOW_domainSpec_in_comparisonNoLHS909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_comparisonNoLHS915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSTANCE_OF_in_comparisonNoLHS925 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_type_in_comparisonNoLHS928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LT_in_comparisonNoLHS938 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_GT_in_comparisonNoLHS943 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_LE_in_comparisonNoLHS948 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_GE_in_comparisonNoLHS953 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_additiveExpression_in_comparisonNoLHS957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeExpression_in_domainSpec970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setMembershipExpression_in_domainSpec974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeStart_in_rangeExpression986 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0011FFFFFL});
    public static final BitSet FOLLOW_expression_in_rangeExpression990 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_rangeExpression993 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0001E003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_rangeExpression997 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0001E003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_rangeEnd_in_rangeExpression1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_rangeStart1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_rangeStart1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RPAREN_in_rangeEnd1051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RBRACK_in_rangeEnd1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_setMembershipExpression1069 = new BitSet(new long[]{0x0000000000000000L,0x91FFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1074 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_setMembershipExpression1077 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1081 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_RBRACE_in_setMembershipExpression1087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1116 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_set_in_relationalExpression1124 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1143 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_INSTANCE_OF_in_relationalExpression1149 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_type_in_relationalExpression1152 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1172 = new BitSet(new long[]{0x0000000000000002L,0x0180000000000000L});
    public static final BitSet FOLLOW_set_in_additiveExpression1177 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1188 = new BitSet(new long[]{0x0000000000000002L,0x0180000000000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1207 = new BitSet(new long[]{0x0000000000000002L,0x0E00000000000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1212 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1227 = new BitSet(new long[]{0x0000000000000002L,0x0E00000000000000L});
    public static final BitSet FOLLOW_PLUS_in_unaryExpression1249 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_MINUS_in_unaryExpression1255 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_POUND_in_unaryExpression1261 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryPrefix_in_primaryExpression1305 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_primarySuffix_in_primaryExpression1310 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_literal_in_primaryPrefix1347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryPrefix1352 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_primaryPrefix1355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryPrefix1357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayLiteral_in_primaryPrefix1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAllocator_in_primaryPrefix1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_primaryPrefix1391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_argumentsSuffix_in_primaryPrefix1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionName_in_primaryPrefix1422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccessSuffix_in_primarySuffix1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldAccessSuffix_in_primarySuffix1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_arrayAccessSuffix1453 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_arrayAccessSuffix1457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_arrayAccessSuffix1459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_fieldAccessSuffix1493 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_ANNOTATE_in_fieldAccessSuffix1498 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_fieldAccessSuffix1506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_argumentsSuffix1521 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000E003FL,0x000000F0003FFFFFL});
    public static final BitSet FOLLOW_argumentList_in_argumentsSuffix1526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_argumentsSuffix1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_argumentList1545 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_argumentList1548 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_argumentList1551 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_xsltLiteral_in_argumentList1558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_integerLiteral_in_literal1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FloatingPointLiteral_in_literal1575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_literal1580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal1585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NullLiteral_in_literal1590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_integerLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_emptyStatement1635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_emptyBodyStatement1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_statementExpression1675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_argumentsSuffix_in_statementExpression1677 = new BitSet(new long[]{0x0000000000000000L,0x6000080000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_primarySuffix_in_statementExpression1682 = new BitSet(new long[]{0x0000000000000000L,0x6000000000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_assignmentOp_in_statementExpression1689 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_statementExpression1691 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_INCR_in_statementExpression1698 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_DECR_in_statementExpression1704 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statementExpression1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_statementExpression1766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000007F80L});
    public static final BitSet FOLLOW_assignmentOp_in_statementExpression1771 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_statementExpression1773 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_INCR_in_statementExpression1780 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_DECR_in_statementExpression1786 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statementExpression1790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_listStatementExpression1843 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_argumentsSuffix_in_listStatementExpression1845 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_primarySuffix_in_listStatementExpression1850 = new BitSet(new long[]{0x0000000000000000L,0x6000000000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_assignmentOp_in_listStatementExpression1857 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_listStatementExpression1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCR_in_listStatementExpression1866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECR_in_listStatementExpression1872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_listStatementExpression1932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000007F80L});
    public static final BitSet FOLLOW_assignmentOp_in_listStatementExpression1937 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_listStatementExpression1939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCR_in_listStatementExpression1946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECR_in_listStatementExpression1952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_assignmentOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_217_in_returnStatement2032 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_returnStatement2035 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_218_in_breakStatement2069 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_breakStatement2071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_219_in_continueStatement2093 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_continueStatement2095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_220_in_throwStatement2115 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_throwStatement2117 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_throwStatement2119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listStatementExpression_in_statementExpressionList2148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_statementExpressionList2151 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00002003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_listStatementExpression_in_statementExpressionList2154 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_221_in_ifRule2167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_ifRule2169 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_ifRule2171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_ifRule2173 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_ifRule2179 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_222_in_ifRule2185 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_ifRule2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_223_in_whileRule2234 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_whileRule2236 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_whileRule2238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_whileRule2240 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_whileRule2242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_224_in_forRule2272 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_forRule2274 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_localVariableDeclaration_in_forRule2288 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2292 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2294 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_emptyStatement_in_forRule2298 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_forRule2303 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2307 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFFFFFFF00006003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2311 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_forRule2315 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_forRule2320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_225_in_tryCatchFinally2360 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_block_in_tryCatchFinally2362 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_catchRule_in_tryCatchFinally2368 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_226_in_catchRule2417 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_catchRule2419 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_type_in_catchRule2428 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_catchRule2432 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_catchRule2438 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_nonBraceBlock_in_catchRule2447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_nonBraceBlock2491 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_nonBraceBlock2505 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_localVariableDeclaration_in_nonBraceBlock2516 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_nonBraceBlock2520 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_nonBraceBlock2527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_227_in_finallyRule2559 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_block_in_finallyRule2561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_semiColon2586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_name2602 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_DOT_in_name2612 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_name2616 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_name_in_nameOrPrimitiveType2656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_nameOrPrimitiveType2661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_expressionName2675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_methodName2687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_typeName2699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_type2710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeName_in_typeAdditionalArrayDim2727 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_primitiveType_in_typeAdditionalArrayDim2734 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_LBRACK_in_typeAdditionalArrayDim2739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_typeAdditionalArrayDim2742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitive_in_primitiveType2764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_primitive0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_localVariableDeclaration2812 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2814 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_localVariableDeclaration2821 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2823 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_localVariableDeclaration2828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_variableDeclarator2865 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_variableDeclarator2870 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_variableDeclarator2892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_localInitializerArrayLiteral2940 = new BitSet(new long[]{0x0000000000000000L,0x91FFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2945 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_216_in_localInitializerArrayLiteral2948 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2952 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_RBRACE_in_localInitializerArrayLiteral2958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_arrayLiteral2985 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_arrayAllocator3020 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_LBRACK_in_arrayAllocator3025 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_expression_in_arrayAllocator3029 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_arrayAllocator3031 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_arrayAllocator3035 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_arrayAllocator3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainSpec_in_synpred1_BaseElement826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainSpec_in_synpred2_BaseElement864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred4_BaseElement1364 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_synpred4_BaseElement1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAllocator_in_synpred5_BaseElement1375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_synpred6_BaseElement1384 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_synpred6_BaseElement1386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_synpred7_BaseElement1668 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_synpred7_BaseElement1670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_synpred8_BaseElement1836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_synpred8_BaseElement1838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred9_BaseElement2282 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred9_BaseElement2284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred10_BaseElement2501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred11_BaseElement2510 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred11_BaseElement2512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_synpred12_BaseElement2876 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_set_in_synpred12_BaseElement2878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred13_BaseElement3395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred14_BaseElement3405 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred14_BaseElement3407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred15_BaseElement3539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred16_BaseElement3548 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred16_BaseElement3550 = new BitSet(new long[]{0x0000000000000002L});

}