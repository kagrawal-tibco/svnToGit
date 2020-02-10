/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import static com.tibco.cep.studio.decision.table.constraintpane.DecisionTableAnalyzerConstants.ANALYZE_FIXABLE_MARKER_NAME;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
//import com.jidesoft.decision.DecisionRule;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * Provide auto resolution for problems reported by decision table analyzer.
 * @author aathalye
 *
 */
public class DecisionTableAnalysisProblemResolution extends WorkbenchMarkerResolution implements
		IMarkerResolution {
	
	private List<String> newRowIndexes;	
	
	private IDecisionTableEditor decisionTableEditor;
	
	private AnalysisResolutionQueue resolutionQueue;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.WorkbenchMarkerResolution#findOtherMarkers(org.eclipse.core.resources.IMarker[])
	 */
	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		List<IMarker> someMarkers = new ArrayList<IMarker>(markers.length);
		for (IMarker marker : markers) {
			try {
				if (ANALYZE_FIXABLE_MARKER_NAME == marker.getType().intern()) {
					if (!someMarkers.contains(marker)) {
						someMarkers.add(marker);
					}
				}
			} catch (CoreException e) {
				DecisionTableUIPlugin.log(e);
			}
		}
		return someMarkers.toArray(new IMarker[someMarkers.size()]);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	@Override
	public String getDescription() {
		String desc = "This will add missing conditions based on the type of the problem to the decision table";
		return desc;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 */
	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.WorkbenchMarkerResolution#run(org.eclipse.core.resources.IMarker[], org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IMarker[] markers, IProgressMonitor monitor) {
		resolutionQueue = new AnalysisResolutionQueue(markers.length);
		newRowIndexes = new ArrayList<String>(markers.length);
		super.run(markers, monitor);
		//Process queued tasks
		try {
		//	processResolutionQueue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		//TODO get nattable highlight rows accordingly
		/*JTable mainTable = 
			decisionTableEditor.getDesignViewer().getDecisionTablePane().getTableScrollPane().getMainTable();
		DecisionTableAnalyzerUtils.highlightRows(mainTable, newRowIndexes.toArray(new String[newRowIndexes.size()]));*/
	}



	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Fix This problem";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void run(IMarker marker) {
		try {
			//Presumably it is the same editor we are working on
			//Hence it safe to do the operation below
			if (decisionTableEditor == null) {
				decisionTableEditor = openEditor(marker);
			}
		//	queueTask(marker);
		} catch (Exception e) {
			DecisionTableUIPlugin.log(e);
		}
	}
	
/*	*//**
	 * @param columnName
	 * @param decisionDataModel
	 * @return
	 *//*
	private DecisionField getFieldForColumn(String columnName, 
			                                DecisionDataModel decisionDataModel) {
		if (columnName != null) {
			//Search
			List<DecisionField> conditionFields = decisionDataModel.getConditionFields();
			for (DecisionField conditionField : conditionFields) {
				if (conditionField.getName().equals(columnName)) {
					return conditionField;
				}
			}
		}
		return null;
	}
	
	*//**
	 * Queue this resolution task to be processed later
	 * @param marker
	 * @throws CoreException
	 *//*
	private void queueTask(IMarker marker) throws CoreException {
		if (!marker.exists()) {
			return;
		}
		processRangeProblem(marker);
		processConflictingActionsProblem(marker);
		processUncoveredDomainEntryProblem(marker);
	}
	
	*//**
	 * @param marker
	 * @throws CoreException
	 *//*
	private void processRangeProblem(IMarker marker) throws CoreException {
		String rangeProblemTypeString = 
			(String)marker.getAttribute(MARKER_ATTR_RANGE_PROBLEM);
		if (rangeProblemTypeString != null) {
			//An Add task
			AnalysisResolutionTask resolutionTask = 
				new AnalysisResolutionTask(AnalysisResolutionTaskType.ADD, marker);
			resolutionQueue.addTask(resolutionTask);
		}
	}
	
	*//**
	 * @param marker
	 * @throws CoreException
	 *//*
	private void processConflictingActionsProblem(IMarker marker) throws CoreException {
		Integer duplicateRuleID = 
			(Integer)marker.getAttribute(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID);
		if (duplicateRuleID != null) {
			//A Delete task
			AnalysisResolutionTask resolutionTask = 
				new AnalysisResolutionTask(AnalysisResolutionTaskType.DELETE, marker);
			resolutionQueue.addTask(resolutionTask);
		}
	}
	
	*//**
	 * @param marker
	 * @throws CoreException
	 *//*
	private void processUncoveredDomainEntryProblem(IMarker marker) throws CoreException {
		String domainEntry = 
			(String)marker.getAttribute(MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY);
		if (domainEntry != null) {
			//A Delete task
			AnalysisResolutionTask resolutionTask = 
				new AnalysisResolutionTask(AnalysisResolutionTaskType.ADD, marker);
			resolutionQueue.addTask(resolutionTask);
		}
	}
	
	*//**
	 * @throws CoreException
	 *//*
	private void processResolutionQueue() throws Exception {
		//Get model object
		DecisionTableModelManager decisionTableModelManager = 
			decisionTableEditor.getDecisionTableModelManager();
		DecisionDataModel decisionDataModel = decisionTableModelManager.getDtTableModel();
		
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		
		String project = editorInput.getProjectName();
		Table eModel = editorInput.getTableEModel();
		
		
		while (resolutionQueue.size() > 0) {
			AnalysisResolutionTask resolutionTask = resolutionQueue.poll();
			IMarker marker = resolutionTask.getMarker();
			
			switch (resolutionTask.getTaskType()) {
			
			case ADD : {
				int ruleNumber = createNewRangeCondition(project,
						 								 eModel,	
						                                 decisionDataModel, 
                                                         marker);
				if (ruleNumber == -1) {
					ruleNumber = createNewUncoveredEntryCondition(project,
							 eModel,	
                             decisionDataModel, 
                             marker);
				}
				if (ruleNumber != -1) {
					newRowIndexes.add(Integer.toString(ruleNumber));
				}
				break;
			}
			case DELETE :
				removeConflictingRules(project,
						               eModel,
						               decisionDataModel,
						               resolutionTask);
				break;
			}
			//Delete the marker
			marker.delete();
		}
	}
	
	private DecisionRule createNewRule(String project,
									   Table tableEModel,
			                           DecisionDataModel decisionDataModel) {
		ICommandCreationListener<AddCommand<TableRule>, TableRule> listener = 
			new DecisionTableRemoveRowCommandListener(decisionDataModel, tableEModel, TableTypes.DECISION_TABLE);
		int ruleIndex = 
			(Integer)CommandFacade.getInstance().executeAddRow(project, tableEModel, TableTypes.DECISION_TABLE, listener);
		int ruleCount = decisionDataModel.getRuleCount();
		if (ruleIndex < ruleCount) {
			return decisionDataModel.getRule(ruleIndex);
		}
		return null;
	}
	
	*//**
	 * @param project
	 * @param tableEModel
	 * @param commandReceiver
	 * @param columnName
	 * @param decisionRule
	 * @param changedExpression
	 *//*
	private TableRuleVariable modifyCell(String project,
							             Table tableEModel,
							             TableRuleVariable commandReceiver,
							             String columnName,
							             DecisionRule decisionRule,
							             String changedExpression) {
		TableRuleVariable oldValue = commandReceiver;
		//Create a new one
		TableRuleVariable newTRV = DtmodelFactory.eINSTANCE.createTableRuleVariable();
		//Set blank expression. Mandatory
		newTRV.setExpr("");
		//Set Its id
		int decisionRuleID = decisionRule.getId();
		DecisionTableAnalyzerUtils.setIdForQuickFix(newTRV, 
				columnName, tableEModel, decisionRuleID);
		CommandFacade.getInstance().executeModify(project, 
				                                  tableEModel, 
				                                  TableTypes.DECISION_TABLE, 
				                                  newTRV, 
				                                  oldValue, 
				                                  new ModifyCommandExpression(changedExpression));
		//This new condition entry has to be added to backend model
		//as that is not taken care of by the above command.
		//Search TableRule with this id
		TableRuleInfo tableRuleInfo = 
			ModelUtils.DecisionTableUtils.getTableRuleInfoFromId(Integer.toString(decisionRuleID), tableEModel.getDecisionTable());
		if (tableRuleInfo != null) {
			TableRule tableRule = tableRuleInfo.getTableRule();
			ICommandCreationListener<AddCommand<TableRuleVariable>, TableRuleVariable> listener = 
				new DecisionTableCellAdditionCommandListener(newTRV, ColumnType.CONDITION);
			CommandFacade.getInstance().executeAddCell(project, 
					                                   tableEModel, 
					                                   tableRule, 
					                                   TableTypes.DECISION_TABLE, 
					                                   listener);
		}
		return newTRV;
	}
	
	*//**
	 * @param decisionDataModel
	 * @param conditionField
	 * @param rangeTypeProblem
	 * @param min
	 * @param max
	 *//*
	private int createNewRangeCondition(String project,
										Table tableEModel,
			                            DecisionDataModel decisionDataModel,
			                            IMarker marker) throws CoreException {
		if (!marker.exists()) {
			return -1;
		}
		String columnName = 
			(String)marker.getAttribute(MARKER_ATTR_COLUMN_NAME);
		String rangeProblemTypeString = 
			(String)marker.getAttribute(MARKER_ATTR_RANGE_PROBLEM);
		if (rangeProblemTypeString == null) {
			return -1;
		}
		RANGE_TYPE_PROBLEM rangeTypeProblem = RANGE_TYPE_PROBLEM.valueOf(rangeProblemTypeString);
		Integer min = (Integer)marker.getAttribute(MARKER_ATTR_RANGE_MIN_VALUE);
		Integer max = (Integer)marker.getAttribute(MARKER_ATTR_RANGE_MAX_VALUE);
		
		//Get field
		DecisionField conditionField = getFieldForColumn(columnName, decisionDataModel);
		
		DecisionRule decisionRule = createNewRule(project, tableEModel, decisionDataModel);
		if (decisionRule == null) {
			return -1;
		}
		DecisionEntry conditionEntry = new DecisionEntry(conditionField);
		decisionRule.addCondition(conditionEntry);
		String value = null;
		TableRuleVariable trv = DtmodelFactory.eINSTANCE.createTableRuleVariable();
		switch (rangeTypeProblem) {
		
		case RANGE_GREATER_THAN_PROBLEM : 
			value = ">= " + min;
			break;
		case RANGE_LESS_THAN_PROBLEM :
			value = "<= " + min;
			break;
		case RANGE_BETWEEN_PROBLEM :
			value = "> " + min + " && < " + max;
			break;
		}
		//Creating new condition
		trv.setExpr("");
		TableRuleVariable newTRV = 
			modifyCell(project, tableEModel, trv, columnName, decisionRule, value);
		conditionEntry.setValue(newTRV);
		return decisionDataModel.getRules().indexOf(decisionRule);
	}
	
	*//**
	 * Create a new condition based on an uncovered domain entry
	 * @param decisionDataModel
	 * @param marker
	 * @return
	 * @throws CoreException
	 *//*
	private int createNewUncoveredEntryCondition(String project,
			                                     Table tableEModel,
			                                     DecisionDataModel decisionDataModel,
                                                 IMarker marker) throws CoreException {
		if (!marker.exists()) {
			return -1;
		}
		String columnName = 
			(String)marker.getAttribute(MARKER_ATTR_COLUMN_NAME);
		String uncoveredDomainEntry = 
			(String)marker.getAttribute(MARKER_ATTR_UNCOVERED_DOMAIN_ENTRY);
		if (uncoveredDomainEntry == null) {
			return -1;
		}
		//Get field
		DecisionField conditionField = getFieldForColumn(columnName, decisionDataModel);
		DecisionRule decisionRule = createNewRule(project, tableEModel, decisionDataModel);
		DecisionEntry conditionEntry = new DecisionEntry(conditionField);
		decisionRule.addCondition(conditionEntry);
		TableRuleVariable trv = DtmodelFactory.eINSTANCE.createTableRuleVariable();
		//Create a blank expression
		trv.setExpr("");
		TableRuleVariable newTRV = 
			modifyCell(project, tableEModel, trv, columnName, decisionRule, uncoveredDomainEntry);
		conditionEntry.setValue(newTRV);
		return decisionDataModel.getRules().indexOf(decisionRule);
	}
	
	*//**
	 * Remove all Conflicting rules in one shot
	 * @param decisionDataModel
	 * @param marker
	 * @throws CoreException
	 *//*
	private void removeConflictingRules(String project,
                                        Table tableEModel,
                                        DecisionDataModel decisionDataModel,
			                            AnalysisResolutionTask headTask) throws Exception {
		//Get marker of head Task
		IMarker headMarker = headTask.getMarker();
		List<DecisionRule> removalRules = new ArrayList<DecisionRule>();
		//Get rule matching this
		DecisionRule rule = getRuleAtIndex(decisionDataModel, headMarker);
		
		if (rule != null) {
			removalRules.add(rule);
			headMarker.delete();
		}
		//Check if it has child tasks
		Iterator<AnalysisResolutionTask> children = headTask.getChildren();
		while (children != null && children.hasNext()) {
			AnalysisResolutionTask childTask = children.next();
			IMarker childMarker = childTask.getMarker();
			DecisionRule tempRule = 
				getRuleAtIndex(decisionDataModel, childMarker);
			if (tempRule != null) {
				removalRules.add(tempRule);
				childMarker.delete();
			}
		}
		
		ICommandCreationListener<RemoveCommand<TableRule>, TableRule> listener = 
			new DecisionTableRowRemovalCommandListener(decisionDataModel, tableEModel,
					 removalRules.toArray(new DecisionRule[removalRules.size()]));
	
		CommandFacade.getInstance().executeRemoval(project, tableEModel, TableTypes.DECISION_TABLE, listener);
	}
	
	*//**
	 * An implementation of {@link SwingWorker} intended to
	 * execute retrieval of rule from the UI model in a swing thread.
	 * <p>
	 * This thread blocks till the output is received.
	 * </p>
	 * @author aathalye
	 *
	 *//*
	class DecisionRuleFetchWorker extends SwingWorker<DecisionRule, Object> {
		
		private int duplicateRuleId;
		
		private DecisionDataModel decisionDataModel;
		
		DecisionRuleFetchWorker(int duplicateRuleId, DecisionDataModel decisionDataModel) {
			this.duplicateRuleId = duplicateRuleId;
			this.decisionDataModel = decisionDataModel;
		}
		 (non-Javadoc)
		 * @see javax.swing.SwingWorker#doInBackground()
		 
		@Override
		protected DecisionRule doInBackground() throws Exception {
			int ruleIndex = decisionDataModel.getRowIndex(duplicateRuleId);
			//Check if this id exists
			//For index position
			//duplicateID = duplicateID - 1;
			return decisionDataModel.getRule(ruleIndex);
		}

	}
	
	*//**
	 * Get {@linkplain DecisionRule} for index specified by the marker
	 * @param decisionDataModel
	 * @param marker
	 * @return
	 * @throws CoreException
	 *//*
	private DecisionRule getRuleAtIndex(DecisionDataModel decisionDataModel, 
			                            IMarker marker) throws Exception {
		if (marker.exists()) {
			Integer problemRuleID = 
				(Integer)marker.getAttribute(MARKER_ATTR_CONFLICTING_ACTIONS_RULEID);
			if (problemRuleID != null) {
				int duplicateID = problemRuleID;
				//Find the actual index in the model
				DecisionRuleFetchWorker ruleWorker = 
					new DecisionRuleFetchWorker(duplicateID, decisionDataModel);
				ruleWorker.execute();
				return ruleWorker.get();
			}
		}
		return null;
	}*/
	
	private IDecisionTableEditor openEditor(IMarker marker) throws Exception {
		IWorkbenchPage workbenchPage = 
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IDecisionTableEditor decisionTableEditor = 
			(IDecisionTableEditor)IDE.openEditor(workbenchPage, marker);
		return decisionTableEditor;
	}
}
