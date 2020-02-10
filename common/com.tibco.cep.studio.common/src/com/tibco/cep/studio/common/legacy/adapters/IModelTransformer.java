/**
 * 
 */
package com.tibco.cep.studio.common.legacy.adapters;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.designtime.core.model.Entity;

/**
 * Implement this interface to transform one model object to its equivalent in other model
 * <p>
 * example : Convert the {@link Concept} to {@link com.tibco.cep.designtime.core.model.element.Concept}
 * </p>
 * @author aathalye
 *
 */
public interface IModelTransformer<A extends AbstractResource, B extends Entity> extends ITransformer<A, B> {
	
	/**
	 * @param adaptFrom
	 * @param adaptTo
	 */
	B transform(A transformFrom, B transformTo);
	
	/**
	 * @param adaptFrom
	 * @param adaptTo
	 */
	A transform(B transformFrom, A transformTo);
}
