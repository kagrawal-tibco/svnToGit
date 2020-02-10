package com.tibco.cep.studio.ui.forms.components;


import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
/**
 * 
 * @author vasharma
 *
 */

public class EditableComboBoxCellEditor extends ComboBoxCellEditor{
	
	private static final int defaultStyle = SWT.NONE;
	
	CCombo comboBox;
	
	int selection;
	
	private String[] items;
	
	public EditableComboBoxCellEditor(){
		super();
	}
	
	public EditableComboBoxCellEditor(Composite parent, String[] items) {
		super(parent, items, defaultStyle);
	}
	
	public EditableComboBoxCellEditor(Composite parent, String[] items, int style) {
		super(parent, items,style);
		
	}
	
	protected Object doGetValue() {
        Object value = super.doGetValue();
        if (((Integer)value).intValue() == -1) {
            CCombo comboBox = getCombo();
            value = comboBox.getText();
        }
        return value;
    }
	
	private CCombo getCombo() {
		return comboBox;
	}

	
	protected void doSetValue(final Object value) {
        int selection = -1;
        if (value instanceof Integer) {
            selection = ((Integer)value).intValue();
        } else {
            String[] items = getItems();
            for (int i = 0; i < items.length; i++) {
                String currentItem = items[i];
                if (currentItem.equals(value.toString())) {
                    selection = i;
                }
            }
        }
        CCombo comboBox = getCombo();
        comboBox.select(selection);
        if (selection >= 0) {
            String[] items = getItems();
            if(items.length > 0){
            	comboBox.setText(items[selection]);
            }
        } else {
            comboBox.setText(value.toString());
        }
    }
	
	protected Control createControl(Composite parent) {
		
		comboBox = ( CCombo ) super.createControl( parent ) ;
		comboBox.setEditable(true);
		return comboBox;
	}
	
}