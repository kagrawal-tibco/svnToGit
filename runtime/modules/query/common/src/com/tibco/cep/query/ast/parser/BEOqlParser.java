// $ANTLR 3.2 Sep 23, 2009 12:02:23 Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g 2012-05-07 17:57:18

      	package com.tibco.cep.query.ast.parser;
      

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class BEOqlParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SELECT_EXPR", "FROM_CLAUSE", "DELETE_EXPR", "ORDER_CLAUSE", "WHERE_CLAUSE", "ITERATOR_DEF", "PROJECTION_ATTRIBUTES", "PROJECTION_LIST", "PROJECTION", "GROUP_CLAUSE", "HAVING_CLAUSE", "GROUP_COLUMN", "ARRAY_INDEX", "ARG_LIST", "FIELD_LIST", "CAST_OP", "ALIAS_CLAUSE", "ALIAS", "IN_CLAUSE", "ASTERISK_CLAUSE", "FOR_ALL_CLAUSE", "EXISTS_CLAUSE", "LIKE_CLAUSE", "BETWEEN_CLAUSE", "PATH_EXPRESSION", "CAST_EXPRESSION", "OR_EXPRESSION", "AND_EXPRESSION", "PATHFUNCTION_EXPRESSION", "FUNCTION_EXPRESSION", "BIND_VARIABLE_EXPRESSION", "BLOCK_EXPRESSION", "SUBSELECT_EXPRESSION", "UNARY_EXPRESSION", "UNARY_OP", "INDEX_SINGLE", "INDEX_MULTIPLE", "RANGE_EXPRESSION", "STREAM_DEF", "WINDOW_DEF", "ACCEPT_CLAUSE", "ACCEPT_NEW", "ACCEPT_ALL", "EMIT_CLAUSE", "EMIT_NEW", "EMIT_DEAD", "POLICY_CLAUSE", "POLICY_BY_CLAUSE", "TIME_WINDOW", "SLIDING_WINDOW", "TUMBLING_WINDOW", "USING_CLAUSE", "PURGE_CLAUSE", "PURGE_FIRST_CLAUSE", "PURGE_WHEN_CLAUSE", "LIMIT_CLAUSE", "LITERALS", "SORT_CRITERION", "SORT_DIRECTION", "SCOPE_IDENTIFIER", "NUMBER_LITERAL", "NULL_LITERAL", "BOOLEAN_LITERAL", "CHAR_LITERAL", "STRING_LITERAL", "DATETIME_LITERAL", "UNARY_MINUS", "IDENTIFIER", "TOK_RPAREN", "TOK_LPAREN", "TOK_COMMA", "TOK_SEMIC", "TOK_DOT", "TOK_DOTDOT", "TOK_COLON", "TOK_INDIRECT", "TOK_AT", "TOK_CONCAT", "TOK_EQ", "TOK_PLUS", "TOK_MINUS", "TOK_SLASH", "TOK_STAR", "TOK_LE", "TOK_GE", "TOK_NE", "TOK_LT", "TOK_GT", "TOK_LBRACK", "TOK_RBRACK", "TOK_DOLLAR", "TOK_AMPERSAND", "TOK_HASH", "TOK_DOUBLE_QUOTE", "TOK_LCURLY", "TOK_RCURLY", "TOK_DISTINCT", "TOK_FROM", "TOK_SELECT", "TOK_DELETE", "TOK_IN", "TOK_AS", "TOK_WHERE", "TOK_GROUP", "TOK_BY", "TOK_HAVING", "TOK_ORDER", "TOK_ASC", "TOK_DESC", "TOK_OR", "TOK_AND", "TOK_FOR", "TOK_ALL", "TOK_EXISTS", "TOK_LIKE", "TOK_BETWEEN", "TOK_UNION", "TOK_EXCEPT", "TOK_INTERSECT", "TOK_MOD", "TOK_ABS", "TOK_NOT", "TOK_FIRST", "TOK_LAST", "TOK_SLIDING", "TOK_TUMBLING", "TOK_UNIQUE", "TOK_SUM", "TOK_MIN", "TOK_MAX", "TOK_AVG", "TOK_COUNT", "TOK_PENDING_COUNT", "TOK_UNDEFINED", "TOK_DEFINED", "TOK_NULL", "TOK_TRUE", "TOK_FALSE", "TOK_CONCEPT", "TOK_ENTITY", "TOK_EVENT", "TOK_OBJECT", "TOK_INT", "TOK_LONG", "TOK_DOUBLE", "TOK_CHAR", "TOK_STRING", "TOK_BOOLEAN", "TOK_DATETIME", "TOK_DATE", "TOK_TIME", "TOK_TIMESTAMP", "TOK_LIMIT", "TOK_ACCEPT", "TOK_EMIT", "TOK_POLICY", "TOK_MAINTAIN", "TOK_USING", "TOK_PURGE", "TOK_WHEN", "TOK_OFFSET", "TOK_NEW", "TOK_DEAD", "TOK_TIME_MILLISECONDS", "TOK_TIME_SECONDS", "TOK_TIME_MINUTES", "TOK_TIME_HOURS", "TOK_TIME_DAYS", "HexDigit", "NameFirstCharacter", "NameCharacter", "OctalEscape", "UnicodeEscape", "EscapeSequence", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "HexLiteral", "DIGIT_ZERO", "DIGIT_OCTAL_RANGE", "DIGIT_DECIMAL_RANGE", "DIGIT_FULL_RANGE", "OCTALDIGIT", "DIGIT", "DIGITS", "DecimalLiteral", "OctalLiteral", "DateTimeLiteral", "CharLiteral", "StringLiteral", "TOK_APPROXIMATE_NUMERIC_LITERAL", "TOK_EXACT_NUMERIC_LITERAL", "Identifier", "WS", "NewLine", "CommentLine", "SLComment", "MultiLineComment"
    };
    public static final int TOK_CONCEPT=142;
    public static final int TOK_AMPERSAND=95;
    public static final int TOK_NE=89;
    public static final int TOK_DISTINCT=100;
    public static final int EXISTS_CLAUSE=25;
    public static final int TOK_CONCAT=81;
    public static final int TOK_TIME=154;
    public static final int SORT_CRITERION=61;
    public static final int TOK_TIME_HOURS=170;
    public static final int EOF=-1;
    public static final int TOK_NOT=125;
    public static final int TOK_TIME_MILLISECONDS=167;
    public static final int NewLine=198;
    public static final int STRING_LITERAL=68;
    public static final int BLOCK_EXPRESSION=35;
    public static final int TOK_EXACT_NUMERIC_LITERAL=195;
    public static final int CharLiteral=192;
    public static final int TOK_DEFINED=138;
    public static final int TOK_SEMIC=75;
    public static final int TOK_POLICY=159;
    public static final int TOK_STAR=86;
    public static final int TOK_FROM=101;
    public static final int TOK_LPAREN=73;
    public static final int TOK_HASH=96;
    public static final int TOK_OFFSET=164;
    public static final int ITERATOR_DEF=9;
    public static final int DateTimeLiteral=191;
    public static final int ASTERISK_CLAUSE=23;
    public static final int TOK_ALL=116;
    public static final int IntegerTypeSuffix=178;
    public static final int NameFirstCharacter=173;
    public static final int CAST_OP=19;
    public static final int FIELD_LIST=18;
    public static final int OR_EXPRESSION=30;
    public static final int TOK_BETWEEN=119;
    public static final int TOK_EXISTS=117;
    public static final int TOK_APPROXIMATE_NUMERIC_LITERAL=194;
    public static final int TOK_DOUBLE_QUOTE=97;
    public static final int UNARY_MINUS=70;
    public static final int NULL_LITERAL=65;
    public static final int TOK_ORDER=110;
    public static final int TOK_WHERE=106;
    public static final int FOR_ALL_CLAUSE=24;
    public static final int WS=197;
    public static final int TIME_WINDOW=52;
    public static final int TOK_PLUS=83;
    public static final int OCTALDIGIT=186;
    public static final int TOK_COMMA=74;
    public static final int ALIAS=21;
    public static final int TOK_INTERSECT=122;
    public static final int TOK_UNION=120;
    public static final int BIND_VARIABLE_EXPRESSION=34;
    public static final int TOK_SELECT=102;
    public static final int TOK_OR=113;
    public static final int LIMIT_CLAUSE=59;
    public static final int TOK_LIKE=118;
    public static final int TOK_MAX=133;
    public static final int SLIDING_WINDOW=53;
    public static final int TOK_CHAR=149;
    public static final int CAST_EXPRESSION=29;
    public static final int TUMBLING_WINDOW=54;
    public static final int PATHFUNCTION_EXPRESSION=32;
    public static final int TOK_DOUBLE=148;
    public static final int TOK_DELETE=103;
    public static final int TOK_DATETIME=152;
    public static final int HexDigit=172;
    public static final int AND_EXPRESSION=31;
    public static final int UNARY_EXPRESSION=37;
    public static final int TOK_AS=105;
    public static final int WHERE_CLAUSE=8;
    public static final int TOK_AT=80;
    public static final int TOK_TIMESTAMP=155;
    public static final int HAVING_CLAUSE=14;
    public static final int PROJECTION_ATTRIBUTES=10;
    public static final int TOK_EMIT=158;
    public static final int BETWEEN_CLAUSE=27;
    public static final int TOK_TRUE=140;
    public static final int ARRAY_INDEX=16;
    public static final int TOK_AVG=134;
    public static final int TOK_MOD=123;
    public static final int ACCEPT_NEW=45;
    public static final int TOK_EVENT=144;
    public static final int SCOPE_IDENTIFIER=63;
    public static final int ACCEPT_CLAUSE=44;
    public static final int MultiLineComment=201;
    public static final int TOK_BY=108;
    public static final int HexLiteral=181;
    public static final int TOK_SUM=131;
    public static final int TOK_RBRACK=93;
    public static final int TOK_UNIQUE=130;
    public static final int TOK_ACCEPT=157;
    public static final int DIGIT_OCTAL_RANGE=183;
    public static final int TOK_FALSE=141;
    public static final int StringLiteral=193;
    public static final int BOOLEAN_LITERAL=66;
    public static final int TOK_NEW=165;
    public static final int CHAR_LITERAL=67;
    public static final int TOK_UNDEFINED=137;
    public static final int POLICY_CLAUSE=50;
    public static final int DIGIT_ZERO=182;
    public static final int PROJECTION=12;
    public static final int IN_CLAUSE=22;
    public static final int TOK_EXCEPT=121;
    public static final int DELETE_EXPR=6;
    public static final int OctalEscape=175;
    public static final int UNARY_OP=38;
    public static final int STREAM_DEF=42;
    public static final int TOK_MAINTAIN=160;
    public static final int FloatTypeSuffix=180;
    public static final int OctalLiteral=190;
    public static final int ORDER_CLAUSE=7;
    public static final int TOK_INDIRECT=79;
    public static final int POLICY_BY_CLAUSE=51;
    public static final int TOK_PURGE=162;
    public static final int ACCEPT_ALL=46;
    public static final int TOK_NULL=139;
    public static final int TOK_ENTITY=143;
    public static final int TOK_GROUP=107;
    public static final int LITERALS=60;
    public static final int TOK_FIRST=126;
    public static final int PATH_EXPRESSION=28;
    public static final int EMIT_CLAUSE=47;
    public static final int Identifier=196;
    public static final int DIGIT_FULL_RANGE=185;
    public static final int TOK_EQ=82;
    public static final int TOK_DATE=153;
    public static final int PURGE_CLAUSE=56;
    public static final int WINDOW_DEF=43;
    public static final int INDEX_SINGLE=39;
    public static final int TOK_WHEN=163;
    public static final int SORT_DIRECTION=62;
    public static final int CommentLine=199;
    public static final int INDEX_MULTIPLE=40;
    public static final int TOK_SLASH=85;
    public static final int TOK_ASC=111;
    public static final int LIKE_CLAUSE=26;
    public static final int ARG_LIST=17;
    public static final int GROUP_COLUMN=15;
    public static final int TOK_COUNT=135;
    public static final int DIGIT_DECIMAL_RANGE=184;
    public static final int TOK_DESC=112;
    public static final int TOK_USING=161;
    public static final int TOK_MIN=132;
    public static final int RANGE_EXPRESSION=41;
    public static final int TOK_GT=91;
    public static final int TOK_ABS=124;
    public static final int TOK_STRING=150;
    public static final int FUNCTION_EXPRESSION=33;
    public static final int SELECT_EXPR=4;
    public static final int TOK_GE=88;
    public static final int TOK_TIME_MINUTES=169;
    public static final int NameCharacter=174;
    public static final int EscapeSequence=177;
    public static final int EMIT_DEAD=49;
    public static final int TOK_FOR=115;
    public static final int EMIT_NEW=48;
    public static final int DIGITS=188;
    public static final int Exponent=179;
    public static final int TOK_RCURLY=99;
    public static final int USING_CLAUSE=55;
    public static final int FROM_CLAUSE=5;
    public static final int TOK_DOT=76;
    public static final int TOK_IN=104;
    public static final int TOK_INT=146;
    public static final int ALIAS_CLAUSE=20;
    public static final int TOK_AND=114;
    public static final int TOK_PENDING_COUNT=136;
    public static final int TOK_COLON=78;
    public static final int IDENTIFIER=71;
    public static final int TOK_LBRACK=92;
    public static final int TOK_TIME_DAYS=171;
    public static final int DIGIT=187;
    public static final int NUMBER_LITERAL=64;
    public static final int PROJECTION_LIST=11;
    public static final int TOK_SLIDING=128;
    public static final int TOK_DOTDOT=77;
    public static final int TOK_HAVING=109;
    public static final int GROUP_CLAUSE=13;
    public static final int TOK_LT=90;
    public static final int PURGE_WHEN_CLAUSE=58;
    public static final int TOK_RPAREN=72;
    public static final int SUBSELECT_EXPRESSION=36;
    public static final int TOK_TIME_SECONDS=168;
    public static final int TOK_LCURLY=98;
    public static final int TOK_LONG=147;
    public static final int DATETIME_LITERAL=69;
    public static final int DecimalLiteral=189;
    public static final int TOK_BOOLEAN=151;
    public static final int TOK_LE=87;
    public static final int TOK_DOLLAR=94;
    public static final int TOK_OBJECT=145;
    public static final int TOK_LIMIT=156;
    public static final int TOK_LAST=127;
    public static final int UnicodeEscape=176;
    public static final int SLComment=200;
    public static final int TOK_MINUS=84;
    public static final int PURGE_FIRST_CLAUSE=57;
    public static final int TOK_DEAD=166;
    public static final int TOK_TUMBLING=129;

    // delegates
    // delegators


        public BEOqlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public BEOqlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[297+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return BEOqlParser.tokenNames; }
    public String getGrammarFileName() { return "Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g"; }


            boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
            private void debugOut(String msg) {
                if(debug) {
                    System.out.println(msg);
                }
            }


            public String getErrorMessage(RecognitionException e, String[] tokenNames)
            {
                return super.getErrorMessage(e, BEOqlParser.tokenNames);
            }

            protected void mismatch(IntStream input, int ttype, BitSet follow)  throws RecognitionException
            {
                throw new MismatchedTokenException(ttype, input);
            }

            public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
                 throw e;
            }

            public void recover(IntStream input, RecognitionException re) {
                 throw new RuntimeException(re);
            }

            public void displayRecognitionError(String[] tokenNames,RecognitionException e) {
              throw new RuntimeException(e);
            }

          

    public static class query_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:623:7: query : ( selectExpr ( TOK_SEMIC )? EOF -> selectExpr | deleteExpr ( TOK_SEMIC )? EOF -> deleteExpr | EOF -> EOF );
    public final BEOqlParser.query_return query() throws RecognitionException {
        BEOqlParser.query_return retval = new BEOqlParser.query_return();
        retval.start = input.LT(1);
        int query_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_SEMIC2=null;
        Token EOF3=null;
        Token TOK_SEMIC5=null;
        Token EOF6=null;
        Token EOF7=null;
        BEOqlParser.selectExpr_return selectExpr1 = null;

        BEOqlParser.deleteExpr_return deleteExpr4 = null;


        CommonTree TOK_SEMIC2_tree=null;
        CommonTree EOF3_tree=null;
        CommonTree TOK_SEMIC5_tree=null;
        CommonTree EOF6_tree=null;
        CommonTree EOF7_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleTokenStream stream_TOK_SEMIC=new RewriteRuleTokenStream(adaptor,"token TOK_SEMIC");
        RewriteRuleSubtreeStream stream_deleteExpr=new RewriteRuleSubtreeStream(adaptor,"rule deleteExpr");
        RewriteRuleSubtreeStream stream_selectExpr=new RewriteRuleSubtreeStream(adaptor,"rule selectExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:623:13: ( selectExpr ( TOK_SEMIC )? EOF -> selectExpr | deleteExpr ( TOK_SEMIC )? EOF -> deleteExpr | EOF -> EOF )
            int alt3=3;
            switch ( input.LA(1) ) {
            case TOK_SELECT:
                {
                alt3=1;
                }
                break;
            case TOK_DELETE:
                {
                alt3=2;
                }
                break;
            case EOF:
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
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:624:15: selectExpr ( TOK_SEMIC )? EOF
                    {
                    pushFollow(FOLLOW_selectExpr_in_query7936);
                    selectExpr1=selectExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selectExpr.add(selectExpr1.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:624:26: ( TOK_SEMIC )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==TOK_SEMIC) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:624:28: TOK_SEMIC
                            {
                            TOK_SEMIC2=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_query7940); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_SEMIC.add(TOK_SEMIC2);


                            }
                            break;

                    }

                    EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_query7944); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EOF.add(EOF3);



                    // AST REWRITE
                    // elements: selectExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 624:45: -> selectExpr
                    {
                        adaptor.addChild(root_0, stream_selectExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:625:17: deleteExpr ( TOK_SEMIC )? EOF
                    {
                    pushFollow(FOLLOW_deleteExpr_in_query7967);
                    deleteExpr4=deleteExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_deleteExpr.add(deleteExpr4.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:625:28: ( TOK_SEMIC )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0==TOK_SEMIC) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:625:30: TOK_SEMIC
                            {
                            TOK_SEMIC5=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_query7971); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_SEMIC.add(TOK_SEMIC5);


                            }
                            break;

                    }

                    EOF6=(Token)match(input,EOF,FOLLOW_EOF_in_query7975); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EOF.add(EOF6);



                    // AST REWRITE
                    // elements: deleteExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 625:47: -> deleteExpr
                    {
                        adaptor.addChild(root_0, stream_deleteExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:626:17: EOF
                    {
                    EOF7=(Token)match(input,EOF,FOLLOW_EOF_in_query7998); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_EOF.add(EOF7);



                    // AST REWRITE
                    // elements: EOF
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 626:21: -> EOF
                    {
                        adaptor.addChild(root_0, stream_EOF.nextNode());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 1, query_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "query"

    public static class selectExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "selectExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:629:7: selectExpr : lc= TOK_SELECT ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? -> ^( SELECT_EXPR[$lc,$selectExpr.text] ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? ) ;
    public final BEOqlParser.selectExpr_return selectExpr() throws RecognitionException {
        BEOqlParser.selectExpr_return retval = new BEOqlParser.selectExpr_return();
        retval.start = input.LT(1);
        int selectExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token TOK_DISTINCT9=null;
        BEOqlParser.limitClause_return limitClause8 = null;

        BEOqlParser.projectionAttributes_return projectionAttributes10 = null;

        BEOqlParser.fromClause_return fromClause11 = null;

        BEOqlParser.whereClause_return whereClause12 = null;

        BEOqlParser.groupClause_return groupClause13 = null;

        BEOqlParser.orderClause_return orderClause14 = null;


        CommonTree lc_tree=null;
        CommonTree TOK_DISTINCT9_tree=null;
        RewriteRuleTokenStream stream_TOK_SELECT=new RewriteRuleTokenStream(adaptor,"token TOK_SELECT");
        RewriteRuleTokenStream stream_TOK_DISTINCT=new RewriteRuleTokenStream(adaptor,"token TOK_DISTINCT");
        RewriteRuleSubtreeStream stream_whereClause=new RewriteRuleSubtreeStream(adaptor,"rule whereClause");
        RewriteRuleSubtreeStream stream_limitClause=new RewriteRuleSubtreeStream(adaptor,"rule limitClause");
        RewriteRuleSubtreeStream stream_orderClause=new RewriteRuleSubtreeStream(adaptor,"rule orderClause");
        RewriteRuleSubtreeStream stream_groupClause=new RewriteRuleSubtreeStream(adaptor,"rule groupClause");
        RewriteRuleSubtreeStream stream_projectionAttributes=new RewriteRuleSubtreeStream(adaptor,"rule projectionAttributes");
        RewriteRuleSubtreeStream stream_fromClause=new RewriteRuleSubtreeStream(adaptor,"rule fromClause");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:629:18: (lc= TOK_SELECT ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? -> ^( SELECT_EXPR[$lc,$selectExpr.text] ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:630:15: lc= TOK_SELECT ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )?
            {
            lc=(Token)match(input,TOK_SELECT,FOLLOW_TOK_SELECT_in_selectExpr8043); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_SELECT.add(lc);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:632:15: ( limitClause )?
            int alt4=2;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:632:16: limitClause
                    {
                    pushFollow(FOLLOW_limitClause_in_selectExpr8061);
                    limitClause8=limitClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limitClause.add(limitClause8.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:634:15: ( TOK_DISTINCT )?
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:634:16: TOK_DISTINCT
                    {
                    TOK_DISTINCT9=(Token)match(input,TOK_DISTINCT,FOLLOW_TOK_DISTINCT_in_selectExpr8081); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_DISTINCT.add(TOK_DISTINCT9);


                    }
                    break;

            }

            pushFollow(FOLLOW_projectionAttributes_in_selectExpr8100);
            projectionAttributes10=projectionAttributes();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_projectionAttributes.add(projectionAttributes10.getTree());
            pushFollow(FOLLOW_fromClause_in_selectExpr8117);
            fromClause11=fromClause();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_fromClause.add(fromClause11.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:639:15: ( whereClause )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==TOK_WHERE) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:639:17: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr8135);
                    whereClause12=whereClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_whereClause.add(whereClause12.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:640:15: ( groupClause )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==TOK_GROUP) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:640:17: groupClause
                    {
                    pushFollow(FOLLOW_groupClause_in_selectExpr8156);
                    groupClause13=groupClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_groupClause.add(groupClause13.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:641:15: ( orderClause )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==TOK_ORDER) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:641:17: orderClause
                    {
                    pushFollow(FOLLOW_orderClause_in_selectExpr8177);
                    orderClause14=orderClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orderClause.add(orderClause14.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: projectionAttributes, orderClause, TOK_DISTINCT, groupClause, whereClause, limitClause, fromClause
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 642:8: -> ^( SELECT_EXPR[$lc,$selectExpr.text] ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:11: ^( SELECT_EXPR[$lc,$selectExpr.text] ( limitClause )? ( TOK_DISTINCT )? projectionAttributes fromClause ( whereClause )? ( groupClause )? ( orderClause )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SELECT_EXPR, lc, input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:47: ( limitClause )?
                if ( stream_limitClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_limitClause.nextTree());

                }
                stream_limitClause.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:62: ( TOK_DISTINCT )?
                if ( stream_TOK_DISTINCT.hasNext() ) {
                    adaptor.addChild(root_1, stream_TOK_DISTINCT.nextNode());

                }
                stream_TOK_DISTINCT.reset();
                adaptor.addChild(root_1, stream_projectionAttributes.nextTree());
                adaptor.addChild(root_1, stream_fromClause.nextTree());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:110: ( whereClause )?
                if ( stream_whereClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_whereClause.nextTree());

                }
                stream_whereClause.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:125: ( groupClause )?
                if ( stream_groupClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_groupClause.nextTree());

                }
                stream_groupClause.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:642:140: ( orderClause )?
                if ( stream_orderClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_orderClause.nextTree());

                }
                stream_orderClause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 2, selectExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "selectExpr"

    public static class deleteExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "deleteExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:645:7: deleteExpr : lc= TOK_DELETE ( TOK_STAR )? fc= fromClause whereClause -> ^( DELETE_EXPR[$lc,$deleteExpr.text] ^( PROJECTION_ATTRIBUTES[$lc,\"id,extId\"] ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] ) ) fromClause whereClause ) ;
    public final BEOqlParser.deleteExpr_return deleteExpr() throws RecognitionException {
        BEOqlParser.deleteExpr_return retval = new BEOqlParser.deleteExpr_return();
        retval.start = input.LT(1);
        int deleteExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token TOK_STAR15=null;
        BEOqlParser.fromClause_return fc = null;

        BEOqlParser.whereClause_return whereClause16 = null;


        CommonTree lc_tree=null;
        CommonTree TOK_STAR15_tree=null;
        RewriteRuleTokenStream stream_TOK_STAR=new RewriteRuleTokenStream(adaptor,"token TOK_STAR");
        RewriteRuleTokenStream stream_TOK_DELETE=new RewriteRuleTokenStream(adaptor,"token TOK_DELETE");
        RewriteRuleSubtreeStream stream_whereClause=new RewriteRuleSubtreeStream(adaptor,"rule whereClause");
        RewriteRuleSubtreeStream stream_fromClause=new RewriteRuleSubtreeStream(adaptor,"rule fromClause");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:645:18: (lc= TOK_DELETE ( TOK_STAR )? fc= fromClause whereClause -> ^( DELETE_EXPR[$lc,$deleteExpr.text] ^( PROJECTION_ATTRIBUTES[$lc,\"id,extId\"] ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] ) ) fromClause whereClause ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:646:15: lc= TOK_DELETE ( TOK_STAR )? fc= fromClause whereClause
            {
            lc=(Token)match(input,TOK_DELETE,FOLLOW_TOK_DELETE_in_deleteExpr8265); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_DELETE.add(lc);

            if ( state.backtracking==0 ) {
            }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:647:14: ( TOK_STAR )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==TOK_STAR) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:647:15: TOK_STAR
                    {
                    TOK_STAR15=(Token)match(input,TOK_STAR,FOLLOW_TOK_STAR_in_deleteExpr8283); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_STAR.add(TOK_STAR15);


                    }
                    break;

            }

            pushFollow(FOLLOW_fromClause_in_deleteExpr8305);
            fc=fromClause();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_fromClause.add(fc.getTree());
            pushFollow(FOLLOW_whereClause_in_deleteExpr8321);
            whereClause16=whereClause();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_whereClause.add(whereClause16.getTree());


            // AST REWRITE
            // elements: fromClause, whereClause
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 649:27: -> ^( DELETE_EXPR[$lc,$deleteExpr.text] ^( PROJECTION_ATTRIBUTES[$lc,\"id,extId\"] ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] ) ) fromClause whereClause )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:649:30: ^( DELETE_EXPR[$lc,$deleteExpr.text] ^( PROJECTION_ATTRIBUTES[$lc,\"id,extId\"] ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] ) ) fromClause whereClause )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(DELETE_EXPR, lc, input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:649:66: ^( PROJECTION_ATTRIBUTES[$lc,\"id,extId\"] ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] ) )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION_ATTRIBUTES, lc, "id,extId"), root_2);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:649:107: ^( PROJECTION[$lc,\"id,extId\"] IDENTIFIER[$lc,\"id\"] IDENTIFIER[$lc,\"extId\"] )
                {
                CommonTree root_3 = (CommonTree)adaptor.nil();
                root_3 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION, lc, "id,extId"), root_3);

                adaptor.addChild(root_3, (CommonTree)adaptor.create(IDENTIFIER, lc, "id"));
                adaptor.addChild(root_3, (CommonTree)adaptor.create(IDENTIFIER, lc, "extId"));

                adaptor.addChild(root_2, root_3);
                }

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_fromClause.nextTree());
                adaptor.addChild(root_1, stream_whereClause.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 3, deleteExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "deleteExpr"

    public static class fromClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fromClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:653:7: fromClause : lc= TOK_FROM iteratorDef ( TOK_COMMA iteratorDef )* -> ^( FROM_CLAUSE[$lc,$fromClause.text] ( iteratorDef )+ ) ;
    public final BEOqlParser.fromClause_return fromClause() throws RecognitionException {
        BEOqlParser.fromClause_return retval = new BEOqlParser.fromClause_return();
        retval.start = input.LT(1);
        int fromClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token TOK_COMMA18=null;
        BEOqlParser.iteratorDef_return iteratorDef17 = null;

        BEOqlParser.iteratorDef_return iteratorDef19 = null;


        CommonTree lc_tree=null;
        CommonTree TOK_COMMA18_tree=null;
        RewriteRuleTokenStream stream_TOK_FROM=new RewriteRuleTokenStream(adaptor,"token TOK_FROM");
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_iteratorDef=new RewriteRuleSubtreeStream(adaptor,"rule iteratorDef");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:653:18: (lc= TOK_FROM iteratorDef ( TOK_COMMA iteratorDef )* -> ^( FROM_CLAUSE[$lc,$fromClause.text] ( iteratorDef )+ ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:654:15: lc= TOK_FROM iteratorDef ( TOK_COMMA iteratorDef )*
            {
            lc=(Token)match(input,TOK_FROM,FOLLOW_TOK_FROM_in_fromClause8409); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_FROM.add(lc);

            pushFollow(FOLLOW_iteratorDef_in_fromClause8411);
            iteratorDef17=iteratorDef();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_iteratorDef.add(iteratorDef17.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:655:15: ( TOK_COMMA iteratorDef )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==TOK_COMMA) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:656:19: TOK_COMMA iteratorDef
            	    {
            	    TOK_COMMA18=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_fromClause8447); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA18);

            	    pushFollow(FOLLOW_iteratorDef_in_fromClause8467);
            	    iteratorDef19=iteratorDef();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_iteratorDef.add(iteratorDef19.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);



            // AST REWRITE
            // elements: iteratorDef
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 659:15: -> ^( FROM_CLAUSE[$lc,$fromClause.text] ( iteratorDef )+ )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:659:18: ^( FROM_CLAUSE[$lc,$fromClause.text] ( iteratorDef )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FROM_CLAUSE, lc, input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_iteratorDef.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_iteratorDef.hasNext() ) {
                    adaptor.addChild(root_1, stream_iteratorDef.nextTree());

                }
                stream_iteratorDef.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 4, fromClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "fromClause"

    public static class iteratorDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "iteratorDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:663:7: iteratorDef : ( ( pathExpr ( streamDef | aliasDef )? ) -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) ( streamDef )? ( aliasDef )? ) | ( TOK_LPAREN selectExpr TOK_RPAREN ( aliasDef )? ) -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] selectExpr aliasDef ) );
    public final BEOqlParser.iteratorDef_return iteratorDef() throws RecognitionException {
        BEOqlParser.iteratorDef_return retval = new BEOqlParser.iteratorDef_return();
        retval.start = input.LT(1);
        int iteratorDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN23=null;
        Token TOK_RPAREN25=null;
        BEOqlParser.pathExpr_return pathExpr20 = null;

        BEOqlParser.streamDef_return streamDef21 = null;

        BEOqlParser.aliasDef_return aliasDef22 = null;

        BEOqlParser.selectExpr_return selectExpr24 = null;

        BEOqlParser.aliasDef_return aliasDef26 = null;


        CommonTree TOK_LPAREN23_tree=null;
        CommonTree TOK_RPAREN25_tree=null;
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_pathExpr=new RewriteRuleSubtreeStream(adaptor,"rule pathExpr");
        RewriteRuleSubtreeStream stream_selectExpr=new RewriteRuleSubtreeStream(adaptor,"rule selectExpr");
        RewriteRuleSubtreeStream stream_streamDef=new RewriteRuleSubtreeStream(adaptor,"rule streamDef");
        RewriteRuleSubtreeStream stream_aliasDef=new RewriteRuleSubtreeStream(adaptor,"rule aliasDef");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:663:19: ( ( pathExpr ( streamDef | aliasDef )? ) -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) ( streamDef )? ( aliasDef )? ) | ( TOK_LPAREN selectExpr TOK_RPAREN ( aliasDef )? ) -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] selectExpr aliasDef ) )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==TOK_SLASH||LA13_0==TOK_HASH||LA13_0==Identifier) ) {
                alt13=1;
            }
            else if ( (LA13_0==TOK_LPAREN) ) {
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
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:9: ( pathExpr ( streamDef | aliasDef )? )
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:9: ( pathExpr ( streamDef | aliasDef )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:11: pathExpr ( streamDef | aliasDef )?
                    {
                    pushFollow(FOLLOW_pathExpr_in_iteratorDef8554);
                    pathExpr20=pathExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathExpr.add(pathExpr20.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:20: ( streamDef | aliasDef )?
                    int alt11=3;
                    alt11 = dfa11.predict(input);
                    switch (alt11) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:21: streamDef
                            {
                            pushFollow(FOLLOW_streamDef_in_iteratorDef8557);
                            streamDef21=streamDef();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_streamDef.add(streamDef21.getTree());

                            }
                            break;
                        case 2 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:670:33: aliasDef
                            {
                            pushFollow(FOLLOW_aliasDef_in_iteratorDef8561);
                            aliasDef22=aliasDef();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_aliasDef.add(aliasDef22.getTree());

                            }
                            break;

                    }


                    }



                    // AST REWRITE
                    // elements: pathExpr, aliasDef, streamDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 671:13: -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) ( streamDef )? ( aliasDef )? )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:671:16: ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) ( streamDef )? ( aliasDef )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ITERATOR_DEF, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:671:70: ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PATH_EXPRESSION, (pathExpr20!=null?((Token)pathExpr20.start):null), (pathExpr20!=null?input.toString(pathExpr20.start,pathExpr20.stop):null)), root_2);

                        adaptor.addChild(root_2, stream_pathExpr.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:671:130: ( streamDef )?
                        if ( stream_streamDef.hasNext() ) {
                            adaptor.addChild(root_1, stream_streamDef.nextTree());

                        }
                        stream_streamDef.reset();
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:671:141: ( aliasDef )?
                        if ( stream_aliasDef.hasNext() ) {
                            adaptor.addChild(root_1, stream_aliasDef.nextTree());

                        }
                        stream_aliasDef.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:673:9: ( TOK_LPAREN selectExpr TOK_RPAREN ( aliasDef )? )
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:673:9: ( TOK_LPAREN selectExpr TOK_RPAREN ( aliasDef )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:673:11: TOK_LPAREN selectExpr TOK_RPAREN ( aliasDef )?
                    {
                    TOK_LPAREN23=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_iteratorDef8621); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN23);

                    pushFollow(FOLLOW_selectExpr_in_iteratorDef8623);
                    selectExpr24=selectExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selectExpr.add(selectExpr24.getTree());
                    TOK_RPAREN25=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_iteratorDef8625); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN25);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:673:44: ( aliasDef )?
                    int alt12=2;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: aliasDef
                            {
                            pushFollow(FOLLOW_aliasDef_in_iteratorDef8627);
                            aliasDef26=aliasDef();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_aliasDef.add(aliasDef26.getTree());

                            }
                            break;

                    }


                    }



                    // AST REWRITE
                    // elements: selectExpr, aliasDef
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 674:13: -> ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] selectExpr aliasDef )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:674:16: ^( ITERATOR_DEF[$iteratorDef.start,$iteratorDef.text] selectExpr aliasDef )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ITERATOR_DEF, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_selectExpr.nextTree());
                        adaptor.addChild(root_1, stream_aliasDef.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 5, iteratorDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "iteratorDef"

    public static class aliasDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aliasDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:678:7: aliasDef : ( TOK_AS )? labelIdentifier -> ^( ALIAS_CLAUSE[$aliasDef.start,$aliasDef.text] labelIdentifier ) ;
    public final BEOqlParser.aliasDef_return aliasDef() throws RecognitionException {
        BEOqlParser.aliasDef_return retval = new BEOqlParser.aliasDef_return();
        retval.start = input.LT(1);
        int aliasDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_AS27=null;
        BEOqlParser.labelIdentifier_return labelIdentifier28 = null;


        CommonTree TOK_AS27_tree=null;
        RewriteRuleTokenStream stream_TOK_AS=new RewriteRuleTokenStream(adaptor,"token TOK_AS");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:679:8: ( ( TOK_AS )? labelIdentifier -> ^( ALIAS_CLAUSE[$aliasDef.start,$aliasDef.text] labelIdentifier ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:680:9: ( TOK_AS )? labelIdentifier
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:680:9: ( TOK_AS )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==TOK_AS) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: TOK_AS
                    {
                    TOK_AS27=(Token)match(input,TOK_AS,FOLLOW_TOK_AS_in_aliasDef8696); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_AS.add(TOK_AS27);


                    }
                    break;

            }

            pushFollow(FOLLOW_labelIdentifier_in_aliasDef8699);
            labelIdentifier28=labelIdentifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_labelIdentifier.add(labelIdentifier28.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 681:16: -> ^( ALIAS_CLAUSE[$aliasDef.start,$aliasDef.text] labelIdentifier )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:681:19: ^( ALIAS_CLAUSE[$aliasDef.start,$aliasDef.text] labelIdentifier )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ALIAS_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_labelIdentifier.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 6, aliasDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "aliasDef"

    public static class streamDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "streamDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:684:7: streamDef : TOK_LCURLY streamDefItems TOK_RCURLY aliasDef -> ^( STREAM_DEF[$streamDef.start,$streamDef.text] aliasDef streamDefItems ) ;
    public final BEOqlParser.streamDef_return streamDef() throws RecognitionException {
        BEOqlParser.streamDef_return retval = new BEOqlParser.streamDef_return();
        retval.start = input.LT(1);
        int streamDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LCURLY29=null;
        Token TOK_RCURLY31=null;
        BEOqlParser.streamDefItems_return streamDefItems30 = null;

        BEOqlParser.aliasDef_return aliasDef32 = null;


        CommonTree TOK_LCURLY29_tree=null;
        CommonTree TOK_RCURLY31_tree=null;
        RewriteRuleTokenStream stream_TOK_LCURLY=new RewriteRuleTokenStream(adaptor,"token TOK_LCURLY");
        RewriteRuleTokenStream stream_TOK_RCURLY=new RewriteRuleTokenStream(adaptor,"token TOK_RCURLY");
        RewriteRuleSubtreeStream stream_streamDefItems=new RewriteRuleSubtreeStream(adaptor,"rule streamDefItems");
        RewriteRuleSubtreeStream stream_aliasDef=new RewriteRuleSubtreeStream(adaptor,"rule aliasDef");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:685:8: ( TOK_LCURLY streamDefItems TOK_RCURLY aliasDef -> ^( STREAM_DEF[$streamDef.start,$streamDef.text] aliasDef streamDefItems ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:686:9: TOK_LCURLY streamDefItems TOK_RCURLY aliasDef
            {
            TOK_LCURLY29=(Token)match(input,TOK_LCURLY,FOLLOW_TOK_LCURLY_in_streamDef8761); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LCURLY.add(TOK_LCURLY29);

            pushFollow(FOLLOW_streamDefItems_in_streamDef8763);
            streamDefItems30=streamDefItems();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_streamDefItems.add(streamDefItems30.getTree());
            TOK_RCURLY31=(Token)match(input,TOK_RCURLY,FOLLOW_TOK_RCURLY_in_streamDef8765); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RCURLY.add(TOK_RCURLY31);

            pushFollow(FOLLOW_aliasDef_in_streamDef8767);
            aliasDef32=aliasDef();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_aliasDef.add(aliasDef32.getTree());


            // AST REWRITE
            // elements: aliasDef, streamDefItems
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 687:16: -> ^( STREAM_DEF[$streamDef.start,$streamDef.text] aliasDef streamDefItems )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:687:19: ^( STREAM_DEF[$streamDef.start,$streamDef.text] aliasDef streamDefItems )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(STREAM_DEF, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_aliasDef.nextTree());
                adaptor.addChild(root_1, stream_streamDefItems.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 7, streamDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "streamDef"

    public static class streamDefItems_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "streamDefItems"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:690:7: streamDefItems : ( ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? ) | ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )? ) );
    public final BEOqlParser.streamDefItems_return streamDefItems() throws RecognitionException {
        BEOqlParser.streamDefItems_return retval = new BEOqlParser.streamDefItems_return();
        retval.start = input.LT(1);
        int streamDefItems_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_SEMIC34=null;
        Token TOK_SEMIC36=null;
        Token TOK_SEMIC39=null;
        Token TOK_SEMIC41=null;
        Token TOK_SEMIC44=null;
        Token TOK_SEMIC46=null;
        Token TOK_SEMIC49=null;
        Token TOK_SEMIC51=null;
        Token TOK_SEMIC54=null;
        Token TOK_SEMIC56=null;
        Token TOK_SEMIC59=null;
        Token TOK_SEMIC61=null;
        BEOqlParser.streamDefItemEmit_return streamDefItemEmit33 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy35 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept37 = null;

        BEOqlParser.streamDefItemEmit_return streamDefItemEmit38 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept40 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy42 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept43 = null;

        BEOqlParser.streamDefItemEmit_return streamDefItemEmit45 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy47 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept48 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy50 = null;

        BEOqlParser.streamDefItemEmit_return streamDefItemEmit52 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy53 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept55 = null;

        BEOqlParser.streamDefItemEmit_return streamDefItemEmit57 = null;

        BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy58 = null;

        BEOqlParser.streamDefItemEmit_return streamDefItemEmit60 = null;

        BEOqlParser.streamDefItemAccept_return streamDefItemAccept62 = null;


        CommonTree TOK_SEMIC34_tree=null;
        CommonTree TOK_SEMIC36_tree=null;
        CommonTree TOK_SEMIC39_tree=null;
        CommonTree TOK_SEMIC41_tree=null;
        CommonTree TOK_SEMIC44_tree=null;
        CommonTree TOK_SEMIC46_tree=null;
        CommonTree TOK_SEMIC49_tree=null;
        CommonTree TOK_SEMIC51_tree=null;
        CommonTree TOK_SEMIC54_tree=null;
        CommonTree TOK_SEMIC56_tree=null;
        CommonTree TOK_SEMIC59_tree=null;
        CommonTree TOK_SEMIC61_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:691:8: ( ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? ) | ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )? ) )
            int alt27=6;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:10: streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )?
                    {
                    pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems8833);
                    streamDefItemEmit33=streamDefItemEmit();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit33.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:28: ( TOK_SEMIC streamDefItemPolicy )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==TOK_SEMIC) ) {
                        int LA15_1 = input.LA(2);

                        if ( (LA15_1==TOK_POLICY) ) {
                            alt15=1;
                        }
                    }
                    switch (alt15) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:29: TOK_SEMIC streamDefItemPolicy
                            {
                            TOK_SEMIC34=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8836); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems8839);
                            streamDefItemPolicy35=streamDefItemPolicy();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy35.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:62: ( TOK_SEMIC streamDefItemAccept )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==TOK_SEMIC) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:63: TOK_SEMIC streamDefItemAccept
                            {
                            TOK_SEMIC36=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8844); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems8847);
                            streamDefItemAccept37=streamDefItemAccept();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept37.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:10: streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )?
                    {
                    pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems8871);
                    streamDefItemEmit38=streamDefItemEmit();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit38.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:28: ( TOK_SEMIC streamDefItemAccept )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==TOK_SEMIC) ) {
                        int LA17_1 = input.LA(2);

                        if ( (LA17_1==TOK_ACCEPT) ) {
                            alt17=1;
                        }
                    }
                    switch (alt17) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:29: TOK_SEMIC streamDefItemAccept
                            {
                            TOK_SEMIC39=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8874); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems8877);
                            streamDefItemAccept40=streamDefItemAccept();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept40.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:62: ( TOK_SEMIC streamDefItemPolicy )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==TOK_SEMIC) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:63: TOK_SEMIC streamDefItemPolicy
                            {
                            TOK_SEMIC41=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8882); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems8885);
                            streamDefItemPolicy42=streamDefItemPolicy();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy42.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:10: streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )?
                    {
                    pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems8913);
                    streamDefItemAccept43=streamDefItemAccept();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept43.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:30: ( TOK_SEMIC streamDefItemEmit )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==TOK_SEMIC) ) {
                        int LA19_1 = input.LA(2);

                        if ( (LA19_1==TOK_EMIT) ) {
                            alt19=1;
                        }
                    }
                    switch (alt19) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:31: TOK_SEMIC streamDefItemEmit
                            {
                            TOK_SEMIC44=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8916); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems8919);
                            streamDefItemEmit45=streamDefItemEmit();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit45.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:62: ( TOK_SEMIC streamDefItemPolicy )?
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==TOK_SEMIC) ) {
                        alt20=1;
                    }
                    switch (alt20) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:63: TOK_SEMIC streamDefItemPolicy
                            {
                            TOK_SEMIC46=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8924); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems8927);
                            streamDefItemPolicy47=streamDefItemPolicy();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy47.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:10: streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )?
                    {
                    pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems8955);
                    streamDefItemAccept48=streamDefItemAccept();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept48.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:30: ( TOK_SEMIC streamDefItemPolicy )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==TOK_SEMIC) ) {
                        int LA21_1 = input.LA(2);

                        if ( (LA21_1==TOK_POLICY) ) {
                            alt21=1;
                        }
                    }
                    switch (alt21) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:31: TOK_SEMIC streamDefItemPolicy
                            {
                            TOK_SEMIC49=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8958); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems8961);
                            streamDefItemPolicy50=streamDefItemPolicy();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy50.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:64: ( TOK_SEMIC streamDefItemEmit )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==TOK_SEMIC) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:65: TOK_SEMIC streamDefItemEmit
                            {
                            TOK_SEMIC51=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems8966); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems8969);
                            streamDefItemEmit52=streamDefItemEmit();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit52.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 5 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:10: streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )?
                    {
                    pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems8997);
                    streamDefItemPolicy53=streamDefItemPolicy();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy53.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:30: ( TOK_SEMIC streamDefItemAccept )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==TOK_SEMIC) ) {
                        int LA23_1 = input.LA(2);

                        if ( (LA23_1==TOK_ACCEPT) ) {
                            alt23=1;
                        }
                    }
                    switch (alt23) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:31: TOK_SEMIC streamDefItemAccept
                            {
                            TOK_SEMIC54=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems9000); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems9003);
                            streamDefItemAccept55=streamDefItemAccept();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept55.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:64: ( TOK_SEMIC streamDefItemEmit )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==TOK_SEMIC) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:65: TOK_SEMIC streamDefItemEmit
                            {
                            TOK_SEMIC56=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems9008); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems9011);
                            streamDefItemEmit57=streamDefItemEmit();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit57.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 6 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )? )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )? )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:10: streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )?
                    {
                    pushFollow(FOLLOW_streamDefItemPolicy_in_streamDefItems9039);
                    streamDefItemPolicy58=streamDefItemPolicy();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemPolicy58.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:30: ( TOK_SEMIC streamDefItemEmit )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==TOK_SEMIC) ) {
                        int LA25_1 = input.LA(2);

                        if ( (LA25_1==TOK_EMIT) ) {
                            alt25=1;
                        }
                    }
                    switch (alt25) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:31: TOK_SEMIC streamDefItemEmit
                            {
                            TOK_SEMIC59=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems9042); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemEmit_in_streamDefItems9045);
                            streamDefItemEmit60=streamDefItemEmit();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemEmit60.getTree());

                            }
                            break;

                    }

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:62: ( TOK_SEMIC streamDefItemAccept )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==TOK_SEMIC) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:702:63: TOK_SEMIC streamDefItemAccept
                            {
                            TOK_SEMIC61=(Token)match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_streamDefItems9050); if (state.failed) return retval;
                            pushFollow(FOLLOW_streamDefItemAccept_in_streamDefItems9053);
                            streamDefItemAccept62=streamDefItemAccept();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, streamDefItemAccept62.getTree());

                            }
                            break;

                    }


                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 8, streamDefItems_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "streamDefItems"

    public static class streamDefItemEmit_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "streamDefItemEmit"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:705:7: streamDefItemEmit : TOK_EMIT TOK_COLON ( TOK_NEW -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_DEAD -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] ) ) ;
    public final BEOqlParser.streamDefItemEmit_return streamDefItemEmit() throws RecognitionException {
        BEOqlParser.streamDefItemEmit_return retval = new BEOqlParser.streamDefItemEmit_return();
        retval.start = input.LT(1);
        int streamDefItemEmit_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_EMIT63=null;
        Token TOK_COLON64=null;
        Token TOK_NEW65=null;
        Token TOK_DEAD66=null;

        CommonTree TOK_EMIT63_tree=null;
        CommonTree TOK_COLON64_tree=null;
        CommonTree TOK_NEW65_tree=null;
        CommonTree TOK_DEAD66_tree=null;
        RewriteRuleTokenStream stream_TOK_NEW=new RewriteRuleTokenStream(adaptor,"token TOK_NEW");
        RewriteRuleTokenStream stream_TOK_DEAD=new RewriteRuleTokenStream(adaptor,"token TOK_DEAD");
        RewriteRuleTokenStream stream_TOK_EMIT=new RewriteRuleTokenStream(adaptor,"token TOK_EMIT");
        RewriteRuleTokenStream stream_TOK_COLON=new RewriteRuleTokenStream(adaptor,"token TOK_COLON");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:706:8: ( TOK_EMIT TOK_COLON ( TOK_NEW -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_DEAD -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:707:9: TOK_EMIT TOK_COLON ( TOK_NEW -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_DEAD -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] ) )
            {
            TOK_EMIT63=(Token)match(input,TOK_EMIT,FOLLOW_TOK_EMIT_in_streamDefItemEmit9093); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_EMIT.add(TOK_EMIT63);

            TOK_COLON64=(Token)match(input,TOK_COLON,FOLLOW_TOK_COLON_in_streamDefItemEmit9095); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_COLON.add(TOK_COLON64);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:708:9: ( TOK_NEW -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_DEAD -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==TOK_NEW) ) {
                alt28=1;
            }
            else if ( (LA28_0==TOK_DEAD) ) {
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
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:709:13: TOK_NEW
                    {
                    TOK_NEW65=(Token)match(input,TOK_NEW,FOLLOW_TOK_NEW_in_streamDefItemEmit9119); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_NEW.add(TOK_NEW65);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 709:21: -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:709:24: ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_NEW[$TOK_NEW,$TOK_NEW.text] )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EMIT_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, (CommonTree)adaptor.create(EMIT_NEW, TOK_NEW65, (TOK_NEW65!=null?TOK_NEW65.getText():null)));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:710:15: TOK_DEAD
                    {
                    TOK_DEAD66=(Token)match(input,TOK_DEAD,FOLLOW_TOK_DEAD_in_streamDefItemEmit9146); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_DEAD.add(TOK_DEAD66);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 710:24: -> ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:710:27: ^( EMIT_CLAUSE[$streamDefItemEmit.start,$streamDefItemEmit.text] EMIT_DEAD[$TOK_DEAD,$TOK_DEAD.text] )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EMIT_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, (CommonTree)adaptor.create(EMIT_DEAD, TOK_DEAD66, (TOK_DEAD66!=null?TOK_DEAD66.getText():null)));

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 9, streamDefItemEmit_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "streamDefItemEmit"

    public static class streamDefItemAccept_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "streamDefItemAccept"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:714:7: streamDefItemAccept : TOK_ACCEPT TOK_COLON ( TOK_NEW -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_ALL -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] ) ) ;
    public final BEOqlParser.streamDefItemAccept_return streamDefItemAccept() throws RecognitionException {
        BEOqlParser.streamDefItemAccept_return retval = new BEOqlParser.streamDefItemAccept_return();
        retval.start = input.LT(1);
        int streamDefItemAccept_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_ACCEPT67=null;
        Token TOK_COLON68=null;
        Token TOK_NEW69=null;
        Token TOK_ALL70=null;

        CommonTree TOK_ACCEPT67_tree=null;
        CommonTree TOK_COLON68_tree=null;
        CommonTree TOK_NEW69_tree=null;
        CommonTree TOK_ALL70_tree=null;
        RewriteRuleTokenStream stream_TOK_NEW=new RewriteRuleTokenStream(adaptor,"token TOK_NEW");
        RewriteRuleTokenStream stream_TOK_ALL=new RewriteRuleTokenStream(adaptor,"token TOK_ALL");
        RewriteRuleTokenStream stream_TOK_ACCEPT=new RewriteRuleTokenStream(adaptor,"token TOK_ACCEPT");
        RewriteRuleTokenStream stream_TOK_COLON=new RewriteRuleTokenStream(adaptor,"token TOK_COLON");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:715:8: ( TOK_ACCEPT TOK_COLON ( TOK_NEW -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_ALL -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:716:9: TOK_ACCEPT TOK_COLON ( TOK_NEW -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_ALL -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] ) )
            {
            TOK_ACCEPT67=(Token)match(input,TOK_ACCEPT,FOLLOW_TOK_ACCEPT_in_streamDefItemAccept9204); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_ACCEPT.add(TOK_ACCEPT67);

            TOK_COLON68=(Token)match(input,TOK_COLON,FOLLOW_TOK_COLON_in_streamDefItemAccept9206); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_COLON.add(TOK_COLON68);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:717:9: ( TOK_NEW -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] ) | TOK_ALL -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] ) )
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==TOK_NEW) ) {
                alt29=1;
            }
            else if ( (LA29_0==TOK_ALL) ) {
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
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:718:13: TOK_NEW
                    {
                    TOK_NEW69=(Token)match(input,TOK_NEW,FOLLOW_TOK_NEW_in_streamDefItemAccept9230); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_NEW.add(TOK_NEW69);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 718:21: -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:718:24: ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_NEW[$TOK_NEW,$TOK_NEW.text] )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ACCEPT_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, (CommonTree)adaptor.create(ACCEPT_NEW, TOK_NEW69, (TOK_NEW69!=null?TOK_NEW69.getText():null)));

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:719:15: TOK_ALL
                    {
                    TOK_ALL70=(Token)match(input,TOK_ALL,FOLLOW_TOK_ALL_in_streamDefItemAccept9257); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_ALL.add(TOK_ALL70);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 719:23: -> ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:719:26: ^( ACCEPT_CLAUSE[$streamDefItemAccept.start,$streamDefItemAccept.text] ACCEPT_ALL[$TOK_ALL,$TOK_ALL.text] )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ACCEPT_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, (CommonTree)adaptor.create(ACCEPT_ALL, TOK_ALL70, (TOK_ALL70!=null?TOK_ALL70.getText():null)));

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 10, streamDefItemAccept_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "streamDefItemAccept"

    public static class streamDefItemPolicy_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "streamDefItemPolicy"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:723:7: streamDefItemPolicy : TOK_POLICY TOK_COLON TOK_MAINTAIN windowDef ( whereClause )? ( policyBy )? -> ^( POLICY_CLAUSE[$streamDefItemPolicy.start,$streamDefItemPolicy.text] ( policyBy )? ( whereClause )? ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef ) ) ;
    public final BEOqlParser.streamDefItemPolicy_return streamDefItemPolicy() throws RecognitionException {
        BEOqlParser.streamDefItemPolicy_return retval = new BEOqlParser.streamDefItemPolicy_return();
        retval.start = input.LT(1);
        int streamDefItemPolicy_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_POLICY71=null;
        Token TOK_COLON72=null;
        Token TOK_MAINTAIN73=null;
        BEOqlParser.windowDef_return windowDef74 = null;

        BEOqlParser.whereClause_return whereClause75 = null;

        BEOqlParser.policyBy_return policyBy76 = null;


        CommonTree TOK_POLICY71_tree=null;
        CommonTree TOK_COLON72_tree=null;
        CommonTree TOK_MAINTAIN73_tree=null;
        RewriteRuleTokenStream stream_TOK_MAINTAIN=new RewriteRuleTokenStream(adaptor,"token TOK_MAINTAIN");
        RewriteRuleTokenStream stream_TOK_COLON=new RewriteRuleTokenStream(adaptor,"token TOK_COLON");
        RewriteRuleTokenStream stream_TOK_POLICY=new RewriteRuleTokenStream(adaptor,"token TOK_POLICY");
        RewriteRuleSubtreeStream stream_windowDef=new RewriteRuleSubtreeStream(adaptor,"rule windowDef");
        RewriteRuleSubtreeStream stream_whereClause=new RewriteRuleSubtreeStream(adaptor,"rule whereClause");
        RewriteRuleSubtreeStream stream_policyBy=new RewriteRuleSubtreeStream(adaptor,"rule policyBy");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:724:8: ( TOK_POLICY TOK_COLON TOK_MAINTAIN windowDef ( whereClause )? ( policyBy )? -> ^( POLICY_CLAUSE[$streamDefItemPolicy.start,$streamDefItemPolicy.text] ( policyBy )? ( whereClause )? ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:725:9: TOK_POLICY TOK_COLON TOK_MAINTAIN windowDef ( whereClause )? ( policyBy )?
            {
            TOK_POLICY71=(Token)match(input,TOK_POLICY,FOLLOW_TOK_POLICY_in_streamDefItemPolicy9315); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_POLICY.add(TOK_POLICY71);

            TOK_COLON72=(Token)match(input,TOK_COLON,FOLLOW_TOK_COLON_in_streamDefItemPolicy9317); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_COLON.add(TOK_COLON72);

            TOK_MAINTAIN73=(Token)match(input,TOK_MAINTAIN,FOLLOW_TOK_MAINTAIN_in_streamDefItemPolicy9319); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_MAINTAIN.add(TOK_MAINTAIN73);

            pushFollow(FOLLOW_windowDef_in_streamDefItemPolicy9329);
            windowDef74=windowDef();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_windowDef.add(windowDef74.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:727:9: ( whereClause )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==TOK_WHERE) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_streamDefItemPolicy9339);
                    whereClause75=whereClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_whereClause.add(whereClause75.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:728:9: ( policyBy )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==TOK_BY) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: policyBy
                    {
                    pushFollow(FOLLOW_policyBy_in_streamDefItemPolicy9350);
                    policyBy76=policyBy();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_policyBy.add(policyBy76.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: policyBy, windowDef, whereClause
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 729:9: -> ^( POLICY_CLAUSE[$streamDefItemPolicy.start,$streamDefItemPolicy.text] ( policyBy )? ( whereClause )? ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef ) )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:729:12: ^( POLICY_CLAUSE[$streamDefItemPolicy.start,$streamDefItemPolicy.text] ( policyBy )? ( whereClause )? ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(POLICY_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:729:83: ( policyBy )?
                if ( stream_policyBy.hasNext() ) {
                    adaptor.addChild(root_1, stream_policyBy.nextTree());

                }
                stream_policyBy.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:729:93: ( whereClause )?
                if ( stream_whereClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_whereClause.nextTree());

                }
                stream_whereClause.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:730:15: ^( WINDOW_DEF[$windowDef.start,$windowDef.text] windowDef )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(WINDOW_DEF, (windowDef74!=null?((Token)windowDef74.start):null), (windowDef74!=null?input.toString(windowDef74.start,windowDef74.stop):null)), root_2);

                adaptor.addChild(root_2, stream_windowDef.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 11, streamDefItemPolicy_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "streamDefItemPolicy"

    public static class policyBy_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "policyBy"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:734:7: policyBy : TOK_BY expr ( TOK_COMMA expr )* -> ^( POLICY_BY_CLAUSE[$policyBy.start,$policyBy.text] ( expr )+ ) ;
    public final BEOqlParser.policyBy_return policyBy() throws RecognitionException {
        BEOqlParser.policyBy_return retval = new BEOqlParser.policyBy_return();
        retval.start = input.LT(1);
        int policyBy_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_BY77=null;
        Token TOK_COMMA79=null;
        BEOqlParser.expr_return expr78 = null;

        BEOqlParser.expr_return expr80 = null;


        CommonTree TOK_BY77_tree=null;
        CommonTree TOK_COMMA79_tree=null;
        RewriteRuleTokenStream stream_TOK_BY=new RewriteRuleTokenStream(adaptor,"token TOK_BY");
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:735:8: ( TOK_BY expr ( TOK_COMMA expr )* -> ^( POLICY_BY_CLAUSE[$policyBy.start,$policyBy.text] ( expr )+ ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:736:9: TOK_BY expr ( TOK_COMMA expr )*
            {
            TOK_BY77=(Token)match(input,TOK_BY,FOLLOW_TOK_BY_in_policyBy9445); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_BY.add(TOK_BY77);

            pushFollow(FOLLOW_expr_in_policyBy9447);
            expr78=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr78.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:736:21: ( TOK_COMMA expr )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==TOK_COMMA) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:736:22: TOK_COMMA expr
            	    {
            	    TOK_COMMA79=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_policyBy9450); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA79);

            	    pushFollow(FOLLOW_expr_in_policyBy9452);
            	    expr80=expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_expr.add(expr80.getTree());

            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);



            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 737:9: -> ^( POLICY_BY_CLAUSE[$policyBy.start,$policyBy.text] ( expr )+ )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:737:12: ^( POLICY_BY_CLAUSE[$policyBy.start,$policyBy.text] ( expr )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(POLICY_BY_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_expr.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_expr.hasNext() ) {
                    adaptor.addChild(root_1, stream_expr.nextTree());

                }
                stream_expr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 12, policyBy_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "policyBy"

    public static class windowDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "windowDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:740:7: windowDef : ( timeWindowDef | tumblingWindowDef | slidingWindowDef );
    public final BEOqlParser.windowDef_return windowDef() throws RecognitionException {
        BEOqlParser.windowDef_return retval = new BEOqlParser.windowDef_return();
        retval.start = input.LT(1);
        int windowDef_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.timeWindowDef_return timeWindowDef81 = null;

        BEOqlParser.tumblingWindowDef_return tumblingWindowDef82 = null;

        BEOqlParser.slidingWindowDef_return slidingWindowDef83 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:741:8: ( timeWindowDef | tumblingWindowDef | slidingWindowDef )
            int alt33=3;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==TOK_LAST) ) {
                int LA33_1 = input.LA(2);

                if ( (LA33_1==DIGITS) ) {
                    int LA33_2 = input.LA(3);

                    if ( (synpred39_BEOql()) ) {
                        alt33=1;
                    }
                    else if ( (synpred40_BEOql()) ) {
                        alt33=2;
                    }
                    else if ( (true) ) {
                        alt33=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 33, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }
            switch (alt33) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:742:9: timeWindowDef
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_timeWindowDef_in_windowDef9511);
                    timeWindowDef81=timeWindowDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, timeWindowDef81.getTree());

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:744:9: tumblingWindowDef
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_tumblingWindowDef_in_windowDef9531);
                    tumblingWindowDef82=tumblingWindowDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tumblingWindowDef82.getTree());

                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:746:9: slidingWindowDef
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_slidingWindowDef_in_windowDef9551);
                    slidingWindowDef83=slidingWindowDef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, slidingWindowDef83.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 13, windowDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "windowDef"

    public static class timeWindowDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "timeWindowDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:749:7: timeWindowDef : ( TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? )=> TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? -> ^( TIME_WINDOW[$timeWindowDef.start,$timeWindowDef.text] DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? ) ;
    public final BEOqlParser.timeWindowDef_return timeWindowDef() throws RecognitionException {
        BEOqlParser.timeWindowDef_return retval = new BEOqlParser.timeWindowDef_return();
        retval.start = input.LT(1);
        int timeWindowDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LAST84=null;
        Token DIGITS85=null;
        BEOqlParser.timeUnit_return timeUnit86 = null;

        BEOqlParser.usingClause_return usingClause87 = null;

        BEOqlParser.windowPurgeExpr_return windowPurgeExpr88 = null;


        CommonTree TOK_LAST84_tree=null;
        CommonTree DIGITS85_tree=null;
        RewriteRuleTokenStream stream_TOK_LAST=new RewriteRuleTokenStream(adaptor,"token TOK_LAST");
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");
        RewriteRuleSubtreeStream stream_windowPurgeExpr=new RewriteRuleSubtreeStream(adaptor,"rule windowPurgeExpr");
        RewriteRuleSubtreeStream stream_timeUnit=new RewriteRuleSubtreeStream(adaptor,"rule timeUnit");
        RewriteRuleSubtreeStream stream_usingClause=new RewriteRuleSubtreeStream(adaptor,"rule usingClause");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:750:8: ( ( TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? )=> TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? -> ^( TIME_WINDOW[$timeWindowDef.start,$timeWindowDef.text] DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:751:9: ( TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? )=> TOK_LAST DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )?
            {
            TOK_LAST84=(Token)match(input,TOK_LAST,FOLLOW_TOK_LAST_in_timeWindowDef9654); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LAST.add(TOK_LAST84);

            DIGITS85=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_timeWindowDef9656); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DIGITS.add(DIGITS85);

            pushFollow(FOLLOW_timeUnit_in_timeWindowDef9658);
            timeUnit86=timeUnit();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_timeUnit.add(timeUnit86.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:757:9: ( usingClause )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==TOK_USING) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:757:10: usingClause
                    {
                    pushFollow(FOLLOW_usingClause_in_timeWindowDef9669);
                    usingClause87=usingClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_usingClause.add(usingClause87.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:758:9: ( windowPurgeExpr )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==TOK_PURGE) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:758:10: windowPurgeExpr
                    {
                    pushFollow(FOLLOW_windowPurgeExpr_in_timeWindowDef9682);
                    windowPurgeExpr88=windowPurgeExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_windowPurgeExpr.add(windowPurgeExpr88.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: DIGITS, windowPurgeExpr, timeUnit, usingClause
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 760:9: -> ^( TIME_WINDOW[$timeWindowDef.start,$timeWindowDef.text] DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:760:12: ^( TIME_WINDOW[$timeWindowDef.start,$timeWindowDef.text] DIGITS timeUnit ( usingClause )? ( windowPurgeExpr )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(TIME_WINDOW, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_DIGITS.nextNode());
                adaptor.addChild(root_1, stream_timeUnit.nextTree());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:761:15: ( usingClause )?
                if ( stream_usingClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_usingClause.nextTree());

                }
                stream_usingClause.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:762:15: ( windowPurgeExpr )?
                if ( stream_windowPurgeExpr.hasNext() ) {
                    adaptor.addChild(root_1, stream_windowPurgeExpr.nextTree());

                }
                stream_windowPurgeExpr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 14, timeWindowDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "timeWindowDef"

    public static class timeUnit_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "timeUnit"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:765:7: timeUnit : ( TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS );
    public final BEOqlParser.timeUnit_return timeUnit() throws RecognitionException {
        BEOqlParser.timeUnit_return retval = new BEOqlParser.timeUnit_return();
        retval.start = input.LT(1);
        int timeUnit_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set89=null;

        CommonTree set89_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:766:8: ( TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set89=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_TIME_MILLISECONDS && input.LA(1)<=TOK_TIME_DAYS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set89));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 15, timeUnit_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "timeUnit"

    public static class usingClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "usingClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:770:2: usingClause : TOK_USING expr -> ^( USING_CLAUSE[$usingClause.start,$usingClause.text] expr ) ;
    public final BEOqlParser.usingClause_return usingClause() throws RecognitionException {
        BEOqlParser.usingClause_return retval = new BEOqlParser.usingClause_return();
        retval.start = input.LT(1);
        int usingClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_USING90=null;
        BEOqlParser.expr_return expr91 = null;


        CommonTree TOK_USING90_tree=null;
        RewriteRuleTokenStream stream_TOK_USING=new RewriteRuleTokenStream(adaptor,"token TOK_USING");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:770:14: ( TOK_USING expr -> ^( USING_CLAUSE[$usingClause.start,$usingClause.text] expr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:771:3: TOK_USING expr
            {
            TOK_USING90=(Token)match(input,TOK_USING,FOLLOW_TOK_USING_in_usingClause9811); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_USING.add(TOK_USING90);

            pushFollow(FOLLOW_expr_in_usingClause9813);
            expr91=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr91.getTree());


            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 772:9: -> ^( USING_CLAUSE[$usingClause.start,$usingClause.text] expr )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:772:12: ^( USING_CLAUSE[$usingClause.start,$usingClause.text] expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(USING_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 16, usingClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "usingClause"

    public static class slidingWindowDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "slidingWindowDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:776:7: slidingWindowDef : ( TOK_LAST DIGITS TOK_SLIDING )=> TOK_LAST DIGITS TOK_SLIDING ( windowPurgeExpr )? -> ^( SLIDING_WINDOW[$slidingWindowDef.start,$slidingWindowDef.text] DIGITS ( windowPurgeExpr )? ) ;
    public final BEOqlParser.slidingWindowDef_return slidingWindowDef() throws RecognitionException {
        BEOqlParser.slidingWindowDef_return retval = new BEOqlParser.slidingWindowDef_return();
        retval.start = input.LT(1);
        int slidingWindowDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LAST92=null;
        Token DIGITS93=null;
        Token TOK_SLIDING94=null;
        BEOqlParser.windowPurgeExpr_return windowPurgeExpr95 = null;


        CommonTree TOK_LAST92_tree=null;
        CommonTree DIGITS93_tree=null;
        CommonTree TOK_SLIDING94_tree=null;
        RewriteRuleTokenStream stream_TOK_LAST=new RewriteRuleTokenStream(adaptor,"token TOK_LAST");
        RewriteRuleTokenStream stream_TOK_SLIDING=new RewriteRuleTokenStream(adaptor,"token TOK_SLIDING");
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");
        RewriteRuleSubtreeStream stream_windowPurgeExpr=new RewriteRuleSubtreeStream(adaptor,"rule windowPurgeExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:777:8: ( ( TOK_LAST DIGITS TOK_SLIDING )=> TOK_LAST DIGITS TOK_SLIDING ( windowPurgeExpr )? -> ^( SLIDING_WINDOW[$slidingWindowDef.start,$slidingWindowDef.text] DIGITS ( windowPurgeExpr )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:778:12: ( TOK_LAST DIGITS TOK_SLIDING )=> TOK_LAST DIGITS TOK_SLIDING ( windowPurgeExpr )?
            {
            TOK_LAST92=(Token)match(input,TOK_LAST,FOLLOW_TOK_LAST_in_slidingWindowDef9891); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LAST.add(TOK_LAST92);

            DIGITS93=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_slidingWindowDef9893); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DIGITS.add(DIGITS93);

            TOK_SLIDING94=(Token)match(input,TOK_SLIDING,FOLLOW_TOK_SLIDING_in_slidingWindowDef9895); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_SLIDING.add(TOK_SLIDING94);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:781:9: ( windowPurgeExpr )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==TOK_PURGE) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:781:10: windowPurgeExpr
                    {
                    pushFollow(FOLLOW_windowPurgeExpr_in_slidingWindowDef9906);
                    windowPurgeExpr95=windowPurgeExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_windowPurgeExpr.add(windowPurgeExpr95.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: DIGITS, windowPurgeExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 782:9: -> ^( SLIDING_WINDOW[$slidingWindowDef.start,$slidingWindowDef.text] DIGITS ( windowPurgeExpr )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:782:12: ^( SLIDING_WINDOW[$slidingWindowDef.start,$slidingWindowDef.text] DIGITS ( windowPurgeExpr )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SLIDING_WINDOW, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_DIGITS.nextNode());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:782:85: ( windowPurgeExpr )?
                if ( stream_windowPurgeExpr.hasNext() ) {
                    adaptor.addChild(root_1, stream_windowPurgeExpr.nextTree());

                }
                stream_windowPurgeExpr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 17, slidingWindowDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "slidingWindowDef"

    public static class tumblingWindowDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "tumblingWindowDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:785:7: tumblingWindowDef : ( TOK_LAST DIGITS TOK_TUMBLING )=> TOK_LAST DIGITS TOK_TUMBLING -> ^( TUMBLING_WINDOW[$tumblingWindowDef.start,$tumblingWindowDef.text] DIGITS ) ;
    public final BEOqlParser.tumblingWindowDef_return tumblingWindowDef() throws RecognitionException {
        BEOqlParser.tumblingWindowDef_return retval = new BEOqlParser.tumblingWindowDef_return();
        retval.start = input.LT(1);
        int tumblingWindowDef_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LAST96=null;
        Token DIGITS97=null;
        Token TOK_TUMBLING98=null;

        CommonTree TOK_LAST96_tree=null;
        CommonTree DIGITS97_tree=null;
        CommonTree TOK_TUMBLING98_tree=null;
        RewriteRuleTokenStream stream_TOK_LAST=new RewriteRuleTokenStream(adaptor,"token TOK_LAST");
        RewriteRuleTokenStream stream_TOK_TUMBLING=new RewriteRuleTokenStream(adaptor,"token TOK_TUMBLING");
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:786:8: ( ( TOK_LAST DIGITS TOK_TUMBLING )=> TOK_LAST DIGITS TOK_TUMBLING -> ^( TUMBLING_WINDOW[$tumblingWindowDef.start,$tumblingWindowDef.text] DIGITS ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:787:9: ( TOK_LAST DIGITS TOK_TUMBLING )=> TOK_LAST DIGITS TOK_TUMBLING
            {
            TOK_LAST96=(Token)match(input,TOK_LAST,FOLLOW_TOK_LAST_in_tumblingWindowDef9992); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LAST.add(TOK_LAST96);

            DIGITS97=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_tumblingWindowDef9994); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_DIGITS.add(DIGITS97);

            TOK_TUMBLING98=(Token)match(input,TOK_TUMBLING,FOLLOW_TOK_TUMBLING_in_tumblingWindowDef9996); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_TUMBLING.add(TOK_TUMBLING98);



            // AST REWRITE
            // elements: DIGITS
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 790:9: -> ^( TUMBLING_WINDOW[$tumblingWindowDef.start,$tumblingWindowDef.text] DIGITS )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:790:12: ^( TUMBLING_WINDOW[$tumblingWindowDef.start,$tumblingWindowDef.text] DIGITS )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(TUMBLING_WINDOW, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_DIGITS.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 18, tumblingWindowDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "tumblingWindowDef"

    public static class windowPurgeExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "windowPurgeExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:793:7: windowPurgeExpr : TOK_PURGE TOK_FIRST first= expr TOK_WHEN when= expr -> ^( PURGE_CLAUSE[$windowPurgeExpr.start,$windowPurgeExpr.text] ^( PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first) ^( PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when) ) ;
    public final BEOqlParser.windowPurgeExpr_return windowPurgeExpr() throws RecognitionException {
        BEOqlParser.windowPurgeExpr_return retval = new BEOqlParser.windowPurgeExpr_return();
        retval.start = input.LT(1);
        int windowPurgeExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_PURGE99=null;
        Token TOK_FIRST100=null;
        Token TOK_WHEN101=null;
        BEOqlParser.expr_return first = null;

        BEOqlParser.expr_return when = null;


        CommonTree TOK_PURGE99_tree=null;
        CommonTree TOK_FIRST100_tree=null;
        CommonTree TOK_WHEN101_tree=null;
        RewriteRuleTokenStream stream_TOK_WHEN=new RewriteRuleTokenStream(adaptor,"token TOK_WHEN");
        RewriteRuleTokenStream stream_TOK_FIRST=new RewriteRuleTokenStream(adaptor,"token TOK_FIRST");
        RewriteRuleTokenStream stream_TOK_PURGE=new RewriteRuleTokenStream(adaptor,"token TOK_PURGE");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:794:8: ( TOK_PURGE TOK_FIRST first= expr TOK_WHEN when= expr -> ^( PURGE_CLAUSE[$windowPurgeExpr.start,$windowPurgeExpr.text] ^( PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first) ^( PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:797:9: TOK_PURGE TOK_FIRST first= expr TOK_WHEN when= expr
            {
            TOK_PURGE99=(Token)match(input,TOK_PURGE,FOLLOW_TOK_PURGE_in_windowPurgeExpr10069); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_PURGE.add(TOK_PURGE99);

            TOK_FIRST100=(Token)match(input,TOK_FIRST,FOLLOW_TOK_FIRST_in_windowPurgeExpr10071); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_FIRST.add(TOK_FIRST100);

            pushFollow(FOLLOW_expr_in_windowPurgeExpr10075);
            first=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(first.getTree());
            TOK_WHEN101=(Token)match(input,TOK_WHEN,FOLLOW_TOK_WHEN_in_windowPurgeExpr10077); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_WHEN.add(TOK_WHEN101);

            pushFollow(FOLLOW_expr_in_windowPurgeExpr10081);
            when=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(when.getTree());


            // AST REWRITE
            // elements: first, when
            // token labels: 
            // rule labels: retval, when, first
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_when=new RewriteRuleSubtreeStream(adaptor,"rule when",when!=null?when.tree:null);
            RewriteRuleSubtreeStream stream_first=new RewriteRuleSubtreeStream(adaptor,"rule first",first!=null?first.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 798:9: -> ^( PURGE_CLAUSE[$windowPurgeExpr.start,$windowPurgeExpr.text] ^( PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first) ^( PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when) )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:798:12: ^( PURGE_CLAUSE[$windowPurgeExpr.start,$windowPurgeExpr.text] ^( PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first) ^( PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PURGE_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:799:15: ^( PURGE_FIRST_CLAUSE[$TOK_FIRST,$first.text] $first)
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PURGE_FIRST_CLAUSE, TOK_FIRST100, (first!=null?input.toString(first.start,first.stop):null)), root_2);

                adaptor.addChild(root_2, stream_first.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:800:15: ^( PURGE_WHEN_CLAUSE[$TOK_WHEN,$when.text] $when)
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PURGE_WHEN_CLAUSE, TOK_WHEN101, (when!=null?input.toString(when.start,when.stop):null)), root_2);

                adaptor.addChild(root_2, stream_when.nextTree());

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 19, windowPurgeExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "windowPurgeExpr"

    public static class pathExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pathExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:804:7: pathExpr : ( labelIdentifier | TOK_SLASH labelIdentifier ( TOK_SLASH labelIdentifier )* );
    public final BEOqlParser.pathExpr_return pathExpr() throws RecognitionException {
        BEOqlParser.pathExpr_return retval = new BEOqlParser.pathExpr_return();
        retval.start = input.LT(1);
        int pathExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_SLASH103=null;
        Token TOK_SLASH105=null;
        BEOqlParser.labelIdentifier_return labelIdentifier102 = null;

        BEOqlParser.labelIdentifier_return labelIdentifier104 = null;

        BEOqlParser.labelIdentifier_return labelIdentifier106 = null;


        CommonTree TOK_SLASH103_tree=null;
        CommonTree TOK_SLASH105_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:804:16: ( labelIdentifier | TOK_SLASH labelIdentifier ( TOK_SLASH labelIdentifier )* )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==TOK_HASH||LA38_0==Identifier) ) {
                alt38=1;
            }
            else if ( (LA38_0==TOK_SLASH) ) {
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
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:805:9: labelIdentifier
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_labelIdentifier_in_pathExpr10185);
                    labelIdentifier102=labelIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, labelIdentifier102.getTree());

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:806:10: TOK_SLASH labelIdentifier ( TOK_SLASH labelIdentifier )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_SLASH103=(Token)match(input,TOK_SLASH,FOLLOW_TOK_SLASH_in_pathExpr10197); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_SLASH103_tree = (CommonTree)adaptor.create(TOK_SLASH103);
                    adaptor.addChild(root_0, TOK_SLASH103_tree);
                    }
                    pushFollow(FOLLOW_labelIdentifier_in_pathExpr10199);
                    labelIdentifier104=labelIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, labelIdentifier104.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:807:8: ( TOK_SLASH labelIdentifier )*
                    loop37:
                    do {
                        int alt37=2;
                        alt37 = dfa37.predict(input);
                        switch (alt37) {
                    	case 1 :
                    	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:808:9: TOK_SLASH labelIdentifier
                    	    {
                    	    TOK_SLASH105=(Token)match(input,TOK_SLASH,FOLLOW_TOK_SLASH_in_pathExpr10218); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    TOK_SLASH105_tree = (CommonTree)adaptor.create(TOK_SLASH105);
                    	    adaptor.addChild(root_0, TOK_SLASH105_tree);
                    	    }
                    	    pushFollow(FOLLOW_labelIdentifier_in_pathExpr10220);
                    	    labelIdentifier106=labelIdentifier();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, labelIdentifier106.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 20, pathExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pathExpr"

    public static class whereClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "whereClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:815:7: whereClause : TOK_WHERE expr -> ^( WHERE_CLAUSE[$whereClause.start,$whereClause.text] expr ) ;
    public final BEOqlParser.whereClause_return whereClause() throws RecognitionException {
        BEOqlParser.whereClause_return retval = new BEOqlParser.whereClause_return();
        retval.start = input.LT(1);
        int whereClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_WHERE107=null;
        BEOqlParser.expr_return expr108 = null;


        CommonTree TOK_WHERE107_tree=null;
        RewriteRuleTokenStream stream_TOK_WHERE=new RewriteRuleTokenStream(adaptor,"token TOK_WHERE");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:815:19: ( TOK_WHERE expr -> ^( WHERE_CLAUSE[$whereClause.start,$whereClause.text] expr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:817:15: TOK_WHERE expr
            {
            TOK_WHERE107=(Token)match(input,TOK_WHERE,FOLLOW_TOK_WHERE_in_whereClause10285); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_WHERE.add(TOK_WHERE107);

            pushFollow(FOLLOW_expr_in_whereClause10287);
            expr108=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr108.getTree());


            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 818:15: -> ^( WHERE_CLAUSE[$whereClause.start,$whereClause.text] expr )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:818:18: ^( WHERE_CLAUSE[$whereClause.start,$whereClause.text] expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(WHERE_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 21, whereClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "whereClause"

    public static class projectionAttributes_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "projectionAttributes"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:822:7: projectionAttributes : ( projectionList -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] projectionList ) | scopeIdentifier -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) );
    public final BEOqlParser.projectionAttributes_return projectionAttributes() throws RecognitionException {
        BEOqlParser.projectionAttributes_return retval = new BEOqlParser.projectionAttributes_return();
        retval.start = input.LT(1);
        int projectionAttributes_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.projectionList_return projectionList109 = null;

        BEOqlParser.scopeIdentifier_return scopeIdentifier110 = null;


        RewriteRuleSubtreeStream stream_projectionList=new RewriteRuleSubtreeStream(adaptor,"rule projectionList");
        RewriteRuleSubtreeStream stream_scopeIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule scopeIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:822:28: ( projectionList -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] projectionList ) | scopeIdentifier -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) )
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:823:8: projectionList
                    {
                    pushFollow(FOLLOW_projectionList_in_projectionAttributes10343);
                    projectionList109=projectionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_projectionList.add(projectionList109.getTree());


                    // AST REWRITE
                    // elements: projectionList
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 823:23: -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] projectionList )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:823:26: ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] projectionList )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION_ATTRIBUTES, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_projectionList.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:824:12: scopeIdentifier
                    {
                    pushFollow(FOLLOW_scopeIdentifier_in_projectionAttributes10366);
                    scopeIdentifier110=scopeIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_scopeIdentifier.add(scopeIdentifier110.getTree());


                    // AST REWRITE
                    // elements: scopeIdentifier
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 824:28: -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:824:31: ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION_ATTRIBUTES, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:824:111: ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION, (scopeIdentifier110!=null?((Token)scopeIdentifier110.start):null), (scopeIdentifier110!=null?input.toString(scopeIdentifier110.start,scopeIdentifier110.stop):null)), root_2);

                        adaptor.addChild(root_2, stream_scopeIdentifier.nextTree());

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 22, projectionAttributes_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "projectionAttributes"

    public static class projectionList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "projectionList"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:829:7: projectionList : lc+= projection ( TOK_COMMA lc+= projection )* -> ( $lc)+ ;
    public final BEOqlParser.projectionList_return projectionList() throws RecognitionException {
        BEOqlParser.projectionList_return retval = new BEOqlParser.projectionList_return();
        retval.start = input.LT(1);
        int projectionList_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_COMMA111=null;
        List list_lc=null;
        RuleReturnScope lc = null;
        CommonTree TOK_COMMA111_tree=null;
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_projection=new RewriteRuleSubtreeStream(adaptor,"rule projection");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:829:22: (lc+= projection ( TOK_COMMA lc+= projection )* -> ( $lc)+ )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:830:9: lc+= projection ( TOK_COMMA lc+= projection )*
            {
            pushFollow(FOLLOW_projection_in_projectionList10417);
            lc=projection();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_projection.add(lc.getTree());
            if (list_lc==null) list_lc=new ArrayList();
            list_lc.add(lc.getTree());

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:831:15: ( TOK_COMMA lc+= projection )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==TOK_COMMA) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:832:19: TOK_COMMA lc+= projection
            	    {
            	    TOK_COMMA111=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_projectionList10453); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA111);

            	    pushFollow(FOLLOW_projection_in_projectionList10475);
            	    lc=projection();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_projection.add(lc.getTree());
            	    if (list_lc==null) list_lc=new ArrayList();
            	    list_lc.add(lc.getTree());


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);



            // AST REWRITE
            // elements: lc
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: lc
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_lc=new RewriteRuleSubtreeStream(adaptor,"token lc",list_lc);
            root_0 = (CommonTree)adaptor.nil();
            // 835:15: -> ( $lc)+
            {
                if ( !(stream_lc.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_lc.hasNext() ) {
                    adaptor.addChild(root_0, stream_lc.nextTree());

                }
                stream_lc.reset();

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 23, projectionList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "projectionList"

    public static class projection_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "projection"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:839:7: projection : (a= expr ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) ) ) ;
    public final BEOqlParser.projection_return projection() throws RecognitionException {
        BEOqlParser.projection_return retval = new BEOqlParser.projection_return();
        retval.start = input.LT(1);
        int projection_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_AS112=null;
        BEOqlParser.expr_return a = null;

        BEOqlParser.labelIdentifier_return li = null;


        CommonTree TOK_AS112_tree=null;
        RewriteRuleTokenStream stream_TOK_AS=new RewriteRuleTokenStream(adaptor,"token TOK_AS");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:839:18: ( (a= expr ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:840:15: (a= expr ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) ) )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:840:15: (a= expr ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:841:19: a= expr ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) )
            {
            pushFollow(FOLLOW_expr_in_projection10578);
            a=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(a.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:842:19: ( ( ( TOK_AS )? li= labelIdentifier )? -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:843:23: ( ( TOK_AS )? li= labelIdentifier )?
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:843:23: ( ( TOK_AS )? li= labelIdentifier )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==TOK_HASH||LA42_0==TOK_AS||LA42_0==Identifier) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:844:27: ( TOK_AS )? li= labelIdentifier
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:844:27: ( TOK_AS )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==TOK_AS) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:844:29: TOK_AS
                            {
                            TOK_AS112=(Token)match(input,TOK_AS,FOLLOW_TOK_AS_in_projection10652); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_AS.add(TOK_AS112);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_labelIdentifier_in_projection10685);
                    li=labelIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_labelIdentifier.add(li.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: a, labelIdentifier
            // token labels: 
            // rule labels: retval, a
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 847:23: -> ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:847:26: ^( PROJECTION[$expr.start,$projection.text] $a ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROJECTION, (a!=null?((Token)a.start):null), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_a.nextTree());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:847:72: ( ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? ) )?
                if ( stream_labelIdentifier.hasNext() ) {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:847:72: ^( ALIAS_CLAUSE[$li.start,$li.text] ( labelIdentifier )? )
                    {
                    CommonTree root_2 = (CommonTree)adaptor.nil();
                    root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ALIAS_CLAUSE, (li!=null?((Token)li.start):null), (li!=null?input.toString(li.start,li.stop):null)), root_2);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:847:108: ( labelIdentifier )?
                    if ( stream_labelIdentifier.hasNext() ) {
                        adaptor.addChild(root_2, stream_labelIdentifier.nextTree());

                    }
                    stream_labelIdentifier.reset();

                    adaptor.addChild(root_1, root_2);
                    }

                }
                stream_labelIdentifier.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }


            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 24, projection_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "projection"

    public static class extExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "extExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:858:8: extExpr : ( expr (lc= TOK_DOT a= expr -> ^( $extExpr $a) )* ) ;
    public final BEOqlParser.extExpr_return extExpr() throws RecognitionException {
        BEOqlParser.extExpr_return retval = new BEOqlParser.extExpr_return();
        retval.start = input.LT(1);
        int extExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        BEOqlParser.expr_return a = null;

        BEOqlParser.expr_return expr113 = null;


        CommonTree lc_tree=null;
        RewriteRuleTokenStream stream_TOK_DOT=new RewriteRuleTokenStream(adaptor,"token TOK_DOT");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:858:16: ( ( expr (lc= TOK_DOT a= expr -> ^( $extExpr $a) )* ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:859:12: ( expr (lc= TOK_DOT a= expr -> ^( $extExpr $a) )* )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:859:12: ( expr (lc= TOK_DOT a= expr -> ^( $extExpr $a) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:860:15: expr (lc= TOK_DOT a= expr -> ^( $extExpr $a) )*
            {
            pushFollow(FOLLOW_expr_in_extExpr10889);
            expr113=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr113.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:861:15: (lc= TOK_DOT a= expr -> ^( $extExpr $a) )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==TOK_DOT) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:862:18: lc= TOK_DOT a= expr
            	    {
            	    lc=(Token)match(input,TOK_DOT,FOLLOW_TOK_DOT_in_extExpr10926); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_DOT.add(lc);

            	    pushFollow(FOLLOW_expr_in_extExpr10930);
            	    a=expr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_expr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: a, extExpr
            	    // token labels: 
            	    // rule labels: retval, a
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 862:36: -> ^( $extExpr $a)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:862:39: ^( $extExpr $a)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_retval.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);


            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 25, extExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "extExpr"

    public static class groupClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "groupClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:867:7: groupClause : TOK_GROUP TOK_BY groupColumn ( TOK_COMMA groupColumn )* ( havingClause )? -> ^( GROUP_CLAUSE[$groupClause.start,$groupClause.text] ( groupColumn )+ ( havingClause )? ) ;
    public final BEOqlParser.groupClause_return groupClause() throws RecognitionException {
        BEOqlParser.groupClause_return retval = new BEOqlParser.groupClause_return();
        retval.start = input.LT(1);
        int groupClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_GROUP114=null;
        Token TOK_BY115=null;
        Token TOK_COMMA117=null;
        BEOqlParser.groupColumn_return groupColumn116 = null;

        BEOqlParser.groupColumn_return groupColumn118 = null;

        BEOqlParser.havingClause_return havingClause119 = null;


        CommonTree TOK_GROUP114_tree=null;
        CommonTree TOK_BY115_tree=null;
        CommonTree TOK_COMMA117_tree=null;
        RewriteRuleTokenStream stream_TOK_BY=new RewriteRuleTokenStream(adaptor,"token TOK_BY");
        RewriteRuleTokenStream stream_TOK_GROUP=new RewriteRuleTokenStream(adaptor,"token TOK_GROUP");
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_groupColumn=new RewriteRuleSubtreeStream(adaptor,"rule groupColumn");
        RewriteRuleSubtreeStream stream_havingClause=new RewriteRuleSubtreeStream(adaptor,"rule havingClause");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:867:19: ( TOK_GROUP TOK_BY groupColumn ( TOK_COMMA groupColumn )* ( havingClause )? -> ^( GROUP_CLAUSE[$groupClause.start,$groupClause.text] ( groupColumn )+ ( havingClause )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:869:15: TOK_GROUP TOK_BY groupColumn ( TOK_COMMA groupColumn )* ( havingClause )?
            {
            TOK_GROUP114=(Token)match(input,TOK_GROUP,FOLLOW_TOK_GROUP_in_groupClause11008); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_GROUP.add(TOK_GROUP114);

            TOK_BY115=(Token)match(input,TOK_BY,FOLLOW_TOK_BY_in_groupClause11010); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_BY.add(TOK_BY115);

            pushFollow(FOLLOW_groupColumn_in_groupClause11012);
            groupColumn116=groupColumn();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_groupColumn.add(groupColumn116.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:870:15: ( TOK_COMMA groupColumn )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==TOK_COMMA) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:871:19: TOK_COMMA groupColumn
            	    {
            	    TOK_COMMA117=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_groupClause11048); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA117);

            	    pushFollow(FOLLOW_groupColumn_in_groupClause11050);
            	    groupColumn118=groupColumn();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_groupColumn.add(groupColumn118.getTree());

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:873:15: ( havingClause )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==TOK_HAVING) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_groupClause11083);
                    havingClause119=havingClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_havingClause.add(havingClause119.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: havingClause, groupColumn
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 874:9: -> ^( GROUP_CLAUSE[$groupClause.start,$groupClause.text] ( groupColumn )+ ( havingClause )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:874:12: ^( GROUP_CLAUSE[$groupClause.start,$groupClause.text] ( groupColumn )+ ( havingClause )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(GROUP_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_groupColumn.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_groupColumn.hasNext() ) {
                    adaptor.addChild(root_1, stream_groupColumn.nextTree());

                }
                stream_groupColumn.reset();
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:874:78: ( havingClause )?
                if ( stream_havingClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_havingClause.nextTree());

                }
                stream_havingClause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 26, groupClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "groupClause"

    public static class havingClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "havingClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:877:7: havingClause : ( TOK_HAVING expr ) -> ^( HAVING_CLAUSE[$havingClause.start,$havingClause.text] expr ) ;
    public final BEOqlParser.havingClause_return havingClause() throws RecognitionException {
        BEOqlParser.havingClause_return retval = new BEOqlParser.havingClause_return();
        retval.start = input.LT(1);
        int havingClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_HAVING120=null;
        BEOqlParser.expr_return expr121 = null;


        CommonTree TOK_HAVING120_tree=null;
        RewriteRuleTokenStream stream_TOK_HAVING=new RewriteRuleTokenStream(adaptor,"token TOK_HAVING");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:877:19: ( ( TOK_HAVING expr ) -> ^( HAVING_CLAUSE[$havingClause.start,$havingClause.text] expr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:878:8: ( TOK_HAVING expr )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:878:8: ( TOK_HAVING expr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:879:9: TOK_HAVING expr
            {
            TOK_HAVING120=(Token)match(input,TOK_HAVING,FOLLOW_TOK_HAVING_in_havingClause11146); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_HAVING.add(TOK_HAVING120);

            pushFollow(FOLLOW_expr_in_havingClause11149);
            expr121=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr121.getTree());

            }



            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 881:8: -> ^( HAVING_CLAUSE[$havingClause.start,$havingClause.text] expr )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:881:11: ^( HAVING_CLAUSE[$havingClause.start,$havingClause.text] expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(HAVING_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 27, havingClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "havingClause"

    public static class groupColumn_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "groupColumn"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:884:7: groupColumn : fieldList -> fieldList ;
    public final BEOqlParser.groupColumn_return groupColumn() throws RecognitionException {
        BEOqlParser.groupColumn_return retval = new BEOqlParser.groupColumn_return();
        retval.start = input.LT(1);
        int groupColumn_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.fieldList_return fieldList122 = null;


        RewriteRuleSubtreeStream stream_fieldList=new RewriteRuleSubtreeStream(adaptor,"rule fieldList");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:884:19: ( fieldList -> fieldList )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:886:8: fieldList
            {
            pushFollow(FOLLOW_fieldList_in_groupColumn11203);
            fieldList122=fieldList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_fieldList.add(fieldList122.getTree());


            // AST REWRITE
            // elements: fieldList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 886:21: -> fieldList
            {
                adaptor.addChild(root_0, stream_fieldList.nextTree());

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 28, groupColumn_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "groupColumn"

    public static class orderClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orderClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:889:7: orderClause : TOK_ORDER TOK_BY sortCriterion ( TOK_COMMA sortCriterion )* -> ^( ORDER_CLAUSE[$orderClause.start,$orderClause.text] ( sortCriterion )+ ) ;
    public final BEOqlParser.orderClause_return orderClause() throws RecognitionException {
        BEOqlParser.orderClause_return retval = new BEOqlParser.orderClause_return();
        retval.start = input.LT(1);
        int orderClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_ORDER123=null;
        Token TOK_BY124=null;
        Token TOK_COMMA126=null;
        BEOqlParser.sortCriterion_return sortCriterion125 = null;

        BEOqlParser.sortCriterion_return sortCriterion127 = null;


        CommonTree TOK_ORDER123_tree=null;
        CommonTree TOK_BY124_tree=null;
        CommonTree TOK_COMMA126_tree=null;
        RewriteRuleTokenStream stream_TOK_BY=new RewriteRuleTokenStream(adaptor,"token TOK_BY");
        RewriteRuleTokenStream stream_TOK_ORDER=new RewriteRuleTokenStream(adaptor,"token TOK_ORDER");
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_sortCriterion=new RewriteRuleSubtreeStream(adaptor,"rule sortCriterion");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:889:19: ( TOK_ORDER TOK_BY sortCriterion ( TOK_COMMA sortCriterion )* -> ^( ORDER_CLAUSE[$orderClause.start,$orderClause.text] ( sortCriterion )+ ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:891:15: TOK_ORDER TOK_BY sortCriterion ( TOK_COMMA sortCriterion )*
            {
            TOK_ORDER123=(Token)match(input,TOK_ORDER,FOLLOW_TOK_ORDER_in_orderClause11250); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_ORDER.add(TOK_ORDER123);

            TOK_BY124=(Token)match(input,TOK_BY,FOLLOW_TOK_BY_in_orderClause11252); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_BY.add(TOK_BY124);

            pushFollow(FOLLOW_sortCriterion_in_orderClause11268);
            sortCriterion125=sortCriterion();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_sortCriterion.add(sortCriterion125.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:893:15: ( TOK_COMMA sortCriterion )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==TOK_COMMA) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:894:19: TOK_COMMA sortCriterion
            	    {
            	    TOK_COMMA126=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_orderClause11304); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA126);

            	    pushFollow(FOLLOW_sortCriterion_in_orderClause11306);
            	    sortCriterion127=sortCriterion();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_sortCriterion.add(sortCriterion127.getTree());

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);



            // AST REWRITE
            // elements: sortCriterion
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 896:15: -> ^( ORDER_CLAUSE[$orderClause.start,$orderClause.text] ( sortCriterion )+ )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:896:18: ^( ORDER_CLAUSE[$orderClause.start,$orderClause.text] ( sortCriterion )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ORDER_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_sortCriterion.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_sortCriterion.hasNext() ) {
                    adaptor.addChild(root_1, stream_sortCriterion.nextTree());

                }
                stream_sortCriterion.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 29, orderClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "orderClause"

    public static class sortCriterion_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sortCriterion"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:899:7: sortCriterion : ( expr ( sortDirection )? ( limitClause )? ) -> ^( SORT_CRITERION[$sortCriterion.start,$sortCriterion.text] expr ^( SORT_DIRECTION ( sortDirection )? ) ( limitClause )? ) ;
    public final BEOqlParser.sortCriterion_return sortCriterion() throws RecognitionException {
        BEOqlParser.sortCriterion_return retval = new BEOqlParser.sortCriterion_return();
        retval.start = input.LT(1);
        int sortCriterion_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.expr_return expr128 = null;

        BEOqlParser.sortDirection_return sortDirection129 = null;

        BEOqlParser.limitClause_return limitClause130 = null;


        RewriteRuleSubtreeStream stream_limitClause=new RewriteRuleSubtreeStream(adaptor,"rule limitClause");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_sortDirection=new RewriteRuleSubtreeStream(adaptor,"rule sortDirection");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:899:21: ( ( expr ( sortDirection )? ( limitClause )? ) -> ^( SORT_CRITERION[$sortCriterion.start,$sortCriterion.text] expr ^( SORT_DIRECTION ( sortDirection )? ) ( limitClause )? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:900:11: ( expr ( sortDirection )? ( limitClause )? )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:900:11: ( expr ( sortDirection )? ( limitClause )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:901:15: expr ( sortDirection )? ( limitClause )?
            {
            pushFollow(FOLLOW_expr_in_sortCriterion11399);
            expr128=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr128.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:902:15: ( sortDirection )?
            int alt47=2;
            alt47 = dfa47.predict(input);
            switch (alt47) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: sortDirection
                    {
                    pushFollow(FOLLOW_sortDirection_in_sortCriterion11415);
                    sortDirection129=sortDirection();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sortDirection.add(sortDirection129.getTree());

                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:903:15: ( limitClause )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==TOK_LCURLY) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:0:0: limitClause
                    {
                    pushFollow(FOLLOW_limitClause_in_sortCriterion11432);
                    limitClause130=limitClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limitClause.add(limitClause130.getTree());

                    }
                    break;

            }


            }



            // AST REWRITE
            // elements: expr, limitClause, sortDirection
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 904:13: -> ^( SORT_CRITERION[$sortCriterion.start,$sortCriterion.text] expr ^( SORT_DIRECTION ( sortDirection )? ) ( limitClause )? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:904:16: ^( SORT_CRITERION[$sortCriterion.start,$sortCriterion.text] expr ^( SORT_DIRECTION ( sortDirection )? ) ( limitClause )? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SORT_CRITERION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:904:80: ^( SORT_DIRECTION ( sortDirection )? )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SORT_DIRECTION, "SORT_DIRECTION"), root_2);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:904:97: ( sortDirection )?
                if ( stream_sortDirection.hasNext() ) {
                    adaptor.addChild(root_2, stream_sortDirection.nextTree());

                }
                stream_sortDirection.reset();

                adaptor.addChild(root_1, root_2);
                }
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:904:114: ( limitClause )?
                if ( stream_limitClause.hasNext() ) {
                    adaptor.addChild(root_1, stream_limitClause.nextTree());

                }
                stream_limitClause.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 30, sortCriterion_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "sortCriterion"

    public static class sortDirection_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sortDirection"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:907:7: sortDirection : ( TOK_ASC -> TOK_ASC | TOK_DESC -> TOK_DESC | -> TOK_ASC );
    public final BEOqlParser.sortDirection_return sortDirection() throws RecognitionException {
        BEOqlParser.sortDirection_return retval = new BEOqlParser.sortDirection_return();
        retval.start = input.LT(1);
        int sortDirection_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_ASC131=null;
        Token TOK_DESC132=null;

        CommonTree TOK_ASC131_tree=null;
        CommonTree TOK_DESC132_tree=null;
        RewriteRuleTokenStream stream_TOK_DESC=new RewriteRuleTokenStream(adaptor,"token TOK_DESC");
        RewriteRuleTokenStream stream_TOK_ASC=new RewriteRuleTokenStream(adaptor,"token TOK_ASC");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:907:21: ( TOK_ASC -> TOK_ASC | TOK_DESC -> TOK_DESC | -> TOK_ASC )
            int alt49=3;
            switch ( input.LA(1) ) {
            case TOK_ASC:
                {
                alt49=1;
                }
                break;
            case TOK_DESC:
                {
                alt49=2;
                }
                break;
            case EOF:
            case TOK_RPAREN:
            case TOK_COMMA:
            case TOK_SEMIC:
            case TOK_LCURLY:
                {
                alt49=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:908:19: TOK_ASC
                    {
                    TOK_ASC131=(Token)match(input,TOK_ASC,FOLLOW_TOK_ASC_in_sortDirection11509); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_ASC.add(TOK_ASC131);



                    // AST REWRITE
                    // elements: TOK_ASC
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 908:27: -> TOK_ASC
                    {
                        adaptor.addChild(root_0, stream_TOK_ASC.nextNode());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:909:19: TOK_DESC
                    {
                    TOK_DESC132=(Token)match(input,TOK_DESC,FOLLOW_TOK_DESC_in_sortDirection11533); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_DESC.add(TOK_DESC132);



                    // AST REWRITE
                    // elements: TOK_DESC
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 909:28: -> TOK_DESC
                    {
                        adaptor.addChild(root_0, stream_TOK_DESC.nextNode());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:910:33: 
                    {

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

                    root_0 = (CommonTree)adaptor.nil();
                    // 910:33: -> TOK_ASC
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(TOK_ASC, "TOK_ASC"));

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 31, sortDirection_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "sortDirection"

    public static class limitClause_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limitClause"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:913:7: limitClause : ( TOK_LCURLY TOK_LIMIT TOK_COLON limitDef TOK_RCURLY ) -> limitDef ;
    public final BEOqlParser.limitClause_return limitClause() throws RecognitionException {
        BEOqlParser.limitClause_return retval = new BEOqlParser.limitClause_return();
        retval.start = input.LT(1);
        int limitClause_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LCURLY133=null;
        Token TOK_LIMIT134=null;
        Token TOK_COLON135=null;
        Token TOK_RCURLY137=null;
        BEOqlParser.limitDef_return limitDef136 = null;


        CommonTree TOK_LCURLY133_tree=null;
        CommonTree TOK_LIMIT134_tree=null;
        CommonTree TOK_COLON135_tree=null;
        CommonTree TOK_RCURLY137_tree=null;
        RewriteRuleTokenStream stream_TOK_LCURLY=new RewriteRuleTokenStream(adaptor,"token TOK_LCURLY");
        RewriteRuleTokenStream stream_TOK_RCURLY=new RewriteRuleTokenStream(adaptor,"token TOK_RCURLY");
        RewriteRuleTokenStream stream_TOK_COLON=new RewriteRuleTokenStream(adaptor,"token TOK_COLON");
        RewriteRuleTokenStream stream_TOK_LIMIT=new RewriteRuleTokenStream(adaptor,"token TOK_LIMIT");
        RewriteRuleSubtreeStream stream_limitDef=new RewriteRuleSubtreeStream(adaptor,"rule limitDef");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:913:18: ( ( TOK_LCURLY TOK_LIMIT TOK_COLON limitDef TOK_RCURLY ) -> limitDef )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:914:15: ( TOK_LCURLY TOK_LIMIT TOK_COLON limitDef TOK_RCURLY )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:914:15: ( TOK_LCURLY TOK_LIMIT TOK_COLON limitDef TOK_RCURLY )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:914:17: TOK_LCURLY TOK_LIMIT TOK_COLON limitDef TOK_RCURLY
            {
            TOK_LCURLY133=(Token)match(input,TOK_LCURLY,FOLLOW_TOK_LCURLY_in_limitClause11597); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LCURLY.add(TOK_LCURLY133);

            TOK_LIMIT134=(Token)match(input,TOK_LIMIT,FOLLOW_TOK_LIMIT_in_limitClause11599); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LIMIT.add(TOK_LIMIT134);

            TOK_COLON135=(Token)match(input,TOK_COLON,FOLLOW_TOK_COLON_in_limitClause11601); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_COLON.add(TOK_COLON135);

            pushFollow(FOLLOW_limitDef_in_limitClause11603);
            limitDef136=limitDef();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_limitDef.add(limitDef136.getTree());
            TOK_RCURLY137=(Token)match(input,TOK_RCURLY,FOLLOW_TOK_RCURLY_in_limitClause11606); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RCURLY.add(TOK_RCURLY137);


            }



            // AST REWRITE
            // elements: limitDef
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 915:17: -> limitDef
            {
                adaptor.addChild(root_0, stream_limitDef.nextTree());

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 32, limitClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "limitClause"

    public static class limitDef_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limitDef"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:918:7: limitDef : first= limitFirst (offset= limitOffset )? -> ^( LIMIT_CLAUSE[$limitDef.start,$limitDef.text] $first ( $offset)? ) ;
    public final BEOqlParser.limitDef_return limitDef() throws RecognitionException {
        BEOqlParser.limitDef_return retval = new BEOqlParser.limitDef_return();
        retval.start = input.LT(1);
        int limitDef_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.limitFirst_return first = null;

        BEOqlParser.limitOffset_return offset = null;


        RewriteRuleSubtreeStream stream_limitFirst=new RewriteRuleSubtreeStream(adaptor,"rule limitFirst");
        RewriteRuleSubtreeStream stream_limitOffset=new RewriteRuleSubtreeStream(adaptor,"rule limitOffset");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:919:8: (first= limitFirst (offset= limitOffset )? -> ^( LIMIT_CLAUSE[$limitDef.start,$limitDef.text] $first ( $offset)? ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:920:9: first= limitFirst (offset= limitOffset )?
            {
            pushFollow(FOLLOW_limitFirst_in_limitDef11670);
            first=limitFirst();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_limitFirst.add(first.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:920:26: (offset= limitOffset )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==TOK_OFFSET) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:920:27: offset= limitOffset
                    {
                    pushFollow(FOLLOW_limitOffset_in_limitDef11675);
                    offset=limitOffset();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_limitOffset.add(offset.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: first, offset
            // token labels: 
            // rule labels: retval, offset, first
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_offset=new RewriteRuleSubtreeStream(adaptor,"rule offset",offset!=null?offset.tree:null);
            RewriteRuleSubtreeStream stream_first=new RewriteRuleSubtreeStream(adaptor,"rule first",first!=null?first.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 921:9: -> ^( LIMIT_CLAUSE[$limitDef.start,$limitDef.text] $first ( $offset)? )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:921:12: ^( LIMIT_CLAUSE[$limitDef.start,$limitDef.text] $first ( $offset)? )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(LIMIT_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_first.nextTree());
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:921:66: ( $offset)?
                if ( stream_offset.hasNext() ) {
                    adaptor.addChild(root_1, stream_offset.nextTree());

                }
                stream_offset.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 33, limitDef_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "limitDef"

    public static class limitFirst_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limitFirst"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:924:7: limitFirst : ( TOK_FIRST firstAsDigits= DIGITS -> ^( $firstAsDigits) | TOK_FIRST bindVar -> ^( bindVar ) );
    public final BEOqlParser.limitFirst_return limitFirst() throws RecognitionException {
        BEOqlParser.limitFirst_return retval = new BEOqlParser.limitFirst_return();
        retval.start = input.LT(1);
        int limitFirst_StartIndex = input.index();
        CommonTree root_0 = null;

        Token firstAsDigits=null;
        Token TOK_FIRST138=null;
        Token TOK_FIRST139=null;
        BEOqlParser.bindVar_return bindVar140 = null;


        CommonTree firstAsDigits_tree=null;
        CommonTree TOK_FIRST138_tree=null;
        CommonTree TOK_FIRST139_tree=null;
        RewriteRuleTokenStream stream_TOK_FIRST=new RewriteRuleTokenStream(adaptor,"token TOK_FIRST");
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");
        RewriteRuleSubtreeStream stream_bindVar=new RewriteRuleSubtreeStream(adaptor,"rule bindVar");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:924:18: ( TOK_FIRST firstAsDigits= DIGITS -> ^( $firstAsDigits) | TOK_FIRST bindVar -> ^( bindVar ) )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==TOK_FIRST) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==DIGITS) ) {
                    alt51=1;
                }
                else if ( (LA51_1==TOK_DOLLAR) ) {
                    alt51=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:925:9: TOK_FIRST firstAsDigits= DIGITS
                    {
                    TOK_FIRST138=(Token)match(input,TOK_FIRST,FOLLOW_TOK_FIRST_in_limitFirst11729); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_FIRST.add(TOK_FIRST138);

                    firstAsDigits=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_limitFirst11733); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_DIGITS.add(firstAsDigits);



                    // AST REWRITE
                    // elements: firstAsDigits
                    // token labels: firstAsDigits
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_firstAsDigits=new RewriteRuleTokenStream(adaptor,"token firstAsDigits",firstAsDigits);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 925:40: -> ^( $firstAsDigits)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:925:43: ^( $firstAsDigits)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_firstAsDigits.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:926:11: TOK_FIRST bindVar
                    {
                    TOK_FIRST139=(Token)match(input,TOK_FIRST,FOLLOW_TOK_FIRST_in_limitFirst11752); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_FIRST.add(TOK_FIRST139);

                    pushFollow(FOLLOW_bindVar_in_limitFirst11754);
                    bindVar140=bindVar();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bindVar.add(bindVar140.getTree());


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 926:29: -> ^( bindVar )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:926:32: ^( bindVar )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_bindVar.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 34, limitFirst_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "limitFirst"

    public static class limitOffset_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limitOffset"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:929:7: limitOffset : ( TOK_OFFSET offsetAsDigits= DIGITS -> ^( $offsetAsDigits) | TOK_OFFSET bindVar -> ^( bindVar ) );
    public final BEOqlParser.limitOffset_return limitOffset() throws RecognitionException {
        BEOqlParser.limitOffset_return retval = new BEOqlParser.limitOffset_return();
        retval.start = input.LT(1);
        int limitOffset_StartIndex = input.index();
        CommonTree root_0 = null;

        Token offsetAsDigits=null;
        Token TOK_OFFSET141=null;
        Token TOK_OFFSET142=null;
        BEOqlParser.bindVar_return bindVar143 = null;


        CommonTree offsetAsDigits_tree=null;
        CommonTree TOK_OFFSET141_tree=null;
        CommonTree TOK_OFFSET142_tree=null;
        RewriteRuleTokenStream stream_TOK_OFFSET=new RewriteRuleTokenStream(adaptor,"token TOK_OFFSET");
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");
        RewriteRuleSubtreeStream stream_bindVar=new RewriteRuleSubtreeStream(adaptor,"rule bindVar");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:929:19: ( TOK_OFFSET offsetAsDigits= DIGITS -> ^( $offsetAsDigits) | TOK_OFFSET bindVar -> ^( bindVar ) )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==TOK_OFFSET) ) {
                int LA52_1 = input.LA(2);

                if ( (LA52_1==DIGITS) ) {
                    alt52=1;
                }
                else if ( (LA52_1==TOK_DOLLAR) ) {
                    alt52=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 52, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:930:9: TOK_OFFSET offsetAsDigits= DIGITS
                    {
                    TOK_OFFSET141=(Token)match(input,TOK_OFFSET,FOLLOW_TOK_OFFSET_in_limitOffset11790); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_OFFSET.add(TOK_OFFSET141);

                    offsetAsDigits=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_limitOffset11794); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_DIGITS.add(offsetAsDigits);



                    // AST REWRITE
                    // elements: offsetAsDigits
                    // token labels: offsetAsDigits
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_offsetAsDigits=new RewriteRuleTokenStream(adaptor,"token offsetAsDigits",offsetAsDigits);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 930:42: -> ^( $offsetAsDigits)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:930:45: ^( $offsetAsDigits)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_offsetAsDigits.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:931:11: TOK_OFFSET bindVar
                    {
                    TOK_OFFSET142=(Token)match(input,TOK_OFFSET,FOLLOW_TOK_OFFSET_in_limitOffset11813); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_OFFSET.add(TOK_OFFSET142);

                    pushFollow(FOLLOW_bindVar_in_limitOffset11815);
                    bindVar143=bindVar();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bindVar.add(bindVar143.getTree());


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 931:30: -> ^( bindVar )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:931:33: ^( bindVar )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_bindVar.nextNode(), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 35, limitOffset_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "limitOffset"

    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:934:7: expr : castExpr -> castExpr ;
    public final BEOqlParser.expr_return expr() throws RecognitionException {
        BEOqlParser.expr_return retval = new BEOqlParser.expr_return();
        retval.start = input.LT(1);
        int expr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.castExpr_return castExpr144 = null;


        RewriteRuleSubtreeStream stream_castExpr=new RewriteRuleSubtreeStream(adaptor,"rule castExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:934:12: ( castExpr -> castExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:945:8: castExpr
            {
            pushFollow(FOLLOW_castExpr_in_expr11867);
            castExpr144=castExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_castExpr.add(castExpr144.getTree());


            // AST REWRITE
            // elements: castExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 945:17: -> castExpr
            {
                adaptor.addChild(root_0, stream_castExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 36, expr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class castExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "castExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:949:7: castExpr : ( ( TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr )=> TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr -> ^( CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type ( TOK_LBRACK )* ) | ( orExpr -> orExpr ) );
    public final BEOqlParser.castExpr_return castExpr() throws RecognitionException {
        BEOqlParser.castExpr_return retval = new BEOqlParser.castExpr_return();
        retval.start = input.LT(1);
        int castExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN145=null;
        Token TOK_LBRACK147=null;
        Token TOK_RBRACK148=null;
        Token TOK_RPAREN149=null;
        BEOqlParser.type_return type146 = null;

        BEOqlParser.expr_return expr150 = null;

        BEOqlParser.orExpr_return orExpr151 = null;


        CommonTree TOK_LPAREN145_tree=null;
        CommonTree TOK_LBRACK147_tree=null;
        CommonTree TOK_RBRACK148_tree=null;
        CommonTree TOK_RPAREN149_tree=null;
        RewriteRuleTokenStream stream_TOK_LBRACK=new RewriteRuleTokenStream(adaptor,"token TOK_LBRACK");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RBRACK=new RewriteRuleTokenStream(adaptor,"token TOK_RBRACK");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        RewriteRuleSubtreeStream stream_orExpr=new RewriteRuleSubtreeStream(adaptor,"rule orExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:949:16: ( ( TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr )=> TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr -> ^( CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type ( TOK_LBRACK )* ) | ( orExpr -> orExpr ) )
            int alt54=2;
            alt54 = dfa54.predict(input);
            switch (alt54) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:950:10: ( TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr )=> TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr
                    {
                    TOK_LPAREN145=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_castExpr11926); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN145);

                    pushFollow(FOLLOW_type_in_castExpr11928);
                    type146=type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_type.add(type146.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:951:21: ( TOK_LBRACK TOK_RBRACK )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==TOK_LBRACK) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:951:22: TOK_LBRACK TOK_RBRACK
                    	    {
                    	    TOK_LBRACK147=(Token)match(input,TOK_LBRACK,FOLLOW_TOK_LBRACK_in_castExpr11931); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_TOK_LBRACK.add(TOK_LBRACK147);

                    	    TOK_RBRACK148=(Token)match(input,TOK_RBRACK,FOLLOW_TOK_RBRACK_in_castExpr11933); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_TOK_RBRACK.add(TOK_RBRACK148);


                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);

                    TOK_RPAREN149=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_castExpr11937); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN149);

                    pushFollow(FOLLOW_expr_in_castExpr11939);
                    expr150=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr150.getTree());


                    // AST REWRITE
                    // elements: expr, TOK_LBRACK, type
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 952:2: -> ^( CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type ( TOK_LBRACK )* )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:952:5: ^( CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type ( TOK_LBRACK )* )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CAST_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_expr.nextTree());
                        adaptor.addChild(root_1, stream_type.nextTree());
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:952:65: ( TOK_LBRACK )*
                        while ( stream_TOK_LBRACK.hasNext() ) {
                            adaptor.addChild(root_1, stream_TOK_LBRACK.nextNode());

                        }
                        stream_TOK_LBRACK.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:953:4: ( orExpr -> orExpr )
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:953:4: ( orExpr -> orExpr )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:953:5: orExpr
                    {
                    pushFollow(FOLLOW_orExpr_in_castExpr11960);
                    orExpr151=orExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_orExpr.add(orExpr151.getTree());


                    // AST REWRITE
                    // elements: orExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 953:12: -> orExpr
                    {
                        adaptor.addChild(root_0, stream_orExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 37, castExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "castExpr"

    public static class orExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:956:7: orExpr : ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a) )* ;
    public final BEOqlParser.orExpr_return orExpr() throws RecognitionException {
        BEOqlParser.orExpr_return retval = new BEOqlParser.orExpr_return();
        retval.start = input.LT(1);
        int orExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        BEOqlParser.andExpr_return a = null;

        BEOqlParser.andExpr_return andExpr152 = null;


        CommonTree lc_tree=null;
        RewriteRuleTokenStream stream_TOK_OR=new RewriteRuleTokenStream(adaptor,"token TOK_OR");
        RewriteRuleSubtreeStream stream_andExpr=new RewriteRuleSubtreeStream(adaptor,"rule andExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:956:14: ( ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:957:15: ( andExpr -> andExpr ) (lc= TOK_OR a= andExpr -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:957:15: ( andExpr -> andExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:957:17: andExpr
            {
            pushFollow(FOLLOW_andExpr_in_orExpr12002);
            andExpr152=andExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_andExpr.add(andExpr152.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 957:25: -> andExpr
            {
                adaptor.addChild(root_0, stream_andExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:958:15: (lc= TOK_OR a= andExpr -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a) )*
            loop55:
            do {
                int alt55=2;
                alt55 = dfa55.predict(input);
                switch (alt55) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:959:19: lc= TOK_OR a= andExpr
            	    {
            	    lc=(Token)match(input,TOK_OR,FOLLOW_TOK_OR_in_orExpr12046); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_OR.add(lc);

            	    pushFollow(FOLLOW_andExpr_in_orExpr12068);
            	    a=andExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_andExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: a, orExpr
            	    // token labels: 
            	    // rule labels: retval, a
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 960:29: -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:960:32: ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(OR_EXPRESSION, lc, input.toString(retval.start,input.LT(-1))), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 38, orExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "orExpr"

    public static class andExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "andExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:964:7: andExpr : ( ( quantifierExpr -> quantifierExpr ) (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )* ) ;
    public final BEOqlParser.andExpr_return andExpr() throws RecognitionException {
        BEOqlParser.andExpr_return retval = new BEOqlParser.andExpr_return();
        retval.start = input.LT(1);
        int andExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        BEOqlParser.quantifierExpr_return a = null;

        BEOqlParser.quantifierExpr_return quantifierExpr153 = null;


        CommonTree lc_tree=null;
        RewriteRuleTokenStream stream_TOK_AND=new RewriteRuleTokenStream(adaptor,"token TOK_AND");
        RewriteRuleSubtreeStream stream_quantifierExpr=new RewriteRuleSubtreeStream(adaptor,"rule quantifierExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:964:15: ( ( ( quantifierExpr -> quantifierExpr ) (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )* ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:965:8: ( ( quantifierExpr -> quantifierExpr ) (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )* )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:965:8: ( ( quantifierExpr -> quantifierExpr ) (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:966:15: ( quantifierExpr -> quantifierExpr ) (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:966:15: ( quantifierExpr -> quantifierExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:966:17: quantifierExpr
            {
            pushFollow(FOLLOW_quantifierExpr_in_andExpr12149);
            quantifierExpr153=quantifierExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_quantifierExpr.add(quantifierExpr153.getTree());


            // AST REWRITE
            // elements: quantifierExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 966:32: -> quantifierExpr
            {
                adaptor.addChild(root_0, stream_quantifierExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:967:15: (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )*
            loop56:
            do {
                int alt56=2;
                alt56 = dfa56.predict(input);
                switch (alt56) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:968:19: lc= TOK_AND a= quantifierExpr
            	    {
            	    lc=(Token)match(input,TOK_AND,FOLLOW_TOK_AND_in_andExpr12192); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_AND.add(lc);

            	    pushFollow(FOLLOW_quantifierExpr_in_andExpr12214);
            	    a=quantifierExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_quantifierExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: a, andExpr
            	    // token labels: 
            	    // rule labels: retval, a
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 969:36: -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:969:39: ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(AND_EXPRESSION, lc, input.toString(retval.start,input.LT(-1))), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 39, andExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "andExpr"

    public static class quantifierExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "quantifierExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:974:7: quantifierExpr : ( ( ( equalityExpr -> equalityExpr ) ) ) ;
    public final BEOqlParser.quantifierExpr_return quantifierExpr() throws RecognitionException {
        BEOqlParser.quantifierExpr_return retval = new BEOqlParser.quantifierExpr_return();
        retval.start = input.LT(1);
        int quantifierExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.equalityExpr_return equalityExpr154 = null;


        RewriteRuleSubtreeStream stream_equalityExpr=new RewriteRuleSubtreeStream(adaptor,"rule equalityExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:974:22: ( ( ( ( equalityExpr -> equalityExpr ) ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:975:8: ( ( ( equalityExpr -> equalityExpr ) ) )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:975:8: ( ( ( equalityExpr -> equalityExpr ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:976:15: ( ( equalityExpr -> equalityExpr ) )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:976:15: ( ( equalityExpr -> equalityExpr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:977:15: ( equalityExpr -> equalityExpr )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:977:15: ( equalityExpr -> equalityExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:977:17: equalityExpr
            {
            pushFollow(FOLLOW_equalityExpr_in_quantifierExpr12323);
            equalityExpr154=equalityExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_equalityExpr.add(equalityExpr154.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 977:30: -> equalityExpr
            {
                adaptor.addChild(root_0, stream_equalityExpr.nextTree());

            }

            retval.tree = root_0;}
            }


            }


            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 40, quantifierExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "quantifierExpr"

    public static class equalityOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1000:7: equalityOp : ( TOK_EQ | TOK_NE );
    public final BEOqlParser.equalityOp_return equalityOp() throws RecognitionException {
        BEOqlParser.equalityOp_return retval = new BEOqlParser.equalityOp_return();
        retval.start = input.LT(1);
        int equalityOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set155=null;

        CommonTree set155_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 41) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1000:18: ( TOK_EQ | TOK_NE )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set155=(Token)input.LT(1);
            if ( input.LA(1)==TOK_EQ||input.LA(1)==TOK_NE ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set155));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 41, equalityOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equalityOp"

    public static class equalityExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "equalityExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1004:7: equalityExpr : ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | likeOp= TOK_LIKE pattern= unaryExpr -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern) )* ;
    public final BEOqlParser.equalityExpr_return equalityExpr() throws RecognitionException {
        BEOqlParser.equalityExpr_return retval = new BEOqlParser.equalityExpr_return();
        retval.start = input.LT(1);
        int equalityExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token likeOp=null;
        BEOqlParser.equalityOp_return e = null;

        BEOqlParser.relationalExpr_return r1 = null;

        BEOqlParser.unaryExpr_return pattern = null;

        BEOqlParser.relationalExpr_return relationalExpr156 = null;


        CommonTree likeOp_tree=null;
        RewriteRuleTokenStream stream_TOK_LIKE=new RewriteRuleTokenStream(adaptor,"token TOK_LIKE");
        RewriteRuleSubtreeStream stream_unaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpr");
        RewriteRuleSubtreeStream stream_relationalExpr=new RewriteRuleSubtreeStream(adaptor,"rule relationalExpr");
        RewriteRuleSubtreeStream stream_equalityOp=new RewriteRuleSubtreeStream(adaptor,"rule equalityOp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 42) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1004:20: ( ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | likeOp= TOK_LIKE pattern= unaryExpr -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1005:15: ( relationalExpr -> relationalExpr ) (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | likeOp= TOK_LIKE pattern= unaryExpr -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1005:15: ( relationalExpr -> relationalExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1005:17: relationalExpr
            {
            pushFollow(FOLLOW_relationalExpr_in_equalityExpr12456);
            relationalExpr156=relationalExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_relationalExpr.add(relationalExpr156.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 1005:32: -> relationalExpr
            {
                adaptor.addChild(root_0, stream_relationalExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1006:15: (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | likeOp= TOK_LIKE pattern= unaryExpr -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern) )*
            loop57:
            do {
                int alt57=3;
                alt57 = dfa57.predict(input);
                switch (alt57) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1007:19: e= equalityOp r1= relationalExpr
            	    {
            	    pushFollow(FOLLOW_equalityOp_in_equalityExpr12500);
            	    e=equalityOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_equalityOp.add(e.getTree());
            	    pushFollow(FOLLOW_relationalExpr_in_equalityExpr12542);
            	    r1=relationalExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalExpr.add(r1.getTree());


            	    // AST REWRITE
            	    // elements: r1, equalityExpr, e
            	    // token labels: 
            	    // rule labels: retval, e, r1
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_e=new RewriteRuleSubtreeStream(adaptor,"rule e",e!=null?e.tree:null);
            	    RewriteRuleSubtreeStream stream_r1=new RewriteRuleSubtreeStream(adaptor,"rule r1",r1!=null?r1.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1009:37: -> ^( $e $equalityExpr $r1)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1009:40: ^( $e $equalityExpr $r1)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_e.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_r1.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 2 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1012:21: likeOp= TOK_LIKE pattern= unaryExpr
            	    {
            	    likeOp=(Token)match(input,TOK_LIKE,FOLLOW_TOK_LIKE_in_equalityExpr12603); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_LIKE.add(likeOp);

            	    pushFollow(FOLLOW_unaryExpr_in_equalityExpr12609);
            	    pattern=unaryExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_unaryExpr.add(pattern.getTree());


            	    // AST REWRITE
            	    // elements: pattern, relationalExpr
            	    // token labels: 
            	    // rule labels: retval, pattern
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_pattern=new RewriteRuleSubtreeStream(adaptor,"rule pattern",pattern!=null?pattern.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1012:59: -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1012:62: ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(LIKE_CLAUSE, likeOp, input.toString(retval.start,input.LT(-1))), root_1);

            	        adaptor.addChild(root_1, stream_relationalExpr.nextTree());
            	        adaptor.addChild(root_1, stream_pattern.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 42, equalityExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "equalityExpr"

    public static class relationalOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1017:7: relationalOp : ( TOK_LT | TOK_GT | TOK_LE | TOK_GE );
    public final BEOqlParser.relationalOp_return relationalOp() throws RecognitionException {
        BEOqlParser.relationalOp_return retval = new BEOqlParser.relationalOp_return();
        retval.start = input.LT(1);
        int relationalOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set157=null;

        CommonTree set157_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 43) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1017:19: ( TOK_LT | TOK_GT | TOK_LE | TOK_GE )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set157=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_LE && input.LA(1)<=TOK_GE)||(input.LA(1)>=TOK_LT && input.LA(1)<=TOK_GT) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set157));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 43, relationalOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalOp"

    public static class relationalExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "relationalExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1024:7: relationalExpr : ( additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) ) )* ;
    public final BEOqlParser.relationalExpr_return relationalExpr() throws RecognitionException {
        BEOqlParser.relationalExpr_return retval = new BEOqlParser.relationalExpr_return();
        retval.start = input.LT(1);
        int relationalExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token a1=null;
        BEOqlParser.relationalOp_return r = null;

        BEOqlParser.additiveExpr_return a = null;

        BEOqlParser.additiveExpr_return a5 = null;

        BEOqlParser.additiveExpr_return a6 = null;

        BEOqlParser.additiveExpr_return additiveExpr158 = null;


        CommonTree lc_tree=null;
        CommonTree a1_tree=null;
        RewriteRuleTokenStream stream_TOK_AND=new RewriteRuleTokenStream(adaptor,"token TOK_AND");
        RewriteRuleTokenStream stream_TOK_BETWEEN=new RewriteRuleTokenStream(adaptor,"token TOK_BETWEEN");
        RewriteRuleSubtreeStream stream_relationalOp=new RewriteRuleSubtreeStream(adaptor,"rule relationalOp");
        RewriteRuleSubtreeStream stream_additiveExpr=new RewriteRuleSubtreeStream(adaptor,"rule additiveExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 44) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1024:22: ( ( additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) ) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1025:15: ( additiveExpr -> additiveExpr ) (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) ) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1025:15: ( additiveExpr -> additiveExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1025:17: additiveExpr
            {
            pushFollow(FOLLOW_additiveExpr_in_relationalExpr12758);
            additiveExpr158=additiveExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_additiveExpr.add(additiveExpr158.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 1025:30: -> additiveExpr
            {
                adaptor.addChild(root_0, stream_additiveExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1026:15: (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) ) )*
            loop58:
            do {
                int alt58=3;
                alt58 = dfa58.predict(input);
                switch (alt58) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1027:19: r= relationalOp a= additiveExpr
            	    {
            	    pushFollow(FOLLOW_relationalOp_in_relationalExpr12802);
            	    r=relationalOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_relationalOp.add(r.getTree());
            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr12806);
            	    a=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a.getTree());


            	    // AST REWRITE
            	    // elements: relationalExpr, r, a
            	    // token labels: 
            	    // rule labels: retval, r, a
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"rule r",r!=null?r.tree:null);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1027:49: -> ^( $r $relationalExpr $a)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1027:52: ^( $r $relationalExpr $a)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_r.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_a.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }
            	    break;
            	case 2 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1029:21: (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    {
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1029:21: (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr )
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1029:22: lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr
            	    {
            	    lc=(Token)match(input,TOK_BETWEEN,FOLLOW_TOK_BETWEEN_in_relationalExpr12856); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_BETWEEN.add(lc);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr12861);
            	    a5=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a5.getTree());
            	    a1=(Token)match(input,TOK_AND,FOLLOW_TOK_AND_in_relationalExpr12865); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_AND.add(a1);

            	    pushFollow(FOLLOW_additiveExpr_in_relationalExpr12869);
            	    a6=additiveExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveExpr.add(a6.getTree());

            	    }



            	    // AST REWRITE
            	    // elements: relationalExpr, a5, a6
            	    // token labels: 
            	    // rule labels: retval, a5, a6
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_a5=new RewriteRuleSubtreeStream(adaptor,"rule a5",a5!=null?a5.tree:null);
            	    RewriteRuleSubtreeStream stream_a6=new RewriteRuleSubtreeStream(adaptor,"rule a6",a6!=null?a6.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1030:23: -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) )
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1030:26: ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) )
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BETWEEN_CLAUSE, lc, input.toString(retval.start,input.LT(-1))), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1030:85: ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6)
            	        {
            	        CommonTree root_2 = (CommonTree)adaptor.nil();
            	        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(RANGE_EXPRESSION, a1, "RANGE_EXPRESSION"), root_2);

            	        adaptor.addChild(root_2, stream_a5.nextTree());
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
            	    break loop58;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 44, relationalExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "relationalExpr"

    public static class additiveOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1033:7: additiveOp : ( TOK_PLUS | TOK_MINUS | TOK_CONCAT );
    public final BEOqlParser.additiveOp_return additiveOp() throws RecognitionException {
        BEOqlParser.additiveOp_return retval = new BEOqlParser.additiveOp_return();
        retval.start = input.LT(1);
        int additiveOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set159=null;

        CommonTree set159_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 45) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1033:17: ( TOK_PLUS | TOK_MINUS | TOK_CONCAT )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set159=(Token)input.LT(1);
            if ( input.LA(1)==TOK_CONCAT||(input.LA(1)>=TOK_PLUS && input.LA(1)<=TOK_MINUS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set159));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 45, additiveOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additiveOp"

    public static class additiveExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "additiveExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1040:7: additiveExpr : ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )* ;
    public final BEOqlParser.additiveExpr_return additiveExpr() throws RecognitionException {
        BEOqlParser.additiveExpr_return retval = new BEOqlParser.additiveExpr_return();
        retval.start = input.LT(1);
        int additiveExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.additiveOp_return a = null;

        BEOqlParser.multiplicativeExpr_return m = null;

        BEOqlParser.multiplicativeExpr_return multiplicativeExpr160 = null;


        RewriteRuleSubtreeStream stream_multiplicativeExpr=new RewriteRuleSubtreeStream(adaptor,"rule multiplicativeExpr");
        RewriteRuleSubtreeStream stream_additiveOp=new RewriteRuleSubtreeStream(adaptor,"rule additiveOp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 46) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1040:20: ( ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1041:15: ( multiplicativeExpr -> multiplicativeExpr ) (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1041:15: ( multiplicativeExpr -> multiplicativeExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1041:17: multiplicativeExpr
            {
            pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr13058);
            multiplicativeExpr160=multiplicativeExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_multiplicativeExpr.add(multiplicativeExpr160.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 1041:36: -> multiplicativeExpr
            {
                adaptor.addChild(root_0, stream_multiplicativeExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1042:15: (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*
            loop59:
            do {
                int alt59=2;
                alt59 = dfa59.predict(input);
                switch (alt59) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1043:19: a= additiveOp m= multiplicativeExpr
            	    {
            	    pushFollow(FOLLOW_additiveOp_in_additiveExpr13102);
            	    a=additiveOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_additiveOp.add(a.getTree());
            	    pushFollow(FOLLOW_multiplicativeExpr_in_additiveExpr13106);
            	    m=multiplicativeExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_multiplicativeExpr.add(m.getTree());


            	    // AST REWRITE
            	    // elements: additiveExpr, a, m
            	    // token labels: 
            	    // rule labels: retval, a, m
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);
            	    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1043:53: -> ^( $a $additiveExpr $m)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1043:56: ^( $a $additiveExpr $m)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_a.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_m.nextTree());

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


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 46, additiveExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "additiveExpr"

    public static class multiplicativeOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1046:7: multiplicativeOp : ( TOK_STAR | TOK_SLASH | TOK_MOD );
    public final BEOqlParser.multiplicativeOp_return multiplicativeOp() throws RecognitionException {
        BEOqlParser.multiplicativeOp_return retval = new BEOqlParser.multiplicativeOp_return();
        retval.start = input.LT(1);
        int multiplicativeOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set161=null;

        CommonTree set161_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 47) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1046:24: ( TOK_STAR | TOK_SLASH | TOK_MOD )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set161=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_SLASH && input.LA(1)<=TOK_STAR)||input.LA(1)==TOK_MOD ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set161));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 47, multiplicativeOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicativeOp"

    public static class multiplicativeExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiplicativeExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1052:7: multiplicativeExpr : ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )* ;
    public final BEOqlParser.multiplicativeExpr_return multiplicativeExpr() throws RecognitionException {
        BEOqlParser.multiplicativeExpr_return retval = new BEOqlParser.multiplicativeExpr_return();
        retval.start = input.LT(1);
        int multiplicativeExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.multiplicativeOp_return m = null;

        BEOqlParser.inExpr_return i = null;

        BEOqlParser.inExpr_return inExpr162 = null;


        RewriteRuleSubtreeStream stream_multiplicativeOp=new RewriteRuleSubtreeStream(adaptor,"rule multiplicativeOp");
        RewriteRuleSubtreeStream stream_inExpr=new RewriteRuleSubtreeStream(adaptor,"rule inExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 48) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1052:26: ( ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1053:15: ( inExpr -> inExpr ) (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1053:15: ( inExpr -> inExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1053:17: inExpr
            {
            pushFollow(FOLLOW_inExpr_in_multiplicativeExpr13237);
            inExpr162=inExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_inExpr.add(inExpr162.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 1053:24: -> inExpr
            {
                adaptor.addChild(root_0, stream_inExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1054:15: (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*
            loop60:
            do {
                int alt60=2;
                alt60 = dfa60.predict(input);
                switch (alt60) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1055:19: m= multiplicativeOp i= inExpr
            	    {
            	    pushFollow(FOLLOW_multiplicativeOp_in_multiplicativeExpr13281);
            	    m=multiplicativeOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_multiplicativeOp.add(m.getTree());
            	    pushFollow(FOLLOW_inExpr_in_multiplicativeExpr13285);
            	    i=inExpr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_inExpr.add(i.getTree());


            	    // AST REWRITE
            	    // elements: i, multiplicativeExpr, m
            	    // token labels: 
            	    // rule labels: retval, m, i
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_m=new RewriteRuleSubtreeStream(adaptor,"rule m",m!=null?m.tree:null);
            	    RewriteRuleSubtreeStream stream_i=new RewriteRuleSubtreeStream(adaptor,"rule i",i!=null?i.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1055:47: -> ^( $m $multiplicativeExpr $i)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1055:50: ^( $m $multiplicativeExpr $i)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_m.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_i.nextTree());

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


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 48, multiplicativeExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiplicativeExpr"

    public static class inExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "inExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1059:7: inExpr : ( unaryExpr -> unaryExpr ) (lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) ) )? ;
    public final BEOqlParser.inExpr_return inExpr() throws RecognitionException {
        BEOqlParser.inExpr_return retval = new BEOqlParser.inExpr_return();
        retval.start = input.LT(1);
        int inExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token TOK_LPAREN164=null;
        Token TOK_RPAREN166=null;
        BEOqlParser.unaryExpr_return u = null;

        BEOqlParser.unaryExpr_return unaryExpr163 = null;

        BEOqlParser.fieldList_return fieldList165 = null;


        CommonTree lc_tree=null;
        CommonTree TOK_LPAREN164_tree=null;
        CommonTree TOK_RPAREN166_tree=null;
        RewriteRuleTokenStream stream_TOK_IN=new RewriteRuleTokenStream(adaptor,"token TOK_IN");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_fieldList=new RewriteRuleSubtreeStream(adaptor,"rule fieldList");
        RewriteRuleSubtreeStream stream_unaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 49) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1059:14: ( ( unaryExpr -> unaryExpr ) (lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) ) )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1060:15: ( unaryExpr -> unaryExpr ) (lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) ) )?
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1060:15: ( unaryExpr -> unaryExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1061:16: unaryExpr
            {
            pushFollow(FOLLOW_unaryExpr_in_inExpr13371);
            unaryExpr163=unaryExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_unaryExpr.add(unaryExpr163.getTree());


            // AST REWRITE
            // elements: unaryExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1061:26: -> unaryExpr
            {
                adaptor.addChild(root_0, stream_unaryExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1063:15: (lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) ) )?
            int alt62=2;
            alt62 = dfa62.predict(input);
            switch (alt62) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1064:19: lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) )
                    {
                    lc=(Token)match(input,TOK_IN,FOLLOW_TOK_IN_in_inExpr13429); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_IN.add(lc);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1065:19: (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) )
                    int alt61=2;
                    alt61 = dfa61.predict(input);
                    switch (alt61) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1066:20: u= unaryExpr
                            {
                            pushFollow(FOLLOW_unaryExpr_in_inExpr13472);
                            u=unaryExpr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_unaryExpr.add(u.getTree());


                            // AST REWRITE
                            // elements: u, inExpr
                            // token labels: 
                            // rule labels: retval, u
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_u=new RewriteRuleSubtreeStream(adaptor,"rule u",u!=null?u.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 1066:33: -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u)
                            {
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1066:36: ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u)
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(IN_CLAUSE, lc, input.toString(retval.start,input.LT(-1))), root_1);

                                adaptor.addChild(root_1, stream_retval.nextTree());
                                adaptor.addChild(root_1, stream_u.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1068:20: TOK_LPAREN fieldList TOK_RPAREN
                            {
                            TOK_LPAREN164=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_inExpr13528); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN164);

                            pushFollow(FOLLOW_fieldList_in_inExpr13530);
                            fieldList165=fieldList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_fieldList.add(fieldList165.getTree());
                            TOK_RPAREN166=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_inExpr13532); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN166);



                            // AST REWRITE
                            // elements: inExpr, fieldList
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 1068:52: -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList )
                            {
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1068:55: ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList )
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(IN_CLAUSE, lc, input.toString(retval.start,input.LT(-1))), root_1);

                                adaptor.addChild(root_1, stream_retval.nextTree());
                                adaptor.addChild(root_1, stream_fieldList.nextTree());

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 49, inExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "inExpr"

    public static class unaryOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1073:7: unaryOp : ( TOK_PLUS | TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text] | TOK_ABS | TOK_NOT );
    public final BEOqlParser.unaryOp_return unaryOp() throws RecognitionException {
        BEOqlParser.unaryOp_return retval = new BEOqlParser.unaryOp_return();
        retval.start = input.LT(1);
        int unaryOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_PLUS167=null;
        Token TOK_MINUS168=null;
        Token TOK_ABS169=null;
        Token TOK_NOT170=null;

        CommonTree TOK_PLUS167_tree=null;
        CommonTree TOK_MINUS168_tree=null;
        CommonTree TOK_ABS169_tree=null;
        CommonTree TOK_NOT170_tree=null;
        RewriteRuleTokenStream stream_TOK_MINUS=new RewriteRuleTokenStream(adaptor,"token TOK_MINUS");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1073:14: ( TOK_PLUS | TOK_MINUS -> UNARY_MINUS[$unaryOp.start,$unaryOp.text] | TOK_ABS | TOK_NOT )
            int alt63=4;
            switch ( input.LA(1) ) {
            case TOK_PLUS:
                {
                alt63=1;
                }
                break;
            case TOK_MINUS:
                {
                alt63=2;
                }
                break;
            case TOK_ABS:
                {
                alt63=3;
                }
                break;
            case TOK_NOT:
                {
                alt63=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1074:19: TOK_PLUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_PLUS167=(Token)match(input,TOK_PLUS,FOLLOW_TOK_PLUS_in_unaryOp13624); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_PLUS167_tree = (CommonTree)adaptor.create(TOK_PLUS167);
                    adaptor.addChild(root_0, TOK_PLUS167_tree);
                    }

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1075:19: TOK_MINUS
                    {
                    TOK_MINUS168=(Token)match(input,TOK_MINUS,FOLLOW_TOK_MINUS_in_unaryOp13644); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_MINUS.add(TOK_MINUS168);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1075:29: -> UNARY_MINUS[$unaryOp.start,$unaryOp.text]
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(UNARY_MINUS, ((Token)retval.start), input.toString(retval.start,input.LT(-1))));

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1076:19: TOK_ABS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_ABS169=(Token)match(input,TOK_ABS,FOLLOW_TOK_ABS_in_unaryOp13669); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_ABS169_tree = (CommonTree)adaptor.create(TOK_ABS169);
                    adaptor.addChild(root_0, TOK_ABS169_tree);
                    }

                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1077:19: TOK_NOT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_NOT170=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_unaryOp13689); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_NOT170_tree = (CommonTree)adaptor.create(TOK_NOT170);
                    adaptor.addChild(root_0, TOK_NOT170_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 50, unaryOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryOp"

    public static class xunaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "xunaryExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1080:7: xunaryExpr : ( TOK_PLUS | TOK_MINUS | TOK_ABS | TOK_NOT )* (p= postfixExpr ) ;
    public final BEOqlParser.xunaryExpr_return xunaryExpr() throws RecognitionException {
        BEOqlParser.xunaryExpr_return retval = new BEOqlParser.xunaryExpr_return();
        retval.start = input.LT(1);
        int xunaryExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_PLUS171=null;
        Token TOK_MINUS172=null;
        Token TOK_ABS173=null;
        Token TOK_NOT174=null;
        BEOqlParser.postfixExpr_return p = null;


        CommonTree TOK_PLUS171_tree=null;
        CommonTree TOK_MINUS172_tree=null;
        CommonTree TOK_ABS173_tree=null;
        CommonTree TOK_NOT174_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 51) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1080:17: ( ( TOK_PLUS | TOK_MINUS | TOK_ABS | TOK_NOT )* (p= postfixExpr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1082:15: ( TOK_PLUS | TOK_MINUS | TOK_ABS | TOK_NOT )* (p= postfixExpr )
            {
            root_0 = (CommonTree)adaptor.nil();

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1082:15: ( TOK_PLUS | TOK_MINUS | TOK_ABS | TOK_NOT )*
            loop64:
            do {
                int alt64=5;
                alt64 = dfa64.predict(input);
                switch (alt64) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1085:19: TOK_PLUS
            	    {
            	    TOK_PLUS171=(Token)match(input,TOK_PLUS,FOLLOW_TOK_PLUS_in_xunaryExpr13764); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    TOK_PLUS171_tree = (CommonTree)adaptor.create(TOK_PLUS171);
            	    root_0 = (CommonTree)adaptor.becomeRoot(TOK_PLUS171_tree, root_0);
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1086:23: TOK_MINUS
            	    {
            	    TOK_MINUS172=(Token)match(input,TOK_MINUS,FOLLOW_TOK_MINUS_in_xunaryExpr13789); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    TOK_MINUS172_tree = (CommonTree)adaptor.create(TOK_MINUS172);
            	    root_0 = (CommonTree)adaptor.becomeRoot(TOK_MINUS172_tree, root_0);
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1087:23: TOK_ABS
            	    {
            	    TOK_ABS173=(Token)match(input,TOK_ABS,FOLLOW_TOK_ABS_in_xunaryExpr13814); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    TOK_ABS173_tree = (CommonTree)adaptor.create(TOK_ABS173);
            	    root_0 = (CommonTree)adaptor.becomeRoot(TOK_ABS173_tree, root_0);
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1088:23: TOK_NOT
            	    {
            	    TOK_NOT174=(Token)match(input,TOK_NOT,FOLLOW_TOK_NOT_in_xunaryExpr13839); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    TOK_NOT174_tree = (CommonTree)adaptor.create(TOK_NOT174);
            	    root_0 = (CommonTree)adaptor.becomeRoot(TOK_NOT174_tree, root_0);
            	    }

            	    }
            	    break;

            	default :
            	    break loop64;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1090:18: (p= postfixExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1090:20: p= postfixExpr
            {
            pushFollow(FOLLOW_postfixExpr_in_xunaryExpr13864);
            p=postfixExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 51, xunaryExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "xunaryExpr"

    public static class unaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unaryExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1099:7: unaryExpr : ( unaryOp x= unaryExpr -> ^( unaryOp $x) | postfixExpr -> postfixExpr );
    public final BEOqlParser.unaryExpr_return unaryExpr() throws RecognitionException {
        BEOqlParser.unaryExpr_return retval = new BEOqlParser.unaryExpr_return();
        retval.start = input.LT(1);
        int unaryExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.unaryExpr_return x = null;

        BEOqlParser.unaryOp_return unaryOp175 = null;

        BEOqlParser.postfixExpr_return postfixExpr176 = null;


        RewriteRuleSubtreeStream stream_unaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule unaryExpr");
        RewriteRuleSubtreeStream stream_postfixExpr=new RewriteRuleSubtreeStream(adaptor,"rule postfixExpr");
        RewriteRuleSubtreeStream stream_unaryOp=new RewriteRuleSubtreeStream(adaptor,"rule unaryOp");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1099:17: ( unaryOp x= unaryExpr -> ^( unaryOp $x) | postfixExpr -> postfixExpr )
            int alt65=2;
            alt65 = dfa65.predict(input);
            switch (alt65) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1100:12: unaryOp x= unaryExpr
                    {
                    pushFollow(FOLLOW_unaryOp_in_unaryExpr13948);
                    unaryOp175=unaryOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryOp.add(unaryOp175.getTree());
                    pushFollow(FOLLOW_unaryExpr_in_unaryExpr13952);
                    x=unaryExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_unaryExpr.add(x.getTree());


                    // AST REWRITE
                    // elements: unaryOp, x
                    // token labels: 
                    // rule labels: retval, x
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_x=new RewriteRuleSubtreeStream(adaptor,"rule x",x!=null?x.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1100:32: -> ^( unaryOp $x)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1100:35: ^( unaryOp $x)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_unaryOp.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_x.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1101:14: postfixExpr
                    {
                    pushFollow(FOLLOW_postfixExpr_in_unaryExpr13976);
                    postfixExpr176=postfixExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_postfixExpr.add(postfixExpr176.getTree());


                    // AST REWRITE
                    // elements: postfixExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1101:26: -> postfixExpr
                    {
                        adaptor.addChild(root_0, stream_postfixExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 52, unaryExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unaryExpr"

    public static class postfixOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfixOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1104:7: postfixOp : ( TOK_DOT | TOK_AT );
    public final BEOqlParser.postfixOp_return postfixOp() throws RecognitionException {
        BEOqlParser.postfixOp_return retval = new BEOqlParser.postfixOp_return();
        retval.start = input.LT(1);
        int postfixOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set177=null;

        CommonTree set177_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 53) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1104:16: ( TOK_DOT | TOK_AT )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set177=(Token)input.LT(1);
            if ( input.LA(1)==TOK_DOT||input.LA(1)==TOK_AT ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set177));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 53, postfixOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "postfixOp"

    public static class postfixExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "postfixExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1110:7: postfixExpr : ( primaryExpr -> primaryExpr ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? ( ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? )* (p= postfixOp si= scopeIdentifier -> ^( $p $postfixExpr $si) )? ;
    public final BEOqlParser.postfixExpr_return postfixExpr() throws RecognitionException {
        BEOqlParser.postfixExpr_return retval = new BEOqlParser.postfixExpr_return();
        retval.start = input.LT(1);
        int postfixExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.arrayIndexExpr_return a = null;

        BEOqlParser.postfixOp_return p = null;

        BEOqlParser.labelIdentifier_return li = null;

        BEOqlParser.scopeIdentifier_return si = null;

        BEOqlParser.primaryExpr_return primaryExpr178 = null;


        RewriteRuleSubtreeStream stream_postfixOp=new RewriteRuleSubtreeStream(adaptor,"rule postfixOp");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        RewriteRuleSubtreeStream stream_arrayIndexExpr=new RewriteRuleSubtreeStream(adaptor,"rule arrayIndexExpr");
        RewriteRuleSubtreeStream stream_primaryExpr=new RewriteRuleSubtreeStream(adaptor,"rule primaryExpr");
        RewriteRuleSubtreeStream stream_scopeIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule scopeIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1110:18: ( ( primaryExpr -> primaryExpr ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? ( ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? )* (p= postfixOp si= scopeIdentifier -> ^( $p $postfixExpr $si) )? )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1111:8: ( primaryExpr -> primaryExpr ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? ( ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? )* (p= postfixOp si= scopeIdentifier -> ^( $p $postfixExpr $si) )?
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1111:8: ( primaryExpr -> primaryExpr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1112:12: primaryExpr
            {
            pushFollow(FOLLOW_primaryExpr_in_postfixExpr14061);
            primaryExpr178=primaryExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_primaryExpr.add(primaryExpr178.getTree());


            // AST REWRITE
            // elements: primaryExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1112:24: -> primaryExpr
            {
                adaptor.addChild(root_0, stream_primaryExpr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1114:9: (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )?
            int alt66=2;
            alt66 = dfa66.predict(input);
            switch (alt66) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1114:11: a= arrayIndexExpr
                    {
                    pushFollow(FOLLOW_arrayIndexExpr_in_postfixExpr14088);
                    a=arrayIndexExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_arrayIndexExpr.add(a.getTree());


                    // AST REWRITE
                    // elements: postfixExpr, a
                    // token labels: 
                    // rule labels: retval, a
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1114:28: -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1114:31: ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARRAY_INDEX, (a!=null?((Token)a.start):null), (a!=null?input.toString(a.start,a.stop):null)), root_1);

                        adaptor.addChild(root_1, stream_retval.nextTree());
                        adaptor.addChild(root_1, stream_a.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1115:9: ( ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? )*
            loop68:
            do {
                int alt68=2;
                alt68 = dfa68.predict(input);
                switch (alt68) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1116:13: ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )?
            	    {
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1116:13: ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) )
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:17: (p= postfixOp li= labelIdentifier )
            	    {
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:17: (p= postfixOp li= labelIdentifier )
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:18: p= postfixOp li= labelIdentifier
            	    {
            	    pushFollow(FOLLOW_postfixOp_in_postfixExpr14150);
            	    p=postfixOp();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_postfixOp.add(p.getTree());
            	    pushFollow(FOLLOW_labelIdentifier_in_postfixExpr14155);
            	    li=labelIdentifier();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_labelIdentifier.add(li.getTree());

            	    }



            	    // AST REWRITE
            	    // elements: postfixExpr, li, p
            	    // token labels: 
            	    // rule labels: li, retval, p
            	    // token list labels: 
            	    // rule list labels: 
            	    // wildcard labels: 
            	    if ( state.backtracking==0 ) {
            	    retval.tree = root_0;
            	    RewriteRuleSubtreeStream stream_li=new RewriteRuleSubtreeStream(adaptor,"rule li",li!=null?li.tree:null);
            	    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	    RewriteRuleSubtreeStream stream_p=new RewriteRuleSubtreeStream(adaptor,"rule p",p!=null?p.tree:null);

            	    root_0 = (CommonTree)adaptor.nil();
            	    // 1118:22: -> ^( $p $postfixExpr $li)
            	    {
            	        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1118:25: ^( $p $postfixExpr $li)
            	        {
            	        CommonTree root_1 = (CommonTree)adaptor.nil();
            	        root_1 = (CommonTree)adaptor.becomeRoot(stream_p.nextNode(), root_1);

            	        adaptor.addChild(root_1, stream_retval.nextTree());
            	        adaptor.addChild(root_1, stream_li.nextTree());

            	        adaptor.addChild(root_0, root_1);
            	        }

            	    }

            	    retval.tree = root_0;}
            	    }

            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1125:13: (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )?
            	    int alt67=2;
            	    alt67 = dfa67.predict(input);
            	    switch (alt67) {
            	        case 1 :
            	            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1125:15: a= arrayIndexExpr
            	            {
            	            pushFollow(FOLLOW_arrayIndexExpr_in_postfixExpr14334);
            	            a=arrayIndexExpr();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_arrayIndexExpr.add(a.getTree());


            	            // AST REWRITE
            	            // elements: postfixExpr, a
            	            // token labels: 
            	            // rule labels: retval, a
            	            // token list labels: 
            	            // rule list labels: 
            	            // wildcard labels: 
            	            if ( state.backtracking==0 ) {
            	            retval.tree = root_0;
            	            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            	            RewriteRuleSubtreeStream stream_a=new RewriteRuleSubtreeStream(adaptor,"rule a",a!=null?a.tree:null);

            	            root_0 = (CommonTree)adaptor.nil();
            	            // 1125:32: -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a)
            	            {
            	                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1125:35: ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a)
            	                {
            	                CommonTree root_1 = (CommonTree)adaptor.nil();
            	                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARRAY_INDEX, (a!=null?((Token)a.start):null), (a!=null?input.toString(a.start,a.stop):null)), root_1);

            	                adaptor.addChild(root_1, stream_retval.nextTree());
            	                adaptor.addChild(root_1, stream_a.nextTree());

            	                adaptor.addChild(root_0, root_1);
            	                }

            	            }

            	            retval.tree = root_0;}
            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1127:12: (p= postfixOp si= scopeIdentifier -> ^( $p $postfixExpr $si) )?
            int alt69=2;
            alt69 = dfa69.predict(input);
            switch (alt69) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1127:15: p= postfixOp si= scopeIdentifier
                    {
                    pushFollow(FOLLOW_postfixOp_in_postfixExpr14382);
                    p=postfixOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_postfixOp.add(p.getTree());
                    pushFollow(FOLLOW_scopeIdentifier_in_postfixExpr14387);
                    si=scopeIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_scopeIdentifier.add(si.getTree());


                    // AST REWRITE
                    // elements: postfixExpr, si, p
                    // token labels: 
                    // rule labels: retval, p, si
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                    RewriteRuleSubtreeStream stream_p=new RewriteRuleSubtreeStream(adaptor,"rule p",p!=null?p.tree:null);
                    RewriteRuleSubtreeStream stream_si=new RewriteRuleSubtreeStream(adaptor,"rule si",si!=null?si.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1127:47: -> ^( $p $postfixExpr $si)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1127:50: ^( $p $postfixExpr $si)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_p.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_retval.nextTree());
                        adaptor.addChild(root_1, stream_si.nextTree());

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 54, postfixExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "postfixExpr"

    public static class arrayIndexExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arrayIndexExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1131:7: arrayIndexExpr : ( TOK_LBRACK index TOK_RBRACK ) -> index ;
    public final BEOqlParser.arrayIndexExpr_return arrayIndexExpr() throws RecognitionException {
        BEOqlParser.arrayIndexExpr_return retval = new BEOqlParser.arrayIndexExpr_return();
        retval.start = input.LT(1);
        int arrayIndexExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LBRACK179=null;
        Token TOK_RBRACK181=null;
        BEOqlParser.index_return index180 = null;


        CommonTree TOK_LBRACK179_tree=null;
        CommonTree TOK_RBRACK181_tree=null;
        RewriteRuleTokenStream stream_TOK_LBRACK=new RewriteRuleTokenStream(adaptor,"token TOK_LBRACK");
        RewriteRuleTokenStream stream_TOK_RBRACK=new RewriteRuleTokenStream(adaptor,"token TOK_RBRACK");
        RewriteRuleSubtreeStream stream_index=new RewriteRuleSubtreeStream(adaptor,"rule index");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 55) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1131:21: ( ( TOK_LBRACK index TOK_RBRACK ) -> index )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1132:13: ( TOK_LBRACK index TOK_RBRACK )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1132:13: ( TOK_LBRACK index TOK_RBRACK )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1132:14: TOK_LBRACK index TOK_RBRACK
            {
            TOK_LBRACK179=(Token)match(input,TOK_LBRACK,FOLLOW_TOK_LBRACK_in_arrayIndexExpr14442); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LBRACK.add(TOK_LBRACK179);

            pushFollow(FOLLOW_index_in_arrayIndexExpr14444);
            index180=index();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_index.add(index180.getTree());
            TOK_RBRACK181=(Token)match(input,TOK_RBRACK,FOLLOW_TOK_RBRACK_in_arrayIndexExpr14446); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RBRACK.add(TOK_RBRACK181);


            }



            // AST REWRITE
            // elements: index
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1132:43: -> index
            {
                adaptor.addChild(root_0, stream_index.nextTree());

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 55, arrayIndexExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "arrayIndexExpr"

    public static class index_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "index"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1136:7: index : (e= expr -> expr ) ( | -> ^( INDEX_SINGLE $index) ) ;
    public final BEOqlParser.index_return index() throws RecognitionException {
        BEOqlParser.index_return retval = new BEOqlParser.index_return();
        retval.start = input.LT(1);
        int index_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.expr_return e = null;


        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1136:13: ( (e= expr -> expr ) ( | -> ^( INDEX_SINGLE $index) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1138:15: (e= expr -> expr ) ( | -> ^( INDEX_SINGLE $index) )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1138:15: (e= expr -> expr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1138:17: e= expr
            {
            pushFollow(FOLLOW_expr_in_index14509);
            e=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(e.getTree());


            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1138:24: -> expr
            {
                adaptor.addChild(root_0, stream_expr.nextTree());

            }

            retval.tree = root_0;}
            }

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1139:15: ( | -> ^( INDEX_SINGLE $index) )
            int alt70=2;
            alt70 = dfa70.predict(input);
            switch (alt70) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1150:15: 
                    {
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1150:33: 
                    {

                    // AST REWRITE
                    // elements: index
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1150:33: -> ^( INDEX_SINGLE $index)
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1150:36: ^( INDEX_SINGLE $index)
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(INDEX_SINGLE, "INDEX_SINGLE"), root_1);

                        adaptor.addChild(root_1, stream_retval.nextTree());

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 56, index_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "index"

    public static class pathFunctionExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pathFunctionExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1156:7: pathFunctionExpr : pathExpr argList -> ^( PATHFUNCTION_EXPRESSION[$pathFunctionExpr.start,$pathFunctionExpr.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) argList ) ;
    public final BEOqlParser.pathFunctionExpr_return pathFunctionExpr() throws RecognitionException {
        BEOqlParser.pathFunctionExpr_return retval = new BEOqlParser.pathFunctionExpr_return();
        retval.start = input.LT(1);
        int pathFunctionExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.pathExpr_return pathExpr182 = null;

        BEOqlParser.argList_return argList183 = null;


        RewriteRuleSubtreeStream stream_argList=new RewriteRuleSubtreeStream(adaptor,"rule argList");
        RewriteRuleSubtreeStream stream_pathExpr=new RewriteRuleSubtreeStream(adaptor,"rule pathExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 57) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1156:23: ( pathExpr argList -> ^( PATHFUNCTION_EXPRESSION[$pathFunctionExpr.start,$pathFunctionExpr.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) argList ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1157:8: pathExpr argList
            {
            pushFollow(FOLLOW_pathExpr_in_pathFunctionExpr14743);
            pathExpr182=pathExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_pathExpr.add(pathExpr182.getTree());
            pushFollow(FOLLOW_argList_in_pathFunctionExpr14745);
            argList183=argList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_argList.add(argList183.getTree());


            // AST REWRITE
            // elements: pathExpr, argList
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1157:25: -> ^( PATHFUNCTION_EXPRESSION[$pathFunctionExpr.start,$pathFunctionExpr.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) argList )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1157:28: ^( PATHFUNCTION_EXPRESSION[$pathFunctionExpr.start,$pathFunctionExpr.text] ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) argList )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PATHFUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1157:102: ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PATH_EXPRESSION, (pathExpr182!=null?((Token)pathExpr182.start):null), (pathExpr182!=null?input.toString(pathExpr182.start,pathExpr182.stop):null)), root_2);

                adaptor.addChild(root_2, stream_pathExpr.nextTree());

                adaptor.addChild(root_1, root_2);
                }
                adaptor.addChild(root_1, stream_argList.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 57, pathFunctionExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "pathFunctionExpr"

    public static class blockExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "blockExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1160:7: blockExpr : (lc= TOK_LPAREN expr TOK_RPAREN ) -> ^( BLOCK_EXPRESSION[$lc,$blockExpr.text] expr ) ;
    public final BEOqlParser.blockExpr_return blockExpr() throws RecognitionException {
        BEOqlParser.blockExpr_return retval = new BEOqlParser.blockExpr_return();
        retval.start = input.LT(1);
        int blockExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        Token TOK_RPAREN185=null;
        BEOqlParser.expr_return expr184 = null;


        CommonTree lc_tree=null;
        CommonTree TOK_RPAREN185_tree=null;
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 58) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1160:16: ( (lc= TOK_LPAREN expr TOK_RPAREN ) -> ^( BLOCK_EXPRESSION[$lc,$blockExpr.text] expr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1161:7: (lc= TOK_LPAREN expr TOK_RPAREN )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1161:7: (lc= TOK_LPAREN expr TOK_RPAREN )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1161:8: lc= TOK_LPAREN expr TOK_RPAREN
            {
            lc=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_blockExpr14791); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(lc);

            pushFollow(FOLLOW_expr_in_blockExpr14793);
            expr184=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(expr184.getTree());
            TOK_RPAREN185=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_blockExpr14795); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN185);


            }



            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1161:40: -> ^( BLOCK_EXPRESSION[$lc,$blockExpr.text] expr )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1161:43: ^( BLOCK_EXPRESSION[$lc,$blockExpr.text] expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BLOCK_EXPRESSION, lc, input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 58, blockExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "blockExpr"

    public static class primaryExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "primaryExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1164:7: primaryExpr : ( blockExpr -> blockExpr | collectionExpr -> collectionExpr | aggregateExpr -> aggregateExpr | pathFunctionExpr -> pathFunctionExpr | labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier ) | bindVar -> bindVar | literal -> literal | TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr ) ;
    public final BEOqlParser.primaryExpr_return primaryExpr() throws RecognitionException {
        BEOqlParser.primaryExpr_return retval = new BEOqlParser.primaryExpr_return();
        retval.start = input.LT(1);
        int primaryExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN194=null;
        Token TOK_RPAREN196=null;
        BEOqlParser.blockExpr_return blockExpr186 = null;

        BEOqlParser.collectionExpr_return collectionExpr187 = null;

        BEOqlParser.aggregateExpr_return aggregateExpr188 = null;

        BEOqlParser.pathFunctionExpr_return pathFunctionExpr189 = null;

        BEOqlParser.labelIdentifier_return labelIdentifier190 = null;

        BEOqlParser.argList_return argList191 = null;

        BEOqlParser.bindVar_return bindVar192 = null;

        BEOqlParser.literal_return literal193 = null;

        BEOqlParser.selectExpr_return selectExpr195 = null;


        CommonTree TOK_LPAREN194_tree=null;
        CommonTree TOK_RPAREN196_tree=null;
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_argList=new RewriteRuleSubtreeStream(adaptor,"rule argList");
        RewriteRuleSubtreeStream stream_selectExpr=new RewriteRuleSubtreeStream(adaptor,"rule selectExpr");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        RewriteRuleSubtreeStream stream_blockExpr=new RewriteRuleSubtreeStream(adaptor,"rule blockExpr");
        RewriteRuleSubtreeStream stream_aggregateExpr=new RewriteRuleSubtreeStream(adaptor,"rule aggregateExpr");
        RewriteRuleSubtreeStream stream_collectionExpr=new RewriteRuleSubtreeStream(adaptor,"rule collectionExpr");
        RewriteRuleSubtreeStream stream_bindVar=new RewriteRuleSubtreeStream(adaptor,"rule bindVar");
        RewriteRuleSubtreeStream stream_pathFunctionExpr=new RewriteRuleSubtreeStream(adaptor,"rule pathFunctionExpr");
        RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 59) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1164:19: ( ( blockExpr -> blockExpr | collectionExpr -> collectionExpr | aggregateExpr -> aggregateExpr | pathFunctionExpr -> pathFunctionExpr | labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier ) | bindVar -> bindVar | literal -> literal | TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1165:15: ( blockExpr -> blockExpr | collectionExpr -> collectionExpr | aggregateExpr -> aggregateExpr | pathFunctionExpr -> pathFunctionExpr | labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier ) | bindVar -> bindVar | literal -> literal | TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1165:15: ( blockExpr -> blockExpr | collectionExpr -> collectionExpr | aggregateExpr -> aggregateExpr | pathFunctionExpr -> pathFunctionExpr | labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier ) | bindVar -> bindVar | literal -> literal | TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr )
            int alt72=8;
            alt72 = dfa72.predict(input);
            switch (alt72) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1166:15: blockExpr
                    {
                    pushFollow(FOLLOW_blockExpr_in_primaryExpr14857);
                    blockExpr186=blockExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_blockExpr.add(blockExpr186.getTree());


                    // AST REWRITE
                    // elements: blockExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1166:25: -> blockExpr
                    {
                        adaptor.addChild(root_0, stream_blockExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1168:19: collectionExpr
                    {
                    pushFollow(FOLLOW_collectionExpr_in_primaryExpr14897);
                    collectionExpr187=collectionExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_collectionExpr.add(collectionExpr187.getTree());


                    // AST REWRITE
                    // elements: collectionExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1168:34: -> collectionExpr
                    {
                        adaptor.addChild(root_0, stream_collectionExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1169:19: aggregateExpr
                    {
                    pushFollow(FOLLOW_aggregateExpr_in_primaryExpr14921);
                    aggregateExpr188=aggregateExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_aggregateExpr.add(aggregateExpr188.getTree());


                    // AST REWRITE
                    // elements: aggregateExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1169:34: -> aggregateExpr
                    {
                        adaptor.addChild(root_0, stream_aggregateExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1170:19: pathFunctionExpr
                    {
                    pushFollow(FOLLOW_pathFunctionExpr_in_primaryExpr14947);
                    pathFunctionExpr189=pathFunctionExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathFunctionExpr.add(pathFunctionExpr189.getTree());


                    // AST REWRITE
                    // elements: pathFunctionExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1170:36: -> pathFunctionExpr
                    {
                        adaptor.addChild(root_0, stream_pathFunctionExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1173:19: labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier )
                    {
                    pushFollow(FOLLOW_labelIdentifier_in_primaryExpr15001);
                    labelIdentifier190=labelIdentifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_labelIdentifier.add(labelIdentifier190.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1174:19: ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier )
                    int alt71=2;
                    alt71 = dfa71.predict(input);
                    switch (alt71) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1175:23: argList
                            {
                            pushFollow(FOLLOW_argList_in_primaryExpr15045);
                            argList191=argList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_argList.add(argList191.getTree());


                            // AST REWRITE
                            // elements: labelIdentifier, argList
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 1175:31: -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList )
                            {
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1175:34: ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList )
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                                adaptor.addChild(root_1, stream_labelIdentifier.nextTree());
                                adaptor.addChild(root_1, stream_argList.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1176:37: 
                            {

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

                            root_0 = (CommonTree)adaptor.nil();
                            // 1176:37: -> labelIdentifier
                            {
                                adaptor.addChild(root_0, stream_labelIdentifier.nextTree());

                            }

                            retval.tree = root_0;}
                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1178:19: bindVar
                    {
                    pushFollow(FOLLOW_bindVar_in_primaryExpr15125);
                    bindVar192=bindVar();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_bindVar.add(bindVar192.getTree());


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1178:27: -> bindVar
                    {
                        adaptor.addChild(root_0, stream_bindVar.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 7 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1179:19: literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpr15149);
                    literal193=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_literal.add(literal193.getTree());


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1179:27: -> literal
                    {
                        adaptor.addChild(root_0, stream_literal.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 8 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1180:19: TOK_LPAREN selectExpr TOK_RPAREN
                    {
                    TOK_LPAREN194=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_primaryExpr15173); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN194);

                    pushFollow(FOLLOW_selectExpr_in_primaryExpr15175);
                    selectExpr195=selectExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selectExpr.add(selectExpr195.getTree());
                    TOK_RPAREN196=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_primaryExpr15177); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN196);



                    // AST REWRITE
                    // elements: selectExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1180:52: -> selectExpr
                    {
                        adaptor.addChild(root_0, stream_selectExpr.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 59, primaryExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "primaryExpr"

    public static class bindVar_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "bindVar"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1186:7: bindVar : (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) ) ;
    public final BEOqlParser.bindVar_return bindVar() throws RecognitionException {
        BEOqlParser.bindVar_return retval = new BEOqlParser.bindVar_return();
        retval.start = input.LT(1);
        int bindVar_StartIndex = input.index();
        CommonTree root_0 = null;

        Token lc=null;
        BEOqlParser.labelIdentifier_return labelIdentifier197 = null;


        CommonTree lc_tree=null;
        RewriteRuleTokenStream stream_TOK_DOLLAR=new RewriteRuleTokenStream(adaptor,"token TOK_DOLLAR");
        RewriteRuleSubtreeStream stream_labelIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule labelIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 60) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1186:15: ( (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1187:9: (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1187:9: (lc= TOK_DOLLAR labelIdentifier -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1188:9: lc= TOK_DOLLAR labelIdentifier
            {
            lc=(Token)match(input,TOK_DOLLAR,FOLLOW_TOK_DOLLAR_in_bindVar15244); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_DOLLAR.add(lc);

            pushFollow(FOLLOW_labelIdentifier_in_bindVar15246);
            labelIdentifier197=labelIdentifier();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_labelIdentifier.add(labelIdentifier197.getTree());


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

            root_0 = (CommonTree)adaptor.nil();
            // 1188:39: -> ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1188:42: ^( BIND_VARIABLE_EXPRESSION[$lc,$bindVar.text] labelIdentifier )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BIND_VARIABLE_EXPRESSION, lc, input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_labelIdentifier.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 60, bindVar_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "bindVar"

    public static class argList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argList"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1192:7: argList : TOK_LPAREN ( expr ( TOK_COMMA expr )* )? TOK_RPAREN -> ^( ARG_LIST[$argList.start,$argList.text] ( expr )* ) ;
    public final BEOqlParser.argList_return argList() throws RecognitionException {
        BEOqlParser.argList_return retval = new BEOqlParser.argList_return();
        retval.start = input.LT(1);
        int argList_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN198=null;
        Token TOK_COMMA200=null;
        Token TOK_RPAREN202=null;
        BEOqlParser.expr_return expr199 = null;

        BEOqlParser.expr_return expr201 = null;


        CommonTree TOK_LPAREN198_tree=null;
        CommonTree TOK_COMMA200_tree=null;
        CommonTree TOK_RPAREN202_tree=null;
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 61) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1192:15: ( TOK_LPAREN ( expr ( TOK_COMMA expr )* )? TOK_RPAREN -> ^( ARG_LIST[$argList.start,$argList.text] ( expr )* ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1194:15: TOK_LPAREN ( expr ( TOK_COMMA expr )* )? TOK_RPAREN
            {
            TOK_LPAREN198=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_argList15295); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN198);

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1195:15: ( expr ( TOK_COMMA expr )* )?
            int alt74=2;
            alt74 = dfa74.predict(input);
            switch (alt74) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1196:19: expr ( TOK_COMMA expr )*
                    {
                    pushFollow(FOLLOW_expr_in_argList15331);
                    expr199=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr199.getTree());
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1197:19: ( TOK_COMMA expr )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==TOK_COMMA) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1198:23: TOK_COMMA expr
                    	    {
                    	    TOK_COMMA200=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_argList15375); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA200);

                    	    pushFollow(FOLLOW_expr_in_argList15399);
                    	    expr201=expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expr.add(expr201.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);


                    }
                    break;

            }

            TOK_RPAREN202=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_argList15454); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN202);



            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1204:15: -> ^( ARG_LIST[$argList.start,$argList.text] ( expr )* )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1204:18: ^( ARG_LIST[$argList.start,$argList.text] ( expr )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARG_LIST, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1204:59: ( expr )*
                while ( stream_expr.hasNext() ) {
                    adaptor.addChild(root_1, stream_expr.nextTree());

                }
                stream_expr.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 61, argList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "argList"

    public static class collectionExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "collectionExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1219:7: collectionExpr : TOK_EXISTS TOK_LPAREN selectExpr TOK_RPAREN -> ^( EXISTS_CLAUSE[$collectionExpr.start,$collectionExpr.text] selectExpr ) ;
    public final BEOqlParser.collectionExpr_return collectionExpr() throws RecognitionException {
        BEOqlParser.collectionExpr_return retval = new BEOqlParser.collectionExpr_return();
        retval.start = input.LT(1);
        int collectionExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_EXISTS203=null;
        Token TOK_LPAREN204=null;
        Token TOK_RPAREN206=null;
        BEOqlParser.selectExpr_return selectExpr205 = null;


        CommonTree TOK_EXISTS203_tree=null;
        CommonTree TOK_LPAREN204_tree=null;
        CommonTree TOK_RPAREN206_tree=null;
        RewriteRuleTokenStream stream_TOK_EXISTS=new RewriteRuleTokenStream(adaptor,"token TOK_EXISTS");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_selectExpr=new RewriteRuleSubtreeStream(adaptor,"rule selectExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1219:22: ( TOK_EXISTS TOK_LPAREN selectExpr TOK_RPAREN -> ^( EXISTS_CLAUSE[$collectionExpr.start,$collectionExpr.text] selectExpr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1226:17: TOK_EXISTS TOK_LPAREN selectExpr TOK_RPAREN
            {
            TOK_EXISTS203=(Token)match(input,TOK_EXISTS,FOLLOW_TOK_EXISTS_in_collectionExpr15628); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_EXISTS.add(TOK_EXISTS203);

            TOK_LPAREN204=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_collectionExpr15630); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN204);

            pushFollow(FOLLOW_selectExpr_in_collectionExpr15632);
            selectExpr205=selectExpr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_selectExpr.add(selectExpr205.getTree());
            TOK_RPAREN206=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_collectionExpr15634); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN206);



            // AST REWRITE
            // elements: selectExpr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1227:19: -> ^( EXISTS_CLAUSE[$collectionExpr.start,$collectionExpr.text] selectExpr )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1227:22: ^( EXISTS_CLAUSE[$collectionExpr.start,$collectionExpr.text] selectExpr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EXISTS_CLAUSE, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_selectExpr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 62, collectionExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "collectionExpr"

    public static class literals_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literals"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1231:7: literals : literal ( TOK_COMMA literal )* -> ^( LITERALS[$literals.start,$literals.text] ( literal )+ ) ;
    public final BEOqlParser.literals_return literals() throws RecognitionException {
        BEOqlParser.literals_return retval = new BEOqlParser.literals_return();
        retval.start = input.LT(1);
        int literals_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_COMMA208=null;
        BEOqlParser.literal_return literal207 = null;

        BEOqlParser.literal_return literal209 = null;


        CommonTree TOK_COMMA208_tree=null;
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1231:16: ( literal ( TOK_COMMA literal )* -> ^( LITERALS[$literals.start,$literals.text] ( literal )+ ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1232:12: literal ( TOK_COMMA literal )*
            {
            pushFollow(FOLLOW_literal_in_literals15700);
            literal207=literal();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_literal.add(literal207.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1232:20: ( TOK_COMMA literal )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==TOK_COMMA) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1232:21: TOK_COMMA literal
            	    {
            	    TOK_COMMA208=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_literals15703); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA208);

            	    pushFollow(FOLLOW_literal_in_literals15705);
            	    literal209=literal();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_literal.add(literal209.getTree());

            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);



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

            root_0 = (CommonTree)adaptor.nil();
            // 1233:12: -> ^( LITERALS[$literals.start,$literals.text] ( literal )+ )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1233:15: ^( LITERALS[$literals.start,$literals.text] ( literal )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(LITERALS, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_literal.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_literal.hasNext() ) {
                    adaptor.addChild(root_1, stream_literal.nextTree());

                }
                stream_literal.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 63, literals_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "literals"

    public static class aggregateOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aggregateOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1236:7: aggregateOp : ( TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG );
    public final BEOqlParser.aggregateOp_return aggregateOp() throws RecognitionException {
        BEOqlParser.aggregateOp_return retval = new BEOqlParser.aggregateOp_return();
        retval.start = input.LT(1);
        int aggregateOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set210=null;

        CommonTree set210_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1236:19: ( TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set210=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_SUM && input.LA(1)<=TOK_AVG) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set210));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 64, aggregateOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "aggregateOp"

    public static class aggregateExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aggregateExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1243:7: aggregateExpr : ( aggregateOp TOK_LPAREN ( TOK_DISTINCT )? expr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] aggregateOp ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | TOK_PENDING_COUNT TOK_LPAREN ( TOK_DISTINCT )? TOK_STAR TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_PENDING_COUNT ( TOK_DISTINCT )? ) | TOK_COUNT TOK_LPAREN ( ( TOK_DISTINCT )? a= expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) ) TOK_RPAREN );
    public final BEOqlParser.aggregateExpr_return aggregateExpr() throws RecognitionException {
        BEOqlParser.aggregateExpr_return retval = new BEOqlParser.aggregateExpr_return();
        retval.start = input.LT(1);
        int aggregateExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN212=null;
        Token TOK_DISTINCT213=null;
        Token TOK_RPAREN215=null;
        Token TOK_PENDING_COUNT216=null;
        Token TOK_LPAREN217=null;
        Token TOK_DISTINCT218=null;
        Token TOK_STAR219=null;
        Token TOK_RPAREN220=null;
        Token TOK_COUNT221=null;
        Token TOK_LPAREN222=null;
        Token TOK_DISTINCT223=null;
        Token TOK_RPAREN225=null;
        BEOqlParser.expr_return a = null;

        BEOqlParser.aggregateOp_return aggregateOp211 = null;

        BEOqlParser.expr_return expr214 = null;

        BEOqlParser.scopeIdentifier_return scopeIdentifier224 = null;


        CommonTree TOK_LPAREN212_tree=null;
        CommonTree TOK_DISTINCT213_tree=null;
        CommonTree TOK_RPAREN215_tree=null;
        CommonTree TOK_PENDING_COUNT216_tree=null;
        CommonTree TOK_LPAREN217_tree=null;
        CommonTree TOK_DISTINCT218_tree=null;
        CommonTree TOK_STAR219_tree=null;
        CommonTree TOK_RPAREN220_tree=null;
        CommonTree TOK_COUNT221_tree=null;
        CommonTree TOK_LPAREN222_tree=null;
        CommonTree TOK_DISTINCT223_tree=null;
        CommonTree TOK_RPAREN225_tree=null;
        RewriteRuleTokenStream stream_TOK_STAR=new RewriteRuleTokenStream(adaptor,"token TOK_STAR");
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_COUNT=new RewriteRuleTokenStream(adaptor,"token TOK_COUNT");
        RewriteRuleTokenStream stream_TOK_PENDING_COUNT=new RewriteRuleTokenStream(adaptor,"token TOK_PENDING_COUNT");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleTokenStream stream_TOK_DISTINCT=new RewriteRuleTokenStream(adaptor,"token TOK_DISTINCT");
        RewriteRuleSubtreeStream stream_aggregateOp=new RewriteRuleSubtreeStream(adaptor,"rule aggregateOp");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        RewriteRuleSubtreeStream stream_scopeIdentifier=new RewriteRuleSubtreeStream(adaptor,"rule scopeIdentifier");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 65) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1243:21: ( aggregateOp TOK_LPAREN ( TOK_DISTINCT )? expr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] aggregateOp ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | TOK_PENDING_COUNT TOK_LPAREN ( TOK_DISTINCT )? TOK_STAR TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_PENDING_COUNT ( TOK_DISTINCT )? ) | TOK_COUNT TOK_LPAREN ( ( TOK_DISTINCT )? a= expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) ) TOK_RPAREN )
            int alt80=3;
            switch ( input.LA(1) ) {
            case TOK_SUM:
            case TOK_MIN:
            case TOK_MAX:
            case TOK_AVG:
                {
                alt80=1;
                }
                break;
            case TOK_PENDING_COUNT:
                {
                alt80=2;
                }
                break;
            case TOK_COUNT:
                {
                alt80=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1248:15: aggregateOp TOK_LPAREN ( TOK_DISTINCT )? expr TOK_RPAREN
                    {
                    pushFollow(FOLLOW_aggregateOp_in_aggregateExpr15870);
                    aggregateOp211=aggregateOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_aggregateOp.add(aggregateOp211.getTree());
                    TOK_LPAREN212=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_aggregateExpr15886); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN212);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1249:26: ( TOK_DISTINCT )?
                    int alt76=2;
                    alt76 = dfa76.predict(input);
                    switch (alt76) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1249:27: TOK_DISTINCT
                            {
                            TOK_DISTINCT213=(Token)match(input,TOK_DISTINCT,FOLLOW_TOK_DISTINCT_in_aggregateExpr15889); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_DISTINCT.add(TOK_DISTINCT213);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_expr_in_aggregateExpr15893);
                    expr214=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr214.getTree());
                    TOK_RPAREN215=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_aggregateExpr15895); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN215);



                    // AST REWRITE
                    // elements: aggregateOp, expr, TOK_DISTINCT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1250:15: -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] aggregateOp ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1250:18: ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] aggregateOp ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_aggregateOp.nextTree());
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1250:94: ( TOK_DISTINCT )?
                        if ( stream_TOK_DISTINCT.hasNext() ) {
                            adaptor.addChild(root_1, stream_TOK_DISTINCT.nextNode());

                        }
                        stream_TOK_DISTINCT.reset();
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1250:110: ^( ARG_LIST[$expr.start,$expr.text] expr )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARG_LIST, (expr214!=null?((Token)expr214.start):null), (expr214!=null?input.toString(expr214.start,expr214.stop):null)), root_2);

                        adaptor.addChild(root_2, stream_expr.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1251:15: TOK_PENDING_COUNT TOK_LPAREN ( TOK_DISTINCT )? TOK_STAR TOK_RPAREN
                    {
                    TOK_PENDING_COUNT216=(Token)match(input,TOK_PENDING_COUNT,FOLLOW_TOK_PENDING_COUNT_in_aggregateExpr15947); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_PENDING_COUNT.add(TOK_PENDING_COUNT216);

                    TOK_LPAREN217=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_aggregateExpr15963); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN217);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1252:26: ( TOK_DISTINCT )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==TOK_DISTINCT) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1252:27: TOK_DISTINCT
                            {
                            TOK_DISTINCT218=(Token)match(input,TOK_DISTINCT,FOLLOW_TOK_DISTINCT_in_aggregateExpr15966); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_TOK_DISTINCT.add(TOK_DISTINCT218);


                            }
                            break;

                    }

                    TOK_STAR219=(Token)match(input,TOK_STAR,FOLLOW_TOK_STAR_in_aggregateExpr15970); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_STAR.add(TOK_STAR219);

                    TOK_RPAREN220=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_aggregateExpr15972); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN220);



                    // AST REWRITE
                    // elements: TOK_DISTINCT, TOK_PENDING_COUNT
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1253:15: -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_PENDING_COUNT ( TOK_DISTINCT )? )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1253:18: ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_PENDING_COUNT ( TOK_DISTINCT )? )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_TOK_PENDING_COUNT.nextNode());
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1253:100: ( TOK_DISTINCT )?
                        if ( stream_TOK_DISTINCT.hasNext() ) {
                            adaptor.addChild(root_1, stream_TOK_DISTINCT.nextNode());

                        }
                        stream_TOK_DISTINCT.reset();

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1254:15: TOK_COUNT TOK_LPAREN ( ( TOK_DISTINCT )? a= expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) ) TOK_RPAREN
                    {
                    TOK_COUNT221=(Token)match(input,TOK_COUNT,FOLLOW_TOK_COUNT_in_aggregateExpr16016); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_COUNT.add(TOK_COUNT221);

                    TOK_LPAREN222=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_aggregateExpr16032); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN222);

                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1256:15: ( ( TOK_DISTINCT )? a= expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) )
                    int alt79=2;
                    alt79 = dfa79.predict(input);
                    switch (alt79) {
                        case 1 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1260:17: ( TOK_DISTINCT )? a= expr
                            {
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1260:17: ( TOK_DISTINCT )?
                            int alt78=2;
                            alt78 = dfa78.predict(input);
                            switch (alt78) {
                                case 1 :
                                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1260:18: TOK_DISTINCT
                                    {
                                    TOK_DISTINCT223=(Token)match(input,TOK_DISTINCT,FOLLOW_TOK_DISTINCT_in_aggregateExpr16088); if (state.failed) return retval; 
                                    if ( state.backtracking==0 ) stream_TOK_DISTINCT.add(TOK_DISTINCT223);


                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_expr_in_aggregateExpr16110);
                            a=expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_expr.add(a.getTree());


                            // AST REWRITE
                            // elements: TOK_DISTINCT, expr, TOK_COUNT
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 1261:24: -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) )
                            {
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1261:27: ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) )
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                                adaptor.addChild(root_1, stream_TOK_COUNT.nextNode());
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1261:102: ( TOK_DISTINCT )?
                                if ( stream_TOK_DISTINCT.hasNext() ) {
                                    adaptor.addChild(root_1, stream_TOK_DISTINCT.nextNode());

                                }
                                stream_TOK_DISTINCT.reset();
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1261:118: ^( ARG_LIST[$expr.start,$expr.text] expr )
                                {
                                CommonTree root_2 = (CommonTree)adaptor.nil();
                                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARG_LIST, (a!=null?((Token)a.start):null), (a!=null?input.toString(a.start,a.stop):null)), root_2);

                                adaptor.addChild(root_2, stream_expr.nextTree());

                                adaptor.addChild(root_1, root_2);
                                }

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1262:17: scopeIdentifier
                            {
                            pushFollow(FOLLOW_scopeIdentifier_in_aggregateExpr16152);
                            scopeIdentifier224=scopeIdentifier();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_scopeIdentifier.add(scopeIdentifier224.getTree());


                            // AST REWRITE
                            // elements: TOK_COUNT, scopeIdentifier
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (CommonTree)adaptor.nil();
                            // 1262:33: -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) )
                            {
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1262:36: ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) )
                                {
                                CommonTree root_1 = (CommonTree)adaptor.nil();
                                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                                adaptor.addChild(root_1, stream_TOK_COUNT.nextNode());
                                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1262:111: ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier )
                                {
                                CommonTree root_2 = (CommonTree)adaptor.nil();
                                root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ARG_LIST, (scopeIdentifier224!=null?((Token)scopeIdentifier224.start):null), (scopeIdentifier224!=null?input.toString(scopeIdentifier224.start,scopeIdentifier224.stop):null)), root_2);

                                adaptor.addChild(root_2, stream_scopeIdentifier.nextTree());

                                adaptor.addChild(root_1, root_2);
                                }

                                adaptor.addChild(root_0, root_1);
                                }

                            }

                            retval.tree = root_0;}
                            }
                            break;

                    }

                    TOK_RPAREN225=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_aggregateExpr16201); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN225);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 65, aggregateExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "aggregateExpr"

    public static class undefinedOp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "undefinedOp"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1267:7: undefinedOp : ( TOK_UNDEFINED | TOK_DEFINED );
    public final BEOqlParser.undefinedOp_return undefinedOp() throws RecognitionException {
        BEOqlParser.undefinedOp_return retval = new BEOqlParser.undefinedOp_return();
        retval.start = input.LT(1);
        int undefinedOp_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set226=null;

        CommonTree set226_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 66) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1267:19: ( TOK_UNDEFINED | TOK_DEFINED )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set226=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_UNDEFINED && input.LA(1)<=TOK_DEFINED) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set226));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 66, undefinedOp_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "undefinedOp"

    public static class undefinedExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "undefinedExpr"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1272:7: undefinedExpr : ( undefinedOp TOK_LPAREN selectExpr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp ^( SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr ) ) | undefinedOp TOK_LPAREN expr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp expr ) );
    public final BEOqlParser.undefinedExpr_return undefinedExpr() throws RecognitionException {
        BEOqlParser.undefinedExpr_return retval = new BEOqlParser.undefinedExpr_return();
        retval.start = input.LT(1);
        int undefinedExpr_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LPAREN228=null;
        Token TOK_RPAREN230=null;
        Token TOK_LPAREN232=null;
        Token TOK_RPAREN234=null;
        BEOqlParser.undefinedOp_return undefinedOp227 = null;

        BEOqlParser.selectExpr_return selectExpr229 = null;

        BEOqlParser.undefinedOp_return undefinedOp231 = null;

        BEOqlParser.expr_return expr233 = null;


        CommonTree TOK_LPAREN228_tree=null;
        CommonTree TOK_RPAREN230_tree=null;
        CommonTree TOK_LPAREN232_tree=null;
        CommonTree TOK_RPAREN234_tree=null;
        RewriteRuleTokenStream stream_TOK_LPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_LPAREN");
        RewriteRuleTokenStream stream_TOK_RPAREN=new RewriteRuleTokenStream(adaptor,"token TOK_RPAREN");
        RewriteRuleSubtreeStream stream_selectExpr=new RewriteRuleSubtreeStream(adaptor,"rule selectExpr");
        RewriteRuleSubtreeStream stream_undefinedOp=new RewriteRuleSubtreeStream(adaptor,"rule undefinedOp");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1272:21: ( undefinedOp TOK_LPAREN selectExpr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp ^( SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr ) ) | undefinedOp TOK_LPAREN expr TOK_RPAREN -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp expr ) )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( ((LA81_0>=TOK_UNDEFINED && LA81_0<=TOK_DEFINED)) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==TOK_LPAREN) ) {
                    int LA81_2 = input.LA(3);

                    if ( (synpred126_BEOql()) ) {
                        alt81=1;
                    }
                    else if ( (true) ) {
                        alt81=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 81, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1273:15: undefinedOp TOK_LPAREN selectExpr TOK_RPAREN
                    {
                    pushFollow(FOLLOW_undefinedOp_in_undefinedExpr16285);
                    undefinedOp227=undefinedOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_undefinedOp.add(undefinedOp227.getTree());
                    TOK_LPAREN228=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_undefinedExpr16301); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN228);

                    pushFollow(FOLLOW_selectExpr_in_undefinedExpr16317);
                    selectExpr229=selectExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_selectExpr.add(selectExpr229.getTree());
                    TOK_RPAREN230=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_undefinedExpr16333); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN230);



                    // AST REWRITE
                    // elements: selectExpr, undefinedOp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1277:15: -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp ^( SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr ) )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1277:18: ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp ^( SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr ) )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_undefinedOp.nextTree());
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1277:95: ^( SUBSELECT_EXPRESSION[$selectExpr.start,$selectExpr.text] selectExpr )
                        {
                        CommonTree root_2 = (CommonTree)adaptor.nil();
                        root_2 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SUBSELECT_EXPRESSION, (selectExpr229!=null?((Token)selectExpr229.start):null), (selectExpr229!=null?input.toString(selectExpr229.start,selectExpr229.stop):null)), root_2);

                        adaptor.addChild(root_2, stream_selectExpr.nextTree());

                        adaptor.addChild(root_1, root_2);
                        }

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1278:15: undefinedOp TOK_LPAREN expr TOK_RPAREN
                    {
                    pushFollow(FOLLOW_undefinedOp_in_undefinedExpr16380);
                    undefinedOp231=undefinedOp();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_undefinedOp.add(undefinedOp231.getTree());
                    TOK_LPAREN232=(Token)match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_undefinedExpr16396); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_LPAREN.add(TOK_LPAREN232);

                    pushFollow(FOLLOW_expr_in_undefinedExpr16412);
                    expr233=expr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_expr.add(expr233.getTree());
                    TOK_RPAREN234=(Token)match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_undefinedExpr16428); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_RPAREN.add(TOK_RPAREN234);



                    // AST REWRITE
                    // elements: expr, undefinedOp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1282:15: -> ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp expr )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1282:18: ^( FUNCTION_EXPRESSION[$undefinedExpr.start,$undefinedExpr.text] undefinedOp expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FUNCTION_EXPRESSION, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_undefinedOp.nextTree());
                        adaptor.addChild(root_1, stream_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 67, undefinedExpr_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "undefinedExpr"

    public static class fieldList_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldList"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1286:7: fieldList : ( field ( TOK_COMMA field )* ) -> ^( FIELD_LIST[$fieldList.start,$fieldList.text] ( field )+ ) ;
    public final BEOqlParser.fieldList_return fieldList() throws RecognitionException {
        BEOqlParser.fieldList_return retval = new BEOqlParser.fieldList_return();
        retval.start = input.LT(1);
        int fieldList_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_COMMA236=null;
        BEOqlParser.field_return field235 = null;

        BEOqlParser.field_return field237 = null;


        CommonTree TOK_COMMA236_tree=null;
        RewriteRuleTokenStream stream_TOK_COMMA=new RewriteRuleTokenStream(adaptor,"token TOK_COMMA");
        RewriteRuleSubtreeStream stream_field=new RewriteRuleSubtreeStream(adaptor,"rule field");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 68) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1286:17: ( ( field ( TOK_COMMA field )* ) -> ^( FIELD_LIST[$fieldList.start,$fieldList.text] ( field )+ ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1287:12: ( field ( TOK_COMMA field )* )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1287:12: ( field ( TOK_COMMA field )* )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1288:15: field ( TOK_COMMA field )*
            {
            pushFollow(FOLLOW_field_in_fieldList16507);
            field235=field();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_field.add(field235.getTree());
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1289:15: ( TOK_COMMA field )*
            loop82:
            do {
                int alt82=2;
                alt82 = dfa82.predict(input);
                switch (alt82) {
            	case 1 :
            	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1290:19: TOK_COMMA field
            	    {
            	    TOK_COMMA236=(Token)match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_fieldList16543); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_TOK_COMMA.add(TOK_COMMA236);

            	    pushFollow(FOLLOW_field_in_fieldList16563);
            	    field237=field();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_field.add(field237.getTree());

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);


            }



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

            root_0 = (CommonTree)adaptor.nil();
            // 1293:14: -> ^( FIELD_LIST[$fieldList.start,$fieldList.text] ( field )+ )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1293:17: ^( FIELD_LIST[$fieldList.start,$fieldList.text] ( field )+ )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(FIELD_LIST, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                if ( !(stream_field.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_field.hasNext() ) {
                    adaptor.addChild(root_1, stream_field.nextTree());

                }
                stream_field.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 68, fieldList_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "fieldList"

    public static class field_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "field"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1297:7: field : ( expr ) ;
    public final BEOqlParser.field_return field() throws RecognitionException {
        BEOqlParser.field_return retval = new BEOqlParser.field_return();
        retval.start = input.LT(1);
        int field_StartIndex = input.index();
        CommonTree root_0 = null;

        BEOqlParser.expr_return expr238 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 69) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1297:13: ( ( expr ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1298:8: ( expr )
            {
            root_0 = (CommonTree)adaptor.nil();

            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1298:8: ( expr )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1303:9: expr
            {
            pushFollow(FOLLOW_expr_in_field16654);
            expr238=expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expr238.getTree());

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 69, field_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "field"

    public static class type_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1308:7: type : ( TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_INT | TOK_CONCEPT | TOK_ENTITY | TOK_EVENT | TOK_OBJECT | pathExpr -> ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) );
    public final BEOqlParser.type_return type() throws RecognitionException {
        BEOqlParser.type_return retval = new BEOqlParser.type_return();
        retval.start = input.LT(1);
        int type_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_LONG239=null;
        Token TOK_DOUBLE240=null;
        Token TOK_CHAR241=null;
        Token TOK_STRING242=null;
        Token TOK_BOOLEAN243=null;
        Token TOK_DATETIME244=null;
        Token TOK_INT245=null;
        Token TOK_CONCEPT246=null;
        Token TOK_ENTITY247=null;
        Token TOK_EVENT248=null;
        Token TOK_OBJECT249=null;
        BEOqlParser.pathExpr_return pathExpr250 = null;


        CommonTree TOK_LONG239_tree=null;
        CommonTree TOK_DOUBLE240_tree=null;
        CommonTree TOK_CHAR241_tree=null;
        CommonTree TOK_STRING242_tree=null;
        CommonTree TOK_BOOLEAN243_tree=null;
        CommonTree TOK_DATETIME244_tree=null;
        CommonTree TOK_INT245_tree=null;
        CommonTree TOK_CONCEPT246_tree=null;
        CommonTree TOK_ENTITY247_tree=null;
        CommonTree TOK_EVENT248_tree=null;
        CommonTree TOK_OBJECT249_tree=null;
        RewriteRuleSubtreeStream stream_pathExpr=new RewriteRuleSubtreeStream(adaptor,"rule pathExpr");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 70) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1308:12: ( TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_INT | TOK_CONCEPT | TOK_ENTITY | TOK_EVENT | TOK_OBJECT | pathExpr -> ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) )
            int alt83=12;
            alt83 = dfa83.predict(input);
            switch (alt83) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1310:15: TOK_LONG
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_LONG239=(Token)match(input,TOK_LONG,FOLLOW_TOK_LONG_in_type16701); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_LONG239_tree = (CommonTree)adaptor.create(TOK_LONG239);
                    adaptor.addChild(root_0, TOK_LONG239_tree);
                    }

                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1311:19: TOK_DOUBLE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_DOUBLE240=(Token)match(input,TOK_DOUBLE,FOLLOW_TOK_DOUBLE_in_type16721); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_DOUBLE240_tree = (CommonTree)adaptor.create(TOK_DOUBLE240);
                    adaptor.addChild(root_0, TOK_DOUBLE240_tree);
                    }

                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1312:19: TOK_CHAR
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_CHAR241=(Token)match(input,TOK_CHAR,FOLLOW_TOK_CHAR_in_type16741); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_CHAR241_tree = (CommonTree)adaptor.create(TOK_CHAR241);
                    adaptor.addChild(root_0, TOK_CHAR241_tree);
                    }

                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1313:19: TOK_STRING
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_STRING242=(Token)match(input,TOK_STRING,FOLLOW_TOK_STRING_in_type16761); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_STRING242_tree = (CommonTree)adaptor.create(TOK_STRING242);
                    adaptor.addChild(root_0, TOK_STRING242_tree);
                    }

                    }
                    break;
                case 5 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1314:19: TOK_BOOLEAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_BOOLEAN243=(Token)match(input,TOK_BOOLEAN,FOLLOW_TOK_BOOLEAN_in_type16781); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_BOOLEAN243_tree = (CommonTree)adaptor.create(TOK_BOOLEAN243);
                    adaptor.addChild(root_0, TOK_BOOLEAN243_tree);
                    }

                    }
                    break;
                case 6 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1315:19: TOK_DATETIME
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_DATETIME244=(Token)match(input,TOK_DATETIME,FOLLOW_TOK_DATETIME_in_type16801); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_DATETIME244_tree = (CommonTree)adaptor.create(TOK_DATETIME244);
                    adaptor.addChild(root_0, TOK_DATETIME244_tree);
                    }

                    }
                    break;
                case 7 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1316:19: TOK_INT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_INT245=(Token)match(input,TOK_INT,FOLLOW_TOK_INT_in_type16821); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_INT245_tree = (CommonTree)adaptor.create(TOK_INT245);
                    adaptor.addChild(root_0, TOK_INT245_tree);
                    }

                    }
                    break;
                case 8 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1317:19: TOK_CONCEPT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_CONCEPT246=(Token)match(input,TOK_CONCEPT,FOLLOW_TOK_CONCEPT_in_type16841); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_CONCEPT246_tree = (CommonTree)adaptor.create(TOK_CONCEPT246);
                    adaptor.addChild(root_0, TOK_CONCEPT246_tree);
                    }

                    }
                    break;
                case 9 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1318:19: TOK_ENTITY
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_ENTITY247=(Token)match(input,TOK_ENTITY,FOLLOW_TOK_ENTITY_in_type16861); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_ENTITY247_tree = (CommonTree)adaptor.create(TOK_ENTITY247);
                    adaptor.addChild(root_0, TOK_ENTITY247_tree);
                    }

                    }
                    break;
                case 10 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1319:19: TOK_EVENT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_EVENT248=(Token)match(input,TOK_EVENT,FOLLOW_TOK_EVENT_in_type16881); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_EVENT248_tree = (CommonTree)adaptor.create(TOK_EVENT248);
                    adaptor.addChild(root_0, TOK_EVENT248_tree);
                    }

                    }
                    break;
                case 11 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1320:19: TOK_OBJECT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TOK_OBJECT249=(Token)match(input,TOK_OBJECT,FOLLOW_TOK_OBJECT_in_type16901); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TOK_OBJECT249_tree = (CommonTree)adaptor.create(TOK_OBJECT249);
                    adaptor.addChild(root_0, TOK_OBJECT249_tree);
                    }

                    }
                    break;
                case 12 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1321:19: pathExpr
                    {
                    pushFollow(FOLLOW_pathExpr_in_type16921);
                    pathExpr250=pathExpr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_pathExpr.add(pathExpr250.getTree());


                    // AST REWRITE
                    // elements: pathExpr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1321:28: -> ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1321:31: ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PATH_EXPRESSION, (pathExpr250!=null?((Token)pathExpr250.start):null), (pathExpr250!=null?input.toString(pathExpr250.start,pathExpr250.stop):null)), root_1);

                        adaptor.addChild(root_1, stream_pathExpr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 70, type_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "type"

    public static class literal_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1325:7: literal : ( objectLiteral -> ^( NULL_LITERAL[$literal.start,$literal.text] ) | booleanLiteral -> ^( BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral ) | DIGITS -> ^( NUMBER_LITERAL[$literal.start,$literal.text] DIGITS ) | decimalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral ) | doubleLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral ) | charLiteral -> ^( CHAR_LITERAL[$literal.start,$literal.text] charLiteral ) | stringLiteral -> ^( STRING_LITERAL[$literal.start,$literal.text] stringLiteral ) | datetimeLiteral -> ^( DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral ) | hexLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral ) | octalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral ) );
    public final BEOqlParser.literal_return literal() throws RecognitionException {
        BEOqlParser.literal_return retval = new BEOqlParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DIGITS253=null;
        BEOqlParser.objectLiteral_return objectLiteral251 = null;

        BEOqlParser.booleanLiteral_return booleanLiteral252 = null;

        BEOqlParser.decimalLiteral_return decimalLiteral254 = null;

        BEOqlParser.doubleLiteral_return doubleLiteral255 = null;

        BEOqlParser.charLiteral_return charLiteral256 = null;

        BEOqlParser.stringLiteral_return stringLiteral257 = null;

        BEOqlParser.datetimeLiteral_return datetimeLiteral258 = null;

        BEOqlParser.hexLiteral_return hexLiteral259 = null;

        BEOqlParser.octalLiteral_return octalLiteral260 = null;


        CommonTree DIGITS253_tree=null;
        RewriteRuleTokenStream stream_DIGITS=new RewriteRuleTokenStream(adaptor,"token DIGITS");
        RewriteRuleSubtreeStream stream_objectLiteral=new RewriteRuleSubtreeStream(adaptor,"rule objectLiteral");
        RewriteRuleSubtreeStream stream_datetimeLiteral=new RewriteRuleSubtreeStream(adaptor,"rule datetimeLiteral");
        RewriteRuleSubtreeStream stream_octalLiteral=new RewriteRuleSubtreeStream(adaptor,"rule octalLiteral");
        RewriteRuleSubtreeStream stream_stringLiteral=new RewriteRuleSubtreeStream(adaptor,"rule stringLiteral");
        RewriteRuleSubtreeStream stream_booleanLiteral=new RewriteRuleSubtreeStream(adaptor,"rule booleanLiteral");
        RewriteRuleSubtreeStream stream_hexLiteral=new RewriteRuleSubtreeStream(adaptor,"rule hexLiteral");
        RewriteRuleSubtreeStream stream_charLiteral=new RewriteRuleSubtreeStream(adaptor,"rule charLiteral");
        RewriteRuleSubtreeStream stream_decimalLiteral=new RewriteRuleSubtreeStream(adaptor,"rule decimalLiteral");
        RewriteRuleSubtreeStream stream_doubleLiteral=new RewriteRuleSubtreeStream(adaptor,"rule doubleLiteral");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 71) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1325:15: ( objectLiteral -> ^( NULL_LITERAL[$literal.start,$literal.text] ) | booleanLiteral -> ^( BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral ) | DIGITS -> ^( NUMBER_LITERAL[$literal.start,$literal.text] DIGITS ) | decimalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral ) | doubleLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral ) | charLiteral -> ^( CHAR_LITERAL[$literal.start,$literal.text] charLiteral ) | stringLiteral -> ^( STRING_LITERAL[$literal.start,$literal.text] stringLiteral ) | datetimeLiteral -> ^( DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral ) | hexLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral ) | octalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral ) )
            int alt84=10;
            alt84 = dfa84.predict(input);
            switch (alt84) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1327:19: objectLiteral
                    {
                    pushFollow(FOLLOW_objectLiteral_in_literal16975);
                    objectLiteral251=objectLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_objectLiteral.add(objectLiteral251.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Object:"+(objectLiteral251!=null?input.toString(objectLiteral251.start,objectLiteral251.stop):null)); 
                    }


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1327:79: -> ^( NULL_LITERAL[$literal.start,$literal.text] )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1327:82: ^( NULL_LITERAL[$literal.start,$literal.text] )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NULL_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1328:19: booleanLiteral
                    {
                    pushFollow(FOLLOW_booleanLiteral_in_literal17004);
                    booleanLiteral252=booleanLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_booleanLiteral.add(booleanLiteral252.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Boolean:"+(booleanLiteral252!=null?input.toString(booleanLiteral252.start,booleanLiteral252.stop):null)); 
                    }


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1328:82: -> ^( BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1328:85: ^( BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BOOLEAN_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_booleanLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1329:19: DIGITS
                    {
                    DIGITS253=(Token)match(input,DIGITS,FOLLOW_DIGITS_in_literal17035); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_DIGITS.add(DIGITS253);

                    if ( state.backtracking==0 ) {
                       debugOut( "Int:"+ (DIGITS253!=null?DIGITS253.getText():null)); 
                    }


                    // AST REWRITE
                    // elements: DIGITS
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1329:63: -> ^( NUMBER_LITERAL[$literal.start,$literal.text] DIGITS )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1329:66: ^( NUMBER_LITERAL[$literal.start,$literal.text] DIGITS )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_DIGITS.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 4 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1330:19: decimalLiteral
                    {
                    pushFollow(FOLLOW_decimalLiteral_in_literal17066);
                    decimalLiteral254=decimalLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_decimalLiteral.add(decimalLiteral254.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Long:"+ (decimalLiteral254!=null?input.toString(decimalLiteral254.start,decimalLiteral254.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: decimalLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1330:80: -> ^( NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1330:83: ^( NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_decimalLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 5 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1331:19: doubleLiteral
                    {
                    pushFollow(FOLLOW_doubleLiteral_in_literal17097);
                    doubleLiteral255=doubleLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_doubleLiteral.add(doubleLiteral255.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Double:"+(doubleLiteral255!=null?input.toString(doubleLiteral255.start,doubleLiteral255.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: doubleLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1331:79: -> ^( NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1331:82: ^( NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_doubleLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 6 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1332:19: charLiteral
                    {
                    pushFollow(FOLLOW_charLiteral_in_literal17128);
                    charLiteral256=charLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_charLiteral.add(charLiteral256.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Char:"+ (charLiteral256!=null?input.toString(charLiteral256.start,charLiteral256.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: charLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1332:74: -> ^( CHAR_LITERAL[$literal.start,$literal.text] charLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1332:77: ^( CHAR_LITERAL[$literal.start,$literal.text] charLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CHAR_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_charLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 7 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1333:19: stringLiteral
                    {
                    pushFollow(FOLLOW_stringLiteral_in_literal17159);
                    stringLiteral257=stringLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_stringLiteral.add(stringLiteral257.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "String:"+ (stringLiteral257!=null?input.toString(stringLiteral257.start,stringLiteral257.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: stringLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1333:80: -> ^( STRING_LITERAL[$literal.start,$literal.text] stringLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1333:83: ^( STRING_LITERAL[$literal.start,$literal.text] stringLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(STRING_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_stringLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 8 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1334:19: datetimeLiteral
                    {
                    pushFollow(FOLLOW_datetimeLiteral_in_literal17190);
                    datetimeLiteral258=datetimeLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_datetimeLiteral.add(datetimeLiteral258.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Date:" + (datetimeLiteral258!=null?input.toString(datetimeLiteral258.start,datetimeLiteral258.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: datetimeLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1334:83: -> ^( DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1334:86: ^( DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(DATETIME_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_datetimeLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 9 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1335:19: hexLiteral
                    {
                    pushFollow(FOLLOW_hexLiteral_in_literal17221);
                    hexLiteral259=hexLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_hexLiteral.add(hexLiteral259.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut( "Hex:"+ (hexLiteral259!=null?input.toString(hexLiteral259.start,hexLiteral259.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: hexLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1335:72: -> ^( NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1335:75: ^( NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_hexLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 10 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1336:19: octalLiteral
                    {
                    pushFollow(FOLLOW_octalLiteral_in_literal17253);
                    octalLiteral260=octalLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_octalLiteral.add(octalLiteral260.getTree());
                    if ( state.backtracking==0 ) {
                       debugOut("Octal:" + (octalLiteral260!=null?input.toString(octalLiteral260.start,octalLiteral260.stop):null)); 
                    }


                    // AST REWRITE
                    // elements: octalLiteral
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 1336:77: -> ^( NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral )
                    {
                        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1336:80: ^( NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(NUMBER_LITERAL, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                        adaptor.addChild(root_1, stream_octalLiteral.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 71, literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class objectLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "objectLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1341:7: objectLiteral : TOK_NULL ;
    public final BEOqlParser.objectLiteral_return objectLiteral() throws RecognitionException {
        BEOqlParser.objectLiteral_return retval = new BEOqlParser.objectLiteral_return();
        retval.start = input.LT(1);
        int objectLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_NULL261=null;

        CommonTree TOK_NULL261_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 72) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1341:21: ( TOK_NULL )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1343:15: TOK_NULL
            {
            root_0 = (CommonTree)adaptor.nil();

            TOK_NULL261=(Token)match(input,TOK_NULL,FOLLOW_TOK_NULL_in_objectLiteral17334); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TOK_NULL261_tree = (CommonTree)adaptor.create(TOK_NULL261);
            adaptor.addChild(root_0, TOK_NULL261_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 72, objectLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "objectLiteral"

    public static class booleanLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "booleanLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1346:7: booleanLiteral : ( TOK_TRUE | TOK_FALSE ) ;
    public final BEOqlParser.booleanLiteral_return booleanLiteral() throws RecognitionException {
        BEOqlParser.booleanLiteral_return retval = new BEOqlParser.booleanLiteral_return();
        retval.start = input.LT(1);
        int booleanLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set262=null;

        CommonTree set262_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 73) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1346:22: ( ( TOK_TRUE | TOK_FALSE ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1348:15: ( TOK_TRUE | TOK_FALSE )
            {
            root_0 = (CommonTree)adaptor.nil();

            set262=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_TRUE && input.LA(1)<=TOK_FALSE) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set262));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 73, booleanLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "booleanLiteral"

    public static class decimalLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "decimalLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1354:7: decimalLiteral : DecimalLiteral ;
    public final BEOqlParser.decimalLiteral_return decimalLiteral() throws RecognitionException {
        BEOqlParser.decimalLiteral_return retval = new BEOqlParser.decimalLiteral_return();
        retval.start = input.LT(1);
        int decimalLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DecimalLiteral263=null;

        CommonTree DecimalLiteral263_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 74) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1354:22: ( DecimalLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1355:13: DecimalLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            DecimalLiteral263=(Token)match(input,DecimalLiteral,FOLLOW_DecimalLiteral_in_decimalLiteral17467); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DecimalLiteral263_tree = (CommonTree)adaptor.create(DecimalLiteral263);
            adaptor.addChild(root_0, DecimalLiteral263_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 74, decimalLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "decimalLiteral"

    public static class hexLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "hexLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1358:7: hexLiteral : HexLiteral ;
    public final BEOqlParser.hexLiteral_return hexLiteral() throws RecognitionException {
        BEOqlParser.hexLiteral_return retval = new BEOqlParser.hexLiteral_return();
        retval.start = input.LT(1);
        int hexLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token HexLiteral264=null;

        CommonTree HexLiteral264_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1358:18: ( HexLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1359:11: HexLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            HexLiteral264=(Token)match(input,HexLiteral,FOLLOW_HexLiteral_in_hexLiteral17498); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            HexLiteral264_tree = (CommonTree)adaptor.create(HexLiteral264);
            adaptor.addChild(root_0, HexLiteral264_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 75, hexLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "hexLiteral"

    public static class octalLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "octalLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1362:7: octalLiteral : OctalLiteral ;
    public final BEOqlParser.octalLiteral_return octalLiteral() throws RecognitionException {
        BEOqlParser.octalLiteral_return retval = new BEOqlParser.octalLiteral_return();
        retval.start = input.LT(1);
        int octalLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token OctalLiteral265=null;

        CommonTree OctalLiteral265_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 76) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1362:20: ( OctalLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1363:10: OctalLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            OctalLiteral265=(Token)match(input,OctalLiteral,FOLLOW_OctalLiteral_in_octalLiteral17528); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OctalLiteral265_tree = (CommonTree)adaptor.create(OctalLiteral265);
            adaptor.addChild(root_0, OctalLiteral265_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 76, octalLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "octalLiteral"

    public static class doubleLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "doubleLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1366:7: doubleLiteral : ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL ) ;
    public final BEOqlParser.doubleLiteral_return doubleLiteral() throws RecognitionException {
        BEOqlParser.doubleLiteral_return retval = new BEOqlParser.doubleLiteral_return();
        retval.start = input.LT(1);
        int doubleLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set266=null;

        CommonTree set266_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 77) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1366:21: ( ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1367:15: ( TOK_APPROXIMATE_NUMERIC_LITERAL | TOK_EXACT_NUMERIC_LITERAL )
            {
            root_0 = (CommonTree)adaptor.nil();

            set266=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_APPROXIMATE_NUMERIC_LITERAL && input.LA(1)<=TOK_EXACT_NUMERIC_LITERAL) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set266));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 77, doubleLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "doubleLiteral"

    public static class charLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "charLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1373:7: charLiteral : CharLiteral ;
    public final BEOqlParser.charLiteral_return charLiteral() throws RecognitionException {
        BEOqlParser.charLiteral_return retval = new BEOqlParser.charLiteral_return();
        retval.start = input.LT(1);
        int charLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token CharLiteral267=null;

        CommonTree CharLiteral267_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1373:19: ( CharLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1375:15: CharLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            CharLiteral267=(Token)match(input,CharLiteral,FOLLOW_CharLiteral_in_charLiteral17659); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            CharLiteral267_tree = (CommonTree)adaptor.create(CharLiteral267);
            adaptor.addChild(root_0, CharLiteral267_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 78, charLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "charLiteral"

    public static class stringLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stringLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1379:7: stringLiteral : StringLiteral ;
    public final BEOqlParser.stringLiteral_return stringLiteral() throws RecognitionException {
        BEOqlParser.stringLiteral_return retval = new BEOqlParser.stringLiteral_return();
        retval.start = input.LT(1);
        int stringLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token StringLiteral268=null;

        CommonTree StringLiteral268_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 79) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1379:21: ( StringLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1380:16: StringLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            StringLiteral268=(Token)match(input,StringLiteral,FOLLOW_StringLiteral_in_stringLiteral17700); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            StringLiteral268_tree = (CommonTree)adaptor.create(StringLiteral268);
            adaptor.addChild(root_0, StringLiteral268_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 79, stringLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "stringLiteral"

    public static class datetimeLiteral_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "datetimeLiteral"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1383:7: datetimeLiteral : DateTimeLiteral ;
    public final BEOqlParser.datetimeLiteral_return datetimeLiteral() throws RecognitionException {
        BEOqlParser.datetimeLiteral_return retval = new BEOqlParser.datetimeLiteral_return();
        retval.start = input.LT(1);
        int datetimeLiteral_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DateTimeLiteral269=null;

        CommonTree DateTimeLiteral269_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1383:23: ( DateTimeLiteral )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1384:15: DateTimeLiteral
            {
            root_0 = (CommonTree)adaptor.nil();

            DateTimeLiteral269=(Token)match(input,DateTimeLiteral,FOLLOW_DateTimeLiteral_in_datetimeLiteral17739); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DateTimeLiteral269_tree = (CommonTree)adaptor.create(DateTimeLiteral269);
            adaptor.addChild(root_0, DateTimeLiteral269_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 80, datetimeLiteral_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "datetimeLiteral"

    public static class keyWords_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "keyWords"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1397:7: keyWords : ( TOK_DISTINCT | TOK_FROM | TOK_SELECT | TOK_IN | TOK_AS | TOK_WHERE | TOK_GROUP | TOK_BY | TOK_HAVING | TOK_ORDER | TOK_ASC | TOK_DESC | TOK_OR | TOK_AND | TOK_FOR | TOK_ALL | TOK_EXISTS | TOK_LIKE | TOK_BETWEEN | TOK_UNION | TOK_EXCEPT | TOK_INTERSECT | TOK_MOD | TOK_ABS | TOK_NOT | TOK_FIRST | TOK_LAST | TOK_UNIQUE | TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG | TOK_COUNT | TOK_UNDEFINED | TOK_DEFINED | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_LIMIT | TOK_EMIT | TOK_POLICY | TOK_MAINTAIN | TOK_USING | TOK_PURGE | TOK_WHEN | TOK_OFFSET | TOK_NEW | TOK_DEAD | TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS | TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_DATE | TOK_TIME | TOK_INT | TOK_CONCEPT | TOK_EVENT | TOK_OBJECT );
    public final BEOqlParser.keyWords_return keyWords() throws RecognitionException {
        BEOqlParser.keyWords_return retval = new BEOqlParser.keyWords_return();
        retval.start = input.LT(1);
        int keyWords_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set270=null;

        CommonTree set270_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 81) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1397:15: ( TOK_DISTINCT | TOK_FROM | TOK_SELECT | TOK_IN | TOK_AS | TOK_WHERE | TOK_GROUP | TOK_BY | TOK_HAVING | TOK_ORDER | TOK_ASC | TOK_DESC | TOK_OR | TOK_AND | TOK_FOR | TOK_ALL | TOK_EXISTS | TOK_LIKE | TOK_BETWEEN | TOK_UNION | TOK_EXCEPT | TOK_INTERSECT | TOK_MOD | TOK_ABS | TOK_NOT | TOK_FIRST | TOK_LAST | TOK_UNIQUE | TOK_SUM | TOK_MIN | TOK_MAX | TOK_AVG | TOK_COUNT | TOK_UNDEFINED | TOK_DEFINED | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_LIMIT | TOK_EMIT | TOK_POLICY | TOK_MAINTAIN | TOK_USING | TOK_PURGE | TOK_WHEN | TOK_OFFSET | TOK_NEW | TOK_DEAD | TOK_TIME_MILLISECONDS | TOK_TIME_SECONDS | TOK_TIME_MINUTES | TOK_TIME_HOURS | TOK_TIME_DAYS | TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_DATE | TOK_TIME | TOK_INT | TOK_CONCEPT | TOK_EVENT | TOK_OBJECT )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set270=(Token)input.LT(1);
            if ( (input.LA(1)>=TOK_DISTINCT && input.LA(1)<=TOK_SELECT)||(input.LA(1)>=TOK_IN && input.LA(1)<=TOK_LAST)||(input.LA(1)>=TOK_UNIQUE && input.LA(1)<=TOK_COUNT)||(input.LA(1)>=TOK_UNDEFINED && input.LA(1)<=TOK_CONCEPT)||(input.LA(1)>=TOK_EVENT && input.LA(1)<=TOK_TIME)||input.LA(1)==TOK_LIMIT||(input.LA(1)>=TOK_EMIT && input.LA(1)<=TOK_TIME_DAYS) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set270));
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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 81, keyWords_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "keyWords"

    public static class labelIdentifier_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "labelIdentifier"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1465:7: labelIdentifier : ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] ) ;
    public final BEOqlParser.labelIdentifier_return labelIdentifier() throws RecognitionException {
        BEOqlParser.labelIdentifier_return retval = new BEOqlParser.labelIdentifier_return();
        retval.start = input.LT(1);
        int labelIdentifier_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_HASH271=null;
        Token Identifier273=null;
        BEOqlParser.keyWords_return keyWords272 = null;


        CommonTree TOK_HASH271_tree=null;
        CommonTree Identifier273_tree=null;
        RewriteRuleTokenStream stream_TOK_HASH=new RewriteRuleTokenStream(adaptor,"token TOK_HASH");
        RewriteRuleTokenStream stream_Identifier=new RewriteRuleTokenStream(adaptor,"token Identifier");
        RewriteRuleSubtreeStream stream_keyWords=new RewriteRuleSubtreeStream(adaptor,"rule keyWords");
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 82) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1465:23: ( ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1466:9: ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] )
            {
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1466:9: ( ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] ) | Identifier -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text] )
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==TOK_HASH) ) {
                alt85=1;
            }
            else if ( (LA85_0==Identifier) ) {
                alt85=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }
            switch (alt85) {
                case 1 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1467:13: ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] )
                    {
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1467:13: ( TOK_HASH keyWords -> IDENTIFIER[$labelIdentifier.start,$keyWords.text] )
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1468:17: TOK_HASH keyWords
                    {
                    TOK_HASH271=(Token)match(input,TOK_HASH,FOLLOW_TOK_HASH_in_labelIdentifier18658); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_TOK_HASH.add(TOK_HASH271);

                    pushFollow(FOLLOW_keyWords_in_labelIdentifier18660);
                    keyWords272=keyWords();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_keyWords.add(keyWords272.getTree());


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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1468:35: -> IDENTIFIER[$labelIdentifier.start,$keyWords.text]
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(IDENTIFIER, ((Token)retval.start), (keyWords272!=null?input.toString(keyWords272.start,keyWords272.stop):null)));

                    }

                    retval.tree = root_0;}
                    }


                    }
                    break;
                case 2 :
                    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1470:15: Identifier
                    {
                    Identifier273=(Token)match(input,Identifier,FOLLOW_Identifier_in_labelIdentifier18694); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_Identifier.add(Identifier273);



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

                    root_0 = (CommonTree)adaptor.nil();
                    // 1470:27: -> IDENTIFIER[$labelIdentifier.start,$labelIdentifier.text]
                    {
                        adaptor.addChild(root_0, (CommonTree)adaptor.create(IDENTIFIER, ((Token)retval.start), input.toString(retval.start,input.LT(-1))));

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 82, labelIdentifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "labelIdentifier"

    public static class scopeIdentifier_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "scopeIdentifier"
    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1475:7: scopeIdentifier : TOK_STAR -> ^( SCOPE_IDENTIFIER[$scopeIdentifier.start,$scopeIdentifier.text] TOK_STAR ) ;
    public final BEOqlParser.scopeIdentifier_return scopeIdentifier() throws RecognitionException {
        BEOqlParser.scopeIdentifier_return retval = new BEOqlParser.scopeIdentifier_return();
        retval.start = input.LT(1);
        int scopeIdentifier_StartIndex = input.index();
        CommonTree root_0 = null;

        Token TOK_STAR274=null;

        CommonTree TOK_STAR274_tree=null;
        RewriteRuleTokenStream stream_TOK_STAR=new RewriteRuleTokenStream(adaptor,"token TOK_STAR");

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 83) ) { return retval; }
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1475:23: ( TOK_STAR -> ^( SCOPE_IDENTIFIER[$scopeIdentifier.start,$scopeIdentifier.text] TOK_STAR ) )
            // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1476:3: TOK_STAR
            {
            TOK_STAR274=(Token)match(input,TOK_STAR,FOLLOW_TOK_STAR_in_scopeIdentifier18735); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TOK_STAR.add(TOK_STAR274);



            // AST REWRITE
            // elements: TOK_STAR
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 1476:12: -> ^( SCOPE_IDENTIFIER[$scopeIdentifier.start,$scopeIdentifier.text] TOK_STAR )
            {
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1476:15: ^( SCOPE_IDENTIFIER[$scopeIdentifier.start,$scopeIdentifier.text] TOK_STAR )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SCOPE_IDENTIFIER, ((Token)retval.start), input.toString(retval.start,input.LT(-1))), root_1);

                adaptor.addChild(root_1, stream_TOK_STAR.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

                    catch (RecognitionException rece) {
        	       boolean debug=Boolean.parseBoolean(System.getProperty("com.tibco.be.oql.debug","false"));
                       if(debug) {
                            System.out.println("Error :" + getErrorMessage(rece,BEOqlParser.tokenNames)+" on line: "+rece.line +" column: "+rece.charPositionInLine);
                        }
                        throw new RuntimeException(rece);
                    }
                    finally {
            if ( state.backtracking>0 ) { memoize(input, 83, scopeIdentifier_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "scopeIdentifier"

    // $ANTLR start synpred19_BEOql
    public final void synpred19_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:9: ( ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:10: streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )?
        {
        pushFollow(FOLLOW_streamDefItemEmit_in_synpred19_BEOql8833);
        streamDefItemEmit();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:28: ( TOK_SEMIC streamDefItemPolicy )?
        int alt89=2;
        int LA89_0 = input.LA(1);

        if ( (LA89_0==TOK_SEMIC) ) {
            int LA89_1 = input.LA(2);

            if ( (LA89_1==TOK_POLICY) ) {
                alt89=1;
            }
        }
        switch (alt89) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:29: TOK_SEMIC streamDefItemPolicy
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred19_BEOql8836); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemPolicy_in_synpred19_BEOql8839);
                streamDefItemPolicy();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:62: ( TOK_SEMIC streamDefItemAccept )?
        int alt90=2;
        int LA90_0 = input.LA(1);

        if ( (LA90_0==TOK_SEMIC) ) {
            alt90=1;
        }
        switch (alt90) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:692:63: TOK_SEMIC streamDefItemAccept
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred19_BEOql8844); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemAccept_in_synpred19_BEOql8847);
                streamDefItemAccept();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred19_BEOql

    // $ANTLR start synpred22_BEOql
    public final void synpred22_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:9: ( ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:9: ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:10: streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )?
        {
        pushFollow(FOLLOW_streamDefItemEmit_in_synpred22_BEOql8871);
        streamDefItemEmit();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:28: ( TOK_SEMIC streamDefItemAccept )?
        int alt91=2;
        int LA91_0 = input.LA(1);

        if ( (LA91_0==TOK_SEMIC) ) {
            int LA91_1 = input.LA(2);

            if ( (LA91_1==TOK_ACCEPT) ) {
                alt91=1;
            }
        }
        switch (alt91) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:29: TOK_SEMIC streamDefItemAccept
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred22_BEOql8874); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemAccept_in_synpred22_BEOql8877);
                streamDefItemAccept();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:62: ( TOK_SEMIC streamDefItemPolicy )?
        int alt92=2;
        int LA92_0 = input.LA(1);

        if ( (LA92_0==TOK_SEMIC) ) {
            alt92=1;
        }
        switch (alt92) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:694:63: TOK_SEMIC streamDefItemPolicy
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred22_BEOql8882); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemPolicy_in_synpred22_BEOql8885);
                streamDefItemPolicy();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred22_BEOql

    // $ANTLR start synpred25_BEOql
    public final void synpred25_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:9: ( ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:10: streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )?
        {
        pushFollow(FOLLOW_streamDefItemAccept_in_synpred25_BEOql8913);
        streamDefItemAccept();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:30: ( TOK_SEMIC streamDefItemEmit )?
        int alt93=2;
        int LA93_0 = input.LA(1);

        if ( (LA93_0==TOK_SEMIC) ) {
            int LA93_1 = input.LA(2);

            if ( (LA93_1==TOK_EMIT) ) {
                alt93=1;
            }
        }
        switch (alt93) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:31: TOK_SEMIC streamDefItemEmit
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred25_BEOql8916); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemEmit_in_synpred25_BEOql8919);
                streamDefItemEmit();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:62: ( TOK_SEMIC streamDefItemPolicy )?
        int alt94=2;
        int LA94_0 = input.LA(1);

        if ( (LA94_0==TOK_SEMIC) ) {
            alt94=1;
        }
        switch (alt94) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:696:63: TOK_SEMIC streamDefItemPolicy
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred25_BEOql8924); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemPolicy_in_synpred25_BEOql8927);
                streamDefItemPolicy();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred25_BEOql

    // $ANTLR start synpred28_BEOql
    public final void synpred28_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:9: ( ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:9: ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:10: streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )?
        {
        pushFollow(FOLLOW_streamDefItemAccept_in_synpred28_BEOql8955);
        streamDefItemAccept();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:30: ( TOK_SEMIC streamDefItemPolicy )?
        int alt95=2;
        int LA95_0 = input.LA(1);

        if ( (LA95_0==TOK_SEMIC) ) {
            int LA95_1 = input.LA(2);

            if ( (LA95_1==TOK_POLICY) ) {
                alt95=1;
            }
        }
        switch (alt95) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:31: TOK_SEMIC streamDefItemPolicy
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred28_BEOql8958); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemPolicy_in_synpred28_BEOql8961);
                streamDefItemPolicy();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:64: ( TOK_SEMIC streamDefItemEmit )?
        int alt96=2;
        int LA96_0 = input.LA(1);

        if ( (LA96_0==TOK_SEMIC) ) {
            alt96=1;
        }
        switch (alt96) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:698:65: TOK_SEMIC streamDefItemEmit
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred28_BEOql8966); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemEmit_in_synpred28_BEOql8969);
                streamDefItemEmit();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred28_BEOql

    // $ANTLR start synpred31_BEOql
    public final void synpred31_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:9: ( ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:9: ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:10: streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )?
        {
        pushFollow(FOLLOW_streamDefItemPolicy_in_synpred31_BEOql8997);
        streamDefItemPolicy();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:30: ( TOK_SEMIC streamDefItemAccept )?
        int alt97=2;
        int LA97_0 = input.LA(1);

        if ( (LA97_0==TOK_SEMIC) ) {
            int LA97_1 = input.LA(2);

            if ( (LA97_1==TOK_ACCEPT) ) {
                alt97=1;
            }
        }
        switch (alt97) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:31: TOK_SEMIC streamDefItemAccept
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred31_BEOql9000); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemAccept_in_synpred31_BEOql9003);
                streamDefItemAccept();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:64: ( TOK_SEMIC streamDefItemEmit )?
        int alt98=2;
        int LA98_0 = input.LA(1);

        if ( (LA98_0==TOK_SEMIC) ) {
            alt98=1;
        }
        switch (alt98) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:700:65: TOK_SEMIC streamDefItemEmit
                {
                match(input,TOK_SEMIC,FOLLOW_TOK_SEMIC_in_synpred31_BEOql9008); if (state.failed) return ;
                pushFollow(FOLLOW_streamDefItemEmit_in_synpred31_BEOql9011);
                streamDefItemEmit();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }


        }
    }
    // $ANTLR end synpred31_BEOql

    // $ANTLR start synpred39_BEOql
    public final void synpred39_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:742:9: ( timeWindowDef )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:742:9: timeWindowDef
        {
        pushFollow(FOLLOW_timeWindowDef_in_synpred39_BEOql9511);
        timeWindowDef();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_BEOql

    // $ANTLR start synpred40_BEOql
    public final void synpred40_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:744:9: ( tumblingWindowDef )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:744:9: tumblingWindowDef
        {
        pushFollow(FOLLOW_tumblingWindowDef_in_synpred40_BEOql9531);
        tumblingWindowDef();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred40_BEOql

    // $ANTLR start synpred63_BEOql
    public final void synpred63_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:902:15: ( sortDirection )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:902:15: sortDirection
        {
        pushFollow(FOLLOW_sortDirection_in_synpred63_BEOql11415);
        sortDirection();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred63_BEOql

    // $ANTLR start synpred71_BEOql
    public final void synpred71_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:950:10: ( TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:950:11: TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr
        {
        match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_synpred71_BEOql11907); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred71_BEOql11909);
        type();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:950:27: ( TOK_LBRACK TOK_RBRACK )*
        loop102:
        do {
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==TOK_LBRACK) ) {
                alt102=1;
            }


            switch (alt102) {
        	case 1 :
        	    // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:950:28: TOK_LBRACK TOK_RBRACK
        	    {
        	    match(input,TOK_LBRACK,FOLLOW_TOK_LBRACK_in_synpred71_BEOql11912); if (state.failed) return ;
        	    match(input,TOK_RBRACK,FOLLOW_TOK_RBRACK_in_synpred71_BEOql11914); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop102;
            }
        } while (true);

        match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_synpred71_BEOql11918); if (state.failed) return ;
        pushFollow(FOLLOW_expr_in_synpred71_BEOql11920);
        expr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred71_BEOql

    // $ANTLR start synpred89_BEOql
    public final void synpred89_BEOql_fragment() throws RecognitionException {   
        BEOqlParser.unaryExpr_return u = null;


        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1066:20: (u= unaryExpr )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1066:20: u= unaryExpr
        {
        pushFollow(FOLLOW_unaryExpr_in_synpred89_BEOql13472);
        u=unaryExpr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred89_BEOql

    // $ANTLR start synpred102_BEOql
    public final void synpred102_BEOql_fragment() throws RecognitionException {   
        BEOqlParser.postfixOp_return p = null;

        BEOqlParser.labelIdentifier_return li = null;

        BEOqlParser.arrayIndexExpr_return a = null;


        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1116:13: ( ( (p= postfixOp li= labelIdentifier ) ) (a= arrayIndexExpr )? )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1116:13: ( (p= postfixOp li= labelIdentifier ) ) (a= arrayIndexExpr )?
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1116:13: ( (p= postfixOp li= labelIdentifier ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:17: (p= postfixOp li= labelIdentifier )
        {
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:17: (p= postfixOp li= labelIdentifier )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1117:18: p= postfixOp li= labelIdentifier
        {
        pushFollow(FOLLOW_postfixOp_in_synpred102_BEOql14150);
        p=postfixOp();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_labelIdentifier_in_synpred102_BEOql14155);
        li=labelIdentifier();

        state._fsp--;
        if (state.failed) return ;

        }


        }

        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1125:13: (a= arrayIndexExpr )?
        int alt104=2;
        int LA104_0 = input.LA(1);

        if ( (LA104_0==TOK_LBRACK) ) {
            alt104=1;
        }
        switch (alt104) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1125:15: a= arrayIndexExpr
                {
                pushFollow(FOLLOW_arrayIndexExpr_in_synpred102_BEOql14334);
                a=arrayIndexExpr();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred102_BEOql

    // $ANTLR start synpred104_BEOql
    public final void synpred104_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1150:15: ()
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1150:15: 
        {
        }
    }
    // $ANTLR end synpred104_BEOql

    // $ANTLR start synpred108_BEOql
    public final void synpred108_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1170:19: ( pathFunctionExpr )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1170:19: pathFunctionExpr
        {
        pushFollow(FOLLOW_pathFunctionExpr_in_synpred108_BEOql14947);
        pathFunctionExpr();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred108_BEOql

    // $ANTLR start synpred110_BEOql
    public final void synpred110_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1173:19: ( labelIdentifier ( argList | ) )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1173:19: labelIdentifier ( argList | )
        {
        pushFollow(FOLLOW_labelIdentifier_in_synpred110_BEOql15001);
        labelIdentifier();

        state._fsp--;
        if (state.failed) return ;
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1174:19: ( argList | )
        int alt105=2;
        int LA105_0 = input.LA(1);

        if ( (LA105_0==TOK_LPAREN) ) {
            alt105=1;
        }
        else if ( (LA105_0==EOF) ) {
            alt105=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 105, 0, input);

            throw nvae;
        }
        switch (alt105) {
            case 1 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1175:23: argList
                {
                pushFollow(FOLLOW_argList_in_synpred110_BEOql15045);
                argList();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1176:37: 
                {
                }
                break;

        }


        }
    }
    // $ANTLR end synpred110_BEOql

    // $ANTLR start synpred126_BEOql
    public final void synpred126_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1273:15: ( undefinedOp TOK_LPAREN selectExpr TOK_RPAREN )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1273:15: undefinedOp TOK_LPAREN selectExpr TOK_RPAREN
        {
        pushFollow(FOLLOW_undefinedOp_in_synpred126_BEOql16285);
        undefinedOp();

        state._fsp--;
        if (state.failed) return ;
        match(input,TOK_LPAREN,FOLLOW_TOK_LPAREN_in_synpred126_BEOql16301); if (state.failed) return ;
        pushFollow(FOLLOW_selectExpr_in_synpred126_BEOql16317);
        selectExpr();

        state._fsp--;
        if (state.failed) return ;
        match(input,TOK_RPAREN,FOLLOW_TOK_RPAREN_in_synpred126_BEOql16333); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred126_BEOql

    // $ANTLR start synpred127_BEOql
    public final void synpred127_BEOql_fragment() throws RecognitionException {   
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1290:19: ( TOK_COMMA field )
        // Q:\\be\\5.1\\runtime\\modules\\query\\common\\src\\com\\tibco\\cep\\query\\ast\\parser\\BEOql.g:1290:19: TOK_COMMA field
        {
        match(input,TOK_COMMA,FOLLOW_TOK_COMMA_in_synpred127_BEOql16543); if (state.failed) return ;
        pushFollow(FOLLOW_field_in_synpred127_BEOql16563);
        field();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred127_BEOql

    // Delegated rules

    public final boolean synpred31_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred31_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred89_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred89_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred28_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred108_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred108_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred102_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred102_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred110_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred110_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred71_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred71_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred104_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred104_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred126_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred126_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred63_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred63_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred127_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred127_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred40_BEOql() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred40_BEOql_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA27 dfa27 = new DFA27(this);
    protected DFA37 dfa37 = new DFA37(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA47 dfa47 = new DFA47(this);
    protected DFA54 dfa54 = new DFA54(this);
    protected DFA55 dfa55 = new DFA55(this);
    protected DFA56 dfa56 = new DFA56(this);
    protected DFA57 dfa57 = new DFA57(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA59 dfa59 = new DFA59(this);
    protected DFA60 dfa60 = new DFA60(this);
    protected DFA62 dfa62 = new DFA62(this);
    protected DFA61 dfa61 = new DFA61(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA65 dfa65 = new DFA65(this);
    protected DFA66 dfa66 = new DFA66(this);
    protected DFA68 dfa68 = new DFA68(this);
    protected DFA67 dfa67 = new DFA67(this);
    protected DFA69 dfa69 = new DFA69(this);
    protected DFA70 dfa70 = new DFA70(this);
    protected DFA72 dfa72 = new DFA72(this);
    protected DFA71 dfa71 = new DFA71(this);
    protected DFA74 dfa74 = new DFA74(this);
    protected DFA76 dfa76 = new DFA76(this);
    protected DFA79 dfa79 = new DFA79(this);
    protected DFA78 dfa78 = new DFA78(this);
    protected DFA82 dfa82 = new DFA82(this);
    protected DFA83 dfa83 = new DFA83(this);
    protected DFA84 dfa84 = new DFA84(this);
    static final String DFA4_eotS =
        "\33\uffff";
    static final String DFA4_eofS =
        "\33\uffff";
    static final String DFA4_minS =
        "\1\111\32\uffff";
    static final String DFA4_maxS =
        "\1\u00c4\32\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\1\2\30\uffff";
    static final String DFA4_specialS =
        "\33\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\2\11\uffff\4\2\7\uffff\1\2\1\uffff\1\2\1\uffff\1\1\1\uffff"+
            "\1\2\20\uffff\1\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff"+
            "\1\2\6\uffff\11\2",
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
            return "632:15: ( limitClause )?";
        }
    }
    static final String DFA5_eotS =
        "\32\uffff";
    static final String DFA5_eofS =
        "\32\uffff";
    static final String DFA5_minS =
        "\1\111\31\uffff";
    static final String DFA5_maxS =
        "\1\u00c4\31\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\1\2\27\uffff";
    static final String DFA5_specialS =
        "\32\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\11\uffff\4\2\7\uffff\1\2\1\uffff\1\2\3\uffff\1\1\20\uffff"+
            "\1\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff"+
            "\11\2",
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
            return "634:15: ( TOK_DISTINCT )?";
        }
    }
    static final String DFA11_eotS =
        "\14\uffff";
    static final String DFA11_eofS =
        "\1\5\13\uffff";
    static final String DFA11_minS =
        "\1\110\13\uffff";
    static final String DFA11_maxS =
        "\1\u00c4\13\uffff";
    static final String DFA11_acceptS =
        "\1\uffff\1\1\1\2\2\uffff\1\3\6\uffff";
    static final String DFA11_specialS =
        "\14\uffff}>";
    static final String[] DFA11_transitionS = {
            "\1\5\1\uffff\2\5\24\uffff\1\2\1\uffff\1\1\6\uffff\1\2\2\5\2"+
            "\uffff\1\5\125\uffff\1\2",
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
            return "670:20: ( streamDef | aliasDef )?";
        }
    }
    static final String DFA12_eotS =
        "\13\uffff";
    static final String DFA12_eofS =
        "\1\4\12\uffff";
    static final String DFA12_minS =
        "\1\110\12\uffff";
    static final String DFA12_maxS =
        "\1\u00c4\12\uffff";
    static final String DFA12_acceptS =
        "\1\uffff\1\1\2\uffff\1\2\6\uffff";
    static final String DFA12_specialS =
        "\13\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\4\1\uffff\2\4\24\uffff\1\1\10\uffff\1\1\2\4\2\uffff\1\4"+
            "\125\uffff\1\1",
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

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "673:44: ( aliasDef )?";
        }
    }
    static final String DFA27_eotS =
        "\15\uffff";
    static final String DFA27_eofS =
        "\15\uffff";
    static final String DFA27_minS =
        "\1\u009d\3\116\3\0\6\uffff";
    static final String DFA27_maxS =
        "\1\u009f\3\116\3\0\6\uffff";
    static final String DFA27_acceptS =
        "\7\uffff\1\1\1\2\1\3\1\4\1\5\1\6";
    static final String DFA27_specialS =
        "\4\uffff\1\0\1\1\1\2\6\uffff}>";
    static final String[] DFA27_transitionS = {
            "\1\2\1\1\1\3",
            "\1\4",
            "\1\5",
            "\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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
            return "690:7: streamDefItems : ( ( streamDefItemEmit ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemAccept )? ) | ( streamDefItemEmit ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemPolicy )? ) | ( streamDefItemAccept ( TOK_SEMIC streamDefItemPolicy )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemAccept )? ( TOK_SEMIC streamDefItemEmit )? ) | ( streamDefItemPolicy ( TOK_SEMIC streamDefItemEmit )? ( TOK_SEMIC streamDefItemAccept )? ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA27_4 = input.LA(1);

                         
                        int index27_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_BEOql()) ) {s = 7;}

                        else if ( (synpred22_BEOql()) ) {s = 8;}

                         
                        input.seek(index27_4);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA27_5 = input.LA(1);

                         
                        int index27_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred25_BEOql()) ) {s = 9;}

                        else if ( (synpred28_BEOql()) ) {s = 10;}

                         
                        input.seek(index27_5);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA27_6 = input.LA(1);

                         
                        int index27_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred31_BEOql()) ) {s = 11;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index27_6);
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
    static final String DFA37_eotS =
        "\17\uffff";
    static final String DFA37_eofS =
        "\1\1\16\uffff";
    static final String DFA37_minS =
        "\1\110\16\uffff";
    static final String DFA37_maxS =
        "\1\u00c4\16\uffff";
    static final String DFA37_acceptS =
        "\1\uffff\1\2\14\uffff\1\1";
    static final String DFA37_specialS =
        "\17\uffff}>";
    static final String[] DFA37_transitionS = {
            "\4\1\11\uffff\1\16\6\uffff\1\1\3\uffff\1\1\1\uffff\1\1\6\uffff"+
            "\3\1\2\uffff\1\1\125\uffff\1\1",
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
            return "()* loopback of 807:8: ( TOK_SLASH labelIdentifier )*";
        }
    }
    static final String DFA39_eotS =
        "\31\uffff";
    static final String DFA39_eofS =
        "\31\uffff";
    static final String DFA39_minS =
        "\1\111\30\uffff";
    static final String DFA39_maxS =
        "\1\u00c4\30\uffff";
    static final String DFA39_acceptS =
        "\1\uffff\1\1\26\uffff\1\2";
    static final String DFA39_specialS =
        "\31\uffff}>";
    static final String[] DFA39_transitionS = {
            "\1\1\11\uffff\3\1\1\30\7\uffff\1\1\1\uffff\1\1\24\uffff\1\1"+
            "\6\uffff\2\1\5\uffff\6\1\2\uffff\3\1\47\uffff\1\1\6\uffff\11"+
            "\1",
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
            "",
            "",
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
            return "822:7: projectionAttributes : ( projectionList -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] projectionList ) | scopeIdentifier -> ^( PROJECTION_ATTRIBUTES[$projectionAttributes.start,$projectionAttributes.text] ^( PROJECTION[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) );";
        }
    }
    static final String DFA47_eotS =
        "\103\uffff";
    static final String DFA47_eofS =
        "\1\6\102\uffff";
    static final String DFA47_minS =
        "\1\110\2\uffff\5\0\73\uffff";
    static final String DFA47_maxS =
        "\1\160\2\uffff\5\0\73\uffff";
    static final String DFA47_acceptS =
        "\1\uffff\1\1\7\uffff\1\2\71\uffff";
    static final String DFA47_specialS =
        "\3\uffff\1\0\1\1\1\2\1\3\1\4\73\uffff}>";
    static final String[] DFA47_transitionS = {
            "\1\7\1\uffff\1\4\1\5\26\uffff\1\3\14\uffff\2\1",
            "",
            "",
            "\1\uffff",
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

    static final short[] DFA47_eot = DFA.unpackEncodedString(DFA47_eotS);
    static final short[] DFA47_eof = DFA.unpackEncodedString(DFA47_eofS);
    static final char[] DFA47_min = DFA.unpackEncodedStringToUnsignedChars(DFA47_minS);
    static final char[] DFA47_max = DFA.unpackEncodedStringToUnsignedChars(DFA47_maxS);
    static final short[] DFA47_accept = DFA.unpackEncodedString(DFA47_acceptS);
    static final short[] DFA47_special = DFA.unpackEncodedString(DFA47_specialS);
    static final short[][] DFA47_transition;

    static {
        int numStates = DFA47_transitionS.length;
        DFA47_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA47_transition[i] = DFA.unpackEncodedString(DFA47_transitionS[i]);
        }
    }

    class DFA47 extends DFA {

        public DFA47(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 47;
            this.eot = DFA47_eot;
            this.eof = DFA47_eof;
            this.min = DFA47_min;
            this.max = DFA47_max;
            this.accept = DFA47_accept;
            this.special = DFA47_special;
            this.transition = DFA47_transition;
        }
        public String getDescription() {
            return "902:15: ( sortDirection )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA47_3 = input.LA(1);

                         
                        int index47_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index47_3);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA47_4 = input.LA(1);

                         
                        int index47_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index47_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA47_5 = input.LA(1);

                         
                        int index47_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index47_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA47_6 = input.LA(1);

                         
                        int index47_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index47_6);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA47_7 = input.LA(1);

                         
                        int index47_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred63_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index47_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 47, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA54_eotS =
        "\73\uffff";
    static final String DFA54_eofS =
        "\73\uffff";
    static final String DFA54_minS =
        "\2\111\37\uffff\3\0\27\uffff";
    static final String DFA54_maxS =
        "\2\u00c4\37\uffff\3\0\27\uffff";
    static final String DFA54_acceptS =
        "\2\uffff\1\2\55\uffff\13\1";
    static final String DFA54_specialS =
        "\1\uffff\1\0\37\uffff\1\1\1\2\1\3\27\uffff}>";
    static final String[] DFA54_transitionS = {
            "\1\1\11\uffff\3\2\10\uffff\1\2\1\uffff\1\2\24\uffff\1\2\6\uffff"+
            "\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff\11\2",
            "\1\2\11\uffff\2\2\1\43\10\uffff\1\2\1\uffff\1\41\5\uffff\1"+
            "\2\16\uffff\1\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\1\67\1\70"+
            "\1\71\1\72\1\66\1\60\1\61\1\62\1\63\1\64\1\65\34\uffff\1\2\6"+
            "\uffff\10\2\1\42",
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
            return "949:7: castExpr : ( ( TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr )=> TOK_LPAREN type ( TOK_LBRACK TOK_RBRACK )* TOK_RPAREN expr -> ^( CAST_EXPRESSION[$castExpr.start,$castExpr.text] expr type ( TOK_LBRACK )* ) | ( orExpr -> orExpr ) );";
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
                        if ( (LA54_1==TOK_LPAREN||(LA54_1>=TOK_PLUS && LA54_1<=TOK_MINUS)||LA54_1==TOK_DOLLAR||LA54_1==TOK_SELECT||LA54_1==TOK_EXISTS||(LA54_1>=TOK_ABS && LA54_1<=TOK_NOT)||(LA54_1>=TOK_SUM && LA54_1<=TOK_PENDING_COUNT)||(LA54_1>=TOK_NULL && LA54_1<=TOK_FALSE)||LA54_1==HexLiteral||(LA54_1>=DIGITS && LA54_1<=TOK_EXACT_NUMERIC_LITERAL)) ) {s = 2;}

                        else if ( (LA54_1==TOK_HASH) ) {s = 33;}

                        else if ( (LA54_1==Identifier) ) {s = 34;}

                        else if ( (LA54_1==TOK_SLASH) ) {s = 35;}

                        else if ( (LA54_1==TOK_LONG) && (synpred71_BEOql())) {s = 48;}

                        else if ( (LA54_1==TOK_DOUBLE) && (synpred71_BEOql())) {s = 49;}

                        else if ( (LA54_1==TOK_CHAR) && (synpred71_BEOql())) {s = 50;}

                        else if ( (LA54_1==TOK_STRING) && (synpred71_BEOql())) {s = 51;}

                        else if ( (LA54_1==TOK_BOOLEAN) && (synpred71_BEOql())) {s = 52;}

                        else if ( (LA54_1==TOK_DATETIME) && (synpred71_BEOql())) {s = 53;}

                        else if ( (LA54_1==TOK_INT) && (synpred71_BEOql())) {s = 54;}

                        else if ( (LA54_1==TOK_CONCEPT) && (synpred71_BEOql())) {s = 55;}

                        else if ( (LA54_1==TOK_ENTITY) && (synpred71_BEOql())) {s = 56;}

                        else if ( (LA54_1==TOK_EVENT) && (synpred71_BEOql())) {s = 57;}

                        else if ( (LA54_1==TOK_OBJECT) && (synpred71_BEOql())) {s = 58;}

                         
                        input.seek(index54_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA54_33 = input.LA(1);

                         
                        int index54_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred71_BEOql()) ) {s = 58;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index54_33);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA54_34 = input.LA(1);

                         
                        int index54_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred71_BEOql()) ) {s = 58;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index54_34);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA54_35 = input.LA(1);

                         
                        int index54_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred71_BEOql()) ) {s = 58;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index54_35);
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
    static final String DFA55_eotS =
        "\27\uffff";
    static final String DFA55_eofS =
        "\1\1\26\uffff";
    static final String DFA55_minS =
        "\1\110\26\uffff";
    static final String DFA55_maxS =
        "\1\u00c4\26\uffff";
    static final String DFA55_acceptS =
        "\1\uffff\1\2\24\uffff\1\1";
    static final String DFA55_specialS =
        "\27\uffff}>";
    static final String[] DFA55_transitionS = {
            "\1\1\1\uffff\3\1\20\uffff\1\1\2\uffff\1\1\1\uffff\2\1\1\uffff"+
            "\1\1\3\uffff\10\1\1\26\60\uffff\2\1\40\uffff\1\1",
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

    static final short[] DFA55_eot = DFA.unpackEncodedString(DFA55_eotS);
    static final short[] DFA55_eof = DFA.unpackEncodedString(DFA55_eofS);
    static final char[] DFA55_min = DFA.unpackEncodedStringToUnsignedChars(DFA55_minS);
    static final char[] DFA55_max = DFA.unpackEncodedStringToUnsignedChars(DFA55_maxS);
    static final short[] DFA55_accept = DFA.unpackEncodedString(DFA55_acceptS);
    static final short[] DFA55_special = DFA.unpackEncodedString(DFA55_specialS);
    static final short[][] DFA55_transition;

    static {
        int numStates = DFA55_transitionS.length;
        DFA55_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA55_transition[i] = DFA.unpackEncodedString(DFA55_transitionS[i]);
        }
    }

    class DFA55 extends DFA {

        public DFA55(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 55;
            this.eot = DFA55_eot;
            this.eof = DFA55_eof;
            this.min = DFA55_min;
            this.max = DFA55_max;
            this.accept = DFA55_accept;
            this.special = DFA55_special;
            this.transition = DFA55_transition;
        }
        public String getDescription() {
            return "()* loopback of 958:15: (lc= TOK_OR a= andExpr -> ^( OR_EXPRESSION[$lc,$orExpr.text] $orExpr $a) )*";
        }
    }
    static final String DFA56_eotS =
        "\30\uffff";
    static final String DFA56_eofS =
        "\1\1\27\uffff";
    static final String DFA56_minS =
        "\1\110\27\uffff";
    static final String DFA56_maxS =
        "\1\u00c4\27\uffff";
    static final String DFA56_acceptS =
        "\1\uffff\1\2\25\uffff\1\1";
    static final String DFA56_specialS =
        "\30\uffff}>";
    static final String[] DFA56_transitionS = {
            "\1\1\1\uffff\3\1\20\uffff\1\1\2\uffff\1\1\1\uffff\2\1\1\uffff"+
            "\1\1\3\uffff\11\1\1\27\57\uffff\2\1\40\uffff\1\1",
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
            "",
            ""
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
            return "()* loopback of 967:15: (lc= TOK_AND a= quantifierExpr -> ^( AND_EXPRESSION[$lc,$andExpr.text] $andExpr $a) )*";
        }
    }
    static final String DFA57_eotS =
        "\32\uffff";
    static final String DFA57_eofS =
        "\1\1\31\uffff";
    static final String DFA57_minS =
        "\1\110\31\uffff";
    static final String DFA57_maxS =
        "\1\u00c4\31\uffff";
    static final String DFA57_acceptS =
        "\1\uffff\1\3\26\uffff\1\1\1\2";
    static final String DFA57_specialS =
        "\32\uffff}>";
    static final String[] DFA57_transitionS = {
            "\1\1\1\uffff\3\1\5\uffff\1\30\6\uffff\1\30\3\uffff\1\1\2\uffff"+
            "\1\1\1\uffff\2\1\1\uffff\1\1\3\uffff\12\1\3\uffff\1\31\53\uffff"+
            "\2\1\40\uffff\1\1",
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
            "",
            "",
            "",
            ""
    };

    static final short[] DFA57_eot = DFA.unpackEncodedString(DFA57_eotS);
    static final short[] DFA57_eof = DFA.unpackEncodedString(DFA57_eofS);
    static final char[] DFA57_min = DFA.unpackEncodedStringToUnsignedChars(DFA57_minS);
    static final char[] DFA57_max = DFA.unpackEncodedStringToUnsignedChars(DFA57_maxS);
    static final short[] DFA57_accept = DFA.unpackEncodedString(DFA57_acceptS);
    static final short[] DFA57_special = DFA.unpackEncodedString(DFA57_specialS);
    static final short[][] DFA57_transition;

    static {
        int numStates = DFA57_transitionS.length;
        DFA57_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA57_transition[i] = DFA.unpackEncodedString(DFA57_transitionS[i]);
        }
    }

    class DFA57 extends DFA {

        public DFA57(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 57;
            this.eot = DFA57_eot;
            this.eof = DFA57_eof;
            this.min = DFA57_min;
            this.max = DFA57_max;
            this.accept = DFA57_accept;
            this.special = DFA57_special;
            this.transition = DFA57_transition;
        }
        public String getDescription() {
            return "()* loopback of 1006:15: (e= equalityOp r1= relationalExpr -> ^( $e $equalityExpr $r1) | likeOp= TOK_LIKE pattern= unaryExpr -> ^( LIKE_CLAUSE[$likeOp, $equalityExpr.text] relationalExpr $pattern) )*";
        }
    }
    static final String DFA58_eotS =
        "\34\uffff";
    static final String DFA58_eofS =
        "\1\1\33\uffff";
    static final String DFA58_minS =
        "\1\110\33\uffff";
    static final String DFA58_maxS =
        "\1\u00c4\33\uffff";
    static final String DFA58_acceptS =
        "\1\uffff\1\3\30\uffff\1\1\1\2";
    static final String DFA58_specialS =
        "\34\uffff}>";
    static final String[] DFA58_transitionS = {
            "\1\1\1\uffff\3\1\5\uffff\1\1\4\uffff\2\32\1\1\2\32\1\uffff"+
            "\1\1\2\uffff\1\1\1\uffff\2\1\1\uffff\1\1\3\uffff\12\1\3\uffff"+
            "\1\1\1\33\52\uffff\2\1\40\uffff\1\1",
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
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA58_eot = DFA.unpackEncodedString(DFA58_eotS);
    static final short[] DFA58_eof = DFA.unpackEncodedString(DFA58_eofS);
    static final char[] DFA58_min = DFA.unpackEncodedStringToUnsignedChars(DFA58_minS);
    static final char[] DFA58_max = DFA.unpackEncodedStringToUnsignedChars(DFA58_maxS);
    static final short[] DFA58_accept = DFA.unpackEncodedString(DFA58_acceptS);
    static final short[] DFA58_special = DFA.unpackEncodedString(DFA58_specialS);
    static final short[][] DFA58_transition;

    static {
        int numStates = DFA58_transitionS.length;
        DFA58_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA58_transition[i] = DFA.unpackEncodedString(DFA58_transitionS[i]);
        }
    }

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = DFA58_eot;
            this.eof = DFA58_eof;
            this.min = DFA58_min;
            this.max = DFA58_max;
            this.accept = DFA58_accept;
            this.special = DFA58_special;
            this.transition = DFA58_transition;
        }
        public String getDescription() {
            return "()* loopback of 1026:15: (r= relationalOp a= additiveExpr -> ^( $r $relationalExpr $a) | (lc= TOK_BETWEEN a5= additiveExpr a1= TOK_AND a6= additiveExpr ) -> ^( BETWEEN_CLAUSE[$lc,$relationalExpr.text] $relationalExpr ^( RANGE_EXPRESSION[$a1,\"RANGE_EXPRESSION\"] $a5 $a6) ) )*";
        }
    }
    static final String DFA59_eotS =
        "\35\uffff";
    static final String DFA59_eofS =
        "\1\1\34\uffff";
    static final String DFA59_minS =
        "\1\110\34\uffff";
    static final String DFA59_maxS =
        "\1\u00c4\34\uffff";
    static final String DFA59_acceptS =
        "\1\uffff\1\2\32\uffff\1\1";
    static final String DFA59_specialS =
        "\35\uffff}>";
    static final String[] DFA59_transitionS = {
            "\1\1\1\uffff\3\1\4\uffff\1\34\1\1\2\34\2\uffff\5\1\1\uffff"+
            "\1\1\2\uffff\1\1\1\uffff\2\1\1\uffff\1\1\3\uffff\12\1\3\uffff"+
            "\2\1\52\uffff\2\1\40\uffff\1\1",
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
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "()* loopback of 1042:15: (a= additiveOp m= multiplicativeExpr -> ^( $a $additiveExpr $m) )*";
        }
    }
    static final String DFA60_eotS =
        "\36\uffff";
    static final String DFA60_eofS =
        "\1\1\35\uffff";
    static final String DFA60_minS =
        "\1\110\35\uffff";
    static final String DFA60_maxS =
        "\1\u00c4\35\uffff";
    static final String DFA60_acceptS =
        "\1\uffff\1\2\33\uffff\1\1";
    static final String DFA60_specialS =
        "\36\uffff}>";
    static final String[] DFA60_transitionS = {
            "\1\1\1\uffff\3\1\4\uffff\4\1\2\35\5\1\1\uffff\1\1\2\uffff\1"+
            "\1\1\uffff\2\1\1\uffff\1\1\3\uffff\12\1\3\uffff\2\1\3\uffff"+
            "\1\35\46\uffff\2\1\40\uffff\1\1",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA60_eot = DFA.unpackEncodedString(DFA60_eotS);
    static final short[] DFA60_eof = DFA.unpackEncodedString(DFA60_eofS);
    static final char[] DFA60_min = DFA.unpackEncodedStringToUnsignedChars(DFA60_minS);
    static final char[] DFA60_max = DFA.unpackEncodedStringToUnsignedChars(DFA60_maxS);
    static final short[] DFA60_accept = DFA.unpackEncodedString(DFA60_acceptS);
    static final short[] DFA60_special = DFA.unpackEncodedString(DFA60_specialS);
    static final short[][] DFA60_transition;

    static {
        int numStates = DFA60_transitionS.length;
        DFA60_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA60_transition[i] = DFA.unpackEncodedString(DFA60_transitionS[i]);
        }
    }

    class DFA60 extends DFA {

        public DFA60(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 60;
            this.eot = DFA60_eot;
            this.eof = DFA60_eof;
            this.min = DFA60_min;
            this.max = DFA60_max;
            this.accept = DFA60_accept;
            this.special = DFA60_special;
            this.transition = DFA60_transition;
        }
        public String getDescription() {
            return "()* loopback of 1054:15: (m= multiplicativeOp i= inExpr -> ^( $m $multiplicativeExpr $i) )*";
        }
    }
    static final String DFA62_eotS =
        "\37\uffff";
    static final String DFA62_eofS =
        "\1\2\36\uffff";
    static final String DFA62_minS =
        "\1\110\36\uffff";
    static final String DFA62_maxS =
        "\1\u00c4\36\uffff";
    static final String DFA62_acceptS =
        "\1\uffff\1\1\1\2\34\uffff";
    static final String DFA62_specialS =
        "\37\uffff}>";
    static final String[] DFA62_transitionS = {
            "\1\2\1\uffff\3\2\4\uffff\13\2\1\uffff\1\2\2\uffff\1\2\1\uffff"+
            "\2\2\1\uffff\1\2\2\uffff\1\1\12\2\3\uffff\2\2\3\uffff\1\2\46"+
            "\uffff\2\2\40\uffff\1\2",
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
            return "1063:15: (lc= TOK_IN (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) ) )?";
        }
    }
    static final String DFA61_eotS =
        "\61\uffff";
    static final String DFA61_eofS =
        "\61\uffff";
    static final String DFA61_minS =
        "\1\111\4\uffff\1\111\22\uffff\27\0\2\uffff";
    static final String DFA61_maxS =
        "\1\u00c4\4\uffff\1\u00c4\22\uffff\27\0\2\uffff";
    static final String DFA61_acceptS =
        "\1\uffff\1\1\56\uffff\1\2";
    static final String DFA61_specialS =
        "\30\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\2\uffff}>";
    static final String[] DFA61_transitionS = {
            "\1\5\11\uffff\3\1\10\uffff\1\1\1\uffff\1\1\24\uffff\1\1\6\uffff"+
            "\2\1\5\uffff\6\1\2\uffff\3\1\47\uffff\1\1\6\uffff\11\1",
            "",
            "",
            "",
            "",
            "\1\30\11\uffff\1\31\1\32\1\43\10\uffff\1\44\1\uffff\1\41\5"+
            "\uffff\1\1\16\uffff\1\35\6\uffff\1\33\1\34\5\uffff\4\36\1\40"+
            "\1\37\2\uffff\1\45\2\46\47\uffff\1\55\6\uffff\1\47\1\50\1\56"+
            "\1\54\1\52\1\53\2\51\1\42",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA61_eot = DFA.unpackEncodedString(DFA61_eotS);
    static final short[] DFA61_eof = DFA.unpackEncodedString(DFA61_eofS);
    static final char[] DFA61_min = DFA.unpackEncodedStringToUnsignedChars(DFA61_minS);
    static final char[] DFA61_max = DFA.unpackEncodedStringToUnsignedChars(DFA61_maxS);
    static final short[] DFA61_accept = DFA.unpackEncodedString(DFA61_acceptS);
    static final short[] DFA61_special = DFA.unpackEncodedString(DFA61_specialS);
    static final short[][] DFA61_transition;

    static {
        int numStates = DFA61_transitionS.length;
        DFA61_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA61_transition[i] = DFA.unpackEncodedString(DFA61_transitionS[i]);
        }
    }

    class DFA61 extends DFA {

        public DFA61(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 61;
            this.eot = DFA61_eot;
            this.eof = DFA61_eof;
            this.min = DFA61_min;
            this.max = DFA61_max;
            this.accept = DFA61_accept;
            this.special = DFA61_special;
            this.transition = DFA61_transition;
        }
        public String getDescription() {
            return "1065:19: (u= unaryExpr -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr $u) | TOK_LPAREN fieldList TOK_RPAREN -> ^( IN_CLAUSE[$lc,$inExpr.text] $inExpr fieldList ) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA61_24 = input.LA(1);

                         
                        int index61_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_24);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA61_25 = input.LA(1);

                         
                        int index61_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_25);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA61_26 = input.LA(1);

                         
                        int index61_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_26);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA61_27 = input.LA(1);

                         
                        int index61_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_27);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA61_28 = input.LA(1);

                         
                        int index61_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_28);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA61_29 = input.LA(1);

                         
                        int index61_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_29);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA61_30 = input.LA(1);

                         
                        int index61_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_30);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA61_31 = input.LA(1);

                         
                        int index61_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_31);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA61_32 = input.LA(1);

                         
                        int index61_32 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_32);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA61_33 = input.LA(1);

                         
                        int index61_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_33);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA61_34 = input.LA(1);

                         
                        int index61_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_34);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA61_35 = input.LA(1);

                         
                        int index61_35 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_35);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA61_36 = input.LA(1);

                         
                        int index61_36 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_36);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA61_37 = input.LA(1);

                         
                        int index61_37 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_37);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA61_38 = input.LA(1);

                         
                        int index61_38 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_38);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA61_39 = input.LA(1);

                         
                        int index61_39 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_39);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA61_40 = input.LA(1);

                         
                        int index61_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_40);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA61_41 = input.LA(1);

                         
                        int index61_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_41);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA61_42 = input.LA(1);

                         
                        int index61_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_42);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA61_43 = input.LA(1);

                         
                        int index61_43 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_43);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA61_44 = input.LA(1);

                         
                        int index61_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_44);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA61_45 = input.LA(1);

                         
                        int index61_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_45);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA61_46 = input.LA(1);

                         
                        int index61_46 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred89_BEOql()) ) {s = 1;}

                        else if ( (true) ) {s = 48;}

                         
                        input.seek(index61_46);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 61, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA64_eotS =
        "\30\uffff";
    static final String DFA64_eofS =
        "\30\uffff";
    static final String DFA64_minS =
        "\1\111\27\uffff";
    static final String DFA64_maxS =
        "\1\u00c4\27\uffff";
    static final String DFA64_acceptS =
        "\1\uffff\1\5\22\uffff\1\1\1\2\1\3\1\4";
    static final String DFA64_specialS =
        "\30\uffff}>";
    static final String[] DFA64_transitionS = {
            "\1\1\11\uffff\1\24\1\25\1\1\10\uffff\1\1\1\uffff\1\1\24\uffff"+
            "\1\1\6\uffff\1\26\1\27\5\uffff\6\1\2\uffff\3\1\47\uffff\1\1"+
            "\6\uffff\11\1",
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
            return "()* loopback of 1082:15: ( TOK_PLUS | TOK_MINUS | TOK_ABS | TOK_NOT )*";
        }
    }
    static final String DFA65_eotS =
        "\30\uffff";
    static final String DFA65_eofS =
        "\30\uffff";
    static final String DFA65_minS =
        "\1\111\27\uffff";
    static final String DFA65_maxS =
        "\1\u00c4\27\uffff";
    static final String DFA65_acceptS =
        "\1\uffff\1\1\3\uffff\1\2\22\uffff";
    static final String DFA65_specialS =
        "\30\uffff}>";
    static final String[] DFA65_transitionS = {
            "\1\5\11\uffff\2\1\1\5\10\uffff\1\5\1\uffff\1\5\24\uffff\1\5"+
            "\6\uffff\2\1\5\uffff\6\5\2\uffff\3\5\47\uffff\1\5\6\uffff\11"+
            "\5",
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
            return "1099:7: unaryExpr : ( unaryOp x= unaryExpr -> ^( unaryOp $x) | postfixExpr -> postfixExpr );";
        }
    }
    static final String DFA66_eotS =
        "\41\uffff";
    static final String DFA66_eofS =
        "\1\2\40\uffff";
    static final String DFA66_minS =
        "\1\110\40\uffff";
    static final String DFA66_maxS =
        "\1\u00c4\40\uffff";
    static final String DFA66_acceptS =
        "\1\uffff\1\1\1\2\36\uffff";
    static final String DFA66_specialS =
        "\41\uffff}>";
    static final String[] DFA66_transitionS = {
            "\1\2\1\uffff\3\2\3\uffff\14\2\1\1\1\2\2\uffff\1\2\1\uffff\2"+
            "\2\1\uffff\1\2\2\uffff\13\2\3\uffff\2\2\3\uffff\1\2\46\uffff"+
            "\2\2\40\uffff\1\2",
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

    static final short[] DFA66_eot = DFA.unpackEncodedString(DFA66_eotS);
    static final short[] DFA66_eof = DFA.unpackEncodedString(DFA66_eofS);
    static final char[] DFA66_min = DFA.unpackEncodedStringToUnsignedChars(DFA66_minS);
    static final char[] DFA66_max = DFA.unpackEncodedStringToUnsignedChars(DFA66_maxS);
    static final short[] DFA66_accept = DFA.unpackEncodedString(DFA66_acceptS);
    static final short[] DFA66_special = DFA.unpackEncodedString(DFA66_specialS);
    static final short[][] DFA66_transition;

    static {
        int numStates = DFA66_transitionS.length;
        DFA66_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA66_transition[i] = DFA.unpackEncodedString(DFA66_transitionS[i]);
        }
    }

    class DFA66 extends DFA {

        public DFA66(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 66;
            this.eot = DFA66_eot;
            this.eof = DFA66_eof;
            this.min = DFA66_min;
            this.max = DFA66_max;
            this.accept = DFA66_accept;
            this.special = DFA66_special;
            this.transition = DFA66_transition;
        }
        public String getDescription() {
            return "1114:9: (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )?";
        }
    }
    static final String DFA68_eotS =
        "\73\uffff";
    static final String DFA68_eofS =
        "\1\2\72\uffff";
    static final String DFA68_minS =
        "\1\110\1\111\21\uffff\1\126\25\uffff\2\0\20\uffff";
    static final String DFA68_maxS =
        "\2\u00c4\21\uffff\1\u00c4\25\uffff\2\0\20\uffff";
    static final String DFA68_acceptS =
        "\2\uffff\1\2\65\uffff\1\1\2\uffff";
    static final String DFA68_specialS =
        "\51\uffff\1\0\1\1\20\uffff}>";
    static final String[] DFA68_transitionS = {
            "\1\2\1\uffff\2\2\1\1\3\uffff\1\23\13\2\1\uffff\1\2\2\uffff"+
            "\1\2\1\uffff\2\2\1\uffff\1\2\2\uffff\13\2\3\uffff\2\2\3\uffff"+
            "\1\2\46\uffff\2\2\40\uffff\1\2",
            "\1\2\11\uffff\4\2\7\uffff\1\2\1\uffff\1\51\24\uffff\1\2\6"+
            "\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff\10\2"+
            "\1\52",
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
            "\1\2\11\uffff\1\70\143\uffff\1\70",
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
            return "()* loopback of 1115:9: ( ( (p= postfixOp li= labelIdentifier ) -> ^( $p $postfixExpr $li) ) (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )? )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA68_41 = input.LA(1);

                         
                        int index68_41 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_BEOql()) ) {s = 56;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index68_41);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA68_42 = input.LA(1);

                         
                        int index68_42 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_BEOql()) ) {s = 56;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index68_42);
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
    static final String DFA67_eotS =
        "\41\uffff";
    static final String DFA67_eofS =
        "\1\2\40\uffff";
    static final String DFA67_minS =
        "\1\110\40\uffff";
    static final String DFA67_maxS =
        "\1\u00c4\40\uffff";
    static final String DFA67_acceptS =
        "\1\uffff\1\1\1\2\36\uffff";
    static final String DFA67_specialS =
        "\41\uffff}>";
    static final String[] DFA67_transitionS = {
            "\1\2\1\uffff\3\2\3\uffff\14\2\1\1\1\2\2\uffff\1\2\1\uffff\2"+
            "\2\1\uffff\1\2\2\uffff\13\2\3\uffff\2\2\3\uffff\1\2\46\uffff"+
            "\2\2\40\uffff\1\2",
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

    static final short[] DFA67_eot = DFA.unpackEncodedString(DFA67_eotS);
    static final short[] DFA67_eof = DFA.unpackEncodedString(DFA67_eofS);
    static final char[] DFA67_min = DFA.unpackEncodedStringToUnsignedChars(DFA67_minS);
    static final char[] DFA67_max = DFA.unpackEncodedStringToUnsignedChars(DFA67_maxS);
    static final short[] DFA67_accept = DFA.unpackEncodedString(DFA67_acceptS);
    static final short[] DFA67_special = DFA.unpackEncodedString(DFA67_specialS);
    static final short[][] DFA67_transition;

    static {
        int numStates = DFA67_transitionS.length;
        DFA67_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA67_transition[i] = DFA.unpackEncodedString(DFA67_transitionS[i]);
        }
    }

    class DFA67 extends DFA {

        public DFA67(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 67;
            this.eot = DFA67_eot;
            this.eof = DFA67_eof;
            this.min = DFA67_min;
            this.max = DFA67_max;
            this.accept = DFA67_accept;
            this.special = DFA67_special;
            this.transition = DFA67_transition;
        }
        public String getDescription() {
            return "1125:13: (a= arrayIndexExpr -> ^( ARRAY_INDEX[$a.start,$a.text] $postfixExpr $a) )?";
        }
    }
    static final String DFA69_eotS =
        "\70\uffff";
    static final String DFA69_eofS =
        "\1\2\67\uffff";
    static final String DFA69_minS =
        "\1\110\1\111\66\uffff";
    static final String DFA69_maxS =
        "\2\u00c4\66\uffff";
    static final String DFA69_acceptS =
        "\2\uffff\1\2\20\uffff\1\1\44\uffff";
    static final String DFA69_specialS =
        "\70\uffff}>";
    static final String[] DFA69_transitionS = {
            "\1\2\1\uffff\2\2\1\1\3\uffff\1\23\13\2\1\uffff\1\2\2\uffff"+
            "\1\2\1\uffff\2\2\1\uffff\1\2\2\uffff\13\2\3\uffff\2\2\3\uffff"+
            "\1\2\46\uffff\2\2\40\uffff\1\2",
            "\1\2\11\uffff\3\2\1\23\7\uffff\1\2\1\uffff\1\2\24\uffff\1"+
            "\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff"+
            "\11\2",
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

    static final short[] DFA69_eot = DFA.unpackEncodedString(DFA69_eotS);
    static final short[] DFA69_eof = DFA.unpackEncodedString(DFA69_eofS);
    static final char[] DFA69_min = DFA.unpackEncodedStringToUnsignedChars(DFA69_minS);
    static final char[] DFA69_max = DFA.unpackEncodedStringToUnsignedChars(DFA69_maxS);
    static final short[] DFA69_accept = DFA.unpackEncodedString(DFA69_acceptS);
    static final short[] DFA69_special = DFA.unpackEncodedString(DFA69_specialS);
    static final short[][] DFA69_transition;

    static {
        int numStates = DFA69_transitionS.length;
        DFA69_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA69_transition[i] = DFA.unpackEncodedString(DFA69_transitionS[i]);
        }
    }

    class DFA69 extends DFA {

        public DFA69(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 69;
            this.eot = DFA69_eot;
            this.eof = DFA69_eof;
            this.min = DFA69_min;
            this.max = DFA69_max;
            this.accept = DFA69_accept;
            this.special = DFA69_special;
            this.transition = DFA69_transition;
        }
        public String getDescription() {
            return "1127:12: (p= postfixOp si= scopeIdentifier -> ^( $p $postfixExpr $si) )?";
        }
    }
    static final String DFA70_eotS =
        "\43\uffff";
    static final String DFA70_eofS =
        "\43\uffff";
    static final String DFA70_minS =
        "\1\135\1\0\41\uffff";
    static final String DFA70_maxS =
        "\1\135\1\0\41\uffff";
    static final String DFA70_acceptS =
        "\41\uffff\1\1\1\2";
    static final String DFA70_specialS =
        "\1\uffff\1\0\41\uffff}>";
    static final String[] DFA70_transitionS = {
            "\1\1",
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

    static final short[] DFA70_eot = DFA.unpackEncodedString(DFA70_eotS);
    static final short[] DFA70_eof = DFA.unpackEncodedString(DFA70_eofS);
    static final char[] DFA70_min = DFA.unpackEncodedStringToUnsignedChars(DFA70_minS);
    static final char[] DFA70_max = DFA.unpackEncodedStringToUnsignedChars(DFA70_maxS);
    static final short[] DFA70_accept = DFA.unpackEncodedString(DFA70_acceptS);
    static final short[] DFA70_special = DFA.unpackEncodedString(DFA70_specialS);
    static final short[][] DFA70_transition;

    static {
        int numStates = DFA70_transitionS.length;
        DFA70_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA70_transition[i] = DFA.unpackEncodedString(DFA70_transitionS[i]);
        }
    }

    class DFA70 extends DFA {

        public DFA70(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 70;
            this.eot = DFA70_eot;
            this.eof = DFA70_eof;
            this.min = DFA70_min;
            this.max = DFA70_max;
            this.accept = DFA70_accept;
            this.special = DFA70_special;
            this.transition = DFA70_transition;
        }
        public String getDescription() {
            return "1139:15: ( | -> ^( INDEX_SINGLE $index) )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA70_1 = input.LA(1);

                         
                        int index70_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred104_BEOql()) ) {s = 33;}

                        else if ( (true) ) {s = 34;}

                         
                        input.seek(index70_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 70, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA72_eotS =
        "\116\uffff";
    static final String DFA72_eofS =
        "\7\uffff\1\56\106\uffff";
    static final String DFA72_minS =
        "\2\111\4\uffff\1\144\1\110\44\uffff\2\0\40\uffff";
    static final String DFA72_maxS =
        "\2\u00c4\4\uffff\1\u00ab\1\u00c4\44\uffff\2\0\40\uffff";
    static final String DFA72_acceptS =
        "\2\uffff\1\2\1\3\4\uffff\1\4\1\6\1\7\11\uffff\1\1\26\uffff\1\10"+
        "\2\uffff\1\5\37\uffff";
    static final String DFA72_specialS =
        "\54\uffff\1\0\1\1\40\uffff}>";
    static final String[] DFA72_transitionS = {
            "\1\1\13\uffff\1\10\10\uffff\1\11\1\uffff\1\6\24\uffff\1\2\15"+
            "\uffff\6\3\2\uffff\3\12\47\uffff\1\12\6\uffff\10\12\1\7",
            "\1\24\11\uffff\3\24\10\uffff\1\24\1\uffff\1\24\5\uffff\1\53"+
            "\16\uffff\1\24\6\uffff\2\24\5\uffff\6\24\2\uffff\3\24\47\uffff"+
            "\1\24\6\uffff\11\24",
            "",
            "",
            "",
            "",
            "\3\54\1\uffff\30\54\2\uffff\6\54\1\uffff\6\54\1\uffff\13\54"+
            "\1\uffff\1\54\1\uffff\16\54",
            "\1\56\1\55\3\56\3\uffff\16\56\2\uffff\1\56\1\uffff\2\56\1"+
            "\uffff\1\56\2\uffff\13\56\3\uffff\2\56\3\uffff\1\56\46\uffff"+
            "\2\56\40\uffff\1\56",
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
            return "1165:15: ( blockExpr -> blockExpr | collectionExpr -> collectionExpr | aggregateExpr -> aggregateExpr | pathFunctionExpr -> pathFunctionExpr | labelIdentifier ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier ) | bindVar -> bindVar | literal -> literal | TOK_LPAREN selectExpr TOK_RPAREN -> selectExpr )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA72_44 = input.LA(1);

                         
                        int index72_44 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred108_BEOql()) ) {s = 8;}

                        else if ( (synpred110_BEOql()) ) {s = 46;}

                         
                        input.seek(index72_44);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA72_45 = input.LA(1);

                         
                        int index72_45 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred108_BEOql()) ) {s = 8;}

                        else if ( (synpred110_BEOql()) ) {s = 46;}

                         
                        input.seek(index72_45);
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
    static final String DFA71_eotS =
        "\42\uffff";
    static final String DFA71_eofS =
        "\1\2\41\uffff";
    static final String DFA71_minS =
        "\1\110\41\uffff";
    static final String DFA71_maxS =
        "\1\u00c4\41\uffff";
    static final String DFA71_acceptS =
        "\1\uffff\1\1\1\2\37\uffff";
    static final String DFA71_specialS =
        "\42\uffff}>";
    static final String[] DFA71_transitionS = {
            "\1\2\1\1\3\2\3\uffff\16\2\2\uffff\1\2\1\uffff\2\2\1\uffff\1"+
            "\2\2\uffff\13\2\3\uffff\2\2\3\uffff\1\2\46\uffff\2\2\40\uffff"+
            "\1\2",
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

    static final short[] DFA71_eot = DFA.unpackEncodedString(DFA71_eotS);
    static final short[] DFA71_eof = DFA.unpackEncodedString(DFA71_eofS);
    static final char[] DFA71_min = DFA.unpackEncodedStringToUnsignedChars(DFA71_minS);
    static final char[] DFA71_max = DFA.unpackEncodedStringToUnsignedChars(DFA71_maxS);
    static final short[] DFA71_accept = DFA.unpackEncodedString(DFA71_acceptS);
    static final short[] DFA71_special = DFA.unpackEncodedString(DFA71_specialS);
    static final short[][] DFA71_transition;

    static {
        int numStates = DFA71_transitionS.length;
        DFA71_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA71_transition[i] = DFA.unpackEncodedString(DFA71_transitionS[i]);
        }
    }

    class DFA71 extends DFA {

        public DFA71(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 71;
            this.eot = DFA71_eot;
            this.eof = DFA71_eof;
            this.min = DFA71_min;
            this.max = DFA71_max;
            this.accept = DFA71_accept;
            this.special = DFA71_special;
            this.transition = DFA71_transition;
        }
        public String getDescription() {
            return "1174:19: ( argList -> ^( FUNCTION_EXPRESSION[$primaryExpr.start,$primaryExpr.text] labelIdentifier argList ) | -> labelIdentifier )";
        }
    }
    static final String DFA74_eotS =
        "\31\uffff";
    static final String DFA74_eofS =
        "\31\uffff";
    static final String DFA74_minS =
        "\1\110\30\uffff";
    static final String DFA74_maxS =
        "\1\u00c4\30\uffff";
    static final String DFA74_acceptS =
        "\1\uffff\1\1\26\uffff\1\2";
    static final String DFA74_specialS =
        "\31\uffff}>";
    static final String[] DFA74_transitionS = {
            "\1\30\1\1\11\uffff\3\1\10\uffff\1\1\1\uffff\1\1\24\uffff\1"+
            "\1\6\uffff\2\1\5\uffff\6\1\2\uffff\3\1\47\uffff\1\1\6\uffff"+
            "\11\1",
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
            "",
            "",
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
            return "1195:15: ( expr ( TOK_COMMA expr )* )?";
        }
    }
    static final String DFA76_eotS =
        "\31\uffff";
    static final String DFA76_eofS =
        "\31\uffff";
    static final String DFA76_minS =
        "\1\111\30\uffff";
    static final String DFA76_maxS =
        "\1\u00c4\30\uffff";
    static final String DFA76_acceptS =
        "\1\uffff\1\1\1\2\26\uffff";
    static final String DFA76_specialS =
        "\31\uffff}>";
    static final String[] DFA76_transitionS = {
            "\1\2\11\uffff\3\2\10\uffff\1\2\1\uffff\1\2\3\uffff\1\1\20\uffff"+
            "\1\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff"+
            "\11\2",
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
            "",
            "",
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
            return "1249:26: ( TOK_DISTINCT )?";
        }
    }
    static final String DFA79_eotS =
        "\32\uffff";
    static final String DFA79_eofS =
        "\32\uffff";
    static final String DFA79_minS =
        "\1\111\31\uffff";
    static final String DFA79_maxS =
        "\1\u00c4\31\uffff";
    static final String DFA79_acceptS =
        "\1\uffff\1\1\27\uffff\1\2";
    static final String DFA79_specialS =
        "\32\uffff}>";
    static final String[] DFA79_transitionS = {
            "\1\1\11\uffff\3\1\1\31\7\uffff\1\1\1\uffff\1\1\3\uffff\1\1"+
            "\20\uffff\1\1\6\uffff\2\1\5\uffff\6\1\2\uffff\3\1\47\uffff\1"+
            "\1\6\uffff\11\1",
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
            "",
            "",
            "",
            ""
    };

    static final short[] DFA79_eot = DFA.unpackEncodedString(DFA79_eotS);
    static final short[] DFA79_eof = DFA.unpackEncodedString(DFA79_eofS);
    static final char[] DFA79_min = DFA.unpackEncodedStringToUnsignedChars(DFA79_minS);
    static final char[] DFA79_max = DFA.unpackEncodedStringToUnsignedChars(DFA79_maxS);
    static final short[] DFA79_accept = DFA.unpackEncodedString(DFA79_acceptS);
    static final short[] DFA79_special = DFA.unpackEncodedString(DFA79_specialS);
    static final short[][] DFA79_transition;

    static {
        int numStates = DFA79_transitionS.length;
        DFA79_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA79_transition[i] = DFA.unpackEncodedString(DFA79_transitionS[i]);
        }
    }

    class DFA79 extends DFA {

        public DFA79(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 79;
            this.eot = DFA79_eot;
            this.eof = DFA79_eof;
            this.min = DFA79_min;
            this.max = DFA79_max;
            this.accept = DFA79_accept;
            this.special = DFA79_special;
            this.transition = DFA79_transition;
        }
        public String getDescription() {
            return "1256:15: ( ( TOK_DISTINCT )? a= expr -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ( TOK_DISTINCT )? ^( ARG_LIST[$expr.start,$expr.text] expr ) ) | scopeIdentifier -> ^( FUNCTION_EXPRESSION[$aggregateExpr.start,$aggregateExpr.text] TOK_COUNT ^( ARG_LIST[$scopeIdentifier.start,$scopeIdentifier.text] scopeIdentifier ) ) )";
        }
    }
    static final String DFA78_eotS =
        "\31\uffff";
    static final String DFA78_eofS =
        "\31\uffff";
    static final String DFA78_minS =
        "\1\111\30\uffff";
    static final String DFA78_maxS =
        "\1\u00c4\30\uffff";
    static final String DFA78_acceptS =
        "\1\uffff\1\1\1\2\26\uffff";
    static final String DFA78_specialS =
        "\31\uffff}>";
    static final String[] DFA78_transitionS = {
            "\1\2\11\uffff\3\2\10\uffff\1\2\1\uffff\1\2\3\uffff\1\1\20\uffff"+
            "\1\2\6\uffff\2\2\5\uffff\6\2\2\uffff\3\2\47\uffff\1\2\6\uffff"+
            "\11\2",
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
            "",
            "",
            ""
    };

    static final short[] DFA78_eot = DFA.unpackEncodedString(DFA78_eotS);
    static final short[] DFA78_eof = DFA.unpackEncodedString(DFA78_eofS);
    static final char[] DFA78_min = DFA.unpackEncodedStringToUnsignedChars(DFA78_minS);
    static final char[] DFA78_max = DFA.unpackEncodedStringToUnsignedChars(DFA78_maxS);
    static final short[] DFA78_accept = DFA.unpackEncodedString(DFA78_acceptS);
    static final short[] DFA78_special = DFA.unpackEncodedString(DFA78_specialS);
    static final short[][] DFA78_transition;

    static {
        int numStates = DFA78_transitionS.length;
        DFA78_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA78_transition[i] = DFA.unpackEncodedString(DFA78_transitionS[i]);
        }
    }

    class DFA78 extends DFA {

        public DFA78(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 78;
            this.eot = DFA78_eot;
            this.eof = DFA78_eof;
            this.min = DFA78_min;
            this.max = DFA78_max;
            this.accept = DFA78_accept;
            this.special = DFA78_special;
            this.transition = DFA78_transition;
        }
        public String getDescription() {
            return "1260:17: ( TOK_DISTINCT )?";
        }
    }
    static final String DFA82_eotS =
        "\37\uffff";
    static final String DFA82_eofS =
        "\1\2\36\uffff";
    static final String DFA82_minS =
        "\1\110\1\111\5\uffff\27\0\1\uffff";
    static final String DFA82_maxS =
        "\1\156\1\u00c4\5\uffff\27\0\1\uffff";
    static final String DFA82_acceptS =
        "\2\uffff\1\2\33\uffff\1\1";
    static final String DFA82_specialS =
        "\7\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\uffff}>";
    static final String[] DFA82_transitionS = {
            "\1\2\1\uffff\1\1\1\2\41\uffff\2\2",
            "\1\7\11\uffff\1\10\1\11\1\22\10\uffff\1\23\1\uffff\1\20\24"+
            "\uffff\1\14\6\uffff\1\12\1\13\5\uffff\4\15\1\17\1\16\2\uffff"+
            "\1\24\2\25\47\uffff\1\34\6\uffff\1\26\1\27\1\35\1\33\1\31\1"+
            "\32\2\30\1\21",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA82_eot = DFA.unpackEncodedString(DFA82_eotS);
    static final short[] DFA82_eof = DFA.unpackEncodedString(DFA82_eofS);
    static final char[] DFA82_min = DFA.unpackEncodedStringToUnsignedChars(DFA82_minS);
    static final char[] DFA82_max = DFA.unpackEncodedStringToUnsignedChars(DFA82_maxS);
    static final short[] DFA82_accept = DFA.unpackEncodedString(DFA82_acceptS);
    static final short[] DFA82_special = DFA.unpackEncodedString(DFA82_specialS);
    static final short[][] DFA82_transition;

    static {
        int numStates = DFA82_transitionS.length;
        DFA82_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA82_transition[i] = DFA.unpackEncodedString(DFA82_transitionS[i]);
        }
    }

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = DFA82_eot;
            this.eof = DFA82_eof;
            this.min = DFA82_min;
            this.max = DFA82_max;
            this.accept = DFA82_accept;
            this.special = DFA82_special;
            this.transition = DFA82_transition;
        }
        public String getDescription() {
            return "()* loopback of 1289:15: ( TOK_COMMA field )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA82_7 = input.LA(1);

                         
                        int index82_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA82_8 = input.LA(1);

                         
                        int index82_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA82_9 = input.LA(1);

                         
                        int index82_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA82_10 = input.LA(1);

                         
                        int index82_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_10);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA82_11 = input.LA(1);

                         
                        int index82_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_11);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA82_12 = input.LA(1);

                         
                        int index82_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_12);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA82_13 = input.LA(1);

                         
                        int index82_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_13);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA82_14 = input.LA(1);

                         
                        int index82_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_14);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA82_15 = input.LA(1);

                         
                        int index82_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_15);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA82_16 = input.LA(1);

                         
                        int index82_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_16);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA82_17 = input.LA(1);

                         
                        int index82_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_17);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA82_18 = input.LA(1);

                         
                        int index82_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_18);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA82_19 = input.LA(1);

                         
                        int index82_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_19);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA82_20 = input.LA(1);

                         
                        int index82_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_20);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA82_21 = input.LA(1);

                         
                        int index82_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_21);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA82_22 = input.LA(1);

                         
                        int index82_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_22);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA82_23 = input.LA(1);

                         
                        int index82_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_23);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA82_24 = input.LA(1);

                         
                        int index82_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_24);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA82_25 = input.LA(1);

                         
                        int index82_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_25);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA82_26 = input.LA(1);

                         
                        int index82_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_26);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA82_27 = input.LA(1);

                         
                        int index82_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_27);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA82_28 = input.LA(1);

                         
                        int index82_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_28);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA82_29 = input.LA(1);

                         
                        int index82_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred127_BEOql()) ) {s = 30;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index82_29);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 82, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA83_eotS =
        "\17\uffff";
    static final String DFA83_eofS =
        "\17\uffff";
    static final String DFA83_minS =
        "\1\125\16\uffff";
    static final String DFA83_maxS =
        "\1\u00c4\16\uffff";
    static final String DFA83_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\2"+
        "\uffff";
    static final String DFA83_specialS =
        "\17\uffff}>";
    static final String[] DFA83_transitionS = {
            "\1\14\12\uffff\1\14\55\uffff\1\10\1\11\1\12\1\13\1\7\1\1\1"+
            "\2\1\3\1\4\1\5\1\6\53\uffff\1\14",
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

    static final short[] DFA83_eot = DFA.unpackEncodedString(DFA83_eotS);
    static final short[] DFA83_eof = DFA.unpackEncodedString(DFA83_eofS);
    static final char[] DFA83_min = DFA.unpackEncodedStringToUnsignedChars(DFA83_minS);
    static final char[] DFA83_max = DFA.unpackEncodedStringToUnsignedChars(DFA83_maxS);
    static final short[] DFA83_accept = DFA.unpackEncodedString(DFA83_acceptS);
    static final short[] DFA83_special = DFA.unpackEncodedString(DFA83_specialS);
    static final short[][] DFA83_transition;

    static {
        int numStates = DFA83_transitionS.length;
        DFA83_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA83_transition[i] = DFA.unpackEncodedString(DFA83_transitionS[i]);
        }
    }

    class DFA83 extends DFA {

        public DFA83(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 83;
            this.eot = DFA83_eot;
            this.eof = DFA83_eof;
            this.min = DFA83_min;
            this.max = DFA83_max;
            this.accept = DFA83_accept;
            this.special = DFA83_special;
            this.transition = DFA83_transition;
        }
        public String getDescription() {
            return "1308:7: type : ( TOK_LONG | TOK_DOUBLE | TOK_CHAR | TOK_STRING | TOK_BOOLEAN | TOK_DATETIME | TOK_INT | TOK_CONCEPT | TOK_ENTITY | TOK_EVENT | TOK_OBJECT | pathExpr -> ^( PATH_EXPRESSION[$pathExpr.start,$pathExpr.text] pathExpr ) );";
        }
    }
    static final String DFA84_eotS =
        "\13\uffff";
    static final String DFA84_eofS =
        "\13\uffff";
    static final String DFA84_minS =
        "\1\u008b\12\uffff";
    static final String DFA84_maxS =
        "\1\u00c3\12\uffff";
    static final String DFA84_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12";
    static final String DFA84_specialS =
        "\13\uffff}>";
    static final String[] DFA84_transitionS = {
            "\1\1\2\2\47\uffff\1\11\6\uffff\1\3\1\4\1\12\1\10\1\6\1\7\2"+
            "\5",
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

    static final short[] DFA84_eot = DFA.unpackEncodedString(DFA84_eotS);
    static final short[] DFA84_eof = DFA.unpackEncodedString(DFA84_eofS);
    static final char[] DFA84_min = DFA.unpackEncodedStringToUnsignedChars(DFA84_minS);
    static final char[] DFA84_max = DFA.unpackEncodedStringToUnsignedChars(DFA84_maxS);
    static final short[] DFA84_accept = DFA.unpackEncodedString(DFA84_acceptS);
    static final short[] DFA84_special = DFA.unpackEncodedString(DFA84_specialS);
    static final short[][] DFA84_transition;

    static {
        int numStates = DFA84_transitionS.length;
        DFA84_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA84_transition[i] = DFA.unpackEncodedString(DFA84_transitionS[i]);
        }
    }

    class DFA84 extends DFA {

        public DFA84(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 84;
            this.eot = DFA84_eot;
            this.eof = DFA84_eof;
            this.min = DFA84_min;
            this.max = DFA84_max;
            this.accept = DFA84_accept;
            this.special = DFA84_special;
            this.transition = DFA84_transition;
        }
        public String getDescription() {
            return "1325:7: literal : ( objectLiteral -> ^( NULL_LITERAL[$literal.start,$literal.text] ) | booleanLiteral -> ^( BOOLEAN_LITERAL[$literal.start,$literal.text] booleanLiteral ) | DIGITS -> ^( NUMBER_LITERAL[$literal.start,$literal.text] DIGITS ) | decimalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] decimalLiteral ) | doubleLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] doubleLiteral ) | charLiteral -> ^( CHAR_LITERAL[$literal.start,$literal.text] charLiteral ) | stringLiteral -> ^( STRING_LITERAL[$literal.start,$literal.text] stringLiteral ) | datetimeLiteral -> ^( DATETIME_LITERAL[$literal.start,$literal.text] datetimeLiteral ) | hexLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] hexLiteral ) | octalLiteral -> ^( NUMBER_LITERAL[$literal.start,$literal.text] octalLiteral ) );";
        }
    }
 

    public static final BitSet FOLLOW_selectExpr_in_query7936 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_query7940 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_query7944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteExpr_in_query7967 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_query7971 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_query7975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_query7998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_SELECT_in_selectExpr8043 = new BitSet(new long[]{0x0000000000000000L,0x3020001540780200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_limitClause_in_selectExpr8061 = new BitSet(new long[]{0x0000000000000000L,0x3020001540780200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_DISTINCT_in_selectExpr8081 = new BitSet(new long[]{0x0000000000000000L,0x3020001540780200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_projectionAttributes_in_selectExpr8100 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr8117 = new BitSet(new long[]{0x0000000000000002L,0x00004C0000000000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr8135 = new BitSet(new long[]{0x0000000000000002L,0x0000480000000000L});
    public static final BitSet FOLLOW_groupClause_in_selectExpr8156 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
    public static final BitSet FOLLOW_orderClause_in_selectExpr8177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DELETE_in_deleteExpr8265 = new BitSet(new long[]{0x0000000000000000L,0x0000002000400000L});
    public static final BitSet FOLLOW_TOK_STAR_in_deleteExpr8283 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_fromClause_in_deleteExpr8305 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_whereClause_in_deleteExpr8321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_FROM_in_fromClause8409 = new BitSet(new long[]{0x0000000000000000L,0x0000000100200200L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_iteratorDef_in_fromClause8411 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_fromClause8447 = new BitSet(new long[]{0x0000000000000000L,0x0000000100200200L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_iteratorDef_in_fromClause8467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_pathExpr_in_iteratorDef8554 = new BitSet(new long[]{0x0000000000000002L,0x0000020500000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_streamDef_in_iteratorDef8557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aliasDef_in_iteratorDef8561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_iteratorDef8621 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectExpr_in_iteratorDef8623 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_iteratorDef8625 = new BitSet(new long[]{0x0000000000000002L,0x0000020100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_aliasDef_in_iteratorDef8627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_AS_in_aliasDef8696 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_aliasDef8699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LCURLY_in_streamDef8761 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000E0000000L});
    public static final BitSet FOLLOW_streamDefItems_in_streamDef8763 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_TOK_RCURLY_in_streamDef8765 = new BitSet(new long[]{0x0000000000000000L,0x0000020100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_aliasDef_in_streamDef8767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems8833 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems8839 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8844 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems8847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems8871 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems8877 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8882 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems8885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems8913 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8916 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems8919 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems8927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems8955 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems8961 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems8966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems8969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems8997 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems9000 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems9003 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems9008 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems9011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_streamDefItems9039 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems9042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_streamDefItems9045 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_streamDefItems9050 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_streamDefItems9053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_EMIT_in_streamDefItemEmit9093 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TOK_COLON_in_streamDefItemEmit9095 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000006000000000L});
    public static final BitSet FOLLOW_TOK_NEW_in_streamDefItemEmit9119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DEAD_in_streamDefItemEmit9146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ACCEPT_in_streamDefItemAccept9204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TOK_COLON_in_streamDefItemAccept9206 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_TOK_NEW_in_streamDefItemAccept9230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ALL_in_streamDefItemAccept9257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_POLICY_in_streamDefItemPolicy9315 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TOK_COLON_in_streamDefItemPolicy9317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_TOK_MAINTAIN_in_streamDefItemPolicy9319 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_windowDef_in_streamDefItemPolicy9329 = new BitSet(new long[]{0x0000000000000002L,0x0000140000000000L});
    public static final BitSet FOLLOW_whereClause_in_streamDefItemPolicy9339 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_policyBy_in_streamDefItemPolicy9350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_BY_in_policyBy9445 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_policyBy9447 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_policyBy9450 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_policyBy9452 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_timeWindowDef_in_windowDef9511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tumblingWindowDef_in_windowDef9531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_slidingWindowDef_in_windowDef9551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LAST_in_timeWindowDef9654 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_DIGITS_in_timeWindowDef9656 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000F8000000000L});
    public static final BitSet FOLLOW_timeUnit_in_timeWindowDef9658 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000600000000L});
    public static final BitSet FOLLOW_usingClause_in_timeWindowDef9669 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_windowPurgeExpr_in_timeWindowDef9682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_timeUnit0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_USING_in_usingClause9811 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_usingClause9813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LAST_in_slidingWindowDef9891 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_DIGITS_in_slidingWindowDef9893 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_TOK_SLIDING_in_slidingWindowDef9895 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_windowPurgeExpr_in_slidingWindowDef9906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LAST_in_tumblingWindowDef9992 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_DIGITS_in_tumblingWindowDef9994 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_TUMBLING_in_tumblingWindowDef9996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_PURGE_in_windowPurgeExpr10069 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_TOK_FIRST_in_windowPurgeExpr10071 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_windowPurgeExpr10075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_TOK_WHEN_in_windowPurgeExpr10077 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_windowPurgeExpr10081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelIdentifier_in_pathExpr10185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_SLASH_in_pathExpr10197 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_pathExpr10199 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_TOK_SLASH_in_pathExpr10218 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_pathExpr10220 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_TOK_WHERE_in_whereClause10285 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_whereClause10287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_projectionList_in_projectionAttributes10343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_scopeIdentifier_in_projectionAttributes10366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_projection_in_projectionList10417 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_projectionList10453 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_projection_in_projectionList10475 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_expr_in_projection10578 = new BitSet(new long[]{0x0000000000000002L,0x0000020100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_TOK_AS_in_projection10652 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_projection10685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_extExpr10889 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_TOK_DOT_in_extExpr10926 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_extExpr10930 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_TOK_GROUP_in_groupClause11008 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_TOK_BY_in_groupClause11010 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_groupColumn_in_groupClause11012 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_groupClause11048 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_groupColumn_in_groupClause11050 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000400L});
    public static final BitSet FOLLOW_havingClause_in_groupClause11083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_HAVING_in_havingClause11146 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_havingClause11149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fieldList_in_groupColumn11203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ORDER_in_orderClause11250 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_TOK_BY_in_orderClause11252 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_sortCriterion_in_orderClause11268 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_orderClause11304 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_sortCriterion_in_orderClause11306 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_expr_in_sortCriterion11399 = new BitSet(new long[]{0x0000000000000000L,0x0001800400000000L});
    public static final BitSet FOLLOW_sortDirection_in_sortCriterion11415 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_limitClause_in_sortCriterion11432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ASC_in_sortDirection11509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DESC_in_sortDirection11533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LCURLY_in_limitClause11597 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_TOK_LIMIT_in_limitClause11599 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_TOK_COLON_in_limitClause11601 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_limitDef_in_limitClause11603 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_TOK_RCURLY_in_limitClause11606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_limitFirst_in_limitDef11670 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_limitOffset_in_limitDef11675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_FIRST_in_limitFirst11729 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_DIGITS_in_limitFirst11733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_FIRST_in_limitFirst11752 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_bindVar_in_limitFirst11754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_OFFSET_in_limitOffset11790 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_DIGITS_in_limitOffset11794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_OFFSET_in_limitOffset11813 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_bindVar_in_limitOffset11815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_castExpr_in_expr11867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_castExpr11926 = new BitSet(new long[]{0x0000000000000000L,0x0000000100200000L,0x0000000001FFC000L,0x0000000000000010L});
    public static final BitSet FOLLOW_type_in_castExpr11928 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000100L});
    public static final BitSet FOLLOW_TOK_LBRACK_in_castExpr11931 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_TOK_RBRACK_in_castExpr11933 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_castExpr11937 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_castExpr11939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_orExpr_in_castExpr11960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_andExpr_in_orExpr12002 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_TOK_OR_in_orExpr12046 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_andExpr_in_orExpr12068 = new BitSet(new long[]{0x0000000000000002L,0x0002000000000000L});
    public static final BitSet FOLLOW_quantifierExpr_in_andExpr12149 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_TOK_AND_in_andExpr12192 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_quantifierExpr_in_andExpr12214 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_equalityExpr_in_quantifierExpr12323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_equalityOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr12456 = new BitSet(new long[]{0x0000000000000002L,0x0040000002040000L});
    public static final BitSet FOLLOW_equalityOp_in_equalityExpr12500 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_relationalExpr_in_equalityExpr12542 = new BitSet(new long[]{0x0000000000000002L,0x0040000002040000L});
    public static final BitSet FOLLOW_TOK_LIKE_in_equalityExpr12603 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_unaryExpr_in_equalityExpr12609 = new BitSet(new long[]{0x0000000000000002L,0x0040000002040000L});
    public static final BitSet FOLLOW_set_in_relationalOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr12758 = new BitSet(new long[]{0x0000000000000002L,0x008000000D800000L});
    public static final BitSet FOLLOW_relationalOp_in_relationalExpr12802 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr12806 = new BitSet(new long[]{0x0000000000000002L,0x008000000D800000L});
    public static final BitSet FOLLOW_TOK_BETWEEN_in_relationalExpr12856 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr12861 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_TOK_AND_in_relationalExpr12865 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_additiveExpr_in_relationalExpr12869 = new BitSet(new long[]{0x0000000000000002L,0x008000000D800000L});
    public static final BitSet FOLLOW_set_in_additiveOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr13058 = new BitSet(new long[]{0x0000000000000002L,0x00000000001A0000L});
    public static final BitSet FOLLOW_additiveOp_in_additiveExpr13102 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_multiplicativeExpr_in_additiveExpr13106 = new BitSet(new long[]{0x0000000000000002L,0x00000000001A0000L});
    public static final BitSet FOLLOW_set_in_multiplicativeOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_multiplicativeExpr13237 = new BitSet(new long[]{0x0000000000000002L,0x0800000000600000L});
    public static final BitSet FOLLOW_multiplicativeOp_in_multiplicativeExpr13281 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_inExpr_in_multiplicativeExpr13285 = new BitSet(new long[]{0x0000000000000002L,0x0800000000600000L});
    public static final BitSet FOLLOW_unaryExpr_in_inExpr13371 = new BitSet(new long[]{0x0000000000000002L,0x0000010000000000L});
    public static final BitSet FOLLOW_TOK_IN_in_inExpr13429 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_unaryExpr_in_inExpr13472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_inExpr13528 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_fieldList_in_inExpr13530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_inExpr13532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_PLUS_in_unaryOp13624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_MINUS_in_unaryOp13644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ABS_in_unaryOp13669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_NOT_in_unaryOp13689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_PLUS_in_xunaryExpr13764 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_MINUS_in_xunaryExpr13789 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_ABS_in_xunaryExpr13814 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_NOT_in_xunaryExpr13839 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_postfixExpr_in_xunaryExpr13864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryOp_in_unaryExpr13948 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_unaryExpr_in_unaryExpr13952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpr_in_unaryExpr13976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_postfixOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpr_in_postfixExpr14061 = new BitSet(new long[]{0x0000000000000002L,0x0000000010011000L});
    public static final BitSet FOLLOW_arrayIndexExpr_in_postfixExpr14088 = new BitSet(new long[]{0x0000000000000002L,0x0000000000011000L});
    public static final BitSet FOLLOW_postfixOp_in_postfixExpr14150 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_postfixExpr14155 = new BitSet(new long[]{0x0000000000000002L,0x0000000010011000L});
    public static final BitSet FOLLOW_arrayIndexExpr_in_postfixExpr14334 = new BitSet(new long[]{0x0000000000000002L,0x0000000000011000L});
    public static final BitSet FOLLOW_postfixOp_in_postfixExpr14382 = new BitSet(new long[]{0x0000000000000000L,0x3020001540780200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_scopeIdentifier_in_postfixExpr14387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LBRACK_in_arrayIndexExpr14442 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_index_in_arrayIndexExpr14444 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_TOK_RBRACK_in_arrayIndexExpr14446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_index14509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpr_in_pathFunctionExpr14743 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_argList_in_pathFunctionExpr14745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_blockExpr14791 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_blockExpr14793 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_blockExpr14795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_blockExpr_in_primaryExpr14857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionExpr_in_primaryExpr14897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aggregateExpr_in_primaryExpr14921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathFunctionExpr_in_primaryExpr14947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelIdentifier_in_primaryExpr15001 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_argList_in_primaryExpr15045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bindVar_in_primaryExpr15125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpr15149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_primaryExpr15173 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectExpr_in_primaryExpr15175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_primaryExpr15177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DOLLAR_in_bindVar15244 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_bindVar15246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_argList15295 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380300L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_argList15331 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_TOK_COMMA_in_argList15375 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_argList15399 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_argList15454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_EXISTS_in_collectionExpr15628 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_collectionExpr15630 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectExpr_in_collectionExpr15632 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_collectionExpr15634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_literals15700 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_literals15703 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0xF020000000003800L,0x000000000000000FL});
    public static final BitSet FOLLOW_literal_in_literals15705 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_set_in_aggregateOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aggregateOp_in_aggregateExpr15870 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_aggregateExpr15886 = new BitSet(new long[]{0x0000000000000000L,0x3020001140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_DISTINCT_in_aggregateExpr15889 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_aggregateExpr15893 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_aggregateExpr15895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_PENDING_COUNT_in_aggregateExpr15947 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_aggregateExpr15963 = new BitSet(new long[]{0x0000000000000000L,0x0000001000400000L});
    public static final BitSet FOLLOW_TOK_DISTINCT_in_aggregateExpr15966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_TOK_STAR_in_aggregateExpr15970 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_aggregateExpr15972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_COUNT_in_aggregateExpr16016 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_aggregateExpr16032 = new BitSet(new long[]{0x0000000000000000L,0x3020001540780200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_TOK_DISTINCT_in_aggregateExpr16088 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_aggregateExpr16110 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_scopeIdentifier_in_aggregateExpr16152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_aggregateExpr16201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_undefinedOp0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedOp_in_undefinedExpr16285 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_undefinedExpr16301 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectExpr_in_undefinedExpr16317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_undefinedExpr16333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedOp_in_undefinedExpr16380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_undefinedExpr16396 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_undefinedExpr16412 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_undefinedExpr16428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_field_in_fieldList16507 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_TOK_COMMA_in_fieldList16543 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_field_in_fieldList16563 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_expr_in_field16654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LONG_in_type16701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DOUBLE_in_type16721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_CHAR_in_type16741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_STRING_in_type16761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_BOOLEAN_in_type16781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_DATETIME_in_type16801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_INT_in_type16821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_CONCEPT_in_type16841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_ENTITY_in_type16861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_EVENT_in_type16881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_OBJECT_in_type16901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathExpr_in_type16921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectLiteral_in_literal16975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal17004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIGITS_in_literal17035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decimalLiteral_in_literal17066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_doubleLiteral_in_literal17097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_charLiteral_in_literal17128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_literal17159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_datetimeLiteral_in_literal17190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hexLiteral_in_literal17221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_octalLiteral_in_literal17253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_NULL_in_objectLiteral17334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_booleanLiteral17374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DecimalLiteral_in_decimalLiteral17467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HexLiteral_in_hexLiteral17498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OctalLiteral_in_octalLiteral17528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_doubleLiteral17563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CharLiteral_in_charLiteral17659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_StringLiteral_in_stringLiteral17700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DateTimeLiteral_in_datetimeLiteral17739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_keyWords0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_HASH_in_labelIdentifier18658 = new BitSet(new long[]{0x0000000000000000L,0xFFFFFF7000000000L,0x00000FFFD7FF7EFCL});
    public static final BitSet FOLLOW_keyWords_in_labelIdentifier18660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_labelIdentifier18694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_STAR_in_scopeIdentifier18735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_synpred19_BEOql8833 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred19_BEOql8836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_synpred19_BEOql8839 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred19_BEOql8844 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_synpred19_BEOql8847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_synpred22_BEOql8871 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred22_BEOql8874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_synpred22_BEOql8877 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred22_BEOql8882 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_synpred22_BEOql8885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_synpred25_BEOql8913 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred25_BEOql8916 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_synpred25_BEOql8919 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred25_BEOql8924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_synpred25_BEOql8927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_synpred28_BEOql8955 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred28_BEOql8958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_synpred28_BEOql8961 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred28_BEOql8966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_synpred28_BEOql8969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_streamDefItemPolicy_in_synpred31_BEOql8997 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred31_BEOql9000 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_streamDefItemAccept_in_synpred31_BEOql9003 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_TOK_SEMIC_in_synpred31_BEOql9008 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamDefItemEmit_in_synpred31_BEOql9011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timeWindowDef_in_synpred39_BEOql9511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tumblingWindowDef_in_synpred40_BEOql9531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sortDirection_in_synpred63_BEOql11415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_synpred71_BEOql11907 = new BitSet(new long[]{0x0000000000000000L,0x0000000100200000L,0x0000000001FFC000L,0x0000000000000010L});
    public static final BitSet FOLLOW_type_in_synpred71_BEOql11909 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000100L});
    public static final BitSet FOLLOW_TOK_LBRACK_in_synpred71_BEOql11912 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_TOK_RBRACK_in_synpred71_BEOql11914 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_synpred71_BEOql11918 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_expr_in_synpred71_BEOql11920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unaryExpr_in_synpred89_BEOql13472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixOp_in_synpred102_BEOql14150 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_labelIdentifier_in_synpred102_BEOql14155 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_arrayIndexExpr_in_synpred102_BEOql14334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathFunctionExpr_in_synpred108_BEOql14947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_labelIdentifier_in_synpred110_BEOql15001 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_argList_in_synpred110_BEOql15045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedOp_in_synpred126_BEOql16285 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_TOK_LPAREN_in_synpred126_BEOql16301 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectExpr_in_synpred126_BEOql16317 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_TOK_RPAREN_in_synpred126_BEOql16333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOK_COMMA_in_synpred127_BEOql16543 = new BitSet(new long[]{0x0000000000000000L,0x3020000140380200L,0xF0200000000039F8L,0x000000000000001FL});
    public static final BitSet FOLLOW_field_in_synpred127_BEOql16563 = new BitSet(new long[]{0x0000000000000002L});

}