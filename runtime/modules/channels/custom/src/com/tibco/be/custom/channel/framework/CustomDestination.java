package com.tibco.be.custom.channel.framework;

import java.util.Map;

import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventContext;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author vasharma
 */
public class CustomDestination extends AbstractDestination<CustomChannel> {

	BaseDestination destination = null;
	final Logger logger;
	
	public CustomDestination(CustomChannel channel, DestinationConfig config, BaseDestination userDestination) {
		super(channel, config);
		this.destination = userDestination;
//		this.destination.setEventProcessor(this);
		this.logger = channel.getLogger();
	}

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass,
			long timeout, Map overrideData) throws Exception {
		//BE-26500: Messages should not be send if channel is stopped
		if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
			Object eventObject = this.getEventSerializer().serialize(outevent,
					new DefaultSerializationContext(RuleSessionManager.getCurrentRuleSession(), this));
			if (!(eventObject instanceof Event)) {
				throw new Exception("Cannot convert from SimpleEvent to Event");
			}
			
			BaseEventSerializer ser = (BaseEventSerializer) Class.forName(serializerClass).newInstance();
			
			Event event= destination.requestEvent((Event) eventObject, responseEventURI.toString(), ser, timeout,
					overrideData);
			return this.getEventSerializer().deserialize(event, new DefaultSerializationContext(RuleSessionManager.getCurrentRuleSession(), this));
		} else {
			throw new Exception("Channel in an invalid state: " + channel.getState());
		}
	}

	@Override
	public void connect() throws Exception {
		destination.connect();
		logger.log(Level.INFO, "Destination:Connected[%s]", getURI());

	}

	@Override
	protected void destroy(RuleSession session) throws Exception {
		destination.close();
		logger.log(Level.INFO, "Destination:Destroyed[%s]", getURI());
	}

	@Override
	public void start(int mode) throws Exception {
		destination.start();
		logger.log(Level.INFO, "Destination:Started[%s:%s]", getURI(), mode);
	}

	@Override
	public void bind(final RuleSession session) throws Exception {
		super.bind(session);
		destination.setContext(session);
		EventProcessor eventProcessor = new EventProcessor() {
			@Override
			public void processEvent(Event event) throws Exception {
				SimpleEvent simpleEvent = CustomDestination.this.getEventSerializer().deserialize(event,
						new DefaultSerializationContext(session, CustomDestination.this));
				event.setDestinationURI(simpleEvent.getDestinationURI());
				EventContext baseCtx = destination.getEventContext(event);
				CustomEventContext ctx = new CustomEventContext(baseCtx, CustomDestination.this);
				simpleEvent.setContext(ctx);
				session.getTaskController().processEvent(CustomDestination.this, simpleEvent, ctx);
				stats.addEventIn();
			}

			@Override
			public String getRuleSessionName() {
				return session.getName();
			}
		};
		destination.bind(eventProcessor);
		destination.setEventProcessor(eventProcessor);
		logger.log(Level.INFO, "Destination:Bind[%s]", getURI());
	}

	@Override
	public int send(SimpleEvent simpleEvent, Map overrideData) throws Exception {
		//BE-26500: Messages should not be send if channel is stopped
		if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
			try {		
				if (simpleEvent.getPayload() instanceof XiNodePayload) {
					((XiNodePayload)simpleEvent.getPayload()).setJSONPayload(isJSONPayload());
				}
				Object eventObject = this.getEventSerializer().serialize(simpleEvent,
						new DefaultSerializationContext((RuleSession) destination.getRuleSession(), this));
				if (!(eventObject instanceof EventWithId)) {
					throw new Exception("Cannot convert from SimpleEvent to Event");
				}
				destination.send((EventWithId) eventObject, overrideData);
				stats.addEventOut();
				return 1;
			} catch (Exception e) {
				logger.log(Level.ERROR, e, "Destination:Exception occured while invoking send on destination : [%s]",
						getURI());
			}
		} else {
			this.channel.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", this.channel.getState());
		}

		return -1;
	}

	@Override
	public void suspend() {
		destination.suspend();
	}
	
	@Override
	public boolean isSuspended() {
		return destination.isSuspended();
    }

	@Override
	public void resume() {
		if (!this.userControlled) destination.resume();
	}
	
	public BaseDestination getBaseDestination(){
		return destination;
	}
	
	//Called via reflection from PayloadFactoryImpl
	public boolean isJSONPayload() {
		if (serializer instanceof BaseEventSerializer) {
			return ((BaseEventSerializer)serializer).isJSONPayload();
		}
		return false;
	}
}
