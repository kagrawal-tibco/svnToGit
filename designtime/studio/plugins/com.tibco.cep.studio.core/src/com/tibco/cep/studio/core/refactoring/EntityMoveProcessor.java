package com.tibco.cep.studio.core.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveProcessor;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.SharableParticipants;
import org.eclipse.ltk.core.refactoring.resource.MoveResourceChange;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.RefactoringUtils;

public class EntityMoveProcessor extends MoveProcessor {

	private IRefactoringContext fContext;
	private MoveArguments fArguments;
	private boolean fUpdateReferences = true;
	private String fNewPath;
	private boolean fRequiresSelection = true;;

	public EntityMoveProcessor(IRefactoringContext context) {
		super();
		this.fContext = context;
		if (fContext.getElement() instanceof IResource) {
			IResource resource = (IResource) fContext.getElement();
			IPath path = resource.getFullPath();
			path = path.removeFirstSegments(1); // remove the project
			if (resource instanceof IFile) {
				path = path.removeLastSegments(1);
			}
			fNewPath = path.toOSString();
			int idx = fNewPath.lastIndexOf('.');
			if (idx > 0) {
				fNewPath = fNewPath.substring(0, idx);
			}
		} else if (fContext.getElement() instanceof Entity) {
			fNewPath = ((Entity) fContext.getElement()).getFolder();
		}
	}

	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws CoreException,
			OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		if (getResource() != null) {
			String elName = getResource().getFullPath().removeFileExtension().lastSegment();
			String newPath = getNewPath()+"/"+elName;
			DesignerElement element = IndexUtils.getElement(getResource().getProject().getName(), newPath);
			if (element != null) {
				status.addFatalError("An element with the name '"+elName+"' already exists at this location");
			}
		}
		fArguments = new MoveArguments(getNewPath(), shouldUpdateReferences());
		return status;
	}

	public String getNewPath() {
		return fNewPath;
	}

	public void setUpdateReferences(boolean updateReferences) {
		fUpdateReferences = updateReferences;
	}

	public void setNewPath(String newPath) {
		fNewPath = newPath;
	}

	public boolean requiresSelection() {
		return fRequiresSelection;
	}
	
	public void setRequiresSelection(boolean requiresSelection) {
		this.fRequiresSelection  = requiresSelection;
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
		// create the actual file move change here
		IResource resource = (IResource) getResource();
		if (resource != null) {
			String newPath = (String) fArguments.getDestination();
			IContainer folder = null;
			if (newPath == null || newPath.length() == 0) {
				folder = resource.getProject();
			} else {
				folder = resource.getProject().getFolder(newPath);
			}
			return new MoveResourceChange(resource, folder);
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
		return "com.tibco.cep.studio.core.refactoring.moveEntityProcessor";
	}

	@Override
	public String getProcessorName() {
		return "Move entity";
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


}
