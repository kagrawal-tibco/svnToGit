package com.tibco.be.custom.channel.kafka.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.be.custom.channel.kafka.KafkaEvent;
import com.tibco.cep.kernel.service.logging.Logger;

public class KafkaMapSerializer extends BaseEventSerializer {

	public static final String TOPIC = "topic";
	public static final String MESSAGE_PROP_EXT_ID = "extId";
	public static final String MESSAGE_PROP_EVENT_ID = "id";
	public static final String MESSAGE_PROP_PAYLOAD = "body";
	
	private String topic;
	
	public KafkaMapSerializer(){
		
	}
	
	@Override
	public void initUserEventSerializer(String destinationName, Properties destinationProperties, Logger logger) {
		this.topic = destinationProperties.getProperty(TOPIC);
	}

	@Override
	public Event deserializeUserEvent(Object message, Map<String, Object> properties) throws Exception {
		Event event = new KafkaEvent();
		if (message instanceof byte[]) {
			Map<String, Object> messageMap = deserializeMap((byte[])message);
			for (Entry<String, Object> entry : messageMap.entrySet()) {
				event.setProperty(entry.getKey(), entry.getValue());
			}
			//setting payload
			event.setPayload(messageMap.get(MESSAGE_PROP_PAYLOAD)!=null?(byte[])messageMap.get(MESSAGE_PROP_PAYLOAD):null);
			//setting extID
			event.setExtId(messageMap.get(MESSAGE_PROP_EXT_ID)!=null?(String) messageMap.get(MESSAGE_PROP_EXT_ID):null);
		} else {
			return null;
		}
		return event;
	}

	@Override
	public Object serializeUserEvent(EventWithId event, Map<String, Object> properties) throws Exception {
		Map<String, Object> messageMap = new HashMap<String, Object>();

		//setting ext id
		messageMap.put(MESSAGE_PROP_EXT_ID, event.getExtId());

		//setting event id
		messageMap.put(MESSAGE_PROP_EVENT_ID, event.getId());

		//setting payload
		messageMap.put(MESSAGE_PROP_PAYLOAD, event.getPayload());

		for (String prop : event.getAllPropertyNames()) {
			messageMap.put(prop, event.getPropertyValue(prop));
		}
		ProducerRecord<String, byte[]> kafkaMessage = new ProducerRecord<String, byte[]>((String) properties.get(TOPIC), String.valueOf(event.getId()), serializeMap(messageMap));
		return kafkaMessage;
	}
	
	private byte[] serializeMap(Map map) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(map);
		oos.close();
		return baos.toByteArray();
	}
	
	private Map deserializeMap(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return (Map)ois.readObject();
	}
}
