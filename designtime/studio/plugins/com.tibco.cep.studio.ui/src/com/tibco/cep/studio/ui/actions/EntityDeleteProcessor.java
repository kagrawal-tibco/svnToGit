package com.tibco.cep.studio.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteArguments;
import org.eclipse.ltk.core.refactoring.participants.DeleteProcessor;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.IRefactoringContext;
import com.tibco.cep.studio.core.util.RefactoringUtils;

public class EntityDeleteProcessor extends DeleteProcessor {

	private IRefactoringContext fContext;
	private DeleteArguments fArguments;
	private RefactoringParticipant[] fRefactoringParticipants;

	public EntityDeleteProcessor(IRefactoringContext context) {
		super();
		this.fContext = context;
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		fArguments = new DeleteArguments();
		RefactoringStatus status = new RefactoringStatus();
		for (RefactoringParticipant refactoringParticipant : fRefactoringParticipants) {
			RefactoringStatus st = refactoringParticipant.checkConditions(pm, context);
			if (st != null) {
				status.merge(st);
			}
		}
		return status;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange delChange = new CompositeChange("Element pre-changes:");
		for (RefactoringParticipant refactoringParticipant : fRefactoringParticipants) {
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

	public IResource getResource() {
		IResource resource = null;
		if (fContext.getElement() instanceof IAdaptable) {
			resource = (IResource) ((IAdaptable) fContext.getElement()).getAdapter(IResource.class);
		} else if (fContext.getElement() instanceof IResource) {
			resource = (IResource) fContext.getElement();
		} else if (fContext.getElement() instanceof Entity) {
			String fileExt = IndexUtils.getFileExtension((Entity)fContext.getElement());
			if (fileExt == null || fileExt.length() == 0) {
				return null;
			}
			EObject obj = IndexUtils.getRootContainer((EObject) fContext.getElement());
			if (obj instanceof Entity) {
				return IndexUtils.getFile(((Entity) obj).getOwnerProjectName(), (Entity) obj);
			}
		}
		return resource;
	}
	@Override
	public Object[] getElements() {
		return new Object[] { fContext.getElement() };
	}

	@Override
	public String getIdentifier() {
		return "com.tibco.cep.studio.core.refactoring.renameEntityProcessor";
	}

	@Override
	public String getProcessorName() {
		return "Rename entity";
	}

	@Override
	public boolean isApplicable() throws CoreException {
		return true;
	}

	@Override
	public RefactoringParticipant[] loadParticipants(RefactoringStatus status,
			SharableParticipants sharedParticipants) throws CoreException {
		fRefactoringParticipants = RefactoringUtils.getRefactoringParticipants();
		List<RefactoringParticipant> participants = new ArrayList<RefactoringParticipant>();
		for (RefactoringParticipant refactoringParticipant : fRefactoringParticipants) {
			if (refactoringParticipant instanceof RefactoringParticipant) {
				((RefactoringParticipant) refactoringParticipant).initialize(this, fContext, fArguments);
				participants.add((RefactoringParticipant) refactoringParticipant);
			}
		}
		return participants.toArray(new RefactoringParticipant[participants.size()]);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (IResource.class.equals(adapter) || IFile.class.equals(adapter)) {
			Object element = fContext.getElement();
			if (element instanceof IResource) {
				return element;
			}
			if (element instanceof EntityElement) {
				element = ((EntityElement) element).getEntity();
			}
			if (element instanceof Entity) {
				return IndexUtils.getFile(((Entity) element).getOwnerProjectName(), (Entity) element);
			}
		}
		return super.getAdapter(adapter);
	}
}