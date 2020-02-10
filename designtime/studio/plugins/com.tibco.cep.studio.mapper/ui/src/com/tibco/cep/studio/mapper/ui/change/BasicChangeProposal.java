package com.tibco.cep.studio.mapper.ui.change;




/**
 * A utility partial implementation of the ChangeProposal interface.
 */
public abstract class BasicChangeProposal implements ChangeProposal {
    protected String mSource;
    protected String mLocationInFile = "";
    protected String mDescription = "";

    /**
     * For version 5, change this to return a VFile.
     * @return The full repo path of the top level AEResource (i.e. file) that is affected.  For repo v5, this will be a VFile.
     */
    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    /**
     * Gets a unique path identifier, for display and organization, within the file
     * (Maybe use a separator or path object)
     * @return The location of the change in the file.  The empty string means none, null is not allowed.
     */
    public String getLocationInFile() {
        return mLocationInFile;
    }

    /**
     * Returns a textual description of the change required.
     * @return A change description.  An empty string means no description provided, null is not allowed.
     */
    public String getDescription() {
        return mDescription;
    }
}
