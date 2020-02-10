/**
 */
package com.tibco.be.util.config.sharedresources.id;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Object Type Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getObjectTypeEnum()
 * @model extendedMetaData="name='objectType-type_._member_._1'"
 * @generated
 */
public enum ObjectTypeEnum implements Enumerator {
	/**
	 * The '<em><b>Url</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #URL_VALUE
	 * @generated
	 * @ordered
	 */
	URL(0, "url", "url"),

	/**
	 * The '<em><b>Cert Plus Key URL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CERT_PLUS_KEY_URL_VALUE
	 * @generated
	 * @ordered
	 */
	CERT_PLUS_KEY_URL(1, "certPlusKeyURL", "certPlusKeyURL"),

	/**
	 * The '<em><b>Username Password</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USERNAME_PASSWORD_VALUE
	 * @generated
	 * @ordered
	 */
	USERNAME_PASSWORD(2, "usernamePassword", "usernamePassword");

	/**
	 * The '<em><b>Url</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Url</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #URL
	 * @model name="url"
	 * @generated
	 * @ordered
	 */
	public static final int URL_VALUE = 0;

	/**
	 * The '<em><b>Cert Plus Key URL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cert Plus Key URL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CERT_PLUS_KEY_URL
	 * @model name="certPlusKeyURL"
	 * @generated
	 * @ordered
	 */
	public static final int CERT_PLUS_KEY_URL_VALUE = 1;

	/**
	 * The '<em><b>Username Password</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Username Password</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USERNAME_PASSWORD
	 * @model name="usernamePassword"
	 * @generated
	 * @ordered
	 */
	public static final int USERNAME_PASSWORD_VALUE = 2;

	/**
	 * An array of all the '<em><b>Object Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ObjectTypeEnum[] VALUES_ARRAY =
		new ObjectTypeEnum[] {
			URL,
			CERT_PLUS_KEY_URL,
			USERNAME_PASSWORD,
		};

	/**
	 * A public read-only list of all the '<em><b>Object Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ObjectTypeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Object Type Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ObjectTypeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ObjectTypeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Object Type Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ObjectTypeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ObjectTypeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Object Type Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ObjectTypeEnum get(int value) {
		switch (value) {
			case URL_VALUE: return URL;
			case CERT_PLUS_KEY_URL_VALUE: return CERT_PLUS_KEY_URL;
			case USERNAME_PASSWORD_VALUE: return USERNAME_PASSWORD;
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
	private ObjectTypeEnum(int value, String name, String literal) {
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
	
} //ObjectTypeEnum
