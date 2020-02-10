package com.tibco.cep.studio.core.rules;

import org.antlr.runtime.RecognitionException;

public interface IRulesSyntaxProblem extends IRulesProblem {

	public RecognitionException getRecognitionException();
	
}
