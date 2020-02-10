package com.tibco.cep.studio.ui.search;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.tibco.cep.studio.core.search.SearchUtils;

public class NestedStudioElementMatch implements IAdaptable,
		IWorkbenchAdapter, IStudioNestedMatch {

	private static final Object[] EMPTY_CHILDREN = new Object[0];
	
	private IStudioMatch fParentMatch;
	private EObject fMatchedElement;

	private boolean fExactMatch = true;

	public NestedStudioElementMatch(IStudioMatch parentMatch, EObject matchedElement) {
		this.fParentMatch = parentMatch;
		this.fMatchedElement = matchedElement;
	}

	public EObject getMatchedElement() {
		return fMatchedElement;
	}

	public Object getAdapter(Class adapter) {
		if (IWorkbenchAdapter.class.equals(adapter)) {
			return this;
		}
		return null;
	}

	public Object[] getChildren(Object o) {
		return EMPTY_CHILDREN;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return PlatformUI.getWorkbench().getSharedImages().
		getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
	}
	
	public String getLabel(Object object) {
		object = fMatchedElement;
		StringBuilder builder = new StringBuilder();
		builder.append(SearchUtils.getElementMatchLabel(object));
		if (!fExactMatch) {
			builder.append(" (potential match)");
		}
		return builder.toString();
	}

	public Object getParent(Object o) {
		return null;
	}

	public void setExact(boolean exact) {
		this.fExactMatch  = exact;
	}

	public boolean isExact() {
		return fExactMatch;
	}
	
}
