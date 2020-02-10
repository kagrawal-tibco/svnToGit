package com.tibco.cep.runtime.service.management.agent;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.EntityMBeansManager;
import com.tibco.cep.runtime.service.management.agent.impl.AgentStatsMBeanImpl;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 21, 2009
 * Time: 8:23:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentMBeansManager extends EntityMBeansManager {
    //TODO: In the future might need different "action" MBeans for different agent types???

    //fields that represent the MBean (ObjectName) Groups for this agent
    //to expose other groups as MBeans add the group name to the array 'agentMBeanGroups' and write the code
    //for the MBean interface and corresponding implementation class following the pattern described in
    //the method 'registerMBeanClasses' of class 'EntityMBeansManager'. Tha name used to construct the pattern
    //must go in the field 'agentMBeanClassGroups'.
    private static final String[] agentMBeanGroups_cache = {"Channels", "Agent", "Object Management", "Profiler", "Working Memory", "Execute Methods"};
    private static final String[] agentMBeanClassGroups_cache = {"Channels", "Agent", "OM", "Profiler", "WM", "MethodExecute"};

    private static final String[] agentMBeanGroups_memory = {"Channels", "Agent", "Object Management", "Profiler", "Working Memory", "Execute Methods"};//, "Hot Deploy"};
    private static final String[] agentMBeanClassGroups_memory = {"Channels", "Agent", "OM", "Profiler", "WM", "MethodExecute"};//, "HotDeploy"};

    /** Used to register the MBeans for agents running In-Memory mode */
    public AgentMBeansManager(int agentId, RuleServiceProvider rsp, String agentName) {
        super(agentMBeanGroups_memory, agentMBeanClassGroups_memory);
        super.cacheAgent = null;
        super.isAgent = true;
        super.agentName = agentName;
        super.agentId = agentId;

        setRuleServiceProvider(rsp);
        setLogger(this.getClass());
    }

    /** Used to register the MBeans for agents running In-Cache mode */
    public AgentMBeansManager(CacheAgent cacheAgent) {
        super(agentMBeanGroups_cache, agentMBeanClassGroups_cache);
        super.cacheAgent = cacheAgent;
        super.agentId = cacheAgent.getAgentId();
        super.isAgent = true;

        setRuleServiceProvider(cacheAgent.getCluster().getRuleServiceProvider());
        setLogger(this.getClass());
    }

    public void registerAgentMBeans() {
       registerMBeanClasses(this.getClass());
       registerMBeans();
       registerStatsMBean();
    }

    private void registerMBeans() {        //todo handle MBeans UNRegistration
        int i;
        logger.log(Level.INFO,"Registering all BE-Agent level Group MBeans for BE-Agent with ID: " + agentId );

        setObjectNames("com.tibco.be:type=Agent,agentId=" + agentId
                                                + ",dir=Methods");

        for (i=0; i < numGroups; i++) {
            init(i);
            register(i);
        }
        logger.log(Level.INFO,"All BE-Agent level Group MBeans SUCCESSFULLY registered for BE-Agent with ID: " + agentId);
    }

    private void registerStatsMBean() {
        final String onStr = "com.tibco.be:type=Agent," +
                "agentId=" + agentId + ",service=Stats,stat=UserDefinedStats";

        try {
            EntityMBeansManager.registerStdMBean (
                    onStr,
                    new AgentStatsMBeanImpl( ruleServiceProvider,
                            getRuleSessions(ruleServiceProvider, agentName)[0],
                            agentId),
                    AgentStatsMBean.class);

        } catch (BEMMUserActivityException e) {
            logger.log(Level.ERROR, "Error registering Stats MBean. " +
                                    "Stats might not be available");
        }
    }

} //AgentMBeansManager
