package com.tibco.cep.driver.kafka;

import com.tibco.cep.runtime.channel.ChannelProperties;

public class KafkaProperties implements ChannelProperties {
	public static final String KEY_CHANNEL_BOOTSTRAP_SERVER = "kafka.broker.urls";
	public static final String KEY_CHANNEL_SECURITY_PROTOCOL = "kafka.security.protocol";
	public static final String KEY_CHANNEL_SASL_MECHANISM = "kafka.sasl.mechanism";
	public static final String KEY_CHANNEL_TRUSTED_CERTS_FOLDER = "kafka.trusted.certs.folder";
	public static final String KEY_CHANNEL_KEYSTORE_IDENTITY = "kafka.keystore.identity";
	public static final String KEY_CHANNEL_TRUSTSTORE_PASSWORD = "kafka.truststore.password";
	
	public static final String KEY_DESTINATION_GROUP_ID = "group.id";
	public static final String KEY_DESTINATION_CLIENT_ID = "client.id";
	public static final String KEY_DESTINATION_TOPIC_NAME = "topic.name";
	public static final String KEY_DESTINATION_CONSUMER_THREADS = "consumer.threads";
	public static final String KEY_DESTINATION_POLL_INTERVAL = "poll.interval";
	public static final String KEY_DESTINATION_ENABLE_AUTOCOMMIT = "enable.autocommit";
	public static final String KEY_DESTINATION_AUTOCOMMIT_INTERVAL = "autocommit.interval";
	public static final String KEY_DESTINATION_HEARTBEAT_INTERVAL = "heartbeat.interval.msec";
	public static final String KEY_DESTINATION_SESSION_TIMEOUT = "session.timeout.msec";
	public static final String KEY_DESTINATION_COMPRESSION_TYPE = "compression.type";
	
//	public static final String KEY_DESTINATION_BATCH_SIZE = "batch.size";
//	public static final String KEY_DESTINATION_QUEUE_TIME = "queue.time";
	public static final String KEY_DESTINATION_SYNC_SENDER = "sync.sender";
	public static final String KEY_DESTINATION_SYNC_SENDER_MAX_WAIT = "sync.sender.max.wait";
	
	public static final String KEY_DESTINATION_MESSAGE_KEY_RF = "message.key.rf";
	
	public static final String KEY_SKIP_BE_ATTRIBUTES = "be.channel.kafka.skip.attributes";
	
	public static final String RESERVED_EVENT_PROP_MESSAGE_KEY = "_messageKey_";
	
	public static final String INTERNAL_PROP_KEY_CHANNEL_URI = "channel.uri";
	public static final String INTERNAL_PROP_KEY_DESTINATION_URI = "destination.uri";
	public static final String INTERNAL_PROP_KEY_KEY_SERIALIZER = "key.serializer";
	public static final String INTERNAL_PROP_KEY_VALUE_SERIALIZER = "value.serializer";
	public static final String INTERNAL_PROP_KEY_KEY_DESERIALIZER = "key.deserializer";
	public static final String INTERNAL_PROP_KEY_VALUE_DESERIALIZER = "value.deserializer";
	
	public static final String PROPERTY_KEY_KAFKA_CLIENT_PROPERTY_PREFIX = "be.channel.kafka";
	public static final String PROPERTY_KEY_KAFKA_ERROR_ENDPOINT_ENABLED = "be.kafka.error.endpoint.enable";
    public static final String PROPERTY_KEY_KAFKA_DEFAULT_ERROR_TOPIC_NAME = "be.kafka.default.error.topic.name";
    public static final String PROPERTY_KEY_KAFKA_PROCESS_EVENT_MAX_ATTEMPTS = "be.kafka.process.event.max.attempts";
    public static final String PROPERTY_KEY_KAFKA_PROCESS_EVENT_ATTEMPTS_INTERVAL = "be.kafka.process.event.attempts.interval";    
}
