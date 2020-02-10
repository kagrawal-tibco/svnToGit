// $ANTLR 3.2 Sep 23, 2009 12:02:23 JXPath.g 2011-11-06 16:04:32

	package com.tibco.jxpath.compiler;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

/**
XPath parser, based on XPath v1 grammar defined by W3C.
http://www.w3.org/TR/xpath

*/
public class JXPathParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FUNCTION_CALL", "FILTER_PREDICATE_EXPR", "QUALIFIED_NAME", "VAR_REF", "SIMPLE_AXIS_STEP", "ABBREVIATED_STEP", "NAMED_AXIS_STEP", "PATH_EXPR", "CONSTANT_EXPR", "AXIS_SPECIFIER", "PREDICATES", "ARGUMENTS", "PATHSEP", "ABRPATH", "AxisName", "CC", "AT", "NodeType", "LPAR", "RPAR", "Literal", "LBRAC", "RBRAC", "DOT", "DOTDOT", "Number", "COMMA", "UNION", "OR", "AND", "EQUAL", "NOT_EQUAL", "LESS_THAN", "GREATER_THAN", "LESS_THAN_EQUALS", "GREATER_THAN_EQUALS", "PLUS", "MINUS", "STAR", "DIV", "MOD", "COLON", "VAR_START", "NCName", "Digits", "Whitespace", "NCNameStartChar", "NCNameChar", "'processing-instruction'"
    };
    public static final int NCName=47;
    public static final int STAR=42;
    public static final int AXIS_SPECIFIER=13;
    public static final int MOD=44;
    public static final int PATHSEP=16;
    public static final int LESS_THAN_EQUALS=38;
    public static final int FILTER_PREDICATE_EXPR=5;
    public static final int DOTDOT=28;
    public static final int AND=33;
    public static final int EOF=-1;
    public static final int VAR_START=46;
    public static final int NAMED_AXIS_STEP=10;
    public static final int AT=20;
    public static final int Literal=24;
    public static final int T__52=52;
    public static final int LPAR=22;
    public static final int Number=29;
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


        public JXPathParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public JXPathParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return JXPathParser.tokenNames; }
    public String getGrammarFileName() { return "JXPath.g"; }


    public static class main_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "main"
    // JXPath.g:34:1: main : expr ;
    public final JXPathParser.main_return main() throws RecognitionException {
        JXPathParser.main_return retval = new JXPathParser.main_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.expr_return expr1 = null;



        try {
            // JXPath.g:34:7: ( expr )
            // JXPath.g:34:10: expr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_expr_in_main92);
            expr1=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr1.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "main"

    public static class locationPath_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "locationPath"
    // JXPath.g:37:1: locationPath : ( relativeLocationPath | absoluteLocationPath );
    public final JXPathParser.locationPath_return locationPath() throws RecognitionException {
        JXPathParser.locationPath_return retval = new JXPathParser.locationPath_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.relativeLocationPath_return relativeLocationPath2 = null;

        JXPathParser.absoluteLocationPath_return absoluteLocationPath3 = null;



        try {
            // JXPath.g:38:3: ( relativeLocationPath | absoluteLocationPath )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==AxisName||(LA1_0>=AT && LA1_0<=NodeType)||(LA1_0>=DOT && LA1_0<=DOTDOT)||LA1_0==STAR||LA1_0==NCName||LA1_0==52) ) {
                alt1=1;
            }
            else if ( ((LA1_0>=PATHSEP && LA1_0<=ABRPATH)) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // JXPath.g:38:6: relativeLocationPath
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_relativeLocationPath_in_locationPath107);
                    relativeLocationPath2=relativeLocationPath();

                    state._fsp--;

                    adaptor.addChild(root_0, relativeLocationPath2.getTree());

                    }
                    break;
                case 2 :
                    // JXPath.g:39:6: absoluteLocationPath
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_absoluteLocationPath_in_locationPath114);
                    absoluteLocationPath3=absoluteLocationPath();

                    state._fsp--;

                    adaptor.addChild(root_0, absoluteLocationPath3.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "locationPath"

    public static class absoluteLocationPath_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "absoluteLocationPath"
    // JXPath.g:42:1: absoluteLocationPath : ( pathSep relativeLocationPath | abbreviatedAbsoluteLocationPath );
    public final JXPathParser.absoluteLocationPath_return absoluteLocationPath() throws RecognitionException {
        JXPathParser.absoluteLocationPath_return retval = new JXPathParser.absoluteLocationPath_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.pathSep_return pathSep4 = null;

        JXPathParser.relativeLocationPath_return relativeLocationPath5 = null;

        JXPathParser.abbreviatedAbsoluteLocationPath_return abbreviatedAbsoluteLocationPath6 = null;



        try {
            // JXPath.g:43:3: ( pathSep relativeLocationPath | abbreviatedAbsoluteLocationPath )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==PATHSEP) ) {
                alt2=1;
            }
            else if ( (LA2_0==ABRPATH) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // JXPath.g:43:6: pathSep relativeLocationPath
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pathSep_in_absoluteLocationPath128);
                    pathSep4=pathSep();

                    state._fsp--;

                    adaptor.addChild(root_0, pathSep4.getTree());
                    pushFollow(FOLLOW_relativeLocationPath_in_absoluteLocationPath130);
                    relativeLocationPath5=relativeLocationPath();

                    state._fsp--;

                    adaptor.addChild(root_0, relativeLocationPath5.getTree());

                    }
                    break;
                case 2 :
                    // JXPath.g:44:6: abbreviatedAbsoluteLocationPath
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_abbreviatedAbsoluteLocationPath_in_absoluteLocationPath137);
                    abbreviatedAbsoluteLocationPath6=abbreviatedAbsoluteLocationPath();

                    state._fsp--;

                    adaptor.addChild(root_0, abbreviatedAbsoluteLocationPath6.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "absoluteLocationPath"

    public static class abbreviatedAbsoluteLocationPath_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "abbreviatedAbsoluteLocationPath"
    // JXPath.g:47:1: abbreviatedAbsoluteLocationPath : abrPath relativeLocationPath ;
    public final JXPathParser.abbreviatedAbsoluteLocationPath_return abbreviatedAbsoluteLocationPath() throws RecognitionException {
        JXPathParser.abbreviatedAbsoluteLocationPath_return retval = new JXPathParser.abbreviatedAbsoluteLocationPath_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.abrPath_return abrPath7 = null;

        JXPathParser.relativeLocationPath_return relativeLocationPath8 = null;



        try {
            // JXPath.g:48:3: ( abrPath relativeLocationPath )
            // JXPath.g:48:5: abrPath relativeLocationPath
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_abrPath_in_abbreviatedAbsoluteLocationPath150);
            abrPath7=abrPath();

            state._fsp--;

            adaptor.addChild(root_0, abrPath7.getTree());
            pushFollow(FOLLOW_relativeLocationPath_in_abbreviatedAbsoluteLocationPath152);
            relativeLocationPath8=relativeLocationPath();

            state._fsp--;

            adaptor.addChild(root_0, relativeLocationPath8.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "abbreviatedAbsoluteLocationPath"

    public static class relativeLocationPath_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relativeLocationPath"
    // JXPath.g:51:1: relativeLocationPath : step ( simpleAxisStep step )* ;
    public final JXPathParser.relativeLocationPath_return relativeLocationPath() throws RecognitionException {
        JXPathParser.relativeLocationPath_return retval = new JXPathParser.relativeLocationPath_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.step_return step9 = null;

        JXPathParser.simpleAxisStep_return simpleAxisStep10 = null;

        JXPathParser.step_return step11 = null;



        try {
            // JXPath.g:52:3: ( step ( simpleAxisStep step )* )
            // JXPath.g:52:6: step ( simpleAxisStep step )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_step_in_relativeLocationPath168);
            step9=step();

            state._fsp--;

            adaptor.addChild(root_0, step9.getTree());
            // JXPath.g:52:11: ( simpleAxisStep step )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=PATHSEP && LA3_0<=ABRPATH)) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // JXPath.g:52:12: simpleAxisStep step
            	    {
            	    pushFollow(FOLLOW_simpleAxisStep_in_relativeLocationPath171);
            	    simpleAxisStep10=simpleAxisStep();

            	    state._fsp--;

            	    adaptor.addChild(root_0, simpleAxisStep10.getTree());
            	    pushFollow(FOLLOW_step_in_relativeLocationPath173);
            	    step11=step();

            	    state._fsp--;

            	    adaptor.addChild(root_0, step11.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relativeLocationPath"

    public static class simpleAxisStep_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simpleAxisStep"
    // JXPath.g:55:1: simpleAxisStep : ( pathSep | abrPath ) ;
    public final JXPathParser.simpleAxisStep_return simpleAxisStep() throws RecognitionException {
        JXPathParser.simpleAxisStep_return retval = new JXPathParser.simpleAxisStep_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.pathSep_return pathSep12 = null;

        JXPathParser.abrPath_return abrPath13 = null;



        try {
            // JXPath.g:56:5: ( ( pathSep | abrPath ) )
            // JXPath.g:56:7: ( pathSep | abrPath )
            {
            root_0 = (Object)adaptor.nil();

            // JXPath.g:56:7: ( pathSep | abrPath )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==PATHSEP) ) {
                alt4=1;
            }
            else if ( (LA4_0==ABRPATH) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // JXPath.g:56:9: pathSep
                    {
                    pushFollow(FOLLOW_pathSep_in_simpleAxisStep192);
                    pathSep12=pathSep();

                    state._fsp--;

                    adaptor.addChild(root_0, pathSep12.getTree());

                    }
                    break;
                case 2 :
                    // JXPath.g:57:9: abrPath
                    {
                    pushFollow(FOLLOW_abrPath_in_simpleAxisStep202);
                    abrPath13=abrPath();

                    state._fsp--;

                    adaptor.addChild(root_0, abrPath13.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "simpleAxisStep"

    public static class pathSep_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pathSep"
    // JXPath.g:61:1: pathSep : PATHSEP -> ^( SIMPLE_AXIS_STEP PATHSEP ) ;
    public final JXPathParser.pathSep_return pathSep() throws RecognitionException {
        JXPathParser.pathSep_return retval = new JXPathParser.pathSep_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PATHSEP14=null;

        Object PATHSEP14_tree=null;
        RewriteRuleTokenStream stream_PATHSEP=new RewriteRuleTokenStream(adaptor,"token PATHSEP");

        try {
            // JXPath.g:62:2: ( PATHSEP -> ^( SIMPLE_AXIS_STEP PATHSEP ) )
            // JXPath.g:62:4: PATHSEP
            {
            PATHSEP14=(Token)match(input,PATHSEP,FOLLOW_PATHSEP_in_pathSep228);  
            stream_PATHSEP.add(PATHSEP14);



            // AST REWRITE
            // elements: PATHSEP
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 62:15: -> ^( SIMPLE_AXIS_STEP PATHSEP )
            {
                // JXPath.g:62:18: ^( SIMPLE_AXIS_STEP PATHSEP )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SIMPLE_AXIS_STEP, "SIMPLE_AXIS_STEP"), root_1);

                adaptor.addChild(root_1, stream_PATHSEP.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pathSep"

    public static class abrPath_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "abrPath"
    // JXPath.g:65:1: abrPath : ABRPATH -> ^( SIMPLE_AXIS_STEP ABRPATH ) ;
    public final JXPathParser.abrPath_return abrPath() throws RecognitionException {
        JXPathParser.abrPath_return retval = new JXPathParser.abrPath_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ABRPATH15=null;

        Object ABRPATH15_tree=null;
        RewriteRuleTokenStream stream_ABRPATH=new RewriteRuleTokenStream(adaptor,"token ABRPATH");

        try {
            // JXPath.g:66:2: ( ABRPATH -> ^( SIMPLE_AXIS_STEP ABRPATH ) )
            // JXPath.g:66:4: ABRPATH
            {
            ABRPATH15=(Token)match(input,ABRPATH,FOLLOW_ABRPATH_in_abrPath251);  
            stream_ABRPATH.add(ABRPATH15);



            // AST REWRITE
            // elements: ABRPATH
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 66:12: -> ^( SIMPLE_AXIS_STEP ABRPATH )
            {
                // JXPath.g:66:15: ^( SIMPLE_AXIS_STEP ABRPATH )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SIMPLE_AXIS_STEP, "SIMPLE_AXIS_STEP"), root_1);

                adaptor.addChild(root_1, stream_ABRPATH.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "abrPath"

    public static class step_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "step"
    // JXPath.g:69:1: step : ( namedAxisStep | abbreviatedStep );
    public final JXPathParser.step_return step() throws RecognitionException {
        JXPathParser.step_return retval = new JXPathParser.step_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.namedAxisStep_return namedAxisStep16 = null;

        JXPathParser.abbreviatedStep_return abbreviatedStep17 = null;



        try {
            // JXPath.g:69:7: ( namedAxisStep | abbreviatedStep )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==AxisName||(LA5_0>=AT && LA5_0<=NodeType)||LA5_0==STAR||LA5_0==NCName||LA5_0==52) ) {
                alt5=1;
            }
            else if ( ((LA5_0>=DOT && LA5_0<=DOTDOT)) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // JXPath.g:69:10: namedAxisStep
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_namedAxisStep_in_step272);
                    namedAxisStep16=namedAxisStep();

                    state._fsp--;

                    adaptor.addChild(root_0, namedAxisStep16.getTree());

                    }
                    break;
                case 2 :
                    // JXPath.g:70:6: abbreviatedStep
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_abbreviatedStep_in_step279);
                    abbreviatedStep17=abbreviatedStep();

                    state._fsp--;

                    adaptor.addChild(root_0, abbreviatedStep17.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "step"

    public static class namedAxisStep_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "namedAxisStep"
    // JXPath.g:73:1: namedAxisStep : ( axisSpecifier )? nodeTest ( predicates )? -> ^( NAMED_AXIS_STEP ( axisSpecifier )? nodeTest ( ^( PREDICATES predicates ) )? ) ;
    public final JXPathParser.namedAxisStep_return namedAxisStep() throws RecognitionException {
        JXPathParser.namedAxisStep_return retval = new JXPathParser.namedAxisStep_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.axisSpecifier_return axisSpecifier18 = null;

        JXPathParser.nodeTest_return nodeTest19 = null;

        JXPathParser.predicates_return predicates20 = null;


        RewriteRuleSubtreeStream stream_predicates=new RewriteRuleSubtreeStream(adaptor,"rule predicates");
        RewriteRuleSubtreeStream stream_nodeTest=new RewriteRuleSubtreeStream(adaptor,"rule nodeTest");
        RewriteRuleSubtreeStream stream_axisSpecifier=new RewriteRuleSubtreeStream(adaptor,"rule axisSpecifier");
        try {
            // JXPath.g:74:5: ( ( axisSpecifier )? nodeTest ( predicates )? -> ^( NAMED_AXIS_STEP ( axisSpecifier )? nodeTest ( ^( PREDICATES predicates ) )? ) )
            // JXPath.g:74:7: ( axisSpecifier )? nodeTest ( predicates )?
            {
            // JXPath.g:74:7: ( axisSpecifier )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==AxisName) ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==CC) ) {
                    alt6=1;
                }
            }
            else if ( (LA6_0==AT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // JXPath.g:74:7: axisSpecifier
                    {
                    pushFollow(FOLLOW_axisSpecifier_in_namedAxisStep294);
                    axisSpecifier18=axisSpecifier();

                    state._fsp--;

                    stream_axisSpecifier.add(axisSpecifier18.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_nodeTest_in_namedAxisStep297);
            nodeTest19=nodeTest();

            state._fsp--;

            stream_nodeTest.add(nodeTest19.getTree());
            // JXPath.g:74:31: ( predicates )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LBRAC) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // JXPath.g:74:31: predicates
                    {
                    pushFollow(FOLLOW_predicates_in_namedAxisStep299);
                    predicates20=predicates();

                    state._fsp--;

                    stream_predicates.add(predicates20.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: axisSpecifier, predicates, nodeTest
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 74:43: -> ^( NAMED_AXIS_STEP ( axisSpecifier )? nodeTest ( ^( PREDICATES predicates ) )? )
            {
                // JXPath.g:74:46: ^( NAMED_AXIS_STEP ( axisSpecifier )? nodeTest ( ^( PREDICATES predicates ) )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(NAMED_AXIS_STEP, "NAMED_AXIS_STEP"), root_1);

                // JXPath.g:74:64: ( axisSpecifier )?
                if ( stream_axisSpecifier.hasNext() ) {
                    adaptor.addChild(root_1, stream_axisSpecifier.nextTree());

                }
                stream_axisSpecifier.reset();
                adaptor.addChild(root_1, stream_nodeTest.nextTree());
                // JXPath.g:74:88: ( ^( PREDICATES predicates ) )?
                if ( stream_predicates.hasNext() ) {
                    // JXPath.g:74:88: ^( PREDICATES predicates )
                    {
                    Object root_2 = (Object)adaptor.nil();
                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDICATES, "PREDICATES"), root_2);

                    adaptor.addChild(root_2, stream_predicates.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_predicates.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "namedAxisStep"

    public static class predicates_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicates"
    // JXPath.g:76:1: predicates : ( predicate )+ ;
    public final JXPathParser.predicates_return predicates() throws RecognitionException {
        JXPathParser.predicates_return retval = new JXPathParser.predicates_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.predicate_return predicate21 = null;



        try {
            // JXPath.g:77:2: ( ( predicate )+ )
            // JXPath.g:77:4: ( predicate )+
            {
            root_0 = (Object)adaptor.nil();

            // JXPath.g:77:4: ( predicate )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==LBRAC) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // JXPath.g:77:4: predicate
            	    {
            	    pushFollow(FOLLOW_predicate_in_predicates331);
            	    predicate21=predicate();

            	    state._fsp--;

            	    adaptor.addChild(root_0, predicate21.getTree());

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


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicates"

    public static class axisSpecifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "axisSpecifier"
    // JXPath.g:80:1: axisSpecifier : ( AxisName CC | AT ) -> ^( AXIS_SPECIFIER ( AxisName )? ( CC )? ( AT )? ) ;
    public final JXPathParser.axisSpecifier_return axisSpecifier() throws RecognitionException {
        JXPathParser.axisSpecifier_return retval = new JXPathParser.axisSpecifier_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AxisName22=null;
        Token CC23=null;
        Token AT24=null;

        Object AxisName22_tree=null;
        Object CC23_tree=null;
        Object AT24_tree=null;
        RewriteRuleTokenStream stream_AT=new RewriteRuleTokenStream(adaptor,"token AT");
        RewriteRuleTokenStream stream_AxisName=new RewriteRuleTokenStream(adaptor,"token AxisName");
        RewriteRuleTokenStream stream_CC=new RewriteRuleTokenStream(adaptor,"token CC");

        try {
            // JXPath.g:81:3: ( ( AxisName CC | AT ) -> ^( AXIS_SPECIFIER ( AxisName )? ( CC )? ( AT )? ) )
            // JXPath.g:81:6: ( AxisName CC | AT )
            {
            // JXPath.g:81:6: ( AxisName CC | AT )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==AxisName) ) {
                alt9=1;
            }
            else if ( (LA9_0==AT) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // JXPath.g:81:7: AxisName CC
                    {
                    AxisName22=(Token)match(input,AxisName,FOLLOW_AxisName_in_axisSpecifier347);  
                    stream_AxisName.add(AxisName22);

                    CC23=(Token)match(input,CC,FOLLOW_CC_in_axisSpecifier349);  
                    stream_CC.add(CC23);


                    }
                    break;
                case 2 :
                    // JXPath.g:82:6: AT
                    {
                    AT24=(Token)match(input,AT,FOLLOW_AT_in_axisSpecifier356);  
                    stream_AT.add(AT24);


                    }
                    break;

            }



            // AST REWRITE
            // elements: CC, AxisName, AT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 82:10: -> ^( AXIS_SPECIFIER ( AxisName )? ( CC )? ( AT )? )
            {
                // JXPath.g:82:13: ^( AXIS_SPECIFIER ( AxisName )? ( CC )? ( AT )? )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(AXIS_SPECIFIER, "AXIS_SPECIFIER"), root_1);

                // JXPath.g:82:30: ( AxisName )?
                if ( stream_AxisName.hasNext() ) {
                    adaptor.addChild(root_1, stream_AxisName.nextNode());

                }
                stream_AxisName.reset();
                // JXPath.g:82:40: ( CC )?
                if ( stream_CC.hasNext() ) {
                    adaptor.addChild(root_1, stream_CC.nextNode());

                }
                stream_CC.reset();
                // JXPath.g:82:44: ( AT )?
                if ( stream_AT.hasNext() ) {
                    adaptor.addChild(root_1, stream_AT.nextNode());

                }
                stream_AT.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "axisSpecifier"

    public static class nodeTest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nodeTest"
    // JXPath.g:85:1: nodeTest : ( nameTest | NodeType LPAR RPAR | 'processing-instruction' LPAR Literal RPAR );
    public final JXPathParser.nodeTest_return nodeTest() throws RecognitionException {
        JXPathParser.nodeTest_return retval = new JXPathParser.nodeTest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token NodeType26=null;
        Token LPAR27=null;
        Token RPAR28=null;
        Token string_literal29=null;
        Token LPAR30=null;
        Token Literal31=null;
        Token RPAR32=null;
        JXPathParser.nameTest_return nameTest25 = null;


        Object NodeType26_tree=null;
        Object LPAR27_tree=null;
        Object RPAR28_tree=null;
        Object string_literal29_tree=null;
        Object LPAR30_tree=null;
        Object Literal31_tree=null;
        Object RPAR32_tree=null;

        try {
            // JXPath.g:85:9: ( nameTest | NodeType LPAR RPAR | 'processing-instruction' LPAR Literal RPAR )
            int alt10=3;
            switch ( input.LA(1) ) {
            case AxisName:
            case STAR:
            case NCName:
                {
                alt10=1;
                }
                break;
            case NodeType:
                {
                alt10=2;
                }
                break;
            case 52:
                {
                alt10=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // JXPath.g:85:12: nameTest
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_nameTest_in_nodeTest383);
                    nameTest25=nameTest();

                    state._fsp--;

                    adaptor.addChild(root_0, nameTest25.getTree());

                    }
                    break;
                case 2 :
                    // JXPath.g:86:6: NodeType LPAR RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    NodeType26=(Token)match(input,NodeType,FOLLOW_NodeType_in_nodeTest390); 
                    NodeType26_tree = (Object)adaptor.create(NodeType26);
                    adaptor.addChild(root_0, NodeType26_tree);

                    LPAR27=(Token)match(input,LPAR,FOLLOW_LPAR_in_nodeTest392); 
                    LPAR27_tree = (Object)adaptor.create(LPAR27);
                    adaptor.addChild(root_0, LPAR27_tree);

                    RPAR28=(Token)match(input,RPAR,FOLLOW_RPAR_in_nodeTest394); 
                    RPAR28_tree = (Object)adaptor.create(RPAR28);
                    adaptor.addChild(root_0, RPAR28_tree);


                    }
                    break;
                case 3 :
                    // JXPath.g:87:6: 'processing-instruction' LPAR Literal RPAR
                    {
                    root_0 = (Object)adaptor.nil();

                    string_literal29=(Token)match(input,52,FOLLOW_52_in_nodeTest401); 
                    string_literal29_tree = (Object)adaptor.create(string_literal29);
                    adaptor.addChild(root_0, string_literal29_tree);

                    LPAR30=(Token)match(input,LPAR,FOLLOW_LPAR_in_nodeTest403); 
                    LPAR30_tree = (Object)adaptor.create(LPAR30);
                    adaptor.addChild(root_0, LPAR30_tree);

                    Literal31=(Token)match(input,Literal,FOLLOW_Literal_in_nodeTest405); 
                    Literal31_tree = (Object)adaptor.create(Literal31);
                    adaptor.addChild(root_0, Literal31_tree);

                    RPAR32=(Token)match(input,RPAR,FOLLOW_RPAR_in_nodeTest407); 
                    RPAR32_tree = (Object)adaptor.create(RPAR32);
                    adaptor.addChild(root_0, RPAR32_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nodeTest"

    public static class predicate_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "predicate"
    // JXPath.g:90:1: predicate : LBRAC expr RBRAC ;
    public final JXPathParser.predicate_return predicate() throws RecognitionException {
        JXPathParser.predicate_return retval = new JXPathParser.predicate_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LBRAC33=null;
        Token RBRAC35=null;
        JXPathParser.expr_return expr34 = null;


        Object LBRAC33_tree=null;
        Object RBRAC35_tree=null;

        try {
            // JXPath.g:91:3: ( LBRAC expr RBRAC )
            // JXPath.g:91:6: LBRAC expr RBRAC
            {
            root_0 = (Object)adaptor.nil();

            LBRAC33=(Token)match(input,LBRAC,FOLLOW_LBRAC_in_predicate421); 
            pushFollow(FOLLOW_expr_in_predicate424);
            expr34=expr();

            state._fsp--;

            adaptor.addChild(root_0, expr34.getTree());
            RBRAC35=(Token)match(input,RBRAC,FOLLOW_RBRAC_in_predicate426); 

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "predicate"

    public static class abbreviatedStep_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "abbreviatedStep"
    // JXPath.g:94:1: abbreviatedStep : ( DOT | DOTDOT ) -> ^( ABBREVIATED_STEP ) ;
    public final JXPathParser.abbreviatedStep_return abbreviatedStep() throws RecognitionException {
        JXPathParser.abbreviatedStep_return retval = new JXPathParser.abbreviatedStep_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token DOT36=null;
        Token DOTDOT37=null;

        Object DOT36_tree=null;
        Object DOTDOT37_tree=null;
        RewriteRuleTokenStream stream_DOTDOT=new RewriteRuleTokenStream(adaptor,"token DOTDOT");
        RewriteRuleTokenStream stream_DOT=new RewriteRuleTokenStream(adaptor,"token DOT");

        try {
            // JXPath.g:95:3: ( ( DOT | DOTDOT ) -> ^( ABBREVIATED_STEP ) )
            // JXPath.g:95:6: ( DOT | DOTDOT )
            {
            // JXPath.g:95:6: ( DOT | DOTDOT )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==DOT) ) {
                alt11=1;
            }
            else if ( (LA11_0==DOTDOT) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // JXPath.g:95:7: DOT
                    {
                    DOT36=(Token)match(input,DOT,FOLLOW_DOT_in_abbreviatedStep442);  
                    stream_DOT.add(DOT36);


                    }
                    break;
                case 2 :
                    // JXPath.g:96:6: DOTDOT
                    {
                    DOTDOT37=(Token)match(input,DOTDOT,FOLLOW_DOTDOT_in_abbreviatedStep449);  
                    stream_DOTDOT.add(DOTDOT37);


                    }
                    break;

            }



            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 97:4: -> ^( ABBREVIATED_STEP )
            {
                // JXPath.g:97:7: ^( ABBREVIATED_STEP )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ABBREVIATED_STEP, "ABBREVIATED_STEP"), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "abbreviatedStep"

    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // JXPath.g:100:1: expr : orExpr ;
    public final JXPathParser.expr_return expr() throws RecognitionException {
        JXPathParser.expr_return retval = new JXPathParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.orExpr_return orExpr38 = null;



        try {
            // JXPath.g:100:7: ( orExpr )
            // JXPath.g:100:10: orExpr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_orExpr_in_expr472);
            orExpr38=orExpr();

            state._fsp--;

            adaptor.addChild(root_0, orExpr38.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class primaryExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryExpr"
    // JXPath.g:103:1: primaryExpr : ( variableReference -> ^( variableReference ) | LPAR expr RPAR -> ^( expr ) | Literal -> ^( CONSTANT_EXPR Literal ) | Number -> ^( CONSTANT_EXPR Number ) | functionCall -> ^( functionCall ) ) ;
    public final JXPathParser.primaryExpr_return primaryExpr() throws RecognitionException {
        JXPathParser.primaryExpr_return retval = new JXPathParser.primaryExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LPAR40=null;
        Token RPAR42=null;
        Token Literal43=null;
        Token Number44=null;
        JXPathParser.variableReference_return variableReference39 = null;

        JXPathParser.expr_return expr41 = null;

        JXPathParser.functionCall_return functionCall45 = null;


        Object LPAR40_tree=null;
        Object RPAR42_tree=null;
        Object Literal43_tree=null;
        Object Number44_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_Literal=new RewriteRuleTokenStream(adaptor,"token Literal");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleTokenStream stream_Number=new RewriteRuleTokenStream(adaptor,"token Number");
        RewriteRuleSubtreeStream stream_functionCall=new RewriteRuleSubtreeStream(adaptor,"rule functionCall");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_variableReference=new RewriteRuleSubtreeStream(adaptor,"rule variableReference");
        try {
            // JXPath.g:104:3: ( ( variableReference -> ^( variableReference ) | LPAR expr RPAR -> ^( expr ) | Literal -> ^( CONSTANT_EXPR Literal ) | Number -> ^( CONSTANT_EXPR Number ) | functionCall -> ^( functionCall ) ) )
            // JXPath.g:104:6: ( variableReference -> ^( variableReference ) | LPAR expr RPAR -> ^( expr ) | Literal -> ^( CONSTANT_EXPR Literal ) | Number -> ^( CONSTANT_EXPR Number ) | functionCall -> ^( functionCall ) )
            {
            // JXPath.g:104:6: ( variableReference -> ^( variableReference ) | LPAR expr RPAR -> ^( expr ) | Literal -> ^( CONSTANT_EXPR Literal ) | Number -> ^( CONSTANT_EXPR Number ) | functionCall -> ^( functionCall ) )
            int alt12=5;
            switch ( input.LA(1) ) {
            case VAR_START:
                {
                alt12=1;
                }
                break;
            case LPAR:
                {
                alt12=2;
                }
                break;
            case Literal:
                {
                alt12=3;
                }
                break;
            case Number:
                {
                alt12=4;
                }
                break;
            case AxisName:
            case NCName:
                {
                alt12=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // JXPath.g:104:7: variableReference
                    {
                    pushFollow(FOLLOW_variableReference_in_primaryExpr487);
                    variableReference39=variableReference();

                    state._fsp--;

                    stream_variableReference.add(variableReference39.getTree());


                    // AST REWRITE
                    // elements: variableReference
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 104:25: -> ^( variableReference )
                    {
                        // JXPath.g:104:28: ^( variableReference )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_variableReference.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // JXPath.g:105:6: LPAR expr RPAR
                    {
                    LPAR40=(Token)match(input,LPAR,FOLLOW_LPAR_in_primaryExpr500);  
                    stream_LPAR.add(LPAR40);

                    pushFollow(FOLLOW_expr_in_primaryExpr502);
                    expr41=expr();

                    state._fsp--;

                    stream_expr.add(expr41.getTree());
                    RPAR42=(Token)match(input,RPAR,FOLLOW_RPAR_in_primaryExpr504);  
                    stream_RPAR.add(RPAR42);



                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 105:21: -> ^( expr )
                    {
                        // JXPath.g:105:24: ^( expr )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_expr.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // JXPath.g:106:6: Literal
                    {
                    Literal43=(Token)match(input,Literal,FOLLOW_Literal_in_primaryExpr517);  
                    stream_Literal.add(Literal43);



                    // AST REWRITE
                    // elements: Literal
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 106:14: -> ^( CONSTANT_EXPR Literal )
                    {
                        // JXPath.g:106:17: ^( CONSTANT_EXPR Literal )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTANT_EXPR, "CONSTANT_EXPR"), root_1);

                        adaptor.addChild(root_1, stream_Literal.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 4 :
                    // JXPath.g:107:6: Number
                    {
                    Number44=(Token)match(input,Number,FOLLOW_Number_in_primaryExpr532);  
                    stream_Number.add(Number44);



                    // AST REWRITE
                    // elements: Number
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 107:14: -> ^( CONSTANT_EXPR Number )
                    {
                        // JXPath.g:107:17: ^( CONSTANT_EXPR Number )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(CONSTANT_EXPR, "CONSTANT_EXPR"), root_1);

                        adaptor.addChild(root_1, stream_Number.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 5 :
                    // JXPath.g:108:6: functionCall
                    {
                    pushFollow(FOLLOW_functionCall_in_primaryExpr549);
                    functionCall45=functionCall();

                    state._fsp--;

                    stream_functionCall.add(functionCall45.getTree());


                    // AST REWRITE
                    // elements: functionCall
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 108:19: -> ^( functionCall )
                    {
                        // JXPath.g:108:22: ^( functionCall )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_functionCall.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "primaryExpr"

    public static class functionCall_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionCall"
    // JXPath.g:111:1: functionCall : functionName LPAR ( expr ( COMMA expr )* )? RPAR -> ^( FUNCTION_CALL functionName ^( ARGUMENTS ( expr )* ) ) ;
    public final JXPathParser.functionCall_return functionCall() throws RecognitionException {
        JXPathParser.functionCall_return retval = new JXPathParser.functionCall_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LPAR47=null;
        Token COMMA49=null;
        Token RPAR51=null;
        JXPathParser.functionName_return functionName46 = null;

        JXPathParser.expr_return expr48 = null;

        JXPathParser.expr_return expr50 = null;


        Object LPAR47_tree=null;
        Object COMMA49_tree=null;
        Object RPAR51_tree=null;
        RewriteRuleTokenStream stream_RPAR=new RewriteRuleTokenStream(adaptor,"token RPAR");
        RewriteRuleTokenStream stream_LPAR=new RewriteRuleTokenStream(adaptor,"token LPAR");
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_functionName=new RewriteRuleSubtreeStream(adaptor,"rule functionName");
        try {
            // JXPath.g:112:3: ( functionName LPAR ( expr ( COMMA expr )* )? RPAR -> ^( FUNCTION_CALL functionName ^( ARGUMENTS ( expr )* ) ) )
            // JXPath.g:112:6: functionName LPAR ( expr ( COMMA expr )* )? RPAR
            {
            pushFollow(FOLLOW_functionName_in_functionCall570);
            functionName46=functionName();

            state._fsp--;

            stream_functionName.add(functionName46.getTree());
            LPAR47=(Token)match(input,LPAR,FOLLOW_LPAR_in_functionCall572);  
            stream_LPAR.add(LPAR47);

            // JXPath.g:112:24: ( expr ( COMMA expr )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=PATHSEP && LA14_0<=AxisName)||(LA14_0>=AT && LA14_0<=LPAR)||LA14_0==Literal||(LA14_0>=DOT && LA14_0<=Number)||(LA14_0>=MINUS && LA14_0<=STAR)||(LA14_0>=VAR_START && LA14_0<=NCName)||LA14_0==52) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // JXPath.g:112:26: expr ( COMMA expr )*
                    {
                    pushFollow(FOLLOW_expr_in_functionCall576);
                    expr48=expr();

                    state._fsp--;

                    stream_expr.add(expr48.getTree());
                    // JXPath.g:112:31: ( COMMA expr )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // JXPath.g:112:33: COMMA expr
                    	    {
                    	    COMMA49=(Token)match(input,COMMA,FOLLOW_COMMA_in_functionCall580);  
                    	    stream_COMMA.add(COMMA49);

                    	    pushFollow(FOLLOW_expr_in_functionCall582);
                    	    expr50=expr();

                    	    state._fsp--;

                    	    stream_expr.add(expr50.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            RPAR51=(Token)match(input,RPAR,FOLLOW_RPAR_in_functionCall590);  
            stream_RPAR.add(RPAR51);



            // AST REWRITE
            // elements: expr, functionName
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 113:4: -> ^( FUNCTION_CALL functionName ^( ARGUMENTS ( expr )* ) )
            {
                // JXPath.g:113:7: ^( FUNCTION_CALL functionName ^( ARGUMENTS ( expr )* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNCTION_CALL, "FUNCTION_CALL"), root_1);

                adaptor.addChild(root_1, stream_functionName.nextTree());
                // JXPath.g:113:36: ^( ARGUMENTS ( expr )* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARGUMENTS, "ARGUMENTS"), root_2);

                // JXPath.g:113:48: ( expr )*
                while ( stream_expr.hasNext() ) {
                    adaptor.addChild(root_2, stream_expr.nextTree());

                }
                stream_expr.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "functionCall"

    public static class unionExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unionExpr"
    // JXPath.g:116:1: unionExpr : ( pathExpr ( UNION unionExpr )? | pathSep UNION unionExpr );
    public final JXPathParser.unionExpr_return unionExpr() throws RecognitionException {
        JXPathParser.unionExpr_return retval = new JXPathParser.unionExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token UNION53=null;
        Token UNION56=null;
        JXPathParser.pathExpr_return pathExpr52 = null;

        JXPathParser.unionExpr_return unionExpr54 = null;

        JXPathParser.pathSep_return pathSep55 = null;

        JXPathParser.unionExpr_return unionExpr57 = null;


        Object UNION53_tree=null;
        Object UNION56_tree=null;

        try {
            // JXPath.g:117:3: ( pathExpr ( UNION unionExpr )? | pathSep UNION unionExpr )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=ABRPATH && LA16_0<=AxisName)||(LA16_0>=AT && LA16_0<=LPAR)||LA16_0==Literal||(LA16_0>=DOT && LA16_0<=Number)||LA16_0==STAR||(LA16_0>=VAR_START && LA16_0<=NCName)||LA16_0==52) ) {
                alt16=1;
            }
            else if ( (LA16_0==PATHSEP) ) {
                int LA16_2 = input.LA(2);

                if ( (LA16_2==AxisName||(LA16_2>=AT && LA16_2<=NodeType)||(LA16_2>=DOT && LA16_2<=DOTDOT)||LA16_2==STAR||LA16_2==NCName||LA16_2==52) ) {
                    alt16=1;
                }
                else if ( (LA16_2==UNION) ) {
                    alt16=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 16, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // JXPath.g:117:6: pathExpr ( UNION unionExpr )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pathExpr_in_unionExpr622);
                    pathExpr52=pathExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, pathExpr52.getTree());
                    // JXPath.g:117:15: ( UNION unionExpr )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==UNION) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // JXPath.g:117:16: UNION unionExpr
                            {
                            UNION53=(Token)match(input,UNION,FOLLOW_UNION_in_unionExpr625); 
                            UNION53_tree = (Object)adaptor.create(UNION53);
                            root_0 = (Object)adaptor.becomeRoot(UNION53_tree, root_0);

                            pushFollow(FOLLOW_unionExpr_in_unionExpr628);
                            unionExpr54=unionExpr();

                            state._fsp--;

                            adaptor.addChild(root_0, unionExpr54.getTree());

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // JXPath.g:118:6: pathSep UNION unionExpr
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_pathSep_in_unionExpr637);
                    pathSep55=pathSep();

                    state._fsp--;

                    adaptor.addChild(root_0, pathSep55.getTree());
                    UNION56=(Token)match(input,UNION,FOLLOW_UNION_in_unionExpr639); 
                    UNION56_tree = (Object)adaptor.create(UNION56);
                    root_0 = (Object)adaptor.becomeRoot(UNION56_tree, root_0);

                    pushFollow(FOLLOW_unionExpr_in_unionExpr642);
                    unionExpr57=unionExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unionExpr57.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unionExpr"

    public static class pathExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pathExpr"
    // JXPath.g:121:1: pathExpr : ( locationPath -> ^( PATH_EXPR locationPath ) | f= filterExpr (s= simpleAxisStep r= relativeLocationPath )? -> { ((CommonTree)((filterExpr_return)f).getTree()).getType() == CONSTANT_EXPR && s==null && r==null }? ^( $f) -> ^( PATH_EXPR $f ( $s)? ( $r)? ) );
    public final JXPathParser.pathExpr_return pathExpr() throws RecognitionException {
        JXPathParser.pathExpr_return retval = new JXPathParser.pathExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.filterExpr_return f = null;

        JXPathParser.simpleAxisStep_return s = null;

        JXPathParser.relativeLocationPath_return r = null;

        JXPathParser.locationPath_return locationPath58 = null;


        RewriteRuleSubtreeStream stream_locationPath=new RewriteRuleSubtreeStream(adaptor,"rule locationPath");
        RewriteRuleSubtreeStream stream_filterExpr=new RewriteRuleSubtreeStream(adaptor,"rule filterExpr");
        RewriteRuleSubtreeStream stream_simpleAxisStep=new RewriteRuleSubtreeStream(adaptor,"rule simpleAxisStep");
        RewriteRuleSubtreeStream stream_relativeLocationPath=new RewriteRuleSubtreeStream(adaptor,"rule relativeLocationPath");
        try {
            // JXPath.g:122:3: ( locationPath -> ^( PATH_EXPR locationPath ) | f= filterExpr (s= simpleAxisStep r= relativeLocationPath )? -> { ((CommonTree)((filterExpr_return)f).getTree()).getType() == CONSTANT_EXPR && s==null && r==null }? ^( $f) -> ^( PATH_EXPR $f ( $s)? ( $r)? ) )
            int alt18=2;
            switch ( input.LA(1) ) {
            case AxisName:
                {
                switch ( input.LA(2) ) {
                case EOF:
                case PATHSEP:
                case ABRPATH:
                case CC:
                case RPAR:
                case LBRAC:
                case RBRAC:
                case COMMA:
                case UNION:
                case OR:
                case AND:
                case EQUAL:
                case NOT_EQUAL:
                case LESS_THAN:
                case GREATER_THAN:
                case LESS_THAN_EQUALS:
                case GREATER_THAN_EQUALS:
                case PLUS:
                case MINUS:
                case STAR:
                case DIV:
                case MOD:
                    {
                    alt18=1;
                    }
                    break;
                case COLON:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==STAR) ) {
                        alt18=1;
                    }
                    else if ( (LA18_5==AxisName||LA18_5==NCName) ) {
                        int LA18_6 = input.LA(4);

                        if ( (LA18_6==LPAR) ) {
                            alt18=2;
                        }
                        else if ( (LA18_6==EOF||(LA18_6>=PATHSEP && LA18_6<=ABRPATH)||LA18_6==RPAR||(LA18_6>=LBRAC && LA18_6<=RBRAC)||(LA18_6>=COMMA && LA18_6<=MOD)) ) {
                            alt18=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case LPAR:
                    {
                    alt18=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }

                }
                break;
            case PATHSEP:
            case ABRPATH:
            case AT:
            case NodeType:
            case DOT:
            case DOTDOT:
            case STAR:
            case 52:
                {
                alt18=1;
                }
                break;
            case NCName:
                {
                switch ( input.LA(2) ) {
                case COLON:
                    {
                    int LA18_5 = input.LA(3);

                    if ( (LA18_5==STAR) ) {
                        alt18=1;
                    }
                    else if ( (LA18_5==AxisName||LA18_5==NCName) ) {
                        int LA18_6 = input.LA(4);

                        if ( (LA18_6==LPAR) ) {
                            alt18=2;
                        }
                        else if ( (LA18_6==EOF||(LA18_6>=PATHSEP && LA18_6<=ABRPATH)||LA18_6==RPAR||(LA18_6>=LBRAC && LA18_6<=RBRAC)||(LA18_6>=COMMA && LA18_6<=MOD)) ) {
                            alt18=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 18, 6, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 18, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case LPAR:
                    {
                    alt18=2;
                    }
                    break;
                case EOF:
                case PATHSEP:
                case ABRPATH:
                case RPAR:
                case LBRAC:
                case RBRAC:
                case COMMA:
                case UNION:
                case OR:
                case AND:
                case EQUAL:
                case NOT_EQUAL:
                case LESS_THAN:
                case GREATER_THAN:
                case LESS_THAN_EQUALS:
                case GREATER_THAN_EQUALS:
                case PLUS:
                case MINUS:
                case STAR:
                case DIV:
                case MOD:
                    {
                    alt18=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 3, input);

                    throw nvae;
                }

                }
                break;
            case LPAR:
            case Literal:
            case Number:
            case VAR_START:
                {
                alt18=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // JXPath.g:122:6: locationPath
                    {
                    pushFollow(FOLLOW_locationPath_in_pathExpr656);
                    locationPath58=locationPath();

                    state._fsp--;

                    stream_locationPath.add(locationPath58.getTree());


                    // AST REWRITE
                    // elements: locationPath
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 122:19: -> ^( PATH_EXPR locationPath )
                    {
                        // JXPath.g:122:22: ^( PATH_EXPR locationPath )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PATH_EXPR, "PATH_EXPR"), root_1);

                        adaptor.addChild(root_1, stream_locationPath.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // JXPath.g:123:6: f= filterExpr (s= simpleAxisStep r= relativeLocationPath )?
                    {
                    pushFollow(FOLLOW_filterExpr_in_pathExpr673);
                    f=filterExpr();

                    state._fsp--;

                    stream_filterExpr.add(f.getTree());
                    // JXPath.g:123:19: (s= simpleAxisStep r= relativeLocationPath )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( ((LA17_0>=PATHSEP && LA17_0<=ABRPATH)) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // JXPath.g:123:20: s= simpleAxisStep r= relativeLocationPath
                            {
                            pushFollow(FOLLOW_simpleAxisStep_in_pathExpr678);
                            s=simpleAxisStep();

                            state._fsp--;

                            stream_simpleAxisStep.add(s.getTree());
                            pushFollow(FOLLOW_relativeLocationPath_in_pathExpr682);
                            r=relativeLocationPath();

                            state._fsp--;

                            stream_relativeLocationPath.add(r.getTree());

                            }
                            break;

                    }



                    // AST REWRITE
                    // elements: s, f, f, r
                    // token labels: 
                    // rule labels: f, retval, s, r
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_f=new RewriteRuleSubtreeStream(adaptor,"rule f",f!=null?f.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_s=new RewriteRuleSubtreeStream(adaptor,"rule s",s!=null?s.tree:null);
                    RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"rule r",r!=null?r.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 124:5: -> { ((CommonTree)((filterExpr_return)f).getTree()).getType() == CONSTANT_EXPR && s==null && r==null }? ^( $f)
                    if ( ((CommonTree)((filterExpr_return)f).getTree()).getType() == CONSTANT_EXPR && s==null && r==null ) {
                        // JXPath.g:124:109: ^( $f)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_f.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }
                    else // 125:5: -> ^( PATH_EXPR $f ( $s)? ( $r)? )
                    {
                        // JXPath.g:125:8: ^( PATH_EXPR $f ( $s)? ( $r)? )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PATH_EXPR, "PATH_EXPR"), root_1);

                        adaptor.addChild(root_1, stream_f.nextTree());
                        // JXPath.g:125:23: ( $s)?
                        if ( stream_s.hasNext() ) {
                            adaptor.addChild(root_1, stream_s.nextTree());

                        }
                        stream_s.reset();
                        // JXPath.g:125:27: ( $r)?
                        if ( stream_r.hasNext() ) {
                            adaptor.addChild(root_1, stream_r.nextTree());

                        }
                        stream_r.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pathExpr"

    public static class filterExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "filterExpr"
    // JXPath.g:128:1: filterExpr : primaryExpr (p+= predicate )* -> { list_p != null && list_p.size() > 0 }? ^( FILTER_PREDICATE_EXPR primaryExpr ^( PREDICATES ( $p)* ) ) -> primaryExpr ;
    public final JXPathParser.filterExpr_return filterExpr() throws RecognitionException {
        JXPathParser.filterExpr_return retval = new JXPathParser.filterExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        List list_p=null;
        JXPathParser.primaryExpr_return primaryExpr59 = null;

        RuleReturnScope p = null;
        RewriteRuleSubtreeStream stream_predicate=new RewriteRuleSubtreeStream(adaptor,"rule predicate");
        RewriteRuleSubtreeStream stream_primaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule primaryExpr");
        try {
            // JXPath.g:129:3: ( primaryExpr (p+= predicate )* -> { list_p != null && list_p.size() > 0 }? ^( FILTER_PREDICATE_EXPR primaryExpr ^( PREDICATES ( $p)* ) ) -> primaryExpr )
            // JXPath.g:129:6: primaryExpr (p+= predicate )*
            {
            pushFollow(FOLLOW_primaryExpr_in_filterExpr733);
            primaryExpr59=primaryExpr();

            state._fsp--;

            stream_primaryExpr.add(primaryExpr59.getTree());
            // JXPath.g:129:19: (p+= predicate )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==LBRAC) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // JXPath.g:129:19: p+= predicate
            	    {
            	    pushFollow(FOLLOW_predicate_in_filterExpr737);
            	    p=predicate();

            	    state._fsp--;

            	    stream_predicate.add(p.getTree());
            	    if (list_p==null) list_p=new ArrayList();
            	    list_p.add(p.getTree());


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);



            // AST REWRITE
            // elements: primaryExpr, p, primaryExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: p
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_p=new RewriteRuleSubtreeStream(adaptor,"token p",list_p);
            root_0 = (Object)adaptor.nil();
            // 129:32: -> { list_p != null && list_p.size() > 0 }? ^( FILTER_PREDICATE_EXPR primaryExpr ^( PREDICATES ( $p)* ) )
            if ( list_p != null && list_p.size() > 0 ) {
                // JXPath.g:129:76: ^( FILTER_PREDICATE_EXPR primaryExpr ^( PREDICATES ( $p)* ) )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FILTER_PREDICATE_EXPR, "FILTER_PREDICATE_EXPR"), root_1);

                adaptor.addChild(root_1, stream_primaryExpr.nextTree());
                // JXPath.g:129:112: ^( PREDICATES ( $p)* )
                {
                Object root_2 = (Object)adaptor.nil();
                root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(PREDICATES, "PREDICATES"), root_2);

                // JXPath.g:129:125: ( $p)*
                while ( stream_p.hasNext() ) {
                    adaptor.addChild(root_2, stream_p.nextTree());

                }
                stream_p.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }
            else // 130:11: -> primaryExpr
            {
                adaptor.addChild(root_0, stream_primaryExpr.nextTree());

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "filterExpr"

    public static class orExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orExpr"
    // JXPath.g:133:1: orExpr : andExpr ( OR andExpr )* ;
    public final JXPathParser.orExpr_return orExpr() throws RecognitionException {
        JXPathParser.orExpr_return retval = new JXPathParser.orExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token OR61=null;
        JXPathParser.andExpr_return andExpr60 = null;

        JXPathParser.andExpr_return andExpr62 = null;


        Object OR61_tree=null;

        try {
            // JXPath.g:133:9: ( andExpr ( OR andExpr )* )
            // JXPath.g:133:12: andExpr ( OR andExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_andExpr_in_orExpr783);
            andExpr60=andExpr();

            state._fsp--;

            adaptor.addChild(root_0, andExpr60.getTree());
            // JXPath.g:133:20: ( OR andExpr )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==OR) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // JXPath.g:133:21: OR andExpr
            	    {
            	    OR61=(Token)match(input,OR,FOLLOW_OR_in_orExpr786); 
            	    OR61_tree = (Object)adaptor.create(OR61);
            	    root_0 = (Object)adaptor.becomeRoot(OR61_tree, root_0);

            	    pushFollow(FOLLOW_andExpr_in_orExpr789);
            	    andExpr62=andExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, andExpr62.getTree());

            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "orExpr"

    public static class andExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andExpr"
    // JXPath.g:136:1: andExpr : equalityExpr ( AND equalityExpr )* ;
    public final JXPathParser.andExpr_return andExpr() throws RecognitionException {
        JXPathParser.andExpr_return retval = new JXPathParser.andExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND64=null;
        JXPathParser.equalityExpr_return equalityExpr63 = null;

        JXPathParser.equalityExpr_return equalityExpr65 = null;


        Object AND64_tree=null;

        try {
            // JXPath.g:136:10: ( equalityExpr ( AND equalityExpr )* )
            // JXPath.g:136:13: equalityExpr ( AND equalityExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_equalityExpr_in_andExpr804);
            equalityExpr63=equalityExpr();

            state._fsp--;

            adaptor.addChild(root_0, equalityExpr63.getTree());
            // JXPath.g:136:26: ( AND equalityExpr )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==AND) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // JXPath.g:136:27: AND equalityExpr
            	    {
            	    AND64=(Token)match(input,AND,FOLLOW_AND_in_andExpr807); 
            	    AND64_tree = (Object)adaptor.create(AND64);
            	    root_0 = (Object)adaptor.becomeRoot(AND64_tree, root_0);

            	    pushFollow(FOLLOW_equalityExpr_in_andExpr810);
            	    equalityExpr65=equalityExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, equalityExpr65.getTree());

            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "andExpr"

    public static class equalityExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpr"
    // JXPath.g:139:1: equalityExpr : relationalExpr ( ( EQUAL | NOT_EQUAL ) relationalExpr )* ;
    public final JXPathParser.equalityExpr_return equalityExpr() throws RecognitionException {
        JXPathParser.equalityExpr_return retval = new JXPathParser.equalityExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set67=null;
        JXPathParser.relationalExpr_return relationalExpr66 = null;

        JXPathParser.relationalExpr_return relationalExpr68 = null;


        Object set67_tree=null;

        try {
            // JXPath.g:140:3: ( relationalExpr ( ( EQUAL | NOT_EQUAL ) relationalExpr )* )
            // JXPath.g:140:6: relationalExpr ( ( EQUAL | NOT_EQUAL ) relationalExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_relationalExpr_in_equalityExpr826);
            relationalExpr66=relationalExpr();

            state._fsp--;

            adaptor.addChild(root_0, relationalExpr66.getTree());
            // JXPath.g:140:21: ( ( EQUAL | NOT_EQUAL ) relationalExpr )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=EQUAL && LA22_0<=NOT_EQUAL)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // JXPath.g:140:22: ( EQUAL | NOT_EQUAL ) relationalExpr
            	    {
            	    set67=(Token)input.LT(1);
            	    set67=(Token)input.LT(1);
            	    if ( (input.LA(1)>=EQUAL && input.LA(1)<=NOT_EQUAL) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set67), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr836);
            	    relationalExpr68=relationalExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, relationalExpr68.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "equalityExpr"

    public static class relationalExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpr"
    // JXPath.g:143:1: relationalExpr : additiveExpr ( ( LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS ) additiveExpr )* ;
    public final JXPathParser.relationalExpr_return relationalExpr() throws RecognitionException {
        JXPathParser.relationalExpr_return retval = new JXPathParser.relationalExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set70=null;
        JXPathParser.additiveExpr_return additiveExpr69 = null;

        JXPathParser.additiveExpr_return additiveExpr71 = null;


        Object set70_tree=null;

        try {
            // JXPath.g:144:3: ( additiveExpr ( ( LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS ) additiveExpr )* )
            // JXPath.g:144:6: additiveExpr ( ( LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS ) additiveExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_additiveExpr_in_relationalExpr852);
            additiveExpr69=additiveExpr();

            state._fsp--;

            adaptor.addChild(root_0, additiveExpr69.getTree());
            // JXPath.g:144:19: ( ( LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS ) additiveExpr )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0>=LESS_THAN && LA23_0<=GREATER_THAN_EQUALS)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // JXPath.g:144:20: ( LESS_THAN | GREATER_THAN | LESS_THAN_EQUALS | GREATER_THAN_EQUALS ) additiveExpr
            	    {
            	    set70=(Token)input.LT(1);
            	    set70=(Token)input.LT(1);
            	    if ( (input.LA(1)>=LESS_THAN && input.LA(1)<=GREATER_THAN_EQUALS) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set70), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr866);
            	    additiveExpr71=additiveExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, additiveExpr71.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "relationalExpr"

    public static class additiveExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpr"
    // JXPath.g:147:1: additiveExpr : multiplicativeExpr ( ( PLUS | MINUS ) multiplicativeExpr )* ;
    public final JXPathParser.additiveExpr_return additiveExpr() throws RecognitionException {
        JXPathParser.additiveExpr_return retval = new JXPathParser.additiveExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set73=null;
        JXPathParser.multiplicativeExpr_return multiplicativeExpr72 = null;

        JXPathParser.multiplicativeExpr_return multiplicativeExpr74 = null;


        Object set73_tree=null;

        try {
            // JXPath.g:148:3: ( multiplicativeExpr ( ( PLUS | MINUS ) multiplicativeExpr )* )
            // JXPath.g:148:6: multiplicativeExpr ( ( PLUS | MINUS ) multiplicativeExpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr882);
            multiplicativeExpr72=multiplicativeExpr();

            state._fsp--;

            adaptor.addChild(root_0, multiplicativeExpr72.getTree());
            // JXPath.g:148:25: ( ( PLUS | MINUS ) multiplicativeExpr )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=PLUS && LA24_0<=MINUS)) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // JXPath.g:148:26: ( PLUS | MINUS ) multiplicativeExpr
            	    {
            	    set73=(Token)input.LT(1);
            	    set73=(Token)input.LT(1);
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set73), root_0);
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr892);
            	    multiplicativeExpr74=multiplicativeExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multiplicativeExpr74.getTree());

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "additiveExpr"

    public static class multiplicativeExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpr"
    // JXPath.g:151:1: multiplicativeExpr : ( unaryExpr ( ( STAR | DIV | MOD ) unaryExpr )* | PATHSEP ( ( DIV | MOD ) multiplicativeExpr )? );
    public final JXPathParser.multiplicativeExpr_return multiplicativeExpr() throws RecognitionException {
        JXPathParser.multiplicativeExpr_return retval = new JXPathParser.multiplicativeExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set76=null;
        Token PATHSEP78=null;
        Token set79=null;
        JXPathParser.unaryExpr_return unaryExpr75 = null;

        JXPathParser.unaryExpr_return unaryExpr77 = null;

        JXPathParser.multiplicativeExpr_return multiplicativeExpr80 = null;


        Object set76_tree=null;
        Object PATHSEP78_tree=null;
        Object set79_tree=null;

        try {
            // JXPath.g:152:3: ( unaryExpr ( ( STAR | DIV | MOD ) unaryExpr )* | PATHSEP ( ( DIV | MOD ) multiplicativeExpr )? )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=ABRPATH && LA27_0<=AxisName)||(LA27_0>=AT && LA27_0<=LPAR)||LA27_0==Literal||(LA27_0>=DOT && LA27_0<=Number)||(LA27_0>=MINUS && LA27_0<=STAR)||(LA27_0>=VAR_START && LA27_0<=NCName)||LA27_0==52) ) {
                alt27=1;
            }
            else if ( (LA27_0==PATHSEP) ) {
                int LA27_2 = input.LA(2);

                if ( (LA27_2==AxisName||(LA27_2>=AT && LA27_2<=NodeType)||(LA27_2>=DOT && LA27_2<=DOTDOT)||LA27_2==UNION||LA27_2==STAR||LA27_2==NCName||LA27_2==52) ) {
                    alt27=1;
                }
                else if ( (LA27_2==EOF||LA27_2==RPAR||LA27_2==RBRAC||LA27_2==COMMA||(LA27_2>=OR && LA27_2<=MINUS)||(LA27_2>=DIV && LA27_2<=MOD)) ) {
                    alt27=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // JXPath.g:152:6: unaryExpr ( ( STAR | DIV | MOD ) unaryExpr )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_unaryExpr_in_multiplicativeExpr908);
                    unaryExpr75=unaryExpr();

                    state._fsp--;

                    adaptor.addChild(root_0, unaryExpr75.getTree());
                    // JXPath.g:152:16: ( ( STAR | DIV | MOD ) unaryExpr )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0>=STAR && LA25_0<=MOD)) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // JXPath.g:152:17: ( STAR | DIV | MOD ) unaryExpr
                    	    {
                    	    set76=(Token)input.LT(1);
                    	    set76=(Token)input.LT(1);
                    	    if ( (input.LA(1)>=STAR && input.LA(1)<=MOD) ) {
                    	        input.consume();
                    	        root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set76), root_0);
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_unaryExpr_in_multiplicativeExpr920);
                    	    unaryExpr77=unaryExpr();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, unaryExpr77.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // JXPath.g:153:6: PATHSEP ( ( DIV | MOD ) multiplicativeExpr )?
                    {
                    root_0 = (Object)adaptor.nil();

                    PATHSEP78=(Token)match(input,PATHSEP,FOLLOW_PATHSEP_in_multiplicativeExpr929); 
                    PATHSEP78_tree = (Object)adaptor.create(PATHSEP78);
                    adaptor.addChild(root_0, PATHSEP78_tree);

                    // JXPath.g:153:14: ( ( DIV | MOD ) multiplicativeExpr )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( ((LA26_0>=DIV && LA26_0<=MOD)) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // JXPath.g:153:15: ( DIV | MOD ) multiplicativeExpr
                            {
                            set79=(Token)input.LT(1);
                            set79=(Token)input.LT(1);
                            if ( (input.LA(1)>=DIV && input.LA(1)<=MOD) ) {
                                input.consume();
                                root_0 = (Object)adaptor.becomeRoot((Object)adaptor.create(set79), root_0);
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            pushFollow(FOLLOW_multiplicativeExpr_in_multiplicativeExpr939);
                            multiplicativeExpr80=multiplicativeExpr();

                            state._fsp--;

                            adaptor.addChild(root_0, multiplicativeExpr80.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpr"

    public static class unaryExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpr"
    // JXPath.g:156:1: unaryExpr : ( MINUS )* unionExpr ;
    public final JXPathParser.unaryExpr_return unaryExpr() throws RecognitionException {
        JXPathParser.unaryExpr_return retval = new JXPathParser.unaryExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token MINUS81=null;
        JXPathParser.unionExpr_return unionExpr82 = null;


        Object MINUS81_tree=null;

        try {
            // JXPath.g:157:3: ( ( MINUS )* unionExpr )
            // JXPath.g:157:6: ( MINUS )* unionExpr
            {
            root_0 = (Object)adaptor.nil();

            // JXPath.g:157:6: ( MINUS )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==MINUS) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // JXPath.g:157:6: MINUS
            	    {
            	    MINUS81=(Token)match(input,MINUS,FOLLOW_MINUS_in_unaryExpr955); 
            	    MINUS81_tree = (Object)adaptor.create(MINUS81);
            	    adaptor.addChild(root_0, MINUS81_tree);


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            pushFollow(FOLLOW_unionExpr_in_unaryExpr958);
            unionExpr82=unionExpr();

            state._fsp--;

            adaptor.addChild(root_0, unionExpr82.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "unaryExpr"

    public static class qName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "qName"
    // JXPath.g:160:1: qName : nCName ( COLON nCName )? -> ^( QUALIFIED_NAME ( nCName )+ ) ;
    public final JXPathParser.qName_return qName() throws RecognitionException {
        JXPathParser.qName_return retval = new JXPathParser.qName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token COLON84=null;
        JXPathParser.nCName_return nCName83 = null;

        JXPathParser.nCName_return nCName85 = null;


        Object COLON84_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_nCName=new RewriteRuleSubtreeStream(adaptor,"rule nCName");
        try {
            // JXPath.g:160:8: ( nCName ( COLON nCName )? -> ^( QUALIFIED_NAME ( nCName )+ ) )
            // JXPath.g:160:11: nCName ( COLON nCName )?
            {
            pushFollow(FOLLOW_nCName_in_qName971);
            nCName83=nCName();

            state._fsp--;

            stream_nCName.add(nCName83.getTree());
            // JXPath.g:160:18: ( COLON nCName )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==COLON) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // JXPath.g:160:19: COLON nCName
                    {
                    COLON84=(Token)match(input,COLON,FOLLOW_COLON_in_qName974);  
                    stream_COLON.add(COLON84);

                    pushFollow(FOLLOW_nCName_in_qName976);
                    nCName85=nCName();

                    state._fsp--;

                    stream_nCName.add(nCName85.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: nCName
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 161:2: -> ^( QUALIFIED_NAME ( nCName )+ )
            {
                // JXPath.g:161:5: ^( QUALIFIED_NAME ( nCName )+ )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(QUALIFIED_NAME, "QUALIFIED_NAME"), root_1);

                if ( !(stream_nCName.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_nCName.hasNext() ) {
                    adaptor.addChild(root_1, stream_nCName.nextTree());

                }
                stream_nCName.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "qName"

    public static class functionName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "functionName"
    // JXPath.g:164:1: functionName : qName ;
    public final JXPathParser.functionName_return functionName() throws RecognitionException {
        JXPathParser.functionName_return retval = new JXPathParser.functionName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        JXPathParser.qName_return qName86 = null;



        try {
            // JXPath.g:165:3: ( qName )
            // JXPath.g:165:6: qName
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_qName_in_functionName1002);
            qName86=qName();

            state._fsp--;

            adaptor.addChild(root_0, qName86.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "functionName"

    public static class variableReference_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variableReference"
    // JXPath.g:168:1: variableReference : VAR_START qName -> ^( VAR_REF qName ) ;
    public final JXPathParser.variableReference_return variableReference() throws RecognitionException {
        JXPathParser.variableReference_return retval = new JXPathParser.variableReference_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token VAR_START87=null;
        JXPathParser.qName_return qName88 = null;


        Object VAR_START87_tree=null;
        RewriteRuleTokenStream stream_VAR_START=new RewriteRuleTokenStream(adaptor,"token VAR_START");
        RewriteRuleSubtreeStream stream_qName=new RewriteRuleSubtreeStream(adaptor,"rule qName");
        try {
            // JXPath.g:169:3: ( VAR_START qName -> ^( VAR_REF qName ) )
            // JXPath.g:169:6: VAR_START qName
            {
            VAR_START87=(Token)match(input,VAR_START,FOLLOW_VAR_START_in_variableReference1018);  
            stream_VAR_START.add(VAR_START87);

            pushFollow(FOLLOW_qName_in_variableReference1020);
            qName88=qName();

            state._fsp--;

            stream_qName.add(qName88.getTree());


            // AST REWRITE
            // elements: qName
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 170:4: -> ^( VAR_REF qName )
            {
                // JXPath.g:170:7: ^( VAR_REF qName )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VAR_REF, "VAR_REF"), root_1);

                adaptor.addChild(root_1, stream_qName.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "variableReference"

    public static class nameTest_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nameTest"
    // JXPath.g:173:1: nameTest : ( STAR | nCName COLON STAR | qName );
    public final JXPathParser.nameTest_return nameTest() throws RecognitionException {
        JXPathParser.nameTest_return retval = new JXPathParser.nameTest_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token STAR89=null;
        Token COLON91=null;
        Token STAR92=null;
        JXPathParser.nCName_return nCName90 = null;

        JXPathParser.qName_return qName93 = null;


        Object STAR89_tree=null;
        Object COLON91_tree=null;
        Object STAR92_tree=null;

        try {
            // JXPath.g:173:9: ( STAR | nCName COLON STAR | qName )
            int alt30=3;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==STAR) ) {
                alt30=1;
            }
            else if ( (LA30_0==AxisName||LA30_0==NCName) ) {
                int LA30_2 = input.LA(2);

                if ( (LA30_2==COLON) ) {
                    int LA30_3 = input.LA(3);

                    if ( (LA30_3==STAR) ) {
                        alt30=2;
                    }
                    else if ( (LA30_3==AxisName||LA30_3==NCName) ) {
                        alt30=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 3, input);

                        throw nvae;
                    }
                }
                else if ( (LA30_2==EOF||(LA30_2>=PATHSEP && LA30_2<=ABRPATH)||LA30_2==RPAR||(LA30_2>=LBRAC && LA30_2<=RBRAC)||(LA30_2>=COMMA && LA30_2<=MOD)) ) {
                    alt30=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // JXPath.g:173:12: STAR
                    {
                    root_0 = (Object)adaptor.nil();

                    STAR89=(Token)match(input,STAR,FOLLOW_STAR_in_nameTest1042); 
                    STAR89_tree = (Object)adaptor.create(STAR89);
                    adaptor.addChild(root_0, STAR89_tree);


                    }
                    break;
                case 2 :
                    // JXPath.g:174:6: nCName COLON STAR
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_nCName_in_nameTest1049);
                    nCName90=nCName();

                    state._fsp--;

                    adaptor.addChild(root_0, nCName90.getTree());
                    COLON91=(Token)match(input,COLON,FOLLOW_COLON_in_nameTest1051); 
                    COLON91_tree = (Object)adaptor.create(COLON91);
                    adaptor.addChild(root_0, COLON91_tree);

                    STAR92=(Token)match(input,STAR,FOLLOW_STAR_in_nameTest1053); 
                    STAR92_tree = (Object)adaptor.create(STAR92);
                    adaptor.addChild(root_0, STAR92_tree);


                    }
                    break;
                case 3 :
                    // JXPath.g:175:6: qName
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_qName_in_nameTest1060);
                    qName93=qName();

                    state._fsp--;

                    adaptor.addChild(root_0, qName93.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nameTest"

    public static class nCName_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nCName"
    // JXPath.g:178:1: nCName : ( NCName | AxisName );
    public final JXPathParser.nCName_return nCName() throws RecognitionException {
        JXPathParser.nCName_return retval = new JXPathParser.nCName_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set94=null;

        Object set94_tree=null;

        try {
            // JXPath.g:178:9: ( NCName | AxisName )
            // JXPath.g:
            {
            root_0 = (Object)adaptor.nil();

            set94=(Token)input.LT(1);
            if ( input.LA(1)==AxisName||input.LA(1)==NCName ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set94));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nCName"

    // Delegated rules


 

    public static final BitSet FOLLOW_expr_in_main92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relativeLocationPath_in_locationPath107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_absoluteLocationPath_in_locationPath114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSep_in_absoluteLocationPath128 = new BitSet(new long[]{0x0010840018340000L});
    public static final BitSet FOLLOW_relativeLocationPath_in_absoluteLocationPath130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_abbreviatedAbsoluteLocationPath_in_absoluteLocationPath137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_abrPath_in_abbreviatedAbsoluteLocationPath150 = new BitSet(new long[]{0x0010840018340000L});
    public static final BitSet FOLLOW_relativeLocationPath_in_abbreviatedAbsoluteLocationPath152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_step_in_relativeLocationPath168 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_simpleAxisStep_in_relativeLocationPath171 = new BitSet(new long[]{0x0010840018340000L});
    public static final BitSet FOLLOW_step_in_relativeLocationPath173 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_pathSep_in_simpleAxisStep192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_abrPath_in_simpleAxisStep202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PATHSEP_in_pathSep228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABRPATH_in_abrPath251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_namedAxisStep_in_step272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_abbreviatedStep_in_step279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_axisSpecifier_in_namedAxisStep294 = new BitSet(new long[]{0x0010840000340000L});
    public static final BitSet FOLLOW_nodeTest_in_namedAxisStep297 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_predicates_in_namedAxisStep299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_predicates331 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_AxisName_in_axisSpecifier347 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_CC_in_axisSpecifier349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_axisSpecifier356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameTest_in_nodeTest383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NodeType_in_nodeTest390 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_LPAR_in_nodeTest392 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RPAR_in_nodeTest394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_nodeTest401 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_LPAR_in_nodeTest403 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_Literal_in_nodeTest405 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RPAR_in_nodeTest407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRAC_in_predicate421 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_expr_in_predicate424 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_RBRAC_in_predicate426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_abbreviatedStep442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOTDOT_in_abbreviatedStep449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_expr472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableReference_in_primaryExpr487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAR_in_primaryExpr500 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_expr_in_primaryExpr502 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_RPAR_in_primaryExpr504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Literal_in_primaryExpr517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Number_in_primaryExpr532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionCall_in_primaryExpr549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_functionName_in_functionCall570 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_LPAR_in_functionCall572 = new BitSet(new long[]{0x0010C60039F70000L});
    public static final BitSet FOLLOW_expr_in_functionCall576 = new BitSet(new long[]{0x0000000040800000L});
    public static final BitSet FOLLOW_COMMA_in_functionCall580 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_expr_in_functionCall582 = new BitSet(new long[]{0x0000000040800000L});
    public static final BitSet FOLLOW_RPAR_in_functionCall590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpr_in_unionExpr622 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_UNION_in_unionExpr625 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_unionExpr_in_unionExpr628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathSep_in_unionExpr637 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_UNION_in_unionExpr639 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_unionExpr_in_unionExpr642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_locationPath_in_pathExpr656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterExpr_in_pathExpr673 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_simpleAxisStep_in_pathExpr678 = new BitSet(new long[]{0x0010840018340000L});
    public static final BitSet FOLLOW_relativeLocationPath_in_pathExpr682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpr_in_filterExpr733 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_predicate_in_filterExpr737 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr783 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_OR_in_orExpr786 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_andExpr_in_orExpr789 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_equalityExpr_in_andExpr804 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_AND_in_andExpr807 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_equalityExpr_in_andExpr810 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr826 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_set_in_equalityExpr829 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr836 = new BitSet(new long[]{0x0000000C00000002L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr852 = new BitSet(new long[]{0x000000F000000002L});
    public static final BitSet FOLLOW_set_in_relationalExpr855 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr866 = new BitSet(new long[]{0x000000F000000002L});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr882 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_set_in_additiveExpr885 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr892 = new BitSet(new long[]{0x0000030000000002L});
    public static final BitSet FOLLOW_unaryExpr_in_multiplicativeExpr908 = new BitSet(new long[]{0x00001C0000000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpr911 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_unaryExpr_in_multiplicativeExpr920 = new BitSet(new long[]{0x00001C0000000002L});
    public static final BitSet FOLLOW_PATHSEP_in_multiplicativeExpr929 = new BitSet(new long[]{0x0000180000000002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpr932 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_multiplicativeExpr_in_multiplicativeExpr939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unaryExpr955 = new BitSet(new long[]{0x0010C60039770000L});
    public static final BitSet FOLLOW_unionExpr_in_unaryExpr958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nCName_in_qName971 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_COLON_in_qName974 = new BitSet(new long[]{0x0000800000040000L});
    public static final BitSet FOLLOW_nCName_in_qName976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qName_in_functionName1002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_START_in_variableReference1018 = new BitSet(new long[]{0x0000840000040000L});
    public static final BitSet FOLLOW_qName_in_variableReference1020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_nameTest1042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nCName_in_nameTest1049 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_COLON_in_nameTest1051 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_STAR_in_nameTest1053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qName_in_nameTest1060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_nCName0 = new BitSet(new long[]{0x0000000000000002L});

}