package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.rule.RuleSet;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jul 20, 2004
 * Time: 8:29:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MutableRuleSet extends RuleSet, MutableEntity {


    /**
     * Creates a Rule of the given name.  Names must be unique within a RuleSet.
     *
     * @param name             The name for the new Rule.
     * @param renameOnConflict If true, will auto-generate a name for this Rule.
     * @return The newly created Rule.
     * @throws com.tibco.cep.designtime.model.ModelException
     *          Thrown if this RuleSet contains a Rule with the given name.
     */
    public MutableRule createRule(String name, boolean renameOnConflict, boolean isAFunction) throws ModelException;


    /**
     * Deletes a Rule of the given name.
     *
     * @param name The name of the Rule to delete.
     */
    public void deleteRule(String name);


    /**
     * Deletes all Rules from this RuleSet.
     */
    public void clear();
}