package com.tibco.cep.studio.core.refactoring;

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
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameProcessor;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.RefactoringUtils;

public class EntityRenameProcessor extends RenameProcessor {

	private IRefactoringContext fContext;
	private RenameArguments fArguments;
	private boolean fUpdateReferences = true;
	private String fNewName;

	public EntityRenameProcessor(IRefactoringContext context) {
		super();
		this.fContext = context;
		if (fContext.getElement() instanceof IResource) {
			fNewName = ((IResource) fContext.getElement()).getName();
			if (fContext.getElement() instanceof IFile){
				int idx = fNewName.lastIndexOf('.');
				if (idx > 0) {
					fNewName = fNewName.substring(0, idx);
				}
			}
		} else if (fContext.getElement() instanceof Entity) {
			fNewName = ((Entity) fContext.getElement()).getName();
		}
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		fArguments = new RenameArguments(getNewName(), shouldUpdateReferences());
		return new RefactoringStatus();
	}

	public String getNewName() {
		return fNewName;
	}

	public void setUpdateReferences(boolean updateReferences) {
		fUpdateReferences = updateReferences;
	}

	public void setNewName(String newName) {
		fNewName = newName;
	}

	public boolean shouldUpdateReferences() {
		return fUpdateReferences;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		// create the actual file name change here
		IResource resource = (IResource) getResource();
		if (resource != null) {
			String newName = fArguments.getNewName();
			if (resource instanceof IFile) {
				newName += "."+resource.getFileExtension();
			}
			return new RenameResourceChange(resource.getFullPath(), newName);
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
		RefactoringParticipant[] refactoringParticipants = RefactoringUtils.getRefactoringParticipants();
		List<RefactoringParticipant> participants = new ArrayList<RefactoringParticipant>();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			if (refactoringParticipant instanceof RefactoringParticipant) {
				((RefactoringParticipant) refactoringParticipant).initialize(this, fContext, fArguments);
				participants.add((RefactoringParticipant) refactoringParticipant);
			}
		}
		return participants.toArray(new RefactoringParticipant[participants.size()]);
	}

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

	public RenameArguments getfArguments() {
		return fArguments;
	}

	public void setfArguments(RenameArguments fArguments) {
		this.fArguments = fArguments;
	}

	public IRefactoringContext getfContext() {
		return fContext;
	}

	public void setfContext(IRefactoringContext fContext) {
		this.fContext = fContext;
	}


}
