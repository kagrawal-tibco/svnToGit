package com.tibco.cep.studio.wizard.as.internal.services.api;

import static com.tibco.as.space.Member.DistributionRole.LEECH;
import static com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD.PROPERTIES;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_AS;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_DISCOVERYURL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_ENABLE_SECURITY;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_ID_PASSWORD;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_LISTENURL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_REMOTELISTENURL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_METASPACENAME;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_POLICY_FILE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_SECURITY_ROLE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_TOKEN_FILE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_ROLE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_FILTER;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_SPACE_NAME;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_TIME_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_EXPIRE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_PUT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_TAKE_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._BEAN_PROP_SETTERS_DESTINATION;
import static com.tibco.cep.studio.wizard.as.ASConstants._BEAN_PROP_SETTERS_SIMPLE_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._CHANNEL_PROPERTY_NAMES;
import static com.tibco.cep.studio.wizard.as.ASConstants._CLASSNAME_AS_DESTINATION_SERIALIZER;
import static com.tibco.cep.studio.wizard.as.ASConstants._EVENT_FOLDER;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getSimpleEventManagementFieldProperties;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.populateDestinationName;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.populateSimpleEventName;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.subMonitorFor;
import static org.eclipse.core.runtime.Status.CANCEL_STATUS;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.ElementFactoryImpl;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.driver.as.ASConstants;
import com.tibco.cep.driver.as.ASConstants.ConsumptionMode;
import com.tibco.cep.driver.as.internal.ASAuthenticationCallback;
import com.tibco.cep.sharedresource.ascon.ASConnectionModelMgr;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.wizard.as.commons.utils.StringUtils;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.services.api.IASService;
import com.tibco.cep.studio.wizard.as.services.exception.InvalidSharedResourceException;
import com.tibco.cep.studio.wizard.as.services.spi.IStageParticipant;

public class ASService implements IASService {

	@Override
	public List<Channel> getASChannels(IProject project) {
		String projectName = project.getName();
		List<Channel> allChannels = CommonIndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
		List<Channel> asChannels = new ArrayList<Channel>();
		for (Channel channel : allChannels) {
			DRIVER_TYPE driverType = channel.getDriver().getDriverType();
			if (DRIVER_AS.equals(driverType.getName())) {
				asChannels.add(channel);
			}
		}
		return Collections.unmodifiableList(asChannels);
	}

	@Override
	public SimpleEvent createSimpleEvent(IProject project, Channel channel, SpaceDef spaceDef) {
		SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();

		Map<String, Object> eventProps = createEventProperties(project, channel, spaceDef);
		populateEntityProperties(event, eventProps, _BEAN_PROP_SETTERS_SIMPLE_EVENT);

		// add PropertyDefinitions
		Collection<PropertyDefinition> propDefs = createEventFieldProperties(spaceDef, eventProps);
		event.getProperties().addAll(propDefs);

		return event;
	}

	private Map<String, Object> createEventProperties(IProject project, Channel channel, SpaceDef spaceDef) {
		Map<String, Object> props = new HashMap<String, Object>();

		// event name
		String spaceName = spaceDef.getName();
		String name = populateSimpleEventName(spaceName);
		props.put("name", name); //$NON-NLS-1$
		// superEventPath
		String superEventPath = ""; //$NON-NLS-1$
		props.put("superEventPath", superEventPath); //$NON-NLS-1$
		// channelURI
		String channelURI = channel.getFullPath();
		props.put("channelURI", channelURI); //$NON-NLS-1$
		// folder name
		String folder = _EVENT_FOLDER;
		props.put("folder", folder); //$NON-NLS-1$
		// name space
		String namespace = folder;
		props.put("namespace", namespace); //$NON-NLS-1$
		// destinationName
		String destinationName = populateDestinationName(spaceName);
		props.put("destinationName", destinationName); //$NON-NLS-1$
		// ttl
		int ttl = 0;
		props.put("ttl", ttl); //$NON-NLS-1$
		// ttlUnits
		TIMEOUT_UNITS ttlUnits = TIMEOUT_UNITS.SECONDS;
		props.put("ttlUnits", ttlUnits); //$NON-NLS-1$
		// eventType
		EVENT_TYPE eventType = EVENT_TYPE.SIMPLE_EVENT;
		props.put("type", eventType); //$NON-NLS-1$

		// common props
		Map<String, Object> entityProps = createCommonProperties(project, channel, spaceDef);
		props.putAll(entityProps);

		return props;
	}

	private Collection<PropertyDefinition> createEventFieldProperties(SpaceDef spaceDef, Map<String, Object> eventProps) {
		Collection<PropertyDefinition> propDefs = new ArrayList<PropertyDefinition>();

        String name = (String) eventProps.get("name"); //$NON-NLS-1$
        String folder = (String) eventProps.get("folder"); //$NON-NLS-1$

        String ownerProjectName = (String) eventProps.get("ownerProjectName"); //$NON-NLS-1$
        String ownerPath = folder + name;

		Collection<FieldDef> fieldDefs = spaceDef.getFieldDefs();
		for (FieldDef fieldDef : fieldDefs) {
			FieldType fieldType = fieldDef.getType();
			PROPERTY_TYPES propType = convertToEventType(fieldType);
			if (null != propType) {
				PropertyDefinition propDef = ElementFactoryImpl.eINSTANCE.createPropertyDefinition();
				String propName = fieldDef.getName();
				propDef.setName(propName);
				propDef.setType(propType);
				propDef.setOwnerProjectName(ownerProjectName);
				propDef.setOwnerPath(ownerPath);
				propDefs.add(propDef);
			}
		}

		// add 3 management properties: consumption mode, browser type and event type
		List<Object[]> manageFieldProps = getSimpleEventManagementFieldProperties();
		for(Object[] manageFieldProp : manageFieldProps) {
			PropertyDefinition propDef = ElementFactoryImpl.eINSTANCE.createPropertyDefinition();
			String propName = (String) manageFieldProp[0];
			PROPERTY_TYPES propType = (PROPERTY_TYPES) manageFieldProp[1];
			propDef.setName(propName);
			propDef.setType(propType);
			propDef.setOwnerProjectName(ownerProjectName);
			propDef.setOwnerPath(ownerPath);
			propDefs.add(propDef);
		}

		return propDefs;
	}

	private PROPERTY_TYPES convertToEventType(FieldType fieldType) {
		PROPERTY_TYPES propType = null;
		propType = PROPERTY_TYPES.get(fieldType.toString());
		if (null == propType) {
			if (fieldType == FieldType.DATETIME) {
				propType = PROPERTY_TYPES.DATE_TIME;
			} else if (fieldType == FieldType.INTEGER) {
				propType = PROPERTY_TYPES.INTEGER;
			} else if (fieldType == FieldType.SHORT) {
				propType = PROPERTY_TYPES.INTEGER;
			} else if (fieldType == FieldType.CHAR) {
				propType = PROPERTY_TYPES.STRING;
			} else if (fieldType == FieldType.FLOAT) {
				propType = PROPERTY_TYPES.DOUBLE;
			}
		}
		return propType;
	}

	@Override
	public Destination createDestination(IProject project, Channel channel, SpaceDef spaceDef) {
		Destination destination = ChannelFactory.eINSTANCE.createDestination();

		Map<String, Object> destinationProps = createDestinationProperties(project, channel, spaceDef);
		populateEntityProperties(destination, destinationProps, _BEAN_PROP_SETTERS_DESTINATION);

		return destination;
	}

	private Map<String, Object> createDestinationProperties(IProject project, Channel channel, SpaceDef spaceDef) {
		Map<String, Object> props = new HashMap<String, Object>();

		// name
		String spaceName = spaceDef.getName();
		String name = populateDestinationName(spaceName);
		props.put("name", name); //$NON-NLS-1$
		// eventURI
		String eventName = populateSimpleEventName(spaceName);
		String folder = _EVENT_FOLDER;
		String eventURI = folder + eventName;
		props.put("eventURI", eventURI); //$NON-NLS-1$
		// driverConfig
		DriverConfig driverConfig = channel.getDriver();
		props.put("driverConfig", driverConfig); //$NON-NLS-1$
		// serializerDeserializerClass
		String serializerDeserializerClass = _CLASSNAME_AS_DESTINATION_SERIALIZER;
		props.put("serializerDeserializerClass", serializerDeserializerClass); //$NON-NLS-1$

		// AS destination properties
		Map<String, String> additionalProps = new HashMap<String, String>();
		// space name
		additionalProps.put(K_AS_DEST_PROP_SPACE_NAME, spaceName);
		// distributionRole
		String distributionRole = DistributionRole.LEECH.toString();
		additionalProps.put(K_AS_DEST_PROP_DISTRIBUTION_ROLE, distributionRole);
		
		String distributionScope = DistributionScope.ALL.toString();
		additionalProps.put(K_AS_DEST_PROP_DISTRIBUTION_SCOPE, distributionScope);
		
		String timescope = "ALL";
		additionalProps.put(K_AS_DEST_PROP_TIME_SCOPE, timescope);
		// filter
		String filter = ""; //$NON-NLS-1$
		additionalProps.put(K_AS_DEST_PROP_FILTER, filter);
		// consumptionMode
		String comsumptionMode = ConsumptionMode.EventListener.toString();
		additionalProps.put(K_AS_DEST_PROP_CONSUMPTION_MODE, comsumptionMode);
		// browserType
		String browserType = BrowserType.GET.toString();
		additionalProps.put(K_AS_DEST_PROP_BROWSER_TYPE, browserType);
		// putEvent
		String putEvent = Boolean.TRUE.toString();
		additionalProps.put(K_V_AS_DEST_PROP_PUT_EVENT, putEvent);
		// takeEvent
		String takeEvent = Boolean.FALSE.toString();
		additionalProps.put(K_V_AS_DEST_PROP_TAKE_EVENT, takeEvent);
		// expireEvent
		String expireEvent = Boolean.FALSE.toString();
		additionalProps.put(K_V_AS_DEST_PROP_EXPIRE_EVENT, expireEvent);
		// properties
		PropertyMap additionalPropMap = createDestinationAdditionalProperties(additionalProps);
		props.put("properties", additionalPropMap); //$NON-NLS-1$

		// common props
		Map<String, Object> entityProps = createCommonProperties(project, channel, spaceDef);
		props.putAll(entityProps);

		return props;
	}

	private PropertyMap createDestinationAdditionalProperties(Map<String, String> additionalProps) {
		PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		Set<Entry<String, String>> additionalPropEntries = additionalProps.entrySet();
		for (Entry<String, String> additionalPropEntry : additionalPropEntries) {
			String additionalPropName = additionalPropEntry.getKey();
			String additionalPropValue = additionalPropEntry.getValue();
			SimpleProperty sp = ModelFactory.eINSTANCE.createSimpleProperty();
			sp.setName(additionalPropName);
			sp.setValue(additionalPropValue);
			propertyMap.getProperties().add(sp);
		}
		return propertyMap;
	}

	private Map<String, Object> createCommonProperties(IProject project, Channel channel, SpaceDef spaceDef) {
		Map<String, Object> props = new HashMap<String, Object>();

		// description
		String description = ""; //$NON-NLS-1$
		props.put("description", description); //$NON-NLS-1$
		// guid
		String guid = GUIDGenerator.getGUID();
		props.put("gUID", guid); //$NON-NLS-1$
		// ownerProjectName
		String ownerProjectName = project.getName();
		props.put("ownerProjectName", ownerProjectName); //$NON-NLS-1$

		return props;
	}

	private void populateEntityProperties(Entity entity, Map<String, Object> entityProps, Map<String, Method> entityPropSetters) {
		Set<Entry<String, Object>> propEnstries = entityProps.entrySet();
		for (Entry<String, Object> propEntry : propEnstries) {
			String propName = propEntry.getKey();
			Object propValue = propEntry.getValue();
			Method propSetter = entityPropSetters.get(propName);
			if (null != propSetter) {
				try {
					propSetter.invoke(entity, propValue);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<SpaceDef> getUserSpaceDefs(IProject project, Channel channel) throws Exception {
		List<SpaceDef> spaceDefs = new ArrayList<SpaceDef>();

		DriverConfig driverConfig = channel.getDriver();

		Map<String, String> props = new HashMap<String, String>();
		// if channel use properties configuration
		if (driverConfig.getConfigMethod() == PROPERTIES) {
			EList<Entity> entities = driverConfig.getProperties().getProperties();
			for (Entity entity : entities) {
				if (entity instanceof SimpleProperty) {
					SimpleProperty sp = (SimpleProperty) entity;
					for (String channelPropName : _CHANNEL_PROPERTY_NAMES) {
						if (channelPropName.equals(sp.getName())) {
							props.put(channelPropName, sp.getValue());
							break;
						}
					}
				}
			}
		}
		// if channel use shared resources
		else {
			String reference = driverConfig.getReference();
			if (null != reference) {
				IResource resource = project.getFile(reference);
				if (resource != null) {
					ASConnectionModelMgr modelMgr = new ASConnectionModelMgr(resource);
					modelMgr.parseModel();
					Map<String, Object> modelProps = modelMgr.getModel().values;
					for (String channelPropName : _CHANNEL_PROPERTY_NAMES) {
						props.put(channelPropName, (String) modelProps.get(channelPropName));
					}
				}
				else {
					throw new InvalidSharedResourceException(Messages.getString("ASService.shared_resource_not_exist", reference)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			else {
				throw new InvalidSharedResourceException(Messages.getString("ASService.no_shared_resource", channel.getFullPath())); //$NON-NLS-1$
			}
		}
		String metaspaceName = props.get(CHANNEL_PROPERTY_METASPACENAME.localName);
		String discoveryUrl = props.get(CHANNEL_PROPERTY_DISCOVERYURL.localName);
		String listenUrl = props.get(CHANNEL_PROPERTY_LISTENURL.localName);
		String remoteListenUrl = props.get(CHANNEL_PROPERTY_REMOTELISTENURL.localName);
		boolean enableSecurity = Boolean.valueOf(props.get(CHANNEL_PROPERTY_ENABLE_SECURITY.localName));

		List<Space> connectedSpaces = new ArrayList<Space>();
		Metaspace metaspace = null;
		try {
			MemberDef md = MemberDef.create();
			if(enableSecurity){
			    String idPassword = props.get(CHANNEL_PROPERTY_ID_PASSWORD.localName);
			    md.setIdentityPassword(StringUtils.isNotEmpty(idPassword) ? idPassword.toCharArray() : null);
        		String role = props.get(CHANNEL_PROPERTY_SECURITY_ROLE.localName);
        		if (role.equals(ASConstants.AS_SECURITY_ROLE_CONTROLLER)) {
        			md.setSecurityPolicyFile(props.get(CHANNEL_PROPERTY_POLICY_FILE.localName))
        				.setListen(listenUrl)
        				.setRemoteListen(remoteListenUrl);
        		} else {
        			md.setSecurityTokenFile(props.get(CHANNEL_PROPERTY_TOKEN_FILE.localName))
        				.setAuthenticationCallback(new ASAuthenticationCallback(props));
        		}
        	} else {
        	    md.setListen(listenUrl).setRemoteListen(remoteListenUrl);
        	}
			md.setDiscovery(discoveryUrl);
			metaspace = Metaspace.connect(metaspaceName, md);
			Collection<String> spaceNames = metaspace.getUserSpaceNames();
			for (String spaceName : spaceNames) {
				Space space = metaspace.getSpace(spaceName, LEECH);
				spaceDefs.add(space.getSpaceDef());
				connectedSpaces.add(space);
			}
		} catch (ASException asEx) {
			asEx.printStackTrace();
			throw asEx;
		}
		finally {
			for (Space space : connectedSpaces) {
				try {
					space.closeAll();
				}
				catch (ASException asEx) {
					// do nothing
				}
			}
			if (null != metaspace) {
				try {
					metaspace.closeAll();
				}
				catch (ASException asEx) {
					// do nothing
				}
			}
		}

		return spaceDefs;
	}

	@Override
	public void persist(Channel channel, SimpleEvent simpleEvent, IProject project, IStageParticipant stageParticipant, IProgressMonitor monitor)
	        throws Exception {
		List<Entity> entities = Arrays.asList(channel, simpleEvent);
		persistEntities(entities, project, stageParticipant, monitor);
	}

	private static void persistEntities(List<Entity> entities, IProject project, IStageParticipant stageParticipant, IProgressMonitor monitor)
	        throws Exception {
		monitor.beginTask(Messages.getString("ASService.prepare_for_persistence"), 100); //$NON-NLS-1$
		String baseURI = project.getLocation().toString();
		Map<?, ?> options = ModelUtils.getPersistenceOptions();
		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		monitor.worked(10);


		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		IProgressMonitor subMonitor = subMonitorFor(monitor, 80);
		int totalWork = 40 *  entities.size();
		subMonitor.beginTask(Messages.getString("ASService.start_to_persist"), totalWork); //$NON-NLS-1$
		for (Entity entity : entities) {
			String folder = entity.getFolder();
			String extension = IndexUtils.getFileExtension(entity);
			URI uri = URI.createFileURI(baseURI + "/" + folder + "/" + entity.getName() + "." + extension); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			subMonitor.worked(10);

			if (monitor.isCanceled() || subMonitor.isCanceled()) {
				stageParticipant.setStatus(CANCEL_STATUS);
				return;
			}
			// using XMI
			ResourceSet resourceSet = new ResourceSetImpl();
			if (null == resourceSet.getResourceFactoryRegistry().getFactory(uri)) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				        .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			}
			Resource resource = resourceSet.createResource(uri);
			if (null != resource) {
				resource.getContents().add(entity);
				resource.save(options);
			}
			monitor.worked(30);
		}
		subMonitor.done();
		monitor.done();
	}

}
