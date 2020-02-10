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
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
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
import com.tibco.cep.webstudio.client.datasources.AbstractRestDS;
import com.tibco.cep.webstudio.client.datasources.RMSGetArtifactDS;
import com.tibco.cep.webstudio.client.datasources.RMSManagedProjectDS;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.groups.ProjectsContentGroup;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DisplayPropertiesManager;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
/**
 * Dialog for checking out a project managed under one of the configured RMS Server.
 * 
 * @author Vikram Patil
 */
public class RMSCheckoutDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {
	
	private SelectItem projectList;
	private ListGrid artifactGrid;
	
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	protected GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private boolean backToDashboard;
	
	private List<ArtifactDetail> artifactListForCheckout;

	/**
	 * Constructor to be used to create Update Dialog
	 * 
	 * @param selectedProject
	 * @param isUpdate
	 */
	public RMSCheckoutDialog() {
		setDialogWidth(710);
		setDialogHeight(355);
		
		setDialogTitle();
		
		initialize();
	}
	
	protected void setDialogTitle() {
		String title = rmsMsgBundle.rmsCheckout_title();
		setDialogTitle(title);
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "checkout" + WebStudioMenubar.ICON_SUFFIX);
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		
		widgetList.add(createCheckoutForm());
		widgetList.add(createArtifactGrid());
		
		return widgetList;
	}
	
	/**
	 * Create form for configuring RMS server and managed projects
	 * @return
	 */
	private HLayout createCheckoutForm() {
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
		projectList.setOptionDataSource(RMSManagedProjectDS.getInstance());
		projectList.setEmptyPickListMessage(globalMsgBundle.message_noData());
        projectList.setWidth("100%");
        projectList.setAutoFetchData(true);
	    projectList.addChangedHandler(new ChangedHandler() {
	    	@Override
	    	public void onChanged(ChangedEvent event) {
	    		
	    		projectList.getValueAsString();
	    		
	    		setupDataSourceForrProjectArtifacts();
	    		
				cleanupCache(artifactGrid);
				artifactGrid.fetchData(new Criteria(), new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							if(response.getTotalRows() == 0) {
								CustomSC.say(rmsMsgBundle.message_allArtifactsCheckedout());
							}
							artifactGrid.selectAllRecords();
						}
					}
				});
				
	    		if (artifactGrid.getSelectedRecords().length > 0) {
	    			okButton.enable();
	    		} else {
	    			okButton.disable();
	    		}
	    	}
	    });

	    form.setItems(new FormItem[] {projectList});
		header.addMember(form);		
	    return header;
	}
	
	protected void setupDataSourceForrProjectArtifacts() {
		RMSGetArtifactDS.getInstance().clearRequestParameters();
		RMSGetArtifactDS.getInstance().setAdditionalURLPath("projects/" + projectList.getValueAsString());
		RMSGetArtifactDS.getInstance().setHttpMethod(HttpMethod.GET);
	}
	
	/**
	 * Create a artifact grid, listing all artifacts associated to a selected project to be checked out
	 * @return
	 */
	private ListGrid createArtifactGrid() {
		artifactGrid = new ListGrid();
		artifactGrid.setWidth100(); 
		artifactGrid.setHeight(224);  
		artifactGrid.setShowAllRecords(true);  
		artifactGrid.setShowEmptyMessage(true);
		artifactGrid.setDataSource(getDataSourceForProjectArtifacts());
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
					okButton.enable();
				} else {
					okButton.disable();
				}
			}
		});

		return artifactGrid;		
	}
	
	protected AbstractRestDS getDataSourceForProjectArtifacts() {
		return RMSGetArtifactDS.getInstance();
	}
	
	@Override
	public void onAction() {
		// set the currently selected project and rms server
		WebStudio.get().setCurrentlySelectedProject(projectList.getValueAsString());

		if (artifactGrid.getSelectedRecords().length == 0) {
			CustomSC.say(rmsMsgBundle.rmsCheckout_selectRecord_message());
		} else {
			// Load display properties if any related to this project
			DisplayPropertiesManager.getInstance().getDisplayProperties(projectList.getValueAsString());

			artifactListForCheckout = new ArrayList<ArtifactDetail>();

			ArtifactDetail artifactDetail = null;
			String processType = null;
			for (ListGridRecord record : artifactGrid.getSelectedRecords()) {
				artifactDetail = new ArtifactDetail(record.getAttributeAsString("artifactPath") + "." + record.getAttributeAsString("fileExtension"),
						record.getAttributeAsString("baseArtifactPath"));
				processType = record.getAttributeAsString("processType");
				if(null!=processType && !processType.trim().isEmpty()){
					artifactDetail.setProcessType(processType);
				}
				artifactListForCheckout.add(artifactDetail);
			}
		}

		Map<String, Object> requestParameters = new HashMap<String,Object>();

		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, WebStudio.get().getCurrentlySelectedProject());
		requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(artifactGrid.getSelectedRecords(), false));

		@SuppressWarnings("rawtypes")
		String xmlData = new SerializeArtifact().generateXML(getServerEndpoint(), requestParameters);

		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.POST);
		request.setPostData(xmlData);
		request.submit(getServerEndpoint().getURL("projects", projectList.getValueAsString()));
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(getServerEndpoint().getURL("projects", projectList.getValueAsString())) != -1) {
			
			NavigatorResource[] projectResources = ProjectExplorerUtil.createProjectResources(artifactListForCheckout, projectList.getValueAsString());
			String projName = projectList.getValueAsString();
			ProjectsContentGroup projGroup = ContentModelManager.getInstance().getProjectsGroup();
			projGroup.setProjectResources(projName, projectResources);

			WebStudio.get().getWorkspacePage().selectDefaultGroups();
			WebStudio.get().getPortalPage().addToAvailableProjectsPortlet(projectList.getValueAsString());

			ArtifactUtil.removeHandlers(this);
			
			destroy();
		}

	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(getServerEndpoint().getURL("projects", projectList.getValueAsString())) != -1) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@Override
	protected void doCancel() {
		super.doCancel();
		
		if (isBackToDashboard()) {
			WebStudio.get().showDashboardPage();
		}
	}

	/**
	 * Get whether to move back to dashboard
	 * 
	 * @return
	 */
	public boolean isBackToDashboard() {
		return backToDashboard;
	}

	/**
	 * Set if onCancel user needs to move back to dashboard
	 * 
	 * @param backToDashboard
	 */
	public void setBackToDashboard(boolean backToDashboard) {
		this.backToDashboard = backToDashboard;
	}
	
	protected ServerEndpoints getServerEndpoint() {
		return ServerEndpoints.RMS_POST_PROJECT_CHECKOUT;
	}
	
	public String getSelectedProject() {
		return projectList.getValueAsString();
	}
}
