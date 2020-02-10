package com.tibco.cep.driver.ftl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringBufferInputStream;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.tibco.cep.driver.ftl.FTLConstants;
import com.tibco.cep.driver.ftl.FTLDestination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Inbox;
import com.tibco.ftl.Message;
import com.tibco.ftl.Realm;
import com.tibco.ftl.TibDateTime;
import com.tibco.ftl.TibProperties;
import com.tibco.ftl.jni.NativeInbox;
import com.tibco.xml.data.primitive.ExpandedName;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import static com.tibco.cep.driver.ftl.FTLConstants.BE_NAME;
import static com.tibco.cep.driver.ftl.FTLConstants.BE_NAMESPACE;

@SuppressWarnings("deprecation")
public class FTLUtils {
	
	static FtlProperties ftlProperties = new FtlProperties();
	
	public static boolean convertStringToBoolean(String booleanStr) {
		 if(booleanStr == null || "false".equalsIgnoreCase(booleanStr.replace(" ", ""))) {
			 return false;
		 } else if ("true".equalsIgnoreCase(booleanStr.replace(" ", ""))) {
			 return true;
		 } else {
			 return false;
		}
	}
	
	public static Message fillMessageWithEvent(SerializationContext context, SimpleEvent simpleEvent) throws Exception{
		  FTLDestination dest = (FTLDestination)context.getDestination();
		  
//		  String formatsType = dest.getFormatsType();
//		  if("build-in".equals(formatsType)) {  //formats type is Built-In
//			 Message msg = null; 
//			 try {
//				   msg = dest.getRealm().createMessage(Message.TIB_BUILTIN_MSG_FMT_OPAQUE);
//			       Object opaqueFieldVal = simpleEvent.getPropertyValue(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME);
//			       if(opaqueFieldVal instanceof String) {
//			    	    String opaqueBase64Val = (String)opaqueFieldVal;
//			    	    BASE64Decoder decoder = new BASE64Decoder();
//			    	    byte[] opaqueVal = decoder.decodeBuffer(opaqueBase64Val);   
//			    	    msg.setOpaque(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME, opaqueVal);
//			       }
//			 } catch (NoSuchFieldException  e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			 }
//
//			   return msg;
//		  } else if ("dynamic".equals(formatsType)) {  //formats type is Dynamic
//			   EventPayload payload = simpleEvent.getPayload();
//			   if(payload != null) {
//				     Object obj = payload.getObject();
//				     if(obj instanceof Message) {
//				    	 return (Message)obj;
//				     }
//			   }
//		  }
		  return fillMessageWithEvent(dest, simpleEvent);
	}
	
	public static Message fillMessageWithEvent(FTLDestination dest, SimpleEvent simpleEvent) throws Exception{
		  Realm realm = dest.getRealm();
		  Message message = realm.createMessage(dest.getFormats());	  //dest.getFormatName()
		  String[] props = simpleEvent.getPropertyNames();
		  final ExpandedName en = simpleEvent.getExpandedName();
		  for(String prop : props) {
			  Object obj = simpleEvent.getPropertyValue(prop);
			  if(prop.contains(FTLConstants.ESCAPE_HYPEN)) {
				  prop = prop.replace(FTLConstants.ESCAPE_HYPEN, "-");
			  }
			  if(obj instanceof Long) {
				  message.setLong(prop, (Long)obj);
			  } else if(obj instanceof String && !"_".equals(prop.substring(0, 1))) {
				  message.setString(prop, obj.toString());
			  } else if(obj instanceof Double) {
				  message.setDouble(prop, (Double)obj);
			  } else if (obj instanceof Calendar) {
				  Calendar calendar = (Calendar)obj;
				  TibDateTime tibDateTime = new TibDateTime();
				  tibDateTime.setFromDate(calendar.getTime());
				  message.setDateTime(prop, tibDateTime);
			  } else {
				  serializeFromXML(dest, prop, (String)obj, message); 
			  }
		  }	  
		  message.setString(BE_NAME , en.getLocalName());
		  message.setString(BE_NAMESPACE , en.getNamespaceURI());
		  return message;
	}
	
	public static void serializeFromXML(FTLDestination dest, String propName, String xml, Message message) throws FTLException, IOException {
		  if(xml == null || "".equals(xml.replace(" ", ""))) {
			  return;
		  }
		  String ftlPropName = propName.substring(propName.indexOf("__") + 2, propName.lastIndexOf("_"));
		  String propType = propName.substring(propName.lastIndexOf("_") + 1);
		  if("LongArray".equals(propType)) {
			   int longSize = ((Double)getValFromXML(xml, "count(/array/element)", true)).intValue();
			   long[] longs = new long[longSize];
			   for (int i = 1; i <= longSize; i++) {
				    String longXpathExp = "/array/element[" + i + "]/@value";
				    String longStr = (String)getValue(getValFromXML(xml, longXpathExp, false));
				    long l =  Long.parseLong(longStr);
				    longs[i - 1] = l;
			   }
			   message.setArray(ftlPropName, longs);
		  } else if ("StringArray".equals(propType)) {
			  int strSize = ((Double)getValFromXML(xml, "count(/array/element)", true)).intValue(); 
			  String[] strs = new String[strSize];
			   for (int i = 1; i <= strSize; i++) {
				    String strXpathExp = "/array/element[" + i + "]/@value";  
				    String str = (String)getValue(getValFromXML(xml, strXpathExp, false));
				    strs[i - 1] = str;
			   }
			   message.setArray(ftlPropName, strs);
		  }  else if ("DoubleArray".equals(propType)) {
			  int size = ((Double)getValFromXML(xml, "count(/array/element)", true)).intValue(); 
			  double[] doubles = new double[size];
			   for (int i = 1; i <= size; i++) {
				    String dXpathExp = "/array/element[" + i + "]/@value";
				    String dStr = (String)getValue(getValFromXML(xml, dXpathExp, false));
				    double d = Double.parseDouble(dStr);
				    doubles[i - 1] = d;
			   }
			   message.setArray(ftlPropName, doubles);
		  } else if ("Opaque".equals(propType)) {
			   String opaXpathExp = "/element/@value";
			   String strOpaVal = (String)getValue(getValFromXML(xml, opaXpathExp, false));
			   
			   //BASE64Decoder decoder = new BASE64Decoder();
			   //byte[] bytes = decoder.decodeBuffer(strOpaVal);
			   Base64.Decoder decoder = Base64.getMimeDecoder();
			   byte[] bytes = decoder.decode(strOpaVal);
			   message.setOpaque(ftlPropName, bytes);
		  } else if("Message".equals(propType)) {
			   String msgXpathExp = "/element/@value";
			   String msgVal = (String)getValue(getValFromXML(xml, msgXpathExp, false));
			   
			   //BASE64Decoder decoder = new BASE64Decoder();
			  // byte[] bytes = decoder.decodeBuffer(msgVal);
			   Base64.Decoder decoder = Base64.getMimeDecoder();
			   byte[] bytes = decoder.decode(msgVal);
			   
			   Realm realm = dest.getRealm();
			   Message subMsg = realm.createMessage(bytes, bytes.length);	  
			   message.setMessage(ftlPropName, subMsg);
		  } else if ("Inbox".equals(propType)) {
			   String inboxXpathExp = "/element/@value";
			   String inboxPeer = (String)getValue(getValFromXML(xml, inboxXpathExp, false));
			   long peer = Long.parseLong(inboxPeer);
			   NativeInbox nativeInbox = new NativeInbox(peer);
			   message.setInbox(ftlPropName, nativeInbox.copy());
		  } else if ("MessageArray".equals(propType)) {
			   int size = ((Double)getValFromXML(xml, "count(/array/element)", true)).intValue(); 
			   Message[] msgArray = new Message[size];
			   for (int i = 1; i <= size; i++) {
				    String msgPathExp = "/array/element[" + i + "]/@value";
				    String msgBase64Str = (String)getValue(getValFromXML(xml, msgPathExp, false));
				    
				    //BASE64Decoder decoder = new BASE64Decoder();
				    //byte[] bytes = decoder.decodeBuffer(msgBase64Str);	
				    Base64.Decoder decoder = Base64.getMimeDecoder();
					byte[] bytes = decoder.decode(msgBase64Str);
				    Realm realm = dest.getRealm();
					Message subMsg = realm.createMessage(bytes, bytes.length);	 
					msgArray[i - 1] = subMsg;
			   }
			   message.setArray(ftlPropName, msgArray);
		  } else if ("DateTimeArray".equals(propType)) {
			   int size = ((Double)getValFromXML(xml, "count(/array/element)", true)).intValue(); 
			   TibDateTime[] tibDateTimes = new TibDateTime[size];
			   for (int i = 1; i <= size; i++) {
				    String datePathExp = "/array/element[" + i + "]/@value";
				    String dateStr = (String)getValue(getValFromXML(xml, datePathExp, false));
				    
				    long time = Long.parseLong(dateStr);
				    Date date = new Date();
				    date.setTime(time);

				    tibDateTimes[i - 1] = new TibDateTime();
				    tibDateTimes[i - 1].setFromDate(date);
			   }
			   message.setArray(ftlPropName, tibDateTimes);
		  }
	}
	
		
	public static Object getValue(Object strObj) {
		String str = strObj.toString();
		if(str.indexOf("=") == -1) {
			return null;
		}
		return str.substring(str.indexOf("=") + 2, str.lastIndexOf("\""));
	}
	
	
	public static Object getValFromXML(String xml, String xPathExp, boolean isNum) {
		InputStream inputStream = new StringBufferInputStream(xml);	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Object object = null;
		try {
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inputStream);
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xPath = xFactory.newXPath();
		XPathExpression expr = xPath.compile(xPathExp);
		if(!isNum) {
	       object = expr.evaluate(doc, XPathConstants.NODE);
		} else {
			object = expr.evaluate(doc, XPathConstants.NUMBER);
		}
		
		} catch(ParserConfigurationException pe) {
			pe.printStackTrace();
		} catch (IOException ie) {
			// TODO: handle exception
			ie.printStackTrace();
		} catch (XPathExpressionException xe) {
			xe.printStackTrace();
			// TODO: handle exception
		} catch (SAXException se) {
			// TODO: handle exception
			se.printStackTrace();
		}
		return object;
	}
	
	public static void deserializeToXML(SimpleEvent event, Message msg, String fieldName, int fieldType) throws Exception{
		   if(Message.TIB_FIELD_TYPE_OPAQUE == fieldType) {
			     event.setProperty("__" + fieldName + "_Opaque", createXMLByMessage("Opaque", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_LONG_ARRAY == fieldType) {
			     event.setProperty("__" + fieldName + "_LongArray", createXMLByMessage("LongArray", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_DOUBLE_ARRAY == fieldType) {
			    event.setProperty("__" + fieldName + "_DoubleArray", createXMLByMessage("DoubleArray", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_STRING_ARRAY == fieldType) {
			    event.setProperty("__" + fieldName + "_StringArray", createXMLByMessage("StringArray", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_MESSAGE == fieldType) {
			     event.setProperty("__" + fieldName + "_Message", createXMLByMessage("Message", msg, fieldName));			     
		   } else if (Message.TIB_FIELD_TYPE_MESSAGE_ARRAY == fieldType) {
			     event.setProperty("__" + fieldName + "_MessageArray", createXMLByMessage("MessageArray", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_DATETIME_ARRAY == fieldType) {
			     event.setProperty("__" + fieldName + "_DateTimeArray", createXMLByMessage("DateTimeArray", msg, fieldName));
		   } else if (Message.TIB_FIELD_TYPE_INBOX == fieldType ) {
			     event.setProperty("__" + fieldName + "_Inbox", createXMLByMessage("Inbox", msg, fieldName));
		   }
	}
	

	public static void parseMessage(Element rootEle, Document document, String type, String childName, Message message, String fieldName) throws FTLException {
		if(fieldName.contains(FTLConstants.ESCAPE_HYPEN)) {
			fieldName = fieldName.replace(FTLConstants.ESCAPE_HYPEN, "-");
		}
		if("LongArray".equals(type)) {
			 long[] longs = message.getLongArray(fieldName);
			 rootEle.setAttribute("type", "LongArray");
			 rootEle.setAttribute("size", longs.length + "");
			 for (long l : longs) {
				 Element childEle = document.createElement(childName);
				 childEle.setAttribute("type", "Long");
				 childEle.setAttribute("value", l + "");
				 rootEle.appendChild(childEle);
			 }	 
		} else if ("DoubleArray".equals(type)) {
			double[] doubles = message.getDoubleArray(fieldName);
			rootEle.setAttribute("type", "DoubleArray");
			rootEle.setAttribute("size", doubles.length + "");
			for (double d : doubles) {
				Element childEle = document.createElement(childName);
				childEle.setAttribute("type", "Double");
				childEle.setAttribute("value", d + "");
				rootEle.appendChild(childEle);
			}
		} else if ("StringArray".equals(type)) {
			String[] strings = message.getStringArray(fieldName);
			rootEle.setAttribute("type", "StringArray");
			rootEle.setAttribute("size", strings.length + "");
			for (String str : strings) {
				Element childEle = document.createElement(childName);
				childEle.setAttribute("type", "String");
				childEle.setAttribute("value", str);
				rootEle.appendChild(childEle);
			}
		} else if ("DateTimeArray".equals(type)) {
			TibDateTime[] dateTimes = message.getDateTimeArray(fieldName);
			rootEle.setAttribute("type", "DateTimeArray");
			rootEle.setAttribute("size", dateTimes.length + "");
			for (TibDateTime tibDateTime : dateTimes) {
				Element childEle = document.createElement(childName);
				childEle.setAttribute("type", "DateTime");
				
				long  time = tibDateTime.toDate().getTime();
				
				childEle.setAttribute("value", time + "");
				rootEle.appendChild(childEle);
			}
		} else if ("Message".equals(type)) {
			Message subMsg = message.getMessage(fieldName);
			
            int length = 2048;
            byte[] bytes = new byte[length];
            length = subMsg.writeToByteArray(bytes);
            if(length > bytes.length)
            {
                bytes = new byte[length];
                length = subMsg.writeToByteArray(bytes);
            }
            
            //BASE64Encoder encoder = new BASE64Encoder();
            Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
            
            rootEle.setAttribute("type", "Message");
            rootEle.setAttribute("value",  encoder.encodeToString(bytes));		
			
		} else if ("MessageArray".equals(type)) {
			Message[] messages = message.getMessageArray(fieldName);
			rootEle.setAttribute("type", "MessageArray");
			rootEle.setAttribute("size", messages.length + "");
			for (Message msg : messages) {
				Element childEle = document.createElement(childName);
				
	            int length = 2048;
	            byte[] bytes = new byte[length];
	            length = msg.writeToByteArray(bytes);
	            if(length > bytes.length)
	            {
	                bytes = new byte[length];
	                length = msg.writeToByteArray(bytes);
	            }
	            
	            //BASE64Encoder encoder = new BASE64Encoder();
	            Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
				
				childEle.setAttribute("type", "Message");
				childEle.setAttribute("value", encoder.encodeToString(bytes));

	        	rootEle.appendChild(childEle);
			}
	
		} else if ("Opaque".equals(type)) {
			//BASE64Encoder encoder = new BASE64Encoder();
			Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
			String opaqueStr = encoder.encodeToString(message.getOpaque(fieldName));
			
			rootEle.setAttribute("type", "Opaque");
			rootEle.setAttribute("value", opaqueStr);
		} else if ("Inbox".equals(type)) {
			Inbox inbox = message.getInbox(fieldName);
			NativeInbox nativeInbox = (NativeInbox)inbox;
			rootEle.setAttribute("type", "Inbox");
			rootEle.setAttribute("value", nativeInbox.getPeer() + "");
		}
	}
	
	public static String createXMLByMessage(String type, Message message, String fieldName) throws FTLException {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();

			Element rootEle = null;
			if(!"Message".equals(type) && !"Opaque".equals(type) && !"Inbox".equals(type)) {
			     rootEle = document.createElement("array");
			     document.appendChild(rootEle);
		    } else {
		    	 rootEle = document.createElement("element");
			     document.appendChild(rootEle);
		    }

			parseMessage(rootEle, document, type, "element", message, fieldName); 

			TransformerFactory tf = TransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl",null);

			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			StreamResult result = new StreamResult(writer);
			transformer.setOutputProperty(OutputKeys.ENCODING, "iso8859-1");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			
			return outputStream.toString();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Loads the overridden FTL client properties (if any).
	 * @param beProperties
	 * @param destinationProps
	 * @param channelUri
	 * @param destUri
	 * @param logger 
	 * @throws FTLException 
	 */
	public static void loadOverridenProperties(Properties beProperties, TibProperties destinationProps, FtlApiType fat, String channelUri, String destUri, Logger logger) {
		if (beProperties == null) {
			return;
		}
		
		try{
			//Read properties one level of override at a time, so that proper priorities are maintained.
			String prefix = FTLConstants.PROPERTY_KEY_FTL_CLIENT_PROPERTY_PREFIX + ".";
			setFtlProperties(beProperties, destinationProps, prefix, fat);
			if (channelUri != null) {
				prefix = FTLConstants.PROPERTY_KEY_FTL_CLIENT_PROPERTY_PREFIX + channelUri + ".";
				setFtlProperties(beProperties, destinationProps, prefix, fat);
			}
			if (destUri != null) {
				prefix = FTLConstants.PROPERTY_KEY_FTL_CLIENT_PROPERTY_PREFIX + destUri + ".";
				setFtlProperties(beProperties, destinationProps, prefix, fat);
			}
			
			
		}catch (Exception e) {
			logger.log(Level.WARN, "Could not set Ftl property : " + e.getMessage());
		}
	}
	
	private static void setFtlProperties(Properties beProperties, TibProperties destinationProps, String prefix, FtlApiType fat) throws FTLException{
		for (Entry<Object, Object> prop : beProperties.entrySet()) {
			if (((String)prop.getKey()).startsWith(prefix)) {
				setFtlProperties(prop, destinationProps, fat, prefix);
			}
		}
	}
	
	private static void setFtlProperties(Entry<Object, Object> beProp, TibProperties destinationProps, FtlApiType fat, String prefix) throws FTLException {
		
		String key = ((String)beProp.getKey()).substring(prefix.length());
		
		switch(fat){
			case REALM : 
				if(ftlProperties.getRealmPropsMap().containsKey(key)){
					setTibProp(destinationProps, key, beProp.getValue(), ftlProperties.getRealmPropsMap().get(key), prefix);
				}
				break;
			case EVENT_QUEUE :
				if(ftlProperties.getEventQueuePropsMap().containsKey(key)){
					setTibProp(destinationProps, key, beProp.getValue(), ftlProperties.getEventQueuePropsMap().get(key), prefix);
				}
				break;
			case PUBLISHER :
				if(ftlProperties.getPublisherPropsMap().containsKey(key)){
					setTibProp(destinationProps, key, beProp.getValue(), ftlProperties.getPublisherPropsMap().get(key), prefix);
				}
				break;
			case SUBSCRIBER :
				if(ftlProperties.getSubscriberPropsMap().containsKey(key)){
					setTibProp(destinationProps, key, beProp.getValue(), ftlProperties.getSubscriberPropsMap().get(key), prefix);
				}
				break;
		}
	}

	private static void setTibProp(TibProperties destinationProps, String key, Object value, String propType, String prefix) throws FTLException {
		
		switch(propType){
			case "java.lang.String" :	destinationProps.set(key, (String)value); break;
			case "java.lang.Boolean" : destinationProps.set(key.substring(prefix.length()), (Boolean)value); break;
			case "java.lang.Integer" : destinationProps.set(key.substring(prefix.length()), (Integer)value); break;
			case "java.lang.Long" : destinationProps.set(key.substring(prefix.length()), (Long)value); break;
			case "java.lang.Double" : destinationProps.set(key.substring(prefix.length()), (Double)value); break;
		}
	}

	public enum FtlApiType{
		REALM, EVENT_QUEUE, PUBLISHER, SUBSCRIBER;
	}
	
	
	
	
	
}
