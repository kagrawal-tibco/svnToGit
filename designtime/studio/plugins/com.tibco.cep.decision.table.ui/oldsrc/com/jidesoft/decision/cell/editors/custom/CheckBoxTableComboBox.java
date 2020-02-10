package com.jidesoft.decision.cell.editors.custom;

import java.awt.Font;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.UIResource;

import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.utils.Lm;
import com.jidesoft.utils.Q;

public class CheckBoxTableComboBox extends MultiSelectTableComboBox {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static {
        try {
            if (Lm.class.getDeclaredMethods().length != 26) {
                System.err.println("The Lm class is invalid, exiting ...");
                System.exit(-1);
            }
        }
        catch (Exception e) {
            System.exit(-1);
        }
        if (Lm.DEMO) {
            Lm.z();
        }
        else if (!Q.zz(Lm.PRODUCT_GRIDS)) {
            Lm.showInvalidProductMessage(CheckBoxTableComboBox.class.getName(), Lm.PRODUCT_GRIDS);
        }
    }


    public CheckBoxTableComboBox() {
    }

    public CheckBoxTableComboBox(Object[] objects) {
        super(objects);
    }

    public CheckBoxTableComboBox(Vector<?> objects) {
        super(objects);
    }

    public CheckBoxTableComboBox(ComboBoxModel model) {
        super(model);
    }

    public CheckBoxTableComboBox(Object[] objects, Class<?> clazz) {
        super(objects, clazz);
    }

    public CheckBoxTableComboBox(Vector<?> objects, Class<?> clazz) {
        super(objects, clazz);
    }

    public CheckBoxTableComboBox(ComboBoxModel model, Class<?> clazz) {
        super(model, clazz);
    }

    /**
     * Creates the MultiSelectListChooserPanel. Subclass can override this method to create its own
     * MultiSelectListChooserPanel. Below is the default implement of this method.
     * <pre><code>
     * return new CheckBoxListChooserPanel(dataModel, clazz, converterContext,
     *          getDialogOKAction(), getDialogCancelAction()) {
     *     protected void setupList(final JList list) {
     *         super.setupList(list);
     *         CheckBoxListComboBox.this.setupList(list);
     *     }
     * };
     * </code></pre>
     *
     * @param dataModel the data model
     * @param clazz the class type
     * @param converterContext the converter context
     * @return ListChooserPanel.
     */
    @Override
    protected MultiSelectTableChooserPanel createTableChooserPanel(ComboBoxModel dataModel, Class<?> clazz, ConverterContext converterContext) {
    	return new CheckBoxTableChooserPanel(dataModel, clazz, converterContext,
    			getDialogOKAction(), getDialogCancelAction()) {
    		/**
    		 * 
    		 */
    		private static final long serialVersionUID = 1L;

			@Override
            protected void setupList(final JList list) {
                super.setupList(list);
                CheckBoxTableComboBox.this.setupList(list);
            }
        };
    }

    @Override
    protected boolean equals(Object object1, Object object2) {
        if (object1 != null && object2 != null && object1.getClass().isArray() && object2.getClass().isArray()) {
            return JideSwingUtilities.equals(object1, object2, true);
        }
        return super.equals(object1, object2);
    }

    /**
     * Setups the JList for the tree used in the popup panel. You can override this method to customize the JList.
     *
     * @param list the list used by ListChooserPanel.
     */
    @Override
    protected void setupList(final JList list) {
        Font font = this.getFont();
        if (font != null && !(font instanceof UIResource)) {
            list.setFont(font);
        }

        if (list instanceof CheckBoxTable) {
            ((CheckBoxTable) list).getCheckBoxListSelectionModel().setSelectionMode(getSelectionMode());
            ((CheckBoxTable) list).getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting() && isUpdateOnChange() && !Boolean.TRUE.equals(list.getClientProperty(PopupPanel.SELECTED_BY_MOUSE_ROLLOVER))) {
                        Object[] item = ((CheckBoxTable) list).getCheckBoxListSelectedValues();
                        Object convertedValue = convertArrayType(item);
                        if (item != null) {
                            if (getEditor().getItem() != convertedValue) {
                                getEditor().setItem(convertedValue);
                                if (getConverterContext() instanceof DefaultConverterContext) {
                                	DefaultConverterContext editor = (DefaultConverterContext)getConverterContext();
                                	Object[] listDisplayValues = ((CheckBoxTable) list).getDisplayValues();
                                	Object[] listexpressionValues = ((CheckBoxTable) list).getExpressionValues();
                                	//Object[] editorDisplayValues = editor.getDisplayValues();
                                	//Object[] editorExpressionValues = editor.getExpressionValues();
                                	editor.setDisplayValues(((CheckBoxTable) list).getDisplayValues());
                                	editor.setExpressionValues(((CheckBoxTable) list).getExpressionValues());
                                	if(!(listDisplayValues.length == 0 && listexpressionValues.length == 0))
                                		editor.dropdown = true;
                                }
                                getEditor().selectAll();
                            }
                        }
                    }
                }
            });
        }
    }
}