package com.tibco.cep.studio.core.configuration;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.*;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.tpcl.IDriverXmlProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.*;

/**
 * @author Pranab Dhar
 *
 */
public class ChannelDriverLoader {
	
	public static final String EXTENSION_ATTR_CLASS = "class"; //$NON-NLS-1$
	
	public static final String EXTENSION_POINT_DRIVER_XML_PROVIDER = "com.tibco.cep.tpcl.driverXmlProvider";//$NON-NLS-1$
	
	private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
	
	
	/**
	 * @param choiceNode
	 * @return
	 */
	private static Choice buildChoiceConfiguration(Element choiceNode) {
		Choice choice = ChannelFactory.eINSTANCE.createChoice();
		
		//Get its displayed attribute
		String displayed = choiceNode.getAttribute(ATTR_DRIVER_PROPERTY_CHOICE_DISPLAYED);
		choice.setDisplayedValue(displayed);
		
		//Get its value
		String value = choiceNode.getAttribute(ATTR_DRIVER_PROPERTY_CHOICE_VALUE);
		choice.setValue(value);
		
		return choice;
	}
	

	
	/**
	 * @param choiceConfiguration
	 * @param choicesNode -> This can be null for extended configurations
	 */
	private static void buildChoicesConfiguration(ChoiceConfiguration choiceConfiguration, 
			                               Element choicesNode) {
		if (choicesNode != null) {
			//Get all <choice> children
			NodeList choiceNodes = 
				choicesNode.getElementsByTagName(ELEMENT_DRIVER_CONFIGURATION_CHOICE_NAME);
			
			for (int loop = 0, length = choiceNodes.getLength(); loop < length; loop++) {
				Element choiceNode = (Element)choiceNodes.item(loop);
				Choice choice = buildChoiceConfiguration(choiceNode);
				choiceConfiguration.getChoices().add(choice);
			}
		}
	}
	
	/**
	 * @param propertyNode
	 * @return
	 */
	private static ChoiceConfiguration buildPropertyConfig(Element propertyNode) {
		ChoiceConfiguration choiceConfiguration = 
			ChannelFactory.eINSTANCE.createChoiceConfiguration();
		
		//Get its name from attribute or child element
		String propertyName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_NAME);
		if (propertyName == null || propertyName.length() == 0) {
			NodeList nameNodes = 
				propertyNode.getElementsByTagName(ELEMENT_NAME);
			Element nameNode = (Element)nameNodes.item(0);
			propertyName = readTextFromNode(nameNode);
		}
		choiceConfiguration.setPropertyName(propertyName);
	
		//Get and check if parent channel
		NodeList parentNodes = 
			propertyNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTY_PARENT_NAME);
		Element parentNode = (Element)parentNodes.item(0);
		choiceConfiguration.setPropertyParent(readTextFromNode(parentNode));
	
		//add displayName
		final String displayName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DISPLAY_NAME);
		if(displayName != null) {
			choiceConfiguration.setDisplayName(displayName);
		}
		
		//Get config type from attribute or child element
		String propertyType = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_TYPE);
		if (propertyType == null || propertyType.length() == 0) {
			NodeList configTypeNodes = 
				propertyNode.getElementsByTagName(ELEMENT_DRIVER_TYPE_NAME);
			Element configTypeNode = (Element)configTypeNodes.item(0);
			propertyType = readTextFromNode(configTypeNode);
		}
		choiceConfiguration.setConfigType(propertyType);
		
		//Get default value if present
//		String defaultValue = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DEFAULT);
//		choiceConfiguration.setDefaultValue(defaultValue);
		
		//Read choices
		NodeList choicesNodes = 
			propertyNode.getElementsByTagName(ELEMENT_DRIVER_CONFIGURATION_CHOICES_NAME);
		if(choicesNodes.getLength() > 0) {
			Element choicesNode = (Element)choicesNodes.item(0);
			String defaultValue = choicesNode.getAttribute(ATTR_DRIVER_PROPERTY_DEFAULT);
			choiceConfiguration.setDefaultValue(defaultValue);
			//Build each individual choice config
			buildChoicesConfiguration(choiceConfiguration, choicesNode);
		}
		return choiceConfiguration;
	}
	
	
	
	/**
	 * @param configurationNode
	 * @return
	 */
	protected static List<ChoiceConfiguration> buildPropertyConfigs(Element configurationNode) {
		List<ChoiceConfiguration> configurations = new ArrayList<ChoiceConfiguration>(0);
		if (configurationNode == null) {
			return configurations;
		}
		//Get its <property> child
		NodeList propertyNodes = 
			configurationNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTY_NAME);
		
		for (int loop = 0, length = propertyNodes.getLength(); loop < length; loop++) { 
			Element propertyNode = (Element)propertyNodes.item(loop);
			ChoiceConfiguration choiceConfiguration = buildPropertyConfig(propertyNode);
			configurations.add(choiceConfiguration);
		}
		return configurations;
	}
	
	private static SimpleProperty constructPropertyConfig(final Element propertyNode) {
		final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
		
		final String propertyName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_NAME);
		property.setName(propertyName);
		
		final String propertyType = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_TYPE);
		property.setType(propertyType);
		
		final String defaultValue = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DEFAULT);
		property.setValue(defaultValue); 
		
		final String displayName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DISPLAY_NAME);
		property.setDisplayName(displayName);
		
		return property;
	}
	
	/**
	 * @param serializerConfig
	 * @param serializerNode
	 */
	private static void constructSerializerInfo(SerializerConfig serializerConfig, 
			                             Element serializerNode) {
		//Get <type> attr
		String serializerType = serializerNode.getAttribute(ATTR_SERIALIZER_TYPE);
		//Get class for this serializer
		String serializerClass = serializerNode.getAttribute(ATTR_SERIALIZER_CLASS);
		//Get default value
		String defaultVal = serializerNode.getAttribute(ATTR_SERIALIZER_DEFAULT);
		SerializerInfo serializerInfo = 
			ChannelFactory.eINSTANCE.createSerializerInfo();
		serializerInfo.setSerializerClass(serializerClass);
		serializerInfo.setSerializerType(serializerType);
		serializerInfo.setDefault(Boolean.parseBoolean(defaultVal));
		serializerConfig.getSerializers().add(serializerInfo);
	}

	
	/**
	 * @param serializersNode
	 * @return
	 */
	protected static SerializerConfig getDestinationSerializerConfig(Element serializersNode) {
		SerializerConfig serializerConfig = null;
		if (serializersNode == null) {
			return null;
		}
		//Get each serializer
		NodeList serializerNodes = 
			serializersNode.getElementsByTagName(ELEMENT_SERIALIZER_NAME);
		serializerConfig = ChannelFactory.eINSTANCE.createSerializerConfig();
		for (int loop = 0,length = serializerNodes.getLength(); loop < length; loop++) {
			Element serializerNode = (Element)serializerNodes.item(loop);
			constructSerializerInfo(serializerConfig, serializerNode);
		}
		return serializerConfig;
	}
	
	/**
	 * @param referenceNode
	 * @return
	 */
	protected static List<String> getReferenceTypes(Element referenceNode) {
		List<String> referencesAllowed = new ArrayList<String>(0);
		if (referenceNode == null) {
			return referencesAllowed;
		}
		//Get its <type> children
		NodeList refTypesNodes = referenceNode.getElementsByTagName(ELEMENT_REFRENCES_TYPE_NAME);
		for (int loop = 0,length = refTypesNodes.getLength(); loop < length; loop++) {
			Element refTypeNode = (Element)refTypesNodes.item(loop);
			String refType = readTextFromNode(refTypeNode);
			referencesAllowed.add(refType);
		}
		return referencesAllowed;
	}
	
	
	/**
	 * @param driverNode
	 * @throws Exception
	 */
	private static void loadDriver(final Element driverNode, Map<DRIVER_TYPE, DriverRegistration> driverMap) throws Exception {
		//Get the Type of the driver
		NodeList typeNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_TYPE_NAME);
		//Get the first one
		Element typeNode = (Element)typeNodes.item(0);
		String driverTypeText = readTextFromNode(typeNode);

		DRIVER_TYPE driver_type = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driver_type.setName(driverTypeText);
		
		//Get driver label
		NodeList labelNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_LABEL_NAME);
		//Get the first one
		Element labelNode = (Element)labelNodes.item(0);
		String label = (labelNode != null) ? readTextFromNode(labelNode) : driverTypeText;
		
		//Get driver description
		NodeList descNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_DESC_NAME);
		//Get the first one
		Element descNode = (Element)descNodes.item(0);
		String description = (descNode != null) ? readTextFromNode(descNode) : "";
		
		//Get driver version
		NodeList versionNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_VERSION_NAME);
		//Get the first one
		Element versionNode = (Element)versionNodes.item(0);
		String version = readTextFromNode(versionNode);
		
		//read all extended configurations
		final NodeList extendedConfigNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_EXTENDED_CONFIGURATION);

		List<ChoiceConfiguration> extendedConfigurations  = null;
		final List<Choice> choiceExtendedConfig = new ArrayList<Choice>();
		for(int i=0; i<extendedConfigNodes.getLength();i++) {
			final Element extendedConfigNode = (Element)extendedConfigNodes.item(i);
			if(extendedConfigNode.getParentNode().getNodeName().equals(ELEMENT_DRIVER_NAME)) {
				extendedConfigurations = 
					readExtendedConfigurations(extendedConfigNode, ELEMENT_DRIVER_NAME);
			} else if(extendedConfigNode.getParentNode().getNodeName().equals(DriverManagerConstants.ELEMENT_CHOICE)) {
				final Choice choice = ChannelFactory.eINSTANCE.createChoice();
				final String choiceValue = (String) ((Element) extendedConfigNode.getParentNode()).getAttribute(ATTR_DRIVER_PROPERTY_CHOICE_VALUE);
				choice.setValue(choiceValue);
				final ExtendedConfiguration extendConfig = readExtendConfigProperties(extendedConfigNode);
			    choice.getExtendedConfiguration().add(extendConfig);
				choiceExtendedConfig.add(choice);
			}
		}
		//Read the driver's properties
		NodeList propertiesNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTIES_NAME);
		//Get the first one
		Element propertiesNode = (Element)propertiesNodes.item(0);
		ChannelDescriptor channelDescriptor = null;
		//only read properties at driver level not under extendedConfiguration
		if(propertiesNode.getParentNode().getNodeName().equals(ELEMENT_DRIVER_NAME)) {
			channelDescriptor = readDescriptor(propertiesNode, driver_type, ChannelDescriptor.class);
		}
		//Read the driver's properties
		NodeList destinationsNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_DESTINATIONS_NAME);
		//Get the first one
		Element destinationsNode = (Element)destinationsNodes.item(0);
		DestinationDescriptor destinationsDescriptor = 
			readDescriptor(destinationsNode, driver_type, DestinationDescriptor.class);
		
		//Read allowed reference types
		NodeList referencesAllowedNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_REFRENCES_NAME);
		//Get first one
		Element referencesAllowedNode = (Element)referencesAllowedNodes.item(0);
		List<String> referencesAllowed = getReferenceTypes(referencesAllowedNode);
		
		//Read serializers
		NodeList serializersNodes = 
			driverNode.getElementsByTagName(ELEMENT_SERIALIZERS_NAME);
		//Get first one
		Element serializersNode = (Element)serializersNodes.item(0);
		SerializerConfig serializerConfig = getDestinationSerializerConfig(serializersNode);
		
		//Read property domains
		NodeList configurationNodes = 
			driverNode.getElementsByTagName(ELEMENT_DRIVER_CONFIGURATION_NAME);
		//Get first one
		Element configurationNode = (Element)configurationNodes.item(0);
		List<ChoiceConfiguration> choiceConfigurations = buildPropertyConfigs(configurationNode);
		
		//Create an add a driver registration
		DriverRegistration driverRegistration = ChannelFactory.eINSTANCE.createDriverRegistration();
		driverRegistration.setDriverType(driver_type);
		driverRegistration.setVersion(version);
		if(extendedConfigurations != null) {
			driverRegistration.getExtendedConfigurations().addAll(extendedConfigurations);
		}
		driverRegistration.getChoice().addAll(choiceExtendedConfig);
		driverRegistration.setDestinationDescriptor(destinationsDescriptor);
		driverRegistration.setChannelDescriptor(channelDescriptor);
		driverRegistration.setDescription(description);
		driverRegistration.setLabel(label);
		driverRegistration.getResourcesAllowed().addAll(referencesAllowed);
		driverRegistration.setSerializerConfig(serializerConfig);
		driverRegistration.getChoiceConfigurations().addAll(choiceConfigurations);
		
		driverMap.put(driver_type, driverRegistration);
		

	}
	
	/**
	 * Loads channel drivers using the IChannelDriverXMLLoader extension poing
	 * @return
	 * @throws CoreException
	 */
	public static Map<String,Map<DRIVER_TYPE,DriverRegistration>> loadDrivers() throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg.getConfigurationElementsFor(EXTENSION_POINT_DRIVER_XML_PROVIDER);
		List<URL> urls = new ArrayList<URL>();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(EXTENSION_ATTR_CLASS);
			if (o instanceof IDriverXmlProvider) {
				IDriverXmlProvider provider = (IDriverXmlProvider) o;
				urls.addAll(provider.getDriverXML());
			}
		}	
		return loadDrivers(urls);
	}
	

	
	public static Map<String,Map<DRIVER_TYPE, DriverRegistration>> loadDrivers(List<URL> urls) throws Exception {
		Map<String,Map<DRIVER_TYPE,DriverRegistration>> rMap = new HashMap<String,Map<DRIVER_TYPE,DriverRegistration>>();
		for(URL url:urls){
			Map<DRIVER_TYPE,DriverRegistration> dMap = new HashMap<DRIVER_TYPE,DriverRegistration>();
			rMap.put(url.toString(), dMap);
			DocumentBuilder documentBuilder = FACTORY.newDocumentBuilder();
			//Load the document from the stream
			Document doc = documentBuilder.parse(url.openStream());
			Element rootNode = doc.getDocumentElement();
			//The root node is presumably the <drivers> node
			//Get each <driver> node
			NodeList driverNodes = rootNode.getElementsByTagName(ELEMENT_DRIVER_NAME);
			//For each driver node
			for (int loop = 0,length = driverNodes.getLength(); loop < length; loop++) {
				Element driverNode = (Element)driverNodes.item(loop);
				loadDriver(driverNode, dMap);
			}
		}
		
		return rMap;
	}
	
	/**
	 * @param propertiesNode
	 * @param descriptor
	 */
	private  static <P extends PropertyDescriptorMap> void populateDescriptorProperties(Element propertiesNode,
			                                 P descriptor) {
		if (propertiesNode == null) {
			throw new IllegalArgumentException("Parameter cannot be null");
		}
		//get all <property> children
		NodeList propertyNodes = propertiesNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTY_NAME);
		for (int loop = 0, length = propertyNodes.getLength(); loop < length; loop++) {
			Element propertyNode = (Element)propertyNodes.item(loop);
			if (propertyNode != null) {
				populateDescriptorProperty(propertyNode, descriptor);
			}
		}
	}
	
	/**
	 * @param propertyNode
	 * @param concept
	 */
	private static <P extends PropertyDescriptorMap> void populateDescriptorProperty(Element propertyNode,
			                                           P descriptor) {
		if (propertyNode == null) {
			throw new IllegalArgumentException("Parameter cannot be null");
		}
		//Get the name attr value
		String propName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_NAME);
		//Get the name attr value
		String propDisplayName = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DISPLAY_NAME);
		//Get the type of the attr
		String propType = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_TYPE);
		//Get the default value
		String defaultValue = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_DEFAULT);
		//Check if mandatory
		String mandatory = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_MANDATORY); //Mandatory attrib not available
		String gvToggle = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_GV_TOGGLE);
		String mask = propertyNode.getAttribute(ATTR_DRIVER_PROPERTY_MASK);
		boolean isMandatory = Boolean.parseBoolean(mandatory);
		boolean isGvToggle = Boolean.parseBoolean(gvToggle);
		boolean isMask = Boolean.parseBoolean(mask);
		
		//Create a property
		PropertyDescriptor propertyDescriptor = ChannelFactory.eINSTANCE.createPropertyDescriptor();
		propertyDescriptor.setName(propName);
		if (propDisplayName != null) {
			propertyDescriptor.setDisplayName(propDisplayName);
		}
		if (propType.equals("Integer")) {
			propType = "int";
		}
		PROPERTY_TYPES propertyType = PROPERTY_TYPES.get(propType);
		if (propertyType != null) {
			propertyDescriptor.setType(propertyType.getLiteral());
		}
		propertyDescriptor.setDefaultValue(defaultValue);
		propertyDescriptor.setMandatory(isMandatory);
		propertyDescriptor.setGvToggle(isGvToggle);
		propertyDescriptor.setMask(isMask);
		//Create a descriptor entry
		PropertyDescriptorMapEntry propertyDescriptorMapEntry = 
			ChannelFactory.eINSTANCE.createPropertyDescriptorMapEntry();
		propertyDescriptorMapEntry.setKey(propName);
		propertyDescriptorMapEntry.setValue(propertyDescriptor);
		descriptor.getDescriptors().add(propertyDescriptorMapEntry);
	}

	
	/**
	 * @param propertiesNode
	 * @param driverType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <P extends PropertyDescriptorMap> P readDescriptor(Element propertiesNode, 
			                                                     DRIVER_TYPE driverType, 
			                                                     Class<P> clazz) throws Exception {
		P descriptor = null;
		if (clazz.isAssignableFrom(ChannelDescriptor.class)) {
			descriptor = (P)ChannelFactory.eINSTANCE.createChannelDescriptor();
		}
		if (clazz.isAssignableFrom(DestinationDescriptor.class)) {
			descriptor = (P)ChannelFactory.eINSTANCE.createDestinationDescriptor();
		}
		//Populate its properties
		populateDescriptorProperties(propertiesNode, descriptor);
		return descriptor;
	}
	
	/**
	 * @param extendedConfigNode
	 * @return
	 */
	private static ExtendedConfiguration readExtendConfigProperties(final Element extendedConfigNode) {
		final ExtendedConfiguration extendConfig = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		if (extendedConfigNode != null) {
			//retrive properties
			final NodeList propertiesNodes = 
				extendedConfigNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTIES_NAME);
			final Element propertiesNode = (Element)propertiesNodes.item(0);
			final NodeList propertyNodes = 
				propertiesNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTY_NAME);
			int length = propertyNodes.getLength();
			for (int i = 0; i < length; i++) {
				final Element propertyNode = (Element)propertyNodes.item(i);
				final SimpleProperty property = constructPropertyConfig(propertyNode);
				extendConfig.getProperties().add(property);
			}
		}
		return extendConfig;
	}
	
	
	/**
	 * @param extendedConfigNode
	 * @param driverType
	 * @param folder
	 * @return
	 * @throws Exception
	 */
	private static List<ChoiceConfiguration> readExtendedConfigurations(Element extendedConfigNode, String parentNodeName ) throws Exception {
		List<ChoiceConfiguration> choiceConfigurations = new ArrayList<ChoiceConfiguration>();
		if (extendedConfigNode != null) {
			//Get its children properties
			NodeList propertiesNodes = 
				extendedConfigNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTIES_NAME);
			Element propertiesNode = (Element)propertiesNodes.item(0);
			if (null != propertiesNode) {
				NodeList propertyNodes = 
					propertiesNode.getElementsByTagName(ELEMENT_DRIVER_PROPERTY_NAME);
				for (int loop = 0,length = propertyNodes.getLength(); loop < length; loop++) {
					Element propertyNode = (Element)propertyNodes.item(loop);
					//properties at driver-root extendConfig and not for properties under choice-root
					if(propertyNode.getParentNode().getParentNode().getParentNode().getNodeName().equals(parentNodeName)) { 
						ChoiceConfiguration choiceConfiguration = buildPropertyConfig(propertyNode);
						choiceConfigurations.add(choiceConfiguration);
					}
				}
			}
		}
		return choiceConfigurations;
	}
	
	/**
	 * @param node
	 * @return the test node child of this node
	 */
	private static String readTextFromNode(Element node) {
		if (node != null) {
			NodeList children = node.getChildNodes();
			for (int loop = 0,length = children.getLength(); loop < length; loop++) {
				//Get text node
				Node item = (Node)children.item(loop);
				if (item instanceof Text) {
					return item.getNodeValue();
				}
			}
		}
		return null;
	}

}
