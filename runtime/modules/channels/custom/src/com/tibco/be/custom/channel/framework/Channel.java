package com.tibco.be.custom.channel.framework;

import com.tibco.cep.runtime.channel.Channel.State;

/**
 * @.category public-api
 */
public interface Channel {

	/**
	 * Initializes the <code>Channel</code> by allocating necessary resources,
	 * creating pools, etc.
	 * <p>
	 * Only valid on an uninitialized or closed channel. After this call, you
	 * will be able to <code>connect</code> or <code>close</code> the
	 * <code>Channel</code>.
	 * @throws Exception 
	 * 				Exception when initialization failed
	 *
	 * @see #connect()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	public void init() throws Exception;

	/**
	 * Connects the <code>Channel</code> to the underlying transport so that it
	 * can send outbound messages. This in turn could mean connecting to the
	 * server etc. Once successfully connected, the connections could be added
	 * to the pool.
	 * <p>
	 * Only valid on an initialized channel. After this call, you will be able
	 * to <code>start</code> or <code>close</code> the <code>Channel</code>.
	 * <p>
	 * <em>Do not start receiving data data here.</em>
	 * </p>
	 * <p>
	 * <em>The implementor must connect all the destinations of this <code>Channel</code>
	 * .</em>
	 * </p>
	 *
	 * @throws Exception
	 *             encountered exception
	 * @see com.tibco.be.custom.channel.framework.Destination#connect()
	 * @see #init()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	public void connect() throws Exception;
	
	/**
	 * Starts the channel, so that it can receive data through its underlying
	 * transport.
	 * <p>
	 * Only valid on a connected channel. After this call, you will be able to
	 * <code>stop</code> and <code>close</code> the <code>Channel</code>.
	 * <p>
	 * <em>The implementor must start all the <strong>bound</strong> destinations of this <code>Channel</code>
	 * .</em>
	 * </p>
	 *
	 * @throws Exception
	 *             encountered exception
	 * @see com.tibco.be.custom.channel.framework.Destination#start()
	 * @see #connect()
	 * @see #close()
	 * @.category public-api
	 * @since 5.4
	 */
	public void start() throws Exception;
	
	/**
	 * @.category public-api
	 */
	public void stop() throws Exception;

	/**
	 * Closes the channel and frees up all its resources.
	 * <p>
	 * Only valid on a started channel. After this call, you will be able to
	 * <code>init</code> the <code>Channel</code>.
	 * <p>
	 * <em>The implementor must close all the destinations of this <code>Channel</code>
	 * .</em>
	 * </p>
	 * 
	 * @throws Exception
	 *             encountered exception
	 * @see #init()
	 * @.category public-api
	 * @since 5.4
	 */
	public void close() throws Exception;
	
	/**
	 * Get current {@link State} of this channel.
	 * @.category public-api
	 * @since 5.6
	 */
	public State getState();
}
