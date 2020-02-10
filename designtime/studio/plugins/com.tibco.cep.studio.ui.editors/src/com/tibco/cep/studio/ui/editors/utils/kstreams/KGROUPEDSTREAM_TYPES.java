/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import org.eclipse.emf.common.util.Enumerator;

/**
 * @author shivkumarchelwa
 *
 */
public enum KGROUPEDSTREAM_TYPES implements Enumerator {

	AGGREGATE(0, "Aggregate", "Aggregate"), COUNT(1, "Count", "Count"), REDUCE(2, "Reduce", "Reduce");

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	KGROUPEDSTREAM_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

	public static boolean contains(String typeName) {
		if (typeName == null || typeName.isEmpty()) {
			return false;
		}
		KGROUPEDSTREAM_TYPES[] types = values();
		for (int i = 0; i < types.length; i++) {
			KGROUPEDSTREAM_TYPES t = types[i];
			if (t.getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}
}
