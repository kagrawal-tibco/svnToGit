package com.tibco.cep.decision.table.editors.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * 
 * @author sasahoo
 * 
 */
public class DeclarationTablePopupListener extends MouseAdapter {
	JPopupMenu popupMenu;

	public DeclarationTablePopupListener(JPopupMenu _popupMenu) {
		popupMenu = _popupMenu;
	}

	public void mousePressed(MouseEvent e) {
		showPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		showPopup(e);
	}

	private void showPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}
