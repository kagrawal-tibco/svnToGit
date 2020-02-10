package com.tibco.cep.studio.mapper.ui.change;

import javax.swing.undo.UndoableEdit;

/**
 * The notification interface for Object (file) types interested in changes to schemas.<br>
 */
public interface ChangeProposal {
    /**
     * For version 5, change this to return a VFile.
     * @return The full repo path of the top level AEResource (i.e. file) that is affected.  For repo v5, this will be a VFile.
     */
    String getSource();

    /**
     * Gets a unique path identifier, for display and organization, within the file
     * (Maybe use a separator or path object)
     * @return The location of the change in the file.  The empty string means none, null is not allowed.
     */
    String getLocationInFile();

    /**
     * Returns a textual description of the change required.
     * @return A change description.  An empty string means no description provided, null is not allowed.
     */
    String getDescription();

    /**
     * Applies the change, returning the undo for it.
     */
    UndoableEdit apply();
}
