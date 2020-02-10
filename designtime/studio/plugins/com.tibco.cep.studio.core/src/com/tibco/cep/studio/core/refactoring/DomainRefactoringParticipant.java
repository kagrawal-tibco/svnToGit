package com.tibco.cep.studio.core.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class DomainRefactoringParticipant extends EntityRefactoringParticipant<Domain> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.DOMAIN_EXTENSION };

	public DomainRefactoringParticipant() {
		super();
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
	protected Domain preProcessEntityChange(Domain refactorParticipant) {
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processDomain(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
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
		// look through all domains and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Domain changes:");
		List<Entity> allDomains = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.DOMAIN});
		for (Entity entity : allDomains) {
			if (entity == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processDomain(projectName, compositeChange, (Domain) entity, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	private Domain processDomain(String projectName,
			CompositeChange compositeChange, Domain domain, Object elementToRefactor, boolean isPreProcess) throws CoreException {
		if (isSharedElement(domain)) {
			return domain;
		}
		// must be sure to copy the domain before making changes, otherwise canceling the wizard will keep the changes made and corrupt the domains
		IFile file = IndexUtils.getFile(projectName, domain);
		domain = (Domain) EcoreUtil.copy(domain);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return domain;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, domain, getNewElementName());
			changed = true;
		}

		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (processFolder(domain)) {
			changed = true;
		}
		if (elementToRefactor instanceof Domain || isFolderRefactor()) {
			String newFullPath = "";
			String oldFullPath = "";
			if (elementToRefactor instanceof Domain) {
				Domain refactoredElement = (Domain) elementToRefactor;
				oldFullPath = refactoredElement.getFullPath();
				if (isRenameRefactor()) {
					newFullPath = refactoredElement.getFolder() + getNewElementName();
				} else if (isMoveRefactor()){
					newFullPath = getNewElementPath() + refactoredElement.getName();
				}
			}
			if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(domain.getSuperDomainPath(), folder)) {
					String newPath = getNewPath(domain.getSuperDomainPath(), folder);
					domain.setSuperDomainPath(newPath);
					changed = true;
				}
			} else {
				if (domain.getSuperDomainPath() != null && domain.getSuperDomainPath().equalsIgnoreCase(oldFullPath)) {
					if (isDeleteRefactor()) {
						newFullPath = null;
					}
					domain.setSuperDomainPath(newFullPath);
					changed = true;
				}
			}
		} 
		
		if (changed) {
			Change change = createTextFileChange(file, domain);
			compositeChange.add(change);
		}
		return domain;
	}

}
