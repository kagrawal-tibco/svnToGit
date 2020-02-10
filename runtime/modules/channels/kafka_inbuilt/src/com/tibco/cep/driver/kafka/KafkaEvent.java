package com.tibco.cep.driver.kafka;

import com.tibco.be.custom.channel.framework.CustomEvent;

public class KafkaEvent extends CustomEvent {
	
	private BEKafkaConsumer beKafkaConsumer;

	public void setBEKafkaConsumer(BEKafkaConsumer beKafkaConsumer) {
		this.beKafkaConsumer = beKafkaConsumer;
	}
	
	public BEKafkaConsumer getBEKafkaConsumer() {
		return beKafkaConsumer;
	}
}
