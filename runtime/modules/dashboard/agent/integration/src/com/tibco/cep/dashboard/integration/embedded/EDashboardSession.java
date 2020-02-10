package com.tibco.cep.dashboard.integration.embedded;

import com.tibco.cep.dashboard.session.DashboardSession;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.*;

import java.util.List;


public class EDashboardSession implements DashboardSession {

	private RuleServiceProvider ruleServiceProvider;

	public EDashboardSession() {
		super();
		ruleServiceProvider = new ERuleServiceProvider();
	}

	@Override
	public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException {
		throw new UnsupportedOperationException("assertObject");
	}

	@Override
	public void executeRules() {
		throw new UnsupportedOperationException("executeRules");
	}

	@Override
	public RuleSessionConfig getConfig() {
		throw new UnsupportedOperationException("getConfig");
	}

	@Override
	public String getName() {
		return "EmbeddedDashboardSession";
	}

	@Override
	public ObjectManager getObjectManager() {
		throw new UnsupportedOperationException("getObjectManager");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getObjects() throws Exception {
		throw new UnsupportedOperationException("getObjects");
	}

	@Override
	public RuleRuntime getRuleRuntime() {
		throw new UnsupportedOperationException("getRuleRuntime");
	}

	@Override
	public RuleServiceProvider getRuleServiceProvider() {
		return ruleServiceProvider;
	}

	@Override
	public TaskController getTaskController() {
		throw new UnsupportedOperationException("getTaskController");
	}

	@Override
	public TimeManager getTimeManager() {
		throw new UnsupportedOperationException("getTimeManager");
	}

	@Override
	public void reset() throws Exception {
		throw new UnsupportedOperationException("reset");
	}

	@Override
	public void retractObject(Object object, boolean executeRules) {
		throw new UnsupportedOperationException("retractObject");
	}

	@Override
	public void setActiveMode(boolean active) {
		throw new UnsupportedOperationException("setActiveMode");
	}

	@Override
	public void start(boolean activeMode) throws Exception {
		throw new UnsupportedOperationException("start");
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("stop");
	}

	@Override
	public void stopAndShutdown() {
		throw new UnsupportedOperationException("stopAndShutdown");
	}

	@Override
	public boolean getActiveMode() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getObjects(Filter filter) throws Exception {
		return null;
	}

	@Override
	public Object invokeFunction(String functionURI, Object[] args, boolean synchronize) {
		return null;
	}

	@Override
	public RuleSessionMetrics getRuleSessionMetrics() {
		throw new UnsupportedOperationException("getRuleSessionMetrics");
	}

	@Override
	public String getLogComponent() {
		return getName();
	}

	@Override
	public void suspend() {
		throw new UnsupportedOperationException("suspend");
	}

	@Override
	public void resume() {
		throw new UnsupportedOperationException("resume");
	}


    @Override
    public ProcessingContextProvider getProcessingContextProvider() {
        throw new UnsupportedOperationException("getProcessingContextProvider");
    }

	@Override
	public Object invokeCatalog(String functionFQName, Object... args) {
		throw new UnsupportedOperationException("invokeCatalog");
	}

  
    
}