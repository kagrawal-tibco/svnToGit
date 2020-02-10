package com.tibco.cep.webstudio.client.process.properties;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to store the reply event property
 * 
 * @author dijadhav
 * 
 */
public class ReplyEventProperty extends Property {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6590254469716596617L;

	private List<ReplyEvent> replyEvents = new LinkedList<ReplyEvent>();

	/**
	 * @return the replyEvents
	 */
	public List<ReplyEvent> getReplyEvents() {
		return replyEvents;
	}

	public void add(ReplyEvent replyEvent) {
		if (null != replyEvent) {
			replyEvents.add(replyEvent);
		}
	}
}
