package com.tibco.cep.runtime.service.cluster.agent;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.CacheAgent.AgentState;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager.AgentListener;
import com.tibco.cep.runtime.service.cluster.agent.AgentManager.AgentTuple;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.annotation.Optional;

import java.util.*;

/*
* Author: Ashwin Jayaprakash / Date: Nov 11, 2010 / Time: 11:32:52 AM
*/

public class AgentRuntimePolicyManager implements AgentListener {
    protected Map<String, NavigableMap<Integer, AgentTuple>> agentTypesAndIds;

    protected Cluster cluster;

    protected AgentManager agentManager;

    protected Logger logger;

    protected boolean parallelStartup;

    public AgentRuntimePolicyManager(Cluster cluster, AgentManager agentManager) {
        this.cluster = cluster;
        this.agentManager = agentManager;

        this.agentTypesAndIds = new HashMap<String, NavigableMap<Integer, AgentTuple>>();
        this.logger = cluster.getRuleServiceProvider().getLogger(getClass());

        // Check for Parallel Startup
        Properties properties = cluster.getRuleServiceProvider().getProperties();
        parallelStartup = Boolean.parseBoolean(properties.getProperty(SystemProperty.ENGINE_STARTUP.getPropertyName(), Boolean.FALSE.toString()));
    }

    public Map<String, NavigableMap<Integer, AgentTuple>> getAgentTypesAndIds() {
        return agentTypesAndIds;
    }

    protected static StringBuilder getAsString(AgentTuple agentTuple) {
        StringBuilder builder = new StringBuilder();

        builder.append("Member: ").append(agentTuple.getMemberId())
                .append(", Agent name: ").append(agentTuple.getAgentName())
                .append(", Agent id: ").append(agentTuple.getAgentId())
                .append(", Agent priority: ").append(agentTuple.getPriority())
                .append(", Status: ").append(agentTuple.getState().name());

        return builder;
    }

    /**
     * @param latestView
     * @param agentName  Useful when the map is empty
     * @return
     */
    protected static StringBuilder getAsString(NavigableMap<Integer, AgentTuple> latestView, String agentName) {
        String newLine = System.getProperty("line.separator");

        StringBuilder stringInfo = new StringBuilder(newLine)
                .append("Currently known [").append(agentName).append("] agents = ")
                .append(latestView.size())
                .append(newLine);

        TreeSet<AgentTuple> sortedList = new TreeSet<AgentTuple>(new AscendingPriorityComparator<AgentTuple>());
        sortedList.addAll(latestView.values());

        for (AgentTuple agentTuple : sortedList) {
            stringInfo.append("   ");
            stringInfo.append(getAsString(agentTuple));
            stringInfo.append(newLine);
        }

        return stringInfo;
    }

    protected static boolean isAgentRunning(AgentState agentState) {
        return agentState == AgentState.ACTIVATED || agentState == AgentState.PREPARETOACTIVATE;
    }

    protected static boolean isAgentReadyToRun(AgentState agentState) {
        return agentState == AgentState.INITIALIZED || agentState == AgentState.DEACTIVATED;
    }

    /**
     * @param agentId
     * @return Can be null
     */
    @Optional
    protected CacheAgent findLocalCacheAgent(int agentId) {
        CacheAgent[] localAgents = agentManager.getLocalAgents();

        for (CacheAgent localAgent : localAgents) {
            if (agentId == localAgent.getAgentId()) {
                return localAgent;
            }
        }

        return null;
    }

    private synchronized NavigableMap<Integer, AgentTuple> fetchAgentFamily(AgentTuple agent, boolean joinIfAbsent) {
        String agentName = agent.getAgentName();
        int agentId = agent.getAgentId();

        NavigableMap<Integer, AgentTuple> map = agentTypesAndIds.get(agentName);

        if (map == null) {
            map = new TreeMap<Integer, AgentTuple>();
            agentTypesAndIds.put(agentName, map);
        }

        if (joinIfAbsent) {
            map.put(agentId, agent);
        }

        return map;
    }

    private synchronized NavigableMap<Integer, AgentTuple> joinAndFetchAgentFamily(AgentTuple agent) {
        return fetchAgentFamily(agent, true);
    }

    /**
     * Acquire GMP lock before calling this!
     *
     * @param logFreshView
     */
    protected final synchronized void cleanAndRefreshGlobalView(boolean logFreshView) {
        purgeAll();

        //--------------

        Collection<AgentTuple> agentTuples = agentManager.getAgents();

        for (AgentTuple agentTuple : agentTuples) {
            NavigableMap<Integer, AgentTuple> latestView = joinAndFetchAgentFamily(agentTuple);

            logger.log(logFreshView ? Level.INFO : Level.DEBUG,
                    getAsString(latestView, agentTuple.getAgentName()).toString());
        }
    }

    public final synchronized void purgeAll() {
        for (NavigableMap<Integer, AgentTuple> navigableMap : agentTypesAndIds.values()) {
            navigableMap.clear();
        }
        agentTypesAndIds.clear();
    }

    protected SortedSet<AgentTuple> getActiveAgents(NavigableMap<Integer, AgentTuple> latestView) {
        TreeSet<AgentTuple> activeAgents = new TreeSet<AgentTuple>(new AscendingPriorityComparator<AgentTuple>());

        for (AgentTuple agentTuple : latestView.values()) {
            if (agentTuple.getState() == AgentState.ACTIVATED ||
                    agentTuple.getState() == AgentState.PREPARETOACTIVATE) {
                activeAgents.add(agentTuple);
            }
        }

        return activeAgents;
    }

    protected synchronized boolean tryActivateAgent(CacheAgent cacheAgent) {
        try {
            if (isAgentReadyToRun(cacheAgent.getAgentState())) {
                logger.log(Level.INFO, "Activating agent [%s:%d]", cacheAgent.getAgentName(), cacheAgent.getAgentId());

                cacheAgent.start(AgentState.ACTIVATED);

                return true;
            }

            logger.log(Level.WARN, "Agent [%s:%d] will not be activated as it is not ready yet. Its current state is [%s]",
                    cacheAgent.getAgentName(), cacheAgent.getAgentId(), cacheAgent.getAgentState().name());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, "Error occurred while activating agent [%s:%d]", cacheAgent.getAgentName(),
                    cacheAgent.getAgentId());
            throw new RuntimeException(e);
        }

        return false;
    }

    protected synchronized boolean tryDeactivateAgent(CacheAgent cacheAgent) {
        try {
            if (isAgentRunning(cacheAgent.getAgentState())) {
                logger.log(Level.INFO, "Deactivating agent [%s:%d]", cacheAgent.getAgentName(),
                        cacheAgent.getAgentId());

                cacheAgent.stop();

                return true;
            }

            logger.log(Level.WARN,
                    "Agent [%s:%d] will not be deactivated as it is not ready yet. Its current state is [%s]",
                    cacheAgent.getAgentName(), cacheAgent.getAgentId(), cacheAgent.getAgentState().name());
        }
        catch (Exception e) {
            logger.log(Level.ERROR, e, "Error occurred while activating agent [%s:%d]", cacheAgent.getAgentName(),
                    cacheAgent.getAgentId());
        }

        return false;
    }

    /**
     * Acquire GMP lock before calling this!
     *
     * @param latestView
     * @param prioritySortedAgentsToCheck If the agent gets activated or deactivated, then it gets removed from this set.
     */
    private synchronized void stabilizeCluster(NavigableMap<Integer, AgentTuple> latestView,
                                               TreeSet<AgentTuple> prioritySortedAgentsToCheck) {

        for (Iterator<AgentTuple> agentIdIter = prioritySortedAgentsToCheck.iterator(); agentIdIter.hasNext(); ) {
            AgentTuple agentToCheck = agentIdIter.next();

            SortedSet<AgentTuple> activeAgents = getActiveAgents(latestView);

            CacheAgent ourAgent = findLocalCacheAgent(agentToCheck.getAgentId());
            if (ourAgent != null) {
                int maxActive = ourAgent.getMaxActive();

                if (isAgentReadyToRun(agentToCheck.getState())) {
                    if (activeAgents.isEmpty() ||
                            AscendingPriorityComparator.isSameOrLowerPriorityThan(agentToCheck, activeAgents.last())) {

                        if (activeAgents.size() == latestView.size() ||
                                (maxActive > 0 && activeAgents.size() == maxActive)) {
                            break;
                        }
                    }

                    /*
                    Go ahead and activate all higher priority agents on this node, even if it crosses the
                     max-active number.

                    Then once the cluster stabilizes, the lower priority ones should deactivate after the global,
                     sorted list is visible to all.
                    */
                    if (tryActivateAgent(ourAgent)) {
                        agentIdIter.remove();
                    }
                }
                else if (isAgentRunning(agentToCheck.getState())) {
                    if (maxActive > 0 && activeAgents.size() > maxActive) {
                        //We are the lowest priority node that is in excess of the max active number.
                        if (activeAgents.last().getAgentId() == ourAgent.getAgentId()) {
                            if (tryDeactivateAgent(ourAgent)) {
                                agentIdIter.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    public final synchronized void syncGlobalView() {
        GroupMembershipService gmp = cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }
        try {
            cleanAndRefreshGlobalView(true);
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }
    }

    public final synchronized void tryActivateLocalAgents() {
        logger.log(Level.DEBUG, "Attempting to activate local agents, if required");

        GroupMembershipService gmp = cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }
        try {
            cleanAndRefreshGlobalView(false);

            CacheAgent[] cacheAgents = agentManager.getLocalAgents();

            for (CacheAgent cacheAgent : cacheAgents) {
                NavigableMap<Integer, AgentTuple> latestView = agentTypesAndIds.get(cacheAgent.getAgentName());

                TreeSet<AgentTuple> fakeList = new TreeSet<AgentTuple>(new AscendingPriorityComparator<AgentTuple>());
                DefaultAgentTuple agentTuple = new DefaultAgentTuple(cacheAgent);
                fakeList.add(agentTuple);

                stabilizeCluster(latestView, fakeList);
            }
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }

        logger.log(Level.DEBUG, "Completed attempt to activate local agents");
    }

    /**
     * Acquire GMP lock before calling!
     *
     * @param latestView
     * @param trigger
     */
    private synchronized void applyChange(NavigableMap<Integer, AgentTuple> latestView, AgentTuple trigger) {
        TreeSet<AgentTuple> agentsToCheckUpOn = new TreeSet<AgentTuple>(new AscendingPriorityComparator<AgentTuple>());

        //Start from after the trigger agent.
        NavigableMap<Integer, AgentTuple> youngerTuples = latestView.tailMap(trigger.getAgentId(), false);
        agentsToCheckUpOn.addAll(youngerTuples.values());

        //Then wrap around.
        NavigableMap<Integer, AgentTuple> olderTuples = latestView.headMap(trigger.getAgentId(), false);
        agentsToCheckUpOn.addAll(olderTuples.values());

        stabilizeCluster(latestView, agentsToCheckUpOn);

        agentsToCheckUpOn.clear();
    }

    //----------------

    @Override
    public final synchronized void onNew(AgentTuple trigger) {
        GroupMembershipService gmp = cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }
        try {
            cleanAndRefreshGlobalView(false);

            NavigableMap<Integer, AgentTuple> latestView = joinAndFetchAgentFamily(trigger);

            logger.log(Level.DEBUG, "Received new agent notification: " + getAsString(trigger));
            logger.log(Level.INFO, getAsString(latestView, trigger.getAgentName()).toString());

            applyChange(latestView, trigger);
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }
    }

    @Override
    public final synchronized void onChange(AgentTuple oldagent, AgentTuple trigger) {
        GroupMembershipService gmp = cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }
        try {
            cleanAndRefreshGlobalView(false);

            NavigableMap<Integer, AgentTuple> latestView = joinAndFetchAgentFamily(trigger);

            logger.log(Level.DEBUG, "Received agent change notification: " + getAsString(trigger));
            logger.log(Level.INFO, getAsString(latestView, trigger.getAgentName()).toString());

            applyChange(latestView, trigger);
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }
    }

    @Override
    public final synchronized void onExit(AgentTuple trigger) {
        GroupMembershipService gmp = cluster.getGroupMembershipService();

        if (!parallelStartup) {
            gmp.lock();
        }

        cluster.getEventTableProvider().releaseEventsOwnedByAgent(trigger.getAgentId());

        try {
            cleanAndRefreshGlobalView(false);

            NavigableMap<Integer, AgentTuple> latestView = fetchAgentFamily(trigger, false);

            logger.log(Level.DEBUG, "Received agent exit notification: " + getAsString(trigger));
            logger.log(Level.INFO, getAsString(latestView, trigger.getAgentName()).toString());

            applyChange(latestView, trigger);
        }
        finally {
            if (!parallelStartup) {
                gmp.unlock();
            }
        }
    }

    //----------------

    protected static class AscendingPriorityComparator<A extends AgentTuple> implements Comparator<A> {
        protected static boolean isHigherPriorityThan(AgentTuple query, AgentTuple that) {
            return $cmp(query.getPriority(), that.getPriority()) < 0;
        }

        protected static boolean isSameOrLowerPriorityThan(AgentTuple query, AgentTuple that) {
            return !isHigherPriorityThan(query, that);
        }

        @Override
        public int compare(A a1, A a2) {
            int x = $cmp(a1.getPriority(), a2.getPriority());

            //In case of a tie.
            if (x == 0) {
                return $cmp(a1.getAgentId(), a2.getAgentId());
            }

            return x;
        }

        private static int $cmp(Integer i1, Integer i2) {
            return i1.compareTo(i2);
        }
    }
}
