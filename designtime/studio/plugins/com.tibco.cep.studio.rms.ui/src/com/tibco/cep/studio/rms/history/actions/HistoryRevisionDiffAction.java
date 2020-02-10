package com.tibco.cep.studio.rms.history.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

/**
 * 
 * @author sasahoo
 *
 */
public class HistoryRevisionDiffAction extends Action {
	
	private TableViewer viewer;
	
	public HistoryRevisionDiffAction(TableViewer viewer) {
		super("Compare with working copy");
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