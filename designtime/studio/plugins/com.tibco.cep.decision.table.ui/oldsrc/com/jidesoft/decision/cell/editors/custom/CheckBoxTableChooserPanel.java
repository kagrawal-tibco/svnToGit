package com.jidesoft.decision.cell.editors.custom;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.Position;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.swing.JideSwingUtilities;

public class CheckBoxTableChooserPanel extends MultiSelectTableChooserPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckBoxTableChooserPanel() {
    }

    public CheckBoxTableChooserPanel(Action okAction, Action cancelAction) {
        super(okAction, cancelAction);
    }

    public CheckBoxTableChooserPanel(Object[] objects, Class<?> clazz) {
        super(objects, clazz);
    }

    public CheckBoxTableChooserPanel(Object[] objects, Class<?> clazz, Action okAction, Action cancelAction) {
        super(objects, clazz, okAction, cancelAction);
    }

    public CheckBoxTableChooserPanel(Vector<?> objects, Class<?> clazz) {
        super(objects, clazz);
    }

    public CheckBoxTableChooserPanel(Vector<?> objects, Class<?> clazz, Action okAction, Action cancelAction) {
        super(objects, clazz, okAction, cancelAction);
    }

    public CheckBoxTableChooserPanel(ComboBoxModel model, Class<?> clazz) {
        super(model, clazz);
    }

    public CheckBoxTableChooserPanel(ComboBoxModel model, Class<?> clazz, Action okAction, Action cancelAction) {
        super(model, clazz, okAction, cancelAction);
    }

    public CheckBoxTableChooserPanel(ComboBoxModel model, Class<?> clazz, ConverterContext elementConverterContext) {
        super(model, clazz, elementConverterContext);
    }

    public CheckBoxTableChooserPanel(ComboBoxModel model, Class<?> clazz, ConverterContext elementConverterContext, Action okAction, Action cancelAction) {
        super(model, clazz, elementConverterContext, okAction, cancelAction);
    }

    /**
     * Configures the scrollable portion which holds the list within the combo box popup. This method is called when the
     * UI class is created.
     *
     * @param scroller the scroll pane to be customized.
     */
    @Override
    protected void customizeScroller(JScrollPane scroller) {
        scroller.setFocusable(false);
        scroller.getVerticalScrollBar().setFocusable(false);
        scroller.setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Subclass can override this method to create a custom JList. The Searchable is installed in this method. If you
     * override, you need to install the Searchable on the list by yourself.
     *
     * @param comboBoxModel the combobox model which is used to create a CheckBoxList.
     * @return the list
     */
    @Override
    protected JList createList(ComboBoxModel comboBoxModel) {
        Vector<?> vector = convertComboBoxModelToVector(comboBoxModel);
        ConverterContext context = getConverterContext();
        return new CheckBoxTable(vector, context) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }

            @Override
            public void processMouseEvent(MouseEvent e) {
                if (JideSwingUtilities.isMenuShortcutKeyDown(e)) {
                    // Fix for 4234053. Filter out the Control Key from the list.
                    // ie., don't allow CTRL key deselection.
                    e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(),
                            e.getModifiers() ^ Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),
                            e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
                }
                super.processMouseEvent(e);
            }
        };
    }


    /**
     * Configures the list. The base class sets cell renderer and add mouse/key listener in this method. Subclass can
     * override this method to do additional setup.
     *
     * @param list the check box list
     */
    @Override
    protected void setupList(JList list) {
        super.setupList(list);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (list instanceof CheckBoxTable) ((CheckBoxTable) list).setClickInCheckBoxOnly(false);
    }

    @Override
    protected Object[] retrieveListSelection() {
        return ((CheckBoxTable) _list).getCheckBoxListSelectedValues();
    }

    @Override
    protected void updateListSelection(Object selectedObject) {
    	CheckBoxTable list = (CheckBoxTable) _list;
        list.getCheckBoxListSelectionModel().setValueIsAdjusting(true);
        list.clearCheckBoxListSelection();
        if (selectedObject != null) {
            if (selectedObject.getClass().isArray()) {
                int length = Array.getLength(selectedObject);
                for (int i = 0; i < length; i++) {
                    list.addCheckBoxListSelectedValue(Array.get(selectedObject, i), false);
                }
            }
            else {
                list.addCheckBoxListSelectedValue(selectedObject, true);
            }
        }
        list.getCheckBoxListSelectionModel().setValueIsAdjusting(false);
    }

    @Override
    protected boolean isAutoScroll() {
        return true;
    }
}