package com.tibco.cep.bpmn.runtime.activity;

import com.tibco.cep.kernel.model.entity.Entity;

/*
* Author: Suresh Subramani / Date: 2/12/12 / Time: 5:31 PM
*/
public interface TriggerIdentifier {

    /**
     * get the Type of the Identifier
     * @return
     */
    Class<? extends Entity> getType();

    /**
     * get the Identifier name
     * @return
     */
    String getName();
}
