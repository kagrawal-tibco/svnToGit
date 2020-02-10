package com.tibco.cep.studio.ui.providers;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class QualifiedNameContentProvider implements ITreeContentProvider {

	private String projectName;

	public QualifiedNameContentProvider(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof List) {
			return ((List) parentElement).toArray();
		}
		if (parentElement instanceof Symbol) {
			Symbol symbol = (Symbol) parentElement;
			String type = symbol.getType();
			String folder = ModelUtils.convertPackageToPath(type);
			Object typeElement = CommonIndexUtils.getElement(projectName, folder);
			if (typeElement instanceof EntityElement) {
				return getChildren(((EntityElement) typeElement).getEntity());
			} else {
				System.out.println("something else");
			}
		}
		if (parentElement instanceof Concept) {
			return ((Concept) parentElement).getPropertyDefinitions(false).toArray();
		}
		if (parentElement instanceof Event) {
			return ((Event) parentElement).getProperties().toArray();
		}
		if (parentElement instanceof PropertyDefinition) {
			PropertyDefinition prop = (PropertyDefinition) parentElement;
			if (prop.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE || prop.getType() == PROPERTY_TYPES.CONCEPT) {
				Concept concept = IndexUtils.getConcept(projectName, prop.getConceptTypePath());
				if (concept != null) {
					return concept.getPropertyDefinitions(false).toArray();
				}
			}
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof PropertyDefinition) {
			PropertyDefinition prop = (PropertyDefinition) element;
			if (prop.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE || prop.getType() == PROPERTY_TYPES.CONCEPT) {
				return true;
			}
			return false;
		}
		return true;
	}

}
