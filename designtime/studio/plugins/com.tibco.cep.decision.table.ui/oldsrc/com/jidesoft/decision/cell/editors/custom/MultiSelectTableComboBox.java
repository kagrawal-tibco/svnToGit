package com.jidesoft.decision.cell.editors.custom;

import java.awt.Font;
import java.lang.reflect.Array;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.UIResource;

import com.jidesoft.combobox.AbstractComboBox;
import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.swing.Resizable;

public class MultiSelectTableComboBox extends AbstractComboBox {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean _updateOnChange = true;

    // start of porting from JComboBox

    /**
     * This protected field is implementation specific. Do not access directly or override. Use the accessor methods
     * instead.
     *
     * @see #getMaximumRowCount
     * @see #setMaximumRowCount
     */
    protected int maximumRowCount = 8;

    protected int _selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
    public static final String PROPERTY_SELECTION_MODE = "selectionMode";

    public MultiSelectTableComboBox() {
        this(new Object[0]);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param objects
     */
    public MultiSelectTableComboBox(Object[] objects) {
        this(objects, Object[].class);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param objects
     */
    public MultiSelectTableComboBox(Vector<?> objects) {
        this(objects, Object[].class);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param model
     */
    public MultiSelectTableComboBox(ComboBoxModel model) {
        this(model, Object[].class);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param objects
     * @param clazz
     */
    public MultiSelectTableComboBox(Object[] objects, Class<?> clazz) {
        super(DROPDOWN);
        setType(clazz);
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(objects);
        comboBoxModel.setSelectedItem(null);
        initComponent(comboBoxModel);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param objects
     * @param clazz
     */
    public MultiSelectTableComboBox(Vector<?> objects, Class<?> clazz) {
        super(DROPDOWN);
        setType(clazz);
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(objects);
        comboBoxModel.setSelectedItem(null);
        initComponent(comboBoxModel);
    }

    /**
     * Creates a new <code>MultiSelectListComboBox</code>.
     *
     * @param model
     * @param clazz
     */
    public MultiSelectTableComboBox(ComboBoxModel model, Class<?> clazz) {
        super(DROPDOWN);
        setType(clazz);
        initComponent(model);
    }

    @Override
    public EditorComponent createEditorComponent() {
        if (isEditable()) {
            return new DefaultTextFieldEditorComponent(getType());
        }
        else {
            return new DefaultRendererComponent(getType());
        }
    }

    @Override
    public PopupPanel createPopupComponent() {
        MultiSelectTableChooserPanel panel = createTableChooserPanel(dataModel, getType().getComponentType(), ConverterContext.getElementConverterContext(getConverterContext()));
        panel.setRenderer(getRenderer());
        panel.setResizable(true);
        panel.setResizableCorners(Resizable.LOWER_RIGHT);
        panel.setMaximumRowCount(getMaximumRowCount());
        return panel;
    }

    /**
     * Creates the MultiSelectListChooserPanel. Subclass can override this method to create its own
     * MultiSelectListChooserPanel. Below is the default implement of this method.
     * <pre><code>
     * return new MultiSelectListChooserPanel(dataModel, clazz, converterContext,
     *         getDefaultOKAction(), getDefaultCancelAction()) {
     *    protected void setupList(final JList list) {
     *        super.setupList(list);
     *        MultiSelectListComboBox.this.setupList(list);
     *    }
     * };
     * </code></pre>
     *
     * @param dataModel
     * @param clazz
     * @param converterContext
     * @return CheckBoxListChooserPanel.
     */
    protected MultiSelectTableChooserPanel createTableChooserPanel(ComboBoxModel dataModel, Class<?> clazz, ConverterContext converterContext) {
    	return new MultiSelectTableChooserPanel(dataModel, clazz, converterContext,
    			getDefaultOKAction(), getDefaultCancelAction()) {
    		/**
    		 * 
    		 */
    		private static final long serialVersionUID = 1L;

    		@Override
    		protected void setupList(final JList list) {
    			super.setupList(list);
    			MultiSelectTableComboBox.this.setupList(list);
    		}
    	};
    }

    /**
     * Sets the maximum number of rows the <code>JComboBox</code> displays. If the number of objects in the model is
     * greater than count, the combo box uses a scrollbar.
     *
     * @param count an integer specifying the maximum number of items to display in the list before using a scrollbar
     *              preferred: true description: The maximum number of rows the popup should have
     */
    public void setMaximumRowCount(int count) {
        int oldCount = maximumRowCount;
        maximumRowCount = count;
        firePropertyChange("maximumRowCount", oldCount, maximumRowCount);
    }

    /**
     * Returns the maximum number of items the combo box can display without a scrollbar.
     *
     * @return an integer specifying the maximum number of items that are displayed in the list before using a
     *         scrollbar
     */
    public int getMaximumRowCount() {
        return maximumRowCount;
    }


    /**
     * Selects the item at index <code>anIndex</code>.
     *
     * @param anIndex an integer specifying the list item to select, where 0 specifies the first item in the list and -1
     *                indicates no selection
     * @throws IllegalArgumentException if <code>anIndex</code> < -1 or <code>anIndex</code> is greater than or equal to
     *                                  size description: The item at index is selected.
     */
    public void setSelectedIndex(int anIndex) {
        int size = dataModel.getSize();

        if (anIndex == -1) {
            super.setSelectedItem(null);
        }
        else if (anIndex < -1 || anIndex >= size) {
            throw new IllegalArgumentException("setSelectedIndex: " + anIndex + " out of bounds");
        }
        else {
            Object array = Array.newInstance(getType().getComponentType(), 1);
            Array.set(array, 0, dataModel.getElementAt(anIndex));
            super.setSelectedItem(array);
        }
    }

    /**
     * Returns the first item in the list that matches the given item. The result is not always defined if the
     * <code>JComboBox</code> allows selected items that are not in the list. Returns -1 if there is no selected item or
     * if the user specified an item which is not in the list.
     *
     * @return an integer specifying the currently selected list item, where 0 specifies the first item in the list; or
     *         -1 if no item is selected or if the currently selected item is not in the list
     */
    public int getSelectedIndex() {
        Object[] objects = getSelectedObjects();

        if (objects == null || objects.length == 0) {
            return -1;
        }

        for (int i = 0, c = dataModel.getSize(); i < c; i++) {
            Object obj = dataModel.getElementAt(i);
            int length = objects.length;
            for (int j = 0; j < length; j++) {
                if (obj != null && obj.equals(objects[j]))
                    return i;
            }
        }
        return -1;
    }

    /**
     * Selects the items at the specified index array.
     *
     * @param indices an array of integer specifying the item to select.
     * @throws IllegalArgumentException if the integer in the array < -1 or is greater than or equal to size
     */
    public void setSelectedIndices(int[] indices) {
        int size = dataModel.getSize();

        if (indices.length == 0) {
            super.setSelectedItem(null);
        }
        else {
            Object array = Array.newInstance(getType().getComponentType(), indices.length);
            for (int i = 0; i < indices.length; i++) {
                int index = indices[i];
                if (index < -1 || index >= size) {
                    throw new IllegalArgumentException("setSelectedIndices: " + index + " out of bounds");
                }
                else {
                    Array.set(array, i, dataModel.getElementAt(index));
                }
            }
            setSelectedItem(array);
        }
    }

    /**
     * Returns an array of indices where each integer is the index of the item in the list
     *
     * @return an array of selected indices. Please note, there could be value with -1 in the array which means the item
     *         is not found in the list.
     */
    public int[] getSelectedIndices() {
        Object[] objects = getSelectedObjects();
        int i, c;
        Object obj;

        int[] selectedIndices = new int[objects.length];
        for (int j = 0; j < objects.length; j++) {
            Object sObject = objects[j];
            selectedIndices[j] = -1;
            for (i = 0, c = dataModel.getSize(); i < c; i++) {
                obj = dataModel.getElementAt(i);
                if (obj != null && obj.equals(sObject)) {
                    selectedIndices[j] = i;
                    break;
                }
            }
        }
        return selectedIndices;
    }

    /**
     * Sets the selected item. If the anObject is an array, it will be used directly. Of course, for non-editable
     * MultiSelectListComboBox case, nothing will be selected if any of the element in this array is not in the
     * ComboBoxModel. If the anObject is not an array, we will wrap it in an array and use it.
     *
     * @param anObject
     * @param fireEvent
     */
    @Override
    public void setSelectedItem(Object anObject, boolean fireEvent) {
        super.setSelectedItem(convertArrayType(anObject), fireEvent);
    }

    /**
     * Converts an array from any component type to the type that can accepted by this combobox. If the input is null,
     * null will be returned. If the input is not array, a new array will be created with the first element to be the
     * input value. If the input is an array but not the type it expected, a new array of the type will be created and
     * elements in the input array will be set to the new array, if the type is assignable. If the getType() is null,
     * the input will be returned.
     *
     * @param anObject an array
     * @return the converted array.
     */
    public Object convertArrayType(Object anObject) {
        if (anObject == null || getType() == null) {
            return anObject;
        }
        else if (anObject.getClass().isArray()) {
            Class<?> componentType = getType().getComponentType();
            if (componentType.isAssignableFrom(anObject.getClass().getComponentType())) {
                return anObject;
            }
            else {
                int length = Array.getLength(anObject);
                Object array = Array.newInstance(componentType, length);
                for (int i = 0; i < length; i++) {
                    Object value = Array.get(anObject, i);
                    try {
                        Array.set(array, i, value);
                    }
                    catch (Exception e) {
                        // ignore any exception, most likely CCE
                    }
                }
                return array;
            }
        }
        else {
            Object array = Array.newInstance(getType().getComponentType(), 1);
            Array.set(array, 0, anObject);
            return array;
        }
    }

    /**
     * Sets the selected objects. It actually calls to {@link #setSelectedItem(Object)} as that method already supports
     * an array. We added this method just to make it clear if user wants to select multiple objects. People tend to
     * think {@link #setSelectedItem(Object)} will only select one object.
     *
     * @param objects an array of objects you want to select.
     */
    public void setSelectedObjects(Object[] objects) {
        super.setSelectedItem(objects);
    }

    /**
     * Delegates {@link #getSelectedObjects()} instead.
     *
     * @return the selected objects.
     */
    @Override
    public Object getSelectedItem() {
        Object obj = super.getSelectedItem();
        return convertArrayType(obj);
    }

    /**
     * Gets the selected objects. In the case of <code>CheckBoxListChooserPanel</code>, the selected object is an array
     * of elements that are checked in the MultiSelectListComboBox. This method will always return an array of Objects.
     * You can also use {@link #getSelectedItem()} method which will return an array of the type you specified in the
     * class parameter of the constructors such as {@link #MultiSelectListComboBox(Object[],Class)}.
     *
     * @return the selected objects.
     */
    @Override
    public Object[] getSelectedObjects() {
        Object selectedValue = super.getSelectedItem();
        if (selectedValue == null) {
            return null;
        }
        Object[] array;
        try {
            int length = Array.getLength(selectedValue);
            array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = Array.get(selectedValue, i);
            }
        }
        catch (IllegalArgumentException e) {
            array = new Object[1];
            array[0] = selectedValue;
        }
        return array;
    }

    /**
     * Gets the underlying JList. It will return a not null value only when the list has ever been displayed. If you
     * want to customize the list, it's better to override {@link #createListChooserPanel(javax.swing.ComboBoxModel,Class,com.jidesoft.converter.ConverterContext)}
     * method and create your own CheckBoxListChooserPanel and JList.
     * <p/>
     * If you want to programmatically set selected index on the list, you can use this method. However do not keep the
     * returned value and use it later because combobox may create a new JList if the popup is volatile.
     * <p/>
     * Please note, this method will show the popup automatically. Otherwise, the JList is not even created.
     *
     * @return list
     */
    public JList getList() {
        if (!isPopupVisible() && isShowing()) {
            showPopup();
        }
        if (getPopupPanel() != null && getPopupPanel() instanceof MultiSelectTableChooserPanel) {
            return ((MultiSelectTableChooserPanel) getPopupPanel()).getList();
        }
        return null;
    }

    @Override
    protected JComponent getDelegateTarget() {
        if (getPopupPanel() != null && getPopupPanel() instanceof MultiSelectTableChooserPanel) {
            return ((MultiSelectTableChooserPanel) getPopupPanel()).getList();
        }
        return null;
    }

    @Override
    protected boolean validateValueForNonEditable(Object value) {
        if (value == null) return true;
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                boolean valid = super.validateValueForNonEditable(Array.get(value, i));
                if (!valid) {
                    return false;
                }
            }
            return true;
        }
        else {
            return super.validateValueForNonEditable(value);
        }
    }

    /**
     * Setups the JList for the tree used in the popup panel. You can override this method to customize the JList.
     *
     * @param list the list used by ListChooserPanel.
     */
    protected void setupList(final JList list) {
        Font font = this.getFont();
        if (font != null && !(font instanceof UIResource)) {
            list.setFont(font);
        }
        list.getSelectionModel().setSelectionMode(getSelectionMode());
        list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && isUpdateOnChange() && !Boolean.TRUE.equals(list.getClientProperty(PopupPanel.SELECTED_BY_MOUSE_ROLLOVER))) {
                    Object[] item = list.getSelectedValues();
                    Object convertedValue = convertArrayType(item);
                    if (convertedValue != null) {
                        if (getEditor().getItem() != convertedValue) {
                            getEditor().setItem(convertedValue);
                            getEditor().selectAll();
                        }
                    }
                }
            }
        });
    }

    /**
     * Sets the selection mode. The following list describes the accepted selection modes: <ul> <li>{@code
     * ListSelectionModel.SINGLE_SELECTION} - Only one list index can be selected at a time. In this mode, {@code
     * setSelectionInterval} and {@code addSelectionInterval} are equivalent, both replacing the current selection with
     * the index represented by the second argument (the "lead"). <li>{@code ListSelectionModel.SINGLE_INTERVAL_SELECTION}
     * - Only one contiguous interval can be selected at a time. In this mode, {@code addSelectionInterval} behaves like
     * {@code setSelectionInterval} (replacing the current selection), unless the given interval is immediately adjacent
     * to or overlaps the existing selection, and can therefore be used to grow it. <li>{@code
     * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION} - In this mode, there's no restriction on what can be selected.
     * </ul>
     *
     * @param selectionMode the new selection mode.
     * @throws IllegalArgumentException if the selection mode isn't one of those allowed
     * @see #getSelectionMode
     */
    public void setSelectionMode(int selectionMode) {
        int old = _selectionMode;
        if (old != selectionMode) {
            _selectionMode = selectionMode;
            firePropertyChange(PROPERTY_SELECTION_MODE, old, selectionMode);
        }
    }

    /**
     * Returns the current selection mode.
     *
     * @return the current selection mode
     *
     * @see #setSelectionMode
     */
    public int getSelectionMode() {
        return _selectionMode;
    }

    /**
     * Checks if the editor area will be updated when the selection is changed in the drop down list.
     *
     * @return true or false.
     */
    public boolean isUpdateOnChange() {
        return _updateOnChange;
    }

    /**
     * Sets the flag if the editor area will be updated when the selection is changed in the drop down list. If true,
     * the editor area will be updated with the selected items the moment user selects them in the drop down list. It is
     * true by default.
     *
     * @param updateOnChange true or false.
     */
    public void setUpdateOnChange(boolean updateOnChange) {
        _updateOnChange = updateOnChange;
    }
}