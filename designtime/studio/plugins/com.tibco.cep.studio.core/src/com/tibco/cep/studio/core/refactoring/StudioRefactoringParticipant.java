package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.common.util.EntityNameHelper.isValidBEEntityIdentifier;
import static com.tibco.cep.studio.core.util.CommonUtil.isDuplicateBEResource;
import static com.tibco.cep.studio.core.util.CommonUtil.isDuplicateResource;
import static com.tibco.cep.studio.core.util.CommonUtil.isNonEntityResource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.FileStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.validation.DependentResources;
import com.tibco.cep.studio.core.validation.ValidationUtils;

/**
 * Abstract class for performing refactoring operations on BE elements.  The
 * RefactoringArguments dictates what type of refactoring operation is taking place,
 * for instance a rename (RenameArguments) or a move (MoveArguments).  Provides
 * common convenience methods for subclasses.
 * 
 * @author rhollom
 *
 */
public abstract class StudioRefactoringParticipant extends RefactoringParticipant {

	protected Object fElement;
	protected String fProjectName;
	private Object fElementToRefactor;
	private RefactoringArguments fArguments;
	
	// the list of affected resources after a delete, so that validation can properly re-validate
	private List<IResource> fAffectedResources = new ArrayList<IResource>();

	/**
	 * Performs initial checks for this refactoring operation<br>
	 * These include:<br>
	 *  - Disallowing project rename<br>
	 *  - Disallowing state model rename<br>
	 *  - Checking whether the new name is a valid BE identifier for rename refactorings<br>
	 *  - Checking for duplicate resource<br>
	 *  
	 *  Subclasses may override to provide their own pre-condition checks
	 */
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		
		if (IndexUtils.isProjectLibType(getResource())) {
			return null;
		}
		
		if (isDeleteRefactor() && !isNonEntityResource(getResource())) {
			//Modified to fix BE-8264 - Anand 10/04/2010
			RefactoringStatus status = checkDeleteConditions(pm);
			if (fAffectedResources.size() > 0) {
				try {
					QualifiedName key = ValidationUtils.getDependentQN(getResource());
					Object prop = getResource().getProject().getSessionProperty(key);
					if (prop instanceof DependentResources) {
						((DependentResources) prop).getDependentResources().addAll(fAffectedResources);
					} else {
						if (getResource() instanceof IFile) {
							DependentResources dr = new DependentResources((IFile) getResource(), fAffectedResources);
							getResource().getProject().setSessionProperty(key, dr);
						}
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			return status;
		}
		RefactoringStatus status = new RefactoringStatus();
		String newName = getNewElementName();
		IResource resource = getResource();
//		if (resource instanceof IProject) {
//			status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("BE_Project_rename_error"));
//			return status;
//		}
		if (resource instanceof IFolder && isMoveRefactor()) {
			// check to see whether there is already a folder of the same name in the target
			String newElementPath = getNewElementPath();
			IContainer folder = "/".equals(newElementPath) ? resource.getProject() : resource.getProject().getFolder(newElementPath);
			if (folder.equals(resource)) {
				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("Folder.invalid.target"));
				return status;
			}
					
			if (folder.getFolder(new Path(resource.getName())).exists()) {
				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("Folder.move.duplicate.name", resource.getName()));
				return status;
			}
		}
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		
		if(IndexUtils.isJavaType(file)){
		     if(isMoveRefactor() && !StudioJavaUtil.isTargetJavaSourceFolder(getResource(),getNewElementPath())){
		    	 status.addFatalError("The Destination Folder is not a valid JavaSource Folder.Please choose a different one");
		    	 return status;
		     }
		}
		
		/*if(isTestDataResource(file)){
			if(isMoveRefactor() || isRenameRefactor()){
				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("Test_Data_Resource_FileExtn"));
				return status;
			}
		}*/

		if(!isDeleteRefactor() && !isMoveRefactor()){
			int idx = newName.lastIndexOf('.');
			if (idx > 0) {
				String extn = newName.substring(idx + 1);
				if(!extn.trim().equals(file.getFileExtension())){
					status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_FileExtn", extn, file.getFileExtension()));
					return status;
				}
				newName = newName.substring(0, idx);
			}
		}
//		else{
//			if (!(getElementToRefactor() instanceof Entity)) {
//				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_Invalid_FileExtn",newName));
//				return status;
//			}
//		}
		if (isNonEntityResource(getResource())){
			String invalidCharRegex = "[^\\!\\[\\]]*";
	    	boolean valid = Pattern.matches(invalidCharRegex, newName);
	    	if (!valid) {
	    		status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("Resource_invalidFilename", newName));
				return status;
	    	}
		}

		if(!isValidBEEntityIdentifier(newName) && !isNonEntityResource(getResource())){
			status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_invalidFilename", newName));
			return status;
		}
		
		IResource parentResource = null;
		if (isRenameRefactor()) {
			parentResource = file.getParent();
		} else if (isMoveRefactor()) {
			String newPath = getNewElementPath();
			if (newPath == null || newPath.length() == 0 || newPath.equals("/")) {
				parentResource = getResource().getProject();
			} else {
				parentResource = getResource().getProject().getFolder(newPath);
			}
		}
		
		if(isNonEntityResource(getResource())){
			checkForDuplicateResource(status, newName, parentResource);
		}else{
			checkForDuplicateElement(status, newName, parentResource);
		}
		return status;
	}

//	public static boolean checkifDestinationFolderisJavaFolder(IResource resource, String newElementPath) {
//		
////		boolean flag =false; 
////		IResource[] nonJavaResources = null;
//////		IResource resource = getResource();
////		IFile file = (IFile) resource;
//////		String newElementPath = getNewElementPath();
////		if(null == newElementPath ) 
////			return false;
////		IContainer folder = "/".equals(newElementPath) ? resource.getProject() : resource.getProject().getFolder(newElementPath);
////		
////		if(IndexUtils.isJavaType(file)){
////			IJavaProject javaProject = (IJavaProject) JavaCore.create( resource.getProject());
////			try{
////				nonJavaResources = (IResource[]) javaProject.getNonJavaResources();
////				for(int i=0;i< nonJavaResources.length;i++){
////					if(nonJavaResources[i] instanceof IFolder){
////						if(folder.toString().equalsIgnoreCase(nonJavaResources[i].toString())){
////							 flag = true; break;
////						}
////					}
////				}
////				
////			}catch(JavaModelException e){
////				e.printStackTrace();
////			}
////		}
////		if(flag) return false; 
////		else return true;
//		
//		String projectName = resource.getProject().getName();
//		String uri = "/" + projectName + newElementPath;
//		
//		return StudioCoreResourceUtils.isInsideJavaSourceFolder(uri, projectName);
//		
//		
//	}
	
	//Added to fix BE-8264 - Anand 10/04/2010 - START 
	protected RefactoringStatus checkDeleteConditions(IProgressMonitor pm) {
		RefactoringStatus status = new RefactoringStatus();
		ISearchParticipant part = getSearchParticipant();
		if (part != null) {
			SearchResult search = part.search(getElementToRefactor(), fProjectName, getNewElementName(), new SubProgressMonitor(pm, 1));
			List<EObject> exactMatches = search.getExactMatches();
			// warn the user that references exist
			for (EObject object : exactMatches) {
				RefactoringStatusContext statusContext = createRefactoringStatusContext(object);
				IFile file = SearchUtils.getFile(object);
				String warningLabel = null;
				if (file != null) {
					if (file.equals(getResource()) || isContainedDelete(file)) {
						// skip the file being deleted, or any file contained within a folder being deleted
						continue;
					}
					warningLabel = "Warning - references exist in "+file.getFullPath().toString();
				} else {
					warningLabel = "Warning - references exist: "+SearchUtils.getElementMatchLabel(object);
				}
				status.addEntry(new RefactoringStatusEntry(RefactoringStatus.WARNING,
						warningLabel, statusContext));
				
				// add this as a resource to be validated, post delete
				addAffectedResource(file);
			}
		}
		return status;
	}

	protected void addAffectedResource(IFile file) {
		if (!fAffectedResources.contains(file)) {
			fAffectedResources.add(file);
		}
	}
	
	protected void removeAffectedResource(IFile file) {
		fAffectedResources.remove(file);
	}
	
	protected boolean hasAffectedResource(IFile file) {
		return fAffectedResources.contains(file);
	}
	
	protected boolean isAffectedResourcesEmpty(){
		return fAffectedResources.isEmpty();
	}
	
	//Added to fix BE-8264 - Anand 10/04/2010 - END
	
	protected boolean isValidProject() {
		IResource resource = getResource();
		if (resource != null) {
			return CommonUtil.isStudioProject(getResource().getProject());
		}
		return false;
	}

	
	protected RefactoringStatusContext createRefactoringStatusContext(
			EObject data) {
		IFile file = SearchUtils.getFile(data);
		if (file != null) {
			return new FileStatusContext(file, null);
		}
		return null;
	}

	protected ISearchParticipant getSearchParticipant() {
		return null;
	}

	/**
	 * Default implementation checks for duplicate file names in the parent folder.
	 * Subclasses may override if this is not a valid check.  For instance, if
	 * a property definition is being renamed, the check should be for duplicate
	 * property definition names, not duplicate file names.
	 * 
	 * @param status
	 * @param newName
	 * @param parentResource
	 */
	protected void checkForDuplicateElement(RefactoringStatus status,
			                                String newName, IResource parentResource) {
	
		//Checking whether elementToRefactor is of type Property Definition Or Destination 
		if(getElementToRefactor() instanceof PropertyDefinition
				|| getElementToRefactor() instanceof Destination){
			return;
		}
		if(isRenameRefactor()){
			StringBuilder duplicateFileName  = new StringBuilder("");
			boolean isDuplicateBEResource = isDuplicateBEResource(parentResource, newName, duplicateFileName);
			if(isDuplicateBEResource){
				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("BE_Resource_FilenameExists", duplicateFileName ,newName));
			}
		}
	}
	
	
	/**
	 * @param status
	 * @param newName
	 * @param parentResource
	 */
	protected void checkForDuplicateResource(RefactoringStatus status, 
			                                 String newName, IResource parentResource) {
		if(isRenameRefactor()){
			StringBuilder duplicateFileName  = new StringBuilder("");
			boolean isDuplicateResource = isDuplicateResource(parentResource, newName, duplicateFileName);
			if(isDuplicateResource){
				status.addFatalError(com.tibco.cep.studio.core.util.Messages.getString("Resource_FilenameExists", duplicateFileName ,newName));
			}
		}
	}

	/**
	 * Returns the RefactoringArguments for this refactoring
	 * operation, dictating the type of change that should
	 * be made by the participant
	 * @see RenameArguments
	 * @see MoveArguments
	 * @see DeleteArguments
	 * @return
	 */
	protected RefactoringArguments getArguments() {
		return fArguments;
	}

	protected void reset() {
		fElement = null;
		fProjectName = null;
		fElementToRefactor = null;
	}

	/**
	 * Returns the resource on which the refactoring operation
	 * is taking place.
	 * @return
	 */
	protected IResource getResource() {
		IResource resource = null;
		if (fElement instanceof IAdaptable) {
			resource = (IResource) ((IAdaptable) fElement).getAdapter(IResource.class);
		} else if (fElement instanceof IResource) {
			resource = (IResource) fElement;
		}
		return resource;
	}

	/**
	 * For rename refactorings, returns the
	 * new name of the renamed element
	 * @return
	 */
	public String getNewElementName() {
		if (!isRenameRefactor()) {
			if (getResource() instanceof IFile) {
				return ((IFile)getResource()).getFullPath().removeFileExtension().lastSegment();
			}
			return getResource().getName();
		}
		RenameArguments arguments = (RenameArguments) getArguments();
		String newName = arguments.getNewName();
		int idx = newName.lastIndexOf('.');
		if (idx > 0) {
			// strip file extension
			newName = newName.substring(0, idx);
		}
		return newName;
	}
	
	/**
	 * For move refactoring operations, returns
	 * the target path of the move
	 * @return
	 */
	public String getNewElementPath() {
		if (!isMoveRefactor()) {
			return null;//getResource().getParent().getFullPath().toOSString();
		}
		MoveArguments arguments = (MoveArguments) getArguments();
		Object destination = arguments.getDestination();
		String path;
		if (destination instanceof IFolder) {
			path = ((IFolder) destination).getFullPath().removeFirstSegments(1).toString();
		} else {
			path = (String) arguments.getDestination();
		}
		int idx = path.lastIndexOf('.');
		if (idx > 0) {
			// strip file extension
			path = path.substring(0, idx);
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		return path;
	}

	@Override
	protected boolean initialize(Object element) {
		reset();

		this.fElement = element;
		return true;
	}

	@Override
	protected void initialize(RefactoringArguments arguments) {
		this.fArguments = arguments;
	}

	/** 
	 * Default implementation attempts to load the IFile being refactored into
	 * an EObject.  If successful, returns the loaded EObject.  Subclasses
	 * may override to obtain their own element to refactor based on the 
	 * refactoring context.
	 * 
	 * @return the object being refactored
	 */
	protected Object getElementToRefactor() {
		if (fElementToRefactor == null) {
			IResource resource = getResource();
			if (fElement instanceof StudioRefactoringContext) {
				fElementToRefactor = ((StudioRefactoringContext) fElement).getElement();
			} 
			if (resource instanceof IResource) {
				this.fProjectName = resource.getProject().getName();
				if (!(fElementToRefactor instanceof Entity)) {
					DesignerElement element = null;
					if (resource instanceof IContainer) {
						element = IndexUtils.getFolder(IndexUtils.getIndex(resource), (IContainer) resource, false, false);
					} else {
						element = IndexUtils.getElement(resource);
					}
					if (element != null) {
						fElementToRefactor = element;
					} else if ("cdd".equalsIgnoreCase(resource.getFileExtension()) /*|| "st".equalsIgnoreCase(resource.getFileExtension())*/) // Commented for St as it was causing issue BE-15019
					{ 
						// do not try to load cdd files, it takes a long time using EMF apis
						fElementToRefactor = fElement;
					} else if (resource instanceof IFile && IndexUtils.isEMFType((IFile) resource)) {
						fElementToRefactor = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource), false);
					} 
				}
			} else if (fElementToRefactor == null && fElement instanceof IAdaptable) {
				Object adapter = ((IAdaptable) fElement).getAdapter(Entity.class);
				if (adapter instanceof Entity) {
					Entity entity = (Entity) adapter;
					this.fProjectName = entity.getOwnerProjectName();
					fElementToRefactor = adapter;
				}
			}
			if (fElementToRefactor == null) {
				fElementToRefactor = fElement;
			}
		}
		return fElementToRefactor;
	}

	protected String getOldElementPath() {
		Object elementToRefactor = getElementToRefactor();
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof Entity) {
			return ((Entity) elementToRefactor).getFolder();
		}
		if (elementToRefactor instanceof TypeElement) {
			return ((TypeElement) elementToRefactor).getFolder();
		}
		IResource resource = getResource();
		IPath path = resource.getProjectRelativePath();
		path = path.removeFileExtension();
		String fullPath = path.toString();
		if (fullPath.length() > 0 && fullPath.charAt(0) != '/') {
			fullPath = "/" + fullPath;
		}
		return fullPath;
	}
	
	protected String getOldElementName() {
		Object elementToRefactor = getElementToRefactor();
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof Entity) {
			return ((Entity) elementToRefactor).getName();
		}
		if (elementToRefactor instanceof DesignerElement) {
			return ((DesignerElement) elementToRefactor).getName();
		}
		if (elementToRefactor instanceof IResource) {
			return ((IResource) elementToRefactor).getName();
		}
		if (elementToRefactor instanceof String) {
			return (String) elementToRefactor;
		}
		
		IResource resource = getResource();
		IPath path = resource.getProjectRelativePath();
		path = path.removeFileExtension();
		String fullPath = path.toString();
		if (fullPath.length() > 0 && fullPath.charAt(0) != '/') {
			fullPath = "/"+fullPath;
		}
		return fullPath;
	}
	
	protected String getOldElementFullPath() {
		return getOldElementPath() + getOldElementName();
	}

	protected boolean shouldUpdateReferences() {
		if (fArguments instanceof RenameArguments) {
			return ((RenameArguments) fArguments).getUpdateReferences();
		}
		if (fArguments instanceof MoveArguments) {
			return ((MoveArguments) fArguments).getUpdateReferences();
		}
		if (fArguments instanceof DeleteArguments) {
			return true;
		}
		return false;
	}

	public boolean isRenameRefactor() {
		return fArguments instanceof RenameArguments;
	}
	
	public boolean isMoveRefactor() {
		return fArguments instanceof MoveArguments;
	}
	
	public boolean isDeleteRefactor() {
		return fArguments instanceof DeleteArguments;
	}
	
	protected boolean isFolderRefactor() {
		return getResource() instanceof IFolder;
	}
	
	protected boolean isProjectRefactor() {
		return getResource() instanceof IProject;
	}
	
	/**
	 * Checks whether the file exists inside of a folder that is being
	 * deleted.  If so, there is generally no reason to process the
	 * file, since it will be deleted
	 * @param file
	 * @return
	 */
	protected boolean isContainedDelete(IFile file) {
		if (file == null) {
			return false;
		}
		if (!isDeleteRefactor() || !isFolderRefactor()) {
			return false;
		}
		IFolder folder = (IFolder) getResource();
		IPath folderPath = folder.getFullPath();
		IPath filePath = file.getFullPath();
		if (filePath.segmentCount() < folderPath.segmentCount()) {
			return false;
		}
		String[] folderSegments = folderPath.segments();
		String[] fileSegments = filePath.segments();
		for (int i = 0; i < folderSegments.length; i++) {
			if (!folderSegments[i].equals(fileSegments[i])) {
				return false;
			}
		}
		return true;
		
	}

	public Object getfElementToRefactor() {
		return fElementToRefactor;
	}

	public void setfElementToRefactor(Object fElementToRefactor) {
		this.fElementToRefactor = fElementToRefactor;
	}
	
}
