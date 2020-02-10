// $ANTLR 3.2 Sep 23, 2009 12:02:23 Concept.g 2014-03-20 17:49:08
 
package com.tibco.cep.studio.core.grammar.concept; 

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteEmptyStreamException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class ConceptParser extends BaseRulesParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_TEMPLATE", "RULE_TEMPLATE_DECL", "TEMPLATE_DECLARATOR", "BINDINGS_BLOCK", "BINDING_VIEW_STATEMENT", "BINDINGS_VIEWS_BLOCK", "BINDING_STATEMENT", "DOMAIN_MODEL", "DOMAIN_MODEL_STATEMENT", "ACTION_CONTEXT_BLOCK", "ACTION_CONTEXT_STATEMENT", "ACTION_TYPE", "QUALIFIER", "QUALIFIED_NAME", "SIMPLE_NAME", "LOCAL_VARIABLE_DECL", "DECLARATORS", "VARIABLE_DECLARATOR", "RULE", "RULE_FUNCTION", "RULE_DECL", "NAME", "RULE_BLOCK", "ATTRIBUTE_BLOCK", "STATEMENTS", "DECLARE_BLOCK", "RULE_FUNC_DECL", "SCOPE_BLOCK", "WHEN_BLOCK", "PREDICATES", "BLOCKS", "THEN_BLOCK", "BODY_BLOCK", "SCOPE_DECLARATOR", "TYPE", "DECLARATOR", "VIRTUAL", "PRIMITIVE_TYPE", "ANNOTATION", "ARGS", "METHOD_CALL", "PRIMARY_ASSIGNMENT_EXPRESSION", "SUFFIX", "EXPRESSION", "IF_RULE", "WHILE_RULE", "FOR_RULE", "ELSE_STATEMENT", "STATEMENT", "RETURN_STATEMENT", "FOR_LOOP", "RETURN_TYPE", "MAPPING_BLOCK", "PRIORITY_STATEMENT", "PRIORITY", "VALIDITY_STATEMENT", "VALIDITY", "RANK_STATEMENT", "REQUEUE_STATEMENT", "FORWARD_CHAIN_STATEMENT", "BACKWARD_CHAIN_STATEMENT", "LASTMOD_STATEMENT", "ALIAS_STATEMENT", "LITERAL", "PREDICATE_STATEMENT", "RANGE_EXPRESSION", "RANGE_START", "RANGE_END", "SET_MEMBERSHIP_EXPRESSION", "EXPRESSIONS", "BREAK_STATEMENT", "CONTINUE_STATEMENT", "THROW_STATEMENT", "TRY_RULE", "BLOCK", "CATCH_RULE", "IDENTIFIER", "FINALLY_RULE", "ARRAY_ACCESS_SUFFIX", "INITIALIZER", "LOCAL_INITIALIZER", "ARRAY_LITERAL", "ARRAY_ALLOCATOR", "TRY_STATEMET", "BODY", "CATCH_CLAUSE", "FINALLY_CLAUSE", "TRY_STATEMENT", "HEADER", "VOID_LITERAL", "MODIFIERS", "BINARY_RELATION_NODE", "UNARY_EXPRESSION_NODE", "LHS", "RHS", "OPERATOR", "PRIMARY_EXPRESSION", "PREFIX", "SUFFIXES", "ASSIGNMENT_SUFFIX", "SUFFIX_EXPRESSION", "MappingEnd", "Identifier", "SEMICOLON", "OR", "AND", "EQ", "NE", "INSTANCE_OF", "LT", "GT", "LE", "GE", "LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIVIDE", "MOD", "POUND", "DOT", "ANNOTATE", "FloatingPointLiteral", "StringLiteral", "NullLiteral", "DecimalLiteral", "HexLiteral", "TRUE", "FALSE", "WS", "INCR", "DECR", "ASSIGN", "PLUS_EQUAL", "MINUS_EQUAL", "MULT_EQUAL", "DIV_EQUAL", "MOD_EQUAL", "HEADER_START", "HeaderSection", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "CharacterLiteral", "UnicodeEscape", "OctalEscape", "Letter", "JavaIDDigit", "COMMENT", "LINE_COMMENT", "CONCEPT_DEFINITION", "PARENT", "PROPERTY_DECLARATION", "PROPERTIES_BLOCK", "PROPERTY_BLOCK", "ATTRIBUTE", "ATTRIBUTES_BLOCK", "STATE_MACHINES_BLOCK", "METADATA_BLOCK", "AUTO_START_STATEMENT", "CONTAINED_STATEMENT", "POLICY_STATEMENT", "HISTORY_STATEMENT", "DOMAIN_STATEMENT", "DOMAIN_NAMES", "STATE_MACHINE_STATEMENT", "META_DATA_PROPERTY_GROUP", "META_DATA_PROPERTY", "'<#mapping>'", "'body'", "'validity'", "'lock'", "'exists'", "'and'", "'key'", "'alias'", "'rank'", "'virtual'", "'abstract'", "'byte'", "'case'", "'char'", "'class'", "'const'", "'default'", "'do'", "'extends'", "'final'", "'float'", "'goto'", "'implements'", "'import'", "'interface'", "'moveto'", "'native'", "'new'", "'package'", "'private'", "'protected'", "'public'", "'short'", "'static'", "'strictfp'", "'super'", "'switch'", "'synchronized'", "'this'", "'throws'", "'transient'", "'volatile'", "'create'", "'modify'", "'call'", "'views'", "'bindings'", "'display'", "'event'", "'concept'", "'\"\"xslt://'", "'\"'", "'void'", "','", "'return'", "'break'", "'continue'", "'throw'", "'if'", "'else'", "'while'", "'for'", "'try'", "'catch'", "'finally'", "'boolean'", "'int'", "'long'", "'double'", "'declare'", "'scope'", "'then'", "'attributes'", "'autoStartStateModel'", "'properties'", "'contained'", "'policy'", "'CHANGES_ONLY'", "'ALL_VALUES'", "'history'", "'domain'", "'stateMachines'", "'metadata'"
    };
    public static final int T__259=259;
    public static final int STATE_MACHINE_STATEMENT=176;
    public static final int MOD=123;
    public static final int T__258=258;
    public static final int T__257=257;
    public static final int T__260=260;
    public static final int T__261=261;
    public static final int TRY_STATEMENT=91;
    public static final int EOF=-1;
    public static final int STATEMENT=52;
    public static final int META_DATA_PROPERTY_GROUP=177;
    public static final int TYPE=38;
    public static final int ARRAY_ALLOCATOR=86;
    public static final int RPAREN=146;
    public static final int T__247=247;
    public static final int MappingEnd=105;
    public static final int T__246=246;
    public static final int T__249=249;
    public static final int T__248=248;
    public static final int INSTANCE_OF=112;
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
    public static final int HISTORY_STATEMENT=173;
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
    public static final int VOID_LITERAL=93;
    public static final int BLOCKS=34;
    public static final int BREAK_STATEMENT=74;
    public static final int PROPERTY_DECLARATION=163;
    public static final int T__215=215;
    public static final int T__216=216;
    public static final int T__213=213;
    public static final int T__214=214;
    public static final int LBRACK=147;
    public static final int T__219=219;
    public static final int T__217=217;
    public static final int DIV_EQUAL=141;
    public static final int TEMPLATE_DECLARATOR=6;
    public static final int T__218=218;
    public static final int DOMAIN_STATEMENT=174;
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
    public static final int STATE_MACHINES_BLOCK=168;
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
    public static final int CONCEPT_DEFINITION=161;
    public static final int T__232=232;
    public static final int T__231=231;
    public static final int T__234=234;
    public static final int OctalEscape=156;
    public static final int T__233=233;
    public static final int BINDING_VIEW_STATEMENT=8;
    public static final int THROW_STATEMENT=76;
    public static final int BINDINGS_BLOCK=7;
    public static final int T__230=230;
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
    public static final int DOMAIN_NAMES=175;
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
    public static final int METADATA_BLOCK=169;
    public static final int DECLARATORS=20;
    public static final int LOCAL_INITIALIZER=84;
    public static final int POLICY_STATEMENT=172;
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
    public static final int ATTRIBUTES_BLOCK=167;
    public static final int RULE_TEMPLATE=4;
    public static final int OR=108;
    public static final int BACKWARD_CHAIN_STATEMENT=64;
    public static final int BINARY_RELATION_NODE=95;
    public static final int PRIORITY_STATEMENT=57;
    public static final int CONTINUE_STATEMENT=75;
    public static final int META_DATA_PROPERTY=178;
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
    public static final int T__180=180;
    public static final int AUTO_START_STATEMENT=170;
    public static final int T__182=182;
    public static final int ALIAS_STATEMENT=66;
    public static final int T__181=181;
    public static final int LITERAL=67;
    public static final int DecimalLiteral=130;
    public static final int TRUE=132;
    public static final int SIMPLE_NAME=18;
    public static final int T__179=179;
    public static final int DOMAIN_MODEL_STATEMENT=12;
    public static final int CONTAINED_STATEMENT=171;
    public static final int UnicodeEscape=155;
    public static final int ASSIGN=137;
    public static final int DECR=136;
    public static final int BINDINGS_VIEWS_BLOCK=9;
    public static final int LE=115;

    // delegates
    public Concept_BaseElement gBaseElement;
    // delegators


        public ConceptParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ConceptParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gBaseElement = new Concept_BaseElement(input, state, this);         
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
        gBaseElement.setTreeAdaptor(this.adaptor);
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ConceptParser.tokenNames; }
    public String getGrammarFileName() { return "Concept.g"; }


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
    		return ((ConceptLexer)getTokenStream().getTokenSource()).getHeaderNode();
    	}



    public static class startRule_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "startRule"
    // Concept.g:128:1: startRule : compilationUnit ;
    public final ConceptParser.startRule_return startRule() throws RecognitionException {
        ConceptParser.startRule_return retval = new ConceptParser.startRule_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        ConceptParser.compilationUnit_return compilationUnit1 = null;



        try {
            // Concept.g:132:2: ( compilationUnit )
            // Concept.g:132:4: compilationUnit
            {
            root_0 = (ConceptASTNode)adaptor.nil();

            pushFollow(FOLLOW_compilationUnit_in_startRule138);
            compilationUnit1=compilationUnit();

            state._fsp--;

            adaptor.addChild(root_0, compilationUnit1.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compilationUnit"
    // Concept.g:135:1: compilationUnit : ( HeaderSection )? conceptDefinition EOF ;
    public final ConceptParser.compilationUnit_return compilationUnit() throws RecognitionException {
        ConceptParser.compilationUnit_return retval = new ConceptParser.compilationUnit_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token HeaderSection2=null;
        Token EOF4=null;
        ConceptParser.conceptDefinition_return conceptDefinition3 = null;


        ConceptASTNode HeaderSection2_tree=null;
        ConceptASTNode EOF4_tree=null;

        try {
            // Concept.g:136:2: ( ( HeaderSection )? conceptDefinition EOF )
            // Concept.g:137:3: ( HeaderSection )? conceptDefinition EOF
            {
            root_0 = (ConceptASTNode)adaptor.nil();

            // Concept.g:137:3: ( HeaderSection )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==HeaderSection) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Concept.g:137:3: HeaderSection
                    {
                    HeaderSection2=(Token)match(input,HeaderSection,FOLLOW_HeaderSection_in_compilationUnit153); 
                    HeaderSection2_tree = (ConceptASTNode)adaptor.create(HeaderSection2);
                    adaptor.addChild(root_0, HeaderSection2_tree);


                    }
                    break;

            }

            pushFollow(FOLLOW_conceptDefinition_in_compilationUnit158);
            conceptDefinition3=conceptDefinition();

            state._fsp--;

            adaptor.addChild(root_0, conceptDefinition3.getTree());
            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_compilationUnit162); 
            EOF4_tree = (ConceptASTNode)adaptor.create(EOF4);
            adaptor.addChild(root_0, EOF4_tree);


            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class conceptDefinition_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conceptDefinition"
    // Concept.g:142:1: conceptDefinition : 'concept' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE conceptNT RBRACE -> ^( CONCEPT_DEFINITION[\"concept\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS conceptNT ) ) ;
    public final ConceptParser.conceptDefinition_return conceptDefinition() throws RecognitionException {
        ConceptParser.conceptDefinition_return retval = new ConceptParser.conceptDefinition_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal5=null;
        Token string_literal6=null;
        Token LBRACE7=null;
        Token RBRACE9=null;
        Concept_BaseElement.name_return nm = null;

        Concept_BaseElement.name_return parent = null;

        ConceptParser.conceptNT_return conceptNT8 = null;


        ConceptASTNode string_literal5_tree=null;
        ConceptASTNode string_literal6_tree=null;
        ConceptASTNode LBRACE7_tree=null;
        ConceptASTNode RBRACE9_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_228=new RewriteRuleTokenStream(adaptor,"token 228");
        RewriteRuleTokenStream stream_197=new RewriteRuleTokenStream(adaptor,"token 197");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_conceptNT=new RewriteRuleSubtreeStream(adaptor,"rule conceptNT");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Concept.g:143:2: ( 'concept' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE conceptNT RBRACE -> ^( CONCEPT_DEFINITION[\"concept\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS conceptNT ) ) )
            // Concept.g:143:4: 'concept' nm= name[TYPE_RULE_DEF] ( 'extends' parent= name[TYPE_RULE_DEF] )? LBRACE conceptNT RBRACE
            {
            string_literal5=(Token)match(input,228,FOLLOW_228_in_conceptDefinition173);  
            stream_228.add(string_literal5);

            pushFollow(FOLLOW_name_in_conceptDefinition177);
            nm=name(TYPE_RULE_DEF);

            state._fsp--;

            stream_name.add(nm.getTree());
            // Concept.g:143:37: ( 'extends' parent= name[TYPE_RULE_DEF] )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==197) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Concept.g:143:38: 'extends' parent= name[TYPE_RULE_DEF]
                    {
                    string_literal6=(Token)match(input,197,FOLLOW_197_in_conceptDefinition181);  
                    stream_197.add(string_literal6);

                    pushFollow(FOLLOW_name_in_conceptDefinition185);
                    parent=name(TYPE_RULE_DEF);

                    state._fsp--;

                    stream_name.add(parent.getTree());

                    }
                    break;

            }

            LBRACE7=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_conceptDefinition190);  
            stream_LBRACE.add(LBRACE7);

            pushFollow(FOLLOW_conceptNT_in_conceptDefinition192);
            conceptNT8=conceptNT();

            state._fsp--;

            stream_conceptNT.add(conceptNT8.getTree());
            RBRACE9=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_conceptDefinition194);  
            stream_RBRACE.add(RBRACE9);

             setRuleElementType(ELEMENT_TYPES.CONCEPT); 


            // AST REWRITE
            // elements: nm, conceptNT, parent
            // token labels: 
            // rule labels: retval, parent, nm
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_parent=new RewriteRuleSubtreeStream(adaptor,"rule parent",parent!=null?parent.tree:null);
            RewriteRuleSubtreeStream stream_nm=new RewriteRuleSubtreeStream(adaptor,"rule nm",nm!=null?nm.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 145:3: -> ^( CONCEPT_DEFINITION[\"concept\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS conceptNT ) )
            {
                // Concept.g:145:6: ^( CONCEPT_DEFINITION[\"concept\"] ^( NAME[\"name\"] $nm) ( ^( PARENT[\"parent\"] $parent) )? ^( BLOCKS conceptNT ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(CONCEPT_DEFINITION, "concept"), root_1);

                // Concept.g:145:38: ^( NAME[\"name\"] $nm)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_nm.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Concept.g:146:4: ( ^( PARENT[\"parent\"] $parent) )?
                if ( stream_parent.hasNext() ) {
                    // Concept.g:146:4: ^( PARENT[\"parent\"] $parent)
                    {
                    ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                    root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(PARENT, "parent"), root_2);

                    adaptor.addChild(root_2, stream_parent.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_parent.reset();
                // Concept.g:146:33: ^( BLOCKS conceptNT )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                adaptor.addChild(root_2, stream_conceptNT.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "conceptDefinition"

    public static class conceptNT_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conceptNT"
    // Concept.g:149:1: conceptNT : ( propertiesNT | attributesNT | statemachinesNT | metadataNT )* ;
    public final ConceptParser.conceptNT_return conceptNT() throws RecognitionException {
        ConceptParser.conceptNT_return retval = new ConceptParser.conceptNT_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        ConceptParser.propertiesNT_return propertiesNT10 = null;

        ConceptParser.attributesNT_return attributesNT11 = null;

        ConceptParser.statemachinesNT_return statemachinesNT12 = null;

        ConceptParser.metadataNT_return metadataNT13 = null;



        try {
            // Concept.g:150:2: ( ( propertiesNT | attributesNT | statemachinesNT | metadataNT )* )
            // Concept.g:150:4: ( propertiesNT | attributesNT | statemachinesNT | metadataNT )*
            {
            root_0 = (ConceptASTNode)adaptor.nil();

            // Concept.g:150:4: ( propertiesNT | attributesNT | statemachinesNT | metadataNT )*
            loop3:
            do {
                int alt3=5;
                switch ( input.LA(1) ) {
                case 253:
                    {
                    alt3=1;
                    }
                    break;
                case 251:
                    {
                    alt3=2;
                    }
                    break;
                case 260:
                    {
                    alt3=3;
                    }
                    break;
                case 261:
                    {
                    alt3=4;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // Concept.g:150:5: propertiesNT
            	    {
            	    pushFollow(FOLLOW_propertiesNT_in_conceptNT245);
            	    propertiesNT10=propertiesNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, propertiesNT10.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Concept.g:151:4: attributesNT
            	    {
            	    pushFollow(FOLLOW_attributesNT_in_conceptNT250);
            	    attributesNT11=attributesNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, attributesNT11.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Concept.g:152:4: statemachinesNT
            	    {
            	    pushFollow(FOLLOW_statemachinesNT_in_conceptNT255);
            	    statemachinesNT12=statemachinesNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, statemachinesNT12.getTree());

            	    }
            	    break;
            	case 4 :
            	    // Concept.g:153:4: metadataNT
            	    {
            	    pushFollow(FOLLOW_metadataNT_in_conceptNT260);
            	    metadataNT13=metadataNT();

            	    state._fsp--;

            	    adaptor.addChild(root_0, metadataNT13.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "conceptNT"

    public static class attributesNT_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributesNT"
    // Concept.g:156:1: attributesNT : 'attributes' attributesNTBlock -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock ) ;
    public final ConceptParser.attributesNT_return attributesNT() throws RecognitionException {
        ConceptParser.attributesNT_return retval = new ConceptParser.attributesNT_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal14=null;
        ConceptParser.attributesNTBlock_return attributesNTBlock15 = null;


        ConceptASTNode string_literal14_tree=null;
        RewriteRuleTokenStream stream_251=new RewriteRuleTokenStream(adaptor,"token 251");
        RewriteRuleSubtreeStream stream_attributesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule attributesNTBlock");
        try {
            // Concept.g:157:2: ( 'attributes' attributesNTBlock -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock ) )
            // Concept.g:157:4: 'attributes' attributesNTBlock
            {
            string_literal14=(Token)match(input,251,FOLLOW_251_in_attributesNT273);  
            stream_251.add(string_literal14);

            pushFollow(FOLLOW_attributesNTBlock_in_attributesNT275);
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

            root_0 = (ConceptASTNode)adaptor.nil();
            // 158:3: -> ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock )
            {
                // Concept.g:158:6: ^( ATTRIBUTES_BLOCK[\"attributes\"] attributesNTBlock )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(ATTRIBUTES_BLOCK, "attributes"), root_1);

                adaptor.addChild(root_1, stream_attributesNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributesNTBlock"
    // Concept.g:161:1: attributesNTBlock : LBRACE ( attributeStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) ) ;
    public final ConceptParser.attributesNTBlock_return attributesNTBlock() throws RecognitionException {
        ConceptParser.attributesNTBlock_return retval = new ConceptParser.attributesNTBlock_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token LBRACE16=null;
        Token RBRACE18=null;
        ConceptParser.attributeStatement_return attributeStatement17 = null;


        ConceptASTNode LBRACE16_tree=null;
        ConceptASTNode RBRACE18_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_attributeStatement=new RewriteRuleSubtreeStream(adaptor,"rule attributeStatement");
        try {
            // Concept.g:161:19: ( LBRACE ( attributeStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) ) )
            // Concept.g:161:21: LBRACE ( attributeStatement )* RBRACE
            {
            LBRACE16=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributesNTBlock297);  
            stream_LBRACE.add(LBRACE16);

             pushScope(ATTRIBUTE_SCOPE); 
            // Concept.g:162:6: ( attributeStatement )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==252) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Concept.g:162:6: attributeStatement
            	    {
            	    pushFollow(FOLLOW_attributeStatement_in_attributesNTBlock306);
            	    attributeStatement17=attributeStatement();

            	    state._fsp--;

            	    stream_attributeStatement.add(attributeStatement17.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            RBRACE18=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_attributesNTBlock309);  
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

            root_0 = (ConceptASTNode)adaptor.nil();
            // 163:2: -> ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) )
            {
                // Concept.g:163:5: ^( BLOCK ^( STATEMENTS ( attributeStatement )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Concept.g:163:13: ^( STATEMENTS ( attributeStatement )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:163:26: ( attributeStatement )*
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

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeStatement"
    // Concept.g:166:1: attributeStatement : 'autoStartStateModel' ASSIGN booleanLiteral SEMICOLON -> ^( AUTO_START_STATEMENT ^( LITERAL booleanLiteral ) ) ;
    public final ConceptParser.attributeStatement_return attributeStatement() throws RecognitionException {
        ConceptParser.attributeStatement_return retval = new ConceptParser.attributeStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal19=null;
        Token ASSIGN20=null;
        Token SEMICOLON22=null;
        Concept_BaseElement.booleanLiteral_return booleanLiteral21 = null;


        ConceptASTNode string_literal19_tree=null;
        ConceptASTNode ASSIGN20_tree=null;
        ConceptASTNode SEMICOLON22_tree=null;
        RewriteRuleTokenStream stream_252=new RewriteRuleTokenStream(adaptor,"token 252");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_booleanLiteral=new RewriteRuleSubtreeStream(adaptor,"rule booleanLiteral");
        try {
            // Concept.g:167:2: ( 'autoStartStateModel' ASSIGN booleanLiteral SEMICOLON -> ^( AUTO_START_STATEMENT ^( LITERAL booleanLiteral ) ) )
            // Concept.g:167:4: 'autoStartStateModel' ASSIGN booleanLiteral SEMICOLON
            {
            string_literal19=(Token)match(input,252,FOLLOW_252_in_attributeStatement337);  
            stream_252.add(string_literal19);

            ASSIGN20=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeStatement339);  
            stream_ASSIGN.add(ASSIGN20);

            pushFollow(FOLLOW_booleanLiteral_in_attributeStatement341);
            booleanLiteral21=booleanLiteral();

            state._fsp--;

            stream_booleanLiteral.add(booleanLiteral21.getTree());
            SEMICOLON22=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeStatement343);  
            stream_SEMICOLON.add(SEMICOLON22);



            // AST REWRITE
            // elements: booleanLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 168:3: -> ^( AUTO_START_STATEMENT ^( LITERAL booleanLiteral ) )
            {
                // Concept.g:168:6: ^( AUTO_START_STATEMENT ^( LITERAL booleanLiteral ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(AUTO_START_STATEMENT, "AUTO_START_STATEMENT"), root_1);

                // Concept.g:168:29: ^( LITERAL booleanLiteral )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class propertiesNT_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesNT"
    // Concept.g:171:1: propertiesNT : 'properties' propertiesNTBlock -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock ) ;
    public final ConceptParser.propertiesNT_return propertiesNT() throws RecognitionException {
        ConceptParser.propertiesNT_return retval = new ConceptParser.propertiesNT_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal23=null;
        ConceptParser.propertiesNTBlock_return propertiesNTBlock24 = null;


        ConceptASTNode string_literal23_tree=null;
        RewriteRuleTokenStream stream_253=new RewriteRuleTokenStream(adaptor,"token 253");
        RewriteRuleSubtreeStream stream_propertiesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule propertiesNTBlock");
        try {
            // Concept.g:172:2: ( 'properties' propertiesNTBlock -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock ) )
            // Concept.g:172:4: 'properties' propertiesNTBlock
            {
            string_literal23=(Token)match(input,253,FOLLOW_253_in_propertiesNT369);  
            stream_253.add(string_literal23);

            pushFollow(FOLLOW_propertiesNTBlock_in_propertiesNT371);
            propertiesNTBlock24=propertiesNTBlock();

            state._fsp--;

            stream_propertiesNTBlock.add(propertiesNTBlock24.getTree());


            // AST REWRITE
            // elements: propertiesNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 173:2: -> ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock )
            {
                // Concept.g:173:5: ^( PROPERTIES_BLOCK[\"properties\"] propertiesNTBlock )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(PROPERTIES_BLOCK, "properties"), root_1);

                adaptor.addChild(root_1, stream_propertiesNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesNTBlock"
    // Concept.g:176:1: propertiesNTBlock : LBRACE ( propertiesBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) ) ;
    public final ConceptParser.propertiesNTBlock_return propertiesNTBlock() throws RecognitionException {
        ConceptParser.propertiesNTBlock_return retval = new ConceptParser.propertiesNTBlock_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token LBRACE25=null;
        Token RBRACE27=null;
        ConceptParser.propertiesBodyDeclaration_return propertiesBodyDeclaration26 = null;


        ConceptASTNode LBRACE25_tree=null;
        ConceptASTNode RBRACE27_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_propertiesBodyDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule propertiesBodyDeclaration");
        try {
            // Concept.g:176:19: ( LBRACE ( propertiesBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) ) )
            // Concept.g:176:21: LBRACE ( propertiesBodyDeclaration )* RBRACE
            {
            LBRACE25=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_propertiesNTBlock392);  
            stream_LBRACE.add(LBRACE25);

             pushScope(ATTRIBUTE_SCOPE); 
            // Concept.g:177:6: ( propertiesBodyDeclaration )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==Identifier||LA5_0==NullLiteral||(LA5_0>=180 && LA5_0<=228)||(LA5_0>=244 && LA5_0<=247)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Concept.g:177:6: propertiesBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_propertiesBodyDeclaration_in_propertiesNTBlock401);
            	    propertiesBodyDeclaration26=propertiesBodyDeclaration();

            	    state._fsp--;

            	    stream_propertiesBodyDeclaration.add(propertiesBodyDeclaration26.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            RBRACE27=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_propertiesNTBlock404);  
            stream_RBRACE.add(RBRACE27);

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

            root_0 = (ConceptASTNode)adaptor.nil();
            // 178:2: -> ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) )
            {
                // Concept.g:178:5: ^( BLOCK ^( STATEMENTS ( propertiesBodyDeclaration )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Concept.g:178:13: ^( STATEMENTS ( propertiesBodyDeclaration )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:178:26: ( propertiesBodyDeclaration )*
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

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertiesBodyDeclaration"
    // Concept.g:181:1: propertiesBodyDeclaration : t= type id= identifier ( SEMICOLON | propertyBlock ) -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? ) ;
    public final ConceptParser.propertiesBodyDeclaration_return propertiesBodyDeclaration() throws RecognitionException {
        ConceptParser.propertiesBodyDeclaration_return retval = new ConceptParser.propertiesBodyDeclaration_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token SEMICOLON28=null;
        Concept_BaseElement.type_return t = null;

        Concept_BaseElement.identifier_return id = null;

        ConceptParser.propertyBlock_return propertyBlock29 = null;


        ConceptASTNode SEMICOLON28_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_propertyBlock=new RewriteRuleSubtreeStream(adaptor,"rule propertyBlock");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Concept.g:182:2: (t= type id= identifier ( SEMICOLON | propertyBlock ) -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? ) )
            // Concept.g:182:4: t= type id= identifier ( SEMICOLON | propertyBlock )
            {
             setTypeReference(true); 
            pushFollow(FOLLOW_type_in_propertiesBodyDeclaration436);
            t=type();

            state._fsp--;

            stream_type.add(t.getTree());
             setTypeReference(false); 
            pushFollow(FOLLOW_identifier_in_propertiesBodyDeclaration442);
            id=identifier();

            state._fsp--;

            stream_identifier.add(id.getTree());
             pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); 
            // Concept.g:184:3: ( SEMICOLON | propertyBlock )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==SEMICOLON) ) {
                alt6=1;
            }
            else if ( (LA6_0==LBRACE) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // Concept.g:184:4: SEMICOLON
                    {
                    SEMICOLON28=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_propertiesBodyDeclaration451);  
                    stream_SEMICOLON.add(SEMICOLON28);


                    }
                    break;
                case 2 :
                    // Concept.g:184:16: propertyBlock
                    {
                    pushFollow(FOLLOW_propertyBlock_in_propertiesBodyDeclaration455);
                    propertyBlock29=propertyBlock();

                    state._fsp--;

                    stream_propertyBlock.add(propertyBlock29.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: propertyBlock, id, t
            // token labels: 
            // rule labels: id, retval, t
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"rule id",id!=null?id.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_t=new RewriteRuleSubtreeStream(adaptor,"rule t",t!=null?t.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 185:3: -> ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? )
            {
                // Concept.g:185:6: ^( PROPERTY_DECLARATION ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )? )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(PROPERTY_DECLARATION, "PROPERTY_DECLARATION"), root_1);

                // Concept.g:185:29: ^( TYPE[\"type\"] $t)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Concept.g:185:48: ^( NAME[\"name\"] $id)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Concept.g:186:4: ( ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock ) )?
                if ( stream_propertyBlock.hasNext() ) {
                    // Concept.g:186:4: ^( PROPERTY_BLOCK[\"prop block\"] propertyBlock )
                    {
                    ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                    root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(PROPERTY_BLOCK, "prop block"), root_2);

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

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyBlock"
    // Concept.g:189:1: propertyBlock : LBRACE ( propertyAttribute )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) ) ;
    public final ConceptParser.propertyBlock_return propertyBlock() throws RecognitionException {
        ConceptParser.propertyBlock_return retval = new ConceptParser.propertyBlock_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token LBRACE30=null;
        Token RBRACE32=null;
        ConceptParser.propertyAttribute_return propertyAttribute31 = null;


        ConceptASTNode LBRACE30_tree=null;
        ConceptASTNode RBRACE32_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_propertyAttribute=new RewriteRuleSubtreeStream(adaptor,"rule propertyAttribute");
        try {
            // Concept.g:190:2: ( LBRACE ( propertyAttribute )* RBRACE -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) ) )
            // Concept.g:190:5: LBRACE ( propertyAttribute )* RBRACE
            {
            LBRACE30=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_propertyBlock504);  
            stream_LBRACE.add(LBRACE30);

             pushScope(ATTRIBUTE_SCOPE); 
            // Concept.g:191:4: ( propertyAttribute )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=254 && LA7_0<=255)||(LA7_0>=258 && LA7_0<=259)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Concept.g:191:4: propertyAttribute
            	    {
            	    pushFollow(FOLLOW_propertyAttribute_in_propertyBlock511);
            	    propertyAttribute31=propertyAttribute();

            	    state._fsp--;

            	    stream_propertyAttribute.add(propertyAttribute31.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            RBRACE32=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_propertyBlock514);  
            stream_RBRACE.add(RBRACE32);

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

            root_0 = (ConceptASTNode)adaptor.nil();
            // 192:3: -> ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) )
            {
                // Concept.g:192:6: ^( BLOCK ^( STATEMENTS ( propertyAttribute )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Concept.g:192:14: ^( STATEMENTS ( propertyAttribute )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:192:27: ( propertyAttribute )*
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

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "propertyAttribute"
    // Concept.g:195:1: propertyAttribute : ( policyStatement | historyStatement | domainStatement | containedStatement );
    public final ConceptParser.propertyAttribute_return propertyAttribute() throws RecognitionException {
        ConceptParser.propertyAttribute_return retval = new ConceptParser.propertyAttribute_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        ConceptParser.policyStatement_return policyStatement33 = null;

        ConceptParser.historyStatement_return historyStatement34 = null;

        ConceptParser.domainStatement_return domainStatement35 = null;

        ConceptParser.containedStatement_return containedStatement36 = null;



        try {
            // Concept.g:196:2: ( policyStatement | historyStatement | domainStatement | containedStatement )
            int alt8=4;
            switch ( input.LA(1) ) {
            case 255:
                {
                alt8=1;
                }
                break;
            case 258:
                {
                alt8=2;
                }
                break;
            case 259:
                {
                alt8=3;
                }
                break;
            case 254:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // Concept.g:196:4: policyStatement
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_policyStatement_in_propertyAttribute543);
                    policyStatement33=policyStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, policyStatement33.getTree());

                    }
                    break;
                case 2 :
                    // Concept.g:197:4: historyStatement
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_historyStatement_in_propertyAttribute548);
                    historyStatement34=historyStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, historyStatement34.getTree());

                    }
                    break;
                case 3 :
                    // Concept.g:198:4: domainStatement
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_domainStatement_in_propertyAttribute553);
                    domainStatement35=domainStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, domainStatement35.getTree());

                    }
                    break;
                case 4 :
                    // Concept.g:199:4: containedStatement
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_containedStatement_in_propertyAttribute558);
                    containedStatement36=containedStatement();

                    state._fsp--;

                    adaptor.addChild(root_0, containedStatement36.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class containedStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "containedStatement"
    // Concept.g:202:1: containedStatement : 'contained' ASSIGN booleanLiteral SEMICOLON -> ^( CONTAINED_STATEMENT ^( LITERAL booleanLiteral ) ) ;
    public final ConceptParser.containedStatement_return containedStatement() throws RecognitionException {
        ConceptParser.containedStatement_return retval = new ConceptParser.containedStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal37=null;
        Token ASSIGN38=null;
        Token SEMICOLON40=null;
        Concept_BaseElement.booleanLiteral_return booleanLiteral39 = null;


        ConceptASTNode string_literal37_tree=null;
        ConceptASTNode ASSIGN38_tree=null;
        ConceptASTNode SEMICOLON40_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_254=new RewriteRuleTokenStream(adaptor,"token 254");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_booleanLiteral=new RewriteRuleSubtreeStream(adaptor,"rule booleanLiteral");
        try {
            // Concept.g:203:2: ( 'contained' ASSIGN booleanLiteral SEMICOLON -> ^( CONTAINED_STATEMENT ^( LITERAL booleanLiteral ) ) )
            // Concept.g:203:4: 'contained' ASSIGN booleanLiteral SEMICOLON
            {
            string_literal37=(Token)match(input,254,FOLLOW_254_in_containedStatement570);  
            stream_254.add(string_literal37);

            ASSIGN38=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_containedStatement572);  
            stream_ASSIGN.add(ASSIGN38);

            pushFollow(FOLLOW_booleanLiteral_in_containedStatement574);
            booleanLiteral39=booleanLiteral();

            state._fsp--;

            stream_booleanLiteral.add(booleanLiteral39.getTree());
            SEMICOLON40=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_containedStatement576);  
            stream_SEMICOLON.add(SEMICOLON40);



            // AST REWRITE
            // elements: booleanLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 204:3: -> ^( CONTAINED_STATEMENT ^( LITERAL booleanLiteral ) )
            {
                // Concept.g:204:6: ^( CONTAINED_STATEMENT ^( LITERAL booleanLiteral ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(CONTAINED_STATEMENT, "CONTAINED_STATEMENT"), root_1);

                // Concept.g:204:28: ^( LITERAL booleanLiteral )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "containedStatement"

    public static class policyStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "policyStatement"
    // Concept.g:207:1: policyStatement : 'policy' ASSIGN (p= 'CHANGES_ONLY' | p= 'ALL_VALUES' ) SEMICOLON -> ^( POLICY_STATEMENT ^( LITERAL $p) ) ;
    public final ConceptParser.policyStatement_return policyStatement() throws RecognitionException {
        ConceptParser.policyStatement_return retval = new ConceptParser.policyStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token p=null;
        Token string_literal41=null;
        Token ASSIGN42=null;
        Token SEMICOLON43=null;

        ConceptASTNode p_tree=null;
        ConceptASTNode string_literal41_tree=null;
        ConceptASTNode ASSIGN42_tree=null;
        ConceptASTNode SEMICOLON43_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_257=new RewriteRuleTokenStream(adaptor,"token 257");
        RewriteRuleTokenStream stream_256=new RewriteRuleTokenStream(adaptor,"token 256");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleTokenStream stream_255=new RewriteRuleTokenStream(adaptor,"token 255");

        try {
            // Concept.g:208:2: ( 'policy' ASSIGN (p= 'CHANGES_ONLY' | p= 'ALL_VALUES' ) SEMICOLON -> ^( POLICY_STATEMENT ^( LITERAL $p) ) )
            // Concept.g:208:4: 'policy' ASSIGN (p= 'CHANGES_ONLY' | p= 'ALL_VALUES' ) SEMICOLON
            {
            string_literal41=(Token)match(input,255,FOLLOW_255_in_policyStatement602);  
            stream_255.add(string_literal41);

            ASSIGN42=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_policyStatement604);  
            stream_ASSIGN.add(ASSIGN42);

            // Concept.g:208:20: (p= 'CHANGES_ONLY' | p= 'ALL_VALUES' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==256) ) {
                alt9=1;
            }
            else if ( (LA9_0==257) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // Concept.g:208:21: p= 'CHANGES_ONLY'
                    {
                    p=(Token)match(input,256,FOLLOW_256_in_policyStatement609);  
                    stream_256.add(p);


                    }
                    break;
                case 2 :
                    // Concept.g:208:40: p= 'ALL_VALUES'
                    {
                    p=(Token)match(input,257,FOLLOW_257_in_policyStatement615);  
                    stream_257.add(p);


                    }
                    break;

            }

            SEMICOLON43=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_policyStatement618);  
            stream_SEMICOLON.add(SEMICOLON43);



            // AST REWRITE
            // elements: p
            // token labels: p
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_p=new RewriteRuleTokenStream(adaptor,"token p",p);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 209:3: -> ^( POLICY_STATEMENT ^( LITERAL $p) )
            {
                // Concept.g:209:6: ^( POLICY_STATEMENT ^( LITERAL $p) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(POLICY_STATEMENT, "POLICY_STATEMENT"), root_1);

                // Concept.g:209:25: ^( LITERAL $p)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_p.nextNode());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "policyStatement"

    public static class historyStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "historyStatement"
    // Concept.g:212:1: historyStatement : 'history' ASSIGN integerLiteral SEMICOLON -> ^( HISTORY_STATEMENT ^( LITERAL integerLiteral ) ) ;
    public final ConceptParser.historyStatement_return historyStatement() throws RecognitionException {
        ConceptParser.historyStatement_return retval = new ConceptParser.historyStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal44=null;
        Token ASSIGN45=null;
        Token SEMICOLON47=null;
        Concept_BaseElement.integerLiteral_return integerLiteral46 = null;


        ConceptASTNode string_literal44_tree=null;
        ConceptASTNode ASSIGN45_tree=null;
        ConceptASTNode SEMICOLON47_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_258=new RewriteRuleTokenStream(adaptor,"token 258");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_integerLiteral=new RewriteRuleSubtreeStream(adaptor,"rule integerLiteral");
        try {
            // Concept.g:213:2: ( 'history' ASSIGN integerLiteral SEMICOLON -> ^( HISTORY_STATEMENT ^( LITERAL integerLiteral ) ) )
            // Concept.g:213:4: 'history' ASSIGN integerLiteral SEMICOLON
            {
            string_literal44=(Token)match(input,258,FOLLOW_258_in_historyStatement645);  
            stream_258.add(string_literal44);

            ASSIGN45=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_historyStatement647);  
            stream_ASSIGN.add(ASSIGN45);

            pushFollow(FOLLOW_integerLiteral_in_historyStatement649);
            integerLiteral46=integerLiteral();

            state._fsp--;

            stream_integerLiteral.add(integerLiteral46.getTree());
            SEMICOLON47=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_historyStatement651);  
            stream_SEMICOLON.add(SEMICOLON47);



            // AST REWRITE
            // elements: integerLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 214:3: -> ^( HISTORY_STATEMENT ^( LITERAL integerLiteral ) )
            {
                // Concept.g:214:6: ^( HISTORY_STATEMENT ^( LITERAL integerLiteral ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(HISTORY_STATEMENT, "HISTORY_STATEMENT"), root_1);

                // Concept.g:214:26: ^( LITERAL integerLiteral )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_integerLiteral.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "historyStatement"

    public static class domainStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainStatement"
    // Concept.g:217:1: domainStatement : 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) ) ;
    public final ConceptParser.domainStatement_return domainStatement() throws RecognitionException {
        ConceptParser.domainStatement_return retval = new ConceptParser.domainStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal48=null;
        Token ASSIGN49=null;
        Token char_literal50=null;
        Token SEMICOLON51=null;
        List list_names=null;
        RuleReturnScope names = null;
        ConceptASTNode string_literal48_tree=null;
        ConceptASTNode ASSIGN49_tree=null;
        ConceptASTNode char_literal50_tree=null;
        ConceptASTNode SEMICOLON51_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_232=new RewriteRuleTokenStream(adaptor,"token 232");
        RewriteRuleTokenStream stream_259=new RewriteRuleTokenStream(adaptor,"token 259");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Concept.g:218:2: ( 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) ) )
            // Concept.g:218:4: 'domain' ASSIGN names+= name[DOMAIN_REF] ( ',' names+= name[DOMAIN_REF] )* SEMICOLON
            {
            string_literal48=(Token)match(input,259,FOLLOW_259_in_domainStatement677);  
            stream_259.add(string_literal48);

            ASSIGN49=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_domainStatement679);  
            stream_ASSIGN.add(ASSIGN49);

            pushFollow(FOLLOW_name_in_domainStatement683);
            names=name(DOMAIN_REF);

            state._fsp--;

            stream_name.add(names.getTree());
            if (list_names==null) list_names=new ArrayList();
            list_names.add(names.getTree());

            // Concept.g:218:44: ( ',' names+= name[DOMAIN_REF] )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==232) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Concept.g:218:45: ',' names+= name[DOMAIN_REF]
            	    {
            	    char_literal50=(Token)match(input,232,FOLLOW_232_in_domainStatement687);  
            	    stream_232.add(char_literal50);

            	    pushFollow(FOLLOW_name_in_domainStatement691);
            	    names=name(DOMAIN_REF);

            	    state._fsp--;

            	    stream_name.add(names.getTree());
            	    if (list_names==null) list_names=new ArrayList();
            	    list_names.add(names.getTree());


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            SEMICOLON51=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_domainStatement696);  
            stream_SEMICOLON.add(SEMICOLON51);



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
            root_0 = (ConceptASTNode)adaptor.nil();
            // 219:3: -> ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) )
            {
                // Concept.g:219:6: ^( DOMAIN_STATEMENT ^( DOMAIN_NAMES ( $names)+ ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(DOMAIN_STATEMENT, "DOMAIN_STATEMENT"), root_1);

                // Concept.g:219:25: ^( DOMAIN_NAMES ( $names)+ )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(DOMAIN_NAMES, "DOMAIN_NAMES"), root_2);

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

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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

    public static class statemachinesNT_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statemachinesNT"
    // Concept.g:222:1: statemachinesNT : 'stateMachines' stateMachinesNTBlock -> ^( STATE_MACHINES_BLOCK[\"state machines\"] stateMachinesNTBlock ) ;
    public final ConceptParser.statemachinesNT_return statemachinesNT() throws RecognitionException {
        ConceptParser.statemachinesNT_return retval = new ConceptParser.statemachinesNT_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal52=null;
        ConceptParser.stateMachinesNTBlock_return stateMachinesNTBlock53 = null;


        ConceptASTNode string_literal52_tree=null;
        RewriteRuleTokenStream stream_260=new RewriteRuleTokenStream(adaptor,"token 260");
        RewriteRuleSubtreeStream stream_stateMachinesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule stateMachinesNTBlock");
        try {
            // Concept.g:223:2: ( 'stateMachines' stateMachinesNTBlock -> ^( STATE_MACHINES_BLOCK[\"state machines\"] stateMachinesNTBlock ) )
            // Concept.g:223:4: 'stateMachines' stateMachinesNTBlock
            {
            string_literal52=(Token)match(input,260,FOLLOW_260_in_statemachinesNT724);  
            stream_260.add(string_literal52);

            pushFollow(FOLLOW_stateMachinesNTBlock_in_statemachinesNT726);
            stateMachinesNTBlock53=stateMachinesNTBlock();

            state._fsp--;

            stream_stateMachinesNTBlock.add(stateMachinesNTBlock53.getTree());


            // AST REWRITE
            // elements: stateMachinesNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 224:2: -> ^( STATE_MACHINES_BLOCK[\"state machines\"] stateMachinesNTBlock )
            {
                // Concept.g:224:5: ^( STATE_MACHINES_BLOCK[\"state machines\"] stateMachinesNTBlock )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATE_MACHINES_BLOCK, "state machines"), root_1);

                adaptor.addChild(root_1, stream_stateMachinesNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "statemachinesNT"

    public static class stateMachinesNTBlock_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateMachinesNTBlock"
    // Concept.g:227:1: stateMachinesNTBlock : LBRACE ( stateMachineStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( stateMachineStatement )* ) ) ;
    public final ConceptParser.stateMachinesNTBlock_return stateMachinesNTBlock() throws RecognitionException {
        ConceptParser.stateMachinesNTBlock_return retval = new ConceptParser.stateMachinesNTBlock_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token LBRACE54=null;
        Token RBRACE56=null;
        ConceptParser.stateMachineStatement_return stateMachineStatement55 = null;


        ConceptASTNode LBRACE54_tree=null;
        ConceptASTNode RBRACE56_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_stateMachineStatement=new RewriteRuleSubtreeStream(adaptor,"rule stateMachineStatement");
        try {
            // Concept.g:228:2: ( LBRACE ( stateMachineStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( stateMachineStatement )* ) ) )
            // Concept.g:228:4: LBRACE ( stateMachineStatement )* RBRACE
            {
            LBRACE54=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_stateMachinesNTBlock749);  
            stream_LBRACE.add(LBRACE54);

             pushScope(ATTRIBUTE_SCOPE); 
            // Concept.g:229:4: ( stateMachineStatement )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==Identifier||LA11_0==NullLiteral||(LA11_0>=180 && LA11_0<=228)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Concept.g:229:4: stateMachineStatement
            	    {
            	    pushFollow(FOLLOW_stateMachineStatement_in_stateMachinesNTBlock756);
            	    stateMachineStatement55=stateMachineStatement();

            	    state._fsp--;

            	    stream_stateMachineStatement.add(stateMachineStatement55.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            RBRACE56=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_stateMachinesNTBlock759);  
            stream_RBRACE.add(RBRACE56);

             popScope(); 


            // AST REWRITE
            // elements: stateMachineStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 230:2: -> ^( BLOCK ^( STATEMENTS ( stateMachineStatement )* ) )
            {
                // Concept.g:230:5: ^( BLOCK ^( STATEMENTS ( stateMachineStatement )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Concept.g:230:13: ^( STATEMENTS ( stateMachineStatement )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:230:26: ( stateMachineStatement )*
                while ( stream_stateMachineStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_stateMachineStatement.nextTree());

                }
                stream_stateMachineStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "stateMachinesNTBlock"

    public static class stateMachineStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stateMachineStatement"
    // Concept.g:233:1: stateMachineStatement : name[DOMAIN_REF] SEMICOLON -> ^( STATE_MACHINE_STATEMENT ^( NAME name ) ) ;
    public final ConceptParser.stateMachineStatement_return stateMachineStatement() throws RecognitionException {
        ConceptParser.stateMachineStatement_return retval = new ConceptParser.stateMachineStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token SEMICOLON58=null;
        Concept_BaseElement.name_return name57 = null;


        ConceptASTNode SEMICOLON58_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Concept.g:234:2: ( name[DOMAIN_REF] SEMICOLON -> ^( STATE_MACHINE_STATEMENT ^( NAME name ) ) )
            // Concept.g:234:4: name[DOMAIN_REF] SEMICOLON
            {
            pushFollow(FOLLOW_name_in_stateMachineStatement788);
            name57=name(DOMAIN_REF);

            state._fsp--;

            stream_name.add(name57.getTree());
            SEMICOLON58=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_stateMachineStatement791);  
            stream_SEMICOLON.add(SEMICOLON58);



            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 235:3: -> ^( STATE_MACHINE_STATEMENT ^( NAME name ) )
            {
                // Concept.g:235:6: ^( STATE_MACHINE_STATEMENT ^( NAME name ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATE_MACHINE_STATEMENT, "STATE_MACHINE_STATEMENT"), root_1);

                // Concept.g:235:32: ^( NAME name )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "stateMachineStatement"

    public static class metadataNT_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "metadataNT"
    // Concept.g:238:1: metadataNT : 'metadata' metadataNTBlock -> ^( METADATA_BLOCK[\"metadata\"] metadataNTBlock ) ;
    public final ConceptParser.metadataNT_return metadataNT() throws RecognitionException {
        ConceptParser.metadataNT_return retval = new ConceptParser.metadataNT_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token string_literal59=null;
        ConceptParser.metadataNTBlock_return metadataNTBlock60 = null;


        ConceptASTNode string_literal59_tree=null;
        RewriteRuleTokenStream stream_261=new RewriteRuleTokenStream(adaptor,"token 261");
        RewriteRuleSubtreeStream stream_metadataNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule metadataNTBlock");
        try {
            // Concept.g:239:2: ( 'metadata' metadataNTBlock -> ^( METADATA_BLOCK[\"metadata\"] metadataNTBlock ) )
            // Concept.g:239:4: 'metadata' metadataNTBlock
            {
            string_literal59=(Token)match(input,261,FOLLOW_261_in_metadataNT817);  
            stream_261.add(string_literal59);

            pushFollow(FOLLOW_metadataNTBlock_in_metadataNT819);
            metadataNTBlock60=metadataNTBlock();

            state._fsp--;

            stream_metadataNTBlock.add(metadataNTBlock60.getTree());


            // AST REWRITE
            // elements: metadataNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 240:2: -> ^( METADATA_BLOCK[\"metadata\"] metadataNTBlock )
            {
                // Concept.g:240:5: ^( METADATA_BLOCK[\"metadata\"] metadataNTBlock )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(METADATA_BLOCK, "metadata"), root_1);

                adaptor.addChild(root_1, stream_metadataNTBlock.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "metadataNT"

    public static class metadataNTBlock_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "metadataNTBlock"
    // Concept.g:243:1: metadataNTBlock : LBRACE ( metadataStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( metadataStatement )* ) ) ;
    public final ConceptParser.metadataNTBlock_return metadataNTBlock() throws RecognitionException {
        ConceptParser.metadataNTBlock_return retval = new ConceptParser.metadataNTBlock_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token LBRACE61=null;
        Token RBRACE63=null;
        ConceptParser.metadataStatement_return metadataStatement62 = null;


        ConceptASTNode LBRACE61_tree=null;
        ConceptASTNode RBRACE63_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_metadataStatement=new RewriteRuleSubtreeStream(adaptor,"rule metadataStatement");
        try {
            // Concept.g:244:2: ( LBRACE ( metadataStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( metadataStatement )* ) ) )
            // Concept.g:244:4: LBRACE ( metadataStatement )* RBRACE
            {
            LBRACE61=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_metadataNTBlock841);  
            stream_LBRACE.add(LBRACE61);

             pushScope(ATTRIBUTE_SCOPE); 
            // Concept.g:245:4: ( metadataStatement )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==StringLiteral) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // Concept.g:245:4: metadataStatement
            	    {
            	    pushFollow(FOLLOW_metadataStatement_in_metadataNTBlock848);
            	    metadataStatement62=metadataStatement();

            	    state._fsp--;

            	    stream_metadataStatement.add(metadataStatement62.getTree());

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            RBRACE63=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_metadataNTBlock851);  
            stream_RBRACE.add(RBRACE63);

             popScope(); 


            // AST REWRITE
            // elements: metadataStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 246:2: -> ^( BLOCK ^( STATEMENTS ( metadataStatement )* ) )
            {
                // Concept.g:246:5: ^( BLOCK ^( STATEMENTS ( metadataStatement )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Concept.g:246:13: ^( STATEMENTS ( metadataStatement )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:246:26: ( metadataStatement )*
                while ( stream_metadataStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_metadataStatement.nextTree());

                }
                stream_metadataStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "metadataNTBlock"

    public static class metadataStatement_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "metadataStatement"
    // Concept.g:249:1: metadataStatement : ( metaDataGroup | metaDataProperty );
    public final ConceptParser.metadataStatement_return metadataStatement() throws RecognitionException {
        ConceptParser.metadataStatement_return retval = new ConceptParser.metadataStatement_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        ConceptParser.metaDataGroup_return metaDataGroup64 = null;

        ConceptParser.metaDataProperty_return metaDataProperty65 = null;



        try {
            // Concept.g:250:2: ( metaDataGroup | metaDataProperty )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==StringLiteral) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==LBRACE) ) {
                    alt13=1;
                }
                else if ( (LA13_1==ASSIGN) ) {
                    alt13=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // Concept.g:250:4: metaDataGroup
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_metaDataGroup_in_metadataStatement880);
                    metaDataGroup64=metaDataGroup();

                    state._fsp--;

                    adaptor.addChild(root_0, metaDataGroup64.getTree());

                    }
                    break;
                case 2 :
                    // Concept.g:250:20: metaDataProperty
                    {
                    root_0 = (ConceptASTNode)adaptor.nil();

                    pushFollow(FOLLOW_metaDataProperty_in_metadataStatement884);
                    metaDataProperty65=metaDataProperty();

                    state._fsp--;

                    adaptor.addChild(root_0, metaDataProperty65.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "metadataStatement"

    public static class metaDataGroup_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "metaDataGroup"
    // Concept.g:253:1: metaDataGroup : nm= StringLiteral LBRACE ( metadataStatement )* RBRACE -> ^( META_DATA_PROPERTY_GROUP ^( NAME $nm) ^( STATEMENTS ( metadataStatement )* ) ) ;
    public final ConceptParser.metaDataGroup_return metaDataGroup() throws RecognitionException {
        ConceptParser.metaDataGroup_return retval = new ConceptParser.metaDataGroup_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token nm=null;
        Token LBRACE66=null;
        Token RBRACE68=null;
        ConceptParser.metadataStatement_return metadataStatement67 = null;


        ConceptASTNode nm_tree=null;
        ConceptASTNode LBRACE66_tree=null;
        ConceptASTNode RBRACE68_tree=null;
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_metadataStatement=new RewriteRuleSubtreeStream(adaptor,"rule metadataStatement");
        try {
            // Concept.g:254:2: (nm= StringLiteral LBRACE ( metadataStatement )* RBRACE -> ^( META_DATA_PROPERTY_GROUP ^( NAME $nm) ^( STATEMENTS ( metadataStatement )* ) ) )
            // Concept.g:254:4: nm= StringLiteral LBRACE ( metadataStatement )* RBRACE
            {
            nm=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_metaDataGroup898);  
            stream_StringLiteral.add(nm);

            LBRACE66=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_metaDataGroup900);  
            stream_LBRACE.add(LBRACE66);

            // Concept.g:254:28: ( metadataStatement )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==StringLiteral) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // Concept.g:254:28: metadataStatement
            	    {
            	    pushFollow(FOLLOW_metadataStatement_in_metaDataGroup902);
            	    metadataStatement67=metadataStatement();

            	    state._fsp--;

            	    stream_metadataStatement.add(metadataStatement67.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            RBRACE68=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_metaDataGroup905);  
            stream_RBRACE.add(RBRACE68);



            // AST REWRITE
            // elements: metadataStatement, nm
            // token labels: nm
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_nm=new RewriteRuleTokenStream(adaptor,"token nm",nm);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 255:3: -> ^( META_DATA_PROPERTY_GROUP ^( NAME $nm) ^( STATEMENTS ( metadataStatement )* ) )
            {
                // Concept.g:255:6: ^( META_DATA_PROPERTY_GROUP ^( NAME $nm) ^( STATEMENTS ( metadataStatement )* ) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(META_DATA_PROPERTY_GROUP, "META_DATA_PROPERTY_GROUP"), root_1);

                // Concept.g:255:33: ^( NAME $nm)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_nm.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // Concept.g:255:45: ^( STATEMENTS ( metadataStatement )* )
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Concept.g:255:58: ( metadataStatement )*
                while ( stream_metadataStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_metadataStatement.nextTree());

                }
                stream_metadataStatement.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "metaDataGroup"

    public static class metaDataProperty_return extends ParserRuleReturnScope {
        ConceptASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "metaDataProperty"
    // Concept.g:258:1: metaDataProperty : nm= StringLiteral ASSIGN lit= StringLiteral SEMICOLON -> ^( META_DATA_PROPERTY ^( NAME $nm) ^( LITERAL $lit) ) ;
    public final ConceptParser.metaDataProperty_return metaDataProperty() throws RecognitionException {
        ConceptParser.metaDataProperty_return retval = new ConceptParser.metaDataProperty_return();
        retval.start = input.LT(1);

        ConceptASTNode root_0 = null;

        Token nm=null;
        Token lit=null;
        Token ASSIGN69=null;
        Token SEMICOLON70=null;

        ConceptASTNode nm_tree=null;
        ConceptASTNode lit_tree=null;
        ConceptASTNode ASSIGN69_tree=null;
        ConceptASTNode SEMICOLON70_tree=null;
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");

        try {
            // Concept.g:259:2: (nm= StringLiteral ASSIGN lit= StringLiteral SEMICOLON -> ^( META_DATA_PROPERTY ^( NAME $nm) ^( LITERAL $lit) ) )
            // Concept.g:259:4: nm= StringLiteral ASSIGN lit= StringLiteral SEMICOLON
            {
            nm=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_metaDataProperty941);  
            stream_StringLiteral.add(nm);

            ASSIGN69=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_metaDataProperty943);  
            stream_ASSIGN.add(ASSIGN69);

            lit=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_metaDataProperty947);  
            stream_StringLiteral.add(lit);

            SEMICOLON70=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_metaDataProperty949);  
            stream_SEMICOLON.add(SEMICOLON70);



            // AST REWRITE
            // elements: nm, lit
            // token labels: lit, nm
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleTokenStream stream_lit=new RewriteRuleTokenStream(adaptor,"token lit",lit);
            RewriteRuleTokenStream stream_nm=new RewriteRuleTokenStream(adaptor,"token nm",nm);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (ConceptASTNode)adaptor.nil();
            // 260:3: -> ^( META_DATA_PROPERTY ^( NAME $nm) ^( LITERAL $lit) )
            {
                // Concept.g:260:6: ^( META_DATA_PROPERTY ^( NAME $nm) ^( LITERAL $lit) )
                {
                ConceptASTNode root_1 = (ConceptASTNode)adaptor.nil();
                root_1 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(META_DATA_PROPERTY, "META_DATA_PROPERTY"), root_1);

                // Concept.g:260:27: ^( NAME $nm)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_nm.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // Concept.g:260:39: ^( LITERAL $lit)
                {
                ConceptASTNode root_2 = (ConceptASTNode)adaptor.nil();
                root_2 = (ConceptASTNode)adaptor.becomeRoot((ConceptASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_lit.nextNode());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (ConceptASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "metaDataProperty"

    // Delegated rules
    public Concept_BaseElement.block_return block() throws RecognitionException { return gBaseElement.block(); }
    public Concept_BaseElement.typeName_return typeName() throws RecognitionException { return gBaseElement.typeName(); }
    public Concept_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException { return gBaseElement.localInitializerArrayLiteral(); }
    public Concept_BaseElement.predicateStatement_return predicateStatement() throws RecognitionException { return gBaseElement.predicateStatement(); }
    public Concept_BaseElement.declareNTBlock_return declareNTBlock() throws RecognitionException { return gBaseElement.declareNTBlock(); }
    public Concept_BaseElement.type_return type() throws RecognitionException { return gBaseElement.type(); }
    public Concept_BaseElement.returnStatement_return returnStatement() throws RecognitionException { return gBaseElement.returnStatement(); }
    public Concept_BaseElement.expression_return expression() throws RecognitionException { return gBaseElement.expression(); }
    public Concept_BaseElement.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException { return gBaseElement.conditionalAndExpression(); }
    public Concept_BaseElement.primaryPrefix_return primaryPrefix() throws RecognitionException { return gBaseElement.primaryPrefix(); }
    public Concept_BaseElement.primaryExpression_return primaryExpression() throws RecognitionException { return gBaseElement.primaryExpression(); }
    public Concept_BaseElement.domainSpec_return domainSpec() throws RecognitionException { return gBaseElement.domainSpec(); }
    public Concept_BaseElement.booleanLiteral_return booleanLiteral() throws RecognitionException { return gBaseElement.booleanLiteral(); }
    public Concept_BaseElement.standaloneExpression_return standaloneExpression() throws RecognitionException { return gBaseElement.standaloneExpression(); }
    public Concept_BaseElement.expressionName_return expressionName() throws RecognitionException { return gBaseElement.expressionName(); }
    public Concept_BaseElement.thenStatements_return thenStatements() throws RecognitionException { return gBaseElement.thenStatements(); }
    public Concept_BaseElement.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException { return gBaseElement.comparisonNoLHS(); }
    public Concept_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException { return gBaseElement.typeAdditionalArrayDim(); }
    public Concept_BaseElement.rangeExpression_return rangeExpression() throws RecognitionException { return gBaseElement.rangeExpression(); }
    public Concept_BaseElement.ifRule_return ifRule() throws RecognitionException { return gBaseElement.ifRule(); }
    public Concept_BaseElement.thenStatement_return thenStatement() throws RecognitionException { return gBaseElement.thenStatement(); }
    public Concept_BaseElement.additiveExpression_return additiveExpression() throws RecognitionException { return gBaseElement.additiveExpression(); }
    public Concept_BaseElement.xsltLiteral_return xsltLiteral() throws RecognitionException { return gBaseElement.xsltLiteral(); }
    public Concept_BaseElement.actionContextStatement_return actionContextStatement() throws RecognitionException { return gBaseElement.actionContextStatement(); }
    public Concept_BaseElement.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException { return gBaseElement.localVariableDeclaration(); }
    public Concept_BaseElement.throwStatement_return throwStatement() throws RecognitionException { return gBaseElement.throwStatement(); }
    public Concept_BaseElement.assignmentOp_return assignmentOp() throws RecognitionException { return gBaseElement.assignmentOp(); }
    public Concept_BaseElement.statementExpressionList_return statementExpressionList() throws RecognitionException { return gBaseElement.statementExpressionList(); }
    public Concept_BaseElement.keywordIdentifier_return keywordIdentifier() throws RecognitionException { return gBaseElement.keywordIdentifier(); }
    public Concept_BaseElement.predicateStatements_return predicateStatements() throws RecognitionException { return gBaseElement.predicateStatements(); }
    public Concept_BaseElement.declarator_return declarator() throws RecognitionException { return gBaseElement.declarator(); }
    public Concept_BaseElement.thenNTBlock_return thenNTBlock() throws RecognitionException { return gBaseElement.thenNTBlock(); }
    public Concept_BaseElement.rangeStart_return rangeStart() throws RecognitionException { return gBaseElement.rangeStart(); }
    public Concept_BaseElement.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException { return gBaseElement.arrayAccessSuffix(); }
    public Concept_BaseElement.arrayLiteral_return arrayLiteral() throws RecognitionException { return gBaseElement.arrayLiteral(); }
    public Concept_BaseElement.forRule_return forRule() throws RecognitionException { return gBaseElement.forRule(); }
    public Concept_BaseElement.relationalExpression_return relationalExpression() throws RecognitionException { return gBaseElement.relationalExpression(); }
    public Concept_BaseElement.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gBaseElement.multiplicativeExpression(); }
    public Concept_BaseElement.primarySuffix_return primarySuffix(RulesASTNode type) throws RecognitionException { return gBaseElement.primarySuffix(type); }
    public Concept_BaseElement.integerLiteral_return integerLiteral() throws RecognitionException { return gBaseElement.integerLiteral(); }
    public Concept_BaseElement.variableDeclarator_return variableDeclarator(RulesASTNode type) throws RecognitionException { return gBaseElement.variableDeclarator(type); }
    public Concept_BaseElement.scopeNTBlock_return scopeNTBlock() throws RecognitionException { return gBaseElement.scopeNTBlock(); }
    public Concept_BaseElement.mappingBlock_return mappingBlock() throws RecognitionException { return gBaseElement.mappingBlock(); }
    public Concept_BaseElement.thenNT_return thenNT() throws RecognitionException { return gBaseElement.thenNT(); }
    public Concept_BaseElement.conditionBlock_return conditionBlock() throws RecognitionException { return gBaseElement.conditionBlock(); }
    public Concept_BaseElement.semiColon_return semiColon() throws RecognitionException { return gBaseElement.semiColon(); }
    public Concept_BaseElement.identifier_return identifier() throws RecognitionException { return gBaseElement.identifier(); }
    public Concept_BaseElement.listStatementExpression_return listStatementExpression() throws RecognitionException { return gBaseElement.listStatementExpression(); }
    public Concept_BaseElement.statementExpression_return statementExpression() throws RecognitionException { return gBaseElement.statementExpression(); }
    public Concept_BaseElement.whileRule_return whileRule() throws RecognitionException { return gBaseElement.whileRule(); }
    public Concept_BaseElement.declareNT_return declareNT() throws RecognitionException { return gBaseElement.declareNT(); }
    public Concept_BaseElement.unaryExpression_return unaryExpression() throws RecognitionException { return gBaseElement.unaryExpression(); }
    public Concept_BaseElement.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException { return gBaseElement.fieldAccessSuffix(); }
    public Concept_BaseElement.argumentList_return argumentList() throws RecognitionException { return gBaseElement.argumentList(); }
    public Concept_BaseElement.lineStatement_return lineStatement() throws RecognitionException { return gBaseElement.lineStatement(); }
    public Concept_BaseElement.name_return name(int nameType) throws RecognitionException { return gBaseElement.name(nameType); }
    public Concept_BaseElement.methodName_return methodName() throws RecognitionException { return gBaseElement.methodName(); }
    public Concept_BaseElement.predicate_return predicate() throws RecognitionException { return gBaseElement.predicate(); }
    public Concept_BaseElement.catchRule_return catchRule() throws RecognitionException { return gBaseElement.catchRule(); }
    public Concept_BaseElement.equalityExpression_return equalityExpression() throws RecognitionException { return gBaseElement.equalityExpression(); }
    public Concept_BaseElement.rangeEnd_return rangeEnd() throws RecognitionException { return gBaseElement.rangeEnd(); }
    public Concept_BaseElement.literal_return literal() throws RecognitionException { return gBaseElement.literal(); }
    public Concept_BaseElement.setMembershipExpression_return setMembershipExpression() throws RecognitionException { return gBaseElement.setMembershipExpression(); }
    public Concept_BaseElement.scopeNT_return scopeNT() throws RecognitionException { return gBaseElement.scopeNT(); }
    public Concept_BaseElement.continueStatement_return continueStatement() throws RecognitionException { return gBaseElement.continueStatement(); }
    public Concept_BaseElement.argumentsSuffix_return argumentsSuffix() throws RecognitionException { return gBaseElement.argumentsSuffix(); }
    public Concept_BaseElement.breakStatement_return breakStatement() throws RecognitionException { return gBaseElement.breakStatement(); }
    public Concept_BaseElement.finallyRule_return finallyRule() throws RecognitionException { return gBaseElement.finallyRule(); }
    public Concept_BaseElement.primitiveType_return primitiveType() throws RecognitionException { return gBaseElement.primitiveType(); }
    public Concept_BaseElement.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException { return gBaseElement.emptyBodyStatement(); }
    public Concept_BaseElement.blockStatement_return blockStatement() throws RecognitionException { return gBaseElement.blockStatement(); }
    public Concept_BaseElement.voidLiteral_return voidLiteral() throws RecognitionException { return gBaseElement.voidLiteral(); }
    public Concept_BaseElement.statement_return statement() throws RecognitionException { return gBaseElement.statement(); }
    public Concept_BaseElement.tryCatchFinally_return tryCatchFinally() throws RecognitionException { return gBaseElement.tryCatchFinally(); }
    public Concept_BaseElement.scopeDeclarator_return scopeDeclarator() throws RecognitionException { return gBaseElement.scopeDeclarator(); }
    public Concept_BaseElement.primitive_return primitive() throws RecognitionException { return gBaseElement.primitive(); }
    public Concept_BaseElement.nameOrPrimitiveType_return nameOrPrimitiveType() throws RecognitionException { return gBaseElement.nameOrPrimitiveType(); }
    public Concept_BaseElement.emptyStatement_return emptyStatement() throws RecognitionException { return gBaseElement.emptyStatement(); }
    public Concept_BaseElement.arrayAllocator_return arrayAllocator() throws RecognitionException { return gBaseElement.arrayAllocator(); }


 

    public static final BitSet FOLLOW_compilationUnit_in_startRule138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HeaderSection_in_compilationUnit153 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_conceptDefinition_in_compilationUnit158 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_compilationUnit162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_228_in_conceptDefinition173 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_conceptDefinition177 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_197_in_conceptDefinition181 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_conceptDefinition185 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_conceptDefinition190 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x2800000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_conceptNT_in_conceptDefinition192 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_conceptDefinition194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertiesNT_in_conceptNT245 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x2800000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_attributesNT_in_conceptNT250 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x2800000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_statemachinesNT_in_conceptNT255 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x2800000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_metadataNT_in_conceptNT260 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x2800000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_251_in_attributesNT273 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_attributesNTBlock_in_attributesNT275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_attributesNTBlock297 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_attributeStatement_in_attributesNTBlock306 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_attributesNTBlock309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_252_in_attributeStatement337 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeStatement339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_attributeStatement341 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeStatement343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_253_in_propertiesNT369 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_propertiesNTBlock_in_propertiesNT371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_propertiesNTBlock392 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFF0000000000002L,0x00F0001FFFFFFFFFL});
    public static final BitSet FOLLOW_propertiesBodyDeclaration_in_propertiesNTBlock401 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFF0000000000002L,0x00F0001FFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_propertiesNTBlock404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_propertiesBodyDeclaration436 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_identifier_in_propertiesBodyDeclaration442 = new BitSet(new long[]{0x0000000000000000L,0x0020080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_propertiesBodyDeclaration451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyBlock_in_propertiesBodyDeclaration455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_propertyBlock504 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0xC000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_propertyAttribute_in_propertyBlock511 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0xC000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_RBRACE_in_propertyBlock514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_policyStatement_in_propertyAttribute543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_historyStatement_in_propertyAttribute548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_domainStatement_in_propertyAttribute553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_containedStatement_in_propertyAttribute558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_254_in_containedStatement570 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_containedStatement572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_containedStatement574 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_containedStatement576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_255_in_policyStatement602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_policyStatement604 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_256_in_policyStatement609 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_257_in_policyStatement615 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_policyStatement618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_258_in_historyStatement645 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_historyStatement647 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_integerLiteral_in_historyStatement649 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_historyStatement651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_259_in_domainStatement677 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_domainStatement679 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_domainStatement683 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_232_in_domainStatement687 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_name_in_domainStatement691 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_domainStatement696 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_260_in_statemachinesNT724 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_stateMachinesNTBlock_in_statemachinesNT726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_stateMachinesNTBlock749 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_stateMachineStatement_in_stateMachinesNTBlock756 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFF0000000000002L,0x0000001FFFFFFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_stateMachinesNTBlock759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_stateMachineStatement788 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_stateMachineStatement791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_261_in_metadataNT817 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_metadataNTBlock_in_metadataNT819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_metadataNTBlock841 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_metadataStatement_in_metadataNTBlock848 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_RBRACE_in_metadataNTBlock851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_metaDataGroup_in_metadataStatement880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_metaDataProperty_in_metadataStatement884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_metaDataGroup898 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_metaDataGroup900 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_metadataStatement_in_metaDataGroup902 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_RBRACE_in_metaDataGroup905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_metaDataProperty941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_metaDataProperty943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_metaDataProperty947 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_metaDataProperty949 = new BitSet(new long[]{0x0000000000000002L});

}