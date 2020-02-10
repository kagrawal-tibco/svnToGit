package com.tibco.cep.studio.tester.ui.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author sasahoo
 * 
 */
public class TestMultipleEntryCheckListManager extends MouseAdapter implements
		ListSelectionListener, ActionListener {
	private ListSelectionModel selectionModel = new DefaultListSelectionModel();
	private JList list = new JList();
	int hotspot = new JCheckBox().getPreferredSize().width;
	private JCheckBox checkBox;

	public TestMultipleEntryCheckListManager(JList list, JCheckBox _checkBox) {
		this.list = list;
		this.checkBox = _checkBox;
		list.setCellRenderer(new TestMultipleEntryCheckListCellRenderer(list
				.getCellRenderer(), selectionModel));
		list.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED);
		list.addMouseListener(this);
		selectionModel.addListSelectionListener(this);
	}

	public ListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	private void toggleSelection(int index) {
		if (index < 0)
			return;
		if (selectionModel.isSelectedIndex(index)) {
			selectionModel.removeSelectionInterval(index, index);
			list.removeSelectionInterval(index, index);
			checkBox.setSelected(false);
		} else{
			selectionModel.addSelectionInterval(index, index);
			list.addSelectionInterval(index, index);
		}
	}

	public void mouseClicked(MouseEvent me) {
		int index = list.locationToIndex(me.getPoint());
		if (index < 0)
			return;
		if (me.getX() > list.getCellBounds(index, index).x + hotspot)
			return;
		toggleSelection(index);
	}

	public void valueChanged(ListSelectionEvent e) {
		list.repaint(list.getCellBounds(e.getFirstIndex(), e.getLastIndex()));
	}

	public void actionPerformed(ActionEvent e) {
		toggleSelection(list.getSelectedIndex());
	}
}