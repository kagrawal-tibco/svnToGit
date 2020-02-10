package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getActionColumns;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getColumn;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getConditionColumns;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getPropertyIcon;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.removeFromCellToDisableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.diff.DiffHelper;
import com.tibco.cep.webstudio.client.diff.DiffHelper.DTDiffType;
import com.tibco.cep.webstudio.client.diff.MergedDiffHelper;
import com.tibco.cep.webstudio.client.diff.MergedDiffModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationType;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.editor.TableDataGrid;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.i18n.CustomizedSmartgwtMessages;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * This initializes Decision Table, Create Configuration, ToolStrip etc. 
 * @author sasahoo
 *
 */
public abstract class AbstractTableEditor extends AbstractEditor implements com.smartgwt.client.widgets.events.ClickHandler, 
                                                                            ClickHandler, 
                                                                            EditorEnterHandler, 
                                                                            EditCompleteHandler, 
                                                                            EditorExitHandler, 
                                                                            RecordDropHandler, 
                                                                            FilterEditorSubmitHandler {

	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	protected GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected CustomizedSmartgwtMessages customSgwtMsgBundle= (CustomizedSmartgwtMessages) I18nRegistry.getResourceBundle(I18nRegistry.CUSTOM_SGWT_MESSAGES);
	
	public static final int TOOL_BAR_HEIGHT = 20;
	public static final int PAGES_WINDOW = 10;

	public static final String TOOL_PAGES_BUTTON_GROUP = "TOOL_PAGES_BUTTON_GROUP";

	protected DecisionTablePropertyTab propertyTab;

	protected TableDataGrid dtTable;
	protected TableDataGrid expTable;
	protected TableDataGrid declTable;
	protected SectionStackSection dtSection;

	private ToolStrip editorToolStrip;   
	protected ToolStrip dtToolStrip;
	protected ToolStrip expToolStrip;

	protected ToolStripButton showTableAnalyzerViewButton;
	protected ToolStripButton showPropertiesViewButton;
	protected ToolStripButton refreshButton;

	protected Menu dtCustomMenu;
	protected Menu expCustomMenu;
	protected MenuItem dtCustomCondItem;
	protected MenuItem dtCustomActionItem;
	
	protected Menu dtFilterMenu;
	protected Menu expFilterMenu;
	protected MenuItem dtFilterItem;
	protected MenuItem dtCustomFilterItem;
	protected MenuItem dtCustomFilterBuilderItem;
	protected MenuItem dtNoFilterItem;
	
	protected MenuItem expCustomCondItem;
	protected MenuItem expCustomActionItem;

	//Decision Table Column/Field Indices 
	protected List<Integer> dtConditionAreaIndices = new ArrayList<Integer>();
	protected List<Integer> dtActionAreaIndices = new ArrayList<Integer>();
	
	protected Map<Integer, String> dtConditionAreaFieldMap = new HashMap<Integer, String>();
	protected Map<Integer, String> dtActionAreaFieldMap = new HashMap<Integer, String>();

	protected Map<Integer, TableColumn> dtConditionAreaColumns = new HashMap<Integer, TableColumn>();
	protected Map<Integer, TableColumn> dtActionAreaColumns = new HashMap<Integer, TableColumn>();
	//--------------------------------
	
	//Exception Table Column/Field Indices
	protected List<Integer> expConditionAreaIndices = new ArrayList<Integer>();
	protected List<Integer> expActionAreaIndices = new ArrayList<Integer>();

	protected Map<Integer, TableColumn> expConditionAreaColumns = new HashMap<Integer, TableColumn>();
	protected Map<Integer, TableColumn> expActionAreaColumns = new HashMap<Integer, TableColumn>();
	
	protected Map<Integer, String> expConditionAreaFieldMap = new HashMap<Integer, String>();
	protected Map<Integer, String> expActionAreaFieldMap = new HashMap<Integer, String>();

	
	protected Map<String, Boolean> dtFieldDomAssocMap = new HashMap<String, Boolean>();
	protected Map<String, Boolean> expFieldDomAssocMap = new HashMap<String, Boolean>();
	
    //---------------------------------
	
	protected Table table;
	
	protected String projectName;

	protected DecisionTableDataSource decisionTableDataSource;
	protected TableGridEditorCustomizer decisionTableEditorcustomizer;

	protected DecisionTableDataSource exceptionTableDataSource;
	protected TableGridEditorCustomizer exceptionTableEditorcustomizer;
	
	protected String tableName;
	protected String folder;
	protected String rfimplements;

	protected List<ArgumentData> argumentList;

	protected DecisionTableAnalyzerPane decisionTableAnalyzerPane;
	protected boolean isAnalyzed = false;
	
	protected int currentPage = 0;
	
	protected long dtlastRecordId = 0; 
	protected long explastRecordId = 0;
	
	protected ToolStrip pageToolStrip;
	protected HStack pagesContainer;
	protected ToolStripButton prevDotlbl;
	protected ToolStripButton nextDotlbl;
	protected int pages = 0;
	protected ToolStripButton firstbutton;
	protected ToolStripButton prevbutton;
	protected ToolStripButton nextbutton;
	protected ToolStripButton lastbutton;
	protected ToolStripButton pagesLabel;
	protected ToolStripButton pageLabel;
	protected DynamicForm gotoPageForm;
	protected TextItem gotoPageTextItem;
	
	protected SectionStackSection declSection;
	protected SectionStackSection expSection;

	protected ToolStripButton dtAddbutton;
	protected ToolStripButton dtRemovebutton;
	protected ToolStripButton dtDupeRowbutton;
	protected ToolStripButton dtShowAllbutton;

	protected ToolStripButton expAddRowbutton;
	protected ToolStripButton expRemoveRowbutton;
	protected ToolStripButton expDupeRowbutton;
	
	protected MenuItem expFilterItem;
	protected MenuItem expCustomFilterItem;
	protected MenuItem expCustomFilterBuilderItem;
	protected MenuItem expNoFilterItem;
	
	protected Map<Integer, ToolStripButton> toolStripButtonMap = new HashMap<Integer, ToolStripButton>();

	protected DecisionTableUpdateProvider decisionTableUpdateprovider = null;
	protected DecisionTableUpdateProvider exceptionTableUpdateprovider = null;
	
	protected Map<Integer, List<String>> rulesToHighlight = null;
	
	private boolean showTableAnalyzer = false;
	private boolean showProperties = false;
	
	protected boolean isHeaderGroupSupported = true;
	
	private String condinlineStart = "<div style=\"text-align:center\"><span style=\"color: #1F0CF2;\">";
	private String actinlineStart = "<div style=\"text-align:center\"><span style=\"color: #053A73;\">";
	private String inlineEnd = "</span></div>";
	
	protected HeaderSpan dtIdSpan = new HeaderSpan("", new String[]{"id"}); 
	protected HeaderSpan dtConditionSpan = new HeaderSpan(condinlineStart + "<b>" + dtMessages.dt_conditions_spanHeader() + "</b>" + inlineEnd, new String[]{});
	protected HeaderSpan dtActionSpan = new HeaderSpan(actinlineStart + "<b>" + dtMessages.dt_actions_spanHeader() + "</b>" + inlineEnd, new String[]{});
	
	protected HeaderSpan expIdSpan = new HeaderSpan("", new String[]{"id"}); 
	protected HeaderSpan expConditionSpan = new HeaderSpan(condinlineStart + "<b>" + dtMessages.dt_conditions_spanHeader() + "</b>" + inlineEnd, new String[]{});
	protected HeaderSpan expActionSpan = new HeaderSpan(actinlineStart + "<b>" + dtMessages.dt_actions_spanHeader() + "</b>" + inlineEnd, new String[]{});
	
	protected HashMap<String, String> fDtTableRowCacheMap = new HashMap<String, String>();
	protected HashMap<String, String> fExpTableRowCacheMap = new HashMap<String, String>();
	
	protected Map<Integer, List<Integer>> fCellsToHighlightMap = new HashMap<Integer, List<Integer>>();
	protected List<Integer> fRowsToHighlightOnPage = new ArrayList<Integer>();
	
	protected Map<String, Set<String>> fDtTableCellsToDisableMap = new HashMap<String, Set<String>>();
	protected Map<String, Set<String>> fExpTableCellsToDisableMap = new HashMap<String, Set<String>>();
	
	protected boolean showCellHighlight = false;

	private boolean isDecisionTablePageDirty = false;
	private boolean isExceptionTableDirty = false;
	
	private int dt_currentColumnMaxId = -1;
	private int exp_currentMaxColumnId = -1;
	
	/**
	 * Map used to store modifications in DT.<br/> Key format - <br/>ColumnModifications : 'column< column id >'<br/>
	 * RuleModifications : 'rule< rule id >' <br/> RuleVariableModification: 'ruleCell< cond/act_id >'
	 */
	protected Map<String, ModificationEntry> modifications = new LinkedHashMap<String, ModificationEntry>();
	
	public AbstractTableEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataOnStartUp, boolean artifactVersionDiff) {
		super(selectedRecord, true, isReadOnly, revisionId, loadDataOnStartUp, artifactVersionDiff);
	}

	public AbstractTableEditor(NavigatorResource record, boolean loadDataAtStartup) {
		super(record, loadDataAtStartup);
	}

	public AbstractTableEditor(NavigatorResource record) {
		super(record);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	@Override
	protected SectionStackSection[] getSections() {
		return new SectionStackSection[] {
				createDeclarationTableSection(), 
				createDecisionTableSection(), 
				createExceptionTableSection()};
	}

	protected SectionStackSection createDeclarationTableSection() {
		declSection = new SectionStackSection(dtMessages.declarationTable());  
		declSection.setExpanded(false);  
		declSection.setCanCollapse(true);
		declTable = new TableDataGrid(this, false, false) {
			@Override  
			protected MenuItem[] getHeaderContextMenuItems(final Integer fieldNum) {
				return getGeneralHeaderContextMenuItems(super.getHeaderContextMenuItems(fieldNum));
			}
		};
		declTable.setCanEdit(false);
		declTable.setCanGroupBy(false);
		declTable.setCanFreezeFields(false);
		declTable.setWidth100();
		declTable.setHeight(100);
		declTable.setShowResizeBar(false);
		declSection.setItems(declTable);
		declSection.setHidden(true);
		return declSection;
	}
	
	protected SectionStackSection createDecisionTableSection() {
		dtSection = new SectionStackSection(dtMessages.decisionTable());  
		dtSection.setExpanded(true);  
		dtSection.setCanCollapse(true);
		dtTable = createNewTable(true);	
		dtSection.setControls(getDecisionTableToolBar());
		pageToolStrip = createPagesControl();
		dtSection.setItems(dtTable, pageToolStrip);
		return dtSection;
	}

	protected SectionStackSection createExceptionTableSection() {
		expSection = new SectionStackSection(dtMessages.exceptionTable());  
		expSection.setHidden(true);
		expSection.setExpanded(false);  
		expSection.setCanCollapse(true);
		expTable = createNewTable(false);
		expSection.setControls(getExceptionTableToolBar());
		expSection.setItems(expTable);
		return expSection;
	}
	
	/**
	 * @param items
	 * @return
	 */
	private MenuItem[] getGeneralHeaderContextMenuItems(MenuItem[] items) {
		List<MenuItem> menuItemList =  new ArrayList<MenuItem>();
		List<String> menuTitles = new ArrayList<String>();
		menuTitles.add(customSgwtMsgBundle.listGrid_configureSortText());
	//	menuTitles.add("Columns");
		
		for (MenuItem mitem : items) {
			if (mitem.getTitle() == null 
					|| menuTitles.contains(mitem.getTitle().trim())) {
				continue;
			}
			menuItemList.add(mitem);
		}
		return menuItemList.toArray(new MenuItem[menuItemList.size()]);
	}
	
	/**
	 * @param fieldNum
	 * @param items
	 * @param isDecisionTable
	 * @return
	 */
	private MenuItem[] updateHeaderContextMenuItems(final Integer fieldNum, MenuItem[] items, final boolean isDecisionTable) {
		if (fieldNum > 0) {
			items = getGeneralHeaderContextMenuItems(items);
			if (isReadOnly()) {
				return items;
			}
			
			MenuItem removeFieldItem = new MenuItem(dtMessages.dtRemoveRow());
			removeFieldItem.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
			removeFieldItem.addClickHandler(new ClickHandler() {
				/* (non-Javadoc)
				 * @see com.smartgwt.client.widgets.menu.events.ClickHandler#onClick(com.smartgwt.client.widgets.menu.events.MenuItemClickEvent)
				 */
				@Override
				public void onClick(MenuItemClickEvent event) {
					if (isDecisionTable) { 
						removeTableColumn(dtTable, fieldNum, table.getDecisionTable());
					} else {
						removeTableColumn(expTable, fieldNum, table.getExceptionTable());	
					}
				}
			});
			
			Menu shiftColumnsSubMenu = new Menu();
			shiftColumnsSubMenu.setTitle(dtMessages.dt_moveColumn_menu());
			
			MenuItem moveColumnToBeginning = new MenuItem(dtMessages.dt_moveColumn_to_beginning());
			moveColumnToBeginning.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					moveColumn(fieldNum, isDecisionTable, false, true);
				}
			});
			shiftColumnsSubMenu.addItem(moveColumnToBeginning);
			
			MenuItem moveColumnLeft = new MenuItem(dtMessages.dt_moveColumn_to_left());
			moveColumnLeft.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					moveColumn(fieldNum, isDecisionTable, false, false);
				}
			});
			shiftColumnsSubMenu.addItem(moveColumnLeft);
			
			MenuItem moveColumnRight = new MenuItem(dtMessages.dt_moveColumn_to_right());
			moveColumnRight.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					moveColumn(fieldNum, isDecisionTable, true, false);
				}
			});
			shiftColumnsSubMenu.addItem(moveColumnRight);
			
			MenuItem moveColumnToEnd = new MenuItem(dtMessages.dt_moveColumn_to_end());
			moveColumnToEnd.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(MenuItemClickEvent event) {
					moveColumn(fieldNum, isDecisionTable, true, true);
				}
			});
			shiftColumnsSubMenu.addItem(moveColumnToEnd);
			
			MenuItem moveFieldMenu = new MenuItem(dtMessages.dt_moveColumn_menu());
			moveFieldMenu.setSubmenu(shiftColumnsSubMenu);
			
			MenuItem fieldSettingsItem = new MenuItem(dtMessages.dt_fieldSettings_menu());
//			fieldSettingsItem.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
			fieldSettingsItem.addClickHandler(new ClickHandler() {
				/* (non-Javadoc)
				 * @see com.smartgwt.client.widgets.menu.events.ClickHandler#onClick(com.smartgwt.client.widgets.menu.events.MenuItemClickEvent)
				 */
				@Override
				public void onClick(MenuItemClickEvent event) {
					ListGridField columnField = isDecisionTable ? dtTable.getField(fieldNum) : expTable.getField(fieldNum);
					TableColumn column = getColumn(isDecisionTable? table.getDecisionTable().getColumns()
							: table.getExceptionTable().getColumns(), columnField.getName());
					
					new DecisionTableFieldSettingsDialog((AbstractDecisionTableEditor) AbstractTableEditor.this, columnField, column).show();
				}
			});
			
			MenuItemSeparator removeSeparator = new MenuItemSeparator();
			MenuItem[] newItems = new MenuItem[items.length + 4];

			for (int i = 0; i < items.length; i++) {
				MenuItem item = items[i];
				newItems[i] = item;
			}
			newItems[items.length] = moveFieldMenu;
			newItems[items.length + 1] =  removeSeparator;
			newItems[items.length + 2] = removeFieldItem;
			newItems[items.length + 3] = fieldSettingsItem;
			
			return newItems;
		}
		return items;
	}
	
	protected ToolStrip getDecisionTableToolBar() {
		dtToolStrip = new ToolStrip();   
		dtToolStrip.setAutoWidth();
		dtToolStrip.setHeight(TOOL_BAR_HEIGHT);
		dtToolStrip.setAlign(Alignment.LEFT); 
		dtToolStrip.setBorder("0px");
		
		dtAddbutton = new ToolStripButton();
		dtAddbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
		dtAddbutton.setTitle(globalMsg.button_add());
//		dtAddbutton.setTitleStyle("ws-dt-toolstrip-addbutton-title");
		dtAddbutton.setTooltip(globalMsg.button_add());
		dtAddbutton.setShowRollOver(false);
		dtAddbutton.addClickHandler(this);
		dtToolStrip.addButton(dtAddbutton);

		dtRemovebutton = new ToolStripButton();
		dtRemovebutton.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
		dtRemovebutton.setTitle(dtMessages.dtRemoveRow());
//		dtRemovebutton.setTitleStyle("ws-dt-toolstrip-addbutton-title");
		dtRemovebutton.setTooltip(dtMessages.dtRemoveRow());
		dtRemovebutton.setShowRollOver(false);
		dtRemovebutton.addClickHandler(this);
		dtRemovebutton.setDisabled(true);

		dtToolStrip.addButton(dtRemovebutton);

		dtDupeRowbutton = new ToolStripButton();
		dtDupeRowbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "duplicateRule.png");
		dtDupeRowbutton.setTitle(dtMessages.dtDuplicateRow());
		dtDupeRowbutton.setTooltip(dtMessages.dtDuplicateRowToolTip());
		dtDupeRowbutton.setShowRollOver(false);
		dtDupeRowbutton.addClickHandler(this);
		dtDupeRowbutton.setDisabled(true);
		dtToolStrip.addButton(dtDupeRowbutton);
		
		dtToolStrip.addSeparator();

		if (!isReadOnly()) {
			dtCustomMenu = new Menu();   
			dtCustomMenu.setShowShadow(true);   
			dtCustomMenu.setShadowDepth(3);   

			dtCustomCondItem = createMenuItem(dtMessages.button_custom_add_cond(), "add.png");   
			dtCustomCondItem.addClickHandler(this);

			dtCustomActionItem = createMenuItem(dtMessages.button_custom_add_action(), "add.png");
			dtCustomMenu.setItems(dtCustomCondItem, dtCustomActionItem);
			dtCustomActionItem.addClickHandler(this);

			ToolStripMenuButton dtMenuButton = new ToolStripMenuButton(dtMessages.button_custom(), dtCustomMenu);   
			dtMenuButton.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
			dtMenuButton.setWidth(100);   
			dtToolStrip.addMenuButton(dtMenuButton);

		}
		
		dtFilterMenu = new Menu();   
		dtFilterMenu.setShowShadow(true);   
		dtFilterMenu.setShadowDepth(3);   

		dtFilterItem = createMenuItem(dtMessages.decisionTable(), "filter.png");   
		dtFilterItem.addClickHandler(this);

//		dtCustomFilterItem = createMenuItem("Custom Filter", "filter.png");
//		dtCustomFilterItem.addClickHandler(this);
//
//		dtCustomFilterBuilderItem = createMenuItem("Filter Builder", "filter.png");
//		dtCustomFilterBuilderItem.addClickHandler(this);

		dtNoFilterItem = createMenuItem(dtMessages.dt_filter_remove(), "filter.png");
		dtNoFilterItem.addClickHandler(this);

		dtFilterMenu.setItems(dtFilterItem, /*dtCustomFilterItem, dtCustomFilterBuilderItem,*/ dtNoFilterItem);

		ToolStripMenuButton dtFilterMenuButton = new ToolStripMenuButton(dtMessages.dt_filter_menu_title(), dtFilterMenu);   
		dtFilterMenuButton.setIcon(WebStudioMenubar.ICON_PREFIX + "filter.png");
		dtFilterMenuButton.setWidth(100);  
		
		dtToolStrip.addMenuButton(dtFilterMenuButton);
				
		return dtToolStrip;
	}  
	
	protected ToolStrip getExceptionTableToolBar() {
		expToolStrip = new ToolStrip();   
		expToolStrip.setWidth100();
		expToolStrip.setHeight(TOOL_BAR_HEIGHT);
		expToolStrip.setAlign(Alignment.LEFT); 
		expToolStrip.setBorder("0px");

		expAddRowbutton = new ToolStripButton();
		expAddRowbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
		expAddRowbutton.setTitle(globalMsg.button_add());
//		expAddRowbutton.setTitleStyle("ws-dt-toolstrip-addbutton-title");
		expAddRowbutton.setTooltip(globalMsg.button_add());
		expAddRowbutton.setShowRollOver(false);
		expAddRowbutton.addClickHandler(this);
		expToolStrip.addButton(expAddRowbutton);
		
		expRemoveRowbutton = new ToolStripButton();
		expRemoveRowbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
		expRemoveRowbutton.setTitle(dtMessages.dtRemoveRow());
//		expRemoveRowbutton.setTitleStyle("ws-dt-toolstrip-addbutton-title");
		expRemoveRowbutton.setTooltip(globalMsg.button_delete());
		expRemoveRowbutton.setShowRollOver(false);
		expRemoveRowbutton.addClickHandler(this);
		expRemoveRowbutton.setDisabled(true);
		
		expToolStrip.addButton(expRemoveRowbutton);

		expDupeRowbutton = new ToolStripButton();
		expDupeRowbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "duplicateRule.png");
		expDupeRowbutton.setTitle(dtMessages.dtDuplicateRow());
		expDupeRowbutton.setTooltip(dtMessages.dtDuplicateRowToolTip());
		expDupeRowbutton.setShowRollOver(false);
		expDupeRowbutton.addClickHandler(this);
		expDupeRowbutton.setDisabled(true);
		expToolStrip.addButton(expDupeRowbutton);
		
		expToolStrip.addSeparator();

		if (!isReadOnly()) {
			expCustomMenu = new Menu();   
			expCustomMenu.setShowShadow(true);   
			expCustomMenu.setShadowDepth(3);   

			expCustomCondItem = createMenuItem(dtMessages.button_custom_add_cond(), "add.png");   
			expCustomCondItem.addClickHandler(this);
			expCustomActionItem = createMenuItem(dtMessages.button_custom_add_action(), "add.png");
			expCustomActionItem.addClickHandler(this);
			expCustomMenu.setItems(expCustomCondItem, expCustomActionItem);
			ToolStripMenuButton expMenuButton = new ToolStripMenuButton(dtMessages.button_custom(), expCustomMenu);   
			expMenuButton.setWidth(100);   
			expToolStrip.addMenuButton(expMenuButton);
		}
		
		expFilterMenu = new Menu();   
		expFilterMenu.setShowShadow(true);   
		expFilterMenu.setShadowDepth(3);   
		
		expFilterItem = createMenuItem(dtMessages.exceptionTable(), "filter.png");   
		expFilterItem.addClickHandler(this);

//		expCustomFilterItem = createMenuItem("Custom Filter", "filter.png");
//		expCustomFilterItem.addClickHandler(this);
//
//		expCustomFilterBuilderItem = createMenuItem("Filter Builder", "filter.png");
//		expCustomFilterBuilderItem.addClickHandler(this);

		expNoFilterItem = createMenuItem(dtMessages.dt_filter_remove(), "filter.png");
		expNoFilterItem.addClickHandler(this);

		expFilterMenu.setItems(expFilterItem, /*expCustomFilterItem, expCustomFilterBuilderItem,*/ expNoFilterItem);

		ToolStripMenuButton expFilterMenuButton = new ToolStripMenuButton(dtMessages.dt_filter_menu_title(), expFilterMenu);   
		expFilterMenuButton.setIcon(WebStudioMenubar.ICON_PREFIX + "filter.png");
		expFilterMenuButton.setWidth(100);  
		
		expToolStrip.addMenuButton(expFilterMenuButton);

		return expToolStrip;
	}  
	
	@Override
	public void addConfigurationSection(VLayout layout) {
		layout.addMember(getEditorToolBar());
	}
	
	//Decision Table Editor level Toolbar
	protected ToolStrip getEditorToolBar() {
		if (editorToolStrip != null) {
			return editorToolStrip;
		}
		editorToolStrip = new ToolStrip();
		editorToolStrip.setWidth100();
		editorToolStrip.setHeight(TOOL_BAR_HEIGHT);
		editorToolStrip.setAlign(Alignment.LEFT); 
		
		final ToolStripButton showDeclSection = new ToolStripButton();  
		showDeclSection.setIcon(Page.getAppImgDir() + "icons/16/decl_table.png");  
		showDeclSection.setActionType(SelectionType.CHECKBOX);  
		showDeclSection.setTooltip(dtMessages.declTableSection_tooltip());
		editorToolStrip.addButton(showDeclSection);  
		showDeclSection.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (showDeclSection.isSelected()) {
					declSection.getSectionStack().showSection(declSection.getID());
					declSection.setExpanded(true);
				} else {
					declSection.getSectionStack().hideSection(declSection.getID());
				}
			}
		});
		
		final ToolStripButton showDecisionTableSection = new ToolStripButton();  
		showDecisionTableSection.setIcon(Page.getAppImgDir() + "icons/16/decision_table_section.png");  
		showDecisionTableSection.setActionType(SelectionType.CHECKBOX);  
		showDecisionTableSection.setTooltip(dtMessages.decisionTableSection_tooltip());
		editorToolStrip.addButton(showDecisionTableSection);  
		showDecisionTableSection.setSelected(true);
		showDecisionTableSection.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (showDecisionTableSection.isSelected()) {
					dtSection.getSectionStack().showSection(dtSection.getID());
					dtSection.setExpanded(true);
				} else {
					if (dtTable.getSelectedRecords().length > 0) {
						dtTable.deselectRecords(dtTable.getSelectedRecords());
					}
					propertyTab.refresh(AbstractTableEditor.this, table, null, null, true, false, false, true);
					dtRemovebutton.setDisabled(true);
					dtDupeRowbutton.setDisabled(true);
					dtSection.getSectionStack().hideSection(dtSection.getID());
				}
			}
		});

		final ToolStripButton showExcTableSection = new ToolStripButton();  
		showExcTableSection.setIcon(Page.getAppImgDir() + "icons/16/exception_table.png");  
		showExcTableSection.setActionType(SelectionType.CHECKBOX);  
		showExcTableSection.setTooltip(dtMessages.exceptionTableSection_tooltip());
		editorToolStrip.addButton(showExcTableSection);  
		showExcTableSection.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (showExcTableSection.isSelected()) {
					expSection.getSectionStack().showSection(expSection.getID());
					expSection.setExpanded(true);
				} else {
					if (expTable.getSelectedRecords().length > 0) {
						expTable.deselectRecords(expTable.getSelectedRecords());
					}
					propertyTab.refresh(AbstractTableEditor.this, table, null, null, true, false, false, false);
					expRemoveRowbutton.setDisabled(true);
					expDupeRowbutton.setDisabled(true);
					expSection.getSectionStack().hideSection(expSection.getID());
				}
			}
		});
		
		editorToolStrip.addSeparator();
		
		showTableAnalyzerViewButton = new ToolStripButton();  
		showTableAnalyzerViewButton.setIcon(Page.getAppImgDir() + "icons/16/analyzer16x16.png");  
		showTableAnalyzerViewButton.setSelected(false);
		showTableAnalyzerViewButton.setActionType(SelectionType.BUTTON);  
		showTableAnalyzerViewButton.setTooltip(dtMessages.decisionTableAnalyzer_tooltip());
		editorToolStrip.addButton(showTableAnalyzerViewButton);  
		showTableAnalyzerViewButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showTableAnalyzer =  !showTableAnalyzer;
				WebStudio.get().getEditorPanel().setShowTableAnalyzer(showTableAnalyzer);
				if (showTableAnalyzer) {
					showTableAnalyzer();
				} else {
					if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
							&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
						WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().close();
					}
				}
			}
		});
		
		editorToolStrip.addSeparator();
		
		showPropertiesViewButton = new ToolStripButton();  
		showPropertiesViewButton.setIcon(Page.getAppImgDir() + "icons/16/property_pane.gif");  
		showPropertiesViewButton.setSelected(false);
		showPropertiesViewButton.setActionType(SelectionType.CHECKBOX);  
		showPropertiesViewButton.setTooltip(globalMsg.text_properties());
		editorToolStrip.addButton(showPropertiesViewButton);  
		showPropertiesViewButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showProperties = !showProperties;
				WebStudio.get().getEditorPanel().getBottomPane().setVisible(showProperties);
				if (showProperties) {
					if (getDecisionTablePropertyTab() != null) {
						WebStudio.get().getEditorPanel().getPropertiesPane().fillProperties(getDecisionTablePropertyTab());
					} else {
						propertyTab = new DecisionTablePropertyTab();
						WebStudio.get().getEditorPanel().getPropertiesPane().fillProperties(propertyTab);
						propertyTab.refresh(AbstractTableEditor.this, table, null, null, true, false, false, true);
					}
					WebStudio.get().getEditorPanel().getBottomContainer().selectTab(0);
				}
			}
		});
		
		editorToolStrip.addSeparator();
		
		refreshButton = new ToolStripButton();  
		refreshButton.setIcon(Page.getAppImgDir() + "icons/16/refresh_16x16.png");  
		refreshButton.setSelected(false);
		refreshButton.setActionType(SelectionType.BUTTON);  
		refreshButton.setTooltip(globalMsg.text_refresh());
		editorToolStrip.addButton(refreshButton);  
		refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadPage(1);
			}
		});
		
		
		return editorToolStrip;
	}
	
	public void enableValidate(boolean enable) {
		WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, !enable);
	}
	
	public boolean getShowProperties() {
		return showProperties;
	}

	public void setShowProperties(boolean showProperties) {
		this.showProperties = showProperties;
	}
	
	public void setShowTableAnalyzer(boolean showTableAnalyzer) {
		this.showTableAnalyzer = showTableAnalyzer;
	}

	public boolean isAnalyzed() {
		return isAnalyzed;
	}
		
	public void setAnalyzed(boolean isAnalyzed) {
		this.isAnalyzed = isAnalyzed;
	}
				
	protected MenuItem createMenuItem(String title, String icon) {
		return new MenuItem(title, WebStudioMenubar.ICON_PREFIX + icon);
	}
	
	protected void clearCache(boolean isDecisionTable) {
		if (isDecisionTable) {
			dtConditionAreaIndices.clear();
			dtActionAreaIndices.clear();

			dtConditionAreaColumns.clear();
			dtActionAreaColumns.clear();

            dtConditionAreaFieldMap.clear();
            dtActionAreaFieldMap.clear();
            
            fDtTableRowCacheMap.clear();			
		} else {
			expConditionAreaIndices.clear();
			expActionAreaIndices.clear();

			expConditionAreaColumns.clear();
			expActionAreaColumns.clear();
			
			expConditionAreaFieldMap.clear();
            expActionAreaFieldMap.clear();
            
            fExpTableRowCacheMap.clear();
		}
	}
	
	private boolean isValidPropertyField(int fieldNum, boolean isDecisionTable) {
		final ListGridField field = isDecisionTable? dtTable.getField(fieldNum) : expTable.getField(fieldNum);
		final String colName = field.getName();
		TableColumn column = getColumn(isDecisionTable? table.getDecisionTable().getColumns()
				: table.getExceptionTable().getColumns(), colName);
		boolean isBooleanField = false;
		if (column.getPropertyType() != null) {
			PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType()));
			if (type == PROPERTY_TYPES.BOOLEAN) {
				isBooleanField = true;
			}
		}
		if (column.getColumnType().equals(ColumnType.CUSTOM_CONDITION.getName()) 
				|| column.getColumnType().equals(ColumnType.CUSTOM_ACTION.getName()) 
				|| isBooleanField) {
			return false;
		}
		return true;
	}
	
	/**
	 * Shifts the DT column to left/right
	 * @param fieldNum The column index (it also takes into account the ID column)
	 * @param isDecisionTable
	 * @param toRight
	 * @param extremeEnd Set to true when move to beginning or end is intended (Beginning when <code>toRight</code> is false and to end when <code>toRight</code> is true.)
	 */
	private void moveColumn(int fieldNum, boolean isDecisionTable, boolean toRight, boolean extremeEnd) {
		TableRuleSet tableRuleSet = isDecisionTable ? table.getDecisionTable() : table.getExceptionTable();
		List<TableColumn> columns = tableRuleSet.getColumns();
		int totalConditionColumns = tableRuleSet.getConditionColumns().size();
		
		TableColumn selectedColumn = columns.get(fieldNum - 1);
		boolean isConditionColumn = ColumnType.CONDITION.getName().equals(selectedColumn.getColumnType())
				|| ColumnType.CUSTOM_CONDITION.getName().equals(selectedColumn.getColumnType());
		
		if (!toRight && ((isConditionColumn && fieldNum <= 1) || (!isConditionColumn && fieldNum <= totalConditionColumns + 1))) {
			CustomSC.say(dtMessages.dt_moveColumn_left_restricted_message());
			CustomSC.say(globalMsg.text_note(), dtMessages.dt_moveColumn_left_restricted_message());
			return;
		}
		if (toRight && ((isConditionColumn && fieldNum >= totalConditionColumns) || (!isConditionColumn && fieldNum >= columns.size()))) {
			CustomSC.say(globalMsg.text_note(), dtMessages.dt_moveColumn_right_restricted_message());
			return;
		}
		
		TableColumn tableColumn = columns.remove(fieldNum - 1);//-1 because of id column
		if (toRight) {//toRight
			if (extremeEnd) {//move to end
				columns.add(isConditionColumn ? totalConditionColumns - 1 : columns.size(), tableColumn);
			}
			else {
				columns.add(fieldNum, tableColumn);
			}
		}
		else {//toLeft
			if (extremeEnd) {//move to beginning
				columns.add(isConditionColumn ? 0 : totalConditionColumns, tableColumn);
			}
			else {
				columns.add(fieldNum - 2, tableColumn);
			}
		}
		
		((AbstractDecisionTableEditor)AbstractTableEditor.this).loadDecisionTable(false, false, false, null, null);
		makeDirty(isDecisionTable);
	}
	
	/**
	 * @param tableDataGrid
	 * @param fieldNum
	 * @param tableRuleSet
	 */
	protected void removeTableColumn(final TableDataGrid tableDataGrid , int fieldNum, final TableRuleSet tableRuleSet) {
		final ListGridField field = tableDataGrid.getField(fieldNum);
		final String colName = field.getName();
		if (colName.equals("id")) {
			CustomSC.say(dtMessages.dtRemoveIdColumn());
			return;
		}
		CustomSC.confirm (dtMessages.dtRemoveColumndialogtitle(), dtMessages.dtRemoveColumndialogdesc(), new BooleanCallback () {
			public void execute (Boolean value) {
				if (Boolean.TRUE.equals (value)) {
					TableColumn column = getColumn(tableRuleSet.getColumns(), colName);
					if (column.getColumnType().equals(ColumnType.CONDITION.getName()) 
							|| column.getColumnType().equals(ColumnType.CUSTOM_CONDITION.getName())) {
						if (getConditionColumns(tableRuleSet).size() == 1 
								&& getActionColumns(tableRuleSet).size() > 0 ) {
							CustomSC.say(dtMessages.dtCannotRemoveConditionColumn_message());
							return;
						}
					}
					String colId = column.getId();
					for (TableRule rule : tableRuleSet.getTableRules()) {
						
						removeFromCellToDisableMap(rule, AbstractTableEditor.this, table, column.getName(), 
								tableRuleSet == AbstractTableEditor.this.table.getDecisionTable(), false);
						
						TableRuleVariable trv = null;
						boolean found = false;
						for (TableRuleVariable ruleVar : rule.getConditions()) {
							if (ruleVar.getColumnId().intern() == colId.intern()) {
								trv = ruleVar;
								found = true;
								break;
							}
						}
						if (found) {
							updateColumnsProvider(rule, colName,
									tableRuleSet == table.getDecisionTable()? decisionTableUpdateprovider : exceptionTableUpdateprovider);
							rule.getConditions().remove(trv);
						} else {
							for (TableRuleVariable ruleVar : rule.getActions()) {
								if (ruleVar.getColumnId().intern() == colId.intern()) {
									trv = ruleVar;
									found = true;
									break;
								}
							}
							if (found) {
								updateColumnsProvider(rule, colName, 
										tableRuleSet == table.getDecisionTable()? decisionTableUpdateprovider : exceptionTableUpdateprovider);
								rule.getActions().remove(trv);
							} 
						}
					}

					int iColId = Integer.parseInt(colId);
					if (tableRuleSet == table.getDecisionTable()) {
						if (dtConditionAreaIndices.contains(iColId)) {
							dtConditionAreaIndices.remove(new Integer(iColId));
						}
						if (dtActionAreaIndices.contains(iColId)) {
							dtActionAreaIndices.remove(new Integer(iColId));
						}
						if (dtConditionAreaColumns.keySet().contains(iColId)) {
							dtConditionAreaColumns.keySet().remove(iColId);
						}
						if (dtActionAreaColumns.keySet().contains(iColId)) {
							dtActionAreaColumns.keySet().remove(iColId);
						}
						
						if (dtActionAreaColumns.keySet().contains(iColId)) {
							dtActionAreaColumns.keySet().remove(iColId);
						}
						if (dtFieldDomAssocMap.containsKey(colName)) {
							dtFieldDomAssocMap.remove(colName);
						}
						
						List<TableColumn> removedCols = new ArrayList<TableColumn>();
						for (TableColumn col : decisionTableUpdateprovider.getNewColumns()) {
							if(col.getId().equals(colId)) {
								removedCols.add(col);
							}
						}
						decisionTableUpdateprovider.getNewColumns().removeAll(removedCols);
						decisionTableUpdateprovider.getDeletedColumns().put(colId, colName);
						table.getDecisionTable().getColumns().remove(column);
						dtTable.hideFields(field);
						
						if (dtTable.getFields().length == 1) {
						  table.getDecisionTable().getTableRules().clear();	
						  final ListGridRecord[]  records = dtTable.getRecords();
						  for (ListGridRecord record : records) {
							  dtTable.getDataSource().removeData(record);
						  }
							
						  ListGridField field = tableDataGrid.getField(0);
						  dtTable.hideFields(field);
						  AbstractTableEditor.this.disableToolBar(true);	
						}
						
					} else {
						if (expConditionAreaIndices.contains(iColId)) {
							expConditionAreaIndices.remove(new Integer(iColId));
						}
						if (expActionAreaIndices.contains(iColId)) {
							expActionAreaIndices.remove(new Integer(iColId));
						}
						if (expConditionAreaColumns.keySet().contains(iColId)) {
							expConditionAreaColumns.keySet().remove(iColId);
						}
						if (expActionAreaColumns.keySet().contains(iColId)) {
							expActionAreaColumns.keySet().remove(iColId);
						}
						if (expFieldDomAssocMap.containsKey(colName)) {
							expFieldDomAssocMap.remove(colName);
						}
						
						List<TableColumn> removedCols = new ArrayList<TableColumn>();
						for (TableColumn col : exceptionTableUpdateprovider.getNewColumns()) {
							if(col.getId().equals(colId)) {
								removedCols.add(col);
							}
						}
						exceptionTableUpdateprovider.getNewColumns().removeAll(removedCols);
						exceptionTableUpdateprovider.getDeletedColumns().put(colId, colName);
						table.getExceptionTable().getColumns().remove(column);
						expTable.hideFields(field);
						
						if (expTable.getFields().length == 1) {
							 table.getExceptionTable().getTableRules().clear();	
							 final ListGridRecord[]  records = expTable.getRecords();
							  for (ListGridRecord record : records) {
								  expTable.getDataSource().removeData(record);
							  }
							ListGridField field = tableDataGrid.getField(0);
							expTable.hideFields(field);
							AbstractTableEditor.this.disableToolBar(false);	
						}

					}
					// Initialise maxIds if not already.
					if (dt_currentColumnMaxId == -1) {
						dt_currentColumnMaxId = getNewColumnId(true) - 1;
					}
					if (exp_currentMaxColumnId == -1) {
						exp_currentMaxColumnId = getNewColumnId(true) - 1;
					}
					makeDirty(tableRuleSet == table.getDecisionTable());
				}
			}
		});
	}
	
	/**
	 * @param rule
	 * @param colName
	 * @param decisionTableUpdateprovider
	 */
	private void updateColumnsProvider(TableRule rule, String colName, 
			DecisionTableUpdateProvider decisionTableUpdateprovider) {
		if (decisionTableUpdateprovider.getModifiedRecords().containsKey(rule.getId())) {
			Set<String> modifiedCols  = decisionTableUpdateprovider.getModifiedRecordColumns().get(rule.getId());
			if (modifiedCols.contains(colName)) {
				modifiedCols.remove(colName);
			}
			decisionTableUpdateprovider.getModifiedRecordColumns().put(rule.getId(), modifiedCols);
		}
	}
	
	/**
	 * @param table
	 * @param ruleset
	 * @param isDecisionTable
	 */
	public void refreshColumnProperties(TableDataGrid table, TableRuleSet ruleset, boolean isDecisionTable) {
	
		if (isHeaderGroupSupported) {
			String[] conditionCols = new String[0];
			String[] actionCols = new String[0];
			if (isDecisionTable) {
				conditionCols = dtConditionAreaFieldMap.values().toArray(new String[dtConditionAreaFieldMap.values().size()]);
				actionCols = dtActionAreaFieldMap.values().toArray(new String[dtActionAreaFieldMap.values().size()]);
			} else {
				conditionCols = expConditionAreaFieldMap.values().toArray(new String[expConditionAreaFieldMap.values().size()]);
				actionCols = expActionAreaFieldMap.values().toArray(new String[expActionAreaFieldMap.values().size()]);
			}
			if (isDecisionTable) {
				dtConditionSpan.setFields(conditionCols);
				dtActionSpan.setFields(actionCols);
			} else {
				expConditionSpan.setFields(conditionCols);
				expActionSpan.setFields(actionCols);
			}
		}
		
		int i = 0;
		for (ListGridField field : table.getFields()) {
			field.setCanFreeze(false);
			field.setCanGroupBy(false);
			field.setCanReorder(false);
			field.setCanFilter(true);
			field.setCanSort(true);
			field.setCanSortClientOnly(true);
			field.setFilterOnKeypress(true);
			field.setShowHover(true);
			if(WebStudio.get().getUserPreference().getAutoFitColumnsApproch().equalsIgnoreCase("Both")){
				field.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
				field.setAutoFitWidth(true);
			}else if(WebStudio.get().getUserPreference().getAutoFitColumnsApproch().equalsIgnoreCase("Value")){
				field.setAutoFitWidthApproach(AutoFitWidthApproach.VALUE);
				field.setAutoFitWidth(true);
			}else if(WebStudio.get().getUserPreference().getAutoFitColumnsApproch().equalsIgnoreCase("Title")){
				field.setAutoFitWidthApproach(AutoFitWidthApproach.TITLE);
				field.setAutoFitWidth(true);
			}
			
			if (isReadOnly()) {
				field.setCanEdit(false);
			}
			if (field.getName().equals("id")) {
				field.setCanEdit(false);
				field.setHeaderTitleStyle("ws-dt-id-header-style");
				field.setCellAlign(Alignment.LEFT);
				field.setWidth(30);
			}
			final TableColumn column = getColumn(ruleset.getColumns(), field.getName());
			if (column != null) {
				if (ColumnType.get(column.getColumnType()) == ColumnType.CUSTOM_CONDITION 
						|| ColumnType.get(column.getColumnType()) == ColumnType.CUSTOM_ACTION) {
					field.setCanSort(false);
					final TextAreaItem textAreaItem = new TextAreaItem();   

					textAreaItem.setInitHandler(new FormItemInitHandler() {
						
						@Override
						public void onInit(FormItem item) {
							final TextAreaItem textAreaItem2 = new TextAreaItem(item.getJsObj());
							textAreaItem2.setInputTransformer(new FormItemInputTransformer() {								
								@Override
								public Object transformInput(DynamicForm form, FormItem item, Object value,
										Object oldValue) {									
									Object newValue = value;
									boolean isNewLine = false;
									if (newValue != null && column.isSubstitution()) {
										if (newValue.toString().contains("\r\n")) {
											newValue = newValue.toString().replaceAll("\r\n", "");
											isNewLine = true;
										} else if (newValue.toString().contains("\n")) {
											newValue = newValue.toString().replaceAll("\n", "");
											isNewLine = true;
										}
										
										if (isNewLine) {
											newValue = DecisionTableUtils.getFormattedString(column, newValue.toString());
										}
									}	
									return newValue;
								}
							});							
						}
					});

					textAreaItem.setHeight(70);
					field.setEditorType(textAreaItem);
					
					field.setHoverCustomizer(new TableCellHoverCustomizer(this, isDecisionTable));
				} 
				PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType()));
				if (type == PROPERTY_TYPES.BOOLEAN) {
					field.setCanSort(false);
				}
                String icon = getPropertyIcon(ColumnType.get(column.getColumnType()), type);
                if (icon != null) {
                	field.setIcon(icon);	
                }
                //DT diff functionality, set proper css for columns in DT diff view.
                DTDiffType diffType = (isDecisionTable ? DTDiffType.DECISION_TABLE : DTDiffType.EXCEPTION_TABLE);
                ModificationEntry modificationEntry = modifications.get(diffType.getKeyPrefix() + "column" + column.getId());
                if (modificationEntry != null) {
                	switch(modificationEntry.getModificationType()) {
                	case ADDED: field.setHeaderTitleStyle("ws-dt-diff-add-style"); break;
                	case DELETED: field.setHeaderTitleStyle("ws-dt-diff-remove-style"); break;
                	case MODIFIED: field.setHeaderTitleStyle("ws-dt-diff-modify-style");
                		//field.setToolTip("<b>" + globalMsg.text_previousValue() + ":</b><br/><nobr>" + modificationEntry.getPreviousValue() + "</nobr>");
                		break;
                	//case UNCHANGED: break;
                	}
                }
                else if(i > 0) {
                	if (isDecisionTable ? dtConditionAreaFieldMap.containsKey(i)
                			: expConditionAreaFieldMap.containsKey(i)) {
                		field.setHeaderTitleStyle("ws-dt-condition-header-style");
        			}
                }
			}
			i++;
		}
		if (table.getFields().length > 0) {
			table.refreshFields();
		}
	}

	@Override
	protected Canvas getWidget() {
		return null;
	}

	@Override
	public TemplateInstanceChangeHandler getChangeHandler() {
		return null;
	}

	@Override
	public void onEditorExit(EditorExitEvent event) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
		// TODO Auto-generated method stub
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.editor.AbstractEditor#onDirty()
	 */
	@Override
	public void onDirty() {
		// TODO Auto-generated method stub
	}

	public Table getTable() {
		return table;
	}
	
	public TableDataGrid getDecisionTableDataGrid() {
		return dtTable;
	}
	
	public TableDataGrid getExceptionTableDataGrid() {
		return expTable;
	}
	
	public Map<Integer, String> getDtConditionAreaFieldMap() {
		return dtConditionAreaFieldMap;
	}

	public Map<Integer, String> getDtActionAreaFieldMap() {
		return dtActionAreaFieldMap;
	}

	public Map<Integer, List<String>> getRulesToHighlight() {
		return rulesToHighlight;
	}

	public void setRulesToHighlight(Map<Integer, List<String>> rulesToHighlight) {
		this.rulesToHighlight = rulesToHighlight;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public DecisionTableDataSource getDecisionTableDataSource() {
		return decisionTableDataSource;
	}

	public void setDecisionTableDataSource(
			DecisionTableDataSource decisionTableDataSource) {
		this.decisionTableDataSource = decisionTableDataSource;
	}

	public DecisionTableDataSource getExceptionTableDataSource() {
		return exceptionTableDataSource;
	}

	public void setExceptionTableDataSource(
			DecisionTableDataSource exceptionTableDataSource) {
		this.exceptionTableDataSource = exceptionTableDataSource;
	}
	
	public long getDtlastRecordId() {
		return dtlastRecordId;
	}

	public void setDtlastRecordId(long dtlastRecordId) {
		this.dtlastRecordId = dtlastRecordId;
	}

	public long getExplastRecordId() {
		return explastRecordId;
	}

	public void setExplastRecordId(long explastRecordId) {
		this.explastRecordId = explastRecordId;
	}
	
	public DecisionTablePropertyTab getDecisionTablePropertyTab() {
		return propertyTab;
	}
		
	public ToolStripButton getShowTableAnalyzerViewButton() {
		return showTableAnalyzerViewButton;
	}

	public ToolStripButton getShowPropertiesViewButton() {
		return showPropertiesViewButton;
	}

	protected void showTableAnalyzer() {
		WebStudio.get().getEditorPanel().getLeftPane().setVisible(true);
		if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() == null) {
			decisionTableAnalyzerPane = new DecisionTableAnalyzerPane(WebStudio.get().getEditorPanel().getLeftPane());
			decisionTableAnalyzerPane.setTitle(Canvas.imgHTML(Page.getAppImgDir() + "icons/16/analyzer16x16.png") + " " + dtMessages.tableAnalyzer_title());
			WebStudio.get().getEditorPanel().getLeftPane().addChild(decisionTableAnalyzerPane);
			WebStudio.get().getEditorPanel().setDecisionTableAnalyzerPane(decisionTableAnalyzerPane);
		} else {
			decisionTableAnalyzerPane = WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane();
			decisionTableAnalyzerPane.setVisible(true);
		}
		String artifactPath = table.getFolder() + table.getName();
		decisionTableAnalyzerPane.update(table.getProjectName(), artifactPath);
	}
	
	protected TableDataGrid createNewTable(final boolean isDecisionTable) {
		final TableDataGrid table = new TableDataGrid(this, false, false) {
			@Override             
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) { 
				return super.getCellCSSText(record, rowNum, colNum);
			} 

			@Override
			protected String getCellStyle(ListGridRecord record, int rowNum,
					int colNum) {
				//DT diff functionality, set proper css for cells in DT diff view.
				if (!modifications.isEmpty() && record != null && record instanceof TableRuleVariableRecord && colNum > 0) {
					TableRule rule = ((TableRuleVariableRecord) record).getRule();
					String colId = isDecisionTable ? getDecisionTableColumnId(colNum) : getExceptionTableColumnId(colNum);
					TableRuleVariable ruleVar = colId != null && rule != null ? DecisionTableUtils.getTableRuleVariable(rule, colId) : null;
					if (ruleVar != null && ruleVar.getId() != null) {
						DTDiffType diffType = (isDecisionTable ? DTDiffType.DECISION_TABLE : DTDiffType.EXCEPTION_TABLE);
						ModificationEntry modificationEntry = modifications.get(diffType.getKeyPrefix() + "ruleCell" + ruleVar.getId());
						if (modificationEntry == null) {//No modification for RuleVariable
							//But CSS style might change due to change on Rule/Column, Eg: cell should be red if it is from a deleted column etc.
							//For Rule Addition/Deletion complete row will be highlighted so no need to handle explicitly. (Only handled column addition/deletion)
							ModificationEntry conditionModificationEntry = modifications.get(diffType.getKeyPrefix() + "column" + colId);
							if (conditionModificationEntry != null) {
								switch(conditionModificationEntry.getModificationType()) {
								case ADDED: return "ws-dt-diff-add-style";
								//case MODIFIED://Not need, cells need not be highlighted for column MODIFIED(i.e. column rename.)
								case DELETED: return "ws-dt-diff-remove-style";
								}
							}
						}
						else if (modificationEntry.getModificationType() == ModificationType.MODIFIED) {//RuleVariable modified
							if (modificationEntry.isApplied()) {
								return "ws-dt-diff-modify-apply-style";
							}
							return "ws-dt-diff-modify-style";
						}
					}
				}
				
				//Highlight Row for Show Coverage
				List<Integer> rowsToHighlight = AbstractTableEditor.this.getRowsToHighlight();
				if (rowsToHighlight.contains(rowNum)) {
					return "ws-dt-coverage-row-style";
				}
				//Highlight Problem Record 
				ProblemMarker probleMarker = AbstractTableEditor.this.getOnOpenMarker();
				if (probleMarker != null) {
					if (probleMarker instanceof DecisionTableProblemMarker) {
						DecisionTableProblemMarker dtProblemMarker = (DecisionTableProblemMarker) probleMarker;
						int markerPage = -1;
						try {		
							markerPage = Integer.parseInt(dtProblemMarker.getPageNum());
						} catch (NumberFormatException nfe) { } //do nothing	
						if (markerPage == getCurrentPage() && dtProblemMarker.getRowNum() == rowNum) {
							return (dtProblemMarker.getSeverity() == ProblemMarker.SEVERITY_WARNING ? "ws-dt-warning-row-style" : "ws-dt-error-row-style");
						}
					}
				}

				if (fCellsToHighlightMap.size() > 0) {
					for (int row : fCellsToHighlightMap.keySet()) {
						if (row == rowNum) {
							List<Integer> colList = fCellsToHighlightMap
									.get(row);
							for (int col : colList) {
								if (col == colNum) {
									return showCellHighlight ? "ws-dt-error-row-style": super.getCellStyle(record, rowNum, colNum);
								}
							}
						}
					}
				}
				
				if (record != null && record instanceof TableRuleVariableRecord) {

					TableRule rule = ((TableRuleVariableRecord) record).getRule();
					String colId = null;
					String colName = isDecisionTable ? dtTable.getField(colNum)
							.getTitle() : expTable.getField(colNum).getTitle();

					if ("ID".equals(colName)) {
						if (!rule.isEnabled()) {
							return "ws-dt-disable-grid-cell-style";
						}
					} else {
						if (isDecisionTable) {
							colId = getDecisionTableColumnId(colNum);
						} else {
							colId = getExceptionTableColumnId(colNum);
						}

						if (colId != null) {
							TableRuleVariable ruleVar = DecisionTableUtils
									.getTableRuleVariable(rule, colId);
							if (ruleVar != null && !ruleVar.isEnabled()) {
								return "ws-dt-disable-grid-cell-style";
							} else if (ruleVar == null && !rule.isEnabled()) {
								return "ws-dt-disable-grid-cell-style";
							}
						}
					}
				}
				return super.getCellStyle(record, rowNum, colNum);

			}
			
			public String getBodyBackgroundColor()  {
				return super.getBodyBackgroundColor();
			}

			@Override  
			protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
				return super.getBaseStyle(record, rowNum, colNum);   
			}   
			
			@Override
			protected MenuItem[] getHeaderContextMenuItems(final Integer fieldNum) {
				if (fieldNum == null) {
					return new MenuItem[]{};
				}
				if (fieldNum == 0) {
					return getGeneralHeaderContextMenuItems(super.getHeaderContextMenuItems(fieldNum));
				}
				return updateHeaderContextMenuItems(fieldNum, super.getHeaderContextMenuItems(fieldNum), isDecisionTable);
			}
			
			@Override
			protected MenuItem[] getHeaderSpanContextMenuItems(HeaderSpan headerSpan) {
				return new MenuItem[]{};
			}
			
			@Override  
			protected Canvas getCellHoverComponent(Record record, Integer rowNum, Integer colNum) {
				if (colNum != 0) {
					//DT diff functionality, set previous value in tooltip for modified cells.
					if (!modifications.isEmpty() && record != null && record instanceof TableRuleVariableRecord && colNum > 0) {
						TableRule rule = ((TableRuleVariableRecord) record).getRule();
						String colId = isDecisionTable ? getDecisionTableColumnId(colNum) : getExceptionTableColumnId(colNum);
						TableRuleVariable ruleVar = colId != null && rule != null ? DecisionTableUtils.getTableRuleVariable(rule, colId) : null;
						String rvId = ruleVar != null ? ruleVar.getId() : null;
						DTDiffType diffType = (isDecisionTable ? DTDiffType.DECISION_TABLE : DTDiffType.EXCEPTION_TABLE);
						ModificationEntry modificationEntry = modifications.get(diffType.getKeyPrefix() + "ruleCell" + rvId);
						if (modificationEntry == null) {//No modification for RuleVariable
							//But tooltip should show changes in column/row if any. Eg: Rule Added etc
							ModificationEntry conditionModificationEntry = modifications.get(diffType.getKeyPrefix() + "column" + colId);
							ModificationEntry ruleModificationEntry = modifications.get(diffType.getKeyPrefix() + "tableRule" + rule.getId());
							if (conditionModificationEntry instanceof MergedDiffModificationEntry && ruleModificationEntry instanceof MergedDiffModificationEntry) {
								return MergedDiffHelper.getTooltipCanvas((MergedDiffModificationEntry)conditionModificationEntry, (MergedDiffModificationEntry)ruleModificationEntry);
							}
							else if (conditionModificationEntry instanceof MergedDiffModificationEntry) {
								return MergedDiffHelper.getTooltipCanvas((MergedDiffModificationEntry)conditionModificationEntry);
							}								
							else if (ruleModificationEntry instanceof MergedDiffModificationEntry) {
								return MergedDiffHelper.getTooltipCanvas((MergedDiffModificationEntry)ruleModificationEntry);
							}
						}
						else {//Cell modification condition.
							if (modificationEntry instanceof MergedDiffModificationEntry) {
								return MergedDiffHelper.getTooltipCanvas((MergedDiffModificationEntry)modificationEntry);
							}
							else {
								Canvas canvas = new Label("<b>" + globalMsg.text_previousValue() + ":</b><br/><nobr>" + modificationEntry.getPreviousValue() + "</nobr>");
								canvas.setHeight(40);
								return canvas;
							}
						}
					}
				}
				return null;
			}

			@Override
			public Boolean willAcceptDrop() {
				return true;
			}
		};
		
		table.setShowResizeBar(false);
		table.setCanHover(true);   
		table.setShowHover(true);   
		table.setShowHoverComponents(true); 
		table.setShowEmptyMessage(true); 
		table.setEmptyMessage(dtMessages.dtcreatecolumnemptydesc());
		table.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
		table.setCanAcceptDrop(true);
		
		if (isHeaderGroupSupported) {
			table.setHeaderSpanHeight(20);
			table.setHeaderHeight(40);
			if (isDecisionTable) {
				table.setHeaderSpans(dtIdSpan, dtConditionSpan, dtActionSpan);
			} else {
				table.setHeaderSpans(expIdSpan, expConditionSpan, expActionSpan);
			}
		}
		return table;
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		indeterminateProgress("", true);
		super.onFailure(event);
	}
	
	public void makeDirty(boolean isDecisionTable) {
		if (isDecisionTable) {
			setDecisionTablePageDirtyState(true);
		} else {
			setExceptionTableDirtyState(true);
		}
		makeDirty();
		enableValidate(false);
	}
	
	public void refreshCells() {
		for (int rowNum : fCellsToHighlightMap.keySet()) {
			List<Integer> colList = fCellsToHighlightMap.get(rowNum);
			for (int colNum :  colList) {
				getDecisionTableDataGrid().refreshCellStyle(rowNum, colNum);
			}
		}
	}
	
	public void enableCells(boolean decisionTable) {
		for (String id : decisionTable ? fDtTableCellsToDisableMap.keySet() : fExpTableCellsToDisableMap.keySet()) {
			int rowNum = -1;
			if (decisionTable ? !fDtTableRowCacheMap.isEmpty() : !fExpTableRowCacheMap.isEmpty()) {
				if (decisionTable ? fDtTableRowCacheMap.containsKey(id) : fExpTableRowCacheMap.containsKey(id)) {
					rowNum = Integer.parseInt(decisionTable ? fDtTableRowCacheMap.get(id) : fExpTableRowCacheMap.get(id));
				}
			} else {
				rowNum = Integer.parseInt(getRecordNumber(id, decisionTable));
			}
			Set<String> colList = decisionTable ? fDtTableCellsToDisableMap.get(id) : fExpTableCellsToDisableMap.get(id);
			for (String fieldName : colList) {
				int colNum = decisionTable ? getDecisionTableDataGrid().getFieldNum(fieldName) 
						: getExceptionTableDataGrid().getFieldNum(fieldName);
				if (decisionTable) {
					getDecisionTableDataGrid().refreshCell(rowNum, colNum);
				} else {
					getExceptionTableDataGrid().refreshCell(rowNum, colNum);
				}
			}
		}
	}
	
	public boolean canEdit(String ruleId, String fieldName, boolean decisionTable) {
		if (decisionTable ? 
				fDtTableCellsToDisableMap.size() > 0 : fExpTableCellsToDisableMap.size() >0) {
			for (String id : decisionTable ? fDtTableCellsToDisableMap.keySet() : fExpTableCellsToDisableMap.keySet()) {
				if (id.equals(ruleId)) {
					Set<String> colList = decisionTable ?
							fDtTableCellsToDisableMap.get(id) : fExpTableCellsToDisableMap.get(id);
					if (colList.contains(fieldName)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public void removeCellHighlights() {
		showCellHighlight = false;
		refreshCells();
		fCellsToHighlightMap.clear();
	}
	
	public String getURI() {
		return projectName +  table.getFolder() + table.getName();
	}

	public void setRowsToHighlight(List<String> rulesToHighlightOnPage) {
		fRowsToHighlightOnPage.clear();
		if (rulesToHighlightOnPage != null) {
			for (int i = 0; i < rulesToHighlightOnPage.size(); i++) {
				String ruleId = rulesToHighlightOnPage.get(i);
				int rowNumber = -1;
				try {
					rowNumber = Integer.parseInt(this.getRecordNumber(ruleId, true));
				} catch (NumberFormatException nfe) { } //do nothing
				if (rowNumber == -1) {
					continue;
				}
				fRowsToHighlightOnPage.add(rowNumber);
			}
		}
	}

	public List<Integer> getRowsToHighlight() {
		return Collections.unmodifiableList(fRowsToHighlightOnPage);
	}

	public void removeRowHighlights() {
		fRowsToHighlightOnPage.clear();
	}

	protected String getRecordNumber(String id, boolean decisionTable) {
		if (decisionTable) {
			if (fDtTableRowCacheMap.isEmpty()) {
				initRecordCache(decisionTable);
			}
		} else {
			if (fExpTableRowCacheMap.isEmpty()) {
				initRecordCache(decisionTable);
			}
		}
		return decisionTable ? fDtTableRowCacheMap.get(id) : fExpTableRowCacheMap.get(id);
	}
	
	protected void initRecordCache(boolean decisionTable) {
		int i = 0;
		if (decisionTable) {
			fDtTableRowCacheMap.clear();
			for(ListGridRecord record : getDecisionTableDataGrid().getRecords()) {
				TableRule rule = (TableRule)record.getAttributeAsObject("rule");
				fDtTableRowCacheMap.put(rule.getId(), Integer.toString(i));
				i++;
			}
		} else {
			fExpTableRowCacheMap.clear();
			for(ListGridRecord record : getExceptionTableDataGrid().getRecords()) {
				TableRule rule = (TableRule)record.getAttributeAsObject("rule");
				fExpTableRowCacheMap.put(rule.getId(), Integer.toString(i));
				i++;
			}
		}
	}

	protected void addRecordCache(boolean decisionTable) {
		if (decisionTable) {
			int recordCnt = getDecisionTableDataGrid().getRecords().length;
			ListGridRecord record = getDecisionTableDataGrid().getRecord(recordCnt - 1);
			TableRule rule = (TableRule)record.getAttributeAsObject("rule");
			fDtTableRowCacheMap.put(rule.getId(), Integer.toString(recordCnt - 1));
		} else {
			int recordCnt = getExceptionTableDataGrid().getRecords().length;
			ListGridRecord record = getExceptionTableDataGrid().getRecord(recordCnt - 1);
			TableRule rule = (TableRule)record.getAttributeAsObject("rule");
			fExpTableRowCacheMap.put(rule.getId(), Integer.toString(recordCnt - 1));
		}
	}

	public Map<String, Set<String>> getDtTableCellsToDisableMap() {
		return fDtTableCellsToDisableMap;
	}
	
	public Map<String, Set<String>> getExpTableCellsToDisableMap() {
		return fExpTableCellsToDisableMap;
	}
	
	public void disableToolBar(boolean isDecisionTable) {
		if (isDecisionTable) {
			dtAddbutton.setDisabled(true);
			dtRemovebutton.setDisabled(true);
			dtDupeRowbutton.setDisabled(true);
			showTableAnalyzerViewButton.setDisabled(true);
			WebStudio.get().getApplicationToolBar()
			.disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
			dtFilterMenu.setDisabled(true);
		} else {
			expAddRowbutton.setDisabled(true);
			expRemoveRowbutton.setDisabled(true);
			expDupeRowbutton.setDisabled(true);
			expFilterMenu.setDisabled(true);
		}
	}

	public void disableToolBarForExistingWithRecords(boolean isDecisionTable, int recordsLength) {
		boolean enable = recordsLength > 0;
		if (isDecisionTable) {
			dtAddbutton.setDisabled(!enable);
			dtRemovebutton.setDisabled(enable);
			dtDupeRowbutton.setDisabled(enable);
			showTableAnalyzerViewButton.setDisabled(!enable);
			dtFilterMenu.setDisabled(!enable);
			WebStudio.get().getApplicationToolBar()
			.disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, !enable);
			
			if (recordsLength == 0) {
				dtAddbutton.setDisabled(false);
				dtRemovebutton.setDisabled(true);
				dtDupeRowbutton.setDisabled(true);
			}
			
		} else {
			expAddRowbutton.setDisabled(!enable);
			expRemoveRowbutton.setDisabled(enable);
			expDupeRowbutton.setDisabled(enable);
			expFilterMenu.setDisabled(!enable);
			
			if (recordsLength == 0) {
				expAddRowbutton.setDisabled(false);
				expRemoveRowbutton.setDisabled(true);
				expDupeRowbutton.setDisabled(true);
			}

		}
	}
	
	public void disableAllToolBar() {
		disableToolBar(true);
		disableToolBar(false);
	}

	protected ToolStrip createPagesControl() {				
		ToolStrip pageToolStrip = new ToolStrip();
		pageToolStrip.setAlign(Alignment.CENTER);
		pageToolStrip.setWidth100();
		pageToolStrip.setHeight(TOOL_BAR_HEIGHT);
		return pageToolStrip;
	}

	/**
	 * @param strip
	 * @param pages
	 * @param type
	 */
	protected void initializePagesControl(int pages) {
		this.pages = pages;

		if (pagesContainer != null) {
			pageToolStrip.removeMember(pagesContainer);
			pagesContainer.destroy();			
		}

		if (!isReadOnly()) {
			if (this.pages <= 1 && !table.getDecisionTable().isShowAll()) {
				return;
			}
		} else { //For read-only revision views
			if (this.pages <= 1) {
				return;
			}						
		}
		
		boolean showPrevButton = false, showNextButton = false;
		if (currentPage >  1) {
			showPrevButton = true;
		}
		if (currentPage <  this.pages) {
			showNextButton = true;
		}
		
		pagesContainer = new HStack();
		pagesContainer.setWidth100();
		pagesContainer.setAlign(Alignment.CENTER);
		pagesContainer.setAlign(VerticalAlignment.CENTER);

		if (!isReadOnly()) {
			dtShowAllbutton = new ToolStripButton();
			dtShowAllbutton.setTitle(table.getDecisionTable().isShowAll() ? dtMessages.dt_pagination_showPages() : dtMessages.dt_pagination_showAll());
			dtShowAllbutton.setTitleStyle("ws-dt-pageToolstrip-button-title");			
			dtShowAllbutton.setTooltip(table.getDecisionTable().isShowAll() ? dtMessages.dt_pagination_showByPage() : dtMessages.dt_pagination_showAllRows());
			dtShowAllbutton.setShowRollOver(true);
			dtShowAllbutton.setShowFocused(false);
			dtShowAllbutton.addClickHandler(this);
//			dtShowAllbutton.setActionType(SelectionType.CHECKBOX);
			dtShowAllbutton.setValign(VerticalAlignment.CENTER);	
//			dtShowAllbutton.setSelected(table.getDecisionTable().isShowAll());
			if (table.getDecisionTable().isShowAll()) {
				pagesContainer.addMember(dtShowAllbutton);
				pageToolStrip.addMember(pagesContainer);				
				return;
			}
		}

		firstbutton = new ToolStripButton();
		firstbutton.setTitle("&laquo; " + dtMessages.dt_pagination_firstPage());
		firstbutton.setTooltip(dtMessages.dt_pagination_firstPage_tooltip());
		firstbutton.setTitleStyle("ws-dt-pageToolstrip-button-title");
		firstbutton.setShowRollOver(true);
		firstbutton.setShowFocused(false);
		firstbutton.addClickHandler(this);
		firstbutton.setValign(VerticalAlignment.CENTER);
		pagesContainer.addMember(firstbutton);

		prevbutton = new ToolStripButton();
		prevbutton.setTitle("&lsaquo; " + dtMessages.dt_pagination_previousPage());
		prevbutton.setTooltip(dtMessages.dt_pagination_previousPage_tooltip());
		prevbutton.setTitleStyle("ws-dt-pageToolstrip-button-title");
		prevbutton.setShowRollOver(true);
		prevbutton.setShowFocused(false);
		prevbutton.addClickHandler(this);
		prevbutton.setValign(VerticalAlignment.CENTER);		
		pagesContainer.addMember(prevbutton);
		
		if (showPrevButton) {
			ToolStripButton separator = new ToolStripButton("|");
			separator.setShowRollOver(false);
			separator.setShowFocused(false);
			separator.setWidth(3);
			pagesContainer.addMember(separator);
		}	
		int noOfPageButtons = Math.min(PAGES_WINDOW, pages);	
		initializePageButtons(noOfPageButtons, pagesContainer);
		if (showNextButton) {
			ToolStripButton separator = new ToolStripButton("|");
			separator.setShowRollOver(false);
			separator.setShowFocused(false);
			separator.setWidth(3);
			pagesContainer.addMember(separator);
		}	
		
		nextbutton = new ToolStripButton();
		nextbutton.setTitle(dtMessages.dt_pagination_nexttPage() + " &rsaquo;");
		nextbutton.setTooltip(dtMessages.dt_pagination_nextPage_tooltip());
		nextbutton.setTitleStyle("ws-dt-pageToolstrip-button-title");
		nextbutton.setShowRollOver(true);
		nextbutton.setShowFocused(false);
		nextbutton.addClickHandler(this);
		nextbutton.setValign(VerticalAlignment.CENTER);		
		pagesContainer.addMember(nextbutton);

		lastbutton = new ToolStripButton();
		lastbutton.setTitle(dtMessages.dt_pagination_lastPage() + " &raquo;");
		lastbutton.setTooltip(dtMessages.dt_pagination_lastPage_tooltip());
		lastbutton.setTitleStyle("ws-dt-pageToolstrip-button-title");
		lastbutton.setShowRollOver(true);
		lastbutton.setShowFocused(false);		
		lastbutton.addClickHandler(this);
		lastbutton.setValign(VerticalAlignment.CENTER);		
		pagesContainer.addMember(lastbutton);
		
		createGotoPageForm();
		
		if (!isReadOnly()) {
			ToolStripButton separator = new ToolStripButton("|");
			separator.setShowRollOver(false);
			separator.setShowFocused(false);
			separator.setWidth(3);
			pagesContainer.addMember(separator);
			pagesContainer.addMember(dtShowAllbutton);			
		}
		
		showPageButtons(noOfPageButtons, currentPage, showPrevButton, showNextButton);
		
		pageToolStrip.addMember(pagesContainer);
	}
	
	/**
	 * @param noOfPageButtons
	 * @param stack
	 */
	protected void initializePageButtons(int noOfPageButtons, HStack stack) {

		pageLabel = new ToolStripButton();
		pageLabel.setTitle(dtMessages.dt_pagination_page() + ": ");
		pageLabel.setTitleStyle("ws-dt-pageToolstrip-title-label");
		pageLabel.setAlign(Alignment.LEFT);
		pageLabel.setShowRollOver(false);
		pageLabel.setShowHover(false);
		pageLabel.setShowGrip(false);
		pageLabel.setShowOverCanvas(false);
		pageLabel.setShowFocused(false);
		stack.addMember(pageLabel);

		prevDotlbl = new ToolStripButton();
		prevDotlbl.setTitle("...");
		prevDotlbl.setShowRollOver(false);
		prevDotlbl.setShowFocused(false);
		prevDotlbl.setValign(VerticalAlignment.CENTER);		
		stack.addMember(prevDotlbl);			
		
		for (int i = 1 ; i <= noOfPageButtons; i++) {
			final ToolStripButton button = new ToolStripButton();
			button.setActionType(SelectionType.RADIO);
			button.setTitle(Integer.toString(i));			
			button.setTooltip(dtMessages.dt_pagination_page_tooltip() + " " + i);
			button.setRadioGroup(TOOL_PAGES_BUTTON_GROUP);
			button.setTitleStyle("ws-dt-pageToolstrip-button-title");
			button.setShowRollOver(true);
			button.setShowFocused(false);
			button.addClickHandler(this);
			button.setWidth(5);
			button.setValign(VerticalAlignment.CENTER);
			toolStripButtonMap.put(i, button);			
			stack.addMember(button);
		}

		nextDotlbl = new ToolStripButton();
		nextDotlbl.setTitle("...");
		nextDotlbl.setShowRollOver(false);
		nextDotlbl.setShowFocused(false);
		nextDotlbl.setValign(VerticalAlignment.CENTER);		
		stack.addMember(nextDotlbl);
		
		pagesLabel = new ToolStripButton();
		pagesLabel.setTitle("( " + dtMessages.dt_pagination_page_of() + " " + pages + " " + dtMessages.dt_pagination_pages() + " )");
		pagesLabel.setTitleStyle("ws-dt-pageToolstrip-button-title");
		pagesLabel.setAlign(Alignment.RIGHT);
		pagesLabel.setShowRollOver(false);
		pagesLabel.setShowFocused(false);		
		pagesLabel.setValign(VerticalAlignment.CENTER);
		stack.addMember(pagesLabel);		
	}

	protected void showPageButtons(int noOfPageButtons, int currentPage, boolean showPrevButton, boolean showNextButton) {

		int prevPageButtons = (noOfPageButtons - 1) / 2;

		if ((currentPage - prevPageButtons) > 1) {
			prevDotlbl.setVisible(true);
		} else {
			prevDotlbl.setVisible(false);
			prevPageButtons = currentPage - 1;
		}
		int nextPageButtons = (noOfPageButtons - 1) - prevPageButtons;
		
		if ((currentPage + nextPageButtons) < pages) {
			nextDotlbl.setVisible(true);
		} else {			
			nextDotlbl.setVisible(false);
			nextPageButtons = pages - currentPage;
			prevPageButtons = (noOfPageButtons - 1) - nextPageButtons;
		}
		
		int start = currentPage - prevPageButtons;
		for (int i = 1 ; i <= noOfPageButtons; i++) {
			toolStripButtonMap.get(i).setTitle(Integer.toString(start));
			toolStripButtonMap.get(i).setTooltip(dtMessages.dt_pagination_page_tooltip() + " " + i);
			if (currentPage == start) {
				toolStripButtonMap.get(i).setSelected(true);
			}
			start++;
		}

		if (showPrevButton) {
			firstbutton.setVisible(true);
			prevbutton.setVisible(true);
		} else {
			firstbutton.setVisible(false);
			prevbutton.setVisible(false);
		}

		if (showNextButton) {
			nextbutton.setVisible(true);
			lastbutton.setVisible(true);
		} else {
			nextbutton.setVisible(false);
			lastbutton.setVisible(false);
		}
	}
	
	private void createGotoPageForm() {
		ToolStripButton separator = new ToolStripButton("|");
		separator.setShowRollOver(false);
		separator.setShowFocused(false);
		separator.setWidth(3);
		pagesContainer.addMember(separator);
		
		gotoPageForm = new DynamicForm();
		gotoPageTextItem = new TextItem("pageNum", dtMessages.dt_pagination_page_tooltip());
		gotoPageTextItem.setTitleStyle("ws-dt-pageToolstrip-button-title");
		gotoPageTextItem.setWidth(50);
		gotoPageTextItem.setHeight(20);
		gotoPageTextItem.setAlign(Alignment.LEFT);
		gotoPageTextItem.setTitleAlign(Alignment.RIGHT);
		gotoPageTextItem.setTitleVAlign(VerticalAlignment.CENTER);
		gotoPageTextItem.setWrapTitle(false);
		gotoPageTextItem.setShowFocused(false);
		gotoPageTextItem.addKeyPressHandler(new KeyPressHandler() {
					
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter")) {
					int pageNum = 0; 
					try {
						pageNum = Integer.parseInt(event.getItem().getValue().toString());
					} catch (NumberFormatException nfe) { }
					
					if (pageNum > 0 && pageNum <= pages) {
						loadPage(pageNum);
					} else {
						ErrorMessageDialog.showError(dtMessages.dt_pagination_invalidPage_message());						
					}
				}				
			}
		});

		gotoPageForm.setFields(gotoPageTextItem);		
		pagesContainer.addMember(gotoPageForm);		
	}
	
	protected void loadPreviousPage() {
		if (this.isDecisionTablePageDirty()) {
			CustomSC.confirm(globalMsg.text_confirm(), dtMessages.dt_pagination_discardPage_message(), new BooleanCallback() {
				public void execute(Boolean confirm) {
					if (confirm != null && confirm) {
						currentPage--;
						loadEditorPageData(selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
						setDecisionTablePageDirtyState(false);
						updateTitle();
					}	
				}
			});
		} else {
			currentPage--;
			loadEditorPageData(this.selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());					
		}		
	}
	
	protected void loadNextPage() {
		if (this.isDecisionTablePageDirty()) {
			CustomSC.confirm(globalMsg.text_confirm(), dtMessages.dt_pagination_discardPage_message(), new BooleanCallback() {
				public void execute(Boolean confirm) {
					if (confirm != null && confirm) {
						currentPage++;
						loadEditorPageData(selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
						setDecisionTablePageDirtyState(false);
						updateTitle();
					}	
				}
			});
		} else {
			currentPage++;
			loadEditorPageData(this.selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());					
		}				
	}

	protected void loadPage(final int pageNum) {
		if (this.isDecisionTablePageDirty()) {
			CustomSC.confirm(globalMsg.text_confirm(), dtMessages.dt_pagination_discardPage_message(), new BooleanCallback() {
				public void execute(Boolean confirm) {
					if (confirm != null && confirm) {
						currentPage = pageNum;
						loadEditorPageData(selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
						setDecisionTablePageDirtyState(false);
						updateTitle();
					} else {
						int noOfPageButtons = Math.min(PAGES_WINDOW, pages);
						for (int i = 1 ; i <= noOfPageButtons; i++) {
							int pageNo = Integer.parseInt(toolStripButtonMap.get(i).getTitle());
							if (pageNo == pageNum) {
								toolStripButtonMap.get(i).setSelected(false);
							} 
							
							if (pageNo == currentPage) {
								toolStripButtonMap.get(i).setSelected(true);
							}
						}
					}
				}
			});
		} else {
			currentPage = pageNum;
			loadEditorPageData(this.selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());					
		}						
	}

	protected void loadPages(final boolean showAll) {
		if (this.isDecisionTablePageDirty()) {
			CustomSC.confirm(globalMsg.text_confirm(), dtMessages.dt_pagination_discardPage_message(), new BooleanCallback() {
				public void execute(Boolean confirm) {
					if (confirm != null && confirm) {
						currentPage = 1;
						loadEditorPageData(selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL(), showAll);
						setDecisionTablePageDirtyState(false);
						updateTitle();
					} else {
//						dtShowAllbutton.setSelected(!showAll);
					}
				}
			});
		} else {
			currentPage = 1;
			loadEditorPageData(this.selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL(), showAll);					
		}						
	}

	/**
	 * Load data for the specific editor
	 */
	protected void loadEditorPageData(NavigatorResource selectedRecord, String URL) {
		String artifactPath = selectedRecord.getId().replace("$", "/");
		projectName = artifactPath.substring(0, artifactPath.indexOf("/"));
		String artifactExtention = artifactPath.substring(artifactPath.indexOf(".") + 1, artifactPath.length());
		artifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));

		ArtifactUtil.addHandlers(this);

		this.request.clearRequestParameters();
		this.request.setMethod(HttpMethod.GET);
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN,
				artifactExtention));
		this.request.addRequestParameters(new RequestParameter("pageNum", currentPage));
		this.request.addRequestParameters(new RequestParameter("tableType", "DECISION_TABLE"));
		if (getAdditionalRequestParams() != null) {
			for (RequestParameter rp : getAdditionalRequestParams()) {
				this.request.addRequestParameters(rp);
			}
		}
		
		String url = null;
		if (isReadOnly() && revisionId != null) {
			url = ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL();
			this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
		} else if (URL == null) {
			url = ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL();
		} else {
			url = URL;
		}
		
		request.submit(url);
		
		if (showLoading) {
			LoadingMask.showMask();
		}
	}

	protected void loadEditorPageData(NavigatorResource selectedRecord, String URL, boolean showAll) {
		String artifactPath = selectedRecord.getId().replace("$", "/");
		projectName = artifactPath.substring(0, artifactPath.indexOf("/"));
		String artifactExtention = artifactPath.substring(artifactPath.indexOf(".") + 1, artifactPath.length());
		artifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));

		ArtifactUtil.addHandlers(this);

		this.request.clearRequestParameters();
		this.request.setMethod(HttpMethod.GET);
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN,
				artifactExtention));
		this.request.addRequestParameters(new RequestParameter("pageNum", currentPage));
		this.request.addRequestParameters(new RequestParameter("tableType", "DECISION_TABLE"));
		this.request.addRequestParameters(new RequestParameter("showAll", showAll));
		if (getAdditionalRequestParams() != null) {
			for (RequestParameter rp : getAdditionalRequestParams()) {
				this.request.addRequestParameters(rp);
			}
		}
		
		String url = null;
		if (isReadOnly()) {
			url = ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL();
			if(revisionId != null) {
				this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
			}
		} else if (URL == null) {
			url = ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL();
		} else {
			url = URL;
		}
		
		request.submit(url);
		
		if (showLoading) {
			LoadingMask.showMask();
		}
	}
	
	/**
	 * Check if DT editor page is dirty
	 * 
	 * @return boolean
	 */
	public boolean isDecisionTablePageDirty() {
		return isDecisionTablePageDirty;
	}

	/**
	 * Set state of DT editor page to dirty - true/false
	 * 
	 * @return boolean
	 */
	public void setDecisionTablePageDirtyState(boolean isDirty) {
		isDecisionTablePageDirty = isDirty;
		if (isDecisionTablePageDirty) {
			makeDirty();
		} else if (!isExceptionTableDirty) {
			setPropertyID(-1);
		}
	}

	/**
	 * Check if ET editor page is dirty
	 * 
	 * @return boolean
	 */
	public boolean isExceptionTableDirty() {
		return 	isExceptionTableDirty;
	}

	/**
	 * Set state of ET editor page to dirty - true/false
	 * 
	 * @return boolean
	 */
	public void setExceptionTableDirtyState(boolean isDirty) {
		isExceptionTableDirty = isDirty;
		if (isExceptionTableDirty) {
			makeDirty();
		} else if (!isDecisionTablePageDirty) {
			setPropertyID(-1);
		}
	}
	
	@Override
	public void addArtifactDiffLegend(VLayout layout) {
		if (editorToolStrip == null) {
			getEditorToolBar();
		}
		if (editorToolStrip != null) {
			editorToolStrip.addMember(DiffHelper.getDiffLegend());
		}
	}
	
	protected String getDecisionTableColumnId(int colNum) {
		return getColumnId(colNum, true);
	}

	protected String getExceptionTableColumnId(int colNum) {
		return getColumnId(colNum, false);
	}
	
	private String getColumnId(int colNum, boolean isDecisionTable) {
		List<Integer> conditionAreaIndices = isDecisionTable ? dtConditionAreaIndices : expConditionAreaIndices;
		Map<Integer, TableColumn> conditionAreaColumns = isDecisionTable ? dtConditionAreaColumns : expConditionAreaColumns;
		List<Integer> actionAreaIndices = isDecisionTable ? dtActionAreaIndices : expActionAreaIndices;
		Map<Integer, TableColumn> actionAreaColumns = isDecisionTable ? dtActionAreaColumns : expActionAreaColumns;
		
		String colId = null;
		int columnNumber = colNum - 1; //-1 to exclude column 0 (ID column)
		if (columnNumber >= 0 && columnNumber < conditionAreaIndices.size() && conditionAreaColumns.get(conditionAreaIndices.get(columnNumber)) != null) {
			colId = conditionAreaColumns.get(conditionAreaIndices.get(columnNumber)).getId();
		}
		if (colId == null) { //column is not in conditions
			columnNumber = columnNumber - conditionAreaIndices.size();//exclude count of condition columns.
			if (columnNumber >= 0 && columnNumber < actionAreaIndices.size() && actionAreaColumns.get(actionAreaIndices.get(columnNumber)) != null) {
				colId = actionAreaColumns.get(actionAreaIndices.get(columnNumber)).getId();
			}
		}
		
		return colId;
	}

	public boolean isMergeComplete() {
		boolean isComplete = true;
		if (modifications != null) {
			Collection<ModificationEntry> mods = modifications.values();
			for (ModificationEntry mod : mods) {
				if (!mod.isApplied()) {
					//ErrorMessageDialog.showError(mod.getModificationType() + " " + mod.getPreviousValue() + " " + mod.isApplied());
					isComplete = false;
					break;
				}
			}		
		}	
		return isComplete;
	}

	/**
	 * Returns a new column id.
	 * @param isDecisionTable
	 * @return
	 */
	protected int getNewColumnId(boolean isDecisionTable) {
		TableRuleSet tableRuleSet = isDecisionTable ? table.getDecisionTable() : table.getExceptionTable();
		List<TableColumn> columns = tableRuleSet.getColumns();
		int maxId = DecisionTableUtils.getColumnMaxID(columns);
		
		if (isDecisionTable) {
			return maxId > dt_currentColumnMaxId + 1 ? (dt_currentColumnMaxId=maxId) : ++dt_currentColumnMaxId;
		}
		else {
			return maxId > exp_currentMaxColumnId + 1 ? (exp_currentMaxColumnId=maxId) : ++exp_currentMaxColumnId;
		}
	}
}