/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.validation;


/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SerializerInfoValidator {
	boolean validate();

	boolean validateSerializerType(String value);
	boolean validateSerializerClass(String value);
	boolean validateDefault(boolean value);
}
