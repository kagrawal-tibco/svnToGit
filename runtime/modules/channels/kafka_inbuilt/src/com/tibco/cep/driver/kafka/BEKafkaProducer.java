package com.tibco.cep.driver.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * This class encloses a KafkaProducer instance that is responsible to send out
 * messages.
 * 
 * @author moshaikh
 */
public class BEKafkaProducer {
	private final BaseEventSerializer serializer;
	private final boolean syncSender;
	private final int syncSenderMaxWait;
	private Properties producerProperties;
	private final Map<String, Object> serializationProperties;

	private KafkaProducer producer;

	private final Logger logger;

	public BEKafkaProducer(BaseEventSerializer ser, Properties destinationProps, Properties beProperties, Logger logger)
			throws Exception {
		this.logger = logger;
		this.serializer = ser;
		this.syncSender = Boolean
				.parseBoolean(destinationProps.getProperty(KafkaProperties.KEY_DESTINATION_SYNC_SENDER));
		this.syncSenderMaxWait = Integer
				.parseInt(destinationProps.getProperty(KafkaProperties.KEY_DESTINATION_SYNC_SENDER_MAX_WAIT, "30000"));
		initProducerProperties(destinationProps, beProperties);

		this.serializationProperties = new HashMap<String, Object>();
		boolean includeEventType = BEKafkaProducer.isIncludeEventTypeWhileSerialize(
				destinationProps.getProperty(KafkaProperties.KEY_DESTINATION_INCLUDE_EVENTTYPE, "ALWAYS"));
		this.serializationProperties.put(KafkaProperties.KEY_DESTINATION_INCLUDE_EVENTTYPE, includeEventType);

		initProducer();
	}

	private void initProducer() {
		producer = new KafkaProducer(producerProperties);
	}

	private void initProducerProperties(Properties destinationProps, Properties beProperties) throws Exception {
		producerProperties = new Properties();

		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				destinationProps.getProperty(KafkaProperties.KEY_CHANNEL_BOOTSTRAP_SERVER), producerProperties);
		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				destinationProps.getProperty(KafkaProperties.INTERNAL_PROP_KEY_KEY_SERIALIZER), producerProperties);
		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				destinationProps.getProperty(KafkaProperties.INTERNAL_PROP_KEY_VALUE_SERIALIZER), producerProperties);
		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.CLIENT_ID_CONFIG,
				destinationProps.getProperty(KafkaProperties.KEY_DESTINATION_CLIENT_ID), producerProperties);
		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.COMPRESSION_TYPE_CONFIG,
				destinationProps.getProperty(KafkaProperties.KEY_DESTINATION_COMPRESSION_TYPE), producerProperties);
		KafkaPropertiesHelper.putValueIfNotEmpty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, syncSenderMaxWait,
				producerProperties);

		KafkaPropertiesHelper.initSslProperties(destinationProps, producerProperties);

		String channelUri = destinationProps.getProperty(KafkaProperties.INTERNAL_PROP_KEY_CHANNEL_URI);
		String destinationUri = destinationProps.getProperty(KafkaProperties.INTERNAL_PROP_KEY_DESTINATION_URI);

		KafkaPropertiesHelper.loadOverridenProperties(KafkaProperties.PROPERTY_KEY_KAFKA_CLIENT_PROPERTY_PREFIX,
				beProperties, producerProperties, channelUri, destinationUri);

		logger.log(Level.INFO,
				"Initializing KafkaProducer for Destination '%s' with properties - SyncSender:%s, RequestTimeout:%s,"
						+ " CompressionType:%s",
				destinationUri, syncSender, producerProperties.get(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG),
				producerProperties.get(ProducerConfig.COMPRESSION_TYPE_CONFIG));
	}

	public void send(EventWithId event, Map overrideData) throws Exception {
		Object obj = serializer.serializeUserEvent(event, serializationProperties);
		if (obj instanceof ProducerRecord) {
			sendProducerRecord((ProducerRecord) obj);
		}
	}

	public void sendProducerRecord(ProducerRecord producerRecord) throws Exception {
		logger.log(Level.DEBUG, "Sending message:\n" + producerRecord);
		Future future = producer.send(producerRecord);
		if (syncSender) {
			future.get(syncSenderMaxWait, TimeUnit.MILLISECONDS);
		}
	}

	public void close() {
		producer.close();
	}

	static boolean isIncludeEventTypeWhileDeserialize(String includeEventTypeValue) {
		return "ALWAYS".equalsIgnoreCase(includeEventTypeValue)
				|| "ON_DESERIALIZE".equalsIgnoreCase(includeEventTypeValue);
	}

	static boolean isIncludeEventTypeWhileSerialize(String includeEventTypeValue) {
		return "ALWAYS".equalsIgnoreCase(includeEventTypeValue)
				|| "ON_SERIALIZE".equalsIgnoreCase(includeEventTypeValue);
	}

	/*
	 * public static boolean validateAddresses(List<String> urls) {//Can't use this
	 * in studio since it would also try to resolve hostnames. try {
	 * ClientUtils.parseAndValidateAddresses(urls); return true; } catch(Exception
	 * e) { return false; } }
	 */
}
