package com.tibco.cep.mapper.xml.xdata.xpath;

/**
 * Encapsulates a range of text
 */
public final class TextRange {
    private int mStartPosition;
    private int mEndPosition;

    public TextRange(int startPos, int endPos) {
        mStartPosition = startPos;
        mEndPosition = endPos;
        if (startPos>endPos) {
            throw new IllegalArgumentException("Starting position (" + startPos + ") > ending position (" + endPos + ")");
        }
    }

    public TextRange(TextRange startingRange, int endPos) {
        this(startingRange.mStartPosition,endPos);
    }

    public TextRange(TextRange startingRange, TextRange endPos) {
        this(startingRange.mStartPosition,endPos.mEndPosition);
    }

    public TextRange(int startingPos, TextRange endPos) {
        this(startingPos,endPos.mEndPosition);
    }

    public TextRange offset(int offset) {
        return new TextRange(mStartPosition+offset,mEndPosition+offset);
    }

    public int getStartPosition() {
        return mStartPosition;
    }

    public int getEndPosition() {
        return mEndPosition;
    }

    public int getLength() {
        return mEndPosition-mStartPosition;
    }

    public boolean containsPosition(int position) {
        return mStartPosition<=position && mEndPosition>=position;
    }

    public String toString() {
        if (getLength()==0) {
            return "(" + mStartPosition + ")";
        }
        return "(" + mStartPosition + "-" + mEndPosition + ")";
    }

    public int hashCode() {
        return mStartPosition + mEndPosition;
    }

    public boolean equals(Object val) {
        if (!(val instanceof TextRange)) return false;
        TextRange tr = (TextRange) val;
        return tr.mStartPosition==mStartPosition && tr.mEndPosition==mEndPosition;
    }
}
