/**
 * 
 */
package com.tibco.be.bemm.functions;

import java.util.Arrays;

import com.tibco.be.bemm.functions.RemoteMetricsCollector.COLLECTOR_TYPE;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author anpatil
 *
 */
public class TopologyEntityProperties {
	
	private Logger logger;
	
	private String monitoredEntityName;
	
	private String[] properties;
	
	private String hostname;

	private int port;	
	
	private String hawkDomain;
	private String service;
	private String daemon;
	private String network;
	
	
	public TopologyEntityProperties(Logger logger,String monitoredEntityName, String[] properties) {
		this.logger = logger;
		this.monitoredEntityName = monitoredEntityName;
		this.properties = properties;
		port = -1;
	}	
	
	public boolean parse(COLLECTOR_TYPE collectorType){
		if (collectorType == COLLECTOR_TYPE.JMX) {
			if (properties.length < 3) {
				logger.log(Level.WARN, "Invalid set of properties " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
			if (properties[2] == null || properties[2].trim().length() == 0) {
				logger.log(Level.WARN, "No JMX properties specified in " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
			String[] managementURIElements = properties[2].split(",");
			if (managementURIElements.length < 2) {
				logger.log(Level.WARN, "Incomplete JMX properties specified in " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
			String[] hostNameSplit = managementURIElements[0].split("=");
			if (hostNameSplit.length != 2) {
				logger.log(Level.WARN, "Incomplete hostname parameter in JMX properties specified in " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
			this.hostname = hostNameSplit[1];
			String[] portNumberSplit = managementURIElements[1].split("=");
			if (hostNameSplit.length != 2) {
				logger.log(Level.WARN, "Incomplete portnumber parameter in JMX properties specified in " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
			try {
				this.port = Integer.parseInt(portNumberSplit[1]);
			} catch (NumberFormatException e) {
				logger.log(Level.WARN, "Invalid portnumber in JMX properties specified in " + Arrays.asList(properties) + " for " + monitoredEntityName);
				return false;
			}
		}
        String hawkHome = System.getProperty("tibco.env.HAWK_HOME");
		if (collectorType == COLLECTOR_TYPE.HAWK && hawkHome != null && hawkHome.trim().length() != 0){
			//getting default hawk properties from BEMM GVs
            GlobalVariables globalVariables = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getGlobalVariables();
            hawkDomain = globalVariables.getVariableAsString(SystemProperty.RV_DOMAIN.getPropertyName(), null);
            service = globalVariables.getVariableAsString(SystemProperty.HAWK_SERVICE.getPropertyName(), null);
            daemon = globalVariables.getVariableAsString(SystemProperty.HAWK_DAEMON.getPropertyName(), null);
            network = globalVariables.getVariableAsString(SystemProperty.HAWK_NETWORK.getPropertyName(), null);

            logger.log(Level.INFO, "Use hawk properties: domain=" + hawkDomain + ", service=" + service
                        + ", network=" + network + ", daemon=" + daemon);
        }
        return true;
	}

	public String getHostName() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	String getHawkDomain() {
		return hawkDomain;
	}

	String getService() {
		return service;
	}

	String getDaemon() {
		return daemon;
	}

	String getNetwork() {
		return network;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(TopologyEntityProperties.class.getSimpleName());
		builder.append("[");
		if (hostname != null && port != -1){
			builder.append("hostname="+hostname);
			builder.append(",port="+port);
		}
		else {
			builder.append("hawkdomain="+hawkDomain);
			builder.append(",service="+service);
			builder.append(",daemon="+daemon);
			builder.append(",network="+network);
		}
		builder.append("]");
		return builder.toString();
	}

//    //test
//    public static void main (String[] args) {
//
//        }


}//class

