package com.tibco.cep.runtime.channel;


import java.util.Collection;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.CommandFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * The <code>ChannelManager</code> manages the lifecycle of, and the access to,
 * each <code>Channel</code> and <code>Destination</code> in the project.
 * It also provides methods for sending an event on a destination.</p>
 * <p>All Destinations are available for send, but are registered with a RuleSession only when
 * configured as an input source.</p>
 * <p>The <code>ChannelManager</code> has no thread related behavior.</p>
 * <!-- TODO what does that mean, specially for sendEvent? -->
 *
 * @version 2.0
 * @see Channel
 * @see com.tibco.cep.runtime.channel.Channel.Destination
 * @since 2.0
 */
public interface ChannelManager {


    public static final int PASSIVE_MODE = 1;
    public static final int ACTIVE_MODE = 2;
    public static final int SUSPEND_MODE = 4;

    /**
     * Gets all the Channels, active or inactive, available from this ChannelManager.
     *
     * @return A collection of Channel.
     * @since 2.0
     */
    Collection getChannels();


    /**
     * Gets a Channel using its its URI.
     * <p>The URI of a <code>Channel</code> is its fully qualified name, for example: <code>"/MyFolder/MyChannel"</code>.</p>
     *
     * @param uri A String URI.
     * @return the <code>Channel</code> found at this URI, or null if nothing was found.
     * @since 2.0
     */
    Channel getChannel(String uri);


    /**
     * Retrieves a Destination given its URI.
     * <p>Example of destination URI: <code>"/MyFolder/MyChannel/MydestinationName"</code>.</p>
     *
     * @param destURI A fully qualified name for the destination.
     * @return the <code>Channel.Destination</code> if found, else null.
     * @since 2.0
     */
    Channel.Destination getDestination(String destURI);


    /**
     * Sends an event on a specified DestinationURI, after optionally overriding some of the destination properties.
     * <p>Only valid on a connected Destination.</p>
     *
     * @param event          A <code>SimpleEvent</code>, either instantiated or retrieved from the WorkingMemory.
     * @param destinationURI Either a fully qualified destination name, or null to use the default destination of the event.
     * @param properties     A String representation of name-value pairs describing destination property values,
     *                       or null if no property value needs overriding.
     *                       The property names are provided by the driver implementor.
     *                       See the <code>drivers.xml</code> file of the specific destination for more information.
     *                       The delimiter for the name-value pair is <code>';'</code>.
     *                       If a value contains a <code>';'</code>, escape it as <code>"\;"</code>.
     * @return The SimpleEvent which was successfully sent.
     * @throws Exception when the event could not be sent
     * @since 2.0
     */
    SimpleEvent sendEvent(SimpleEvent event, String destinationURI, String properties) throws Exception;
    
    /**
     * Sends an event on a specified destinationPath and waits for a response. Wait duration is identified by timeout param.
     * @param outevent
     * @param responseEventURI
     * @param outgoingDestinationPath
     * @param timeout
     * @param properties
     * @return
     * @throws Exception
     * @since 5.2.1
     */
    Object requestEvent(SimpleEvent outevent, String responseEventURI, String outgoingDestinationPath, long timeout, String properties) throws Exception;
    
    //SR_Id:1-DGHMLR: Send immediately, in the RTC. Dont post it to Post-RTC
    String sendEventImmediate(SimpleEvent event, String destinationURI, String properties) throws Exception;



    /**
     * Returns the RuleServiceProvider associated to this ChannelManager.
     *
     * @return a RuleServiceProvider
     * @since 2.0
     */
    RuleServiceProvider getRuleServiceProvider();


    /**
     * Instructs all input destinations to start or stop listening.
     *
     * @param mode <code>true</code> iif the input destinations should be active (i.e. listening).
     * @throws Exception
     * @since 2.0
     */
    void setMode(int mode) throws Exception;



    /**
     * Gets true iif destinations should be listening.
     *
     * @since 2.0
     */
    int getMode();


    /**
     * Connects all the channels known to this <code>ChannelManager</code>.
     *
     * @throws Exception if a channel could not be connected.
     * @see com.tibco.cep.runtime.channel.Channel#connect()
     * @since 2.0
     */
    void connectChannels() throws Exception;

    /**
     * Init all the channels known to this <code>ChannelManager</code>.
     *
     * @throws Exception when a channel could not be initializdd.
     * @since 3.0.2
     */
    void init() throws Exception;



    /**
     * Starts all the channels known to this <code>ChannelManager</code>.
     *
     * @throws Exception when a channel could not be started.
     * @see Channel#start(int)
     * @since 2.0
     */
    void startChannels(int initialMode) throws Exception;


    /**
     * Starts the specified channel in the mode specified.
     *
     * @param channelUri a string identifying the channel to be started.
     * @param mode <code>true</code> iif the input destination should be active | suspended | passive(i.e. listening).
     * @throws Exception if the channed could not be started.
     * @see Channel#start(int)
     * @since 2.0
     */
    void startChannel(String channelUri, int mode) throws Exception;
    //TODO why the boolean parameter? the activemode should be determined internally by the ChannelManager  

    /**
     * Stops all the channels known to this <code>ChannelManager</code>.
     *
     * @since 2.0
     */
    void stopChannels();


    /**
     * Stops the Channel identified by the given URI.
     *
     * @param channelURI the String URI that identifies the channel to stop.
     * @since 2.0
     */
    void stopChannel(String channelURI);


    /**
     * Shuts down this <code>ChannelManager</code>.
     *
     * @since 2.0
     */
    void shutdown();


    /**
     * Suspend All the channels temporarily. The listener are active, but the callback thread is blocked.
     */
    void suspendChannels();


    /**
     * Resume the channel
     */
    void resumeChannels();


    CommandFactory getCommandFactory();

}//class

