package com.tibco.cep.designtime.model.mutable;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;


/**
 * This interface defines the abstraction of a path for all Entity objects that are not contained in a View.
 *
 * @author ishaan
 * @version Apr 14, 2004 3:11:32 PM
 */
public interface MutableFolder extends Folder, MutableEntity {


    /**
     * Sets the parent of this Folder.
     *
     * @param parent The new parent Folder.
     * @throws ModelException
     */
    public void setParent(MutableFolder parent) throws ModelException;


    /**
     * Creates a new sub MutableFolder for this Folder with the specified short name.
     *
     * @param shortName          the first short name for the new sub Folder.
     * @param autoNameOnConflict Whether or not to suggest a name, if necessary.
     * @throws ModelException Thrown when the specified short name is invalid, empty, null, or there arleady exists a sub Folder starting with the short name.
     */
    public MutableFolder createSubFolder(String shortName, boolean autoNameOnConflict) throws ModelException;


    /**
     * Deletes all sub Folders starting with the specified short name.
     *
     * @param shortName Deletes all Folders starting with the specified short name.
     */
    public void deleteSubFolder(String shortName);


}
