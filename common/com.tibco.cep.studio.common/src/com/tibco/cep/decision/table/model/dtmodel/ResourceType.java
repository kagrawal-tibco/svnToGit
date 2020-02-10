/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Resource Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getResourceType()
 * @model
 * @generated
 */
public enum ResourceType implements Enumerator {
	/**
	 * The '<em><b>Undefined</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDEFINED(0, "undefined", "UNDEFINED"),

	/**
	 * The '<em><b>Concept</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT(1, "concept", "CONCEPT"),

	/**
	 * The '<em><b>Ruleset</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULESET_VALUE
	 * @generated
	 * @ordered
	 */
	RULESET(2, "ruleset", "RULESET"),

	/**
	 * The '<em><b>Rule</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_VALUE
	 * @generated
	 * @ordered
	 */
	RULE(3, "rule", "RULE"),

	/**
	 * The '<em><b>Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	EVENT(4, "event", "EVENT"),

	/**
	 * The '<em><b>Decision Table</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DECISION_TABLE_VALUE
	 * @generated
	 * @ordered
	 */
	DECISION_TABLE(5, "decisionTable", "DECISIONTABLE"),

	/**
	 * The '<em><b>Property</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY(6, "property", "PROPERTY"),

	/**
	 * The '<em><b>Attribute</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ATTRIBUTE_VALUE
	 * @generated
	 * @ordered
	 */
	ATTRIBUTE(7, "attribute", "ATTRIBUTE"), /**
	 * The '<em><b>Rule Function</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_FUNCTION_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_FUNCTION(8, "ruleFunction", "ruleFunction"), /**
	 * The '<em><b>Folder</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FOLDER_VALUE
	 * @generated
	 * @ordered
	 */
	FOLDER(9, "folder", "folder");

	/**
	 * The '<em><b>Undefined</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Undefined</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED
	 * @model name="undefined" literal="UNDEFINED"
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINED_VALUE = 0;

	/**
	 * The '<em><b>Concept</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Concept</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT
	 * @model name="concept" literal="CONCEPT"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_VALUE = 1;

	/**
	 * The '<em><b>Ruleset</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ruleset</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULESET
	 * @model name="ruleset" literal="RULESET"
	 * @generated
	 * @ordered
	 */
	public static final int RULESET_VALUE = 2;

	/**
	 * The '<em><b>Rule</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE
	 * @model name="rule" literal="RULE"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_VALUE = 3;

	/**
	 * The '<em><b>Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVENT
	 * @model name="event" literal="EVENT"
	 * @generated
	 * @ordered
	 */
	public static final int EVENT_VALUE = 4;

	/**
	 * The '<em><b>Decision Table</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Decision Table</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DECISION_TABLE
	 * @model name="decisionTable" literal="DECISIONTABLE"
	 * @generated
	 * @ordered
	 */
	public static final int DECISION_TABLE_VALUE = 5;

	/**
	 * The '<em><b>Property</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Property</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY
	 * @model name="property" literal="PROPERTY"
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_VALUE = 6;

	/**
	 * The '<em><b>Attribute</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Attribute</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ATTRIBUTE
	 * @model name="attribute" literal="ATTRIBUTE"
	 * @generated
	 * @ordered
	 */
	public static final int ATTRIBUTE_VALUE = 7;

	/**
	 * The '<em><b>Rule Function</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Function</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_FUNCTION
	 * @model name="ruleFunction"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_FUNCTION_VALUE = 8;

	/**
	 * The '<em><b>Folder</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Folder</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FOLDER
	 * @model name="folder"
	 * @generated
	 * @ordered
	 */
	public static final int FOLDER_VALUE = 9;

	/**
	 * An array of all the '<em><b>Resource Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ResourceType[] VALUES_ARRAY =
		new ResourceType[] {
			UNDEFINED,
			CONCEPT,
			RULESET,
			RULE,
			EVENT,
			DECISION_TABLE,
			PROPERTY,
			ATTRIBUTE,
			RULE_FUNCTION,
			FOLDER,
		};

	/**
	 * A public read-only list of all the '<em><b>Resource Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ResourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResourceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResourceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType get(int value) {
		switch (value) {
			case UNDEFINED_VALUE: return UNDEFINED;
			case CONCEPT_VALUE: return CONCEPT;
			case RULESET_VALUE: return RULESET;
			case RULE_VALUE: return RULE;
			case EVENT_VALUE: return EVENT;
			case DECISION_TABLE_VALUE: return DECISION_TABLE;
			case PROPERTY_VALUE: return PROPERTY;
			case ATTRIBUTE_VALUE: return ATTRIBUTE;
			case RULE_FUNCTION_VALUE: return RULE_FUNCTION;
			case FOLDER_VALUE: return FOLDER;
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
	private ResourceType(int value, String name, String literal) {
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
	
} //ResourceType
