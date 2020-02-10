package com.tibco.cep.studio.ui.editors.rules.text;

import com.tibco.cep.studio.core.rules.IRulesProblem;

public class SyntaxProblemContext implements IProblemContext {

	private IRulesProblem fProblem;

	public SyntaxProblemContext(IRulesProblem problem) {
		this.fProblem = problem;
	}

	public IRulesProblem getProblem() {
		return fProblem;
	}

	@Override
	public int getProblemCode() {
		return fProblem.getProblemCode();
	}

}
