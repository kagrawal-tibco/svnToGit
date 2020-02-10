/**
 * 
 */
package com.tibco.cep.decision.table.constraintpane;

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
	
	protected void reportProblem(IResource resource, String message,
			int lineNumber, int start, int length, int severity) {
		
//		ProblemEvent problemEvent = new ProblemEvent(null, message, resource,
//				lineNumber, severity);
		//Do not add this to the collection for the time-being
		//fProblemEvents.add(problemEvent);
	}

	protected void reportRangeProblem(IResource resource, String message,
			String columnName, int minValue, int maxValue,
			RANGE_TYPE_PROBLEM rangeTypeProblem, int lineNumber, int severity) {
		
		ProblemEvent problemEvent = new RangeProblemEvent(null, null, message,
				null, resource, minValue, maxValue, columnName,
				rangeTypeProblem, lineNumber, severity);
		fProblemEvents.add(problemEvent);
	}
	
	protected void  reportOverlappingRangeProblem(final IResource resource,
            final String message, 
            int ruleId, 
            final int severity) {
		
		ProblemEvent problemEvent = new OverlappingRangeProblemEvent(null, message, resource,
				ruleId,  severity);
		fProblemEvents.add(problemEvent);
	}

	protected void reportRuleCombinationProblem(final IResource resource,
			                                    final String message, 
			                                    String location, 
			                                    final int severity) {

		ProblemEvent problemEvent = new RuleCombinationProblemEvent(null, message, resource,
				Integer.parseInt(location),  severity);
		fProblemEvents.add(problemEvent);
	}

	protected void reportDuplicatesProblem(final IResource resource,
			final String message, final int duplicateRuleID, final int severity) {

		
		ProblemEvent problemEvent = new DuplicateRuleProblemEvent(null,
				message, resource, duplicateRuleID, -1, severity);
		fProblemEvents.add(problemEvent);
	}
	
	protected void reportConflictingActionsProblem(final IResource resource,
			                                       final String message, 
			                                       final int problemRuleID, final int severity) {

		
		ProblemEvent problemEvent = new ConflictingActionsProblemEvent(null,
				message, resource, problemRuleID, severity);
		fProblemEvents.add(problemEvent);
	}
	
	protected void reportUncoveredDomainEntriesProblem(final IResource resource,
			final String message, final String domainValue, final String columnName, final int severity) {

		ProblemEvent problemEvent = new UncoveredDomainEntryProblemEvent(null,
				message, resource, domainValue, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
	
	protected List<ProblemEvent> getProblems() {
		return Collections.unmodifiableList(fProblemEvents);
	}
	
	protected void reportUncoveredEqualsProblem(final IResource resource,
			                                    final String message, 
			                                    final Integer expression,
			                                    final String columnName,
			                                    final int severity) {
		ProblemEvent problemEvent = new MissingEqualsCriterionProblemEvent(
				null, message, resource, expression, columnName, severity);
		fProblemEvents.add(problemEvent);
	}
}
