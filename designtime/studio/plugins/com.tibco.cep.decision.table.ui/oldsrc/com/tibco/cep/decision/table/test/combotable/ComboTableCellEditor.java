package com.tibco.cep.decision.table.test.combotable;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author sasahoo
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class ComboTableCellEditor extends DefaultCellEditor implements TableCellEditor {
	
	private TableComboField comboBox;
	
	/**
	 * @param combo
	 */
	public ComboTableCellEditor(TableComboField combo) {
		super(combo);
		this.comboBox = combo;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		if(comboBox.getPopupTable().getSelectionModel().isSelectionEmpty()){
			return comboBox.getEditor().getItem();
		}
		return super.getCellEditorValue();
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
													boolean isSelected,
													int row, 
													int column) {
		setCellEditorValue(value);
		return comboBox;
	}
	
	private JTextField textField;
    /**
     * @param value
     */
    public void setCellEditorValue(Object value) {
		comboBox.getEditor().setItem(value);
		textField = (JTextField) comboBox.getEditor().getEditorComponent();
		textField.getDocument().addDocumentListener(new DocumentListener(){
			/* (non-Javadoc)
			 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
			 */
			public void changedUpdate(DocumentEvent e) {
				clearSelection();
			}
			/* (non-Javadoc)
			 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
			 */
			public void insertUpdate(DocumentEvent e) {
				clearSelection();
			}
			/* (non-Javadoc)
			 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
			 */
			public void removeUpdate(DocumentEvent e) {
				clearSelection();
				
			}
		});
	}
    
    /**
     * @param value
     */
    private void clearSelection(){
    		if(!comboBox.getPopupTable().getSelectionModel().isSelectionEmpty()){
    			comboBox.getPopupTable().getSelectionModel().clearSelection();
    		}
    }
    
	/* (non-Javadoc)
	 * @see javax.swing.DefaultCellEditor#isCellEditable(java.util.EventObject)
	 */
	@Override
	public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            int clickCount;
            clickCount = 2;
            return ((MouseEvent)evt).getClickCount() == clickCount;
        }
        return true;
    }
}