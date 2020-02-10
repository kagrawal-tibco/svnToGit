/**
 * 
 */
package com.tibco.cep.bemm.service.cluster;

import java.util.ArrayList;
import java.util.LinkedList;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.impl.DelegatedQueryOM;
import com.tibco.cep.query.service.impl.MasterHelper;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.QueryAgent;
import com.tibco.cep.query.stream.impl.rete.service.QueryAgentFactory;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.MAgentFactory;
import com.tibco.cep.runtime.service.cluster.agent.NewInferenceAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.GvCommonUtils;

/**
 * @author Nick
 *
 */
public class MMAgentFactory implements MAgentFactory{

	static final Logger logger = LogManagerFactory.getLogManager().getLogger(QueryAgentFactory.class);
	
    /**
     * constructor - noargs
     */
    public MMAgentFactory() {

    }
    public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception{
    	return null;
    }
    
    public CacheAgent createQueryAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception{
        BEProperties beProperties = (BEProperties) rsp.getProperties();
        LinkedList<AgentService> queryAgentServices = new LinkedList<AgentService>();
        
        AgentClassConfig configClass = agentConfig.getRef();
        QueryAgentClassConfig qacc = ((MmAgentClassConfig) configClass).getMmQueryAgentClass();
        
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(agentConfig.getKey()));
        
        DefaultAgentConfiguration config = new DefaultAgentConfiguration(qacc.getId(),
        		key, rsp);
        QueryRuleSessionImpl session =
                (QueryRuleSessionImpl) rsp.getRuleRuntime().createRuleSession(config.getAgentName());

        DelegatedQueryOM queryOM = session.getDelegatedQueryOM();
        QueryAgent qa = new QueryAgent(config, session);

        String agentName = qa.getAgentConfig().getAgentName();
        //+1 is needed to differentiate the agentId for the MM-query-agent and the MM-inference-agent.
        //If other agents subsequently  join the cluster the correct agentId will be picked up, so it's all good.
        int agentId = qa.getAgentId()+1;
        queryOM.setDistributedServiceInfo(clusterName, agentName, agentId);

        logger.log(Level.INFO,
                "Query agent [" + clusterName + "-" + agentName + "-" + agentId + "] starting in cluster mode.");

        queryAgentServices.add(qa);
        MasterHelper.start(queryAgentServices, beProperties, logger);
        return qa;
    }
    
    private CacheAgent createInfAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {
        AgentClassConfig configClass = agentConfig.getRef();
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(agentConfig.getKey()));
        
        InferenceAgentClassConfig iacc = ((MmAgentClassConfig) configClass).getMmInferenceAgentClass();
        DefaultAgentConfiguration config = new DefaultAgentConfiguration(iacc.getId(),
        		key, rsp);
        return new NewInferenceAgent(config, rsp, CacheAgent.Type.INFERENCE);
       
    }
    
    public ArrayList<CacheAgent> createAgents(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {

    	ArrayList<CacheAgent> agents = new ArrayList<CacheAgent>();
        CacheAgent agent = this.createInfAgent(rsp, clusterName, agentConfig);
        agents.add(agent);
        agent = this.createQueryAgent(rsp, clusterName, agentConfig);
        agents.add(agent);
        
        return agents;

    }
}
