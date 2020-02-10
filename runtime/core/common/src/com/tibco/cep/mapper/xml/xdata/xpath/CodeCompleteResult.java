package com.tibco.cep.mapper.xml.xdata.xpath;


/**
 * The 'report' for the code completion logic.
 */
public final class CodeCompleteResult {
    private final String mDisplayString;
    private final String mDropString;
    private final String mOptionalNamespace;

    public CodeCompleteResult(String displayStr, String dropStr) {
        mDisplayString = displayStr;
        mDropString = dropStr;
        mOptionalNamespace = "";
    }

    /**
     * @param displayStr The string for display purposes.
     * @param optionalNamespace A namespace, or null, or "" for none.
     */
    public CodeCompleteResult(String displayStr, String dropStr, String optionalNamespace) {
        mDisplayString = displayStr;
        mDropString = dropStr;
        mOptionalNamespace = optionalNamespace==null ? "" : optionalNamespace;
    }

    public String getDisplayString() {
        return mDisplayString;
    }

    public String getDropString() {
        return mDropString;
    }

    /**
     * Gets the namespace for the above result, if any, returns empty string if none.
     */
    public String getOptionalNamespace() {
        return mOptionalNamespace;
    }
}

