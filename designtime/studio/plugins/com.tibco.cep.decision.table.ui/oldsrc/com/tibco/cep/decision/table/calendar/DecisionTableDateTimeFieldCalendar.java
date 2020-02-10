package com.tibco.cep.decision.table.calendar;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.eclipse.swt.widgets.Shell;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.decision.cell.editors.CellEditorController;
import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.decision.cell.editors.utils.CellEditorUtils;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.grid.TableScrollPane;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.impl.ModifyCommandExpression;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * Date Time handling in Decision Table cell.
 * @author sasahoo
 * @author aathalye
 *
 */
public class DecisionTableDateTimeFieldCalendar extends DateTimeCalendar {
	
	private static final String CLASS = DecisionTableDateTimeFieldCalendar.class.getName();

	private DecisionTablePane pane;
	
	private TableRuleVariable ruleVariable;
	
	private int selectedRow;
	
	private int selectedColumn;
	
	private String project;
	
	private Table tableEModel;
	
	private TableTypes tableType;
		
	/**
	 * @param parentShell
	 * @param pane
	 * @param table
	 * @param selectedRow
	 * @param selectedColumn
	 */
	public DecisionTableDateTimeFieldCalendar(Shell parentShell, 
											  DecisionTablePane pane,
											  JTable table, 
											  int selectedRow, 
											  int selectedColumn) {
		super(parentShell);
		this.selectedRow = selectedRow;
		this.selectedColumn = selectedColumn;
		this.pane = pane;
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)pane.getDecisionTableEditor().getEditorInput();
		project = editorInput.getProjectName();
		tableEModel = editorInput.getTableEModel();
		tableType = pane.getDecisionDataModel().getTableType();
		setCurrentDateTime(table, selectedRow, selectedColumn);// User has to set current date time 
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.calendar.DateTimeCalendar#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		newShell.setText("DateTime Field Calendar"); 
		super.configureShell(newShell);
	}

	/**
	 * @param table
	 * @param selectedRow
	 * @param selectedColumn
	 * @return
	 */
	private void setCurrentDateTime(JTable table, int selectedRow, int selectedColumn) {
		Object object = table.getValueAt(selectedRow, selectedColumn);
		if (object instanceof TableRuleVariable) {
			TableRuleVariable drc = (TableRuleVariable)object;
			ruleVariable = drc;
			DecisionTableUIPlugin.debug(CLASS, "Current cell value {0}", ruleVariable);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.calendar.DateTimeCalendar#setWidgetDateTime(java.lang.String)
	 */
	@Override
	protected void setWidgetDateTime(String dateTime) {
		if (pane != null) {
			TableScrollPane tableScrollPane = pane.getTableScrollPane();
			JTable mainTable = tableScrollPane.getMainTable();
			if (selectedRow != -1 && selectedColumn != -1) {
				TableCellEditor cellEditor = mainTable.getCellEditor(selectedRow, selectedColumn);
				if (cellEditor instanceof DefaultRuleVariableCellEditor) {
					DefaultRuleVariableCellEditor ruleVariableCellEditor = (DefaultRuleVariableCellEditor)cellEditor;
					if (tableScrollPane.isEditing()) {
						tableScrollPane.getCellEditor().cancelCellEditing();
					}
					mainTable.prepareEditor(cellEditor, selectedRow, selectedColumn);
					/**
					 * Internally this calls getActualRowAt, so let us call this on
					 * the UI index "seen" only.
					 */
					mainTable.setValueAt(getCellValueAt(mainTable, ruleVariableCellEditor), selectedRow, selectedColumn);
				}
			} 
		}
	}
	
	/**
	 * Get modified cell value.
	 * @see DefaultRuleVariableCellEditor#getCellEditorValue()
	 * @param mainTable
	 * @param ruleVariableCellEditor
	 * @return
	 */
	private Object getCellValueAt(JTable mainTable, DefaultRuleVariableCellEditor ruleVariableCellEditor) {
		CellEditorController cellEditorController = ruleVariableCellEditor.getCellEditorController();
		DecisionTableModel decisionTableModel = CellEditorUtils.getTableModel(mainTable);
		//Get decision field at this
		DecisionField decisionField = CellEditorUtils.getDecisionFieldAt(mainTable, selectedRow, selectedColumn + 1);
		//Use this only to get right TRV id
		int actualRowIndex = TableModelWrapperUtils.getActualRowAt(mainTable.getModel(), selectedRow, decisionTableModel);
		DecisionTableUIPlugin.debug(CLASS, "Selected Row Number {0} and actual row number {1}", selectedRow, actualRowIndex);
		if (ruleVariable == null) {
			//This will be old object.
			ruleVariable = cellEditorController.createCellEditorModelObject(decisionField);
			cellEditorController.setTableRuleVariableID(actualRowIndex, selectedColumn + 1, decisionTableModel, ruleVariable);
		}

		//Create a dummy tablerulevariable and use it as new value
		TableRuleVariable newTableRuleVariable = cellEditorController.createCellEditorModelObject(decisionField);
		//Set its id
		cellEditorController.setTableRuleVariableID(actualRowIndex, 
				                                    selectedColumn + 1,
				                                    decisionTableModel,
				                                    newTableRuleVariable);
		ConverterContext converterContext = ruleVariableCellEditor.getConverterContext();
		ruleVariableCellEditor.setCellEditorValue(ruleVariableCellEditor.getConverter().toString(
				ruleVariable, converterContext));
		CommandFacade.getInstance().executeModify(project,
				                                  tableEModel,
				                                  tableType,
				                                  newTableRuleVariable,
				                                  ruleVariable,
				                                  new ModifyCommandExpression(dateTime));
		return newTableRuleVariable;
	}
}
