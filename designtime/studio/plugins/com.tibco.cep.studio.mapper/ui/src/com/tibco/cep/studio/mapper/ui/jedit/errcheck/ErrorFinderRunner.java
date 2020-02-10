package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.tibco.cep.studio.mapper.ui.jedit.syntaxcoloring.SyntaxDocument;

/**
 * Purposefully package private, used by JExtendedEditTextArea.
 * A utility class for searching errors, monitors cursor & document changes.
 */
class ErrorFinderRunner {
    private Document mDocument;
    private CodeErrorFinder mCodeErrorFinder;
    private JExtendedEditTextArea mEditTextArea;
    private Listener mListener = new Listener();
    private LineErrMsgList mCurrentErrors;
    Runner mRunner = new Runner();

    int mChangeNumber = 1;
    int mDocChangeNumber = 1;
    String mUpToCurrent;
    String mBeyond;
    private boolean mIsWaitingForRun;

    public ErrorFinderRunner(JExtendedEditTextArea area) {
        mEditTextArea = area;
        setDocument(area.getDocument());
    }

    private void findError(final String text) {
        if (mCodeErrorFinder==null) {
            return;
        }
        try {
            final ErrCheckErrorList errors = mCodeErrorFinder.getErrors(new StringReader(text));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    mIsWaitingForRun = false;
                    mCurrentErrors = new LineErrMsgList(mEditTextArea,errors);
                    mEditTextArea.errorCheckFinished(mCurrentErrors,true); // true-> full update.
                }
            });
        } catch (IOException ioe) {
            // Shouldn't happen, ever, because it is a StringReader now.
            ioe.printStackTrace(System.err);
        }
    }

    private class Runner extends javax.swing.Timer implements ActionListener {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Runner() {
            super(1000,null); // 1000-> not used.
            setRepeats(false); // repeats is on by default...
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent ae) {
            rerun(null);
        }
    }


    private void changed(int firstAffectedCharacter) {
        // What line is this?

        mChangeNumber++;
        mDocChangeNumber++;
        mIsWaitingForRun = true;

        LineErrMsgList lines = mCurrentErrors;
        if (lines!=null) {
            Point p = mEditTextArea.getRowAndColumnForOffset(firstAffectedCharacter);
            if (p!=null) {
                boolean changed = lines.removeAllMessagesAfter(p.y,p.x);
                if (changed) {
                    mEditTextArea.errorCheckFinished(mCurrentErrors,false); // false-> not a full update.
                }
            }
        }
        rescheduleRun();
    }

    private void rescheduleRun() {
        if (!mIsWaitingForRun) {
            mEditTextArea.errorRunPending();
        }
        mIsWaitingForRun = true;
        mRunner.stop();
        mRunner.setDelay(500);
        mRunner.setRepeats(false);
        mRunner.start();
    }

    public void runNow() {
        runNow(null);
    }

    public void runNow(Runnable runWhenDone) {
        mRunner.stop();
        rerun(runWhenDone);
    }

    public boolean isWaitingForRun() {
        return mIsWaitingForRun;
    }

    private void rerun(final Runnable runWhenDone) {
        // This could be kinda bad... we're getting whole text in Swing thread,
        // probably not an issue unless document is HUGE. (can optimize later...)
        try {
            // maybe someday optimize so it gets a Reader instead of a String (somehow)
            final String text = new String(mDocument.getText(0,mDocument.getLength()));
            //final int cursor = mEditor.getCaretPosition();
            Thread t = new Thread(new Runnable() {
                public void run() {
                    findError(text);
                    if (runWhenDone!=null) {
                        runWhenDone.run();
                    }
                };
            });
            // error checking is MINIMUM priority! -- don't slow down typing, etc.
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        } catch (BadLocationException ble) {
            // just retry.
            //ble.printStackTrace(System.out);
        }
    }

    public void setDocument(SyntaxDocument document) {
        if (mDocument!=null) {
            mDocument.removeDocumentListener(mListener);
        }
        mDocument = document;
        mDocument.addDocumentListener(mListener);
    }

    public CodeErrorFinder getCodeErrorFinder() {
        return mCodeErrorFinder;
    }

    public void setCodeErrorFinder(CodeErrorFinder coe) {
        mCodeErrorFinder = coe;
    }

    private class Listener implements DocumentListener {
        public void removeUpdate(DocumentEvent de) {
            changed(de.getOffset());
        }

        public void insertUpdate(DocumentEvent de) {
            changed(de.getOffset());
        }

        public void changedUpdate(DocumentEvent de) {
            changed(de.getOffset());
        }

        /* What was this for??? public void caretUpdate(CaretEvent ce) {
            mChangeNumber++;
            rescheduleRun();
        }*/
    }
}
