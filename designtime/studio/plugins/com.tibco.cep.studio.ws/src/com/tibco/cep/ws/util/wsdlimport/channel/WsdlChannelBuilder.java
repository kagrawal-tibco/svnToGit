package com.tibco.cep.ws.util.wsdlimport.channel;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.ws.util.wsdlimport.context.BindingContext;
import com.tibco.cep.ws.util.wsdlimport.context.OperationContext;


public interface WsdlChannelBuilder {
	
	
	
	Map<String, Channel> getChannels();

	
    Set<Entity> getCreatedEntities();

    
    Set<Entity> getModifiedEntities();

    
	ResourceSet getSharedResources();

	
	Channel processBinding(
			BindingContext bindingContext)
			throws Exception;	
	
	Destination[] processOperation(
			OperationContext opContext,
			String soapAction,
			SimpleEvent inEvent,
			SimpleEvent outEvent,
			String ruleFnURI)	
			throws Exception;	
	
}
