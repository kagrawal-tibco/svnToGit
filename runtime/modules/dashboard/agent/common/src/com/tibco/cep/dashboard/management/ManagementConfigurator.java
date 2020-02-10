package com.tibco.cep.dashboard.management;

import com.tibco.cep.dashboard.common.utils.CopyUtil;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

public final class ManagementConfigurator {
	
	public static enum MODE {SERVER, CLIENT, TOOL, EMBED};
	
	private DashboardSession dashboardSession;
	
	private MODE mode;
	
	private ManagementService managementService;
	
	private boolean initialized;
	
	public ManagementConfigurator() {
		initialized = false;
	}

	public DashboardSession getDashboardSession() {
		return dashboardSession;
	}

	public void setDashboardSession(DashboardSession dashboardSession) {
		this.dashboardSession = dashboardSession;
	}

	public MODE getMode() {
		return mode;
	}

	public void setMode(MODE mode) {
		this.mode = mode;
	}
	
	public void createManagementService() throws ManagementException {
		if (dashboardSession == null){
			throw new IllegalArgumentException("Dashboard Session cannot be null");
		}
		if (mode == null){
			mode = MODE.SERVER;
		}
		//create the management service
		managementService = ManagementService.getInstance();
	}

	public void init() throws ManagementException {
		if (initialized == true){
			return;
		}
		//initialize the logging service 
		RuleServiceProvider ruleServiceProvider = dashboardSession.getRuleServiceProvider();
		LoggingService.getInstance().init(ruleServiceProvider);
		ServiceContext context = new ServiceContext();
		context.setRuleServiceProvider(ruleServiceProvider);
		context.setRuleSession(dashboardSession);
		ManagementProperties properties = new ManagementProperties(ruleServiceProvider.getProperties(),ManagementUtils.BE_AGENT_DASHBOARD_KEY_PREFIX,true);
		// ntamhank: copy all global variables in management properties
		CopyUtil.copyGlobalVariablesToProperties(ruleServiceProvider, properties);
		// ntamhank: registering a ChangeListener to RSP
		// from CacheCluster
		//((WorkingMemoryImpl)ruleSessionImpl.getWorkingMemory()).setChangeListener(activationListener);
		
		ChangeListener gvChangeListener = new GlobalVariablesChangeListener(ruleServiceProvider,properties);
		if (ruleServiceProvider instanceof RuleServiceProviderImpl) {
			((RuleServiceProviderImpl)ruleServiceProvider).registerHDListener(gvChangeListener);
		}
		
		managementService.init(LoggingService.getRootLogger(), mode, properties, context);
		initialized = true;
	}
	
	public ManagementService getManagementService() throws ManagementException{
		return managementService;
	}	
}
