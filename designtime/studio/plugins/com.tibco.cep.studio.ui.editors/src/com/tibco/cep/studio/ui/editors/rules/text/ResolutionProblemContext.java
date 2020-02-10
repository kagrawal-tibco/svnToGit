package com.tibco.cep.studio.ui.editors.rules.text;

import com.tibco.cep.studio.core.index.model.ElementReference;

public class ResolutionProblemContext implements IProblemContext {

	private ElementReference fReference;
	private int fProblemCode;

	public ResolutionProblemContext(int problemCode, ElementReference reference) {
		this.fProblemCode = problemCode;
		this.fReference = reference;
	}

	public ElementReference getReference() {
		return fReference;
	}

	@Override
	public int getProblemCode() {
		return fProblemCode;
	}
	
}
