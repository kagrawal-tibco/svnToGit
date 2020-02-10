package com.tibco.cep.driver.as.internal;

import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_SESSION;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_ROLE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_PREFETCH;
import static com.tibco.cep.driver.as.ASConstants.V_AS_DEST_PROP_PREFETCH;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_SPACE_NAME;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_START_MODE;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumptionmodes.ASConsumptionModeFactory;
import com.tibco.cep.driver.as.internal.consumptionmodes.IASConsumptionMode;
import com.tibco.cep.driver.as.utils.StringUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel.State;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;

public class ASDestination extends AbstractDestination<ASChannel> implements IASDestination {

    private Logger logger;
    private Space space;
    private SpaceDef spaceDef;
    private String spaceName;

    // Supports suspend and resume
    private boolean isBinded;
    private Map<Object, Object> condition = new HashMap<Object, Object>();

    private IASConsumptionMode consumptionMode;
    private DistributionRole distributionRole;
    private BrowserType browserType;

    private List<QueueEntry> localQueue;

    public ASDestination(ASChannel channel, DestinationConfig config) {
        super(channel, config);
        postConstruction();
    }

    private void postConstruction() {
        logger = channel.getLogger();
        // Load spaceName setting
        spaceName = channel.getGlobalVariables().substituteVariables(config.getProperties().getProperty(K_AS_DEST_PROP_SPACE_NAME)).toString();
        distributionRole = DistributionRole.valueOf(config.getProperties().getProperty(K_AS_DEST_PROP_DISTRIBUTION_ROLE));
        browserType = BrowserType.valueOf(config.getProperties().getProperty(K_AS_DEST_PROP_BROWSER_TYPE));

        logger.log(Level.INFO, "AS Destination %s - SpaceName: %s", this.getName(), spaceName);
        logger.log(Level.INFO, "AS Destination %s - DistributionRole: %s BrowserType: %s", this.getName(), distributionRole, browserType);
        localQueue = new LinkedList<QueueEntry>();
        backwardCompatibility();
    }

    private void backwardCompatibility() {
        Properties props = getConfig().getProperties();
        if (StringUtils.isEmpty(props.getProperty(K_AS_DEST_PROP_PREFETCH))) {
            props.setProperty(K_AS_DEST_PROP_PREFETCH, V_AS_DEST_PROP_PREFETCH);
        }
    }

    @Override
    public void bind(RuleSession session) throws Exception {
        isBinded = true;

        // create consumption mode for input destination
        condition.put(K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG, config);
        condition.put(K_AS_DEST_CONSUMPTION_MODE_CONDITION_SESSION, session);
        // load consumption mode setting
        consumptionMode = ASConsumptionModeFactory.createASConsumptionMode(this, config, logger);

        logger.log(Level.INFO, "This destination %s is an input destination - SpaceName: %s ", this.getName(), spaceName);
        super.bind(session);
    }

    @Override
    public void connect() throws Exception {
        if (null != space) {
            return;
        }
        logger.log(Level.INFO, "%s connecting to space '%s'", this.getName(), spaceName);
        Metaspace metaspace = channel.getMetaspace();
        try {
            this.space = metaspace.getSpace(spaceName, distributionRole);
        } catch (Exception e) {
            logger.log(Level.WARN, "%s failed connecting to Space '%s' failed! Error: %s", this.getName(), spaceName, e.getMessage());
            return;
        }
        if (space != null) {
            spaceDef = space.getSpaceDef();
            String lockTTL = channel.getGlobalVariables().substituteVariables(System.getProperty(ASConstants.PROP_LOCK_TTL, SpaceDef.DEFAULT_LOCK_TTL + "")).toString();
            spaceDef.setLockTTL(Long.parseLong(lockTTL));
            //logger.log(Level.INFO, "Set TTL for space:" + lockTTL);
            logger.log(Level.INFO, "%s successfully connected to Space '%s'", this.getName(), spaceName);
        }
    }

    @Override
    public void reconnect() {
        try {
            connect();
            if (spaceDef != null) {
                consumptionMode.start(condition);
            }
        } catch (Exception ex) {
            logger.log(Level.WARN, ex, "%s failed reconnecting to Space '%s'", this.getName(), spaceName);
        }
    }

    @Override
    public void start(int mode) throws Exception {
        logger.log(Level.INFO, "%s starting Space '%s'", this.getName(), spaceName);
        if (mode == ChannelManager.ACTIVE_MODE) {
            synchronized (syncObject) {
                for (QueueEntry qe : localQueue) {
                    space.put(qe.tuple);
                    localQueue.remove(qe);
                }
            }
        }
        int startMode = (ChannelManager.ACTIVE_MODE == mode) && (this.userControlled) ? ChannelManager.SUSPEND_MODE : mode;
        if (isBinded) {
            condition.put(K_AS_DEST_START_MODE, startMode);
            consumptionMode.start(condition);
        }
        super.start(mode);
        logger.log(Level.INFO, "%s started Space '%s' mode=%s start-mode=%s", this.getName(), spaceName, mode, startMode);
        if (spaceDef == null) {
            logger.log(Level.WARN, "Space definition is null! Can't start destination");
        }
    }

    @Override
    public int send(SimpleEvent event, @SuppressWarnings("rawtypes") Map overrideData) throws Exception {
        RuleSession rSession = RuleSessionManager.getCurrentRuleSession();
        Tuple tuple = (Tuple) serializer.serialize(event, new DefaultSerializationContext(rSession, this));
        if (channel.getState() == State.CONNECTED || channel.getState() == State.STARTED) {
            logger.log(Level.DEBUG, "Put tuple: " + tuple.toString() + " to space: " + spaceName);
            space.put(tuple);
        } else {
            if (channel.getState() == State.INITIALIZED) {
                channel.getLogger().log(Level.INFO, "Channel Initialized, but not started. Queuing message for later delivery");

                synchronized (this.syncObject) {
                    QueueEntry localQueueEntry = new QueueEntry(tuple);
                    localQueue.add(localQueueEntry);
                }
                return 1;
            }
        }
        return super.send(event, overrideData);
    }

    @Override
    public void suspend() {
        logger.log(Level.INFO, "Suspend the destination: %s", getURI());
        try {
            if (isBinded) {
                consumptionMode.suspend();
            }
            super.suspend();
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Failed to suspend the destination: %s", getURI());
        }
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, "Stop the destination: %s", getURI());
        try {
            if (isBinded) {
                consumptionMode.stop();
            }
            super.stop();
        } catch (Exception e) {
            logger.log(Level.WARN, e, "Failed to stop the destination: %s", getURI());
        }
    }

    @Override
    public void resume() {
        logger.log(Level.INFO, "Resume the destination: %s", getURI());
        if (!this.userControlled) {
            try {
                if (isBinded) {
                    condition.put(K_AS_DEST_START_MODE, ChannelManager.ACTIVE_MODE);
                    consumptionMode.resume();
                }
                super.resume();
            } catch (Exception e) {
                logger.log(Level.WARN, e, "Failed to resume the destination: %s", getURI());
            }
        }
    }

    @Override
    public void close() {
        logger.log(Level.INFO, "%s closing Space '%s'", this.getName(), spaceName);
        if (null != consumptionMode) {
            // destroy() won't be called if this is not an input destination,
            // so we must ensure that close consumptionMode instance.
            try {
                consumptionMode.close();
            } catch (Exception ex) {
                logger.log(Level.ERROR, ex, "%s failed closing consumption mode: '%s'", this.getName(), spaceName);
            } finally {
                consumptionMode = null;
            }
        }

        if (null != space) {
            try {
                space.close();
                logger.log(Level.INFO,
                        ResourceManager.getInstance().formatMessage("channel.destination.close", getURI()));
            } catch (Exception ex) {
                logger.log(Level.ERROR, ex, "Close destination failed: " + spaceName);
            } finally {
                space = null;
                spaceDef = null;
            }
        }
    }

    @Override
    protected void destroy(RuleSession session) throws Exception {
        logger.log(Level.INFO, "%s destroying Space '%s'", this.getName(), spaceName);
        try {
            if (null != consumptionMode) {
                consumptionMode.close();
            }
        } finally {
            consumptionMode = null;
        }
    }

    @Override
    public Space getSpace() {
        return space;
    }

    @Override
    public SpaceDef getSpaceDef() {
        return spaceDef;
    }

    public DistributionRole getDistributionRole() {
        return distributionRole;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    public ASChannel getChannel() {
        return channel;
    }

    private class QueueEntry {
        private Tuple tuple;

        QueueEntry(Tuple tuple) {
            this.tuple = tuple;
        }
    }

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
		throw new Exception("requestEvent() is not supported with AS Channel");
	}
}
