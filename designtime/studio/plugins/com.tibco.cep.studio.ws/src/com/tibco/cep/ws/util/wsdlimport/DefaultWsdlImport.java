package com.tibco.cep.ws.util.wsdlimport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.MessageContext;
import com.tibco.cep.ws.util.wsdlimport.context.MessageReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.PortContext;
import com.tibco.cep.ws.util.wsdlimport.context.ServiceContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapBindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.SoapMessageReferenceContext;
import com.tibco.cep.ws.util.wsdlimport.context.WsdlContext;
import com.tibco.cep.ws.util.wsdlimport.context.impl.MessageContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.OperationContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.OperationReferenceContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.ServiceContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.SoapBindingContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.SoapMessageReferenceContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.SoapOperationReferenceContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.SoapPortContextImpl;
import com.tibco.cep.ws.util.wsdlimport.context.impl.WsdlContextImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.ws.wsdl.WsBinding;
import com.tibco.xml.ws.wsdl.WsMessageKind;
import com.tibco.xml.ws.wsdl.WsOperation;
import com.tibco.xml.ws.wsdl.WsOperationMessage;
import com.tibco.xml.ws.wsdl.WsOperationMessageReference;
import com.tibco.xml.ws.wsdl.WsOperationReference;
import com.tibco.xml.ws.wsdl.WsPort;
import com.tibco.xml.ws.wsdl.WsService;
import com.tibco.xml.ws.wsdl.WsWsdl;


public class DefaultWsdlImport
{


    public static final String W3C_SOAP_OVER_JMS_TRANSPORT_URI = "http://www.w3.org/2010/soapjms/";
    public static final ExpandedName W3C_SOAP_OVER_JMS_DELIVERY_MODE =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "deliveryMode");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_CONNECTION_FACTORY_NAME =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "jndiConnectionFactoryName");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "jndiContextParameter");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER_NAME =
            ExpandedName.makeName("name");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_CONTEXT_PARAMETER_VALUE =
            ExpandedName.makeName("value");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_INITIAL_CONTEXT_FACTORY =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "jndiInitialContextFactory");
    public static final ExpandedName W3C_SOAP_OVER_JMS_JNDI_URL =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "jndiURL");
    public static final ExpandedName W3C_SOAP_OVER_JMS_PRIORITY =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "priority");
    public static final ExpandedName W3C_SOAP_OVER_JMS_TIME_TO_LIVE =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "timeToLive");
    public static final ExpandedName W3C_SOAP_OVER_JMS_REPLY_TO_NAME =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "replyToName");
    public static final ExpandedName W3C_SOAP_OVER_JMS_TOPIC_REPLY_TO_NAME =
            ExpandedName.makeName(W3C_SOAP_OVER_JMS_TRANSPORT_URI, "topicReplyToName");


    public static final String TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI = "http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS";
    public static final ExpandedName TIBCO_2004_SOAP_OVER_JMS_BINDING =
            ExpandedName.makeName(TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI, "binding");
    public static final ExpandedName TIBCO_2004_SOAP_OVER_JMS_MESSAGE_FORMAT =
            ExpandedName.makeName("messageFormat");
    public static final ExpandedName TIBCO_2004_SOAP_OVER_JMS_CONNECTION_FACTORY =
            ExpandedName.makeName(TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI, "connectionFactory");
    public static final ExpandedName TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS =
            ExpandedName.makeName(TIBCO_2004_SOAP_OVER_JMS_TRANSPORT_URI, "targetAddress");
    public static final ExpandedName TIBCO_2004_SOAP_OVER_JMS_TARGET_ADDRESS_DESTINATION =
            ExpandedName.makeName("destination");

    protected final WsWsdl wsdl;

    protected DefaultWsdlImportResult result;


    public DefaultWsdlImport(
            WsWsdl wsdl)
            throws Exception
    {
        this.wsdl = wsdl;
        this.initializeResults();
    }


    public DefaultWsdlImportResult execute()
            throws Exception
    {
        this.importWsdl(this.makeWsdlContext(wsdl));
        return this.result;
    }

//	
//	
//	private String getOperationName(
//			final WsSoapOperation opn)
//	{
//		return opn.getLocalName(); //TODO WSDL #2.4.5
//	}


    protected void importBinding(
            SoapBindingContext bindingContext)
            throws Exception
    {
        if (null == bindingContext) {
            return;
        }

        this.importPortType(bindingContext);

        for (@SuppressWarnings("unchecked")
             final Iterator<WsOperationReference> opRefIter =
                     bindingContext.getBinding().getOperations();
             opRefIter.hasNext(); ) {

            this.importOperationReference(
                    this.makeOperationReferenceContext(
                            bindingContext,
                            opRefIter.next()));
        }
    }


    protected void importFaultMessageReference(
            MessageReferenceContext messageReferenceContext)
    {
        this.importMessageReference(messageReferenceContext);
    }


    protected void importInputMessageReference(
            MessageReferenceContext messageReferenceContext)
    {
        this.importMessageReference(messageReferenceContext);
    }


    protected void importMessage(
            MessageContext msgContext)
    {
        // TODO
    }


    protected void importMessageReference(
            MessageReferenceContext messageReferenceContext)
    {
        this.importMessage(
                this.makeMessageContext(
                        messageReferenceContext,
                        messageReferenceContext.getOperationMessageReference().getMessage()));
    }


    protected void importMessageReferences(
            OperationReferenceContext opRefContext)
            throws Exception
    {
        final List<WsOperationMessageReference> faultMsgRefs =
                new ArrayList<WsOperationMessageReference>();
        WsOperationMessageReference inputMsgRef = null;
        WsOperationMessageReference outputMsgRef = null;
        for (@SuppressWarnings("unchecked")
             final Iterator<WsOperationMessageReference> i =
                     opRefContext.getOperationReference().getMessages();
             i.hasNext();) {
            final WsOperationMessageReference msgRef = i.next();
            if (WsMessageKind.INPUT.equals(msgRef.getMessageKind())) {
                if (null == inputMsgRef) {
                    inputMsgRef = msgRef;
                }
            } else if (WsMessageKind.OUTPUT.equals(msgRef.getMessageKind())) {
                if (null == outputMsgRef) {
                    outputMsgRef = msgRef;
                }
            } else {
                faultMsgRefs.add(msgRef);
            }
        }

        if (null == inputMsgRef) {
            //TODO ?
        } else {
            this.importInputMessageReference(
                    this.makeInputMessageReferenceContext(opRefContext, inputMsgRef));
        }

        if (null == outputMsgRef) {
            //TODO ?
        } else {
            this.importOutputMessageReference(
                    this.makeOutputMessageReferenceContext(opRefContext, inputMsgRef));
        }

        for (final WsOperationMessageReference msgRef: faultMsgRefs) {
            this.importFaultMessageReference(
                    this.makeFaultMessageReferenceContext(opRefContext, msgRef));
        }
    }


    protected void importOperation(
            OperationContext operationContext)
            throws Exception
    {
        //TODO ?
    }


    protected void importOperationReference(
            OperationReferenceContext opRefContext)
            throws Exception
    {
        if (null == opRefContext) {
            return;
        }

        final WsOperationReference opRef = opRefContext.getOperationReference();

        if (null == opRef.getOperation()) {
            this.storeError(
                    "Couldn't find operation child of a portType element for "
                            + opRef.getLocalName());
            return;

        } else {
            this.importOperation( //TODO keep or not?
                    this.makeOperationContext(opRefContext, opRef.getOperation()));

            this.importMessageReferences(opRefContext);
        }
    }


    protected void importOutputMessageReference(
            MessageReferenceContext messageReferenceContext)
    {
        this.importMessageReference(messageReferenceContext);
    }


    protected void importPort(
            PortContext portContext)
            throws Exception
    {
        if (null == portContext) {
            return;
        }

        this.importBinding(
                this.makeBindingContext(
                        portContext,
                        portContext.getPort().getBinding()));
    }


    protected void importPortType(
            BindingContext bindingContext)
            throws Exception
    {
    }


    protected void importService(
            ServiceContext serviceContext)
            throws Exception
    {
        if (null == serviceContext) {
            return;
        }
        for (final Iterator<?> portIter = serviceContext.getService().getPorts();
             portIter.hasNext(); ) {

            this.importPort(
                    this.makePortContext(
                            serviceContext,
                            (WsPort) portIter.next()));
        }
    }


    protected void importWsdl(
            WsdlContext wsdlContext)
            throws Exception
    {
        this.result.setAbstract(true);

        if (null == wsdlContext) {
            return;
        }

        for (@SuppressWarnings("unchecked")
             final Iterator<WsService> serviceIter = wsdlContext.getWsdl().getServices();
             serviceIter.hasNext();
             this.result.setAbstract(false)) {

            this.importService(
                    this.makeServiceContext(
                            wsdlContext,
                            serviceIter.next()));
        }
    }


    protected void initializeResults()
            throws Exception
    {
        this.result = new DefaultWsdlImportResult();
    }


    protected SoapBindingContext makeBindingContext(
            PortContext portContext,
            WsBinding binding)
            throws Exception
    {
        return new SoapBindingContextImpl(portContext, binding);
    }


    protected SoapMessageReferenceContext makeFaultMessageReferenceContext(
            OperationReferenceContext opRefContext,
            WsOperationMessageReference msgRef)
    {
        return this.makeMessageReferenceContext(opRefContext, msgRef);
    }


    protected SoapMessageReferenceContext makeInputMessageReferenceContext(
            OperationReferenceContext opRefContext,
            WsOperationMessageReference msgRef)
    {
        return this.makeMessageReferenceContext(opRefContext, msgRef);
    }


    protected MessageContext makeMessageContext(
            MessageReferenceContext messageReferenceContext,
            WsOperationMessage message)
    {
        return new MessageContextImpl(messageReferenceContext, message);
    }


    protected SoapMessageReferenceContext makeMessageReferenceContext(
            OperationReferenceContext opRefContext,
            WsOperationMessageReference msgRef)
    {
        return new SoapMessageReferenceContextImpl(opRefContext, msgRef);
    }


    protected OperationContext makeOperationContext(
            OperationReferenceContext opRefContext,
            WsOperation op)
            throws Exception
    {
        return new OperationContextImpl(opRefContext, op);
    }


    protected OperationReferenceContextImpl makeOperationReferenceContext(
            BindingContext bindingContext,
            WsOperationReference opRef)
            throws Exception
    {
        return new SoapOperationReferenceContextImpl(bindingContext, opRef);
    }


    protected SoapMessageReferenceContext makeOutputMessageReferenceContext(
            OperationReferenceContext opRefContext,
            WsOperationMessageReference msgRef)
    {
        return this.makeMessageReferenceContext(opRefContext, msgRef);
    }


    protected PortContext makePortContext(
            ServiceContext serviceContext,
            WsPort port)
            throws Exception
    {
        return new SoapPortContextImpl(serviceContext, port);
    }


    protected ServiceContext makeServiceContext(
            WsdlContext wsdlContext,
            WsService service)
            throws Exception
    {
        return new ServiceContextImpl(wsdlContext, service);
    }


    protected WsdlContext makeWsdlContext(
            WsWsdl wsdl)
            throws Exception
    {
        return new WsdlContextImpl(wsdl);
    }



    protected void storeError(
            String errorString)
    {
        this.result.getErrors().add(errorString);
    }
}
