// $ANTLR 3.2 Sep 23, 2009 12:02:23 BaseElement.g 2014-03-26 14:10:36
 
package com.tibco.cep.studio.core.grammar.event; 

import com.tibco.cep.studio.core.rules.*; 
import com.tibco.cep.studio.core.rules.ast.*; 
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class Event_BaseElement extends BaseRulesParser {
    public static final int T__259=259;
    public static final int MOD=123;
    public static final int T__258=258;
    public static final int T__257=257;
    public static final int PAYLOAD_BLOCK=181;
    public static final int T__262=262;
    public static final int T__263=263;
    public static final int T__260=260;
    public static final int T__261=261;
    public static final int T__266=266;
    public static final int T__267=267;
    public static final int T__264=264;
    public static final int T__265=265;
    public static final int TRY_STATEMENT=91;
    public static final int EOF=-1;
    public static final int STATEMENT=52;
    public static final int TYPE=38;
    public static final int ARRAY_ALLOCATOR=86;
    public static final int RPAREN=146;
    public static final int T__247=247;
    public static final int MappingEnd=105;
    public static final int T__246=246;
    public static final int T__249=249;
    public static final int T__248=248;
    public static final int INSTANCE_OF=112;
    public static final int DEFAULT_DESTINATION_STATEMENT=171;
    public static final int MOD_EQUAL=142;
    public static final int LOCAL_VARIABLE_DECL=19;
    public static final int T__250=250;
    public static final int ARGS=43;
    public static final int T__251=251;
    public static final int T__252=252;
    public static final int T__253=253;
    public static final int T__254=254;
    public static final int T__255=255;
    public static final int EQ=110;
    public static final int T__256=256;
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
    public static final int WS=134;
    public static final int FINALLY_RULE=81;
    public static final int ACTION_TYPE=15;
    public static final int T__269=269;
    public static final int FloatingPointLiteral=127;
    public static final int T__268=268;
    public static final int LHS=97;
    public static final int JavaIDDigit=158;
    public static final int LOCATION=178;
    public static final int GT=114;
    public static final int STATEMENTS=28;
    public static final int VALIDITY=60;
    public static final int ELSE_STATEMENT=51;
    public static final int VOID_LITERAL=93;
    public static final int BLOCKS=34;
    public static final int T__270=270;
    public static final int BREAK_STATEMENT=74;
    public static final int PROPERTY_DECLARATION=163;
    public static final int T__215=215;
    public static final int T__216=216;
    public static final int RETRY_ON_EXCEPTION_STATEMENT=170;
    public static final int T__213=213;
    public static final int T__214=214;
    public static final int LBRACK=147;
    public static final int T__219=219;
    public static final int T__217=217;
    public static final int DIV_EQUAL=141;
    public static final int T__218=218;
    public static final int TEMPLATE_DECLARATOR=6;
    public static final int DOMAIN_STATEMENT=168;
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
    public static final int PARENT=162;
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
    public static final int PAYLOAD_STRING_STATEMENT=174;
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
    public static final int TTL=179;
    public static final int RULE_DECL=24;
    public static final int BODY_BLOCK=36;
    public static final int LITERALS=175;
    public static final int ARRAY_LITERAL=85;
    public static final int Identifier=106;
    public static final int NAMESPACES_BLOCK=180;
    public static final int NAMESPACE_STATEMENT=176;
    public static final int UNITS=173;
    public static final int PREDICATE_STATEMENT=68;
    public static final int PRIMARY_EXPRESSION=100;
    public static final int NAME=25;
    public static final int DOMAIN_NAMES=169;
    public static final int INCR=135;
    public static final int PROPERTIES_BLOCK=164;
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
    public static final int NAMESPACE=177;
    public static final int DECLARATOR=39;
    public static final int RANGE_EXPRESSION=69;
    public static final int EXPIRY_ACTION_BLOCK=182;
    public static final int SET_MEMBERSHIP_EXPRESSION=72;
    public static final int PREDICATES=33;
    public static final int T__200=200;
    public static final int BINDING_STATEMENT=10;
    public static final int ATTRIBUTE_BLOCK=27;
    public static final int PRIORITY=58;
    public static final int T__201=201;
    public static final int ATTRIBUTES_BLOCK=167;
    public static final int RULE_TEMPLATE=4;
    public static final int OR=108;
    public static final int TTL_STATEMENT=172;
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
    public static final int ATTRIBUTE=166;
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
    public static final int PROPERTY_BLOCK=165;
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
    public static final int ALIAS_STATEMENT=66;
    public static final int LITERAL=67;
    public static final int EVENT_DEFINITION=161;
    public static final int DecimalLiteral=130;
    public static final int TRUE=132;
    public static final int SIMPLE_NAME=18;
    public static final int DOMAIN_MODEL_STATEMENT=12;
    public static final int UnicodeEscape=155;
    public static final int ASSIGN=137;
    public static final int DECR=136;
    public static final int BINDINGS_VIEWS_BLOCK=9;
    public static final int LE=115;

    // delegates
    // delegators
    public EventParser gEvent;
    public EventParser gParent;


        public Event_BaseElement(TokenStream input, EventParser gEvent) {
            this(input, new RecognizerSharedState(), gEvent);
        }
        public Event_BaseElement(TokenStream input, RecognizerSharedState state, EventParser gEvent) {
            super(input, state);
            this.gEvent = gEvent;
             
            gParent = gEvent;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EventParser.tokenNames; }
    public String getGrammarFileName() { return "BaseElement.g"; }


    	public RulesASTNode getHeaderNode() {
    		return null; // root grammars will return the proper value
    	}


    public static class mappingBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mappingBlock"
    // BaseElement.g:48:1: mappingBlock : '<#mapping>' (~ MappingEnd )* MappingEnd ;
    public final Event_BaseElement.mappingBlock_return mappingBlock() throws RecognitionException {
        Event_BaseElement.mappingBlock_return retval = new Event_BaseElement.mappingBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal1=null;
        Token set2=null;
        Token MappingEnd3=null;

        EventASTNode string_literal1_tree=null;
        EventASTNode set2_tree=null;
        EventASTNode MappingEnd3_tree=null;

        try {
            // BaseElement.g:57:2: ( '<#mapping>' (~ MappingEnd )* MappingEnd )
            // BaseElement.g:57:4: '<#mapping>' (~ MappingEnd )* MappingEnd
            {
            root_0 = (EventASTNode)adaptor.nil();

            string_literal1=(Token)match(input,183,FOLLOW_183_in_mappingBlock382); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal1_tree = (EventASTNode)adaptor.create(string_literal1);
            adaptor.addChild(root_0, string_literal1_tree);
            }
            // BaseElement.g:57:17: (~ MappingEnd )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=RULE_TEMPLATE && LA1_0<=SUFFIX_EXPRESSION)||(LA1_0>=Identifier && LA1_0<=270)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // BaseElement.g:57:18: ~ MappingEnd
            	    {
            	    set2=(Token)input.LT(1);
            	    if ( (input.LA(1)>=RULE_TEMPLATE && input.LA(1)<=SUFFIX_EXPRESSION)||(input.LA(1)>=Identifier && input.LA(1)<=270) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set2));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            MappingEnd3=(Token)match(input,MappingEnd,FOLLOW_MappingEnd_in_mappingBlock390); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MappingEnd3_tree = (EventASTNode)adaptor.create(MappingEnd3);
            adaptor.addChild(root_0, MappingEnd3_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "mappingBlock"

    public static class conditionBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionBlock"
    // BaseElement.g:60:1: conditionBlock : ( predicateStatement )* EOF ;
    public final Event_BaseElement.conditionBlock_return conditionBlock() throws RecognitionException {
        Event_BaseElement.conditionBlock_return retval = new Event_BaseElement.conditionBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token EOF5=null;
        Event_BaseElement.predicateStatement_return predicateStatement4 = null;


        EventASTNode EOF5_tree=null;

        try {
            // BaseElement.g:61:2: ( ( predicateStatement )* EOF )
            // BaseElement.g:61:4: ( predicateStatement )* EOF
            {
            root_0 = (EventASTNode)adaptor.nil();

            // BaseElement.g:61:4: ( predicateStatement )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=Identifier && LA2_0<=SEMICOLON)||(LA2_0>=EQ && LA2_0<=LBRACE)||(LA2_0>=PLUS && LA2_0<=MINUS)||LA2_0==POUND||(LA2_0>=FloatingPointLiteral && LA2_0<=FALSE)||LA2_0==LPAREN||LA2_0==LBRACK||(LA2_0>=184 && LA2_0<=232)||(LA2_0>=248 && LA2_0<=251)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // BaseElement.g:61:4: predicateStatement
            	    {
            	    pushFollow(FOLLOW_predicateStatement_in_conditionBlock402);
            	    predicateStatement4=predicateStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, predicateStatement4.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            EOF5=(Token)match(input,EOF,FOLLOW_EOF_in_conditionBlock405); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF5_tree = (EventASTNode)adaptor.create(EOF5);
            adaptor.addChild(root_0, EOF5_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "conditionBlock"

    public static class standaloneExpression_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "standaloneExpression"
    // BaseElement.g:64:1: standaloneExpression : predicate EOF ;
    public final Event_BaseElement.standaloneExpression_return standaloneExpression() throws RecognitionException {
        Event_BaseElement.standaloneExpression_return retval = new Event_BaseElement.standaloneExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token EOF7=null;
        Event_BaseElement.predicate_return predicate6 = null;


        EventASTNode EOF7_tree=null;

        try {
            // BaseElement.g:65:2: ( predicate EOF )
            // BaseElement.g:65:4: predicate EOF
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_predicate_in_standaloneExpression417);
            predicate6=predicate();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, predicate6.getTree());
            EOF7=(Token)match(input,EOF,FOLLOW_EOF_in_standaloneExpression419); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF7_tree = (EventASTNode)adaptor.create(EOF7);
            adaptor.addChild(root_0, EOF7_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "standaloneExpression"

    public static class identifier_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "identifier"
    // BaseElement.g:68:1: identifier : ( Identifier | keywordIdentifier ) -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? ) ;
    public final Event_BaseElement.identifier_return identifier() throws RecognitionException {
        Event_BaseElement.identifier_return retval = new Event_BaseElement.identifier_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token Identifier8=null;
        Event_BaseElement.keywordIdentifier_return keywordIdentifier9 = null;


        EventASTNode Identifier8_tree=null;
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_keywordIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule keywordIdentifier");
        try {
            // BaseElement.g:69:2: ( ( Identifier | keywordIdentifier ) -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? ) )
            // BaseElement.g:69:4: ( Identifier | keywordIdentifier )
            {
            // BaseElement.g:69:4: ( Identifier | keywordIdentifier )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==Identifier) ) {
                alt3=1;
            }
            else if ( (LA3_0==NullLiteral||(LA3_0>=184 && LA3_0<=232)) ) {
                alt3=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // BaseElement.g:69:5: Identifier
                    {
                    Identifier8=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier432); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier8);


                    }
                    break;
                case 2 :
                    // BaseElement.g:69:18: keywordIdentifier
                    {
                    pushFollow(FOLLOW_keywordIdentifier_in_identifier436);
                    keywordIdentifier9=keywordIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_keywordIdentifier.add(keywordIdentifier9.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: Identifier, keywordIdentifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 69:37: -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? )
            {
                // BaseElement.g:69:40: ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SIMPLE_NAME, "SIMPLE_NAME"), root_1);

                // BaseElement.g:69:54: ( Identifier )?
                if ( stream_Identifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_Identifier.nextNode());

                }
                stream_Identifier.reset();
                // BaseElement.g:69:66: ( keywordIdentifier )?
                if ( stream_keywordIdentifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_keywordIdentifier.nextTree());

                }
                stream_keywordIdentifier.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "identifier"

    public static class keywordIdentifier_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keywordIdentifier"
    // BaseElement.g:72:1: keywordIdentifier : ( 'body' | 'validity' | 'null' | 'lock' | 'exists' | 'and' | 'key' | 'alias' | 'rank' | 'virtual' | 'abstract' | 'byte' | 'case' | 'char' | 'class' | 'const' | 'default' | 'do' | 'extends' | 'final' | 'float' | 'goto' | 'implements' | 'import' | 'interface' | 'moveto' | 'native' | 'new' | 'package' | 'private' | 'protected' | 'public' | 'short' | 'static' | 'strictfp' | 'super' | 'switch' | 'synchronized' | 'this' | 'throws' | 'transient' | 'volatile' | 'create' | 'modify' | 'call' | 'views' | 'bindings' | 'display' | 'event' | 'concept' );
    public final Event_BaseElement.keywordIdentifier_return keywordIdentifier() throws RecognitionException {
        Event_BaseElement.keywordIdentifier_return retval = new Event_BaseElement.keywordIdentifier_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set10=null;

        EventASTNode set10_tree=null;

        try {
            // BaseElement.g:73:2: ( 'body' | 'validity' | 'null' | 'lock' | 'exists' | 'and' | 'key' | 'alias' | 'rank' | 'virtual' | 'abstract' | 'byte' | 'case' | 'char' | 'class' | 'const' | 'default' | 'do' | 'extends' | 'final' | 'float' | 'goto' | 'implements' | 'import' | 'interface' | 'moveto' | 'native' | 'new' | 'package' | 'private' | 'protected' | 'public' | 'short' | 'static' | 'strictfp' | 'super' | 'switch' | 'synchronized' | 'this' | 'throws' | 'transient' | 'volatile' | 'create' | 'modify' | 'call' | 'views' | 'bindings' | 'display' | 'event' | 'concept' )
            // BaseElement.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set10=(Token)input.LT(1);
            if ( input.LA(1)==NullLiteral||(input.LA(1)>=184 && input.LA(1)<=232) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set10));
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "keywordIdentifier"

    public static class xsltLiteral_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "xsltLiteral"
    // BaseElement.g:81:1: xsltLiteral : '\"\"xslt://' '\"' '\"' ;
    public final Event_BaseElement.xsltLiteral_return xsltLiteral() throws RecognitionException {
        Event_BaseElement.xsltLiteral_return retval = new Event_BaseElement.xsltLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal11=null;
        Token char_literal12=null;
        Token char_literal13=null;

        EventASTNode string_literal11_tree=null;
        EventASTNode char_literal12_tree=null;
        EventASTNode char_literal13_tree=null;

        try {
            // BaseElement.g:82:2: ( '\"\"xslt://' '\"' '\"' )
            // BaseElement.g:82:4: '\"\"xslt://' '\"' '\"'
            {
            root_0 = (EventASTNode)adaptor.nil();

            string_literal11=(Token)match(input,233,FOLLOW_233_in_xsltLiteral678); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal11_tree = (EventASTNode)adaptor.create(string_literal11);
            adaptor.addChild(root_0, string_literal11_tree);
            }
            char_literal12=(Token)match(input,234,FOLLOW_234_in_xsltLiteral680); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal12_tree = (EventASTNode)adaptor.create(char_literal12);
            adaptor.addChild(root_0, char_literal12_tree);
            }
            char_literal13=(Token)match(input,234,FOLLOW_234_in_xsltLiteral682); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal13_tree = (EventASTNode)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "xsltLiteral"

    public static class voidLiteral_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "voidLiteral"
    // BaseElement.g:85:1: voidLiteral : 'void' -> ^( VOID_LITERAL 'void' ) ;
    public final Event_BaseElement.voidLiteral_return voidLiteral() throws RecognitionException {
        Event_BaseElement.voidLiteral_return retval = new Event_BaseElement.voidLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal14=null;

        EventASTNode string_literal14_tree=null;
        RewriteRuleTokenStream stream_235=new RewriteRuleTokenStream(adaptor,"token 235");

        try {
            // BaseElement.g:86:2: ( 'void' -> ^( VOID_LITERAL 'void' ) )
            // BaseElement.g:86:4: 'void'
            {
            string_literal14=(Token)match(input,235,FOLLOW_235_in_voidLiteral694); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_235.add(string_literal14);



            // AST REWRITE
            // elements: 235
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 86:11: -> ^( VOID_LITERAL 'void' )
            {
                // BaseElement.g:86:14: ^( VOID_LITERAL 'void' )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(VOID_LITERAL, "VOID_LITERAL"), root_1);

                adaptor.addChild(root_1, stream_235.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "voidLiteral"

    public static class predicateStatements_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicateStatements"
    // BaseElement.g:89:1: predicateStatements : ( predicateStatement )* ;
    public final Event_BaseElement.predicateStatements_return predicateStatements() throws RecognitionException {
        Event_BaseElement.predicateStatements_return retval = new Event_BaseElement.predicateStatements_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.predicateStatement_return predicateStatement15 = null;



        try {
            // BaseElement.g:90:2: ( ( predicateStatement )* )
            // BaseElement.g:90:4: ( predicateStatement )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            // BaseElement.g:90:4: ( predicateStatement )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=Identifier && LA4_0<=SEMICOLON)||(LA4_0>=EQ && LA4_0<=LBRACE)||(LA4_0>=PLUS && LA4_0<=MINUS)||LA4_0==POUND||(LA4_0>=FloatingPointLiteral && LA4_0<=FALSE)||LA4_0==LPAREN||LA4_0==LBRACK||(LA4_0>=184 && LA4_0<=232)||(LA4_0>=248 && LA4_0<=251)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // BaseElement.g:90:4: predicateStatement
            	    {
            	    pushFollow(FOLLOW_predicateStatement_in_predicateStatements714);
            	    predicateStatement15=predicateStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, predicateStatement15.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicateStatement"
    // BaseElement.g:93:1: predicateStatement : ( predicate SEMICOLON -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) ) | emptyStatement -> emptyStatement ) ;
    public final Event_BaseElement.predicateStatement_return predicateStatement() throws RecognitionException {
        Event_BaseElement.predicateStatement_return retval = new Event_BaseElement.predicateStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token SEMICOLON17=null;
        Event_BaseElement.predicate_return predicate16 = null;

        Event_BaseElement.emptyStatement_return emptyStatement18 = null;


        EventASTNode SEMICOLON17_tree=null;
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

            if ( (LA5_0==Identifier||(LA5_0>=EQ && LA5_0<=LBRACE)||(LA5_0>=PLUS && LA5_0<=MINUS)||LA5_0==POUND||(LA5_0>=FloatingPointLiteral && LA5_0<=FALSE)||LA5_0==LPAREN||LA5_0==LBRACK||(LA5_0>=184 && LA5_0<=232)||(LA5_0>=248 && LA5_0<=251)) ) {
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
                    predicate16=predicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_predicate.add(predicate16.getTree());
                    SEMICOLON17=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_predicateStatement730); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON17);



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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 94:25: -> ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) )
                    {
                        // BaseElement.g:94:28: ^( PREDICATE_STATEMENT ^( EXPRESSION predicate ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PREDICATE_STATEMENT, "PREDICATE_STATEMENT"), root_1);

                        // BaseElement.g:94:50: ^( EXPRESSION predicate )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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
                    emptyStatement18=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_emptyStatement.add(emptyStatement18.getTree());


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

                    root_0 = (EventASTNode)adaptor.nil();
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicate"
    // BaseElement.g:98:1: predicate : expression ;
    public final Event_BaseElement.predicate_return predicate() throws RecognitionException {
        Event_BaseElement.predicate_return retval = new Event_BaseElement.predicate_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.expression_return expression19 = null;



        try {
            // BaseElement.g:99:2: ( expression )
            // BaseElement.g:99:4: expression
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_expression_in_predicate763);
            expression19=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression19.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // BaseElement.g:102:1: expression : conditionalAndExpression ( OR conditionalAndExpression )* ;
    public final Event_BaseElement.expression_return expression() throws RecognitionException {
        Event_BaseElement.expression_return retval = new Event_BaseElement.expression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token OR21=null;
        Event_BaseElement.conditionalAndExpression_return conditionalAndExpression20 = null;

        Event_BaseElement.conditionalAndExpression_return conditionalAndExpression22 = null;


        EventASTNode OR21_tree=null;

        try {
            // BaseElement.g:103:2: ( conditionalAndExpression ( OR conditionalAndExpression )* )
            // BaseElement.g:103:4: conditionalAndExpression ( OR conditionalAndExpression )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_conditionalAndExpression_in_expression775);
            conditionalAndExpression20=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression20.getTree());
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
            	    OR21=(Token)match(input,OR,FOLLOW_OR_in_expression780); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    OR21_tree = (EventASTNode)adaptor.create(OR21);
            	    root_0 = (EventASTNode)adaptor.becomeRoot(OR21_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_conditionalAndExpression_in_expression783);
            	    conditionalAndExpression22=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, conditionalAndExpression22.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionalAndExpression"
    // BaseElement.g:107:1: conditionalAndExpression : equalityExpression ( AND equalityExpression )* ;
    public final Event_BaseElement.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException {
        Event_BaseElement.conditionalAndExpression_return retval = new Event_BaseElement.conditionalAndExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token AND24=null;
        Event_BaseElement.equalityExpression_return equalityExpression23 = null;

        Event_BaseElement.equalityExpression_return equalityExpression25 = null;


        EventASTNode AND24_tree=null;

        try {
            // BaseElement.g:108:2: ( equalityExpression ( AND equalityExpression )* )
            // BaseElement.g:108:4: equalityExpression ( AND equalityExpression )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression797);
            equalityExpression23=equalityExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression23.getTree());
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
            	    AND24=(Token)match(input,AND,FOLLOW_AND_in_conditionalAndExpression802); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    AND24_tree = (EventASTNode)adaptor.create(AND24);
            	    root_0 = (EventASTNode)adaptor.becomeRoot(AND24_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression805);
            	    equalityExpression25=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, equalityExpression25.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpression"
    // BaseElement.g:112:1: equalityExpression : ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )* ;
    public final Event_BaseElement.equalityExpression_return equalityExpression() throws RecognitionException {
        Event_BaseElement.equalityExpression_return retval = new Event_BaseElement.equalityExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token EQ29=null;
        Token NE30=null;
        Event_BaseElement.relationalExpression_return relationalExpression26 = null;

        Event_BaseElement.domainSpec_return domainSpec27 = null;

        Event_BaseElement.comparisonNoLHS_return comparisonNoLHS28 = null;

        Event_BaseElement.relationalExpression_return relationalExpression31 = null;

        Event_BaseElement.domainSpec_return domainSpec32 = null;

        Event_BaseElement.comparisonNoLHS_return comparisonNoLHS33 = null;


        EventASTNode EQ29_tree=null;
        EventASTNode NE30_tree=null;

        try {
            // BaseElement.g:113:2: ( ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )* )
            // BaseElement.g:113:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) ( ( EQ | NE ) ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS ) )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            // BaseElement.g:113:4: ( relationalExpression | ( domainSpec )=> domainSpec | comparisonNoLHS )
            int alt8=3;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // BaseElement.g:113:5: relationalExpression
                    {
                    pushFollow(FOLLOW_relationalExpression_in_equalityExpression820);
                    relationalExpression26=relationalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression26.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:114:4: ( domainSpec )=> domainSpec
                    {
                    pushFollow(FOLLOW_domainSpec_in_equalityExpression829);
                    domainSpec27=domainSpec();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec27.getTree());

                    }
                    break;
                case 3 :
                    // BaseElement.g:115:4: comparisonNoLHS
                    {
                    pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression835);
                    comparisonNoLHS28=comparisonNoLHS();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS28.getTree());

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
            	            EQ29=(Token)match(input,EQ,FOLLOW_EQ_in_equalityExpression845); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            EQ29_tree = (EventASTNode)adaptor.create(EQ29);
            	            root_0 = (EventASTNode)adaptor.becomeRoot(EQ29_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // BaseElement.g:117:10: NE
            	            {
            	            NE30=(Token)match(input,NE,FOLLOW_NE_in_equalityExpression850); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            NE30_tree = (EventASTNode)adaptor.create(NE30);
            	            root_0 = (EventASTNode)adaptor.becomeRoot(NE30_tree, root_0);
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
            	            relationalExpression31=relationalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression31.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // BaseElement.g:119:5: ( domainSpec )=> domainSpec
            	            {
            	            pushFollow(FOLLOW_domainSpec_in_equalityExpression867);
            	            domainSpec32=domainSpec();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec32.getTree());

            	            }
            	            break;
            	        case 3 :
            	            // BaseElement.g:120:4: comparisonNoLHS
            	            {
            	            pushFollow(FOLLOW_comparisonNoLHS_in_equalityExpression872);
            	            comparisonNoLHS33=comparisonNoLHS();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, comparisonNoLHS33.getTree());

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comparisonNoLHS"
    // BaseElement.g:124:1: comparisonNoLHS : ( ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression ) | ( INSTANCE_OF type ) | ( ( LT | GT | LE | GE ) additiveExpression ) );
    public final Event_BaseElement.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException {
        Event_BaseElement.comparisonNoLHS_return retval = new Event_BaseElement.comparisonNoLHS_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token EQ34=null;
        Token NE35=null;
        Token INSTANCE_OF38=null;
        Token LT40=null;
        Token GT41=null;
        Token LE42=null;
        Token GE43=null;
        Event_BaseElement.domainSpec_return domainSpec36 = null;

        Event_BaseElement.unaryExpression_return unaryExpression37 = null;

        Event_BaseElement.type_return type39 = null;

        Event_BaseElement.additiveExpression_return additiveExpression44 = null;


        EventASTNode EQ34_tree=null;
        EventASTNode NE35_tree=null;
        EventASTNode INSTANCE_OF38_tree=null;
        EventASTNode LT40_tree=null;
        EventASTNode GT41_tree=null;
        EventASTNode LE42_tree=null;
        EventASTNode GE43_tree=null;

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
            case 213:
            case 214:
            case 215:
            case 216:
            case 217:
            case 218:
            case 219:
            case 220:
            case 221:
            case 222:
            case 223:
            case 224:
            case 225:
            case 226:
            case 227:
            case 228:
            case 229:
            case 230:
            case 231:
            case 232:
            case 248:
            case 249:
            case 250:
            case 251:
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
                    root_0 = (EventASTNode)adaptor.nil();

                    // BaseElement.g:125:4: ( ( EQ | NE ) ( domainSpec )=> domainSpec | unaryExpression )
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( ((LA13_0>=EQ && LA13_0<=NE)) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==Identifier||(LA13_0>=PLUS && LA13_0<=MINUS)||LA13_0==POUND||(LA13_0>=FloatingPointLiteral && LA13_0<=FALSE)||LA13_0==LPAREN||(LA13_0>=184 && LA13_0<=232)||(LA13_0>=248 && LA13_0<=251)) ) {
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
                                    EQ34=(Token)match(input,EQ,FOLLOW_EQ_in_comparisonNoLHS894); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    EQ34_tree = (EventASTNode)adaptor.create(EQ34);
                                    root_0 = (EventASTNode)adaptor.becomeRoot(EQ34_tree, root_0);
                                    }

                                    }
                                    break;
                                case 2 :
                                    // BaseElement.g:126:10: NE
                                    {
                                    NE35=(Token)match(input,NE,FOLLOW_NE_in_comparisonNoLHS899); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    NE35_tree = (EventASTNode)adaptor.create(NE35);
                                    root_0 = (EventASTNode)adaptor.becomeRoot(NE35_tree, root_0);
                                    }

                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_domainSpec_in_comparisonNoLHS909);
                            domainSpec36=domainSpec();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, domainSpec36.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:128:5: unaryExpression
                            {
                            pushFollow(FOLLOW_unaryExpression_in_comparisonNoLHS915);
                            unaryExpression37=unaryExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression37.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // BaseElement.g:130:2: ( INSTANCE_OF type )
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    // BaseElement.g:130:2: ( INSTANCE_OF type )
                    // BaseElement.g:130:4: INSTANCE_OF type
                    {
                    INSTANCE_OF38=(Token)match(input,INSTANCE_OF,FOLLOW_INSTANCE_OF_in_comparisonNoLHS925); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INSTANCE_OF38_tree = (EventASTNode)adaptor.create(INSTANCE_OF38);
                    root_0 = (EventASTNode)adaptor.becomeRoot(INSTANCE_OF38_tree, root_0);
                    }
                    pushFollow(FOLLOW_type_in_comparisonNoLHS928);
                    type39=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, type39.getTree());

                    }


                    }
                    break;
                case 3 :
                    // BaseElement.g:131:2: ( ( LT | GT | LE | GE ) additiveExpression )
                    {
                    root_0 = (EventASTNode)adaptor.nil();

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
                            LT40=(Token)match(input,LT,FOLLOW_LT_in_comparisonNoLHS938); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LT40_tree = (EventASTNode)adaptor.create(LT40);
                            root_0 = (EventASTNode)adaptor.becomeRoot(LT40_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // BaseElement.g:131:11: GT
                            {
                            GT41=(Token)match(input,GT,FOLLOW_GT_in_comparisonNoLHS943); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            GT41_tree = (EventASTNode)adaptor.create(GT41);
                            root_0 = (EventASTNode)adaptor.becomeRoot(GT41_tree, root_0);
                            }

                            }
                            break;
                        case 3 :
                            // BaseElement.g:131:17: LE
                            {
                            LE42=(Token)match(input,LE,FOLLOW_LE_in_comparisonNoLHS948); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            LE42_tree = (EventASTNode)adaptor.create(LE42);
                            root_0 = (EventASTNode)adaptor.becomeRoot(LE42_tree, root_0);
                            }

                            }
                            break;
                        case 4 :
                            // BaseElement.g:131:23: GE
                            {
                            GE43=(Token)match(input,GE,FOLLOW_GE_in_comparisonNoLHS953); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            GE43_tree = (EventASTNode)adaptor.create(GE43);
                            root_0 = (EventASTNode)adaptor.becomeRoot(GE43_tree, root_0);
                            }

                            }
                            break;

                    }

                    pushFollow(FOLLOW_additiveExpression_in_comparisonNoLHS957);
                    additiveExpression44=additiveExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression44.getTree());

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainSpec"
    // BaseElement.g:134:1: domainSpec : ( rangeExpression | setMembershipExpression );
    public final Event_BaseElement.domainSpec_return domainSpec() throws RecognitionException {
        Event_BaseElement.domainSpec_return retval = new Event_BaseElement.domainSpec_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.rangeExpression_return rangeExpression45 = null;

        Event_BaseElement.setMembershipExpression_return setMembershipExpression46 = null;



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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_rangeExpression_in_domainSpec970);
                    rangeExpression45=rangeExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, rangeExpression45.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:135:22: setMembershipExpression
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_setMembershipExpression_in_domainSpec974);
                    setMembershipExpression46=setMembershipExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, setMembershipExpression46.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeExpression"
    // BaseElement.g:138:1: rangeExpression : rangeStart (start= expression )? ',' (end= expression )? rangeEnd -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) ) ;
    public final Event_BaseElement.rangeExpression_return rangeExpression() throws RecognitionException {
        Event_BaseElement.rangeExpression_return retval = new Event_BaseElement.rangeExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal48=null;
        Event_BaseElement.expression_return start = null;

        Event_BaseElement.expression_return end = null;

        Event_BaseElement.rangeStart_return rangeStart47 = null;

        Event_BaseElement.rangeEnd_return rangeEnd49 = null;


        EventASTNode char_literal48_tree=null;
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_rangeStart=new RewriteRuleSubtreeStream(adaptor,"rule rangeStart");
        RewriteRuleSubtreeStream stream_rangeEnd=new RewriteRuleSubtreeStream(adaptor,"rule rangeEnd");
        try {
            // BaseElement.g:139:2: ( rangeStart (start= expression )? ',' (end= expression )? rangeEnd -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) ) )
            // BaseElement.g:139:4: rangeStart (start= expression )? ',' (end= expression )? rangeEnd
            {
            pushFollow(FOLLOW_rangeStart_in_rangeExpression986);
            rangeStart47=rangeStart();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_rangeStart.add(rangeStart47.getTree());
            // BaseElement.g:139:20: (start= expression )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==Identifier||(LA17_0>=EQ && LA17_0<=LBRACE)||(LA17_0>=PLUS && LA17_0<=MINUS)||LA17_0==POUND||(LA17_0>=FloatingPointLiteral && LA17_0<=FALSE)||LA17_0==LPAREN||LA17_0==LBRACK||(LA17_0>=184 && LA17_0<=232)||(LA17_0>=248 && LA17_0<=251)) ) {
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

            char_literal48=(Token)match(input,236,FOLLOW_236_in_rangeExpression993); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_236.add(char_literal48);

            // BaseElement.g:139:40: (end= expression )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==Identifier||(LA18_0>=EQ && LA18_0<=LBRACE)||(LA18_0>=PLUS && LA18_0<=MINUS)||LA18_0==POUND||(LA18_0>=FloatingPointLiteral && LA18_0<=FALSE)||LA18_0==LPAREN||LA18_0==LBRACK||(LA18_0>=184 && LA18_0<=232)||(LA18_0>=248 && LA18_0<=251)) ) {
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
            rangeEnd49=rangeEnd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_rangeEnd.add(rangeEnd49.getTree());


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

            root_0 = (EventASTNode)adaptor.nil();
            // 140:3: -> ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) )
            {
                // BaseElement.g:140:6: ^( RANGE_EXPRESSION ^( RANGE_START ( $start)? ) ^( RANGE_END $end) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RANGE_EXPRESSION, "RANGE_EXPRESSION"), root_1);

                // BaseElement.g:140:25: ^( RANGE_START ( $start)? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RANGE_START, "RANGE_START"), root_2);

                // BaseElement.g:140:39: ( $start)?
                if ( stream_start.hasNext() ) {
                    adaptor.addChild(root_2, stream_start.nextTree());

                }
                stream_start.reset();

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:140:48: ^( RANGE_END $end)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RANGE_END, "RANGE_END"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeStart"
    // BaseElement.g:143:1: rangeStart : ( '(' | '[' );
    public final Event_BaseElement.rangeStart_return rangeStart() throws RecognitionException {
        Event_BaseElement.rangeStart_return retval = new Event_BaseElement.rangeStart_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal50=null;
        Token char_literal51=null;

        EventASTNode char_literal50_tree=null;
        EventASTNode char_literal51_tree=null;

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
                    root_0 = (EventASTNode)adaptor.nil();

                    char_literal50=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_rangeStart1034); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal50_tree = (EventASTNode)adaptor.create(char_literal50);
                    adaptor.addChild(root_0, char_literal50_tree);
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:144:10: '['
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    char_literal51=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_rangeStart1038); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal51_tree = (EventASTNode)adaptor.create(char_literal51);
                    adaptor.addChild(root_0, char_literal51_tree);
                    }
                    if ( state.backtracking==0 ) {
                       arrayDepth++; 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rangeEnd"
    // BaseElement.g:147:1: rangeEnd : ( ')' | ']' );
    public final Event_BaseElement.rangeEnd_return rangeEnd() throws RecognitionException {
        Event_BaseElement.rangeEnd_return retval = new Event_BaseElement.rangeEnd_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal52=null;
        Token char_literal53=null;

        EventASTNode char_literal52_tree=null;
        EventASTNode char_literal53_tree=null;

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
                    root_0 = (EventASTNode)adaptor.nil();

                    char_literal52=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_rangeEnd1051); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal52_tree = (EventASTNode)adaptor.create(char_literal52);
                    adaptor.addChild(root_0, char_literal52_tree);
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:148:10: ']'
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    char_literal53=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_rangeEnd1055); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal53_tree = (EventASTNode)adaptor.create(char_literal53);
                    adaptor.addChild(root_0, char_literal53_tree);
                    }
                    if ( state.backtracking==0 ) {
                       arrayDepth--; 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "setMembershipExpression"
    // BaseElement.g:151:1: setMembershipExpression : LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) ) ;
    public final Event_BaseElement.setMembershipExpression_return setMembershipExpression() throws RecognitionException {
        Event_BaseElement.setMembershipExpression_return retval = new Event_BaseElement.setMembershipExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE54=null;
        Token char_literal55=null;
        Token RBRACE56=null;
        List list_exp=null;
        RuleReturnScope exp = null;
        EventASTNode LBRACE54_tree=null;
        EventASTNode char_literal55_tree=null;
        EventASTNode RBRACE56_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:152:2: ( LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) ) )
            // BaseElement.g:152:4: LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE
            {
            LBRACE54=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_setMembershipExpression1069); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE54);

            // BaseElement.g:152:11: (exp+= expression ( ',' exp+= expression )* )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==Identifier||(LA22_0>=EQ && LA22_0<=LBRACE)||(LA22_0>=PLUS && LA22_0<=MINUS)||LA22_0==POUND||(LA22_0>=FloatingPointLiteral && LA22_0<=FALSE)||LA22_0==LPAREN||LA22_0==LBRACK||(LA22_0>=184 && LA22_0<=232)||(LA22_0>=248 && LA22_0<=251)) ) {
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

                        if ( (LA21_0==236) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // BaseElement.g:152:29: ',' exp+= expression
                    	    {
                    	    char_literal55=(Token)match(input,236,FOLLOW_236_in_setMembershipExpression1077); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_236.add(char_literal55);

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

            RBRACE56=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_setMembershipExpression1087); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE56);



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
            root_0 = (EventASTNode)adaptor.nil();
            // 153:3: -> ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) )
            {
                // BaseElement.g:153:6: ^( SET_MEMBERSHIP_EXPRESSION ^( EXPRESSIONS ( $exp)* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SET_MEMBERSHIP_EXPRESSION, "SET_MEMBERSHIP_EXPRESSION"), root_1);

                // BaseElement.g:153:34: ^( EXPRESSIONS ( $exp)* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpression"
    // BaseElement.g:156:1: relationalExpression : lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )* ;
    public final Event_BaseElement.relationalExpression_return relationalExpression() throws RecognitionException {
        Event_BaseElement.relationalExpression_return retval = new Event_BaseElement.relationalExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set57=null;
        Token INSTANCE_OF58=null;
        Event_BaseElement.additiveExpression_return lhs = null;

        Event_BaseElement.additiveExpression_return rhs = null;

        Event_BaseElement.type_return type59 = null;


        EventASTNode set57_tree=null;
        EventASTNode INSTANCE_OF58_tree=null;

        try {
            // BaseElement.g:157:2: (lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )* )
            // BaseElement.g:157:4: lhs= additiveExpression ( ( ( LT | GT | LE | GE ) rhs= additiveExpression | INSTANCE_OF type ) )*
            {
            root_0 = (EventASTNode)adaptor.nil();

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
            	            set57=(Token)input.LT(1);
            	            set57=(Token)input.LT(1);
            	            if ( (input.LA(1)>=LT && input.LA(1)<=GE) ) {
            	                input.consume();
            	                if ( state.backtracking==0 ) root_0 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(set57), root_0);
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
            	            INSTANCE_OF58=(Token)match(input,INSTANCE_OF,FOLLOW_INSTANCE_OF_in_relationalExpression1149); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            INSTANCE_OF58_tree = (EventASTNode)adaptor.create(INSTANCE_OF58);
            	            root_0 = (EventASTNode)adaptor.becomeRoot(INSTANCE_OF58_tree, root_0);
            	            }
            	            pushFollow(FOLLOW_type_in_relationalExpression1152);
            	            type59=type();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, type59.getTree());

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpression"
    // BaseElement.g:165:1: additiveExpression : multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )* ;
    public final Event_BaseElement.additiveExpression_return additiveExpression() throws RecognitionException {
        Event_BaseElement.additiveExpression_return retval = new Event_BaseElement.additiveExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set61=null;
        Event_BaseElement.multiplicativeExpression_return rhs = null;

        Event_BaseElement.multiplicativeExpression_return multiplicativeExpression60 = null;


        EventASTNode set61_tree=null;

        try {
            // BaseElement.g:166:2: ( multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )* )
            // BaseElement.g:166:4: multiplicativeExpression ( ( PLUS | MINUS ) rhs= multiplicativeExpression )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1172);
            multiplicativeExpression60=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression60.getTree());
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
            	    set61=(Token)input.LT(1);
            	    set61=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) root_0 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(set61), root_0);
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpression"
    // BaseElement.g:172:1: multiplicativeExpression : unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )* ;
    public final Event_BaseElement.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        Event_BaseElement.multiplicativeExpression_return retval = new Event_BaseElement.multiplicativeExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set63=null;
        Event_BaseElement.unaryExpression_return rhs = null;

        Event_BaseElement.unaryExpression_return unaryExpression62 = null;


        EventASTNode set63_tree=null;

        try {
            // BaseElement.g:173:2: ( unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )* )
            // BaseElement.g:173:4: unaryExpression ( ( MULT | DIVIDE | MOD ) rhs= unaryExpression )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1207);
            unaryExpression62=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression62.getTree());
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
            	    set63=(Token)input.LT(1);
            	    set63=(Token)input.LT(1);
            	    if ( (input.LA(1)>=MULT && input.LA(1)<=MOD) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) root_0 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(set63), root_0);
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpression"
    // BaseElement.g:179:1: unaryExpression : ( (op= PLUS | op= MINUS | op= POUND ) unaryExpression -> ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) ) | primaryExpression );
    public final Event_BaseElement.unaryExpression_return unaryExpression() throws RecognitionException {
        Event_BaseElement.unaryExpression_return retval = new Event_BaseElement.unaryExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token op=null;
        Event_BaseElement.unaryExpression_return unaryExpression64 = null;

        Event_BaseElement.primaryExpression_return primaryExpression65 = null;


        EventASTNode op_tree=null;
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
            else if ( (LA28_0==Identifier||(LA28_0>=FloatingPointLiteral && LA28_0<=FALSE)||LA28_0==LPAREN||(LA28_0>=184 && LA28_0<=232)||(LA28_0>=248 && LA28_0<=251)) ) {
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
                    unaryExpression64=unaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryExpression.add(unaryExpression64.getTree());


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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 181:3: -> ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) )
                    {
                        // BaseElement.g:181:6: ^( UNARY_EXPRESSION_NODE ^( EXPRESSION unaryExpression ) ^( OPERATOR $op) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(UNARY_EXPRESSION_NODE, "UNARY_EXPRESSION_NODE"), root_1);

                        // BaseElement.g:181:30: ^( EXPRESSION unaryExpression )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        adaptor.addChild(root_2, stream_unaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:181:60: ^( OPERATOR $op)
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_primaryExpression_in_unaryExpression1291);
                    primaryExpression65=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primaryExpression65.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryExpression"
    // BaseElement.g:185:1: primaryExpression : p= primaryPrefix (s+= primarySuffix[(RulesASTNode) p.getTree()] )* -> ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) ) ;
    public final Event_BaseElement.primaryExpression_return primaryExpression() throws RecognitionException {
        Event_BaseElement.primaryExpression_return retval = new Event_BaseElement.primaryExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        List list_s=null;
        Event_BaseElement.primaryPrefix_return p = null;

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
            // elements: p, s
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
            root_0 = (EventASTNode)adaptor.nil();
            // 187:2: -> ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) )
            {
                // BaseElement.g:187:5: ^( PRIMARY_EXPRESSION ^( PREFIX $p) ^( SUFFIXES ( $s)* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PRIMARY_EXPRESSION, "PRIMARY_EXPRESSION"), root_1);

                // BaseElement.g:187:26: ^( PREFIX $p)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PREFIX, "PREFIX"), root_2);

                adaptor.addChild(root_2, stream_p.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:187:39: ^( SUFFIXES ( $s)* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SUFFIXES, "SUFFIXES"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryPrefix"
    // BaseElement.g:190:1: primaryPrefix : ( literal | '(' expression ')' | ( type LBRACE )=> arrayLiteral | ( arrayAllocator )=> arrayAllocator | ( methodName '(' )=>m= methodName argumentsSuffix -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ) | expressionName );
    public final Event_BaseElement.primaryPrefix_return primaryPrefix() throws RecognitionException {
        Event_BaseElement.primaryPrefix_return retval = new Event_BaseElement.primaryPrefix_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal67=null;
        Token char_literal69=null;
        Event_BaseElement.methodName_return m = null;

        Event_BaseElement.literal_return literal66 = null;

        Event_BaseElement.expression_return expression68 = null;

        Event_BaseElement.arrayLiteral_return arrayLiteral70 = null;

        Event_BaseElement.arrayAllocator_return arrayAllocator71 = null;

        Event_BaseElement.argumentsSuffix_return argumentsSuffix72 = null;

        Event_BaseElement.expressionName_return expressionName73 = null;


        EventASTNode char_literal67_tree=null;
        EventASTNode char_literal69_tree=null;
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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_primaryPrefix1347);
                    literal66=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal66.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:192:4: '(' expression ')'
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    char_literal67=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_primaryPrefix1352); if (state.failed) return retval;
                    pushFollow(FOLLOW_expression_in_primaryPrefix1355);
                    expression68=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression68.getTree());
                    char_literal69=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_primaryPrefix1357); if (state.failed) return retval;

                    }
                    break;
                case 3 :
                    // BaseElement.g:193:4: ( type LBRACE )=> arrayLiteral
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayLiteral_in_primaryPrefix1369);
                    arrayLiteral70=arrayLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayLiteral70.getTree());

                    }
                    break;
                case 4 :
                    // BaseElement.g:194:4: ( arrayAllocator )=> arrayAllocator
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayAllocator_in_primaryPrefix1378);
                    arrayAllocator71=arrayAllocator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAllocator71.getTree());

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
                    argumentsSuffix72=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix72.getTree());
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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 196:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) )
                    {
                        // BaseElement.g:196:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:196:20: ^( NAME $m)
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:196:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARGS, "ARGS"), root_2);

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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_expressionName_in_primaryPrefix1422);
                    expressionName73=expressionName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionName73.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primarySuffix"
    // BaseElement.g:200:1: primarySuffix[RulesASTNode type] : ( arrayAccessSuffix | fieldAccessSuffix );
    public final Event_BaseElement.primarySuffix_return primarySuffix(RulesASTNode type) throws RecognitionException {
        Event_BaseElement.primarySuffix_return retval = new Event_BaseElement.primarySuffix_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.arrayAccessSuffix_return arrayAccessSuffix74 = null;

        Event_BaseElement.fieldAccessSuffix_return fieldAccessSuffix75 = null;



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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_arrayAccessSuffix_in_primarySuffix1435);
                    arrayAccessSuffix74=arrayAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, arrayAccessSuffix74.getTree());
                    if ( state.backtracking==0 ) {
                       markAsArray(type); 
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:201:47: fieldAccessSuffix
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_fieldAccessSuffix_in_primarySuffix1441);
                    fieldAccessSuffix75=fieldAccessSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fieldAccessSuffix75.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAccessSuffix"
    // BaseElement.g:204:1: arrayAccessSuffix : '[' expression ']' -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) ) ;
    public final Event_BaseElement.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException {
        Event_BaseElement.arrayAccessSuffix_return retval = new Event_BaseElement.arrayAccessSuffix_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal76=null;
        Token char_literal78=null;
        Event_BaseElement.expression_return expression77 = null;


        EventASTNode char_literal76_tree=null;
        EventASTNode char_literal78_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:205:2: ( '[' expression ']' -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) ) )
            // BaseElement.g:205:4: '[' expression ']'
            {
            char_literal76=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_arrayAccessSuffix1453); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACK.add(char_literal76);

            if ( state.backtracking==0 ) {
               arrayDepth++; 
            }
            pushFollow(FOLLOW_expression_in_arrayAccessSuffix1457);
            expression77=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression77.getTree());
            char_literal78=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_arrayAccessSuffix1459); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACK.add(char_literal78);

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

            root_0 = (EventASTNode)adaptor.nil();
            // 206:3: -> ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) )
            {
                // BaseElement.g:206:6: ^( ARRAY_ACCESS_SUFFIX ^( EXPRESSION expression ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARRAY_ACCESS_SUFFIX, "ARRAY_ACCESS_SUFFIX"), root_1);

                // BaseElement.g:206:28: ^( EXPRESSION expression )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldAccessSuffix"
    // BaseElement.g:209:1: fieldAccessSuffix : ( DOT | ANNOTATE ) id= identifier ;
    public final Event_BaseElement.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException {
        Event_BaseElement.fieldAccessSuffix_return retval = new Event_BaseElement.fieldAccessSuffix_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token DOT79=null;
        Token ANNOTATE80=null;
        Event_BaseElement.identifier_return id = null;


        EventASTNode DOT79_tree=null;
        EventASTNode ANNOTATE80_tree=null;

         
        boolean attributeRef = false; 

        try {
            // BaseElement.g:213:2: ( ( DOT | ANNOTATE ) id= identifier )
            // BaseElement.g:213:4: ( DOT | ANNOTATE ) id= identifier
            {
            root_0 = (EventASTNode)adaptor.nil();

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
                    DOT79=(Token)match(input,DOT,FOLLOW_DOT_in_fieldAccessSuffix1493); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // BaseElement.g:213:12: ANNOTATE
                    {
                    ANNOTATE80=(Token)match(input,ANNOTATE,FOLLOW_ANNOTATE_in_fieldAccessSuffix1498); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       attributeRef = true; 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_identifier_in_fieldAccessSuffix1506);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
            if ( state.backtracking==0 ) {
               pushFieldAccessReference((RulesASTNode)id.getTree(), attributeRef); 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentsSuffix"
    // BaseElement.g:216:1: argumentsSuffix : '(' ( argumentList )? ')' ;
    public final Event_BaseElement.argumentsSuffix_return argumentsSuffix() throws RecognitionException {
        Event_BaseElement.argumentsSuffix_return retval = new Event_BaseElement.argumentsSuffix_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal81=null;
        Token char_literal83=null;
        Event_BaseElement.argumentList_return argumentList82 = null;


        EventASTNode char_literal81_tree=null;
        EventASTNode char_literal83_tree=null;

        try {
            // BaseElement.g:217:2: ( '(' ( argumentList )? ')' )
            // BaseElement.g:217:4: '(' ( argumentList )? ')'
            {
            root_0 = (EventASTNode)adaptor.nil();

            char_literal81=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_argumentsSuffix1521); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               arrayDepth++; 
            }
            // BaseElement.g:217:27: ( argumentList )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==Identifier||(LA33_0>=EQ && LA33_0<=LBRACE)||(LA33_0>=PLUS && LA33_0<=MINUS)||LA33_0==POUND||(LA33_0>=FloatingPointLiteral && LA33_0<=FALSE)||LA33_0==LPAREN||LA33_0==LBRACK||(LA33_0>=184 && LA33_0<=233)||(LA33_0>=248 && LA33_0<=251)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // BaseElement.g:217:27: argumentList
                    {
                    pushFollow(FOLLOW_argumentList_in_argumentsSuffix1526);
                    argumentList82=argumentList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argumentList82.getTree());

                    }
                    break;

            }

            char_literal83=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_argumentsSuffix1529); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               arrayDepth--; 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argumentList"
    // BaseElement.g:220:1: argumentList : ( expression ( ',' expression )* | xsltLiteral );
    public final Event_BaseElement.argumentList_return argumentList() throws RecognitionException {
        Event_BaseElement.argumentList_return retval = new Event_BaseElement.argumentList_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal85=null;
        Event_BaseElement.expression_return expression84 = null;

        Event_BaseElement.expression_return expression86 = null;

        Event_BaseElement.xsltLiteral_return xsltLiteral87 = null;


        EventASTNode char_literal85_tree=null;

        try {
            // BaseElement.g:221:2: ( expression ( ',' expression )* | xsltLiteral )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==Identifier||(LA35_0>=EQ && LA35_0<=LBRACE)||(LA35_0>=PLUS && LA35_0<=MINUS)||LA35_0==POUND||(LA35_0>=FloatingPointLiteral && LA35_0<=FALSE)||LA35_0==LPAREN||LA35_0==LBRACK||(LA35_0>=184 && LA35_0<=232)||(LA35_0>=248 && LA35_0<=251)) ) {
                alt35=1;
            }
            else if ( (LA35_0==233) ) {
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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_expression_in_argumentList1545);
                    expression84=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression84.getTree());
                    // BaseElement.g:221:15: ( ',' expression )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==236) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // BaseElement.g:221:16: ',' expression
                    	    {
                    	    char_literal85=(Token)match(input,236,FOLLOW_236_in_argumentList1548); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_expression_in_argumentList1551);
                    	    expression86=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression86.getTree());

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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_xsltLiteral_in_argumentList1558);
                    xsltLiteral87=xsltLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, xsltLiteral87.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // BaseElement.g:225:1: literal : ( integerLiteral | FloatingPointLiteral | StringLiteral | booleanLiteral | NullLiteral );
    public final Event_BaseElement.literal_return literal() throws RecognitionException {
        Event_BaseElement.literal_return retval = new Event_BaseElement.literal_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token FloatingPointLiteral89=null;
        Token StringLiteral90=null;
        Token NullLiteral92=null;
        Event_BaseElement.integerLiteral_return integerLiteral88 = null;

        Event_BaseElement.booleanLiteral_return booleanLiteral91 = null;


        EventASTNode FloatingPointLiteral89_tree=null;
        EventASTNode StringLiteral90_tree=null;
        EventASTNode NullLiteral92_tree=null;

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
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_integerLiteral_in_literal1570);
                    integerLiteral88=integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, integerLiteral88.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:227:4: FloatingPointLiteral
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    FloatingPointLiteral89=(Token)match(input,FloatingPointLiteral,FOLLOW_FloatingPointLiteral_in_literal1575); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FloatingPointLiteral89_tree = (EventASTNode)adaptor.create(FloatingPointLiteral89);
                    adaptor.addChild(root_0, FloatingPointLiteral89_tree);
                    }

                    }
                    break;
                case 3 :
                    // BaseElement.g:228:4: StringLiteral
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    StringLiteral90=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_literal1580); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    StringLiteral90_tree = (EventASTNode)adaptor.create(StringLiteral90);
                    adaptor.addChild(root_0, StringLiteral90_tree);
                    }

                    }
                    break;
                case 4 :
                    // BaseElement.g:229:4: booleanLiteral
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_booleanLiteral_in_literal1585);
                    booleanLiteral91=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booleanLiteral91.getTree());

                    }
                    break;
                case 5 :
                    // BaseElement.g:230:4: NullLiteral
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    NullLiteral92=(Token)match(input,NullLiteral,FOLLOW_NullLiteral_in_literal1590); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NullLiteral92_tree = (EventASTNode)adaptor.create(NullLiteral92);
                    adaptor.addChild(root_0, NullLiteral92_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "integerLiteral"
    // BaseElement.g:233:1: integerLiteral : ( DecimalLiteral | HexLiteral );
    public final Event_BaseElement.integerLiteral_return integerLiteral() throws RecognitionException {
        Event_BaseElement.integerLiteral_return retval = new Event_BaseElement.integerLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set93=null;

        EventASTNode set93_tree=null;

        try {
            // BaseElement.g:234:2: ( DecimalLiteral | HexLiteral )
            // BaseElement.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set93=(Token)input.LT(1);
            if ( (input.LA(1)>=DecimalLiteral && input.LA(1)<=HexLiteral) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set93));
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanLiteral"
    // BaseElement.g:239:1: booleanLiteral : ( TRUE | FALSE );
    public final Event_BaseElement.booleanLiteral_return booleanLiteral() throws RecognitionException {
        Event_BaseElement.booleanLiteral_return retval = new Event_BaseElement.booleanLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set94=null;

        EventASTNode set94_tree=null;

        try {
            // BaseElement.g:240:2: ( TRUE | FALSE )
            // BaseElement.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set94=(Token)input.LT(1);
            if ( (input.LA(1)>=TRUE && input.LA(1)<=FALSE) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set94));
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyStatement"
    // BaseElement.g:243:1: emptyStatement : SEMICOLON ;
    public final Event_BaseElement.emptyStatement_return emptyStatement() throws RecognitionException {
        Event_BaseElement.emptyStatement_return retval = new Event_BaseElement.emptyStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token SEMICOLON95=null;

        EventASTNode SEMICOLON95_tree=null;

        try {
            // BaseElement.g:244:2: ( SEMICOLON )
            // BaseElement.g:244:4: SEMICOLON
            {
            root_0 = (EventASTNode)adaptor.nil();

            SEMICOLON95=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_emptyStatement1635); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON95_tree = (EventASTNode)adaptor.create(SEMICOLON95);
            adaptor.addChild(root_0, SEMICOLON95_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "emptyBodyStatement"
    // BaseElement.g:247:1: emptyBodyStatement : WS ;
    public final Event_BaseElement.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException {
        Event_BaseElement.emptyBodyStatement_return retval = new Event_BaseElement.emptyBodyStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token WS96=null;

        EventASTNode WS96_tree=null;

        try {
            // BaseElement.g:248:2: ( WS )
            // BaseElement.g:248:4: WS
            {
            root_0 = (EventASTNode)adaptor.nil();

            WS96=(Token)match(input,WS,FOLLOW_WS_in_emptyBodyStatement1647); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WS96_tree = (EventASTNode)adaptor.create(WS96);
            adaptor.addChild(root_0, WS96_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpression"
    // BaseElement.g:259:1: statementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? SEMICOLON -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) SEMICOLON -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );
    public final Event_BaseElement.statementExpression_return statementExpression() throws RecognitionException {
        Event_BaseElement.statementExpression_return retval = new Event_BaseElement.statementExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token op=null;
        Token SEMICOLON100=null;
        Token SEMICOLON103=null;
        Event_BaseElement.methodName_return m = null;

        Event_BaseElement.assignmentOp_return assignOp = null;

        Event_BaseElement.argumentsSuffix_return argumentsSuffix97 = null;

        Event_BaseElement.primarySuffix_return primarySuffix98 = null;

        Event_BaseElement.expression_return expression99 = null;

        Event_BaseElement.primaryExpression_return primaryExpression101 = null;

        Event_BaseElement.expression_return expression102 = null;


        EventASTNode op_tree=null;
        EventASTNode SEMICOLON100_tree=null;
        EventASTNode SEMICOLON103_tree=null;
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
                    argumentsSuffix97=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix97.getTree());
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
                            	    primarySuffix98=primarySuffix((RulesASTNode) m.getTree());

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_primarySuffix.add(primarySuffix98.getTree());

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
                                    expression99=expression();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_expression.add(expression99.getTree());

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

                    SEMICOLON100=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statementExpression1710); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON100);



                    // AST REWRITE
                    // elements: argumentsSuffix, op, m, primarySuffix, expression, assignOp
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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 262:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:262:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:262:20: ^( NAME $m)
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:262:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARGS, "ARGS"), root_2);

                        // BaseElement.g:262:38: ( argumentsSuffix )?
                        if ( stream_argumentsSuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_argumentsSuffix.nextTree());

                        }
                        stream_argumentsSuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:4: ^( SUFFIX ( primarySuffix )* )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SUFFIX, "SUFFIX"), root_2);

                        // BaseElement.g:263:13: ( primarySuffix )*
                        while ( stream_primarySuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_primarySuffix.nextTree());

                        }
                        stream_primarySuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:29: ^( EXPRESSION ( expression )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        // BaseElement.g:263:42: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:263:55: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

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
                    primaryExpression101=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primaryExpression.add(primaryExpression101.getTree());
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
                            expression102=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression102.getTree());

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

                    SEMICOLON103=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_statementExpression1790); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON103);



                    // AST REWRITE
                    // elements: assignOp, op, expression, primaryExpression
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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 265:3: -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:265:6: ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PRIMARY_ASSIGNMENT_EXPRESSION, "PRIMARY_ASSIGNMENT_EXPRESSION"), root_1);

                        // BaseElement.g:265:38: ^( LHS primaryExpression )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LHS, "LHS"), root_2);

                        adaptor.addChild(root_2, stream_primaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:265:63: ^( RHS ( expression )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RHS, "RHS"), root_2);

                        // BaseElement.g:265:69: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:265:82: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "listStatementExpression"
    // BaseElement.g:268:1: listStatementExpression : ( ( methodName '(' )=>m= methodName argumentsSuffix ( ( primarySuffix[(RulesASTNode) m.getTree()] )* (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) )? -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) | primaryExpression (assignOp= assignmentOp expression | (op= INCR | op= DECR ) ) -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) ) );
    public final Event_BaseElement.listStatementExpression_return listStatementExpression() throws RecognitionException {
        Event_BaseElement.listStatementExpression_return retval = new Event_BaseElement.listStatementExpression_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token op=null;
        Event_BaseElement.methodName_return m = null;

        Event_BaseElement.assignmentOp_return assignOp = null;

        Event_BaseElement.argumentsSuffix_return argumentsSuffix104 = null;

        Event_BaseElement.primarySuffix_return primarySuffix105 = null;

        Event_BaseElement.expression_return expression106 = null;

        Event_BaseElement.primaryExpression_return primaryExpression107 = null;

        Event_BaseElement.expression_return expression108 = null;


        EventASTNode op_tree=null;
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
                    argumentsSuffix104=argumentsSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_argumentsSuffix.add(argumentsSuffix104.getTree());
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
                            	    primarySuffix105=primarySuffix((RulesASTNode) m.getTree());

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_primarySuffix.add(primarySuffix105.getTree());

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
                                    expression106=expression();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) stream_expression.add(expression106.getTree());

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
                    // elements: assignOp, m, argumentsSuffix, op, primarySuffix, expression
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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 271:3: -> ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:271:6: ^( METHOD_CALL ^( NAME $m) ^( ARGS ( argumentsSuffix )? ) ^( SUFFIX ( primarySuffix )* ) ^( EXPRESSION ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(METHOD_CALL, "METHOD_CALL"), root_1);

                        // BaseElement.g:271:20: ^( NAME $m)
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_m.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:271:31: ^( ARGS ( argumentsSuffix )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARGS, "ARGS"), root_2);

                        // BaseElement.g:271:38: ( argumentsSuffix )?
                        if ( stream_argumentsSuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_argumentsSuffix.nextTree());

                        }
                        stream_argumentsSuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:4: ^( SUFFIX ( primarySuffix )* )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SUFFIX, "SUFFIX"), root_2);

                        // BaseElement.g:272:13: ( primarySuffix )*
                        while ( stream_primarySuffix.hasNext() ) {
                            adaptor.addChild(root_2, stream_primarySuffix.nextTree());

                        }
                        stream_primarySuffix.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:29: ^( EXPRESSION ( expression )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                        // BaseElement.g:272:42: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:272:55: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

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
                    primaryExpression107=primaryExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_primaryExpression.add(primaryExpression107.getTree());
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
                            expression108=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression108.getTree());

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

                    root_0 = (EventASTNode)adaptor.nil();
                    // 274:3: -> ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                    {
                        // BaseElement.g:274:6: ^( PRIMARY_ASSIGNMENT_EXPRESSION ^( LHS primaryExpression ) ^( RHS ( expression )? ) ^( OPERATOR ( $op)? ( $assignOp)? ) )
                        {
                        EventASTNode root_1 = (EventASTNode)adaptor.nil();
                        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PRIMARY_ASSIGNMENT_EXPRESSION, "PRIMARY_ASSIGNMENT_EXPRESSION"), root_1);

                        // BaseElement.g:274:38: ^( LHS primaryExpression )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LHS, "LHS"), root_2);

                        adaptor.addChild(root_2, stream_primaryExpression.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:274:63: ^( RHS ( expression )? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RHS, "RHS"), root_2);

                        // BaseElement.g:274:69: ( expression )?
                        if ( stream_expression.hasNext() ) {
                            adaptor.addChild(root_2, stream_expression.nextTree());

                        }
                        stream_expression.reset();

                        adaptor.addChild(root_1, root_2);
                        }
                        // BaseElement.g:274:82: ^( OPERATOR ( $op)? ( $assignOp)? )
                        {
                        EventASTNode root_2 = (EventASTNode)adaptor.nil();
                        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(OPERATOR, "OPERATOR"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignmentOp"
    // BaseElement.g:277:1: assignmentOp : ( ASSIGN | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL );
    public final Event_BaseElement.assignmentOp_return assignmentOp() throws RecognitionException {
        Event_BaseElement.assignmentOp_return retval = new Event_BaseElement.assignmentOp_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set109=null;

        EventASTNode set109_tree=null;

        try {
            // BaseElement.g:278:2: ( ASSIGN | PLUS_EQUAL | MINUS_EQUAL | MULT_EQUAL | DIV_EQUAL | MOD_EQUAL )
            // BaseElement.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set109=(Token)input.LT(1);
            if ( (input.LA(1)>=ASSIGN && input.LA(1)<=MOD_EQUAL) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set109));
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "returnStatement"
    // BaseElement.g:281:1: returnStatement : 'return' ( expression SEMICOLON | SEMICOLON ) -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) ) ;
    public final Event_BaseElement.returnStatement_return returnStatement() throws RecognitionException {
        Event_BaseElement.returnStatement_return retval = new Event_BaseElement.returnStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal110=null;
        Token SEMICOLON112=null;
        Token SEMICOLON113=null;
        Event_BaseElement.expression_return expression111 = null;


        EventASTNode string_literal110_tree=null;
        EventASTNode SEMICOLON112_tree=null;
        EventASTNode SEMICOLON113_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_237=new RewriteRuleTokenStream(adaptor,"token 237");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:282:2: ( 'return' ( expression SEMICOLON | SEMICOLON ) -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) ) )
            // BaseElement.g:282:4: 'return' ( expression SEMICOLON | SEMICOLON )
            {
            string_literal110=(Token)match(input,237,FOLLOW_237_in_returnStatement2032); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_237.add(string_literal110);

            // BaseElement.g:282:13: ( expression SEMICOLON | SEMICOLON )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==Identifier||(LA51_0>=EQ && LA51_0<=LBRACE)||(LA51_0>=PLUS && LA51_0<=MINUS)||LA51_0==POUND||(LA51_0>=FloatingPointLiteral && LA51_0<=FALSE)||LA51_0==LPAREN||LA51_0==LBRACK||(LA51_0>=184 && LA51_0<=232)||(LA51_0>=248 && LA51_0<=251)) ) {
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
                    expression111=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression111.getTree());
                    SEMICOLON112=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2037); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON112);


                    }
                    break;
                case 2 :
                    // BaseElement.g:282:37: SEMICOLON
                    {
                    SEMICOLON113=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_returnStatement2041); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON113);


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

            root_0 = (EventASTNode)adaptor.nil();
            // 283:3: -> ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) )
            {
                // BaseElement.g:283:6: ^( RETURN_STATEMENT ^( EXPRESSION ( expression )? ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RETURN_STATEMENT, "RETURN_STATEMENT"), root_1);

                // BaseElement.g:283:25: ^( EXPRESSION ( expression )? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "breakStatement"
    // BaseElement.g:286:1: breakStatement : 'break' SEMICOLON -> ^( BREAK_STATEMENT 'break' ) ;
    public final Event_BaseElement.breakStatement_return breakStatement() throws RecognitionException {
        Event_BaseElement.breakStatement_return retval = new Event_BaseElement.breakStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal114=null;
        Token SEMICOLON115=null;

        EventASTNode string_literal114_tree=null;
        EventASTNode SEMICOLON115_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_238=new RewriteRuleTokenStream(adaptor,"token 238");

        try {
            // BaseElement.g:287:2: ( 'break' SEMICOLON -> ^( BREAK_STATEMENT 'break' ) )
            // BaseElement.g:287:4: 'break' SEMICOLON
            {
            string_literal114=(Token)match(input,238,FOLLOW_238_in_breakStatement2069); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_238.add(string_literal114);

            SEMICOLON115=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_breakStatement2071); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON115);



            // AST REWRITE
            // elements: 238
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 288:3: -> ^( BREAK_STATEMENT 'break' )
            {
                // BaseElement.g:288:6: ^( BREAK_STATEMENT 'break' )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BREAK_STATEMENT, "BREAK_STATEMENT"), root_1);

                adaptor.addChild(root_1, stream_238.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continueStatement"
    // BaseElement.g:291:1: continueStatement : 'continue' SEMICOLON -> ^( CONTINUE_STATEMENT ) ;
    public final Event_BaseElement.continueStatement_return continueStatement() throws RecognitionException {
        Event_BaseElement.continueStatement_return retval = new Event_BaseElement.continueStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal116=null;
        Token SEMICOLON117=null;

        EventASTNode string_literal116_tree=null;
        EventASTNode SEMICOLON117_tree=null;
        RewriteRuleTokenStream stream_239=new RewriteRuleTokenStream(adaptor,"token 239");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");

        try {
            // BaseElement.g:292:2: ( 'continue' SEMICOLON -> ^( CONTINUE_STATEMENT ) )
            // BaseElement.g:292:4: 'continue' SEMICOLON
            {
            string_literal116=(Token)match(input,239,FOLLOW_239_in_continueStatement2093); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_239.add(string_literal116);

            SEMICOLON117=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_continueStatement2095); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON117);



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

            root_0 = (EventASTNode)adaptor.nil();
            // 293:3: -> ^( CONTINUE_STATEMENT )
            {
                // BaseElement.g:293:6: ^( CONTINUE_STATEMENT )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(CONTINUE_STATEMENT, "CONTINUE_STATEMENT"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "throwStatement"
    // BaseElement.g:296:1: throwStatement : 'throw' expression SEMICOLON -> ^( THROW_STATEMENT ^( EXPRESSION expression ) ) ;
    public final Event_BaseElement.throwStatement_return throwStatement() throws RecognitionException {
        Event_BaseElement.throwStatement_return retval = new Event_BaseElement.throwStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal118=null;
        Token SEMICOLON120=null;
        Event_BaseElement.expression_return expression119 = null;


        EventASTNode string_literal118_tree=null;
        EventASTNode SEMICOLON120_tree=null;
        RewriteRuleTokenStream stream_240=new RewriteRuleTokenStream(adaptor,"token 240");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:297:2: ( 'throw' expression SEMICOLON -> ^( THROW_STATEMENT ^( EXPRESSION expression ) ) )
            // BaseElement.g:297:4: 'throw' expression SEMICOLON
            {
            string_literal118=(Token)match(input,240,FOLLOW_240_in_throwStatement2115); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_240.add(string_literal118);

            pushFollow(FOLLOW_expression_in_throwStatement2117);
            expression119=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression119.getTree());
            SEMICOLON120=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_throwStatement2119); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON120);



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

            root_0 = (EventASTNode)adaptor.nil();
            // 298:3: -> ^( THROW_STATEMENT ^( EXPRESSION expression ) )
            {
                // BaseElement.g:298:6: ^( THROW_STATEMENT ^( EXPRESSION expression ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(THROW_STATEMENT, "THROW_STATEMENT"), root_1);

                // BaseElement.g:298:24: ^( EXPRESSION expression )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statementExpressionList"
    // BaseElement.g:308:1: statementExpressionList : listStatementExpression ( ',' listStatementExpression )* ;
    public final Event_BaseElement.statementExpressionList_return statementExpressionList() throws RecognitionException {
        Event_BaseElement.statementExpressionList_return retval = new Event_BaseElement.statementExpressionList_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal122=null;
        Event_BaseElement.listStatementExpression_return listStatementExpression121 = null;

        Event_BaseElement.listStatementExpression_return listStatementExpression123 = null;


        EventASTNode char_literal122_tree=null;

        try {
            // BaseElement.g:309:2: ( listStatementExpression ( ',' listStatementExpression )* )
            // BaseElement.g:309:4: listStatementExpression ( ',' listStatementExpression )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_listStatementExpression_in_statementExpressionList2148);
            listStatementExpression121=listStatementExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, listStatementExpression121.getTree());
            // BaseElement.g:309:28: ( ',' listStatementExpression )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==236) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // BaseElement.g:309:29: ',' listStatementExpression
            	    {
            	    char_literal122=(Token)match(input,236,FOLLOW_236_in_statementExpressionList2151); if (state.failed) return retval;
            	    pushFollow(FOLLOW_listStatementExpression_in_statementExpressionList2154);
            	    listStatementExpression123=listStatementExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, listStatementExpression123.getTree());

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ifRule"
    // BaseElement.g:312:1: ifRule : 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )? -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) ) ;
    public final Event_BaseElement.ifRule_return ifRule() throws RecognitionException {
        Event_BaseElement.ifRule_return retval = new Event_BaseElement.ifRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal124=null;
        Token char_literal125=null;
        Token char_literal127=null;
        Token string_literal128=null;
        Event_BaseElement.statement_return ifst = null;

        Event_BaseElement.statement_return elst = null;

        Event_BaseElement.expression_return expression126 = null;


        EventASTNode string_literal124_tree=null;
        EventASTNode char_literal125_tree=null;
        EventASTNode char_literal127_tree=null;
        EventASTNode string_literal128_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_241=new RewriteRuleTokenStream(adaptor,"token 241");
        RewriteRuleTokenStream stream_242=new RewriteRuleTokenStream(adaptor,"token 242");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // BaseElement.g:312:8: ( 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )? -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) ) )
            // BaseElement.g:312:10: 'if' '(' expression ')' ifst= statement ( 'else' elst= statement )?
            {
            string_literal124=(Token)match(input,241,FOLLOW_241_in_ifRule2167); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_241.add(string_literal124);

            char_literal125=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_ifRule2169); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal125);

            pushFollow(FOLLOW_expression_in_ifRule2171);
            expression126=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression126.getTree());
            char_literal127=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_ifRule2173); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal127);

            if ( state.backtracking==0 ) {
               pushScope(IF_SCOPE); 
            }
            pushFollow(FOLLOW_statement_in_ifRule2179);
            ifst=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(ifst.getTree());
            if ( state.backtracking==0 ) {
               popScope(); 
            }
            // BaseElement.g:313:2: ( 'else' elst= statement )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==242) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // BaseElement.g:313:3: 'else' elst= statement
                    {
                    string_literal128=(Token)match(input,242,FOLLOW_242_in_ifRule2185); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_242.add(string_literal128);

                    if ( state.backtracking==0 ) {
                       pushScope(IF_SCOPE); 
                    }
                    pushFollow(FOLLOW_statement_in_ifRule2191);
                    elst=statement();

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

            root_0 = (EventASTNode)adaptor.nil();
            // 314:2: -> ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) )
            {
                // BaseElement.g:314:5: ^( IF_RULE ^( EXPRESSION expression ) ^( STATEMENT $ifst) ^( ELSE_STATEMENT ( $elst)? ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(IF_RULE, "IF_RULE"), root_1);

                // BaseElement.g:314:15: ^( EXPRESSION expression )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:314:40: ^( STATEMENT $ifst)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENT, "STATEMENT"), root_2);

                adaptor.addChild(root_2, stream_ifst.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:314:59: ^( ELSE_STATEMENT ( $elst)? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ELSE_STATEMENT, "ELSE_STATEMENT"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whileRule"
    // BaseElement.g:317:1: whileRule : 'while' '(' expression ')' statement -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) ) ;
    public final Event_BaseElement.whileRule_return whileRule() throws RecognitionException {
        Event_BaseElement.whileRule_return retval = new Event_BaseElement.whileRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal129=null;
        Token char_literal130=null;
        Token char_literal132=null;
        Event_BaseElement.expression_return expression131 = null;

        Event_BaseElement.statement_return statement133 = null;


        EventASTNode string_literal129_tree=null;
        EventASTNode char_literal130_tree=null;
        EventASTNode char_literal132_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_243=new RewriteRuleTokenStream(adaptor,"token 243");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // BaseElement.g:317:11: ( 'while' '(' expression ')' statement -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) ) )
            // BaseElement.g:317:13: 'while' '(' expression ')' statement
            {
            string_literal129=(Token)match(input,243,FOLLOW_243_in_whileRule2234); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_243.add(string_literal129);

            char_literal130=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_whileRule2236); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal130);

            pushFollow(FOLLOW_expression_in_whileRule2238);
            expression131=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression131.getTree());
            char_literal132=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_whileRule2240); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal132);

            pushFollow(FOLLOW_statement_in_whileRule2242);
            statement133=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(statement133.getTree());


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

            root_0 = (EventASTNode)adaptor.nil();
            // 318:2: -> ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) )
            {
                // BaseElement.g:318:5: ^( WHILE_RULE ^( EXPRESSION expression ) ^( STATEMENT statement ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(WHILE_RULE, "WHILE_RULE"), root_1);

                // BaseElement.g:318:18: ^( EXPRESSION expression )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                adaptor.addChild(root_2, stream_expression.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:318:43: ^( STATEMENT statement )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENT, "STATEMENT"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "forRule"
    // BaseElement.g:321:1: forRule : 'for' '(' ( ( type identifier )=> localVariableDeclaration | statementExpressionList SEMICOLON | emptyStatement ) ( expression )? SEMICOLON ( statementExpressionList )? ')' statement -> ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement ) ;
    public final Event_BaseElement.forRule_return forRule() throws RecognitionException {
        Event_BaseElement.forRule_return retval = new Event_BaseElement.forRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal134=null;
        Token char_literal135=null;
        Token SEMICOLON138=null;
        Token SEMICOLON141=null;
        Token char_literal143=null;
        Event_BaseElement.localVariableDeclaration_return localVariableDeclaration136 = null;

        Event_BaseElement.statementExpressionList_return statementExpressionList137 = null;

        Event_BaseElement.emptyStatement_return emptyStatement139 = null;

        Event_BaseElement.expression_return expression140 = null;

        Event_BaseElement.statementExpressionList_return statementExpressionList142 = null;

        Event_BaseElement.statement_return statement144 = null;


        EventASTNode string_literal134_tree=null;
        EventASTNode char_literal135_tree=null;
        EventASTNode SEMICOLON138_tree=null;
        EventASTNode SEMICOLON141_tree=null;
        EventASTNode char_literal143_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_244=new RewriteRuleTokenStream(adaptor,"token 244");
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
            string_literal134=(Token)match(input,244,FOLLOW_244_in_forRule2272); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_244.add(string_literal134);

            char_literal135=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_forRule2274); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal135);

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
                    localVariableDeclaration136=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_localVariableDeclaration.add(localVariableDeclaration136.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:322:51: statementExpressionList SEMICOLON
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2292);
                    statementExpressionList137=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementExpressionList.add(statementExpressionList137.getTree());
                    SEMICOLON138=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2294); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON138);


                    }
                    break;
                case 3 :
                    // BaseElement.g:322:87: emptyStatement
                    {
                    pushFollow(FOLLOW_emptyStatement_in_forRule2298);
                    emptyStatement139=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_emptyStatement.add(emptyStatement139.getTree());

                    }
                    break;

            }

            // BaseElement.g:323:2: ( expression )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==Identifier||(LA55_0>=EQ && LA55_0<=LBRACE)||(LA55_0>=PLUS && LA55_0<=MINUS)||LA55_0==POUND||(LA55_0>=FloatingPointLiteral && LA55_0<=FALSE)||LA55_0==LPAREN||LA55_0==LBRACK||(LA55_0>=184 && LA55_0<=232)||(LA55_0>=248 && LA55_0<=251)) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // BaseElement.g:323:3: expression
                    {
                    pushFollow(FOLLOW_expression_in_forRule2303);
                    expression140=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(expression140.getTree());

                    }
                    break;

            }

            SEMICOLON141=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_forRule2307); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON141);

            // BaseElement.g:324:2: ( statementExpressionList )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==Identifier||(LA56_0>=FloatingPointLiteral && LA56_0<=FALSE)||LA56_0==LPAREN||(LA56_0>=184 && LA56_0<=232)||(LA56_0>=248 && LA56_0<=251)) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // BaseElement.g:324:3: statementExpressionList
                    {
                    pushFollow(FOLLOW_statementExpressionList_in_forRule2311);
                    statementExpressionList142=statementExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_statementExpressionList.add(statementExpressionList142.getTree());

                    }
                    break;

            }

            char_literal143=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_forRule2315); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal143);

            pushFollow(FOLLOW_statement_in_forRule2320);
            statement144=statement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_statement.add(statement144.getTree());
            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: statement, localVariableDeclaration, expression, emptyStatement, statementExpressionList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 327:3: -> ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement )
            {
                // BaseElement.g:327:6: ^( FOR_LOOP ( localVariableDeclaration )? ( statementExpressionList )* ( emptyStatement )? ( expression )? statement )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(FOR_LOOP, "FOR_LOOP"), root_1);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tryCatchFinally"
    // BaseElement.g:331:1: tryCatchFinally : 'try' block ( catchRule ( finallyRule )? | finallyRule ) -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) ) ;
    public final Event_BaseElement.tryCatchFinally_return tryCatchFinally() throws RecognitionException {
        Event_BaseElement.tryCatchFinally_return retval = new Event_BaseElement.tryCatchFinally_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal145=null;
        Event_BaseElement.block_return block146 = null;

        Event_BaseElement.catchRule_return catchRule147 = null;

        Event_BaseElement.finallyRule_return finallyRule148 = null;

        Event_BaseElement.finallyRule_return finallyRule149 = null;


        EventASTNode string_literal145_tree=null;
        RewriteRuleTokenStream stream_245=new RewriteRuleTokenStream(adaptor,"token 245");
        RewriteRuleSubtreeStream stream_catchRule=new RewriteRuleSubtreeStream(adaptor,"rule catchRule");
        RewriteRuleSubtreeStream stream_finallyRule=new RewriteRuleSubtreeStream(adaptor,"rule finallyRule");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // BaseElement.g:332:2: ( 'try' block ( catchRule ( finallyRule )? | finallyRule ) -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) ) )
            // BaseElement.g:332:4: 'try' block ( catchRule ( finallyRule )? | finallyRule )
            {
            string_literal145=(Token)match(input,245,FOLLOW_245_in_tryCatchFinally2360); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_245.add(string_literal145);

            pushFollow(FOLLOW_block_in_tryCatchFinally2362);
            block146=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block146.getTree());
            // BaseElement.g:333:2: ( catchRule ( finallyRule )? | finallyRule )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==246) ) {
                alt58=1;
            }
            else if ( (LA58_0==247) ) {
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
                    catchRule147=catchRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_catchRule.add(catchRule147.getTree());
                    // BaseElement.g:333:14: ( finallyRule )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==247) ) {
                        alt57=1;
                    }
                    switch (alt57) {
                        case 1 :
                            // BaseElement.g:333:14: finallyRule
                            {
                            pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2370);
                            finallyRule148=finallyRule();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_finallyRule.add(finallyRule148.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // BaseElement.g:334:4: finallyRule
                    {
                    pushFollow(FOLLOW_finallyRule_in_tryCatchFinally2377);
                    finallyRule149=finallyRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_finallyRule.add(finallyRule149.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: block, catchRule, finallyRule
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 335:3: -> ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) )
            {
                // BaseElement.g:335:6: ^( TRY_STATEMENT ^( BODY block ) ^( CATCH_CLAUSE ( catchRule )? ) ^( FINALLY_CLAUSE ( finallyRule )? ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TRY_STATEMENT, "TRY_STATEMENT"), root_1);

                // BaseElement.g:335:22: ^( BODY block )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BODY, "BODY"), root_2);

                adaptor.addChild(root_2, stream_block.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:335:36: ^( CATCH_CLAUSE ( catchRule )? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(CATCH_CLAUSE, "CATCH_CLAUSE"), root_2);

                // BaseElement.g:335:51: ( catchRule )?
                if ( stream_catchRule.hasNext() ) {
                    adaptor.addChild(root_2, stream_catchRule.nextTree());

                }
                stream_catchRule.reset();

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:335:63: ^( FINALLY_CLAUSE ( finallyRule )? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(FINALLY_CLAUSE, "FINALLY_CLAUSE"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "catchRule"
    // BaseElement.g:338:1: catchRule : 'catch' '(' t= type id= identifier ')' block -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY block ) ) ;
    public final Event_BaseElement.catchRule_return catchRule() throws RecognitionException {
        Event_BaseElement.catchRule_return retval = new Event_BaseElement.catchRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal150=null;
        Token char_literal151=null;
        Token char_literal152=null;
        Event_BaseElement.type_return t = null;

        Event_BaseElement.identifier_return id = null;

        Event_BaseElement.block_return block153 = null;


        EventASTNode string_literal150_tree=null;
        EventASTNode char_literal151_tree=null;
        EventASTNode char_literal152_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_246=new RewriteRuleTokenStream(adaptor,"token 246");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:338:10: ( 'catch' '(' t= type id= identifier ')' block -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY block ) ) )
            // BaseElement.g:338:12: 'catch' '(' t= type id= identifier ')' block
            {
            string_literal150=(Token)match(input,246,FOLLOW_246_in_catchRule2417); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_246.add(string_literal150);

            char_literal151=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_catchRule2419); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(char_literal151);

            pushFollow(FOLLOW_type_in_catchRule2423);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());
            pushFollow(FOLLOW_identifier_in_catchRule2427);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) t.getTree()); 
            }
            char_literal152=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_catchRule2437); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(char_literal152);

            pushFollow(FOLLOW_block_in_catchRule2439);
            block153=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block153.getTree());


            // AST REWRITE
            // elements: t, block, id
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

            root_0 = (EventASTNode)adaptor.nil();
            // 341:3: -> ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY block ) )
            {
                // BaseElement.g:341:6: ^( CATCH_RULE ^( TYPE $t) ^( IDENTIFIER $id) ^( BODY block ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(CATCH_RULE, "CATCH_RULE"), root_1);

                // BaseElement.g:341:19: ^( TYPE $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:341:30: ^( IDENTIFIER $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(IDENTIFIER, "IDENTIFIER"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:341:48: ^( BODY block )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BODY, "BODY"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class finallyRule_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "finallyRule"
    // BaseElement.g:344:1: finallyRule : 'finally' block -> ^( FINALLY_RULE ^( BODY block ) ) ;
    public final Event_BaseElement.finallyRule_return finallyRule() throws RecognitionException {
        Event_BaseElement.finallyRule_return retval = new Event_BaseElement.finallyRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal154=null;
        Event_BaseElement.block_return block155 = null;


        EventASTNode string_literal154_tree=null;
        RewriteRuleTokenStream stream_247=new RewriteRuleTokenStream(adaptor,"token 247");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        try {
            // BaseElement.g:344:13: ( 'finally' block -> ^( FINALLY_RULE ^( BODY block ) ) )
            // BaseElement.g:344:15: 'finally' block
            {
            string_literal154=(Token)match(input,247,FOLLOW_247_in_finallyRule2478); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_247.add(string_literal154);

            pushFollow(FOLLOW_block_in_finallyRule2480);
            block155=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_block.add(block155.getTree());


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

            root_0 = (EventASTNode)adaptor.nil();
            // 345:3: -> ^( FINALLY_RULE ^( BODY block ) )
            {
                // BaseElement.g:345:6: ^( FINALLY_RULE ^( BODY block ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(FINALLY_RULE, "FINALLY_RULE"), root_1);

                // BaseElement.g:345:21: ^( BODY block )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BODY, "BODY"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "semiColon"
    // BaseElement.g:348:1: semiColon : SEMICOLON ;
    public final Event_BaseElement.semiColon_return semiColon() throws RecognitionException {
        Event_BaseElement.semiColon_return retval = new Event_BaseElement.semiColon_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token SEMICOLON156=null;

        EventASTNode SEMICOLON156_tree=null;

        try {
            // BaseElement.g:349:2: ( SEMICOLON )
            // BaseElement.g:349:4: SEMICOLON
            {
            root_0 = (EventASTNode)adaptor.nil();

            SEMICOLON156=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_semiColon2505); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SEMICOLON156_tree = (EventASTNode)adaptor.create(SEMICOLON156);
            adaptor.addChild(root_0, SEMICOLON156_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name"
    // BaseElement.g:359:1: name[int nameType] : ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )* ;
    public final Event_BaseElement.name_return name(int nameType) throws RecognitionException {
        Event_BaseElement.name_return retval = new Event_BaseElement.name_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token DOT158=null;
        Event_BaseElement.identifier_return ident = null;

        Event_BaseElement.identifier_return identifier157 = null;


        EventASTNode DOT158_tree=null;
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:360:2: ( ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )* )
            // BaseElement.g:360:4: ( identifier -> identifier ) ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )*
            {
            // BaseElement.g:360:4: ( identifier -> identifier )
            // BaseElement.g:360:5: identifier
            {
            pushFollow(FOLLOW_identifier_in_name2521);
            identifier157=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(identifier157.getTree());


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

            root_0 = (EventASTNode)adaptor.nil();
            // 360:16: -> identifier
            {
                adaptor.addChild(root_0, stream_identifier.nextTree());

            }

            retval.tree = root_0;}
            }

            // BaseElement.g:361:3: ( DOT ident= identifier -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident) )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==DOT) ) {
                    int LA59_2 = input.LA(2);

                    if ( (LA59_2==Identifier) ) {
                        alt59=1;
                    }
                    else if ( (LA59_2==NullLiteral||(LA59_2>=184 && LA59_2<=232)) ) {
                        alt59=1;
                    }


                }


                switch (alt59) {
            	case 1 :
            	    // BaseElement.g:361:4: DOT ident= identifier
            	    {
            	    DOT158=(Token)match(input,DOT,FOLLOW_DOT_in_name2531); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_DOT.add(DOT158);

            	    pushFollow(FOLLOW_identifier_in_name2535);
            	    ident=identifier();

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

            	    root_0 = (EventASTNode)adaptor.nil();
            	    // 362:4: -> ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident)
            	    {
            	        // BaseElement.g:362:7: ^( QUALIFIED_NAME ^( QUALIFIER $name) $ident)
            	        {
            	        EventASTNode root_1 = (EventASTNode)adaptor.nil();
            	        root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(QUALIFIED_NAME, "QUALIFIED_NAME"), root_1);

            	        // BaseElement.g:362:24: ^( QUALIFIER $name)
            	        {
            	        EventASTNode root_2 = (EventASTNode)adaptor.nil();
            	        root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(QUALIFIER, "QUALIFIER"), root_2);

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
            	    break loop59;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               pushName(nameType, retval.tree); 
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nameOrPrimitiveType"
    // BaseElement.g:366:1: nameOrPrimitiveType : ( name[BaseRulesParser.TYPE_REF] | primitiveType );
    public final Event_BaseElement.nameOrPrimitiveType_return nameOrPrimitiveType() throws RecognitionException {
        Event_BaseElement.nameOrPrimitiveType_return retval = new Event_BaseElement.nameOrPrimitiveType_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.name_return name159 = null;

        Event_BaseElement.primitiveType_return primitiveType160 = null;



        try {
            // BaseElement.g:367:2: ( name[BaseRulesParser.TYPE_REF] | primitiveType )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==Identifier||LA60_0==NullLiteral||(LA60_0>=184 && LA60_0<=232)) ) {
                alt60=1;
            }
            else if ( ((LA60_0>=248 && LA60_0<=251)) ) {
                alt60=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // BaseElement.g:367:4: name[BaseRulesParser.TYPE_REF]
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_name_in_nameOrPrimitiveType2575);
                    name159=name(BaseRulesParser.TYPE_REF);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name159.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:367:37: primitiveType
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_primitiveType_in_nameOrPrimitiveType2580);
                    primitiveType160=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, primitiveType160.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expressionName"
    // BaseElement.g:371:1: expressionName : name[TYPE_REF] ;
    public final Event_BaseElement.expressionName_return expressionName() throws RecognitionException {
        Event_BaseElement.expressionName_return retval = new Event_BaseElement.expressionName_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.name_return name161 = null;



        try {
            // BaseElement.g:372:2: ( name[TYPE_REF] )
            // BaseElement.g:372:4: name[TYPE_REF]
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_expressionName2594);
            name161=name(TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name161.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "methodName"
    // BaseElement.g:375:1: methodName : name[TYPE_METHOD_REF] ;
    public final Event_BaseElement.methodName_return methodName() throws RecognitionException {
        Event_BaseElement.methodName_return retval = new Event_BaseElement.methodName_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.name_return name162 = null;



        try {
            // BaseElement.g:376:2: ( name[TYPE_METHOD_REF] )
            // BaseElement.g:376:4: name[TYPE_METHOD_REF]
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_methodName2606);
            name162=name(TYPE_METHOD_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name162.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeName"
    // BaseElement.g:379:1: typeName : name[TYPE_REF] ;
    public final Event_BaseElement.typeName_return typeName() throws RecognitionException {
        Event_BaseElement.typeName_return retval = new Event_BaseElement.typeName_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.name_return name163 = null;



        try {
            // BaseElement.g:380:2: ( name[TYPE_REF] )
            // BaseElement.g:380:4: name[TYPE_REF]
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_name_in_typeName2618);
            name163=name(TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name163.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // BaseElement.g:383:1: type : typeAdditionalArrayDim ;
    public final Event_BaseElement.type_return type() throws RecognitionException {
        Event_BaseElement.type_return retval = new Event_BaseElement.type_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim164 = null;



        try {
            // BaseElement.g:383:6: ( typeAdditionalArrayDim )
            // BaseElement.g:383:8: typeAdditionalArrayDim
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_typeAdditionalArrayDim_in_type2629);
            typeAdditionalArrayDim164=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, typeAdditionalArrayDim164.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "typeAdditionalArrayDim"
    // BaseElement.g:386:1: typeAdditionalArrayDim : (t= typeName | p= primitiveType ) ( '[' ']' )? ;
    public final Event_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException {
        Event_BaseElement.typeAdditionalArrayDim_return retval = new Event_BaseElement.typeAdditionalArrayDim_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal165=null;
        Token char_literal166=null;
        Event_BaseElement.typeName_return t = null;

        Event_BaseElement.primitiveType_return p = null;


        EventASTNode char_literal165_tree=null;
        EventASTNode char_literal166_tree=null;

        try {
            // BaseElement.g:387:2: ( (t= typeName | p= primitiveType ) ( '[' ']' )? )
            // BaseElement.g:387:4: (t= typeName | p= primitiveType ) ( '[' ']' )?
            {
            root_0 = (EventASTNode)adaptor.nil();

            // BaseElement.g:387:4: (t= typeName | p= primitiveType )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==Identifier||LA61_0==NullLiteral||(LA61_0>=184 && LA61_0<=232)) ) {
                alt61=1;
            }
            else if ( ((LA61_0>=248 && LA61_0<=251)) ) {
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
                    // BaseElement.g:387:5: t= typeName
                    {
                    if ( state.backtracking==0 ) {
                       setTypeReference(true); 
                    }
                    pushFollow(FOLLOW_typeName_in_typeAdditionalArrayDim2646);
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
                    // BaseElement.g:387:74: p= primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_typeAdditionalArrayDim2653);
                    p=primitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());

                    }
                    break;

            }

            // BaseElement.g:387:91: ( '[' ']' )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==LBRACK) ) {
                int LA62_1 = input.LA(2);

                if ( (LA62_1==RBRACK) ) {
                    alt62=1;
                }
            }
            switch (alt62) {
                case 1 :
                    // BaseElement.g:387:93: '[' ']'
                    {
                    char_literal165=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_typeAdditionalArrayDim2658); if (state.failed) return retval;
                    char_literal166=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_typeAdditionalArrayDim2661); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       if (t != null) markAsArray((RulesASTNode) t.getTree()); else markAsArray((RulesASTNode) p.getTree()); 
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitiveType"
    // BaseElement.g:391:1: primitiveType : primitive -> ^( PRIMITIVE_TYPE[\"type\"] primitive ) ;
    public final Event_BaseElement.primitiveType_return primitiveType() throws RecognitionException {
        Event_BaseElement.primitiveType_return retval = new Event_BaseElement.primitiveType_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.primitive_return primitive167 = null;


        RewriteRuleSubtreeStream stream_primitive=new RewriteRuleSubtreeStream(adaptor,"rule primitive");
        try {
            // BaseElement.g:392:2: ( primitive -> ^( PRIMITIVE_TYPE[\"type\"] primitive ) )
            // BaseElement.g:392:4: primitive
            {
            pushFollow(FOLLOW_primitive_in_primitiveType2683);
            primitive167=primitive();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_primitive.add(primitive167.getTree());


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

            root_0 = (EventASTNode)adaptor.nil();
            // 393:2: -> ^( PRIMITIVE_TYPE[\"type\"] primitive )
            {
                // BaseElement.g:393:5: ^( PRIMITIVE_TYPE[\"type\"] primitive )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PRIMITIVE_TYPE, "type"), root_1);

                adaptor.addChild(root_1, stream_primitive.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primitive"
    // BaseElement.g:396:1: primitive : ( 'boolean' | 'int' | 'long' | 'double' );
    public final Event_BaseElement.primitive_return primitive() throws RecognitionException {
        Event_BaseElement.primitive_return retval = new Event_BaseElement.primitive_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set168=null;

        EventASTNode set168_tree=null;

        try {
            // BaseElement.g:397:2: ( 'boolean' | 'int' | 'long' | 'double' )
            // BaseElement.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set168=(Token)input.LT(1);
            if ( (input.LA(1)>=248 && input.LA(1)<=251) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (EventASTNode)adaptor.create(set168));
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localVariableDeclaration"
    // BaseElement.g:401:1: localVariableDeclaration : t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) ) ;
    public final Event_BaseElement.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException {
        Event_BaseElement.localVariableDeclaration_return retval = new Event_BaseElement.localVariableDeclaration_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal170=null;
        Token SEMICOLON172=null;
        Event_BaseElement.type_return t = null;

        Event_BaseElement.variableDeclarator_return variableDeclarator169 = null;

        Event_BaseElement.variableDeclarator_return variableDeclarator171 = null;


        EventASTNode char_literal170_tree=null;
        EventASTNode SEMICOLON172_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleSubtreeStream stream_variableDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule variableDeclarator");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // BaseElement.g:402:2: (t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) ) )
            // BaseElement.g:402:4: t= type variableDeclarator[(RulesASTNode) t.getTree()] ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )* SEMICOLON
            {
            pushFollow(FOLLOW_type_in_localVariableDeclaration2731);
            t=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(t.getTree());
            pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2733);
            variableDeclarator169=variableDeclarator((RulesASTNode) t.getTree());

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_variableDeclarator.add(variableDeclarator169.getTree());
            // BaseElement.g:403:3: ( ',' variableDeclarator[(RulesASTNode) t.getTree()] )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==236) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // BaseElement.g:403:4: ',' variableDeclarator[(RulesASTNode) t.getTree()]
            	    {
            	    char_literal170=(Token)match(input,236,FOLLOW_236_in_localVariableDeclaration2740); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_236.add(char_literal170);

            	    pushFollow(FOLLOW_variableDeclarator_in_localVariableDeclaration2742);
            	    variableDeclarator171=variableDeclarator((RulesASTNode) t.getTree());

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_variableDeclarator.add(variableDeclarator171.getTree());

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);

            SEMICOLON172=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_localVariableDeclaration2747); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON172);



            // AST REWRITE
            // elements: t, variableDeclarator
            // token labels: 
            // rule labels: retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 404:3: -> ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) )
            {
                // BaseElement.g:404:6: ^( LOCAL_VARIABLE_DECL ^( TYPE $t) ^( DECLARATORS ( variableDeclarator )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LOCAL_VARIABLE_DECL, "LOCAL_VARIABLE_DECL"), root_1);

                // BaseElement.g:404:28: ^( TYPE $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:404:39: ^( DECLARATORS ( variableDeclarator )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DECLARATORS, "DECLARATORS"), root_2);

                // BaseElement.g:404:53: ( variableDeclarator )*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variableDeclarator"
    // BaseElement.g:407:1: variableDeclarator[RulesASTNode type] : id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) ;
    public final Event_BaseElement.variableDeclarator_return variableDeclarator(RulesASTNode type) throws RecognitionException {
        Event_BaseElement.variableDeclarator_return retval = new Event_BaseElement.variableDeclarator_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token ASSIGN173=null;
        Event_BaseElement.identifier_return id = null;

        Event_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral174 = null;

        Event_BaseElement.expression_return expression175 = null;


        EventASTNode ASSIGN173_tree=null;
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:408:2: (id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) )
            // BaseElement.g:408:4: id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            {
            pushFollow(FOLLOW_identifier_in_variableDeclarator2784);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushDefineName((RulesASTNode) id.getTree(), type); 
            }
            // BaseElement.g:408:73: ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==ASSIGN) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // BaseElement.g:408:74: ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    {
                    ASSIGN173=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_variableDeclarator2789); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN173);

                    // BaseElement.g:409:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    int alt64=2;
                    alt64 = dfa64.predict(input);
                    switch (alt64) {
                        case 1 :
                            // BaseElement.g:409:3: ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral
                            {
                            pushFollow(FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2806);
                            localInitializerArrayLiteral174=localInitializerArrayLiteral();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral174.getTree());

                            }
                            break;
                        case 2 :
                            // BaseElement.g:410:4: expression
                            {
                            pushFollow(FOLLOW_expression_in_variableDeclarator2811);
                            expression175=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression175.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }



            // AST REWRITE
            // elements: localInitializerArrayLiteral, id, expression
            // token labels: 
            // rule labels: id, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 411:3: -> ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
            {
                // BaseElement.g:411:6: ^( VARIABLE_DECLARATOR ^( NAME $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(VARIABLE_DECLARATOR, "VARIABLE_DECLARATOR"), root_1);

                // BaseElement.g:411:28: ^( NAME $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:412:3: ( ^( INITIALIZER localInitializerArrayLiteral ) )?
                if ( stream_localInitializerArrayLiteral.hasNext() ) {
                    // BaseElement.g:412:3: ^( INITIALIZER localInitializerArrayLiteral )
                    {
                    EventASTNode root_2 = (EventASTNode)adaptor.nil();
                    root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

                    adaptor.addChild(root_2, stream_localInitializerArrayLiteral.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_localInitializerArrayLiteral.reset();
                // BaseElement.g:413:3: ( ^( EXPRESSION expression ) )?
                if ( stream_expression.hasNext() ) {
                    // BaseElement.g:413:3: ^( EXPRESSION expression )
                    {
                    EventASTNode root_2 = (EventASTNode)adaptor.nil();
                    root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "localInitializerArrayLiteral"
    // BaseElement.g:416:1: localInitializerArrayLiteral : LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) ) ;
    public final Event_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException {
        Event_BaseElement.localInitializerArrayLiteral_return retval = new Event_BaseElement.localInitializerArrayLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE176=null;
        Token char_literal177=null;
        Token RBRACE178=null;
        List list_exp=null;
        RuleReturnScope exp = null;
        EventASTNode LBRACE176_tree=null;
        EventASTNode char_literal177_tree=null;
        EventASTNode RBRACE178_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // BaseElement.g:417:2: ( LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) ) )
            // BaseElement.g:417:4: LBRACE (exp+= expression ( ',' exp+= expression )* )? RBRACE
            {
            LBRACE176=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_localInitializerArrayLiteral2859); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE176);

            // BaseElement.g:417:11: (exp+= expression ( ',' exp+= expression )* )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==Identifier||(LA67_0>=EQ && LA67_0<=LBRACE)||(LA67_0>=PLUS && LA67_0<=MINUS)||LA67_0==POUND||(LA67_0>=FloatingPointLiteral && LA67_0<=FALSE)||LA67_0==LPAREN||LA67_0==LBRACK||(LA67_0>=184 && LA67_0<=232)||(LA67_0>=248 && LA67_0<=251)) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // BaseElement.g:417:12: exp+= expression ( ',' exp+= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2864);
                    exp=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    if (list_exp==null) list_exp=new ArrayList();
                    list_exp.add(exp.getTree());

                    // BaseElement.g:417:28: ( ',' exp+= expression )*
                    loop66:
                    do {
                        int alt66=2;
                        int LA66_0 = input.LA(1);

                        if ( (LA66_0==236) ) {
                            alt66=1;
                        }


                        switch (alt66) {
                    	case 1 :
                    	    // BaseElement.g:417:29: ',' exp+= expression
                    	    {
                    	    char_literal177=(Token)match(input,236,FOLLOW_236_in_localInitializerArrayLiteral2867); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_236.add(char_literal177);

                    	    pushFollow(FOLLOW_expression_in_localInitializerArrayLiteral2871);
                    	    exp=expression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expression.add(exp.getTree());
                    	    if (list_exp==null) list_exp=new ArrayList();
                    	    list_exp.add(exp.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop66;
                        }
                    } while (true);


                    }
                    break;

            }

            RBRACE178=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_localInitializerArrayLiteral2877); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE178);



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
            root_0 = (EventASTNode)adaptor.nil();
            // 418:3: -> ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) )
            {
                // BaseElement.g:418:6: ^( LOCAL_INITIALIZER ^( EXPRESSIONS ( $exp)* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LOCAL_INITIALIZER, "LOCAL_INITIALIZER"), root_1);

                // BaseElement.g:418:26: ^( EXPRESSIONS ( $exp)* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSIONS, "EXPRESSIONS"), root_2);

                // BaseElement.g:418:40: ( $exp)*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayLiteral"
    // BaseElement.g:421:1: arrayLiteral : type localInitializerArrayLiteral -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) ) ;
    public final Event_BaseElement.arrayLiteral_return arrayLiteral() throws RecognitionException {
        Event_BaseElement.arrayLiteral_return retval = new Event_BaseElement.arrayLiteral_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.type_return type179 = null;

        Event_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral180 = null;


        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // BaseElement.g:422:2: ( type localInitializerArrayLiteral -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) ) )
            // BaseElement.g:422:4: type localInitializerArrayLiteral
            {
            pushFollow(FOLLOW_type_in_arrayLiteral2904);
            type179=type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_type.add(type179.getTree());
            pushFollow(FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2906);
            localInitializerArrayLiteral180=localInitializerArrayLiteral();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral180.getTree());


            // AST REWRITE
            // elements: localInitializerArrayLiteral, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 423:3: -> ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) )
            {
                // BaseElement.g:423:6: ^( ARRAY_LITERAL ^( TYPE type ) ^( INITIALIZER localInitializerArrayLiteral ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARRAY_LITERAL, "ARRAY_LITERAL"), root_1);

                // BaseElement.g:423:22: ^( TYPE type )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_type.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:423:35: ^( INITIALIZER localInitializerArrayLiteral )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayAllocator"
    // BaseElement.g:426:1: arrayAllocator : t= typeAdditionalArrayDim '[' expression ']' LBRACE '}' -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) ) ;
    public final Event_BaseElement.arrayAllocator_return arrayAllocator() throws RecognitionException {
        Event_BaseElement.arrayAllocator_return retval = new Event_BaseElement.arrayAllocator_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal181=null;
        Token char_literal183=null;
        Token LBRACE184=null;
        Token char_literal185=null;
        Event_BaseElement.typeAdditionalArrayDim_return t = null;

        Event_BaseElement.expression_return expression182 = null;


        EventASTNode char_literal181_tree=null;
        EventASTNode char_literal183_tree=null;
        EventASTNode LBRACE184_tree=null;
        EventASTNode char_literal185_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_typeAdditionalArrayDim=new RewriteRuleSubtreeStream(adaptor,"rule typeAdditionalArrayDim");
        try {
            // BaseElement.g:427:2: (t= typeAdditionalArrayDim '[' expression ']' LBRACE '}' -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) ) )
            // BaseElement.g:427:4: t= typeAdditionalArrayDim '[' expression ']' LBRACE '}'
            {
            pushFollow(FOLLOW_typeAdditionalArrayDim_in_arrayAllocator2939);
            t=typeAdditionalArrayDim();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_typeAdditionalArrayDim.add(t.getTree());
            char_literal181=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_arrayAllocator2941); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACK.add(char_literal181);

            if ( state.backtracking==0 ) {
               arrayDepth++; markAsArray((RulesASTNode) t.getTree()); 
            }
            pushFollow(FOLLOW_expression_in_arrayAllocator2950);
            expression182=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expression.add(expression182.getTree());
            char_literal183=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_arrayAllocator2952); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACK.add(char_literal183);

            if ( state.backtracking==0 ) {
               arrayDepth--; 
            }
            LBRACE184=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_arrayAllocator2956); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE184);

            char_literal185=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_arrayAllocator2958); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(char_literal185);



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

            root_0 = (EventASTNode)adaptor.nil();
            // 430:3: -> ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) )
            {
                // BaseElement.g:430:6: ^( ARRAY_ALLOCATOR ^( TYPE $t) ^( EXPRESSION expression ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ARRAY_ALLOCATOR, "ARRAY_ALLOCATOR"), root_1);

                // BaseElement.g:430:24: ^( TYPE $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "TYPE"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:430:35: ^( EXPRESSION expression )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class declareNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareNT"
    // BaseElement.g:434:1: declareNT : 'declare' declareNTBlock -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock ) ;
    public final Event_BaseElement.declareNT_return declareNT() throws RecognitionException {
        Event_BaseElement.declareNT_return retval = new Event_BaseElement.declareNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal186=null;
        Event_BaseElement.declareNTBlock_return declareNTBlock187 = null;


        EventASTNode string_literal186_tree=null;
        RewriteRuleTokenStream stream_252=new RewriteRuleTokenStream(adaptor,"token 252");
        RewriteRuleSubtreeStream stream_declareNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule declareNTBlock");
        try {
            // BaseElement.g:438:2: ( 'declare' declareNTBlock -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock ) )
            // BaseElement.g:438:4: 'declare' declareNTBlock
            {
            string_literal186=(Token)match(input,252,FOLLOW_252_in_declareNT2995); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_252.add(string_literal186);

            pushFollow(FOLLOW_declareNTBlock_in_declareNT2997);
            declareNTBlock187=declareNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_declareNTBlock.add(declareNTBlock187.getTree());


            // AST REWRITE
            // elements: declareNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 439:2: -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock )
            {
                // BaseElement.g:439:5: ^( DECLARE_BLOCK[\"declare\"] declareNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DECLARE_BLOCK, "declare"), root_1);

                adaptor.addChild(root_1, stream_declareNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "declareNT"

    public static class declareNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareNTBlock"
    // BaseElement.g:442:1: declareNTBlock : LBRACE ( declarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) ;
    public final Event_BaseElement.declareNTBlock_return declareNTBlock() throws RecognitionException {
        Event_BaseElement.declareNTBlock_return retval = new Event_BaseElement.declareNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE188=null;
        Token RBRACE190=null;
        Event_BaseElement.declarator_return declarator189 = null;


        EventASTNode LBRACE188_tree=null;
        EventASTNode RBRACE190_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        try {
            // BaseElement.g:442:16: ( LBRACE ( declarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) )
            // BaseElement.g:442:18: LBRACE ( declarator )* RBRACE
            {
            LBRACE188=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_declareNTBlock3018); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE188);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.DECLARE_SCOPE); 
            }
            // BaseElement.g:442:71: ( declarator )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==Identifier||LA68_0==NullLiteral||(LA68_0>=184 && LA68_0<=232)) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // BaseElement.g:442:71: declarator
            	    {
            	    pushFollow(FOLLOW_declarator_in_declareNTBlock3022);
            	    declarator189=declarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_declarator.add(declarator189.getTree());

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

            RBRACE190=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_declareNTBlock3025); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE190);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: declarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 443:2: -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
            {
                // BaseElement.g:443:5: ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // BaseElement.g:443:13: ^( STATEMENTS[\"declarators\"] ( declarator )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "declarators"), root_2);

                // BaseElement.g:443:41: ( declarator )*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "declareNTBlock"

    public static class scopeNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeNT"
    // BaseElement.g:446:1: scopeNT : 'scope' scopeNTBlock -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock ) ;
    public final Event_BaseElement.scopeNT_return scopeNT() throws RecognitionException {
        Event_BaseElement.scopeNT_return retval = new Event_BaseElement.scopeNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal191=null;
        Event_BaseElement.scopeNTBlock_return scopeNTBlock192 = null;


        EventASTNode string_literal191_tree=null;
        RewriteRuleTokenStream stream_253=new RewriteRuleTokenStream(adaptor,"token 253");
        RewriteRuleSubtreeStream stream_scopeNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule scopeNTBlock");
        try {
            // BaseElement.g:447:2: ( 'scope' scopeNTBlock -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock ) )
            // BaseElement.g:447:4: 'scope' scopeNTBlock
            {
            string_literal191=(Token)match(input,253,FOLLOW_253_in_scopeNT3054); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_253.add(string_literal191);

            pushFollow(FOLLOW_scopeNTBlock_in_scopeNT3056);
            scopeNTBlock192=scopeNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_scopeNTBlock.add(scopeNTBlock192.getTree());


            // AST REWRITE
            // elements: scopeNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 448:2: -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock )
            {
                // BaseElement.g:448:5: ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SCOPE_BLOCK, "scope"), root_1);

                adaptor.addChild(root_1, stream_scopeNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "scopeNT"

    public static class scopeNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeNTBlock"
    // BaseElement.g:451:1: scopeNTBlock : LBRACE ( scopeDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) ;
    public final Event_BaseElement.scopeNTBlock_return scopeNTBlock() throws RecognitionException {
        Event_BaseElement.scopeNTBlock_return retval = new Event_BaseElement.scopeNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE193=null;
        Token RBRACE195=null;
        Event_BaseElement.scopeDeclarator_return scopeDeclarator194 = null;


        EventASTNode LBRACE193_tree=null;
        EventASTNode RBRACE195_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_scopeDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule scopeDeclarator");
        try {
            // BaseElement.g:451:14: ( LBRACE ( scopeDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) )
            // BaseElement.g:451:16: LBRACE ( scopeDeclarator )* RBRACE
            {
            LBRACE193=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_scopeNTBlock3076); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE193);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.SCOPE_SCOPE); 
            }
            // BaseElement.g:451:67: ( scopeDeclarator )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==Identifier||LA69_0==NullLiteral||(LA69_0>=184 && LA69_0<=232)||(LA69_0>=248 && LA69_0<=251)) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // BaseElement.g:451:67: scopeDeclarator
            	    {
            	    pushFollow(FOLLOW_scopeDeclarator_in_scopeNTBlock3080);
            	    scopeDeclarator194=scopeDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_scopeDeclarator.add(scopeDeclarator194.getTree());

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);

            RBRACE195=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_scopeNTBlock3083); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE195);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: scopeDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 452:2: -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
            {
                // BaseElement.g:452:5: ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // BaseElement.g:452:13: ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "declarators"), root_2);

                // BaseElement.g:452:41: ( scopeDeclarator )*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "scopeNTBlock"

    public static class scopeDeclarator_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeDeclarator"
    // BaseElement.g:455:1: scopeDeclarator : t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ) ;
    public final Event_BaseElement.scopeDeclarator_return scopeDeclarator() throws RecognitionException {
        Event_BaseElement.scopeDeclarator_return retval = new Event_BaseElement.scopeDeclarator_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token char_literal196=null;
        Token char_literal197=null;
        Token SEMICOLON198=null;
        Event_BaseElement.nameOrPrimitiveType_return t = null;

        Event_BaseElement.identifier_return id = null;


        EventASTNode char_literal196_tree=null;
        EventASTNode char_literal197_tree=null;
        EventASTNode SEMICOLON198_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_nameOrPrimitiveType=new RewriteRuleSubtreeStream(adaptor,"rule nameOrPrimitiveType");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:456:2: (t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ) )
            // BaseElement.g:456:4: t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_nameOrPrimitiveType_in_scopeDeclarator3117);
            t=nameOrPrimitiveType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_nameOrPrimitiveType.add(t.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            // BaseElement.g:457:3: ( '[' ']' )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==LBRACK) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // BaseElement.g:457:5: '[' ']'
                    {
                    char_literal196=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_scopeDeclarator3126); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACK.add(char_literal196);

                    char_literal197=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_scopeDeclarator3128); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACK.add(char_literal197);

                    if ( state.backtracking==0 ) {
                       markAsArray((RulesASTNode) t.getTree()); 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_identifier_in_scopeDeclarator3137);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            SEMICOLON198=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_scopeDeclarator3139); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON198);

            if ( state.backtracking==0 ) {
               pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); 
            }


            // AST REWRITE
            // elements: t, id
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

            root_0 = (EventASTNode)adaptor.nil();
            // 459:2: -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) )
            {
                // BaseElement.g:459:5: ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(SCOPE_DECLARATOR, "declarator"), root_1);

                // BaseElement.g:459:38: ^( TYPE[\"type\"] $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:460:3: ^( NAME[\"name\"] $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "scopeDeclarator"

    public static class declarator_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declarator"
    // BaseElement.g:463:1: declarator : nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) ) ;
    public final Event_BaseElement.declarator_return declarator() throws RecognitionException {
        Event_BaseElement.declarator_return retval = new Event_BaseElement.declarator_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token SEMICOLON199=null;
        Event_BaseElement.name_return nm = null;

        Event_BaseElement.identifier_return id = null;


        EventASTNode SEMICOLON199_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:464:2: (nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) ) )
            // BaseElement.g:464:4: nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_name_in_declarator3185);
            nm=name(BaseRulesParser.TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(nm.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            pushFollow(FOLLOW_identifier_in_declarator3192);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            SEMICOLON199=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_declarator3194); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON199);

            if ( state.backtracking==0 ) {
               pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)nm.getTree()); 
            }


            // AST REWRITE
            // elements: nm, id
            // token labels: 
            // rule labels: id, retval, nm
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_nm=new RewriteRuleSubtreeStream(adaptor,"rule nm",nm!=null?nm.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 466:2: -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) )
            {
                // BaseElement.g:466:5: ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DECLARATOR, "declarator"), root_1);

                // BaseElement.g:466:32: ^( TYPE[\"type\"] $nm)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_nm.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:467:3: ^( NAME[\"name\"] $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "declarator"

    public static class thenNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenNT"
    // BaseElement.g:470:1: thenNT : 'then' thenNTBlock -> ^( THEN_BLOCK[\"then\"] thenNTBlock ) ;
    public final Event_BaseElement.thenNT_return thenNT() throws RecognitionException {
        Event_BaseElement.thenNT_return retval = new Event_BaseElement.thenNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal200=null;
        Event_BaseElement.thenNTBlock_return thenNTBlock201 = null;


        EventASTNode string_literal200_tree=null;
        RewriteRuleTokenStream stream_254=new RewriteRuleTokenStream(adaptor,"token 254");
        RewriteRuleSubtreeStream stream_thenNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule thenNTBlock");
        try {
            // BaseElement.g:473:8: ( 'then' thenNTBlock -> ^( THEN_BLOCK[\"then\"] thenNTBlock ) )
            // BaseElement.g:473:10: 'then' thenNTBlock
            {
            string_literal200=(Token)match(input,254,FOLLOW_254_in_thenNT3237); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_254.add(string_literal200);

            pushFollow(FOLLOW_thenNTBlock_in_thenNT3239);
            thenNTBlock201=thenNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenNTBlock.add(thenNTBlock201.getTree());


            // AST REWRITE
            // elements: thenNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 474:2: -> ^( THEN_BLOCK[\"then\"] thenNTBlock )
            {
                // BaseElement.g:474:5: ^( THEN_BLOCK[\"then\"] thenNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(THEN_BLOCK, "then"), root_1);

                adaptor.addChild(root_1, stream_thenNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "thenNT"

    public static class thenNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenNTBlock"
    // BaseElement.g:477:1: thenNTBlock : LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) ;
    public final Event_BaseElement.thenNTBlock_return thenNTBlock() throws RecognitionException {
        Event_BaseElement.thenNTBlock_return retval = new Event_BaseElement.thenNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE202=null;
        Token RBRACE204=null;
        Event_BaseElement.thenStatements_return thenStatements203 = null;


        EventASTNode LBRACE202_tree=null;
        EventASTNode RBRACE204_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_thenStatements=new RewriteRuleSubtreeStream(adaptor,"rule thenStatements");
        try {
            // BaseElement.g:477:13: ( LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) )
            // BaseElement.g:477:15: LBRACE thenStatements RBRACE
            {
            LBRACE202=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_thenNTBlock3260); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE202);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.THEN_SCOPE); 
            }
            pushFollow(FOLLOW_thenStatements_in_thenNTBlock3264);
            thenStatements203=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenStatements.add(thenStatements203.getTree());
            RBRACE204=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_thenNTBlock3269); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE204);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: thenStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 480:2: -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
            {
                // BaseElement.g:480:5: ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // BaseElement.g:480:13: ^( STATEMENTS ( thenStatements )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // BaseElement.g:480:26: ( thenStatements )*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "thenNTBlock"

    public static class thenStatements_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatements"
    // BaseElement.g:483:1: thenStatements : ( thenStatement )* ;
    public final Event_BaseElement.thenStatements_return thenStatements() throws RecognitionException {
        Event_BaseElement.thenStatements_return retval = new Event_BaseElement.thenStatements_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.thenStatement_return thenStatement205 = null;



        try {
            // BaseElement.g:484:2: ( ( thenStatement )* )
            // BaseElement.g:484:4: ( thenStatement )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            // BaseElement.g:484:4: ( thenStatement )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( ((LA71_0>=Identifier && LA71_0<=SEMICOLON)||LA71_0==LBRACE||(LA71_0>=FloatingPointLiteral && LA71_0<=WS)||LA71_0==LPAREN||(LA71_0>=184 && LA71_0<=232)||(LA71_0>=237 && LA71_0<=241)||(LA71_0>=243 && LA71_0<=245)||(LA71_0>=248 && LA71_0<=251)) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // BaseElement.g:484:4: thenStatement
            	    {
            	    pushFollow(FOLLOW_thenStatement_in_thenStatements3300);
            	    thenStatement205=thenStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement205.getTree());

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "thenStatements"

    public static class thenStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatement"
    // BaseElement.g:487:1: thenStatement : ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement );
    public final Event_BaseElement.thenStatement_return thenStatement() throws RecognitionException {
        Event_BaseElement.thenStatement_return retval = new Event_BaseElement.thenStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.actionContextStatement_return actionContextStatement206 = null;

        Event_BaseElement.localVariableDeclaration_return localVariableDeclaration207 = null;

        Event_BaseElement.statement_return statement208 = null;

        Event_BaseElement.emptyBodyStatement_return emptyBodyStatement209 = null;



        try {
            // BaseElement.g:488:2: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement )
            int alt72=4;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // BaseElement.g:488:4: ({...}? actionContextStatement )=> actionContextStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_actionContextStatement_in_thenStatement3320);
                    actionContextStatement206=actionContextStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextStatement206.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:489:4: ( type identifier )=> localVariableDeclaration
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_localVariableDeclaration_in_thenStatement3331);
                    localVariableDeclaration207=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, localVariableDeclaration207.getTree());

                    }
                    break;
                case 3 :
                    // BaseElement.g:490:4: statement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_statement_in_thenStatement3336);
                    statement208=statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement208.getTree());

                    }
                    break;
                case 4 :
                    // BaseElement.g:491:4: emptyBodyStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_emptyBodyStatement_in_thenStatement3342);
                    emptyBodyStatement209=emptyBodyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyBodyStatement209.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "thenStatement"

    public static class statement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // BaseElement.g:494:1: statement : ( lineStatement | blockStatement );
    public final Event_BaseElement.statement_return statement() throws RecognitionException {
        Event_BaseElement.statement_return retval = new Event_BaseElement.statement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.lineStatement_return lineStatement210 = null;

        Event_BaseElement.blockStatement_return blockStatement211 = null;



        try {
            // BaseElement.g:495:2: ( lineStatement | blockStatement )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( ((LA73_0>=Identifier && LA73_0<=SEMICOLON)||(LA73_0>=FloatingPointLiteral && LA73_0<=FALSE)||LA73_0==LPAREN||(LA73_0>=184 && LA73_0<=232)||(LA73_0>=237 && LA73_0<=240)||(LA73_0>=248 && LA73_0<=251)) ) {
                alt73=1;
            }
            else if ( (LA73_0==LBRACE||LA73_0==241||(LA73_0>=243 && LA73_0<=245)) ) {
                alt73=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // BaseElement.g:495:4: lineStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_lineStatement_in_statement3354);
                    lineStatement210=lineStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lineStatement210.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:495:20: blockStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_blockStatement_in_statement3358);
                    blockStatement211=blockStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, blockStatement211.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "statement"

    public static class lineStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lineStatement"
    // BaseElement.g:498:1: lineStatement : ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement );
    public final Event_BaseElement.lineStatement_return lineStatement() throws RecognitionException {
        Event_BaseElement.lineStatement_return retval = new Event_BaseElement.lineStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.emptyStatement_return emptyStatement212 = null;

        Event_BaseElement.statementExpression_return statementExpression213 = null;

        Event_BaseElement.returnStatement_return returnStatement214 = null;

        Event_BaseElement.breakStatement_return breakStatement215 = null;

        Event_BaseElement.continueStatement_return continueStatement216 = null;

        Event_BaseElement.throwStatement_return throwStatement217 = null;

        Event_BaseElement.actionContextStatement_return actionContextStatement218 = null;



        try {
            // BaseElement.g:499:2: ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement )
            int alt74=7;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // BaseElement.g:499:4: emptyStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_emptyStatement_in_lineStatement3370);
                    emptyStatement212=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyStatement212.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:500:4: statementExpression
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_statementExpression_in_lineStatement3375);
                    statementExpression213=statementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpression213.getTree());

                    }
                    break;
                case 3 :
                    // BaseElement.g:501:4: returnStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_lineStatement3380);
                    returnStatement214=returnStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnStatement214.getTree());

                    }
                    break;
                case 4 :
                    // BaseElement.g:502:4: breakStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_breakStatement_in_lineStatement3385);
                    breakStatement215=breakStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, breakStatement215.getTree());

                    }
                    break;
                case 5 :
                    // BaseElement.g:503:4: continueStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_continueStatement_in_lineStatement3390);
                    continueStatement216=continueStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continueStatement216.getTree());

                    }
                    break;
                case 6 :
                    // BaseElement.g:504:4: throwStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_throwStatement_in_lineStatement3395);
                    throwStatement217=throwStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwStatement217.getTree());

                    }
                    break;
                case 7 :
                    // BaseElement.g:505:4: {...}? => actionContextStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    if ( !(( isInActionContextBlock() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "lineStatement", " isInActionContextBlock() ");
                    }
                    pushFollow(FOLLOW_actionContextStatement_in_lineStatement3404);
                    actionContextStatement218=actionContextStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextStatement218.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "lineStatement"

    public static class blockStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "blockStatement"
    // BaseElement.g:508:1: blockStatement : ( ifRule | whileRule | forRule | block | tryCatchFinally );
    public final Event_BaseElement.blockStatement_return blockStatement() throws RecognitionException {
        Event_BaseElement.blockStatement_return retval = new Event_BaseElement.blockStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.ifRule_return ifRule219 = null;

        Event_BaseElement.whileRule_return whileRule220 = null;

        Event_BaseElement.forRule_return forRule221 = null;

        Event_BaseElement.block_return block222 = null;

        Event_BaseElement.tryCatchFinally_return tryCatchFinally223 = null;



        try {
            // BaseElement.g:509:2: ( ifRule | whileRule | forRule | block | tryCatchFinally )
            int alt75=5;
            switch ( input.LA(1) ) {
            case 241:
                {
                alt75=1;
                }
                break;
            case 243:
                {
                alt75=2;
                }
                break;
            case 244:
                {
                alt75=3;
                }
                break;
            case LBRACE:
                {
                alt75=4;
                }
                break;
            case 245:
                {
                alt75=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // BaseElement.g:509:4: ifRule
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_ifRule_in_blockStatement3416);
                    ifRule219=ifRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ifRule219.getTree());

                    }
                    break;
                case 2 :
                    // BaseElement.g:510:4: whileRule
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_whileRule_in_blockStatement3421);
                    whileRule220=whileRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, whileRule220.getTree());

                    }
                    break;
                case 3 :
                    // BaseElement.g:511:4: forRule
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_forRule_in_blockStatement3426);
                    forRule221=forRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, forRule221.getTree());

                    }
                    break;
                case 4 :
                    // BaseElement.g:512:4: block
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_block_in_blockStatement3431);
                    block222=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block222.getTree());

                    }
                    break;
                case 5 :
                    // BaseElement.g:513:4: tryCatchFinally
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_tryCatchFinally_in_blockStatement3436);
                    tryCatchFinally223=tryCatchFinally();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tryCatchFinally223.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "blockStatement"

    public static class block_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // BaseElement.g:517:1: block : LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) ;
    public final Event_BaseElement.block_return block() throws RecognitionException {
        Event_BaseElement.block_return retval = new Event_BaseElement.block_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE224=null;
        Token RBRACE228=null;
        Event_BaseElement.actionContextStatement_return actionContextStatement225 = null;

        Event_BaseElement.localVariableDeclaration_return localVariableDeclaration226 = null;

        Event_BaseElement.statement_return statement227 = null;


        EventASTNode LBRACE224_tree=null;
        EventASTNode RBRACE228_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_actionContextStatement=new RewriteRuleSubtreeStream(adaptor,"rule actionContextStatement");
        RewriteRuleSubtreeStream stream_localVariableDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule localVariableDeclaration");
        try {
            // BaseElement.g:517:7: ( LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) )
            // BaseElement.g:517:9: LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE
            {
            LBRACE224=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_block3448); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE224);

            if ( state.backtracking==0 ) {
               pushScope(BLOCK_SCOPE); 
            }
            // BaseElement.g:518:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*
            loop76:
            do {
                int alt76=4;
                alt76 = dfa76.predict(input);
                switch (alt76) {
            	case 1 :
            	    // BaseElement.g:518:5: ({...}? actionContextStatement )=> actionContextStatement
            	    {
            	    pushFollow(FOLLOW_actionContextStatement_in_block3464);
            	    actionContextStatement225=actionContextStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_actionContextStatement.add(actionContextStatement225.getTree());

            	    }
            	    break;
            	case 2 :
            	    // BaseElement.g:518:87: ( type identifier )=> localVariableDeclaration
            	    {
            	    pushFollow(FOLLOW_localVariableDeclaration_in_block3475);
            	    localVariableDeclaration226=localVariableDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_localVariableDeclaration.add(localVariableDeclaration226.getTree());

            	    }
            	    break;
            	case 3 :
            	    // BaseElement.g:518:134: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block3479);
            	    statement227=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement227.getTree());

            	    }
            	    break;

            	default :
            	    break loop76;
                }
            } while (true);

            RBRACE228=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_block3483); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE228);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: actionContextStatement, statement, localVariableDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 520:3: -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
            {
                // BaseElement.g:520:6: ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // BaseElement.g:520:14: ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // BaseElement.g:520:27: ( actionContextStatement )*
                while ( stream_actionContextStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionContextStatement.nextTree());

                }
                stream_actionContextStatement.reset();
                // BaseElement.g:520:51: ( localVariableDeclaration )*
                while ( stream_localVariableDeclaration.hasNext() ) {
                    adaptor.addChild(root_2, stream_localVariableDeclaration.nextTree());

                }
                stream_localVariableDeclaration.reset();
                // BaseElement.g:520:77: ( statement )*
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

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "block"

    public static class actionContextStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionContextStatement"
    // BaseElement.g:523:1: actionContextStatement : (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) ) ;
    public final Event_BaseElement.actionContextStatement_return actionContextStatement() throws RecognitionException {
        Event_BaseElement.actionContextStatement_return retval = new Event_BaseElement.actionContextStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token t=null;
        Token SEMICOLON229=null;
        Event_BaseElement.methodName_return m = null;

        Event_BaseElement.identifier_return id = null;

        Event_BaseElement.name_return nm = null;


        EventASTNode t_tree=null;
        EventASTNode SEMICOLON229_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_225=new RewriteRuleTokenStream(adaptor,"token 225");
        RewriteRuleTokenStream stream_226=new RewriteRuleTokenStream(adaptor,"token 226");
        RewriteRuleTokenStream stream_227=new RewriteRuleTokenStream(adaptor,"token 227");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_methodName=new RewriteRuleSubtreeStream(adaptor,"rule methodName");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // BaseElement.g:524:2: ( (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) ) )
            // BaseElement.g:524:4: (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON
            {
            // BaseElement.g:524:4: (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==227) ) {
                alt78=1;
            }
            else if ( ((LA78_0>=225 && LA78_0<=226)) ) {
                alt78=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // BaseElement.g:524:5: t= 'call' m= methodName id= identifier
                    {
                    t=(Token)match(input,227,FOLLOW_227_in_actionContextStatement3524); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_227.add(t);

                    pushFollow(FOLLOW_methodName_in_actionContextStatement3528);
                    m=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodName.add(m.getTree());
                    pushFollow(FOLLOW_identifier_in_actionContextStatement3532);
                    id=identifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
                    if ( state.backtracking==0 ) {
                       pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode)m.getTree()); 
                    }

                    }
                    break;
                case 2 :
                    // BaseElement.g:525:3: ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) )
                    {
                    // BaseElement.g:525:3: ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) )
                    // BaseElement.g:525:4: (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier )
                    {
                    // BaseElement.g:525:4: (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier )
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==225) ) {
                        alt77=1;
                    }
                    else if ( (LA77_0==226) ) {
                        alt77=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 0, input);

                        throw nvae;
                    }
                    switch (alt77) {
                        case 1 :
                            // BaseElement.g:525:5: t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier
                            {
                            t=(Token)match(input,225,FOLLOW_225_in_actionContextStatement3544); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_225.add(t);

                            if ( state.backtracking==0 ) {
                               setTypeReference(true); 
                            }
                            pushFollow(FOLLOW_name_in_actionContextStatement3550);
                            nm=name(BaseRulesParser.TYPE_REF);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_name.add(nm.getTree());
                            if ( state.backtracking==0 ) {
                               setTypeReference(false); 
                            }
                            pushFollow(FOLLOW_identifier_in_actionContextStatement3557);
                            id=identifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
                            if ( state.backtracking==0 ) {
                               pushDefineName((RulesASTNode) id.getTree(), (RulesASTNode) nm.getTree()); 
                            }

                            }
                            break;
                        case 2 :
                            // BaseElement.g:526:5: t= 'modify' id= identifier
                            {
                            t=(Token)match(input,226,FOLLOW_226_in_actionContextStatement3567); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_226.add(t);

                            pushFollow(FOLLOW_identifier_in_actionContextStatement3571);
                            id=identifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;

            }

            SEMICOLON229=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_actionContextStatement3578); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON229);



            // AST REWRITE
            // elements: t, nm, id, m
            // token labels: t
            // rule labels: id, retval, m, nm
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleTokenStream stream_t=new RewriteRuleTokenStream(adaptor,"token t",t);
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);
            RewriteRuleSubtreeStream stream_nm=new RewriteRuleSubtreeStream(adaptor,"rule nm",nm!=null?nm.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 528:3: -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) )
            {
                // BaseElement.g:528:6: ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ACTION_CONTEXT_STATEMENT, "actionContext"), root_1);

                // BaseElement.g:528:50: ^( ACTION_TYPE[\"action type\"] $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ACTION_TYPE, "action type"), root_2);

                adaptor.addChild(root_2, stream_t.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:528:83: ^( TYPE[\"type\"] ( $m)? ( $nm)? )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "type"), root_2);

                // BaseElement.g:528:98: ( $m)?
                if ( stream_m.hasNext() ) {
                    adaptor.addChild(root_2, stream_m.nextTree());

                }
                stream_m.reset();
                // BaseElement.g:528:102: ( $nm)?
                if ( stream_nm.hasNext() ) {
                    adaptor.addChild(root_2, stream_nm.nextTree());

                }
                stream_nm.reset();

                adaptor.addChild(root_1, root_2);
                }
                // BaseElement.g:528:108: ^( NAME[\"name\"] $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "actionContextStatement"

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
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_BaseElement

    // $ANTLR start synpred10_BaseElement
    public final void synpred10_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:409:3: ( localInitializerArrayLiteral ( ';' | ',' ) )
        // BaseElement.g:409:4: localInitializerArrayLiteral ( ';' | ',' )
        {
        pushFollow(FOLLOW_localInitializerArrayLiteral_in_synpred10_BaseElement2795);
        localInitializerArrayLiteral();

        state._fsp--;
        if (state.failed) return ;
        if ( input.LA(1)==SEMICOLON||input.LA(1)==236 ) {
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
    // $ANTLR end synpred10_BaseElement

    // $ANTLR start synpred11_BaseElement
    public final void synpred11_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:488:4: ({...}? actionContextStatement )
        // BaseElement.g:488:5: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred11_BaseElement", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred11_BaseElement3316);
        actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_BaseElement

    // $ANTLR start synpred12_BaseElement
    public final void synpred12_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:489:4: ( type identifier )
        // BaseElement.g:489:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred12_BaseElement3326);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred12_BaseElement3328);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_BaseElement

    // $ANTLR start synpred13_BaseElement
    public final void synpred13_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:518:5: ({...}? actionContextStatement )
        // BaseElement.g:518:6: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred13_BaseElement", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred13_BaseElement3460);
        actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_BaseElement

    // $ANTLR start synpred14_BaseElement
    public final void synpred14_BaseElement_fragment() throws RecognitionException {   
        // BaseElement.g:518:87: ( type identifier )
        // BaseElement.g:518:88: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred14_BaseElement3469);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred14_BaseElement3471);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_BaseElement

    // Delegated rules

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


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA30 dfa30 = new DFA30(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA50 dfa50 = new DFA50(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA76 dfa76 = new DFA76(this);
    static final String DFA8_eotS =
        "\26\uffff";
    static final String DFA8_eofS =
        "\26\uffff";
    static final String DFA8_minS =
        "\1\152\25\uffff";
    static final String DFA8_maxS =
        "\1\u00fb\25\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\14\1\2\2\1\3\6\uffff";
    static final String DFA8_specialS =
        "\1\0\25\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\12\3\uffff\7\17\1\16\1\uffff\1\1\1\2\3\uffff\1\3\2\uffff"+
            "\1\5\1\6\1\10\2\4\2\7\13\uffff\1\11\1\uffff\1\15\44\uffff\61"+
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

                        else if ( ((LA8_0>=184 && LA8_0<=232)) ) {s = 11;}

                        else if ( ((LA8_0>=248 && LA8_0<=251)) ) {s = 12;}

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
        "\1\u00fb\25\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\14\1\2\2\1\3\6\uffff";
    static final String DFA10_specialS =
        "\1\0\25\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\12\3\uffff\7\17\1\16\1\uffff\1\1\1\2\3\uffff\1\3\2\uffff"+
            "\1\5\1\6\1\10\2\4\2\7\13\uffff\1\11\1\uffff\1\15\44\uffff\61"+
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

                        else if ( ((LA10_0>=184 && LA10_0<=232)) ) {s = 11;}

                        else if ( ((LA10_0>=248 && LA10_0<=251)) ) {s = 12;}

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
        "\1\u00fb\6\uffff\3\0\4\uffff";
    static final String DFA30_acceptS =
        "\1\uffff\1\1\3\uffff\1\1\1\2\3\uffff\1\3\1\4\1\5\1\6";
    static final String DFA30_specialS =
        "\7\uffff\1\0\1\1\1\2\4\uffff}>";
    static final String[] DFA30_transitionS = {
            "\1\7\24\uffff\2\1\1\5\4\1\13\uffff\1\6\46\uffff\61\10\17\uffff"+
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
        "\1\u00fb\2\0\4\uffff\1\0\3\uffff";
    static final String DFA43_acceptS =
        "\3\uffff\1\2\6\uffff\1\1";
    static final String DFA43_specialS =
        "\1\uffff\1\0\1\1\4\uffff\1\2\3\uffff}>";
    static final String[] DFA43_transitionS = {
            "\1\1\24\uffff\2\3\1\2\4\3\13\uffff\1\3\46\uffff\61\7\17\uffff"+
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
        "\1\u00fb\2\0\4\uffff\1\0\3\uffff";
    static final String DFA50_acceptS =
        "\3\uffff\1\2\6\uffff\1\1";
    static final String DFA50_specialS =
        "\1\uffff\1\0\1\1\4\uffff\1\2\3\uffff}>";
    static final String[] DFA50_transitionS = {
            "\1\1\24\uffff\2\3\1\2\4\3\13\uffff\1\3\46\uffff\61\7\17\uffff"+
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
        "\1\u00fb\3\u00e8\1\uffff\1\u00e8\1\uffff\1\u00e8\1\u00fb\2\uffff"+
        "\1\u00fb\3\u00e8";
    static final String DFA54_acceptS =
        "\4\uffff\1\2\1\uffff\1\3\2\uffff\2\1\4\uffff";
    static final String DFA54_specialS =
        "\1\uffff\1\3\1\1\1\4\1\uffff\1\0\6\uffff\1\5\1\6\1\2}>";
    static final String[] DFA54_transitionS = {
            "\1\1\1\6\23\uffff\2\4\1\2\4\4\13\uffff\1\4\46\uffff\61\5\17"+
            "\uffff\4\3",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\44\uffff\61\12",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\44\uffff\61\12",
            "\1\11\12\uffff\1\4\13\uffff\1\12\21\uffff\1\13\44\uffff\61"+
            "\12",
            "",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\44\uffff\61\12",
            "",
            "\1\14\26\uffff\1\15\66\uffff\61\15",
            "\1\4\3\uffff\10\4\1\uffff\2\4\3\uffff\1\4\2\uffff\7\4\13\uffff"+
            "\1\4\1\uffff\1\4\1\16\43\uffff\61\4\17\uffff\4\4",
            "",
            "",
            "\1\4\3\uffff\10\4\1\uffff\2\4\3\uffff\1\4\2\uffff\7\4\13\uffff"+
            "\1\4\1\uffff\1\4\1\16\43\uffff\61\4\17\uffff\4\4",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\44\uffff\61\12",
            "\1\11\12\uffff\1\4\7\uffff\1\7\1\4\2\uffff\1\12\5\uffff\10"+
            "\4\2\uffff\1\4\1\uffff\1\10\44\uffff\61\12",
            "\1\11\12\uffff\1\4\13\uffff\1\12\21\uffff\1\4\44\uffff\61"+
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

                        else if ( (LA54_5==LBRACE||LA54_5==ANNOTATE||(LA54_5>=INCR && LA54_5<=MOD_EQUAL)||LA54_5==LPAREN) ) {s = 4;}

                        else if ( (LA54_5==LBRACK) ) {s = 8;}

                        else if ( (LA54_5==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_5==NullLiteral||(LA54_5>=184 && LA54_5<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA54_2 = input.LA(1);

                         
                        int index54_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_2==DOT) ) {s = 7;}

                        else if ( (LA54_2==LBRACE||LA54_2==ANNOTATE||(LA54_2>=INCR && LA54_2<=MOD_EQUAL)||LA54_2==LPAREN) ) {s = 4;}

                        else if ( (LA54_2==LBRACK) ) {s = 8;}

                        else if ( (LA54_2==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_2==NullLiteral||(LA54_2>=184 && LA54_2<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA54_14 = input.LA(1);

                         
                        int index54_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_14==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_14==NullLiteral||(LA54_14>=184 && LA54_14<=232)) && (synpred9_BaseElement())) {s = 10;}

                        else if ( (LA54_14==LBRACE||LA54_14==LBRACK) ) {s = 4;}

                         
                        input.seek(index54_14);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA54_1 = input.LA(1);

                         
                        int index54_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_1==DOT) ) {s = 7;}

                        else if ( (LA54_1==LBRACE||LA54_1==ANNOTATE||(LA54_1>=INCR && LA54_1<=MOD_EQUAL)||LA54_1==LPAREN) ) {s = 4;}

                        else if ( (LA54_1==LBRACK) ) {s = 8;}

                        else if ( (LA54_1==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_1==NullLiteral||(LA54_1>=184 && LA54_1<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_1);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA54_3 = input.LA(1);

                         
                        int index54_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_3==LBRACK) ) {s = 11;}

                        else if ( (LA54_3==LBRACE) ) {s = 4;}

                        else if ( (LA54_3==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_3==NullLiteral||(LA54_3>=184 && LA54_3<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_3);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA54_12 = input.LA(1);

                         
                        int index54_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_12==LBRACE||LA54_12==ANNOTATE||(LA54_12>=INCR && LA54_12<=MOD_EQUAL)||LA54_12==LPAREN) ) {s = 4;}

                        else if ( (LA54_12==DOT) ) {s = 7;}

                        else if ( (LA54_12==LBRACK) ) {s = 8;}

                        else if ( (LA54_12==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_12==NullLiteral||(LA54_12>=184 && LA54_12<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA54_13 = input.LA(1);

                         
                        int index54_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA54_13==LBRACK) ) {s = 8;}

                        else if ( (LA54_13==DOT) ) {s = 7;}

                        else if ( (LA54_13==LBRACE||LA54_13==ANNOTATE||(LA54_13>=INCR && LA54_13<=MOD_EQUAL)||LA54_13==LPAREN) ) {s = 4;}

                        else if ( (LA54_13==Identifier) && (synpred9_BaseElement())) {s = 9;}

                        else if ( (LA54_13==NullLiteral||(LA54_13>=184 && LA54_13<=232)) && (synpred9_BaseElement())) {s = 10;}

                         
                        input.seek(index54_13);
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
    static final String DFA64_eotS =
        "\27\uffff";
    static final String DFA64_eofS =
        "\27\uffff";
    static final String DFA64_minS =
        "\1\152\1\0\25\uffff";
    static final String DFA64_maxS =
        "\1\u00fb\1\0\25\uffff";
    static final String DFA64_acceptS =
        "\2\uffff\1\2\23\uffff\1\1";
    static final String DFA64_specialS =
        "\1\uffff\1\0\25\uffff}>";
    static final String[] DFA64_transitionS = {
            "\1\2\3\uffff\7\2\1\1\1\uffff\2\2\3\uffff\1\2\2\uffff\7\2\13"+
            "\uffff\1\2\1\uffff\1\2\44\uffff\61\2\17\uffff\4\2",
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

    static final short[] DFA64_eot = DFA.unpackEncodedString(DFA64_eotS);
    static final short[] DFA64_eof = DFA.unpackEncodedString(DFA64_eofS);
    static final char[] DFA64_min = DFA.unpackEncodedStringToUnsignedChars(DFA64_minS);
    static final char[] DFA64_max = DFA.unpackEncodedStringToUnsignedChars(DFA64_maxS);
    static final short[] DFA64_accept = DFA.unpackEncodedString(DFA64_acceptS);
    static final short[] DFA64_special = DFA.unpackEncodedString(DFA64_specialS);
    static final short[][] DFA64_transition;

    static {
        int numStates = DFA64_transitionS.length;
        DFA64_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA64_transition[i] = DFA.unpackEncodedString(DFA64_transitionS[i]);
        }
    }

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = DFA64_eot;
            this.eof = DFA64_eof;
            this.min = DFA64_min;
            this.max = DFA64_max;
            this.accept = DFA64_accept;
            this.special = DFA64_special;
            this.transition = DFA64_transition;
        }
        public String getDescription() {
            return "409:2: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA64_1 = input.LA(1);

                         
                        int index64_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_BaseElement()) ) {s = 22;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index64_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 64, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA72_eotS =
        "\50\uffff";
    static final String DFA72_eofS =
        "\50\uffff";
    static final String DFA72_minS =
        "\7\152\1\uffff\1\152\1\uffff\6\152\2\153\2\uffff\5\152\2\153\3"+
        "\uffff\1\152\2\153\1\0\2\152\1\0\2\152\1\uffff";
    static final String DFA72_maxS =
        "\1\u00fb\6\u00e8\1\uffff\1\u00e8\1\uffff\1\u00e8\1\u00fb\6\u00ec"+
        "\2\uffff\1\u00fb\4\u00e8\2\153\3\uffff\1\u00e8\2\153\1\0\2\u00e8"+
        "\1\0\2\u00e8\1\uffff";
    static final String DFA72_acceptS =
        "\7\uffff\1\3\1\uffff\1\4\10\uffff\2\2\7\uffff\3\2\11\uffff\1\1";
    static final String DFA72_specialS =
        "\1\uffff\1\27\1\12\1\2\1\14\1\17\1\25\1\uffff\1\16\3\uffff\1\3"+
        "\1\26\1\10\1\4\1\15\1\11\3\uffff\1\5\1\1\1\30\1\24\1\23\1\0\3\uffff"+
        "\1\22\1\20\1\33\1\21\1\6\1\32\1\7\1\13\1\31\1\uffff}>";
    static final String[] DFA72_transitionS = {
            "\1\4\1\7\11\uffff\1\7\11\uffff\2\7\1\5\4\7\1\11\12\uffff\1"+
            "\7\46\uffff\51\10\1\2\1\3\1\1\5\10\4\uffff\5\7\1\uffff\3\7\2"+
            "\uffff\4\6",
            "\1\14\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\15\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\15",
            "\1\16\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\17\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\17",
            "\1\20\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\21\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\21",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\7\13\uffff\1\23\21\uffff\1\24\44\uffff\61"+
            "\23",
            "",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\23",
            "",
            "\1\25\26\uffff\1\26\66\uffff\61\26",
            "\1\7\3\uffff\10\7\1\uffff\2\7\3\uffff\1\7\2\uffff\7\7\13\uffff"+
            "\1\7\1\uffff\1\7\1\27\43\uffff\61\7\17\uffff\4\7",
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\56\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\56\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\56\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\56\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\41\35\uffff\1\33\142\uffff\1\34",
            "\1\41\35\uffff\1\33\142\uffff\1\34",
            "",
            "",
            "\1\7\3\uffff\10\7\1\uffff\2\7\3\uffff\1\7\2\uffff\7\7\13\uffff"+
            "\1\7\1\uffff\1\7\1\27\43\uffff\61\7\17\uffff\4\7",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\7\13\uffff\1\23\21\uffff\1\7\44\uffff\61"+
            "\23",
            "\1\42\26\uffff\1\43\66\uffff\61\43",
            "\1\44",
            "\1\44",
            "",
            "",
            "",
            "\1\45\26\uffff\1\46\66\uffff\61\46",
            "\1\44",
            "\1\44",
            "\1\uffff",
            "\1\31\22\uffff\1\30\3\uffff\1\32\66\uffff\61\32",
            "\1\31\22\uffff\1\30\3\uffff\1\32\66\uffff\61\32",
            "\1\uffff",
            "\1\37\22\uffff\1\36\3\uffff\1\40\66\uffff\61\40",
            "\1\37\22\uffff\1\36\3\uffff\1\40\66\uffff\61\40",
            ""
    };

    static final short[] DFA72_eot = DFA.unpackEncodedString(DFA72_eotS);
    static final short[] DFA72_eof = DFA.unpackEncodedString(DFA72_eofS);
    static final char[] DFA72_min = DFA.unpackEncodedStringToUnsignedChars(DFA72_minS);
    static final char[] DFA72_max = DFA.unpackEncodedStringToUnsignedChars(DFA72_maxS);
    static final short[] DFA72_accept = DFA.unpackEncodedString(DFA72_acceptS);
    static final short[] DFA72_special = DFA.unpackEncodedString(DFA72_specialS);
    static final short[][] DFA72_transition;

    static {
        int numStates = DFA72_transitionS.length;
        DFA72_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA72_transition[i] = DFA.unpackEncodedString(DFA72_transitionS[i]);
        }
    }

    class DFA72 extends DFA {

        public DFA72(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 72;
            this.eot = DFA72_eot;
            this.eof = DFA72_eof;
            this.min = DFA72_min;
            this.max = DFA72_max;
            this.accept = DFA72_accept;
            this.special = DFA72_special;
            this.transition = DFA72_transition;
        }
        public String getDescription() {
            return "487:1: thenStatement : ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA72_26 = input.LA(1);

                         
                        int index72_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_26==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index72_26);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA72_22 = input.LA(1);

                         
                        int index72_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_22==LBRACE||LA72_22==ANNOTATE||(LA72_22>=INCR && LA72_22<=MOD_EQUAL)||LA72_22==LPAREN) ) {s = 7;}

                        else if ( (LA72_22==DOT) ) {s = 10;}

                        else if ( (LA72_22==LBRACK) ) {s = 11;}

                        else if ( (LA72_22==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_22==NullLiteral||(LA72_22>=184 && LA72_22<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_22);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA72_3 = input.LA(1);

                         
                        int index72_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_3==Identifier) && (( isInActionContextBlock() ))) {s = 16;}

                        else if ( (LA72_3==NullLiteral||(LA72_3>=184 && LA72_3<=232)) && (( isInActionContextBlock() ))) {s = 17;}

                        else if ( (LA72_3==DOT) ) {s = 10;}

                        else if ( (LA72_3==LBRACK) ) {s = 11;}

                        else if ( (LA72_3==LBRACE||LA72_3==ANNOTATE||(LA72_3>=INCR && LA72_3<=MOD_EQUAL)||LA72_3==LPAREN) ) {s = 7;}

                         
                        input.seek(index72_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA72_12 = input.LA(1);

                         
                        int index72_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_12==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA72_12==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA72_12==NullLiteral||(LA72_12>=184 && LA72_12<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA72_12==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_12==236) && (synpred12_BaseElement())) {s = 28;}

                        else if ( (LA72_12==SEMICOLON) && (synpred12_BaseElement())) {s = 29;}

                         
                        input.seek(index72_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA72_15 = input.LA(1);

                         
                        int index72_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_15==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA72_15==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA72_15==NullLiteral||(LA72_15>=184 && LA72_15<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA72_15==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_15==236) && (synpred12_BaseElement())) {s = 28;}

                        else if ( (LA72_15==SEMICOLON) && (synpred12_BaseElement())) {s = 29;}

                         
                        input.seek(index72_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA72_21 = input.LA(1);

                         
                        int index72_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_21==LBRACK) ) {s = 11;}

                        else if ( (LA72_21==DOT) ) {s = 10;}

                        else if ( (LA72_21==LBRACE||LA72_21==ANNOTATE||(LA72_21>=INCR && LA72_21<=MOD_EQUAL)||LA72_21==LPAREN) ) {s = 7;}

                        else if ( (LA72_21==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_21==NullLiteral||(LA72_21>=184 && LA72_21<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_21);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA72_34 = input.LA(1);

                         
                        int index72_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_34==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA72_34==NullLiteral||(LA72_34>=184 && LA72_34<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA72_34==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index72_34);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA72_36 = input.LA(1);

                         
                        int index72_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_BaseElement()) ) {s = 39;}

                        else if ( (( isInActionContextBlock() )) ) {s = 7;}

                         
                        input.seek(index72_36);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA72_14 = input.LA(1);

                         
                        int index72_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_14==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA72_14==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA72_14==NullLiteral||(LA72_14>=184 && LA72_14<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA72_14==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_14==236) && (synpred12_BaseElement())) {s = 28;}

                        else if ( (LA72_14==SEMICOLON) && (synpred12_BaseElement())) {s = 29;}

                         
                        input.seek(index72_14);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA72_17 = input.LA(1);

                         
                        int index72_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_17==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_17==236) && (synpred12_BaseElement())) {s = 28;}

                        else if ( (LA72_17==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                         
                        input.seek(index72_17);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA72_2 = input.LA(1);

                         
                        int index72_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_2==DOT) ) {s = 10;}

                        else if ( (LA72_2==LBRACK) ) {s = 11;}

                        else if ( (LA72_2==LBRACE||LA72_2==ANNOTATE||(LA72_2>=INCR && LA72_2<=MOD_EQUAL)||LA72_2==LPAREN) ) {s = 7;}

                        else if ( (LA72_2==Identifier) && (( isInActionContextBlock() ))) {s = 14;}

                        else if ( (LA72_2==NullLiteral||(LA72_2>=184 && LA72_2<=232)) && (( isInActionContextBlock() ))) {s = 15;}

                         
                        input.seek(index72_2);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA72_37 = input.LA(1);

                         
                        int index72_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_37==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA72_37==NullLiteral||(LA72_37>=184 && LA72_37<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA72_37==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index72_37);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA72_4 = input.LA(1);

                         
                        int index72_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_4==DOT) ) {s = 10;}

                        else if ( (LA72_4==LBRACE||LA72_4==ANNOTATE||(LA72_4>=INCR && LA72_4<=MOD_EQUAL)||LA72_4==LPAREN) ) {s = 7;}

                        else if ( (LA72_4==LBRACK) ) {s = 11;}

                        else if ( (LA72_4==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_4==NullLiteral||(LA72_4>=184 && LA72_4<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_4);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA72_16 = input.LA(1);

                         
                        int index72_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_16==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA72_16==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_16==236) && (synpred12_BaseElement())) {s = 28;}

                         
                        input.seek(index72_16);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA72_8 = input.LA(1);

                         
                        int index72_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_8==DOT) ) {s = 10;}

                        else if ( (LA72_8==LBRACK) ) {s = 11;}

                        else if ( (LA72_8==LBRACE||LA72_8==ANNOTATE||(LA72_8>=INCR && LA72_8<=MOD_EQUAL)||LA72_8==LPAREN) ) {s = 7;}

                        else if ( (LA72_8==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_8==NullLiteral||(LA72_8>=184 && LA72_8<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_8);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA72_5 = input.LA(1);

                         
                        int index72_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_5==DOT) ) {s = 10;}

                        else if ( (LA72_5==LBRACK) ) {s = 11;}

                        else if ( (LA72_5==LBRACE||LA72_5==ANNOTATE||(LA72_5>=INCR && LA72_5<=MOD_EQUAL)||LA72_5==LPAREN) ) {s = 7;}

                        else if ( (LA72_5==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_5==NullLiteral||(LA72_5>=184 && LA72_5<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_5);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA72_31 = input.LA(1);

                         
                        int index72_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_31==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index72_31);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA72_33 = input.LA(1);

                         
                        int index72_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_BaseElement()) ) {s = 39;}

                        else if ( (synpred12_BaseElement()) ) {s = 29;}

                        else if ( (( isInActionContextBlock() )) ) {s = 7;}

                         
                        input.seek(index72_33);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA72_30 = input.LA(1);

                         
                        int index72_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_30==Identifier) && (( isInActionContextBlock() ))) {s = 37;}

                        else if ( (LA72_30==NullLiteral||(LA72_30>=184 && LA72_30<=232)) && (( isInActionContextBlock() ))) {s = 38;}

                         
                        input.seek(index72_30);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA72_25 = input.LA(1);

                         
                        int index72_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_25==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index72_25);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA72_24 = input.LA(1);

                         
                        int index72_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_24==Identifier) && (( isInActionContextBlock() ))) {s = 34;}

                        else if ( (LA72_24==NullLiteral||(LA72_24>=184 && LA72_24<=232)) && (( isInActionContextBlock() ))) {s = 35;}

                         
                        input.seek(index72_24);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA72_6 = input.LA(1);

                         
                        int index72_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_6==LBRACK) ) {s = 20;}

                        else if ( (LA72_6==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_6==NullLiteral||(LA72_6>=184 && LA72_6<=232)) && (synpred12_BaseElement())) {s = 19;}

                        else if ( (LA72_6==LBRACE) ) {s = 7;}

                         
                        input.seek(index72_6);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA72_13 = input.LA(1);

                         
                        int index72_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_13==ASSIGN) && (synpred12_BaseElement())) {s = 27;}

                        else if ( (LA72_13==236) && (synpred12_BaseElement())) {s = 28;}

                        else if ( (LA72_13==SEMICOLON) && (synpred12_BaseElement())) {s = 29;}

                        else if ( (LA72_13==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA72_13==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA72_13==NullLiteral||(LA72_13>=184 && LA72_13<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                         
                        input.seek(index72_13);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA72_1 = input.LA(1);

                         
                        int index72_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_1==DOT) ) {s = 10;}

                        else if ( (LA72_1==LBRACK) ) {s = 11;}

                        else if ( (LA72_1==LBRACE||LA72_1==ANNOTATE||(LA72_1>=INCR && LA72_1<=MOD_EQUAL)||LA72_1==LPAREN) ) {s = 7;}

                        else if ( (LA72_1==Identifier) && (( isInActionContextBlock() ))) {s = 12;}

                        else if ( (LA72_1==NullLiteral||(LA72_1>=184 && LA72_1<=232)) && (( isInActionContextBlock() ))) {s = 13;}

                         
                        input.seek(index72_1);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA72_23 = input.LA(1);

                         
                        int index72_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_23==LBRACE||LA72_23==LBRACK) ) {s = 7;}

                        else if ( (LA72_23==Identifier) && (synpred12_BaseElement())) {s = 18;}

                        else if ( (LA72_23==NullLiteral||(LA72_23>=184 && LA72_23<=232)) && (synpred12_BaseElement())) {s = 19;}

                         
                        input.seek(index72_23);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA72_38 = input.LA(1);

                         
                        int index72_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_38==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA72_38==NullLiteral||(LA72_38>=184 && LA72_38<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA72_38==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index72_38);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA72_35 = input.LA(1);

                         
                        int index72_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_35==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA72_35==NullLiteral||(LA72_35>=184 && LA72_35<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA72_35==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index72_35);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA72_32 = input.LA(1);

                         
                        int index72_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA72_32==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index72_32);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 72, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA74_eotS =
        "\13\uffff";
    static final String DFA74_eofS =
        "\13\uffff";
    static final String DFA74_minS =
        "\1\152\2\uffff\1\152\4\uffff\2\152\1\uffff";
    static final String DFA74_maxS =
        "\1\u00fb\2\uffff\1\u00e8\4\uffff\2\u00e8\1\uffff";
    static final String DFA74_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6\2\uffff\1\7";
    static final String DFA74_specialS =
        "\3\uffff\1\2\4\uffff\1\1\1\0\1\uffff}>";
    static final String[] DFA74_transitionS = {
            "\1\2\1\1\23\uffff\7\2\13\uffff\1\2\46\uffff\51\2\1\10\1\11"+
            "\1\3\5\2\4\uffff\1\4\1\5\1\6\1\7\7\uffff\4\2",
            "",
            "",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\44\uffff\61\12",
            "",
            "",
            "",
            "",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\44\uffff\61\12",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\44\uffff\61\12",
            ""
    };

    static final short[] DFA74_eot = DFA.unpackEncodedString(DFA74_eotS);
    static final short[] DFA74_eof = DFA.unpackEncodedString(DFA74_eofS);
    static final char[] DFA74_min = DFA.unpackEncodedStringToUnsignedChars(DFA74_minS);
    static final char[] DFA74_max = DFA.unpackEncodedStringToUnsignedChars(DFA74_maxS);
    static final short[] DFA74_accept = DFA.unpackEncodedString(DFA74_acceptS);
    static final short[] DFA74_special = DFA.unpackEncodedString(DFA74_specialS);
    static final short[][] DFA74_transition;

    static {
        int numStates = DFA74_transitionS.length;
        DFA74_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA74_transition[i] = DFA.unpackEncodedString(DFA74_transitionS[i]);
        }
    }

    class DFA74 extends DFA {

        public DFA74(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 74;
            this.eot = DFA74_eot;
            this.eof = DFA74_eof;
            this.min = DFA74_min;
            this.max = DFA74_max;
            this.accept = DFA74_accept;
            this.special = DFA74_special;
            this.transition = DFA74_transition;
        }
        public String getDescription() {
            return "498:1: lineStatement : ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA74_9 = input.LA(1);

                         
                        int index74_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA74_9==LBRACE||(LA74_9>=DOT && LA74_9<=ANNOTATE)||(LA74_9>=INCR && LA74_9<=MOD_EQUAL)||LA74_9==LPAREN||LA74_9==LBRACK) ) {s = 2;}

                        else if ( (LA74_9==Identifier||LA74_9==NullLiteral||(LA74_9>=184 && LA74_9<=232)) && (( isInActionContextBlock() ))) {s = 10;}

                         
                        input.seek(index74_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA74_8 = input.LA(1);

                         
                        int index74_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA74_8==LBRACE||(LA74_8>=DOT && LA74_8<=ANNOTATE)||(LA74_8>=INCR && LA74_8<=MOD_EQUAL)||LA74_8==LPAREN||LA74_8==LBRACK) ) {s = 2;}

                        else if ( (LA74_8==Identifier||LA74_8==NullLiteral||(LA74_8>=184 && LA74_8<=232)) && (( isInActionContextBlock() ))) {s = 10;}

                         
                        input.seek(index74_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA74_3 = input.LA(1);

                         
                        int index74_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA74_3==LBRACE||(LA74_3>=DOT && LA74_3<=ANNOTATE)||(LA74_3>=INCR && LA74_3<=MOD_EQUAL)||LA74_3==LPAREN||LA74_3==LBRACK) ) {s = 2;}

                        else if ( (LA74_3==Identifier||LA74_3==NullLiteral||(LA74_3>=184 && LA74_3<=232)) && (( isInActionContextBlock() ))) {s = 10;}

                         
                        input.seek(index74_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 74, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA76_eotS =
        "\50\uffff";
    static final String DFA76_eofS =
        "\50\uffff";
    static final String DFA76_minS =
        "\1\152\1\uffff\6\152\1\uffff\7\152\2\153\2\uffff\5\152\2\153\3"+
        "\uffff\1\152\2\153\1\0\2\152\1\0\2\152\1\uffff";
    static final String DFA76_maxS =
        "\1\u00fb\1\uffff\6\u00e8\1\uffff\2\u00e8\1\u00fb\6\u00ec\2\uffff"+
        "\1\u00fb\4\u00e8\2\153\3\uffff\1\u00e8\2\153\1\0\2\u00e8\1\0\2\u00e8"+
        "\1\uffff";
    static final String DFA76_acceptS =
        "\1\uffff\1\4\6\uffff\1\3\11\uffff\2\2\7\uffff\3\2\11\uffff\1\1";
    static final String DFA76_specialS =
        "\2\uffff\1\26\1\11\1\2\1\13\1\17\1\24\1\uffff\1\15\2\uffff\1\3"+
        "\1\25\1\7\1\4\1\14\1\10\3\uffff\1\5\1\1\1\27\1\23\1\22\1\0\3\uffff"+
        "\1\21\1\20\1\33\1\32\1\6\1\31\1\16\1\12\1\30\1\uffff}>";
    static final String[] DFA76_transitionS = {
            "\1\5\1\10\11\uffff\1\10\1\1\10\uffff\2\10\1\6\4\10\13\uffff"+
            "\1\10\46\uffff\51\11\1\3\1\4\1\2\5\11\4\uffff\5\10\1\uffff\3"+
            "\10\2\uffff\4\7",
            "",
            "\1\14\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\15\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\15",
            "\1\16\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\17\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\17",
            "\1\20\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\21\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\21",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\10\13\uffff\1\23\21\uffff\1\24\44\uffff\61"+
            "\23",
            "",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\23",
            "\1\25\26\uffff\1\26\66\uffff\61\26",
            "\1\10\3\uffff\10\10\1\uffff\2\10\3\uffff\1\10\2\uffff\7\10"+
            "\13\uffff\1\10\1\uffff\1\10\1\27\43\uffff\61\10\17\uffff\4\10",
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\56\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\56\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\56\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\56\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\41\35\uffff\1\33\142\uffff\1\34",
            "\1\41\35\uffff\1\33\142\uffff\1\34",
            "",
            "",
            "\1\10\3\uffff\10\10\1\uffff\2\10\3\uffff\1\10\2\uffff\7\10"+
            "\13\uffff\1\10\1\uffff\1\10\1\27\43\uffff\61\10\17\uffff\4\10",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\10\7\uffff\1\12\1\10\2\uffff\1\23\5\uffff"+
            "\10\10\2\uffff\1\10\1\uffff\1\13\44\uffff\61\23",
            "\1\22\12\uffff\1\10\13\uffff\1\23\21\uffff\1\10\44\uffff\61"+
            "\23",
            "\1\42\26\uffff\1\43\66\uffff\61\43",
            "\1\44",
            "\1\44",
            "",
            "",
            "",
            "\1\45\26\uffff\1\46\66\uffff\61\46",
            "\1\44",
            "\1\44",
            "\1\uffff",
            "\1\31\22\uffff\1\30\3\uffff\1\32\66\uffff\61\32",
            "\1\31\22\uffff\1\30\3\uffff\1\32\66\uffff\61\32",
            "\1\uffff",
            "\1\37\22\uffff\1\36\3\uffff\1\40\66\uffff\61\40",
            "\1\37\22\uffff\1\36\3\uffff\1\40\66\uffff\61\40",
            ""
    };

    static final short[] DFA76_eot = DFA.unpackEncodedString(DFA76_eotS);
    static final short[] DFA76_eof = DFA.unpackEncodedString(DFA76_eofS);
    static final char[] DFA76_min = DFA.unpackEncodedStringToUnsignedChars(DFA76_minS);
    static final char[] DFA76_max = DFA.unpackEncodedStringToUnsignedChars(DFA76_maxS);
    static final short[] DFA76_accept = DFA.unpackEncodedString(DFA76_acceptS);
    static final short[] DFA76_special = DFA.unpackEncodedString(DFA76_specialS);
    static final short[][] DFA76_transition;

    static {
        int numStates = DFA76_transitionS.length;
        DFA76_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA76_transition[i] = DFA.unpackEncodedString(DFA76_transitionS[i]);
        }
    }

    class DFA76 extends DFA {

        public DFA76(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 76;
            this.eot = DFA76_eot;
            this.eof = DFA76_eof;
            this.min = DFA76_min;
            this.max = DFA76_max;
            this.accept = DFA76_accept;
            this.special = DFA76_special;
            this.transition = DFA76_transition;
        }
        public String getDescription() {
            return "()* loopback of 518:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA76_26 = input.LA(1);

                         
                        int index76_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_26==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index76_26);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA76_22 = input.LA(1);

                         
                        int index76_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_22==LBRACE||LA76_22==ANNOTATE||(LA76_22>=INCR && LA76_22<=MOD_EQUAL)||LA76_22==LPAREN) ) {s = 8;}

                        else if ( (LA76_22==DOT) ) {s = 10;}

                        else if ( (LA76_22==LBRACK) ) {s = 11;}

                        else if ( (LA76_22==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_22==NullLiteral||(LA76_22>=184 && LA76_22<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_22);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA76_4 = input.LA(1);

                         
                        int index76_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_4==Identifier) && (( isInActionContextBlock() ))) {s = 16;}

                        else if ( (LA76_4==NullLiteral||(LA76_4>=184 && LA76_4<=232)) && (( isInActionContextBlock() ))) {s = 17;}

                        else if ( (LA76_4==DOT) ) {s = 10;}

                        else if ( (LA76_4==LBRACK) ) {s = 11;}

                        else if ( (LA76_4==LBRACE||LA76_4==ANNOTATE||(LA76_4>=INCR && LA76_4<=MOD_EQUAL)||LA76_4==LPAREN) ) {s = 8;}

                         
                        input.seek(index76_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA76_12 = input.LA(1);

                         
                        int index76_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_12==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA76_12==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA76_12==NullLiteral||(LA76_12>=184 && LA76_12<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA76_12==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_12==236) && (synpred14_BaseElement())) {s = 28;}

                        else if ( (LA76_12==SEMICOLON) && (synpred14_BaseElement())) {s = 29;}

                         
                        input.seek(index76_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA76_15 = input.LA(1);

                         
                        int index76_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_15==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA76_15==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA76_15==NullLiteral||(LA76_15>=184 && LA76_15<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA76_15==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_15==236) && (synpred14_BaseElement())) {s = 28;}

                        else if ( (LA76_15==SEMICOLON) && (synpred14_BaseElement())) {s = 29;}

                         
                        input.seek(index76_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA76_21 = input.LA(1);

                         
                        int index76_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_21==LBRACK) ) {s = 11;}

                        else if ( (LA76_21==DOT) ) {s = 10;}

                        else if ( (LA76_21==LBRACE||LA76_21==ANNOTATE||(LA76_21>=INCR && LA76_21<=MOD_EQUAL)||LA76_21==LPAREN) ) {s = 8;}

                        else if ( (LA76_21==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_21==NullLiteral||(LA76_21>=184 && LA76_21<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_21);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA76_34 = input.LA(1);

                         
                        int index76_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_34==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA76_34==NullLiteral||(LA76_34>=184 && LA76_34<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA76_34==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index76_34);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA76_14 = input.LA(1);

                         
                        int index76_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_14==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA76_14==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA76_14==NullLiteral||(LA76_14>=184 && LA76_14<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA76_14==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_14==236) && (synpred14_BaseElement())) {s = 28;}

                        else if ( (LA76_14==SEMICOLON) && (synpred14_BaseElement())) {s = 29;}

                         
                        input.seek(index76_14);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA76_17 = input.LA(1);

                         
                        int index76_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_17==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA76_17==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_17==236) && (synpred14_BaseElement())) {s = 28;}

                         
                        input.seek(index76_17);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA76_3 = input.LA(1);

                         
                        int index76_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_3==DOT) ) {s = 10;}

                        else if ( (LA76_3==LBRACK) ) {s = 11;}

                        else if ( (LA76_3==LBRACE||LA76_3==ANNOTATE||(LA76_3>=INCR && LA76_3<=MOD_EQUAL)||LA76_3==LPAREN) ) {s = 8;}

                        else if ( (LA76_3==Identifier) && (( isInActionContextBlock() ))) {s = 14;}

                        else if ( (LA76_3==NullLiteral||(LA76_3>=184 && LA76_3<=232)) && (( isInActionContextBlock() ))) {s = 15;}

                         
                        input.seek(index76_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA76_37 = input.LA(1);

                         
                        int index76_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_37==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA76_37==NullLiteral||(LA76_37>=184 && LA76_37<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA76_37==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index76_37);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA76_5 = input.LA(1);

                         
                        int index76_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_5==DOT) ) {s = 10;}

                        else if ( (LA76_5==LBRACE||LA76_5==ANNOTATE||(LA76_5>=INCR && LA76_5<=MOD_EQUAL)||LA76_5==LPAREN) ) {s = 8;}

                        else if ( (LA76_5==LBRACK) ) {s = 11;}

                        else if ( (LA76_5==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_5==NullLiteral||(LA76_5>=184 && LA76_5<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_5);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA76_16 = input.LA(1);

                         
                        int index76_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_16==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA76_16==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_16==236) && (synpred14_BaseElement())) {s = 28;}

                         
                        input.seek(index76_16);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA76_9 = input.LA(1);

                         
                        int index76_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_9==DOT) ) {s = 10;}

                        else if ( (LA76_9==LBRACK) ) {s = 11;}

                        else if ( (LA76_9==LBRACE||LA76_9==ANNOTATE||(LA76_9>=INCR && LA76_9<=MOD_EQUAL)||LA76_9==LPAREN) ) {s = 8;}

                        else if ( (LA76_9==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_9==NullLiteral||(LA76_9>=184 && LA76_9<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_9);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA76_36 = input.LA(1);

                         
                        int index76_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_BaseElement()) ) {s = 39;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index76_36);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA76_6 = input.LA(1);

                         
                        int index76_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_6==DOT) ) {s = 10;}

                        else if ( (LA76_6==LBRACK) ) {s = 11;}

                        else if ( (LA76_6==LBRACE||LA76_6==ANNOTATE||(LA76_6>=INCR && LA76_6<=MOD_EQUAL)||LA76_6==LPAREN) ) {s = 8;}

                        else if ( (LA76_6==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_6==NullLiteral||(LA76_6>=184 && LA76_6<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_6);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA76_31 = input.LA(1);

                         
                        int index76_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_31==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index76_31);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA76_30 = input.LA(1);

                         
                        int index76_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_30==Identifier) && (( isInActionContextBlock() ))) {s = 37;}

                        else if ( (LA76_30==NullLiteral||(LA76_30>=184 && LA76_30<=232)) && (( isInActionContextBlock() ))) {s = 38;}

                         
                        input.seek(index76_30);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA76_25 = input.LA(1);

                         
                        int index76_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_25==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index76_25);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA76_24 = input.LA(1);

                         
                        int index76_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_24==Identifier) && (( isInActionContextBlock() ))) {s = 34;}

                        else if ( (LA76_24==NullLiteral||(LA76_24>=184 && LA76_24<=232)) && (( isInActionContextBlock() ))) {s = 35;}

                         
                        input.seek(index76_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA76_7 = input.LA(1);

                         
                        int index76_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_7==LBRACK) ) {s = 20;}

                        else if ( (LA76_7==LBRACE) ) {s = 8;}

                        else if ( (LA76_7==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_7==NullLiteral||(LA76_7>=184 && LA76_7<=232)) && (synpred14_BaseElement())) {s = 19;}

                         
                        input.seek(index76_7);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA76_13 = input.LA(1);

                         
                        int index76_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_13==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA76_13==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA76_13==NullLiteral||(LA76_13>=184 && LA76_13<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA76_13==ASSIGN) && (synpred14_BaseElement())) {s = 27;}

                        else if ( (LA76_13==236) && (synpred14_BaseElement())) {s = 28;}

                        else if ( (LA76_13==SEMICOLON) && (synpred14_BaseElement())) {s = 29;}

                         
                        input.seek(index76_13);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA76_2 = input.LA(1);

                         
                        int index76_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_2==DOT) ) {s = 10;}

                        else if ( (LA76_2==LBRACK) ) {s = 11;}

                        else if ( (LA76_2==LBRACE||LA76_2==ANNOTATE||(LA76_2>=INCR && LA76_2<=MOD_EQUAL)||LA76_2==LPAREN) ) {s = 8;}

                        else if ( (LA76_2==Identifier) && (( isInActionContextBlock() ))) {s = 12;}

                        else if ( (LA76_2==NullLiteral||(LA76_2>=184 && LA76_2<=232)) && (( isInActionContextBlock() ))) {s = 13;}

                         
                        input.seek(index76_2);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA76_23 = input.LA(1);

                         
                        int index76_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_23==Identifier) && (synpred14_BaseElement())) {s = 18;}

                        else if ( (LA76_23==NullLiteral||(LA76_23>=184 && LA76_23<=232)) && (synpred14_BaseElement())) {s = 19;}

                        else if ( (LA76_23==LBRACE||LA76_23==LBRACK) ) {s = 8;}

                         
                        input.seek(index76_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA76_38 = input.LA(1);

                         
                        int index76_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_38==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA76_38==NullLiteral||(LA76_38>=184 && LA76_38<=232)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA76_38==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index76_38);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA76_35 = input.LA(1);

                         
                        int index76_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_35==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA76_35==NullLiteral||(LA76_35>=184 && LA76_35<=232)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA76_35==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index76_35);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA76_33 = input.LA(1);

                         
                        int index76_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_BaseElement()) ) {s = 39;}

                        else if ( (synpred14_BaseElement()) ) {s = 29;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index76_33);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA76_32 = input.LA(1);

                         
                        int index76_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA76_32==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index76_32);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 76, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_183_in_mappingBlock382 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0x0000000000007FFFL});
    public static final BitSet FOLLOW_set_in_mappingBlock385 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0x0000000000007FFFL});
    public static final BitSet FOLLOW_MappingEnd_in_mappingBlock390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicateStatement_in_conditionBlock402 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_EOF_in_conditionBlock405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_standaloneExpression417 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_standaloneExpression419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keywordIdentifier_in_identifier436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keywordIdentifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_233_in_xsltLiteral678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_234_in_xsltLiteral680 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_234_in_xsltLiteral682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_235_in_voidLiteral694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicateStatement_in_predicateStatements714 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_predicate_in_predicateStatement728 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_predicateStatement730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStatement_in_predicateStatement747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_predicate763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression775 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_OR_in_expression780 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_conditionalAndExpression_in_expression783 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression797 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_AND_in_conditionalAndExpression802 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression805 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression820 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression829 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression835 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_EQ_in_equalityExpression845 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_NE_in_equalityExpression850 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression857 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_domainSpec_in_equalityExpression867 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_comparisonNoLHS_in_equalityExpression872 = new BitSet(new long[]{0x0000000000000002L,0x0000C00000000000L});
    public static final BitSet FOLLOW_EQ_in_comparisonNoLHS894 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x00000000000A0000L});
    public static final BitSet FOLLOW_NE_in_comparisonNoLHS899 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x00000000000A0000L});
    public static final BitSet FOLLOW_domainSpec_in_comparisonNoLHS909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpression_in_comparisonNoLHS915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSTANCE_OF_in_comparisonNoLHS925 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_type_in_comparisonNoLHS928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LT_in_comparisonNoLHS938 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_GT_in_comparisonNoLHS943 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_LE_in_comparisonNoLHS948 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_GE_in_comparisonNoLHS953 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_additiveExpression_in_comparisonNoLHS957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeExpression_in_domainSpec970 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setMembershipExpression_in_domainSpec974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeStart_in_rangeExpression986 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0011FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_rangeExpression990 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_rangeExpression993 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000001E003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_rangeExpression997 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000001E003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_rangeEnd_in_rangeExpression1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_rangeStart1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_rangeStart1038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RPAREN_in_rangeEnd1051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RBRACK_in_rangeEnd1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_setMembershipExpression1069 = new BitSet(new long[]{0x0000000000000000L,0x91FFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1074 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_setMembershipExpression1077 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_setMembershipExpression1081 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_RBRACE_in_setMembershipExpression1087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1116 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_set_in_relationalExpression1124 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1143 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_INSTANCE_OF_in_relationalExpression1149 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_type_in_relationalExpression1152 = new BitSet(new long[]{0x0000000000000002L,0x001F000000000000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1172 = new BitSet(new long[]{0x0000000000000002L,0x0180000000000000L});
    public static final BitSet FOLLOW_set_in_additiveExpression1177 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1188 = new BitSet(new long[]{0x0000000000000002L,0x0180000000000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1207 = new BitSet(new long[]{0x0000000000000002L,0x0E00000000000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1212 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1227 = new BitSet(new long[]{0x0000000000000002L,0x0E00000000000000L});
    public static final BitSet FOLLOW_PLUS_in_unaryExpression1249 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_MINUS_in_unaryExpression1255 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_POUND_in_unaryExpression1261 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_unaryExpression1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryPrefix_in_primaryExpression1305 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_primarySuffix_in_primaryExpression1310 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_literal_in_primaryPrefix1347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryPrefix1352 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_primaryPrefix1355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryPrefix1357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayLiteral_in_primaryPrefix1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAllocator_in_primaryPrefix1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_primaryPrefix1391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_argumentsSuffix_in_primaryPrefix1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionName_in_primaryPrefix1422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayAccessSuffix_in_primarySuffix1435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldAccessSuffix_in_primarySuffix1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_arrayAccessSuffix1453 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_arrayAccessSuffix1457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_arrayAccessSuffix1459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_fieldAccessSuffix1493 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_ANNOTATE_in_fieldAccessSuffix1498 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_fieldAccessSuffix1506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_argumentsSuffix1521 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000E003FL,0x0F0003FFFFFFFFFFL});
    public static final BitSet FOLLOW_argumentList_in_argumentsSuffix1526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_argumentsSuffix1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_argumentList1545 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_argumentList1548 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_argumentList1551 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
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
    public static final BitSet FOLLOW_assignmentOp_in_statementExpression1689 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_statementExpression1691 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_INCR_in_statementExpression1698 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_DECR_in_statementExpression1704 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statementExpression1710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_statementExpression1766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000007F80L});
    public static final BitSet FOLLOW_assignmentOp_in_statementExpression1771 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_statementExpression1773 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_INCR_in_statementExpression1780 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_DECR_in_statementExpression1786 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_statementExpression1790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_methodName_in_listStatementExpression1843 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_argumentsSuffix_in_listStatementExpression1845 = new BitSet(new long[]{0x0000000000000002L,0x6000000000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_primarySuffix_in_listStatementExpression1850 = new BitSet(new long[]{0x0000000000000000L,0x6000000000000000L,0x0000000000087F80L});
    public static final BitSet FOLLOW_assignmentOp_in_listStatementExpression1857 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_listStatementExpression1859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCR_in_listStatementExpression1866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECR_in_listStatementExpression1872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_listStatementExpression1932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000007F80L});
    public static final BitSet FOLLOW_assignmentOp_in_listStatementExpression1937 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_listStatementExpression1939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INCR_in_listStatementExpression1946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECR_in_listStatementExpression1952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_assignmentOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_237_in_returnStatement2032 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_returnStatement2035 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_returnStatement2041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_238_in_breakStatement2069 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_breakStatement2071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_239_in_continueStatement2093 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_continueStatement2095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_240_in_throwStatement2115 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_throwStatement2117 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_throwStatement2119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_listStatementExpression_in_statementExpressionList2148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_statementExpressionList2151 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000002003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_listStatementExpression_in_statementExpressionList2154 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_241_in_ifRule2167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_ifRule2169 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_ifRule2171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_ifRule2173 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_statement_in_ifRule2179 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_242_in_ifRule2185 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_statement_in_ifRule2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_243_in_whileRule2234 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_whileRule2236 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_whileRule2238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_whileRule2240 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_statement_in_whileRule2242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_244_in_forRule2272 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_forRule2274 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_localVariableDeclaration_in_forRule2288 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2292 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2294 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_emptyStatement_in_forRule2298 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_forRule2303 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_forRule2307 = new BitSet(new long[]{0x0000000000000000L,0x9180040000000000L,0xFF0000000006003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_statementExpressionList_in_forRule2311 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_forRule2315 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_statement_in_forRule2320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_245_in_tryCatchFinally2360 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_block_in_tryCatchFinally2362 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_catchRule_in_tryCatchFinally2368 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_finallyRule_in_tryCatchFinally2377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_246_in_catchRule2417 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_LPAREN_in_catchRule2419 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_type_in_catchRule2423 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_catchRule2427 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_catchRule2437 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_block_in_catchRule2439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_247_in_finallyRule2478 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_block_in_finallyRule2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMICOLON_in_semiColon2505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_name2521 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_DOT_in_name2531 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_name2535 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_name_in_nameOrPrimitiveType2575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_nameOrPrimitiveType2580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_expressionName2594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_methodName2606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_typeName2618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_type2629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeName_in_typeAdditionalArrayDim2646 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_primitiveType_in_typeAdditionalArrayDim2653 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_LBRACK_in_typeAdditionalArrayDim2658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_typeAdditionalArrayDim2661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitive_in_primitiveType2683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_primitive0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_localVariableDeclaration2731 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2733 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_localVariableDeclaration2740 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_variableDeclarator_in_localVariableDeclaration2742 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_localVariableDeclaration2747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_variableDeclarator2784 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_variableDeclarator2789 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_variableDeclarator2806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_variableDeclarator2811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_localInitializerArrayLiteral2859 = new BitSet(new long[]{0x0000000000000000L,0x91FFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2864 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_localInitializerArrayLiteral2867 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_localInitializerArrayLiteral2871 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_RBRACE_in_localInitializerArrayLiteral2877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_arrayLiteral2904 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_arrayLiteral2906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeAdditionalArrayDim_in_arrayAllocator2939 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_LBRACK_in_arrayAllocator2941 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFF000000000A003FL,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_expression_in_arrayAllocator2950 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_arrayAllocator2952 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_arrayAllocator2956 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_arrayAllocator2958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_252_in_declareNT2995 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_declareNTBlock_in_declareNT2997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_declareNTBlock3018 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_declarator_in_declareNTBlock3022 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_declareNTBlock3025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_253_in_scopeNT3054 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_scopeNTBlock_in_scopeNT3056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_scopeNTBlock3076 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_scopeDeclarator_in_scopeNTBlock3080 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_scopeNTBlock3083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameOrPrimitiveType_in_scopeDeclarator3117 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000080002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_LBRACK_in_scopeDeclarator3126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_scopeDeclarator3128 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_scopeDeclarator3137 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_scopeDeclarator3139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_declarator3185 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_declarator3192 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_declarator3194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_254_in_thenNT3237 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_thenNTBlock_in_thenNT3239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_thenNTBlock3260 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFF000000000A007FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_thenStatements_in_thenNTBlock3264 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_thenNTBlock3269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_thenStatements3300 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFF000000000A007FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_thenStatement3320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_thenStatement3331 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_thenStatement3336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyBodyStatement_in_thenStatement3342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lineStatement_in_statement3354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blockStatement_in_statement3358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStatement_in_lineStatement3370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_lineStatement3375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_lineStatement3380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakStatement_in_lineStatement3385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continueStatement_in_lineStatement3390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwStatement_in_lineStatement3395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_lineStatement3404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifRule_in_blockStatement3416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileRule_in_blockStatement3421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forRule_in_blockStatement3426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_blockStatement3431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tryCatchFinally_in_blockStatement3436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_block3448 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_block3464 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_localVariableDeclaration_in_block3475 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_statement_in_block3479 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFF000000000A003FL,0x0F3BE1FFFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_block3483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_227_in_actionContextStatement3524 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_methodName_in_actionContextStatement3528 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement3532 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_225_in_actionContextStatement3544 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_actionContextStatement3550 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement3557 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_226_in_actionContextStatement3567 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement3571 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_actionContextStatement3578 = new BitSet(new long[]{0x0000000000000002L});
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
    public static final BitSet FOLLOW_type_in_synpred9_BaseElement2282 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred9_BaseElement2284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_synpred10_BaseElement2795 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_set_in_synpred10_BaseElement2797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred11_BaseElement3316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred12_BaseElement3326 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred12_BaseElement3328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred13_BaseElement3460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred14_BaseElement3469 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred14_BaseElement3471 = new BitSet(new long[]{0x0000000000000002L});

}