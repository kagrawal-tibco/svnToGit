/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils.kstreams;

import org.eclipse.emf.common.util.Enumerator;

/**
 * @author shivkumarchelwa
 *
 */
public enum KSTREAM_TYPES implements Enumerator {

	FILTER(0, "Filter", "Filter"), FILTER_NOT(1, "FilterNot", "FilterNot"),
	FLAT_MAP_VALUES(2, "FlatMapValues", "FlatMapValues"), MAP_VALUES(3, "MapValues", "MapValues"),
	GROUP_BY_KEY(4, "GroupByKey", "GroupByKey"), JOIN(5, "Join", "Join"), LEFT_JOIN(6, "LeftJoin", "LeftJoin"),
	OUTER_JOIN(7, "OuterJoin", "OuterJoin"), SELECT_KEY(8, "SelectKey", "SelectKey");

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

	KSTREAM_TYPES(int value, String name, String literal) {
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
		KSTREAM_TYPES[] types = values();
		for (int i = 0; i < types.length; i++) {
			KSTREAM_TYPES t = types[i];
			if (t.getName().equals(typeName)) {
				return true;
			}
		}
		return false;
	}
}
