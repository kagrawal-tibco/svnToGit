package com.tibco.cep.driver.http.serializer;

import static com.tibco.be.util.BEStringUtilities.convertByteArrayToString;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAME_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_PAYLOAD_PROPERTY;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Map;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.HttpClientSerializationContext;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AdvisoryEventImpl;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.util.StringUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBase64Binary;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmParticleTerm;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 2, 2008
 * Time: 8:49:58 PM
 *
 * @author aathalye
 */
public class RESTMessageSerializer extends HTTPMessageSerializer {

    private static com.tibco.cep.kernel.service.logging.Logger LOGGER = com.tibco.cep.kernel.service.logging.LogManagerFactory.getLogManager().getLogger(RESTMessageSerializer.class);

    /**
     * The method supports both <b>GET</b>, and <b>POST</b> requests.
     * <p/>
     * For <b>GET</b> requests, send the <b>_ns_</b>, and <b>_nm_</b>
     * values in the query string.
     * </p>
     * <p/>
     * For <b>POST</b> requests, the post data should be a serialized <tt>
     * Map&lt;String, Object&gt;</tt>.
     * The properties should be sent with value key <b>"Properties"</b>,
     * whereas payload should be sent as value for key <b>"Payload"</b>.
     * </p>
     *
     * @param message: The incoming <tt>HttpServletRequest</tt> object.
     * @param context: The serialization context.
     * @return the deserialized event populated with properties, and payload.
     * @throws Exception: exception generated while deserializing payload, or paroperties.
     */
    public SimpleEvent deserialize(final Object message,
                                   final SerializationContext context) throws Exception {
        SimpleEvent event;
        if (message == null) {
            return null;
        }

        //Hopefully cast will not cause problems
        HttpDestination destination = (HttpDestination) context.getDestination();
        if (destination == null) {
            return null;
        }
        RuleSession session = context.getRuleSession();
        if (context instanceof HttpClientSerializationContext) {
            Map overrideData = ((HttpClientSerializationContext) context).getOverrideData();
            Class<?> responseEventType = (Class<?>) overrideData.get(HttpUtils.HTTP_RESPONSE_EVENT_TYPE);
            event = deserializeResponse((HttpChannelClientResponse) message, session, destination, responseEventType);
        } else {
            event = deserializeRequest((HttpChannelServerRequest) message, session, destination);
        }
        return event;
    }

    /**
     * Deserialize incoming message to the appropriate {@linkplain SimpleEvent}
     *
     * @param request
     * @param session
     * @param destination
     * @return
     * @throws Exception
     */
    protected SimpleEvent deserializeRequest(
            HttpChannelServerRequest request,
            RuleSession session,
            HttpDestination destination)
            throws Exception {
        SimpleEvent event = null;
        //Get namespace ns. Should be sent as query string for get request
        String ns = null;
        String nm = null;
        String extId = null;
        String method = request.getMethod();
        String encoding = request.getCharacterEncoding();
        if (HttpMethods.METHOD_GET.equals(method)) {
            //Check the query string params
            String[] values;
            if (destination.isDeSerializingEventType()) {
                values = request.getParameterValues(MESSAGE_HEADER_NAMESPACE_PROPERTY);
                if ((values != null) && (values.length > 0)) {
                    ns = values[0];
                }
                values = request.getParameterValues(MESSAGE_HEADER_NAME_PROPERTY);
                if ((values != null) && (values.length > 0)) {
                    nm = values[0];
                }
            }
            event = createEvent(ns, nm, session, destination.getConfig());
            //Send external id in query string for GET
            String[] extIds = request.getParameterValues(MESSAGE_HEADER_EXTID_PROPERTY);
            if (extIds != null && extIds.length > 0)
                extId = extIds[0];
            if (extId != null) {
                event.setExtId(extId);
            }
            values = request.getParameterValues(MESSAGE_HEADER_PAYLOAD_PROPERTY);
            if ((values != null) && (values.length > 0)) {
                deserializePayload(event, session, values[0], encoding, destination.isJSONPayload());
            }
        } else if (HttpMethods.METHOD_POST.equals(method)) {
            if (destination.isDeSerializingEventType()) {
                //Get ns, nm headers
                ns = request.getHeader(MESSAGE_HEADER_NAMESPACE_PROPERTY);
                nm = request.getHeader(MESSAGE_HEADER_NAME_PROPERTY);
            }
            //Send external id as header for POST
            extId = request.getHeader(MESSAGE_HEADER_EXTID_PROPERTY);
            event = createEvent(ns, nm, session, destination.getConfig());
            if (extId != null) {
                event.setExtId(extId);
            }
            //Get the payload from the request's body
            byte[] input = getPostData(request, session);
            deserializePayload(event, session, input, encoding, destination.isJSONPayload());
        }
        populateEventProperties(event, request);
        return event;
    }


    protected SimpleEvent deserializeResponse(
            HttpChannelClientResponse response,
            RuleSession session,
            HttpDestination destination,
            Class<?> responseEventType)
            throws Exception {
        SimpleEvent event;
        //Get namespace ns. Should be sent as query string for get request
        String extId;
        //Send external id as header for POST
        extId = response.getFirstHeaderValue(MESSAGE_HEADER_EXTID_PROPERTY);
        if (responseEventType == null) {
            if (destination.isDeSerializingEventType()) {
                final String ns = response.getFirstHeaderValue(MESSAGE_HEADER_NAMESPACE_PROPERTY);
                final String nm = response.getFirstHeaderValue(MESSAGE_HEADER_NAME_PROPERTY);
                event = createEvent(ns, nm, session, destination.getConfig());
            } else {
                event = createEvent(null, null, session, destination.getConfig());
            }
        } else {
            event = createEventInstance(responseEventType, session);
        }
        if (extId != null) {
            event.setExtId(extId);
        }
        Object payload = response.getEntity();
        deserializePayload(event, session, payload, destination.isJSONPayload());
        populateEventProperties(event, response);
        return event;
    }


    protected SimpleEvent createEventInstance(Class<?> clz,
                                              RuleSession session) throws Exception {
        Constructor cons = clz.getConstructor(long.class);
        return (SimpleEvent) cons.newInstance(session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz));
    }

    protected SimpleEvent createEvent(String ns,
                                      String nm,
                                      RuleSession session,
                                      DestinationConfig config) throws Exception {
        ExpandedName en;
        SimpleEvent event;
        if ((ns == null) || (nm == null) ||
                (ns.length() == 0) || (nm.length() == 0)) {
            en = config.getDefaultEventURI();
        } else {
            en = ExpandedName.makeName(ns, nm);
        }
        event = (SimpleEvent) session.getRuleServiceProvider().getTypeManager().createEntity(en);
        return event;
    }

    public Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
        return super.serialize(event, context);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Extract post data accompanied with HTTP request
     *
     * @param request
     * @param ruleSession
     * @return
     * @throws IOException
     * @throws Exception
     */
    protected byte[] getPostData(final HttpChannelServerRequest request,
            final RuleSession ruleSession) throws Exception {
        //Get the inputstream
        InputStream is = request.getInputStream();
        if (is != null) {
            //create channel
            ReadableByteChannel channel = Channels.newChannel(is);
            try {
                //get content length
                int contentLength = request.getContentLength();
                LOGGER.log(Level.DEBUG, "Content Length %s", contentLength);
                byte[] contentAsArray = null;
                if (contentLength > 0) {
                    //we have content length
                    //create byte buffer, which can hold the complete content
                    ByteBuffer buffer = ByteBuffer.allocate(contentLength);
                    //start reading using the channel
                    while(channel.read(buffer) > 0) {
                        //buffer will keep filling up
                    }
                    //check if we read less then the content length
                    if (buffer.hasRemaining() == true) {
                    	//we read less then the content length
                        LOGGER.log(Level.WARN, "Expecting %s, received %s", contentLength, buffer.position() + 1);
                        //we need to copy only what we read from the channel which is based on position
                        contentAsArray = new byte[buffer.position()+1];
                        buffer.get(contentAsArray);
                    }
                    else {
                        //we can do this since we create the buffer with array allocation
                        contentAsArray = buffer.array();
                    }
                    //check if we have more data then the content length
                    if (channel.read(ByteBuffer.allocateDirect(1)) > 0) {
                        LOGGER.log(Level.WARN, "Received more content then the content length of %s, stopping at content length", contentLength);
                    }
                }
                else {
                    //create byte buffer, we will read 4K at a time
                    ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
                    //create a list to store data from each individual read
                    ArrayList<byte[]> buffers = new ArrayList<byte[]>();
                    int totalRead = 0;
                    while(channel.read(buffer) > 0) {
                    	byte[] temp = new byte[buffer.position()+1];

                    	//rewind the buffer
                        ((Buffer)buffer).rewind();
                        
                        //copy to a temp array and store in the buffers list
                        buffer.get(temp);
                        buffers.add(temp);
                        totalRead = totalRead + temp.length;
                        //clear the buffer for next read
                        ((Buffer)buffer).clear();
                    }
                    
                    contentAsArray = new byte[totalRead];
                    int contentArrayIndex = 0;
                    for (byte[] data : buffers) {
                        System.arraycopy(data, 0, contentAsArray, contentArrayIndex, data.length);
                        contentArrayIndex += data.length;
                    }
                }
                String header = request.getHeader(HTTPHeaders.CONTENT_TRANFER_ENCODING.value());
                if (header != null && header.trim().length() != 0 && "base64".equalsIgnoreCase(header)) {
                    try {
                        String s = convertByteArrayToString(contentAsArray, request.getCharacterEncoding());
                        contentAsArray = XsBase64Binary.parseBase64Binary(s);// incase of binary data come as base64 encoded
                    } catch (Exception e) {
                        ruleSession.assertObject(new AdvisoryEventImpl(ruleSession.getRuleServiceProvider().getIdGenerator().nextEntityId(AdvisoryEventImpl.class),
                                null, AdvisoryEvent.CATEGORY_ENGINE, e.getClass().getName(), e.getMessage()), true);
                    }
                }
                return contentAsArray;
            } finally {
                channel.close();
            }
        }
        return new byte[]{};
    }


    /**
     * Map message headers/parameters to events properties.
     * <p>
     * If a property exists in message headers, it will be used, else property will
     * be searched for in the parameters.
     * </p>
     *
     * @param event
     * @param request
     * @throws Exception
     */
    protected void populateEventProperties(final SimpleEvent event,
                                           final HttpChannelServerRequest request) throws Exception {
    	if (event != null) {
    		for (String propName : event.getPropertyNames()) {
    			String header = getHeader(propName);
    			String value = request.getHeader(header);
    			if (null == value) {
    				//Look for it in parameters
    				final String[] paramValue = request.getParameterValues(propName);
    				//Massage it
    				value = StringUtilities.join(paramValue, ",");
    			}
    			//Fix For NumberFormatException in MM as a result of blank property values.
    			if (null != value && value.length() > 0) {
    				event.setProperty(propName, value);
    			}
    		}
    	}
    }

    /**
     * Make sure that parameter value maps to a value consistent with the type defined in the model.
     *
     * @param event
     * @param response
     * @throws Exception
     */
    protected void populateEventProperties(final SimpleEvent event,
                                           final HttpChannelClientResponse response) throws Exception {
        for (String propName : event.getPropertyNames()) {
            String header = getHeader(propName);
            final String value = response.getFirstHeaderValue(header);
            if (null != value) {
                event.setProperty(propName, value);
            } else {
                //Look for parameters
                Object param = response.getParameter(propName);
                if (null != param) {
                    event.setProperty(propName, param);
                }
            }
        }
    }

    /**
     * Use this method when payload format is sure to be JSON or non-JSON.
     * 
     * @param event
     * @param session
     * @param message
     * @param encoding
     * @param isJSONPayload True for JSON payload, False otherwise.
     * @throws Exception
     */
    protected void deserializePayload(final SimpleEvent event,
                                      final RuleSession session,
                                      final Object message,
                                      final String encoding,
                                      final boolean isJSONPayload) throws Exception {
        //Get the RSP from this
        RuleServiceProvider rsp = session.getRuleServiceProvider();
        PayloadFactory payloadFactory = rsp.getTypeManager().getPayloadFactory();
        if (payloadFactory != null) {
            EventPayload payload = null;
            //Get the schema associated with payload
            SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());
            if (payloadTerm == null || payloadTerm.getSchema() == null) {
                if (message != null) {
                    payload = (message instanceof byte[]) ? new ObjectPayload(message, encoding) : new ObjectPayload(message);
                }
            } else {
                ExpandedName exName = event.getExpandedName();
                try {
                    if (message instanceof String) {
                        String msg = (String) message;
                        if (msg.length() > 0) {
                            //If XML is sent as string
                            payload = payloadFactory.createPayload(exName, (String) message, isJSONPayload);
                        }
                    }
                    if (message instanceof byte[]) {
                        payload = payloadFactory.createPayload(exName, (byte[]) message, isJSONPayload);
                        LOGGER.log(Level.DEBUG, "Payload String conversion %s", payload.toString());
                    }
                    if (message instanceof XiNode) {
                        payload = payloadFactory.createPayload(exName, (XiNode) message);
                    }
                } catch (RuntimeException re) {
                    PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(re, message, event, session);
                }
            }
            event.setPayload(payload);
        }
    }

    protected void deserializePayload(final SimpleEvent event,
                                      final RuleSession session,
                                      final Object message,
                                      final boolean isJSONPayload) throws Exception {
        deserializePayload(event, session, message, System.getProperty("file.encoding"), isJSONPayload);
    }
    
    /**
     * Use this method when payload format is unknown
     * 
     * @param event
     * @param session
     * @param message
     * @param encoding
     * @throws Exception
     */
    protected void deserializePayload(final SimpleEvent event,
                                      final RuleSession session,
                                      final Object message,
                                      final String encoding) throws Exception {
        //Get the RSP from this
        RuleServiceProvider rsp = session.getRuleServiceProvider();
        PayloadFactory payloadFactory = rsp.getTypeManager().getPayloadFactory();
        if (payloadFactory != null) {
            EventPayload payload = null;
            //Get the schema associated with payload
            SmParticleTerm payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());
            if (payloadTerm == null || payloadTerm.getSchema() == null) {
                if (message != null) {
                    payload = (message instanceof byte[]) ? new ObjectPayload(message, encoding) : new ObjectPayload(message);
                }
            } else {
                ExpandedName exName = event.getExpandedName();
                try {
                    if (message instanceof String) {
                        String msg = (String) message;
                        if (msg.length() > 0) {
                            //If XML is sent as string
                            payload = payloadFactory.createPayload(exName, (String) message);
                        }
                    }
                    if (message instanceof byte[]) {
                        payload = payloadFactory.createPayload(exName, (byte[]) message);
                        LOGGER.log(Level.DEBUG, "Payload String conversion %s", payload.toString());
                    }
                    if (message instanceof XiNode) {
                        payload = payloadFactory.createPayload(exName, (XiNode) message);
                    }
                } catch (RuntimeException re) {
                    PayloadExceptionUtil.assertPayloadExceptionAdvisoryEvent(re, message, event, session);
                }
            }
            event.setPayload(payload);
        }
    }
    
    protected void deserializePayload(final SimpleEvent event,
            final RuleSession session,
            final Object message) throws Exception {
    	deserializePayload(event, session, message, System.getProperty("file.encoding"));
    }
}
