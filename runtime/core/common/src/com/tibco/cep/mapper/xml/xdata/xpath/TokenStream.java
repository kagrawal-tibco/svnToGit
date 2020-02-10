package com.tibco.cep.mapper.xml.xdata.xpath;


/**
 * A stream interface for accessing tokens, has helpful utility function.
 */
public class TokenStream
{
    private final Token[] mTokens;
    private final Token mEOFToken;
    private int mPosition;

    public TokenStream(Token[] tokens) {
        mTokens = tokens;
        int endPos = tokens.length==0 ? 0 : tokens[tokens.length-1].getTextRange().getEndPosition();
        mEOFToken = new Token(Token.TYPE_EOF,"",new TextRange(endPos,endPos));
    }

    public Token peek() {
        if (mPosition<mTokens.length) {
            return mTokens[mPosition];
        }
        return mEOFToken;
    }

    public Token peek2() {
        if (mPosition+1<mTokens.length) {
            return mTokens[mPosition+1];
        }
        return mEOFToken;
    }

    /**
     * Utility fn. which returns the range of the token <b>before</b> the
     * current one.
     * Error if called when the stream is on the first token.
     */
    public TextRange getPreviousRange() {
        if (mTokens.length==0) return new TextRange(0,0);
        if (mPosition>mTokens.length) return mTokens[mTokens.length-1].getTextRange();
        return mTokens[mPosition-1].getTextRange();
    }

    public Token pop() {
        Token t = peek();
        mPosition++;
        return t;
    }
}
