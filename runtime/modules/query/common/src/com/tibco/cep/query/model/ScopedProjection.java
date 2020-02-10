/**
 * 
 */
package com.tibco.cep.query.model;


/**
 * @author pdhar
 *
 */
public interface ScopedProjection extends Projection {
	
	/**
	 * @return the array of Projection concepts for the given scope
	 */
	Entity[] getScopedEntities();	
	
}
