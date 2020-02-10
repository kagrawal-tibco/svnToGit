package com.tibco.cep.decision.table.test.combotable;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DomainTableModel extends DefaultTableModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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