/**
 */
package com.tibco.be.util.config.sharedresources.id;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>File Type Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getFileTypeEnum()
 * @model extendedMetaData="name='fileType-type_._member_._1'"
 * @generated
 */
public enum FileTypeEnum implements Enumerator {
	/**
	 * The '<em><b>Entrust</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ENTRUST_VALUE
	 * @generated
	 * @ordered
	 */
	ENTRUST(0, "Entrust", "Entrust"),

	/**
	 * The '<em><b>JCEKS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JCEKS_VALUE
	 * @generated
	 * @ordered
	 */
	JCEKS(1, "JCEKS", "JCEKS"),

	/**
	 * The '<em><b>JKS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JKS_VALUE
	 * @generated
	 * @ordered
	 */
	JKS(2, "JKS", "JKS"),

	/**
	 * The '<em><b>PEM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PEM_VALUE
	 * @generated
	 * @ordered
	 */
	PEM(3, "PEM", "PEM"),

	/**
	 * The '<em><b>PKCS12</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PKCS12_VALUE
	 * @generated
	 * @ordered
	 */
	PKCS12(4, "PKCS12", "PKCS12"),

	/**
	 * The '<em><b></b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #__VALUE
	 * @generated
	 * @ordered
	 */
	_(5, "_", ""),

	/**
	 * The '<em><b>SSO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SSO_VALUE
	 * @generated
	 * @ordered
	 */
	SSO(6, "SSO", "SSO");

	/**
	 * The '<em><b>Entrust</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Entrust</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ENTRUST
	 * @model name="Entrust"
	 * @generated
	 * @ordered
	 */
	public static final int ENTRUST_VALUE = 0;

	/**
	 * The '<em><b>JCEKS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JCEKS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JCEKS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JCEKS_VALUE = 1;

	/**
	 * The '<em><b>JKS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JKS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JKS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JKS_VALUE = 2;

	/**
	 * The '<em><b>PEM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PEM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PEM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PEM_VALUE = 3;

	/**
	 * The '<em><b>PKCS12</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PKCS12</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PKCS12
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PKCS12_VALUE = 4;

	/**
	 * The '<em><b></b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b></b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #_
	 * @model literal=""
	 * @generated
	 * @ordered
	 */
	public static final int __VALUE = 5;

	/**
	 * The '<em><b>SSO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SSO</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SSO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SSO_VALUE = 6;

	/**
	 * An array of all the '<em><b>File Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final FileTypeEnum[] VALUES_ARRAY =
		new FileTypeEnum[] {
			ENTRUST,
			JCEKS,
			JKS,
			PEM,
			PKCS12,
			_,
			SSO,
		};

	/**
	 * A public read-only list of all the '<em><b>File Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<FileTypeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>File Type Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileTypeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FileTypeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>File Type Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileTypeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			FileTypeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>File Type Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FileTypeEnum get(int value) {
		switch (value) {
			case ENTRUST_VALUE: return ENTRUST;
			case JCEKS_VALUE: return JCEKS;
			case JKS_VALUE: return JKS;
			case PEM_VALUE: return PEM;
			case PKCS12_VALUE: return PKCS12;
			case __VALUE: return _;
			case SSO_VALUE: return SSO;
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
	private FileTypeEnum(int value, String name, String literal) {
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
	
} //FileTypeEnum
