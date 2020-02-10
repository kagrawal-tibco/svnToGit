package com.tibco.cep.decision.table.editors;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 
 * @author sasahoo
 * 
 */
public class LegendCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 5882386798854702985L;

	public LegendCellRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		ImageIcon icon = (ImageIcon) value;
		setText(icon.getDescription());
		setIcon(icon);
		return this;
	}
}
