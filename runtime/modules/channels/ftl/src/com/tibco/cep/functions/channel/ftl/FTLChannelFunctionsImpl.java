package com.tibco.cep.functions.channel.ftl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.driver.ftl.FTLDestination;
import com.tibco.cep.driver.ftl.util.FTLUtils;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Inbox;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.MessageIterator;
import com.tibco.ftl.Publisher;
import com.tibco.ftl.Realm;
import com.tibco.ftl.TibDateTime;
import com.tibco.ftl.jni.NativeInbox;

//import sun.misc.BASE64Decoder;

public class FTLChannelFunctionsImpl {

	private static FTLDestination getFTLDestination(String destinationUri) {
		ChannelManager cmgr = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();
		Channel.Destination destination = cmgr.getDestination(destinationUri);
		if (destination == null || !(destination instanceof FTLDestination)) {
			throw new RuntimeException("Invalid Destination[" + destinationUri + "]");
		}
		return (FTLDestination) destination;
	}

	public static void sendMessage(String destinationURI, SimpleEvent message) {
		try {
			FTLDestination dest = getFTLDestination(destinationURI);
			Publisher pub = dest.getPub();

			Message msg = FTLUtils.fillMessageWithEvent(dest, message);
			pub.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sendInboxMessage(String destinationURI, String recvDestinationURI, SimpleEvent message) {
		try {
			FTLDestination dest = getFTLDestination(destinationURI);
			Publisher pub = dest.getPub();
			FTLDestination recvDest = getFTLDestination(recvDestinationURI);
			Message msg = FTLUtils.fillMessageWithEvent(recvDest, message);
			pub.sendToInbox(recvDest.getInboxSub().getInbox(),msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object getFtlPropertyValue(String propertyName, SimpleEvent event) {
		
		Message ftlMessage = null;
		if(event!=null){
			ftlMessage = (Message) event.getContext().getMessage();
		}else{
			return null;
		}
		
		if(propertyName==null){
			Map<String, Object> props = new HashMap<String, Object>();
			for (MessageIterator iterator = ftlMessage.iterator(); iterator.hasNext();) {
				   try {
						MessageFieldRef fieldRef = iterator.next();
						int fieldType = ftlMessage.getFieldType(fieldRef);
						String fieldName = fieldRef.getFieldName();
						Object val = getIndividualPropVal(fieldType, ftlMessage, fieldName);
						props.put(fieldName, val);
				   }catch(Exception e){
					   e.printStackTrace();
				   }
				   
			}
			return props;
		}else{
			try {
				int messageType = ftlMessage.getFieldType(propertyName);
				return getIndividualPropVal(messageType, ftlMessage, propertyName);
			} catch (FTLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private static Object getIndividualPropVal(int messageType, Message ftlMessage, String propertyName) throws FTLException{
		
		switch(messageType){
		
			case Message.TIB_FIELD_TYPE_LONG_ARRAY :
					long[] longArray = ftlMessage.getLongArray(propertyName);
					List<Long> arrayLong = new ArrayList<Long>();
					for(long l : longArray){
						arrayLong.add(l);
					}
					return arrayLong;
			case Message.TIB_FIELD_TYPE_DOUBLE_ARRAY :
					double[] doubleArray = ftlMessage.getDoubleArray(propertyName);
					List<Double> arrayDouble = new ArrayList<Double>();
					for(double d : doubleArray){
						arrayDouble.add(d);
					}
					return arrayDouble;
			case Message.TIB_FIELD_TYPE_STRING_ARRAY :
					String[] stringArray = ftlMessage.getStringArray(propertyName);
					List<String> arrayString = new ArrayList<String>();
					for(String s : stringArray){
						arrayString.add(s);
					}
					return arrayString;
			case Message.TIB_FIELD_TYPE_MESSAGE_ARRAY :
					Message[] messageArray = ftlMessage.getMessageArray(propertyName);
					List<Message> arrayMessage = new ArrayList<Message>();
					for(Message m : messageArray){
						arrayMessage.add(m);
					}
					return arrayMessage;
			case Message.TIB_FIELD_TYPE_DATETIME_ARRAY :
					TibDateTime[] dateTimeArray = ftlMessage.getDateTimeArray(propertyName);
					List<Calendar> arrayDateTime = new ArrayList<Calendar>();
					for(TibDateTime dt : dateTimeArray){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dt.toDate());
						arrayDateTime.add(calendar);
					}
					return arrayDateTime;
			case Message.TIB_FIELD_TYPE_LONG :
					long l = ftlMessage.getLong(propertyName);
					return l;
			case Message.TIB_FIELD_TYPE_DOUBLE :
					double d = ftlMessage.getDouble(propertyName);
					return d;
			case Message.TIB_FIELD_TYPE_STRING :
					String s = ftlMessage.getString(propertyName);
					return s;
			case Message.TIB_FIELD_TYPE_MESSAGE :
					Message m = ftlMessage.getMessage(propertyName);
					return m;
			case Message.TIB_FIELD_TYPE_DATETIME :
					TibDateTime dt = ftlMessage.getDateTime(propertyName);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dt.toDate());
					return calendar;
			case Message.TIB_FIELD_TYPE_OPAQUE :
					byte[] ba = ftlMessage.getOpaque(propertyName);
					return ba;
			case Message.TIB_FIELD_TYPE_INBOX :
					Inbox in = ftlMessage.getInbox(propertyName);
					return in;
			
		}
		return null;
	}
	
	public static Object getArrayFromXml(String xml, SimpleEvent event) {
		FTLDestination dest = getFTLDestination(event.getDestinationURI());
		if(xml == null || !xml.contains("</array>")) {
			return null;
		}
		String arrayTypeXpathExp = "/array/@type";
		String arraySizeXpathExp = "count(/array/element)";
		Object arrayTypeObj = FTLUtils.getValFromXML(xml, arrayTypeXpathExp, false);
		if(arrayTypeObj == null) {
			return null;
		}
		String arrayType = (String)FTLUtils.getValue(arrayTypeObj);
		int arraySize = ((Double)FTLUtils.getValFromXML(xml, arraySizeXpathExp, true)).intValue();
		if("LongArray".equals(arrayType)) {
			List<Long> arrayLong = new ArrayList<Long>();
			 for (int i = 0; i < arraySize; i++) {
				  String longXpathExp = "/array/element[" + (i + 1) + "]/@value";
				  String longStr = (String)FTLUtils.getValue(FTLUtils.getValFromXML(xml, longXpathExp, false));
				  long l =  Long.parseLong(longStr);
				  arrayLong.add(l);
			  }
			 return arrayLong;
		} else if ("StringArray".equals(arrayType)) {
			List<String> arrayStr = new ArrayList<String>();
			 for (int i = 0; i < arraySize; i++) {
				  String strXpathExp = "/array/element[" + (i + 1) + "]/@value";
				  String strStr = (String)FTLUtils.getValue(FTLUtils.getValFromXML(xml, strXpathExp, false));
				  arrayStr.add(strStr);
			  }
			 return arrayStr;
		} else if ("DoubleArray".equals(arrayType)) {
			List<Double> arrayDouble = new ArrayList<Double>();
			 for (int i = 0; i < arraySize; i++) {
				  String doubleXpathExp = "/array/element[" + (i + 1) + "]/@value";
				  String doubleStr = (String)FTLUtils.getValue(FTLUtils.getValFromXML(xml, doubleXpathExp, false));
				  arrayDouble.add(Double.parseDouble(doubleStr));
			  }
			 return arrayDouble;
		} else if ("MessageArray".equals(arrayType)) {
			List<Message> arrayMsg = new ArrayList<Message>();
			 for (int i = 0; i < arraySize; i++) {
				  String msgXpathExp = "/array/element[" + (i + 1) + "]/@value";
				  String msgBase64Str = (String)FTLUtils.getValue(FTLUtils.getValFromXML(xml, msgXpathExp, false));
				  //BASE64Decoder decoder = new BASE64Decoder();
				  Base64.Decoder decoder = Base64.getMimeDecoder();
				  byte[] bytes;
				try {
					 //bytes = decoder.decodeBuffer(msgBase64Str);
					 bytes = decoder.decode(msgBase64Str);
					 Realm realm = dest.getRealm();
			         Message subMsg = realm.createMessage(bytes, bytes.length);	 
			         arrayMsg.add(subMsg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		    
			  }
			 return arrayMsg;
		} else if ("DateTimeArray".equals(arrayType)) {
			List<TibDateTime> arrayDateTime = new ArrayList<TibDateTime>();
			 for (int i = 0; i < arraySize; i++) {
				  String dateTimeXpathExp = "/array/element[" + (i + 1) + "]/@value";
				  String dateTimeStr = (String)FTLUtils.getValue(FTLUtils.getValFromXML(xml, dateTimeXpathExp, false));
				  long time = Long.parseLong(dateTimeStr);
				  TibDateTime dataTimes = new TibDateTime();
				  dataTimes.setFromMillis(time);
				  arrayDateTime.add(dataTimes);
			  }
			 return arrayDateTime;
		} else {
			 return null;
		}
	}
	
	
	public static Object getMessageFromXml(String xml, SimpleEvent event) {
		if(xml == null) {
			return null;
		}
		FTLDestination dest = getFTLDestination(event.getDestinationURI());
		Realm realm = dest.getRealm();
		String elementTypeXpathExp = "/element/@type";
		Object eleTypeObj = FTLUtils.getValFromXML(xml, elementTypeXpathExp, false);
		if (eleTypeObj == null) {
			return null;
		}
		String elementType = (String) FTLUtils.getValue(eleTypeObj);
		if ("Message".equals(elementType)) {
			String msgXpathExp = "/element/@value";
			Object msgObj = FTLUtils.getValFromXML(xml, msgXpathExp, false);
			if (msgObj == null) {
				return null;
			}
			String msgVal = (String) FTLUtils.getValue(msgObj);
			//BASE64Decoder decoder = new BASE64Decoder();
			Base64.Decoder decoder = Base64.getMimeDecoder();
			
			try {
				//byte[] bytes = decoder.decodeBuffer(msgVal);
				byte[] bytes = decoder.decode(msgVal);
				Message msg = realm.createMessage(bytes, bytes.length);
				return msg;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		} else {
			return null;
		}
	}
	
	public static Object getInboxFromXml(String xml) {
		 if(xml == null || "".equals(xml)) {
			 return null;
		 }
		 Object eleTypeObj = FTLUtils.getValFromXML(xml, "/element/@type", false);
		 if (eleTypeObj == null) {
				return null;
		 }
		 String elementType = (String) FTLUtils.getValue(eleTypeObj);
		 if("Inbox".equals(elementType)) {
			  Object inboxObject = FTLUtils.getValFromXML(xml, "/element/@value", false);
			  if(inboxObject == null) {
				  return null;
			  }
			  String inboxStr = (String)FTLUtils.getValue(inboxObject);
			  long peer = Long.parseLong(inboxStr);
			  
			  NativeInbox nativeInbox = new NativeInbox(peer);
			  try {
				  return nativeInbox.copy();
			  } catch (FTLException e) {
				  e.printStackTrace();
				  return null;
			  }
		 } else {
			 return null;
		 }
		 
	}

	
	
}
