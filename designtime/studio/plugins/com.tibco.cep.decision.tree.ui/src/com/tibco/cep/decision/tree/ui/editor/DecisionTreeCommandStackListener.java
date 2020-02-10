package com.tibco.cep.decision.tree.ui.editor;

import java.util.EventObject;

import org.eclipse.emf.common.command.CommandStackListener;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeCommandStackListener implements CommandStackListener {
	
	private DecisionTreeEditor editor;
	
	/**
	 * @param editor
	 */
	public DecisionTreeCommandStackListener(DecisionTreeEditor editor) {
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
	 */
	@Override
	public void commandStackChanged(final EventObject event) {
		editor.getControl().getDisplay().asyncExec(
				new Runnable() {
					/* (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						editor.modified();
					}
				});
	}
}