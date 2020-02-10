package com.tibco.cep.driver.ftl.internal;

import com.tibco.cep.driver.ftl.FTLDestination;
import com.tibco.cep.driver.ftl.util.FTLUtils;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Inbox;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.MessageIterator;
import com.tibco.ftl.Publisher;

public class FTLMessageContext extends AbstractEventContext {
	 private Destination dest;
	 private Message msg;
	 
	 public FTLMessageContext(Destination dest,Message msg){
		 this.dest = dest;
		 this.msg = msg;
	 }
	
	@Override
	public Destination getDestination() {
		return dest;
	}

	@Override
	public Object getMessage() {
		return msg;
	}

	@Override
	public boolean reply(SimpleEvent replyEvent) {
		try {
//			FTLDestination ftlDestination = (FTLDestination) dest;
			Inbox inbox = null;
			MessageIterator messageIterator = msg.iterator();
			while (messageIterator.hasNext()) {
				MessageFieldRef messageFieldRef = (MessageFieldRef) messageIterator.next();
				int fieldType = msg.getFieldType(messageFieldRef);
				if (fieldType == Message.TIB_FIELD_TYPE_INBOX) {
					inbox = msg.getInbox(messageFieldRef);
					break;
				}
			}
			
			
			Publisher publisher = ((FTLDestination)getDestination()).getPub();	
			Message newMsg = FTLUtils.fillMessageWithEvent((FTLDestination)getDestination(), replyEvent);
			
			publisher.sendToInbox(inbox, newMsg);
			return true;
		} catch (FTLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public String replyImmediate(SimpleEvent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
