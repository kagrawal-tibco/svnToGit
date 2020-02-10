package com.tibco.cep.runtime.service.cluster.deploy;

/**
 * Rule Template Instance mbean API's
 * 
 * @author vpatil
 */
public interface RuleTemplateDeployerMBean {

    /**
     * Deploy all rule template instances into this agent.
     * @param agentName
     */
    public void loadAndDeployRuleTemplateInstances(String agentName);

    /**
     * Deploy a rule template instance into this agent.
     * @param agentName
     * @param projectName -> The project in which the RTI exists.
     * @param ruleTemplateInstanceFQN  - The FQN of the RTI.
     */
    public void loadAndDeployRuleTemplateInstance(String agentName, String projectName, String ruleTemplateInstanceFQN);

    /**
     * Undeploy all rule template instances running in this agent
     * @param agentName
     */
    public void unDeployRuleTemplateInstances(String agentName);

    /**
     * Undeploy a rule template instance running in this agent.
     * @param agentName
     * @param projectName -> The project in which the RTI exists.
     * @param ruleTemplateInstanceFQN  - The FQN of the RTI.
     */
    public void unDeployRuleTemplateInstances(String agentName, String projectName, String ruleTemplateInstanceFQN);
}
