package com.tibco.cep.studio.ui.forms.components;


import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
/**
 * 
 * @author aasingh
 *
 */

public class UneditableComboBoxCellEditor extends ComboBoxCellEditor{
	
	private static final int defaultStyle = SWT.NONE;
	
	CCombo comboBox;
	
	int selection;
	
	private String[] items;
	
	public UneditableComboBoxCellEditor(){
		super();
	}
	
	public UneditableComboBoxCellEditor(Composite parent, String[] items) {
		super(parent, items, defaultStyle);
	}
	
	public UneditableComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, items,style);
		
	}
	
	protected void doSetValue(Object value) {
		if ( comboBox != null && !(value instanceof Integer) ) {
			Integer val = ((Integer) value).intValue() ;
			if ( ! (val >= 0 && val < items.length ) )
			return ;
		}
		Assert.isTrue(comboBox != null && (value instanceof Integer));
		selection = ((Integer) value).intValue();
		comboBox.select(selection);
	}
	
	protected Control createControl(Composite parent) {
		
		comboBox = ( CCombo ) super.createControl( parent ) ;
		comboBox.setEditable(false);
		return comboBox;
	}
	
}