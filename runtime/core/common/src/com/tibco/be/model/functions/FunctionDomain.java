package com.tibco.be.model.functions;



/**
 * Describes an enumerated function execution domain
 *
 * @version 5.2.0
 * @since 5.2.0
 * @.category public-api 
 */
public enum FunctionDomain {

	/**
     * This domain indicates that the associated function can be used in a Rule Condition as a non mutable function
     *
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 */
	CONDITION("condition",0),

	/**
     * This domain indicates that the associated function can be used in a Rule Action as a mutable and non mutable function
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 */
	ACTION("action",1),

	/**
     * This domain indicates that the associated function can be used as a non mutable query function
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 */
	QUERY("query",2),
	
	/**
     * This domain indicates that the associated function can be used in a Decision Table as a mutable and non mutable function
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 */
	BUI("bui",3),
	
	/**
     * This domain indicates that the associated function can be used in a Process as a mutable and non mutable function
	 * @version 5.2.0
	 * @since 5.2.0
	 * @.category public-api
	 */
	PROCESS("process",4);
	
	private String typeValue;
	private int precedence; // 0-high 9-Low
	private FunctionDomain(String type,int precedence) {
		this.typeValue = type;
		this.precedence = precedence;
	}
	public String getTypeValue() { return typeValue; }
	public String toString() { return typeValue; }
}
