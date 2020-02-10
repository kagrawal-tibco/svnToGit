package com.tibco.cep.runtime.channel;


import java.util.Properties;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Contains all the configuration information specified at design time for one destination.
 * Please refer to the User Guide regarding the creation of channels and destinations.
 *
 * @version 2.0
 * @see com.tibco.cep.runtime.channel.Channel.Destination
 * @since 2.0
 */
public interface DestinationConfig {


    /**
     * Gets the name of the <code>Destination</code>, which uniquely identifies it within its <code>Channel</code>.
     *
     * @return the String name of Destination
     * @since 2.0
     */
    String getName();


    /**
     * Gets the full path of the Destination.
     *
     * @return full String URI of the destination
     * @since 2.0
     */
    String getURI();


    /**
     * Gets the URI of the event type that is used by default for deserializing incoming messages.
     * <p>This default will be used when the destination cannot find the event type in the message itself using
     * {@link Channel#EVENT_NAME_PROPERTY Channel.EVENT_NAME_PROPERTY} and
     * {@link Channel#EVENT_NAMESPACE_PROPERTY Channel.EVENT_NAMESPACE_PROPERTY_PROPERTY}.
     * <!--TODO Shouldnt those be part of Destination? -->
     *
     * @return an ExpandedName
     * @since 2.0
     */
    ExpandedName getDefaultEventURI();
    // TODO why return an Expanded Name in this public interface, and not a String?


    /**
     * Gets the EventSerializer used in this Destination.
     * <p/>
     *
     * @return an EventSerializer.
     * @since 2.0
     */
    EventSerializer getEventSerializer();
    //TODO why "custom"?


    /**
     * Gets the driver-defined properties of the configured <code>Destination</code>.
     * The list of properties available is defined in the matching <code>drivers.xml</code>.
     *
     * @return a Properties
     * @since 2.0
     */
    Properties getProperties();


    /**
     * Gets the channel configuration which contains this destination configuration.
     *
     * @return
     * @since 2.0
     */
    ChannelConfig getChannelConfig();


    /**
     * @param event an Event whose type will be the only event type allowed for this destination.
     *              If null all event types are allowed.
     *              <!-- TODO shouldnt this be a Class instead? -->
     * @since 2.0
     */
    void setFilter(Event event);
    // TODO how come this is never used?


    /**
     * @return the Event whose type is allowed, or null if all event types are allowed.
     * @since 2.0
     */
    Event getFilter();
    // TODO how come this is never used?


}
