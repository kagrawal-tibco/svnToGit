package com.tibco.rta.service.rules.impl;

import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.FunctionDescriptor.FunctionParamValue;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.runtime.model.rule.mutable.MutableRule;
import com.tibco.rta.service.metric.MetricEventListener;
import com.tibco.rta.service.rules.RuleService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


public class RuleRegistry implements MetricEventListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_RULES.getCategory());

    // Map of schema/cube/hierarchy/measurement to rules to a map of rulename to the rule
    // (allows evaluation of only related rules)
    private Map<String, Map<String, MutableRule>> rules = new ConcurrentHashMap<String, Map<String, MutableRule>>();
    // Map of rulename to schema/cube/hierarchy/measurement
    private Map<String, String> allRules = new ConcurrentHashMap<String, String>();
    // Map of design time rules
    private Map<String, RuleDef> ruleDefs = new ConcurrentHashMap<String, RuleDef>();

    private RuleService ruleService;


    // TODO: Maybe a different sized queue?
//	private WorkItemService workItemService;

    public RuleRegistry(RuleService ruleService) throws Exception {
        this.ruleService = ruleService;
//		this.workItemService = ServiceProviderManager.getInstance().newWorkItemService("WorkPool-Rules");
    }

    public void init(Properties configuration) throws Exception {
//		workItemService.init(configuration);
    }

    @Override
    public void onValueChange(MetricNodeEvent nodeEvent) throws Exception {
        //Now done differently. Its already in a different thread. No need to dispatch yet again to a thread-pool
        RuleProcessingJob ruleProcessingJob = new RuleProcessingJob(nodeEvent);
        //so just call the "call" method
        ruleProcessingJob.call();
        //no need to post to another thread pool
        //workItemService.addWorkItem(ruleProcessingJob);
    }
    
    public void processSnapshotRuleJob(MetricNodeEvent nodeEvent) throws Exception {
        SnapshotRuleProcessingJob ruleProcessingJob = new SnapshotRuleProcessingJob(nodeEvent);
        ruleProcessingJob.call();
    }


    private String createRuleContextStr(String schemaName, String cubeName, String hierarchyName) {
        return schemaName + "-" + cubeName + "-" + hierarchyName;
    }

    public void remove(String ruleName) throws Exception {
        RuleDef rule = ruleDefs.get(ruleName);
        if (rule != null) {
            ruleDefs.remove(ruleName);
            allRules.remove(ruleName);
        } else {
            throw new DuplicateSchemaElementException(String.format("%s does not exist", ruleName));
        }
    }
    
    public void clearRuleRegistry(){
    	allRules.clear();
    	ruleDefs.clear();
    	rules.clear();
    }
    
    void put(RuleDef ruleDef) throws Exception {
        String ruleName = ruleDef.getName();
        RuleDef rule = ruleDefs.get(ruleName);
        if (rule == null) {
            ruleDefs.put(ruleName, ruleDef);
        } else {
            throw new DuplicateSchemaElementException(String.format("%s already registered. Cannot register it again", ruleDef.getName()));
        }
    }

    public void unRegisterRule(String ruleName) throws Exception {
        String ruleContextKey = allRules.get(ruleName);
        if (ruleContextKey != null) {
            Map<String, MutableRule> rulesMap = rules.get(ruleContextKey);
            if (rulesMap != null) {
                rulesMap.remove(ruleName);
            }
            remove(ruleName);
        }
    }

    public void updateRule(RuleDef ruleDef) throws Exception {
        unRegisterRule(ruleDef.getName());
        registerRule(ruleDef);
    }

    public void registerRule(RuleDef ruleDef) throws Exception {
        MutableRule rule = newRule(ruleDef);
        put(ruleDef);
        registerRule(rule);
    }

    public List<RuleDef> getRules() {
        return new ArrayList<RuleDef>(ruleDefs.values());
    }

    public RuleDef getRuleDef(String ruleName) {
        return ruleDefs.get(ruleName);
    }

    private MutableRule newRule(RuleDef ruleDef) throws Exception {
        if (ruleDef.isStreamingQuery()) {
            MutableRule queryRuleImpl = new QueryRuleImpl(ruleDef);
            addActions(queryRuleImpl, ruleDef.getSetActionDefs(), true);
            return queryRuleImpl;
        } else {
            MutableRule ruleImpl = new RuleImpl(ruleDef);
            ((MutableRuleDef) ruleDef).setAsStreamingQuery(false);
            addActions(ruleImpl, ruleDef.getSetActionDefs(), true);
            addActions(ruleImpl, ruleDef.getClearActionDefs(), false);
            return ruleImpl;
        }
    }

    private void addActions(MutableRule ruleImpl, Collection<ActionDef> actionList, boolean isSetAction) throws Exception {
        for (ActionDef actionDef : actionList) {

            String actionName = actionDef.getActionFunctionDescriptor().getName();

            ActionHandlerContext context = ruleService.getActionHandlerContext(actionName);
            if (context == null) {
                throw new Exception(String.format("Action with action name [%s] not found", actionName));
            }

            Action action = context.getAction(ruleImpl, actionDef);
            action.setSetAction(isSetAction);
            action.setActionDef(actionDef);

            for (FunctionParamValue fnVal : actionDef.getActionFunctionDescriptor().getFunctionParamValues()) {
                action.addFunctionParamVal(fnVal);
            }

            if (isSetAction) {
                ruleImpl.addSetAction(action);
            } else {
                ruleImpl.addClearAction(action);
            }
        }
    }

    private void registerRule(MutableRule rule) throws Exception {
        if (allRules.containsKey(rule.getRuleDef().getName())) {
            throw new Exception(String.format("Rule already exists: %s", rule.getRuleDef().getName()));
        }
        QueryDef setCondition = rule.getRuleDef().getSetCondition();
        String schemaName = setCondition.getSchemaName();
        String cubeName = setCondition.getCubeName();
        String hierarchyName = setCondition.getHierarchyName();

        String ruleContextKey = createRuleContextStr(schemaName, cubeName, hierarchyName);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Rule context key [%s]", ruleContextKey);
        }

        Map<String, MutableRule> rulesMap = rules.get(ruleContextKey);
        if (rulesMap == null) {
            rulesMap = new ConcurrentHashMap<String, MutableRule>();
            rules.put(ruleContextKey, rulesMap);
        }

        rulesMap.put(rule.getName(), rule);

        allRules.put(rule.getName(), ruleContextKey);
    }

    public Rule getRule(String ruleName) {
        Rule rule = null;
        RuleDef ruleDef = getRuleDef(ruleName);
        if (ruleDef != null) {
            String ruleContextKey = allRules.get(ruleName);
            Map<String, MutableRule> rulesMap = rules.get(ruleContextKey);
            if (rulesMap != null) {
                rule = rulesMap.get(ruleName);
            }
        }
        return rule;
    }

    /**
     * For every metric node event rule processing job is created.
     */
    class RuleProcessingJob implements WorkItem<String> {

        private MetricNodeEvent nodeEvent;

        public RuleProcessingJob(MetricNodeEvent nodeEvent) {
            this.nodeEvent = nodeEvent;
        }

        @Override
        public String call() {
            try {
                MetricNode mNode = nodeEvent.getMetricNode();
                MetricKey key = (MetricKey) mNode.getKey();

                String schemaName = key.getSchemaName();
                String cubeName = key.getCubeName();
                String hierarchyName = key.getDimensionHierarchyName();
                // String measurementName = key.getMeasurementName();

                String ruleContextKey = createRuleContextStr(schemaName, cubeName, hierarchyName);

                Map<String, MutableRule> rulesMap = rules.get(ruleContextKey);
                if (rulesMap != null) {
 
                	Transaction txn = null;
                	try {
                		
                		for (MutableRule rule : rulesMap.values()) {
                			
                			if (!rule.getRuleDef().isStreamingQuery()) {
                				//do this only if there is at least 1 non-streaming query..
                				if (txn == null) {
                					txn = RtaTransaction.get();
                            		txn.beginTransaction();
                				}
                			}
                			
                			// TODO The actual rule evaluation can be made in parallel.
                			if (LOGGER.isEnabledFor(Level.DEBUG)) {
                				LOGGER.log(Level.DEBUG, "Trying to evaluate rule [%s] for metric node event [%s]", rule.getName(), nodeEvent);
                			}
                			try {
                				rule.eval(nodeEvent);
                			} catch (Exception e) {
                				LOGGER.log(Level.ERROR, "Error while processing rule [%s] for node [%s]", e, rule.getName(), nodeEvent.getMetricNode().getKey());
                			}
                			if (LOGGER.isEnabledFor(Level.DEBUG)) {
                				LOGGER.log(Level.DEBUG, "Evaluation of rule [%s] completed for metric node event [%s].", rule.getName(), nodeEvent.getMetricNode().getKey());
                			}
                		}
                        if (txn != null) {
                        	txn.commit();
                        }
                	} catch (Exception e) {
                		LOGGER.log(Level.ERROR, "Error while evaluating rule", e);
                		if (txn != null) {
                			txn.rollback();
                		}
                	} finally {
                		if (txn != null) {
                			txn.remove();
                		}
                	}
                    
                    
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while process rule evaluation job", e);
            }
            return null;
        }

        @Override
        public String get() {
            // TODO Auto-generated method stub
            return null;
        }

    }
    
    
    /*
     *For every node from picked by the Snapshot Rule Eval service a Snapshot rule service is created 
     */
    class SnapshotRuleProcessingJob implements WorkItem<String> {

        private MetricNodeEvent nodeEvent;

        public SnapshotRuleProcessingJob(MetricNodeEvent nodeEvent) {
            this.nodeEvent = nodeEvent;
        }

        @Override
        public String call() {
            try {
                MetricNode mNode = nodeEvent.getMetricNode();
                MetricKey key = (MetricKey) mNode.getKey();

                String schemaName = key.getSchemaName();
                String cubeName = key.getCubeName();
                String hierarchyName = key.getDimensionHierarchyName();
                // String measurementName = key.getMeasurementName();

                String ruleContextKey = createRuleContextStr(schemaName, cubeName, hierarchyName);

                Map<String, MutableRule> rulesMap = rules.get(ruleContextKey);
                if (rulesMap != null) {
 
                	Transaction txn = null;
                	try {
                		
                		for (MutableRule rule : rulesMap.values()) {
                			
                			if (!rule.getRuleDef().isStreamingQuery()) {
                				//do this only if there is at least 1 non-streaming query..
                				if (txn == null) {
                					txn = RtaTransaction.get();
                            		txn.beginTransaction();
                				}
                			}
                			
                			// TODO The actual rule evaluation can be made in parallel.
                			if (LOGGER.isEnabledFor(Level.DEBUG)) {
                				LOGGER.log(Level.DEBUG, "SnapshotRuleProcessingJob :Trying to evaluate rule [%s] for metric node event [%s]", rule.getName(), nodeEvent);
                			}
                			try {
                				rule.eval(nodeEvent);
                			} catch (Exception e) {
                				LOGGER.log(Level.ERROR, "Error while processing rule [%s] for node [%s]", e, rule.getName(), nodeEvent.getMetricNode().getKey());
                			}
                			if (LOGGER.isEnabledFor(Level.DEBUG)) {
                				LOGGER.log(Level.DEBUG, "SnapshotRuleProcessingJob :Evaluation of rule [%s] completed for metric node event [%s].", rule.getName(), nodeEvent.getMetricNode().getKey());
                			}
                		}
                        if (txn != null) {
                        	txn.commit();
                        }
                	} catch (Exception e) {
                		LOGGER.log(Level.ERROR, "SnapshotRuleProcessingJob :Error while evaluating rule", e);
                		if (txn != null) {
                			txn.rollback();
                		}
                	} finally {
                		if (txn != null) {
                			txn.remove();
                		}
                	}
                    
                    
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "SnapshotRuleProcessingJob : Error while process rule evaluation job", e);
            }
            return null;
        }

        @Override
        public String get() {
            // TODO Auto-generated method stub
            return null;
        }

    }
    
    
}
