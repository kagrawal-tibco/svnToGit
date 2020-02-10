/**
 *
 */
package com.tibco.be.bemm.functions;

import java.io.IOException;
import java.util.Map;

import COM.TIBCO.hawk.console.hawkeye.ConsoleInitializationException;
import COM.TIBCO.hawk.console.hawkeye.TIBHawkConsole;
import COM.TIBCO.hawk.talon.MicroAgentData;

import com.tibco.cep.kernel.service.logging.Level;

class MachinePinger extends HAWKMetricTypeHandler {

	private TopologyEntityProperties entityProperties;

	public MachinePinger(TopologyEntityProperties entityProperties) {
		super();
		this.type = "Machine-Pinger";
		this.entityProperties = entityProperties;
	}

	protected void init() throws IOException {
		hawkInit();
	}

	@Override
	protected void hawkInit() throws IOException {
		TIBHawkConsole hawkConsole = new TIBHawkConsole(entityProperties.getHawkDomain(), entityProperties.getService(), entityProperties.getNetwork(), entityProperties.getDaemon());
		this.agentManager = hawkConsole.getAgentManager();
		try {
			this.agentManager.initialize();
			this.microAgentID = findMicroAgentId(SYS_INFO_AGENT_ID);
			if (this.microAgentID != null){
				methodInvoker = new HAWKMethodInvoker(logger,agentManager,microAgentID, OS_METHOD_NAME,null, -1);
			}
			if (methodInvoker == null){
				throw new IOException("could not find "+SYS_INFO_AGENT_ID+"."+OS_METHOD_NAME+" on "+monitoredEntityName);
			}
		} catch (ConsoleInitializationException e) {
			logger.log(Level.WARN, "could not initialize AgentManager for "+monitoredEntityName,e);
			throw new IOException("could not initialize machine pinger for "+monitoredEntityName);
		}
	}

	public boolean ping(){
		MicroAgentData data = null;
		int i = 3;
		while ((data == null || data.getData() == null) && i > 0) {
			data = invoke(microAgentID, methodInvoker);
			i--;
		}
		if (data == null || data.getData() == null){
			return false;
		}
		return true;
	}

	@Override
	protected void parseData(Object data, Map<String, Object> parsedData) {
		throw new UnsupportedOperationException("parseData");
	}

}