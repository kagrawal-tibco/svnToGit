package com.tibco.cep.studio.core.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.cep.studio.core.util.RefactoringUtils;

/**
 * This class wraps the StudioRefactoringParticipants during
 * rename operations
 * 
 * @author rhollom
 *
 */
public class StudioRenameParticipantWrapper extends RenameParticipant {

	private RefactoringParticipant[] fRefactoringParticipants;

	public StudioRenameParticipantWrapper() {
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange renameChange = new CompositeChange("Element pre-changes:");
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			Change change = refactoringParticipant.createPreChange(pm);
			if (change != null && change.getAffectedObjects() != null && change.getAffectedObjects().length > 0) {
				renameChange.add(change);
			}
		}
		if (renameChange.getChildren() != null && renameChange.getChildren().length > 0) {
			return renameChange;
		}
		return null;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			RefactoringStatus st = refactoringParticipant.checkConditions(pm, context);
			if (st != null) {
				status.merge(st);
			}
		}
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange renameChange = new CompositeChange("Element changes:");
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			Change change = refactoringParticipant.createChange(pm);
			if (change != null && change.getAffectedObjects() != null && change.getAffectedObjects().length > 0) {
				renameChange.add(change);
			}
		}
		if (renameChange.getChildren() != null && renameChange.getChildren().length > 0) {
			return renameChange;
		}
		return null;
	}

	@Override
	public String getName() {
		return "Wrapper Rename Participant";
	}

	@Override
	protected boolean initialize(Object element) {
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			refactoringParticipant.initialize(getProcessor(), element, getArguments());
		}
		return true;
	}

	private RefactoringParticipant[] getRefactoringParticipants() {
		if (fRefactoringParticipants == null) {
			fRefactoringParticipants = RefactoringUtils.getRefactoringParticipants();
		}
		return fRefactoringParticipants;
	}

}
