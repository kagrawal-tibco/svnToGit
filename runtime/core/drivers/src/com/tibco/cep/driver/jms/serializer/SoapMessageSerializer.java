package com.tibco.cep.driver.jms.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.tibco.cep.driver.jms.JMSDestination;
import com.tibco.cep.driver.soap.EventNameRegistry;
import com.tibco.cep.driver.soap.MimeTypes;
import com.tibco.cep.driver.soap.SoapConstants;
import com.tibco.cep.driver.soap.SoapHelper;
import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.channel.impl.JMSSerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SOAPEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.io.IOHelper;
import com.tibco.net.mime.MimeCodec;
import com.tibco.net.mime.MimeConstants;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.soap.api.transport.TransportMessage;
import com.tibco.xml.soap.impl.transport.DefaultTransportMessage;
import com.tibco.xml.soap.impl.transport.jms.JmsTransportAddress;
import com.tibco.xml.soap.impl.transport.jms.JmsTransportContext;


public class SoapMessageSerializer
        extends AbstractMessageSerializer<Message>
{

    private static final Pattern CORRELATION_ID_PATTERN = Pattern.compile("SOAP([^,]+),\\d+");    
    private static final int CORRELATION_ID_PATTERN_NAME_GROUP = 1;    
    private static final EventNameRegistry EVENT_NAME_REGISTRY = EventNameRegistry.getInstance();


    private TransportMessage buildTransportMessage(
            Message jmsMessage)
            throws JMSException, IOException
    {
        final TransportMessage transportMessage = new DefaultTransportMessage();
        final JmsTransportContext transportContext = new JmsTransportContext(
                null,
                new JmsTransportAddress(
                		new HashMap<Object, Object>(),
                		jmsMessage.getJMSDestination().toString()),
                null,
                jmsMessage.getJMSDestination());
        transportContext.setMessageID(jmsMessage.getJMSMessageID());
        transportContext.setMessageReference(jmsMessage.getJMSCorrelationID());
        transportContext.setReplyTo(jmsMessage.getJMSReplyTo());
        transportContext.setSoapAction(jmsMessage.getStringProperty(SoapConstants.SOAP_ACTION));
        //noinspection unchecked
        transportContext.getFieldMap()
                .put(MimeConstants.CONTENT_TYPE, jmsMessage.getStringProperty(SoapConstants.CONTENT_TYPE));
        transportMessage.setTransportContext(transportContext);

        this.readEntityFromMessage(jmsMessage, transportMessage);

        return transportMessage;
    }


    @Override
    protected SimpleEvent createEvent(
            Message message,
            SerializationContext context)
            throws Exception
    {
        
        if (null != message.getJMSCorrelationID()) {
            final Matcher m = CORRELATION_ID_PATTERN.matcher(message.getJMSCorrelationID());
            if (m.matches()) {
                return (SimpleEvent)
                        context.getRuleSession().getRuleServiceProvider().getTypeManager().createEntity(
                                m.group(CORRELATION_ID_PATTERN_NAME_GROUP));
            }
        }

        final String destinationPath = context.getDestination().getURI();
        final String serviceName = this.readTargetService(message);
        final String soapAction = this.readSoapAction(message);

        final RuleFunction pp = EVENT_NAME_REGISTRY.getPreprocessor(destinationPath, serviceName, soapAction);
        if (null != pp) {
            final RuleSessionConfig.InputDestinationConfig cfg = context.getDeployedDestinationConfig();
            ((DefaultSerializationContext) context).setDeployedDestinationConfig(
                    new RuleSessionImpl.InputDestinationConfigImpl(
                            cfg.getURI(),
                            cfg.getFilter(),
                            pp,
                            true,
                            cfg.getThreadingModel(),
                            cfg.getNumWorker(),
                            cfg.getQueueSize(),
                            cfg.getWeight()));
        }

        final ExpandedName eventName = EVENT_NAME_REGISTRY.getEventName(destinationPath, serviceName, soapAction);
        if (null == eventName) {
            return super.createEvent(message, context);
        } else {
        	this.logger.log(Level.DEBUG, "Deserializing to registered event type: %s", eventName);
            return (SimpleEvent)
                    context.getRuleSession().getRuleServiceProvider().getTypeManager().createEntity(eventName);
        }
    }


    @Override
    protected Message createMessage(
            Session session,
            JMSSerializationContext context)
            throws Exception
    {
        final Message requestMessage = context.getRequestMessage();
        if (requestMessage instanceof TextMessage) {
            return session.createTextMessage();
        } else {
            return session.createBytesMessage();
        }
    }


    @Override
    public SimpleEvent deserialize(
            Object message,
            SerializationContext context)
            throws Exception
    {
        final SimpleEvent e = super.deserialize((Message) message, context);

        //TODO option to restrict to W3C, to restrict to TIBCO, or to allow both?
        final String soapActionW3c =
                this.getUnquotedStringPropertyValue((Message) message, SoapConstants.SOAP_ACTION_W3C);
        final String soapActionTibco =
                this.getUnquotedStringPropertyValue((Message) message, SoapConstants.SOAP_ACTION_TIBCO);

        String soapAction;
        if ((null != soapActionW3c) && !soapActionW3c.isEmpty()) {
            soapAction = soapActionW3c;
            if (((null == soapActionTibco) || soapActionTibco.isEmpty())
                    && e.getPropertyNamesAsSet().contains(SoapConstants.SOAP_ACTION_TIBCO)) {
                e.setPropertyValue(SoapConstants.SOAP_ACTION_TIBCO, soapAction);
            }
        } else if ((null != soapActionTibco) && !soapActionTibco.isEmpty()) {
            soapAction = soapActionTibco;
            if (e.getPropertyNamesAsSet().contains(SoapConstants.SOAP_ACTION_W3C)) {
                e.setPropertyValue(SoapConstants.SOAP_ACTION_W3C, soapAction);
            }
        } else {
            soapAction = null;
            // TODO
        }

        if (e instanceof SOAPEvent) {
            ((SOAPEvent) e).setSoapAction(soapAction);
        }

        return e;
    }


    @Override
    protected void deserializePayload(
            SimpleEvent event,
            Message jmsMessage,
            SerializationContext context)
            throws Exception
    {
        final RuleSession ruleSession = context.getRuleSession();
        final PayloadFactory payloadFactory = ruleSession.getRuleServiceProvider().getTypeManager().getPayloadFactory();
        if (null != payloadFactory) {
            try {
                final TransportMessage transportMessage = this.buildTransportMessage(jmsMessage);
                event.setPayload(
                        payloadFactory.createPayload(
                                event.getExpandedName(),
                                SoapHelper.buildPayloadAsXiNode(event, transportMessage)));
            } catch (RuntimeException e) {
                PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(e, jmsMessage, event, ruleSession);
            }
        }
    }


    public static EventNameRegistry getEventNameRegistry()
    {
        return EVENT_NAME_REGISTRY;
    }


    private String getUnquotedStringPropertyValue(
            Message message,
            String name)
    {
        String value;
        try {
            value = message.getStringProperty(name);
        } catch (JMSException ignored) {
            value = "";
        }
        if (null == value) {
            return "";
        } else if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        } else {
            return value;
        }
    }


    protected void readEntityFromMessage(
            Message message,
            TransportMessage transportMessage)
            throws JMSException, IOException
    {
        transportMessage.getBody().setContentType(message.getStringProperty(SoapConstants.CONTENT_TYPE));

        final InputStream is;
        if (message instanceof BytesMessage) {
            is = new BytesMessageInputStream((BytesMessage) message);
        } else if (message instanceof TextMessage) {
            // Ugly stuff from JmsTransportDriver
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                IOHelper.copy(new StringReader(((TextMessage) message).getText()), new OutputStreamWriter(baos));
            } finally {
                baos.close();
            }
            is = new ByteArrayInputStream(baos.toByteArray());
        } else {
            throw new RuntimeException("Unsupported JMS message type: " + message.getClass().getName());
        }
        try {
            SoapHelper.readTransportMessageFromStream(is, transportMessage);
        } finally {
            is.close();
        }
    }


    private String readSoapAction(
            Message message)
    {
        final String soapActionW3c =
                this.getUnquotedStringPropertyValue(message, SoapConstants.SOAP_ACTION_W3C);
        if ((null != soapActionW3c) && !soapActionW3c.isEmpty()) {
            return soapActionW3c;
        }

        final String soapActionTibco =
                this.getUnquotedStringPropertyValue(message, SoapConstants.SOAP_ACTION_TIBCO);
        return ((null != soapActionTibco) && !soapActionTibco.isEmpty())
                ? soapActionTibco
                : null;
    }


    private String readTargetService(
            Message message)
    {
        final String targetService =
                this.getUnquotedStringPropertyValue(message, SoapConstants.TARGET_SERVICE);
        return ((null != targetService) && !targetService.isEmpty())
                ? targetService
                : null;
    }


    @Override
    public Message serialize(
            SimpleEvent event,
            SerializationContext context)
            throws Exception
    {
    	final Message message = (Message) super.serialize(event, context);
    	final Set<?> propertyNames = event.getPropertyNamesAsSet();
    	
    	message.setStringProperty(SoapConstants.BINDING_VERSION, "1.0");   
    	message.setStringProperty(SoapConstants.PROPERTY_REQUEST_URI_W3C,
    			"jms:jndi:" + ((JMSDestination) context.getDestination()).getName());    	
    	
    	String soapAction = null;
    	if (propertyNames.contains(SoapConstants.SOAP_ACTION_W3C)) {
    		soapAction = String.valueOf(event.getPropertyValue(SoapConstants.SOAP_ACTION_W3C)); 
    	}
    	if (((null == soapAction) || soapAction.isEmpty())
    			&& propertyNames.contains(SoapConstants.SOAP_ACTION_TIBCO)) {
    		
    		soapAction =
    				String.valueOf(event.getPropertyValue(SoapConstants.SOAP_ACTION_TIBCO));
    	}
    	if (((null == soapAction) || soapAction.isEmpty())
    			&& (event instanceof SOAPEvent)) {
    		soapAction = ((SOAPEvent) event).getSoapAction();
    	}
    	if ((null != soapAction) && !soapAction.isEmpty()) {
	    	message.setStringProperty(SoapConstants.SOAP_ACTION_W3C, soapAction);
	    	message.setStringProperty(SoapConstants.SOAP_ACTION_TIBCO, soapAction);
    	}

    	if (propertyNames.contains(SoapConstants.TARGET_SERVICE)) {
	    	final String targetService = String.valueOf(event.getPropertyValue(SoapConstants.TARGET_SERVICE));
	    	if ((null != targetService) && !targetService.isEmpty()) {    	
	    		message.setStringProperty(SoapConstants.TARGET_SERVICE, targetService);
	    	}
    	}
    	        
        return message;
    }


    @Override
    protected void serializePayload(
            SimpleEvent event,
            Message msg,
            JMSSerializationContext context)
            throws Exception
    {

        final TransportMessage transportMessage = new DefaultTransportMessage();
        transportMessage.setTransportContext(new JmsTransportContext(null, (JmsTransportAddress) null));
        transportMessage.setContentBoundary(MimeCodec.generateBoundary());

        final EventPayload payload = event.getPayload();
        if (null != payload) {
            SoapHelper.buildSoapResponse((XiNodePayload) payload, transportMessage);
        }

        if (transportMessage.getAttachments().hasNext()) {
            //TODO fix this
            msg.setStringProperty(MimeConstants.CONTENT_TYPE, SoapHelper.getContentTypeValue(transportMessage));
        } else {
            //TODO fix this
            msg.setStringProperty(MimeConstants.CONTENT_TYPE, MimeTypes.XML_TEXT.getLiteral());
        }

        if (msg instanceof TextMessage) {
            ((TextMessage) msg).setText(
                    new String(SoapHelper.getTransportMessageAsBytes(transportMessage), "UTF-8")); //TODO charset?
        } else {
            ((BytesMessage) msg).writeBytes(SoapHelper.getTransportMessageAsBytes(transportMessage));
        }
    }


}
