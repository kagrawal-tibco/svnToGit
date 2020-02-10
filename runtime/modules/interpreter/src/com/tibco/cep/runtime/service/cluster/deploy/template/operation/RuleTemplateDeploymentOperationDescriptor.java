package com.tibco.cep.runtime.service.cluster.deploy.template.operation;

import java.io.Serializable;


/**
 * User: nprade
 * Date: 3/14/12
 * Time: 4:57 PM
 */
public abstract class RuleTemplateDeploymentOperationDescriptor
        implements Serializable {


    private final String ruleSessionName;


    protected RuleTemplateDeploymentOperationDescriptor(
            String ruleSessionName) {

        this.ruleSessionName = ruleSessionName;
    }


    public String getRuleSessionName() {
        return this.ruleSessionName;
    }

}
