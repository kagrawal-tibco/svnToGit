package com.tibco.cep.studio.core.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;

import com.tibco.cep.studio.core.util.RefactoringUtils;

/**
 * This class wraps the StudioRefactoringParticipants during
 * delete operations
 * 
 * @author rhollom
 *
 */
public class StudioDeleteParticipantWrapper extends DeleteParticipant {

	private RefactoringParticipant[] fRefactoringParticipants;

	public StudioDeleteParticipantWrapper() {
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange delChange = new CompositeChange("Element pre-changes:");
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			Change change = refactoringParticipant.createPreChange(pm);
			if (change != null && change.getAffectedObjects() != null && change.getAffectedObjects().length > 0) {
				delChange.add(change);
			}
		}
		if (delChange.getChildren() != null && delChange.getChildren().length > 0) {
			return delChange;
		}
		return null;
	}

	private RefactoringParticipant[] getRefactoringParticipants() {
		if (fRefactoringParticipants == null) {
			fRefactoringParticipants = RefactoringUtils.getRefactoringParticipants();
		}
		return fRefactoringParticipants;
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
		CompositeChange delChange = new CompositeChange("Element changes:");
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			Change change = refactoringParticipant.createChange(pm);
			if (change != null && change.getAffectedObjects() != null && change.getAffectedObjects().length > 0) {
				delChange.add(change);
			}
		}
		if (delChange.getChildren() != null && delChange.getChildren().length > 0) {
			return delChange;
		}
		return null;
	}

	@Override
	public String getName() {
		return "Wrapper Delete Participant";
	}

	@Override
	protected boolean initialize(Object element) {
		fRefactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : fRefactoringParticipants) {
			refactoringParticipant.initialize(getProcessor(), element, getArguments());
		}
		return true;
	}

}
