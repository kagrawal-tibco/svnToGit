// $ANTLR 3.4 /home/aditya/Desktop/SPMCLI.g 2012-12-06 14:57:56

package com.tibco.rta.model.command.ast;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class SPMCLIParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ADD", "AGGREGATOR", "ATTRIBUTE", "ATTRIBUTES_BLOCK", "BLOCK", "COMMAND", "COMMIT", "CONNECT", "CREATE", "CUBE", "DATATYPE", "DIMENSION", "EQUALS", "EXPORT", "FOR", "FROM", "HIERARCHY", "IDENTIFIER", "IMPORT", "IN", "JAVAIDDIGIT", "LETTER", "LINK", "LPAREN", "MEASUREMENT", "MODEL_BLOCK", "NEWLINE", "PASSWORD", "PREPOSITION", "PREPOSITIONS", "QUOTES", "REMOVE", "RPAREN", "SCHEMA", "SHOW", "TARGET_BLOCK", "TARGET_OBJECTS", "TO", "USERNAME", "WS"
    };

    public static final int EOF=-1;
    public static final int ADD=4;
    public static final int AGGREGATOR=5;
    public static final int ATTRIBUTE=6;
    public static final int ATTRIBUTES_BLOCK=7;
    public static final int BLOCK=8;
    public static final int COMMAND=9;
    public static final int COMMIT=10;
    public static final int CONNECT=11;
    public static final int CREATE=12;
    public static final int CUBE=13;
    public static final int DATATYPE=14;
    public static final int DIMENSION=15;
    public static final int EQUALS=16;
    public static final int EXPORT=17;
    public static final int FOR=18;
    public static final int FROM=19;
    public static final int HIERARCHY=20;
    public static final int IDENTIFIER=21;
    public static final int IMPORT=22;
    public static final int IN=23;
    public static final int JAVAIDDIGIT=24;
    public static final int LETTER=25;
    public static final int LINK=26;
    public static final int LPAREN=27;
    public static final int MEASUREMENT=28;
    public static final int MODEL_BLOCK=29;
    public static final int NEWLINE=30;
    public static final int PASSWORD=31;
    public static final int PREPOSITION=32;
    public static final int PREPOSITIONS=33;
    public static final int QUOTES=34;
    public static final int REMOVE=35;
    public static final int RPAREN=36;
    public static final int SCHEMA=37;
    public static final int SHOW=38;
    public static final int TARGET_BLOCK=39;
    public static final int TARGET_OBJECTS=40;
    public static final int TO=41;
    public static final int USERNAME=42;
    public static final int WS=43;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public SPMCLIParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public SPMCLIParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return SPMCLIParser.tokenNames; }
    public String getGrammarFileName() { return "/home/aditya/Desktop/SPMCLI.g"; }


    public static class attribute_values_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "attribute_values"
    // /home/aditya/Desktop/SPMCLI.g:57:1: attribute_values : IDENTIFIER ;
    public final SPMCLIParser.attribute_values_return attribute_values() throws RecognitionException {
        SPMCLIParser.attribute_values_return retval = new SPMCLIParser.attribute_values_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token IDENTIFIER1=null;

        CommonTree IDENTIFIER1_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:58:5: ( IDENTIFIER )
            // /home/aditya/Desktop/SPMCLI.g:58:9: IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();


            IDENTIFIER1=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_attribute_values375); 
            IDENTIFIER1_tree = 
            (CommonTree)adaptor.create(IDENTIFIER1)
            ;
            adaptor.addChild(root_0, IDENTIFIER1_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "attribute_values"


    public static class model_attribute_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "model_attribute"
    // /home/aditya/Desktop/SPMCLI.g:102:1: model_attribute : ( DATATYPE | AGGREGATOR );
    public final SPMCLIParser.model_attribute_return model_attribute() throws RecognitionException {
        SPMCLIParser.model_attribute_return retval = new SPMCLIParser.model_attribute_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set2=null;

        CommonTree set2_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:103:5: ( DATATYPE | AGGREGATOR )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set2=(Token)input.LT(1);

            if ( input.LA(1)==AGGREGATOR||input.LA(1)==DATATYPE ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set2)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "model_attribute"


    public static class verb_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "verb"
    // /home/aditya/Desktop/SPMCLI.g:106:1: verb : ( CREATE | ADD | REMOVE | CONNECT | SHOW | LINK | IMPORT | EXPORT | COMMIT );
    public final SPMCLIParser.verb_return verb() throws RecognitionException {
        SPMCLIParser.verb_return retval = new SPMCLIParser.verb_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set3=null;

        CommonTree set3_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:107:5: ( CREATE | ADD | REMOVE | CONNECT | SHOW | LINK | IMPORT | EXPORT | COMMIT )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set3=(Token)input.LT(1);

            if ( input.LA(1)==ADD||(input.LA(1) >= COMMIT && input.LA(1) <= CREATE)||input.LA(1)==EXPORT||input.LA(1)==IMPORT||input.LA(1)==LINK||input.LA(1)==REMOVE||input.LA(1)==SHOW ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set3)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "verb"


    public static class model_object_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "model_object"
    // /home/aditya/Desktop/SPMCLI.g:118:1: model_object : ( SCHEMA | CUBE | MEASUREMENT | ATTRIBUTE | DIMENSION | HIERARCHY | AGGREGATOR );
    public final SPMCLIParser.model_object_return model_object() throws RecognitionException {
        SPMCLIParser.model_object_return retval = new SPMCLIParser.model_object_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set4=null;

        CommonTree set4_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:119:5: ( SCHEMA | CUBE | MEASUREMENT | ATTRIBUTE | DIMENSION | HIERARCHY | AGGREGATOR )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set4=(Token)input.LT(1);

            if ( (input.LA(1) >= AGGREGATOR && input.LA(1) <= ATTRIBUTE)||input.LA(1)==CUBE||input.LA(1)==DIMENSION||input.LA(1)==HIERARCHY||input.LA(1)==MEASUREMENT||input.LA(1)==SCHEMA ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set4)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "model_object"


    public static class creds_object_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "creds_object"
    // /home/aditya/Desktop/SPMCLI.g:128:1: creds_object : ( USERNAME | PASSWORD );
    public final SPMCLIParser.creds_object_return creds_object() throws RecognitionException {
        SPMCLIParser.creds_object_return retval = new SPMCLIParser.creds_object_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set5=null;

        CommonTree set5_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:129:5: ( USERNAME | PASSWORD )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set5=(Token)input.LT(1);

            if ( input.LA(1)==PASSWORD||input.LA(1)==USERNAME ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set5)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "creds_object"


    public static class preposition_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "preposition"
    // /home/aditya/Desktop/SPMCLI.g:133:1: preposition : ( FROM | IN | TO | FOR );
    public final SPMCLIParser.preposition_return preposition() throws RecognitionException {
        SPMCLIParser.preposition_return retval = new SPMCLIParser.preposition_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set6=null;

        CommonTree set6_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:134:5: ( FROM | IN | TO | FOR )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set6=(Token)input.LT(1);

            if ( (input.LA(1) >= FOR && input.LA(1) <= FROM)||input.LA(1)==IN||input.LA(1)==TO ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set6)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "preposition"


    public static class target_object_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "target_object"
    // /home/aditya/Desktop/SPMCLI.g:140:1: target_object : model_object ;
    public final SPMCLIParser.target_object_return target_object() throws RecognitionException {
        SPMCLIParser.target_object_return retval = new SPMCLIParser.target_object_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SPMCLIParser.model_object_return model_object7 =null;



        try {
            // /home/aditya/Desktop/SPMCLI.g:141:5: ( model_object )
            // /home/aditya/Desktop/SPMCLI.g:141:10: model_object
            {
            root_0 = (CommonTree)adaptor.nil();


            pushFollow(FOLLOW_model_object_in_target_object1143);
            model_object7=model_object();

            state._fsp--;

            adaptor.addChild(root_0, model_object7.getTree());

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "target_object"


    public static class verb_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "verb_expr"
    // /home/aditya/Desktop/SPMCLI.g:153:1: verb_expr : ( WS )? verb ( WS )? ;
    public final SPMCLIParser.verb_expr_return verb_expr() throws RecognitionException {
        SPMCLIParser.verb_expr_return retval = new SPMCLIParser.verb_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS8=null;
        Token WS10=null;
        SPMCLIParser.verb_return verb9 =null;


        CommonTree WS8_tree=null;
        CommonTree WS10_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:154:5: ( ( WS )? verb ( WS )? )
            // /home/aditya/Desktop/SPMCLI.g:154:10: ( WS )? verb ( WS )?
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/aditya/Desktop/SPMCLI.g:154:10: ( WS )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==WS) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:154:10: WS
                    {
                    WS8=(Token)match(input,WS,FOLLOW_WS_in_verb_expr1222); 
                    WS8_tree = 
                    (CommonTree)adaptor.create(WS8)
                    ;
                    adaptor.addChild(root_0, WS8_tree);


                    }
                    break;

            }


            pushFollow(FOLLOW_verb_in_verb_expr1225);
            verb9=verb();

            state._fsp--;

            adaptor.addChild(root_0, verb9.getTree());

            // /home/aditya/Desktop/SPMCLI.g:154:19: ( WS )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==WS) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:154:19: WS
                    {
                    WS10=(Token)match(input,WS,FOLLOW_WS_in_verb_expr1227); 
                    WS10_tree = 
                    (CommonTree)adaptor.create(WS10)
                    ;
                    adaptor.addChild(root_0, WS10_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "verb_expr"


    public static class target_object_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "target_object_expr"
    // /home/aditya/Desktop/SPMCLI.g:157:1: target_object_expr : ( WS )? target_object ( WS )? ;
    public final SPMCLIParser.target_object_expr_return target_object_expr() throws RecognitionException {
        SPMCLIParser.target_object_expr_return retval = new SPMCLIParser.target_object_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS11=null;
        Token WS13=null;
        SPMCLIParser.target_object_return target_object12 =null;


        CommonTree WS11_tree=null;
        CommonTree WS13_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:158:5: ( ( WS )? target_object ( WS )? )
            // /home/aditya/Desktop/SPMCLI.g:158:10: ( WS )? target_object ( WS )?
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/aditya/Desktop/SPMCLI.g:158:10: ( WS )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==WS) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:158:10: WS
                    {
                    WS11=(Token)match(input,WS,FOLLOW_WS_in_target_object_expr1255); 
                    WS11_tree = 
                    (CommonTree)adaptor.create(WS11)
                    ;
                    adaptor.addChild(root_0, WS11_tree);


                    }
                    break;

            }


            pushFollow(FOLLOW_target_object_in_target_object_expr1258);
            target_object12=target_object();

            state._fsp--;

            adaptor.addChild(root_0, target_object12.getTree());

            // /home/aditya/Desktop/SPMCLI.g:158:28: ( WS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==WS) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:158:28: WS
                    {
                    WS13=(Token)match(input,WS,FOLLOW_WS_in_target_object_expr1260); 
                    WS13_tree = 
                    (CommonTree)adaptor.create(WS13)
                    ;
                    adaptor.addChild(root_0, WS13_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "target_object_expr"


    public static class identifier_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "identifier_expr"
    // /home/aditya/Desktop/SPMCLI.g:161:1: identifier_expr : ( WS )? IDENTIFIER ( WS )? ;
    public final SPMCLIParser.identifier_expr_return identifier_expr() throws RecognitionException {
        SPMCLIParser.identifier_expr_return retval = new SPMCLIParser.identifier_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS14=null;
        Token IDENTIFIER15=null;
        Token WS16=null;

        CommonTree WS14_tree=null;
        CommonTree IDENTIFIER15_tree=null;
        CommonTree WS16_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:162:5: ( ( WS )? IDENTIFIER ( WS )? )
            // /home/aditya/Desktop/SPMCLI.g:162:10: ( WS )? IDENTIFIER ( WS )?
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/aditya/Desktop/SPMCLI.g:162:10: ( WS )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==WS) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:162:10: WS
                    {
                    WS14=(Token)match(input,WS,FOLLOW_WS_in_identifier_expr1287); 
                    WS14_tree = 
                    (CommonTree)adaptor.create(WS14)
                    ;
                    adaptor.addChild(root_0, WS14_tree);


                    }
                    break;

            }


            IDENTIFIER15=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_identifier_expr1290); 
            IDENTIFIER15_tree = 
            (CommonTree)adaptor.create(IDENTIFIER15)
            ;
            adaptor.addChild(root_0, IDENTIFIER15_tree);


            // /home/aditya/Desktop/SPMCLI.g:162:25: ( WS )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==WS) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:162:25: WS
                    {
                    WS16=(Token)match(input,WS,FOLLOW_WS_in_identifier_expr1292); 
                    WS16_tree = 
                    (CommonTree)adaptor.create(WS16)
                    ;
                    adaptor.addChild(root_0, WS16_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "identifier_expr"


    public static class datatype_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "datatype_expr"
    // /home/aditya/Desktop/SPMCLI.g:165:1: datatype_expr : ( WS )? IDENTIFIER ( WS )? ;
    public final SPMCLIParser.datatype_expr_return datatype_expr() throws RecognitionException {
        SPMCLIParser.datatype_expr_return retval = new SPMCLIParser.datatype_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS17=null;
        Token IDENTIFIER18=null;
        Token WS19=null;

        CommonTree WS17_tree=null;
        CommonTree IDENTIFIER18_tree=null;
        CommonTree WS19_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:166:5: ( ( WS )? IDENTIFIER ( WS )? )
            // /home/aditya/Desktop/SPMCLI.g:166:10: ( WS )? IDENTIFIER ( WS )?
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/aditya/Desktop/SPMCLI.g:166:10: ( WS )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==WS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:166:10: WS
                    {
                    WS17=(Token)match(input,WS,FOLLOW_WS_in_datatype_expr1319); 
                    WS17_tree = 
                    (CommonTree)adaptor.create(WS17)
                    ;
                    adaptor.addChild(root_0, WS17_tree);


                    }
                    break;

            }


            IDENTIFIER18=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_datatype_expr1322); 
            IDENTIFIER18_tree = 
            (CommonTree)adaptor.create(IDENTIFIER18)
            ;
            adaptor.addChild(root_0, IDENTIFIER18_tree);


            // /home/aditya/Desktop/SPMCLI.g:166:25: ( WS )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==WS) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:166:25: WS
                    {
                    WS19=(Token)match(input,WS,FOLLOW_WS_in_datatype_expr1324); 
                    WS19_tree = 
                    (CommonTree)adaptor.create(WS19)
                    ;
                    adaptor.addChild(root_0, WS19_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "datatype_expr"


    public static class attribute_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "attribute_expr"
    // /home/aditya/Desktop/SPMCLI.g:172:1: attribute_expr : ( WS )* model_attribute ( WS )* EQUALS ( WS )* QUOTES ( WS )* attribute_values ( WS )* QUOTES ( WS )* -> ^( model_attribute ^( IDENTIFIER attribute_values ) ) ;
    public final SPMCLIParser.attribute_expr_return attribute_expr() throws RecognitionException {
        SPMCLIParser.attribute_expr_return retval = new SPMCLIParser.attribute_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS20=null;
        Token WS22=null;
        Token EQUALS23=null;
        Token WS24=null;
        Token QUOTES25=null;
        Token WS26=null;
        Token WS28=null;
        Token QUOTES29=null;
        Token WS30=null;
        SPMCLIParser.model_attribute_return model_attribute21 =null;

        SPMCLIParser.attribute_values_return attribute_values27 =null;


        CommonTree WS20_tree=null;
        CommonTree WS22_tree=null;
        CommonTree EQUALS23_tree=null;
        CommonTree WS24_tree=null;
        CommonTree QUOTES25_tree=null;
        CommonTree WS26_tree=null;
        CommonTree WS28_tree=null;
        CommonTree QUOTES29_tree=null;
        CommonTree WS30_tree=null;
        RewriteRuleTokenStream stream_WS=new RewriteRuleTokenStream(adaptor,"token WS");
        RewriteRuleTokenStream stream_EQUALS=new RewriteRuleTokenStream(adaptor,"token EQUALS");
        RewriteRuleTokenStream stream_QUOTES=new RewriteRuleTokenStream(adaptor,"token QUOTES");
        RewriteRuleSubtreeStream stream_model_attribute=new RewriteRuleSubtreeStream(adaptor,"rule model_attribute");
        RewriteRuleSubtreeStream stream_attribute_values=new RewriteRuleSubtreeStream(adaptor,"rule attribute_values");
        try {
            // /home/aditya/Desktop/SPMCLI.g:173:5: ( ( WS )* model_attribute ( WS )* EQUALS ( WS )* QUOTES ( WS )* attribute_values ( WS )* QUOTES ( WS )* -> ^( model_attribute ^( IDENTIFIER attribute_values ) ) )
            // /home/aditya/Desktop/SPMCLI.g:173:10: ( WS )* model_attribute ( WS )* EQUALS ( WS )* QUOTES ( WS )* attribute_values ( WS )* QUOTES ( WS )*
            {
            // /home/aditya/Desktop/SPMCLI.g:173:10: ( WS )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==WS) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:10: WS
            	    {
            	    WS20=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1353);  
            	    stream_WS.add(WS20);


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);


            pushFollow(FOLLOW_model_attribute_in_attribute_expr1356);
            model_attribute21=model_attribute();

            state._fsp--;

            stream_model_attribute.add(model_attribute21.getTree());

            // /home/aditya/Desktop/SPMCLI.g:173:30: ( WS )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==WS) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:30: WS
            	    {
            	    WS22=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1358);  
            	    stream_WS.add(WS22);


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            EQUALS23=(Token)match(input,EQUALS,FOLLOW_EQUALS_in_attribute_expr1361);  
            stream_EQUALS.add(EQUALS23);


            // /home/aditya/Desktop/SPMCLI.g:173:41: ( WS )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==WS) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:41: WS
            	    {
            	    WS24=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1363);  
            	    stream_WS.add(WS24);


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            QUOTES25=(Token)match(input,QUOTES,FOLLOW_QUOTES_in_attribute_expr1366);  
            stream_QUOTES.add(QUOTES25);


            // /home/aditya/Desktop/SPMCLI.g:173:52: ( WS )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==WS) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:52: WS
            	    {
            	    WS26=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1368);  
            	    stream_WS.add(WS26);


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            pushFollow(FOLLOW_attribute_values_in_attribute_expr1371);
            attribute_values27=attribute_values();

            state._fsp--;

            stream_attribute_values.add(attribute_values27.getTree());

            // /home/aditya/Desktop/SPMCLI.g:173:73: ( WS )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==WS) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:73: WS
            	    {
            	    WS28=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1373);  
            	    stream_WS.add(WS28);


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            QUOTES29=(Token)match(input,QUOTES,FOLLOW_QUOTES_in_attribute_expr1376);  
            stream_QUOTES.add(QUOTES29);


            // /home/aditya/Desktop/SPMCLI.g:173:84: ( WS )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==WS) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:173:84: WS
            	    {
            	    WS30=(Token)match(input,WS,FOLLOW_WS_in_attribute_expr1378);  
            	    stream_WS.add(WS30);


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            // AST REWRITE
            // elements: model_attribute, attribute_values
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 174:5: -> ^( model_attribute ^( IDENTIFIER attribute_values ) )
            {
                // /home/aditya/Desktop/SPMCLI.g:174:8: ^( model_attribute ^( IDENTIFIER attribute_values ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(stream_model_attribute.nextNode(), root_1);

                // /home/aditya/Desktop/SPMCLI.g:174:26: ^( IDENTIFIER attribute_values )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(IDENTIFIER, "IDENTIFIER")
                , root_2);

                adaptor.addChild(root_2, stream_attribute_values.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "attribute_expr"


    public static class all_attributes_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "all_attributes_expr"
    // /home/aditya/Desktop/SPMCLI.g:180:1: all_attributes_expr : ( LPAREN )? ( attribute_expr )* ( RPAREN )? -> ^( ATTRIBUTES_BLOCK ( attribute_expr )* ) ;
    public final SPMCLIParser.all_attributes_expr_return all_attributes_expr() throws RecognitionException {
        SPMCLIParser.all_attributes_expr_return retval = new SPMCLIParser.all_attributes_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token LPAREN31=null;
        Token RPAREN33=null;
        SPMCLIParser.attribute_expr_return attribute_expr32 =null;


        CommonTree LPAREN31_tree=null;
        CommonTree RPAREN33_tree=null;
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleSubtreeStream stream_attribute_expr=new RewriteRuleSubtreeStream(adaptor,"rule attribute_expr");
        try {
            // /home/aditya/Desktop/SPMCLI.g:181:5: ( ( LPAREN )? ( attribute_expr )* ( RPAREN )? -> ^( ATTRIBUTES_BLOCK ( attribute_expr )* ) )
            // /home/aditya/Desktop/SPMCLI.g:181:8: ( LPAREN )? ( attribute_expr )* ( RPAREN )?
            {
            // /home/aditya/Desktop/SPMCLI.g:181:8: ( LPAREN )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==LPAREN) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:181:9: LPAREN
                    {
                    LPAREN31=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_all_attributes_expr1421);  
                    stream_LPAREN.add(LPAREN31);


                    }
                    break;

            }


            // /home/aditya/Desktop/SPMCLI.g:181:18: ( attribute_expr )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==WS) ) {
                    int LA16_2 = input.LA(2);

                    if ( (LA16_2==AGGREGATOR||LA16_2==DATATYPE||LA16_2==WS) ) {
                        alt16=1;
                    }


                }
                else if ( (LA16_0==AGGREGATOR||LA16_0==DATATYPE) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:181:19: attribute_expr
            	    {
            	    pushFollow(FOLLOW_attribute_expr_in_all_attributes_expr1426);
            	    attribute_expr32=attribute_expr();

            	    state._fsp--;

            	    stream_attribute_expr.add(attribute_expr32.getTree());

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            // /home/aditya/Desktop/SPMCLI.g:181:37: ( RPAREN )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==RPAREN) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:181:38: RPAREN
                    {
                    RPAREN33=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_all_attributes_expr1432);  
                    stream_RPAREN.add(RPAREN33);


                    }
                    break;

            }


            // AST REWRITE
            // elements: attribute_expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 182:5: -> ^( ATTRIBUTES_BLOCK ( attribute_expr )* )
            {
                // /home/aditya/Desktop/SPMCLI.g:182:8: ^( ATTRIBUTES_BLOCK ( attribute_expr )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(ATTRIBUTES_BLOCK, "ATTRIBUTES_BLOCK")
                , root_1);

                // /home/aditya/Desktop/SPMCLI.g:182:27: ( attribute_expr )*
                while ( stream_attribute_expr.hasNext() ) {
                    adaptor.addChild(root_1, stream_attribute_expr.nextTree());

                }
                stream_attribute_expr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "all_attributes_expr"


    public static class prep_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "prep_expr"
    // /home/aditya/Desktop/SPMCLI.g:185:1: prep_expr : ( WS )? preposition ( WS )? ;
    public final SPMCLIParser.prep_expr_return prep_expr() throws RecognitionException {
        SPMCLIParser.prep_expr_return retval = new SPMCLIParser.prep_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS34=null;
        Token WS36=null;
        SPMCLIParser.preposition_return preposition35 =null;


        CommonTree WS34_tree=null;
        CommonTree WS36_tree=null;

        try {
            // /home/aditya/Desktop/SPMCLI.g:186:5: ( ( WS )? preposition ( WS )? )
            // /home/aditya/Desktop/SPMCLI.g:186:10: ( WS )? preposition ( WS )?
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/aditya/Desktop/SPMCLI.g:186:10: ( WS )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==WS) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:186:10: WS
                    {
                    WS34=(Token)match(input,WS,FOLLOW_WS_in_prep_expr1484); 
                    WS34_tree = 
                    (CommonTree)adaptor.create(WS34)
                    ;
                    adaptor.addChild(root_0, WS34_tree);


                    }
                    break;

            }


            pushFollow(FOLLOW_preposition_in_prep_expr1487);
            preposition35=preposition();

            state._fsp--;

            adaptor.addChild(root_0, preposition35.getTree());

            // /home/aditya/Desktop/SPMCLI.g:186:26: ( WS )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==WS) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:186:26: WS
                    {
                    WS36=(Token)match(input,WS,FOLLOW_WS_in_prep_expr1489); 
                    WS36_tree = 
                    (CommonTree)adaptor.create(WS36)
                    ;
                    adaptor.addChild(root_0, WS36_tree);


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "prep_expr"


    public static class model_object_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "model_object_expr"
    // /home/aditya/Desktop/SPMCLI.g:189:1: model_object_expr : ( WS )? model_object ( WS )? -> model_object ;
    public final SPMCLIParser.model_object_expr_return model_object_expr() throws RecognitionException {
        SPMCLIParser.model_object_expr_return retval = new SPMCLIParser.model_object_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token WS37=null;
        Token WS39=null;
        SPMCLIParser.model_object_return model_object38 =null;


        CommonTree WS37_tree=null;
        CommonTree WS39_tree=null;
        RewriteRuleTokenStream stream_WS=new RewriteRuleTokenStream(adaptor,"token WS");
        RewriteRuleSubtreeStream stream_model_object=new RewriteRuleSubtreeStream(adaptor,"rule model_object");
        try {
            // /home/aditya/Desktop/SPMCLI.g:190:5: ( ( WS )? model_object ( WS )? -> model_object )
            // /home/aditya/Desktop/SPMCLI.g:190:10: ( WS )? model_object ( WS )?
            {
            // /home/aditya/Desktop/SPMCLI.g:190:10: ( WS )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WS) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:190:10: WS
                    {
                    WS37=(Token)match(input,WS,FOLLOW_WS_in_model_object_expr1514);  
                    stream_WS.add(WS37);


                    }
                    break;

            }


            pushFollow(FOLLOW_model_object_in_model_object_expr1517);
            model_object38=model_object();

            state._fsp--;

            stream_model_object.add(model_object38.getTree());

            // /home/aditya/Desktop/SPMCLI.g:190:27: ( WS )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==WS) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:190:27: WS
                    {
                    WS39=(Token)match(input,WS,FOLLOW_WS_in_model_object_expr1519);  
                    stream_WS.add(WS39);


                    }
                    break;

            }


            // AST REWRITE
            // elements: model_object
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 191:4: -> model_object
            {
                adaptor.addChild(root_0, stream_model_object.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "model_object_expr"


    public static class attrs_with_identifiers_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "attrs_with_identifiers_expr"
    // /home/aditya/Desktop/SPMCLI.g:197:1: attrs_with_identifiers_expr : ( identifier_expr all_attributes_expr )* -> ( ^( BLOCK ^( IDENTIFIER identifier_expr ) all_attributes_expr ) )* ;
    public final SPMCLIParser.attrs_with_identifiers_expr_return attrs_with_identifiers_expr() throws RecognitionException {
        SPMCLIParser.attrs_with_identifiers_expr_return retval = new SPMCLIParser.attrs_with_identifiers_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SPMCLIParser.identifier_expr_return identifier_expr40 =null;

        SPMCLIParser.all_attributes_expr_return all_attributes_expr41 =null;


        RewriteRuleSubtreeStream stream_all_attributes_expr=new RewriteRuleSubtreeStream(adaptor,"rule all_attributes_expr");
        RewriteRuleSubtreeStream stream_identifier_expr=new RewriteRuleSubtreeStream(adaptor,"rule identifier_expr");
        try {
            // /home/aditya/Desktop/SPMCLI.g:198:5: ( ( identifier_expr all_attributes_expr )* -> ( ^( BLOCK ^( IDENTIFIER identifier_expr ) all_attributes_expr ) )* )
            // /home/aditya/Desktop/SPMCLI.g:198:10: ( identifier_expr all_attributes_expr )*
            {
            // /home/aditya/Desktop/SPMCLI.g:198:10: ( identifier_expr all_attributes_expr )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==WS) ) {
                    int LA22_1 = input.LA(2);

                    if ( (LA22_1==IDENTIFIER) ) {
                        alt22=1;
                    }


                }
                else if ( (LA22_0==IDENTIFIER) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:198:11: identifier_expr all_attributes_expr
            	    {
            	    pushFollow(FOLLOW_identifier_expr_in_attrs_with_identifiers_expr1558);
            	    identifier_expr40=identifier_expr();

            	    state._fsp--;

            	    stream_identifier_expr.add(identifier_expr40.getTree());

            	    pushFollow(FOLLOW_all_attributes_expr_in_attrs_with_identifiers_expr1560);
            	    all_attributes_expr41=all_attributes_expr();

            	    state._fsp--;

            	    stream_all_attributes_expr.add(all_attributes_expr41.getTree());

            	    }
            	    break;

            	default :
            	    break loop22;
                }
            } while (true);


            // AST REWRITE
            // elements: all_attributes_expr, identifier_expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 199:5: -> ( ^( BLOCK ^( IDENTIFIER identifier_expr ) all_attributes_expr ) )*
            {
                // /home/aditya/Desktop/SPMCLI.g:199:8: ( ^( BLOCK ^( IDENTIFIER identifier_expr ) all_attributes_expr ) )*
                while ( stream_all_attributes_expr.hasNext()||stream_identifier_expr.hasNext() ) {
                    // /home/aditya/Desktop/SPMCLI.g:199:8: ^( BLOCK ^( IDENTIFIER identifier_expr ) all_attributes_expr )
                    {
                    CommonTree root_1 = (CommonTree)adaptor.nil();
                    root_1 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(BLOCK, "BLOCK")
                    , root_1);

                    // /home/aditya/Desktop/SPMCLI.g:199:16: ^( IDENTIFIER identifier_expr )
                    {
                    CommonTree root_2 = (CommonTree)adaptor.nil();
                    root_2 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(IDENTIFIER, "IDENTIFIER")
                    , root_2);

                    adaptor.addChild(root_2, stream_identifier_expr.nextTree());

                    adaptor.addChild(root_1, root_2);
                    }

                    adaptor.addChild(root_1, stream_all_attributes_expr.nextTree());

                    adaptor.addChild(root_0, root_1);
                    }

                }
                stream_all_attributes_expr.reset();
                stream_identifier_expr.reset();

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "attrs_with_identifiers_expr"


    public static class preps_with_model_objects_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "preps_with_model_objects_expr"
    // /home/aditya/Desktop/SPMCLI.g:205:1: preps_with_model_objects_expr : ( prep_expr model_object_expr identifier_expr )* -> ^( PREPOSITIONS ( ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) ) )* ) ;
    public final SPMCLIParser.preps_with_model_objects_expr_return preps_with_model_objects_expr() throws RecognitionException {
        SPMCLIParser.preps_with_model_objects_expr_return retval = new SPMCLIParser.preps_with_model_objects_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SPMCLIParser.prep_expr_return prep_expr42 =null;

        SPMCLIParser.model_object_expr_return model_object_expr43 =null;

        SPMCLIParser.identifier_expr_return identifier_expr44 =null;


        RewriteRuleSubtreeStream stream_model_object_expr=new RewriteRuleSubtreeStream(adaptor,"rule model_object_expr");
        RewriteRuleSubtreeStream stream_prep_expr=new RewriteRuleSubtreeStream(adaptor,"rule prep_expr");
        RewriteRuleSubtreeStream stream_identifier_expr=new RewriteRuleSubtreeStream(adaptor,"rule identifier_expr");
        try {
            // /home/aditya/Desktop/SPMCLI.g:206:5: ( ( prep_expr model_object_expr identifier_expr )* -> ^( PREPOSITIONS ( ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) ) )* ) )
            // /home/aditya/Desktop/SPMCLI.g:206:10: ( prep_expr model_object_expr identifier_expr )*
            {
            // /home/aditya/Desktop/SPMCLI.g:206:10: ( prep_expr model_object_expr identifier_expr )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0 >= FOR && LA23_0 <= FROM)||LA23_0==IN||LA23_0==TO||LA23_0==WS) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:206:11: prep_expr model_object_expr identifier_expr
            	    {
            	    pushFollow(FOLLOW_prep_expr_in_preps_with_model_objects_expr1612);
            	    prep_expr42=prep_expr();

            	    state._fsp--;

            	    stream_prep_expr.add(prep_expr42.getTree());

            	    pushFollow(FOLLOW_model_object_expr_in_preps_with_model_objects_expr1614);
            	    model_object_expr43=model_object_expr();

            	    state._fsp--;

            	    stream_model_object_expr.add(model_object_expr43.getTree());

            	    pushFollow(FOLLOW_identifier_expr_in_preps_with_model_objects_expr1616);
            	    identifier_expr44=identifier_expr();

            	    state._fsp--;

            	    stream_identifier_expr.add(identifier_expr44.getTree());

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            // AST REWRITE
            // elements: prep_expr, model_object_expr, identifier_expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 207:5: -> ^( PREPOSITIONS ( ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) ) )* )
            {
                // /home/aditya/Desktop/SPMCLI.g:207:8: ^( PREPOSITIONS ( ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) ) )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(PREPOSITIONS, "PREPOSITIONS")
                , root_1);

                // /home/aditya/Desktop/SPMCLI.g:207:23: ( ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) ) )*
                while ( stream_prep_expr.hasNext()||stream_model_object_expr.hasNext()||stream_identifier_expr.hasNext() ) {
                    // /home/aditya/Desktop/SPMCLI.g:207:23: ^( PREPOSITION prep_expr ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) ) )
                    {
                    CommonTree root_2 = (CommonTree)adaptor.nil();
                    root_2 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(PREPOSITION, "PREPOSITION")
                    , root_2);

                    adaptor.addChild(root_2, stream_prep_expr.nextTree());

                    // /home/aditya/Desktop/SPMCLI.g:207:47: ^( MODEL_BLOCK ^( model_object_expr ^( IDENTIFIER identifier_expr ) ) )
                    {
                    CommonTree root_3 = (CommonTree)adaptor.nil();
                    root_3 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(MODEL_BLOCK, "MODEL_BLOCK")
                    , root_3);

                    // /home/aditya/Desktop/SPMCLI.g:207:61: ^( model_object_expr ^( IDENTIFIER identifier_expr ) )
                    {
                    CommonTree root_4 = (CommonTree)adaptor.nil();
                    root_4 = (CommonTree)adaptor.becomeRoot(stream_model_object_expr.nextNode(), root_4);

                    // /home/aditya/Desktop/SPMCLI.g:207:81: ^( IDENTIFIER identifier_expr )
                    {
                    CommonTree root_5 = (CommonTree)adaptor.nil();
                    root_5 = (CommonTree)adaptor.becomeRoot(
                    (CommonTree)adaptor.create(IDENTIFIER, "IDENTIFIER")
                    , root_5);

                    adaptor.addChild(root_5, stream_identifier_expr.nextTree());

                    adaptor.addChild(root_4, root_5);
                    }

                    adaptor.addChild(root_3, root_4);
                    }

                    adaptor.addChild(root_2, root_3);
                    }

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_prep_expr.reset();
                stream_model_object_expr.reset();
                stream_identifier_expr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "preps_with_model_objects_expr"


    public static class command_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "command_expr"
    // /home/aditya/Desktop/SPMCLI.g:213:1: command_expr : verb_expr target_object_expr attrs_with_identifiers_expr preps_with_model_objects_expr -> ^( COMMAND ^( verb_expr ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) ) preps_with_model_objects_expr ) ) ;
    public final SPMCLIParser.command_expr_return command_expr() throws RecognitionException {
        SPMCLIParser.command_expr_return retval = new SPMCLIParser.command_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        SPMCLIParser.verb_expr_return verb_expr45 =null;

        SPMCLIParser.target_object_expr_return target_object_expr46 =null;

        SPMCLIParser.attrs_with_identifiers_expr_return attrs_with_identifiers_expr47 =null;

        SPMCLIParser.preps_with_model_objects_expr_return preps_with_model_objects_expr48 =null;


        RewriteRuleSubtreeStream stream_target_object_expr=new RewriteRuleSubtreeStream(adaptor,"rule target_object_expr");
        RewriteRuleSubtreeStream stream_verb_expr=new RewriteRuleSubtreeStream(adaptor,"rule verb_expr");
        RewriteRuleSubtreeStream stream_preps_with_model_objects_expr=new RewriteRuleSubtreeStream(adaptor,"rule preps_with_model_objects_expr");
        RewriteRuleSubtreeStream stream_attrs_with_identifiers_expr=new RewriteRuleSubtreeStream(adaptor,"rule attrs_with_identifiers_expr");
        try {
            // /home/aditya/Desktop/SPMCLI.g:214:5: ( verb_expr target_object_expr attrs_with_identifiers_expr preps_with_model_objects_expr -> ^( COMMAND ^( verb_expr ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) ) preps_with_model_objects_expr ) ) )
            // /home/aditya/Desktop/SPMCLI.g:214:10: verb_expr target_object_expr attrs_with_identifiers_expr preps_with_model_objects_expr
            {
            pushFollow(FOLLOW_verb_expr_in_command_expr1675);
            verb_expr45=verb_expr();

            state._fsp--;

            stream_verb_expr.add(verb_expr45.getTree());

            pushFollow(FOLLOW_target_object_expr_in_command_expr1677);
            target_object_expr46=target_object_expr();

            state._fsp--;

            stream_target_object_expr.add(target_object_expr46.getTree());

            pushFollow(FOLLOW_attrs_with_identifiers_expr_in_command_expr1679);
            attrs_with_identifiers_expr47=attrs_with_identifiers_expr();

            state._fsp--;

            stream_attrs_with_identifiers_expr.add(attrs_with_identifiers_expr47.getTree());

            pushFollow(FOLLOW_preps_with_model_objects_expr_in_command_expr1681);
            preps_with_model_objects_expr48=preps_with_model_objects_expr();

            state._fsp--;

            stream_preps_with_model_objects_expr.add(preps_with_model_objects_expr48.getTree());

            // AST REWRITE
            // elements: preps_with_model_objects_expr, target_object_expr, verb_expr, attrs_with_identifiers_expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 215:5: -> ^( COMMAND ^( verb_expr ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) ) preps_with_model_objects_expr ) )
            {
                // /home/aditya/Desktop/SPMCLI.g:215:8: ^( COMMAND ^( verb_expr ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) ) preps_with_model_objects_expr ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(COMMAND, "COMMAND")
                , root_1);

                // /home/aditya/Desktop/SPMCLI.g:215:18: ^( verb_expr ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) ) preps_with_model_objects_expr )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(stream_verb_expr.nextNode(), root_2);

                // /home/aditya/Desktop/SPMCLI.g:215:30: ^( TARGET_BLOCK ^( target_object_expr attrs_with_identifiers_expr ) )
                {
                CommonTree root_3 = (CommonTree)adaptor.nil();
                root_3 = (CommonTree)adaptor.becomeRoot(
                (CommonTree)adaptor.create(TARGET_BLOCK, "TARGET_BLOCK")
                , root_3);

                // /home/aditya/Desktop/SPMCLI.g:215:45: ^( target_object_expr attrs_with_identifiers_expr )
                {
                CommonTree root_4 = (CommonTree)adaptor.nil();
                root_4 = (CommonTree)adaptor.becomeRoot(stream_target_object_expr.nextNode(), root_4);

                adaptor.addChild(root_4, stream_attrs_with_identifiers_expr.nextTree());

                adaptor.addChild(root_3, root_4);
                }

                adaptor.addChild(root_2, root_3);
                }

                adaptor.addChild(root_2, stream_preps_with_model_objects_expr.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "command_expr"

    // Delegated rules


 

    public static final BitSet FOLLOW_IDENTIFIER_in_attribute_values375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_model_object_in_target_object1143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_verb_expr1222 = new BitSet(new long[]{0x0000004804421C10L});
    public static final BitSet FOLLOW_verb_in_verb_expr1225 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_verb_expr1227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_target_object_expr1255 = new BitSet(new long[]{0x000000201010A060L});
    public static final BitSet FOLLOW_target_object_in_target_object_expr1258 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_target_object_expr1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_identifier_expr1287 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_identifier_expr1290 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_identifier_expr1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_datatype_expr1319 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_datatype_expr1322 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_datatype_expr1324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1353 = new BitSet(new long[]{0x0000080000004020L});
    public static final BitSet FOLLOW_model_attribute_in_attribute_expr1356 = new BitSet(new long[]{0x0000080000010000L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1358 = new BitSet(new long[]{0x0000080000010000L});
    public static final BitSet FOLLOW_EQUALS_in_attribute_expr1361 = new BitSet(new long[]{0x0000080400000000L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1363 = new BitSet(new long[]{0x0000080400000000L});
    public static final BitSet FOLLOW_QUOTES_in_attribute_expr1366 = new BitSet(new long[]{0x0000080000200000L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1368 = new BitSet(new long[]{0x0000080000200000L});
    public static final BitSet FOLLOW_attribute_values_in_attribute_expr1371 = new BitSet(new long[]{0x0000080400000000L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1373 = new BitSet(new long[]{0x0000080400000000L});
    public static final BitSet FOLLOW_QUOTES_in_attribute_expr1376 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_attribute_expr1378 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_LPAREN_in_all_attributes_expr1421 = new BitSet(new long[]{0x0000081000004022L});
    public static final BitSet FOLLOW_attribute_expr_in_all_attributes_expr1426 = new BitSet(new long[]{0x0000081000004022L});
    public static final BitSet FOLLOW_RPAREN_in_all_attributes_expr1432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_prep_expr1484 = new BitSet(new long[]{0x00000200008C0000L});
    public static final BitSet FOLLOW_preposition_in_prep_expr1487 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_prep_expr1489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WS_in_model_object_expr1514 = new BitSet(new long[]{0x000000201010A060L});
    public static final BitSet FOLLOW_model_object_in_model_object_expr1517 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_WS_in_model_object_expr1519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifier_expr_in_attrs_with_identifiers_expr1558 = new BitSet(new long[]{0x0000081008204020L});
    public static final BitSet FOLLOW_all_attributes_expr_in_attrs_with_identifiers_expr1560 = new BitSet(new long[]{0x0000080000200002L});
    public static final BitSet FOLLOW_prep_expr_in_preps_with_model_objects_expr1612 = new BitSet(new long[]{0x000008201010A060L});
    public static final BitSet FOLLOW_model_object_expr_in_preps_with_model_objects_expr1614 = new BitSet(new long[]{0x0000080000200000L});
    public static final BitSet FOLLOW_identifier_expr_in_preps_with_model_objects_expr1616 = new BitSet(new long[]{0x00000A00008C0002L});
    public static final BitSet FOLLOW_verb_expr_in_command_expr1675 = new BitSet(new long[]{0x000008201010A060L});
    public static final BitSet FOLLOW_target_object_expr_in_command_expr1677 = new BitSet(new long[]{0x00000A0000AC0000L});
    public static final BitSet FOLLOW_attrs_with_identifiers_expr_in_command_expr1679 = new BitSet(new long[]{0x00000A00008C0000L});
    public static final BitSet FOLLOW_preps_with_model_objects_expr_in_command_expr1681 = new BitSet(new long[]{0x0000000000000002L});

}