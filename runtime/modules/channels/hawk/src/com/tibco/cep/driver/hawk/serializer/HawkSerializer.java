package com.tibco.cep.driver.hawk.serializer;

import java.util.EventObject;
import java.util.Properties;

import COM.TIBCO.hawk.console.hawkeye.AgentMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.ClearAlertEvent;
import COM.TIBCO.hawk.console.hawkeye.ErrorExceptionEvent;
import COM.TIBCO.hawk.console.hawkeye.MicroAgentListMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.PostAlertEvent;
import COM.TIBCO.hawk.console.hawkeye.RuleBaseListMonitorEvent;
import COM.TIBCO.hawk.console.hawkeye.RuleBaseStatus;
import COM.TIBCO.hawk.console.hawkeye.WarningExceptionEvent;
import COM.TIBCO.hawk.talon.CompositeData;
import COM.TIBCO.hawk.talon.DataElement;
import COM.TIBCO.hawk.talon.TabularData;

import com.tibco.cep.driver.hawk.HawkConstants;
import com.tibco.cep.driver.hawk.HawkDestination;
import com.tibco.cep.driver.hawk.internal.HawkMonitorEvent;
import com.tibco.cep.driver.hawk.util.HawkUtil;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.EventSerializer;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;

public class HawkSerializer implements EventSerializer {

	protected Logger logger;

	@Override
	public SimpleEvent deserialize(Object message, SerializationContext context) throws Exception {
		HawkDestination dest = (HawkDestination) context.getDestination();
		RuleSession session = context.getRuleSession();
		DestinationConfig config = dest.getConfig();
		ExpandedName en = config.getDefaultEventURI();
		SimpleEvent event = (SimpleEvent) session.getRuleServiceProvider().getTypeManager().createEntity(en);

		String[] propertynames = event.getPropertyNames();
		Properties props = new Properties();

		if (message instanceof HawkMonitorEvent) {
			String eventType = ((HawkMonitorEvent) message).getEventType();
			props.setProperty(HawkConstants.HAWK_EVENT_TYPE, eventType);
			EventObject me = ((HawkMonitorEvent) message).getEvent();

			if (me instanceof PostAlertEvent) {
				PostAlertEvent pae = (PostAlertEvent) me;
				String agentName = pae.getAgentInstance().getAgentID().getName();
				String alertText = pae.getAlertText();
				Properties paeProps = pae.getProperties();
				props.putAll(paeProps);
				props.setProperty(HawkConstants.PROP_AGENT_NAME, agentName);
				props.setProperty(HawkConstants.PROP_ALERT_TEXT, alertText);

			} else if (me instanceof ClearAlertEvent) {
				ClearAlertEvent cae = (ClearAlertEvent) me;
				String agentName = cae.getAgentInstance().getAgentID().getName();
				String alertText = cae.getReasonClearedText();
				String ruleBase = cae.getRuleBaseStatus().getName();
				props.setProperty(HawkConstants.PROP_AGENT_NAME, agentName);
				props.setProperty(HawkConstants.PROP_ALERT_TEXT, alertText);
				props.setProperty(HawkConstants.PROP_RULEBASE, ruleBase);

			} else if (me instanceof RuleBaseListMonitorEvent) {
				RuleBaseListMonitorEvent rbme = (RuleBaseListMonitorEvent) me;
				RuleBaseStatus rbs = rbme.getRuleBaseStatus();

				String agentName = rbme.getAgentInstance().getAgentID().getName();
				String name = rbs.getName();
				String state = String.valueOf(rbs.getState());

				props.setProperty(HawkConstants.PROP_AGENT_NAME, agentName);
				props.setProperty(HawkConstants.PROP_RULEBASE_NAME, name);
				props.setProperty(HawkConstants.PROP_RULEBASE_STATE, state);

			} else if (me instanceof AgentMonitorEvent) {
				AgentMonitorEvent ame = (AgentMonitorEvent) me;
				String agentName = ame.getAgentInstance().getAgentID().getName();
				String cluster = ame.getAgentInstance().getCluster();
				String ip = ame.getAgentInstance().getIPAddress();
				props.setProperty(HawkConstants.PROP_AGENT_NAME, agentName);
				props.setProperty(HawkConstants.PROP_AGENT_CLUSTER, cluster);
				props.setProperty(HawkConstants.PROP_AGENT_IP, ip);

			} else if (me instanceof MicroAgentListMonitorEvent) {
				MicroAgentListMonitorEvent male = (MicroAgentListMonitorEvent) me;
				String agentName = male.getMicroAgentID().getAgent().getName();
				String microAgentName = male.getMicroAgentID().getName();
				String displayName = male.getMicroAgentID().getDisplayName();

				props.setProperty(HawkConstants.PROP_AGENT_NAME, agentName);
				props.setProperty(HawkConstants.PROP_MICROAGENT_NAME, microAgentName);
				props.setProperty(HawkConstants.PROP_MICROAGENT_DISPLAYNAME, displayName);
			} else if (me instanceof ErrorExceptionEvent) {
				ErrorExceptionEvent ee = (ErrorExceptionEvent) me;
				String errorMessage = ee.getConsoleError().getMessage();
				String exceptionMessage = ee.getConsoleException().getMessage();

				props.setProperty(HawkConstants.PROP_ERROR_MESSAGE, errorMessage);
				props.setProperty(HawkConstants.PROP_EXCEPTION_MESSAGE, exceptionMessage);

			} else if (me instanceof WarningExceptionEvent) {
				WarningExceptionEvent we = (WarningExceptionEvent) me;
				String warningMessage = we.getConsoleWarning().getMessage();
				String exceptionMessage = we.getConsoleException().getMessage();

				props.setProperty(HawkConstants.PROP_WARNING_MESSAGE, warningMessage);
				props.setProperty(HawkConstants.PROP_EXCEPTION_MESSAGE, exceptionMessage);
			}

		} else if (message instanceof CompositeData) {
			CompositeData compData = (CompositeData) message;
			DataElement[] data = compData.getDataElements();

			for (int i = 0; i < data.length; i++) {
				props.put(HawkUtil.formatEventName(data[i].getName()), data[i].getValue());
			}

		}
		// it could be TabularData
		else if (message instanceof TabularData) {
			TabularData tabData = (TabularData) message;

			String[] columnNames = tabData.getColumnNames();
			// alternatively you can use getAllDataElements() as well
			Object[] data = tabData.getAllData()[0];

			for (int i = 0; i < columnNames.length; i++) {
				props.put(HawkUtil.formatEventName(columnNames[i]), data[i]);
			}
		}

		// match properties to event properties
		for (String pname : propertynames) {
			Object value = props.get(pname);
			if (value != null) {
				event.setProperty(pname, value);
			}
		}
		return event;
	}

	public void init(ChannelManager channelManager, DestinationConfig arg1) {
		this.logger = channelManager.getRuleServiceProvider().getLogger(this.getClass());
	}

	public Object serialize(SimpleEvent arg0, SerializationContext arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
