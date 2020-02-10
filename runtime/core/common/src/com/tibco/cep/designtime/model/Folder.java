package com.tibco.cep.designtime.model;


import java.util.Collection;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 5:09:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Folder extends Entity {


    char FOLDER_SEPARATOR_CHAR = '/';


    /**
     * Returns the parent of this Folder.
     *
     * @return The parent Folder, or null.
     */
    Folder getParent();


    /**
     * Returns an array of all direct sub Folders.
     *
     * @return An array of all direct sub Folders.
     */
    Collection getSubFolders();


    /**
     * Returns the direct sub Folder starting with the specified short name.
     *
     * @param shortName The starting short name for the requested sub Folder.
     * @return The Folder which has this Folder as a parent, and starts with the specified short name, or null if no such Folder exists.
     */
    Folder getSubFolder(String shortName);


    /**
     * Returns true if the specified Folder is an ancestor of this Folder.
     *
     * @param f The Folder to check.
     * @return true if f is an ancestor of this Folder, false otherwise.
     */
    boolean hasPredecessor(Folder f);


    /**
     * Returns true if the specified Folder is a sibling of this Folder.
     *
     * @param f The Folder to check.
     * @return true if both f and this Folder have the same Parent.
     */
    boolean hasSibling(Folder f);


    /**
     * Returns true if this Folder has the supplied folder as a child.
     *
     * @param f The Folder to check.
     * @return true, if the f descends from this Folder; false otherwise.
     */
    boolean hasDescendant(Folder f);


    /**
     * Returns true if this Folder is the parent of f.
     *
     * @param f The Folder to check.
     * @return true if f is a child of this Folder, false otherwise.
     */
    boolean hasChild(Folder f);


    /**
     * Returns the path from the root Folder to this down inclusive, and in order.
     *
     * @return A collection of Folders, whose first element is the root Folder, and whose last element is this Folder.
     */
    Collection getFolderList();


    /**
     * Returns all Entities that belong directly to this Folder.
     *
     * @param includeSubFolders Whether or not to include sub-Folders.s
     * @return A Collection containing all Instances that belong directly to this Folder.
     */
    List getEntities(boolean includeSubFolders);


    /**
     * Returns an Entity with the specified name, or null, if this Folder does not contain such an Entity.
     *
     * @param name              The name of the Entity.
     * @param includeSubFolders Whether or not to include sub-Folders.  If true, and a Folder and a non-MutableFolder of the same name exist, the non-MutableFolder is returned.
     * @return The Entity with the specified name, or null, if this Folder does not contain such an Instance.
     */
    Entity getEntity(String name, boolean includeSubFolders);
}
