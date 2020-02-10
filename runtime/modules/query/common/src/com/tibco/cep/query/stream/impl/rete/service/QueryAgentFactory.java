/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.impl.rete.service;

import java.util.LinkedList;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.service.impl.DelegatedQueryOM;
import com.tibco.cep.query.service.impl.MasterHelper;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.provider.adapters.QueryAgentToBEArchiveAdapter;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentFactory;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Suresh Subramani / Date: Dec 6, 2010 / Time: 6:30:30 PM
*/
public class QueryAgentFactory implements AgentFactory {

    static final Logger logger = LogManagerFactory.getLogManager().getLogger(QueryAgentFactory.class);

    public QueryAgentFactory() {

    }

    @Override
    public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig classConfig)
            throws Exception {

        BEProperties beProperties = (BEProperties) rsp.getProperties();
        LinkedList<AgentService> queryAgentServices = new LinkedList<AgentService>();
        boolean standaloneMode =
                beProperties.getBoolean(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), Boolean.FALSE);

        if (standaloneMode) {
            throw new RuntimeException("Method not supported in standalone mode");
        }
        
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(classConfig.getKey()));
        
        DefaultAgentConfiguration config =
                new DefaultAgentConfiguration(
                        classConfig.getRef().getId(),
                        key,
                        rsp);
        QueryRuleSessionImpl session = null;

        if (QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName().equals(classConfig.getRef().getId())) {
            RuleSessionManagerImpl manager = (RuleSessionManagerImpl) rsp.getRuleRuntime();

            Properties properties = rsp.getProperties();
            ClusterConfig clusterConfig =
                    (ClusterConfig) properties.get(SystemProperty.CLUSTER_CONFIG.getPropertyName());

            session = new QueryRuleSessionImpl(new QueryAgentToBEArchiveAdapter(clusterConfig,
                    (QueryAgentClassConfig) classConfig.getRef(), classConfig.getRef().getId()), manager);
        }
        else {
            session = (QueryRuleSessionImpl) rsp.getRuleRuntime().createRuleSession(config.getAgentName());
        }

        DelegatedQueryOM queryOM = session.getDelegatedQueryOM();
        QueryAgent qa = new QueryAgent(config, session);

        String agentName = qa.getAgentConfig().getAgentName();
        int agentId = qa.getAgentId();
        queryOM.setDistributedServiceInfo(clusterName, agentName, agentId);

        logger.log(Level.INFO,
                "Query agent [" + clusterName + "-" + agentName + "-" + agentId + "] starting in cluster mode.");

        queryAgentServices.add(qa);
        MasterHelper.start(queryAgentServices, beProperties, logger);
        return qa;

    }


    //todo Suresh: Standalone
    public AgentService createStandAloneAgent(RuleServiceProvider rsp, String clusterName, AgentConfig classConfig)
            throws Exception {

        BEProperties beProperties = (BEProperties) rsp.getProperties();
        LinkedList<AgentService> queryAgentServices = new LinkedList<AgentService>();
        boolean standaloneMode =
                beProperties.getBoolean(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), Boolean.FALSE);

        if (!standaloneMode) {
            throw new RuntimeException("Method not supported in multi-agent mode");
        }
        
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(classConfig.getKey()));

        DefaultAgentConfiguration config =
                new DefaultAgentConfiguration(classConfig.getRef().getId(),
                		key, rsp);
        QueryRuleSessionImpl session =
                (QueryRuleSessionImpl) rsp.getRuleRuntime().createRuleSession(config.getAgentName());

        DelegatedQueryOM queryOM = session.getDelegatedQueryOM();
        AgentService agentService = null;
        BEClassLoader beClassLoader = (BEClassLoader) rsp.getClassLoader();
        String name = rsp.getCluster().getClusterName() + "-" + session.getName() + "-" + System.nanoTime();
        agentService = new StandAloneAgentService(name, beClassLoader, session);
        queryAgentServices.add(agentService);
        queryOM.setSingleServiceInfo(name);
        logger.log(Level.INFO, "Query agent [" + name + "] starting in standalone mode.");
        MasterHelper.start(queryAgentServices, beProperties, logger);

        return agentService;


    }
}
