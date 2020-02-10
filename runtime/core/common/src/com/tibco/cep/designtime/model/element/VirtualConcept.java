package com.tibco.cep.designtime.model.element;


import java.util.Iterator;

import com.tibco.cep.designtime.model.service.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 29, 2005
 * Time: 10:55:31 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents a Concept that is externalized to BE.
 */

public interface VirtualConcept extends Concept {


    /**
     * @return a DataSource
     */
    public DataSource getDataSource();


    /**
     * @return a boolean
     */
    public boolean isQueryAble();


    /**
     *
     */
    public boolean isInsertAble();


    /**
     *
     */
    public boolean isUpdateAble();


    /**
     *
     */
    public boolean isDeleteAble();


    /**
     * @return a boolean
     */
    public boolean isExtendAble(Concept concept);


    /**
     *
     */
    public Iterator primaryKeys();


}
