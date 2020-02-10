package com.tibco.cep.studio.dbconcept.conceptgen;

import java.util.Set;

/**
 * 
 * @author bgokhale
 * This interface is used for storing relationships between entities.
 * Relationship can either be containment or reference
 * 
 */
public interface BaseRelationship {
	
	/**
	 * Types of relationships
	 * 
	 */
	public static final int REFERENCE = 0;
	public static final int CONTAINMENT = 1;

	/**
	 * 
	 * @return Name of the relationship
	 */
    public String getName();
    
    /**
     * 
     * @return Full name of the entity to which this relationship maps the parent to.
     */
    public String getChildEntityName();

    /**
     * 
     * @return BaseEntity object that this relationship holds.
     */
    public BaseEntity getChildEntity();
    
    /**
     * 
     * @return Type of relationship, one of the above integer values.
     */
    public int getRelationshipEnum();

    /**
     * 
     * @return How many instances of this entity can the parent hold. 0 means unbounded
     */
    public int getCardinality();
    
    /**
     * 
     * @return Set of RelationshipKey objects
     */
    public Set<?> getRelationshipKeySet();
    
    /**
     * 
     * @return int position at which this property appears in BaseEntity
     */
    public int getPosition();
    
}