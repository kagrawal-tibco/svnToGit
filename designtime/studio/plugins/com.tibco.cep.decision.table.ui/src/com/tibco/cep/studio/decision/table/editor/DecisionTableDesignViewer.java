package com.tibco.cep.studio.decision.table.editor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.IRowIdAccessor;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.filterrow.command.ToggleFilterRowCommand;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupExpandCollapseLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.group.event.GroupColumnsEvent;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.layer.stack.ColumnGroupBodyLayerStack;
import org.eclipse.nebula.widgets.nattable.reorder.RowReorderLayer;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeColumnsCommand;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeRowsCommand;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionBindings;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.decision.DecisionTableMetaDataModel;
import com.tibco.cep.decision.table.command.CommandFacade;
import com.tibco.cep.decision.table.command.ICommandCreationListener;
import com.tibco.cep.decision.table.command.impl.AddCommand;
import com.tibco.cep.decision.table.command.impl.DuplicateCommand;
import com.tibco.cep.decision.table.command.impl.RemoveCommand;
import com.tibco.cep.decision.table.command.memento.ColumnPositionStateMemento;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.Messages;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableAddRowCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableColumnAdditionCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableDuplicateRowCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.DecisionTableRemoveRowCommandListener;
import com.tibco.cep.studio.decision.table.command.listeners.InsertLocation;
import com.tibco.cep.studio.decision.table.configuration.DTBodyStyleConfiguration;
import com.tibco.cep.studio.decision.table.configuration.DTCellDoubleClickConfiguration;
import com.tibco.cep.studio.decision.table.configuration.DTColumnHeaderStyleConfiguration;
import com.tibco.cep.studio.decision.table.configuration.DTLabelAccumulator;
import com.tibco.cep.studio.decision.table.configuration.DTRowHeaderStyleConfiguration;
import com.tibco.cep.studio.decision.table.configuration.DTSelectionStyleConfiguration;
import com.tibco.cep.studio.decision.table.listeners.DTCellSelectionListener;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.providers.DTColumnPropertyAccessor;
import com.tibco.cep.studio.decision.table.providers.DTHeaderDataProvider;
import com.tibco.cep.studio.decision.table.providers.DTRowHeaderDataProvider;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.RuleIDGeneratorManager;
import com.tibco.cep.studio.decision.table.validation.DecisionTableDataValidator;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TransformedList;

/**
 * Responsible for creating and configuring a NatTable based decision/exception table
 * form editor.  The editor contains a declarations section, a decision table section,
 * and an exception table section.
 * 
 * @author rhollom
 *
 */
public class DecisionTableDesignViewer implements IPropertyChangeListener {
	private DecisionTableMetaDataModel decTableDataModel;
	private DecisionTableMetaDataModel expTableDataModel;
	
    public class UpdateListTask extends TimerTask {

        private EventList<TableRule> target;
		private TableRuleSet tableRuleSet;

        public UpdateListTask(EventList<TableRule> target, TableRuleSet tableRuleSet) {
            this.target = target;
            this.tableRuleSet = tableRuleSet;
        }

        public void run() {
            target.getReadWriteLock().writeLock().lock();
            target.clear();
            target.addAll(tableRuleSet.getRule());
            target.getReadWriteLock().writeLock().unlock();
        }
     }

    public static final String CHECK_BOX_CONFIG_LABEL = "CheckBoxLabel";
	public static final String CHECK_BOX_EDITOR_CONFIG_LABEL = "CheckBoxEditorLabel";
	public static final String COMBO_BOX_CONFIG_LABEL = "ComboBoxLabel";
	public static final String COMBO_BOX_EDITOR_CONFIG_LABEL = "ComboBoxEditorLabel";
	public static final String COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL = "comboBoxDisplayEditorLabel";
	public static final String MULTI_COMBO_BOX_CONFIG_LABEL = "multiComboBoxLabel";
	public static final String MULTI_COMBO_BOX_EDITOR_CONFIG_LABEL = "multiComboBoxEditorLabel";
	public static final String MULTI_COMBO_BOX_DISPLAY_EDITOR_CONFIG_LABEL = "multiComboBoxDisplayEditorLabel";

	public static final String ACTIONS_COLUMN_GROUP = "Actions";
	public static final String CONDITIONS_COLUMN_GROUP = "Conditions";
	public static final String ACTIONS_GROUP_LABEL = "ActionsLabel";
	public static final String ACTIONS_GROUP_ALT_LABEL = "ActionsAltLabel";
	public static final String CONDITIONS_GROUP_LABEL = "ConditionsLabel";
	public static final String CONDITIONS_GROUP_ALT_LABEL = "ConditionsAltLabel";
	public static final String DISABLE_LABEL = "DisableLabel";
	public static final String VALUE_NOTSET_LABEL = "ValueNotSetLabel";
	public static final String CHECK_BOX_COMMENT_LABEL = "CheckBoxCommentLabel";
	public static final String COMBO_BOX_COMMENT_LABEL = "ComboBoxCommentLabel";
	public static final String TEXT_BOX_COMMENT_LABEL = "TextBoxCommentLabel";
	public static final String ANALYZER_COVERAGE_LABEL = "AnalyzerCoverageLabel";
	public static final String COLUMN_STYLE = "ColumnStyle";
//	private static final String PROPERTIES_FILE = "natTable.properties";
	public static final String DATETIME_LABEL = "DateTimeLabel";
	
	private Table table;
	private IDecisionTableEditor editor;
	protected Section declSection;
	protected Section decisionTableSection;
	protected Section exceptionTableSection;
	protected Action showDeclAction;
	protected Action showDecisionTableAction;
	protected Action showExceptionTableAction;
	private IExpansionListener expansionListener;
	private EventList<TableRule> decisionTableEventList = new BasicEventList<TableRule>();
	private EventList<TableRule> exceptionTableEventList = new BasicEventList<TableRule>();
	private NatTable decisionTableNT;
	private NatTable exceptionTableNT;
	private DTBodyLayerStack<TableRule> dtBodyLayer = null;
	private DTBodyLayerStack<TableRule> etBodyLayer = null;
	private DTHeaderDataProvider dtColumnHeaderDataProvider;
	private DTHeaderDataProvider etColumnHeaderDataProvider;
	
	private ToolBar decisionTableToolbar = null;
	private ToolBar exceptionTableToolbar = null;
	private boolean invalidPart = false;
	/**
	 * Flag indicating whether the toggle text button is on/off.
	 */
	private boolean toggleTextAliasFlag;
	
	/**
	 * Flag indicating whether the toggle text button is on/off.
	 */
	private boolean expToggleTextAliasFlag;
	
	/**
	 * The date/time format to use for this viewer.
	 * Subclasses may override get getDateFormat() if necessary.
	 */
    private final SimpleDateFormat sdf = new SimpleDateFormat(com.tibco.cep.studio.core.utils.ModelUtils.DATE_TIME_PATTERN);
	
	final IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
	protected SashForm sashForm;
	protected ManagedForm managedForm;
	private Composite emptyTooltipComposite;
    protected void createPartControl(Composite container,String title,Image titleImage){
    	
    	managedForm = new ManagedForm(container);
		final ScrolledForm form = managedForm.getForm();
		
		FormToolkit toolkit = managedForm.getToolkit();
		form.setText(title);
		form.setImage(titleImage);
		form.setBackgroundImage(DecisionTableUIPlugin.getDefault().getImage("icons/form_banner.gif"));
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 1;
		form.getBody().setLayout(layout);
		
//		createGeneralPart(form, toolkit);
		
		sashForm = new SashForm(form.getBody(), SWT.VERTICAL);
		
		sashForm.setData("form", managedForm);
		toolkit.adapt(sashForm, false, false);
		sashForm.setMenu(form.getBody().getMenu());
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		
//		createExpiryActionPart(managedForm, sashForm);
//		createPropertiesPart(managedForm, sashForm);
//		createPayloadPart(managedForm, sashForm);//for Event
//		createExtendedPropertiesPart(managedForm, sashForm);
		createExtensionContributedParts(managedForm, sashForm);
		
//		hookResizeListener();
		
		managedForm.initialize();
    }
    
	public DecisionTableDesignViewer(Table table, IDecisionTableEditor editor, boolean invalidPart) {
		this.table = table;
		this.editor = editor;
		this.invalidPart = invalidPart;
		prefStore.addPropertyChangeListener(this);
	}
	
	public void tableModified() {
		editor.modified();
	}

	protected void createExtensionContributedParts(IManagedForm form,
			Composite parent) {
		if (invalidPart) {
			return;
		}
		setupToolbarActions();
		FormToolkit toolkit = managedForm.getToolkit();
		createDeclarationsSection(parent, toolkit);
		{
			
			// Decision Table section
			decisionTableSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
			decisionTableSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
			decisionTableSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
			decisionTableSection.setText("Decision Table");
			
			decisionTableSection.addExpansionListener(getExpansionListener());
			
			GridData dtgd = new GridData(GridData.FILL_BOTH);
			decisionTableSection.setLayoutData(dtgd);
			
			Composite dtsectionClient = toolkit.createComposite(decisionTableSection, SWT.NONE);
			decisionTableSection.setClient(dtsectionClient);
		
//			createNatTable(dtsectionClient, table.getDecisionTable());
			decTableDataModel = new DecisionTableMetaDataModel(TableTypes.DECISION_TABLE);
			decTableDataModel.initializeCustomColumnCounters(table);
			// Column header
			dtColumnHeaderDataProvider = createDTHeaderDataProvider(table.getDecisionTable());
			decisionTableNT = createNatTableWithGL(dtsectionClient, table.getDecisionTable(), decisionTableEventList, dtColumnHeaderDataProvider, TableTypes.DECISION_TABLE);
			decisionTableNT.addMouseListener(new DTCellSelectionListener(decisionTableNT, editor));
			decisionTableToolbar = new ToolBar(decisionTableSection, SWT.FLAT | SWT.HORIZONTAL | SWT.LEFT);
			createToolbar(decisionTableSection, decisionTableToolbar, decisionTableNT, decisionTableEventList, 
																							table.getDecisionTable(), dtColumnHeaderDataProvider, TableTypes.DECISION_TABLE);
			if (decisionTableEventList.size() == 0) {
				new Timer().schedule(new UpdateListTask(decisionTableEventList, table.getDecisionTable()), 0);
			}
			EList<TableRule> rules = table.getDecisionTable().getRule();
			for(TableRule rule : rules) {
				RuleIDGeneratorManager.INSTANCE.steadyIncrement(table.getOwnerProjectName(), table.getPath(), Integer.parseInt(rule.getId()));
			}
		}
		createExceptionTable(parent, toolkit);
	//	boolean showExpandedText=prefStore.getBoolean(PreferenceConstants.SHOW_ALIAS);
		//toggleShowExpandedText(decisionTableEventList,showExpandedText);
		//toggleShowExpandedText(exceptionTableEventList,showExpandedText);
		updateWeights();
	 
	}

	protected DTHeaderDataProvider createDTHeaderDataProvider(TableRuleSet ruleSet) {
	    return new DTHeaderDataProvider(ruleSet);
	}
	
    protected void createExceptionTable(Composite parent, FormToolkit toolkit) {
        // Exception table section
        exceptionTableSection = toolkit.createSection(parent, Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
        exceptionTableSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        exceptionTableSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
        exceptionTableSection.setText("Exception Table");
        exceptionTableSection.addExpansionListener(getExpansionListener());
        
        
        GridData dtgd = new GridData(GridData.FILL_BOTH);
        exceptionTableSection.setLayoutData(dtgd);
        
        Composite dtsectionClient = toolkit.createComposite(exceptionTableSection, SWT.NONE);
        exceptionTableSection.setClient(dtsectionClient);
        
//			createNatTable(dtsectionClient, table.getExceptionTable());
        expTableDataModel = new DecisionTableMetaDataModel(TableTypes.EXCEPTION_TABLE);
        expTableDataModel.initializeCustomColumnCounters(table);
        // Column header
        etColumnHeaderDataProvider = new DTHeaderDataProvider(table.getExceptionTable());
        exceptionTableNT = createNatTableWithGL(dtsectionClient, table.getExceptionTable(), exceptionTableEventList, etColumnHeaderDataProvider, TableTypes.EXCEPTION_TABLE);
        exceptionTableToolbar = new ToolBar(exceptionTableSection, SWT.FLAT | SWT.HORIZONTAL | SWT.LEFT);
        createToolbar(exceptionTableSection, exceptionTableToolbar, exceptionTableNT, exceptionTableEventList, 
        																				table.getExceptionTable(), etColumnHeaderDataProvider, TableTypes.EXCEPTION_TABLE);
        if (exceptionTableEventList.size() == 0) {
        	new Timer().schedule(new UpdateListTask(exceptionTableEventList, table.getExceptionTable()), 0);
        }
        EList<TableRule> rules = table.getExceptionTable().getRule();
        for(TableRule rule : rules) {
        	RuleIDGeneratorManager.INSTANCE.steadyIncrement(table.getOwnerProjectName(), table.getPath(), Integer.parseInt(rule.getId()));
        }
    }

    protected void createDeclarationsSection(Composite parent, FormToolkit toolkit) {
        // Declarations section
        declSection = toolkit.createSection(parent, Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
        declSection.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        declSection.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
        declSection.setText("Declarations");
        declSection.addExpansionListener(getExpansionListener());

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        declSection.setLayoutData(gd);

        Composite sectionClient = toolkit.createComposite(declSection, SWT.NONE);

        declSection.setClient(sectionClient);
        createDeclGroup(sectionClient, toolkit);
    }	

	// Preferences are saved and loaded from Preference Store so no need to save/load nattable state
//	private void loadState() {
//		Properties properties = new Properties();
//
//		try {
//			System.out.println("Loading NatTable state from " + PROPERTIES_FILE);
//			properties.load(new FileInputStream(new File(PROPERTIES_FILE)));
//			decisionTableNT.loadState(table.getName(), properties);
//		} catch (FileNotFoundException e) {
//			// No file found, oh well, move along
//			System.out.println(PROPERTIES_FILE + " not found, skipping load");
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
	
//	public void saveState() {
//		Properties properties = new Properties();
//
//		decisionTableNT.saveState(table.getName(), properties);
//
//		try {
//			System.out.println("Saving NatTable state to " + PROPERTIES_FILE);
//			properties.store(new FileOutputStream(new File(PROPERTIES_FILE)), "NatTable state");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	private void createToolbar(Section parentSection, ToolBar toolbar, final NatTable targetTable, final EventList<TableRule> targetList, 
			final TableRuleSet targetRuleSet, DTHeaderDataProvider columnHeaderDataProvider, final TableTypes tableType) {
		final DTBodyLayerStack<TableRule> targetStack = (DTBodyLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getBodyLayer();	
		toolbar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final ToolItem addItem = new ToolItem(toolbar, SWT.DROP_DOWN);
		addItem.setText("Add Row");
		addItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/add_16x16.png"));
		addItem.setToolTipText("Add row");
		addItem.setWidth(200);
		
		final Menu addRowMenu = createAddRowMenu(targetTable, targetRuleSet, tableType, targetStack, targetList);
		
		addItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();

				if (e.detail == SWT.ARROW) {
					MenuItem[] items = addRowMenu.getItems();
					Integer[] selectedRows = getSelectedRows(targetStack);
					if (selectedRows.length == 0) {
						items[0].setEnabled(false);
						items[1].setEnabled(false);
					} else {
						items[0].setEnabled(true);
						items[1].setEnabled(true);
					}
					addRowMenu.setVisible(true);
					return;
				}
				ICommandCreationListener<AddCommand<TableRule>, TableRule> listener = new DecisionTableAddRowCommandListener(targetList, targetRuleSet, table, table.getPath(), -1, InsertLocation.END);
				CommandFacade.getInstance().executeAddRow(table.getOwnerProjectName(), table, tableType, listener);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		final ToolItem removeItem = new ToolItem(toolbar, SWT.PUSH | SWT.LEFT_TO_RIGHT);
		removeItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/remove_16x16.png"));
		removeItem.setToolTipText("Remove selected rows");
		removeItem.setText("Remove Row");
		removeItem.setWidth(200);
		removeItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				List<TableRule> selectedRules = getSelectedRules(targetList, targetStack);
				if (selectedRules != null && ( selectedRules.isEmpty() || selectedRules.size() == 0 ))
					return ;
				ICommandCreationListener<RemoveCommand<TableRule>, TableRule> listener = new DecisionTableRemoveRowCommandListener(targetList, targetRuleSet, selectedRules);
				CommandFacade.getInstance().executeRemoval(table.getOwnerProjectName(), table, tableType, listener);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		final ToolItem customItem = createAddColumnToolItem(toolbar);
		customItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/add_16x16.png"));
		customItem.setWidth(200);
		
		// creating Shell and menu for custom actions
		
		final Menu menu = createAddColumnMenu(targetTable, targetRuleSet, tableType);
		
		customItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				menu.setVisible(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		new ToolItem(toolbar, SWT.SEPARATOR);
		
		final ToolItem fitContentItem = new ToolItem(toolbar, SWT.PUSH);
		fitContentItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/fit_content_16x16.png"));
		fitContentItem.setToolTipText("Fit Content");
		fitContentItem.setText("Fit Content");
		fitContentItem.setWidth(200);
		fitContentItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				resizeRows(targetTable);
				resizeColumns(targetTable);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		final ToolItem mergeItem = new ToolItem(toolbar, SWT.CHECK);
		mergeItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/merge_16x16.png"));
		mergeItem.setToolTipText("Merge Rows");
		mergeItem.setSelection(prefStore.getBoolean(PreferenceConstants.AUTO_MERGE_VIEW));
		mergeItem.setText("Merge Rows");
		mergeItem.setWidth(200);
		mergeItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) { 
				stopEditing();
				setMergeCells(targetTable, targetStack, mergeItem.getSelection());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		setMergeCells(targetTable, targetStack, mergeItem.getSelection());
		
		boolean showColumnFilter = prefStore
				.getBoolean(PreferenceConstants.SHOW_COLUMN_FILTER);
		
		final ToolItem showFilterRowItem = new ToolItem(toolbar, SWT.CHECK);
		showFilterRowItem.setImage(GUIHelper.getImage("toggle_filter"));
		showFilterRowItem.setToolTipText("Show Filter Row");
		showFilterRowItem.setText("Show Filter");
		showFilterRowItem.setWidth(200);
		showFilterRowItem.setSelection(showColumnFilter);
		showFilterRowItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				toggleFilterRowVisible(targetTable);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		if(!showColumnFilter){
			toggleFilterRowVisible(targetTable);
		}
		final ToolItem duplicateRuleItem = new ToolItem(toolbar, SWT.PUSH);
		duplicateRuleItem.setImage(DecisionTableUIPlugin.getDefault().getImage("icons/duplicateRule.png"));
		duplicateRuleItem.setToolTipText("Duplicate Selected Rules");
		duplicateRuleItem.setText("Duplicate Rule");
		duplicateRuleItem.setWidth(200);
		duplicateRuleItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				List<TableRule> selectedRules = getSelectedRules(targetList, targetStack);
				ICommandCreationListener<DuplicateCommand, TableRule> listener = new DecisionTableDuplicateRowCommandListener(targetList, targetRuleSet, selectedRules, table.getOwnerProjectName(), table.getPath());
				CommandFacade.getInstance().executeDuplication(table.getOwnerProjectName(), table, tableType, listener);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		boolean showAlias=prefStore.getBoolean(PreferenceConstants.SHOW_ALIAS);
		final ToolItem showTextItem = new ToolItem(toolbar, SWT.CHECK);
		showTextItem.setImage(DecisionTableUIPlugin.getDefault().getImage(
				"icons/string_16x16.png"));
		showTextItem.setToolTipText("Show/hide the text alias");
		showTextItem.setText("Show Text");
		showTextItem.setWidth(200);
		showTextItem.setSelection(showAlias);
		showTextItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopEditing();
				if(TableTypes.DECISION_TABLE.equals(tableType)){
					toggleTextAliasFlag =showTextItem.getSelection();
				}else {
					expToggleTextAliasFlag=showTextItem.getSelection();
				}
				if(showTextItem.getSelection()){
					unregisterConfigAttribute(targetTable);
				}else{
					targetTable.configure();
				}
				targetTable.redraw();
				
			}		
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		if(TableTypes.DECISION_TABLE.equals(tableType)){
			toggleTextAliasFlag =showAlias;
			if(toggleTextAliasFlag)
			unregisterConfigAttribute(targetTable);
		}else {
			expToggleTextAliasFlag=showAlias;
			if(expToggleTextAliasFlag)
			unregisterConfigAttribute(targetTable);
		}
		updateToolBar(toolbar, targetTable, columnHeaderDataProvider);
		
		parentSection.setTextClient(toolbar);
		
	}

    protected void stopEditing() {
		getDecisionTable().commitAndCloseActiveCellEditor();
	}

    private int getInsertLocation(EventList<TableRule> targetList, DTBodyLayerStack<TableRule> targetStack, InsertLocation loc) {
    	int insertIdx = -1;
    	switch (loc) {
    	case BEGINNING:
    		insertIdx = 0;
    		break;
    		
    	case END:
    		insertIdx = -1;
    		break;
    		
		case BEFORE:
		{
			Integer[] selectedRows = getSelectedRows(targetStack);
			if (selectedRows.length != 0) {
				insertIdx = selectedRows[0];
			}
			
			if (insertIdx == targetStack.getSelectionLayer().getRowCount()) {
				insertIdx = -1;
			}
			break;
		}

		case AFTER:
		{
			Integer[] selectedRows = getSelectedRows(targetStack);
			if (selectedRows.length != 0) {
				insertIdx = selectedRows[selectedRows.length-1]+1;
			}
			if (insertIdx == targetStack.getSelectionLayer().getRowCount()) {
				insertIdx = -1;
			}
			break;
		}
			
		default:
			break;
		}
    	// If the rows are filtered, the index will be off - so we need to normalize by getting the index in the event list
    	if (insertIdx >= 0 && targetStack.getBodyDataProvider().getRowCount() > 0 && targetList.size() > 0) {
    		TableRule dpRule = targetStack.getBodyDataProvider().getRowObject(insertIdx);
    		insertIdx = targetList.indexOf(dpRule);
    	}
		
    	return insertIdx;
    }
    
    protected Menu createAddRowMenu(final NatTable targetTable, final TableRuleSet targetRuleSet, final TableTypes tableType, final DTBodyLayerStack<TableRule> targetStack, final EventList<TableRule> targetList) {
    	Shell shell = new Shell();
    	final Menu menu = new Menu(shell, SWT.POP_UP);
    	MenuItem addAboveItem = new MenuItem(menu, SWT.NULL);
    	addAboveItem.setText(getAddAboveLabel());
    	addAboveItem.setData("type", InsertLocation.BEFORE);
    	
    	MenuItem addBelowItem = new MenuItem(menu, SWT.NULL);
    	addBelowItem.setText(getAddBelowLabel());
    	addBelowItem.setData("type", InsertLocation.AFTER);

    	MenuItem addAtBeginningItem = new MenuItem(menu, SWT.NULL);
    	addAtBeginningItem.setText(getAddBeginLabel());
    	addAtBeginningItem.setData("type", InsertLocation.BEGINNING);
    	
    	MenuItem addAtEndItem = new MenuItem(menu, SWT.NULL);
    	addAtEndItem.setText(getAddAtEndLabel());
    	addAtEndItem.setData("type", InsertLocation.END);

    	SelectionListener listener = new SelectionListener() {
    		
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			InsertLocation loc = (InsertLocation) ((MenuItem)e.getSource()).getData("type");
    			int insertIdx = getInsertLocation(targetList, targetStack, loc);
    			ICommandCreationListener<AddCommand<TableRule>, TableRule> listener = new DecisionTableAddRowCommandListener(targetList, targetRuleSet, table, table.getPath(), insertIdx, loc);
    			CommandFacade.getInstance().executeAddRow(table.getOwnerProjectName(), table, tableType, listener);

    			menu.setVisible(false);
    		}
    		
    		@Override
    		public void widgetDefaultSelected(SelectionEvent e) {}
    	};
    	
    	addAboveItem.addSelectionListener(listener);
    	addBelowItem.addSelectionListener(listener);
    	addAtBeginningItem.addSelectionListener(listener);
    	addAtEndItem.addSelectionListener(listener);
    	return menu;
    }
    
	protected Menu createAddColumnMenu(final NatTable targetTable, final TableRuleSet targetRuleSet, final TableTypes tableType) {
        Shell shell = new Shell();
		final Menu menu = new Menu(shell, SWT.POP_UP);
		MenuItem customConditionItem = new MenuItem(menu, SWT.NULL);
		customConditionItem.setText(getCustomConditionLabel());
		customConditionItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String columnName = null;
				int customCounter = 0;
				if(TableTypes.DECISION_TABLE.equals(tableType)){
					customCounter = decTableDataModel.getNextCustomConditionCount();
					columnName = DTConstants.CUSTOM_CONDITION_PREFIX + customCounter;
				} else{
					customCounter = expTableDataModel.getNextCustomConditionCount();
					columnName = DTConstants.CUSTOM_CONDITION_PREFIX + customCounter;
				}

				ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener = 
						new DecisionTableColumnAdditionCommandListener( editor, 
								targetRuleSet,
								targetTable, 
								ColumnType.CUSTOM_CONDITION,
								editor.getTable().getOwnerProjectName(),
								columnName,
								"",
								0,
								false,
								true,
								false
								);
				CommandFacade.getInstance().executeColumnAddition(table.getOwnerProjectName(), table, tableType, columnCreationCommandListener);
				menu.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		MenuItem customActionItem = new MenuItem(menu, SWT.NULL);
		customActionItem.setText(getCustomActionLabel());
		customActionItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//Check if at least one condition is present
				boolean isConditionAvailable = false;
				if (targetRuleSet.getColumns() != null) {
					for(Column column : targetRuleSet.getColumns().getColumn()){
						if(column.getColumnType().equals(ColumnType.CONDITION) || column.getColumnType().equals(ColumnType.CUSTOM_CONDITION)){
							isConditionAvailable = true;
							break;
						}
					}
				}
				if (!isConditionAvailable) {
					String message = Messages.getString("ERROR_ADD_ACTION");
					DecisionTableUIPlugin.showMessageDialog("Error", message, null, editor.getSite().getShell());
					return;
				}

				//Increment counters
				String columnName = null;
				int customCounter = 0;
				if(TableTypes.DECISION_TABLE.equals(tableType)){
					customCounter = decTableDataModel.getNextCustomActionCount();
					columnName = DTConstants.CUSTOM_ACTION_PREFIX + customCounter;
				} else{
					customCounter = expTableDataModel.getNextCustomActionCount();
					columnName = DTConstants.CUSTOM_ACTION_PREFIX + customCounter;
				}

				ICommandCreationListener<AddCommand<ColumnPositionStateMemento>, ColumnPositionStateMemento> columnCreationCommandListener = 
						new DecisionTableColumnAdditionCommandListener( editor, 
								targetRuleSet,
								targetTable, 
								ColumnType.CUSTOM_ACTION,
								editor.getTable().getOwnerProjectName(),
								columnName,
								"",
								0,
								false,
								true,
								false
								);
				CommandFacade.getInstance().executeColumnAddition(table.getOwnerProjectName(), table, tableType, columnCreationCommandListener);
				menu.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        return menu;
    }

	protected String getCustomActionLabel() {
		return "Add Custom Action";
	}

	protected String getCustomConditionLabel() {
		return "Add Custom Condition";
	}

	protected String getAddAboveLabel() {
		return "Add Row Above Selection";
	}

	protected String getAddBelowLabel() {
		return "Add Row Below Selection";
	}

	protected String getAddAtEndLabel() {
		return "Add Row To the End";
	}
	
	protected String getAddBeginLabel() {
		return "Add Row To the Beginning";
	}
	
    protected ToolItem createAddColumnToolItem(ToolBar toolbar) {
        ToolItem customItem = new ToolItem(toolbar, SWT.DROP_DOWN);
		customItem.setText("Add Custom");
		customItem.setToolTipText("Add custom condition or action");
        return customItem;
    }

	public void updateToolBars() {
		updateToolBar(decisionTableToolbar, decisionTableNT, dtColumnHeaderDataProvider);
		updateToolBar(exceptionTableToolbar, exceptionTableNT, etColumnHeaderDataProvider);
	}
	
	public void updateToolBar(ToolBar toolbar, NatTable targetTable, DTHeaderDataProvider columnHeaderDataProvider) {
	    if (targetTable == null) {
	        return;
	    }
		DTColumnHeaderLayerStack<TableRule> columnHeaderLayerStack = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
		if (columnHeaderLayerStack.getColumnCount() == 0 && columnHeaderDataProvider.getColumnCount() == 0) {
			setToolBarDisabled(toolbar);
		} else {
			setToolBarEnabled(toolbar);
		}
	}

	public void setToolBarEnabled(ToolBar toolbar) {
		boolean enableButtons = !editor.isEditorInputReadOnly();
		ToolItem[] toolItems = toolbar.getItems();
		for (int i = 0; i < toolItems.length; i++) {
			toolItems[i].setEnabled(enableButtons);
		}
		//label.setEnabled(true);
	}

	public void setToolBarDisabled(ToolBar toolbar) {
		boolean enableButtons = !editor.isEditorInputReadOnly();
		ToolItem[] toolItems = toolbar.getItems();
		for (int i = 0; i < toolItems.length; i++) {
			if ("Custom".equals(toolItems[i].getText())) {
				toolItems[i].setEnabled(enableButtons);
			} else {
				toolItems[i].setEnabled(false);
			}
		}
		//label.setEnabled(false);
	}
	
	private void setMergeCells(final NatTable targetTable,
			final DTBodyLayerStack<TableRule> targetStack,
			final boolean merge) {
		targetStack.setSpan(merge);
		targetTable.refresh();
	}
	
	protected void toggleFilterRowVisible(NatTable targetTable) {
		targetTable.doCommand(new ToggleFilterRowCommand());
	}


	public void resizeColumns(NatTable natTable) {
		for (int i=0; i < natTable.getColumnCount(); i++) { 
			InitializeAutoResizeColumnsCommand columnCommand = 
					new InitializeAutoResizeColumnsCommand(
							natTable, i, natTable.getConfigRegistry(), 
							new GCFactory(natTable)); 
			natTable.doCommand(columnCommand); 
		} 
	}
	
	public void resizeRows(NatTable natTable) {
		for (int i=0; i < natTable.getRowCount(); i++) { 
			InitializeAutoResizeRowsCommand rowCommand = 
					new InitializeAutoResizeRowsCommand(
							natTable, i, natTable.getConfigRegistry(), 
							new GCFactory(natTable)); 
			natTable.doCommand(rowCommand); 
		} 
	}

	protected void addCustom(TableRuleSet targetRuleSet, NatTable targetTable, ColumnType colType) {
		DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = (DTColumnHeaderLayerStack<TableRule>) ((GridLayer)targetTable.getLayer()).getColumnHeaderLayer();
		Column newColumn = DtmodelFactory.eINSTANCE.createColumn();
		newColumn.setColumnType(colType);
		newColumn.setName("");
		EList<Column> columns = targetRuleSet.getColumns().getColumn();
		int idx = columns.size();
		if (colType == ColumnType.CUSTOM_CONDITION && idx > 0) {
			for (int i=0; i<columns.size(); i++) {
				idx = i;
				Column column = columns.get(i);
				if (column.getColumnType() != ColumnType.CONDITION && column.getColumnType() != ColumnType.CUSTOM_CONDITION) {
					break;
				}
			}
		}
		columns.add(idx, newColumn);
		ColumnGroupHeaderLayer headerLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
		if (newColumn.getColumnType() == ColumnType.CONDITION || newColumn.getColumnType() == ColumnType.CUSTOM_CONDITION) {
			// just reconfigure rather than off-setting each subsequent column
			headerLayer.clearAllGroups();
			IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
			boolean showColumnGroups = prefStore.getBoolean(PreferenceConstants.SHOW_COLUMN_GROUPS);
			if (showColumnGroups) {
				configureColumnGroups(targetRuleSet, columnHeaderLayer);
			}
		} else {
			headerLayer.addColumnsIndexesToGroup(ACTIONS_COLUMN_GROUP, idx);
		}
		headerLayer.fireLayerEvent(new GroupColumnsEvent(headerLayer));

		tableModified();
		targetTable.refresh();
	}

	public void updateEmptyTooltipVisibility() {
		if (emptyTooltipComposite == null) {
			return;
		}
		if (!isTableEmpty() && emptyTooltipComposite.isVisible()) {
			emptyTooltipComposite.setVisible(false);
		} else if (isTableEmpty() && !emptyTooltipComposite.isVisible()) {
			emptyTooltipComposite.setVisible(true);
		}
	}

	private List<TableRule> getSelectedRules(EventList<TableRule> targetList, DTBodyLayerStack<TableRule> targetLayer) {
		Integer[] selectedRows = getSelectedRows(targetLayer);
		List<TableRule> selectedRules = new ArrayList<TableRule>();
		for (int selectedRow : selectedRows) {
			TableRule tableRule = targetList.get(selectedRow);
			TableRule dpRule = targetLayer.getBodyDataProvider().getRowObject(selectedRow);
			if (tableRule != dpRule) {
				System.err.println("not the same");
			}
			selectedRules.add(tableRule);
		}
		return selectedRules;
	}

	public Integer[] getSelectedRows(DTBodyLayerStack<TableRule> targetLayer) {
		List<Integer> selectedRows = new ArrayList<Integer>();
		Set<Range> selectedRowPositions = targetLayer.getSelectionLayer().getSelectedRowPositions();
		if (selectedRowPositions != null && selectedRowPositions.size() > 0) {
			Iterator<Range> iterator = selectedRowPositions.iterator();
			while (iterator.hasNext()) {
				Range range = (Range) iterator.next();
				int start = range.start;
				int end = range.end;
				while (start < end) {
					selectedRows.add(start);
					start++;
				}
			}
		}
		return (Integer[]) selectedRows.toArray(new Integer[selectedRows
				.size()]);
	}
	

	protected IExpansionListener getExpansionListener() {
		if (expansionListener == null) {
			expansionListener = new IExpansionListener() {
				
				@Override
				public void expansionStateChanging(ExpansionEvent e) {
				}
				
				@Override
				public void expansionStateChanged(ExpansionEvent e) {
					updateWeights();
				}
			};
		};
		return expansionListener;
	}

	public ScrolledForm getForm(){
		return managedForm.getForm();
	}
	
	protected void setupToolbarActions() {
		// Show declarations action
		showDeclAction = new Action("showDeclarations", Action.AS_CHECK_BOX) {
			public void run() {
				declSection.setExpanded(showDeclAction.isChecked());
				declSection.setVisible(showDeclAction.isChecked());
				updateWeights();
				if (showDecisionTableAction.isChecked()) {
				} else {
				}
				getForm().reflow(true);
			}
		};
		showDeclAction.setChecked(false);
		showDeclAction.setToolTipText("Show Declarations Section");
		showDeclAction.setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/decl_table.png"));
		getForm().getToolBarManager().add(showDeclAction);
		
		// Show decision table action
		showDecisionTableAction = new Action("showDecisionTable", Action.AS_CHECK_BOX) {
			public void run() {
				decisionTableSection.setExpanded(showDecisionTableAction.isChecked());
				decisionTableSection.setVisible(showDecisionTableAction.isChecked());
				updateWeights();
				if (showDecisionTableAction.isChecked()) {
				} else {
				}
				getForm().reflow(true);
			}
		};
		showDecisionTableAction.setChecked(true);
		showDecisionTableAction.setToolTipText("Show Decision Table Section");
		showDecisionTableAction.setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/decision_table_section.png"));
		getForm().getToolBarManager().add(showDecisionTableAction);
		
		// Show exception table action
		showExceptionTableAction = new Action("showExceptionTable", Action.AS_CHECK_BOX) {
			public void run() {
				exceptionTableSection.setExpanded(showExceptionTableAction.isChecked());
				exceptionTableSection.setVisible(showExceptionTableAction.isChecked());
				updateWeights();
				if (showDecisionTableAction.isChecked()) {
				} else {
				}
				getForm().reflow(true);
			}
		};
		showExceptionTableAction.setChecked(false);
		showExceptionTableAction.setToolTipText("Show Exception Table Section");
		showExceptionTableAction.setImageDescriptor(DecisionTableUIPlugin.getImageDescriptor("icons/exception_table.png"));
		getForm().getToolBarManager().add(showExceptionTableAction);
		getForm().updateToolBar();

	}

	protected void updateWeights() {
		boolean showDecl = showDeclAction.isChecked();
		boolean showDT = showDecisionTableAction.isChecked();
		boolean showET = showExceptionTableAction.isChecked();
		
		boolean declExpanded = declSection.isExpanded();
		boolean dtExpanded = decisionTableSection.isExpanded();
		boolean etExpanded = exceptionTableSection.isExpanded();
		
		// defaults
		int declWeight = 20;
		int dtWeight = 60;
		int etWeight = 15;
		
		if (showDecl && showDT && showET) {
			if (!declExpanded && !dtExpanded) {
				declWeight = 5;
				dtWeight = 5;
				etWeight = 85;
			} else if (!declExpanded) {
				declWeight = 5;
				dtWeight = 25;
				etWeight = 10;
			} else if (!dtExpanded) {
				dtWeight = 5;
				etWeight = 75;
			} else if (!etExpanded) {
				dtWeight = 75;
				etWeight = 5;
			}
			// keep defaults
		} else if (showDecl && showDT) {
			declWeight = 20;
			dtWeight = 80;
			etWeight = 0;
			if (!declExpanded) {
				declWeight = 5;
				dtWeight = 90;
			} else if (!dtExpanded) {
				declWeight = 50;
				dtWeight = 50;
			}
		} else if (showDT && showET) {
			declWeight = 0;
			dtWeight = 60;
			etWeight = 35;
			if (!dtExpanded) {
				dtWeight = 5;
				etWeight = 90;
			} else if (!etExpanded) {
				dtWeight = 90;
				etWeight = 5;
			}
		} else if (showDecl && showET) {
			declWeight = 20;
			dtWeight = 0;
			etWeight = 80;
			if (!declExpanded) {
				declWeight = 5;
				etWeight = 90;
			} else if (!etExpanded) {
				declWeight = 50;
				etWeight = 50;
			}
		} else if (showDecl) {
			declWeight = 95;
			dtWeight = 0;
			etWeight = 0;
		} else if (showDT) {
			declWeight = 0;
			dtWeight = 95;
			etWeight = 0;
		} else if (showET) {
			declWeight = 0;
			dtWeight = 0;
			etWeight = 95;
		}			
		sashForm.setWeights(new int[] { declWeight, dtWeight, etWeight } );

	}

	public void createPartControl(Composite container) {
		//super.createPartControl(container, "Decision Table: "+table.getName(), null);
		createPartControl(container, "Decision Table: ", null);
	}

	protected void createDeclGroup(Composite composite, FormToolkit toolkit) {
		composite.setBackground(GUIHelper.COLOR_WHITE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		org.eclipse.swt.widgets.Table scopeTable = new org.eclipse.swt.widgets.Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		scopeTable.setHeaderVisible(true);
		scopeTable.setLinesVisible(true);
		scopeTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		scopeTable.setLayout(new GridLayout());
		TableColumn col1 = new TableColumn(scopeTable, SWT.NULL);
		col1.setText("Name");
		col1.setWidth(400);

		TableColumn col2 = new TableColumn(scopeTable, SWT.NULL);
		col2.setText("Alias");
		col2.setWidth(300);
		
		TableColumn col3 = new TableColumn(scopeTable, SWT.NULL);
		col3.setText("Array");
		col3.setWidth(200);
		
		String vrfPath = table.getImplements();
		RuleElement ruleElement = IndexUtils.getRuleElement(table.getOwnerProjectName(), vrfPath, ELEMENT_TYPES.RULE_FUNCTION);
		EList<GlobalVariableDef> globalVariables = ruleElement.getGlobalVariables();
		for (GlobalVariableDef varDef : globalVariables) {
			TableItem item = new TableItem(scopeTable, SWT.NULL);
			String path = varDef.getType();
			path = ModelUtils.convertPackageToPath(path);
			String name = varDef.getName();
			String array = varDef.isArray() ? "true" : "false";
			item.setText(new String[] { path, name, array });
		}
	}
	
	private NatTable createNatTableWithGL(Composite composite, final TableRuleSet ruleSet, EventList<TableRule> source, 
																			DTHeaderDataProvider columnHeaderDataProvider, TableTypes tableType) {
		composite.setBackground(GUIHelper.COLOR_WHITE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		ConfigRegistry configRegistry = new ConfigRegistry();

		TransformedList<TableRule, TableRule> rowObjectsGlazedList = GlazedLists.threadSafeList(source);
		FilterList<TableRule> filterList = new FilterList<TableRule>(rowObjectsGlazedList);
		SortedList<TableRule> sortedList = new SortedList<TableRule>(filterList, null);

		IColumnPropertyAccessor<TableRule> columnPropertyAccessor = new DTColumnPropertyAccessor(editor, table, tableType);

		IRowIdAccessor<TableRule> accessor = new IRowIdAccessor<TableRule>() {

			@Override
			public Serializable getRowId(TableRule rule) {
				return rule.getId();
			}
		};
		

		final ColumnGroupModel columnGroupModel = new ColumnGroupModel();
		final DTBodyLayerStack<TableRule> bodyLayer = new DTBodyLayerStack<TableRule>(table.getOwnerProjectName(),
				sortedList, accessor, columnPropertyAccessor, configRegistry, columnGroupModel, ruleSet, this);
		if (tableType == TableTypes.DECISION_TABLE) {
			this.dtBodyLayer = bodyLayer;
		} else {
			this.etBodyLayer = bodyLayer;
		}
				
		boolean autoMerge = prefStore.getBoolean(PreferenceConstants.AUTO_MERGE_VIEW);
		if (autoMerge) {
			bodyLayer.setSpan(true);
		}

		DTColumnHeaderLayerStack<TableRule> columnHeaderLayer = new DTColumnHeaderLayerStack<TableRule>( 
				sortedList,
				filterList,
				columnHeaderDataProvider,
				columnPropertyAccessor, 
				bodyLayer,
				bodyLayer.getSelectionLayer(),
				columnGroupModel,
				configRegistry);

		// Column group for conditions and actions
		boolean showColumnGroups = prefStore.getBoolean(PreferenceConstants.SHOW_COLUMN_GROUPS);
		if (showColumnGroups) {
			configureColumnGroups(ruleSet, columnHeaderLayer);
		}
		
		// Row header
		IDataProvider rowHeaderDataProvider = new DTRowHeaderDataProvider(ruleSet, bodyLayer.getBodyDataProvider());
		DataLayer rowHeaderDataLayer = new DataLayer(rowHeaderDataProvider);
		rowHeaderDataLayer.setDefaultColumnWidth(60);
		rowHeaderDataLayer.setDefaultRowHeight(20);
		ILayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, bodyLayer, bodyLayer.getSelectionLayer());
		rowHeaderLayer.addLayerListener(new ILayerListener() {
			
			@Override
			public void handleLayerEvent(ILayerEvent event) {
				handleRowLayerEvent(event);
			}
		});

		// Corner
		DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(columnHeaderDataProvider, rowHeaderDataProvider) {

			@Override
			public Object getDataValue(int columnIndex, int rowIndex) {
				return "ID";
			}
			
		};
		DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		CornerLayer cornerLayer = new CornerLayer(cornerDataLayer, rowHeaderLayer, columnHeaderLayer);
		// Composite
		GridLayer compositeLayer = new GridLayer(bodyLayer, columnHeaderLayer, rowHeaderLayer, cornerLayer);
		
		
		// Create NatTable
		NatTable natTable = new NatTable(composite, NatTable.DEFAULT_STYLE_OPTIONS | SWT.BORDER, compositeLayer, false);
		natTable.addLayerListener(new ILayerListener() {
			
			@Override
			public void handleLayerEvent(ILayerEvent event) {
				handleRowLayerEvent(event);
			}
		});
		
		// Listener added for DT preferences
		boolean autoResizeColumn = prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN);
		if (autoResizeColumn) {
			natTable.addListener(SWT.Paint, new Listener() {
				boolean colResized=false;
				
				@Override
				public void handleEvent(Event event) {
					if(colResized){
						return;
					}
			        boolean autoResizeColumn = prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_COLUMN);
			        if(autoResizeColumn) {
						resizeColumns(decisionTableNT);	
						colResized = true;
			        }				
				}
			});
		}
		
		boolean autoResizeRow = prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_ROWS);
		if (autoResizeRow) {
			natTable.addListener(SWT.Paint, new Listener() {
				boolean rowResized=false;
				
				@Override
				public void handleEvent(Event event) {
					if(rowResized){
						return;
					}
			        boolean autoResizeRow = prefStore.getBoolean(PreferenceConstants.AUTO_RESIZE_ROWS);
			        if(autoResizeRow) {
			        	resizeRows(decisionTableNT);
			        	rowResized = true;
			        }
					
				}
			});
		}
		
//		ColumnOverrideLabelAccumulator columnLabelAccumulator = new ColumnOverrideLabelAccumulator(bodyLayer.getBodyDataLayer());
//		bodyLayer.getBodyDataLayer().setConfigLabelAccumulator(columnLabelAccumulator);
				
		// One label accumulator for all labels
		DTLabelAccumulator labelAccumulator 
		= new DTLabelAccumulator(bodyLayer, editor, natTable, ruleSet, table.getOwnerProjectName());
		bodyLayer.getBodyDataLayer().setConfigLabelAccumulator(labelAccumulator);
		
//		AggregrateConfigLabelAccumulator aggregrateConfigLabelAccumulator = new AggregrateConfigLabelAccumulator();
//		bodyLayer.getBodyDataLayer().setConfigLabelAccumulator(aggregrateConfigLabelAccumulator);
		
		// Style action columns differently
//		IConfigLabelAccumulator cellLabelAccumulator = new IConfigLabelAccumulator() {
//			public void accumulateConfigLabels(LabelStack configLabels, int columnPosition, int rowPosition) {
//				int columnIndex = bodyLayer.getColumnIndexByPosition(columnPosition);
//				Column column = ruleSet.getColumns().getColumn().get(columnIndex);
//				if (column.getColumnType() == ColumnType.ACTION || column.getColumnType() == ColumnType.CUSTOM_ACTION) {
//					configLabels.addLabel(ACTIONS_GROUP_LABEL);
//				}
//			}
//		};
//		bodyLayer.setConfigLabelAccumulator(cellLabelAccumulator);

//		natTable.addConfiguration(new AbstractRegistryConfiguration() {
//			public void configureRegistry(IConfigRegistry configRegistry) {
//				Style cellStyle = new Style();
//				cellStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, ColorConstants.darkGray);
//				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.NORMAL, ACTIONS_GROUP_LABEL);
//			}
//		});

		styleAndConfigureNatTableWithGL(natTable, configRegistry, ruleSet, bodyLayer, tableType);

		natTable.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL_BOTH);
		data.minimumHeight = 300;
		natTable.setLayoutData(data);
		natTable.configure();
		addDragAndDropSupport(natTable, tableType);
		
		if (emptyTooltipComposite == null && getEmptyTableTooltip() != null) {
			emptyTooltipComposite = new Composite(natTable, SWT.BORDER);
			GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
			gd.widthHint = 250;
			emptyTooltipComposite.setLayoutData(gd);
			GridLayout l = new GridLayout();
			l.marginHeight = 5;
			l.marginWidth = 5;
			emptyTooltipComposite.setLayout(l);
			String borderColor = prefStore.getString(PreferenceConstants.SELECTION_COLOR);
			emptyTooltipComposite.setBackground(GUIHelper.getColor(PreferenceConstants.convertToRGB(borderColor)));
			emptyTooltipComposite.setBackground(GUIHelper.COLOR_DARK_GRAY);
			Label emptyTooltip = new Label(emptyTooltipComposite, SWT.WRAP);
			emptyTooltip.setAlignment(SWT.CENTER);
			emptyTooltip.setLayoutData(new GridData(GridData.FILL_BOTH));
			emptyTooltip.setText(getEmptyTableTooltip());
			String bgColor = prefStore.getString(PreferenceConstants.HEADER_BACK_GROUND_COLOR);
			String fgColor = prefStore.getString(PreferenceConstants.HEADER_FORE_GROUND_COLOR);
			emptyTooltip.setBackground(GUIHelper.getColor(PreferenceConstants.convertToRGB(bgColor)));
			emptyTooltip.setForeground(GUIHelper.getColor(PreferenceConstants.convertToRGB(fgColor)));
			FontData fd = new FontData(prefStore.getString(PreferenceConstants.COLUMN_HEADER_FONT));
			fd.setHeight(fd.getHeight()+4);
			Font font = GUIHelper.getFont(fd);
			emptyTooltip.setFont(font);
		}
		
		if (emptyTooltipComposite != null) {
			if (isTableEmpty()) {
				emptyTooltipComposite.setVisible(true);
			} else {
				emptyTooltipComposite.setVisible(false);
			}
		}
		return natTable;
	}

	protected String getEmptyTableTooltip() {
		return "\nTo add a new column, expand the Decision Table in the Studio Explorer view and drag a Concept/Event property to the canvas.  Alternatively, add a Custom Condition by using the \"Add Custom\" button above\n";
	}

	private boolean isTableEmpty() {
		ArrayList<String> columnNames = getColumnNames(TableTypes.DECISION_TABLE);
		if (columnNames != null && columnNames.size() > 0) {
			return false;
		}
		columnNames = getColumnNames(TableTypes.EXCEPTION_TABLE);
		if (columnNames != null && columnNames.size() > 0) {
			return false;
		}
		return true;
	}

	public ColumnGroupHeaderLayer configureColumnGroups(
			final TableRuleSet ruleSet,
			DTColumnHeaderLayerStack<TableRule> columnHeaderLayer) {
		ColumnGroupHeaderLayer columnGroupHeaderLayer = columnHeaderLayer.getColumnGroupHeaderLayer();
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			EList<Column> column = columns.getColumn();
			for (int i=0; i<column.size(); i++) {
				Column dtCol = column.get(i);
				if (dtCol.getColumnType() == ColumnType.CONDITION || dtCol.getColumnType() == ColumnType.CUSTOM_CONDITION) {
					columnGroupHeaderLayer.addColumnsIndexesToGroup(CONDITIONS_COLUMN_GROUP, i);
				} else {
					columnGroupHeaderLayer.addColumnsIndexesToGroup(ACTIONS_COLUMN_GROUP, i);
				}
			}
		}
		return columnGroupHeaderLayer;
	}

	/* This method creates a NatTable without Glazed Lists integration */
	@SuppressWarnings("unused")
	private void createNatTable(Composite composite, final TableRuleSet ruleSet, DTHeaderDataProvider columnHeaderDataProvider, TableTypes tableType) {
		composite.setBackground(GUIHelper.COLOR_WHITE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		ConfigRegistry configRegistry = new ConfigRegistry();
		IColumnPropertyAccessor<TableRule> columnPropertyAccessor = new DTColumnPropertyAccessor(editor, table, tableType);
		
		final ListDataProvider<TableRule> bodyDataProvider = new ListDataProvider<TableRule>(ruleSet.getRule(), columnPropertyAccessor);
		
//		final DTDataProvider bodyDataProvider = new DTDataProvider(table);
		DataLayer bodyDataLayer = new DataLayer(bodyDataProvider);
		bodyDataLayer.setDefaultRowHeight(20);
		bodyDataLayer.setDefaultColumnWidth(125);
		
        RowReorderLayer rowReorderLayer =
                new RowReorderLayer(bodyDataLayer);
//		DefaultBodyLayerStack bodyLayerStack = new DefaultBodyLayerStack(bodyDataLayer);

//		SelectionLayer selectionLayer = new SelectionLayer(new ColumnHideShowLayer(new ColumnReorderLayer(bodyDataLayer)));
//		ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);
		
		// Column header
		columnHeaderDataProvider = new DTHeaderDataProvider(ruleSet);
		DataLayer columnHeaderDataLayer = new DataLayer(columnHeaderDataProvider);
		columnHeaderDataLayer.setDefaultRowHeight(25);
		columnHeaderDataLayer.setDefaultColumnWidth(175);

		ColumnGroupModel columnGroupModel = new ColumnGroupModel();
		ColumnGroupBodyLayerStack bodyLayer = new ColumnGroupBodyLayerStack(rowReorderLayer, columnGroupModel);
		ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, bodyLayer, bodyLayer.getSelectionLayer());

		// Column group for conditions and actions
		ColumnGroupHeaderLayer columnGroupHeaderLayer = new ColumnGroupHeaderLayer(columnHeaderLayer, bodyLayer.getSelectionLayer(), columnGroupModel);
		Columns columns = ruleSet.getColumns();
		if (columns != null) {
			EList<Column> column = columns.getColumn();
			for (int i=0; i<column.size(); i++) {
				Column dtCol = column.get(i);
				if (dtCol.getColumnType() == ColumnType.CONDITION || dtCol.getColumnType() == ColumnType.CUSTOM_CONDITION) {
					columnGroupHeaderLayer.addColumnsIndexesToGroup(CONDITIONS_COLUMN_GROUP, i);
				} else {
					columnGroupHeaderLayer.addColumnsIndexesToGroup(ACTIONS_COLUMN_GROUP, i);
				}
			}
		}
		
		// Row header
//		DefaultRowHeaderDataProvider rowHeaderDataProvider2 = new DefaultRowHeaderDataProvider(bodyDataProvider);
		IDataProvider rowHeaderDataProvider = new DTRowHeaderDataProvider(ruleSet, bodyDataProvider);
		DataLayer rowHeaderDataLayer = new DataLayer(rowHeaderDataProvider);
		rowHeaderDataLayer.setDefaultColumnWidth(60);
		rowHeaderDataLayer.setDefaultRowHeight(20);
		ILayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, bodyLayer, bodyLayer.getSelectionLayer());
		// Corner
		DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(columnHeaderDataProvider, rowHeaderDataProvider) {

			@Override
			public Object getDataValue(int columnIndex, int rowIndex) {
				return "ID";
			}
			
		};
		DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		CornerLayer cornerLayer = new CornerLayer(cornerDataLayer, rowHeaderLayer, columnGroupHeaderLayer);
		
		// Composite
		GridLayer compositeLayer = new GridLayer(bodyLayer, columnGroupHeaderLayer, rowHeaderLayer, cornerLayer);
		
		// Create NatTable
		NatTable natTable = new NatTable(composite, NatTable.DEFAULT_STYLE_OPTIONS | SWT.BORDER, compositeLayer, false);
		//natTable.addDropSupport( DND.DROP_COPY, null, new DefaultDropHandler(null));
		styleAndConfigureNatTable(natTable, configRegistry, bodyDataProvider, bodyLayer.getColumnGroupExpandCollapseLayer(), ruleSet, tableType);
		
		natTable.setLayout(new GridLayout());
		natTable.addLayerListener(new ILayerListener() {
			
			@Override
			public void handleLayerEvent(ILayerEvent event) {
				handleRowLayerEvent(event);
			}
		});
		GridData data = new GridData(GridData.FILL_BOTH);
		data.minimumHeight = 300;
		natTable.setLayoutData(data);
		addDragAndDropSupport(natTable, tableType);
	}
	
	protected void handleRowLayerEvent(ILayerEvent event) {
		// default impl does nothing
	}

	private void styleAndConfigureNatTableWithGL(NatTable natTable, ConfigRegistry configRegistry, 
																	TableRuleSet ruleSet, 
																	DTBodyLayerStack<TableRule> bodyLayer, TableTypes tableType) {
		natTable.addConfiguration(getBodyStyleConfiguration(ruleSet, bodyLayer, tableType));
		natTable.addConfiguration(new DTCellDoubleClickConfiguration(ruleSet, editor, bodyLayer));
		styleAndConfigureNatTable(natTable, configRegistry, bodyLayer.getBodyDataProvider(), 
																bodyLayer.getColumnGroupExpandCollapseLayer(), ruleSet, tableType);
	}

	protected DTBodyStyleConfiguration getBodyStyleConfiguration(
			TableRuleSet ruleSet, DTBodyLayerStack<TableRule> bodyLayer,
			TableTypes tableType) {
		return new DTBodyStyleConfiguration(bodyLayer, editor, ruleSet,tableType);
	}
	
	public String openExpressionEditor(TableRuleVariable ruleVariable, NatTable natTable, Point location) {
		return null;
	}

	private void styleAndConfigureNatTable(NatTable natTable, ConfigRegistry configRegistry, 
															ListDataProvider<TableRule> bodyDataProvider, 
															ColumnGroupExpandCollapseLayer columnGroupExpandCollapseLayer, 
															TableRuleSet ruleSet, TableTypes tableType) {
		// add copy/paste/etc
		natTable.addConfiguration(new DefaultSelectionBindings());

		// zoom in/out
		natTable.addConfiguration(new ZoomLevelConfiguration(natTable, editor));
		
		attachToolTip(natTable);
//		natTable.setLayerPainter(new NatGridLayerPainter(natTable));
		natTable.setConfigRegistry(configRegistry);
		
		// add configuration for context menu here
		natTable.addConfiguration(new ContextMenuConfiguration(natTable,editor));
		
		natTable.addConfiguration(new SingleClickSortConfiguration());
		
		// Add popup menu - build your own popup menu using the PopupMenuBuilder
		//natTable.addConfiguration(new HeaderMenuConfiguration(natTable));
		natTable.addConfiguration(createDTHeaderMenuConfiguration(natTable, columnGroupExpandCollapseLayer, tableType));
		
		// Add default selection configuration (overridden in row/header configs)
		natTable.addConfiguration(new DTSelectionStyleConfiguration());

		styleAndConfigureNatTableHeaders(natTable);
		
		// disabled for now
//		registerEditValidator(configRegistry, bodyDataProvider, ruleSet);
		
		natTable.configure();
	}

    protected DTHeaderMenuConfiguration createDTHeaderMenuConfiguration(NatTable natTable, ColumnGroupExpandCollapseLayer columnGroupExpandCollapseLayer,
                                                                        TableTypes tableType) {
        return new DTHeaderMenuConfiguration(editor, natTable, columnGroupExpandCollapseLayer, tableType);
    }

    protected void styleAndConfigureNatTableHeaders(NatTable natTable) {
        // Column/Row header style and custom painters
		natTable.addConfiguration(new DTRowHeaderStyleConfiguration());
		natTable.addConfiguration(new DTColumnHeaderStyleConfiguration());
    }
	
	@SuppressWarnings("unused")
	private void registerEditValidator(IConfigRegistry configRegistry, ListDataProvider<TableRule> bodyDataProvider, TableRuleSet ruleSet) {

		TextCellEditor textCellEditor = new TextCellEditor();
		textCellEditor.setErrorDecorationEnabled(true);
		textCellEditor.setErrorDecorationText("Invalid expression");
//		textCellEditor.setInputValidationErrorHandler(new IEditErrorHandler() {
//			
//			@Override
//			public void removeError(ICellEditor cellEditor) {
//				
//			}
//			
//			@Override
//			public void displayError(ICellEditor cellEditor, Exception e) {
//				System.err.println("Got an error: "+e);
//			}
//		});
		textCellEditor.setDecorationPositionOverride(SWT.RIGHT | SWT.TOP);		
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, textCellEditor, DisplayMode.NORMAL, GridRegion.BODY);
		
		DecisionTableDataValidator validator = getTableValidator(bodyDataProvider, ruleSet);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, validator, DisplayMode.EDIT);
		
	}

	private DecisionTableDataValidator getTableValidator(final ListDataProvider<TableRule> bodyDataProvider, TableRuleSet ruleSet) {
		return new DecisionTableDataValidator(table, ruleSet, bodyDataProvider);
	}
	
	private void attachToolTip(NatTable natTable) {
		DefaultToolTip toolTip = new DecisionTableToolTip(natTable, editor);
		toolTip.setBackgroundColor(natTable.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		toolTip.setPopupDelay(300);
		toolTip.activate();
		toolTip.setShift(new Point(10, 10));
	}
	
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Returns the data/time format preferred by this viewer.
	 */
	public SimpleDateFormat getDateFormat() {
	    return sdf;
	}
	
	/**
	 * Enable the table to receive dropped elements
	 */
	private void addDragAndDropSupport(NatTable natTable, TableTypes tableType) {
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		Transfer[] transferTypes = new Transfer[] {TextTransfer.getInstance()};
		DropTargetListener dropListener = editor.createDropTargetListener(natTable, tableType);
		natTable.addDropSupport(operations, transferTypes, dropListener);
	}
	
	public void dispose() {
		prefStore.removePropertyChangeListener(this);
		if (managedForm!= null){
			managedForm.dispose();
		}
		editor = null;
		declSection = null;
		decisionTableSection = null;
		exceptionTableSection = null;
		decisionTableNT.dispose();
		if (exceptionTableNT != null) {
		    exceptionTableNT.dispose();
		}
	}

	public NatTable getDecisionTable() {
		return decisionTableNT;
	}
	
	public NatTable getExceptionTable() {
		return exceptionTableNT;
	}
	
	public IDecisionTableEditor getEditor() {
		return editor;
	}
	
	public ArrayList<String> getColumnNames(TableTypes tableType) {
		return tableType == TableTypes.DECISION_TABLE ? dtColumnHeaderDataProvider.getColumnNames() : new ArrayList();
	}
	
	public EventList<TableRule> getDecisionTableEventList() {
		return decisionTableEventList;
	}
	
	public EventList<TableRule> getExceptionTableEventList() {
		return exceptionTableEventList;
	}
	
	public DTBodyLayerStack<TableRule> getDtBodyLayer() {
		return dtBodyLayer;
	}

	public DTBodyLayerStack<TableRule> getEtBodyLayer() {
		return etBodyLayer;
	}

	public void setToggleMergeButton (boolean autoMerge) {
		if (decisionTableNT != null  && null!=exceptionTableNT) {
			toggleFilterRowVisible(decisionTableNT);
			toggleFilterRowVisible(exceptionTableNT);
			if(null!=decisionTableToolbar && null!= exceptionTableToolbar){
				toggleSelection(autoMerge,decisionTableToolbar.getItems());
				toggleSelection(autoMerge,exceptionTableToolbar.getItems());
			}
		}
	}
	public void setToggleColumnFilterButton(boolean showColumnFilter) {
		if (decisionTableNT != null  && null!=exceptionTableNT) {
			toggleFilterRowVisible(decisionTableNT);
			toggleFilterRowVisible(exceptionTableNT);
			if(null!=decisionTableToolbar && null!= exceptionTableToolbar){
				toggleSelection(showColumnFilter,decisionTableToolbar.getItems());
				toggleSelection(showColumnFilter,exceptionTableToolbar.getItems());
			}
		}
	}

	private void toggleSelection(boolean toggle, ToolItem[] toolItems) {
		if(null!=toolItems && toolItems.length>0){
			for (ToolItem toolItem : toolItems) {
				
				if(null!=toolItem){
					if("Show Filter".equals(toolItem.getText())){
						toolItem.setSelection(toggle);
					}else if("Merge rows".equals(toolItem.getText())){
						toolItem.setSelection(toggle);
					}
				}
			}
		}
	}


	public boolean isToggleTextAliasFlag() {
		return toggleTextAliasFlag;
	}

	public boolean isExpToggleTextAliasFlag() {
		return expToggleTextAliasFlag;
	}
	private void unregisterConfigAttribute(final NatTable targetTable) {
		targetTable.getConfigRegistry().unregisterConfigAttribute(CellConfigAttributes.CELL_PAINTER, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
		targetTable.getConfigRegistry().unregisterConfigAttribute(CellConfigAttributes.DISPLAY_CONVERTER, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);
		targetTable.getConfigRegistry().unregisterConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.NORMAL, DecisionTableDesignViewer.CHECK_BOX_CONFIG_LABEL);		
		targetTable.getConfigRegistry().unregisterConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, DisplayMode.EDIT);
	}
	public void updateAppearance(){
		if (decisionTableNT != null  && null!=exceptionTableNT) {
			decisionTableNT.configure();
			decisionTableNT.refresh();
			exceptionTableNT.configure();
		}
	}

	public Control getControl() {
		return getForm();
	}
	
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getProperty().startsWith(PreferenceConstants.APPEARANCE_PREFIX)) { 
            NatTable natTable = getDecisionTable();
            natTable.configure();
            resizeRows(natTable);
            resizeColumns(natTable);
            natTable.refresh();
        }
    }

}
