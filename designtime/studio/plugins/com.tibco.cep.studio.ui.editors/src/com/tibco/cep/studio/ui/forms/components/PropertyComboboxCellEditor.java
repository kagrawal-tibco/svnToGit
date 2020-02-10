package com.tibco.cep.studio.ui.forms.components;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;

/**
 * 
 * @author sasahoo
 * 
 */
@SuppressWarnings("serial")
public class PropertyComboboxCellEditor extends DefaultCellEditor {
	private int count = 0;

	public PropertyComboboxCellEditor(CustomPropertyComboBox combo) {
		super(combo);
	}

	// @Override
	// public boolean isCellEditable(EventObject evt) {
	// if (evt instanceof MouseEvent) {
	// int clickCount;
	// clickCount = 2;
	// return ((MouseEvent)evt).getClickCount() == clickCount;
	// }
	// return true;
	// }

	/** Implements the <code>TableCellEditor</code> interface. */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (count % 2 == 0) {
			// delegate.setValue(value);
			CustomPropertyComboBox combo = (CustomPropertyComboBox) editorComponent;
			String tableValue = combo.getTable().getValueAt(row, column)
					.toString();
			int selectedIndex = PROPERTY_TYPES.get(tableValue).getValue();
			combo.setSelectedIndex(selectedIndex);
		}
		count++;
		return editorComponent;
	}
}