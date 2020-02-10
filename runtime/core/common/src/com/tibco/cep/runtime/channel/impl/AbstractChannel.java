package com.tibco.cep.runtime.channel.impl;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.event.CommandEvent;
import com.tibco.cep.runtime.model.event.CommandListener;
import com.tibco.cep.util.ResourceManager;


/**
 * Base for all implementations of <code>Channel<code>.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractChannel<D extends Channel.Destination> implements Channel {


    /**
     * Stores the location of the channel in its containing project.
     *
     * @since 2.0
     */
    protected String uri;

    /**
     * Stores the configuration of this Channel.
     *
     * @since 2.0
     */
    protected ChannelConfig channelConfig;

    /**
     * References the <code>ChannelManager</code> that manages this <code>Channel</code>.
     *
     * @since 2.0
     */
    protected ChannelManager channelManager;

    /**
     * Stores all the <code>Destination</code> instances for this <code>Channel</code>.
     *
     * @since 2.0
     */
    protected HashMap<String, D> destinations;

    private State state;
    private Logger logger;


    static {
        ResourceManager manager = ResourceManager.getInstance();
        manager.addResourceBundle("com.tibco.cep.driver.messages", manager.getDefaultLocale());
    }


    /**
     * Protected constructor used by the <code>ChannelManager</code>.
     *
     * @param manager the <code>ChannelManager</code> that manages the constructed <code>Channel<code>.
     * @param uri     the String path of the created <code>Channel</code> in its project.
     * @param config  the complete configuration of the created <code>Channel</code>.
     * @since 2.0
     */
    protected AbstractChannel(ChannelManager manager, String uri, ChannelConfig config) {
        this.channelManager = manager;
        this.logger = this.channelManager.getRuleServiceProvider().getLogger(this.getClass());
        this.uri = uri;
        this.channelConfig = config;
        destinations = new HashMap<String, D>();
    }


    /**
     * Chooses which <code>ChannelManager</code> manages this <code>Channel</code>.
     *
     * @param manager the <code>ChannelManager</code> that manages this <code>Channel</code>
     * @since 2.0
     */
    protected void setChannelManager(ChannelManager manager) {
        this.channelManager = manager;
    }
    //TODO this looks dangerous, and nobody is using it...


    /**
     * Gets the name of this <code>Channel</code>.
     *
     * @return a String
     * @since 2.0
     */
    public String getName() {
        return channelConfig.getName();
    }


    public String getURI() {
        return uri;
    }


    public Map<String, D> getDestinations() {
        return destinations;
    }


    /**
     * Adds the given <code>Destination</code> to this channel.
     * Used when building the <code>Channel</code>.
     *
     * @param dest a Destination.
     * @since 2.0
     */
    protected void addDestination(D dest) {
        this.destinations.put(dest.getURI(), dest);
    }


    /**
     * Utility method that gets the <code>Logger</code> of the <code>RuleServiceProvider</code>
     * that contains this <code>Channel</code>.
     *
     * @return the <code>Logger</code> of the containing <code>RuleServiceProvider</code>.
     * @since 2.0
     */
    public Logger getLogger() {
        return this.logger;
    }


    /**
     * Utility method that gets the properties of the <code>RuleServiceProvider</code>
     * that contains this <code>Channel</code>.
     *
     * @return the <code>Properties</code> of the containing <code>RuleServiceProvider</code>.
     * @since 2.0
     */
    public Properties getServiceProviderProperties() {
        return channelManager.getRuleServiceProvider().getProperties();
    }


    /**
     * Utility method that gets the global variables of the <code>RuleServiceProvider</code>
     * that contains this <code>Channel</code>.
     *
     * @return the <code>GlobalVariables</code> of the containing <code>RuleServiceProvider</code>.
     * @since 2.0
     */
    public GlobalVariables getGlobalVariables() {
        return channelManager.getRuleServiceProvider().getGlobalVariables();
    }


    /**
     * Gets the <code>ChannelManager</code> that manages this <code>Channel</code>.
     *
     * @return the <code>ChannelManager</code> that manages this <code>Channel</code>.
     * @since 2.0
     */
    public ChannelManager getChannelManager() {
        return channelManager;
    }


    public State getState() {
        return state;
    }


    /**
     * Sets the current state of this <code>Channel</code>.
     *
     * @param s a State
     * @since 2.0
     */
    protected void setState(State s) {
        state = s;
    }


    //TODO: What is this? How is it different from close() or stop()? Why is it not part of the interface?
    public void shutdown() throws Exception {
        state = State.UNINITIALIZED;
    }


    public void init() throws Exception {
        state = State.INITIALIZED;
    }


    public void start(int mode) throws Exception {
        state = State.STARTED;
    }

    public void stop() {
        state = State.STOPPED;
    }


    public void suspend() {
        Iterator<D> r = destinations.values().iterator();
        while (r.hasNext()) {
            D dest = r.next();
            dest.suspend();
        }
    }

    public void resume() {
    	if (state != State.STOPPED) {
    		Iterator<D> r = destinations.values().iterator();
    		while (r.hasNext()) {
    			D dest = r.next();
    			dest.resume();
    		}
    	}
    }


    public void deregisterCommand(String key) throws Exception{
        throw new UnsupportedOperationException("openCommand Not supported by this channel");
    }

    public void sendCommand(String key, CommandEvent cmd, boolean oneToMany) throws Exception {
        throw new UnsupportedOperationException("openCommand Not supported by this channel");
    }

    public boolean supportsCommand() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerCommand(String key, CommandListener commandListener) throws Exception {
        throw new UnsupportedOperationException("openCommand Not supported by this channel");
    }

}
