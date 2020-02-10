package com.tibco.cep.runtime.channel.spi;


import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;


/**
 * Service Provider Interface for Channels:
 * implementations of <code>ChannelDriver</code> provide a method for creating instances of <code>Channel</code>
 * for a given driver type.
 * <p>Implementors should take the following steps:
 * <ol>
 * <li>Implement a <code>Channel</code> class and its matching <code>Destination</code> class
 * by extending <code>AbstractChannel</code> and <code>AbstractDestination</code>, respectively.</li>
 * <li>Implement a <code>ChannelDriver</code> class which creates instances of that type of channel.</li>
 * <li>Locate or create a <code>drivers.xml</code> file at the root of the jar file which contains the channel classes.
 * That file must conform to the <code>drivers.xsd</code> schema located in this package.</li>
 * <li>Complete a <code>driver</code> entry in that <code>drivers.xml</code> file.</li>
 * <li>Place the jar in the classpath of the engine.</li>
 * </ol>
 *
 * @version 2.0
 * @see ChannelManager
 * @see Channel
 * @since 2.0
 */
public interface ChannelDriver {


    /**
     * Creates a new <code>Channel</code> for this driver with the given configuration,
     * and registers it in the given <code>ChannelManager</code> under the given URI.
     *
     * @param manager the <code>ChannelManager</code> that will host the new <code>Channel</code>.
     * @param uri     the path to the <code>Channel</code> in the archive, used to identify the <code>Channel</code>.
     * @param config  a <code>ChannelConfig</code> describing how the new <code>Channel</code> instance
     *                should be configured.
     * @return a <code>Channel</code>.
     * @throws Exception if the <code>Channel</code> could not be created.
     * @since 2.0
     */
    public Channel createChannel(ChannelManager manager, String uri, ChannelConfig config) throws Exception;


}
