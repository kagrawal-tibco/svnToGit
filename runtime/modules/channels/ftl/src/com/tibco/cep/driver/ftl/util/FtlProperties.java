package com.tibco.cep.driver.ftl.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssinghal
 *
 */

public class FtlProperties{
	
	private Map<String, String> realmPropsMap = new HashMap<String, String>();
	private Map<String, String> eventQueuePropsMap = new HashMap<String, String>();
	private Map<String, String> publisherPropsMap = new HashMap<String, String>();
	private Map<String, String> subscriberPropsMap = new HashMap<String, String>();
	
	
	public FtlProperties() {
		
		for (FTLRealmPropertiesEnum enumVal : FTLRealmPropertiesEnum.values()) {
			realmPropsMap.put(enumVal.propName, enumVal.propType);
		}
		
		for (FTLEventQueuePropertiesEnum enumVal : FTLEventQueuePropertiesEnum.values()) {
			eventQueuePropsMap.put(enumVal.propName, enumVal.propType);
		}
		
		for (FTLPublisherPropertiesEnum enumVal : FTLPublisherPropertiesEnum.values()) {
			publisherPropsMap.put(enumVal.propName, enumVal.propType);
		}
		
		for (FTLSubscriberPropertiesEnum enumVal : FTLSubscriberPropertiesEnum.values()) {
			subscriberPropsMap.put(enumVal.propName, enumVal.propType);
		}
	}
	
	

	protected Map<String, String> getRealmPropsMap() {
		return realmPropsMap;
	}

	protected Map<String, String> getEventQueuePropsMap() {
		return eventQueuePropsMap;
	}

	protected Map<String, String> getPublisherPropsMap() {
		return publisherPropsMap;
	}

	protected Map<String, String> getSubscriberPropsMap() {
		return subscriberPropsMap;
	}

	protected enum FTLRealmPropertiesEnum {
		
		//realm 
		REALM_USERNAME("com.tibco.ftl.client.username", String.class.getName()),
		REALM_USER_PASSWORD("com.tibco.ftl.client.userpassword", String.class.getName()),
		REALM_SECONDARY_SERVER("com.tibco.ftl.client.secondary", String.class.getName()),
		REALM_APPINSTANCE_IDENTIFIER("com.tibco.ftl.client.appinstance.identifier", String.class.getName()),
		REALM_CLIENT_LABEL("com.tibco.ftl.client.label", String.class.getName());
		
		String propName;
		String propType;
		
		private FTLRealmPropertiesEnum(String propName, String porpType){
			this.propName = propName;
			this.propType = porpType;
		}
	}
		
	protected enum FTLEventQueuePropertiesEnum {
		
		//eventqueue
		EVENT_QUEUE_BOOL_INLINE_MODE("com.tibco.ftl.client.inline", Boolean.class.getName()),
		EVENT_QUEUE_INT_DISCARD_POLICY("com.tibco.ftl.client.discard.policy", Integer.class.getName()),
		EVENT_QUEUE_INT_DISCARD_POLICY_MAX_EVENTS("com.tibco.ftl.client.discard.max_events", Integer.class.getName()),
		EVENT_QUEUE_INT_DISCARD_POLICY_DISCARD_AMOUNT("com.tibco.ftl.client.discard.amount", Integer.class.getName()),
		EVENT_QUEUE_QUEUE_NAME("com.tibco.ftl.client.queue.name", String.class.getName());
		
		String propName;
		String propType;
		private FTLEventQueuePropertiesEnum(String propName, String porpType){
			this.propName = propName;
			this.propType = porpType;
		}
	}
		
		
	protected enum FTLPublisherPropertiesEnum {	
			
		
		//publisher
		PUBLISHER_RELEASE_MSGS_TO_SEND("com.tibco.ftl.client.publisher.release", Boolean.class.getName());
		
		String propName;
		String propType;
		private FTLPublisherPropertiesEnum(String propName, String porpType){
			this.propName = propName;
			this.propType = porpType;
		}
	}
		
	
	protected enum FTLSubscriberPropertiesEnum {
		
		//subscriber
		SUBSCRIBER_SUBSCRIBER_NAME("com.tibco.ftl.client.subscriber.name", String.class.getName()),
		SUBSCRIBER_DURABLE_NAME("com.tibco.ftl.client.durable.name", String.class.getName()),
		SUBSCRIBER_EXPLICIT_ACK("com.tibco.ftl.client.subscriber.explicitack", Boolean.class.getName()),
		SUBSCRIBER_RELEASE_MSGS_TO_CALLBACK("com.tibco.ftl.client.subscriber.release", Boolean.class.getName()),
		SUBSCRIBER_KEY_FIELD_NAME("com.tibco.ftl.client.subscriber.keyfieldname", String.class.getName());
		
		String propName;
		String propType;
		private FTLSubscriberPropertiesEnum(String propName, String porpType){
			this.propName = propName;
			this.propType = porpType;
		}
	}	
}
