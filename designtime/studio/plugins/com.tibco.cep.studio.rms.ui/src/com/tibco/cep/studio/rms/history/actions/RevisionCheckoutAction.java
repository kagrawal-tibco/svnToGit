package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

/**
 * 
 * @author sasahoo
 *
 */
public class RevisionCheckoutAction extends Action {
	
	private TableViewer viewer;
	
	public RevisionCheckoutAction(TableViewer viewer) {
		super("Checkout...");
		this.viewer = viewer;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if(!viewer.getSelection().isEmpty()){
			//TODO
		}
	}
}