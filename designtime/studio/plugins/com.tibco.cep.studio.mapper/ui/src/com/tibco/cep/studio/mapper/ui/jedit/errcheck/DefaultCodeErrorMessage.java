package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 * Provides a list of error messages for the editor.
 */
public class DefaultCodeErrorMessage implements CodeErrorMessage {
    private String mMessage;
    private int mSeverity;
    private CompletionProposal[] mAutoFixProposals;
    private TextRange mTextRange;
    private static final CompletionProposal[] EMPTY_PROPOSALS = new CompletionProposal[0];

    public DefaultCodeErrorMessage(String msg, TextRange textRange, int severity) {
        this(msg,textRange,severity,EMPTY_PROPOSALS);
    }

    public DefaultCodeErrorMessage(String msg, TextRange textRange, int severity, CompletionProposal[] completionProposals) {
        mMessage = msg;
        mSeverity = severity;
        mTextRange = textRange;
        mAutoFixProposals = completionProposals;
    }

    public TextRange getTextRange() {
        return mTextRange;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getSeverity() {
        return mSeverity;
    }

    public CompletionProposal[] getAutoFixProposals() {
        return mAutoFixProposals;
    }
}
