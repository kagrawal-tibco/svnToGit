package com.tibco.cep.driver.as.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.as.space.DateTime;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.be.model.types.Converter;
import com.tibco.be.model.types.xsd.DatetimeConverter;
import com.tibco.be.util.XiSupport;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBase64Binary;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsDecimal;
import com.tibco.xml.data.primitive.values.XsInteger;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;

public class ASUtils
{
	/**
	 * set tuple value with different types
	 * @param tuple
	 * @param valueName
	 * @param value
	 */
	public static void updateTuple(Tuple tuple, FieldType fieldType, String key, Object value){
		if (null != value) {
    		if(value instanceof String){
    			if (fieldType.equals(FieldType.CHAR)) {
    				tuple.putChar(key, ((String) value).toCharArray()[0]);
    			} else {
    				tuple.putString(key,(String)value);
    			}
    		}else if(value instanceof Integer){
    			if (fieldType.equals(FieldType.SHORT)) {
    				tuple.putShort(key, ((Integer) value).shortValue());
    			} else {
    				tuple.putInt(key,(Integer) value);
    			}
    		}else if(value instanceof Long){
    			tuple.putLong(key, (Long)value);
    		}else if(value instanceof Double){
    			if (fieldType.equals(FieldType.FLOAT)) {
    				tuple.putFloat(key, ((Double) value).floatValue());
    			} else {
    				tuple.putDouble(key, (Double)value);
    			}
    		}else if(value instanceof Boolean){
    			tuple.putBoolean(key, (Boolean)value);
    		}else if(value instanceof Calendar){
    			tuple.putDateTime(key, DateTime.create((Calendar) value));
    		}
		}
	}

	/**
	 * 
	 * @param event
	 * @param key
	 * @param value
	 */
	public static void updateEvent(SimpleEvent event, String key, Object value) throws Exception {
		if (null != value) {
    		String[] props = event.getPropertyNames();
    		boolean hasProp = false;
    		
    		//TODO can avoid this if generated setPropety function does not throw exception for invalid property name
    		for (String prop : props) {
    			if (prop.equals(key)) {
    				hasProp = true;
    				break;
    			}
    		}
    		
    		if (hasProp) {
        		if (value instanceof DateTime) {
        			event.setProperty(key, ((DateTime) value).getTime());
        		} else if (value instanceof Character) {
        			event.setProperty(key, String.valueOf((Character) value));
        		} else if (value instanceof Float) {
        			event.setProperty(key, Float.valueOf((Float) value).doubleValue());
        		} else if (value instanceof Short) {
        			event.setProperty(key, Short.valueOf((Short) value).intValue());
        		} else {
        			event.setProperty(key, value);
        		}
    		}
		}
	}

	
	public static XiNodePayload createXiNodePayload(Tuple tuple, SpaceDef spaceDef, SmParticleTerm payloadTerm) throws Exception {
		SmSchema schema = payloadTerm.getSchema();
        if(schema != null) {
    		XiNode payloadNode = XiSupport.getXiFactory().createElement(payloadTerm.getExpandedName());
			SmElement el = (SmElement)schema.getComponents(SmComponent.ELEMENT_TYPE).next();
			if(el != null) {
				Iterator<SmParticle> parts = el.getType().getContentModel().getParticles();
				while(parts.hasNext()) {
					SmParticle part = parts.next();
					if(part != null) {
						String name = part.getTerm().getName();
						FieldDef fd = spaceDef.getFieldDef(name);
						if(fd != null) {
							ExpandedName exName = part.getTerm().getExpandedName();
							Object value = tuple.get(name);
							if(value == null) {
								payloadNode.appendElement(exName).setAttributeStringValue(XiNodeUtilities.NIL_ATTR_QNAME, "true");
							} else {
								switch(fd.getType()) {
								case BLOB:
									//TODO look in the event payload schema to see if it's base64 or hex
									if(value != null) payloadNode.appendElement(exName, new XsBase64Binary((byte[])value));
									break;
								case BOOLEAN:
									if(value != null) payloadNode.appendElement(exName, XsBoolean.create((Boolean)value));
									break;
								case CHAR: 
									if(value != null) payloadNode.appendElement(exName, String.valueOf((Character)value));
									break;
								case DATETIME: 
									if(value != null) payloadNode.appendElement(exName, Converter.getDateTimeProperty(((DateTime)value).getTime()));
									break;
								case DOUBLE: 
									if(value != null) payloadNode.appendElement(exName, new XsDecimal(((Double)value)));
									break;
								case FLOAT: 
									if(value != null) payloadNode.appendElement(exName, new XsDecimal(((Float)value).doubleValue()));
									break;
								case INTEGER: 
									if(value != null) payloadNode.appendElement(exName, new XsInteger((Integer)value));
									break;
								case LONG: 
									if(value != null) payloadNode.appendElement(exName, new XsInteger(BigInteger.valueOf((Long)value)));
									break;
								case SHORT: 
									if(value != null) payloadNode.appendElement(exName, new XsInteger(((Short)value).intValue()));
									break;
								case STRING: 
									if(value != null) payloadNode.appendElement(exName, (String)value);
									break;
								default: 
									assert false : "encountered uknown field type " + fd.getType().name();
									break;
								}
							}
						}
					}
				}
			}
			return new XiNodePayload(payloadTerm, payloadNode);
		}
		return null;
	}
	
	
	/**
	 * fill tuple with event
	 * @param tuple
	 * @param event
	 * @throws Exception
	 */
	public static void fillTupleWithEvent(SpaceDef spaceDef, Tuple tuple, SimpleEvent event, RuleSession session) throws Exception {
		String[] names = event.getPropertyNames();
        for (String name : names) {
        	FieldDef fieldDef = spaceDef.getFieldDef(name);
        	if (null != fieldDef) {
        		FieldType fieldType = fieldDef.getType();
        		updateTuple(tuple, fieldType, name, event.getProperty(name));
        	}
        }
        EventPayload payload = event.getPayload();
        if (null != payload) {
        	Object payloadObj = payload.getObject();
        	if (payloadObj instanceof Object[][]) {
        		Object[][] payloadArray = (Object[][]) payloadObj;
            	for (Object[] blobPair : payloadArray) {
            		String blobFieldName = (String) blobPair[0];
            		byte[] blobFieldValue = (byte[]) blobPair[1];
            		tuple.putBlob(blobFieldName, blobFieldValue);
            	}
        	} else {
        		XiNode xiNode = XiFactoryFactory.newInstance().createDocument();
        		payload.toXiNode(xiNode);
        		xiNode = XiChild.getChild(xiNode, ExpandedName.makeName(EventPayload.PAYLOAD_PROPERTY));
        		if(xiNode != null) {
        			xiNode = xiNode.getFirstChild(); //root node
	    			if(xiNode != null) {
	    				xiNode = xiNode.getFirstChild(); //first child of payload
	    				for(; xiNode != null; xiNode = xiNode.getNextSibling()) {
	        				String name = xiNode.getName().getLocalName();
	        				FieldDef fieldDef = spaceDef.getFieldDef(name);
	        				if(fieldDef != null) {
	    						putXiNodeToTuple(tuple, xiNode, fieldDef.getType(), name);
	        				}
	        			}
	        		}
        		}
    		}
        }
	}
	
	protected static void putXiNodeToTuple(Tuple tuple, XiNode node, FieldType type, String name) {
		//overwrite any value from the event property with null
		if(XiNodeUtilities.isNil(node)) {
			tuple.put(name, (Object)null);
		} else if(type == FieldType.STRING){
			tuple.putString(name, node.getStringValue());
		} else {
			XmlTypedValue typed = node.getTypedValue();
			if(typed != null && typed.size() > 0) {
				XmlAtomicValue atomic = typed.getAtom(0);
				try {
					switch(type) {
					case BLOB:
						//TODO look in the event payload schema to see if it's base64 or hex
						tuple.putBlob(name, atomic.castAsBase64Binary());
						break;
					case BOOLEAN:
						tuple.putBoolean(name, atomic.castAsBoolean());
						break;
					case CHAR:
						String str = atomic.castAsString();
						if(str != null && str.length() > 0) {
								tuple.putChar(name, str.charAt(0));
						}
						break;
					case DATETIME:
						tuple.putDateTime(name, 
								DateTime.create(
										DatetimeConverter
											.convertToGregorianCalendar(atomic.castAsDateTime())));
						break;
					case DOUBLE:
						tuple.putDouble(name, atomic.castAsDouble());
						break;
					case FLOAT:
						tuple.putFloat(name, atomic.castAsFloat());
						break;
					case INTEGER:
						tuple.putInt(name, atomic.castAsInt());
						break;
					case LONG:
						tuple.putLong(name, atomic.castAsLong());
						break;
					case SHORT:
						tuple.putShort(name, atomic.castAsShort());
						break;
					default:
						assert false : "encountered uknown field type " + type.name();
						break;
					}
				} catch (XmlAtomicValueCastException xavce) {
					xavce.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * fill event with tuple
	 * @param event
	 * @param tuple
	 * @throws Exception
	 */
	public static void fillEventWithTuple(SpaceDef spaceDef, SimpleEvent event, Tuple tuple, RuleSession session) throws Exception {
        Collection<String> names = tuple.getFieldNames();
        List<String> blobFieldNames = new ArrayList<String>();
        
        for (String name : names) {
        	FieldDef fieldDef = spaceDef.getFieldDef(name);
        	if (null != fieldDef) {
            	FieldType fieldType = fieldDef.getType();
            	if (!FieldType.BLOB.equals(fieldType)) {
            		updateEvent(event, name, tuple.get(name));
            	} else {
            		blobFieldNames.add(name);
            	}
        	} else {
        		updateEvent(event, name, tuple.get(name));
        	}
        }
        
        SmParticleTerm payloadTerm = null;
        PayloadFactory payloadFactory = session.getRuleServiceProvider().getTypeManager().getPayloadFactory();
        if(payloadFactory != null) {
        	payloadTerm = payloadFactory.getPayloadElement(event.getExpandedName());
    	}
        
        if (payloadTerm != null) {
        	event.setPayload(createXiNodePayload(tuple, spaceDef, payloadTerm));
        } else if(!blobFieldNames.isEmpty()) {
        	List<Object[]> payloadList = new ArrayList<Object[]>();
        	for (String blobFieldName : blobFieldNames) {
        		Object[] blobPair = {blobFieldName, tuple.getBlob(blobFieldName)};
        		payloadList.add(blobPair);
        	}
        	Object[][] payloadArray = payloadList.toArray(new Object[payloadList.size()][2]);
        	ObjectPayload payload = new ObjectPayload(payloadArray);
        	event.setPayload(payload);
        }
	}

	private static Object convertTuple(SpaceDef spaceDef, ExpandedName eventName, Tuple tuple) throws Exception {
		Object result = null;
		if (null != eventName) {
			result = convertTuple2SimpleEvent(spaceDef, eventName, tuple);
		} else {
			result = convertTuple2Objects(tuple);
		}
		return result;
	}

	/**
	 * 
	 * @param asDestination
	 * @param tuple
	 * @return
	 */
	public static Object convertTuple(IASDestination asDestination, SimpleEvent eventTemplate, Tuple tuple) throws Exception {
		Object result = null;
		if (null != tuple) {
			ExpandedName eventName = asDestination.getConfig().getDefaultEventURI();
			if (null == eventName && null != eventTemplate) {
				eventName = eventTemplate.getExpandedName();
			}
			SpaceDef  spaceDef = asDestination.getSpaceDef();
			result = convertTuple(spaceDef, eventName, tuple);
		}
		return result;
	}

	public static Object[] convertTuples(IASDestination asDestination, SimpleEvent eventTemplate, Collection<Tuple> tuples) throws Exception {
		List<Object> tupleObjs = new ArrayList<Object>();
		for (Tuple tuple : tuples) {
			tupleObjs.add(convertTuple(asDestination, eventTemplate, tuple));
		}
		return !tupleObjs.isEmpty() ? tupleObjs.toArray(new Object[tupleObjs.size()]) : null;
	}


	private static SimpleEvent convertTuple2SimpleEvent(SpaceDef spaceDef, ExpandedName eventName, Tuple tuple) throws Exception {
		RuleSession session = RuleSessionManager.getCurrentRuleSession();
		SimpleEvent event = (SimpleEvent) session.getRuleServiceProvider().getTypeManager().createEntity(eventName);
		fillEventWithTuple(spaceDef, event, tuple, session);
		return event;
    }

	private static Object[] convertTuple2Objects(Tuple tuple) {
    	Collection<String> names = tuple.getFieldNames();
    	List<Object[]> keyValuePairs = new ArrayList<Object[]>(names.size());
    	for (String name : names) {
    		Object value = tuple.get(name);
    		if (value instanceof DateTime) {
    			value = ((DateTime) value).getTime();
    		}
    		keyValuePairs.add(new Object[] {name, value});
    	}
    	return keyValuePairs.toArray(new Object[keyValuePairs.size()]);
	}

	public static BrowserDef getBrowserDef(String timeScope, String distributionScope, int timeout, long prefetch, long queryLimit) {
		BrowserDef browserDef = getBrowserDef(timeScope, distributionScope, timeout, prefetch);
		browserDef.setQueryLimit(queryLimit);
		return browserDef;
	}
	
	public static BrowserDef getBrowserDef(String timeScope, String distributionScope, int timeout, long prefetch) {
		BrowserDef browserDef = BrowserDef.create();

		if (timeScope.equalsIgnoreCase("snapshot")) {
			browserDef.setTimeScope(BrowserDef.TimeScope.SNAPSHOT);
		}
		else if (timeScope.equalsIgnoreCase("all")) {
			browserDef.setTimeScope(BrowserDef.TimeScope.ALL);
		}
		else if (timeScope.equalsIgnoreCase("new")) {
			browserDef.setTimeScope(BrowserDef.TimeScope.NEW);
		}
		else {
			throw new RuntimeException("unknown BrowserDef timescope " + timeScope);
		}

		if (distributionScope.equalsIgnoreCase("all")) {
			browserDef.setDistributionScope(DistributionScope.ALL);
		}
		else if (distributionScope.equalsIgnoreCase("seeded")) {
			browserDef.setDistributionScope(DistributionScope.SEEDED);
		}
		else {
			throw new RuntimeException("unknown BrowserDef distribution scope " + distributionScope);
		}

		browserDef.setTimeout(timeout);
		browserDef.setPrefetch(prefetch);

		return browserDef;

	}

	public static SimpleEvent findSimpleEventTemplate(SimpleEvent[] keys) {
		SimpleEvent eventTemplate = null;
		for (SimpleEvent event : keys) {
			if (null != event) {
				eventTemplate = event;
			}
		}
		return eventTemplate;
    }

}
