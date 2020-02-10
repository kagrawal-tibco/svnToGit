package com.tibco.cep.runtime.service.cluster.deploy.template.transaction;

import java.util.concurrent.ConcurrentHashMap;


/**
 * User: nprade
 * Date: 3/16/12
 * Time: 2:41 PM
 */
public class RuleTemplateDeploymentTransactionRegistry
        extends ConcurrentHashMap<Long, RuleTemplateDeploymentTransaction> {


    private RuleTemplateDeploymentTransactionRegistry() {
    }


    public static RuleTemplateDeploymentTransactionRegistry getInstance() {
        return LazySingletonHolder.SINGLETON;
    }


    private static class LazySingletonHolder {


        public static final RuleTemplateDeploymentTransactionRegistry SINGLETON =
                new RuleTemplateDeploymentTransactionRegistry();

    }


}
