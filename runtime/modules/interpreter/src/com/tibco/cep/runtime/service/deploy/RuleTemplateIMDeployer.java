/**
 * 
 */
package com.tibco.cep.runtime.service.deploy;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.RuleTemplateDeploymentTransactionClose;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.RuleTemplateDeploymentTransactionCommit;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.RuleTemplateDeploymentTransactionCreate;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.RuleTemplateDeploymentTransactionPrepare;
import com.tibco.cep.runtime.service.cluster.deploy.template.transaction.phase.RuleTemplateDeploymentTransactionRollBack;
import com.tibco.cep.runtime.service.cluster.deploy.util.RuleTemplateInstanceDeployTool;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author vpatil
 *
 */
public class RuleTemplateIMDeployer implements RuleTemplateDeployerMBean, Deployable {
	private final Logger logger;
	
	public RuleTemplateIMDeployer(RuleServiceProvider rsp) {
		this.logger = rsp.getLogger(RuleTemplateIMDeployer.class);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean#loadAndDeployRuleTemplateInstances(java.lang.String)
	 */
	@Override
	public void loadAndDeployRuleTemplateInstances(String agentName) {
		logger.log(Level.INFO, "Hot deployment of rule template instances requested.");

        try {
            RuleTemplateInstanceDeployTool.deployRuleTemplateInstances(this, agentName);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean#loadAndDeployRuleTemplateInstance(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void loadAndDeployRuleTemplateInstance(String agentName, String projectName,
			String ruleTemplateInstanceFQN) {
		logger.log(Level.INFO, "Hot deployment of rule template instance [%s] requested.", ruleTemplateInstanceFQN);
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean#unDeployRuleTemplateInstances(java.lang.String)
	 */
	@Override
	public void unDeployRuleTemplateInstances(String agentName) {
		logger.log(Level.INFO, "Hot undeployment of rule template instances requested.");
        try {
            RuleTemplateInstanceDeployTool.undeployRuleTemplateInstances(this, agentName);
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error invoking MBean operation", e);
            throw new RuntimeException(e);
        }
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean#unDeployRuleTemplateInstances(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void unDeployRuleTemplateInstances(String agentName, String projectName, String ruleTemplateInstanceFQN) {
		logger.log(Level.INFO, "Hot undeployment of rule template instance [%s] requested.", ruleTemplateInstanceFQN);
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
	
	public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:dir=Hot Deploy,Name=Rule Template Instance Deployer");
            StandardMBean mBean = new StandardMBean(this, RuleTemplateDeployerMBean.class);
            mbs.registerMBean(mBean, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	@Override
	public void deploy(Set<RuleTemplateDeploymentOperationDescriptor> operations) throws Exception {
		final long id = System.nanoTime();
		try {
			try {
			new RuleTemplateDeploymentTransactionCreate(id, operations).invoke(null);
			new RuleTemplateDeploymentTransactionPrepare(id).invoke(null);
				new RuleTemplateDeploymentTransactionCommit(id).invoke(null);
			} catch (Throwable t) {
				t.printStackTrace();
				new RuleTemplateDeploymentTransactionRollBack(id).invoke(null);
			}
		} finally {
			new RuleTemplateDeploymentTransactionClose(id).invoke(null);
		}
	}
	
	@Override
	public Cluster getCluster() {
		return null;
	}

}
