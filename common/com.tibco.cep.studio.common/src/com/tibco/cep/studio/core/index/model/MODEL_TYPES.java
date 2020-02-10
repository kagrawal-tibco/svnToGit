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
 * A representation of the literals of the enumeration '<em><b>MODEL TYPES</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getMODEL_TYPES()
 * @model
 * @generated
 */
public enum MODEL_TYPES implements Enumerator {
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
	ADAPTER_ARCHIVE(16, "AdapterArchive", "AdapterArchive");

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
	 * An array of all the '<em><b>MODEL TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final MODEL_TYPES[] VALUES_ARRAY =
		new MODEL_TYPES[] {
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
		};

	/**
	 * A public read-only list of all the '<em><b>MODEL TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<MODEL_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>MODEL TYPES</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MODEL_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MODEL_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>MODEL TYPES</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MODEL_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			MODEL_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>MODEL TYPES</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MODEL_TYPES get(int value) {
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
	private MODEL_TYPES(int value, String name, String literal) {
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
	
} //MODEL_TYPES
