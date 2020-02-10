package com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase;

import com.tibco.cep.runtime.service.om.api.Invocable;


/**
 * User: nprade
 * Date: 3/16/12
 * Time: 3:11 PM
 */
public abstract class RuleTemplateDeploymentInvocable
        implements Invocable {


    private final long transactionId;


    public RuleTemplateDeploymentInvocable(
            long transactionId)
            throws Exception {

        this.transactionId = transactionId;
    }


    public long getTransactionId() {
        return transactionId;
    }


}
