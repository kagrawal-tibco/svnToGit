package com.tibco.cep.kernel.model.rule;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:14:19 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents an action within a rule.
 *
 * @see Rule
 * @version 2.0.0
 * @since 2.0.0
 * @.category public-api
 */
//TODO enhance description
public interface Action {


    /**
     * Gets all the <code>Identifier</code> for this action.
     * This is the same as <code>getRule().getIdentifiers()</code>.
     *
     * @return array of the Identifiers
     * @since 2.0.0
     */
    Identifier[] getIdentifiers();  //todo - remove this?


    /**
     * Executes the expression of this action with the given objects.
     *
     * @param objects set of <code>Object</code> parameters for the expression to execute.
     * @.category public-api
     * @since 2.0.0
     */
    void execute(Object[] objects);


    /**
     * Gets the rule that contains this Action.
     *
     * @return the Rule of this Action.
     * @since 2.0.0
     */
    Rule getRule();
}
