package com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.interpreter.template.TemplatedRuleFactory;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.RuleTemplateDeploymentTransaction;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.RuleTemplateDeploymentTransactionRegistry;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;


/**
 * User: nprade
 * Date: 3/16/12
 * Time: 3:13 PM
 */
public class RuleTemplateDeploymentTransactionCreate
        extends RuleTemplateDeploymentInvocable {


    private static final Map<RuleServiceProvider, TemplatedRuleFactory> RSP_TO_TRF =
            new HashMap<RuleServiceProvider, TemplatedRuleFactory>();

    private Set<RuleTemplateDeploymentOperationDescriptor> operations;


    public RuleTemplateDeploymentTransactionCreate(
            long transactionId,
            Set<RuleTemplateDeploymentOperationDescriptor> operations)
            throws Exception {

        super(transactionId);
        this.operations = Collections.unmodifiableSet(operations);
    }


    public Set<RuleTemplateDeploymentOperationDescriptor> getOperations() {
        return operations;
    }


    @Override
    public Object invoke(
            Map.Entry entry)
            throws Exception {

        final long id = getTransactionId();

        final RuleTemplateDeploymentTransaction transaction =
                new RuleTemplateDeploymentTransaction(id, getTemplatedRuleFactory(), operations);

        if (null != RuleTemplateDeploymentTransactionRegistry.getInstance().putIfAbsent(id, transaction)) {
            throw new IllegalStateException("Cannot create transaction that already exists: " + id);
        }

        return null;
    }


    private TemplatedRuleFactory getTemplatedRuleFactory()
            throws Exception {

        TemplatedRuleFactory trf;

        final RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        synchronized (RSP_TO_TRF) {
            trf = RSP_TO_TRF.get(rsp);
            if (null == trf) {
                trf = new TemplatedRuleFactory(rsp);
                RSP_TO_TRF.put(rsp, trf);
            }
        }
        return trf;
    }


}
