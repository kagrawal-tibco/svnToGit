package com.tibco.cep.designtime.model.element.stategraph;


import com.tibco.cep.designtime.model.rule.Rule;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 26, 2006
 * Time: 4:08:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StateTransition extends StateLink {


    /**
     * Get the "from" State (where the link originates) for this link.
     *
     * @return The "from" State (where the link originates) for this link.
     */
    State getFromState();


    /**
     * Get the guard (a Rule) for this link.  This is the rule that determines when
     * this link will be followed.
     *
     * @param createIfNeeded Should this method create a new Rule (and return it) if none currently set.
     * @return The guard (a Rule) for this link.
     */
    Rule getGuardRule(
            boolean createIfNeeded);


    /**
     * Get the "to" State (where the link terminates) for this link.
     *
     * @return The "to" State (where the link terminates) for this link.
     */
    State getToState();


    /**
     * Is this transition a "lambda" transition (has no condition and is taken immediately).
     *
     * @return true if this transition is a "lambda" transition, otherwise false.
     */
    boolean isLambda();


    int getPriority();


    boolean forwardCorrelates();
}
