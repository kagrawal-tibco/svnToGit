/**
 *
 */
package com.tibco.be.bemm.functions;

import java.io.IOException;

import COM.TIBCO.hawk.console.hawkeye.AgentManager;
import COM.TIBCO.hawk.console.hawkeye.ConsoleInitializationException;
import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * @author anpatil
 *
 */
public class RemoteMetricsCollectorHawkImpl extends RemoteMetricsCollectorImpl {    //todo changed to public

	private static final String PROP_KEY_MICRO_AGENT_DIRECTED_SEARCH = "be.mm.hawk.microagentsearch.direct";

    //hawk specific information
	private TIBHawkConsole hawkConsole;
	private AgentManager agentManager;

	private boolean directMicroAgentSearch;

	RemoteMetricsCollectorHawkImpl(String monitoredEntityName, COLLECTOR_TYPE type, String[] properties) {
        super(type, properties, monitoredEntityName);
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        directMicroAgentSearch = Boolean.parseBoolean(currRuleServiceProvider.getProperties().getProperty(PROP_KEY_MICRO_AGENT_DIRECTED_SEARCH, "true"));
	}

    protected boolean initConnection() {
		if (collectorType == COLLECTOR_TYPE.HAWK){
			try {
				hawkConsole = new TIBHawkConsole(entityProperties.getHawkDomain(), entityProperties.getService(), entityProperties.getNetwork(), entityProperties.getDaemon());
				agentManager = hawkConsole.getAgentManager();
				agentManager.initialize();
			} catch (ConsoleInitializationException e) {
				if (logger.isEnabledFor(Level.DEBUG)){
					logger.log(Level.DEBUG, "could not connect to "+monitoredEntityName+" using HAWK",e);
				}
				else {
					logger.log(Level.WARN, "could not connect to "+monitoredEntityName+" using HAWK");
				}
				return false;
			}
		}
		else if (collectorType == COLLECTOR_TYPE.CACHE){
			//no initialization needed for cache connection
		}
		return true;
	}

    protected void closeConnection() {
    	try{
    		if (collectorType == COLLECTOR_TYPE.HAWK){
                    agentManager.shutdown();
                    agentManager = null;
                    hawkConsole = null;
    		}
    	}
    	catch(Exception e){}
    }

    protected MetricTypeHandler getHandler(String type) {
		try {
			MetricTypeHandler handler = typeHandlerFactory.getHandler(type);
			handler.setLogger(logger);
			handler.setMonitoredEntityName(monitoredEntityName);
			handler.setDelay(frequency);
			if (collectorType == COLLECTOR_TYPE.HAWK){
				HAWKMetricTypeHandler hawkHandler = (HAWKMetricTypeHandler) handler;
				hawkHandler.setDomainName(entityProperties.getHawkDomain());
				hawkHandler.setAgentManager(agentManager);
				hawkHandler.setDirectedSearch(directMicroAgentSearch);
			}
			else if (collectorType == COLLECTOR_TYPE.CACHE){
				//do nothing;
			}
			handler.init();
			return handler;
		} catch (IOException e) {
			if (logger.isEnabledFor(Level.DEBUG)){
				logger.log(Level.DEBUG, "could not initialize "+collectorType+" agent(s) for "+monitoredEntityName+"...",e);
			}
			else {
				logger.log(Level.WARN, "could not initialize "+collectorType+" agent(s) for "+monitoredEntityName+"...");
			}
			return null;
		}
	}

}
