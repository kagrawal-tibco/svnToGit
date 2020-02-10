package com.tibco.be.rms.repo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.Validatable;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 15, 2010
 * Time: 4:08:28 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class ManagedFolder implements Folder {

    private Ontology managedOntology;

    /**
     * The canonical folder path
     */
    private String folderPath;

    public ManagedFolder(Ontology managedOntology, String folderPath) {
        this.managedOntology = managedOntology;

        this.folderPath = folderPath;
    }

    public boolean hasChild(Folder folder) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasDescendant(Folder folder) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasPredecessor(Folder folder) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasSibling(Folder folder) {
        // TODO Auto-generated method stub
        return false;
    }

    public String getAlias() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getBindingString() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<?, ?> getExtendedProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getGUID() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<?, ?> getHiddenProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getHiddenProperty(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    public Icon getIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getIconPath() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLastModified() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    public Map<?, ?> getTransientProperties() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getTransientProperty(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    public void serialize(OutputStream outputstream) throws IOException {
        // TODO Auto-generated method stub

    }

    public Validatable[] getInvalidObjects() {
        // TODO Auto-generated method stub
        return null;
    }

    public List<?> getModelErrors() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getStatusMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isValid(boolean flag) {
        // TODO Auto-generated method stub
        return false;
    }

    public void makeValid(boolean flag) {
        // TODO Auto-generated method stub

    }

    /**
     * Returns an array of all direct sub Folders.
     *
     * @return An array of all direct sub Folders.
     */
    public Collection<?> getSubFolders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns all Entities that belong directly to this Folder.
     *
     * @param includeSubFolders Whether or not to include sub-Folders.s
     * @return A Collection containing all Instances that belong directly to this Folder.
     */
    public List<?> getEntities(boolean includeSubFolders) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns an Entity with the specified name, or null, if this Folder does not contain such an Entity.
     *
     * @param name              The name of the Entity.
     * @param includeSubFolders Whether or not to include sub-Folders.  If true, and a Folder and a non-MutableFolder of the same name exist, the non-MutableFolder is returned.
     * @return The Entity with the specified name, or null, if this Folder does not contain such an Instance.
     */
    public Entity getEntity(String name, boolean includeSubFolders) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the path from the root Folder to this down inclusive, and in order.
     *
     * @return A collection of Folders, whose first element is the root Folder, and whose last element is this Folder.
     */
    public Collection<?> getFolderList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the parent of this Folder.
     *
     * @return The parent Folder, or null.
     */
    public Folder getParent() {
        return null;
    }

    /**
     * Returns the direct sub Folder starting with the specified short name.
     *
     * @param shortName The starting short name for the requested sub Folder.
     * @return The Folder which has this Folder as a parent, and starts with the specified short name, or null if no such Folder exists.
     */
    public Folder getSubFolder(String shortName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the Folder in which this Entity resides.
     *
     * @return The Folder in which this Entity resides.
     */
    public Folder getFolder() {
        return new ManagedFolder(managedOntology, folderPath);
    }

    /**
     * Returns the path of the Folder for this Entity.
     *
     * @return The path of the Folder for this Entity.
     */
    public String getFolderPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the full path plus the name of this Entity.
     *
     * @return The full path plus the name of this Entity.
     */
    public String getFullPath() {
        return folderPath;
    }

    /**
     * Returns the name of this Entity.
     *
     * @return This is a String representation of the name.
     */
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns the Ontology to which this Entity belongs.
     *
     * @return The Ontology to which this Entity belongs.
     */
    public Ontology getOntology() {
        return managedOntology;
    }
}
