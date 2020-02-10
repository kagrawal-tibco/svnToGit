package com.tibco.cep.studio.dashboard.ui.editors.util;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

/**
 * <p>
 * The default <code>ComboBoxCellEditor</code> doesn't support user-entered
 * values when used within a table. The cell modifier expects
 * {@link #doGetValue()}and {@link #doSetValue(Object)}to return and take
 * <code>Integer</code> values respectively. This effectively prevents
 * user-entered values from being used. This subclass is capable of using the
 * standard list of values as well as displaying a user-entered value not
 * defined in the list.
 *
 * <p>
 * See Eclipse Bug#88427 (https://bugs.eclipse.org/bugs/show_bug.cgi?id=88427)
 * for details.
 *
 * @author dchesney
 */
public class UnrestrictedComboBoxCellEditor extends ComboBoxCellEditor {
    /**
     * The <code>CCombo</code> component.
     */
    private CCombo _combo;

    /**
     * @param table
     * @param values
     * @param read_only
     */
    public UnrestrictedComboBoxCellEditor(Table table, String[] values, int readOnly) {
        super(table, values, readOnly);
    }

    /**
     *
     * @return the <code>CCombo</code> used by this editor
     */
    public CCombo getCombo() {
        return _combo;
    }

    //-------------------
    // ComboBoxCellEditor
    //-------------------

    /**
     * Returns the text from the <code>CCombo</code>.
     */
    protected Object doGetValue() {
        Object value = super.doGetValue();
        if (((Integer)value).intValue() == -1) {
            // return the entered value
            CCombo comboBox = getCombo();
            value = comboBox.getText();
        }
        // return the selected index value
        return value;
    }

    /**
     * If <code>value</code> is an <code>Integer</code>, selects the item
     * at the given index. Sets text in combo to item.toString(). If
     * <code>value</code> is a <code>String</code>, tries to find the index
     * of the item using toString() comparison. Sets text in combo to
     * <code>value</code>.
     */
    protected void doSetValue(final Object value) {
        int selection = -1;
        if (value instanceof Integer) {
            // use the index we were given
            selection = ((Integer)value).intValue();
        } else {
            // find matching index
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
            comboBox.setText(items[selection]);
        } else {
            comboBox.setText(value.toString());
        }
    }

    /**
     * Override to remember the <code>CCombo</code> used by this editor. The
     * <code>CCombo</code> is accessible through {@link #getCombo()}.
     */
    protected Control createControl(Composite parent) {
        _combo = (CCombo)super.createControl(parent);
        return _combo;
    }
}