package com.tibco.cep.runtime.service.cluster.deploy;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.*;
import com.tibco.cep.runtime.service.cluster.deploy.util.RuleTemplateInstanceDeployTool;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.deploy.Deployable;
import com.tibco.cep.runtime.service.om.api.Invocable;
import com.tibco.cep.runtime.service.om.api.InvocationService;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Set;

public class RuleTemplateDeployer implements RuleTemplateDeployerMBean, Deployable {

    private final Cluster cluster;
    private final String sequenceName;
    private final Logger logger;

    public RuleTemplateDeployer() throws Exception {

        this(CacheClusterProvider.getInstance().getCacheCluster());
    }

    public RuleTemplateDeployer(Cluster cluster) throws Exception {

        if (null == cluster) {
            throw new IllegalArgumentException("Cluster cannot be null.");
        }
        this.cluster = cluster;
        this.logger = cluster.getRuleServiceProvider().getLogger(RuleTemplateDeployer.class);
        this.sequenceName = RuleTemplateDeployer.class.getName() + "_" + cluster.getClusterName();
        cluster.getSequenceManager().createSequence(this.sequenceName, 0, Long.MAX_VALUE, 1000, false);
    }

    @Override
    public void deploy(Set<RuleTemplateDeploymentOperationDescriptor> operations) throws Exception {

        final InvocationService invoker = cluster.getDaoProvider().getInvocationService();
        final Set<GroupMember> groupMembers = cluster.getGroupMembershipService().getMembers();

        final long id = generateTransactionId();

        try {
            doInvoke(new RuleTemplateDeploymentTransactionCreate(id, operations), invoker, groupMembers, false);
            doInvoke(new RuleTemplateDeploymentTransactionPrepare(id), invoker, groupMembers, false);
            try {
                doInvoke(new RuleTemplateDeploymentTransactionCommit(id), invoker, groupMembers, false);
                //TODO: storage
            } catch (Throwable t) {
                doInvoke(new RuleTemplateDeploymentTransactionRollBack(id), invoker, groupMembers, true);
            }
        } finally {
            doInvoke(new RuleTemplateDeploymentTransactionClose(id), invoker, groupMembers, true);
        }
    }

    public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:service=RuleTemplateDeployer,name=RuleTemplateInstanceDeployer");
            StandardMBean mBean = new StandardMBean(this, RuleTemplateDeployerMBean.class);
            mbs.registerMBean(mBean, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doInvoke(RuleTemplateDeploymentInvocable invocable, InvocationService invoker, Set<GroupMember> groupMembers, boolean continueOnError) throws Exception {

        final String invocableName = invocable.getClass().getSimpleName();
        final long transactionId = invocable.getTransactionId();
        logger.log(Level.DEBUG, "Invoking %s[%s]", invocableName, transactionId);

        final StringBuilder failures = new StringBuilder();

        final Map<String, Invocable.Result> results = invoker.invoke(invocable, groupMembers);

        for (final Map.Entry<String, Invocable.Result> entry : results.entrySet()) {
            final String member = entry.getKey();
            final Invocable.Result result = entry.getValue();
            final Invocable.Status status = result.getStatus();
            final Exception e = result.getException();
            if (null != e) {
                logger.log(Level.ERROR, "Failed in %s[%s]: %s='%s'", e, invocableName, transactionId, member, status);
                failures.append(transactionId).append("='").append(status).append("' ");
                if (!continueOnError) {
                    break;
                }
            } else if (Invocable.Status.ERROR == status) {
                logger.log(Level.ERROR, "Failed in %s[%s]: %s='%s'", invocableName, transactionId, member, status);
                failures.append(transactionId).append("='").append(status).append("' ");
                if (!continueOnError) {
                    break;
                }
            } else {
                logger.log(Level.DEBUG, "%s[%s]: %s='%s'", invocableName, transactionId, member, status);
            }
        }

        if (failures.length() > 0) {
            throw new Exception("Failed in " + invocableName + "[" + transactionId + "]: " + failures);
        }
    }

    private long generateTransactionId() throws Exception {

        return cluster.getSequenceManager().nextSequence(sequenceName);
    }

    @Override
    public void loadAndDeployRuleTemplateInstances(String agentName) {
        logger.log(Level.INFO, "Cluster [%s] : Hot deployment of rule template instances requested.", cluster.getClusterName());

        try {
            RuleTemplateInstanceDeployTool.deployRuleTemplateInstances(this, agentName);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param agentName
     * @param projectName
     *            -> The project in which the RTI exists.
     * @param ruleTemplateInstanceFQN
     *            - The FQN of the RTI.
     */
    @Override
    public void loadAndDeployRuleTemplateInstance(String agentName, String projectName, String ruleTemplateInstanceFQN) {
        logger.log(Level.INFO, "Cluster [%s] : Hot deployment of rule template instance [%s] requested.", cluster.getClusterName(), ruleTemplateInstanceFQN);
        if (projectName == null) {
            throw new IllegalArgumentException("Project name parameter cannot be found");
        }
        if (ruleTemplateInstanceFQN == null) {
            throw new IllegalArgumentException("Rule Template Instance Fully Qualified Name parameter cannot be found");
        }
        try {
            RuleTemplateInstanceDeployTool.deployRuleTemplateInstance(this, agentName, projectName, ruleTemplateInstanceFQN);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unDeployRuleTemplateInstances(String agentName) {
        logger.log(Level.INFO, "Cluster [%s] : Hot undeployment of rule template instances requested.", cluster.getClusterName());
        try {
            RuleTemplateInstanceDeployTool.undeployRuleTemplateInstances(this, agentName);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param agentName
     * @param projectName
     *            -> The project in which the RTI exists.
     * @param ruleTemplateInstanceFQN
     *            - The FQN of the RTI.
     */
    @Override
    public void unDeployRuleTemplateInstances(String agentName, String projectName, String ruleTemplateInstanceFQN) {
        logger.log(Level.INFO, "Cluster [%s] : Hot undeployment of rule template instance [%s] requested.", cluster.getClusterName(), ruleTemplateInstanceFQN);
        if (projectName == null) {
            throw new IllegalArgumentException("Project name parameter cannot be found");
        }
        if (ruleTemplateInstanceFQN == null) {
            throw new IllegalArgumentException("Rule Template Instance Fully Qualified Name parameter cannot be found");
        }
        try {
            RuleTemplateInstanceDeployTool.undeployRuleTemplateInstance(this, agentName, projectName, ruleTemplateInstanceFQN);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cluster getCluster() {
        return cluster;
    }
}
