package com.tibco.cep.studio.decision.table.constraintpane;

import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_FIXABLE_MARKER_NAME;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_GENERAL_MARKER_NAME;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_RULECOMB_MARKER_NAME;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_COLUMN_NAME;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_CONFLICTING_ACTIONS_RULEID;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_DUPLICATE_RULEID;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_MAX_VALUE;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_MIN_VALUE;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RANGE_PROBLEM;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_RULE_COMBINATION_PROBLEM;
import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.extension.IDecisionTableAnalyzerActionProvider;
import com.tibco.cep.studio.decision.table.extension.IDecisionTableAnalyzerExtension;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.IAutofixableProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;

public class SimpleDecisionTableAnalyzerExtension implements IDecisionTableAnalyzerExtension, IDecisionTableAnalyzerActionProvider {

	/**
	 * 
	 */
	protected IDecisionTableEditor editor;

	/**
	 * @param decisionTableAnalyzerView
	 */
	public SimpleDecisionTableAnalyzerExtension() {
	}

	@Override
	public boolean canAnalyze() {
		return true;
	}

	@Override
	public boolean canGenerateTestData() {
		return false;
	}

	@Override
	public boolean canShowCoverage() {
		return true;
	}

	@Override
	public boolean canShowTestDataCoverage() {
		return false;
	}

	@Override
	public void setCurrentDecisionTable(IDecisionTableEditor editor) {
		this.editor = editor;
	}

	@Override
	public void processAnalyzerProblemEvent(ProblemEvent problemEvent,
			Table table) throws Exception {
		IFile resource = getUnderlyingFile();
		if (resource == null) {
			return;
		}
		String message = problemEvent.getMessage();
		int location = problemEvent.getLocation();
		int severity = problemEvent.getSeverity();
		// Else ignore it
		if (problemEvent instanceof IAutofixableProblemEvent) {
			IMarker marker = resource
					.createMarker(ANALYZE_FIXABLE_MARKER_NAME);
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, severity);
			if (location >= 0) {
				attributes.put(IMarker.LINE_NUMBER, location);
			}
			if (problemEvent instanceof RangeProblemEvent) {
				RangeProblemEvent rangeProblemEvent = (RangeProblemEvent) problemEvent;

				attributes.put(MARKER_ATTR_COLUMN_NAME,
						rangeProblemEvent.getGuiltyColumnName());
				attributes.put(MARKER_ATTR_RANGE_MIN_VALUE,
						rangeProblemEvent.getMin());
				attributes.put(MARKER_ATTR_RANGE_MAX_VALUE,
						rangeProblemEvent.getMax());
				attributes.put(MARKER_ATTR_RANGE_PROBLEM, rangeProblemEvent
						.getRangeTypeProblem().toString());

			}
			if (problemEvent instanceof DuplicateRuleProblemEvent) {
				DuplicateRuleProblemEvent duplicateRulePE = (DuplicateRuleProblemEvent) problemEvent;
				attributes.put(MARKER_ATTR_DUPLICATE_RULEID,
						duplicateRulePE.getDuplicateRuleID());

			}
			if (problemEvent instanceof ConflictingActionsProblemEvent) {
				ConflictingActionsProblemEvent conflictingRulesPE = (ConflictingActionsProblemEvent) problemEvent;
				attributes.put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
						conflictingRulesPE.getProblemRuleId());

			}
			if (problemEvent instanceof UncoveredDomainEntryProblemEvent) {
				UncoveredDomainEntryProblemEvent domainEntryPE = (UncoveredDomainEntryProblemEvent) problemEvent;
				attributes.put(MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY,
						domainEntryPE.getUncoveredDomainValue());
				attributes.put(MARKER_ATTR_COLUMN_NAME,
						domainEntryPE.getColumnName());
			}
			marker.setAttributes(attributes);
		} else {
			if (problemEvent instanceof RuleCombinationProblemEvent) {
				IMarker marker = resource
						.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put(IMarker.MESSAGE, message);
				attributes.put(IMarker.SEVERITY, severity);
				if (location >= 0) {
					attributes.put(IMarker.LINE_NUMBER, location);
				}
				attributes.put(MARKER_ATTR_RULE_COMBINATION_PROBLEM, true);
				marker.setAttributes(attributes);
			} else if (problemEvent instanceof MissingEqualsCriterionProblemEvent) {
				IMarker marker = resource
						.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put(IMarker.MESSAGE, message);
				attributes.put(IMarker.SEVERITY, severity);
				if (location >= 0) {
					attributes.put(IMarker.LINE_NUMBER, location);
				}
				attributes.put(
						MARKER_ATTR_MISSING_EQUALS_CRITERION_PROBLEM, true);
				marker.setAttributes(attributes);
			} else if (problemEvent instanceof OverlappingRangeProblemEvent) {
				IMarker marker = resource
						.createMarker(ANALYZE_RULECOMB_MARKER_NAME);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put(IMarker.MESSAGE, message);
				attributes.put(IMarker.SEVERITY, severity);
				OverlappingRangeProblemEvent overlappingRulesPE = (OverlappingRangeProblemEvent) problemEvent;
				attributes.put(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID,
						overlappingRulesPE.getProblemRuleId());
				attributes.put(IMarker.LINE_NUMBER,
						overlappingRulesPE.getProblemRuleId());
				marker.setAttributes(attributes);
			} else {
				// for all generic types
				IMarker marker = resource
						.createMarker(ANALYZE_GENERAL_MARKER_NAME);
				Map<String, Object> attributes = new HashMap<String, Object>();
				if (problemEvent.getDetails() != null) {
					attributes.put(IMarker.MESSAGE, message + " [" + problemEvent.getDetails() + "]");
				} else {
					attributes.put(IMarker.MESSAGE, message);
				}
				attributes.put(IMarker.SEVERITY, severity);
				attributes.put(IMarker.LINE_NUMBER,
						problemEvent.getLocation());
				marker.setAttributes(attributes);
			}
		}
	}

	@Override
	public IFile getUnderlyingFile() {
		if (editor != null) {
			IEditorInput editorInput = editor.getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				return ((IFileEditorInput) editorInput).getFile();
			}
		}
		return null;
	}

	@Override
	public IAction getAnalyzeAction(DecisionTableAnalyzerView view) {
		return new AnalyzeAction(view);
	}

	@Override
	public IAction getShowCoverageAction(DecisionTableAnalyzerView view) {
		return new ShowCoverageAction(view);
	}

	@Override
	public IAction getGenerateTestDataAction(DecisionTableAnalyzerView view) {
		return null;
	}

	@Override
	public IAction getShowTestDataCoverageAction(DecisionTableAnalyzerView view) {
		return null;
	}

    @Override
    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(com.tibco.cep.studio.core.utils.ModelUtils.DATE_TIME_PATTERN);
    }
}