package com.tibco.rta.service.metric;

import com.tibco.rta.Fact;
import com.tibco.rta.common.service.FactMessageContext;

public interface FactListener<C extends FactMessageContext> {

    /**
     *
     * @param messageContext
     * @param fact
     */
	void onFactAsserted(C messageContext, Fact fact);

}
