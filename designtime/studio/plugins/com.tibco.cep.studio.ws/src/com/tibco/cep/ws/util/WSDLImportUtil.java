package com.tibco.cep.ws.util;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HTTP;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ModelUtilsCore;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapAddress;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;

public class WSDLImportUtil {
	
	private static final ExpandedName SOAP11_ADDRESS_XNAME =
			ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.ADDRESS_ELEMENT);
	private static final ExpandedName SOAP12_ADDRESS_XNAME =
			ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.ADDRESS_ELEMENT);

	private static final String SERVER_TYPE_HTTP_COMPONENTS = "TOMCAT";
	
	public static Channel createChannel(String projectName, String folder, String namespace,  String fileName ){
		Channel newChannel = ChannelFactory.eINSTANCE.createChannel();
		populateDriverConfig(newChannel);
		
		populateEntity(projectName, fileName, folder , namespace,  newChannel);
		return newChannel;
	}
	
	private static void populateDriverConfig(Channel newChannel) {
		DriverConfig newDriverConfig = ChannelFactory.eINSTANCE.createHttpChannelDriverConfig();
		newChannel.setDriver(newDriverConfig);
		DRIVER_TYPE driverType = ChannelFactory.eINSTANCE.createDriverTypeInfo();
		driverType.setName(DRIVER_HTTP);
		newDriverConfig.setDriverType(driverType);
		newDriverConfig.setConfigMethod(CONFIG_METHOD.get(1));
		newDriverConfig.setLabel(DRIVER_HTTP);
		newDriverConfig.setChannel(newChannel);
		
		ExtendedConfiguration extendedConfiguration = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
		simpleProperty.setName("serverType");
		simpleProperty.setValue(SERVER_TYPE_HTTP_COMPONENTS);
		extendedConfiguration.getProperties().add(simpleProperty);
		
		newDriverConfig.setExtendedConfiguration(extendedConfiguration);
		
	}
	
	public static void populateEntity(
            String projectName,
            String filename,
            String folder,
            String namespace,
            Entity entity)
    {
		entity.setName(filename);
		entity.setDescription("");
		entity.setFolder(folder);
		entity.setNamespace(namespace);
		entity.setGUID(GUIDGenerator.getGUID());
		entity.setOwnerProjectName(projectName);
	}
	
	public static RuleSet createRuleSet(){
		RuleSet entity = RuleFactory.eINSTANCE.createRuleSet();

		return entity;
	}
	
	public static Rule createRule(String projectName, String filename, String folder, String namespace){
		Rule rule = RuleFactory.eINSTANCE.createRule();

		populateEntity(projectName, filename, folder , namespace,  rule);
		rule.setPriority(5);
		rule.setForwardChain(true); // default is true
		rule.setConditionText("");
		rule.setActionText("");
		
		return rule;
	}
	
	public static RuleFunction createRuleFunction(String projectName, String filename, String folder, String namespace){
		RuleFunction ruleFunction = RuleFactory.eINSTANCE.createRuleFunction();

		populateEntity(projectName, filename, folder ,namespace ,ruleFunction);
		ruleFunction.setConditionText("");
		ruleFunction.setActionText("");

		return ruleFunction;
	}
	
	public static SimpleEvent createSimpleEvent(String projectName, String filename, String folder, String namespace){
		SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();

		populateEntity(projectName, filename, folder ,namespace , event);
		
		event.setSuperEventPath("");
		event.setDestinationName(filename);
		event.setTtl("0");
		event.setTtlUnits(TIMEOUT_UNITS.SECONDS);
		event.setType(EVENT_TYPE.SIMPLE_EVENT);
		
		return event;
	}

	
//	public static void persistEntities(
//			Collection<Entity> entities, File projectPath, IProgressMonitor monitor) {
//		String baseURI = projectPath.getPath();
//		Map<?, ?> options = new HashMap();
//		for (Entity entity : entities) {
//			if (entity instanceof RuleSet) {
//				continue;
//			}
//			monitor.subTask("persisting "+entity.getName());
//			String folder = entity.getFolder();
//			String extension = IndexUtils.getFileExtension(entity);
//			URI uri = URI.createFileURI(baseURI+folder+entity.getName()+"."+extension);
//			if (entity instanceof Rule) {
//				try {
//					ModelUtils.persistRule((Rule)entity, uri);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				continue;
//			}
//
//			if (entity instanceof RuleFunction) {
//				try {
//					ModelUtils.persistRuleFunction((RuleFunction)entity, uri);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				continue;
//			}
//			
//			// using XMI
//			ResourceSet resourceSet = new ResourceSetImpl();
//			if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
//				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
//					.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
//			}
//			Resource resource = resourceSet.createResource(uri);
//			if (resource == null)
//				continue;
//
//			resource.getContents().add(entity);
//			try {
//				resource.save(options);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			monitor.worked(1);
//		}
//		
//	}
	
	/**
	 * 
	 */
	private static String removeInvalidChars(String replaceString) {
		if (replaceString.contains(".")) {
			replaceString = replaceString.replace(".", "_");
		}
		if (replaceString.contains("$")) {
			replaceString = replaceString.replace("$", "_");
		}
		if (replaceString.contains("~")) {
			replaceString = replaceString.replace("~", "_");
		}
		if (replaceString.contains("^")) {
			replaceString = replaceString.replace("^", "_");
		}
		return replaceString;
	}
	
	/**
	 * 
	 */
	private static void checkValidCharacters(Entity entity) {
		// validate/recreate the entity
		// name,folder,namespace,return type,

		// name
		String entity_name = entity.getName();
		entity_name = removeInvalidChars(entity_name);
		entity.setName(entity_name);

		// folder
		String entity_folder = entity.getFolder();
		entity_folder = removeInvalidChars(entity_folder);
		entity.setFolder(entity_folder);

		// namespace
		String entity_namespace = entity.getNamespace();
		entity_namespace = removeInvalidChars(entity_namespace);
		entity.setNamespace(entity_namespace);

		// return type
		if (entity instanceof RuleFunction) {
			// return type
			String entity_returntype = ((RuleFunction) entity).getReturnType();
			if(entity_returntype != null) {
				entity_returntype = removeInvalidChars(entity_returntype);
			}
			((RuleFunction) entity).setReturnType(entity_returntype);
			// ConditionText
			String entity_conditionText = ((RuleFunction) entity)
					.getConditionText();
			if(entity_conditionText != null) {
				entity_conditionText = removeInvalidChars(entity_conditionText);
			}
			((RuleFunction) entity).setConditionText(entity_conditionText);
			// Symbol
			for (Symbol symbol : ((RuleFunction) entity).getSymbols()
					.getSymbolList()) {
				String symbol_type = symbol.getType();
				if(symbol_type != null) {
					symbol_type = removeInvalidChars(symbol_type);
				}
				symbol.setType(symbol_type);
			}
		}

		if (entity instanceof Rule) {
			for (Symbol symbol : ((Rule) entity).getSymbols().getSymbolList()) {
				String symbol_type = symbol.getType();
				if(symbol_type != null) {
					symbol_type = removeInvalidChars(symbol_type);
				}
				symbol.setType(symbol_type);
			}
		}

		if (entity instanceof Channel) {

			List<Destination> destinationList = ((Channel) entity).getDriver()
					.getDestinations();
			for (Destination destination : destinationList) {
				String entity_eventURI = destination.getEventURI();
				if(entity_eventURI != null) {
					entity_eventURI = removeInvalidChars(entity_eventURI);
					destination.setEventURI(entity_eventURI);
				}
			}
		}

		if (entity instanceof SimpleEvent) {
			String entity_ChannelURI = ((SimpleEvent) entity).getChannelURI();
			 if(entity_ChannelURI != null) { //abstract wsdl case #BE-10394
		                 entity_ChannelURI = removeInvalidChars(entity_ChannelURI);
                		 ((SimpleEvent) entity).setChannelURI(entity_ChannelURI);
			 }

			String entity_destinationName = ((SimpleEvent) entity)
					.getDestinationName();
			if(entity_destinationName != null) {
				entity_destinationName = removeInvalidChars(entity_destinationName);
			}
			((SimpleEvent) entity).setDestinationName(entity_destinationName);
		}
	}
	
	
	
	public static void persistEntities (
	        Set<Entity> entities,
			Set<Entity> overwritableEntities,
			IProject prj,
			IProgressMonitor monitor)
			throws Exception
	{
		String baseURI = prj.getLocation().toString();
		String projectName = prj.getName();
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		for (Entity entity : entities) {
			Entity existingEntity = CommonIndexUtils.getEntity(projectName, entity.getFullPath());
			if (!((null == existingEntity) || overwritableEntities.contains(entity))) {
				throw new Exception("Can not import. \nEntity \""+existingEntity.getFullPath()+"\" already exist");
			}
			if (entity instanceof RuleSet) {
				continue;
			}
			//validate/recreate the entity name,namespace,return type, folder
			checkValidCharacters(entity);
			monitor.subTask("persisting "+entity.getName());
			String folder = entity.getFolder();
			String extension = IndexUtils.getFileExtension(entity);
			URI uri = URI.createFileURI(baseURI+folder+entity.getName()+"."+extension);
			if (entity instanceof Rule) {
				try {
					ModelUtilsCore.persistRule((Rule)entity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}

			if (entity instanceof RuleFunction) {
				try {
					ModelUtilsCore.persistRuleFunction((RuleFunction)entity, uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
			
			// using XMI
			ResourceSet resourceSet = new ResourceSetImpl();
			if (resourceSet.getResourceFactoryRegistry().getFactory(uri) == null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
					.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			}
			Resource resource = resourceSet.createResource(uri);
			if (resource == null)
				continue;

			resource.getContents().add(entity);
			try {
				resource.save(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
			monitor.worked(1);
		}
		
	}
	
	
	public static void persistSchema(){
		
	}
	

	public static String getSOAPEndPointURL(
			WsPort port)
	{
		WsExtensionElement element = port.getExtensionElement(SOAP11_ADDRESS_XNAME);
		if (null == element) {
			element = port.getExtensionElement(SOAP12_ADDRESS_XNAME);
		}
		return (null == element) ? "" : ((WsSoapAddress) element).getLocation();
	}
	
	public static String replaceParentheses(
			String s)
	{
		return s.replace("[\\(\\)]", "_");
	}

	public static String makeValidName(String name) {
		return replaceParentheses(
				BEStringUtilities.convertToValidTibcoIdentifier(name, true));
	}
	
}
