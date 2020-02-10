package com.tibco.cep.dashboard.integration.be;

import java.io.IOException;
import java.net.SocketException;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.streaming.Streamer;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.tcp.catalog.TCPHelper;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class BusinessEventsStreamer extends Streamer {
	
	public static final char DEFAULT_EOL = '\0';
	
	private RuleServiceProvider ruleServiceProvider;
	private ChannelManager channelManager;
	private TypeManager typeManager;
	
	private String sessionName;
	private String eventURI;
	private String destinationURI;
	
	private Session session;
	
	private String toString;

	BusinessEventsStreamer(String sessionName, String eventURI){
		if (StringUtil.isEmptyOrBlank(sessionName) == true) {
			throw new IllegalArgumentException("session name cannot be null");
		}
		if (StringUtil.isEmptyOrBlank(eventURI) == true) {
			throw new IllegalArgumentException("event URI cannot be null");
		}		
		ruleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
		if (ruleServiceProvider == null){
			throw new IllegalStateException("Should be invoked within the context of a BE Session");
		}
		typeManager = this.ruleServiceProvider.getTypeManager();
		try {
			SimpleEvent event = (SimpleEvent) typeManager.createEntity(eventURI);
			this.sessionName = sessionName;
			this.eventURI = eventURI;
			this.destinationURI = event.getDestinationURI();
			if (StringUtil.isEmptyOrBlank(destinationURI) == false){
				channelManager = this.ruleServiceProvider.getChannelManager();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid eventURI[" + eventURI + "] for " + sessionName, e);
		}
		StringBuilder sb = new StringBuilder("BusinessEventsStreamer[");
		sb.append("session="+sessionName);
		sb.append(",eventURI="+eventURI);
		sb.append("]");
		toString = sb.toString();
	}
	
	BusinessEventsStreamer(Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		this.session = session;
		this.sessionName = this.session.getId();
		StringBuilder sb = new StringBuilder("BusinessEventsStreamer[");
		sb.append("session="+sessionName);
		sb.append("]");
		toString = sb.toString();		
	}

	@Override
	public void init() throws IOException {
		//do nothing
	}		

	@Override
	protected void doStream(String data) throws IOException {
		if (StringUtil.isEmptyOrBlank(data) == true) {
			return;
		}
		if (data.charAt(data.length()-1) != DEFAULT_EOL){
			data = data + DEFAULT_EOL;
		}
		if (session != null){
			byte[] bytes = data.getBytes();
			try {
				session.getWriter().write(bytes, 0, bytes.length);
				session.getWriter().flush();
			} catch (Exception e) {
				throw new IOException("could not send an data over "+session.getId(),e);
			}
		}
		else {
			try {
				SimpleEvent event = (SimpleEvent) typeManager.createEntity(eventURI);
				event.setPayload(new ObjectPayload(data));
				if (channelManager != null) {
					try {
						channelManager.sendEvent(event, destinationURI, null);
					} catch (Exception e) {
						throw new IOException("could not send an instance of "+eventURI+" on "+destinationURI,e);
					}
				}
				else {
					try {
						TCPHelper.write(sessionName, event);
					} catch (RuntimeException e) {
						if (e.getCause() instanceof SocketException){
							throw (SocketException)e.getCause();
						}
					}
				}				
			} catch (Exception e) {
				throw new IOException("could not create an instance of "+eventURI,e);
			}			
		}
	}

	@Override
	protected void doClose() {
		if (session != null){
			try {
				session.getWriter().stop();
			} catch (Exception ignoreex) {
				//INFO ignoring session.getWriter().stop() exception
			}
		}
		else {
			TCPHelper.endWrite(sessionName);
		}
		typeManager = null;
		channelManager = null;
		ruleServiceProvider = null;		
	}

	@Override
	public String toString() {
		return toString;
	}
}
