package com.tibco.cep.runtime.service.cluster.deploy.template.operation;


import java.util.Collections;
import java.util.Set;


/**
 * User: nprade
 * Date: 3/14/12
 * Time: 5:20 PM
 */
public class RuleTemplateUnsetDescriptor
        extends RuleTemplateDeploymentOperationDescriptor {


    private final Set<String> ids;


    public RuleTemplateUnsetDescriptor(
            String ruleSessionName,
            Set<String> ids) {

        super(ruleSessionName);
        this.ids = Collections.unmodifiableSet(ids);
    }


    public Set<String> getIds() {
        return this.ids;
    }

}
