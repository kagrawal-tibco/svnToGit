package com.tibco.cep.studio.wizard.as.internal.ui.tree;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

import com.tibco.as.space.SpaceDef;

public class SpacePatternFilter extends PatternFilter {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.internal.dialogs.PatternFilter#isElementSelectable(java.lang.Object)
	 */
	public boolean isElementSelectable(Object element) {
		return element instanceof SpaceDef;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.internal.dialogs.PatternFilter#isElementMatch(org.eclipse.jface.viewers.Viewer, java.lang.Object)
	 */
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		boolean match = false;
		if (element instanceof TreeNode) {
			TreeNode treeNode = (TreeNode) element;
			Object userObject = treeNode.getValue();
			if (null != userObject && userObject instanceof SpaceDef) {
				SpaceDef spaceDef = (SpaceDef) userObject;
				String text = spaceDef.getName();
				match = wordMatches(text);
			}
		}
		return match;
	}

}

