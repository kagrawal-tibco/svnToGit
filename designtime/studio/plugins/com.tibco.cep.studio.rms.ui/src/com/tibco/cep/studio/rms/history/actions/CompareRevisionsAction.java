package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

/**
 * 
 * @author sasahoo
 *
 */
public class CompareRevisionsAction extends Action {
	
//	private TableViewer viewer;
	
	public CompareRevisionsAction(TableViewer viewer) {
		super("Compare revisions");
//		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
//		ISelection selection = viewer.getSelection();
//		if (selection instanceof IStructuredSelection) {
//			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
//		}
	}
}