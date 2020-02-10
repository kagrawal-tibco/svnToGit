package com.tibco.cep.studio.core.rules;

import java.util.List;

public interface IProblemHandler {

	public void handleProblem(IRulesProblem problem);
	public boolean hasProblems();
	public List<IRulesProblem> getHandledProblems();
	
}
