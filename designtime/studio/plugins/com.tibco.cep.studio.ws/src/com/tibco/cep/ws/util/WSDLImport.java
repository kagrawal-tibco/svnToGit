package com.tibco.cep.ws.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.util.StudioConfig;
import com.tibco.cep.ws.util.wsdlimport.ServerSideWsdlImport;
import com.tibco.cep.ws.util.wsdlimport.ServerSideWsdlImportResult;
import com.tibco.io.xml.XMLStreamReader;
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
import com.tibco.xml.ws.wsdl.WsMessage;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperation;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsPortType;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;

public class WSDLImport {
	
	private static final String ARG_TYPE_IN = "soapeventin";
	private static final String XSD_FILE_EXTN = ".xsd";

	private static XiParser xiParser = XiParserFactory.newInstance();
	private static XiFactory xiFactory = XiFactoryFactory.newInstance();

	private String schemasFolderName = "Schemas";
	private String projectName;
	private List<String> importedSchemas;
	private List<String> schemaFileNames;
	private String eventsFolderName = "Events";
	private String rulesFolderName = "Rules";
	private String rfFolderName = "RuleFunctions";
	private String workSpacePath;
	private String schemaFolderPath;

	private List<ResourceSet> sharedResources = new ArrayList<ResourceSet>();

	private List<Entity> createdEntities;
    private Set<Entity> modifiedEntities;
	private Set<WsPortType> processedTypes;
	final IProject project;
	
	//array to store the error list while importing WSDL
	//the error list will be shown to the user...
	private Set<String> m_ErrorList = new HashSet<String>();

	public WSDLImport(IProject prj) {
		this.projectName = prj.getName();
		this.project = prj;		
	}

	public void importWSDL(String wsdlFilePath, IProgressMonitor monitor)
			throws Exception {
		if (monitor == null)
			monitor = new NullProgressMonitor();

		createdEntities = new ArrayList<Entity>();
		processedTypes = new HashSet<WsPortType>();

		DefaultFactory wsdlParser = DefaultFactory.getInstance();
		String pathWsdl = wsdlFilePath;
		String property = StudioConfig.getInstance().getProperty("com.tibco.cep.studio.wsdl.includeSchema", "false");
		if(!Boolean.valueOf(property)){
			//IFile file2 = project.getFile(wsdlFilePath);
			//pathWsdl = file2.getLocation().toPortableString(); 
			pathWsdl = wsdlFilePath;
		}

		if(pathWsdl.contains(".projlib")){
			String projectLibPath = pathWsdl.split(".projlib")[0]+".projlib";
			String resourcePath = pathWsdl.split(".projlib")[1];
			if(!new File(projectLibPath).isDirectory()){
				pathWsdl = unzipProjectLibrary(projectLibPath, resourcePath);
				pathWsdl = pathWsdl+File.separator+resourcePath;
			}
		}
		
		
		InputSource source = new InputSource(new FileInputStream(new File(
				pathWsdl)));
		source.setSystemId(pathWsdl); ////#BE-10394 - relative location of xsd are also processed now.
		WsWsdl wsdl = wsdlParser.parse(source);

		monitor.worked(10);

		
		//if (Boolean.valueOf(property))
		importSchemas(wsdl);
		monitor.worked(10);

		importConcrete(wsdl);
		monitor.worked(10);

		importAbstract(wsdl);
		monitor.worked(10);

		persistSchema();
		monitor.worked(15);

		persistSharedResources();
		monitor.worked(15);

		persistEntities();
		monitor.worked(15);
	}
	
	private String unzipProjectLibrary(String zipFilePath, String wsdlPath) throws IOException{
		final File tmpDir = new File(System.getProperty("java.io.tmpdir"));
		final String fileName = "projlib_zip";
		String destDirectory = tmpDir.getAbsolutePath() +
	            File.separatorChar + 
	            fileName;
		File destDir = new File(destDirectory);
		FileUtils.deleteDirectory(destDir);
	    if (!destDir.exists()) {
	        destDir.mkdir();
	    }
	    
	    if(wsdlPath.charAt(0) == '/'||wsdlPath.charAt(0) == '\\'){
	    	wsdlPath = wsdlPath.substring(1, wsdlPath.length());
	    }
	    
	    
	    ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	    ZipEntry entry = zipIn.getNextEntry();
	    while (entry != null) {
	        String filePath = destDirectory + File.separator + entry.getName();
	        if (!entry.isDirectory()) {
	        	if(filePath.contains(wsdlPath)){
		        	File file = new File(filePath);
		        	file.getParentFile().mkdirs();
		        	file.createNewFile();
		        	extractFile(zipIn, filePath);
	        	}
	        } else {
	            File dir = new File(filePath);
	            dir.mkdir();
	        }
	        zipIn.closeEntry();
	        entry = zipIn.getNextEntry();
	    }
	    zipIn.close();
	    return destDirectory;
		
	}
	
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
	    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
	    byte[] bytesIn = new byte[4096];
	    int read = 0;
	    while ((read = zipIn.read(bytesIn)) != -1) {
	        bos.write(bytesIn, 0, read);
	    }
	    bos.close();
	}

	private void importSchemas(WsWsdl wsdl) throws Exception {
		Iterator<?> schemaIter = wsdl.getInternalSchemas();
		importedSchemas = new ArrayList<String>();
		schemaFileNames = new ArrayList<String>();
		while (schemaIter.hasNext()) {
			XSDLSchema schema = (XSDLSchema) schemaIter.next();

			NamespaceCounter nsc = new NamespaceCounter();
			schema.accept(nsc);

			XSDWriter xsdWriter = new XSDWriter();

			OutputStream oStream = new ByteArrayOutputStream();
			Writer w = new OutputStreamWriter(oStream, XMLStreamReader
					.getJavaEncodingName(XMLStreamReader.UTF8));
			xsdWriter.writeXmlHeader(w, XMLStreamReader
					.getXMLEncodingName(XMLStreamReader.UTF8));
			xsdWriter.setProperty(XSDWriter.SORTED, false);
			xsdWriter.write(schema, w, null);
			w.flush();

			String xsd = oStream.toString();
			xsd = addImports(xsd, nsc);

			if(schema.getNamespace() != null){
				String convertToValidTibcoIdentifier = BEStringUtilities.convertToValidTibcoIdentifier(schema.getNamespace(), true);
				convertToValidTibcoIdentifier = removeParentheses(convertToValidTibcoIdentifier);
				schemaFileNames.add(convertToValidTibcoIdentifier);	
				importedSchemas.add(xsd);
			}

		}
	}

	private String addImports(String xsd, NamespaceCounter nsc)
			throws Exception {
		Iterator<?> ns = nsc.getImportedNamespaces();
		XiNode rootNode = xiParser.parse(new InputSource(
				new ByteArrayInputStream(xsd.getBytes())));
		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName.makeName(
				XSDL.NAMESPACE, "schema"));
		String schemaNS = schemaNode.getName().getNamespaceURI();
		while (ns.hasNext()) {
			String importNS = (String) ns.next();
			XiNode importNode = xiFactory.createElement(ExpandedName.makeName(
					schemaNS, "import"));
			importNode.setAttributeStringValue(ExpandedName
					.makeName("namespace"), importNS);
			schemaNode.insertBefore(importNode, XiChild
					.getFirstChild(schemaNode));
		}
		xsd = XiSerializer.serialize(schemaNode);
		
		return xsd;
	}
	
	
	/**
	 * store error string in error list..
	 * @param errorString
	 */
	private void storeErrors(String errorString) {
		m_ErrorList.add(errorString);
	}
	
	/**
	 * getErrors 
	 * @return
	 * 
	 * display the errors to user if any after importing wsdl
	 * 
	 */
	public String getStoredErrors(){
		StringBuffer errors = new StringBuffer();
		for (String str : m_ErrorList) {
			errors.append(str).append("\n");
		}
		m_ErrorList.clear();
		return errors.toString();
	}

	private void importConcrete(
			WsWsdl wsdl)
			throws Exception {

		final ServerSideWsdlImportResult wsdlImportResult =
				new ServerSideWsdlImport(wsdl, this.project).execute();
		
		this.createdEntities.addAll(wsdlImportResult.getCreatedEntities());
		this.modifiedEntities = wsdlImportResult.getModifiedEntities();
		this.processedTypes.addAll(wsdlImportResult.getProcessedTypes());
		this.sharedResources.addAll(wsdlImportResult.getSharedResources());
		
		for (String error : wsdlImportResult.getErrors()) {
			this.storeErrors(error);
		}		
		
		if (wsdlImportResult.isAbstract()) { //abstract wsdl case #BE-10394
             final String errorString = "Warning: WSDL is abstract. Cannot create a channel.";
    	     storeErrors(errorString);
		}
	}	

	
	private void importAbstract(WsWsdl wsdl) throws Exception {

		// Iterate over port type and its operation to create rule,rule-function
		// Also create in, out and fault event for operation
		Iterator<?> iter = wsdl.getPortTypes();
		while (iter.hasNext()) {
			WsPortType portType = (WsPortType) iter.next();
			if (processedTypes.contains(portType)) {
				continue;
			}
			String portTypeName = portType.getName().getLocalName();
			String portTypeFolderPath = "/" + portTypeName;
			String rulesFolderPath = getRulesFolderPath(portTypeFolderPath, portTypeName );
			String ruleFnFolderPath = getRulesFnFolderPath(portTypeFolderPath);
			String eventFolderPath = getEventFolderPath(portTypeFolderPath);
			RuleSet ruleSet = WSDLImportUtil.createRuleSet();
			ruleSet.setFolder(rulesFolderPath);
			
			createdEntities.add(ruleSet);
			// iterate over operation and create rule function and rules
			Iterator<?> opnIter = portType.getOperations();
			while (opnIter.hasNext()) {
				WsOperation opn = (WsOperation) opnIter.next();
				String opName = opn.getLocalName(); 
				RuleFunction ruleFn = WSDLImportUtil.createRuleFunction(
						projectName, opName, ruleFnFolderPath + "/",
						ruleFnFolderPath);

				Rule rule = WSDLImportUtil.createRule(projectName, opName,
						rulesFolderPath + "/", rulesFolderPath);

				// defaults
                rule.setReturnType(";");
                final String dummyActionText = "// TODO - Provide Rule Action \n ;";
                rule.setActionText(dummyActionText);
                
				createdEntities.add(ruleFn);
				createdEntities.add(rule);
			
				Event inEvent = createEvent(eventFolderPath, opn,
						WsMessageKind.INPUT);
				if(inEvent != null){
					Symbols createSymbols = RuleFactory.eINSTANCE.createSymbols();
					Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
					String inEventURI = inEvent.getFullPath();
					symbol.setIdName(ARG_TYPE_IN);
					symbol.setType(inEventURI);
					createSymbols.getSymbolMap().put(ARG_TYPE_IN, symbol);
					rule.setSymbols(createSymbols);

					createSymbols = RuleFactory.eINSTANCE.createSymbols();
					symbol = RuleFactory.eINSTANCE.createSymbol();
					inEventURI = inEvent.getFullPath();
					symbol.setIdName(ARG_TYPE_IN);
					symbol.setType(inEventURI);
					createSymbols.getSymbolMap().put(ARG_TYPE_IN, symbol);
					ruleFn.setSymbols(createSymbols);
					createdEntities.add(inEvent);
				}

				Event outEvent = createEvent(eventFolderPath, opn,
						WsMessageKind.OUTPUT);
				if (outEvent == null) {
					ruleFn.setReturnType(null);
				}else{
					String eventURI = outEvent.getFullPath();
					ruleFn.setReturnType(eventURI);
					ruleFn.setActionText("return null;");
					createdEntities.add(outEvent);
				}

			}
		}
	}

	
	private Event createEvent(String folder, WsOperation opn, WsMessageKind kind)
			throws Exception {
		WsOperationMessage[] opMsgs = opn.getMessages(kind);
		if (opMsgs.length <= 0) {
			return null;
		}
		WsOperationMessage opMsg = opn.getMessages(kind)[0];
		WsMessage msg = opMsg.getMessage();
		String eventName = opMsg.getName().getLocalName();
		if (eventName == null || "".equals(eventName)) {
			eventName = msg.getName().getLocalName();
		}

		// Add message parts to payload schema
		SOAPEventPayloadBuilder builder = new SOAPEventPayloadBuilder();
		Iterator<?> iter = msg.getMessageParts();
		while (iter.hasNext()) {
			WsMessagePart wsMsgPart = (WsMessagePart) iter.next();
			addMsgAsBodyPart(wsMsgPart, builder);
		}

		if (kind == WsMessageKind.OUTPUT) {
			WsOperationMessage[] opFaultMsgs = opn
					.getMessages(WsMessageKind.FAULT);
			if (opFaultMsgs.length > 0) {
				WsOperationMessage opFaultMsg = opFaultMsgs[0];
				WsMessage faultMsg = opFaultMsg.getMessage();
				Iterator<?> msgPartIter = faultMsg
						.getMessageParts();
				while (msgPartIter.hasNext()) {
					addMsgAsFaultPart((WsMessagePart)msgPartIter.next(), builder);
				}
			}
		}

		return createEvent(folder, eventName, builder);
	}


	private SimpleEvent createEvent(String eventFolderPath, String eventName,
			SOAPEventPayloadBuilder builder) throws Exception {
		// Create new event and setpayload
		SimpleEvent event = WSDLImportUtil.createSimpleEvent(projectName,
				eventName, eventFolderPath + "/", eventFolderPath+ "/");
		event.setSuperEventPath(RDFTypes.SOAP_EVENT.getName());

		// // set soap schema as payload
		// Element payload = builder.getPayloadElement();
		// event.setPayloadSchema(payload);
		Element payloadElement = builder.getPayloadElement();
		event.setPayloadString(XiSerializer.serialize(payloadElement));
		// // payload prefix and namespace pair is added to event namespace
		// importer
		NamespaceImporter nsImporter = builder.getNamespaceImporter();
		Iterator<?> prefixIter = nsImporter.getPrefixes();
		while (prefixIter.hasNext()) {
			String prefix = (String) prefixIter.next();
			nsImporter.getNamespaceURIForPrefix(prefix);
			String namespaceURI = nsImporter.getNamespaceURIForPrefix(prefix);
			NamespaceEntry entry = EventFactory.eINSTANCE
					.createNamespaceEntry();
			entry.setPrefix(prefix);
			entry.setNamespace(namespaceURI);
			event.getNamespaceEntries().add(entry);
		}

		// return event
		return event;
	}


	private void addMsgAsBodyPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addBodyPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
//			SmType type = wsMsgPart.getType();
//			SmModelGroup smModelGroup = type.getContentModel();
//			// This will be null for simple types
//			if (smModelGroup != null) {
//				 Iterator particles = smModelGroup.getParticles();
//				 while(particles.hasNext()){
//					 SmParticle next = (SmParticle)particles.next();
//					 SmParticleTerm term = next.getTerm();
//					 if(term instanceof SmElement){
//						 SmElement element = (SmElement)term;
//						 builder.addBodyPart(element.getExpandedName(), element);
//					 }
//				 }
//			}
			builder.addBodyPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}

	private void addMsgAsFaultPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addFaultPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
			builder.addFaultPart(wsMsgPart.getName(), wsMsgPart.getType());
		}
	}


	public String getChannelInfo(String address) {
		int beginIndex = 0;
		if (address.startsWith("https")) {
			beginIndex = "https://".length();
		} else if (address.startsWith("http")) {
			beginIndex = "http://".length();
		}
		int endIndex = address.indexOf("/", beginIndex);
		if (endIndex < address.length()) {
			return address.substring(endIndex + 1);
		} else {
			return "Channel";
		}
	}

	private void persistSchema() throws Exception {
		if (importedSchemas == null)
			return;
		for (int i = 0; i < importedSchemas.size(); i++) {
			File folder = new File(getSchemaFolderPath());
			folder.mkdirs();

			File file = new File(folder.getAbsolutePath() + "/" + schemaFileNames.get(i)
					+ XSD_FILE_EXTN);
			if (file.exists())
				throw new Exception("Can not import. \nFile \""
						+ file.getAbsolutePath() + "\" already exist");
			file.createNewFile();

			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(importedSchemas.get(i));
			// Close the output stream
			out.close();
		}
	}

	private void persistSharedResources()
			throws Exception
	{
		final Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		
		for (ResourceSet resourceSet: this.sharedResources) {
			for (Resource resource : resourceSet.getResources()) {
				resource.save(options);
			}
		}
	}

	private void persistEntities() throws Exception {
	    final Set<Entity> entities = new HashSet<Entity>(this.createdEntities); 
	    entities.addAll(this.modifiedEntities);
		WSDLImportUtil.persistEntities(
		        entities,
		        this.modifiedEntities,
		        this.project,
				new NullProgressMonitor());
	}

	private String getCurrentProjectPath() {
		if (workSpacePath == null)
			workSpacePath = project.getLocation().toString();
		return workSpacePath;
	}

	private String getSchemaFolderPath() {
		if (schemaFolderPath == null)
			schemaFolderPath = getCurrentProjectPath() +"/" + schemasFolderName;

		return schemaFolderPath;
	}
	

	private String getRulesFolderPath(String portTypeFolderPath, String portTypeName) {
		String path = portTypeFolderPath + "/" + rulesFolderName + "/" + portTypeName;

		return path;
	}

	private String getRulesFnFolderPath(String portTypeFolderPath) {
		String path = portTypeFolderPath + "/" + rfFolderName;

		return path;
	}

	private String getEventFolderPath(String portTypeFolderPath) {
		String path = portTypeFolderPath + "/" + eventsFolderName;

		return path;
	}
	
	private String removeParentheses(
			String s)
	{
		return s.replace("[\\(\\)]", "_");
	}

}
