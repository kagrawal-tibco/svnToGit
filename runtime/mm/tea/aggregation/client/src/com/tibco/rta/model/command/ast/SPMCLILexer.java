// $ANTLR 3.4 /home/aditya/Desktop/SPMCLI.g 2012-12-06 14:57:56

package com.tibco.rta.model.command.ast;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class SPMCLILexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public SPMCLILexer() {} 
    public SPMCLILexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SPMCLILexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/aditya/Desktop/SPMCLI.g"; }

    // $ANTLR start "ADD"
    public final void mADD() throws RecognitionException {
        try {
            int _type = ADD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:11:5: ( 'add' )
            // /home/aditya/Desktop/SPMCLI.g:11:7: 'add'
            {
            match("add"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ADD"

    // $ANTLR start "AGGREGATOR"
    public final void mAGGREGATOR() throws RecognitionException {
        try {
            int _type = AGGREGATOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:12:12: ( 'aggregator' )
            // /home/aditya/Desktop/SPMCLI.g:12:14: 'aggregator'
            {
            match("aggregator"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AGGREGATOR"

    // $ANTLR start "ATTRIBUTE"
    public final void mATTRIBUTE() throws RecognitionException {
        try {
            int _type = ATTRIBUTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:13:11: ( 'attribute' )
            // /home/aditya/Desktop/SPMCLI.g:13:13: 'attribute'
            {
            match("attribute"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ATTRIBUTE"

    // $ANTLR start "COMMIT"
    public final void mCOMMIT() throws RecognitionException {
        try {
            int _type = COMMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:14:8: ( 'commit' )
            // /home/aditya/Desktop/SPMCLI.g:14:10: 'commit'
            {
            match("commit"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMIT"

    // $ANTLR start "CONNECT"
    public final void mCONNECT() throws RecognitionException {
        try {
            int _type = CONNECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:15:9: ( 'connect' )
            // /home/aditya/Desktop/SPMCLI.g:15:11: 'connect'
            {
            match("connect"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CONNECT"

    // $ANTLR start "CREATE"
    public final void mCREATE() throws RecognitionException {
        try {
            int _type = CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:16:8: ( 'create' )
            // /home/aditya/Desktop/SPMCLI.g:16:10: 'create'
            {
            match("create"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CREATE"

    // $ANTLR start "CUBE"
    public final void mCUBE() throws RecognitionException {
        try {
            int _type = CUBE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:17:6: ( 'cube' )
            // /home/aditya/Desktop/SPMCLI.g:17:8: 'cube'
            {
            match("cube"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "CUBE"

    // $ANTLR start "DATATYPE"
    public final void mDATATYPE() throws RecognitionException {
        try {
            int _type = DATATYPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:18:10: ( 'datatype' )
            // /home/aditya/Desktop/SPMCLI.g:18:12: 'datatype'
            {
            match("datatype"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DATATYPE"

    // $ANTLR start "DIMENSION"
    public final void mDIMENSION() throws RecognitionException {
        try {
            int _type = DIMENSION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:19:11: ( 'dimension' )
            // /home/aditya/Desktop/SPMCLI.g:19:13: 'dimension'
            {
            match("dimension"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DIMENSION"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:20:8: ( '=' )
            // /home/aditya/Desktop/SPMCLI.g:20:10: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "EXPORT"
    public final void mEXPORT() throws RecognitionException {
        try {
            int _type = EXPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:21:8: ( 'export' )
            // /home/aditya/Desktop/SPMCLI.g:21:10: 'export'
            {
            match("export"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPORT"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:22:5: ( 'for' )
            // /home/aditya/Desktop/SPMCLI.g:22:7: 'for'
            {
            match("for"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:23:6: ( 'from' )
            // /home/aditya/Desktop/SPMCLI.g:23:8: 'from'
            {
            match("from"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "HIERARCHY"
    public final void mHIERARCHY() throws RecognitionException {
        try {
            int _type = HIERARCHY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:24:11: ( 'hierarchy' )
            // /home/aditya/Desktop/SPMCLI.g:24:13: 'hierarchy'
            {
            match("hierarchy"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HIERARCHY"

    // $ANTLR start "IMPORT"
    public final void mIMPORT() throws RecognitionException {
        try {
            int _type = IMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:25:8: ( 'import' )
            // /home/aditya/Desktop/SPMCLI.g:25:10: 'import'
            {
            match("import"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IMPORT"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:26:4: ( 'in' )
            // /home/aditya/Desktop/SPMCLI.g:26:6: 'in'
            {
            match("in"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "LINK"
    public final void mLINK() throws RecognitionException {
        try {
            int _type = LINK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:27:6: ( 'link' )
            // /home/aditya/Desktop/SPMCLI.g:27:8: 'link'
            {
            match("link"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LINK"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:28:8: ( '(' )
            // /home/aditya/Desktop/SPMCLI.g:28:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "MEASUREMENT"
    public final void mMEASUREMENT() throws RecognitionException {
        try {
            int _type = MEASUREMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:29:13: ( 'measurement' )
            // /home/aditya/Desktop/SPMCLI.g:29:15: 'measurement'
            {
            match("measurement"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MEASUREMENT"

    // $ANTLR start "PASSWORD"
    public final void mPASSWORD() throws RecognitionException {
        try {
            int _type = PASSWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:30:10: ( 'password' )
            // /home/aditya/Desktop/SPMCLI.g:30:12: 'password'
            {
            match("password"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PASSWORD"

    // $ANTLR start "QUOTES"
    public final void mQUOTES() throws RecognitionException {
        try {
            int _type = QUOTES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:31:8: ( '\"' )
            // /home/aditya/Desktop/SPMCLI.g:31:10: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "QUOTES"

    // $ANTLR start "REMOVE"
    public final void mREMOVE() throws RecognitionException {
        try {
            int _type = REMOVE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:32:8: ( 'remove' )
            // /home/aditya/Desktop/SPMCLI.g:32:10: 'remove'
            {
            match("remove"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "REMOVE"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:33:8: ( ')' )
            // /home/aditya/Desktop/SPMCLI.g:33:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "SCHEMA"
    public final void mSCHEMA() throws RecognitionException {
        try {
            int _type = SCHEMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:34:8: ( 'schema' )
            // /home/aditya/Desktop/SPMCLI.g:34:10: 'schema'
            {
            match("schema"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SCHEMA"

    // $ANTLR start "SHOW"
    public final void mSHOW() throws RecognitionException {
        try {
            int _type = SHOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:35:6: ( 'show' )
            // /home/aditya/Desktop/SPMCLI.g:35:8: 'show'
            {
            match("show"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SHOW"

    // $ANTLR start "TO"
    public final void mTO() throws RecognitionException {
        try {
            int _type = TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:36:4: ( 'to' )
            // /home/aditya/Desktop/SPMCLI.g:36:6: 'to'
            {
            match("to"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "TO"

    // $ANTLR start "USERNAME"
    public final void mUSERNAME() throws RecognitionException {
        try {
            int _type = USERNAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:37:10: ( 'username' )
            // /home/aditya/Desktop/SPMCLI.g:37:12: 'username'
            {
            match("username"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "USERNAME"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:62:5: ( LETTER ( LETTER | JAVAIDDIGIT )* )
            // /home/aditya/Desktop/SPMCLI.g:62:10: LETTER ( LETTER | JAVAIDDIGIT )*
            {
            mLETTER(); 


            // /home/aditya/Desktop/SPMCLI.g:62:17: ( LETTER | JAVAIDDIGIT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='$'||(LA1_0 >= '0' && LA1_0 <= '9')||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||(LA1_0 >= '\u00C0' && LA1_0 <= '\u00D6')||(LA1_0 >= '\u00D8' && LA1_0 <= '\u00F6')||(LA1_0 >= '\u00F8' && LA1_0 <= '\u1FFF')||(LA1_0 >= '\u3040' && LA1_0 <= '\u318F')||(LA1_0 >= '\u3300' && LA1_0 <= '\u337F')||(LA1_0 >= '\u3400' && LA1_0 <= '\u3D2D')||(LA1_0 >= '\u4E00' && LA1_0 <= '\u9FFF')||(LA1_0 >= '\uF900' && LA1_0 <= '\uFAFF')||(LA1_0 >= '\uFF61' && LA1_0 <= '\uFF9F')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u3040' && input.LA(1) <= '\u318F')||(input.LA(1) >= '\u3300' && input.LA(1) <= '\u337F')||(input.LA(1) >= '\u3400' && input.LA(1) <= '\u3D2D')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FFF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFAFF')||(input.LA(1) >= '\uFF61' && input.LA(1) <= '\uFF9F') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // /home/aditya/Desktop/SPMCLI.g:68:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' | '\\uff61' .. '\\uff9f' )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||(input.LA(1) >= '\u00C0' && input.LA(1) <= '\u00D6')||(input.LA(1) >= '\u00D8' && input.LA(1) <= '\u00F6')||(input.LA(1) >= '\u00F8' && input.LA(1) <= '\u1FFF')||(input.LA(1) >= '\u3040' && input.LA(1) <= '\u318F')||(input.LA(1) >= '\u3300' && input.LA(1) <= '\u337F')||(input.LA(1) >= '\u3400' && input.LA(1) <= '\u3D2D')||(input.LA(1) >= '\u4E00' && input.LA(1) <= '\u9FFF')||(input.LA(1) >= '\uF900' && input.LA(1) <= '\uFAFF')||(input.LA(1) >= '\uFF61' && input.LA(1) <= '\uFF9F') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "JAVAIDDIGIT"
    public final void mJAVAIDDIGIT() throws RecognitionException {
        try {
            // /home/aditya/Desktop/SPMCLI.g:86:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // /home/aditya/Desktop/SPMCLI.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= '\u0660' && input.LA(1) <= '\u0669')||(input.LA(1) >= '\u06F0' && input.LA(1) <= '\u06F9')||(input.LA(1) >= '\u0966' && input.LA(1) <= '\u096F')||(input.LA(1) >= '\u09E6' && input.LA(1) <= '\u09EF')||(input.LA(1) >= '\u0A66' && input.LA(1) <= '\u0A6F')||(input.LA(1) >= '\u0AE6' && input.LA(1) <= '\u0AEF')||(input.LA(1) >= '\u0B66' && input.LA(1) <= '\u0B6F')||(input.LA(1) >= '\u0BE7' && input.LA(1) <= '\u0BEF')||(input.LA(1) >= '\u0C66' && input.LA(1) <= '\u0C6F')||(input.LA(1) >= '\u0CE6' && input.LA(1) <= '\u0CEF')||(input.LA(1) >= '\u0D66' && input.LA(1) <= '\u0D6F')||(input.LA(1) >= '\u0E50' && input.LA(1) <= '\u0E59')||(input.LA(1) >= '\u0ED0' && input.LA(1) <= '\u0ED9')||(input.LA(1) >= '\u1040' && input.LA(1) <= '\u1049') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "JAVAIDDIGIT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:145:5: ( ( '\\r' )? '\\n' )
            // /home/aditya/Desktop/SPMCLI.g:145:7: ( '\\r' )? '\\n'
            {
            // /home/aditya/Desktop/SPMCLI.g:145:7: ( '\\r' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\r') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/aditya/Desktop/SPMCLI.g:145:7: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/aditya/Desktop/SPMCLI.g:150:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
            // /home/aditya/Desktop/SPMCLI.g:150:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
            {
            // /home/aditya/Desktop/SPMCLI.g:150:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0 >= '\t' && LA3_0 <= '\n')||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/aditya/Desktop/SPMCLI.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/aditya/Desktop/SPMCLI.g:1:8: ( ADD | AGGREGATOR | ATTRIBUTE | COMMIT | CONNECT | CREATE | CUBE | DATATYPE | DIMENSION | EQUALS | EXPORT | FOR | FROM | HIERARCHY | IMPORT | IN | LINK | LPAREN | MEASUREMENT | PASSWORD | QUOTES | REMOVE | RPAREN | SCHEMA | SHOW | TO | USERNAME | IDENTIFIER | NEWLINE | WS )
        int alt4=30;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // /home/aditya/Desktop/SPMCLI.g:1:10: ADD
                {
                mADD(); 


                }
                break;
            case 2 :
                // /home/aditya/Desktop/SPMCLI.g:1:14: AGGREGATOR
                {
                mAGGREGATOR(); 


                }
                break;
            case 3 :
                // /home/aditya/Desktop/SPMCLI.g:1:25: ATTRIBUTE
                {
                mATTRIBUTE(); 


                }
                break;
            case 4 :
                // /home/aditya/Desktop/SPMCLI.g:1:35: COMMIT
                {
                mCOMMIT(); 


                }
                break;
            case 5 :
                // /home/aditya/Desktop/SPMCLI.g:1:42: CONNECT
                {
                mCONNECT(); 


                }
                break;
            case 6 :
                // /home/aditya/Desktop/SPMCLI.g:1:50: CREATE
                {
                mCREATE(); 


                }
                break;
            case 7 :
                // /home/aditya/Desktop/SPMCLI.g:1:57: CUBE
                {
                mCUBE(); 


                }
                break;
            case 8 :
                // /home/aditya/Desktop/SPMCLI.g:1:62: DATATYPE
                {
                mDATATYPE(); 


                }
                break;
            case 9 :
                // /home/aditya/Desktop/SPMCLI.g:1:71: DIMENSION
                {
                mDIMENSION(); 


                }
                break;
            case 10 :
                // /home/aditya/Desktop/SPMCLI.g:1:81: EQUALS
                {
                mEQUALS(); 


                }
                break;
            case 11 :
                // /home/aditya/Desktop/SPMCLI.g:1:88: EXPORT
                {
                mEXPORT(); 


                }
                break;
            case 12 :
                // /home/aditya/Desktop/SPMCLI.g:1:95: FOR
                {
                mFOR(); 


                }
                break;
            case 13 :
                // /home/aditya/Desktop/SPMCLI.g:1:99: FROM
                {
                mFROM(); 


                }
                break;
            case 14 :
                // /home/aditya/Desktop/SPMCLI.g:1:104: HIERARCHY
                {
                mHIERARCHY(); 


                }
                break;
            case 15 :
                // /home/aditya/Desktop/SPMCLI.g:1:114: IMPORT
                {
                mIMPORT(); 


                }
                break;
            case 16 :
                // /home/aditya/Desktop/SPMCLI.g:1:121: IN
                {
                mIN(); 


                }
                break;
            case 17 :
                // /home/aditya/Desktop/SPMCLI.g:1:124: LINK
                {
                mLINK(); 


                }
                break;
            case 18 :
                // /home/aditya/Desktop/SPMCLI.g:1:129: LPAREN
                {
                mLPAREN(); 


                }
                break;
            case 19 :
                // /home/aditya/Desktop/SPMCLI.g:1:136: MEASUREMENT
                {
                mMEASUREMENT(); 


                }
                break;
            case 20 :
                // /home/aditya/Desktop/SPMCLI.g:1:148: PASSWORD
                {
                mPASSWORD(); 


                }
                break;
            case 21 :
                // /home/aditya/Desktop/SPMCLI.g:1:157: QUOTES
                {
                mQUOTES(); 


                }
                break;
            case 22 :
                // /home/aditya/Desktop/SPMCLI.g:1:164: REMOVE
                {
                mREMOVE(); 


                }
                break;
            case 23 :
                // /home/aditya/Desktop/SPMCLI.g:1:171: RPAREN
                {
                mRPAREN(); 


                }
                break;
            case 24 :
                // /home/aditya/Desktop/SPMCLI.g:1:178: SCHEMA
                {
                mSCHEMA(); 


                }
                break;
            case 25 :
                // /home/aditya/Desktop/SPMCLI.g:1:185: SHOW
                {
                mSHOW(); 


                }
                break;
            case 26 :
                // /home/aditya/Desktop/SPMCLI.g:1:190: TO
                {
                mTO(); 


                }
                break;
            case 27 :
                // /home/aditya/Desktop/SPMCLI.g:1:193: USERNAME
                {
                mUSERNAME(); 


                }
                break;
            case 28 :
                // /home/aditya/Desktop/SPMCLI.g:1:202: IDENTIFIER
                {
                mIDENTIFIER(); 


                }
                break;
            case 29 :
                // /home/aditya/Desktop/SPMCLI.g:1:213: NEWLINE
                {
                mNEWLINE(); 


                }
                break;
            case 30 :
                // /home/aditya/Desktop/SPMCLI.g:1:221: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\1\uffff\3\23\1\uffff\5\23\1\uffff\2\23\1\uffff\1\23\1\uffff\3\23"+
        "\1\uffff\1\26\1\55\1\uffff\15\23\1\74\6\23\1\103\1\23\1\uffff\1"+
        "\105\11\23\1\117\3\23\1\uffff\6\23\1\uffff\1\23\1\uffff\5\23\1\137"+
        "\3\23\1\uffff\1\143\2\23\1\146\4\23\1\153\6\23\1\uffff\3\23\1\uffff"+
        "\2\23\1\uffff\4\23\1\uffff\3\23\1\176\1\23\1\u0080\2\23\1\u0083"+
        "\1\23\1\u0085\2\23\1\u0088\1\u0089\3\23\1\uffff\1\u008d\1\uffff"+
        "\2\23\1\uffff\1\23\1\uffff\2\23\2\uffff\3\23\1\uffff\1\u0096\3\23"+
        "\1\u009a\1\u009b\1\23\1\u009d\1\uffff\1\u009e\1\u009f\1\23\2\uffff"+
        "\1\u00a1\3\uffff\1\23\1\uffff\1\u00a3\1\uffff";
    static final String DFA4_eofS =
        "\u00a4\uffff";
    static final String DFA4_minS =
        "\1\11\1\144\1\157\1\141\1\uffff\1\170\1\157\1\151\1\155\1\151\1"+
        "\uffff\1\145\1\141\1\uffff\1\145\1\uffff\1\143\1\157\1\163\1\uffff"+
        "\1\12\1\11\1\uffff\1\144\1\147\1\164\1\155\1\145\1\142\1\164\1\155"+
        "\1\160\1\162\1\157\1\145\1\160\1\44\1\156\1\141\1\163\1\155\1\150"+
        "\1\157\1\44\1\145\1\uffff\1\44\2\162\1\155\1\156\1\141\1\145\1\141"+
        "\1\145\1\157\1\44\1\155\1\162\1\157\1\uffff\1\153\2\163\1\157\1"+
        "\145\1\167\1\uffff\1\162\1\uffff\1\145\2\151\1\145\1\164\1\44\1"+
        "\164\1\156\1\162\1\uffff\1\44\1\141\1\162\1\44\1\165\1\167\1\166"+
        "\1\155\1\44\1\156\1\147\1\142\1\164\1\143\1\145\1\uffff\1\171\1"+
        "\163\1\164\1\uffff\1\162\1\164\1\uffff\1\162\1\157\1\145\1\141\1"+
        "\uffff\2\141\1\165\1\44\1\164\1\44\1\160\1\151\1\44\1\143\1\44\1"+
        "\145\1\162\2\44\1\155\2\164\1\uffff\1\44\1\uffff\1\145\1\157\1\uffff"+
        "\1\150\1\uffff\1\155\1\144\2\uffff\1\145\1\157\1\145\1\uffff\1\44"+
        "\1\156\1\171\1\145\2\44\1\162\1\44\1\uffff\2\44\1\156\2\uffff\1"+
        "\44\3\uffff\1\164\1\uffff\1\44\1\uffff";
    static final String DFA4_maxS =
        "\1\uff9f\1\164\1\165\1\151\1\uffff\1\170\1\162\1\151\1\156\1\151"+
        "\1\uffff\1\145\1\141\1\uffff\1\145\1\uffff\1\150\1\157\1\163\1\uffff"+
        "\1\12\1\40\1\uffff\1\144\1\147\1\164\1\156\1\145\1\142\1\164\1\155"+
        "\1\160\1\162\1\157\1\145\1\160\1\uff9f\1\156\1\141\1\163\1\155\1"+
        "\150\1\157\1\uff9f\1\145\1\uffff\1\uff9f\2\162\1\155\1\156\1\141"+
        "\1\145\1\141\1\145\1\157\1\uff9f\1\155\1\162\1\157\1\uffff\1\153"+
        "\2\163\1\157\1\145\1\167\1\uffff\1\162\1\uffff\1\145\2\151\1\145"+
        "\1\164\1\uff9f\1\164\1\156\1\162\1\uffff\1\uff9f\1\141\1\162\1\uff9f"+
        "\1\165\1\167\1\166\1\155\1\uff9f\1\156\1\147\1\142\1\164\1\143\1"+
        "\145\1\uffff\1\171\1\163\1\164\1\uffff\1\162\1\164\1\uffff\1\162"+
        "\1\157\1\145\1\141\1\uffff\2\141\1\165\1\uff9f\1\164\1\uff9f\1\160"+
        "\1\151\1\uff9f\1\143\1\uff9f\1\145\1\162\2\uff9f\1\155\2\164\1\uffff"+
        "\1\uff9f\1\uffff\1\145\1\157\1\uffff\1\150\1\uffff\1\155\1\144\2"+
        "\uffff\1\145\1\157\1\145\1\uffff\1\uff9f\1\156\1\171\1\145\2\uff9f"+
        "\1\162\1\uff9f\1\uffff\2\uff9f\1\156\2\uffff\1\uff9f\3\uffff\1\164"+
        "\1\uffff\1\uff9f\1\uffff";
    static final String DFA4_acceptS =
        "\4\uffff\1\12\5\uffff\1\22\2\uffff\1\25\1\uffff\1\27\3\uffff\1\34"+
        "\2\uffff\1\36\26\uffff\1\35\16\uffff\1\20\6\uffff\1\32\1\uffff\1"+
        "\1\11\uffff\1\14\17\uffff\1\7\3\uffff\1\15\2\uffff\1\21\4\uffff"+
        "\1\31\22\uffff\1\4\1\uffff\1\6\2\uffff\1\13\1\uffff\1\17\2\uffff"+
        "\1\26\1\30\3\uffff\1\5\10\uffff\1\10\3\uffff\1\24\1\33\1\uffff\1"+
        "\3\1\11\1\16\1\uffff\1\2\1\uffff\1\23";
    static final String DFA4_specialS =
        "\u00a4\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\26\1\25\2\uffff\1\24\22\uffff\1\26\1\uffff\1\15\1\uffff\1"+
            "\23\3\uffff\1\12\1\17\23\uffff\1\4\3\uffff\32\23\4\uffff\1\23"+
            "\1\uffff\1\1\1\23\1\2\1\3\1\5\1\6\1\23\1\7\1\10\2\23\1\11\1"+
            "\13\2\23\1\14\1\23\1\16\1\20\1\21\1\22\5\23\105\uffff\27\23"+
            "\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff\u0150\23\u0170\uffff"+
            "\u0080\23\u0080\uffff\u092e\23\u10d2\uffff\u5200\23\u5900\uffff"+
            "\u0200\23\u0461\uffff\77\23",
            "\1\27\2\uffff\1\30\14\uffff\1\31",
            "\1\32\2\uffff\1\33\2\uffff\1\34",
            "\1\35\7\uffff\1\36",
            "",
            "\1\37",
            "\1\40\2\uffff\1\41",
            "\1\42",
            "\1\43\1\44",
            "\1\45",
            "",
            "\1\46",
            "\1\47",
            "",
            "\1\50",
            "",
            "\1\51\4\uffff\1\52",
            "\1\53",
            "\1\54",
            "",
            "\1\25",
            "\2\26\2\uffff\1\26\22\uffff\1\26",
            "",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\104",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\120",
            "\1\121",
            "\1\122",
            "",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "",
            "\1\131",
            "",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\140",
            "\1\141",
            "\1\142",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\144",
            "\1\145",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "",
            "\1\162",
            "\1\163",
            "\1\164",
            "",
            "\1\165",
            "\1\166",
            "",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\177",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u0081",
            "\1\u0082",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u0084",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u0086",
            "\1\u0087",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "",
            "\1\u008e",
            "\1\u008f",
            "",
            "\1\u0090",
            "",
            "\1\u0091",
            "\1\u0092",
            "",
            "",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u009c",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "\1\u00a0",
            "",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
            "",
            "",
            "",
            "\1\u00a2",
            "",
            "\1\23\13\uffff\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32"+
            "\23\105\uffff\27\23\1\uffff\37\23\1\uffff\u1f08\23\u1040\uffff"+
            "\u0150\23\u0170\uffff\u0080\23\u0080\uffff\u092e\23\u10d2\uffff"+
            "\u5200\23\u5900\uffff\u0200\23\u0461\uffff\77\23",
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
            return "1:1: Tokens : ( ADD | AGGREGATOR | ATTRIBUTE | COMMIT | CONNECT | CREATE | CUBE | DATATYPE | DIMENSION | EQUALS | EXPORT | FOR | FROM | HIERARCHY | IMPORT | IN | LINK | LPAREN | MEASUREMENT | PASSWORD | QUOTES | REMOVE | RPAREN | SCHEMA | SHOW | TO | USERNAME | IDENTIFIER | NEWLINE | WS );";
        }
    }
 

}