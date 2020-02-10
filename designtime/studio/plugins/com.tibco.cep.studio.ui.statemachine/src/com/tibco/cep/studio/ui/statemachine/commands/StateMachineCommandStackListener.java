package com.tibco.cep.studio.ui.statemachine.commands;

import java.util.EventObject;

import org.eclipse.emf.common.command.CommandStackListener;

import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineCommandStackListener implements CommandStackListener {
	
	private StateMachineEditor editor;
	
	/**
	 * @param editor
	 */
	public StateMachineCommandStackListener(StateMachineEditor editor) {
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