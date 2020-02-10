package com.tibco.cep.studio.ui.editors.rules;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class RulesContentProvider implements ITreeContentProvider {

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof CommonTree) {
			Object[] children = ((CommonTree)parentElement).getChildren().toArray();
			List<Object> kids = new ArrayList<Object>();
			for (Object object : children) {
//				if (((CommonTree)object).getType() == RulesParser.NAME) {
//					// skip name children
//					continue;
//				}
				if (object instanceof RulesASTNode && ((RulesASTNode)object).isHidden()) {
					// skip
					continue;
				}

				kids.add(object);
			}
			return kids.toArray();
		}
		return new Object[0];
	}

	public Object getParent(Object element) {
		if (element instanceof CommonTree) {
			return ((CommonTree)element).parent;
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof CommonTree) {
			return ((CommonTree)element).getChildCount() > 0;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

}
