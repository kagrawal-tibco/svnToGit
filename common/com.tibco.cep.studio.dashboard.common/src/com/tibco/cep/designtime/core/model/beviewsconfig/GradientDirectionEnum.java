/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Gradient Direction Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getGradientDirectionEnum()
 * @model extendedMetaData="name='GradientDirectionEnum'"
 * @generated
 */
public enum GradientDirectionEnum implements Enumerator {
	/**
	 * The '<em><b>Toptobottom</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TOPTOBOTTOM_VALUE
	 * @generated
	 * @ordered
	 */
	TOPTOBOTTOM(0, "toptobottom", "toptobottom"),

	/**
	 * The '<em><b>Lefttoright</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEFTTORIGHT_VALUE
	 * @generated
	 * @ordered
	 */
	LEFTTORIGHT(1, "lefttoright", "lefttoright"),

	/**
	 * The '<em><b>Bottomtotop</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOTTOMTOTOP_VALUE
	 * @generated
	 * @ordered
	 */
	BOTTOMTOTOP(2, "bottomtotop", "bottomtotop"),

	/**
	 * The '<em><b>Righttoleft</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RIGHTTOLEFT_VALUE
	 * @generated
	 * @ordered
	 */
	RIGHTTOLEFT(3, "righttoleft", "righttoleft");

	/**
	 * The '<em><b>Toptobottom</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Toptobottom</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TOPTOBOTTOM
	 * @model name="toptobottom"
	 * @generated
	 * @ordered
	 */
	public static final int TOPTOBOTTOM_VALUE = 0;

	/**
	 * The '<em><b>Lefttoright</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Lefttoright</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LEFTTORIGHT
	 * @model name="lefttoright"
	 * @generated
	 * @ordered
	 */
	public static final int LEFTTORIGHT_VALUE = 1;

	/**
	 * The '<em><b>Bottomtotop</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bottomtotop</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOTTOMTOTOP
	 * @model name="bottomtotop"
	 * @generated
	 * @ordered
	 */
	public static final int BOTTOMTOTOP_VALUE = 2;

	/**
	 * The '<em><b>Righttoleft</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Righttoleft</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RIGHTTOLEFT
	 * @model name="righttoleft"
	 * @generated
	 * @ordered
	 */
	public static final int RIGHTTOLEFT_VALUE = 3;

	/**
	 * An array of all the '<em><b>Gradient Direction Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final GradientDirectionEnum[] VALUES_ARRAY =
		new GradientDirectionEnum[] {
			TOPTOBOTTOM,
			LEFTTORIGHT,
			BOTTOMTOTOP,
			RIGHTTOLEFT,
		};

	/**
	 * A public read-only list of all the '<em><b>Gradient Direction Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<GradientDirectionEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Gradient Direction Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GradientDirectionEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GradientDirectionEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Gradient Direction Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GradientDirectionEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GradientDirectionEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Gradient Direction Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GradientDirectionEnum get(int value) {
		switch (value) {
			case TOPTOBOTTOM_VALUE: return TOPTOBOTTOM;
			case LEFTTORIGHT_VALUE: return LEFTTORIGHT;
			case BOTTOMTOTOP_VALUE: return BOTTOMTOTOP;
			case RIGHTTOLEFT_VALUE: return RIGHTTOLEFT;
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
	private GradientDirectionEnum(int value, String name, String literal) {
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
	
} //GradientDirectionEnum
