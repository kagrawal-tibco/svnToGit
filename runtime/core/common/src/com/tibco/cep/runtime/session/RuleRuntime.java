package com.tibco.cep.runtime.session;

import com.tibco.cep.kernel.service.ObjectManager;

/**
 * Manages the life cycle of <code>RuleSession</code> objects.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface RuleRuntime {


    /**
     * Initializes the RuleRuntime.
     * This is by default called by the <code>RuleServiceProvider</code> implementation.
     *
     * @since 2.0.0
     */
    public void init() throws Exception;


    /**
     * Starts the <code>RuleRuntime</code>, by starting all the rule sessions.
     *
     * @param activeMode mode which specifies whether to the start the rule session in an execution mode
     *                   or in an evaluation mode only.
     * @throws Exception
     * @since 2.0.0
     */
    public void start(boolean activeMode) throws Exception;


    /**
     * Stops the <code>RuleSessionManager</code>, which will stop all its rule sessions.
     * For some administrative purpose, use this call.
     * If you want to restart the manager, call the start again
     *
     * @throws Exception
     * @since 2.0.0
     */
    public void stop() throws Exception;


    /**
     * Shuts down the <code>RuleSessionManager</code> gracefully.
     * This should be called only before performing a <code>System.exit</code>.
     *
     * @since 2.0.0
     */
    public void shutdown();


    /**
     * Changes the rule session from evaluation mode to execution mode and vice versa.
     *
     * @param activeMode
     * @since 2.0.0
     */
    public void setActiveMode(boolean activeMode);


    /**
     * Gets an array of all the <code>RuleSession</code> associated with this runtime.
     *
     * @return an array of all the <code>RuleSession</code> associated with this runtime.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleSession[] getRuleSessions();


    /**
     * Gets a <code>RuleSession</code> by name.
     *
     * @param name - A string whose value is one of the BusinessEvent's archive name
     *             (not the Enterprise archive) configured during the packaging process.
     * @return The <code>RuleSession</code> found, or null if not found.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleSession getRuleSession(String name);


    /**
     * Removes a rule session by name.
     * This will internally invoke a shutdown on the session, which will wait for rule evaluation cycle to complete.
     *
     * @param name The business events archive name
     * @return the rule session that was removed.
     * @since 2.0.0
     */
    public RuleSession removeRuleSession(String name);


    /**
     * Creates and initializes a rule session by name (see <code>getRuleSession</code>).
     *
     * @param name - The business events archive name.
     * @return a newly created and initialized RuleSession.
     * @throws Exception
     * @see #getRuleSession(String)
     * @since 2.0.0
     */
    public RuleSession createRuleSession(String name) throws Exception;

    public RuleSession createRuleSession(String name, ObjectManager objectManager) throws Exception;


    /**
     * Returns the <code>RuleServiceProvider</code> associated with this <code>RuleRuntime</code>.
     *
     * @return the <code>RuleServiceProvider</code> associated with this <code>RuleRuntime</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleServiceProvider getRuleServiceProvider();


    public void suspendWMs();

    public void suspendWMs(boolean holdLock);

    public void releaseWMs();

    public boolean isLocked();


}
