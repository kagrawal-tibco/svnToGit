package com.tibco.cep.kernel.model.entity;


import com.tibco.cep.kernel.model.knowledgebase.Handle;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: May 18, 2004
* Time: 1:55:44 PM
* To change this template use Options | File Templates.
*/

/**
 * Base for events and elements.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
// TODO document the class
public interface Entity {


    final public static String ATTRIBUTE_ID = "Id";
    final public static String ATTRIBUTE_EXTID = "extId";
    final public static String ATTRIBUTE_TYPE = "type";


    /**
     * Gets the external Id of this <code>Entity</code>.
     * This external Id should be unique within an engine.
     *
     * @return the external Id of this <code>Entity</code>.
     * @.category public-api
     * @since 2.0.0
     */
    String getExtId();


    /**
     * Gets the internal id of this <code>Entity</code>.
     *
     * @return the id of this <code>Entity</code>..
     * @.category public-api
     * @since 2.0.0
     */
    long getId();


    /**
     * Called whenever the entity is asserted into a WorkingMemory.
     *
     * @param handle the <code>Handle</code> that the working memory is created for this entity
     */
    void start(Handle handle);

    void setLoadedFromCache();

    boolean isLoadedFromCache();

    public String getType();

    void setPropertyValue(String name, Object value) throws Exception;

    Object getPropertyValue(String name) throws NoSuchFieldException;    
}
