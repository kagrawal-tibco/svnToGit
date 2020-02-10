/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>File Extension Types</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getFileExtensionTypes()
 * @model extendedMetaData="name='FileExtensionTypes'"
 * @generated
 */
public enum FileExtensionTypes implements Enumerator {
	/**
	 * The '<em><b>RULE EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_EXTENSION(0, "RULE_EXTENSION", "rule"),

	/**
	 * The '<em><b>RULEFUNCTION EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	RULEFUNCTION_EXTENSION(1, "RULEFUNCTION_EXTENSION", "rulefunction"),

	/**
	 * The '<em><b>XSLT FUNCTION EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	XSLT_FUNCTION_EXTENSION(2, "XSLT_FUNCTION_EXTENSION", "xsltfunction"), /**
	 * The '<em><b>RULE FN IMPL EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_FN_IMPL_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_FN_IMPL_EXTENSION(3, "RULE_FN_IMPL_EXTENSION", "rulefunctionimpl"),

	/**
	 * The '<em><b>RULE TEMPLATE EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_TEMPLATE_EXTENSION(4, "RULE_TEMPLATE_EXTENSION", "ruletemplate"),

	/**
	 * The '<em><b>RULE TEMPLATE VIEW EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_VIEW_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_TEMPLATE_VIEW_EXTENSION(5, "RULE_TEMPLATE_VIEW_EXTENSION", "ruletemplateview"),

	/**
	 * The '<em><b>EAR EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EAR_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	EAR_EXTENSION(6, "EAR_EXTENSION", "archive"),

	/**
	 * The '<em><b>TIME EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIME_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_EXTENSION(7, "TIME_EXTENSION", "time"),

	/**
	 * The '<em><b>STATEMACHINE EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	STATEMACHINE_EXTENSION(8, "STATEMACHINE_EXTENSION", "statemachine"),

	/**
	 * The '<em><b>JAVA SOURCE EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_SOURCE_EXTENSION(9, "JAVA_SOURCE_EXTENSION", "java"), /**
	 * The '<em><b>CONCEPT EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT_EXTENSION(10, "CONCEPT_EXTENSION", "concept"),

	/**
	 * The '<em><b>EVENT EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVENT_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	EVENT_EXTENSION(11, "EVENT_EXTENSION", "event"),

	/**
	 * The '<em><b>SCORECARD EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCORECARD_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	SCORECARD_EXTENSION(12, "SCORECARD_EXTENSION", "scorecard"),

	/**
	 * The '<em><b>AESCHEMA EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AESCHEMA_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	AESCHEMA_EXTENSION(13, "AESCHEMA_EXTENSION", "aeschema"),

	/**
	 * The '<em><b>XSD EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSD_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	XSD_EXTENSION(14, "XSD_EXTENSION", "xsd"),

	/**
	 * The '<em><b>DTD EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DTD_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	DTD_EXTENSION(15, "DTD_EXTENSION", "dtd"),

	/**
	 * The '<em><b>WSDL EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WSDL_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	WSDL_EXTENSION(16, "WSDL_EXTENSION", "wsdl"),

	/**
	 * The '<em><b>DOMAIN EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	DOMAIN_EXTENSION(17, "DOMAIN_EXTENSION", "domain"),

	/**
	 * The '<em><b>CHANNEL EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANNEL_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	CHANNEL_EXTENSION(18, "CHANNEL_EXTENSION", "channel"),

	/**
	 * The '<em><b>GLOBAL VAR EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GLOBAL_VAR_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	GLOBAL_VAR_EXTENSION(19, "GLOBAL_VAR_EXTENSION", "substvar"),

	/**
	 * The '<em><b>QUERY EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY_EXTENSION(20, "QUERY_EXTENSION", "query"),

	/**
	 * The '<em><b>BE QUERY EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BE_QUERY_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	BE_QUERY_EXTENSION(21, "BE_QUERY_EXTENSION", "bequery"),

	/**
	 * The '<em><b>METRIC EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METRIC_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	METRIC_EXTENSION(22, "METRIC_EXTENSION", "metric"),

	/**
	 * The '<em><b>PROCESS EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS_EXTENSION(23, "PROCESS_EXTENSION", "beprocess"),

	/**
	 * The '<em><b>SITE TOPOLOGY EXTENSION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SITE_TOPOLOGY_EXTENSION_VALUE
	 * @generated
	 * @ordered
	 */
	SITE_TOPOLOGY_EXTENSION(24, "SITE_TOPOLOGY_EXTENSION", "st"), /**
	 * The '<em><b>CHART</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHART_VALUE
	 * @generated
	 * @ordered
	 */
	CHART(25, "CHART", "chart"), /**
	 * The '<em><b>SMCOMPONENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMCOMPONENT_VALUE
	 * @generated
	 * @ordered
	 */
	SMCOMPONENT(26, "SMCOMPONENT", "smcomponent"), /**
	 * The '<em><b>PAGE SELECTOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PAGE_SELECTOR_VALUE
	 * @generated
	 * @ordered
	 */
	PAGE_SELECTOR(27, "PAGE_SELECTOR", "pageselector"), /**
	 * The '<em><b>ALERT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALERT_VALUE
	 * @generated
	 * @ordered
	 */
	ALERT(28, "ALERT", "alert"), /**
	 * The '<em><b>CONTEXT ACTION RULESET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTEXT_ACTION_RULESET_VALUE
	 * @generated
	 * @ordered
	 */
	CONTEXT_ACTION_RULESET(29, "CONTEXT_ACTION_RULESET", "contextactionruleset"), /**
	 * The '<em><b>BLUEPRINT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BLUEPRINT_VALUE
	 * @generated
	 * @ordered
	 */
	BLUEPRINT(30, "BLUEPRINT", "blueprint"), /**
	 * The '<em><b>QUERY MANAGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_MANAGER_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY_MANAGER(31, "QUERY_MANAGER", "querymanager"), /**
	 * The '<em><b>SEARCH VIEW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEARCH_VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	SEARCH_VIEW(32, "SEARCH_VIEW", "searchview"), /**
	 * The '<em><b>RELATED ASSETS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELATED_ASSETS_VALUE
	 * @generated
	 * @ordered
	 */
	RELATED_ASSETS(33, "RELATED_ASSETS", "relatedassets"), /**
	 * The '<em><b>DRILLDOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DRILLDOWN_VALUE
	 * @generated
	 * @ordered
	 */
	DRILLDOWN(34, "DRILLDOWN", "drilldown"), /**
	 * The '<em><b>VIEW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	VIEW(36, "VIEW", "view"), /**
	 * The '<em><b>DASHBOARDPAGE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASHBOARDPAGE_VALUE
	 * @generated
	 * @ordered
	 */
	DASHBOARDPAGE(37, "DASHBOARDPAGE", "dashboardpage"), /**
	 * The '<em><b>ASSETPAGE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSETPAGE_VALUE
	 * @generated
	 * @ordered
	 */
	ASSETPAGE(38, "ASSETPAGE", "assetpage"), /**
	 * The '<em><b>SEARCHPAGE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SEARCHPAGE_VALUE
	 * @generated
	 * @ordered
	 */
	SEARCHPAGE(39, "SEARCHPAGE", "searchpage"), /**
	 * The '<em><b>PAGESET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PAGESET_VALUE
	 * @generated
	 * @ordered
	 */
	PAGESET(40, "PAGESET", "pageset"), /**
	 * The '<em><b>SERIES COLOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SERIES_COLOR_VALUE
	 * @generated
	 * @ordered
	 */
	SERIES_COLOR(41, "SERIES_COLOR", "seriescolor"), /**
	 * The '<em><b>TEXT COLORSET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TEXT_COLORSET_VALUE
	 * @generated
	 * @ordered
	 */
	TEXT_COLORSET(42, "TEXT_COLORSET", "textcolorset"), /**
	 * The '<em><b>CHART COLORSET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHART_COLORSET_VALUE
	 * @generated
	 * @ordered
	 */
	CHART_COLORSET(43, "CHART_COLORSET", "chartcolorset"), /**
	 * The '<em><b>HEADER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HEADER_VALUE
	 * @generated
	 * @ordered
	 */
	HEADER(44, "HEADER", "header"), /**
	 * The '<em><b>LOGIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOGIN_VALUE
	 * @generated
	 * @ordered
	 */
	LOGIN(45, "LOGIN", "login"), /**
	 * The '<em><b>SKIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKIN_VALUE
	 * @generated
	 * @ordered
	 */
	SKIN(46, "SKIN", "skin"), /**
	 * The '<em><b>ROLEPREFERENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROLEPREFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	ROLEPREFERENCE(47, "ROLEPREFERENCE", "rolepreference"), /**
	 * The '<em><b>DATASOURCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATASOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	DATASOURCE(48, "DATASOURCE", "datasource"), /**
	 * The '<em><b>SYSTEM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SYSTEM_VALUE
	 * @generated
	 * @ordered
	 */
	SYSTEM(49, "SYSTEM", "system");

	/**
	 * The '<em><b>RULE EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_EXTENSION
	 * @model literal="rule"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_EXTENSION_VALUE = 0;

	/**
	 * The '<em><b>RULEFUNCTION EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULEFUNCTION EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION_EXTENSION
	 * @model literal="rulefunction"
	 * @generated
	 * @ordered
	 */
	public static final int RULEFUNCTION_EXTENSION_VALUE = 1;

	/**
	 * The '<em><b>XSLT FUNCTION EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XSLT FUNCTION EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION_EXTENSION
	 * @model literal="xsltfunction"
	 * @generated
	 * @ordered
	 */
	public static final int XSLT_FUNCTION_EXTENSION_VALUE = 2;

	/**
	 * The '<em><b>RULE FN IMPL EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE FN IMPL EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_FN_IMPL_EXTENSION
	 * @model literal="rulefunctionimpl"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_FN_IMPL_EXTENSION_VALUE = 3;

	/**
	 * The '<em><b>RULE TEMPLATE EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE TEMPLATE EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_EXTENSION
	 * @model literal="ruletemplate"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_TEMPLATE_EXTENSION_VALUE = 4;

	/**
	 * The '<em><b>RULE TEMPLATE VIEW EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE TEMPLATE VIEW EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_TEMPLATE_VIEW_EXTENSION
	 * @model literal="ruletemplateview"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_TEMPLATE_VIEW_EXTENSION_VALUE = 5;

	/**
	 * The '<em><b>EAR EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EAR EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EAR_EXTENSION
	 * @model literal="archive"
	 * @generated
	 * @ordered
	 */
	public static final int EAR_EXTENSION_VALUE = 6;

	/**
	 * The '<em><b>TIME EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIME EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIME_EXTENSION
	 * @model literal="time"
	 * @generated
	 * @ordered
	 */
	public static final int TIME_EXTENSION_VALUE = 7;

	/**
	 * The '<em><b>STATEMACHINE EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STATEMACHINE EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE_EXTENSION
	 * @model literal="statemachine"
	 * @generated
	 * @ordered
	 */
	public static final int STATEMACHINE_EXTENSION_VALUE = 8;

	/**
	 * The '<em><b>JAVA SOURCE EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JAVA SOURCE EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_EXTENSION
	 * @model literal="java"
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SOURCE_EXTENSION_VALUE = 9;

	/**
	 * The '<em><b>CONCEPT EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONCEPT EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_EXTENSION
	 * @model literal="concept"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_EXTENSION_VALUE = 10;

	/**
	 * The '<em><b>EVENT EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EVENT EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVENT_EXTENSION
	 * @model literal="event"
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_EXTENSION_VALUE = 11;

	/**
	 * The '<em><b>SCORECARD EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCORECARD EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCORECARD_EXTENSION
	 * @model literal="scorecard"
	 * @generated
	 * @ordered
	 */
	public static final int SCORECARD_EXTENSION_VALUE = 12;

	/**
	 * The '<em><b>AESCHEMA EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>AESCHEMA EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AESCHEMA_EXTENSION
	 * @model literal="aeschema"
	 * @generated
	 * @ordered
	 */
	public static final int AESCHEMA_EXTENSION_VALUE = 13;

	/**
	 * The '<em><b>XSD EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XSD EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSD_EXTENSION
	 * @model literal="xsd"
	 * @generated
	 * @ordered
	 */
	public static final int XSD_EXTENSION_VALUE = 14;

	/**
	 * The '<em><b>DTD EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DTD EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DTD_EXTENSION
	 * @model literal="dtd"
	 * @generated
	 * @ordered
	 */
	public static final int DTD_EXTENSION_VALUE = 15;

	/**
	 * The '<em><b>WSDL EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WSDL EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WSDL_EXTENSION
	 * @model literal="wsdl"
	 * @generated
	 * @ordered
	 */
	public static final int WSDL_EXTENSION_VALUE = 16;

	/**
	 * The '<em><b>DOMAIN EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DOMAIN EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_EXTENSION
	 * @model literal="domain"
	 * @generated
	 * @ordered
	 */
	public static final int DOMAIN_EXTENSION_VALUE = 17;

	/**
	 * The '<em><b>CHANNEL EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANNEL EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANNEL_EXTENSION
	 * @model literal="channel"
	 * @generated
	 * @ordered
	 */
	public static final int CHANNEL_EXTENSION_VALUE = 18;

	/**
	 * The '<em><b>GLOBAL VAR EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GLOBAL VAR EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GLOBAL_VAR_EXTENSION
	 * @model literal="substvar"
	 * @generated
	 * @ordered
	 */
	public static final int GLOBAL_VAR_EXTENSION_VALUE = 19;

	/**
	 * The '<em><b>QUERY EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>QUERY EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY_EXTENSION
	 * @model literal="query"
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_EXTENSION_VALUE = 20;

	/**
	 * The '<em><b>BE QUERY EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BE QUERY EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BE_QUERY_EXTENSION
	 * @model literal="bequery"
	 * @generated
	 * @ordered
	 */
	public static final int BE_QUERY_EXTENSION_VALUE = 21;

	/**
	 * The '<em><b>METRIC EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>METRIC EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METRIC_EXTENSION
	 * @model literal="metric"
	 * @generated
	 * @ordered
	 */
	public static final int METRIC_EXTENSION_VALUE = 22;

	/**
	 * The '<em><b>PROCESS EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROCESS EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESS_EXTENSION
	 * @model literal="beprocess"
	 * @generated
	 * @ordered
	 */
	public static final int PROCESS_EXTENSION_VALUE = 23;

	/**
	 * The '<em><b>SITE TOPOLOGY EXTENSION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SITE TOPOLOGY EXTENSION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SITE_TOPOLOGY_EXTENSION
	 * @model literal="st"
	 * @generated
	 * @ordered
	 */
	public static final int SITE_TOPOLOGY_EXTENSION_VALUE = 24;

	/**
	 * The '<em><b>CHART</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHART</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHART
	 * @model literal="chart"
	 * @generated
	 * @ordered
	 */
	public static final int CHART_VALUE = 25;

	/**
	 * The '<em><b>SMCOMPONENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SMCOMPONENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SMCOMPONENT
	 * @model literal="smcomponent"
	 * @generated
	 * @ordered
	 */
	public static final int SMCOMPONENT_VALUE = 26;

	/**
	 * The '<em><b>PAGE SELECTOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PAGE SELECTOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PAGE_SELECTOR
	 * @model literal="pageselector"
	 * @generated
	 * @ordered
	 */
	public static final int PAGE_SELECTOR_VALUE = 27;

	/**
	 * The '<em><b>ALERT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ALERT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALERT
	 * @model literal="alert"
	 * @generated
	 * @ordered
	 */
	public static final int ALERT_VALUE = 28;

	/**
	 * The '<em><b>CONTEXT ACTION RULESET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONTEXT ACTION RULESET</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONTEXT_ACTION_RULESET
	 * @model literal="contextactionruleset"
	 * @generated
	 * @ordered
	 */
	public static final int CONTEXT_ACTION_RULESET_VALUE = 29;

	/**
	 * The '<em><b>BLUEPRINT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BLUEPRINT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BLUEPRINT
	 * @model literal="blueprint"
	 * @generated
	 * @ordered
	 */
	public static final int BLUEPRINT_VALUE = 30;

	/**
	 * The '<em><b>QUERY MANAGER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>QUERY MANAGER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY_MANAGER
	 * @model literal="querymanager"
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_MANAGER_VALUE = 31;

	/**
	 * The '<em><b>SEARCH VIEW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SEARCH VIEW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEARCH_VIEW
	 * @model literal="searchview"
	 * @generated
	 * @ordered
	 */
	public static final int SEARCH_VIEW_VALUE = 32;

	/**
	 * The '<em><b>RELATED ASSETS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RELATED ASSETS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RELATED_ASSETS
	 * @model literal="relatedassets"
	 * @generated
	 * @ordered
	 */
	public static final int RELATED_ASSETS_VALUE = 33;

	/**
	 * The '<em><b>DRILLDOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DRILLDOWN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DRILLDOWN
	 * @model literal="drilldown"
	 * @generated
	 * @ordered
	 */
	public static final int DRILLDOWN_VALUE = 34;

	/**
	 * The '<em><b>VIEW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VIEW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIEW
	 * @model literal="view"
	 * @generated
	 * @ordered
	 */
	public static final int VIEW_VALUE = 36;

	/**
	 * The '<em><b>DASHBOARDPAGE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DASHBOARDPAGE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASHBOARDPAGE
	 * @model literal="dashboardpage"
	 * @generated
	 * @ordered
	 */
	public static final int DASHBOARDPAGE_VALUE = 37;

	/**
	 * The '<em><b>ASSETPAGE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ASSETPAGE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSETPAGE
	 * @model literal="assetpage"
	 * @generated
	 * @ordered
	 */
	public static final int ASSETPAGE_VALUE = 38;

	/**
	 * The '<em><b>SEARCHPAGE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SEARCHPAGE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SEARCHPAGE
	 * @model literal="searchpage"
	 * @generated
	 * @ordered
	 */
	public static final int SEARCHPAGE_VALUE = 39;

	/**
	 * The '<em><b>PAGESET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PAGESET</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PAGESET
	 * @model literal="pageset"
	 * @generated
	 * @ordered
	 */
	public static final int PAGESET_VALUE = 40;

	/**
	 * The '<em><b>SERIES COLOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SERIES COLOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SERIES_COLOR
	 * @model literal="seriescolor"
	 * @generated
	 * @ordered
	 */
	public static final int SERIES_COLOR_VALUE = 41;

	/**
	 * The '<em><b>TEXT COLORSET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TEXT COLORSET</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TEXT_COLORSET
	 * @model literal="textcolorset"
	 * @generated
	 * @ordered
	 */
	public static final int TEXT_COLORSET_VALUE = 42;

	/**
	 * The '<em><b>CHART COLORSET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHART COLORSET</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHART_COLORSET
	 * @model literal="chartcolorset"
	 * @generated
	 * @ordered
	 */
	public static final int CHART_COLORSET_VALUE = 43;

	/**
	 * The '<em><b>HEADER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HEADER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HEADER
	 * @model literal="header"
	 * @generated
	 * @ordered
	 */
	public static final int HEADER_VALUE = 44;

	/**
	 * The '<em><b>LOGIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGIN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGIN
	 * @model literal="login"
	 * @generated
	 * @ordered
	 */
	public static final int LOGIN_VALUE = 45;

	/**
	 * The '<em><b>SKIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SKIN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKIN
	 * @model literal="skin"
	 * @generated
	 * @ordered
	 */
	public static final int SKIN_VALUE = 46;

	/**
	 * The '<em><b>ROLEPREFERENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ROLEPREFERENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROLEPREFERENCE
	 * @model literal="rolepreference"
	 * @generated
	 * @ordered
	 */
	public static final int ROLEPREFERENCE_VALUE = 47;

	/**
	 * The '<em><b>DATASOURCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DATASOURCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATASOURCE
	 * @model literal="datasource"
	 * @generated
	 * @ordered
	 */
	public static final int DATASOURCE_VALUE = 48;

	/**
	 * The '<em><b>SYSTEM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SYSTEM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SYSTEM
	 * @model literal="system"
	 * @generated
	 * @ordered
	 */
	public static final int SYSTEM_VALUE = 49;

	/**
	 * An array of all the '<em><b>File Extension Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FileExtensionTypes[] VALUES_ARRAY =
		new FileExtensionTypes[] {
			RULE_EXTENSION,
			RULEFUNCTION_EXTENSION,
			XSLT_FUNCTION_EXTENSION,
			RULE_FN_IMPL_EXTENSION,
			RULE_TEMPLATE_EXTENSION,
			RULE_TEMPLATE_VIEW_EXTENSION,
			EAR_EXTENSION,
			TIME_EXTENSION,
			STATEMACHINE_EXTENSION,
			JAVA_SOURCE_EXTENSION,
			CONCEPT_EXTENSION,
			EVENT_EXTENSION,
			SCORECARD_EXTENSION,
			AESCHEMA_EXTENSION,
			XSD_EXTENSION,
			DTD_EXTENSION,
			WSDL_EXTENSION,
			DOMAIN_EXTENSION,
			CHANNEL_EXTENSION,
			GLOBAL_VAR_EXTENSION,
			QUERY_EXTENSION,
			BE_QUERY_EXTENSION,
			METRIC_EXTENSION,
			PROCESS_EXTENSION,
			SITE_TOPOLOGY_EXTENSION,
			CHART,
			SMCOMPONENT,
			PAGE_SELECTOR,
			ALERT,
			CONTEXT_ACTION_RULESET,
			BLUEPRINT,
			QUERY_MANAGER,
			SEARCH_VIEW,
			RELATED_ASSETS,
			DRILLDOWN,
			VIEW,
			DASHBOARDPAGE,
			ASSETPAGE,
			SEARCHPAGE,
			PAGESET,
			SERIES_COLOR,
			TEXT_COLORSET,
			CHART_COLORSET,
			HEADER,
			LOGIN,
			SKIN,
			ROLEPREFERENCE,
			DATASOURCE,
			SYSTEM,
		};

	/**
	 * A public read-only list of all the '<em><b>File Extension Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FileExtensionTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>File Extension Types</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileExtensionTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FileExtensionTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>File Extension Types</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileExtensionTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FileExtensionTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>File Extension Types</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileExtensionTypes get(int value) {
		switch (value) {
			case RULE_EXTENSION_VALUE: return RULE_EXTENSION;
			case RULEFUNCTION_EXTENSION_VALUE: return RULEFUNCTION_EXTENSION;
			case XSLT_FUNCTION_EXTENSION_VALUE: return XSLT_FUNCTION_EXTENSION;
			case RULE_FN_IMPL_EXTENSION_VALUE: return RULE_FN_IMPL_EXTENSION;
			case RULE_TEMPLATE_EXTENSION_VALUE: return RULE_TEMPLATE_EXTENSION;
			case RULE_TEMPLATE_VIEW_EXTENSION_VALUE: return RULE_TEMPLATE_VIEW_EXTENSION;
			case EAR_EXTENSION_VALUE: return EAR_EXTENSION;
			case TIME_EXTENSION_VALUE: return TIME_EXTENSION;
			case STATEMACHINE_EXTENSION_VALUE: return STATEMACHINE_EXTENSION;
			case JAVA_SOURCE_EXTENSION_VALUE: return JAVA_SOURCE_EXTENSION;
			case CONCEPT_EXTENSION_VALUE: return CONCEPT_EXTENSION;
			case EVENT_EXTENSION_VALUE: return EVENT_EXTENSION;
			case SCORECARD_EXTENSION_VALUE: return SCORECARD_EXTENSION;
			case AESCHEMA_EXTENSION_VALUE: return AESCHEMA_EXTENSION;
			case XSD_EXTENSION_VALUE: return XSD_EXTENSION;
			case DTD_EXTENSION_VALUE: return DTD_EXTENSION;
			case WSDL_EXTENSION_VALUE: return WSDL_EXTENSION;
			case DOMAIN_EXTENSION_VALUE: return DOMAIN_EXTENSION;
			case CHANNEL_EXTENSION_VALUE: return CHANNEL_EXTENSION;
			case GLOBAL_VAR_EXTENSION_VALUE: return GLOBAL_VAR_EXTENSION;
			case QUERY_EXTENSION_VALUE: return QUERY_EXTENSION;
			case BE_QUERY_EXTENSION_VALUE: return BE_QUERY_EXTENSION;
			case METRIC_EXTENSION_VALUE: return METRIC_EXTENSION;
			case PROCESS_EXTENSION_VALUE: return PROCESS_EXTENSION;
			case SITE_TOPOLOGY_EXTENSION_VALUE: return SITE_TOPOLOGY_EXTENSION;
			case CHART_VALUE: return CHART;
			case SMCOMPONENT_VALUE: return SMCOMPONENT;
			case PAGE_SELECTOR_VALUE: return PAGE_SELECTOR;
			case ALERT_VALUE: return ALERT;
			case CONTEXT_ACTION_RULESET_VALUE: return CONTEXT_ACTION_RULESET;
			case BLUEPRINT_VALUE: return BLUEPRINT;
			case QUERY_MANAGER_VALUE: return QUERY_MANAGER;
			case SEARCH_VIEW_VALUE: return SEARCH_VIEW;
			case RELATED_ASSETS_VALUE: return RELATED_ASSETS;
			case DRILLDOWN_VALUE: return DRILLDOWN;
			case VIEW_VALUE: return VIEW;
			case DASHBOARDPAGE_VALUE: return DASHBOARDPAGE;
			case ASSETPAGE_VALUE: return ASSETPAGE;
			case SEARCHPAGE_VALUE: return SEARCHPAGE;
			case PAGESET_VALUE: return PAGESET;
			case SERIES_COLOR_VALUE: return SERIES_COLOR;
			case TEXT_COLORSET_VALUE: return TEXT_COLORSET;
			case CHART_COLORSET_VALUE: return CHART_COLORSET;
			case HEADER_VALUE: return HEADER;
			case LOGIN_VALUE: return LOGIN;
			case SKIN_VALUE: return SKIN;
			case ROLEPREFERENCE_VALUE: return ROLEPREFERENCE;
			case DATASOURCE_VALUE: return DATASOURCE;
			case SYSTEM_VALUE: return SYSTEM;
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
	private FileExtensionTypes(int value, String name, String literal) {
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
	
} //FileExtensionTypes
