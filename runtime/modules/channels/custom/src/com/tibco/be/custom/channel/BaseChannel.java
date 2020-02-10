package com.tibco.be.custom.channel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.custom.channel.framework.Channel;
import com.tibco.be.custom.channel.framework.CustomChannelConfig;
import com.tibco.be.custom.channel.framework.Destination;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.Channel.State;
import com.tibco.cep.runtime.util.GvCommonUtils;

/**
 * A <code>BaseChannel</code> represents a channel or a transport to which BE can connect, and which can receive and send BE
 * {@link Event Event} objects.
 * <p>Each {@link BaseChannel Channel} instance has a set of <code>BaseDestination</code>, each of which
 * represent an end-points that the transport can use.</p>
 * 
 * The execution sequence in a Channel's lifecycle is as follows : 
 * {@link #init() init()} -&gt; {@link #connect() connect()} -&gt; {@link #start() start()} -&gt; {@link #close() close()} 
 * <p>Users must take care of this sequence while providing the Channel's implementation  
 * 
 * <p>{@link #connect() connect()} - Default implementation connects to all of the underlying destinations 
 * <p>{@link #start() start()} - Default implementation starts all of the underlying destinations 
 * <p>{@link #close() close()} - Default implementation closes all of the underlying destinations
 * 
 * <p>If needed user can override these methods and provide own implementation
 * @.category public-api
 * @since 5.4
 */
public abstract class BaseChannel implements Channel {
	
	protected State state;
	
	protected Object ruleServiceProvider;
	
	/**
     * Stores the configuration of this Channel.
     * @.category public-api
     * @since 5.4
     */
	protected CustomChannelConfig config;
	
	/**
     * Stores the location of the channel in its containing project.
     * @.category public-api
     * @since 5.4
     */
	protected String uri;
	
	/**
     * Stores all the {@link BaseDestination Destination} instances for this {@link BaseChannel Channel}.
     * @.category public-api
     * @since 5.4
     */
	protected Map<String, BaseDestination> destinations=new HashMap<String,BaseDestination>();
    
	
    public BaseChannel() {
    }
    
    /**
     * A life cycle method used by the framework to initialize the channel.
     * 
     * This default implementation sequentially initializes each of its Destinations by invoking <code>Destination.init()</code> 
     * User extensions should override this method to perform channel specific initializations.
     * If overridden, it the users responsibility to connect on each of its destinations. The overridden method can use <code>super.connect()</code> to do that.
     * <code>super.init()</code> in order to initialize its destinations.
     *
     * @see #connect()
     * @see #close()
     * @since 5.4
     * @.category public-api
     * @throws Exception : propagates the exception thrown by any of its destination's init() methods
     */
    @Override
	public void init() throws Exception {
    	for (BaseDestination d : destinations.values()) {
    		d.init();
    	}
    	setState(State.INITIALIZED);
    }
	
	/**
     * A life cycle method used by the framework to connect the channels's destinations to its transport.
     * This default implementation will sequentially invoke <code>Destination.connect()</code> on each of its destinations.
     * If overridden, it the users responsibility to invoke connect on each of its destinations. The overridden method can use <code>super.connect()</code> to do that.
     * 
     * @throws Exception encountered while invoking connect on each of its destinations.
     * @see com.tibco.be.custom.channel.BaseDestination#connect()
     * @see #init()
     * @see #close()
     * @.category public-api
     * @since 5.4
     */
	@Override
	public void connect() throws Exception {
		final Iterator i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((BaseDestination) i.next()).connect();
		}
		setState(State.CONNECTED);
		this.getLogger().log(Level.INFO, "Channel Connected : "+getUri());
	}

	/**
     * A life cycle method used by the framework to start receiving messages from its destinations. 
     * This default implementation will sequentially invoke <code>Destination.start()</code> on each of its destinations.
     * If overridden, it is the users responsibility to start all its destinations. The overridden method can use <code>super.start()</code> to do that.
     *
     * @throws Exception encountered exception 
     * @see com.tibco.be.custom.channel.BaseDestination#start()
     * @see #connect()
     * @see #close()
     * @.category public-api
     * @since 5.4
     */
	@Override
	public void start() throws Exception {
		final Iterator<BaseDestination> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			BaseDestination dest = i.next();
			dest.start();
		}
		setState(State.STARTED);
		this.getLogger().log(Level.INFO, "Channel Started : " + getUri());
	}
	
	@Override
	public void stop() {
		final Iterator<BaseDestination> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			BaseDestination dest = i.next();
			dest.suspend();
		}
		setState(State.STOPPED);
		this.getLogger().log(Level.INFO, "Channel Stopped : " + getUri());
	}
	
	
	 /**
     * A life cycle method used by the framework to close the channel and its associated resources.
     * This default implementation will sequentially invoke <code>Destination.cose()</code> on each of its destinations.
     * If overridden, it is the users responsibility to start all its destinations. The overridden method can use <code>super.close()</code> to do that.
     * 
     * @throws Exception encountered exception
     * @see #init()
     * @.category public-api
     * @since 5.4
     */
	@Override
	public void close() throws Exception {
		final Iterator<BaseDestination> i = getDestinations().values().iterator();
		while (i.hasNext()) {
			final BaseDestination dest = i.next();
			dest.close();
		}
		setState(State.DISCONNECTED);
		this.getLogger().log(Level.INFO, "Channel Closed : "+getUri());
	}
	
    /**
     * A life cycle method used by the framework to suspend the channel
     * This default implementation will sequentially invoke <code>Destination.suspend()</code> on each of its destinations.
     * If overridden, it is the users responsibility to suspend all its destinations. The overridden method can use <code>super.suspend()</code> to do that.
     * @.category public-api
     * @since 5.4
     */
    public void suspend() {
		Iterator<BaseDestination> r = destinations.values().iterator();
		while (r.hasNext()) {
			Destination dest = r.next();
			dest.suspend();
		}
		this.getLogger().log(Level.INFO, "Channel Suspended : "+getUri());
	}
    
    /**
     * A life cycle method used by the framework to resume the channel 
     * This default implementation will sequentially invoke <code>Destination.resume()</code> on each of its destinations.
     * If overridden, it is the users responsibility to resume all its destinations. The overridden method can use <code>super.resume()</code> to do that.
     * @.category public-api
     * @since 5.4
     */
    public void resume() {
		Iterator<BaseDestination> r = destinations.values().iterator();
		while (r.hasNext()) {
			BaseDestination dest = r.next();
			dest.resume();
		}
		this.getLogger().log(Level.INFO, "Channel Resumed : "+getUri());
	}

    /**
     * Gets the full path of the {@link BaseChannel Channel} in the project.
     *
     * @return a String URI.
     * @.category public-api
     * @since 5.4
     */
    public String getUri() {
		return config.getURI();
	}
    
    /**
     * Get all the destinations of this {@link BaseChannel Channel}
     * 
     * @return a <code>Map</code> with the <code>String</code>URI of the Destination as its key and the associated <code>Destination</code> as its value
     * @.category public-api
     * @since 5.4
    */
    public Map<String, BaseDestination> getDestinations() {
		return destinations;
	}

    /**
     * The framework uses this method to set the Channels destination objects
     * 
     * @param destinations map of destination uri versus destination
     * @.category public-api
     */
    final public void setDestinations(Map<String, BaseDestination> destinations) {
		this.destinations = destinations;
	}
    
    /**
     * Utility method that gets the value of the Global Variable as specified in the <code>BE Project</code>
     * that contains this {@link BaseChannel Channel}.
     * 
     * @param name name which identifies the Global Variable
     * @return the <code>GlobalVariables</code> of the containing <code>BE Project</code>.
     * @.category public-api
     * @since 5.4
     */
    public Object getGlobalVariableValue(String name) {
    	if (GvCommonUtils.isGlobalVar(name)) {
    		GlobalVariableDescriptor variable = (config.getGv()).getVariable(GvCommonUtils.stripGvMarkers(name));
	    	return getGlobalVarAsType(config.getGv(), variable);
    	} else {
    		return name;
    	}
	}
    
    private Object getGlobalVarAsType(GlobalVariables vars,
			GlobalVariableDescriptor variable) {
    	String gvTypeName = variable.getType();
        if (gvTypeName.equalsIgnoreCase("String")) {
            return vars.getVariableAsString(variable.getFullName(), "");
        }
        if (gvTypeName.equalsIgnoreCase("Integer")) {
            return vars.getVariableAsInt(variable.getFullName(), 0);
        }
        if (gvTypeName.equalsIgnoreCase("Boolean")) {
            return vars.getVariableAsBoolean(variable.getFullName(), false);
        }
        if (gvTypeName.equalsIgnoreCase("Password")) {
            return vars.getVariableAsString(variable.getFullName(), ""); 
        }
        return vars.getVariableAsString(variable.getFullName(), "");
	}
    
    /**
     * Gets BE runtime properties for use in the user channel.
     * 
     * 
     * @return the runtime <code>Properties</code> of the BE application
     * @.category public-api
     * @since 5.4
     */
    public Properties getBeProperties() {
    	return config.getBeProperties();
	}
    
    /**
     * Gets the channel properties as defined in its <code>drivers.xml</code> for use in the user channel.
     *
     * @return the Channel properties.
     * @.category public-api
     * @since 5.4
     */
    public Properties getChannelProperties(){
    	return config.getProperties();
    }
    
    /**
	 * for internal use
	 * @return context
	 */
	public final Object getRuleServiceProvider() {
		return ruleServiceProvider;
	}
	
	/**
	 * for internal use
	 * @param context context
	 */
	public final void setRuleServiceProvider(Object ruleServiceProvider) {
		this.ruleServiceProvider = ruleServiceProvider;
	}
    
    /**
     * Gets the runtime Logger instance for use in the user channel.
     *
     * @return the <code>Logger</code> of the containing <code>BE Project</code>.
     * @.category public-api
     * @since 5.4
     */
    public Logger getLogger() {
    	return config.getLogger();
	}
    
    /**
	 *  Internal use
	 *  
	 *  @param channelConfig ChannelConfig
	 */
    final public void setConfig(CustomChannelConfig channelConfig) {
		this.config=channelConfig;
	}
    
    public State getState() {
    	return this.state;
    }
    
    protected void setState(State state) {
    	this.state = state;
    }
}
