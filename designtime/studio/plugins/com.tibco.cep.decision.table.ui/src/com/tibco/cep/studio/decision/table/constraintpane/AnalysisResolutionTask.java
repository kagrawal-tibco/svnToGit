/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IMarker;

/**
 * @author aathalye
 *
 */
public class AnalysisResolutionTask {
	
	private AnalysisResolutionTaskType resolutionTaskType;
	
	private LinkedList<AnalysisResolutionTask> childrenTasks = new LinkedList<AnalysisResolutionTask>();
	
	private IMarker marker;
	
	public AnalysisResolutionTask(final AnalysisResolutionTaskType resolutionTaskType,
			                      final IMarker marker) {
		this.resolutionTaskType = resolutionTaskType;
		this.marker = marker;
	}
	
	public AnalysisResolutionTaskType getTaskType() {
		return resolutionTaskType;
	}
	
	public IMarker getMarker() {
		return marker;
	}
	
	public void addChild(AnalysisResolutionTask childTask) {
		if (!childrenTasks.contains(childTask)) {
			childrenTasks.add(childTask);
		}
	}
	
	public Iterator<AnalysisResolutionTask> getChildren() {
		if (!childrenTasks.isEmpty()) {
			return childrenTasks.iterator();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AnalysisResolutionTask)) {
			return false;
		}
		AnalysisResolutionTask other = (AnalysisResolutionTask)obj;
		if (!other.getMarker().equals(marker)) {
			return false;
		}
		if (!other.getTaskType().equals(resolutionTaskType)) {
			return false;
		}
		return true;
	}
}
