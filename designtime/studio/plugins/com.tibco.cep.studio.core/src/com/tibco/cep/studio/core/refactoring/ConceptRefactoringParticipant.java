package com.tibco.cep.studio.core.refactoring;

import static com.tibco.cep.studio.core.util.CommonUtil.replace;

import java.util.ArrayList;
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
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.ConceptSearchParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;

public class ConceptRefactoringParticipant extends EntityRefactoringParticipant<Concept> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.CONCEPT_EXTENSION, CommonIndexUtils.SCORECARD_EXTENSION };
	private final static String TYPE_SHARED_JDBC="sharedjdbc";

	public ConceptRefactoringParticipant() {
		super();
	}

	@Override
	protected ISearchParticipant getSearchParticipant() {
		return new ConceptSearchParticipant();
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
	protected void checkForDuplicateElement(RefactoringStatus status,
			String newName, IResource parentResource) {
		Object element = getElementToRefactor();
		if (element instanceof PropertyDefinition) {
			EObject container = ((PropertyDefinition) element).eContainer();
			if (container instanceof Concept) {
				EList<PropertyDefinition> properties = ((Concept) container).getProperties();
				for (int i=0; i<properties.size(); i++) {
					if (newName.equals(properties.get(i).getName())) {
						status.addFatalError("A property already exists with the name '"+newName+"'");
						return;
					}
				}
			}
		} else {
			super.checkForDuplicateElement(status, newName, parentResource);
		}
	}

	@Override
	protected Concept preProcessEntityChange(Concept refactorParticipant) {
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processConcept(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return refactorParticipant;
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException,
			OperationCanceledException {
		// look through all concepts and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Concept changes:");
		List<Entity> allConcepts = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD });
		for (Entity entity : allConcepts) {
			if (entity == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processConcept(projectName, compositeChange, (Concept) entity, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	protected Concept processConcept(String projectName,
			CompositeChange compositeChange, Concept origConcept, Object elementToRefactor, boolean isPreProcess) throws CoreException {
		if (isSharedElement(origConcept)) {
			return origConcept;
		}
		// must be sure to copy the concept before making changes, otherwise canceling the wizard will keep the changes made and corrupt the concepts
		IFile file = IndexUtils.getFile(projectName, origConcept);
		Concept concept = (Concept) EcoreUtil.copy(origConcept);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return concept;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, concept, getNewElementName());
			changed = true;
		}
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (processFolder(concept)) {
			changed = true;
		}
		if (elementToRefactor instanceof Concept || isFolderRefactor()) {
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Concept) {
				Concept refactoredElement = (Concept) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
			}
			
			// check super concept path
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(concept.getSuperConceptPath(), folder)) {
					String newPath = getNewPath(concept.getSuperConceptPath(), folder);
					concept.setSuperConceptPath(newPath);
					changed = true;
				}
			} else {
				if (concept.getSuperConceptPath() != null && concept.getSuperConceptPath().equalsIgnoreCase(oldFullPath)) {
					if (isDeleteRefactor()){
						newFullPath = null;
					}
					concept.setSuperConceptPath(newFullPath);
					changed = true;
				}
			}
			if (isDeleteRefactor()){
				if (concept.getSuperConceptPath() != null && concept.getSuperConceptPath().equalsIgnoreCase(oldFullPath)) {
					concept.setSuperConceptPath(newFullPath);
					changed = true;
				}
			}
			
			// check parent concept path
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(concept.getParentConceptPath(), folder)) {
					String newPath = getNewPath(concept.getParentConceptPath(), folder);
					concept.setSuperConceptPath(newPath);
					changed = true;
				}
			} else {
				if (concept.getParentConceptPath() != null && concept.getParentConceptPath().equalsIgnoreCase(oldFullPath)) {
					concept.setParentConceptPath(newFullPath);
					changed = true;
				}
			}
			
			// check concept reference properties
			EList<PropertyDefinition> properties = concept.getProperties();
			for (PropertyDefinition propDef : properties) {
				String conceptPath = propDef.getConceptTypePath();
				String ownerPath = propDef.getOwnerPath();
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(ownerPath, folder)) {
						String newPath = getNewPath(ownerPath, folder);
						propDef.setOwnerPath(newPath);
						changed = true;
					}
					if (IndexUtils.startsWithPath(conceptPath, folder)) {
						String newPath = getNewPath(conceptPath, folder);
						propDef.setConceptTypePath(newPath);
						changed = true;
					}
				} else {
					if (ownerPath != null && ownerPath.equals(oldFullPath)) {
						propDef.setOwnerPath(newFullPath);
						changed = true;
					}
					if (conceptPath != null && conceptPath.equals(oldFullPath)) {
						propDef.setConceptTypePath(newFullPath);
						changed = true;
					}
				}
			}
		} 

		if (elementToRefactor instanceof PropertyDefinition) {
			if (isPreProcess) {
				PropertyDefinition propDef = (PropertyDefinition) elementToRefactor;
				EList<PropertyDefinition> properties = concept.getProperties();
				for (PropertyDefinition propertyDef : properties) {
					if (propertyDef.getName().equals(propDef.getName())) {
						propertyDef.setName(getNewElementName());
					}
				}
			}
		} 

		if (elementToRefactor instanceof StateMachine || isFolderRefactor()) {
			String newFullPath = "";
			if (elementToRefactor instanceof StateMachine) {
				if (isRenameRefactor()) {
					newFullPath = ((StateMachine)elementToRefactor).getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + ((StateMachine)elementToRefactor).getName();
				}
			}
			EList<String> stateMachinePaths = concept.getStateMachinePaths();
			for (int i = 0; i < stateMachinePaths.size(); i++) {
				String path = stateMachinePaths.get(i);
				if (isFolderRefactor()) {
					IFolder folder = (IFolder) getResource();
					if (IndexUtils.startsWithPath(path, folder)) {
						String newPath = getNewPath(path, folder);
						stateMachinePaths.remove(i);
						if(!newPath.trim().equalsIgnoreCase("")){
							stateMachinePaths.add(i, newPath);
						}
						changed = true;
					}
				} else {
					if (path.equals(((StateMachine) elementToRefactor).getFullPath())) {
						stateMachinePaths.remove(i);
						if(!newFullPath.trim().equalsIgnoreCase("")){
							stateMachinePaths.add(i, newFullPath);
						}
						changed = true;
					}
				}
			}
		} 

		if (elementToRefactor instanceof Domain || isFolderRefactor()) {
			String newFullPath = "";
			if (elementToRefactor instanceof Domain) {
				if (isRenameRefactor()) {
					newFullPath = ((Domain)elementToRefactor).getFolder() + getNewElementName();
				} else if (isMoveRefactor()) {
					newFullPath = getNewElementPath() + ((Domain)elementToRefactor).getName();
				}
			}
			for(DomainInstance instance:concept.getAllDomainInstances()){
				PropertyDefinition propertyDefinition =  instance.getOwnerProperty();
				EList<DomainInstance> domainInstnces = propertyDefinition.getDomainInstances();
				List<DomainInstance> disToRemove = new ArrayList<DomainInstance>();
				for (int i = 0; i < domainInstnces.size(); i++) {
					DomainInstance di = domainInstnces.get(i);
					if (isFolderRefactor()) {
						IFolder folder = (IFolder) getResource();
						if (IndexUtils.startsWithPath(di.getResourcePath(), folder)) {
							if (isDeleteRefactor()) {
								disToRemove.add(di);
							} else {
								String newPath = getNewPath(di.getResourcePath(), folder);
								di.setResourcePath(newPath);
								changed = true;
							}
						}
					} else {
						if (di.getResourcePath().equals(((Domain) elementToRefactor).getFullPath())) {
							if (isDeleteRefactor()) {
								disToRemove.add(di);
							} else {
								di.setResourcePath(newFullPath);
								changed = true;
							}
						}
					}
				}
				for (DomainInstance domainInstance : disToRemove) {
					domainInstnces.remove(domainInstance);
					changed = true;
				}
			}
		}
		
		if(isSharedJdbcResourceRefactor()){
			IFile sfile = (IFile)getResource();
			String oldSharedResourcePath = sfile.getFullPath().makeRelative().toOSString();
			oldSharedResourcePath = oldSharedResourcePath.substring(oldSharedResourcePath.indexOf(projectName)+projectName.length());
			oldSharedResourcePath = replace(oldSharedResourcePath,"\\","/");
			String newSharedResourcePath = null;
			if(isMoveRefactor()){
				newSharedResourcePath = getNewElementPath() + getNewElementName()+ "."+ sfile.getFileExtension();
			}else if (isRenameRefactor()) {
				newSharedResourcePath = sfile.getParent().getFullPath().makeRelative().toOSString();
				newSharedResourcePath = newSharedResourcePath.substring(newSharedResourcePath.indexOf(projectName)+projectName.length());
				newSharedResourcePath = newSharedResourcePath + "/" + getNewElementName()+ "."+ sfile.getFileExtension();
				newSharedResourcePath = replace(newSharedResourcePath,"\\","/");
				
			}else if (isDeleteRefactor()) {
				newSharedResourcePath =  "";
			}
			changed = modifyConceptForSharedJdbcRefactor(concept, oldSharedResourcePath, newSharedResourcePath);
		}

		if (changed) {
			Change change = createTextFileChange(file, concept);
			compositeChange.add(change);
		}
		return concept;
	}
	
	private boolean isSharedJdbcResourceRefactor() {
		if(getResource() instanceof IFile){
			IFile file = (IFile)getResource();
			if(TYPE_SHARED_JDBC.equalsIgnoreCase(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
	
	private boolean modifyConceptForSharedJdbcRefactor(Concept concept, String oldSharedResourcePath, String newSharedResourcePath ){
		if(concept.getExtendedProperties() == null)
			return false;
		EList<Entity> properties = concept.getExtendedProperties().getProperties();
		for (Entity entity : properties) {
			if(entity instanceof SimpleProperty){
				SimpleProperty op = (SimpleProperty)entity;
				String name = op.getName();
				if(name.equalsIgnoreCase("JDBC_RESOURCE")){
					if(op.getValue().equalsIgnoreCase(oldSharedResourcePath)){
						op.setValue(newSharedResourcePath);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	protected void processChildren(String newName, EObject eobj, String newProjName) {
		super.processChildren(newName, eobj, newProjName);
		if(eobj.eClass().getEStructuralFeature("ownerPath") != null) {
			if (eobj.eIsSet(eobj.eClass().getEStructuralFeature("ownerPath"))) {
				String ownerPath = ((Concept)((PropertyDefinition)eobj).eContainer()).getFolder()+((Concept)((PropertyDefinition)eobj).eContainer()).getName();
				((PropertyDefinition) eobj).setOwnerPath(ownerPath);
			}
		}
	}
}
