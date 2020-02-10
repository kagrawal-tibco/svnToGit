// $ANTLR 3.2 Sep 23, 2009 12:02:23 JavaPackageLexer.g 2013-02-06 20:30:55
 
package com.tibco.cep.studio.core.grammar.java; 


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class JavaPackageLexer extends Lexer {
    public static final int WS=5;
    public static final int PackageStatement=7;
    public static final int LINE_COMMENT=12;
    public static final int EverythingElse=13;
    public static final int PackageToken=4;
    public static final int JavaIDDigit=9;
    public static final int COMMENT=11;
    public static final int HEADER_START=10;
    public static final int EOF=-1;
    public static final int Letter=8;
    public static final int Identifier=6;


    @Override
    public void reportError(RecognitionException exception) {
    	if (exception instanceof MismatchedTokenException) {
    		if (exception.getUnexpectedType() == EOF) {
    			return;
    		}
    	}
    	// super.reportError(exception);
    }



    // delegates
    // delegators

    public JavaPackageLexer() {;} 
    public JavaPackageLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public JavaPackageLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "JavaPackageLexer.g"; }

    // $ANTLR start "PackageToken"
    public final void mPackageToken() throws RecognitionException {
        try {
            int _type = PackageToken;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:26:2: ( 'package' )
            // JavaPackageLexer.g:26:4: 'package'
            {
            match("package"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PackageToken"

    // $ANTLR start "PackageStatement"
    public final void mPackageStatement() throws RecognitionException {
        try {
            int _type = PackageStatement;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:30:2: ( PackageToken WS Identifier ( WS )? ( '.' ( WS )? Identifier ( WS )? )* ';' )
            // JavaPackageLexer.g:30:4: PackageToken WS Identifier ( WS )? ( '.' ( WS )? Identifier ( WS )? )* ';'
            {
            mPackageToken(); if (state.failed) return ;
            mWS(); if (state.failed) return ;
            mIdentifier(); if (state.failed) return ;
            // JavaPackageLexer.g:30:31: ( WS )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>='\t' && LA1_0<='\n')||(LA1_0>='\f' && LA1_0<='\r')||LA1_0==' ') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // JavaPackageLexer.g:0:0: WS
                    {
                    mWS(); if (state.failed) return ;

                    }
                    break;

            }

            // JavaPackageLexer.g:30:35: ( '.' ( WS )? Identifier ( WS )? )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='.') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // JavaPackageLexer.g:30:36: '.' ( WS )? Identifier ( WS )?
            	    {
            	    match('.'); if (state.failed) return ;
            	    // JavaPackageLexer.g:30:40: ( WS )?
            	    int alt2=2;
            	    int LA2_0 = input.LA(1);

            	    if ( ((LA2_0>='\t' && LA2_0<='\n')||(LA2_0>='\f' && LA2_0<='\r')||LA2_0==' ') ) {
            	        alt2=1;
            	    }
            	    switch (alt2) {
            	        case 1 :
            	            // JavaPackageLexer.g:0:0: WS
            	            {
            	            mWS(); if (state.failed) return ;

            	            }
            	            break;

            	    }

            	    mIdentifier(); if (state.failed) return ;
            	    // JavaPackageLexer.g:30:55: ( WS )?
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( ((LA3_0>='\t' && LA3_0<='\n')||(LA3_0>='\f' && LA3_0<='\r')||LA3_0==' ') ) {
            	        alt3=1;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // JavaPackageLexer.g:0:0: WS
            	            {
            	            mWS(); if (state.failed) return ;

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PackageStatement"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            // JavaPackageLexer.g:34:5: ( Letter ( Letter | JavaIDDigit )* )
            // JavaPackageLexer.g:34:9: Letter ( Letter | JavaIDDigit )*
            {
            mLetter(); if (state.failed) return ;
            // JavaPackageLexer.g:34:16: ( Letter | JavaIDDigit )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='$'||(LA5_0>='0' && LA5_0<='9')||(LA5_0>='A' && LA5_0<='Z')||LA5_0=='_'||(LA5_0>='a' && LA5_0<='z')||(LA5_0>='\u00C0' && LA5_0<='\u00D6')||(LA5_0>='\u00D8' && LA5_0<='\u00F6')||(LA5_0>='\u00F8' && LA5_0<='\u1FFF')||(LA5_0>='\u3040' && LA5_0<='\u318F')||(LA5_0>='\u3300' && LA5_0<='\u337F')||(LA5_0>='\u3400' && LA5_0<='\u3D2D')||(LA5_0>='\u4E00' && LA5_0<='\u9FFF')||(LA5_0>='\uF900' && LA5_0<='\uFAFF')||(LA5_0>='\uFF61' && LA5_0<='\uFF9F')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // JavaPackageLexer.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFF9F') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // JavaPackageLexer.g:42:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' | '\\uff61' .. '\\uff9f' )
            // JavaPackageLexer.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF')||(input.LA(1)>='\uFF61' && input.LA(1)<='\uFF9F') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "JavaIDDigit"
    public final void mJavaIDDigit() throws RecognitionException {
        try {
            // JavaPackageLexer.g:60:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // JavaPackageLexer.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "JavaIDDigit"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:77:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // JavaPackageLexer.g:77:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "HEADER_START"
    public final void mHEADER_START() throws RecognitionException {
        try {
            // JavaPackageLexer.g:81:2: ( '/**' )
            // JavaPackageLexer.g:81:4: '/**'
            {
            match("/**"); if (state.failed) return ;


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEADER_START"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:85:5: ( ( '/*' | HEADER_START ) ~ '*' ( options {greedy=false; } : . )* '*/' )
            // JavaPackageLexer.g:85:9: ( '/*' | HEADER_START ) ~ '*' ( options {greedy=false; } : . )* '*/'
            {
            // JavaPackageLexer.g:85:9: ( '/*' | HEADER_START )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='/') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='*') ) {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2=='*') ) {
                        alt6=2;
                    }
                    else if ( ((LA6_2>='\u0000' && LA6_2<=')')||(LA6_2>='+' && LA6_2<='\uFFFF')) ) {
                        alt6=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // JavaPackageLexer.g:85:10: '/*'
                    {
                    match("/*"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // JavaPackageLexer.g:85:17: HEADER_START
                    {
                    mHEADER_START(); if (state.failed) return ;

                    }
                    break;

            }

            if ( (input.LA(1)>='\u0000' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='\uFFFF') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // JavaPackageLexer.g:85:36: ( options {greedy=false; } : . )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='*') ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1=='/') ) {
                        alt7=2;
                    }
                    else if ( ((LA7_1>='\u0000' && LA7_1<='.')||(LA7_1>='0' && LA7_1<='\uFFFF')) ) {
                        alt7=1;
                    }


                }
                else if ( ((LA7_0>='\u0000' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='\uFFFF')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // JavaPackageLexer.g:85:64: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match("*/"); if (state.failed) return ;

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:89:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // JavaPackageLexer.g:89:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); if (state.failed) return ;

            // JavaPackageLexer.g:89:12: (~ ( '\\n' | '\\r' ) )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='\t')||(LA8_0>='\u000B' && LA8_0<='\f')||(LA8_0>='\u000E' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // JavaPackageLexer.g:0:0: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            // JavaPackageLexer.g:89:26: ( '\\r' )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\r') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // JavaPackageLexer.g:0:0: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;

            }

            match('\n'); if (state.failed) return ;
            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "EverythingElse"
    public final void mEverythingElse() throws RecognitionException {
        try {
            int _type = EverythingElse;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaPackageLexer.g:93:2: ( . )
            // JavaPackageLexer.g:93:4: .
            {
            matchAny(); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EverythingElse"

    public void mTokens() throws RecognitionException {
        // JavaPackageLexer.g:1:8: ( PackageToken | PackageStatement | WS | COMMENT | LINE_COMMENT | EverythingElse )
        int alt10=6;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // JavaPackageLexer.g:1:10: PackageToken
                {
                mPackageToken(); if (state.failed) return ;

                }
                break;
            case 2 :
                // JavaPackageLexer.g:1:23: PackageStatement
                {
                mPackageStatement(); if (state.failed) return ;

                }
                break;
            case 3 :
                // JavaPackageLexer.g:1:40: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 4 :
                // JavaPackageLexer.g:1:43: COMMENT
                {
                mCOMMENT(); if (state.failed) return ;

                }
                break;
            case 5 :
                // JavaPackageLexer.g:1:51: LINE_COMMENT
                {
                mLINE_COMMENT(); if (state.failed) return ;

                }
                break;
            case 6 :
                // JavaPackageLexer.g:1:64: EverythingElse
                {
                mEverythingElse(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred46_JavaPackageLexer
    public final void synpred46_JavaPackageLexer_fragment() throws RecognitionException {   
        // JavaPackageLexer.g:1:40: ( WS )
        // JavaPackageLexer.g:1:40: WS
        {
        mWS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred46_JavaPackageLexer

    public final boolean synpred46_JavaPackageLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred46_JavaPackageLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\1\uffff\1\4\1\6\1\4\12\uffff\1\17\2\uffff";
    static final String DFA10_eofS =
        "\21\uffff";
    static final String DFA10_minS =
        "\1\0\1\141\1\0\1\52\1\uffff\1\143\1\0\2\uffff\1\153\1\uffff\1\141"+
        "\1\147\1\145\1\11\2\uffff";
    static final String DFA10_maxS =
        "\1\uffff\1\141\1\0\1\57\1\uffff\1\143\1\0\2\uffff\1\153\1\uffff"+
        "\1\141\1\147\1\145\1\40\2\uffff";
    static final String DFA10_acceptS =
        "\4\uffff\1\6\2\uffff\1\4\1\5\1\uffff\1\3\4\uffff\1\1\1\2";
    static final String DFA10_specialS =
        "\1\0\5\uffff\1\1\12\uffff}>";
    static final String[] DFA10_transitionS = {
            "\11\4\2\2\1\4\2\2\22\4\1\2\16\4\1\3\100\4\1\1\uff8f\4",
            "\1\5",
            "\1\uffff",
            "\1\7\4\uffff\1\10",
            "",
            "\1\11",
            "\1\uffff",
            "",
            "",
            "\1\13",
            "",
            "\1\14",
            "\1\15",
            "\1\16",
            "\2\20\1\uffff\2\20\22\uffff\1\20",
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
            return "1:1: Tokens : ( PackageToken | PackageStatement | WS | COMMENT | LINE_COMMENT | EverythingElse );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_0 = input.LA(1);

                        s = -1;
                        if ( (LA10_0=='p') ) {s = 1;}

                        else if ( ((LA10_0>='\t' && LA10_0<='\n')||(LA10_0>='\f' && LA10_0<='\r')||LA10_0==' ') ) {s = 2;}

                        else if ( (LA10_0=='/') ) {s = 3;}

                        else if ( ((LA10_0>='\u0000' && LA10_0<='\b')||LA10_0=='\u000B'||(LA10_0>='\u000E' && LA10_0<='\u001F')||(LA10_0>='!' && LA10_0<='.')||(LA10_0>='0' && LA10_0<='o')||(LA10_0>='q' && LA10_0<='\uFFFF')) ) {s = 4;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_6 = input.LA(1);

                         
                        int index10_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred46_JavaPackageLexer()) ) {s = 10;}

                        else if ( (true) ) {s = 4;}

                         
                        input.seek(index10_6);
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
 

}