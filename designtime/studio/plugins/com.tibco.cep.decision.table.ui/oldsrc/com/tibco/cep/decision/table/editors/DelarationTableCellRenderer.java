package com.tibco.cep.decision.table.editors;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author sasahoo
 * 
 */
public class DelarationTableCellRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		return (JComponent) value;
	}
}
