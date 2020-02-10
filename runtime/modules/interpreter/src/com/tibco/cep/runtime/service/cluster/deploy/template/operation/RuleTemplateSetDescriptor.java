package com.tibco.cep.runtime.service.cluster.deploy.template.operation;

import java.util.Collections;
import java.util.Set;

import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;


/**
 * User: nprade
 * Date: 3/14/12
 * Time: 5:20 PM
 */
public class RuleTemplateSetDescriptor
        extends RuleTemplateDeploymentOperationDescriptor {


    private final Set<RuleTemplateInstance> configs;


    public RuleTemplateSetDescriptor(
            String ruleSessionName,
            Set<RuleTemplateInstance> configs) {

        super(ruleSessionName);
        this.configs = Collections.unmodifiableSet(configs);
    }


    public Set<RuleTemplateInstance> getConfigs() {
        return this.configs;
    }


}
