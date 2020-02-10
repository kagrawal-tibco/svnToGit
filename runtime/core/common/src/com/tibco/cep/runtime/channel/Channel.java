package com.tibco.cep.runtime.channel;


import java.util.Map;

import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * A <code>Channel</code> represents a communication transport which can receive and send
 * {@link SimpleEvent SimpleEvent} objects.
 * <p>Each <code>Channel</code> instance has a set of <code>Channel.Destination</code>
 * which represent the end-points that the transport can use.</p>
 *
 * @version 2.0
 * @.category public-api
 * @since 2.0
 */
public interface Channel {


    /**
     * Name of the message property that specifies the name of the BusinessEvents event type corresponding to the message.
     * <p>Used in conjunction with <code>EVENT_NAMESPACE_PROPERTY</code>.</p>
     * <p>If found in an incoming message, the message will be mapped to an event of that type.</p>
     * <p>When sending an event, the outgoing message will contain a property of that name specifying the type of the event sent.
     *
     * @see #EVENT_NAMESPACE_PROPERTY
     * @since 2.0
     */
    public static String EVENT_NAME_PROPERTY = "_nm_";
    // TODO This is about event [de]serialization => Why is this not in Destination instead?


    /**
     * <p/>
     * Name of the message property that specifies the namespace of the BusinessEvents event type corresponding to the message.
     * <p>Used in conjunction with <code>EVENT_NAME_PROPERTY</code>.</p>
     * <p>If found in an incoming message, the message will be mapped to an event of that type.</p>
     * <p>When sending an event, the outgoing message will contain a property of that name specifying the type of the event sent.
     *
     * @see #EVENT_NAME_PROPERTY
     * @since 2.0
     */
    public static String EVENT_NAMESPACE_PROPERTY = "_ns_";
    //TODO This is about event [de]serialization => Why is this not in Destination instead?


    /**
     * Gets the current state of the <code>Channel</code>.
     *
     * @return a State
     * @since 2.0
     */
    public State getState();


    /**
     * Gets the full path of the <code>Channel</code> in the project.
     *
     * @return a String URI.
     * @since 2.0
     */
    public String getURI();


    /**
     * Initializes the <code>Channel</code> by allocating necessary resources, creating pools, etc.
     * <p>Only valid on an uninitialized or closed channel.
     * After this call, you will be able to
     * <code>connect</code> or <code>close</code> the <code>Channel</code>.
     * <p><em>Do not connect the underlying transport here.</em></p>
     * <p><em>The implementor must make sure that all the channel's Destinations are created,
     * and must now {@link com.tibco.cep.runtime.channel.Channel.Destination#init() initialize} them.</em></p>
     *
     * @throws Exception exception logged by the ChannelManager.
     * @see #connect()
     * @see #close()
     * @since 2.0
     */
    public void init() throws Exception;


    /**
     * Connects the <code>Channel</code> to the underlying transport so that it can send outbound messages.
     * This in turn could mean connecting to the server etc.
     * Once successfully connected, the connections could be added to the pool.
     * <p>Only valid on an initialized channel.
     * After this call, you will be able to
     * <code>start</code> or <code>close</code> the <code>Channel</code>.
     * <p><em>Do not start receiving data data here.</em></p>
     * <p><em>The implementor must connect all the destinations of this <code>Channel</code>.</em></p>
     *
     * @throws Exception exception logged by the ChannelManager
     * @see com.tibco.cep.runtime.channel.Channel.Destination#connect()
     * @see #init()
     * @see #start(int)
     * @see #close()
     * @since 2.0
     */
    public void connect() throws Exception;


    /**
     * Starts the channel, so that it can receive data through its underlying transport.
     * <p>Only valid on a connected channel.
     * After this call, you will be able to
     * <code>stop</code> and <code>close</code> the <code>Channel</code>.
     * <p><em>The implementor must start all the <strong>bound</strong> destinations of this <code>Channel</code>.</em></p>
     *
     * @param mode
     * @throws Exception exception logged by the ChannelManager
     * @see Destination#bind(com.tibco.cep.runtime.session.RuleSession)
     * @see com.tibco.cep.runtime.channel.Channel.Destination#start(int)
     * @see #connect()
     * @see #stop()
     * @see #close()
     * @since 2.0
     */
    public void start(int mode) throws Exception;


    /**
     * Stops this channel, so that it cannot receive data through its underlying transport.
     * <p>Only valid on a started channel.
     * After this call, you will be able to
     * <code>start</code> or <code>close</code> the <code>Channel</code>.
     * <p><em>The implementor must stop all the <strong>bound</strong> destinations of this <code>Channel</code>.</em></p>
     *
     * @see Destination#bind(com.tibco.cep.runtime.session.RuleSession)
     * @see com.tibco.cep.runtime.channel.Channel.Destination#stop()
     * @see #init()
     * @see #connect()
     * @see #start(int)
     * @see #close()
     * @since 2.0
     */
    public void stop();


    /**
     * Closes the channel and frees up all its resources.
     * <p>Only valid on a started channel.
     * After this call, you will be able to
     * <code>init</code> the <code>Channel</code>.
     * <p><em>The implementor must close all the destinations of this <code>Channel</code>.</em></p>
     *
     * @see #init()
     * @since 2.0
     */
    public void close() throws Exception;


    /**
     * Returns all the destinations of this <code>Channel</code>
     * in a <code>Map</code> of <code>String</code> URI to <code>Channel.Destination</code>.
     *
     * @return a <code>Map</code> of <code>String</code> URI to <code>Channel.Destination</code>.
     * @since 2.0
     */
    <D extends Channel.Destination> Map<String, D> getDestinations();


    /**
     * Sends an event on the specified DestinationURI, after optionally overriding some of the destination properties.
     * <p>Only valid on a connected <code>Channel</code>.</p>
     *
     * @param event          A <code>SimpleEvent</code>, either instantiated or retrieved from the WorkingMemory.
     * @param destinationURI Either a fully qualified destination name,
     *                       or null to use the default destination of the event.
     * @param overrideData   A String representation of name-value pairs describing destination property values,
     *                       or null if no property value needs overriding.
     *                       The property names are provided by the driver implementor.
     *                       See the <code>drivers.xml</code> file of the specific destination for more information.
     *                       The delimiter for the name-value pair is <code>';'</code>.
     *                       If a value contains a <code>';'</code>, escape it as <code>"\;"</code>.
     * @throws Exception when the event could not be sent.
     * @since 2.0
     */
    public void send(SimpleEvent event, String destinationURI, Map overrideData) throws Exception;
    //TODO what happens if the destination is in another channel? (i.e. channel mismatch)
    //TODO why is this is never used?


    /**
     * A Destination represents an individual endpoint for the underlying transport of a <code>Channel</code>.
     * The notion of Destination is primarily taken from the JMS specification,
     * but could be applied to various facets of a transport.
     * <p>Examples:
     * <ul><li>In a TCP channel a destination could represent a TCP port.</li>
     * <li>In a UDP channel a destination could represent a service.</li>
     * <li>In a SOAP channel a destination could represent a SOAP operation.</li></ul>
     * <p>Implementors are encouraged to extend
     * {@link com.tibco.cep.runtime.channel.impl.AbstractDestination AbstractDestination}.</p>
     *
     * @version 2.0
     * @since 2.0
     */

    /**
     * Suspend the channel and its corresponding destinations
     * @version 2.0
     * @since 2.0
     */
    public void suspend();

    /**
     * Resume the channel for normal operation.
     * @version 2.0
     * @since 2.0
     */

    public void resume();

    /**
     * Tests whether the channel supports synchronous mode of communication
     * or request, and response are decoupled.
     * @return true if chhanel supports decoupled mode of communication like in
     * case of <b>JMS</b>, <b>RV</b>.
     * <p>
     * Returns false for a channel like HTTP.
     * </p>
     */
    public boolean isAsync();

    /**
     * Create a new destination with the specified DestinationConfig.
     * This method is optional for implementors if the Destination is not a BW (BusinessWorks)EventSource.
     * The method is called by a BWEventSource when the same destination is used as multiple
     * Process Starters. For more info see BW Process Starter
     *
     * @param config
     * @return
     * @throws Exception
     */
    public <D extends Destination> D createDestination(DestinationConfig config);


    interface Destination {

        //TODO why no getState? State is accessible from ChannelDestinationStats which seems uselessly indirect.

        /**
         * Returns the fully qualified destination name as specified during design time.
         * <p>For a destination named <code>"Queue"</code> in the channel named <code>"JMS"</code>
         * located in the folder <code>"/Channels"</code>, the value returned would be <code>"/Channels/JMS/Queue"<code>.
         *
         * @return a String
         * @since 2.0
         */
        String getURI();


        /**
         * Adds to this destination a binding with the given <code>RuleSession</code>.
         * While this destination is started, whenever a message is received by this destination, the following things
         * can be done by the programmer:
         * <p>Either</p>
         * <ol>
         * <li>Deserialize the message to a SimpleEvent using the <code>EventSerializer</code> of the Destination.</li>
         * <li>{@link RuleSession#assertObject(Object, boolean) Assert}  the <code>SimpleEvent</code> into the <code>RuleSession</code>.</li>
         * </ol>
         * or
         * <ol>
         * <li>Create an <code>EventContext</code> that represents the message.</li>
         * <li>Pass it to {@link com.tibco.cep.runtime.channel.impl.AbstractDestination#onMessage(com.tibco.cep.runtime.session.RuleSession, com.tibco.cep.runtime.model.event.EventContext) AbstractDestination.newMessage}.
         * </ol>
         *
         * <p>The same destination can be bound to multiple distinct <code>RuleSession</code>.</p>
         * <p>The implementor may choose to create a listener per bind call and associate it to the destination
         * or multiplex it. The threading model is left to the programmer.</p>
         * <p>Understanding the stateful behavior of RuleSession is very important.
         * The programmer must choose his implementation carefully.</p>
         *
         * @param session The RuleSession to bind with this destination.
         * @since 2.0
         */
        void bind(RuleSession session) throws Exception;


        /**
         * Sends an event on this <code>Destination</code>.
         *
         * @param event        the <code>SimpleEvent</code> to send.
         * @param overrideData A <code>Map</code> of <code>String</code> name to <code>String</code> value
         *                     describing destination property values, or null if no property value needs overriding.
         *                     The property names are provided by the driver implementor.
         *                     See the <code>drivers.xml</code> file of the specific destination for more information.
         * @return an int
         * @throws Exception when the event could not be sent
         * @since 2.0
         */
        int send(SimpleEvent event, Map overrideData) throws Exception;
        //TODO make constants out of the various magic numbers returned by this method, then document them.
        //TODO why are return values never used?

        /**
         * Sends an event on the specified destination and waits for a response.
         * @param outevent
         * @param responseEventURI
         * @param serializerClass
         * @param timeout
         * @param overrideData
         * @return Returns the response deserialized as SimpleEvent or raw response Object if serializer not defined.
         * @throws Exception
         */
        Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData) throws Exception;
        
        //SR_Id:1-DGHMLR: Send immediately, in the RTC. Dont post it to Post-RTC
        String sendImmediate(SimpleEvent event, Map overrideData) throws Exception;

        /**
         * Gets the configuration information for this <code>Destination</code>.
         *
         * @return A DestinationConfig
         * @since 2.0
         */
        DestinationConfig getConfig();


        /**
         * Gets the <code>Channel</code> associated to this <code>Destination</code>.
         *
         * @return a Channel
         * @since 2.0
         */
        Channel getChannel();


        /**
         * Gets the <code>EventSerializer<code> used by this destination
         * to serialize events into objects that the underlying transport can use,
         * and deserialize those objects into events.
         *
         * @return the <code>EventSerializer<code> used by this destination.
         * @since 2.0
         */
        EventSerializer getEventSerializer();


        /**
         * Initializes the <code>Destination</code> by allocating necessary resources, creating pools, etc.
         * <p>Only valid on a closed <code>Destination</code>.
         * After this call, you will be able to
         * <code>connect</code> or <code>close</code> this <code>Destination</code>.
         * @throws Exception when initialization failed
         * @see #connect()
         * @see #close()
         * @since 2.0
         */
        public void init() throws Exception;


        /**
         * Connects the <code>Destination</code> to the underlying endpoint.
         * <p>Only valid on an initialized <code>Destination</code>.
         * After this call, you will be able to
         * <code>start</code> or <code>close</code> the <code>Destination</code>.
         * <p><em>Do not start sending or receiving data data here.</em></p>
         * @throws Exception when the Destination could not be connected to the underlying endpoint.
         * @see #init()
         * @see #start(int)
         * @see #close()
         * @since 2.0
         */
        public void connect() throws Exception;


        /**
         * Starts the destination, so that it can receive data from the endpoint.
         * <p>Only valid on a connected <code>Destination</code> which is
         * {@link #bind(com.tibco.cep.runtime.session.RuleSession) bound} to a RuleSession.
         * After this call, you will be able to
         * <code>stop</code> and <code>close</code> the <code>Destination</code>.
         *
         * @param mode
         * @throws Exception when the Destination could not be started.
         * @see #bind(com.tibco.cep.runtime.session.RuleSession)
         * @see #connect()
         * @see #stop()
         * @see #close()
         * @since 2.0
         */
        public void start(int mode) throws Exception;

        /**
         * @param message The message that arrived over the destination (like JMS Message).
         * @return The EventContext wrapping the message.
         */
        EventContext createEventContext(Object message);

        /**
         * Stops this <code>Destination</code>, so that it cannot receive data. It can still send data.
         * <p>Only valid on a <code>Destination</code> that has been started.
         * After this call, you will be able to
         * <code>start</code> or <code>close</code> the <code>Destination</code>.
         *
         * @see #init()
         * @see #connect()
         * @see #start(int)
         * @see #close()
         * @since 2.0
         */
        public void stop();


        /**
         * Closes the Destination and frees up all its resources.
         * <p>After this call, you will be able to
         * <code>init</code> the <code>Destination</code>.
         *
         * @see #init()
         * @since 2.0
         */
        public void close();

        public void suspend();

        public void resume();

        boolean isSuspended();

    }


    /**
     * Represents a state in the lifecycle of a channel.
     * <p>Note that a Channel implementation does not need to pass through all of the available states.</p>
     *
     * @version 2.0
     * @.category public-api
     * @since 2.0
     */
    public static class State {


        /**
         * State of a <code>Channel</code> whose resources are not initialized yet or have been freed by <code>close</code>.
         *
         * @since 2.0
         */
        public static final State UNINITIALIZED = new State("UnInitialize");

        /**
         * State of a <code>Channel</code> whose resources have been initialized,
         * and whose underlying transport is not connected.
         *
         * @since 2.0
         */
        public static final State INITIALIZED = new State("Initialized");

        /**
         * State of a <code>Channel</code> whose underlying transport is connected,
         * and whose destinations either have never been started or have been cleanly stopped with <code>stop</code>.
         *
         * @since 2.0
         */
        public static final State CONNECTED = new State("Connected");

        /**
         *  State of a <code>Channel</code> which has been marked as inactive at designtime to avoid binding.
         *  @since 5.1
         */
        public static final State INACTIVE = new State("Inactive");

        /**
         * State of a <code>Channel</code> whose underlying transport is connected,
         * and whose destinations have been started then stopped otherwise than with <code>stop</code>.
         *
         * @since 2.0
         */
        public static final State DISCONNECTED = new State("Disconnected");

        /**
         * State of a <code>Channel</code> whose underlying transport is connected,
         * and whose destinations are started.
         *
         *
         * @since 2.0
         */
        public static final State STARTED = new State("Started");

        /**
         * State of a <code>Channel</code> whose underlying transport is connected,
         * which stopped accepting inputs, and which has no input data remaining to be processed.
         *
         * @since 2.0
         */
        public static final State STOPPED = new State("Stopped");

        /**
         * State of a <code>Channel</code> whose underlying transport is in an exception state.
         *
         * @since 2.0
         */
        public static final State EXCEPTION = new State("Exception");

        /**
         * State of a <code>Channel</code> whose underlying transport is reconnecting after reaching an exception state.
         *
         * @since 2.0
         */
        public static final State RECONNECTING = new State("Reconnecting");
        

        protected String stateStr;


        /**
         * Constructs a State from a String.
         *
         * @param stateStr a String representing the state
         * @since 2.0
         */
        protected State(String stateStr) {
            this.stateStr = stateStr;
        }


        /**
         * Gets a string representation of the state.
         *
         * @return a String
         * @since 2.0
         */
        public String toString() {
            return stateStr;
        }


        /**
         * Final version of the default <code>equals</code>.
         *
         * @param o an Object
         * @return true iif o == this.
         * @since 2.0
         */
        public final boolean equals(Object o) {
            return super.equals(o);
        }


        /**
         * Final version of the default <code>hashCode</code>.
         *
         * @return an int.
         */
        public final int hashCode() {
            return super.hashCode();
        }

    }

}


