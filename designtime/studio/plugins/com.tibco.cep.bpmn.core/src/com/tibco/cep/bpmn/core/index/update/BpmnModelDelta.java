package com.tibco.cep.bpmn.core.index.update;

import java.util.List;


public class BpmnModelDelta  {

	private List<BpmnProjectDelta> fChangedProjects;
	private boolean fReferencesOnly;

	public BpmnModelDelta(List<BpmnProjectDelta> changedProjects, boolean referencesOnly) {
		this.fChangedProjects = changedProjects;
		this.fReferencesOnly = referencesOnly;
	}

	public boolean isReferencesOnly() {
		return fReferencesOnly;
	}

	public List<BpmnProjectDelta> getChangedProjects() {
		return fChangedProjects;
	}
	
}
