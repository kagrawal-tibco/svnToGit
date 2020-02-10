package com.tibco.cep.designtime.model.rule.mutable;


import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.rule.Compilable;


/**
 * @author ishaan
 * @version Jan 19, 2005, 6:12:42 PM
 */
public interface MutableCompilable extends Compilable, MutableScopeContainer, MutableEntity {


    /**
     * Sets the return type for this RuleFunction.
     *
     * @param type The path of the return type Entity associated with this function.
     */
    public void setReturnType(String type);


    public void setConditionText(String text);


    public void setActionText(String text);


    public void setCompilationStatus(int status);

}
