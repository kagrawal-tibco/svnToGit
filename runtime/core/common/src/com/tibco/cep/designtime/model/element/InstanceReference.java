package com.tibco.cep.designtime.model.element;


import com.tibco.cep.designtime.model.EntityReference;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 7:47:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface InstanceReference extends EntityReference {


    /**
     * Returns the Instance to which this object refers.
     *
     * @return The Instance to which this object refers, or null if the Instance doesn't exist.
     */
    Instance getInstance();
}
