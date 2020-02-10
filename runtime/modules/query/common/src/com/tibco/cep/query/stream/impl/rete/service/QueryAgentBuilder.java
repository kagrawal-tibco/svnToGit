package com.tibco.cep.query.stream.impl.rete.service;

import java.util.Collection;
import java.util.Properties;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.impl.DelegatedQueryOM;
import com.tibco.cep.query.service.impl.MasterHelper;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: Sep 29, 2010 / Time: 11:32:59 AM
*/

public class QueryAgentBuilder {
    public static AgentService createAgent(AgentConfiguration config, RuleServiceProvider rsp, String clusterName)
            throws Exception {
        Logger logger = rsp.getLogger(QueryAgentBuilder.class);

        QueryRuleSessionImpl session =
                (QueryRuleSessionImpl) rsp.getRuleRuntime().createRuleSession(config.getAgentName());

        DelegatedQueryOM queryOM = session.getDelegatedQueryOM();

        BEProperties beProperties = (BEProperties) rsp.getProperties();

        AgentService agentService = null;

        boolean standaloneMode =
                beProperties.getBoolean(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), Boolean.FALSE);

        if (standaloneMode) {
            BEClassLoader beClassLoader = (BEClassLoader) rsp.getClassLoader();

            String name = rsp.getCluster().getClusterName() + "-" + session.getName() + "-" + System.nanoTime();

            agentService = new StandAloneAgentService(name, beClassLoader, session);

            queryOM.setSingleServiceInfo(name);

            logger.log(Level.INFO, "Query agent [" + name + "] starting in standalone mode.");
        }
        else {
            QueryAgent qa = new QueryAgent(config, session);
            agentService = qa;

            String agentName = qa.getAgentConfig().getAgentName();
            int agentId = qa.getAgentId();
            queryOM.setDistributedServiceInfo(clusterName, agentName, agentId);

            logger.log(Level.INFO,
                    "Query agent [" + clusterName + "-" + agentName + "-" + agentId + "] starting in cluster mode.");
        }

        return agentService;
    }

    /**
     * @param queryAgentServices AgentServices
     * @param rsp
     * @throws Exception
     */
    public static void startQueryMaster(Collection<AgentService> queryAgentServices, RuleServiceProvider rsp)
            throws Exception {
        Properties properties = rsp.getProperties();

        com.tibco.cep.kernel.service.logging.Logger logger = rsp.getLogger(MasterHelper.class);

        MasterHelper.start(queryAgentServices, properties, logger);
    }

    public static void stopQueryMaster() throws Exception {
        MasterHelper.stop();
    }
}
