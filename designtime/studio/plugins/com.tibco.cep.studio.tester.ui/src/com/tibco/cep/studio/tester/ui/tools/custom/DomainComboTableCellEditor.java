package com.tibco.cep.studio.tester.ui.tools.custom;

import javax.swing.table.TableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
public class DomainComboTableCellEditor {

	private Object[][] rowData;
    private Object[] columnNames; 
    private int maxRowCount;
    private boolean editable;
    private int comboColumnIndex;

    /**
	 * @param _rowData
	 * @param _columnNames
	 * @param _maxRowCount
	 * @param _comboColumnIndex
	 * @param _editable
	 */
	public DomainComboTableCellEditor(Object[][] _rowData, 
			                          Object[] _columnNames, 
			                          int _maxRowCount,
			                          int _comboColumnIndex,
		                              boolean _editable){
		rowData = _rowData;
		columnNames =_columnNames;
		maxRowCount =_maxRowCount;
		editable= _editable;
		comboColumnIndex =_comboColumnIndex;
	}
	
	/**
	 * @return
	 */
	public ComboTableCellEditor getCellEditor(){
		try {
			TableComboField tableComboBox = new TableComboField();
			tableComboBox.setMaximumRowCount(maxRowCount);
			tableComboBox.setEditable(editable);
			TableModel tableModel = new DomainTableModel(rowData, columnNames);
			tableComboBox.setModel(tableModel,comboColumnIndex);
			return new ComboTableCellEditor(tableComboBox);
		} catch (ComboBoxTableException e) {
			e.printStackTrace();
		}
		return null;
	}

}
