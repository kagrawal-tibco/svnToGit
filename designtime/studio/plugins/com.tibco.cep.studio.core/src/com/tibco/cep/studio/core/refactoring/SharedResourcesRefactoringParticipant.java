package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.core.util.CommonUtil.isSharedResource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class SharedResourcesRefactoringParticipant extends StudioRefactoringParticipant{
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			Object elementToRefactor = getElementToRefactor();
			// folder refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
			return processSharedResource(elementToRefactor, fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		if (!isSharedResource(getResource())) {
			return null;//new NullChange();
		}
		Object elementToRefactor = getElementToRefactor();
//		DesignerElement element = IndexUtils.getElement(file);
//		if (!(element instanceof SharedElement)) {
//			return null;
//		}
		CompositeChange change = new CompositeChange("Changes to "+ /*element.getName()*/getName());
//		
//		processSharedResourceElement(fProjectName, change, (DecisionTableElement) element, elementToRefactor);
		return change;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.refactoring.EntityRenameParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			                                 CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
		}
		RefactoringStatus status = super.checkConditions(pm, context);
		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return status;
		}
//		IFile file = (IFile) resource;
		if (isDeleteRefactor()
				|| isSharedResource(getResource())) {
			return status;
		}

		return status;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		return processSharedResource(elementToRefactor, fProjectName, pm);
	}

	/**
	 * @param elementToRefactor
	 * @param projectName
	 * @param pm
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	public Change processSharedResource(Object elementToRefactor, 
			                           String projectName, 
			                           IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		// look through all Decision Tables and make appropriate changes

		CompositeChange compositeChange = new CompositeChange("Shared Resources changes:");
//		List<SharedElement> allSharedResources = IndexUtils.getAllElements(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.Shared_Resouces });
//		for (DesignerElement element : allSharedResources) {
//			if (element == elementToRefactor) {
//				// already processed in the pre-change
//				continue;
//			}
//			processSharedResourceElement(projectName, compositeChange, (SharedElement) element, elementToRefactor);
//		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
	 */
	@Override
	public String getName() {
		return "Shared Resource Refactor participant";
	}

	/**
	 * @param projectName
	 * @param compositeChange
	 * @param sharedElement
	 * @param elementToRefactor
	 * @throws CoreException
	 */
	private void processSharedResourceElement(String projectName,
			                                 CompositeChange compositeChange, 
			                                 SharedElement sharedElement, 
			                                 Object elementToRefactor) throws CoreException {
		//TODO
	}
	
	/**
	 * @param table
	 * @return
	 */
	protected boolean processFolder(Table table) {
		if (isFolderRefactor()) {
			IFolder folder = (IFolder) getResource();
			if (IndexUtils.startsWithPath(table.getFolder(), folder, true)) {
				String newPath = getNewPath(table.getFolder(), folder);
				table.setFolder(newPath);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param elementPath
	 * @param folder
	 * @return
	 */
	protected String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}
}
