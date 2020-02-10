package com.tibco.cep.driver.as.serializers;

import com.tibco.as.space.Tuple;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.utils.ASUtils;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;

public class ActiveSpacesSerializer implements EventSerializer {

	protected Logger logger;

	@Override
	public SimpleEvent deserialize(Object message, SerializationContext context)
			throws Exception {
        IASDestination dest = (IASDestination) context.getDestination();
        RuleSession session = context.getRuleSession();
        DestinationConfig config = dest.getConfig();
        ExpandedName en = config.getDefaultEventURI();
        SimpleEvent event = (SimpleEvent)session.getRuleServiceProvider().getTypeManager().createEntity(en);
        Tuple tuple = (Tuple) message;
        ASUtils.fillEventWithTuple(dest.getSpaceDef(), event, tuple, session);
		return event;
	}

	@Override
	public void init(ChannelManager channelManager, DestinationConfig config) {
		this.logger = channelManager.getRuleServiceProvider().getLogger(this.getClass());
	}

	@Override
	public Object serialize(SimpleEvent event, SerializationContext context)
			throws Exception {
    	{
    		EventContext evCtx = event.getContext();
	        if (evCtx != null && !evCtx.isEventModified()) {
	        	Object message = evCtx.getMessage();
	        	if(message != null) return message;
	        }
    	}
		IASDestination dest = (IASDestination) context.getDestination();
		Tuple tuple = Tuple.create();
        ASUtils.fillTupleWithEvent(dest.getSpaceDef(), tuple, event, context.getRuleSession());
		return tuple;
	}

}
