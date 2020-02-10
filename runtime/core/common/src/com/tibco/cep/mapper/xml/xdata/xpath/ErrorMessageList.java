package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/**
 * This needs a better home, it's pretty generic.
 * A list of errors.
 */
public class ErrorMessageList
{
    private ErrorMessage[] mErrors;

    public static final ErrorMessageList EMPTY_LIST = new ErrorMessageList();

    private ErrorMessageList() {
        mErrors = new ErrorMessage[0];
    }

    public ErrorMessageList(ErrorMessage[] errs)
    {
        mErrors = (ErrorMessage[]) errs.clone();
    }

    public ErrorMessageList(ErrorMessage msg)
    {
        mErrors = new ErrorMessage[] {msg};
    }

    public ErrorMessageList addMessage(ErrorMessage message) {
        ErrorMessage[] l = new ErrorMessage[mErrors.length+1];
        for (int i=0;i<mErrors.length;i++) {
            l[i] = mErrors[i];
        }
        l[mErrors.length] = message;
        return new ErrorMessageList(l);
    }

    public ErrorMessageList addMessages(ErrorMessageList list)
    {
        if (list.mErrors.length==0)
        {
            return this;
        }
        ErrorMessage[] l = new ErrorMessage[mErrors.length+list.mErrors.length];
        for (int i=0;i<mErrors.length;i++) {
            l[i] = mErrors[i];
        }
        for (int i=0;i<list.mErrors.length;i++) {
            l[mErrors.length+i] = list.mErrors[i];
        }
        return new ErrorMessageList(l);
    }

    public ErrorMessageList getAllMessagesBefore(int col) {
        if (mErrors.length==0) return this;
        ArrayList ret = new ArrayList();
        for (int i=0;i<mErrors.length;i++) {
            ErrorMessage m = mErrors[i];
            if (m.getTextRange().getEndPosition()<col) {
                ret.add(m);
            }
        }
        if (ret.size()==0) {
            return EMPTY_LIST;
        }
        return new ErrorMessageList((ErrorMessage[]) ret.toArray(new ErrorMessage[0]));
    }

    /**
     * Returns all error messages (including warnings)
     */
    public ErrorMessage[] getMessages() {
        if (mErrors.length==0) return mErrors;
        return (ErrorMessage[]) mErrors.clone();
    }

    public ErrorMessage[] getErrorAndWarningMessages() {
        if (mErrors.length==0) return mErrors;
        ArrayList al = new ArrayList();
        for (int i=0;i<mErrors.length;i++) {
            ErrorMessage em = mErrors[i];
            if (em.getType()==ErrorMessage.TYPE_ERROR || em.getType()==ErrorMessage.TYPE_WARNING) {
                al.add(em);
            }
        }
        return (ErrorMessage[]) al.toArray(new ErrorMessage[al.size()]);
    }

    public ErrorMessage[] getErrorAndWarningAndMarkerMessages() {
        if (mErrors.length==0) return mErrors;
        ArrayList al = new ArrayList();
        for (int i=0;i<mErrors.length;i++) {
            ErrorMessage em = mErrors[i];
            if (em.getType()==ErrorMessage.TYPE_ERROR || em.getType()==ErrorMessage.TYPE_WARNING || em.getType()==ErrorMessage.TYPE_MARKER) {
                al.add(em);
            }
        }
        return (ErrorMessage[]) al.toArray(new ErrorMessage[al.size()]);
    }

    public ErrorMessage[] getErrorMessages() {
        if (mErrors.length==0) return mErrors;
        ArrayList al = new ArrayList();
        for (int i=0;i<mErrors.length;i++) {
            ErrorMessage em = mErrors[i];
            if (em.getType()==ErrorMessage.TYPE_ERROR) {
                al.add(em);
            }
        }
        return (ErrorMessage[]) al.toArray(new ErrorMessage[al.size()]);
    }

    public ErrorMessage[] getMarkerMessages() {
        if (mErrors.length==0) return mErrors;
        ArrayList al = new ArrayList();
        for (int i=0;i<mErrors.length;i++) {
            ErrorMessage em = mErrors[i];
            if (em.getType()==ErrorMessage.TYPE_MARKER) {
                al.add(em);
            }
        }
        return (ErrorMessage[]) al.toArray(new ErrorMessage[al.size()]);
    }

    public ErrorMessageList sortByPriority() {
        if (mErrors.length==0 || mErrors.length==1) {
            return this;
        }
        ErrorMessage[] m = (ErrorMessage[]) mErrors.clone();
        Arrays.sort(m,new Comparator() {
            public int compare(Object o1, Object o2) {
                ErrorMessage em1 = (ErrorMessage) o1;
                ErrorMessage em2 = (ErrorMessage) o2;
                return em1.getType()<em2.getType() ? -1 : 1;
            }
        });
        return new ErrorMessageList(m);
    }

    /**
     * Returns the total count of errors, regardless of type.
     */
    public int getCount() {
        return mErrors.length;
    }

    /**
     * Returns the count of ErrorMessage.TYPE_ERROR messages.
     */
    public int getErrorCount() {
        int ct=0;
        for (int i=0;i<mErrors.length;i++) {
            if (mErrors[i].getType()==ErrorMessage.TYPE_ERROR) {
                ct++;
            }
        }
        return ct;
    }

    /**
     * Returns true if this has no errors.
     */
    public boolean hasZeroErrorCount()
    {
        for (int i=0;i<mErrors.length;i++) {
            int type = mErrors[i].getType();
            if (type==ErrorMessage.TYPE_ERROR || type == ErrorMessage.TYPE_WARNING) { // LAMb: should this just be ERROR?
                return false;
            }
        }
        return true;
    }

    public int getErrorAndWarningCount() {
        int ct=0;
        for (int i=0;i<mErrors.length;i++) {
            int type = mErrors[i].getType();
            if (type==ErrorMessage.TYPE_ERROR || type == ErrorMessage.TYPE_WARNING) {
                ct++;
            }
        }
        return ct;
    }

    public int getErrorAndWarningAndMarkerCount() {
        int ct=0;
        for (int i=0;i<mErrors.length;i++) {
            int type = mErrors[i].getType();
            if (type==ErrorMessage.TYPE_ERROR || type == ErrorMessage.TYPE_WARNING || type == ErrorMessage.TYPE_MARKER) {
                ct++;
            }
        }
        return ct;
    }

    /**
     * Creates an array of error message lists, one per line in the text.
     * The error text ranges are repositioned to start for the line they are on.
     * (Array length may be less than the total # of lines...)
     */
    public ErrorMessageList[] divideIntoLines(String text) {
        if (mErrors.length==0)
        {
            return new ErrorMessageList[0];
        }
        if (text.length()==0)
        {
            // all the errors (should be one:-) will be the first & only line:
            return new ErrorMessageList[] {this};
        }
        int lineStart = 0;
        ArrayList lines = new ArrayList();
        for (int i=0;i<text.length();i++)
        {
            char c = text.charAt(i);
            if (c=='\n' || i==text.length()-1) {
                ArrayList temp = new ArrayList();
                for (int j=0;j<mErrors.length;j++) {
                    ErrorMessage err = mErrors[j];
                    if (err.getTextRange().getStartPosition()<=i)
                    {
                        if (err.getTextRange().getEndPosition()>=lineStart)
                        {
                            TextRange r2 = err.getTextRange().offset(-lineStart);
                            ErrorMessage moved = new ErrorMessage(err.getType(),err.getMessage(),err.getSupplementalText(),r2);
                            temp.add(moved);
                        }
                    }
                }
                ErrorMessageList t = new ErrorMessageList((ErrorMessage[])temp.toArray(new ErrorMessage[0]));
                lines.add(t);

                lineStart = i+1;
            }
        }
        return (ErrorMessageList[]) lines.toArray(new ErrorMessageList[lines.size()]);
    }

    public int getMarkerCount() {
        int ct=0;
        for (int i=0;i<mErrors.length;i++) {
            int type = mErrors[i].getType();
            if (type==ErrorMessage.TYPE_MARKER) {
                ct++;
            }
        }
        return ct;
    }

    public int getCount(int errorType) {
        int ct=0;
        for (int i=0;i<mErrors.length;i++) {
            if (mErrors[i].getType()==errorType) {
                ct++;
            }
        }
        return ct;
    }

    public int size() {
        return mErrors.length;
    }

    public int hashCode() {
        return mErrors.length;
    }

    public boolean equals(Object val) {
        if (!(val instanceof ErrorMessageList)) return false;
        ErrorMessageList eml = (ErrorMessageList) val;
        ErrorMessage[] em = eml.getMessages();
        if (em.length!=mErrors.length) return false;
        for (int i=0;i<em.length;i++) {
            if (!em[i].equals(mErrors[i])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (size()==0) {
            return "<< no errors >>";
        }
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<size();i++) {
            if (i>0) sb.append(", ");
            sb.append(getMessages()[i]);
        }
        return sb.toString();
    }
}
