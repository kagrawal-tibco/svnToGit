package com.tibco.cep.dashboard.integration.be;

import java.lang.management.ManagementFactory;

import javax.management.ObjectName;

import com.tibco.be.functions.event.EventHelper;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementConfigurator;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementService;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.BEArchiveResource;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

public class DashboardSessionImpl extends RuleSessionImpl implements DashboardSession {

	private ManagementService managementService;

	private TransportDeployer transportDeployer;

	private RuleSessionConfig ruleSessionConfig;

//	private DashboardRuleServiceProviderProxy ruleServiceProviderProxy;

	private ManagementConfigurator configurator;

	private int agentId;

	@SuppressWarnings("unused")
	private String agentName;

//    public DashboardSessionImpl(BEArchiveResource barResource, BEProperties beProperties, RuleSessionManagerImpl manager) throws Exception {
//        super(barResource,beProperties, manager);
//		configurator = new ManagementConfigurator();
//		configurator.setDashboardSession(this);
//		configurator.createManagementService();
//    }
//
//	public DashboardSessionImpl(BEArchiveResource barResource, RuleSessionManager manager) throws Exception {
//		super(barResource, (RuleSessionManagerImpl) manager);
//		configurator = new ManagementConfigurator();
//		configurator.setDashboardSession(this);
//		configurator.createManagementService();
//	}

	public DashboardSessionImpl(BEArchiveResource barResource, BEProperties beProperties, RuleSessionManagerImpl manager, ObjectManager objectManager) throws Exception {
		super(barResource, beProperties, manager, objectManager);
		configurator = new ManagementConfigurator();
		configurator.setDashboardSession(this);
		configurator.createManagementService();
	}

	void setAgentID(int agentID){
		this.agentId = agentID;
	}

	void setAgentName(String agentName){
		this.agentName = agentName;
	}

	@Override
	public void init(boolean cacheServerOnly) throws Exception {
		super.init(cacheServerOnly);
		//TODO what do we do with the cacheServerOnly flag?
		configurator.init();
		managementService = configurator.getManagementService();
	}

	@Override
	public void start(boolean active) throws Exception {
		super.start(active);
		if (active == true){
			boolean deploymentNeeded = (Boolean) BEIntegrationProperties.AUTO_CHANNEL_CREATION_ENABLE.getValue(managementService.getProperties());
			if (deploymentNeeded == true){
				transportDeployer = new AutoTransportDeployer();
				Class<? extends SimpleEvent> asSubclass = SimpleDBEvent.class.asSubclass(SimpleEvent.class);
				ArchiveInputDestinationConfig[] destinationConfigs = transportDeployer.deploy(this,LoggingService.getChildLogger(LoggingService.getRootLogger(), "channels"),managementService.getProperties(), asSubclass);
				ruleSessionConfig = new MediatingRuleSessionConfig(super.getConfig(),destinationConfigs);
			}
			else {
				transportDeployer = null;
			}
			managementService.setBeanServer(ManagementFactory.getPlatformMBeanServer());
			ObjectName baseName = new ObjectName("com.tibco.be:type=Agent,agentId=" + agentId + ",subType=Dashboard");
			managementService.setBaseName(baseName);
			managementService.start();
			if (transportDeployer != null){
				transportDeployer.start(this);
			}
		}
	}

	public void suspend() {
		if (transportDeployer != null){
			transportDeployer.pause();
		}
		try {
			managementService.pause();
		} catch (ManagementException e) {
			throw new RuntimeException("could not suspend "+managementService.getInstanceName(),e);
		}
	}

	public void resume(){
		try {
			managementService.resume();
			if (transportDeployer != null){
				transportDeployer.resume();
			}
		} catch (ManagementException e) {
			throw new RuntimeException("could not resume "+managementService.getInstanceName(),e);
		}
	}

	@Override
	public void stop() {
		if (transportDeployer != null){
			transportDeployer.pause();
		}
		managementService.stop();
		if (transportDeployer != null){
			transportDeployer.stop();
		}
		super.stop();
	}

	@Override
	public void stopAndShutdown() {
		if (transportDeployer != null){
			transportDeployer.pause();
		}
		managementService.stop();
		if (transportDeployer != null){
			transportDeployer.shutdown();
		}
		super.stopAndShutdown();
	}

	@Override
	public RuleSessionConfig getConfig() {
		if (ruleSessionConfig == null){
			return super.getConfig();
		}
		return ruleSessionConfig;
	}

	@Override
	public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException {
		if (object instanceof SimpleDBEvent){
			processDBEvent(object);
			return;
		}
		super.assertObject(object, executeRules);
	}

	private void processDBEvent(Object object) {
		RuleSession currentRuleSession = RuleSessionManager.getCurrentRuleSession();
		try {
			RuleSessionManager.currentRuleSessions.set(this);
			SimpleDBEvent dbRequestEvent = (SimpleDBEvent) object;
			BizResponse bizResponse = DashboardAgentFunctions.processRequest(dbRequestEvent.getPayloadAsString());
			SimpleDBEvent dbResponseEvent = new SimpleDBEvent();
			DashboardAgentFunctions.populateEvent(dbResponseEvent, bizResponse);
			EventHelper.replyEvent(dbRequestEvent, dbResponseEvent);
		} catch (Exception e) {
			throw new RuntimeException("could not process incoming dashboard request event",e);
		} finally {
			RuleSessionManager.currentRuleSessions.set(currentRuleSession);
		}
	}

	@Override
	public RuleServiceProvider getRuleServiceProvider() {
//		if (ruleServiceProviderProxy == null){
//			synchronized (this) {
//				if (ruleServiceProviderProxy == null){
//					ruleServiceProviderProxy = new DashboardRuleServiceProviderProxy((RuleServiceProviderImpl) super.getRuleServiceProvider());
//				}
//			}
//		}
//		return ruleServiceProviderProxy;
		return super.getRuleServiceProvider();
	}

}