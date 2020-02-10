package com.tibco.be.custom.channel;

import com.tibco.be.custom.channel.framework.CustomChannel;
import com.tibco.be.custom.channel.framework.CustomChannelConfig;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.repo.BEProject;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;

/**
 * User defined or custom channels should extend this class. It acts as an entry point for the user defined channel into BE. The fully qualified name of this extended class has to be specified in <code>drivers.xml</code>
 * It acts as a provider for user defined <code>BaseChannel</code> and <code>BaseDestination</code> instances.
 *@.category public-api
 * @since 5.4
 */
public abstract class BaseDriver implements ChannelDriver {
	
	/**
	 * Gets user defined instance of {@link BaseChannel BaseChannel}
	 * which provides the implementations for the Channel's functionality
	 * The framework calls this method once during startup
	 * The user defined BaseChannel should provide an empty argument constructor
	 * 
	 * @return instance of {@link BaseChannel BaseChannel}
	 * @.category public-api
	 * @since 5.4
	 */
	public abstract BaseChannel getChannel();

	/**
	 * Gets user defined instance of {@link BaseDestination
	 * BaseDestination} which provides implementations for the Destination's
	 * functionality
	 * The framework calls this method once during startup
	 * The user defined BaseChannel should provide an empty argument constructor
	 *
	 * @return instance of {@link BaseDestination BaseDestination}
	 * @.category public-api
	 * @since 5.4
	 */
	public abstract BaseDestination getDestination();
	
	/**
	 * Internal use
	 * 
	 */

	@Override
	final public Channel createChannel(ChannelManager manager, String uri, ChannelConfig config) throws Exception {

		final BEProject project = (BEProject) manager.getRuleServiceProvider().getProject();
		CustomChannelConfig channelConfig = new CustomChannelConfig(config, project.getGlobalVariables(),
				config.getProperties(), (BEProperties) manager.getRuleServiceProvider().getProperties(),
				manager.getRuleServiceProvider().getLogger(this.getClass()));

		// Load User channel Class
		BaseChannel userChannel = getChannel();
		return new CustomChannel(manager, uri, channelConfig, userChannel, this);
	}


}
