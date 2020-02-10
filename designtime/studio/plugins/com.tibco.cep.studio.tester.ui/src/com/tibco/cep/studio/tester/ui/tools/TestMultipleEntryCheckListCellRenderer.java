package com.tibco.cep.studio.tester.ui.tools;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
/**
 * 
 * @author sasahoo
 *
 */
public class TestMultipleEntryCheckListCellRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private ListCellRenderer delegate;
	private ListSelectionModel selectionModel;
	private JCheckBox checkBox = new JCheckBox();

	public TestMultipleEntryCheckListCellRenderer(ListCellRenderer renderer,
			ListSelectionModel selectionModel) {
		this.delegate = renderer;
		this.selectionModel = selectionModel;
		setLayout(new BorderLayout());
		setOpaque(false);
		checkBox.setOpaque(false);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component renderer = delegate.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		checkBox.setSelected(selectionModel.isSelectedIndex(index));
		removeAll();
		add(checkBox, BorderLayout.WEST);
		add(renderer, BorderLayout.CENTER);
		return this;
	}
}
