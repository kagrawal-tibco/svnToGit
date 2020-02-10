package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.io.IOException;
import java.io.Reader;

/**
 * Implement this so that {@link com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea} can do error checking on the document.<br>
 * Note that these calls <b>will</b> be made in a different thread than the Swing thread, so take appropriate precautions.
 * @see com.tibco.cep.studio.mapper.ui.jedit.errcheck.JExtendedEditTextArea
 */
public interface CodeErrorFinder {
    /**
     * @param documentReader The reader for the document.
     * @return A list of errors, broken down by line number.
     * @throws java.io.IOException Because the reader can throw that exception (in practice it won't)
     */
    ErrCheckErrorList getErrors(Reader documentReader) throws IOException;

    /**
     *
     * @param documentReader The reader for the document.
     * @param offset The offset where the user requested completion proposals.
     * @return The set of completion proposals.
     * @throws IOException Because the reader can throw that exception
     */
    CompletionProposal[] computeCompletionProposals(Reader documentReader, int offset) throws IOException;
}
