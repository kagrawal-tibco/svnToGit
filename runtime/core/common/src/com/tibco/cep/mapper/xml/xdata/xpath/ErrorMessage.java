package com.tibco.cep.mapper.xml.xdata.xpath;


/**
 * This needs a better home, it's pretty generic.
 */
public final class ErrorMessage
{
    private final String mErrorText;
    private final String mSupplementalText;
    private final String mSupplementalText2;
    private final TextRange mTextRange;
    private XPathFilter filter;
    private final int mType;
    private final int mCode;

    /**
     * Indicates that (almost certainly) there is a problem and it won't work.
     */
    public static final int TYPE_ERROR = 1; // default;

    /**
     * Indicates that this is probably incorrect, though may produce some behavior.
     */
    public static final int TYPE_WARNING = 2;

    /**
     * Indicates the checker couldn't understand the expression (because it is too difficult,
     * not implemented, or whatever), so you're on your own.
     */
    public static final int TYPE_UNCHECKED = 3;

    /**
     * Indicates that this is a 'normal' conversion and which conversion is being done.
     * Supplemental text should be the name of the function applied.
     */
    public static final int TYPE_AUTOCAST = 4;

    /**
     * Indicates a 'tbd' marker was found, so while there isn't really an error,
     * the expression isn't done.
     */
    public static final int TYPE_MARKER = 5;

    /**
     * The undefined error code.
     */
    public static final int CODE_UNKNOWN = 0;

    /**
     * The error code for when a match would occur if only there was a prefix...
     * In this case, the supplemental text contains the namespace (not the prefix).
     */
    public static final int CODE_MISSING_PREFIX = 1;

    public ErrorMessage(String errorText, TextRange textRange) {
        this(TYPE_ERROR,errorText,textRange);
    }

    public ErrorMessage(int typeCode, String errorText, TextRange textRange) {
        mErrorText = errorText;
        mSupplementalText = null;
        mSupplementalText2 = null;
        mTextRange = textRange;
        mType = typeCode;
        if (mErrorText==null) {
            throw new NullPointerException("Null text");
        }
        mCode = 0;
    }

    public ErrorMessage(int typeCode, String errorText, String supplementalText, TextRange textRange) {
        mErrorText = errorText;
        mSupplementalText = supplementalText;
        mSupplementalText2 = null;
        mTextRange = textRange;
        mType = typeCode;
        if (mErrorText==null) {
            throw new NullPointerException("Null text");
        }
        mCode = 0;
    }

    public ErrorMessage(int typeCode, String errorText, int code, String supplementalText, TextRange textRange) {
        mErrorText = errorText;
        mSupplementalText = supplementalText;
        mSupplementalText2 = null;
        mTextRange = textRange;
        mType = typeCode;
        if (mErrorText==null) {
            throw new NullPointerException("Null text");
        }
        mCode = code;
    }

    public ErrorMessage(int typeCode, String errorText, int code, String supplementalText, String supplementalText2, TextRange textRange) {
        mErrorText = errorText;
        mSupplementalText = supplementalText;
        mSupplementalText2 = supplementalText2;
        mTextRange = textRange;
        mType = typeCode;
        if (mErrorText==null) {
            throw new NullPointerException("Null text");
        }
        mCode = code;
    }

    public String getMessage() {
        return mErrorText;
    }

    public int getCode() {
        return mCode;
    }

    public void setFilter(XPathFilter filter)
    {
        this.filter = filter;
    }

    /**
     * Gets the filter that fixes this error/warning.<br>
     * The filter should be applied to the <b>entire</b> xpath and should not be applied recursively (i.e.
     * you don't need to use {@link XPathFilterSupport#applyFilterRecursively}.
     * @return The filter, or null for none.
     */
    public XPathFilter getFilter()
    {
        return filter;
    }

    /**
     * Supplemental text, may be null, meaning depends on type of error
     */
    public String getSupplementalText() {
        return mSupplementalText;
    }

    /**
     * Supplemental text2, may be null, meaning depends on type of error
     */
    public String getSupplementalText2() {
        return mSupplementalText2;
    }

    public TextRange getTextRange() {
        return mTextRange;
    }

    /**
     * Either {@link #TYPE_ERROR} or {@link #TYPE_WARNING}, etc.
     */
    public int getType() {
        return mType;
    }

    public String toString() {
        if (mTextRange==null) {
            return getCodeString(mType) + ": " + mErrorText;
        }
        return getCodeString(mType) + ": " + mTextRange.toString() + ": " + mErrorText;
    }

    public static String getCodeString(int code) {
        switch (code) {
            case TYPE_ERROR: return "error";
            case TYPE_WARNING: return "warning";
            case TYPE_UNCHECKED: return "unchecked";
            case TYPE_AUTOCAST: return "autocast";
            case TYPE_MARKER: return "marker";
        }
        return "Unknown error code:" + code;
    }

    public int hashCode() {
        return mErrorText.hashCode();
    }

    public boolean equals(Object val) {
        if (!(val instanceof ErrorMessage)) {
            return false;
        }
        ErrorMessage em = (ErrorMessage) val;
        return em.mType==mType && em.mTextRange.equals(mTextRange) && em.mErrorText.equals(mErrorText);
    }
}
