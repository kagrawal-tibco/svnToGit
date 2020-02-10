/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.*;
import com.tibco.be.util.config.factories.ClusterConfigFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

import java.io.IOException;
import java.util.*;


/**
 * @author Nick
 *
 */
public class CddConfigUtil {
	
	private ClusterConfig cddConfig;
	private Logger logger;
	private static Map<String, CddConfigUtil> instances = new HashMap<String, CddConfigUtil>();
	
	public CddConfigUtil(String cddPath) throws Exception {
        RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
        if (ruleSession != null) {
            RuleServiceProvider currRuleServiceProvider = ruleSession.getRuleServiceProvider();
            logger = currRuleServiceProvider.getLogger(CddConfigUtil.class);
        } else {
            logger = null;
        }

    	try{
    		this.cddConfig = new ClusterConfigFactory().newConfig(cddPath);
    	}
        catch(IOException ioe) {
            if (logger != null) logger.log(Level.ERROR, "Cannot load the master CDD file: %s", cddPath);
            throw ioe;
        }
        catch(Exception e) {
            if (logger != null) logger.log(Level.ERROR, "Cannot load the master CDD file: %s", cddPath);
            throw e;
        }
    	if(cddConfig == null){
            if (logger != null) logger.log(Level.ERROR, "No cluster configured in the master CDD file: %s", cddPath);
    		throw new RuntimeException("No cluster configured in the master CDD file: "+cddPath);
    	}
	}
/*
	public CddConfigUtil() throws RuntimeException{
        RuleServiceProvider currRuleServiceProvider = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
        logger = currRuleServiceProvider.getLogger(this.getClass().getPackage().getName());
        this.cddPath = currRuleServiceProvider.getProperties().getProperty("be.mm.target.cdd.file");
        if (cddPath == null || cddPath.trim().length() == 0){
            logger.log(Level.ERROR,"Missing or invalid be.mm.target.cdd.file");
            throw new RuntimeException("be.mm.target.cdd.file is not specifed!");
        }
    	try{
    		this.cddConfig = new ClusterConfigFactory().newConfig(cddPath);
    	}
    	catch(Exception e){
    		logger.log(Level.ERROR, "cannot parse the target CDD file: "+cddPath);
    		throw new RuntimeException("cannot parse the target CDD file: "+cddPath);
    	}
    	if(cddConfig == null){
    		logger.log(Level.ERROR, "cannot parse the target CDD file: "+cddPath);
    		throw new RuntimeException("cannot parse the target CDD file: "+cddPath);    		
    	} 
	}
*/
	public static CddConfigUtil getInstance(String cddPath) throws Exception {
        CddConfigUtil instance = instances.get(cddPath);
		if(instance == null){
			instance = new CddConfigUtil(cddPath);
            instances.put(cddPath, instance);
		}
		return instance;		
	}
	
	public ProcessingUnitConfig getPUConfig(String puid){
		if(puid == null || puid.isEmpty()){
			return null;
		}
		return (ProcessingUnitConfig) CddTools.findById(
				cddConfig.getProcessingUnits().getProcessingUnit(), puid);
	}
	
	public String getPUProperty(String puid, String propKey){
		if(puid == null || puid.isEmpty()){
			return null;
		}
		if(propKey == null || propKey.isEmpty()){
			return null;
		}
		ProcessingUnitConfig pu = this.getPUConfig(puid);
		Properties prop = (Properties) pu.toProperties();
		return prop.getProperty(propKey);
	}

    public List<String> getPUIds(){
        List<String> puIds = new LinkedList<String>();
		ProcessingUnitsConfig pus = cddConfig.getProcessingUnits();
		for (final ProcessingUnitConfig pu : pus.getProcessingUnit()) {
            puIds.add(pu.getId());
		}
		return puIds;
	}
    
	public String getPUJMXPort(String puid){
		return getPUProperty(puid, "be.mm.jmxremote.port");
	}
	
	public ArrayList<BemmAgent> getAgentList(String puid){
		
		ArrayList<BemmAgent> bemmAgentList = new ArrayList<BemmAgent>();
		ProcessingUnitConfig pu = this.getPUConfig(puid);
		if(pu == null){
			if (logger != null)
                logger.log(Level.ERROR, "No Process Unit found in target CDD file for PUID: "+puid);
			return null;
		}
		AgentsConfig agents = pu.getAgents();
		for (final AgentConfig agent : agents.getAgent()) {
            String type = null;
            
            AgentClassConfig typeClass = agent.getRef();
            String agentName = typeClass.getId();
            if(typeClass instanceof InferenceAgentClassConfig){
            	type = "inference";
            }
            else if(typeClass instanceof QueryAgentClassConfig){
            	type = "query";
            }
            else if(typeClass instanceof CacheAgentClassConfig){
            	type = "cache";
            }
            else{
            	type = "dashboard";
            }
            
            bemmAgentList.add(new BemmAgent(type, agentName));
		}
		return bemmAgentList;
	}

    public ClusterConfig getClusterConfig() {
        return cddConfig;
    }
    
//    public static void main(String[] args) {
//        try {
//    	    CddConfigUtil util = new CddConfigUtil("c:/Users/Nick/Desktop/BEMM_Nicolas.cdd");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    	int i = 1;
//    }
}
