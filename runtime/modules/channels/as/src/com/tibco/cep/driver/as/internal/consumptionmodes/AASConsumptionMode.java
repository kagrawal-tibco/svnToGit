package com.tibco.cep.driver.as.internal.consumptionmodes;

import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_CONSUMPTION_MODE_CONDITION_SESSION;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_FILTER;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_START_MODE;

import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import com.tibco.cep.driver.as.IASDestination;
import com.tibco.cep.driver.as.internal.consumers.IASPayloadConsumer;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.session.RuleSession;

abstract class AASConsumptionMode implements IASConsumptionMode {

	protected IASDestination		asDest;
	protected Logger				logger;
	protected IASPayloadConsumer[]	consumers;
	protected RuleSession			session;
	protected String				filter;
	protected Queue<Object>         payloadQueue;
	protected boolean               initialized;
	public Status                   status;
	
	//Added to support ChannelFunction api resume and suspend. This is updated in the subclasses.
	// After browser/lister/router starts browsing/listening/routing we never stop them. Suspending will just put the entries from them in the payloadQueue
	protected boolean isAttachedToSpace;


	protected AASConsumptionMode(IASDestination asDest, Logger logger, IASPayloadConsumer ... consumers) {
		this.asDest = asDest;
		this.logger = logger;
		this.consumers = consumers;
		this.status = Status.init;
		isAttachedToSpace = false;
	}

	@Override
	public final void start(Map<Object, Object> condition) throws Exception {
	    Status oldstatus = status;
	    if (null == session) {
	        this.session = (RuleSession) condition.get(K_AS_DEST_CONSUMPTION_MODE_CONDITION_SESSION);
	    }
	    if (null == filter) {
    		DestinationConfig config = (DestinationConfig) condition.get(K_AS_DEST_CONSUMPTION_MODE_CONDITION_DESTINATION_CONFIG);
            Properties ps = config.getProperties();
            GlobalVariables gvs = this.session.getRuleServiceProvider().getGlobalVariables();
            CharSequence cs = gvs.substituteVariables(ps.getProperty(K_AS_DEST_PROP_FILTER));
            this.filter = (cs == null ? "" : cs.toString());
	    }
	    if (null == payloadQueue) {
	        payloadQueue = new LinkedList<Object>();
	    }
		doStart(condition);
        int startMode = (Integer) condition.get(K_AS_DEST_START_MODE);
        if (startMode == ChannelManager.SUSPEND_MODE) {
            status = Status.suspended;
        } else if (startMode == ChannelManager.ACTIVE_MODE) {
            status = Status.running;
            if (oldstatus.equals(Status.suspended) == true) {
            	// Consume any messages queued during suspend
                resume();
            }
        }
	}

	/**
	 * Consume payload
	 * @param payload
	 * @throws Exception
	 */
	public void consume(Object payload) throws Exception {
		if (status.equals(Status.suspended)) {
			payloadQueue.offer(payload);
		} else if (status.equals(Status.running)) {
			for (IASPayloadConsumer consumer : consumers) {
				if (consumer.consume(payload, asDest, session, logger)) {
					break;
				}
			}
		}
	}

	@Override
	public void suspend() throws Exception {
		status = Status.suspended;
	}

	@Override
	final public void resume() throws Exception {
        if (status.equals(Status.stopped) || status.equals(Status.suspended)) {
            if(!isAttachedToSpace){
            	doResume();
            }
            status = Status.running;
        } else {
            if (initialized) {
                status = Status.running;
            }
        }
		if (Status.running == status) {
    		Object payload = null;
    		while((payload = payloadQueue.poll()) != null) {
    			consume(payload);
    		}
		}
	}
	
	@Override
	public void stop() throws Exception {
		status = Status.stopped;
		isAttachedToSpace = false;
	}
	
	@Override
	public void close() throws Exception {
		stop();
		status = Status.closed;
	}

	abstract protected void doStart(Map<Object, Object> condition) throws Exception;
	abstract protected void doResume() throws Exception ;
	
    public static enum Status {
    	init,running,suspended,stopped,closed
    }
}
