package com.tibco.cep.bemm.monitoring.metric.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.service.exception.ServiceInitializationException;
import com.tibco.cep.bemm.monitoring.metric.probe.ProbeContainer;
import com.tibco.cep.bemm.monitoring.metric.service.AggregationService;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaConnection;
import com.tibco.rta.RtaConnectionFactory;
import com.tibco.rta.RtaConnectionFactoryEx;
import com.tibco.rta.RtaSession;
import com.tibco.rta.engine.RtaEngine;
import com.tibco.rta.engine.RtaEngineExFactory;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.util.ServiceConstants;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

public class AggregationServiceImpl implements AggregationService {
 
	private RtaEngine engine = null;
	
	private String defaultSchemaName = null;
	
	private RtaSchema defaultSchema = null;
	
	private RtaSession session = null;
	
	private String pluginDir = null;
	
	private ProbeContainer probeContainer=null;
	
	@Override
	public void init(Properties configuration) throws ServiceInitializationException {
		try {
			//RTA properties file
			/*String rtaConfigFile = (String) ConfigProperty.TEA_AGENT_BE_RTA_CONFIG_FILE.getValue(configuration);
			Properties rtaProperties = new Properties();
			//Load the RTA engine properties file
			rtaProperties.load(new FileInputStream(new File(rtaConfigFile)));
			*/
			//Adding rule store location in the RtaProperties
			String repoLocation=(String) ConfigProperty.BE_TEA_AGENT_APPLICATION_DATASTORE.getValue(configuration);
			String ruleStore=repoLocation+File.separator+BETeaAgentProps.BE_DATASTORE_RULE_FOLDER;
			
			//Setting properties required fo InMemoryRule Persistence
			configuration.setProperty(ServiceConstants.RULES_DATASTORE, ruleStore);
			configuration.setProperty(ServiceConstants.RULE_FILE_STORE_EXTENSION, BETeaAgentProps.BE_DATASTORE_RULE_EXTENSION);
			configuration.setProperty(ServiceConstants.RULE_APP_NAME_SEPERATOR, BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			//Initialize the engine
			this.engine = RtaEngineExFactory.getInstance().getEngine();
			this.engine.init(configuration);
			this.defaultSchemaName = (String) ConfigProperty.BE_TEA_AGENT_RTA_DEFAULT_SCHEMA.getValue(configuration);
			this.pluginDir = (String) ConfigProperty.BE_TEA_AGENT_METRICS_PROBE_PLUGIN_DIR.getValue(configuration);
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ServiceInitializationException(ex);
		}
	}
	
	@Override
	public void start() throws Exception {
		engine.start();
		onStart();
	}
	
	private void onStart() throws Exception {
		initSession();
		//Get the default schema instance 
		this.defaultSchema = session.getSchema(defaultSchemaName);
		initProbe();
	}
	

	private void initProbe() {
		probeContainer=new ProbeContainer(pluginDir);
		probeContainer.start();
	}

	private void initSession() throws Exception {
		RtaConnectionFactory connectionFac = new RtaConnectionFactoryEx();
		Map<com.tibco.rta.ConfigProperty, PropertyAtom<?>> configurationMap = new HashMap<>();
		RtaConnection rtaConnection = connectionFac.getConnection("local", "", "", configurationMap);
        String sessionName = UUID.randomUUID().toString();
        if (session == null) {
            session = (RtaSession) rtaConnection.createSession(sessionName, configurationMap);
            session.init();
            session.getAllActionFunctionDescriptors();
            //session.setCommandListener(new RuleCommandListener());
        }
	}
	
	@Override
	public RtaSession getSession() throws Exception {
		checkService();
		return session;
	}	
	
	
	public Fact createFact() throws Exception {
		checkService();
		if (defaultSchema == null) {
			throw new Exception("Default schema is not defined");
		}
		return defaultSchema.createFact();
	}
	
	@Override
	public Fact createFact(String schemaName) throws Exception {
		checkService();
		RtaSchema schema=session.getSchema(schemaName);
		if (schema == null) {
			throw new Exception("Given schema name not defined");
		}
		return schema.createFact();
	}
	
	
	@Override
	public void stop() {
		probeContainer.stopAll();
		engine.stop();
	}

	private void checkService() throws Exception {
		if (this.engine != null && !this.engine.isStarted()) {
			throw new Exception("Aggregation service is not running.");
		}		
	}
	@Override
	public ProbeContainer getProbeContainer() {
		return probeContainer;
	}
	
	@Override
	public  RtaSchema getDefaultSchema() {
		return defaultSchema;
	}
	
	
}
