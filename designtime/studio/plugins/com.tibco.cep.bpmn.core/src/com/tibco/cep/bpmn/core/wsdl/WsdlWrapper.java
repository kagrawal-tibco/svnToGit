package com.tibco.cep.bpmn.core.wsdl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.xml.sax.InputSource;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsExtensionPoint;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperation;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.WsPortType;
import com.tibco.xml.ws.wsdl.WsService;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapAddress;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;
import com.tibco.xml.ws.wsdl.helpers.DefaultFactory;
import com.tibco.xml.ws.wsdl.helpers.WsdlConstants;
import com.tibco.xml.ws.wsdl.impl.Operation;

/**
 * 
 * @author majha
 *
 */
public class WsdlWrapper {
	private final static String JMS_W3C_TRANSPORT="http://www.w3.org/2010/soapjms/";
	private final static String JMS_TIBCO_TRANSPORT="http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS";
//	private final static String HTTP_TRANSPORT="http://schemas.xmlsoap.org/soap/http";
	private static final String JMS_W3C_URI="http://www.w3.org/2010/soapjms/";
	private static final String JMS_TIBCO_URI="http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS";
	private static final String JNDI_TIBCO_URI="http://www.tibco.com/namespaces/ws/2004/soap/apis/jndi";
	
	private static final String W3C_DELIVERY_MODE= "deliveryMode";
	//private static final String W3C_DELIVERY_MODE_IN_QUERY= "deliveryMode";
	private static final String W3C_DESTINATION_NAME= "destinationName";
	//private static final String W3C_DESTINATION_IN_QUERY= "jms-dest";
	private static final String W3C_CONNECTION_FACTORY= "jndiConnectionFactoryName";
	//private static final String W3C_CONNECTION_FACTORY_IN_QUERY= "jndiConnectionFactoryName";
	private static final String W3C_INITIAL_CONTEXT_FACTORY= "jndiInitialContextFactory";
	//private static final String W3C_INITIAL_CONTEXT_FACTORY_IN_QUERY= "jndiInitialContextFactory";
	private static final String W3C_JNDI_URL= "jndiURL";
	//private static final String W3C_JNDI_URL_IN_QUERY= "jndiURL";
	private static final String W3C_REPLY_TO_NAME= "replyToName";
	//private static final String W3C_REPLY_TO_NAME_IN_QUERY= "replyToName";
	//private static final String W3C_TOPIC_REPLY_TO_NAME= "topicReplyToName";
	//private static final String W3C_TOPIC_REPLY_TO_NAME_IN_QUERY= "topicReplyToName";
	private static final String W3C_PRIORITY= "priority";
	//private static final String W3C_PRIORITY_IN_QUERY= "priority";
	//private static final String W3C_TARGET_SERVICE= "targetService";
	//private static final String W3C_TARGET_SERVICE_IN_QUERY= "targetService";
	private static final String W3C_TTL_SERVICE= "timeToLive";
	//private static final String W3C_TTL_IN_QUERY= "timeToLive";
	private static final String W3C_JNDI_CONTEXT_PARAMETER= "jndiContextParameter";
	private static final String W3C_JNDI_CONTEXT_PARAMETER_PREFIX= "jndi-";
	private static final String W3C_MESSAGE_FORMAT_ATTRIBUT="contentType";
	private static final String TIBCO_MESSAGE_FORMAT_ATTRIBUTE = "messageFormat";
	private static final String TIBCO_JMS_TARGET_ADDRESS_EXT_TAG = "targetAddress";
	private static final String TIBCO_JMS_CONNECTIONFACTORY_EXT_TAG = "connectionFactory";
	
//	private static final String TIBCO_JMS_DESTINATION_ATTRIBUTE = "destination";
	private static final String TIBCO_JMS_VALUE_ATTRIBUTE = "value";
	private static final String TIBCO_JNDI_CONTEXT_EXT_TAG = "context";
	private static final String TIBCO_JNDI_CONTEXTPROPERTY_ELEMENT_TAG = "property";
	private static final String TIBCO_JNDI_CONTEXTPROPERTY_NAME_ATTRIBUTE = "name";
	private static final String TIBCO_JNDI_CONTEXTPROPERTY_TYPE_ATTRIBUTE = "name";
	
	private static final String JNDI_PROVIDER_URL = "java.naming.provider.url";
	private static final String JNDI_INITIAL_CONTEXTFACTORY = "java.naming.factory.initial";
	
	private Map<String, String> jmsConfigURI= new HashMap<String,String>();
	private Map<String, String> jmsConfigService=new HashMap<String,String>();
	private Map<String, String> jmsConfigPort=new HashMap<String,String>();
	private Map<String, String> jmsConfigBinding=new HashMap<String,String>();
	
	private List<EObject> lstJndiContextParameterURI = new ArrayList<EObject>();
	private List<EObject> lstJndiContextParameterService = new ArrayList<EObject>();
	private List<EObject> lstJndiContextParameterPort = new ArrayList<EObject>();
	private List<EObject> lstJndiContextParameterBinding = new ArrayList<EObject>();
	
	private IProject project;
	private WsWsdl wsdl;
	private String wsdlpath;
	private String wsdlname;
	private SharedElement wsdlElt;

	public WsdlWrapper(IProject iProject, String path) {
		this.project = iProject;
		this.wsdlpath = path;
		init();
	}
	
	private void init(){
		try {
			InputSource source=null;
			IFile file2 = project.getFile(wsdlpath);
			if(!file2.exists()){
				IFile fileLoc=IndexUtils.getLinkedResource(this.project.getProject().getName(),wsdlpath);
				if(fileLoc!=null){
					file2=fileLoc;	
					wsdlElt=null;
					handleLinkRes(fileLoc);
					InputStream is=IndexUtils.getInputStream(wsdlElt);
					source=new InputSource(is);
					source.setSystemId(IndexUtils.getSysIdforWsdlAndXsdInProjLib(wsdlElt));
				}
			}
			String pathWsdl = file2.getLocation().toPortableString();
			if(!pathWsdl.contains( ".wsdl"))
				 pathWsdl = pathWsdl + ".wsdl";
//			 pathWsdl = file2.getLocation().toPortableString()+".wsdl"; 
			
			if(wsdlElt==null){
			source = new InputSource(new FileInputStream(new File(pathWsdl)));
			source.setSystemId(pathWsdl);
			}
			DefaultFactory wsdlParser = DefaultFactory.getInstance();
			wsdl = wsdlParser.parse(source);
			wsdlname = file2.getName();
//			CommonECoreHelper.loadWsdlPortTypes(wsdl, project.getName(), wsdlname);
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
		}

	}
	
	private void handleLinkRes(IFile fileLoc){
		DesignerProject index = IndexUtils.getIndex(project);
		if (index == null) {
			return;
		}
		EList<DesignerProject> projects = index.getReferencedProjects();
		for (int i = 0; i < projects.size(); i++) {
			DesignerProject referencedProject = projects.get(i);
			checkEntries( project,referencedProject,fileLoc);
			if(wsdlElt!= null)
				break;
		}
	}
	
	private void checkEntries(IProject project,
			ElementContainer container,IFile fileLoc) {
		EList<DesignerElement> entries = container.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement designerElement = entries.get(i);
			if (designerElement instanceof ElementContainer) {
				checkEntries(project, (ElementContainer) designerElement, fileLoc);
			} else if (designerElement instanceof SharedElement) {
				SharedElement element = (SharedElement) designerElement;
				int count=fileLoc.getFullPath().segmentCount();
				String filename=fileLoc.getFullPath().segment(count-1);
				if(element.getFileName().equals(filename)){
					wsdlElt= element;
					return ;
				}
			}
		}
	}

	
	public String getWsdlName(){
		return wsdlname;
	}
	
	
	public Set<String> getServices(){
		Set<String> services = new HashSet<String>();
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				services.add(service.getLocalName());			
			}
		}
		return services;
	}
	
	public String getEndPointUrl(String serviceName, String portName){
		String endPoint = "";
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							endPoint = getSOAPEndPointURL(next);
							break;
						}
					}
					
				}		
			}
		}
		return endPoint;
	}
	
	private String getSOAPEndPointURL(WsPort port) {
		WsExtensionElement element = port.getExtensionElement(ExpandedName
				.makeName(WsdlConstants.SOAP_URI_11,
						WsdlConstants.ADDRESS_ELEMENT));
		if (element == null) {
			element = port.getExtensionElement(ExpandedName.makeName(
					WsdlConstants.SOAP_URI_12, WsdlConstants.ADDRESS_ELEMENT));
		}
		return (element != null) ? ((WsSoapAddress) element).getLocation() : "";
	}
	
	public Set<String> getPort(String serviceName){
		Set<String> portNames = new HashSet<String>();
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						portNames.add(next.getLocalName());	
					}
					
				}
						
			}
		}	
		return portNames;
	}
	
	public Set<String> getOperationTypes(String portName, String serviceName){
		Set<String> operations = new HashSet<String>();
		Iterator<?> iter = wsdl.getServices();
		if(wsdl != null){
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							Iterator<?> op = next.getBinding().getOperations();
							while (op.hasNext()) {
								WsOperationReference object = (WsOperationReference) op.next();
								operations.add(object.getLocalName());
							}
						}
					}
					
				}
						
			}
		}
		return operations;
	}
	
	public WsOperationReference getOperationRef(String portName, String serviceName, String operationName){
		Iterator<?> iter = wsdl.getServices();
		if(wsdl != null){
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							Iterator<?> op = next.getBinding().getOperations();
							while (op.hasNext()) {
								WsOperationReference object = (WsOperationReference) op.next();
								if(object.getLocalName().equals(operationName))
									return object;
							}
						}
					}
					
				}
						
			}	
		}
		return null;
	}
	
	public String getSoapAction(String portName, String serviceName, String operationName){
		String soapAction = "";
		if(wsdl!= null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							Iterator<?> op = next.getBinding().getOperations();
							while (op.hasNext()) {
								WsOperationReference object = (WsOperationReference) op.next();
								if(object.getLocalName().equals(operationName)){
									WsExtensionElement extensionElement = getExtensionElement(object);
									if(extensionElement!=null)
									{
										soapAction = ((WsSoapOperation) extensionElement)
											.getSoapAction();
									break;
									}
								}
							}
						}
					}
					
				}
						
			}
		}
		return soapAction;
	}
	
	public boolean isHttpTransport(String portName, String serviceName){
		boolean isHttp = true;
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							WsExtensionElement bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement == null)
								bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement != null){
								String transport = ((WsSoapBinding)bindingElement).getTransport();
								if(transport.equals(JMS_TIBCO_TRANSPORT) || transport.equals(JMS_W3C_TRANSPORT)){
									isHttp = false;
								}
							}
							break;
						}
					}
					
				}
						
			}
		}
			
		return isHttp;
	}
	
	private WsExtensionElement getExtensionElement(WsOperationReference opnRef  ) {
		WsExtensionElement element = opnRef.getExtensionElement(ExpandedName
				.makeName(WsdlConstants.SOAP_URI_11,
						WsdlConstants.OPERATION_ELEMENT));
		if (element == null) {
			element = opnRef.getExtensionElement(ExpandedName.makeName(
					WsdlConstants.SOAP_URI_12,
					WsdlConstants.OPERATION_ELEMENT));
		}
		return element;
	}
	
	
	public Set<String> getOperationTypes(){
		Set<String> operations = new HashSet<String>();
		Iterator<?> iter = wsdl.getPortTypes();
		while (iter.hasNext()) {
			WsPortType portType = (WsPortType) iter.next();
			Iterator<?> op = portType.getOperations();
			while (op.hasNext()) {
				Operation object = (Operation) op.next();
				operations.add(object.getLocalName());
			}
			
		}
		
		return operations;
	}
	
	public SmElement getInMessgeElement(String operationName){
		if(wsdl != null){
			Iterator<?> portTypes = wsdl.getPortTypes();
			while (portTypes.hasNext()) {
				WsPortType portType = (WsPortType) portTypes.next();
				Iterator<?> operations = portType.getOperations();
		    	while (operations.hasNext()) {
					WsOperation opn = (WsOperation) operations.next();
					String opName = opn.getName().getLocalName();
					if(opName.equalsIgnoreCase(operationName)){
						WsOperationMessage[] opMsgs = opn.getMessages(WsMessageKind.INPUT);
						if (opMsgs.length > 0) {
							WsOperationMessage wsOperationMessage = opMsgs[0];
							Iterator<?> iter = wsOperationMessage.getMessage()
									.getMessageParts();
							if (iter.hasNext()) {
								WsMessagePart wsMsgPart = (WsMessagePart) iter.next();
								return wsMsgPart.getElement();
							}
						}
					}
		    	}
			}
		}
		
		return null;
	}
	
	public SmElement getOutMessgeElement(String operationName){
		if(wsdl != null){
			Iterator<?> portTypes = wsdl.getPortTypes();
			while (portTypes.hasNext()) {
				WsPortType portType = (WsPortType) portTypes.next();
				Iterator<?> operations = portType.getOperations();
		    	while (operations.hasNext()) {
					WsOperation opn = (WsOperation) operations.next();
					String opName = opn.getName().getLocalName();
					if(opName.equalsIgnoreCase(operationName)){
						WsOperationMessage[] opMsgs = opn.getMessages(WsMessageKind.OUTPUT);
						if (opMsgs.length > 0) {
							WsOperationMessage wsOperationMessage = opMsgs[0];
							Iterator<?> iter = wsOperationMessage.getMessage()
									.getMessageParts();
							if (iter.hasNext()) {
								WsMessagePart wsMsgPart = (WsMessagePart) iter.next();
								return wsMsgPart.getElement();
							}
						}
					}
		    	}
			}
		}
		return null;
	}
	
	private boolean isTibcoJmsTransport(String portName, String serviceName){
		boolean isTibcoJmsTransport = false;
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							WsExtensionElement bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement == null)
								bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement != null){
								String transport = ((WsSoapBinding)bindingElement).getTransport();
								if(transport.equals(JMS_TIBCO_TRANSPORT)){
									isTibcoJmsTransport = true;
								}
							}
							break;
						}
					}
					
				}
						
			}
		}
		return isTibcoJmsTransport;
	}
	
	public void getJmsTransportProperties(String serviceName, String portName, EObjectWrapper<EClass, EObject> taskWrapper){
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(taskWrapper);
		if(addDataExtensionValueWrapper != null){
			EObject object = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
			EObjectWrapper<EClass, EObject> jmsConfig = null;
			if(object != null){
				jmsConfig = EObjectWrapper.wrap(object);	
			}else{
				jmsConfig = EObjectWrapper.createInstance(BpmnModelClass.EXTN_JMS_CONFIG_DATA);
				addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG, jmsConfig.getEInstance());
			}
			
			if(isTibcoJmsTransport(portName, serviceName)){
				jmsConfig.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_W3C_STANDARD, false);
				fetchTibcoJmsTransportPropertiesFromService(serviceName, portName, jmsConfig);
			}else if (isW3CJmsTransport(portName, serviceName)){
				jmsConfig.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_W3C_STANDARD, true);
				fetchW3CJmsTransportPropertiesFromService(serviceName, portName, jmsConfig);
			}
			else{
				/* Todo
				 * we should  give a warning here 
				 */
				jmsConfig.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IS_W3C_STANDARD, false);
				fetchTibcoJmsTransportPropertiesFromService(serviceName, portName, jmsConfig);
			}
			
		}
			
	}
	
	private Map<String,String> parseJmsURI(String jmsUri){
		Map<String,String> mapjmsProp= new HashMap<String,String>();
		if (jmsUri!= null){
			String[] arrJmsProp=jmsUri.split("\\?");
			String[] arrJmsPropTemp=null;
			
			try{
			if(arrJmsProp[1]!=null){
				arrJmsPropTemp=arrJmsProp[1].split("\\&");
				mapjmsProp.put("jms-uri", arrJmsProp[0]);
				for(String str:arrJmsPropTemp){
					mapjmsProp.put(str.split("=")[0], str.split("=")[1]);
					}
			}
			}catch(Exception e){
				//for catching null pointer exception
			}
		}
		return mapjmsProp;
	}
	private boolean isW3CJmsTransport(String portName, String serviceName) {

		boolean isW3CJmsTransport = false;
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if(service.getLocalName().equals(serviceName)){
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							WsExtensionElement bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_11, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement == null)
								bindingElement = next.getBinding().getExtensionElement(ExpandedName.makeName(WsdlConstants.SOAP_URI_12, WsdlConstants.BINDING_ELEMENT));
							if(bindingElement != null){
								String transport = ((WsSoapBinding)bindingElement).getTransport();
								if(transport.equals(JMS_W3C_TRANSPORT)){
									isW3CJmsTransport = true;
								}
							}
							break;
						}
					}
					
				}
						
			}
		}
		return isW3CJmsTransport;
	
	}

	private void fetchW3CJmsTransportPropertiesFromService(String serviceName, String portName,
			EObjectWrapper<EClass, EObject> jmsConfig){
		
		Map<String,String> mapjmsPropURI= new HashMap<String,String>();
		if(wsdl != null){
			mapjmsPropURI=parseJmsURI(getEndPointUrl(serviceName,portName));
			if(mapjmsPropURI.size()!=0){
				getJmsPropertiesFromURI(mapjmsPropURI);
			}
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if (service.getLocalName().equals(serviceName)) {
					fetchW3CTransportPropertiesFromExtensionPoint(service, jmsConfigService,lstJndiContextParameterService);
					
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){

							if (next.getBinding()!=null)
								fetchW3CTransportPropertiesFromExtensionPoint(next.getBinding(),jmsConfigBinding,lstJndiContextParameterBinding);
							fetchW3CTransportPropertiesFromExtensionPoint(next, jmsConfigPort,lstJndiContextParameterPort);
						}
					}
				}

			}
			//check for precedence of Jms properties
			applyPrecedenceofJmsTransportProperties(jmsConfig);
		}
		
		
	}
	
	private void applyPrecedenceofJmsTransportProperties(EObjectWrapper<EClass, EObject> jmsConfig){
		
		//msgFormat
		String msgFormat=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT);
		if (msgFormat != null
				&& !msgFormat.trim().isEmpty()) {
			if(msgFormat.equalsIgnoreCase("text")){
				jmsConfig
				.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,
						BpmnModelClass.ENUM_MESSAGE_FORMAT_TEXT);
			}else {
				jmsConfig
				.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,
						BpmnModelClass.ENUM_MESSAGE_FORMAT_BYTES);
			}
			
		}
		
		
		//jndiProviderURL
		String jndiProviderURL=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
		if(jndiProviderURL != null && !jndiProviderURL.isEmpty())
		jmsConfig
		.setAttribute(
				BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL,jndiProviderURL);
		
		//deliveryMode
		String deliveryMode=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE);
		
		Integer ideliveryMode=2;
		if (deliveryMode!=null)
		 ideliveryMode=deliveryMode.equals("NON-PERSISTENT")?1:2;
		jmsConfig
		.setAttribute(
				BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE,ideliveryMode);
		
		//destinationName
		String destinationName=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME);
		if (destinationName != null
				&& !destinationName.trim().isEmpty())
			jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME,
							destinationName);
		
		//jndiInitialContextFactory
		String jndiInitialContextFactory=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY);
		if (jndiInitialContextFactory != null
				&& !jndiInitialContextFactory.trim().isEmpty())
			jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY,
							jndiInitialContextFactory);
		
		//timeToLive
		String timeToLive=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE);
		Long ltimeToLivee=Long.parseLong(timeToLive);
		if (ltimeToLivee != null)
			jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE,
							ltimeToLivee);
		
		//priority
		String priority=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY);
		Integer ipriority=Integer.parseInt(priority);
		if (ipriority != null && ipriority<=9 && ipriority>=0  )
			jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY,
							ipriority);
		
		//replyToName
		String replyToName=getJmsPropertiesWithHighestPrecedence(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION);
		if (replyToName != null  && !replyToName.trim().isEmpty() )
			jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION,
							replyToName);
		//jndiProps
		List<EObject> lstJndiContextParameter= getJndipropWithHighestPrecedence();
		if(lstJndiContextParameter!=null){
			for(EObject eobj:lstJndiContextParameter){
			jmsConfig.addToListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS,eobj );
			}
		}
	}
	
	/*
	 * Precedence order
	 * 1.URI in address
	 * 2.Port
	 * 3.Service
	 * 4.Binding
	 */
	
	
	private String getJmsPropertiesWithHighestPrecedence(String attribute){
		if(jmsConfigURI.get(attribute)!=null)
			return jmsConfigURI.get(attribute);
		else if (jmsConfigPort.get(attribute)!=null)
			return jmsConfigPort.get(attribute);
		else if (jmsConfigService.get(attribute)!=null)
			return (jmsConfigService.get(attribute)).toString();
		else 
			return jmsConfigBinding.get(attribute);
	}
	
	private List<EObject> getJndipropWithHighestPrecedence(){
		if(lstJndiContextParameterURI!= null && !lstJndiContextParameterURI.isEmpty())
			return lstJndiContextParameterURI;
		else if (lstJndiContextParameterPort!= null && !lstJndiContextParameterPort.isEmpty())
			return lstJndiContextParameterPort;
		else if (lstJndiContextParameterService!= null && !lstJndiContextParameterService.isEmpty())
			return lstJndiContextParameterService;
		else if (lstJndiContextParameterBinding!= null && !lstJndiContextParameterBinding.isEmpty())
			return lstJndiContextParameterBinding;
		else
			return null;
	}
	private void getJmsPropertiesFromURI(Map<String,String> mapjmsPropURI){
		
		
		//jndiContextParameter
		lstJndiContextParameterURI.clear();
		for(Map.Entry<String, String> entry:mapjmsPropURI.entrySet()){
			if(entry.getKey().startsWith(W3C_JNDI_CONTEXT_PARAMETER_PREFIX)){
				EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
						.createInstance(BpmnModelClass.EXTN_JMS_CONTEXT_PARAMETER);
				createInstance.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME,entry.getKey().substring(W3C_JNDI_CONTEXT_PARAMETER_PREFIX.length(), entry.getKey().length()));
				createInstance.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VALUE, entry.getValue());
				lstJndiContextParameterURI.add(createInstance.getEInstance());
			}
			
		}
		
		if(mapjmsPropURI.containsKey(W3C_MESSAGE_FORMAT_ATTRIBUT)){
			String msgFormat=mapjmsPropURI.get(W3C_MESSAGE_FORMAT_ATTRIBUT).trim();
			jmsConfigURI
			.put(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,msgFormat);
				
		}
		
		if(mapjmsPropURI.containsKey(W3C_JNDI_URL)){
			String jndiURL=mapjmsPropURI.get(W3C_JNDI_URL).trim();
				jmsConfigURI
				.put(
						BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL,
						jndiURL);

		}
		//
		if(mapjmsPropURI.containsKey(W3C_DELIVERY_MODE)){
			String deliveryMode=mapjmsPropURI.get(W3C_DELIVERY_MODE).trim();
			jmsConfigURI
					.put(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE,
							deliveryMode);
		}
		
		if(mapjmsPropURI.containsKey(W3C_DESTINATION_NAME)){
			String destinationName=mapjmsPropURI.get(W3C_DESTINATION_NAME).trim();
				jmsConfigURI
						.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME,
								destinationName);
		}
		
		if(mapjmsPropURI.containsKey(W3C_INITIAL_CONTEXT_FACTORY)){
			String jndiInitialContextFactory=mapjmsPropURI.get(W3C_INITIAL_CONTEXT_FACTORY).trim();
				jmsConfigURI
						.put(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY,
								jndiInitialContextFactory);
		}
		
		if(mapjmsPropURI.containsKey(W3C_TTL_SERVICE)){
			String timeToLive=mapjmsPropURI.get(W3C_TTL_SERVICE).trim();
				jmsConfigURI
						.put(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE,
								timeToLive);
		}
		
		if(mapjmsPropURI.containsKey(W3C_PRIORITY)){
			String timeToLive=mapjmsPropURI.get(W3C_PRIORITY).trim();
				jmsConfigURI
						.put(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY,
								timeToLive);
			
		}
		
		if(mapjmsPropURI.containsKey(W3C_REPLY_TO_NAME)){
			String replyToName=mapjmsPropURI.get(W3C_REPLY_TO_NAME).trim();
				jmsConfigURI
						.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION,
								replyToName);
			
		}
		
	}
	
	
	private void fetchW3CTransportPropertiesFromExtensionPoint(WsExtensionPoint service, Map<String,String> jmsConfig,List<EObject> jndiContextParam){
		

		//jndiContextParameter 
		WsExtensionElement extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_JNDI_CONTEXT_PARAMETER));
		Iterator<?> extensionElements = service
				.getExtensionElements(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_JNDI_CONTEXT_PARAMETER));
		jndiContextParam.clear();
		while (extensionElements.hasNext()){
			extensionElement = (WsExtensionElement) extensionElements.next();
			if (extensionElement.getContent() instanceof XiNode){
				XiNode node=extensionElement.getContent();
				if (node.getName() != null && node.getName()
						.getLocalName()
						.equals(W3C_JNDI_CONTEXT_PARAMETER)){
					
						String name = node
								.getAttributeStringValue(ExpandedName
									.makeName(TIBCO_JNDI_CONTEXTPROPERTY_NAME_ATTRIBUTE));
						String factory = node.getAttributeStringValue(ExpandedName
									.makeName(TIBCO_JMS_VALUE_ATTRIBUTE));
						String type = node
								.getAttributeStringValue(ExpandedName
										.makeName(TIBCO_JNDI_CONTEXTPROPERTY_TYPE_ATTRIBUTE));
						
						
						EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
								.createInstance(BpmnModelClass.EXTN_JMS_CONTEXT_PARAMETER);
						createInstance.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME,name);
						createInstance.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VALUE, factory);
						createInstance.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE, type);
						
						if(name != null && !name.isEmpty() && factory != null && !factory.isEmpty())
							jndiContextParam.add(createInstance.getEInstance());
						
					}
				}
			}

		extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI, W3C_JNDI_CONTEXT_PARAMETER));
					
		
		//msgFormat
		 extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_MESSAGE_FORMAT_ATTRIBUT));
		if (extensionElement != null) {
			String msgFormat = extensionElement.getContent()
					.getStringValue();
					jmsConfig
					.put(BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,	msgFormat);
		}
		
		//jndiConnectionFactoryName
		extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_CONNECTION_FACTORY));
		if (extensionElement != null) {
			String connectionFactory = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME,connectionFactory);
			}

		
		//jndiURL
		extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_JNDI_URL));
		if (extensionElement != null) {
			String jndiURL = extensionElement.getContent()
					.getStringValue();
			jmsConfig.put(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL,jndiURL);

		} 
		//delivery mode  
		extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_W3C_URI,
						W3C_DELIVERY_MODE));
		if (extensionElement != null) {
			String deliveryMode = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_DELIVERY_MODE,deliveryMode);

		}
		//destinationName
		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_W3C_URI,
						W3C_DESTINATION_NAME));
		if (extensionElement != null) {
			String destinationName = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME,
								destinationName);

		}
		
		//jndiInitialContextFactory
		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_W3C_URI,
						W3C_INITIAL_CONTEXT_FACTORY));
		if (extensionElement != null) {
			String jndiInitialContextFactory = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY,
								jndiInitialContextFactory);

		}
		
		//timetolive
		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_W3C_URI,
						W3C_TTL_SERVICE));
		if (extensionElement != null) {
			String timetolive = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_TIME_TO_LIVE,
								timetolive);
		}
		
		//priority 
		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_W3C_URI,
						W3C_PRIORITY));
		if (extensionElement != null) {
			String priority = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_PRIORITY,
								priority);

		}
		
		//replyToName 
		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_W3C_URI,
						W3C_REPLY_TO_NAME));
		if (extensionElement != null) {
			String replyToName = extensionElement.getContent()
					.getStringValue();
				jmsConfig
						.put(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION,
								replyToName);

		}
		
		
	}
	
	private void fetchTibcoJmsTransportPropertiesFromService(
			String serviceName, String portName,
			EObjectWrapper<EClass, EObject> jmsConfig) {
		if(wsdl != null){
			Iterator<?> iter = wsdl.getServices();
			while (iter.hasNext()) {
				WsService service = (WsService) iter.next();
				if (service.getLocalName().equals(serviceName)) {
					fetchTransportPropertiesFromExtensionPoint(service, jmsConfig);
					Iterator<?> ports = service.getPorts();
					while (ports.hasNext()) {
						WsPort next = (WsPort)ports.next();
						if(portName.equals(next.getLocalName())){
							fetchTransportPropertiesFromExtensionPoint(next, jmsConfig);
						}
					}
				}

			}
		}
		
	}
	

	
	private void fetchTransportPropertiesFromExtensionPoint(WsExtensionPoint service, EObjectWrapper<EClass, EObject> jmsConfig){
		WsExtensionElement extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_TIBCO_URI,WsdlConstants.BINDING_ELEMENT));
		if (extensionElement != null) {
			String msgFormat = extensionElement.getContent().getAttributeStringValue(ExpandedName.makeName(TIBCO_MESSAGE_FORMAT_ATTRIBUTE));
			if (msgFormat != null
					&& !msgFormat.trim().isEmpty()) {
				if(msgFormat.equalsIgnoreCase("text")){
					jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,
							BpmnModelClass.ENUM_MESSAGE_FORMAT_TEXT);
				}else {
					jmsConfig
					.setAttribute(
							BpmnMetaModelExtensionConstants.E_ATTR_MESSAGE_FORMAT,
							BpmnModelClass.ENUM_MESSAGE_FORMAT_BYTES);
				}
				
			}
		}
		extensionElement = service
				.getExtensionElement(ExpandedName.makeName(
						JMS_TIBCO_URI,
						TIBCO_JMS_CONNECTIONFACTORY_EXT_TAG));
		if (extensionElement != null) {
			String connectionFactory = extensionElement.getContent()
					.getStringValue();
			if (connectionFactory != null
					&& !connectionFactory.trim().isEmpty()) {
				jmsConfig
						.setAttribute(
								BpmnMetaModelExtensionConstants.E_ATTR_JNDI_CONNECTION_FACTORY_NAME,
								connectionFactory);
			}

		}

		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JMS_TIBCO_URI,
						TIBCO_JMS_TARGET_ADDRESS_EXT_TAG));
		if (extensionElement != null) {
			String destinationName = extensionElement.getContent()
					.getStringValue();
			if (destinationName != null
					&& !destinationName.trim().isEmpty()) {
				jmsConfig
						.setAttribute(
								BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION_NAME,
								destinationName);
			}

		}

		extensionElement = service.getExtensionElement(ExpandedName
				.makeName(JNDI_TIBCO_URI, TIBCO_JNDI_CONTEXT_EXT_TAG));
		if (extensionElement != null) {
			XiNode context = extensionElement.getContent();
			Iterator<?> children = context.getChildren();
			while (children.hasNext()) {
				Object object = (Object) children.next();
				if (object instanceof XiNode) {
					XiNode node = (XiNode) object;
					if (node.getName() != null && node.getName()
							.getLocalName()
							.equals(TIBCO_JNDI_CONTEXTPROPERTY_ELEMENT_TAG)) {
						String name = node
								.getAttributeStringValue(ExpandedName
										.makeName(TIBCO_JNDI_CONTEXTPROPERTY_NAME_ATTRIBUTE));
						if (name.trim().equals(JNDI_PROVIDER_URL)) {
							String url = node.getStringValue();
							if (url != null & !url.isEmpty()) {
								jmsConfig
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL,
												url);
							}
						} else if (name.trim().equals(
								JNDI_INITIAL_CONTEXTFACTORY)) {
							String factory = node.getStringValue();
							if (factory != null & !factory.isEmpty()) {
								jmsConfig
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_CONTEXT_FACTORY,
												factory);
							}
						} else {
							String factory = node.getStringValue();
							if (factory != null & !factory.isEmpty()) {
								EObjectWrapper<EClass, EObject> createInstance = EObjectWrapper
										.createInstance(BpmnModelClass.EXTN_JMS_CONTEXT_PARAMETER);
								createInstance
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_NAME,
												name);
								createInstance
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_VALUE,
												factory);
								String type = node
										.getAttributeStringValue(ExpandedName
												.makeName(TIBCO_JNDI_CONTEXTPROPERTY_TYPE_ATTRIBUTE));
								if (type != null && !type.isEmpty())
									createInstance
											.setAttribute(
													BpmnMetaModelExtensionConstants.E_ATTR_TYPE,
													factory);

								jmsConfig
										.addToListAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROPS,
												createInstance
														.getEInstance());
							}
						}
					}
				}
			}

		}
	}

	public WsWsdl getWsdl() {
		return wsdl;
	}
	

}
