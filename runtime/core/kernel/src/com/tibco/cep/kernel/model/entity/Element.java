package com.tibco.cep.kernel.model.entity;


import java.util.List;

/*
* Created by IntelliJ IDEA.
* User: nleong
* Date: Apr 7, 2004
* Time: 3:04:47 PM
*
* Copyright (c) 2004  TIBCO Software Inc.
* Contact: Nick Leong (nleong@tibco.com)
*
*/

/**
 * Base for mutable entities that may have children.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface Element extends Entity, Mutable {


    /**
     * Gets a list of all the children instances of this instance.
     *
     * @return a <code>List</code> of all children instances under this instance.
     * @.category public-api
     * @since 2.0.0
     */
    List getChildren();
    
    List getChildIds();


    /**
     * This method will be called to clean up when the element instance is retracted from the working memory
     * explicitly, or after a failure to assert.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void delete();

}


