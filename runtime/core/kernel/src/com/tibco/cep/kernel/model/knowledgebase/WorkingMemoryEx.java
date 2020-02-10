package com.tibco.cep.kernel.model.knowledgebase;

import com.tibco.cep.kernel.model.rule.Rule;

/*
* Author: Suresh Subramani / Date: 5/2/12 / Time: 6:55 PM
*/
public interface WorkingMemoryEx extends WorkingMemory {

    /**
     * Add a rule dynamically
     * @param rule
     * @return
     * @throws SetupException
     */
    Rule addRule(Rule rule) throws SetupException;

    /**
     * Remove a rule dynamically
     * @param rule
     * @return
     * @throws SetupException
     */
    Rule removeRule(Rule rule) throws SetupException;
}
