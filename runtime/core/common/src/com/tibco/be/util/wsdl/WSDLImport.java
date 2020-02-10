package com.tibco.be.util.wsdl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.mutable.MutableFolder;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.rule.mutable.MutableRule;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleSet;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDestination;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.impl.DefaultMutableDestination;
import com.tibco.cep.repo.mutable.MutableBEProject;
import com.tibco.io.xml.XMLStreamReader;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.DefaultNamespaceMapper;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.util.NamespaceCounter;
import com.tibco.xml.util.XSDWriter;
import com.tibco.xml.ws.wsdl.WsBinding;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsMessage;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperation;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.WsPortType;
import com.tibco.xml.ws.wsdl.WsService;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimeMultipartRelated;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimePart;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapAddress;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBody;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapHeader;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapStyle;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapBody_12;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapHeader_12;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;

/**
 * This is a utility class that take designer project path, wsdl file
 * location and import WSDL components as Designer artifacts.
 *
 * The WSDL Component to Designer artifacts mapping is as follows-
 *
 * types - schemas
 * portType - folder and ruleset
 * operation - rulefunction and rules
 * message - soap event
 * service - folder
 * port and binding - channel and transport
 * soapaction - destination
 *
 * @author schelwa
 *
 */
public class WSDLImport {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	private static final String PATH_SEPARATOR = "/";
	private static final String ARG_TYPE_IN = "soapeventin";

	private static final String DRIVER_TYPE_HTTP = "HTTP";

	private static final String DEFAULT_HTTP_PORT = "80";
	private static final String DEFAULT_HTTPS_PORT = "443";
	private static final String HTTP_RESOURCE_URI = "www.tibco.com/shared/HTTPConnection";
	private static final String HTTP_RESOURCE_TAGNAME = "httpSharedResource";
	private static final String HTTP_RESOURCE_EXTN = ".sharedhttp";
	private static final String SERVER_TYPE_HTTP_COMPONENTS = "BUILT-IN";
	private static final String SERVER_TYPE_TOMCAT = "TOMCAT";
	private static final String XSD_FILE_EXTN = ".xsd";

	private static XiParser xiParser = XiParserFactory.newInstance();
	private static XiFactory xiFactory = XiFactoryFactory.newInstance();

	private String schemasFolderName = "Schemas";
	private String eventsFolderName = "Events";
	private String rulesFolderName = "Rules";
	private String rfFolderName = "RuleFunctions";
	private String transportFolderName = "Transports";


	private MutableBEProject project;
	private MutableOntology  ontology;
	private String projectPath;
	private String wsdlPath;
	private String importURI = "/";
	private String httpServerType = SERVER_TYPE_HTTP_COMPONENTS;
	private ArrayList<String> earURI = new ArrayList<String>();

	private Map<WsOperation, MutableEvent> opInEventMap = new HashMap<WsOperation, MutableEvent>();
	private Map<WsOperation, String> opRulefnMap        = new HashMap<WsOperation, String>();
	private Map<String, String> destRulefnMap        = new HashMap<String, String>();
	private Set<WsPortType> processedTypes = new HashSet<WsPortType>();

	/**
	 * Create a WSDLImport instance using array of arguments
	 */
	public WSDLImport(String[] args){
		parseArgs(args);
	}

	/**
	 * Create a WSDLImport instance
	 *
	 * @param projectPath path of project in which wsdl import
	 * @param wsdlFilePath wsdl file path
	 */
	public WSDLImport(String projectPath, String wsdlFilePath){
		this.projectPath = projectPath;
		this.wsdlPath = wsdlFilePath;
	}

	/**
	 * Create a WSDLImport instance
	 *
	 * @param projectPath path of project in which wsdl import
	 * @param wsdlFilePath wsdl file path
	 * @param baseURI path where imported artifacts are generated
	 */
	public WSDLImport(String projectPath, String wsdlFilePath, String baseURI){
		this(projectPath, wsdlFilePath);
		this.importURI = baseURI;
	}

	private void parseArgs(String[] args) {
		int i = 0;
		while (i < args.length) {
			if (args[i].equalsIgnoreCase("-p")) {
				if ((i + 1) >= args.length)
					usage();
				projectPath = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-w")) {
				if ((i + 1) >= args.length)
					usage();
				wsdlPath = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-u")) {
				if ((i + 1) >= args.length)
					usage();
				importURI = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-s")) {
				if ((i + 1) >= args.length)
					usage();
				httpServerType = args[i + 1];
				i += 2;
			} else if (args[i].equalsIgnoreCase("-ear")) {
				if ((i + 1) >= args.length)
					usage();
				String uriStr = args[i + 1];
				if(uriStr != null && !"".equals(uriStr)){
					String[] uris = uriStr.split(",");
					for(String uri: uris){
						earURI.add(uri);
					}
				}
				i += 2;
			} else if (args[i].equalsIgnoreCase("-help")) {
				usage();
			} else {
				System.err.println("Unrecognized parameter: " + args[i]);
				usage();
			}
		}
		if ("".equals(projectPath) || "".equals(wsdlPath)) {
			usage();
		}
	}

	/**
	 * Imports the WSDL in give project at given location
	 */
	public void importWSDL() throws Exception {

		projectPath = projectPath.replace('/', File.separatorChar);
		projectPath = projectPath.replace('\\', File.separatorChar);
		this.project = new MutableBEProject(this.projectPath);
		this.project.load();

		DefaultFactory wsdlParser = DefaultFactory.getInstance();
		InputSource source = new InputSource(new FileInputStream(new File(wsdlPath)));
		WsWsdl wsdl = wsdlParser.parse(source);

		this.ontology = (MutableOntology) project.getOntology();

		if(wsdl.getName() != null && !"".equals(wsdl.getName().getLocalName()) && !"Untitled".equals(wsdl.getName().getLocalName())){
			String wsdlName = wsdl.getName().getLocalName();
			String path = "";
			if(importURI.endsWith(PATH_SEPARATOR)){
				path = importURI + wsdlName;
			} else {
				path = importURI + PATH_SEPARATOR + wsdlName;
			}
			MutableFolder folder = ontology.createFolder(path, true);
			importURI = folder.getFullPath();
		}
		MutableFolder folder = (MutableFolder) ontology.getFolder(importURI);

		importSchemas(wsdl, project, folder);
		importConcrete(wsdl, project, folder);
		importAbstract(wsdl, project, importURI);

		configureEAR();

		project.save();
	}

	/**
	 * Import wsdl types as schemas in project
	 */
	private void importSchemas(WsWsdl wsdl, MutableBEProject project, MutableFolder folder) throws Exception {

		MutableFolder schemaFolder = getOrAddFolder(folder, schemasFolderName);

		Iterator schemaIter = wsdl.getInternalSchemas();
		while (schemaIter.hasNext()) {
			XSDLSchema schema = (XSDLSchema) schemaIter.next();

			NamespaceCounter nsc = new NamespaceCounter();
			schema.accept(nsc);

			XSDWriter xsdWriter = new XSDWriter();

			//MutableNamespaceContext nsCtx = new DefaultNamespaceContext();
			//nsCtx.add("", XSDL.NAMESPACE); // xmlns="http://www.w3.org/2001/XMLSchema"
			//xsdWriter.setProperty(XSDWriter.PREFERRED_NAMESPACECTX, nsCtx);

			OutputStream oStream = new ByteArrayOutputStream();
			Writer w = new OutputStreamWriter(oStream,
					XMLStreamReader.getJavaEncodingName(XMLStreamReader.UTF8));
			xsdWriter.writeXmlHeader(w, XMLStreamReader.getXMLEncodingName(XMLStreamReader.UTF8));
			xsdWriter.setProperty(XSDWriter.SORTED, false);
			xsdWriter.write(schema, w, null);
			w.flush();

			String xsd = oStream.toString();
			xsd = addImports(xsd, nsc);

			String filename = BEStringUtilities.convertToValidTibcoIdentifier(schema.getNamespace(), true);
			String schemaPath = schemaFolder.getFullPath() + filename + XSD_FILE_EXTN;
			GenUtil.writeToVFile(project, schemaPath, xsd.getBytes());
		}
	}

	private String addImports(String xsd, NamespaceCounter nsc) throws Exception {
		Iterator ns = nsc.getImportedNamespaces();
		XiNode rootNode = xiParser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes())));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
				.makeName(XSDL.NAMESPACE, "schema"));
		String schemaNS = schemaNode.getName().getNamespaceURI();
		while (ns.hasNext()) {
			String importNS = (String) ns.next();
			XiNode importNode = xiFactory.createElement(ExpandedName.makeName(schemaNS, "import"));
			importNode.setAttributeStringValue(ExpandedName.makeName("namespace"), importNS);
			schemaNode.insertBefore(importNode, XiChild.getFirstChild(schemaNode));
		}
		xsd = XiSerializer.serialize(schemaNode);
		return xsd;
	}

	private void importAbstract(WsWsdl wsdl, MutableBEProject project, String basePath) throws Exception {

		// Iterate over port type and its operation to create rule,rule-function
		// Also create in, out and fault event for operation
		Iterator iter = wsdl.getPortTypes();
		while (iter.hasNext()) {
			WsPortType portType = (WsPortType) iter.next();
			if(processedTypes.contains(portType)){
				continue;
			}
			String portTypeName = portType.getName().getLocalName();
			String path = basePath + portTypeName;
			MutableFolder folder = ontology.createFolder(path, true);
			MutableFolder rulesFolder = getOrAddFolder(folder, rulesFolderName);
			MutableFolder ruleFnFolder = getOrAddFolder(folder, rfFolderName);
			MutableFolder eventFolder = getOrAddFolder(folder, eventsFolderName);

			MutableRuleSet ruleSet = ontology.createRuleSet(rulesFolder, portTypeName, true);

			// iterate over operation and create rule function and rules
			Iterator opnIter = portType.getOperations();
			while (opnIter.hasNext()) {
				WsOperation opn = (WsOperation) opnIter.next();
				String opName = opn.getName().getLocalName();
				MutableRuleFunction ruleFn = ontology.createRuleFunction(ruleFnFolder, opName, true);

				// Need this map to set thie rule function as destintion PP
				opRulefnMap.put(opn, ruleFn.getFullPath());

				MutableRule rule = ruleSet.createRule(opName, true, false);

				WsOperationMessage[] inMsg = opn.getMessages(WsMessageKind.INPUT);
				WsOperationMessage[] outMsg = opn.getMessages(WsMessageKind.OUTPUT);

				MutableEvent inEvent[] = new MutableEvent[inMsg.length];
				MutableEvent outEvent[] = new MutableEvent[outMsg.length];
				for(int i = 0; i < inMsg.length; i++){
					inEvent[i] = createEvent(eventFolder, opn, WsMessageKind.INPUT);
					String eventURI = inEvent[i].getFullPath();
					ruleFn.setArgumentType(ARG_TYPE_IN, eventURI);
					rule.addDeclaration(ARG_TYPE_IN, eventURI);
					rule.setActionText("//TODO Provide Rule Action");

					opInEventMap.put(opn, inEvent[i]);
				}

				for(int i =0; i < outMsg.length; i++){
					outEvent[i] = createEvent(eventFolder, opn, WsMessageKind.OUTPUT);
					String eventURI = outEvent[i].getFullPath();
					ruleFn.setReturnType(eventURI);
					ruleFn.setBody("return null;");
				}
			}
		}
	}

	private void importConcrete(WsWsdl wsdl, MutableBEProject project, MutableFolder folder)throws Exception {

		Iterator serviceIter = wsdl.getServices();
		while(serviceIter.hasNext()){
			WsService service = (WsService) serviceIter.next();
			String serviceName = service.getName().getLocalName();
			serviceName = BEStringUtilities.convertToValidTibcoIdentifier(serviceName, false);
			MutableFolder servicefolder = folder.createSubFolder(serviceName, true);
			
			MutableFolder transportFolder = getOrAddFolder(servicefolder, transportFolderName);

			Iterator<WsPort> portIter = service.getPorts();
			while (portIter.hasNext()) {

				WsPort port = portIter.next();
				WsBinding binding = port.getBinding();
				
				WsExtensionElement extnElement = binding.getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BINDING_ELEMENT));
				if(extnElement == null){
					System.out.println("Info:WSDL Import supports only soap binding. port=" + port.getLocalName() + " binding=" + binding.getLocalName() + " is skipped.");
					continue;
				}
				
				WsSoapBinding soapBinding = (WsSoapBinding) extnElement;;
				String transport = soapBinding.getTransport();
				if(!WsdlConstants.SOAP_11_TRANSPORT_URI.equals(transport)){
					System.out.println("Info:WSDL Import does not support transport " + transport + ". port=" + port.getLocalName() + " binding=" + binding.getLocalName()+ " is skipped.");
					continue;
				}
				
				String portName = port.getLocalName();
				String httpResName = BEStringUtilities.convertToValidTibcoIdentifier(portName, false);
				String httpResPath = transportFolder.getFullPath() + httpResName + HTTP_RESOURCE_EXTN;
				String addressURL = getSOAPEndPointURL(port);
				createHTTPResource(project, httpResPath, addressURL);
				MutableChannel channel = createChannel(addressURL, httpResPath);

				WsPortType portType = binding.getPortType();
				processedTypes.add(portType);
				String portTypeName = portType.getName().getLocalName();
				MutableFolder portTypeFolder = servicefolder.createSubFolder(portTypeName, true);
				MutableFolder rulesFolder = getOrAddFolder(portTypeFolder, rulesFolderName);
				MutableFolder ruleFnFolder = getOrAddFolder(portTypeFolder, rfFolderName);
				MutableFolder eventFolder = getOrAddFolder(portTypeFolder, eventsFolderName);
				MutableRuleSet ruleSet = ontology.createRuleSet(rulesFolder, portTypeName, true);
				
				Iterator opnIter = binding.getOperations();
				while(opnIter.hasNext()){
					WsOperationReference opnRef = (WsOperationReference) opnIter.next();
					WsExtensionElement soapExtnElement = opnRef.getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.OPERATION_ELEMENT));
					if(soapExtnElement != null){
						WsSoapOperation soapOp = (WsSoapOperation) soapExtnElement;
						if(soapOp.getStyle() != WsSoapStyle.NONE && soapOp.getStyle() != WsSoapStyle.DOCUMENT ){
							System.out.println("Info:WSDL Import - style \'" +
									soapOp.getStyle() + "\' is not supported. soap operation binding is skipped. port=" + port.getLocalName() + " binding=" + binding.getLocalName()+ " operation="+ opnRef.getOperation().getLocalName());
							continue;
						}
						WsOperation opn = opnRef.getOperation();
						String opName = opn.getName().getLocalName();
						MutableRuleFunction ruleFn = ontology.createRuleFunction(ruleFnFolder, opName, true);
						MutableRule rule = ruleSet.createRule(opName, true, false);
						
						MutableEvent inEvent = createEvent(eventFolder, opnRef, WsMessageKind.INPUT);
						String inEventURI = inEvent.getFullPath();
						ruleFn.setArgumentType(ARG_TYPE_IN, inEventURI);
						rule.addDeclaration(ARG_TYPE_IN, inEventURI);
						rule.setActionText("//TODO - Provide Rule Action");
						
						MutableEvent outEvent = createEvent(eventFolder, opnRef, WsMessageKind.OUTPUT);
						if(outEvent == null){
							ruleFn.setReturnType(null);
						} else {
							String outEventURI = outEvent.getFullPath();
							ruleFn.setReturnType(outEventURI);
							ruleFn.setBody("return null;");
						}

						String soapAction = ((WsSoapOperation)soapExtnElement).getSoapAction();
						if(soapAction != null && !"".equals(soapAction)){
							String destName  = soapAction;
							if(BEStringUtilities.startsWithInValidTibcoIdentifier(destName)){
								destName = destName.substring(1);
							}
							destName = BEStringUtilities.convertToValidTibcoIdentifier(destName, true);
							String ruleFnURI = ruleFn.getFullPath();
							addDestination(channel, destName, inEvent, outEvent, ruleFnURI);
						}
					}
				}
			}
		}
	}
	
	private MutableEvent createEvent(MutableFolder folder, WsOperationReference opRef, WsMessageKind kind) throws Exception {
		String eventName = "";
		List<WsOperationMessageReference> opMsgRefList = getOperationMessage(opRef, kind);
		WsOperationMessage opMsg = null;
		if(opMsgRefList.size() > 0){
			// for input and output message there will be only one msg reference, hence using index 0
			opMsg = opMsgRefList.get(0).getMessage();
			WsMessage msg = opMsg.getMessage();
			eventName = opMsg.getName().getLocalName();
			if(eventName == null || "".equals(eventName)){
				eventName = msg.getName().getLocalName();
			}
		} else {
			WsOperationMessage[] opMsgs = opRef.getOperation().getMessages(kind);
			if(opMsgs.length >0){
				opMsg = opMsgs[0];
				WsMessage msg = opMsg.getMessage();
				eventName = opMsg.getName().getLocalName();
				if(eventName == null || "".equals(eventName)){
					eventName = msg.getName().getLocalName();
				}
			}
		}
		
		if(opMsg == null){
			return null;
		}

		SOAPEventPayloadBuilder builder = new SOAPEventPayloadBuilder();
		if(opMsgRefList.size() > 0){
			// Add message parts to payload schema
			handleWsOperationMessageReference(opMsgRefList.get(0), builder);
		} else if(opMsg != null){
			Iterator<WsMessagePart> msgPartIter = opMsg.getMessage().getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart(msgPartIter.next(), builder);
			}
		}
		
		// add fault message parts to payload
		if(kind == WsMessageKind.OUTPUT){
			List<WsOperationMessageReference> opFaultMsgRefList = getOperationMessage(opRef, WsMessageKind.FAULT);
			if(opFaultMsgRefList.size() > 0){
				for (WsOperationMessageReference opFaultMsgRef : opFaultMsgRefList) {
					ExpandedName faultMsgName = opFaultMsgRef.getName();
					WsOperationMessage opFaultMsg = opRef.getOperation().getMessage(faultMsgName);
					Iterator<WsMessagePart> msgPartIter = opFaultMsg.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart(msgPartIter.next(), builder);
					}
				}

			} else {
				WsOperationMessage[] opMsgs = opRef.getOperation().getMessages(WsMessageKind.FAULT);
				for (int i = 0; i < opMsgs.length; i++) {
					WsOperationMessage opFaultMsg = opMsgs[i];
					Iterator<WsMessagePart> msgPartIter = opFaultMsg.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart(msgPartIter.next(), builder);
					}
				}
			}
		}
		
		return createEvent(folder, eventName, builder);
	}
	
	private MutableEvent createEvent(MutableFolder folder, String eventName, SOAPEventPayloadBuilder builder) throws Exception {
		// Create new mutable event and setpayload
		MutableEvent event = ontology.createEvent(folder, eventName, "0", Event.SECONDS_UNITS, true);
		event.setSuperEventPath(RDFTypes.SOAP_EVENT.getName());
		
		// set soap schema as payload
		Element payload = builder.getPayloadElement();
		event.setPayloadSchema(payload);

		// payload prefix and namespace pair is added to event namespace importer
		NamespaceImporter nsImporter = builder.getNamespaceImporter();
		Iterator<String> prefixIter = nsImporter.getPrefixes();
		while (prefixIter.hasNext()) {
			String prefix = (String) prefixIter.next();
			nsImporter.getNamespaceURIForPrefix(prefix);
			String namespaceURI = nsImporter.getNamespaceURIForPrefix(prefix);
			((DefaultNamespaceMapper)event.getPayloadNamespaceImporter()).addNamespaceURI(prefix, namespaceURI);
		}

		// return event
		return event;
	}

	private String getSOAPEndPointURL(WsPort port) {
		WsExtensionElement element = port.getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.ADDRESS_ELEMENT));
		if(element == null){
			element = port.getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.ADDRESS_ELEMENT));
		}
		return (element != null)?((WsSoapAddress)element).getLocation() : "";
	}
	
	private List getOperationMessage(WsOperationReference opRef, WsMessageKind kind){
		List<WsOperationMessageReference> list = new ArrayList<WsOperationMessageReference>();
		Iterator<WsOperationMessageReference> iter = opRef.getMessages();
		while (iter.hasNext()) {
			WsOperationMessageReference wsOpMsgRef = iter.next();
			if(wsOpMsgRef.getMessageKind() == kind){
				list.add(wsOpMsgRef);
			}
		}
		return list;
	}
	
	private void handleWsOperationMessageReference(WsOperationMessageReference opMsgRef, SOAPEventPayloadBuilder builder){
		WsMessage msg = opMsgRef.getMessage().getMessage();
		Iterator<WsExtensionElement> opMsgRefExtnIter = opMsgRef.getExtensionElements();
		while (opMsgRefExtnIter.hasNext()) {
			WsExtensionElement wsExtensionElement = (WsExtensionElement) opMsgRefExtnIter
					.next();
			if(wsExtensionElement instanceof WsSoapBody || wsExtensionElement instanceof WsSoapBody_12){
				WsSoapBody soapBody = (WsSoapBody)wsExtensionElement;
				addSoapBodyPartsToPayload(soapBody, msg, builder);
			} else if(wsExtensionElement instanceof WsSoapHeader || wsExtensionElement instanceof WsSoapHeader_12){
				WsSoapHeader soapHeader = (WsSoapHeader)wsExtensionElement;
				ExpandedName msgName = soapHeader.getMessage();
				if(msgName != null){
					String part = soapHeader.getPart();
					if(part != null){
						WsMessage message = opMsgRef.getWsdl().getMessage(msgName);
						WsMessagePart wsMsgPart = getMessagePart(message, part);
						addMsgAsHeaderPart(wsMsgPart, builder);
					} else {
						Iterator<WsMessagePart> msgPartIter = msg.getMessageParts();
						while (msgPartIter.hasNext()) {
							addMsgAsHeaderPart(msgPartIter.next(), builder);
						}
					}
				}
			} else if(wsExtensionElement instanceof WsMimeMultipartRelated){
				WsMimeMultipartRelated mimeMultipart = (WsMimeMultipartRelated)wsExtensionElement;
				Iterator<WsMimePart> iter = mimeMultipart.getParts();
				while (iter.hasNext()) {
					WsMimePart wsMimePart = (WsMimePart) iter.next();
					Iterator<WsSoapBody> iterSoapBodies = wsMimePart.getSoapBodies();
					if(iterSoapBodies.hasNext()){
						WsSoapBody soapBody = iterSoapBodies.next();
						addSoapBodyPartsToPayload(soapBody, msg, builder);
					} else {
						continue;
					}
				}
			}
		}
	}
	
	private void addSoapBodyPartsToPayload(WsSoapBody soapBody, WsMessage msg, SOAPEventPayloadBuilder builder){
		String[] parts = soapBody.getParts();
		if(parts != null){
			for(String part : parts){
				WsMessagePart wsMsgPart = getMessagePart(msg, part);
				addMsgAsBodyPart(wsMsgPart, builder);
			}
		} else {
			Iterator<WsMessagePart> msgPartIter = msg.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart(msgPartIter.next(), builder);
			}
		}
	}
	
	private WsMessagePart getMessagePart(WsMessage message, String partName){
		Iterator<WsMessagePart> iter = message.getMessageParts();
		while (iter.hasNext()) {
			WsMessagePart wsMessagePart = (WsMessagePart) iter.next();
			String name = wsMessagePart.getName().getLocalName();
			if(name != null && name.equals(partName)){
				return wsMessagePart;
			}
		}
		return null;
	}
	
	private void addMsgAsBodyPart(WsMessagePart wsMsgPart, SOAPEventPayloadBuilder builder){
		if(wsMsgPart.getElement() != null){
			builder.addBodyPart(wsMsgPart.getElementName(), wsMsgPart.getElement());
		} else if(wsMsgPart.getType() != null){
			builder.addBodyPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}
	
	private void addMsgAsFaultPart(WsMessagePart wsMsgPart, SOAPEventPayloadBuilder builder){
		if(wsMsgPart.getElement() != null){
			builder.addFaultPart(wsMsgPart.getElementName(), wsMsgPart.getElement());
		} else if(wsMsgPart.getType() != null){
			builder.addFaultPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}

	private void addMsgAsHeaderPart(WsMessagePart wsMsgPart, SOAPEventPayloadBuilder builder){
		if(wsMsgPart.getElement() != null){
			builder.addHeaderPart(wsMsgPart.getElementName(), wsMsgPart.getElement());
		} else if(wsMsgPart.getType() != null){
			builder.addHeaderPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}
	
	private MutableFolder getOrAddFolder(MutableFolder parentFolder, String subFolderName) throws Exception {
		if(subFolderName != null && !"".equals(subFolderName)){
			MutableFolder folder = (MutableFolder) parentFolder.getSubFolder(subFolderName);
			if(folder == null){
				folder = parentFolder.createSubFolder(subFolderName, true);
			}
			return folder;
		}
		return parentFolder;
	}

	/*
	 * Resolves soap:address as http://host:port/<channel folder structure/channel_name>
	 * and create channel(by reference)
	 */
	private MutableChannel createChannel(String soapAddress, String httpResourcePath) throws Exception {
		MutableChannel channel = null;
		String pathInfo = getChannelInfo(soapAddress);
		pathInfo.replace('\\', '/');
		String path[] = pathInfo.split("/");
		String channelPath = "";
		for (int i = 0; i < path.length-1; i++) {
			String folderName = BEStringUtilities.convertToValidTibcoIdentifier(path[i], false);
			channelPath = channelPath.concat(folderName);
			if(i+1 < (path.length-1)){
				channelPath = channelPath.concat("/");
			}
		}
		String channelName = BEStringUtilities.convertToValidTibcoIdentifier(path[path.length-1], false);
		String channelURI = "/" + channelPath + channelName;
		Entity existingChannel = ontology.getEntity(channelURI);
		if(existingChannel != null){
			throw new Exception("Channel already exist " + channelURI);
		}

		MutableFolder folder = (MutableFolder) ontology.getRootFolder();
		if(channelPath != null && !"".equals(channelPath)){
			folder = folder.createSubFolder(channelPath, true);
		}

		channel = ontology.createChannel(folder, channelName, false);
		channel.setDriver(DRIVER_TYPE_HTTP);
		MutableDriverConfig config = (MutableDriverConfig) channel.getDriver();
		config.setConfigMethod(DriverConfig.CONFIG_BY_REFERENCE);
		config.setReference(httpResourcePath);
		config.setServerType(httpServerType);
		channel.setDriver(config);
		return channel;
	}

	/*
	 *
	 */
	private void addDestination(MutableChannel channel, String destinationName, MutableEvent inEvent, MutableEvent outEvent, String ruleFnURI) throws Exception {
		MutableDriverConfig config = (MutableDriverConfig) channel.getDriver();
		MutableDestination destination = new DefaultMutableDestination(destinationName, config);
		destination.setSerializerDeserializerClass("com.tibco.cep.driver.http.serializer.SOAPMessageSerializerV2");
		destination = destination.setEventURI(inEvent.getFullPath());
		config.addDestination(destination);

		String channelURI = channel.getFullPath();
		String destURI    = destination.getFullPath();
		if(inEvent != null){
			inEvent.setChannelURI(channelURI);
			inEvent.setDestinationName(destURI);
		}
		
		if(outEvent != null){
			outEvent.setChannelURI(channelURI);
			outEvent.setDestinationName(destURI);
		}
		String destChannelPath = channelURI + ".channel" + "/" + destURI;
		destRulefnMap.put(destChannelPath, ruleFnURI);
	}

	/*
	 * Create a event and add message part namespace to event payload
	 */
	private MutableEvent createEvent(MutableFolder folder, WsOperation opn, WsMessageKind kind) throws Exception {
		WsOperationMessage[] opMsgs = opn.getMessages(kind);
		if(opMsgs.length <= 0){
			return null;
		}
		WsOperationMessage opMsg = opn.getMessages(kind)[0];
		WsMessage msg = opMsg.getMessage();
		String eventName = opMsg.getName().getLocalName();
		if(eventName == null || "".equals(eventName)){
			eventName = msg.getName().getLocalName();
		}

		// Add message parts to payload schema
		SOAPEventPayloadBuilder builder = new SOAPEventPayloadBuilder();
		Iterator<WsMessagePart> iter = msg.getMessageParts();
		while (iter.hasNext()) {
			WsMessagePart wsMsgPart = (WsMessagePart) iter.next();
			addMsgAsBodyPart(wsMsgPart, builder);
		}
		
		if(kind == WsMessageKind.OUTPUT){
			WsOperationMessage[] opFaultMsgs = opn.getMessages(WsMessageKind.FAULT);
			if(opFaultMsgs.length > 0){
				WsOperationMessage opFaultMsg = opFaultMsgs[0];
				WsMessage faultMsg = opFaultMsg.getMessage();
				Iterator<WsMessagePart> msgPartIter = faultMsg.getMessageParts();
				while (msgPartIter.hasNext()) {
					addMsgAsFaultPart(msgPartIter.next(), builder);
				}
			}
		}

		return createEvent(folder, eventName, builder);
	}
	
	private void createHTTPResource(MutableBEProject project, String path, String address) throws Exception {

		boolean isSecure = false;
		if(address == null || "".equals(address)){
			return;
		}

		int beginIndex = 0;
		if(address.startsWith("http")){
			beginIndex = "http://".length();
		} else if(address.startsWith("https")){
			beginIndex = "https://".length();
			isSecure = true;
		}

		String host = "";
		String port = "";
		int endIndex = address.indexOf("/", beginIndex);
		String hostPort = address.substring(beginIndex, endIndex);
		if(hostPort.contains(":")){
			int index = hostPort.lastIndexOf(":");
			if(index < hostPort.length()) {
				host = hostPort.substring(0, index);
				port = hostPort.substring(index+1);
			} else {
				host = hostPort;
				port = DEFAULT_HTTP_PORT;
			}
		} else {
			host = hostPort;
			port = isSecure ? DEFAULT_HTTPS_PORT : DEFAULT_HTTP_PORT; 
		}

		XiNode httpSharedResNode = xiFactory.createElement(ExpandedName.makeName(HTTP_RESOURCE_URI, HTTP_RESOURCE_TAGNAME));
		XiNode configNode = xiFactory.createElement(ExpandedName.makeName("config"));
		XiNode hostNode = xiFactory.createElement(ExpandedName.makeName("Host"));
		hostNode.appendChild(xiFactory.createTextNode(host));
		XiNode portNode = xiFactory.createElement(ExpandedName.makeName("Port"));
		portNode.appendChild(xiFactory.createTextNode(port));

		httpSharedResNode.appendChild(configNode);
		configNode.appendChild(hostNode);
		configNode.appendChild(portNode);

		Writer w = new StringWriter();
		XiSerializer.serialize(httpSharedResNode, w, true);
		String text = w.toString();
		GenUtil.writeToVFile(project, path, text.getBytes());
	}

	/*
	 * Search for archive files in the project and configure it
	 * to set all destination enabled and set PP to destinations
	 */
	private void configureEAR() throws Exception {

		VFileDirectory rootDir = this.project.getVFileFactory().getRootDirectory();
		List<VFileStream> archives = new ArrayList<VFileStream>();
		if(earURI.size() > 0){
			for(String uri: earURI){
				GenUtil.findArchive(rootDir, archives, uri);
			}
		} else {
			GenUtil.findArchive(rootDir, archives);
		}
		
		for (VFileStream fileStream : archives) {
			XiNode earNode = GenUtil.getVFileAsXiNode(fileStream);
			if(earNode == null){
				continue;
			}
			XiNode barNode = XiChild.getChild(earNode, ExpandedName.makeName("BusinessEventsArchive"));
			if(barNode != null){
				ExpandedName destRFName = ExpandedName.makeName("destinationsRuleFunctions");
				XiNode destRFNode = XiChild.getChild(barNode, destRFName);
				if(destRFNode != null){
					//update the node value
					String urisString = destRFNode.getStringValue();
					Map existingMap = decodeDestinationConfig(urisString);
					for(String destination: this.destRulefnMap.keySet()){
						if(!existingMap.containsKey(destination)){
							existingMap.put(destination, this.destRulefnMap.get(destination));
						}
					}
					urisString = generateDestinationPP(this.destRulefnMap);
					destRFNode.setStringValue(urisString);

				} else {
					String urisString = generateDestinationPP(this.destRulefnMap);
					destRFNode = xiFactory.createElement(destRFName);
					destRFNode.setStringValue(urisString);
					barNode.appendChild(destRFNode);
				}

				if(SERVER_TYPE_TOMCAT.equals(httpServerType)){
					ExpandedName destWorkerName = ExpandedName.makeName("destinationsworkers");
					XiNode destWorkerNode = XiChild.getChild(barNode, destWorkerName);
					if(destWorkerNode != null){
						String urisString = destWorkerNode.getStringValue();
						Map<String, String> existingMap = decodeDestinationConfig(urisString);
						for(String destination: this.destRulefnMap.keySet()){
							existingMap.put(destination, "-1");
						}
						urisString = generateDestinationWorkers(existingMap);
						destWorkerNode.setStringValue(urisString);
					} else {
						String urisString = generateDestinationWorkers(this.destRulefnMap);
						destWorkerNode = xiFactory.createElement(destWorkerName);
						destWorkerNode.setStringValue(urisString);
						barNode.appendChild(destWorkerNode);
					}
				}
				
				String value = XiSerializer.serialize(earNode.getRootNode());
				fileStream.update(new ByteArrayInputStream(value.getBytes()));
			}
		}
	}

	private String generateDestinationPP(Map<String, String> destRFMap){
		String uris[] = new String[destRFMap.size()];
		int index = 0;
		Set<Map.Entry<String, String>> mapEntries = destRFMap.entrySet();
		for (Entry<String, String> entry : mapEntries) {
			String destURI = entry.getKey();
			String rfURI = entry.getValue();
			String destRFEntry = destURI.concat("=").concat(rfURI);
			uris[index++] = destRFEntry;
		}
		String retString  = encodeStrings(uris);
		return retString;
	}

	private String generateDestinationWorkers(Map<String, String> destRFMap){
		String uris[] = new String[destRFMap.size()];
		int index = 0;
		Set<Map.Entry<String, String>> mapEntries = destRFMap.entrySet();
		for (Entry<String, String> entry : mapEntries) {
			String destURI = entry.getKey();
			String workerOption = entry.getValue();
			String destWorkerEntry = destURI.concat("=").concat(workerOption);
			uris[index++] = destWorkerEntry;
		}
		String retString  = encodeStrings(uris);
		return retString;
	}
	
	private static String encodeStrings(String[] uris) {
		final StringBuffer buffer = new StringBuffer();
		try {
			for (int i = 0, max = uris.length; i < max; i++) {
				if (0 < i) {
					buffer.append(',');
				}//if
				buffer.append(URLEncoder.encode(uris[i], "UTF-16BE"));
			}//for
		} catch (UnsupportedEncodingException cannotHappen) {
			cannotHappen.printStackTrace();
		}//catch
		return buffer.toString();
	}//encodeUris

	private Map decodeDestinationConfig(String urisString){
		final HashMap<String, String> uris = new HashMap<String, String>();
		if (null == urisString) {
			return uris;

		} else {
			final StringTokenizer tokenizer = new StringTokenizer(urisString, ",", false);
			try {
				while (tokenizer.hasMoreTokens()) {
					final String encodedURI = tokenizer.nextToken();

					if (!"".equals(encodedURI)) {
						final String uri = URLDecoder.decode(encodedURI, "UTF-16BE");
						final StringTokenizer tk = new StringTokenizer(uri, "=");
						if (tk.countTokens() == 2) {
							uris.put(URLDecoder.decode(tk.nextToken(), "UTF-16BE"),
									URLDecoder.decode(tk.nextToken(), "UTF-16BE"));
						}
					}//if
				}//while
			} catch (UnsupportedEncodingException cannotHappen) {
				cannotHappen.printStackTrace();
			}//catch
			return uris;
		}//else
	}

	/*-----------------------------------------------------------------------
	 * usage
	 *----------------------------------------------------------------------*/
	public static void usage() {
		System.err.println("\nUsage: java WSDLImport [options] ");
		System.err.println("");
		System.err.println("   where options are:");
		System.err.println("");
		System.err.println(" -p absolute path to project\n -w absolute path to wsdl file\n [-u project folder uri, where imported artifacts are generated]\n [-s http server type]\n [-ear comma separated EAR uri. By default, all EARs in project are configured]");
		System.err.println(" -help : help message\n");
		System.exit(0);
	}

	public static void main(String agrs[]){
		try {
			if(agrs.length < 2){
				usage();
			}
			WSDLImport importer = new WSDLImport(agrs);
			importer.importWSDL();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getChannelInfo(String address){
		int beginIndex = 0;
		if(address.startsWith("http")){
			beginIndex = "http://".length();
		} else if(address.startsWith("https")){
			beginIndex = "https://".length();
		}
		int endIndex = address.indexOf("/", beginIndex);
		if(endIndex < address.length()) {
			return address.substring(endIndex + 1);
		} else {
			return "Channel";
		}
	}
}