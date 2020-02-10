/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;

/**
 * @author aathalye
 *
 */
public class ChannelAdapter extends EntityAdapter<Channel> implements
		com.tibco.cep.designtime.model.service.channel.Channel, ICacheableAdapter {

	/**
	 * @param adapted
	 */
	public ChannelAdapter(Channel adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
		
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.adapters.EntityAdapter#getAdapted()
	 */
	@Override
	protected Channel getAdapted() {
		return adapted;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Channel#getDriver()
	 */
	
	public DriverConfig getDriver() {
		com.tibco.cep.designtime.core.model.service.channel.DriverConfig driverConfig = adapted.getDriver();
		if (!(driverConfig instanceof HttpChannelDriverConfig)) {
			return new DriverConfigAdapter<com.tibco.cep.designtime.core.model.service.channel.DriverConfig>(driverConfig, emfOntology);
		}
		return new HTTPDriverConfigAdapter((HttpChannelDriverConfig)driverConfig, emfOntology);
	}
}
