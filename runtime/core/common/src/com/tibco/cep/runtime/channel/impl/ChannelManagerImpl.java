package com.tibco.cep.runtime.channel.impl;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DriverManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.event.LifecycleListener;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.CommandFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 28, 2006
 * Time: 4:00:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelManagerImpl implements ChannelManager {

    private static final String TRUE_AS_STRING  = Boolean.TRUE.toString();
    private static final String FALSE_AS_STRING = Boolean.FALSE.toString();

    private static final Pattern PATTERN_PROPERTIES_STATEMENTS = Pattern.compile(
            ";*((?:[^;\\\\]|(?:\\\\[=;\\\\]))+)", Pattern.MULTILINE);
    // first any number of ; then at least once { (neither \ nor ;) or (\ then (= or ; or \)) }
    private static final Pattern PATTERN_PROPERTIES_NAME_EQ_VALUE = Pattern.compile(
            "((?:[^=\\\\]|(?:\\\\[=;\\\\]))+)=(.*)", Pattern.MULTILINE);
    // at least once {(neither \ nor =) or (\ then (= or ; or \)) } then = then .*
    private static final Pattern PATTERN_PROPERTIES_ESCAPING = Pattern.compile("\\\\(.)", Pattern.MULTILINE);

    protected LinkedList m_eventQueue = new LinkedList();
    protected boolean m_started = false;
    /**
     * Map of ChannelURI, and Channels
     */
    protected Map m_channels = new HashMap ();
    protected Map m_inactiveChannels = new HashMap ();
    protected Map m_destinations = new HashMap ();

    RuleServiceProvider serviceProvider;
    Logger logger;

    private static byte STATE_ERROR = -1;
    private static byte STATE_UNINITIALIZED = 0;
    private static byte STATE_INITIALIZED = 1;
    private static byte STATE_CONNECTED = 2;
    private static byte STATE_STARTED = 3;
    private static byte STATE_STOP = 4;
    private boolean isActive = false;

    private int mode = ChannelManager.PASSIVE_MODE;

    private byte status = STATE_UNINITIALIZED;

    private CommandFactory m_commandFactory = new CommandFactory();
    
    public ChannelManagerImpl (RuleServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        this.logger = serviceProvider.getLogger(this.getClass());
    }

    public void init() throws Exception {
        this.logger.log(Level.INFO, "Initialized Channel Manager");
        DriverManager.initialize();
        Ontology ont = serviceProvider.getProject().getOntology();
        String deActivatedChannelsProp = (String)serviceProvider.getProperties().get(SystemProperty.CHANNEL_DEACTIVATE.getPropertyName());
        deActivatedChannelsProp = (deActivatedChannelsProp == null) ? "" : deActivatedChannelsProp;
        String[] deActivatedChannelPaths = BEStringUtilities.split(deActivatedChannelsProp, ",");
        Iterator<com.tibco.cep.designtime.model.service.channel.Channel> i = ont.getChannels().iterator();
        
        while (i.hasNext()) {
            com.tibco.cep.designtime.model.service.channel.Channel channel = i.next();
            boolean shouldActivate = true;
            for (String deActivatedChannelPath : deActivatedChannelPaths) {
                if (channel.getFullPath().equals(deActivatedChannelPath)) {
                    logger.log(Level.WARN, "Channel %s not activated", deActivatedChannelPath);
                    shouldActivate = false;
                    break;
                }
            }
            
            if (shouldActivate) {
            	ChannelConfig config = new ChannelConfigurationImpl(
                    	serviceProvider.getGlobalVariables(),
                    	channel);
                registerChannel(config);
            } else {
            	m_inactiveChannels.put(channel.getFullPath(), channel);
            }
        }
        status = STATE_INITIALIZED;
    }

	public Channel registerChannel(ChannelConfig config) throws Exception {
		ChannelDriver chDriver = DriverManager.driverForName(config.getType());
		if (chDriver == null) {
			throw new Exception("Driver with name '" + config.getType() + "' not found in classpath.");
		}
		Channel channel = chDriver.createChannel(this, config.getURI(), config);
		channel.init();
		m_destinations.putAll(channel.getDestinations());
		Map allDestinations = channel.getDestinations();
		if (this.logger.isEnabledFor(Level.DEBUG)) {
		    this.logger.log(Level.DEBUG, "Available destination URI:");
		    for (Object o : allDestinations.keySet()) {
		        this.logger.log(Level.DEBUG, "Destination URI = %s", o);
		    }
		}
		m_channels.put(config.getURI(), channel);
		return channel;
	}

    public CommandFactory getCommandFactory() {
        return m_commandFactory;
    }

    public Collection getChannels () {
        return m_channels.values();
    }

    public Object getChannelForReconnect(String uri)  {
    	Object channel = (Channel) m_channels.get(uri);
    	if (channel == null) {
    		channel =  m_inactiveChannels.get(uri);
    	}
        return channel; 
    }

    public Channel getChannel(String uri)  {
    	return (Channel) m_channels.get(uri);
    }

    public Channel.Destination getDestination(String destURI) {
        return (Channel.Destination) this.m_destinations.get(destURI);
    }

    public SimpleEvent sendEvent(SimpleEvent event, String destinationPath, String properties) throws Exception {
        final Map propertiesMap = makeParamsMap(properties);
        return sendEvent(event, destinationPath, propertiesMap);
    }

    public SimpleEvent sendEvent(
            SimpleEvent event,
            String destinationPath,
            Map properties) throws Exception {
        Channel.Destination dest = (Channel.Destination)m_destinations.get(destinationPath);
        LifecycleListener l = getLifecycleListener();
        if (dest == null) {
        	if (l != null && status == STATE_UNINITIALIZED) {
            	l.eventSent(event, null);
            	return null; // don't throw exception during unit test - capture send and return 
            } else {
            	throw new Exception("Invalid destination uri [" + destinationPath + "] specified. Event will not be sent.");
            }
        }
        int ret = dest.send(event, properties);
        if (l != null) {
        	l.eventSent(event, dest);
        }
        if (ret < 0) {
            return null;
        }
        return event;
    }

    private LifecycleListener getLifecycleListener() {
    	if (serviceProvider instanceof RuleServiceProviderImpl) {
    		return ((RuleServiceProviderImpl) serviceProvider).getLifecycleListener();
    	}
    	return null;
	}

	private static Map makeParamsMap(String properties) {
        final Map map = new HashMap();
        if (null == properties) {
            return null;
        }//if
        properties = properties.trim();
        if (properties.equals("")) {
            return null;
        }//if

        final Matcher statementMatcher = PATTERN_PROPERTIES_STATEMENTS.matcher(properties);
        while (statementMatcher.find()) {
            final String statement = statementMatcher.group(1);
            final Matcher nvMatcher = PATTERN_PROPERTIES_NAME_EQ_VALUE.matcher(statement);
            if (nvMatcher.matches()) {
                final String escapedName = nvMatcher.group(1);
                final String name = unescape(escapedName);
                final String escapedValue = nvMatcher.group(2);
                final String value = unescape(escapedValue);
                map.put(name.toLowerCase(), value);
            }//if
        }//while
        return map;
    }//makeParamsMap

    private static String unescape(String nameOrValue) {
        final Matcher matcher = PATTERN_PROPERTIES_ESCAPING.matcher(nameOrValue);
        final StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group());
        }//while
        matcher.appendTail(sb);
        return sb.toString();
    }//unescapeNameOrValue

    /**
     *
     * @param event
     * @throws Exception
     */
    public SimpleEvent sendEvent(SimpleEvent event) throws Exception{
        String destURI = event.getDestinationURI();
        return sendEvent(event, destURI, (Map)null);
    }

    public SimpleEvent sendEvent(SimpleEvent event, String override) throws Exception{
        return sendEvent(event, override, (Map)null);
    }

    public void shutdown() {
        stopChannels();

        for (Iterator it = m_channels.values().iterator(); it.hasNext();) {
            final Channel channel = (Channel)it.next();
            try {
            	this.logger.log(Level.INFO, "Closing Channel: %s", channel.getURI());
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }//catch
        }//for
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return this.serviceProvider;
    }

    public void connectChannels() throws Exception {
        if (status < STATE_INITIALIZED) {
            //TODO - get it from ResourceBundle
            String errString = "Cannot start - RulesessionManager is in invalid state (error/uninitialized). Try initializing again";
            this.logger.log(Level.ERROR, errString);
            throw new Exception (errString);
        }
        for (Iterator it = m_channels.values().iterator(); it.hasNext();) {
            final Channel channel = (Channel)it.next();
            channel.connect();
        }
        status = STATE_CONNECTED;
    }

    public void startChannels(int initialMode) throws Exception {
        if (status < STATE_INITIALIZED) {
            //TODO - get it from ResourceBundle
            String errString = "Cannot start - RulesessionManager is in invalid state (error/uninitialized). Try initializing again";
            this.logger.log(Level.ERROR, errString);
            throw new Exception (errString);
        }
        for (Iterator it = m_channels.values().iterator(); it.hasNext();) {
            final Channel channel = (Channel)it.next();
            if (status != STATE_CONNECTED) {
                channel.connect();
            }
            channel.start(initialMode);
        }//for
        mode = initialMode;
        status = STATE_STARTED;
    }

    public void startChannel(String channelName, int mode) throws Exception {
        Channel channel = (Channel)m_channels.get(channelName);
        if (channel == null) {
            throw new Exception("Invalid channel name specified -" + channelName);
        }
        channel.connect();
        channel.start(mode);
    }
    
    public void startChannelDuringReconnect(String channelName, int mode) throws Exception {
    	Channel channel = (Channel) m_channels.get(channelName);
    	if (channel == null) {
    		com.tibco.cep.designtime.model.service.channel.Channel designTimeChannel = (com.tibco.cep.designtime.model.service.channel.Channel) m_inactiveChannels.get(channelName);
    		if (designTimeChannel == null) {
            	throw new Exception("Invalid channel name specified - " + channelName);
            }
    		
    		ChannelConfig config = new ChannelConfigurationImpl(serviceProvider.getGlobalVariables(), designTimeChannel);
            registerChannel(config);
            channel = (Channel) m_channels.get(channelName);
            if (channel == null) {
            	throw new Exception("Invalid channel name specified - " + channelName);
            }
    	}
    	channel.connect();
    	
    	for (Channel.Destination d : channel.getDestinations().values()) {
    		((RuleServiceProviderImpl)serviceProvider).bindForReconnect(d);
    	}
    	
    	channel.start(ChannelManager.ACTIVE_MODE);
    	
    	m_inactiveChannels.remove(channelName);
    }

    public void stopChannels() {
        if (status == STATE_STOP ) {
            return;
        }
        if (status < STATE_INITIALIZED) {
            return;
        }

        for (Iterator it = m_channels.values().iterator(); it.hasNext();) {
            final Channel channel = (Channel)it.next();

            try {
            	this.logger.log(Level.INFO, "Stopping channel: %s", channel.getURI());
                channel.stop();
            } catch (Exception e) {
                //TODO - get it from ResourceBundle
                this.logger.log(Level.ERROR, "Error stopping channel: %s", channel.getURI());
                throw new RuntimeException(e);
            }
        }//for
        status = STATE_STOP;
    }

    public void stopChannel(String channelName) {
        Channel channel = (Channel)m_channels.get(channelName);
        if (channel == null) {
            //TODO - get it from ResourceBundle
            String errString = "Invalid channel specified for stopping - " + channelName;
            this.logger.log(Level.ERROR, errString);
            throw new RuntimeException(errString);
        }

        try {
            channel.stop();
        } catch (Exception e) {
            //TODO - get it from ResourceBundle
            this.logger.log(Level.ERROR, "Error stopping channel: %s", channel.getURI());
            throw new RuntimeException(e);
        }
    }

    public void setMode(int lMode) throws Exception {
        if (lMode == mode) return;

        //The original mode was active, and changine
        switch (mode) {
            case ChannelManager.ACTIVE_MODE :
                mode = lMode;
                if (lMode == ChannelManager.PASSIVE_MODE) {
                    stopChannels();
                    return;
                }
                else { //This is Suspend State
                    suspendChannels();
                    return;
                }
            case ChannelManager.SUSPEND_MODE:
                mode = lMode;
                if (lMode == ChannelManager.PASSIVE_MODE) {
                    resumeChannels();
                    stopChannels();
                    return;
                }
                else {
                    resumeChannels();
                    return;
                }
            case ChannelManager.PASSIVE_MODE:
                mode = lMode;
                if (lMode == ChannelManager.ACTIVE_MODE) {
                    startChannels(ChannelManager.ACTIVE_MODE);
                    return;
                }
                else {
                    startChannels(ChannelManager.SUSPEND_MODE);
                    return;
                }
            default:
                throw new Exception("Invalid mode specified");
        }
    }

    public int getMode() {
        return this.mode;
    }

    public void suspendChannels() {
       Iterator r = m_channels.values().iterator();
        while (r.hasNext()) {
            Channel ch = (Channel)r.next();
            ch.suspend();
        }
    }

    public void resumeChannels() {
        Iterator r = m_channels.values().iterator();
        while (r.hasNext()) {
            Channel ch = (Channel)r.next();
            ch.resume();
        }
    }

	@Override
	public String sendEventImmediate(SimpleEvent event, String destinationURI,
			String properties) throws Exception {
        final Map propertiesMap = makeParamsMap(properties);
        return sendEventImmediate(event, destinationURI, propertiesMap);
	}

    public String sendEventImmediate(
            SimpleEvent event,
            String destinationPath,
            Map properties)
            throws Exception
    {
        Channel.Destination dest = (Channel.Destination)m_destinations.get(destinationPath);
        if (dest == null) {
            throw new Exception("Invalid destination uri [" + destinationPath + "] specified. Event will not be sent.");
        }
        String ret = dest.sendImmediate(event, properties);
        return ret;
    }

	@Override
	public Object requestEvent(SimpleEvent outevent, String responseEventURI, String outgoingDestinationPath, long timeout, String properties)
			throws Exception {
		Channel.Destination dest = (Channel.Destination)m_destinations.get(outgoingDestinationPath);
		if (dest == null) {
			throw new Exception("Invalid destination uri [" + outgoingDestinationPath + "] specified. Event will not be sent.");
		}
		
		String serializerClass = null;
		ExpandedName responseEventURIExpName = null;
		if (responseEventURI != null) {
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
			Channel.Destination referenceDest = (Channel.Destination)m_destinations.get(evt.getDestinationURI());
			serializerClass = referenceDest.getEventSerializer().getClass().getName();
			responseEventURIExpName = evt.getExpandedName();
		}
		
		Map propertiesMap = makeParamsMap(properties);
		return dest.requestEvent(outevent, responseEventURIExpName, serializerClass, timeout, propertiesMap);
	}
}
