package com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase;

import java.util.Map;

import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.RuleTemplateDeploymentTransaction;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.RuleTemplateDeploymentTransactionRegistry;


/**
 * User: nprade
 * Date: 3/16/12
 * Time: 3:13 PM
 */
public class RuleTemplateDeploymentTransactionRollBack
        extends RuleTemplateDeploymentInvocable {


    public RuleTemplateDeploymentTransactionRollBack(
            long transactionId)
            throws Exception {

        super(transactionId);
    }


    @Override
    public Object invoke(
            Map.Entry entry)
            throws Exception {

        final long id = getTransactionId();

        final RuleTemplateDeploymentTransaction transaction =
                RuleTemplateDeploymentTransactionRegistry.getInstance().get(id);

        if (null == transaction) {
            throw new IllegalStateException("Cannot roll back transaction that does not exist: " + id);
        }

        transaction.rollback();

        return null;
    }


}
