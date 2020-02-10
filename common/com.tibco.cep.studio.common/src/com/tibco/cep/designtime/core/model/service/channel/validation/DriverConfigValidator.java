/**
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.validation;

import com.tibco.cep.designtime.core.model.PropertyMap;

import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DriverConfigValidator {
	boolean validate();

	boolean validateConfigMethod(CONFIG_METHOD value);
	boolean validateReference(String value);
	boolean validateLabel(String value);
	boolean validateChannel(Channel value);
	boolean validateProperties(PropertyMap value);
	boolean validateDestinations(EList<Destination> value);
	boolean validateExtendedConfiguration(ExtendedConfiguration value);
	boolean validateDriverType(DRIVER_TYPE value);
	boolean validateChoice(Choice value);
}
