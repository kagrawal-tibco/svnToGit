package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.util.ArrayList;

/**
 * Provides a list of error messages for the editor.
 */
public class ErrCheckErrorList {
    private ArrayList<CodeErrorMessage> mErrors = new ArrayList<CodeErrorMessage>();

    public ErrCheckErrorList() {
    }

    public void addMessage(CodeErrorMessage message) {
        mErrors.add(message);
    }

    public void addMessages(ErrCheckErrorList list) {
        for (int i=0;i<list.mErrors.size();i++) {
            addMessage(list.mErrors.get(i));
        }
    }

    /*
    public ErrCheckErrorList getAllMessagesBefore(int col) {
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
        return new ErrCheckErrorList((ErrorMessage[]) ret.toArray(new ErrorMessage[0]));
    }*/

    /**
     * Returns all error messages (including warnings)
     */
    public CodeErrorMessage[] getMessages() {
        return mErrors.toArray(new CodeErrorMessage[mErrors.size()]);
    }

    /*
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
    }*/

    /*
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
    }*/

    /**
     * Returns the total count of errors, regardless of type.
     */
    public int getCount() {
        return mErrors.size();
    }
}
