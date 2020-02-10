package com.tibco.cep.studio.core.index.update;

import java.util.List;


public class StudioModelDelta  {

	private List<StudioProjectDelta> fChangedProjects;
	private boolean fHasReferenceChanges;

	public StudioModelDelta(List<StudioProjectDelta> changedProjects, boolean hasReferenceChanges) {
		this.fChangedProjects = changedProjects;
		this.fHasReferenceChanges = hasReferenceChanges;
	}

	public boolean hasReferenceChanges() {
		return fHasReferenceChanges;
	}

	public List<StudioProjectDelta> getChangedProjects() {
		return fChangedProjects;
	}
	
}
