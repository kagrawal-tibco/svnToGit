package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.addNewRecord;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.addNewTableRule;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustActionColumnName;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustColumnName;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.cacheCellEnables;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.fetchRecords;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getColumn;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getTableRuleVariable;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.initializeCellEnabledCache;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.isDuplicateColumn;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.populateFields;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.removeFromCellToDisableMap;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.removeHighlights;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.BrowserEvent;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentDataTransfer;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.MetaData;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.ParentArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.diff.DiffHelper.DTDiffType;
import com.tibco.cep.webstudio.client.diff.MergedDiffModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationEntry;
import com.tibco.cep.webstudio.client.diff.ModificationType;
import com.tibco.cep.webstudio.client.editor.TableDataGrid;
import com.tibco.cep.webstudio.client.i18n.DisplayUtils;
import com.tibco.cep.webstudio.client.merge.AbstractEditorMergeHandler;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;


/**
 * @author sasahoo
 *
 */
public abstract class AbstractDecisionTableEditor extends AbstractTableEditor implements CellContextClickHandler{

	private int editcolId;
	
	protected Menu contextMenu;

	public static final String DOT = ".";
	
	public static final String RULE_FN_IMPL_EXTENSION 	= "rulefunctionimpl";    //$NON-NLS-1$
	
	public AbstractDecisionTableEditor(NavigatorResource record, boolean loadDataAtStartup) {
		this(record, false, null, loadDataAtStartup, false);
	}

	public AbstractDecisionTableEditor(NavigatorResource record) {
		this(record, false, null, true, false);
	}
	
	public AbstractDecisionTableEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataOnStartUp, boolean artifactVersionDiff) {
		super(selectedRecord, isReadOnly, revisionId, loadDataOnStartUp, artifactVersionDiff);
	}
	
	@Override
	protected void initialize(){
		if (loadDataAtStartUp) {
			indeterminateProgress(dtMessages.loading_decision_table(), false);
		}
		super.initialize();
	}
	
	@Override
	public void close() {
		super.close();
		if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
				&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
			WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().clearComponents();
		}
		if (getDecisionTablePropertyTab() != null && 
				getDecisionTablePropertyTab().isVisible()) {
			getDecisionTablePropertyTab().destroy();
			propertyTab = null;
		}
		if (WebStudio.get().getEditorPanel().getTabs().length == 1) {
			if (WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null) {
				WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().close();
			}
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
	}

	protected void loadDeclarationTable() {
		argumentList = table.getArguments();
		ListGridField pathfield = new ListGridField("path", dtMessages.dt_declarations_gridHeader_path(), 200);
		ListGridField aliasfield = new ListGridField("alias", dtMessages.dt_declarations_gridHeader_alias(), 200);
		ListGridField arrayfield = new ListGridField("array", dtMessages.dt_declarations_gridHeader_array(), 200);
		
		declTable.setCanAcceptDroppedRecords(false);
		declTable.setCanEdit(false);
		declTable.setCanAcceptDrop(false);
		
		declTable.setFields(pathfield, aliasfield, arrayfield);
		
		ListGridRecord[] argRecords = new ListGridRecord[argumentList.size() + 1];
		for (int r= 0; r < argumentList.size(); r++) {
			ArgumentData arg = argumentList.get(r);
			ListGridRecord record = new ListGridRecord();
			record.setAttribute("path", arg.getPropertyPath());
			record.setAttribute("alias", arg.getAlias());
			record.setAttribute("array", arg.getArray());
			argRecords[r] = record;
		}
		declTable.setRecords(argRecords);
	}
	
	protected void loadDecisionTable(boolean init, boolean initDecisionTablePage, boolean newColumn, String colId, String colName) {
		if (table.getDecisionTable() != null && 
				table.getDecisionTable().getColumns() != null && 
				table.getDecisionTable().getColumns().size() > 0 ) {
			
			clearCache(true);
			ListGridField[] fields = populateFields(table.getDecisionTable(), 
												    dtConditionAreaIndices, 
												    dtConditionAreaColumns, 
												    dtActionAreaIndices, 
												    dtActionAreaColumns, 
												    dtConditionAreaFieldMap, 
												    dtActionAreaFieldMap,
												    table.getArguments());
			if (newColumn) {
				if (isHeaderGroupSupported) {
					dtTable.destroy();
					dtTable = createNewTable(true);
					addTableHandlers(dtTable);
					pageToolStrip.destroy();
					pageToolStrip = createPagesControl();
					initializePagesControl(table.getDecisionTable().getPages());
					dtSection.setItems(dtTable, pageToolStrip);
				}
				if (dtlastRecordId < explastRecordId) {
					dtlastRecordId = explastRecordId;
				}
				updateTableRuleVariables(decisionTableUpdateprovider, 
										 colId, 
						                 colName, 
		                                 table.getDecisionTable(), 
		                                 dtlastRecordId, 
		                                 dtConditionAreaColumns, 
		                                 dtActionAreaColumns);
			}
			
			if (decisionTableDataSource != null) {
				decisionTableDataSource.destroy();
			}
			
			//BE-17659 if all the rows are removed from DT we need to add blank row when it is re-opened
			if(table.getDecisionTable().getTableRules().size() == 0) {
				if (dtlastRecordId < explastRecordId) {
					dtlastRecordId = explastRecordId;
				}
				addRowToEmptyTable(false, dtConditionAreaColumns, dtActionAreaColumns, table.getDecisionTable(), dtlastRecordId);
			}
			
			decisionTableDataSource =  new DecisionTableDataSource(table.getDecisionTable().getColumns(), fields);
			dtTable.setDataSource(decisionTableDataSource);
			refreshColumnProperties(dtTable, table.getDecisionTable(), true);
			
			if (isHeaderGroupSupported && newColumn) {
				dtSection.setExpanded(false);
				dtSection.setExpanded(true);
			}
			
			ListGridRecord[] records = fetchRecords(table.getDecisionTable());
			 //DT diff functionality, set proper css for rules in DT diff view for decisionTable section.
			if (modifications!= null && !modifications.isEmpty()) {
				for (ListGridRecord record : records) {
					if (record instanceof TableRuleVariableRecord) {
						TableRuleVariableRecord trvr = (TableRuleVariableRecord)record;
						DTDiffType diffType = DTDiffType.DECISION_TABLE;
						ModificationEntry modificationEntry = modifications.get(diffType.getKeyPrefix() + "tableRule" + trvr.getRule().getId());
				        if (modificationEntry != null) {
				        	switch(modificationEntry.getModificationType()) {
				        	case ADDED: trvr.set_baseStyle("ws-dt-diff-add-style"); break;
				        	case DELETED: trvr.set_baseStyle("ws-dt-diff-remove-style"); break;
				        	//MODIFIED not needed since individual modified cells will be highlighted.
				        	//case UNCHANGED: break;
				        	}
				        }
					}
				}
			}
			if (init || initDecisionTablePage) {
				initializeCellEnabledCache(this, records, table, true);
			}

			disableToolBarForExistingWithRecords(true, records.length);

			if (records.length > 0) {

				dtlastRecordId = table.getDecisionTable().getLastRow();
				decisionTableDataSource.setCacheData(records);
				dtTable.fetchData(new Criteria(), new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if (!isReadOnly()) {
								ProblemMarker marker = getOnOpenMarker();
								if(marker instanceof DecisionTableProblemMarker) {
									DecisionTableProblemMarker dtProblemMarker = (DecisionTableProblemMarker) marker; 
									processProblemRecord(dtProblemMarker);
								}	
								
								if (getRulesToHighlight() != null) {
									List<String> rulesToHighlightOnPage = getRulesToHighlight().get(getCurrentPage());
									AbstractDecisionTableEditor.this.setRowsToHighlight(rulesToHighlightOnPage);
								}								
							}
							if (isAnalyzed())
								WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().run(true);
						}
					}
				});
			}
		} 
		if (init || initDecisionTablePage) {		
			if (table.getDecisionTable() == null) {
				TableRuleSet decisionTableRuleSet = new TableRuleSet(table, Table.DECISION_TABLE);
				List<TableColumn> columnList =  new ArrayList<TableColumn>();
				decisionTableRuleSet.setColumns(columnList);
				table.setDecisionTable(decisionTableRuleSet);
			}
			if (init) {
				addTableHandlers(dtTable);
//				dtTable.setEmptyMessage(dtMessages.dtcreatecolumnemptydesc());
			}	

			initializePagesControl(table.getDecisionTable().getPages());
		
		}		
	}

	protected void loadExceptionTable(boolean init, boolean newColumn, String colId, String colName) {
		if (table.getExceptionTable() != null && 
				table.getExceptionTable().getColumns() != null && 
				table.getExceptionTable().getColumns().size() > 0 ) {
			clearCache(false);
			ListGridField[] fields = populateFields(table.getExceptionTable(), 
													expConditionAreaIndices, 
													expConditionAreaColumns, 
													expActionAreaIndices, 
													expActionAreaColumns, 
													expConditionAreaFieldMap, 
													expActionAreaFieldMap,
													table.getArguments());
			if (newColumn) {
				if (isHeaderGroupSupported) {
					expTable.destroy();
					expTable = createNewTable(false);
					addTableHandlers(expTable);
					expSection.setItems(expTable);
				}
				if (explastRecordId < dtlastRecordId) {
					explastRecordId = dtlastRecordId;
				}
				updateTableRuleVariables(exceptionTableUpdateprovider, 
										 colId, 
						                 colName, 
		                                 table.getExceptionTable(), 
		                                 explastRecordId, 
		                                 expConditionAreaColumns, 
		                                 expActionAreaColumns);
			}
			
			if (exceptionTableDataSource != null) {
				exceptionTableDataSource.destroy();
			}
			
			//BE-17659 if all the rows are removed from DT we need to add blank row when it is re-opened
			if(table.getExceptionTable().getTableRules().size() == 0){
				if (explastRecordId < dtlastRecordId) {
					explastRecordId = dtlastRecordId;
				}
				addRowToEmptyTable(false, expConditionAreaColumns, expActionAreaColumns, table.getExceptionTable(), explastRecordId);
			}
			
			exceptionTableDataSource =  new DecisionTableDataSource(table.getExceptionTable().getColumns(), fields);
			expTable.setDataSource(exceptionTableDataSource);
			refreshColumnProperties(expTable, table.getExceptionTable(), false);
			
			if (isHeaderGroupSupported && newColumn) {
				expSection.setExpanded(false);
				expSection.setExpanded(true);
			}
			
			ListGridRecord[] records = fetchRecords(table.getExceptionTable());
			//DT diff functionality, set proper css for rules in DT diff view for exceptionTable section.
			if (modifications!= null && !modifications.isEmpty()) {
				for (ListGridRecord record : records) {
					if (record instanceof TableRuleVariableRecord) {
						TableRuleVariableRecord trvr = (TableRuleVariableRecord)record;
						DTDiffType diffType = DTDiffType.EXCEPTION_TABLE;
						ModificationEntry modificationEntry = modifications.get(diffType.getKeyPrefix() + "tableRule" + trvr.getRule().getId());
				        if (modificationEntry != null) {
				        	switch(modificationEntry.getModificationType()) {
				        	case ADDED: trvr.set_baseStyle("ws-dt-diff-add-style"); break;
				        	case DELETED: trvr.set_baseStyle("ws-dt-diff-remove-style"); break;
				        	//MODIFIED not needed since individual modified cells will be highlighted.
				        	//case UNCHANGED: break;
				        	}
				        }
					}
				}
			}
			
			if (init) {		
				initializeCellEnabledCache(this, records, table, false);
			}
			
			disableToolBarForExistingWithRecords(false, records.length);
			
			if (records.length > 0) {
				if (table.getExceptionTable().getLastRow() > explastRecordId)
					explastRecordId = table.getExceptionTable().getLastRow();
				exceptionTableDataSource.setCacheData(records);
				expTable.fetchData();
			}
		}
		
		if (init) {
			if (table.getExceptionTable() == null) {
				TableRuleSet expTableRuleSet = new TableRuleSet(table, Table.EXCEPTION_TABLE);
				List<TableColumn> columnList =  new ArrayList<TableColumn>();
				expTableRuleSet.setColumns(columnList);
				table.setExceptionTable(expTableRuleSet);
			}
			addTableHandlers(expTable);
			expTable.setEmptyMessage(dtMessages.dtcreatecolumnemptydesc());
		}
	}

	private void addRowToEmptyTable(boolean isDecisionTable,
			Map<Integer, TableColumn> dtConditionAreaColumns,
			Map<Integer, TableColumn> dtActionAreaColumns,
			TableRuleSet ruleSet,
			long dtlastRecordId
			) {
		String newruleId = null;
		if(isDecisionTable){
			dtlastRecordId = dtlastRecordId + 1;
			setDtlastRecordId(dtlastRecordId);
			newruleId = Long.toString(dtlastRecordId);
		} else{
			explastRecordId = explastRecordId + 1;
			setExplastRecordId(explastRecordId);
			newruleId = Long.toString(explastRecordId);
		}
			
		TableRule newRule = new TableRule(newruleId);
		List<TableRuleVariable> conditions = new ArrayList<TableRuleVariable>();
		List<TableRuleVariable> actions = new ArrayList<TableRuleVariable>();
		addNewTableRule(newruleId, newRule, dtConditionAreaColumns, conditions);
		addNewTableRule(newruleId, newRule, dtActionAreaColumns, actions);
		newRule.setConditions(conditions);
		newRule.setActions(actions);
		ruleSet.getTableRules().add(newRule);
	}
	
	public void loadDependencies(boolean isDecisionTablePage) {
		//Do not refresh the Table Analyzer pane on page load
		if (isDecisionTablePage == false) {
			if (WebStudio.get().getEditorPanel().getLeftPane().isVisible() 
					&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
					&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
				WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().refresh(table);
				setShowTableAnalyzer(true);
			}
		}
		
		if (WebStudio.get().getEditorPanel().getBottomPane().isVisible()) {
			setShowProperties(true);			
		}

		propertyTab = new DecisionTablePropertyTab();
		WebStudio.get().getEditorPanel().getPropertiesPane().fillProperties(propertyTab);
		propertyTab.refresh(this, table, null, null, true, false, false, true);
	}
	
	protected void addTableHandlers(final TableDataGrid table) {
		table.addClickHandler(this);
		table.addCellContextClickHandler(this);
		table.addEditorEnterHandler(this);
		table.addEditCompleteHandler(this);
		table.addEditorExitHandler(this);
		table.addRecordDropHandler(this);
		table.addEditorExitHandler(this);
		table.addFilterEditorSubmitHandler(this);
		table.addCellClickHandler(new CellClickHandler() {
			
			@Override
			public void onCellClick(CellClickEvent event) {
				
				if(event.getColNum()==0){
					Window dropDownPopup = new Window();
			        dropDownPopup.setShowMinimizeButton(false);
			        dropDownPopup.setShowMaximizeButton(false);
			        dropDownPopup.setShowHeader(false);
			        dropDownPopup.setShowFooter(false);
			        dropDownPopup.setCanDragReposition(false);
			        dropDownPopup.setShowStatusBar(false);
			        dropDownPopup.setShowEdges(false);
			        dropDownPopup.setIsModal(true);
			        dropDownPopup.setDismissOnEscape(true);
			        dropDownPopup.setDismissOnOutsideClick(true);
			        dropDownPopup.setHeight(150);
			        dropDownPopup.setWidth(400);
			        dropDownPopup.setShowCustomScrollbars(true);
			        dropDownPopup.setScrollbarSize(10);
			        
			        if(event.getY()+ dropDownPopup.getHeight()>= com.google.gwt.user.client.Window.getClientHeight()){
			        	dropDownPopup.moveTo(event.getX()+10 , event.getY() - dropDownPopup.getHeight());
			        }else{
			        	dropDownPopup.moveTo(event.getX()+10, event.getY());
			        }
			        
			        DetailViewer detailViewer =  new  DetailViewer();
			        detailViewer.setDataSource(table.getDataSource());
					Criteria criteria = new Criteria();
					criteria.addCriteria("id", event.getRecord().getAttribute("id"));
					detailViewer.fetchData(criteria);
					dropDownPopup.addItem(detailViewer);
					dropDownPopup.show();
				}
				
			}
		});
		
		if (table == dtTable) {
			decisionTableEditorcustomizer = null;
			decisionTableEditorcustomizer = new TableGridEditorCustomizer(this);
			decisionTableEditorcustomizer.setDecisionTable(true);
			table.setEditorCustomizer(decisionTableEditorcustomizer);
		} else {
			exceptionTableEditorcustomizer = null;
			exceptionTableEditorcustomizer = new TableGridEditorCustomizer(this);
			exceptionTableEditorcustomizer.setDecisionTable(false);
			table.setEditorCustomizer(exceptionTableEditorcustomizer);
		}
	}

	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.menu.events.ClickHandler#onClick(com.smartgwt.client.widgets.menu.events.MenuItemClickEvent)
	 */
	@Override
	public void onClick(MenuItemClickEvent event) {
		if (event.getSource() == dtCustomCondItem) {
			addConditionColumn(dtMessages.dt_column_customCondition() + " ", null, true, true);
		} else if (event.getSource() == dtCustomActionItem) {
			int condSize = dtConditionAreaFieldMap.size();
			if (condSize == 0) {
				CustomSC.say(dtMessages.dt_atleastOne_condition_message());
				return;
			}
			addActionColumn(dtMessages.dt_column_customAction() + " ", null, true, true);
		} else if (event.getSource() == expCustomCondItem) {
			addConditionColumn(dtMessages.dt_column_customCondition() + " ", null, true, false);
		} else if (event.getSource() == expCustomActionItem) {
			int condSize = expConditionAreaFieldMap.size();
			if (condSize == 0) {
				CustomSC.say(dtMessages.dt_atleastOne_condition_message());
				return;
			}
			addActionColumn(dtMessages.dt_column_customAction() + " ", null, true, false);
		} else if (event.getSource() == dtFilterItem) {
			executeFilter(dtTable, true, true, true);
		} else if (event.getSource() == dtCustomFilterItem) {
			executeFilter(dtTable, false, true, true);
		} else if (event.getSource() == dtCustomFilterBuilderItem) {
			executeFilter(dtTable, false, false, true);
		} else if (event.getSource() == dtNoFilterItem) {
			executeFilter(dtTable, false, false, false);
		} else if (event.getSource() == expFilterItem) {
			executeFilter(expTable, true, true, true);
		} else if (event.getSource() == expCustomFilterItem) {
			executeFilter(expTable, false, true, true);
		} else if (event.getSource() == expCustomFilterBuilderItem) {
			executeFilter(expTable, false, false, true);
		} else if (event.getSource() == expNoFilterItem) {
			executeFilter(expTable, false, false, false);
		} 
	}
	
	/**
	 * @param tablegrid
	 */
	public void removeFilter(TableDataGrid tablegrid) {
		if (tablegrid.getFields().length > 0 ) {
			if (tablegrid.getFilterEditorCriteria() != null) {
				tablegrid.clearCriteria();
				tablegrid.filterData();
			}
			tablegrid.setShowFilterEditor(false);
		}
	}
	
	/**
	 * @param tablegrid
	 * @param datasource
	 * @param simpleFilter
	 * @param customFilter
	 * @param show
	 */
	private void executeFilter(TableDataGrid tablegrid,	
			                boolean simpleFilter, 
			                boolean customFilter, 
			                boolean show) {
		removeHighlights(this);
		if (show) {
			if (tablegrid.getRecords().length > 0) {
				tablegrid.clearCriteria();
				tablegrid.filterData();
				tablegrid.setShowFilterEditor(true);
				if (!simpleFilter) {
					new DecisionCustomFilterDialog(tablegrid, 
							tablegrid == dtTable ? decisionTableDataSource : exceptionTableDataSource, customFilter).draw();
				}
			}
		} else {
			removeFilter(tablegrid);
		}
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof ToolStripButton) {
			ToolStripButton button = (ToolStripButton)event.getSource();
			if (button.getTitle() != null) {
				if (button == firstbutton) {
					loadPage(1);
				} else if (button == prevbutton) {
					loadPreviousPage();
				} else if (button == nextbutton) {
					loadNextPage();
				} else if (button == lastbutton) {
					loadPage(pages);
				} else if (toolStripButtonMap.values().contains(button)) {
					int pageNum = Integer.parseInt(button.getTitle());
					loadPage(pageNum);
				}
			}
		}
		
		if (event.getSource() == dtAddbutton) {
			if (dtlastRecordId < explastRecordId) {
				dtlastRecordId = explastRecordId;
			}
			addRow(dtTable, decisionTableDataSource, decisionTableUpdateprovider, table.getDecisionTable(), dtlastRecordId, dtConditionAreaColumns, dtActionAreaColumns);
		} else if (event.getSource() == expAddRowbutton) {
			if (explastRecordId < dtlastRecordId) {
				explastRecordId = dtlastRecordId;
			}
			addRow(expTable, exceptionTableDataSource, exceptionTableUpdateprovider, table.getExceptionTable(), explastRecordId, expConditionAreaColumns, expActionAreaColumns);
		} else if (event.getSource() == dtRemovebutton) {
			removeRow(dtTable, table.getDecisionTable(), decisionTableUpdateprovider, decisionTableDataSource);
		} else if (event.getSource() == expRemoveRowbutton) {
			removeRow(expTable, table.getExceptionTable(), exceptionTableUpdateprovider, exceptionTableDataSource);
		} else if (event.getSource() == dtDupeRowbutton) {
			if (dtlastRecordId < explastRecordId) {
				dtlastRecordId = explastRecordId;
			}
			duplicateRow(dtTable, decisionTableDataSource, decisionTableUpdateprovider, table.getDecisionTable(), dtlastRecordId, dtConditionAreaColumns, dtActionAreaColumns);
		}  else if (event.getSource() == expDupeRowbutton) {
			if (explastRecordId < dtlastRecordId) {
				explastRecordId = dtlastRecordId;
			}
			duplicateRow(expTable, exceptionTableDataSource, exceptionTableUpdateprovider, table.getExceptionTable(), explastRecordId, expConditionAreaColumns, expActionAreaColumns);
		} else if (event.getSource() == dtShowAllbutton) {
//			boolean showAll = "Show All".equals(dtShowAllbutton.getTitle()) ? true : false; 
			loadPages("Show All".equals(dtShowAllbutton.getTitle()));
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		} else if (event.getSource() == dtTable) {
			if (isReadOnly()) {
				dtRemovebutton.setDisabled(true);
				dtDupeRowbutton.setDisabled(true);
			} else {
				if (dtTable.getSelectedRecords().length > 0 ) {
					dtRemovebutton.setDisabled(false);
					dtDupeRowbutton.setDisabled(false);
				} else {
					dtRemovebutton.setDisabled(true);
					dtDupeRowbutton.setDisabled(true);
				}
			}
			if (dtTable.getSelectedRecords().length == 0) {
				propertyTab.refresh(this, table, null, null, true, false, false, true);
			}
			ListGridRecord record = dtTable.getSelectedRecord();
			if (record != null) {
				TableRule rule = (TableRule)record.getAttributeAsObject("rule");
				propertyTab.refresh(this, table, rule, null, false, true, false, true);
			}
		} else if (event.getSource() == expTable) {
			if (isReadOnly()) {
				expRemoveRowbutton.setDisabled(true);
				expDupeRowbutton.setDisabled(true);
			} else {
				if (expTable.getSelectedRecords().length > 0 ) {
					expRemoveRowbutton.setDisabled(false);
					expDupeRowbutton.setDisabled(false);
				} else {
					expRemoveRowbutton.setDisabled(true);
					expDupeRowbutton.setDisabled(true);
				}
			}
			if (expTable.getSelectedRecords().length == 0) {
				propertyTab.refresh(this, table, null, null, true, false, false, false);
			}
			ListGridRecord record = expTable.getSelectedRecord();
			if (record != null) {
				TableRule rule = (TableRule)record.getAttributeAsObject("rule");
				propertyTab.refresh(this, table, rule, null, false, true, false, false);
			}
		} 
	}
		
	/**
	 * @param dtTable
	 * @param ruleset
	 * @param decisionTableUpdateprovider
	 * @param decisionTableDataSource
	 */
	protected void removeRow(final TableDataGrid dtTable, 
			               final TableRuleSet ruleset, 
			               final DecisionTableUpdateProvider decisionTableUpdateprovider, 
			               final DecisionTableDataSource decisionTableDataSource) {
		if (dtTable.getFields().length == 0) {
			return;
		}
		final ListGridRecord[]  records = dtTable.getSelectedRecords();
		if (records.length == 0) {
			return;
		}
		CustomSC.confirm (dtMessages.dtRemoveRowdialogtitle(), dtMessages.dtRemoveRowdialogdesc(), new BooleanCallback () {
			public void execute (Boolean value) {
				if (Boolean.TRUE.equals (value)) {
					
					removeHighlights(AbstractDecisionTableEditor.this);
					executeFilter(dtTable, false, false, false);
					
					List<TableRule> tableRules = ruleset.getTableRules();
					for (ListGridRecord record : records) {
						if (record instanceof TableRuleVariableRecord) {
							TableRuleVariableRecord trvRecord = (TableRuleVariableRecord)record;
							TableRule rule = trvRecord.getRule();
							String ruleId = rule.getId();
							
							removeFromCellToDisableMap(rule, AbstractDecisionTableEditor.this, table, null, dtTable == AbstractDecisionTableEditor.this.dtTable, true);
							
							//Remove from NEW Change Set
							if (decisionTableUpdateprovider.getNewRecords().containsKey(ruleId)) {
								decisionTableUpdateprovider.getNewRecords().remove(ruleId);
							}
							//Remove from Modified Change Set
							if (decisionTableUpdateprovider.getModifiedRecords().containsKey(ruleId)) {
								decisionTableUpdateprovider.getModifiedRecords().remove(ruleId);
							}
							//Add to Removed Change Set
							if (!decisionTableUpdateprovider.getDeletedRecords().contains(ruleId)) {
								decisionTableUpdateprovider.getDeletedRecords().add(ruleId);
							}
							tableRules.remove(rule);
						}
					}
					for (ListGridRecord record :records) {
						decisionTableDataSource.removeData(record, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
									boolean isDecisionTable = dtTable == AbstractDecisionTableEditor.this.dtTable;
									disableToolBarForExistingWithRecords(isDecisionTable, 
											isDecisionTable ? AbstractDecisionTableEditor.this.dtTable.getRecords().length 
													: AbstractDecisionTableEditor.this.expTable.getRecords().length);
									initRecordCache(isDecisionTable);
									makeDirty(isDecisionTable);
								}
							}
						});
					}
				}
			}
		});
	}
	
	/**
	 * @param dtTable
	 * @param decisionTableDataSource
	 * @param decisionTableUpdateprovider
	 * @param ruleSet
	 * @param dtlastRecordId
	 * @param dtConditionAreaColumns
	 * @param dtActionAreaColumns
	 */
	private void addRow(final TableDataGrid dtTable, 
			            DecisionTableDataSource decisionTableDataSource, 
			            final DecisionTableUpdateProvider decisionTableUpdateprovider,
			            TableRuleSet ruleSet, 
			            long dtlastRecordId, 
			            Map<Integer, TableColumn> dtConditionAreaColumns, 
			            Map<Integer, TableColumn> dtActionAreaColumns) {
		if (dtTable.getFields().length == 0) {
			return;
		}
		
		removeHighlights(this);
		executeFilter(dtTable, false, false, false);
		
		List<TableRule> tableRules = ruleSet.getTableRules();
		if (tableRules == null) {
			tableRules = new ArrayList<TableRule>();
			ruleSet.setTableRules(tableRules);
		}
		dtlastRecordId = dtlastRecordId + 1;
		
		if (ruleSet == table.getDecisionTable()) {
			setDtlastRecordId(dtlastRecordId);
			table.getDecisionTable().setLastRow(dtlastRecordId);
		} else {
			setExplastRecordId(dtlastRecordId);
			table.getDecisionTable().setLastRow(dtlastRecordId);
		}
		final String newruleId = Long.toString(dtlastRecordId);
		final TableRule newRule = new TableRule(newruleId);
		newRule.setNewRule(true);
		List<TableRuleVariable> conditions = new ArrayList<TableRuleVariable>();
		List<TableRuleVariable> actions = new ArrayList<TableRuleVariable>();
		addNewTableRule(newruleId, newRule, dtConditionAreaColumns, conditions);
		addNewTableRule(newruleId, newRule, dtActionAreaColumns, actions);
		newRule.setConditions(conditions);
		newRule.setActions(actions);
		tableRules.add(newRule);
		
		//Add Decision Table Data Source if not available
		if (decisionTableDataSource == null) {
			decisionTableDataSource =  new DecisionTableDataSource(ruleSet.getColumns(), dtTable.getFields());
			if (ruleSet == table.getDecisionTable()) {
				setDecisionTableDataSource(decisionTableDataSource);
			} else {
				setExceptionTableDataSource(decisionTableDataSource);
			}
		}
		
		//create new List Grid Record and add to Decision Table
		TableRuleVariableRecord record = addNewRecord(newRule, ruleSet);
		decisionTableDataSource.addData(record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					boolean isDecisionTable = dtTable == AbstractDecisionTableEditor.this.dtTable;
					disableToolBarForExistingWithRecords(isDecisionTable, 
							isDecisionTable ? AbstractDecisionTableEditor.this.dtTable.getRecords().length 
									: AbstractDecisionTableEditor.this.expTable.getRecords().length);
					dtTable.scrollToBottom();
					//Add to Change Set
					if (!decisionTableUpdateprovider.getNewRecords().containsKey(newruleId)) {
						decisionTableUpdateprovider.getNewRecords().put(newruleId, newRule);
					}
					addRecordCache(isDecisionTable); 
					makeDirty(isDecisionTable);
				}
			}
		});
	}

	/**
	 * @param dtTable
	 * @param decisionTableDataSource
	 * @param decisionTableUpdateprovider
	 * @param ruleSet
	 * @param dtlastRecordId
	 * @param dtConditionAreaColumns
	 * @param dtActionAreaColumns
	 */
	private void duplicateRow(final TableDataGrid dtTable, 
			          	DecisionTableDataSource decisionTableDataSource, 
			            final DecisionTableUpdateProvider decisionTableUpdateprovider,
			            final TableRuleSet ruleSet, 
			            long dtlastRecordId, 
			            Map<Integer, TableColumn> dtConditionAreaColumns, 
			            Map<Integer, TableColumn> dtActionAreaColumns) {
		if (dtTable.getFields().length == 0) {
			return;
		}
		final ListGridRecord[]  records = dtTable.getSelectedRecords();
		if (records.length == 0) {
			return;
		}
		
		removeHighlights(this);
		executeFilter(dtTable, false, false, false);

		List<TableRule> tableRules = ruleSet.getTableRules();
		for (ListGridRecord record : records) {
			if (record instanceof TableRuleVariableRecord) {
				TableRuleVariableRecord trvRecord = (TableRuleVariableRecord)record;
				TableRule rule = trvRecord.getRule();
				dtlastRecordId = dtlastRecordId + 1;
				final String newruleId = Long.toString(dtlastRecordId);
				final TableRule duplicateRule = rule.copy(newruleId);
				duplicateRule.setNewRule(true);
				List<MetaData> rlmdlist = rule.getMetaData();
				duplicateRule.setMetaData(rlmdlist);
				tableRules.add(duplicateRule);
				
				//create new List Grid Record and add to Decision Table
				TableRuleVariableRecord duplicatedRecord = addNewRecord(duplicateRule, ruleSet);
				decisionTableDataSource.addData(duplicatedRecord, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							dtTable.scrollToBottom();
							//Add to Change Set
							if (!decisionTableUpdateprovider.getNewRecords().containsKey(newruleId)) {
								decisionTableUpdateprovider.getNewRecords().put(newruleId, duplicateRule);
							}
							addRecordCache(ruleSet == table.getDecisionTable());
							makeDirty(ruleSet == table.getDecisionTable());
						}
					}
				});
			}
		}
		
		if (ruleSet == table.getDecisionTable()) {
			setDtlastRecordId(dtlastRecordId);
		} else {
			setExplastRecordId(dtlastRecordId);
		}		
	}
	
	public void validate() {
	}
	
	/**
	 * @param event
	 */
	@Override
	public void onEditorEnter(EditorEnterEvent event) {
		onEditorEnter(event, event.getSource() == dtTable);
	}
	
	/**
	 * @param event
	 * @param isDecisionTable
	 */
	private void onEditorEnter(EditorEnterEvent event, boolean isDecisionTable) {
		removeHighlights(this);
		editcolId = isDecisionTable ? dtTable.getEditCol() : expTable.getEditCol();
		if (editcolId == -1 || editcolId == 0) {
			return;
		}
		ListGridRecord record = (isDecisionTable ? dtTable.getSelectedRecord() : expTable.getSelectedRecord());
		if (record != null) {
			TableRule rule = (TableRule)record.getAttributeAsObject("rule");
			String colName = isDecisionTable ? dtTable.getField(editcolId).getName() : expTable.getField(editcolId).getName();
			String colId = getColumn(isDecisionTable ?  table.getDecisionTable().getColumns() 
					: table.getExceptionTable().getColumns(), colName).getId();
			TableRuleVariable ruleVar = getTableRuleVariable(rule, colId);
			if (ruleVar != null) {
				propertyTab.refresh(this, table, null, ruleVar, false, false, true, isDecisionTable);
			}
		}
	}
	
	@Override
	public void onEditComplete(EditCompleteEvent event) {
		int colNum =  event.getColNum();
		if (colNum <= 0) {
			return;
		}
		Map map = event.getNewValues();
		String colName = (event.getSource() == dtTable) ? dtTable.getField(colNum).getName() : expTable.getField(colNum).getName();
		String newExpr = "";
		if (map.get(colName) != null) {
			newExpr = map.get(colName).toString();
		}
		onEditComplete(newExpr, event.getOldRecord(), event.getSource() == dtTable, colNum);
	}
	
	/**
	 * Updates the new value into the ListGridRecord with a proper type. This enables proper numeric or otherwise sorting on the UI.
	 * @param newExpr
	 * @param record
	 * @param isDecisionTable
	 */
	private void onEditComplete(String newExpr, Record record, boolean isDecisionTable, int colNum) {
		if (newExpr == null) {
			return;
		}
		TableRule tableRule = (TableRule)record.getAttributeAsObject("rule");
		String colName = isDecisionTable ? dtTable.getField(colNum).getName() : expTable.getField(colNum).getName();
		TableColumn column = getColumn(isDecisionTable ? table.getDecisionTable().getColumns() : table.getExceptionTable().getColumns() , colName); 
		String colId = column.getId();
		
		TableRuleVariable ruleVar = getTableRuleVariable(tableRule, colId);
		if (ruleVar == null) {
			ruleVar = new TableRuleVariable(tableRule.getId() + "_" + colId, colId, newExpr, true, "", tableRule);
			if (isDecisionTable ? dtConditionAreaColumns.containsKey(Integer.parseInt(colId))
					: expConditionAreaColumns.containsKey(Integer.parseInt(colId))) {
				tableRule.getConditions().add(ruleVar);
			} else if (isDecisionTable ?  dtActionAreaColumns.containsKey(Integer.parseInt(colId)) : expActionAreaColumns.containsKey(Integer.parseInt(colId))){
				tableRule.getActions().add(ruleVar);
			}
		} else {
			String oldExpr = ruleVar.getExpression();
			if (oldExpr.intern() == newExpr.intern()) {
				return;
			}
			if (column.isSubstitution()) {
				newExpr = DecisionTableUtils.getFormattedString(column, newExpr);
			}
			ruleVar.setExpression(newExpr);
		}
		if (isDecisionTable) {
			if (!decisionTableUpdateprovider.getModifiedRecords().containsKey(tableRule.getId())) {
				Set<String> modifiedCols = new HashSet<String>(); 
				modifiedCols.add(colName);
				decisionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
				decisionTableUpdateprovider.getModifiedRecords().put(tableRule.getId(), tableRule);
			} else {
				Set<String> modifiedCols  = decisionTableUpdateprovider.getModifiedRecordColumns().get(tableRule.getId());
				modifiedCols.add(colName);
				decisionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
			}
		} else {
			if (!exceptionTableUpdateprovider.getModifiedRecords().containsKey(tableRule.getId())) {
				Set<String> modifiedCols = new HashSet<String>(); 
				modifiedCols.add(colName);
				exceptionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
				exceptionTableUpdateprovider.getModifiedRecords().put(tableRule.getId(), tableRule);
			} else {
				Set<String> modifiedCols  = exceptionTableUpdateprovider.getModifiedRecordColumns().get(tableRule.getId());
				modifiedCols.add(colName);
				exceptionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
			}
		}
		
		PROPERTY_TYPES type = PROPERTY_TYPES.get(Integer.parseInt(column.getPropertyType()));
		//Update the new value with a proper type in ListGridRecord to enable proper sorting etc.
		record.setAttribute(colName, DecisionTableUtils.getValueByType(type, newExpr));
		
		makeDirty(isDecisionTable);
	}
	
	@Override
	public void onEditorExit(EditorExitEvent event) {
		int colNum =  event.getColNum();
		if (colNum <= 0 || event.getNewValue() == null) {
			return;
		}
		onEditComplete(event.getNewValue().toString(), event.getRecord(), event.getSource() == dtTable, colNum);
	}
	
	@Override
	public void onRecordDrop(RecordDropEvent event) {
		event.cancel();
		final boolean isDecisionTable = event.getSource() == dtTable;

		String dragSourceParentURI = ArgumentDataTransfer.getInstance().getURI();

		final String argPath = ArgumentDataTransfer.getInstance().getArgumentPath();

		String targetURI = getProjectName() +  getTable().getFolder() + getTable().getName();
		if (!targetURI.endsWith(DOT + RULE_FN_IMPL_EXTENSION)) {
			targetURI = getProjectName() +  getTable().getFolder() + getTable().getName() + DOT + RULE_FN_IMPL_EXTENSION;
		}

		if (!dragSourceParentURI.equals(targetURI)) {
			CustomSC.warn("Invalid Argument");
			event.cancel();
			return;
		}

		final ArgumentResource argResource = ArgumentDataTransfer.getInstance().getArgumentResource();
		if (argResource == null) {
			event.cancel();
			return;
		}
		
		final ListGridRecord listGridRecord = event.getTargetRecord();
		if (null == listGridRecord) {
			ArgumentDataTransfer.getInstance().clear();
			final SelectColumnTypeDialog selectColumnTypeDialog = new SelectColumnTypeDialog(
					dtMessages.dtcreatecolumn(), new CustomBooleanCallBack() {
						public void execute(Boolean value) {
							if (Boolean.TRUE.equals(value)) {
								if (isCondition()) {
									dropAndCreateConditionColumn(argResource, argPath, isDecisionTable);
								} else {
									if (argResource.isPrimitive()) {
										ErrorMessageDialog.showError(dtMessages.dtcreatecolumnerror(argResource.getName()));
										return;
									}
									dropAndCreateActionColumn(argResource, argPath, isDecisionTable);
								}
							}
						}
					});
			selectColumnTypeDialog.draw();
		} else {
			dropArgument(isDecisionTable, argPath, argResource);
		}
	}

	/**
	 * @param argResource
	 * @param parentargres
	 */
	private void getParents(ArgumentResource argResource, List<ArgumentResource> parentargres) {
		if (argResource.getParent() != null) {
			parentargres.add(argResource.getParent());
			getParents(argResource.getParent(), parentargres);
		} 
	}

	/**
	 * @param argResource
	 * @param isDecisionTable
	 */
	private void dropAndCreateConditionColumn (ArgumentResource argResource, String argumentPath, boolean isDecisionTable) {
		String newArgName = argResource.isArray() ? argResource.getName() +"[]" : argResource.getName();
		String alias = "";
		String newColumnName = newArgName;
		if (!argResource.isPrimitive()) {
			StringBuffer buffer = new StringBuffer();
			List<ArgumentResource> list = new ArrayList<ArgumentResource>();
			getParents(argResource, list);
			for (int i = list.size() -1 ; i >= 0 ; i--) {
				buffer.append(DOT);
				ArgumentResource argRes = list.get(i);
				String argName = argRes.getAlias() != null && !argRes.getAlias().isEmpty() ?  argRes.getAlias() : argRes.getName();
				if (list.get(i).isArray()) {
					argName = argName + "[]";
				}
				//buffer.append(i == list.size() -1 ? argName.toLowerCase() : argName);
				buffer.append(argName);
			}
			alias  = buffer.toString().substring(1);
			String actAlias = argResource.getParent().getAlias(); 
			if (actAlias != null && !actAlias.isEmpty()) {
				boolean aliasArray = false;
				String a = alias;
				String l = null;
				if (alias.indexOf(DOT) != -1) {
					a = alias.substring(0, alias.indexOf(DOT));
					l = alias.substring(alias.indexOf(DOT) + 1);
				} 
				if (a.contains("[]")) {
					aliasArray = true;
				}
				alias = actAlias;
				if (aliasArray) {
					alias = alias + "[]";
				}
				if (l != null) {
					alias = alias + DOT + l;
				}
			}
			newColumnName = alias + DOT + newArgName;
			newColumnName = adjustColumnName(newColumnName, false);
		} 
		
		//check for duplicate Condition
		if (isDuplicateColumn(isDecisionTable ? table.getDecisionTable().getColumns() : 
			table.getExceptionTable().getColumns(), newColumnName, true)) {
			newColumnName = alias + DOT + argResource.getName();
			CustomSC.say(dtMessages.dtduplicatecolumn(newColumnName, ColumnType.CONDITION.getName()));
			return;
		}
		if (isDuplicateColumn(isDecisionTable ? table.getDecisionTable().getColumns() : 
			table.getExceptionTable().getColumns(), newColumnName, false)) {
			newColumnName = alias + DOT + argResource.getName();
			CustomSC.say(dtMessages.dtduplicatecolumn(newColumnName, ColumnType.ACTION.getName()));
			return;
		}
		addConditionColumn(newColumnName, argResource, false, isDecisionTable);
	}
	
	/**
	 * @param argResource
	 * @param isDecisionTable
	 */
	private void dropAndCreateActionColumn(ArgumentResource argResource,  String argumentPath, boolean isDecisionTable) {
		int condSize = isDecisionTable ? dtConditionAreaFieldMap.size() : expConditionAreaFieldMap.size();
		if (condSize == 0) {
			CustomSC.say(dtMessages.dt_atleastOne_condition_message());
			return;
		}
		StringBuffer buffer = new StringBuffer();
		List<ArgumentResource> list = new ArrayList<ArgumentResource>();
		getParents(argResource, list);
		for (int i = list.size() -1 ; i >= 0 ; i--) {
			buffer.append(DOT);
			ArgumentResource argRes = list.get(i);
			String argName = argRes.getAlias() != null && !argRes.getAlias().isEmpty() ?  argRes.getAlias() : argRes.getName();
			if (list.get(i).isArray()) {
				argName = argName + "[]";
			}
			//buffer.append(i == list.size() -1 ? argName.toLowerCase() : argName);
			buffer.append(argName);
		}
		String alias  = buffer.toString().substring(1);
		String actAlias = argResource.getParent().getAlias(); 
		if (actAlias != null && !actAlias.isEmpty()) {
			boolean aliasArray = false;
			String a = alias;
			String l = null;
			if (alias.indexOf(DOT) != -1) {
				a = alias.substring(0, alias.indexOf(DOT));
				l = alias.substring(alias.indexOf(DOT) + 1);
			} 
			if (a.contains("[]")) {
				aliasArray = true;
			}
			alias = actAlias;
			if (aliasArray) {
				alias = alias + "[]";
			}
			if (l != null) {
				alias = alias + DOT + l;
			}
		}
		String newArgName = argResource.isArray() ? argResource.getName() +"[]" : argResource.getName();
		String newColumnName = alias + DOT + newArgName;
		
		newColumnName = adjustColumnName(newColumnName, false);
		newColumnName = adjustActionColumnName(newColumnName);
		
		//Check for duplicate Action column
		if (isDuplicateColumn(isDecisionTable ? table.getDecisionTable().getColumns() : 
			table.getExceptionTable().getColumns(), newColumnName, false)) {
			newColumnName = alias + DOT + argResource.getName();
			CustomSC.say(dtMessages.dtduplicatecolumn(newColumnName, ColumnType.ACTION.getName()));
			return;
		}
		if (isDuplicateColumn(isDecisionTable ? table.getDecisionTable().getColumns() : 
			table.getExceptionTable().getColumns(), newColumnName, true)) {
			newColumnName = alias + DOT + argResource.getName();
			CustomSC.say(dtMessages.dtduplicatecolumn(newColumnName, ColumnType.CONDITION.getName()));
			return;
		}
		
		addActionColumn(newColumnName, argResource, false, isDecisionTable);
	}

	/**
	 * @param newColumnName
	 * @param argResource
	 * @param custom
	 * @param isDecisionTable
	 */
	private void addConditionColumn(String newColumnName, 
			                        ArgumentResource argResource, 
			                        boolean custom, 
			                        boolean isDecisionTable) {
		removeHighlights(this);
		executeFilter(isDecisionTable ? dtTable : expTable, false, false, false);
		int lastindex = getNewColumnId(isDecisionTable);
		TableColumn newConditionColumn = null;
		if (custom) {
			newColumnName = newColumnName + lastindex;
			newConditionColumn = 
					new TableColumn(isDecisionTable ? table.getDecisionTable() : table.getExceptionTable(), Integer.toString(lastindex), newColumnName, 
							null, Integer.toString(PROPERTY_TYPES.STRING.getValue()),  ColumnType.CUSTOM_CONDITION.getName(), null, false, false, false, "");
		} else {
			String propertyPath = DisplayUtils.parentDisplayModelExists(argResource) ? argResource.getParent().getPath() + argResource.getParent().getName() + "/" + argResource.getName() 
					: argResource.getOwnerPath() + "/" + argResource.getName();
			newConditionColumn = 
					new TableColumn(isDecisionTable ? table.getDecisionTable() : table.getExceptionTable(), Integer.toString(lastindex), newColumnName, 
							propertyPath, Integer.toString(PROPERTY_TYPES.get(argResource.getType().toUpperCase()).getValue()), 
							ColumnType.CONDITION.getName(), null, argResource.hasAssociatedDomain(), false, argResource.isArray(), "");
		}
		if (isDecisionTable) {
			table.getDecisionTable().addColumn(newConditionColumn);
			decisionTableUpdateprovider.getNewColumns().add(newConditionColumn);
			loadDecisionTable(false, false, true, newConditionColumn.getId(),  newConditionColumn.getName());
		} else {
			table.getExceptionTable().addColumn(newConditionColumn);
			exceptionTableUpdateprovider.getNewColumns().add(newConditionColumn);
			loadExceptionTable(false, true, newConditionColumn.getId(),  newConditionColumn.getName());
		}
		makeDirty(isDecisionTable);
	}
	
	/**
	 * @param newColumnName
	 * @param argResource
	 * @param custom
	 * @param isDecisionTable
	 */
	private void addActionColumn(String newColumnName, ArgumentResource argResource, boolean custom , boolean isDecisionTable) {
		int condSize = isDecisionTable ? dtConditionAreaFieldMap.size() : expConditionAreaFieldMap.size();
		if (condSize == 0) {
			CustomSC.say(dtMessages.dt_atleastOne_condition_message());
			return;
		}
		if (isDecisionTable ? dtTable.getFields().length == 0 : expTable.getFields().length == 0) {
			CustomSC.say(dtMessages.dt_atleastOne_condition_message());
			return;
		}
		removeHighlights(this);
		executeFilter(isDecisionTable ? dtTable : expTable, false, false, false);
		int lastindex = getNewColumnId(isDecisionTable);
		TableColumn newActionColumn = null;
		if (custom) {
			newColumnName = newColumnName + lastindex;
			newActionColumn = 
					new TableColumn(isDecisionTable ? table.getDecisionTable() : table.getExceptionTable(), Integer.toString(lastindex), 
							newColumnName, null, Integer.toString(PROPERTY_TYPES.STRING.getValue()), ColumnType.CUSTOM_ACTION.getName(), null, false, false, false, "");
		} else {
			String propertyPath = DisplayUtils.parentDisplayModelExists(argResource) ? argResource.getParent().getPath() + argResource.getParent().getName() + "/" + argResource.getName() 
					: argResource.getOwnerPath() + "/" + argResource.getName();
			newActionColumn = 
					new TableColumn(isDecisionTable ? table.getDecisionTable() : table.getExceptionTable(), Integer.toString(lastindex), newColumnName, 
							propertyPath, Integer.toString(PROPERTY_TYPES.get(argResource.getType().toUpperCase()).getValue()), 
							ColumnType.ACTION.getName(), null, argResource.hasAssociatedDomain(), false, argResource.isArray(), "");
		}
		
		if (isDecisionTable) {
			table.getDecisionTable().addColumn(newActionColumn);
			decisionTableUpdateprovider.getNewColumns().add(newActionColumn);
			loadDecisionTable(false, false, true, newActionColumn.getId(),  newActionColumn.getName());
		} else {
			table.getExceptionTable().addColumn(newActionColumn);
			exceptionTableUpdateprovider.getNewColumns().add(newActionColumn);
			loadExceptionTable(false, true, newActionColumn.getId(),  newActionColumn.getName());
		}
		makeDirty(isDecisionTable);
	}
	
	/**
	 * @param decisionTableUpdateprovider
	 * @param colId
	 * @param colName
	 * @param ruleSet
	 * @param dtlastRecordId
	 * @param dtConditionAreaColumns
	 * @param dtActionAreaColumns
	 */
	protected void updateTableRuleVariables(DecisionTableUpdateProvider decisionTableUpdateprovider, 
										    String colId, 
										    String colName,
								            TableRuleSet ruleSet, 
								            long dtlastRecordId, 
								            Map<Integer, TableColumn> dtConditionAreaColumns, 
								            Map<Integer, TableColumn> dtActionAreaColumns) {
		
		List<TableRule> tableRules = ruleSet.getTableRules();
		if (tableRules == null) {
			tableRules = new ArrayList<TableRule>();
			ruleSet.setTableRules(tableRules);
		}
		if (tableRules.size() == 0 ) {
			dtlastRecordId = dtlastRecordId + 1;
			if (ruleSet == table.getDecisionTable()) {
				setDtlastRecordId(dtlastRecordId);
			} else {
				setExplastRecordId(dtlastRecordId);
			}
			ruleSet.setLastRow(dtlastRecordId);
			String newruleId = Long.toString(dtlastRecordId);
			TableRule newRule = new TableRule(newruleId);
			List<TableRuleVariable> conditions = new ArrayList<TableRuleVariable>();
			List<TableRuleVariable> actions = new ArrayList<TableRuleVariable>();
			addNewTableRule(newruleId, newRule, dtConditionAreaColumns, conditions);
			addNewTableRule(newruleId, newRule, dtActionAreaColumns, actions);
			newRule.setConditions(conditions);
			newRule.setActions(actions);
			tableRules.add(newRule);
			//Add to Change Set
			if (!decisionTableUpdateprovider.getNewRecords().containsKey(newruleId)) {
				decisionTableUpdateprovider.getNewRecords().put(newruleId, newRule);
			}
		} else {
			
			for (TableRule tableRule: tableRules) {
				String tableRulevarId = tableRule.getId() + "_" + colId;
				TableRuleVariable ruleVar = new TableRuleVariable(tableRulevarId, colId, "", true, "", tableRule);
				if (dtConditionAreaColumns.containsKey(Integer.parseInt(colId))) {
					tableRule.getConditions().add(ruleVar);
				} else if (dtActionAreaColumns.containsKey(Integer.parseInt(colId))){
					tableRule.getActions().add(ruleVar);
				}
				
				if (!decisionTableUpdateprovider.getModifiedRecords().containsKey(tableRule.getId())) {
					Set<String> modifiedCols = new HashSet<String>(); 
					modifiedCols.add(colName);
					decisionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
					decisionTableUpdateprovider.getModifiedRecords().put(tableRule.getId(), tableRule);
				} else {
					Set<String> modifiedCols  = decisionTableUpdateprovider.getModifiedRecordColumns().get(tableRule.getId());
					modifiedCols.add(colName);
					decisionTableUpdateprovider.getModifiedRecordColumns().put(tableRule.getId(), modifiedCols);
				}
			}
		}
	}
	
	@Override
	public void gotoMarker(ProblemMarker marker) {
		DecisionTableUtils.removeProblemHighlights(this);
		
		if (marker instanceof DecisionTableProblemMarker) {
			DecisionTableProblemMarker dtProblemMarker = (DecisionTableProblemMarker) marker; 
			
			int gotoPage = -1;
			try {
				gotoPage = Integer.parseInt(dtProblemMarker.getPageNum());
			} catch (NumberFormatException nfe) {} //do nothing	

			if (gotoPage > 0) {
				if (currentPage != gotoPage) {
					currentPage = gotoPage;
					loadEditorPageData(this.selectedRecord, ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
					setOnOpenMarker(marker);
				} else {
					processProblemRecord(dtProblemMarker);
					if (fCellsToHighlightMap.size() > 0) {
						refreshCells();
					}
					DecisionTableUtils.highlightProblemRecord(this, dtProblemMarker);
				}	
			}

		}		
	}

	private void processProblemRecord(DecisionTableProblemMarker dtProblemMarker) {
		String[] ruleIdAndColId = dtProblemMarker.getRuleAndColumnId();
		if (ruleIdAndColId != null && ruleIdAndColId.length > 0) {
			String ruleId = ruleIdAndColId[0];
			if (ruleId != null) {
				int rowNumber = -1;
				try {
					rowNumber = Integer.parseInt(this.getRecordNumber(ruleId, true));
				} catch (NumberFormatException nfe) { } //do nothing
				
				dtProblemMarker.setRowNum(rowNumber);
				
				if (ruleIdAndColId.length == 2) {
					String col = ruleIdAndColId[1];
					List<Integer> colList = new ArrayList<Integer>();
					colList.add(Integer.parseInt(col));
					fCellsToHighlightMap.put(rowNumber, colList);
					showCellHighlight = true;
				}
			}			
		}	
	}
	
	private String getActualArgumentAlias(Table table, String argPath) {
		for (ArgumentData data : table.getArguments()) {
			if (data.getPropertyPath().endsWith(argPath)) {
				return data.getAlias();
			}
		}
		return null;
	}

	@Override
	public void makeReadOnly() {
		dtAddbutton.setDisabled(true);
		dtRemovebutton.setDisabled(true);
		dtDupeRowbutton.setDisabled(true);
		expAddRowbutton.setDisabled(true);
		expRemoveRowbutton.setDisabled(true);
		expDupeRowbutton.setDisabled(true);
		
		WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
		WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);

		showTableAnalyzerViewButton.setDisabled(true);
	}
	
	/**
	 * 
	 */	
	@Override
	public void onCellContextClick(CellContextClickEvent event) {

		if (this.isMerge()) {
			AbstractEditorMergeHandler mergeHandler = new DecisionTableMergeHandler(); 
			mergeHandler.setMergeMenu(event);
		} else {
			ListGridRecord record = event.getRecord();
			int colNum = event.getColNum();
			contextMenu = new Menu();
			contextMenu.setShowShadow(true);
			contextMenu.setShadowDepth(10);
			MenuItem menuItem = null;
			boolean isEnabled = false;
			TableRule rule = (TableRule)record.getAttributeAsObject("rule");	
			
			boolean isDecisionTable = (event.getSource() == dtTable);
			String colId = isDecisionTable ? getDecisionTableColumnId(colNum) : getExceptionTableColumnId(colNum);
	
			// If ID get the TableRule enable value else TableRuleVariable
			if("ID".equals(dtTable.getField(colNum).getTitle())) {
				if(rule.isEnabled() ){
					isEnabled = true;
				} else{
					isEnabled = false;
				}
			} else{		
				TableRuleVariable ruleVar = getTableRuleVariable(rule, colId);
				if(ruleVar == null){
					return;
				}
				if(ruleVar.isEnabled()){
					isEnabled = true;
				} else{
					isEnabled = false;
				}			
			}
			
			//If enabled give option to disable & vice versa
			if (isEnabled) {
				menuItem = new MenuItem("Disable");
				menuItem.addClickHandler(new EnableDisableHandler());
				menuItem.setAttribute("colNum",colNum);
				menuItem.setAttribute("rowNum",event.getRowNum());
			} else {
				menuItem = new MenuItem("Enable");
				menuItem.addClickHandler(new EnableDisableHandler());
				menuItem.setAttribute("colNum",colNum);
				menuItem.setAttribute("rowNum",event.getRowNum());
			}
			this.contextMenu.addItem(menuItem);

			if (event.getSource() == dtTable) {
				dtTable.setContextMenu(contextMenu);
			} else {
				expTable.setContextMenu(contextMenu);
			}
		}				
	}
	
	/**
	 * 
	 * EnableDisableHandler
	 *
	 */
	class EnableDisableHandler implements ClickHandler {
		@Override
		public void onClick(MenuItemClickEvent event) {
			boolean isEnabled = false;
			boolean isDecisionTable = false;
			ListGridRecord record = null;
			
			if("Enable".equals(((MenuItem)event.getSource()).getTitle())){
				isEnabled = true;
			}
						
			if (event.getTarget() == dtTable) {
				if (!isReadOnly()) {
					record = dtTable.getSelectedRecord();
				}
				isDecisionTable = true;
			} else if (event.getTarget() == expTable) {
				if (!isReadOnly()) {
					record = expTable.getSelectedRecord();
				}
			}

			int colNum = ((MenuItem) event.getSource()).getAttributeAsInt("colNum");
			int rowNum = ((MenuItem) event.getSource()).getAttributeAsInt("rowNum");
			String colName = isDecisionTable ? dtTable.getField(colNum).getTitle() : expTable.getField(colNum).getTitle();
			String colId = isDecisionTable ? getDecisionTableColumnId(colNum) : getExceptionTableColumnId(colNum);			
			TableRule rule = (TableRule) record.getAttributeAsObject("rule");

			if ("ID".equals(colName)) {
				rule.setEnabled(isEnabled, true);
				List<TableRuleVariable> conds = rule.getConditions();
				if (conds != null) {
					for (int i = 0; i < conds.size(); i++) {
						cacheCellEnables(conds.get(i), table, AbstractDecisionTableEditor.this, isDecisionTable);
					}
				}	
				List<TableRuleVariable> acts = rule.getActions();
				if (acts != null) {
					for (int i = 0; i < acts.size(); i++) {
						cacheCellEnables(acts.get(i), table, AbstractDecisionTableEditor.this, isDecisionTable);
					}
				}				
				dtTable.refreshRow(rowNum);
			} else {
				TableRuleVariable ruleVar = getTableRuleVariable(rule, colId);
				ruleVar.setEnabled(isEnabled);
				cacheCellEnables(ruleVar, table, AbstractDecisionTableEditor.this, isDecisionTable);
				dtTable.refreshCellStyle(rowNum, colNum);
			}
			makeDirty(isDecisionTable);
		}
	}

	class DecisionTableMergeHandler extends AbstractEditorMergeHandler {
		
		private final String ADD_ROW = MergeType.ADD.getLiteral() + " row";
		private final String ADD_COLUMN = MergeType.ADD.getLiteral() + " column";
		private final String REMOVE_ROW = MergeType.REMOVE.getLiteral() + " row";
		private final String REMOVE_COLUMN = MergeType.REMOVE.getLiteral() + " column";
		
		@SuppressWarnings("rawtypes")
		@Override
		public void setMergeMenu(BrowserEvent bEvent) {
			CellContextClickEvent event = (CellContextClickEvent)bEvent;
			TableRuleVariableRecord record = (TableRuleVariableRecord)event.getRecord();
			TableRule rule = record.getRule();
			int colNum = event.getColNum();
			int rowNum = event.getRowNum();
			boolean isDecisionTable = (event.getSource() == dtTable);
			String colId = isDecisionTable ? getDecisionTableColumnId(colNum) : getExceptionTableColumnId(colNum);
			DTDiffType diffType = (isDecisionTable ? DTDiffType.DECISION_TABLE : DTDiffType.EXCEPTION_TABLE);
			if (modifications!= null && !modifications.isEmpty()) {
				contextMenu = new Menu();
				contextMenu.setShowShadow(true);
				contextMenu.setShadowDepth(10);

				boolean hasMenu = false;
				ModificationEntry modificationEntry = null;
				TableRuleVariable ruleVar = getTableRuleVariable(rule, colId);
				String ruleVarId = null;
				if (ruleVar != null) {
					ruleVarId = ruleVar.getId();
				} else {
					ruleVarId = rule.getId() + "_" + colId;
				}			
				//Cell modifications
				modificationEntry = modifications.get(diffType.getKeyPrefix() + "ruleCell" + ruleVarId);
				if (modificationEntry != null && !modificationEntry.isApplied()) {
					MenuItem menuItem = null, menuItem2 = null;
					if(ModificationType.MODIFIED.equals(modificationEntry.getModificationType())) {							
						menuItem = new MenuItem(MergeType.USE_LOCAL.getLiteral());
						menuItem.setAttribute("rowNum", rowNum);
						menuItem.setAttribute("colNum", colNum);
						menuItem.setAttribute("colId", colId);
						menuItem.setAttribute("rule", rule);
						menuItem.setAttribute(MOD_ENTRY_ATTR, modificationEntry);
						menuItem.addClickHandler(this);
						AbstractDecisionTableEditor.this.contextMenu.addItem(menuItem);
						hasMenu = true;
						if (modificationEntry instanceof MergedDiffModificationEntry) {
							menuItem2 = new MenuItem(MergeType.USE_BASE.getLiteral());
							menuItem2.setAttribute("rowNum", rowNum);
							menuItem2.setAttribute("colNum", colNum);
							menuItem2.setAttribute("colId", colId);
							menuItem2.setAttribute("rule", rule);
							menuItem2.setAttribute(MOD_ENTRY_ATTR, modificationEntry);
							menuItem2.addClickHandler(this);
							AbstractDecisionTableEditor.this.contextMenu.addItem(menuItem2);
						}
					}		
				}
				//Rule modifications
				modificationEntry = modifications.get(diffType.getKeyPrefix() + "tableRule" + rule.getId());
				if (modificationEntry != null && !modificationEntry.isApplied()) {
					MenuItem menuItem = null;
					switch(modificationEntry.getModificationType()) {
			        	case ADDED: 
			        	case DELETED:
			        		menuItem = new MenuItem(REMOVE_ROW);
			        		menuItem.addClickHandler(this);
			        		menuItem.setAttribute("rowNum", rowNum);
			        		menuItem.setAttribute("colNum", colNum);
							menuItem.setAttribute(MOD_ENTRY_ATTR, modificationEntry);							
							AbstractDecisionTableEditor.this.contextMenu.addItem(menuItem);
							hasMenu = true;
			        		break;
					}		
				}
				//Column modifications
				modificationEntry = modifications.get(diffType.getKeyPrefix() + "column" + colId);
				if (modificationEntry != null && !modificationEntry.isApplied()) {
					MenuItem menuItem = null;
					switch(modificationEntry.getModificationType()) {
			        	case ADDED: 
			        	case DELETED:
			        		menuItem = new MenuItem(REMOVE_COLUMN);
			        		menuItem.addClickHandler(this);
			        		menuItem.setAttribute("rowNum", rowNum);
			        		menuItem.setAttribute("colNum", colNum);
			        		menuItem.setAttribute(MOD_ENTRY_ATTR, modificationEntry);							
							AbstractDecisionTableEditor.this.contextMenu.addItem(menuItem);
							hasMenu = true;
			        		break;
					}		
				}
				if (hasMenu) {
					if (event.getSource() == dtTable) {
						dtTable.setContextMenu(contextMenu);
					} else {
						expTable.setContextMenu(contextMenu);
					}
				} else {
					if (event.getSource() == dtTable) {
						dtTable.setContextMenu(null);
					} else {
						expTable.setContextMenu(null);
					}					
				}
			}			
		}

		@Override
		public void useLocal(MenuItemClickEvent event) {
			ModificationEntry modificationEntry = (ModificationEntry)((MenuItem) event.getSource()).getAttributeAsObject(MOD_ENTRY_ATTR);
			String localValue = modificationEntry.getPreviousValue();
			if (modificationEntry instanceof MergedDiffModificationEntry) {
				if(((MergedDiffModificationEntry) modificationEntry).getLocalVersion() != null){
					localValue = ((MergedDiffModificationEntry) modificationEntry).getLocalVersion();
				} else{
					localValue = ((MergedDiffModificationEntry) modificationEntry).getBaseVersion();
				}
				
			}
			useValue(event, localValue);
		}

		@Override
		public void useBase(MenuItemClickEvent event) {
			ModificationEntry modificationEntry = (ModificationEntry)((MenuItem) event.getSource()).getAttributeAsObject(MOD_ENTRY_ATTR);
			if (modificationEntry instanceof MergedDiffModificationEntry) {
				String baseValue = ((MergedDiffModificationEntry) modificationEntry).getBaseVersion();
				useValue(event, baseValue);
			}
		}

		private void useValue(MenuItemClickEvent event, String value) {
			ModificationEntry modificationEntry = (ModificationEntry)((MenuItem) event.getSource()).getAttributeAsObject(MOD_ENTRY_ATTR);
			int colNum = ((MenuItem) event.getSource()).getAttributeAsInt("colNum");
			int rowNum = ((MenuItem) event.getSource()).getAttributeAsInt("rowNum");
			String colId = ((MenuItem) event.getSource()).getAttribute("colId");
			TableRule tableRule = (TableRule) ((MenuItem) event.getSource()).getAttributeAsObject("rule");
			TableRuleVariableRecord record = null;
			boolean isDecisionTable = false;
			if (event.getTarget() == dtTable) {
				String colName = dtTable.getField(colNum).getName();
				record = (TableRuleVariableRecord) dtTable.getSelectedRecord();
				record.setAttribute(colName, value);
				dtTable.updateData(record);

				TableRuleVariable ruleVar = getTableRuleVariable(tableRule, colId);
				if (ruleVar == null) {
					ruleVar = new TableRuleVariable(tableRule.getId() + "_" + colId, colId, value, true, "", tableRule);
					if (dtConditionAreaColumns.containsKey(Integer.parseInt(colId))) {
						tableRule.getConditions().add(ruleVar);
					} else if (dtActionAreaColumns.containsKey(Integer.parseInt(colId))){
						tableRule.getActions().add(ruleVar);
					}
				} else {
					String oldExpr = ruleVar.getExpression();
					if (oldExpr.intern() != value.intern()) {
						ruleVar.setExpression(value);
					}
				}

				isDecisionTable = true;
			} else if (event.getTarget() == expTable) {
				String colName = expTable.getField(colNum).getTitle();
				record = (TableRuleVariableRecord) expTable.getSelectedRecord();
				record.setAttribute(colName, value);
				expTable.updateData(record);

				TableRuleVariable ruleVar = getTableRuleVariable(tableRule, colId);
				if (ruleVar == null) {
					ruleVar = new TableRuleVariable(tableRule.getId() + "_" + colId, colId, value, true, "", tableRule);
					if (expConditionAreaColumns.containsKey(Integer.parseInt(colId))) {
						tableRule.getConditions().add(ruleVar);
					} else if (expActionAreaColumns.containsKey(Integer.parseInt(colId))){
						tableRule.getActions().add(ruleVar);
					}
				} else {
					String oldExpr = ruleVar.getExpression();
					if (oldExpr.intern() != value.intern()) {
						ruleVar.setExpression(value);
					}
				}			
			}
			AbstractDecisionTableEditor.this.makeDirty(isDecisionTable);
		}
		
		@Override
		public void add(MenuItemClickEvent event) {
			boolean isDecisionTable = false;
			if(ADD_ROW.equals(((MenuItem)event.getSource()).getTitle())) {
				if (event.getTarget() == dtTable) {
					isDecisionTable = true;
				} else if (event.getTarget() == expTable) {
				
				}				
			} else if(ADD_COLUMN.equals(((MenuItem)event.getSource()).getTitle())) {
				if (event.getTarget() == dtTable) {
					isDecisionTable = true;
				} else if (event.getTarget() == expTable) {

				}				
			}
			AbstractDecisionTableEditor.this.makeDirty(isDecisionTable);
		}

		@Override
		public void remove(MenuItemClickEvent event) {
			int colNum = ((MenuItem) event.getSource()).getAttributeAsInt("colNum");
			if(REMOVE_ROW.equals(((MenuItem)event.getSource()).getTitle())) {
				if (event.getTarget() == dtTable) {
					TableRuleSet dtRuleSet = AbstractDecisionTableEditor.this.getTable().getDecisionTable();
        			removeRow(dtTable, dtRuleSet, decisionTableUpdateprovider, decisionTableDataSource);
        			//Change/Remove style
				} else if (event.getTarget() == expTable) {
					TableRuleSet etRuleSet = AbstractDecisionTableEditor.this.getTable().getExceptionTable();
					removeRow(expTable, etRuleSet, exceptionTableUpdateprovider, exceptionTableDataSource);
        			//Change/Remove style
				}				
			}  else if(REMOVE_COLUMN.equals(((MenuItem)event.getSource()).getTitle())) {
				if (event.getTarget() == dtTable) {
        			removeTableColumn(dtTable, colNum, AbstractDecisionTableEditor.this.getTable().getDecisionTable());
        			//Change/Remove style
				} else if (event.getTarget() == expTable) {
        			removeTableColumn(expTable, colNum, AbstractDecisionTableEditor.this.getTable().getExceptionTable());
        			//Change/Remove style
				}				
			}
		}

		@Override
		public void onMerge(MenuItemClickEvent event) {
			ModificationEntry modificationEntry = (ModificationEntry)((MenuItem) event.getSource()).getAttributeAsObject(MOD_ENTRY_ATTR);
			modificationEntry.setApplied(true);
			String title = ((MenuItem)event.getSource()).getTitle();
			if(ADD_ROW.equals(title)) {
				int rowNum = ((MenuItem) event.getSource()).getAttributeAsInt("rowNum");
				TableRuleVariableRecord record = null;
				TableDataGrid tableDataGrid = null;
				if (event.getTarget() == dtTable) {
					record = (TableRuleVariableRecord)dtTable.getSelectedRecord();
					tableDataGrid = dtTable;
				} else if (event.getTarget() == expTable) {
					record = (TableRuleVariableRecord)expTable.getSelectedRecord();
					tableDataGrid = expTable;
				}
				switch(modificationEntry.getModificationType()) {
	    		case ADDED:
	    			record.set_baseStyle("ws-dt-diff-add-apply-style");
	    			tableDataGrid.refreshRow(rowNum);
	    			break;
	    		case DELETED:
	    			record.set_baseStyle("ws-dt-diff-remove-apply-style");
	    			tableDataGrid.refreshRow(rowNum);
	    			break;
				}
			} else if(ADD_COLUMN.equals(((MenuItem)event.getSource()).getTitle())) {
				int colNum = ((MenuItem) event.getSource()).getAttributeAsInt("colNum");
				ListGridField columnField = dtTable.getField(colNum);
				columnField.setHeaderTitleStyle("ws-dt-diff-add-apply-style");
			} else if (MergeType.USE_LOCAL.getLiteral().equals(((MenuItem)event.getSource()).getTitle()) || MergeType.USE_BASE.getLiteral().equals(((MenuItem)event.getSource()).getTitle())) {
				int colNum = ((MenuItem) event.getSource()).getAttributeAsInt("colNum");
				int rowNum = ((MenuItem) event.getSource()).getAttributeAsInt("rowNum");
				dtTable.refreshCellStyle(rowNum, colNum);
			}
		}
	}

	/**
	 * Drop the argument in cell
	 * 
	 * @param isDecisionTable
	 * @param argPath
	 * @param argResource
	 */
	private void dropArgument(final boolean isDecisionTable,
			final String argPath, final ArgumentResource argResource) 
	{

		final int colNum = dtTable.getEventColumn();
		final int rowNum = dtTable.getEventRow();
		String argumentAlias = getArgumentAlias(argResource, argPath,
				isDecisionTable);

		ListGridField listGridField = dtTable.getField(colNum);

		// Drop is allowed in the columns of type text,float and integer only.
		if (ListGridFieldType.TEXT.equals(listGridField.getType())
				|| ListGridFieldType.FLOAT.equals(listGridField.getType())
				|| ListGridFieldType.INTEGER.equals(listGridField.getType()))
		{
			
			TableRuleSet tableRuleSet = table.getDecisionTable();
			if (null != tableRuleSet) 
			{

				List<TableRule> tableRules = tableRuleSet.getTableRules();
				if (null != tableRules && !tableRules.isEmpty()) {
					TableRule tableRule = tableRules.get(rowNum);
					if (null != tableRule) {
						List<TableRuleVariable> conditions = tableRule
								.getConditions();
						if (colNum > conditions.size()) {
							int index = colNum - conditions.size() - 1;
							List<TableRuleVariable> actions = tableRule
									.getActions();
							TableRuleVariable tableRuleVariable = actions
									.get(index);
							String expression = tableRuleVariable
									.getExpression();
							updateExpression(colNum, rowNum, argumentAlias,
									tableRuleVariable, expression);
						} else {
							if (null != conditions && !conditions.isEmpty()) {
								int index = colNum - 1;
								TableRuleVariable tableRuleVariable = conditions
										.get(index);
								String expression = tableRuleVariable
										.getExpression();
								updateExpression(colNum, rowNum, argumentAlias,
										tableRuleVariable, expression);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method is used to update the expression.
	 * 
	 * @param colNum
	 * @param rowNum
	 * @param argumentAlias
	 * @param tableRuleVariable
	 * @param expression
	 */
	private void updateExpression(final int colNum, final int rowNum,
			String argumentAlias, TableRuleVariable tableRuleVariable,
			String expression) {
		if (null == expression || expression.trim().isEmpty()) {
			expression = argumentAlias;
		} else {
			expression += argumentAlias;
		}
		tableRuleVariable.setExpression(expression);
		dtTable.startEditing(rowNum, colNum, true);
		dtTable.setEditValue(rowNum, colNum, expression);
		if (!this.isDirty()) {
			this.makeDirty();
		}
	}

	/**
	 * This method is used to get the argument alias.
	 * 
	 * @param argResource
	 * @param argumentPath
	 * @param isDecisionTable
	 * @return
	 */
	private String getArgumentAlias(ArgumentResource argResource,
			String argumentPath, boolean isDecisionTable) {
		String newArgName = argResource.isArray() ? argResource.getName()
				+ "[]" : argResource.getName();
		String alias = "";
		String newColumnName = newArgName;
		if (!argResource.isPrimitive()) {
			StringBuffer buffer = new StringBuffer();
			List<ArgumentResource> list = new ArrayList<ArgumentResource>();
			getParents(argResource, list);
			for (int i = list.size() - 1; i >= 0; i--) {
				buffer.append(DOT);
				String argName = list.get(i).getName();
				if (list.get(i).isArray()) {
					argName = argName + "[]";
				}
				//buffer.append(i == list.size() - 1 ? argName.toLowerCase() : argName);
				buffer.append(argName);
			}
			alias = buffer.toString().substring(1);
			String actAlias = getActualArgumentAlias(table, argumentPath);
			if (actAlias != null) {
				boolean aliasArray = false;
				String a = alias;
				String l = null;
				if (alias.indexOf(DOT) != -1) {
					a = alias.substring(0, alias.indexOf(DOT));
					l = alias.substring(alias.indexOf(DOT) + 1);
				}
				if (a.contains("[]")) {
					aliasArray = true;
				}
				alias = actAlias;
				if (aliasArray) {
					alias = alias + "[]";
				}
				if (l != null) {
					alias = alias + DOT + l;
				}
			}
			newColumnName = alias + DOT + newArgName;
		}
		return newColumnName;
	}

}