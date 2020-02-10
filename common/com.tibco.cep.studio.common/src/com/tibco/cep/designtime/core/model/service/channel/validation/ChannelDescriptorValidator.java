/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.validation;

import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;

/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ChannelDescriptorValidator {
	boolean validate();

	boolean validateDestinationDescriptor(DestinationDescriptor value);
}
