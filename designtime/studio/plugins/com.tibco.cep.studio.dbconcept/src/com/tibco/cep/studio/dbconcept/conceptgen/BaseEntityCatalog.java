package com.tibco.cep.studio.dbconcept.conceptgen;

import java.util.Map;


/**
 * 
 * @author bgokhale
 * A catalog that holds all the entities.
 * 
 */
public interface BaseEntityCatalog {

	/**
	 * 
	 * @return A list of BaseEntity objects
	 */
	public Map<?, ?> getEntities();

	/**
	 * 
	 * @param entityName Full name of the entity
	 * @return BaseEntity object corresponding to this name
	 */
	public BaseEntity getEntity(String entityName);

	/**
	 * 
	 * @return Name of this catalog
	 */
	public String getName();

}