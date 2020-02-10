/**
 * 
 */
package com.tibco.cep.decision.table.validators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.decision.table.checkpoint.CheckpointEntryType;
import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.UndoableCommandCheckpointEntry;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.builder.StudioProjectBuilder;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant;
import com.tibco.decision.table.core.DecisionTableCorePlugin;

/**
 * @author aathalye
 *
 */
public class DecisionTableResourceSyntaxValidator extends DefaultResourceValidator {
	
	private static final String CLASS = DecisionTableResourceSyntaxValidator.class.getName();
		
	/**
	 * 
	 */
	public DecisionTableResourceSyntaxValidator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.IResourceValidator#canContinue()
	 */
	@Override
	public boolean canContinue() {
		return super.canContinue();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.IResourceValidator#enablesFor(org.eclipse.core.resources.IResource)
	 */
	@Override
	public boolean enablesFor(IResource resource) {
		return super.enablesFor(resource);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.IResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {
		//Wait for update jobs to complete to avoid race condition
		IndexUtils.waitForUpdate();
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		DesignerElement modelObj = getModelObject(resource);
		
		IProject project = resource.getProject();
		String projectName = project.getName();
		
		if (modelObj instanceof DecisionTableElement) {
			DecisionTableElement decisionTableElement = (DecisionTableElement)modelObj;
			Table table = (Table)decisionTableElement.getImplementation();
			if (!isValid(project, table.getImplements())) {
				reportProblem(resource, "Unable to resolve implemented Virtual Rule Function: [ "+table.getImplements()+" ]");
				return true;
			}
			DecisionTableErrorRecorder errorRecorder = 
				CommandFacade.getInstance().getErrorRecorder(projectName, table);			
			if (validationContext.getBuildType() == StudioProjectBuilder.FULL_BUILD) {
				errorRecorder.clear();
				checkTable(projectName, table, errorRecorder, true);
			} else {
				checkTable(projectName, table, errorRecorder, false);
			}
		}
		return true;
	}
	
	public static boolean isValid(IProject project, String implement) {
		IPath path = Path.fromOSString(implement + ".rulefunction");
		if (!project.getFile(path).exists()) {
			// check whether this is a project library element
			DesignerElement element = IndexUtils.getElement(project.getName(), implement);
			if (element instanceof SharedRuleElement) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	private List<UndoableCommandCheckpointEntry<IExecutableCommand>> getCheckpointDeltaObjects(String project,
			                                       Table tableEModel) {
		return CommandFacade.getInstance().getCheckpointDeltaObjects(project, tableEModel);
	}
	
	private void clearAssociatedErrors(List<?> deltaObjects, DecisionTableErrorRecorder errorRecorder) {
		for (Object affectedObject : deltaObjects) {
			if (affectedObject instanceof TableRule) {
				TableRule tableRule = (TableRule)affectedObject;
				for (TableRuleVariable condition : tableRule.getCondition()) {
					DecisionTableSyntaxProblemParticipant participant = 
							new DecisionTableSyntaxProblemParticipant(condition);
					participant.setContainingRule(tableRule);
					errorRecorder.clearError(participant);
				}
				for (TableRuleVariable action : tableRule.getAction()) {
					DecisionTableSyntaxProblemParticipant participant = 
							new DecisionTableSyntaxProblemParticipant(action);
					participant.setContainingRule(tableRule);
					errorRecorder.clearError(participant);
				}
			} else if (affectedObject instanceof TableRuleVariable) {
				TableRuleVariable trv = (TableRuleVariable)affectedObject;
				DecisionTableSyntaxProblemParticipant participant = 
					new DecisionTableSyntaxProblemParticipant(trv);
				errorRecorder.clearError(participant);
			}
		}
	}
	
	private void checkTable(String projectName,
			                Table tableEModel,
			                DecisionTableErrorRecorder errorRecorder,
			                boolean doFullBuild) {
		
		List<UndoableCommandCheckpointEntry<IExecutableCommand>> checkpointDeltaEntries = 
			getCheckpointDeltaObjects(projectName, tableEModel);
		if (checkpointDeltaEntries.isEmpty() || doFullBuild) {
			DecisionTableCorePlugin.debug(CLASS, "Validating entire table {0}", tableEModel.getName());
			DTValidator.checkTable(tableEModel, projectName, errorRecorder);
			errorRecorder.reportAllErrors();
		} else {
			DecisionTableCorePlugin.debug(CLASS, "Validating delta table {0}", tableEModel.getName());
			List<DecisionTableSyntaxProblemParticipant> participants = 
				new ArrayList<DecisionTableSyntaxProblemParticipant>(checkpointDeltaEntries.size());
			for (ICheckpointDeltaEntry<IExecutableCommand> deltaEntry : checkpointDeltaEntries) {
				IExecutableCommand command = deltaEntry.getCommand();
				List<?> deltaObjects = command.getAffectedObjects();
				
				if (deltaEntry.getCheckpointEntryType() == CheckpointEntryType.DELETE) {
					DecisionTableCorePlugin.debug(CLASS, "Entry has been removed. No validation required");
					//Clear the error if there is one
					clearAssociatedErrors(deltaObjects, errorRecorder);
					continue;
				}
				//No
				//errorRecorder.clear();
	
				for (Object deltaObject : deltaObjects) {
					DecisionTableCorePlugin.debug(CLASS, "Validating {0}", deltaObject);
					if (deltaObject instanceof TableRuleVariable) {
						TableRuleVariable trv = (TableRuleVariable)deltaObject;
						DecisionTableSyntaxProblemParticipant participant = 
							new DecisionTableSyntaxProblemParticipant(trv);
						if (!participants.contains(participant)) {
							DTValidator.checkTableRuleVariable(trv, 
									tableEModel, 
									projectName, 
									errorRecorder);
							participants.add(participant);
						}
					}
					if (deltaObject instanceof TableRule) {
						TableRule tableRule = (TableRule)deltaObject;
						checkTable(tableRule, tableEModel, projectName, 
								errorRecorder, participants);
					}
				}
			}
			errorRecorder.reportAllErrors();
		}
	}
	
	private void checkTable(TableRule tableRule, 
			                Table tableEModel, 
			                String projectName, 
			                DecisionTableErrorRecorder errorRecorder,
			                List<DecisionTableSyntaxProblemParticipant> participants) { 
		for (TableRuleVariable condition : tableRule.getCondition()) {
			DecisionTableSyntaxProblemParticipant participant = 
				new DecisionTableSyntaxProblemParticipant(condition);
			if (!participants.contains(participant)) {
				DTValidator.checkTableRuleVariable(condition, 
						tableEModel, 
						projectName, 
						errorRecorder);
				participants.add(participant);
			}
		}
		for (TableRuleVariable action : tableRule.getAction()) {
			DecisionTableSyntaxProblemParticipant participant = 
				new DecisionTableSyntaxProblemParticipant(action);
			if (!participants.contains(participant)) {
				DTValidator.checkTableRuleVariable(action, 
						tableEModel, 
						projectName, 
						errorRecorder);
				participants.add(participant);
			}
		}
	}
}
