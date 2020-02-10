package com.tibco.cep.decision.table.editors;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LabelRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	private static final Color LINK_COLOR = Color.blue;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(Color.WHITE);
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(LINK_COLOR);
			setBackground(table.getBackground());
		}

		setOpaque(true);
		setText((value == null) ? "" : "<html><u>" + value.toString()
				+ "</u></html>");
		// addMouseListener(new HandCursor());
		return this;
	}
	// static class HandCursor extends MouseAdapter {
	// public void mouseEntered(MouseEvent e) {
	// e.getComponent().setCursor(
	// Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	// }
	//
	// public void mouseExited(MouseEvent e) {
	// e.getComponent().setCursor(Cursor.getDefaultCursor());
	// }
	// }
}
