package com.tibco.be.custom.channel.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class KafkaListener implements Runnable {
	private final KafkaConsumer<String, byte[]> consumer;
	private final EventProcessor eventProcessor;
	private final BaseEventSerializer serializer;
	private final int pollInterval;
	private final Logger logger;

	public KafkaListener(final KafkaConsumer<String, byte[]> consumer, final int threadNumber,
			final EventProcessor eventProcessor, BaseEventSerializer serializer, int pollInterval, Logger logger) {
		this.consumer = consumer;
		this.eventProcessor = eventProcessor;
		this.serializer = serializer;
		this.pollInterval = pollInterval;
		this.logger = logger;
	}

	@Override
	public void run() {
		while (true) {
			ConsumerRecords<String, byte[]> records = consumer.poll(pollInterval);
			for (ConsumerRecord<String, byte[]> record : records) {

				Event event = null;
				try {
					event = serializer.deserializeUserEvent(record.value(), null);
				} catch (Exception e) {
					logger.log(Level.ERROR, "KafkaListener : Exception occurred while deserializing message : " + e);
				}
				if (event != null) {
					try {
						eventProcessor.processEvent(event);
					} catch (final Exception e) {
						logger.log(Level.ERROR, e, "KafkaListener : Exception occurred while processing event : " + e);
					}
				}
			}
		}
	}

	public void close() {
		consumer.close();
	}
}