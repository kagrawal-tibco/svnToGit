package com.tibco.cep.driver.as3;

import static com.tibco.datagrid.Event.TIBDG_EVENT_ERRORCODE_COPYSET_LEADER_FAILURE;
import static com.tibco.datagrid.Event.TIBDG_EVENT_ERRORCODE_GRID_REDISTRIBUTING;
import static com.tibco.datagrid.Event.TIBDG_EVENT_ERRORCODE_TABLE_DELETED;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.store.as.ASItem;
import com.tibco.datagrid.EventType;
import com.tibco.datagrid.Row;
import com.tibco.datagrid.TableEventHandler;
import com.tibco.datagrid.TableListener;

public class AS3EventListener implements TableEventHandler {
	private final EventProcessor eventProcessor;
	private AS3Destination destination;
	private final Logger logger;
	private State currentState;
	boolean[] consumerSupportEvents;
	Set<ListenerEvents> configuredEvents;

	private enum ListenerEvents {
		PUT, DELETE, EXPIRE
	}

	private enum State {
		ACTIVE, SUSPENDED
	}

	public AS3EventListener(EventProcessor eventProcessor, AS3Destination destination) {
		this.eventProcessor = eventProcessor;
		this.destination = destination;
		this.logger = destination.getLogger();
		this.currentState = State.SUSPENDED;
		this.configuredEvents = EnumSet.noneOf(ListenerEvents.class);
		
		if ("true".equals(destination.getDestinationProperties().getProperty("PutEvent"))) {
			configuredEvents.add(ListenerEvents.PUT);
		}
		if ("true".equals(destination.getDestinationProperties().getProperty("DeleteEvent"))) {
			configuredEvents.add(ListenerEvents.DELETE);
		}
		if ("true".equals(destination.getDestinationProperties().getProperty("ExpireEvent"))) {
			configuredEvents.add(ListenerEvents.EXPIRE);
		}

	}

	@Override
	public void eventsReceived(List<com.tibco.datagrid.Event> events, TableListener listener) {
		try {
			for (com.tibco.datagrid.Event event : events) {
				EventType eventType = event.getType();
				logger.log(Level.DEBUG, "Table event received: %s", eventType.toString());
				switch (eventType) {
				case PUT:
					if (configuredEvents.contains(ListenerEvents.PUT)) {
						processEventRow(event.getCurrent());
					}
					break;
				case DELETE:
					if (configuredEvents.contains(ListenerEvents.DELETE)) {
						processEventRow(event.getPrevious());
					}
					break;
				case EXPIRED:
					if (configuredEvents.contains(ListenerEvents.EXPIRE)) {
						processEventRow(event.getPrevious());
					}
					break;
				case ERROR:
					switch (event.getErrorCode()) {
					case TIBDG_EVENT_ERRORCODE_TABLE_DELETED:
					case TIBDG_EVENT_ERRORCODE_COPYSET_LEADER_FAILURE:
					case TIBDG_EVENT_ERRORCODE_GRID_REDISTRIBUTING:
						logger.log(Level.ERROR,
								"Error event received on the listener, errorcode: %s  description: %s closing the ActiveSpaces 3.x destination",
								event.getErrorCode(), event.getErrorDescription());
						this.destination.close();
						break;
					}
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "Error in the event listener.");
			throw new RuntimeException(e);
		}
	}

	private void processEventRow(Row row) {
		if (this.currentState == State.SUSPENDED) return;
		
		ASItem item = new ASItem();
		item.setRow(row);
		item.setContainer(destination.getASContainer());
		
		Event event = null;
		try {
			event = destination.getSerializer().deserializeUserEvent(item, null);
			eventProcessor.processEvent(event);
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "Error while processing event.");
			throw new RuntimeException(e);
		} finally {
			try {
				item.destroy();
			} catch (Exception e) {
				logger.log(Level.ERROR, e, "Error while destroying the item.");
				throw new RuntimeException(e);
			}
		}
	}

	public void resume() {
		this.currentState = State.ACTIVE;
	}

	public void suspend() {
		this.currentState = State.SUSPENDED;
	}
}
