/**
 * 
 */
package com.tibco.cep.studio.decision.table.command.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.nebula.widgets.nattable.NatTable;

import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.IMoveCommand;
import com.tibco.cep.decision.table.command.impl.CommandEvent;
import com.tibco.cep.decision.table.command.impl.MoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.IDecisionTableEditor;

/**
 * Handle backend model updates for column moves.
 * @author vdhumal
 *
 */
public class DecisionTableColumnMoveCommandListener implements ICommandCreationListener<MoveCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> {
	
	/**
	 * Reference to the editor we use
	 */	
	private IDecisionTableEditor decisionTableSWTEditor;
	private TableRuleSet tableRuleSet;
	private NatTable targetTable;
	private int fromColIndex;
	private int toColIndex;

	private List<ColumnPositionStateMemento> affectedObjects;
	private ColumnModelController columnModelController;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandCreated(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public Object commandCreated(CommandEvent<MoveCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IMoveCommand) {
			IMoveCommand command = (IMoveCommand)source;
			TableTypes tableType = command.getTableType();
			Table tableEModel = command.getParent();
			this.tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();			
			EList<Column> columns = this.tableRuleSet.getColumns().getColumn();
			Column columnToBeMoved = columns.get(fromColIndex);
			columnModelController.move(this.tableRuleSet.getColumns(), fromColIndex, toColIndex);			
			ColumnPositionStateMemento columnPositionStateMemento = new ColumnPositionStateMemento(columnToBeMoved, fromColIndex);			
			affectedObjects.add(columnPositionStateMemento);
			targetTable.refresh();
			return columnToBeMoved;
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#commandUndone(com.tibco.cep.decision.table.command.impl.CommandEvent)
	 */
	@Override
	public void commandUndone(CommandEvent<MoveCommand<ColumnPositionStateMemento>> commandEvent) {
		Object source = commandEvent.getSource();
		if (source instanceof IMoveCommand) {
			IMoveCommand command = (IMoveCommand)source;
			TableTypes tableType = command.getTableType();
			Table tableEModel = command.getParent();
			this.tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();			

			if (affectedObjects.size() > 0) {
				columnModelController.move(this.tableRuleSet.getColumns(), toColIndex, fromColIndex);
				targetTable.refresh();
				affectedObjects.clear();
			}	
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.command.ICommandCreationListener#getAffectedObjects()
	 */
	@Override
	public List<ColumnPositionStateMemento> getAffectedObjects() {
		return affectedObjects;
	}
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 * @param fieldToMove
	 * @param decisionDataModel
	 */
	public DecisionTableColumnMoveCommandListener(String projectName,
													Table tableEModel,
													IDecisionTableEditor decisionTableEditor,
													NatTable targetTable,
													int fromColIndex,
													int toColIndex) {
		this.fromColIndex = fromColIndex;
		this.toColIndex = toColIndex;
		this.targetTable = targetTable;
		this.affectedObjects = new ArrayList<ColumnPositionStateMemento>();
		this.decisionTableSWTEditor = decisionTableEditor;
		this.columnModelController = new ColumnModelController(projectName);
	}
}
