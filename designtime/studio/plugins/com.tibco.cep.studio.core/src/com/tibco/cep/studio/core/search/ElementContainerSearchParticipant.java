package com.tibco.cep.studio.core.search;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;

public class ElementContainerSearchParticipant extends EntitySearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		searchEntries((ElementContainer) index, resolvedElement, result);
	}

	private void searchEntries(ElementContainer container, EObject resolvedElement,
			SearchResult result) {
		EList<DesignerElement> entries = container.getEntries();
		for (DesignerElement designerElement : entries) {
			if (isEqual(designerElement, resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, container.eClass(), designerElement));
			}
			if (designerElement instanceof ElementContainer) {
				searchEntries((ElementContainer) designerElement, resolvedElement, result);
			}
		}
	}

	@Override
	protected boolean isEqual(Object element, Object resolvedElement) {
		if (element instanceof ElementContainer
				&& resolvedElement instanceof ElementContainer) {
			ElementContainer folder1 = (ElementContainer) element;
			ElementContainer folder2 = (ElementContainer) resolvedElement;
			if (folder1.getName().equals(folder2.getName())
					&& areParentsEqual(folder1, folder2)) {
				return true;
			}
		}
		return super.isEqual(element, resolvedElement);
	}

	private boolean areParentsEqual(ElementContainer folder1,
			ElementContainer folder2) {
		if (folder1.eContainer() == null && folder2.eContainer() == null) {
			return true;
		}
		return isEqual(folder1.eContainer(), folder2.eContainer());
	}

}
