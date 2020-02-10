package com.tibco.cep.driver.hawk.internal.listeners;

import java.util.EventListener;
import java.util.LinkedList;
import java.util.Queue;

import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMessageContext;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.session.RuleSession;

public class HawkListener implements EventListener {

	protected Logger logger;
	protected HawkDestination dest;
	protected RuleSession session;
	public int mode;
	public Queue<Object> eventQueue; 

	public HawkListener(HawkDestination dest, RuleSession session, Logger logger) {
		this.logger = logger;
		this.dest = dest;
		this.session = session;
		this.eventQueue = new LinkedList<Object>();
	}

	public void onMessage(Object hawkEvent, HawkDestination dest, RuleSession session, Logger logger)
			throws Exception {

		if(mode == ChannelManager.SUSPEND_MODE){
			eventQueue.offer(hawkEvent);
		}else{
			// construct context
			EventContext ctx = new HawkMessageContext(dest, hawkEvent);

			try {
				session.getTaskController().processEvent(dest, null, ctx);
				// Count only if this server received the message.
				dest.getStats().addEventIn();
			} catch (Throwable e) {
				logger.log(Level.ERROR, e, "Handle message failed.");
			}
		}
		
	}

}
