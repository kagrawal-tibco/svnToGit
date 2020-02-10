/**
 * 
 */
package com.jidesoft.decision.cell.editors;

import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.getTableModel;
import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.isAsterixedExpression;
import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.massageCellEditorRawValue;

import javax.swing.JTable;

import com.jidesoft.decision.cell.editors.custom.DefaultMultipleEnumCellEditor;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTConstants;

/**
 * @author aathalye
 *
 */
public class DefaultEnumRuleVariableCellEditor extends DefaultMultipleEnumCellEditor {
	
	private static final String CLASS = DefaultEnumRuleVariableCellEditor.class.getName();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9040504129636061025L;
	
	/**
	 * A reference to the actual table editor we are working with.
	 */
	protected DecisionTableEditor decisionTableEditor;
	
	/**
	 * The table we are working with.
	 */
	protected JTable mainTable;
	
	/**
	 * Use this for any EMF model interaction.
	 */
	protected CellEditorController cellEditorController;
	
	
	/**
	 * The massaged cell value as String.
	 */
	protected String actualCellRawValue;
	
	/**
	 * 
	 * @param decisionTableEditor
	 */
	public DefaultEnumRuleVariableCellEditor(DecisionTableEditor decisionTableEditor) {
		this.decisionTableEditor = decisionTableEditor;
		cellEditorController = new CellEditorController(decisionTableEditor);
	}
	
	
	/**
	 * @return the cellEditorController
	 */
	public final CellEditorController getCellEditorController() {
		return cellEditorController;
	}


	/* (non-Javadoc)
	 * @see com.jidesoft.grid.MultipleEnumCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		Object rawValue = super.getCellEditorValue();
		IDecisionTableEditorInput IDecisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		//This will be a string only.
		String cellValueString = (String)rawValue;
		if (isAsterixedExpression(cellValueString)) {
			//Only asterix should be set as expression.
			cellValueString = DTConstants.ASTERISK;
		}
		int editedColumnNumber = mainTable.getEditingColumn();
		DecisionTableUIPlugin.debug(CLASS, "Column number edited {0}", editedColumnNumber);
		if (editedColumnNumber == -1) {
			return rawValue;
		}
		//Get column name in the model at this column number
		Column column = cellEditorController.getColumnAt(editedColumnNumber, getTableModel(mainTable).getDecisionDataModel()); 
		String actualCellRawValue = 
			massageCellEditorRawValue(cellValueString, IDecisionTableEditorInput.getProjectName(), column);
		DecisionTableUIPlugin.debug(CLASS, "Massaged cell value {0}", actualCellRawValue);
		return actualCellRawValue;
	}
}