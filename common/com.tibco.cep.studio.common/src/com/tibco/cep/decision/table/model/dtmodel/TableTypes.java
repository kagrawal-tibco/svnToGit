/**
 * 
 */
package com.tibco.cep.decision.table.model.dtmodel;


/**
 * @author aathalye
 *
 */
public enum TableTypes {
	
	DECISION_TABLE("DecisionTable"),
	
	EXCEPTION_TABLE("ExceptionTable");
	
	private static final TableTypes[] VALUES_ARRAY =
		new TableTypes[] {
		DECISION_TABLE,
		EXCEPTION_TABLE,
		};
	
	private String value;
	
	private TableTypes(String value) {
		this.value = value;
	}
	
	public static TableTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TableTypes result = VALUES_ARRAY[i];
			if (result.value.equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}
	
	
}
