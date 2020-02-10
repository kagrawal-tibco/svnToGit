package com.tibco.cep.studio.core.index;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.StructuredElement;

public class StudioElementCollector extends DefaultStructuredElementVisitor {

	private List<EObject> fElements;
	private boolean fIncludeLocalVariables = true;

	public StudioElementCollector(List<EObject> elements) {
		this(elements, false);
	}
	
	public StudioElementCollector(List<EObject> elements, boolean includeLocalVariables) {
		this.setElements(elements);
		this.fIncludeLocalVariables = includeLocalVariables;
	}

	@Override
	public boolean visit(StructuredElement element) {
		if (element instanceof DesignerElement) {
			if (element instanceof LocalVariableDef && !includeLocalVariables()) {
				// don't add
			} else {
				getElements().add((DesignerElement) element);
			}
		}
		super.visit(element);
		return true;
	}

	public boolean includeLocalVariables() {
		return fIncludeLocalVariables;
	}

	public void setIncludeLocalVariables(boolean includeLocalVariables) {
		fIncludeLocalVariables = includeLocalVariables;
	}

	@Override
	public boolean visitEntityElement(EntityElement element) {
		switch (element.getElementType()) {
		case SIMPLE_EVENT:
		case TIME_EVENT:
		case CONCEPT:
		case SCORECARD:
		{			
			Entity entity = element.getEntity();
			if (entity instanceof Concept) {
				EList<PropertyDefinition> allProperties = ((Concept) entity).getAllProperties();
				addAll(allProperties);
			} else if (entity instanceof Event) {
				EList<PropertyDefinition> allUserProperties = ((Event) entity).getAllUserProperties();
				addAll(allUserProperties);
			}
			break;
		}

		default:
			break;
		}
		return super.visitEntityElement(element);
	}

	private void addAll(EList<PropertyDefinition> list) {
		if (list != null && list.size() > 0) {
			fElements.addAll(list);
		}
	}

	public void setElements(List<EObject> fElements) {
		this.fElements = fElements;
	}

	public List<EObject> getElements() {
		return fElements;
	}

}
