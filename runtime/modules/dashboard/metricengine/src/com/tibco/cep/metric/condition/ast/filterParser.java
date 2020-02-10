// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g 2016-03-24 11:19:17

	package com.tibco.cep.metric.condition.ast;
      

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class filterParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TOK_SELECTOR", "OR_EXPRESSION", "AND_EXPRESSION", "PARENTHESES_EXPRESSION", "RANGE_EXPRESSION", "BIND_VARIABLE_EXPRESSION", "IDENTIFIER", "PROPERTY", "UNARY_MINUS", "TOK_NOTLIKE", "TOK_NOR", "TOK_NOTIN", "TOK_NOTBETWEEN", "TOK_ISNOT", "TOK_RPAREN", "TOK_LPAREN", "TOK_COMMA", "TOK_DOT", "TOK_COLON", "TOK_CONCAT", "TOK_EQ", "TOK_PLUS", "TOK_MINUS", "TOK_SLASH", "TOK_STAR", "TOK_LE", "TOK_GE", "TOK_NE", "TOK_LT", "TOK_GT", "TOK_DOLLAR", "TOK_HASH", "TOK_AT", "TOK_IN", "TOK_OR", "TOK_AND", "TOK_LIKE", "TOK_BETWEEN", "TOK_MOD", "TOK_ABS", "TOK_NOT", "TOK_NULL", "TOK_TRUE", "TOK_FALSE", "TOK_DOUBLE", "TOK_IS", "HexDigit", "NameFirstCharacter", "NameCharacter", "OctalEscape", "UnicodeEscape", "EscapeSequence", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "HexLiteral", "DIGIT_ZERO", "DIGIT_OCTAL_RANGE", "DIGIT_DECIMAL_RANGE", "DIGIT_FULL_RANGE", "OCTALDIGIT", "DIGIT", "DIGITS", "DecimalLiteral", "OctalLiteral", "SUBDATE", "SUBTIME", "SUBTIMEMILLIS", "GMT", "DateLiteral", "TimeLiteral", "DateTimeLiteral", "StringLiteral", "TOK_APPROXIMATE_NUMERIC_LITERAL", "TOK_EXACT_NUMERIC_LITERAL", "Identifier", "WS", "NewLine", "CommentLine", "SLComment", "MultiLineComment"
    };
    public static final int TOK_OR=38;
    public static final int TOK_PLUS=25;
    public static final int TOK_NOT=44;
    public static final int RANGE_EXPRESSION=8;
    public static final int DateLiteral=73;
    public static final int TOK_NOR=14;
    public static final int BIND_VARIABLE_EXPRESSION=9;
    public static final int TOK_DOLLAR=34;
    public static final int TOK_LIKE=40;
    public static final int AND_EXPRESSION=6;
    public static final int TOK_GT=33;
    public static final int TOK_AND=39;
    public static final int Identifier=79;
    public static final int OctalLiteral=68;
    public static final int CommentLine=82;
    public static final int SUBTIME=70;
    public static final int TOK_APPROXIMATE_NUMERIC_LITERAL=77;
    public static final int TOK_GE=30;
    public static final int SLComment=83;
    public static final int TOK_LT=32;
    public static final int NewLine=81;
    public static final int PROPERTY=11;
    public static final int TOK_NOTBETWEEN=16;
    public static final int TOK_COLON=22;
    public static final int SUBTIMEMILLIS=71;
    public static final int HexDigit=50;
    public static final int GMT=72;
    public static final int TOK_COMMA=20;
    public static final int TOK_NOTLIKE=13;
    public static final int StringLiteral=76;
    public static final int TOK_NOTIN=15;
    public static final int TOK_SLASH=27;
    public static final int OCTALDIGIT=64;
    public static final int TOK_LE=29;
    public static final int OctalEscape=53;
    public static final int TOK_DOT=21;
    public static final int SUBDATE=69;
    public static final int TimeLiteral=74;
    public static final int DIGIT_ZERO=60;
    public static final int Exponent=57;
    public static final int TOK_IN=37;
    public static final int TOK_EQ=24;
    public static final int NameFirstCharacter=51;
    public static final int DIGIT_FULL_RANGE=63;
    public static final int DIGITS=66;
    public static final int TOK_EXACT_NUMERIC_LITERAL=78;
    public static final int TOK_IS=49;
    public static final int TOK_STAR=28;
    public static final int TOK_FALSE=47;
    public static final int TOK_RPAREN=18;
    public static final int IntegerTypeSuffix=56;
    public static final int TOK_ISNOT=17;
    public static final int TOK_LPAREN=19;
    public static final int DecimalLiteral=67;
    public static final int FloatTypeSuffix=58;
    public static final int IDENTIFIER=10;
    public static final int UnicodeEscape=54;
    public static final int WS=80;
    public static final int EOF=-1;
    public static final int TOK_MINUS=26;
    public static final int TOK_ABS=43;
    public static final int MultiLineComment=84;
    public static final int EscapeSequence=55;
    public static final int DIGIT_OCTAL_RANGE=61;
    public static final int TOK_DOUBLE=48;
    public static final int TOK_BETWEEN=41;
    public static final int OR_EXPRESSION=5;
    public static final int DIGIT=65;
    public static final int PARENTHESES_EXPRESSION=7;
    public static final int DIGIT_DECIMAL_RANGE=62;
    public static final int TOK_AT=36;
    public static final int TOK_TRUE=46;
    public static final int TOK_CONCAT=23;
    public static final int TOK_MOD=42;
    public static final int TOK_NE=31;
    public static final int UNARY_MINUS=12;
    public static final int DateTimeLiteral=75;
    public static final int HexLiteral=59;
    public static final int TOK_HASH=35;
    public static final int TOK_SELECTOR=4;
    public static final int NameCharacter=52;
    public static final int TOK_NULL=45;

    // delegates
    // delegators


        public filterParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public filterParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[91+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return filterParser.tokenNames; }
    public String getGrammarFileName() { return "/home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g"; }


            protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException
            {
                throw new MismatchedTokenException(ttype, input);
            }

            public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
                 System.out.println(e.getMessage());
                 throw e;
            }

          

    public static class selector_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "selector"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:279:1: selector : return_or= orExpr EOF -> ^( TOK_SELECTOR $return_or) ;
    public final filterParser.selector_return selector() throws RecognitionException {
        filterParser.selector_return retval = new filterParser.selector_return();
        retval.start = input.LT(1);
        int selector_StartIndex = input.index();
        Object root_0 = null;

        Token EOF1=null;
        filterParser.orExpr_return return_or = null;


        Object EOF1_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_orExpr=new RewriteRuleSubtreeStream(adaptor,"rule orExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:280:5: (return_or= orExpr EOF -> ^( TOK_SELECTOR $return_or) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:280:9: return_or= orExpr EOF
            {
            pushFollow(FOLLOW_orExpr_in_selector3525);
            return_or=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_orExpr.add(return_or.getTree());
            EOF1=(Token)match(input,EOF,FOLLOW_EOF_in_selector3527); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF1);



            // AST REWRITE
            // elements: return_or
            // token labels: 
            // rule labels: return_or, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_return_or=new RewriteRuleSubtreeStream(adaptor,"rule return_or",return_or!=null?return_or.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 280:32: -> ^( TOK_SELECTOR $return_or)
            {
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:280:35: ^( TOK_SELECTOR $return_or)
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_SELECTOR, "TOK_SELECTOR"), root_1);

                adaptor.addChild(root_1, stream_return_or.nextTree());

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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 1, selector_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "selector"

    public static class orExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:284:1: orExpr : ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( $lc $orExpr $a) )* ;
    public final filterParser.orExpr_return orExpr() throws RecognitionException {
        filterParser.orExpr_return retval = new filterParser.orExpr_return();
        retval.start = input.LT(1);
        int orExpr_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        filterParser.andExpr_return a = null;

        filterParser.andExpr_return andExpr2 = null;


        Object lc_tree=null;
        RewriteRuleTokenStream stream_TOK_OR=new RewriteRuleTokenStream(adaptor,"token TOK_OR");
        RewriteRuleSubtreeStream stream_andExpr=new RewriteRuleSubtreeStream(adaptor,"rule andExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:284:8: ( ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( $lc $orExpr $a) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:285:15: ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( $lc $orExpr $a) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:285:15: ( andExpr -> andExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:285:17: andExpr
            {
            pushFollow(FOLLOW_andExpr_in_orExpr3575);
            andExpr2=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpr.add(andExpr2.getTree());


            // AST REWRITE
            // elements: andExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 285:25: -> andExpr
            {
                adaptor.addChild(root_0, stream_andExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:286:15: (lc= TOK_OR a= andExpr -> ^( $lc $orExpr $a) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==TOK_OR) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:287:19: lc= TOK_OR a= andExpr
            	    {
            	    lc=(Token)match(input,TOK_OR,FOLLOW_TOK_OR_in_orExpr3619); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_OR.add(lc);

            	    pushFollow(FOLLOW_andExpr_in_orExpr3641);
            	    a=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_andExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: lc, orExpr, a
            	    // token labels: lc
            	    // rule labels: a, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_lc=new RewriteRuleTokenStream(adaptor,"token lc",lc);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 288:29: -> ^( $lc $orExpr $a)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:288:32: ^( $lc $orExpr $a)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_lc.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 2, orExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "orExpr"

    public static class andExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:292:1: andExpr : ( ( equalityExpr -> equalityExpr ) (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )* ) ;
    public final filterParser.andExpr_return andExpr() throws RecognitionException {
        filterParser.andExpr_return retval = new filterParser.andExpr_return();
        retval.start = input.LT(1);
        int andExpr_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        filterParser.equalityExpr_return a = null;

        filterParser.equalityExpr_return equalityExpr3 = null;


        Object lc_tree=null;
        RewriteRuleTokenStream stream_TOK_AND=new RewriteRuleTokenStream(adaptor,"token TOK_AND");
        RewriteRuleSubtreeStream stream_equalityExpr=new RewriteRuleSubtreeStream(adaptor,"rule equalityExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:292:9: ( ( ( equalityExpr -> equalityExpr ) (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )* ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:293:8: ( ( equalityExpr -> equalityExpr ) (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )* )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:293:8: ( ( equalityExpr -> equalityExpr ) (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:294:15: ( equalityExpr -> equalityExpr ) (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:294:15: ( equalityExpr -> equalityExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:294:17: equalityExpr
            {
            pushFollow(FOLLOW_equalityExpr_in_andExpr3720);
            equalityExpr3=equalityExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_equalityExpr.add(equalityExpr3.getTree());


            // AST REWRITE
            // elements: equalityExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 294:30: -> equalityExpr
            {
                adaptor.addChild(root_0, stream_equalityExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:295:15: (lc= TOK_AND a= equalityExpr -> ^( $lc $andExpr $a) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==TOK_AND) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:296:19: lc= TOK_AND a= equalityExpr
            	    {
            	    lc=(Token)match(input,TOK_AND,FOLLOW_TOK_AND_in_andExpr3763); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_AND.add(lc);

            	    pushFollow(FOLLOW_equalityExpr_in_andExpr3785);
            	    a=equalityExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equalityExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: andExpr, a, lc
            	    // token labels: lc
            	    // rule labels: a, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_lc=new RewriteRuleTokenStream(adaptor,"token lc",lc);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 297:34: -> ^( $lc $andExpr $a)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:297:37: ^( $lc $andExpr $a)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_lc.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 3, andExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "andExpr"

    public static class equalityOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityOp"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:302:1: equalityOp : ( TOK_EQ | TOK_NE );
    public final filterParser.equalityOp_return equalityOp() throws RecognitionException {
        filterParser.equalityOp_return retval = new filterParser.equalityOp_return();
        retval.start = input.LT(1);
        int equalityOp_StartIndex = input.index();
        Object root_0 = null;

        Token set4=null;

        Object set4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:302:12: ( TOK_EQ | TOK_NE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            root_0 = (Object)adaptor.nil();

            set4=(Token)input.LT(1);
            if ( input.LA(1)==TOK_EQ||input.LA(1)==TOK_NE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set4));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 4, equalityOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equalityOp"

    public static class equalityExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:307:1: equalityExpr : ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | lc= TOK_LIKE r2= relationalExpr -> ^( $lc $equalityExpr $r2) | TOK_NOT lc2= TOK_LIKE r2= relationalExpr -> ^( TOK_NOTLIKE $equalityExpr $r2) | is= TOK_IS nul= TOK_NULL -> ^( $is $equalityExpr $nul) | is= TOK_IS return_not= TOK_NOT nul= TOK_NULL -> ^( TOK_ISNOT $equalityExpr $nul) )* ;
    public final filterParser.equalityExpr_return equalityExpr() throws RecognitionException {
        filterParser.equalityExpr_return retval = new filterParser.equalityExpr_return();
        retval.start = input.LT(1);
        int equalityExpr_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        Token lc2=null;
        Token is=null;
        Token nul=null;
        Token return_not=null;
        Token TOK_NOT6=null;
        filterParser.equalityOp_return e = null;

        filterParser.relationalExpr_return r1 = null;

        filterParser.relationalExpr_return r2 = null;

        filterParser.relationalExpr_return relationalExpr5 = null;


        Object lc_tree=null;
        Object lc2_tree=null;
        Object is_tree=null;
        Object nul_tree=null;
        Object return_not_tree=null;
        Object TOK_NOT6_tree=null;
        RewriteRuleTokenStream stream_TOK_NOT=new RewriteRuleTokenStream(adaptor,"token TOK_NOT");
        RewriteRuleTokenStream stream_TOK_IS=new RewriteRuleTokenStream(adaptor,"token TOK_IS");
        RewriteRuleTokenStream stream_TOK_LIKE=new RewriteRuleTokenStream(adaptor,"token TOK_LIKE");
        RewriteRuleTokenStream stream_TOK_NULL=new RewriteRuleTokenStream(adaptor,"token TOK_NULL");
        RewriteRuleSubtreeStream stream_equalityOp=new RewriteRuleSubtreeStream(adaptor,"rule equalityOp");
        RewriteRuleSubtreeStream stream_relationalExpr=new RewriteRuleSubtreeStream(adaptor,"rule relationalExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:307:14: ( ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | lc= TOK_LIKE r2= relationalExpr -> ^( $lc $equalityExpr $r2) | TOK_NOT lc2= TOK_LIKE r2= relationalExpr -> ^( TOK_NOTLIKE $equalityExpr $r2) | is= TOK_IS nul= TOK_NULL -> ^( $is $equalityExpr $nul) | is= TOK_IS return_not= TOK_NOT nul= TOK_NULL -> ^( TOK_ISNOT $equalityExpr $nul) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:308:8: ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | lc= TOK_LIKE r2= relationalExpr -> ^( $lc $equalityExpr $r2) | TOK_NOT lc2= TOK_LIKE r2= relationalExpr -> ^( TOK_NOTLIKE $equalityExpr $r2) | is= TOK_IS nul= TOK_NULL -> ^( $is $equalityExpr $nul) | is= TOK_IS return_not= TOK_NOT nul= TOK_NULL -> ^( TOK_ISNOT $equalityExpr $nul) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:308:8: ( relationalExpr -> relationalExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:308:10: relationalExpr
            {
            pushFollow(FOLLOW_relationalExpr_in_equalityExpr3898);
            relationalExpr5=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_relationalExpr.add(relationalExpr5.getTree());


            // AST REWRITE
            // elements: relationalExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 308:25: -> relationalExpr
            {
                adaptor.addChild(root_0, stream_relationalExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:309:8: (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | lc= TOK_LIKE r2= relationalExpr -> ^( $lc $equalityExpr $r2) | TOK_NOT lc2= TOK_LIKE r2= relationalExpr -> ^( TOK_NOTLIKE $equalityExpr $r2) | is= TOK_IS nul= TOK_NULL -> ^( $is $equalityExpr $nul) | is= TOK_IS return_not= TOK_NOT nul= TOK_NULL -> ^( TOK_ISNOT $equalityExpr $nul) )*
            loop3:
            do {
                int alt3=6;
                alt3 = dfa3.predict(input);
                switch (alt3) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:310:19: e= equalityOp r1= relationalExpr
            	    {
            	    pushFollow(FOLLOW_equalityOp_in_equalityExpr3935);
            	    e=equalityOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equalityOp.add(e.getTree());
            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr3977);
            	    r1=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalExpr.add(r1.getTree());


            	    // AST REWRITE
            	    // elements: e, equalityExpr, r1
            	    // token labels: 
            	    // rule labels: e, retval, r1
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_r1=new RewriteRuleSubtreeStream(adaptor,"rule r1",r1!=null?r1.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 312:37: -> ^( $e $equalityExpr $r1)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:312:40: ^( $e $equalityExpr $r1)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_e.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_r1.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 2 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:315:20: lc= TOK_LIKE r2= relationalExpr
            	    {
            	    lc=(Token)match(input,TOK_LIKE,FOLLOW_TOK_LIKE_in_equalityExpr4035); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_LIKE.add(lc);

            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr4039);
            	    r2=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalExpr.add(r2.getTree());


            	    // AST REWRITE
            	    // elements: r2, equalityExpr, lc
            	    // token labels: lc
            	    // rule labels: r2, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_lc=new RewriteRuleTokenStream(adaptor,"token lc",lc);
            	    RewriteRuleSubtreeStream stream_r2=new RewriteRuleSubtreeStream(adaptor,"rule r2",r2!=null?r2.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 315:51: -> ^( $lc $equalityExpr $r2)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:315:54: ^( $lc $equalityExpr $r2)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_lc.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_r2.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 3 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:316:20: TOK_NOT lc2= TOK_LIKE r2= relationalExpr
            	    {
            	    TOK_NOT6=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_equalityExpr4074); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_NOT.add(TOK_NOT6);

            	    lc2=(Token)match(input,TOK_LIKE,FOLLOW_TOK_LIKE_in_equalityExpr4078); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_LIKE.add(lc2);

            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr4082);
            	    r2=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalExpr.add(r2.getTree());


            	    // AST REWRITE
            	    // elements: r2, equalityExpr
            	    // token labels: 
            	    // rule labels: r2, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_r2=new RewriteRuleSubtreeStream(adaptor,"rule r2",r2!=null?r2.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 316:60: -> ^( TOK_NOTLIKE $equalityExpr $r2)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:316:63: ^( TOK_NOTLIKE $equalityExpr $r2)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_NOTLIKE, "TOK_NOTLIKE"), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_r2.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 4 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:317:20: is= TOK_IS nul= TOK_NULL
            	    {
            	    is=(Token)match(input,TOK_IS,FOLLOW_TOK_IS_in_equalityExpr4118); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_IS.add(is);

            	    nul=(Token)match(input,TOK_NULL,FOLLOW_TOK_NULL_in_equalityExpr4122); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_NULL.add(nul);



            	    // AST REWRITE
            	    // elements: nul, is, equalityExpr
            	    // token labels: nul, is
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_nul=new RewriteRuleTokenStream(adaptor,"token nul",nul);
            	    RewriteRuleTokenStream stream_is=new RewriteRuleTokenStream(adaptor,"token is",is);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 317:43: -> ^( $is $equalityExpr $nul)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:317:46: ^( $is $equalityExpr $nul)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_is.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_nul.nextNode());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 5 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:318:20: is= TOK_IS return_not= TOK_NOT nul= TOK_NULL
            	    {
            	    is=(Token)match(input,TOK_IS,FOLLOW_TOK_IS_in_equalityExpr4159); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_IS.add(is);

            	    return_not=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_equalityExpr4163); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_NOT.add(return_not);

            	    nul=(Token)match(input,TOK_NULL,FOLLOW_TOK_NULL_in_equalityExpr4167); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_NULL.add(nul);



            	    // AST REWRITE
            	    // elements: equalityExpr, nul
            	    // token labels: nul
            	    // rule labels: retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_nul=new RewriteRuleTokenStream(adaptor,"token nul",nul);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 318:62: -> ^( TOK_ISNOT $equalityExpr $nul)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:318:65: ^( TOK_ISNOT $equalityExpr $nul)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_ISNOT, "TOK_ISNOT"), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_nul.nextNode());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 5, equalityExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equalityExpr"

    public static class relationalOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalOp"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:322:1: relationalOp : ( TOK_LT | TOK_GT | TOK_LE | TOK_GE );
    public final filterParser.relationalOp_return relationalOp() throws RecognitionException {
        filterParser.relationalOp_return retval = new filterParser.relationalOp_return();
        retval.start = input.LT(1);
        int relationalOp_StartIndex = input.index();
        Object root_0 = null;

        Token set7=null;

        Object set7_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:322:13: ( TOK_LT | TOK_GT | TOK_LE | TOK_GE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            root_0 = (Object)adaptor.nil();

            set7=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_LE && input.LA(1)<=TOK_GE)||(input.LA(1)>=TOK_LT && input.LA(1)<=TOK_GT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set7));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 6, relationalOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalOp"

    public static class relationalExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:329:1: relationalExpr : (ae= additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) ) | bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) ) )* ;
    public final filterParser.relationalExpr_return relationalExpr() throws RecognitionException {
        filterParser.relationalExpr_return retval = new filterParser.relationalExpr_return();
        retval.start = input.LT(1);
        int relationalExpr_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        Token a1=null;
        Token bc=null;
        Token TOK_NOT8=null;
        Token TOK_BETWEEN9=null;
        filterParser.additiveExpr_return ae = null;

        filterParser.relationalOp_return r = null;

        filterParser.additiveExpr_return a = null;

        filterParser.additiveExpr_return a5 = null;

        filterParser.additiveExpr_return a6 = null;


        Object lc_tree=null;
        Object a1_tree=null;
        Object bc_tree=null;
        Object TOK_NOT8_tree=null;
        Object TOK_BETWEEN9_tree=null;
        RewriteRuleTokenStream stream_TOK_NOT=new RewriteRuleTokenStream(adaptor,"token TOK_NOT");
        RewriteRuleTokenStream stream_TOK_BETWEEN=new RewriteRuleTokenStream(adaptor,"token TOK_BETWEEN");
        RewriteRuleTokenStream stream_TOK_AND=new RewriteRuleTokenStream(adaptor,"token TOK_AND");
        RewriteRuleSubtreeStream stream_relationalOp=new RewriteRuleSubtreeStream(adaptor,"rule relationalOp");
        RewriteRuleSubtreeStream stream_additiveExpr=new RewriteRuleSubtreeStream(adaptor,"rule additiveExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:329:16: ( (ae= additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) ) | bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) ) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:330:8: (ae= additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) ) | bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) ) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:330:8: (ae= additiveExpr -> additiveExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:330:10: ae= additiveExpr
            {
            pushFollow(FOLLOW_additiveExpr_in_relationalExpr4290);
            ae=additiveExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_additiveExpr.add(ae.getTree());


            // AST REWRITE
            // elements: additiveExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 330:26: -> additiveExpr
            {
                adaptor.addChild(root_0, stream_additiveExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:331:8: (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) ) | bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) ) )*
            loop4:
            do {
                int alt4=4;
                alt4 = dfa4.predict(input);
                switch (alt4) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:332:12: r= relationalOp a= additiveExpr
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpr4320);
            	    r=relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalOp.add(r.getTree());
            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr4324);
            	    a=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: r, relationalExpr, a
            	    // token labels: 
            	    // rule labels: a, r, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);
            	    RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"rule r",r!=null?r.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 332:42: -> ^( $r $relationalExpr $a)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:332:45: ^( $r $relationalExpr $a)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_r.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 2 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:13: bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    {
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:16: (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:18: lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr
            	    {
            	    lc=(Token)match(input,TOK_BETWEEN,FOLLOW_TOK_BETWEEN_in_relationalExpr4357); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_BETWEEN.add(lc);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr4362);
            	    a5=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a5.getTree());
            	    a1=(Token)match(input,TOK_AND,FOLLOW_TOK_AND_in_relationalExpr4366); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_AND.add(a1);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr4370);
            	    a6=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a6.getTree());

            	    }



            	    // AST REWRITE
            	    // elements: lc, ae, a5, ae, a6
            	    // token labels: lc
            	    // rule labels: ae, a5, a6, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleTokenStream stream_lc=new RewriteRuleTokenStream(adaptor,"token lc",lc);
            	    RewriteRuleSubtreeStream stream_ae=new RewriteRuleSubtreeStream(adaptor,"rule ae",ae!=null?ae.tree:null);
            	    RewriteRuleSubtreeStream stream_a5=new RewriteRuleSubtreeStream(adaptor,"rule a5",a5!=null?a5.tree:null);
            	    RewriteRuleSubtreeStream stream_a6=new RewriteRuleSubtreeStream(adaptor,"rule a6",a6!=null?a6.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 333:79: -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) )
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:82: ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_lc.nextNode(), root_1);

            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:88: ^( TOK_GE $ae $a5)
            	        {
            	        Object root_2 = (Object)adaptor.nil();
            	        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_GE, "TOK_GE"), root_2);

            	        adaptor.addChild(root_2, stream_ae.nextTree());
            	        adaptor.addChild(root_2, stream_a5.nextTree());

            	        adaptor.addChild(root_1, root_2);
            	        }
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:333:106: ^( TOK_LE $ae $a6)
            	        {
            	        Object root_2 = (Object)adaptor.nil();
            	        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_LE, "TOK_LE"), root_2);

            	        adaptor.addChild(root_2, stream_ae.nextTree());
            	        adaptor.addChild(root_2, stream_a6.nextTree());

            	        adaptor.addChild(root_1, root_2);
            	        }

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 3 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:13: bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    {
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:16: ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:18: TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr
            	    {
            	    TOK_NOT8=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_relationalExpr4418); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_NOT.add(TOK_NOT8);

            	    TOK_BETWEEN9=(Token)match(input,TOK_BETWEEN,FOLLOW_TOK_BETWEEN_in_relationalExpr4420); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_BETWEEN.add(TOK_BETWEEN9);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr4425);
            	    a5=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a5.getTree());
            	    a1=(Token)match(input,TOK_AND,FOLLOW_TOK_AND_in_relationalExpr4429); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_AND.add(a1);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr4433);
            	    a6=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a6.getTree());

            	    }



            	    // AST REWRITE
            	    // elements: ae, a5, a6, ae
            	    // token labels: 
            	    // rule labels: ae, a5, a6, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_ae=new RewriteRuleSubtreeStream(adaptor,"rule ae",ae!=null?ae.tree:null);
            	    RewriteRuleSubtreeStream stream_a5=new RewriteRuleSubtreeStream(adaptor,"rule a5",a5!=null?a5.tree:null);
            	    RewriteRuleSubtreeStream stream_a6=new RewriteRuleSubtreeStream(adaptor,"rule a6",a6!=null?a6.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 334:84: -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) )
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:87: ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) )
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_NOTBETWEEN, "TOK_NOTBETWEEN"), root_1);

            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:104: ^( TOK_LT $ae $a5)
            	        {
            	        Object root_2 = (Object)adaptor.nil();
            	        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_LT, "TOK_LT"), root_2);

            	        adaptor.addChild(root_2, stream_ae.nextTree());
            	        adaptor.addChild(root_2, stream_a5.nextTree());

            	        adaptor.addChild(root_1, root_2);
            	        }
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:334:122: ^( TOK_GT $ae $a6)
            	        {
            	        Object root_2 = (Object)adaptor.nil();
            	        root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_GT, "TOK_GT"), root_2);

            	        adaptor.addChild(root_2, stream_ae.nextTree());
            	        adaptor.addChild(root_2, stream_a6.nextTree());

            	        adaptor.addChild(root_1, root_2);
            	        }

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 7, relationalExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalExpr"

    public static class additiveOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveOp"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:337:7: additiveOp : ( TOK_PLUS | TOK_MINUS | TOK_CONCAT );
    public final filterParser.additiveOp_return additiveOp() throws RecognitionException {
        filterParser.additiveOp_return retval = new filterParser.additiveOp_return();
        retval.start = input.LT(1);
        int additiveOp_StartIndex = input.index();
        Object root_0 = null;

        Token set10=null;

        Object set10_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:337:17: ( TOK_PLUS | TOK_MINUS | TOK_CONCAT )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            root_0 = (Object)adaptor.nil();

            set10=(Token)input.LT(1);
            if ( input.LA(1)==TOK_CONCAT||(input.LA(1)>=TOK_PLUS && input.LA(1)<=TOK_MINUS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set10));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 8, additiveOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additiveOp"

    public static class additiveExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:342:7: additiveExpr : ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )* ;
    public final filterParser.additiveExpr_return additiveExpr() throws RecognitionException {
        filterParser.additiveExpr_return retval = new filterParser.additiveExpr_return();
        retval.start = input.LT(1);
        int additiveExpr_StartIndex = input.index();
        Object root_0 = null;

        filterParser.additiveOp_return a = null;

        filterParser.multiplicativeExpr_return m = null;

        filterParser.multiplicativeExpr_return multiplicativeExpr11 = null;


        RewriteRuleSubtreeStream stream_additiveOp=new RewriteRuleSubtreeStream(adaptor,"rule additiveOp");
        RewriteRuleSubtreeStream stream_multiplicativeExpr=new RewriteRuleSubtreeStream(adaptor,"rule multiplicativeExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:342:20: ( ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:343:15: ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:343:15: ( multiplicativeExpr -> multiplicativeExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:343:17: multiplicativeExpr
            {
            pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr4577);
            multiplicativeExpr11=multiplicativeExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicativeExpr.add(multiplicativeExpr11.getTree());


            // AST REWRITE
            // elements: multiplicativeExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 343:36: -> multiplicativeExpr
            {
                adaptor.addChild(root_0, stream_multiplicativeExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:344:15: (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*
            loop5:
            do {
                int alt5=2;
                alt5 = dfa5.predict(input);
                switch (alt5) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:345:19: a= additiveOp m= multiplicativeExpr
            	    {
            	    pushFollow(FOLLOW_additiveOp_in_additiveExpr4621);
            	    a=additiveOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveOp.add(a.getTree());
            	    pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr4625);
            	    m=multiplicativeExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_multiplicativeExpr.add(m.getTree());


            	    // AST REWRITE
            	    // elements: m, a, additiveExpr
            	    // token labels: 
            	    // rule labels: a, m, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);
            	    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 345:53: -> ^( $a $additiveExpr $m)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:345:56: ^( $a $additiveExpr $m)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_a.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_m.nextTree());

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


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 9, additiveExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additiveExpr"

    public static class multiplicativeOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeOp"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:348:7: multiplicativeOp : ( TOK_STAR | TOK_SLASH | TOK_MOD );
    public final filterParser.multiplicativeOp_return multiplicativeOp() throws RecognitionException {
        filterParser.multiplicativeOp_return retval = new filterParser.multiplicativeOp_return();
        retval.start = input.LT(1);
        int multiplicativeOp_StartIndex = input.index();
        Object root_0 = null;

        Token set12=null;

        Object set12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:348:24: ( TOK_STAR | TOK_SLASH | TOK_MOD )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            root_0 = (Object)adaptor.nil();

            set12=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_SLASH && input.LA(1)<=TOK_STAR)||input.LA(1)==TOK_MOD ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set12));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 10, multiplicativeOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicativeOp"

    public static class multiplicativeExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:353:7: multiplicativeExpr : ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )* ;
    public final filterParser.multiplicativeExpr_return multiplicativeExpr() throws RecognitionException {
        filterParser.multiplicativeExpr_return retval = new filterParser.multiplicativeExpr_return();
        retval.start = input.LT(1);
        int multiplicativeExpr_StartIndex = input.index();
        Object root_0 = null;

        filterParser.multiplicativeOp_return m = null;

        filterParser.inExpr_return i = null;

        filterParser.inExpr_return inExpr13 = null;


        RewriteRuleSubtreeStream stream_inExpr=new RewriteRuleSubtreeStream(adaptor,"rule inExpr");
        RewriteRuleSubtreeStream stream_multiplicativeOp=new RewriteRuleSubtreeStream(adaptor,"rule multiplicativeOp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:353:26: ( ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:354:15: ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:354:15: ( inExpr -> inExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:354:17: inExpr
            {
            pushFollow(FOLLOW_inExpr_in_multiplicativeExpr4749);
            inExpr13=inExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_inExpr.add(inExpr13.getTree());


            // AST REWRITE
            // elements: inExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 354:24: -> inExpr
            {
                adaptor.addChild(root_0, stream_inExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:355:15: (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*
            loop6:
            do {
                int alt6=2;
                alt6 = dfa6.predict(input);
                switch (alt6) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:356:19: m= multiplicativeOp i= inExpr
            	    {
            	    pushFollow(FOLLOW_multiplicativeOp_in_multiplicativeExpr4793);
            	    m=multiplicativeOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_multiplicativeOp.add(m.getTree());
            	    pushFollow(FOLLOW_inExpr_in_multiplicativeExpr4797);
            	    i=inExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_inExpr.add(i.getTree());


            	    // AST REWRITE
            	    // elements: i, m, multiplicativeExpr
            	    // token labels: 
            	    // rule labels: i, m, retval
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_i=new RewriteRuleSubtreeStream(adaptor,"rule i",i!=null?i.tree:null);
            	    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            	    root_0 = (Object)adaptor.nil();
            	    // 356:47: -> ^( $m $multiplicativeExpr $i)
            	    {
            	        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:356:50: ^( $m $multiplicativeExpr $i)
            	        {
            	        Object root_1 = (Object)adaptor.nil();
            	        root_1 = (Object)adaptor.becomeRoot(stream_m.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_i.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 11, multiplicativeExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpr"

    public static class inExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:360:7: inExpr : (e= unaryExpr -> $e) (lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) ) | TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) ) )? ;
    public final filterParser.inExpr_return inExpr() throws RecognitionException {
        filterParser.inExpr_return retval = new filterParser.inExpr_return();
        retval.start = input.LT(1);
        int inExpr_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        Token TOK_LPAREN14=null;
        Token TOK_COMMA16=null;
        Token TOK_RPAREN18=null;
        Token TOK_NOT19=null;
        Token TOK_LPAREN20=null;
        Token TOK_COMMA22=null;
        Token TOK_RPAREN24=null;
        filterParser.unaryExpr_return e = null;

        filterParser.unaryExpr_return u = null;

        filterParser.field_return field15 = null;

        filterParser.field_return field17 = null;

        filterParser.field_return field21 = null;

        filterParser.field_return field23 = null;


        Object lc_tree=null;
        Object TOK_LPAREN14_tree=null;
        Object TOK_COMMA16_tree=null;
        Object TOK_RPAREN18_tree=null;
        Object TOK_NOT19_tree=null;
        Object TOK_LPAREN20_tree=null;
        Object TOK_COMMA22_tree=null;
        Object TOK_RPAREN24_tree=null;
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleTokenStream stream_TOK_NOT=new RewriteRuleTokenStream(adaptor,"token TOK_NOT");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleTokenStream stream_TOK_IN=new RewriteRuleTokenStream(adaptor,"token TOK_IN");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleSubtreeStream stream_unaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpr");
        RewriteRuleSubtreeStream stream_field=new RewriteRuleSubtreeStream(adaptor,"rule field");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:360:14: ( (e= unaryExpr -> $e) (lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) ) | TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) ) )? )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:361:15: (e= unaryExpr -> $e) (lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) ) | TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) ) )?
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:361:15: (e= unaryExpr -> $e)
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:362:16: e= unaryExpr
            {
            pushFollow(FOLLOW_unaryExpr_in_inExpr4885);
            e=unaryExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_unaryExpr.add(e.getTree());


            // AST REWRITE
            // elements: e
            // token labels: 
            // rule labels: e, retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 362:28: -> $e
            {
                adaptor.addChild(root_0, stream_e.nextTree());

            }

            retval.tree = root_0;}
            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:364:15: (lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) ) | TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) ) )?
            int alt11=3;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:365:19: lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) )
                    {
                    lc=(Token)match(input,TOK_IN,FOLLOW_TOK_IN_in_inExpr4944); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_IN.add(lc);

                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:366:19: (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) )
                    int alt8=2;
                    alt8 = dfa8.predict(input);
                    switch (alt8) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:367:19: u= unaryExpr
                            {
                            pushFollow(FOLLOW_unaryExpr_in_inExpr4986);
                            u=unaryExpr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_unaryExpr.add(u.getTree());


                            // AST REWRITE
                            // elements: inExpr, u
                            // token labels: 
                            // rule labels: u, retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_u=new RewriteRuleSubtreeStream(adaptor,"rule u",u!=null?u.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 367:32: -> ^( TOK_OR $inExpr $u)
                            {
                                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:367:35: ^( TOK_OR $inExpr $u)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_OR, "TOK_OR"), root_1);

                                adaptor.addChild(root_1, stream_retval.nextTree());
                                adaptor.addChild(root_1, stream_u.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:368:21: TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN
                            {
                            TOK_LPAREN14=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_inExpr5022); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN14);

                            pushFollow(FOLLOW_field_in_inExpr5024);
                            field15=field();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_field.add(field15.getTree());
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:368:38: ( TOK_COMMA field )*
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==TOK_COMMA) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:368:39: TOK_COMMA field
                            	    {
                            	    TOK_COMMA16=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_inExpr5027); if (state.failed) return retval; 
                            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA16);

                            	    pushFollow(FOLLOW_field_in_inExpr5029);
                            	    field17=field();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_field.add(field17.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop7;
                                }
                            } while (true);

                            TOK_RPAREN18=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_inExpr5033); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN18);



                            // AST REWRITE
                            // elements: field, e
                            // token labels: 
                            // rule labels: e, retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 368:68: -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ )
                            {
                                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:368:71: ^( TOK_OR ( ^( TOK_EQ $e field ) )+ )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_OR, "TOK_OR"), root_1);

                                if ( !(stream_field.hasNext()||stream_e.hasNext()) ) {
                                    throw new RewriteEarlyExitException();
                                }
                                while ( stream_field.hasNext()||stream_e.hasNext() ) {
                                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:368:80: ^( TOK_EQ $e field )
                                    {
                                    Object root_2 = (Object)adaptor.nil();
                                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_EQ, "TOK_EQ"), root_2);

                                    adaptor.addChild(root_2, stream_e.nextTree());
                                    adaptor.addChild(root_2, stream_field.nextTree());

                                    adaptor.addChild(root_1, root_2);
                                    }

                                }
                                stream_field.reset();
                                stream_e.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:371:21: TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) )
                    {
                    TOK_NOT19=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_inExpr5109); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_NOT.add(TOK_NOT19);

                    lc=(Token)match(input,TOK_IN,FOLLOW_TOK_IN_in_inExpr5113); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_IN.add(lc);

                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:373:19: (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) )
                    int alt10=2;
                    alt10 = dfa10.predict(input);
                    switch (alt10) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:374:19: u= unaryExpr
                            {
                            pushFollow(FOLLOW_unaryExpr_in_inExpr5174);
                            u=unaryExpr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_unaryExpr.add(u.getTree());


                            // AST REWRITE
                            // elements: inExpr, u
                            // token labels: 
                            // rule labels: u, retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_u=new RewriteRuleSubtreeStream(adaptor,"rule u",u!=null?u.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 374:32: -> ^( TOK_NOR $inExpr $u)
                            {
                                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:374:35: ^( TOK_NOR $inExpr $u)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_NOR, "TOK_NOR"), root_1);

                                adaptor.addChild(root_1, stream_retval.nextTree());
                                adaptor.addChild(root_1, stream_u.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:375:21: TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN
                            {
                            TOK_LPAREN20=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_inExpr5210); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN20);

                            pushFollow(FOLLOW_field_in_inExpr5212);
                            field21=field();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_field.add(field21.getTree());
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:375:38: ( TOK_COMMA field )*
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==TOK_COMMA) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:375:39: TOK_COMMA field
                            	    {
                            	    TOK_COMMA22=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_inExpr5215); if (state.failed) return retval; 
                            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA22);

                            	    pushFollow(FOLLOW_field_in_inExpr5217);
                            	    field23=field();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) stream_field.add(field23.getTree());

                            	    }
                            	    break;

                            	default :
                            	    break loop9;
                                }
                            } while (true);

                            TOK_RPAREN24=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_inExpr5221); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN24);



                            // AST REWRITE
                            // elements: field, e
                            // token labels: 
                            // rule labels: e, retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 375:68: -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ )
                            {
                                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:375:71: ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ )
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_NOR, "TOK_NOR"), root_1);

                                if ( !(stream_field.hasNext()||stream_e.hasNext()) ) {
                                    throw new RewriteEarlyExitException();
                                }
                                while ( stream_field.hasNext()||stream_e.hasNext() ) {
                                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:375:81: ^( TOK_EQ $e field )
                                    {
                                    Object root_2 = (Object)adaptor.nil();
                                    root_2 = (Object)adaptor.becomeRoot((Object)adaptor.create(TOK_EQ, "TOK_EQ"), root_2);

                                    adaptor.addChild(root_2, stream_e.nextTree());
                                    adaptor.addChild(root_2, stream_field.nextTree());

                                    adaptor.addChild(root_1, root_2);
                                    }

                                }
                                stream_field.reset();
                                stream_e.reset();

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 12, inExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inExpr"

    public static class unaryOp_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryOp"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:383:7: unaryOp : ( TOK_PLUS | TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text] | TOK_ABS | TOK_NOT );
    public final filterParser.unaryOp_return unaryOp() throws RecognitionException {
        filterParser.unaryOp_return retval = new filterParser.unaryOp_return();
        retval.start = input.LT(1);
        int unaryOp_StartIndex = input.index();
        Object root_0 = null;

        Token TOK_PLUS25=null;
        Token TOK_MINUS26=null;
        Token TOK_ABS27=null;
        Token TOK_NOT28=null;

        Object TOK_PLUS25_tree=null;
        Object TOK_MINUS26_tree=null;
        Object TOK_ABS27_tree=null;
        Object TOK_NOT28_tree=null;
        RewriteRuleTokenStream stream_TOK_MINUS=new RewriteRuleTokenStream(adaptor,"token TOK_MINUS");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:383:14: ( TOK_PLUS | TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text] | TOK_ABS | TOK_NOT )
            int alt12=4;
            switch ( input.LA(1) ) {
            case TOK_PLUS:
                {
                alt12=1;
                }
                break;
            case TOK_MINUS:
                {
                alt12=2;
                }
                break;
            case TOK_ABS:
                {
                alt12=3;
                }
                break;
            case TOK_NOT:
                {
                alt12=4;
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
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:384:19: TOK_PLUS
                    {
                    root_0 = (Object)adaptor.nil();

                    TOK_PLUS25=(Token)match(input,TOK_PLUS,FOLLOW_TOK_PLUS_in_unaryOp5349); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_PLUS25_tree = (Object)adaptor.create(TOK_PLUS25);
                    adaptor.addChild(root_0, TOK_PLUS25_tree);
                    }

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:385:19: TOK_MINUS
                    {
                    TOK_MINUS26=(Token)match(input,TOK_MINUS,FOLLOW_TOK_MINUS_in_unaryOp5369); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_MINUS.add(TOK_MINUS26);



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

                    root_0 = (Object)adaptor.nil();
                    // 385:29: -> UNARY_MINUS[$unaryOp.start,$unaryOp.text]
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(UNARY_MINUS, ((Token)retval.start), input.toString(retval.start,input.LT(-1))));

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:386:19: TOK_ABS
                    {
                    root_0 = (Object)adaptor.nil();

                    TOK_ABS27=(Token)match(input,TOK_ABS,FOLLOW_TOK_ABS_in_unaryOp5394); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_ABS27_tree = (Object)adaptor.create(TOK_ABS27);
                    adaptor.addChild(root_0, TOK_ABS27_tree);
                    }

                    }
                    break;
                case 4 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:387:19: TOK_NOT
                    {
                    root_0 = (Object)adaptor.nil();

                    TOK_NOT28=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_unaryOp5414); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_NOT28_tree = (Object)adaptor.create(TOK_NOT28);
                    adaptor.addChild(root_0, TOK_NOT28_tree);
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 13, unaryOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryOp"

    public static class unaryExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpr"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:390:7: unaryExpr : ( unaryOp x= unaryExpr -> ^( unaryOp $x) | term -> term );
    public final filterParser.unaryExpr_return unaryExpr() throws RecognitionException {
        filterParser.unaryExpr_return retval = new filterParser.unaryExpr_return();
        retval.start = input.LT(1);
        int unaryExpr_StartIndex = input.index();
        Object root_0 = null;

        filterParser.unaryExpr_return x = null;

        filterParser.unaryOp_return unaryOp29 = null;

        filterParser.term_return term30 = null;


        RewriteRuleSubtreeStream stream_unaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpr");
        RewriteRuleSubtreeStream stream_unaryOp=new RewriteRuleSubtreeStream(adaptor,"rule unaryOp");
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:390:17: ( unaryOp x= unaryExpr -> ^( unaryOp $x) | term -> term )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:391:12: unaryOp x= unaryExpr
                    {
                    pushFollow(FOLLOW_unaryOp_in_unaryExpr5452);
                    unaryOp29=unaryOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryOp.add(unaryOp29.getTree());
                    pushFollow(FOLLOW_unaryExpr_in_unaryExpr5456);
                    x=unaryExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryExpr.add(x.getTree());


                    // AST REWRITE
                    // elements: unaryOp, x
                    // token labels: 
                    // rule labels: x, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"rule x",x!=null?x.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 391:32: -> ^( unaryOp $x)
                    {
                        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:391:35: ^( unaryOp $x)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(stream_unaryOp.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_x.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:392:14: term
                    {
                    pushFollow(FOLLOW_term_in_unaryExpr5480);
                    term30=term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_term.add(term30.getTree());


                    // AST REWRITE
                    // elements: term
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 392:19: -> term
                    {
                        adaptor.addChild(root_0, stream_term.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 14, unaryExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryExpr"

    public static class term_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:395:1: term : ( TOK_LPAREN o= orExpr TOK_RPAREN -> ^( PARENTHESES_EXPRESSION $o) | literal -> literal | labelIdentifier -> labelIdentifier | bindVar -> bindVar );
    public final filterParser.term_return term() throws RecognitionException {
        filterParser.term_return retval = new filterParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        Object root_0 = null;

        Token TOK_LPAREN31=null;
        Token TOK_RPAREN32=null;
        filterParser.orExpr_return o = null;

        filterParser.literal_return literal33 = null;

        filterParser.labelIdentifier_return labelIdentifier34 = null;

        filterParser.bindVar_return bindVar35 = null;


        Object TOK_LPAREN31_tree=null;
        Object TOK_RPAREN32_tree=null;
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        RewriteRuleSubtreeStream stream_bindVar=new RewriteRuleSubtreeStream(adaptor,"rule bindVar");
        RewriteRuleSubtreeStream stream_orExpr=new RewriteRuleSubtreeStream(adaptor,"rule orExpr");
        RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:396:5: ( TOK_LPAREN o= orExpr TOK_RPAREN -> ^( PARENTHESES_EXPRESSION $o) | literal -> literal | labelIdentifier -> labelIdentifier | bindVar -> bindVar )
            int alt14=4;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:396:9: TOK_LPAREN o= orExpr TOK_RPAREN
                    {
                    TOK_LPAREN31=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_term5507); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN31);

                    pushFollow(FOLLOW_orExpr_in_term5511);
                    o=orExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpr.add(o.getTree());
                    TOK_RPAREN32=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_term5513); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN32);



                    // AST REWRITE
                    // elements: o
                    // token labels: 
                    // rule labels: retval, o
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_o=new RewriteRuleSubtreeStream(adaptor,"rule o",o!=null?o.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 396:40: -> ^( PARENTHESES_EXPRESSION $o)
                    {
                        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:396:43: ^( PARENTHESES_EXPRESSION $o)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(PARENTHESES_EXPRESSION, "PARENTHESES_EXPRESSION"), root_1);

                        adaptor.addChild(root_1, stream_o.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:397:9: literal
                    {
                    pushFollow(FOLLOW_literal_in_term5532);
                    literal33=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_literal.add(literal33.getTree());


                    // AST REWRITE
                    // elements: literal
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 397:17: -> literal
                    {
                        adaptor.addChild(root_0, stream_literal.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:398:9: labelIdentifier
                    {
                    pushFollow(FOLLOW_labelIdentifier_in_term5546);
                    labelIdentifier34=labelIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_labelIdentifier.add(labelIdentifier34.getTree());


                    // AST REWRITE
                    // elements: labelIdentifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 398:25: -> labelIdentifier
                    {
                        adaptor.addChild(root_0, stream_labelIdentifier.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:399:9: bindVar
                    {
                    pushFollow(FOLLOW_bindVar_in_term5560);
                    bindVar35=bindVar();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bindVar.add(bindVar35.getTree());


                    // AST REWRITE
                    // elements: bindVar
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 399:17: -> bindVar
                    {
                        adaptor.addChild(root_0, stream_bindVar.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 15, term_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "term"

    public static class bindVar_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindVar"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:402:1: bindVar : (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) ) ;
    public final filterParser.bindVar_return bindVar() throws RecognitionException {
        filterParser.bindVar_return retval = new filterParser.bindVar_return();
        retval.start = input.LT(1);
        int bindVar_StartIndex = input.index();
        Object root_0 = null;

        Token lc=null;
        filterParser.labelIdentifier_return labelIdentifier36 = null;


        Object lc_tree=null;
        RewriteRuleTokenStream stream_TOK_DOLLAR=new RewriteRuleTokenStream(adaptor,"token TOK_DOLLAR");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:402:9: ( (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:403:2: (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:403:2: (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:404:4: lc= TOK_DOLLAR labelIdentifier
            {
            lc=(Token)match(input,TOK_DOLLAR,FOLLOW_TOK_DOLLAR_in_bindVar5585); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_DOLLAR.add(lc);

            pushFollow(FOLLOW_labelIdentifier_in_bindVar5587);
            labelIdentifier36=labelIdentifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_labelIdentifier.add(labelIdentifier36.getTree());


            // AST REWRITE
            // elements: labelIdentifier
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 404:34: -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier )
            {
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:404:37: ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(BIND_VARIABLE_EXPRESSION, lc, input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_labelIdentifier.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 16, bindVar_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "bindVar"

    public static class labelIdentifier_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "labelIdentifier"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:407:1: labelIdentifier : ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] | TOK_AT Identifier -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text] ) ;
    public final filterParser.labelIdentifier_return labelIdentifier() throws RecognitionException {
        filterParser.labelIdentifier_return retval = new filterParser.labelIdentifier_return();
        retval.start = input.LT(1);
        int labelIdentifier_StartIndex = input.index();
        Object root_0 = null;

        Token TOK_HASH37=null;
        Token Identifier39=null;
        Token TOK_AT40=null;
        Token Identifier41=null;
        filterParser.keyWords_return keyWords38 = null;


        Object TOK_HASH37_tree=null;
        Object Identifier39_tree=null;
        Object TOK_AT40_tree=null;
        Object Identifier41_tree=null;
        RewriteRuleTokenStream stream_TOK_AT=new RewriteRuleTokenStream(adaptor,"token TOK_AT");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleTokenStream stream_TOK_HASH=new RewriteRuleTokenStream(adaptor,"token TOK_HASH");
        RewriteRuleSubtreeStream stream_keyWords=new RewriteRuleSubtreeStream(adaptor,"rule keyWords");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:407:17: ( ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] | TOK_AT Identifier -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text] ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:408:9: ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] | TOK_AT Identifier -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text] )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:408:9: ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] | TOK_AT Identifier -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text] )
            int alt15=3;
            switch ( input.LA(1) ) {
            case TOK_HASH:
                {
                alt15=1;
                }
                break;
            case Identifier:
                {
                alt15=2;
                }
                break;
            case TOK_AT:
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
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:409:13: ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] )
                    {
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:409:13: ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:410:17: TOK_HASH keyWords
                    {
                    TOK_HASH37=(Token)match(input,TOK_HASH,FOLLOW_TOK_HASH_in_labelIdentifier5652); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_HASH.add(TOK_HASH37);

                    pushFollow(FOLLOW_keyWords_in_labelIdentifier5654);
                    keyWords38=keyWords();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_keyWords.add(keyWords38.getTree());


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

                    root_0 = (Object)adaptor.nil();
                    // 410:35: -> IDENTIFIER[$labelIdentifier.start,$keyWords.text]
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(IDENTIFIER, ((Token)retval.start), (keyWords38!=null?input.toString(keyWords38.start,keyWords38.stop):null)));

                    }

                    retval.tree = root_0;}
                    }


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:412:15: Identifier
                    {
                    Identifier39=(Token)match(input,Identifier,FOLLOW_Identifier_in_labelIdentifier5688); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier39);



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

                    root_0 = (Object)adaptor.nil();
                    // 412:27: -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text]
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(IDENTIFIER, ((Token)retval.start), input.toString(retval.start,input.LT(-1))));

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:413:15: TOK_AT Identifier
                    {
                    TOK_AT40=(Token)match(input,TOK_AT,FOLLOW_TOK_AT_in_labelIdentifier5709); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_AT.add(TOK_AT40);

                    Identifier41=(Token)match(input,Identifier,FOLLOW_Identifier_in_labelIdentifier5711); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier41);



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

                    root_0 = (Object)adaptor.nil();
                    // 413:33: -> PROPERTY[$labelIdentifier.start,$labelIdentifier.text]
                    {
                        adaptor.addChild(root_0, (Object)adaptor.create(PROPERTY, ((Token)retval.start), input.toString(retval.start,input.LT(-1))));

                    }

                    retval.tree = root_0;}
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 17, labelIdentifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "labelIdentifier"

    public static class valueList_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "valueList"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:417:1: valueList : (f= field ( TOK_COMMA f= field )* -> ( field )+ ) ;
    public final filterParser.valueList_return valueList() throws RecognitionException {
        filterParser.valueList_return retval = new filterParser.valueList_return();
        retval.start = input.LT(1);
        int valueList_StartIndex = input.index();
        Object root_0 = null;

        Token TOK_COMMA42=null;
        filterParser.field_return f = null;


        Object TOK_COMMA42_tree=null;
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_field=new RewriteRuleSubtreeStream(adaptor,"rule field");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:417:11: ( (f= field ( TOK_COMMA f= field )* -> ( field )+ ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:418:12: (f= field ( TOK_COMMA f= field )* -> ( field )+ )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:418:12: (f= field ( TOK_COMMA f= field )* -> ( field )+ )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:419:15: f= field ( TOK_COMMA f= field )*
            {
            pushFollow(FOLLOW_field_in_valueList5772);
            f=field();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_field.add(f.getTree());
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:420:15: ( TOK_COMMA f= field )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==TOK_COMMA) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:421:19: TOK_COMMA f= field
            	    {
            	    TOK_COMMA42=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_valueList5808); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA42);

            	    pushFollow(FOLLOW_field_in_valueList5831);
            	    f=field();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_field.add(f.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);



            // AST REWRITE
            // elements: field
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 423:20: -> ( field )+
            {
                if ( !(stream_field.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_field.hasNext() ) {
                    adaptor.addChild(root_0, stream_field.nextTree());

                }
                stream_field.reset();

            }

            retval.tree = root_0;}
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 18, valueList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "valueList"

    public static class field_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "field"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:428:1: field : ( orExpr ) ;
    public final filterParser.field_return field() throws RecognitionException {
        filterParser.field_return retval = new filterParser.field_return();
        retval.start = input.LT(1);
        int field_StartIndex = input.index();
        Object root_0 = null;

        filterParser.orExpr_return orExpr43 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:428:7: ( ( orExpr ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:429:8: ( orExpr )
            {
            root_0 = (Object)adaptor.nil();

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:429:8: ( orExpr )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:430:9: orExpr
            {
            pushFollow(FOLLOW_orExpr_in_field5908);
            orExpr43=orExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, orExpr43.getTree());

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 19, field_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "field"

    public static class literal_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:435:2: literal : ( objectLiteral | booleanLiteral | DIGITS | decimalLiteral | doubleLiteral | dateLiteral | timeLiteral | datetimeLiteral | stringLiteral | hexLiteral | octalLiteral );
    public final filterParser.literal_return literal() throws RecognitionException {
        filterParser.literal_return retval = new filterParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        Object root_0 = null;

        Token DIGITS46=null;
        filterParser.objectLiteral_return objectLiteral44 = null;

        filterParser.booleanLiteral_return booleanLiteral45 = null;

        filterParser.decimalLiteral_return decimalLiteral47 = null;

        filterParser.doubleLiteral_return doubleLiteral48 = null;

        filterParser.dateLiteral_return dateLiteral49 = null;

        filterParser.timeLiteral_return timeLiteral50 = null;

        filterParser.datetimeLiteral_return datetimeLiteral51 = null;

        filterParser.stringLiteral_return stringLiteral52 = null;

        filterParser.hexLiteral_return hexLiteral53 = null;

        filterParser.octalLiteral_return octalLiteral54 = null;


        Object DIGITS46_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:435:10: ( objectLiteral | booleanLiteral | DIGITS | decimalLiteral | doubleLiteral | dateLiteral | timeLiteral | datetimeLiteral | stringLiteral | hexLiteral | octalLiteral )
            int alt17=11;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:437:19: objectLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_objectLiteral_in_literal5956);
                    objectLiteral44=objectLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, objectLiteral44.getTree());

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:438:19: booleanLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_booleanLiteral_in_literal5977);
                    booleanLiteral45=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, booleanLiteral45.getTree());

                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:439:19: DIGITS
                    {
                    root_0 = (Object)adaptor.nil();

                    DIGITS46=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_literal5997); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DIGITS46_tree = (Object)adaptor.create(DIGITS46);
                    adaptor.addChild(root_0, DIGITS46_tree);
                    }

                    }
                    break;
                case 4 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:440:19: decimalLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_decimalLiteral_in_literal6018);
                    decimalLiteral47=decimalLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, decimalLiteral47.getTree());

                    }
                    break;
                case 5 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:441:19: doubleLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_doubleLiteral_in_literal6039);
                    doubleLiteral48=doubleLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doubleLiteral48.getTree());

                    }
                    break;
                case 6 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:442:19: dateLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_dateLiteral_in_literal6060);
                    dateLiteral49=dateLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dateLiteral49.getTree());

                    }
                    break;
                case 7 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:443:19: timeLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_timeLiteral_in_literal6080);
                    timeLiteral50=timeLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeLiteral50.getTree());

                    }
                    break;
                case 8 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:444:19: datetimeLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_datetimeLiteral_in_literal6101);
                    datetimeLiteral51=datetimeLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, datetimeLiteral51.getTree());

                    }
                    break;
                case 9 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:445:19: stringLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_stringLiteral_in_literal6121);
                    stringLiteral52=stringLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, stringLiteral52.getTree());

                    }
                    break;
                case 10 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:446:19: hexLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_hexLiteral_in_literal6142);
                    hexLiteral53=hexLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, hexLiteral53.getTree());

                    }
                    break;
                case 11 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:447:19: octalLiteral
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_octalLiteral_in_literal6164);
                    octalLiteral54=octalLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, octalLiteral54.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 20, literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class objectLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objectLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:450:7: objectLiteral : TOK_NULL ;
    public final filterParser.objectLiteral_return objectLiteral() throws RecognitionException {
        filterParser.objectLiteral_return retval = new filterParser.objectLiteral_return();
        retval.start = input.LT(1);
        int objectLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token TOK_NULL55=null;

        Object TOK_NULL55_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:450:21: ( TOK_NULL )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:452:15: TOK_NULL
            {
            root_0 = (Object)adaptor.nil();

            TOK_NULL55=(Token)match(input,TOK_NULL,FOLLOW_TOK_NULL_in_objectLiteral6204); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TOK_NULL55_tree = (Object)adaptor.create(TOK_NULL55);
            adaptor.addChild(root_0, TOK_NULL55_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 21, objectLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "objectLiteral"

    public static class booleanLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:455:7: booleanLiteral : ( TOK_TRUE | TOK_FALSE ) ;
    public final filterParser.booleanLiteral_return booleanLiteral() throws RecognitionException {
        filterParser.booleanLiteral_return retval = new filterParser.booleanLiteral_return();
        retval.start = input.LT(1);
        int booleanLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token set56=null;

        Object set56_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:455:22: ( ( TOK_TRUE | TOK_FALSE ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:457:15: ( TOK_TRUE | TOK_FALSE )
            {
            root_0 = (Object)adaptor.nil();

            set56=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_TRUE && input.LA(1)<=TOK_FALSE) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set56));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 22, booleanLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "booleanLiteral"

    public static class decimalLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "decimalLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:463:7: decimalLiteral : DecimalLiteral ;
    public final filterParser.decimalLiteral_return decimalLiteral() throws RecognitionException {
        filterParser.decimalLiteral_return retval = new filterParser.decimalLiteral_return();
        retval.start = input.LT(1);
        int decimalLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token DecimalLiteral57=null;

        Object DecimalLiteral57_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:463:22: ( DecimalLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:464:13: DecimalLiteral
            {
            root_0 = (Object)adaptor.nil();

            DecimalLiteral57=(Token)match(input,DecimalLiteral,FOLLOW_DecimalLiteral_in_decimalLiteral6337); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DecimalLiteral57_tree = (Object)adaptor.create(DecimalLiteral57);
            adaptor.addChild(root_0, DecimalLiteral57_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 23, decimalLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "decimalLiteral"

    public static class hexLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hexLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:467:7: hexLiteral : HexLiteral ;
    public final filterParser.hexLiteral_return hexLiteral() throws RecognitionException {
        filterParser.hexLiteral_return retval = new filterParser.hexLiteral_return();
        retval.start = input.LT(1);
        int hexLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token HexLiteral58=null;

        Object HexLiteral58_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:467:18: ( HexLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:468:11: HexLiteral
            {
            root_0 = (Object)adaptor.nil();

            HexLiteral58=(Token)match(input,HexLiteral,FOLLOW_HexLiteral_in_hexLiteral6368); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            HexLiteral58_tree = (Object)adaptor.create(HexLiteral58);
            adaptor.addChild(root_0, HexLiteral58_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 24, hexLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "hexLiteral"

    public static class octalLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "octalLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:471:7: octalLiteral : OctalLiteral ;
    public final filterParser.octalLiteral_return octalLiteral() throws RecognitionException {
        filterParser.octalLiteral_return retval = new filterParser.octalLiteral_return();
        retval.start = input.LT(1);
        int octalLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token OctalLiteral59=null;

        Object OctalLiteral59_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:471:20: ( OctalLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:472:10: OctalLiteral
            {
            root_0 = (Object)adaptor.nil();

            OctalLiteral59=(Token)match(input,OctalLiteral,FOLLOW_OctalLiteral_in_octalLiteral6398); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OctalLiteral59_tree = (Object)adaptor.create(OctalLiteral59);
            adaptor.addChild(root_0, OctalLiteral59_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 25, octalLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "octalLiteral"

    public static class doubleLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "doubleLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:475:7: doubleLiteral : ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL ) ;
    public final filterParser.doubleLiteral_return doubleLiteral() throws RecognitionException {
        filterParser.doubleLiteral_return retval = new filterParser.doubleLiteral_return();
        retval.start = input.LT(1);
        int doubleLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token set60=null;

        Object set60_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:475:21: ( ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:476:15: ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL )
            {
            root_0 = (Object)adaptor.nil();

            set60=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_APPROXIMATE_NUMERIC_LITERAL && input.LA(1)<=TOK_EXACT_NUMERIC_LITERAL) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set60));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 26, doubleLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "doubleLiteral"

    public static class stringLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stringLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:483:7: stringLiteral : StringLiteral ;
    public final filterParser.stringLiteral_return stringLiteral() throws RecognitionException {
        filterParser.stringLiteral_return retval = new filterParser.stringLiteral_return();
        retval.start = input.LT(1);
        int stringLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token StringLiteral61=null;

        Object StringLiteral61_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:483:21: ( StringLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:484:16: StringLiteral
            {
            root_0 = (Object)adaptor.nil();

            StringLiteral61=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_stringLiteral6530); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            StringLiteral61_tree = (Object)adaptor.create(StringLiteral61);
            adaptor.addChild(root_0, StringLiteral61_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 27, stringLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "stringLiteral"

    public static class dateLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dateLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:487:7: dateLiteral : DateLiteral ;
    public final filterParser.dateLiteral_return dateLiteral() throws RecognitionException {
        filterParser.dateLiteral_return retval = new filterParser.dateLiteral_return();
        retval.start = input.LT(1);
        int dateLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token DateLiteral62=null;

        Object DateLiteral62_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:487:19: ( DateLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:488:15: DateLiteral
            {
            root_0 = (Object)adaptor.nil();

            DateLiteral62=(Token)match(input,DateLiteral,FOLLOW_DateLiteral_in_dateLiteral6569); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DateLiteral62_tree = (Object)adaptor.create(DateLiteral62);
            adaptor.addChild(root_0, DateLiteral62_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 28, dateLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dateLiteral"

    public static class timeLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "timeLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:491:7: timeLiteral : TimeLiteral ;
    public final filterParser.timeLiteral_return timeLiteral() throws RecognitionException {
        filterParser.timeLiteral_return retval = new filterParser.timeLiteral_return();
        retval.start = input.LT(1);
        int timeLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token TimeLiteral63=null;

        Object TimeLiteral63_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:491:19: ( TimeLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:492:15: TimeLiteral
            {
            root_0 = (Object)adaptor.nil();

            TimeLiteral63=(Token)match(input,TimeLiteral,FOLLOW_TimeLiteral_in_timeLiteral6608); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TimeLiteral63_tree = (Object)adaptor.create(TimeLiteral63);
            adaptor.addChild(root_0, TimeLiteral63_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 29, timeLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "timeLiteral"

    public static class datetimeLiteral_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "datetimeLiteral"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:495:7: datetimeLiteral : DateTimeLiteral ;
    public final filterParser.datetimeLiteral_return datetimeLiteral() throws RecognitionException {
        filterParser.datetimeLiteral_return retval = new filterParser.datetimeLiteral_return();
        retval.start = input.LT(1);
        int datetimeLiteral_StartIndex = input.index();
        Object root_0 = null;

        Token DateTimeLiteral64=null;

        Object DateTimeLiteral64_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:495:23: ( DateTimeLiteral )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:496:15: DateTimeLiteral
            {
            root_0 = (Object)adaptor.nil();

            DateTimeLiteral64=(Token)match(input,DateTimeLiteral,FOLLOW_DateTimeLiteral_in_datetimeLiteral6647); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DateTimeLiteral64_tree = (Object)adaptor.create(DateTimeLiteral64);
            adaptor.addChild(root_0, DateTimeLiteral64_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 30, datetimeLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "datetimeLiteral"

    public static class keyWords_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyWords"
    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:500:7: keyWords : ( TOK_IN | TOK_OR | TOK_AND | TOK_LIKE | TOK_BETWEEN | TOK_MOD | TOK_ABS | TOK_NOT | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_DOUBLE | TOK_IS );
    public final filterParser.keyWords_return keyWords() throws RecognitionException {
        filterParser.keyWords_return retval = new filterParser.keyWords_return();
        retval.start = input.LT(1);
        int keyWords_StartIndex = input.index();
        Object root_0 = null;

        Token set65=null;

        Object set65_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:500:15: ( TOK_IN | TOK_OR | TOK_AND | TOK_LIKE | TOK_BETWEEN | TOK_MOD | TOK_ABS | TOK_NOT | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_DOUBLE | TOK_IS )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            root_0 = (Object)adaptor.nil();

            set65=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_IN && input.LA(1)<=TOK_IS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (Object)adaptor.create(set65));
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

                    catch (RecognitionException rece) {
                        throw rece;
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 31, keyWords_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "keyWords"

    // $ANTLR start synpred21_filter
    public final void synpred21_filter_fragment() throws RecognitionException {   
        filterParser.unaryExpr_return u = null;


        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:367:19: (u= unaryExpr )
        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:367:19: u= unaryExpr
        {
        pushFollow(FOLLOW_unaryExpr_in_synpred21_filter4986);
        u=unaryExpr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_filter

    // $ANTLR start synpred24_filter
    public final void synpred24_filter_fragment() throws RecognitionException {   
        filterParser.unaryExpr_return u = null;


        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:374:19: (u= unaryExpr )
        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:374:19: u= unaryExpr
        {
        pushFollow(FOLLOW_unaryExpr_in_synpred24_filter5174);
        u=unaryExpr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_filter

    // Delegated rules

    public final boolean synpred24_filter() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_filter_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_filter() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_filter_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA3_eotS =
        "\14\uffff";
    static final String DFA3_eofS =
        "\1\1\13\uffff";
    static final String DFA3_minS =
        "\1\22\10\uffff\1\54\2\uffff";
    static final String DFA3_maxS =
        "\1\61\10\uffff\1\55\2\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\6\4\uffff\1\1\1\2\1\3\1\uffff\1\4\1\5";
    static final String DFA3_specialS =
        "\14\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\1\uffff\1\1\3\uffff\1\6\6\uffff\1\6\6\uffff\2\1\1\7\3\uffff"+
            "\1\10\4\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\13\1\12",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "()* loopback of 309:8: (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | lc= TOK_LIKE r2= relationalExpr -> ^( $lc $equalityExpr $r2) | TOK_NOT lc2= TOK_LIKE r2= relationalExpr -> ^( TOK_NOTLIKE $equalityExpr $r2) | is= TOK_IS nul= TOK_NULL -> ^( $is $equalityExpr $nul) | is= TOK_IS return_not= TOK_NOT nul= TOK_NULL -> ^( TOK_ISNOT $equalityExpr $nul) )*";
        }
    }
    static final String DFA4_eotS =
        "\16\uffff";
    static final String DFA4_eofS =
        "\1\1\15\uffff";
    static final String DFA4_minS =
        "\1\22\2\uffff\1\50\12\uffff";
    static final String DFA4_maxS =
        "\1\61\2\uffff\1\51\12\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\4\10\uffff\1\1\1\2\1\uffff\1\3";
    static final String DFA4_specialS =
        "\16\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1\1\uffff\1\1\3\uffff\1\1\4\uffff\2\12\1\1\2\12\4\uffff\3"+
            "\1\1\13\2\uffff\1\3\4\uffff\1\1",
            "",
            "",
            "\1\1\1\15",
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

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "()* loopback of 331:8: (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | bc= (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( $lc ^( TOK_GE $ae $a5) ^( TOK_LE $ae $a6) ) | bc= ( TOK_NOT TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( TOK_NOTBETWEEN ^( TOK_LT $ae $a5) ^( TOK_GT $ae $a6) ) )*";
        }
    }
    static final String DFA5_eotS =
        "\15\uffff";
    static final String DFA5_eofS =
        "\1\1\14\uffff";
    static final String DFA5_minS =
        "\1\22\14\uffff";
    static final String DFA5_maxS =
        "\1\61\14\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\2\12\uffff\1\1";
    static final String DFA5_specialS =
        "\15\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1\1\uffff\1\1\2\uffff\1\14\1\1\2\14\2\uffff\5\1\4\uffff\4"+
            "\1\2\uffff\1\1\4\uffff\1\1",
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

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "()* loopback of 344:15: (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*";
        }
    }
    static final String DFA6_eotS =
        "\16\uffff";
    static final String DFA6_eofS =
        "\1\1\15\uffff";
    static final String DFA6_minS =
        "\1\22\15\uffff";
    static final String DFA6_maxS =
        "\1\61\15\uffff";
    static final String DFA6_acceptS =
        "\1\uffff\1\2\13\uffff\1\1";
    static final String DFA6_specialS =
        "\16\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1\1\uffff\1\1\2\uffff\4\1\2\15\5\1\4\uffff\4\1\1\15\1\uffff"+
            "\1\1\4\uffff\1\1",
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
            return "()* loopback of 355:15: (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*";
        }
    }
    static final String DFA11_eotS =
        "\22\uffff";
    static final String DFA11_eofS =
        "\1\3\21\uffff";
    static final String DFA11_minS =
        "\1\22\1\uffff\1\45\17\uffff";
    static final String DFA11_maxS =
        "\1\61\1\uffff\1\51\17\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\13\uffff\1\2\2\uffff";
    static final String DFA11_specialS =
        "\22\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\3\1\uffff\1\3\2\uffff\13\3\3\uffff\1\1\5\3\1\uffff\1\2\4"+
            "\uffff\1\3",
            "",
            "\1\17\2\uffff\2\3",
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

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "364:15: (lc= TOK_IN (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) ) | TOK_NOT lc= TOK_IN (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) ) )?";
        }
    }
    static final String DFA8_eotS =
        "\52\uffff";
    static final String DFA8_eofS =
        "\52\uffff";
    static final String DFA8_minS =
        "\1\23\4\uffff\1\23\17\uffff\24\0\1\uffff";
    static final String DFA8_maxS =
        "\1\117\4\uffff\1\117\17\uffff\24\0\1\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\47\uffff\1\2";
    static final String DFA8_specialS =
        "\25\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\5\5\uffff\2\1\7\uffff\3\1\6\uffff\5\1\13\uffff\1\1\6\uffff"+
            "\3\1\4\uffff\7\1",
            "",
            "",
            "",
            "",
            "\1\31\5\uffff\1\25\1\26\7\uffff\1\50\1\45\1\47\6\uffff\1\27"+
            "\1\30\1\32\2\33\13\uffff\1\43\6\uffff\1\34\1\35\1\44\4\uffff"+
            "\1\37\1\40\1\41\1\42\2\36\1\46",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "366:19: (u= unaryExpr -> ^( TOK_OR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_OR ( ^( TOK_EQ $e field ) )+ ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_21 = input.LA(1);

                         
                        int index8_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_21);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_22 = input.LA(1);

                         
                        int index8_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_22);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_23 = input.LA(1);

                         
                        int index8_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_23);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_24 = input.LA(1);

                         
                        int index8_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_24);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_25 = input.LA(1);

                         
                        int index8_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_25);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA8_26 = input.LA(1);

                         
                        int index8_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_26);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA8_27 = input.LA(1);

                         
                        int index8_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_27);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA8_28 = input.LA(1);

                         
                        int index8_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_28);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA8_29 = input.LA(1);

                         
                        int index8_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_29);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA8_30 = input.LA(1);

                         
                        int index8_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_30);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA8_31 = input.LA(1);

                         
                        int index8_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_31);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA8_32 = input.LA(1);

                         
                        int index8_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_32);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA8_33 = input.LA(1);

                         
                        int index8_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_33);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA8_34 = input.LA(1);

                         
                        int index8_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_34);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA8_35 = input.LA(1);

                         
                        int index8_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_35);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA8_36 = input.LA(1);

                         
                        int index8_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_36);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA8_37 = input.LA(1);

                         
                        int index8_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_37);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA8_38 = input.LA(1);

                         
                        int index8_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_38);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA8_39 = input.LA(1);

                         
                        int index8_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_39);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA8_40 = input.LA(1);

                         
                        int index8_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred21_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index8_40);
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
        "\52\uffff";
    static final String DFA10_eofS =
        "\52\uffff";
    static final String DFA10_minS =
        "\1\23\4\uffff\1\23\17\uffff\24\0\1\uffff";
    static final String DFA10_maxS =
        "\1\117\4\uffff\1\117\17\uffff\24\0\1\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\47\uffff\1\2";
    static final String DFA10_specialS =
        "\25\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\5\5\uffff\2\1\7\uffff\3\1\6\uffff\5\1\13\uffff\1\1\6\uffff"+
            "\3\1\4\uffff\7\1",
            "",
            "",
            "",
            "",
            "\1\31\5\uffff\1\25\1\26\7\uffff\1\50\1\45\1\47\6\uffff\1\27"+
            "\1\30\1\32\2\33\13\uffff\1\43\6\uffff\1\34\1\35\1\44\4\uffff"+
            "\1\37\1\40\1\41\1\42\2\36\1\46",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "373:19: (u= unaryExpr -> ^( TOK_NOR $inExpr $u) | TOK_LPAREN field ( TOK_COMMA field )* TOK_RPAREN -> ^( TOK_NOR ( ^( TOK_EQ $e field ) )+ ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_21 = input.LA(1);

                         
                        int index10_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_21);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_22 = input.LA(1);

                         
                        int index10_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_22);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA10_23 = input.LA(1);

                         
                        int index10_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_23);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA10_24 = input.LA(1);

                         
                        int index10_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_24);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA10_25 = input.LA(1);

                         
                        int index10_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_25);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA10_26 = input.LA(1);

                         
                        int index10_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_26);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA10_27 = input.LA(1);

                         
                        int index10_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_27);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA10_28 = input.LA(1);

                         
                        int index10_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_28);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA10_29 = input.LA(1);

                         
                        int index10_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_29);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA10_30 = input.LA(1);

                         
                        int index10_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_30);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA10_31 = input.LA(1);

                         
                        int index10_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_31);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA10_32 = input.LA(1);

                         
                        int index10_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_32);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA10_33 = input.LA(1);

                         
                        int index10_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_33);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA10_34 = input.LA(1);

                         
                        int index10_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_34);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA10_35 = input.LA(1);

                         
                        int index10_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_35);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA10_36 = input.LA(1);

                         
                        int index10_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_36);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA10_37 = input.LA(1);

                         
                        int index10_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_37);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA10_38 = input.LA(1);

                         
                        int index10_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_38);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA10_39 = input.LA(1);

                         
                        int index10_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_39);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA10_40 = input.LA(1);

                         
                        int index10_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_filter()) ) {s = 1;}

                        else if ( (true) ) {s = 41;}

                         
                        input.seek(index10_40);
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
    static final String DFA13_eotS =
        "\25\uffff";
    static final String DFA13_eofS =
        "\25\uffff";
    static final String DFA13_minS =
        "\1\23\24\uffff";
    static final String DFA13_maxS =
        "\1\117\24\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\17\uffff";
    static final String DFA13_specialS =
        "\25\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\5\5\uffff\2\1\7\uffff\3\5\6\uffff\2\1\3\5\13\uffff\1\5\6"+
            "\uffff\3\5\4\uffff\7\5",
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

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "390:7: unaryExpr : ( unaryOp x= unaryExpr -> ^( unaryOp $x) | term -> term );";
        }
    }
    static final String DFA14_eotS =
        "\21\uffff";
    static final String DFA14_eofS =
        "\21\uffff";
    static final String DFA14_minS =
        "\1\23\20\uffff";
    static final String DFA14_maxS =
        "\1\117\20\uffff";
    static final String DFA14_acceptS =
        "\1\uffff\1\1\1\2\12\uffff\1\3\2\uffff\1\4";
    static final String DFA14_specialS =
        "\21\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\1\16\uffff\1\20\2\15\10\uffff\3\2\13\uffff\1\2\6\uffff\3"+
            "\2\4\uffff\6\2\1\15",
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

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "395:1: term : ( TOK_LPAREN o= orExpr TOK_RPAREN -> ^( PARENTHESES_EXPRESSION $o) | literal -> literal | labelIdentifier -> labelIdentifier | bindVar -> bindVar );";
        }
    }
    static final String DFA17_eotS =
        "\14\uffff";
    static final String DFA17_eofS =
        "\14\uffff";
    static final String DFA17_minS =
        "\1\55\13\uffff";
    static final String DFA17_maxS =
        "\1\116\13\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13";
    static final String DFA17_specialS =
        "\14\uffff}>";
    static final String[] DFA17_transitionS = {
            "\1\1\2\2\13\uffff\1\12\6\uffff\1\3\1\4\1\13\4\uffff\1\6\1\7"+
            "\1\10\1\11\2\5",
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

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "435:2: literal : ( objectLiteral | booleanLiteral | DIGITS | decimalLiteral | doubleLiteral | dateLiteral | timeLiteral | datetimeLiteral | stringLiteral | hexLiteral | octalLiteral );";
        }
    }
 

    public static final BitSet FOLLOW_orExpr_in_selector3525 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_selector3527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr3575 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_TOK_OR_in_orExpr3619 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_andExpr_in_orExpr3641 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_equalityExpr_in_andExpr3720 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_TOK_AND_in_andExpr3763 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_equalityExpr_in_andExpr3785 = new BitSet(new long[]{0x0000008000000002L});
    public static final BitSet FOLLOW_set_in_equalityOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr3898 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_equalityOp_in_equalityExpr3935 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr3977 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_TOK_LIKE_in_equalityExpr4035 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr4039 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_TOK_NOT_in_equalityExpr4074 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_TOK_LIKE_in_equalityExpr4078 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr4082 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_TOK_IS_in_equalityExpr4118 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_TOK_NULL_in_equalityExpr4122 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_TOK_IS_in_equalityExpr4159 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_TOK_NOT_in_equalityExpr4163 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_TOK_NULL_in_equalityExpr4167 = new BitSet(new long[]{0x0002110081000002L});
    public static final BitSet FOLLOW_set_in_relationalOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4290 = new BitSet(new long[]{0x0000120360000002L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr4320 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4324 = new BitSet(new long[]{0x0000120360000002L});
    public static final BitSet FOLLOW_TOK_BETWEEN_in_relationalExpr4357 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4362 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_TOK_AND_in_relationalExpr4366 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4370 = new BitSet(new long[]{0x0000120360000002L});
    public static final BitSet FOLLOW_TOK_NOT_in_relationalExpr4418 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_TOK_BETWEEN_in_relationalExpr4420 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4425 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_TOK_AND_in_relationalExpr4429 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr4433 = new BitSet(new long[]{0x0000120360000002L});
    public static final BitSet FOLLOW_set_in_additiveOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr4577 = new BitSet(new long[]{0x0000000006800002L});
    public static final BitSet FOLLOW_additiveOp_in_additiveExpr4621 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr4625 = new BitSet(new long[]{0x0000000006800002L});
    public static final BitSet FOLLOW_set_in_multiplicativeOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_multiplicativeExpr4749 = new BitSet(new long[]{0x0000040018000002L});
    public static final BitSet FOLLOW_multiplicativeOp_in_multiplicativeExpr4793 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_inExpr_in_multiplicativeExpr4797 = new BitSet(new long[]{0x0000040018000002L});
    public static final BitSet FOLLOW_unaryExpr_in_inExpr4885 = new BitSet(new long[]{0x0000102000000002L});
    public static final BitSet FOLLOW_TOK_IN_in_inExpr4944 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_unaryExpr_in_inExpr4986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_inExpr5022 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_field_in_inExpr5024 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_TOK_COMMA_in_inExpr5027 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_field_in_inExpr5029 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_inExpr5033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_NOT_in_inExpr5109 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_TOK_IN_in_inExpr5113 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_unaryExpr_in_inExpr5174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_inExpr5210 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_field_in_inExpr5212 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_TOK_COMMA_in_inExpr5215 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_field_in_inExpr5217 = new BitSet(new long[]{0x0000000000140000L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_inExpr5221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_PLUS_in_unaryOp5349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_MINUS_in_unaryOp5369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ABS_in_unaryOp5394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_NOT_in_unaryOp5414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryOp_in_unaryExpr5452 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_unaryExpr_in_unaryExpr5456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_unaryExpr5480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_term5507 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_orExpr_in_term5511 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_term5513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_term5532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelIdentifier_in_term5546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bindVar_in_term5560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DOLLAR_in_bindVar5585 = new BitSet(new long[]{0x0000001800000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_labelIdentifier_in_bindVar5587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_HASH_in_labelIdentifier5652 = new BitSet(new long[]{0x0003FFE000000000L});
    public static final BitSet FOLLOW_keyWords_in_labelIdentifier5654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_labelIdentifier5688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_AT_in_labelIdentifier5709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_Identifier_in_labelIdentifier5711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_field_in_valueList5772 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_TOK_COMMA_in_valueList5808 = new BitSet(new long[]{0x0800F81C06080000L,0x000000000000FE1CL});
    public static final BitSet FOLLOW_field_in_valueList5831 = new BitSet(new long[]{0x0000000000100002L});
    public static final BitSet FOLLOW_orExpr_in_field5908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectLiteral_in_literal5956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal5977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIGITS_in_literal5997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decimalLiteral_in_literal6018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doubleLiteral_in_literal6039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal6060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeLiteral_in_literal6080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_datetimeLiteral_in_literal6101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_literal6121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hexLiteral_in_literal6142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_octalLiteral_in_literal6164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_NULL_in_objectLiteral6204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral6244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DecimalLiteral_in_decimalLiteral6337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HexLiteral_in_hexLiteral6368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OctalLiteral_in_octalLiteral6398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_doubleLiteral6433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_stringLiteral6530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DateLiteral_in_dateLiteral6569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TimeLiteral_in_timeLiteral6608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DateTimeLiteral_in_datetimeLiteral6647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyWords0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpr_in_synpred21_filter4986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpr_in_synpred24_filter5174 = new BitSet(new long[]{0x0000000000000002L});

}