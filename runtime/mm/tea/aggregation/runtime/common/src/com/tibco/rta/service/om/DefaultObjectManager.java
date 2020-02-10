package com.tibco.rta.service.om;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.service.L1Cache;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.RtaTransaction.RtaTransactionElement;
import com.tibco.rta.common.service.RtaTransaction.TxnType;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.impl.L1CacheImpl;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.stats.L1CacheStats;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.RtaNode;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.persistence.PersistenceService;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;


public class DefaultObjectManager extends AbstractStartStopServiceImpl implements ObjectManager, L1CacheProvider, GMPActivationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_OBJECT_MANAGER.getCategory());

    private PersistenceService pService;

    private L1Cache<Key, RtaNode> metricCache;

    private L1Cache<Key, Fact> factCache;

    private L1Cache<RuleMetricNodeStateKey, RuleMetricNodeState> ruleMetricNodeCache;

    long cacheHits;


    @Override
    public void init(Properties configuration) throws Exception {
        super.init(configuration);
        pService = ServiceProviderManager.getInstance().getPersistenceService();

        long cachesize = 10000L;
        try {
            cachesize = Long.parseLong((String) ConfigProperty.RTA_L1_CACHE_SIZE.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: L1 cache size for metric nodes [%d]", cachesize);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. L1 cache size for rule/action nodes [%d]", cachesize);
            }
        }
        metricCache = new L1CacheImpl<Key, RtaNode>(cachesize);

        factCache = new L1CacheImpl<Key, Fact>(10);

        long ruleActionNodeCacheSize = 10000L;
        try {
            ruleActionNodeCacheSize = Long.parseLong((String) ConfigProperty.RTA_L1_CACHE_SIZE_FOR_RULEMETRIC_NODES.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: L1 cache size for metric nodes [%d]", ruleActionNodeCacheSize);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. L1 cache size for rule/action nodes [%d]", ruleActionNodeCacheSize);
            }
        }
        ruleMetricNodeCache = new L1CacheImpl<RuleMetricNodeStateKey, RuleMetricNodeState>(ruleActionNodeCacheSize);

        long assetCacheSize = 0L;
        try {
            assetCacheSize = Long.parseLong((String) ConfigProperty.RTA_L1_CACHE_SIZE_FOR_ASSET_NODES.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configuration: L1 cache size for asset nodes [%d]", assetCacheSize);
            }
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Bad Configuration: Using default. L1 cache size for asset nodes [%d]", assetCacheSize);
            }
        }

        registerMBean();
        ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);
    }

    private void registerMBean() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
        ObjectName name = new ObjectName(mbeanPrefix + ".cache:type=MetricCache");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(metricCache, name);
        }
        ObjectName name1 = new ObjectName(mbeanPrefix + ".cache:type=RuleMetricNodeCache");
        if (!mbs.isRegistered(name1)) {
            mbs.registerMBean(ruleMetricNodeCache, name1);
        }
    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting ObjectManager service..");
        }
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting ObjectManager service Complete.");
        }
    }

    @Override
    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping ObjectManager service..");
        }
        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping ObjectManager service Complete.");
        }
    }

    @Override
    public <N> RtaNode getNode(Key key) throws Exception {
        return getNode(key, true);
    }


    @Override
    public void save(Fact fact) throws Exception {
        if (((FactImpl) fact).isNew()) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording new fact [%s] in RTA Transaction..", fact);
            }
            RtaTransaction.get().recordNewFact(fact);
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording update on fact [%s] in RTA Transaction..", fact);
            }
            RtaTransaction.get().recordUpdateFact(fact);
        }
        // pService.save(fact);
    }


    @Override
    public void save(RtaNode node) throws Exception {
        if (!(node instanceof MetricNode)) {
            return;
        }
        MetricNode metricNode = (MetricNode) node;
        if (!metricNode.isNew()) {
            if (node instanceof MutableMetricNode) {
                long lastModifiedTime = System.currentTimeMillis();
                ((MutableMetricNode) node).setLastModifiedTime(lastModifiedTime);
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording update on metric node [%s] in RTA Transaction..", metricNode);
            }
            RtaTransaction.get().recordUpdateMetricNode(metricNode);
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording new metric node [%s] in RTA Transaction..", metricNode);
            }
            RtaTransaction.get().recordNewMetricNode(metricNode);
        }
//		pService.save(metricNode);
    }


    @Override
    public void delete(Fact node) {
        RtaTransaction.get().recordDeleteFact(node);
    }

    @Override
    public void delete(RtaNode node) {
        if (!(node instanceof MetricNode)) {
            return;
        }
        MetricNode metricNode = (MetricNode) node;
        RtaTransaction.get().recordDeleteMetricNode(metricNode);
    }

    @Override
    public void addChildFact(RtaNode node, Fact fact) throws Exception {
        if (node instanceof MetricNode) {
            MetricNode metricNode = (MetricNode) node;
            RtaTransaction.get().recordMetricNodeChild(metricNode, fact);
        }
    }

    @Override
    public void deleteChildFact(RtaNode metricNode, Fact fact) throws Exception {

    }

    @Override
    public void commit(boolean retryInfinite) throws Exception {
        pService.applyTransaction(retryInfinite);

        updateCache();
    }

    private void updateCache() {
        RtaTransaction txn = (RtaTransaction) RtaTransaction.get();

        List<MetricNodeEvent> changeList = txn.getMetricNodeChanges();
        for (MetricNodeEvent e : changeList) {
            if (!e.getEventType().equals(MetricEventType.DELETE)) {
                metricCache.put(e.getMetricNode().getKey(), e.getMetricNode());
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "MetricNode [%s] put into L1 Cache", e.getMetricNode().getKey());
                }
            } else {
                metricCache.remove(e.getMetricNode().getKey());
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "MetricNode [%s] removed from L1 Cache", e.getMetricNode().getKey());
                }
            }
        }

        //now update rulemetricnode changes to L1cache
        for (RtaTransactionElement e : txn.getTxnList().values()) {
            if (e.getNode() instanceof RuleMetricNodeState) {
                RuleMetricNodeState rmns = (RuleMetricNodeState) e.getNode();
                if (!e.getTxnType().equals(TxnType.DELETE)) {
                    ruleMetricNodeCache.put(rmns.getKey(), rmns);
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Node [%s] put into L1 Cache", rmns.getKey());
                    }
                } else {
                    ruleMetricNodeCache.remove(rmns.getKey());
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Node [%s] removed from L1 Cache", rmns.getKey());
                    }
                }
            }
        }
    }

    @Override
    public void rollback() throws Exception {
        pService.rollback();
    }

    @Override
    public void save(Fact fact, DimensionHierarchy hr) throws Exception {
        RtaTransaction.get().recordfactProcessedForHr(fact, hr);
    }

    @Override
    public void beginTransaction() throws Exception {
        pService.beginTransaction();

    }

    @Override
    public <N> RtaNode getNode(Key key, boolean clone) throws Exception {
        Transaction txn = RtaTransaction.get();
        RtaNode node;
        if ((node = txn.getNode(key)) == null) {
            if ((node = metricCache.get(key)) == null) {
                if ((node = pService.getNode(key)) != null) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "MetricNode [%s] found in database. Adding to L1 cache.", key);
                    }
                    metricCache.put(key, node);
                }
            } else {
                cacheHits++;
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "MetricNode [%s] found in L1 cache.", key);
                }
            }
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "MetricNode [%s] found in Transaction.", key);
            }
            return node;
        }
        if (clone) {
            if (node != null) {
                node = node.deepCopy();
            } else {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "MetricNode [%s] not found.", key);
                }
            }
        }
        return node;
    }

    @Override
    public void deleteRuleMetricNodeState(String ruleName) throws Exception {

        Map<RuleMetricNodeStateKey, RuleMetricNodeState> deleteKeys = new HashMap<RuleMetricNodeStateKey, RuleMetricNodeState>();
        for (Entry<RuleMetricNodeStateKey, RuleMetricNodeState> entry : ruleMetricNodeCache.entrySet()) {
            if (entry.getKey().getRuleName().equals(ruleName)) {
                deleteKeys.put(entry.getKey(), entry.getValue());
            }
        }

        for (RuleMetricNodeStateKey key : deleteKeys.keySet()) {
            ruleMetricNodeCache.remove(key);
//    		removeRuleMetricNode(deleteKeys.get(key));
            ServiceProviderManager.getInstance().getRuleService().getActionManager().removeActionSchedule(deleteKeys.get(key));
        }
    }

    @Override
    public RuleMetricNodeState getRuleMetricNode(String ruleName, String actionName, MetricKey key) throws Exception {

        Transaction txn = RtaTransaction.get();
        RuleMetricNodeState node;

        RuleMetricNodeStateKey rKey = new RuleMetricNodeStateKey(ruleName, actionName, key);

        if ((node = txn.getRuleMetricNode(ruleName, actionName, rKey.getMetricKey())) == null) {
            if ((node = ruleMetricNodeCache.get(rKey)) == null) {
                if ((node = pService.getRuleMetricNode(ruleName, actionName, rKey.getMetricKey())) != null) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "RuleMetricNode [%s] found in database. Adding to L1 cache.", rKey);
                    }
                    ruleMetricNodeCache.put(rKey, node);
                    node.setIsNew(false);
                } else {
                    // For first time.
                    //put a dummy node with isStored=false!
                    node = new RuleMetricNodeStateImpl(ruleName, actionName, key, false);
                    ruleMetricNodeCache.put(rKey, node);
                    return null;
                }
            } else {
                if (node.isStored()) {
                    cacheHits++;
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "RuleMetricNode [%s] found in L1 cache.", rKey);
                    }
                    node.setIsNew(false);
                } else {
                    node = null;
                }
            }
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "RuleMetricNode [%s] found in Transaction.", rKey);
            }
            return node;
        }

//BALA: Revisit this later..		
//		if (clone) {
//			if (node != null) {
//				node = node.deepCopy();
//			}
//		}

        return node;
    }


    @Override
    public void removeRuleMetricNode(RuleMetricNodeState key) throws Exception {
        RtaTransaction.get().recordDeleteRuleMetricNode(key);
    }

    @Override
    public void save(RuleMetricNodeState actionState) {
        if (!actionState.isNew()) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording update on rule metric node [%s] in RTA Transaction..", actionState);
            }
            RtaTransaction.get().recordUpdateRuleMetricNode(actionState);
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording new rule metric node [%s] in RTA Transaction..", actionState);
            }
            RtaTransaction.get().recordNewRuleMetricNode(actionState);
        }
    }

    @Override
    public void onActivate() {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Engine Activated to Primary. Will Clear all caches");
        }
        clearAllCaches();
    }

    private void clearAllCaches() {
        metricCache.clear();
        factCache.clear();
        ruleMetricNodeCache.clear();
    }

    @Override
    public void onDeactivate() {

    }

    @Override
    public void save(AlertNodeState actionState) {
        if (!actionState.isNew()) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording update on alert node [%s] in RTA Transaction..", actionState);
            }
            RtaTransaction.get().recordUpdateAlertNodeState(actionState);
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Recording new alert node [%s] in RTA Transaction..", actionState);
            }
            RtaTransaction.get().recordNewAlertNodeState(actionState);
        }
    }

    @Override
    public L1CacheStats getMetricCacheStats() {
        return getCacheStats((L1CacheImpl) metricCache);
    }

    @Override
    public L1CacheStats getFactCacheStats() {
        return getCacheStats((L1CacheImpl) factCache);
    }

    @Override
    public L1Cache<RuleMetricNodeStateKey, RuleMetricNodeState> getRuleMetricCache() {
        return ruleMetricNodeCache;
    }

    private L1CacheStats getCacheStats(L1CacheImpl l1Cache) {
        long capacity = l1Cache.getCapacity();
        int currentCacheSize = l1Cache.getCurrentSize();
        long hits = l1Cache.getHits();
        long misses = l1Cache.getMisses();
        double hitPercentage = l1Cache.getHitPercentage();
        double missPercentage = l1Cache.getMissPercentage();

        return new L1CacheStats(capacity, currentCacheSize, hits, misses, hitPercentage, missPercentage);
    }
    
    @Override
    public L1CacheStats getRuleMetricCacheStats() {
        return getCacheStats((L1CacheImpl) ruleMetricNodeCache);
    }
}
