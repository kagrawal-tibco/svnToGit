package com.jidesoft.decision.cell.editors.custom;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.grid.AbstractComboBoxCellEditor;

public class CheckBoxTableComboBoxCellEditor extends AbstractComboBoxCellEditor {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckBoxTableComboBoxCellEditor() {
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that contains the elements in the specified array.  By
     * default the first item in the array (and therefore the data model) becomes selected.
     *
     * @param data an array of objects to insert into the combo box
     */
    public CheckBoxTableComboBoxCellEditor(Object[] data) {
        this(data, Object[].class);
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that contains the elements in the specified Vector.  By
     * default the first item in the Vector (and therefore the data model) becomes selected.
     *
     * @param data an array of objects to insert into the combo box
     */
    public CheckBoxTableComboBoxCellEditor(Vector<?> data) {
        this(data, Object[].class);
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that takes it's items from an existing
     * <code>ComboBoxModel</code>.  Since the <code>ComboBoxModel</code> is provided, a combo box created using this
     * constructor does not create a default combo box model and may impact how the insert, remove and add methods
     * behave.
     *
     * @param model the <code>ComboBoxModel</code> that provides the displayed list of items
     */
    public CheckBoxTableComboBoxCellEditor(ComboBoxModel model) {
        this(model, Object[].class);
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that contains the elements in the specified array.  By
     * default the first item in the array (and therefore the data model) becomes selected.
     *
     * @param data an array of objects to insert into the combo box
     * @param type the array type of element in the data array as the CheckBoxListComboBoxCellEditor is used to select
     *             multiple items.
     */
    public CheckBoxTableComboBoxCellEditor(Object[] data, Class<?> type) {
        _comboBox.setModel(new DefaultComboBoxModel(data));
        _comboBox.setType(type);
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that contains the elements in the specified Vector.  By
     * default the first item in the Vector (and therefore the data model) becomes selected.
     *
     * @param data an array of objects to insert into the combo box
     * @param type the array type of element in the data array as the CheckBoxListComboBoxCellEditor is used to select
     *             multiple items.
     */
    public CheckBoxTableComboBoxCellEditor(Vector<?> data, Class<?> type) {
        _comboBox.setModel(new DefaultComboBoxModel(data));
        _comboBox.setType(type);
    }

    /**
     * Creates a <code>CheckBoxListComboBoxCellEditor</code> that takes it's items from an existing
     * <code>ComboBoxModel</code>.  Since the <code>ComboBoxModel</code> is provided, a combo box created using this
     * constructor does not create a default combo box model and may impact how the insert, remove and add methods
     * behave.
     *
     * @param model the <code>ComboBoxModel</code> that provides the displayed list of items
     * @param type  the array type of element in the data array as the CheckBoxListComboBoxCellEditor is used to select
     *              multiple items.
     */
    public CheckBoxTableComboBoxCellEditor(ComboBoxModel model, Class<?> type) {
        _comboBox.setModel(model);
        _comboBox.setType(type);
    }

    @Override
    public AbstractComboBox createAbstractComboBox() {
        return createCheckBoxTableComboBox(null, null);
    }

    /**
     * Updates the model for the combobox.
     *
     * @param model the model for the combobox.
     */
    public void setComboBoxModel(ComboBoxModel model) {
        _comboBox.setModel(model);
    }

    /**
     * Updates the data type for the combobox.
     *
     * @param type the type for the combobox.
     */
    public void setComboBoxType(Class<?> type) {
        _comboBox.setType(type);
    }

    /**
     * Creates the checkbox list combobox.
     *
     * @param model the combobox model
     * @param type  the element type
     * @return the list combobox.
     */
    protected CheckBoxTableComboBox createCheckBoxTableComboBox(ComboBoxModel model, Class<?> type) {
        return new CheckBoxTableComboBox(model, type);
    }
}