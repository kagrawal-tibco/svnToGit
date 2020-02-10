package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.schema.SmSequenceType;

/**
 * The 'report' for the code completion logic.
 */
public final class CodeCompleteData {
    private final CodeCompleteResult[] mPossibleStrings;
    private final SmSequenceType mContextType;
    private final SmSequenceType mExpectedReturnType;
    private final TextRange mContextRange;

    /**
     * @param contextType At this text position, the context's type (xpath).
     */
    public CodeCompleteData(SmSequenceType contextType, SmSequenceType expectedReturnType, CodeCompleteResult[] strings, TextRange contextRange) {
        mContextType = contextType;
        mExpectedReturnType = expectedReturnType;
        mPossibleStrings = strings;
        mContextRange = contextRange;
    }

    public CodeCompleteResult[] getPossibleStrings() {
        return mPossibleStrings;
    }

    public SmSequenceType getContextType() {
        return mContextType;
    }

    /**
     * Returns the type that is expected to be returned by this point in the text.<br>
     * For example,
     * <code>concat(<i>XXX</i>)</code> , where <i>XXX</i> denotes the marker would have an expected return type of the
     * string primitive.<br>
     * <code>$something + <i>XXX</i></code>, would have an expected return type of the number primitive.<BR>
     * <b>May be null... not fully used everywhere</b>
     */
    public SmSequenceType getExpectedReturnType() {
        return mExpectedReturnType;
    }

    public TextRange getContextRange() {
        return mContextRange;
    }
}

