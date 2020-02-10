package com.tibco.cep.driver.jms;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.pool.ObjectPool;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.CommandChannel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.CommandEvent;
import com.tibco.cep.runtime.model.event.CommandListener;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.CommandFactory;
import com.tibco.cep.runtime.session.impl.FTAsyncRuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

public abstract class BaseJMSChannel extends AbstractChannel<JMSDestination> implements CommandChannel {

    public static final String PROP_DISABLED_QUEUE_URIS = "be.channel.tibjms.queue.disabled";
    public static final String PROP_DISABLED_TOPIC_URIS = "be.channel.tibjms.topic.disabled";
    public static final String PROP_DISALLOW_DUP_CLIENT_ID = "be.channel.jms.disallow.dup.clientid";
    public static final String PROP_SENDER_POOL_MAX = "be.channel.jms.sender.session.pool.maxsize";
    
    public static final String PROP_RECONNECT_TIMEOUT = "be.jms.reconnect.timeout";
    public static final String PROP_RECONNECT_TIMEOUT_INCREMENTAL_ENABLED = "be.jms.reconnect.timeout.incremental.enabled";
    public static final String PROP_RECONNECT_TIMEOUT_INCREMENTAL_MAXWAIT = "be.jms.reconnect.timeout.incremental.maxwait";
    public static final String PROP_RECONNECT_TIMEOUT_INCREMENTAL_MULTIPLIER = "be.jms.reconnect.timeout.incremental.multiplier";

    protected boolean                hasQueue = false;
    protected boolean                hasTopic = false;
    protected boolean useSenderPool = false;

    protected JmsExceptionListener exceptionListener;
    private String channelFQN = null;
    private ArrayList dynamicDestinations = new ArrayList(5);   //This specifically for BW - SR:1-7UOFOB ++
    private JMSDestination commandDestination;
    private ObjectPool<JmsSenderSessionContext> jmsSenderSessionContextPool;

    final ConcurrentHashMap<String, MessageConsumer> commandQueueListeners =
            new ConcurrentHashMap<String, MessageConsumer>();
    final ConcurrentHashMap<String, MessageProducer> commandQueueSenders =
            new ConcurrentHashMap<String, MessageProducer>();

    final ConcurrentHashMap commandRegistrations  = new ConcurrentHashMap();

    final ConcurrentHashMap<String, MessageConsumer> commandTopicListeners =
            new ConcurrentHashMap<String, MessageConsumer>();
    final ConcurrentHashMap<String, MessageProducer> commandTopicSenders =
            new ConcurrentHashMap<String, MessageProducer>();
    private boolean enableStartChannelOnRequestEvent=true;
	String ignoreStartupErrorsForChannels[] = {};

	protected BaseJMSChannel(ChannelManager manager, String uri, JMSChannelConfig config) {
        super(manager, uri, config);
        //For : BE-23784 , this flag will govern starting of connected channels in case of requestEvent , if not already started.
        enableStartChannelOnRequestEvent=Boolean.parseBoolean(manager.getRuleServiceProvider().getProperties().getProperty(SystemProperty.ENABLE_JMS_CHANEL_START_ON_REQUEST_ENABLE.getPropertyName(),"true"));
        
        Iterator i = config.getDestinations().iterator();
        while (i.hasNext()) {
            DestinationConfig destConfig = (DestinationConfig) i.next();
            boolean isQueue = "true".equalsIgnoreCase(destConfig.getProperties().getProperty("Queue"));
            String destName = getGlobalVariables().substituteVariables(destConfig.getProperties().getProperty("Name")).toString();
             if (destName.trim().equals("")) {
                 if (isQueue) {
                    this.getLogger().log(Level.ERROR,
                            "Queue name in destination %s is empty. This destination is ignored.",
                            destConfig.getName());
                 } else {
                     this.getLogger().log(Level.ERROR,
                             "Topic name in destination %s is empty. This destination is ignored.",
                             destConfig.getName());
                 }
                continue;
            }
            if (isQueue) {
                hasQueue = true;
            } else {
                hasTopic = true;
            }
            JMSDestination dest = new JMSDestination(this, destConfig);
            //Destination dest = createDestination((DestinationConfig) i.next());
            this.destinations.put(dest.getURI(),dest);
        }

        exceptionListener = new JmsExceptionListener(this);
        
        String ignoreStartupErrorsForChannelsStr = getServiceProviderProperties().getProperty("be.jms.ignore.startup.error.channels", "");
        String [] ignoredChannels = ignoreStartupErrorsForChannelsStr.split(",");
        ignoreStartupErrorsForChannels = new String [ignoredChannels.length];
        int j=0;
        for (String s : ignoredChannels) {
        	ignoreStartupErrorsForChannels[j++] = s.trim();
        }
    }
    
    protected static void setClientId(Connection jmsConnection, String clientId, int dupNum, Properties p) throws InvalidClientIDException {
        try {
            jmsConnection.setClientID((0 == dupNum)
                    ? clientId
                    : clientId + "-" + dupNum);
        }
        catch (InvalidClientIDException e) {
            if(Boolean.parseBoolean(p.getProperty(BaseJMSChannel.PROP_DISALLOW_DUP_CLIENT_ID, Boolean.FALSE.toString()))) {
                throw e;
            }
            ++dupNum;
            LogManagerFactory.getLogManager().getLogger(BaseJMSChannel.class)
                    .log(Level.INFO, "%s: ClientID = %s. Setting new ClientID = %s-%s",
                            e.getMessage(), clientId, clientId, dupNum);
            setClientId(jmsConnection, clientId, dupNum, p);
        }
        catch (Exception e) {
            LogManagerFactory.getLogManager().getLogger(BaseJMSChannel.class)
                    .log(Level.ERROR, "%s: ClientID = %s will not be set.", e.getMessage(), clientId);
        }
    }

    protected static String getConnectionPassword(JMSChannelConfig config) throws AXSecurityException {
        final String password = config.getPassword();
        if ((null == password) || password.isEmpty()) {
            return password;
        }
        else {
            return new String(ObfuscationEngine.decrypt(password));
        }
    }

    public JMSDestination createDestination(DestinationConfig config)  {
        JMSDestination dest = new JMSDestination(this, config);
        dynamicDestinations.add(dest);
        return dest;
    }

    private void destroyAllCommandSessions() throws Exception {
        Iterator allRegistrations=commandRegistrations.keySet().iterator();
        while (allRegistrations.hasNext()) {
            String key= (String) allRegistrations.next();
            destroyCommandSessions(key);
        }
    }

    private void createAllCommandSessions() throws Exception {
        Iterator allRegistrations=commandRegistrations.entrySet().iterator();
        while (allRegistrations.hasNext()) {
            Map.Entry entry= (Map.Entry) allRegistrations.next();
            String key= (String) entry.getKey();
            CommandListener lsnr= (CommandListener) entry.getValue();
            createCommandSessions(key, lsnr);
        }
    }

    private void destroyCommandSessions(String key) throws Exception {
    	MessageConsumer qr= commandQueueListeners.remove(key);
        if (qr != null) {
            qr.close();
        }
        MessageConsumer ts= commandTopicListeners.remove(key);
        if (ts != null) {
            ts.close();
        }
        commandQueueSenders.remove(key);
        commandTopicSenders.remove(key);
    }

    protected abstract void createCommandSenderSessions(String key) throws Exception;
    protected abstract void createCommandReceiverSessions(String key, CommandListener commandListener) throws Exception;
    protected abstract void createCommandSessions(String key, CommandListener commandListener) throws Exception;
    protected abstract BytesMessage createCommandMessage(boolean oneToMany) throws Exception;

    public boolean supportsCommand() {
        return true;
    }

    public void sendCommand(String key, CommandEvent cmd, boolean oneToMany) throws Exception {
        createCommandSenderSessions(key);

        final MessageProducer messageProducer;
        if (oneToMany) {
            messageProducer = commandTopicSenders.get(key);
        } else {
            messageProducer = commandQueueSenders.get(key);
        }

        final BytesMessage msg = this.createCommandMessage(oneToMany);
        if ((null != msg) && (null != messageProducer)) {
            msg.setStringProperty("COMMAND_URI", cmd.getURI());
                byte [] payload=cmd.serialize();
                if ((payload != null) && (payload.length > 0)) {
                    msg.writeBytes(payload);
                }
                messageProducer.send(msg);
        } else {
            throw new Exception("Command Channel[" + key + "] not registered");
        }
    }

    public synchronized void registerCommand(String key, CommandListener commandListener) throws Exception {
        if (commandRegistrations.get(key) == null) {
            commandRegistrations.put(key, commandListener);
            createCommandSessions(key, commandListener);
        }
    }

    public synchronized void deregisterCommand(String key) throws Exception{
        if (commandRegistrations.remove(key) != null) {
            destroyCommandSessions(key);
        }
    }

    public String toString() {

        if (channelFQN != null) return channelFQN.intern();
        String str = getURI();
        try {
            JMSChannelConfig config = (JMSChannelConfig)channelConfig;
            String user = config.getUserID();
            String clientId = config.clientID;


            if(config.useJNDI) {
				String jndiURL = (String) config.jndiContext.getEnvironment().get("java.naming.provider.url");
                if (jndiURL == null || jndiURL.length() == 0)
                    jndiURL = "";
                else
                    jndiURL = getGlobalVariables().substituteVariables(jndiURL).toString();

                str = ResourceManager.getInstance().formatMessage("channel.connecting.jms.jndi", getURI(),
                        jndiURL, config.getQcf(), config.getTcf(), user, clientId);
            }
            else {
                str = ResourceManager.getInstance().formatMessage("channel.connecting.jms.provider", getURI(), config.getProviderURL(),
                        user, clientId);
            }
            channelFQN = str;
            return channelFQN.intern();

        }
        catch (Exception e) {
			str = "Exception while forming string : " + getURI();
        }
        return str;


    }
    
    public void init() throws Exception {

        Iterator<JMSDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            i.next().init();
        }

        super.init();
    }
    
    protected abstract void createSessions() throws JMSException;
    protected abstract void createConnections() throws Exception;
    protected abstract void createConnectionFactories() throws Exception;
    protected abstract boolean isTibcoJMS();
    
    protected boolean shouldUseSenderPool() {
        return this.useSenderPool;
    }
    
    protected void createSenderSessionPool() throws Exception {
        String maxPoolSizeProp = this.channelManager
                .getRuleServiceProvider().getProperties()
                .getProperty(BaseJMSChannel.PROP_SENDER_POOL_MAX);
        
        if(maxPoolSizeProp != null && maxPoolSizeProp.trim().length() > 0) {
            int maxPoolSize = Integer.parseInt(maxPoolSizeProp);
            this.getLogger().log(Level.INFO, "JMS channel %s will use a pool of sender sessions of max size %d", this.getURI(), maxPoolSize);
            this.jmsSenderSessionContextPool = new ObjectPool<JmsSenderSessionContext>(maxPoolSize) {
                @Override
                protected JmsSenderSessionContext createObject() throws Exception {
                    //1. ack mode is irrelevant here since its a send only session
                    //2. getQueueConnection could return a unified connection, or queue connection. regardless, send to queue or topic will work
                    BaseJMSChannel.this.getLogger().log(Level.INFO, "Adding new pooled sender session for JMS channel %s", BaseJMSChannel.this.getURI());
                    return new JmsSenderSessionContext(BaseJMSChannel.this, getQueueConnection().createSession(false, Session.AUTO_ACKNOWLEDGE));
                }
                @Override
                public boolean removeObject(JmsSenderSessionContext object) throws Exception {
                	return false;
                }
            };
            this.useSenderPool = true;
        }
    }
    
   public void connect() throws Exception {
    	
        try {
			State state = getState();

			if (state == State.UNINITIALIZED) {
			    init();
			}

			if ((state == State.STARTED) || (state == State.CONNECTED) || (state == State.STOPPED)) {
			    return;
			}



			if ((state == State.INITIALIZED) || (state == State.RECONNECTING)) {
			    synchronized(state) {

			        state = getState(); //Check the state again, call the getState, as opposed to the state variable
			        if ((state == State.STARTED) || (state == State.CONNECTED) || (state == State.STOPPED)) {
			            return;
			        }

			        createConnectionFactories();
			        if (state != State.RECONNECTING) {
			        this.getLogger().log(Level.INFO, "%s [Thread: %s] [State: %s]",
			                this, Thread.currentThread().getName(), state);
			    	}	

			        // Set to true to allow calling MessageConsumer.close(), Session.close() or Connection.close() in the MessageListener callback
			        EmsHelper.setAllowCloseInCallback();

			        createConnections();
			        createSenderSessionPool();
			        createSessions();

			        rebindDestinations(state);
			        //SR:1-7UOFOB --
			        setState(State.CONNECTED);
			        return;
			    }
			}

			if (state == State.EXCEPTION) {
			    JMSChannelConfig config = (JMSChannelConfig)channelConfig;
			    if (isTibcoJMS()) {
			    	createConnectionFactories();
			        this.getLogger().log(Level.INFO, "%s [Thread: %s] [State: %s]",
			                this, Thread.currentThread().getName(), state);
			        createConnections();
			        //createSessions();
			        rebindDestinations(State.RECONNECTING);
			        setState(State.CONNECTED);
			        return;
			    }
			}

			throw new Exception("Invalid state to connect..." + state);
		} catch (JMSException e) {
			if (getState() == State.INITIALIZED) {
				boolean ignoreError = false;
				for (String s : ignoreStartupErrorsForChannels) {
					if (s.equals("*")) {
						ignoreError = true;
						break;
					} else if (s.equals(getURI())) {
						ignoreError = true;
						break;
					}
				}
				if (ignoreError) {
					exceptionListener.onException(e);
					this.getLogger().log(Level.INFO, "JMS channel [%s] configured for ignoring startup errors. Will continue reconnect attempts.", this.getURI());
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		}
    }

    private void rebindDestinations(State state) throws Exception {
        Iterator<JMSDestination> i = getDestinations().values().iterator();
        while (i.hasNext()) {
            JMSDestination dest = i.next();
            dest.connect();
            if (state == State.RECONNECTING) {
                dest.rebind();
            }
        }
        //SR:1-7UOFOB ++
        Iterator itr = dynamicDestinations.iterator();
        while (itr.hasNext()) {
            JMSDestination dest = (JMSDestination)itr.next();
            dest.connect();
            if (state == State.RECONNECTING) {
                dest.rebind();
            }
        }
    }

    protected abstract void startConnections() throws JMSException;
    protected abstract void stopConnections() throws JMSException;
    protected abstract void closeConnections();
    
    public void start(int mode) throws Exception {
        if ((getState() == State.INITIALIZED) || (getState() == State.UNINITIALIZED)) {
            connect();
        }

        if ((getState() == State.CONNECTED) || (getState() == State.STOPPED) || (getState() == State.STARTED)) {
            Iterator i = getDestinations().values().iterator();
            while (i.hasNext()) {
                ((Destination) i.next()).start(mode);
            }
            createAllCommandSessions();
            if ((mode != ChannelManager.PASSIVE_MODE) && (getState() != State.STARTED)) {
                startConnections();
            }
            else {
                return;
            }
            
            //new Thread(exceptionListener).run();
        }
        if (getState() != State.EXCEPTION && getState() != State.RECONNECTING) {
        	super.start(mode);
        }
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
    }


    /**
     * Temporarily call stop on the channel
     */
    public void stop()  {

        try {
            if ((getState() == State.STARTED)) {

                Iterator i = getDestinations().values().iterator();
                while (i.hasNext()) {
                    ((Destination) i.next()).stop();
                }
                stopConnections();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.stop();
    }



    public void close() throws Exception {

        if ((getState() == State.STARTED) || (getState() == State.STOPPED)) {
            stop();
//
//            try {
//                if (m_jmsTopicSession != null)  m_jmsTopicSession.close();
//            } catch (JMSException e) {
//                //e.printStackTrace(); //eat the exception
//            }
//            try {
//                if (m_jmsQueueSession != null) m_jmsQueueSession.close();
//            } catch (JMSException e) {
//                //e.printStackTrace(); eat the exception
//            }
            closeConnections(); 
        }

    }

    protected abstract void doChildShutdown();
    
    public void shutdown() throws Exception {

        for (Iterator it = getDestinations().values().iterator(); it.hasNext(); ) {
            final Destination dest = (Destination) it.next();
            dest.close();
        }//for
        destroyAllCommandSessions();

//        if (m_jmsTopicSession != null) {
//            try {
//                m_jmsTopicSession.close();
//            }
//            catch(Exception ex) {}//eat}
//
//            m_jmsTopicSession=null;
//        }
//
//        if (m_jmsQueueSession != null) {
//            try {
//                m_jmsQueueSession.close();
//            }
//            catch(Exception ex) {}//eat}
//
//            m_jmsQueueSession=null;
//        }

        doChildShutdown();
        
        if (this.getState() != State.RECONNECTING) {
            this.setState(State.UNINITIALIZED);
        }
    }

    public void send(SimpleEvent event, String destURI, Map overrideData) throws Exception {
        //todo - SS?
    }
    
    public JMSChannelConfig getConfig() {
        return (JMSChannelConfig)channelConfig;
    }


    public abstract Connection getTopicConnection();
    public abstract Connection getQueueConnection();
    
    public ObjectPool<JmsSenderSessionContext> getSenderSessionContextPool() {
        return this.jmsSenderSessionContextPool;
    }


    protected int getCommandQueueAckMode()
            throws JMSException {

        final int queueAckMode = this.isTibcoJMS() ? EmsHelper.DEFAULT_ACK_MODE : Session.CLIENT_ACKNOWLEDGE;

        return ((BEProperties) this.getServiceProviderProperties())
                .getInt("be.channel.tibjms.queue.ack.mode", queueAckMode);
    }


    protected int getCommandTopicAckMode()
            throws JMSException {

        final int topicAckMode = this.isTibcoJMS() ? EmsHelper.DEFAULT_ACK_MODE : Session.CLIENT_ACKNOWLEDGE;

        return ((BEProperties) this.getServiceProviderProperties())
                .getInt("be.channel.tibjms.topic.ack.mode", topicAckMode);
    }


    public boolean hasQueue() {
        return this.hasQueue;
    }


    public boolean hasTopic() {
        return this.hasTopic;
    }


    protected abstract void _setClientID(String clientID) throws JMSException;
    
    protected void setClientID(String clientId, int dupNum) {
        String newClientId = dupNum == 0 ? clientId : clientId + "-" + String.valueOf(dupNum);
        try {
            _setClientID(newClientId);
        }
        catch (Exception ex) {
            if (ex instanceof InvalidClientIDException) {
                String nextClientId = clientId + "-" + String.valueOf(++dupNum);
                this.getLogger().log(Level.INFO, "%s: ClientID = %s. Setting new ClientID = %s",
                        ex.getMessage(), clientId, nextClientId);
                setClientID(clientId, dupNum);
            } else {
                this.getLogger().log(Level.ERROR, "%s: ClientID = %s will not be set.", ex.getMessage(), clientId);
            }
         }
    }

    class JmsExceptionListener implements ExceptionListener, Runnable  {


        private static final String FT_PREFIX = "FT-SWITCH: ";
        private static final String FT_RECONNECT_PREFIX = FT_PREFIX + "Reconnect";

        BaseJMSChannel m_channel;
        int           m_attempt=0;
        Thread        m_monitor;
        WaitTimeout jmsWaitTimeout;

        int channelMode = -1;

        int[] exceptionCount = new int[] {0};


        protected JmsExceptionListener(BaseJMSChannel channel) {
        	if (System.getProperty("ems.ft.notification.enabled", "true").equalsIgnoreCase("true")) {
        		setExceptionOnFTSwitch(true);
        	} else {
        		setExceptionOnFTSwitch(false);
        	}
            m_channel=channel;
            
            initJMSWaitTimeout();
        }
        
        protected void setExceptionOnFTSwitch(boolean enabled) {
        	try {
        		boolean useJndi = ((JMSChannelConfig)channelConfig).isUseJNDI();
        		String ctxFactory =  useJndi ? (String) ((JMSChannelConfig)channelConfig).jndiContext.getEnvironment().get("java.naming.factory.initial") : null;
        		if (useJndi && !"com.tibco.tibjms.naming.TibjmsInitialContextFactory".equals(ctxFactory)) {
        			return;//Non EMS, should not proceed.
        		}
        		
        		Class clz = Class.forName("com.tibco.tibjms.Tibjms");
        		if (clz != null) {
        			Method m = clz.getMethod("setExceptionOnFTSwitch", boolean.class);
        			if (m != null) {
        				m.invoke(null, enabled);
        			}
        		}
        	} catch (Exception e) {
        		BaseJMSChannel.this.getLogger().log(Level.WARN, "Error while enabling FT switch logging", e);
        	}
        }
        
        /**
         * Initialises the JMS reconnect wait timeout policy according to user preference.
         */
        private void initJMSWaitTimeout() {
        	int reconnectTimeoutSec = Integer.parseInt(m_channel.getServiceProviderProperties().getProperty(PROP_RECONNECT_TIMEOUT, "0"));
            
            boolean incrementalWaitEnabled = Boolean.parseBoolean(m_channel.getServiceProviderProperties().getProperty(PROP_RECONNECT_TIMEOUT_INCREMENTAL_ENABLED, "false"));
            
            if (incrementalWaitEnabled) {
            	int minWaitSec = reconnectTimeoutSec;
            	int maxWaitSec = Integer.parseInt(m_channel.getServiceProviderProperties().getProperty(PROP_RECONNECT_TIMEOUT_INCREMENTAL_MAXWAIT, String.valueOf(minWaitSec * LinearWaitTimeout.DEFAULT_MAX_WAIT_MULTIPLE)));
            	int linearMultiple = Integer.parseInt(m_channel.getServiceProviderProperties().getProperty(PROP_RECONNECT_TIMEOUT_INCREMENTAL_MULTIPLIER, String.valueOf(LinearWaitTimeout.DEFAULT_MULTIPLIER)));
            	if (minWaitSec <= 0) {
            		BaseJMSChannel.this.getLogger().log(Level.ERROR, "Incremental wait - Specify a non zero minimum wait ('" + PROP_RECONNECT_TIMEOUT + "').");
                	throw new RuntimeException("Incremental wait - Minimum is not specified.");
            	}
            	else if (maxWaitSec < 2*minWaitSec) {
            		BaseJMSChannel.this.getLogger().log(Level.ERROR, "Incremental wait - Specified max wait(%d) must be atleast twice of min wait(%d).", maxWaitSec, minWaitSec);
                	throw new RuntimeException("Incremental wait - Maximum wait is too less.");
            	}
            	BaseJMSChannel.this.getLogger().log(Level.DEBUG, "Using incremental reconnect wait timeouts for channel - '%s', with minimum & maximum waits as %d seconds & %d seconds.", m_channel.getName(), minWaitSec, maxWaitSec);
            	jmsWaitTimeout = new LinearWaitTimeout(minWaitSec * 1000, maxWaitSec * 1000, linearMultiple);
            }
            else if (reconnectTimeoutSec > 0) {
            	jmsWaitTimeout = new ConstantWaitTimeout(reconnectTimeoutSec * 1000);
            }
            // else need not initialise jmsWaitTimeout
        }

        private boolean startWithAnyFilter(String msg) {

            String reconnectMsgCodes = m_channel.getServiceProviderProperties().getProperty("be.jms.reconnect.msgCodes", "*");
            String[] reconnectFilters = reconnectMsgCodes.split(",");
            for(int i = 0; i < reconnectFilters.length; i++ ) {
                if(reconnectFilters[i].trim().equals("*") || msg.startsWith(reconnectFilters[i].trim())) {
                    return true;
                }
            }
            return false;
        }

        public void onException(JMSException e) {
            final String errorCode = e.getErrorCode();
            if ((null != errorCode) && errorCode.startsWith(FT_PREFIX)) {
                this.m_channel.getLogger().log(
                        (errorCode.startsWith(FT_RECONNECT_PREFIX) ? Level.INFO : Level.WARN),
                        "JMS FT event: [%s] [%s]", errorCode, e.getMessage());
                return;
            }

            this.m_channel.getLogger().log(Level.ERROR, "JMS Exception [code]:%s [message]:%s",
                    errorCode, e.getMessage());
            try {
                final Exception linked = e.getLinkedException();
                if (null != linked) {
                    this.m_channel.getLogger().log(Level.ERROR, "JMS Linked exception [message]: %s",
                            linked.getMessage());
                }
            }
            catch (Exception ignored) {
            }            
            e.printStackTrace();

            synchronized(exceptionCount) {
                if(exceptionCount[0] == 0) {
                    exceptionCount[0] = exceptionCount[0] + 1;
                    //handle exception

//                    channelMode = m_channel.getState().
                    m_channel.setState(State.EXCEPTION);

                    if (jmsWaitTimeout != null && startWithAnyFilter(e.getMessage())) {
                        if (m_channel.getState() == State.RECONNECTING) return;
                        m_channel.setState(State.RECONNECTING);
                        if ((m_monitor != null) && m_monitor.isAlive()) return;
                        m_monitor=new Thread(this, "BE-JMSReconnector");
                        m_monitor.start();
                    }
                }
                else
                    exceptionCount[0] = exceptionCount[0] + 1;
            }
        }


        synchronized public void run() {
            try {
                m_attempt=0;
                //SR:1-7UOFOB
                synchronized(m_channel.getState()) {
                    while (true) {
                    	int waitTimeout = jmsWaitTimeout.getTimeoutDelay(m_attempt+1);
                    	this.m_channel.getLogger().log(Level.DEBUG, "Waiting for %d seconds to retry connect. Channel - '%s'", waitTimeout/1000, m_channel.getName());
                        wait(waitTimeout);
                        synchronized(exceptionCount) {
                            if(!m_channel.getState().equals(State.STARTED) && !m_channel.getState().equals(State.CONNECTED)) {
                                try {
                                    m_channel.shutdown();
                                    m_channel.connect();
                                } catch (Exception e) {
                                    ++this.m_attempt;
                                    if (this.m_channel.getLogger().isEnabledFor(Level.DEBUG)) {
                                        this.m_channel.getLogger().log(Level.DEBUG,
                                                "Reconnect failed attempt #%d [channel]%s [thread]%s",
                                                this.m_attempt, this.m_channel.getURI(),
                                                Thread.currentThread().getName());
                                    }
                                    continue;
                                }
                                //bounce the connection to ensure unacked messages are flushed
                                this.m_channel.getLogger().log(Level.DEBUG, "Bouncing the JMS connection");
                                this.m_channel.setState(State.RECONNECTING);
                                try {
                                    m_channel.shutdown();
                                    m_channel.connect();
                                } catch (Exception e) {
                                    ++this.m_attempt;
                                    if (this.m_channel.getLogger().isEnabledFor(Level.DEBUG)) {
                                        this.m_channel.getLogger().log(Level.DEBUG,
                                                "Reconnect failed attempt #%d [channel]%s [thread]%s",
                                                this.m_attempt, this.m_channel.getURI(),
                                                Thread.currentThread().getName());
                                    }
                                    continue;
                                }
                                
                                boolean startListener = true;
                                Object rsp = ((RuleServiceProviderImpl)channelManager.getRuleServiceProvider()).getContainerRsp();
                                if(rsp instanceof FTAsyncRuleServiceProviderImpl)
                                    startListener = ((FTAsyncRuleServiceProviderImpl)rsp).isPrimary();
//                                else if(rsp instanceof FTRuleServiceProviderImpl)
//                                    startListener = ((FTRuleServiceProviderImpl)rsp).isPrimary();

                                if(startListener) {
                                    if(!m_channel.getState().equals(State.STARTED)) {
                                        //SR:1-7UOFOB ++
                                        m_channel.start(ChannelManager.ACTIVE_MODE); //toDo SS/NL which mode should we start in?
                                        Iterator itr = dynamicDestinations.iterator();
                                        while (itr.hasNext()) {
                                            JMSDestination dest = (JMSDestination)itr.next();
                                            dest.start(ChannelManager.ACTIVE_MODE); //toDo SS/NL which mode should we start in?
                                        }
                                        //SR:1-7UOFOB --
                                    }
                                    this.m_channel.getLogger().log(Level.INFO,
                                            "Reconnected to JMS server and restarted the listeners...");
                                }
                                else {
                                    this.m_channel.getLogger().log(Level.INFO, "Reconnected to JMS server ...");
                                }
                            }
                            exceptionCount[0] = 0;
                            break; // we are done this will stop the thread.
                        }
                    }
                }
            } catch (Exception ee) {
                this.m_channel.getLogger().log(Level.ERROR, ee, "Error reconnecting to JMS server giving up ...");
                //ee.printStackTrace();
            }
        }
    }

    public boolean isAsync() {
        return true;
    }


    class CommandMsgListener implements MessageListener{
        CommandListener lsnr;
        CommandFactory cmdFactory;

        CommandMsgListener(CommandListener lsnr, CommandFactory cmdFactory) {
            this.lsnr=lsnr;
            this.cmdFactory=cmdFactory;
        }

        public void onMessage(Message message) {
            try {
                if (message instanceof BytesMessage) {
                    BytesMessage bm= (BytesMessage) message;
                    String uri=bm.getStringProperty("COMMAND_URI");

                    byte [] b=new byte[(int) bm.getBodyLength()];
                    if (b.length > 0) {
                        bm.readBytes(b);
                    }
                    CommandEvent event=cmdFactory.createCommandEvent(uri, b);
                    event.setContext(new JMSCommandContext(message));
                    lsnr.onCommand(event);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        class JMSCommandContext implements CommandEvent.CommandContext {
            Message msg;
            JMSCommandContext(Message msg) {
                this.msg=msg;
            }

            public void acknowledge() {
                try {
                    if (msg != null) {
                        msg.acknowledge();
                        msg=null;
                    }
                } catch (Exception ex) { 
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public boolean isEnableStartChannelOnRequestEvent() {
		return enableStartChannelOnRequestEvent;
	}
}