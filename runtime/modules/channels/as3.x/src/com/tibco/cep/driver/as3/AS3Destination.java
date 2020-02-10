package com.tibco.cep.driver.as3;

import java.util.Map;

import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.store.as.ASConnection;
import com.tibco.cep.store.as.ASContainer;
import com.tibco.cep.store.as.ASItem;
import com.tibco.datagrid.TableListener;

public class AS3Destination extends BaseDestination {
	private ASContainer asContainer;
	private String tableName;
	private String filter;
	private TableListener listener;
	private AS3EventListener eventConsumer;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.be.custom.channel.BaseDestination#init()
	 */
	@Override
	public void init() throws Exception {
		String filter = (String) getChannel().getGlobalVariableValue(destinationProperties.getProperty("Filter"));
		if (!filter.isEmpty()) {
			this.filter = filter;
		}
		this.tableName = (String) getChannel().getGlobalVariableValue(destinationProperties.getProperty("TableName"));
		if (this.tableName.isEmpty()) {
			throw new RuntimeException(String.format("Table Name is empty in destination [%s] in Channel [%s] ",
					this.destinationName, this.getChannel().getUri()));
		}
	}

	@Override
	public void connect() throws Exception {
		AS3Channel channel = (AS3Channel) this.getChannel();
		ASConnection connection = channel.getConnection();
		asContainer = (ASContainer) connection.createContainer(this.tableName);
		((AS3Serializer)this.getSerializer()).setContainer(asContainer);
		((AS3Serializer)this.getSerializer()).setEvent(this.defaultEventUri);
	}

	@Override
	public void start() throws Exception {
		// starts in suspended mode
		if (eventConsumer != null) {
			if (listener == null) listener = asContainer.getTable().createTableListener(filter, eventConsumer);
			else eventConsumer.resume();
			getLogger().log(Level.INFO, "Activespaces 3.x destination started - " + this.getUri() + ", Serializer:"
					+ this.getSerializer().getClass());
		}
	}

	public ASContainer getASContainer() {
		return asContainer;
	}

	@Override
	public void close() throws Exception {
		getLogger().log(Level.INFO, "Closing Activespaces 3.x destination - " + this.getUri());
		if (listener != null) {
			listener.close();
		}
	}

	@Override
	public void send(EventWithId event, Map userData) throws Exception {
		ASItem item = (ASItem) getSerializer().serializeUserEvent(event, userData);
		try {
			this.asContainer.putItem(item);
		} finally {
			item.destroy();
		}
	}

	public void bind(EventProcessor ep) throws Exception {
		// create and bind eventConsumer
		if(this.eventConsumer == null) {
			this.eventConsumer = new AS3EventListener(ep, this);
		}
	}

	@Override
	public void resume() {
		synchronized (this) {
			this.suspended = false;
			if (eventConsumer != null) {
				this.eventConsumer.resume();
			}
			this.getLogger().log(Level.INFO, "Destination Resumed : " + getUri());
		}
	}

	@Override
	public void suspend() {
		synchronized (this) {
			this.suspended = true;
			if (eventConsumer != null) {
				this.eventConsumer.suspend();
			}
			this.getLogger().log(Level.INFO, "Destination Suspended : " + getUri());
		}
	}

	@Override
	public Event requestEvent(Event outevent, String responseEventURI, BaseEventSerializer serializer, long timeout,
			Map userData) throws Exception {
		return null;
	}
}
