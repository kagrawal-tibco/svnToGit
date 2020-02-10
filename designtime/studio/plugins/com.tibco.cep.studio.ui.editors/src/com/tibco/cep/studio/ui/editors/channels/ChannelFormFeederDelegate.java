/**
 * 
 */
package com.tibco.cep.studio.ui.editors.channels;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.ChoiceConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.PropertyDescriptorMapEntry;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.ui.editors.channels.contoller.ChannelFormFeeder;
import com.tibco.cep.studio.ui.editors.channels.contoller.ChannelFormFeederManager;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;

/**
 * A delegate class to be used by the channel UI to get template
 * fields input for rendering each driver's configuration.
 * @author aathalye
 *
 */
public class ChannelFormFeederDelegate {
	
	private ChannelFormFeeder formFeeder;
		
	public ChannelFormFeederDelegate(final String project) throws Exception {
		//Get the feeder for this project
		formFeeder = ChannelFormFeederManager.INSTANCE.getFormFeeder(project);
	}
	
	/**
	 * Get a <code>String[]</code> of all configured driver types.
	 * <p>
	 * All driver xml files are used.
	 * </p>
	 * @return
	 */
	public String[] getConfiguredDriverTypes(String projectName) {
		// ensure project configuration is loaded
		if (projectName != null) {
			StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		}
		List<DRIVER_TYPE> driverTypes = formFeeder.getDriverTypes(projectName);
		String[] drivers = new String[driverTypes.size()];
		int counter = 0;
		for (DRIVER_TYPE driver_type : driverTypes) {
			drivers[counter++] = driver_type.getName();
		}
		return drivers;
	}
	
	/**
	 * Get all the properties configured for this driver.
	 * <p>
	 * <b>
	 * Used only if Config Method is selected to "Properties"
	 * </b>
	 * </p>
	 * @param driverType
	 * @return {@linkplain List<PropertyConfiguration>}
	 */
	public List<PropertyConfiguration> getDriverProperties(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		List<PropertyDescriptorMapEntry> propDefs = formFeeder.getDriverProperties(driver_Type);
		//Get all choice configurations for this driver
		List<ChoiceConfiguration> choiceConfigurations = 
			formFeeder.getAllPropertyConfigurations(driver_Type);
		List<PropertyConfiguration> driverProps = getProperties(propDefs, choiceConfigurations);
		return driverProps;	
	}
	
	/**
	 * Get any extended configuration for this driver.
	 * <p>
	 * <b>
	 * This configuration exists irrespective of the configuration method
	 * </b>
	 * </p>
	 * @param driverType
	 * @return {@linkplain List<PropertyConfiguration>}
	 */
	public List<PropertyConfiguration> getExtendedConfiguration(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		List<ChoiceConfiguration> choiceConfigurations = 
			formFeeder.getDriverExtendedConfigurations(driver_Type);
		List<PropertyConfiguration> driverExtendedConfig = 
				new ArrayList<PropertyConfiguration>(choiceConfigurations.size());
		for (ChoiceConfiguration choiceConfiguration : choiceConfigurations) {
			PropertyConfiguration propertyConfiguration = new PropertyConfiguration();
			propertyConfiguration.setPropertyName(choiceConfiguration.getPropertyName());
			propertyConfiguration.setPropertyConfigType(choiceConfiguration.getConfigType());
			propertyConfiguration.setDisplayName(choiceConfiguration.getDisplayName());
			List<Choice> choices = choiceConfiguration.getChoices();
			List<String> displayedValues = new ArrayList<String>(choices.size());
			List<String> values = new ArrayList<String>(choices.size());
			for (Choice choice : choices) {
				displayedValues.add(choice.getDisplayedValue());
				
				values.add(choice.getValue());
			}
			propertyConfiguration.setDisplayedValues(displayedValues);
			propertyConfiguration.setValues(values);
			propertyConfiguration.setDefaultValue((String)choiceConfiguration.getDefaultValue());
			driverExtendedConfig.add(propertyConfiguration);
		}
		return driverExtendedConfig;
	}
	
	/**
	 * get extendConfiguration-properties under choice element in a drivers.xml
	 * 
	 * @param driverType
	 * @param choiceValue
	 * @return
	 */
	public List<PropertyConfiguration> getPropertiesForChoice(final String driverType, final String choiceValue) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		final List<PropertyConfiguration> choiceProperties = new ArrayList<PropertyConfiguration>();
		final DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		final Choice choiceConfig = formFeeder.getChoiceConfiguration(driver_Type, choiceValue);
		if(choiceConfig == null) {
			return null;
		}
		final EList<ExtendedConfiguration> extendedConfiguration = choiceConfig.getExtendedConfiguration();
		if(extendedConfiguration == null) {
			return null;
		}
		//only one extend config under a choice
		final EList<SimpleProperty> properties = extendedConfiguration.get(0).getProperties();  
		for (final SimpleProperty property : properties) {
			final PropertyConfiguration propertyConfiguration = new PropertyConfiguration();
			propertyConfiguration.setPropertyName(property.getName());
			propertyConfiguration.setPropertyConfigType(property.getType());
			propertyConfiguration.setDefaultValue((String)property.getValue());
			propertyConfiguration.setDisplayName(property.getDisplayName());
			choiceProperties.add(propertyConfiguration);
		}
		return choiceProperties;
	}
	
	/**
	 * get extendConfiguration-properties under choice element in a drivers.xml
	 * 
	 * @param driverType
	 * @param choiceValue
	 * @return
	 */
	public ExtendedConfiguration getExtendedConfigurationForChoice(final String driverType, final String choiceValue) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		final DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		final Choice choiceConfig = formFeeder.getChoiceConfiguration(driver_Type, choiceValue);
		if(choiceConfig == null) {
			return null;
		}
		final EList<ExtendedConfiguration> extendedConfiguration = choiceConfig.getExtendedConfiguration();
		if(extendedConfiguration == null) {
			return null;
		}
		//only one extend config under a choice
		return extendedConfiguration.get(0);
	}
	
	/**
	 * get extendConfiguration-properties under choice element in a drivers.xml
	 * 
	 * @param driverType
	 * @param choiceValue
	 * @return
	 */
	public List<String> getExtenConfigChoices(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		final DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		final List<Choice> choices = formFeeder.getAllChoiceConfigurations(driver_Type);
		final List<String> choiceValues = new ArrayList<String>();
		for(final Choice choice:choices) {
			choiceValues.add(choice.getValue());
		}
		return choiceValues;
	}
	
	
	/**
	 * Get all properties for a destination for this driver type.
	 * @param driverType
	 * @return {@linkplain List<PropertyConfiguration>}
	 */
	public List<PropertyConfiguration> getDestinationConfiguration(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		List<PropertyDescriptorMapEntry> propDescriptorEntries = 
				formFeeder.getDestinationsConfiguration(driver_Type);
		List<ChoiceConfiguration> choiceConfigurations = 
				formFeeder.getAllPropertyConfigurations(driver_Type);
		List<PropertyConfiguration> destinationProps = 
				getProperties(propDescriptorEntries, choiceConfigurations);
		return destinationProps;
	}
	
	/**
	 * @param propDefs
	 * @return
	 */
	private List<PropertyConfiguration> getProperties(List<PropertyDescriptorMapEntry> propDefs ,
			                                          List<ChoiceConfiguration> choiceConfigurations) {
		List<PropertyConfiguration> properties = 
			new ArrayList<PropertyConfiguration>(propDefs.size());
		for (PropertyDescriptorMapEntry prop : propDefs) {
			//Key is property name
			String key = prop.getKey();
			PropertyDescriptor propertyDescriptor = prop.getValue();
			PropertyConfiguration configuration = new PropertyConfiguration();
			//See if any choice exists for this property
			ChoiceConfiguration choiceConfiguration = getChoiceConfigFor(key, choiceConfigurations);
			buildProperty(propertyDescriptor, configuration, choiceConfiguration);
			properties.add(configuration);
		}
		return properties;
	}
	
	/**
	 * @param propName
	 * @param choiceConfigurations
	 * @return
	 */
	private ChoiceConfiguration getChoiceConfigFor(String propName,
			                                       List<ChoiceConfiguration> choiceConfigurations) {
		for (ChoiceConfiguration choiceConfiguration : choiceConfigurations) {
			if (choiceConfiguration.getPropertyName().equals(propName)) {
				return choiceConfiguration;
			}
		}
		return null;
	}
	
	
	/**
	 * @param from
	 * @param to
	 * @param choiceConfiguration
	 */
	private void buildProperty(PropertyDescriptor from, 
			                   PropertyConfiguration to,
			                   ChoiceConfiguration choiceConfiguration) {
		to.setPropertyName(from.getName());
		String propDisplayName = from.getDisplayName();
		if (propDisplayName != null && !propDisplayName.isEmpty()) {
			to.setDisplayName(propDisplayName);
		}
		to.setPropertyConfigType(from.getType());
		to.setDefaultValue(from.getDefaultValue());
		to.setMandatory(from.isMandatory());
		to.setGvToggle(from.isGvToggle());
		to.setMask(from.isMask());
		if (choiceConfiguration != null) {
			//This means some domain values are associated with this property
			List<Choice> choices = choiceConfiguration.getChoices();
			List<String> displayedValues = new ArrayList<String>(choices.size());
			List<String> values = new ArrayList<String>(choices.size());
			for (Choice choice : choices) {
				displayedValues.add(choice.getDisplayedValue());
				values.add(choice.getValue());
			}
			to.setDisplayedValues(displayedValues);
			to.setValues(values);
		}
	}
	
	/**
	 * Fetch the label of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverLabel(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getDriverLabel(driver_Type);
	}
	
	/**
	 * Fetch the version of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverVersion(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getDriverVersion(driver_Type);
	}
	
	/**
	 * Fetch the description of this driver.
	 * @param driverType
	 * @return a String
	 */
	public String getDriverDescription(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getDriverDescription(driver_Type);
	}
	
	/**
	 * Get {@linkplain List} of all allowed resource types for this driver
	 * @param driverType
	 * @return
	 */
	public List<String> getAllowedResourceTypes(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getAllowedResourceTypes(driver_Type);
	}
	
	/**
	 * Get {@linkplain List} of all configured serializers for this driver
	 * @param driverType
	 * @return empty list if no serializers
	 */
	public List<String> getSerializerClasses(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getSerializerClasses(driver_Type);
	}
	
	/**
	 * Get default serializer class for this driver
	 * @param driverType
	 * @return a null if there are no serializers
	 */
	public String getDefaultSerializerClass(final String driverType) {
		if (driverType == null) {
			throw new IllegalArgumentException("Driver Type cannot be null");
		}
		DRIVER_TYPE driver_Type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_Type.setName(driverType);
		return formFeeder.getDefaultSerializerClass(driver_Type);
	}
}
