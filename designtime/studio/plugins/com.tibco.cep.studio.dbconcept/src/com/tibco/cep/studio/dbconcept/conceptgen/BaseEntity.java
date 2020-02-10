package com.tibco.cep.studio.dbconcept.conceptgen;

import java.util.List;

/**
 * @author bgokhale
 * This interface describes an entity
 * 
 */
public interface BaseEntity {
	
	/**
	 * Types of BE entities
	 */
	public static final int CONCEPT = 0;
	public static final int EVENT = 1;
	public static final int SCORECARD = 2;

	/**
	 * @return Name of the entity. Example: MyConcept
	 */
    public String getName();
    
	/**
	 * @return Name of the entity.
	 */
    public String getDescription();
    
    /**
     * 
     * @return Full name of the entity (path inclusive). Example: /Myproject/MyConcept 
     */
    
    public String getFullName();
    
    /**
     * 
     * @return Path or namespace portion of the full name. Example /MyProject/
     */
    public String getNamespace();
    
    /**
     * 
     * @return Full name of the super entity that this entity should extend
     */

    public String getSuperEntityName();

    /**
     * 
     * @return A list of BaseProperty objects that defines the properties of this entity
     */
    public List<?> getProperties();
    
    /**
     * 
     * @param propertyName Name of the property
     * @return BaseProperty object that corresponds to this name
     */

    public BaseProperty getProperty(String propertyName);

    /**
     * 
     * @return A list of BaseRelationship objects that are children of this entity
     */
    public List<?> getChildEntities();

    /**
     * 
     * @param relationshipName Name of the relationship property
     * @return Corresponding relationship
     */
    public BaseRelationship getRelationship(String relationshipName);

    /**
     * 
     * @return An alias name for this entity
     */
    public String getAlias();
    
    /**
     * 
     * @param dictionary An alias for this entity based on some nomenclature
     * @return
     */

    public String getAlias(String dictionary);
    
    
    /**
     * @return business event type of this entity
     */
    public int getType();

}



