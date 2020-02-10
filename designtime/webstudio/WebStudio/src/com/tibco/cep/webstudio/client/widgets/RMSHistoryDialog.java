/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.smartgwt.client.widgets.DateChooser;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSRevisionDS;
import com.tibco.cep.webstudio.client.datasources.RMSRevisionDetailsDS;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;

/**
 * Dialog for checking the revision history associated with a selected artifact.
 * 
 * @author Vikram Patil
 */
public class RMSHistoryDialog extends AbstractWebStudioDialog implements MinimizeClickHandler, RestoreClickHandler {
	
	private ListGrid revisionGrid;
	private ListGrid revisionDetailsGrid;
	private String projectName;
	private String selectedArtifactPath;
	
	private DateChooser dateToField;
    private DateChooser dateFromField;
    private DateItem dateFrom;
    private DateItem dateTo;
    private TextItem dateFromTextItem;
    private TextItem dateToTextItem;
    
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	/**
	 * Default Constructor
	 */
	public RMSHistoryDialog() {
		setDialogWidth(710);
		setDialogHeight(490);
		setDialogTitle(rmsMsgBundle.rmsHistory_title() + " [" + WebStudio.get().getCurrentlySelectedArtifact().replace("$", "/") + "]");
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "history" + WebStudioMenubar.ICON_SUFFIX);
		setShowOkButton(false);
		setCancelButtonText(globalMsgBundle.button_close());	
		
		initialize();		
		String artifactPath = WebStudio.get().getCurrentlySelectedArtifact().replace("$", "/");
		projectName = artifactPath.substring(0, artifactPath.indexOf("/"));
		selectedArtifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));
		
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			setBodyStyle("historyWindowBody");
			setStyleName("historyWindow");
			setAutoCenter(false);
			moveTo(400, 100);
			buttonContainer.setStyleName("historyButtonContainer");
		}
		
		getRevisionHistory();
		
		setIsModal(false);
		this.addRestoreClickHandler(this);
		this.addMinimizeClickHandler(this);
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();

		widgetList.add(createCriteriaForm());
		widgetList.add(createRevisionGrid());
		widgetList.add(createRevisionDetailsGrid());
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			widgetList.add(createDateFromField());
			widgetList.add(createDateToField());
		}

		return widgetList;
	}
	
	private DateChooser createDateFromField(){
		dateFromField = new DateChooser();
		dateFromField.setPosition("absolute");
		dateFromField.setHeight(20);
		dateFromField.setStyleName("dateFromCalendar");
		dateFromField.setBaseWeekendStyle("dateChooserWeekendStyle");
		dateFromField.setFirstDayOfWeek(6);   
		dateFromField.setShowCancelButton(true);
		dateFromField.hide();
		dateFromField.addDataChangedHandler(new DataChangedHandler() {
			@Override
			public void onDataChanged(DataChangedEvent event) {
				// TODO Auto-generated method stub
				Date date = dateFromField.getData();	
				DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
                String formatedDate = dateFormat.format(date);
                dateFromTextItem.setValue(formatedDate);
				dateFromField.hide();
			}
     	});   
		
		return dateFromField;		
	}
	
	private DateChooser createDateToField(){
		dateToField = new DateChooser();
		dateToField.setPosition("absolute");
		dateToField.setStyleName("dateToCalendar");
		dateToField.setHeight(20);
        dateToField.setFirstDayOfWeek(6);	
        dateToField.setShowCancelButton(true);
        dateToField.setBaseWeekendStyle("dateChooserWeekendStyle");
        dateToField.hide();
        dateToField.addDataChangedHandler(new DataChangedHandler() {
			@Override
			public void onDataChanged(DataChangedEvent event) {
				// TODO Auto-generated method stub
				Date date = dateToField.getData();	                        
               dateToTextItem.setValue(date);  
               DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
               String formatedDate = dateFormat.format(date);
               dateToTextItem.setValue(formatedDate);
               dateToField.hide();	
			}
    	});
     
		return dateToField;
		
	}
	
	/**
	 * Create the criteria form for filtering selections.
	 * @return
	 */
	private HLayout createCriteriaForm() {
		final HLayout criteriaContainer = new HLayout(5);
		criteriaContainer.setWidth100();
		criteriaContainer.setHeight(30);
		criteriaContainer.setLayoutTopMargin(5);
		criteriaContainer.setLayoutBottomMargin(5);
		criteriaContainer.setLayoutLeftMargin(10);
		criteriaContainer.setLayoutRightMargin(10);
		criteriaContainer.setBorder("1px solid grey");
		criteriaContainer.setBackgroundColor("#e8e8e8");
		final DynamicForm criteriaForm = new DynamicForm();
		criteriaForm.setNumCols(6);
		criteriaForm.setMargin(0);
		
		final TextItem search = new TextItem("search", rmsMsgBundle.rmsHistory_search());
		search.setHint(rmsMsgBundle.rmsHistory_searchEmptyText());
		search.setShowHintInField(true);
		search.setWrapTitle(false);
		search.setWidth(200);
		search.setStartRow(false);
		search.setEndRow(true);
		
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			criteriaForm.setNumCols(10);
			final ButtonItem dateFromButtonItem = new ButtonItem(); 
			dateFromButtonItem.setIcon("[SKIN]DynamicForm/date_control.png");
			dateFromButtonItem.setBaseStyle("dateFromButton");
			dateFromButtonItem.setAutoFit(true);
			dateFromButtonItem.setStartRow(false);  
			dateFromButtonItem.setEndRow(false); 
			dateFromButtonItem.setWidth(20);
		    
		    dateFromTextItem = new TextItem(); 	
	        dateFromTextItem.setTitle(rmsMsgBundle.rmsHistory_from());   
	        dateFromTextItem.setWrapTitle(false); 
	        dateFromTextItem.setStartRow(true);
	        dateFromTextItem.setEndRow(false);
	        dateFromTextItem.setAlign(Alignment.RIGHT);
	        dateFromTextItem.setWidth(70);         
	        
	        dateFromButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler()  {  
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					// TODO Auto-generated method stub	
					if(dateToField != null){
						dateToField.hide();
					}
					dateFromField.setVisibility(Visibility.VISIBLE);   
					dateFromField.setHeight(20);	                
				}  
	        }); 
	        
	        dateToTextItem = new TextItem(); 		 
			final ButtonItem dateToButtonItem = new ButtonItem(); 
			dateToButtonItem.setIcon("[SKIN]DynamicForm/date_control.png");
			dateToButtonItem.setBaseStyle("dateToButton");
	        dateToButtonItem.setWidth(20); 
	        dateToButtonItem.setAutoFit(true);
	        dateToButtonItem.setStartRow(false);
	        dateToButtonItem.setEndRow(false);	        
	        
	        dateToTextItem.setTitle(rmsMsgBundle.rmsHistory_to());   
	        dateToTextItem.setAlign(Alignment.RIGHT);
	        dateToTextItem.setWrapTitle(false);  
	        dateToTextItem.setWidth(74);
	        dateToTextItem.setStartRow(false);
	        dateToTextItem.setEndRow(false);
	        
	        dateToButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler()  {  
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					// TODO Auto-generated method stub
					if(dateFromField != null){
						dateFromField.hide();
					}
					 dateToField.setVisibility(Visibility.VISIBLE);
	                 criteriaForm.redraw();
				}  
	        }); 
	        
	        criteriaForm.setItems(dateFromTextItem,dateFromButtonItem ,dateToTextItem,dateToButtonItem, search);
		}
		else{
			dateFrom = new DateItem("from", rmsMsgBundle.rmsHistory_from());
			dateFrom.setUseTextField(true);
			dateFrom.setWrapTitle(false);
			dateFrom.setWidth(100);
			dateFrom.setValidateOnExit(true);
			
			dateTo = new DateItem("to", rmsMsgBundle.rmsHistory_to());  
			dateTo.setUseTextField(true);
			dateTo.setWrapTitle(false);
			dateTo.setWidth(100);
			dateTo.setValidateOnExit(true);
			
			criteriaForm.setItems(dateFrom, dateTo, search);
		}	
		
		IButton goButton = new IButton(rmsMsgBundle.rmsHistory_go());
		goButton.setWidth(75);  
		goButton.setShowRollOver(true);  
		goButton.setShowDisabled(true);  
		goButton.setShowDown(true);
		goButton.setAlign(Alignment.CENTER);
		goButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (criteriaForm.hasErrors()) {
					return;//do nothing
				}
				AdvancedCriteria searchText = new AdvancedCriteria(OperatorId.OR, new Criterion[]{
						new Criterion(RMSRevisionDS.FIELD_NAME_USERNAME, OperatorId.ICONTAINS,search.getValueAsString()),
						new Criterion(RMSRevisionDS.FIELD_NAME_CHECKIN_COMMENT, OperatorId.ICONTAINS,search.getValueAsString())
				});
				AdvancedCriteria dateBetween = null;
				if (LocaleInfo.getCurrentLocale().isRTL()) {
					dateBetween = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
							new Criterion(RMSRevisionDS.FIELD_NAME_CHECKIN_TIME, OperatorId.GREATER_OR_EQUAL, dateFromField.getData()),
							new Criterion(RMSRevisionDS.FIELD_NAME_CHECKIN_TIME, OperatorId.LESS_OR_EQUAL, dateToField.getData())
					});
				}
				else{
					  dateBetween = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
							new Criterion(RMSRevisionDS.FIELD_NAME_CHECKIN_TIME, OperatorId.GREATER_OR_EQUAL, dateFrom.getValueAsDate()),
							new Criterion(RMSRevisionDS.FIELD_NAME_CHECKIN_TIME, OperatorId.LESS_OR_EQUAL, dateTo.getValueAsDate())
					});
				}
				
				
				AdvancedCriteria searchCrtieria = new AdvancedCriteria(OperatorId.AND, new Criterion[]{
						dateBetween,
						searchText
				});
				revisionGrid.filterData(searchCrtieria);//Filter the data in revision history grid.
				revisionDetailsGrid.setData(new ListGridRecord[]{});//Clear the data in revision history details grid if any.
			}
		});
		criteriaContainer.addMember(criteriaForm);
		criteriaContainer.addMember(goButton);
		
		return criteriaContainer;
	}
	
	/**
	 * Create the revision grid, listing out revision associated with a particular artifact.
	 * @return
	 */
	private SectionStack createRevisionGrid() {
		SectionStack sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight(175);
		
		SectionStackSection section = new SectionStackSection(rmsMsgBundle.rmsHistory_revisionsTitle());  
        section.setCanCollapse(false);
        section.setExpanded(true);
        
		revisionGrid = new ListGrid();
		revisionGrid.setWidth100();
		revisionGrid.setShowAllRecords(true);
		revisionGrid.setShowEmptyMessage(true);
		revisionGrid.setEmptyMessage(globalMsgBundle.message_noData());
		revisionGrid.setDataSource(RMSRevisionDS.getInstance());
		revisionGrid.setAutoFetchData(true);
		revisionGrid.setShowHeaderContextMenu(false);
		revisionGrid.setShowHeaderMenuButton(false);
		revisionGrid.addRecordClickHandler(new RecordClickHandler() {
			public void onRecordClick(RecordClickEvent event) {
				String revisionId = event.getRecord().getAttributeAsString(RMSRevisionDS.FIELD_NAME_REVISION_ID);
				RMSRevisionDetailsDS revisionDetailsDS = RMSRevisionDetailsDS.getInstance();
				revisionDetailsDS.clearRequestParameters();
				revisionDetailsDS.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
				revisionDetailsDS.setAdditionalURLPath("worklist/" + revisionId);
				revisionDetailsGrid.setDataSource(revisionDetailsDS);
				revisionDetailsGrid.fetchData();
			}
		});
		
		section.addItem(revisionGrid);
		sectionStack.setSections(section);
		
		return sectionStack;
	}
	
	/**
	 * Create the revision details grid, listing out the revision details associated with a particular artifact
	 * @return
	 */
	private SectionStack createRevisionDetailsGrid() {
		SectionStack sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setHeight(175);
		
		
		SectionStackSection section = new SectionStackSection(rmsMsgBundle.rmsHistory_revisionDetailsTitle());  
        section.setCanCollapse(false);
        section.setExpanded(true);  
        
        revisionDetailsGrid = new ListGrid() {
        	@Override
        	protected String getCellCSSText(ListGridRecord record, int rowNum,
        			int colNum) {
        		String commitOperation = record.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_COMMIT_OPERATION);
        		String cellColor = getCellColorByChangeType(commitOperation);
        		if (selectedArtifactPath.equals(record.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_ARTIFACT_PATH))) {
        			return (cellColor != null ? cellColor : "") + " font-weight:bold;";
        		}
        		else {
        			return (cellColor != null ? cellColor : "") + " opacity:.70; filter: alpha(opacity=70); ";//Faint out artifacts other than the one that was selected.
        		}
        	}
        };
		revisionDetailsGrid.setWidth100();
		revisionDetailsGrid.setShowAllRecords(true);
		revisionDetailsGrid.setShowEmptyMessage(true);
		revisionDetailsGrid.setEmptyMessage(globalMsgBundle.message_noData());
		revisionDetailsGrid.setShowHeaderContextMenu(false);
		revisionDetailsGrid.setShowHeaderMenuButton(false);
		revisionDetailsGrid.setDataSource(RMSRevisionDetailsDS.getInstance());
		revisionDetailsGrid.setAutoFetchData(false);
		revisionDetailsGrid.addRowContextClickHandler(new RowContextClickHandler() {
			@Override
			public void onRowContextClick(RowContextClickEvent event) {
				ListGridRecord selectedRecord = event.getRecord();
				if (selectedRecord != null) {
					String commitOperation = selectedRecord.getAttribute("operationType");
					if ("Modify".equalsIgnoreCase(commitOperation)) {
						final String artifactPath = selectedRecord.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_ARTIFACT_PATH);
						final String revisionId = selectedRecord.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_REVISION_ID);
						final String artifactFileExtn = selectedRecord.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_ARTIFACT_EXTN);
						final String artifactType = selectedRecord.getAttributeAsString(RMSRevisionDetailsDS.FIELD_NAME_ARTIFACT_TYPE);
						
						Menu contextMenu = new Menu();
						MenuItem diffMenuItem = new MenuItem(globalMsgBundle.menu_rmsShowDiff(), WebStudioMenubar.ICON_PREFIX + "diff.png");
						diffMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							@Override
							public void onClick(MenuItemClickEvent event) {
								if (artifactType.equals("ruletemplateinstance") || artifactType.equals("rulefunctionimpl")) {
									RMSHistoryDialog.this.minimize();
									setMinimizeCoordinates();
									
									// Open artifact diff
									openArtifactForDiff(projectName + artifactPath + "." + artifactFileExtn, revisionId);
								} else {
									CustomSC.warn(globalMsgBundle.explorer_DiffResource_error());
								}
							}
						});
						contextMenu.addItem(diffMenuItem);
						revisionDetailsGrid.setContextMenu(contextMenu);
						contextMenu.show();
					} else {
						revisionDetailsGrid.setContextMenu(null);
						event.cancel();
					}
				}
			}
		});
		
		section.addItem(revisionDetailsGrid);
		sectionStack.setSections(section);
		
		return sectionStack;
	}
	
	@Override
	public void onAction() {
	}
	
	/**
	 * Fetch checkin history for the artifact.
	 */
	private void getRevisionHistory() {
		RMSRevisionDS.getInstance().clearRequestParameters();
		
		String artifactPath = WebStudio.get().getCurrentlySelectedArtifact().replace("$", "/");
		String artifactExtention = artifactPath.substring(artifactPath.indexOf(".") + 1, artifactPath.length());
		artifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));
		
		RMSRevisionDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		RMSRevisionDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, artifactExtention));
		RMSRevisionDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		
		cleanupCache(revisionGrid);
		revisionGrid.getDataSource().invalidateCache();
		revisionGrid.addSort(new SortSpecifier(RMSRevisionDS.FIELD_NAME_REVISION_ID, SortDirection.DESCENDING));
	}
	
	/**
	 * Open artifact in diff mode, comparing it with previous approved version.
	 */
	private void openArtifactForDiff(String completeArtifactPath, String revisionId) {
		NavigatorResource selectedResource = getResource(completeArtifactPath);
		if (selectedResource.getType().equals("ruletemplateinstance") || selectedResource.getType().equals("rulefunctionimpl")) {
			Tab[] tabs = WebStudio.get().getEditorPanel().getTabs();
			for (Tab tab : tabs) {
				if (tab instanceof AbstractEditor) {
					if (selectedResource.equals(((AbstractEditor) tab).getSelectedResource()) && ((AbstractEditor) tab).isReadOnly() && ((AbstractEditor) tab).getRevisionId().equals(revisionId)) {
						WebStudio.get().showWorkSpacePage();
						WebStudio.get().getEditorPanel().selectTab(tab);						
						return;
					}
				}
			}
		}
		
		IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(selectedResource);
		
		Tab editor = null;
		if (editorFactory instanceof RuleTemplateInstanceEditorFactory) {
			RuleTemplateInstanceEditorFactory rtiEditorFactory = (RuleTemplateInstanceEditorFactory) editorFactory;
			RuleTemplateInstanceEditor rtiEditor = (RuleTemplateInstanceEditor) rtiEditorFactory.createEditor(selectedResource, true, revisionId, true, null);
			
			editor = rtiEditor;
		} else if (editorFactory instanceof DecisionTableEditorFactory) {
			DecisionTableEditorFactory dtEditorFactory = (DecisionTableEditorFactory) editorFactory;
			DecisionTableEditor dtEditor = (DecisionTableEditor) dtEditorFactory.createEditor(selectedResource, true, revisionId, true, null);
			
			editor = dtEditor;
		}
		
		WebStudio.get().showWorkSpacePage();
		WebStudio.get().getEditorPanel().addTab(editor);
		WebStudio.get().getEditorPanel().selectTab(editor);
	}
	
	/**
	 * Create the resource for the selected Artifact
	 * 
	 * @param selectedArtifact
	 * @return
	 */
	private NavigatorResource getResource(String completeArtifactPath) {
		String artifactId = completeArtifactPath.replace("/", "$");
		String artifactName = artifactId.substring(artifactId.lastIndexOf("$") + 1, artifactId.length());
		String artifactParent = artifactId.substring(0, artifactId.lastIndexOf("$"));
		String artifactFileExtn = completeArtifactPath.substring(completeArtifactPath.indexOf(".") + 1, completeArtifactPath.length());
		
		NavigatorResource resource = ProjectExplorerUtil.createProjectResource(artifactName, artifactParent, artifactFileExtn, artifactId, false);
		
		return resource;
	}
	
	/**
	 * Set appropriate coordinates for minimising the worklist dialog
	 */
	private void setMinimizeCoordinates() {
		int top = WebStudio.get().getEditorPanel().getAbsoluteTop() + WebStudio.get().getEditorPanel().getHeight();
//		top -= RMSHistoryDialog.this.getMinimizeHeight();
		RMSHistoryDialog.this.setLeft(0);
		RMSHistoryDialog.this.setTop(top);
	}
	
	@Override
	public void onMinimizeClick(MinimizeClickEvent event) {
		setMinimizeCoordinates();
	}
	
	@Override
	public void onRestoreClick(RestoreClickEvent event) {
		this.setAutoCenter(true);
		this.centerInPage();
	}
	
	@Override
	public void onCloseClick(CloseClickEvent event) {
		revisionGrid.getDataSource().invalidateCache();
		super.onCloseClick(event);
	}
}
