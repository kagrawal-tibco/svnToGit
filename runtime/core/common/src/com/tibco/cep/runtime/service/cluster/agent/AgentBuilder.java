/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.ArtifactConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitConfig;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

public class AgentBuilder {

    static AgentBuilder agentBuilder;
    static final Map<String, String> agentFactoryTable = initFactoryTable();

    /**
     * Map of AbstractAgentClassConfig : an AgentFactory class Name.
     * @return
     */
    private static Map<String, String> initFactoryTable() {

        Map table = new HashMap<String, String>();
        //Inference Agent
        table.put("com.tibco.be.util.config.cdd.impl.InferenceAgentClassConfigImpl", "com.tibco.cep.runtime.service.cluster.agent.InferenceAgentFactory");

        //Cache Agent - same as inferenceAgent,
        table.put("com.tibco.be.util.config.cdd.impl.CacheAgentClassConfigImpl", "com.tibco.cep.runtime.service.cluster.agent.InferenceAgentFactory");

        //Monitoring and Management
        table.put("com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl", "com.tibco.cep.bemm.service.cluster.MMAgentFactory");

        //Query Agent
        table.put("com.tibco.be.util.config.cdd.impl.QueryAgentClassConfigImpl", "com.tibco.cep.query.stream.impl.rete.service.QueryAgentFactory");
        table.put("com.tibco.be.util.config.cdd.QueryAgentClassConfig", "com.tibco.cep.query.stream.impl.rete.service.QueryAgentFactory");

        //DashboardAgent
        table.put("com.tibco.be.util.config.cdd.impl.DashboardAgentClassConfigImpl", "com.tibco.cep.dashboard.integration.be.DashboardAgentFactory");

        //ProcessAgent
        table.put("com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl", "com.tibco.cep.bpmn.runtime.agent.ProcessAgentFactory");
        
        //Liveview Agent
        table.put("com.tibco.be.util.config.cdd.impl.LiveViewAgentClassConfigImpl", "com.tibco.cep.liveview.agent.LiveViewAgentFactory");

        return table;

    }

    public synchronized static AgentBuilder getInstance() {
        if (agentBuilder == null) {
            agentBuilder = new AgentBuilder();
        }
        return agentBuilder;
    }

    public CacheAgent[] build(RuleServiceProvider rsp, String clusterName) throws Exception {
        Properties properties = rsp.getProperties();
        ClusterConfig clusterConfig = (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());

        final String pucId = properties.getProperty(SystemProperty.PROCESSING_UNIT_ID.getPropertyName());

        ProcessingUnitConfig puc = (ProcessingUnitConfig) CddTools.findById(
        		(EList<? extends ArtifactConfig>) clusterConfig.getProcessingUnits().getProcessingUnit(), pucId);
        if (puc == null) {
            //throw new Exception("No processing unit configured.");
            return new CacheAgent[0];
        }

        CacheAgent agents[] = new CacheAgent[puc.getAgents().getAgent().size()];

        int i = 0;
        for (AgentConfig agentConfig : puc.getAgents().getAgent()) {
            AgentClassConfig configClass = agentConfig.getRef();
            
            //to handle MM agent class
            if(configClass instanceof MmAgentClassConfig){
                String rtClassName = agentFactoryTable.get(configClass.getClass().getName());
                Class clz = Class.forName(rtClassName);
                MAgentFactory factory = (MAgentFactory) clz.newInstance();
                ArrayList<CacheAgent> mmAgents = factory.createAgents(rsp, clusterName, agentConfig);
            	agents = new CacheAgent[mmAgents.size()];
                for(int j=0;j<mmAgents.size();j++){
                	agents[i] = mmAgents.get(j);
                	++i;
                }
                break;
            }
            String rtClassName = agentFactoryTable.get(configClass.getClass().getName());
            Class clz = Class.forName(rtClassName);
            AgentFactory factory = (AgentFactory) clz.newInstance();
            agents[i] = factory.createAgent(rsp, clusterName, agentConfig);
            ++i;
        }

        return agents;
    }

    public CacheAgent build(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {
        AgentClassConfig configClass = agentConfig.getRef();

        Class klass = configClass.getClass();

        String rtClassName = agentFactoryTable.get(klass.getName());
        if (rtClassName == null) {
            klass = klass.getSuperclass();
            if (klass != null) {
                rtClassName = agentFactoryTable.get(klass.getName());
            }
        }

        Class clz = Class.forName(rtClassName);
        AgentFactory factory = (AgentFactory) clz.newInstance();
        return factory.createAgent(rsp, clusterName, agentConfig);
    }
}