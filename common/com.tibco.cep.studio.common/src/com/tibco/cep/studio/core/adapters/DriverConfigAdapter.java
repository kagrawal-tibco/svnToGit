/**
 * 
 */
package com.tibco.cep.studio.core.adapters;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyInstance;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.model.service.channel.impl.ExtendedConfigurationImpl;
import com.tibco.cep.designtime.model.service.channel.impl.PropertyDescriptorImpl;


/**
 * This does not extend {@linkplain EntityAdapter} because this is not an
 * {@linkplain Entity}.
 * @author aathalye
 *
 */
public class DriverConfigAdapter<D extends com.tibco.cep.designtime.core.model.service.channel.DriverConfig> implements DriverConfig, ICacheableAdapter {
	
	/**
	 * The adapted instance
	 */
	protected D adapted;
	
	protected Ontology emfOntology;
	
	public DriverConfigAdapter(D adapted,
			                   Ontology emfOntology) {
		if (adapted == null) {
			throw new IllegalArgumentException("Adapted Driver Config cannot be null");
		}
		this.adapted = adapted;
		this.emfOntology = emfOntology;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getAllowedResourceTypes()
	 */
	
	public String[] getAllowedResourceTypes() {
		throw new UnsupportedOperationException("This may not be needed since it is already present with Driver Manager");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getAvailableDestinations()
	 */
	
	public Iterator<Destination> getAvailableDestinations() {
		throw new UnsupportedOperationException("This may not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getChannel()
	 */
	
	public Channel getChannel() {
		com.tibco.cep.designtime.core.model.service.channel.Channel adaptedChannel = adapted.getChannel();
		if (adaptedChannel != null) {
			try {
				return CoreAdapterFactory.INSTANCE.createAdapter(adaptedChannel, emfOntology);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getConfigMethod()
	 */
	
	public String getConfigMethod() {
		return adapted.getConfigMethod().getLiteral();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDefaultSerializer()
	 */
	
	public String getDefaultSerializer() {
		//The default serializer will come from the driver manager for this channel
		DriverManager driverManager = getDriverManager();
		DRIVER_TYPE driverType = adapted.getDriverType();
		if (driverManager != null) {
			return driverManager.getDefaultSerializer(driverType).getSerializerClass();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDestination(java.lang.String)
	 */
	
	public Destination getDestination(String destinationName) {
		com.tibco.cep.designtime.core.model.service.channel.Channel adaptedChannel = adapted.getChannel();
		if (adaptedChannel != null) {
			//Get its destinations
			List<com.tibco.cep.designtime.core.model.service.channel.Destination> destinations = adapted.getDestinations();
			for (com.tibco.cep.designtime.core.model.service.channel.Destination dest : destinations) {
				//Search for this one
				if (dest.getName().equals(destinationName)) {
					try {
						return CoreAdapterFactory.INSTANCE.createAdapter(dest, emfOntology);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Return the {@linkplain DriverManager} associated with the adapted {@linkplain com.tibco.cep.designtime.core.model.service.channel.Channel}
	 * @return null if not such manager exists
	 */
	private DriverManager getDriverManager() {
		com.tibco.cep.designtime.core.model.service.channel.Channel adaptedChannel = adapted.getChannel();
		if (adaptedChannel != null) { 
			DriverManager driverManager = adaptedChannel.getDriverManager();
			return driverManager;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDestinations()
	 */
	
	public Iterator<Destination> getDestinations() {
		List<com.tibco.cep.designtime.core.model.service.channel.Destination> destinations = adapted.getDestinations();
		Set<Destination> dests = new LinkedHashSet<Destination>();
		for (com.tibco.cep.designtime.core.model.service.channel.Destination destination : destinations) {
			try {
				Destination eachDestination = CoreAdapterFactory.INSTANCE.createAdapter(destination, emfOntology);
				dests.add(eachDestination);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return dests.iterator();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDestinationDescriptor()
	 */
	
	public DestinationDescriptor getDestinationDescriptor() {
		DriverManager driverManager = getDriverManager();
		DRIVER_TYPE driverType = adapted.getDriverType();
		com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor destinationDescriptor = 
			driverManager.getDestinationDescriptor(driverType);
		if (destinationDescriptor != null) {
			try {
				return new DestinationDescriptorAdapter(destinationDescriptor);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDestinationsList()
	 */
	
	public List<?> getDestinationsList() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getDriverType()
	 */
	
	public String getDriverType() {
		return adapted.getDriverType().getName();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getLabel()
	 */
	
	public String getLabel() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getPropertiesConcept()
	 */
	
	public com.tibco.cep.designtime.model.service.channel.ChannelDescriptor getChannelDescriptor() {
		DriverManager driverManager = getDriverManager();
		DRIVER_TYPE driverType = adapted.getDriverType();
		ChannelDescriptor channelDescriptor = 
			driverManager.getChannelDescriptor(driverType);
		if (channelDescriptor != null) {
			try {
				return new ChannelDescriptorAdapter(channelDescriptor);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getPropertiesInstance()
	 */
	
	public Properties getChannelProperties() {
		final PropertyMap pm = this.adapted.getProperties();
		final Properties props = new Properties();
		if (pm != null) {
			for (com.tibco.cep.designtime.core.model.Entity p : pm.getProperties()) {
				final SimpleProperty sp = (SimpleProperty) p;
				props.put(sp.getName(), sp.getValue());
			}
		}
		//Abhijit
		final Choice choice = this.adapted.getChoice();
		if(choice != null) {
			final EList<com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration> extendedConfigurations = choice.getExtendedConfiguration();
			for(final com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration extendedConfiguration:extendedConfigurations) {
				for(final SimpleProperty property:extendedConfiguration.getProperties()) {
					props.put(property.getName(), property.getValue());
				}
			}
		}
		return props;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getProperty(java.lang.String)
	 */
	
	public PropertyInstance getProperty(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getPropertyAsString(java.lang.String)
	 */
	
	public String getPropertyAsString(String propertyName) {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getReference()
	 */
	
	public String getReference() {
		return adapted.getReference();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#test()
	 */
	
	public String test() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.service.channel.DriverConfig#getExtendedConfiguration()
	 */
	public ExtendedConfiguration getExtendedConfiguration() {
		com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration adaptedExtConfig = adapted.getExtendedConfiguration();
		ExtendedConfiguration extendedConfiguration = new ExtendedConfigurationImpl();

        if (adaptedExtConfig == null) {
            return null;
        }
        for (SimpleProperty property : adaptedExtConfig.getProperties()) {
			String propertyName = property.getName();
			String defaultValue = property.getValue();
			PropertyDescriptor propertyDescriptor =
				new PropertyDescriptorImpl(propertyName, -1, defaultValue, null);
			extendedConfiguration.put(propertyName, propertyDescriptor);
		}
		return extendedConfiguration;
	}
}
