/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.rms.model.event.IWorklistModelChangeListener;
import com.tibco.cep.studio.rms.model.event.ModelChangeEvent;


/**
 * @author aathalye
 *
 */
public class CommittedArtifactDetails extends ArtifactCommitMetaData {
	
	private ArtifactReviewTask parent;
	
	/**
	 * The workflow parameter
	 */
	private String status;
	
	/**
	 * This status is the changed status
	 */
	private String volatileStatus;
	
	/**
	 * The reviewer comments given before submit
	 */
	private String volatileReviewerComments;
	
	private boolean changed;
	
	private ArtifactWorkflowInfo workflowInfo;
	
	private List<IWorklistModelChangeListener> worklistModelChangeListeners = new ArrayList<IWorklistModelChangeListener>();

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public final void setStatus(String status) {
		this.status = status;
	}

	public ArtifactReviewTask getParent() {
		return parent;
	}

	public void setParent(ArtifactReviewTask parent) {
		this.parent = parent;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
		ModelChangeEvent modelChangeEvent = 
			new ModelChangeEvent(this, ModelChangeEvent.Features.STATUS_CHANGE);
		fireModelChangeEvent(modelChangeEvent);
	}

	/**
	 * @return the volatileStatus
	 */
	public final String getVolatileStatus() {
		return volatileStatus;
	}

	/**
	 * @param volatileStatus the volatileStatus to set
	 */
	public final void setVolatileStatus(String volatileStatus) {
		this.volatileStatus = volatileStatus;
	}

	/**
	 * @return the volatileReviewerComments
	 */
	public final String getVolatileReviewerComments() {
		return volatileReviewerComments;
	}

	/**
	 * @param volatileReviewerComments the volatileReviewerComments to set
	 */
	public final void setVolatileReviewerComments(String volatileReviewerComments) {
		this.volatileReviewerComments = volatileReviewerComments;
	}

	/**
	 * @param worklistModelChangeListener the worklistModelChangeListener to set
	 */
	public final void addWorklistModelChangeListener(IWorklistModelChangeListener worklistModelChangeListener) {
		if (!worklistModelChangeListeners.contains(worklistModelChangeListener)) {
			worklistModelChangeListeners.add(worklistModelChangeListener);
		}
	}
	
		
	private void fireModelChangeEvent(ModelChangeEvent modelChangeEvent) {
		for (IWorklistModelChangeListener modelChangeListener : worklistModelChangeListeners) {
			modelChangeListener.modelChanged(modelChangeEvent);
		}
	}
	
	public List<IWorklistModelChangeListener> getModelListeners() {
		return worklistModelChangeListeners;
	}

	/**
	 * @return the workflowInfo
	 */
	public final ArtifactWorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	/**
	 * @param workflowInfo the workflowInfo to set
	 */
	public final void setWorkflowInfo(ArtifactWorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}
}
