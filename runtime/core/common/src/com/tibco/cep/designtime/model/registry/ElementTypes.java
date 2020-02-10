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
 * A representation of the literals of the enumeration '<em><b>Element Types</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getElementTypes()
 * @model extendedMetaData="name='ElementTypes'"
 * @generated
 */
public enum ElementTypes implements Enumerator {
	/**
	 * The '<em><b>UNKNOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN_VALUE
	 * @generated
	 * @ordered
	 */
	UNKNOWN(0, "UNKNOWN", "UNKNOWN"),

	/**
	 * The '<em><b>CONCEPT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT(1, "CONCEPT", "CONCEPT"),

	/**
	 * The '<em><b>SCORECARD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCORECARD_VALUE
	 * @generated
	 * @ordered
	 */
	SCORECARD(2, "SCORECARD", "SCORECARD"),

	/**
	 * The '<em><b>INSTANCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INSTANCE_VALUE
	 * @generated
	 * @ordered
	 */
	INSTANCE(3, "INSTANCE", "INSTANCE"),

	/**
	 * The '<em><b>SIMPLEEVENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIMPLEEVENT_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLEEVENT(4, "SIMPLEEVENT", "SIMPLE_EVENT"),

	/**
	 * The '<em><b>METRIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METRIC_VALUE
	 * @generated
	 * @ordered
	 */
	METRIC(5, "METRIC", "METRIC"),

	/**
	 * The '<em><b>CHANNEL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANNEL_VALUE
	 * @generated
	 * @ordered
	 */
	CHANNEL(6, "CHANNEL", "CHANNEL"),

	/**
	 * The '<em><b>DESTINATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESTINATION_VALUE
	 * @generated
	 * @ordered
	 */
	DESTINATION(7, "DESTINATION", "DESTINATION"),

	/**
	 * The '<em><b>RULE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_VALUE
	 * @generated
	 * @ordered
	 */
	RULE(8, "RULE", "RULE"),

	/**
	 * The '<em><b>RULEFUNCTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	RULEFUNCTION(9, "RULEFUNCTION", "RULEFUNCTION"),

	/**
	 * The '<em><b>XSLT FUNCTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	XSLT_FUNCTION(10, "XSLT_FUNCTION", "XSLT_FUNCTION"), /**
	 * The '<em><b>RULESET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULESET_VALUE
	 * @generated
	 * @ordered
	 */
	RULESET(11, "RULESET", "RULESET"),

	/**
	 * The '<em><b>STATEMACHINE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE_VALUE
	 * @generated
	 * @ordered
	 */
	STATEMACHINE(12, "STATEMACHINE", "STATE_MACHINE"),

	/**
	 * The '<em><b>TIMEEVENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIMEEVENT_VALUE
	 * @generated
	 * @ordered
	 */
	TIMEEVENT(13, "TIMEEVENT", "TIME_EVENT"),

	/**
	 * The '<em><b>ENTERPRISEARCHIVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ENTERPRISEARCHIVE_VALUE
	 * @generated
	 * @ordered
	 */
	ENTERPRISEARCHIVE(14, "ENTERPRISEARCHIVE", "ENTERPRISEARCHIVE"),

	/**
	 * The '<em><b>BEARCHIVERESOURCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BEARCHIVERESOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	BEARCHIVERESOURCE(15, "BEARCHIVERESOURCE", "BEARCHIVERESOURCE"),

	/**
	 * The '<em><b>DOMAIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOMAIN_VALUE
	 * @generated
	 * @ordered
	 */
	DOMAIN(16, "DOMAIN", "DOMAIN"),

	/**
	 * The '<em><b>DECISIONTABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DECISIONTABLE_VALUE
	 * @generated
	 * @ordered
	 */
	DECISIONTABLE(17, "DECISIONTABLE", "DECISION_TABLE"),

	/**
	 * The '<em><b>FOLDER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FOLDER_VALUE
	 * @generated
	 * @ordered
	 */
	FOLDER(18, "FOLDER", "FOLDER"),

	/**
	 * The '<em><b>RULETEMPLATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATE_VALUE
	 * @generated
	 * @ordered
	 */
	RULETEMPLATE(19, "RULETEMPLATE", "RULE_TEMPLATE"),

	/**
	 * The '<em><b>QUERY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY(20, "QUERY", "QUERY"),

	/**
	 * The '<em><b>VIEW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIEW_VALUE
	 * @generated
	 * @ordered
	 */
	VIEW(21, "VIEW", "VIEW"),

	/**
	 * The '<em><b>PROCESS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS(22, "PROCESS", "PROCESS"), /**
	 * The '<em><b>JAVA SOURCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_SOURCE(23, "JAVA_SOURCE", "JAVA_SOURCE");

	/**
	 * The '<em><b>UNKNOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNKNOWN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNKNOWN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNKNOWN_VALUE = 0;

	/**
	 * The '<em><b>CONCEPT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CONCEPT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_VALUE = 1;

	/**
	 * The '<em><b>SCORECARD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCORECARD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCORECARD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCORECARD_VALUE = 2;

	/**
	 * The '<em><b>INSTANCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INSTANCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INSTANCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INSTANCE_VALUE = 3;

	/**
	 * The '<em><b>SIMPLEEVENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SIMPLEEVENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SIMPLEEVENT
	 * @model literal="SIMPLE_EVENT"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLEEVENT_VALUE = 4;

	/**
	 * The '<em><b>METRIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>METRIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METRIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METRIC_VALUE = 5;

	/**
	 * The '<em><b>CHANNEL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHANNEL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANNEL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHANNEL_VALUE = 6;

	/**
	 * The '<em><b>DESTINATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DESTINATION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DESTINATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DESTINATION_VALUE = 7;

	/**
	 * The '<em><b>RULE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RULE_VALUE = 8;

	/**
	 * The '<em><b>RULEFUNCTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULEFUNCTION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULEFUNCTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RULEFUNCTION_VALUE = 9;

	/**
	 * The '<em><b>XSLT FUNCTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XSLT FUNCTION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XSLT_FUNCTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int XSLT_FUNCTION_VALUE = 10;

	/**
	 * The '<em><b>RULESET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULESET</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULESET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RULESET_VALUE = 11;

	/**
	 * The '<em><b>STATEMACHINE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STATEMACHINE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STATEMACHINE
	 * @model literal="STATE_MACHINE"
	 * @generated
	 * @ordered
	 */
	public static final int STATEMACHINE_VALUE = 12;

	/**
	 * The '<em><b>TIMEEVENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIMEEVENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIMEEVENT
	 * @model literal="TIME_EVENT"
	 * @generated
	 * @ordered
	 */
	public static final int TIMEEVENT_VALUE = 13;

	/**
	 * The '<em><b>ENTERPRISEARCHIVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ENTERPRISEARCHIVE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ENTERPRISEARCHIVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ENTERPRISEARCHIVE_VALUE = 14;

	/**
	 * The '<em><b>BEARCHIVERESOURCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BEARCHIVERESOURCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BEARCHIVERESOURCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BEARCHIVERESOURCE_VALUE = 15;

	/**
	 * The '<em><b>DOMAIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DOMAIN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOMAIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DOMAIN_VALUE = 16;

	/**
	 * The '<em><b>DECISIONTABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DECISIONTABLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DECISIONTABLE
	 * @model literal="DECISION_TABLE"
	 * @generated
	 * @ordered
	 */
	public static final int DECISIONTABLE_VALUE = 17;

	/**
	 * The '<em><b>FOLDER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FOLDER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FOLDER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FOLDER_VALUE = 18;

	/**
	 * The '<em><b>RULETEMPLATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RULETEMPLATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULETEMPLATE
	 * @model literal="RULE_TEMPLATE"
	 * @generated
	 * @ordered
	 */
	public static final int RULETEMPLATE_VALUE = 19;

	/**
	 * The '<em><b>QUERY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>QUERY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_VALUE = 20;

	/**
	 * The '<em><b>VIEW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VIEW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIEW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VIEW_VALUE = 21;

	/**
	 * The '<em><b>PROCESS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROCESS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROCESS_VALUE = 22;

	/**
	 * The '<em><b>JAVA SOURCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JAVA SOURCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SOURCE_VALUE = 23;

	/**
	 * An array of all the '<em><b>Element Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ElementTypes[] VALUES_ARRAY =
		new ElementTypes[] {
			UNKNOWN,
			CONCEPT,
			SCORECARD,
			INSTANCE,
			SIMPLEEVENT,
			METRIC,
			CHANNEL,
			DESTINATION,
			RULE,
			RULEFUNCTION,
			XSLT_FUNCTION,
			RULESET,
			STATEMACHINE,
			TIMEEVENT,
			ENTERPRISEARCHIVE,
			BEARCHIVERESOURCE,
			DOMAIN,
			DECISIONTABLE,
			FOLDER,
			RULETEMPLATE,
			QUERY,
			VIEW,
			PROCESS,
			JAVA_SOURCE,
		};

	/**
	 * A public read-only list of all the '<em><b>Element Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ElementTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Element Types</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ElementTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Element Types</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ElementTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Element Types</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementTypes get(int value) {
		switch (value) {
			case UNKNOWN_VALUE: return UNKNOWN;
			case CONCEPT_VALUE: return CONCEPT;
			case SCORECARD_VALUE: return SCORECARD;
			case INSTANCE_VALUE: return INSTANCE;
			case SIMPLEEVENT_VALUE: return SIMPLEEVENT;
			case METRIC_VALUE: return METRIC;
			case CHANNEL_VALUE: return CHANNEL;
			case DESTINATION_VALUE: return DESTINATION;
			case RULE_VALUE: return RULE;
			case RULEFUNCTION_VALUE: return RULEFUNCTION;
			case XSLT_FUNCTION_VALUE: return XSLT_FUNCTION;
			case RULESET_VALUE: return RULESET;
			case STATEMACHINE_VALUE: return STATEMACHINE;
			case TIMEEVENT_VALUE: return TIMEEVENT;
			case ENTERPRISEARCHIVE_VALUE: return ENTERPRISEARCHIVE;
			case BEARCHIVERESOURCE_VALUE: return BEARCHIVERESOURCE;
			case DOMAIN_VALUE: return DOMAIN;
			case DECISIONTABLE_VALUE: return DECISIONTABLE;
			case FOLDER_VALUE: return FOLDER;
			case RULETEMPLATE_VALUE: return RULETEMPLATE;
			case QUERY_VALUE: return QUERY;
			case VIEW_VALUE: return VIEW;
			case PROCESS_VALUE: return PROCESS;
			case JAVA_SOURCE_VALUE: return JAVA_SOURCE;
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
	private ElementTypes(int value, String name, String literal) {
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
	
} //ElementTypes
