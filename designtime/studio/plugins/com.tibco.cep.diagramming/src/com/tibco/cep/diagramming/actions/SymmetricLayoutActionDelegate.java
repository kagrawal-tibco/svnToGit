package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.utils.DiagramUtils;

/**
 * 
 * @author hitesh
 *
 */

public class SymmetricLayoutActionDelegate implements IEditorActionDelegate {

	private IWorkbenchPage page;
	public static final int SYMMETRIC_STYLE = 1;

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if(targetEditor!=null)
			page =  targetEditor.getEditorSite().getPage();
	}

	@Override
	public void run(IAction action) {
		DiagramUtils.layoutAction(page, SYMMETRIC_STYLE);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
