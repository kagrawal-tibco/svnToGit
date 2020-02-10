package com.tibco.cep.studio.dbconcept.conceptgen;

/**
 * This interface describes the relationship key
 * 
 * @author bgokhale
 *
 */
public interface RelationshipKey {
	
	public static final String UsedForSelect = "Y";
	
	/**
	 * 
	 * @return returns the parent key name
	 */
	public String getParentKey();

	/**
	 * 
	 * @return returns the child key name
	 */
	public String getChildKey();

	/**
	 * 
	 * @return returns true is key is used for select 
	 */

	public boolean isUsedForSelect();

}
