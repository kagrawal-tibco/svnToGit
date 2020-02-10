package com.tibco.cep.kernel.model.rule;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:14:26 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents a condition within a rule.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
//TODO enhance description
public interface Condition {


    /**
     * Gets all the <code>Identifier</code> objects that will be used for this <code>Condition</code>.
     *
     * @return array of <code>Identifier</code>.
     * @.category public-api
     * @since 2.0.0
     */
    Identifier[] getIdentifiers();


    /**
     * Evaluates the expression of this <code>Condition</code>.
     *
     * @param objects <code>Object[]</code> containing parameter values for the expression.
     * @return the boolean result of the evaluation.
     * @.category public-api
     * @since 2.0.0
     */
    boolean eval(Object[] objects);


    /**
     * Gets the rule that contains this Condition.
     *
     * @return Rule of this Action
     * @.category public-api
     * @since 2.0.0
     */
    Rule getRule();


    /**
     * Lets the rule engine knows if this condition needs to be re-evaluated everytime and not memorize its result.
     *
     * @return true if always need to re-evaluate this condition for a rule.
     * @.category public-api
     * @since 2.0.0
     */
    boolean alwaysEvaluate();


    /**
     * Returns an array of class names this condition is depending on.
     * In case of hot deployment, the condition needs to be re-evaluated when the depends-on class has changed.
     *
     * @return array of class names on which this condition depends.
     */
    String[] getDependsOn();

}
