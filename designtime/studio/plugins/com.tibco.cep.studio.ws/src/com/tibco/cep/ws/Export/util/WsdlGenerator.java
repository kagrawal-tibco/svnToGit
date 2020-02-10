package com.tibco.cep.ws.Export.util;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_HTTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.xerces.util.XML11Char;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Composite;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.config.sharedresources.sharedjmscon.Config;
import com.tibco.be.util.config.sharedresources.sharedjmscon.NamingEnvironment;
import com.tibco.be.util.config.sharedresources.util.SharedResourcesHelper;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.sharedresource.httpconfig.HttpConfigModelMgr;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.RuleCreator;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.editors.clusterconfig.ClusterConfigUtils;
import com.tibco.cep.studio.ws.WsPlugin;
import com.tibco.cep.ws.WsdlNames;
import com.tibco.cep.ws.Export.util.EventRegistrationParser.Entry;
import com.tibco.cep.ws.util.WSDLImportUtil;
import com.tibco.cep.ws.util.wsdlimport.DefaultWsdlImport;
import com.tibco.io.uri.URI;
import com.tibco.xml.data.primitive.DefaultNamespaceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver.NamespaceNotFoundException;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver.PrefixNotFoundException;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableComponent;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.helpers.UeberSchemaNamespace;
import com.tibco.xml.schema.impl.DefaultAttribute;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultMetaForeignAttribute;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.parse.XiNodeParticleTermParser;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.tns.TargetNamespace;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.TnsImport;
import com.tibco.xml.tns.parse.TnsInclude;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.ws.wsdl.WsException;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.build.WsMutableBinding;
import com.tibco.xml.ws.wsdl.build.WsMutableExtensionElement;
import com.tibco.xml.ws.wsdl.build.WsMutableMessage;
import com.tibco.xml.ws.wsdl.build.WsMutableMessagePart;
import com.tibco.xml.ws.wsdl.build.WsMutableOperation;
import com.tibco.xml.ws.wsdl.build.WsMutableOperationMessage;
import com.tibco.xml.ws.wsdl.build.WsMutableOperationMessageReference;
import com.tibco.xml.ws.wsdl.build.WsMutableOperationReference;
import com.tibco.xml.ws.wsdl.build.WsMutablePort;
import com.tibco.xml.ws.wsdl.build.WsMutablePortType;
import com.tibco.xml.ws.wsdl.build.WsMutableService;
import com.tibco.xml.ws.wsdl.build.WsMutableWsdl;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapAddress;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBody;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapFault;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapHeader;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapStyle;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapUse;
import com.tibco.xml.ws.wsdl.helpers.DefaultExtensionsRegistry;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;
import com.tibco.xml.ws.wsdl.impl.Wsdl;

/**
 *
 * @author majha Generate XSD schemas for a BE project.
 *
 */
public class WsdlGenerator {

    private static final Pattern PORT_TYPE_PATTERN = Pattern.compile("/[^/]+/([^/]+)/Events/.*");
    private static final String DEFAULT_HTTP_PORT = "80";
    private static final String DEFAULT_HTTPS_PORT = "443";

    public static final String HEADER = "Header";
    public static final String BODY = "Body";
    public static final String FAULT = "Fault";

    String projectName;
    String generateLocation;

    /**
     * This base namespace will be used so as to avoid namespace clashes with BE
     * namespaces For example if a namespace is provided like
     * www.my-org.com/schemas then the namespace like
     * www.tibco.com/be/ontology/cepts/MyCept.xsd will be converted to
     *
     * www.my-org.com/schemas/cepts/MyCept.xsd
     *
     */
    String baseNamespace = "www.tibco.com/be/ontology";
    private Map<String, XSDLSchema> schemas;
    private Map<String, Event> events;
    private List<Channel> httpChannels;
    private List<Channel> jmsChannels;
    private HashMap<String, List<Compilable>> rules;
    private List<String> eventURIs;
    private Map<String, List<Entry>> destinationNameToRegisteredEntry;
    private DefaultFactory wsdlFactory;
    private Map<String, WsMutableMessage> createdMessages;
    private Map<String, WsMutableMessage> headerMessages;
    private Map<String, WsMutableMessage> faultMessages;
    private Map<String, WsMutableOperation> createdOperations;

    private Map<WsMutableOperation, List<String>> operationToEventUriMap;
    private WsMutablePortType portType;
    private Set<TnsDocument> addedSchemas;
    private Set<Concept> addedConcepts;
    private Set<Event> addedEvents;
    private EMFTnsCache cache;

    private ClusterConfigModelMgr clusterConfigModelMgr;

    private boolean baseConceptSchemaAdded;
    private boolean baseEventSchemaAdded;
    private IProject project;
    private boolean baseSoapEventSchemaAdded;

    public void setBaseNamespace(String baseNamespace) {
        this.baseNamespace = trim(baseNamespace);
    }

    public WsdlGenerator(IProject prj, Composite comp) throws Exception {
        this.project = prj;
        this.projectName = prj.getName();
        this.cache = StudioCorePlugin.getCache(this.project.getName());
    }

    public void generateWsdl(String loc, String name, String cddUri, IProgressMonitor monitor)
            throws Exception {
        if (monitor == null)
            monitor = new NullProgressMonitor();

        this.clusterConfigModelMgr = new ClusterConfigModelMgr(this.project.getFile(cddUri));
        this.clusterConfigModelMgr.parseModel();
        this.parseEventRegistrations();

        File dir = new File(loc);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new Exception("Can't create wsdl folder: " + loc);
            }
        }

        File file = new File(dir, name );

        FileWriter fw = new FileWriter(file);

        wsdlFactory = DefaultFactory.getInstance();

        String wsdlName = "_"+projectName;
        if(wsdlName.contains(":"))
            wsdlName = wsdlName.replaceAll(":", "-");

        final WsMutableWsdl wsdl = this.wsdlFactory.createWsdl(WsdlConstants.WSDL_URI_11, wsdlName);
        wsdl.setTargetNamespace(baseNamespace + "/" + projectName);
        wsdl.ensureNamespaceDeclaration("tns", baseNamespace + "/"
                + projectName);
        wsdl.ensureNamespaceDeclaration("soap",
                "http://schemas.xmlsoap.org/wsdl/soap/");
        wsdl.ensureNamespaceDeclaration("xsd",
                "http://www.w3.org/2001/XMLSchema");

        filterEntitesForWsdl();
        monitor.worked(20);

        addEvents(wsdl);
        monitor.worked(40);

        addRules(wsdl);
        monitor.worked(50);

        addChannelsAndDestinations(wsdl);
        monitor.worked(60);
        // addTypes(instance, wsdl);
        // addMessage(instance, wsdl);

        wsdlFactory.writeFormatted(wsdl, fw);
        fw.close();
        monitor.worked(90);
    }


    private void parseEventRegistrations()
            throws Exception
    {
        this.destinationNameToRegisteredEntry = new HashMap<String, List<EventRegistrationParser.Entry>>();

        final EventRegistrationParser parser = new EventRegistrationParser();
        for (final String fnUri : ClusterConfigUtils.getStartupFunctionUris(this.clusterConfigModelMgr)) {
            final Compilable rf = new RuleCreator(true).createRule((IFile)
                    this.project.findMember(
                            new org.eclipse.core.runtime.Path(
                                    fnUri + "." + CommonIndexUtils.RULEFUNCTION_EXTENSION)));
            for (final EventRegistrationParser.Entry entry : parser.parseBody(rf.getActionText())) {
                List<EventRegistrationParser.Entry> entries = this.destinationNameToRegisteredEntry.get(entry.destination);
                if (null == entries) {
                    entries = new ArrayList<EventRegistrationParser.Entry>();
                    this.destinationNameToRegisteredEntry.put(entry.destination, entries);
                }
                entries.add(entry);
            }
        }
    }


    private void filterEntitesForWsdl() {
        List<Event> allEvents = CommonIndexUtils.getAllEvents(this.project.getName());
        List<DesignerElement> rulelements = CommonIndexUtils.getAllElements(
        		this.project.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_FUNCTION });

        List<Compilable> allRules = new ArrayList<Compilable>();
        for (DesignerElement element : rulelements) {
            allRules.add(((RuleElement) element).getRule());
        }
        List<Channel> channels = CommonIndexUtils.getAllEntities(this.project.getName(),
                new ELEMENT_TYPES[] { ELEMENT_TYPES.CHANNEL });
        filterSoapEvents(allEvents);
        filterSoapEventDependentRule(allRules);
        this.filterChannels(channels);
    }

    private void filterSoapEvents(List<Event> allEvents) {
        events = new HashMap<String, Event>();
        eventURIs = new ArrayList<String>();
        for (Event event : allEvents) {
            if (isSoapEvent(event)) {
                events.put(event.getFullPath(), event);
                eventURIs.add(event.getFullPath());
            }
        }
    }

    private void addEvents(WsMutableWsdl wsdl) throws SAXException,
            IOException, PrefixNotFoundException {
        createdMessages = new HashMap<String, WsMutableMessage>();
        for (String eventStr : eventURIs) {
            Event event = events.get(eventStr);

            EList<NamespaceEntry> namespaceEntries = event
                    .getNamespaceEntries();
            writeNamespaces(wsdl, namespaceEntries);
            EList<ImportRegistryEntry> registryImportEntries = event
                    .getRegistryImportEntries();
            addTypes(wsdl, registryImportEntries);
            String messageName = eventStr.replaceAll("/", "_");
            if(messageName.startsWith("_"))
                messageName = messageName.substring(1);
            WsMutableMessage message = wsdlFactory.createMessage(wsdl, messageName);
            createdMessages.put(eventStr, message);
            String payloadString = event.getPayloadString();
            XiNode payloadPropertyNode = XiParserFactory.newInstance().parse(
                    new InputSource(new StringReader(payloadString)));
            SmElement smElementFromPath = getSmElementFromPath(this.project.getName(),
                    event.getFullPath());
            SmSchema schema = smElementFromPath.getSchema();
            SmParticleTerm term = XiNodeParticleTermParser.getTerm(XiChild
                    .getChild(payloadPropertyNode, ExpandedName
                            .makeName("payload")), (DefaultSchema) schema);
//			MutableType msgT = null;
            MutableType envT = null;
            Iterator<?> headerParts = null;
            Iterator<?> bodyParts = null;
            Iterator<?> faultParts = null;

            SmParticle envP = null;
            SmParticle bodyP = null;
            SmParticle faultP = null;
            MutableElement e = (MutableElement) term;
//			msgT = (MutableType) e.getType();
            envP = getParticleByName(e, "Envelope");
            envT = (MutableType) ((SmElement) envP.getTerm()).getType();

            Iterator<?> rootParts = envT.getContentModel().getParticles();
            while (rootParts.hasNext()) {
                SmParticle p = (SmParticle) rootParts.next();
                if (p.getTerm().getName().equalsIgnoreCase("Header")) {
                    headerParts = getParticleParts(p);
                } else if (p.getTerm().getName().equalsIgnoreCase("Body")) {
                    bodyP = p;
                    bodyParts = getParticleParts(p);
                    faultP = getParticleByName(
                            (MutableElement) bodyP.getTerm(), "Fault");
                    faultParts = getParticleParts(faultP);
                }
            }

            if (bodyParts != null) {
                while (bodyParts.hasNext()) {
                    SmParticle p = (SmParticle) bodyParts.next();
                    if (p.getTerm().getName().equalsIgnoreCase("Fault")) {
                        continue;
                    }
                    SmElement element = (SmElement) p.getTerm();
                    ExpandedName expandedName = element.getExpandedName();
                    WsMutableMessagePart messagePart = wsdlFactory
                            .createMessagePart(message, expandedName
                                    .getLocalName());
                    messagePart.setElement(getElementRef(p, element, wsdl,baseNamespace+event.getFullPath()+"/body", namespaceEntries));
                }
            }
            if (headerParts != null) {
                WsMutableMessage headerMessage = null;
                while (headerParts.hasNext()) {
                    if (headerMessages == null)
                        headerMessages = new HashMap<String, WsMutableMessage>();
                    if (headerMessage == null)
                        headerMessage = wsdlFactory.createMessage(wsdl,
                                "HeaderMsg_" + messageName);

                    SmParticle p = (SmParticle) headerParts.next();
                    SmElement element = (SmElement) p.getTerm();
                    ExpandedName expandedName = element.getExpandedName();

                    WsMutableMessagePart messagePart = wsdlFactory
                            .createMessagePart(headerMessage, expandedName
                                    .getLocalName());
                    messagePart.setElement(getElementRef(p, element, wsdl,baseNamespace+event.getFullPath()+"/header", namespaceEntries ));

                    headerMessages.put(eventStr, headerMessage);
                }
            }

            if (faultParts != null) {
                if (faultParts.hasNext()) {
                    if (faultMessages == null)
                        faultMessages = new HashMap<String, WsMutableMessage>();
                    SmParticle p = (SmParticle) faultParts.next();
                    SmElement element = (SmElement) p.getTerm();
                    ExpandedName expandedName = element.getExpandedName();
                    WsMutableMessage faultMessage = wsdlFactory.createMessage(
                            wsdl, "FaultMsg_" + messageName);
                    WsMutableMessagePart messagePart = wsdlFactory.createMessagePart(faultMessage,
                            expandedName.getLocalName());
                    messagePart.setElement(getElementRef(p, element, wsdl,baseNamespace+event.getFullPath()+"/fault", namespaceEntries ));
                    faultMessages.put(eventStr, faultMessage);
                }
            }
        }
    }
    private ExpandedName getElementRef(SmParticle p ,SmElement element, WsMutableWsdl wsdl,
                                       String nameSpaceUri, List<NamespaceEntry> namespaces) {
        ExpandedName expandedName = element.getExpandedName();
        SmType type = element.getType();
        if (type == null) {// Element referenced from schema
            String namespaceURIForPrefix = null;
            if (expandedName.getNamespaceURI() != null) {
                namespaceURIForPrefix = getNameSpaceUriForPrefix(wsdl, expandedName.getNamespaceURI(), namespaces);
            }
            return ExpandedName.makeName(namespaceURIForPrefix, expandedName
                    .getLocalName());
        } else {// Allow for in place schema elements
            MutableSchema schema = createSchema(nameSpaceUri);
            MutableElement child = addNsToType(schema, null, p, wsdl,namespaces);
            removeIsNullableAttribute(schema);
            Iterator internalSchemas = wsdl.getInternalSchemas();
            while (internalSchemas.hasNext()) {
                Object object = (Object) internalSchemas.next();
                if(object instanceof SmSchema){
                    SmSchema intSchema = (SmSchema) object;
                    if(intSchema.getNamespaceURI().equals(nameSpaceUri)){
                        wsdl.removeInternalSchema(intSchema);
                        break;
                    }
                }

            }
            wsdl.addInternalSchema(schema);
            return child.getExpandedName();
        }
    }

    private String getNameSpaceUriForPrefix(WsMutableWsdl wsdl,
                                            String prefix, List<NamespaceEntry> namespaces) {
        String namespaceURIForPrefix = null;
        if(namespaces != null){
            for (NamespaceEntry namespaceEntry : namespaces) {
                if(namespaceEntry.getPrefix().equalsIgnoreCase(prefix)){
                    namespaceURIForPrefix  = namespaceEntry.getNamespace();
                    break;
                }
            }
        }

        if(namespaceURIForPrefix == null){
            try {
                namespaceURIForPrefix = wsdl.getNamespaceURIForPrefix(prefix);
            } catch (PrefixNotFoundException e) {
                namespaceURIForPrefix = "http://www.w3.org/2001/XMLSchema";
            }
        }

        return namespaceURIForPrefix;
    }

    private MutableElement addNsToType (MutableSchema schema, MutableType onType, SmParticle p, WsMutableWsdl wsdl, List<NamespaceEntry> namespaces) {
        MutableElement mutEle = null;
        int min = p.getMinOccurrence();
        int max = p.getMaxOccurrence();
        SmElement element = (SmElement) p.getTerm();
        SmType type = element.getType();
        if (type == null) {//Element referenced from schema
            ExpandedName en = element.getExpandedName();
            ExpandedName makeName = ExpandedName.makeName(getNameSpaceUriForPrefix(wsdl, en.getNamespaceURI(), namespaces), en.getLocalName());
            MutableComponent c = createComponentRef(schema, makeName, SmComponent.ELEMENT_TYPE);
            MutableSupport.addElement(onType, (MutableElement)c, min, max);
        } else {//Allow for in place schema elements
            MutableType mutableType = null;
            String localName = element.getExpandedName().localName;
            ExpandedName en = type.getExpandedName();
            if(en != null && en.namespaceURI != null && type.getAtomicType() == null){
                // This is a referenced type
                mutableType = MutableSupport.createType(schema, null, type);
                ExpandedName makeName = ExpandedName.makeName(getNameSpaceUriForPrefix(wsdl, en.getNamespaceURI(), namespaces), en.getLocalName() );
                mutableType.setExpandedName(makeName);
                mutableType.setDerivationMethod(SmComponent.EXTENSION);
                mutableType.setComplex();
                if (onType != null)
                    mutEle = MutableSupport.addLocalElement(onType, localName,
                            mutableType, min, max);
                else{
                    mutEle = MutableSupport.createElement(schema, localName, mutableType);
                    TnsImport imprt = new TnsImportImpl(null, null, mutableType.getNamespace(), SmNamespace.class);
                    TnsFragment singleFragment = schema.getSingleFragment();
                    Set<String> importsList = getImportsList(singleFragment);
                    if (!importsList.contains(imprt.getNamespaceURI()))
                        singleFragment.addImport(imprt);;	    			}
            } else {// This is a local type
                boolean isComplex = true;
                SmType pType = element.getType();
                if(pType.getContentModel() == null){
                    isComplex = false;
                }

                if(isComplex){// treat as complexType
                    MutableComponent mutableComponent= null;
                    if(en != null)
                        mutableComponent = createComponentRef(schema, en, SmComponent.TYPE_TYPE);
                    mutableType = MutableSupport.createType(schema, null, (MutableType)mutableComponent);
                } else {// this is a simpleType
                    SmType baseType = element.getType().getBaseType();
                    mutableType = MutableSupport.createType(schema, null, baseType);
                }
                mutableType.setDerivationMethod(SmComponent.EXTENSION);
                mutableType.setComplex();
                if (onType != null)
                    mutEle = MutableSupport.addLocalElement(onType, localName,
                            mutableType, min, max);
                else{
                    mutEle = MutableSupport.createElement(schema, localName, mutableType);
                }

                //Check if it has particles to account for its children
                Iterator<?> childParticles = getParticleParts(p);
                if (childParticles != null) {
                    while (childParticles.hasNext()) {
                        SmParticle child = (SmParticle)childParticles.next();
                        SmParticleTerm pTerm = p.getTerm();
                        if(pTerm instanceof SmElement)
                            addNsToType(schema, mutableType,child,wsdl , namespaces);//Recurse
                    }
                }
            }
        }
        return mutEle;
    }

    private MutableComponent createComponentRef(MutableSchema schema,
                                                ExpandedName en, int type) {
        MutableComponent mutableComponent = MutableSupport.createComponentRef(
                schema, type);
        mutableComponent.setReference(schema);
        mutableComponent.setExpandedName(en);
        return mutableComponent;
    }

    private MutableSchema createSchema(String namespaceURI){
        if(schemas == null)
            schemas = new HashMap<String, XSDLSchema>();
        XSDLSchema schema = schemas.get(namespaceURI);
        if(schema == null){
            schema = (XSDLSchema)DefaultComponentFactory.getTnsAwareInstance().createSchema();
            schema.setNamespace(namespaceURI);
            schema.setFlavor(XSDL.FLAVOR);
            schema.setElementFormQualified(true);
            DefaultNamespaceContext defaultNamespaceContext = new DefaultNamespaceContext();
            defaultNamespaceContext.add("", namespaceURI);
            schema.setNamespaceContext(defaultNamespaceContext);
            schemas.put(namespaceURI, schema);
        }
        return schema;
    }

    private Iterator<?> getParticleParts(SmParticle p) {
        Iterator<?> partsI = null;
        if (p != null) {
            MutableElement element = (MutableElement) p.getTerm();
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
                SmModelGroup smModelGroup = elementType.getContentModel();
                // This will be null for simple types
                if (smModelGroup != null) {
                    Iterator<?> particles = smModelGroup.getParticles();
                    return particles;
                }
            }
        }
        return partsI;
    }

    /*
     * Finds a particles by name in an Element
     */
    private SmParticle getParticleByName(MutableElement p, String termName) {
        if (p != null) {
            MutableElement element = p;
            if (element != null) {
                MutableType elementType = (MutableType) element.getType();
                Iterator<?> partsI = elementType.getContentModel().getParticles();
                while (partsI.hasNext()) {
                    SmParticle particle = (SmParticle) partsI.next();
                    if (particle.getTerm().getName().equalsIgnoreCase(termName)
                            && NoNamespace.URI == particle.getTerm()
                            .getNamespace()) {
                        return particle;
                    }
                }
            }
        }
        return null;

    }

    public static SmElement getSmElementFromPath(String projectName,
                                                 String entity) {
        SmNamespaceProvider snp = StudioCorePlugin.getCache(projectName)
                .getSmNamespaceProvider();
        SmNamespace sm = snp
                .getNamespace(EMFRDFTnsFlavor.BE_NAMESPACE + entity);
        String[] terms = entity.split("/");
        String elname = terms[terms.length - 1];

        if (sm != null) {
            return (SmElement) sm
                    .getComponent(SmComponent.ELEMENT_TYPE, elname);
        } else {
            return null;
        }
    }

    private void addRules(WsMutableWsdl wsdl) {
        Set<String> operations = new HashSet<String>();
        createdOperations = new HashMap<String, WsMutableOperation>();
        operationToEventUriMap = new HashMap<WsMutableOperation, List<String>>();
        portType = wsdlFactory.createPortType(wsdl, projectName + "PortType");
        for (String eventStr : eventURIs) {
            List<Compilable> list = rules.get(eventStr);
            if (list == null)
                continue;
            for (Compilable rule : list) {
                String name = rule.getFullPath().replaceAll("/", "_");
                if(name.startsWith("_")){
                    name = name.substring(1);
                }
                if (!operations.contains(name)) {
                    WsMutableOperation operation = wsdlFactory.createOperation(
                            portType, name);
                    Symbols symbols = rule.getSymbols();
                    EList<Symbol> symbolList = symbols.getSymbolList();
                    String type = null;
                    for (Symbol symbol : symbolList) {
                        String typeTemp = symbol.getType();
                        if (eventURIs.contains(typeTemp)) {
                            WsMutableMessage message = createdMessages.get(typeTemp);
                            WsMutableOperationMessage operationMessage = wsdlFactory
                                    .createOperationMessage(operation, "input",
                                            WsMessageKind.INPUT);
                            operationMessage.setMessage(message.getExpandedName());
                            type = typeTemp;
                            break;
                        }
                    }

                    String returnType = rule.getReturnType();
                    if (eventURIs.contains(returnType)) {
                        WsMutableMessage message = createdMessages
                                .get(returnType);
                        WsMutableOperationMessage operationMessage = wsdlFactory
                                .createOperationMessage(operation, "output",
                                        WsMessageKind.OUTPUT);
                        operationMessage.setMessage(message.getExpandedName());
                        if(faultMessages != null){
                            WsMutableMessage fault = faultMessages.get(returnType);
                            if(fault != null){
                                WsMutableOperationMessage faultMsg = wsdlFactory
                                        .createOperationMessage(operation, "fault",
                                                WsMessageKind.FAULT);
                                faultMsg.setMessage(fault.getExpandedName());
                            }
                        }
                    }
                    operations.add(name);
                    this.createdOperations.put(rule.getFullPath(), operation);
                    List<String> eventUriList = operationToEventUriMap.get(operation);
                    if(eventUriList == null){
                        eventUriList = new ArrayList<String>();
                    }
                    if (type != null)
                        eventUriList.add(type);
                    if(returnType != null)
                        eventUriList.add(returnType);

                    operationToEventUriMap.put(operation, eventUriList);
                }
            }

        }
    }

    private void addChannelsAndDestinations(
            WsMutableWsdl wsdl)
    {
        final Set<Channel> channels = new HashSet<Channel>(this.httpChannels);
        channels.addAll(this.jmsChannels);

        for (final Channel channel: channels) {
            final String channelTypeName = channel.getDriver().getDriverType().getName();

            if (DriverManagerConstants.DRIVER_HTTP.equals(channelTypeName)) {
                this.processHttpChannel(wsdl, channel);
            }
            else if (DriverManagerConstants.DRIVER_JMS.equals(channelTypeName)) {
                this.processJmsChannel(wsdl, channel);
            }
        }
    }


    private void processHttpChannel(
            WsMutableWsdl wsdl,
            Channel channel)
    {
        final DefaultExtensionsRegistry extensionsRegistry =
                new DefaultExtensionsRegistry();

        final String bindingName = channel.getName().contains("Binding")
                ? channel.getName()
                : channel.getName() + "Binding";
        final WsMutableBinding binding = this.addBinding(
                wsdl, extensionsRegistry, bindingName, WsdlConstants.SOAP_11_TRANSPORT_URI);

        for (final Destination destination : channel.getDriver().getDestinations()) {
            this.processDestination(binding, destination, destination.getName(),
                    this.createdOperations.get(this.getPreprocessor(destination)),
                    extensionsRegistry);
        }

        final String sharedResRef = channel.getDriver().getReference();
        if ((null != sharedResRef) && sharedResRef.endsWith("sharedhttp")) {
        	IFile sharedResFile = this.project.getFile(sharedResRef);
       		if(!sharedResFile.exists()){
       			StudioProjectConfiguration projectConfig = StudioProjectConfigurationCache.getInstance().get(this.project.getName());
       			EList<ProjectLibraryEntry> projectLibEntries = projectConfig.getProjectLibEntries();
       			for(ProjectLibraryEntry entry:projectLibEntries) {	
       				String projLibPath=entry.getPath();
       				String projLibName=projLibPath.substring((projLibPath.lastIndexOf(File.separator)+1));
       				sharedResFile= this.project.getFile("/"+projLibName+sharedResRef);
       			}
       		}
             final WsMutableService service = this.wsdlFactory
                    .createService(wsdl, channel.getName() + "Service");
            
            String portName = sharedResFile.getName();
            portName = portName.substring(0, portName.lastIndexOf('.'))
                    .replaceAll("\\s+", "")
                    .replace(':', '-');
            if (!XML11Char.isXML11ValidName(portName)) {
                portName = "_" + portName;
            }
            final WsMutablePort port = this.wsdlFactory.createPort(service, portName);
            port.setBinding(binding.getExpandedName());

            final WsSoapAddress address = (WsSoapAddress)
                    extensionsRegistry.createExtensionElement(
                            WsdlNames.SOAP11_ADDRESS, port);
            address.setLocation(this.getLocation(sharedResFile, channel));
        }
    }


    private void processJmsChannel(
            WsMutableWsdl wsdl,
            Channel channel)
    {
        final WsMutableService service = this.wsdlFactory
                .createService(wsdl, channel.getName() + "Service");

        boolean usedChannel = false;

        for (final Destination destination : channel.getDriver().getDestinations()) {
            final DefaultExtensionsRegistry extensionsRegistry =
                    new DefaultExtensionsRegistry();

            final WsMutableBinding binding =
                    this.processJmsDestination(wsdl, destination, extensionsRegistry);

            if (null != binding) {
                usedChannel = true;

                String portName = WSDLImportUtil.makeValidName(destination.getName());
                if (!XML11Char.isXML11ValidName(portName)) {
                    portName = "_" + portName;
                }
                final WsMutablePort port = this.wsdlFactory.createPort(service, portName);
                port.setBinding(binding.getExpandedName());

                final WsSoapAddress address = (WsSoapAddress) extensionsRegistry
                        .createExtensionElement(WsdlNames.SOAP11_ADDRESS, port);
                address.setLocation("");

                final Map<String, String> destinationProperties =
                        this.getDestinationProperties(destination);
                final Boolean isQueue = Boolean.valueOf(destinationProperties.get("Queue"));

                try {
                    String connFactoryName = this.getConnectionFactoryName(channel, isQueue);
                    if ((null != connFactoryName) && !connFactoryName.isEmpty()) {
                        final WsMutableExtensionElement connectionFactory = extensionsRegistry
                                .createExtensionElement(
                                        DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_CONNECTION_FACTORY,
                                        port);
                        connectionFactory.getContent().appendText(connFactoryName);
                    }
                } catch(Exception ignored) {}

                final WsMutableExtensionElement targetAddress = extensionsRegistry
                        .createExtensionElement(
                                DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS,
                                port);
                final XiNode targetAddressNode = targetAddress.getContent();
                targetAddressNode.appendText(destinationProperties.get("Name"));
                targetAddressNode.setAttributeStringValue(
                        DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS_DESTINATION,
                        (isQueue ? "queue" : "topic"));
            }
        }

        if (!usedChannel) {
            wsdl.removeService(service);
        }
    }


    private String getConnectionFactoryName(
            Channel channel,
            Boolean isQueue)
            throws IOException
    {
        final DriverConfig driverConfig = channel.getDriver();
        if (CONFIG_METHOD.REFERENCE == driverConfig.getConfigMethod()) {
            final Config cfg = SharedResourcesHelper.loadSharedJmsConnectionConfig(
                    this.project.getLocation() + driverConfig.getReference());
            if (null != cfg) {
                final NamingEnvironment namingEnv = cfg.getNamingEnvironment();
                if (null != namingEnv) {
                    return isQueue
                            ? namingEnv.getQueueFactoryName()
                            : namingEnv.getTopicFactoryName();
                }
            }
        }
        return "";
    }


    private Map<String, String> getDestinationProperties(
            Destination destination)
    {
        final Map<String, String> props = new HashMap<String, String>();

        for (final Entity p : destination.getProperties().getProperties()) {
            if (p instanceof SimpleProperty) {
                props.put(p.getName(), ((SimpleProperty) p).getValue());
            }
        }

        return props ;
    }

    private WsMutableBinding processJmsDestination(
            WsMutableWsdl wsdl,
            Destination destination,
            DefaultExtensionsRegistry extensionsRegistry)
    {
        String bindingName = WSDLImportUtil.makeValidName(destination.getFullPath().substring(1));
        if (!bindingName.endsWith("Binding")) {
            bindingName += "Binding";
        }

        final WsMutableBinding binding = this.addBinding(
                wsdl, extensionsRegistry, bindingName,
                DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI);

        boolean usedDestination = false;

        final List<Entry> entries = this.destinationNameToRegisteredEntry.get(destination.getFullPath());
        if (null != entries) {
          for (final Entry e : entries) {
            if ((e.input) && (null != e.portType)) {
                final String k =
                        '/' + WSDLImportUtil.makeValidName(e.service)
                                + '/' + WSDLImportUtil.makeValidName(e.portType)
                                + "/RuleFunctions/" + WSDLImportUtil.makeValidName(e.operation.trim());

                if (this.processDestination(binding, destination, e.soapAction,
                        this.createdOperations.get(k), extensionsRegistry)) {
                    usedDestination = true;

                    final WsMutableExtensionElement jmsBinding = extensionsRegistry
                            .createExtensionElement(
                                    DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_BINDING, binding);
                    jmsBinding.getContent().setAttributeStringValue(
                            DefaultWsdlImport.TIBCO_2004_SOAP_OVER_JMS_MESSAGE_FORMAT,
                            "bytes");
                }
            }
          }
        }

        if (usedDestination) {
            return binding;
        } else {
            wsdl.removeBinding(binding);
            return null;
        }
    }

    private WsMutableBinding addBinding(
            WsMutableWsdl wsdl,
            DefaultExtensionsRegistry extensionsRegistry,
            String bindingName,
            String transport)
    {
        final WsMutableBinding binding = this.wsdlFactory.createBinding(wsdl, bindingName);
        binding.setPortType(this.portType.getExpandedName());

        final WsSoapBinding soapBinding = (WsSoapBinding)
                extensionsRegistry.createExtensionElement(WsdlNames.SOAP11_BINDING, binding);
        soapBinding.setStyle(WsSoapStyle.DOCUMENT);
        soapBinding.setTransport(transport);
        binding.addExtensionElement(soapBinding);

        return binding;
    }


    private boolean processDestination(
            WsMutableBinding binding,
            Destination destination,
            String soapAction,
            WsMutableOperation wsMutableOperation,
            DefaultExtensionsRegistry extensionsRegistry)
    {
        if (null == wsMutableOperation) {
            return false;
        }

        final String eventURI = destination.getEventURI();
        if (!this.eventURIs.contains(eventURI)) {
            return false;
        }

        final WsMutableOperationReference operationReference =
                this.wsdlFactory.createOperationReference(
                        binding, wsMutableOperation.getName().getLocalName());

        final WsSoapOperation soapOperation = (WsSoapOperation)
                extensionsRegistry.createExtensionElement(
                        WsdlNames.SOAP11_ELEMENT, operationReference);
        soapOperation.setSoapAction(soapAction);
        soapOperation.setStyle(WsSoapStyle.DOCUMENT);

        final WsMutableOperationMessageReference inputOperationMessageReference =
                this.wsdlFactory.createOperationMessageReference(
                        operationReference, "", WsMessageKind.INPUT);
        // operationReference.addMessage(inputOperationMessageReference, 0);

        WsSoapBody soapBody = (WsSoapBody)
                extensionsRegistry.createExtensionElement(
                        WsdlNames.SOAP11_BODY, inputOperationMessageReference);
        soapBody.setUse(WsSoapUse.LITERAL);

        if (null != this.headerMessages) {
            this.addHeaderMessage(
                    inputOperationMessageReference,
                    this.headerMessages.get(eventURI));
        }

        final WsOperationMessage[] messages =
                wsMutableOperation.getMessages(WsMessageKind.OUTPUT);
        if (messages.length > 0) {
            final WsMutableOperationMessageReference outputOperationMessageReference =
                    this.wsdlFactory.createOperationMessageReference(
                            operationReference, "", WsMessageKind.OUTPUT);
            // operationReference.addMessage(outputOperationMessageReference, 0);

            soapBody = (WsSoapBody)
                    extensionsRegistry.createExtensionElement(
                            WsdlNames.SOAP11_BODY, outputOperationMessageReference);
            soapBody.setUse(WsSoapUse.LITERAL);

            final List<String> list = this.operationToEventUriMap.get(wsMutableOperation);
            if ((null != list) && (list.size() == 2) && (null != this.headerMessages)) {
                this.addHeaderMessage(
                        outputOperationMessageReference,
                        this.headerMessages.get(list.get(1)));
            }
        }

        final WsOperationMessage[] faultMessages =
                wsMutableOperation.getMessages(WsMessageKind.FAULT);

        if (faultMessages.length > 0) {
            final WsMutableOperationMessageReference faultOperationMessageReference =
                    this.wsdlFactory.createOperationMessageReference(
                            operationReference, WsdlConstants.FAULT_ELEMENT, WsMessageKind.FAULT);
            // operationReference.addMessage(outputOperationMessageReference, 0);

            final WsSoapFault soapFault = (WsSoapFault)
                    extensionsRegistry.createExtensionElement(
                            WsdlNames.SOAP11_FAULT, faultOperationMessageReference);
            soapFault.setNameAttribute(faultMessages[0].getLocalName());
            soapFault.setNamespace(faultMessages[0].getName().getNamespaceURI());
            soapFault.setUse(WsSoapUse.LITERAL);
        }

        return true;
    }


    private String getPreprocessor(
            Destination dest)
    {
        String preProcessor = "";
        Map<String, Map<String, String>> destinationMap = new HashMap<String, Map<String,String>>();
        ClusterConfigUtils.getAgentClassesDestinations(this.clusterConfigModelMgr, destinationMap);
        Collection<Map<String, String>> values = destinationMap.values();
        for (Map<String, String> preProcessorMap : values) {
            final String string = preProcessorMap.get(dest.getFullPath());
//			        channel.getFullPath()+"/"+dest.getName());
            if(string != null){
                preProcessor = string;
                break;
            }
        }
        return preProcessor;
    }

    private void addHeaderMessage(WsMutableOperationMessageReference ref, WsMutableMessage headerMessage){
        if(headerMessage!= null){
            Iterator<?> messageParts = headerMessage.getMessageParts();
            if(messageParts.hasNext()){
                DefaultExtensionsRegistry defaultExtensionsRegistry = new DefaultExtensionsRegistry();
                WsSoapHeader soapHeader = (WsSoapHeader) defaultExtensionsRegistry
                        .createExtensionElement(
                                ExpandedName
                                        .makeName(
                                                "http://schemas.xmlsoap.org/wsdl/soap/",
                                                "header"),
                                ref);
                soapHeader.setMessage(headerMessage.getExpandedName());
                WsMessagePart next = (WsMessagePart)messageParts.next();
                soapHeader.setPart(next.getLocalName());

                soapHeader.setUse(WsSoapUse.LITERAL);
            }
        }
    }

    private String getLocation(
            IFile sharedResourceFile,
            Channel channel)
    {
        //TODO JMS
        final HttpConfigModelMgr modelmgr = new HttpConfigModelMgr(sharedResourceFile);
        modelmgr.parseModel();

        String host = getStringValue(modelmgr, "Host");
        String port = getStringValue(modelmgr, "Port");
        boolean useSSl = Boolean.valueOf(modelmgr.getStringValue("useSsl"));
        String address;
        if (useSSl) {
            port = port.trim().isEmpty() ? DEFAULT_HTTPS_PORT : port;
            host = host.trim().isEmpty() ? "localhost" : host;
            address = "https://" + host + ":" + port + channel.getFullPath();
        } else {
            port = port.trim().isEmpty() ? DEFAULT_HTTP_PORT : port;
            host = host.trim().isEmpty() ? "localhost" : host;
            address = "http://" + host + ":" + port + channel.getFullPath();
        }

        return address;
    }

    private String getStringValue(HttpConfigModelMgr modelmgr, String key) {
        String str = modelmgr.getStringValue(key);
        String value = "";
        if (isGlobalVar(str)) {
            str = str.substring(str.indexOf("%%") + 2);
            str = str.substring(0, str.indexOf("%%"));
            GlobalVariablesProvider provider = GlobalVariablesMananger
                    .getInstance().getProvider(this.project.getName());
            GlobalVariableDescriptor variable = provider.getVariable(str);
            if (variable != null) {
                value = variable.getValueAsString();
            }
        }else{
            value = str;
        }
        return value;
    }

    private boolean isGlobalVar(String str) {
        if (str==null)
            return false;
        if (str.startsWith("%%") && str.endsWith("%%"))
            return true;
        return false;
    }


    public void writeNamespaces(WsMutableWsdl wsdl,
                                EList<NamespaceEntry> namespaceEntries) {
        if (addedSchemas == null)
            addedSchemas = new HashSet<TnsDocument>();
        Set<String> includedConcepts = new HashSet<String>();
        Set<String> includedEvents = new HashSet<String>();
        for (NamespaceEntry namespaceEntry : namespaceEntries) {
            try {
                wsdl.getPrefixForNamespaceURI(namespaceEntry.getNamespace());
            } catch (NamespaceNotFoundException e1) {
                wsdl.ensureNamespaceDeclaration(namespaceEntry.getPrefix(),
                        namespaceEntry.getNamespace());
            }
//			
//			String location = cache.getLocation(namespaceEntry.getNamespace(),
//							SmNamespace.class);
//			if (location != null) {
            TnsDocument document = getDocument(wsdl, namespaceEntry.getNamespace());//cache.getDocument(location);
            if (document != null
                    && !addedSchemas.contains(document)) {
                removeImportLocations(wsdl, document);
                String location = document.getLocation();
                if (document instanceof MutableSchema){
                    removeIsNullableAttribute((SmSchema) document);
                    wsdl.addInternalSchema((SmSchema) document);
                }else if(document instanceof Wsdl){
                    WsWsdl temp = null;
                    try {
                        temp = getWsdl(location);
                    } catch (Exception e) {
                        temp = null;
                    }
                    if(temp != null){
                        Iterator<?> internalSchemas = temp.getInternalSchemas();
                        while (internalSchemas.hasNext()) {
                            XSDLSchema schema = (XSDLSchema) internalSchemas
                                    .next();
                            if (!addedSchemas.contains(schema)) {
                                removeIsNullableAttribute(schema);
                                wsdl.addInternalSchema(schema);
                                addedSchemas.add(schema);
                                addImportedSchema(wsdl, schema);
                            }
                        }
                    }
                }
                else{
                    if(location.endsWith("concept")|| location.endsWith("scorecard"))
                        includedConcepts.add(namespaceEntry.getNamespace());
                    else if(location.endsWith("event"))
                        includedEvents.add(namespaceEntry.getNamespace());
                }
                addedSchemas.add(document);
                addImportedSchema(wsdl, document);
            }
//			}
        }
        includeConceptSchemas(wsdl, includedConcepts);
        includeEventSchemas(wsdl, includedEvents);
        addBaseSchemas(wsdl);
    }

    private TnsDocument getDocument(WsMutableWsdl wsdl,String namespace) {
        TnsDocument document = null;
        TargetNamespace tns = cache.getNamespace(namespace, SmNamespace.class);
        if (tns instanceof TnsDocument) {
            String location = ((TnsDocument) tns).getLocation();
            if (location == null) {
                //If location doesn't exist, then document contains pre-loaded schema
                document = (TnsDocument) tns;
            } else {
                document = cache.getDocument(location);
            }
        } else if (tns instanceof TnsFragment) {
            String location = ((TnsFragment) tns).getDocument().getLocation();
            if (location == null) {
                //If location doesn't exist, then document contains pre-loaded schema
                document = ((TnsFragment) tns).getDocument();
            } else {
                document = cache.getDocument(location);
            }
        } else if (tns instanceof UeberSchemaNamespace) {
            UeberSchemaNamespace uNs = (UeberSchemaNamespace)tns;
            Iterator<?> fragments = uNs.getFragments();
            List<MutableSchema> schemas = new ArrayList<MutableSchema>();
            while (fragments.hasNext()) {
                TnsFragment frag = (TnsFragment) fragments.next();
                TnsDocument doc = frag.getDocument();
                if(doc instanceof MutableSchema)
                    schemas.add((MutableSchema)doc);
            }
            document = combineSchema(wsdl, uNs.getNamespace(), schemas);
        }

        return document;
    }

    private MutableSchema combineSchema(WsMutableWsdl wsdl, String namespaceURI,
                                        List<MutableSchema> schemas) {
        MutableSchema schema = null;
        if (schemas.size() > 0) {
            schema= createSchema(namespaceURI);
            List<MutableSchema> addedSchemas = new ArrayList<MutableSchema>();
            for (MutableSchema mutableSchema : schemas) {
                addToSchema(wsdl, schema, mutableSchema, addedSchemas);
                TnsFragment singleFragment = schema.getSingleFragment();
                Iterator<?> imports = mutableSchema.getSingleFragment().getImports();
                addImports(singleFragment, imports);
            }
        }
        return schema;
    }

    private void addToSchema(WsMutableWsdl wsdl, MutableSchema toSchema, MutableSchema fromSchema, List<MutableSchema> addedSchemas){
        if(addedSchemas.contains(fromSchema))
            return;

        addedSchemas.add(fromSchema);
        addImportedSchema(wsdl, fromSchema);
        Iterator<?> includes = fromSchema.getSingleFragment().getIncludes();
        while (includes.hasNext()) {
            TnsInclude next = (TnsInclude) includes.next();
            TnsDocument doc =next.getDocument();
            if(doc!= null)
                if (doc instanceof MutableSchema) {
                    addToSchema(wsdl, toSchema, (MutableSchema)doc, addedSchemas);
                }

        }
        Iterator<?> components = fromSchema.getComponents(SmComponent.ALL_TYPES);
        while (components.hasNext()) {
            SmComponent object = (SmComponent) components.next();
            toSchema.addSchemaComponent(object);
        }

    }

    private WsWsdl getWsdl(String wsdlFilePath) throws IOException, WsException {
        DefaultFactory wsdlParser = DefaultFactory.getInstance();
        String wsPath = wsdlFilePath;
        if(wsdlFilePath.startsWith("file:///"))
            wsPath = wsdlFilePath.substring(8);

        InputSource source = new InputSource(new FileInputStream(new File(
                wsPath)));
        String path = ResourceHelper.getLocationURI(project).getPath();
        File file = new File(path);
        URI uri = new URI(file.getCanonicalFile());
        source.setSystemId(uri.getFullName());
        WsWsdl wsdl = wsdlParser.parse(source);
        return wsdl;
    }

    private void addImportedSchema(WsMutableWsdl wsdl, TnsDocument document) {

        if (addedSchemas == null)
            addedSchemas = new HashSet<TnsDocument>();

        if (document == null )
            return;

        Set<String> includedConcepts = new HashSet<String>();
        Set<String> includedEvents = new HashSet<String>();

        Iterator<?> imports = document.getSingleFragment().getImports();

        while (imports.hasNext()) {
            TnsImport next = (TnsImport) imports.next();
            TnsDocument doc = getDocument(wsdl, next.getNamespaceURI());

            if (doc != null && !addedSchemas.contains(doc)) {
                if (doc instanceof MutableSchema) {
                    removeImportLocations(wsdl, document);
                    removeIsNullableAttribute((SmSchema) doc);
                    wsdl.addInternalSchema((SmSchema) doc);
                }else{
                    String location = doc.getLocation();
                    if(location.endsWith("concept")|| location.endsWith("scorecard"))
                        includedConcepts.add(next.getNamespaceURI());
                    else if(location.endsWith("event"))
                        includedEvents.add(next.getNamespaceURI());
                }
                addedSchemas.add(doc);
                addImportedSchema(wsdl, doc);
            }
        }
        includeConceptSchemas(wsdl, includedConcepts);
        includeEventSchemas(wsdl, includedEvents);
        addBaseSchemas(wsdl);
    }


    private void addBaseSchemas(WsMutableWsdl wsdl){
        if(addedConcepts.size()!= 0){
            SmSchema smSchema = getSmSchema("www.tibco.com/be/ontology/_BaseConcept");
            if (smSchema != null && !baseConceptSchemaAdded){
                removeIsNullableAttribute(smSchema);
                wsdl.addInternalSchema(smSchema);
                baseConceptSchemaAdded = true;
            }
        }

        if(addedEvents.size()!= 0){
            for (Event event : addedEvents) {
                if(isSoapEvent(event) && !baseSoapEventSchemaAdded){
                    SmSchema smSchema = getSmSchema("www.tibco.com/be/ontology/_BaseSOAPEvent");
                    if (smSchema != null ) {
                        removeIsNullableAttribute(smSchema);
                        wsdl.addInternalSchema(smSchema);
                        baseSoapEventSchemaAdded = true;
                    }
                }else if(!baseEventSchemaAdded){
                    SmSchema smSchema = getSmSchema("www.tibco.com/be/ontology/_BaseEvent");
                    if (smSchema != null) {
                        removeIsNullableAttribute(smSchema);
                        wsdl.addInternalSchema(smSchema);
                        baseEventSchemaAdded = true;
                    }
                }

            }

        }
    }

    private void includeConceptSchemas(WsMutableWsdl wsdl,Set<String> nameSpaces ){
        Map<String, Concept> allConcepts = getAllConcepts();
        if (addedConcepts == null)
            addedConcepts = new HashSet<Concept>();
        for (String string : nameSpaces) {
            Concept concept = allConcepts.get(string);
            if (concept != null)
                includeConceptSchema(wsdl, concept);
        }

    }

    private Map<String, Concept> getAllConcepts(){
        Map<String, Concept> concepts = new HashMap<String, Concept>();
        List<Entity> allEntities = CommonIndexUtils.getAllEntities(this.project.getName(), new ELEMENT_TYPES[]{ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD});
        for (Entity entity : allEntities) {
            concepts.put(baseNamespace+entity.getNamespace()+entity.getName(), (Concept)entity);
        }
        return concepts;
    }

    private void includeConceptSchema(WsMutableWsdl wsdl, Concept concept) {
        if (!addedConcepts.contains(concept)) {
            String namespaceURI = baseNamespace + concept.getNamespace()
                    + concept.getName();
            if (namespaceURI != null) {
                SmSchema smSchemaFromPath = getSmSchema(namespaceURI);
                if (smSchemaFromPath != null) {
                    removeIsNullableAttribute(smSchemaFromPath);
                    wsdl.addInternalSchema(smSchemaFromPath);
                    addedConcepts.add(concept);
                }
            }
        }
        Set<Concept> concepts = new HashSet<Concept>();
        Concept parentConcept = concept.getParentConcept();
        Concept superConcept = concept.getSuperConcept();
        if (parentConcept != null)
            concepts.add(parentConcept);
        if (superConcept != null)
            concepts.add(superConcept);
        EList<Concept> subConcepts = concept.getSubConcepts();
        if (subConcepts != null)
            concepts.addAll(subConcepts);
        for (Concept conc : concepts) {
            if (!addedConcepts.contains(conc))
                includeConceptSchema(wsdl, conc);
        }
    }

    private void includeEventSchemas(WsMutableWsdl wsdl,Set<String> nameSpaces ){
        Map<String, Event> allEvents = getAllEvents();
        if (addedEvents == null)
            addedEvents = new HashSet<Event>();
        for (String string : nameSpaces) {
            Event event = allEvents.get(string);
            if (event != null)
                includeEventSchema(wsdl, event);
        }
    }

    private Map<String, Event> getAllEvents(){
        Map<String, Event> events = new HashMap<String, Event>();
        List<Event> allEntities = CommonIndexUtils.getAllEvents(this.project.getName());
        for (Event entity : allEntities) {
            events.put(baseNamespace+entity.getNamespace()+entity.getName(), entity);
        }
        return events;
    }

    private void includeEventSchema(WsMutableWsdl wsdl,Event event ){
        if(!addedEvents.contains(event)){
            String namespaceURI = baseNamespace+ event.getNamespace()+event.getName();
            if(namespaceURI != null){
                SmSchema smSchemaFromPath = getSmSchema(namespaceURI);
                if(smSchemaFromPath != null) {
                    removeIsNullableAttribute(smSchemaFromPath);
                    wsdl.addInternalSchema(smSchemaFromPath);
                    addedEvents.add(event);
                }
            }
        }
    }

    private SmSchema getSmSchema(String nameSpace) {
        SmNamespaceProvider snp = cache.getSmNamespaceProvider();
        SmNamespace sm =  snp.getNamespace(nameSpace);
        if(sm == null)
            return null;
        SmElement element = null;
        MutableSchema schema = null;
        String[] terms = nameSpace.split("/");
        String elname = terms[terms.length - 1];
        element= (SmElement)sm.getComponent(SmComponent.ELEMENT_TYPE, elname);
        if(element != null)
            schema = (MutableSchema)element.getSchema();

        SmSchema normalizeSchema = normalizeSchema (schema);
        return normalizeSchema;
    }

    private SmSchema normalizeSchema(MutableSchema schema){
        MutableSchema cached;
        MutableComponentFactoryTNS mcf = DefaultComponentFactory.getTnsAwareInstance();
        cached= mcf.createSchema();
        cached.setNamespace(schema.getNamespace());
        cached.setFlavor(XSDL.FLAVOR);

        Iterator<?> imports = schema.getSingleFragment().getImports();
        addImports(cached.getSingleFragment(), imports);
        Iterator<?>  components = schema.getComponents(SmComponent.ELEMENT_TYPE);
        while(components.hasNext()){
            SmComponent c = (SmComponent)components.next();
            SmComponent component = cached.getComponent(c.getComponentType(), c.getName());
            if (component == null){
                cached.addSchemaComponent((SmComponent) c);
            }
        }

        return cached;
    }

    private void removeIsNullableAttribute(SmSchema schema) {
        Set<SmType> smTypeSet = new HashSet<SmType>();
        Iterator<?> components = schema.getComponents(SmType.ELEMENT_TYPE);
        while (components.hasNext()) {
            Object next = components.next();
            if(next instanceof SmElement){
                SmElement c = (SmElement)next;
                replaceIsNullableAttr(c.getMetaForeignAttributes());
                SmType smType = c.getType();
                if(smType != null){
                    removeIsNullableAttribute(smType, smTypeSet);
                }
            }
        }

        components = schema.getComponents(SmType.TYPE_TYPE);
        while (components.hasNext()) {
            Object next = components.next();
            if(next instanceof SmType){
                removeIsNullableAttribute((SmType)next, smTypeSet);
            }
        }
    }

    private void removeImportLocations( WsMutableWsdl wsdl, TnsDocument schema) {
        Iterator<?> imports = schema.getSingleFragment().getImports();
        while (imports.hasNext()) {
            TnsImport tnsImport = (TnsImport) imports.next();
            ((TnsImportImpl)tnsImport).setLocation(null);
            TnsDocument document = getDocument(wsdl, tnsImport.getNamespaceURI());
            if (document instanceof SmSchema) {
                SmSchema sch = (SmSchema) document;
                Iterator components = sch.getComponents(SmType.ATTRIBUTE_TYPE);
                while (components.hasNext()) {
                    Object next = components.next();
                    if (next instanceof DefaultAttribute) {
                        DefaultAttribute defAttr = (DefaultAttribute) next;
                        SmType baseType = defAttr.getType().getBaseType();
                        if (baseType.equals(XSDL.BOOLEAN)) {
                            defAttr.setDefaultValue(null);
                        }
                    }
                }
            }
        }
    }

    private void removeIsNullableAttribute(SmType smType, Set<SmType> smTypeSet) {
        Iterator<?> metaForeignAttributes2 = smType.getMetaForeignAttributes();
        while (metaForeignAttributes2.hasNext()) {
            Object object = metaForeignAttributes2.next();
            if(object instanceof DefaultMetaForeignAttribute){
                DefaultMetaForeignAttribute attr = (DefaultMetaForeignAttribute) object;
                if (attr != null && attr.getName().equalsIgnoreCase("isNillable")){
                    try {
                        metaForeignAttributes2.remove();
                    } catch(Exception e) {
                        break;
                    }
                }
            }
        }

        SmModelGroup contentModel = smType.getContentModel();

        if(contentModel == null)
            return;

        if (smTypeSet.contains(smType)) {
            return;
        }

        smTypeSet.add(smType);
        WsPlugin.debug("SmType Element--Name--{0}-NameSpace-{1}", smType.getName() != null ? smType.getName() : "", smType.getNamespace() != null ? smType.getNamespace() : "");

        Iterator<?> particles = contentModel.getParticles();
        while (particles.hasNext()) {
            SmParticle particle = (SmParticle) particles.next();
            SmParticleTerm particleTerm = particle.getTerm();
            if(particleTerm == null)
                continue;
            Iterator<?> metaForeignAttributes = particleTerm
                    .getMetaForeignAttributes();
            replaceIsNullableAttr(metaForeignAttributes);
            if(particleTerm instanceof SmElement){
                SmElement ele = (SmElement)particleTerm;
                SmType type = ele.getType();
                if(type != null && type != smType){
                    removeIsNullableAttribute(type, smTypeSet);
                }
            }
        }
    }

    private void replaceIsNullableAttr(Iterator<?> metaForeignAttributes ){
        while (metaForeignAttributes.hasNext()) {
            Object object = metaForeignAttributes.next();
            if(object instanceof DefaultMetaForeignAttribute){
                DefaultMetaForeignAttribute attr = (DefaultMetaForeignAttribute) object;
                if (attr != null && attr.getName().equalsIgnoreCase("isNillable"))
                    attr.setName("nillable");
            }
        }
    }

    protected static void addImports(TnsFragment schemaFragment,
                                     Iterator<?> importNodes) {
        Set<String> importsList = getImportsList(schemaFragment);
        while (importNodes.hasNext()) {
            TnsImport node = (TnsImport) importNodes.next();
            TnsImport imprt = new TnsImportImpl(null, null,
                    node.getNamespaceURI(), SmNamespace.class);
            if (!importsList.contains(imprt.getNamespaceURI()))
                schemaFragment.addImport(imprt);
        }
    }

    protected static Set<String> getImportsList(TnsFragment schemaFragment){
        Set<String> importsList = new HashSet<String>();
        Iterator<?> imports = schemaFragment.getImports();
        while (imports.hasNext()) {
            TnsImport node = (TnsImport) imports.next();
            importsList.add(node.getNamespaceURI());
        }

        return importsList;
    }

    private void addTypes(WsMutableWsdl wsdl,
                          EList<ImportRegistryEntry> registryImportEntries) {
        if (addedSchemas == null)
            addedSchemas = new HashSet<TnsDocument>();
        for (ImportRegistryEntry importRegistryEntry : registryImportEntries) {
            String location = importRegistryEntry.getLocation();
            TnsDocument document = StudioCorePlugin.getCache(this.project.getName())
                    .getDocument(location);
            if (document != null && document instanceof MutableSchema
                    && !addedSchemas.contains(document)) {
                if (document.getLocation().endsWith("xsd")) {
                    removeIsNullableAttribute((MutableSchema) document);
                    wsdl.addInternalSchema((MutableSchema) document);
                    addedSchemas.add(document);
                }
            }
        }
    }

    private void filterSoapEventDependentRule(List<Compilable> allRules) {
        rules = new HashMap<String, List<Compilable>>();
        for (Compilable rule : allRules) {
            Symbols symbols = rule.getSymbols();
            EList<Symbol> symbolList = symbols.getSymbolList();
            for (Symbol symbol : symbolList) {
                String type = symbol.getType();
                if (eventURIs.contains(type)) {
                    List<Compilable> list = rules.get(type);
                    if (list == null) {
                        list = new ArrayList<Compilable>();
                        rules.put(type, list);
                    }
                    list.add(rule);
                }
            }
        }
    }


    private void filterChannels(List<Channel> channels) {
        httpChannels = new ArrayList<Channel>();
        this.jmsChannels = new ArrayList<Channel>();
        for (Channel channel : channels) {
            DRIVER_TYPE driverType = channel.getDriver().getDriverType();
            if (DRIVER_HTTP.equals(driverType.getName())) {
                httpChannels.add(channel);
            } else if (DriverManagerConstants.DRIVER_JMS.equals(driverType.getName())) {
                this.jmsChannels.add(channel);
            }
        }
    }


    private static String trim(String str) {
        if (str == null) {
            return "";
        }
        if (str != null && !str.equals("")) {
            // strip trailing "/" or "\"
            if (str.endsWith("/") || str.endsWith("\\")) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    protected String getSystemID(File file) throws IOException {
        return new URI(file.getCanonicalFile()).getFullName();
    }

    protected InputSource getInputSource(File file) throws IOException {
        String sysID = getSystemID(file);
        InputSource is = new InputSource(sysID);
        is.setByteStream(new FileInputStream(file));
        return is;
    }

    protected boolean isSoapEvent(Event event) {
        String eventSuperPath = (String) event.getSuperEventPath();
        boolean isSoapEvent = RDFTypes.SOAP_EVENT.getName().equals(
                eventSuperPath);
        return isSoapEvent;
    }

	public void setProjectName(String projName) {
		this.projectName = projName;
	}
	
}