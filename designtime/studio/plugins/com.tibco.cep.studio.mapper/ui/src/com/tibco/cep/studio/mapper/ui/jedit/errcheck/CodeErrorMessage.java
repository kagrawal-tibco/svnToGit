package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 * An error message and (possibly) ways to fix it.
 */
public interface CodeErrorMessage {
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
     * Indicates a 'tbd' marker was found, so while there isn't really an error,
     * the expression isn't done.
     */
    public static final int TYPE_MARKER = 5;

    String getMessage();
    int getSeverity();
    TextRange getTextRange();

    CompletionProposal[] getAutoFixProposals();
}
