/**
 * 
 */
package com.tibco.cep.studio.rms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Summary of task list for particular role
 * @author aathalye
 *
 */
public class ArtifactReviewTaskSummary {
	
	private List<ArtifactReviewTask> taskList = new ArrayList<ArtifactReviewTask>();
	
	private String[] delegationRoles;

	/**
	 * @return the taskList
	 */
	public final List<ArtifactReviewTask> getTaskList() {
		return Collections.unmodifiableList(taskList);
	}

	/**
	 * Add a new {@link ArtifactReviewTask} to this summary.
	 * Also the summary becomes parent of the newly added task.
	 * @param reviewTask
	 * @return
	 */
	public boolean addTask(ArtifactReviewTask reviewTask) {
		boolean taskAdded = taskList.add(reviewTask);
		reviewTask.setParent(this);
		return taskAdded;
	}
	
	/**
	 * remove a new {@link ArtifactReviewTask} from this summary.
	 * @param reviewTask
	 * @return
	 */
	public boolean removeTask(ArtifactReviewTask reviewTask) {
		boolean taskRemoved = taskList.remove(reviewTask);
		return taskRemoved;
	}
	
	/**
	 * 
	 */
	public ArtifactReviewTaskSummary() {
	}

	/**
	 * @return the delegationRoles
	 */
	public final String[] getDelegationRoles() {
		return delegationRoles;
	}

	/**
	 * @param delegationRoles the delegationRoles to set
	 */
	public final void setDelegationRoles(String[] delegationRoles) {
		this.delegationRoles = delegationRoles;
	}
}
