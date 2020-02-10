package com.tibco.cep.designtime.model.rule.mutable;

import com.tibco.cep.designtime.model.rule.RuleFunction;


/**
 * @author ishaan
 * @version Jan 18, 2005, 4:59:57 PM
 */
public interface MutableRuleFunction extends RuleFunction, MutableCompilable {


    /**
     * Sets whether or not to allow this RuleFunction in the condition block of a Rule.
     *
     * @param actionOnly
     */
    public void setValidity(Validity actionOnly);


    /**
     * Sets the associated type of an identifier.
     *
     * @param identifier
     * @param type
     */
    public void setArgumentType(String identifier, String type);


    /**
     * Deletes an identifier.
     *
     * @param identifier
     */
    public boolean deleteIdentifier(String identifier);


    /**
     * Sets the body text of a RuleFunction.
     *
     * @param body
     */
    public void setBody(String body);


    public void setVirtual(boolean isVirtual);


}
