package com.tibco.cep.runtime.service.cluster.deploy.util;


import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.interpreter.template.TemplateConfigFileLoader;
import com.tibco.cep.interpreter.template.TemplateConfigRegistry;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.ClusterCapability;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateSetDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateUnsetDescriptor;
import com.tibco.cep.runtime.service.deploy.Deployable;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/3/12
 * Time: 3:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleTemplateInstanceDeployTool {

    /**
     *
     * @param ruleTemplateDeployer
     * @param ruleSessionName
     * @throws Exception
     */
    public static void deployRuleTemplateInstances(Deployable ruleTemplateDeployer, String ruleSessionName) throws Exception {
        String deploymentDir = (ruleTemplateDeployer.getCluster() != null) ? ClusterCapability.getValue(ruleTemplateDeployer.getCluster().getCapabilities(), ClusterCapability.RTIDEPLOYERDIR, String.class) : getDeploymentDirectory();
        File projectDirFile = check(deploymentDir);
        doDeploy(ruleTemplateDeployer, ruleSessionName, projectDirFile, true);
    }

    /**
     *
     * @param ruleTemplateDeployer
     * @param ruleSessionName
     * @throws Exception
     */
    public static void undeployRuleTemplateInstances(Deployable ruleTemplateDeployer, String ruleSessionName) throws Exception {
    	String deploymentDir = (ruleTemplateDeployer.getCluster() != null) ? ClusterCapability.getValue(ruleTemplateDeployer.getCluster().getCapabilities(), ClusterCapability.RTIDEPLOYERDIR, String.class) : getDeploymentDirectory();
        File projectDirFile = check(deploymentDir);
        doUndeploy(ruleTemplateDeployer, ruleSessionName, projectDirFile, true);
    }

    /**
     *
     * @param ruleTemplateDeployer
     * @param ruleSessionName
     * @param ruleTemplateInstanceFQN
     * @throws Exception
     */
    public static void deployRuleTemplateInstance(Deployable ruleTemplateDeployer, String ruleSessionName, String projectName, String ruleTemplateInstanceFQN) throws Exception {
    	String deploymentDir = (ruleTemplateDeployer.getCluster() != null) ? ClusterCapability.getValue(ruleTemplateDeployer.getCluster().getCapabilities(), ClusterCapability.RTIDEPLOYERDIR, String.class) : getDeploymentDirectory();
        
        File projectDirFile = check(deploymentDir);        
        if (projectDirFile.getPath().endsWith(projectName)) {
	        File RTIFile = new File(projectDirFile, ruleTemplateInstanceFQN + ".ruletemplateinstance");
	        doDeploy(ruleTemplateDeployer, ruleSessionName, RTIFile, false);
        } else {
        	throw new RuntimeException("Deployment directory[" + deploymentDir +"] missing valid project name[" + projectName + "]");
        }
    }

    /**
    *
    * @param ruleTemplateDeployer
    * @param ruleSessionName
    * @param ruleTemplateInstanceFQN
    * @throws Exception
    */
   public static void undeployRuleTemplateInstance(Deployable ruleTemplateDeployer, String ruleSessionName, String projectName, String ruleTemplateInstanceFQN) throws Exception {
	   String deploymentDir = (ruleTemplateDeployer.getCluster() != null) ? ClusterCapability.getValue(ruleTemplateDeployer.getCluster().getCapabilities(), ClusterCapability.RTIDEPLOYERDIR, String.class) : getDeploymentDirectory();
       
       File projectDirFile = check(deploymentDir);
       if (projectDirFile.getPath().endsWith(projectName)) {
    	   File RTIFile = new File(projectDirFile, ruleTemplateInstanceFQN + ".ruletemplateinstance");
    	   doUndeploy(ruleTemplateDeployer, ruleSessionName, RTIFile, false);
       } else {
    	   throw new RuntimeException("Deployment directory[" + deploymentDir +"] missing valid project name[" + projectName + "]");
       }
   }

    /**
     * Perform deployment of one or more RTIs
     *
     * @param ruleTemplateDeployer
     * @param ruleSessionName
     * @param sourceFile
     * @param recursive
     * @throws Exception
     */
    private static void doDeploy(Deployable ruleTemplateDeployer, String ruleSessionName, File sourceFile, boolean recursive) throws Exception {
        TemplateConfigRegistry templateConfigRegistry = new CustomTemplateConfigRegistry();
        TemplateConfigFileLoader templateConfigFileLoader = new TemplateConfigFileLoader(templateConfigRegistry);
        templateConfigFileLoader.loadAll(sourceFile, recursive);
        checkSession(ruleTemplateDeployer.getCluster(), ruleSessionName);
        //Get and deploy all ruletemplateinstances loaded
        deployRuleTemplateInstances(ruleTemplateDeployer, ruleSessionName, templateConfigRegistry.getTemplateConfigs());
    }

    /**
    *
    * @param ruleTemplateDeployer
    * @param ruleSessionName
    * @param ruleTemplateInstances
    * @throws Exception
    */
   public static void deployRuleTemplateInstances(Deployable ruleTemplateDeployer, String ruleSessionName, Collection<RuleTemplateInstance> ruleTemplateInstances) throws Exception {
       Set<RuleTemplateInstance> ruleTemplateInstanceSet = new HashSet<RuleTemplateInstance>(ruleTemplateInstances);
       Set<RuleTemplateDeploymentOperationDescriptor> operations = new HashSet<RuleTemplateDeploymentOperationDescriptor>();
       operations.add(new RuleTemplateSetDescriptor(ruleSessionName, ruleTemplateInstanceSet));
       // Deploy the requested ops.
       ruleTemplateDeployer.deploy(operations);
   }

    /**
     * Perform undeployment of one or more RTIs
     *
     * @param ruleTemplateDeployer
     * @param ruleSessionName
     * @param sourceFile
     * @param recursive
     * @throws Exception
     */
    private static void doUndeploy(Deployable ruleTemplateDeployer, String ruleSessionName, File sourceFile, boolean recursive) throws Exception {
        TemplateConfigRegistry templateConfigRegistry = new CustomTemplateConfigRegistry();
        TemplateConfigFileLoader templateConfigFileLoader = new TemplateConfigFileLoader(templateConfigRegistry);
        templateConfigFileLoader.loadAll(sourceFile, recursive);
        checkSession(ruleTemplateDeployer.getCluster(), ruleSessionName);
        //Get and deploy all ruletemplateinstances loaded
        undeployRuleTemplateInstances(ruleTemplateDeployer, ruleSessionName, templateConfigRegistry.getTemplateConfigs());
    }

    /**
    *
    * @param ruleTemplateDeployer
    * @param ruleSessionName
    * @param ruleTemplateInstances
    * @throws Exception
    */
   public static void undeployRuleTemplateInstances(Deployable ruleTemplateDeployer, String ruleSessionName, Collection<RuleTemplateInstance> ruleTemplateInstances) throws Exception {
        Set<String> ruleTemplateInstanceIdSet = new HashSet<String>();
        for (RuleTemplateInstance ruleTemplateInstance : ruleTemplateInstances) {
            ruleTemplateInstanceIdSet.add(ruleTemplateInstance.getId());
        }
        Set<RuleTemplateDeploymentOperationDescriptor> operations = new HashSet<RuleTemplateDeploymentOperationDescriptor>();
        operations.add(new RuleTemplateUnsetDescriptor(ruleSessionName, ruleTemplateInstanceIdSet));
        // Deploy the requested ops.
        ruleTemplateDeployer.deploy(operations);
   }

    /**
     * Basic file checks
     *
     * @param deploymentDir
     * @return
     * @throws Exception
     */
    private static File check(String deploymentDir) throws Exception {
        if (deploymentDir == null) {
            throw new Exception(String.format("Rule template deploy source dir [%s] not found", deploymentDir));
        }
        File sourceDirFile = new File(deploymentDir);
        if (!sourceDirFile.isDirectory()) {
            throw new Exception(String.format("Rule template deploy source dir [%s] is not a directory", deploymentDir));
        }
        if (!sourceDirFile.canRead()) {
            throw new Exception(String.format("Rule template deploy source dir [%s] cannot be read", sourceDirFile));
        }
        return sourceDirFile;
    }

    /**
     * Check for existence of a {@linkplain RuleSession} based on the given name.
     *
     * @param cluster
     * @param ruleSessionName
     * @throws Exception
     */
    private static void checkSession(Cluster cluster, String ruleSessionName) throws Exception {
        RuleServiceProvider RSP = (cluster != null) ? cluster.getRuleServiceProvider() : RuleServiceProviderManager.getInstance().getDefaultProvider();
        if (RSP == null) throw new Exception("Rule Service Provider null or default provider not set.");
        
        RuleSession ruleSession = RSP.getRuleRuntime().getRuleSession(ruleSessionName);
        if (ruleSession == null) {
            throw new Exception(String.format("Rule Session with Name [%s] does not exist in this cluster", ruleSessionName));
        }
    }
    
    private static String getDeploymentDirectory() {
    	RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
    	String rtiDeploymentDir = RSP.getProperties().getProperty(SystemProperty.CLUSTER_RULETEMPLATE_INSTANCE_DEPLOYER_DIR.getPropertyName());
    	rtiDeploymentDir = RSP.getGlobalVariables().substituteVariables(rtiDeploymentDir).toString();
    	return rtiDeploymentDir;
    }

    static class CustomTemplateConfigRegistry extends TemplateConfigRegistry {

    }
}
