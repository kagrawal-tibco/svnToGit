package com.tibco.cep.studio.tester.ui.tools;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * 
 * @author sasahoo
 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607 
 */
public class TxComboBox extends JComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean layingOut;

	public TxComboBox() {
		layingOut = false;
	}

	public TxComboBox(Object aobj[]) {
		super(aobj);
		layingOut = false;
	}

	@SuppressWarnings("rawtypes")
	public TxComboBox(Vector vector) {
		super(vector);
		layingOut = false;
	}

	public TxComboBox(ComboBoxModel comboboxmodel) {
		super(comboboxmodel);
		layingOut = false;
	}

	public void doLayout() {
		layingOut = true;
		super.doLayout();
		layingOut = false;
	}

	public Dimension getSize() {
		Dimension dimension = super.getSize();
		if (!layingOut) {
			dimension.width = Math.max(dimension.width,
					getPreferredSize().width);
		}
		return dimension;
	}
}
