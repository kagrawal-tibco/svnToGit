package com.tibco.cep.driver.http.serializer;

import static com.tibco.cep.driver.http.HttpChannelConstants.CUSTOM_HEADER_PREFIX;
import static com.tibco.cep.driver.http.HttpChannelConstants.DEFAULT_HTTP_CONTENT_TYPE_HEADER;
import static com.tibco.cep.driver.http.HttpChannelConstants.HTTP_STATUS_CODE_HEADER;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAME_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.UTF8_ENCODING;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.util.JSONXiNodeConversionUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 9:48:40 AM
 * Do not use this class directly as Serializer/Deserializer
 */
public abstract class HTTPMessageSerializer implements EventSerializer {
    protected RuleServiceProvider RSP;

    protected BEProperties properties;

    protected Logger logger;

    public abstract SimpleEvent deserialize(Object message, SerializationContext context) throws Exception;

    public void init(ChannelManager channelManager,
                     DestinationConfig config) {
        RSP = channelManager.getRuleServiceProvider();

        this.logger = RSP.getLogger(HTTPMessageSerializer.class);

        properties = (BEProperties)RSP.getProperties();
    }
    
    public Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
        XiFactory xif = XiSupport.getXiFactory();
        if (context instanceof HttpDestination.HttpServerSerializationContext) {
            HttpChannelServerResponse response =
                    ((HttpDestination.HttpServerSerializationContext) context).getResponse();
            //Check for property
            boolean serializePropertiesAsXML =
                    properties.getBoolean("be.channel.http.propertiesAsXML", false);

            logger.log(Level.DEBUG, "XML Serialization of event properties %s ", serializePropertiesAsXML);

            if (!serializePropertiesAsXML) {
                serializeEvent(event, response, xif,
                        ((HttpDestination) context.getDestination()).isSerializingEventType());
            }  else {
                serializeEventWithPropsAsXML(event, response, xif);
            }
            response.sendResponse();
        } else if (context instanceof HttpDestination.HttpClientSerializationContext) {
            HttpChannelClient httpChannelClient = ((HttpDestination.HttpClientSerializationContext)context).getClient();
            HttpChannelClientRequest httpChannelClientReq =
                    ((HttpDestination.HttpClientSerializationContext)context).getClientRequest();
            if (httpChannelClientReq == null) {
                httpChannelClientReq = httpChannelClient.getClientRequest();
            }

            EventPayload payload = event.getPayload();
            if (HttpMethods.METHOD_POST.equals(httpChannelClientReq.getMethodType())) {
                String[] propertyNames = event.getPropertyNames();
                HashMap<String, String> nvp = new HashMap<String,String>();
                for (String propertyName : propertyNames) {
                    Object propValue = event.getProperty(propertyName);
                	if (propValue != null) {
                		String header = getHeader(propertyName);
                        nvp.put(header, propValue.toString());
                	}
                }
                try {
                    String extid = (String) event.getExtId();
                    if (extid != null) {
                        nvp.put(HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY, extid);
                    }
                } catch (Exception e) {
                    // do nothing
                }

                if (((HttpDestination) context.getDestination()).isSerializingEventType()) {
                    final ExpandedName en = event.getExpandedName();
                    nvp.put(MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
                    nvp.put(MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
                }

                httpChannelClientReq.writeContent(nvp);
                if (payload instanceof ObjectPayload) {
                    ObjectPayload objectPayload = (ObjectPayload)payload;
                    byte[] payloadBytes = (objectPayload.getObject() != null 
                    		&& objectPayload.getObject() instanceof String) 
                    		? ((String) objectPayload.getObject()).getBytes()
                    		: objectPayload.toBytes();   		
                    httpChannelClientReq.writeContent(payloadBytes);
                } else if (payload instanceof XiNodePayload) {
                    XiNodePayload xiNodePayload = (XiNodePayload)payload;
                    String payloadContent = null;
                    if (xiNodePayload.isJSONPayload()) {
                    	payloadContent = xiNodePayload.toString();
                    } else {
                    	XiNode doc = xif.createDocument();
                    	doc.appendChild(xiNodePayload.getNode());
                    	StringWriter sw = new StringWriter();
                    	DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(sw, UTF8_ENCODING);
                    	doc.serialize(handler);

                    	payloadContent = sw.getBuffer().toString();
                    }
                    httpChannelClientReq.writeContent(payloadContent);
                }
            } else if(httpChannelClientReq.getMethodType().equals(HttpMethods.METHOD_GET)) {
                String [] propertyNames = event.getPropertyNames();
                HashMap<String, String> nvp = new HashMap<String, String>();
                for (String propertyName : propertyNames) {
                    Object propValue = event.getProperty(propertyName);
                	if (propValue != null) {
                		String header = getHeader(propertyName);
                        nvp.put(header, propValue.toString());
                	}
                }
                try {
                    String extid = (String) event.getExtId();
                    if (extid != null) {
                        nvp.put(HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY, extid);
                    }
                } catch (Exception e) {
                    // do nothing
                }
                nvp.put("payload",event.getPayloadAsString());
                httpChannelClientReq.writeContent(nvp);

                if (((HttpDestination) context.getDestination()).isSerializingEventType()) {
                    final ExpandedName en = event.getExpandedName();
                    httpChannelClientReq.setParameter(MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
                    httpChannelClientReq.setParameter(MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
                }
            }
        }
        return null;
    }
    

    /**
     *
     * @param event -> The event to serialize
     * @param serverResponse
     * @param xif
     * @throws Exception
     */
    private void serializeEvent(
            SimpleEvent event,
            HttpChannelServerResponse serverResponse,
            XiFactory xif,
            boolean includeEventType)
            throws Exception {
        String[] propertyNames = event.getPropertyNames();
        EventPayload payload = event.getPayload();

        int httpStatus; boolean isContentTypeSet = false;
        for (String property : propertyNames) {
        	Object value = event.getProperty(property);
        	if (value != null) {
                logger.log(Level.DEBUG, "Event Property %s value %s ", property, value);
                String header = getHeader(property);
                // check if http Status code has been set
                if (header.equalsIgnoreCase(HTTP_STATUS_CODE_HEADER)) {
                	if (! (value instanceof Integer || value instanceof String)) {
                		throw new Exception("Http Status code needs to be an Integer/String value.");
                	}
                	httpStatus = (value instanceof String) ? Integer.parseInt((String)value) : (Integer) value;
                	serverResponse.setStatus(httpStatus);
                } else {
                	if (header.equalsIgnoreCase(HTTPHeaders.CONTENT_TYPE.value())) isContentTypeSet = true;
                	serverResponse.setHeader(header, value.toString());
                }
        	}
        }
        
        // set default content type if none is set, typically for POST/PUT containing payload
        if (payload != null && !isContentTypeSet) {
        	serverResponse.setContentType(DEFAULT_HTTP_CONTENT_TYPE_HEADER);
        }

        if (includeEventType) {
            final ExpandedName en = event.getExpandedName();
            serverResponse.setHeader(MESSAGE_HEADER_NAMESPACE_PROPERTY, en.getNamespaceURI());
            serverResponse.setHeader(MESSAGE_HEADER_NAME_PROPERTY, en.getLocalName());
        }

        if (payload instanceof ObjectPayload) {
            serializeObjectPayload((ObjectPayload)payload, serverResponse);
            
        } else if (payload instanceof XiNodePayload) {
        	// check if XiNode with JSON destination, if yes, convert
        	if (checkForJSONPayloadConversion(event)) {
        		if (payload instanceof XiNodePayload) {
	            	XiNodePayload payloadNode = (XiNodePayload)payload;
	            	EventPayload jsonEventPayload = JSONXiNodeConversionUtil.convertXiNodeToJSON(payloadNode.getNode(), true);
	            	if (jsonEventPayload != null) {
	            		event.setPayload(jsonEventPayload);
	            		serializeObjectPayload((ObjectPayload)jsonEventPayload, serverResponse);
	            	}
        		}
        	} else {
        		XiNodePayload xiNodePayload = (XiNodePayload)payload;
        		XiNode doc = xif.createDocument();
        		doc.appendChild(xiNodePayload.getNode());

        		DefaultXmlContentSerializer handler =
        				new DefaultXmlContentSerializer(serverResponse.getWriter(), UTF8_ENCODING);
        		doc.serialize(handler);
        	}
        }
    }

    /**
     * This is not supposed to be exposed as a regular feature
     * @param event
     * @param serverResponse
     * @param xif
     * @throws Exception
     */
    private void serializeEventWithPropsAsXML(SimpleEvent event,
                                              HttpChannelServerResponse serverResponse,
                                              XiFactory xif) throws Exception {
        EventPayload payload = event.getPayload();
        if (payload instanceof ObjectPayload) {
            serializeObjectPayload((ObjectPayload)payload, serverResponse);
        }  else {
            XiNode doc = xif.createDocument();
            XiNode child = xif.createElement(ExpandedName.makeName("event"));
            doc.appendChild(child);
            event.toXiNode(child);
            DefaultXmlContentSerializer handler =
                    new DefaultXmlContentSerializer(serverResponse.getWriter(), "UTF-8");
            doc.serialize(handler);
        }
    }

    private void serializeObjectPayload(ObjectPayload objectPayload,
                                        HttpChannelServerResponse serverResponse) throws Exception {
        String object = objectPayload.toString();
        Writer writer = serverResponse.getWriter();
        writer.write(object);
    }
    
    /**
     * Convert the event property name to header name  by replacing '-'
     * @param eventPropertyName
     * @return
     */
    final protected String getHeader(String eventPropertyName) {
        String header = eventPropertyName.replaceAll("_", "-");
    	if (HTTPHeaders.contains(header.toLowerCase())) {
    		return header;
        }
    	
    	if (header != null && header.startsWith(CUSTOM_HEADER_PREFIX)) {
    		return header.substring(CUSTOM_HEADER_PREFIX.length());
    	}
    	
        return eventPropertyName;
    }
    
    private static boolean checkForJSONPayloadConversion(SimpleEvent evt) throws Exception {
    	RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
    	if (rsp == null) {
    		// this can happen in API mode
    		return false;
    	}
    	Channel.Destination destination = rsp.getChannelManager().getDestination(evt.getDestinationURI());
    	if (destination instanceof HttpDestination) 
    		return (((HttpDestination)destination).isJSONPayload() && evt.getPayload() instanceof XiNodePayload);

    	return false;
    }
}
