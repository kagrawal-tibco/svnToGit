/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import com.tibco.cep.decisionproject.ontology.AbstractResource;

/**
 * @author aathalye
 *
 */
public interface ILegacyResourceValidatorFactory {
	
	/**
	 * Create an instance of {@link ILegacyResourceExistenceValidator<A>} based on input {@link Class}
	 * object of the {@link AbstractResource}
	 * @param <A> -> The {@link AbstractResource}
	 * @param <T> -> The instance of {@link ILegacyResourceExistenceValidator<A>}
	 * @param clazz -> The {@link Class} of an {@link AbstractResource}
	 * @param dpPath -> The full path of the Decision Project
	 * @param rootPath -> The base project path of the selected designer project
	 * @return -> The appropriate Factory class
	 * @throws Exception
	 */
	<A extends AbstractResource, T extends ILegacyResourceExistenceValidator<A>> T create (Class<A> clazz, String dpPath, String rootPath) throws Exception;
}
