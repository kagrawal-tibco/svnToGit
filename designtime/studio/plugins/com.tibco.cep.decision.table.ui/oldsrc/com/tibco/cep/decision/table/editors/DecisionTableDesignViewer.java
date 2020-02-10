package com.tibco.cep.decision.table.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.CellEditor;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IFindReplaceTarget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.texteditor.FindReplaceAction;

import sun.reflect.Reflection;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.ObjectConverterManager;
import com.jidesoft.decision.DecisionConstants;
import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.DecisionFieldArea;
import com.jidesoft.decision.DecisionFieldBox;
import com.jidesoft.decision.DecisionRule;
import com.jidesoft.decision.DecisionTablePane;
import com.jidesoft.decision.RuleVariableCellRenderer;
import com.jidesoft.decision.RuleVariableConverter;
import com.jidesoft.decision.SubMenuButtonPanel;
import com.jidesoft.decision.UserObject;
import com.jidesoft.decision.cell.editors.DefaultRuleVariableCellEditor;
import com.jidesoft.decision.cell.editors.custom.DefaultMultipleEnumCellEditor;
import com.jidesoft.grid.CellEditorFactory;
import com.jidesoft.grid.CellEditorManager;
import com.jidesoft.grid.CellRendererManager;
import com.jidesoft.grid.CellSpanTable;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.JideTableSearchable;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.grid.TableUtils;
import com.jidesoft.pane.BookmarkPane;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.popup.JidePopup;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideComboBox;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.Resizable;
import com.jidesoft.swing.ResizablePanel;
import com.jidesoft.swing.Searchable;
import com.jidesoft.swing.StyledLabelBuilder;
import com.jidesoft.swt.JideSwtUtilities;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.DuplicateCommand;
import com.tibco.cep.decision.table.command.impl.ModifyCommandExpression;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.listeners.DecisionTableAddRowCommandListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableColumnAdditionCommandListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableRowDuplicationCommandListener;
import com.tibco.cep.decision.table.command.listeners.DecisionTableRowRemovalCommandListener;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.editors.listener.DTDeclTableModelListener;
import com.tibco.cep.decision.table.editors.listener.DecisionTableCellEditorListener;
import com.tibco.cep.decision.table.editors.listener.DecisionTableModelSelectionListener;
import com.tibco.cep.decision.table.editors.listener.DefaultCellEditCompletionListener;
import com.tibco.cep.decision.table.editors.listener.TextDropTargetListener;
import com.tibco.cep.decision.table.model.DecisionTableModelManager;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelFactoryImpl;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decision.table.utils.ImageIconsFactory;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.overview.CommonOverview;


@SuppressWarnings("serial")
public class DecisionTableDesignViewer {

	private ManagedForm managedForm;
	private Composite swtAwtComponent;
	private DecisionTableModelManager decisionTableModelManager;
	private JTable declTable;
	
	private DecisionTableEditor editor;
	
	private TableModel declTableModel;
		
	protected DecisionTablePane _decisionTablePane;
	
	protected DecisionDataModel _decisionDataModel;
	protected DecisionDataModel _exceptionDataModel;
	
	private BookmarkPane bookMarkPane;
	private boolean setDeclOpen = true;
	private boolean setDecisionOpen = true;
	private boolean setExceptionOpen = false;
	
	private JPanel topPanel;
	private JideButton addButton;
	private JideButton duplicateButton;
	private JideButton removeButton;
	private JideButton customButton;
	
	private JideButton toggleMergeButton = null;
	private JideButton toggleTextAliasButton;
	
	/**
	 * Flag indicating whether the toggle text button is on/off.
	 */
	private boolean toggleTextAliasFlag;
	// private JideButton toggleGraphicsAliasButton;
	private JideButton toggleFitContentButton;
	private JideButton findButton;
	private JideComboBox jideComboBox;
	private JLabel label = null;
	private JTextField functionTextField = null;

	private DecisionTablePane _exceptionTablePane;
	private JPanel expTopPanel;
	private JideButton expAddButton;
	private JideButton expDuplicateButton;
	private JideButton expRemoveButton;
	private JideButton expCustomButton;
//	private JideButton expToggleVerticalButton;
//	private JideButton expToggleHorizontalButton;
	private JideButton expToggleMergeButton;
	private JideButton expToggleTextAliasButton;
	
	/**
	 * Flag indicating whether the toggle text button is on/off.
	 */
	private boolean expToggleTextAliasFlag;
	// private JideButton expToggleGraphicsAliasButton;
	private JideButton expToggleFitContentButton;
	private JideButton expFindButton;
	private JLabel expLabel = null;
	private JTextField expFunctionTextField = null;
	
	private ICommandCreationListener<AddCommand<TableRule>, TableRule> rowAdditionListener;
	
	private ICommandCreationListener<DuplicateCommand, TableRule> rowDuplicationListener;
	
	private ICommandCreationListener<RemoveCommand<TableRule>, TableRule> rowRemovalListener; 

	private StringBuffer typeBuffer = null;
	private int dtSelectedRow = -1;
	private int dtSelectedColumn= -1;
	private static String[] properties = new String[] { "IN", "OUT", "BOTH", "EXCEPTION" };
	private IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();

	private boolean invalidPart;
	
	/**
	 * DnD Handler for DTable Conditions
	 */
	private DecisionTablePaneDropHandler decisionTableConditionsPaneDropHandler;
	
	/**
	 * DnD Handler for DTable Actions
	 */
	private DecisionTablePaneDropHandler decisionTableActionsPaneDropHandler;
	
	/**
	 * DnD Handler for ETable Conditions
	 */
	private DecisionTablePaneDropHandler exceptionTableConditionsPaneDropHandler;
	
	/**
	 * DnD Handler for ETable Actions
	 */
	private DecisionTablePaneDropHandler exceptionTableActionsPaneDropHandler;
	
		
	public DecisionTableDesignViewer(DecisionTableEditor editor,
			                         DecisionTableModelManager decisionTableModelManager, 
			                         boolean invalidPart) {

		this.editor = editor;
		this.decisionTableModelManager = decisionTableModelManager;
		this.invalidPart = invalidPart;
	}

	public Control getControl() {
		return getForm();
	}

	public ScrolledForm getForm() {
		return managedForm.getForm();
	}

	
	
	public void createPartControl(Composite container) {
		managedForm = new ManagedForm(container);
		if(!invalidPart) {
			if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS)) {
				TableWrapLayout layout = new TableWrapLayout();
				layout.numColumns = 1;
				getForm().getBody().setLayout(layout);
				ScrolledForm form = getForm();
				FormToolkit toolkit = managedForm.getToolkit();
				createDeclarationSection(form, toolkit);
				// TODO: We need this only for DT Rules
				// createSituationSection(form, toolkit);
				createDecisionSection(form, toolkit);
				createExceptionSection(form, toolkit);

			} else {
				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				getForm().getBody().setLayout(layout);
				ScrolledForm form = getForm();
				FormToolkit toolkit = managedForm.getToolkit();
				createFormParts(form, toolkit);
			}
			this.showOverview();
			//Register cell editors once only.
			initConvertersEditors(decisionTableModelManager.getTabelEModel());
		}
		managedForm.initialize();
	}

	private Section declSection;

	private void createDeclarationSection(final ScrolledForm form, final FormToolkit toolkit) {
		declSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR
				| Section.TWISTIE | Section.EXPANDED);
		declSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		declSection.setToggleColor(toolkit.getColors().getColor(
				IFormColors.SEPARATOR));
		declSection.setText(Messages.getString("DT_Declaration_Tab"));

		declSection.setFont(new Font(Display.getCurrent(), new FontData(
				"Tahoma", 8, SWT.BOLD)));
		Composite sectionClient = toolkit.createComposite(declSection,
				SWT.EMBEDDED);
		declSection.setClient(sectionClient);
		declSection.setExpanded(setDeclOpen);
		
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 80;

		declSection.setLayoutData(td);

		JideSwtUtilities.initSwing();
		Container cpanel = JideSwtUtilities.getSwingContainer(sectionClient);
		cpanel.setLayout(new BorderLayout());

		JPanel panel = new JPanel(new BorderLayout() {
			public void layoutContainer(Container target) {
				super.layoutContainer(target);
			}
		});
		declTableModel = null;
		if (decisionTableModelManager != null) {
			declTableModel = decisionTableModelManager.getDeclarationTableModel();
		}
		declTable = new JTable(declTableModel) {
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class<?> c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row, column);
						if (o != null) {
							c = getValueAt(row, column).getClass();
						}
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}

			public TableCellEditor getCellEditor(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellEditor editor = tableColumn.getCellEditor();
				if (editor == null) {
					Class<?> c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row, column);
						if (o != null) {
							c = getValueAt(row, column).getClass();
						}
					}
					editor = getDefaultEditor(c);
				}
				return editor;
			}

			public Dimension getPreferredScrollableViewportSize() {
				Dimension preferredScrollableViewportSize = super
						.getPreferredScrollableViewportSize();
				preferredScrollableViewportSize.height = Math.max(3, Math.min(
						getRowCount(), 3))
						* getRowHeight();
				return preferredScrollableViewportSize;
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		declTable.setRowHeight(18);
		declTableModelListener = new DTDeclTableModelListener(editor);
		declTable.getModel().addTableModelListener(declTableModelListener);
		final JComboBox comb = new JComboBox(properties);
		comb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					
				}
			}
		});
		declTable.getColumnModel().getColumn(2).setCellEditor(new PropertyCellEditor(comb));
		declTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		declTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		declTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		declTable.setGridColor(new java.awt.Color(236, 233, 216));
		JScrollPane scrollPane = new JScrollPane(declTable);
		scrollPane.getViewport().setBackground(Color.white);
		panel.setBackground(Color.white);

		
		final DefaultOverlayable overlayTable = new DefaultOverlayable(scrollPane);
		declTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				overlayTable.setOverlayVisible(declTable.getModel().getRowCount() == 0);
			}
		});
		overlayTable.addOverlayComponent(StyledLabelBuilder
				.createStyledLabel("{" + Messages.getString("DT_Declaration_instruction")
						+ ":f:gray}"));
		overlayTable.setOverlayVisible(declTable.getModel().getRowCount() == 0);

		declSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState()) {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 160;
					declSection.setLayoutData(td);
				} else {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 20;
					declSection.setLayoutData(td);
				}
				form.reflow(true);
			}
		});
		panel.add(overlayTable);
		cpanel.add(panel);

		toolkit.paintBordersFor(sectionClient);
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean isToggleTextAliasFlag() {
		return toggleTextAliasFlag;
	}

	private Section decisionSection;
	
	/**
	 * Code for creating decision table section.
	 * @param form
	 * @param toolkit
	 */
	private void createDecisionSection(final ScrolledForm form, final FormToolkit toolkit) {
		decisionSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		decisionSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		decisionSection.setToggleColor(toolkit.getColors().getColor(
				IFormColors.SEPARATOR));

		decisionSection.setText(Messages.getString("DT_Decision_Tab"));
		decisionSection.setFont(new Font(Display.getCurrent(), new FontData(
				"Tahoma", 8, SWT.BOLD)));
		final Composite sectionClient = toolkit.createComposite(
				decisionSection, SWT.EMBEDDED);
		decisionSection.setClient(sectionClient);
		decisionSection.setExpanded(true);
		
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 400;

		decisionSection.setLayoutData(td);
		decisionSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState()) {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 400;
					decisionSection.setLayoutData(td);
				} else {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 20;
					decisionSection.setLayoutData(td);
				}
				form.reflow(true);
			}
		});
		
		JideSwtUtilities.initSwing();
		Container cpanel = JideSwtUtilities.getSwingContainer(sectionClient);
		cpanel.setLayout(new BorderLayout());
		cpanel.add(createDecisionTable());

		toolkit.paintBordersFor(sectionClient);
	}

	private Section exceptionSection;
	private DTDeclTableModelListener declTableModelListener;
	private RuleVariableCellRenderer cellRenderer;
	
	/**
	 * Code for creating exception table section.
	 * @param form
	 * @param toolkit
	 */
	private void createExceptionSection(final ScrolledForm form, final FormToolkit toolkit) {

		exceptionSection = toolkit.createSection(form.getBody(),
				Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		exceptionSection.setActiveToggleColor(toolkit.getHyperlinkGroup()
				.getActiveForeground());
		exceptionSection.setToggleColor(toolkit.getColors().getColor(
				IFormColors.SEPARATOR));

		exceptionSection.setText(Messages.getString("DT_Exception_Tab"));
		exceptionSection.setFont(new Font(Display.getCurrent(), new FontData(
				"Tahoma", 8, SWT.BOLD)));
		final Composite sectionClient = toolkit.createComposite(
				exceptionSection, SWT.EMBEDDED);
		exceptionSection.setClient(sectionClient);
		exceptionSection.setExpanded(setExceptionOpen);
		TableWrapLayout layout = new TableWrapLayout();
		layout.numColumns = 1;

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.grabHorizontal = true;
		td.heightHint = 20;

		exceptionSection.setLayoutData(td);
		exceptionSection.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent e) {
				if (e.getState()) {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 400;
					exceptionSection.setLayoutData(td);
				} else {
					TableWrapData td = new TableWrapData(TableWrapData.FILL);
					td.grabHorizontal = true;
					td.heightHint = 20;
					exceptionSection.setLayoutData(td);
				}
				form.reflow(true);
			}
		});

		JideSwtUtilities.initSwing();
		Container cpanel = JideSwtUtilities.getSwingContainer(sectionClient);
		cpanel.setLayout(new BorderLayout());
		cpanel.add(createExceptionTable());
		toolkit.paintBordersFor(sectionClient);
	}

	// Without Sections

	private void createFormParts(final ScrolledForm form, final FormToolkit toolkit) {
		Composite sectionClient = toolkit.createComposite(form.getBody(),
				SWT.EMBEDDED);

		GridLayout elayout = new GridLayout();
		elayout.numColumns = 1;
		elayout.makeColumnsEqualWidth = false;
		sectionClient.setLayout(elayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 70;
		gd.widthHint = 200;
		sectionClient.setLayoutData(gd);

		JideSwtUtilities.initSwing();
		Container panel = JideSwtUtilities.getSwingContainer(sectionClient);
		panel.setLayout(new BorderLayout());
		bookMarkPane = new ScrollableBookmarkPane();
		bookMarkPane.setOpaque(true);
		bookMarkPane.setBackground(Color.WHITE);

		insertDeclarationsTab(bookMarkPane, Messages.getString("DT_Declaration_Tab"));
		// TODO:
		// Needed for DT Rules
		// insertSituationTab(bookMarkPane, Messages.DT_Situation_Tab);
		insertDecisionTableTab(bookMarkPane, Messages.getString("DT_Decision_Tab"));
		insertExceptionTableTab(bookMarkPane, Messages.getString("DT_Exception_Tab"));

		// this.showOverview();

		panel.add(new JScrollPane(bookMarkPane), BorderLayout.CENTER);
	}

	private void insertDeclarationsTab(BookmarkPane bookMarkPane, String text) {
		JPanel panel = new JPanel(new BorderLayout() {
			public void layoutContainer(Container target) {
				super.layoutContainer(target);
			}
		});

		panel.setBorder(BorderFactory.createCompoundBorder(
				new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup
						.getColor("controlShadow"), 1, PartialSide.NORTH),
						Messages.getString("DT_Declaration_Tab"), JideTitledBorder.RIGHT,
						JideTitledBorder.CENTER), BorderFactory
						.createEmptyBorder(2, 2, 2, 2)));

		declTableModel = null;
		if (decisionTableModelManager != null) {
			declTableModel = decisionTableModelManager.getDeclarationTableModel();
		}
		declTable = new JTable(declTableModel) {
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellRenderer renderer = tableColumn.getCellRenderer();
				if (renderer == null) {
					Class<?> c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row, column);
						if (o != null) {
							c = getValueAt(row, column).getClass();
						}
					}
					renderer = getDefaultRenderer(c);
				}
				return renderer;
			}

			public TableCellEditor getCellEditor(int row, int column) {
				TableColumn tableColumn = getColumnModel().getColumn(column);
				TableCellEditor editor = tableColumn.getCellEditor();
				if (editor == null) {
					Class<?> c = getColumnClass(column);
					if (c.equals(Object.class)) {
						Object o = getValueAt(row, column);
						if (o != null) {
							c = getValueAt(row, column).getClass();
						}
					}
					editor = getDefaultEditor(c);
				}
				return editor;
			}

			public Dimension getPreferredScrollableViewportSize() {
				Dimension preferredScrollableViewportSize = super
						.getPreferredScrollableViewportSize();
				preferredScrollableViewportSize.height = Math.max(3, Math.min(
						getRowCount(), 3))
						* getRowHeight();
				return preferredScrollableViewportSize;
			}

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		declTable.setRowHeight(18);
		declTableModelListener = new DTDeclTableModelListener(editor);
		declTable.getModel().addTableModelListener(declTableModelListener);
		final JComboBox comb = new JComboBox(properties);
		comb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					
				}
			}
		});
		declTable.getColumnModel().getColumn(2).setCellEditor(new PropertyCellEditor(comb));
		declTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		declTable.getColumnModel().getColumn(1).setPreferredWidth(80);
		declTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		declTable.setGridColor(new java.awt.Color(236, 233, 216));
		
		JScrollPane scrollPane = new JScrollPane(declTable);
		scrollPane.getViewport().setBackground(Color.white);
		panel.setBackground(Color.white);

		final DefaultOverlayable overlayTable = new DefaultOverlayable(scrollPane);
		declTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				overlayTable.setOverlayVisible(declTable.getModel().getRowCount() == 0);
			}
		});
		overlayTable.addOverlayComponent(StyledLabelBuilder
				.createStyledLabel("{Drag properties here:f:gray}"));
		overlayTable.setOverlayVisible(declTable.getModel().getRowCount() == 0);
		panel.add(overlayTable);

		bookMarkPane.addTab(text, panel);
		bookMarkPane.setTabSelected(bookMarkPane.getTabCount() - 1, setDeclOpen);
		
	}

	public StringBuffer getTypeBuffer() {
		return typeBuffer;
	}

	public void setTypeBuffer(StringBuffer typeBuffer) {
		this.typeBuffer = typeBuffer;
	}

	private void insertDecisionTableTab(BookmarkPane bookMarkPane, String text) {
		bookMarkPane.addTab(text, createDecisionTable());
		bookMarkPane.setTabSelected(bookMarkPane.getTabCount() - 1, setDecisionOpen);
	}

	public void initConvertersEditors(final Table tableEModel) {
		CellEditorManager.initDefaultEditor();
		CellRendererManager.initDefaultRenderer();
		ObjectConverterManager.initDefaultConverter();
		
		cellRenderer = new RuleVariableCellRenderer(this);
		cellRenderer.setType(TableRuleVariable.class);
		CellRendererManager.registerRenderer(TableRuleVariable.class,
				                             cellRenderer);
		
		final RuleVariableConverter converter = new RuleVariableConverter(this, tableEModel);
		//MultipleEnumConverter multipleEnumConverter = new MultipleEnumConverter();
		CellEditorManager.unregisterEditor(TableRuleVariable.class);
		CellEditorManager.registerEditor(TableRuleVariable.class,
				new CellEditorFactory() {
					public CellEditor create() {
						final DefaultMultipleEnumCellEditor rvc = new DefaultRuleVariableCellEditor(editor);
						DecisionTableCellEditorListener decisionTableCellEditorListener = new DecisionTableCellEditorListener();
						rvc.setConverter(converter);
						rvc.setUseConverterContext(true);
						rvc.addCellEditorListener(decisionTableCellEditorListener);
						decisionTableCellEditorListener.addCellEditCompletionListener(new DefaultCellEditCompletionListener(functionTextField));
						return rvc;
					}
				});
		TableUtils.autoResizeAllColumns(_decisionTablePane.getTableScrollPane().getMainTable());
		_decisionTablePane.repaint();
		ObjectConverterManager.registerConverter(TableRuleVariable.class, converter);
	}
	
	
	
	
	/**
	 * @return component
	 */
	public Component createDecisionTable() {	
		final Table emodel = decisionTableModelManager.getTabelEModel();
		_decisionDataModel = decisionTableModelManager.getDtTableModel();
		functionTextField = new JTextField(20);
		if (_decisionDataModel == null) { 
			_decisionDataModel = new DecisionDataModel();
		}
		_decisionDataModel.setTableType(TableTypes.DECISION_TABLE);
		_decisionTablePane = new DecisionTablePane(_decisionDataModel, editor, functionTextField){
            @Override
            protected DecisionFieldArea createFieldArea(int areaType) {
                DecisionFieldArea fieldArea = super.createFieldArea(areaType);
                fieldArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                return fieldArea;
            }

            @Override
            protected DecisionFieldBox createFieldBox(DecisionField field, boolean sortArrowVisible, boolean filterButtonVisible) {
                DecisionFieldBox box = super.createFieldBox(field, sortArrowVisible, filterButtonVisible);
                box.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                return box;
            }
        };
;
//		initConvertersEditors(emodel);
		
		final TableScrollPane tableScrollPane = _decisionTablePane.getTableScrollPane();
		//tableScrollPane.add
		final JTable mainTable = tableScrollPane.getMainTable();
		
		if (prefStore.getBoolean(PreferenceConstants.HORIZONTAL_VERTICAL_DECISION_VIEW)) {
			//DO Nothing
		} else {
			mainTable.setDropTarget(new DropTarget(tableScrollPane, new FunctionDropHandler()));
		}
		
		DecisionFieldArea conditionsFieldArea = _decisionTablePane.getConditionFieldsArea();
		DecisionFieldArea actionsFieldArea = _decisionTablePane.getActionFieldsArea();
		
		decisionTableConditionsPaneDropHandler = new DecisionTablePaneDropHandler(decisionTableModelManager, this,
												 conditionsFieldArea,
												 TableTypes.DECISION_TABLE);
		
		decisionTableActionsPaneDropHandler = new DecisionTablePaneDropHandler(decisionTableModelManager, this,
												 actionsFieldArea,
												 TableTypes.DECISION_TABLE);
		conditionsFieldArea.setDropTarget(
				new DropTarget(tableScrollPane.getRowHeaderTable(), decisionTableConditionsPaneDropHandler));
		actionsFieldArea.setDropTarget(
				new DropTarget(tableScrollPane.getRowHeaderTable(), decisionTableActionsPaneDropHandler));
		
		JPanel toolPanel = new JPanel(new java.awt.GridLayout(1, 1));

		topPanel = new JPanel(new java.awt.GridLayout(2, 1));

		JToolBar buttonToolBar = new JToolBar();
		buttonToolBar.setFloatable(false);
		
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		final String projectName = editorInput.getProjectName();
		
		
		Action addRowAction = new AbstractAction("Add") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1482716066084619450L;

			public void actionPerformed(ActionEvent e) {
				//Stop any cell edits
				stopCellEditing(_decisionTablePane);
				rowAdditionListener = 
					new DecisionTableAddRowCommandListener(_decisionDataModel, emodel, TableTypes.DECISION_TABLE);
				int ruleIndex = 
					(Integer)CommandFacade.getInstance().executeAddRow(projectName, emodel, TableTypes.DECISION_TABLE, rowAdditionListener);
				
				((JideTable)mainTable).scrollRowToVisible(ruleIndex);
				removeButton.setEnabled(true);			
				//Select newly added rule
                _decisionTablePane.setSelectedRule(ruleIndex);
			}
		};
		addButton = new JideButton(addRowAction);
		addButton.setToolTipText("Add a new rule.");
		addButton.setIcon(ImageIconsFactory.createImageIcon("add_16x16.png"));
		buttonToolBar.add(addButton);

		Action removeColumnAction = new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				//Stop any cell edits
				stopCellEditing(_decisionTablePane);
				int[] selectedRows = mainTable.getSelectedRows();
				if (selectedRows.length == 0) {
					JOptionPane.showMessageDialog(null, Messages.getString("ROW_NOT_SELECTED"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				DecisionRule[] rules = _decisionTablePane.getSelectedRules();
				
				// check if any rule is selected
				if (rules.length > 0) {
					rowRemovalListener = 
						new DecisionTableRowRemovalCommandListener(_decisionDataModel, emodel, rules);
					CommandFacade.getInstance().
						executeRemoval(projectName, emodel, TableTypes.DECISION_TABLE, rowRemovalListener);
				}
			}
		};
		
		removeButton = new JideButton(removeColumnAction);
		//removeButton.setEnabled(false);
		removeButton.setToolTipText("Remove a new rule.");
		removeButton.setIcon(ImageIconsFactory.createImageIcon("remove_16x16.png"));
		buttonToolBar.add(removeButton);
		buttonToolBar.addSeparator();
		
		Action addCustomAction = new AbstractAction("Custom") {
			public void actionPerformed(ActionEvent e) {
				//Stop any cell edits
				stopCellEditing(_decisionTablePane);
				final JidePopup _popup = new JidePopup();
				Action conditionAction = new AbstractAction("Add a Custom Condition") {
					public void actionPerformed(ActionEvent e) {
						//Increment counters
						_decisionDataModel.incrementAndSet(DecisionConstants.AREA_CONDITION);
						int customCounter = 
							_decisionDataModel.get(DecisionConstants.AREA_CONDITION);
						String name = DTConstants.CUSTOM_CONDITION_PREFIX + customCounter;
						ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener =  
							new DecisionTableColumnAdditionCommandListener(_decisionDataModel, 
									               emodel,
									               getEditor(), 
									               name, 
								                   null, 
								                   0, 
								                   false,
								                   DecisionConstants.AREA_CONDITION, 
								                   true,
								                   false);
						CommandFacade.getInstance().
							executeColumnAddition(projectName, emodel, TableTypes.DECISION_TABLE, columnCreationCommandListener);
						//FIXME
						//autoResizeColumn(_pivotTablePane);
						_popup.hidePopup();
					}
				};
				
				Action actionAction = new AbstractAction("Add a Custom Action") {
					public void actionPerformed(ActionEvent e) {
						//Stop any cell edits
						stopCellEditing(_decisionTablePane);
						//Check if at least one condition is present
						int conditionFieldCount = _decisionDataModel.getConditionFieldCount();
						if (conditionFieldCount == 0) {
							JOptionPane.showMessageDialog(null, Messages.getString("ERROR_ADD_ACTION"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
							return;
						}
						//Increment counters
						_decisionDataModel.incrementAndSet(DecisionConstants.AREA_ACTION);
						int customCounter = 
							_decisionDataModel.get(DecisionConstants.AREA_ACTION);
						String name = DTConstants.CUSTOM_ACTION_PREFIX + customCounter;
						ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener =  
							new DecisionTableColumnAdditionCommandListener(_decisionDataModel, 
									               emodel,
									               getEditor(), 
									               name, 
								                   null, 
								                   0, 
								                   false,
								                   DecisionConstants.AREA_ACTION, 
								                   true,
								                   false);
						CommandFacade.getInstance().
							executeColumnAddition(projectName, emodel, TableTypes.DECISION_TABLE, columnCreationCommandListener);
						_popup.hidePopup();
					}
				};
				
				_popup.setPopupType(JidePopup.HEAVY_WEIGHT_POPUP);
				SubMenuButtonPanel buttonPanel = new SubMenuButtonPanel();
				JideButton addConditionButton = new JideButton(conditionAction);
				addConditionButton.setHorizontalAlignment(SwingConstants.LEFT);
				addConditionButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				buttonPanel.add(addConditionButton);
				JideButton addActionButton = new JideButton(actionAction);
				addActionButton.setHorizontalAlignment(SwingConstants.LEFT);
				addActionButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				buttonPanel.add(addActionButton);
				_popup.add(buttonPanel);
				_popup.setPopupBorder(BorderFactory
						.createLineBorder(Color.GRAY));
				// position of pop-up relative to the button
				Point p = new Point(0, ((JideButton) e.getSource()).getHeight());
				SwingUtilities.convertPointToScreen(p, (Component) e
						.getSource());
				DecisionTableUtil.custompopup = _popup;
				_popup.showPopup(p.x, p.y, (Component) e.getSource());
			}
		};
		customButton = new JideButton(addCustomAction);
		customButton.setToolTipText("Add a custom condition or action");
		customButton.setIcon(ImageIconsFactory.createImageIcon("add_16x16.png"));
		buttonToolBar.add(customButton);
		buttonToolBar.addSeparator();
		

		toggleFitContentButton = new JideButton(new AbstractAction("Fit Content") {
			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_decisionTablePane);
				toggleFitContentButton.setSelected(!toggleFitContentButton.isSelected());
				com.tibco.cep.studio.util.TableUtils.resizeAllTableColumns(mainTable, true, _decisionTablePane.getPreferredColumnWidths());
				toggleFitContentButton.setSelected(false);
			}
		});
		
		toggleFitContentButton.setToolTipText("Fit conditions and actions.");
		toggleFitContentButton.setIcon(ImageIconsFactory.createImageIcon("fit_content_16x16.png"));
//		toggleFitContentButton.setSelected(prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN));
		buttonToolBar.add(toggleFitContentButton);
		
//		toggleFitContentButton.doClick();

		Action toggleMerge = new AbstractAction("Merge Rows") {
			
			public void actionPerformed(ActionEvent e) {
				//Stop any cell edits
				stopCellEditing(_decisionTablePane);
				int ruleCount = _decisionDataModel.getRuleCount();
				if (ruleCount == 0) {
                	JOptionPane.showMessageDialog(null, Messages.getString("ROWS_ABSENT"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                	return;
                }
				JTable mainTable = _decisionTablePane.getTableScrollPane().getMainTable();
                JTable headerTable = _decisionTablePane.getTableScrollPane().getRowHeaderTable();
                int spanOn = ((CellSpanTable) mainTable).getAutoCellMerge();
                int cellMerge = spanOn == 
                	CellSpanTable.AUTO_CELL_MERGE_OFF ? 
                			CellSpanTable.AUTO_CELL_MERGE_ROWS_LIMITED : 
                				CellSpanTable.AUTO_CELL_MERGE_OFF;
                ((CellSpanTable) mainTable).setAutoCellMerge(cellMerge);
                ((CellSpanTable) headerTable).setAutoCellMerge(cellMerge);
				toggleMergeButton.setSelected((cellMerge == 0) ? false : true);
			}
		};

		toggleMergeButton = new JideButton(toggleMerge);
		toggleMergeButton.setToolTipText("Toggle between merged and separate rows");
		toggleMergeButton.setIcon(ImageIconsFactory.createImageIcon("merge_16x16.png"));
		buttonToolBar.add(toggleMergeButton);
		
		if (prefStore.getBoolean(PreferenceConstants.AUTO_MERGE_VIEW)) {
			JTable headerTable = _decisionTablePane.getTableScrollPane().getRowHeaderTable();
            int spanOn = ((CellSpanTable) mainTable).getAutoCellMerge();
            int cellMerge = spanOn == 
            	CellSpanTable.AUTO_CELL_MERGE_OFF ? 
            			CellSpanTable.AUTO_CELL_MERGE_ROWS_LIMITED : 
            				CellSpanTable.AUTO_CELL_MERGE_OFF;
            ((CellSpanTable) mainTable).setAutoCellMerge(cellMerge);
            ((CellSpanTable) headerTable).setAutoCellMerge(cellMerge);
			toggleMergeButton.setSelected((cellMerge == 0) ? true : false);
		}
		
		Action duplicateRule = new AbstractAction("Duplicate Row", ImageIconsFactory.createImageIcon("duplicateRule.png")) {
            private static final long serialVersionUID = 5837415002811011208L;

            public void actionPerformed(ActionEvent e) {
            	//Stop any cell edits
				stopCellEditing(_decisionTablePane);
                DecisionRule[] selectedRules = _decisionTablePane.getSelectedRules();
                if (selectedRules.length == 0) {
                	JOptionPane.showMessageDialog(null, Messages.getString("ROW_NOT_SELECTED"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                	return;
                }
                rowDuplicationListener = 
                	new DecisionTableRowDuplicationCommandListener(_decisionDataModel, selectedRules, emodel, TableTypes.DECISION_TABLE);
                int ruleCount = _decisionDataModel.getRuleCount();
                
            	CommandFacade.getInstance().executeDuplication(projectName, emodel, TableTypes.DECISION_TABLE, rowDuplicationListener);
            	int lastRow = ruleCount;
            	_decisionTablePane.setSelectedRules(lastRow, lastRow + selectedRules.length - 1);
            }
        };
        duplicateButton = new JideButton(duplicateRule); 
        duplicateButton.setToolTipText("Duplicates one or more selected rows");
        buttonToolBar.add(duplicateButton);

		Action toggleTextAlias = new AbstractAction("Show Text") {
			public void actionPerformed(ActionEvent e) {
				int ruleCount = _decisionDataModel.getRuleCount();
				if (ruleCount == 0) {
					JOptionPane.showMessageDialog(null, Messages.getString("ROWS_ABSENT"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
					return;
				}
//				RuleVariableConverter
//						.setTextAliasVisible(!RuleVariableConverter
//								.isTextAliasVisible());
				toggleTextAliasFlag = !toggleTextAliasFlag;
				//TODO Only resize the selected cell
				TableUtils.autoResizeAllColumns(_decisionTablePane.getTableScrollPane().getMainTable());
				_decisionTablePane.repaint();
//				toggleTextAliasButton.setSelected(RuleVariableConverter
//						.isTextAliasVisible());
				toggleTextAliasButton.setSelected(toggleTextAliasFlag);
			}
		};

		toggleTextAliasButton = new JideButton(toggleTextAlias) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				setSelected(toggleTextAliasFlag);
			}
		};
		toggleTextAliasButton.setToolTipText("Show/hide the text alias");
		
//		toggleTextAliasButton.setSelected(RuleVariableConverter
//				.isTextAliasVisible());
		toggleTextAliasButton.setSelected(toggleTextAliasFlag);
		toggleTextAliasButton.setIcon(ImageIconsFactory.createImageIcon("string_16x16.png"));
		buttonToolBar.add(toggleTextAliasButton);
		buttonToolBar.addSeparator();
		

		jideComboBox = new JideComboBox();
		jideComboBox.setPrototypeDisplayValue("AAAAAAAAAAAA");
		jideComboBox.setEditable(true);
		Action search = new AbstractAction("Search") {
			public void actionPerformed(ActionEvent e) {
				Object selectedItem = jideComboBox.getEditor().getItem();
				if (selectedItem != null) {
					final String stringToFind = selectedItem.toString();
					jideComboBox.setSelectedItem(selectedItem);
					if (search(stringToFind, jideComboBox, _decisionTablePane)) {
						int row = mainTable.getSelectedRow();
						((JideTable)mainTable).scrollRowToVisible(row);
						return;
					}
				}
				Toolkit.getDefaultToolkit().beep();
			}
		};
		
		jideComboBox.setToolTipText("Search conditions and actions.");

		findButton = new JideButton(search);
		findButton.setToolTipText("Click here to Search conditions and actions.");
		findButton.setIcon(ImageIconsFactory.createImageIcon("search_16x16.png"));

		buttonToolBar.add(jideComboBox);
		buttonToolBar.add(findButton);

		JToolBar functionToolBar = new JToolBar();
		functionToolBar.setFloatable(false);
		label = new JLabel();
		
		functionTextField.setDropTarget(new DropTarget(functionTextField,
				new TextDropTargetListener(functionTextField)));
				
		// add key listener
		
		functionTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				/**
				 * update cell value when Enter key is pressed
				 */
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					try {
						JTable mainTable = _decisionTablePane.getTableScrollPane().getMainTable();
						dtSelectedRow = mainTable.getSelectedRow();
						dtSelectedColumn = mainTable.getSelectedColumn();
						updateFunction(functionTextField, 
								       _decisionTablePane, 
								       dtSelectedRow , 
								       dtSelectedColumn);
					} catch (Exception ex) {
						DecisionTableUIPlugin.log(ex);
					} 
				}
			}
		});
		

		// add mouse listener
		functionTextField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				/**
				 * mouse clicked inside Fx box then cancel cell editing and that particular cell shoud be selectd
				 */
				stopCellEditing(_decisionTablePane);
			}
		});
		
		label.setIcon(ImageIconsFactory.createImageIcon("genericfunction_16x16.png"));

		functionToolBar.add(label);
		functionToolBar.add(Box.createHorizontalStrut(4));
		functionToolBar.add(functionTextField);
		
		mainTable.getSelectionModel().
		addListSelectionListener(new DecisionTableModelSelectionListener(this.getEditor(), mainTable, 
				                                                         functionTextField, 
				                                                         DTConstants.DECISION_TABLE));

		topPanel.add(buttonToolBar);
		topPanel.add(functionToolBar);

		toolPanel.add(topPanel);

		setToolBarDisabled();
		
	
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(toolPanel, BorderLayout.BEFORE_FIRST_LINE);
		panel.add(_decisionTablePane, BorderLayout.CENTER);
		if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS) == false) {
			panel.setBorder(BorderFactory.createCompoundBorder(
					new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup
							.getColor("controlShadow"), 1, PartialSide.NORTH),
							Messages.getString("DT_Decision_Tab"), JideTitledBorder.RIGHT,
							JideTitledBorder.CENTER), BorderFactory
							.createEmptyBorder(2, 2, 2, 2)));
		
		}

		updateToolBar();

		return panel;
	}

	public void autoResizeColumnsForAllTables() {
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault()
				.getPreferenceStore();
		if (prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN)) {
			
		}
	}

	/**
	 * update cell value as per the formula bar value
	 * @param pane
	 */
	protected void updateFunction(JTextField textField ,DecisionTablePane pane , int selectedRow , int selectedColumn) {
		TableRuleVariable ruleVariable = DtmodelFactoryImpl.eINSTANCE
				.createTableRuleVariable();

		//Expression expression = ruleVariable.getExpression();
		String expression = ruleVariable.getExpr();
		if (expression == null) {
			//expression = DtmodelFactoryImpl.eINSTANCE.createExpression();
			ruleVariable.setExpr("");
		}
		String body = textField.getText();
		ruleVariable.setExpr(body);
		TableScrollPane scrollPane = pane.getTableScrollPane();
		if (pane instanceof DecisionTablePane) {
			/*SortableTable table = ((SimpleDecisionTablePane) pane)
					.getDecisionTable();*/
			
			
			if (selectedRow != -1 && selectedColumn != -1){
				// fix CR 1-9M59YH (3.0.2)
				Object oldValue = scrollPane.getMainTable().getValueAt(selectedRow, selectedColumn);
				if (oldValue instanceof TableRuleVariable) {
					TableRuleVariable oldTRV = (TableRuleVariable)oldValue;
					String colId = oldTRV.getColId();
					String id = oldTRV.getId();
					String comment = oldTRV.getComment();
					MetaData metaDat = oldTRV.getMetatData();					
					ruleVariable.setColId(colId);
					ruleVariable.setId(id);
					ruleVariable.setComment(comment);
					ruleVariable.setMetatData(metaDat);			
					
				}
				TableCellEditor cellEditor = 
					scrollPane.getMainTable().getCellEditor(selectedRow, selectedColumn);
				if (cellEditor instanceof DefaultRuleVariableCellEditor){
					DefaultRuleVariableCellEditor editor = (DefaultRuleVariableCellEditor) cellEditor;
					Object userObject = editor.getConverterContext().getUserObject();
					Column column = ((UserObject)userObject).getColumn();
					if (column.isSubstitution()) {
						//String[] arguments = DecisionTableUtil.getArguments(body);
						String displayString = DecisionTableUtil.getFormattedString(column, body);
						//TODO This may need to be Fixed
						ruleVariable.setExpr(displayString);
					}
					if (editor.isValueAllowed()) {
						// set cell value with editor 
						editor.setEditorValue(ruleVariable);
						scrollPane.getMainTable().setValueAt(ruleVariable, selectedRow, selectedColumn);
						
						TableTypes tableType = pane.getDecisionDataModel().getTableType();
						if (tableType.equals(TableTypes.DECISION_TABLE)) {	// update constraints table only for decision table
							DecisionTableEditor dtEditor = pane.getDecisionTableEditor();
							IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)dtEditor.getEditorInput();
							String project = editorInput.getProjectName();
							Table dtTable = decisionTableModelManager.getTabelEModel();
							TableTypes dtType = decisionTableModelManager.getDtTableModel().getTableType();
							CommandFacade.getInstance().executeModify(project, dtTable, dtType, ruleVariable, oldValue,  new ModifyCommandExpression(body));
						} 
						/**
						 * it's a costly operation but can not get away with it , because PivotDataModel should be recalculated if value is set using Sortable
						 * Table Model API ,other wise it gives problem
						 */
						//pane.getPivotDataModel().calculate();
					} else {
						JOptionPane.showMessageDialog(null, Messages.getString("CELL_INPUT_NOT_ALLOWED"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
						if (oldValue instanceof TableRuleVariable){
							TableRuleVariable oldTRV = (TableRuleVariable)oldValue;
							textField.setText(oldTRV.getExpr());							
						} else {
							textField.setText("");
						}
						return;						
					}
				}
			
		
			}
		} else {
			if (scrollPane.getMainTable().getSelectedRow() != -1) {
				scrollPane.getMainTable().setValueAt(ruleVariable,
						scrollPane.getSelectedRow(),
						scrollPane.getSelectedColumn());
			}
			JTable rowHeaderTable = scrollPane.getRowHeaderTable();
			if (rowHeaderTable.getSelectedRow() != -1) {
				rowHeaderTable
						.setValueAt(
								ruleVariable,
								rowHeaderTable.getSelectedRow(),
								rowHeaderTable.getSelectedColumn());

			}
		}
	}
	
	public void stopCellEditing(final DecisionTablePane pane) {
		if (pane == null)
			return;
		TableScrollPane scrollPane = pane.getTableScrollPane();
		//JTable columnHedaerTable = scrollPane.
		JTable mainTable = scrollPane.getMainTable();
		if (mainTable.isEditing()) {
			mainTable.getCellEditor().stopCellEditing();
		}
	}
	
	/**
	 * 
	 * @param pane
	 */
	public void stopAllCellEditing() {
		stopCellEditing(_decisionTablePane);
		stopCellEditing(_exceptionTablePane);
	}
	
	public void stopPopupCellEditing(final DecisionTablePane pane) {
		if (pane == null)
			return;
		TableScrollPane scrollPane = pane.getTableScrollPane();
		JTable rowHeaderTable = scrollPane.getRowHeaderTable();
				
		// Hide any popups on the editor
		if (scrollPane.getCellEditor() != null && 
				editor.getSite().getPage().getActivePart() instanceof AbstractStudioResourceEditorPart) {
			scrollPane.getCellEditor().stopCellEditing();
		}
		
		if (rowHeaderTable != null
				&& rowHeaderTable.isEditing()) {
			if (rowHeaderTable.getCellEditor() instanceof DefaultRuleVariableCellEditor) {
				DefaultRuleVariableCellEditor ruleVariableCellEditor = 
					(DefaultRuleVariableCellEditor)rowHeaderTable.getCellEditor();
				if (ruleVariableCellEditor.getComboBox().isPopupVisible())
					ruleVariableCellEditor.getComboBox().setPopupVisible(false);
			}
		}
	}
	public void updateToolBar() {
		DecisionFieldArea conditionsFieldArea = _decisionTablePane.getConditionFieldsArea();
		DecisionFieldArea actionsFieldArea = _decisionTablePane.getActionFieldsArea();
		if (conditionsFieldArea.getFieldBoxes().length == 0
				&& actionsFieldArea.getFieldBoxes().length == 0) {
			setToolBarDisabled();
		} else {
			setToolBarEnabled();
		}
	}

	public void updateExpToolBar() {
		//setExpToolBarEnabled()
		DecisionFieldArea conditionsFieldArea = _exceptionTablePane.getConditionFieldsArea();
		DecisionFieldArea actionsFieldArea = _exceptionTablePane.getActionFieldsArea();
		if (conditionsFieldArea.getFieldBoxes().length == 0
				&& actionsFieldArea.getFieldBoxes().length == 0) {
			setExpToolBarDisabled();
		} else {
			setExpToolBarEnabled();
		}
	}

	public void setToolBarEnabled() {
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		boolean enableButtons = !editorInput.isReadOnly();
		addButton.setEnabled(enableButtons);
		removeButton.setEnabled(enableButtons);
		duplicateButton.setEnabled(enableButtons);
		//toggleVerticalButton.setEnabled(true);
		//toggleHorizontalButton.setEnabled(true);
		toggleFitContentButton.setEnabled(enableButtons);
		findButton.setEnabled(enableButtons);
		jideComboBox.setEnabled(enableButtons);
		functionTextField.setEnabled(enableButtons);
		customButton.setEnabled(enableButtons);
		toggleMergeButton.setEnabled(enableButtons);
		toggleTextAliasButton.setEnabled(enableButtons);
		// toggleGraphicsAliasButton.setEnabled(true);
		label.setEnabled(true);
	}

	public void setToolBarDisabled() {
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		boolean enableButtons = !editorInput.isReadOnly();
		addButton.setEnabled(false);
		removeButton.setEnabled(false);
		duplicateButton.setEnabled(false);
		//toggleVerticalButton.setEnabled(false);
		//toggleHorizontalButton.setEnabled(false);
		toggleFitContentButton.setEnabled(false);
		findButton.setEnabled(false);
		jideComboBox.setEnabled(false);
		functionTextField.setEnabled(false);
		// Leave the Custom button enabled at all times. Refer CR 1-916LXV.
		customButton.setEnabled(enableButtons);
		toggleMergeButton.setEnabled(false);
		toggleTextAliasButton.setEnabled(false);
		// toggleGraphicsAliasButton.setEnabled(false);
		label.setEnabled(false);
	}

	public void setExpToolBarEnabled() {
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		boolean enableButtons = !editorInput.isReadOnly();
		expAddButton.setEnabled(enableButtons);
		expRemoveButton.setEnabled(enableButtons);
		expDuplicateButton.setEnabled(enableButtons);
		expToggleFitContentButton.setEnabled(enableButtons);
		expFindButton.setEnabled(enableButtons);
		expJideComboBox.setEnabled(enableButtons);
		//expFunctionTextField.setEnabled(true);
		expCustomButton.setEnabled(enableButtons);
		expToggleMergeButton.setEnabled(enableButtons);
		expToggleTextAliasButton.setEnabled(enableButtons);
		// expToggleGraphicsAliasButton.setEnabled(true);
		expLabel.setEnabled(true);
	}

	public void setExpToolBarDisabled() {
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		boolean enableButtons = !editorInput.isReadOnly();
		expAddButton.setEnabled(false);
		expRemoveButton.setEnabled(false);
		expDuplicateButton.setEnabled(false);
		expToggleFitContentButton.setEnabled(false);
		expFindButton.setEnabled(false);
		expJideComboBox.setEnabled(false);
		//expFunctionTextField.setEnabled(false);
		// Leave the Custom button enabled at all times. Refer CR 1-916LXV.
		expCustomButton.setEnabled(enableButtons);
		expToggleMergeButton.setEnabled(false);
		expToggleTextAliasButton.setEnabled(false);
		// expToggleGraphicsAliasButton.setEnabled(false);
		expLabel.setEnabled(false);
	}

	private boolean search(String stringToFind, JideComboBox comboBox,
			DecisionTablePane decisionTablePane) {

		JTable table = decisionTablePane.getTableScrollPane().getMainTable();
		if (stringToFind.trim().length() != 0) {
			boolean itemPresent = false;
			if (comboBox.getItemCount() > 0)
				for (int count = 0; count < comboBox.getItemCount(); count++) {
					if (comboBox.getItemAt(count).toString().equalsIgnoreCase(
							stringToFind)) {
						itemPresent = true;
					}
				}
			if (itemPresent == false) {
				comboBox.addItem(stringToFind);
			}
			
			
			Searchable previousDataSearchable = Searchable
					.getSearchable(table);
			DecisionTableSearchable searchable = previousDataSearchable instanceof DecisionTableSearchable ? (DecisionTableSearchable) previousDataSearchable
					: new DecisionTableSearchable(this, table);
			searchable.setMainIndex(-1);
			int previousIndex = searchable.getSelectedIndex();
			
			searchable.setRepeats(true);
			searchable.setCaseSensitive(false);
			searchable.setFromStart(false);
			int index = -1;
			if (previousIndex >= 0) {
				index = searchable.findNext(stringToFind);
			} else {
				index = searchable.findFirst(stringToFind);
			}
			if (index != -1) {
				searchable.setSelectedIndex(index, false);
				return true;
			}
		}
		return false;
	}
	
	

	private JideComboBox expJideComboBox;

	// Returns the invoker's class loader, or null if none.
	// NOTE: This must always be invoked when there is exactly one intervening
	// frame from the core libraries on the stack between this method's
	// invocation and the desired invoker.
	static ClassLoader getCallerClassLoader() {
		// NOTE use of more generic Reflection.getCallerClass()
		Class<?> caller = Reflection.getCallerClass(3);
		// This can be null if the VM is requesting it
		if (caller == null) {
			return null;
		}
		// Circumvent security check since this is package-private
		return caller.getClassLoader();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object get(Object key) {
		Object value = UIManager.get(key);
		if (value instanceof Map && "Theme.painter".equals(key)) {
			Map map = (Map) value;
			try {
				ClassLoader classLoader = getCallerClassLoader();
				Object o = map.get(classLoader);
				while (o == null && classLoader.getParent() != null) {
					classLoader = classLoader.getParent();
					o = map.get(classLoader);
				}
				if (o == null && map.size() >= 1) {
					Collection<Object> classLoaders = map.values();
					for (Object cl : classLoaders) {
						if (cl != null) {
							o = cl;
							break;
						}
					}
				}
				return o;
			} catch (Exception e) {
				return map.get(LookAndFeelFactory.getUIManagerClassLoader());
			}
		}
		return value;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isExpToggleTextAliasFlag() {
		return expToggleTextAliasFlag;
	}

	public Component createExceptionTable() {
		
		final Table emodel = decisionTableModelManager.getTabelEModel();
		_exceptionDataModel = decisionTableModelManager.getExceptionTableModel();
		_exceptionDataModel.setTableType(TableTypes.EXCEPTION_TABLE);
//		initConvertersEditors(emodel);
		expFunctionTextField = new JTextField(20);
		_exceptionTablePane = new DecisionTablePane(_exceptionDataModel, editor, expFunctionTextField){
            @Override
            protected DecisionFieldArea createFieldArea(int areaType) {
                DecisionFieldArea fieldArea = super.createFieldArea(areaType);
                fieldArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                return fieldArea;
            }

            @Override
            protected DecisionFieldBox createFieldBox(DecisionField field, boolean sortArrowVisible, boolean filterButtonVisible) {
                DecisionFieldBox box = super.createFieldBox(field, sortArrowVisible, filterButtonVisible);
                box.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
                return box;
            }
        };
;
		final TableScrollPane tableScrollPane = _exceptionTablePane.getTableScrollPane();
		final JTable mainTable = tableScrollPane.getMainTable();
		
		if (prefStore
				.getBoolean(PreferenceConstants.HORIZONTAL_VERTICAL_DECISION_VIEW) == true) {
			
		} else {
			mainTable.setDropTarget(new DropTarget(tableScrollPane, new FunctionDropHandler()));
		}

		DecisionFieldArea conditionsFieldArea = _exceptionTablePane.getConditionFieldsArea();
		DecisionFieldArea actionsFieldArea = _exceptionTablePane.getActionFieldsArea();
		
		IDecisionTableEditorInput editorInput = (IDecisionTableEditorInput)editor.getEditorInput();
		final String projectName = editorInput.getProjectName();
		
		exceptionTableConditionsPaneDropHandler = new DecisionTablePaneDropHandler(decisionTableModelManager, this,
													  conditionsFieldArea,
													  TableTypes.EXCEPTION_TABLE);

		exceptionTableActionsPaneDropHandler = new DecisionTablePaneDropHandler(decisionTableModelManager, this,
													  actionsFieldArea,
													  TableTypes.EXCEPTION_TABLE);
		conditionsFieldArea.setDropTarget(
				new DropTarget(tableScrollPane.getRowHeaderTable(), exceptionTableConditionsPaneDropHandler));
		actionsFieldArea.setDropTarget(
				new DropTarget(tableScrollPane.getRowHeaderTable(), exceptionTableActionsPaneDropHandler));

		JPanel toolPanel = new JPanel(new java.awt.GridLayout(1, 1));

		expTopPanel = new JPanel(new java.awt.GridLayout(2, 1));

		JToolBar buttonToolBar = new JToolBar();
		buttonToolBar.setFloatable(false);

		Action addRowAction = new AbstractAction("Add") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1482716066084619450L;

			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_exceptionTablePane);
				rowAdditionListener = new DecisionTableAddRowCommandListener(_exceptionDataModel, emodel, TableTypes.EXCEPTION_TABLE);
				int ruleIndex = 
					(Integer)CommandFacade.getInstance().executeAddRow(projectName, emodel, TableTypes.EXCEPTION_TABLE, rowAdditionListener);
				
				((JideTable)mainTable).scrollRowToVisible(ruleIndex);

				//Select newly added rule
				_exceptionTablePane.setSelectedRule(ruleIndex);
			}
		};
		expAddButton = new JideButton(addRowAction);
		expAddButton.setToolTipText("Add a new rule.");
		expAddButton.setIcon(ImageIconsFactory.createImageIcon("add_16x16.png"));
		buttonToolBar.add(expAddButton);

		
		Action removeColumnAction = new AbstractAction("Remove") {
			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_exceptionTablePane);
				int[] selectedRows = mainTable.getSelectedRows();
				if (selectedRows.length == 0) {
					JOptionPane.showMessageDialog(null, Messages.getString("ROW_NOT_SELECTED"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
					return;
				}
				DecisionRule[] rules = _exceptionTablePane.getSelectedRules();
				rowRemovalListener = 
					new DecisionTableRowRemovalCommandListener(_exceptionDataModel, emodel, rules);
				CommandFacade.getInstance().
					executeRemoval(projectName, emodel, TableTypes.EXCEPTION_TABLE, rowRemovalListener);
			}
		};

		expRemoveButton = new JideButton(removeColumnAction);
		expRemoveButton.setToolTipText("Remove a new rule.");
		expRemoveButton.setIcon(ImageIconsFactory.createImageIcon("remove_16x16.png"));
		buttonToolBar.add(expRemoveButton);
		buttonToolBar.addSeparator();
				
		Action addCustomAction = new AbstractAction("Custom") {
			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_exceptionTablePane);
				final JidePopup _popup = new JidePopup();
				Action conditionAction = new AbstractAction("Add a Custom Condition") {
					public void actionPerformed(ActionEvent e) {
						//Increment counters
						_exceptionDataModel.incrementAndSet(DecisionConstants.AREA_CONDITION);
						int customCounter = 
							_exceptionDataModel.get(DecisionConstants.AREA_CONDITION);
						String name = DTConstants.CUSTOM_CONDITION_PREFIX + customCounter;
						ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener =  
							new DecisionTableColumnAdditionCommandListener(_exceptionDataModel, 
									               emodel,
									               getEditor(), 
									               name, 
								                   null, 
								                   0,
								                   false,
								                   DecisionConstants.AREA_CONDITION, 
								                   true,
								                   false);
						CommandFacade.getInstance().
							executeColumnAddition(projectName, emodel, TableTypes.EXCEPTION_TABLE, columnCreationCommandListener);
						_popup.hidePopup();
					}
				};
				
				Action actionAction = new AbstractAction("Add a Custom Action") {
					public void actionPerformed(ActionEvent e) {
						stopCellEditing(_exceptionTablePane);
						int conditionFieldCount = _exceptionDataModel.getConditionFieldCount();
						if (conditionFieldCount == 0) {
							JOptionPane.showMessageDialog(null, Messages.getString("ERROR_ADD_ACTION"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
							return;
						}
						//Increment counters
						_exceptionDataModel.incrementAndSet(DecisionConstants.AREA_ACTION);
						int customCounter = 
							_exceptionDataModel.get(DecisionConstants.AREA_ACTION);
						String name = DTConstants.CUSTOM_ACTION_PREFIX + customCounter;
						ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener =  
							new DecisionTableColumnAdditionCommandListener(_exceptionDataModel, 
									               emodel,
									               getEditor(), 
									               name, 
								                   null, 
								                   0, 
								                   false,
								                   DecisionConstants.AREA_ACTION, 
								                   true,
								                   false);
						CommandFacade.getInstance().
							executeColumnAddition(projectName, emodel, TableTypes.EXCEPTION_TABLE, columnCreationCommandListener);
						_popup.hidePopup();
					}
				};
				
				_popup.setPopupType(JidePopup.HEAVY_WEIGHT_POPUP);
				SubMenuButtonPanel buttonPanel = new SubMenuButtonPanel();
				JideButton addConditionButton = new JideButton(conditionAction);
				addConditionButton.setHorizontalAlignment(SwingConstants.LEFT);
				addConditionButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				buttonPanel.add(addConditionButton);
				JideButton addActionButton = new JideButton(actionAction);
				addActionButton.setHorizontalAlignment(SwingConstants.LEFT);
				addActionButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				buttonPanel.add(addActionButton);
				_popup.add(buttonPanel);
				_popup.setPopupBorder(BorderFactory
						.createLineBorder(Color.GRAY));
				// position of pop-up relative to the button
				Point p = new Point(0, ((JideButton) e.getSource()).getHeight());
				SwingUtilities.convertPointToScreen(p, (Component) e
						.getSource());
				DecisionTableUtil.custompopup = _popup;
				_popup.showPopup(p.x, p.y, (Component) e.getSource());
			}
		};
		expCustomButton = new JideButton(addCustomAction);
		expCustomButton.setToolTipText("Add a custom condition or action");
		expCustomButton.setIcon(ImageIconsFactory.createImageIcon("add_16x16.png"));
		buttonToolBar.add(expCustomButton);
		buttonToolBar.addSeparator();

		expToggleFitContentButton = new JideButton(new AbstractAction("Fit Content") {
			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_exceptionTablePane);
				expToggleFitContentButton.setSelected(!toggleFitContentButton.isSelected());
				JTable table = _exceptionTablePane.getTableScrollPane().getMainTable();
				com.tibco.cep.studio.util.TableUtils.resizeAllTableColumns(table, true, _exceptionTablePane.getPreferredColumnWidths());
				expToggleFitContentButton.setSelected(false);
			}
		});
		
		
		expToggleFitContentButton.setToolTipText("Fit conditions and actions.");
		expToggleFitContentButton.setIcon(ImageIconsFactory.createImageIcon("fit_content_16x16.png"));
		//toggleFitContentButton.setSelected(prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN));
		buttonToolBar.add(expToggleFitContentButton);

		Action toggleMerge = new AbstractAction("Merge Rows") {
			public void actionPerformed(ActionEvent e) {
				stopCellEditing(_exceptionTablePane);
				int ruleCount = _exceptionDataModel.getRuleCount();
				if (ruleCount == 0) {
                	JOptionPane.showMessageDialog(null, Messages.getString("ROWS_ABSENT"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                	return;
                }
				JTable mainTable = _exceptionTablePane.getTableScrollPane().getMainTable();
                JTable headerTable = _exceptionTablePane.getTableScrollPane().getRowHeaderTable();
                int spanOn = ((CellSpanTable) mainTable).getAutoCellMerge();
                int cellMerge = spanOn == 
                	CellSpanTable.AUTO_CELL_MERGE_OFF ? 
                			CellSpanTable.AUTO_CELL_MERGE_ROWS_LIMITED : 
                				CellSpanTable.AUTO_CELL_MERGE_OFF;
                ((CellSpanTable) mainTable).setAutoCellMerge(cellMerge);
                ((CellSpanTable) headerTable).setAutoCellMerge(cellMerge);
                expToggleMergeButton.setSelected((cellMerge == 0) ? true : false);
			}
		};

		expToggleMergeButton = new JideButton(toggleMerge);
		expToggleMergeButton.setToolTipText("Toggle between merged and separate rows");
		expToggleMergeButton.setIcon(ImageIconsFactory.createImageIcon("merge_16x16.png"));
		buttonToolBar.add(expToggleMergeButton);

		Action duplicateRule = new AbstractAction("Duplicate Row", ImageIconsFactory.createImageIcon("duplicateRule.png")) {
            private static final long serialVersionUID = 5837415002811011208L;

            public void actionPerformed(ActionEvent e) {
            	stopCellEditing(_exceptionTablePane);
                DecisionRule[] rules = _exceptionTablePane.getSelectedRules();
                if (rules.length == 0) {
                	JOptionPane.showMessageDialog(null, Messages.getString("ROW_NOT_SELECTED"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                	return;
                }
                rowDuplicationListener = 
                	new DecisionTableRowDuplicationCommandListener(_exceptionDataModel, rules, emodel, TableTypes.EXCEPTION_TABLE);
                int ruleCount = _exceptionDataModel.getRuleCount();
                
            	CommandFacade.getInstance().executeDuplication(projectName, emodel, TableTypes.EXCEPTION_TABLE, rowDuplicationListener);
            	int lastRow = ruleCount;
            	_exceptionTablePane.setSelectedRules(lastRow, lastRow + rules.length - 1);
            }
        };
        expDuplicateButton = new JideButton(duplicateRule); 
        buttonToolBar.add(expDuplicateButton);

        Action toggleTextAlias = new AbstractAction("Show Text") {
			public void actionPerformed(ActionEvent e) {
				int ruleCount = _exceptionDataModel.getRuleCount();
				if (ruleCount == 0) {
                	JOptionPane.showMessageDialog(null, Messages.getString("ROWS_ABSENT"), Messages.getString("MESSAGE_TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                	return;
                }
				expToggleTextAliasFlag = !expToggleTextAliasFlag;
				//TODO Only resize the selected cell
				TableUtils.autoResizeAllColumns(_exceptionTablePane.getTableScrollPane().getMainTable());
				_exceptionTablePane.repaint();
				expToggleTextAliasButton.setSelected(expToggleTextAliasFlag);
			}
		};

		expToggleTextAliasButton = new JideButton(toggleTextAlias) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				setSelected(expToggleTextAliasFlag);
			}
		};
		expToggleTextAliasButton.setToolTipText("Show/hide the text alias");
		expToggleTextAliasButton.setSelected(expToggleTextAliasFlag);
		expToggleTextAliasButton.setIcon(ImageIconsFactory.createImageIcon("string_16x16.png"));
		buttonToolBar.add(expToggleTextAliasButton);
		buttonToolBar.addSeparator();       

		expJideComboBox = new JideComboBox();
		expJideComboBox.setPrototypeDisplayValue("AAAAAAAAAAAA");
		expJideComboBox.setEditable(true);
		Action search = new AbstractAction("Search") {
			public void actionPerformed(ActionEvent e) {
				Object selectedItem = expJideComboBox.getEditor().getItem();
				if (selectedItem != null) {
					final String stringToFind = selectedItem.toString();
					expJideComboBox.setSelectedItem(selectedItem);
					if (search(stringToFind, expJideComboBox, _exceptionTablePane)) {
						int row = mainTable.getSelectedRow();
						((JideTable)mainTable).scrollRowToVisible(row);
						return;
					}
				}
				Toolkit.getDefaultToolkit().beep();
			}
		};
		
		expJideComboBox.setToolTipText("Search conditions and actions.");

		expFindButton = new JideButton(search);
		expFindButton.setToolTipText("Click here to Search conditions and actions.");
		expFindButton.setIcon(ImageIconsFactory.createImageIcon("search_16x16.png"));

		buttonToolBar.add(expJideComboBox);
		buttonToolBar.add(expFindButton);

		JToolBar functionToolBar = new JToolBar();
		functionToolBar.setFloatable(false);
		expLabel = new JLabel();
		
		expFunctionTextField.setDropTarget(new DropTarget(expFunctionTextField, new TextDropTargetListener(expFunctionTextField)));
				
		// add key listener
		
		expFunctionTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				/**
				 * update cell value when Enter key is pressed
				 */
				JTable mainTable = _exceptionTablePane.getTableScrollPane().getMainTable();
				dtSelectedRow = mainTable.getSelectedRow();
				dtSelectedColumn = mainTable.getSelectedColumn();
				if (KeyEvent.VK_ENTER == e.getKeyCode()){
					try {
					updateFunction(expFunctionTextField, 
							       _exceptionTablePane, 
							       dtSelectedRow , 
							       dtSelectedColumn);
					} catch (Exception ex){
						DecisionTableUIPlugin.log(ex);
					} finally {
						// reset 
						dtSelectedRow = -1;
						dtSelectedColumn = -1;
					}
				}
			}
		});

		// add mouse listener
		expFunctionTextField.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				/**
				 * mouse clicked inside Fx box then cancel cell editing and that particular cell shoud be selectd
				 */
				stopCellEditing(_exceptionTablePane);
			}
		});
		
		expLabel.setIcon(ImageIconsFactory.createImageIcon("genericfunction_16x16.png"));
		

		functionToolBar.add(expLabel);
		functionToolBar.add(Box.createHorizontalStrut(4));
		functionToolBar.add(expFunctionTextField);
		
		mainTable.getSelectionModel().addListSelectionListener(new DecisionTableModelSelectionListener(this.getEditor(), mainTable, 
				                                                         expFunctionTextField, 
				                                                         DTConstants.EXCEPTION_TABLE));

		expTopPanel.add(buttonToolBar);
		expTopPanel.add(functionToolBar);

		toolPanel.add(expTopPanel);

		setExpToolBarDisabled();

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(toolPanel, BorderLayout.BEFORE_FIRST_LINE);
		panel.add(_exceptionTablePane, BorderLayout.CENTER);
		if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS) == false) {
			panel.setBorder(BorderFactory.createCompoundBorder(
					new JideTitledBorder(new PartialLineBorder(UIDefaultsLookup
							.getColor("controlShadow"), 1, PartialSide.NORTH),
							Messages.getString("DT_Exception_Tab"), JideTitledBorder.RIGHT,
							JideTitledBorder.CENTER), BorderFactory
							.createEmptyBorder(2, 2, 2, 2)));
		}

		updateExpToolBar();
		
		if (mainTable.getColumnCount() > 0) {
			setExceptionOpen = true;
		}
		
		return panel;
	}

	
	private void insertExceptionTableTab(BookmarkPane bookMarkPane, String text) {
		bookMarkPane.addTab(text, createExceptionTable());
		bookMarkPane.setTabSelected(bookMarkPane.getTabCount() - 1, setExceptionOpen);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.swtAwtComponent.setFocus();
	}

	public void showOverview() {
		IWorkbench workBench = PlatformUI.getWorkbench();
		if(workBench != null){
			IWorkbenchWindow activeWorkBenchWindow = workBench.getActiveWorkbenchWindow();
				if(activeWorkBenchWindow != null){
					IWorkbenchPage workBenchPage = activeWorkBenchWindow.getActivePage();
						if(workBenchPage != null){
							IViewPart view = workBenchPage.findView(CommonOverview.ID);
							if (view != null) {
								((CommonOverview) view).getOverview().setScrollPane(
										(JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, _decisionTablePane));
							}
						}
				}
		}
	}

	public DecisionDataModel getModel() {
		return _decisionDataModel;
	}

	public TableModel getDeclTableModel() {
		return declTableModel;
	}

	public void setDeclTableModel(TableModel declTableModel) {
		this.declTableModel = declTableModel;
	}

	public JTable getDecltable() {
		return declTable;
	}

	public void setDecltable(JTable decltable) {
		this.declTable = decltable;
	}

	public DecisionTablePane getDecisionTablePane() {
		return _decisionTablePane;
	}

	public void set_DecisionTablePane(DecisionTablePane tablePane) {
		_decisionTablePane = tablePane;
	}

	private class DecisionTableSearchable extends JideTableSearchable {
		private int fCurrentIndex;
		
		private RuleVariableConverter ruleVariableConverter;

		public DecisionTableSearchable(DecisionTableDesignViewer decisionTableDesignViewer, JTable table) {
			super(table);
			ruleVariableConverter = new RuleVariableConverter(decisionTableDesignViewer, decisionTableModelManager.getTabelEModel());
		}

		@Override
		protected Object getElementAt(int i) {
			fCurrentIndex = i;
			return super.getElementAt(i);
		}

		@Override
		protected String convertElementToString(Object item) {
			if (item instanceof TableRuleVariable) {
				int columns = ((JTable) getComponent()).getColumnCount();
				int column = 0;
				int row = 0;
				int i = fCurrentIndex;
				if (columns > 0 && i > 0) {
					column = i % columns;
					row = i / columns;
				}
				TableCellRenderer cellRenderer = ((JTable) getComponent())
						.getCellRenderer(row, column);
				ConverterContext converterContext = null;
				RuleVariableCellRenderer rvCellRenderer = null;
				if (cellRenderer instanceof RuleVariableCellRenderer) {
					rvCellRenderer = (RuleVariableCellRenderer) cellRenderer;
				}
				if (rvCellRenderer != null) {
					converterContext = rvCellRenderer.getConverterContext();
				}
				String value = ruleVariableConverter.toString(item, converterContext);
				return value;
			}
			return super.convertElementToString(item);
		}

		@Override
		protected int getSelectedIndex() {
			return super.getSelectedIndex();
		}

		@Override
		public void setSelectedIndex(int i, boolean scroll) {
			super.setSelectedIndex(i, scroll);
		}
	}

	public JTextField getFunctionTextField() {
		return functionTextField;
	}

	public void setFunctionTextField(JTextField functionTextField) {
		this.functionTextField = functionTextField;
	}

	private static class ScrollableBookmarkPane extends BookmarkPane implements
			Scrollable {

		public Dimension getPreferredScrollableViewportSize() {
			return getPreferredSize();
		}

		public int getScrollableUnitIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			return 100;
		}

		public int getScrollableBlockIncrement(Rectangle visibleRect,
				int orientation, int direction) {
			return 100;
		}

		public boolean getScrollableTracksViewportWidth() {
			return true;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		@Override
		public void insertTab(String title, Icon icon, Component component,
				String tip, int index) {
			if (!(component instanceof ResizablePanel)) {
				ResizablePanel panel = new ResizablePanel() {
					private static final long serialVersionUID = 724237876398523124L;

					@Override
					protected Resizable createResizable() {
						Resizable resizable = new Resizable(this) {

							@Override
							public void resizing(int resizeCorner, int newX,
									int newY, int newW, int newH) {
								// TODO Auto-generated method stub
								super.resizing(resizeCorner, newX, newY, newW,
										newH);
								getComponent().invalidate();
								Container p = getComponent().getParent();
								while (!(p instanceof Panel)) {
									p = p.getParent();
								}
								if (p instanceof Panel) {
									p.invalidate();
									JideSwtUtilities.doLayoutAll(p);
								} else {
									JideSwtUtilities
											.doLayoutAll(getComponent());
								}
							}
						};
						resizable.setResizableCorners(Resizable.LOWER);
						resizable.setTopLevel(true);
						return resizable;
					}
				};
				panel.setLayout(new BorderLayout());
				panel.add(component, BorderLayout.CENTER);
				panel.setBorder(new EmptyBorder(0, 0, 4, 0) {
					@Override
					public void paintBorder(Component c, Graphics g, int x,
							int y, int width, int height) {
						super.paintBorder(c, g, x, y, width, height);
						ThemePainter painter = (ThemePainter) get("Theme.painter");
						if (painter == null) {
							LookAndFeelFactory.installJideExtension();
							painter = (ThemePainter) get("Theme.painter");
						}
						Insets insets = getBorderInsets(c);
						Rectangle rect = new Rectangle(0, y + height
								- insets.bottom, width, insets.bottom);
						painter.paintGripper((JComponent) c, g, rect,
								SwingConstants.HORIZONTAL,
								ThemePainter.STATE_DEFAULT);
					}
				});
				super.insertTab(title, icon, panel, tip, index);
			} else {
				super.insertTab(title, icon, component, tip, index);
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			if (isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}
	}

	public void performFind(JTextField focused) {
		FindReplaceAction action;
		if (editor != null) {
			action = new FindReplaceAction(ResourceBundle
					.getBundle("com.tibco.cep.decision.table.utils.messages"),
					"FindReplaceAction.", editor.getSite().getShell(),
					new DTFindReplaceTarget(focused));
		} else {
			action = new FindReplaceAction(ResourceBundle
					.getBundle("com.tibco.cep.decision.table.utils.messages"),
					"FindReplaceAction.", new Shell(SWT.SYSTEM_MODAL),
					new DTFindReplaceTarget(focused));
		}
		action.run();
	}

	public DecisionTableEditor getEditor() {
		return editor;
	}

	public void setEditor(DecisionTableEditor editor) {
		this.editor = editor;
	}

	public DecisionTablePane getExceptionTablePane() {
		return _exceptionTablePane;
	}

	public void setExceptionTablePane(DecisionTablePane exceptionTablePane) {
		this._exceptionTablePane = exceptionTablePane;
	}

	public JideButton getToggleFitContentButton() {
		return toggleFitContentButton;
	}

	public JideButton getExpToggleFitContentButton() {
		return expToggleFitContentButton;
	}

	public JideButton getToggleMergeButton() {
		return toggleMergeButton;
	}

	public JideButton getExpToggleMergeButton() {
		return expToggleMergeButton;
	}

	public JideButton getToggleTextAliasButton() {
		return toggleTextAliasButton;
	}

	public JideButton getExpToggleTextAliasButton() {
		return expToggleTextAliasButton;
	}
	
	public boolean isDeclarationSectionOpen()
	{
		if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS) == true)
			return (declSection != null ? declSection.isExpanded() : false);
		else
			return (bookMarkPane.isTabSelected(0));
	}
	
	public boolean isDecisionSectionOpen()
	{
		if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS) == true)
			return (decisionSection != null ? decisionSection.isExpanded() : false);
		else
			return (bookMarkPane.isTabSelected(1));
	}

	public boolean isExceptionSectionOpen()
	{
		if (prefStore.getBoolean(PreferenceConstants.EDITOR_SECTIONS) == true)
			return (exceptionSection != null ? exceptionSection.isExpanded() : false);
		else
			return (bookMarkPane.isTabSelected(2));
	}

	public class DTFindReplaceTarget implements IFindReplaceTarget {

		private JTextField initialTextField;
		protected String currentFindString = null;
		private int currentSelectionIndex = -1;
		private JTable currentTable = null;
		private DecisionTablePane[] tablesToSearch;
		private int currentTableIndex = 0;

		public DTFindReplaceTarget(JTextField focused) {
			this.initialTextField = focused;
			tablesToSearch = new DecisionTablePane[] { _decisionTablePane,
					_exceptionTablePane };
		}

		public boolean canPerformFind() {
			return true;
		}

		public int findAndSelect(final int widgetOffset,
				final String findString, final boolean searchForward,
				final boolean caseSensitive, final boolean wholeWord) {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					public void run() {
						internalFindAndSelect(widgetOffset);
					}

					private void internalFindAndSelect(int initialOffset) {
						boolean findFirst = initialOffset == -1;
						DecisionTablePane pane = tablesToSearch[currentTableIndex];
						
						if (!pane.isShowing()) {
							switchCurrentTable();
							return;
						}
						currentFindString = findString;
						int oldTableIndex = currentTableIndex;
						JTable table = pane.getTableScrollPane().getMainTable();
						if (findInTable(findString, searchForward,
								caseSensitive, table, findFirst)) {
							return;
						} else {
							if (oldTableIndex != currentTableIndex) {
								// we've looped to a different table, run
								// search again
								if ((searchForward && oldTableIndex < currentTableIndex)
										|| !searchForward
										&& oldTableIndex > currentTableIndex) {
									internalFindAndSelect(-1);
								}
							} else {
								currentSelectionIndex = -1;
							}
						}
					
					}

					private void switchCurrentTable() {
						if (searchForward && currentTableIndex == 1) {
							currentTableIndex = currentTableIndex == 0 ? 1 : 0; // this
							// assumes
							// there
							// are
							// only
							// two
							// tables
							// to
							// search
							currentSelectionIndex = -1;
						} else if (!searchForward && currentTableIndex == 0) {
							currentTableIndex = currentTableIndex == 0 ? 1 : 0; // this
							// assumes
							// there
							// are
							// only
							// two
							// tables
							// to
							// search
							currentSelectionIndex = -1;
						} else {
							currentTableIndex = currentTableIndex == 0 ? 1 : 0; // this
							// assumes
							// there
							// are
							// only
							// two
							// tables
							// to
							// search
							currentSelectionIndex = -1;
						}
					}

					private boolean findInTable(final String findString,
							final boolean searchForward,
							final boolean caseSensitive, JTable table,
							boolean findFirst) {
						stopCellEditing(_decisionTablePane);
						stopCellEditing(_exceptionTablePane);
						Searchable previousDataSearchable = Searchable
								.getSearchable(table);
						DecisionTableSearchable searchable = previousDataSearchable instanceof DecisionTableSearchable ? (DecisionTableSearchable) previousDataSearchable
								: new DecisionTableSearchable(_decisionTablePane.getDecisionTableEditor().getDecisionTableDesignViewer(), table);
						if (searchable != null) {
							int prevIndex = searchable.getSelectedIndex();
							searchable.setCaseSensitive(caseSensitive);
							searchable.setRepeats(false);
							searchable.setFromStart(false);
							int index = -1;
							if (searchForward) {
								index = findFirst ? searchable
										.findFirst(findString) : searchable
										.findNext(findString);
								if (!findFirst && index < prevIndex
										&& table.equals(currentTable)) {
									switchCurrentTable();
									return false; // loop to the next table
								}
							} else {
								index = findFirst ? searchable
										.findLast(findString) : searchable
										.findPrevious(findString);
								if (!findFirst && index > prevIndex
										&& table.equals(currentTable)) {
									return false; // loop to the next table
								}
							}
							if (index == prevIndex && !findFirst) {
								switchCurrentTable();
								return false;
							}
							if (index != -1) {
								searchable.setSelectedIndex(index, false);
								currentTable = table;
								currentSelectionIndex = index;
								return true;
							}
						}
						return false;
					}

				});
			} catch (InterruptedException e) {
				DecisionTableUIPlugin.log(e);
			} catch (InvocationTargetException e) {
				DecisionTableUIPlugin.log(e);
			}

			return currentSelectionIndex;
		}

		public org.eclipse.swt.graphics.Point getSelection() {
			if (initialTextField != null) {
				return new org.eclipse.swt.graphics.Point(initialTextField
						.getSelectionStart(), initialTextField
						.getSelectionEnd());
			}
			return new org.eclipse.swt.graphics.Point(0, 1);
		}

		public String getSelectionText() {
			Component comp = KeyboardFocusManager
					.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
			if (comp != null && comp instanceof JTextField) {
				JTextField focused = (JTextField) comp;
				String text = focused.getSelectedText();
				return text != null ? text : "";
			} 
			//TODO FIX THIS
			/*else if (comp instanceof DecisionTable) {
				DecisionTable table = (DecisionTable) comp;
				Object item = table.getValueAt(table.getSelectedRow(), table
						.getSelectedColumn());
				if (item instanceof TableRuleVariable) {
					return new RuleVariableConverter(decisionTableModelManager.getTabelEModel()).toString(item, null);
				}
			}*/
			return "";
		}

		public boolean isEditable() {
			return false; // disable 'replace' for the time being
		}

		public void replaceSelection(String text) {
			// this is not currently enabled (see isEditable()), nor is it
			// completely accurate.
			// replace doesn't necessarily make sense for DT cells, since many
			// of the values might be
			// selected from a list
			Component comp = KeyboardFocusManager
					.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
			if (comp != null && comp instanceof JTextField) {
				JTextField focused = (JTextField) comp;
				String curr = focused.getText();
				String newString = curr.replaceAll(text, text);
				focused.setText(newString);
			} //TODO FIX THIS
			/*else if (comp instanceof DecisionTable) {
				DecisionTable table = (DecisionTable) comp;
				Object item = table.getValueAt(table.getSelectedRow(), table
						.getSelectedColumn());
				if (item instanceof TableRuleVariable) {
					String repText = new RuleVariableConverter(decisionTableModelManager.getTabelEModel()).toString(item,
							null);
					System.out.println("current text " + repText
							+ "; current findString " + currentFindString);
				}
			}*/
		}

	}

	public boolean isSetDeclOpen() {
		return setDeclOpen;
	}

	public boolean isSetDecisionOpen() {
		return setDecisionOpen;
	}

	public boolean isSetExceptionOpen() {
		return setExceptionOpen;
	}

	public void dispose() {
		if (managedForm != null) {
			managedForm.dispose();
		}
		if (bookMarkPane != null) {
			bookMarkPane.removeAll();
			bookMarkPane = null;
		}
		editor = null;
		if (swtAwtComponent != null) {
			swtAwtComponent.dispose();
			swtAwtComponent = null;
		}
		declSection = null;
		if (declTable != null) {
			declTable.getModel().removeTableModelListener(declTableModelListener);
			declTable = null;
		}
		if (cellRenderer != null) {
			CellRendererManager.unregisterRenderer(TableRuleVariable.class);
			cellRenderer.dispose();
			cellRenderer = null;
		}

		declTableModel = null;
		
		exceptionSection = null;
		_decisionDataModel = null;
		_decisionTablePane = null;
		_exceptionDataModel = null;
		_exceptionTablePane = null;
		
		topPanel = null;
		expTopPanel = null;
		disposeButtons();
	}
	
	private void disposeButtons() {
		addButton = null;
		duplicateButton = null;
		removeButton = null;
		customButton = null;
		
		toggleMergeButton = null;
		toggleTextAliasButton = null;
		
		toggleFitContentButton = null;
		findButton = null;
		jideComboBox = null;
		functionTextField = null;

		expAddButton = null;
		expDuplicateButton = null;
		expRemoveButton = null;
		expCustomButton = null;
		expToggleMergeButton = null;
		expToggleTextAliasButton = null;
		
		expToggleFitContentButton = null;
		expFindButton = null;
	}

	public void setToggleMergeButton (boolean autoMerge) {
		if (toggleMergeButton != null) {
			toggleMergeButton.setSelected(autoMerge);
		}
	}
	
	public boolean getExpTextAliasFlag() {
		return expToggleTextAliasFlag;
	}

}