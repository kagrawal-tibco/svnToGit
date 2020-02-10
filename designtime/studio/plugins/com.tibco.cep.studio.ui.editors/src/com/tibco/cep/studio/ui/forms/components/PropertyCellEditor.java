package com.tibco.cep.studio.ui.forms.components;

import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class PropertyCellEditor extends DefaultCellEditor {
   private int count = 0;
	public PropertyCellEditor(JTextField textField) {
		super(textField);
		setClickCountToStart(2);
	}

	public PropertyCellEditor(String[] items) {
		super(new JComboBox(items));
	}
	public PropertyCellEditor(JComboBox combo) {
		super(combo);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			int clickCount; 
			// For single-click activation 
			//				clickCount = 1;
			// For double-click activation 
			clickCount = 2; 
			// For triple-click activation 
			//				clickCount = 3;
			return ((MouseEvent)anEvent).getClickCount() >= clickCount; 
		} 
		return true;
	}

}