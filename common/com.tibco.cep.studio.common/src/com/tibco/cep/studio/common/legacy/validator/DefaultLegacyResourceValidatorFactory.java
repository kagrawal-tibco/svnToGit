/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import java.lang.reflect.Constructor;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;

/**
 * @author aathalye
 *
 */
public class DefaultLegacyResourceValidatorFactory implements
		ILegacyResourceValidatorFactory {
	
	private static ILegacyResourceValidatorFactory factory = new DefaultLegacyResourceValidatorFactory();

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.validator.ILegacyResourceValidatorFactory#create(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <A extends AbstractResource, T extends ILegacyResourceExistenceValidator<A>> T create(Class<A> clazz, 
			    																				 String dpPath,
			                                                                                     String rootPath) throws Exception {
		if (Concept.class.isAssignableFrom(clazz)) {
			Constructor<LegacyConceptExistenceValidator> constructor = 
				LegacyConceptExistenceValidator.class.getConstructor(String.class, String.class);
			return (T)constructor.newInstance(dpPath, rootPath);
		}
		if (Event.class.isAssignableFrom(clazz)) {
			Constructor<LegacyEventExistenceValidator> constructor = 
				LegacyEventExistenceValidator.class.getConstructor(String.class, String.class);
			return (T)constructor.newInstance(dpPath, rootPath);
		}
		//TODO Handle other resource types also
		return null;
	}
	
	public static ILegacyResourceValidatorFactory newInstance() {
		return factory;
	}

}
