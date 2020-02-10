/**
 * 
 */
package com.tibco.cep.decision.table.command.listeners;

import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * Use this class to perform any editor ops like dirty/un-dirty,
 * based on command stack changes.
 * @author aathalye
 *
 */
public class DecisionTableCommandStackEditorOpsListener implements
		ICommandStackListener<IExecutableCommand> {
	
	/**
	 * The editor instance to perform ops
	 */
	private DecisionTableEditor editor;
	
	public DecisionTableCommandStackEditorOpsListener(DecisionTableEditor editor) {
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandExecuted(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	public void commandExecuted(CommandEvent<IExecutableCommand> commandEvent) {
		IExecutableCommand command = (IExecutableCommand)commandEvent.getSource();
		CommandStack<IExecutableCommand> commandStack = command.getOwnerStack();
		//Bring editor back to last save position if topmost command is checkpoint
		IExecutableCommand topCommand = commandStack.peek();
		
		if (!topCommand.shouldDirty()) {
			//Only if not a defunct command
			if (editor.isDirty() && !topCommand.isDefunct()) {
				editor.editorContentRestored();
				return;
			}
		}
		//If not dirty, then make it dirty if command stack is not empty
		if (!command.isDefunct()) {
			if (command.shouldDirty()) {
				makeEditorDirty();
			} else {
				if (editor.isDirty()) {
					editor.editorContentRestored();
				}
			}
		}
	}
	
	private void makeEditorDirty() {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				editor.editorContentModified();
			}
		}, false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandStackListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	
	public void commandUndone(CommandEvent<IExecutableCommand> commandEvent) {
		commandExecuted(commandEvent);
	}
}
