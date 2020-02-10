/**
 * 
 */
package com.tibco.cep.studio.core.validation.dt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.utils.ModelUtils.DecisionTableUtils.TableRuleInfo;

/**
 * @author aathalye
 *
 */
public class DelegatingRuleErrorsRecorder implements DecisionTableErrorRecorder {
	
	private IMarkerErrorRecorder<DecisionTableSyntaxProblemParticipant> delegateErrorRecorder;
	
	private List<DecisionTableSyntaxProblemParticipant> errorSources;
	
	public DelegatingRuleErrorsRecorder(IMarkerErrorRecorder<DecisionTableSyntaxProblemParticipant> delegateErrorRecorder) {
		this.delegateErrorRecorder = delegateErrorRecorder;
		errorSources = new ArrayList<DecisionTableSyntaxProblemParticipant>();
		IMarker[] existingMarkers = delegateErrorRecorder.getExistingMarkers();
		if (existingMarkers != null) {
			createExistingEntries(existingMarkers);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.RuleErrorRecorder#reportError(com.tibco.be.parser.RuleError, com.tibco.cep.decision.table.model.dtmodel.Table, com.tibco.cep.decision.table.model.dtmodel.TableRuleSet, com.tibco.cep.decision.table.model.dtmodel.TableRule, com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable)
	 */
	public void reportError(RuleError rulErr, Table table, TableRuleSet trs,
			TableRule row, TableRuleVariable col) {
		DecisionTableSyntaxProblemParticipant participant = 
			new DecisionTableSyntaxProblemParticipant(col);
		int index = -1;
		if ((index = errorSources.indexOf(participant)) != -1) {
			//errorSources.add(participant);
			DecisionTableSyntaxProblemParticipant existingParticipant = 
				errorSources.get(index);
			//Clear the previous error on this since there is a new error
			delegateErrorRecorder.clearError(existingParticipant);
			//participant.setTableEModel(table);
			existingParticipant.setRuleError(rulErr);
			//participant.setTrs(trs);
			//participant.setContainingRule(row);
		} else {
			//It is a new entry
			errorSources.add(participant);
			participant.setTableEModel(table);
			participant.setRuleError(rulErr);
			participant.setTrs(trs);
			participant.setContainingRule(row);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.RuleErrorRecorder#reportError(com.tibco.be.parser.RuleError)
	 */
	public void reportError(RuleError rulErr) {
		// TODO Auto-generated method stub

	}
	
	private void createExistingEntries(IMarker[] existingMarkers) {
		for (IMarker marker : existingMarkers) {
			IResource resource = marker.getResource();
			//Get location
			String locationId = marker.getAttribute(IMarker.LOCATION, "-1");
			if (locationId != null) {
				DecisionTableElement decisionTableElement = 
					(DecisionTableElement)IndexUtils.getElement(resource);
				if (decisionTableElement != null) {
					Table table = (Table)decisionTableElement.getImplementation();
					//Get Table rule set
					try {
						String trvId = 
							(String)marker.getAttribute(IMarkerErrorRecorder.MARKER_TRV_ATTR);
						String errMessage = 
							(String)marker.getAttribute(IMarker.MESSAGE);
						TableRuleInfo tableRuleInfo = getTableRuleInfo(trvId, table);
						if (tableRuleInfo != null) {
							TableRule tableRule = tableRuleInfo.getTableRule();
							TableRuleSet trs = tableRuleInfo.getTrs();
							TableRuleVariable trv = 
								getTableRuleVariable(trvId, tableRule);
							DecisionTableSyntaxProblemParticipant syntaxProblemParticipant = 
								new DecisionTableSyntaxProblemParticipant(trv);
							syntaxProblemParticipant.setTrs(trs);
							syntaxProblemParticipant.setTableEModel(table);
							syntaxProblemParticipant.setContainingRule(tableRule);
							RuleError ruleError = new RuleError(errMessage, RuleError.SYNTAX_TYPE);
							syntaxProblemParticipant.setRuleError(ruleError);
							errorSources.add(syntaxProblemParticipant);
						}
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clear()
	 */
	public void clear() {
		//Clear the errors in the delegate recorder
		delegateErrorRecorder.clear();
		errorSources.clear();
	}
	
	private TableRuleInfo getTableRuleInfo(String trvId, Table tableEModel) {
		String containingRuleId = 
			ModelUtils.DecisionTableUtils.getContainingRuleId(trvId);
		return ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(containingRuleId, tableEModel);
	}
	
	private TableRuleVariable getTableRuleVariable(String trvId, TableRule tableRule) {
		TableRuleVariable trv = null;
		for (TableRuleVariable condition : tableRule.getCondition()) {
			if (condition.getId().equals(trvId)) {
				trv = condition;
				return trv;
			}
		}
		for (TableRuleVariable action : tableRule.getAction()) {
			if (action.getId().equals(trvId)) {
				trv = action;
				return trv;
			}
		}
		return trv;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#reportAllErrors()
	 */
	public void reportAllErrors() {
		for (DecisionTableSyntaxProblemParticipant participant : errorSources) {
			RuleError ruleError = participant.getRuleError();
			Table tableEModel = participant.getTableEModel();
			TableRuleSet trs = participant.getTrs();
			TableRule containingRule = participant.getContainingRule();
			TableRuleVariable source = participant.getTableRuleVariable();
			if (trs != null && source != null) {
				delegateErrorRecorder.reportError(ruleError, tableEModel, trs, containingRule, source);
			} else {
				delegateErrorRecorder.reportError(ruleError);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.RuleErrorRecorder#clearError(com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant)
	 */
	public void clearError(
			DecisionTableSyntaxProblemParticipant problemParticipant) {
		//Check if it is present in error sources
		if (errorSources.contains(problemParticipant)) {
			delegateErrorRecorder.clearError(problemParticipant);
			//Remove it from that source
			errorSources.remove(problemParticipant);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder#clearError(com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable, com.tibco.cep.decision.table.model.dtmodel.TableRule)
	 */
	public void clearError(TableRuleVariable problemTableRuleVar,
			               TableRule containingRule) {
		DecisionTableSyntaxProblemParticipant problemParticipant = 
			new DecisionTableSyntaxProblemParticipant(problemTableRuleVar);
		problemParticipant.setContainingRule(containingRule);
		clearError(problemParticipant);
	}
}
