package com.tibco.cep.driver.as.internal.consumptionmodes;

import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_DISTRIBUTION_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_TIME_SCOPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_START_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_EXPIRE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_PUT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_TAKE_EVENT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.as.space.Space;
import com.tibco.as.space.listener.Listener;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumers.IASPayloadConsumer;
import com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener.ASExpireListener;
import com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener.ASPutListener;
import com.tibco.cep.driver.as.internal.consumptionmodes.eventlistener.ASTakeListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;

class ASConsumptionModeEventListener extends AASConsumptionMode {

	private List<Listener> listeners;
	private ListenerDef listenerDef;

	ASConsumptionModeEventListener(IASDestination asDest, Logger logger, IASPayloadConsumer... consumers) {
		super(asDest, logger, consumers);
	}

	@Override
	protected void doStart(Map<Object, Object> condition) throws Exception {
        if (!initialized) {
            Space space = asDest.getSpace();
            if (null != space) {
                DestinationConfig config = (DestinationConfig) condition
                        .get(K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG);
                Properties props = config.getProperties();
                if (null == listeners) {
                    listeners = createASListeners(props);
                }

                // add listeners to space
                listenerDef = ListenerDef.create();

                String timeScope = props.getProperty(K_AS_DEST_PROP_TIME_SCOPE);
                String distributionScope = props.getProperty(K_AS_DEST_PROP_DISTRIBUTION_SCOPE);
                if (timeScope != null && !timeScope.equals("")) {
                    listenerDef.setTimeScope(ListenerDef.TimeScope.valueOf(timeScope));
                } else {
                    listenerDef.setTimeScope(ListenerDef.TimeScope.ALL);
                }
                if (distributionScope != null && !distributionScope.equals("")) {
                    listenerDef.setDistributionScope(ListenerDef.DistributionScope.valueOf(distributionScope));
                } else {
                    listenerDef.setDistributionScope(ListenerDef.DistributionScope.ALL);
                }
                initialized = true;
            }
        }

    	int startMode = (int) condition.get(K_AS_DEST_START_MODE);
        if (initialized && startMode == ChannelManager.ACTIVE_MODE) {
            doResume();
        }
	}

	private List<Listener> createASListeners(Properties props) {
		String timeScope = props.getProperty(K_AS_DEST_PROP_TIME_SCOPE);
		String distributionScope = props.getProperty(K_AS_DEST_PROP_DISTRIBUTION_SCOPE);
		List<Listener> listeners = new ArrayList<Listener>();
		if (Boolean.valueOf(props.getProperty(K_V_AS_DEST_PROP_PUT_EVENT, "false"))) {
			listeners.add(new ASPutListener(this, logger));
            logger.log(Level.INFO, "AS Channel created event listener for %s type=%s filter=%s time-scope=%s dist-scope=%s",
                    asDest.getSpace().getName(), K_V_AS_DEST_PROP_PUT_EVENT, filter, timeScope, distributionScope);
		}
		if (Boolean.valueOf(props.getProperty(K_V_AS_DEST_PROP_TAKE_EVENT, "false"))) {
			listeners.add(new ASTakeListener(this, logger));
            logger.log(Level.INFO, "AS Channel created event listener for %s type=%s filter=%s time-scope=%s dist-scope=%s",
                    asDest.getSpace().getName(), K_V_AS_DEST_PROP_TAKE_EVENT, filter, timeScope, distributionScope);
		}
		if (Boolean.valueOf(props.getProperty(K_V_AS_DEST_PROP_EXPIRE_EVENT, "false"))) {
			listeners.add(new ASExpireListener(this, logger));
            logger.log(Level.INFO, "AS Channel created event listener for %s type=%s filter=%s time-scope=%s dist-scope=%s",
                    asDest.getSpace().getName(), K_V_AS_DEST_PROP_EXPIRE_EVENT, filter, timeScope, distributionScope);
		}
		return listeners;
	}

	@Override
	public void stop() throws Exception {
	    Space space = asDest.getSpace();
	    if (null != space) {
    		for (Listener l : listeners) {
    			space.stopListener(l);
    		}
	    }
		super.stop();
	}

	@Override
	protected void doResume() throws Exception {
        Space space = asDest.getSpace();
        for (Listener l : listeners) {
        	space.listen(l, listenerDef, filter);
            logger.log(Level.INFO, "AS Channel event listener '%s' started listening with query-limit=%s", 
                    space.getName(), listenerDef.getQueryLimit());
        }
        isAttachedToSpace = true;
	}
}
