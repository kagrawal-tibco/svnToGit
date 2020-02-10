package com.tibco.cep.studio.core.rules;


public interface IProblemTypes {

	public static final int PROBLEM_UNKNOWN = -99;

	public static final int PROBLEM_SYNTAX_ERROR = -1;
	
	public static final int PROBLEM_RESOLUTION = 1;

	public static final int PROBLEM_INVALID_RETURN = 2;

	public static final int PROBLEM_ARRAY_ACCESS_ERROR = 3;

	public static final int PROBLEM_MISMATCHED_ARGUMENT_ERROR = 4;

	public static final int PROBLEM_MISMATCHED_RETURN_TYPE = 5;

	public static final int PROBLEM_MISMATCHED_BINARY_TYPE = 6;

	public static final int PROBLEM_IMMUTABLE_ASSIGNMENT_ERROR = 7;

	public static final int PROBLEM_AMBIGUOUS_REFERENCE_ERROR = 8;

	public static final int PROBLEM_NON_ALLOWED_METHOD_CALL = 9;

	public static final int PROBLEM_INVALID_METHOD_CALL = 10;

	public static final int PROBLEM_INVALID_BODY = 11;

	public static final int PROBLEM_DUPLICATE_VARIABLE = 12;

	
}
