/**
 * 
 */
package com.tibco.cep.decision.table.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.decision.table.checkpoint.UndoableCommandCheckpointEntry;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.CheckPointCommand;
import com.tibco.cep.decision.table.command.impl.CommandManager;
import com.tibco.cep.decision.table.command.impl.CommandStack;
import com.tibco.cep.decision.table.command.impl.DuplicateCommand;
import com.tibco.cep.decision.table.command.impl.GenericMetadataCommand;
import com.tibco.cep.decision.table.command.impl.ModifyCellCommand;
import com.tibco.cep.decision.table.command.impl.ModifyCommandExpression;
import com.tibco.cep.decision.table.command.impl.MoveCommand;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.impl.RenameCommand;
import com.tibco.cep.decision.table.command.impl.UndoableMetadataCommand;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnDefaultCellTextStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.command.memento.StateMemento;
import com.tibco.cep.decision.table.command.memento.TableRuleStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.dt.DecisionTableErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.DecisionTableSyntaxProblemParticipant;
import com.tibco.cep.studio.core.validation.dt.DelegatingRuleErrorsRecorder;
import com.tibco.cep.studio.core.validation.dt.IMarkerErrorRecorder;
import com.tibco.cep.studio.core.validation.dt.TableModelErrorsRecorder;

/**
 * All operations required by clients to talk to command framework
 * should be added here.
 * @author aathalye
 *
 */
public final class CommandFacade {
	
	private static CommandFacade INSTANCE = new CommandFacade();
	
	private CommandManager<IExecutableCommand> commandManager;
	
	private CommandFacade() {
		commandManager = new CommandManager<IExecutableCommand>();
	}
	
	public static CommandFacade getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Execute a cell modification request by wrapping
	 * into an {@link IUndoableCommand}
	 * @param project -> The project name
	 * @param tablePath -> The path of the table e.g /Virtual_RF/TableName
	 * @param tableEModel -> The emodel object
	 * @param commandReceiver -> The command receiver is a {@link TableRuleVariable}
	 * @param oldValue -> The old {@link TableRuleVariable}
	 * @param changedExpression -> The changed cell value
	 */
	public void executeModify(String project,
			                  Table tableEModel,
			                  TableTypes tableType,
			                  TableRuleVariable commandReceiver,
			                  Object oldValue,
			                  ModifyCommandExpression changedExpression) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		ModifyCellCommand modifyCellCommand = 
			new ModifyCellCommand(commandStack, tableEModel, tableType, commandReceiver, changedExpression);
		modifyCellCommand.saveMemento(new StateMemento<TableRuleVariable>(commandReceiver, oldValue));
		commandManager.execute(commandStack, modifyCellCommand);
	}
	
	/**
	 * @param project
	 * @param tablePath
	 * @param tableEModel
	 * @param listener
	 * @return
	 */
	public Object executeAddRow(String project,
			                    Table tableEModel,
			                    TableTypes tableType,
			                    ICommandCreationListener<AddCommand<TableRule>, TableRule> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(
				project, getTablePath(tableEModel));
		AddCommand<TableRule> addCommand = 
			new AddCommand<TableRule>(commandStack, tableEModel, tableType, null, listener);
		commandManager.execute(commandStack, addCommand);
		return addCommand.getCreatedObject();
	}
	
	private String getTablePath(Table tableEModel) {
		String tablePath = tableEModel.getFolder() + tableEModel.getName();
		return tablePath;
	}
	
	/**
	 * @param project
	 * @param tablePath
	 * @param tableEModel
	 * @param listener
	 * @return
	 */
	public Object executeDuplication(String project,
			                         Table tableEModel,
			                         TableTypes tableType,
			                         ICommandCreationListener<DuplicateCommand, TableRule> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		DuplicateCommand duplicateCommand = 
			new DuplicateCommand(commandStack, tableEModel, tableType, null, listener);
		commandManager.execute(commandStack, duplicateCommand);
		return duplicateCommand.getCreatedObject();
	}
	
	/**
	 * @param project
	 * @param tablePath
	 * @param tableEModel
	 * @param listener
	 * @return
	 */
	public Object executeCellRemoval(String project,
			                     Table tableEModel,
			                     TableTypes tableType,
			                     ICommandCreationListener<RemoveCommand<TableRuleVariable>, TableRuleVariable> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		RemoveCommand<TableRuleVariable> removeCommand = 
			new RemoveCommand<TableRuleVariable>(tableEModel, tableType, null, commandStack, listener);
		commandManager.execute(commandStack, removeCommand);
		return removeCommand.getRemovedObject();
	}
	
	/**
	 * @param project
	 * @param tablePath
	 * @param tableEModel
	 * @param listener
	 * @return
	 */
	public Object executeRemoval(String project,
			                     Table tableEModel,
			                     TableTypes tableType,
			                     ICommandCreationListener<RemoveCommand<TableRule>, TableRule> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		RemoveCommand<TableRule> removeCommand = 
			new RemoveCommand<TableRule>(tableEModel, tableType, null, commandStack, listener);
		commandManager.execute(commandStack, removeCommand);
		return removeCommand.getRemovedObject();
	}
	
	/**
	 * Add listeners to the {@link CommandStack}
	 * @param project
	 * @param tablePath
	 * @param listeners
	 */
	public void addCommandStackListeners(String project, 
                                         String tablePath,
                                         ICommandStackListener<IExecutableCommand>... listeners) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, tablePath);
		for (ICommandStackListener<IExecutableCommand> listener : listeners) {
			if (!commandStack.containsListener(listener)) {
				commandStack.addCommandStackListsner(listener);
			}
		}
	}
	/**
	 * Remove listener from the {@link CommandStack}
	 * @param project
	 * @param tablePath
	 * @param listeners
	 */
	public void removeCommandStackListener(String project, 
                                         String tablePath,
                                         ICommandStackListener<IExecutableCommand> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, tablePath);
		if (commandStack.containsListener(listener)) {
			commandStack.removeCommandStackListsner(listener);
		}
	}	

	public DecisionTableErrorRecorder getErrorRecorder(String project, 
			                                           Table tableEModel) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		return commandStack.getErrorRecorder();
	}
	
	/**
	 * Call undo operations on the {@link CommandStack} for this project, and tablepath
	 * @param project
	 * @param tablePath
	 */
	public void undo(String project, 
                     String tablePath) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, tablePath);
		commandManager.undo(commandStack);
	}
	
	private CommandStack<IExecutableCommand> getCommandStack(String project, 
			                                                 String tablePath) {
		if (project == null || tablePath == null) {
			throw new IllegalArgumentException("Project and Table path should not be null");
		}
		CommandStack<IExecutableCommand> commandStack = commandManager.getCommandStack(project, tablePath);
		if (commandStack.getErrorRecorder() == null) {
			DecisionTableElement decisionTableElement = 
				(DecisionTableElement)IndexUtils.getElement(project, tablePath, ELEMENT_TYPES.DECISION_TABLE);
			if (decisionTableElement != null) {
				IResource resource = IndexUtils.getFile(project, decisionTableElement);
				IMarkerErrorRecorder<DecisionTableSyntaxProblemParticipant> problemsViewErrRecorder = 
								new TableModelErrorsRecorder(resource);
				DecisionTableErrorRecorder delegatingRecorder = new DelegatingRuleErrorsRecorder(problemsViewErrRecorder);
				commandStack.setErrorRecorder(delegatingRecorder);
			}
		}
		return commandStack;
	}
	
	/**
	 * 
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 */
	public void createCheckpoint(String project,
			                     Table tableEModel,
			                     TableTypes tableType) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		CheckPointCommand checkpoint = 
			new CheckPointCommand(commandStack, tableEModel, tableType, tableEModel, new Date());
		commandManager.execute(commandStack, checkpoint);
	}
	
	/**
	 * Get delta between 2 check points.
	 * @param project
	 * @param tablePath
	 * @param tableEModel
	 * @return
	 */
	public List<UndoableCommandCheckpointEntry<IExecutableCommand>> getCheckpointDeltaObjects(String project,
			                                                  Table tableEModel) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		CommandStack<IExecutableCommand>.CheckpointDeltaList checkpointDeltaCommands = 
					commandStack.getCheckpointDelta();
		List<UndoableCommandCheckpointEntry<IExecutableCommand>> deltaObjects = 
			new ArrayList<UndoableCommandCheckpointEntry<IExecutableCommand>>(checkpointDeltaCommands);
		
		return deltaObjects;
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnAddition(String project,
			                     		Table tableEModel,
			                     		TableTypes tableType,
			                     		ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		AddCommand<ColumnPositionStateMemento> addCommand = 
			new AddCommand<ColumnPositionStateMemento>(commandStack, tableEModel, tableType, null, listener);
		commandManager.execute(commandStack, addCommand);
		return addCommand.getCreatedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnRemoval(String project, 
			                           Table tableEModel,
			                           TableTypes tableType,
			                           ICommandCreationListener<RemoveCommand<Object>, Object> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(
				project, getTablePath(tableEModel));
		RemoveCommand<Object> removeCommand = 
			new RemoveCommand<Object>(tableEModel, tableType, null, commandStack, listener);
		commandManager.execute(commandStack, removeCommand);
		return removeCommand.getRemovedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnMove(String project, 
			                        Table tableEModel,
			                        TableTypes tableType,
			                        ICommandCreationListener<MoveCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		MoveCommand<ColumnPositionStateMemento> moveCommand = 
			new MoveCommand<ColumnPositionStateMemento>(tableEModel, null, commandStack, tableType, listener);
		commandManager.execute(commandStack, moveCommand);
		return moveCommand.getMovedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeRowMove(String project, 
			                        Table tableEModel,
			                        TableTypes tableType,
			                        ICommandCreationListener<MoveCommand<TableRuleStateMemento>, TableRuleStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		MoveCommand<TableRuleStateMemento> moveCommand = 
			new MoveCommand<TableRuleStateMemento>(tableEModel, null, commandStack, tableType, listener);
		commandManager.execute(commandStack, moveCommand);
		return moveCommand.getMovedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnRename(String project, 
			                          Table tableEModel,
			                          TableTypes tableType,
			                          ICommandCreationListener<RenameCommand<ColumnNameStateMemento>, ColumnNameStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		RenameCommand<ColumnNameStateMemento> renameCommand = 
			new RenameCommand<ColumnNameStateMemento>(tableEModel, null, commandStack, tableType, listener);
		commandManager.execute(commandStack, renameCommand);
		return renameCommand.getRenamedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnAliasChange(String project, 
			                               Table tableEModel,
			                               TableTypes tableType,
			                               ICommandCreationListener<RenameCommand<ColumnAliasStateMemento>, ColumnAliasStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		RenameCommand<ColumnAliasStateMemento> renameCommand = 
			new RenameCommand<ColumnAliasStateMemento>(tableEModel, null, commandStack, tableType, listener);
		commandManager.execute(commandStack, renameCommand);
		return renameCommand.getRenamedObject();
	}
	
	/**
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeColumnDefaultCellTextChange(String project, 
			                               Table tableEModel,
			                               TableTypes tableType,
			                               ICommandCreationListener<RenameCommand<ColumnDefaultCellTextStateMemento>, ColumnDefaultCellTextStateMemento> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		RenameCommand<ColumnDefaultCellTextStateMemento> renameCommand = 
			new RenameCommand<ColumnDefaultCellTextStateMemento>(tableEModel, null, commandStack, tableType, listener);
		commandManager.execute(commandStack, renameCommand);
		return renameCommand.getRenamedObject();
	}
	
	/**
	 * Add a new {@link TableRuleVariable} to an existing {@link TableRule}
	 * as a result of cell addition.
	 * <p>
	 * example -> Creating an uncovered condition while auto fixing
	 * </p>
	 * @param project
	 * @param tableEModel
	 * @param tableRule
	 * @param tableType
	 * @param listener
	 * @return
	 */
	public Object executeAddCell(String project, 
			                     Table tableEModel,
			                     TableRule tableRule,
			                     TableTypes tableType,
			                     ICommandCreationListener<AddCommand<TableRuleVariable>, TableRuleVariable> listener) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(
				project, getTablePath(tableEModel));
		AddCommand<TableRuleVariable> addCommand = 
			new AddCommand<TableRuleVariable>(commandStack, tableEModel, tableType, tableRule, listener);
		commandManager.execute(commandStack, addCommand);
		return addCommand.getCreatedObject();
	}
	
	/**
	 * Clear the command stack and remove it from the map of stacks
	 * 
	 * @param tableEModel
	 * @param project
	 */
	public void clearCommandStack(Table tableEModel, String project) {
		String tablePath = getTablePath(tableEModel);
		
		CommandStack<IExecutableCommand> stack = getCommandStack(project, tablePath);
		commandManager.clear(stack, tablePath);
	}
	
	/**
	 * Execute updates to metadata of entire table or any other {@link Metadatable} feature.
	 * @see {@link Table}
	 * @see {@link TableRule}
	 * @param <T>
	 * @param <M>
	 * @param project
	 * @param tableEModel
	 * @param metadatableFeature
	 * @param metadataFeature
	 * @param changedValue
	 * @param featureDataType
	 */
	public <T extends Enum<T> & IMetadataFeature, 
			M extends Metadatable> void executeTableMetadataUpdate(String project, 
			                               Table tableEModel,
			                               M metadatableFeature,
			                               T metadataFeature,
			                               String changedValue,
			                               String featureDataType) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		GenericMetadataCommand<T, M> metadataUpdateCommand = 
			new GenericMetadataCommand<T, M>(tableEModel, metadatableFeature, commandStack, metadataFeature, changedValue, featureDataType);
		commandManager.execute(commandStack, metadataUpdateCommand);
	}
	
	/**
	 * Execute updates to metadata of entire table or any other {@link Metadatable} feature with an undoable command.
	 * @see {@link Table}
	 * @see {@link TableRule}
	 * @param <T>
	 * @param <M>
	 * @param project the name of the project containing the table
	 * @param tableEModel the table
	 * @param metadatableFeature the object owning the metadata
	 * @param metadataFeature the metadata property to modify
	 * @param changedValue the new value
	 * @param featureDataType the data type of the property
	 */
	public <T extends Enum<T> & IMetadataFeature, 
	M extends Metadatable> void executeUndoableTableMetadataUpdate(String project, 
	                                                       Table tableEModel,
	                                                       M metadatableFeature,
	                                                       T metadataFeature,
	                                                       String changedValue,
	                                                       String oldValue,
	                                                       String featureDataType) {
	    CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
	    UndoableMetadataCommand<T, M> metadataUpdateCommand = 
	            new UndoableMetadataCommand<T, M>(tableEModel, metadatableFeature, commandStack, metadataFeature, changedValue, oldValue, featureDataType);
	    commandManager.execute(commandStack, metadataUpdateCommand);
	}
	
	/**
	 * 
	 * @param project
	 * @param tableEModel
	 * @param tableRuleVariable
	 * @param changedValue
	 * @param propertyFeature
	 */
	public void executeCellPropertyUpdates(String project, 
			                               Table tableEModel,
			                               TableRuleVariable tableRuleVariable,
			                               Object changedValue,
			                               PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature) {
		CommandStack<IExecutableCommand> commandStack = getCommandStack(project, getTablePath(tableEModel));
		PropertyUpdateCommand propertyUpdateCommand = 
			new PropertyUpdateCommand(tableEModel, tableRuleVariable, commandStack, propertyFeature, changedValue);
		commandManager.execute(commandStack, propertyUpdateCommand);
	}
}
