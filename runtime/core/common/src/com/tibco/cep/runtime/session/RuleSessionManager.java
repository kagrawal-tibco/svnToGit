package com.tibco.cep.runtime.session;


/**
 * Provides access to <code>RuleSession</code> objects.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */                                                        
public abstract class RuleSessionManager implements RuleRuntime {


    public static final ThreadLocal<RuleSession> currentRuleSessions = new ThreadLocal<RuleSession>();
    public static final String DEFAULT_CLUSTER_RULESESSION_NAME = "$cluster";
//    protected static final ThreadLocal currentInputEventSessions = new ThreadLocal();

//    /**
//     * Gets the InputEventSession of the current thread.
//     * TODO more about the InputEventSession
//     *
//     * @return an InputEventSession
//     * @since 2.0
//     */
//    public static InputEventSession getCurrentEventInputSession() {
//        return (InputEventSession) currentInputEventSessions.get();
//    }


    /**
     * Gets the <code>RuleSession</code> of the current thread.
     *
     * @return the <code>RuleSession</code> of the current thread.
     * @.category public-api
     * @since 2.0
     */
    public static RuleSession getCurrentRuleSession() {
        return currentRuleSessions.get();
    }
}
