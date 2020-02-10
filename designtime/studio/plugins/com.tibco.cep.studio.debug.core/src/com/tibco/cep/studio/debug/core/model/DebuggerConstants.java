package com.tibco.cep.studio.debug.core.model;

import java.util.HashMap;
import java.util.Map;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.BEProperties;

/*
@author ssailapp
@date Jul 14, 2009 4:39:37 PM
 */

public interface DebuggerConstants {

	public static final String ID_RULE_DEBUG_MODEL = "com.tibco.cep.studio.debug.ui.ruleDebugModel"; 
	
    public static final String CODE_GEN_PREFIX = BEProperties.loadDefault().getString("be.codegen.rootPackage", "be.gen");

    public static final String RULE_SESSION_MANAGER_CLASS = "com.tibco.cep.runtime.session.RuleSessionManager";
    public static final String GET_CURRENT_RULE_SESSION_METHOD = "getCurrentRuleSession";
    public static final String GET_CURRENT_RULE_SESSION_METHOD_SIGNATURE = "()Lcom/tibco/cep/runtime/session/RuleSession;";

    public static final String AGENDA_TO_ARRAY_METHOD = "agendaToArray";
    public static final String AGENDA_TO_ARRAY_METHOD_SIGNATURE = "()[Lcom/tibco/cep/kernel/core/rete/conflict/AgendaItem;";

    public static final String CURRENT_AGENDA_ITEM_VAR = "item";

    public static final String AGENDA_ITEM_RULE_FIELD = "rule";
    public static final String AGENDA_ITEM_OBJECTS_FIELD = "objects";

    public static final String DATETIME_FUNCTION_CLASS = "com.tibco.cep.kernel.helper.Format";
    public static final String SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD = "setTimeZone";
    public static final String SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD_SIGNATURE = "(Ljava/util/TimeZone;)V";

    public static final String SIMPLE_DATE_FORMAT_SET_TIME_METHOD = "setTime";
    public static final String SIMPLE_DATE_FORMAT_SET_TIME_METHOD_SIGNATURE = "(Ljava/util/Date;)V";

    public static final String CALENDAR_CLASS_GET_TIMEZONE_METHOD = "getTimeZone";
    public static final String CALENDAR_CLASS_GET_TIMEZONE_METHOD_SIGNATURE = "()Ljava/util/TimeZone;";

    public static final String CALENDAR_CLASS_GET_TIME_METHOD = "getTime";
    public static final String CALENDAR_CLASS_GET_TIME_METHOD_SIGNATURE = "()Ljava/util/Date;";

    public static final String SIMPLE_DATE_FORMAT_CLASS = "java.text.SimpleDateFormat";
    public static final String SIMPLE_DATE_FORMAT_CONSTRUCTOR_SIGNATURE = "(Ljava/lang/String;)V";

    public static final String CONSTRUCTOR_NAME = "<init>";
    public static final String DEFAULT_CONSTRUCTOR_SIGNATURE = "()V";
    public static final String DATETIME_FUNCTION_CLASS_FORMAT_METHOD = "format";
    public static final String DATETIME_FUNCTION_CLASS_FORMAT_SIGNATURE = "(Ljava/util/Date;)Ljava/lang/String;";

    public static final String DEBUG_PAYLOAD_TO_STRING_METHOD = "debugPayloadToString";
    public static final String DEBUG_PAYLOAD_TO_STRING_METHOD_SIGNATURE = "()Ljava/lang/String;";

    public static final String GET_NAME_METHOD = "getName";
    public static final String GET_NAME_METHOD_SIGNATURE = "()Ljava/lang/String;";

    public static final String OBJECT_TO_STRING_METHOD = "toString";
    public static final String OBJECT_TO_STRING_METHOD_SIGNATURE = "()Ljava/lang/String;";

    public static final String PROPERTY_ATOM_GET_STRING_METHOD = "getString";
    public static final String PROPERTY_ATOM_GET_STRING_METHOD_SIGNATURE = "()Ljava/lang/String;";

    public static final String PROPERTY_ATOM_GET_VALUE_METHOD = "getValue";
    public static final String PROPERTY_ATOM_GET_VALUE_METHOD_SIGNATURE = "()Ljava/lang/Object;";

    public static final String PROPERTY_ARRAY_TO_ARRAY_METHOD = "toArray";
    public static final String PROPERTY_ARRAY_TO_ARRAY_METHOD_SIGNATURE = "()[Lcom/tibco/cep/runtime/model/element/PropertyAtom;";

    public static final String PROPERTY_CONCEPT_GET_CONCEPT_METHOD = "getConcept";
    public static final String PROPERTY_CONCEPT_GET_CONCEPT_METHOD_SIGNATURE = "()Lcom/tibco/cep/runtime/model/element/MutableConcept;";

    public static final String TIME_EVENT_GET_CLOSURE_METHOD = "getClosure";
    public static final String TIME_EVENT_GET_CLOSURE_METHOD_SIGNATURE = "()Ljava/lang/String;";

    public static final String TIME_EVENT_GET_INTERVAL_METHOD = "getInterval";
    public static final String TIME_EVENT_GET_INTERVAL_METHOD_SIGNATURE = "()J";

    public static final String TIME_EVENT_GET_SCHEDULED_TIME_METHOD = "getScheduledTime";
    public static final String TIME_EVENT_GET_SCHEDULED_TIME_METHOD_SIGNATURE = "()Ljava/util/Calendar;";

    public static final String CALENDAR_GET_TIME_METHOD = "getTime";
    public static final String CALENDAR_GET_TIME_METHOD_SIGNATURE = "()Ljava/util/Date;";

    public static final String SIMPLE_DATE_FORMAT_FORMAT_METHOD = "format";
    public static final String SIMPLE_DATE_FORMAT_FORMAT_METHOD_SIGNATURE = "(Ljava/util/Date;)Ljava/lang/String;";

    public static final String RDF_IMPL_PACKAGE = "com.tibco.cep.runtime.model.element.impl";

    public static final String ABSTRACT_CONCEPT_IMPL_CLASS = RDF_IMPL_PACKAGE + ".ConceptImpl";
    public static final String ABSTRACT_PROPERTY_ATOM_IMPL_CLASS = RDF_IMPL_PACKAGE + ".PropertyAtomImpl";
    public static final String ABSTRACT_PROPERTY_ARRAY_IMPL_CLASS = RDF_IMPL_PACKAGE + ".PropertyArrayImpl";
    public static final String PROPERTY_ATOM_CONTAINED_CONCEPT_IMPL_CLASS = RDF_IMPL_PACKAGE + ".PropertyAtomContainedConceptImpl";
    public static final String PROPERTY_ARRAY_CONTAINED_CONCEPT_IMPL_CLASS = RDF_IMPL_PACKAGE + ".PropertyArrayContainedConceptImpl";
    public static final String PROCESS_TASK_INTERFACE_INTERFACE = "com.tibco.cep.bpmn.runtime.activity.Task";//$NON-NLS-N$
    public static final String PROCESS_VARIABLES_INTERFACE_INTERFACE = "com.tibco.cep.bpmn.runtime.utils.Variables";//$NON-NLS-N$
    public static final String CONCEPT_INTERFACE_INTERFACE = com.tibco.cep.runtime.model.element.Concept.class.getName();
    public static final String EVENT_INTERFACE_INTERFACE = com.tibco.cep.kernel.model.entity.Event.class.getName();
    public static final String SIMPLE_EVENT_INTERFACE_INTERFACE = com.tibco.cep.runtime.model.event.SimpleEvent.class.getName();
    public static final String NAMED_INSTANCE_INTERFACE = com.tibco.cep.kernel.model.entity.NamedInstance.class.getName();
    public static final String TIME_EVENT_INTERFACE = com.tibco.cep.runtime.model.event.TimeEvent.class.getName();
    public static final String CONDITION_INTERFACE = com.tibco.cep.kernel.model.rule.Condition.class.getName();
    public static final String RULEFUNCTION_INTERFACE = com.tibco.cep.kernel.model.rule.RuleFunction.class.getName();
    public static final String ENTITY_INTERFACE_INTERFACE = com.tibco.cep.kernel.model.entity.Entity.class.getName();

    public static final String[] PROPERTY_ATOM_IMPL_CLASSES = {RDF_IMPL_PACKAGE + ".PropertyAtomIntImpl", RDF_IMPL_PACKAGE + ".PropertyAtomCharImpl",
            RDF_IMPL_PACKAGE + ".PropertyAtomBooleanImpl", RDF_IMPL_PACKAGE + ".PropertyAtomStringImpl",
            RDF_IMPL_PACKAGE + ".PropertyAtomLongImpl", RDF_IMPL_PACKAGE + ".PropertyAtomDoubleImpl",
            RDF_IMPL_PACKAGE + ".PropertyAtomContainedConceptImpl", RDF_IMPL_PACKAGE + ".PropertyAtomConceptReferenceImpl",
            RDF_IMPL_PACKAGE + ".PropertyAtomDateTimeImpl"
    };

    public static final String[] PROPERTY_ARRAY_IMPL_CLASSES = {RDF_IMPL_PACKAGE + ".PropertyArrayIntImpl", RDF_IMPL_PACKAGE + ".PropertyArrayCharImpl",
            RDF_IMPL_PACKAGE + ".PropertyArrayBooleanImpl", RDF_IMPL_PACKAGE + ".PropertyArrayStringImpl",
            RDF_IMPL_PACKAGE + ".PropertyArrayLongImpl", RDF_IMPL_PACKAGE + ".PropertyArrayDoubleImpl",
            RDF_IMPL_PACKAGE + ".PropertyArrayContainedConceptImpl", RDF_IMPL_PACKAGE + ".PropertyArrayConceptReferenceImpl",
            RDF_IMPL_PACKAGE + ".PropertyArrayDateTimeImpl"
    };

    public static final String RDF_PACKAGE = "com.tibco.cep.runtime.model.element";
    //    public static final Map PROPERTY_ATOM_INTERFACES2IMPL = DebuggerSupport.initAtomInterface2ImplMap();
    public static final Map<String, String> PROPERTY_ATOM_INTERFACES2IMPL = new HashMap<String, String>() {
        /**
		 * 
		 */
		private static final long serialVersionUID = -9062424101367395111L;

		@Override
        public void putAll(Map<? extends String, ? extends String> map) {
            Map<String, String> m = new HashMap<String, String>();
            m.put(RDF_PACKAGE + ".PropertyAtomInt", PROPERTY_ATOM_IMPL_CLASSES[0]);
            m.put(RDF_PACKAGE + ".PropertyAtomChar", PROPERTY_ATOM_IMPL_CLASSES[1]);
            m.put(RDF_PACKAGE + ".PropertyAtomBoolean", PROPERTY_ATOM_IMPL_CLASSES[2]);
            m.put(RDF_PACKAGE + ".PropertyAtomString", PROPERTY_ATOM_IMPL_CLASSES[3]);
            m.put(RDF_PACKAGE + ".PropertyAtomLong", PROPERTY_ATOM_IMPL_CLASSES[4]);
            m.put(RDF_PACKAGE + ".PropertyAtomDouble", PROPERTY_ATOM_IMPL_CLASSES[5]);
            m.put(RDF_PACKAGE + ".PropertyAtomContainedConcept", PROPERTY_ATOM_IMPL_CLASSES[6]);
            m.put(RDF_PACKAGE + ".PropertyAtomConceptReference", PROPERTY_ATOM_IMPL_CLASSES[7]);
            m.put(RDF_PACKAGE + ".PropertyAtomDateTime", PROPERTY_ATOM_IMPL_CLASSES[8]);
            super.putAll(m);
        }
    };
    //    public static final Map PROPERTY_ARRAY_INTERFACES2IMPL = DebuggerSupport.initArrayInterface2ImplMap();
    public static final Map<String, String> PROPERTY_ARRAY_INTERFACES2IMPL = new HashMap<String, String>() {
        @Override
        public void putAll(Map<? extends String, ? extends String> map) {
            Map<String, String> m = new HashMap<String, String>();
            m.put(RDF_PACKAGE + ".PropertyArrayInt", PROPERTY_ARRAY_IMPL_CLASSES[0]);
            m.put(RDF_PACKAGE + ".PropertyArrayChar", PROPERTY_ARRAY_IMPL_CLASSES[1]);
            m.put(RDF_PACKAGE + ".PropertyArrayBoolean", PROPERTY_ARRAY_IMPL_CLASSES[2]);
            m.put(RDF_PACKAGE + ".PropertyArrayString", PROPERTY_ARRAY_IMPL_CLASSES[3]);
            m.put(RDF_PACKAGE + ".PropertyArrayLong", PROPERTY_ARRAY_IMPL_CLASSES[4]);
            m.put(RDF_PACKAGE + ".PropertyArrayDouble", PROPERTY_ARRAY_IMPL_CLASSES[5]);
            m.put(RDF_PACKAGE + ".PropertyArrayContainedConcept", PROPERTY_ARRAY_IMPL_CLASSES[6]);
            m.put(RDF_PACKAGE + ".PropertyArrayConceptReference", PROPERTY_ARRAY_IMPL_CLASSES[7]);
            m.put(RDF_PACKAGE + ".PropertyArrayDateTime", PROPERTY_ARRAY_IMPL_CLASSES[8]);
            super.putAll(m);
        }
    };

    public static final String GET_PROPERTY_PREFIX = "get" + ModelNameUtil.MEMBER_PREFIX;
    public static final String THREAD_START_REQUEST_TYPE = "requestType";
    public static final String THREAD_START_APP = "applicationThread";
    public static final String THREAD_START_INVOKER = "invokerThread";
    public static final String ACTION_FLAGS = "actionFlags";
    public static final String USER_ACTION = "isUserAction";
    public static final String METHOD_DECL_TYPE = "methodClassType";
    public static final String BREAKPOINT_TYPE = "breakpointType";
    public static final String BREAKPOINT_ID = "breakpointId";
    public static final String METHOD_NAME = "method-name";
    public static final String REGISTER_CLASSES = "registerClasses";
    public static final String BREAKPOINT_AGENDA_RULE = "agendaRule";
    public static final String BREAKPOINT_PROCESS_RECORDED = "processRecorded";
    public static final String BREAKPOINT_INIT_PROJECT = "initProject";
    public static final String BREAKPOINT_TASK_REGISTRY_INIT = "TaskRegistryInit";
    public static final String BREAKPOINT_WM_START = "ReteWM.start";
    public static final String BREAKPOINT_INTERNAL = "internalBP";
    public static final String BREAKPOINT_USER = "userBP";
    public static final String BREAKPOINT_PROCESS = "processBP";
    public static final String BREAKPOINT_STEPOUT = "stepOutBP";
    public static final String BREAKPOINT_ENDOFMETHOD = "endofmethodBP";
    public static final String BREAKPOINT_STATE_TRANSITION_RULE = "stateTransitionRuleBP";
    public static final String BREAKPOINT_PROPERTY_STATE_MACHINE = "propertyStateMachineBP";
    public static final String BREAKPOINT_DEBUGGER_SERVICE = "debuggerServiceBP";
    public static final String BREAKPOINT_DEBUG_TASK_RESPONSE = "debugTaskResponseBP";
    public static final String BREAKPOINT_RULE_JAVA = "ruleJavaBP";
    public static final Object VM_TASK_KEY = "taskID";
    public static final String OM_TYPE_CACHE = "Cache";
    public static final String OM_TYPE_DB = "DB";
    public static final String OM_TYPE_INMEMORY = "In Memory";
    public static final String DEBUGGER_DATE_FORMAT ="yyyy MMM dd HH:mm:ss.SSS";
    
    // Debug Thread Action & Status Mask 
    public static final int DEBUG_ACTION_STATUS_MASK      = 0xFF00000;
	public static final int DEBUG_ACTION_TYPE_MASK        = 0x0FFFFF;
	
	// Debug Thread Action status
	public static final int DEBUG_ACTION_STATUS_COMPLETED 	= 0x100000 & DEBUG_ACTION_STATUS_MASK; // 1st bit on
	public static final int DEBUG_ACTION_STATUS_INVOKE_USER = 0x200000 & DEBUG_ACTION_STATUS_MASK; // 2nd bit on
	
	// Debug Thread Action detail
	public static final int DEBUG_ACTION_INIT           = 0x0;
	public static final int DEBUG_ACTION_STOP           = 0x1;
	public static final int DEBUG_ACTION_START          = 0x2;
	public static final int DEBUG_ACTION_SUSPEND        = 0x4;
	public static final int DEBUG_ACTION_RESUME         = 0x8;
	public static final int DEBUG_ACTION_STEP_IN        = 0x10;
	public static final int DEBUG_ACTION_STEP_OVER      = 0x20;
	public static final int DEBUG_ACTION_STEP_OUT       = 0x40;
	public static final int DEBUG_CALLBACK_BREAKPOINT   = 0x100;



	public static final Object NULL_TASK = -1L;



	public static final String DEBUGGER_EXTENSION_POINT_RESPONSE = "com.tibco.cep.studio.debug.core.responseDeserializer";



	public static final String DEBUGGER_EXTENSION_POINT_RESPONSE_DESERIALIZER = "deserializer";





}
