/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>ELEMENT TYPES</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getELEMENT_TYPES()
 * @model
 * @generated
 */
public enum ELEMENT_TYPES implements Enumerator {
	/**
	 * The '<em><b>Unknown</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(0, "Unknown", "Unknown"),

	/**
	 * The '<em><b>Concept</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT(1, "Concept", "Concept"),

	/**
	 * The '<em><b>Scorecard</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCORECARD_VALUE
	 * @generated
	 * @ordered
	 */
	SCORECARD(2, "Scorecard", "Scorecard"),

	/**
	 * The '<em><b>Instance</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INSTANCE_VALUE
	 * @generated
	 * @ordered
	 */
	INSTANCE(3, "Instance", "Instance"),

	/**
	 * The '<em><b>Simple Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIMPLE_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLE_EVENT(4, "SimpleEvent", "SimpleEvent"),

	/**
	 * The '<em><b>Channel</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANNEL_VALUE
	 * @generated
	 * @ordered
	 */
	CHANNEL(5, "Channel", "Channel"),

	/**
	 * The '<em><b>Destination</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESTINATION_VALUE
	 * @generated
	 * @ordered
	 */
	DESTINATION(6, "Destination", "Destination"),

	/**
	 * The '<em><b>Rule</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_VALUE
	 * @generated
	 * @ordered
	 */
	RULE(7, "Rule", "Rule"),

	/**
	 * The '<em><b>Rule Function</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_FUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_FUNCTION(8, "RuleFunction", "RuleFunction"),

	/**
	 * The '<em><b>Rule Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_SET_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_SET(9, "RuleSet", "RuleSet"),

	/**
	 * The '<em><b>State Machine</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATE_MACHINE_VALUE
	 * @generated
	 * @ordered
	 */
	STATE_MACHINE(10, "StateMachine", "StateMachine"),

	/**
	 * The '<em><b>Time Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIME_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_EVENT(11, "TimeEvent", "TimeEvent"),

	/**
	 * The '<em><b>Enterprise Archive</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ENTERPRISE_ARCHIVE_VALUE
	 * @generated
	 * @ordered
	 */
	ENTERPRISE_ARCHIVE(12, "EnterpriseArchive", "EnterpriseArchive"),

	/**
	 * The '<em><b>BE Archive Resource</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BE_ARCHIVE_RESOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	BE_ARCHIVE_RESOURCE(13, "BEArchiveResource", "BEArchiveResource"),

	/**
	 * The '<em><b>Shared Archive</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SHARED_ARCHIVE_VALUE
	 * @generated
	 * @ordered
	 */
	SHARED_ARCHIVE(14, "SharedArchive", "SharedArchive"),

	/**
	 * The '<em><b>Process Archive</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_ARCHIVE_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS_ARCHIVE(15, "ProcessArchive", "ProcessArchive"),

	/**
	 * The '<em><b>Adapter Archive</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADAPTER_ARCHIVE_VALUE
	 * @generated
	 * @ordered
	 */
	ADAPTER_ARCHIVE(16, "AdapterArchive", "AdapterArchive"), /**
	 * The '<em><b>Primitive Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	PRIMITIVE_TYPE(16, "PrimitiveType", "PrimitiveType"), /**
	 * The '<em><b>Decision Table</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DECISION_TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	DECISION_TABLE(17, "DecisionTable", "DecisionTable"), /**
	 * The '<em><b>Metric</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METRIC_VALUE
	 * @generated
	 * @ordered
	 */
	METRIC(18, "Metric", "Metric"), /**
	 * The '<em><b>Domain</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_VALUE
	 * @generated
	 * @ordered
	 */
	DOMAIN(19, "Domain", "Domain"), /**
	 * The '<em><b>Project</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROJECT_VALUE
	 * @generated
	 * @ordered
	 */
	PROJECT(20, "Project", "Project"), /**
	 * The '<em><b>Folder</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FOLDER_VALUE
	 * @generated
	 * @ordered
	 */
	FOLDER(21, "Folder", "Folder"), /**
	 * The '<em><b>Domain Instance</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_INSTANCE_VALUE
	 * @generated
	 * @ordered
	 */
	DOMAIN_INSTANCE(22, "DomainInstance", "DomainInstance"), /**
	 * The '<em><b>Text Chart Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEXT_CHART_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	TEXT_CHART_COMPONENT(24, "TextChartComponent", "TextChartComponent"), /**
	 * The '<em><b>State Machine Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATE_MACHINE_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	STATE_MACHINE_COMPONENT(25, "StateMachineComponent", "StateMachineComponent"), /**
	 * The '<em><b>Page Selector Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PAGE_SELECTOR_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	PAGE_SELECTOR_COMPONENT(26, "PageSelectorComponent", "PageSelectorComponent"), /**
	 * The '<em><b>Alert Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALERT_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	ALERT_COMPONENT(27, "AlertComponent", "AlertComponent"), /**
	 * The '<em><b>Context Action Rule Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTEXT_ACTION_RULE_SET_VALUE
	 * @generated
	 * @ordered
	 */
	CONTEXT_ACTION_RULE_SET(28, "ContextActionRuleSet", "ContextActionRuleSet"), /**
	 * The '<em><b>Blue Print Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BLUE_PRINT_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	BLUE_PRINT_COMPONENT(29, "BluePrintComponent", "BluePrintComponent"), /**
	 * The '<em><b>Query Manager Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_MANAGER_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY_MANAGER_COMPONENT(30, "QueryManagerComponent", "QueryManagerComponent"), /**
	 * The '<em><b>Search View Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEARCH_VIEW_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	SEARCH_VIEW_COMPONENT(31, "SearchViewComponent", "SearchViewComponent"), /**
	 * The '<em><b>Related Assets Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELATED_ASSETS_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	RELATED_ASSETS_COMPONENT(32, "RelatedAssetsComponent", "RelatedAssetsComponent"), /**
	 * The '<em><b>Drilldown Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DRILLDOWN_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	DRILLDOWN_COMPONENT(33, "DrilldownComponent", "DrilldownComponent"), /**
	 * The '<em><b>Query</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY(34, "Query", "Query"), /**
	 * The '<em><b>View</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	VIEW(35, "View", "View"), /**
	 * The '<em><b>Dashboard Page</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASHBOARD_PAGE_VALUE
	 * @generated
	 * @ordered
	 */
	DASHBOARD_PAGE(36, "DashboardPage", "DashboardPage"), /**
	 * The '<em><b>Asset Page</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSET_PAGE_VALUE
	 * @generated
	 * @ordered
	 */
	ASSET_PAGE(37, "AssetPage", "AssetPage"), /**
	 * The '<em><b>Search Page</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEARCH_PAGE_VALUE
	 * @generated
	 * @ordered
	 */
	SEARCH_PAGE(38, "SearchPage", "SearchPage"), /**
	 * The '<em><b>Page Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PAGE_SET_VALUE
	 * @generated
	 * @ordered
	 */
	PAGE_SET(39, "PageSet", "PageSet"), /**
	 * The '<em><b>Series Color</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SERIES_COLOR_VALUE
	 * @generated
	 * @ordered
	 */
	SERIES_COLOR(40, "SeriesColor", "SeriesColor"), /**
	 * The '<em><b>Text Component Color Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEXT_COMPONENT_COLOR_SET_VALUE
	 * @generated
	 * @ordered
	 */
	TEXT_COMPONENT_COLOR_SET(41, "TextComponentColorSet", "TextComponentColorSet"), /**
	 * The '<em><b>Chart Component Color Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHART_COMPONENT_COLOR_SET_VALUE
	 * @generated
	 * @ordered
	 */
	CHART_COMPONENT_COLOR_SET(42, "ChartComponentColorSet", "ChartComponentColorSet"), /**
	 * The '<em><b>Header</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HEADER_VALUE
	 * @generated
	 * @ordered
	 */
	HEADER(43, "Header", "Header"), /**
	 * The '<em><b>Login</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOGIN_VALUE
	 * @generated
	 * @ordered
	 */
	LOGIN(44, "Login", "Login"), /**
	 * The '<em><b>Skin</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKIN_VALUE
	 * @generated
	 * @ordered
	 */
	SKIN(45, "Skin", "Skin"), /**
	 * The '<em><b>Role Preference</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROLE_PREFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	ROLE_PREFERENCE(46, "RolePreference", "RolePreference"), /**
	 * The '<em><b>Chart Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHART_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	CHART_COMPONENT(47, "ChartComponent", "ChartComponent"), /**
	 * The '<em><b>Text Component</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEXT_COMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	TEXT_COMPONENT(48, "TextComponent", "TextComponent"), /**
	 * The '<em><b>Data Source</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATA_SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	DATA_SOURCE(49, "DataSource", "DataSource"), /**
	 * The '<em><b>Rule Template</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_TEMPLATE(50, "RuleTemplate", "RuleTemplate"), /**
	 * The '<em><b>Rule Template View</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_TEMPLATE_VIEW(51, "RuleTemplateView", "RuleTemplateView"), /**
	 * The '<em><b>Process</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS(52, "Process", "Process"), /**
	 * The '<em><b>Xslt Function</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	XSLT_FUNCTION(53, "XsltFunction", "XsltFunction"), /**
	 * The '<em><b>Java Source</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_SOURCE(54, "JavaSource", "JavaSource"), /**
	 * The '<em><b>Java Resource</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_RESOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_RESOURCE(55, "JavaResource", "JavaResource");

	/**
	 * The '<em><b>Unknown</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unknown</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN
	 * @model name="Unknown"
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * The '<em><b>Concept</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Concept</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT
	 * @model name="Concept"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_VALUE = 1;

	/**
	 * The '<em><b>Scorecard</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Scorecard</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCORECARD
	 * @model name="Scorecard"
	 * @generated
	 * @ordered
	 */
	public static final int SCORECARD_VALUE = 2;

	/**
	 * The '<em><b>Instance</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Instance</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INSTANCE
	 * @model name="Instance"
	 * @generated
	 * @ordered
	 */
	public static final int INSTANCE_VALUE = 3;

	/**
	 * The '<em><b>Simple Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Simple Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SIMPLE_EVENT
	 * @model name="SimpleEvent"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLE_EVENT_VALUE = 4;

	/**
	 * The '<em><b>Channel</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Channel</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANNEL
	 * @model name="Channel"
	 * @generated
	 * @ordered
	 */
	public static final int CHANNEL_VALUE = 5;

	/**
	 * The '<em><b>Destination</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Destination</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DESTINATION
	 * @model name="Destination"
	 * @generated
	 * @ordered
	 */
	public static final int DESTINATION_VALUE = 6;

	/**
	 * The '<em><b>Rule</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE
	 * @model name="Rule"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_VALUE = 7;

	/**
	 * The '<em><b>Rule Function</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Function</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_FUNCTION
	 * @model name="RuleFunction"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_FUNCTION_VALUE = 8;

	/**
	 * The '<em><b>Rule Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_SET
	 * @model name="RuleSet"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_SET_VALUE = 9;

	/**
	 * The '<em><b>State Machine</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>State Machine</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATE_MACHINE
	 * @model name="StateMachine"
	 * @generated
	 * @ordered
	 */
	public static final int STATE_MACHINE_VALUE = 10;

	/**
	 * The '<em><b>Time Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Time Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIME_EVENT
	 * @model name="TimeEvent"
	 * @generated
	 * @ordered
	 */
	public static final int TIME_EVENT_VALUE = 11;

	/**
	 * The '<em><b>Enterprise Archive</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Enterprise Archive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ENTERPRISE_ARCHIVE
	 * @model name="EnterpriseArchive"
	 * @generated
	 * @ordered
	 */
	public static final int ENTERPRISE_ARCHIVE_VALUE = 12;

	/**
	 * The '<em><b>BE Archive Resource</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BE Archive Resource</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BE_ARCHIVE_RESOURCE
	 * @model name="BEArchiveResource"
	 * @generated
	 * @ordered
	 */
	public static final int BE_ARCHIVE_RESOURCE_VALUE = 13;

	/**
	 * The '<em><b>Shared Archive</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Shared Archive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHARED_ARCHIVE
	 * @model name="SharedArchive"
	 * @generated
	 * @ordered
	 */
	public static final int SHARED_ARCHIVE_VALUE = 14;

	/**
	 * The '<em><b>Process Archive</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Process Archive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESS_ARCHIVE
	 * @model name="ProcessArchive"
	 * @generated
	 * @ordered
	 */
	public static final int PROCESS_ARCHIVE_VALUE = 15;

	/**
	 * The '<em><b>Adapter Archive</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Adapter Archive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADAPTER_ARCHIVE
	 * @model name="AdapterArchive"
	 * @generated
	 * @ordered
	 */
	public static final int ADAPTER_ARCHIVE_VALUE = 16;

	/**
	 * The '<em><b>Primitive Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Primitive Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PRIMITIVE_TYPE
	 * @model name="PrimitiveType"
	 * @generated
	 * @ordered
	 */
	public static final int PRIMITIVE_TYPE_VALUE = 16;

	/**
	 * The '<em><b>Decision Table</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Decision Table</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DECISION_TABLE
	 * @model name="DecisionTable"
	 * @generated
	 * @ordered
	 */
	public static final int DECISION_TABLE_VALUE = 17;

	/**
	 * The '<em><b>Metric</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Metric</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METRIC
	 * @model name="Metric"
	 * @generated
	 * @ordered
	 */
	public static final int METRIC_VALUE = 18;

	/**
	 * The '<em><b>Domain</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Domain</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOMAIN
	 * @model name="Domain"
	 * @generated
	 * @ordered
	 */
	public static final int DOMAIN_VALUE = 19;

	/**
	 * The '<em><b>Project</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Project</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROJECT
	 * @model name="Project"
	 * @generated
	 * @ordered
	 */
	public static final int PROJECT_VALUE = 20;

	/**
	 * The '<em><b>Folder</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Folder</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FOLDER
	 * @model name="Folder"
	 * @generated
	 * @ordered
	 */
	public static final int FOLDER_VALUE = 21;

	/**
	 * The '<em><b>Domain Instance</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Domain Instance</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_INSTANCE
	 * @model name="DomainInstance"
	 * @generated
	 * @ordered
	 */
	public static final int DOMAIN_INSTANCE_VALUE = 22;

	/**
	 * The '<em><b>Text Chart Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Text Chart Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEXT_CHART_COMPONENT
	 * @model name="TextChartComponent"
	 * @generated
	 * @ordered
	 */
	public static final int TEXT_CHART_COMPONENT_VALUE = 24;

	/**
	 * The '<em><b>State Machine Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>State Machine Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATE_MACHINE_COMPONENT
	 * @model name="StateMachineComponent"
	 * @generated
	 * @ordered
	 */
	public static final int STATE_MACHINE_COMPONENT_VALUE = 25;

	/**
	 * The '<em><b>Page Selector Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Page Selector Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PAGE_SELECTOR_COMPONENT
	 * @model name="PageSelectorComponent"
	 * @generated
	 * @ordered
	 */
	public static final int PAGE_SELECTOR_COMPONENT_VALUE = 26;

	/**
	 * The '<em><b>Alert Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Alert Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALERT_COMPONENT
	 * @model name="AlertComponent"
	 * @generated
	 * @ordered
	 */
	public static final int ALERT_COMPONENT_VALUE = 27;

	/**
	 * The '<em><b>Context Action Rule Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Context Action Rule Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONTEXT_ACTION_RULE_SET
	 * @model name="ContextActionRuleSet"
	 * @generated
	 * @ordered
	 */
	public static final int CONTEXT_ACTION_RULE_SET_VALUE = 28;

	/**
	 * The '<em><b>Blue Print Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Blue Print Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BLUE_PRINT_COMPONENT
	 * @model name="BluePrintComponent"
	 * @generated
	 * @ordered
	 */
	public static final int BLUE_PRINT_COMPONENT_VALUE = 29;

	/**
	 * The '<em><b>Query Manager Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Query Manager Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY_MANAGER_COMPONENT
	 * @model name="QueryManagerComponent"
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_MANAGER_COMPONENT_VALUE = 30;

	/**
	 * The '<em><b>Search View Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Search View Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEARCH_VIEW_COMPONENT
	 * @model name="SearchViewComponent"
	 * @generated
	 * @ordered
	 */
	public static final int SEARCH_VIEW_COMPONENT_VALUE = 31;

	/**
	 * The '<em><b>Related Assets Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Related Assets Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RELATED_ASSETS_COMPONENT
	 * @model name="RelatedAssetsComponent"
	 * @generated
	 * @ordered
	 */
	public static final int RELATED_ASSETS_COMPONENT_VALUE = 32;

	/**
	 * The '<em><b>Drilldown Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Drilldown Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DRILLDOWN_COMPONENT
	 * @model name="DrilldownComponent"
	 * @generated
	 * @ordered
	 */
	public static final int DRILLDOWN_COMPONENT_VALUE = 33;

	/**
	 * The '<em><b>Query</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Query</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY
	 * @model name="Query"
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_VALUE = 34;

	/**
	 * The '<em><b>View</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>View</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIEW
	 * @model name="View"
	 * @generated
	 * @ordered
	 */
	public static final int VIEW_VALUE = 35;

	/**
	 * The '<em><b>Dashboard Page</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dashboard Page</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASHBOARD_PAGE
	 * @model name="DashboardPage"
	 * @generated
	 * @ordered
	 */
	public static final int DASHBOARD_PAGE_VALUE = 36;

	/**
	 * The '<em><b>Asset Page</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Asset Page</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSET_PAGE
	 * @model name="AssetPage"
	 * @generated
	 * @ordered
	 */
	public static final int ASSET_PAGE_VALUE = 37;

	/**
	 * The '<em><b>Search Page</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Search Page</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEARCH_PAGE
	 * @model name="SearchPage"
	 * @generated
	 * @ordered
	 */
	public static final int SEARCH_PAGE_VALUE = 38;

	/**
	 * The '<em><b>Page Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Page Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PAGE_SET
	 * @model name="PageSet"
	 * @generated
	 * @ordered
	 */
	public static final int PAGE_SET_VALUE = 39;

	/**
	 * The '<em><b>Series Color</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Series Color</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SERIES_COLOR
	 * @model name="SeriesColor"
	 * @generated
	 * @ordered
	 */
	public static final int SERIES_COLOR_VALUE = 40;

	/**
	 * The '<em><b>Text Component Color Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Text Component Color Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEXT_COMPONENT_COLOR_SET
	 * @model name="TextComponentColorSet"
	 * @generated
	 * @ordered
	 */
	public static final int TEXT_COMPONENT_COLOR_SET_VALUE = 41;

	/**
	 * The '<em><b>Chart Component Color Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Chart Component Color Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHART_COMPONENT_COLOR_SET
	 * @model name="ChartComponentColorSet"
	 * @generated
	 * @ordered
	 */
	public static final int CHART_COMPONENT_COLOR_SET_VALUE = 42;

	/**
	 * The '<em><b>Header</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Header</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HEADER
	 * @model name="Header"
	 * @generated
	 * @ordered
	 */
	public static final int HEADER_VALUE = 43;

	/**
	 * The '<em><b>Login</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Login</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGIN
	 * @model name="Login"
	 * @generated
	 * @ordered
	 */
	public static final int LOGIN_VALUE = 44;

	/**
	 * The '<em><b>Skin</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Skin</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKIN
	 * @model name="Skin"
	 * @generated
	 * @ordered
	 */
	public static final int SKIN_VALUE = 45;

	/**
	 * The '<em><b>Role Preference</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Role Preference</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROLE_PREFERENCE
	 * @model name="RolePreference"
	 * @generated
	 * @ordered
	 */
	public static final int ROLE_PREFERENCE_VALUE = 46;

	/**
	 * The '<em><b>Chart Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Chart Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHART_COMPONENT
	 * @model name="ChartComponent"
	 * @generated
	 * @ordered
	 */
	public static final int CHART_COMPONENT_VALUE = 47;

	/**
	 * The '<em><b>Text Component</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Text Component</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEXT_COMPONENT
	 * @model name="TextComponent"
	 * @generated
	 * @ordered
	 */
	public static final int TEXT_COMPONENT_VALUE = 48;

	/**
	 * The '<em><b>Data Source</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Data Source</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATA_SOURCE
	 * @model name="DataSource"
	 * @generated
	 * @ordered
	 */
	public static final int DATA_SOURCE_VALUE = 49;

	/**
	 * The '<em><b>Rule Template</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Template</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE
	 * @model name="RuleTemplate"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_TEMPLATE_VALUE = 50;

	/**
	 * The '<em><b>Rule Template View</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Template View</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_VIEW
	 * @model name="RuleTemplateView"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_TEMPLATE_VIEW_VALUE = 51;

	/**
	 * The '<em><b>Process</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Process</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESS
	 * @model name="Process"
	 * @generated
	 * @ordered
	 */
	public static final int PROCESS_VALUE = 52;

	/**
	 * The '<em><b>Xslt Function</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Xslt Function</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION
	 * @model name="XsltFunction"
	 * @generated
	 * @ordered
	 */
	public static final int XSLT_FUNCTION_VALUE = 53;

	/**
	 * The '<em><b>Java Source</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Java Source</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE
	 * @model name="JavaSource"
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SOURCE_VALUE = 54;

	/**
	 * The '<em><b>Java Resource</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Java Resource</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_RESOURCE
	 * @model name="JavaResource"
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_RESOURCE_VALUE = 55;

	/**
	 * An array of all the '<em><b>ELEMENT TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ELEMENT_TYPES[] VALUES_ARRAY =
		new ELEMENT_TYPES[] {
			UNKNOWN,
			CONCEPT,
			SCORECARD,
			INSTANCE,
			SIMPLE_EVENT,
			CHANNEL,
			DESTINATION,
			RULE,
			RULE_FUNCTION,
			RULE_SET,
			STATE_MACHINE,
			TIME_EVENT,
			ENTERPRISE_ARCHIVE,
			BE_ARCHIVE_RESOURCE,
			SHARED_ARCHIVE,
			PROCESS_ARCHIVE,
			ADAPTER_ARCHIVE,
			PRIMITIVE_TYPE,
			DECISION_TABLE,
			METRIC,
			DOMAIN,
			PROJECT,
			FOLDER,
			DOMAIN_INSTANCE,
			TEXT_CHART_COMPONENT,
			STATE_MACHINE_COMPONENT,
			PAGE_SELECTOR_COMPONENT,
			ALERT_COMPONENT,
			CONTEXT_ACTION_RULE_SET,
			BLUE_PRINT_COMPONENT,
			QUERY_MANAGER_COMPONENT,
			SEARCH_VIEW_COMPONENT,
			RELATED_ASSETS_COMPONENT,
			DRILLDOWN_COMPONENT,
			QUERY,
			VIEW,
			DASHBOARD_PAGE,
			ASSET_PAGE,
			SEARCH_PAGE,
			PAGE_SET,
			SERIES_COLOR,
			TEXT_COMPONENT_COLOR_SET,
			CHART_COMPONENT_COLOR_SET,
			HEADER,
			LOGIN,
			SKIN,
			ROLE_PREFERENCE,
			CHART_COMPONENT,
			TEXT_COMPONENT,
			DATA_SOURCE,
			RULE_TEMPLATE,
			RULE_TEMPLATE_VIEW,
			PROCESS,
			XSLT_FUNCTION,
			JAVA_SOURCE,
			JAVA_RESOURCE,
		};

	/**
	 * A public read-only list of all the '<em><b>ELEMENT TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ELEMENT_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>ELEMENT TYPES</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ELEMENT_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ELEMENT_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ELEMENT TYPES</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ELEMENT_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ELEMENT_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ELEMENT TYPES</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ELEMENT_TYPES get(int value) {
		switch (value) {
			case UNKNOWN_VALUE: return UNKNOWN;
			case CONCEPT_VALUE: return CONCEPT;
			case SCORECARD_VALUE: return SCORECARD;
			case INSTANCE_VALUE: return INSTANCE;
			case SIMPLE_EVENT_VALUE: return SIMPLE_EVENT;
			case CHANNEL_VALUE: return CHANNEL;
			case DESTINATION_VALUE: return DESTINATION;
			case RULE_VALUE: return RULE;
			case RULE_FUNCTION_VALUE: return RULE_FUNCTION;
			case RULE_SET_VALUE: return RULE_SET;
			case STATE_MACHINE_VALUE: return STATE_MACHINE;
			case TIME_EVENT_VALUE: return TIME_EVENT;
			case ENTERPRISE_ARCHIVE_VALUE: return ENTERPRISE_ARCHIVE;
			case BE_ARCHIVE_RESOURCE_VALUE: return BE_ARCHIVE_RESOURCE;
			case SHARED_ARCHIVE_VALUE: return SHARED_ARCHIVE;
			case PROCESS_ARCHIVE_VALUE: return PROCESS_ARCHIVE;
			case ADAPTER_ARCHIVE_VALUE: return ADAPTER_ARCHIVE;
			case DECISION_TABLE_VALUE: return DECISION_TABLE;
			case METRIC_VALUE: return METRIC;
			case DOMAIN_VALUE: return DOMAIN;
			case PROJECT_VALUE: return PROJECT;
			case FOLDER_VALUE: return FOLDER;
			case DOMAIN_INSTANCE_VALUE: return DOMAIN_INSTANCE;
			case TEXT_CHART_COMPONENT_VALUE: return TEXT_CHART_COMPONENT;
			case STATE_MACHINE_COMPONENT_VALUE: return STATE_MACHINE_COMPONENT;
			case PAGE_SELECTOR_COMPONENT_VALUE: return PAGE_SELECTOR_COMPONENT;
			case ALERT_COMPONENT_VALUE: return ALERT_COMPONENT;
			case CONTEXT_ACTION_RULE_SET_VALUE: return CONTEXT_ACTION_RULE_SET;
			case BLUE_PRINT_COMPONENT_VALUE: return BLUE_PRINT_COMPONENT;
			case QUERY_MANAGER_COMPONENT_VALUE: return QUERY_MANAGER_COMPONENT;
			case SEARCH_VIEW_COMPONENT_VALUE: return SEARCH_VIEW_COMPONENT;
			case RELATED_ASSETS_COMPONENT_VALUE: return RELATED_ASSETS_COMPONENT;
			case DRILLDOWN_COMPONENT_VALUE: return DRILLDOWN_COMPONENT;
			case QUERY_VALUE: return QUERY;
			case VIEW_VALUE: return VIEW;
			case DASHBOARD_PAGE_VALUE: return DASHBOARD_PAGE;
			case ASSET_PAGE_VALUE: return ASSET_PAGE;
			case SEARCH_PAGE_VALUE: return SEARCH_PAGE;
			case PAGE_SET_VALUE: return PAGE_SET;
			case SERIES_COLOR_VALUE: return SERIES_COLOR;
			case TEXT_COMPONENT_COLOR_SET_VALUE: return TEXT_COMPONENT_COLOR_SET;
			case CHART_COMPONENT_COLOR_SET_VALUE: return CHART_COMPONENT_COLOR_SET;
			case HEADER_VALUE: return HEADER;
			case LOGIN_VALUE: return LOGIN;
			case SKIN_VALUE: return SKIN;
			case ROLE_PREFERENCE_VALUE: return ROLE_PREFERENCE;
			case CHART_COMPONENT_VALUE: return CHART_COMPONENT;
			case TEXT_COMPONENT_VALUE: return TEXT_COMPONENT;
			case DATA_SOURCE_VALUE: return DATA_SOURCE;
			case RULE_TEMPLATE_VALUE: return RULE_TEMPLATE;
			case RULE_TEMPLATE_VIEW_VALUE: return RULE_TEMPLATE_VIEW;
			case PROCESS_VALUE: return PROCESS;
			case XSLT_FUNCTION_VALUE: return XSLT_FUNCTION;
			case JAVA_SOURCE_VALUE: return JAVA_SOURCE;
			case JAVA_RESOURCE_VALUE: return JAVA_RESOURCE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ELEMENT_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //ELEMENT_TYPES
