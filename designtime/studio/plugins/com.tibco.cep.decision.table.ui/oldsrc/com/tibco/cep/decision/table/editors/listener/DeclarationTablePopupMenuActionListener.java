package com.tibco.cep.decision.table.editors.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tibco.cep.decision.table.ui.utils.Messages;

/**
 * 
 * @author sasahoo
 * 
 */
public class DeclarationTablePopupMenuActionListener implements ActionListener {

	JTable table;

	public DeclarationTablePopupMenuActionListener(JTable _table) {
		table = _table;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Messages.getString("DT_Delete_Action_Command"))) {
			int[] rows= table.getSelectedRows();
			
			for(int k = rows.length - 1; k >= 0; k--){
				((DefaultTableModel) table.getModel()).removeRow(rows[k]);
			}
		}
	}
}