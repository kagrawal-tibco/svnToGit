/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSArtifactOperationDisplayUtil;
import com.tibco.cep.webstudio.client.datasources.RMSRoleDS;
import com.tibco.cep.webstudio.client.datasources.RMSWorklistDS;
import com.tibco.cep.webstudio.client.datasources.RMSWorklistDetailsDS;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.WorklistItem;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.portal.DASHBOARD_PORTLETS;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDelegationDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.WorklistDeleteDataItem;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectValidationHelper;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog for viewing/approving the worklist plus view the audit trail of a
 * particular artifact
 * 
 * @author Vikram Patil
 */
public class RMSWorklistDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler, MinimizeClickHandler, RestoreClickHandler {
	private ListGrid worklistGrid;
	private List<WorklistItem> worklistItems;
	private Map<String, ListGrid> worklistsToItemsMapping;
	private SelectItem delegateItem;
	private Label messageLabel;
	private IButton actionButton, applyButton;
	private SelectItem action;//Drop down to select action (Delegate/Delete worklist item)
	
	private List<String> projectsForDeployableCheck = new ArrayList<String>();
	private Label responseLabel;
	
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

    private String progressMsg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
    + Canvas.imgHTML(Page.getAppImgDir() + "icons/16/gen_deploy_loading.gif") + "<br>" + rmsMsgBundle.rmsWorklist_buildAndDeployInProgress() + "..";

	/**
	 * Default Constructor
	 */
	public RMSWorklistDialog() {
		this(null);
	}
	
	/**
	 * 
	 * @param button
	 */
	public RMSWorklistDialog(IButton button) {
		setDialogWidth(812);
		setDialogHeight(350);
		setDialogTitle(rmsMsgBundle.rmsWorklist_title());
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "worklist" + WebStudioMenubar.ICON_SUFFIX);

		worklistItems = new ArrayList<WorklistItem>();
		worklistsToItemsMapping = new HashMap<String, ListGrid>();
		actionButton = button;
		
		initialize();
		
		if (actionButton == null) {
			actionButton = okButton;
		}
		
		setIsModal(false);
		
		this.addRestoreClickHandler(this);
		this.addMinimizeClickHandler(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#getWidgetList
	 * ()
	 */
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();

		widgetList.add(createWorklistGrid());
		if (actionButton == okButton) {
			widgetList.add(createDelegationAndCleanUpWidget());
		}
		widgetList.add(createResponseLabel());
		return widgetList;
	}

	private Label createResponseLabel() {
		responseLabel = new Label();
		responseLabel.setWidth100();
		responseLabel.setHeight(10);
		responseLabel.hide();
		return responseLabel;
	}

	/**
	 * Create the Worklist items grid, listing out all worklist items associated
	 * to a revisiont.
	 * 
	 * @return
	 */
	private VLayout createWorklistGrid() {
		VLayout gridLayout = new VLayout();
		gridLayout.setWidth100();
		gridLayout.setHeight(310);
		
		worklistGrid = new ListGrid() {
			@Override
			protected Canvas getExpansionComponent(ListGridRecord record) {
				String revisionId = record.getAttributeAsString("revisionId");

				ListGrid worklistDetailsGrid = worklistsToItemsMapping.get(revisionId);
				if (worklistDetailsGrid == null) {
					worklistDetailsGrid = RMSWorklistDialog.this.createWorklistDetailsGrid(record, revisionId);
					worklistsToItemsMapping.put(revisionId, worklistDetailsGrid);

					RMSWorklistDetailsDS worklistDS = (RMSWorklistDetailsDS) worklistDetailsGrid.getDataSource();
					worklistDS.clearRequestParameters();
					worklistDS.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_REVISION_ID, revisionId));
					worklistDS.setAdditionalURLPath("worklist/" + revisionId);

					if (worklistDetailsGrid.getResultSet() != null) {
						worklistDetailsGrid.getResultSet().invalidateCache();
					}

					worklistDetailsGrid.fetchData();
				}

				return worklistDetailsGrid;
			}
		};

		worklistGrid.setWidth100();
		worklistGrid.setHeight100();
		worklistGrid.setShowAllRecords(true);
		worklistGrid.setShowEmptyMessage(true);
		worklistGrid.setEmptyMessage(globalMsgBundle.message_noData());
		worklistGrid.setDataSource(RMSWorklistDS.getInstance());
		worklistGrid.setAutoFetchData(true);
		worklistGrid.setShowHeaderContextMenu(false);
		worklistGrid.setShowHeaderMenuButton(false);
		worklistGrid.setCanExpandRecords(true);
		worklistGrid.setEditEvent(ListGridEditEvent.CLICK);
		worklistGrid.setEditByCell(true);
		worklistGrid.setCanExpandMultipleRecords(false);
		worklistGrid.setSelectOnEdit(false);
		
		if (actionButton == okButton) {
			worklistGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			worklistGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
				public void onSelectionUpdated(SelectionUpdatedEvent event) {
					updateButtonStatuses();
				}
			});
		}

		gridLayout.addMember(worklistGrid);

		return gridLayout;
	}

	/**
	 * Create the revision details grid, listing out the revision details
	 * associated with a particular artifact
	 * 
	 * @return
	 */
	private ListGrid createWorklistDetailsGrid(ListGridRecord record, String revisionId) {
		final ListGrid worklistDetailsGrid = new ListGrid(){
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				String commitOperation = record.getAttributeAsString("commitOperation");
				String cellColor = getCellColorByChangeType(commitOperation);
				if (cellColor != null) {
					return cellColor;
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			}
		};
		worklistDetailsGrid.setWidth100();
		worklistDetailsGrid.setHeight(150);
		worklistDetailsGrid.setShowAllRecords(true);
		worklistDetailsGrid.setShowEmptyMessage(true);
		worklistDetailsGrid.setEmptyMessage(globalMsgBundle.message_noData());
		worklistDetailsGrid.setShowHeaderContextMenu(false);
		worklistDetailsGrid.setShowHeaderMenuButton(false);
		worklistDetailsGrid.setDataSource(RMSWorklistDetailsDS.getInstance(revisionId));
		worklistDetailsGrid.setAutoFetchData(false);
		worklistDetailsGrid.setEditEvent(ListGridEditEvent.CLICK);
		worklistDetailsGrid.setEditByCell(true);
		worklistDetailsGrid.setSaveLocally(true);
		
		if (actionButton == okButton) {
			worklistDetailsGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
		}
		
		worklistDetailsGrid.addEditCompleteHandler(new EditCompleteHandler() {
			public void onEditComplete(EditCompleteEvent event) {
				addWorklistItem(event.getOldRecord(), getSelectedProjectName(), event.getNewValues());
				actionButton.enable();
			}
		});
		
		
		worklistDetailsGrid.addCellClickHandler(new CellClickHandler() {
			public void onCellClick(CellClickEvent event) {
				ListGridRecord selectedRecord = worklistDetailsGrid.getSelectedRecord(); 
				if (selectedRecord != null) {
					String statusOptions = selectedRecord.getAttributeAsString("applicableStages");
					String currentStatus = selectedRecord.getAttributeAsString("reviewStatus");
					selectedRecord.setAttribute("reviewStatus", RMSArtifactOperationDisplayUtil.getDisplayName(currentStatus));
					if (statusOptions != null && !statusOptions.isEmpty()) {
						if ((statusOptions.trim().toLowerCase().equals(currentStatus.trim().toLowerCase()) && worklistItems
								.size() == 0 && !currentStatus.equalsIgnoreCase(RMSArtifactOperationDisplayUtil.getDisplayName("BuildAndDeploy")))
								|| (worklistDetailsGrid.getField(event.getColNum()).getName().trim().equals("reviewComments") && statusOptions.indexOf(currentStatus) == -1 
								&& !currentStatus.equalsIgnoreCase(RMSArtifactOperationDisplayUtil.getDisplayName("BuildAndDeploy")))) {
							worklistDetailsGrid.getField("reviewComments").setCanEdit(false);
						} else {
							worklistDetailsGrid.getField("reviewComments").setCanEdit(true);
							String[] optionsMap = statusOptions.split(",");
							LinkedHashMap<String, String> options = new LinkedHashMap<String, String> ();
							for(String value : optionsMap) {
								options.put(value.toString(), RMSArtifactOperationDisplayUtil.getDisplayName(value));
							}
							worklistDetailsGrid.setValueMap("reviewStatus", options);
							
							//if (statusOptions.equals(RMSArtifactOperationDisplayUtil.getDisplayName("BuildAndDeploy"))) {
							  if (statusOptions.equals("BuildAndDeploy")){
								worklistDetailsGrid.getField("deployEnvironments").setCanEdit(true);
								String applicableEnvironments = selectedRecord.getAttributeAsString("applicableEnvironments");							
								String[] applicableEnvironmentMap = (applicableEnvironments != null && applicableEnvironments.indexOf(",") != -1) ? applicableEnvironments.split(",") : new String[] {applicableEnvironments};

								options.clear();
								for(String value : applicableEnvironmentMap) {
									options.put(value.toString(), RMSArtifactOperationDisplayUtil.getDisplayName(value));
								}
								worklistDetailsGrid.setValueMap("deployEnvironments", options);
							}
						}
					} else {
						worklistDetailsGrid.getField("reviewComments").setCanEdit(false);
						worklistDetailsGrid.setValueMap("reviewStatus", new String[]{});
					}
				}
			}
		});
		
		worklistDetailsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				String artifactType = event.getRecord().getAttributeAsString("artifactType");
				if (artifactType.equals("ruletemplateinstance") || artifactType.equals("rulefunctionimpl") || artifactType.equals("domain")) {
					if (actionButton == okButton) {
						RMSWorklistDialog.this.minimize();
						setMinimizeCoordinates();
					} else {
						// case for when opened via portlet
						WebStudio.get().getPortalPage().restorePortlet(DASHBOARD_PORTLETS.WORKLIST.getTitle());
					}
					openArtifactForReview(event.getRecord());
				} else {
					CustomSC.warn(globalMsgBundle.explorer_ViewResource_error());
				}
			}
		});
		
		worklistDetailsGrid.addRowContextClickHandler(new RowContextClickHandler() {
			@Override
			public void onRowContextClick(RowContextClickEvent event) {
				ListGridRecord selectedRecord = event.getRecord();
				if (selectedRecord != null) {
					String commitOperation = selectedRecord.getAttributeAsString("operationType");
					
					if (commitOperation.equalsIgnoreCase("Modify")) {
						final String artifactPath = selectedRecord.getAttributeAsString("artifactPath");
						final String artifactType = selectedRecord.getAttributeAsString("artifactType");
						final String revisionId = selectedRecord.getAttributeAsString("revisionId");
						final String artifactFileExtn = selectedRecord.getAttributeAsString("artifactFileExtn");
						
						Menu contextMenu = new Menu();
						MenuItem diffMenuItem = new MenuItem(globalMsgBundle.menu_rmsShowDiff(), WebStudioMenubar.ICON_PREFIX + "diff.png");
						diffMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							@Override
							public void onClick(MenuItemClickEvent event) {
								if (artifactType.equals("ruletemplateinstance") || artifactType.equals("rulefunctionimpl")) {
									RMSWorklistDialog.this.minimize();
									setMinimizeCoordinates();								
									Record eventRecord = new Record();
									eventRecord.setAttribute("artifactPath", artifactPath);
									eventRecord.setAttribute("artifactType", artifactType);
									eventRecord.setAttribute("revisionId", revisionId);
									eventRecord.setAttribute("artifactFileExtn", artifactFileExtn);
									// Open artifact diff
									openArtifactForDiff(eventRecord);
								} else {
									CustomSC.warn(globalMsgBundle.explorer_DiffResource_error());
								}
							}
						});
						contextMenu.addItem(diffMenuItem);
						worklistDetailsGrid.setContextMenu(contextMenu);
						contextMenu.show();
					} else {
						worklistDetailsGrid.setContextMenu(null);
						event.cancel();
					}
				}
			}
		});

		return worklistDetailsGrid;
	}
	
	/**
	 * Creates a container for Delegation and CleanUp Widget
	 * 
	 * @return
	 */
	private HLayout createDelegationAndCleanUpWidget() {
		final HLayout bottomLayout = new HLayout(2);
		
		DynamicForm actionForm = new DynamicForm();
		
		action = new SelectItem();
		//action.setWidth(100);
		action.setValueMap(rmsMsgBundle.rmsWorklist_delegateSelected(), rmsMsgBundle.rmsWorklist_deleteSelected());
		action.setHint(rmsMsgBundle.rmsWorklist_selectAction());
		action.setShowHintInField(true);
		action.setShowTitle(false);
		action.addChangedHandler(new ChangedHandler(){
			@Override
			public void onChanged(ChangedEvent event) {
				final String actionValue = (String)event.getValue();
				if (actionValue.equals(rmsMsgBundle.rmsWorklist_delegateSelected())) {
					DynamicForm form = new DynamicForm();
					form.setID("delegationForm");
					form.setWidth(120);
					form.setAlign(Alignment.LEFT);
			
					delegateItem = new SelectItem("role");
					delegateItem.setWrapTitle(false);
					delegateItem.setWidth(100);
					delegateItem.setHeight(22);
					delegateItem.setTitle(rmsMsgBundle.rmsWorklist_delegateTo_text());
					delegateItem.setAutoFetchData(true);
					delegateItem.setOptionDataSource(RMSRoleDS.getInstance());
					delegateItem.setDisabled(true);
					delegateItem.setMultiple(true);
					delegateItem.setMultipleAppearance(MultipleAppearance.PICKLIST);
					delegateItem.addChangedHandler(new ChangedHandler() {
						@Override
						public void onChanged(ChangedEvent event) {
							updateButtonStatuses();
						}
					});
					
					form.setItems(delegateItem);
					if (bottomLayout.getMembers().length == 2) {
						bottomLayout.addMember(form, 1);
					} else {
						bottomLayout.addMember(form);
					}
				} else {
					Canvas memberForm = bottomLayout.getMember("delegationForm");
					if(memberForm != null) {
						bottomLayout.removeMember(memberForm);					
						memberForm.destroy();
						delegateItem = null;
					}
				}
				
				Canvas applyAction = bottomLayout.getMember("applyAction");
				if (applyAction == null) {
					applyButton = new IButton(globalMsgBundle.button_apply());
					applyButton.setTooltip(globalMsgBundle.button_apply());
					applyButton.setWidth(80);
					applyButton.setHeight(23);
					applyButton.setID("applyAction");
					applyButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							applyButton.disable();
							if (action.getValueAsString().equals(rmsMsgBundle.rmsWorklist_delegateSelected())) {
								delegateItem.disable();

								WorklistDelegationDataItem delegationDataItem = new WorklistDelegationDataItem();
								delegationDataItem.setRoles(Arrays.asList(delegateItem.getValues()));

								List<String> revisionIds = new ArrayList<String>(); 
								for (ListGridRecord record : worklistGrid.getSelectedRecords()) {
									revisionIds.add(record.getAttributeAsString("revisionId"));
								}
								delegationDataItem.setRevisionId(revisionIds);

								delegateWorklistItems(delegationDataItem);
							} else {
								WorklistDeleteDataItem deleteDataItem = new WorklistDeleteDataItem();
								List<String> revisionIds = new ArrayList<String>();
								for (ListGridRecord record : worklistGrid.getSelectedRecords()) {
									String revisionId = record.getAttributeAsString("revisionId");
									revisionIds.add(revisionId);
								}
								deleteDataItem.setRevisionId(revisionIds);
								deleteWorklistItems(deleteDataItem);
							}
						}
					});
					bottomLayout.addMember(applyButton);
				}
				updateButtonStatuses();
			}
		});
		
		actionForm.setItems(action);
		bottomLayout.addMember(actionForm);

		return bottomLayout;
	}
	
	private void deleteWorklistItems(WorklistDeleteDataItem deleteDataItem) {
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, deleteDataItem);
		
		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_DELETE_WORKLIST_ITEM, requestParameters);
		ArtifactUtil.addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.DELETE);
		
		request.submit(ServerEndpoints.RMS_DELETE_WORKLIST_ITEM.getURL());
	}
	
	/**
	 * Push Worklist item delegation to another role
	 * @param delegationDataItem
	 */
	private void delegateWorklistItems(WorklistDelegationDataItem delegationDataItem) {
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, delegationDataItem);
		
		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_PUT_DELEGATE_WORKLIST_ITEM, requestParameters);
		
		ArtifactUtil.addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);
		
		request.submit(ServerEndpoints.RMS_PUT_DELEGATE_WORKLIST_ITEM.getURL());
	}

	Map<String, List<RequestParameter>> requestParameters = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#onAction()
	 */
	@Override
	public void onAction() {
		projectsForDeployableCheck.clear();
		requestParameters = getRevisionWorklistMapping();
		ArtifactUtil.addHandlers(this);
		if (projectsForDeployableCheck.size() > 0) {
			responseLabel.setContents(progressMsg);
			responseLabel.show();
			HttpRequest request = new HttpRequest();
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectsForDeployableCheck));
			request.submit(ServerEndpoints.RMS_CHECK_DEPLOYABLE.getURL());
		} else {
			submitWorklistItems();
		}
	}
	
	

	/**
	 * Add Data to worklist items
	 * 
	 * @param record
	 */
	private void addWorklistItem(Record oldrecord, String projectName, Map<String, String> newValues) {
		String revisionId = oldrecord.getAttributeAsString("revisionId");
		String artifactPath = oldrecord.getAttributeAsString("artifactPath");
		String artifactType = oldrecord.getAttributeAsString("artifactType");
		String reviewStatus = (String) ((newValues.get("reviewStatus") != null) ? newValues.get("reviewStatus") : oldrecord.getAttributeAsString("reviewStatus"));
		String reviewComments = (String) ((newValues.get("reviewComments") != null) ? newValues.get("reviewComments") : oldrecord.getAttributeAsString("reviewComments"));
		String deployEnvironments = newValues.get("deployEnvironments"); 

		WorklistItem newWorkListItem = new WorklistItem(revisionId,
				artifactPath,
				artifactType,
				reviewStatus,
				reviewComments,
				projectName);
		if (deployEnvironments != null) newWorkListItem.setDeployEnvironments(deployEnvironments);

		WorklistItem existingWorklistItem = null;
		if (worklistItems.contains(newWorkListItem)) {
			existingWorklistItem = worklistItems.get(worklistItems.indexOf(newWorkListItem));
			existingWorklistItem.setReviewStatus(reviewStatus);
			existingWorklistItem.setReviewComments(reviewComments);
			if (deployEnvironments != null) existingWorklistItem.setDeployEnvironments(deployEnvironments);
		} else {
			worklistItems.add(getInsertPoint(revisionId),newWorkListItem);
		}
	}
	
	/**
	 * Get the appropriate insertion point for the revision, so the data is sorted by modification order
	 * 
	 * @param currentRevisionId
	 * @return
	 */
	private int getInsertPoint(String currentRevisionId) {
		int currentRevId = Integer.parseInt(currentRevisionId);
		
		int revId = -1;
		for (int i = 0; i < worklistItems.size(); i++) {
			revId = Integer.parseInt(worklistItems.get(i).getRevisionId());
			if (currentRevId < revId) {
				return i;
			}
		}
		return worklistItems.size();
	}

	/**
	 * Return the selected project name for the edited worklist
	 * 
	 * @return
	 */
	private String getSelectedProjectName() {
		ListGridRecord record = worklistGrid.getSelectedRecord();
		if (record == null) {
			for (ListGridRecord worklistItem : worklistGrid.getRecords()) {
				if (worklistGrid.getCurrentExpansionComponent(worklistItem) != null) {
					record = worklistItem;
					break;
				}
			}
		}

		String projectName = null;
		if (record != null) {
			projectName = record.getAttributeAsString("managedProjectName");
		}
		return projectName;
	}

	/**
	 * Create a revision to worklist item mapping
	 * 
	 * @return
	 */
	private Map<String, List<RequestParameter>> getRevisionWorklistMapping() {
		Map<String, List<RequestParameter>> revisionWorklistMap = new HashMap<String, List<RequestParameter>>();

		for (WorklistItem worklistItem : worklistItems) {
			String revisionId = worklistItem.getRevisionId();

			if (!revisionWorklistMap.keySet().contains(revisionId)) {
				List<RequestParameter> wklistItems = new ArrayList<RequestParameter>();
				wklistItems.add(createWorklistRequestParameter(worklistItem));
				revisionWorklistMap.put(revisionId, wklistItems);
			} else {
				List<RequestParameter> wklistItems = (List<RequestParameter>) revisionWorklistMap.get(revisionId);
				wklistItems.add(createWorklistRequestParameter(worklistItem));
			}
		}

		return revisionWorklistMap;
	}

	/**
	 * Create a Worklist Request Parameter
	 * 
	 * @param worklistItem
	 * @return
	 */
	private RequestParameter createWorklistRequestParameter(WorklistItem worklistItem) {
		RequestParameter parameter = new RequestParameter(RequestParameter.REQUEST_PARAM_PATH,
				worklistItem.getArtifactPath());
		parameter.add(RequestParameter.REQUEST_PARAM_TYPE, worklistItem.getArtifactType());
		parameter.add(RequestParameter.REQUEST_PROJECT_NAME, worklistItem.getProjectName());
		parameter.add(RequestParameter.REQUEST_PARAM_APPROVAL_STATUS, worklistItem.getReviewStatus());
		if (worklistItem.getArtifactType().equals("rulefunctionimpl") 
				&& worklistItem.getReviewStatus().equals("BuildAndDeploy")) {
			projectsForDeployableCheck.add(worklistItem.getProjectName());
		}
		if (worklistItem.getReviewStatus().equals("BuildAndDeploy")) {
			if (worklistItem.getDeployEnvironments() == null || worklistItem.getDeployEnvironments().isEmpty()) {
				//TODO  - error case
			}
			parameter.add(RequestParameter.REQUEST_PARAM_APPROVAL_ENVIRONMENTS, worklistItem.getDeployEnvironments());
		}
		parameter.add(RequestParameter.REQUEST_PARAM_APPROVAL_COMMENTS, worklistItem.getReviewComments());

		return parameter;
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
	
		boolean bValidEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_WORKLIST_ITEMS.getURL()) != -1) {
			
			clearLocks();
		
			if (this.actionButton == okButton) {
				this.destroy();
				bValidEvent = true;				
			} else {
				this.actionButton.disable();
				clearWorklistState();
				responseLabel.hide();
				bValidEvent = true;
			}
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_DELEGATE_WORKLIST_ITEM.getURL()) != -1
				|| event.getUrl().indexOf(ServerEndpoints.RMS_DELETE_WORKLIST_ITEM.getURL()) != -1) {
			bValidEvent = true;
			
			for (ListGridRecord record : worklistGrid.getRecords()) {
				worklistGrid.collapseRecord(record);
			}
			
			if (worklistGrid.getResultSet() != null){
				worklistGrid.getResultSet().invalidateCache();
			}
			worklistGrid.fetchData();
			
			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("responseMessage").item(0).getFirstChild().getNodeValue();
			responseMessage = event.getUrl().indexOf(ServerEndpoints.RMS_PUT_DELEGATE_WORKLIST_ITEM.getURL()) != -1 ?
									globalizeDelegateSuccessMsg(responseMessage)
									: globalizeDeleteSuccessMsg(responseMessage);
			messageLabel.setContents(responseMessage);
		} else 	if (event.getUrl().indexOf(ServerEndpoints.RMS_CHECK_DEPLOYABLE.getURL()) != -1) {
			submitWorklistItems();
		}
		
		if (bValidEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void submitWorklistItems() {
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_WORKLIST_ITEMS, requestParameters);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.POST);
		request.setPostData(xmlData);
		request.submit(ServerEndpoints.RMS_POST_WORKLIST_ITEMS.getURL());
	}
	

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean bValidEvent = false;
		boolean isPostWorklistItem = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_WORKLIST_ITEMS.getURL()) != -1) {
			bValidEvent = true;
			isPostWorklistItem = true;
			okButton.disable();
			responseLabel.hide();
			if (this.actionButton != okButton) {
				this.actionButton.disable();
			}
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_DELEGATE_WORKLIST_ITEM.getURL()) != -1) {
			bValidEvent = true;
			delegateItem.enable();
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_CHECK_DEPLOYABLE.getURL()) != -1) {
			bValidEvent = true;
			okButton.disable();
			responseLabel.hide();
		}
		if (bValidEvent) {
			if (isPostWorklistItem && event.getData().getElementsByTagName("errors").item(0).hasChildNodes()) {
				ProjectValidationHelper.getInstance().postProblems(event.getData());
				ArtifactUtil.removeHandlers(this);
				clearWorklistState();
			} else {
				String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
				ErrorMessageDialog.showError(responseMessage);
				ArtifactUtil.removeHandlers(this);
				clearWorklistState();
			}
		}
	}
	
	/**
	 * Clear locks if auto unlock on Review is enabled
	 */
	private void clearLocks() {
		if (WebStudio.get().getUserPreference().isAutoUnLockOnReview()) {
			
			WebStudioNavigatorGrid navigatorGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
			
			String artifactId = null;
			NavigatorResource navigatorResource = null;
			for (WorklistItem workListItem : worklistItems) {
				if (workListItem.getReviewStatus().equals("Approve") || workListItem.getReviewStatus().equals("Reject")) {
					artifactId = workListItem.getProjectName() + workListItem.getArtifactPath() + "." + workListItem.getArtifactType();
					artifactId = artifactId.replace("/", "$");
					
					navigatorResource = navigatorGrid.getResourceById(artifactId);
					if (navigatorResource != null) {
						navigatorResource.setLocked(false);
					}
				}
			}
		}
	}
	
	/**
	 * Creates a label for associated commit revision
	 * 
	 * @return
	 */
	@Override
	protected void addCustomWidget(HLayout container) {
		messageLabel = new Label();
		messageLabel.setWidth100();
		messageLabel.setHeight100();
		messageLabel.setStyleName("ws-commit-revision-message");
		
		container.addMember(messageLabel);
	}
	
	public ListGrid getWorklistGrid() {
		return worklistGrid;
	}
	
	/**
	 * Create the resource for the selected Artifact
	 * 
	 * @param selectedArtifact
	 * @return
	 */
	private NavigatorResource getResource(Record selectedArtifact) {
		String projectName = getSelectedProjectName();
		String artifactPath = projectName + selectedArtifact.getAttributeAsString("artifactPath") + "."
				+ selectedArtifact.getAttributeAsString("artifactFileExtn");

		String artifactId = artifactPath.replace("/", "$");
		String artifactName = artifactId.substring(artifactId.lastIndexOf("$")+1, artifactId.length());
		String artifactParent = artifactId.substring(0, artifactId.lastIndexOf("$"));
		
		NavigatorResource resource = ProjectExplorerUtil.createProjectResource(artifactName, artifactParent, selectedArtifact.getAttributeAsString("artifactFileExtn"), artifactId, false);
		
		return resource;
	}
	
	/**
	 * Open the selected artifact for review
	 * 
	 * @param selectedArtifact
	 */
	private void openArtifactForReview(Record selectedArtifact) {
		NavigatorResource selectedResource = getResource(selectedArtifact);
		
		String revisionId = selectedArtifact.getAttributeAsString("revisionId");

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
		
		IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(selectedResource);
		Tab editor = editorFactory.createEditor(selectedResource, true, revisionId);
		
		WebStudio.get().showWorkSpacePage();
		WebStudio.get().getEditorPanel().addTab(editor);
		WebStudio.get().getEditorPanel().selectTab(editor);
	}
	
	/**
	 * Set appropriate coordinates for minimizing the worklist dialog
	 */
	private void setMinimizeCoordinates() {
		int top = WebStudio.get().getEditorPanel().getAbsoluteTop() + WebStudio.get().getEditorPanel().getHeight();
//		top -= RMSWorklistDialog.this.getMinimizeHeight();
		RMSWorklistDialog.this.setLeft(0);
		RMSWorklistDialog.this.setTop(top);
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
	
	/**
	 * Clear any previous worklist state and reload data
	 */
	private void clearWorklistState() {
		for (ListGridRecord record : worklistGrid.getRecords()) {
			worklistGrid.collapseRecord(record);
		}
		
		if (worklistGrid.getResultSet() != null){
			worklistGrid.getResultSet().invalidateCache();
		}
		worklistGrid.fetchData();
		
		worklistItems.clear();
		worklistsToItemsMapping.clear();
	}
	
	/**
	 * Display the difference between 2 artifact versions
	 * 
	 * @param selectedArtifact
	 */
	private void openArtifactForDiff(Record selectedArtifact) {
		NavigatorResource selectedResource = getResource(selectedArtifact);
		
		String revisionId = selectedArtifact.getAttributeAsString("revisionId");

		Tab[] tabs = WebStudio.get().getEditorPanel().getTabs();
		for (Tab tab : tabs) {
			if (tab instanceof AbstractEditor) {
				if (selectedResource.equals(((AbstractEditor) tab).getSelectedResource()) && ((AbstractEditor) tab).isReadOnly() && revisionId != null && revisionId.equals(((AbstractEditor) tab).getRevisionId())) {
					WebStudio.get().showWorkSpacePage();
					WebStudio.get().getEditorPanel().selectTab(tab);						
					return;
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
	 * Updates status of all buttons and select items on this dialog.
	 */
	private void updateButtonStatuses() {
		if(worklistGrid.getSelectedRecords().length > 0 && action != null) {
			if (rmsMsgBundle.rmsWorklist_deleteSelected().equals(action.getValue())) {
				if (applyButton != null) applyButton.enable();
				if (delegateItem != null) delegateItem.disable();
				return;
			}
			else if (rmsMsgBundle.rmsWorklist_delegateSelected().equals(action.getValue()) && delegateItem != null) {
				delegateItem.enable();
				if (delegateItem.getValues().length > 0) {
					if (applyButton != null) applyButton.enable();
				}
				else {
					if (applyButton != null) applyButton.disable();
				}
				return;
			}
		}
		if (applyButton != null) applyButton.disable();
		if (delegateItem != null) delegateItem.disable();
	}
	
	private String globalizeDeleteSuccessMsg(String responseMessage) {
		if (responseMessage == null) {
			return "";
		}
		if ("No Revisions were deleted. Revisions having artifacts which are not yet approved cannot be deleted.".equals(responseMessage)) {
			responseMessage = rmsMsgBundle.servermessage_worklist_delete_notAllowed();
			return responseMessage;
		}
		RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*", "i");//Revision(s) [%s] successfully deleted.
		MatchResult matchResult = regexp.exec(responseMessage);
		if (matchResult != null && matchResult.getGroupCount() > 1) {
			String revisionIds = matchResult.getGroup(1);
			responseMessage = rmsMsgBundle.servermessage_worklist_delete_successful(revisionIds);
		}
		return responseMessage;
	}
	
	private String globalizeDelegateSuccessMsg(String responseMessage) {
		if (responseMessage == null) {
			return "";
		}
		RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*\\[(.*?)\\].*", "i");//Revisions [%s] successfully delegated to role [%s]
		MatchResult matchResult = regexp.exec(responseMessage);
		if (matchResult != null && matchResult.getGroupCount() > 2) {
			String revisionIds = matchResult.getGroup(1);
			String roles = matchResult.getGroup(2);
			responseMessage = rmsMsgBundle.servermessage_worklist_delegate_successful(revisionIds, roles);
		}
		return responseMessage;
	}
}
