package com.tibco.cep.dashboard.integration.be;

import java.util.Locale;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.ExecutableResource;
import com.tibco.cep.util.ResourceManager;

public class DashboardRuleServiceProviderProxy extends RuleServiceProviderImpl {

	private RuleServiceProviderImpl ruleServiceProviderImpl;

	private BEProperties extendedProperties;

	DashboardRuleServiceProviderProxy(RuleServiceProviderImpl ruleServiceProviderImpl){
		this.ruleServiceProviderImpl = ruleServiceProviderImpl;
		BEProperties properties = (BEProperties) ruleServiceProviderImpl.getProperties();
		extendedProperties = new BEProperties(properties);
		extendedProperties.put(InferenceAgent.PROP_INFERENCE_AGENT_CLASS_NAME, DashboardAgent.class.getName());
		//PATCH RuleServiceProviderImpl exposes public variables , replicate them here : Shiv commented out the public variable to make debugger work properly
//		this.started = this.ruleServiceProviderImpl.started;
		this.cacheServerMode = this.ruleServiceProviderImpl.cacheServerMode;
	}

	public void addShutdownListener(RSPShutdownListener listener) throws Exception {
		ruleServiceProviderImpl.addShutdownListener(listener);
	}

	public void bind(RuleSession ruleSession) throws Exception {
		ruleServiceProviderImpl.bind(ruleSession);
	}

	public void configure(int configureType) throws Exception {
		ruleServiceProviderImpl.configure(configureType);
	}

	public void connectChannels(RuleSession ruleSession) throws Exception {
		ruleServiceProviderImpl.connectChannels(ruleSession);
	}

	public RuleSessionManager createRuleSessionManager(BEProperties env) throws Exception {
		return ruleServiceProviderImpl.createRuleSessionManager(env);
	}

	public void deactivateSession() {
		ruleServiceProviderImpl.deactivateSession();
	}

	public void ensureRSP() {
		ruleServiceProviderImpl.ensureRSP();
	}

	public boolean equals(Object obj) {
		return ruleServiceProviderImpl.equals(obj);
	}

	public ChannelManager getChannelManager() {
		return ruleServiceProviderImpl.getChannelManager();
	}

	public ClassLoader getClassLoader() {
		return ruleServiceProviderImpl.getClassLoader();
	}

	public RuleServiceProvider getContainerRsp() {
		return ruleServiceProviderImpl.getContainerRsp();
	}

	public GlobalVariables getGlobalVariables() {
		return ruleServiceProviderImpl.getGlobalVariables();
	}

	public IdGenerator getIdGenerator() {
		return ruleServiceProviderImpl.getIdGenerator();
	}

	public Locale getLocale() {
		return ruleServiceProviderImpl.getLocale();
	}

	@SuppressWarnings("rawtypes")
	public Logger getLogger(Class clazz) {
		return ruleServiceProviderImpl.getLogger(clazz);
	}

	public Logger getLogger(String name) {
		return ruleServiceProviderImpl.getLogger(name);
	}

	public String getName() {
		return ruleServiceProviderImpl.getName();
	}

	public DeployedProject getProject() {
		return ruleServiceProviderImpl.getProject();
	}

	public Properties getProperties() {
		return extendedProperties;
	}

	public ResourceManager getResourceManager() {
		return ruleServiceProviderImpl.getResourceManager();
	}

	public ThreadGroup getRSPThreadGroup() {
		return ruleServiceProviderImpl.getRSPThreadGroup();
	}

	public RuleAdministrator getRuleAdministrator() {
		return ruleServiceProviderImpl.getRuleAdministrator();
	}

	public RuleRuntime getRuleRuntime() {
		return ruleServiceProviderImpl.getRuleRuntime();
	}

	public RuleSessionManager getRuleSessionManager() {
		return ruleServiceProviderImpl.getRuleSessionManager();
	}

	public RuleSessionManager getRuleSessionService() {
		return ruleServiceProviderImpl.getRuleSessionService();
	}

	public byte getStatus() {
		return ruleServiceProviderImpl.getStatus();
	}

	public int getSuspendMode() {
		return ruleServiceProviderImpl.getSuspendMode();
	}

	public BEProperties getSystemProperties() {
		return ruleServiceProviderImpl.getSystemProperties();
	}

	public TypeManager getTypeManager() {
		return ruleServiceProviderImpl.getTypeManager();
	}

	public Object getVariable(Object key) {
		return ruleServiceProviderImpl.getVariable(key);
	}

	public int hashCode() {
		return ruleServiceProviderImpl.hashCode();
	}

	public void hawkShutdown() {
		ruleServiceProviderImpl.hawkShutdown();
	}

	public void initProject() throws Exception {
		ruleServiceProviderImpl.initProject();
	}

	public boolean isCacheServerMode() {
		return ruleServiceProviderImpl.isCacheServerMode();
	}

	public boolean isContained() {
		return ruleServiceProviderImpl.isContained();
	}

	public boolean isMultiEngineMode() {
		return ruleServiceProviderImpl.isMultiEngineMode();
	}

	public boolean isRTCLocked() {
		return ruleServiceProviderImpl.isRTCLocked();
	}

	public boolean isShuttingDown() {
		return ruleServiceProviderImpl.isShuttingDown();
	}

	public boolean isSuspended() {
		return ruleServiceProviderImpl.isSuspended();
	}

	public void lockRTC() throws Exception {
		ruleServiceProviderImpl.lockRTC();
	}

	public void notify(ChangeEvent e) {
		ruleServiceProviderImpl.notify(e);
	}

	public void onOutOfMemory(Thread t, Throwable e) {
		ruleServiceProviderImpl.onOutOfMemory(t, e);
	}

	public void registerExecutableResource(ExecutableResource resource) {
		ruleServiceProviderImpl.registerExecutableResource(resource);
	}

	public void removeVariable(Object key) {
		ruleServiceProviderImpl.removeVariable(key);
	}

	public void resumeChannels(RuleSession ruleSession) throws Exception {
		ruleServiceProviderImpl.resumeChannels(ruleSession);
	}

	public void resumeRSP() {
		ruleServiceProviderImpl.resumeRSP();
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		ruleServiceProviderImpl.setIdGenerator(idGenerator);
	}

	public void setVariable(Object key, Object value) {
		ruleServiceProviderImpl.setVariable(key, value);
	}

	public void shutdown() {
		ruleServiceProviderImpl.shutdown();
	}

	public void startChannels(RuleSession ruleSession, int initialMode) throws Exception {
		ruleServiceProviderImpl.startChannels(ruleSession, initialMode);
	}

	public void suspendChannels(RuleSession ruleSession) throws Exception {
		ruleServiceProviderImpl.suspendChannels(ruleSession);
	}

	public void suspendRSP() {
		ruleServiceProviderImpl.suspendRSP();
	}

	public void suspendRSP(int suspendMode) {
		ruleServiceProviderImpl.suspendRSP(suspendMode);
	}

	public String toString() {
		return ruleServiceProviderImpl.toString();
	}

	public void unbindRuleSession(RuleSession ruleSession) throws Exception {
		ruleServiceProviderImpl.unbindRuleSession(ruleSession);
	}

	public void unlockRTC() throws Exception {
		ruleServiceProviderImpl.unlockRTC();
	}

	public void unregisterExecutableResource(ExecutableResource resource) {
		ruleServiceProviderImpl.unregisterExecutableResource(resource);
	}

	public void waitForTaskControllers() {
		ruleServiceProviderImpl.waitForTaskControllers();
	}

	public void registerHDListener(ChangeListener gvChangeListener) {
		ruleServiceProviderImpl.registerHDListener(gvChangeListener);

	}

	public void startCluster() throws LifecycleException {
		ruleServiceProviderImpl.startCluster();
	}

	public Cluster getCluster() {
		return ruleServiceProviderImpl.getCluster();
	}
}
