package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.core.util.CommonUtil.updateStateMachineRuleSymbols;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.EntitySearchParticipant;
import com.tibco.cep.studio.core.search.StateMachineSearchParticipant;

public class StateMachineRefactoringParticipant extends EntityRefactoringParticipant<StateMachine> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.STATEMACHINE_EXTENSION };

	public StateMachineRefactoringParticipant() {
		super();
	}

	@Override
	protected EntitySearchParticipant getSearchParticipant() {
		return new StateMachineSearchParticipant();
	}

	@Override
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
		}

		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (isSupportedResource(file)) {
			return super.checkConditions(pm, context);
		}
		return new RefactoringStatus();
	}
	
	@Override
	protected StateMachine preProcessEntityChange(StateMachine refactorParticipant) {
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processStateMachine(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return refactorParticipant;
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException,
			OperationCanceledException {
//		if (isDeleteRefactor()) {
//			return new NullChange();
//		}
		// look through all state machines and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("State Model changes:");
		List<Entity> allStateMachines = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.STATE_MACHINE });
		for (Entity entity : allStateMachines) {
			if (entity == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processStateMachine(projectName, compositeChange, (StateMachine) entity, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	private StateMachine processStateMachine(String projectName,
			CompositeChange compositeChange, StateMachine stateMachine, Object elementToRefactor, boolean preChange) throws CoreException {
		if (isSharedElement(stateMachine)) {
			return stateMachine;
		}

		CompilableRenameParticipant compilableParticipant = new CompilableRenameParticipant(isMoveRefactor(), getOldElementName(), getOldElementPath());

		// must be sure to copy the concept before making changes, otherwise canceling the wizard will keep the changes made and corrupt the concepts
		IFile file = IndexUtils.getFile(projectName, stateMachine);
		stateMachine = (StateMachine) EcoreUtil.copy(stateMachine);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return stateMachine;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, stateMachine, getNewElementName());
			changed = true;
		}

		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof RuleElement) {
			elementToRefactor = ((RuleElement) elementToRefactor).getRule();
		}
		if (processFolder(stateMachine)) {
			changed = true;
		}
		if (elementToRefactor instanceof Entity || isFolderRefactor()) {
			String newFullPath = getNewFullPath(elementToRefactor);

			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(stateMachine.getOwnerConceptPath(), folder)) {
					String newPath = getNewPath(stateMachine.getOwnerConceptPath(), folder);
					stateMachine.setOwnerConceptPath(newPath);
					changed = true;
				}
			} else {
				Entity refactoredElement = (Entity) elementToRefactor;
				if (/*oldFullPath*/refactoredElement.getFullPath().equals(stateMachine.getOwnerConceptPath())) {
					if (isDeleteRefactor()){
						stateMachine.setOwnerConceptPath(null);
						updateStateMachineRuleSymbols(stateMachine, projectName);
						changed = true;
					}else{
						stateMachine.setOwnerConceptPath(newFullPath);
						changed = true;
					}
				}
			}

			Object obj = elementToRefactor;
//			if (isFolderRefactor()) {
//				obj = getResource();
//			}
			
			if (!isDeleteRefactor()){
				// need to change all scopes that contain this concept
				EList<StateTransition> stateTransitions = stateMachine.getStateTransitions();
				for (StateTransition stateTransition : stateTransitions) {
					if (compilableParticipant.processRule(stateTransition.getGuardRule(), obj, getNewElementPath(), getNewElementName())) {
						changed = true;
					}
					if (stateTransition.getOwnerStateMachinePath() != null && elementToRefactor instanceof StateMachine) {
						StateMachine sm = (StateMachine) elementToRefactor;
						if (stateTransition.getOwnerStateMachinePath().equals(sm.getFullPath())) {
							stateTransition.setOwnerStateMachinePath(newFullPath);
						}
					}
				}
				if (processStateEntity(stateMachine, obj, getNewElementPath(), getNewElementName())) {
					changed = true;
				}
			}
		}

		if (changed) {
			Change change = createTextFileChange(file, stateMachine);
			compositeChange.add(change);
		}

		return stateMachine;
	}

	protected String getNewFullPath(Object elementToRefactor) {
		String newFullPath = "";
		if (elementToRefactor instanceof Entity) {
			Entity refactoredElement = (Entity) elementToRefactor;
			if (isRenameRefactor()) {
				newFullPath = refactoredElement.getFolder() + getNewElementName();
			} else if (isMoveRefactor()){
				newFullPath = getNewElementPath() + refactoredElement.getName();
			}
		}
		return newFullPath;
	}

	private boolean processStateEntity(StateEntity stateEntity, Object entity, String newFolder, String newName) {
		CompilableRenameParticipant compilableParticipant = new CompilableRenameParticipant(isMoveRefactor(), getOldElementName(), getOldElementPath());
		boolean changed = false;
		
		String newFullPath = "";
		if (entity instanceof StateMachine) {
			if (isRenameRefactor()) {
				newFullPath = getOldElementPath() + newName;
			} else if (isMoveRefactor()){
				newFullPath = newFolder + getOldElementName();
			}
			stateEntity.setOwnerStateMachinePath(newFullPath);
			if (stateEntity.getOwnerStateMachinePath() != null) {
				changed = true;
			}
		}
		if (stateEntity instanceof State) {
			State state = (State) stateEntity;
			if (compilableParticipant.processRule(state.getEntryAction(), entity, newFolder, newName)) changed = true;
			if (compilableParticipant.processRule(state.getExitAction(), entity, newFolder, newName)) changed = true;
			if (compilableParticipant.processRule(state.getInternalTransitionRule(), entity, newFolder, newName)) changed = true;
			if (compilableParticipant.processRule(state.getTimeoutAction(), entity, newFolder, newName)) changed = true;
			if (compilableParticipant.processRuleFunction(state.getTimeoutExpression(), entity, newFolder, newName)) changed = true;
		} 
		if (stateEntity instanceof StateComposite) {
			EList<StateEntity> stateEntities = ((StateComposite) stateEntity).getStateEntities();
			for (StateEntity se : stateEntities) {
				if (processStateEntity(se, entity, newFolder, newName)) changed = true;
			}
			EList<StateComposite> regions = ((StateComposite) stateEntity).getRegions();
			for (StateComposite region : regions) {
				if (processStateEntity(region, entity, newFolder, newName)) changed = true;
			}
		}
		if (stateEntity instanceof StateSubmachine) {
			StateSubmachine subMach = (StateSubmachine) stateEntity;
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(subMach.getURI(), folder, true)) {
					String newPath = getNewPath(subMach.getURI(), folder);
					subMach.setURI(newPath);
					changed = true;
				}
			} else {
				Entity refactoredElement = (Entity) entity;
				newFullPath = "";
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()) {
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
				if (refactoredElement.getFullPath().equals(subMach.getOwnerStateMachinePath())) {
					subMach.setOwnerStateMachinePath(newFullPath);
				}
				if (refactoredElement.getFullPath().equals(subMach.getURI())) {
					subMach.setURI(newFullPath);
					changed = true;
				}
			}
		}
		return changed;
	}

	@Override
	protected void processChildren(String newName, EObject eobj, String newProjName) {
		super.processChildren(newName, eobj, newProjName);
		if(eobj.eClass().getEStructuralFeature("ownerStateMachinePath") != null) {
			if (eobj.eIsSet(eobj.eClass().getEStructuralFeature("ownerStateMachinePath"))) {
				StateMachine container = getContainer(eobj);
				if (container != null) {
					String newFullPath = container.getFullPath();
					((StateEntity)eobj).setOwnerStateMachinePath(newFullPath);
				}
			}
		} else if (eobj instanceof Rule) {
			StateMachine container = getContainer(eobj);
			if (container != null) {
				String newFullPath = container.getFullPath();
				((Rule) eobj).setFolder(newFullPath);
			}
		}
	}

	private StateMachine getContainer(EObject container) {
		while (container!= null) {
			if (container instanceof StateMachine) {
				return (StateMachine) container;
			}
			container = container.eContainer();
		}
		return null;
	}
}
