/**
 * 
 */
package com.tibco.be.functions.event;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tibco.cep.driver.util.PayloadExceptionUtil;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmParticleTerm;

/**
 * Parsing JSON as a document structure and converting it into a Concept
 * 
 * @author vpatil
 */
public class JSONEventInstance {
	private SimpleEvent event;
	private Stack<Object> elements;
	private RuleSession session;
	
	private boolean isAttribute;
	private boolean isExtIdEntry;
	private boolean isPayloadEntry;
	
	/**
	 * Constructor
	 * 
	 * @param cept
	 * @param session
	 */
	public JSONEventInstance(RuleSession session, SimpleEvent evt) {
		this.session = session;
		this.event = evt;
		elements = new Stack<Object>();
		
		elements.push(event);
	}
	
	/**
	 * Parse JSON data, token by token
	 * 
	 * @param reader
	 * @throws Exception 
	 */
	public void parseJSON(JsonParser parser) throws Exception {
        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();

            if (token == null)
                break;
            
            switch(token) {
            
            case START_OBJECT:
               	parseJSON(parser);
                break;
                
            case END_OBJECT:
            	if (!elements.isEmpty() && !isAttribute) {
                   	endJSONEntry();
               	}            			
           		if (isAttribute) isAttribute = !isAttribute;
                break;
                
            case FIELD_NAME:
            	parseJSONEntry(parser.getCurrentName());
            	break;
                
            case VALUE_STRING:
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
            case VALUE_FALSE:
            case VALUE_TRUE:
            	handlePrimitiveValue(parser, token);
                break;
                
            case VALUE_NULL:
                break;
            default:
                throw new RuntimeException("Invalid Token");
            }
        }
	}
	
	/**
	 * Handle primitive Values
	 *
	 * @param reader
	 * @param token
	 * @throws IOException
	 */
	private void handlePrimitiveValue(JsonParser parser, JsonToken token) throws IOException {
		if (token.equals(JsonToken.VALUE_STRING)) {
			parseJSONValue(parser.getValueAsString());
		} else if (token == JsonToken.VALUE_NUMBER_INT) {
			Object numericValue = null;
			try {
				numericValue = parser.getValueAsInt();
			} catch (Exception exception) {
				numericValue = parser.getValueAsLong();
			}
			parseJSONValue(numericValue);
		} else if(token == JsonToken.VALUE_NUMBER_FLOAT) {
			parseJSONValue(parser.getValueAsDouble());
		} else if (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE) {
			parseJSONValue(parser.getValueAsBoolean());
		}
	}
	
	/**
	 * Parse JSON entry
	 * 
	 * @param name
	 * 
	 * @return
	 */
	private boolean parseJSONEntry(String name) {
		try {
			isExtIdEntry = false;
			    		
    		if (name.equals("attributes")) {
				isAttribute = true;
			} else if (name.equals("extId") && isAttribute) {
				isExtIdEntry = true;
			} else if (name.equals("payload")) {
				isPayloadEntry = true;
			} else if (event != null) {
    			elements.push(name);
			}
    	} catch (Exception exception) {
    		throw new RuntimeException(exception);
    	}
		
		return true;
	}
	
	/**
	 * Parse the JSON value
	 * 
	 * @param value
	 */
	private void parseJSONValue(Object value) {
		if (!elements.empty()) {
			if (event != null) {
				if (isExtIdEntry) { 
					event.setExtId((String)value);
				} else if (isPayloadEntry) {
					String defEncoding = "UTF-8";
					try {
						byte[] payloadData = ((String)value).getBytes(defEncoding);
						setPayload(event, session, payloadData, defEncoding);
					} catch(Exception unsupportedEncoding) {
						throw new RuntimeException("Error setting the event payload.");
					}
					isPayloadEntry = !isPayloadEntry;
				} else {
					String propertyName = (String)elements.pop();
					try {
						event.setProperty(propertyName, value);
					} catch(Exception exception) {
						throw new RuntimeException("Error setting event property[" + propertyName + "] to value[" + value.toString() + "].");
					}
				}
			}
		}
    }
	
	/**
	 * End JSON entry to pop the parsed entry 
	 */
	private void endJSONEntry() {
		if (!elements.empty() && elements.size() > 1) {
			elements.pop();
		} else {
			elements.peek();
		}
    }
	
	/**
	 * Create a new Instance
	 * 
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	private Object newInstance (Class clz) throws Exception {
		long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
		Constructor cons = clz.getConstructor(new Class[] {long.class});
		Object o = cons.newInstance(new Object[] {new Long(id)});
		return o;
	}
	
	/**
	 * Fetch the parsed Event
	 * 
	 * @return
	 */
	public SimpleEvent getEvent() {
		return (SimpleEvent) elements.pop();
	}
	
	/**
	 * Set the event Payload
	 * 
	 * @param event
	 * @param session
	 * @param message
	 * @param encoding
	 * @throws Exception
	 */
	private void setPayload(final SimpleEvent event,
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
							payload = payloadFactory.createPayload(exName, (String) message);
						}
					}
					if (message instanceof byte[]) {
						payload = payloadFactory.createPayload(exName, (byte[]) message);
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
}

