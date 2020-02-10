package com.tibco.rta.service.rules.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.log.Level;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;

class DBActionManagerImpl extends ActionManagerImpl {

    protected Map<String, ActionScheduler> actionSchedulerMap = new LinkedHashMap<String, ActionScheduler>();

    protected Map<String, Timer> actionSchedulerTimerMap = new LinkedHashMap<String, Timer>();

    public DBActionManagerImpl() throws Exception {
    }

    @Override
    public void init(Properties configuration) throws Exception {
        this.ruleService = ServiceProviderManager.getInstance().getRuleService();
        this.om = ServiceProviderManager.getInstance().getObjectManager();
        this.pService = ServiceProviderManager.getInstance().getPersistenceService();

        this.configuration = configuration;

        try {
            scanFrequency = Long.parseLong((String) ConfigProperty.RTA_ACTIONS_SCAN_FREQUENCY.getValue(configuration));
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Configured database Scan Frequency for scheduled actions =[%d] milliseconds", scanFrequency);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "Using default scan frequency %d", scanFrequency);
        }

        for (RtaSchema schema : ModelRegistry.INSTANCE.getAllRegistryEntries()) {
            for (Cube cube : schema.getCubes()) {
                for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
					if (!dh.isEnabled()) {
						continue;
					}

                    actionSchedulerMap.put(schema.getName() + "-" + cube.getName() + "-" + dh.getName(),
                            new ActionScheduler(schema.getName(), cube.getName(), dh.getName()));
                }
            }
        }
    }

    public void start() throws Exception {
        for (Map.Entry<String, ActionScheduler> timerEntry : actionSchedulerMap.entrySet()) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Starting ActionTimer for [%s]", timerEntry.getKey());
            }
            Timer timer = new Timer("DBActionScheduler-" + timerEntry.getKey());
            if (!actionSchedulerTimerMap.containsKey(timerEntry.getKey())) {
                actionSchedulerTimerMap.put(timerEntry.getKey(), timer);
                timer.schedule(timerEntry.getValue(), scanFrequency, scanFrequency);
            }
        }
    }


    @Override
    public void stop() throws Exception {
        for (Map.Entry<String, Timer> timerEntry : actionSchedulerTimerMap.entrySet()) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Stopping action timer for [%s]", timerEntry.getKey());
            }
            try {
                timerEntry.getValue().cancel();
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Stopped action timer for [%s]", timerEntry.getKey());
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Exception while cancelling action timer for [%s]", timerEntry.getKey());
            }
        }

    }

    @Override
    public void scheduleAction(RuleMetricNodeState actionState) {
        //No-op for DB based scheduler.
    }

    @Override
    public void removeActionSchedule(RuleMetricNodeState actionState) {
        // DONT remove associated state here, its required for clear action.
//			ruleMetricStore.removeRuleMetricNode(actionState.getKey());
        //just mark scheduled time to -1
        actionState.setScheduledTime(-1);
    }

    protected void doWork(String schemaName, String cubeName, String hierarchyName, long now) throws Exception {
        Browser<RuleMetricNodeState> browser = pService.getScheduledActions(schemaName, cubeName, hierarchyName, now);
        while (browser.hasNext()) {
            RuleMetricNodeState actionNode = browser.next();
            RuleMetricNodeStateKey key = actionNode.getKey();
            processActionsForNode(key);
        }
    }

    class ActionScheduler extends TimerTask {

        String schemaName;
        String cubeName;
        String hierarchyName;
        String schedulerName;

        public ActionScheduler(String schemaName, String cubeName, String hierarchyName) {
            this.schemaName = schemaName;
            this.cubeName = cubeName;
            this.hierarchyName = hierarchyName;
            this.schedulerName = schemaName + "-" + cubeName + "-" + hierarchyName;
        }

        @Override
        public void run() {
            try {
                if (LOGGER.isEnabledFor(Level.TRACE)) {
                    LOGGER.log(Level.TRACE, "DBAction scheduler fired for [%s]", schedulerName);
                }
                long now = System.currentTimeMillis();
                Transaction txn = null;
                try {
                    txn = RtaTransaction.get();
                    txn.beginTransaction();

                    doWork(schemaName, cubeName, hierarchyName, now);

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