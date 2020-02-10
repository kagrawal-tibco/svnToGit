package com.tibco.cep.mapper.xml.xdata.xpath;


/**
 * This class is in charge of lexical processing of the XPath
 * expression into tokens.
 */
public final class CharStream
{
    private int mPosition;
    private final char[] mBuffer;
    private int m_offset;

    public CharStream(String str) {
        mBuffer = str.toCharArray();
    }

    /**
     * @param offset The starting offset for reporting columns; affects {@link #getPosition} and {@link #getEndPosition}
     */
    public CharStream(String str, int offset)
    {
        mBuffer = str.toCharArray();
        m_offset = offset;
    }

    public boolean isEOF() {
        return mPosition >= mBuffer.length;
    }

    /**
     * Looks at the character <i>after</i> the current character without moving
     * the stream position.
     */
    public char peek() {
        if (mPosition<mBuffer.length)
        {
            return mBuffer[mPosition];
        }
        return 0;
    }

    /**
     * Looks at the character <i>after</i> the current character without moving
     * the stream position.
     */
    public char peek2() {
        if (mPosition+1<mBuffer.length) {
            return mBuffer[mPosition+1];
        }
        return 0;
    }

    /**
     * Looks at the character <i>after</i> <i>after</i> the current character without moving
     * the stream position.
     */
    public char peek3() {
        if (mPosition+2<mBuffer.length) {
            return mBuffer[mPosition+2];
        }
        return 0;
    }

    /**
     * Advances the stream one character.
     */
    public void pop() {
        mPosition++;
    }

    /**
     * Retrieves the current position of the stream, in total characters.
     * The first character is at position 0.
     */
    public int getPosition() {
        return mPosition+m_offset;
    }

    /**
     * Returns the position of the EOF, which is
     * one greater than the last real character in the stream.
     * This position never changes.
     */
    public int getEndPosition() {
        return mBuffer.length+m_offset;
    }
}
