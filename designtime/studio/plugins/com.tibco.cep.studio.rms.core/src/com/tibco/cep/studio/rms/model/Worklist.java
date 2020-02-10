package com.tibco.cep.studio.rms.model;

import java.util.ArrayList;
import java.util.List;


public class Worklist {

	private List<ArtifactReviewTask> artifactReviewTask = new ArrayList<ArtifactReviewTask>();
	
	public Worklist() {
		artifactReviewTask.clear();
	}
	
	public List<ArtifactReviewTask> getRequests() {
		return artifactReviewTask;
	}
	
	public void add(ArtifactReviewTask request) {
		artifactReviewTask.add(request);
	}
	
	public void remove(ArtifactReviewTask request) {
		artifactReviewTask.remove(request);
	}

	public void removeAllRequests(){
		artifactReviewTask.clear();
	}
}
