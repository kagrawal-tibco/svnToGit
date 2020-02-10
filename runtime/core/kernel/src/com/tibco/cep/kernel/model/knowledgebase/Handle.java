package com.tibco.cep.kernel.model.knowledgebase;

import com.tibco.cep.kernel.core.base.EntitySharingLevel;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 3:14:57 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Representation of an <code>Object</code> in the kernel.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface Handle {


    /**
     * Gets the object associated to this handle.
     *
     * @return the object of this handle.
     * @.category public-api
     * @since 2.0.0
     */
    Object getObject();


    /**
     * Gets the type info associated to this handle.
     *
     * @return the type info of this handle.
     * @since 2.0.0
     */
    TypeInfo getTypeInfo();


    /** Check if the handle is associated to the object
     *
     * @return boolean   true if associated to an object , otherwise false.
     * @since 3.0.1
     */
    boolean hasRef();
    
    EntitySharingLevel getSharingLevel();
}
