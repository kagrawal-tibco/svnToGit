package com.tibco.cep.driver.hawk;

import com.tibco.xml.data.primitive.ExpandedName;

public interface HawkConstants {

	final static ExpandedName XML_NODE_CONFIG = ExpandedName.makeName("config");

	static final String CHANNEL_DRIVER_HAWK = "Hawk";

	static final String CHANNEL_PROPERTY_DESCRIPTION = "Description";
	static final String CHANNEL_PROPERTY_TRANSPORT = "Transport";
	static final String CHANNEL_PROPERTY_HAWKDOAMIN = "Domain";
	// rv parameters
	static final String CHANNEL_PROPERTY_RVSERVICE = "RV_Service";
	static final String CHANNEL_PROPERTY_RVNETWORK = "RV_Network";
	static final String CHANNEL_PROPERTY_RVDAEMON = "RV_Daemon";
	// ems parameters
	static final String CHANNEL_PROPERTY_SERVER_URL = "EMS_ServerURL";
	static final String CHANNEL_PROPERTY_USERNAME = "EMS_UserName";
	static final String CHANNEL_PROPERTY_PASSWORD = "EMS_Password";

	static final String TRANSPORT_TYPE_RV = "RendezVous";
	static final String TRANSPORT_TYPE_EMS = "EMS";

	static final String HAWK_SERIALIZER = "com.tibco.cep.driver.hawk.serializer.HawkSerializer";

	static final String DESTINATION_PROP_MONITORTYPE = "MonitorType";
	static final String DESTINATION_PROP_SUBSCRIPTION = "SubscriptionMethodURI";
	static final String DESTINATION_PROP_IS_SYNCHRONIZED = "isSynchronized";
	static final String DESTINATION_PROP_TIMEINTERVAL = "TimeInterval";
	static final String DESTINATION_PROP_ARGUMENTS = "Arguments";

	final static String MONITOR_TYPE_ALERT = "AlertMonitor";
	final static String MONITOR_TYPE_RULEBASE = "RuleBaseListMonitor";
	final static String MONITOR_TYPE_AGENT = "AgentMonitor";
	final static String MONITOR_TYPE_MICROAGENT = "MicroAgentListMonitor";
	final static String MONITOR_TYPE_ERROR = "ErrorMonitor";
	final static String MONITOR_TYPE_WARNING = "WarningMonitor";
	final static String MONITOR_TYPE_SUBSCRIPTION = "MicroAgent Method Subscription";

	final static String PROP_ALERT_TEXT = "AlertString";
	static final String PROP_RULEBASE = "RuleBase";
	final static String PROP_RULEBASE_NAME = "RuleBaseName";
	final static String PROP_RULEBASE_STATE = "RuleBaseState";

	final static String PROP_AGENT_NAME = "AgentName";
	final static String PROP_AGENT_CLUSTER = "Cluster";
	final static String PROP_AGENT_IP = "IP";

	final static String PROP_MICROAGENT_NAME = "MicroAgentName";
	final static String PROP_MICROAGENT_DISPLAYNAME = "DisplayName";

	final static String PROP_ERROR_MESSAGE = "ErrorMessage";
	final static String PROP_WARNING_MESSAGE = "WarningMessage";
	final static String PROP_EXCEPTION_MESSAGE = "ExcetpionMessage";

	final static String PROP_TIMEINTERVAL = "TimeInterval";

	final static String ESCAPE_CHAR_AND = "&";
	final static String ESCAPE_CHAR_SPACE = " ";
	final static String ESCAPE_CHAR_SLASH = "/";
	final static String ESCAPE_CHAR_PERCENT = "%";
	
	final static String CHAR_EMPTY = "";
	final static String REPLACE_CHAR_AND = "AND";
	final static String REPLACE_CHAR_SPACE = "_";
	final static String REPLACE_CHAR_SLASH = "PER";
	final static String REPLACE_CHAR_PERCENT = "PERCENTAGE_";

	// hawk event type
	final static String HAWK_EVENT_TYPE = "HawkEventType";
	final static String EVENT_AGENT_ALIVE = "AgentAliveEvent";
	final static String EVENT_AGENT_EXPIRED = "AgentExpiredEvent";
	final static String EVENT_ALERT_MONITOR = "AlertMonitorEvent";
	static final String EVENT_MICROAGENT_ADDED = "MicroAgentAddedEvent";
	static final String EVENT_MICROAGENT_REMOVED = "MicroAgentRemovedEvent";
	static final String EVENT_RULEBASE_ADDED = "RuleBaseAddedEvent";
	static final String EVENT_RULEBASE_REMOVEED = "RuleBaseRemovedEvent";
	static final String EVENT_ERROR = "ERROR";
	static final String EVENT_WARNING = "WARNING";

}
