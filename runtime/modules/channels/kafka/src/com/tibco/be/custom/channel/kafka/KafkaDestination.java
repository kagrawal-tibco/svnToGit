package com.tibco.be.custom.channel.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.cep.kernel.service.logging.Level;

public class KafkaDestination extends BaseDestination {

	private List<KafkaListener> listeners = new ArrayList<KafkaListener>();
	private Producer<String, byte[]> producer;
	private Properties consumerProps = new Properties();
	private Properties producerProps = new Properties();
	private String topic = "";
	private int threads = 0;
	private int pollInterval;

	// keep a reference to the channel's executor service
	private ExecutorService executor;

	// CONSTANTS
	public static final String PROP_TOPIC = "topic";
	public static final String CONFIG_BOOTSTRAP_SERVERS = "kafka.broker.urls";
	public static final String CONFIG_GROUP_ID = "group.id";
	public static final String CONFIG_CLIENT_ID = "client.id";
	public static final String CONFIG_TOPIC = "topic";
	public static final String CONFIG_POLL_INTERVAL = "poll.interval";
	public static final String CONFIG_THREADS = "consumer.threads";

	// Serializers/Deserializers
	public static final String KAFKA_STRING_SERIALIZER_URI = "org.apache.kafka.common.serialization.StringSerializer";
	public static final String KAFKA_STRING_DESERIALIZER_URI = "org.apache.kafka.common.serialization.StringDeserializer";
	public static final String KAFKA_BYTE_ARRAY_SERIALIZER_URI = "org.apache.kafka.common.serialization.ByteArraySerializer";
	public static final String KAFKA_BYTE_ARRAY_DESERIALIZER_URI = "org.apache.kafka.common.serialization.ByteArrayDeserializer";

	/**
	 * Initialize the destination. Store a handle to the channel's resource - in
	 * this case the executor which is initialized during Channel.init(), and
	 * set some Kafka destination related configurations.
	 */
	@Override
	public void init() throws Exception {
		executor = ((KafkaChannel) getChannel()).getJobPool();
		
		try {
			threads = Integer.parseInt(getDestinationProperties().getProperty(CONFIG_THREADS));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		topic = getDestinationProperties().getProperty(CONFIG_TOPIC);
		
		try {
			pollInterval = Integer.parseInt(getDestinationProperties().getProperty(CONFIG_POLL_INTERVAL));
		} catch (Exception e) {
			e.printStackTrace();
		}

		initConsumerProps();

		initProducerProps();
	}

	/**
	 * connect() is where we connect the destination to its transport. In this case
	 * the KafkaConsumer internally connects to the server Note that it has not
	 * yet started receiving or consuming. That will happen in the start() call
	 */
	@Override
	public void connect() throws Exception {
		//Create producer(s)
		producer = new KafkaProducer<>(producerProps);
	}
	
	/**
	 * Create listener for the specified EventProcessor don't start polling here.
	 */
	@Override
	public void bind(EventProcessor eventProcessor) throws Exception {
		//Create consumer(s) for received EventProcessor, don't start polling yet
		for (int i = 0; i < threads; i++) {
			KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(consumerProps);
			consumer.subscribe(Arrays.asList(topic));
			KafkaListener listener = new KafkaListener(consumer, i, eventProcessor, getSerializer(), pollInterval, getLogger());
			listeners.add(listener);
		}
	}
	
	/**
	 * Start receiving Kafka messages on this destination. A Kafka receiver job
	 * is started. This job runs forever, polling the Kafka endpoint for
	 * messages.
	 */
	@Override
	public void start() throws Exception {
		for (final KafkaListener listener : listeners) {
			// Start Listeners (start polling)
			executor.submit(listener);
		}
	}
	
	/**
	 * Stop the consumers.
	 */
	@Override
	public void close() throws Exception {
		if (producer != null) {
			producer.close();
		}
		if (listeners != null) {
			for (final KafkaListener listener : listeners) {
				listener.close();
			}
		}
	}
	
	/**
	 * Send the event
	 */
	@Override
	public void send(EventWithId event, Map overrideData) throws Exception {
		HashMap<String, Object> serializationProps = new HashMap<String, Object>();
		serializationProps.put(PROP_TOPIC, topic);
		final Object message = getSerializer().serializeUserEvent(event, serializationProps);
		if (null != message) {
			send(message, overrideData);
		}
	}
	
	@Override
	public Event requestEvent(Event outevent, String responseEventURI, BaseEventSerializer serializer, long timeout,
			Map overrideData) throws Exception {
		throw new Exception("requestEvent() is not supported with this Channel");
	}
	
	/**
	 * Initializes Consumer props
	 */
	private void initConsumerProps() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				getChannel().getChannelProperties().getProperty(CONFIG_BOOTSTRAP_SERVERS));
		props.put(ConsumerConfig.GROUP_ID_CONFIG, getDestinationProperties().getProperty(CONFIG_GROUP_ID));
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KAFKA_STRING_DESERIALIZER_URI);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KAFKA_BYTE_ARRAY_DESERIALIZER_URI);
		consumerProps = props;
	}
	
	/**
	 * Initializes Producer props
	 */
	private void initProducerProps() {
		final Properties props = new Properties();
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KAFKA_STRING_SERIALIZER_URI);	
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KAFKA_BYTE_ARRAY_SERIALIZER_URI);
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				getChannel().getChannelProperties().getProperty(CONFIG_BOOTSTRAP_SERVERS));
		producerProps = props;
	}
	
	/**
	 * Sends kafka message to the topic
	 */
	private int send(final Object msg, Map overrideData) throws Exception {
		logger.log(Level.DEBUG, "Sending Kafka msg %s", msg);
		Future<RecordMetadata> future = producer.send((ProducerRecord<String, byte[]>) msg);
		future.get();//Wait till the message is actually sent.
		logger.log(Level.DEBUG, "Sent Kafka msg");
		return 1;
	}
}
