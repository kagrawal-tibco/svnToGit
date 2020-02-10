package com.tibco.cep.runtime.channel.impl;


import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;


/**
 * Base for all implementations of <code>Channel.Destination<code>.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractDestination<A extends AbstractChannel> implements Channel.Destination {


    /**
     * Stores the configuration data for this Destination.
     *
     * @since 2.0
     */
    protected DestinationConfig config;

    /**
     * Reference to the Channel that this Destination is part of.
     *
     * @since 2.0
     */
    protected A channel;

    /**
     * Stores the serializer used to serialize events into objects that the underlying transport can use,
     * and deserialize those objects into events.
     *
     * @since 2.0
     */
    protected EventSerializer serializer;

    /**
     * Stores the <code>ChannelDestinationStats</code> which keeps runtime stats on the Destination.
     *
     * @since 2.0
     */
    protected ChannelDestinationStats stats;

    /**
     * Stores the name of the Destination.
     *
     * @since 2.0
     */
    protected String destinationName;

    /**
     * Stores the Set of <code>RuleSession</code> that are bound to this <code>Destination</code>.
     *
     * @since 2.0
     */
    protected Set boundRuleSessions;


    protected boolean suspended = false;

    protected final Object syncObject = new Object();

    protected String uri;

    protected boolean shuttingDown = false;

    /**
     * Constructs a destination for the given <code>AbstractChannel</code> using the given <code>DestinationConfig</code>.
     *
     * @param channel the <code>AbstractChannel</code> that will hold this <code>Destination</code>.
     * @param config  the <code>DestinationConfig</code> that specifies how to configure this <code>Destination</code>.
     * @since 2.0
     */
    public AbstractDestination(A channel, DestinationConfig config) {
        this.channel = channel;
        this.config = config;
        this.serializer = config.getEventSerializer();
        //Serializer will be null for local channel
        if (serializer != null) {
            serializer.init(channel.getChannelManager(), config);
        }
        this.stats = new ChannelDestinationStats();
        this.boundRuleSessions = new HashSet();
        this.uri = config.getURI();
    }


    /**
     * Gets the value of a destination property after substituting all the variables it may contain.
     *
     * @param propertyName               String name of the property.
     *                                   Refer to the matching <code>drivers.xml</code> file for a list of available properties.
     * @param replaceSpaceWithUnderScore if true, any spaces in the value will be converted to an underscore.
     * @return If the property is found, the String value of the property after substitution, else an empty String.
     * @since 2.0
     */
    public String getSubstitutedPropertyValue(String propertyName, boolean replaceSpaceWithUnderScore) {
        // TODO Can the GVs, engine name, or URIs change during runtime? If not, substitutions should be done once in the DeployedProject, not here.

        String name = channel.getGlobalVariables().substituteVariables(config.getProperties().getProperty(propertyName)).toString();
        name = name.replaceAll("%%EngineName%%", channel.getChannelManager().getRuleServiceProvider().getName());
        name = name.replaceAll("%%ChannelURI%%", channel.getURI());
        name = name.replaceAll("%%ChannelName%%", channel.getName());
        name = name.replaceAll("%%DestinationName%%", this.getName());
        name = name.replaceAll("%%DestinationURI%%", this.getURI());

        if (replaceSpaceWithUnderScore) {
            name = name.replace(' ', '_');
        }

        return name;
    }


    /**
     * Returns the name of the <code>Destination</code>, which uniquely identifies it whitin its <code>Channel</code>.
     *
     * @return a String
     * @since 2.0
     */
    public String getName() {
        return config.getName();
    }


    public String getURI() {
        return uri;
    }


    public void bind(RuleSession session) throws Exception {
        boundRuleSessions.add(session);
        shuttingDown = false;
    }

    public Set getBoundedRuleSessions() {
        return boundRuleSessions;
    }
    /**
     * TODO move to interface?
     * Returns all the RuleSessions that are bound to this Destination.
     *
     * @return a Collection of <code>RuleSession</code>
     * @since 2.0
     */
    public Collection getBoundSessions() {
        return boundRuleSessions;
    }


    public void unbind(RuleSession session) throws Exception {
        if (boundRuleSessions.contains(session)) {
            destroy(session);
            boundRuleSessions.remove(session);
        }
    }

    public void postUnbind(RuleSession session) throws Exception {
    	postDestroy(session);
    }

    protected abstract void destroy(RuleSession session) throws Exception;

    protected void postDestroy(RuleSession session) throws Exception {
    	
    }

    /**
     * @param event
     * @param overrideData
     * @return
     * @throws Exception
     * @since 2.0
     */
    public int send(SimpleEvent event, Map overrideData) throws Exception {
        stats.addEventOut();
        return 0;
    }

    //added for the sake of returning the JMSMessageID
    public String sendImmediate(SimpleEvent event, Map overrideData) throws Exception {
        stats.addEventOut();
        return null;
    }


    /**
     * Called upon receiving a new message from the underlying transport,
     * this method deserializes the message into a SimpleEvent,
     * and transmits that event to the RuleSession,
     * either through the destination preprocessor
     * or by directly asserting the event if there is no preprocessor.
     *
     * @param session the RuleSession to update.
     * @param ctx     the EventContext that contains all information about the message received.
     * @throws Exception if the message was not successfully processed.
     * @since 2.0
     */
    public void onMessage(RuleSession session, EventContext ctx) throws Exception {

        this.onMessage(session, null, ctx);
    }

    public void onMessage(RuleSession session, SimpleEvent event) throws Exception {
        this.onMessage(session, event, null);

    }

    private void onMessage(RuleSession session, SimpleEvent event,  EventContext ctx) throws Exception {
        // suspended is an artifical state.
        if (suspended) {
            synchronized(syncObject) {
                while (this.suspended) {
                    syncObject.wait(1000l);

	            	// do not let msg pass through when engine is shutting down
	            	if (shuttingDown) {
	                	return;
	              	}
	        	}
            }
        }

        session.getTaskController().processEvent(this, event, ctx);
        stats.addEventIn();
    }


    public A getChannel() {
        return channel;
    }


    public EventSerializer getEventSerializer() {
        return serializer;
    }


    public DestinationConfig getConfig() {
        return config;
    }


    public void init() throws Exception {

    }


    public void start(int mode) throws Exception {

    }

    /**
     * @param message The message that arrived over the destination (like JMS Message).
     * @throws UnsupportedOperationException unless over ridden.
     */
    @Override
    public AbstractEventContext createEventContext(Object message) {
        throw new UnsupportedOperationException();
    }

    public void stop() {

    }


    public void close() {

    }


    /**
     * Gets the <code>ChannelDestinationStats</code> which keeps runtime stats on this Destination.
     *
     * @return a ChannelDestinationStats
     * @since 2.0
     */
    public ChannelDestinationStats getStats() {
        return stats;
    }


    /**
     * Gets the event filter associated to the given RuleSession in this destination.
     *
     * @param session a RuleSession.
     * @return the Event whose type is allowed, or null if all event types are allowed.
     * @since 2.0
     */
    protected Event getEventFilter(RuleSession session) {
        //TODO - find out and document why all this code is there instead of "return this.config.getFilter();"...?
        RuleSessionConfig config = session.getConfig();
        RuleSessionConfig.InputDestinationConfig dcs[] = config.getInputDestinations();
        if (dcs == null) {
            return null;
        }
        for (int i = 0; i < dcs.length; i++) {
            RuleSessionConfig.InputDestinationConfig dc = dcs[i];
            if (dc.getURI().equals(getURI())) {
                return dc.getFilter();
            }
        }
        return null;
    }

    public  void suspend() {
        synchronized(syncObject) {
            AbstractDestination.this.suspended = true;
        }
    }

    protected boolean userControlled = false;
    public void suspendEx() {
        AbstractDestination.this.userControlled = true;
        suspend();
    }

    public void resume() {
        if (!AbstractDestination.this.userControlled) {
            synchronized (syncObject) {
                AbstractDestination.this.suspended = false;
                syncObject.notifyAll();
            }
        }

    }

    public boolean isSuspended() {
        synchronized(syncObject) {
            return this.suspended;
        }
    }

    public void resumeEx() {
         AbstractDestination.this.userControlled = false;
        resume();
    }

    public void setShuttingDown() {
        AbstractDestination.this.shuttingDown = true;
    }
}
