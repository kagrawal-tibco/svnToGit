/**
 * 
 */
package com.tibco.cep.query.model;

import com.tibco.cep.query.model.validation.Resolvable;

/**
 * @author pdhar
 *
 */
public interface Projection
        extends Aliased, QueryContext, TypedContext, Resolvable
{
	
    /**
    * @return ModelContext the
    */
   Expression getExpression();

    /**
     * Returns the projection expression as parsed in String form
      * @return
     */
   String getProjectionText();

	

}
