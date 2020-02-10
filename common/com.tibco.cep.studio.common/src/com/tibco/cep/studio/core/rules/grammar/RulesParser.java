// $ANTLR 3.2 Sep 23, 2009 12:02:23 Rules.g 2015-06-26 10:31:22
 
package com.tibco.cep.studio.core.rules.grammar; 

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
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

import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class RulesParser extends BaseRulesParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_TEMPLATE", "RULE_TEMPLATE_DECL", "TEMPLATE_DECLARATOR", "BINDINGS_BLOCK", "BINDING_VIEW_STATEMENT", "BINDINGS_VIEWS_BLOCK", "BINDING_STATEMENT", "DOMAIN_MODEL", "DOMAIN_MODEL_STATEMENT", "ACTION_CONTEXT_BLOCK", "ACTION_CONTEXT_STATEMENT", "ACTION_TYPE", "QUALIFIER", "QUALIFIED_NAME", "SIMPLE_NAME", "LOCAL_VARIABLE_DECL", "DECLARATORS", "VARIABLE_DECLARATOR", "RULE", "RULE_FUNCTION", "RULE_DECL", "NAME", "RULE_BLOCK", "ATTRIBUTE_BLOCK", "STATEMENTS", "DECLARE_BLOCK", "RULE_FUNC_DECL", "SCOPE_BLOCK", "WHEN_BLOCK", "PREDICATES", "BLOCKS", "THEN_BLOCK", "BODY_BLOCK", "SCOPE_DECLARATOR", "TYPE", "DECLARATOR", "VIRTUAL", "PRIMITIVE_TYPE", "ANNOTATION", "ARGS", "METHOD_CALL", "PRIMARY_ASSIGNMENT_EXPRESSION", "SUFFIX", "EXPRESSION", "IF_RULE", "WHILE_RULE", "FOR_RULE", "ELSE_STATEMENT", "STATEMENT", "RETURN_STATEMENT", "FOR_LOOP", "RETURN_TYPE", "MAPPING_BLOCK", "PRIORITY_STATEMENT", "PRIORITY", "VALIDITY_STATEMENT", "VALIDITY", "RANK_STATEMENT", "REQUEUE_STATEMENT", "FORWARD_CHAIN_STATEMENT", "BACKWARD_CHAIN_STATEMENT", "LASTMOD_STATEMENT", "ALIAS_STATEMENT", "LITERAL", "PREDICATE_STATEMENT", "RANGE_EXPRESSION", "RANGE_START", "RANGE_END", "SET_MEMBERSHIP_EXPRESSION", "EXPRESSIONS", "BREAK_STATEMENT", "CONTINUE_STATEMENT", "THROW_STATEMENT", "TRY_RULE", "BLOCK", "CATCH_RULE", "IDENTIFIER", "FINALLY_RULE", "ARRAY_ACCESS_SUFFIX", "INITIALIZER", "LOCAL_INITIALIZER", "ARRAY_LITERAL", "ARRAY_ALLOCATOR", "TRY_STATEMET", "BODY", "CATCH_CLAUSE", "FINALLY_CLAUSE", "TRY_STATEMENT", "HEADER", "VOID_LITERAL", "MODIFIERS", "BINARY_RELATION_NODE", "UNARY_EXPRESSION_NODE", "LHS", "RHS", "OPERATOR", "PRIMARY_EXPRESSION", "PREFIX", "SUFFIXES", "ASSIGNMENT_SUFFIX", "SUFFIX_EXPRESSION", "MappingEnd", "Identifier", "SEMICOLON", "OR", "AND", "EQ", "NE", "INSTANCE_OF", "LT", "GT", "LE", "GE", "LBRACE", "RBRACE", "PLUS", "MINUS", "MULT", "DIVIDE", "MOD", "POUND", "DOT", "ANNOTATE", "FloatingPointLiteral", "StringLiteral", "NullLiteral", "DecimalLiteral", "HexLiteral", "TRUE", "FALSE", "WS", "INCR", "DECR", "ASSIGN", "PLUS_EQUAL", "MINUS_EQUAL", "MULT_EQUAL", "DIV_EQUAL", "MOD_EQUAL", "HEADER_START", "HeaderSection", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "CharacterLiteral", "UnicodeEscape", "OctalEscape", "Letter", "JavaIDDigit", "COMMENT", "LINE_COMMENT", "DISPLAY_BLOCK", "DISPLAY_PROPERTY", "'<#mapping>'", "'body'", "'validity'", "'lock'", "'exists'", "'and'", "'key'", "'alias'", "'rank'", "'virtual'", "'abstract'", "'byte'", "'case'", "'char'", "'class'", "'const'", "'default'", "'do'", "'extends'", "'final'", "'float'", "'goto'", "'implements'", "'import'", "'interface'", "'moveto'", "'native'", "'new'", "'package'", "'private'", "'protected'", "'public'", "'short'", "'static'", "'strictfp'", "'super'", "'switch'", "'synchronized'", "'this'", "'throws'", "'transient'", "'volatile'", "'create'", "'modify'", "'call'", "'views'", "'bindings'", "'display'", "'event'", "'concept'", "'\"\"xslt://'", "'\"'", "'void'", "','", "'return'", "'break'", "'continue'", "'throw'", "'if'", "'else'", "'while'", "'for'", "'try'", "'catch'", "'finally'", "'boolean'", "'int'", "'long'", "'double'", "'declare'", "'scope'", "'then'", "'rule'", "'ruletemplate'", "'rulefunction'", "'actionContext'", "'when'", "'attribute'", "'priority'", "'forwardChain'", "'backwardChain'", "'requeue'", "'$lastmod'", "'ACTION'", "'CONDITION'", "'QUERY'"
    };
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
    public Rules_BaseElement gBaseElement;
    // delegators


        public RulesParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RulesParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gBaseElement = new Rules_BaseElement(input, state, this);         
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
        gBaseElement.setTreeAdaptor(this.adaptor);
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return RulesParser.tokenNames; }
    public String getGrammarFileName() { return "Rules.g"; }


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
    	super.addProblemCollector(collector);
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
    		return ((RulesLexer)getTokenStream().getTokenSource()).getHeaderNode();
    	}


    public static class startRule_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "startRule"
    // Rules.g:140:1: startRule : compilationUnit ;
    public final RulesParser.startRule_return startRule() throws RecognitionException {
        RulesParser.startRule_return retval = new RulesParser.startRule_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.compilationUnit_return compilationUnit1 = null;



        try {
            // Rules.g:149:2: ( compilationUnit )
            // Rules.g:149:4: compilationUnit
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_compilationUnit_in_startRule409);
            compilationUnit1=compilationUnit();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, compilationUnit1.getTree());

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
    // $ANTLR end "startRule"

    public static class compilationUnit_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compilationUnit"
    // Rules.g:152:1: compilationUnit : ( HeaderSection )? ( ruleFunctionDeclaration ( mappingBlock )? -> ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleDeclaration ( mappingBlock )? -> ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleTemplateDeclaration ( mappingBlock )? -> ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) )* EOF ;
    public final RulesParser.compilationUnit_return compilationUnit() throws RecognitionException {
        RulesParser.compilationUnit_return retval = new RulesParser.compilationUnit_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token HeaderSection2=null;
        Token EOF9=null;
        RulesParser.ruleFunctionDeclaration_return ruleFunctionDeclaration3 = null;

        RulesParser.mappingBlock_return mappingBlock4 = null;

        RulesParser.ruleDeclaration_return ruleDeclaration5 = null;

        RulesParser.mappingBlock_return mappingBlock6 = null;

        RulesParser.ruleTemplateDeclaration_return ruleTemplateDeclaration7 = null;

        RulesParser.mappingBlock_return mappingBlock8 = null;


        RulesASTNode HeaderSection2_tree=null;
        RulesASTNode EOF9_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_HeaderSection=new RewriteRuleTokenStream(adaptor,"token HeaderSection");
        RewriteRuleSubtreeStream stream_ruleDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule ruleDeclaration");
        RewriteRuleSubtreeStream stream_ruleTemplateDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule ruleTemplateDeclaration");
        RewriteRuleSubtreeStream stream_ruleFunctionDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule ruleFunctionDeclaration");
        RewriteRuleSubtreeStream stream_mappingBlock=new RewriteRuleSubtreeStream(adaptor,"rule mappingBlock");
        try {
            // Rules.g:153:2: ( ( HeaderSection )? ( ruleFunctionDeclaration ( mappingBlock )? -> ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleDeclaration ( mappingBlock )? -> ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleTemplateDeclaration ( mappingBlock )? -> ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) )* EOF )
            // Rules.g:154:3: ( HeaderSection )? ( ruleFunctionDeclaration ( mappingBlock )? -> ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleDeclaration ( mappingBlock )? -> ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleTemplateDeclaration ( mappingBlock )? -> ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) )* EOF
            {
            // Rules.g:154:3: ( HeaderSection )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==HeaderSection) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // Rules.g:154:3: HeaderSection
                    {
                    HeaderSection2=(Token)match(input,HeaderSection,FOLLOW_HeaderSection_in_compilationUnit424); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_HeaderSection.add(HeaderSection2);


                    }
                    break;

            }

            // Rules.g:155:3: ( ruleFunctionDeclaration ( mappingBlock )? -> ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleDeclaration ( mappingBlock )? -> ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) | ruleTemplateDeclaration ( mappingBlock )? -> ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? ) )*
            loop5:
            do {
                int alt5=4;
                switch ( input.LA(1) ) {
                case Identifier:
                case NullLiteral:
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
                case 215:
                case 228:
                case 229:
                case 230:
                case 231:
                case 237:
                    {
                    alt5=1;
                    }
                    break;
                case 235:
                    {
                    alt5=2;
                    }
                    break;
                case 236:
                    {
                    alt5=3;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // Rules.g:155:4: ruleFunctionDeclaration ( mappingBlock )?
            	    {
            	    pushFollow(FOLLOW_ruleFunctionDeclaration_in_compilationUnit430);
            	    ruleFunctionDeclaration3=ruleFunctionDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_ruleFunctionDeclaration.add(ruleFunctionDeclaration3.getTree());
            	    // Rules.g:155:28: ( mappingBlock )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( (LA2_0==163) ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // Rules.g:155:28: mappingBlock
            	            {
            	            pushFollow(FOLLOW_mappingBlock_in_compilationUnit432);
            	            mappingBlock4=mappingBlock();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_mappingBlock.add(mappingBlock4.getTree());

            	            }
            	            break;

            	    }



            	    // AST REWRITE
            	    // elements: mappingBlock, ruleFunctionDeclaration
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (RulesASTNode)adaptor.nil();
            	    // 156:4: -> ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	    {
            	        // Rules.g:156:7: ^( RULE_FUNCTION[\"rule function\"] ruleFunctionDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	        {
            	        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
            	        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE_FUNCTION, "rule function"), root_1);

            	        adaptor.addChild(root_1, stream_ruleFunctionDeclaration.nextTree());
            	        // Rules.g:157:5: ( ^( MAPPING_BLOCK mappingBlock ) )?
            	        if ( stream_mappingBlock.hasNext() ) {
            	            // Rules.g:157:5: ^( MAPPING_BLOCK mappingBlock )
            	            {
            	            RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
            	            root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(MAPPING_BLOCK, "MAPPING_BLOCK"), root_2);

            	            adaptor.addChild(root_2, stream_mappingBlock.nextTree());

            	            adaptor.addChild(root_1, root_2);
            	            }

            	        }
            	        stream_mappingBlock.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 2 :
            	    // Rules.g:158:5: ruleDeclaration ( mappingBlock )?
            	    {
            	    pushFollow(FOLLOW_ruleDeclaration_in_compilationUnit463);
            	    ruleDeclaration5=ruleDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_ruleDeclaration.add(ruleDeclaration5.getTree());
            	    // Rules.g:158:21: ( mappingBlock )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0==163) ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // Rules.g:158:21: mappingBlock
            	            {
            	            pushFollow(FOLLOW_mappingBlock_in_compilationUnit465);
            	            mappingBlock6=mappingBlock();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_mappingBlock.add(mappingBlock6.getTree());

            	            }
            	            break;

            	    }



            	    // AST REWRITE
            	    // elements: mappingBlock, ruleDeclaration
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (RulesASTNode)adaptor.nil();
            	    // 159:4: -> ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	    {
            	        // Rules.g:159:7: ^( RULE[\"rule\"] ruleDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	        {
            	        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
            	        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE, "rule"), root_1);

            	        adaptor.addChild(root_1, stream_ruleDeclaration.nextTree());
            	        // Rules.g:160:5: ( ^( MAPPING_BLOCK mappingBlock ) )?
            	        if ( stream_mappingBlock.hasNext() ) {
            	            // Rules.g:160:5: ^( MAPPING_BLOCK mappingBlock )
            	            {
            	            RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
            	            root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(MAPPING_BLOCK, "MAPPING_BLOCK"), root_2);

            	            adaptor.addChild(root_2, stream_mappingBlock.nextTree());

            	            adaptor.addChild(root_1, root_2);
            	            }

            	        }
            	        stream_mappingBlock.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 3 :
            	    // Rules.g:161:5: ruleTemplateDeclaration ( mappingBlock )?
            	    {
            	    pushFollow(FOLLOW_ruleTemplateDeclaration_in_compilationUnit496);
            	    ruleTemplateDeclaration7=ruleTemplateDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_ruleTemplateDeclaration.add(ruleTemplateDeclaration7.getTree());
            	    // Rules.g:161:29: ( mappingBlock )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==163) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // Rules.g:161:29: mappingBlock
            	            {
            	            pushFollow(FOLLOW_mappingBlock_in_compilationUnit498);
            	            mappingBlock8=mappingBlock();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_mappingBlock.add(mappingBlock8.getTree());

            	            }
            	            break;

            	    }



            	    // AST REWRITE
            	    // elements: mappingBlock, ruleTemplateDeclaration
            	    // token labels: 
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (RulesASTNode)adaptor.nil();
            	    // 162:4: -> ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	    {
            	        // Rules.g:162:7: ^( RULE_TEMPLATE[\"rule_template\"] ruleTemplateDeclaration ( ^( MAPPING_BLOCK mappingBlock ) )? )
            	        {
            	        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
            	        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE_TEMPLATE, "rule_template"), root_1);

            	        adaptor.addChild(root_1, stream_ruleTemplateDeclaration.nextTree());
            	        // Rules.g:163:5: ( ^( MAPPING_BLOCK mappingBlock ) )?
            	        if ( stream_mappingBlock.hasNext() ) {
            	            // Rules.g:163:5: ^( MAPPING_BLOCK mappingBlock )
            	            {
            	            RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
            	            root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(MAPPING_BLOCK, "MAPPING_BLOCK"), root_2);

            	            adaptor.addChild(root_2, stream_mappingBlock.nextTree());

            	            adaptor.addChild(root_1, root_2);
            	            }

            	        }
            	        stream_mappingBlock.reset();

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            EOF9=(Token)match(input,EOF,FOLLOW_EOF_in_compilationUnit527); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF9);


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
    // $ANTLR end "compilationUnit"

    public static class mappingBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mappingBlock"
    // Rules.g:166:1: mappingBlock : '<#mapping>' (~ MappingEnd )* MappingEnd ;
    public final RulesParser.mappingBlock_return mappingBlock() throws RecognitionException {
        RulesParser.mappingBlock_return retval = new RulesParser.mappingBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal10=null;
        Token set11=null;
        Token MappingEnd12=null;

        RulesASTNode string_literal10_tree=null;
        RulesASTNode set11_tree=null;
        RulesASTNode MappingEnd12_tree=null;

        try {
            // Rules.g:167:2: ( '<#mapping>' (~ MappingEnd )* MappingEnd )
            // Rules.g:167:4: '<#mapping>' (~ MappingEnd )* MappingEnd
            {
            root_0 = (RulesASTNode)adaptor.nil();

            string_literal10=(Token)match(input,163,FOLLOW_163_in_mappingBlock538); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal10_tree = (RulesASTNode)adaptor.create(string_literal10);
            adaptor.addChild(root_0, string_literal10_tree);
            }
            // Rules.g:167:17: (~ MappingEnd )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>=RULE_TEMPLATE && LA6_0<=SUFFIX_EXPRESSION)||(LA6_0>=Identifier && LA6_0<=248)) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Rules.g:167:18: ~ MappingEnd
            	    {
            	    set11=(Token)input.LT(1);
            	    if ( (input.LA(1)>=RULE_TEMPLATE && input.LA(1)<=SUFFIX_EXPRESSION)||(input.LA(1)>=Identifier && input.LA(1)<=248) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set11));
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
            	    break loop6;
                }
            } while (true);

            MappingEnd12=(Token)match(input,MappingEnd,FOLLOW_MappingEnd_in_mappingBlock546); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MappingEnd12_tree = (RulesASTNode)adaptor.create(MappingEnd12);
            adaptor.addChild(root_0, MappingEnd12_tree);
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
    // $ANTLR end "mappingBlock"

    public static class conditionBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionBlock"
    // Rules.g:170:1: conditionBlock : ( predicateStatement )* EOF ;
    public final RulesParser.conditionBlock_return conditionBlock() throws RecognitionException {
        RulesParser.conditionBlock_return retval = new RulesParser.conditionBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EOF14=null;
        Rules_BaseElement.predicateStatement_return predicateStatement13 = null;


        RulesASTNode EOF14_tree=null;

        try {
            // Rules.g:171:2: ( ( predicateStatement )* EOF )
            // Rules.g:171:4: ( predicateStatement )* EOF
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:171:4: ( predicateStatement )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>=Identifier && LA7_0<=SEMICOLON)||(LA7_0>=EQ && LA7_0<=LBRACE)||(LA7_0>=PLUS && LA7_0<=MINUS)||LA7_0==POUND||(LA7_0>=FloatingPointLiteral && LA7_0<=FALSE)||LA7_0==LPAREN||LA7_0==LBRACK||(LA7_0>=164 && LA7_0<=212)||(LA7_0>=228 && LA7_0<=231)) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Rules.g:171:4: predicateStatement
            	    {
            	    pushFollow(FOLLOW_predicateStatement_in_conditionBlock558);
            	    predicateStatement13=predicateStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, predicateStatement13.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            EOF14=(Token)match(input,EOF,FOLLOW_EOF_in_conditionBlock561); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF14_tree = (RulesASTNode)adaptor.create(EOF14);
            adaptor.addChild(root_0, EOF14_tree);
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
    // $ANTLR end "conditionBlock"

    public static class actionBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionBlock"
    // Rules.g:174:1: actionBlock : thenStatements EOF ;
    public final RulesParser.actionBlock_return actionBlock() throws RecognitionException {
        RulesParser.actionBlock_return retval = new RulesParser.actionBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EOF16=null;
        RulesParser.thenStatements_return thenStatements15 = null;


        RulesASTNode EOF16_tree=null;

        try {
            // Rules.g:175:2: ( thenStatements EOF )
            // Rules.g:175:4: thenStatements EOF
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_thenStatements_in_actionBlock573);
            thenStatements15=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatements15.getTree());
            EOF16=(Token)match(input,EOF,FOLLOW_EOF_in_actionBlock575); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF16_tree = (RulesASTNode)adaptor.create(EOF16);
            adaptor.addChild(root_0, EOF16_tree);
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
    // $ANTLR end "actionBlock"

    public static class standaloneExpression_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "standaloneExpression"
    // Rules.g:178:1: standaloneExpression : predicate EOF ;
    public final RulesParser.standaloneExpression_return standaloneExpression() throws RecognitionException {
        RulesParser.standaloneExpression_return retval = new RulesParser.standaloneExpression_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EOF18=null;
        Rules_BaseElement.predicate_return predicate17 = null;


        RulesASTNode EOF18_tree=null;

        try {
            // Rules.g:179:2: ( predicate EOF )
            // Rules.g:179:4: predicate EOF
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_predicate_in_standaloneExpression587);
            predicate17=predicate();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, predicate17.getTree());
            EOF18=(Token)match(input,EOF,FOLLOW_EOF_in_standaloneExpression589); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF18_tree = (RulesASTNode)adaptor.create(EOF18);
            adaptor.addChild(root_0, EOF18_tree);
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
    // $ANTLR end "standaloneExpression"

    public static class standaloneThenStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "standaloneThenStatement"
    // Rules.g:182:1: standaloneThenStatement : thenStatement EOF ;
    public final RulesParser.standaloneThenStatement_return standaloneThenStatement() throws RecognitionException {
        RulesParser.standaloneThenStatement_return retval = new RulesParser.standaloneThenStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EOF20=null;
        RulesParser.thenStatement_return thenStatement19 = null;


        RulesASTNode EOF20_tree=null;

        try {
            // Rules.g:183:2: ( thenStatement EOF )
            // Rules.g:183:4: thenStatement EOF
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_thenStatement_in_standaloneThenStatement602);
            thenStatement19=thenStatement();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement19.getTree());
            EOF20=(Token)match(input,EOF,FOLLOW_EOF_in_standaloneThenStatement604); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF20_tree = (RulesASTNode)adaptor.create(EOF20);
            adaptor.addChild(root_0, EOF20_tree);
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
    // $ANTLR end "standaloneThenStatement"

    public static class ruleFunctionBody_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleFunctionBody"
    // Rules.g:186:1: ruleFunctionBody : thenStatements EOF ;
    public final RulesParser.ruleFunctionBody_return ruleFunctionBody() throws RecognitionException {
        RulesParser.ruleFunctionBody_return retval = new RulesParser.ruleFunctionBody_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token EOF22=null;
        RulesParser.thenStatements_return thenStatements21 = null;


        RulesASTNode EOF22_tree=null;

        try {
            // Rules.g:187:2: ( thenStatements EOF )
            // Rules.g:187:4: thenStatements EOF
            {
            root_0 = (RulesASTNode)adaptor.nil();

            pushFollow(FOLLOW_thenStatements_in_ruleFunctionBody616);
            thenStatements21=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatements21.getTree());
            EOF22=(Token)match(input,EOF,FOLLOW_EOF_in_ruleFunctionBody618); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF22_tree = (RulesASTNode)adaptor.create(EOF22);
            adaptor.addChild(root_0, EOF22_tree);
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
    // $ANTLR end "ruleFunctionBody"

    public static class identifier_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "identifier"
    // Rules.g:190:1: identifier : ( Identifier | keywordIdentifier ) -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? ) ;
    public final RulesParser.identifier_return identifier() throws RecognitionException {
        RulesParser.identifier_return retval = new RulesParser.identifier_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token Identifier23=null;
        RulesParser.keywordIdentifier_return keywordIdentifier24 = null;


        RulesASTNode Identifier23_tree=null;
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_keywordIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule keywordIdentifier");
        try {
            // Rules.g:191:2: ( ( Identifier | keywordIdentifier ) -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? ) )
            // Rules.g:191:4: ( Identifier | keywordIdentifier )
            {
            // Rules.g:191:4: ( Identifier | keywordIdentifier )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==Identifier) ) {
                alt8=1;
            }
            else if ( (LA8_0==NullLiteral||(LA8_0>=164 && LA8_0<=212)) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // Rules.g:191:5: Identifier
                    {
                    Identifier23=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifier631); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier23);


                    }
                    break;
                case 2 :
                    // Rules.g:191:18: keywordIdentifier
                    {
                    pushFollow(FOLLOW_keywordIdentifier_in_identifier635);
                    keywordIdentifier24=keywordIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_keywordIdentifier.add(keywordIdentifier24.getTree());

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 191:37: -> ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? )
            {
                // Rules.g:191:40: ^( SIMPLE_NAME ( Identifier )? ( keywordIdentifier )? )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SIMPLE_NAME, "SIMPLE_NAME"), root_1);

                // Rules.g:191:54: ( Identifier )?
                if ( stream_Identifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_Identifier.nextNode());

                }
                stream_Identifier.reset();
                // Rules.g:191:66: ( keywordIdentifier )?
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
    // $ANTLR end "identifier"

    public static class keywordIdentifier_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keywordIdentifier"
    // Rules.g:194:1: keywordIdentifier : ( 'body' | 'validity' | 'null' | 'lock' | 'exists' | 'and' | 'key' | 'alias' | 'rank' | 'virtual' | 'abstract' | 'byte' | 'case' | 'char' | 'class' | 'const' | 'default' | 'do' | 'extends' | 'final' | 'float' | 'goto' | 'implements' | 'import' | 'interface' | 'moveto' | 'native' | 'new' | 'package' | 'private' | 'protected' | 'public' | 'short' | 'static' | 'strictfp' | 'super' | 'switch' | 'synchronized' | 'this' | 'throws' | 'transient' | 'volatile' | 'create' | 'modify' | 'call' | 'views' | 'bindings' | 'display' | 'event' | 'concept' );
    public final RulesParser.keywordIdentifier_return keywordIdentifier() throws RecognitionException {
        RulesParser.keywordIdentifier_return retval = new RulesParser.keywordIdentifier_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set25=null;

        RulesASTNode set25_tree=null;

        try {
            // Rules.g:195:2: ( 'body' | 'validity' | 'null' | 'lock' | 'exists' | 'and' | 'key' | 'alias' | 'rank' | 'virtual' | 'abstract' | 'byte' | 'case' | 'char' | 'class' | 'const' | 'default' | 'do' | 'extends' | 'final' | 'float' | 'goto' | 'implements' | 'import' | 'interface' | 'moveto' | 'native' | 'new' | 'package' | 'private' | 'protected' | 'public' | 'short' | 'static' | 'strictfp' | 'super' | 'switch' | 'synchronized' | 'this' | 'throws' | 'transient' | 'volatile' | 'create' | 'modify' | 'call' | 'views' | 'bindings' | 'display' | 'event' | 'concept' )
            // Rules.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set25=(Token)input.LT(1);
            if ( input.LA(1)==NullLiteral||(input.LA(1)>=164 && input.LA(1)<=212) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set25));
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
    // $ANTLR end "keywordIdentifier"

    public static class xsltLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "xsltLiteral"
    // Rules.g:203:1: xsltLiteral : '\"\"xslt://' '\"' '\"' ;
    public final RulesParser.xsltLiteral_return xsltLiteral() throws RecognitionException {
        RulesParser.xsltLiteral_return retval = new RulesParser.xsltLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal26=null;
        Token char_literal27=null;
        Token char_literal28=null;

        RulesASTNode string_literal26_tree=null;
        RulesASTNode char_literal27_tree=null;
        RulesASTNode char_literal28_tree=null;

        try {
            // Rules.g:204:2: ( '\"\"xslt://' '\"' '\"' )
            // Rules.g:204:4: '\"\"xslt://' '\"' '\"'
            {
            root_0 = (RulesASTNode)adaptor.nil();

            string_literal26=(Token)match(input,213,FOLLOW_213_in_xsltLiteral877); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal26_tree = (RulesASTNode)adaptor.create(string_literal26);
            adaptor.addChild(root_0, string_literal26_tree);
            }
            char_literal27=(Token)match(input,214,FOLLOW_214_in_xsltLiteral879); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal27_tree = (RulesASTNode)adaptor.create(char_literal27);
            adaptor.addChild(root_0, char_literal27_tree);
            }
            char_literal28=(Token)match(input,214,FOLLOW_214_in_xsltLiteral881); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal28_tree = (RulesASTNode)adaptor.create(char_literal28);
            adaptor.addChild(root_0, char_literal28_tree);
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
    // $ANTLR end "xsltLiteral"

    public static class ruleDeclaration_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleDeclaration"
    // Rules.g:207:1: ruleDeclaration : 'rule' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleNT RBRACE -> ^( RULE_DECL[\"rule\"] ^( NAME name ) ^( BLOCKS ruleNT ) ) ;
    public final RulesParser.ruleDeclaration_return ruleDeclaration() throws RecognitionException {
        RulesParser.ruleDeclaration_return retval = new RulesParser.ruleDeclaration_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal29=null;
        Token LBRACE31=null;
        Token RBRACE33=null;
        Rules_BaseElement.name_return name30 = null;

        RulesParser.ruleNT_return ruleNT32 = null;


        RulesASTNode string_literal29_tree=null;
        RulesASTNode LBRACE31_tree=null;
        RulesASTNode RBRACE33_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_235=new RewriteRuleTokenStream(adaptor,"token 235");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_ruleNT=new RewriteRuleSubtreeStream(adaptor,"rule ruleNT");
        try {
            // Rules.g:208:2: ( 'rule' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleNT RBRACE -> ^( RULE_DECL[\"rule\"] ^( NAME name ) ^( BLOCKS ruleNT ) ) )
            // Rules.g:208:4: 'rule' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleNT RBRACE
            {
            string_literal29=(Token)match(input,235,FOLLOW_235_in_ruleDeclaration893); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_235.add(string_literal29);

            pushFollow(FOLLOW_name_in_ruleDeclaration895);
            name30=name(BaseRulesParser.TYPE_RULE_DEF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name30.getTree());
            LBRACE31=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleDeclaration898); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE31);

            pushFollow(FOLLOW_ruleNT_in_ruleDeclaration900);
            ruleNT32=ruleNT();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ruleNT.add(ruleNT32.getTree());
            RBRACE33=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleDeclaration902); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE33);

            if ( state.backtracking==0 ) {
               setRuleElementType(ELEMENT_TYPES.RULE); 
            }


            // AST REWRITE
            // elements: ruleNT, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 210:2: -> ^( RULE_DECL[\"rule\"] ^( NAME name ) ^( BLOCKS ruleNT ) )
            {
                // Rules.g:210:5: ^( RULE_DECL[\"rule\"] ^( NAME name ) ^( BLOCKS ruleNT ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE_DECL, "rule"), root_1);

                // Rules.g:210:25: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:210:38: ^( BLOCKS ruleNT )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCKS, "BLOCKS"), root_2);

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
    // $ANTLR end "ruleDeclaration"

    public static class ruleTemplateDeclaration_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleTemplateDeclaration"
    // Rules.g:213:1: ruleTemplateDeclaration : 'ruletemplate' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruletemplateNT RBRACE -> ^( RULE_TEMPLATE_DECL[\"ruleTemplate\"] ^( NAME name ) ^( BLOCKS ruletemplateNT ) ) ;
    public final RulesParser.ruleTemplateDeclaration_return ruleTemplateDeclaration() throws RecognitionException {
        RulesParser.ruleTemplateDeclaration_return retval = new RulesParser.ruleTemplateDeclaration_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal34=null;
        Token LBRACE36=null;
        Token RBRACE38=null;
        Rules_BaseElement.name_return name35 = null;

        RulesParser.ruletemplateNT_return ruletemplateNT37 = null;


        RulesASTNode string_literal34_tree=null;
        RulesASTNode LBRACE36_tree=null;
        RulesASTNode RBRACE38_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_236=new RewriteRuleTokenStream(adaptor,"token 236");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_ruletemplateNT=new RewriteRuleSubtreeStream(adaptor,"rule ruletemplateNT");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Rules.g:214:2: ( 'ruletemplate' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruletemplateNT RBRACE -> ^( RULE_TEMPLATE_DECL[\"ruleTemplate\"] ^( NAME name ) ^( BLOCKS ruletemplateNT ) ) )
            // Rules.g:214:4: 'ruletemplate' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruletemplateNT RBRACE
            {
            string_literal34=(Token)match(input,236,FOLLOW_236_in_ruleTemplateDeclaration936); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_236.add(string_literal34);

            pushFollow(FOLLOW_name_in_ruleTemplateDeclaration938);
            name35=name(BaseRulesParser.TYPE_RULE_DEF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name35.getTree());
            LBRACE36=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleTemplateDeclaration941); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE36);

            pushFollow(FOLLOW_ruletemplateNT_in_ruleTemplateDeclaration943);
            ruletemplateNT37=ruletemplateNT();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ruletemplateNT.add(ruletemplateNT37.getTree());
            RBRACE38=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleTemplateDeclaration945); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE38);

            if ( state.backtracking==0 ) {
               setRuleElementType(ELEMENT_TYPES.RULE_TEMPLATE); 
            }


            // AST REWRITE
            // elements: ruletemplateNT, name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 216:2: -> ^( RULE_TEMPLATE_DECL[\"ruleTemplate\"] ^( NAME name ) ^( BLOCKS ruletemplateNT ) )
            {
                // Rules.g:216:5: ^( RULE_TEMPLATE_DECL[\"ruleTemplate\"] ^( NAME name ) ^( BLOCKS ruletemplateNT ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE_TEMPLATE_DECL, "ruleTemplate"), root_1);

                // Rules.g:216:42: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:216:55: ^( BLOCKS ruletemplateNT )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCKS, "BLOCKS"), root_2);

                adaptor.addChild(root_2, stream_ruletemplateNT.nextTree());

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
    // $ANTLR end "ruleTemplateDeclaration"

    public static class ruleFunctionDeclaration_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleFunctionDeclaration"
    // Rules.g:219:1: ruleFunctionDeclaration : ( 'virtual' )? ( returnType )? 'rulefunction' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleFunctionNT RBRACE -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ^( MODIFIERS ( ^( VIRTUAL 'virtual' ) )? ) ^( NAME name ) ^( RETURN_TYPE ( returnType )? ) ^( BLOCKS ruleFunctionNT ) ) ;
    public final RulesParser.ruleFunctionDeclaration_return ruleFunctionDeclaration() throws RecognitionException {
        RulesParser.ruleFunctionDeclaration_return retval = new RulesParser.ruleFunctionDeclaration_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal39=null;
        Token string_literal41=null;
        Token LBRACE43=null;
        Token RBRACE45=null;
        RulesParser.returnType_return returnType40 = null;

        Rules_BaseElement.name_return name42 = null;

        RulesParser.ruleFunctionNT_return ruleFunctionNT44 = null;


        RulesASTNode string_literal39_tree=null;
        RulesASTNode string_literal41_tree=null;
        RulesASTNode LBRACE43_tree=null;
        RulesASTNode RBRACE45_tree=null;
        RewriteRuleTokenStream stream_172=new RewriteRuleTokenStream(adaptor,"token 172");
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleTokenStream stream_237=new RewriteRuleTokenStream(adaptor,"token 237");
        RewriteRuleSubtreeStream stream_ruleFunctionNT=new RewriteRuleSubtreeStream(adaptor,"rule ruleFunctionNT");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_returnType=new RewriteRuleSubtreeStream(adaptor,"rule returnType");
        try {
            // Rules.g:220:2: ( ( 'virtual' )? ( returnType )? 'rulefunction' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleFunctionNT RBRACE -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ^( MODIFIERS ( ^( VIRTUAL 'virtual' ) )? ) ^( NAME name ) ^( RETURN_TYPE ( returnType )? ) ^( BLOCKS ruleFunctionNT ) ) )
            // Rules.g:220:4: ( 'virtual' )? ( returnType )? 'rulefunction' name[BaseRulesParser.TYPE_RULE_DEF] LBRACE ruleFunctionNT RBRACE
            {
            // Rules.g:220:4: ( 'virtual' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==172) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Rules.g:220:5: 'virtual'
                    {
                    string_literal39=(Token)match(input,172,FOLLOW_172_in_ruleFunctionDeclaration980); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_172.add(string_literal39);

                    if ( state.backtracking==0 ) {
                       virtual=true;
                    }

                    }
                    break;

            }

            // Rules.g:220:34: ( returnType )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==Identifier||LA10_0==NullLiteral||(LA10_0>=164 && LA10_0<=212)||LA10_0==215||(LA10_0>=228 && LA10_0<=231)) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Rules.g:220:34: returnType
                    {
                    pushFollow(FOLLOW_returnType_in_ruleFunctionDeclaration986);
                    returnType40=returnType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_returnType.add(returnType40.getTree());

                    }
                    break;

            }

            string_literal41=(Token)match(input,237,FOLLOW_237_in_ruleFunctionDeclaration989); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_237.add(string_literal41);

            pushFollow(FOLLOW_name_in_ruleFunctionDeclaration991);
            name42=name(BaseRulesParser.TYPE_RULE_DEF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name42.getTree());
            LBRACE43=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_ruleFunctionDeclaration994); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE43);

            pushFollow(FOLLOW_ruleFunctionNT_in_ruleFunctionDeclaration996);
            ruleFunctionNT44=ruleFunctionNT();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ruleFunctionNT.add(ruleFunctionNT44.getTree());
            RBRACE45=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_ruleFunctionDeclaration998); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE45);

            if ( state.backtracking==0 ) {
               setRuleElementType(ELEMENT_TYPES.RULE_FUNCTION); 
            }


            // AST REWRITE
            // elements: ruleFunctionNT, name, returnType, 172
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 222:2: -> ^( RULE_FUNC_DECL[\"ruleFunction\"] ^( MODIFIERS ( ^( VIRTUAL 'virtual' ) )? ) ^( NAME name ) ^( RETURN_TYPE ( returnType )? ) ^( BLOCKS ruleFunctionNT ) )
            {
                // Rules.g:222:5: ^( RULE_FUNC_DECL[\"ruleFunction\"] ^( MODIFIERS ( ^( VIRTUAL 'virtual' ) )? ) ^( NAME name ) ^( RETURN_TYPE ( returnType )? ) ^( BLOCKS ruleFunctionNT ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RULE_FUNC_DECL, "ruleFunction"), root_1);

                // Rules.g:222:38: ^( MODIFIERS ( ^( VIRTUAL 'virtual' ) )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(MODIFIERS, "MODIFIERS"), root_2);

                // Rules.g:222:50: ( ^( VIRTUAL 'virtual' ) )?
                if ( stream_172.hasNext() ) {
                    // Rules.g:222:50: ^( VIRTUAL 'virtual' )
                    {
                    RulesASTNode root_3 = (RulesASTNode)adaptor.nil();
                    root_3 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(VIRTUAL, "VIRTUAL"), root_3);

                    adaptor.addChild(root_3, stream_172.nextNode());

                    adaptor.addChild(root_2, root_3);
                    }

                }
                stream_172.reset();

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:222:73: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:222:86: ^( RETURN_TYPE ( returnType )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RETURN_TYPE, "RETURN_TYPE"), root_2);

                // Rules.g:222:100: ( returnType )?
                if ( stream_returnType.hasNext() ) {
                    adaptor.addChild(root_2, stream_returnType.nextTree());

                }
                stream_returnType.reset();

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:222:113: ^( BLOCKS ruleFunctionNT )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCKS, "BLOCKS"), root_2);

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
    // $ANTLR end "ruleFunctionDeclaration"

    public static class returnType_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "returnType"
    // Rules.g:225:1: returnType : (n= nameOrPrimitiveType ( '[' ']' )? | voidLiteral );
    public final RulesParser.returnType_return returnType() throws RecognitionException {
        RulesParser.returnType_return retval = new RulesParser.returnType_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal46=null;
        Token char_literal47=null;
        Rules_BaseElement.nameOrPrimitiveType_return n = null;

        RulesParser.voidLiteral_return voidLiteral48 = null;


        RulesASTNode char_literal46_tree=null;
        RulesASTNode char_literal47_tree=null;

        try {
            // Rules.g:226:2: (n= nameOrPrimitiveType ( '[' ']' )? | voidLiteral )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==Identifier||LA12_0==NullLiteral||(LA12_0>=164 && LA12_0<=212)||(LA12_0>=228 && LA12_0<=231)) ) {
                alt12=1;
            }
            else if ( (LA12_0==215) ) {
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
                    // Rules.g:226:4: n= nameOrPrimitiveType ( '[' ']' )?
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    if ( state.backtracking==0 ) {
                       setTypeReference(true); 
                    }
                    pushFollow(FOLLOW_nameOrPrimitiveType_in_returnType1054);
                    n=nameOrPrimitiveType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, n.getTree());
                    if ( state.backtracking==0 ) {
                       setTypeReference(false); 
                    }
                    // Rules.g:226:83: ( '[' ']' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==LBRACK) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // Rules.g:226:85: '[' ']'
                            {
                            char_literal46=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_returnType1060); if (state.failed) return retval;
                            char_literal47=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_returnType1063); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               markAsArray((RulesASTNode) n.getTree()); 
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // Rules.g:227:5: voidLiteral
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_voidLiteral_in_returnType1075);
                    voidLiteral48=voidLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, voidLiteral48.getTree());

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
    // $ANTLR end "returnType"

    public static class voidLiteral_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "voidLiteral"
    // Rules.g:230:1: voidLiteral : 'void' -> ^( VOID_LITERAL 'void' ) ;
    public final RulesParser.voidLiteral_return voidLiteral() throws RecognitionException {
        RulesParser.voidLiteral_return retval = new RulesParser.voidLiteral_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal49=null;

        RulesASTNode string_literal49_tree=null;
        RewriteRuleTokenStream stream_215=new RewriteRuleTokenStream(adaptor,"token 215");

        try {
            // Rules.g:231:2: ( 'void' -> ^( VOID_LITERAL 'void' ) )
            // Rules.g:231:4: 'void'
            {
            string_literal49=(Token)match(input,215,FOLLOW_215_in_voidLiteral1086); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_215.add(string_literal49);



            // AST REWRITE
            // elements: 215
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 231:11: -> ^( VOID_LITERAL 'void' )
            {
                // Rules.g:231:14: ^( VOID_LITERAL 'void' )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(VOID_LITERAL, "VOID_LITERAL"), root_1);

                adaptor.addChild(root_1, stream_215.nextNode());

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
    // $ANTLR end "voidLiteral"

    public static class ruleNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleNT"
    // Rules.g:234:1: ruleNT : ( attributeNT | declareNT | whenNT | thenNT )* ;
    public final RulesParser.ruleNT_return ruleNT() throws RecognitionException {
        RulesParser.ruleNT_return retval = new RulesParser.ruleNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.attributeNT_return attributeNT50 = null;

        RulesParser.declareNT_return declareNT51 = null;

        RulesParser.whenNT_return whenNT52 = null;

        RulesParser.thenNT_return thenNT53 = null;



        try {
            // Rules.g:242:8: ( ( attributeNT | declareNT | whenNT | thenNT )* )
            // Rules.g:243:2: ( attributeNT | declareNT | whenNT | thenNT )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:243:2: ( attributeNT | declareNT | whenNT | thenNT )*
            loop13:
            do {
                int alt13=5;
                switch ( input.LA(1) ) {
                case 240:
                    {
                    alt13=1;
                    }
                    break;
                case 232:
                    {
                    alt13=2;
                    }
                    break;
                case 239:
                    {
                    alt13=3;
                    }
                    break;
                case 234:
                    {
                    alt13=4;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // Rules.g:243:3: attributeNT
            	    {
            	    pushFollow(FOLLOW_attributeNT_in_ruleNT1112);
            	    attributeNT50=attributeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attributeNT50.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Rules.g:244:4: declareNT
            	    {
            	    pushFollow(FOLLOW_declareNT_in_ruleNT1117);
            	    declareNT51=declareNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, declareNT51.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Rules.g:245:4: whenNT
            	    {
            	    pushFollow(FOLLOW_whenNT_in_ruleNT1122);
            	    whenNT52=whenNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, whenNT52.getTree());

            	    }
            	    break;
            	case 4 :
            	    // Rules.g:246:4: thenNT
            	    {
            	    pushFollow(FOLLOW_thenNT_in_ruleNT1127);
            	    thenNT53=thenNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenNT53.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
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
    // $ANTLR end "ruleNT"

    public static class ruleFunctionNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruleFunctionNT"
    // Rules.g:249:1: ruleFunctionNT : ( attributeNT | scopeNT | bodyNT )* ;
    public final RulesParser.ruleFunctionNT_return ruleFunctionNT() throws RecognitionException {
        RulesParser.ruleFunctionNT_return retval = new RulesParser.ruleFunctionNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.attributeNT_return attributeNT54 = null;

        RulesParser.scopeNT_return scopeNT55 = null;

        RulesParser.bodyNT_return bodyNT56 = null;



        try {
            // Rules.g:249:16: ( ( attributeNT | scopeNT | bodyNT )* )
            // Rules.g:250:2: ( attributeNT | scopeNT | bodyNT )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:250:2: ( attributeNT | scopeNT | bodyNT )*
            loop14:
            do {
                int alt14=4;
                switch ( input.LA(1) ) {
                case 240:
                    {
                    alt14=1;
                    }
                    break;
                case 233:
                    {
                    alt14=2;
                    }
                    break;
                case 164:
                    {
                    alt14=3;
                    }
                    break;

                }

                switch (alt14) {
            	case 1 :
            	    // Rules.g:250:3: attributeNT
            	    {
            	    pushFollow(FOLLOW_attributeNT_in_ruleFunctionNT1143);
            	    attributeNT54=attributeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attributeNT54.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Rules.g:251:4: scopeNT
            	    {
            	    pushFollow(FOLLOW_scopeNT_in_ruleFunctionNT1148);
            	    scopeNT55=scopeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, scopeNT55.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Rules.g:252:4: bodyNT
            	    {
            	    pushFollow(FOLLOW_bodyNT_in_ruleFunctionNT1153);
            	    bodyNT56=bodyNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, bodyNT56.getTree());

            	    }
            	    break;

            	default :
            	    break loop14;
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
    // $ANTLR end "ruleFunctionNT"

    public static class ruletemplateNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "ruletemplateNT"
    // Rules.g:255:1: ruletemplateNT : ( attributeNT | bindingViewsNT | bindingsNT | displayNT | variablesNT | preConditionsNT | actionContextNT )* ;
    public final RulesParser.ruletemplateNT_return ruletemplateNT() throws RecognitionException {
        RulesParser.ruletemplateNT_return retval = new RulesParser.ruletemplateNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.attributeNT_return attributeNT57 = null;

        RulesParser.bindingViewsNT_return bindingViewsNT58 = null;

        RulesParser.bindingsNT_return bindingsNT59 = null;

        RulesParser.displayNT_return displayNT60 = null;

        RulesParser.variablesNT_return variablesNT61 = null;

        RulesParser.preConditionsNT_return preConditionsNT62 = null;

        RulesParser.actionContextNT_return actionContextNT63 = null;



        try {
            // Rules.g:256:2: ( ( attributeNT | bindingViewsNT | bindingsNT | displayNT | variablesNT | preConditionsNT | actionContextNT )* )
            // Rules.g:256:4: ( attributeNT | bindingViewsNT | bindingsNT | displayNT | variablesNT | preConditionsNT | actionContextNT )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:256:4: ( attributeNT | bindingViewsNT | bindingsNT | displayNT | variablesNT | preConditionsNT | actionContextNT )*
            loop15:
            do {
                int alt15=8;
                switch ( input.LA(1) ) {
                case 240:
                    {
                    alt15=1;
                    }
                    break;
                case 208:
                    {
                    alt15=2;
                    }
                    break;
                case 209:
                    {
                    alt15=3;
                    }
                    break;
                case 210:
                    {
                    alt15=4;
                    }
                    break;
                case 232:
                    {
                    alt15=5;
                    }
                    break;
                case 239:
                    {
                    alt15=6;
                    }
                    break;
                case 238:
                    {
                    alt15=7;
                    }
                    break;

                }

                switch (alt15) {
            	case 1 :
            	    // Rules.g:256:5: attributeNT
            	    {
            	    pushFollow(FOLLOW_attributeNT_in_ruletemplateNT1167);
            	    attributeNT57=attributeNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attributeNT57.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Rules.g:257:4: bindingViewsNT
            	    {
            	    pushFollow(FOLLOW_bindingViewsNT_in_ruletemplateNT1172);
            	    bindingViewsNT58=bindingViewsNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, bindingViewsNT58.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Rules.g:258:4: bindingsNT
            	    {
            	    pushFollow(FOLLOW_bindingsNT_in_ruletemplateNT1177);
            	    bindingsNT59=bindingsNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, bindingsNT59.getTree());

            	    }
            	    break;
            	case 4 :
            	    // Rules.g:259:4: displayNT
            	    {
            	    pushFollow(FOLLOW_displayNT_in_ruletemplateNT1182);
            	    displayNT60=displayNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, displayNT60.getTree());

            	    }
            	    break;
            	case 5 :
            	    // Rules.g:260:4: variablesNT
            	    {
            	    pushFollow(FOLLOW_variablesNT_in_ruletemplateNT1187);
            	    variablesNT61=variablesNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, variablesNT61.getTree());

            	    }
            	    break;
            	case 6 :
            	    // Rules.g:261:4: preConditionsNT
            	    {
            	    pushFollow(FOLLOW_preConditionsNT_in_ruletemplateNT1192);
            	    preConditionsNT62=preConditionsNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, preConditionsNT62.getTree());

            	    }
            	    break;
            	case 7 :
            	    // Rules.g:262:4: actionContextNT
            	    {
            	    pushFollow(FOLLOW_actionContextNT_in_ruletemplateNT1197);
            	    actionContextNT63=actionContextNT();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextNT63.getTree());

            	    }
            	    break;

            	default :
            	    break loop15;
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
    // $ANTLR end "ruletemplateNT"

    public static class bindingViewsNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingViewsNT"
    // Rules.g:265:1: bindingViewsNT : 'views' bindingViewsNTBlock -> ^( BINDINGS_VIEWS_BLOCK[\"views\"] bindingViewsNTBlock ) ;
    public final RulesParser.bindingViewsNT_return bindingViewsNT() throws RecognitionException {
        RulesParser.bindingViewsNT_return retval = new RulesParser.bindingViewsNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal64=null;
        RulesParser.bindingViewsNTBlock_return bindingViewsNTBlock65 = null;


        RulesASTNode string_literal64_tree=null;
        RewriteRuleTokenStream stream_208=new RewriteRuleTokenStream(adaptor,"token 208");
        RewriteRuleSubtreeStream stream_bindingViewsNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule bindingViewsNTBlock");
        try {
            // Rules.g:266:2: ( 'views' bindingViewsNTBlock -> ^( BINDINGS_VIEWS_BLOCK[\"views\"] bindingViewsNTBlock ) )
            // Rules.g:266:4: 'views' bindingViewsNTBlock
            {
            string_literal64=(Token)match(input,208,FOLLOW_208_in_bindingViewsNT1210); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_208.add(string_literal64);

            pushFollow(FOLLOW_bindingViewsNTBlock_in_bindingViewsNT1212);
            bindingViewsNTBlock65=bindingViewsNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_bindingViewsNTBlock.add(bindingViewsNTBlock65.getTree());


            // AST REWRITE
            // elements: bindingViewsNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 267:2: -> ^( BINDINGS_VIEWS_BLOCK[\"views\"] bindingViewsNTBlock )
            {
                // Rules.g:267:5: ^( BINDINGS_VIEWS_BLOCK[\"views\"] bindingViewsNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BINDINGS_VIEWS_BLOCK, "views"), root_1);

                adaptor.addChild(root_1, stream_bindingViewsNTBlock.nextTree());

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
    // $ANTLR end "bindingViewsNT"

    public static class bindingViewsNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingViewsNTBlock"
    // Rules.g:270:1: bindingViewsNTBlock : LBRACE ( bindingViewStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( bindingViewStatement )* ) ) ;
    public final RulesParser.bindingViewsNTBlock_return bindingViewsNTBlock() throws RecognitionException {
        RulesParser.bindingViewsNTBlock_return retval = new RulesParser.bindingViewsNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE66=null;
        Token RBRACE68=null;
        RulesParser.bindingViewStatement_return bindingViewStatement67 = null;


        RulesASTNode LBRACE66_tree=null;
        RulesASTNode RBRACE68_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_bindingViewStatement=new RewriteRuleSubtreeStream(adaptor,"rule bindingViewStatement");
        try {
            // Rules.g:271:2: ( LBRACE ( bindingViewStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( bindingViewStatement )* ) ) )
            // Rules.g:271:5: LBRACE ( bindingViewStatement )* RBRACE
            {
            LBRACE66=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_bindingViewsNTBlock1234); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE66);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.BINDING_VIEWS_SCOPE); 
            }
            // Rules.g:271:64: ( bindingViewStatement )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==Identifier||LA16_0==NullLiteral||(LA16_0>=164 && LA16_0<=212)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // Rules.g:271:64: bindingViewStatement
            	    {
            	    pushFollow(FOLLOW_bindingViewStatement_in_bindingViewsNTBlock1238);
            	    bindingViewStatement67=bindingViewStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_bindingViewStatement.add(bindingViewStatement67.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            RBRACE68=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_bindingViewsNTBlock1243); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE68);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: bindingViewStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 274:2: -> ^( BLOCK ^( STATEMENTS ( bindingViewStatement )* ) )
            {
                // Rules.g:274:5: ^( BLOCK ^( STATEMENTS ( bindingViewStatement )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:274:13: ^( STATEMENTS ( bindingViewStatement )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:274:26: ( bindingViewStatement )*
                while ( stream_bindingViewStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_bindingViewStatement.nextTree());

                }
                stream_bindingViewStatement.reset();

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
    // $ANTLR end "bindingViewsNTBlock"

    public static class bindingViewStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingViewStatement"
    // Rules.g:277:1: bindingViewStatement : name[BaseRulesParser.TYPE_VIEW_REF] SEMICOLON -> ^( BINDING_VIEW_STATEMENT[\"viewReference\"] ^( NAME name ) ) ;
    public final RulesParser.bindingViewStatement_return bindingViewStatement() throws RecognitionException {
        RulesParser.bindingViewStatement_return retval = new RulesParser.bindingViewStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token SEMICOLON70=null;
        Rules_BaseElement.name_return name69 = null;


        RulesASTNode SEMICOLON70_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Rules.g:278:2: ( name[BaseRulesParser.TYPE_VIEW_REF] SEMICOLON -> ^( BINDING_VIEW_STATEMENT[\"viewReference\"] ^( NAME name ) ) )
            // Rules.g:278:4: name[BaseRulesParser.TYPE_VIEW_REF] SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_name_in_bindingViewStatement1275);
            name69=name(BaseRulesParser.TYPE_VIEW_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name69.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            SEMICOLON70=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_bindingViewStatement1280); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON70);



            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 279:2: -> ^( BINDING_VIEW_STATEMENT[\"viewReference\"] ^( NAME name ) )
            {
                // Rules.g:279:5: ^( BINDING_VIEW_STATEMENT[\"viewReference\"] ^( NAME name ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BINDING_VIEW_STATEMENT, "viewReference"), root_1);

                // Rules.g:279:47: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

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
    // $ANTLR end "bindingViewStatement"

    public static class bindingsNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingsNT"
    // Rules.g:282:1: bindingsNT : 'bindings' bindingsNTBlock -> ^( BINDINGS_BLOCK[\"bindings\"] bindingsNTBlock ) ;
    public final RulesParser.bindingsNT_return bindingsNT() throws RecognitionException {
        RulesParser.bindingsNT_return retval = new RulesParser.bindingsNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal71=null;
        RulesParser.bindingsNTBlock_return bindingsNTBlock72 = null;


        RulesASTNode string_literal71_tree=null;
        RewriteRuleTokenStream stream_209=new RewriteRuleTokenStream(adaptor,"token 209");
        RewriteRuleSubtreeStream stream_bindingsNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule bindingsNTBlock");
        try {
            // Rules.g:283:2: ( 'bindings' bindingsNTBlock -> ^( BINDINGS_BLOCK[\"bindings\"] bindingsNTBlock ) )
            // Rules.g:283:4: 'bindings' bindingsNTBlock
            {
            string_literal71=(Token)match(input,209,FOLLOW_209_in_bindingsNT1306); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_209.add(string_literal71);

            pushFollow(FOLLOW_bindingsNTBlock_in_bindingsNT1308);
            bindingsNTBlock72=bindingsNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_bindingsNTBlock.add(bindingsNTBlock72.getTree());


            // AST REWRITE
            // elements: bindingsNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 284:2: -> ^( BINDINGS_BLOCK[\"bindings\"] bindingsNTBlock )
            {
                // Rules.g:284:5: ^( BINDINGS_BLOCK[\"bindings\"] bindingsNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BINDINGS_BLOCK, "bindings"), root_1);

                adaptor.addChild(root_1, stream_bindingsNTBlock.nextTree());

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
    // $ANTLR end "bindingsNT"

    public static class bindingsNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingsNTBlock"
    // Rules.g:287:1: bindingsNTBlock : LBRACE ( bindingStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( bindingStatement )* ) ) ;
    public final RulesParser.bindingsNTBlock_return bindingsNTBlock() throws RecognitionException {
        RulesParser.bindingsNTBlock_return retval = new RulesParser.bindingsNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE73=null;
        Token RBRACE75=null;
        RulesParser.bindingStatement_return bindingStatement74 = null;


        RulesASTNode LBRACE73_tree=null;
        RulesASTNode RBRACE75_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_bindingStatement=new RewriteRuleSubtreeStream(adaptor,"rule bindingStatement");
        try {
            // Rules.g:288:2: ( LBRACE ( bindingStatement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( bindingStatement )* ) ) )
            // Rules.g:288:5: LBRACE ( bindingStatement )* RBRACE
            {
            LBRACE73=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_bindingsNTBlock1330); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE73);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.BINDINGS_SCOPE); 
            }
            // Rules.g:288:59: ( bindingStatement )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==Identifier||LA17_0==NullLiteral||(LA17_0>=164 && LA17_0<=212)||(LA17_0>=228 && LA17_0<=231)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // Rules.g:288:59: bindingStatement
            	    {
            	    pushFollow(FOLLOW_bindingStatement_in_bindingsNTBlock1334);
            	    bindingStatement74=bindingStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_bindingStatement.add(bindingStatement74.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            RBRACE75=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_bindingsNTBlock1339); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE75);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: bindingStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 291:2: -> ^( BLOCK ^( STATEMENTS ( bindingStatement )* ) )
            {
                // Rules.g:291:5: ^( BLOCK ^( STATEMENTS ( bindingStatement )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:291:13: ^( STATEMENTS ( bindingStatement )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:291:26: ( bindingStatement )*
                while ( stream_bindingStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_bindingStatement.nextTree());

                }
                stream_bindingStatement.reset();

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
    // $ANTLR end "bindingsNTBlock"

    public static class bindingStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindingStatement"
    // Rules.g:294:1: bindingStatement : t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? ( domainModelStatement )? SEMICOLON -> ^( BINDING_STATEMENT[\"binding\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ( ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement ) )? ) ;
    public final RulesParser.bindingStatement_return bindingStatement() throws RecognitionException {
        RulesParser.bindingStatement_return retval = new RulesParser.bindingStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token ASSIGN76=null;
        Token SEMICOLON80=null;
        Rules_BaseElement.nameOrPrimitiveType_return t = null;

        RulesParser.identifier_return id = null;

        Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral77 = null;

        Rules_BaseElement.expression_return expression78 = null;

        RulesParser.domainModelStatement_return domainModelStatement79 = null;


        RulesASTNode ASSIGN76_tree=null;
        RulesASTNode SEMICOLON80_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_domainModelStatement=new RewriteRuleSubtreeStream(adaptor,"rule domainModelStatement");
        RewriteRuleSubtreeStream stream_nameOrPrimitiveType=new RewriteRuleSubtreeStream(adaptor,"rule nameOrPrimitiveType");
        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Rules.g:295:2: (t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? ( domainModelStatement )? SEMICOLON -> ^( BINDING_STATEMENT[\"binding\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ( ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement ) )? ) )
            // Rules.g:295:4: t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? ( domainModelStatement )? SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_nameOrPrimitiveType_in_bindingStatement1373);
            t=nameOrPrimitiveType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_nameOrPrimitiveType.add(t.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            pushFollow(FOLLOW_identifier_in_bindingStatement1379);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); 
            }
            // Rules.g:297:2: ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ASSIGN) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Rules.g:297:3: ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    {
                    ASSIGN76=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_bindingStatement1387); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN76);

                    // Rules.g:297:11: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    int alt18=2;
                    alt18 = dfa18.predict(input);
                    switch (alt18) {
                        case 1 :
                            // Rules.g:297:12: ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral
                            {
                            pushFollow(FOLLOW_localInitializerArrayLiteral_in_bindingStatement1403);
                            localInitializerArrayLiteral77=localInitializerArrayLiteral();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral77.getTree());

                            }
                            break;
                        case 2 :
                            // Rules.g:298:4: expression
                            {
                            pushFollow(FOLLOW_expression_in_bindingStatement1408);
                            expression78=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression78.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }

            // Rules.g:298:18: ( domainModelStatement )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LPAREN) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // Rules.g:298:18: domainModelStatement
                    {
                    pushFollow(FOLLOW_domainModelStatement_in_bindingStatement1413);
                    domainModelStatement79=domainModelStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_domainModelStatement.add(domainModelStatement79.getTree());

                    }
                    break;

            }

            SEMICOLON80=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_bindingStatement1416); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON80);



            // AST REWRITE
            // elements: expression, localInitializerArrayLiteral, t, id, domainModelStatement
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
            // 299:2: -> ^( BINDING_STATEMENT[\"binding\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ( ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement ) )? )
            {
                // Rules.g:299:5: ^( BINDING_STATEMENT[\"binding\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ( ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement ) )? )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BINDING_STATEMENT, "binding"), root_1);

                // Rules.g:299:36: ^( TYPE[\"type\"] $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:300:3: ^( NAME[\"name\"] $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:300:23: ( ^( INITIALIZER localInitializerArrayLiteral ) )?
                if ( stream_localInitializerArrayLiteral.hasNext() ) {
                    // Rules.g:300:23: ^( INITIALIZER localInitializerArrayLiteral )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

                    adaptor.addChild(root_2, stream_localInitializerArrayLiteral.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_localInitializerArrayLiteral.reset();
                // Rules.g:301:3: ( ^( EXPRESSION expression ) )?
                if ( stream_expression.hasNext() ) {
                    // Rules.g:301:3: ^( EXPRESSION expression )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(EXPRESSION, "EXPRESSION"), root_2);

                    adaptor.addChild(root_2, stream_expression.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_expression.reset();
                // Rules.g:301:29: ( ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement ) )?
                if ( stream_domainModelStatement.hasNext() ) {
                    // Rules.g:301:29: ^( DOMAIN_MODEL[\"domain model\"] domainModelStatement )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DOMAIN_MODEL, "domain model"), root_2);

                    adaptor.addChild(root_2, stream_domainModelStatement.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_domainModelStatement.reset();

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
    // $ANTLR end "bindingStatement"

    public static class domainModelStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "domainModelStatement"
    // Rules.g:304:1: domainModelStatement : LPAREN name[BaseRulesParser.DOMAIN_REF] RPAREN -> ^( DOMAIN_MODEL_STATEMENT ^( NAME name ) ) ;
    public final RulesParser.domainModelStatement_return domainModelStatement() throws RecognitionException {
        RulesParser.domainModelStatement_return retval = new RulesParser.domainModelStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LPAREN81=null;
        Token RPAREN83=null;
        Rules_BaseElement.name_return name82 = null;


        RulesASTNode LPAREN81_tree=null;
        RulesASTNode RPAREN83_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Rules.g:305:2: ( LPAREN name[BaseRulesParser.DOMAIN_REF] RPAREN -> ^( DOMAIN_MODEL_STATEMENT ^( NAME name ) ) )
            // Rules.g:305:4: LPAREN name[BaseRulesParser.DOMAIN_REF] RPAREN
            {
            LPAREN81=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_domainModelStatement1478); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN81);

            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_name_in_domainModelStatement1482);
            name82=name(BaseRulesParser.DOMAIN_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name82.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            RPAREN83=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_domainModelStatement1487); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN83);



            // AST REWRITE
            // elements: name
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 306:2: -> ^( DOMAIN_MODEL_STATEMENT ^( NAME name ) )
            {
                // Rules.g:306:5: ^( DOMAIN_MODEL_STATEMENT ^( NAME name ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DOMAIN_MODEL_STATEMENT, "DOMAIN_MODEL_STATEMENT"), root_1);

                // Rules.g:306:30: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

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
    // $ANTLR end "domainModelStatement"

    public static class actionContextNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionContextNT"
    // Rules.g:325:1: actionContextNT : 'actionContext' actionContextNTBlock -> ^( ACTION_CONTEXT_BLOCK[\"action context\"] actionContextNTBlock ) ;
    public final RulesParser.actionContextNT_return actionContextNT() throws RecognitionException {
        RulesParser.actionContextNT_return retval = new RulesParser.actionContextNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal84=null;
        RulesParser.actionContextNTBlock_return actionContextNTBlock85 = null;


        RulesASTNode string_literal84_tree=null;
        RewriteRuleTokenStream stream_238=new RewriteRuleTokenStream(adaptor,"token 238");
        RewriteRuleSubtreeStream stream_actionContextNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule actionContextNTBlock");
        try {
            // Rules.g:326:2: ( 'actionContext' actionContextNTBlock -> ^( ACTION_CONTEXT_BLOCK[\"action context\"] actionContextNTBlock ) )
            // Rules.g:326:4: 'actionContext' actionContextNTBlock
            {
            string_literal84=(Token)match(input,238,FOLLOW_238_in_actionContextNT1515); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_238.add(string_literal84);

            pushFollow(FOLLOW_actionContextNTBlock_in_actionContextNT1517);
            actionContextNTBlock85=actionContextNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_actionContextNTBlock.add(actionContextNTBlock85.getTree());


            // AST REWRITE
            // elements: actionContextNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 327:2: -> ^( ACTION_CONTEXT_BLOCK[\"action context\"] actionContextNTBlock )
            {
                // Rules.g:327:5: ^( ACTION_CONTEXT_BLOCK[\"action context\"] actionContextNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ACTION_CONTEXT_BLOCK, "action context"), root_1);

                adaptor.addChild(root_1, stream_actionContextNTBlock.nextTree());

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
    // $ANTLR end "actionContextNT"

    public static class actionContextNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionContextNTBlock"
    // Rules.g:330:1: actionContextNTBlock : LBRACE actionContextStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatements )? ) ) ;
    public final RulesParser.actionContextNTBlock_return actionContextNTBlock() throws RecognitionException {
        RulesParser.actionContextNTBlock_return retval = new RulesParser.actionContextNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE86=null;
        Token RBRACE88=null;
        RulesParser.actionContextStatements_return actionContextStatements87 = null;


        RulesASTNode LBRACE86_tree=null;
        RulesASTNode RBRACE88_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_actionContextStatements=new RewriteRuleSubtreeStream(adaptor,"rule actionContextStatements");
        try {
            // Rules.g:331:2: ( LBRACE actionContextStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatements )? ) ) )
            // Rules.g:331:5: LBRACE actionContextStatements RBRACE
            {
            LBRACE86=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_actionContextNTBlock1539); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE86);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.ACTION_CONTEXT_SCOPE); setInActionContextBlock(true); 
            }
            pushFollow(FOLLOW_actionContextStatements_in_actionContextNTBlock1543);
            actionContextStatements87=actionContextStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_actionContextStatements.add(actionContextStatements87.getTree());
            RBRACE88=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_actionContextNTBlock1547); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE88);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: actionContextStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 334:2: -> ^( BLOCK ^( STATEMENTS ( actionContextStatements )? ) )
            {
                // Rules.g:334:5: ^( BLOCK ^( STATEMENTS ( actionContextStatements )? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:334:13: ^( STATEMENTS ( actionContextStatements )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:334:26: ( actionContextStatements )?
                if ( stream_actionContextStatements.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionContextStatements.nextTree());

                }
                stream_actionContextStatements.reset();

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
    // $ANTLR end "actionContextNTBlock"

    public static class actionContextStatements_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionContextStatements"
    // Rules.g:337:1: actionContextStatements : ( acStatement )* ;
    public final RulesParser.actionContextStatements_return actionContextStatements() throws RecognitionException {
        RulesParser.actionContextStatements_return retval = new RulesParser.actionContextStatements_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.acStatement_return acStatement89 = null;



        try {
            // Rules.g:338:2: ( ( acStatement )* )
            // Rules.g:338:4: ( acStatement )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:338:4: ( acStatement )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=Identifier && LA21_0<=SEMICOLON)||LA21_0==LBRACE||(LA21_0>=FloatingPointLiteral && LA21_0<=WS)||LA21_0==LPAREN||(LA21_0>=164 && LA21_0<=212)||(LA21_0>=217 && LA21_0<=221)||(LA21_0>=223 && LA21_0<=225)||(LA21_0>=228 && LA21_0<=231)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // Rules.g:338:4: acStatement
            	    {
            	    pushFollow(FOLLOW_acStatement_in_actionContextStatements1577);
            	    acStatement89=acStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, acStatement89.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
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
    // $ANTLR end "actionContextStatements"

    public static class acStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "acStatement"
    // Rules.g:341:1: acStatement : ( actionContextStatement | thenStatement );
    public final RulesParser.acStatement_return acStatement() throws RecognitionException {
        RulesParser.acStatement_return retval = new RulesParser.acStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.actionContextStatement_return actionContextStatement90 = null;

        RulesParser.thenStatement_return thenStatement91 = null;



        try {
            // Rules.g:342:2: ( actionContextStatement | thenStatement )
            int alt22=2;
            alt22 = dfa22.predict(input);
            switch (alt22) {
                case 1 :
                    // Rules.g:342:4: actionContextStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_actionContextStatement_in_acStatement1590);
                    actionContextStatement90=actionContextStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextStatement90.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:342:29: thenStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_thenStatement_in_acStatement1594);
                    thenStatement91=thenStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement91.getTree());

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
    // $ANTLR end "acStatement"

    public static class actionContextStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "actionContextStatement"
    // Rules.g:345:1: actionContextStatement : (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) ) ;
    public final RulesParser.actionContextStatement_return actionContextStatement() throws RecognitionException {
        RulesParser.actionContextStatement_return retval = new RulesParser.actionContextStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token t=null;
        Token SEMICOLON92=null;
        Rules_BaseElement.methodName_return m = null;

        RulesParser.identifier_return id = null;

        Rules_BaseElement.name_return nm = null;


        RulesASTNode t_tree=null;
        RulesASTNode SEMICOLON92_tree=null;
        RewriteRuleTokenStream stream_205=new RewriteRuleTokenStream(adaptor,"token 205");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_206=new RewriteRuleTokenStream(adaptor,"token 206");
        RewriteRuleTokenStream stream_207=new RewriteRuleTokenStream(adaptor,"token 207");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_methodName=new RewriteRuleSubtreeStream(adaptor,"rule methodName");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Rules.g:346:2: ( (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) ) )
            // Rules.g:346:4: (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) ) SEMICOLON
            {
            // Rules.g:346:4: (t= 'call' m= methodName id= identifier | ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==207) ) {
                alt24=1;
            }
            else if ( ((LA24_0>=205 && LA24_0<=206)) ) {
                alt24=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // Rules.g:346:5: t= 'call' m= methodName id= identifier
                    {
                    t=(Token)match(input,207,FOLLOW_207_in_actionContextStatement1609); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_207.add(t);

                    pushFollow(FOLLOW_methodName_in_actionContextStatement1613);
                    m=methodName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_methodName.add(m.getTree());
                    pushFollow(FOLLOW_identifier_in_actionContextStatement1617);
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
                    // Rules.g:347:3: ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) )
                    {
                    // Rules.g:347:3: ( (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier ) )
                    // Rules.g:347:4: (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier )
                    {
                    // Rules.g:347:4: (t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier | t= 'modify' id= identifier )
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==205) ) {
                        alt23=1;
                    }
                    else if ( (LA23_0==206) ) {
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
                            // Rules.g:347:5: t= 'create' nm= name[BaseRulesParser.TYPE_REF] id= identifier
                            {
                            t=(Token)match(input,205,FOLLOW_205_in_actionContextStatement1629); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_205.add(t);

                            if ( state.backtracking==0 ) {
                               setTypeReference(true); 
                            }
                            pushFollow(FOLLOW_name_in_actionContextStatement1635);
                            nm=name(BaseRulesParser.TYPE_REF);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_name.add(nm.getTree());
                            if ( state.backtracking==0 ) {
                               setTypeReference(false); 
                            }
                            pushFollow(FOLLOW_identifier_in_actionContextStatement1642);
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
                            // Rules.g:348:5: t= 'modify' id= identifier
                            {
                            t=(Token)match(input,206,FOLLOW_206_in_actionContextStatement1652); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_206.add(t);

                            pushFollow(FOLLOW_identifier_in_actionContextStatement1656);
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

            SEMICOLON92=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_actionContextStatement1663); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON92);



            // AST REWRITE
            // elements: id, nm, m, t
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

            root_0 = (RulesASTNode)adaptor.nil();
            // 350:3: -> ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) )
            {
                // Rules.g:350:6: ^( ACTION_CONTEXT_STATEMENT[\"actionContext\"] ^( ACTION_TYPE[\"action type\"] $t) ^( TYPE[\"type\"] ( $m)? ( $nm)? ) ^( NAME[\"name\"] $id) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ACTION_CONTEXT_STATEMENT, "actionContext"), root_1);

                // Rules.g:350:50: ^( ACTION_TYPE[\"action type\"] $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ACTION_TYPE, "action type"), root_2);

                adaptor.addChild(root_2, stream_t.nextNode());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:350:83: ^( TYPE[\"type\"] ( $m)? ( $nm)? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "type"), root_2);

                // Rules.g:350:98: ( $m)?
                if ( stream_m.hasNext() ) {
                    adaptor.addChild(root_2, stream_m.nextTree());

                }
                stream_m.reset();
                // Rules.g:350:102: ( $nm)?
                if ( stream_nm.hasNext() ) {
                    adaptor.addChild(root_2, stream_nm.nextTree());

                }
                stream_nm.reset();

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:350:108: ^( NAME[\"name\"] $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "name"), root_2);

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
    // $ANTLR end "actionContextStatement"

    public static class preConditionsNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "preConditionsNT"
    // Rules.g:370:1: preConditionsNT : 'when' preConditionsNTBlock -> ^( WHEN_BLOCK[\"when\"] preConditionsNTBlock ) ;
    public final RulesParser.preConditionsNT_return preConditionsNT() throws RecognitionException {
        RulesParser.preConditionsNT_return retval = new RulesParser.preConditionsNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal93=null;
        RulesParser.preConditionsNTBlock_return preConditionsNTBlock94 = null;


        RulesASTNode string_literal93_tree=null;
        RewriteRuleTokenStream stream_239=new RewriteRuleTokenStream(adaptor,"token 239");
        RewriteRuleSubtreeStream stream_preConditionsNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule preConditionsNTBlock");
        try {
            // Rules.g:371:2: ( 'when' preConditionsNTBlock -> ^( WHEN_BLOCK[\"when\"] preConditionsNTBlock ) )
            // Rules.g:371:4: 'when' preConditionsNTBlock
            {
            string_literal93=(Token)match(input,239,FOLLOW_239_in_preConditionsNT1713); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_239.add(string_literal93);

            pushFollow(FOLLOW_preConditionsNTBlock_in_preConditionsNT1715);
            preConditionsNTBlock94=preConditionsNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preConditionsNTBlock.add(preConditionsNTBlock94.getTree());


            // AST REWRITE
            // elements: preConditionsNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 372:2: -> ^( WHEN_BLOCK[\"when\"] preConditionsNTBlock )
            {
                // Rules.g:372:5: ^( WHEN_BLOCK[\"when\"] preConditionsNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(WHEN_BLOCK, "when"), root_1);

                adaptor.addChild(root_1, stream_preConditionsNTBlock.nextTree());

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
    // $ANTLR end "preConditionsNT"

    public static class preConditionsNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "preConditionsNTBlock"
    // Rules.g:375:1: preConditionsNTBlock : LBRACE preconditionStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( preconditionStatements )? ) ) ;
    public final RulesParser.preConditionsNTBlock_return preConditionsNTBlock() throws RecognitionException {
        RulesParser.preConditionsNTBlock_return retval = new RulesParser.preConditionsNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE95=null;
        Token RBRACE97=null;
        RulesParser.preconditionStatements_return preconditionStatements96 = null;


        RulesASTNode LBRACE95_tree=null;
        RulesASTNode RBRACE97_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_preconditionStatements=new RewriteRuleSubtreeStream(adaptor,"rule preconditionStatements");
        try {
            // Rules.g:376:2: ( LBRACE preconditionStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( preconditionStatements )? ) ) )
            // Rules.g:376:5: LBRACE preconditionStatements RBRACE
            {
            LBRACE95=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_preConditionsNTBlock1737); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE95);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.WHEN_SCOPE); 
            }
            pushFollow(FOLLOW_preconditionStatements_in_preConditionsNTBlock1741);
            preconditionStatements96=preconditionStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_preconditionStatements.add(preconditionStatements96.getTree());
            RBRACE97=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_preConditionsNTBlock1745); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE97);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: preconditionStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 379:2: -> ^( BLOCK ^( STATEMENTS ( preconditionStatements )? ) )
            {
                // Rules.g:379:5: ^( BLOCK ^( STATEMENTS ( preconditionStatements )? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:379:13: ^( STATEMENTS ( preconditionStatements )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:379:26: ( preconditionStatements )?
                if ( stream_preconditionStatements.hasNext() ) {
                    adaptor.addChild(root_2, stream_preconditionStatements.nextTree());

                }
                stream_preconditionStatements.reset();

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
    // $ANTLR end "preConditionsNTBlock"

    public static class preconditionStatements_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "preconditionStatements"
    // Rules.g:382:1: preconditionStatements : ( preconditionStatement )* ;
    public final RulesParser.preconditionStatements_return preconditionStatements() throws RecognitionException {
        RulesParser.preconditionStatements_return retval = new RulesParser.preconditionStatements_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.preconditionStatement_return preconditionStatement98 = null;



        try {
            // Rules.g:383:2: ( ( preconditionStatement )* )
            // Rules.g:383:4: ( preconditionStatement )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:383:4: ( preconditionStatement )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=Identifier && LA25_0<=SEMICOLON)||(LA25_0>=EQ && LA25_0<=LBRACE)||(LA25_0>=PLUS && LA25_0<=MINUS)||LA25_0==POUND||(LA25_0>=FloatingPointLiteral && LA25_0<=WS)||LA25_0==LPAREN||LA25_0==LBRACK||(LA25_0>=164 && LA25_0<=212)||(LA25_0>=217 && LA25_0<=221)||(LA25_0>=223 && LA25_0<=225)||(LA25_0>=228 && LA25_0<=231)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // Rules.g:383:5: preconditionStatement
            	    {
            	    pushFollow(FOLLOW_preconditionStatement_in_preconditionStatements1776);
            	    preconditionStatement98=preconditionStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, preconditionStatement98.getTree());

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
    // $ANTLR end "preconditionStatements"

    public static class preconditionStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "preconditionStatement"
    // Rules.g:386:1: preconditionStatement : ( ( predicateStatement )=> predicateStatement | thenStatement );
    public final RulesParser.preconditionStatement_return preconditionStatement() throws RecognitionException {
        RulesParser.preconditionStatement_return retval = new RulesParser.preconditionStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.predicateStatement_return predicateStatement99 = null;

        RulesParser.thenStatement_return thenStatement100 = null;



        try {
            // Rules.g:387:2: ( ( predicateStatement )=> predicateStatement | thenStatement )
            int alt26=2;
            alt26 = dfa26.predict(input);
            switch (alt26) {
                case 1 :
                    // Rules.g:387:4: ( predicateStatement )=> predicateStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_predicateStatement_in_preconditionStatement1795);
                    predicateStatement99=predicateStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, predicateStatement99.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:387:48: thenStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_thenStatement_in_preconditionStatement1799);
                    thenStatement100=thenStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement100.getTree());

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
    // $ANTLR end "preconditionStatement"

    public static class displayNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "displayNT"
    // Rules.g:390:1: displayNT : 'display' displayNTBlock -> ^( DISPLAY_BLOCK[\"display\"] displayNTBlock ) ;
    public final RulesParser.displayNT_return displayNT() throws RecognitionException {
        RulesParser.displayNT_return retval = new RulesParser.displayNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal101=null;
        RulesParser.displayNTBlock_return displayNTBlock102 = null;


        RulesASTNode string_literal101_tree=null;
        RewriteRuleTokenStream stream_210=new RewriteRuleTokenStream(adaptor,"token 210");
        RewriteRuleSubtreeStream stream_displayNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule displayNTBlock");
        try {
            // Rules.g:391:2: ( 'display' displayNTBlock -> ^( DISPLAY_BLOCK[\"display\"] displayNTBlock ) )
            // Rules.g:391:4: 'display' displayNTBlock
            {
            string_literal101=(Token)match(input,210,FOLLOW_210_in_displayNT1811); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_210.add(string_literal101);

            pushFollow(FOLLOW_displayNTBlock_in_displayNT1813);
            displayNTBlock102=displayNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_displayNTBlock.add(displayNTBlock102.getTree());


            // AST REWRITE
            // elements: displayNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 392:2: -> ^( DISPLAY_BLOCK[\"display\"] displayNTBlock )
            {
                // Rules.g:392:5: ^( DISPLAY_BLOCK[\"display\"] displayNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DISPLAY_BLOCK, "display"), root_1);

                adaptor.addChild(root_1, stream_displayNTBlock.nextTree());

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
    // $ANTLR end "displayNT"

    public static class displayNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "displayNTBlock"
    // Rules.g:395:1: displayNTBlock : LBRACE ( displayProperty )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"displayProperties\"] ( displayProperty )* ) ) ;
    public final RulesParser.displayNTBlock_return displayNTBlock() throws RecognitionException {
        RulesParser.displayNTBlock_return retval = new RulesParser.displayNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE103=null;
        Token RBRACE105=null;
        RulesParser.displayProperty_return displayProperty104 = null;


        RulesASTNode LBRACE103_tree=null;
        RulesASTNode RBRACE105_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_displayProperty=new RewriteRuleSubtreeStream(adaptor,"rule displayProperty");
        try {
            // Rules.g:396:2: ( LBRACE ( displayProperty )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"displayProperties\"] ( displayProperty )* ) ) )
            // Rules.g:396:4: LBRACE ( displayProperty )* RBRACE
            {
            LBRACE103=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_displayNTBlock1835); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE103);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.DISPLAY_SCOPE); 
            }
            // Rules.g:396:57: ( displayProperty )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==Identifier||LA27_0==NullLiteral||(LA27_0>=164 && LA27_0<=212)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // Rules.g:396:57: displayProperty
            	    {
            	    pushFollow(FOLLOW_displayProperty_in_displayNTBlock1839);
            	    displayProperty104=displayProperty();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_displayProperty.add(displayProperty104.getTree());

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            RBRACE105=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_displayNTBlock1842); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE105);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: displayProperty
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 397:2: -> ^( BLOCK ^( STATEMENTS[\"displayProperties\"] ( displayProperty )* ) )
            {
                // Rules.g:397:5: ^( BLOCK ^( STATEMENTS[\"displayProperties\"] ( displayProperty )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:397:13: ^( STATEMENTS[\"displayProperties\"] ( displayProperty )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "displayProperties"), root_2);

                // Rules.g:397:47: ( displayProperty )*
                while ( stream_displayProperty.hasNext() ) {
                    adaptor.addChild(root_2, stream_displayProperty.nextTree());

                }
                stream_displayProperty.reset();

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
    // $ANTLR end "displayNTBlock"

    public static class displayProperty_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "displayProperty"
    // Rules.g:400:1: displayProperty : name[TYPE_REF] ASSIGN StringLiteral SEMICOLON -> ^( DISPLAY_PROPERTY ^( NAME name ) ^( LITERAL StringLiteral ) ) ;
    public final RulesParser.displayProperty_return displayProperty() throws RecognitionException {
        RulesParser.displayProperty_return retval = new RulesParser.displayProperty_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token ASSIGN107=null;
        Token StringLiteral108=null;
        Token SEMICOLON109=null;
        Rules_BaseElement.name_return name106 = null;


        RulesASTNode ASSIGN107_tree=null;
        RulesASTNode StringLiteral108_tree=null;
        RulesASTNode SEMICOLON109_tree=null;
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        try {
            // Rules.g:401:2: ( name[TYPE_REF] ASSIGN StringLiteral SEMICOLON -> ^( DISPLAY_PROPERTY ^( NAME name ) ^( LITERAL StringLiteral ) ) )
            // Rules.g:401:4: name[TYPE_REF] ASSIGN StringLiteral SEMICOLON
            {
            pushFollow(FOLLOW_name_in_displayProperty1871);
            name106=name(TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(name106.getTree());
            ASSIGN107=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_displayProperty1874); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN107);

            StringLiteral108=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_displayProperty1876); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_StringLiteral.add(StringLiteral108);

            SEMICOLON109=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_displayProperty1878); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON109);



            // AST REWRITE
            // elements: name, StringLiteral
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 402:3: -> ^( DISPLAY_PROPERTY ^( NAME name ) ^( LITERAL StringLiteral ) )
            {
                // Rules.g:402:6: ^( DISPLAY_PROPERTY ^( NAME name ) ^( LITERAL StringLiteral ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DISPLAY_PROPERTY, "DISPLAY_PROPERTY"), root_1);

                // Rules.g:402:25: ^( NAME name )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                adaptor.addChild(root_2, stream_name.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:402:38: ^( LITERAL StringLiteral )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                adaptor.addChild(root_2, stream_StringLiteral.nextNode());

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
    // $ANTLR end "displayProperty"

    public static class variablesNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variablesNT"
    // Rules.g:405:1: variablesNT : 'declare' variablesNTBlock -> ^( DECLARE_BLOCK[\"declare\"] variablesNTBlock ) ;
    public final RulesParser.variablesNT_return variablesNT() throws RecognitionException {
        RulesParser.variablesNT_return retval = new RulesParser.variablesNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal110=null;
        RulesParser.variablesNTBlock_return variablesNTBlock111 = null;


        RulesASTNode string_literal110_tree=null;
        RewriteRuleTokenStream stream_232=new RewriteRuleTokenStream(adaptor,"token 232");
        RewriteRuleSubtreeStream stream_variablesNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule variablesNTBlock");
        try {
            // Rules.g:406:2: ( 'declare' variablesNTBlock -> ^( DECLARE_BLOCK[\"declare\"] variablesNTBlock ) )
            // Rules.g:406:4: 'declare' variablesNTBlock
            {
            string_literal110=(Token)match(input,232,FOLLOW_232_in_variablesNT1910); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_232.add(string_literal110);

            pushFollow(FOLLOW_variablesNTBlock_in_variablesNT1912);
            variablesNTBlock111=variablesNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_variablesNTBlock.add(variablesNTBlock111.getTree());


            // AST REWRITE
            // elements: variablesNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 407:2: -> ^( DECLARE_BLOCK[\"declare\"] variablesNTBlock )
            {
                // Rules.g:407:5: ^( DECLARE_BLOCK[\"declare\"] variablesNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DECLARE_BLOCK, "declare"), root_1);

                adaptor.addChild(root_1, stream_variablesNTBlock.nextTree());

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
    // $ANTLR end "variablesNT"

    public static class variablesNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variablesNTBlock"
    // Rules.g:410:1: variablesNTBlock : LBRACE ( templateDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( templateDeclarator )* ) ) ;
    public final RulesParser.variablesNTBlock_return variablesNTBlock() throws RecognitionException {
        RulesParser.variablesNTBlock_return retval = new RulesParser.variablesNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE112=null;
        Token RBRACE114=null;
        RulesParser.templateDeclarator_return templateDeclarator113 = null;


        RulesASTNode LBRACE112_tree=null;
        RulesASTNode RBRACE114_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_templateDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule templateDeclarator");
        try {
            // Rules.g:411:2: ( LBRACE ( templateDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( templateDeclarator )* ) ) )
            // Rules.g:411:4: LBRACE ( templateDeclarator )* RBRACE
            {
            LBRACE112=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_variablesNTBlock1933); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE112);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.DECLARE_SCOPE); 
            }
            // Rules.g:411:57: ( templateDeclarator )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==Identifier||LA28_0==NullLiteral||(LA28_0>=164 && LA28_0<=212)||(LA28_0>=228 && LA28_0<=231)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // Rules.g:411:57: templateDeclarator
            	    {
            	    pushFollow(FOLLOW_templateDeclarator_in_variablesNTBlock1937);
            	    templateDeclarator113=templateDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_templateDeclarator.add(templateDeclarator113.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            RBRACE114=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_variablesNTBlock1940); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE114);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: templateDeclarator
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 412:2: -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( templateDeclarator )* ) )
            {
                // Rules.g:412:5: ^( BLOCK ^( STATEMENTS[\"declarators\"] ( templateDeclarator )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:412:13: ^( STATEMENTS[\"declarators\"] ( templateDeclarator )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "declarators"), root_2);

                // Rules.g:412:41: ( templateDeclarator )*
                while ( stream_templateDeclarator.hasNext() ) {
                    adaptor.addChild(root_2, stream_templateDeclarator.nextTree());

                }
                stream_templateDeclarator.reset();

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
    // $ANTLR end "variablesNTBlock"

    public static class templateDeclarator_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "templateDeclarator"
    // Rules.g:415:1: templateDeclarator : t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? SEMICOLON -> ^( TEMPLATE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) ;
    public final RulesParser.templateDeclarator_return templateDeclarator() throws RecognitionException {
        RulesParser.templateDeclarator_return retval = new RulesParser.templateDeclarator_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token ASSIGN115=null;
        Token SEMICOLON118=null;
        Rules_BaseElement.nameOrPrimitiveType_return t = null;

        RulesParser.identifier_return id = null;

        Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral116 = null;

        Rules_BaseElement.expression_return expression117 = null;


        RulesASTNode ASSIGN115_tree=null;
        RulesASTNode SEMICOLON118_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        RewriteRuleSubtreeStream stream_nameOrPrimitiveType=new RewriteRuleSubtreeStream(adaptor,"rule nameOrPrimitiveType");
        RewriteRuleSubtreeStream stream_localInitializerArrayLiteral=new RewriteRuleSubtreeStream(adaptor,"rule localInitializerArrayLiteral");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Rules.g:416:2: (t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? SEMICOLON -> ^( TEMPLATE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? ) )
            // Rules.g:416:4: t= nameOrPrimitiveType id= identifier ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )? SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_nameOrPrimitiveType_in_templateDeclarator1973);
            t=nameOrPrimitiveType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_nameOrPrimitiveType.add(t.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            pushFollow(FOLLOW_identifier_in_templateDeclarator1979);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            if ( state.backtracking==0 ) {
               pushGlobalScopeName((RulesASTNode)id.getTree(), (RulesASTNode)t.getTree()); 
            }
            // Rules.g:418:2: ( ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression ) )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ASSIGN) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // Rules.g:418:3: ASSIGN ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    {
                    ASSIGN115=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_templateDeclarator1987); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN115);

                    // Rules.g:418:11: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )
                    int alt29=2;
                    alt29 = dfa29.predict(input);
                    switch (alt29) {
                        case 1 :
                            // Rules.g:418:12: ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral
                            {
                            pushFollow(FOLLOW_localInitializerArrayLiteral_in_templateDeclarator2003);
                            localInitializerArrayLiteral116=localInitializerArrayLiteral();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_localInitializerArrayLiteral.add(localInitializerArrayLiteral116.getTree());

                            }
                            break;
                        case 2 :
                            // Rules.g:419:4: expression
                            {
                            pushFollow(FOLLOW_expression_in_templateDeclarator2008);
                            expression117=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expression.add(expression117.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }

            SEMICOLON118=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_templateDeclarator2013); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON118);



            // AST REWRITE
            // elements: t, id, localInitializerArrayLiteral, expression
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
            // 420:2: -> ^( TEMPLATE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
            {
                // Rules.g:420:5: ^( TEMPLATE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ( ^( INITIALIZER localInitializerArrayLiteral ) )? ( ^( EXPRESSION expression ) )? )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TEMPLATE_DECLARATOR, "declarator"), root_1);

                // Rules.g:420:41: ^( TYPE[\"type\"] $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:421:3: ^( NAME[\"name\"] $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "name"), root_2);

                adaptor.addChild(root_2, stream_id.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:421:23: ( ^( INITIALIZER localInitializerArrayLiteral ) )?
                if ( stream_localInitializerArrayLiteral.hasNext() ) {
                    // Rules.g:421:23: ^( INITIALIZER localInitializerArrayLiteral )
                    {
                    RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                    root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(INITIALIZER, "INITIALIZER"), root_2);

                    adaptor.addChild(root_2, stream_localInitializerArrayLiteral.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_localInitializerArrayLiteral.reset();
                // Rules.g:422:3: ( ^( EXPRESSION expression ) )?
                if ( stream_expression.hasNext() ) {
                    // Rules.g:422:3: ^( EXPRESSION expression )
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
    // $ANTLR end "templateDeclarator"

    public static class attributeNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeNT"
    // Rules.g:425:1: attributeNT : 'attribute' attributeNTBlock -> ^( ATTRIBUTE_BLOCK[\"attribute\"] attributeNTBlock ) ;
    public final RulesParser.attributeNT_return attributeNT() throws RecognitionException {
        RulesParser.attributeNT_return retval = new RulesParser.attributeNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal119=null;
        RulesParser.attributeNTBlock_return attributeNTBlock120 = null;


        RulesASTNode string_literal119_tree=null;
        RewriteRuleTokenStream stream_240=new RewriteRuleTokenStream(adaptor,"token 240");
        RewriteRuleSubtreeStream stream_attributeNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule attributeNTBlock");
        try {
            // Rules.g:426:2: ( 'attribute' attributeNTBlock -> ^( ATTRIBUTE_BLOCK[\"attribute\"] attributeNTBlock ) )
            // Rules.g:426:4: 'attribute' attributeNTBlock
            {
            string_literal119=(Token)match(input,240,FOLLOW_240_in_attributeNT2066); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_240.add(string_literal119);

            pushFollow(FOLLOW_attributeNTBlock_in_attributeNT2068);
            attributeNTBlock120=attributeNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_attributeNTBlock.add(attributeNTBlock120.getTree());


            // AST REWRITE
            // elements: attributeNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 427:2: -> ^( ATTRIBUTE_BLOCK[\"attribute\"] attributeNTBlock )
            {
                // Rules.g:427:5: ^( ATTRIBUTE_BLOCK[\"attribute\"] attributeNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ATTRIBUTE_BLOCK, "attribute"), root_1);

                adaptor.addChild(root_1, stream_attributeNTBlock.nextTree());

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
    // $ANTLR end "attributeNT"

    public static class attributeNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeNTBlock"
    // Rules.g:430:1: attributeNTBlock : LBRACE ( attributeBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeBodyDeclaration )* ) ) ;
    public final RulesParser.attributeNTBlock_return attributeNTBlock() throws RecognitionException {
        RulesParser.attributeNTBlock_return retval = new RulesParser.attributeNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE121=null;
        Token RBRACE123=null;
        RulesParser.attributeBodyDeclaration_return attributeBodyDeclaration122 = null;


        RulesASTNode LBRACE121_tree=null;
        RulesASTNode RBRACE123_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_attributeBodyDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule attributeBodyDeclaration");
        try {
            // Rules.g:430:18: ( LBRACE ( attributeBodyDeclaration )* RBRACE -> ^( BLOCK ^( STATEMENTS ( attributeBodyDeclaration )* ) ) )
            // Rules.g:430:20: LBRACE ( attributeBodyDeclaration )* RBRACE
            {
            LBRACE121=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_attributeNTBlock2089); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE121);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.ATTRIBUTE_SCOPE); 
            }
            // Rules.g:431:6: ( attributeBodyDeclaration )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==165||(LA31_0>=170 && LA31_0<=171)||(LA31_0>=241 && LA31_0<=245)) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // Rules.g:431:6: attributeBodyDeclaration
            	    {
            	    pushFollow(FOLLOW_attributeBodyDeclaration_in_attributeNTBlock2098);
            	    attributeBodyDeclaration122=attributeBodyDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_attributeBodyDeclaration.add(attributeBodyDeclaration122.getTree());

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            RBRACE123=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_attributeNTBlock2101); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE123);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: attributeBodyDeclaration
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 432:2: -> ^( BLOCK ^( STATEMENTS ( attributeBodyDeclaration )* ) )
            {
                // Rules.g:432:5: ^( BLOCK ^( STATEMENTS ( attributeBodyDeclaration )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:432:13: ^( STATEMENTS ( attributeBodyDeclaration )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:432:26: ( attributeBodyDeclaration )*
                while ( stream_attributeBodyDeclaration.hasNext() ) {
                    adaptor.addChild(root_2, stream_attributeBodyDeclaration.nextTree());

                }
                stream_attributeBodyDeclaration.reset();

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
    // $ANTLR end "attributeNTBlock"

    public static class attributeBodyDeclaration_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attributeBodyDeclaration"
    // Rules.g:435:1: attributeBodyDeclaration : ( 'priority' ASSIGN p= integerLiteral SEMICOLON -> ^( PRIORITY_STATEMENT ^( PRIORITY $p) ) | 'forwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'backwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'requeue' ASSIGN booleanLiteral SEMICOLON -> ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) ) | '$lastmod' ASSIGN StringLiteral SEMICOLON -> ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) ) | 'validity' ASSIGN v= validityType SEMICOLON -> ^( VALIDITY_STATEMENT ^( VALIDITY $v) ) | 'alias' ASSIGN StringLiteral SEMICOLON -> ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) ) | 'rank' ASSIGN r= name[BaseRulesParser.TYPE_REF] SEMICOLON -> ^( RANK_STATEMENT ^( NAME $r) ) ) ;
    public final RulesParser.attributeBodyDeclaration_return attributeBodyDeclaration() throws RecognitionException {
        RulesParser.attributeBodyDeclaration_return retval = new RulesParser.attributeBodyDeclaration_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal124=null;
        Token ASSIGN125=null;
        Token SEMICOLON126=null;
        Token string_literal127=null;
        Token ASSIGN128=null;
        Token SEMICOLON130=null;
        Token string_literal131=null;
        Token ASSIGN132=null;
        Token SEMICOLON134=null;
        Token string_literal135=null;
        Token ASSIGN136=null;
        Token SEMICOLON138=null;
        Token string_literal139=null;
        Token ASSIGN140=null;
        Token StringLiteral141=null;
        Token SEMICOLON142=null;
        Token string_literal143=null;
        Token ASSIGN144=null;
        Token SEMICOLON145=null;
        Token string_literal146=null;
        Token ASSIGN147=null;
        Token StringLiteral148=null;
        Token SEMICOLON149=null;
        Token string_literal150=null;
        Token ASSIGN151=null;
        Token SEMICOLON152=null;
        Rules_BaseElement.integerLiteral_return p = null;

        RulesParser.validityType_return v = null;

        Rules_BaseElement.name_return r = null;

        Rules_BaseElement.booleanLiteral_return booleanLiteral129 = null;

        Rules_BaseElement.booleanLiteral_return booleanLiteral133 = null;

        Rules_BaseElement.booleanLiteral_return booleanLiteral137 = null;


        RulesASTNode string_literal124_tree=null;
        RulesASTNode ASSIGN125_tree=null;
        RulesASTNode SEMICOLON126_tree=null;
        RulesASTNode string_literal127_tree=null;
        RulesASTNode ASSIGN128_tree=null;
        RulesASTNode SEMICOLON130_tree=null;
        RulesASTNode string_literal131_tree=null;
        RulesASTNode ASSIGN132_tree=null;
        RulesASTNode SEMICOLON134_tree=null;
        RulesASTNode string_literal135_tree=null;
        RulesASTNode ASSIGN136_tree=null;
        RulesASTNode SEMICOLON138_tree=null;
        RulesASTNode string_literal139_tree=null;
        RulesASTNode ASSIGN140_tree=null;
        RulesASTNode StringLiteral141_tree=null;
        RulesASTNode SEMICOLON142_tree=null;
        RulesASTNode string_literal143_tree=null;
        RulesASTNode ASSIGN144_tree=null;
        RulesASTNode SEMICOLON145_tree=null;
        RulesASTNode string_literal146_tree=null;
        RulesASTNode ASSIGN147_tree=null;
        RulesASTNode StringLiteral148_tree=null;
        RulesASTNode SEMICOLON149_tree=null;
        RulesASTNode string_literal150_tree=null;
        RulesASTNode ASSIGN151_tree=null;
        RulesASTNode SEMICOLON152_tree=null;
        RewriteRuleTokenStream stream_170=new RewriteRuleTokenStream(adaptor,"token 170");
        RewriteRuleTokenStream stream_StringLiteral=new RewriteRuleTokenStream(adaptor,"token StringLiteral");
        RewriteRuleTokenStream stream_171=new RewriteRuleTokenStream(adaptor,"token 171");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleTokenStream stream_241=new RewriteRuleTokenStream(adaptor,"token 241");
        RewriteRuleTokenStream stream_245=new RewriteRuleTokenStream(adaptor,"token 245");
        RewriteRuleTokenStream stream_244=new RewriteRuleTokenStream(adaptor,"token 244");
        RewriteRuleTokenStream stream_243=new RewriteRuleTokenStream(adaptor,"token 243");
        RewriteRuleTokenStream stream_242=new RewriteRuleTokenStream(adaptor,"token 242");
        RewriteRuleTokenStream stream_165=new RewriteRuleTokenStream(adaptor,"token 165");
        RewriteRuleTokenStream stream_ASSIGN=new RewriteRuleTokenStream(adaptor,"token ASSIGN");
        RewriteRuleSubtreeStream stream_validityType=new RewriteRuleSubtreeStream(adaptor,"rule validityType");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_booleanLiteral=new RewriteRuleSubtreeStream(adaptor,"rule booleanLiteral");
        RewriteRuleSubtreeStream stream_integerLiteral=new RewriteRuleSubtreeStream(adaptor,"rule integerLiteral");
        try {
            // Rules.g:436:2: ( ( 'priority' ASSIGN p= integerLiteral SEMICOLON -> ^( PRIORITY_STATEMENT ^( PRIORITY $p) ) | 'forwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'backwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'requeue' ASSIGN booleanLiteral SEMICOLON -> ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) ) | '$lastmod' ASSIGN StringLiteral SEMICOLON -> ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) ) | 'validity' ASSIGN v= validityType SEMICOLON -> ^( VALIDITY_STATEMENT ^( VALIDITY $v) ) | 'alias' ASSIGN StringLiteral SEMICOLON -> ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) ) | 'rank' ASSIGN r= name[BaseRulesParser.TYPE_REF] SEMICOLON -> ^( RANK_STATEMENT ^( NAME $r) ) ) )
            // Rules.g:436:4: ( 'priority' ASSIGN p= integerLiteral SEMICOLON -> ^( PRIORITY_STATEMENT ^( PRIORITY $p) ) | 'forwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'backwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'requeue' ASSIGN booleanLiteral SEMICOLON -> ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) ) | '$lastmod' ASSIGN StringLiteral SEMICOLON -> ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) ) | 'validity' ASSIGN v= validityType SEMICOLON -> ^( VALIDITY_STATEMENT ^( VALIDITY $v) ) | 'alias' ASSIGN StringLiteral SEMICOLON -> ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) ) | 'rank' ASSIGN r= name[BaseRulesParser.TYPE_REF] SEMICOLON -> ^( RANK_STATEMENT ^( NAME $r) ) )
            {
            // Rules.g:436:4: ( 'priority' ASSIGN p= integerLiteral SEMICOLON -> ^( PRIORITY_STATEMENT ^( PRIORITY $p) ) | 'forwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'backwardChain' ASSIGN booleanLiteral SEMICOLON -> ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) ) | 'requeue' ASSIGN booleanLiteral SEMICOLON -> ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) ) | '$lastmod' ASSIGN StringLiteral SEMICOLON -> ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) ) | 'validity' ASSIGN v= validityType SEMICOLON -> ^( VALIDITY_STATEMENT ^( VALIDITY $v) ) | 'alias' ASSIGN StringLiteral SEMICOLON -> ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) ) | 'rank' ASSIGN r= name[BaseRulesParser.TYPE_REF] SEMICOLON -> ^( RANK_STATEMENT ^( NAME $r) ) )
            int alt32=8;
            switch ( input.LA(1) ) {
            case 241:
                {
                alt32=1;
                }
                break;
            case 242:
                {
                alt32=2;
                }
                break;
            case 243:
                {
                alt32=3;
                }
                break;
            case 244:
                {
                alt32=4;
                }
                break;
            case 245:
                {
                alt32=5;
                }
                break;
            case 165:
                {
                alt32=6;
                }
                break;
            case 170:
                {
                alt32=7;
                }
                break;
            case 171:
                {
                alt32=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // Rules.g:436:5: 'priority' ASSIGN p= integerLiteral SEMICOLON
                    {
                    string_literal124=(Token)match(input,241,FOLLOW_241_in_attributeBodyDeclaration2131); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_241.add(string_literal124);

                    ASSIGN125=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2133); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN125);

                    pushFollow(FOLLOW_integerLiteral_in_attributeBodyDeclaration2137);
                    p=integerLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_integerLiteral.add(p.getTree());
                    SEMICOLON126=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2139); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON126);



                    // AST REWRITE
                    // elements: p
                    // token labels: 
                    // rule labels: retval, p
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_p=new RewriteRuleSubtreeStream(adaptor,"rule p",p!=null?p.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 437:3: -> ^( PRIORITY_STATEMENT ^( PRIORITY $p) )
                    {
                        // Rules.g:437:6: ^( PRIORITY_STATEMENT ^( PRIORITY $p) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIORITY_STATEMENT, "PRIORITY_STATEMENT"), root_1);

                        // Rules.g:437:27: ^( PRIORITY $p)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(PRIORITY, "PRIORITY"), root_2);

                        adaptor.addChild(root_2, stream_p.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Rules.g:438:4: 'forwardChain' ASSIGN booleanLiteral SEMICOLON
                    {
                    string_literal127=(Token)match(input,242,FOLLOW_242_in_attributeBodyDeclaration2159); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_242.add(string_literal127);

                    ASSIGN128=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2161); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN128);

                    pushFollow(FOLLOW_booleanLiteral_in_attributeBodyDeclaration2163);
                    booleanLiteral129=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_booleanLiteral.add(booleanLiteral129.getTree());
                    SEMICOLON130=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2165); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON130);



                    // AST REWRITE
                    // elements: booleanLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 439:3: -> ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) )
                    {
                        // Rules.g:439:6: ^( FORWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(FORWARD_CHAIN_STATEMENT, "FORWARD_CHAIN_STATEMENT"), root_1);

                        // Rules.g:439:32: ^( LITERAL booleanLiteral )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                        adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Rules.g:440:4: 'backwardChain' ASSIGN booleanLiteral SEMICOLON
                    {
                    string_literal131=(Token)match(input,243,FOLLOW_243_in_attributeBodyDeclaration2185); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_243.add(string_literal131);

                    ASSIGN132=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2187); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN132);

                    pushFollow(FOLLOW_booleanLiteral_in_attributeBodyDeclaration2189);
                    booleanLiteral133=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_booleanLiteral.add(booleanLiteral133.getTree());
                    SEMICOLON134=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2191); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON134);



                    // AST REWRITE
                    // elements: booleanLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 441:3: -> ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) )
                    {
                        // Rules.g:441:6: ^( BACKWARD_CHAIN_STATEMENT ^( LITERAL booleanLiteral ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BACKWARD_CHAIN_STATEMENT, "BACKWARD_CHAIN_STATEMENT"), root_1);

                        // Rules.g:441:33: ^( LITERAL booleanLiteral )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                        adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // Rules.g:442:4: 'requeue' ASSIGN booleanLiteral SEMICOLON
                    {
                    string_literal135=(Token)match(input,244,FOLLOW_244_in_attributeBodyDeclaration2211); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_244.add(string_literal135);

                    ASSIGN136=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2213); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN136);

                    pushFollow(FOLLOW_booleanLiteral_in_attributeBodyDeclaration2215);
                    booleanLiteral137=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_booleanLiteral.add(booleanLiteral137.getTree());
                    SEMICOLON138=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2217); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON138);



                    // AST REWRITE
                    // elements: booleanLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 443:3: -> ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) )
                    {
                        // Rules.g:443:6: ^( REQUEUE_STATEMENT ^( LITERAL booleanLiteral ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(REQUEUE_STATEMENT, "REQUEUE_STATEMENT"), root_1);

                        // Rules.g:443:26: ^( LITERAL booleanLiteral )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                        adaptor.addChild(root_2, stream_booleanLiteral.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // Rules.g:444:4: '$lastmod' ASSIGN StringLiteral SEMICOLON
                    {
                    string_literal139=(Token)match(input,245,FOLLOW_245_in_attributeBodyDeclaration2236); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_245.add(string_literal139);

                    ASSIGN140=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2238); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN140);

                    StringLiteral141=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_attributeBodyDeclaration2240); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_StringLiteral.add(StringLiteral141);

                    SEMICOLON142=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2242); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON142);



                    // AST REWRITE
                    // elements: StringLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 445:3: -> ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) )
                    {
                        // Rules.g:445:6: ^( LASTMOD_STATEMENT ^( LITERAL StringLiteral ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LASTMOD_STATEMENT, "LASTMOD_STATEMENT"), root_1);

                        // Rules.g:445:26: ^( LITERAL StringLiteral )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                        adaptor.addChild(root_2, stream_StringLiteral.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // Rules.g:446:4: 'validity' ASSIGN v= validityType SEMICOLON
                    {
                    string_literal143=(Token)match(input,165,FOLLOW_165_in_attributeBodyDeclaration2262); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_165.add(string_literal143);

                    ASSIGN144=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2264); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN144);

                    pushFollow(FOLLOW_validityType_in_attributeBodyDeclaration2268);
                    v=validityType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_validityType.add(v.getTree());
                    SEMICOLON145=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2270); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON145);



                    // AST REWRITE
                    // elements: v
                    // token labels: 
                    // rule labels: v, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_v=new RewriteRuleSubtreeStream(adaptor,"rule v",v!=null?v.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 447:3: -> ^( VALIDITY_STATEMENT ^( VALIDITY $v) )
                    {
                        // Rules.g:447:6: ^( VALIDITY_STATEMENT ^( VALIDITY $v) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(VALIDITY_STATEMENT, "VALIDITY_STATEMENT"), root_1);

                        // Rules.g:447:27: ^( VALIDITY $v)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(VALIDITY, "VALIDITY"), root_2);

                        adaptor.addChild(root_2, stream_v.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 7 :
                    // Rules.g:448:4: 'alias' ASSIGN StringLiteral SEMICOLON
                    {
                    string_literal146=(Token)match(input,170,FOLLOW_170_in_attributeBodyDeclaration2290); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_170.add(string_literal146);

                    ASSIGN147=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2292); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN147);

                    StringLiteral148=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_attributeBodyDeclaration2294); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_StringLiteral.add(StringLiteral148);

                    SEMICOLON149=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2296); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON149);



                    // AST REWRITE
                    // elements: StringLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 449:3: -> ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) )
                    {
                        // Rules.g:449:6: ^( ALIAS_STATEMENT ^( LITERAL StringLiteral ) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(ALIAS_STATEMENT, "ALIAS_STATEMENT"), root_1);

                        // Rules.g:449:24: ^( LITERAL StringLiteral )
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERAL, "LITERAL"), root_2);

                        adaptor.addChild(root_2, stream_StringLiteral.nextNode());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 8 :
                    // Rules.g:450:4: 'rank' ASSIGN r= name[BaseRulesParser.TYPE_REF] SEMICOLON
                    {
                    string_literal150=(Token)match(input,171,FOLLOW_171_in_attributeBodyDeclaration2315); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_171.add(string_literal150);

                    ASSIGN151=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_attributeBodyDeclaration2317); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ASSIGN.add(ASSIGN151);

                    if ( state.backtracking==0 ) {
                       setTypeReference(true); 
                    }
                    pushFollow(FOLLOW_name_in_attributeBodyDeclaration2323);
                    r=name(BaseRulesParser.TYPE_REF);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_name.add(r.getTree());
                    if ( state.backtracking==0 ) {
                       setTypeReference(false); 
                    }
                    SEMICOLON152=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_attributeBodyDeclaration2328); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON152);



                    // AST REWRITE
                    // elements: r
                    // token labels: 
                    // rule labels: retval, r
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"rule r",r!=null?r.tree:null);

                    root_0 = (RulesASTNode)adaptor.nil();
                    // 451:3: -> ^( RANK_STATEMENT ^( NAME $r) )
                    {
                        // Rules.g:451:6: ^( RANK_STATEMENT ^( NAME $r) )
                        {
                        RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                        root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(RANK_STATEMENT, "RANK_STATEMENT"), root_1);

                        // Rules.g:451:23: ^( NAME $r)
                        {
                        RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                        root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                        adaptor.addChild(root_2, stream_r.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

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
    // $ANTLR end "attributeBodyDeclaration"

    public static class validityType_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "validityType"
    // Rules.g:454:1: validityType : ( 'ACTION' | 'CONDITION' | 'QUERY' );
    public final RulesParser.validityType_return validityType() throws RecognitionException {
        RulesParser.validityType_return retval = new RulesParser.validityType_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set153=null;

        RulesASTNode set153_tree=null;

        try {
            // Rules.g:455:2: ( 'ACTION' | 'CONDITION' | 'QUERY' )
            // Rules.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set153=(Token)input.LT(1);
            if ( (input.LA(1)>=246 && input.LA(1)<=248) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set153));
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
    // $ANTLR end "validityType"

    public static class declareNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareNT"
    // Rules.g:458:1: declareNT : 'declare' declareNTBlock -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock ) ;
    public final RulesParser.declareNT_return declareNT() throws RecognitionException {
        RulesParser.declareNT_return retval = new RulesParser.declareNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal154=null;
        RulesParser.declareNTBlock_return declareNTBlock155 = null;


        RulesASTNode string_literal154_tree=null;
        RewriteRuleTokenStream stream_232=new RewriteRuleTokenStream(adaptor,"token 232");
        RewriteRuleSubtreeStream stream_declareNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule declareNTBlock");
        try {
            // Rules.g:459:2: ( 'declare' declareNTBlock -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock ) )
            // Rules.g:459:4: 'declare' declareNTBlock
            {
            string_literal154=(Token)match(input,232,FOLLOW_232_in_declareNT2375); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_232.add(string_literal154);

            pushFollow(FOLLOW_declareNTBlock_in_declareNT2377);
            declareNTBlock155=declareNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_declareNTBlock.add(declareNTBlock155.getTree());


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

            root_0 = (RulesASTNode)adaptor.nil();
            // 460:2: -> ^( DECLARE_BLOCK[\"declare\"] declareNTBlock )
            {
                // Rules.g:460:5: ^( DECLARE_BLOCK[\"declare\"] declareNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DECLARE_BLOCK, "declare"), root_1);

                adaptor.addChild(root_1, stream_declareNTBlock.nextTree());

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
    // $ANTLR end "declareNT"

    public static class declareNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declareNTBlock"
    // Rules.g:463:1: declareNTBlock : LBRACE ( declarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) ;
    public final RulesParser.declareNTBlock_return declareNTBlock() throws RecognitionException {
        RulesParser.declareNTBlock_return retval = new RulesParser.declareNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE156=null;
        Token RBRACE158=null;
        RulesParser.declarator_return declarator157 = null;


        RulesASTNode LBRACE156_tree=null;
        RulesASTNode RBRACE158_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_declarator=new RewriteRuleSubtreeStream(adaptor,"rule declarator");
        try {
            // Rules.g:463:16: ( LBRACE ( declarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) ) )
            // Rules.g:463:18: LBRACE ( declarator )* RBRACE
            {
            LBRACE156=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_declareNTBlock2398); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE156);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.DECLARE_SCOPE); 
            }
            // Rules.g:463:71: ( declarator )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==Identifier||LA33_0==NullLiteral||(LA33_0>=164 && LA33_0<=212)) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // Rules.g:463:71: declarator
            	    {
            	    pushFollow(FOLLOW_declarator_in_declareNTBlock2402);
            	    declarator157=declarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_declarator.add(declarator157.getTree());

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            RBRACE158=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_declareNTBlock2405); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE158);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 464:2: -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
            {
                // Rules.g:464:5: ^( BLOCK ^( STATEMENTS[\"declarators\"] ( declarator )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:464:13: ^( STATEMENTS[\"declarators\"] ( declarator )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "declarators"), root_2);

                // Rules.g:464:41: ( declarator )*
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
    // $ANTLR end "declareNTBlock"

    public static class scopeNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeNT"
    // Rules.g:467:1: scopeNT : 'scope' scopeNTBlock -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock ) ;
    public final RulesParser.scopeNT_return scopeNT() throws RecognitionException {
        RulesParser.scopeNT_return retval = new RulesParser.scopeNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal159=null;
        RulesParser.scopeNTBlock_return scopeNTBlock160 = null;


        RulesASTNode string_literal159_tree=null;
        RewriteRuleTokenStream stream_233=new RewriteRuleTokenStream(adaptor,"token 233");
        RewriteRuleSubtreeStream stream_scopeNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule scopeNTBlock");
        try {
            // Rules.g:468:2: ( 'scope' scopeNTBlock -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock ) )
            // Rules.g:468:4: 'scope' scopeNTBlock
            {
            string_literal159=(Token)match(input,233,FOLLOW_233_in_scopeNT2434); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_233.add(string_literal159);

            pushFollow(FOLLOW_scopeNTBlock_in_scopeNT2436);
            scopeNTBlock160=scopeNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_scopeNTBlock.add(scopeNTBlock160.getTree());


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

            root_0 = (RulesASTNode)adaptor.nil();
            // 469:2: -> ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock )
            {
                // Rules.g:469:5: ^( SCOPE_BLOCK[\"scope\"] scopeNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SCOPE_BLOCK, "scope"), root_1);

                adaptor.addChild(root_1, stream_scopeNTBlock.nextTree());

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
    // $ANTLR end "scopeNT"

    public static class scopeNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeNTBlock"
    // Rules.g:472:1: scopeNTBlock : LBRACE ( scopeDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) ;
    public final RulesParser.scopeNTBlock_return scopeNTBlock() throws RecognitionException {
        RulesParser.scopeNTBlock_return retval = new RulesParser.scopeNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE161=null;
        Token RBRACE163=null;
        RulesParser.scopeDeclarator_return scopeDeclarator162 = null;


        RulesASTNode LBRACE161_tree=null;
        RulesASTNode RBRACE163_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_scopeDeclarator=new RewriteRuleSubtreeStream(adaptor,"rule scopeDeclarator");
        try {
            // Rules.g:472:14: ( LBRACE ( scopeDeclarator )* RBRACE -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) ) )
            // Rules.g:472:16: LBRACE ( scopeDeclarator )* RBRACE
            {
            LBRACE161=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_scopeNTBlock2456); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE161);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.SCOPE_SCOPE); 
            }
            // Rules.g:472:67: ( scopeDeclarator )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==Identifier||LA34_0==NullLiteral||(LA34_0>=164 && LA34_0<=212)||(LA34_0>=228 && LA34_0<=231)) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // Rules.g:472:67: scopeDeclarator
            	    {
            	    pushFollow(FOLLOW_scopeDeclarator_in_scopeNTBlock2460);
            	    scopeDeclarator162=scopeDeclarator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_scopeDeclarator.add(scopeDeclarator162.getTree());

            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            RBRACE163=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_scopeNTBlock2463); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE163);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 473:2: -> ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
            {
                // Rules.g:473:5: ^( BLOCK ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:473:13: ^( STATEMENTS[\"declarators\"] ( scopeDeclarator )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "declarators"), root_2);

                // Rules.g:473:41: ( scopeDeclarator )*
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
    // $ANTLR end "scopeNTBlock"

    public static class scopeDeclarator_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeDeclarator"
    // Rules.g:476:1: scopeDeclarator : t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ) ;
    public final RulesParser.scopeDeclarator_return scopeDeclarator() throws RecognitionException {
        RulesParser.scopeDeclarator_return retval = new RulesParser.scopeDeclarator_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token char_literal164=null;
        Token char_literal165=null;
        Token SEMICOLON166=null;
        Rules_BaseElement.nameOrPrimitiveType_return t = null;

        RulesParser.identifier_return id = null;


        RulesASTNode char_literal164_tree=null;
        RulesASTNode char_literal165_tree=null;
        RulesASTNode SEMICOLON166_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_nameOrPrimitiveType=new RewriteRuleSubtreeStream(adaptor,"rule nameOrPrimitiveType");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Rules.g:477:2: (t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) ) )
            // Rules.g:477:4: t= nameOrPrimitiveType ( '[' ']' )? id= identifier SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_nameOrPrimitiveType_in_scopeDeclarator2497);
            t=nameOrPrimitiveType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_nameOrPrimitiveType.add(t.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            // Rules.g:478:3: ( '[' ']' )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==LBRACK) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Rules.g:478:5: '[' ']'
                    {
                    char_literal164=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_scopeDeclarator2506); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACK.add(char_literal164);

                    char_literal165=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_scopeDeclarator2508); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACK.add(char_literal165);

                    if ( state.backtracking==0 ) {
                       markAsArray((RulesASTNode) t.getTree()); 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_identifier_in_scopeDeclarator2517);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            SEMICOLON166=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_scopeDeclarator2519); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON166);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 480:2: -> ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) )
            {
                // Rules.g:480:5: ^( SCOPE_DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $t) ^( NAME[\"name\"] $id) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(SCOPE_DECLARATOR, "declarator"), root_1);

                // Rules.g:480:38: ^( TYPE[\"type\"] $t)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_t.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:481:3: ^( NAME[\"name\"] $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "name"), root_2);

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
    // $ANTLR end "scopeDeclarator"

    public static class declarator_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declarator"
    // Rules.g:484:1: declarator : nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) ) ;
    public final RulesParser.declarator_return declarator() throws RecognitionException {
        RulesParser.declarator_return retval = new RulesParser.declarator_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token SEMICOLON167=null;
        Rules_BaseElement.name_return nm = null;

        RulesParser.identifier_return id = null;


        RulesASTNode SEMICOLON167_tree=null;
        RewriteRuleTokenStream stream_SEMICOLON=new RewriteRuleTokenStream(adaptor,"token SEMICOLON");
        RewriteRuleSubtreeStream stream_name=new RewriteRuleSubtreeStream(adaptor,"rule name");
        RewriteRuleSubtreeStream stream_identifier=new RewriteRuleSubtreeStream(adaptor,"rule identifier");
        try {
            // Rules.g:485:2: (nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) ) )
            // Rules.g:485:4: nm= name[BaseRulesParser.TYPE_REF] id= identifier SEMICOLON
            {
            if ( state.backtracking==0 ) {
               setTypeReference(true); 
            }
            pushFollow(FOLLOW_name_in_declarator2565);
            nm=name(BaseRulesParser.TYPE_REF);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_name.add(nm.getTree());
            if ( state.backtracking==0 ) {
               setTypeReference(false); 
            }
            pushFollow(FOLLOW_identifier_in_declarator2572);
            id=identifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_identifier.add(id.getTree());
            SEMICOLON167=(Token)match(input,SEMICOLON,FOLLOW_SEMICOLON_in_declarator2574); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SEMICOLON.add(SEMICOLON167);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 487:2: -> ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) )
            {
                // Rules.g:487:5: ^( DECLARATOR[\"declarator\"] ^( TYPE[\"type\"] $nm) ^( NAME[\"name\"] $id) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DECLARATOR, "declarator"), root_1);

                // Rules.g:487:32: ^( TYPE[\"type\"] $nm)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(TYPE, "type"), root_2);

                adaptor.addChild(root_2, stream_nm.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Rules.g:488:3: ^( NAME[\"name\"] $id)
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "name"), root_2);

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
    // $ANTLR end "declarator"

    public static class whenNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whenNT"
    // Rules.g:491:1: whenNT : 'when' whenNTBlock -> ^( WHEN_BLOCK[\"when\"] whenNTBlock ) ;
    public final RulesParser.whenNT_return whenNT() throws RecognitionException {
        RulesParser.whenNT_return retval = new RulesParser.whenNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal168=null;
        RulesParser.whenNTBlock_return whenNTBlock169 = null;


        RulesASTNode string_literal168_tree=null;
        RewriteRuleTokenStream stream_239=new RewriteRuleTokenStream(adaptor,"token 239");
        RewriteRuleSubtreeStream stream_whenNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule whenNTBlock");
        try {
            // Rules.g:491:8: ( 'when' whenNTBlock -> ^( WHEN_BLOCK[\"when\"] whenNTBlock ) )
            // Rules.g:491:10: 'when' whenNTBlock
            {
            string_literal168=(Token)match(input,239,FOLLOW_239_in_whenNT2615); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_239.add(string_literal168);

            pushFollow(FOLLOW_whenNTBlock_in_whenNT2617);
            whenNTBlock169=whenNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_whenNTBlock.add(whenNTBlock169.getTree());


            // AST REWRITE
            // elements: whenNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 492:2: -> ^( WHEN_BLOCK[\"when\"] whenNTBlock )
            {
                // Rules.g:492:5: ^( WHEN_BLOCK[\"when\"] whenNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(WHEN_BLOCK, "when"), root_1);

                adaptor.addChild(root_1, stream_whenNTBlock.nextTree());

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
    // $ANTLR end "whenNT"

    public static class whenNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whenNTBlock"
    // Rules.g:495:1: whenNTBlock : LBRACE predicateStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( predicateStatements )? ) ) ;
    public final RulesParser.whenNTBlock_return whenNTBlock() throws RecognitionException {
        RulesParser.whenNTBlock_return retval = new RulesParser.whenNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE170=null;
        Token RBRACE172=null;
        Rules_BaseElement.predicateStatements_return predicateStatements171 = null;


        RulesASTNode LBRACE170_tree=null;
        RulesASTNode RBRACE172_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_predicateStatements=new RewriteRuleSubtreeStream(adaptor,"rule predicateStatements");
        try {
            // Rules.g:495:13: ( LBRACE predicateStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( predicateStatements )? ) ) )
            // Rules.g:495:15: LBRACE predicateStatements RBRACE
            {
            LBRACE170=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_whenNTBlock2637); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE170);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.WHEN_SCOPE); 
            }
            pushFollow(FOLLOW_predicateStatements_in_whenNTBlock2641);
            predicateStatements171=predicateStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_predicateStatements.add(predicateStatements171.getTree());
            RBRACE172=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_whenNTBlock2645); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE172);

            if ( state.backtracking==0 ) {
               popScope(); 
            }


            // AST REWRITE
            // elements: predicateStatements
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 498:2: -> ^( BLOCK ^( STATEMENTS ( predicateStatements )? ) )
            {
                // Rules.g:498:5: ^( BLOCK ^( STATEMENTS ( predicateStatements )? ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:498:13: ^( STATEMENTS ( predicateStatements )? )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:498:26: ( predicateStatements )?
                if ( stream_predicateStatements.hasNext() ) {
                    adaptor.addChild(root_2, stream_predicateStatements.nextTree());

                }
                stream_predicateStatements.reset();

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
    // $ANTLR end "whenNTBlock"

    public static class thenNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenNT"
    // Rules.g:501:1: thenNT : 'then' thenNTBlock -> ^( THEN_BLOCK[\"then\"] thenNTBlock ) ;
    public final RulesParser.thenNT_return thenNT() throws RecognitionException {
        RulesParser.thenNT_return retval = new RulesParser.thenNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal173=null;
        RulesParser.thenNTBlock_return thenNTBlock174 = null;


        RulesASTNode string_literal173_tree=null;
        RewriteRuleTokenStream stream_234=new RewriteRuleTokenStream(adaptor,"token 234");
        RewriteRuleSubtreeStream stream_thenNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule thenNTBlock");
        try {
            // Rules.g:504:8: ( 'then' thenNTBlock -> ^( THEN_BLOCK[\"then\"] thenNTBlock ) )
            // Rules.g:504:10: 'then' thenNTBlock
            {
            string_literal173=(Token)match(input,234,FOLLOW_234_in_thenNT2677); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_234.add(string_literal173);

            pushFollow(FOLLOW_thenNTBlock_in_thenNT2679);
            thenNTBlock174=thenNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenNTBlock.add(thenNTBlock174.getTree());


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

            root_0 = (RulesASTNode)adaptor.nil();
            // 505:2: -> ^( THEN_BLOCK[\"then\"] thenNTBlock )
            {
                // Rules.g:505:5: ^( THEN_BLOCK[\"then\"] thenNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(THEN_BLOCK, "then"), root_1);

                adaptor.addChild(root_1, stream_thenNTBlock.nextTree());

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
    // $ANTLR end "thenNT"

    public static class thenNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenNTBlock"
    // Rules.g:508:1: thenNTBlock : LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) ;
    public final RulesParser.thenNTBlock_return thenNTBlock() throws RecognitionException {
        RulesParser.thenNTBlock_return retval = new RulesParser.thenNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE175=null;
        Token RBRACE177=null;
        RulesParser.thenStatements_return thenStatements176 = null;


        RulesASTNode LBRACE175_tree=null;
        RulesASTNode RBRACE177_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_thenStatements=new RewriteRuleSubtreeStream(adaptor,"rule thenStatements");
        try {
            // Rules.g:508:13: ( LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) )
            // Rules.g:508:15: LBRACE thenStatements RBRACE
            {
            LBRACE175=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_thenNTBlock2700); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE175);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.THEN_SCOPE); 
            }
            pushFollow(FOLLOW_thenStatements_in_thenNTBlock2704);
            thenStatements176=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenStatements.add(thenStatements176.getTree());
            RBRACE177=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_thenNTBlock2709); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE177);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 511:2: -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
            {
                // Rules.g:511:5: ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:511:13: ^( STATEMENTS ( thenStatements )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:511:26: ( thenStatements )*
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
    // $ANTLR end "thenNTBlock"

    public static class bodyNT_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bodyNT"
    // Rules.g:514:1: bodyNT : 'body' bodyNTBlock -> ^( BODY_BLOCK[\"body\"] bodyNTBlock ) ;
    public final RulesParser.bodyNT_return bodyNT() throws RecognitionException {
        RulesParser.bodyNT_return retval = new RulesParser.bodyNT_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal178=null;
        RulesParser.bodyNTBlock_return bodyNTBlock179 = null;


        RulesASTNode string_literal178_tree=null;
        RewriteRuleTokenStream stream_164=new RewriteRuleTokenStream(adaptor,"token 164");
        RewriteRuleSubtreeStream stream_bodyNTBlock=new RewriteRuleSubtreeStream(adaptor,"rule bodyNTBlock");
        try {
            // Rules.g:514:8: ( 'body' bodyNTBlock -> ^( BODY_BLOCK[\"body\"] bodyNTBlock ) )
            // Rules.g:514:10: 'body' bodyNTBlock
            {
            string_literal178=(Token)match(input,164,FOLLOW_164_in_bodyNT2739); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_164.add(string_literal178);

            pushFollow(FOLLOW_bodyNTBlock_in_bodyNT2741);
            bodyNTBlock179=bodyNTBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_bodyNTBlock.add(bodyNTBlock179.getTree());


            // AST REWRITE
            // elements: bodyNTBlock
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (RulesASTNode)adaptor.nil();
            // 515:2: -> ^( BODY_BLOCK[\"body\"] bodyNTBlock )
            {
                // Rules.g:515:5: ^( BODY_BLOCK[\"body\"] bodyNTBlock )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BODY_BLOCK, "body"), root_1);

                adaptor.addChild(root_1, stream_bodyNTBlock.nextTree());

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
    // $ANTLR end "bodyNT"

    public static class bodyNTBlock_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bodyNTBlock"
    // Rules.g:518:1: bodyNTBlock : LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) ;
    public final RulesParser.bodyNTBlock_return bodyNTBlock() throws RecognitionException {
        RulesParser.bodyNTBlock_return retval = new RulesParser.bodyNTBlock_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE180=null;
        Token RBRACE182=null;
        RulesParser.thenStatements_return thenStatements181 = null;


        RulesASTNode LBRACE180_tree=null;
        RulesASTNode RBRACE182_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_thenStatements=new RewriteRuleSubtreeStream(adaptor,"rule thenStatements");
        try {
            // Rules.g:518:13: ( LBRACE thenStatements RBRACE -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) ) )
            // Rules.g:518:15: LBRACE thenStatements RBRACE
            {
            LBRACE180=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_bodyNTBlock2762); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE180);

            if ( state.backtracking==0 ) {
               pushScope(BaseRulesParser.BODY_SCOPE); 
            }
            pushFollow(FOLLOW_thenStatements_in_bodyNTBlock2766);
            thenStatements181=thenStatements();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_thenStatements.add(thenStatements181.getTree());
            RBRACE182=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_bodyNTBlock2771); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE182);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 521:2: -> ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
            {
                // Rules.g:521:5: ^( BLOCK ^( STATEMENTS ( thenStatements )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:521:13: ^( STATEMENTS ( thenStatements )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:521:26: ( thenStatements )*
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
    // $ANTLR end "bodyNTBlock"

    public static class thenStatements_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatements"
    // Rules.g:524:1: thenStatements : ( thenStatement )* ;
    public final RulesParser.thenStatements_return thenStatements() throws RecognitionException {
        RulesParser.thenStatements_return retval = new RulesParser.thenStatements_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.thenStatement_return thenStatement183 = null;



        try {
            // Rules.g:525:2: ( ( thenStatement )* )
            // Rules.g:525:4: ( thenStatement )*
            {
            root_0 = (RulesASTNode)adaptor.nil();

            // Rules.g:525:4: ( thenStatement )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( ((LA36_0>=Identifier && LA36_0<=SEMICOLON)||LA36_0==LBRACE||(LA36_0>=FloatingPointLiteral && LA36_0<=WS)||LA36_0==LPAREN||(LA36_0>=164 && LA36_0<=212)||(LA36_0>=217 && LA36_0<=221)||(LA36_0>=223 && LA36_0<=225)||(LA36_0>=228 && LA36_0<=231)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // Rules.g:525:4: thenStatement
            	    {
            	    pushFollow(FOLLOW_thenStatement_in_thenStatements2802);
            	    thenStatement183=thenStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, thenStatement183.getTree());

            	    }
            	    break;

            	default :
            	    break loop36;
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
    // $ANTLR end "thenStatements"

    public static class thenStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "thenStatement"
    // Rules.g:528:1: thenStatement : ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement );
    public final RulesParser.thenStatement_return thenStatement() throws RecognitionException {
        RulesParser.thenStatement_return retval = new RulesParser.thenStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.actionContextStatement_return actionContextStatement184 = null;

        Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration185 = null;

        RulesParser.statement_return statement186 = null;

        Rules_BaseElement.emptyBodyStatement_return emptyBodyStatement187 = null;



        try {
            // Rules.g:529:2: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement )
            int alt37=4;
            alt37 = dfa37.predict(input);
            switch (alt37) {
                case 1 :
                    // Rules.g:529:4: ({...}? actionContextStatement )=> actionContextStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_actionContextStatement_in_thenStatement2822);
                    actionContextStatement184=actionContextStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextStatement184.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:530:4: ( type identifier )=> localVariableDeclaration
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_localVariableDeclaration_in_thenStatement2833);
                    localVariableDeclaration185=localVariableDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, localVariableDeclaration185.getTree());

                    }
                    break;
                case 3 :
                    // Rules.g:531:4: statement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_statement_in_thenStatement2838);
                    statement186=statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement186.getTree());

                    }
                    break;
                case 4 :
                    // Rules.g:532:4: emptyBodyStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_emptyBodyStatement_in_thenStatement2844);
                    emptyBodyStatement187=emptyBodyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyBodyStatement187.getTree());

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
    // $ANTLR end "thenStatement"

    public static class statement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "statement"
    // Rules.g:535:1: statement : ( lineStatement | blockStatement );
    public final RulesParser.statement_return statement() throws RecognitionException {
        RulesParser.statement_return retval = new RulesParser.statement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        RulesParser.lineStatement_return lineStatement188 = null;

        RulesParser.blockStatement_return blockStatement189 = null;



        try {
            // Rules.g:536:2: ( lineStatement | blockStatement )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( ((LA38_0>=Identifier && LA38_0<=SEMICOLON)||(LA38_0>=FloatingPointLiteral && LA38_0<=FALSE)||LA38_0==LPAREN||(LA38_0>=164 && LA38_0<=212)||(LA38_0>=217 && LA38_0<=220)||(LA38_0>=228 && LA38_0<=231)) ) {
                alt38=1;
            }
            else if ( (LA38_0==LBRACE||LA38_0==221||(LA38_0>=223 && LA38_0<=225)) ) {
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
                    // Rules.g:536:4: lineStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_lineStatement_in_statement2856);
                    lineStatement188=lineStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lineStatement188.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:536:20: blockStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_blockStatement_in_statement2860);
                    blockStatement189=blockStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, blockStatement189.getTree());

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
    // $ANTLR end "statement"

    public static class lineStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lineStatement"
    // Rules.g:539:1: lineStatement : ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement );
    public final RulesParser.lineStatement_return lineStatement() throws RecognitionException {
        RulesParser.lineStatement_return retval = new RulesParser.lineStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.emptyStatement_return emptyStatement190 = null;

        Rules_BaseElement.statementExpression_return statementExpression191 = null;

        Rules_BaseElement.returnStatement_return returnStatement192 = null;

        Rules_BaseElement.breakStatement_return breakStatement193 = null;

        Rules_BaseElement.continueStatement_return continueStatement194 = null;

        Rules_BaseElement.throwStatement_return throwStatement195 = null;

        RulesParser.actionContextStatement_return actionContextStatement196 = null;



        try {
            // Rules.g:540:2: ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement )
            int alt39=7;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // Rules.g:540:4: emptyStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_emptyStatement_in_lineStatement2872);
                    emptyStatement190=emptyStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, emptyStatement190.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:541:4: statementExpression
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_statementExpression_in_lineStatement2877);
                    statementExpression191=statementExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementExpression191.getTree());

                    }
                    break;
                case 3 :
                    // Rules.g:542:4: returnStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_lineStatement2882);
                    returnStatement192=returnStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnStatement192.getTree());

                    }
                    break;
                case 4 :
                    // Rules.g:543:4: breakStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_breakStatement_in_lineStatement2887);
                    breakStatement193=breakStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, breakStatement193.getTree());

                    }
                    break;
                case 5 :
                    // Rules.g:544:4: continueStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_continueStatement_in_lineStatement2892);
                    continueStatement194=continueStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continueStatement194.getTree());

                    }
                    break;
                case 6 :
                    // Rules.g:545:4: throwStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_throwStatement_in_lineStatement2897);
                    throwStatement195=throwStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwStatement195.getTree());

                    }
                    break;
                case 7 :
                    // Rules.g:546:4: {...}? => actionContextStatement
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    if ( !(( isInActionContextBlock() )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "lineStatement", " isInActionContextBlock() ");
                    }
                    pushFollow(FOLLOW_actionContextStatement_in_lineStatement2906);
                    actionContextStatement196=actionContextStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, actionContextStatement196.getTree());

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
    // $ANTLR end "lineStatement"

    public static class blockStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "blockStatement"
    // Rules.g:549:1: blockStatement : ( ifRule | whileRule | forRule | block | tryCatchFinally );
    public final RulesParser.blockStatement_return blockStatement() throws RecognitionException {
        RulesParser.blockStatement_return retval = new RulesParser.blockStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Rules_BaseElement.ifRule_return ifRule197 = null;

        Rules_BaseElement.whileRule_return whileRule198 = null;

        Rules_BaseElement.forRule_return forRule199 = null;

        RulesParser.block_return block200 = null;

        Rules_BaseElement.tryCatchFinally_return tryCatchFinally201 = null;



        try {
            // Rules.g:550:2: ( ifRule | whileRule | forRule | block | tryCatchFinally )
            int alt40=5;
            switch ( input.LA(1) ) {
            case 221:
                {
                alt40=1;
                }
                break;
            case 223:
                {
                alt40=2;
                }
                break;
            case 224:
                {
                alt40=3;
                }
                break;
            case LBRACE:
                {
                alt40=4;
                }
                break;
            case 225:
                {
                alt40=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // Rules.g:550:4: ifRule
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_ifRule_in_blockStatement2918);
                    ifRule197=ifRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ifRule197.getTree());

                    }
                    break;
                case 2 :
                    // Rules.g:551:4: whileRule
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_whileRule_in_blockStatement2923);
                    whileRule198=whileRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, whileRule198.getTree());

                    }
                    break;
                case 3 :
                    // Rules.g:552:4: forRule
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_forRule_in_blockStatement2928);
                    forRule199=forRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, forRule199.getTree());

                    }
                    break;
                case 4 :
                    // Rules.g:553:4: block
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_block_in_blockStatement2933);
                    block200=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block200.getTree());

                    }
                    break;
                case 5 :
                    // Rules.g:554:4: tryCatchFinally
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_tryCatchFinally_in_blockStatement2938);
                    tryCatchFinally201=tryCatchFinally();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tryCatchFinally201.getTree());

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
    // $ANTLR end "blockStatement"

    public static class block_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // Rules.g:558:1: block : LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) ;
    public final RulesParser.block_return block() throws RecognitionException {
        RulesParser.block_return retval = new RulesParser.block_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token LBRACE202=null;
        Token RBRACE206=null;
        RulesParser.actionContextStatement_return actionContextStatement203 = null;

        Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration204 = null;

        RulesParser.statement_return statement205 = null;


        RulesASTNode LBRACE202_tree=null;
        RulesASTNode RBRACE206_tree=null;
        RewriteRuleTokenStream stream_RBRACE=new RewriteRuleTokenStream(adaptor,"token RBRACE");
        RewriteRuleTokenStream stream_LBRACE=new RewriteRuleTokenStream(adaptor,"token LBRACE");
        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        RewriteRuleSubtreeStream stream_actionContextStatement=new RewriteRuleSubtreeStream(adaptor,"rule actionContextStatement");
        RewriteRuleSubtreeStream stream_localVariableDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule localVariableDeclaration");
        try {
            // Rules.g:558:7: ( LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) ) )
            // Rules.g:558:9: LBRACE ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )* RBRACE
            {
            LBRACE202=(Token)match(input,LBRACE,FOLLOW_LBRACE_in_block2950); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACE.add(LBRACE202);

            if ( state.backtracking==0 ) {
               pushScope(BLOCK_SCOPE); 
            }
            // Rules.g:559:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*
            loop41:
            do {
                int alt41=4;
                alt41 = dfa41.predict(input);
                switch (alt41) {
            	case 1 :
            	    // Rules.g:559:5: ({...}? actionContextStatement )=> actionContextStatement
            	    {
            	    pushFollow(FOLLOW_actionContextStatement_in_block2966);
            	    actionContextStatement203=actionContextStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_actionContextStatement.add(actionContextStatement203.getTree());

            	    }
            	    break;
            	case 2 :
            	    // Rules.g:559:87: ( type identifier )=> localVariableDeclaration
            	    {
            	    pushFollow(FOLLOW_localVariableDeclaration_in_block2977);
            	    localVariableDeclaration204=localVariableDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_localVariableDeclaration.add(localVariableDeclaration204.getTree());

            	    }
            	    break;
            	case 3 :
            	    // Rules.g:559:134: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block2981);
            	    statement205=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement205.getTree());

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            RBRACE206=(Token)match(input,RBRACE,FOLLOW_RBRACE_in_block2985); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACE.add(RBRACE206);

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

            root_0 = (RulesASTNode)adaptor.nil();
            // 561:3: -> ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
            {
                // Rules.g:561:6: ^( BLOCK ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(BLOCK, "BLOCK"), root_1);

                // Rules.g:561:14: ^( STATEMENTS ( actionContextStatement )* ( localVariableDeclaration )* ( statement )* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Rules.g:561:27: ( actionContextStatement )*
                while ( stream_actionContextStatement.hasNext() ) {
                    adaptor.addChild(root_2, stream_actionContextStatement.nextTree());

                }
                stream_actionContextStatement.reset();
                // Rules.g:561:51: ( localVariableDeclaration )*
                while ( stream_localVariableDeclaration.hasNext() ) {
                    adaptor.addChild(root_2, stream_localVariableDeclaration.nextTree());

                }
                stream_localVariableDeclaration.reset();
                // Rules.g:561:77: ( statement )*
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
    // $ANTLR end "block"

    // $ANTLR start synpred1_Rules
    public final void synpred1_Rules_fragment() throws RecognitionException {   
        // Rules.g:297:12: ( localInitializerArrayLiteral ( ';' | ',' ) )
        // Rules.g:297:13: localInitializerArrayLiteral ( ';' | ',' )
        {
        pushFollow(FOLLOW_localInitializerArrayLiteral_in_synpred1_Rules1392);
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
    // $ANTLR end synpred1_Rules

    // $ANTLR start synpred2_Rules
    public final void synpred2_Rules_fragment() throws RecognitionException {   
        // Rules.g:387:4: ( predicateStatement )
        // Rules.g:387:5: predicateStatement
        {
        pushFollow(FOLLOW_predicateStatement_in_synpred2_Rules1791);
        predicateStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Rules

    // $ANTLR start synpred3_Rules
    public final void synpred3_Rules_fragment() throws RecognitionException {   
        // Rules.g:418:12: ( localInitializerArrayLiteral ( ';' | ',' ) )
        // Rules.g:418:13: localInitializerArrayLiteral ( ';' | ',' )
        {
        pushFollow(FOLLOW_localInitializerArrayLiteral_in_synpred3_Rules1992);
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
    // $ANTLR end synpred3_Rules

    // $ANTLR start synpred4_Rules
    public final void synpred4_Rules_fragment() throws RecognitionException {   
        // Rules.g:529:4: ({...}? actionContextStatement )
        // Rules.g:529:5: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred4_Rules", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred4_Rules2818);
        actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Rules

    // $ANTLR start synpred5_Rules
    public final void synpred5_Rules_fragment() throws RecognitionException {   
        // Rules.g:530:4: ( type identifier )
        // Rules.g:530:5: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred5_Rules2828);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred5_Rules2830);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Rules

    // $ANTLR start synpred6_Rules
    public final void synpred6_Rules_fragment() throws RecognitionException {   
        // Rules.g:559:5: ({...}? actionContextStatement )
        // Rules.g:559:6: {...}? actionContextStatement
        {
        if ( !(( isInActionContextBlock() )) ) {
            if (state.backtracking>0) {state.failed=true; return ;}
            throw new FailedPredicateException(input, "synpred6_Rules", " isInActionContextBlock() ");
        }
        pushFollow(FOLLOW_actionContextStatement_in_synpred6_Rules2962);
        actionContextStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Rules

    // $ANTLR start synpred7_Rules
    public final void synpred7_Rules_fragment() throws RecognitionException {   
        // Rules.g:559:87: ( type identifier )
        // Rules.g:559:88: type identifier
        {
        pushFollow(FOLLOW_type_in_synpred7_Rules2971);
        type();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_identifier_in_synpred7_Rules2973);
        identifier();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Rules

    // Delegated rules
    public Rules_BaseElement.nameOrPrimitiveType_return nameOrPrimitiveType() throws RecognitionException { return gBaseElement.nameOrPrimitiveType(); }
    public Rules_BaseElement.continueStatement_return continueStatement() throws RecognitionException { return gBaseElement.continueStatement(); }
    public Rules_BaseElement.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gBaseElement.multiplicativeExpression(); }
    public Rules_BaseElement.breakStatement_return breakStatement() throws RecognitionException { return gBaseElement.breakStatement(); }
    public Rules_BaseElement.emptyStatement_return emptyStatement() throws RecognitionException { return gBaseElement.emptyStatement(); }
    public Rules_BaseElement.typeAdditionalArrayDim_return typeAdditionalArrayDim() throws RecognitionException { return gBaseElement.typeAdditionalArrayDim(); }
    public Rules_BaseElement.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException { return gBaseElement.localVariableDeclaration(); }
    public Rules_BaseElement.setMembershipExpression_return setMembershipExpression() throws RecognitionException { return gBaseElement.setMembershipExpression(); }
    public Rules_BaseElement.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException { return gBaseElement.conditionalAndExpression(); }
    public Rules_BaseElement.expression_return expression() throws RecognitionException { return gBaseElement.expression(); }
    public Rules_BaseElement.relationalExpression_return relationalExpression() throws RecognitionException { return gBaseElement.relationalExpression(); }
    public Rules_BaseElement.literal_return literal() throws RecognitionException { return gBaseElement.literal(); }
    public Rules_BaseElement.arrayAllocator_return arrayAllocator() throws RecognitionException { return gBaseElement.arrayAllocator(); }
    public Rules_BaseElement.semiColon_return semiColon() throws RecognitionException { return gBaseElement.semiColon(); }
    public Rules_BaseElement.arrayAccessSuffix_return arrayAccessSuffix() throws RecognitionException { return gBaseElement.arrayAccessSuffix(); }
    public Rules_BaseElement.catchRule_return catchRule() throws RecognitionException { return gBaseElement.catchRule(); }
    public Rules_BaseElement.fieldAccessSuffix_return fieldAccessSuffix() throws RecognitionException { return gBaseElement.fieldAccessSuffix(); }
    public Rules_BaseElement.primitiveType_return primitiveType() throws RecognitionException { return gBaseElement.primitiveType(); }
    public Rules_BaseElement.comparisonNoLHS_return comparisonNoLHS() throws RecognitionException { return gBaseElement.comparisonNoLHS(); }
    public Rules_BaseElement.nonBraceBlock_return nonBraceBlock() throws RecognitionException { return gBaseElement.nonBraceBlock(); }
    public Rules_BaseElement.additiveExpression_return additiveExpression() throws RecognitionException { return gBaseElement.additiveExpression(); }
    public Rules_BaseElement.localInitializerArrayLiteral_return localInitializerArrayLiteral() throws RecognitionException { return gBaseElement.localInitializerArrayLiteral(); }
    public Rules_BaseElement.forRule_return forRule() throws RecognitionException { return gBaseElement.forRule(); }
    public Rules_BaseElement.statementExpressionList_return statementExpressionList() throws RecognitionException { return gBaseElement.statementExpressionList(); }
    public Rules_BaseElement.rangeStart_return rangeStart() throws RecognitionException { return gBaseElement.rangeStart(); }
    public Rules_BaseElement.type_return type() throws RecognitionException { return gBaseElement.type(); }
    public Rules_BaseElement.variableDeclarator_return variableDeclarator(RulesASTNode type) throws RecognitionException { return gBaseElement.variableDeclarator(type); }
    public Rules_BaseElement.equalityExpression_return equalityExpression() throws RecognitionException { return gBaseElement.equalityExpression(); }
    public Rules_BaseElement.methodName_return methodName() throws RecognitionException { return gBaseElement.methodName(); }
    public Rules_BaseElement.predicateStatement_return predicateStatement() throws RecognitionException { return gBaseElement.predicateStatement(); }
    public Rules_BaseElement.unaryExpression_return unaryExpression() throws RecognitionException { return gBaseElement.unaryExpression(); }
    public Rules_BaseElement.domainSpec_return domainSpec() throws RecognitionException { return gBaseElement.domainSpec(); }
    public Rules_BaseElement.argumentsSuffix_return argumentsSuffix() throws RecognitionException { return gBaseElement.argumentsSuffix(); }
    public Rules_BaseElement.expressionName_return expressionName() throws RecognitionException { return gBaseElement.expressionName(); }
    public Rules_BaseElement.assignmentOp_return assignmentOp() throws RecognitionException { return gBaseElement.assignmentOp(); }
    public Rules_BaseElement.integerLiteral_return integerLiteral() throws RecognitionException { return gBaseElement.integerLiteral(); }
    public Rules_BaseElement.typeName_return typeName() throws RecognitionException { return gBaseElement.typeName(); }
    public Rules_BaseElement.arrayLiteral_return arrayLiteral() throws RecognitionException { return gBaseElement.arrayLiteral(); }
    public Rules_BaseElement.finallyRule_return finallyRule() throws RecognitionException { return gBaseElement.finallyRule(); }
    public Rules_BaseElement.rangeEnd_return rangeEnd() throws RecognitionException { return gBaseElement.rangeEnd(); }
    public Rules_BaseElement.statementExpression_return statementExpression() throws RecognitionException { return gBaseElement.statementExpression(); }
    public Rules_BaseElement.primitive_return primitive() throws RecognitionException { return gBaseElement.primitive(); }
    public Rules_BaseElement.predicate_return predicate() throws RecognitionException { return gBaseElement.predicate(); }
    public Rules_BaseElement.argumentList_return argumentList() throws RecognitionException { return gBaseElement.argumentList(); }
    public Rules_BaseElement.throwStatement_return throwStatement() throws RecognitionException { return gBaseElement.throwStatement(); }
    public Rules_BaseElement.rangeExpression_return rangeExpression() throws RecognitionException { return gBaseElement.rangeExpression(); }
    public Rules_BaseElement.whileRule_return whileRule() throws RecognitionException { return gBaseElement.whileRule(); }
    public Rules_BaseElement.ifRule_return ifRule() throws RecognitionException { return gBaseElement.ifRule(); }
    public Rules_BaseElement.listStatementExpression_return listStatementExpression() throws RecognitionException { return gBaseElement.listStatementExpression(); }
    public Rules_BaseElement.returnStatement_return returnStatement() throws RecognitionException { return gBaseElement.returnStatement(); }
    public Rules_BaseElement.booleanLiteral_return booleanLiteral() throws RecognitionException { return gBaseElement.booleanLiteral(); }
    public Rules_BaseElement.primaryExpression_return primaryExpression() throws RecognitionException { return gBaseElement.primaryExpression(); }
    public Rules_BaseElement.primarySuffix_return primarySuffix(RulesASTNode type) throws RecognitionException { return gBaseElement.primarySuffix(type); }
    public Rules_BaseElement.tryCatchFinally_return tryCatchFinally() throws RecognitionException { return gBaseElement.tryCatchFinally(); }
    public Rules_BaseElement.predicateStatements_return predicateStatements() throws RecognitionException { return gBaseElement.predicateStatements(); }
    public Rules_BaseElement.primaryPrefix_return primaryPrefix() throws RecognitionException { return gBaseElement.primaryPrefix(); }
    public Rules_BaseElement.name_return name(int nameType) throws RecognitionException { return gBaseElement.name(nameType); }
    public Rules_BaseElement.emptyBodyStatement_return emptyBodyStatement() throws RecognitionException { return gBaseElement.emptyBodyStatement(); }

    public final boolean synpred1_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Rules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Rules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA26 dfa26 = new DFA26(this);
    protected DFA29 dfa29 = new DFA29(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA41 dfa41 = new DFA41(this);
    static final String DFA18_eotS =
        "\27\uffff";
    static final String DFA18_eofS =
        "\27\uffff";
    static final String DFA18_minS =
        "\1\152\1\0\25\uffff";
    static final String DFA18_maxS =
        "\1\u00e7\1\0\25\uffff";
    static final String DFA18_acceptS =
        "\2\uffff\1\2\23\uffff\1\1";
    static final String DFA18_specialS =
        "\1\uffff\1\0\25\uffff}>";
    static final String[] DFA18_transitionS = {
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

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "297:11: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_1 = input.LA(1);

                         
                        int index18_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Rules()) ) {s = 22;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index18_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA22_eotS =
        "\27\uffff";
    static final String DFA22_eofS =
        "\27\uffff";
    static final String DFA22_minS =
        "\4\152\1\uffff\4\152\2\153\1\152\2\153\1\152\2\153\1\uffff\2\152"+
        "\1\uffff\2\152";
    static final String DFA22_maxS =
        "\1\u00e7\3\u00d4\1\uffff\6\u00d8\1\u00d4\2\153\1\u00d4\2\153\1"+
        "\uffff\2\u00d4\1\uffff\2\u00d4";
    static final String DFA22_acceptS =
        "\4\uffff\1\2\14\uffff\1\1\2\uffff\1\1\2\uffff";
    static final String DFA22_specialS =
        "\27\uffff}>";
    static final String[] DFA22_transitionS = {
            "\2\4\11\uffff\1\4\11\uffff\10\4\12\uffff\1\4\22\uffff\51\4"+
            "\1\2\1\3\1\1\5\4\4\uffff\5\4\1\uffff\3\4\2\uffff\4\4",
            "\1\5\12\uffff\1\4\7\uffff\2\4\2\uffff\1\6\5\uffff\10\4\2\uffff"+
            "\1\4\1\uffff\1\4\20\uffff\61\6",
            "\1\7\12\uffff\1\4\7\uffff\2\4\2\uffff\1\10\5\uffff\10\4\2"+
            "\uffff\1\4\1\uffff\1\4\20\uffff\61\10",
            "\1\11\12\uffff\1\4\7\uffff\2\4\2\uffff\1\12\5\uffff\10\4\2"+
            "\uffff\1\4\1\uffff\1\4\20\uffff\61\12",
            "",
            "\1\14\1\4\21\uffff\1\13\3\uffff\1\15\7\uffff\1\4\32\uffff"+
            "\61\15\3\uffff\1\4",
            "\1\14\1\4\21\uffff\1\13\3\uffff\1\15\7\uffff\1\4\32\uffff"+
            "\61\15\3\uffff\1\4",
            "\1\17\1\4\21\uffff\1\16\3\uffff\1\20\7\uffff\1\4\32\uffff"+
            "\61\20\3\uffff\1\4",
            "\1\17\1\4\21\uffff\1\16\3\uffff\1\20\7\uffff\1\4\32\uffff"+
            "\61\20\3\uffff\1\4",
            "\1\21\35\uffff\1\4\116\uffff\1\4",
            "\1\21\35\uffff\1\4\116\uffff\1\4",
            "\1\22\26\uffff\1\23\42\uffff\61\23",
            "\1\24",
            "\1\24",
            "\1\25\26\uffff\1\26\42\uffff\61\26",
            "\1\24",
            "\1\24",
            "",
            "\1\14\22\uffff\1\13\3\uffff\1\15\42\uffff\61\15",
            "\1\14\22\uffff\1\13\3\uffff\1\15\42\uffff\61\15",
            "",
            "\1\17\22\uffff\1\16\3\uffff\1\20\42\uffff\61\20",
            "\1\17\22\uffff\1\16\3\uffff\1\20\42\uffff\61\20"
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "341:1: acStatement : ( actionContextStatement | thenStatement );";
        }
    }
    static final String DFA26_eotS =
        "\43\uffff";
    static final String DFA26_eofS =
        "\43\uffff";
    static final String DFA26_minS =
        "\1\152\3\uffff\11\0\1\uffff\1\0\7\uffff\4\0\11\uffff";
    static final String DFA26_maxS =
        "\1\u00e7\3\uffff\11\0\1\uffff\1\0\7\uffff\4\0\11\uffff";
    static final String DFA26_acceptS =
        "\1\uffff\3\1\11\uffff\1\1\1\uffff\7\1\4\uffff\1\2\10\uffff";
    static final String DFA26_specialS =
        "\1\0\3\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\12"+
        "\7\uffff\1\13\1\14\1\15\1\16\11\uffff}>";
    static final String[] DFA26_transitionS = {
            "\1\12\1\26\2\uffff\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\16"+
            "\1\uffff\1\1\1\2\3\uffff\1\3\2\uffff\1\5\1\6\1\10\2\4\2\7\1"+
            "\32\12\uffff\1\11\1\uffff\1\15\20\uffff\51\31\1\27\1\30\1\13"+
            "\5\31\4\uffff\5\32\1\uffff\3\32\2\uffff\4\14",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "386:1: preconditionStatement : ( ( predicateStatement )=> predicateStatement | thenStatement );";
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
                        if ( (LA26_0==PLUS) && (synpred2_Rules())) {s = 1;}

                        else if ( (LA26_0==MINUS) && (synpred2_Rules())) {s = 2;}

                        else if ( (LA26_0==POUND) && (synpred2_Rules())) {s = 3;}

                        else if ( ((LA26_0>=DecimalLiteral && LA26_0<=HexLiteral)) ) {s = 4;}

                        else if ( (LA26_0==FloatingPointLiteral) ) {s = 5;}

                        else if ( (LA26_0==StringLiteral) ) {s = 6;}

                        else if ( ((LA26_0>=TRUE && LA26_0<=FALSE)) ) {s = 7;}

                        else if ( (LA26_0==NullLiteral) ) {s = 8;}

                        else if ( (LA26_0==LPAREN) ) {s = 9;}

                        else if ( (LA26_0==Identifier) ) {s = 10;}

                        else if ( (LA26_0==207) ) {s = 11;}

                        else if ( ((LA26_0>=228 && LA26_0<=231)) ) {s = 12;}

                        else if ( (LA26_0==LBRACK) && (synpred2_Rules())) {s = 13;}

                        else if ( (LA26_0==LBRACE) ) {s = 14;}

                        else if ( (LA26_0==EQ) && (synpred2_Rules())) {s = 15;}

                        else if ( (LA26_0==NE) && (synpred2_Rules())) {s = 16;}

                        else if ( (LA26_0==INSTANCE_OF) && (synpred2_Rules())) {s = 17;}

                        else if ( (LA26_0==LT) && (synpred2_Rules())) {s = 18;}

                        else if ( (LA26_0==GT) && (synpred2_Rules())) {s = 19;}

                        else if ( (LA26_0==LE) && (synpred2_Rules())) {s = 20;}

                        else if ( (LA26_0==GE) && (synpred2_Rules())) {s = 21;}

                        else if ( (LA26_0==SEMICOLON) ) {s = 22;}

                        else if ( (LA26_0==205) ) {s = 23;}

                        else if ( (LA26_0==206) ) {s = 24;}

                        else if ( ((LA26_0>=164 && LA26_0<=204)||(LA26_0>=208 && LA26_0<=212)) ) {s = 25;}

                        else if ( (LA26_0==WS||(LA26_0>=217 && LA26_0<=221)||(LA26_0>=223 && LA26_0<=225)) ) {s = 26;}

                         
                        input.seek(index26_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA26_4 = input.LA(1);

                         
                        int index26_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA26_5 = input.LA(1);

                         
                        int index26_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA26_6 = input.LA(1);

                         
                        int index26_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA26_7 = input.LA(1);

                         
                        int index26_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_7);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA26_8 = input.LA(1);

                         
                        int index26_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_8);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA26_9 = input.LA(1);

                         
                        int index26_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA26_10 = input.LA(1);

                         
                        int index26_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA26_11 = input.LA(1);

                         
                        int index26_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_11);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA26_12 = input.LA(1);

                         
                        int index26_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_12);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA26_14 = input.LA(1);

                         
                        int index26_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_14);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA26_22 = input.LA(1);

                         
                        int index26_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_22);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA26_23 = input.LA(1);

                         
                        int index26_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_23);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA26_24 = input.LA(1);

                         
                        int index26_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_24);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA26_25 = input.LA(1);

                         
                        int index26_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Rules()) ) {s = 21;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index26_25);
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
    static final String DFA29_eotS =
        "\27\uffff";
    static final String DFA29_eofS =
        "\27\uffff";
    static final String DFA29_minS =
        "\1\152\1\0\25\uffff";
    static final String DFA29_maxS =
        "\1\u00e7\1\0\25\uffff";
    static final String DFA29_acceptS =
        "\2\uffff\1\2\23\uffff\1\1";
    static final String DFA29_specialS =
        "\1\uffff\1\0\25\uffff}>";
    static final String[] DFA29_transitionS = {
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

    static final short[] DFA29_eot = DFA.unpackEncodedString(DFA29_eotS);
    static final short[] DFA29_eof = DFA.unpackEncodedString(DFA29_eofS);
    static final char[] DFA29_min = DFA.unpackEncodedStringToUnsignedChars(DFA29_minS);
    static final char[] DFA29_max = DFA.unpackEncodedStringToUnsignedChars(DFA29_maxS);
    static final short[] DFA29_accept = DFA.unpackEncodedString(DFA29_acceptS);
    static final short[] DFA29_special = DFA.unpackEncodedString(DFA29_specialS);
    static final short[][] DFA29_transition;

    static {
        int numStates = DFA29_transitionS.length;
        DFA29_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA29_transition[i] = DFA.unpackEncodedString(DFA29_transitionS[i]);
        }
    }

    class DFA29 extends DFA {

        public DFA29(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 29;
            this.eot = DFA29_eot;
            this.eof = DFA29_eof;
            this.min = DFA29_min;
            this.max = DFA29_max;
            this.accept = DFA29_accept;
            this.special = DFA29_special;
            this.transition = DFA29_transition;
        }
        public String getDescription() {
            return "418:11: ( ( localInitializerArrayLiteral ( ';' | ',' ) )=> localInitializerArrayLiteral | expression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA29_1 = input.LA(1);

                         
                        int index29_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Rules()) ) {s = 22;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index29_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 29, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA37_eotS =
        "\50\uffff";
    static final String DFA37_eofS =
        "\50\uffff";
    static final String DFA37_minS =
        "\7\152\1\uffff\1\152\1\uffff\6\152\2\153\2\uffff\4\152\3\uffff"+
        "\1\152\2\153\1\152\2\153\1\0\2\152\1\0\2\152\1\uffff";
    static final String DFA37_maxS =
        "\1\u00e7\6\u00d4\1\uffff\1\u00d4\1\uffff\1\u00d4\1\u00e7\6\u00d8"+
        "\2\uffff\1\u00e7\3\u00d4\3\uffff\1\u00d4\2\153\1\u00d4\2\153\1\0"+
        "\2\u00d4\1\0\2\u00d4\1\uffff";
    static final String DFA37_acceptS =
        "\7\uffff\1\3\1\uffff\1\4\10\uffff\2\2\4\uffff\3\2\14\uffff\1\1";
    static final String DFA37_specialS =
        "\1\uffff\1\23\1\10\1\11\1\20\1\4\1\14\1\uffff\1\6\3\uffff\1\33"+
        "\1\5\1\21\1\24\1\7\1\2\3\uffff\1\1\1\16\1\30\3\uffff\1\26\1\15\1"+
        "\13\1\25\1\17\1\27\1\12\1\32\1\3\1\22\1\31\1\0\1\uffff}>";
    static final String[] DFA37_transitionS = {
            "\1\4\1\7\11\uffff\1\7\11\uffff\2\7\1\5\4\7\1\11\12\uffff\1"+
            "\7\22\uffff\51\10\1\2\1\3\1\1\5\10\4\uffff\5\7\1\uffff\3\7\2"+
            "\uffff\4\6",
            "\1\14\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\15\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\15",
            "\1\16\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\17\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\17",
            "\1\20\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\21\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\21",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\7\13\uffff\1\23\21\uffff\1\24\20\uffff\61"+
            "\23",
            "",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\23",
            "",
            "\1\25\26\uffff\1\26\42\uffff\61\26",
            "\1\7\3\uffff\10\7\1\uffff\2\7\3\uffff\1\7\2\uffff\7\7\13\uffff"+
            "\1\7\1\uffff\1\7\1\27\17\uffff\61\7\17\uffff\4\7",
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
            "\1\7\3\uffff\10\7\1\uffff\2\7\3\uffff\1\7\2\uffff\7\7\13\uffff"+
            "\1\7\1\uffff\1\7\1\27\17\uffff\61\7\17\uffff\4\7",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\7\7\uffff\1\12\1\7\2\uffff\1\23\5\uffff\10"+
            "\7\2\uffff\1\7\1\uffff\1\13\20\uffff\61\23",
            "\1\22\12\uffff\1\7\13\uffff\1\23\21\uffff\1\7\20\uffff\61"+
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

    static final short[] DFA37_eot = DFA.unpackEncodedString(DFA37_eotS);
    static final short[] DFA37_eof = DFA.unpackEncodedString(DFA37_eofS);
    static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars(DFA37_minS);
    static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars(DFA37_maxS);
    static final short[] DFA37_accept = DFA.unpackEncodedString(DFA37_acceptS);
    static final short[] DFA37_special = DFA.unpackEncodedString(DFA37_specialS);
    static final short[][] DFA37_transition;

    static {
        int numStates = DFA37_transitionS.length;
        DFA37_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA37_transition[i] = DFA.unpackEncodedString(DFA37_transitionS[i]);
        }
    }

    class DFA37 extends DFA {

        public DFA37(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 37;
            this.eot = DFA37_eot;
            this.eof = DFA37_eof;
            this.min = DFA37_min;
            this.max = DFA37_max;
            this.accept = DFA37_accept;
            this.special = DFA37_special;
            this.transition = DFA37_transition;
        }
        public String getDescription() {
            return "528:1: thenStatement : ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement | emptyBodyStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA37_38 = input.LA(1);

                         
                        int index37_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_38==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA37_38==NullLiteral||(LA37_38>=164 && LA37_38<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA37_38==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index37_38);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA37_21 = input.LA(1);

                         
                        int index37_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_21==LBRACK) ) {s = 11;}

                        else if ( (LA37_21==DOT) ) {s = 10;}

                        else if ( (LA37_21==LBRACE||LA37_21==ANNOTATE||(LA37_21>=INCR && LA37_21<=MOD_EQUAL)||LA37_21==LPAREN) ) {s = 7;}

                        else if ( (LA37_21==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_21==NullLiteral||(LA37_21>=164 && LA37_21<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA37_17 = input.LA(1);

                         
                        int index37_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_17==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA37_17==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_17==216) && (synpred5_Rules())) {s = 25;}

                         
                        input.seek(index37_17);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA37_35 = input.LA(1);

                         
                        int index37_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_35==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA37_35==NullLiteral||(LA37_35>=164 && LA37_35<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA37_35==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                         
                        input.seek(index37_35);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA37_5 = input.LA(1);

                         
                        int index37_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_5==LBRACK) ) {s = 11;}

                        else if ( (LA37_5==DOT) ) {s = 10;}

                        else if ( (LA37_5==LBRACE||LA37_5==ANNOTATE||(LA37_5>=INCR && LA37_5<=MOD_EQUAL)||LA37_5==LPAREN) ) {s = 7;}

                        else if ( (LA37_5==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_5==NullLiteral||(LA37_5>=164 && LA37_5<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA37_13 = input.LA(1);

                         
                        int index37_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_13==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                        else if ( (LA37_13==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA37_13==NullLiteral||(LA37_13>=164 && LA37_13<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA37_13==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_13==216) && (synpred5_Rules())) {s = 25;}

                        else if ( (LA37_13==SEMICOLON) && (synpred5_Rules())) {s = 26;}

                         
                        input.seek(index37_13);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA37_8 = input.LA(1);

                         
                        int index37_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_8==DOT) ) {s = 10;}

                        else if ( (LA37_8==LBRACK) ) {s = 11;}

                        else if ( (LA37_8==LBRACE||LA37_8==ANNOTATE||(LA37_8>=INCR && LA37_8<=MOD_EQUAL)||LA37_8==LPAREN) ) {s = 7;}

                        else if ( (LA37_8==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_8==NullLiteral||(LA37_8>=164 && LA37_8<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA37_16 = input.LA(1);

                         
                        int index37_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_16==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA37_16==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_16==216) && (synpred5_Rules())) {s = 25;}

                         
                        input.seek(index37_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA37_2 = input.LA(1);

                         
                        int index37_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_2==DOT) ) {s = 10;}

                        else if ( (LA37_2==LBRACK) ) {s = 11;}

                        else if ( (LA37_2==LBRACE||LA37_2==ANNOTATE||(LA37_2>=INCR && LA37_2<=MOD_EQUAL)||LA37_2==LPAREN) ) {s = 7;}

                        else if ( (LA37_2==Identifier) && (( isInActionContextBlock() ))) {s = 14;}

                        else if ( (LA37_2==NullLiteral||(LA37_2>=164 && LA37_2<=212)) && (( isInActionContextBlock() ))) {s = 15;}

                         
                        input.seek(index37_2);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA37_3 = input.LA(1);

                         
                        int index37_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_3==DOT) ) {s = 10;}

                        else if ( (LA37_3==LBRACK) ) {s = 11;}

                        else if ( (LA37_3==LBRACE||LA37_3==ANNOTATE||(LA37_3>=INCR && LA37_3<=MOD_EQUAL)||LA37_3==LPAREN) ) {s = 7;}

                        else if ( (LA37_3==Identifier) && (( isInActionContextBlock() ))) {s = 16;}

                        else if ( (LA37_3==NullLiteral||(LA37_3>=164 && LA37_3<=212)) && (( isInActionContextBlock() ))) {s = 17;}

                         
                        input.seek(index37_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA37_33 = input.LA(1);

                         
                        int index37_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Rules()) ) {s = 39;}

                        else if ( (synpred5_Rules()) ) {s = 26;}

                        else if ( (( isInActionContextBlock() )) ) {s = 7;}

                         
                        input.seek(index37_33);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA37_29 = input.LA(1);

                         
                        int index37_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_29==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index37_29);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA37_6 = input.LA(1);

                         
                        int index37_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_6==LBRACK) ) {s = 20;}

                        else if ( (LA37_6==LBRACE) ) {s = 7;}

                        else if ( (LA37_6==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_6==NullLiteral||(LA37_6>=164 && LA37_6<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_6);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA37_28 = input.LA(1);

                         
                        int index37_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_28==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index37_28);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA37_22 = input.LA(1);

                         
                        int index37_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_22==LBRACK) ) {s = 11;}

                        else if ( (LA37_22==LBRACE||LA37_22==ANNOTATE||(LA37_22>=INCR && LA37_22<=MOD_EQUAL)||LA37_22==LPAREN) ) {s = 7;}

                        else if ( (LA37_22==DOT) ) {s = 10;}

                        else if ( (LA37_22==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_22==NullLiteral||(LA37_22>=164 && LA37_22<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_22);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA37_31 = input.LA(1);

                         
                        int index37_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_31==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index37_31);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA37_4 = input.LA(1);

                         
                        int index37_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_4==DOT) ) {s = 10;}

                        else if ( (LA37_4==LBRACE||LA37_4==ANNOTATE||(LA37_4>=INCR && LA37_4<=MOD_EQUAL)||LA37_4==LPAREN) ) {s = 7;}

                        else if ( (LA37_4==LBRACK) ) {s = 11;}

                        else if ( (LA37_4==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_4==NullLiteral||(LA37_4>=164 && LA37_4<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_4);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA37_14 = input.LA(1);

                         
                        int index37_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_14==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA37_14==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA37_14==NullLiteral||(LA37_14>=164 && LA37_14<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA37_14==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_14==216) && (synpred5_Rules())) {s = 25;}

                        else if ( (LA37_14==SEMICOLON) && (synpred5_Rules())) {s = 26;}

                         
                        input.seek(index37_14);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA37_36 = input.LA(1);

                         
                        int index37_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Rules()) ) {s = 39;}

                        else if ( (( isInActionContextBlock() )) ) {s = 7;}

                         
                        input.seek(index37_36);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA37_1 = input.LA(1);

                         
                        int index37_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_1==DOT) ) {s = 10;}

                        else if ( (LA37_1==LBRACK) ) {s = 11;}

                        else if ( (LA37_1==LBRACE||LA37_1==ANNOTATE||(LA37_1>=INCR && LA37_1<=MOD_EQUAL)||LA37_1==LPAREN) ) {s = 7;}

                        else if ( (LA37_1==Identifier) && (( isInActionContextBlock() ))) {s = 12;}

                        else if ( (LA37_1==NullLiteral||(LA37_1>=164 && LA37_1<=212)) && (( isInActionContextBlock() ))) {s = 13;}

                         
                        input.seek(index37_1);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA37_15 = input.LA(1);

                         
                        int index37_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_15==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA37_15==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA37_15==NullLiteral||(LA37_15>=164 && LA37_15<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA37_15==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_15==216) && (synpred5_Rules())) {s = 25;}

                        else if ( (LA37_15==SEMICOLON) && (synpred5_Rules())) {s = 26;}

                         
                        input.seek(index37_15);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA37_30 = input.LA(1);

                         
                        int index37_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_30==Identifier) && (( isInActionContextBlock() ))) {s = 37;}

                        else if ( (LA37_30==NullLiteral||(LA37_30>=164 && LA37_30<=212)) && (( isInActionContextBlock() ))) {s = 38;}

                         
                        input.seek(index37_30);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA37_27 = input.LA(1);

                         
                        int index37_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_27==Identifier) && (( isInActionContextBlock() ))) {s = 34;}

                        else if ( (LA37_27==NullLiteral||(LA37_27>=164 && LA37_27<=212)) && (( isInActionContextBlock() ))) {s = 35;}

                         
                        input.seek(index37_27);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA37_32 = input.LA(1);

                         
                        int index37_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_32==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index37_32);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA37_23 = input.LA(1);

                         
                        int index37_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_23==LBRACE||LA37_23==LBRACK) ) {s = 7;}

                        else if ( (LA37_23==Identifier) && (synpred5_Rules())) {s = 18;}

                        else if ( (LA37_23==NullLiteral||(LA37_23>=164 && LA37_23<=212)) && (synpred5_Rules())) {s = 19;}

                         
                        input.seek(index37_23);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA37_37 = input.LA(1);

                         
                        int index37_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_37==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA37_37==NullLiteral||(LA37_37>=164 && LA37_37<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA37_37==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index37_37);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA37_34 = input.LA(1);

                         
                        int index37_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_34==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA37_34==NullLiteral||(LA37_34>=164 && LA37_34<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                        else if ( (LA37_34==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                         
                        input.seek(index37_34);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA37_12 = input.LA(1);

                         
                        int index37_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA37_12==ASSIGN) && (synpred5_Rules())) {s = 24;}

                        else if ( (LA37_12==216) && (synpred5_Rules())) {s = 25;}

                        else if ( (LA37_12==SEMICOLON) && (synpred5_Rules())) {s = 26;}

                        else if ( (LA37_12==DOT) && (( isInActionContextBlock() ))) {s = 27;}

                        else if ( (LA37_12==Identifier) && (( isInActionContextBlock() ))) {s = 28;}

                        else if ( (LA37_12==NullLiteral||(LA37_12>=164 && LA37_12<=212)) && (( isInActionContextBlock() ))) {s = 29;}

                         
                        input.seek(index37_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 37, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA39_eotS =
        "\13\uffff";
    static final String DFA39_eofS =
        "\13\uffff";
    static final String DFA39_minS =
        "\1\152\2\uffff\1\152\4\uffff\2\152\1\uffff";
    static final String DFA39_maxS =
        "\1\u00e7\2\uffff\1\u00d4\4\uffff\2\u00d4\1\uffff";
    static final String DFA39_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\3\1\4\1\5\1\6\2\uffff\1\7";
    static final String DFA39_specialS =
        "\3\uffff\1\0\4\uffff\1\2\1\1\1\uffff}>";
    static final String[] DFA39_transitionS = {
            "\1\2\1\1\23\uffff\7\2\13\uffff\1\2\22\uffff\51\2\1\10\1\11"+
            "\1\3\5\2\4\uffff\1\4\1\5\1\6\1\7\7\uffff\4\2",
            "",
            "",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\20\uffff\61\12",
            "",
            "",
            "",
            "",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\20\uffff\61\12",
            "\1\12\12\uffff\1\2\7\uffff\2\2\2\uffff\1\12\5\uffff\10\2\2"+
            "\uffff\1\2\1\uffff\1\2\20\uffff\61\12",
            ""
    };

    static final short[] DFA39_eot = DFA.unpackEncodedString(DFA39_eotS);
    static final short[] DFA39_eof = DFA.unpackEncodedString(DFA39_eofS);
    static final char[] DFA39_min = DFA.unpackEncodedStringToUnsignedChars(DFA39_minS);
    static final char[] DFA39_max = DFA.unpackEncodedStringToUnsignedChars(DFA39_maxS);
    static final short[] DFA39_accept = DFA.unpackEncodedString(DFA39_acceptS);
    static final short[] DFA39_special = DFA.unpackEncodedString(DFA39_specialS);
    static final short[][] DFA39_transition;

    static {
        int numStates = DFA39_transitionS.length;
        DFA39_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA39_transition[i] = DFA.unpackEncodedString(DFA39_transitionS[i]);
        }
    }

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = DFA39_eot;
            this.eof = DFA39_eof;
            this.min = DFA39_min;
            this.max = DFA39_max;
            this.accept = DFA39_accept;
            this.special = DFA39_special;
            this.transition = DFA39_transition;
        }
        public String getDescription() {
            return "539:1: lineStatement : ( emptyStatement | statementExpression | returnStatement | breakStatement | continueStatement | throwStatement | {...}? => actionContextStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA39_3 = input.LA(1);

                         
                        int index39_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA39_3==Identifier||LA39_3==NullLiteral||(LA39_3>=164 && LA39_3<=212)) && (( isInActionContextBlock() ))) {s = 10;}

                        else if ( (LA39_3==LBRACE||(LA39_3>=DOT && LA39_3<=ANNOTATE)||(LA39_3>=INCR && LA39_3<=MOD_EQUAL)||LA39_3==LPAREN||LA39_3==LBRACK) ) {s = 2;}

                         
                        input.seek(index39_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA39_9 = input.LA(1);

                         
                        int index39_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA39_9==LBRACE||(LA39_9>=DOT && LA39_9<=ANNOTATE)||(LA39_9>=INCR && LA39_9<=MOD_EQUAL)||LA39_9==LPAREN||LA39_9==LBRACK) ) {s = 2;}

                        else if ( (LA39_9==Identifier||LA39_9==NullLiteral||(LA39_9>=164 && LA39_9<=212)) && (( isInActionContextBlock() ))) {s = 10;}

                         
                        input.seek(index39_9);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA39_8 = input.LA(1);

                         
                        int index39_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA39_8==Identifier||LA39_8==NullLiteral||(LA39_8>=164 && LA39_8<=212)) && (( isInActionContextBlock() ))) {s = 10;}

                        else if ( (LA39_8==LBRACE||(LA39_8>=DOT && LA39_8<=ANNOTATE)||(LA39_8>=INCR && LA39_8<=MOD_EQUAL)||LA39_8==LPAREN||LA39_8==LBRACK) ) {s = 2;}

                         
                        input.seek(index39_8);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 39, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA41_eotS =
        "\50\uffff";
    static final String DFA41_eofS =
        "\50\uffff";
    static final String DFA41_minS =
        "\1\152\1\uffff\6\152\1\uffff\7\152\2\153\2\uffff\5\152\2\153\3"+
        "\uffff\1\152\2\153\1\0\2\152\1\0\2\152\1\uffff";
    static final String DFA41_maxS =
        "\1\u00e7\1\uffff\6\u00d4\1\uffff\2\u00d4\1\u00e7\6\u00d8\2\uffff"+
        "\1\u00e7\4\u00d4\2\153\3\uffff\1\u00d4\2\153\1\0\2\u00d4\1\0\2\u00d4"+
        "\1\uffff";
    static final String DFA41_acceptS =
        "\1\uffff\1\4\6\uffff\1\3\11\uffff\2\2\7\uffff\3\2\11\uffff\1\1";
    static final String DFA41_specialS =
        "\2\uffff\1\23\1\10\1\11\1\21\1\4\1\15\1\uffff\1\6\2\uffff\1\33"+
        "\1\5\1\22\1\24\1\7\1\2\3\uffff\1\1\1\17\1\30\1\26\1\16\1\12\3\uffff"+
        "\1\25\1\20\1\27\1\14\1\32\1\3\1\13\1\31\1\0\1\uffff}>";
    static final String[] DFA41_transitionS = {
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
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\32\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\31\1\35\21\uffff\1\30\3\uffff\1\32\7\uffff\1\33\32\uffff"+
            "\61\32\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\32\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\37\1\35\21\uffff\1\36\3\uffff\1\40\7\uffff\1\33\32\uffff"+
            "\61\40\3\uffff\1\34",
            "\1\41\35\uffff\1\33\116\uffff\1\34",
            "\1\41\35\uffff\1\33\116\uffff\1\34",
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
            "\1\42\26\uffff\1\43\42\uffff\61\43",
            "\1\44",
            "\1\44",
            "",
            "",
            "",
            "\1\45\26\uffff\1\46\42\uffff\61\46",
            "\1\44",
            "\1\44",
            "\1\uffff",
            "\1\31\22\uffff\1\30\3\uffff\1\32\42\uffff\61\32",
            "\1\31\22\uffff\1\30\3\uffff\1\32\42\uffff\61\32",
            "\1\uffff",
            "\1\37\22\uffff\1\36\3\uffff\1\40\42\uffff\61\40",
            "\1\37\22\uffff\1\36\3\uffff\1\40\42\uffff\61\40",
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
            return "()* loopback of 559:3: ( ({...}? actionContextStatement )=> actionContextStatement | ( type identifier )=> localVariableDeclaration | statement )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA41_38 = input.LA(1);

                         
                        int index41_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_38==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA41_38==NullLiteral||(LA41_38>=164 && LA41_38<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA41_38==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index41_38);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA41_21 = input.LA(1);

                         
                        int index41_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_21==LBRACE||LA41_21==ANNOTATE||(LA41_21>=INCR && LA41_21<=MOD_EQUAL)||LA41_21==LPAREN) ) {s = 8;}

                        else if ( (LA41_21==LBRACK) ) {s = 11;}

                        else if ( (LA41_21==DOT) ) {s = 10;}

                        else if ( (LA41_21==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_21==NullLiteral||(LA41_21>=164 && LA41_21<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_21);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA41_17 = input.LA(1);

                         
                        int index41_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_17==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                        else if ( (LA41_17==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_17==216) && (synpred7_Rules())) {s = 28;}

                         
                        input.seek(index41_17);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA41_35 = input.LA(1);

                         
                        int index41_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_35==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA41_35==NullLiteral||(LA41_35>=164 && LA41_35<=212)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA41_35==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index41_35);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA41_6 = input.LA(1);

                         
                        int index41_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_6==DOT) ) {s = 10;}

                        else if ( (LA41_6==LBRACK) ) {s = 11;}

                        else if ( (LA41_6==LBRACE||LA41_6==ANNOTATE||(LA41_6>=INCR && LA41_6<=MOD_EQUAL)||LA41_6==LPAREN) ) {s = 8;}

                        else if ( (LA41_6==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_6==NullLiteral||(LA41_6>=164 && LA41_6<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA41_13 = input.LA(1);

                         
                        int index41_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_13==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA41_13==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA41_13==NullLiteral||(LA41_13>=164 && LA41_13<=212)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA41_13==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_13==216) && (synpred7_Rules())) {s = 28;}

                        else if ( (LA41_13==SEMICOLON) && (synpred7_Rules())) {s = 29;}

                         
                        input.seek(index41_13);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA41_9 = input.LA(1);

                         
                        int index41_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_9==DOT) ) {s = 10;}

                        else if ( (LA41_9==LBRACK) ) {s = 11;}

                        else if ( (LA41_9==LBRACE||LA41_9==ANNOTATE||(LA41_9>=INCR && LA41_9<=MOD_EQUAL)||LA41_9==LPAREN) ) {s = 8;}

                        else if ( (LA41_9==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_9==NullLiteral||(LA41_9>=164 && LA41_9<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_9);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA41_16 = input.LA(1);

                         
                        int index41_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_16==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_16==216) && (synpred7_Rules())) {s = 28;}

                        else if ( (LA41_16==SEMICOLON) && (( isInActionContextBlock() ))) {s = 33;}

                         
                        input.seek(index41_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA41_3 = input.LA(1);

                         
                        int index41_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_3==DOT) ) {s = 10;}

                        else if ( (LA41_3==LBRACK) ) {s = 11;}

                        else if ( (LA41_3==LBRACE||LA41_3==ANNOTATE||(LA41_3>=INCR && LA41_3<=MOD_EQUAL)||LA41_3==LPAREN) ) {s = 8;}

                        else if ( (LA41_3==Identifier) && (( isInActionContextBlock() ))) {s = 14;}

                        else if ( (LA41_3==NullLiteral||(LA41_3>=164 && LA41_3<=212)) && (( isInActionContextBlock() ))) {s = 15;}

                         
                        input.seek(index41_3);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA41_4 = input.LA(1);

                         
                        int index41_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_4==DOT) ) {s = 10;}

                        else if ( (LA41_4==LBRACK) ) {s = 11;}

                        else if ( (LA41_4==LBRACE||LA41_4==ANNOTATE||(LA41_4>=INCR && LA41_4<=MOD_EQUAL)||LA41_4==LPAREN) ) {s = 8;}

                        else if ( (LA41_4==Identifier) && (( isInActionContextBlock() ))) {s = 16;}

                        else if ( (LA41_4==NullLiteral||(LA41_4>=164 && LA41_4<=212)) && (( isInActionContextBlock() ))) {s = 17;}

                         
                        input.seek(index41_4);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA41_26 = input.LA(1);

                         
                        int index41_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_26==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index41_26);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA41_36 = input.LA(1);

                         
                        int index41_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_Rules()) ) {s = 39;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index41_36);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA41_33 = input.LA(1);

                         
                        int index41_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_Rules()) ) {s = 39;}

                        else if ( (synpred7_Rules()) ) {s = 29;}

                        else if ( (( isInActionContextBlock() )) ) {s = 8;}

                         
                        input.seek(index41_33);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA41_7 = input.LA(1);

                         
                        int index41_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_7==LBRACK) ) {s = 20;}

                        else if ( (LA41_7==LBRACE) ) {s = 8;}

                        else if ( (LA41_7==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_7==NullLiteral||(LA41_7>=164 && LA41_7<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_7);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA41_25 = input.LA(1);

                         
                        int index41_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_25==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index41_25);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA41_22 = input.LA(1);

                         
                        int index41_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_22==LBRACE||LA41_22==ANNOTATE||(LA41_22>=INCR && LA41_22<=MOD_EQUAL)||LA41_22==LPAREN) ) {s = 8;}

                        else if ( (LA41_22==DOT) ) {s = 10;}

                        else if ( (LA41_22==LBRACK) ) {s = 11;}

                        else if ( (LA41_22==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_22==NullLiteral||(LA41_22>=164 && LA41_22<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_22);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA41_31 = input.LA(1);

                         
                        int index41_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_31==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index41_31);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA41_5 = input.LA(1);

                         
                        int index41_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_5==DOT) ) {s = 10;}

                        else if ( (LA41_5==LBRACK) ) {s = 11;}

                        else if ( (LA41_5==LBRACE||LA41_5==ANNOTATE||(LA41_5>=INCR && LA41_5<=MOD_EQUAL)||LA41_5==LPAREN) ) {s = 8;}

                        else if ( (LA41_5==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_5==NullLiteral||(LA41_5>=164 && LA41_5<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_5);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA41_14 = input.LA(1);

                         
                        int index41_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_14==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_14==216) && (synpred7_Rules())) {s = 28;}

                        else if ( (LA41_14==SEMICOLON) && (synpred7_Rules())) {s = 29;}

                        else if ( (LA41_14==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA41_14==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA41_14==NullLiteral||(LA41_14>=164 && LA41_14<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                         
                        input.seek(index41_14);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA41_2 = input.LA(1);

                         
                        int index41_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_2==DOT) ) {s = 10;}

                        else if ( (LA41_2==LBRACK) ) {s = 11;}

                        else if ( (LA41_2==LBRACE||LA41_2==ANNOTATE||(LA41_2>=INCR && LA41_2<=MOD_EQUAL)||LA41_2==LPAREN) ) {s = 8;}

                        else if ( (LA41_2==Identifier) && (( isInActionContextBlock() ))) {s = 12;}

                        else if ( (LA41_2==NullLiteral||(LA41_2>=164 && LA41_2<=212)) && (( isInActionContextBlock() ))) {s = 13;}

                         
                        input.seek(index41_2);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA41_15 = input.LA(1);

                         
                        int index41_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_15==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                        else if ( (LA41_15==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA41_15==NullLiteral||(LA41_15>=164 && LA41_15<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA41_15==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_15==216) && (synpred7_Rules())) {s = 28;}

                        else if ( (LA41_15==SEMICOLON) && (synpred7_Rules())) {s = 29;}

                         
                        input.seek(index41_15);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA41_30 = input.LA(1);

                         
                        int index41_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_30==Identifier) && (( isInActionContextBlock() ))) {s = 37;}

                        else if ( (LA41_30==NullLiteral||(LA41_30>=164 && LA41_30<=212)) && (( isInActionContextBlock() ))) {s = 38;}

                         
                        input.seek(index41_30);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA41_24 = input.LA(1);

                         
                        int index41_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_24==Identifier) && (( isInActionContextBlock() ))) {s = 34;}

                        else if ( (LA41_24==NullLiteral||(LA41_24>=164 && LA41_24<=212)) && (( isInActionContextBlock() ))) {s = 35;}

                         
                        input.seek(index41_24);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA41_32 = input.LA(1);

                         
                        int index41_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_32==SEMICOLON) && (( isInActionContextBlock() ))) {s = 36;}

                         
                        input.seek(index41_32);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA41_23 = input.LA(1);

                         
                        int index41_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_23==LBRACE||LA41_23==LBRACK) ) {s = 8;}

                        else if ( (LA41_23==Identifier) && (synpred7_Rules())) {s = 18;}

                        else if ( (LA41_23==NullLiteral||(LA41_23>=164 && LA41_23<=212)) && (synpred7_Rules())) {s = 19;}

                         
                        input.seek(index41_23);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA41_37 = input.LA(1);

                         
                        int index41_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_37==Identifier) && (( isInActionContextBlock() ))) {s = 31;}

                        else if ( (LA41_37==NullLiteral||(LA41_37>=164 && LA41_37<=212)) && (( isInActionContextBlock() ))) {s = 32;}

                        else if ( (LA41_37==DOT) && (( isInActionContextBlock() ))) {s = 30;}

                         
                        input.seek(index41_37);
                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA41_34 = input.LA(1);

                         
                        int index41_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_34==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA41_34==NullLiteral||(LA41_34>=164 && LA41_34<=212)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA41_34==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                         
                        input.seek(index41_34);
                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA41_12 = input.LA(1);

                         
                        int index41_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA41_12==DOT) && (( isInActionContextBlock() ))) {s = 24;}

                        else if ( (LA41_12==Identifier) && (( isInActionContextBlock() ))) {s = 25;}

                        else if ( (LA41_12==NullLiteral||(LA41_12>=164 && LA41_12<=212)) && (( isInActionContextBlock() ))) {s = 26;}

                        else if ( (LA41_12==ASSIGN) && (synpred7_Rules())) {s = 27;}

                        else if ( (LA41_12==216) && (synpred7_Rules())) {s = 28;}

                        else if ( (LA41_12==SEMICOLON) && (synpred7_Rules())) {s = 29;}

                         
                        input.seek(index41_12);
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
 

    public static final BitSet FOLLOW_compilationUnit_in_startRule409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HeaderSection_in_compilationUnit424 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_ruleFunctionDeclaration_in_compilationUnit430 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF800000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_mappingBlock_in_compilationUnit432 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_ruleDeclaration_in_compilationUnit463 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF800000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_mappingBlock_in_compilationUnit465 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_ruleTemplateDeclaration_in_compilationUnit496 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF800000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_mappingBlock_in_compilationUnit498 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000038F0009FFFFFL});
    public static final BitSet FOLLOW_EOF_in_compilationUnit527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_163_in_mappingBlock538 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0x01FFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_set_in_mappingBlock541 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0xFFFFFFFFFFFFFFFFL,0x01FFFFFFFFFFFFFFL});
    public static final BitSet FOLLOW_MappingEnd_in_mappingBlock546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicateStatement_in_conditionBlock558 = new BitSet(new long[]{0x0000000000000000L,0x91BFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_EOF_in_conditionBlock561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatements_in_actionBlock573 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_actionBlock575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_standaloneExpression587 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_standaloneExpression589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_standaloneThenStatement602 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_standaloneThenStatement604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatements_in_ruleFunctionBody616 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_ruleFunctionBody618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_identifier631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keywordIdentifier_in_identifier635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keywordIdentifier0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_213_in_xsltLiteral877 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_214_in_xsltLiteral879 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_214_in_xsltLiteral881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_235_in_ruleDeclaration893 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_ruleDeclaration895 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleDeclaration898 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0001850000000000L});
    public static final BitSet FOLLOW_ruleNT_in_ruleDeclaration900 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleDeclaration902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_236_in_ruleTemplateDeclaration936 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_ruleTemplateDeclaration938 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleTemplateDeclaration941 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_ruletemplateNT_in_ruleTemplateDeclaration943 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleTemplateDeclaration945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_172_in_ruleFunctionDeclaration980 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x000020F0009FFFFFL});
    public static final BitSet FOLLOW_returnType_in_ruleFunctionDeclaration986 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_237_in_ruleFunctionDeclaration989 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_ruleFunctionDeclaration991 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_LBRACE_in_ruleFunctionDeclaration994 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000001000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_ruleFunctionNT_in_ruleFunctionDeclaration996 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_ruleFunctionDeclaration998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameOrPrimitiveType_in_returnType1054 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_LBRACK_in_returnType1060 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_returnType1063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_voidLiteral_in_returnType1075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_215_in_voidLiteral1086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributeNT_in_ruleNT1112 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001850000000000L});
    public static final BitSet FOLLOW_declareNT_in_ruleNT1117 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001850000000000L});
    public static final BitSet FOLLOW_whenNT_in_ruleNT1122 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001850000000000L});
    public static final BitSet FOLLOW_thenNT_in_ruleNT1127 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001850000000000L});
    public static final BitSet FOLLOW_attributeNT_in_ruleFunctionNT1143 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_scopeNT_in_ruleFunctionNT1148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_bodyNT_in_ruleFunctionNT1153 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L,0x0001020000000000L});
    public static final BitSet FOLLOW_attributeNT_in_ruletemplateNT1167 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_bindingViewsNT_in_ruletemplateNT1172 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_bindingsNT_in_ruletemplateNT1177 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_displayNT_in_ruletemplateNT1182 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_variablesNT_in_ruletemplateNT1187 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_preConditionsNT_in_ruletemplateNT1192 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_actionContextNT_in_ruletemplateNT1197 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0001C10000070000L});
    public static final BitSet FOLLOW_208_in_bindingViewsNT1210 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_bindingViewsNTBlock_in_bindingViewsNT1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_bindingViewsNTBlock1234 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_bindingViewStatement_in_bindingViewsNTBlock1238 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_bindingViewsNTBlock1243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_bindingViewStatement1275 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_bindingViewStatement1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_209_in_bindingsNT1306 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_bindingsNTBlock_in_bindingsNT1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_bindingsNTBlock1330 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_bindingStatement_in_bindingsNTBlock1334 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_bindingsNTBlock1339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameOrPrimitiveType_in_bindingStatement1373 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_bindingStatement1379 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000020200L});
    public static final BitSet FOLLOW_ASSIGN_in_bindingStatement1387 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_bindingStatement1403 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_expression_in_bindingStatement1408 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_domainModelStatement_in_bindingStatement1413 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_bindingStatement1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_domainModelStatement1478 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_domainModelStatement1482 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_RPAREN_in_domainModelStatement1487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_238_in_actionContextNT1515 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_actionContextNTBlock_in_actionContextNT1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_actionContextNTBlock1539 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_actionContextStatements_in_actionContextNTBlock1543 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_actionContextNTBlock1547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_acStatement_in_actionContextStatements1577 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_acStatement1590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_acStatement1594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_207_in_actionContextStatement1609 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_methodName_in_actionContextStatement1613 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement1617 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_205_in_actionContextStatement1629 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_actionContextStatement1635 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement1642 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_206_in_actionContextStatement1652 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_actionContextStatement1656 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_actionContextStatement1663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_239_in_preConditionsNT1713 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_preConditionsNTBlock_in_preConditionsNT1715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_preConditionsNTBlock1737 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_preconditionStatements_in_preConditionsNTBlock1741 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_preConditionsNTBlock1745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_preconditionStatement_in_preconditionStatements1776 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_predicateStatement_in_preconditionStatement1795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_preconditionStatement1799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_210_in_displayNT1811 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_displayNTBlock_in_displayNT1813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_displayNTBlock1835 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_displayProperty_in_displayNTBlock1839 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_displayNTBlock1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_displayProperty1871 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_displayProperty1874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_displayProperty1876 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_displayProperty1878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_232_in_variablesNT1910 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_variablesNTBlock_in_variablesNT1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_variablesNTBlock1933 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_templateDeclarator_in_variablesNTBlock1937 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_variablesNTBlock1940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameOrPrimitiveType_in_templateDeclarator1973 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_templateDeclarator1979 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_templateDeclarator1987 = new BitSet(new long[]{0x0000000000000000L,0x91BFC40000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_templateDeclarator2003 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_expression_in_templateDeclarator2008 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_templateDeclarator2013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_240_in_attributeNT2066 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_attributeNTBlock_in_attributeNT2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_attributeNTBlock2089 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x00000C2000000000L,0x003E000000000000L});
    public static final BitSet FOLLOW_attributeBodyDeclaration_in_attributeNTBlock2098 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x00000C2000000000L,0x003E000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_attributeNTBlock2101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_241_in_attributeBodyDeclaration2131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x000000000000000CL});
    public static final BitSet FOLLOW_integerLiteral_in_attributeBodyDeclaration2137 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_242_in_attributeBodyDeclaration2159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2161 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_attributeBodyDeclaration2163 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_243_in_attributeBodyDeclaration2185 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_attributeBodyDeclaration2189 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_244_in_attributeBodyDeclaration2211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_booleanLiteral_in_attributeBodyDeclaration2215 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_245_in_attributeBodyDeclaration2236 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_attributeBodyDeclaration2240 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_attributeBodyDeclaration2262 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x01C0000000000000L});
    public static final BitSet FOLLOW_validityType_in_attributeBodyDeclaration2268 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_170_in_attributeBodyDeclaration2290 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2292 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_StringLiteral_in_attributeBodyDeclaration2294 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_171_in_attributeBodyDeclaration2315 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGN_in_attributeBodyDeclaration2317 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_name_in_attributeBodyDeclaration2323 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_attributeBodyDeclaration2328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_validityType0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_232_in_declareNT2375 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_declareNTBlock_in_declareNT2377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_declareNTBlock2398 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_declarator_in_declareNTBlock2402 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_declareNTBlock2405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_233_in_scopeNT2434 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_scopeNTBlock_in_scopeNT2436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_scopeNTBlock2456 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_scopeDeclarator_in_scopeNTBlock2460 = new BitSet(new long[]{0x0000000000000000L,0x0040040000000000L,0xFFFFFFF000000002L,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_scopeNTBlock2463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameOrPrimitiveType_in_scopeDeclarator2497 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000080002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_LBRACK_in_scopeDeclarator2506 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RBRACK_in_scopeDeclarator2508 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_scopeDeclarator2517 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_scopeDeclarator2519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_in_declarator2565 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_declarator2572 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_SEMICOLON_in_declarator2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_239_in_whenNT2615 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_whenNTBlock_in_whenNT2617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_whenNTBlock2637 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F0001FFFFFL});
    public static final BitSet FOLLOW_predicateStatements_in_whenNTBlock2641 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_whenNTBlock2645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_234_in_thenNT2677 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_thenNTBlock_in_thenNT2679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_thenNTBlock2700 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_thenStatements_in_thenNTBlock2704 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_thenNTBlock2709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_164_in_bodyNT2739 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_bodyNTBlock_in_bodyNT2741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_bodyNTBlock2762 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_thenStatements_in_bodyNTBlock2766 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_RBRACE_in_bodyNTBlock2771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_thenStatement_in_thenStatements2802 = new BitSet(new long[]{0x0000000000000002L,0x91BFCC0000000000L,0xFFFFFFF0000A007FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_thenStatement2822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localVariableDeclaration_in_thenStatement2833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_thenStatement2838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyBodyStatement_in_thenStatement2844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lineStatement_in_statement2856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blockStatement_in_statement2860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStatement_in_lineStatement2872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementExpression_in_lineStatement2877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_lineStatement2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakStatement_in_lineStatement2887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continueStatement_in_lineStatement2892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwStatement_in_lineStatement2897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_lineStatement2906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifRule_in_blockStatement2918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileRule_in_blockStatement2923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forRule_in_blockStatement2928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_blockStatement2933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tryCatchFinally_in_blockStatement2938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACE_in_block2950 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_actionContextStatement_in_block2966 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_localVariableDeclaration_in_block2977 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_statement_in_block2981 = new BitSet(new long[]{0x0000000000000000L,0x91FFCC0000000000L,0xFFFFFFF0000A003FL,0x000000F3BE1FFFFFL});
    public static final BitSet FOLLOW_RBRACE_in_block2985 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_synpred1_Rules1392 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_set_in_synpred1_Rules1394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicateStatement_in_synpred2_Rules1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localInitializerArrayLiteral_in_synpred3_Rules1992 = new BitSet(new long[]{0x0000000000000000L,0x0000080000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_set_in_synpred3_Rules1994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred4_Rules2818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred5_Rules2828 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred5_Rules2830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_actionContextStatement_in_synpred6_Rules2962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_synpred7_Rules2971 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L,0xFFFFFFF000000002L,0x00000000001FFFFFL});
    public static final BitSet FOLLOW_identifier_in_synpred7_Rules2973 = new BitSet(new long[]{0x0000000000000002L});

}