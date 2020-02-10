package com.tibco.cep.designtime.model.service;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jul 29, 2005
 * Time: 10:57:30 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents an external connection to query, insert, update and delete instances of virtual concepts.
 */
public interface DataSource {


    /**
     *
     */
    public String getType();


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

}
