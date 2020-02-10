/**
 * 
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

/**
 * Use this class to perform any editor ops like dirty/un-dirty,
 * based on command stack changes.
 *
 */
public class DecisionTableCommandStackEditorOpsListener implements
		ICommandStackListener<IExecutableCommand> {
	
	/**
	 * The editor instance to perform ops
	 */
	private IDecisionTableEditor editor;
	
	public DecisionTableCommandStackEditorOpsListener(IDecisionTableEditor editor) {
		this.editor = editor;
	}

	
	@Override
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
		DecisionTableUtil.invokeOnDisplayThread(new Runnable() {
			public void run() {
				editor.modified();
			}
		}, false);
	}

	
	@Override
	public void commandUndone(CommandEvent<IExecutableCommand> commandEvent) {
		commandExecuted(commandEvent);
	}
}
