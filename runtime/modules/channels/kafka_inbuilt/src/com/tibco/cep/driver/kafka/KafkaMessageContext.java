package com.tibco.cep.driver.kafka;
import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventContext;

/**
 * EventContext for Kafka events.
 * 
 * @author moshaikh
 */
public class KafkaMessageContext implements EventContext {

	private BEKafkaConsumer beKafkaConsumer;
	private Event event;

	public KafkaMessageContext(Event event, BEKafkaConsumer beKafkaConsumer) {
		this.event = event;
		this.beKafkaConsumer = beKafkaConsumer;
	}

	@Override
	public boolean reply(Event replyEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acknowledge() {
		this.beKafkaConsumer.setCurrentEventAcked();
	}

	@Override
	public void rollback() {
		this.beKafkaConsumer.setCurrentEventRolledback();
	}

	@Override
	public BaseDestination getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
