/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Resolvable;

/**
 * @author pdhar
 *
 */
public interface Entity extends Resolvable, RegistryContext, TypedContext {

    /**
     * @return the entity name
     */
    String getEntityName();

    /**
     * @param name the entity name to set
     */
    void setEntityName(String name);

    /**
     * @return the entity name prefixed with parent folder paths
     */
    String getEntityPath();


    /**
     * @return EntityAttributes
     */
    EntityAttribute getEntityAttribute(String name) throws Exception;

    /**
     * @return the list of Entity attributes
     */
    EntityAttribute[] getEntityAttributes() throws Exception;

    /**
     * @return EntityProperty
     */
    EntityProperty getEntityProperty(String name) throws Exception;

    /**
     * @return the list of Entity properties
     */
    EntityProperty[] getEntityProperties()throws Exception;

    /**
     * @return the implemented class of the Entity
     * @throws Exception
     */
    Class getEntityClass() throws Exception;


}
