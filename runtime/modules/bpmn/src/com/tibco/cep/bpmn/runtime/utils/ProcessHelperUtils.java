package com.tibco.cep.bpmn.runtime.utils;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.events.EndEvent;

public class ProcessHelperUtils {
	
	public static void dfs(Task currentTask, Task endTask,
			LinkedList<Task> path, List<LinkedList<Task>> paths) {
		
		if(currentTask == null || currentTask instanceof EndEvent)
			return;

		if (path.contains(currentTask)) {
			return;
		}

		path.add(currentTask);

		if (currentTask == endTask) {
			paths.add(new LinkedList<Task>(path));
			return;
		}

		Transition[] transitions = currentTask.getOutgoingTransitions();
		for (Transition t : transitions) {
			dfs(t.toTask(), endTask, path, paths);
			if(!path.isEmpty()){
				path.removeLast();
			}
		}

	}

}
