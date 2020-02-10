package com.tibco.cep.decision.table.editors.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTable;

/**
 * 
 * @author sasahoo
 *
 */
public class RequestTablePopUpActionListener implements ActionListener {

	private JTable table;
	private JMenuItem menuItem;
	public RequestTablePopUpActionListener(JTable table,JMenuItem menuItem){
		this.table = table;
		this.menuItem = menuItem;
	}
	public void actionPerformed(ActionEvent e) {
		for (int row : table.getSelectedRows()) {
			table.setValueAt(menuItem.getText(), row, 4);
		}
	}

}
