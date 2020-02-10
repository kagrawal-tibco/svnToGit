package com.tibco.be.custom.channel.file;

import java.util.Map;
import java.util.Properties;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.DefaultEventImpl;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.cep.kernel.service.logging.Logger;

public class FileEventSerializer extends BaseEventSerializer {

	long extId = 0;

	@Override
	public void initUserEventSerializer(String destinationName, Properties destinationProperties, Logger logger) {

	}

	@Override
	public Event deserializeUserEvent(Object message, Map<String, Object> properties) throws Exception {
		DefaultEventImpl event = new DefaultEventImpl();
		event.setExtId((extId++) + "");
		event.setPayload(message.toString().getBytes());
		event.setProperty("TYPE", "CREATE");
		return event;
	}

	@Override
	public Object serializeUserEvent(EventWithId event, Map<String, Object> properties) throws Exception {
		String message = "FILE_EVENT_SENT";
		return message;
	}

}
