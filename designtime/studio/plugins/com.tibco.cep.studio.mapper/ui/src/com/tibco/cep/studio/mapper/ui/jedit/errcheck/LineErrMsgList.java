package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Internal class which provides error line lookups.
 */
class LineErrMsgList {
    private ArrayList<ArrayList<CodeErrorMessage>> mErrorsByLine = new ArrayList<ArrayList<CodeErrorMessage>>(); // of (ArrayList or null->ErrorMessage)
    private CodeErrorMessage[] mAllMessages;
    private static CodeErrorMessage[] EMPTY_ERROR_LIST = new CodeErrorMessage[0];
    private JExtendedEditTextArea mTextArea;

    public LineErrMsgList(JExtendedEditTextArea textArea, ErrCheckErrorList list) {
        CodeErrorMessage[] m = list.getMessages();
        for (int i=0;i<m.length;i++) {
            addMsg(textArea,m[i]);
        }
        mTextArea = textArea;
        mAllMessages = m;
    }

    public int getMessageCount() {
        return mAllMessages.length;
    }

    public CodeErrorMessage getMessage(int index) {
        return mAllMessages[index];
    }

    private void addMsg(JExtendedEditTextArea textArea, CodeErrorMessage msg) {
        Point start = textArea.getRowAndColumnForOffset(msg.getTextRange().getStartPosition());
        Point end = textArea.getRowAndColumnForOffset(msg.getTextRange().getEndPosition());
        if (start==null || end==null) {
            return;
        }
        for (int yLine=start.y;yLine<=end.y;yLine++) {
            addMsgToLine(yLine,msg);
        }
    }

    private void addMsgToLine(int yLine, CodeErrorMessage msg) {
        if (mErrorsByLine.size()<=yLine) {
            mErrorsByLine.ensureCapacity(yLine+50);
            for (int i=mErrorsByLine.size();i<=yLine;i++) {
                // stretch the array list (isn't there a way to do this like setSize()???)
                mErrorsByLine.add(null);
            }
        }
        ArrayList<CodeErrorMessage> al = mErrorsByLine.get(yLine);
        if (al==null) {
            al = new ArrayList<CodeErrorMessage>();
            mErrorsByLine.set(yLine,al);
        }
        al.add(msg);
    }

    /**
     * Prunes the error message list based on line and column.
     * @param line The line
     * @param col The column
     * @return True if ANY messages were removed, false otherwise.
     */
    public boolean removeAllMessagesAfter(int line, int col) {
        boolean anyFound = false;
        for (int i=line;i<mErrorsByLine.size();i++) {
            ArrayList<CodeErrorMessage> al = mErrorsByLine.get(i);
            if (al!=null) {
                anyFound = true;
                if (i==line) {
                    // same line, check column:
                    ArrayList<CodeErrorMessage> ret = new ArrayList<CodeErrorMessage>();
                    for (int j=0;j<al.size();j++) {
                        try {
                            CodeErrorMessage em = (CodeErrorMessage) al.get(j);
                            int et = em.getTextRange().getEndPosition();
                            int lo = mTextArea.getLineStartOffset(line);
                            int ncol = et-lo;
                            if (col>ncol) {
                                // the error is before where the column specified, so keep it.
                                ret.add(em);
                            }
                        } catch (Exception e) {
                            // ignore anything... just let the errors go away.
                        }
                    }
                    if (ret.size()==0) {
                        mErrorsByLine.set(i,null);
                    } else {
                        mErrorsByLine.set(i,ret);
                    }
                } else {
                    mErrorsByLine.set(i,null);
                }
            }
        }
        return anyFound;
    }

    /**
     *
     * @param lineNumber The line (zero based) to check for errors.
     * @return An array of all the error messages applying to that line.  A zero length array is returned if none.
     */
    public CodeErrorMessage[] getMessagesOnLine(int lineNumber) {
        if (mErrorsByLine.size()<=lineNumber) {
            return EMPTY_ERROR_LIST;
        }
        ArrayList<CodeErrorMessage> al = mErrorsByLine.get(lineNumber);
        if (al==null) {
            return EMPTY_ERROR_LIST;
        }
        return (CodeErrorMessage[]) al.toArray(new CodeErrorMessage[al.size()]);
    }
}
