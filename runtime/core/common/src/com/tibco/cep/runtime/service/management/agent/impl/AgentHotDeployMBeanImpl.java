package com.tibco.cep.runtime.service.management.agent.impl;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.deploy.DefaultHotDeployer;
import com.tibco.cep.runtime.service.cluster.deploy.LoadExternalClasses;
import com.tibco.cep.runtime.service.management.agent.AgentHotDeployMBean;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

public class AgentHotDeployMBeanImpl implements AgentMBeansSetter, AgentHotDeployMBean
{
	protected String agentName;
	protected RuleServiceProvider ruleServiceProvider;
	protected Logger logger;
	
	@Override
	public void loadAndDeploy() {
		try {
			loadAllDTsFromDirectory(ruleServiceProvider, logger);
		} catch (Exception ex) {
		    logger.log(Level.WARN, ex, "Hot Deployment Request failed");
		}
	}

	public static void loadAllDTsFromDirectory(RuleServiceProvider rsp, Logger logger) throws Exception {
		logger.log(Level.INFO, "Hot Deployment Request, Loading Classes From File System");
	    String classesDir = rsp.getProperties().getProperty(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName());
    	if (classesDir != null) {
    		classesDir = rsp.getGlobalVariables().substituteVariables(classesDir).toString();
		    String packageExclusions = rsp.getProperties().getProperty(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PACKAGE_EXCLUSIONS.getPropertyName());    
	        Map<String, byte[]> classes = LoadExternalClasses.loadClasses(classesDir, packageExclusions);

	        if (classes.size() == 0) {
	            logger.log(Level.WARN, "No classes present in the directory to load");
	        } else {
	        	LoadExternalClasses.redefineClasses(classes, rsp, null, false);
	        }
    	}
    }


	@Override
	public void loadAndDeploy(String vrfURI, String implName) {
        try {
            logger.log(Level.INFO, "Hot Deployment Request, Loading Class From File System");
            String classesDir = ruleServiceProvider.getProperties().getProperty(SystemProperty.CLUSTER_EXTERNAL_CLASSES_PATH.getPropertyName());
            if (classesDir != null) {
            	classesDir = ruleServiceProvider.getGlobalVariables().substituteVariables(classesDir).toString();
            	String className = LoadExternalClasses.resolveClass(ruleServiceProvider.getProject().getOntology().getRuleFunction(vrfURI), implName, logger);
                byte[] classBytes = LoadExternalClasses.readClassFromArchiveOrDirectory(classesDir, className);
                HashMap<String, byte[]> classes = new HashMap(1);
                classes.put(className, classBytes);
                LoadExternalClasses.redefineClasses(classes, ruleServiceProvider, null, false);
            }

        } catch (Exception ex) {
            logger.log(Level.WARN, ex, "Loading/deploying Cache Cluster failed");
        }
	}
	
	@Override
	public void unloadClass(String vrfURI, String implName) throws Exception {
		DefaultHotDeployer.unloadExternalClass(ruleServiceProvider, vrfURI, implName);
	}

	@Override
	public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
		this.ruleServiceProvider = ruleServiceProvider;
		
	}

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
		
	}

	@Override
	public void setCacheAgent(CacheAgent cacheAgent) {
		//this class is only for memory OM 
	}

	@Override
	public void setAgentName(String agentName) {
		this.agentName = agentName;
		
	}
}
