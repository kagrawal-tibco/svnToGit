package com.tibco.cep.decision.table.problems;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.util.logger.problems.IProblemEventListener;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEventManager;

public class ProblemsManager {

	private class ProblemEventListener implements IProblemEventListener {


		public void handleProblemEvent(ProblemEvent event) {
			if (event == null) {
				return;
			}
			synchronized (problems) {
				problems.add(event);
			}
		}

		public void clearAllProblems() {
			synchronized (problems) {
				problems.clear();
			}
		}

		public void clearProblems(String errorCode) {
			if (errorCode == null) {
				clearAllProblems();
				return;
			}
			List<ProblemEvent> toBeRemoved = new ArrayList<ProblemEvent>();
			synchronized (problems) {
				for (ProblemEvent event : problems) {
					if (errorCode.equals(event.getErrorCode())) {
						toBeRemoved.add(event);
					}
				}
				for (ProblemEvent problemEvent : toBeRemoved) {
					problems.remove(problemEvent);
				}
			}
		}

	}
	
	private static ProblemsManager 	instance = new ProblemsManager();
	private IProblemEventListener 	listener = new ProblemEventListener();
	private List<ProblemEvent> 		problems = new ArrayList<ProblemEvent>();
	
	public static ProblemsManager getInstance() {
		return instance;
	}

	public ProblemsManager() {
	}
	
	public void dispose() {
		ProblemEventManager.getInstance().removeProblemEventListener(listener);
	}

	public void init() {
		// register to receive problem view events
		ProblemEventManager.getInstance().addProblemEventListener(listener);
	}

	public List<ProblemEvent> getProblems() {
		return problems;
	}
	
}
