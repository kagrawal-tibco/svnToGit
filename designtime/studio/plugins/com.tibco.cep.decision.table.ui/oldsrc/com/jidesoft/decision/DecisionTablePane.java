package com.jidesoft.decision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.jidesoft.comparator.ObjectComparatorManager;
import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.filter.Filter;
import com.jidesoft.grid.Field;
import com.jidesoft.grid.FilterableTableModel;
import com.jidesoft.grid.GridIconsFactory;
import com.jidesoft.grid.GridResource;
import com.jidesoft.grid.IFilterableTableModel;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.MultipleValuesFilter;
import com.jidesoft.grid.SortableTable;
import com.jidesoft.grid.SortableTableModel;
import com.jidesoft.grid.TableModelWrapperUtils;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.grid.TableScrollPaneSearchable;
import com.jidesoft.grid.TableUtils;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideCursors;
import com.jidesoft.swing.JidePopupMenu;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.PartialGradientLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.utils.ColorUtils;
import com.tibco.cep.decision.table.calendar.DecisionTableDateTimeFieldCalendar;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.PropertyUpdateCommand;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnMoveCommandListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnRemovalCommandListener;
import com.tibco.cep.decision.table.constraintpane.DecisionTable;
import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.editors.IDecisionTableEditorInput;
import com.tibco.cep.decision.table.editors.listener.DecisionTableCellSelectionListener;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.ImageIconsFactory;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DecisionTablePane extends JPanel implements DecisionConstants {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean _showFilterIcon = false;
    private Icon _filterIcon = null;
    protected Map<DecisionField, DecisionFieldBox> _fieldBoxMap;

    protected DecisionFieldArea _conditionFieldsArea;
    protected DecisionFieldArea _actionFieldsArea;

    private DecisionDataModel _decisionDataModel;
    private DecisionTableEditor _decisionTableEditor;
    private JTextField _functionTextField;
    private boolean _hideFieldOnDraggingOut = false;
    private Cursor _dragNoDropCursor = null;
    private Cursor _dragRemoveCursor = JideCursors.getPredefinedCursor(JideCursors.DELETE_CURSOR);
    private String _version;
    private DecisionTableModel _decisionTableModel;
    private JPanel _areasPanel;
    private DecisionFieldBoxInputListener _listener;
    private TableScrollPane _pane;
    private SortableTableModel _sortableTableModel;
    private IFilterableTableModel _filterableTableModel;
    private PropertyChangeListener _fieldPropertyChangeListener;
    private PopupMenuCustomizer _dataTablePopupMenuCustomizer;
    private PopupMenuCustomizer _headerTablePopupMenuCustomizer;
    
    private Map<String, Integer> preferredColumnWidths = new HashMap<String,Integer>();  

	public static final String FIELD_SETTING_DIALOG_OK_BUTTON = "OptionPane.okButtonText";
    public static final String FIELD_SETTING_DIALOG_CANCEL_BUTTON = "OptionPane.cancelButtonText";
    public static final String FIELD_SETTING_DIALOG_REMOVE_BUTTON = "DecisionTablePane.remove";

    protected static final String CLIENT_PROPERTY_COLUMN_MODEL_LISTENER = "DecisionTablePane.columnModelListener";
    protected static final String CLIENT_PROPERTY_PROPERTY_CHANGE_LISTENER = "DecisionTablePane.propertyChangeListener";
    protected static final String CLIENT_PROPERTY_COMPONENT_LISTENER = "DecisionTablePane.componentListener";

    private PopupMenuCustomizer _popupMenuCustomizer;
    private PopupMenuCustomizer _tablePopupMenuCustomizer;
    private PopupMenuCustomizer _rowHeaderPopupMenuCustomizer;
    private boolean _rearrangable = true;

    public static final int FIELD_GAP = 2;

    public DecisionTablePane(DecisionDataModel decisionDataModel,
    		                 DecisionTableEditor decisionTableEditor,
    		                 JTextField functionTextField) {
        _decisionDataModel = decisionDataModel;
        _decisionTableEditor = decisionTableEditor;
        _functionTextField = functionTextField;
        _decisionDataModel.addDecisionDataModelListener(new DecisionDataModelListener() {
            @Override
            public void eventHappened(DecisionDataModelEvent e) {
                switch (e.getType()) {
                    case DecisionDataModelEvent.DECISION_CONDITION_ADDED:
                    case DecisionDataModelEvent.DECISION_CONDITION_REMOVED:
                        conditionFieldsUpdated();
                        break;
                    case DecisionDataModelEvent.DECISION_ACTION_ADDED:
                    case DecisionDataModelEvent.DECISION_ACTION_REMOVED:
                        actionFieldsUpdated();
                        break;
                    case DecisionDataModelEvent.DECISION_RULE_ADDED:
                    case DecisionDataModelEvent.DECISION_RULE_REMOVED:
                        getDecisionTableModel().fireTableDataChanged();
                        break;
                }
            }
        });
        initComponents();
    }

    protected void initComponents() {
        if (_listener == null) {
            _listener = createDecisionFieldBoxInputListener();
        }

        _fieldBoxMap = new WeakHashMap<DecisionField, DecisionFieldBox>();

        _conditionFieldsArea = createFieldArea(AREA_CONDITION);
        _conditionFieldsArea.setBackground(new Color(0xFFFFD7));
        _actionFieldsArea = createFieldArea(AREA_ACTION);
        fillConditionFieldArea();
        fillActionFieldArea();
        _decisionTableModel = new DecisionTableModel(_decisionDataModel, false);

        _areasPanel = new JPanel();
        _areasPanel.setLayout(new JideBoxLayout(_areasPanel, JideBoxLayout.X_AXIS, 0));
        _areasPanel.add(_conditionFieldsArea);
        _areasPanel.add(_actionFieldsArea, JideBoxLayout.VARY);


        _sortableTableModel = new SortableTableModel(_decisionTableModel);
        _filterableTableModel = new FilterableTableModel(_sortableTableModel);
        _pane = new TableScrollPane((FilterableTableModel) _filterableTableModel) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected JTable createTable(TableModel model, boolean sortable) {
                SortableTable sortableTable = new SortableTable(model) {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    protected void configureEnclosingScrollPane() {
                        Container p = getParent();
                        if (p instanceof JViewport) {
                            Container parent = p.getParent();
                            if (parent instanceof JScrollPane) {
                                JScrollPane scrollPane = (JScrollPane) parent;
                                // Make certain we are the viewPort's view and not, for
                                // example, the rowHeaderView of the scrollPane -
                                // an implementation of fixed columns might do this.
                                JViewport viewport = scrollPane.getViewport();
                                if (viewport == null || viewport.getView() != _areasPanel) {
                                    return;
                                }
                                scrollPane.setColumnHeaderView(_areasPanel);
                                Border border = scrollPane.getBorder();
                                if (border == null || border instanceof UIResource) {
                                    Border scrollPaneBorder =
                                            UIDefaultsLookup.getBorder("Table.scrollPaneBorder");
                                    if (scrollPaneBorder != null) {
                                        scrollPane.setBorder(scrollPaneBorder);
                                    }
                                }
                            }
                        }
                    }
                };
                sortableTable.setClickCountToStart(2);
                sortableTable.setEditorAutoCompletionMode(JideTable.EDITOR_AUTO_COMPLETION_MODE_COLUMN);
                boolean readOnly = ((IDecisionTableEditorInput)_decisionTableEditor.getEditorInput()).isReadOnly();
                sortableTable.setEnabled(!readOnly);
                return sortableTable;
            }
        };

        new TableScrollPaneSearchable(_pane);
                      
        ((JideTable) _pane.getMainTable()).setColumnResizable(true);
        
//        IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
//        ((JideTable) _pane.getMainTable()).setRowAutoResizes(prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_ROWS));

        MouseListener listener = createPopupMenuMouseListener();
        JTable mainTable = _pane.getMainTable();
        mainTable.getTableHeader().setReorderingAllowed(true);
        mainTable.addMouseListener(listener);
//        mainTable.setCellSelectionEnabled(true);
        
		attachListeners(mainTable);
		JTable rowHeaderTable = _pane.getRowHeaderTable();
		rowHeaderTable.setCellSelectionEnabled(false);
		//ID table.
		attachListeners(rowHeaderTable);
        _pane.getRowHeaderTable().addMouseListener(listener);
        adjustFieldAreaSize(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(_pane);

        _pane.setColumnHeaderView(_areasPanel);
        ((SortableTable) _pane.getRowHeaderTable()).setDefaultCellRenderer(new RowHeaderCellRenderer());
        ((SortableTable) _pane.getRowHeaderTable()).getColumnModel().getColumn(0).setPreferredWidth(50);

        synchronizeFieldAreaWithColumnWidth(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);

        setLayout(new BorderLayout());
        add(tablePanel);
    }
    
    protected void attachListeners(JTable mainTable) {
    	DecisionTableCellSelectionListener mouseKeyListener = 
    		new DecisionTableCellSelectionListener(_functionTextField, _pane, _decisionTableEditor, _decisionDataModel.getTableType());
		mainTable.addMouseListener(mouseKeyListener);
		_pane.addMouseListener(mouseKeyListener);
		mainTable.addKeyListener(mouseKeyListener);
		DecisionTableMouseMotionListener mouseMotionListener = new DecisionTableMouseMotionListener(this);
		mainTable.addMouseMotionListener(mouseMotionListener);
    }
    
    /**
     * 
     * @param rowIndex
     * @param columnIndex
     * @param rect
     * @param table
     */
    public void showCalendarPopup(int rowIndex, int columnIndex, Rectangle rect, JTable table) {
//		if (actionpopup != null && actionpopup.isPopupVisible()) {
//			actionpopup.hidePopup();
//		}
		TableCellEditor cellEditor = table.getCellEditor(rowIndex, columnIndex);
		if (cellEditor instanceof DefaultRuleVariableCellEditor) {
			DefaultRuleVariableCellEditor ruleVariableCellEditor = (DefaultRuleVariableCellEditor) cellEditor;
			ConverterContext converterContext = ruleVariableCellEditor.getConverterContext();
			if (converterContext != null) {
				Object object = converterContext.getUserObject();
				if (object instanceof UserObject) {
					UserObject userObject = (UserObject) object;
					if (DecisionTableUtil.checkDateType(userObject.getColumn().getPropertyType())) {
						// Condition Cells pop-up
						JidePopup actionpopup = new JidePopup();
						actionpopup.setPopupType(JidePopup.HEAVY_WEIGHT_POPUP);
						if (DecisionTableUtil.IsEditorPopupMenuEnabled) {
							calendarToolbarPanel(table,
									             actionpopup,
									             rowIndex,
									             columnIndex,
									             rect);
						}
						actionpopup.setPopupBorder(BorderFactory.createEmptyBorder());
						if (DecisionTableUtil.actionpopup != null) {
							DecisionTableUtil.hideAllEditorPopups(getDecisionTableEditor().getSite().getWorkbenchWindow().getActivePage().getActiveEditor(), true);
						}
						DecisionTableUtil.actionpopup = actionpopup;

						Point cp = new Point(rect.x, rect.y + 1
								+ table.getIntercellSpacing().height);
						SwingUtilities.convertPointToScreen(cp, table);
						DecisionTableUtil.actionpopup.showPopup(cp.x, cp.y, table);
					}
				}
			}
		}
	}
	
	private void calendarToolbarPanel(final JTable table, final JidePopup popup, final int selectedRow , final int selectedColumn, final Rectangle rect) {
		SmallButtonPanel buttonPanel = new SmallButtonPanel();
		//Create the calendar button.
		JideButton calendarButton = 
			createButton(ImageIconsFactory.createImageIcon("calendar_10x10.png"));
		calendarButton.setToolTipText("Edit DateTimeField");
		calendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDateTime(table, selectedRow, selectedColumn, rect);
				popup.setVisible(false);
			}
		});
		buttonPanel.add(calendarButton);
		popup.add(buttonPanel);
	}
	
	private void editDateTime(final JTable table, 
			                  final int selectedRow, 
			                  final int selectedColumn,
			                  final Rectangle rect) {
		final DecisionTablePane decisionTablePane = this;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					IWorkbenchWindow window = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
					new DecisionTableDateTimeFieldCalendar(window.getShell(),
							decisionTablePane, table, selectedRow,
							selectedColumn).open();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	private static final int SMALL_BUTTON_SIZE = 12;
	
	protected JideButton createButton(Icon icon) {
		JideButton button = new JideButton(icon);
		button.setPreferredSize(new Dimension(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE));
		button.setMaximumSize(new Dimension(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE));
		button.setMinimumSize(new Dimension(SMALL_BUTTON_SIZE, SMALL_BUTTON_SIZE));
		button.setOpaque(false);
		return button;
	}

    /**
	 * @return the _decisionTableEditor
	 */
	public final DecisionTableEditor getDecisionTableEditor() {
		return _decisionTableEditor;
	}

	public DecisionRule[] getSelectedRules() {
        int[] indices = _pane.getSelectedRows();
        if (indices == null) {
            return new DecisionRule[0];
        }
        DecisionRule[] rules = new DecisionRule[indices.length];
        for (int i = 0; i < indices.length; i++) {
            rules[i] = _decisionTableModel.getDecisionRuleAt(TableModelWrapperUtils.getActualRowAt(_pane.getTableModel(), indices[i]), 0);
        }
        return rules;
    }

	public DecisionRule getSelectedRule(int selectedrow) {      
            return _decisionTableModel.getDecisionRuleAt(TableModelWrapperUtils.getActualRowAt(_pane.getTableModel(), selectedrow), 0);       
    }
	
    public int[] getSelectedRuleIndices() {
        int[] indices = _pane.getSelectedRows();
        if (indices == null) {
            return new int[0];
        }
        for (int i = 0; i < indices.length; i++) {
            indices[i] = TableModelWrapperUtils.getActualRowAt(_pane.getTableModel(), indices[i]);
        }
        return indices;
    }

    public void setSelectedRule(int ruleIndex) {
        int visualRuleIndex = TableModelWrapperUtils.getRowAt(_pane.getTableModel(), ruleIndex);
        _pane.getMainTable().getSelectionModel().setSelectionInterval(visualRuleIndex, visualRuleIndex);
    }

    public void setSelectedRules(int firstRuleIndex, int lastRuleIndex) {
        int visualFirstRuleIndex = TableModelWrapperUtils.getRowAt(_pane.getTableModel(), firstRuleIndex);
        int visualLastRuleIndex = lastRuleIndex == firstRuleIndex ? visualFirstRuleIndex : TableModelWrapperUtils.getRowAt(_pane.getTableModel(), lastRuleIndex);
        _pane.getMainTable().getSelectionModel().setSelectionInterval(visualFirstRuleIndex, visualLastRuleIndex);
    }

    protected DecisionFieldArea createFieldArea(int areaType) {
        DecisionFieldArea fieldArea = new DecisionFieldArea(this, areaType);
        
        IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
        
        if (prefStore.getBoolean(PreferenceConstants.SHOW_TITLED_BORDER) == true) {
	        /**
	         * This logic is for setting area titles like conditon/action etc.
	         */
	        Color foreGroundColor = fieldArea.getForeground();
	        TitledBorder titledBorder = new TitledBorder(new PartialGradientLineBorder(new Color[] { foreGroundColor, UIDefaultsLookup.getColor("control") }, 1, PartialSide.NORTH), " "
					+ getFieldAreaName(areaType) + " ", TitledBorder.LEADING, TitledBorder.CENTER) {
				private static final long serialVersionUID = -5745691255576884703L;
	
				@Override
				public Insets getBorderInsets(Component c, Insets insets) {
					return new Insets(14, 0, 0, 0);
				}
			};
			titledBorder.setTitleColor(foreGroundColor);
			fieldArea.setBorder(titledBorder);
        }
		
		return fieldArea;
    }

    /**
     * Creates the FieldBoxInputListener. This is the listener added to the FieldBox to implement drag-n-drop, handle
     * clicks etc.
     *
     * @return FieldBoxInputListener.
     */
    protected DecisionFieldBoxInputListener createDecisionFieldBoxInputListener() {
        return new DecisionFieldBoxInputListener(this);
    }

    /**
     * Creates the FieldBox.
     *
     * @param field               the decision field
     * @param sortArrowVisible    whether sort arrow should be visible.
     * @param filterButtonVisible whether the filter button should be visible.
     * @return a Field Box.
     */
    protected DecisionFieldBox createFieldBox(DecisionField field, boolean sortArrowVisible, boolean filterButtonVisible) {
        return new DecisionFieldBox(field, sortArrowVisible, filterButtonVisible);
    }

    /**
     * Customizes the FieldBox.
     *
     * @param box the field box to be customized.
     */
    protected void customizeFieldBox(DecisionFieldBox box) {
        if (box.getDecisionTablePane() != this) {
            box.setDecisionTablePane(this);
            box.addMouseListener(_listener);
            box.addMouseMotionListener(_listener);
            box.addKeyListener(_listener);
            box.addActionListener(_listener);
            box.addPropertyChangeListener(_listener);
            synchronizeFieldAreaWithField(box);
        }
    }

    protected MouseListener createPopupMenuMouseListener() {
        return new PopupMenuMouseListener();
    }

    protected class PopupMenuMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            showContextMenu(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showContextMenu(e);
        }
    }

    public PopupMenuCustomizer getDataTablePopupMenuCustomizer() {
        return _dataTablePopupMenuCustomizer;
    }

    public void setDataTablePopupMenuCustomizer(PopupMenuCustomizer dataTablePopupMenuCustomizer) {
        _dataTablePopupMenuCustomizer = dataTablePopupMenuCustomizer;
    }

    public PopupMenuCustomizer getHeaderTablePopupMenuCustomizer() {
        return _headerTablePopupMenuCustomizer;
    }

    public void setHeaderTablePopupMenuCustomizer(PopupMenuCustomizer headerTablePopupMenuCustomizer) {
        _headerTablePopupMenuCustomizer = headerTablePopupMenuCustomizer;
    }

    private boolean isEditorRedOnly(){
    	IEditorInput dei = _decisionTableEditor.getEditorInput();
    	if (dei instanceof IDecisionTableEditorInput && !((IDecisionTableEditorInput)dei).isReadOnly()) {
    		return false;
    	}
    	return true;
    }
    

    //BE-17529
	private void addPropertyUpdateCommand(String project, Table tableEModel,
										  TableRuleVariable tableRuleVariable, Boolean isEnabled) {
		PropertyUpdateCommand.PROPERTY_FEATURE propertyFeature = PropertyUpdateCommand.PROPERTY_FEATURE.ENABLED;
		CommandFacade.getInstance().executeCellPropertyUpdates(project,	tableEModel, tableRuleVariable, 
																isEnabled, propertyFeature);
	}

    private void showContextMenu(MouseEvent e) {
//    	IEditorInput dei = _decisionTableEditor.getEditorInput();
        if (e.isPopupTrigger()) {
            Point p = e.getPoint();
            JTable table = (JTable) e.getSource();
            final int rowIndex = table.rowAtPoint(p);
            final int columnIndex = table.columnAtPoint(p);

            JPopupMenu popupMenu = new JidePopupMenu();

            final int actualRowIndex = TableModelWrapperUtils.getActualRowAt(_pane.getTableModel(), rowIndex, _decisionTableModel);
            final ResourceBundle resourceBundle = DecisionResources.getResourceBundle(Locale.getDefault());
            if (_pane.getMainTable() == table) {
                final DecisionEntry entry = _decisionTableModel.getDecisionEntryAt(actualRowIndex, columnIndex + 1, false);
                if (entry != null) {
                	Object value = _decisionTableModel.getValueAt(actualRowIndex, columnIndex + 1);
                	final String comment;
                	if (value instanceof TableRuleVariable) {
                		
                		TableRuleVariable trv = (TableRuleVariable)value;
                		
                		comment = trv.getComment();
                		JMenuItem commentMenu = new JMenuItem(new AbstractAction(resourceBundle.getString(comment != null && comment.trim().length() > 0 ? "DecisionTablePane.editComment" : "DecisionTablePane.addComment")) {
	                        private static final long serialVersionUID = -1364305920490342951L;
	
	                        public void actionPerformed(ActionEvent e) {
	                        	
	                        	Display.getDefault().asyncExec(new Runnable() {
	    			    			public void run() {
	    					    		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    					    		CommentDialog dialog = new CommentDialog(window.getShell());
	    	                            dialog.setText(comment);
	    	                            dialog.create();
	    	                            dialog.open();
	    	                            _decisionTableModel.setCommentsAt(dialog.getText(), actualRowIndex, columnIndex + 1);
	    	                            Object value = _decisionTableModel.getValueAt(actualRowIndex, columnIndex + 1);
	    	                            String newComment = dialog.getText();
	    	                            if (newComment == null)
	    	                            	newComment = "";
	    	                            if (value instanceof TableRuleVariable) {
	    	                            	TableRuleVariable trv = (TableRuleVariable)value;
	    	                            	if (newComment.trim().equals("")) {
	    	                            		_decisionTableModel.setCommentsAt(null, actualRowIndex, columnIndex + 1);
	    	                            		trv.setComment(null);
	    	                            	} else {
	    	                            		trv.setComment(newComment);
	    	                            	}
	    	                            	asyncModified();
	    	                            }
	    			    			}
	    			    		});
	                        }
	                    });                		

                		if(isEditorRedOnly()){
                    		commentMenu.setEnabled(false);
                		}
                		
	                    JMenuItem menu = popupMenu.add(commentMenu);
                	
	                    menu.setMnemonic(resourceBundle.getString(comment != null && comment.trim().length() > 0 ? "DecisionTablePane.editComment.mnemonic" : "DecisionTablePane.addComment.mnemonic").charAt(0));
	                    
	                    if (comment != null && comment.trim().length() > 0) {
	                    	JMenuItem deleteComment = new JMenuItem(new AbstractAction(resourceBundle.getString("DecisionTablePane.deleteComment")) {
	                            private static final long serialVersionUID = -1455929403125906768L;
	
	                            public void actionPerformed(ActionEvent e) {
	                                _decisionTableModel.setCommentsAt(null, actualRowIndex, columnIndex + 1);
	                                Object value = _decisionTableModel.getValueAt(actualRowIndex, columnIndex + 1);
		                            if (value instanceof TableRuleVariable) {
		                            	TableRuleVariable trv = (TableRuleVariable)value;
		                            	trv.setComment(null);
		                            	asyncModified();
		                            }
	                            }
	                        });
	                    	if(isEditorRedOnly()){
	                    		deleteComment.setEnabled(false);
	                    	}
	                        menu = popupMenu.add(deleteComment);
	                        menu.setMnemonic(resourceBundle.getString("DecisionTablePane.deleteComment.mnemonic").charAt(0));
	                    }
	                    if (trv.isEnabled() == true) {
	                    	JMenuItem disableMenu = new JMenuItem(new AbstractAction(resourceBundle.getString("DecisionTablePane.disable")) {
	                            private static final long serialVersionUID = 6378003926880550254L;
	
	                            public void actionPerformed(ActionEvent e) {
	                                Object value = _decisionTableModel.getValueAt(actualRowIndex, columnIndex + 1);
		                            if (value instanceof TableRuleVariable) {
		                            	TableRuleVariable trv = (TableRuleVariable)value;
		                            	if (!trv.getExpr().isEmpty()) {
		                            		DecisionTable constraintsTable = _decisionTableEditor.getDecisionTable();
		                            		Table tableEModel = _decisionTableEditor.getDecisionTableModelManager().getTabelEModel();
		                            		List<Column> columns = tableEModel.getDecisionTable().getColumns().getColumn();
		                            		ColumnType colType = null;
		                            		String colName = "";
		                            		for (Column col : columns) {
		                            			if (col.getId().equals(trv.getColId())) {
		                            				colType = col.getColumnType();
		                            				colName = col.getName();
		                            			}
		                            		}
		                            		boolean isDefaultInvoked = 
		                            			DecisionTableUtil.checkRuleForDefaultInvocation(trv.getId(), tableEModel, colType);
		                            		
		                            		if (isDefaultInvoked) {
		                            			Display.getDefault().syncExec(
													new Runnable() {
														public void run() {
															String error = "Rule is invoked by default now";
															String dialogTitle = "Default rule invocation";
															MessageDialog.openWarning(_decisionTableEditor.getSite().getShell(),
																			dialogTitle,
																			error);
														}
													});
		                            			addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, false);
												trv.setEnabled(false);
				                            	asyncModified();
		                            			constraintsTable.toggleCell(colName, trv, colType);
		    		                            _decisionTableModel.setEnabledAt(false, actualRowIndex, columnIndex + 1);
		                            		} else {
		                            			addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, false);
		                            			trv.setEnabled(false);
				                            	asyncModified();
		                            			constraintsTable.toggleCell(colName, trv, colType);
		    		                            _decisionTableModel.setEnabledAt(false, actualRowIndex, columnIndex + 1);
		                            		}
		                            	}
		                            }
	                            }
	                        });
	                    	if(isEditorRedOnly()){
	                    		disableMenu.setEnabled(false);
	                    	}
	                        menu = popupMenu.add(disableMenu);
	                        menu.setMnemonic(resourceBundle.getString("DecisionTablePane.disable.mnemonic").charAt(0));
	                    }
	                    else {
	                    	JMenuItem enableMenu = new JMenuItem(new AbstractAction(resourceBundle.getString("DecisionTablePane.enable")) {
	                            private static final long serialVersionUID = -3712152267614041661L;
	
	                            public void actionPerformed(ActionEvent e) {
	                                _decisionTableModel.setEnabledAt(true, actualRowIndex, columnIndex + 1);
	                                Object value = _decisionTableModel.getValueAt(actualRowIndex, columnIndex + 1);
	                                Table tableEModel = _decisionTableEditor.getDecisionTableModelManager().getTabelEModel();
		                            if (value instanceof TableRuleVariable) {
		                            	TableRuleVariable trv = (TableRuleVariable)value;
		                            	addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, true);
		                            	trv.setEnabled(true);
		                            	asyncModified();
		                            	if (!trv.getExpr().isEmpty()) {
		                            		DecisionTable constraintsTable = _decisionTableEditor.getDecisionTable();
		                            		List<Column> columns = tableEModel.getDecisionTable().getColumns().getColumn();
		                            		ColumnType colType = null;
		                            		String colName = "";
		                            		for (Column col : columns) {
		                            			if (col.getId().equals(trv.getColId())) {
		                            				colType = col.getColumnType();
		                            				colName = col.getName();
		                            			}
		                            		}
		                            		constraintsTable.toggleCell(colName, trv, colType);
		                            	}
		                            }
	                            }
	                        });
	                    	
	                    	enableMenu.setEnabled(!isEditorRedOnly());	                    	
	                        menu = popupMenu.add(enableMenu);
	                        menu.setMnemonic(resourceBundle.getString("DecisionTablePane.enable.mnemonic").charAt(0));
	                    }
                	}
                }
            }
            else if (_pane.getRowHeaderTable() == table) {
               final DecisionRule rule = _decisionTableModel.getDecisionRuleAt(actualRowIndex, 0);
                if (rule != null) {
                	JMenuItem menu = null;
                  	JMenuItem popupItem = null;
                    if (rule.isEnabled()) {
                    	popupItem = new JMenuItem(new AbstractAction(resourceBundle.getString("DecisionTablePane.disable")) {
                            private static final long serialVersionUID = -7549493205297979056L;

                            public void actionPerformed(ActionEvent e) {
                            	rule.setEnabled(false);      
                                DecisionTable constraintsTable = _decisionTableEditor.getDecisionTable();
                                Table tableEModel = _decisionTableEditor.getDecisionTableModelManager().getTabelEModel();
                                for(DecisionEntry entry : rule.getActions()){
                             	   if(entry.getValue() instanceof TableRuleVariable){
                             		   TableRuleVariable trv = (TableRuleVariable)entry.getValue();
                             		   addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, rule.isEnabled());
                             		   trv.setEnabled(rule.isEnabled());                             		  
                             	   }
                             	}
                                 
                                for(DecisionEntry entry : rule.getConditions()){
                                	if(entry.getValue() instanceof TableRuleVariable){
                              		   TableRuleVariable trv = (TableRuleVariable)entry.getValue();
                              		   addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, rule.isEnabled());
                              		   trv.setEnabled(rule.isEnabled());                              		 
                              	   }
                                }
                                _decisionTableModel.getDecisionRuleAt(actualRowIndex, 0).setEnabled(false);
                                asyncModified();  
                                constraintsTable.toggleRule(rule);
                                _decisionTableModel.setEnabledAt(rule.isEnabled(), actualRowIndex, 0);
                                
                            }
                        });
                    	popupItem.setEnabled(!isEditorRedOnly());
                        menu = popupMenu.add(popupItem);
                        menu.setMnemonic(resourceBundle.getString("DecisionTablePane.disable.mnemonic").charAt(0));
                    }
                    else {
                    	popupItem = new JMenuItem(new AbstractAction(resourceBundle.getString("DecisionTablePane.enable")) {
                            private static final long serialVersionUID = -1463951463111857038L;

                            public void actionPerformed(ActionEvent e) {
                            	rule.setEnabled(true);
                            	DecisionTable constraintsTable = _decisionTableEditor.getDecisionTable();
                            	Table tableEModel = _decisionTableEditor.getDecisionTableModelManager().getTabelEModel();
                                for(DecisionEntry entry : rule.getActions()){
                             	   if(entry.getValue() instanceof TableRuleVariable){
                             		   TableRuleVariable trv = (TableRuleVariable)entry.getValue();
                             		   addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, rule.isEnabled());	
                             		   trv.setEnabled(rule.isEnabled());                             		  
                             	   }
                             	}
                                 
                                for(DecisionEntry entry : rule.getConditions()){
                                	if(entry.getValue() instanceof TableRuleVariable){
                              		   TableRuleVariable trv = (TableRuleVariable)entry.getValue();
                              		   addPropertyUpdateCommand(_decisionTableEditor.getProject().getName(), tableEModel, trv, rule.isEnabled());
                              		   trv.setEnabled(rule.isEnabled());                              		 
                              	   }
                                }
                                _decisionTableModel.getDecisionRuleAt(actualRowIndex, 0).setEnabled(true);
                                asyncModified();  
                                constraintsTable.toggleRule(rule);
                                _decisionTableModel.setEnabledAt(rule.isEnabled(), actualRowIndex, 0);
                            }
                        });
                       
                        popupItem.setEnabled(!isEditorRedOnly());
                        menu = popupMenu.add(popupItem);
                        menu.setMnemonic(resourceBundle.getString("DecisionTablePane.enable.mnemonic").charAt(0));
                    }
                }
            }

            PopupMenuCustomizer popupMenuCustomizer = table == _pane.getMainTable() ? getDataTablePopupMenuCustomizer() : getHeaderTablePopupMenuCustomizer();
            if (popupMenuCustomizer != null) {
                if (popupMenuCustomizer instanceof TablePopupMenuCustomizer) {
                    ((TablePopupMenuCustomizer) popupMenuCustomizer).customize(popupMenu, this, rowIndex, columnIndex);
                }
                else {
                    popupMenuCustomizer.customize(popupMenu, this);
                }
            }

            if (popupMenu.getComponentCount() > 0) {
                popupMenu.show(table, p.x, p.y);
            }
        }

    }
    
    private void asyncModified(){
  	  Display.getDefault().asyncExec(new Runnable(){

  		@Override
  		public void run() {
  			_decisionTableEditor.modified();
  		}});	
  	}

    public void synchronizeFieldAreaWithField(final DecisionFieldBox box) {
        if (_fieldPropertyChangeListener == null) {
            _fieldPropertyChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (DecisionField.PROPERTY_SELECTED_POSSIBLE_VALUES.equals(evt.getPropertyName())) {
                        // possible values changed
//                        Object[] selectedValues = saveSelection();
                        DecisionField field = (DecisionField) evt.getSource();
                        Object[] objects = (Object[]) evt.getNewValue();
                        int index = _decisionTableModel.indexOf(field);
                        _filterableTableModel.removeAllFilters(index + 1);
                        if (objects != null) {
                            _filterableTableModel.addFilter(index + 1, new MultipleValuesFilter(objects));
                        }
                        _filterableTableModel.setFiltersApplied(true);
//                        loadSelection(selectedValues);
                    }
                    else if (DecisionField.PROPERTY_FILTER.equals(evt.getPropertyName())) {
                        // possible values changed
//                        Object[] selectedValues = saveSelection();
                        DecisionField field = (DecisionField) evt.getSource();
                        Filter filter = (Filter) evt.getNewValue();
                        int index = _decisionTableModel.indexOf(field);
                        _filterableTableModel.removeAllFilters(index + 1);
                        if (filter != null) {
                            _filterableTableModel.addFilter(index + 1, filter);
                        }
                        _filterableTableModel.setFiltersApplied(true);
//                        loadSelection(selectedValues);
                    }
                    else if (DecisionField.PROPERTY_ASCENDING.equals(evt.getPropertyName())
                            || DecisionField.PROPERTY_SORT_ORDER.equals(evt.getPropertyName())) {
                        Object source = evt.getSource();
                        DecisionField field = (DecisionField) source;
                        int index = _decisionTableModel.indexOf(field);
                        if (field.getSortOrder() == Field.SORT_ORDER_UNSORTED) {
                            _sortableTableModel.unsortColumn(index + 1);
                        }
                        else {
                            _sortableTableModel.sortColumn(index + 1, false, field.getSortOrder() == Field.SORT_ORDER_ASCENDING);
                        }
                    }
                }
            };
        }
        PropertyChangeListener[] listeners = box.getField().getPropertyChangeListeners();
        boolean registered = false;
        for (PropertyChangeListener listener : listeners) {
            if (listener == _fieldPropertyChangeListener) {
                registered = true;
                break;
            }
        }
        if (!registered) {
            box.getField().addPropertyChangeListener(_fieldPropertyChangeListener);
        }
    }

    protected void uncustomizeFieldBox(DecisionFieldBox box) {
        box.uninstallListeners();
        box.setDecisionTablePane(null);
        box.removeMouseListener(_listener);
        box.removeMouseMotionListener(_listener);
        box.removeKeyListener(_listener);
        box.removeActionListener(_listener);
        if (_fieldPropertyChangeListener != null) {
            box.getField().removePropertyChangeListener(_fieldPropertyChangeListener);
        }
    }

    public void updatePossibleValues() {
        Set<DecisionField> keys = _fieldBoxMap.keySet();
        for (DecisionField field : keys) {
            DecisionFieldBox fieldBox = _fieldBoxMap.get(field);
            int index = _decisionTableModel.indexOf(field);
            fieldBox.setPossibleValues(_filterableTableModel.getPossibleValues(index + 1,
                    ObjectComparatorManager.getComparator(field.getType(), field.getComparatorContext())));
        }
    }

    public void updatePossibleValues(DecisionFieldBox box) {
        DecisionField field = box.getField();
        int index = _decisionTableModel.indexOf(field);
        box.setPossibleValues(_filterableTableModel.getPossibleValues(index + 1,
                ObjectComparatorManager.getComparator(field.getType(), field.getComparatorContext())));
    }

    private void fillConditionFieldArea() {
        List<DecisionField> fields = _decisionDataModel.getConditionFields();
        _conditionFieldsArea.removeAll();
        for (int i = 0; i < fields.size(); i++) {
            DecisionField field = fields.get(i);
            field.setAreaIndex(i * 2 + 1);
            DecisionFieldBox box = _fieldBoxMap.get(field);
            boolean filterable = field.isFilterable();
            if (box != null) {
                box.setSortArrowVisible(true);
                box.setFilterButtonVisible(filterable);
            }
            else {
                box = createFieldBox(field, true, filterable);
                _fieldBoxMap.put(field, box);
                customizeFieldBox(box);
            }
            box.setSelected(false);
            box.setFirst(i == 0);
            box.setLast(i == fields.size() - 1);
            _conditionFieldsArea.add(box);
        }
        _conditionFieldsArea.doLayout();
        _conditionFieldsArea.repaint();

        if (_actionFieldsArea != null && _pane != null) {
            _actionFieldsArea.setStartingColumnIndex(_conditionFieldsArea.getComponentCount());
            adjustFieldAreaSize(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        }
    }

    private void fillActionFieldArea() {
        List<DecisionField> fields = _decisionDataModel.getActionFields();
        _actionFieldsArea.removeAll();
        for (int i = 0; i < fields.size(); i++) {
            DecisionField field = fields.get(i);
            field.setAreaIndex(i * 2 + 1);
            DecisionFieldBox box = _fieldBoxMap.get(field);
            boolean filterable = field.isFilterable();
            if (box != null) {
                box.setSortArrowVisible(true);
                box.setFilterButtonVisible(filterable);
            }
            else {
                box = createFieldBox(field, true, filterable);
                _fieldBoxMap.put(field, box);
                customizeFieldBox(box);
            }
            box.setSelected(false);
            box.setFirst(i == 0);
            box.setLast(i == fields.size() - 1);
            _actionFieldsArea.add(box);
        }
        _actionFieldsArea.doLayout();
        _actionFieldsArea.repaint();

        if (_conditionFieldsArea != null && _pane != null) {
            adjustFieldAreaSize(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        }
    }

    public void synchronizeFieldAreaWithColumnWidth(final JTable table, final DecisionFieldArea firstFieldArea, final DecisionFieldArea secondFieldArea) {
        firstFieldArea.setTable(table, 0);
        secondFieldArea.setTable(table, firstFieldArea.getComponentCount());

        int conditionCount = firstFieldArea.getComponentCount();
        for (int i = 0; i < conditionCount; i++) {
        	TableColumnModel tableColumnModel = table.getColumnModel();
        	tableColumnModel.getColumn(i).setPreferredWidth(firstFieldArea.getComponent(i).getPreferredSize().width + FIELD_GAP * 2);
        }
        for (int i = 0; i < secondFieldArea.getComponentCount(); i++) {
            table.getColumnModel().getColumn(i + conditionCount).setPreferredWidth(secondFieldArea.getComponent(i).getPreferredSize().width + FIELD_GAP * 2);
        }

        if (table.getClientProperty(CLIENT_PROPERTY_COLUMN_MODEL_LISTENER) == null) {
            final TableColumnModelListener columnModelListener = new TableColumnModelListener() {
                public void columnMarginChanged(ChangeEvent e) {
                    adjustFieldAreaSize(table, firstFieldArea, secondFieldArea);
                }

                public void columnSelectionChanged(ListSelectionEvent e) {
                }

                public void columnAdded(TableColumnModelEvent e) {
                }

                public void columnMoved(TableColumnModelEvent e) {
                }

                public void columnRemoved(TableColumnModelEvent e) {
                }
            };
            table.getColumnModel().addColumnModelListener(columnModelListener);
            table.putClientProperty(CLIENT_PROPERTY_COLUMN_MODEL_LISTENER, columnModelListener);
        }

        if (table.getClientProperty(CLIENT_PROPERTY_PROPERTY_CHANGE_LISTENER) == null) {
            final Object listener = table.getClientProperty(CLIENT_PROPERTY_COLUMN_MODEL_LISTENER);
            final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("columnModel".equals(evt.getPropertyName())) {
                        TableColumnModel model = (TableColumnModel) evt.getOldValue();
                        if (model != null) {
                            model.removeColumnModelListener((TableColumnModelListener) listener);
                        }
                        model = (TableColumnModel) evt.getNewValue();
                        if (model != null) {
                            model.addColumnModelListener((TableColumnModelListener) listener);
                        }
                    }
                    else if ("model".equals(evt.getPropertyName())) {
                        updateTableColumnWidth(table, firstFieldArea, secondFieldArea);
                    }
                }
            };
            table.addPropertyChangeListener(propertyChangeListener);
            table.putClientProperty(CLIENT_PROPERTY_PROPERTY_CHANGE_LISTENER, propertyChangeListener);
        }
        postSynchronizeFieldAreaWithColumnWidth(table);
    }
    
    /**
     * Calculates Table preferred widths
     * @param table
     */
    private void postSynchronizeFieldAreaWithColumnWidth(JTable table) {
    	Set<String> keys = new HashSet<String>();
    	for ( int columnIndex = 0; columnIndex < table.getColumnCount() ; columnIndex++) {
    		TableColumn col = table.getColumnModel().getColumn(columnIndex);
    		String key = col.getHeaderValue().toString().trim();
    		keys.add(key);
    		int newVal = col.getPreferredWidth();
    		if (preferredColumnWidths.containsKey(key)) {
    			int val = preferredColumnWidths.get(key);
    			if (val != -1 && val > newVal) {
    				preferredColumnWidths.put(key, newVal);
    			} 
    		} else {
    			preferredColumnWidths.put(key, newVal);
    		}
    	}
    	
       	//removing keys, if column already removed
    	Iterator<String> itr = preferredColumnWidths.keySet().iterator();
        while (itr.hasNext()) {
        	String key = itr.next();
    		if (!keys.contains(key)) {
    			itr.remove();
    		}
    	}
    }

    private void adjustFieldAreaSize(JTable table, DecisionFieldArea firstFieldArea, DecisionFieldArea secondFieldArea) {
        int width = 0;
        int conditionCount = firstFieldArea.getComponentCount();
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < conditionCount; i++) {
        	int j = i; 
        	//The condition count variable is always 1
        	//more than column count.
        	int columnCount = columnModel.getColumnCount();
        	if (columnCount > 0) {
        		//When j equals column count
        		//decrement it so as to get the
        		//last column in the model
        		if (j == columnCount) {
        			j = columnCount - 1;
        		}
        		width += columnModel.getColumn(j).getWidth();
        	} else {
        		width += firstFieldArea.getComponent(0).getWidth();
        	}
        }
        Dimension size = firstFieldArea.getPreferredSize();
        size.width = width;
        firstFieldArea.setPreferredSize(size);
        
        int preferredWidth = firstFieldArea.getPreferredSize().width;
        if (preferredWidth != width && firstFieldArea.getComponentCount() == 0) {
            table.getColumnModel().getColumn(0).setWidth(preferredWidth);
            table.getColumnModel().getColumn(0).setPreferredWidth(preferredWidth);
        }
        firstFieldArea.invalidate();
        firstFieldArea.doLayout();
        firstFieldArea.savePreferredWidth();
        
        width = 0;
        int actionCount = secondFieldArea.getComponentCount();
        for (int i = 0; i < actionCount && i + conditionCount < table.getColumnCount(); i++) {
        	width += table.getColumnModel().getColumn(i + conditionCount).getWidth();
        }
        
        size = secondFieldArea.getPreferredSize();
        size.width = width;
        secondFieldArea.setPreferredSize(size);
        
        preferredWidth = secondFieldArea.getPreferredSize().width;        
        if (preferredWidth != width && secondFieldArea.getComponentCount() == 0) {
            table.getColumnModel().getColumn(table.getColumnCount() - 1).setWidth(preferredWidth);
            table.getColumnModel().getColumn(table.getColumnCount() - 1).setPreferredWidth(preferredWidth);
        }

        secondFieldArea.invalidate();
        secondFieldArea.doLayout();
        secondFieldArea.savePreferredWidth();

    }


    private static void updateTableColumnWidth(JTable table, DecisionFieldArea firstFieldArea, DecisionFieldArea secondFieldArea) {
        Object clientProperty = table.getClientProperty(CLIENT_PROPERTY_COLUMN_MODEL_LISTENER);
        TableColumnModel model = table.getColumnModel();
        if (clientProperty != null && clientProperty instanceof TableColumnModelListener) {
            table.getColumnModel().removeColumnModelListener((TableColumnModelListener) clientProperty);
        }
        try {
            int conditionCount = firstFieldArea.getComponentCount();
            if (conditionCount == 0) {
                if (model.getColumnCount() != 0) {
                    model.getColumn(0).setPreferredWidth(firstFieldArea.getMinimumSize().width);
                }
            }
            else {
                for (int i = 0; i < conditionCount; i++) {
                    int width1 = ((DecisionFieldBox) firstFieldArea.getComponent(i)).getField().getPreferredWidth();
                    int width2 = firstFieldArea.getComponent(i).getPreferredSize().width;
                    if (i >= 0 && i < model.getColumnCount()) {
                        model.getColumn(i).setPreferredWidth(Math.max(width1, width2) + FIELD_GAP * 2);
                    }
                }
                for (int i = 0; i < secondFieldArea.getComponentCount(); i++) {
                    int width1 = ((DecisionFieldBox) secondFieldArea.getComponent(i)).getField().getPreferredWidth();
                    int width2 = secondFieldArea.getComponent(i).getPreferredSize().width;
                    if (i + conditionCount >= 0 && i + conditionCount < model.getColumnCount()) {
                        model.getColumn(i + conditionCount).setPreferredWidth(Math.max(width1, width2) + FIELD_GAP * 2);
                    }
                }
            }
        }
        finally {
            if (clientProperty != null && clientProperty instanceof TableColumnModelListener) {
                table.getColumnModel().addColumnModelListener((TableColumnModelListener) clientProperty);
            }
        }
    }


    /**
     * Checks if the filter icon should be visible on the FieldBox when there is filter for a field.
     *
     * @return true or false.
     */
    public boolean isShowFilterIcon() {
        return _showFilterIcon;
    }

    /**
     * Sets the flag to show the filter icon.
     *
     * @param showFilterIcon true or false.
     */
    public void setShowFilterIcon(boolean showFilterIcon) {
        if (_showFilterIcon != showFilterIcon) {
            _showFilterIcon = showFilterIcon;
            updateFilterIcons();
        }
    }

    /**
     * Sets the filter icon. This icon is displayed on the FieldBox when there is filter if {@link #isShowFilterIcon()}
     * returns true.
     *
     * @param filterIcon a new filter icon.
     */
    public void setFilterIcon(Icon filterIcon) {
        _filterIcon = filterIcon;
        updateFilterIcons();
    }

    private void updateFilterIcons() {
        if (_fieldBoxMap != null) {
            Collection<DecisionFieldBox> boxes = _fieldBoxMap.values();
            for (DecisionFieldBox fieldBox : boxes) {
                fieldBox.updateFilterIcon();
            }
        }
    }
    
    public void updateFilterButtons() {
    	if (_fieldBoxMap != null) {
            Collection<DecisionFieldBox> boxes = _fieldBoxMap.values();
            for (DecisionFieldBox fieldBox : boxes) {
                fieldBox.updateFilterButton();
            }
        } 
    }

    /**
     * Gets the filter icon. This icon is displayed on the FieldBox when there is filter if {@link #isShowFilterIcon()}
     * returns true.
     *
     * @return the filter icon.
     */
    public Icon getFilterIcon() {
        if (_filterIcon == null) {
            _filterIcon = GridIconsFactory.getImageIcon(GridIconsFactory.Table.FILTER);
        }
        return _filterIcon;
    }

    /**
     * Converters the element to string and used in FieldBox. By default, if the value is Filter.ALL, we will convert it
     * to GridResource.getResourceBundle(locale).getString("Filter.all"). If the value is Filter.NULL or null, we will
     * convert it to GridResource.getResourceBundle(locale).getString("Filter.null"). Otherwise, we will use
     * ObjectConverterManager to do the conversion.
     *
     * @param locale  the locale
     * @param value   the value
     * @param clazz   the type
     * @param context the converter context.
     * @return the String converted from the value.
     */
    public String convertElementToString(Locale locale, Object value, Class<?> clazz, ConverterContext context) {
        if (Filter.ALL.equals(value)) {
            return GridResource.getResourceBundle(locale).getString("Filter.all");
        }
        else if (Filter.CUSTOM.equals(value)) {
            return GridResource.getResourceBundle(locale).getString("Filter.custom");
        }
        else if (value == null || Filter.NULL.equals(value)) {
            return GridResource.getResourceBundle(locale).getString("Filter.null");
        }
        else if ("".equals(value)) {
            return " ";
        }
        else {
            String s = ObjectConverterManager.toString(value, clazz == null ? value.getClass() : clazz, context);
            if (s == null || s.length() == 0) {
                s = " ";
            }
            return s;
        }
    }

    /**
     * Stops the cell editing in case the tables in DecisionTablePane is in editing mode.
     */
    public void stopCellEditing() {
    }

    public String getFieldAreaMessage(int areaType) {
        String text;
        switch (areaType) {
            case DecisionConstants.AREA_CONDITION:
                text = getResourceString("FieldArea.condition.message");
                break;
            case DecisionConstants.AREA_ACTION:
                text = getResourceString("FieldArea.action.message");
                break;
            default:
                text = "";
        }
        return text;
    }

    public DecisionFieldArea getConditionFieldsArea() {
        return _conditionFieldsArea;
    }

    public DecisionFieldArea getActionFieldsArea() {
        return _actionFieldsArea;
    }

    public String getFieldAreaName(int areaType) {
        String text;
        switch (areaType) {
            case DecisionConstants.AREA_CONDITION:
                text = getResourceString("DecisionTablePane.conditionArea");
                break;
            case DecisionConstants.AREA_ACTION:
                text = getResourceString("DecisionTablePane.actionArea");
                break;
            default:
                text = "";
        }
        return text;
    }

    public int getFieldAreaType(String name) {
        if (name == null) {
            return AREA_CONDITION;
        }
        else if (name.equals(getFieldAreaName(AREA_CONDITION))) {
            return AREA_CONDITION;
        }
        else if (name.equals(getFieldAreaName(AREA_ACTION))) {
            return DecisionConstants.AREA_ACTION;
        }
        else {
            return DecisionConstants.AREA_CONDITION;
        }
    }

    public boolean isRearrangable() {
        return _rearrangable;
    }

    /**
     * Sets the rearrangable flag. If true (default), user can drag the fields and rearrange it. If false, the field
     * layout can only be setup by API. User will not be able to change it.
     *
     * @param rearrangable true or false.
     */
    public void setRearrangable(boolean rearrangable) {
        _rearrangable = rearrangable;
    }

    /**
     * Gets the popup menu customizer to customize the menu when user right click on the fields.
     *
     * @return the popup menu customizer.
     */
    public PopupMenuCustomizer getPopupMenuCustomizer() {
        return _popupMenuCustomizer;
    }

    /**
     * Sets the popup menu customizer to customize the menu when user right click on the fields.
     *
     * @param popupMenuCustomizer a new PopupMenuCustomizer.
     */
    public void setPopupMenuCustomizer(PopupMenuCustomizer popupMenuCustomizer) {
        _popupMenuCustomizer = popupMenuCustomizer;
    }

    /**
     * Gets the popup menu customizer to customize the menu when user right click on the table.
     *
     * @return the popup menu customizer.
     */
    public PopupMenuCustomizer getTablePopupMenuCustomizer() {
        return _tablePopupMenuCustomizer;
    }

    /**
     * Sets the popup menu customizer to customize the menu when user right click on the row or column header tables.
     *
     * @param popupMenuCustomizer a new PopupMenuCustomizer.
     */
    public void setTablePopupMenuCustomizer(PopupMenuCustomizer popupMenuCustomizer) {
        _tablePopupMenuCustomizer = popupMenuCustomizer;
    }

    /**
     * Gets the popup menu customizer to customize the menu when user right click on the data table.
     *
     * @return the popup menu customizer.
     */
    public PopupMenuCustomizer getRowHeaderPopupMenuCustomizer() {
        return _rowHeaderPopupMenuCustomizer;
    }

    /**
     * Sets the popup menu customizer to customize the menu when user right click on the data table.
     *
     * @param popupMenuCustomizer a new PopupMenuCustomizer.
     */
    public void setRowHeaderPopupMenuCustomizer(PopupMenuCustomizer popupMenuCustomizer) {
        _rowHeaderPopupMenuCustomizer = popupMenuCustomizer;
    }

    static Container getValidParentOf(Component draggingComponent, Component component) {
        Component c = component;
        if (c == null) {
            return null;
        }

        if (isValidComponent(draggingComponent, c)) {
            return (Container) c;
        }

        do {
            c = c.getParent();
            if (c == null) {
                return null;
            }
        }
        while (!isValidComponent(draggingComponent, c));
        return (Container) c;
    }

    private static boolean isValidComponent(Component draggingComponent, Component c) {
        return c instanceof DecisionFieldBox || c instanceof DecisionFieldArea || c instanceof JTable;
    }


    private ResourceBundle getResourceBundle() {
        return DecisionResources.getResourceBundle(getLocale());
    }

    /**
     * Gets the resource string used in DecisionTablePane. Subclass can override it to provide their own strings.
     *
     * @param key the resource key
     * @return the localized string.
     */
    public String getResourceString(String key) {
        try {
            return getResourceBundle().getString(key);
        }
        catch (MissingResourceException e) {
            return null;
        }
    }

    public DecisionTableModel getDecisionTableModel() {
        return _decisionTableModel;
    }

    /**
     * Gets the DecisionDataModel.
     *
     * @return the DecisionDataModel.
     */
    public DecisionDataModel getDecisionDataModel() {
        return _decisionDataModel;
    }

    /**
     * Gets an optional version string.
     *
     * @return version string.
     */
    public String getVersion() {
        return _version;
    }

    /**
     * Sets version string.
     *
     * @param version the version of the saved layout.
     */
    public void setVersion(String version) {
        _version = version;
    }

    protected void showPopup(final Point p) {
        Component component = findComponentAt(p);
        Container target = getValidParentOf(null, component);
        JPopupMenu popupMenu = new JidePopupMenu();
        if (target instanceof DecisionFieldBox) {
            final DecisionFieldBox fieldBox = (DecisionFieldBox) target;
            final ICommandCreationListener<RemoveCommand<Object>, Object> 
    		columnRemovalCommandListener =
    			new DecisionTableColumnRemovalCommandListener(this, null, fieldBox);
            if (isRearrangable()) {
                JMenuItem menuItem;
                //if (fieldBox.getField().isAllowedAsUnassignedField()) {
                    menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.remove")) {
                        private static final long serialVersionUID = -7197710989465411756L;

                        public void actionPerformed(ActionEvent e) {
                        	IEditorInput editor = _decisionTableEditor.getEditorInput();
                        	                        			
                            //removeFieldBox(fieldBox);
                        	IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor;
                        	String project = editorInput.getProjectName();
                        	Table tableEModel = editorInput.getTableEModel();
                        	CommandFacade.getInstance().
                        			executeColumnRemoval(project, 
                        			                     tableEModel, 
                        			                     _decisionDataModel.getTableType(), 
                        			                     columnRemovalCommandListener);
                        }
                    });
                    menuItem.setName("DecisionTablePane.remove");
                    menuItem.setMnemonic(getResourceString("DecisionTablePane.remove.mnemonic").charAt(0));
                    if(isEditorRedOnly()){
                    	menuItem.setEnabled(false);
                    }
                    popupMenu.add(menuItem);
                //}

                JMenu moveMenu = new JMenu(getResourceString("DecisionTablePane.move"));
                if(isEditorRedOnly()){
                	moveMenu.setEnabled(false);
                }
                moveMenu.setName("DecisionTablePane.move");
                moveMenu.setMnemonic(getResourceString("DecisionTablePane.move.mnemonic").charAt(0));

                menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.moveBeginning")) {
                    private static final long serialVersionUID = -5574451744126677463L;

                    public void actionPerformed(ActionEvent e) {
                        moveFieldBoxToBeginning(fieldBox);
                    }
                });
                moveMenu.setName("DecisionTablePane.moveBeginning");
                menuItem.setMnemonic(getResourceString("DecisionTablePane.moveBeginning.mnemonic").charAt(0));
                menuItem.setEnabled(!fieldBox.isFirst());
                moveMenu.add(menuItem);

                menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.moveLeft")) {
                    private static final long serialVersionUID = -2297645520537207300L;

                    public void actionPerformed(ActionEvent e) {
                        moveFieldBox(fieldBox, true);
                    }
                });
                moveMenu.setName("DecisionTablePane.moveLeft");
                menuItem.setMnemonic(getResourceString("DecisionTablePane.moveLeft.mnemonic").charAt(0));
                menuItem.setEnabled(!fieldBox.isFirst());
                moveMenu.add(menuItem);

                menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.moveRight")) {
                    private static final long serialVersionUID = 142467024186108231L;

                    public void actionPerformed(ActionEvent e) {
                        moveFieldBox(fieldBox, false);
                    }
                });
                moveMenu.setName("DecisionTablePane.moveRight");
                menuItem.setMnemonic(getResourceString("DecisionTablePane.moveRight.mnemonic").charAt(0));
                menuItem.setEnabled(!fieldBox.isLast());
                moveMenu.add(menuItem);

                menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.moveEnd")) {
                    private static final long serialVersionUID = 490229878402108571L;

                    public void actionPerformed(ActionEvent e) {
                        moveFieldBoxToEnd(fieldBox);
                    }
                });
                moveMenu.setName("DecisionTablePane.moveEnd");
                menuItem.setMnemonic(getResourceString("DecisionTablePane.moveEnd.mnemonic").charAt(0));
                menuItem.setEnabled(!fieldBox.isLast());
                moveMenu.add(menuItem);

                popupMenu.add(moveMenu);

            }

            if (fieldBox.getField().isCustomizable()) {
                if (popupMenu.getComponentCount() > 0) popupMenu.addSeparator();

                JMenuItem menuItem = new JMenuItem(new AbstractAction(getResourceString("DecisionTablePane.fieldSettings")) {
                    private static final long serialVersionUID = 2896069689906651621L;

                    public void actionPerformed(ActionEvent e) {
                        showFieldSettingsDialog(fieldBox);
                    }
                });
                menuItem.setName("DecisionTablePane.fieldSettings");
                menuItem.setMnemonic(getResourceString("DecisionTablePane.fieldSettings.mnemonic").charAt(0));
                if(isEditorRedOnly()){
                	menuItem.setEnabled(false);
                }
                popupMenu.add(menuItem);
            }
        }

        PopupMenuCustomizer popupMenuCustomizer = getPopupMenuCustomizer();
        if (popupMenuCustomizer != null) {
            popupMenuCustomizer.customize(popupMenu, target);
        }

        if (popupMenu.getComponentCount() > 0) {
            popupMenu.show(this, p.x, p.y);
        }
    }
    
    private boolean containsActions() {
    	DecisionFieldArea actionsFieldArea = this.getActionFieldsArea();
    	Component[] components = actionsFieldArea.getFieldBoxes();
    	for (Component component : components) {
    		if (component instanceof DecisionFieldBox) {
    			DecisionFieldBox decisionFieldBox = (DecisionFieldBox)component;
    			DecisionField decisionField = decisionFieldBox.getField();
    			
    			int areaType = decisionField.getAreaType();
    			if (areaType == AREA_ACTION) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private int getConditionCount() {
    	return getConditionFieldsArea().getFieldBoxes().length;
    }

    /**
     * Removes the field box.
     *
     * @param fieldBox the field box to be removed.
     */
    public int removeFieldBox(DecisionFieldBox fieldBox) {
        // if (fieldBox.getField().isAllowedAsUnassignedField()) {
    	
		_decisionDataModel.setAdjusting(true);
		DecisionField decisionField = fieldBox.getField();
		int removalIndex = -1;
		try {
			int areaType = fieldBox.getField().getAreaType();

			// fieldBox.getField().setAreaType(AREA_NOT_ASSIGNED);
			switch (areaType) {
			case AREA_CONDITION:
				if (getConditionCount() == 1) {
			    	//Check if it has any actions
			    	if (containsActions()) {
			    		Display.getDefault().asyncExec(new Runnable() {
			    			public void run() {
					    		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					    		MessageDialog.openError(window.getShell(), Messages.getString("MESSAGE_TITLE_ERROR"), Messages.getString("ERROR_REMOVE_CONDITION"));
			    			}
			    		});
			    		return -1;
			    	}
		    	}
				removalIndex = _decisionDataModel
						.getDecisionFieldIndex(decisionField);
				_decisionDataModel.removeConditionField(decisionField);
				// conditionFieldsUpdated();
				break;
			case AREA_ACTION:
				removalIndex = _decisionDataModel
						.getDecisionFieldIndex(decisionField);
				_decisionDataModel.removeActionField(decisionField);
				// actionFieldsUpdated();
				break;
			}
			// Reset to the original area type
			decisionField.setAreaType(areaType);
		} finally {
			_decisionDataModel.setAdjusting(false);
		}
		return removalIndex;
		// }
    }

    /**
     * Moves the field box
     *
     * @param fieldBox the field box to be moved.
     * @param before   true to move it before its current position. False to move after its current position.
     */
    protected void moveFieldBox(DecisionFieldBox fieldBox, boolean before) {
        getDecisionDataModel().setAdjusting(true);
        try {
            fieldBox.getField().setAreaIndex(before ? fieldBox.getField().getAreaIndex() - 3 : fieldBox.getField().getAreaIndex() + 3);
            moveFieldsUpdated(fieldBox.getField());
        }
        finally {
            getDecisionDataModel().setAdjusting(false);
        }
    }

    protected void moveFieldBoxToBeginning(DecisionFieldBox fieldBox) {
        getDecisionDataModel().setAdjusting(true);
        try {
            fieldBox.getField().setAreaIndex(0);
            moveFieldsUpdated(fieldBox.getField());
        }
        finally {
            getDecisionDataModel().setAdjusting(false);
        }
    }

    protected void moveFieldBoxToEnd(DecisionFieldBox fieldBox) {
        getDecisionDataModel().setAdjusting(true);
        try {
            fieldBox.getField().setAreaIndex(Integer.MAX_VALUE);
            moveFieldsUpdated(fieldBox.getField());
        }
        finally {
            getDecisionDataModel().setAdjusting(false);
        }
    }

    protected void addFieldBox(DecisionFieldBox fieldBox, int areaType) {
        try {
            List<DecisionField> fields = areaType == DecisionConstants.AREA_CONDITION ? getDecisionDataModel().getConditionFields() : getDecisionDataModel().getActionFields();
            int maxAreaIndex = -1;
            for (DecisionField field : fields) {
                if (field.getAreaType() == areaType && field.getAreaIndex() > maxAreaIndex) {
                    maxAreaIndex = field.getAreaIndex();
                }
            }
            fieldBox.getField().setAreaType(areaType);
            maxAreaIndex++;
            if (maxAreaIndex % 2 == 0) {
                fieldBox.getField().setAreaIndex(maxAreaIndex);
            }
            else {
                fieldBox.getField().setAreaIndex(Integer.MAX_VALUE);
            }
        }
        catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    MessageFormat.format(getResourceString("DecisionTablePane.incompatibleType.message"), fieldBox.getField().getName()),
                    getResourceString("DecisionTablePane.incompatibleType.title"), JOptionPane.OK_OPTION);
        }
        fieldsUpdated();
    }

    protected void moveFieldBox(DecisionFieldBox fieldBox, DecisionFieldBox destFieldBox, boolean before) {
        try {
            int newAreaType = destFieldBox.getField().getAreaType();
            int oldAreaType = fieldBox.getField().getAreaType();
            fieldBox.getField().setAreaType(newAreaType);
            fieldBox.getField().setAreaIndex(before ? destFieldBox.getField().getAreaIndex() - 1 : destFieldBox.getField().getAreaIndex() + 1);
            if (newAreaType == oldAreaType || oldAreaType == AREA_NOT_ASSIGNED || newAreaType == AREA_NOT_ASSIGNED) {
                if (newAreaType == AREA_CONDITION) {
                    conditionFieldsUpdated();
                }
                else if (newAreaType == AREA_ACTION) {
                    actionFieldsUpdated();
                }
            }
            else {
                fieldsUpdated();
            }
        }
        catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    MessageFormat.format(getResourceString("DecisionTablePane.incompatibleType.message"), fieldBox.getField().getName()),
                    getResourceString("DecisionTablePane.incompatibleType.title"), JOptionPane.OK_OPTION);
            fieldsUpdated();
        }
    }

    public void fieldsUpdated() {
        getDecisionDataModel().updateConditionFields();
        getDecisionDataModel().updateActionFields();
        String pref = TableUtils.getTablePreferenceByName(_pane);
        fillConditionFieldArea();
        fillActionFieldArea();
        getDecisionTableModel().fireTableStructureChanged();
        synchronizeFieldAreaWithColumnWidth(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        TableUtils.setTableColumnWidthByName(_pane, pref);
    }

    public void moveFieldsUpdated(DecisionField decisionField) {
    	IDecisionTableEditorInput decisionTableEditorInput = (IDecisionTableEditorInput)_decisionTableEditor.getEditorInput();
    	String projectName = decisionTableEditorInput.getProjectName();
    	Table tableEModel = decisionTableEditorInput.getTableEModel();
    	DecisionTableColumnMoveCommandListener listener = 
    		new DecisionTableColumnMoveCommandListener(projectName, tableEModel, decisionField, _decisionDataModel);
    	CommandFacade.getInstance().executeColumnMove(projectName, tableEModel, _decisionDataModel.getTableType(), listener);
        String pref = TableUtils.getTablePreferenceByName(_pane);
        fillConditionFieldArea();
        fillActionFieldArea();
        getDecisionTableModel().fireTableStructureChanged();
        synchronizeFieldAreaWithColumnWidth(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        TableUtils.setTableColumnWidthByName(_pane, pref);
    }
    
    public void conditionFieldsUpdated() {
    	_decisionDataModel.updateConditionFields();
        String pref = TableUtils.getTablePreferenceByName(_pane);
        fillConditionFieldArea();
        //Enable buttons
        TableTypes tableType = _decisionDataModel.getTableType();
        switch (tableType) {
        
        case DECISION_TABLE :
        	_decisionTableEditor.getDecisionTableDesignViewer().updateToolBar();
        	break;
        case EXCEPTION_TABLE :
        	_decisionTableEditor.getDecisionTableDesignViewer().updateExpToolBar();
        	break;
        }
        _decisionTableModel.fireTableStructureChanged();
        _pane.getMainTable().createDefaultColumnsFromModel();
        synchronizeFieldAreaWithColumnWidth(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        TableUtils.setTableColumnWidthByName(_pane, pref);
    }

    public void actionFieldsUpdated() {
    	_decisionDataModel.updateActionFields();
        String pref = TableUtils.getTablePreferenceByName(_pane);
        fillActionFieldArea();
      //Enable buttons
        TableTypes tableType = _decisionDataModel.getTableType();
        switch (tableType) {
        
        case DECISION_TABLE :
        	_decisionTableEditor.getDecisionTableDesignViewer().updateToolBar();
        	break;
        case EXCEPTION_TABLE :
        	_decisionTableEditor.getDecisionTableDesignViewer().updateExpToolBar();
        	break;
        }
        _decisionTableModel.fireTableStructureChanged();
        _pane.getMainTable().createDefaultColumnsFromModel();
        synchronizeFieldAreaWithColumnWidth(_pane.getMainTable(), _conditionFieldsArea, _actionFieldsArea);
        TableUtils.setTableColumnWidthByName(_pane, pref);
    }

    /**
     * Checks if the field box will be hidden when user drags it and drop outside the field areas. It is true by
     * default.
     *
     * @return true or false.
     */
    public boolean isHideFieldOnDraggingOut() {
        return _hideFieldOnDraggingOut;
    }

    /**
     * Sets the flag if dragging a field box outside the field area will hide the field. It is true by default. If you
     * set it to false, your user can still hide it by right click to show the popup menu which has a "Hide" menu item
     * or drag it to the unassigned field area to hide it.
     *
     * @param hideFieldOnDraggingOut true or false.
     */
    public void setHideFieldOnDraggingOut(boolean hideFieldOnDraggingOut) {
        _hideFieldOnDraggingOut = hideFieldOnDraggingOut;
    }

    /**
     * Get the cursor to be displayed when the field is going to be removed from current area.
     *
     * @return the cursor.
     */
    public Cursor getDragRemoveCursor() {
        return _dragRemoveCursor;
    }

    /**
     * Set the cursor to be displayed when the field is going to be removed from current area.
     *
     * @param dragRemoveCursor the cursor.
     */
    public void setDragRemoveCursor(Cursor dragRemoveCursor) {
        _dragRemoveCursor = dragRemoveCursor;
    }

    /**
     * Get the cursor to be displayed when the field is prohibited to be dropped into current area.
     * <p/>
     *
     * @return the cursor.
     *
     * @see com.jidesoft.decision.DecisionFieldBoxInputListener#isDropAllowed(java.awt.Component, java.awt.Rectangle,
     *      int, java.awt.Container)
     */
    public Cursor getDragNoDropCursor() {
        return _dragNoDropCursor;
    }

    /**
     * Set the cursor to be displayed when the field is prohibited to be dropped into current area.
     * <p/>
     *
     * @param dragNoDropCursor the cursor. //     * @see com.jidesoft.decision.FieldBoxInputListener#isDropAllowed(java.awt.Component,
     *                         java.awt.Rectangle, int, //     *      java.awt.Container)
     */
    public void setDragNoDropCursor(Cursor dragNoDropCursor) {
        _dragNoDropCursor = dragNoDropCursor;
    }

    public static String getAreaName(Locale locale, int areaType) {
        switch (areaType) {
            case AREA_CONDITION:
                return DecisionResources.getResourceBundle(locale).getString("AreaType.condition");
            case AREA_ACTION:
                return DecisionResources.getResourceBundle(locale).getString("AreaType.action");
        }
        return "";
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (_listener != null && _listener.isDragging()) {
            _listener.drawItem(g);
        }
    }

    public TableScrollPane getTableScrollPane() {
        return _pane;
    }

    public void autoResizeAllColumns() {
        autoResizeAllColumns(false);
    }

    public void autoResizeAllColumns(boolean visibleAreaOnly) {
        TableUtils.autoResizeAllColumns(_pane.getRowHeaderTable(), null, false, visibleAreaOnly);
        TableUtils.autoResizeAllColumns(_pane.getMainTable(), null, false, visibleAreaOnly);
    }


    /**
     * Shows the field setting dialog.
     *
     * @param fieldBox the field box for which the field settings dialog is for.
     */
    protected void showFieldSettingsDialog(DecisionFieldBox fieldBox) {
        Window window = JideSwingUtilities.getWindowForComponent(this);
        FieldSettingsDialog dialog;
        if (window instanceof Frame) {
            dialog = new FieldSettingsDialog((Frame) window, this, fieldBox, getResourceString("FieldSettings.title"));
        }
        else {
            dialog = new FieldSettingsDialog((Dialog) window, this, fieldBox, getResourceString("FieldSettings.title"));
        }
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        dialog.getFieldSettingsPanel().setField(fieldBox.getField());
        dialog.getFieldSettingsPanel().loadData();

        dialog.setVisible(true);
        if (dialog.getDialogResult() == FieldSettingsDialog.RESULT_AFFIRMED) {
            dialog.getFieldSettingsPanel().saveData();
            fieldsUpdated();
        }
        else if (dialog.getDialogResult() == FieldSettingsDialog.RESULT_HIDE) {
            removeFieldBox(fieldBox);
        }
    }

    /**
     * Creates the field settings panel. This is the panel that will be used as the content pane of the field setting
     * dialog. Subclass can override this method to create your own field settings panel.
     * @param decisionFieldBox -> The field box used to create the panel
     * @return the field settings panel.
     */
    protected FieldSettingsPanel createFieldSettingsPanel(DecisionFieldBox decisionFieldBox) {
        return new FieldSettingsPanel(this, decisionFieldBox);
    }

    /**
     * Creates the button panel in field settings dialog. This is the panel that will be used as the button pane of the
     * field setting dialog. Subclass can override this method to create your own button panel or change the font of the
     * buttons.
     * <p/>
     * By default, we have three buttons: ok, cancel and hide. You can retreive them by getName(). Their names are:
     * <p/>
     * <code>FIELD_SETTING_DIALOG_OK_BUTTON</code> <code>FIELD_SETTING_DIALOG_CANCEL_BUTTON</code>
     * <code>FIELD_SETTING_DIALOG_HIDE_BUTTON</code>
     *
     * @param dialog the FieldSettingDialog instance
     * @return the button panel of the field settings dialog.
     */
    protected ButtonPanel createFieldSettingsDialogButtonPanel(final StandardDialog dialog) {
        final ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.TOP);
        JButton okButton = new JButton(new AbstractAction(UIDefaultsLookup.getString(FIELD_SETTING_DIALOG_OK_BUTTON)) {
            private static final long serialVersionUID = 8834800451061035928L;

            public void actionPerformed(ActionEvent e) {
                dialog.setDialogResult(FieldSettingsDialog.RESULT_AFFIRMED);
                dialog.setVisible(false);
            }
        });
        okButton.setName(FIELD_SETTING_DIALOG_OK_BUTTON);
        buttonPanel.addButton(okButton, ButtonPanel.AFFIRMATIVE_BUTTON);

        JButton cancelButton = new JButton(new AbstractAction(UIDefaultsLookup.getString(FIELD_SETTING_DIALOG_CANCEL_BUTTON)) {
            private static final long serialVersionUID = -4067081832260858892L;

            public void actionPerformed(ActionEvent e) {
                dialog.setDialogResult(FieldSettingsDialog.RESULT_CANCELLED);
                dialog.setVisible(false);
            }
        });
        cancelButton.setName(FIELD_SETTING_DIALOG_CANCEL_BUTTON);
        buttonPanel.addButton(cancelButton, ButtonPanel.CANCEL_BUTTON);

        JButton hideButton = new JButton(new AbstractAction(getResourceString(FIELD_SETTING_DIALOG_REMOVE_BUTTON)) {
            private static final long serialVersionUID = 3273783807560168556L;

            public void actionPerformed(ActionEvent e) {
                dialog.setDialogResult(FieldSettingsDialog.RESULT_HIDE);
                dialog.setVisible(false);
            }
        });
        hideButton.setName(FIELD_SETTING_DIALOG_REMOVE_BUTTON);
        hideButton.setMnemonic(getResourceString("DecisionTablePane.remove.mnemonic").charAt(0));
        //buttonPanel.addButton(hideButton, ButtonPanel.OTHER_BUTTON);

//        JButton advancedButton = new JButton(new AbstractAction("Advanced ...") {
//            public void actionPerformed(ActionEvent e) {
//                FieldAdvanceSettingsDialog dialog = new FieldAdvanceSettingsDialog(JOptionPane.getFrameForComponent(buttonPanel), "PivotTable Field Advanced Options");
//                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//                dialog.pack();
//                dialog.setLocationRelativeTo(buttonPanel);
//                dialog.setVisible(true);
//            }
//        });
//        buttonPanel.add(advancedButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.setDefaultAction(okButton.getAction());
        dialog.setDefaultCancelAction(cancelButton.getAction());
        getRootPane().setDefaultButton(okButton);
        return buttonPanel;
    }

    private class RowHeaderCellRenderer extends JLabel implements TableCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Insets getInsets() {
            return new Insets(0, 0, 0, 0);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setHorizontalAlignment(SwingConstants.RIGHT);
            setText(value.toString());
            setOpaque(true);
            Color color = UIDefaultsLookup.getColor("control");
            setBackground(isSelected ? ColorUtils.getDerivedColor(color, 0.30f) : ColorUtils.getDerivedColor(color, 0.45f));
            setForeground(UIDefaultsLookup.getColor("controlText"));
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
    public interface PopupMenuCustomizer {
        void customize(JPopupMenu menu, Component target);
    }

    public interface TablePopupMenuCustomizer {
        void customize(JPopupMenu menu, Component target, int rowIndex, int columnIndex);
    }

    public Map<String, Integer> getPreferredColumnWidths() {
		return preferredColumnWidths;
	}


}