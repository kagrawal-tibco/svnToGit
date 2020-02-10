package com.tibco.cep.kernel.model.knowledgebase;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.kernel.concurrent.Guard;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.kernel.service.logging.LogManager;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 3:26:39 PM
 * To change this template use File | Settings | File Templates.
 */
/**
 * @version 2.0.0
 * @since 2.0.0
 */

public interface WorkingMemory {

    /**
     * Get the Id of the WM.
     * @return the Id of the WM.
     * @.category public-api
     */
    int getId();

    /**
     * Get the name of the WM.
     * @return the name of this WM.
     * @.category public-api
     */
    String getName();

    /**
     * Get the logger of the WM.
     * @return the logger of this WM.
     * @.category public-api
     */
    LogManager getLogManager();

    /**
     * Get the RuleLoader of the WM.
     * @return the RuleLoader of this WM.
     * @.category public-api
     */
    RuleLoader getRuleLoader();

    /**
     * Get the TimeManager of the WM.
     * @return the TimeManager of the WM.
     */
    TimeManager getTimeManager();

    /**
     * Get the ObjectManager of the WM.
     * @return the ObjectManager of the WM.
     */
    ObjectManager getObjectManager();

    /**
     * This method initial the WM.
     * Rule can be loaded after calling this method and recovery should be performed after that.
     * The startup action will be called when start() is called the first time.
     * The activate action will be called everytime the activeMode is set to active.
     * @param startup
     * @param activate
     * @param rules
     */
    void init(Action startup, Action activate, Set rules) throws Exception;

    /**
     * Channel should be started after this
     * When this method is invoked for the first time, statup and timerContext action will be called.
     * @param activeMode start the working Memory in active model or not
     * @throws Exception if working memory is not able to start
     * @.category public-api
     */
    void start(boolean activeMode) throws Exception;

    /**
     * Channel should be stopped first prior to this.
     * This method cleanup the WM.
     * Object Manager should be stopped after this.
     * @.category public-api
     */
    void stop() throws Exception;

    /**
     * Channel should be disabled first prior to this.
     * This method disable the timer, invoke the shutdown action, and the WM.
     * Object Manager should be disable after this.
     * @param shutdown
     * @.category public-api
     */
    void stopAndShutdown(Action shutdown) throws Exception;

    /**
     * Remove all objects in the working memory and reset the working memory
     * including the total number of rule fired.
     * @.category public-api
     */
    void reset();

    /**
     * If activeMode is true, rule will be evaluated and fired.  Otherwise, rule will just be evaluated,
     * no rule agenda will be build, and no rule will be fired.
     * @return activeMode
     * @.category public-api
     */
    boolean getActiveMode();

    /**
     * Set the activeMode of the Working Memory.  If activeMode is true, rule will be evaluated and fired.
     * Otherwise, rule will just be evaluated, no rule agenda will be build, and no rule will be fired.
     * @param activeMode mode to be set
     * @.category public-api
     */
    void setActiveMode(boolean activeMode);

    /**
     * Get the total number of rules fired in the WM.
     * @return the total number of rules fired.
     * @.category public-api
     */
    long getTotalNumberRulesFired();
    
    /**
     * Get the number of rules fired for a rule identified by the specified ruleUri.
     * @param ruleUri uri of the rule.
     * @return the number of rules fired for specified ruleUri.
     * @.category public-api
     * @since 5.2.2
     */
    long getNumberOfRulesFired(String ruleUri);

    /**
     * Reset the total number of rules fired in the WM.
     * @.category public-api
     */
    void resetTotalNumberRulesFired();

    /**
     * Invoke an action in the WM given the set of objects.  This executes
     * all the triggered rules in the result of invoking the action.
     * @param action to be invoked.
     * @param objs set of objects as the input of the action.
     * @.category public-api
     */
    void invoke(Action action, Object[] objs);

    /**
     * Invoke a rule function in the WM given an array of objects. The objects in
     * the array have to follow the same order as the input parameters.  Primitive
     * type has to be boxed.  The return value will also be boxed if it is primitive
     * type.
     * This executes all the triggered rules in the result of invoking the rule function.
     * @param function the rule function to be invoked.
     * @param args as the input parameters for the rule function.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * @.category public-api
     */
    Object invoke(RuleFunction function, Object[] args);

    /**
     * Invoke the rule function  in the WM given a map. The input map should map the parameter
     * name to its value. Input parameter of primitive type has to be boxed and the return value
     * will be also boxed if it is primitive type.
     * This executes all the triggered rules in the result of invoking the rule function.
     * @param function the rule function to be invoked.
     * @param maps as the input parameters for the rule function, maps the parameter name to the
     * corresponding value.
     * @return return value of the rule function.  If the return type is void, null will be returned.
     * @.category public-api
     */
    Object invoke(RuleFunction function, Map args);

    /**
     * Execute rules that are triggered by previous actions (Add, Update, Remove Object).
     * @.category public-api
     */
    void executeRules();

    /**
     * Add an object to the WM and execute all the triggered rules (conflict
     * resolve/executeRules is called) if executeRule is set to true
     * @param obj to be asserted.
     * @param executeRule determines if conflict resolve will be called after asserting the object
     * @return Handle for this object.
     * @throws DuplicateExtIdException
     * @.category public-api
     */
    Handle assertObject(Object obj, boolean executeRule) throws DuplicateExtIdException;

    /**
     * Update an object in the WM and execute all the triggered rules (conflict
     * resolve/executeRules is called) if executeRule is set to true
     * @param obj or the handle of the object.
     * @param executeRule determines if conflict resolve will be called after modifying the object
     * @return true if the operation success, false otherwise.
     * @.category public-api
     */
    boolean modifyObject(Object obj, boolean executeRule, boolean recordThisModification);

    /**
     * Remove an object from the WM.
     * @param obj or the handle of the object.
     * @param executeRule specifies if executeRuls will be called after retracting the object.
     * @.category public-api
     */
    void retractObject(Object obj, boolean executeRule);

    /**
     * Get warning messages if any for this working memory.  Usually, used after
     * deploying some rules.  Warning messages tell which rule may have some issue.
     * @return warning messages
     * @.category public-api
     */
    String getWarningMessages();

    /**
     * clear the warning messages
     * @.category public-api
     */
    void clearWarningMessages();

    /**
     * @return Must <b>never</b> be <code>null</code>.
     */
    Guard getGuard();


    boolean isConcurrent();

    void applyElementChanges(String description, long id, Class entityClz, int [] dirtyBits,
                                List reloadFromCacheObjects, List assertHandles, LinkedHashSet deletedObjects,List reevaluateObjects, boolean reloadOnly);

    void applyDelete(String description, long id, Class entityClz, List reloadFromCacheObjects, List assertHandles,
                     LinkedHashSet deletedObjects,List reevaluateObjects);

    boolean stateChanged(Object obj, int index);

    /**
     * Register a class with the rete wm so that its {@link TypeInfo} can be retrieved.
     * @param objectType
     */
    void registerType(Class objectType);

    /**
     * Retrieve {@link TypeInfo} of a class registered with the Rete
     * @param objectClass
     * @return
     */
    TypeInfo getTypeInfo(Class objectClass);

    void suspend();
    void resume();
}
