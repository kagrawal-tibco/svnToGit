/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
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
import com.tibco.cep.webstudio.client.datasources.RMSUpdateDS;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.handler.IEditorPostCloseHandler;
import com.tibco.cep.webstudio.client.handler.IEditorPostSaveHandler;
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
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog for updating a project managed under the configured WS Server.
 * 
 * @author Vikram Patil
 */
public class RMSUpdateDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler, MinimizeClickHandler, RestoreClickHandler {

	private SelectItem projectList;
	private ListGrid artifactGrid;
	private String[] selectedArtifacts;
	private List<Tab> openArtifactEditorTabs;
	
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	/**
	 * Constructor to be used to create Update Dialog
	 * 
	 * @param selectedArtifacts
	 */
	public RMSUpdateDialog(String[] selectedArtifacts) {
		this.selectedArtifacts = selectedArtifacts;
		String title = rmsMsgBundle.rmsUpdate_title();
				
		setDialogWidth(710);
		setDialogHeight(355);
		setDialogTitle(title);		
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "synchronize" + WebStudioMenubar.ICON_SUFFIX);
		
		initialize();
		
		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		projectList.setValue(selectedProject);

		getUpdatableArtifacts();
		setIsModal(false);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#getWidgetList()
	 */
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		
		widgetList.add(createUpdateForm());
		widgetList.add(createArtifactGrid());
		
		return widgetList;
	}
	
	/**
	 * Create form for configuring managed projects
	 * @return
	 */
	private HLayout createUpdateForm() {
		HLayout header = new HLayout(5);
		header.setWidth100();
		header.setLayoutMargin(5);
		header.setBorder("1px solid grey");
		header.setBackgroundColor("#e8e8e8");
		
		VLayout formContainer = new VLayout(10);
		formContainer.setWidth100();
		formContainer.setLeft(0);
				
		final DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setNumCols(2);
		form.setColWidths("12%", "*");
					 
		projectList = new SelectItem("projectName", rmsMsgBundle.rmsCheckout_projectList());
        projectList.setWidth("100%");
        projectList.setCanEdit(false);

	    form.setItems(new FormItem[] {projectList});
		header.addMember(form);		
		
	    return header;
	}
	
	/**
	 * Create a artifact grid, listing all artifacts associated to a selected project to be updated
	 * @return
	 */
	private ListGrid createArtifactGrid() {
		artifactGrid = new ListGrid(){
			@Override
			protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				String changeType = record.getAttributeAsString("changeType");
				String cellColor = getCellColorByChangeType(changeType);
				if (cellColor != null) {
					return cellColor;
				} else {
					return super.getCellCSSText(record, rowNum, colNum);
				}
			}
		};
		artifactGrid.setWidth100(); 
		artifactGrid.setHeight(224);  
		artifactGrid.setShowAllRecords(true);  
		artifactGrid.setShowEmptyMessage(true);
		artifactGrid.setDataSource(RMSUpdateDS.getInstance());
		artifactGrid.setAutoSaveEdits(false);
		artifactGrid.setCanEdit(true);
		artifactGrid.setEditByCell(true);
		artifactGrid.setAutoFetchData(false);
		artifactGrid.setSelectionType(SelectionStyle.SIMPLE);  
		artifactGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		artifactGrid.setEmptyMessage(globalMsgBundle.message_noData());
		artifactGrid.setShowHeaderContextMenu(false);
		artifactGrid.setShowHeaderMenuButton(false);
		
		artifactGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				if (artifactGrid.getSelectedRecords().length > 0) {
					okButton.enable();
				}
			}
		});
		
		artifactGrid.addRowContextClickHandler(new RowContextClickHandler() {
			@Override
			public void onRowContextClick(RowContextClickEvent event) {
				ListGridRecord selectedRecord = event.getRecord();
				if (selectedRecord != null) {
					String changeType = selectedRecord.getAttributeAsString("operationType");
					if ("Modified".equalsIgnoreCase(changeType)) {
						final String artifactPath = selectedRecord.getAttributeAsString("artifactPath");
						final String artifactFileExtn = selectedRecord.getAttributeAsString("fileExtension");
						final String artifactType = selectedRecord.getAttributeAsString("artifactType");
						
						Menu contextMenu = new Menu();
						MenuItem diffMenuItem = new MenuItem(globalMsgBundle.menu_rmsShowDiff(), WebStudioMenubar.ICON_PREFIX + "diff.png");
						diffMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
							@Override
							public void onClick(MenuItemClickEvent event) {
								if (artifactType.equals("ruletemplateinstance") || artifactType.equals("rulefunctionimpl")) {
									RMSUpdateDialog.this.minimize();
									setMinimizeCoordinates();								
									// Open artifact diff
									openArtifactForDiff(projectList.getValueAsString(), artifactPath, artifactFileExtn, false);
								} else {
									CustomSC.warn(globalMsgBundle.explorer_DiffResource_error());
								}
							}
						});
						contextMenu.addItem(diffMenuItem);
						artifactGrid.setContextMenu(contextMenu);
						contextMenu.show();
					}
					else {
						artifactGrid.setContextMenu(null);
						event.cancel();
					}
				}
			}
		});
		
		artifactGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record selectedRecord = event.getRecord();
				if (selectedRecord != null) {
					Boolean hasConflict = selectedRecord.getAttributeAsBoolean("hasConflict");
					String artifactType = selectedRecord.getAttributeAsString("artifactType");
					if ((artifactType.equals("ruletemplateinstance") || artifactType.equals("rulefunctionimpl"))) {
						if (Boolean.TRUE.equals(hasConflict)) {
							String artifactPath = selectedRecord.getAttributeAsString("artifactPath");
							String artifactFileExtn = selectedRecord.getAttributeAsString("fileExtension");
							RMSUpdateDialog.this.minimize();
							setMinimizeCoordinates();
							openArtifactForDiff(projectList.getValueAsString(), artifactPath, artifactFileExtn,
									"rulefunctionimpl".equalsIgnoreCase(artifactFileExtn) || "ruletemplateinstance".equalsIgnoreCase(artifactFileExtn));
						}	
					} else {
						CustomSC.warn(globalMsgBundle.explorer_DiffResource_error());
					}
				}	
			}
		});
				
		return artifactGrid;		
	}
	
	/**
	 * Create a disclaimer Text
	 * @return
	 */
	@Override
	protected void addCustomWidget(HLayout container) {
		HLayout disclaimerContainer = new HLayout(2);
		disclaimerContainer.setWidth100();
		disclaimerContainer.setHeight100();
		
		Label noteText = new Label();
		noteText.setWrap(false);
		noteText.setWidth("5%");
		noteText.setHeight100();
		noteText.setStyleName("ws-note-message");
		noteText.setContents(globalMsgBundle.text_note() + ": ");
		disclaimerContainer.addMember(noteText);
		
		Label disclaimerText = new Label();
		disclaimerText.setWidth("95%");
		disclaimerText.setHeight100();
		disclaimerText.setContents(rmsMsgBundle.rmsUpdate_overwriteNote());
		disclaimerContainer.addMember(disclaimerText);
		
		container.addMember(disclaimerContainer);
	}
	
	/**
	 * Fetch updatable artifacts
	 */
	private void getUpdatableArtifacts() {
		RMSUpdateDS.getInstance().clearRequestParameters();
		RMSUpdateDS.getInstance().setHttpMethod(HttpMethod.POST);
		
		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		RMSUpdateDS.getInstance().setAdditionalURLPath("projects/" + selectedProject);
		RMSUpdateDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectList.getValueAsString()));
		
		if (selectedArtifacts != null) {
			List<ListGridRecord> listOfRecords = new ArrayList<ListGridRecord>();
			
			String type = null;
			for (String artifact : selectedArtifacts) {
				if (!artifact.trim().equals(projectList.getValueAsString().trim())) {
					artifact = artifact.replace("$", "/");
					if (artifact.indexOf("/") != -1) {
						artifact = artifact.substring(artifact.indexOf("/"));
					}
					if (artifact.indexOf(".") != -1) {
						type = artifact.substring(artifact.indexOf(".") + 1, artifact.length());
						artifact = artifact.substring(0, artifact.indexOf("."));
					}

					ListGridRecord record = new ListGridRecord();
					record.setAttribute(RequestParameter.REQUEST_PARAM_PATH, artifact);
					record.setAttribute(RequestParameter.REQUEST_PARAM_TYPE, type);
					
					listOfRecords.add(record);
				}
			}
			
			RMSUpdateDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(listOfRecords.toArray(new ListGridRecord[listOfRecords.size()]), false)));
		}
		
		artifactGrid.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (artifactGrid.getRecords().length > 0) {
					artifactGrid.selectAllRecords();
					ListGridRecord[] artifactRecords = artifactGrid.getRecords();
					for (int i = 0; i < artifactRecords.length; i++) {
						Boolean hasConflict = artifactRecords[i].getAttributeAsBoolean("hasConflict");
						if (Boolean.TRUE.equals(hasConflict)) {
							artifactRecords[i].set_baseStyle("ws-dt-diff-remove-apply-style");
						}
					}	
				}
			}
		});
		
		cleanupCache(artifactGrid);
		artifactGrid.fetchData();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog#onAction()
	 */
	@Override
	public void onAction() {
		if (artifactGrid.getSelectedRecords().length == 0) {
			CustomSC.say(rmsMsgBundle.rmsUpdate_selectRecord_message());
		} else {			
			Map<String, Object> requestParameters = new HashMap<String,Object>();
			
			//Send the selected data to the server
			requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectList.getValueAsString());
			requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(artifactGrid.getSelectedRecords(), true));
			
			@SuppressWarnings("unchecked")
			String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS, requestParameters);
			
			ArtifactUtil.addHandlers(this);
			
			HttpRequest request = new HttpRequest();
			request.setMethod(HttpMethod.POST);
			request.setPostData(xmlData);
			
			request.submit(ServerEndpoints.RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject()));
			
			okButton.disable();
		}
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			ProjectExplorerUtil.updateProjectExplorerTree(artifactGrid.getSelectedRecords(), projectList.getValueAsString());
			
			ArtifactUtil.removeHandlers(this);
			this.destroy();
		}
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_SYNCRONIZE_PROJECT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Display the difference between the local copy and server copy.
	 * 
	 * @param selectedArtifact
	 */
	private void openArtifactForDiff(final String project, final String artifactPath, final String artifactextn, final boolean isMerge) {
		String completeArtifactPath = project + artifactPath + "." + artifactextn; 
		NavigatorResource selectedResource = getResource(completeArtifactPath);
		String tabTitle = selectedResource.getName().substring(0, selectedResource.getName().indexOf('.')) + " [Sync Diff]";
		Tab[] openTabs = WebStudio.get().getEditorPanel().getTabs();
		for (Tab tab : openTabs) {
			if (tab instanceof AbstractEditor) {
				NavigatorResource resourceInEditor = ((AbstractEditor) tab).getSelectedResource();
				if (resourceInEditor.getId().equalsIgnoreCase(selectedResource.getId()) 
																|| resourceInEditor.getId().startsWith(selectedResource.getId())) {
					if (((AbstractEditor) tab).isMerge() == isMerge) {
						WebStudio.get().showWorkSpacePage();
						WebStudio.get().getEditorPanel().selectTab(tab);
						return;
					}	
				}
			}
		}
		if (isMerge) {
			ProjectExplorerUtil.removeArtifactFromEditor(selectedResource); //remove all other editors for the resource
		}
		
		IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(selectedResource);
		List<RequestParameter> additionalParams = new ArrayList<RequestParameter>();
		additionalParams.add(new RequestParameter(RequestParameter.REQUEST_PARAM_DIFFMODE, "1"));
		AbstractEditor editor = null;
		if (editorFactory instanceof RuleTemplateInstanceEditorFactory) {
			RuleTemplateInstanceEditorFactory rtiEditorFactory = (RuleTemplateInstanceEditorFactory) editorFactory;
			RuleTemplateInstanceEditor rtiEditor = (RuleTemplateInstanceEditor) rtiEditorFactory.createEditor(selectedResource, true, null, true, additionalParams);				
			editor = rtiEditor;
			editor.setTitle(tabTitle);
		} else if (editorFactory instanceof DecisionTableEditorFactory) {
			DecisionTableEditorFactory dtEditorFactory = (DecisionTableEditorFactory) editorFactory;
			final DecisionTableEditor dtEditor = (DecisionTableEditor) dtEditorFactory.createEditor(selectedResource, true, null, true, true, isMerge, additionalParams);
			editor = dtEditor;
			if (isMerge) {
				dtEditor.makeDirty(true);
			}	
		}
		if (editor != null) {
			if (isMerge) {
				editor.makeDirty();
				ListGridRecord selectedRecord = null;	
				ListGridRecord[] records = artifactGrid.getRecords();
				for (int i = 0; i < records.length ; i++) {
					if (artifactPath.equals(records[i].getAttribute("artifactPath"))) {
						selectedRecord = records[i];
						break;
					}
				}
				final ListGridRecord selectedRecord2 = selectedRecord;
				if (selectedRecord2 != null) {
					Boolean updated = selectedRecord2.getAttributeAsBoolean("updated");
					editor.setSyncMerge(!updated);
					artifactGrid.deselectRecord(selectedRecord2);
					selectedRecord2.setEnabled(false);
				
					editor.addEditorPostSaveHandler(new IEditorPostSaveHandler() {
						@Override
						public void postSave() {
							selectedRecord2.setAttribute("updated", Boolean.TRUE);
							selectedRecord2.setEnabled(false);
						}						
					});
				
					final AbstractEditor editor2 = editor;
					editor.addEditorPostCloseHandler(new IEditorPostCloseHandler() {						
						@Override
						public void onClose() {
							if (editor2.isDirty()) {
								Boolean updated = selectedRecord2.getAttributeAsBoolean("updated");
								if (!Boolean.TRUE.equals(updated)) {
									selectedRecord2.setEnabled(true);
								}	
							}
							if (openArtifactEditorTabs != null) {
								openArtifactEditorTabs.remove(editor2);
							}	
						}
					});
				}
			} else {
				editor.setTitle(tabTitle);					
			}				
//				editor.setIcon(ICON_PREFIX + "diff.png");
			WebStudio.get().showWorkSpacePage();
			WebStudio.get().getEditorPanel().addTab(editor);
			WebStudio.get().getEditorPanel().selectTab(editor);

			//add to the list of open editors
			if (openArtifactEditorTabs == null) {
				openArtifactEditorTabs = new ArrayList<Tab>();
			}
			openArtifactEditorTabs.add(editor);									
		}
	}
	
	/**
	 * Set appropriate coordinates for minimizing the worklist dialog
	 */
	private void setMinimizeCoordinates() {
		int top = WebStudio.get().getEditorPanel().getAbsoluteTop() + WebStudio.get().getEditorPanel().getHeight();
//		top -= RMSUpdateDialog.this.getMinimizeHeight();
		RMSUpdateDialog.this.setLeft(0);
		RMSUpdateDialog.this.setTop(top);
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

	@Override
	public void onRestoreClick(RestoreClickEvent event) {
		this.setAutoCenter(true);
		this.centerInPage();
	}

	@Override
	public void onMinimizeClick(MinimizeClickEvent event) {
		setMinimizeCoordinates();
	}

	@Override	
	public void preDestroy() {
		if (openArtifactEditorTabs != null) {
			for (int i = 0; i < openArtifactEditorTabs.size(); i++) {
				Tab openArtifactEditorTab = openArtifactEditorTabs.get(i); 
				((AbstractEditor)openArtifactEditorTab).close();
				WebStudio.get().getEditorPanel().removeTab(openArtifactEditorTab);			
			}
		}
	}
}
