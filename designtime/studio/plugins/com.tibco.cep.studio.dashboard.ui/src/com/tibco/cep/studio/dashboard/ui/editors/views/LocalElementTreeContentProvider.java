package com.tibco.cep.studio.dashboard.ui.editors.views;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalElementTreeContentProvider implements ITreeContentProvider {
	
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	private ExceptionHandler exHandler;
	
	private boolean showTopLevelElementsAsLeaf;

	private String type;
	
	private List<String> rootTypes;
	
	private LocalElement input;

	public LocalElementTreeContentProvider(ExceptionHandler exHandler, String type, List<String> rootTypes, boolean showTopLevelElementsAsLeaf) {
		super();
		this.type = type;
		this.rootTypes = rootTypes;
		this.showTopLevelElementsAsLeaf = showTopLevelElementsAsLeaf;
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (input == null) {
			return EMPTY_ARRAY;
		}
		if (rootTypes == null || rootTypes.isEmpty() == true) {
			return new TreeContentNode[]{ new TreeContentNode(null, input) };
		}
		List<LocalElement> children = getChildren(input, rootTypes);
		TreeContentNode[] nodes = new TreeContentNode[children.size()];
		int i = 0;
		for (LocalElement child : children) {
			nodes[i] = new TreeContentNode(null, child);
			i++;
		}
		return nodes;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (input == null || element == null) {
			return false;
		}
		TreeContentNode node = (TreeContentNode) element;
		LocalElement data = (LocalElement) node.getData();
		return !getChildren(data, null).isEmpty();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (input == null || parentElement == null) {
			return EMPTY_ARRAY;
		}
		TreeContentNode node = (TreeContentNode) parentElement;
		LocalElement data = (LocalElement) node.getData();
		List<LocalElement> children = getChildren(data, null);
		TreeContentNode[] nodes = new TreeContentNode[children.size()];
		int i = 0;
		for (LocalElement child : children) {
			nodes[i] = new TreeContentNode(node, child);
			i++;
		}
		return nodes;		
	}

	@Override
	public Object getParent(Object element) {
		if (input == null || element == null) {
			return null;
		}
		TreeContentNode node = (TreeContentNode) element;		
		return node.getParent();
	}

	@Override
	public void dispose() {
		exHandler = null;
		type = null;
		input = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput == null) {
			input = null;
			return;
		}
		if (newInput instanceof LocalElement) {
			input = (LocalElement) newInput;
			if (type.equals(input.getElementType()) == false) {
				input = null;
				throw new IllegalArgumentException(input.getElementType()+" does not match "+type);
			}
			return;
		}
		throw new IllegalArgumentException(input.getElementType()+" is not a local element");
	}
	
	private List<LocalElement> getChildren(LocalElement element, List<String> particleNamesToUse) {
		if (input != element && BEViewsElementNames.isTopLevelElement(element.getElementType()) == true && showTopLevelElementsAsLeaf == true) {
			return Collections.emptyList();
		}
		List<String> particleNames = element.getParticleNames(true);
		if (particleNamesToUse != null && particleNamesToUse.isEmpty() == false) {
			particleNames.retainAll(particleNamesToUse);
		}
		List<LocalElement> children = new LinkedList<LocalElement>();
		for (String particleName : particleNames) {
			try {
				children.addAll(element.getChildren(particleName));
			} catch (Exception e) {
				exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not retrieve "+particleName+"(s) from "+element.getDisplayableName(), e));				
			}
		}
		return children;
	}	

}
