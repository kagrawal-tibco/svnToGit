package com.tibco.cep.functions.channel.ftl;

import com.tibco.ftl.*;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;


public class PublisherHelperDelegate {
	
	public static void bind(Object publisher, String eventUri, String messageFormat){
		try{
			FTLPublisher pub = FTLPublisher.class.cast(publisher);
			pub.bind(eventUri, messageFormat);
		} catch(Exception exp){
			throw new RuntimeException(exp);
		}
	}
	 
	 public static void publishMessage (Object publisher, Object event) {
		 try {
			 	FTLPublisher pub = FTLPublisher.class.cast(publisher);
			 	SimpleEvent ev = SimpleEvent.class.cast(event);
			 	pub.publishMessage(ev);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
