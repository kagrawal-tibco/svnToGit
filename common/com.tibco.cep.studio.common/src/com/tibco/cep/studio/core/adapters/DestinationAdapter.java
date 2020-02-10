/**
 * 
 */
package com.tibco.cep.studio.core.adapters;


import java.util.Properties;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;


/**
 * @author aathalye
 *
 */
public class DestinationAdapter extends EntityAdapter<Destination> implements
		com.tibco.cep.designtime.model.service.channel.Destination {

	/**
	 * @param adapted
	 */
	public DestinationAdapter(Destination adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.adapters.EntityAdapter#getAdapted()
	 */
	@Override
	protected Destination getAdapted() {
		return adapted;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Destination#getDriverConfig()
	 */
	
	public DriverConfig getDriverConfig() {
		com.tibco.cep.designtime.core.model.service.channel.DriverConfig driverConfig = adapted.getDriverConfig();
		if (!(driverConfig instanceof HttpChannelDriverConfig)) {
			return new DriverConfigAdapter<com.tibco.cep.designtime.core.model.service.channel.DriverConfig>(driverConfig, emfOntology);
		}
		return new HTTPDriverConfigAdapter((HttpChannelDriverConfig)driverConfig, emfOntology);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Destination#getEventURI()
	 */
	
	public String getEventURI() {
		return adapted.getEventURI();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Destination#getProperties()
	 */
	
	public Properties getProperties() {
		final Properties props = new Properties();
		for (Entity p : this.adapted.getProperties().getProperties()) {
			final SimpleProperty sp = (SimpleProperty) p;
			props.put(sp.getName(), sp.getValue());
		}
		return props;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Destination#getProperty(java.lang.String)
	 */
	
	public Object getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.Destination#getSerializerDeserializerClass()
	 */
	
	public String getSerializerDeserializerClass() {
		return adapted.getSerializerDeserializerClass();
	}
	
	@Override
	public boolean isInputEnabled() {
		return adapted.isInputEnabled();
	}
	
	@Override
	public boolean isOutputEnabled() {
		return adapted.isOutputEnabled();
	}


    @Override
    public String getFullPath() {
		return this.adapted.getDriverConfig().getChannel().getFullPath() + "/" + this.getName();
	}
}
