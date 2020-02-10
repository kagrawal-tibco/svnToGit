package com.tibco.cep.runtime.service.tester.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleAdministrator;
import com.tibco.cep.runtime.session.RuleRuntime;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 6:01:46 PM
 * <!--
 * Add Description of the class here
 * -->
 */
public class TesterRuleServiceProvider implements RuleServiceProvider {

    /**
     * Delegate all calls to this
     */
    private RuleServiceProvider delegate;


	private List<ReteChangeWatchdogNotificationManager> changeListeners = new ArrayList<>();

    /**
     * Session Id to {@link TesterSession}
     */
    private ConcurrentHashMap<String, TesterSession> connectedSessions = new ConcurrentHashMap<String, TesterSession>();

    public TesterRuleServiceProvider(RuleServiceProvider delegate) {
        this.delegate = delegate;
    }
    /**
     * Initializes and starts this <code>RuleServiceProvider</code> in the given mode.
     *
     * @param mode one of MODE_API, MODE_INIT, MODE_PRIMARY, MODE_LOAD_ONLY
     * @throws Exception
     * @.category public-api
     * @see #MODE_API
     * @see #MODE_INIT
     * @see #MODE_PRIMARY
     * @see #MODE_LOAD_ONLY
     */
    public void configure(int mode) throws Exception {
        delegate.configure(mode);
    }

    /**
     * Gets the <code>ChannelManager</code> which manages all the {@link com.tibco.cep.runtime.channel.Channel Channels}
     * in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>ChannelManager</code>.
     * @since 2.0.0
     */
    public ChannelManager getChannelManager() {
        return delegate.getChannelManager();
    }

    /**
     * Gets the class loader used by deserialization and hot deployment.
     *
     * @return a <code>ClassLoader</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public ClassLoader getClassLoader() {
        return delegate.getClassLoader();
    }

    /**
	 * Register a new {@link TesterSession} with this RSP Manager.
	 * <p>
	 * Returns previously registered {@link TesterSession} if a session already existed.
	 * </p>
     * <p>
     * Invoke this method from the client using the JDI interfaces
     * @param ruleSession
	 * @param sessionName
	 */
    @Remote
    public TesterSession registerSession(RuleSession ruleSession, String sessionName) {
        TesterSession testerSession = new TesterSession(this, ruleSession, sessionName);
        TesterSession previousSession = connectedSessions.putIfAbsent(sessionName, testerSession);
        TesterSession returnSession = (previousSession != null) ? previousSession : testerSession;
        return returnSession;
    }

    /**
	 * Remove a registered {@link TesterSession}
	 * @param sessionId
	 */
    @Remote
    public void deregisterSession(String sessionId) {
		connectedSessions.remove(sessionId);
	}

    /**
	 * List of all tester sessions connected to this RSP
	 * @return
	 */
    @Remote
    public Collection<TesterSession> getAllConnectedSessions() {
		return connectedSessions.values();
	}

//    public List<ReteChangeWatchdogNotificationManager> getChangeListener() {
//        return changeListeners;
//    }

    public void registerWatchdog(ReteChangeWatchdog reteChangeWatchdog) {
    	for (ReteChangeWatchdogNotificationManager reteChangeWatchdogNotificationManager : changeListeners) {
    		reteChangeWatchdogNotificationManager.registerWatchdog(reteChangeWatchdog);
		}
    }
    
    public void setChangeListener(ReteChangeWatchdogNotificationManager changeListener) {
        this.changeListeners.add(changeListener);
    }

    /**
     * Gets the <code>GlobalVariables</code> that manages all global variables
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a GlobalVariables.
     * @.category public-api
     * @since 2.0.0
     */
    public GlobalVariables getGlobalVariables() {
        return delegate.getGlobalVariables();
    }

    /**
     * Gets the <code>IdGenerator</code> which manages unique IDs
     * in this <code>RuleServiceProvider</code>.
     *
     * @return an IdGenerator
     * @since 2.0.0
     */
    public IdGenerator getIdGenerator() {
        return delegate.getIdGenerator();
    }

    public Locale getLocale() {
        return delegate.getLocale();
    }

    /**
     * Gets the <code>Logger</code> for a given module.
     *
     * @param clazz Class used to determine the module.
     * @return a <code>Logger</code>.
     * @.category public-api
     * @since 4.0.0
     */
    public Logger getLogger(Class clazz) {
        return delegate.getLogger(clazz);
    }

    /**
     * Gets the <code>Logger</code> for a given module.
     *
     * @param name String name of the module.
     * @return a <code>Logger</code>.
     * @.category public-api
     * @since 4.0.0
     */
    public Logger getLogger(String name) {
        return delegate.getLogger(name);
    }

    /**
     * Gets the name under which this <code>RuleServiceProvider</code> is registered
     * in its parent <code>RuleServiceProviderManager</code>.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0
     */
    public String getName() {
        return delegate.getName();
    }

    /**
     * Gets the <code>DeployedProject</code> which contains the part of the packaged project that has been deployed
     * to this <code>RuleServiceProvider</code>.
     *
     * @return a <code>DeployedProject</code>
     * @since 2.0.0
     */
    public DeployedProject getProject() {
        return delegate.getProject();
    }

    public boolean isConceptAvailable(String extId) {
        return true;
    }

    /**
     * Gets the properties used to build this <code>RuleServiceProvider</code>.
     *
     * @return a <code>Properties</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public Properties getProperties() {
        return delegate.getProperties();
    }

    /**
     * Gets the <code>RuleAdministrator</code> which manages remote administration
     * for this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleAdministrator</code>.
     * @since 2.0.0
     */
    public RuleAdministrator getRuleAdministrator() {
        return delegate.getRuleAdministrator();
    }

    /**
     * Returns the <code>RuleRuntime</code> that manages the rule sessions in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleRuntime</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleRuntime getRuleRuntime() {
        return delegate.getRuleRuntime();
    }

    /**
     * Gets the <code>TypeManager</code> which manages all Ontology type definitions
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a <code>TypeManager</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public TypeManager getTypeManager() {
        return delegate.getTypeManager();
    }

    public void initProject() throws Exception {
        throw new UnsupportedOperationException("Operation not supported on this RSP");
    }

    public boolean isMultiEngineMode() {
        return delegate.isMultiEngineMode();
    }

    /**
     * Stops the components in the reverse order they were started. Valid only when running in a MODE_PRIMARY.
     * For API and INIT Mode it is a no-op.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public void shutdown() {
        delegate.shutdown();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.session.RuleServiceProvider#getCluster()
     */
    @Override
    public Cluster getCluster() {
        return delegate.getCluster();  
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.session.RuleServiceProvider#isCacheServerMode()
     */
    @Override
    public boolean isCacheServerMode() {
        return delegate.isCacheServerMode();
    }

    /**
     * @return
     */
    public RuleServiceProvider getDelegate() {
		return delegate;
	}
    
	/**
	 * @param delegate
	 */
	public void setDelegate(RuleServiceProvider delegate) {
		this.delegate = delegate;
	}
}
