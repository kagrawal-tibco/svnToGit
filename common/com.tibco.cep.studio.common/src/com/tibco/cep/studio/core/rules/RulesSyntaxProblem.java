package com.tibco.cep.studio.core.rules;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.RecognitionException;

import com.tibco.cep.studio.core.rules.grammar.RulesParser;

public class RulesSyntaxProblem implements IRulesSyntaxProblem {

	private String fErrorMessage;
	private RecognitionException fRecognitionException;

	public RulesSyntaxProblem(String errorMessage, RecognitionException ex) {
		this.fErrorMessage = errorMessage;
		this.fRecognitionException = ex;
	}

	public String getErrorMessage() {
		return fErrorMessage;
	}

	public int getLength() {
		if (fRecognitionException.token instanceof CommonToken) {
			return ((CommonToken)fRecognitionException.token).getStopIndex() -
			((CommonToken)fRecognitionException.token).getStartIndex() + 1;
		} else if (fRecognitionException instanceof EarlyExitException) {
			return 1;
		}
		return -1;
	}

	public int getOffset() {
		if (fRecognitionException.token instanceof CommonToken) {
			if (fRecognitionException.token.getType() == RulesParser.EOF) {
				return -1;
			}
			return ((CommonToken)fRecognitionException.token).getStartIndex();
		} else if (fRecognitionException instanceof EarlyExitException) {
			return fRecognitionException.index;
		}
		return -1;
	}

	public RecognitionException getRecognitionException() {
		return fRecognitionException;
	}

	public int getLine() {
		return fRecognitionException.token != null ? fRecognitionException.token.getLine() : fRecognitionException.line;
	}

	@Override
	public int getSeverity() {
		return 2; // IMarker.SEVERITY_ERROR
	}

	@Override
	public int getProblemCode() {
		return IProblemTypes.PROBLEM_SYNTAX_ERROR;
	}

}
