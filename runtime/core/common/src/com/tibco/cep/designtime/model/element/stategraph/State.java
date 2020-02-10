package com.tibco.cep.designtime.model.element.stategraph;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.rule.Compilable.CodeBlock;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 25, 2006
 * Time: 10:35:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface State extends StateVertex {


    int NO_ACTION_TIMEOUT_POLICY = 0;
    int NON_DETERMINISTIC_STATE_TIMEOUT_POLICY = 1;
    int DETERMINISTIC_STATE_POLICY = 2;


    int getTimeoutPolicy();


    boolean isInternalTransitionEnabled();


    /**
     * Get the action to perform on entry to this state.
     *
     * @return The action to perform on entry to this state.
     */
    Rule getEntryAction(boolean create) throws ModelException;


    /**
     * Get the action to perform on exit from this state.
     *
     * @return The action to perform on exit from this state.
     */
    Rule getExitAction(boolean create) throws ModelException;


    Rule getTimeoutAction(boolean create) throws ModelException;


    RuleFunction getTimeoutExpression();


    /**
     * Get the internal transition on this state (if any).
     *
     * @return The internal transition on this state (if any).
     */
    Rule getInternalTransition(boolean create) throws ModelException;


    State getTimeoutState();
    
    /**
     * returns the entry action code block offsets
     * @since 4.0
     * @return
     */
    CodeBlock getEntryCodeBlock();
    
    /**
     * returns the exit action code block offsets
     * @since 4.0
     * @return
     */
    CodeBlock getExitCodeBlock();
    
    /**
     * returns the timeout action code block offsets
     * @since 4.0
     * @return
     */
    CodeBlock getTimeoutCodeBlock();
}
