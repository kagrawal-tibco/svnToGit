package com.tibco.cep.studio.ui.forms.components;

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
@SuppressWarnings("serial")
public class ComboCellRenderer extends JLabel implements ListCellRenderer {

	public ComboCellRenderer() {
		setOpaque(true);
//		setHorizontalAlignment(LEFT);
//		setVerticalAlignment(CENTER);
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
