package com.tibco.rta.service.rules.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.query.StreamingQueryEvaluator;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.RuleMetricNodeState;
import com.tibco.rta.runtime.model.rule.impl.AlertNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateImpl;
import com.tibco.rta.runtime.model.rule.impl.RuleMetricNodeStateKey;
import com.tibco.rta.runtime.model.rule.mutable.MutableRule;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.rules.RuleService;

/**
 * Provide both set and clear condition evaluation
 */
public class RuleImpl implements MutableRule {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_RULES.getCategory());

    protected StreamingQueryEvaluator clearConditionEvaluator;

    protected StreamingQueryEvaluator setConditionEvaluator;

    protected Map<String, Action> clearActionList = new LinkedHashMap<String, Action>();

    protected Map<String, Action> setActionList = new LinkedHashMap<String, Action>();

    protected RuleService ruleService;

    protected ObjectManager om;

    protected RuleDef ruleDef;

    protected AtomicLong totalEval = new AtomicLong(0);

    protected AtomicLong totalTime = new AtomicLong(0);
    
    protected boolean isEnabled;
    
    public RuleImpl(RuleDef ruleDef) throws Exception {
        this.ruleService = ServiceProviderManager.getInstance().getRuleService();
        this.om = ServiceProviderManager.getInstance().getObjectManager();
        this.isEnabled = ruleDef.isEnabled();

        this.ruleDef = ruleDef;
        if (ruleDef.getSetCondition() != null) {
        	 if (LOGGER.isEnabledFor(Level.DEBUG)) {
                 LOGGER.log(Level.DEBUG, "Rule SetCondition [%s]", ruleDef.getSetCondition());
             }
            this.setConditionEvaluator = new StreamingQueryEvaluator(ruleDef.getSetCondition());
        }
        if (ruleDef.getClearCondition() != null) {
        	 if (LOGGER.isEnabledFor(Level.DEBUG)) {
                 LOGGER.log(Level.DEBUG, "Rule ClearCondition [%s]", ruleDef.getClearCondition());
             }
            this.clearConditionEvaluator = new StreamingQueryEvaluator(ruleDef.getClearCondition());
        }
    }

    @Override
    public String getName() {
        return ruleDef.getName();
    }

    @Override
    public RuleDef getRuleDef() {
        return ruleDef;
    }

    @Override
    public void addClearAction(Action action) {
        clearActionList.put(action.getActionDef().getName(), action);
    }

    @Override
    public void addSetAction(Action action) {
        setActionList.put(action.getActionDef().getName(), action);

    }

    @Override
    public Action getSetAction(String actionName) {
        return setActionList.get(actionName);
    }

    @Override
    public Action getClearAction(String actionName) {
        return clearActionList.get(actionName);
    }

    @Override
    public Collection<Action> getSetActions() {
        return setActionList.values();
    }

    @Override
    public Collection<Action> getClearActions() {
        return clearActionList.values();
    }

    @Override
    public void eval(MetricNodeEvent nodeEvent) throws Exception {
        MetricNode mNode = nodeEvent.getMetricNode();
        long t1, t2;
        
        if (!isEnabled) {
        	if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "Rule [%s] is disabled, will not evaluate.", getName());
			}
        	return;
        }

        if (setConditionEvaluator == null) {
            return;
        }
        
        //Skipping the metric events of type Delete for rule evaluation
        if (!ruleService.isProcessDeleteEvents() && MetricEventType.DELETE.equals(nodeEvent.getEventType())) {
            return;
        }
        
        boolean eval = setConditionEvaluator.eval(mNode);
        if (eval) {
			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, "SetCondition evaluated TRUE for [%s] Rule=[%s] SetCondition=[%s]", nodeEvent,
						getName(), setConditionEvaluator.getQueryDef());
			}
            t1 = System.currentTimeMillis();
            handleSetConditionForSetActions(nodeEvent, true);
            t2 = System.currentTimeMillis();
            totalTime.addAndGet(t2 - t1);
            totalEval.getAndIncrement();

        } else if (clearConditionEvaluator != null) {
            eval = clearConditionEvaluator.eval(mNode);
            if (eval) {
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "ClearCondition evaluated TRUE for [%s] Rule=[%s] ClearCondition=[%s]",
							nodeEvent, getName(), clearConditionEvaluator.getQueryDef());
				}
                RuleMetricNodeState rmState = handleClearConditionForSetActions(nodeEvent);
                if (rmState != null) {
                    t1 = System.currentTimeMillis();
                    handleClearConditionForClearActions(rmState, nodeEvent);
                    t2 = System.currentTimeMillis();
                    totalTime.addAndGet(t2 - t1);
                    totalEval.getAndIncrement();

                } else {

                }
            } else {
                handleSetConditionForSetActions(nodeEvent, false);
            }
        } else {
            handleSetConditionForSetActions(nodeEvent, false);
        }
    }

    private RuleMetricNodeState handleClearConditionForSetActions(MetricNodeEvent nodeEvent) {
//        boolean isSetBefore = false;
        try {
            MetricKey mKey = (MetricKey) nodeEvent.getMetricNode().getKey();
            for (Action action : setActionList.values()) {
                RuleMetricNodeState ruleMetricNodeState = om.getRuleMetricNode(ruleDef.getName(), action.getActionDef().getName(), mKey);									
				handleClearConditionForSetAction(action, nodeEvent, ruleMetricNodeState);
				if (ruleMetricNodeState != null) {
					return ruleMetricNodeState;
				}
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
        return null;
    }

    private void handleClearConditionForSetAction(Action action, MetricNodeEvent nodeEvent, RuleMetricNodeState actionState) throws Exception {
        if (actionState == null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Clear condition passed, but not previously set [%s], [%s]", nodeEvent.getMetricNode().getKey(), action.getActionDef().getName());
            }
        } else {
        	actionState.setClearConditionKey((MetricKey) nodeEvent.getMetricNode().getKey());
            if (action.getActionDef().getConstraint().getConstraint().equals(Constraint.TIMED)) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Clear condition passed, removing action schedule [%s], [%s]", nodeEvent.getMetricNode().getKey(), action.getActionDef().getName());
                }
                ruleService.getActionManager().removeActionSchedule(actionState);
                // And clear the state too.
                om.removeRuleMetricNode(actionState);

            } else {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Clear condition passed, clearing set state for action [%s], [%s]", nodeEvent.getMetricNode().getKey(), action.getActionDef().getName());
                }
                om.removeRuleMetricNode(actionState);
            }
        }

    }

    private void handleClearConditionForClearActions(RuleMetricNodeState rmState, MetricNodeEvent nodeEvent) {
        try {
            for (Action action : clearActionList.values()) {
                RuleMetricNodeStateKey rKey = new RuleMetricNodeStateKey(ruleDef.getName(), action.getActionDef().getName(), (MetricKey) nodeEvent.getMetricNode().getKey());
                handleClearConditionForClearAction(rmState, action, nodeEvent, rKey);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    private void handleSetConditionForSetActions(MetricNodeEvent nodeEvent, boolean isSet) {
        try {
            MetricKey mKey = (MetricKey) nodeEvent.getMetricNode().getKey();
            for (Action action : setActionList.values()) {
                RuleMetricNodeStateKey rKey = new RuleMetricNodeStateKey(ruleDef.getName(), action.getActionDef().getName(), mKey);

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Trying to get Key: %s iSet:%s", rKey, isSet);
                }
                RuleMetricNodeState ruleMetricNodeState = om.getRuleMetricNode(ruleDef.getName(), action.getActionDef().getName(), mKey);
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					if (ruleMetricNodeState == null) {
						LOGGER.log(Level.DEBUG, " NOT FOUND Key: %s", rKey);
					} else {
						LOGGER.log(Level.DEBUG, " FOUND Key: %s, RuleMetricNodeState=[%s]", rKey, ruleMetricNodeState);
					}
				}
                handleSetConditionForSetAction(action, nodeEvent, ruleMetricNodeState, isSet, rKey);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    private void handleSetConditionForSetAction(Action action, MetricNodeEvent nodeEvent, RuleMetricNodeState actionState, boolean isSet, RuleMetricNodeStateKey rKey) throws Exception {
        ActionDef actionDef = action.getActionDef();

        if (actionDef.getConstraint().getConstraint().equals(InvokeConstraint.Constraint.ALWAYS)) {
            if (!isSet) {
                return;
            }
            try {
            	//Setting flag which denotes that the rule is set atleast once
            	if(getRuleDef()!=null && getRuleDef() instanceof MutableRuleDef){
            		((MutableRuleDef)getRuleDef()).setSetOnce(true);
            	}
            	
                if (actionState == null) {
//                    actionState = store.createRuleMetricNode(this.getName(), action.getName(), (MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState = new RuleMetricNodeStateImpl(this.getName(), action.getName(), (MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState.setIsNew(true);
                    actionState.setSetConditionKey((MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState.setMetricNode(nodeEvent.getMetricNode());
                    om.save(actionState);
                }
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "ALWAYS fired for action %s", rKey);
                }
               
                action.performAction(this, nodeEvent);
                
                //Write to the Alert logs.
                AlertNodeState alertNode = new AlertNodeStateImpl(this, action, actionState);
                //Create alert
                ruleService.createAndAssertAlertFactFromAlertNode(alertNode);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                LOGGER.log(Level.ERROR, "Error while performing action %s", e, actionDef.getName());
            }
        } else if (actionDef.getConstraint().getConstraint().equals(InvokeConstraint.Constraint.ONCE_ONLY)) {
            if (!isSet) {
                return;
            }
            if (actionState == null) {
                try {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "ONCEONLY fired for action %s", rKey);
                    }
                    //Setting flag which denotes that the rule is set atleast once
                	if(getRuleDef()!=null && getRuleDef() instanceof MutableRuleDef){
                		((MutableRuleDef)getRuleDef()).setSetOnce(true);
                	}

                    actionState = new RuleMetricNodeStateImpl(this.getName(), action.getName(), (MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState.setIsNew(true);
                    actionState.setSetConditionKey((MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState.setMetricNode(nodeEvent.getMetricNode());
                    om.save(actionState);
                    
                    action.performAction(this, nodeEvent);
                    
                    //Write to the Alert logs.
                    AlertNodeState alertNode = new AlertNodeStateImpl(this, action, actionState);
                    //Create alert
                    ruleService.createAndAssertAlertFactFromAlertNode(alertNode);

                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error while performing action %s", e, rKey);
                }
            } else {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "ONCEONLY, hence action not fired for action %s", rKey);
                }
            }
        } else {
            if (actionState == null) {
                if (!isSet) {
                    return;
                } else {                	
                    // First time ever, perform action immediately and schedule rest number of times.
                    action.performAction(this, nodeEvent);
                    TimeBasedConstraint tbc = (TimeBasedConstraint) actionDef.getConstraint();
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Timer started for action [%s] Constraint=[%s]", rKey, tbc);
                    }
                    
                    // first time ever..., register it
                    actionState = new RuleMetricNodeStateImpl(this.getName(), action.getName(), (MetricKey) nodeEvent.getMetricNode().getKey());
                    actionState.setIsNew(true);
                    actionState.setSetConditionKey((MetricKey) nodeEvent.getMetricNode().getKey());
                    // Clone metric node and set here.
                    MetricNode clonedMetric = nodeEvent.getMetricNode().deepCopy();                    
                    actionState.setMetricNode(clonedMetric);
                    long now = System.currentTimeMillis();
                    actionState.setLastFireTime(now);
                    actionState.setScheduledTime(now + tbc.getInvocationFrequency());
                    ruleService.getActionManager().scheduleAction(actionState);
                    om.save(actionState);
                }
            } else {
                if (isSet) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Action already scheduled %s", rKey);
                    }
                } else {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Condition improved for action %s", rKey);
                    }
                    // condition improved. Check if the constraint is to cancel the action
                    TimeBasedConstraint tbc = (TimeBasedConstraint) actionDef.getConstraint();
                    if (tbc.getTimeConstraint().equals(TimeBasedConstraint.Constraint.TILL_CONDITION_IMPROVES)) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Condition improved so canceling action %s", rKey);
                        }
                        ruleService.getActionManager().removeActionSchedule(actionState);
                        om.removeRuleMetricNode(actionState);
                    } else {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Condition improved but not clearing action since configured for WAIT_TILL_CLEAR %s", rKey);
                        }
                    }
                }

            }

        }

    }

	private void handleClearConditionForClearAction(RuleMetricNodeState rmState, Action action, MetricNodeEvent nodeEvent, RuleMetricNodeStateKey rKey) {

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Clear condition passed, executing clear action %s", rKey);
        }
        try {

            action.performAction(this, nodeEvent);
            
            //Write to the Alert logs.
            AlertNodeState alertNode = new AlertNodeStateImpl(this, action, rmState);
            
            //Create alert for clear condition met.
            ruleService.createAndAssertAlertFactFromAlertNode(alertNode);

        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while performing action %s", e, rKey);
        }
    }        

}
