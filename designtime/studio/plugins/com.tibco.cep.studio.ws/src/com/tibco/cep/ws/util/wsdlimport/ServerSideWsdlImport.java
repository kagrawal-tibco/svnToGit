package com.tibco.cep.ws.util.wsdlimport;

import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.be.util.wsdl.SOAPEventPayloadBuilder;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
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
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.StudioWorkbenchConstants;
import com.tibco.cep.ws.util.WSDLImportUtil;
import com.tibco.cep.ws.util.wsdlimport.channel.WsdlChannelBuilder;
import com.tibco.cep.ws.util.wsdlimport.channel.WsdlChannelBuilderFactory;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.cep.ws.util.wsdlimport.context.ServiceContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapBindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapOperationReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.WsdlContext;
import com.tibco.xml.NamespaceImporter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.ws.wsdl.WsBinding;
import com.tibco.xml.ws.wsdl.WsExtensionElement;
import com.tibco.xml.ws.wsdl.WsMessage;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsMessagePart;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.WsService;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimeMultipartRelated;
import com.tibco.xml.ws.wsdl.ext.mime.WsMimePart;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBinding;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapBody;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapHeader;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapOperation;
import com.tibco.xml.ws.wsdl.ext.soap11.WsSoapStyle;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapBody_12;
import com.tibco.xml.ws.wsdl.ext.soap12.WsSoapHeader_12;


public class ServerSideWsdlImport
	extends DefaultWsdlImport
{

	private static final String EVENTS_FOLDER_NAME = "Events";
	private static final String INPUT_EVENT_ARG_NAME = "soapeventin";
	private static final String RULEFUNCTIONS_FOLDER_NAME = "RuleFunctions";
	private static final String RULES_FOLDER_NAME = "Rules";
	private static final String TRANSPORT_FOLDER_NAME = "Transport";


	private final IProject project;
	private final WsdlChannelBuilderFactory channelBuilderFactory;
	
	
	public ServerSideWsdlImport(
			WsWsdl wsdl,
			IProject prj)
			throws Exception
	{
		super(wsdl);

		this.project = prj;		
		this.channelBuilderFactory = new WsdlChannelBuilderFactory(prj, TRANSPORT_FOLDER_NAME);
	}


	private SimpleEvent createEvent(
			String eventFolderPath,
			String eventName,
			SOAPEventPayloadBuilder builder)
			throws Exception
	{		
		final String payloadString = XiSerializer.serialize(builder.getPayloadElement());
		
		SimpleEvent event = this.findSoapEvent(eventFolderPath, eventName, payloadString);
		if (null == event) {
			event = WSDLImportUtil.createSimpleEvent(
					this.project.getName(),
					eventName,
					eventFolderPath + "/",
					eventFolderPath + "/");
			event.setDestinationName("");
			event.setPayloadString(payloadString);
			event.setSuperEventPath(RDFTypes.SOAP_EVENT.getName());
			
			final NamespaceImporter nsImporter = builder.getNamespaceImporter();
			final EList<NamespaceEntry> nsEntries = event.getNamespaceEntries();
			for (@SuppressWarnings("unchecked")
					final Iterator<String> it = nsImporter.getPrefixes(); it.hasNext(); ) {
				final String prefix = it.next();
				final NamespaceEntry entry = EventFactory.eINSTANCE.createNamespaceEntry();
				entry.setPrefix(prefix);
				entry.setNamespace(nsImporter.getNamespaceURIForPrefix(prefix));							
				nsEntries.add(entry);
			}

			((ServerSideWsdlImportResult) this.result).getCreatedEntities().add(event);		
		}
		
		return event;
	}
	
	
	private Symbols createSymbols(
			String name,
			String type)
	{
		final Symbols symbols = RuleFactory.eINSTANCE.createSymbols();
		final Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
		symbol.setIdName(name);
		symbol.setType(type);
		symbols.getSymbolMap().put(name, symbol);
		return symbols;
	}
	
	
	private boolean defineChannel(
			SoapBindingContext soapBindingContext)
			throws Exception
	{
		final WsSoapBinding soapBinding = soapBindingContext.getSoapBinding();
		if (null == soapBinding) {
			return false;
		}

		final String transport = soapBinding.getTransport();		

		@SuppressWarnings("unchecked")
		final Map<String, WsdlChannelBuilder> transportToChannelBuilder =
				(Map<String, WsdlChannelBuilder>)
				soapBindingContext.getPortContext().getServiceContext().getWsdlContext()
				.getProperties().get("channelBuilders");	
		
		WsdlChannelBuilder channelBuilder = transportToChannelBuilder.get(transport);
		if (null == channelBuilder) {
				channelBuilder = this.channelBuilderFactory.make(transport);
				if (null == channelBuilder) {
					this.storeError("Info: WSDL Import does not support transport "
							+ transport
							+ "\nport=" + soapBindingContext.getPortContext().getPort().getLocalName()
							+ "\nbinding=" + soapBindingContext.getBinding().getLocalName()
							+ " is skipped.");
					return false;
				} else {
					transportToChannelBuilder.put(transport, channelBuilder);
				}
		}
		
		soapBindingContext.getProperties().put("channelBuilder", channelBuilder);
		
		channelBuilder.processBinding(soapBindingContext);
		return true;
	}

	
	@Override
	public ServerSideWsdlImportResult execute()
			throws Exception
	{
		return (ServerSideWsdlImportResult) super.execute();
	}


	private SimpleEvent findSoapEvent(
			String eventFolderPath,
			String eventName,
			String payloadString)
	{
		final String soapEventTypeName = RDFTypes.SOAP_EVENT.getName();		
		final String extension = StudioResourceUtils.getExtensionFor(StudioWorkbenchConstants._WIZARD_TYPE_NAME_EVENT);
		final ServerSideWsdlImportResult result = (ServerSideWsdlImportResult) this.result;

		SimpleEvent event = null;
		int i = 0;
		String name = eventName;
		do {
			final String fullPath = eventFolderPath + "/" + name; 
			
			for (final Entity entity: result.getCreatedEntities()) {			
				if ((entity instanceof SimpleEvent)
						&& entity.getFullPath().equals(fullPath)) {
					event = (SimpleEvent) entity;
					break;
				}
			}
			
			if (null == event) {
				event = (SimpleEvent) CommonIndexUtils.loadEObject(
					this.project.getFile(fullPath + extension).getLocationURI(),
					false);
			}
			
			name = eventName + "_" + (++i);
		} while (!(
				(null == event)
				|| soapEventTypeName.equals(event.getSuperEventPath())
				|| payloadString.equals(event.getPayloadString())
		));
		
		return event;
	}
	
	
	private String getPortTypeNameFolderPath(
			String serviceFoldername,
			String portTypeName)
	{
		return "/" + serviceFoldername + "/" + portTypeName;	
	}

	
	private String getRulesFolderPath(
			String portTypeFolderPath,
			String portTypeName)
	{
		return portTypeFolderPath + "/" + RULES_FOLDER_NAME + "/" + portTypeName;
	}

	
	private String getRulesFnFolderPath(
			String portTypeFolderPath)
	{
		return portTypeFolderPath + "/" + RULEFUNCTIONS_FOLDER_NAME;
	}

	
	private String getEventFolderPath(
			String portTypeFolderPath)
	{
		return portTypeFolderPath + "/" + EVENTS_FOLDER_NAME;
	}
	
	
	public RuleFunction getOrCreateEventRegistrationRuleFunction(
			ServiceContext serviceContext)
	{		
		final Properties props = serviceContext.getProperties();

		RuleFunction rf = (RuleFunction) props.get("eventRegistrationRuleFunction");

		if (null == rf) {
			final String folderPath = new StringBuilder("/")
				.append(WSDLImportUtil.makeValidName(serviceContext.getService().getLocalName()))
				.append("/")
				.append(TRANSPORT_FOLDER_NAME)
				.toString();			;
				
			rf = WSDLImportUtil.createRuleFunction(
					this.project.getName(),
					"RegisterSoapEventUris",
					folderPath + "/",
					folderPath);
			
			rf.setActionText("String eventUri, destination, service, soapAction, preprocessor;");
			rf.setDescription("Generated during WSDL import. "
					+ "This function registers specific event types for use in some SOAP deserializers. ");
			rf.setReturnType(null);
			
			((ServerSideWsdlImportResult) this.result).getCreatedEntities().add(rf);
			
			props.put("eventRegistrationRuleFunction", rf);
		}

		return rf;
	}


	@Override
	protected void importBinding(
			SoapBindingContext bindingContext)
			throws Exception
	{	
		if (null == bindingContext) {
			return;
		}
		
		super.importBinding(bindingContext);
				
        ((ServerSideWsdlImportResult) this.result).getProcessedTypes().add(
                bindingContext.getBinding().getPortType());
	}

	
//	@Override
//	protected void importMessageReferences(
//			OperationReferenceContext opRefContext)
//			throws Exception
//	{
////TODO
////		final SOAPEventPayloadBuilder payloadBuilder = new SOAPEventPayloadBuilder();
////		opRefContext.getProperties().put("payloadBuilder", payloadBuilder);
//		
//		super.importMessageReferences(opRefContext);		
//
//	}

	
	@Override
	protected void importOperation(
			OperationContext opContext)
			throws Exception
	{
		if (null == opContext) {
			return;
		}
		
		final SoapOperationReferenceContext opRefContext =
				(SoapOperationReferenceContext)
				opContext.getOperationReferenceContext();		
		final WsSoapOperation soapOp = opRefContext.getSoapOperation();		
		final BindingContext bindingContext = opRefContext.getBindingContext();
		final Properties bindingProps = bindingContext.getProperties();

		if ((soapOp.getStyle() != WsSoapStyle.NONE)
				&& (soapOp.getStyle() != WsSoapStyle.DOCUMENT)) { //TODO RPC
			this.storeError(
					"Info: WSDL Import - style '"
					+ soapOp.getStyle()
					+ "' is not supported. Operation binding is skipped. type="
					+ bindingContext.getBinding().getPortType().getLocalName()
					+ " binding="
					+ bindingContext.getBinding().getLocalName()
					+ " operation="
					+ opContext.getOperation().getLocalName());
			return;
		}
						
		final String eventFolderPath = bindingProps.getProperty("eventFolder");
		
		final SimpleEvent inputEvent = this.createEvent(
				eventFolderPath, opRefContext.getOperationReference(), WsMessageKind.INPUT);

		final SimpleEvent outputEvent = this.createEvent(
				eventFolderPath, opRefContext.getOperationReference(), WsMessageKind.OUTPUT);

		final String opName = opContext.getOperation().getLocalName(); 
		final RuleFunction ruleFn = this.makeRuleFunction(opName, bindingProps, inputEvent, outputEvent);

        if (null != inputEvent) {
            PropertyDefinition property = inputEvent.getPropertyDefinition("JMSCorrelationID");
            if (null == property) {
            	property = ElementFactory.eINSTANCE.createPropertyDefinition();
            	property.setOwnerProjectName(project.getName());
            	property.setOwnerPath(inputEvent.getFullPath());
            	property.setName("JMSCorrelationID");
            	inputEvent.getProperties().add(property);
            }
            property = inputEvent.getPropertyDefinition("JMSReplyTo");
            if (null == property) {
            	property = ElementFactory.eINSTANCE.createPropertyDefinition();
            	property.setName("JMSReplyTo");
            	property.setOwnerProjectName(project.getName());
            	property.setOwnerPath(inputEvent.getFullPath());
            	inputEvent.getProperties().add(property);
            }
            this.makeOnRequestRule(opName, bindingProps, inputEvent, outputEvent);
        }
        if (null != outputEvent) {
            PropertyDefinition property = outputEvent.getPropertyDefinition("JMSCorrelationID");
            if (null == property) {
            	property = ElementFactory.eINSTANCE.createPropertyDefinition();
            	property.setOwnerProjectName(project.getName());
            	property.setOwnerPath(inputEvent.getFullPath());
            	property.setName("JMSCorrelationID");
            	outputEvent.getProperties().add(property);
            }
            this.makeOnResponseRule(opName, bindingProps, outputEvent);
        }

		final String soapAction = soapOp.getSoapAction();
		if ((soapAction != null) && !soapAction.isEmpty()) {
			final String rfPath = ruleFn.getFullPath();
			final Destination[] destinations = ((WsdlChannelBuilder) bindingProps.get("channelBuilder"))
					.processOperation(opContext, soapAction, inputEvent, outputEvent, rfPath);

			if ((null != destinations) && (destinations.length > 0)) {
			    int i=0;
			    final ServiceContext serviceContext = bindingContext.getPortContext().getServiceContext();
                final String portTypeName = bindingContext.getBinding().getPortType().getLocalName();
			    for (final Destination destination : destinations) {
			        i++;
			        if (null != destination) {
        	            final String destinationPath = destination.getFullPath();
        				final int lastSlash = destinationPath.lastIndexOf('/'); 
                        if (1 == i) {
                            if ((null != inputEvent) && (lastSlash >= 0)) {
            					inputEvent.setChannelURI(destinationPath.substring(0, lastSlash));
            					inputEvent.setDestinationName(destinationPath.substring(lastSlash + 1));
                                this.updateEventRegistrationRuleFunction(
                                        serviceContext,
                                        true,
                                        portTypeName,
                                        opName,
                                        inputEvent.getFullPath(),
                                        destinationPath,                    
                                        soapAction,
                                        rfPath);    
                            }
        				} else if ((null != outputEvent) && (lastSlash >= 0)) {
                            outputEvent.setChannelURI(destinationPath.substring(0, lastSlash));
                            outputEvent.setDestinationName(destinationPath.substring(lastSlash + 1));        				    
                            this.updateEventRegistrationRuleFunction(
                                    serviceContext,
                                    false,
                                    portTypeName,
                                    opName,
                                    outputEvent.getFullPath(),
                                    destinationPath,                    
                                    soapAction,
                                    rfPath);    
        				} 
			        }
    			}
			}
		}
	}
	

	@Override
	protected void importWsdl(
			WsdlContext wsdlContext)
			throws Exception
	{	
		if (null == wsdlContext) {
			return;
		}
		
		final Map<String, WsdlChannelBuilder> transportToChannelBuilder = new HashMap<String, WsdlChannelBuilder>();
		wsdlContext.getProperties().put("channelBuilders", transportToChannelBuilder);
		
		super.importWsdl(wsdlContext);
		
		final ServerSideWsdlImportResult result = (ServerSideWsdlImportResult) this.result;
		for (final WsdlChannelBuilder channelBuilder : transportToChannelBuilder.values()) {			
            result.getCreatedEntities().addAll(channelBuilder.getCreatedEntities());        
            result.getModifiedEntities().addAll(channelBuilder.getModifiedEntities());        
            result.getSharedResources().add(channelBuilder.getSharedResources());
		}
	}

	
	@Override
	protected void initializeResults()
			throws Exception
	{
		this.result = new ServerSideWsdlImportResult();
	}

	
	@Override
	protected SoapBindingContext makeBindingContext(
			PortContext portContext,
			WsBinding binding)
			throws Exception
	{
		final SoapBindingContext ctx = super.makeBindingContext(portContext, binding);
		
		if (!this.defineChannel(ctx)) {
			return null;
		}		

		final String portTypeName = binding.getPortType().getLocalName();
		final String portTypeFolderPath = this.getPortTypeNameFolderPath(
				portContext.getServiceContext().getProperties().getProperty("folder"),
				portTypeName);		
		final RuleSet ruleset = WSDLImportUtil.createRuleSet(); //TODO check if already exists?
	    ((ServerSideWsdlImportResult) this.result).getCreatedEntities().add(ruleset);        

        final Properties p = ctx.getProperties();   
		p.put("ruleset", ruleset);			
		p.setProperty("rulesFolder", this.getRulesFolderPath(portTypeFolderPath, portTypeName));
		p.setProperty("ruleFnFolder", this.getRulesFnFolderPath(portTypeFolderPath));
		p.setProperty("eventFolder", this.getEventFolderPath(portTypeFolderPath));

		return ctx;		
	}		
		

//	@Override
//	protected PortContext makePortContext(
//			ServiceContext serviceContext,
//			WsPort port)
//			throws Exception
//	{
//		final PortContext ctx = super.makePortContext(serviceContext, port);
//		
//		return ctx;
//	}


    private Rule makeOnRequestRule(
            String opName,
            Properties bindingProps,
            SimpleEvent inputEvent,
            SimpleEvent outputEvent)
    {
        final String rulesFolderPath = bindingProps.getProperty("rulesFolder");
        final Rule rule = WSDLImportUtil.createRule(  //TODO check if already exists
                this.project.getName(), opName,
                rulesFolderPath + "/", rulesFolderPath);

        final String eventPath = (null != outputEvent)
                ? outputEvent.getFullPath()
                : inputEvent.getFullPath();

        rule.setActionText("// TODO - implement reply"
                + "\nLog.log(Log.getLogger(\"SOAP\"), \"info\", \"Operation: %s\", \""
                + StringEscapeUtils.escapeJava(opName) + "\");"
                + "\nEvent reply = Event.createEvent(\"xslt://{{"
                + eventPath
                + "}}<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n<xsl:stylesheet xmlns:ns1=\\\""
                + TypeManager.DEFAULT_BE_NAMESPACE_URI
                + eventPath
                + "\\\" xmlns:xsl=\\\"http://www.w3.org/1999/XSL/Transform\\\" xmlns:ns=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" "
                + "xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\" version=\\\"1.0\\\" exclude-result-prefixes=\\\"xsl xsd\\\">"
                + "<xsl:output method=\\\"xml\\\"/><xsl:param name=\\\""
                + INPUT_EVENT_ARG_NAME
                + "\\\"/><xsl:template match=\\\"/\\\"><createEvent><event>"
                + "<xsl:if test=\\\"$"
                + INPUT_EVENT_ARG_NAME
                + "/JMSCorrelationID\\\"><JMSCorrelationID><xsl:value-of select=\\\"$"
                + INPUT_EVENT_ARG_NAME
                + "/JMSCorrelationID\\\"/></JMSCorrelationID></xsl:if>"
                + "<payload><ns1:message><ns:Envelope><ns:Body><ns:Fault>"
                + "<faultcode><xsl:value-of select=\\\"\\'SOAP-ENV:Server\\'\\\"/></faultcode>"
                + "<faultstring><xsl:value-of select=\\\"\\'Not implemented: "
                + StringEscapeUtils.escapeJava(opName)
                + "\\'\\\"/></faultstring></ns:Fault></ns:Body></ns:Envelope></ns1:message></payload></event>"
                + "</createEvent></xsl:template>\\n</xsl:stylesheet>\");"
                + "\nEvent.replyEvent("
                + INPUT_EVENT_ARG_NAME
                + ", reply);"
                + "\nEvent.consumeEvent("
                + INPUT_EVENT_ARG_NAME
                + ");");
        rule.setAuthor("WsdlImport");
        rule.setSymbols(this.createSymbols(INPUT_EVENT_ARG_NAME, inputEvent.getFullPath()));

        final RuleSet ruleset = ((RuleSet) bindingProps.get("ruleset"));
        ruleset.getRules().add(rule);

        final ServerSideWsdlImportResult result = (ServerSideWsdlImportResult) this.result;
        result.getModifiedEntities().add(ruleset);
        result.getCreatedEntities().add(rule);

        return rule;
    }


    private Rule makeOnResponseRule(
			String opName,
			Properties bindingProps,
            SimpleEvent outputEvent)
	{
        final String eventParamName = "response"; 
        final String eventPath = outputEvent.getFullPath(); 
        final String rulesFolderPath = bindingProps.getProperty("rulesFolder");
		final Rule rule = WSDLImportUtil.createRule(  //TODO check if already exists
				this.project.getName(), opName + "_Response",
				rulesFolderPath + "/", rulesFolderPath);	

		rule.setActionText("\nLog.log(Log.getLogger(\"SOAP\"), \"info\", \"Response %s to operation: "
                + StringEscapeUtils.escapeJava(opName) + "\", " + eventParamName + "@id);"
				+ "// TODO - implement"
                + "\nEvent.consumeEvent(" + eventParamName+ ");");
		rule.setAuthor("WsdlImport");
    	rule.setSymbols(this.createSymbols(eventParamName, eventPath));

		final RuleSet ruleset = ((RuleSet) bindingProps.get("ruleset")); 
		ruleset.getRules().add(rule);

        final ServerSideWsdlImportResult result = (ServerSideWsdlImportResult) this.result;
        result.getModifiedEntities().add(ruleset);
        result.getCreatedEntities().add(rule);
        
		return rule;
	}


	private RuleFunction makeRuleFunction(
			String opName,
			Properties bindingProps,
			Event inputEvent,
			Event outputEvent)
	{
		final String ruleFnFolderPath = bindingProps.getProperty("ruleFnFolder");
		final RuleFunction ruleFn = WSDLImportUtil.createRuleFunction(//TODO check if already exists
				this.project.getName(), opName,
				ruleFnFolderPath + "/", ruleFnFolderPath);
		
		if (null != inputEvent) {
			ruleFn.setSymbols(this.createSymbols(INPUT_EVENT_ARG_NAME, inputEvent.getFullPath()));
		}

		if (null != outputEvent) {
			ruleFn.setReturnType(outputEvent.getFullPath());
			ruleFn.setActionText("return null;");
		} else {
			ruleFn.setReturnType(null);
		}

		((ServerSideWsdlImportResult) this.result).getCreatedEntities().add(ruleFn);
		return ruleFn;
	}

	
	@Override
	protected ServiceContext makeServiceContext(
			WsdlContext wsdlContext,
			WsService service)
			throws Exception
	{
		final ServiceContext ctx = super.makeServiceContext(wsdlContext, service);
		
		ctx.getProperties().setProperty(
			"folder",
			BEStringUtilities.convertToValidTibcoIdentifier(
					ctx.getService().getLocalName(), false));
		
		this.getOrCreateEventRegistrationRuleFunction(ctx);
		
		//TODO JMS properties?
		
		return ctx;
	}

		
	private void updateEventRegistrationRuleFunction(//TODO
			ServiceContext serviceContext,
			boolean input,
            String portType,
            String operation,
			String eventUri,
			String destinationPath,
			String soapAction,
			String preprocessor)
	{
		final RuleFunction rf = this.getOrCreateEventRegistrationRuleFunction(serviceContext);

		((ServerSideWsdlImportResult) this.result).getModifiedEntities().add(rf);
		
		rf.setActionText(new StringBuilder(rf.getActionText())
            .append("\n\n// ")
            .append(input ? "input" : "output")
            .append(" for [")
            .append(StringEscapeUtils.escapeJava(operation))
            .append("] @ [")
            .append(StringEscapeUtils.escapeJava(portType))
            .append("]\nservice = \"")
			.append(StringEscapeUtils.escapeJava(serviceContext.getService().getLocalName()))
			.append("\";\neventUri = \"")
			.append(eventUri)
			.append("\";\ndestination = \"")
			.append(destinationPath)
			.append("\";\nsoapAction = \"")
			.append(StringEscapeUtils.escapeJava(soapAction))
			.append("\";\npreprocessor = \"")
			.append(StringEscapeUtils.escapeJava(preprocessor))
			.append("\";\nSOAP.registerEventUri(eventUri, destination, service, soapAction, preprocessor);")
			.append("\nSOAP.registerEventUri(eventUri, destination, \"\",      soapAction, preprocessor);")
			.toString());
	}

	
	
	
	
	
	
	
	
	
	//TODO clean-up the mess below -----------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private SimpleEvent createEvent(
			String eventFolderPath,
			WsOperationReference opRef,
			WsMessageKind kind)
			throws Exception {
				
		String eventName = "";
		WsOperationMessage opMsg = null;
		final List<?> msgRefs = this.getOperationMessage(opRef, kind);
		if (msgRefs.isEmpty()) {
			final WsOperationMessage[] opMsgs = opRef.getOperation().getMessages(kind);
			if (opMsgs.length > 0) {
				opMsg = opMsgs[0];
				eventName = opMsg.getLocalName();
				if ((eventName == null) || eventName.isEmpty()) {
					eventName = opMsg.getMessage().getLocalName();
				}
			}
		} else {
			opMsg = (WsOperationMessage)((WsOperationMessageReference)msgRefs.get(0)).getMessage();
			eventName = opMsg.getName().getLocalName();
			if (eventName == null || "".equals(eventName)) {
				eventName = opMsg.getMessage().getLocalName();
			}
		}

		if (opMsg == null) {
			return null;
		}

		final SOAPEventPayloadBuilder payloadBuilder = new SOAPEventPayloadBuilder();
		if (!msgRefs.isEmpty()) {
			this.handleWsOperationMessageReference(
					(WsOperationMessageReference)msgRefs.get(0), payloadBuilder);
		} else if (opMsg != null) {
			for (final Iterator<?> i = opMsg.getMessage().getMessageParts(); i.hasNext(); ) {
				this.addMsgAsBodyPart(
						(WsMessagePart)i.next(), payloadBuilder);
			}
		}

		// add fault message parts to payload
		if (kind == WsMessageKind.OUTPUT) {
			List<WsOperationMessageReference> opFaultMsgRefList = getOperationMessage(
					opRef, WsMessageKind.FAULT);
			if (opFaultMsgRefList.size() > 0) {
				for (WsOperationMessageReference opFaultMsgRef : opFaultMsgRefList) {
					ExpandedName faultMsgName = opFaultMsgRef.getName();
					WsOperationMessage opFaultMsg = opRef.getOperation()
							.getMessage(faultMsgName);
					Iterator<?> msgPartIter = opFaultMsg
							.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart((WsMessagePart) msgPartIter.next(), payloadBuilder);
					}
				}

			} else {
				WsOperationMessage[] opMsgs = opRef.getOperation().getMessages(
						WsMessageKind.FAULT);
				for (int i = 0; i < opMsgs.length; i++) {
					WsOperationMessage opFaultMsg = opMsgs[i];
					Iterator<?> msgPartIter = opFaultMsg
							.getMessage().getMessageParts();
					while (msgPartIter.hasNext()) {
						addMsgAsFaultPart((WsMessagePart) msgPartIter.next(), payloadBuilder);
					}
				}
			}
		}

		return createEvent(eventFolderPath, eventName, payloadBuilder);
	}
	
	
//	private Event createEvent(
//			String folder,
//			WsOperation opn,
//			WsMessageKind kind)
//			throws Exception
//	{
//		final WsOperationMessage[] opMsgs = opn.getMessages(kind);
//		if (opMsgs.length <= 0) {
//			return null;
//		}
//		
//		final WsOperationMessage opMsg = opMsgs[0];
//		final WsMessage msg = opMsg.getMessage();
//		String eventName = opMsg.getLocalName();
//		if (eventName == null || "".equals(eventName)) {
//			eventName = msg.getLocalName();
//		}
//
//		final SOAPEventPayloadBuilder payloadBuilder = new SOAPEventPayloadBuilder();
//		for (final Iterator<?> i = msg.getMessageParts(); i.hasNext(); ) {
//			this.addMsgAsBodyPart((WsMessagePart) i.next(), payloadBuilder);
//		}
//
//		if (kind == WsMessageKind.OUTPUT) {
//			final WsOperationMessage[] faultMsgs = opn.getMessages(WsMessageKind.FAULT);
//			if (faultMsgs.length > 0) {
//				for (final Iterator<?> j = faultMsgs[0].getMessage().getMessageParts();
//						j.hasNext();) {
//					this.addMsgAsFaultPart((WsMessagePart) j.next(), payloadBuilder);//TODO
//				}
//			}
//		}
//
//		return this.createEvent(folder, eventName, payloadBuilder);
//	}
	
	
//	private void importAbstract(WsWsdl wsdl) throws Exception {
//
//		final Set<WsPortType> processedTypes = ((ServerSideWsdlImportResult) result).getProcessedTypes();
//		final Set<Entity> createdEntities = ((ServerSideWsdlImportResult) result).getCreatedEntities();
//		
//		// Iterate over port type and its operation to create rule,rule-function
//		// Also create in, out and fault event for operation
//		Iterator<?> iter = wsdl.getPortTypes();
//		while (iter.hasNext()) {
//			WsPortType portType = (WsPortType) iter.next();
//			if (processedTypes.contains(portType)) {
//				continue;
//			}
//			String portTypeName = portType.getName().getLocalName();
//			String portTypeFolderPath = "/" + portTypeName;
//			String rulesFolderPath = getRulesFolderPath(portTypeFolderPath, portTypeName );
//			String ruleFnFolderPath = getRulesFnFolderPath(portTypeFolderPath);
//			String eventFolderPath = getEventFolderPath(portTypeFolderPath);
//			RuleSet ruleSet = WSDLImportUtil.createRuleSet();
//			ruleSet.setFolder(rulesFolderPath);
//			
//			final String projectName = this.project.getName();
//			
//			createdEntities.add(ruleSet);
//			// iterate over operation and create rule function and rules
//			Iterator<?> opnIter = portType.getOperations();			
//			while (opnIter.hasNext()) {
//				WsOperation opn = (WsOperation) opnIter.next();
//				String opName = opn.getLocalName(); 
//				RuleFunction ruleFn = WSDLImportUtil.createRuleFunction(
//						projectName, opName, ruleFnFolderPath + "/",
//						ruleFnFolderPath);
//
//				Rule rule = WSDLImportUtil.createRule(projectName, opName,
//						rulesFolderPath + "/", rulesFolderPath);
//
//				// defaults
//                rule.setReturnType(";");
//                final String dummyActionText = "// TODO - Provide Rule Action \n ;";
//                rule.setActionText(dummyActionText);
//                
//				createdEntities.add(ruleFn);
//				createdEntities.add(rule);
//			
//				Event inEvent = createEvent(eventFolderPath, opn,
//						WsMessageKind.INPUT);
//				if(inEvent != null){
//					Symbols createSymbols = RuleFactory.eINSTANCE.createSymbols();
//					Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
//					String inEventURI = inEvent.getFullPath();
//					symbol.setIdName(INPUT_EVENT_ARG_NAME);
//					symbol.setType(inEventURI);
//					createSymbols.getSymbolMap().put(INPUT_EVENT_ARG_NAME, symbol);
//					rule.setSymbols(createSymbols);
//
//					createSymbols = RuleFactory.eINSTANCE.createSymbols();
//					symbol = RuleFactory.eINSTANCE.createSymbol();
//					inEventURI = inEvent.getFullPath();
//					symbol.setIdName(INPUT_EVENT_ARG_NAME);
//					symbol.setType(inEventURI);
//					createSymbols.getSymbolMap().put(INPUT_EVENT_ARG_NAME, symbol);
//					ruleFn.setSymbols(createSymbols);
//					createdEntities.add(inEvent);
//				}
//
//				Event outEvent = createEvent(eventFolderPath, opn,
//						WsMessageKind.OUTPUT);
//				if (outEvent == null) {
//					ruleFn.setReturnType(null);
//				}else{
//					String eventURI = outEvent.getFullPath();
//					ruleFn.setReturnType(eventURI);
//					ruleFn.setActionText("return null;");
//					createdEntities.add(outEvent);
//				}
//
//			}
//		}
//	}




	private List<WsOperationMessageReference> getOperationMessage(WsOperationReference opRef,
			WsMessageKind kind) {
		List<WsOperationMessageReference> list = new ArrayList<WsOperationMessageReference>();
		Iterator<?> iter = opRef.getMessages();
		while (iter.hasNext()) {
			WsOperationMessageReference wsOpMsgRef = (WsOperationMessageReference)iter.next();
			if (wsOpMsgRef.getMessageKind() == kind) {
				list.add(wsOpMsgRef);
			}
		}
		return list;
	}

	private void handleWsOperationMessageReference(
			WsOperationMessageReference opMsgRef,
			SOAPEventPayloadBuilder builder) throws Exception {
		WsMessage msg = opMsgRef.getMessage().getMessage();
		Iterator<?> opMsgRefExtnIter = opMsgRef
				.getExtensionElements();
		while (opMsgRefExtnIter.hasNext()) {
			WsExtensionElement wsExtensionElement = (WsExtensionElement) opMsgRefExtnIter
					.next();
			if (wsExtensionElement instanceof WsSoapBody) {
				WsSoapBody soapBody = (WsSoapBody) wsExtensionElement;
				addSoapBodyPartsToPayload(soapBody, msg, builder);
			} else if ( wsExtensionElement instanceof WsSoapBody_12) {
				WsSoapBody_12 soapBody = (WsSoapBody_12) wsExtensionElement;
				addSoapBodyPartsToPayload(soapBody, msg, builder);
			} else if (wsExtensionElement instanceof WsSoapHeader) {
				WsSoapHeader soapHeader = (WsSoapHeader) wsExtensionElement;
				ExpandedName msgName = soapHeader.getMessage();
				if (msgName != null) {
					String part = soapHeader.getPart();
					if (part != null) {
						WsMessage message = opMsgRef.getWsdl().getMessage(
								msgName);
						WsMessagePart wsMsgPart = getMessagePart(message, part);
						addMsgAsHeaderPart(wsMsgPart, builder);
					} else {
						Iterator<?> msgPartIter = msg
								.getMessageParts();
						while (msgPartIter.hasNext()) {
							addMsgAsHeaderPart((WsMessagePart)msgPartIter.next(), builder);
						}
					}
				}
			} else if (wsExtensionElement instanceof WsSoapHeader_12) {
				WsSoapHeader_12 soapHeader = (WsSoapHeader_12) wsExtensionElement;
				ExpandedName msgName = soapHeader.getMessage();
				if (msgName != null) {
					String part = soapHeader.getPart();
					if (part != null) {
						WsMessage message = opMsgRef.getWsdl().getMessage(
								msgName);
						WsMessagePart wsMsgPart = getMessagePart(message, part);
						addMsgAsHeaderPart(wsMsgPart, builder);
					} else {
						Iterator<?> msgPartIter = msg
								.getMessageParts();
						while (msgPartIter.hasNext()) {
							addMsgAsHeaderPart((WsMessagePart)msgPartIter.next(), builder);
						}
					}
				}
			} else if (wsExtensionElement instanceof WsMimeMultipartRelated) {
				WsMimeMultipartRelated mimeMultipart = (WsMimeMultipartRelated) wsExtensionElement;
				Iterator<?> iter = mimeMultipart.getParts();
				while (iter.hasNext()) {
					WsMimePart wsMimePart = (WsMimePart) iter.next();
					Iterator<?> iterSoapBodies = wsMimePart
							.getSoapBodies();
					if (iterSoapBodies.hasNext()) {
						WsSoapBody soapBody = (WsSoapBody)iterSoapBodies.next();
						addSoapBodyPartsToPayload(soapBody, msg, builder);
					} else {
						continue;
					}
				}
			}
		}
	}

	private void addSoapBodyPartsToPayload(WsSoapBody soapBody, WsMessage msg,
			SOAPEventPayloadBuilder builder) throws Exception {
		String[] parts = soapBody.getParts();
		
		if (parts != null) {
			for (String part : parts) {
				WsMessagePart wsMsgPart = getMessagePart(msg, part);
				addMsgAsBodyPart(wsMsgPart, builder);
			}
		} else {
			Iterator<?> msgPartIter = msg.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart((WsMessagePart)msgPartIter.next(), builder);
			}
		}
	}
	
	private void addSoapBodyPartsToPayload(WsSoapBody_12 soapBody, WsMessage msg,
			SOAPEventPayloadBuilder builder) throws Exception {
		String[] parts = soapBody.getParts();
		if (parts != null) {
			for (String part : parts) {
				WsMessagePart wsMsgPart = getMessagePart(msg, part);
				if (wsMsgPart != null)
					addMsgAsBodyPart(wsMsgPart, builder);
			}
		} else {
			Iterator<?> msgPartIter = msg.getMessageParts();
			while (msgPartIter.hasNext()) {
				addMsgAsBodyPart((WsMessagePart)msgPartIter.next(), builder);
			}
		}
	}

	private WsMessagePart getMessagePart(WsMessage message, String partName) {
		Iterator<?> iter = message.getMessageParts();
		while (iter.hasNext()) {
			WsMessagePart wsMessagePart = (WsMessagePart) iter.next();
			String name = wsMessagePart.getName().getLocalName();
			if (name != null && name.equals(partName)) {
				return wsMessagePart;
			}
		}
		return null;
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

	private void addMsgAsHeaderPart(WsMessagePart wsMsgPart,
			SOAPEventPayloadBuilder builder) {
		if (wsMsgPart.getElement() != null) {
			builder.addHeaderPart(wsMsgPart.getElementName(), wsMsgPart
					.getElement());
		} else if (wsMsgPart.getType() != null) {
			builder.addHeaderPart(wsMsgPart.getName(), wsMsgPart.getType());
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

}
