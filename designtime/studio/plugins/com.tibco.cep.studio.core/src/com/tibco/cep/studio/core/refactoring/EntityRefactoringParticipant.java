package com.tibco.cep.studio.core.refactoring;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.internal.core.refactoring.resource.MoveResourcesProcessor;
import org.eclipse.ltk.internal.core.refactoring.resource.RenameResourceProcessor;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class EntityRefactoringParticipant<E extends Entity> extends StudioRefactoringParticipant implements IStudioPasteParticipant {

	private class ResourceCollector implements IResourceVisitor {

		private List<IFile> fCollectedResources = new ArrayList<IFile>();
		
		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (!(resource instanceof IFile)) {
				return true;
			}
			IFile file = (IFile) resource;
			String[] extensions = getSupportedExtensions();
			if (extensions == null && (file.getFileExtension() == null || file.getFileExtension().length() == 0)) {
				fCollectedResources.add(file);
				return false;
			}
			
			if (extensions != null) {
				for (String ext : extensions) {
					if (ext.equalsIgnoreCase(file.getFileExtension())) {
						fCollectedResources.add(file);
						return false;
					}
				}
			}
			return false;
		}
		
		public List<IFile> getCollectedResources() {
			return fCollectedResources;
		}

	}
	
	protected static final String[] EMPTY_STRING_ARR = new String[0];
	
	protected boolean projectPaste = false;

	public EntityRefactoringParticipant() {
	}

	/**
	 * Process the <code>elementToRefactor</code> by searching relevant Entities and making
	 * the appropriate changes.  For instance, a ConceptRefactoringParticipant will look
	 * at all Concepts in the project <code>projectName</code> and look for references to
	 * <code>elementToRefactor</code>, updating reference where needed.  If <code>elementToRefactor</code>
	 * is a StateMachine, for instance, this will look at each Concept's state machine paths,
	 * updating as needed.
	 * @param elementToRefactor - the element being refactored (renamed/moved/etc).
	 * @param projectName - the name of the project in which this element exists
	 * @param pm
	 * @param preChange - whether this processing is occurring before or after the element is refactored
	 * @return
	 * @throws CoreException
	 * @throws OperationCanceledException
	 */
	protected Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException, OperationCanceledException {
		// default implementation does nothing, clients must override
		return null;
	}

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
			return null;
		}
		IFile file = (IFile) resource;
		if (IndexUtils.getFileType(file) == null) {
			return status;
		}
		return status;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createPreChange(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IProject) {
			Object elementToRefactor = getElementToRefactor();
			// project refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
			return processEntity(elementToRefactor, fProjectName, pm, true);
		}
		if (resource instanceof IFolder) {
			Object elementToRefactor = getElementToRefactor();
			// folder refactorings need to be done pre-change, as elements could have moved and therefore post-changes cannot be computed
			return processEntity(elementToRefactor, fProjectName, pm, true);
		}
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (!isSupportedResource(file)) {
			return null;
		}
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		Entity refactoredEntity =  null;
		if (IndexUtils.isRuleType(file.getFileExtension()) || IndexUtils.isRuleFunctionType(file.getFileExtension())){
			String rfPath = file.getProject().getLocation().toPortableString()+"/"+ file.getProjectRelativePath().toPortableString();
			refactoredEntity = IndexUtils.parseCompilableFile(file.getProject().getName(), rfPath);
		} else {
			EObject object = null;
			try {
				object = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
			} catch (Exception e) {
			}
			if (!(object instanceof Entity)) {
				return null;
			}
			refactoredEntity = (Entity) object;
		}
		boolean renameEntity = false;
		boolean moveEntity = false;
		if (getProcessor() instanceof EntityRenameProcessor) {
			renameEntity = ((EntityRenameProcessor)getProcessor()).getResource() != null;
		} else if (getProcessor() instanceof RenameResourceProcessor) {
			renameEntity = true;
		}
		if (getProcessor() instanceof EntityMoveProcessor) {
			moveEntity = ((EntityMoveProcessor)getProcessor()).getResource() != null;
		} else if (getProcessor() instanceof MoveResourcesProcessor) {
			moveEntity = true;
		}
		refactoredEntity = (Entity) EcoreUtil.copy(refactoredEntity);
		if (isDeleteRefactor()) {
			refactoredEntity = preProcessEntityDelete((E)refactoredEntity);
		} else {
			refactoredEntity = preProcessEntityChange((E)refactoredEntity);
		}
		if (refactoredEntity == null) {
			// this implies no change
			return null;
		}
		if (renameEntity) {
			String newName = getNewElementName();
			refactoredEntity.setName(newName);
		}
		if (moveEntity) {
			String newFolder = getNewElementPath();
			if (refactoredEntity instanceof Entity) {
				Entity entity = (Entity)refactoredEntity;
				entity.setFolder(newFolder);
				entity.setNamespace(newFolder);
			}
		}
		Change change = createTextFileChange((IFile) getResource(), refactoredEntity);
		return change;
	}

	protected boolean isSupportedResource(IFile file) {
		String[] extensions = getSupportedExtensions();
		if (extensions == null && (file.getFileExtension() == null || file.getFileExtension().length() == 0)) {
			return true;
		}
		if (extensions != null) {
			for (String ext : extensions) {
				if (ext.equalsIgnoreCase(file.getFileExtension())) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected String[] getSupportedExtensions() {
		return EMPTY_STRING_ARR;
	}
	
	/**
	 * Some changes need to be done prior to the file rename/move.  This is required as
	 * the file name/path will change, and therefore any post-refactoring changes on the 
	 * old file will be invalid, as the old file will no longer exist.
	 * @param entity
	 * @return the altered entity, or <code>null</code> if no changes are made
	 */
	protected E preProcessEntityChange(E refactorParticipant) {
//		need to preProcess entity in specific refactoring participants, for instance, if a concept is renamed
//		and it has a property that refers to itself, that also needs to change
		return null;
	}

	/**
	 * Some changes need to be done prior to the file delete.  In most cases,
	 * it is suitable to simply process the delete in the processEntity call,
	 * where the changes are made post-delete
	 * @param entity
	 * @return the altered entity, or <code>null</code> if no changes are made
	 */
	protected Entity preProcessEntityDelete(Entity refactorParticipant) {
//		need to preProcess entity in specific refactoring participants
		return null;
	}
	
	/**
	 * Creates a <code>Change</code> object given the IFile and Entity.
	 * Simply serializes the Entity and creates a ReplaceEdit for the
	 * IFile.  This is generally used after processing the entity, to
	 * create the actual refactoring change to take place
	 * @param file
	 * @param refactorParticipant
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, Entity refactorParticipant)
			throws CoreException {
		
		//To restrict references to be refactored from Project Library 
		if(!file.exists()){
			return null;
		}
		if (isDeleteRefactor() && isDeleted(file)) {
			// if this file is already getting deleted, then don't create a text file change for it
			return null;
		}
		refactorParticipant = (Entity) IndexUtils.getRootContainer(refactorParticipant);
		byte[] objectContents;
		TextFileChange change = null;
		InputStream fis = null;
		try {
			
			String contents = null;
			if (refactorParticipant instanceof RuleFunction) {
				contents = ModelUtils.getRuleFunctionAsSource((RuleFunction)refactorParticipant);
			} else if(refactorParticipant instanceof Rule){
				contents = ModelUtils.getRuleAsSource((Rule)refactorParticipant);
			} else {
				//Modified by Anand on 04/29/2009 to fix 1-AP56AF & 1-AMFJDZ
				String pathAsString = null;
				if (isProjectRefactor()) {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getOldElementName());
					IPath projectLocation = project.getLocation();
//					projectLocation = projectLocation.removeLastSegments(1);
//					projectLocation = projectLocation.append(getNewElementName());
					IPath path = projectLocation == null ? null : projectLocation.append(refactorParticipant.getFolder());
					pathAsString = path == null ? null : path.toString();
				} else {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(refactorParticipant.getOwnerProjectName());
					IPath projectLocation = project.getLocation();
					IPath path = projectLocation == null ? null : projectLocation.append(refactorParticipant.getFolder());
					pathAsString = path == null ? null : path.toString();
				}
				objectContents = IndexUtils.getEObjectContents(pathAsString,refactorParticipant);
				contents = new String(objectContents, file.getCharset());
			}
			
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return change;
	}

	private boolean isDeleted(IFile file) {
		RefactoringProcessor processor = getProcessor();
		if (isDeleteRefactor()) {
			Object[] elements = processor.getElements();
			for (int i=0; i<elements.length; i++) {
				Object o = elements[i];
				if (o instanceof IFile) {
					if (o.equals(file)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "Entity rename participant";
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (resource instanceof IFolder || resource instanceof IProject) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		return processEntity(elementToRefactor, fProjectName, pm, false);
	}

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

	protected boolean processFolder(Entity entity) {
		// check folder
		if (isFolderRefactor()) {
			IFolder folder = (IFolder) getResource();
			if (IndexUtils.startsWithPath(entity.getFolder(), folder, true)) {
				if (isDeleteRefactor()) {
					return false; // this entity is contained within the folder getting deleted.  Ignore it.
				}
				String newPath = getNewPath(entity.getFolder(), folder);
				entity.setFolder(newPath);
				entity.setNamespace(newPath);
				return true;
			}
		}
		return false;
	}

	@Override
	public IStatus pasteElement(String newName, IResource resource, IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
    	if (resource instanceof IFile && IndexUtils.isEntityType((IFile)resource)) {
    		EObject entity = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
    		if (entity instanceof Entity) {
    			pasteEntity(newName, (Entity)entity, resource, (IContainer) target, overwrite);
    		}
    	}
		return null;
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
    	if (resource instanceof IFile && isSupportedResource((IFile)resource)) {
    		return true;
    	}
		return false;
	}

	/**
	 * @param entity
	 * @param source
	 * @param target
	 * @throws CoreException 
	 */
	protected void pasteEntity(String newName, Entity entity, IResource source, IContainer target, boolean overwrite) throws CoreException {
		if (newName == null) {
			return;
		}
		int idx = newName.lastIndexOf('.');
		if (idx > 0) {
			String newEntName = newName.substring(0, idx);
			entity.setName(newEntName);
		} else {
			entity.setName(newName);
		}
		
		if (!source.getParent().equals(target)) {
			// different folder, update entity folder/namespace.  
			// If the target is an IProject (i.e. root level), then use single slash for folder/ns
			String newPath = target instanceof IProject ? "/" : "/"+target.getFullPath().removeFirstSegments(1).toString()+"/";
			entity.setFolder(newPath);
			entity.setNamespace(newPath);
		}
		entity.setGUID(GUIDGenerator.getGUID());
		
		if (entity instanceof Concept && !isProjectPaste()) {
			Concept concept = (Concept)entity;
			concept.getStateMachinePaths().clear();
		}
		
		if (!entity.getOwnerProjectName().equals(target.getProject().getName())) {
			// reset owner project name
			entity.setOwnerProjectName(target.getProject().getName());
		}
		processChildren(newName, entity, target.getProject().getName());
		saveEntity(newName, entity, target, overwrite);
	}

	/**
	 * Recursively uses generic EMF APIs to reset the 'ownerProjectName' 
	 * attribute for all entities contained in this EObject, as well as the GUID. 
	 * This should be called for any subclasses that have overridden the pasteElement
	 * method, or alternative measures should be taken to properly update
	 * @param newName
	 * @param eobj
	 * @param newProjName
	 */
	protected void processChildren(String newName, EObject eobj, String newProjName) {
		if (eobj instanceof Entity) {
			// if it's not set, just move on (will not be set for SimpleProperties, for instance)
			if (eobj.eIsSet(eobj.eClass().getEStructuralFeature("ownerProjectName"))) {
				((Entity) eobj).setOwnerProjectName(newProjName);
			}
			if (!isRenameRefactor()) {
				// no need to reset the GUID if it is a rename refactor
				if (eobj.eIsSet(eobj.eClass().getEStructuralFeature("GUID"))) {
					((Entity) eobj).setGUID(GUIDGenerator.getGUID());
				}
			}
		}
		EList<EStructuralFeature> allStructuralFeatures = eobj.eClass().getEAllStructuralFeatures();
		for (EStructuralFeature structuralFeature : allStructuralFeatures) {
			if (structuralFeature instanceof EReference) {
				if (((EReference) structuralFeature).isContainment()) {
					Object get = eobj.eGet(structuralFeature);
					if (get instanceof EObject) {
						processChildren(newName, (EObject) get, newProjName);
					} else if (get instanceof List) {
						processList((List)get, newName, newProjName);
					}
				}
			}
		}
	}

	private void processList(List list, String newName, String oldProjName) {
		for (Object obj : list) {
			if (obj instanceof EObject) {
				processChildren(newName, (EObject) obj, oldProjName);
			} else if (obj instanceof List) {
				processList((List) obj, newName, oldProjName);
			}
		}
	}

	protected void saveEntity(String newName, Entity entity, IContainer target, boolean overwrite) throws CoreException {
		byte[] objectContents;
		try {
			//Modified by Anand on 04/29/2009 to fix 1-AP56AF & 1-AMFJDZ
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
			IPath projectLocation = project.getLocation();
			IPath path = projectLocation.append(entity.getFolder());
			objectContents = IndexUtils.getEObjectContents(path.toString(),entity);
			ByteArrayInputStream bos = new ByteArrayInputStream(objectContents);
			IFile file = target.getFile(new Path(newName));
			if (file.exists()) {
				if (overwrite) {
					file.setContents(bos, IResource.FORCE, new NullProgressMonitor());
				}
			} else {
				file.create(bos, false, new NullProgressMonitor());
				if(entity instanceof StateMachine){
					updateOwnerConcept((StateMachine)entity);
				}
			}
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioCorePlugin.PLUGIN_ID, "could not save "+entity.getFullPath(),e));
		} 
	}
	
	/**
	 * @param stateMachine
	 */
	private void updateOwnerConcept(StateMachine stateMachine){
		try {
			Concept concept = IndexUtils.getConcept(stateMachine.getOwnerProjectName(), stateMachine.getOwnerConceptPath());
			if (concept == null) {
				return; // perhaps the concept has not yet been pasted
			}
			concept.getStateMachinePaths().add(stateMachine.getFullPath());
			ModelUtils.saveEObject(concept);
//			concept.eResource().save(ModelUtils.getPersistenceOptions());
			//Persist Owner Concept with State Model Association Changes
			// If editor open, resource change listener will take care
			IndexUtils.getFile(stateMachine.getOwnerProjectName(), concept).refreshLocal(0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		if (!EntityNameHelper.isValidBEEntityIdentifier(newName)) {
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, newName + " is not a valid BE identifier");
		}
		return Status.OK_STATUS;
	}

	protected boolean isSharedElement(Entity entity) {
		return entity.eContainer() instanceof SharedElement;
	}

	@Override
	public boolean isProjectPaste() {
		return projectPaste;
	}

	@Override
	public void setProjectPaste(boolean projectPaste) {
		this.projectPaste = projectPaste;
	}

}
