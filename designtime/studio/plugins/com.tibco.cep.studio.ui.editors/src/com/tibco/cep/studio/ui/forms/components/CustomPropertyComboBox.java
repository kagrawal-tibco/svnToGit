package com.tibco.cep.studio.ui.forms.components;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class CustomPropertyComboBox extends JComboBox{

	private JTable table;
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	public CustomPropertyComboBox(Object[] items, JTable table){
		super(items);
		this.table =table;
		ComboCellRenderer comborenderer = new ComboCellRenderer();
		comborenderer.setPreferredSize(new Dimension(30, 20));
		this.setRenderer(comborenderer);
	}
	
}
