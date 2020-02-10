package com.tibco.cep.designtime.model.mutable;

import java.util.Map;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.ModelException;


/**
 * Entity is the base type for all classes, objects, rules, and properties.
 * An Entity provides a way to be referred to uniquely in the system
 * (via its GUID), and getters and setters for a name.
 *
 * @author ishaan
 * @version Mar 16, 2004 6:38:46 PM
 */

public interface MutableEntity extends Entity {


    /**
     * Sets the Ontology to which this Entity belongs.  All sub-Entities will
     * also have their setOntology() methods called.  This method will not
     * remove the Entity from its old Ontology.
     *
     * @param ontology The Ontology to which this Entity will be assigned.
     */
    public void setOntology(MutableOntology ontology);


    /**
     * Sets the namespace for this Entity.  If null or an empty String are passed in, the default namespace (folder path)
     * is assumed.
     *
     * @param namespace The new namespace for the Entity.
     */
    public void setNamespace(String namespace);


    /**
     * Sets the description for this Entity.
     *
     * @param description The Description for this Entity.
     */
    public void setDescription(String description);


    /**
     * Sets the name of this entity.
     *
     * @param name The new name for the entity.
     * @throws ModelException Thrown if the new name is invalid.
     */
    public void setName(String name, boolean renameOnConflict) throws ModelException;


    /**
     * Adds a change listener to this entity.
     *
     * @param listener The listener to add.
     */
    public void addEntityChangeListener(EntityChangeListener listener);


    /**
     * Removes a change listener from this entity.
     *
     * @param listener
     */
    public void removeEntityChangeListener(EntityChangeListener listener);


    /**
     * Notifies all listeners that this entity has changed.
     */
    public void notifyListeners();


    /**
     * Sets the Folder path for this Entity.
     *
     * @param path The new path for this Entity.
     * @throws ModelException thrown if an Entity of the same name already exists at the location.
     */
    public void setFolderPath(String path) throws ModelException;


    /**
     * Sets the Folder for this Entity.
     *
     * @param folder The Folder object for this Entity.
     * @throws ModelException thrown if an Entity of the same name already exists at the location.
     */
    public void setFolder(MutableFolder folder) throws ModelException;


    /**
     * Sets the icon URL for this Concept.
     *
     * @param path A String specifying the URL for the Concept, or null to use the default icon.
     */
    public void setIconPath(String path);


    /**
     * Creates a key-value pair for this Entity.  These mappings are NOT persisted.
     *
     * @param key   The key for the mapping.
     * @param value The value for the mapping.
     */
    public void setTransientProperty(String key, Object value);


    /**
     * Sets a name-value pair that is persistent.
     */
    public void setHiddenProperty(String key, String value);


    /**
     * Sets extended properties.
     */
    public void setExtendedProperties(Map props);


    /**
     * Deletes this Entity from its Ontology.
     */
    public void delete();


    /**
     * Sets the String representation of the bindings used to create mappings on this Entity.
     *
     * @param bindings the String representation of the bindings used to create mappings on this Entity.
     */
    public void setBindingString(String bindings);


    public void setLastModified(String xsDateTime);
}
