package com.tibco.cep.decision.table.editors;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author sasahoo
 * 
 */
public class PropertyCellRenderer extends JComboBox implements
		TableCellRenderer {

	private static final long serialVersionUID = 2124426272063129962L;

	public PropertyCellRenderer(String[] items) {
		super(items);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}

		// Select the current value
		setSelectedItem(value);
		return this;
	}
}
