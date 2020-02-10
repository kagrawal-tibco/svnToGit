/**
 * 
 */
package com.tibco.cep.store;

import java.util.Arrays;

/**
 * @author vpatil
 *
 */
public enum StoreType {
	AS("AS");
	
	private String value;
	
	StoreType(String value) {
		this.value = value;
	}
	
	public static String[] getNames() {
	    return Arrays.toString(StoreType.class.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}
}
