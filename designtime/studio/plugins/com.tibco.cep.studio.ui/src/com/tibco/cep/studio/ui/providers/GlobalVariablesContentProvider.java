package com.tibco.cep.studio.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.ui.views.GlobalVariablesView.GlobalVariableContainerNode;

public class GlobalVariablesContentProvider implements
		IStructuredContentProvider, ITreeContentProvider {

	public GlobalVariablesContentProvider() {
	}

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getChildren(Object element) {
		List<Object> children = new ArrayList<Object>(0);
		if (element instanceof GlobalVariablesProvider) {
			return ((GlobalVariablesProvider) element).getVariables().toArray();
		} else if (element instanceof GlobalVariableContainerNode) {
			return ((GlobalVariableContainerNode) element).getChildren().toArray();
		} else if (element instanceof Object[]) {
			return (Object[]) element;
		}
		return children.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof GlobalVariableContainerNode) {
			return true;
		}
		return false;
	}
}
