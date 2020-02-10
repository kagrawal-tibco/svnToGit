package com.tibco.cep.modules.db.functions;

/**
 * Represents a single parameter of a PreparedStatement.
 * 
 * @author moshaikh
 * 
 */
public class PreparedStatementParam {
	
	/**
	 * Enum containing supported dataTypes for <code>PreparedStatementParam</code>.
	 * @author moshaikh
	 *
	 */
	public static enum ParamDataType {
		STRING, INT, DOUBLE, OBJECT
	};

	/**
	 * The dataType of PreparedStatement parameter.
	 */
	private ParamDataType dataType;

	/**
	 * The data/value of PreparedStatement parameter.
	 */
	private Object data;

	/**
	 * Creates a PreparedStatementParam with the specified dataType and value.
	 * 
	 * @param dataType
	 * @param data
	 */
	public PreparedStatementParam(ParamDataType dataType, Object data) {
		this.dataType = dataType;
		this.data = data;
	}

	public ParamDataType getDataType() {
		return dataType;
	}

	public void setDataType(ParamDataType dataType) {
		this.dataType = dataType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
