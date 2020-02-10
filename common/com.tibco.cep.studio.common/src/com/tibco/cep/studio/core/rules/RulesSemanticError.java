package com.tibco.cep.studio.core.rules;

public class RulesSemanticError implements IRulesProblem {

	private String fErrorMessage;
	private int fOffset;
	private int fLength;
	private int fLine;
	private int fSeverity;
	private int fProblemCode;

	public RulesSemanticError(int problemCode, String errorMessage, int offset, int length, int line) {
		this(problemCode, errorMessage, offset, length, line, 2); // severity error
	}
	
	public RulesSemanticError(int problemCode, String errorMessage, int offset, int length, int line, int severity) {
		this.fProblemCode = problemCode;
		this.fErrorMessage = errorMessage;
		this.fOffset = offset;
		this.fLength = length;
		this.fLine = line;
		this.fSeverity = severity;
	}

	@Override
	public String getErrorMessage() {
		return fErrorMessage;
	}

	@Override
	public int getLength() {
		return fLength;
	}

	@Override
	public int getLine() {
		return fLine;
	}

	@Override
	public int getOffset() {
		return fOffset;
	}

	@Override
	public int getSeverity() {
		return fSeverity;
	}

	@Override
	public int getProblemCode() {
		return fProblemCode;
	}
	
}
