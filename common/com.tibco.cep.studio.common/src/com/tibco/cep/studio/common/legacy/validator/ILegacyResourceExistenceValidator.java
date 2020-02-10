/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import java.util.List;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;

/**
 * @author aathalye
 *
 */
public interface ILegacyResourceExistenceValidator<A extends AbstractResource> {
	
	/**
	 * Validate a legacy {@link AbstractResource} with an equivalent (if any) 
	 * {@link Entity} in the new 4.0 project.
	 * 
	 * @param abstractResource -> The {@link AbstractResource} to validate against the new project
	 * @param compliances
	 * @return true | false
	 */
	void validate(A abstractResource, List<LegacyOntCompliance> compliances);
	
	void act(A abstractResource);
}
