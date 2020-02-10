// $ANTLR 3.2 Sep 23, 2009 12:02:23 Event.g 2014-03-26 14:10:35
 
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


import org.antlr.runtime.tree.*;

public class EventParser extends BaseRulesParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_TEMPLATE", "RULE_TEMPLATE_DECL", "TEMPLATE_DECLARATOR", "BINDINGS_BLOCK", "BINDING_VIEW_STATEMENT", "BINDINGS_VIEWS_BLOCK", "BINDING_STATEMENT", "DOMAIN_MODEL", "DOMAIN_MODEL_STATEMENT", "ACTION_CONTEXT_BLOCK", "ACTION_CONTEXT_STATEMENT", "ACTION_TYPE", "QUALIFIER", "QUALIFIED_NAME", "SIMPLE_NAME", "LOCAL_VARIABLE_DECL", "DECLARATORS", "VARIABLE_DECLARATOR", "RULE", "RULE_FUNCTION", "RULE_DECL", "NAME", "RULE_BLOCK", "ATTRIBUTE_BLOCK", "STATEMENTS", "DECLARE_BLOCK", "RULE_FUNC_DECL", "SCOPE_BLOCK", "WHEN_BLOCK", "PREDICATES", "BLOCKS", "THEN_BLOCK", "BODY_BLOCK", "SCOPE_DECLARATOR", "TYPE", "DECLARATOR", "VIRTUAL", "PRIMITIVE_TYPE", "ANNOTATION", "ARGS", "METHOD_CALL", "PRIMARY_ASSIGNMENT_EXPRESSION", "SUFFIX", "EXPRESSION", "IF_RULE", "WHILE_RULE", "FOR_RULE", "ELSE_STATEMENT", "STATEMENT", "RETURN_STATEMENT", "FOR_LOOP", "RETURN_TYPE", "MAPPING_BLOCK", "PRIORITY_STATEMENT", "PRIORITY", "VALIDITY_STATEMENT", "VALIDITY", "RANK_STATEMENT", "REQUEUE_STATEMENT", "FORWARD_CHAIN_STATEMENT", "BACKWARD_CHAIN_STATEMENT", "LASTMOD_STATEMENT", "ALIAS_STATEMENT", "LITERAL", "PREDICATE_STATEMENT", "RANGE_EXPRESSION", "RANGE_START", "RANGE_END", "SET_MEMBERSHIP_EXPRESSION", "EXPRESSIONS", "BREAK_STATEMENT", "CONTINUE_STATEMENT", "THROW_STATEMENT", "TRY_RULE", "BLOCK", "CATCH_RULE", "IDENTIFIER", "FINALLY_RULE", "ARRAY_ACCESS_SUFFIX", "INITIALIZER", "LOCAL_INITIALIZER", "ARRAY_LITERAL", "ARRAY_ALLOCATOR", "TRY_STATEMET", "BODY", "CATCH_CLAUSE", "FINALLY_CLAUSE", "TRY_STATEMENT", "HEADER", "VOID_LITERAL", "MODIFIERS", "BINARY_RELATION_NODE", "UNARY_EXPRESSION_NODE", "LHS", "RHS", "OPERATOR", "PRIMARY_EXPRESSION", "PREFIX", "SUFFIXES", "ASSIGNMENT_SUFFIX", "SUFFIX_EXPRESSION", "MappingEnd", "Identifier", "SEMICOLON", "OR", "AND", "EQ", "NE", "INSTANCE_OF", "LT", "GT", "LE", "GE", "LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIVIDE", "MOD", "POUND", "DOT", "ANNOTATE", "FloatingPointLiteral", "StringLiteral", "NullLiteral", "DecimalLiteral", "HexLiteral", "TRUE", "FALSE", "WS", "INCR", "DECR", "ASSIGN", "PLUS_EQUAL", "MINUS_EQUAL", "MULT_EQUAL", "DIV_EQUAL", "MOD_EQUAL", "HEADER_START", "HeaderSection", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "CharacterLiteral", "UnicodeEscape", "OctalEscape", "Letter", "JavaIDDigit", "COMMENT", "LINE_COMMENT", "EVENT_DEFINITION", "PARENT", "PROPERTY_DECLARATION", "PROPERTIES_BLOCK", "PROPERTY_BLOCK", "ATTRIBUTE", "ATTRIBUTES_BLOCK", "DOMAIN_STATEMENT", "DOMAIN_NAMES", "RETRY_ON_EXCEPTION_STATEMENT", "DEFAULT_DESTINATION_STATEMENT", "TTL_STATEMENT", "UNITS", "PAYLOAD_STRING_STATEMENT", "LITERALS", "NAMESPACE_STATEMENT", "NAMESPACE", "LOCATION", "TTL", "NAMESPACES_BLOCK", "PAYLOAD_BLOCK", "EXPIRY_ACTION_BLOCK", "'<#mapping>'", "'body'", "'validity'", "'lock'", "'exists'", "'and'", "'key'", "'alias'", "'rank'", "'virtual'", "'abstract'", "'byte'", "'case'", "'char'", "'class'", "'const'", "'default'", "'do'", "'extends'", "'final'", "'float'", "'goto'", "'implements'", "'import'", "'interface'", "'moveto'", "'native'", "'new'", "'package'", "'private'", "'protected'", "'public'", "'short'", "'static'", "'strictfp'", "'super'", "'switch'", "'synchronized'", "'this'", "'throws'", "'transient'", "'volatile'", "'create'", "'modify'", "'call'", "'views'", "'bindings'", "'display'", "'event'", "'concept'", "'\"\"xslt://'", "'\"'", "'void'", "','", "'return'", "'break'", "'continue'", "'throw'", "'if'", "'else'", "'while'", "'for'", "'try'", "'catch'", "'finally'", "'boolean'", "'int'", "'long'", "'double'", "'declare'", "'scope'", "'then'", "'attributes'", "'retryOnException'", "'defaultDestination'", "'properties'", "'domain'", "'timeToLive'", "'Milliseconds'", "'Seconds'", "'Minutes'", "'Hours'", "'Days'", "'expiryAction'", "'payload'", "'payloadString'", "'namespaces'", "'location'"
    };
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
    public Event_BaseElement gBaseElement;
    // delegators


        public EventParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EventParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gBaseElement = new Event_BaseElement(input, state, this);         
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
        gBaseElement.setTreeAdaptor(this.adaptor);
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EventParser.tokenNames; }
    public String getGrammarFileName() { return "Event.g"; }


    protected boolean virtual = false;
    protected RulesASTNode headerTree = null;

    public void setRuleElementType(ELEMENT_TYPES type) {
    	gBaseElement.setRuleElementType(type);
    }

    public void setTypeReference(boolean typeRef) {
    	gBaseElement.setTypeReference(typeRef);
    }
    	
    public void markAsArray(RulesASTNode node) {
    	gBaseElement.markAsArray(node);
    }

    public void popScope() {
    	gBaseElement.popScope();
    }

    public void pushScope(int type) {
    	gBaseElement.pushScope(type);
    }

    public boolean isInActionContextBlock() {
    	return gBaseElement.isInActionContextBlock();
    }

    public void setInActionContextBlock(boolean b) {
    	gBaseElement.setInActionContextBlock(b);
    }

    public void pushGlobalScopeName(RulesASTNode node, RulesASTNode type) {
    	gBaseElement.pushGlobalScopeName(node, type);
    }
    	
    public void pushDefineName(RulesASTNode node, RulesASTNode type) {
    	gBaseElement.pushDefineName(node, type);
    }

    public void addProblemCollector(IProblemHandler collector) {
    	gBaseElement.addProblemCollector(collector);
    }

    public RuleElement getRuleElement() {
    	return gBaseElement.getRuleElement();
    }

    public void setRuleElement(RuleElement element) {
    	gBaseElement.setRuleElement(element);
    }

    public CommonToken getCurrentToken() {
    	return gBaseElement.getCurrentToken();
    }

    public ScopeBlock getLastPoppedScope() {
    	return gBaseElement.getLastPoppedScope();
    }

    public ScopeBlock getScope() {
    	return gBaseElement.getScope();
    }

    public ScopeBlock getLocalScope() {
    	return gBaseElement.getLocalScope();
    }

    	// THIS METHOD IS UNIQUE TO EACH GRAMMAR, AS THE LEXER CLASS IS UNIQUE
    	public RulesASTNode getHeaderNode()
    	{
    		return ((EventLexer)getTokenStream().getTokenSource()).getHeaderNode();
    	}



    public static class startRule_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "startRule"
    // Event.g:128:1: startRule : compilationUnit ;
    public final EventParser.startRule_return startRule() throws RecognitionException {
        EventParser.startRule_return retval = new EventParser.startRule_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        EventParser.compilationUnit_return compilationUnit1 = null;



        try {
            // Event.g:132:2: ( compilationUnit )
            // Event.g:132:4: compilationUnit
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_compilationUnit_in_startRule151);
            compilationUnit1=compilationUnit();

            state._fsp--;

            adaptor.addChild(root_0, compilationUnit1.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "startRule"

    public static class compilationUnit_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compilationUnit"
    // Event.g:135:1: compilationUnit : ( HeaderSection )? eventDefinition EOF ;
    public final EventParser.compilationUnit_return compilationUnit() throws RecognitionException {
        EventParser.compilationUnit_return retval = new EventParser.compilationUnit_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token HeaderSection2=null;
        Token EOF4=null;
        EventParser.eventDefinition_return eventDefinition3 = null;


        EventASTNode HeaderSection2_tree=null;
        EventASTNode EOF4_tree=null;

        try {
            // Event.g:136:2: ( ( HeaderSection )? eventDefinition EOF )
            // Event.g:137:3: ( HeaderSection )? eventDefinition EOF
            {
            root_0 = (EventASTNode)adaptor.nil();

            // Event.g:137:3: ( HeaderSection )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==HeaderSection) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Event.g:137:3: HeaderSection
                    {
                    HeaderSection2=(Token)match(input,HeaderSection,FOLLOW_HeaderSection_in_compilationUnit166); 
                    HeaderSection2_tree = (EventASTNode)adaptor.create(HeaderSection2);
                    adaptor.addChild(root_0, HeaderSection2_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_eventDefinition_in_compilationUnit171);
            eventDefinition3=eventDefinition();

            state._fsp--;

            adaptor.addChild(root_0, eventDefinition3.getTree());
            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_compilationUnit175); 
            EOF4_tree = (EventASTNode)adaptor.create(EOF4);
            adaptor.addChild(root_0, EOF4_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "compilationUnit"

    public static class eventDefinition_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eventDefinition"
    // Event.g:142:1: eventDefinition : 'event' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE eventNT RBRACE -> ^( EVENT_DEFINITION[\"event\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS eventNT ) ) ;
    public final EventParser.eventDefinition_return eventDefinition() throws RecognitionException {
        EventParser.eventDefinition_return retval = new EventParser.eventDefinition_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal5=null;
        Token string_literal6=null;
        Token LBRACE7=null;
        Token RBRACE9=null;
        Event_BaseElement.name_return nm = null;

        Event_BaseElement.name_return parent = null;

        EventParser.eventNT_return eventNT8 = null;


        EventASTNode string_literal5_tree=null;
        EventASTNode string_literal6_tree=null;
        EventASTNode LBRACE7_tree=null;
        EventASTNode RBRACE9_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_201=new RewriteRuleTokenStream(adaptor,"token 201");
        RewriteRuleTokenStream stream_231=new RewriteRuleTokenStream(adaptor,"token 231");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_eventNT=new RewriteRuleSubtreeStream(adaptor,"rule eventNT");
        try {
            // Event.g:143:2: ( 'event' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE eventNT RBRACE -> ^( EVENT_DEFINITION[\"event\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS eventNT ) ) )
            // Event.g:143:4: 'event' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE eventNT RBRACE
            {
            string_literal5=(Token)match(input,231,FOLLOW_231_in_eventDefinition186);  
            stream_231.add(string_literal5);

            pushFollow(FOLLOW_name_in_eventDefinition190);
            nm=name(TYPE_RULE_DEF);

            state._fsp--;

            stream_name.add(nm.getTree());
            // Event.g:143:35: ( 'extends' parent= name[TYPE_RULE_DEF] )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==201) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Event.g:143:36: 'extends' parent= name[TYPE_RULE_DEF]
                    {
                    string_literal6=(Token)match(input,201,FOLLOW_201_in_eventDefinition194);  
                    stream_201.add(string_literal6);

                    pushFollow(FOLLOW_name_in_eventDefinition198);
                    parent=name(TYPE_RULE_DEF);

                    state._fsp--;

                    stream_name.add(parent.getTree());

                    }
                    break;

            }

            LBRACE7=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_eventDefinition203);  
            stream_LBRACE.add(LBRACE7);

            pushFollow(FOLLOW_eventNT_in_eventDefinition205);
            eventNT8=eventNT();

            state._fsp--;

            stream_eventNT.add(eventNT8.getTree());
            RBRACE9=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_eventDefinition207);  
            stream_RBRACE.add(RBRACE9);

             setRuleElementType(ELEMENT_TYPES.SIMPLE_EVENT); 


            // AST REWRITE
            // elements: nm, eventNT, parent
            // token labels: 
            // rule labels: retval, parent, nm
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_parent=new RewriteRuleSubtreeStream(adaptor,"rule parent",parent!=null?parent.tree:null);
            RewriteRuleSubtreeStream stream_nm=new RewriteRuleSubtreeStream(adaptor,"rule nm",nm!=null?nm.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 145:3: -> ^( EVENT_DEFINITION[\"event\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS eventNT ) )
            {
                // Event.g:145:6: ^( EVENT_DEFINITION[\"event\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS eventNT ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EVENT_DEFINITION, "event"), root_1);

                // Event.g:145:34: ^( NAME[\"name\"] $nm)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_nm.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:146:4: ( ^( PARENT[\"parent\"] $parent) )?
                if ( stream_parent.hasNext() ) {
                    // Event.g:146:4: ^( PARENT[\"parent\"] $parent)
                    {
                    EventASTNode root_2 = (EventASTNode)adaptor.nil();
                    root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PARENT, "parent"), root_2);

                    adaptor.addChild(root_2, stream_parent.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_parent.reset();
                // Event.g:146:33: ^( BLOCKS eventNT )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                adaptor.addChild(root_2, stream_eventNT.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "eventDefinition"

    public static class eventNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eventNT"
    // Event.g:149:1: eventNT : ( propertiesNT | attributesNT | expiryActionNT | payloadNT )* ;
    public final EventParser.eventNT_return eventNT() throws RecognitionException {
        EventParser.eventNT_return retval = new EventParser.eventNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        EventParser.propertiesNT_return propertiesNT10 = null;

        EventParser.attributesNT_return attributesNT11 = null;

        EventParser.expiryActionNT_return expiryActionNT12 = null;

        EventParser.payloadNT_return payloadNT13 = null;



        try {
            // Event.g:150:2: ( ( propertiesNT | attributesNT | expiryActionNT | payloadNT )* )
            // Event.g:150:4: ( propertiesNT | attributesNT | expiryActionNT | payloadNT )*
            {
            root_0 = (EventASTNode)adaptor.nil();

            // Event.g:150:4: ( propertiesNT | attributesNT | expiryActionNT | payloadNT )*
            loop3:
            do {
                int alt3=5;
                switch ( input.LA(1) ) {
                case 258:
                    {
                    alt3=1;
                    }
                    break;
                case 255:
                    {
                    alt3=2;
                    }
                    break;
                case 266:
                    {
                    alt3=3;
                    }
                    break;
                case 267:
                    {
                    alt3=4;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // Event.g:150:5: propertiesNT
            	    {
            	    pushFollow(FOLLOW_propertiesNT_in_eventNT258);
            	    propertiesNT10=propertiesNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, propertiesNT10.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Event.g:151:4: attributesNT
            	    {
            	    pushFollow(FOLLOW_attributesNT_in_eventNT263);
            	    attributesNT11=attributesNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, attributesNT11.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Event.g:152:4: expiryActionNT
            	    {
            	    pushFollow(FOLLOW_expiryActionNT_in_eventNT268);
            	    expiryActionNT12=expiryActionNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, expiryActionNT12.getTree());

            	    }
            	    break;
            	case 4 :
            	    // Event.g:153:4: payloadNT
            	    {
            	    pushFollow(FOLLOW_payloadNT_in_eventNT273);
            	    payloadNT13=payloadNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, payloadNT13.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "eventNT"

    public static class attributesNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributesNT"
    // Event.g:156:1: attributesNT : 'attributes' attributesNTBlock -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock ) ;
    public final EventParser.attributesNT_return attributesNT() throws RecognitionException {
        EventParser.attributesNT_return retval = new EventParser.attributesNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal14=null;
        EventParser.attributesNTBlock_return attributesNTBlock15 = null;


        EventASTNode string_literal14_tree=null;
        RewriteRuleTokenStream stream_255=new RewriteRuleTokenStream(adaptor,"token 255");
        RewriteRuleSubtreeStream stream_attributesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule attributesNTBlock");
        try {
            // Event.g:157:2: ( 'attributes' attributesNTBlock -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock ) )
            // Event.g:157:4: 'attributes' attributesNTBlock
            {
            string_literal14=(Token)match(input,255,FOLLOW_255_in_attributesNT286);  
            stream_255.add(string_literal14);

            pushFollow(FOLLOW_attributesNTBlock_in_attributesNT288);
            attributesNTBlock15=attributesNTBlock();

            state._fsp--;

            stream_attributesNTBlock.add(attributesNTBlock15.getTree());


            // AST REWRITE
            // elements: attributesNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 158:3: -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock )
            {
                // Event.g:158:6: ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(ATTRIBUTES_BLOCK, "attributes"), root_1);

                adaptor.addChild(root_1, stream_attributesNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "attributesNT"

    public static class attributesNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributesNTBlock"
    // Event.g:161:1: attributesNTBlock : LBRACE ( attributeStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) ) ;
    public final EventParser.attributesNTBlock_return attributesNTBlock() throws RecognitionException {
        EventParser.attributesNTBlock_return retval = new EventParser.attributesNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE16=null;
        Token RBRACE18=null;
        EventParser.attributeStatement_return attributeStatement17 = null;


        EventASTNode LBRACE16_tree=null;
        EventASTNode RBRACE18_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_attributeStatement=new RewriteRuleSubtreeStream(adaptor,"rule attributeStatement");
        try {
            // Event.g:161:19: ( LBRACE ( attributeStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) ) )
            // Event.g:161:21: LBRACE ( attributeStatement )* RBRACE
            {
            LBRACE16=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributesNTBlock310);  
            stream_LBRACE.add(LBRACE16);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:162:6: ( attributeStatement )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=256 && LA4_0<=257)||LA4_0==260) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Event.g:162:6: attributeStatement
            	    {
            	    pushFollow(FOLLOW_attributeStatement_in_attributesNTBlock319);
            	    attributeStatement17=attributeStatement();

            	    state._fsp--;

            	    stream_attributeStatement.add(attributeStatement17.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            RBRACE18=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_attributesNTBlock322);  
            stream_RBRACE.add(RBRACE18);

             popScope(); 


            // AST REWRITE
            // elements: attributeStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 163:2: -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) )
            {
                // Event.g:163:5: ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:163:13: ^( STATEMENTS ( attributeStatement )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:163:26: ( attributeStatement )*
                while ( stream_attributeStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_attributeStatement.nextTree());

                }
                stream_attributeStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "attributesNTBlock"

    public static class attributeStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeStatement"
    // Event.g:166:1: attributeStatement : ( defaultDestinationStatement | ttlStatement | retryOnExceptionStatement );
    public final EventParser.attributeStatement_return attributeStatement() throws RecognitionException {
        EventParser.attributeStatement_return retval = new EventParser.attributeStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        EventParser.defaultDestinationStatement_return defaultDestinationStatement19 = null;

        EventParser.ttlStatement_return ttlStatement20 = null;

        EventParser.retryOnExceptionStatement_return retryOnExceptionStatement21 = null;



        try {
            // Event.g:167:2: ( defaultDestinationStatement | ttlStatement | retryOnExceptionStatement )
            int alt5=3;
            switch ( input.LA(1) ) {
            case 257:
                {
                alt5=1;
                }
                break;
            case 260:
                {
                alt5=2;
                }
                break;
            case 256:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // Event.g:167:4: defaultDestinationStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_defaultDestinationStatement_in_attributeStatement350);
                    defaultDestinationStatement19=defaultDestinationStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, defaultDestinationStatement19.getTree());

                    }
                    break;
                case 2 :
                    // Event.g:168:4: ttlStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_ttlStatement_in_attributeStatement355);
                    ttlStatement20=ttlStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, ttlStatement20.getTree());

                    }
                    break;
                case 3 :
                    // Event.g:169:4: retryOnExceptionStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_retryOnExceptionStatement_in_attributeStatement360);
                    retryOnExceptionStatement21=retryOnExceptionStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, retryOnExceptionStatement21.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "attributeStatement"

    public static class retryOnExceptionStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "retryOnExceptionStatement"
    // Event.g:172:1: retryOnExceptionStatement : 'retryOnException' ASSIGN booleanLiteral SEMICOLON -> ^( RETRY_ON_EXCEPTION_STATEMENT ^( LITERAL booleanLiteral ) ) ;
    public final EventParser.retryOnExceptionStatement_return retryOnExceptionStatement() throws RecognitionException {
        EventParser.retryOnExceptionStatement_return retval = new EventParser.retryOnExceptionStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal22=null;
        Token ASSIGN23=null;
        Token SEMICOLON25=null;
        Event_BaseElement.booleanLiteral_return booleanLiteral24 = null;


        EventASTNode string_literal22_tree=null;
        EventASTNode ASSIGN23_tree=null;
        EventASTNode SEMICOLON25_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_256=new RewriteRuleTokenStream(adaptor,"token 256");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_booleanLiteral=new RewriteRuleSubtreeStream(adaptor,"rule booleanLiteral");
        try {
            // Event.g:173:2: ( 'retryOnException' ASSIGN booleanLiteral SEMICOLON -> ^( RETRY_ON_EXCEPTION_STATEMENT ^( LITERAL booleanLiteral ) ) )
            // Event.g:173:4: 'retryOnException' ASSIGN booleanLiteral SEMICOLON
            {
            string_literal22=(Token)match(input,256,FOLLOW_256_in_retryOnExceptionStatement372);  
            stream_256.add(string_literal22);

            ASSIGN23=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_retryOnExceptionStatement374);  
            stream_ASSIGN.add(ASSIGN23);

            pushFollow(FOLLOW_booleanLiteral_in_retryOnExceptionStatement376);
            booleanLiteral24=booleanLiteral();

            state._fsp--;

            stream_booleanLiteral.add(booleanLiteral24.getTree());
            SEMICOLON25=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_retryOnExceptionStatement378);  
            stream_SEMICOLON.add(SEMICOLON25);



            // AST REWRITE
            // elements: booleanLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 174:3: -> ^( RETRY_ON_EXCEPTION_STATEMENT ^( LITERAL booleanLiteral ) )
            {
                // Event.g:174:6: ^( RETRY_ON_EXCEPTION_STATEMENT ^( LITERAL booleanLiteral ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(RETRY_ON_EXCEPTION_STATEMENT, "RETRY_ON_EXCEPTION_STATEMENT"), root_1);

                // Event.g:174:37: ^( LITERAL booleanLiteral )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "retryOnExceptionStatement"

    public static class defaultDestinationStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "defaultDestinationStatement"
    // Event.g:177:1: defaultDestinationStatement : 'defaultDestination' ASSIGN name[DOMAIN_REF] SEMICOLON -> ^( DEFAULT_DESTINATION_STATEMENT ^( NAME name ) ) ;
    public final EventParser.defaultDestinationStatement_return defaultDestinationStatement() throws RecognitionException {
        EventParser.defaultDestinationStatement_return retval = new EventParser.defaultDestinationStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal26=null;
        Token ASSIGN27=null;
        Token SEMICOLON29=null;
        Event_BaseElement.name_return name28 = null;


        EventASTNode string_literal26_tree=null;
        EventASTNode ASSIGN27_tree=null;
        EventASTNode SEMICOLON29_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_257=new RewriteRuleTokenStream(adaptor,"token 257");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Event.g:178:2: ( 'defaultDestination' ASSIGN name[DOMAIN_REF] SEMICOLON -> ^( DEFAULT_DESTINATION_STATEMENT ^( NAME name ) ) )
            // Event.g:178:4: 'defaultDestination' ASSIGN name[DOMAIN_REF] SEMICOLON
            {
            string_literal26=(Token)match(input,257,FOLLOW_257_in_defaultDestinationStatement403);  
            stream_257.add(string_literal26);

            ASSIGN27=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_defaultDestinationStatement405);  
            stream_ASSIGN.add(ASSIGN27);

            pushFollow(FOLLOW_name_in_defaultDestinationStatement407);
            name28=name(DOMAIN_REF);

            state._fsp--;

            stream_name.add(name28.getTree());
            SEMICOLON29=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_defaultDestinationStatement410);  
            stream_SEMICOLON.add(SEMICOLON29);



            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 179:3: -> ^( DEFAULT_DESTINATION_STATEMENT ^( NAME name ) )
            {
                // Event.g:179:6: ^( DEFAULT_DESTINATION_STATEMENT ^( NAME name ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DEFAULT_DESTINATION_STATEMENT, "DEFAULT_DESTINATION_STATEMENT"), root_1);

                // Event.g:179:38: ^( NAME name )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "defaultDestinationStatement"

    public static class propertiesNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesNT"
    // Event.g:182:1: propertiesNT : 'properties' propertiesNTBlock -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock ) ;
    public final EventParser.propertiesNT_return propertiesNT() throws RecognitionException {
        EventParser.propertiesNT_return retval = new EventParser.propertiesNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal30=null;
        EventParser.propertiesNTBlock_return propertiesNTBlock31 = null;


        EventASTNode string_literal30_tree=null;
        RewriteRuleTokenStream stream_258=new RewriteRuleTokenStream(adaptor,"token 258");
        RewriteRuleSubtreeStream stream_propertiesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule propertiesNTBlock");
        try {
            // Event.g:183:2: ( 'properties' propertiesNTBlock -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock ) )
            // Event.g:183:4: 'properties' propertiesNTBlock
            {
            string_literal30=(Token)match(input,258,FOLLOW_258_in_propertiesNT436);  
            stream_258.add(string_literal30);

            pushFollow(FOLLOW_propertiesNTBlock_in_propertiesNT438);
            propertiesNTBlock31=propertiesNTBlock();

            state._fsp--;

            stream_propertiesNTBlock.add(propertiesNTBlock31.getTree());


            // AST REWRITE
            // elements: propertiesNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 184:2: -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock )
            {
                // Event.g:184:5: ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PROPERTIES_BLOCK, "properties"), root_1);

                adaptor.addChild(root_1, stream_propertiesNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "propertiesNT"

    public static class propertiesNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesNTBlock"
    // Event.g:187:1: propertiesNTBlock : LBRACE ( propertiesBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) ) ;
    public final EventParser.propertiesNTBlock_return propertiesNTBlock() throws RecognitionException {
        EventParser.propertiesNTBlock_return retval = new EventParser.propertiesNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE32=null;
        Token RBRACE34=null;
        EventParser.propertiesBodyDeclaration_return propertiesBodyDeclaration33 = null;


        EventASTNode LBRACE32_tree=null;
        EventASTNode RBRACE34_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_propertiesBodyDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule propertiesBodyDeclaration");
        try {
            // Event.g:187:19: ( LBRACE ( propertiesBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) ) )
            // Event.g:187:21: LBRACE ( propertiesBodyDeclaration )* RBRACE
            {
            LBRACE32=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_propertiesNTBlock459);  
            stream_LBRACE.add(LBRACE32);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:188:6: ( propertiesBodyDeclaration )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==Identifier||LA6_0==NullLiteral||(LA6_0>=184 && LA6_0<=232)||(LA6_0>=248 && LA6_0<=251)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Event.g:188:6: propertiesBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_propertiesBodyDeclaration_in_propertiesNTBlock468);
            	    propertiesBodyDeclaration33=propertiesBodyDeclaration();

            	    state._fsp--;

            	    stream_propertiesBodyDeclaration.add(propertiesBodyDeclaration33.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            RBRACE34=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_propertiesNTBlock471);  
            stream_RBRACE.add(RBRACE34);

             popScope(); 


            // AST REWRITE
            // elements: propertiesBodyDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 189:2: -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) )
            {
                // Event.g:189:5: ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:189:13: ^( STATEMENTS ( propertiesBodyDeclaration )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:189:26: ( propertiesBodyDeclaration )*
                while ( stream_propertiesBodyDeclaration.hasNext() ) {
                    adaptor.addChild(root_2, stream_propertiesBodyDeclaration.nextTree());

                }
                stream_propertiesBodyDeclaration.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "propertiesNTBlock"

    public static class propertiesBodyDeclaration_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesBodyDeclaration"
    // Event.g:192:1: propertiesBodyDeclaration : t= type id= identifier ( SEMICOLON | propertyBlock ) -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? ) ;
    public final EventParser.propertiesBodyDeclaration_return propertiesBodyDeclaration() throws RecognitionException {
        EventParser.propertiesBodyDeclaration_return retval = new EventParser.propertiesBodyDeclaration_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token SEMICOLON35=null;
        Event_BaseElement.type_return t = null;

        Event_BaseElement.identifier_return id = null;

        EventParser.propertyBlock_return propertyBlock36 = null;


        EventASTNode SEMICOLON35_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_propertyBlock=new RewriteRuleSubtreeStream(adaptor,"rule propertyBlock");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Event.g:193:2: (t= type id= identifier ( SEMICOLON | propertyBlock ) -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? ) )
            // Event.g:193:4: t= type id= identifier ( SEMICOLON | propertyBlock )
            {
             setTypeReference(true); 
            pushFollow(FOLLOW_type_in_propertiesBodyDeclaration503);
            t=type();

            state._fsp--;

            stream_type.add(t.getTree());
             setTypeReference(false); 
            pushFollow(FOLLOW_identifier_in_propertiesBodyDeclaration509);
            id=identifier();

            state._fsp--;

            stream_identifier.add(id.getTree());
             pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); 
            // Event.g:195:3: ( SEMICOLON | propertyBlock )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==SEMICOLON) ) {
                alt7=1;
            }
            else if ( (LA7_0==LBRACE) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // Event.g:195:4: SEMICOLON
                    {
                    SEMICOLON35=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_propertiesBodyDeclaration518);  
                    stream_SEMICOLON.add(SEMICOLON35);


                    }
                    break;
                case 2 :
                    // Event.g:195:16: propertyBlock
                    {
                    pushFollow(FOLLOW_propertyBlock_in_propertiesBodyDeclaration522);
                    propertyBlock36=propertyBlock();

                    state._fsp--;

                    stream_propertyBlock.add(propertyBlock36.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: propertyBlock, t, id
            // token labels: 
            // rule labels: id, retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 196:3: -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? )
            {
                // Event.g:196:6: ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PROPERTY_DECLARATION, "PROPERTY_DECLARATION"), root_1);

                // Event.g:196:29: ^( TYPE[\"type\"] $t)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:196:48: ^( NAME[\"name\"] $id)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:197:4: ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )?
                if ( stream_propertyBlock.hasNext() ) {
                    // Event.g:197:4: ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock )
                    {
                    EventASTNode root_2 = (EventASTNode)adaptor.nil();
                    root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PROPERTY_BLOCK, "prop block"), root_2);

                    adaptor.addChild(root_2, stream_propertyBlock.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_propertyBlock.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "propertiesBodyDeclaration"

    public static class propertyBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyBlock"
    // Event.g:200:1: propertyBlock : LBRACE ( propertyAttribute )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) ) ;
    public final EventParser.propertyBlock_return propertyBlock() throws RecognitionException {
        EventParser.propertyBlock_return retval = new EventParser.propertyBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE37=null;
        Token RBRACE39=null;
        EventParser.propertyAttribute_return propertyAttribute38 = null;


        EventASTNode LBRACE37_tree=null;
        EventASTNode RBRACE39_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_propertyAttribute=new RewriteRuleSubtreeStream(adaptor,"rule propertyAttribute");
        try {
            // Event.g:201:2: ( LBRACE ( propertyAttribute )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) ) )
            // Event.g:201:5: LBRACE ( propertyAttribute )* RBRACE
            {
            LBRACE37=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_propertyBlock571);  
            stream_LBRACE.add(LBRACE37);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:202:4: ( propertyAttribute )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==259) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // Event.g:202:4: propertyAttribute
            	    {
            	    pushFollow(FOLLOW_propertyAttribute_in_propertyBlock578);
            	    propertyAttribute38=propertyAttribute();

            	    state._fsp--;

            	    stream_propertyAttribute.add(propertyAttribute38.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            RBRACE39=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_propertyBlock581);  
            stream_RBRACE.add(RBRACE39);

             popScope(); 


            // AST REWRITE
            // elements: propertyAttribute
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 203:3: -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) )
            {
                // Event.g:203:6: ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:203:14: ^( STATEMENTS ( propertyAttribute )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:203:27: ( propertyAttribute )*
                while ( stream_propertyAttribute.hasNext() ) {
                    adaptor.addChild(root_2, stream_propertyAttribute.nextTree());

                }
                stream_propertyAttribute.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "propertyBlock"

    public static class propertyAttribute_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyAttribute"
    // Event.g:206:1: propertyAttribute : domainStatement ;
    public final EventParser.propertyAttribute_return propertyAttribute() throws RecognitionException {
        EventParser.propertyAttribute_return retval = new EventParser.propertyAttribute_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        EventParser.domainStatement_return domainStatement40 = null;



        try {
            // Event.g:207:2: ( domainStatement )
            // Event.g:207:4: domainStatement
            {
            root_0 = (EventASTNode)adaptor.nil();

            pushFollow(FOLLOW_domainStatement_in_propertyAttribute610);
            domainStatement40=domainStatement();

            state._fsp--;

            adaptor.addChild(root_0, domainStatement40.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "propertyAttribute"

    public static class domainStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainStatement"
    // Event.g:210:1: domainStatement : 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) ) ;
    public final EventParser.domainStatement_return domainStatement() throws RecognitionException {
        EventParser.domainStatement_return retval = new EventParser.domainStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal41=null;
        Token ASSIGN42=null;
        Token char_literal43=null;
        Token SEMICOLON44=null;
        List list_names=null;
        RuleReturnScope names = null;
        EventASTNode string_literal41_tree=null;
        EventASTNode ASSIGN42_tree=null;
        EventASTNode char_literal43_tree=null;
        EventASTNode SEMICOLON44_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_259=new RewriteRuleTokenStream(adaptor,"token 259");
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Event.g:211:2: ( 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) ) )
            // Event.g:211:4: 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON
            {
            string_literal41=(Token)match(input,259,FOLLOW_259_in_domainStatement622);  
            stream_259.add(string_literal41);

            ASSIGN42=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_domainStatement624);  
            stream_ASSIGN.add(ASSIGN42);

            pushFollow(FOLLOW_name_in_domainStatement628);
            names=name(DOMAIN_REF);

            state._fsp--;

            stream_name.add(names.getTree());
            if (list_names==null) list_names=new ArrayList();
            list_names.add(names.getTree());

            // Event.g:211:44: ( ',' names+= name[DOMAIN_REF] )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==236) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // Event.g:211:45: ',' names+= name[DOMAIN_REF]
            	    {
            	    char_literal43=(Token)match(input,236,FOLLOW_236_in_domainStatement632);  
            	    stream_236.add(char_literal43);

            	    pushFollow(FOLLOW_name_in_domainStatement636);
            	    names=name(DOMAIN_REF);

            	    state._fsp--;

            	    stream_name.add(names.getTree());
            	    if (list_names==null) list_names=new ArrayList();
            	    list_names.add(names.getTree());


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            SEMICOLON44=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_domainStatement641);  
            stream_SEMICOLON.add(SEMICOLON44);



            // AST REWRITE
            // elements: names
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: names
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_names=new RewriteRuleSubtreeStream(adaptor,"token names",list_names);
            root_0 = (EventASTNode)adaptor.nil();
            // 212:3: -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) )
            {
                // Event.g:212:6: ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DOMAIN_STATEMENT, "DOMAIN_STATEMENT"), root_1);

                // Event.g:212:25: ^( DOMAIN_NAMES ( $names)+ )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(DOMAIN_NAMES, "DOMAIN_NAMES"), root_2);

                if ( !(stream_names.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_names.hasNext() ) {
                    adaptor.addChild(root_2, stream_names.nextTree());

                }
                stream_names.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "domainStatement"

    public static class ttlStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ttlStatement"
    // Event.g:215:1: ttlStatement : 'timeToLive' ASSIGN integerLiteral ttlUnits SEMICOLON -> ^( TTL_STATEMENT ^( TTL integerLiteral ) ^( UNITS ttlUnits ) ) ;
    public final EventParser.ttlStatement_return ttlStatement() throws RecognitionException {
        EventParser.ttlStatement_return retval = new EventParser.ttlStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal45=null;
        Token ASSIGN46=null;
        Token SEMICOLON49=null;
        Event_BaseElement.integerLiteral_return integerLiteral47 = null;

        EventParser.ttlUnits_return ttlUnits48 = null;


        EventASTNode string_literal45_tree=null;
        EventASTNode ASSIGN46_tree=null;
        EventASTNode SEMICOLON49_tree=null;
        RewriteRuleTokenStream stream_260=new RewriteRuleTokenStream(adaptor,"token 260");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_ttlUnits=new RewriteRuleSubtreeStream(adaptor,"rule ttlUnits");
        RewriteRuleSubtreeStream stream_integerLiteral=new RewriteRuleSubtreeStream(adaptor,"rule integerLiteral");
        try {
            // Event.g:216:2: ( 'timeToLive' ASSIGN integerLiteral ttlUnits SEMICOLON -> ^( TTL_STATEMENT ^( TTL integerLiteral ) ^( UNITS ttlUnits ) ) )
            // Event.g:216:4: 'timeToLive' ASSIGN integerLiteral ttlUnits SEMICOLON
            {
            string_literal45=(Token)match(input,260,FOLLOW_260_in_ttlStatement669);  
            stream_260.add(string_literal45);

            ASSIGN46=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_ttlStatement671);  
            stream_ASSIGN.add(ASSIGN46);

            pushFollow(FOLLOW_integerLiteral_in_ttlStatement673);
            integerLiteral47=integerLiteral();

            state._fsp--;

            stream_integerLiteral.add(integerLiteral47.getTree());
            pushFollow(FOLLOW_ttlUnits_in_ttlStatement675);
            ttlUnits48=ttlUnits();

            state._fsp--;

            stream_ttlUnits.add(ttlUnits48.getTree());
            SEMICOLON49=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_ttlStatement677);  
            stream_SEMICOLON.add(SEMICOLON49);



            // AST REWRITE
            // elements: integerLiteral, ttlUnits
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 217:3: -> ^( TTL_STATEMENT ^( TTL integerLiteral ) ^( UNITS ttlUnits ) )
            {
                // Event.g:217:6: ^( TTL_STATEMENT ^( TTL integerLiteral ) ^( UNITS ttlUnits ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TTL_STATEMENT, "TTL_STATEMENT"), root_1);

                // Event.g:217:22: ^( TTL integerLiteral )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(TTL, "TTL"), root_2);

                adaptor.addChild(root_2, stream_integerLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:217:44: ^( UNITS ttlUnits )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(UNITS, "UNITS"), root_2);

                adaptor.addChild(root_2, stream_ttlUnits.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "ttlStatement"

    public static class ttlUnits_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ttlUnits"
    // Event.g:220:1: ttlUnits : ( 'Milliseconds' | 'Seconds' | 'Minutes' | 'Hours' | 'Days' );
    public final EventParser.ttlUnits_return ttlUnits() throws RecognitionException {
        EventParser.ttlUnits_return retval = new EventParser.ttlUnits_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token set50=null;

        EventASTNode set50_tree=null;

        try {
            // Event.g:221:2: ( 'Milliseconds' | 'Seconds' | 'Minutes' | 'Hours' | 'Days' )
            // Event.g:
            {
            root_0 = (EventASTNode)adaptor.nil();

            set50=(Token)input.LT(1);
            if ( (input.LA(1)>=261 && input.LA(1)<=265) ) {
                input.consume();
                adaptor.addChild(root_0, (EventASTNode)adaptor.create(set50));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "ttlUnits"

    public static class expiryActionNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expiryActionNT"
    // Event.g:228:1: expiryActionNT : 'expiryAction' expiryActionNTBlock -> ^( EXPIRY_ACTION_BLOCK expiryActionNTBlock ) ;
    public final EventParser.expiryActionNT_return expiryActionNT() throws RecognitionException {
        EventParser.expiryActionNT_return retval = new EventParser.expiryActionNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal51=null;
        EventParser.expiryActionNTBlock_return expiryActionNTBlock52 = null;


        EventASTNode string_literal51_tree=null;
        RewriteRuleTokenStream stream_266=new RewriteRuleTokenStream(adaptor,"token 266");
        RewriteRuleSubtreeStream stream_expiryActionNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule expiryActionNTBlock");
        try {
            // Event.g:229:2: ( 'expiryAction' expiryActionNTBlock -> ^( EXPIRY_ACTION_BLOCK expiryActionNTBlock ) )
            // Event.g:229:4: 'expiryAction' expiryActionNTBlock
            {
            string_literal51=(Token)match(input,266,FOLLOW_266_in_expiryActionNT741);  
            stream_266.add(string_literal51);

            pushFollow(FOLLOW_expiryActionNTBlock_in_expiryActionNT743);
            expiryActionNTBlock52=expiryActionNTBlock();

            state._fsp--;

            stream_expiryActionNTBlock.add(expiryActionNTBlock52.getTree());


            // AST REWRITE
            // elements: expiryActionNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 230:2: -> ^( EXPIRY_ACTION_BLOCK expiryActionNTBlock )
            {
                // Event.g:230:5: ^( EXPIRY_ACTION_BLOCK expiryActionNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(EXPIRY_ACTION_BLOCK, "EXPIRY_ACTION_BLOCK"), root_1);

                adaptor.addChild(root_1, stream_expiryActionNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "expiryActionNT"

    public static class expiryActionNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expiryActionNTBlock"
    // Event.g:233:1: expiryActionNTBlock : LBRACE ( expiryActionStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( expiryActionStatement )* ) ) ;
    public final EventParser.expiryActionNTBlock_return expiryActionNTBlock() throws RecognitionException {
        EventParser.expiryActionNTBlock_return retval = new EventParser.expiryActionNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE53=null;
        Token RBRACE55=null;
        EventParser.expiryActionStatement_return expiryActionStatement54 = null;


        EventASTNode LBRACE53_tree=null;
        EventASTNode RBRACE55_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_expiryActionStatement=new RewriteRuleSubtreeStream(adaptor,"rule expiryActionStatement");
        try {
            // Event.g:234:2: ( LBRACE ( expiryActionStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( expiryActionStatement )* ) ) )
            // Event.g:234:4: LBRACE ( expiryActionStatement )* RBRACE
            {
            LBRACE53=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_expiryActionNTBlock764);  
            stream_LBRACE.add(LBRACE53);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:235:5: ( expiryActionStatement )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==252||LA10_0==254) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Event.g:235:5: expiryActionStatement
            	    {
            	    pushFollow(FOLLOW_expiryActionStatement_in_expiryActionNTBlock772);
            	    expiryActionStatement54=expiryActionStatement();

            	    state._fsp--;

            	    stream_expiryActionStatement.add(expiryActionStatement54.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            RBRACE55=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_expiryActionNTBlock775);  
            stream_RBRACE.add(RBRACE55);

             popScope(); 


            // AST REWRITE
            // elements: expiryActionStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 236:2: -> ^( BLOCK ^( STATEMENTS ( expiryActionStatement )* ) )
            {
                // Event.g:236:5: ^( BLOCK ^( STATEMENTS ( expiryActionStatement )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:236:13: ^( STATEMENTS ( expiryActionStatement )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:236:26: ( expiryActionStatement )*
                while ( stream_expiryActionStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_expiryActionStatement.nextTree());

                }
                stream_expiryActionStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "expiryActionNTBlock"

    public static class expiryActionStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expiryActionStatement"
    // Event.g:239:1: expiryActionStatement : ( declareNT | thenNT );
    public final EventParser.expiryActionStatement_return expiryActionStatement() throws RecognitionException {
        EventParser.expiryActionStatement_return retval = new EventParser.expiryActionStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Event_BaseElement.declareNT_return declareNT56 = null;

        Event_BaseElement.thenNT_return thenNT57 = null;



        try {
            // Event.g:240:2: ( declareNT | thenNT )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==252) ) {
                alt11=1;
            }
            else if ( (LA11_0==254) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // Event.g:240:4: declareNT
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_declareNT_in_expiryActionStatement807);
                    declareNT56=declareNT();

                    state._fsp--;

                    adaptor.addChild(root_0, declareNT56.getTree());

                    }
                    break;
                case 2 :
                    // Event.g:240:16: thenNT
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_thenNT_in_expiryActionStatement811);
                    thenNT57=thenNT();

                    state._fsp--;

                    adaptor.addChild(root_0, thenNT57.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "expiryActionStatement"

    public static class payloadNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "payloadNT"
    // Event.g:243:1: payloadNT : 'payload' payloadNTBlock -> ^( PAYLOAD_BLOCK payloadNTBlock ) ;
    public final EventParser.payloadNT_return payloadNT() throws RecognitionException {
        EventParser.payloadNT_return retval = new EventParser.payloadNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal58=null;
        EventParser.payloadNTBlock_return payloadNTBlock59 = null;


        EventASTNode string_literal58_tree=null;
        RewriteRuleTokenStream stream_267=new RewriteRuleTokenStream(adaptor,"token 267");
        RewriteRuleSubtreeStream stream_payloadNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule payloadNTBlock");
        try {
            // Event.g:244:2: ( 'payload' payloadNTBlock -> ^( PAYLOAD_BLOCK payloadNTBlock ) )
            // Event.g:244:4: 'payload' payloadNTBlock
            {
            string_literal58=(Token)match(input,267,FOLLOW_267_in_payloadNT823);  
            stream_267.add(string_literal58);

            pushFollow(FOLLOW_payloadNTBlock_in_payloadNT825);
            payloadNTBlock59=payloadNTBlock();

            state._fsp--;

            stream_payloadNTBlock.add(payloadNTBlock59.getTree());


            // AST REWRITE
            // elements: payloadNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 245:2: -> ^( PAYLOAD_BLOCK payloadNTBlock )
            {
                // Event.g:245:5: ^( PAYLOAD_BLOCK payloadNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PAYLOAD_BLOCK, "PAYLOAD_BLOCK"), root_1);

                adaptor.addChild(root_1, stream_payloadNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "payloadNT"

    public static class payloadNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "payloadNTBlock"
    // Event.g:248:1: payloadNTBlock : LBRACE ( payloadStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( payloadStatement )* ) ) ;
    public final EventParser.payloadNTBlock_return payloadNTBlock() throws RecognitionException {
        EventParser.payloadNTBlock_return retval = new EventParser.payloadNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE60=null;
        Token RBRACE62=null;
        EventParser.payloadStatement_return payloadStatement61 = null;


        EventASTNode LBRACE60_tree=null;
        EventASTNode RBRACE62_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_payloadStatement=new RewriteRuleSubtreeStream(adaptor,"rule payloadStatement");
        try {
            // Event.g:249:2: ( LBRACE ( payloadStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( payloadStatement )* ) ) )
            // Event.g:249:4: LBRACE ( payloadStatement )* RBRACE
            {
            LBRACE60=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_payloadNTBlock846);  
            stream_LBRACE.add(LBRACE60);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:250:5: ( payloadStatement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>=268 && LA12_0<=269)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // Event.g:250:5: payloadStatement
            	    {
            	    pushFollow(FOLLOW_payloadStatement_in_payloadNTBlock854);
            	    payloadStatement61=payloadStatement();

            	    state._fsp--;

            	    stream_payloadStatement.add(payloadStatement61.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            RBRACE62=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_payloadNTBlock857);  
            stream_RBRACE.add(RBRACE62);

             popScope(); 


            // AST REWRITE
            // elements: payloadStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 251:2: -> ^( BLOCK ^( STATEMENTS ( payloadStatement )* ) )
            {
                // Event.g:251:5: ^( BLOCK ^( STATEMENTS ( payloadStatement )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:251:13: ^( STATEMENTS ( payloadStatement )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:251:26: ( payloadStatement )*
                while ( stream_payloadStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_payloadStatement.nextTree());

                }
                stream_payloadStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "payloadNTBlock"

    public static class payloadStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "payloadStatement"
    // Event.g:254:1: payloadStatement : ( namespaceNT | payloadStringStatement );
    public final EventParser.payloadStatement_return payloadStatement() throws RecognitionException {
        EventParser.payloadStatement_return retval = new EventParser.payloadStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        EventParser.namespaceNT_return namespaceNT63 = null;

        EventParser.payloadStringStatement_return payloadStringStatement64 = null;



        try {
            // Event.g:255:2: ( namespaceNT | payloadStringStatement )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==269) ) {
                alt13=1;
            }
            else if ( (LA13_0==268) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // Event.g:255:4: namespaceNT
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_namespaceNT_in_payloadStatement887);
                    namespaceNT63=namespaceNT();

                    state._fsp--;

                    adaptor.addChild(root_0, namespaceNT63.getTree());

                    }
                    break;
                case 2 :
                    // Event.g:255:18: payloadStringStatement
                    {
                    root_0 = (EventASTNode)adaptor.nil();

                    pushFollow(FOLLOW_payloadStringStatement_in_payloadStatement891);
                    payloadStringStatement64=payloadStringStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, payloadStringStatement64.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "payloadStatement"

    public static class payloadStringStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "payloadStringStatement"
    // Event.g:258:1: payloadStringStatement : 'payloadString' ASSIGN StringLiteral SEMICOLON -> ^( PAYLOAD_STRING_STATEMENT ^( LITERALS StringLiteral ) ) ;
    public final EventParser.payloadStringStatement_return payloadStringStatement() throws RecognitionException {
        EventParser.payloadStringStatement_return retval = new EventParser.payloadStringStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal65=null;
        Token ASSIGN66=null;
        Token StringLiteral67=null;
        Token SEMICOLON68=null;

        EventASTNode string_literal65_tree=null;
        EventASTNode ASSIGN66_tree=null;
        EventASTNode StringLiteral67_tree=null;
        EventASTNode SEMICOLON68_tree=null;
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_268=new RewriteRuleTokenStream(adaptor,"token 268");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");

        try {
            // Event.g:259:2: ( 'payloadString' ASSIGN StringLiteral SEMICOLON -> ^( PAYLOAD_STRING_STATEMENT ^( LITERALS StringLiteral ) ) )
            // Event.g:259:4: 'payloadString' ASSIGN StringLiteral SEMICOLON
            {
            string_literal65=(Token)match(input,268,FOLLOW_268_in_payloadStringStatement903);  
            stream_268.add(string_literal65);

            ASSIGN66=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_payloadStringStatement905);  
            stream_ASSIGN.add(ASSIGN66);

            StringLiteral67=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_payloadStringStatement907);  
            stream_StringLiteral.add(StringLiteral67);

            SEMICOLON68=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_payloadStringStatement909);  
            stream_SEMICOLON.add(SEMICOLON68);



            // AST REWRITE
            // elements: StringLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 260:3: -> ^( PAYLOAD_STRING_STATEMENT ^( LITERALS StringLiteral ) )
            {
                // Event.g:260:6: ^( PAYLOAD_STRING_STATEMENT ^( LITERALS StringLiteral ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PAYLOAD_STRING_STATEMENT, "PAYLOAD_STRING_STATEMENT"), root_1);

                // Event.g:260:33: ^( LITERALS StringLiteral )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LITERALS, "LITERALS"), root_2);

                adaptor.addChild(root_2, stream_StringLiteral.nextNode());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "payloadStringStatement"

    public static class namespaceNT_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namespaceNT"
    // Event.g:263:1: namespaceNT : 'namespaces' namespaceNTBlock -> ^( NAMESPACES_BLOCK namespaceNTBlock ) ;
    public final EventParser.namespaceNT_return namespaceNT() throws RecognitionException {
        EventParser.namespaceNT_return retval = new EventParser.namespaceNT_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token string_literal69=null;
        EventParser.namespaceNTBlock_return namespaceNTBlock70 = null;


        EventASTNode string_literal69_tree=null;
        RewriteRuleTokenStream stream_269=new RewriteRuleTokenStream(adaptor,"token 269");
        RewriteRuleSubtreeStream stream_namespaceNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule namespaceNTBlock");
        try {
            // Event.g:264:2: ( 'namespaces' namespaceNTBlock -> ^( NAMESPACES_BLOCK namespaceNTBlock ) )
            // Event.g:264:4: 'namespaces' namespaceNTBlock
            {
            string_literal69=(Token)match(input,269,FOLLOW_269_in_namespaceNT935);  
            stream_269.add(string_literal69);

            pushFollow(FOLLOW_namespaceNTBlock_in_namespaceNT937);
            namespaceNTBlock70=namespaceNTBlock();

            state._fsp--;

            stream_namespaceNTBlock.add(namespaceNTBlock70.getTree());


            // AST REWRITE
            // elements: namespaceNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 265:2: -> ^( NAMESPACES_BLOCK namespaceNTBlock )
            {
                // Event.g:265:5: ^( NAMESPACES_BLOCK namespaceNTBlock )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAMESPACES_BLOCK, "NAMESPACES_BLOCK"), root_1);

                adaptor.addChild(root_1, stream_namespaceNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "namespaceNT"

    public static class namespaceNTBlock_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namespaceNTBlock"
    // Event.g:268:1: namespaceNTBlock : LBRACE ( namespaceStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( namespaceStatement )* ) ) ;
    public final EventParser.namespaceNTBlock_return namespaceNTBlock() throws RecognitionException {
        EventParser.namespaceNTBlock_return retval = new EventParser.namespaceNTBlock_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token LBRACE71=null;
        Token RBRACE73=null;
        EventParser.namespaceStatement_return namespaceStatement72 = null;


        EventASTNode LBRACE71_tree=null;
        EventASTNode RBRACE73_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_namespaceStatement=new RewriteRuleSubtreeStream(adaptor,"rule namespaceStatement");
        try {
            // Event.g:269:2: ( LBRACE ( namespaceStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( namespaceStatement )* ) ) )
            // Event.g:269:4: LBRACE ( namespaceStatement )* RBRACE
            {
            LBRACE71=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_namespaceNTBlock957);  
            stream_LBRACE.add(LBRACE71);

             pushScope(ATTRIBUTE_SCOPE); 
            // Event.g:270:5: ( namespaceStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==Identifier||LA14_0==NullLiteral||(LA14_0>=184 && LA14_0<=232)) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // Event.g:270:5: namespaceStatement
            	    {
            	    pushFollow(FOLLOW_namespaceStatement_in_namespaceNTBlock965);
            	    namespaceStatement72=namespaceStatement();

            	    state._fsp--;

            	    stream_namespaceStatement.add(namespaceStatement72.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            RBRACE73=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_namespaceNTBlock968);  
            stream_RBRACE.add(RBRACE73);

             popScope(); 


            // AST REWRITE
            // elements: namespaceStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 271:3: -> ^( BLOCK ^( STATEMENTS ( namespaceStatement )* ) )
            {
                // Event.g:271:6: ^( BLOCK ^( STATEMENTS ( namespaceStatement )* ) )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Event.g:271:14: ^( STATEMENTS ( namespaceStatement )* )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Event.g:271:27: ( namespaceStatement )*
                while ( stream_namespaceStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_namespaceStatement.nextTree());

                }
                stream_namespaceStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "namespaceNTBlock"

    public static class namespaceStatement_return extends ParserRuleReturnScope {
        EventASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namespaceStatement"
    // Event.g:274:1: namespaceStatement : identifier ASSIGN ns= StringLiteral ( 'location' ASSIGN loc= StringLiteral )? SEMICOLON -> ^( NAMESPACE_STATEMENT ^( PREFIX identifier ) ^( NAMESPACE $ns) ( ^( LOCATION $loc) )? ) ;
    public final EventParser.namespaceStatement_return namespaceStatement() throws RecognitionException {
        EventParser.namespaceStatement_return retval = new EventParser.namespaceStatement_return();
        retval.start = input.LT(1);

        EventASTNode root_0 = null;

        Token ns=null;
        Token loc=null;
        Token ASSIGN75=null;
        Token string_literal76=null;
        Token ASSIGN77=null;
        Token SEMICOLON78=null;
        Event_BaseElement.identifier_return identifier74 = null;


        EventASTNode ns_tree=null;
        EventASTNode loc_tree=null;
        EventASTNode ASSIGN75_tree=null;
        EventASTNode string_literal76_tree=null;
        EventASTNode ASSIGN77_tree=null;
        EventASTNode SEMICOLON78_tree=null;
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_270=new RewriteRuleTokenStream(adaptor,"token 270");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Event.g:275:2: ( identifier ASSIGN ns= StringLiteral ( 'location' ASSIGN loc= StringLiteral )? SEMICOLON -> ^( NAMESPACE_STATEMENT ^( PREFIX identifier ) ^( NAMESPACE $ns) ( ^( LOCATION $loc) )? ) )
            // Event.g:275:4: identifier ASSIGN ns= StringLiteral ( 'location' ASSIGN loc= StringLiteral )? SEMICOLON
            {
            pushFollow(FOLLOW_identifier_in_namespaceStatement1001);
            identifier74=identifier();

            state._fsp--;

            stream_identifier.add(identifier74.getTree());
            ASSIGN75=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_namespaceStatement1003);  
            stream_ASSIGN.add(ASSIGN75);

            ns=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_namespaceStatement1007);  
            stream_StringLiteral.add(ns);

            // Event.g:275:39: ( 'location' ASSIGN loc= StringLiteral )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==270) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // Event.g:275:40: 'location' ASSIGN loc= StringLiteral
                    {
                    string_literal76=(Token)match(input,270,FOLLOW_270_in_namespaceStatement1010);  
                    stream_270.add(string_literal76);

                    ASSIGN77=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_namespaceStatement1012);  
                    stream_ASSIGN.add(ASSIGN77);

                    loc=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_namespaceStatement1016);  
                    stream_StringLiteral.add(loc);


                    }
                    break;

            }

            SEMICOLON78=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_namespaceStatement1020);  
            stream_SEMICOLON.add(SEMICOLON78);



            // AST REWRITE
            // elements: ns, identifier, loc
            // token labels: ns, loc
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_ns=new RewriteRuleTokenStream(adaptor,"token ns",ns);
            RewriteRuleTokenStream stream_loc=new RewriteRuleTokenStream(adaptor,"token loc",loc);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (EventASTNode)adaptor.nil();
            // 276:3: -> ^( NAMESPACE_STATEMENT ^( PREFIX identifier ) ^( NAMESPACE $ns) ( ^( LOCATION $loc) )? )
            {
                // Event.g:276:6: ^( NAMESPACE_STATEMENT ^( PREFIX identifier ) ^( NAMESPACE $ns) ( ^( LOCATION $loc) )? )
                {
                EventASTNode root_1 = (EventASTNode)adaptor.nil();
                root_1 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAMESPACE_STATEMENT, "NAMESPACE_STATEMENT"), root_1);

                // Event.g:276:28: ^( PREFIX identifier )
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(PREFIX, "PREFIX"), root_2);

                adaptor.addChild(root_2, stream_identifier.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:276:49: ^( NAMESPACE $ns)
                {
                EventASTNode root_2 = (EventASTNode)adaptor.nil();
                root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(NAMESPACE, "NAMESPACE"), root_2);

                adaptor.addChild(root_2, stream_ns.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // Event.g:276:66: ( ^( LOCATION $loc) )?
                if ( stream_loc.hasNext() ) {
                    // Event.g:276:66: ^( LOCATION $loc)
                    {
                    EventASTNode root_2 = (EventASTNode)adaptor.nil();
                    root_2 = (EventASTNode)adaptor.becomeRoot((EventASTNode)adaptor.create(LOCATION, "LOCATION"), root_2);

                    adaptor.addChild(root_2, stream_loc.nextNode());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_loc.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (EventASTNode)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

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
    // $ANTLR end "namespaceStatement"

    // Delegated rules
    public Event_BaseElement.semiColon_return semiColon() throws RecognitionException { return gBaseElement.semiColon(); }
    public Event_BaseElement.predicateStatement_return predicateStatement() throws RecognitionException { return gBaseElement.predicateStatement(); }
    public Event_BaseElement.blockStatement_return blockStatement() throws RecognitionException { return gBaseElement.blockStatement(); }
    public Event_BaseElement.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException { return gBaseElement.localVariableDeclaration(); }
    public Event_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException { return gBaseElement.localInitializerArrayLiteral(); }
    public Event_BaseElement.predicate_return predicate() throws RecognitionException { return gBaseElement.predicate(); }
    public Event_BaseElement.unaryExpression_return unaryExpression() throws RecognitionException { return gBaseElement.unaryExpression(); }
    public Event_BaseElement.arrayAllocator_return arrayAllocator() throws RecognitionException { return gBaseElement.arrayAllocator(); }
    public Event_BaseElement.listStatementExpression_return listStatementExpression() throws RecognitionException { return gBaseElement.listStatementExpression(); }
    public Event_BaseElement.type_return type() throws RecognitionException { return gBaseElement.type(); }
    public Event_BaseElement.block_return block() throws RecognitionException { return gBaseElement.block(); }
    public Event_BaseElement.statement_return statement() throws RecognitionException { return gBaseElement.statement(); }
    public Event_BaseElement.rangeEnd_return rangeEnd() throws RecognitionException { return gBaseElement.rangeEnd(); }
    public Event_BaseElement.primarySuffix_return primarySuffix(RulesASTNode type) throws RecognitionException { return gBaseElement.primarySuffix(type); }
    public Event_BaseElement.throwStatement_return throwStatement() throws RecognitionException { return gBaseElement.throwStatement(); }
    public Event_BaseElement.thenStatements_return thenStatements() throws RecognitionException { return gBaseElement.thenStatements(); }
    public Event_BaseElement.thenNT_return thenNT() throws RecognitionException { return gBaseElement.thenNT(); }
    public Event_BaseElement.declarator_return declarator() throws RecognitionException { return gBaseElement.declarator(); }
    public Event_BaseElement.breakStatement_return breakStatement() throws RecognitionException { return gBaseElement.breakStatement(); }
    public Event_BaseElement.emptyStatement_return emptyStatement() throws RecognitionException { return gBaseElement.emptyStatement(); }
    public Event_BaseElement.continueStatement_return continueStatement() throws RecognitionException { return gBaseElement.continueStatement(); }
    public Event_BaseElement.arrayLiteral_return arrayLiteral() throws RecognitionException { return gBaseElement.arrayLiteral(); }
    public Event_BaseElement.xsltLiteral_return xsltLiteral() throws RecognitionException { return gBaseElement.xsltLiteral(); }
    public Event_BaseElement.integerLiteral_return integerLiteral() throws RecognitionException { return gBaseElement.integerLiteral(); }
    public Event_BaseElement.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException { return gBaseElement.arrayAccessSuffix(); }
    public Event_BaseElement.scopeNT_return scopeNT() throws RecognitionException { return gBaseElement.scopeNT(); }
    public Event_BaseElement.domainSpec_return domainSpec() throws RecognitionException { return gBaseElement.domainSpec(); }
    public Event_BaseElement.mappingBlock_return mappingBlock() throws RecognitionException { return gBaseElement.mappingBlock(); }
    public Event_BaseElement.ifRule_return ifRule() throws RecognitionException { return gBaseElement.ifRule(); }
    public Event_BaseElement.lineStatement_return lineStatement() throws RecognitionException { return gBaseElement.lineStatement(); }
    public Event_BaseElement.relationalExpression_return relationalExpression() throws RecognitionException { return gBaseElement.relationalExpression(); }
    public Event_BaseElement.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException { return gBaseElement.comparisonNoLHS(); }
    public Event_BaseElement.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gBaseElement.multiplicativeExpression(); }
    public Event_BaseElement.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException { return gBaseElement.conditionalAndExpression(); }
    public Event_BaseElement.voidLiteral_return voidLiteral() throws RecognitionException { return gBaseElement.voidLiteral(); }
    public Event_BaseElement.booleanLiteral_return booleanLiteral() throws RecognitionException { return gBaseElement.booleanLiteral(); }
    public Event_BaseElement.catchRule_return catchRule() throws RecognitionException { return gBaseElement.catchRule(); }
    public Event_BaseElement.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException { return gBaseElement.emptyBodyStatement(); }
    public Event_BaseElement.scopeDeclarator_return scopeDeclarator() throws RecognitionException { return gBaseElement.scopeDeclarator(); }
    public Event_BaseElement.statementExpressionList_return statementExpressionList() throws RecognitionException { return gBaseElement.statementExpressionList(); }
    public Event_BaseElement.primaryPrefix_return primaryPrefix() throws RecognitionException { return gBaseElement.primaryPrefix(); }
    public Event_BaseElement.methodName_return methodName() throws RecognitionException { return gBaseElement.methodName(); }
    public Event_BaseElement.variableDeclarator_return variableDeclarator(RulesASTNode type) throws RecognitionException { return gBaseElement.variableDeclarator(type); }
    public Event_BaseElement.additiveExpression_return additiveExpression() throws RecognitionException { return gBaseElement.additiveExpression(); }
    public Event_BaseElement.thenStatement_return thenStatement() throws RecognitionException { return gBaseElement.thenStatement(); }
    public Event_BaseElement.argumentList_return argumentList() throws RecognitionException { return gBaseElement.argumentList(); }
    public Event_BaseElement.expressionName_return expressionName() throws RecognitionException { return gBaseElement.expressionName(); }
    public Event_BaseElement.scopeNTBlock_return scopeNTBlock() throws RecognitionException { return gBaseElement.scopeNTBlock(); }
    public Event_BaseElement.name_return name(int nameType) throws RecognitionException { return gBaseElement.name(nameType); }
    public Event_BaseElement.declareNT_return declareNT() throws RecognitionException { return gBaseElement.declareNT(); }
    public Event_BaseElement.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException { return gBaseElement.fieldAccessSuffix(); }
    public Event_BaseElement.forRule_return forRule() throws RecognitionException { return gBaseElement.forRule(); }
    public Event_BaseElement.expression_return expression() throws RecognitionException { return gBaseElement.expression(); }
    public Event_BaseElement.predicateStatements_return predicateStatements() throws RecognitionException { return gBaseElement.predicateStatements(); }
    public Event_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException { return gBaseElement.typeAdditionalArrayDim(); }
    public Event_BaseElement.primaryExpression_return primaryExpression() throws RecognitionException { return gBaseElement.primaryExpression(); }
    public Event_BaseElement.conditionBlock_return conditionBlock() throws RecognitionException { return gBaseElement.conditionBlock(); }
    public Event_BaseElement.rangeStart_return rangeStart() throws RecognitionException { return gBaseElement.rangeStart(); }
    public Event_BaseElement.nameOrPrimitiveType_return nameOrPrimitiveType() throws RecognitionException { return gBaseElement.nameOrPrimitiveType(); }
    public Event_BaseElement.setMembershipExpression_return setMembershipExpression() throws RecognitionException { return gBaseElement.setMembershipExpression(); }
    public Event_BaseElement.returnStatement_return returnStatement() throws RecognitionException { return gBaseElement.returnStatement(); }
    public Event_BaseElement.identifier_return identifier() throws RecognitionException { return gBaseElement.identifier(); }
    public Event_BaseElement.thenNTBlock_return thenNTBlock() throws RecognitionException { return gBaseElement.thenNTBlock(); }
    public Event_BaseElement.primitiveType_return primitiveType() throws RecognitionException { return gBaseElement.primitiveType(); }
    public Event_BaseElement.typeName_return typeName() throws RecognitionException { return gBaseElement.typeName(); }
    public Event_BaseElement.statementExpression_return statementExpression() throws RecognitionException { return gBaseElement.statementExpression(); }
    public Event_BaseElement.assignmentOp_return assignmentOp() throws RecognitionException { return gBaseElement.assignmentOp(); }
    public Event_BaseElement.tryCatchFinally_return tryCatchFinally() throws RecognitionException { return gBaseElement.tryCatchFinally(); }
    public Event_BaseElement.equalityExpression_return equalityExpression() throws RecognitionException { return gBaseElement.equalityExpression(); }
    public Event_BaseElement.keywordIdentifier_return keywordIdentifier() throws RecognitionException { return gBaseElement.keywordIdentifier(); }
    public Event_BaseElement.standaloneExpression_return standaloneExpression() throws RecognitionException { return gBaseElement.standaloneExpression(); }
    public Event_BaseElement.actionContextStatement_return actionContextStatement() throws RecognitionException { return gBaseElement.actionContextStatement(); }
    public Event_BaseElement.declareNTBlock_return declareNTBlock() throws RecognitionException { return gBaseElement.declareNTBlock(); }
    public Event_BaseElement.primitive_return primitive() throws RecognitionException { return gBaseElement.primitive(); }
    public Event_BaseElement.whileRule_return whileRule() throws RecognitionException { return gBaseElement.whileRule(); }
    public Event_BaseElement.argumentsSuffix_return argumentsSuffix() throws RecognitionException { return gBaseElement.argumentsSuffix(); }
    public Event_BaseElement.literal_return literal() throws RecognitionException { return gBaseElement.literal(); }
    public Event_BaseElement.finallyRule_return finallyRule() throws RecognitionException { return gBaseElement.finallyRule(); }
    public Event_BaseElement.rangeExpression_return rangeExpression() throws RecognitionException { return gBaseElement.rangeExpression(); }


 

    public static final BitSet FOLLOW_compilationUnit_in_startRule151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HeaderSection_in_compilationUnit166 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventDefinition_in_compilationUnit171 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_compilationUnit175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_231_in_eventDefinition186 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_eventDefinition190 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_201_in_eventDefinition194 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_eventDefinition198 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_eventDefinition203 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000C04L});
    public static final BitSet FOLLOW_eventNT_in_eventDefinition205 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_eventDefinition207 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertiesNT_in_eventNT258 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000C04L});
    public static final BitSet FOLLOW_attributesNT_in_eventNT263 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000C04L});
    public static final BitSet FOLLOW_expiryActionNT_in_eventNT268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000C04L});
    public static final BitSet FOLLOW_payloadNT_in_eventNT273 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000C04L});
    public static final BitSet FOLLOW_255_in_attributesNT286 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_attributesNTBlock_in_attributesNT288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_attributesNTBlock310 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000013L});
    public static final BitSet FOLLOW_attributeStatement_in_attributesNTBlock319 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000013L});
    public static final BitSet FOLLOW_RBRACE_in_attributesNTBlock322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_defaultDestinationStatement_in_attributeStatement350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ttlStatement_in_attributeStatement355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_retryOnExceptionStatement_in_attributeStatement360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_256_in_retryOnExceptionStatement372 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_retryOnExceptionStatement374 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_retryOnExceptionStatement376 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_retryOnExceptionStatement378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_257_in_defaultDestinationStatement403 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_defaultDestinationStatement405 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_defaultDestinationStatement407 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_defaultDestinationStatement410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_258_in_propertiesNT436 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_propertiesNTBlock_in_propertiesNT438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_propertiesNTBlock459 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_propertiesBodyDeclaration_in_propertiesNTBlock468 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x0F0001FFFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_propertiesNTBlock471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_propertiesBodyDeclaration503 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_propertiesBodyDeclaration509 = new BitSet(new long[]{0x0000000000000000L,0x0020080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_propertiesBodyDeclaration518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyBlock_in_propertiesBodyDeclaration522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_propertyBlock571 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_propertyAttribute_in_propertyBlock578 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_RBRACE_in_propertyBlock581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainStatement_in_propertyAttribute610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_259_in_domainStatement622 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_domainStatement624 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_domainStatement628 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_236_in_domainStatement632 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_domainStatement636 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_domainStatement641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_260_in_ttlStatement669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_ttlStatement671 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_integerLiteral_in_ttlStatement673 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_ttlUnits_in_ttlStatement675 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_ttlStatement677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_ttlUnits0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_266_in_expiryActionNT741 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_expiryActionNTBlock_in_expiryActionNT743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_expiryActionNTBlock764 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x5000000000000000L});
    public static final BitSet FOLLOW_expiryActionStatement_in_expiryActionNTBlock772 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x5000000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_expiryActionNTBlock775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declareNT_in_expiryActionStatement807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenNT_in_expiryActionStatement811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_267_in_payloadNT823 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_payloadNTBlock_in_payloadNT825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_payloadNTBlock846 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_payloadStatement_in_payloadNTBlock854 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_RBRACE_in_payloadNTBlock857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namespaceNT_in_payloadStatement887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_payloadStringStatement_in_payloadStatement891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_268_in_payloadStringStatement903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_payloadStringStatement905 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_payloadStringStatement907 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_payloadStringStatement909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_269_in_namespaceNT935 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_namespaceNTBlock_in_namespaceNT937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_namespaceNTBlock957 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_namespaceStatement_in_namespaceNTBlock965 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFF00000000000002L,0x000001FFFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_namespaceNTBlock968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_in_namespaceStatement1001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_namespaceStatement1003 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_namespaceStatement1007 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_270_in_namespaceStatement1010 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_namespaceStatement1012 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_namespaceStatement1016 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_namespaceStatement1020 = new BitSet(new long[]{0x0000000000000002L});

}