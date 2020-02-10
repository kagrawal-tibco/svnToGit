package com.tibco.cep.runtime.service.tester.core;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 4, 2010
 * Time: 5:57:03 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class TesterConstants {

    public static final String TESTER_RESULTS_NS = "www.tibco.com/be/studio/tester";
    public static final String TIBCO_BE_BASE_NS = "www.tibco.com/be/ontology";

    public static final ExpandedName EX_EXT_ID = ExpandedName.makeName("extId");
    public static final ExpandedName EX_ID = ExpandedName.makeName("Id");
    public static final ExpandedName EX_NAME = ExpandedName.makeName("name");
    public static final ExpandedName EX_NAMESPACE = ExpandedName.makeName("namespace");
    public static final ExpandedName EX_IS_SCORECARD = ExpandedName.makeName("isScorecard");
    public static final ExpandedName EX_IS_TIME_EVENT = ExpandedName.makeName("isTimeEvent");
    public static final ExpandedName EX_MULTIPLE = ExpandedName.makeName("multiple");
    public static final ExpandedName EX_DATA_TYPE = ExpandedName.makeName("dataType");
    public static final ExpandedName EX_TIMESTAMP = ExpandedName.makeName("timestamp");

    public static final ExpandedName EX_RUN_NAME = ExpandedName.makeName(TESTER_RESULTS_NS, "RunName");
    public static final ExpandedName EX_EXECUTION_OBJECT = ExpandedName.makeName(TESTER_RESULTS_NS, "ExecutionObject");
    public static final ExpandedName EX_RETE_OBJECT = ExpandedName.makeName(TESTER_RESULTS_NS, "ReteObject");
    public static final ExpandedName EX_CHANGE_TYPE = ExpandedName.makeName(TESTER_RESULTS_NS, "ChangeType");
    public static final ExpandedName EX_LIFECYCLE_OBJECT = ExpandedName.makeName(TESTER_RESULTS_NS, "LifecycleObject");
    public static final ExpandedName EX_LIFECYCLE_EVENT_TYPE = ExpandedName.makeName(TESTER_RESULTS_NS, "LifecycleEventType");

    public static final ExpandedName EX_INVOCATION_OBJECT = ExpandedName.makeName(TESTER_RESULTS_NS, "InvocationObject");
    public static final ExpandedName EX_INVOCATION_OBJECT_NS = ExpandedName.makeName(TESTER_RESULTS_NS, "Namespace");
    public static final ExpandedName EX_INVOCATION_OBJECT_TYPE = ExpandedName.makeName(TESTER_RESULTS_NS, "Type");

    public static final ExpandedName EX_CAUSAL_OBJECTS_END_STATE = ExpandedName.makeName(TESTER_RESULTS_NS, "CausalObjectsEndState");
    public static final ExpandedName EX_CAUSAL_OBJECTS = ExpandedName.makeName(TESTER_RESULTS_NS, "CausalObjects");
    public static final ExpandedName EX_RESULTS = ExpandedName.makeName(TESTER_RESULTS_NS, "TesterResults");

    public static final ExpandedName EX_CONCEPT = ExpandedName.makeName(TESTER_RESULTS_NS, "Concept");
    public static final ExpandedName EX_EVENT = ExpandedName.makeName(TESTER_RESULTS_NS, "Event");
    public static final ExpandedName EX_RULE = ExpandedName.makeName(TESTER_RESULTS_NS, "Rule");
    public static final ExpandedName EX_PROPERTY = ExpandedName.makeName(TESTER_RESULTS_NS, "property");
    public static final ExpandedName EX_MODIFIED_PROPERTY = ExpandedName.makeName(TESTER_RESULTS_NS, "modifiedProperty");
    public static final ExpandedName EX_PROPERTY_NAME = EX_NAME;
    public static final ExpandedName EX_PAYLOAD = ExpandedName.makeName(TESTER_RESULTS_NS, "payload");
    public static final ExpandedName EX_PROPERTY_INITIAL_VALUE = ExpandedName.makeName(TESTER_RESULTS_NS, "initialValue");
    public static final ExpandedName EX_PROPERTY_OLD_VALUE = ExpandedName.makeName(TESTER_RESULTS_NS, "previousValue");
    public static final ExpandedName EX_PROPERTY_NEW_VALUE = ExpandedName.makeName(TESTER_RESULTS_NS, "newValue");
}
