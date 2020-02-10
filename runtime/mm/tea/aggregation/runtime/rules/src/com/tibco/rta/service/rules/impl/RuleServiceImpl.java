package com.tibco.rta.service.rules.impl;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.management.service.BEApplicationsManagementService;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.monitoring.metric.rule.RuleExistsException;
import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.FatalException;
import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.common.service.WorkItem;
import com.tibco.rta.common.service.WorkItemService;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.transport.http.DefaultMessageContext;
import com.tibco.rta.impl.SingleValueMetricImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.SnapshotQueryExecutor;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.AlertNodeState;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.runtime.model.rule.impl.AlertNodeStateKey;
import com.tibco.rta.runtime.model.rule.mutable.MutableRule;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipListener;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.rules.ActionManager;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.util.ServiceConstants;
import com.tibco.tea.agent.api.TeaObject;
import com.tibco.tea.agent.be.provider.ObjectCacheProvider;
import com.tibco.tea.agent.be.provider.ObjectProvider;
import com.tibco.tea.agent.events.NotificationService;
import com.tibco.tea.agent.events.Event.EventType;

public class RuleServiceImpl extends AbstractStartStopServiceImpl implements RuleService, GroupMembershipListener, GMPActivationListener, RuleServiceImplMBean {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_RULES.getCategory());

    RuleRegistry ruleRegistry;
    RuleFactory rfac;
    //	RuleMetricStore ruleMetricStore;
    private volatile boolean initialized;
    ActionManager actionManager;
    PersistenceService pServ;
    private static String[] itemNames = {"RuleName", "TotalEval", "TotalTime", "AvgTime"};
    private static String[] itemDescriptions = {"Rule Name", "Total evaluations for rule", "Total time to evaluate the rule", "Average time for evaluate the rule"};
    private static OpenType[] itemTypes = {SimpleType.STRING, SimpleType.LONG, SimpleType.LONG, SimpleType.DOUBLE};
    private static String[] indexNames = {"RuleName"};

    private static CompositeType compositeType = null;
    private static TabularType tabularType = null;
    private String ruleAppNameSeperator="$"; //default
    private boolean processDeleteEvents=false;

    Map<String, ActionHandlerContext> actionHandlerContexts = new LinkedHashMap<String, ActionHandlerContext>();

    private WorkItemService systemMetricsWorkHandler;

    static {
        try {
            compositeType = new CompositeType("Rule", "Rule-Execution-Info", itemNames, itemDescriptions, itemTypes);
            tabularType = new TabularType("Rule Execution", "Rule Execution statistics", compositeType, indexNames);
        } catch (OpenDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public RuleServiceImpl() throws Exception {
        super();
        rfac = new RuleFactory();
        ruleRegistry = new RuleRegistry(this);
//		ruleMetricStore = new RuleMetricStoreImpl();

    }

    @Override
    public void init(Properties configuration) throws Exception {
        if (!initialized) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Initializing Rule service..");
            }
            super.init(configuration);
            pServ = ServiceProviderManager.getInstance().getPersistenceService();
            ruleRegistry.init(configuration);
            ruleAppNameSeperator=configuration.getProperty(ServiceConstants.RULE_APP_NAME_SEPERATOR);
            String processDeleteEventsStr =(String) ConfigProperty.RTA_RULE_PROCESS_DELETE_METRIC_EVENTS.getValue(ServiceProviderManager.getInstance().getConfiguration());
            if (processDeleteEventsStr.equalsIgnoreCase("true") || processDeleteEventsStr.equalsIgnoreCase("false")) {
            	processDeleteEvents=Boolean.valueOf(processDeleteEventsStr);
            }
            
            //Build registry of action functions
            ActionFunctionsRepository actionFunctionsRepository = ActionFunctionsRepository.INSTANCE;

            Collection<String> actionNames = actionFunctionsRepository.getMetricFunctionDescriptorNames();

            for (String actionName : actionNames) {
                ActionFunctionDescriptor afd = actionFunctionsRepository.getFunctionDescriptor(actionName);
                String actionClz = afd.getImplClass();

                try {
                    ActionHandlerContext actionHandlerContext = (ActionHandlerContext) Class.forName(actionClz).newInstance();
                    actionHandlerContexts.put(actionName, actionHandlerContext);

                    actionHandlerContext.init(configuration);
                    if (LOGGER.isEnabledFor(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Initialization of action handler for action: [%s] successful.", actionName);
                    }

                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Initialization of action handler for action: [%s] failed.", e, actionName);
                    // throw e;
                }
            }

            String type = (String) ConfigProperty.RTA_ACTION_MANAGER_POLICY.getValue(configuration);
            if ("database".equals(type)) {
                actionManager = (ActionManager) Class.forName("com.tibco.rta.service.rules.impl.DBActionManagerImpl").newInstance();
            } else {
                actionManager = (ActionManager) Class.forName("com.tibco.rta.service.rules.impl.ActionManagerImpl").newInstance();
            }

            actionManager.init(configuration);
            initialized = true;

            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Initializing Rule service Complete.");
            }

            try {
                systemMetricsWorkHandler = ServiceProviderManager.getInstance().newWorkItemService("system-metrics-thread");
                //To avoid deadlocks, ensure queue depth is infinite here.
                //In reality, this queue will *never reach infinite depth since if there is a problem upstream, it will throttle itself.
                String overrideQueueSize = ConfigProperty.RTA_WORKER_QUEUE_SIZE.getPropertyName() + "." + "system-metrics-thread";
                configuration.setProperty(overrideQueueSize, String.valueOf(Integer.MAX_VALUE));
                systemMetricsWorkHandler.init(configuration);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "", e);
            }

            ServiceProviderManager.getInstance().getGroupMembershipService().addMembershipListener(this);
            ServiceProviderManager.getInstance().getGroupMembershipService().addActivationListener(this);
        }
        registerMBean(configuration);
    }

    @Override
    public WorkItemService getSystemMetricsWorkHandler() {
        return systemMetricsWorkHandler;
    }

    @Override
    public ActionHandlerContext getActionHandlerContext(String name) {
        return actionHandlerContexts.get(name);
    }

    private void registerMBean(Properties configuration) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(configuration);
        ObjectName name = new ObjectName(mbeanPrefix + ".rules:type=" + "RuleService");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

    private void loadAllRules() throws Exception {
        List<RuleDef> ruleList;

        if (pServ != null) {
            ruleList = pServ.getAllRuleNames();
            if (ruleList != null) {

            	for (RuleDef rule : ruleList) {
            		if (rule != null) {
            			if (LOGGER.isEnabledFor(Level.INFO)) {
            				LOGGER.log(Level.INFO, "Loading Rule: %s", rule.getName());
            			}
            			if(rule instanceof MutableRuleDef ){
            				//Note : The rules are loaded at the inception as disabled.
            				//They will be enabled after the initial state of the entities
            				//has been recognized.
            				if(rule.isEnabled()==true){
            					((MutableRuleDef)rule).setEnabled(false);
            					((MutableRuleDef)rule).setProperty("isEnabled", "true");
            				}
            				else if(rule.isEnabled()==false){
            					((MutableRuleDef)rule).setProperty("isEnabled", "false");
            				}
            				
            				//Resetting the set state of the rule to false
            				((MutableRuleDef)rule).setSetOnce(false);
            				
            				ruleRegistry.registerRule(rule);
            			}
            		}
            	}
            }
        }
    }

    @Override
    public void start() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Rule service..");
        }

        // load up all the rules
        loadAllRules();
        // then attach the listener, once all rules are loaded
        ServiceProviderManager.getInstance().getMetricsService().addMetricValueChangeListener(ruleRegistry);
        // ServiceProviderManager.getInstance().getPingService().addMetricValueChangeListener(ruleRegistry);

        if (systemMetricsWorkHandler != null) {
            systemMetricsWorkHandler.start();
        }
        actionManager.start();
        super.start();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Starting Rule service Complete.");
        }

    }

    @Override
    public void stop() throws Exception {
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Rule service..");
        }

        try {
            if (systemMetricsWorkHandler != null) {
                systemMetricsWorkHandler.stop();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }

        ServiceProviderManager.getInstance().getMetricsService().removeMetricValueChangeListener(ruleRegistry);
        actionManager.stop();
        ruleRegistry.clearRuleRegistry();
        super.stop();
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Stopping Rule service Complete.");
        }
    }

    @Override
    public void createRule(RuleDef ruleDef) throws Exception {
        try {
            if (!initialized) {
                throw new Exception("Service not initialized yet");
            }

            String queryOrRule = ruleDef.isStreamingQuery() ? "Streaming Query" : "Rule";

            String ruleName = ruleDef.getName();
            RuleDef existingRule = ruleRegistry.getRuleDef(ruleName);
            if (existingRule != null) {
                if (LOGGER.isEnabledFor(Level.WARN)) {
                    LOGGER.log(Level.WARN, "[%s] [%s] already exists", queryOrRule, existingRule.getName());
                    throw new RuleExistsException(existingRule.getName()+" already exists.");
                }
            } else {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.DEBUG, "Registering [%s] : [%s]", queryOrRule, ruleDef.getName());
                }
                if (!ruleDef.isStreamingQuery()) {
                    persistRule(ruleDef);
                }
                ruleRegistry.registerRule(ruleDef);
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error saving Rule [%s]", e, ruleDef.getName());
            throw e;
        }
    }

    private void persistRule(RuleDef ruleDef) throws Exception {
        if (!ruleDef.isStreamingQuery()) {
            pServ.insertRule(ruleDef);
        }
    }

    @Override
    public void deleteRule(String ruleName) throws Exception {
        try {
            pServ.deleteRule(ruleName);
            ruleRegistry.unRegisterRule(ruleName);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error deleting Rule %s", e, ruleName);
            throw e;
        }
    }

    @Override
    public List<RuleDef> getRules() throws Exception {
        List<RuleDef> ruleDefs;
        try {
            ruleDefs = ruleRegistry.getRules();
            return ruleDefs;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error in getRules", e);
            throw e;
        }
    }

    @Override
    public RuleDef getRuleDef(String ruleName) throws Exception {
        return ruleRegistry.getRuleDef(ruleName);
    }

    @Override
    public void updateRule(RuleDef rule) throws Exception {
        try {
            pServ.updateRule(rule);
            ruleRegistry.updateRule(rule);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error in updateRule %s", e, rule.getName());
            throw e;
        }
    }

    @Override
    public Rule getRule(String ruleName) throws Exception {
        try {
            return ruleRegistry.getRule(ruleName);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while getting rule = [%s]", ruleName);
            throw e;
        }
    }

    @Override
    public ActionManager getActionManager() {
        return actionManager;
    }

    @Override
    public <G extends GroupMember> void memberJoined(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void memberLeft(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onPrimary(G member) {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Rule Service.");
            }
            if (!isStarted()) {
                start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Rule Service", e);
        }
    }

    @Override
    public <G extends GroupMember> void onSecondary(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void quorumComplete(GroupMember... groupMembers) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void networkFailed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void networkEstablished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onFenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onUnfenced(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <G extends GroupMember> void onConflict(G member) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onActivate() {
        try {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Engine Activated to Primary. Will start Rule Service.");
            }
            if (!isStarted()) {
                start();
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while starting Rule Service", e);
        }
    }

    @Override
    public TabularData getRuleStatistics() {
        TabularDataSupport tb = new TabularDataSupport(tabularType);

        List<RuleDef> rules = ruleRegistry.getRules();

        for (RuleDef ruleDef : rules) {
            if (!ruleDef.isStreamingQuery()) {
                Rule rule = ruleRegistry.getRule(ruleDef.getName());
                Map<String, Object> items = new HashMap<String, Object>();
                items.put(itemNames[0], ruleDef.getName());
                items.put(itemNames[1], ((RuleImpl) rule).totalEval.get());
                items.put(itemNames[2], ((RuleImpl) rule).totalTime.get());
                Double avg = 0.0;
                if (((RuleImpl) rule).totalEval.get() != 0) {
                    avg = (double) (((RuleImpl) rule).totalTime.get() / ((RuleImpl) rule).totalEval.get());
                }

                items.put(itemNames[3], avg);
                try {
                    CompositeDataSupport compositeData = new CompositeDataSupport(compositeType, items);
                    tb.put(compositeData);
                } catch (OpenDataException e) {
                    e.printStackTrace();
                }
            }
        }
        return tb;
    }

    @Override
    public void onDeactivate() {
        try {
            stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createAndAssertAlertFactFromAlertNode(AlertNodeState alertNode) {
        ServiceProviderManager sp = ServiceProviderManager.getInstance();
        WorkItem workItem = new SystemAlertFactJob(alertNode);
        try {
            sp.getRuleService().getSystemMetricsWorkHandler().addWorkItem(workItem);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error while performing adding to system metric job pool.", e);
        }

    }

    class SystemAlertFactJob implements WorkItem {

        AlertNodeState alertNodeState;

        public SystemAlertFactJob(AlertNodeState alertNodeState) {
            this.alertNodeState = alertNodeState;
        }

        @Override
        public Object call() throws Exception {

            try {
                //create a "fact" from this.

                RtaSchema systemSchema = ModelRegistry.INSTANCE.getRegistryEntry("BETEA");

                Fact fact = systemSchema.createFact();

                AlertNodeStateKey alertKey = (AlertNodeStateKey) alertNodeState.getKey();
                MetricKey mKey = alertKey.getMetricKey();


                fact.setAttribute("alert_id", alertKey.getId());
                fact.setAttribute("schemaName", mKey.getSchemaName());
                fact.setAttribute("cube", mKey.getCubeName());
                fact.setAttribute("hierarchy", mKey.getDimensionHierarchyName());
                fact.setAttribute("dimLevel", mKey.getDimensionLevelName());
                fact.setAttribute("metricNode", mKey.toString());
                fact.setAttribute("setNode", alertNodeState.getSetConditionKey() == null ? "" :
                        alertNodeState.getSetConditionKey().toString());
                fact.setAttribute("clearNode", alertNodeState.getClearConditionKey() == null ? "" :
                        alertNodeState.getClearConditionKey().toString());
                fact.setAttribute("userName", alertNodeState.getRuleUserName()==null ? "":alertNodeState.getRuleUserName());
                fact.setAttribute("ruleName", alertKey.getRuleName());

                //TODO: Get it from RuleRegistry
                String setCondStr = "";
                try {
                    setCondStr = alertNodeState.getAction().getActionDef().getRuleDef().getSetCondition().toString();
                } catch (Exception e) {
                }

                String clearCondStr = "";
                try {
                    clearCondStr = alertNodeState.getAction().getActionDef().getRuleDef().getClearCondition().toString();
                } catch (Exception e) {
                }

                fact.setAttribute("setCondition", setCondStr);
                fact.setAttribute("clearCondition", clearCondStr);

                fact.setAttribute("actionName", alertNodeState.getActionName());
                fact.setAttribute("isSetAction", alertNodeState.getAction().isSetAction());
                fact.setAttribute("alertLevel", alertNodeState.getAlertLevel() == null ? "" : alertNodeState.getAlertLevel());
                fact.setAttribute("alertType", alertNodeState.getAlertType() == null ? "" : alertNodeState.getAlertType());
                fact.setAttribute("alertText", alertNodeState.getAlertText() == null ? "" : alertNodeState.getAlertText());
                fact.setAttribute("alertDetails", alertNodeState.getAlertDetails() == null ? "" : alertNodeState.getAlertDetails());
                fact.setAttribute("currentCount", alertNodeState.getCount());
                fact.setAttribute("totalCount", alertNodeState.getTotalCount());
                fact.setAttribute("timestamp", System.currentTimeMillis());
                fact.setAttribute("isAlertCleared", false);
                
                //Getting app name from Rule-Name to be stored in the alerts schema
                String appName="";
                if(alertKey.getRuleName()!=null && !alertKey.getRuleName().isEmpty()){
                	int index=alertKey.getRuleName().indexOf(ruleAppNameSeperator);
        			if(index>0) {
        				appName = alertKey.getRuleName().substring(0,index);
        				fact.setAttribute("appName", appName);
        			}
        			
        			//Getting rule and setting the is admin property for alert
                    MutableRule rule=(MutableRule) getRule(alertKey.getRuleName());
                    
                    if(rule!=null){
                    	String isAdmin=rule.getRuleDef().getProperty("isAdmin");
                    	
                    	if("true".equals(isAdmin) || "false".equals(isAdmin)){
                    		fact.setAttribute("isAdmin", Boolean.parseBoolean(isAdmin));
                    	}
                    	else{
                    		fact.setAttribute("isAdmin", Boolean.parseBoolean("false"));
                    	}
                    		
                    }
                }
                ArrayList<Fact> list=new ArrayList<Fact>();
            	list.add(fact);
            	BEApplicationsManagementService applicationService = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService();
            	Application application = applicationService.getApplicationByName(appName);
            	if(application.getAlertCount() == null)
            		application.setAlertCount(0);
            	application.setAlertCount(application.getAlertCount() + 1);
            	ServiceProviderManager.getInstance().getMetricsService().assertFact(new DefaultMessageContext(System.currentTimeMillis()),list);
            	ObjectProvider<? extends TeaObject> provider = ObjectCacheProvider.getInstance()
						.getProvider(Constants.BE_APPLICATION);
            	NotificationService notificationService = ObjectCacheProvider.getInstance()
    					.getProvider(Constants.BE_APPLICATION).getNotificationService();  
            	notificationService.notify(provider.getInstance(appName), EventType.CHILDREN_CHANGE, null, application);
            	//ServiceProviderManager.getInstance().getMetricsService().assertFact(fact);

                return null;
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error while asserting AlertFact: %s", e, "");
                return null;
            }

        }

        @Override
        public Object get() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    /**
     * Note that this method bypasses the sticky threads. In this case it is OK since alert metric keys are unique and not updatable.
     *
     * @param queryDef
     * @throws Exception
     */
    @Override
    public void clearAlerts(QueryDef queryDef) throws Exception {

        //validate the query, ensure only Alerts nodes are targeted.
//		validateForAlertQuery(queryDef);

        Transaction txn = RtaTransaction.get();
        try {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Alert Delete Transaction [%d] Started at time: [%d]", txn.hashCode(), System.currentTimeMillis());
            }

            //Creates a thread local transaction context
            txn.beginTransaction();

            //Queries for and updates the metric node to isCleared.
            setAlertIsCleared(queryDef);

            //This call flushes the transaction list to the database.
            txn.commit(false);


            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Alert Delete Transaction [%d] Completed at time: [%d]", txn.hashCode(), System.currentTimeMillis());
            }

            try {
                //publish the updates
                ServiceProviderManager.getInstance().getMetricsService().publishTxn(txn);
            } catch (Exception e) {
                LOGGER.log(Level.DEBUG, "Exception while publishing Alert transaction [%d]", txn.hashCode());
            }

        } catch (FatalException e) {

            LOGGER.log(Level.ERROR, "A fatal error occured while processing Alert Delete Transaction. Will stop the server.", e, txn.hashCode());
            try {
                txn.rollback();
            } catch (Exception e1) {
            }

            //initiate a shutdown
            ServiceProviderManager.getInstance().getShutdownService().shutdown();

            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Alert Delete Transaction [%d] Error. No retry.", e, txn.hashCode());
            try {
                txn.rollback();
            } catch (Exception e1) {
            }

            throw e;
        } finally {
            txn.remove();
        }
    }

    /**
     * This call updates the transaction log with the updated metric value. Hence "scheduleForDeletes"
     *
     * @throws Exception
     */
    private void setAlertIsCleared(QueryDef queryDef) throws Exception {

        ObjectManager om = ServiceProviderManager.getInstance().getObjectManager();

        SnapshotQueryExecutor executor = new SnapshotQueryExecutor(queryDef);

        List<MetricNode> nodes = executor.getNextBatch();
        while (nodes.size() > 0) {
            for (MetricNode node : nodes) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Setting IsAlertCleared for node [%s].", node.getKey());
                }
                SingleValueMetricImpl svm = (SingleValueMetricImpl) node.getMetric("IsAlertCleared");
                if (svm != null) {
                    svm.setValue(true);
                }
                ((MutableMetricNode) node).setIsNew(false);
                om.save(node);
            }
            nodes = executor.getNextBatch();
        }
    }
    
    @Override
    public boolean isProcessDeleteEvents(){
    	return processDeleteEvents;
    }
    
    @Override
    public RuleRegistry getRuleRegistry(){
    	return ruleRegistry;
    }
}
