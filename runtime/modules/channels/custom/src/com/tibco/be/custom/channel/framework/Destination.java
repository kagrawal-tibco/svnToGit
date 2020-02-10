package com.tibco.be.custom.channel.framework;

import java.util.Map;
import java.util.Properties;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.EventWithId;
import com.tibco.be.custom.channel.EventProcessor;
import com.tibco.be.custom.channel.Event;

/**
 * @.category public-api
 */
public interface Destination {
	
	/**
	 * 
	 * @throws Exception
	 */
	
	void init() throws Exception;
	
	/**
	 * Connects the <code>Destination</code> to the underlying endpoint.
	 * <p>
	 * Only valid on an initialized <code>Destination</code>. After this call,
	 * you will be able to <code>start</code> or <code>close</code> the
	 * <code>Destination</code>.
	 * <p>
	 * <em>Do not start sending or receiving data data here.</em>
	 * 
	 * @throws Exception
	 *             when the Destination could not be connected to the underlying
	 *             endpoint.
	 * @see #start()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	void connect() throws Exception;

	/**
	 * Starts the destination, so that it can receive data from the endpoint.
	 * <p>
	 * Only valid on a connected <code>Destination</code> After this call, you
	 * will be able to <code>stop</code> and <code>close</code> the
	 * <code>Destination</code>.
	 *
	 * @throws Exception
	 *             when the Destination could not be started.
	 * @see #connect()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	void start() throws Exception;

	/**
	 * Closes the Destination and frees up all its resources.
	 * <p>
	 * After this call, you will be able to <code>init</code> the
	 * <code>Destination</code>.
	 * 
	 * @throws Exception
	 *             encountered exception
	 * @.category public-api
	 * @since 5.4
	 */
	void close() throws Exception;

	void suspend();
	
	boolean isSuspended();

	void resume();


	/**
	 * Sends an event on this <code>Destination</code>.
	 *
	 * @param event
	 *            the <code>Event</code> to send.
	 * @param overrideData
	 *            A <code>Map</code> of <code>String</code> name to
	 *            <code>String</code> value describing destination property
	 *            values, or null if no property value needs overriding. The
	 *            property names are provided by the driver implementor. See the
	 *            <code>drivers.xml</code> file of the specific destination for
	 *            more information.
	 * @throws Exception
	 *             when the event could not be sent
	 * @since 5.4
	 */
	void send(EventWithId event, Map overrideData) throws Exception;

	/**
	 * Initializes the listener(s) for the specified EventProcessor. The framework can call this method
	 * multiple times, once for each agent where-in this destination is specified as input destination.
	 * Listener(s) must just be initialized here, actual polling should only start on {@link #start() start}.
	 * The threading model is left to the programmer.
	 * 
	 * @throws Exception
	 *             encountered exception
	 * @since 5.4.1
	 */
	void bind(EventProcessor eventProcessor) throws Exception;

	/**
	 * Sends an event on the specified destination and waits for a response.
	 * 
	 * @param outevent
	 *            The outgoing <code>Event</code>
	 * @param responseEventURI
	 *            the URI of the response event
	 * @param serializer 
	 * 			  the instance of serializer
	 * @param timeout 
	 * 			  timeout
	 * @param overrideData
	 *            A <code>Map</code> of <code>String</code> name to
	 *            <code>String</code> value describing destination property
	 *            values, or null if no property value needs overriding. The
	 *            property names are provided by the driver implementor. See the
	 *            <code>drivers.xml</code> file of the specific destination for
	 *            more information.
	 * 
	 * @return Returns the response deserialized as <code>Event</code>.
	 * @throws Exception 
	 * 			  Exception when requestEvent failed
	 * @since 5.4
	 */
	Event requestEvent(Event outevent, String responseEventURI, BaseEventSerializer serializer, long timeout, Map overrideData)
			throws Exception;

	void setEventProcessor(EventProcessor eventCallback);

	void initInputDestination(BaseChannel channel, String name, String destinationUri, String defaultEventUri,
			Properties properties, BaseEventSerializer eventSerializer) throws Exception;
}
