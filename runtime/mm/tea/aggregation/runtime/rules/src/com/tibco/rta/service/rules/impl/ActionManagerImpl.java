package com.tibco.rta.service.rules.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.impl.MetricNodeEventImpl;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.AlertNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.rules.ActionManager;
import com.tibco.rta.service.rules.RuleService;


class ActionManagerImpl implements ActionManager {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_ACTIONS.getCategory());

    //mapping of scheduled time to actions sorted by time.
    protected SortedMap<Long, Set<RuleMetricNodeStateKey>> scheduledActions =
            Collections.synchronizedSortedMap(new TreeMap<Long, Set<RuleMetricNodeStateKey>>());

    //mapping of individual actions to their scheduled time.
    protected Map<RuleMetricNodeStateKey, Long> actionSchedules =
            Collections.synchronizedMap(new HashMap<RuleMetricNodeStateKey, Long>());

    protected RuleService ruleService;

    protected ObjectManager om;

    protected PersistenceService pService;

    protected Timer actionScheduleTimer;

    protected Properties configuration;

    protected long scanFrequency = 2000;

    public ActionManagerImpl() throws Exception {

    }

    @Override
    public void init(Properties configuration) throws Exception {
        this.ruleService = ServiceProviderManager.getInstance().getRuleService();
        this.om = ServiceProviderManager.getInstance().getObjectManager();
        this.pService = ServiceProviderManager.getInstance().getPersistenceService();

        this.configuration = configuration;

        try {
            scanFrequency = Long.parseLong((String) ConfigProperty.RTA_ACTIONS_SCAN_FREQUENCY.getValue(configuration));
        } catch (Exception e) {
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "Using default scan frequency %d", scanFrequency);
            }
        }
    }

    @Override
    public void start() throws Exception {
        this.actionScheduleTimer = new Timer("ActionScheduleTimer");
        actionScheduleTimer.schedule(new ActionScheduler(), scanFrequency, scanFrequency);
    }

    @Override
    public void stop() throws Exception {
        if (actionScheduleTimer != null) {
            actionScheduleTimer.cancel();
        }
    }

    @Override
    synchronized public void scheduleAction(RuleMetricNodeState actionState) {
        long timestamp = actionState.getScheduledTime();
        Set<RuleMetricNodeStateKey> scheduledList = scheduledActions.get(timestamp);
        if (scheduledList == null) {
            scheduledList = new HashSet<RuleMetricNodeStateKey>();
            scheduledActions.put(timestamp, scheduledList);
            actionSchedules.put(actionState.getKey(), timestamp);
        }
        scheduledList.add(actionState.getKey());
    }

    @Override
    synchronized public void removeActionSchedule(RuleMetricNodeState actionState) {
        Long scheduledTime = actionSchedules.get(actionState.getKey());
        if (scheduledTime != null) {
            Set<RuleMetricNodeStateKey> scheduledSet = scheduledActions
                    .get(scheduledTime);
            if (scheduledSet != null) {
                scheduledSet.remove(actionState.getKey());
                if (scheduledSet.size() == 0) {
                    // remove the entire set from the collection
                    scheduledActions.remove(scheduledTime);
                }
            }
            // DONT remove associated state here, its required for clear action.
//			ruleMetricStore.removeRuleMetricNode(actionState.getKey());
        }
    }


    protected void processActionsForNode(RuleMetricNodeStateKey key) {
        try {

            String ruleName = key.getRuleName();
            String actionName = key.getActionName();

            //Get the state from the store.
            RuleMetricNodeState actionState = om.getRuleMetricNode(ruleName, actionName, key.getMetricKey());

            if (actionState != null) {
                //from here, get the rule and the action
                Rule rule = ruleService.getRule(ruleName);
                if (rule != null) {

                    //Get the action
                    Action action = rule.getSetAction(actionName);

                    ActionDef actionDef = action.getActionDef();
                    
                    TimeBasedConstraint tbc = (TimeBasedConstraint) actionDef.getConstraint();
                    if (actionState.getCount() < tbc.getMaxInvocationCount()) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Firing action for %s", actionState.getKey());
                        }
                        //Get the node.
                        try {
                        	MetricNode mNode = actionState.getMetricNode();

                            //Write to the Alert logs.
                            AlertNodeState alertNode = new AlertNodeStateImpl(rule, action, actionState);
                            //Create alert
                            ruleService.createAndAssertAlertFactFromAlertNode(alertNode);
                        	
                            //TODO: Figure out if its new or update?
                            MetricNodeEventImpl nodeEvent = new MetricNodeEventImpl(mNode, MetricEventType.UPDATE);
                            action.performAction(rule, nodeEvent);
                            
                            actionState.setCount(actionState.getCount() + 1);
                            actionState.setScheduledTime(actionState.getScheduledTime() + tbc.getInvocationFrequency());

                            scheduleAction(actionState);
                        } catch (Exception e) {
                            LOGGER.log(Level.ERROR, "Error while processing action", e);
                        }

                    } else if (actionState.getCount() == tbc.getMaxInvocationCount()) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Max count reached, removing scheduler for %s", actionState.getKey());
                        }
                        removeActionSchedule(actionState);
                    }
                } else {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Rule not found for %s", actionState.getKey());
                    }
                }
                om.save(actionState);
            } else {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "RuleMetricNodeState not found for %s", (ruleName + "/" + actionName + "/" + key.getMetricKey()));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while processing timer action %s, %s, %s", e, key.getRuleName(), key.getActionName(), key.getMetricKey());
        }
    }


    protected void doWork(long now) throws Exception {
        List<RuleMetricNodeStateKey> expiredList = getExpiredNodeBrowser(now);
        for (RuleMetricNodeStateKey ruleMetricNodeStateKey : expiredList) {
            processActionsForNode(ruleMetricNodeStateKey);
        }
    }


    private List<RuleMetricNodeStateKey> getExpiredNodeBrowser(long now) {
        List<RuleMetricNodeStateKey> expiredList = new ArrayList<RuleMetricNodeStateKey>();

        //make this atomic wrt register/unregister..
        synchronized (ActionManagerImpl.this) {
            Map<Long, Set<RuleMetricNodeStateKey>> expiredMap = scheduledActions.headMap(now + 1);

            Iterator<Map.Entry<Long, Set<RuleMetricNodeStateKey>>> iterator = expiredMap
                    .entrySet().iterator();

            while (iterator.hasNext()) {
                Set<RuleMetricNodeStateKey> expiredValues = iterator.next().getValue();
                expiredList.addAll(expiredValues);
                iterator.remove();
                Iterator<RuleMetricNodeStateKey> expiredValsI = expiredValues.iterator();
                while (expiredValsI.hasNext()) {
                    RuleMetricNodeStateKey key = expiredValsI.next();
                    actionSchedules.remove(key);
                }
            }
        }
        return expiredList;
    }

   
    class ActionScheduler extends TimerTask {
        @Override
        public void run() {
            try {

                long now = System.currentTimeMillis();
                Transaction txn = null;
                try {
                    txn = RtaTransaction.get();
                    txn.beginTransaction();

                    doWork(now);

                    txn.commit();

                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while processing actions", e);
                    if (txn != null) {
                        txn.rollback();
                    }
                } finally {
                    if (txn != null) {
                        txn.remove();
                    }
                }

            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while running ActionScheduler", e);
            }
        }
    }
}