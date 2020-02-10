/**
 * 
 */
package com.tibco.cep.studio.common.legacy.comparators;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;

/**
 * Compare 2 entities. One of them is an {@link AbstractResource}, and other
 * one is {@link Entity}
 * @author aathalye
 *
 */
public interface IResourceComparator<A extends AbstractResource, E extends Entity> {
	
	/**
	 * Compare an {@link AbstractResource} with an {@link Entity}
	 * @param oldResource
	 * @param newResource
	 * @return a boolean
	 */
	LegacyOntCompliance compareTo(A oldResource, E newResource);
}
