/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * A priority queue which orders all "delete" tasks before "add" tasks.
 * <p>
 * Delete tasks are maintained in a {@link LinkedList} with the head
 * being the parent, whereas add tasks have equal priority.
 * </p>
 * @author aathalye
 *
 */
public class AnalysisResolutionQueue {
	
	private PriorityQueue<AnalysisResolutionTask> resolutionTaskQueue;
	
	public AnalysisResolutionQueue(int initialQueueSize) {
		if (initialQueueSize < 0) {
			initialQueueSize = 0; 
		}
		resolutionTaskQueue = 
			new PriorityQueue<AnalysisResolutionTask>(initialQueueSize, new ResolutionTaskComparator()); 
	}
	
	public void addTask(AnalysisResolutionTask analysisResolutionTask) {
		if (analysisResolutionTask.getTaskType().equals(AnalysisResolutionTaskType.DELETE)) {
			//Check if it has any delete task
			for (AnalysisResolutionTask task : resolutionTaskQueue) {
				if (task.getTaskType().equals(AnalysisResolutionTaskType.DELETE)) {
					//This should be the first one
					task.addChild(analysisResolutionTask);
					return;
				}
			}
		} 
		resolutionTaskQueue.offer(analysisResolutionTask);
	}
	
	private class ResolutionTaskComparator implements Comparator<AnalysisResolutionTask> {

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(AnalysisResolutionTask task1, AnalysisResolutionTask task2) {
			//Check the task types
			AnalysisResolutionTaskType taskType1 = task1.getTaskType();
			AnalysisResolutionTaskType taskType2 = task2.getTaskType();
			//Compare ordinals
			int ordinal1 = taskType1.ordinal();
			int ordinal2 = taskType2.ordinal();
			if (ordinal1 > ordinal2) {
				return 1;
			}
			else if (ordinal1 == ordinal2) {
				return 0;
			}
			else if (ordinal1 < ordinal2) {
				return -1;
			}
			return Integer.MIN_VALUE;
		}
	}
	
	public AnalysisResolutionTask poll() {
		return resolutionTaskQueue.poll();
	}
	
	public int size() {
		return resolutionTaskQueue.size();
	}
}
