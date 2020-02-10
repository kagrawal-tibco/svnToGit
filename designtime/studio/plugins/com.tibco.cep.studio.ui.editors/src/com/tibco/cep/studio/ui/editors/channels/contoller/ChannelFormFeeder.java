/**
 * 
 */
package com.tibco.cep.studio.ui.editors.channels.contoller;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HTTP;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * @author aathalye
 *
 */
public class ChannelFormFeeder {
	
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	/**
	 * Hold a reference to the {@linkplain DriverManager} object
	 * to retrieve driver configuration details.
	 */
	private DriverManager driverManager;
	
	/**
	 * Whether this feeder has been initalized
	 */
	private boolean initialized;
	
	/**
	 * The name of the project
	 */
	private String project;
	
	/**
	 * @param project
	 */
	public void initialize(String project) throws Exception {
		driverManager = StudioCorePlugin.getDefault().getDriverManager();
		this.project = project;
		initialized = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	/**
	 * Get a <code>List</code> of all configured driver types.
	 * <p>
	 * All driver xml files are used.
	 * </p>
	 * @see DRIVER_TYPE
	 * @return
	 */
	public List<DRIVER_TYPE> getDriverTypes(String projectName) {
		if (initialized) {
			return driverManager.getDriverTypes();
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * @param driverType
	 * @return
	 */
	public List<PropertyDescriptorMapEntry> getDriverProperties(DRIVER_TYPE driverType) {
		if (initialized) {
			List<PropertyDescriptorMapEntry> properties = new ArrayList<PropertyDescriptorMapEntry>(0);
			
			ChannelDescriptor channelDescriptor = 
				driverManager.getChannelDescriptor(driverType);
			
			if (channelDescriptor != null) {
				//Get its properties
				return channelDescriptor.getDescriptors();
			}
			return properties;
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Get any extended configuration for this driver type
	 * @param driverType
	 * @return
	 */
	public List<ChoiceConfiguration> getDriverExtendedConfigurations(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getExtendedConfigurations(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
		
	/**
	 * @param driverType
	 * @return
	 */
	public List<PropertyDescriptorMapEntry> getDestinationsConfiguration(DRIVER_TYPE driverType) {
		if (initialized) {
			List<PropertyDescriptorMapEntry> properties = new ArrayList<PropertyDescriptorMapEntry>(0);
			
			DestinationDescriptor destinationDescriptor = 
				driverManager.getDestinationDescriptor(driverType);
			
			if (destinationDescriptor != null) {
				//Get its properties
				return destinationDescriptor.getDescriptors();
			}
			return properties;
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Fetch the version of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverVersion(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getVersion(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Fetch the label of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverLabel(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getLabel(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Fetch the description of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverDescription(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getDescription(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Get {@linkplain List} of all allowed resource types for this driver
	 * @param driverType
	 * @return a {@linkplain List}
	 */
	public List<String> getAllowedResourceTypes(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getAllowedResourceTypes(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Get a {@linkplain List} of all the {@link ChoiceConfiguration} elements
	 * represented by &lt;configuration&gt; elements for this driver. 
	 * @param driverType
	 * @return a {@linkplain List}
	 */
	public List<ChoiceConfiguration> getAllPropertyConfigurations(DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getPropertyConfigurations(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	/**
	 * Get {@linkplain List} of all serializer classes for this driver.
	 * 
	 * @param driverType
	 * @return Empty list if no serializers are configured
	 */
	public List<String> getSerializerClasses(DRIVER_TYPE driverType) {
		List<String> serializers = new ArrayList<String>(0);
		if (initialized) {
			List<SerializerInfo> serializerInfos = 
				driverManager.getDestinationsSerializerClasses(driverType);
			if (serializerInfos != null) {
				for (SerializerInfo serializerInfo : serializerInfos) {
					String serializerClass = serializerInfo.getSerializerClass();
					serializers.add(serializerClass);
				}
			}
			return serializers;
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}
	
	public static void main(String[] r) throws Exception {
		ChannelFormFeeder delegate = new ChannelFormFeeder();
		delegate.initialize("Channel");
		DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driverType.setName(DRIVER_HTTP);
		List<ChoiceConfiguration> choiceConfigurations = 
			delegate.getAllPropertyConfigurations(driverType);
		choiceConfigurations.size();
	}
	/**
	 * Return default serializer class if any
	 * @param driverType
	 * @return String representing fully qualified name of the class
	 */
	public String getDefaultSerializerClass(DRIVER_TYPE driverType) {
		if (initialized) {
			SerializerInfo serializerInfo = driverManager.getDefaultSerializer(driverType);
			if (serializerInfo != null) {
				return serializerInfo.getSerializerClass();
			}
			return null;
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ChannelFormFeeder)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		ChannelFormFeeder other = (ChannelFormFeeder)obj;
		if (other.project.equalsIgnoreCase(project)) {
			return true;
		}
		return false;
	}

	public Choice getChoiceConfiguration(final DRIVER_TYPE driverType, final String choiceValue) {
		if (initialized) {
			final EList<Choice> choices = driverManager.getExtendedConfigurationForChoice(driverType);
			for(final Choice choice:choices) {
				if(choice.getValue().equals(choiceValue)) {
					return choice;
				}
			}
			return null;
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");	
	}
	
	public List<Choice> getAllChoiceConfigurations(final DRIVER_TYPE driverType) {
		if (initialized) {
			return driverManager.getExtendedConfigurationForChoice(driverType);
		}
		throw new IllegalArgumentException("Feeder not initialized for this project");	
	}
}
