package com.tibco.cep.query.model.validation;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 9:37:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Resolvable {
    /**
     * Resolves a context by matching known BE objects to unknown identifiers
     * @return boolean true if resolved else false
     * @throws Exception
     */
     boolean resolveContext() throws Exception;

    /**
     * Returns true if the context has been resolved or false
     * @return boolean
     */
     boolean isResolved();

}
