package com.tibco.cep.studio.tester.ui.tools.custom;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class DomainTableModel extends DefaultTableModel {
	
    /**
     * @param data
     * @param columnNames
     */
    public DomainTableModel(Object[][] data, Object[] columnNames) {
        super(data,columnNames);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int columnIndex) {
        return false;
    }
}