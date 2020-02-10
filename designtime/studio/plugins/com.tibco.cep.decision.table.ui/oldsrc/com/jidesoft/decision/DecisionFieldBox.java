/*
 * @(#)FieldBox.java 4/28/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.decision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicButtonListener;

import org.eclipse.jface.preference.IPreferenceStore;

import com.jidesoft.combobox.CheckBoxListChooserPanel;
import com.jidesoft.combobox.PopupPanel;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.filter.CustomFilterEditor;
import com.jidesoft.filter.Filter;
import com.jidesoft.filter.FilterFactoryManager;
import com.jidesoft.grid.CustomFilterEditorDialog;
import com.jidesoft.grid.GridResource;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.CheckBoxListSelectionModel;
import com.jidesoft.swing.HeaderBox;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.ListSearchable;
import com.jidesoft.utils.PortingUtils;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * The component that represents a field in decision table.
 */
@SuppressWarnings({"rawtypes"})
public class DecisionFieldBox extends HeaderBox implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	public static final String PROPERTY_SORT_ARROW_VISIBLE = "sortArrowVisible";
    public static final String PROPERTY_FILTER_BUTTON_VISIBLE = "filterButtonVisible";
    public static final String PROPERTY_ASCENDING = "ascending";
    public static final String PROPERTY_SORT_ORDER = "sortOrder";
    public static final String PROPERTY_SORT_BY = "sortBy";
    public static final String PROPERTY_POSSIBLE_VALUES = "possibleValues";
    public static final String PROPERTY_SELECTED_POSSIBLE_VALUES = "selectedPossibleValues";
    public static final String PROPERTY_FILTER = "filter";

    private JLabel _label;
    private JLabel _sortArrow;
    private JLabel _filterIcon;
    private AbstractButton _filterButton;

    private Icon _downIcon = new ArrowIcon(SwingConstants.SOUTH);
    private Icon _upIcon = new ArrowIcon(SwingConstants.NORTH);

    private DecisionField _field;

    private DecisionTablePane _decisionTablePane;

    protected Object HIDE_POPUP_KEY = null;

    /**
     * Creates a button with no set text or icon.
     */
    public DecisionFieldBox() {
        initComponents(null, "", null, "", false, false);
    }

    public DecisionFieldBox(DecisionField field) {
        initComponents(field, field.getTitle(), field.getIcon(), field.getDescription(), true, field.isFilterable());
    }

    public DecisionFieldBox(DecisionField field, String title) {
        initComponents(field, title, field.getIcon(), field.getDescription(), true, field.isFilterable());
    }

    public DecisionFieldBox(DecisionField field, Icon icon) {
        initComponents(field, field.getTitle(), icon, field.getDescription(), true, field.isFilterable());
    }

    public DecisionFieldBox(DecisionField field, String title, Icon icon) {
        initComponents(field, title, icon, title, true, true);
    }

    public DecisionFieldBox(DecisionField field, boolean sortArrowVisible, boolean filterButtonVisible) {
        initComponents(field, field.getTitle(), field.getIcon(), field.getDescription(), sortArrowVisible, filterButtonVisible);
    }

    public DecisionFieldBox(DecisionField field, String title, Icon icon, boolean sortArrowVisible, boolean filterButtonVisible) {
        initComponents(field, title, icon, title, sortArrowVisible, filterButtonVisible);
    }

    public DecisionFieldBox(DecisionField field, String title, Icon icon, String description, boolean sortArrowVisible, boolean filterButtonVisible) {
        initComponents(field, title, icon, description, sortArrowVisible, filterButtonVisible);
    }

    protected void initComponents(DecisionField field, String text, Icon icon, String description, boolean sortArrowVisible, boolean filterButtonVisible) {
        _field = field;
        // set my own button model to disallow pressed state if the field is not sortable
        setModel(new DefaultButtonModel() {
            private static final long serialVersionUID = -8648094766925746268L;

            @Override
            public boolean isPressed() {
                return !(_field != null && !_field.isSortable()) && super.isPressed();
            }
        });
        if (filterButtonVisible) {
            filterButtonVisible = field.isFilterable();
        }
        _filterButtonVisible = filterButtonVisible;
        _sortArrowVisible = sortArrowVisible;
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 1));
        JideBoxLayout layout = new JideBoxLayout(this, JideBoxLayout.X_AXIS, 4);
        layout.setAlwaysLayout(true);
        setLayout(layout);

        _filterIcon = new JLabel();
        _filterIcon.setOpaque(false);
        _filterIcon.setVerticalAlignment(JLabel.CENTER);
        add(_filterIcon, JideBoxLayout.FIX);
        setFilterIconVisible(false);

        _label = new JLabel(icon, SwingConstants.LEADING);
        setTitle(text == null ? getField().getTitle() : text);
        _label.setOpaque(false);
        setToolTipText(description);
        add(_label);

        _sortArrow = new JLabel(_upIcon);
        _sortArrow.setOpaque(false);
        _sortArrow.setVerticalAlignment(JLabel.CENTER);
        int sortOrder = getField().getSortOrder();
        switch (sortOrder) {
            case DecisionField.SORT_ORDER_UNSORTED:
                _sortArrow.setIcon(null);
                break;
            case DecisionField.SORT_ORDER_ASCENDING:
                _sortArrow.setIcon(_upIcon);
                break;
            case DecisionField.SORT_ORDER_DESCENDING:
                _sortArrow.setIcon(_downIcon);
                break;

        }
        add(_sortArrow, JideBoxLayout.FIX);
        _sortArrow.setVisible(isSortArrowVisible() && getField().isSortable());
        addFilterButton();
        installListeners();
    }

    private void addFilterButton() {
        _filterButton = createDefaultButton();
        _filterButton.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = 8599992033335287609L;

            public void actionPerformed(ActionEvent e) {
                if (isPopupVisible()) {
                    hidePopup();
                }
                else {
                    _decisionTablePane.updatePossibleValues(DecisionFieldBox.this);
                    Object[] possibleValues = getPossibleValues();
                    PopupPanel popupPanel = createPopupPanel(_decisionTablePane, _field, possibleValues);
                    JideSwingUtilities.putClientPropertyRecursively(popupPanel, "doNotCancelPopup", HIDE_POPUP_KEY);
                    requestFocus();
                    _popup = createPopupWindow();
                    _popup.addPopupMenuListener(new PopupMenuListener() {
                        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                        }

                        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                            _preferredSize = _popup.getSize();
                        }

                        public void popupMenuCanceled(PopupMenuEvent e) {
                        }
                    });
                    _popup.setOwner(_filterButton);
                    if (_preferredSize != null) {
                        _popup.setPreferredSize(_preferredSize);
                    }
                    customizePopupWindow(_popup, popupPanel);

                    if (popupPanel.isStretchToFit()) {
                        if (popupPanel.getActualPreferredSize().width != getWidth()) {
                            Border border = _popup.getPopupBorder();
                            int offset = 0;
                            if (border != null) {
                                Insets borderInsets = border.getBorderInsets(popupPanel);
                                offset = borderInsets.left + borderInsets.right;
                            }
                            int width = Math.max(popupPanel.getActualPreferredSize().width, getWidth() - offset);
                            popupPanel.setPreferredSize(new Dimension(width, popupPanel.getPreferredSize().height));
                        }
                    }

                    if (!_popup.isPopupVisible()) {
                        Point p = calculatePopupLocation(popupPanel);
                        if (popupPanel.getDefaultFocusComponent() != null) {
                            _popup.setDefaultFocusComponent(popupPanel.getDefaultFocusComponent());
                        }
                        _popup.setResizable(popupPanel.isResizable());
                        _popup.showPopup(p.x, p.y, _filterButton);
                    }
                    else {
                        hidePopup();
                        _popup = null;
                    }
                }
            }
        });
        _filterButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                getModel().setRollover(true);
            }
        });
        add(_filterButton, JideBoxLayout.FIX);
        
        IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
        boolean showColumnFilter = prefStore.getBoolean(PreferenceConstants.SHOW_COLUMN_FILTER);
        _filterButton.setVisible(showColumnFilter);
    }

    protected void installListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && getField().isSortable()) {
                	//Before executing sort, disable cell editing if any
                	_decisionTablePane.getDecisionTableEditor().getDecisionTableDesignViewer().stopAllCellEditing();
                    boolean extend = (e.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
                    if (!extend) {
                        // unsort all other fields
                        java.util.List<DecisionField> conditions = _decisionTablePane.getDecisionDataModel().getConditionFields();
                        for (DecisionField condition : conditions) {
                            if (condition == _field) {
                                continue;
                            }
                            condition.setSortOrder(DecisionField.SORT_ORDER_UNSORTED);
                        }
                        java.util.List<DecisionField> actions = _decisionTablePane.getDecisionDataModel().getActionFields();
                        for (DecisionField action : actions) {
                            if (action == _field) {
                                continue;
                            }
                            action.setSortOrder(DecisionField.SORT_ORDER_UNSORTED);
                        }
                    }
                    toggleAscending();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                DecisionFieldBox fieldBox = (DecisionFieldBox) evt.getSource();
                if (DecisionFieldBox.PROPERTY_ASCENDING.equals(evt.getPropertyName())
                        || DecisionFieldBox.PROPERTY_SORT_BY.equals(evt.getPropertyName())
                        || DecisionFieldBox.PROPERTY_SORT_ORDER.equals(evt.getPropertyName())) {
                    if (_sortArrow != null) {
                        int sortOrder = fieldBox.getField().getSortOrder();
                        switch (sortOrder) {
                            case DecisionField.SORT_ORDER_UNSORTED:
                                _sortArrow.setIcon(null);
                                break;
                            case DecisionField.SORT_ORDER_ASCENDING:
                                _sortArrow.setIcon(_upIcon);
                                break;
                            case DecisionField.SORT_ORDER_DESCENDING:
                                _sortArrow.setIcon(_downIcon);
                                break;

                        }
                    }
                }
            }
        });
        _field.addPropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (DecisionField.PROPERTY_TITLE.equals(evt.getPropertyName())) {
            setTitle("" + evt.getNewValue());
            setToolTipText("" + evt.getNewValue());
        }
        else if (DecisionField.PROPERTY_DESCRIPTION.equals(evt.getPropertyName())) {
            setToolTipText("" + evt.getNewValue());
        }
        else if (DecisionField.PROPERTY_FILTER.equals(evt.getPropertyName())) {
            setTitle(getField().getTitle());
            updateFilterIcon();
        }
        else if (DecisionField.PROPERTY_SELECTED_POSSIBLE_VALUES.equals(evt.getPropertyName())) {
            setTitle(getField().getTitle());
            updateFilterIcon();
        }
        else if (DecisionField.PROPERTY_ICON.equals(evt.getPropertyName())) {
            if (evt.getNewValue() instanceof Icon) {
                setIcon((Icon) evt.getNewValue());
            }
            else if (evt.getNewValue() == null) {
                setIcon(null);
            }
        }
        else if (DecisionField.PROPERTY_FILTERABLE.equals(evt.getPropertyName())) {
            setFilterButtonVisible(Boolean.TRUE.equals(evt.getNewValue()));
        }
        else if (DecisionField.PROPERTY_SORT_ORDER.equals(evt.getPropertyName())) {
            firePropertyChange(DecisionFieldBox.PROPERTY_SORT_ORDER, evt.getOldValue(), evt.getNewValue());
        }
        else if (DecisionField.PROPERTY_ASCENDING.equals(evt.getPropertyName())) {
            firePropertyChange(DecisionFieldBox.PROPERTY_ASCENDING, evt.getOldValue(), evt.getNewValue());
        }
        else if (DecisionField.PROPERTY_EXPANDABLE.equals(evt.getPropertyName())) {
//            JTable rowHeaderTable = _decisionTablePane.getRowHeaderTable();
//            if (rowHeaderTable != null) rowHeaderTable.repaint();
//            JTable columnHeaderTable = _decisionTablePane.getColumnHeaderTable();
//            if (columnHeaderTable != null) columnHeaderTable.repaint();
        }
        else if (DecisionField.PROPERTY_SORTABLE.equals(evt.getPropertyName())) {
            _sortArrow.setVisible(isSortArrowVisible() && getField().isSortable());
        }
    }

    public void updateFilterIcon() {
        if (_decisionTablePane != null && _decisionTablePane.isShowFilterIcon()) {
            setFilterIconVisible(_field.getFilter() != null || _field.getSelectedPossibleValues() != null);
        }
        else {
            setFilterIconVisible(false);
        }
    }
    
    public void updateFilterButton() {
    	IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
    	Boolean showColumnFilter = prefStore.getBoolean(PreferenceConstants.SHOW_COLUMN_FILTER);
    	_filterButton.setVisible(showColumnFilter);
    }

    protected void uninstallListeners() {
        _field.removePropertyChangeListener(this);
    }

    private boolean _sortArrowVisible = true;

    private boolean _filterButtonVisible = true;

    public boolean isSortArrowVisible() {
        return _sortArrowVisible;
    }

    public void setSortArrowVisible(boolean sortArrowVisible) {
        boolean old = _sortArrowVisible;
        if (old != sortArrowVisible) {
            _sortArrowVisible = sortArrowVisible;
            firePropertyChange(DecisionFieldBox.PROPERTY_SORT_ARROW_VISIBLE, old, _sortArrowVisible);
            _sortArrow.setVisible(isSortArrowVisible() && getField().isSortable());
        }
    }

    public boolean isFilterButtonVisible() {
        return _filterButtonVisible;
    }

    public void setFilterButtonVisible(boolean filterButtonVisible) {
        boolean old = _filterButtonVisible;
        if (old != filterButtonVisible) {
            _filterButtonVisible = filterButtonVisible;
            firePropertyChange(DecisionFieldBox.PROPERTY_FILTER_BUTTON_VISIBLE, old, filterButtonVisible);
            _filterButton.setVisible(_filterButtonVisible);
            updateFilterIcon();
        }
    }

    public void setTitle(String title) {
//        if(getFilter() != null || getSelectedPossibleValues() != null) {
//            _label.setText("<html><b>" + title + "</b></html>");
//        }
//        else
        _label.setText(title);
    }

    @Override
    public void setIcon(Icon icon) {
        _label.setIcon(icon);
    }

    private void setFilterIconVisible(boolean visible) {
        _filterIcon.setVisible(visible);
        if (visible) {
            _filterIcon.setIcon(_decisionTablePane.getFilterIcon());
        }
    }

    public boolean isAscending() {
        return getField().isAscending();
    }

    public void toggleAscending() {
//        Values[] selectedValues = _decisionTablePane.saveSelection();
        int order = getField().getSortOrder();
        switch (order) {
            case DecisionField.SORT_ORDER_UNSORTED:
                getField().setSortOrder(DecisionField.SORT_ORDER_ASCENDING);
                break;
            case DecisionField.SORT_ORDER_ASCENDING:
                getField().setSortOrder(DecisionField.SORT_ORDER_DESCENDING);
                break;
            case DecisionField.SORT_ORDER_DESCENDING:
                getField().setSortOrder(DecisionField.SORT_ORDER_UNSORTED);
                break;
        }
//        _decisionTablePane.loadSelection(selectedValues);
    }

    public void setAscending(boolean ascending) {
        boolean old = getField().isAscending();
        if (old != ascending) {
            getField().setAscending(ascending);
            firePropertyChange(DecisionFieldBox.PROPERTY_ASCENDING, old, ascending);
        }
    }

    private Object[] _possibleValues;

    public Object[] getPossibleValues() {
        return _possibleValues;
    }

    public void setPossibleValues(Object[] possibleValues) {
        Object[] old = _possibleValues;
        _possibleValues = possibleValues;
        firePropertyChange(PROPERTY_POSSIBLE_VALUES, old, possibleValues);
    }

    public void setSelectedPossibleValues(Object[] selectedPossibleValues) {
//        Values[] selectedValues = _decisionTablePane.saveSelection();
        getField().setSelectedPossibleValues(selectedPossibleValues);
//        _decisionTablePane.loadSelection(selectedValues);
    }

	public void setFilter(Filter filter) {
//        Values[] selectedValues = _decisionTablePane.saveSelection();
        getField().setFilter(filter);
//        _decisionTablePane.loadSelection(selectedValues);
    }

    public Object[] getSelectedPossibleValues() {
        return getField().getSelectedPossibleValues();
    }

    public Filter getFilter() {
        return getField().getFilter();
    }

    public class ArrowIcon implements Icon, Serializable {
        private int _direction = SwingConstants.NORTH;
        private static final long serialVersionUID = 6213406821880564676L;

        public ArrowIcon(int direction) {
            _direction = direction;
        }

        public int getIconWidth() {
            return 9;
        }

        public int getIconHeight() {
//            return 5;  // triangle
            return 8; // arrow
        }

        public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(new Color(172, 168, 153));
            switch (_direction) {
                case SwingConstants.NORTH:
// a triangle
//                    for (int i = 0; i < 5; i ++) {
//                        g.drawLine(mx + i, my + i, mx + 8 - i, my + i);
//                    }

// arrow
                    for (int i = 0; i < 7; i++) {
                        g.drawLine(x + i, y + Math.abs(3 - i), x + i, y + Math.abs(3 - i) + 1);
                    }
                    g.drawLine(x + 3, y, x + 3, y + getIconHeight());
                    break;
                case SwingConstants.SOUTH:
// a triangle
//                    for (int i = 0; i < 5; i ++) {
//                        g.drawLine(mx + 4 - i, my + i, mx + 4 + i, my + i);
//                    }
// arrow
                    for (int i = 0; i < 7; i++) {
                        g.drawLine(x + i, y + 8 - Math.abs(3 - i), x + i, y + 8 - Math.abs(3 - i) + 1);
                    }
                    g.drawLine(x + 3, y, x + 3, y + getIconHeight());
                    break;
            }
        }
    }

    /**
     * Creates the default combobox button. This method is used only if createButtonComponent() returns null. The idea
     * is each combobox can implement createButtonComponent() to provide its own button. However the default
     * implementation should still be the button created by this method.
     *
     * @return the default combobox button.
     */
    protected AbstractButton createDefaultButton() {
        AbstractButton defaultButton = null;
        boolean useDefaultButton = /*UIManager.getLookAndFeel() instanceof WindowsLookAndFeel && */!UIDefaultsLookup.getBoolean("AbstractComboBox.useJButton");
        boolean aquaLnf = LookAndFeelFactory.isLnfInUse(LookAndFeelFactory.AQUA_LNF);
        if (!aquaLnf) {
            try {
                JComboBox ComboBox = new JComboBox();
                ComboBox.setEnabled(isEnabled());
                ComboBox.setEditable(true);
                ComboBox.doLayout();
                Component[] components = ComboBox.getComponents();
                for (Component component : components) {
                    if (component instanceof AbstractButton) {
                        // set a new name so that Synthetica 2.5 won't bother casting the component to JComboBox
                        // this is not necessary for 2.5+ release.
                        component.setName("AbstractComboBox.arrowButton");
                        defaultButton = (AbstractButton) component;
                        defaultButton.setBorder(BorderFactory.createEmptyBorder());

                        // try to figure out the correct size of the button.
                        Dimension size = defaultButton.getPreferredSize();
                        int height = ComboBox.getPreferredSize().height;
                        Insets insets = ComboBox.getInsets();
                        int buttonSize = height - (insets != null ? (insets.top + insets.bottom) : 0);
                        if (size.height < 16) {
                            size.height = buttonSize;
                        }
                        if (size.width < 16) {
                            size.width = buttonSize;
                        }
                        defaultButton.setPreferredSize(size);

                        MouseListener[] mouseListeners = defaultButton.getMouseListeners();
                        for (MouseListener l : mouseListeners) {
                            if (l instanceof BasicButtonListener) {
                                continue;
                            }
                            defaultButton.removeMouseListener(l);
                        }

                        defaultButton.setRequestFocusEnabled(false);
                        defaultButton.setFocusable(false);
                        HIDE_POPUP_KEY = defaultButton.getClientProperty("doNotCancelPopup");
                        defaultButton.putClientProperty("doNotCancelPopup", null);
                        break;
                    }
                }
            }
            catch (UnsupportedOperationException e) {
                // ignore
            }
        }
        if (!useDefaultButton || defaultButton == null) {
            JButton button = aquaLnf ? new JideButton(JideIconsFactory.getImageIcon(JideIconsFactory.Arrow.DOWN)) : new JButton(JideIconsFactory.getImageIcon(JideIconsFactory.Arrow.DOWN));
            button.setRequestFocusEnabled(false);
            button.setFocusable(false);
            button.putClientProperty("doNotCancelPopup", null);
            button.setMargin(defaultButton != null ? defaultButton.getMargin() : new Insets(0, 0, 0, 0));
            if (defaultButton != null) {
                Dimension preferredSize = defaultButton.getPreferredSize();
                if (preferredSize.height < 16) {
                    int height = getHeight();
                    Insets insets = getInsets();
                    int buttonSize = height - (insets.top + insets.bottom);
                    preferredSize.height = buttonSize;
                    preferredSize.width = buttonSize;
                }
                button.setPreferredSize(preferredSize);
            }
            else {
                button.setPreferredSize(new Dimension(16, 16));
            }
            button.setEnabled(isEnabled());
            return button;
        }
        else {
            return defaultButton;
        }
    }

    /**
     * The popup window that will be shown when button is pressed.
     */
    private JidePopup _popup;

    private Dimension _preferredSize = null;

    /**
     * Creates the popup window. By default it will create a JidePopup which is not detached and not resizable. Subclass
     * can override it to create your own JidePopup or customize the default one.
     *
     * @return the popup window.
     */
    protected JidePopup createPopupWindow() {
        JidePopup popup = com.jidesoft.popup.JidePopupFactory.getSharedInstance().createPopup();
        popup.setDetached(false);
        popup.setDefaultMoveOperation(JidePopup.MOVE_ON_MOVED);
        popup.setPopupBorder(UIDefaultsLookup.getBorder("PopupMenu.border"));
        return popup;
    }


    protected PopupPanel createPopupPanel(final DecisionTablePane decisionTablePane, final DecisionField field, final Object[] possibleValues) {
        final boolean isCustomFilterAllowed = field.isCustomFilterAllowed();

        final CheckBoxListChooserPanel listChooserPanel = new CheckBoxListChooserPanel(possibleValues, field.getType()) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected Vector<Object> convertComboBoxModelToVector(ComboBoxModel comboBoxModel) {
                Vector<Object> vector = super.convertComboBoxModelToVector(comboBoxModel);
                vector.add(0, Filter.ALL);
                if (isCustomFilterAllowed) {
                    vector.add(1, Filter.CUSTOM);
                }
                return vector;
            }

//            @Override
//            protected JList createList(ComboBoxModel comboBoxModel) {
//                Vector<?> vector = convertComboBoxModelToVector(comboBoxModel);
//                return new CheckBoxList(vector) {
//                    public boolean isCheckBoxEnabled(int index) {
//                        return index != 1;
//                    }
//
//                    @Override
//                    public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
//                        return -1;
//                    }
//
//                    @Override
//                    public void processMouseEvent(MouseEvent e) {
//                        if ((e.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0) {
//                            // Fix for 4234053. Filter out the Control Key from the list.
//                            // ie., don't allow CTRL key deselection.
//                            e = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(),
//                                    e.getModifiers() ^ Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),
//                                    e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
//                        }
//                        super.processMouseEvent(e);
//                    }
//                };
//            }

            @Override
            protected void setupList(final JList list) {
                super.setupList(list);
                list.setLocale(decisionTablePane.getLocale());
                final FieldListCellRenderer cellRenderer = new FieldListCellRenderer(getField().getType()) {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public String convertElementToString(Locale locale, Object value, Class<?> clazz, ConverterContext context) {
                        return decisionTablePane.convertElementToString(locale, value, clazz, context);
                    }
                };
                cellRenderer.setConverterContext(getField().getConverterContext());
                list.setCellRenderer(cellRenderer);
                new ListSearchable(list) {
                    @Override
                    protected String convertElementToString(Object object) {
                        return decisionTablePane.convertElementToString(decisionTablePane.getLocale(), object, cellRenderer.getType(), cellRenderer.getConverterContext());
                    }
                };
                CheckBoxListSelectionModel selectionModel = isCustomFilterAllowed ? new CustomValueFilterListSelectionModel() : new ValueFilterListSelectionModel();
                ((CheckBoxList) list).setCheckBoxListSelectionModel(selectionModel);
                final Filter filter = isCustomFilterAllowed ? getFilter() : null;
                if (filter != null) {
                    selectionModel.clearSelection();
                    selectionModel.addSelectionInterval(1, 1);
                }
                else {
                    Object[] selectPossibleValues = getSelectedPossibleValues();
                    if (selectPossibleValues == null) {
                        selectionModel.addSelectionInterval(0, 0);
                    }
                    else {
                        selectionModel.setValueIsAdjusting(true);
                        for (Object value : selectPossibleValues) {
                            ListModel model = list.getModel();
                            for (int j = 0; j < model.getSize(); j++) {
                                Object val = model.getElementAt(j);
                                if (JideSwingUtilities.equals(val, value)) {
                                    selectionModel.addSelectionInterval(j, j);
                                    break;
                                }
                            }
                        }
                        selectionModel.setValueIsAdjusting(false);
                    }
                }
                JideSwingUtilities.insertMouseListener(list, new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (isCustomFilterAllowed) {
                            if (list.getCellBounds(1, 1).contains(e.getPoint())) {
                                e.consume();
                            }
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (isCustomFilterAllowed) {
                            if (list.getCellBounds(1, 1).contains(e.getPoint())) {
                                showCustomFilterDialog(field, possibleValues);
                                e.consume();
                            }
                        }
                    }
                }, 0);
            }
        };
//        listChooserPanel.setRenderer(getRenderer());
        listChooserPanel.setMaximumRowCount(8);
        listChooserPanel.setOkAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
            private static final long serialVersionUID = 2702038447764295109L;

            public void actionPerformed(ActionEvent e) {
                if (_decisionTablePane != null) {
                    _decisionTablePane.stopCellEditing();
                }

                CheckBoxList list = (CheckBoxList) listChooserPanel.getList();
                CheckBoxListSelectionModel selectionModel = list.getCheckBoxListSelectionModel();
                if (selectionModel != null) {
                    if (selectionModel.isSelectedIndex(0)) {
                        setSelectedPossibleValues(null);
                        setFilter(null);
                    }
                    else {
                        setFilter(null);
                        Object[] possibleValues = getPossibleValues();
                        Vector<Object> selected = new Vector<Object>();
                        for (int i = 0; i < possibleValues.length; i++) {
                            if (selectionModel.isSelectedIndex(i + (isCustomFilterAllowed ? 2 : 1))) {
                                selected.add(possibleValues[i]);
                            }
                        }
                        setSelectedPossibleValues(selected.toArray(new Object[selected.size()]));
                    }
                }
                hidePopup();
            }
        });
        listChooserPanel.setCancelAction(new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
            private static final long serialVersionUID = 9088271180396048607L;

            public void actionPerformed(ActionEvent e) {
                hidePopup();
            }
        });
        listChooserPanel.setResizable(true);
        return listChooserPanel;
    }

    private void showCustomFilterDialog(DecisionField field, Object[] possibleValues) {
        hidePopup();
        final CustomFilterEditor filterEditor = createCustomFilterEditor(FilterFactoryManager.getDefaultInstance(), field.getType(), field.getConverterContext(), possibleValues);
        Filter filter = getFilter();
        filterEditor.setFilter(filter);
        filterEditor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Container container = this.getTopLevelAncestor();
        CustomFilterEditorDialog dialog;
        String title = MessageFormat.format(GridResource.getResourceBundle(this.getLocale()).getString("Filter.customFilterTitle"), field.getName());
        if (container instanceof Frame) {
            dialog = new CustomFilterEditorDialog((Frame) container, title, filterEditor);
        }
        else if (container instanceof Dialog) {
            dialog = new CustomFilterEditorDialog((Dialog) container, title, filterEditor);
        }
        else {
            dialog = new CustomFilterEditorDialog((Frame) null, title, filterEditor);
        }
        dialog.setLocationRelativeTo(this);
        dialog.pack();
        dialog.setVisible(true);
        if (dialog.getDialogResult() == StandardDialog.RESULT_AFFIRMED) {
            setFilter(filterEditor.getFilter());
        }
    }

    /**
     * Creates the CustomFilterEditor used in the FieldBox's custom filter drop down list.
     *
     * @param filterFactoryManager the FilterFactoryManager.
     * @param type                 the type.
     * @param converterContext     the ConverterContext.
     * @param possibleValues       the possible values.
     * @return a new instance of CustomFilterEditor.
     */
    protected CustomFilterEditor createCustomFilterEditor(FilterFactoryManager filterFactoryManager, Class<?> type, ConverterContext converterContext, Object[] possibleValues) {
        return new CustomFilterEditor(filterFactoryManager, type, converterContext, possibleValues);
    }

    protected void customizePopupWindow(JidePopup popup, PopupPanel popupPanel) {
        popup.setLayout(new BorderLayout(2, 2));
        popup.add(popupPanel);
    }

    /**
     * calculate the popup location.
     *
     * @param popupPanel the PopupPanel
     * @return the location of popup.
     */
    protected Point calculatePopupLocation(PopupPanel popupPanel) {
        Border border = _popup.getPopupBorder();
        int xOffset = 0;
        int yOffset = 0;
        if (border != null) {
            Insets borderInsets = border.getBorderInsets(popupPanel);
            xOffset = borderInsets.left + borderInsets.right;
            yOffset = borderInsets.top + borderInsets.bottom;
        }
        Point p;
        try {
            p = getLocationOnScreen();
        }
        catch (IllegalArgumentException e) {
            p = getLocation();
        }
        p.y += getHeight();
        Dimension size = popupPanel.getPreferredSize();
        p.x -= size.width - getWidth() + xOffset;

        int bottom = p.y + size.height;

        Rectangle screenBounds = PortingUtils.getContainingScreenBounds(new Rectangle(p, size), true);
        if (bottom > screenBounds.y + screenBounds.height) {
            p.y = p.y - size.height - getHeight() - yOffset; // flip to upward
        }

        Rectangle bounds = PortingUtils.containsInScreenBounds(this, new Rectangle(p, size));
        p.x = bounds.x;
        p.y = bounds.y;

        return p;
    }

    /**
     * Causes the combo box to close its popup window.
     */
    public void hidePopup() {
        if (isPopupVisible()) {
            _popup.hidePopup();
        }
    }

    /**
     * Determines the visibility of the popup.
     *
     * @return true if the popup is visible, otherwise returns false
     */
    public boolean isPopupVisible() {
        return _popup != null && _popup.isPopupVisible();
    }

    public DecisionField getField() {
        return _field;
    }

    public DecisionTablePane getDecisionTablePane() {
        return _decisionTablePane;
    }

    public void setDecisionTablePane(DecisionTablePane decisionTablePane) {
        _decisionTablePane = decisionTablePane;
        if (_decisionTablePane != null) {
            updateFilterIcon();
        }
    }

    private transient boolean _isLast;
    private transient boolean _isFirst;

    /**
     * Checks if the decision field is the last one in its current FieldArea.
     *
     * @return true if it's the last one.
     */
    boolean isLast() {
        return _isLast;
    }

    void setLast(boolean last) {
        _isLast = last;
    }

    /**
     * Checks if the decision field is the first one in its current FieldArea.
     *
     * @return true if it's the last one.
     */
    boolean isFirst() {
        return _isFirst;
    }

    void setFirst(boolean first) {
        _isFirst = first;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (_filterButton != null) {
            remove(_filterButton);
            addFilterButton();
        }
    }
}