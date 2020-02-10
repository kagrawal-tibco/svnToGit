package com.tibco.cep.bemm.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import com.tibco.tea.agent.be.version.be_teagentVersion;

public enum ConfigProperty {

	//Management Properties
	BE_TEA_AGENT_APPLICATION_DATASTORE("be.tea.agent.application.datastore", "repo", "Location of deployment topologies config."),
	BE_TEA_SERVER_URL("be.tea.server.url", "http://localhost:8777/tea", "Location of deployment topologies config."),
	BE_TEA_AGENT_RETRY_INTERVAL("be.tea.agent.retry.interval", "5000", "Retry interval to connect TEA server if server is not running"),
	BE_TEA_AGENT_POLLER_DELAY("be.tea.agent.poller.delay", "15000", "Application instances status poller interval (in milliseconds)"),
	BE_TEA_AGENT_EXPOSE_PYTHON_API("be.tea.agent.expose.python.api", "true", "Expose python api"),
	BE_TEA_AGENT_PORT("be.tea.agent.port", "9777", "Default port on which TEA Agent runs"),
	BE_TEA_AGENT_SSH_PRIVATEKEY_FILE("be.tea.agent.ssh.privatekey.file", null, "Private key file path for password-less SSH authentication"),
	BE_TEA_AGENT_SSH_PRIVATEKEY_PASSPHRASE("be.tea.agent.ssh.privatekey.passphrase", null, "Passphrase to private key file for password-less SSH authentication"),
	BE_TEA_AGENT_BE_LOCKING_ENABLE("be.tea.agent.be.locking.enable", "false", "Enable/disable locking."),
	BE_TEA_AGENT_BE_LOCKING_VERSION_CHECK_ENABLE("be.tea.agent.be.locking.version.check.enable", "false", "Enable/disable version checking."),
	BE_TEA_AGENT_JMX_PORT("be.tea.agent.jmx.port", "5566", "JMX port for the BE Tea Agent."),
	BE_TEA_AGENT_JMX_USE_SINGLEPORT("be.tea.agent.jmx.usesingleport", "true", "Flag specifying to use single JMX port or not"),
	BE_TEA_AGENT_JMX_USE_SSL("be.tea.agent.jmx.usessl", "false", "Flag specifying to use ssl or not"),
	
	//Monitoring Properties
	BE_TEA_AGENT_RTA_CONFIG_FILE("be.tea.agent.rta.config.file", "config/be-teagent.props", "Location of property file."),
	BE_TEA_AGENT_METRICS_PROBE_PLUGIN_DIR("be.tea.agent.rta.config.plugin.dir", "config/plugins/", "Location of plugin directory."),
	BE_TEA_AGENT_METRICS_COLLECTION_ENABLE("be.tea.agent.metrics.collection.enable", "true", "Enable/disable stats monitoring"),
	BE_TEA_AGENT_METRICS_VIEW_CONFIG_FILE("be.tea.agent.metrics.view.config.file", "config/EntityMetricViewConfig.xml", "Location of Metric View Config File"),
	BE_TEA_AGENT_METRICS_RULES_CONFIG_FILE("be.tea.agent.metrics.rules.config.file", "config/InitialRulesConfig.xml", "Location of predefined rules"),
	BE_TEA_AGENT_METRICS_RULES_ATTR_MAP_FILE("be.tea.agent.metrics.rules.attr.map.file", "config/RuleEntityAttrMap.xml", "Location of attr entity mapping file"),
	BE_TEA_AGENT_DEFAULT_RULES_ENABLE("be.tea.agent.default.rules.enable", "false", "Flag to enable/disable default/preconfigured rules"),
	BE_TEA_AGENT_METRICS_VIEW_BEENTITY_FILE("be.tea.agent.view.beentity.config.file", "config/BeEntityMap.xml", "Location of BeEntity view config file"),
	BE_TEA_AGENT_ENABLE_ADDITIONAL_VIEWS("be.tea.agent.enable.gc.charts", "false", "Flag to enable disable GC and memory pool charts."),
	BE_TEA_AGENT_SELF_METRICS_VIEW_CONFIG_FILE("be.tea.agent.self.metrics.view.config.file", "config/BeTeaAgentMetricViewConfig.xml", "Location of Be Tea Agent Metric View Config File"),
	
	
	//Internal
	BE_TEA_AGENT_RTA_DEFAULT_SCHEMA("be.tea.agent.rta.schema.name", "BE_MM", "Default schema."),
	BE_TEA_AGENT_METRICS_CONFIG_FILE("be.tea.agent.metrics.config.file", "config/EntityMetricsConfig.xml", "Location of Metrics config XML."),
	BE_TEA_AGENT_INSTANCE_PINGER_FREQUENCY("be.tea.agent.instance.pinger.frequency", "30000", "Instance health pinger frequency."),
	BE_TEA_AGENT_INSTANCE_START_TIME_THRESHOLD("be.tea.agent.instance.start.time.threshold", "120000", "Time threshold for a instance to start."),
	BE_TEA_AGENT_AUDIT_ENABLED("be.tea.agent.audit.enabled", "false", "Enable the audit of tea agent."),
	BE_TEA_AGENT_VERSION("be.tea.agent.version", be_teagentVersion.version, "Version of BE-TEA agent"),
	BE_TEA_AGENT_CONTEXT("be.tea.agent.context", "/beTeaAgent", "Context of BE-TEA agent"),
	BE_TEA_AGENT_RESOURCES_BASE("be.tea.agent.resource.base", "runtime/mm/tea/", "Location of resource of BE-TEA agent."),	
	BE_TEA_AGENT_NAME("be.tea.agent.name", "BE", "Name of BE TEA agent"),
	BE_TEA_AGENT_TYPE_DESCRIPTION("be.tea.agent.type.description", "TIBCO's event driven solution allows you to capture and analyze data in real-time", "Description of BE-TEA agent."),
	BE_TEA_AGENT_UI_RESOURCES_BASE("be.tea.agent.ui.resource.base", "ui", "Location of UI resource of BE-TEA agent."),
	BE_TEA_AGENT_CLUSTER_AS_CONN_RETRY_INTERVAL("tea.agent.cluster.as.connection.retry.interval", "5000", "Retry interval to connect to AS cluster (in milliseconds)."),
	BE_TEA_AGENT_IDLE_TIMEOUT("be.tea.agent.idle.timeout", String.valueOf(Integer.MAX_VALUE), "Idel Timeout for agent"),
	BE_TEA_AGENT_DOC_URL("be.tea.agent.doc.url", "https://docs.tibco.com/products/tibco-businessevents", "TIBCO BusinessEvents documentation URL"),
	BE_TEA_AGENT_HOST("be.tea.agent.host",ConfigProperty.getHost(), "IP address of machine on which BE-TEA agent is running"),
	BE_TEA_AGENT_HOST_REACHABLE_TIMEOUT("be.tea.agent.host.reachable.timeout","3000", "Timeout to check BE service instance host is reachable or not"),
	BE_TEA_AGENT_JSCH_TIMEOUT("be.tea.agent.ssh.connection.timeout", "30000", "Sets the timeout setting. This value is used as the socket timeout parameter, and also as the default connection timeout."),
	BE_TEA_AGENT_TEMP_FILE_DELETE_TASK_DELAY("be.tea.agent.temp.file.delete.task.delay","3600000", "Delay to run temporary file delete timer task"),
	BE_TEA_AGENT_SSH_EXCLUSIVE_SESSION_LOCK_WAIT_TIMEOUT("be.tea.agent.ssh.exclusive.session.lock.wait.timeout", "30000", "The timeout to wait to aquire lock for exclusive ssh(jsch) session."),
	BE_TEA_AGENT_SSH_MAX_RETRY("be.tea.agent.ssh.max.retry", "600", "Maximum retry to check channel is closed or not."), 
	BE_TEA_AGENT_SSH_SLEEP_TIME("be.tea.agent.ssh.sleep.time", "100", "Time in milli seconds to sleep the thread to check channel is closed or not."),
	BE_TEA_AGENT_DISPLAY_BE_HOME_ENABLED("be.tea.agent.display.be.home.enabled", "false", "Enable display of BE homes older than 5.3.0"),
	BE_TEA_AGENT_UNREGISTER_ENABLE("be.tea.agent.unregister.enable", "false", "Unregister previously registered agent"),
	BE_TEA_AGENT_MESSAGES_FILE("be.tea.agent.message.file", "config/messages/messages_en_US.properties", "Location of BE TEA Agent messages file"),
	BE_TEA_AGENT_MONITORING_APPLICATION("be.tea.agent.monitoring.application", "false", "Enable BE TEA Agent for creating only monitorable applications"),
	BE_TEA_AGENT_AUTO_REGISTRATION_ENABLED("be.tea.agent.auto.registration.enabled", "true", "Enable the auto registration of BE TEA agent"),
	BE_TEA_AGENT_LOG_LEVEL("be.tea.agent.log.level", "info", "Log level property for BETEAgent");
	// Constants
	protected final String propertyName;

	protected String defaultValue;

	protected String description;

	ConfigProperty(String propertyName, String defaultValue, String description) {
		this.propertyName = propertyName;
		this.defaultValue = defaultValue;
		this.description = description;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public Object getValue(Properties props) {
		Object value = props.get(propertyName);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	private static String getHost() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {

		}
		return "localhost";
	}

}
