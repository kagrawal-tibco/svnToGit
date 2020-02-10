package com.tibco.cep.studio.core.search;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class EntitySearchParticipant implements ISearchParticipant {

	public EntitySearchParticipant() {
		super();
	}

	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		
		SearchResult result = new SearchResult();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(project);
		if (resolvedElement instanceof EObject) {
			searchEntity((EObject) resolvedElement, index, nameToFind, monitor, result);
		} else {
//			System.out.println("resolved element not an EObject: "+resolvedElement);
		}
		return result;

	}

	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		
	}
	
	protected boolean isEqual(Object element, Object resolvedElement) {
		// need to fill this out, or add "equals" method to all possible comparables
		if (element == null) {
			return false;
		}
		if (element instanceof PropertyDefinition
				&& resolvedElement instanceof PropertyDefinition) {
			PropertyDefinition prop1 = (PropertyDefinition) element;
			PropertyDefinition prop2 = (PropertyDefinition) resolvedElement;
			if (prop1.getName().equals(prop2.getName()) 
					&& (prop1.getOwnerPath() != null && prop1.getOwnerPath().equals(prop2.getOwnerPath()))
					&& prop1.getType().equals(prop2.getType())) {
				return true; // TODO : is this sufficient?
			}
			return false;
		}
		if (resolvedElement instanceof EntityElement && element instanceof Entity) {
			resolvedElement = ((EntityElement)resolvedElement).getEntity();
		}
		if (element instanceof Entity && resolvedElement instanceof Entity) {
			return IndexUtils.areEqual((Entity)element, (Entity)resolvedElement);
		}
		return element.equals(resolvedElement);
	}

	protected ElementMatch createElementMatch(int featureId,
			EClass class1, EObject matchedElement) {
		ElementMatch match = SearchFactory.eINSTANCE.createElementMatch();
		if (featureId == DEFINITION_FEATURE) {
			match.setFeature(matchedElement);
		} else {
			EStructuralFeature feature = class1.getEStructuralFeature(featureId);
			match.setFeature(feature);
		}
		match.setMatchedElement(matchedElement);
		return match;
	}

}
