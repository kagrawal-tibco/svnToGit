/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RANGE_TYPE_PROBLEM;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;

/**
 * @author aathalye
 * 
 */
public class AnalysisProblemsCollector {
	
	private List<ProblemEvent> fProblemEvents = new ArrayList<ProblemEvent>();
	
	public void reportProblem(ProblemEvent problem) {
		fProblemEvents.add(problem);
	}
	
	public void reportProblem(IResource resource, String message, String details,
			int lineNumber, int start, int length, int severity) {
		
		ProblemEvent problemEvent = new ProblemEvent(null, null, message, details, resource,
				lineNumber, severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportRangeProblem(IResource resource, String message,
			String columnName, Object max2, Object min,
			RANGE_TYPE_PROBLEM rangeTypeProblem, int lineNumber, int severity) {
		
		ProblemEvent problemEvent = new RangeProblemEvent(null, null, message,
				null, resource, max2, min, columnName,
				rangeTypeProblem, lineNumber, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void  reportOverlappingRangeProblem(final IResource resource,
            final String message, 
            int ruleId, 
            final int severity) {
		
		ProblemEvent problemEvent = new OverlappingRangeProblemEvent(null, message, resource,
				ruleId,  severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportRuleCombinationProblem(final IResource resource,
			                                    final String message, 
			                                    String location, 
			                                    final int severity) {

		ProblemEvent problemEvent = new RuleCombinationProblemEvent(null, message, resource,
				Integer.parseInt(location),  severity);
		fProblemEvents.add(problemEvent);
	}

	public void reportDuplicatesProblem(final IResource resource,
			final String message, final int duplicateRuleID, final int severity) {

		
		ProblemEvent problemEvent = new DuplicateRuleProblemEvent(null,
				message, resource, duplicateRuleID, -1, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void reportConflictingActionsProblem(final IResource resource,
			                                       final String message, 
			                                       final int problemRuleID, final int severity) {

		
		ProblemEvent problemEvent = new ConflictingActionsProblemEvent(null,
				message, resource, problemRuleID, severity);
		fProblemEvents.add(problemEvent);
	}
	
	public void reportUncoveredDomainEntriesProblem(final IResource resource,
			final String message, final String domainValue, final String columnName, final int severity) {

		ProblemEvent problemEvent = new UncoveredDomainEntryProblemEvent(null,
				message, resource, domainValue, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
	
	protected List<ProblemEvent> getProblems() {
		return Collections.unmodifiableList(fProblemEvents);
	}
	
	public void reportUncoveredEqualsProblem(final IResource resource,
			                                    final String message, 
			                                    final Object expressionKey,
			                                    final String columnName,
			                                    final int severity) {
		ProblemEvent problemEvent = new MissingEqualsCriterionProblemEvent(
				null, message, resource, expressionKey, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
}
