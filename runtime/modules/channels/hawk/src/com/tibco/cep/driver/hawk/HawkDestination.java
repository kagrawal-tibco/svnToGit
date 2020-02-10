package com.tibco.cep.driver.hawk;

import java.util.Map;
import java.util.Properties;
import java.util.Queue;

import COM.TIBCO.hawk.console.hawkeye.AgentMonitor;
import COM.TIBCO.hawk.talon.MicroAgentException;
import COM.TIBCO.hawk.talon.Subscription;

import com.tibco.cep.driver.hawk.internal.listeners.AgentListener;
import com.tibco.cep.driver.hawk.internal.listeners.AlertListener;
import com.tibco.cep.driver.hawk.internal.listeners.ErrorListener;
import com.tibco.cep.driver.hawk.internal.listeners.HawkListener;
import com.tibco.cep.driver.hawk.internal.listeners.HawkSubscriber;
import com.tibco.cep.driver.hawk.internal.listeners.MicroAgentListener;
import com.tibco.cep.driver.hawk.internal.listeners.RuleBaseListener;
import com.tibco.cep.driver.hawk.internal.listeners.WarningListener;
import com.tibco.cep.driver.hawk.util.SubscriptionUtil;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.hawk.jshma.plugin.HawkConsoleBase;
import com.tibco.xml.data.primitive.ExpandedName;

public class HawkDestination extends AbstractDestination<HawkChannel> {
	private RuleSession session;
	private Logger logger;
	private String monitorType;
	private boolean isMonitor = true;
	private boolean isBinded = false;
	private HawkListener eventListener;
	private Subscription subscription;
	private String subscriptionMethodURI, timeInterval, arguments;

	public HawkDestination(HawkChannel channel, DestinationConfig config) {
		super(channel, config);
		logger = channel.getLogger();
		Properties props = config.getProperties();
		monitorType = props.getProperty(HawkConstants.DESTINATION_PROP_MONITORTYPE);
		logger.log(Level.INFO, "MonitorType: " + monitorType);
		if (HawkConstants.MONITOR_TYPE_SUBSCRIPTION.equals(monitorType)) {
			isMonitor = false;
			subscriptionMethodURI = getSubstitutedPropertyValue(HawkConstants.DESTINATION_PROP_SUBSCRIPTION, false);
			timeInterval = props.getProperty(HawkConstants.DESTINATION_PROP_TIMEINTERVAL);
			arguments = props.getProperty(HawkConstants.DESTINATION_PROP_ARGUMENTS);
		}
	}

	@Override
	public void connect() throws Exception {
		// do nothing
	}

	@Override
	public void bind(RuleSession session) throws Exception {
		// logger.log(Level.INFO, "This destination is input destination");
		this.session = session;
		isBinded = true;
		
		//BE-26535: As destination is bound again on reconnect(jmx),
		//a null check is required to avoid creating listener again
		if(eventListener == null) {
			if (isMonitor) {
				if (monitorType.equals(HawkConstants.MONITOR_TYPE_ALERT)) {
					eventListener = new AlertListener(this, session, logger);
				} else if (monitorType.equals(HawkConstants.MONITOR_TYPE_RULEBASE)) {
					eventListener = new RuleBaseListener(this, session, logger);
				} else if (monitorType.equals(HawkConstants.MONITOR_TYPE_AGENT)) {
					eventListener = new AgentListener(this, session, logger);
				} else if (monitorType.equals(HawkConstants.MONITOR_TYPE_MICROAGENT)) {
					eventListener = new MicroAgentListener(this, session, logger);
				} else if (monitorType.equals(HawkConstants.MONITOR_TYPE_ERROR)) {
					eventListener = new ErrorListener(this, session, logger);
				} else if (monitorType.equals(HawkConstants.MONITOR_TYPE_WARNING)) {
					eventListener = new WarningListener(this, session, logger);
				}
			} else {
				eventListener = new HawkSubscriber(this, session, logger);
			}
		}
		super.bind(session);
	}

	@Override
	public void start(int mode) throws Exception {
		if (mode == ChannelManager.SUSPEND_MODE) {
			if (isBinded) {
				eventListener.mode = mode;
				logger.log(Level.INFO, "Start Destination: " + getURI());
				if (isMonitor) {
					logger.log(Level.INFO, "MonitorType: " + monitorType);
					AgentMonitor agentMonitor = ((HawkChannel) channel).getHawkConsole().getAgentMonitor();
					if (eventListener instanceof AlertListener) {
						agentMonitor.addAlertMonitorListener((AlertListener) eventListener);
					} else if (eventListener instanceof RuleBaseListener) {
						agentMonitor.addRuleBaseListMonitorListener((RuleBaseListener) eventListener);
					} else if (eventListener instanceof AgentListener) {
						agentMonitor.addAgentMonitorListener((AgentListener) eventListener);
					} else if (eventListener instanceof MicroAgentListener) {
						agentMonitor.addMicroAgentListMonitorListener((MicroAgentListener) eventListener);
					} else if (eventListener instanceof ErrorListener) {
						agentMonitor.addErrorExceptionListener((ErrorListener) eventListener);
					} else if (eventListener instanceof WarningListener) {
						agentMonitor.addWarningExceptionListener((WarningListener) eventListener);
					}
				} else {
					logger.log(Level.INFO, "Subscribe to: " + subscriptionMethodURI);
					String params[] = subscriptionMethodURI.substring(1).split("/");
					if (params != null && params.length == 3) {
						HawkConsoleBase hawkConsoleBase = channel.getHawkConsoleBase();
						try {
							subscription = SubscriptionUtil.subscribe(hawkConsoleBase, params, arguments, timeInterval,(HawkSubscriber)eventListener);
							if (subscription == null) {
								logger.log(Level.ERROR, "ERROR while Subscription!");
							}
						} catch (MicroAgentException mae) {
							logger.log(Level.ERROR, "ERROR while performing getMicroAgentIDs: " + mae);
						}
					} else {
						logger.log(Level.ERROR,
								"URI of subsctiption method is wrong! Should be /agentName/microAgentName/methodName");
					}
				}
			}
		} else if (mode == ChannelManager.ACTIVE_MODE) {
			if (isBinded) {
				eventListener.mode = mode;
				if (eventListener.eventQueue != null && !eventListener.eventQueue.isEmpty()) {
					for (Object object : eventListener.eventQueue) {
						eventListener.onMessage(object, this, session, logger);
					}					
				}
			}
		}
	}
	
	@Override
	public void suspend() {
		logger.log(Level.INFO, "Suspend the destination: "+getURI());
		try {
			if(isBinded){
				eventListener.mode = ChannelManager.SUSPEND_MODE;
			}
			super.suspend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		//BE-26535
		//Once initialized, EventListeners should not be removed from AgentMonitor
		//An AgentMonitor does not allow adding listeners after initializing
		//Also once AgentMonitor is shutdown, it does not allow any method calls
		/*try {
			if(isBinded){
				AgentMonitor agentMonitor = ((HawkChannel) channel).getHawkConsole().getAgentMonitor();
				if (eventListener instanceof AlertListener) {
					agentMonitor.removeAlertMonitorListener((AlertListener) eventListener);
				} else if (eventListener instanceof RuleBaseListener) {
					agentMonitor.removeRuleBaseListMonitorListener((RuleBaseListener) eventListener);
				} else if (eventListener instanceof AgentListener) {
					agentMonitor.removeAgentMonitorListener((AgentListener) eventListener);
				} else if (eventListener instanceof MicroAgentListener) {
					agentMonitor.removeMicroAgentListMonitorListener((MicroAgentListener) eventListener);
				} else if (eventListener instanceof ErrorListener) {
					agentMonitor.removeErrorExceptionListener((ErrorListener) eventListener);
				} else if (eventListener instanceof WarningListener) {
					agentMonitor.removeWarningExceptionListener((WarningListener) eventListener);
				}
				if (subscription != null) {
					subscription.cancel();
				}
			}
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		logger.log(Level.INFO, "Stop the destination: "+getURI());
		if(isBinded){
			eventListener.mode = ChannelManager.SUSPEND_MODE;
		}
		super.stop();
	}

	@Override
	public void resume() {
		logger.log(Level.INFO, "Resume the destination: "+getURI());
		if (!this.userControlled){
			try {
				if(isBinded){
					eventListener.mode = ChannelManager.ACTIVE_MODE;
					Queue<Object> eventQueue = eventListener.eventQueue;
					for (Object object : eventQueue) {
						eventListener.onMessage(object, this, session, logger);
					}
				}
				super.resume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		logger.log(Level.INFO, "Closing Destination: " + this.getURI());
		stop();
		super.close();
	}

	@Override
	protected void destroy(RuleSession arg0) throws Exception {
		logger.log(Level.INFO, "Destroying Destination: " + this.getURI());
		if (subscription != null) {
			subscription.cancel();
		}
		eventListener = null;
		subscription = null;
	}

	@Override
	public Object requestEvent(SimpleEvent outevent, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData)
			throws Exception {
		throw new Exception("requestEvent() is not supported with Hawk Channel");
	}
}
