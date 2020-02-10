/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IUndoableCommand;

/**
 * A command manager is maintained per table in a project
 * @author aathalye
 *
 */
public class CommandManager<C extends IExecutableCommand> {
	
	private Map<Key, CommandStack<C>> commandStackMap = new HashMap<Key, CommandStack<C>>();
	
	private class Key {
		
		private String project;
		
		private String tablePath;
		
		Key(String project, String tablePath) {
			this.project = project;
			this.tablePath = tablePath;
		}

		/**
		 * @return the project
		 */
		public final String getProject() {
			return project;
		}

		/**
		 * @return the tablePath
		 */
		public final String getTablePath() {
			return tablePath;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof CommandManager.Key)) {
				return false;
			}
			CommandManager.Key other = (CommandManager.Key)obj;
			if (!this.project.equals(other.getProject())) {
				return false;
			}
			if (!this.tablePath.equals(other.getTablePath())) {
				return false;
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return project.hashCode() * tablePath.hashCode();
		}
	}
	
	@SuppressWarnings("unchecked")
	public CommandStack<C> getCommandStack(String project, String tablePath) {
		CommandManager<C>.Key key = new CommandManager.Key(project, tablePath);
		CommandStack<C> commandStack = commandStackMap.get(key);
		if (commandStack == null) {
			commandStack = new CommandStack<C>(project);
			//Create a checkpoint by default
			CheckPointCommand checkpoint = 
				new CheckPointCommand(null, null, null, null, new Date());
			commandStack.addCommand((C)checkpoint);
			commandStackMap.put(key, commandStack);
		}
		return commandStack;
	}
	
	public void execute(CommandStack<C> stack, C command) {
		if (command == null) {
			return;
		}
		command.execute();
		//Push the command
		stack.addCommand(command);
		stack.fireCommandAdded(command);
	}
	
	public void undo(CommandStack<C> stack) {
		if (!stack.isEmpty()) {
			C command = stack.pop();
			if (command != null) {
				if (command instanceof IUndoableCommand) {
					((IUndoableCommand)command).undo();
					stack.fireCommandUndone(command);
				}
			}
		}
	}
	
	/**
	 * Empties the command stack, resets its error recorder, 
	 * removes all the listeners, remove map reference
	 * @param stack the command stack
	 */
	public void clear(CommandStack<C> stack, String tablePath) {
		if (stack != null) {
			// clear the stack
			stack.clear();
			// remove the error recorder
			stack.setErrorRecorder(null);
			// clear the listeners
			stack.clearCommandStackListeners();						
			// remove from map
			commandStackMap.remove(new Key(stack.getProject(), tablePath));
		}
	}
}
