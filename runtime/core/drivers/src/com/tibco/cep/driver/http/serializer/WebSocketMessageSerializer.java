/**
 * 
 */
package com.tibco.cep.driver.http.serializer;

import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_EXTID_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY;
import static com.tibco.cep.driver.http.HttpChannelConstants.MESSAGE_HEADER_NAME_PROPERTY;

import java.io.ByteArrayInputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tibco.be.model.rdf.primitives.RDFBooleanTerm;
import com.tibco.be.model.rdf.primitives.RDFDateTimeTerm;
import com.tibco.be.model.rdf.primitives.RDFDoubleTerm;
import com.tibco.be.model.rdf.primitives.RDFIntegerTerm;
import com.tibco.be.model.rdf.primitives.RDFLongTerm;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFStringTerm;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.WebSocketSerializationContext;
import com.tibco.cep.driver.http.server.impl.websocket.WSEndpointContext;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.channel.ChannelProperties;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.util.JSONXiNodeConversionUtil;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.Element;

/**
 * @author vpatil
 *
 */
public class WebSocketMessageSerializer extends RESTMessageSerializer {
	private static String ISO_8601_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	/* (non-Javadoc)
	 * @see com.tibco.cep.driver.http.serializer.HTTPMessageSerializer#deserialize(java.lang.Object, com.tibco.cep.runtime.channel.SerializationContext)
	 */
	@Override
	public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception {
		SimpleEvent event = null;
        if (message == null) return null;
        
		WSEndpointContext endpointContext = (WSEndpointContext) message;
		
		HttpDestination destination = (HttpDestination) context.getDestination();
		if (destination == null) {
			return null;
		}
		
		RuleSession session = context.getRuleSession();
		if (context instanceof HttpDestination.WebSocketSerializationContext) {
			
		} else {
			event = (destination.isJSONPayload()) ? deserializeFromJSON(destination, session, endpointContext) 
					: deserializeFromXML(destination, session, endpointContext) ;
		}
		return event;
	}
	
	@Override
	public Object serialize(SimpleEvent event, SerializationContext context) throws Exception {
		HttpDestination destination = (HttpDestination) ((WebSocketSerializationContext)context).getDestination();
		return (destination.isJSONPayload()) ? serializeToJSON(event, destination) : serializeToXML(event, destination);
	}
	
	private String serializeToJSON(SimpleEvent event, HttpDestination destination) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		
		Map<String, Object> jsonStructure = new LinkedHashMap<String, Object>();
		
		// system properties
		Map<String, Object> attributesMap = new LinkedHashMap<String, Object>();
		attributesMap.put(ChannelProperties.RESERVED_EVENT_PROP_EVENT_ID, event.getId());
		if (event.getExtId() != null) attributesMap.put(ChannelProperties.RESERVED_EVENT_PROP_EXT_ID, event.getExtId());
		
		if (destination.isSerializingEventType() && event.getType() != null) {
			if (event.getType().startsWith("{")) {
				attributesMap.put("type", event.getType().substring(1, event.getType().indexOf('}')));
			} else {
				attributesMap.put("type", event.getType());
			}
		}
		jsonStructure.put("attributes", attributesMap);
		
		// user properties
		for (String propertyName : event.getPropertyNames()) {
			Object propValue = event.getPropertyValue(propertyName);
			if (propValue != null) jsonStructure.put(propertyName, propValue);
		}
		
		//payload
		EventPayload payload = event.getPayload();
		if (payload != null) {
			if (payload instanceof XiNodePayload) {
				XiNodePayload payloadNode = (XiNodePayload)payload;
				EventPayload jsonPayload = JSONXiNodeConversionUtil.convertXiNodeToJSON(payloadNode.getNode(), true);
				TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
				HashMap<String,Object> jsonMap = mapper.readValue(jsonPayload.toString(), typeRef);
				jsonStructure.put("payload", jsonMap);
			} else if (payload instanceof ObjectPayload){
				jsonStructure.put("payload", ((ObjectPayload)payload).toString());
			}
		}
		
		return mapper.writeValueAsString(jsonStructure);
	}
	
	private String serializeToXML(SimpleEvent event, HttpDestination destination) throws Exception {
		String eventNS = null;
		String name = null;
		
		if (destination.isSerializingEventType() && event.getType() != null) {
			if (event.getType().startsWith("{")) {
				eventNS = event.getType().substring(1, event.getType().indexOf('}'));
				name = event.getType().substring(event.getType().lastIndexOf('}') + 1, event.getType().length());
			} else {
				eventNS = event.getType();
				name = eventNS.substring(eventNS.lastIndexOf("/") + 1, eventNS.length());
			}
		}
		
		// root element name creation
		if (name == null) name = "Event";
		if (eventNS != null) name = "ns0:"+name;
		XiNode node = XiSupport.getXiFactory().createElement(ExpandedName.makeName(name));
		
		if (eventNS != null) node.setNamespace("ns0", eventNS);
		node.setAttributeStringValue(ExpandedName.makeName(Entity.ATTRIBUTE_ID), String.valueOf(event.getId()));
		
        if (event.getExtId() != null) {
            node.setAttributeStringValue(ExpandedName.makeName(Entity.ATTRIBUTE_EXTID), event.getExtId());
        }
        for (String propName : event.getPropertyNames()) {
        	node.appendElement(ExpandedName.makeName(propName)).appendText(new XsString(toStringValue(event.getPropertyValue(propName))));
        }
        
        EventPayload payload = event.getPayload();
        if (payload instanceof XiNodePayload) {
        	node.appendElement(ExpandedName.makeName("payload")).appendChild(((XiNodePayload)payload).getNode());
        } else if (payload instanceof ObjectPayload) {
        	node.appendElement(ExpandedName.makeName("payload")).appendText(new XsString(((ObjectPayload)payload).toString()));
        }
        
        return XiSerializer.serialize(node);
	}
	
	private SimpleEvent deserializeFromJSON(HttpDestination destination, RuleSession session, WSEndpointContext endpointContext) throws Exception {
		SimpleEvent event = null;
      
		Object data = endpointContext.getData();
        String eventJson = (data instanceof String) ? new String((String)data) : new String((byte[])data);
        
        ObjectMapper mapper = new ObjectMapper();
		Object eventData = mapper.readValue(eventJson, Object.class);
		if (!(eventData instanceof Map)) throw new RuntimeException("Data should be in JSON format");
		
		Map<String, Object> eventDataMap = (Map<String, Object>) eventData;
		String ns = (String)eventDataMap.get(MESSAGE_HEADER_NAMESPACE_PROPERTY);
		String nm = (String)eventDataMap.get(MESSAGE_HEADER_NAME_PROPERTY);
        
        event = createEvent(ns, nm, session, destination.getConfig());
        String extId = (String)eventDataMap.get(MESSAGE_HEADER_EXTID_PROPERTY);
        if (extId != null) {
            event.setExtId(extId);
        }
        
        deserializePayload(event, session, eventDataMap.get("payload"), destination.isJSONPayload());
        
        // populate properties
        Object value = null;
        if (event != null) {
    		for (String propName : event.getPropertyNames()) {
    			value = eventDataMap.get(propName);
    			if (value != null) {
    				event.setProperty(propName, value);
                }
    		}
        }
        
        return event;
	}
	
	private SimpleEvent deserializeFromXML(HttpDestination destination, RuleSession session, WSEndpointContext endpointContext) throws Exception {
		SimpleEvent event = null;
		
		Object data = endpointContext.getData();
		byte[] xmlBytes = (data instanceof String) ? ((String)data).getBytes() : (byte[])data;
		
		XiNode root = XiSupport.getParser().parse(new InputSource(new ByteArrayInputStream(xmlBytes)));
		Iterator content = root.getChildren();
		
		while (content.hasNext()) {
			Object eventContent = content.next();

			com.tibco.cep.designtime.model.event.Event designTimeEvent = null;
			com.tibco.cep.designtime.model.Entity entity = getEntity(destination.getConfig().getDefaultEventURI().namespaceURI.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length()));
			if (entity != null && entity instanceof com.tibco.cep.designtime.model.event.Event) {
				designTimeEvent = (com.tibco.cep.designtime.model.event.Event) entity;
			}
			
			Iterator eventProps = ((Element)eventContent).getChildren();
			String ns = null;
			String nm = null;
			XiNode payload = null;
			Map<String, Object> eventProperties = new HashMap<String, Object>();
			
			while (eventProps.hasNext()) {
				Object p = eventProps.next();
				if (!(p instanceof Element)) {
					continue;
				}
				Element prop = (Element)p;
				if ("payload".equals(prop.getName().getLocalName())) {
					XiNodeUtilities.cleanTextNodes(prop);
					payload = prop.getFirstChild();
				} else if (ChannelProperties.BE_NAMESPACE.equals(prop.getName().getLocalName())) {
					ns = prop.getStringValue();
				} else if (ChannelProperties.BE_NAME.equals(prop.getName().getLocalName())) {
					nm = prop.getStringValue();
				} else {
					eventProperties.put(prop.getName().getLocalName(), getValueByType(designTimeEvent.getPropertyType(prop.getName().localName), prop.getStringValue()));
				}
			}
			
			event = createEvent(ns, nm, session, destination.getConfig());
			event.setExtId(((Element)eventContent).getAttributeStringValue(ExpandedName.makeName(Entity.ATTRIBUTE_EXTID)));
			deserializePayload(event, session, payload, destination.isJSONPayload());
			
			// populate properties
			if (eventProperties.size() > 0) {
				Object value = null;
				for (String propName : event.getPropertyNames()) {
	    			value = eventProperties.get(propName);
	    			if (value != null) {
	    				event.setProperty(propName, value);
	                }
	    		}
			}
			
			break;
		}
		return event;
	}
	
	private com.tibco.cep.designtime.model.Entity getEntity(String entityURI) throws ClassNotFoundException {
		RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
		return RSPM.getDefaultProvider().getProject().getOntology().getEntity(entityURI);
	}
	
	private Object getValueByType(RDFPrimitiveTerm rdfType, String value) throws Exception {
		Object convertedValue = null;
		
		if (rdfType instanceof RDFStringTerm) convertedValue = value;
		else if (rdfType instanceof RDFIntegerTerm) convertedValue = Integer.parseInt(value);
		else if (rdfType instanceof RDFLongTerm) convertedValue = Long.parseLong(value);
		else if (rdfType instanceof RDFDoubleTerm) convertedValue = Double.parseDouble(value);
		else if (rdfType instanceof RDFBooleanTerm) convertedValue = Boolean.parseBoolean(value);
		else if (rdfType instanceof RDFDateTimeTerm) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ISO_8601_FULL_FORMAT);
			ZonedDateTime zdt = ZonedDateTime.parse(value, dtf);
	    	convertedValue = GregorianCalendar.from(zdt);
		}

		return convertedValue;
	}
	
	private String toStringValue(Object value) {
		if (value instanceof String) return (String)value;
		else if (value instanceof Calendar) {
			Calendar cal = (Calendar)value;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ISO_8601_FULL_FORMAT);
			ZonedDateTime zdt = ((GregorianCalendar)cal).toZonedDateTime();
			return dtf.format(zdt);
		}
		else return String.valueOf(value);
	}
}
