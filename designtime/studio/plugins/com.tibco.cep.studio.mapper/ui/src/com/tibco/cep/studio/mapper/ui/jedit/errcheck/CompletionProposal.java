package com.tibco.cep.studio.mapper.ui.jedit.errcheck;

import java.awt.Image;
import java.awt.Point;

import javax.swing.text.Document;

/**
 *
 * Modelled after Eclipse's ICompletionProposal.
 */
public interface CompletionProposal {

    /**
     * Applies the proposed completion to the document.
     * @param document The document to update with the change.
     */
    void apply(Document document);

    /**
     * Returns optional additional information about the proposal
     */
    String getAdditionalProposalInfo();

    /**
     * The display string to be displayed in the list of completion proposals.
     * @return A normal string.
     */
    String getDisplayString();

    /**
     * Returns the image to be displayed in the list of completion proposals.
     * @return An image.
     */
    Image getImage();

    /**
     * Returns the new selection after the proposal has been applied to the given document in absolute document coordinates.
     * @param document The document this proposal applied to.
     * @return The selection (row,column) point.
     */
    Point getSelection(Document document);
}
