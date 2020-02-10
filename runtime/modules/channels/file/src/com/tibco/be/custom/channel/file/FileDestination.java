package com.tibco.be.custom.channel.file;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventWithId;

public class FileDestination extends BaseDestination {

	String filePath = "D:/temp/";
	File file = null;
	FileInputStream stream = null;
	Object context;
	FileAddedListener listener = null;
	int pollInterval = 10000;

	@Override
	public void send(EventWithId event, Map overrideData) throws Exception {
		final Object message = this.getSerializer().serializeUserEvent(event, null);
		if (null != message) {
			this.send(message);
		}
	}

	private void send(final Object msg) throws Exception {

	}

	@Override
	public void connect() throws Exception {
		listener = new FileAddedListener(filePath, pollInterval, this.eventProcessor, context, this.getSerializer());
	}

	@Override
	public void start() throws Exception {
		if (listener != null)
			listener.start();
	}

	@Override
	public void close() throws Exception {
		if (listener != null)
			listener.stop();
	}

	@Override
	public EventWithId requestEvent(Event outevent, String responseEventURI, BaseEventSerializer serializer, long timeout,
			Map overrideData) throws Exception {

		return null;
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

}
