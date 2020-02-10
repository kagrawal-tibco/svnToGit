package com.tibco.cep.driver.jms;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigConfig;
import com.tibco.be.util.config.cdd.LoadBalancerPairConfigsConfig;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.JSONPayloadDestination;
import com.tibco.cep.runtime.channel.Channel.State;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;
import com.tibco.cep.util.RuleTriggerManager;
import com.tibco.xml.data.primitive.ExpandedName;

import javax.jms.*;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class JMSDestination
        extends AbstractDestination<BaseJMSChannel> implements JSONPayloadDestination {

    private boolean isJSONPayload;
    private Boolean isMq;
    private final List<JmsReceiver> listeners = new ArrayList<JmsReceiver>();
    final Logger logger;
    private final ConcurrentHashMap<String, Destination> nameToDestinationFromJndi
            = new ConcurrentHashMap<>();
    private JmsSessionContextProvider queueSessionContextProvider;
    private ExecutorService sessionThreadPool;
    private final Object suspensionLock = new Object();
    private JmsSessionContextProvider topicSessionContextProvider;
    private final boolean ack, oneJmsSessionPerMessage, transacted;
    
    public static final String PROP_SESSION_RECOVER_ON_ACK_FAILURE = "be.channel.jms.session.recover.onAckFailure";

    public JMSDestination(
            BaseJMSChannel channel,
            DestinationConfig config) {

        super(channel, new JmsDestinationConfig(channel, config));
        this.logger = channel.getLogger();
        
        transacted = this.getConfig().isTransacted();
        if (transacted) {
            this.ack = false;
            this.oneJmsSessionPerMessage = true;
        } 
        else {
        	//Fix for BE-21917 : Duplicate Durable error. If the destination is a Topic (Durable or not), only one consumer is allowed, hence disable multiple sessions
            boolean ojspmOverride = getConfig().isQueue();
            
            switch (this.getConfig().getJmsAckMode()) {
                case Session.AUTO_ACKNOWLEDGE:
                case EmsHelper.NO_ACKNOWLEDGE:
                    this.ack = false;
                    this.oneJmsSessionPerMessage = false;
                    break;
                case EmsHelper.EXPLICIT_CLIENT_ACKNOWLEDGE:
                case EmsHelper.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE:
                    this.ack = true;
                    this.oneJmsSessionPerMessage = false;
                    break;
                case Session.CLIENT_ACKNOWLEDGE:
                case Session.DUPS_OK_ACKNOWLEDGE:
                default:
                    this.ack = true;
                    boolean enable = Boolean.parseBoolean(getConfig().getProperties().getProperty(
                    		SystemProperty.ENABLE_ONE_JMS_SESSION_PER_MESSAGE.getPropertyName(), "false")); 
                    this.oneJmsSessionPerMessage =  enable && ojspmOverride;
                    break;
            }
        }
        
        Properties configProperties = config.getProperties();
        isJSONPayload = Boolean.parseBoolean(configProperties.getProperty("IsJSONPayload"));
    }


    public void bind(
            RuleSession ruleSession)
            throws Exception {

        try {

            if (!this.getBoundSessions().contains(ruleSession)) {
                final JMSInterceptor interceptor = this.createOptionalInterceptor(ruleSession);

                if (sessionThreadPool != null) {
                    final JmsReceiver listener = new JmsReceiver(this, ruleSession, interceptor,
                            this.ack, this.oneJmsSessionPerMessage);
                    this.listeners.add(listener);
                    this.sessionThreadPool.submit(listener);
                }
            }

            super.bind(ruleSession);
        }
        catch (Exception e) {
            this.logger.log(Level.ERROR, e,
                    "Failed to bind destination %s with rule session '%s'.",
                    this.getURI(),
                    ruleSession.getName());
        }
    }


    public void close() {

        this.stop();

        for (final JmsReceiver receiver : this.listeners) {
            receiver.destroy();
        }
        this.listeners.clear();

        if (null != this.sessionThreadPool) {
            try {
                this.sessionThreadPool.shutdown();
                this.sessionThreadPool.awaitTermination(10, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                this.sessionThreadPool.shutdownNow();
            }
            finally {
                this.sessionThreadPool = null;
            }
        }
    }


    public void connect()
            throws Exception {

        Connection connection = this.channel.getQueueConnection();
        if (null != connection && this.getConfig().isQueue()) {
            this.queueSessionContextProvider = new JmsSessionContextProvider(this, connection);
        }

        connection = this.channel.getTopicConnection();
        if (null != connection && !this.getConfig().isQueue()) {
            this.topicSessionContextProvider = new JmsSessionContextProvider(this, connection);
        }

        this.sessionThreadPool = Executors.newCachedThreadPool(new DefaultThreadFactory(this.getURI()));
    }


    @Override
    public JmsMessageContext createEventContext(
            Object message) {
        try {
            return new JmsMessageContext(this, (Message) message, this.getCurrentSessionContext(), this.ack, this.oneJmsSessionPerMessage, sessionRecoverOnAckFailure());
        }
        catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean sessionRecoverOnAckFailure() {
    	return Boolean.parseBoolean(this.channel.getChannelManager().getRuleServiceProvider().getProperties().getProperty(PROP_SESSION_RECOVER_ON_ACK_FAILURE, "true"));
    }


    //TODO: ?
    private JMSInterceptor createOptionalInterceptor(RuleSession ruleSession) {
        String interceptString = null;
        String channelName = getURI();

        ClusterConfig cc = (ClusterConfig) ruleSession.getRuleServiceProvider().getProperties()
                .get(SystemProperty.CLUSTER_CONFIG.getPropertyName());
        LoadBalancerPairConfigsConfig pairConfigs = cc.getLoadBalancerConfigs().getLoadBalancerPairConfigs();

        LoadBalancerPairConfigConfig selectedLbPairConfig = null;

        for (LoadBalancerPairConfigConfig pairConfig : pairConfigs.getLoadBalancerPairConfigs()) {
            String cddChannelName = CddTools.getValueFromMixed(pairConfig.getChannel());
            if (channelName.equals(cddChannelName)) {
                String cddReceiverName = CddTools.getValueFromMixed(pairConfig.getReceiver());
                String cddRouterName = CddTools.getValueFromMixed(pairConfig.getRouter());

                if (cddRouterName.equals(ruleSession.getName())) {
                    interceptString = "com.tibco.cep.loadbalancer.impl.server.integ.ForwardingJmsDestination";

                    selectedLbPairConfig = pairConfig;

                    break;
                }
                else if (cddReceiverName.equals(ruleSession.getName())) {
                    interceptString = "com.tibco.cep.loadbalancer.impl.client.integ.PseudoJmsDestination";

                    selectedLbPairConfig = pairConfig;

                    break;
                }
            }
        }

        //Fallback to system property override.
        if (interceptString == null) {
            String interceptFlag =
                    "agent." + ruleSession.getName() + ".destination." + getConfig()
                            .getURI() + ".interceptor.classname";
            interceptString = System.getProperty(interceptFlag);
            if (interceptString != null) {
                interceptString = interceptString.trim();

                if (interceptString.length() == 0) {
                    interceptString = null;
                }
            }
        }

        if (interceptString == null) {
            return null;
        }

        Map<Object, Object> interceptorProperties = (selectedLbPairConfig == null)
                ? new HashMap<Object, Object>()
                : selectedLbPairConfig.toProperties();

        try {
            final JMSInterceptor jmsInterceptor = (JMSInterceptor)
                    Class.forName(interceptString, true, this.getClass().getClassLoader()).newInstance();

            jmsInterceptor.setProperties(interceptorProperties);

            this.logger.log(Level.INFO, "Interceptor [%s] created for destination [%s] in channel [%s]",
                    interceptString, getName(), channel.getName());

            return jmsInterceptor;
        }
        catch (Throwable t) {
            String s = String.format("Interceptor [%s] for destination [%s] in channel [%s] could not be created.",
                    interceptString, getName(), channel.getName());

            throw new RuntimeException(s, t);
        }
    }

    @Override
    protected void destroy(RuleSession ruleSession)
            throws Exception {
        this.logger.log(Level.DEBUG, "Performing destroy for jms.");
        stop();//Don't actually close session at this point, just close consumer. Session will be closed during postUnbind (postDestroy())
    }

    @Override
    public void postDestroy(RuleSession ruleSession) throws Exception {
        this.logger.log(Level.DEBUG, "Performing post destroy for jms.");
        for (final JmsReceiver listener : new ArrayList<JmsReceiver>(this.listeners)) {
            if (listener.getRuleSession() == ruleSession) {
                listener.destroy();//Do the actual closing of session.
                this.listeners.remove(listener);
            }
        }
    }

    private RuleSessionConfig.InputDestinationConfig findInputDestinationConfig(
            RuleSession ruleSession) {

        if (null != this.uri) {
            for (final RuleSessionConfig.InputDestinationConfig inputDestinationConfig
                    : ruleSession.getConfig().getInputDestinations()) {
                if ((null != inputDestinationConfig) && inputDestinationConfig.getURI().equals(this.uri)) {
                    return inputDestinationConfig;
                }
            }
        }

        return null;
    }


    @Override
    public JmsDestinationConfig getConfig() {
        return (JmsDestinationConfig) super.getConfig();
    }


    JmsSessionContext getCurrentSessionContext() throws JMSException {
    	if(!oneJmsSessionPerMessage) {
    		return getJmsSessionContextProvider().getSharedJmsSessionContext();
    	} else {
    		Object trigger = RuleTriggerManager.getRuleTrigger();
    		if(trigger instanceof SimpleEvent) {
                final EventContext context = ((SimpleEvent) trigger).getContext();
                if (context instanceof JmsMessageContext) {
                    return ((JmsMessageContext) context).getSessionContext();
                }
            }
    	}
    	return getJmsSessionContextProvider().createJmsSessionContext();
	}


    Destination getDestinationFromJndi(
            String destinationName) {

        if (null == destinationName) {
            destinationName = this.getConfig().getJmsDestinationName();
        }

        synchronized (this.nameToDestinationFromJndi) {
            Destination destination = this.nameToDestinationFromJndi.get(destinationName);

            try {
                if ((null == destination)
                        && !this.nameToDestinationFromJndi.containsKey(destinationName)
                        && this.channel.getConfig().isUseJNDI()) {

                    destination = (Destination) this.channel.getConfig().getJndiContext().lookup(destinationName);
                    this.nameToDestinationFromJndi.put(destinationName, destination);
                }
            }
            catch (Exception ignored) {
            }

            return destination;
        }
    }


    @Override
    public Event getEventFilter(
            RuleSession session) {  // Upgraded to public scope.

        return super.getEventFilter(session);
    }


    JmsSessionContextProvider getJmsSessionContextProvider()
            throws JMSException {

        return this.getConfig().isQueue()
                ? this.queueSessionContextProvider
                : this.topicSessionContextProvider;
    }


    Logger getLogger() {
        return this.logger;
    }


    public Session getSession()
            throws JMSException {

        return this.getCurrentSessionContext().getJmsSession();
    }


    public boolean isMQ() {
        if (null == this.isMq) {
            try {
                // TODO : This creates a session context if there is none. Can this be avoided?
                this.isMq = this.getJmsSessionContextProvider().getSharedJmsSessionContext().getJmsDestination()
                        .getClass().getName().startsWith("com.ibm.");
            }
            catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
        return this.isMq;
    }


    public boolean isSuspended() {
        synchronized (suspensionLock) {
            return this.suspended;
        }
    }


    public Destination lookUpOrCreateDestination(
            Map overrideData)
            throws Exception {

        final boolean defaultIsQueue = this.getConfig().isQueue();
        final String defaultName = this.getConfig().getJmsDestinationName();

        final boolean isQueue;
        final String destinationName;

        if (null == overrideData) {
            isQueue = defaultIsQueue;
            destinationName = defaultName;

        }
        else {
            final Object queue = overrideData.get("queue");
            isQueue = (null == queue) ? defaultIsQueue : "true".equalsIgnoreCase(queue.toString());

            final Object name = overrideData.get("name");
            if (null == name) {
                destinationName = defaultName;
                this.logger.log(Level.DEBUG,
                        "Destination 'name' override property not specified. Will use the default: %s",
                        destinationName);
            }
            else {
                destinationName = this.channel.getGlobalVariables().substituteVariables(name.toString())
                        .toString().trim();
            }
        }

        return this.getCurrentSessionContext().getJmsDestination(destinationName, isQueue);
    }


    private void printConfig() {

        final StringBuffer sb = new StringBuffer();

        boolean first = true;
        for (final Object objectKey : this.config.getProperties().keySet()) {
            final String k = (null == objectKey) ? "null" : objectKey.toString();
            if (!k.startsWith("Durable")) {
                if (first) {
                    first = false;
                }
                else {
                    sb.append(" ");
                }
                sb.append(k).append("=")
                        .append(this.getSubstitutedPropertyValue(k, !"Selector".equalsIgnoreCase(k)));
            }
        }

        if (null != this.config.getEventSerializer()) {
            sb.append(" Serializer=").append(this.config.getEventSerializer().getClass().getName());
        }

        this.logger.log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.destination.config", this.getURI(), sb));
    }


    protected void rebind()
            throws Exception {

        this.stop();

        for (final JmsReceiver receiver : this.listeners) {
            receiver.destroy();
        }
        this.listeners.clear();

        final Collection boundSessions = new ArrayList(this.getBoundSessions());
        this.getBoundSessions().clear();

        for (final Object ruleSession : boundSessions) {
            this.bind((RuleSession) ruleSession);
        }
    }


    public void resume() {

        synchronized (this.suspensionLock) {

            if (this.userControlled) {
                this.logger.log(Level.DEBUG, "Destination [%s] is suspended. Listener is not started.", this.getURI());

            }
            else {
                this.suspended = false;

                for (final JmsReceiver listener : this.listeners) {
                    try {
                        listener.start(ChannelManager.ACTIVE_MODE);
                        // ChannelManagerImpl.mode is not set in cache OM. How about other OM?
                    }
                    catch (Exception e) {
                        this.suspended = true;
                        this.logger.log(Level.ERROR, e,
                                "Can not resume destination %s: Exception while re-creating consumer.",
                                this.getURI());
                    }
                }
            }
        }
    }


    public int send(
            SimpleEvent event,
            Map overrideData)
            throws Exception {

        final Destination dest = this.lookUpOrCreateDestination(overrideData);

        if(this.channel.shouldUseSenderPool() && !this.oneJmsSessionPerMessage) {
            JmsSenderSessionContext jmsSenderSessionContext = this.channel.getSenderSessionContextPool().takeFromPool();
            try {
                return jmsSenderSessionContext.send(event, null, dest, this, overrideData);
            } finally {
                this.channel.getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
            }
        } else {
        return this.getCurrentSessionContext().send(event, null, dest, this, overrideData);
    }
    }


    public String sendImmediate(
            SimpleEvent event,
            Map overrideData)
            throws Exception {

        final Destination dest = this.lookUpOrCreateDestination(overrideData);

        if(!this.oneJmsSessionPerMessage && this.channel.shouldUseSenderPool()) {
            JmsSenderSessionContext jmsSenderSessionContext = this.channel.getSenderSessionContextPool().takeFromPool();
            try {
                return jmsSenderSessionContext.sendImmediate(event, null, dest, this, overrideData);
            } finally {
                this.channel.getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
            }
        } else {
            return this.getCurrentSessionContext().sendImmediate(event, null, dest, this, overrideData);
        }
            
    }


    private void sendPendingMessages()
            throws Exception {
//AA nothing was ever added to this list
//        final JmsSessionContextProvider sessionContextProvider = this.queueSessionContextProvider;
//        if (null != sessionContextProvider) {
//            for (final JmsSessionContext ctx : sessionContextProvider.getAllContexts()) {
//                ctx.sendPendingMessages();
//            }
//        }
    }


    void setSuspended(
            boolean suspended) {

        this.suspended = suspended;
    }


    public void stop() {

        synchronized (this.suspensionLock) {
            for (final JmsReceiver receiver : this.listeners) {
                receiver.stop();
            }
        }
    }


    public void start(
            int mode)
            throws Exception {

        synchronized (this.suspensionLock) {
            this.printConfig();

//            this.sendPendingMessages();

            final int listenerMode = (ChannelManager.ACTIVE_MODE == mode) && this.userControlled
                    ? ChannelManager.SUSPEND_MODE
                    : mode;

            for (final JmsReceiver listener : this.listeners) {
                listener.start(listenerMode);
            }

            super.start(mode);
        }
    }


    public void suspend() {

        synchronized (this.suspensionLock) {
            final String uri = this.getURI();
            for (final JmsReceiver listener : this.listeners) {
                this.logger.log(Level.DEBUG, "Suspending JMS receiver for %s", uri);
                listener.stop();
            }
            this.setSuspended(true);
        }
    }

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
		JMSTemporaryDestination referenceDestination = null;
		if(responseEventURI != null && serializerClass != null) {
			// Note: This is code repetition. Instead Channel.Destination api
			// needs to change and pass response event destination uri instead
			// of serializerClass
			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			TypeManager typemanager = session.getRuleServiceProvider().getTypeManager();
			TypeDescriptor typeDescriptor = typemanager.getTypeDescriptor(responseEventURI);
			if (typeDescriptor == null) {
				throw new Exception("Not a valid URI - '" + responseEventURI + "'");
			}
			
			Class<?> clz = typeDescriptor.getImplClass();
			if(clz == null || !SimpleEvent.class.isAssignableFrom(clz)) {
				throw new Exception("Not a SimpleEvent URI - '" + responseEventURI + "'");
			}
			
			Constructor<?> cons = clz.getConstructor(new Class[] {long.class});
			SimpleEvent evt = (SimpleEvent)cons.newInstance(new Object[] {new Long(0)});
			
			if (evt.getDestinationURI() == null) {
				throw new Exception("A Default Destination should be specified at Event - '" + responseEventURI + "'");
			}
			Channel.Destination referenceDest = session.getRuleServiceProvider().getChannelManager().getDestination(evt.getDestinationURI());
			boolean isJSONPayload = false;
			if(referenceDest instanceof JSONPayloadDestination) {
				JSONPayloadDestination jsonDestination = (JSONPayloadDestination)referenceDest;
				isJSONPayload = jsonDestination.isJSONPayload();
			}
			referenceDestination = new JMSTemporaryDestination(this.getChannel(), responseEventURI, serializerClass);
			referenceDestination.setJSONPayload(isJSONPayload);
		}
		//BE-23784 : Starting the channel , if not already started
		if(this.getChannel().isEnableStartChannelOnRequestEvent()){
			if(this.getChannel().getState().equals(State.CONNECTED)){
				this.getChannel().start(ChannelManager.ACTIVE_MODE);
			}
		}				
		
		Destination dest = this.lookUpOrCreateDestination(overrideData);
		if(this.channel.shouldUseSenderPool() && !this.oneJmsSessionPerMessage) {
			JmsSenderSessionContext jmsSenderSessionContext = this.channel.getSenderSessionContextPool().takeFromPool();
            try {
                return jmsSenderSessionContext.request(outevent, null, dest, this, referenceDestination, overrideData, timeout);
            } finally {
                this.channel.getSenderSessionContextPool().returnToPool(jmsSenderSessionContext);
            }
        } else {
            return this.getCurrentSessionContext().request(outevent, null, dest, this, referenceDestination, overrideData, timeout);
        }
	}

	public boolean handleFailedDeserialization(Message message) {
		if (JMSErrorEndpointHelper.isErrorEndpointEnabled()) {
			return JMSErrorEndpointHelper.forwardMessageToErrorEndpoint(this, message);
		}
		return false;
	}

	public boolean isOneJmsSessionPerMessage() {
		return oneJmsSessionPerMessage;
	}
	
	@Override
    public boolean isJSONPayload() {
		return isJSONPayload;
	}
	
    public void setJSONPayload(boolean isJSONPayload) {
		this.isJSONPayload = isJSONPayload;
	}
}
