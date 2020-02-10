package com.tibco.cep.studio.tester.ui.tools;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;


/**
 * 
 * @author sasahoo
 * 
 */
@SuppressWarnings("serial")
public class TestColumnHeaderRenderer extends DefaultTableCellRenderer {
	
	private int textPosition = SwingConstants.LEFT;
	
	public TestColumnHeaderRenderer() {
		
	}
	
	public TestColumnHeaderRenderer (int textPosition) {
		this.textPosition = textPosition;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// Inherit the colors and font from the header component
		if (table != null) {
			JTableHeader header = table.getTableHeader();
			if (header != null) {
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}
		}
		if (value instanceof DataAndIcon) {
			setText(((DataAndIcon) value).text);
			setIcon(((DataAndIcon) value).icon);
			setToolTipText(value == null ? "" : ((DataAndIcon) value).text);
		} else {
			setText((value == null) ? "" : value.toString());
			setToolTipText(value == null ? "" : value.toString());
			setIcon(null);
		}

		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalTextPosition(textPosition);
		setHorizontalAlignment(JLabel.CENTER);
		return this;
	}
}
