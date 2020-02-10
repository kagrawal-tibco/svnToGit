package com.tibco.cep.studio.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Tree;

import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;

public class NavigatorContextProvider implements IContextProvider {

	private ProjectExplorer fNavigator;

	public NavigatorContextProvider(ProjectExplorer projectExplorer) {
		this.fNavigator = projectExplorer;
	}

	public IContext getContext(Object target) {
		fNavigator.getCommonViewer().getSelection();
		return HelpSystem.getContext(INavigatorHelpContextIds.DESIGNER_NAVIGATOR);
	}

	public int getContextChangeMask() {
		return SELECTION;
	}

	public String getSearchExpression(Object target) {
		if (target instanceof Tree) {
			TreeSelection selection3 = (TreeSelection) fNavigator.getCommonViewer().getSelection();
			Object firstElement = selection3.getFirstElement();
			if (firstElement instanceof IFile) {
				return ((IFile)firstElement).getFileExtension();
			} else {
				return firstElement == null ? "" : firstElement.toString();
			}
		}
		return target.toString();
	}

}
