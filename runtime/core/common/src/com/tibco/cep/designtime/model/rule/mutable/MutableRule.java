package com.tibco.cep.designtime.model.rule.mutable;


import java.util.Set;

import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.xml.datamodel.XiNode;


/**
 * Defines the interface for a BusinessEvents Rule.
 *
 * @author ishaan
 * @version Mar 17, 2004 10:01:02 AM
 */
public interface MutableRule extends Rule, MutableCompilable {


    /**
     * Sets the priority of this Rule.
     *
     * @param priority The new priority for this Rule. If priority is less than MIN_PRIORITY or greater than MAX_PRIORITY, it is automatically set to MIN_PRIORITY or MAX_PRIORITY, respectively.
     */
    public void setPriority(int priority);


    public void setTestInterval(long intervalInMilliseconds);


    public void setStartTime(long startTimeInMilliseconds);


    public void setDeclarations(Symbols declarations);


    public void addDeclaration(String identifier, String entityPath);


    public boolean deleteDeclaration(String identifier);


    public void setRequeue(boolean doesRequeue);


    public void setRequeueCount(int maxRules);


    public void setChainingPolicy(boolean forwardChain, boolean backwardChain);


    public void setRuleSet(RuleSet ruleSet) throws ModelException;


    public void setRequeueIdentifiers(Set identifierNames);


    void setFunctionTypeAsCondition(boolean isFunctionACondition);


    void setCompilationStatus(int status);


    public void setTemplate(XiNode template);


    public void setAuthor(String author);
}
