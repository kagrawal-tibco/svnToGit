package com.tibco.cep.ws.util.wsdlimport.channel;

import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.JndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Row;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedJmsConRoot;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconFactory;
import com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage;
import com.tibco.be.util.config.sharedresources.sharedjmscon.impl.SharedjmsconPackageImpl;
import com.tibco.be.util.config.sharedresources.util.SharedResourcesHelper;
import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.driver.jms.serializer.SoapMessageSerializer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.ws.util.WSDLImportUtil;
import com.tibco.cep.ws.util.wsdlimport.DefaultWsdlImport;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapPortContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsExtensionPoint;


public class WsdlJmsChannelBuilder
        extends AbstractWsdlChannelBuilder
{


    private static final String SOAP_JMS_BASE_EVENT_FOLDER_PATH = "/Events/";
    private static final String SOAP_JMS_BASE_EVENT_NAME = "SoapJms";
    private static final String SOAP_JMS_BASE_EVENT_PATH = SOAP_JMS_BASE_EVENT_FOLDER_PATH
            + SOAP_JMS_BASE_EVENT_NAME
            + StudioResourceUtils.getExtensionFor(StudioWorkbenchConstants._WIZARD_TYPE_NAME_EVENT);
    private static final String SOAP_JMS_BASE_EVENT_PAYLOAD;
    private static final Pattern JMS_ADDRESS_URI_PATTERN = Pattern.compile("^jms:jndi:([^#\\?]*).*$");
    private static final int JMS_ADDRESS_URI_PATTERN_JNDI_DESTINATION_NAME_GROUP = 1;


    static {
        try {
            SOAP_JMS_BASE_EVENT_PAYLOAD =
                    XiSerializer.serialize(new SOAPEventPayloadBuilder().getPayloadElement());
        } catch (Exception shouldNotHappen) {
            throw new RuntimeException(shouldNotHappen);
        }
    }


    private final URI baseEventUri;


    public WsdlJmsChannelBuilder(
            IProject project,
            String transportFolderName)
    {
        super(project, transportFolderName);

        this.baseEventUri = this.project.getFile(SOAP_JMS_BASE_EVENT_PATH).getLocationURI();

        SharedjmsconPackageImpl.eINSTANCE.eClass();

        final Resource.Factory.Registry registry = this.sharedResources.getResourceFactoryRegistry();
        registry.getExtensionToFactoryMap().put("*", new GenericXMLResourceFactoryImpl());
    }


    private void addSimpleProperty(
            EList<Entity> properties,
            String name,
            String value)
    {
        final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
        property.setName(name);
        property.setValue(value);
        properties.add(property);
    }


    private void addPropertyDefinition(SimpleEvent event,
            EList<PropertyDefinition> properties,
            String name,
            PROPERTY_TYPES type)
    {
        final PropertyDefinition property = ElementFactory.eINSTANCE.createPropertyDefinition();
        property.setOwnerProjectName(project.getName());
        property.setOwnerPath(event.getFullPath());
        property.setName(name);
        property.setType(type);
        properties.add(property);
    }


    private boolean areJmsConnectionConfigsInterchangeable(
            SharedJmsConRoot root1,
            SharedJmsConRoot root2)
    {
        if (null == root1) {
            return null == root2;
        } else if (null == root2) {
            return false;
        }

        final BwSharedResource bwsr1 = root1.getBWSharedResource();
        final BwSharedResource bwsr2 = root2.getBWSharedResource();
        if (null == bwsr1) {
            return null == bwsr2;
        } else if (null == bwsr2) {
            return false;
        }

        final Config config1 = bwsr1.getConfig();
        final Config config2 = bwsr2.getConfig();
        if (null == config1) {
            return null == config2;
        } else if (null == config2) {
            return false;
        }

        final Object useSharedJndiConfig1 = config1.getUseSharedJndiConfig();
        final Object useSharedJndiConfig2 = config1.getUseSharedJndiConfig();
        if (!(areEquivalent(useSharedJndiConfig1, useSharedJndiConfig2, Boolean.FALSE)
                && areEquivalent(config1.getUseSsl(), config2.getUseSsl(), Boolean.FALSE)
                && areEquivalent(config1.getUseXacf(), config2.getUseXacf(), Boolean.FALSE))
                ) {
            return false;
        }

        final ConnectionAttributes attr1 = config1.getConnectionAttributes();
        final ConnectionAttributes attr2 = config1.getConnectionAttributes();
        final Object autoClientId1 = (null == attr1) ? Boolean.FALSE : attr1.getAutoGenClientID();
        final Object autoClientId2 = (null == attr2) ? Boolean.FALSE : attr2.getAutoGenClientID();
        final Object clientId1 = (null == attr1) ? "" : attr1.getClientId();
        final Object clientId2 = (null == attr2) ? "" : attr2.getClientId();
        final Object user1 = (null == attr1) ? "" : attr1.getUsername();
        final Object user2 = (null == attr2) ? "" : attr2.getUsername();
        if (!(areEquivalent(autoClientId1, autoClientId2, "")
                && areEquivalent(clientId1, clientId2, "")
                && areEquivalent(user1, user2, ""))) {
            return false;
        }

        //TODO config1.getJndiProperties() and config1.getJndiSharedConfiguration()

        final NamingEnvironment namingEnv1 = config1.getNamingEnvironment();
        final NamingEnvironment namingEnv2 = config2.getNamingEnvironment();
        final Object qcf1 = (null == namingEnv1) ? null : namingEnv1.getQueueFactoryName();
        final Object qcf2 = (null == namingEnv2) ? null : namingEnv2.getQueueFactoryName();
        final Object tcf1 = (null == namingEnv1) ? null : namingEnv1.getTopicFactoryName();
        final Object tcf2 = (null == namingEnv2) ? null : namingEnv2.getTopicFactoryName();
        final Object useJndi1 = (null == namingEnv1) ? Boolean.FALSE : namingEnv1.getUseJndi();
        final Object useJndi2 = (null == namingEnv2) ? Boolean.FALSE : namingEnv2.getUseJndi();
        if (!(areEquivalent(useJndi1, useJndi2, "")
                && areEquivalent(qcf1, qcf2, "")
                && areEquivalent(tcf1, tcf2, ""))) {
            return false;
        }

        final Object namingCf1 = (null == namingEnv1) ? null : namingEnv1.getNamingInitialContextFactory();
        final Object namingCf2 = (null == namingEnv2) ? null : namingEnv2.getNamingInitialContextFactory();
        final Object namingUrl1 = (null == namingEnv1) ? null : namingEnv1.getNamingUrl();
        final Object namingUrl2 = (null == namingEnv2) ? null : namingEnv2.getNamingUrl();
        if (!Boolean.TRUE.equals(useJndi1)) {
            final Object namingPrincipal1 = (null == namingEnv1) ? null : namingEnv1.getNamingPrincipal();
            final Object namingPrincipal2 = (null == namingEnv2) ? null : namingEnv2.getNamingPrincipal();
            final Object providerUrl1 = (null == namingEnv1) ? null : namingEnv1.getProviderUrl();
            final Object providerUrl2 = (null == namingEnv2) ? null : namingEnv2.getProviderUrl();
            if (!(areEquivalent(namingCf1, namingCf2, "")
                    && areEquivalent(namingUrl1, namingUrl2, "")
                    && areEquivalent(providerUrl1, providerUrl2, "")
                    && areEquivalent(namingPrincipal1, namingPrincipal2, ""))) {
                return false;
            }
        } else if (!Boolean.TRUE.equals(useSharedJndiConfig1)) {
            if (!(areEquivalent(namingCf1, namingCf2, "")
                    && areEquivalent(namingUrl1, namingUrl2, ""))) {
                return false;
            }
        }

        return true;
    }


    private static boolean areEquivalent(
            Object o1,
            Object o2,
            Object defaultValue)
    {
        if ((null == o1) || ((o1 instanceof String) && ((String) o1).isEmpty())) {
            o1 = defaultValue;
        }
        if ((null == o2)  || ((o2 instanceof String) && ((String) o2).isEmpty())) {
            o2 = defaultValue;
        }
        if (null == o1) {
            return (null == o2);
        } else {
            return o1.equals(o2);
        }
    }


    protected Destination createDestination(
            String name,
            Map<String, String> properties)
    {
        final Destination destination = ChannelFactory.eINSTANCE.createDestination();
        final PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
        final EList<Entity> p = propertyMap.getProperties();
        this.addSimpleProperty(p, "AckMode", properties.get("AckMode"));
        this.addSimpleProperty(p, "DeliveryMode", properties.get("DeliveryMode"));
        this.addSimpleProperty(p, "DurableSuscriberName", properties.get("DurableSuscriberName"));
        this.addSimpleProperty(p, "Name", properties.get("Name"));
        this.addSimpleProperty(p, "Priority", properties.get("Priority"));
        this.addSimpleProperty(p, "Queue", properties.get("Queue"));
        this.addSimpleProperty(p, "Selector", properties.get("Selector"));
        this.addSimpleProperty(p, "TTL", properties.get("TTL"));
        this.addSimpleProperty(p, "SharedSubscriptionName", properties.get("SharedSubscriptionName"));

        destination.setEventURI(this.getOrAddBaseEvent().getFullPath());
        destination.setName(name);
        destination.setSerializerDeserializerClass(SoapMessageSerializer.class.getName());
        destination.setOwnerProjectName(this.project.getName());
        destination.setProperties(propertyMap);

        return destination;
    }


    protected SharedJmsConRoot createJmsConnection(
            BindingContext bindingContext)
    {
        final String jndiConnectionFactoryName = this
                .getJndiConnectionFactoryName(bindingContext);
        final String jndiContextFactory = this.getJndiContextFactory(bindingContext, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
        final Map<String, String> jndiContextParams = this.getJndiContextParameters(bindingContext);
        final String jndiUrl = this.getJndiUrl(bindingContext, "tibjmsnaming://localhost:7222");
        final String qcf = jndiConnectionFactoryName.isEmpty()
                ? "QueueConnectionFactory"
                : jndiConnectionFactoryName;
        final String tcf = jndiConnectionFactoryName.isEmpty()
                ? "TopicConnectionFactory"
                : jndiConnectionFactoryName;

        return this.createJmsConnection(
                qcf,
                tcf,
                jndiContextFactory,
                jndiContextParams,
                jndiUrl);
    }


    private SharedJmsConRoot createJmsConnection(
            String queueConnectionFactory,
            String topicConnectionFactory,
            String jndiContextFactory,
            Map<String, String> jndiContextParams,
            String jndiUrl)
    {
        final SharedjmsconFactory factory = SharedjmsconFactory.eINSTANCE;
        final SharedJmsConRoot root = factory.createSharedJmsConRoot();
        final BwSharedResource bwsr = factory.createBwSharedResource();
        final Config config = factory.createConfig();
        final ConnectionAttributes connectionAttributes = factory.createConnectionAttributes();
        final NamingEnvironment namingEnvironment = factory.createNamingEnvironment();
        final JndiProperties properties = factory.createJndiProperties();
        final EList<Row> rowList = properties.getRow();
        for (Map.Entry<String, String> e : jndiContextParams.entrySet()) {
            final Row row = factory.createRow();
            row.setName(e.getKey());
            row.setType("String");
            row.setValue(e.getValue());
            rowList.add(row);
        }

        root.setBWSharedResource(bwsr);
        bwsr.setConfig(config);
        config.setConnectionAttributes(connectionAttributes);
        config.setNamingEnvironment(namingEnvironment);
        config.setUseSharedJndiConfig(false);
        config.setUseSsl(false);
        namingEnvironment.setNamingInitialContextFactory(jndiContextFactory);
        namingEnvironment.setNamingUrl(jndiUrl);
        namingEnvironment.setQueueFactoryName(queueConnectionFactory);
        namingEnvironment.setTopicFactoryName(topicConnectionFactory);
        namingEnvironment.setUseJndi(true);

        return root;
    }


    @Override
    protected String getDefaultChannelFolder()
    {
        return "/Channels";
    }


    @Override
    protected String getDefaultChannelName()
    {
        return "SoapJms";
    }


    @Override
    protected String getDriverTypeName()
    {
        return DriverManagerConstants.DRIVER_JMS;
    }


    private String getFromUri(
            BindingContext bindingContext,
            String paramName)
    {
        final PortContext portContext = bindingContext.getPortContext();
        return (portContext instanceof SoapPortContext)
                ? this.getFromUri((SoapPortContext) portContext, paramName)
                : "";
    }


    private String getFromUri(
            SoapPortContext portContext,
            String paramName)
    {
        String fromUri = portContext.getSoapAddressQueryParameters().get(paramName);

        if (null != fromUri) {
            fromUri = fromUri.trim();
            if (!fromUri.isEmpty()) {
                return fromUri;
            }
        }

        return "";
    }


    private String getJmsAckMode(
            BindingContext bindingContext,
            String defaultValue)
    {
        // TODO ?
        return defaultValue;
    }


    private String getJmsDeliveryMode(
            BindingContext bindingContext)
    {
        final String fromUri = this.getFromUri(bindingContext, "deliveryMode");
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.W3C_SOAP_OVER_JMS_DELIVERY_MODE},
                "")
                : fromUri;
    }


    private String getJmsDurableSubscriberName(
            BindingContext bindingContext,
            String defaultValue)
    {
        // TODO ?
        return defaultValue;
    }


    private String getJmsPriority(
            BindingContext bindingContext,
            String defaultValue)
    {
        final String fromUri = this.getFromUri(bindingContext, "priority").trim();
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.W3C_SOAP_OVER_JMS_PRIORITY},
                defaultValue)
                : fromUri;
    }


    private String getJmsQueue(
            BindingContext bindingContext)
    {
        final WsExtensionElement address = bindingContext.getPortContext().getPort()
                .getExtensionElement(DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS);
        if (null != address) {
            final String destination = address.getContent().getAttributeStringValue(
                    DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS_DESTINATION);
            if ("topic".equals(destination)) {
                return "false";
            }
        }

        return "true";
    }


    private String getJmsReplyToName(
            BindingContext bindingContext)
    {
        final String fromUri = this.getFromUri(bindingContext, "replyToName");
        if (!fromUri.isEmpty()) {
            return fromUri;
        }

        return "";
    }


    private String getJmsSelector(
            BindingContext bindingContext,
            String defaultValue)
    {
        // TODO ?
        return defaultValue;
    }


    private String getJmsTargetService(
            BindingContext bindingContext)
    {
        // TODO use
        final String fromUri = this.getFromUri(bindingContext, "targetService");
        if (!fromUri.isEmpty()) {
            return fromUri;
        }

        return "";
    }


    private String getJmsTopicReplyToName(
            BindingContext bindingContext)
    {
        final String fromUri = this.getFromUri(bindingContext, "topicReplyToName");
        if (!fromUri.isEmpty()) {
            return fromUri;
        }

        return "";
    }


    private String getJmsTtl(
            BindingContext bindingContext,
            String defaultValue)
    {
        final String fromUri = this.getFromUri(bindingContext, "timeToLive");
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.W3C_SOAP_OVER_JMS_TIME_TO_LIVE},
                defaultValue)
                : fromUri;
    }


    protected String getDestinationName(
            BindingContext bindingContext,
            String defaultValue)
    {
        final PortContext portContext = bindingContext.getPortContext();
        if (portContext instanceof SoapPortContext) {
            final URI uri = ((SoapPortContext) portContext).getSoapAddressUri();
            if (null != uri) {
                final Matcher matcher = JMS_ADDRESS_URI_PATTERN.matcher(uri.toString());
                if (matcher.matches()) {
                    final String name = matcher.group(JMS_ADDRESS_URI_PATTERN_JNDI_DESTINATION_NAME_GROUP);
                    if ((null != name) && !name.isEmpty()) {
                        return name;
                    }
                }
            }
        }

        return this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS},
                defaultValue);
    }


    private WsExtensionElement getJmsExtension(
            BindingContext bindingContext,
            ExpandedName[] names)
    {
        final PortContext portContext = bindingContext.getPortContext();
        final WsExtensionPoint[] extensionPoints = new WsExtensionPoint[]{
                portContext.getPort(),
                portContext.getServiceContext().getService(),
                bindingContext.getBinding()
        };
        WsExtensionElement ext = null;
        for (WsExtensionPoint p : extensionPoints) {
            ext = this.getJmsExtension(p, names);
            if (null != ext) {
                return ext;
            }
        }
        return null;
    }


    private WsExtensionElement getJmsExtension(
            WsExtensionPoint extensionPoint,
            ExpandedName[] names)
    {
        WsExtensionElement ext = null;
        for (ExpandedName name : names) {
            ext = extensionPoint.getExtensionElement(name);
            if (null != ext) {
                return ext;
            }
        }
        return null;
    }


    private String getJmsExtensionContent(
            BindingContext bindingContext,
            ExpandedName[] names,
            String defaultValue)
    {
        final WsExtensionElement ext = this.getJmsExtension(bindingContext, names);

        if (null != ext) {
            final String result = ext.getContent().getStringValue().trim();
            if (!result.isEmpty()) {
                return result;
            }
        }

        return defaultValue;
    }


    private String getJndiConnectionFactoryName(
            BindingContext bindingContext)
    {
        final String fromUri = this.getFromUri(bindingContext, "jndiConnectionFactoryName");
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{
                        DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_CONNECTION_FACTORY,
                        DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_CONNECTION_FACTORY_NAME
                },
                "")
                : fromUri;
    }


    private String getJndiContextFactory(
            BindingContext bindingContext,
            String defaultValue)
    {
        final String fromUri = this.getFromUri(bindingContext, "jndiInitialContextFactory");
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_INITIAL_CONTEXT_FACTORY},
                defaultValue)
                : fromUri;
    }


    private Map<String, String> getJndiContextParameters(
            BindingContext bindingContext)
    {
        final SoapPortContext portContext = (SoapPortContext) bindingContext.getPortContext();

        final Map<String, String> params = this.getJndiContextParameters(bindingContext.getBinding());
        params.putAll(this.getJndiContextParameters(portContext.getServiceContext().getService()));
        params.putAll(this.getJndiContextParameters(portContext.getPort()));
        for (Entry<String, String> e : portContext.getSoapAddressQueryParameters().entrySet()) {
            final String k = e.getKey();
            if ((null != k) && k.startsWith("jndi-")) {
                params.put(k.substring("jndi-".length()), e.getValue());
            }
        }
        return params;
    }


    private Map<String, String> getJndiContextParameters(
            WsExtensionPoint extensionPoint)
    {
        final Map<String, String> params = new HashMap<String, String>();

        for (@SuppressWarnings("unchecked")
             final Iterator<WsExtensionElement> i = extensionPoint.getExtensionElements(DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER);
             i.hasNext(); ) {
            final WsExtensionElement ext = i.next();
            params.put(
                    ext.getContent().getAttributeStringValue(DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER_NAME),
                    ext.getContent().getAttributeStringValue(DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER_VALUE));
        }

        return params;
    }


    private String getJndiUrl(
            BindingContext bindingContext,
            String defaultValue)
    {
        final String fromUri = this.getFromUri(bindingContext, "jndiURL");
        return fromUri.isEmpty()
                ? this.getJmsExtensionContent(
                bindingContext,
                new ExpandedName[]{DefaultWsdlImport.W3C_SOAP_OVER_JMS_JNDI_URL},
                defaultValue)
                : fromUri;
    }


    protected SimpleEvent getOrAddBaseEvent()
    {
        SimpleEvent event = (SimpleEvent) CommonIndexUtils.loadEObject(
                this.baseEventUri, false);
        if (null == event) {
            event = WSDLImportUtil.createSimpleEvent(this.project.getName(),
                    SOAP_JMS_BASE_EVENT_NAME, SOAP_JMS_BASE_EVENT_FOLDER_PATH,
                    SOAP_JMS_BASE_EVENT_FOLDER_PATH);
            event.setDestinationName("");
            event.setPayloadString(SOAP_JMS_BASE_EVENT_PAYLOAD);
            event.setSuperEventPath(RDFTypes.SOAP_EVENT.getName());

            final EList<PropertyDefinition> props = event.getProperties();
            this.addPropertyDefinition(event, props, "JMSCorrelationID", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "JMSReplyTo", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "SOAPJMS_bindingVersion", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "SOAPJMS_contentType", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "SOAPJMS_isFault", PROPERTY_TYPES.BOOLEAN);
            this.addPropertyDefinition(event, props, "SOAPJMS_requestURI", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "SOAPJMS_soapAction", PROPERTY_TYPES.STRING);
            this.addPropertyDefinition(event, props, "SOAPJMS_targetService", PROPERTY_TYPES.STRING);

            final Resource resource = this.sharedResources.createResource(
                    org.eclipse.emf.common.util.URI.createFileURI(
                            this.project.getLocation() + SOAP_JMS_BASE_EVENT_PATH));
            resource.getContents().add(event);

        } else if (!RDFTypes.SOAP_EVENT.getName().equals(event.getSuperEventPath())) {
            throw new IllegalArgumentException(this.baseEventUri + " already exiot a SOAPEvent.");
        }

        return event;
    }


    @Override
    protected String getOrAddConnectionResource(
            String existingResourcePath,
            BindingContext bindingContext,
            URI endpointUri)
            throws Exception
    {
        final SharedJmsConRoot newConnection = this.createJmsConnection(bindingContext);

        final String projectLocation = this.project.getLocation().toString();
        String path = new StringBuilder(projectLocation).append(existingResourcePath).toString();
        SharedJmsConRoot existingConnection = null;
        try {
            existingConnection = (SharedJmsConRoot) this.sharedResources
                    .getResource(org.eclipse.emf.common.util.URI.createFileURI(path), false)
                    .getContents().get(0);
            if (this.areJmsConnectionConfigsInterchangeable(newConnection, existingConnection)) {
                return existingResourcePath;
            }
        } catch (Exception ignored) {}

        try {
            existingConnection = SharedResourcesHelper.loadSharedJmsConnectionDocRoot(path);
        } catch (Exception ignored) {}
        if (this.areJmsConnectionConfigsInterchangeable(newConnection, existingConnection)) {
            return existingResourcePath;
        }

        existingConnection = null;
        final String baseResourcePath = this.makeSharedResourcePath(bindingContext, "");
        final String extension = "." + SharedjmsconPackage.eNAME;
        StringBuilder projectPath;
        for (int i = 0; ; i++) {
            projectPath = new StringBuilder(baseResourcePath);
            if (i > 0) {
                projectPath.append("_").append(i);
            }
            projectPath.append(extension);
            path = new StringBuilder(projectLocation).append(projectPath).toString();
            try {
                existingConnection = SharedResourcesHelper.loadSharedJmsConnectionDocRoot(path);
            } catch (FileNotFoundException ignored) {}

            if (null == existingConnection) {
                final Resource resource = this.sharedResources.createResource(
                        org.eclipse.emf.common.util.URI.createFileURI(path));
                resource.getContents().add(newConnection);
                return projectPath.toString();

            } else if (this.areJmsConnectionConfigsInterchangeable(newConnection, existingConnection)) {
                return projectPath.toString();
            }
        }
    }


    protected Destination getOrAddDestination(
            DriverConfig driverConfig,
            BindingContext bindingContext)
            throws Exception
    {
        Destination destination = (Destination) bindingContext.getProperties().get("destination");

        if (null == destination) {
            final Map<String, String> destProps = this.makeDestinationPropertyMap(bindingContext);
            final String name = destProps.get("Name");
            final String isQueue = destProps.get("Queue");
            final Set<String> names = new HashSet<String>();
            boolean found = false;
            for (final Destination d : driverConfig.getDestinations()) {
                names.add(d.getName());
                if (!found) {
                    final Map<String, String> existingDestProps = new TreeMap<String, String>();
                    for (final Entity p : d.getProperties().getProperties()) {
                        if (p instanceof SimpleProperty) {
                            existingDestProps.put(p.getName(), ((SimpleProperty) p).getValue());
                        }
                    }
                    if (name.equals(existingDestProps.get("Name"))
                            && isQueue.equals(existingDestProps.get("Queue"))) {
                        if (destProps.equals(existingDestProps)) {
                            found = true;
                            destination = d;
                        } else {
                            final StringBuilder sb = new StringBuilder("Destination conflict @ ")
                                    .append(d.getFullPath()).append(":\n\n");
                            for (Entry<String, String> entry : existingDestProps.entrySet()) {
                                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
                            }
                            sb.append("\n\nvs\n\n");
                            for (Entry<String, String> entry : destProps.entrySet()) {
                                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(" ");
                            }
                            throw new Exception(sb.toString());
                        }
                    }
                }
            }

            if (null == destination) {
                String beName = WSDLImportUtil.makeValidName(name);
                final String prefix = beName + "_";
                int i = 0;
                while (names.contains(beName)) {
                    beName = prefix + (++i);
                }

                destination = this.createDestination(beName, destProps);

                destination.setDriverConfig(driverConfig);
                driverConfig.getDestinations().add(destination);
                this.getModifiedEntities().add(driverConfig.getChannel());
            }

            bindingContext.getProperties().put("destination", destination);
        }

        return destination;
    }


    protected Destination getOrAddReplyToDestination(
            BindingContext bindingContext,
            String queueName)
    {
        final Channel channel = (Channel) bindingContext.getProperties().get("channel");
        final DriverConfig driverConfig = channel.getDriver();

        final Set<String> names = new HashSet<String>();
        for (final Destination destination : driverConfig.getDestinations()) {
            names.add(destination.getName());

            boolean matchesName = false;
            boolean isQueue = false;

            for (final Entity p : destination.getProperties().getProperties()) {
                if (p instanceof SimpleProperty) {
                    if ("Name".equals(p.getName())) {
                        matchesName = queueName.equals(((SimpleProperty) p).getValue());
                        if (isQueue || !matchesName) {
                            break;
                        }
                    }
                    else if ("Queue".equals(p.getName())) {
                        isQueue = Boolean.valueOf(((SimpleProperty) p).getValue());
                        if (matchesName || !isQueue) {
                            break;
                        }
                    }
                }
            }

            if (matchesName && isQueue) {
                return destination;
            }
        }

        String beName = WSDLImportUtil.makeValidName(queueName);
        final String prefix = beName + "_";
        int i = 0;
        while (names.contains(beName)) {
            beName = prefix + (++i);
        }

        final Destination newDestination = this.createDestination(
                beName,
                this.makeDestinationPropertyMap(queueName));
        newDestination.setDriverConfig(driverConfig);
        driverConfig.getDestinations().add(newDestination);
        this.getModifiedEntities().add(channel);

        return newDestination;
    }


    private Map<String, String> makeDestinationPropertyMap(
            String name)
    {
        final Map<String, String> destinationProps = new TreeMap<String, String>();
        destinationProps.put("AckMode", "23");
        destinationProps.put("DeliveryMode", "2");
        destinationProps.put("DurableSuscriberName", "%%Deployment%%:%%EngineName%%:%%SessionName%%:%%ChannelURI%%:%%DestinationName%%");
        destinationProps.put("Name", name);
        destinationProps.put("Priority", "4");
        destinationProps.put("Queue", "true");
        destinationProps.put("Selector", "");
        destinationProps.put("TTL", "0");
        destinationProps.put("SharedSubscriptionName", "");

        return destinationProps;
    }


    private Map<String, String> makeDestinationPropertyMap(
            BindingContext bindingContext)
    {
        String deliveryMode = this.getJmsDeliveryMode(bindingContext);
        if ("NON_PERSISTENT".equals(deliveryMode)) {
            deliveryMode = "1";
        } else if ("RELIABLE".equals(deliveryMode)) {
            deliveryMode = "22";
        } else {
            deliveryMode = "2";
        }

        final Map<String, String> destinationProps = new TreeMap<String, String>();
        destinationProps.put("AckMode", this.getJmsAckMode(bindingContext, "23"));
        destinationProps.put("DeliveryMode", deliveryMode);
        destinationProps.put("DurableSuscriberName",
                this.getJmsDurableSubscriberName(
                        bindingContext,
                        "%%Deployment%%:%%EngineName%%:%%SessionName%%:%%ChannelURI%%:%%DestinationName%%"));
        destinationProps.put("Name", this.getDestinationName(bindingContext, "destinationName"));
        destinationProps.put("Priority", this.getJmsPriority(bindingContext, "4"));
        destinationProps.put("Queue", this.getJmsQueue(bindingContext));
        destinationProps.put("Selector", this.getJmsSelector(bindingContext, ""));
        destinationProps.put("TTL", this.getJmsTtl(bindingContext, "0"));
        destinationProps.put("SharedSubscriptionName", "");

        return destinationProps;
    }


    private RuleFunction makeRequestRuleFunction(
            String opName,
            String soapAction,
            Destination destination,
            String replyToName,
            BindingContext bindingContext,
            SimpleEvent inputEvent,
            SimpleEvent outputEvent)
    {
        final String requestEventPath = inputEvent.getFullPath();
        final String ruleFnFolderPath = bindingContext.getProperties().getProperty("ruleFnFolder");
        final RuleFunction ruleFn = WSDLImportUtil.createRuleFunction(//TODO check if already exists
                this.project.getName(), opName + "_SOAPJMSRequest",
                ruleFnFolderPath + "/", ruleFnFolderPath);

        ruleFn.setActionText("String correlationId = "
                +  ((null == outputEvent) ? "\"" : ("SOAP.newCorrelationId(\"" + outputEvent.getFullPath()))
                + "\");\nString replyTo = \""
                + (((null != outputEvent) && ((null == replyToName) || replyToName.isEmpty()))
                ? "\"; //TODO: Not found in the WSDL! Define this, and setup a Channel destination for it."
                : (StringEscapeUtils.escapeJava(StringEscapeUtils.escapeXml(replyToName)) + "\";"))
                + "\n\n//TODO: Add the request data.\n"
                + requestEventPath.replace('/', '.').substring(1)
                + " request ="
                + "\n\tEvent.createEvent(\"xslt://{{"
                + requestEventPath
                + "}}<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>"
                + "\\n<xsl:stylesheet xmlns:ns2=\\\"www.tibco.com/be/ontology/"
                + requestEventPath
                + "\\\" xmlns:ns1=\\\"http://www.example.com/xsd/books\\\" "
                + "xmlns:xsl=\\\"http://www.w3.org/1999/XSL/Transform\\\" "
                + "xmlns:ns=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" "
                + "xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\" version=\\\"1.0\\\" "
                + "exclude-result-prefixes=\\\"ns2 ns1 xsl ns xsd\\\">"
                + "<xsl:output method=\\\"xml\\\"/><xsl:param name=\\\"correlationId\\\"/>"
                + "<xsl:template match=\\\"/\\\"><createEvent><event>"
                + "<xsl:attribute name=\\\"extId\\\">"
                + "<xsl:value-of select=\\\"$correlationId\\\"/></xsl:attribute>"                
                + "<xsl:attribute name=\\\"soapAction\\\"><xsl:value-of select=\\\"\\'"
                + StringEscapeUtils.escapeJava(StringEscapeUtils.escapeXml(soapAction))
                + "\\'\\\"/></xsl:attribute>"
                + "<JMSCorrelationID><xsl:value-of select=\\\"$correlationId\\\"/></JMSCorrelationID>"
                + "<JMSReplyTo><xsl:value-of select=\\\"$replyTo\\\"/></JMSReplyTo>"
                + "</event></createEvent></xsl:template></xsl:stylesheet>\");"
                + "\n\nLog.log(Log.getLogger(\"SOAP\"), \"info\", \"Request %s for operation: "
                + StringEscapeUtils.escapeJava(StringEscapeUtils.escapeXml(opName))
                + "\", request@id);"
                + "\nEvent.routeTo(request, \"" + destination.getFullPath() + "\", null);"
                + "\nreturn request;");
        ruleFn.setReturnType(requestEventPath);

        this.createdEntities.add(ruleFn);
        return ruleFn;
    }

    @Override
    public Channel processBinding(BindingContext bindingContext)
            throws Exception
    {
        final Channel channel = super.processBinding(bindingContext);

        if (null != channel) {
            this.getOrAddDestination(channel.getDriver(), bindingContext);
        }

        return channel;
    }


    @Override
    public Destination[] processOperation(
            OperationContext opContext,
            String soapAction,
            SimpleEvent inEvent,
            SimpleEvent outEvent,
            String ruleFnURI)
            throws Exception
    {
        final BindingContext bindingContext = opContext.getOperationReferenceContext().getBindingContext();

        final Destination destination = (Destination) bindingContext.getProperties().get("destination");

        String replyToName = this.getJmsReplyToName(bindingContext);
        if (replyToName.isEmpty()) {
            replyToName = this.getJmsTopicReplyToName(bindingContext);
        }
        final Destination replyDestination = replyToName.isEmpty()
                ? null
                : this.getOrAddReplyToDestination(bindingContext, replyToName);

//        this.makeRequestRuleFunction(
//                opContext.getOperation().getLocalName(),
//                soapAction,
//                destination,
//                replyToName,
//                opContext.getOperationReferenceContext().getBindingContext(),
//                inEvent,
//                outEvent);

        return (null == replyDestination)
                ? new Destination[]{destination}
                : new Destination[]{destination, replyDestination};
    }

}
