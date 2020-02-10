// $ANTLR 3.2 Sep 23, 2009 12:02:23 /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g 2016-03-24 11:19:17

      	package com.tibco.cep.metric.condition.ast;
      

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class filterLexer extends Lexer {
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

    public filterLexer() {;} 
    public filterLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public filterLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g"; }

    // $ANTLR start "TOK_NOTLIKE"
    public final void mTOK_NOTLIKE() throws RecognitionException {
        try {
            int _type = TOK_NOTLIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:11:13: ( 'notlike' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:11:15: 'notlike'
            {
            match("notlike"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOTLIKE"

    // $ANTLR start "TOK_NOR"
    public final void mTOK_NOR() throws RecognitionException {
        try {
            int _type = TOK_NOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:12:9: ( 'nor' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:12:11: 'nor'
            {
            match("nor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOR"

    // $ANTLR start "TOK_NOTIN"
    public final void mTOK_NOTIN() throws RecognitionException {
        try {
            int _type = TOK_NOTIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:13:11: ( 'notin' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:13:13: 'notin'
            {
            match("notin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOTIN"

    // $ANTLR start "TOK_NOTBETWEEN"
    public final void mTOK_NOTBETWEEN() throws RecognitionException {
        try {
            int _type = TOK_NOTBETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:14:16: ( 'notbetween' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:14:18: 'notbetween'
            {
            match("notbetween"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOTBETWEEN"

    // $ANTLR start "TOK_ISNOT"
    public final void mTOK_ISNOT() throws RecognitionException {
        try {
            int _type = TOK_ISNOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:15:11: ( 'isnot' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:15:13: 'isnot'
            {
            match("isnot"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ISNOT"

    // $ANTLR start "TOK_RPAREN"
    public final void mTOK_RPAREN() throws RecognitionException {
        try {
            int _type = TOK_RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:57:25: ( ')' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:57:34: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_RPAREN"

    // $ANTLR start "TOK_LPAREN"
    public final void mTOK_LPAREN() throws RecognitionException {
        try {
            int _type = TOK_LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:58:25: ( '(' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:58:34: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LPAREN"

    // $ANTLR start "TOK_COMMA"
    public final void mTOK_COMMA() throws RecognitionException {
        try {
            int _type = TOK_COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:59:25: ( ',' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:59:34: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_COMMA"

    // $ANTLR start "TOK_DOT"
    public final void mTOK_DOT() throws RecognitionException {
        try {
            int _type = TOK_DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:60:18: ( '.' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:60:27: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOT"

    // $ANTLR start "TOK_COLON"
    public final void mTOK_COLON() throws RecognitionException {
        try {
            int _type = TOK_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:61:25: ( ':' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:61:34: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_COLON"

    // $ANTLR start "TOK_CONCAT"
    public final void mTOK_CONCAT() throws RecognitionException {
        try {
            int _type = TOK_CONCAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:62:25: ( '|' '|' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:62:34: '|' '|'
            {
            match('|'); 
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_CONCAT"

    // $ANTLR start "TOK_EQ"
    public final void mTOK_EQ() throws RecognitionException {
        try {
            int _type = TOK_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:63:17: ( '=' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:63:26: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EQ"

    // $ANTLR start "TOK_PLUS"
    public final void mTOK_PLUS() throws RecognitionException {
        try {
            int _type = TOK_PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:64:25: ( '+' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:64:34: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_PLUS"

    // $ANTLR start "TOK_MINUS"
    public final void mTOK_MINUS() throws RecognitionException {
        try {
            int _type = TOK_MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:65:25: ( '-' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:65:34: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MINUS"

    // $ANTLR start "TOK_SLASH"
    public final void mTOK_SLASH() throws RecognitionException {
        try {
            int _type = TOK_SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:66:25: ( '/' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:66:34: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_SLASH"

    // $ANTLR start "TOK_STAR"
    public final void mTOK_STAR() throws RecognitionException {
        try {
            int _type = TOK_STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:67:25: ( '*' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:67:34: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_STAR"

    // $ANTLR start "TOK_LE"
    public final void mTOK_LE() throws RecognitionException {
        try {
            int _type = TOK_LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:68:25: ( '<=' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:68:34: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LE"

    // $ANTLR start "TOK_GE"
    public final void mTOK_GE() throws RecognitionException {
        try {
            int _type = TOK_GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:69:25: ( '>=' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:69:34: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_GE"

    // $ANTLR start "TOK_NE"
    public final void mTOK_NE() throws RecognitionException {
        try {
            int _type = TOK_NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:70:25: ( '<>' | '!=' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='<') ) {
                alt1=1;
            }
            else if ( (LA1_0=='!') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:70:34: '<>'
                    {
                    match("<>"); 


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:70:39: '!='
                    {
                    match("!="); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NE"

    // $ANTLR start "TOK_LT"
    public final void mTOK_LT() throws RecognitionException {
        try {
            int _type = TOK_LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:71:25: ( '<' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:71:34: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LT"

    // $ANTLR start "TOK_GT"
    public final void mTOK_GT() throws RecognitionException {
        try {
            int _type = TOK_GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:72:25: ( '>' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:72:34: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_GT"

    // $ANTLR start "TOK_DOLLAR"
    public final void mTOK_DOLLAR() throws RecognitionException {
        try {
            int _type = TOK_DOLLAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:73:13: ( '$' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:73:17: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOLLAR"

    // $ANTLR start "TOK_HASH"
    public final void mTOK_HASH() throws RecognitionException {
        try {
            int _type = TOK_HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:74:11: ( '#' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:74:14: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_HASH"

    // $ANTLR start "TOK_AT"
    public final void mTOK_AT() throws RecognitionException {
        try {
            int _type = TOK_AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:75:10: ( '@' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:75:13: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AT"

    // $ANTLR start "TOK_IN"
    public final void mTOK_IN() throws RecognitionException {
        try {
            int _type = TOK_IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:76:10: ( ( 'I' | 'i' ) ( 'N' | 'n' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:76:13: ( 'I' | 'i' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_IN"

    // $ANTLR start "TOK_OR"
    public final void mTOK_OR() throws RecognitionException {
        try {
            int _type = TOK_OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:77:10: ( ( 'O' | 'o' ) ( 'R' | 'r' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:77:13: ( 'O' | 'o' ) ( 'R' | 'r' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_OR"

    // $ANTLR start "TOK_AND"
    public final void mTOK_AND() throws RecognitionException {
        try {
            int _type = TOK_AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:78:11: ( ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:78:14: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_AND"

    // $ANTLR start "TOK_LIKE"
    public final void mTOK_LIKE() throws RecognitionException {
        try {
            int _type = TOK_LIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:79:11: ( ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'K' | 'k' ) ( 'E' | 'e' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:79:14: ( 'L' | 'l' ) ( 'I' | 'i' ) ( 'K' | 'k' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_LIKE"

    // $ANTLR start "TOK_BETWEEN"
    public final void mTOK_BETWEEN() throws RecognitionException {
        try {
            int _type = TOK_BETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:80:14: ( ( 'B' | 'b' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'W' | 'w' ) ( 'E' | 'e' ) ( 'E' | 'e' ) ( 'N' | 'n' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:80:17: ( 'B' | 'b' ) ( 'E' | 'e' ) ( 'T' | 't' ) ( 'W' | 'w' ) ( 'E' | 'e' ) ( 'E' | 'e' ) ( 'N' | 'n' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_BETWEEN"

    // $ANTLR start "TOK_MOD"
    public final void mTOK_MOD() throws RecognitionException {
        try {
            int _type = TOK_MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:81:11: ( ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' ) | '%' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='M'||LA2_0=='m') ) {
                alt2=1;
            }
            else if ( (LA2_0=='%') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:81:14: ( 'M' | 'm' ) ( 'O' | 'o' ) ( 'D' | 'd' )
                    {
                    if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:81:44: '%'
                    {
                    match('%'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_MOD"

    // $ANTLR start "TOK_ABS"
    public final void mTOK_ABS() throws RecognitionException {
        try {
            int _type = TOK_ABS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:82:11: ( ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:82:14: ( 'A' | 'a' ) ( 'B' | 'b' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_ABS"

    // $ANTLR start "TOK_NOT"
    public final void mTOK_NOT() throws RecognitionException {
        try {
            int _type = TOK_NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:83:11: ( ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:83:14: ( 'N' | 'n' ) ( 'O' | 'o' ) ( 'T' | 't' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NOT"

    // $ANTLR start "TOK_NULL"
    public final void mTOK_NULL() throws RecognitionException {
        try {
            int _type = TOK_NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:84:11: ( ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:84:14: ( 'N' | 'n' ) ( 'U' | 'u' ) ( 'L' | 'l' ) ( 'L' | 'l' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_NULL"

    // $ANTLR start "TOK_TRUE"
    public final void mTOK_TRUE() throws RecognitionException {
        try {
            int _type = TOK_TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:85:12: ( ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:85:21: ( 'T' | 't' ) ( 'R' | 'r' ) ( 'U' | 'u' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

              

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_TRUE"

    // $ANTLR start "TOK_FALSE"
    public final void mTOK_FALSE() throws RecognitionException {
        try {
            int _type = TOK_FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:86:13: ( ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:86:16: ( 'F' | 'f' ) ( 'A' | 'a' ) ( 'L' | 'l' ) ( 'S' | 's' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

              

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_FALSE"

    // $ANTLR start "TOK_DOUBLE"
    public final void mTOK_DOUBLE() throws RecognitionException {
        try {
            int _type = TOK_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:87:13: ( ( 'D' | 'd' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:87:16: ( 'D' | 'd' ) ( 'O' | 'o' ) ( 'U' | 'u' ) ( 'B' | 'b' ) ( 'L' | 'l' ) ( 'E' | 'e' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_DOUBLE"

    // $ANTLR start "TOK_IS"
    public final void mTOK_IS() throws RecognitionException {
        try {
            int _type = TOK_IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:88:18: ( ( 'I' | 'i' ) ( 'S' | 's' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:88:34: ( 'I' | 'i' ) ( 'S' | 's' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_IS"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:94:16: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:94:18: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "NameFirstCharacter"
    public final void mNameFirstCharacter() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:98:25: ( ( 'A' .. 'Z' | 'a' .. 'z' | '_' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:99:15: ( 'A' .. 'Z' | 'a' .. 'z' | '_' )
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NameFirstCharacter"

    // $ANTLR start "NameCharacter"
    public final void mNameCharacter() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:103:20: ( ( NameFirstCharacter | '0' .. '9' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:104:15: ( NameFirstCharacter | '0' .. '9' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "NameCharacter"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:11: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\\') ) {
                int LA3_1 = input.LA(2);

                if ( ((LA3_1>='0' && LA3_1<='3')) ) {
                    int LA3_2 = input.LA(3);

                    if ( ((LA3_2>='0' && LA3_2<='7')) ) {
                        int LA3_4 = input.LA(4);

                        if ( ((LA3_4>='0' && LA3_4<='7')) ) {
                            alt3=1;
                        }
                        else {
                            alt3=2;}
                    }
                    else {
                        alt3=3;}
                }
                else if ( ((LA3_1>='4' && LA3_1<='7')) ) {
                    int LA3_3 = input.LA(3);

                    if ( ((LA3_3>='0' && LA3_3<='7')) ) {
                        alt3=2;
                    }
                    else {
                        alt3=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:15: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:20: ( '0' .. '3' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:21: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:31: ( '0' .. '7' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:32: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:42: ( '0' .. '7' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:108:43: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:109:15: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:109:20: ( '0' .. '7' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:109:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:109:31: ( '0' .. '7' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:109:32: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:110:15: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:110:20: ( '0' .. '7' )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:110:21: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:115:11: ( ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:115:15: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:115:15: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:115:16: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:120:11: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UnicodeEscape | OctalEscape )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt4=1;
                    }
                    break;
                case 'u':
                    {
                    alt4=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt4=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:120:15: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:121:15: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:122:15: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:125:27: ( ( 'l' | 'L' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:125:29: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IntegerTypeSuffix"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:128:18: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:128:20: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:128:30: ( '+' | '-' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='+'||LA5_0=='-') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:128:41: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:128:42: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "FloatTypeSuffix"
    public final void mFloatTypeSuffix() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:131:25: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:131:27: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FloatTypeSuffix"

    // $ANTLR start "HexLiteral"
    public final void mHexLiteral() throws RecognitionException {
        try {
            int _type = HexLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:28: ( HexDigit )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='F')||(LA7_0>='a' && LA7_0<='f')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:28: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:38: ( IntegerTypeSuffix )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='L'||LA8_0=='l') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:133:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

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
    // $ANTLR end "HexLiteral"

    // $ANTLR start "DIGIT_ZERO"
    public final void mDIGIT_ZERO() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:136:18: ( '0' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:136:20: '0'
            {
            match('0'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_ZERO"

    // $ANTLR start "DIGIT_OCTAL_RANGE"
    public final void mDIGIT_OCTAL_RANGE() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:139:25: ( '1' .. '7' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:139:27: '1' .. '7'
            {
            matchRange('1','7'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_OCTAL_RANGE"

    // $ANTLR start "DIGIT_DECIMAL_RANGE"
    public final void mDIGIT_DECIMAL_RANGE() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:142:27: ( DIGIT_OCTAL_RANGE | '8' .. '9' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            if ( (input.LA(1)>='1' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_DECIMAL_RANGE"

    // $ANTLR start "DIGIT_FULL_RANGE"
    public final void mDIGIT_FULL_RANGE() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:145:24: ( DIGIT_ZERO | DIGIT_DECIMAL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT_FULL_RANGE"

    // $ANTLR start "OCTALDIGIT"
    public final void mOCTALDIGIT() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:148:18: ( DIGIT_ZERO | DIGIT_OCTAL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='7') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "OCTALDIGIT"

    // $ANTLR start "DIGIT"
    public final void mDIGIT() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:151:13: ( DIGIT_OCTAL_RANGE | '8' | '9' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            {
            if ( (input.LA(1)>='1' && input.LA(1)<='9') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGIT"

    // $ANTLR start "DIGITS"
    public final void mDIGITS() throws RecognitionException {
        try {
            int _type = DIGITS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:153:8: ( DIGIT_ZERO | ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* ) )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='0') ) {
                alt10=1;
            }
            else if ( ((LA10_0>='1' && LA10_0<='9')) ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:153:10: DIGIT_ZERO
                    {
                    mDIGIT_ZERO(); 

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:154:19: ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* )
                    {
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:154:19: ( DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )* )
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:154:20: DIGIT_DECIMAL_RANGE ( DIGIT_FULL_RANGE )*
                    {
                    mDIGIT_DECIMAL_RANGE(); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:154:40: ( DIGIT_FULL_RANGE )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:154:40: DIGIT_FULL_RANGE
                    	    {
                    	    mDIGIT_FULL_RANGE(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIGITS"

    // $ANTLR start "DecimalLiteral"
    public final void mDecimalLiteral() throws RecognitionException {
        try {
            int _type = DecimalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:156:16: ( DIGIT ( '0' | DIGIT )* ( IntegerTypeSuffix )? )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:156:18: DIGIT ( '0' | DIGIT )* ( IntegerTypeSuffix )?
            {
            mDIGIT(); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:156:23: ( '0' | DIGIT )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:156:38: ( IntegerTypeSuffix )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='L'||LA12_0=='l') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:156:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

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
    // $ANTLR end "DecimalLiteral"

    // $ANTLR start "OctalLiteral"
    public final void mOctalLiteral() throws RecognitionException {
        try {
            int _type = OctalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:14: ( '0' ( OCTALDIGIT )+ ( IntegerTypeSuffix )? )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:16: '0' ( OCTALDIGIT )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:20: ( OCTALDIGIT )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='7')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:20: OCTALDIGIT
            	    {
            	    mOCTALDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:32: ( IntegerTypeSuffix )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='L'||LA14_0=='l') ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:158:32: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

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
    // $ANTLR end "OctalLiteral"

    // $ANTLR start "SUBDATE"
    public final void mSUBDATE() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:162:8: ( DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:162:11: DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_MINUS DIGIT_FULL_RANGE DIGIT_FULL_RANGE
            {
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_MINUS(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_MINUS(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "SUBDATE"

    // $ANTLR start "SUBTIME"
    public final void mSUBTIME() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:170:8: ( DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:170:11: DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE TOK_COLON DIGIT_FULL_RANGE DIGIT_FULL_RANGE
            {
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_COLON(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mTOK_COLON(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "SUBTIME"

    // $ANTLR start "SUBTIMEMILLIS"
    public final void mSUBTIMEMILLIS() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:177:16: ( DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:177:18: DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
            {
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "SUBTIMEMILLIS"

    // $ANTLR start "GMT"
    public final void mGMT() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:180:6: ( DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:180:8: DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE DIGIT_FULL_RANGE
            {
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 
            mDIGIT_FULL_RANGE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "GMT"

    // $ANTLR start "DateLiteral"
    public final void mDateLiteral() throws RecognitionException {
        try {
            int _type = DateLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:182:13: ( '\\'' SUBDATE '\\'' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:182:15: '\\'' SUBDATE '\\''
            {
            match('\''); 
            mSUBDATE(); 
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DateLiteral"

    // $ANTLR start "TimeLiteral"
    public final void mTimeLiteral() throws RecognitionException {
        try {
            int _type = TimeLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:13: ( '\\'' SUBTIME ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )? '\\'' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:15: '\\'' SUBTIME ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )? '\\''
            {
            match('\''); 
            mSUBTIME(); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:28: ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0=='.') ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:29: TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )?
                    {
                    mTOK_DOT(); 
                    mSUBTIMEMILLIS(); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:51: ( ( TOK_PLUS | TOK_MINUS ) GMT )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0=='+'||LA15_0=='-') ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:184:52: ( TOK_PLUS | TOK_MINUS ) GMT
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            mGMT(); 

                            }
                            break;

                    }


                    }
                    break;

            }

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TimeLiteral"

    // $ANTLR start "DateTimeLiteral"
    public final void mDateTimeLiteral() throws RecognitionException {
        try {
            int _type = DateTimeLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:17: ( '\\'' SUBDATE 'T' SUBTIME ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )? '\\'' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:19: '\\'' SUBDATE 'T' SUBTIME ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )? '\\''
            {
            match('\''); 
            mSUBDATE(); 
            match('T'); 
            mSUBTIME(); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:44: ( TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )? )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='.') ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:45: TOK_DOT SUBTIMEMILLIS ( ( TOK_PLUS | TOK_MINUS ) GMT )?
                    {
                    mTOK_DOT(); 
                    mSUBTIMEMILLIS(); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:67: ( ( TOK_PLUS | TOK_MINUS ) GMT )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0=='+'||LA17_0=='-') ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:186:68: ( TOK_PLUS | TOK_MINUS ) GMT
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            mGMT(); 

                            }
                            break;

                    }


                    }
                    break;

            }

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DateTimeLiteral"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:189:9: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' | '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0=='\"') ) {
                alt21=1;
            }
            else if ( (LA21_0=='\'') ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:189:12: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
                    {
                    match('\"'); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:189:16: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
                    loop19:
                    do {
                        int alt19=3;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0=='\\') ) {
                            alt19=1;
                        }
                        else if ( ((LA19_0>='\u0000' && LA19_0<='!')||(LA19_0>='#' && LA19_0<='[')||(LA19_0>=']' && LA19_0<='\uFFFF')) ) {
                            alt19=2;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:189:18: EscapeSequence
                    	    {
                    	    mEscapeSequence(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:189:35: ~ ( '\\\\' | '\"' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    match('\"'); 
                     

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:190:11: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\''
                    {
                    match('\''); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:190:16: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )*
                    loop20:
                    do {
                        int alt20=3;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0=='\\') ) {
                            alt20=1;
                        }
                        else if ( ((LA20_0>='\u0000' && LA20_0<='&')||(LA20_0>='(' && LA20_0<='[')||(LA20_0>=']' && LA20_0<='\uFFFF')) ) {
                            alt20=2;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:190:18: EscapeSequence
                    	    {
                    	    mEscapeSequence(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:190:35: ~ ( '\\'' | '\\\\' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);

                    match('\''); 
                     

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "TOK_APPROXIMATE_NUMERIC_LITERAL"
    public final void mTOK_APPROXIMATE_NUMERIC_LITERAL() throws RecognitionException {
        try {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:196:15: ( ( 'e' | 'E' ) ( '+' | '-' )? DIGITS )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:196:23: ( 'e' | 'E' ) ( '+' | '-' )? DIGITS
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:196:33: ( '+' | '-' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='+'||LA22_0=='-') ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            mDIGITS(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "TOK_APPROXIMATE_NUMERIC_LITERAL"

    // $ANTLR start "TOK_EXACT_NUMERIC_LITERAL"
    public final void mTOK_EXACT_NUMERIC_LITERAL() throws RecognitionException {
        try {
            int _type = TOK_EXACT_NUMERIC_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:201:27: ( '.' DIGITS ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | DIGITS ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )? )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0=='.') ) {
                alt27=1;
            }
            else if ( ((LA27_0>='0' && LA27_0<='9')) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:203:19: '.' DIGITS ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    {
                    match('.'); 
                    mDIGITS(); 
                     _type = TOK_EXACT_NUMERIC_LITERAL; 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:207:19: ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='E'||LA23_0=='e') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:208:23: TOK_APPROXIMATE_NUMERIC_LITERAL
                            {
                            mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                             _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:212:19: DIGITS ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    {
                    mDIGITS(); 
                     _type = DIGIT; 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:216:19: ( '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )? | TOK_APPROXIMATE_NUMERIC_LITERAL )?
                    int alt26=3;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0=='.') ) {
                        alt26=1;
                    }
                    else if ( (LA26_0=='E'||LA26_0=='e') ) {
                        alt26=2;
                    }
                    switch (alt26) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:217:23: '.' ( DIGITS )* ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                            {
                            match('.'); 
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:218:23: ( DIGITS )*
                            loop24:
                            do {
                                int alt24=2;
                                int LA24_0 = input.LA(1);

                                if ( ((LA24_0>='0' && LA24_0<='9')) ) {
                                    alt24=1;
                                }


                                switch (alt24) {
                            	case 1 :
                            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:218:24: DIGITS
                            	    {
                            	    mDIGITS(); 

                            	    }
                            	    break;

                            	default :
                            	    break loop24;
                                }
                            } while (true);

                             _type = TOK_EXACT_NUMERIC_LITERAL; 
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:220:23: ( TOK_APPROXIMATE_NUMERIC_LITERAL )?
                            int alt25=2;
                            int LA25_0 = input.LA(1);

                            if ( (LA25_0=='E'||LA25_0=='e') ) {
                                alt25=1;
                            }
                            switch (alt25) {
                                case 1 :
                                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:220:24: TOK_APPROXIMATE_NUMERIC_LITERAL
                                    {
                                    mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                                     _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:222:24: TOK_APPROXIMATE_NUMERIC_LITERAL
                            {
                            mTOK_APPROXIMATE_NUMERIC_LITERAL(); 
                             _type = TOK_APPROXIMATE_NUMERIC_LITERAL; 

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOK_EXACT_NUMERIC_LITERAL"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:227:19: ( NameFirstCharacter ( NameCharacter )* )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:229:15: NameFirstCharacter ( NameCharacter )*
            {
            mNameFirstCharacter(); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:230:15: ( NameCharacter )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>='0' && LA28_0<='9')||(LA28_0>='A' && LA28_0<='Z')||LA28_0=='_'||(LA28_0>='a' && LA28_0<='z')) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:230:17: NameCharacter
            	    {
            	    mNameCharacter(); 

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:237:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:237:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "NewLine"
    public final void mNewLine() throws RecognitionException {
        try {
            int _type = NewLine;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:241:9: ( ( '\\n' | '\\r\\n' | '\\r' ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:242:15: ( '\\n' | '\\r\\n' | '\\r' )
            {
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:242:15: ( '\\n' | '\\r\\n' | '\\r' )
            int alt29=3;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='\n') ) {
                alt29=1;
            }
            else if ( (LA29_0=='\r') ) {
                int LA29_2 = input.LA(2);

                if ( (LA29_2=='\n') ) {
                    alt29=2;
                }
                else {
                    alt29=3;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:243:16: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:244:19: '\\r\\n'
                    {
                    match("\r\n"); 


                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:245:19: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

             /* newline();*/ _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NewLine"

    // $ANTLR start "CommentLine"
    public final void mCommentLine() throws RecognitionException {
        try {
            int _type = CommentLine;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:252:13: ( '/' '/' (~ '\\n' )* ( '\\n' | EOF ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:253:15: '/' '/' (~ '\\n' )* ( '\\n' | EOF )
            {
            match('/'); 
            match('/'); 
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:254:15: (~ '\\n' )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>='\u0000' && LA30_0<='\t')||(LA30_0>='\u000B' && LA30_0<='\uFFFF')) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:255:19: ~ '\\n'
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:257:15: ( '\\n' | EOF )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0=='\n') ) {
                alt31=1;
            }
            else {
                alt31=2;}
            switch (alt31) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:257:16: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:257:23: EOF
                    {
                    match(EOF); 

                    }
                    break;

            }

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CommentLine"

    // $ANTLR start "SLComment"
    public final void mSLComment() throws RecognitionException {
        try {
            int _type = SLComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:263:11: ( '--' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? | EOF ) )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:263:14: '--' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? | EOF )
            {
            match("--"); 

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:23: (~ ( '\\n' | '\\r' ) )*
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( ((LA32_0>='\u0000' && LA32_0<='\t')||(LA32_0>='\u000B' && LA32_0<='\f')||(LA32_0>='\u000E' && LA32_0<='\uFFFF')) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:24: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop32;
                }
            } while (true);

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:39: ( '\\n' | '\\r' ( '\\n' )? | EOF )
            int alt34=3;
            switch ( input.LA(1) ) {
            case '\n':
                {
                alt34=1;
                }
                break;
            case '\r':
                {
                alt34=2;
                }
                break;
            default:
                alt34=3;}

            switch (alt34) {
                case 1 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:40: '\\n'
                    {
                    match('\n'); 

                    }
                    break;
                case 2 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:45: '\\r' ( '\\n' )?
                    {
                    match('\r'); 
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:49: ( '\\n' )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0=='\n') ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:50: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:264:57: EOF
                    {
                    match(EOF); 

                    }
                    break;

            }

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLComment"

    // $ANTLR start "MultiLineComment"
    public final void mMultiLineComment() throws RecognitionException {
        try {
            int _type = MultiLineComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:274:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:274:15: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:274:20: ( options {greedy=false; } : . )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0=='*') ) {
                    int LA35_1 = input.LA(2);

                    if ( (LA35_1=='/') ) {
                        alt35=2;
                    }
                    else if ( ((LA35_1>='\u0000' && LA35_1<='.')||(LA35_1>='0' && LA35_1<='\uFFFF')) ) {
                        alt35=1;
                    }


                }
                else if ( ((LA35_0>='\u0000' && LA35_0<=')')||(LA35_0>='+' && LA35_0<='\uFFFF')) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:274:47: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            match("*/"); 

             _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MultiLineComment"

    public void mTokens() throws RecognitionException {
        // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:8: ( TOK_NOTLIKE | TOK_NOR | TOK_NOTIN | TOK_NOTBETWEEN | TOK_ISNOT | TOK_RPAREN | TOK_LPAREN | TOK_COMMA | TOK_DOT | TOK_COLON | TOK_CONCAT | TOK_EQ | TOK_PLUS | TOK_MINUS | TOK_SLASH | TOK_STAR | TOK_LE | TOK_GE | TOK_NE | TOK_LT | TOK_GT | TOK_DOLLAR | TOK_HASH | TOK_AT | TOK_IN | TOK_OR | TOK_AND | TOK_LIKE | TOK_BETWEEN | TOK_MOD | TOK_ABS | TOK_NOT | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_DOUBLE | TOK_IS | HexLiteral | DIGITS | DecimalLiteral | OctalLiteral | DateLiteral | TimeLiteral | DateTimeLiteral | StringLiteral | TOK_EXACT_NUMERIC_LITERAL | Identifier | WS | NewLine | CommentLine | SLComment | MultiLineComment )
        int alt36=52;
        alt36 = dfa36.predict(input);
        switch (alt36) {
            case 1 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:10: TOK_NOTLIKE
                {
                mTOK_NOTLIKE(); 

                }
                break;
            case 2 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:22: TOK_NOR
                {
                mTOK_NOR(); 

                }
                break;
            case 3 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:30: TOK_NOTIN
                {
                mTOK_NOTIN(); 

                }
                break;
            case 4 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:40: TOK_NOTBETWEEN
                {
                mTOK_NOTBETWEEN(); 

                }
                break;
            case 5 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:55: TOK_ISNOT
                {
                mTOK_ISNOT(); 

                }
                break;
            case 6 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:65: TOK_RPAREN
                {
                mTOK_RPAREN(); 

                }
                break;
            case 7 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:76: TOK_LPAREN
                {
                mTOK_LPAREN(); 

                }
                break;
            case 8 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:87: TOK_COMMA
                {
                mTOK_COMMA(); 

                }
                break;
            case 9 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:97: TOK_DOT
                {
                mTOK_DOT(); 

                }
                break;
            case 10 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:105: TOK_COLON
                {
                mTOK_COLON(); 

                }
                break;
            case 11 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:115: TOK_CONCAT
                {
                mTOK_CONCAT(); 

                }
                break;
            case 12 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:126: TOK_EQ
                {
                mTOK_EQ(); 

                }
                break;
            case 13 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:133: TOK_PLUS
                {
                mTOK_PLUS(); 

                }
                break;
            case 14 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:142: TOK_MINUS
                {
                mTOK_MINUS(); 

                }
                break;
            case 15 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:152: TOK_SLASH
                {
                mTOK_SLASH(); 

                }
                break;
            case 16 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:162: TOK_STAR
                {
                mTOK_STAR(); 

                }
                break;
            case 17 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:171: TOK_LE
                {
                mTOK_LE(); 

                }
                break;
            case 18 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:178: TOK_GE
                {
                mTOK_GE(); 

                }
                break;
            case 19 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:185: TOK_NE
                {
                mTOK_NE(); 

                }
                break;
            case 20 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:192: TOK_LT
                {
                mTOK_LT(); 

                }
                break;
            case 21 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:199: TOK_GT
                {
                mTOK_GT(); 

                }
                break;
            case 22 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:206: TOK_DOLLAR
                {
                mTOK_DOLLAR(); 

                }
                break;
            case 23 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:217: TOK_HASH
                {
                mTOK_HASH(); 

                }
                break;
            case 24 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:226: TOK_AT
                {
                mTOK_AT(); 

                }
                break;
            case 25 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:233: TOK_IN
                {
                mTOK_IN(); 

                }
                break;
            case 26 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:240: TOK_OR
                {
                mTOK_OR(); 

                }
                break;
            case 27 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:247: TOK_AND
                {
                mTOK_AND(); 

                }
                break;
            case 28 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:255: TOK_LIKE
                {
                mTOK_LIKE(); 

                }
                break;
            case 29 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:264: TOK_BETWEEN
                {
                mTOK_BETWEEN(); 

                }
                break;
            case 30 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:276: TOK_MOD
                {
                mTOK_MOD(); 

                }
                break;
            case 31 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:284: TOK_ABS
                {
                mTOK_ABS(); 

                }
                break;
            case 32 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:292: TOK_NOT
                {
                mTOK_NOT(); 

                }
                break;
            case 33 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:300: TOK_NULL
                {
                mTOK_NULL(); 

                }
                break;
            case 34 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:309: TOK_TRUE
                {
                mTOK_TRUE(); 

                }
                break;
            case 35 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:318: TOK_FALSE
                {
                mTOK_FALSE(); 

                }
                break;
            case 36 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:328: TOK_DOUBLE
                {
                mTOK_DOUBLE(); 

                }
                break;
            case 37 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:339: TOK_IS
                {
                mTOK_IS(); 

                }
                break;
            case 38 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:346: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 39 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:357: DIGITS
                {
                mDIGITS(); 

                }
                break;
            case 40 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:364: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 41 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:379: OctalLiteral
                {
                mOctalLiteral(); 

                }
                break;
            case 42 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:392: DateLiteral
                {
                mDateLiteral(); 

                }
                break;
            case 43 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:404: TimeLiteral
                {
                mTimeLiteral(); 

                }
                break;
            case 44 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:416: DateTimeLiteral
                {
                mDateTimeLiteral(); 

                }
                break;
            case 45 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:432: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 46 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:446: TOK_EXACT_NUMERIC_LITERAL
                {
                mTOK_EXACT_NUMERIC_LITERAL(); 

                }
                break;
            case 47 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:472: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 48 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:483: WS
                {
                mWS(); 

                }
                break;
            case 49 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:486: NewLine
                {
                mNewLine(); 

                }
                break;
            case 50 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:494: CommentLine
                {
                mCommentLine(); 

                }
                break;
            case 51 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:506: SLComment
                {
                mSLComment(); 

                }
                break;
            case 52 :
                // /home/bala/be/5.3/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/condition/grammar/filter.g:1:516: MultiLineComment
                {
                mMultiLineComment(); 

                }
                break;

        }

    }


    protected DFA36 dfa36 = new DFA36(this);
    static final String DFA36_eotS =
        "\1\uffff\2\43\3\uffff\1\55\4\uffff\1\60\1\63\1\uffff\1\65\1\67\4"+
        "\uffff\6\43\1\uffff\4\43\2\103\4\uffff\1\46\1\uffff\3\43\1\115\1"+
        "\116\1\115\13\uffff\1\117\10\43\3\uffff\1\103\3\uffff\1\134\1\135"+
        "\1\134\2\43\3\uffff\1\140\1\141\2\43\1\32\3\43\1\uffff\3\43\2\uffff"+
        "\1\154\1\43\2\uffff\1\156\1\43\1\160\2\43\2\uffff\1\43\1\166\1\43"+
        "\1\uffff\1\170\1\uffff\1\43\1\uffff\1\172\1\43\2\uffff\1\43\1\uffff"+
        "\1\43\1\uffff\1\43\1\uffff\1\u0081\2\uffff\1\u0084\1\43\1\u0086"+
        "\4\uffff\1\43\3\uffff\1\43\2\uffff\1\u0090\44\uffff";
    static final String DFA36_eofS =
        "\u00b1\uffff";
    static final String DFA36_minS =
        "\1\11\1\117\1\116\3\uffff\1\60\4\uffff\1\55\1\52\1\uffff\2\75\4"+
        "\uffff\1\116\1\122\1\102\1\111\1\105\1\117\1\uffff\1\117\1\122\1"+
        "\101\1\117\2\56\1\0\3\uffff\1\12\1\uffff\2\124\1\114\3\60\13\uffff"+
        "\1\60\1\104\1\123\1\113\1\124\1\104\1\125\1\114\1\125\3\uffff\1"+
        "\56\1\uffff\1\0\1\uffff\3\60\1\114\1\157\3\uffff\2\60\1\105\1\127"+
        "\1\60\1\105\1\123\1\102\1\0\1\151\1\156\1\145\2\uffff\1\60\1\164"+
        "\2\uffff\1\60\1\105\1\60\1\105\1\114\2\0\1\153\1\60\1\164\1\uffff"+
        "\1\60\1\uffff\1\105\1\uffff\1\60\1\105\2\0\1\145\1\uffff\1\167\1"+
        "\uffff\1\116\1\uffff\1\60\2\0\1\60\1\145\1\60\1\uffff\2\0\1\uffff"+
        "\1\145\1\uffff\2\0\1\156\2\0\1\60\1\0\1\uffff\1\0\1\uffff\1\0\1"+
        "\uffff\2\0\1\uffff\2\0\1\uffff\15\0\1\uffff\1\0\1\uffff\10\0";
    static final String DFA36_maxS =
        "\1\174\1\165\1\163\3\uffff\1\71\4\uffff\1\55\1\57\1\uffff\1\76\1"+
        "\75\4\uffff\1\163\1\162\1\156\1\151\1\145\1\157\1\uffff\1\165\1"+
        "\162\1\141\1\157\1\170\1\154\1\uffff\3\uffff\1\12\1\uffff\2\164"+
        "\1\154\3\172\13\uffff\1\172\1\144\1\163\1\153\1\164\1\144\1\165"+
        "\1\154\1\165\3\uffff\1\154\1\uffff\1\uffff\1\uffff\3\172\1\154\1"+
        "\157\3\uffff\2\172\1\145\1\167\1\172\1\145\1\163\1\142\1\uffff\1"+
        "\151\1\156\1\145\2\uffff\1\172\1\164\2\uffff\1\172\1\145\1\172\1"+
        "\145\1\154\2\uffff\1\153\1\172\1\164\1\uffff\1\172\1\uffff\1\145"+
        "\1\uffff\1\172\1\145\2\uffff\1\145\1\uffff\1\167\1\uffff\1\156\1"+
        "\uffff\1\172\2\uffff\1\172\1\145\1\172\1\uffff\2\uffff\1\uffff\1"+
        "\145\1\uffff\2\uffff\1\156\2\uffff\1\172\1\uffff\1\uffff\1\uffff"+
        "\1\uffff\1\uffff\1\uffff\2\uffff\1\uffff\2\uffff\1\uffff\15\uffff"+
        "\1\uffff\1\uffff\1\uffff\10\uffff";
    static final String DFA36_acceptS =
        "\3\uffff\1\6\1\7\1\10\1\uffff\1\12\1\13\1\14\1\15\2\uffff\1\20\2"+
        "\uffff\1\23\1\26\1\27\1\30\6\uffff\1\36\7\uffff\1\55\1\57\1\60\1"+
        "\uffff\1\60\6\uffff\1\11\1\56\1\63\1\16\1\62\1\64\1\17\1\21\1\24"+
        "\1\22\1\25\11\uffff\1\46\1\51\1\47\1\uffff\1\50\1\uffff\1\61\5\uffff"+
        "\1\45\1\31\1\32\14\uffff\1\40\1\2\2\uffff\1\33\1\37\12\uffff\1\41"+
        "\1\uffff\1\34\1\uffff\1\42\5\uffff\1\3\1\uffff\1\5\1\uffff\1\43"+
        "\6\uffff\1\44\2\uffff\1\1\1\uffff\1\35\7\uffff\1\53\1\uffff\1\4"+
        "\1\uffff\1\53\2\uffff\1\52\2\uffff\1\52\15\uffff\1\54\1\uffff\1"+
        "\54\10\uffff";
    static final String DFA36_specialS =
        "\41\uffff\1\45\44\uffff\1\20\21\uffff\1\52\16\uffff\1\5\1\51\12"+
        "\uffff\1\23\1\13\7\uffff\1\2\1\16\4\uffff\1\6\1\53\3\uffff\1\25"+
        "\1\14\1\uffff\1\12\1\17\1\uffff\1\43\1\uffff\1\1\1\uffff\1\30\1"+
        "\uffff\1\0\1\32\1\uffff\1\21\1\44\1\uffff\1\24\1\27\1\3\1\34\1\7"+
        "\1\36\1\26\1\37\1\4\1\11\1\10\1\31\1\47\1\uffff\1\50\1\uffff\1\33"+
        "\1\35\1\15\1\46\1\40\1\41\1\42\1\22}>";
    static final String[] DFA36_transitionS = {
            "\1\46\1\44\1\uffff\1\46\1\45\22\uffff\1\46\1\20\1\42\1\22\1"+
            "\21\1\32\1\uffff\1\41\1\4\1\3\1\15\1\12\1\5\1\13\1\6\1\14\1"+
            "\37\11\40\1\7\1\uffff\1\16\1\11\1\17\1\uffff\1\23\1\26\1\30"+
            "\1\43\1\36\1\43\1\35\2\43\1\24\2\43\1\27\1\31\1\33\1\25\4\43"+
            "\1\34\6\43\4\uffff\1\43\1\uffff\1\26\1\30\1\43\1\36\1\43\1\35"+
            "\2\43\1\2\2\43\1\27\1\31\1\1\1\25\4\43\1\34\6\43\1\uffff\1\10",
            "\1\50\5\uffff\1\51\31\uffff\1\47\5\uffff\1\51",
            "\1\53\4\uffff\1\54\32\uffff\1\53\4\uffff\1\52",
            "",
            "",
            "",
            "\12\56",
            "",
            "",
            "",
            "",
            "\1\57",
            "\1\62\4\uffff\1\61",
            "",
            "\1\64\1\20",
            "\1\66",
            "",
            "",
            "",
            "",
            "\1\53\4\uffff\1\54\32\uffff\1\53\4\uffff\1\54",
            "\1\70\37\uffff\1\70",
            "\1\72\13\uffff\1\71\23\uffff\1\72\13\uffff\1\71",
            "\1\73\37\uffff\1\73",
            "\1\74\37\uffff\1\74",
            "\1\75\37\uffff\1\75",
            "",
            "\1\50\5\uffff\1\51\31\uffff\1\50\5\uffff\1\51",
            "\1\76\37\uffff\1\76",
            "\1\77\37\uffff\1\77",
            "\1\100\37\uffff\1\100",
            "\1\56\1\uffff\10\102\15\uffff\1\56\22\uffff\1\101\14\uffff"+
            "\1\56\22\uffff\1\101",
            "\1\56\1\uffff\12\104\13\uffff\1\56\6\uffff\1\105\30\uffff\1"+
            "\56\6\uffff\1\105",
            "\60\42\12\106\uffc6\42",
            "",
            "",
            "",
            "\1\107",
            "",
            "\1\112\35\uffff\1\111\1\uffff\1\110",
            "\1\112\37\uffff\1\112",
            "\1\113\37\uffff\1\113",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\15\43\1\114\14\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\120\37\uffff\1\120",
            "\1\121\37\uffff\1\121",
            "\1\122\37\uffff\1\122",
            "\1\123\37\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "\1\125\37\uffff\1\125",
            "\1\126\37\uffff\1\126",
            "\1\127\37\uffff\1\127",
            "",
            "",
            "",
            "\1\56\1\uffff\12\104\13\uffff\1\56\6\uffff\1\105\30\uffff\1"+
            "\56\6\uffff\1\105",
            "",
            "\60\42\12\130\uffc6\42",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\1\43\1\133\6\43\1"+
            "\132\2\43\1\131\16\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\136\37\uffff\1\136",
            "\1\137",
            "",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\142\37\uffff\1\142",
            "\1\143\37\uffff\1\143",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\144\37\uffff\1\144",
            "\1\145\37\uffff\1\145",
            "\1\146\37\uffff\1\146",
            "\60\42\12\150\1\147\uffc5\42",
            "\1\151",
            "\1\152",
            "\1\153",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\155",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\157\37\uffff\1\157",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\161\37\uffff\1\161",
            "\1\162\37\uffff\1\162",
            "\60\42\12\163\uffc6\42",
            "\60\42\12\164\uffc6\42",
            "\1\165",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\167",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\1\171\37\uffff\1\171",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\173\37\uffff\1\173",
            "\60\42\12\174\uffc6\42",
            "\55\42\1\175\uffd2\42",
            "\1\176",
            "",
            "\1\177",
            "",
            "\1\u0080\37\uffff\1\u0080",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\72\42\1\u0082\uffc5\42",
            "\60\42\12\u0083\uffc6\42",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u0085",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\60\42\12\u0087\uffc6\42",
            "\60\42\12\u0088\uffc6\42",
            "",
            "\1\u0089",
            "",
            "\60\42\12\u008a\uffc6\42",
            "\55\42\1\u008b\uffd2\42",
            "\1\u008c",
            "\47\42\1\u008e\6\42\1\u008d\uffd1\42",
            "\60\42\12\u008f\uffc6\42",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\60\42\12\u0091\uffc6\42",
            "",
            "\60\42\12\u0093\uffc6\42",
            "",
            "\60\42\12\u0094\uffc6\42",
            "",
            "\47\42\1\u0095\54\42\1\u0096\uffab\42",
            "\60\42\12\u0097\uffc6\42",
            "",
            "\60\42\12\u0099\uffc6\42",
            "\47\42\1\u008e\3\42\1\u009a\1\42\1\u009a\uffd2\42",
            "",
            "\60\42\12\u009b\uffc6\42",
            "\60\42\12\u009c\uffc6\42",
            "\72\42\1\u009d\uffc5\42",
            "\60\42\12\u009e\uffc6\42",
            "\60\42\12\u009f\uffc6\42",
            "\60\42\12\u00a0\uffc6\42",
            "\60\42\12\u00a1\uffc6\42",
            "\60\42\12\u00a2\uffc6\42",
            "\72\42\1\u00a3\uffc5\42",
            "\47\42\1\u008e\uffd8\42",
            "\60\42\12\u00a4\uffc6\42",
            "\60\42\12\u00a5\uffc6\42",
            "\47\42\1\u00a6\6\42\1\u00a7\uffd1\42",
            "",
            "\60\42\12\u00a9\uffc6\42",
            "",
            "\60\42\12\u00aa\uffc6\42",
            "\60\42\12\u00ab\uffc6\42",
            "\47\42\1\u00a6\3\42\1\u00ac\1\42\1\u00ac\uffd2\42",
            "\60\42\12\u00ad\uffc6\42",
            "\60\42\12\u00ae\uffc6\42",
            "\60\42\12\u00af\uffc6\42",
            "\60\42\12\u00b0\uffc6\42",
            "\47\42\1\u00a6\uffd8\42"
    };

    static final short[] DFA36_eot = DFA.unpackEncodedString(DFA36_eotS);
    static final short[] DFA36_eof = DFA.unpackEncodedString(DFA36_eofS);
    static final char[] DFA36_min = DFA.unpackEncodedStringToUnsignedChars(DFA36_minS);
    static final char[] DFA36_max = DFA.unpackEncodedStringToUnsignedChars(DFA36_maxS);
    static final short[] DFA36_accept = DFA.unpackEncodedString(DFA36_acceptS);
    static final short[] DFA36_special = DFA.unpackEncodedString(DFA36_specialS);
    static final short[][] DFA36_transition;

    static {
        int numStates = DFA36_transitionS.length;
        DFA36_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA36_transition[i] = DFA.unpackEncodedString(DFA36_transitionS[i]);
        }
    }

    class DFA36 extends DFA {

        public DFA36(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 36;
            this.eot = DFA36_eot;
            this.eof = DFA36_eof;
            this.min = DFA36_min;
            this.max = DFA36_max;
            this.accept = DFA36_accept;
            this.special = DFA36_special;
            this.transition = DFA36_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TOK_NOTLIKE | TOK_NOR | TOK_NOTIN | TOK_NOTBETWEEN | TOK_ISNOT | TOK_RPAREN | TOK_LPAREN | TOK_COMMA | TOK_DOT | TOK_COLON | TOK_CONCAT | TOK_EQ | TOK_PLUS | TOK_MINUS | TOK_SLASH | TOK_STAR | TOK_LE | TOK_GE | TOK_NE | TOK_LT | TOK_GT | TOK_DOLLAR | TOK_HASH | TOK_AT | TOK_IN | TOK_OR | TOK_AND | TOK_LIKE | TOK_BETWEEN | TOK_MOD | TOK_ABS | TOK_NOT | TOK_NULL | TOK_TRUE | TOK_FALSE | TOK_DOUBLE | TOK_IS | HexLiteral | DIGITS | DecimalLiteral | OctalLiteral | DateLiteral | TimeLiteral | DateTimeLiteral | StringLiteral | TOK_EXACT_NUMERIC_LITERAL | Identifier | WS | NewLine | CommentLine | SLComment | MultiLineComment );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA36_147 = input.LA(1);

                        s = -1;
                        if ( (LA36_147=='\'') ) {s = 149;}

                        else if ( (LA36_147=='T') ) {s = 150;}

                        else if ( ((LA36_147>='\u0000' && LA36_147<='&')||(LA36_147>='(' && LA36_147<='S')||(LA36_147>='U' && LA36_147<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA36_143 = input.LA(1);

                        s = -1;
                        if ( ((LA36_143>='0' && LA36_143<='9')) ) {s = 147;}

                        else if ( ((LA36_143>='\u0000' && LA36_143<='/')||(LA36_143>=':' && LA36_143<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA36_124 = input.LA(1);

                        s = -1;
                        if ( (LA36_124==':') ) {s = 130;}

                        else if ( ((LA36_124>='\u0000' && LA36_124<='9')||(LA36_124>=';' && LA36_124<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA36_155 = input.LA(1);

                        s = -1;
                        if ( (LA36_155==':') ) {s = 157;}

                        else if ( ((LA36_155>='\u0000' && LA36_155<='9')||(LA36_155>=';' && LA36_155<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA36_161 = input.LA(1);

                        s = -1;
                        if ( (LA36_161==':') ) {s = 163;}

                        else if ( ((LA36_161>='\u0000' && LA36_161<='9')||(LA36_161>=';' && LA36_161<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA36_103 = input.LA(1);

                        s = -1;
                        if ( ((LA36_103>='0' && LA36_103<='9')) ) {s = 115;}

                        else if ( ((LA36_103>='\u0000' && LA36_103<='/')||(LA36_103>=':' && LA36_103<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA36_130 = input.LA(1);

                        s = -1;
                        if ( ((LA36_130>='0' && LA36_130<='9')) ) {s = 135;}

                        else if ( ((LA36_130>='\u0000' && LA36_130<='/')||(LA36_130>=':' && LA36_130<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA36_157 = input.LA(1);

                        s = -1;
                        if ( ((LA36_157>='0' && LA36_157<='9')) ) {s = 159;}

                        else if ( ((LA36_157>='\u0000' && LA36_157<='/')||(LA36_157>=':' && LA36_157<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA36_163 = input.LA(1);

                        s = -1;
                        if ( ((LA36_163>='0' && LA36_163<='9')) ) {s = 164;}

                        else if ( ((LA36_163>='\u0000' && LA36_163<='/')||(LA36_163>=':' && LA36_163<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA36_162 = input.LA(1);

                        s = -1;
                        if ( (LA36_162=='\'') ) {s = 142;}

                        else if ( ((LA36_162>='\u0000' && LA36_162<='&')||(LA36_162>='(' && LA36_162<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA36_138 = input.LA(1);

                        s = -1;
                        if ( (LA36_138=='.') ) {s = 141;}

                        else if ( (LA36_138=='\'') ) {s = 142;}

                        else if ( ((LA36_138>='\u0000' && LA36_138<='&')||(LA36_138>='(' && LA36_138<='-')||(LA36_138>='/' && LA36_138<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA36_116 = input.LA(1);

                        s = -1;
                        if ( (LA36_116=='-') ) {s = 125;}

                        else if ( ((LA36_116>='\u0000' && LA36_116<=',')||(LA36_116>='.' && LA36_116<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA36_136 = input.LA(1);

                        s = -1;
                        if ( (LA36_136=='-') ) {s = 139;}

                        else if ( ((LA36_136>='\u0000' && LA36_136<=',')||(LA36_136>='.' && LA36_136<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA36_171 = input.LA(1);

                        s = -1;
                        if ( (LA36_171=='+'||LA36_171=='-') ) {s = 172;}

                        else if ( (LA36_171=='\'') ) {s = 166;}

                        else if ( ((LA36_171>='\u0000' && LA36_171<='&')||(LA36_171>='(' && LA36_171<='*')||LA36_171==','||(LA36_171>='.' && LA36_171<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA36_125 = input.LA(1);

                        s = -1;
                        if ( ((LA36_125>='0' && LA36_125<='9')) ) {s = 131;}

                        else if ( ((LA36_125>='\u0000' && LA36_125<='/')||(LA36_125>=':' && LA36_125<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA36_139 = input.LA(1);

                        s = -1;
                        if ( ((LA36_139>='0' && LA36_139<='9')) ) {s = 143;}

                        else if ( ((LA36_139>='\u0000' && LA36_139<='/')||(LA36_139>=':' && LA36_139<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA36_70 = input.LA(1);

                        s = -1;
                        if ( ((LA36_70>='0' && LA36_70<='9')) ) {s = 88;}

                        else if ( ((LA36_70>='\u0000' && LA36_70<='/')||(LA36_70>=':' && LA36_70<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA36_150 = input.LA(1);

                        s = -1;
                        if ( ((LA36_150>='0' && LA36_150<='9')) ) {s = 153;}

                        else if ( ((LA36_150>='\u0000' && LA36_150<='/')||(LA36_150>=':' && LA36_150<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA36_176 = input.LA(1);

                        s = -1;
                        if ( (LA36_176=='\'') ) {s = 166;}

                        else if ( ((LA36_176>='\u0000' && LA36_176<='&')||(LA36_176>='(' && LA36_176<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA36_115 = input.LA(1);

                        s = -1;
                        if ( ((LA36_115>='0' && LA36_115<='9')) ) {s = 124;}

                        else if ( ((LA36_115>='\u0000' && LA36_115<='/')||(LA36_115>=':' && LA36_115<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA36_153 = input.LA(1);

                        s = -1;
                        if ( ((LA36_153>='0' && LA36_153<='9')) ) {s = 155;}

                        else if ( ((LA36_153>='\u0000' && LA36_153<='/')||(LA36_153>=':' && LA36_153<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA36_135 = input.LA(1);

                        s = -1;
                        if ( ((LA36_135>='0' && LA36_135<='9')) ) {s = 138;}

                        else if ( ((LA36_135>='\u0000' && LA36_135<='/')||(LA36_135>=':' && LA36_135<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA36_159 = input.LA(1);

                        s = -1;
                        if ( ((LA36_159>='0' && LA36_159<='9')) ) {s = 161;}

                        else if ( ((LA36_159>='\u0000' && LA36_159<='/')||(LA36_159>=':' && LA36_159<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA36_154 = input.LA(1);

                        s = -1;
                        if ( ((LA36_154>='0' && LA36_154<='9')) ) {s = 156;}

                        else if ( ((LA36_154>='\u0000' && LA36_154<='/')||(LA36_154>=':' && LA36_154<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA36_145 = input.LA(1);

                        s = -1;
                        if ( ((LA36_145>='0' && LA36_145<='9')) ) {s = 148;}

                        else if ( ((LA36_145>='\u0000' && LA36_145<='/')||(LA36_145>=':' && LA36_145<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA36_164 = input.LA(1);

                        s = -1;
                        if ( ((LA36_164>='0' && LA36_164<='9')) ) {s = 165;}

                        else if ( ((LA36_164>='\u0000' && LA36_164<='/')||(LA36_164>=':' && LA36_164<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA36_148 = input.LA(1);

                        s = -1;
                        if ( ((LA36_148>='0' && LA36_148<='9')) ) {s = 151;}

                        else if ( ((LA36_148>='\u0000' && LA36_148<='/')||(LA36_148>=':' && LA36_148<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA36_169 = input.LA(1);

                        s = -1;
                        if ( ((LA36_169>='0' && LA36_169<='9')) ) {s = 170;}

                        else if ( ((LA36_169>='\u0000' && LA36_169<='/')||(LA36_169>=':' && LA36_169<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA36_156 = input.LA(1);

                        s = -1;
                        if ( ((LA36_156>='0' && LA36_156<='9')) ) {s = 158;}

                        else if ( ((LA36_156>='\u0000' && LA36_156<='/')||(LA36_156>=':' && LA36_156<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA36_170 = input.LA(1);

                        s = -1;
                        if ( ((LA36_170>='0' && LA36_170<='9')) ) {s = 171;}

                        else if ( ((LA36_170>='\u0000' && LA36_170<='/')||(LA36_170>=':' && LA36_170<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA36_158 = input.LA(1);

                        s = -1;
                        if ( ((LA36_158>='0' && LA36_158<='9')) ) {s = 160;}

                        else if ( ((LA36_158>='\u0000' && LA36_158<='/')||(LA36_158>=':' && LA36_158<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA36_160 = input.LA(1);

                        s = -1;
                        if ( ((LA36_160>='0' && LA36_160<='9')) ) {s = 162;}

                        else if ( ((LA36_160>='\u0000' && LA36_160<='/')||(LA36_160>=':' && LA36_160<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA36_173 = input.LA(1);

                        s = -1;
                        if ( ((LA36_173>='0' && LA36_173<='9')) ) {s = 174;}

                        else if ( ((LA36_173>='\u0000' && LA36_173<='/')||(LA36_173>=':' && LA36_173<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA36_174 = input.LA(1);

                        s = -1;
                        if ( ((LA36_174>='0' && LA36_174<='9')) ) {s = 175;}

                        else if ( ((LA36_174>='\u0000' && LA36_174<='/')||(LA36_174>=':' && LA36_174<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA36_175 = input.LA(1);

                        s = -1;
                        if ( ((LA36_175>='0' && LA36_175<='9')) ) {s = 176;}

                        else if ( ((LA36_175>='\u0000' && LA36_175<='/')||(LA36_175>=':' && LA36_175<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA36_141 = input.LA(1);

                        s = -1;
                        if ( ((LA36_141>='0' && LA36_141<='9')) ) {s = 145;}

                        else if ( ((LA36_141>='\u0000' && LA36_141<='/')||(LA36_141>=':' && LA36_141<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA36_151 = input.LA(1);

                        s = -1;
                        if ( (LA36_151=='+'||LA36_151=='-') ) {s = 154;}

                        else if ( (LA36_151=='\'') ) {s = 142;}

                        else if ( ((LA36_151>='\u0000' && LA36_151<='&')||(LA36_151>='(' && LA36_151<='*')||LA36_151==','||(LA36_151>='.' && LA36_151<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA36_33 = input.LA(1);

                        s = -1;
                        if ( ((LA36_33>='0' && LA36_33<='9')) ) {s = 70;}

                        else if ( ((LA36_33>='\u0000' && LA36_33<='/')||(LA36_33>=':' && LA36_33<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA36_172 = input.LA(1);

                        s = -1;
                        if ( ((LA36_172>='0' && LA36_172<='9')) ) {s = 173;}

                        else if ( ((LA36_172>='\u0000' && LA36_172<='/')||(LA36_172>=':' && LA36_172<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA36_165 = input.LA(1);

                        s = -1;
                        if ( (LA36_165=='\'') ) {s = 166;}

                        else if ( ((LA36_165>='\u0000' && LA36_165<='&')||(LA36_165>='(' && LA36_165<='-')||(LA36_165>='/' && LA36_165<='\uFFFF')) ) {s = 34;}

                        else if ( (LA36_165=='.') ) {s = 167;}

                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA36_167 = input.LA(1);

                        s = -1;
                        if ( ((LA36_167>='0' && LA36_167<='9')) ) {s = 169;}

                        else if ( ((LA36_167>='\u0000' && LA36_167<='/')||(LA36_167>=':' && LA36_167<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA36_104 = input.LA(1);

                        s = -1;
                        if ( ((LA36_104>='0' && LA36_104<='9')) ) {s = 116;}

                        else if ( ((LA36_104>='\u0000' && LA36_104<='/')||(LA36_104>=':' && LA36_104<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA36_88 = input.LA(1);

                        s = -1;
                        if ( (LA36_88==':') ) {s = 103;}

                        else if ( ((LA36_88>='0' && LA36_88<='9')) ) {s = 104;}

                        else if ( ((LA36_88>='\u0000' && LA36_88<='/')||(LA36_88>=';' && LA36_88<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA36_131 = input.LA(1);

                        s = -1;
                        if ( ((LA36_131>='0' && LA36_131<='9')) ) {s = 136;}

                        else if ( ((LA36_131>='\u0000' && LA36_131<='/')||(LA36_131>=':' && LA36_131<='\uFFFF')) ) {s = 34;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 36, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}