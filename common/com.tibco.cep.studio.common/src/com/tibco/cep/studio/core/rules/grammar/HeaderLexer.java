// $ANTLR 3.2 Sep 23, 2009 12:02:23 Header.g 2012-11-14 16:13:39
package com.tibco.cep.studio.core.rules.grammar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;

public class HeaderLexer extends Lexer {
    public static final int WS_STAR=16;
    public static final int T__19=19;
    public static final int NAME=8;
    public static final int WS=13;
    public static final int T__18=18;
    public static final int NEWLINE=14;
    public static final int T__17=17;
    public static final int GUID_STATEMENT=6;
    public static final int HEADER_BLOCK=9;
    public static final int DESCRIPTION_STATEMENT=4;
    public static final int STATEMENTS=10;
    public static final int LITERALS=7;
    public static final int ID=11;
    public static final int END=15;
    public static final int EOF=-1;
    public static final int CHARACTER=12;
    public static final int AUTHOR_STATEMENT=5;

    public void reportError(RecognitionException e) {
    	// ignore, don't care for header sections, and we don't want to display a lexer error to user
    	return;
    }


    // delegates
    // delegators

    public HeaderLexer() {;} 
    public HeaderLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public HeaderLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Header.g"; }

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:15:7: ( '@author' )
            // Header.g:15:9: '@author'
            {
            match("@author"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:16:7: ( '@description' )
            // Header.g:16:9: '@description'
            {
            match("@description"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:17:7: ( '@GUID' )
            // Header.g:17:9: '@GUID'
            {
            match("@GUID"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:75:9: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // Header.g:75:11: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // Header.g:75:11: ( 'a' .. 'z' | 'A' .. 'Z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='A' && LA1_0<='Z')||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Header.g:
            	    {
            	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:78:9: ( '*/' )
            // Header.g:78:11: '*/'
            {
            match("*/"); 

            emit(Token.EOF_TOKEN);

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "WS_STAR"
    public final void mWS_STAR() throws RecognitionException {
        try {
            int _type = WS_STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:83:1: ( NEWLINE ( WS )? ( '*/' | '*' ) ( WS )? )
            // Header.g:83:3: NEWLINE ( WS )? ( '*/' | '*' ) ( WS )?
            {
            mNEWLINE(); 
            // Header.g:83:11: ( WS )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\t'||LA2_0==' ') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Header.g:83:11: WS
                    {
                    mWS(); 

                    }
                    break;

            }

            // Header.g:83:15: ( '*/' | '*' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='*') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='/') ) {
                    alt3=1;
                }
                else {
                    alt3=2;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // Header.g:83:16: '*/'
                    {
                    match("*/"); 

                    _type=END; emit(Token.EOF_TOKEN);

                    }
                    break;
                case 2 :
                    // Header.g:83:59: '*'
                    {
                    match('*'); 
                    _type=NEWLINE;

                    }
                    break;

            }

            // Header.g:83:81: ( WS )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\t'||LA4_0==' ') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // Header.g:83:81: WS
                    {
                    mWS(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS_STAR"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:86:9: ( ( '\\n' | '\\r' )+ )
            // Header.g:86:11: ( '\\n' | '\\r' )+
            {
            // Header.g:86:11: ( '\\n' | '\\r' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='\n'||LA5_0=='\r') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Header.g:
            	    {
            	    if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:89:9: ( ( ' ' | '\\t' )+ )
            // Header.g:89:11: ( ' ' | '\\t' )+
            {
            // Header.g:89:11: ( ' ' | '\\t' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\t'||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Header.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "CHARACTER"
    public final void mCHARACTER() throws RecognitionException {
        try {
            int _type = CHARACTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Header.g:92:11: ( . )
            // Header.g:93:4: .
            {
            matchAny(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CHARACTER"

    public void mTokens() throws RecognitionException {
        // Header.g:1:8: ( T__17 | T__18 | T__19 | ID | END | WS_STAR | NEWLINE | WS | CHARACTER )
        int alt7=9;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // Header.g:1:10: T__17
                {
                mT__17(); 

                }
                break;
            case 2 :
                // Header.g:1:16: T__18
                {
                mT__18(); 

                }
                break;
            case 3 :
                // Header.g:1:22: T__19
                {
                mT__19(); 

                }
                break;
            case 4 :
                // Header.g:1:28: ID
                {
                mID(); 

                }
                break;
            case 5 :
                // Header.g:1:31: END
                {
                mEND(); 

                }
                break;
            case 6 :
                // Header.g:1:35: WS_STAR
                {
                mWS_STAR(); 

                }
                break;
            case 7 :
                // Header.g:1:43: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 8 :
                // Header.g:1:51: WS
                {
                mWS(); 

                }
                break;
            case 9 :
                // Header.g:1:54: CHARACTER
                {
                mCHARACTER(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\1\uffff\1\6\1\uffff\1\6\1\14\10\uffff\1\14\2\uffff";
    static final String DFA7_eofS =
        "\20\uffff";
    static final String DFA7_minS =
        "\1\0\1\107\1\uffff\1\57\1\11\10\uffff\1\11\2\uffff";
    static final String DFA7_maxS =
        "\1\uffff\1\144\1\uffff\1\57\1\52\10\uffff\1\52\2\uffff";
    static final String DFA7_acceptS =
        "\2\uffff\1\4\2\uffff\1\10\1\11\1\1\1\2\1\3\1\4\1\5\1\7\1\uffff"+
        "\1\6\1\10";
    static final String DFA7_specialS =
        "\1\0\17\uffff}>";
    static final String[] DFA7_transitionS = {
            "\11\6\1\5\1\4\2\6\1\4\22\6\1\5\11\6\1\3\25\6\1\1\32\2\6\6\32"+
            "\2\uff85\6",
            "\1\11\31\uffff\1\7\2\uffff\1\10",
            "",
            "\1\13",
            "\1\16\1\15\2\uffff\1\15\22\uffff\1\16\11\uffff\1\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\16\1\15\2\uffff\1\15\22\uffff\1\16\11\uffff\1\16",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__17 | T__18 | T__19 | ID | END | WS_STAR | NEWLINE | WS | CHARACTER );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_0 = input.LA(1);

                        s = -1;
                        if ( (LA7_0=='@') ) {s = 1;}

                        else if ( ((LA7_0>='A' && LA7_0<='Z')||(LA7_0>='a' && LA7_0<='z')) ) {s = 2;}

                        else if ( (LA7_0=='*') ) {s = 3;}

                        else if ( (LA7_0=='\n'||LA7_0=='\r') ) {s = 4;}

                        else if ( (LA7_0=='\t'||LA7_0==' ') ) {s = 5;}

                        else if ( ((LA7_0>='\u0000' && LA7_0<='\b')||(LA7_0>='\u000B' && LA7_0<='\f')||(LA7_0>='\u000E' && LA7_0<='\u001F')||(LA7_0>='!' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='?')||(LA7_0>='[' && LA7_0<='`')||(LA7_0>='{' && LA7_0<='\uFFFF')) ) {s = 6;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}