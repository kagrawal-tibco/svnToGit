/**
 * 
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.event.GroupColumnsEvent;

import ca.odell.glazedlists.EventList;

import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IRemovalCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.decision.table.editor.DTColumnHeaderLayerStack;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;


public class DecisionTableColumnRemovalCommandListener implements ICommandCreationListener<RemoveCommand<Object>, Object> {
	
	/**
	 * Reference to the editor we use
	 */
	private IDecisionTableEditor decisionTableSWTEditor;
	private TableRuleSet tableRuleSet;
	private NatTable targetTable;
	private int colIndex;
	
	/**
	 * 
	 * Contain the removed column and its associated TRVs
	 */
	private List<Object> affectedObjects;
	
	private ColumnModelController columnModelController;
	

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public Object commandCreated(CommandEvent<RemoveCommand<Object>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			IRemovalCommand command = (IRemovalCommand)source;
			TableTypes tableType = command.getTableType();
			Table tableEModel = command.getParent();
			this.tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
			
			EList<Column> columns = this.tableRuleSet.getColumns().getColumn();
			Column coulmnToBeRemoved = columns.get(colIndex);
			
			if (columns.size() == 1) {//Flush all rules if this is the last column.
				flushRules(tableEModel, tableType);
			}
			removeRelevantColumnEntries(tableEModel, tableType, coulmnToBeRemoved);
			columns.remove(coulmnToBeRemoved);
			
			if(columns.size() <= 0){
				((GridLayer)targetTable.getLayer()).getCornerLayer().dispose();
			}
			DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
			ColumnGroupHeaderLayer headerLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
			headerLayer.clearAllGroups();
			decisionTableSWTEditor.getDtDesignViewer().configureColumnGroups(this.tableRuleSet, columnHeaderLayer);
			headerLayer.fireLayerEvent(new GroupColumnsEvent(headerLayer));

			targetTable.refresh();
			decisionTableSWTEditor.modified();
			return affectedObjects;
		}
		return null;
	}
	
	/**
	 * Removes all the rules.
	 * @param tableEModel
	 * @param tableType
	 */
	private void flushRules(Table tableEModel, TableTypes tableType) {
		List<TableRule> allRules = new ArrayList<TableRule>();
		for (TableRule rule : this.tableRuleSet.getRule()) {
			allRules.add(rule);
		}
		EventList<TableRule> targetList = (tableType == TableTypes.DECISION_TABLE) ? decisionTableSWTEditor.getDtDesignViewer().getDecisionTableEventList()
				: decisionTableSWTEditor.getDtDesignViewer().getExceptionTableEventList();
		
		ICommandCreationListener<RemoveCommand<TableRule>, TableRule> listener =
				new DecisionTableRemoveRowCommandListener(targetList, this.tableRuleSet, allRules);
		CommandFacade.getInstance().executeRemoval(tableEModel.getOwnerProjectName(), tableEModel, tableType, listener);
	}
	
	/**
	 * 
	 * @param tableEModel
	 * @param tableType
	 */
	private void removeRelevantColumnEntries(Table tableEModel,
			                                 TableTypes tableType, 
			                                 Column column) {
		//Get column name
		String columnName = column.getName();
		Columns columns = this.tableRuleSet.getColumns();
		
		removeTableRuleVariables(columnName, column.getColumnType(), column);
		ColumnPositionStateMemento columnPositionStateMemento = new ColumnPositionStateMemento(column, colIndex);
		columnModelController.removeColumn(columns, column);
		affectedObjects.add(columnPositionStateMemento);
	}
		

	/**
	 * Remove all conditions/actions using this column id
	 * before the column id is removed
	 * @param tableRuleSet
	 * @param columnName
	 * @param columnType
	 */
	private void removeTableRuleVariables(String columnName, 
			                              ColumnType columnType,
			                              Column column) {
		if (this.tableRuleSet == null) {
			return;
		}
		if (column == null) {
			return;
		}
		// Get its id
		String colId = column.getId();
		// Get all rules in it
		List<TableRule> allRules = this.tableRuleSet.getRule();

		for (TableRule tableRule : allRules) {
			switch (columnType.massagedOrdinal()) {
			case 1: {
				List<TableRuleVariable> conditions = tableRule.getCondition();
				// Search for condition with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : conditions) {
					if (trv.getColId().equals(colId)) {
						toRemove = trv;
						break;
					}
				}
				conditions.remove(toRemove);
				if (toRemove != null) {
					affectedObjects.add(toRemove);
				}
				break;
			}
			case 2: {
				List<TableRuleVariable> actions = tableRule.getAction();
				// Search for action with this col id
				TableRuleVariable toRemove = null;
				for (TableRuleVariable trv : actions) {
					if (trv.getColId().equals(colId)) {
						toRemove = trv;
						break;
					}
				}
				actions.remove(toRemove);
				if (toRemove != null) {
					affectedObjects.add(toRemove);
				}
				break;
			}
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	public void commandUndone(CommandEvent<RemoveCommand<Object>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IRemovalCommand) {
			IRemovalCommand removalCommand = (IRemovalCommand)source;
			if (removalCommand.isDefunct()) {
				return;
			}
			Table tableEModel = removalCommand.getParent();
			TableTypes tableType = removalCommand.getTableType();
			addColumnEntries(tableEModel, tableType);
			if (this.tableRuleSet == null) {
				this.tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
			}
			
			DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
			ColumnGroupHeaderLayer headerLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
			headerLayer.clearAllGroups();
			decisionTableSWTEditor.getDtDesignViewer().configureColumnGroups(this.tableRuleSet, columnHeaderLayer);
			headerLayer.fireLayerEvent(new GroupColumnsEvent(headerLayer));

			targetTable.refresh();
			decisionTableSWTEditor.modified();
			
		}
	}
	
	/**
	 * Add a {@link TableRuleVariable} back into the column
	 * @param tableRuleSet
	 * @param column
	 * @param index
	 * @param tableRuleVariable
	 */
	private void addTableRuleVariable(Column column,
                                      int index,
                                      TableRuleVariable tableRuleVariable) {
		String trvId = tableRuleVariable.getId();
		//Get tableRule into which the TRV has to be added
		TableRule tableRule = null;
		String containingRuleId = 
			ModelUtils.DecisionTableUtils.getContainingRuleId(trvId);
		for (TableRule tr : this.tableRuleSet.getRule()) {
			if (tr.getId().equals(containingRuleId)) {
				tableRule = tr;
				break;
			}
		}
		switch (column.getColumnType()) {
		case CONDITION :
		case CUSTOM_CONDITION :
			int nConditions = tableRule.getCondition().size();
			index = (index <= nConditions) ? index : nConditions;
			tableRule.getCondition().add(index, tableRuleVariable);
			break;
		case ACTION :
		case CUSTOM_ACTION :
			int insertIndex = index - tableRule.getCondition().size();
			int nActions = tableRule.getAction().size();
			insertIndex = (insertIndex <= nActions) ? insertIndex : nActions;
			tableRule.getAction().add(insertIndex, tableRuleVariable);
			break;
		}
	}
	
	/**
	 * @param decisionDataModel
	 * @param tableEModel
	 * @param tableType
	 */
	private void addColumnEntries(Table tableEModel,
			                      TableTypes tableType) {
		//Get all affected objects
		Column column = null;
		//This is the same index where we will add the entries.
		//However this index begins with 1
		int colIndex = -1;
		for (int loop = affectedObjects.size() - 1; loop >= 0; loop--) {
			Object affectedObject = affectedObjects.get(loop);
			if (affectedObject instanceof ColumnPositionStateMemento) {
				ColumnPositionStateMemento columnPositionStateMemento = (ColumnPositionStateMemento)affectedObject;
				column = columnPositionStateMemento.getMonitored();
				//Get original position
				Integer position = (Integer)columnPositionStateMemento.getValue();
				colIndex = position;
				columnModelController.addColumn(this.tableRuleSet.getColumns(), position, column);
			}
			if (affectedObject instanceof TableRuleVariable) {
				if (column != null) {
					TableRuleVariable tableRuleVariable = (TableRuleVariable)affectedObject;
					if (colIndex != -1) {
						addTableRuleVariable(column, colIndex, tableRuleVariable);
					}
				}
			}
		}
	}

	/**
	 * @param decisionTablePane
	 * @param tableEModel
	 * @param fieldBoxToRemove
	 */
	public DecisionTableColumnRemovalCommandListener(IDecisionTableEditor decisionTableEditor,
			NatTable targetTable,
			int colIndex) {
		this.colIndex = colIndex;
		
		this.targetTable = targetTable;
		affectedObjects = new ArrayList<Object>();
		this.decisionTableSWTEditor = decisionTableEditor;
		columnModelController = new ColumnModelController(decisionTableEditor.getTable().getOwnerProjectName());
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	public List<Object> getAffectedObjects() {
		return affectedObjects;
	}
}
