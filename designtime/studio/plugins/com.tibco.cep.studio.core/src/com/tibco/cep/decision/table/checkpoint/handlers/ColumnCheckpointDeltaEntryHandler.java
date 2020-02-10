/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

import java.util.List;

import com.tibco.cep.decision.table.checkpoint.ICheckpointDeltaEntry;
import com.tibco.cep.decision.table.command.ICreationCommand;
import com.tibco.cep.decision.table.command.IExecutableCommand;
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.memento.ColumnAliasStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnNameStateMemento;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.StudioCorePlugin;

/**
 * Handle index updates for column addition/removal.
 * TODO Handle correct ordering too.
 * @author aathalye
 *
 */
public class ColumnCheckpointDeltaEntryHandler implements ICheckpointDeltaEntryHandler {
	
	private Table tableEModel;
	
	/**
	 * The delta entry
	 */
	private ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry;
	
	private static final String CLASS = ColumnCheckpointDeltaEntryHandler.class.getName();
	

	/**
	 * @param tableEModel
	 * @param checkpointDeltaEntry
	 */
	public ColumnCheckpointDeltaEntryHandler(Table tableEModel,
			                                 ICheckpointDeltaEntry<IExecutableCommand> checkpointDeltaEntry) {
		this.tableEModel = tableEModel;
		this.checkpointDeltaEntry = checkpointDeltaEntry;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.checkpoint.handlers.ICheckpointDeltaEntryHandler#handleEntry()
	 */
	public void handleEntry() {
		IExecutableCommand command = checkpointDeltaEntry.getCommand();
		if (command == null || command.isDefunct()) {
			return;
		}
		
		//Add such a column to the columns
		TableTypes tableType = command.getTableType();
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		
		List<?> affectedObjects = command.getAffectedObjects();
		for (Object affectedObject : affectedObjects) {
			if (affectedObject instanceof ColumnPositionStateMemento) {
				ColumnPositionStateMemento columnPositionStateMemento = (ColumnPositionStateMemento)affectedObject;
				if (command instanceof ICreationCommand) {
					addColumn(tableRuleSet, columnsModel, columnPositionStateMemento);
				} else if (command instanceof IRemovalCommand) {
					removeColumn(tableRuleSet, columnsModel, columnPositionStateMemento);
				} else if (command instanceof IMoveCommand) {
					moveColumn(tableRuleSet, columnsModel, columnPositionStateMemento);
				} 
			} else if (affectedObject instanceof ColumnNameStateMemento) {
				ColumnNameStateMemento columnNameStateMemento = (ColumnNameStateMemento)affectedObject;
				renameColumn(tableRuleSet, columnsModel, columnNameStateMemento);
			} else if (affectedObject instanceof ColumnAliasStateMemento) {
				ColumnAliasStateMemento columnAliasStateMemento = (ColumnAliasStateMemento)affectedObject;
				changeColumnAlias(tableRuleSet, columnsModel, columnAliasStateMemento);
			}
		}
	}
	
	/**
	 * Add the new added column to the {@link TableRuleSet}
	 * @param tableRuleSet
	 * @param columns
	 * @param column
	 */
	private void addColumn(TableRuleSet tableRuleSet,
			               Columns columns,
			               ColumnPositionStateMemento affectedObject) {
		if (columns == null) {
			columns = DtmodelFactory.eINSTANCE.createColumns();
			tableRuleSet.setColumns(columns);
		}
		Column columnToCreate = (Column)affectedObject.getMonitored();
		int orderIndex = affectedObject.getCurrentOrderIndex();
		StudioCorePlugin.debug(CLASS, "Adding column {0} with id {1} to indexed table", columnToCreate.getName(), columnToCreate.getId());
		if (!isColumnPresent(columns, columnToCreate)) {
			columns.add(orderIndex, columnToCreate);
		}
		StudioCorePlugin.debug(CLASS, "Number of columns in indexed table are {0}", columns.getColumn().size());
	}
	
	/**
	 * 
	 * @param trs
	 * @param columns
	 * @param columnPositionStateMemento
	 */
	private void removeColumn(TableRuleSet trs, Columns columns, ColumnPositionStateMemento columnPositionStateMemento) {
		if (columns == null) {
			return;
		}
		Column columnToRemove = columnPositionStateMemento.getMonitored(); 
		columns.removeColumnById(columnToRemove.getId());
	}
	
	/**
	 * 
	 * @param trs
	 * @param columns
	 * @param columnNameStateMemento
	 */
	private void renameColumn(TableRuleSet trs, Columns columns, ColumnNameStateMemento columnNameStateMemento) {
		if (columns == null) {
			return;
		}
		Column cachedColumn = columnNameStateMemento.getMonitored(); 
		//Search column in the model with this column's id
		Column matchingColumn = columns.search(cachedColumn.getId());
		matchingColumn.setName(columnNameStateMemento.getNewColumnName());
	}
	
	/**
	 * 
	 * @param trs
	 * @param columns
	 * @param columnNameStateMemento
	 */
	private void changeColumnAlias(TableRuleSet trs, Columns columns, ColumnAliasStateMemento columnAliasStateMemento) {
		if (columns == null) {
			return;
		}
		Column cachedColumn = columnAliasStateMemento.getMonitored(); 
		//Search column in the model with this column's id
		Column matchingColumn = columns.search(cachedColumn.getId());
		matchingColumn.setAlias(columnAliasStateMemento.getNewColumnAlias());
	}
	
	/**
	 * 
	 * @param trs
	 * @param columns
	 * @param columnPositionStateMemento
	 */
	private void moveColumn(TableRuleSet trs, Columns columns, ColumnPositionStateMemento columnPositionStateMemento) {
		if (columns == null) {
			return;
		}
		//Get previous index and new index
		int previousIndex = (Integer)columnPositionStateMemento.getValue();
		int currentIndex = columnPositionStateMemento.getCurrentOrderIndex();
		columns.move(currentIndex, previousIndex);
	}
	
	/**
	 * 
	 * @param allColumns
	 * @param column
	 * @return
	 */
	private boolean isColumnPresent(Columns columns, Column column) {
		return columns.search(column.getId()) != null;
	}
}
