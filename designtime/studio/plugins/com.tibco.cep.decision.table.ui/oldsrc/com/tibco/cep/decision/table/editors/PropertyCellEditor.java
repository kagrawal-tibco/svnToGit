package com.tibco.cep.decision.table.editors;

import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author sasahoo
 * 
 */
public class PropertyCellEditor extends DefaultCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 3305384490691160562L;
	public PropertyCellEditor(JTextField textField) {
		super(textField);
	}

	public PropertyCellEditor(String[] items) {
		super(new JComboBox(items));
	}
	public PropertyCellEditor(JComboBox combo) {
		super(combo);
	}
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