/**
 * 
 */
package com.tibco.cep.decision.table.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.checkpoint.UndoableCommandCheckpointEntry;
import com.tibco.cep.decision.table.command.ICommandStackListener;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IIndexableCommand;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;

/**
 * @author aathalye
 *
 */
public class CommandStack<C extends IExecutableCommand> {
	
	private Stack<C> commandStack = new Stack<C>();
	
	private List<ICommandStackListener<C>> listeners = new ArrayList<ICommandStackListener<C>>();
	
	/**
	 * An error recorder to keep track of errors created while performing actions
	 */
	private DecisionTableErrorRecorder errorRecorder;
	
	/**
	 * What project we are working on
	 */
	private String project;
	
	protected CommandStack(String project, DecisionTableErrorRecorder errorRecorder) {
		this.project = project;
		this.errorRecorder = errorRecorder;
	}
	
	protected CommandStack(String project) {
		this.project = project;
	}
	
	public void addCommand(C command) {
		//if (!commandStack.contains(command)) {
			commandStack.push(command);
		//}
	}
	
	public C pop() {
		C command = peek();
		if (command.canUndo()) {
			command = commandStack.pop();
		}
		return command;
	}
	
	public C peek() {
		return commandStack.peek();
	}
	
	public boolean isEmpty() {
		return commandStack.isEmpty();
	}
	
	public void addCommandStackListsner(ICommandStackListener<C> commandStackListener) {
		listeners.add(commandStackListener);
	}
	
	public void removeCommandStackListsner(ICommandStackListener<C> commandStackListener) {
		listeners.remove(commandStackListener);
	}
	
	public void fireCommandAdded(C command) {
		CommandEvent<C> event = new CommandEvent<C>(command);
		for (ICommandStackListener<C> listener : listeners) {
			listener.commandExecuted(event);
		}
	}
	
	public void fireCommandUndone(C command) {
		CommandEvent<C> event = new CommandEvent<C>(command);
		for (ICommandStackListener<C> listener : listeners) {
			listener.commandUndone(event);
		}
	}
	
	public C getMostRecentCheckpoint() {
		Iterator<C> commands = commandStack.iterator();
		while (commands.hasNext()) {
			C command = commands.next();
			if (command instanceof CheckPointCommand) {
				return command;
			}
		}
		return null;
	}
	
	
	
	/**
	 * @return the errorRecorder
	 */
	public final DecisionTableErrorRecorder getErrorRecorder() {
		return errorRecorder;
	}
	

	/**
	 * @param errorRecorder the errorRecorder to set
	 */
	public final void setErrorRecorder(DecisionTableErrorRecorder errorRecorder) {
		this.errorRecorder = errorRecorder;
	}

	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	public class CheckpointDeltaList extends ArrayList<UndoableCommandCheckpointEntry<C>> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 331719428416446707L;

		/* (non-Javadoc)
		 * @see java.util.ArrayList#add(java.lang.Object)
		 */
		
		public boolean add(UndoableCommandCheckpointEntry<C> e) {
			if (!contains(e)) {
				return super.add(e);
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see java.util.ArrayList#add(int, java.lang.Object)
		 */
		@Override
		public void add(int index, UndoableCommandCheckpointEntry<C> element) {
			if (!contains(element)) {
				super.add(index, element);
			}
		}

		/* (non-Javadoc)
		 * @see java.util.ArrayList#contains(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		public int indexOf(Object o) {
			if (o == null) {
				for (int i = 0; i < size(); i++) {
					if (get(i) == null) {
						return i;
					}
				}
			} 
			if (!(o instanceof ICheckpointDeltaEntry)) {
				return -1;
			} else {
				UndoableCommandCheckpointEntry<C> other = (UndoableCommandCheckpointEntry<C>)o;
				for (int i = 0; i < size(); i++) {
					if (other.like(get(i))) {
						return i;
					}
				}
			}
			return -1;
		}
	}
	
	public CheckpointDeltaList getCheckpointDelta() {
		CheckpointDeltaList checkpointDelta = new CheckpointDeltaList();
		//Indicating the most recent checkpoint
		int counter = 0;
		for (int loop = commandStack.size(); loop > 0; loop--) {
			C command = commandStack.elementAt(loop - 1);
			if (command instanceof CheckPointCommand) {
				if (counter < 1) {
					counter++;
				} else {
					//We found the next checkpoint
					break;
				}
			} else {
				//CheckpointDeltaEntryFactory.INSTANCE.getDeltaEntryList(command, checkpointDelta);
				if (!command.isDefunct()) {
					boolean needsReIndex = (command instanceof IIndexableCommand) ? true : false;
					checkpointDelta.add(new UndoableCommandCheckpointEntry<C>(command, needsReIndex));
				}
			}
		}
		return checkpointDelta;
	}
	
	public void removeLast() {
		commandStack.pop();
	}
	
	/**
	 * Clears the command stack
	 */
	public void clear() {
		commandStack.clear();
	}
	
	/**
	 * Removes all the listeners for the command stack
	 */
	public void clearCommandStackListeners() {
		listeners.clear();
	}
	
	/**
	 * Checks if a listener is already registered on the command stack
	 * @param commandStackListener the listener to be checked
	 * @return boolean true if listener exists; false otherwise 
	 */
	public boolean containsListener(ICommandStackListener<IExecutableCommand> commandStackListener) {
		return listeners.contains(commandStackListener);
	}
}
