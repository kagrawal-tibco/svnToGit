package com.tibco.cep.studio.core.rules;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.RecognitionException;

public class DefaultProblemHandler implements IProblemHandler {

	private List<IRulesProblem> fErrors = new ArrayList<IRulesProblem>();
	
	public void handleProblem(IRulesProblem problem) {
		// errors can be reported multiple times due to the recovery mechanism
		// perform a (slow) check to make sure we don't report the same error
		// multiple times
		if (!collected(problem)) {
			fErrors.add(problem);
		}
	}

	private boolean collected(IRulesProblem problem) {
		for (IRulesProblem prob : fErrors) {
			if (prob instanceof IRulesSyntaxProblem) {
				RecognitionException recognitionException = ((IRulesSyntaxProblem) prob).getRecognitionException();
				RecognitionException recognitionException2 = ((IRulesSyntaxProblem) problem).getRecognitionException();
				if (areEqual(recognitionException, recognitionException2)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean areEqual(RecognitionException r1,
			RecognitionException r2) {
		return (r1.approximateLineInfo == r2.approximateLineInfo
				&& r1.c == r2.c
				&& r1.charPositionInLine == r2.charPositionInLine
				&& r1.index == r2.index
				&& r1.line == r2.line);
	}

	@Override
	public List<IRulesProblem> getHandledProblems() {
		return fErrors;
	}

	@Override
	public boolean hasProblems() {
		return fErrors.size() > 0;
	}
	
}
