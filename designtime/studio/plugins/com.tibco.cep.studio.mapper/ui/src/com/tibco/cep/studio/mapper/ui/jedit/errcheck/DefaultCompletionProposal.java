package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Image;
import java.awt.Point;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 * Default implementation of CompletionProposal.
 */
public class DefaultCompletionProposal implements CompletionProposal {
    protected final String mDisplayString;
    protected TextRange mTextRange;
    protected String mReplacementText;

    public DefaultCompletionProposal(String displayString, TextRange affectedRange, String replacementText) {
        mDisplayString = displayString;
        mTextRange = affectedRange;
        mReplacementText = replacementText;
    }

    public void apply(Document document) {
        performReplacement(document,mTextRange,mReplacementText);
    }

    /**
     * Performs a replacement in the document; removes the specified textRange and inserts the replacementText there.
     * @param document The doucment
     * @param textRange The textRange to use.
     * @param replacementText The replacement text to replace in that range.
     */
    protected void performReplacement(Document document, TextRange textRange, String replacementText) {
        try {
            document.remove(textRange.getStartPosition(),textRange.getLength());
            document.insertString(textRange.getStartPosition(),replacementText,document.getDefaultRootElement().getAttributes());
        } catch (BadLocationException ble) {
            // ignore this.
        }
    }

    /**
     * Returns optional additional information about the proposal
     */
    public String getAdditionalProposalInfo() {
        return "";
    }

    /**
     * The display string to be displayed in the list of completion proposals.
     * @return A normal string.
     */
    public String getDisplayString() {
        return mDisplayString;
    }

    /**
     * Returns the image to be displayed in the list of completion proposals.
     * @return An image.
     */
    public Image getImage() {
        return null;
    }

    /**
     * Returns the new selection after the proposal has been applied to the given document in absolute document coordinates.
     * @param document The document this proposal applied to.
     * @return The selection (row,column) point.
     */
    public Point getSelection(Document document) {
        return null;
    }
}
