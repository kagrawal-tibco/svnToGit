package com.jidesoft.decision.cell.editors.custom;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.Position;

import com.jidesoft.combobox.ButtonPopupPanel;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverter;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.swing.AutoScroll;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.ListSearchable;
import com.jidesoft.swing.SearchableUtils;

public class MultiSelectTableChooserPanel extends ButtonPopupPanel implements ItemListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Border _border = BorderFactory.createEmptyBorder(0, 0, 0, 0);

    protected JList _list;

    private ComboBoxModel _model;

    protected Class<?> _class;

    private ConverterContext _converterContext = null;

    private ObjectConverter _converter = null;

    private int _maximumRowCount;

    private ListCellRenderer _renderer = new ContextSensitiveListCellRenderer();

    private JScrollPane _scroller;

    private AutoScroll _autoScroll;

    public MultiSelectTableChooserPanel() {
        this(new Object[0], Object.class);
    }

    public MultiSelectTableChooserPanel(Action okAction, Action cancelAction) {
        this(new Object[0], Object.class, okAction, cancelAction);
    }

    /**
     * Creates a new <code>CheckBoxListChooserPanel</code>.
     *
     * @param objects an array of objects to insert into the combo box
     * @param clazz   the element type
     */
    public MultiSelectTableChooserPanel(Object[] objects, Class<?> clazz) {
        this(new DefaultComboBoxModel(objects), clazz);
    }

    /**
     * @param objects      an array of objects to insert into the combo box
     * @param clazz        the element type
     * @param okAction     the OK action
     * @param cancelAction the cancel action
     */
    public MultiSelectTableChooserPanel(Object[] objects, Class<?> clazz, Action okAction, Action cancelAction) {
        this(new DefaultComboBoxModel(objects), clazz, okAction, cancelAction);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer and a flow layout.
     *
     * @param objects a vector of the objects
     * @param clazz   the element type
     */
    public MultiSelectTableChooserPanel(Vector<?> objects, Class<?> clazz) {
        this(new DefaultComboBoxModel(objects), clazz);
    }

    public MultiSelectTableChooserPanel(Vector<?> objects, Class<?> clazz, Action okAction, Action cancelAction) {
        this(new DefaultComboBoxModel(objects), clazz, okAction, cancelAction);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer and a flow layout.
     *
     * @param model the <code>ComboBoxModel</code> that provides the displayed list of items
     * @param clazz the element type
     */
    public MultiSelectTableChooserPanel(ComboBoxModel model, Class<?> clazz) {
        this(model, clazz, null);
    }

    /**
     * @param model        the <code>ComboBoxModel</code> that provides the displayed list of items
     * @param clazz        the element type
     * @param okAction     the OK action
     * @param cancelAction the cancel action
     */
    public MultiSelectTableChooserPanel(ComboBoxModel model, Class<?> clazz, Action okAction, Action cancelAction) {
        this(model, clazz, null, okAction, cancelAction);
    }

    /**
     * Creates a new <code>JPanel</code> with a double buffer and a flow layout.
     *
     * @param model                   the combobox model
     * @param clazz                   the element type
     * @param elementConverterContext the converter context for the elements.
     */
    public MultiSelectTableChooserPanel(ComboBoxModel model, Class<?> clazz, ConverterContext elementConverterContext) {
        _model = model;
        _class = clazz;
        _converterContext = elementConverterContext;
        initComponents();
    }

    public MultiSelectTableChooserPanel(ComboBoxModel model, Class<?> clazz, ConverterContext elementConverterContext, Action okAction, Action cancelAction) {
        super(okAction, cancelAction);
        _model = model;
        _class = clazz;
        _converterContext = elementConverterContext;
        initComponents();
    }

    protected void initComponents() {
        setStretchToFit(true);
        
        setLayout(new BorderLayout());
        setBorder(_border);
        _list = createList(_model);
        _list.setName("ComboBox.list");
        _scroller = new JScrollPane(_list);
        _scroller.setName("ComboBox.scrollPane");
        setupList(_list);
        setDefaultFocusComponent(_list);
        customizeScroller(_scroller);
        add(_scroller, BorderLayout.CENTER);
        Component buttonPanel = createButtonPanel(SwingConstants.RIGHT);
        if (buttonPanel != null) add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
        addItemListener(this);

        if (isAutoScroll()) {
            _autoScroll = new AutoScroll(_list) {
                @Override
                public void autoScrollingStarted(int direction) {
                    if (direction == SCROLL_UP || direction == SCROLL_LEFT) {
                        Point convertedPoint = SwingUtilities.convertPoint(_scroller, new Point(1, 1), _list);
                        int top = _list.locationToIndex(convertedPoint);
                        _list.setSelectedIndex(top);
                    }
                    else {
                        Dimension size = _scroller.getSize();
                        Point convertedPoint = SwingUtilities.convertPoint(_scroller,
                                new Point(1, (size.height - 1) - 2),
                                _list);
                        int bottom = _list.locationToIndex(convertedPoint);
                        _list.setSelectedIndex(bottom);
                    }
                }

                @Override
                public void autoScrolling(int direction) {
                    if (direction == SCROLL_UP || direction == SCROLL_LEFT) {
                        int index = _list.getSelectedIndex();
                        if (index > 0) {
                            _list.setSelectedIndex(index - 1);
                            _list.ensureIndexIsVisible(index - 1);
                        }
                    }
                    else {
                        int index = _list.getSelectedIndex();
                        int lastItem = _list.getModel().getSize() - 1;
                        if (index < lastItem) {
                            _list.setSelectedIndex(index + 1);
                            _list.ensureIndexIsVisible(index + 1);
                        }
                    }
                }

                @Override
                public void updateSelectionForEvent(MouseEvent e, boolean shouldScroll) {
                    Point location = e.getPoint();
                    if (_list == null)
                        return;
                    int index = _list.locationToIndex(location);
                    if (index == -1) {
                        if (location.y < 0)
                            index = 0;
                        else
                            index = _model.getSize() - 1;
                    }
                    if (_list.getSelectedIndex() != index) {
                        _list.setSelectedIndex(index);
                        if (shouldScroll)
                            _list.ensureIndexIsVisible(index);
                    }
                }
            };
        }
    }

    /**
     * Configures the scrollable portion which holds the list within the combo box popup. This method is called when the
     * UI class is created.
     *
     * @param scroller the scroll pane to be customized.
     */
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
    protected JList createList(ComboBoxModel comboBoxModel) {
        Vector<?> vector = convertComboBoxModelToVector(comboBoxModel);
        JList list;
        list = new JList(vector) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
                return -1;
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return JideSwingUtilities.adjustPreferredScrollableViewportSize(this, super.getPreferredScrollableViewportSize());
            }
        };
        return list;
    }

    protected Vector<Object> convertComboBoxModelToVector(ComboBoxModel comboBoxModel) {
        Vector<Object> vector = new Vector<Object>();
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            Object object = comboBoxModel.getElementAt(i);
            if (_class == null && object != null) {
                vector.add(object);
            }
            else {
                vector.add(object);
            }
        }
        return vector;
    }

    /**
     * Configures the list. The base class sets cell renderer and add mouse/key listener in this method. Subclass can
     * override this method to do additional setup.
     *
     * @param list the check box list
     */
    protected void setupList(JList list) {
        ListSearchable searchable = SearchableUtils.installSearchable(list);
        searchable.setUseRendererAsConverter(true);
        if (getRenderer() != null) {
            list.setCellRenderer(getRenderer());
        }
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(list.getModel().getSize());
        installListListeners();
        registerKeyStrokes(list);
    }

    private void registerKeyStrokes(JList list) {
        Action enterAction = new AbstractAction() {
            private static final long serialVersionUID = 8319462990433718719L;

            public void actionPerformed(ActionEvent e) {
                setSelectedObject(getSelectedObject());
            }
        };
        list.registerKeyboardAction(enterAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object selectedObject = e.getItem();
            if (_list != null) {
                updateListSelection(selectedObject);
            }
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
            _list.setSelectedIndex(-1);
        }
    }

    protected Object[] retrieveListSelection() {
        return _list.getSelectedValues();
    }

    protected void updateListSelection(Object selectedObject) {
        try {
            _list.getSelectionModel().setValueIsAdjusting(true);
            if (selectedObject != null) {
                if (selectedObject.getClass().isArray()) {
                    int length = Array.getLength(selectedObject);
                    int[] selectedIndices = new int[length];
                    Arrays.fill(selectedIndices, -1);
                    for (int i = 0; i < _list.getModel().getSize(); i++) {
                        Object object = _list.getModel().getElementAt(i);
                        for (int j = 0; j < length; j++) {
                            if (selectedIndices[j] != -1) {
                                continue;
                            }
                            Object selectedValue = Array.get(selectedObject, j);
                            if (selectedValue != null && selectedValue.equals(object)) {
                                selectedIndices[j] = i;
                            }
                            else if (selectedValue == null && selectedValue == object) {
                                selectedIndices[j] = i;
                            }
                        }
                    }
                    _list.setSelectedIndices(selectedIndices);
                }
                else {
                    _list.setSelectedValue(selectedObject, true);
                }
            }
        }
        finally {
            _list.getSelectionModel().setValueIsAdjusting(false);
        }
    }

    /**
     * Gets the maximum number of rows the <code>JList</code> displays
     *
     * @return the maximum number of rows the <code>JList</code> displays.
     */
    public int getMaximumRowCount() {
        return _maximumRowCount;
    }

    /**
     * Sets the maximum number of rows the <code>JList</code> displays. If the number of objects in the model is greater
     * than count, the list uses a scrollbar.
     *
     * @param count an integer specifying the maximum number of items to display in the list before using a scrollbar
     */
    public void setMaximumRowCount(int count) {
        int oldCount = _maximumRowCount;
        _maximumRowCount = count;
        if (_maximumRowCount < _list.getModel().getSize() || _list.getModel().getSize() == 0) {
            _list.setVisibleRowCount(_maximumRowCount);
        }
        firePropertyChange("maximumRowCount", oldCount, _maximumRowCount);
    }

    /**
     * Returns the renderer used to display the selected item in the <code>JComboBox</code> field.
     *
     * @return the list cell renderer.
     */
    public ListCellRenderer getRenderer() {
        return _renderer;
    }

    /**
     * Sets the renderer that paints the list items and the item selected from the list in the JComboBox field. The
     * renderer is used if the JComboBox is not editable. If it is editable, the editor is used to render and edit the
     * selected item.
     * <p/>
     * The default renderer displays a string or an icon. Other renderers can handle graphic images and composite
     * items.
     * <p/>
     * To display the selected item, <code>aRenderer.getListCellRendererComponent</code> is called, passing the list
     * object and an index of -1.
     *
     * @param renderer the <code>ListCellRenderer</code> that displays the selected item
     */
    public void setRenderer(ListCellRenderer renderer) {
        _renderer = renderer;
        if (getRenderer() != null && _list != null) {
            _list.setCellRenderer(getRenderer());
        }
    }

    /**
     * Gets the converter context that used to convert the element in the list to/from string.
     *
     * @return the converter context.
     */
    public ConverterContext getConverterContext() {
        return _converterContext;
    }

    /**
     * Sets the converter context that used to convert the element in the list to/from string.
     *
     * @param converterContext the converter context.
     */
    public void setConverterContext(ConverterContext converterContext) {
        _converterContext = converterContext;
    }

    /**
     * Gets the converter that will convert the element in the ListModel to String that can be displayed on the JList.
     *
     * @return the converter.
     */
    public ObjectConverter getConverter() {
        return _converter;
    }

    /**
     * Sets a new converter that will convert the element in the ListModel to String that can be displayed on the
     * JList.
     *
     * @param converter the object converter
     */
    public void setConverter(ObjectConverter converter) {
        _converter = converter;
    }

    /**
     * Gets the JList.
     *
     * @return the JList.
     */
    public JList getList() {
        return _list;
    }

    class ContextSensitiveListCellRenderer extends DefaultListCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            return super.getListCellRendererComponent(list,
                    getConverter() != null ? getConverter().toString(value, getConverterContext()) :
                            ObjectConverterManager.toString(value, _class, getConverterContext()), index, isSelected, cellHasFocus);
        }
    }

    /**
     * Gets the selected object. In the case of <code>MultiSelectTableChooserPanel</code>, the selected object is an
     * array of elements that are checked in the CheckBoxList. The return type is always Object[] regardless of the
     * actual data type.
     *
     * @return the selected object.
     */
    @Override
    public Object getSelectedObject() {
        if (_list != null) {
            return retrieveListSelection();

// do not convert to the array type that is _class. It had problem when _class is primitive type and when there are mixed type in the selection.
//            Object array = Array.newInstance(_class, selectedValues.length);
//            for (int i = 0; i < selectedValues.length; i++) {
//                Object value = selectedValues[i];
//                if (value == null || _class.isAssignableFrom(value.getClass())) {
//                    Array.set(array, i, value);
//                }
//            }
//            return array;
        }
        else {
            return null;
        }
    }

    protected class Handler implements MouseListener, MouseMotionListener, PropertyChangeListener, Serializable {
        private static final long serialVersionUID = 8747239924092579259L;

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                Object selectedObject = _list.getSelectedValue();
                if (selectedObject != null) {
                    Object result[] = new Object[1];
                    result[0] = selectedObject;
                    selectedObject = result;
                }
                updateListSelection(selectedObject);
                setSelectedObject(selectedObject);
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getSource() == _list) {
                // JList mouse listener
//                setSelectedObject(_list.getSelectedValue());
//                // workaround for cancelling an edited item (bug 4530953)
//                if (comboBox.isEditable() && comboBox.getEditor() != null) {
//                    comboBox.configureEditor(comboBox.getEditor(),
//                            comboBox.getSelectedItem());
//                }
                return;
            }
            // JComboBox mouse listener
            if (isAutoScroll()) {
                Component source = (Component) e.getSource();
                Dimension size = source.getSize();
                Rectangle bounds = new Rectangle(0, 0, size.width - 1, size.height - 1);
                if (!bounds.contains(e.getPoint())) {
                    MouseEvent newEvent = convertMouseEvent(e);
                    Point location = newEvent.getPoint();
                    Rectangle r = new Rectangle();
                    _list.computeVisibleRect(r);
                    if (r.contains(location)) {
                        setSelectedObject(_list.getSelectedValue());
                    }
                }
                _autoScroll.mouseReleased(e);
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        //
        // MouseMotionListener:
        // NOTE: this is added to both the List and ComboBox
        //
        public void mouseMoved(MouseEvent e) {
            if (isAutoScroll()) {
                _autoScroll.mouseMoved(e);
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (isAutoScroll()) {
                _autoScroll.mouseDragged(e);
            }
        }

        //
        // PropertyChangeListener
        //
        public void propertyChange(PropertyChangeEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            String propertyName = e.getPropertyName();

            if ("model".equals(propertyName)) {
//                ComboBoxModel oldModel = (ComboBoxModel) e.getOldValue();
                ComboBoxModel newModel = (ComboBoxModel) e.getNewValue();
// TODO: commented for now
//                uninstallComboBoxModelListeners(oldModel);
//                installComboBoxModelListeners(newModel);

                _list.setModel(newModel);

                if (isVisible()) {
                    setVisible(false);
                }
            }
            else if ("renderer".equals(propertyName)) {
                _list.setCellRenderer(comboBox.getRenderer());
                if (isVisible()) {
                    setVisible(false);
                }
            }
            else if ("componentOrientation".equals(propertyName)) {
                // Pass along the new component orientation
                // to the _list and the scroller

                ComponentOrientation o = (ComponentOrientation) e.getNewValue();
                if (o != getComponentOrientation()) {
                    setComponentOrientation(o);
                }
            }
// TODO: commented for now
//            else if (propertyName == "lightWeightPopupEnabled") {
//                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
//            }
        }
    }

    protected MouseEvent convertMouseEvent(MouseEvent e) {
        if (e.getSource() == _list) {
            return e;
        }

        Point convertedPoint = SwingUtilities.convertPoint((Component) e.getSource(),
                e.getPoint(), _list);
        return new MouseEvent((Component) e.getSource(),
                e.getID(),
                e.getWhen(),
                e.getModifiers(),
                convertedPoint.x,
                convertedPoint.y,
                e.getClickCount(),
                e.isPopupTrigger());
    }

    /**
     * Creates a listener that will watch for mouse-press and release events on the combo box.
     * <p/>
     * <strong>Warning:</strong> When overriding this method, make sure to maintain the existing behavior.
     *
     * @return a <code>MouseListener</code> which will be added to the combo box or null
     */
    protected MouseListener createMouseListener() {
        return getHandler();
    }

    /**
     * Creates the mouse motion listener which will be added to the combo box.
     * <p/>
     * <strong>Warning:</strong> When overriding this method, make sure to maintain the existing behavior.
     *
     * @return a <code>MouseMotionListener</code> which will be added to the combo box or null
     */
    protected MouseMotionListener createMouseMotionListener() {
        return getHandler();
    }

    /**
     * Creates a mouse listener that watches for mouse events in the popup's list. If this method returns null then it
     * will not be added to the combo box.
     *
     * @return an instance of a <code>MouseListener</code> or null
     */
    protected MouseListener createListMouseListener() {
        return getHandler();
    }

    /**
     * Creates a mouse motion listener that watches for mouse motion events in the popup's list. If this method returns
     * null then it will not be added to the combo box.
     *
     * @return an instance of a <code>MouseMotionListener</code> or null
     */
    protected MouseMotionListener createListMouseMotionListener() {
        return getHandler();
    }

    /**
     * Creates a <code>PropertyChangeListener</code> which will be added to the combo box. If this method returns null
     * then it will not be added to the combo box.
     *
     * @return an instance of a <code>PropertyChangeListener</code> or null
     */
    protected PropertyChangeListener createPropertyChangeListener() {
        return getHandler();
    }

    private MultiSelectTableChooserPanel.Handler getHandler() {
        if (handler == null) {
            handler = new MultiSelectTableChooserPanel.Handler();
        }
        return handler;
    }

    /**
     * Implementation of all the listener classes.
     */
    private MultiSelectTableChooserPanel.Handler handler;

    /**
     * This protected field is implementation specific. Do not access directly or override. Use the accessor or create
     * methods instead.
     */
    protected MouseMotionListener mouseMotionListener;
    /**
     * This protected field is implementation specific. Do not access directly or override. Use the accessor or create
     * methods instead.
     */
    protected MouseListener mouseListener;

    // Listeners that are attached to the list
    /**
     * This protected field is implementation specific. Do not access directly or override. Use the create method
     * instead.
     *
     * @see #createListMouseListener
     */
    protected MouseListener listMouseListener;
    /**
     * This protected field is implementation specific. Do not access directly or override. Use the create method
     * instead
     *
     * @see #createListMouseMotionListener
     */
    protected MouseMotionListener listMouseMotionListener;

    /**
     * Adds the listeners to the list control.
     */
    protected void installListListeners() {
        if ((listMouseListener = createListMouseListener()) != null) {
            _list.addMouseListener(listMouseListener);
        }
        if ((listMouseMotionListener = createListMouseMotionListener()) != null) {
            _list.addMouseMotionListener(listMouseMotionListener);
        }
    }

    void uninstallListListeners() {
        if (listMouseListener != null) {
            _list.removeMouseListener(listMouseListener);
            listMouseListener = null;
        }
        if (listMouseMotionListener != null) {
            _list.removeMouseMotionListener(listMouseMotionListener);
            listMouseMotionListener = null;
        }
        handler = null;
    }

    protected boolean isAutoScroll() {
        return false;
    }
}