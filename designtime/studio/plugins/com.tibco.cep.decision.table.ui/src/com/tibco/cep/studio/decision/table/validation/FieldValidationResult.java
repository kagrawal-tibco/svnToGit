package com.tibco.cep.studio.decision.table.validation;

public class FieldValidationResult {
	
	public static final int SUCCESS_CODE = 0x1;
	
	/**
	 * e.g : Duplicate name used.
	 */
	public static final int DUPLICATE_CODE = 0x2;
	
	/**
	 * e.g : Invalid field name.
	 */
	public static final int INVALID_CODE = 0x3;
	
	private int resultCode;
	
	/**
	 * Typically used for failure cases.
	 */
	private String message;

	public FieldValidationResult(int resultCode) {
		this(resultCode, null);
	}

	public FieldValidationResult(int resultCode, String message) {
		this.resultCode = resultCode;
		this.message = message;
	}

	/**
	 * @return the resultCode
	 */
	public final int getResultCode() {
		return resultCode;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}
}
