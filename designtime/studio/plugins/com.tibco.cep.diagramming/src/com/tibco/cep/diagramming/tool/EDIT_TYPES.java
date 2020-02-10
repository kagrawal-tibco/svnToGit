package com.tibco.cep.diagramming.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public enum EDIT_TYPES {

	CUT(0, "Cut", "CUT"),
	COPY(1, "Copy", "Copy"),
	PASTE(2, "Paste", "Paste"),
	DELETE(3, "Delete", "Delete");

	public static final int CUT_VALUE = 0;
	public static final int COPY_VALUE = 1;
	public static final int PASTE_VALUE = 2;
	public static final int DELETE_VALUE = 3;


	private static final EDIT_TYPES[] VALUES_ARRAY =
		new EDIT_TYPES[] {
			CUT,
			COPY,
			PASTE,
			DELETE,
		};

	public static final List<EDIT_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * @param literal
	 * @return
	 */
	public static EDIT_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EDIT_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * @param name
	 * @return
	 */
	public static EDIT_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EDIT_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * @param value
	 * @return
	 */
	public static EDIT_TYPES get(int value) {
		switch (value) {
			case CUT_VALUE: return CUT;
			case COPY_VALUE: return COPY;
			case PASTE_VALUE: return PASTE;
			case DELETE_VALUE: return DELETE;
		}
		return null;
	}

	private final int value;

	private final String name;

	private final String literal;

	/**
	 * @param value
	 * @param name
	 * @param literal
	 */
	private EDIT_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	public int getValue() {
	  return value;
	}

	public String getName() {
	  return name;
	}

	public String getLiteral() {
	  return literal;
	}

	@Override
	public String toString() {
		return literal;
	}
	
} 