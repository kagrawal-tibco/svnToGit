package com.tibco.cep.studio.core.index.resolution;

import java.util.List;

public class AmbiguousReference {

	private List<? extends Object> fPotentialReferences;

	public AmbiguousReference(List<? extends Object> potentialReferences) {
		this.fPotentialReferences = potentialReferences;
	}

	public List<? extends Object> getPotentialReferences() {
		return fPotentialReferences;
	}

	public void setPotentialReferences(List<Object> potentialReferences) {
		fPotentialReferences = potentialReferences;
	}

}
