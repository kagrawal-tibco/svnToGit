package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider;
import com.tibco.cep.studio.core.index.resolution.ResolutionUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class RulesTextContentProvider implements IStructuredContentProvider {

	//INFO Anand Added to fix 1-APDXAR START
	private static final List<ELEMENT_TYPES> ELEMENT_TYPES_TO_HIDE = Arrays.asList(
		ELEMENT_TYPES.TEXT_CHART_COMPONENT,
		ELEMENT_TYPES.STATE_MACHINE_COMPONENT,
		ELEMENT_TYPES.PAGE_SELECTOR_COMPONENT, 
		ELEMENT_TYPES.ALERT_COMPONENT,
		ELEMENT_TYPES.CONTEXT_ACTION_RULE_SET,
		ELEMENT_TYPES.BLUE_PRINT_COMPONENT,
		ELEMENT_TYPES.QUERY_MANAGER_COMPONENT,
		ELEMENT_TYPES.SEARCH_VIEW_COMPONENT,
		ELEMENT_TYPES.RELATED_ASSETS_COMPONENT,
		ELEMENT_TYPES.DRILLDOWN_COMPONENT,
		ELEMENT_TYPES.QUERY,
		ELEMENT_TYPES.VIEW,
		ELEMENT_TYPES.DASHBOARD_PAGE,
		ELEMENT_TYPES.ASSET_PAGE,
		ELEMENT_TYPES.SEARCH_PAGE,
		ELEMENT_TYPES.PAGE_SET,
		ELEMENT_TYPES.SERIES_COLOR,
		ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET,
		ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET,
		ELEMENT_TYPES.HEADER,
		ELEMENT_TYPES.LOGIN,
		ELEMENT_TYPES.SKIN,
		ELEMENT_TYPES.ROLE_PREFERENCE,
		ELEMENT_TYPES.CHART_COMPONENT,
		ELEMENT_TYPES.TEXT_COMPONENT,
		ELEMENT_TYPES.DATA_SOURCE		
	);
	//INFO Anand Added to fix 1-APDXAR START

	public static final Object[] EMPTY_CHILDREN = new Object[0];
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public static Object[] getChildren(Object element) {
		if (element == null) {
			return EMPTY_CHILDREN;
		}
		if (element instanceof ElementContainer) {
			return getElementContainerChildren((ElementContainer) element);
		}
		if (element instanceof EntityElement) {
			return getEntityChildren(((EntityElement) element).getEntity());
		}
		if (element instanceof RuleElement) {
			return getRuleElementChildren((RuleElement) element);
		}
		if (element instanceof Entity) {
			return getEntityChildren((Entity) element);
		}
		if (element instanceof FunctionsCategory) {
			return getFunctionsCategoryChildren((FunctionsCategory) element);
		}
		if (element instanceof PropertyDefinition) {
			return getPropertyChildren((PropertyDefinition) element);
		}
		if (element instanceof VariableDefinition) {
			return getVariableDefinitionChildren((VariableDefinition) element);
		}
		// for all other objects, query the resolution providers
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		List<Object> result = new ArrayList<Object>();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			Object[] prochildren = provider.getChildren(element);
			if (prochildren != null) {
				result.addAll(Arrays.asList(prochildren));
			}
		}

		return (Object[]) result.toArray(new Object[result.size()]);
	}

	private static Object[] getVariableDefinitionChildren(VariableDefinition element) {
		Object typeElement = ElementReferenceResolver.resolveVariableDefinitionType(element);
		return getChildren(typeElement);
	}

	private static Object[] getRuleElementChildren(RuleElement element) {
		String returnType = element.getRule().getReturnType();
		if (returnType != null) {
			DesignerElement returnElement = IndexUtils.getElement(element.getIndexName(), ModelUtils.convertPackageToPath(returnType));
			return getChildren(returnElement);
		}
		return EMPTY_CHILDREN;
	}

	//INFO Anand Modified to fix 1-APDXAR START
	private static Object[] getElementContainerChildren(ElementContainer element) {
		List<Object> children = new ArrayList<Object>(element.getEntries());
		ListIterator<Object> childrenListIterator = children.listIterator();
		while (childrenListIterator.hasNext()) {
			Object obj = childrenListIterator.next();
			if (!(obj instanceof DesignerElement)) {
				continue;
			}
			DesignerElement child = (DesignerElement) obj;
			if (ELEMENT_TYPES_TO_HIDE.contains(child.getElementType()) == true) {
				childrenListIterator.remove();
			}
		}
		// for all other objects, query the resolution providers
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		EObject rootContainer = IndexUtils.getRootContainer(element);
		if (rootContainer instanceof DesignerProject) {
			String name = ((DesignerProject) rootContainer).getName();
			for (IElementResolutionProvider provider : elementResolutionProviders) {
				Object[] prochildren = provider.getElementContainerChildren(element, name);
				for (Object object : prochildren) {
					children.add(object);
				}
			}
		}

		return children.toArray();
	}

	private static Object[] getFunctionsCategoryChildren(
			FunctionsCategory element) {
		List<Object> children = new ArrayList<Object>();
		for (Iterator<?> iterator = element.all(); iterator.hasNext();) {
			children.add(iterator.next());
		}

		return children.toArray();
	}

	public static Object[] getEntityChildren(Entity element) {
		if (element instanceof Concept) {
			return getConceptChildren((Concept) element);
		}
		if (element instanceof Event) {
			return getEventChildren((Event) element);
		}
		if (element instanceof PropertyDefinition) {
			return getPropertyChildren((PropertyDefinition) element);
		}
		return EMPTY_CHILDREN;
	}
	
	public static Object[] getConceptChildren(Concept element) {
		List<Object> children = new ArrayList<Object>();
		children.addAll(element.getAllProperties());
		return children.toArray();
	}

	public static Object[] getEventChildren(Event element) {
		List<Object> children = new ArrayList<Object>();
		children.addAll(element.getAllUserProperties());
		return children.toArray();
	}
	
	public static Object[] getPropertyChildren(PropertyDefinition element) {
		List<Object> children = new ArrayList<Object>();
		if (element.getConceptTypePath() != null) {
			String conceptPath = element.getConceptTypePath();
			Concept concept = IndexUtils.getConcept(((Entity)element.eContainer()).getOwnerProjectName(), conceptPath);
			if (concept != null) {
				return getConceptChildren(concept);
			}
		}
		return children.toArray();
	}
	
}
