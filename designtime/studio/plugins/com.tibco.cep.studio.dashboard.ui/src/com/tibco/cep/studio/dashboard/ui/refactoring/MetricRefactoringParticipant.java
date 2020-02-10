package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.ConceptRefactoringParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.dashboard.ui.search.DashboardSearchParticipant;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class MetricRefactoringParticipant extends ConceptRefactoringParticipant {

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if (isDeleteRefactor() == true) {
			if ((resource instanceof IFile) == false) {
				return super.checkConditions(pm, context);
			}
			if (isSupportedResource((IFile) getResource()) == true) {
				// checks for references to the deleted element(s)
				return super.checkConditions(pm, context);
			}
			return null;
		}
		return super.checkConditions(pm, context);
	}

	@Override
	protected ISearchParticipant getSearchParticipant() {
		return new DashboardSearchParticipant();
	}

	protected String changeTitle() {
		return "Changed Metrics";
	}

	@Override
	protected String[] getSupportedExtensions() {
		return new String[] { BEViewsElementNames.EXT_METRIC };
	}
	
	@Override
	protected Concept preProcessEntityChange(Concept refactorParticipant) {
		CompositeChange compositeChange = new CompositeChange("");
		try {
			Concept concept = processConcept(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
			
			// Rename metric tracking fields (user defined properties)
			Object elementToRefactor = getElementToRefactor();
			if (concept instanceof Metric && elementToRefactor instanceof PropertyDefinition) {
				PropertyDefinition propDef = (PropertyDefinition) elementToRefactor;
				EList<PropertyDefinition> properties = ((Metric) concept).getUserDefinedFields();
				for (PropertyDefinition propertyDef : properties) {
					if (propertyDef.getName().equals(propDef.getName())) {
						propertyDef.setName(getNewElementName());
					}
				}
			}
			return concept;
		} catch (CoreException e) {
			e.printStackTrace();
		}		
		return refactorParticipant;
		
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException, OperationCanceledException {
		// look through all concepts and make appropriate changes
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Metric changes:");
		List<Entity> allConcepts = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.METRIC });
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
	
//	//IStudioPasteParticipant implementations 
//	@Override
//	public IStatus pasteElement(String newName, IResource resource, IContainer target, boolean overwrite, IProgressMonitor pm) throws CoreException, OperationCanceledException {
//		if (newName == null || newName.trim().length() == 0){
//			return null;
//		}
//		if ((resource instanceof IFile || IndexUtils.isEntityType((IFile)resource)) == false) {
//			return null;
//		}
//		Entity entity = (Entity) IndexUtils.loadEObject(resource.getLocationURI());
//		String newPath = "/"+target.getFullPath().removeFirstSegments(1).toString()+"/";
//		String actualNewName = extractName(newName);
//		entity.setName(actualNewName);
//		entity.setNamespace(newPath);
//		entity.setFolder(newPath);
//		entity.setGUID(GUIDGenerator.getGUID());
//		TreeIterator<Object> contents = EcoreUtil.getAllProperContents(entity, false);
//		while (contents.hasNext()) {
//			Object object = (Object) contents.next();
//			if (object instanceof Entity) {
//				Entity objectAsEntity = (Entity) object;
//				if (StringUtil.isEmpty(objectAsEntity.getFolder()) == false) {
//					objectAsEntity.setFolder(newPath);
//				}
//				if (StringUtil.isEmpty(objectAsEntity.getNamespace()) == false) {
//					objectAsEntity.setNamespace(newPath);
//				}
//				if (StringUtil.isEmpty(objectAsEntity.getGUID()) == false) {
//					objectAsEntity.setGUID(GUIDGenerator.getGUID());
//				}
//				if (objectAsEntity instanceof PropertyDefinition){
//					PropertyDefinition objectAsPropertyDefinition = (PropertyDefinition) objectAsEntity;
//					if (StringUtil.isEmpty(objectAsPropertyDefinition.getOwnerPath()) == false) {
//						objectAsPropertyDefinition.setOwnerPath(entity.getFullPath());
//					}					
//				}
//			}
//		}
//		saveEntity(newName, entity, target, overwrite);
//		return null;
//	}
//
//	private String extractName(String name) {
//		int idx = name.lastIndexOf(".");
//		if (idx != -1){
//			return name.substring(0,idx);
//		}
//		return name;
//	}	
}