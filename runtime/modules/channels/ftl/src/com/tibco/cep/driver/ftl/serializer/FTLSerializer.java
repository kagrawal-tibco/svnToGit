package com.tibco.cep.driver.ftl.serializer;

import java.util.Calendar;

import com.tibco.cep.driver.ftl.FTLConstants;
import com.tibco.cep.driver.ftl.FTLDestination;
import com.tibco.cep.driver.ftl.util.FTLUtils;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.MessageIterator;
import com.tibco.ftl.TibDateTime;
import com.tibco.xml.data.primitive.ExpandedName;
import static com.tibco.cep.driver.ftl.FTLConstants.BE_NAME;
import static com.tibco.cep.driver.ftl.FTLConstants.BE_NAMESPACE;

public class FTLSerializer implements EventSerializer {

	protected Logger logger;

	@Override
	public void init(ChannelManager channelManager, DestinationConfig paramDestinationConfig) {
		this.logger = channelManager.getRuleServiceProvider().getLogger(this.getClass());
	}

	@Override
	public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception {
		FTLDestination dest = (FTLDestination) context.getDestination();
		RuleSession session = context.getRuleSession();
		DestinationConfig config = dest.getConfig();
		ExpandedName en = config.getDefaultEventURI();
		Message msg = (Message) message;
		
		String namespace = null;
		String name = null;
		for (MessageIterator iterator = msg.iterator(); iterator.hasNext();) {
			   try {
					MessageFieldRef fieldRef = iterator.next();
					int fieldType = msg.getFieldType(fieldRef);
					String fieldName = fieldRef.getFieldName();
					if (Message.TIB_FIELD_TYPE_STRING == fieldType) {
						if(fieldName.equals(BE_NAME)){
							name = msg.getString(fieldRef);
						}else if(fieldName.equals(BE_NAMESPACE)){
							namespace = msg.getString(fieldRef);
						}
					}
			   }catch(Exception e){
				   
			   }
		}
		if ((null != namespace) && (null != name)) {
            en = ExpandedName.makeName(namespace, name);
        }
		SimpleEvent event = (SimpleEvent) session.getRuleServiceProvider().getTypeManager().createEntity(en);
	    
	    
//		String formatsType = dest.getFormatsType();
//		if ("built-in(opaque)".equals(formatsType)) { // formats type is Built-In
//			try {
//				  byte[] opaque = msg.getOpaque(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME);
//				  BASE64Encoder encoder = new BASE64Encoder();
//				  event.setProperty(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME, encoder.encode(opaque));
//				  return event;
//			} catch (FTLNotFoundException e) {
//
//			}
//		} else if ("built-in(keyedOpaque)".equals(formatsType)) {
//			try {
//				String _key = msg.getString(Message.TIB_BUILTIN_MSG_FMT_KEY_FIELDNAME);
//				byte[] _data = msg.getOpaque(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME);
//				event.setProperty(Message.TIB_BUILTIN_MSG_FMT_KEY_FIELDNAME, _key);
//				event.setProperty(Message.TIB_BUILTIN_MSG_FMT_OPAQUE_FIELDNAME, _data);
//				return event;
//			} catch (FTLNotFoundException e) {
//
//			}
//		} else if ("dynamic".equals(formatsType)) { // formats type is Dynamic
//			FTLMsgPayload msgPayload = new FTLMsgPayload(msg);
//			event.setPayload(msgPayload);
//			return event;
//		}
		
		for (MessageIterator iterator = msg.iterator(); iterator.hasNext();) {
		   try {
				MessageFieldRef fieldRef = iterator.next();
				int fieldType = msg.getFieldType(fieldRef);
				String fieldName = fieldRef.getFieldName();
				if (fieldName.contains("-")) {
					fieldName = fieldName.replace("-", FTLConstants.ESCAPE_HYPEN);
				}
				if (Message.TIB_FIELD_TYPE_STRING == fieldType) {
					event.setProperty(fieldName, msg.getString(fieldRef));
				} else if (Message.TIB_FIELD_TYPE_LONG == fieldType) {
					event.setProperty(fieldName, msg.getLong(fieldRef));
				} else if (Message.TIB_FIELD_TYPE_DOUBLE == fieldType) {
					event.setProperty(fieldName, msg.getDouble(fieldName));
				} else if (Message.TIB_FIELD_TYPE_DATETIME == fieldType) {
					TibDateTime tibDateTime = msg.getDateTime(fieldRef);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(tibDateTime.toDate());
					event.setProperty(fieldName, calendar);
				} else {
					FTLUtils.deserializeToXML(event, msg, fieldName, fieldType);
				}
			} catch (NoSuchFieldException e) {

			}
		}
		return event;
	}

	@Override
	public Object serialize(SimpleEvent paramSimpleEvent, SerializationContext paramSerializationContext)
			throws Exception {	
		// TODO Auto-generated method stub
		if(paramSimpleEvent.getContext() != null) {
			return paramSimpleEvent.getContext().getMessage();
		}
		Message sendMsg = FTLUtils.fillMessageWithEvent(paramSerializationContext, paramSimpleEvent);
		return sendMsg;
	}

}
