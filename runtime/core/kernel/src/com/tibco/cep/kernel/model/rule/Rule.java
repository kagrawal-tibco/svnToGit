package com.tibco.cep.kernel.model.rule;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:13:16 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Runtime rule.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface Rule {


    /**
     * Minimum priority of a rule.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public static final int MIN_PRIORITY = 1;

    /**
     * Maximum priority of a rule.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public static final int MAX_PRIORITY = 10;

    /**
     * Default priority of a rule.
     *
     * @.category public-api
     * @since 2.0.0
     */
    public static final int DEFAULT_PRIORITY = 5;


    /**
     * Gets the Id of this rule, which is unique within all rules in a container.
     *
     * @return the Id of the rule.
     * @since 2.0.0
     */
    // ...and should be assigned by RuleImpl.getNextRuleId()
    int getId();


    /**
     * Gets the name of the rule.
     *
     * @return the name of the rule.
     * @.category public-api
     * @since 2.0.0
     */
    String getName();


    /**
     * Gets the URI of the rule.
     *
     * @return String URI of the rule.
     * @.category public-api
     * @since 4.0.0
     */
    String getUri();


    /**
     * Gets the description of the rule.
     *
     * @return a <code>String</code> describing this rule.
     * @.category public-api
     * @since 2.0.0
     */
    String getDescription();


    /**
     * Tests if the rule is active.
     *
     * @return true if the rule is active.
     * @.category public-api
     * @see #activate()
     * @see #deactivate()
     * @since 2.0.0
     */
    boolean isActive();


    /**
     * Sets the priority of the rule.
     *
     * @param priority the priority to set.
     * @.category public-api
     * @see #DEFAULT_PRIORITY
     * @see #MIN_PRIORITY
     * @see #MAX_PRIORITY
     * @since 2.0.0
     */
    void setPriority(int priority);


    /**
     * Gets the priority of the rule.
     *
     * @return the priority of the rule
     * @.category public-api
     * @see #DEFAULT_PRIORITY
     * @see #MIN_PRIORITY
     * @see #MAX_PRIORITY
     * @since 2.0.0
     */
    int getPriority();


    /**
     * Gets the identifiers from the declaration section of the rule.
     *
     * @return an array of all the <code>Identifier</code> of this rule.
     * @.category public-api
     * @since 2.0.0
     */
    Identifier[] getIdentifiers();


    /**
     * Get the conditions of this rule.
     *
     * @return an array of all the <code>Condition</code> of this rule.
     * @.category public-api
     * @since 2.0.0
     */
    Condition[] getConditions();


    /**
     * Get the actions of this rule.
     *
     * @return an array of all the <code>Action</code> of this rule.
     * @.category public-api
     * @since 2.0.0
     */
    Action[] getActions();


    /**
     * Get the time when this rule was loaded into the session.
     *
     * @return the time when the rule was loaded.
     */
    String getLoadTime();


    /**
     * Called automatically when the rule is loaded into the session.
     *
     * @param loadTime time when the rule was loaded into the session.
     */
    void setLoadTime(String loadTime);


    /**
     * Activates the rule.
     *
     * @.category public-api
     * @see #deactivate()
     * @see #isActive()
     * @since 2.0.0
     */
    public void activate();


    /**
     * Deactivates the rule.
     *
     * @.category public-api
     * @see #activate()
     * @see #isActive()
     * @since 2.0.0
     */
    public void deactivate();


    public boolean forwardChain();
    
    public RuleFunction getRank();
}
