/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.service.channel.HTTPDriverConfig;
import com.tibco.cep.designtime.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.designtime.model.service.channel.impl.WebApplicationDescriptorImpl;

/**
 * @author aditya
 *
 */
public class HTTPDriverConfigAdapter extends DriverConfigAdapter<com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig> implements HTTPDriverConfig {

	/**
	 * @param adapted
	 * @param emfOntology
	 */
	public HTTPDriverConfigAdapter(com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig adapted,
			                       Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	@Override
	public List<WebApplicationDescriptor> getWebApplicationDescriptors() {
		List<com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor> webApps = adapted.getWebApplicationDescriptors();
		List<WebApplicationDescriptor> webAppDescriptors = new ArrayList<WebApplicationDescriptor>(webApps.size());
		
		for (com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor descriptor : webApps) {
			WebApplicationDescriptor webApplicationDescriptor = new WebApplicationDescriptorImpl(descriptor.getContextURI(), descriptor.getWebAppSourcePath());
			webAppDescriptors.add(webApplicationDescriptor);
		}
		return webAppDescriptors;
	}
}
