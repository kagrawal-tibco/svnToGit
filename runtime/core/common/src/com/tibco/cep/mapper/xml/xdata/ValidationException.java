// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;


/**
 * This exception class is used when a metadata constraint
 * is violated in a data object
 */
public class ValidationException extends RuntimeException {
    private String mReason;
    private String mPath;

    public ValidationException() {
        super();
        mReason = "";
    }

    public ValidationException(String reason) {
        super();
        mReason = reason;
    }

    public ValidationException(String reason, String path) {
        super();
        mReason = reason;
        mPath = path;
    }

    /**
     * Adds to the leading part of the path.
     * @param path
     */
    public void addToPath(String path) {
        if (mPath==null || mPath.length()==0) {
            mPath = path;
        } else {
            mPath = path + "/" + mPath;
        }
    }

    public String getPath() {
        return mPath;
    }

    public String getMessage()
    {
        if (mPath==null || mPath.length()==0) {
            return mReason;
        }
        return mPath + ": " + mReason;
    }
}