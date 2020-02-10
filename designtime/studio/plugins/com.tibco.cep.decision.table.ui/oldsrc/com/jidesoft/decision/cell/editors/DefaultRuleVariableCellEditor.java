/**
 * 
 */
package com.jidesoft.decision.cell.editors;
import static com.jidesoft.decision.cell.editors.utils.CellEditorUtils.getDecisionFieldAt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.EnumConverter;
import com.jidesoft.converter.ObjectConverter;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionTableModel;
import com.jidesoft.decision.RuleVariableConverter;
import com.jidesoft.decision.cell.editors.custom.CheckBoxTableComboBox;
import com.jidesoft.decision.cell.editors.custom.DefaultConverterContext;
import com.jidesoft.decision.cell.editors.custom.DefaultMultipleEnumConverter;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.tibco.cep.decision.table.editors.DecisionTableContentEditorInput;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;


/**
 * The defacto cell editor to be used for all decision/exception
 * table cells.
 * @author aathalye
 *
 */
public class DefaultRuleVariableCellEditor extends DefaultEnumRuleVariableCellEditor {
	
	private static final String CLASS = DefaultRuleVariableCellEditor.class.getName();
	/**
	 * 
	 */
	private static final long serialVersionUID = 6553859096939477518L;
	
	/**
	 * 
	 */
	private final ObjectConverter RULE_VARIABLE_CONVERTER;

	/**
	 * The model object for the cell set as its value.
	 * 
	 */
	protected TableRuleVariable _tableRuleVariable;
	
	/**
	 * 
	 * @param decisionTableEditor
	 */
	public DefaultRuleVariableCellEditor(DecisionTableEditor decisionTableEditor) {
		super(decisionTableEditor);
		IDecisionTableEditorInput decisionTableEditorInput= null;
		if (decisionTableEditor.isJar()) {
			decisionTableEditorInput = new DecisionTableContentEditorInput((JarEntryEditorInput)decisionTableEditor.getEditorInput());
		} else {
			decisionTableEditorInput = (IDecisionTableEditorInput)decisionTableEditor.getEditorInput();
		}
		RULE_VARIABLE_CONVERTER = new RuleVariableConverter(decisionTableEditor
				.getDecisionTableDesignViewer(), decisionTableEditorInput.getTableEModel());
		
	}
	
	//Dummy implementation
	public boolean isValueAllowed() {
		return true;
	}
	
	//TODO Handle Undo properly.
	/* (non-Javadoc)
	 * @see com.jidesoft.grid.MultipleEnumCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		String massagedRawValue = (String)super.getCellEditorValue(); 
		DecisionTableModel decisionTableModel = getActualTableModel();
		if (!cellEditorController.isCellExpressionChanged(_tableRuleVariable, massagedRawValue, decisionTableModel.getDecisionDataModel())) {
			DecisionTableUIPlugin.debug(CLASS, "No change in cell expression");
			//No change in expression.
			return _tableRuleVariable;
		}
		//Always work with edit row and edit column
		int editRow = mainTable.getEditingRow();
		int editColumn = mainTable.getEditingColumn();
		
		if (editRow == -1 || editColumn == -1) {
			//No edit case. Cant imagine why this could come
			return _tableRuleVariable;
		}
		//Create the cell model object using decision field
		//+ 1 needed when table API is used.
		DecisionField decisionField = getDecisionFieldAt(mainTable, editRow, editColumn + 1);
		
		//Required in case filtering or sorting is done
    	final int actualRowIndex = 
    		TableModelWrapperUtils.getActualRowAt(mainTable.getModel(), editRow, decisionTableModel);
    	DecisionTableUIPlugin.debug(CLASS, "Actual row index edited {0}", actualRowIndex);
    	
		if (_tableRuleVariable == null) {
			//1.)New cell addition case.
			_tableRuleVariable = cellEditorController.createCellEditorModelObject(decisionField);
			//Set all ids and such so that we can use it for undo.
			cellEditorController.setTableRuleVariableID(actualRowIndex, 
														editColumn + 1,
														decisionTableModel,
														_tableRuleVariable);
		}

		//Create a dummy tablerulevariable and use it as new value
		TableRuleVariable newTableRuleVariable = cellEditorController.createCellEditorModelObject(decisionField);
		newTableRuleVariable.setEnabled(_tableRuleVariable.isEnabled());
		//Set its id
		cellEditorController.setTableRuleVariableID(actualRowIndex, 
				                                    editColumn + 1,
				                                    decisionTableModel,
				                                    newTableRuleVariable);
				executeCellModification(massagedRawValue, newTableRuleVariable, 
				(ObjectConverter)getConverter(),(DefaultConverterContext) decisionField.getConverterContext());
		
		return _tableRuleVariable = newTableRuleVariable;
	}
	
	@Override
	public void setCellEditorValue(Object object) {
		if (object instanceof TableRuleVariable) {
			TableRuleVariable ruleVariable = (TableRuleVariable) object;
			ConverterContext converterContext = getConverterContext();
			super.setCellEditorValue(RULE_VARIABLE_CONVERTER.toString(ruleVariable, converterContext));
		} else {
			super.setCellEditorValue(object);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public TableRuleVariable getEditorValue() {
		return _tableRuleVariable;
	}
	
	/**
	 * Required for setting from outside.
	 * e.g : Date case.
	 * @return
	 */
	public void setEditorValue(TableRuleVariable tableRuleVariable) {
		this._tableRuleVariable = tableRuleVariable;
	}
	
	/**
	 * Get actual row index for selected row.
	 * @param selectedRowIndex
	 * @return
	 */
	private DecisionTableModel getActualTableModel() {
		//Get table model
		TableModel filterableTableModel = mainTable.getModel();
    	//Get actual model
    	DecisionTableModel decisionTableModel = 
    		(DecisionTableModel)TableModelWrapperUtils.getActualTableModel(filterableTableModel);
    	return decisionTableModel;
	}
	
	/**
	 * 
	 * @param massagedCellValue
	 * @param newTableRuleVariable -> The new object.
	 */
	private void executeCellModification(String massagedCellValue, 
			                             TableRuleVariable newTableRuleVariable, 
			                             ObjectConverter converter, 
			                             DefaultConverterContext context) {
		cellEditorController.
			executeCellModification(getActualTableModel().getDecisionDataModel(),
					_tableRuleVariable, newTableRuleVariable, massagedCellValue, converter, context);
	}
	
	/**
	 * 
	 * @param selectedRow
	 * @param selectedColmn
	 * @return
	 */
	private ConverterContext getConverterContextAt(int selectedRow, int selectedColmn) {
		//Get field at this selected row and column. 
		DecisionField decisionField = getDecisionFieldAt(mainTable, selectedRow, selectedColmn);
    	return decisionField.getConverterContext();
	}
	
	/* (non-Javadoc)
	 * @see com.jidesoft.grid.AbstractComboBoxCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, 
                                                 Object value,
                                                 boolean isSelected, 
                                                 int rowIndex, 
                                                 int columnIndex) {
		//The value is existing TableRuleVariable
		//Store this
		_tableRuleVariable = (TableRuleVariable)value;
		this.mainTable = table;
		ConverterContext converterContext = getConverterContextAt(rowIndex, columnIndex + 1);
    	//Show display values for the combo box if domains are associated.
		IPreferenceStore preferenceStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
    	String[] dropdownValues = 
    		cellEditorController.getDisplayValues(converterContext, 
    				                              preferenceStore.getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION));
    	//Set appropriate converter for it
    	setConverter(dropdownValues, converterContext);
    	Component cellEditorComponent = 
    		super.getTableCellEditorComponent(table, value, isSelected, rowIndex, columnIndex);
    	
    	if (cellEditorComponent instanceof AbstractComboBox) {
    		((AbstractComboBox) cellEditorComponent).setButtonVisible(dropdownValues.length > 0);
    		((AbstractComboBox) cellEditorComponent).addActionListener(new ActionListener(){
    			
				@Override
				public void actionPerformed(ActionEvent e) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							decisionTableEditor.setFocus();
						}
					});
				}
    		});
    	}
    	
    	/**
    	 * TODO Sarita Move this to designviewer
    	 */
    	if (RULE_VARIABLE_CONVERTER instanceof RuleVariableConverter) {
    		FilterableTableModel fModel = (FilterableTableModel)table.getModel(); 
    		DecisionTableModel dtModel = (DecisionTableModel)((SortableTableModel)fModel.getActualModel()).getActualModel();
    		if (TableTypes.DECISION_TABLE == dtModel.getDecisionDataModel().getTableType() &&
    				((RuleVariableConverter) RULE_VARIABLE_CONVERTER).getDecisionTableDesignViewer().isToggleTextAliasFlag()) {
    			cellEditorComponent.setEnabled(false);
    		} else if (TableTypes.EXCEPTION_TABLE == dtModel.getDecisionDataModel().getTableType() &&
    				((RuleVariableConverter) RULE_VARIABLE_CONVERTER).getDecisionTableDesignViewer().isExpToggleTextAliasFlag()) {
    			cellEditorComponent.setEnabled(false);
    		}  		
    		
    	}
    	return cellEditorComponent;
	}
	
	/**
	 * 
	 * @param dropdownValues
	 * @param converterContext
	 */
	private void setConverter(String[] dropdownValues, ConverterContext converterContext) {
		//For separating values.
		String separator = " ";
		EnumConverter converter = null;
		if (dropdownValues != null && dropdownValues.length > 0) {
			converter = new EnumConverter(
					converterContext.getName(), String.class,
					dropdownValues, dropdownValues, dropdownValues[0]);
		
			if (_comboBox instanceof CheckBoxTableComboBox) {
				CheckBoxTableComboBox checkBoxListComboBox = (CheckBoxTableComboBox)_comboBox;
				checkBoxListComboBox.setSelectionMode(cellEditorController.getDomainSelectionMode(converterContext));
			}

	    	if (converterContext instanceof DefaultConverterContext) {
	    		
	    		List<List<String>> list = cellEditorController.getDropDownValueDescription(converterContext);
	    		List<String> valuesL = list.get(0);
	    		String[] values = new String[valuesL.size()];
	    		valuesL.toArray(values);
	    		
	    		List<String> descL = list.get(1);
		    	String[] descriptions = new String[descL.size()];
		    	descL.toArray(descriptions);
	    		
	    		DefaultConverterContext defaultConverterContext = (DefaultConverterContext)converterContext;
	    		defaultConverterContext.setValues(values);
	    		defaultConverterContext.setDescriptions(descriptions);
	    		if (_tableRuleVariable != null) {
	    			//TODO: Fix using converter
	    			defaultConverterContext.setDisplayValues(convert(_tableRuleVariable.getExpr()));
	    			defaultConverterContext.setExpressionValues(convert(_tableRuleVariable.getExpr()));
	    		} else {
	    			defaultConverterContext.setDisplayValues(new String[0]);
	    			defaultConverterContext.setExpressionValues(new String[0]);
	    		}
	    	}
			
			separator = DTDomainUtil.SEPARATOR.trim();
		} else {
			//The no domain case.
			converter = new EnumConverter(
					converterContext.getName(), String.class,
					new String[0], new String[0], null);
		}
		converter.setStrict(DTDomainUtil.IS_STRICT);
		final DefaultMultipleEnumConverter multiConverter = new DefaultMultipleEnumConverter(separator, converter);
		setConverter(converter);
		/*if (dropdownValues.length >0)*/
			setEnumConverter(multiConverter, converterContext);
		
	}

	private Object[] convert(String value) {
		return value.split("\\|\\|");
	}
}