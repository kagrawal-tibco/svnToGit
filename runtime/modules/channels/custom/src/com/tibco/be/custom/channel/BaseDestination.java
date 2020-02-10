package com.tibco.be.custom.channel;

import java.util.Map;
import java.util.Properties;

import com.tibco.be.custom.channel.framework.Destination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * A <code>BaseDestination</code> represent the end-points that the transport
 * can use.
 * <br>
 * The execution sequence in a Destination's lifecycle is as follows :
 * <br>
 * {@link #init() init()} -&gt; {@link #connect() connect()} -&gt;
 * {@link #start() start()} -&gt; {@link #close() close()}
 * <p>
 * Users must take care of this sequence while providing the Destination's
 * implementation
 * @.category public-api
 * @since 5.4
 */
public abstract class BaseDestination implements Destination {

	private final Object syncObject = new Object();

	protected Object context;

	/**
	 * Reference to the Channel that this Destination is part of.
	 * @.category public-api
	 * @since 5.4
	 */
	protected BaseChannel channel;

	/**
	 * Stores the name of the destination
	 * @.category public-api
	 * @since 5.4
	 */
	protected String destinationName;

	/**
	 * Flag denoting that the Destination is suspended or not
	 * @.category public-api
	 * @since 5.4
	 */
	protected boolean suspended = false;

	/**
	 * Stores the URI of the {@link BaseDestination Destination}
	 * @.category public-api
	 * @since 5.4
	 */
	protected String uri;

	/**
	 * Stores the default Event's URI associated to this {@link BaseDestination
	 * Destination}
	 * @.category public-api
	 * @since 5.4
	 */
	protected String defaultEventUri;

	/**
	 * Stores the serializer used to serialize events into objects that the
	 * underlying transport can use, and deserialize those objects into events.
	 * @.category public-api
	 * @since 5.4
	 */
	protected BaseEventSerializer serializer;

	/**
	 * Stores the Logger instance associated
	 * @.category public-api
	 * @since 5.4
	 */
	protected Logger logger;

	/**
	 * Stores the destination properties
	 * @.category public-api
	 * @since 5.4
	 */
	protected Properties destinationProperties;

	/**
	 * The instance of <code>EventProcessor</code>.This is used to process
	 * events received on the destination
	 * @.category public-api
	 */
	protected EventProcessor eventProcessor;

	public BaseDestination() {
	}

	/**
     * Initializes the {@link BaseDestination Destination} by allocating necessary resources, creating pools, etc.
     * After this call, you will be able to
     * <code>connect</code>, <code>start</code> or <code>close</code> this <code>Destination</code>.
     * @throws Exception when initialization failed
	 * @see #connect()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	abstract public void init() throws Exception;

	/**
	 * Connects the {@link BaseDestination Destination} to its underlying end-point.
	 * <p> After this call, you will be able to <code>start</code> or <code>close</code>
	 * the {@link BaseDestination Destination}.
	 * <p>
	 * <em>Do not start sending or receiving data here.</em>
	 * 
	 * @throws Exception
	 *             when the Destination could not be connected to the underlying
	 *             endpoint.
	 * @see #start()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	@Override
	public abstract void connect() throws Exception;

	/**
	 * Starts the destination, so that it can receive data from the end-point.
	 * <p>
	 * Only valid on a connected {@link BaseDestination Destination} After this
	 * call, you will be able to <code>stop</code> and <code>close</code> the
	 * {@link BaseDestination Destination}.
	 *
	 * @throws Exception
	 *             when the Destination could not be started.
	 * @see #connect()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	@Override
	public abstract void start() throws Exception;

	/**
	 * Closes the Destination and frees up all its resources.
	 * <p>
	 * After this call, you will be able to <code>init</code> the
	 * {@link BaseDestination Destination}.
	 * 
	 * @throws Exception
	 *             encountered exception
	 * @.category public-api
	 * @since 5.4
	 */
	@Override
	public abstract void close() throws Exception;

	/**
	 * Sets the flag suspended to "true". Users can override this method to
	 * provide their own implementation of suspend
	 * @.category public-api
	 * @since 5.4
	 */
	public void suspend() {
		synchronized (syncObject) {
			this.suspended = true;
			this.getLogger().log(Level.INFO, "Destination Suspended : " + getUri());
		}
	}

	/**
	 * Returns the suspension state of Destination.
	 * @.category public-api
	 * @since 5.4.1
	 */
	@Override
	public boolean isSuspended() {
		return this.suspended;
	}

	/**
	 * Sets the flag suspended to "false". Users can override this method to
	 * provide their own implementation of resume
	 * @.category public-api
	 * @since 5.4
	 */
	public void resume() {
		synchronized (syncObject) {
			this.suspended = false;
			syncObject.notifyAll();
			this.getLogger().log(Level.INFO, "Destination Resumed : " + getUri());
		}
	}

	/**
	 * Sends an event on this {@link BaseDestination Destination}.
	 * 
	 * This method is invoked when the BE application code invokes <code>Event.send()</code>
	 *
	 * @param event
	 *            the {@link EventWithId Event} to send. The user's Destination implementation should use the Destination's serializer as configured in the project and send this data to its configured end-point
	 * @param userData During runtime, the application code can optionally pass a map of key/value pairs using <code>Event.sendEvent</code>.
	 * The users destination may choose to make use of this additional data.
	 * 
	 * @throws Exception
	 *             when the event could not be sent
	 * @.category public-api
	 * @since 5.4
	 */
	@Override
	public abstract void send(EventWithId event, Map userData) throws Exception;

	/**
	 * Initializes the listener(s) for the specified EventProcessor. The framework can call this method
	 * multiple times, once for each agent where-in this destination is specified as input destination.
	 * Listener(s) must just be initialized here, actual polling should only start on {@link #start() start}.
	 * The threading model is left to the programmer.
	 * 
	 * @throws Exception
	 *             encountered exception
	 * @.category public-api
	 * @since 5.4.1
	 */
	@Override
	public void bind(EventProcessor eventProcessor) throws Exception {
		
	}

	/**
	 * Sends an event on the specified destination and waits for a response.
	 * 
	 * @param outevent
	 *            The outgoing {@link Event Event}
	 * @param responseEventURI
	 *            the URI of the response event
	 * @param serializer
	 *            the instance of serializer
	 * @param timeout
	 *            timeout
	 * @param userData
	 *            During runtime, the application code can optionally pass a map of key/value pairs using <code>Event.requestEvent</code>.
	 * The users destination may choose to make use of this additional data.
	 * 
	 * @return Returns the an {@link Event Event} of type <code>responseEventURI</code>
	 * @throws Exception
	 *             Exception when request failed
	 * @.category public-api
	 * @since 5.4
	 */
	@Override
	public abstract Event requestEvent(Event outevent, String responseEventURI, BaseEventSerializer serializer, long timeout,
			Map userData) throws Exception;


	/**
	 * Gets the name of the {@link BaseDestination
	 * Destination}
	 *
	 * @return the {@link BaseDestination Destination} name
	 * @.category public-api
	 * @since 5.4
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * Gets the Properties associated with the
	 * {@link BaseDestination Destination}
	 *
	 * @return the {@link BaseDestination Destination} Properties
	 * @.category public-api
	 * @since 5.4
	 */
	public Properties getDestinationProperties() {
		return destinationProperties;
	}


	/**
	 * Gets the URI of this {@link BaseDestination
	 * Destination}
	 *
	 * @return URI of the Destination
	 * @.category public-api
	 * @since 5.4
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Gets the default Event URI associated with this
	 * {@link BaseDestination Destination}
	 *
	 * @return default Event URI associated with this {@link BaseDestination
	 *         Destination}
	 * @.category public-api
	 * @since 5.4
	 */
	public String getDefaultEventUri() {
		return defaultEventUri;
	}

	/**
	 * Gets the {@link BaseChannel Channel} instance
	 * associated with this destination
	 *
	 * @return the instance of the {@link BaseChannel Channel} associated with
	 *         this destination
	 * @.category public-api
	 * @since 5.4
	 */
	public BaseChannel getChannel() {
		return channel;
	}

	/**
	 * Gets the <code>BaseEventSerializer</code> instance
	 * associated with this destination
	 *
	 * @return the <code>BaseEventSerializer</code> instance associated with
	 *         this destination
	 * @.category public-api
	 * @since 5.4
	 */
	public BaseEventSerializer getSerializer() {
		return serializer;
	}


	/**
	 * Gets the <code>Logger</code> of the
	 * <code>BE Project</code> that contains this {@link BaseChannel Channel}.
	 *
	 * @return the <code>Logger</code> of the containing <code>BE Project</code>
	 * @.category public-api .
	 * @since 5.4
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets the instance of <code>EventProcessor</code>.This
	 * is used to process events received on the destination
	 * 
	 * @return the <code>EventProcessorr</code>.
	 * @.category public-api
	 * @since 5.4
	 */
	public EventProcessor getEventProcessor() {
		return eventProcessor;
	}
	
	
	/**
	 * Gets the user <code>EventContext</code>
	 * You can override to provide custom implementation for <code>EventContext</code>
	 * If not defined the DefaultEventContext will be used.
	 * 
	 * @return the <code>EventContext</code>.
	 * @param event the  event for which <code>EventContext</code> is required.
	 * @.category public-api
	 * @since 5.4
	 */
	public EventContext getEventContext(Event event) {
		return new DefaultEventContext(event, this);
	}

	/**
	 * for internal use
	 * @return context
	 */
	public final Object getRuleSession() {
		return context;
	}

	/**
	 * for internal use
	 * @param context context
	 */
	public final void setContext(Object context) {
		this.context = context;
	}

	/**
	 * for internal use
	 */
	@Override
	public final void setEventProcessor(EventProcessor eventCallback) {
		this.eventProcessor = eventCallback;
	}


	/**
	 * for internal use
	 */
	@Override
	public final void initInputDestination(BaseChannel channel, String destinationName, String destinationUri,
			String defaultEventUri, Properties properties, BaseEventSerializer eventSerializer) {
		this.channel = channel;
		this.destinationName = destinationName;
		this.serializer = (BaseEventSerializer) eventSerializer;
		this.uri = destinationUri;
		this.logger = channel.getLogger();
		this.destinationProperties = properties;
		this.defaultEventUri = defaultEventUri;

		this.getLogger().log(Level.DEBUG, "BaseDestination initialized : " + getUri());
	}
}
