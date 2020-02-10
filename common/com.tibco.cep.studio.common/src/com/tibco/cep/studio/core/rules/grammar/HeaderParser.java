// $ANTLR 3.2 Sep 23, 2009 12:02:23 Header.g 2012-11-14 16:13:38
 
package com.tibco.cep.studio.core.rules.grammar; 

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
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

public class HeaderParser extends BaseRulesParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DESCRIPTION_STATEMENT", "AUTHOR_STATEMENT", "GUID_STATEMENT", "LITERALS", "NAME", "HEADER_BLOCK", "STATEMENTS", "ID", "CHARACTER", "WS", "NEWLINE", "END", "WS_STAR", "'@author'", "'@description'", "'@GUID'"
    };
    public static final int WS_STAR=16;
    public static final int T__19=19;
    public static final int NAME=8;
    public static final int WS=13;
    public static final int NEWLINE=14;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int GUID_STATEMENT=6;
    public static final int HEADER_BLOCK=9;
    public static final int DESCRIPTION_STATEMENT=4;
    public static final int STATEMENTS=10;
    public static final int LITERALS=7;
    public static final int END=15;
    public static final int ID=11;
    public static final int EOF=-1;
    public static final int CHARACTER=12;
    public static final int AUTHOR_STATEMENT=5;

    // delegates
    // delegators


        public HeaderParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public HeaderParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return HeaderParser.tokenNames; }
    public String getGrammarFileName() { return "Header.g"; }


    	public RulesASTNode getHeaderNode() {
    		return null; // root grammars will return the proper value
    	}


    public static class header_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "header"
    // Header.g:48:1: header : (aSt+= authorStatement | dSt+= descriptionStatement | gSt+= guidStatement | idOrChar )+ -> ^( HEADER_BLOCK ^( STATEMENTS ( $aSt)* ( $dSt)* ( $gSt)* ) ) ;
    public final HeaderParser.header_return header() throws RecognitionException {
        HeaderParser.header_return retval = new HeaderParser.header_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        List list_aSt=null;
        List list_dSt=null;
        List list_gSt=null;
        HeaderParser.idOrChar_return idOrChar1 = null;

        RuleReturnScope aSt = null;
        RuleReturnScope dSt = null;
        RuleReturnScope gSt = null;
        RewriteRuleSubtreeStream stream_idOrChar=new RewriteRuleSubtreeStream(adaptor,"rule idOrChar");
        RewriteRuleSubtreeStream stream_authorStatement=new RewriteRuleSubtreeStream(adaptor,"rule authorStatement");
        RewriteRuleSubtreeStream stream_guidStatement=new RewriteRuleSubtreeStream(adaptor,"rule guidStatement");
        RewriteRuleSubtreeStream stream_descriptionStatement=new RewriteRuleSubtreeStream(adaptor,"rule descriptionStatement");
        try {
            // Header.g:48:8: ( (aSt+= authorStatement | dSt+= descriptionStatement | gSt+= guidStatement | idOrChar )+ -> ^( HEADER_BLOCK ^( STATEMENTS ( $aSt)* ( $dSt)* ( $gSt)* ) ) )
            // Header.g:48:10: (aSt+= authorStatement | dSt+= descriptionStatement | gSt+= guidStatement | idOrChar )+
            {
            // Header.g:48:10: (aSt+= authorStatement | dSt+= descriptionStatement | gSt+= guidStatement | idOrChar )+
            int cnt1=0;
            loop1:
            do {
                int alt1=5;
                switch ( input.LA(1) ) {
                case 17:
                    {
                    alt1=1;
                    }
                    break;
                case 18:
                    {
                    alt1=2;
                    }
                    break;
                case 19:
                    {
                    alt1=3;
                    }
                    break;
                case ID:
                case CHARACTER:
                case WS:
                case NEWLINE:
                    {
                    alt1=4;
                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // Header.g:48:12: aSt+= authorStatement
            	    {
            	    pushFollow(FOLLOW_authorStatement_in_header106);
            	    aSt=authorStatement();

            	    state._fsp--;

            	    stream_authorStatement.add(aSt.getTree());
            	    if (list_aSt==null) list_aSt=new ArrayList();
            	    list_aSt.add(aSt.getTree());


            	    }
            	    break;
            	case 2 :
            	    // Header.g:48:35: dSt+= descriptionStatement
            	    {
            	    pushFollow(FOLLOW_descriptionStatement_in_header112);
            	    dSt=descriptionStatement();

            	    state._fsp--;

            	    stream_descriptionStatement.add(dSt.getTree());
            	    if (list_dSt==null) list_dSt=new ArrayList();
            	    list_dSt.add(dSt.getTree());


            	    }
            	    break;
            	case 3 :
            	    // Header.g:48:63: gSt+= guidStatement
            	    {
            	    pushFollow(FOLLOW_guidStatement_in_header118);
            	    gSt=guidStatement();

            	    state._fsp--;

            	    stream_guidStatement.add(gSt.getTree());
            	    if (list_gSt==null) list_gSt=new ArrayList();
            	    list_gSt.add(gSt.getTree());


            	    }
            	    break;
            	case 4 :
            	    // Header.g:49:5: idOrChar
            	    {
            	    pushFollow(FOLLOW_idOrChar_in_header124);
            	    idOrChar1=idOrChar();

            	    state._fsp--;

            	    stream_idOrChar.add(idOrChar1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);



            // AST REWRITE
            // elements: gSt, aSt, dSt
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: dSt, aSt, gSt
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_dSt=new RewriteRuleSubtreeStream(adaptor,"token dSt",list_dSt);
            RewriteRuleSubtreeStream stream_aSt=new RewriteRuleSubtreeStream(adaptor,"token aSt",list_aSt);
            RewriteRuleSubtreeStream stream_gSt=new RewriteRuleSubtreeStream(adaptor,"token gSt",list_gSt);
            root_0 = (RulesASTNode)adaptor.nil();
            // 50:2: -> ^( HEADER_BLOCK ^( STATEMENTS ( $aSt)* ( $dSt)* ( $gSt)* ) )
            {
                // Header.g:50:5: ^( HEADER_BLOCK ^( STATEMENTS ( $aSt)* ( $dSt)* ( $gSt)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(HEADER_BLOCK, "HEADER_BLOCK"), root_1);

                // Header.g:50:20: ^( STATEMENTS ( $aSt)* ( $dSt)* ( $gSt)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(STATEMENTS, "STATEMENTS"), root_2);

                // Header.g:50:33: ( $aSt)*
                while ( stream_aSt.hasNext() ) {
                    adaptor.addChild(root_2, stream_aSt.nextTree());

                }
                stream_aSt.reset();
                // Header.g:50:39: ( $dSt)*
                while ( stream_dSt.hasNext() ) {
                    adaptor.addChild(root_2, stream_dSt.nextTree());

                }
                stream_dSt.reset();
                // Header.g:50:45: ( $gSt)*
                while ( stream_gSt.hasNext() ) {
                    adaptor.addChild(root_2, stream_gSt.nextTree());

                }
                stream_gSt.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "header"

    public static class authorStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "authorStatement"
    // Header.g:52:1: authorStatement : '@author' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( AUTHOR_STATEMENT ^( NAME ( $id)* ) ) ;
    public final HeaderParser.authorStatement_return authorStatement() throws RecognitionException {
        HeaderParser.authorStatement_return retval = new HeaderParser.authorStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal2=null;
        Token EOF4=null;
        List list_id=null;
        HeaderParser.whiteSpace_return whiteSpace3 = null;

        RuleReturnScope id = null;
        RulesASTNode string_literal2_tree=null;
        RulesASTNode EOF4_tree=null;
        RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_idOrChar=new RewriteRuleSubtreeStream(adaptor,"rule idOrChar");
        RewriteRuleSubtreeStream stream_whiteSpace=new RewriteRuleSubtreeStream(adaptor,"rule whiteSpace");
        try {
            // Header.g:53:2: ( '@author' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( AUTHOR_STATEMENT ^( NAME ( $id)* ) ) )
            // Header.g:53:4: '@author' ( whiteSpace (id+= idOrChar )* | EOF )
            {
            string_literal2=(Token)match(input,17,FOLLOW_17_in_authorStatement161);  
            stream_17.add(string_literal2);

            // Header.g:53:14: ( whiteSpace (id+= idOrChar )* | EOF )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0>=WS && LA3_0<=NEWLINE)) ) {
                alt3=1;
            }
            else if ( (LA3_0==EOF) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Header.g:53:15: whiteSpace (id+= idOrChar )*
                    {
                    pushFollow(FOLLOW_whiteSpace_in_authorStatement164);
                    whiteSpace3=whiteSpace();

                    state._fsp--;

                    stream_whiteSpace.add(whiteSpace3.getTree());
                    // Header.g:53:26: (id+= idOrChar )*
                    loop2:
                    do {
                        int alt2=2;
                        switch ( input.LA(1) ) {
                        case ID:
                            {
                            alt2=1;
                            }
                            break;
                        case CHARACTER:
                            {
                            alt2=1;
                            }
                            break;
                        case WS:
                        case NEWLINE:
                            {
                            alt2=1;
                            }
                            break;

                        }

                        switch (alt2) {
                    	case 1 :
                    	    // Header.g:53:27: id+= idOrChar
                    	    {
                    	    pushFollow(FOLLOW_idOrChar_in_authorStatement169);
                    	    id=idOrChar();

                    	    state._fsp--;

                    	    stream_idOrChar.add(id.getTree());
                    	    if (list_id==null) list_id=new ArrayList();
                    	    list_id.add(id.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Header.g:53:45: EOF
                    {
                    EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_authorStatement176);  
                    stream_EOF.add(EOF4);


                    }
                    break;

            }



            // AST REWRITE
            // elements: id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: id
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"token id",list_id);
            root_0 = (RulesASTNode)adaptor.nil();
            // 54:3: -> ^( AUTHOR_STATEMENT ^( NAME ( $id)* ) )
            {
                // Header.g:54:6: ^( AUTHOR_STATEMENT ^( NAME ( $id)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(AUTHOR_STATEMENT, "AUTHOR_STATEMENT"), root_1);

                // Header.g:54:25: ^( NAME ( $id)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(NAME, "NAME"), root_2);

                // Header.g:54:32: ( $id)*
                while ( stream_id.hasNext() ) {
                    adaptor.addChild(root_2, stream_id.nextTree());

                }
                stream_id.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "authorStatement"

    public static class descriptionStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "descriptionStatement"
    // Header.g:57:1: descriptionStatement : '@description' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( DESCRIPTION_STATEMENT ^( LITERALS ( $id)* ) ) ;
    public final HeaderParser.descriptionStatement_return descriptionStatement() throws RecognitionException {
        HeaderParser.descriptionStatement_return retval = new HeaderParser.descriptionStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal5=null;
        Token EOF7=null;
        List list_id=null;
        HeaderParser.whiteSpace_return whiteSpace6 = null;

        RuleReturnScope id = null;
        RulesASTNode string_literal5_tree=null;
        RulesASTNode EOF7_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
        RewriteRuleSubtreeStream stream_idOrChar=new RewriteRuleSubtreeStream(adaptor,"rule idOrChar");
        RewriteRuleSubtreeStream stream_whiteSpace=new RewriteRuleSubtreeStream(adaptor,"rule whiteSpace");
        try {
            // Header.g:58:2: ( '@description' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( DESCRIPTION_STATEMENT ^( LITERALS ( $id)* ) ) )
            // Header.g:58:4: '@description' ( whiteSpace (id+= idOrChar )* | EOF )
            {
            string_literal5=(Token)match(input,18,FOLLOW_18_in_descriptionStatement204);  
            stream_18.add(string_literal5);

            // Header.g:58:19: ( whiteSpace (id+= idOrChar )* | EOF )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=WS && LA5_0<=NEWLINE)) ) {
                alt5=1;
            }
            else if ( (LA5_0==EOF) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // Header.g:58:20: whiteSpace (id+= idOrChar )*
                    {
                    pushFollow(FOLLOW_whiteSpace_in_descriptionStatement207);
                    whiteSpace6=whiteSpace();

                    state._fsp--;

                    stream_whiteSpace.add(whiteSpace6.getTree());
                    // Header.g:58:31: (id+= idOrChar )*
                    loop4:
                    do {
                        int alt4=2;
                        switch ( input.LA(1) ) {
                        case ID:
                            {
                            alt4=1;
                            }
                            break;
                        case CHARACTER:
                            {
                            alt4=1;
                            }
                            break;
                        case WS:
                        case NEWLINE:
                            {
                            alt4=1;
                            }
                            break;

                        }

                        switch (alt4) {
                    	case 1 :
                    	    // Header.g:58:32: id+= idOrChar
                    	    {
                    	    pushFollow(FOLLOW_idOrChar_in_descriptionStatement212);
                    	    id=idOrChar();

                    	    state._fsp--;

                    	    stream_idOrChar.add(id.getTree());
                    	    if (list_id==null) list_id=new ArrayList();
                    	    list_id.add(id.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Header.g:58:50: EOF
                    {
                    EOF7=(Token)match(input,EOF,FOLLOW_EOF_in_descriptionStatement219);  
                    stream_EOF.add(EOF7);


                    }
                    break;

            }



            // AST REWRITE
            // elements: id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: id
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"token id",list_id);
            root_0 = (RulesASTNode)adaptor.nil();
            // 59:3: -> ^( DESCRIPTION_STATEMENT ^( LITERALS ( $id)* ) )
            {
                // Header.g:59:6: ^( DESCRIPTION_STATEMENT ^( LITERALS ( $id)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(DESCRIPTION_STATEMENT, "DESCRIPTION_STATEMENT"), root_1);

                // Header.g:59:30: ^( LITERALS ( $id)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERALS, "LITERALS"), root_2);

                // Header.g:59:41: ( $id)*
                while ( stream_id.hasNext() ) {
                    adaptor.addChild(root_2, stream_id.nextTree());

                }
                stream_id.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "descriptionStatement"

    public static class guidStatement_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "guidStatement"
    // Header.g:62:1: guidStatement : '@GUID' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( GUID_STATEMENT ^( LITERALS ( $id)* ) ) ;
    public final HeaderParser.guidStatement_return guidStatement() throws RecognitionException {
        HeaderParser.guidStatement_return retval = new HeaderParser.guidStatement_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token string_literal8=null;
        Token EOF10=null;
        List list_id=null;
        HeaderParser.whiteSpace_return whiteSpace9 = null;

        RuleReturnScope id = null;
        RulesASTNode string_literal8_tree=null;
        RulesASTNode EOF10_tree=null;
        RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_idOrChar=new RewriteRuleSubtreeStream(adaptor,"rule idOrChar");
        RewriteRuleSubtreeStream stream_whiteSpace=new RewriteRuleSubtreeStream(adaptor,"rule whiteSpace");
        try {
            // Header.g:63:2: ( '@GUID' ( whiteSpace (id+= idOrChar )* | EOF ) -> ^( GUID_STATEMENT ^( LITERALS ( $id)* ) ) )
            // Header.g:63:4: '@GUID' ( whiteSpace (id+= idOrChar )* | EOF )
            {
            string_literal8=(Token)match(input,19,FOLLOW_19_in_guidStatement247);  
            stream_19.add(string_literal8);

            // Header.g:63:12: ( whiteSpace (id+= idOrChar )* | EOF )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>=WS && LA7_0<=NEWLINE)) ) {
                alt7=1;
            }
            else if ( (LA7_0==EOF) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // Header.g:63:13: whiteSpace (id+= idOrChar )*
                    {
                    pushFollow(FOLLOW_whiteSpace_in_guidStatement250);
                    whiteSpace9=whiteSpace();

                    state._fsp--;

                    stream_whiteSpace.add(whiteSpace9.getTree());
                    // Header.g:63:24: (id+= idOrChar )*
                    loop6:
                    do {
                        int alt6=2;
                        switch ( input.LA(1) ) {
                        case ID:
                            {
                            alt6=1;
                            }
                            break;
                        case CHARACTER:
                            {
                            alt6=1;
                            }
                            break;
                        case WS:
                        case NEWLINE:
                            {
                            alt6=1;
                            }
                            break;

                        }

                        switch (alt6) {
                    	case 1 :
                    	    // Header.g:63:25: id+= idOrChar
                    	    {
                    	    pushFollow(FOLLOW_idOrChar_in_guidStatement255);
                    	    id=idOrChar();

                    	    state._fsp--;

                    	    stream_idOrChar.add(id.getTree());
                    	    if (list_id==null) list_id=new ArrayList();
                    	    list_id.add(id.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Header.g:63:43: EOF
                    {
                    EOF10=(Token)match(input,EOF,FOLLOW_EOF_in_guidStatement262);  
                    stream_EOF.add(EOF10);


                    }
                    break;

            }



            // AST REWRITE
            // elements: id
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: id
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_id=new RewriteRuleSubtreeStream(adaptor,"token id",list_id);
            root_0 = (RulesASTNode)adaptor.nil();
            // 64:3: -> ^( GUID_STATEMENT ^( LITERALS ( $id)* ) )
            {
                // Header.g:64:6: ^( GUID_STATEMENT ^( LITERALS ( $id)* ) )
                {
                RulesASTNode root_1 = (RulesASTNode)adaptor.nil();
                root_1 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(GUID_STATEMENT, "GUID_STATEMENT"), root_1);

                // Header.g:64:23: ^( LITERALS ( $id)* )
                {
                RulesASTNode root_2 = (RulesASTNode)adaptor.nil();
                root_2 = (RulesASTNode)adaptor.becomeRoot((RulesASTNode)adaptor.create(LITERALS, "LITERALS"), root_2);

                // Header.g:64:34: ( $id)*
                while ( stream_id.hasNext() ) {
                    adaptor.addChild(root_2, stream_id.nextTree());

                }
                stream_id.reset();

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "guidStatement"

    public static class idOrChar_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "idOrChar"
    // Header.g:67:1: idOrChar : ( ID | CHARACTER | whiteSpace );
    public final HeaderParser.idOrChar_return idOrChar() throws RecognitionException {
        HeaderParser.idOrChar_return retval = new HeaderParser.idOrChar_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token ID11=null;
        Token CHARACTER12=null;
        HeaderParser.whiteSpace_return whiteSpace13 = null;


        RulesASTNode ID11_tree=null;
        RulesASTNode CHARACTER12_tree=null;

        try {
            // Header.g:68:2: ( ID | CHARACTER | whiteSpace )
            int alt8=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt8=1;
                }
                break;
            case CHARACTER:
                {
                alt8=2;
                }
                break;
            case WS:
            case NEWLINE:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // Header.g:68:4: ID
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    ID11=(Token)match(input,ID,FOLLOW_ID_in_idOrChar291); 
                    ID11_tree = (RulesASTNode)adaptor.create(ID11);
                    adaptor.addChild(root_0, ID11_tree);


                    }
                    break;
                case 2 :
                    // Header.g:68:9: CHARACTER
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    CHARACTER12=(Token)match(input,CHARACTER,FOLLOW_CHARACTER_in_idOrChar295); 
                    CHARACTER12_tree = (RulesASTNode)adaptor.create(CHARACTER12);
                    adaptor.addChild(root_0, CHARACTER12_tree);


                    }
                    break;
                case 3 :
                    // Header.g:68:21: whiteSpace
                    {
                    root_0 = (RulesASTNode)adaptor.nil();

                    pushFollow(FOLLOW_whiteSpace_in_idOrChar299);
                    whiteSpace13=whiteSpace();

                    state._fsp--;

                    adaptor.addChild(root_0, whiteSpace13.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "idOrChar"

    public static class whiteSpace_return extends ParserRuleReturnScope {
        RulesASTNode tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whiteSpace"
    // Header.g:71:1: whiteSpace : ( WS | NEWLINE );
    public final HeaderParser.whiteSpace_return whiteSpace() throws RecognitionException {
        HeaderParser.whiteSpace_return retval = new HeaderParser.whiteSpace_return();
        retval.start = input.LT(1);

        RulesASTNode root_0 = null;

        Token set14=null;

        RulesASTNode set14_tree=null;

        try {
            // Header.g:72:2: ( WS | NEWLINE )
            // Header.g:
            {
            root_0 = (RulesASTNode)adaptor.nil();

            set14=(Token)input.LT(1);
            if ( (input.LA(1)>=WS && input.LA(1)<=NEWLINE) ) {
                input.consume();
                adaptor.addChild(root_0, (RulesASTNode)adaptor.create(set14));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (RulesASTNode)adaptor.rulePostProcessing(root_0);
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
    // $ANTLR end "whiteSpace"

    // Delegated rules


 

    public static final BitSet FOLLOW_authorStatement_in_header106 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_descriptionStatement_in_header112 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_guidStatement_in_header118 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_idOrChar_in_header124 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_17_in_authorStatement161 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_whiteSpace_in_authorStatement164 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_idOrChar_in_authorStatement169 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_EOF_in_authorStatement176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_descriptionStatement204 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_whiteSpace_in_descriptionStatement207 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_idOrChar_in_descriptionStatement212 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_EOF_in_descriptionStatement219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_guidStatement247 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_whiteSpace_in_guidStatement250 = new BitSet(new long[]{0x00000000000E7800L});
    public static final BitSet FOLLOW_idOrChar_in_guidStatement255 = new BitSet(new long[]{0x00000000000E7802L});
    public static final BitSet FOLLOW_EOF_in_guidStatement262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_idOrChar291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CHARACTER_in_idOrChar295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whiteSpace_in_idOrChar299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_whiteSpace0 = new BitSet(new long[]{0x0000000000000002L});

}