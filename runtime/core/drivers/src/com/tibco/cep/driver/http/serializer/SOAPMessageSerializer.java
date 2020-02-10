package com.tibco.cep.driver.http.serializer;

import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAME_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_SOAPACTION_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.SOAPACTION_WRAP_QUOTES_PROPERTY;
import static com.tibco.net.mime.MimeConstants.CONTENT_TYPE;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.driver.soap.SoapHelper;
import com.tibco.cep.driver.soap.SoapUtils;
import com.tibco.cep.driver.util.PayloadExceptionUtil;

import org.apache.commons.httpclient.ChunkedOutputStream;
import org.apache.http.client.methods.HttpUriRequest;
import org.xml.sax.SAXParseException;

import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.HttpClientSerializationContext;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.soap.MimeTypes;
import com.tibco.cep.driver.http.serializer.soap.SOAPTransportContext;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SOAPEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.net.mime.MimeCodec;
import com.tibco.net.mime.MutableBodyMimePart;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.soap.api.transport.TransportContext;
import com.tibco.xml.soap.api.transport.TransportEntity;
import com.tibco.xml.soap.api.transport.TransportMessage;
import com.tibco.xml.soap.helpers.HttpHelper;
import com.tibco.xml.soap.helpers.MimeHelper;
import com.tibco.xml.soap.impl.transport.DefaultTransportMessage;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Mar 26, 2009
 * Time: 6:45:21 PM
 *
 * Serializer/Deserializer for soap messages
 */
public class SOAPMessageSerializer extends RESTMessageSerializer {

    /**
     * Maintain a registry to content ids to validate incoming attachment ids
     * Keep this thread local. This is based on the assumption
     * that each request is handled by a separate thread
     */
    private ThreadLocal<List<String>> contentIdRegistry = new ThreadLocal<List<String>>();
    
    /**
     * Deserialize an incoming soap message to the appropriate event.
     * @param message
     * @param context
     * @return the {@linkplain SimpleEvent}
     * @throws Exception
     */
    @Override
    public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception {
        SimpleEvent event = null;
        if (message == null) {
            return null;
        }
        //Hopefully cast will not cause problems
        HttpDestination destination = (HttpDestination) context.getDestination();
        if (destination == null) {
            return null;
        }

        List<String> contentIds = new ArrayList<String>();
        //Set this for this thread
        contentIdRegistry.set(contentIds);

        RuleSession session = context.getRuleSession();
        DestinationConfig config = destination.getConfig();

        if (message instanceof HttpChannelServerRequest) {
            HttpChannelServerRequest request = (HttpChannelServerRequest) message;
            //Deserialize into event
            //Get namespace ns.
            String ns = null;
            String nm = null;
            String extId;
            String method = request.getMethod();
            if (HttpMethods.METHOD_POST.equals(method)) {
                if (destination.isDeSerializingEventType()) {
                    //Get ns, nm headers
                    ns = request.getHeader(MESSAGE_HEADER_NAMESPACE_PROPERTY);
                    nm = request.getHeader(MESSAGE_HEADER_NAME_PROPERTY);
                }
                //Send external id as header for POST
                extId = request.getHeader(MESSAGE_HEADER_EXTID_PROPERTY);
                event = createEvent(ns, nm, session, config);
                if (extId != null) {
                    event.setExtId(extId);
                }
                buildEventModel(event, session, request);
            }
        } else if (message instanceof HttpChannelClientResponse) {
            HttpChannelClientResponse response = (HttpChannelClientResponse) message;

            Map overrideData = ((HttpClientSerializationContext) context).getOverrideData();
            Class<?> responseEventType = (Class<?>) overrideData.get(HttpUtils.HTTP_RESPONSE_EVENT_TYPE);
            if (session == null) {
                //Get from override data
                session = (RuleSession) overrideData.get(RuleSession.class.getName());
            }
            //Send external id as header for POST
            String extId = response.getFirstHeaderValue(MESSAGE_HEADER_EXTID_PROPERTY);
            if (responseEventType == null) {
                if (destination.isDeSerializingEventType()) {
                    final String ns = response.getFirstHeaderValue(MESSAGE_HEADER_NAMESPACE_PROPERTY);
                    final String nm = response.getFirstHeaderValue(MESSAGE_HEADER_NAME_PROPERTY);
                    event = createEvent(ns, nm, session, config);
                } else {
                    event = createEvent(null, null, session, config);
                }
            } else {
                event = createEventInstance(responseEventType, session);
            }
            if (extId != null) {
                event.setExtId(extId);
            }
            buildEventModel(event, session, response);
        }
        return event;
    }

    /**
     * Build the event's properties and payload using the transport headers,
     * and post data if any which is added as payload.
     * @param event
     * @param session
     * @param request
     * @throws Exception
     */
    private void buildEventModel(final SimpleEvent event,
            final RuleSession session,
            final HttpChannelServerRequest request) throws Exception {
        super.populateEventProperties(event, request);
        //create a transport context
        SOAPTransportContext context = new SOAPTransportContext(request);
        //Get input stream
        InputStream iStream = request.getInputStream();
        //Get the incoming message
        TransportMessage transportMessage = buildTransportMessage(iStream, context);

        deserializePayload(event, session, transportMessage);
    }

    /**
     * Build the event's properties and payload using the transport headers,
     * and post data if any which is added as payload.
     * @param event
     * @param session
     * @param response
     * @throws Exception
     */
    private void buildEventModel(final SimpleEvent event,
            final RuleSession session,
            final HttpChannelClientResponse response) throws Exception {
        super.populateEventProperties(event, response);

        //create a transport context
        SOAPTransportContext context = new SOAPTransportContext(response);

        //Get input stream
        byte[] data = response.getEntity();

        InputStream iStream = new BufferedInputStream((new ByteArrayInputStream(data)));

        //Get the incoming message
        TransportMessage transportMessage = buildTransportMessage(iStream, context);

        deserializePayload(event, session, transportMessage);
    }

    /**
     * Construct a {@linkplain TransportMessage} based on the incoming
     * request data.
     * <p>
     * This includes adding all MIME parts as part of the post data.
     * </p>
     * @param iStream InputStream
     * @param context TransportContext
     * @return {@linkplain TransportMessage}
     * @throws IOException
     */
    public TransportMessage buildTransportMessage(InputStream iStream, TransportContext context) throws IOException {
        TransportMessage transportMessage = new DefaultTransportMessage();
        //Associate a transport context
        transportMessage.setTransportContext(context);

        HttpHelper.readTransportMessage(iStream, transportMessage);
        return transportMessage;
    }

    /**
     * Construct event payload as an {@linkplain XiNode} using the
     * {@linkplain TransportMessage} as input.
     * <p>
     * The envelope part and any optional attachments are added to the payload
     * in homogeneous form.
     * </p>
     * @param event
     * @param session
     * @param message
     * @throws Exception
     */
    protected void deserializePayload(final SimpleEvent event,
            final RuleSession session,
            final TransportMessage message) throws Exception {
        //Get the RSP from this
        RuleServiceProvider rsp = session.getRuleServiceProvider();
        PayloadFactory payloadFactory =
                rsp.getTypeManager().getPayloadFactory();
        ExpandedName exName = event.getExpandedName();

        if (payloadFactory != null) {
        	EventPayload payload;
        	Exception ex = null;
        	try {
        		//Get XiNode representation
        		final XiNode payloadXiNode = SoapHelper.buildPayloadAsXiNode(event, message);

        		payload = payloadFactory.createPayload(exName, payloadXiNode);
        		event.setPayload(payload);
        	} catch (SAXParseException spe) {
        		ex = spe;
        	} catch (RuntimeException re) {
        		ex = re;
        	}
        	
        	if (ex != null) {
        		PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(ex, message, event, session);
        	}
        }
        /**Clear the ThreadLocal's contents. Both server implementations
         * use thread pools and do not provide guarantee that
         * the thread local will be safely unset
         *
         * TODO Test this with sending concurrent requests = size of the thread pool
         * TODO for tomcat and hc
         */
        contentIdRegistry.get().clear();
    }

    
    public XiNode buildMessageAsXiNode( TransportMessage message, ExpandedName messageExpandedName) throws Exception {
        XiNode messageNode = XiSupport.getXiFactory().createElement(messageExpandedName);
        //Get the soap envelope which presumably is its body
        TransportEntity envelopeEntity = message.getBody();
        //Get its content
        envelopeEntity.loadContent();//load the content before reading, lazy loading
        InputStream envelopeStream = envelopeEntity.getContentStream();

        //Get an XiNode for it
        XiNode rootNode = XiNodeBuilder.parse(envelopeStream);
        //cleanTextNodes is called here to be consistent with 
        //JMS way of handling nodes, 
        //and this will not allow mixed case content nodes...
        XiNodeUtilities.cleanTextNodes(rootNode);
        XiNode envelopeNode = rootNode.getRootNode().getFirstChild();

        //TODO Check that every immediate header child is namespace qualified
        SoapUtils.validateHeaders(rootNode);

        if (rootNode != null) {
            //Add this as child to main node
            messageNode.appendChild(envelopeNode);
            //Get attachments
            SoapHelper.buildAttachmentsXiNode(message, messageNode);
        }

        return messageNode;
    }


    /**
     * Set event properties as headers on response
     * @param event
     * @param response
     * @throws Exception
     */
    private void setResponseHeaders(
            final SimpleEvent event,
            final HttpChannelServerResponse response,
            final boolean includeEventType)
            throws Exception {    	
        for (String property : event.getPropertyNames()) {
            String header = getHeader(property);
            Object propValue = event.getProperty(property);
            if (propValue != null) {
                response.setHeader(header, propValue.toString());
            }
        }

        if (includeEventType) {
            final ExpandedName en = event.getExpandedName();
            response.setHeader(MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
            response.setHeader(MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
        }
    }


    /**
     * Set event properties as headers on response
     * @param event
     * @param request
     * @param destination
     * @throws Exception
     */
    private void setRequestHeaders(
            SimpleEvent event,
            HttpChannelClientRequest request,
            HttpDestination destination,
            boolean includeEventType)
            throws Exception {
        HashMap<String, String> nvp = new HashMap<String, String>();
        
        String header, headerValue = null;
        // parse event properties
        for (String property : event.getPropertyNames()) {
            Object propValue = event.getProperty(property);
            if (propValue != null) {
                header = getHeader(property);
                headerValue = (header.equalsIgnoreCase(MESSAGE_HEADER_SOAPACTION_PROPERTY)) ? getQuoteWrappedValue(propValue.toString()) : propValue.toString();
                nvp.put(header, headerValue);
            }
        }
        
        String soapHeaderKey = null;
        
        // check for event attributes, specifically @soapAction in SOAPEvent if exists
        // this will override any value set in Event properties
        if (event instanceof SOAPEvent) {
        	SOAPEvent soapEvent = (SOAPEvent) event;
        	if (soapEvent.getSoapAction() != null && !soapEvent.getSoapAction().isEmpty()) {
        		// first remove any previous occurrence
        		soapHeaderKey = checkSOAPActionHeader(nvp);
        		if (soapHeaderKey != null && !soapHeaderKey.isEmpty()) {
        			nvp.remove(soapHeaderKey);
        		}
        		
        		// now add the attribute value
        		nvp.put(MESSAGE_HEADER_SOAPACTION_PROPERTY, getQuoteWrappedValue(soapEvent.getSoapAction()));
        		soapHeaderKey = null;
        	}
        }
        
        // fall back code, if neither property nor attribute is set
        soapHeaderKey = checkSOAPActionHeader(nvp);
        if (soapHeaderKey == null || soapHeaderKey.isEmpty()) {
        	nvp.put(MESSAGE_HEADER_SOAPACTION_PROPERTY, getQuoteWrappedValue(destination.getName()));
        	soapHeaderKey = null;
        }
        
        // finally wrap SOAPAction in quotes
        boolean wrapQuotes = true;
        String wrapQuotesPropertyValue = System.getProperty(SOAPACTION_WRAP_QUOTES_PROPERTY, "true");        
        if (wrapQuotesPropertyValue != null && !wrapQuotesPropertyValue.isEmpty()) wrapQuotes = Boolean.valueOf(wrapQuotesPropertyValue);
        
        if (!wrapQuotes) {
        	soapHeaderKey = checkSOAPActionHeader(nvp);
        	if (soapHeaderKey != null && !soapHeaderKey.isEmpty()) {
        		String soapActionValue = nvp.get(soapHeaderKey);
        		soapActionValue = soapActionValue.replaceAll("^\"|\"$", "");
        		nvp.put(soapHeaderKey, soapActionValue);
        	}
        }

        if (includeEventType) {
            final ExpandedName en = event.getExpandedName();
            nvp.put(MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
            nvp.put(MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
        }

        request.writeContent(nvp);
    }
    
    /**
     * Check if SOAPAction exists in the header keys 
     * 
     * @param headers
     * @return
     */
    private String checkSOAPActionHeader(Map<String,String> headers) {
    	String headerKey = headers.get(MESSAGE_HEADER_SOAPACTION_PROPERTY);
    	if (headerKey != null && !headerKey.isEmpty()) return MESSAGE_HEADER_SOAPACTION_PROPERTY;
    	
    	Set<String> headerKeys = headers.keySet();
    	
    	for (String key : headerKeys) {
    		if (key.equalsIgnoreCase(MESSAGE_HEADER_SOAPACTION_PROPERTY)) {
    			return key;
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Wrapped the SOAPAction in Quotes
     * 
     * @param soapActionValue
     * @return
     */
    private String getQuoteWrappedValue(String soapActionValue) {
    	StringBuilder soapActionValueBuilder = new StringBuilder();
    	soapActionValueBuilder.append("\"");
    	soapActionValueBuilder.append(soapActionValue);
    	soapActionValueBuilder.append("\"");
    	
    	return soapActionValueBuilder.toString();
    }

    /**
     * Serialize the response event's contents to the response stream
     * @param event
     * @param context
     * @return
     * @throws Exception
     */
    @Override
    public Object serialize(SimpleEvent event,
            SerializationContext context) throws Exception {
        //Get event payload
        EventPayload payload = event.getPayload();
        //Create the output message
        TransportMessage message = new DefaultTransportMessage();
        if (payload != null) {
            //The payload will be XiNodePayload
            XiNodePayload xiNodePayload = (XiNodePayload) payload;

            SoapHelper.buildSoapResponse(xiNodePayload, message);
        }

        if (context instanceof HttpDestination.HttpServerSerializationContext) {
            //Serialize this to the response writer
            HttpChannelServerResponse response =
                    ((HttpDestination.HttpServerSerializationContext) context).getResponse();

            //Set response headers
            setResponseHeaders(event, response, ((HttpDestination) context.getDestination()).isSerializingEventType());
            //Associate a transport context
            message.setTransportContext(new SOAPTransportContext(response));

            serialize(message, response);

            response.sendResponse();
        } else if (context instanceof HttpDestination.HttpClientSerializationContext) {
            //Serialize this to the response writer
            HttpChannelClientRequest request =
                    ((HttpDestination.HttpClientSerializationContext) context).getClientRequest();
            if (request == null) {
            	HttpChannelClient httpChannelClient = ((HttpDestination.HttpClientSerializationContext)context).getClient();
            	request = httpChannelClient.getClientRequest();
            }

            // this should be safe class cast
            HttpDestination destination = (HttpDestination) context.getDestination();
            setRequestHeaders(event, request, destination,
                    ((HttpDestination) context.getDestination()).isSerializingEventType());
            //Associate a transport context
            message.setTransportContext(new SOAPTransportContext(request));

            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            serialize(message, request, oStream);
            request.writeContent(oStream.toByteArray());
        }
        return null;
    }

    /**
     * Serialize the {@linkplain TransportMessage} to an outputstream.
     * <p>
     * If the message contains attachments, the {@linkplain ChunkedOutputStream}
     * is used to write to the main {@linkplain OutputStream}.
     *
     * @param outputMessage
     * @param response
     * @throws Exception
     */
    protected void serialize(TransportMessage outputMessage,
            HttpChannelServerResponse response) throws Exception {

        OutputStream outputStream = response.getOutputStream();
        //Serialize the message
        boolean hasAttachments = outputMessage.getAttachments().hasNext();

        String contentBoundary = MimeCodec.generateBoundary();
        //Generate a boundary for multipart messages
        outputMessage.setContentBoundary(contentBoundary);

        MutableBodyMimePart mime = MimeHelper.createMimePart(outputMessage, false);

        mime.setOutputStream(outputStream);

        if (hasAttachments) {
            response.setHeader(CONTENT_TYPE, SoapHelper.getContentTypeValue(outputMessage));
        } else {
            String type = MimeTypes.XML_TEXT.getLiteral();
            if (outputMessage != null && outputMessage.getBody() != null)
            	type = outputMessage.getBody().getContentType().replace("\"", "");
            
            response.setHeader(CONTENT_TYPE, type);
        }
        mime.writePart();
        outputStream.close();
    }


    /**
     * Serialize the {@linkplain TransportMessage} to an outputstream.
     * <p>
     * If the message contains attachments, the {@linkplain ChunkedOutputStream}
     * is used to write to the main {@linkplain OutputStream}.
     *
     * @param outputMessage
     * @param request
     * @param oStream
     * @throws Exception
     */
    public void serialize(TransportMessage outputMessage,
            HttpChannelClientRequest request,
            OutputStream oStream) throws Exception {

        HttpUriRequest httpRequest = ((HttpUriRequest) request.getRequestMethod());

        //Serialize the message
        boolean useChunked = outputMessage.getAttachments().hasNext();

        String contentBoundary = MimeCodec.generateBoundary();
        //Generate a boundary for multipart messages
        outputMessage.setContentBoundary(contentBoundary);

        MutableBodyMimePart mime =
                MimeHelper.createMimePart(outputMessage, false);
        mime.setOutputStream(oStream);

        ChunkedOutputStream output = null;
        if (useChunked) {
            //Use this for atachments
            output = new ChunkedOutputStream(oStream);
            mime.setOutputStream(output);
            final String type = SoapHelper.getContentTypeValue(outputMessage);
            mime.setType(type);
            httpRequest.setHeader(CONTENT_TYPE, type);
        } else {
        	String type = MimeTypes.XML_TEXT.getLiteral();
            if (outputMessage != null && outputMessage.getBody() != null)
            	type = outputMessage.getBody().getContentType().replace("\"", "");
            
            httpRequest.setHeader(CONTENT_TYPE, type);
        }
        //Write the mime part
        mime.writePart();

        oStream.flush();

        if (useChunked) {
            //Finish the operation
            output.finish();
        }
    }
}
