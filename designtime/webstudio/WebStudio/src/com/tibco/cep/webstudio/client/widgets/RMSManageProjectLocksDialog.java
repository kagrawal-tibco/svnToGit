package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSProjectArtifactsLockInfoDS;
import com.tibco.cep.webstudio.client.datasources.RMSLockMgmtProjectListDS;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog to manage locks for a Project.
 * @author vdhumal
 *
 */
public class RMSManageProjectLocksDialog extends AbstractWebStudioDialog
		implements HttpSuccessHandler, HttpFailureHandler,
		MinimizeClickHandler, RestoreClickHandler {

	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	private SelectItem projectList;
	private ListGrid artifactGrid;
	protected IButton unLockButton;
	/**
	 * Create Project Artifacts Locks Dialog
	 * 
	 */
	public RMSManageProjectLocksDialog() {
		String title = rmsMsgBundle.rmsManageLocks_title();
		
		setDialogWidth(710);
		setDialogHeight(355);
		setDialogTitle(title);		
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "lockedstate.gif");
		setShowOkButton(false);
		
		initialize();
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

	/**
	 * Set appropriate coordinates for minimizing the dialog
	 */
	private void setMinimizeCoordinates() {
		int top = WebStudio.get().getEditorPanel().getAbsoluteTop() + WebStudio.get().getEditorPanel().getHeight();
		RMSManageProjectLocksDialog.this.setLeft(0);
		RMSManageProjectLocksDialog.this.setTop(top);
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_UNLOCK_ARTIFACT.getURL()) != -1) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);			
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_UNLOCK_ARTIFACT.getURL()) != -1) {		
			ArtifactUtil.removeHandlers(this);
			this.destroy();
		}
	}

	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();		
		widgetList.add(createManageArtifactLocksForm());
		widgetList.add(createManageArtifactLocksGrid());		
		return widgetList;
	}

	protected void addCustomWidget(HLayout container) {
		unLockButton = new IButton(rmsMsgBundle.button_UnLock());
		unLockButton.setWidth(100);  
		unLockButton.setShowRollOver(true);  
		unLockButton.setShowDisabled(true);  
		unLockButton.setShowDown(true);
		unLockButton.disable();
		unLockButton.setAlign(Alignment.CENTER);
		unLockButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onAction();
			}
		});
		container.addMember(unLockButton);
	}

	/**
	 * Create form for configuring RMS server and managed projects
	 * @return
	 */
	private HLayout createManageArtifactLocksForm() {
		HLayout header = new HLayout(5);
		header.setWidth100();
		header.setLayoutMargin(5);
		header.setBorder("1px solid grey");
		header.setBackgroundColor("#e8e8e8");
		
		VLayout formContainer = new VLayout(10);
		formContainer.setWidth("85%");
		formContainer.setLeft(0);
				
		final DynamicForm form = new DynamicForm();
		form.setWidth100();
		form.setNumCols(2);
		form.setColWidths("12%", "*");
        
		projectList = new SelectItem("projectName", rmsMsgBundle.rmsCheckout_projectList());
		projectList.setOptionDataSource(RMSLockMgmtProjectListDS.getInstance());
        projectList.setWidth("100%");
        projectList.setAutoFetchData(true);
        projectList.setEmptyPickListMessage(globalMsgBundle.message_browseButton_emptyMessage());
	    projectList.addChangedHandler(new ChangedHandler() {
	    	@Override
	    	public void onChanged(ChangedEvent event) {
	    		
	    		projectList.getValueAsString();
	    		
	    		RMSProjectArtifactsLockInfoDS.getInstance().clearRequestParameters();
	    		RMSProjectArtifactsLockInfoDS.getInstance().setHttpMethod(HttpMethod.GET);
	    		RMSProjectArtifactsLockInfoDS.getInstance().setAdditionalURLPath("projects/" + projectList.getValueAsString());
				
				cleanupCache(artifactGrid);
				artifactGrid.fetchData(new Criteria(), new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getTotalRows() == 0) {
								CustomSC.say(rmsMsgBundle.message_noArtifactsLocked());
							}
						}
					}
				});
				
	    		if (artifactGrid.getSelectedRecords().length > 0) {
	    			unLockButton.enable();
	    		} else {
	    			unLockButton.disable();
	    		}
	    	}
	    });

	    form.setItems(new FormItem[] {projectList});
		header.addMember(form);		
	    return header;
	}
	
	/**
	 * Create a artifact grid, listing all artifacts locked associated to a selected project
	 * @return
	 */
	private ListGrid createManageArtifactLocksGrid() {
		artifactGrid = new ListGrid();
		artifactGrid.setWidth100(); 
		artifactGrid.setHeight(224);  
		artifactGrid.setShowAllRecords(true);  
		artifactGrid.setShowEmptyMessage(true);
		artifactGrid.setDataSource(RMSProjectArtifactsLockInfoDS.getInstance());
		artifactGrid.setAutoSaveEdits(false);
		artifactGrid.setAutoFetchData(false);
		artifactGrid.setSelectionType(SelectionStyle.SIMPLE);  
		artifactGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		artifactGrid.setEmptyMessage(globalMsgBundle.message_noData());
		artifactGrid.setShowHeaderContextMenu(false);
		artifactGrid.setShowHeaderMenuButton(false);
		artifactGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {
			public void onSelectionUpdated(SelectionUpdatedEvent event) {
				if (artifactGrid.getSelectedRecords().length > 0) {
					unLockButton.enable();
				} else {
					unLockButton.disable();
				}
			}
		});

		return artifactGrid;		
	}
	
	@Override
	public void onAction() {
		ListGridRecord[] artifactsToUnLock = artifactGrid.getSelectedRecords();
		if (artifactsToUnLock != null && artifactsToUnLock.length > 0) {
			List<RequestParameter> selectedArtifacts = new ArrayList<RequestParameter>(artifactsToUnLock.length);		
			Map<String, Object> requestParameters = new HashMap<String, Object>();
			requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectList.getValueAsString());
			requestParameters.put("actionForcibly", true);
			
			for (int i = 0; i < artifactsToUnLock.length; i++) {			
				RequestParameter requestParameter = new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactsToUnLock[i].getAttribute("artifactPath"));
				requestParameter.add(RequestParameter.REQUEST_PARAM_TYPE, artifactsToUnLock[i].getAttribute("artifactType"));
				requestParameter.add(RequestParameter.REQUEST_PARAM_FILE_EXTN, artifactsToUnLock[i].getAttribute("fileExtension"));
				requestParameter.add("lockRequestor", artifactsToUnLock[i].getAttribute("lockOwner"));
				selectedArtifacts.add(requestParameter);
				requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, selectedArtifacts);
			}
			
			String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_UNLOCK_ARTIFACT, requestParameters);
			ArtifactUtil.addHandlers(this);

			HttpRequest request = new HttpRequest();
			request.setMethod(HttpMethod.POST);
			request.setPostData(xmlData);
			
			request.submit(ServerEndpoints.RMS_UNLOCK_ARTIFACT.getURL());
		}	
	}
}
