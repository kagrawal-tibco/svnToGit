package com.tibco.cep.runtime.session;


import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.runtime.scheduler.TaskController;

import java.util.List;


/**
 * The RuleSession is the interface which interacts with the kernel of the engine.
 * It allows the programmer to assert objects and evaluate rules.
 * <p>The programmer gets access to an initialized <code>RuleSession</code> in one of the following ways:</p>
 * <ul>
 * <li><code>RuleSessionManager.getCurrentRuleSession()</code></li>
 * <li><code>RuleServiceProvider.getRuleRuntime().getRuleSession(somename)</code></li>
 * </ul>
 * <p>These mechanisms are very different and have different meanings.</p>
 * <p>The first one is used when coding a custom function.
 * Since the custom function is evaluated as a callback while evaluating/executing a rule, the session is well known,
 * so the programmer get access to the <code>RuleSession</code> whitin which the function is operating.</p>
 * <p>The second mechanism is used when the programmer is managing the lifecycle of the <code>RuleSession</code>'s.</p>
 * <p>A rule evaluation cycle (RTC) is:</p>
 * <ul>
 * <li>Test all the rules that match a condition for the fact that got asserted.</li>
 * <li>Create a rule agenda for all the rules ready to be fired - a rule in which all conditions are true is considered to be ready </li>
 * <li>Select the top rule based on rule priority as specified during designtime. </li>
 * <li>Execute the actions associated to the rule. </li>
 * <li>The action may change the agenda by modifying/asserting/deleting facts, which in turn causes the system to reevaluate the agenda.</li>
 * <li>The Run To Completion ends when there are no agenda items and the system is in equilibrium.</li>
 * </ul>
 * <p>The rule session is always stateful, i.e. it always maintains the state of the instances asserted
 * across multiple RTCs.
 * The programmer can call <code>assert(object, true)</code> multiple times with different objects.
 * To create an object use the the {@link com.tibco.cep.runtime.model.TypeManager TypeManager} factory
 * and pass the object fact type (concept/event/scorecard) which was created in TIBCO Designer.
 * Set the object's property values using standard <code>setProperty</code> methods.</p>
 * <p>Stateless behavior can be achieved by calling {@link #reset() reset} on the <code>RuleSession</code> after every RTC.</p>
 * <p>The RTC is evaluated on the caller's thread when an assert is invoked with execute permission.
 * At this point, the rule session is set as a ThreadLocal on the caller's thread,
 * so it is available to every function/custom function which is invoked as a result of an action fired
 * or condition evaluated.</p>
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO: doc the threading model for this interface
//TODO: doc the administrative operations supported by this Interface
public interface RuleSession {


    /**
     * Returns the name of this <code>RuleSession</code>.
     * This is the same as the name of the corresponding BusinessEvents Archive Resource specified in TIBCO Designer.
     *
     * @return the name of this <code>RuleSession</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public String getName();


    /**
     * Gets the configuration parameters used for building this <code>RuleSession</code>.
     *
     * @return the configuration parameters used for building this <code>RuleSession</code>.
     * @.category public-api
     * @since 2.0.0
     */
    public RuleSessionConfig getConfig();


    /**
     * Resets the rule session. The following actions are performed:
     * <ol>
     * <li>All the exceptions and all the <code>NamedInstance</code> objects are removed.</li>
     * <li>All the timer events and event expiry timers are stopped.</li>
     * <li>The object store is cleaned.</li>
     * <li>All the repeating event timers are restarted.</li>
     * </ol>
     *
     * @throws Exception
     */
    public void reset() throws Exception; //todo - consume all the event? startup action? perserve ther SC value?  reset num of fired?

    /**
     * Invokes a rule function com.tibco.cep.runtime.model.service.RuleFunction with a map. The input map
     * should map the parameter name to its value.  Input parameter of primitive type has to be boxed.
     * The return value will be boxed if it is primitive type.
     * @param functionURI the URI of the Rule Function
     * @param args of type Map as the input parameters for the rule function, maps the parameter name to the
     * corresponding value.
     * @param synchronize specified if the function will be synchronized and invoked inside the rule session.
     * Synchronizing the rule session allows caller to modify existing objects.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * /
    public Object invokeFunction(String functionURI, Map args, boolean synchronize);

    /**
     * Invoke a rule function com.tibco.cep.runtime.model.service.RuleFunction with an array of objects.
     * The objects in the array have to follow the same order as the input parameters.  Primitive type has
     * to be boxed. The return value will also be boxed if it is primitive type.
     * @param functionURI the URI of the Rule Function
     * @param args of type Object[] as the input parameters for the rule function.
     * @param synchronize specified if the function will be synchronized and invoked inside the rule session.
     * Synchronizing the rule session allows caller to modify existing objects.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * /
    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize);
     */

    /**
     * Executes rules that are triggered by previous actions (Add, Update, Remove Object).
     *
     * @.category public-api
     * @see #assertObject(Object, boolean)
     * @since 2.0.0
     */
    public void executeRules();


    /**
     * Gets all the simple events, concepts and scorecards present in this Session.
     *
     * @return a List of {@link com.tibco.cep.runtime.model.event.SimpleEvent SimpleEvent}
     *         or {@link com.tibco.cep.runtime.model.element.Concept Concept}.
     * @throws Exception
     * @.category public-api
     * @since 2.0.0
     */
    public List getObjects() throws Exception;


    /**
     * Adds an object to the Rule Session and optionally executes all the triggered rules
     * (conflict resolve / executeRules is called).
     *
     * @param object       the object to assert.
     * @param executeRules determines if conflict resolution will happen after asserting the object.
     * @throws DuplicateExtIdException if the external ID of the object to assert already exist in the session.
     * @.category public-api
     * @since 2.0.0
     */
    public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException;


    /**
     * Removes an object from the session.
     *
     * @param object       or the handle of the object.
     * @param executeRules determines if conflict resolution will happen after retracting the object.
     * @.category public-api
     * @since 2.0.0
     */
    public void retractObject(Object object, boolean executeRules);


    /**
     * Gets the manager (parent) who created this session.
     *
     * @return the <code>RuleRuntime</code> object which is the manager who created this session.
     * @.category public-api
     * @since 2.0.0
     */
    RuleRuntime getRuleRuntime();


    /**
     * Returns the <code>RuleServiceProvider</code> associated with this session.
     *
     * @return the <code>RuleServiceProvider</code> associated with this session.
     * @.category public-api
     * @since 2.0.0
     */
    RuleServiceProvider getRuleServiceProvider();


    /**
     * Gets the object manager associated to the session.
     *
     * @return the object manager associated to the session.
     * @since 2.0.0
     */
    ObjectManager getObjectManager();


    /**
     * Gets the time scheduler associated to the session.
     *
     * @return the time scheduler associated to the session.
     */
    TimeManager getTimeManager();


    /**
     * Starts this <code>RuleSession</code> in either active or inactive mode.
     *
     * @param activeMode - The active mode specifies whether to the start the rule session in a execution mode or evaluation mode only.
     *                   This means even if active mode is set to false, then if even the rule condition evaluates to true, the actions won't be executed.
     *                   The underlying Rete graph of the working memory is still in a consistent.
     * @throws Exception
     */
    void start(boolean activeMode) throws Exception;


    /**
     * Stops this ruleSession
     */
    void stop();

    void stopAndShutdown();

    /**
     * Shuts down this rulesession.
     */
    //void shutdown();


    /**
     * Sets the ActiveMode of this <code>RuleSession</code>.
     *
     * @param active activate the ruleSession or not.
     * @.category public-api
     * @since 2.0.0
     */
    void setActiveMode(boolean active);

    public TaskController getTaskController();

    /**
     * Invoke a RuleFunction on this <code>RuleSession</code>.
     *
     * @param functionURI the URI of the RuleFunction to be invoked
     * @param args an Object array specifies the input arguments
     * @param synchronize tells whether to execute the RuleFunction inside the RuleSession OR execute externally
     * and apply newly created and deleted instance(s) to the RuleSession.  Modify a Concept instance is not allow
     * if this parameter is set to true or the engine will throw a RuntimeException.
     * @return the result of the RuleFunction
     *
     * @.category public-api
     * @since 3.0.2
     */    
    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize);
    
    
    /**
     * 
     * @param functionFQName the fully qualified Catalog function name to be invoked i.e. "System.debugOut"
     * @param args a variable argument specifying the input arguments
     * @return the result of the Catalog function
     * 
     * @.category public-api
     * @since 5.2
     */
    public Object invokeCatalog(String functionFQName,Object... args);

    public List getObjects(Filter filter) throws Exception;

    public boolean getActiveMode();

    public RuleSessionMetrics getRuleSessionMetrics();

    String getLogComponent();


    ProcessingContextProvider getProcessingContextProvider();   //TODO simplify?


    interface PostRTCAction {
        void post(Runnable tak);
        public boolean supportsPostAction();
    }
}
