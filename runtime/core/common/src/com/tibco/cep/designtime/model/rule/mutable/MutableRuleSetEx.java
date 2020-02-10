package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.rule.RuleSetEx;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: May 19, 2006
 * Time: 7:25:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableRuleSetEx extends RuleSetEx, MutableRuleSet {


    /**
     * Create a rule of the appropriate type
     *
     * @param name             - A name passed by the user
     * @param renameOnConflict - Should the ontology assign a name in case of conflict
     * @param ruleType         - The type of rule the user wants. @see Rule Types
     * @return - an appropriate instance of a rule
     * @throws com.tibco.cep.designtime.model.ModelException
     *
     */
    public MutableRule createRule(String name, boolean renameOnConflict, int ruleType) throws ModelException;


}
