/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.validation;

import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.service.channel.DriverRegistration}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DriverRegistrationValidator {
	boolean validate();

	boolean validateLabel(String value);
	boolean validateVersion(String value);
	boolean validateDescription(String value);
	boolean validateChannelDescriptor(ChannelDescriptor value);
	boolean validateDestinationDescriptor(DestinationDescriptor value);
	boolean validateResourcesAllowed(EList<String> value);
	boolean validateSerializerConfig(SerializerConfig value);
	boolean validateChoiceConfigurations(EList<ChoiceConfiguration> value);
	boolean validateExtendedConfigurations(EList<ChoiceConfiguration> value);
	boolean validateDriverType(DRIVER_TYPE value);
	boolean validateChoice(EList<Choice> value);
}
