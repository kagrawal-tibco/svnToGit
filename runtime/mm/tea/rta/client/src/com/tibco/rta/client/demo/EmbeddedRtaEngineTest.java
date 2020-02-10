package com.tibco.rta.client.demo;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.tibco.rta.ConfigProperty;
//import com.tibco.rta.ExampleComponentVersion;
import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.RtaCommand;
import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.RtaConnection;
import com.tibco.rta.RtaConnectionFactory;
import com.tibco.rta.RtaConnectionFactoryEx;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaNotification;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.RtaSession;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.client.taskdefs.IdempotentRetryTask;
import com.tibco.rta.engine.RtaEngine;
import com.tibco.rta.engine.RtaEngineExFactory;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.RuleFactoryEx;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.rule.mutable.MutableTimeBasedConstraint;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomBoolean;
import com.tibco.rta.property.impl.PropertyAtomInt;
import com.tibco.rta.property.impl.PropertyAtomLong;
import com.tibco.rta.property.impl.PropertyAtomString;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FactResultTuple;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.Query;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.query.impl.QueryFactoryEx;

public class EmbeddedRtaEngineTest {

    public static DefaultRtaSession session;
    private String sessionName;
    private String ruleName = "SPMAPIDemoRule";


    private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger("rta.api.demo");

    private Properties configuration;
    private String propFileName;
    private String[] args;

    // Schema
    public static String SCHEMA_NAME = "SPM_Example";

    // Cube(s)
    public static String CUBE_DEV = "DemoNodeCube";

    // Dimension Hierarchies
    public static String DIM_HIERARCHY_ASSET = "AssetHierarchy";
    public static String DIM_HIERARCHY_DEMO = "DemoServiceHitCount";

    // DEMO Attributes
    public static String ATTRIB_ENVIRONMENT = "environment";
    public static String ATTRIB_APPLICATION = "application_name";
    public static String ATTRIB_SERVICE = "contract_name";
    public static String ATTRIB_SERVICETYPE = "contract_type";
    public static String ATTRIB_OPERATION = "operation_name";
    public static String ATTRIB_HOST = "host";
    public static String ATTRIB_NODE = "node";
    public static String ATTRIB_TIMESTAMP = "timestamp";
    public static String ATTRIB_HIT = "hit";
    public static String ATTRIB_SUCCESS = "success";
    public static String ATTRIB_FAULT = "fault";
    public static String ATTRIB_ASSET_STATUS = "asset_status";

    // DEMO Dimensions
    public static String DIM_LEVEL_ENVIRONMENT = "environment";
    public static String DIM_LEVEL_APPLICATION = "application_name";
    public static String DIM_LEVEL_SERVICE = "service_name";
    public static String DIM_LEVEL_SERVICETYPE = "service_type";
    public static String DIM_LEVEL_OPERATION = "operation_name";
    public static String DIM_LEVEL_HOST = "host";
    public static String DIM_LEVEL_NODE = "node";

    // Time Dimensions
    public static String DIM_LEVEL_WEEK = "weeks";
    public static String DIM_LEVEL_DAY = "days";
    public static String DIM_LEVEL_HOUR = "hours";
    public static String DIM_LEVEL_MINUTE = "minutes";

    // Computed Measurement
    public static String MEASUREMENT_HITCOUNT = "HitCount";
    public static String MEASUREMENT_ASSETSTATUS = "AssetStatus";

    public EmbeddedRtaEngineTest(String[] args) {
        this.args = args;
    }

    public static void main(String args[]) {
        try {
        	EmbeddedRtaEngineTest spmApiDemo = new EmbeddedRtaEngineTest(args);
            spmApiDemo.parseArgs();
            spmApiDemo.getConfiguration();
//            System.out.print(ExampleComponentVersion.printVersionBanner());
//            System.out.println(ExampleComponentVersion.printVersionInfo());
            spmApiDemo.startRtaEngine();
            spmApiDemo.startDemo();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void parseArgs() {
        int i = 0;

        if (args.length == 0) {
            usage();
        }

        while (i < args.length) {
            if (args[i].compareTo("-propFile") == 0) {
                if ((i + 1) >= args.length) {
                    usage();
                }
                propFileName = args[i + 1];
                i += 2;
            } else {
                usage();
                break;
            }
        }
    }

    public Properties getConfiguration() throws Exception {
        if (configuration == null) {
            configuration = new Properties();
            configuration.load(new FileInputStream(new File(propFileName)));
        }
        return configuration;
    }

    private void startRtaEngine() throws Exception {
		RtaEngine engine = RtaEngineExFactory.getInstance().getEngine();		
		engine.init(getConfiguration());
		engine.start();
		Thread.sleep(5000);    	
    }
    
    private void usage() {
        System.err.println("SpmApiDemo -propFile <filename>");
        System.exit(0);
    }

    private PropertyAtom<?> getPropertyAtom(DataType dataType, String value) {
        switch (dataType) {
            case BOOLEAN:
                return new PropertyAtomBoolean(Boolean.parseBoolean(value));
            case STRING:
                return new PropertyAtomString(value);
            case INTEGER:
                return new PropertyAtomInt(Integer.parseInt(value));
            case LONG:
                return new PropertyAtomLong(Long.parseLong(value));
            default:
                return null;
        }
    }

    private void initSession() throws Exception {

        String serverUrl = "local"; //(String) ConfigProperty.JMS_PROVIDER_JNDI_URL.getValue(configuration);
        RtaConnectionFactory connectionFac = new RtaConnectionFactoryEx();
        Map<ConfigProperty, PropertyAtom<?>> configurationMap = new HashMap<ConfigProperty, PropertyAtom<?>>();
        Enumeration<?> propertyNamesEnumeration = configuration.propertyNames();

        while (propertyNamesEnumeration.hasMoreElements()) {
            String propertyName = (String) propertyNamesEnumeration.nextElement();
            ConfigProperty configProperty = ConfigProperty.getByPropertyName(propertyName);
            if (configProperty != null) {
                configurationMap.put(configProperty, getPropertyAtom(configProperty.getDataType(), (String) configProperty.getValue(configuration)));
            }
        }

        RtaConnection connection = connectionFac.getConnection(serverUrl, ConfigProperty.CONNECTION_USERNAME.getValue(configuration).toString(), ConfigProperty.CONNECTION_PASSWORD.getValue(configuration).toString(), configurationMap);
        sessionName = UUID.randomUUID().toString();
        if (session == null) {
            session = (DefaultRtaSession) connection.createSession(sessionName, configurationMap);
            //session.setNotificationListener(new MyRtaNotificationListener(), NotificationListenerKey.EVENT_SERVER_HEALTH | NotificationListenerKey.EVENT_FACT_DROP | NotificationListenerKey.EVENT_FACT_PUBLISH | NotificationListenerKey.EVENT_TASK_REJECT);
            session.init();
            session.getAllActionFunctionDescriptors();
            session.setCommandListener(new MyCommandListener());
        }
    }

    public void startDemo() throws Exception {
        String copyrightMsg = "(c) Copyright 2012-2013, TIBCO Software Inc.  All rights reserved.";
        System.out.println("\n### TIBCO SPM 2.2.0 " + copyrightMsg + " ###");
        System.out.println("\nTIBCO SPM 2.2.0 API Demo Starting..");
        System.out.println("\n********************************************************************************");
        System.out.println("Establishing session with the Metric Server..");
        initSession();
        System.out.println("Establishing session with the Metric Server Complete.");

        System.out.println("\n\n********************************************************************************");
        System.out.println("\nDemonstrating publish facts..");
        System.out.println("Sending 60 facts to the metric server in all..");
        System.out.println("20 facts where engine=machine1, service=PnrCommonService and operation=checkPnrStatus");
        publishFactPnrCommonService(session, 20);
        System.out.println("20 facts where engine=machine1, service=SeatAllocationService and operation=getFrequentFlyerMiles");
        publishFactSeatAllocationService(session, 20);
        System.out.println("20 facts where engine=machine1, service=ClaimService and operation=createClaim");
        publishFactBaggageService(session, 20);
        System.out.println("Sending 60 facts to the metric server in all Completed. See server logs for processing details.");
        System.out.println("\nDemonstrating publish facts Complete.");

        System.out.println("\n********************************************************************************");

        Thread.sleep(1000);
        System.out.println("\n\n********************************************************************************");
        System.out.println("\n\nDemonstrating Snapshot query..");
        System.out.println("Select all metric nodes where Hit Count > 15 at the Service Level");
        System.out.println("3 rows expected, with HitCount=20");
        createServiceLevelSnapshotQueryWithFilter(session);
        System.out.println("Demonstrating Snapshot query Complete.");
        System.out.println("\n********************************************************************************");

        Thread.sleep(1000);
        System.out.println("\n\n********************************************************************************");
        System.out.println("\nDemonstrating Streaming query..");
        System.out.println("Registering a streaming query to stream when HitCount=40 and level=service..");
        executeServiceLevelStreamingQueryWithGtFilter(session);
        System.out.println("Registering a streaming query to stream when HitCount=40 and level=service Complete.");
        System.out.println("Sending 60 more facts to the metric server..");
        System.out.println("3 rows expected at the service level, with HitCount=40");
        publishFactPnrCommonService(session, 20);
        publishFactSeatAllocationService(session, 20);
        publishFactBaggageService(session, 20);
        Thread.sleep(10000);
        System.out.println("\nDemonstrating Streaming query Complete.");
        System.out.println("\n********************************************************************************");


        System.out.println("\n\n********************************************************************************");
        System.out.println("\nDemonstrating Rule..");
        System.out.println("Creating a rule where SET condition is 41 > HitCount > 45 and CLEAR condition is 47 > HitCount > 50 for level=service");
        System.out.println("This rule will fire for every 1 second for a maximum of 5 times");
        System.out.println("The SET action is a COMMAND notification to this session indicating that the condition is set.");
        System.out.println("The CLEAR action is a COMMAND notification to this session indicating that the condition has cleared.");

        try {
            createRule(session);
        } catch (Exception e) {
            System.out.println("Rule already created, demo will proceed to show rules being evaluated and action taken.");
        }

        System.out.println("\nPublishing some facts in order to increase the HitCount that will SET the condition...");
        for (int i = 0; i < 5; i++) {
            Thread.sleep(2000);
            publishFactPnrCommonService(session, 1);
        }
        Thread.sleep(5000);

        System.out.println("\nPublishing some more facts in order to increase the HitCount that will CLEAR the condition...");
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            publishFactPnrCommonService(session, 1);
        }

        System.out.println("\nDemonstrate Rule Complete");
        session.close();
        System.out.println("Finished closing session");
        Thread.sleep(30000);

        System.out.println("\n********************************************************************************");
        System.out.println("\nTIBCO SPM 2.2.0 API Demo Complete.");
    }

//    private Query executeAlertsStreamingQuery(RtaSession session) throws Exception {
//            final Query rtaQuery = session.createQuery();
//
//            QueryByFilterDef queryDef = rtaQuery.newQueryByFilterDef("SPM_2_0",
//                    "SystemAlerts",
//                    "Alerts",
//                    "AlertLevel");
//
//            queryDef.setName("AlertSubscription");
//            queryDef.setQueryType(QueryType.STREAMING);
//            queryDef.setBatchSize(100);
//
//            //dimension user name filter
//            Filter userNameFilter = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, "userName", "admin");;
//            queryDef.setFilter(userNameFilter);
//
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        rtaQuery.execute();
////                        rtaQuery.setResultHandler(new QueryResultHandlerImpl());
//                    } catch (RtaException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            new Thread(runnable).start();
//            Thread.sleep(1000000);
//            return rtaQuery;
//
//        }

    private void publishFactPnrCommonService(RtaSession session, int count) throws Exception {
        RtaSchema schema = session.getSchema(SCHEMA_NAME);

        for (int i = 0; i < count; i++) {
            Fact fact1 = schema.createFact();

            fact1.setAttribute(ATTRIB_NODE, "node1");
            fact1.setAttribute(ATTRIB_SERVICE, "PnrCommonService");
            fact1.setAttribute(ATTRIB_OPERATION, "checkPnrStatus");
            fact1.setAttribute(ATTRIB_HIT, 1L);
            fact1.setAttribute(ATTRIB_SUCCESS, 1L);
            fact1.setAttribute(ATTRIB_FAULT, 0L);
            fact1.setAttribute(ATTRIB_TIMESTAMP, 1363856700233L);

            fact1.setAttribute(ATTRIB_ENVIRONMENT, "env1");
            fact1.setAttribute(ATTRIB_APPLICATION, "app1");
            fact1.setAttribute(ATTRIB_SERVICETYPE, "service");
            fact1.setAttribute(ATTRIB_HOST, "TH1");

            session.publishFact(fact1);
//            Thread.sleep(1000);
        }
        Thread.sleep(1000);
    }

    private void publishFactSeatAllocationService(RtaSession session, int count) throws Exception {
        RtaSchema schema = session.getSchema(SCHEMA_NAME);
        for (int i = 0; i < count; i++) {
            Fact fact1 = schema.createFact();

            fact1.setAttribute(ATTRIB_NODE, "node1");
            fact1.setAttribute(ATTRIB_SERVICE, "SeatAllocationService");
            fact1.setAttribute(ATTRIB_OPERATION, "getFrequentFlyerMiles");
            fact1.setAttribute(ATTRIB_HIT, 1L);
            fact1.setAttribute(ATTRIB_SUCCESS, 1L);
            fact1.setAttribute(ATTRIB_FAULT, 0L);
            fact1.setAttribute(ATTRIB_TIMESTAMP, 1363856700233L);

            fact1.setAttribute(ATTRIB_ENVIRONMENT, "env1");
            fact1.setAttribute(ATTRIB_APPLICATION, "app1");
            fact1.setAttribute(ATTRIB_SERVICETYPE, "service");
            fact1.setAttribute(ATTRIB_HOST, "TH1");

            session.publishFact(fact1);
//            Thread.sleep(1000);
        }
        Thread.sleep(1000);
    }

    private void publishFactBaggageService(RtaSession session, int count) throws Exception {
        RtaSchema schema = session.getSchema(SCHEMA_NAME);
        for (int i = 0; i < count; i++) {
            Fact fact1 = schema.createFact();

            fact1.setAttribute(ATTRIB_NODE, "node1");
            fact1.setAttribute(ATTRIB_SERVICE, "BaggageService");
            fact1.setAttribute(ATTRIB_OPERATION, "getBaggageLostClaimsInfo");
            fact1.setAttribute(ATTRIB_HIT, 1L);
            fact1.setAttribute(ATTRIB_SUCCESS, 1L);
            fact1.setAttribute(ATTRIB_FAULT, 0L);
            fact1.setAttribute(ATTRIB_TIMESTAMP, 1363856700233L);

            fact1.setAttribute(ATTRIB_ENVIRONMENT, "env1");
            fact1.setAttribute(ATTRIB_APPLICATION, "app1");
            fact1.setAttribute(ATTRIB_SERVICETYPE, "service");
            fact1.setAttribute(ATTRIB_HOST, "TH1");

            session.publishFact(fact1);
//            Thread.sleep(1000);
        }
        Thread.sleep(1000);
    }

    private void createServiceLevelSnapshotQueryWithFilter(RtaSession session) throws Exception {
        Thread.sleep(1000);
        final Query query = session.createQuery();

        QueryByFilterDef queryDef = query.newQueryByFilterDef(SCHEMA_NAME, CUBE_DEV, DIM_HIERARCHY_DEMO, MEASUREMENT_HITCOUNT);
        queryDef.setName("SnapshotQueryDef_Gt_filter");
        queryDef.setQueryType(QueryType.SNAPSHOT);
        queryDef.setBatchSize(5);

        Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, DIM_LEVEL_SERVICE);
        Filter gtFilter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 5.0);
        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilter.addFilter(eqFilter, gtFilter);
        queryDef.setFilter(andFilter);

        Browser<MetricResultTuple> browser = query.execute();

        processMetricResults(browser);

        query.close();
    }

    private void processMetricResults(Browser<MetricResultTuple> browser) throws Exception {

        while (browser.hasNext()) {
            MetricResultTuple rs = browser.next();
            @SuppressWarnings("unchecked")
            SingleValueMetric<Long> metric = (SingleValueMetric<Long>) rs.getMetric("HitCount");
            MetricKey key = (MetricKey) metric.getKey();

            System.out.println(String.format("  Level = %s", key.getDimensionLevelName()));
            for (String dimName : key.getDimensionNames()) {
                System.out.println(String.format("    Dimension name = %s, value = %s", dimName, key.getDimensionValue(dimName)));
            }
            System.out.println(String.format("    Metric = %s, value = %s createdTime = %d, updatedTime = %d",
                    metric.getDescriptor().getMeasurementName(), metric.getValue(), metric.getCreatedTime(), metric.getLastModifiedTime()));
            System.out.println();

            getChildMetrics(metric);
            getConstituentFacts(metric);
        }
    }

    private void getChildMetrics(Metric<?> metric) throws Exception {
        System.out.println("\n\nDemonstrating getChildMetrics.");

        Browser<QueryResultTuple> childMetricsBrowser = session.getChildMetrics(metric, null);

        if (childMetricsBrowser != null) {
            while (childMetricsBrowser.hasNext()) {
                QueryResultTuple queryResultTuple = (QueryResultTuple) childMetricsBrowser.next();
                MetricResultTuple metricResultTuple = queryResultTuple.getMetricResultTuple();

                for (String metricName1 : metricResultTuple.getMetricNames()) {
                    SingleValueMetric<?> childMetric = (SingleValueMetric<?>) metricResultTuple.getMetric(metricName1);
                    MetricKey key = (MetricKey) childMetric.getKey();
                    System.out.println(String.format( "   Level = %s", key.getDimensionLevelName()));

                    for (String dimName : key.getDimensionNames()) {
                        System.out.println(String.format("   Dimension name = %s, value = %s", dimName, key.getDimensionValue(dimName)));
                    }

                    System.out.println(String.format("   Metric = %s, value = %s", childMetric.getDescriptor().getMeasurementName(), childMetric.getValue()));

                    System.out.println();
                    Thread.sleep(1000);
                }
            }
        }
        System.out.println("Demonstrating getChildMetrics Complete.");
        System.out.println("\n********************************************************************************");

    }

    private void getConstituentFacts(Metric<?> metric) throws Exception {
        System.out.println("\n\nDemonstrating getConstituentFacts.");

        Browser<FactResultTuple> childFactsBrowser = session.getConstituentFacts(metric, null);

        if (childFactsBrowser != null) {
            while (childFactsBrowser.hasNext()) {
                FactResultTuple childFactResultTuple = childFactsBrowser.next();
                FactKeyImpl factKey = childFactResultTuple.getFactKey();
                Map<String, Object> attributes = childFactResultTuple.getFactAttributes();

                System.out.println("Fact Details");
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    System.out.println(String.format( "   Attribute name = %s, value = %s", entry.getKey(), entry.getValue()));
                }
            }
        }
        System.out.println("\n\nDemonstrating getConstituentFacts Complete.");
        System.out.println("\n********************************************************************************");
    }

    private Query executeServiceLevelStreamingQueryWithGtFilter(RtaSession session) throws Exception {
        final Query query = session.createQuery();
        query.setResultHandler(new MyQueryResultHandler());

        QueryByFilterDef queryDef = query.newQueryByFilterDef(SCHEMA_NAME, CUBE_DEV, DIM_HIERARCHY_DEMO, MEASUREMENT_HITCOUNT);
        queryDef.setName("HitCount_40_filter");
        queryDef.setQueryType(QueryType.STREAMING);
        queryDef.setBatchSize(9);

        Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, DIM_LEVEL_SERVICE);
        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 40.0);

        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilter.addFilter(eqFilter, eqFilter1);
        queryDef.setFilter(andFilter);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    query.execute();
                } catch (RtaException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
        Thread.sleep(1000);
//        query.close();
        return query;

    }

    @SuppressWarnings("unused")
    private MutableRuleDef createDummyRule(RtaSession session) throws Exception {
        MutableRuleDef ruleDef;
        QueryFactory qfac = QueryFactoryEx.INSTANCE;
        RuleFactory factory = new RuleFactoryEx();
        ruleDef = factory.newRuleDef(ruleName + Math.round(Math.random()));

        ruleDef.setScheduleName("schedule1");
        ruleDef.setUserName("userName1");

        // set condition
        QueryByFilterDef setCondition = qfac.newQueryByFilterDef(SCHEMA_NAME, CUBE_DEV, DIM_HIERARCHY_DEMO, MEASUREMENT_HITCOUNT);
        setCondition.setName("RuleService");
        setCondition.setQueryType(QueryType.STREAMING);
        setCondition.setBatchSize(6);
        Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, DIM_LEVEL_SERVICE);
        Filter gtFilter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 41.0);
        Filter ltFilter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 45.0);
        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
        //OrFilter orFilter = QueryFactoryEx.INSTANCE.newLikeFilter(FilterKeyQualifier., valueRegEx)


        andFilter.addFilter(eqFilter, gtFilter, ltFilter);
        setCondition.setFilter(andFilter);

        ruleDef.setSetCondition(setCondition);

        return ruleDef;
    }

    private MutableRuleDef createRule(RtaSession session) throws Exception {
        MutableRuleDef ruleDef;
        QueryFactory qfac = QueryFactoryEx.INSTANCE;

        // -------------------Set Condition------------------------
        QueryByFilterDef setCondition = qfac.newQueryByFilterDef(SCHEMA_NAME, CUBE_DEV, DIM_HIERARCHY_DEMO, MEASUREMENT_HITCOUNT);
        setCondition.setName("RuleService");
        setCondition.setQueryType(QueryType.STREAMING);
        setCondition.setBatchSize(6);
        Filter eqFilter = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, DIM_LEVEL_SERVICE);
        Filter gtFilter = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 41.0);
        Filter ltFilter = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 45.0);

        AndFilter andFilterInnerSet = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilterInnerSet.addFilter(gtFilter, ltFilter);

        AndFilter andFilter = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilter.addFilter(andFilterInnerSet, eqFilter);
        setCondition.setFilter(andFilter);

        // -----------------Clear Condition-------------------------------
        // Clear Condition
        QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(SCHEMA_NAME, CUBE_DEV, DIM_HIERARCHY_DEMO, MEASUREMENT_HITCOUNT);
        clearCondition.setName("ClearCondition");
        clearCondition.setQueryType(QueryType.STREAMING);
        clearCondition.setBatchSize(6);
        Filter eqFilter1 = QueryFactoryEx.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, DIM_LEVEL_SERVICE);
        Filter gtFilter1 = QueryFactoryEx.INSTANCE.newGtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 47.0);
        Filter ltFilter1 = QueryFactoryEx.INSTANCE.newLtFilter(FilterKeyQualifier.MEASUREMENT_NAME, MEASUREMENT_HITCOUNT, 50.0);

        AndFilter andFilterInner = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilterInner.addFilter(gtFilter1, ltFilter1);

        AndFilter andFilter2 = QueryFactoryEx.INSTANCE.newAndFilter();
        andFilter2.addFilter(andFilterInner, eqFilter1);
        clearCondition.setFilter(andFilter2);

        RuleFactory factory = new RuleFactoryEx();
        ruleDef = factory.newRuleDef(ruleName + Math.round(Math.random()));

        ruleDef.setScheduleName("schedule1");
        ruleDef.setUserName("userName1");
        ruleDef.setSetCondition(setCondition);
        ruleDef.setClearCondition(clearCondition);


        InvokeConstraint invokeConstraint1 = factory.newInvokeConstraint(Constraint.TIMED);
        MutableTimeBasedConstraint tbc = (MutableTimeBasedConstraint) invokeConstraint1;
        tbc.setInvocationFrequency(1000);
        tbc.setMaxInvocationCount(5);
        tbc.setTimeConstraint(TimeBasedConstraint.Constraint.TILL_CONDITION_CLEARS);

        ActionFunctionDescriptor sendToSessionActionFn = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Sample-Action");

//        FunctionParam param = sendToSessionActionFn.getFunctionParam("session-name");
//        ActionFunctionDescriptorImpl.FunctionParamValueImpl paramValue = new FunctionDescriptorImpl.FunctionParamValueImpl();
//        paramValue.setName(param.getName());
//        paramValue.setDataType(param.getDataType());
//        paramValue.setIndex(param.getIndex());
//        paramValue.setDescription(param.getDescription());
//        paramValue.setValue(sessionName);
//        sendToSessionActionFn.addFunctionParamValue(paramValue);

        ActionDef setSendSessionAction = factory.newSetActionDef(ruleDef, sendToSessionActionFn, invokeConstraint1);
        ActionDef clearSessionAction = factory.newClearActionDef(ruleDef, setSendSessionAction);

        System.out.println("Rule Created..");
        session.createRule(ruleDef);
        return ruleDef;
    }

    private class MyQueryResultHandler implements QueryResultHandler {

        @Override
        public void onData(QueryResultTuple queryResultTuple) {
            synchronized (MyQueryResultHandler.class) {
                String pad = new String(new char[queryResultTuple.getQueryName().length()]);
                MetricResultTuple rs = queryResultTuple.getMetricResultTuple();
                if (rs != null) {
                    for (String metricName : rs.getMetricNames()) {

                        SingleValueMetric metric = (SingleValueMetric) rs.getMetric(metricName);
                        if (metric != null) {
//                            String measurementName = metric.getDescriptor().getMeasurementName();
                            MetricKey key = (MetricKey) metric.getKey();
                            if (metric.getDescriptor().getMeasurementName().equalsIgnoreCase(MEASUREMENT_HITCOUNT) || metric.getDescriptor().getMeasurementName().equalsIgnoreCase(MEASUREMENT_ASSETSTATUS)) {
                                System.out.println(String.format("%s: Level = %s", queryResultTuple.getQueryName(), key.getDimensionLevelName()));
                                for (String dimName : key.getDimensionNames()) {
                                    System.out.println(String.format("%s: Dimension name = %s, value = %s", pad, dimName, key.getDimensionValue(dimName)));
                                }
                                System.out.println(String.format("%s: Metric = %s, value = %s created = %d, updated = %d", pad,
                                        metric.getDescriptor().getMeasurementName(), metric.getValue(), metric.getCreatedTime(), metric.getLastModifiedTime()));
                                System.out.println();
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onError(Object errorContext) {
            System.out.println("OnError...");
        }
    }

    private class MyCommandListener implements RtaCommandListener {
        @Override
        public synchronized void onCommand(RtaCommand command) {

            System.out.println(String.format("Command received: %s", command.getCommand()));
            System.out.println(String.format("   Command parameters"));
            for (String property : command.getProperties()) {
                System.out.println(String.format("     Param [%s] and value [%s]", property, command.getValue(property)));
            }
        }
    }

    private class MyRtaNotificationListener implements RtaNotificationListener {

        @Override
        public void onNotification(RtaNotification notification) {
            Integer connectionEvent = (Integer) notification.getValue(RtaNotifications.CONNECTION_EVENT.name());
            if (connectionEvent != null) {
            	System.out.println(String.format("Connection Event [%s]", connectionEvent));
            }
            Fact rejectedFact = (Fact) notification.getValue(RtaNotifications.FACT_REJECT.name());
            if (rejectedFact != null) {
            	System.out.println(String.format("Fact Rejected [%s]", rejectedFact.getKey()));
            }
            IdempotentRetryTask retryTask = (IdempotentRetryTask) notification.getValue(RtaNotifications.TASK_REJECT.name());
            if (retryTask != null) {
            	System.out.println(String.format("Task Rejected [%s]", retryTask.getWrappedTask()));
            }
        }
    }
}
 
