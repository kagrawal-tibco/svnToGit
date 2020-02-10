package com.tibco.cep.designtime.model;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.swing.Icon;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 4:23:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Entity {

    public final static String EXTPROP_ENTITY_BACKINGSTORE= "Backing Store";
    public final static String EXTPROP_ENTITY_BACKINGSTORE_TYPENAME= "Type Name";
    public final static String EXTPROP_ENTITY_BACKINGSTORE_TABLENAME= "Table Name";
    public final static String EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE="hasBackingStore";

    public final static String EXTPROP_ENTITY_CACHE= "Cache";
    public final static String EXTPROP_ENTITY_CACHE_PRELOAD_ALL= "Pre Load On Recovery [true | false]";
    public final static String EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE= "Maximum Records to Load On Recovery [Set 0 for All]";
    public final static String EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK= "Check for Version [true | false]";
    public final static String EXTPROP_ENTITY_CACHE_ISCACHELIMITED= "Is Cache Limited[true | false]";
    public final static String EXTPROP_ENTITY_CACHE_EVICTONUPDATE=  "Evict From Cache on Update [true | false]";
    public final static String EXTPROP_ENTITY_CACHE_CONSTANT= "Constant [true | false]";    

    public final static String EXTPROP_PROPERTY_BACKINGSTORE= "Backing Store";
    public final static String EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME= "Column Name";
    public final static String EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH= "Max Length";
    public final static String EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME= "Nested Table Name";

    Icon getIcon();


    /**
     * Returns the Ontology to which this Entity belongs.
     *
     * @return The Ontology to which this Entity belongs.
     */
    Ontology getOntology();


    /**
     * Returns the unique identifier for this entity in the ontology.
     *
     * @return This is a unique identifier for this entity in the ontology.
     */
    String getGUID();


    /**
     * Returns the namespace for the Entity.  By default, the Folder path of the Entity is returned.
     * If the Entity doesn't have a folder, then an empty String is returned.
     */
    String getNamespace();


    /**
     * Returns the name of this Entity.
     *
     * @return This is a String representation of the name.
     */
    String getName();


    /**
     * Returns the description for this Entity.
     *
     * @return The description for this Entity.
     */
    String getDescription();


    /**
     * The stream to which this Entity will write itself.
     *
     * @param out The OutputStream.
     */
    void serialize(OutputStream out) throws IOException;


    /**
     * Returns the full path plus the name of this Entity.
     *
     * @return The full path plus the name of this Entity.
     */
    String getFullPath();


    /**
     * Returns the path of the Folder for this Entity.
     *
     * @return The path of the Folder for this Entity.
     */
    String getFolderPath();


    /**
     * Returns the Folder in which this Entity resides.
     *
     * @return The Folder in which this Entity resides.
     */
    Folder getFolder();


    /**
     * Returns the URL for the String used for this Concept in visualizations.
     *
     * @return A String representing the URL of the icon.  This may be null.
     */
    String getIconPath();


    /**
     * Returns the value associated with the provided key, or <code>null</code> if it does not exist.
     *
     * @return The value associated with the provided key, or <code>null</code> if it does not exist.
     */
    Object getTransientProperty(String key);


    /**
     * Returns the transient name-value pairs associated with the Entity.
     *
     * @return Returns the transient name-value pairs associated with the Entity.
     */
    Map getTransientProperties();


    /**
     * Returns the String associated with the provided key.
     *
     * @return Returns the String associated with the provided key.
     */
    String getHiddenProperty(String key);


    /**
     * Returns the hidden name-value pairs associated with the Entity.
     *
     * @return Returns the hidden name-value pairs associated with the Entity.
     */
    Map getHiddenProperties();


    /**
     * Returns the extended properties associated with the Entity.
     *
     * @return Returns the extended properties associated with the Entity.
     */
    Map getExtendedProperties();


    /**
     * Returns a String representation of the bindings used to create mappings on this Entity.
     *
     * @return a String representation of the bindings used to create mappings on this Entity.
     */
    String getBindingString();


    String getLastModified();

    String getAlias();
}
