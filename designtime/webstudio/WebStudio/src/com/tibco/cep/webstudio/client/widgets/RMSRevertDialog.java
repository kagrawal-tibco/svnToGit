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
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSCommitDS;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
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
 * Dialog for reverting a project artifacts managed under one of the configured RMS Server. 
 * 
 * @author Vikram Patil
 */
public class RMSRevertDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {

	private ListGrid artifactGrid;
	private String[] selectedArtifacts;
	private Label revisionLabel;
	private ListGridRecord[] revertedRecords; 
	
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Default Constructor
	 */
	public RMSRevertDialog(String[] artifactPaths) {
		this.selectedArtifacts = artifactPaths;
		
		setDialogWidth(702);
		setDialogHeight(235);
		setDialogTitle(rmsMsgBundle.rmsRevert_title());
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "revert" + WebStudioMenubar.ICON_SUFFIX);
		
		initialize();
		
		getArtifactsToRevert();
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		
		Label dialogTitle = new Label(rmsMsgBundle.rmsRevert_selectRecord_message());
		dialogTitle.setHeight(10);

		widgetList.add(dialogTitle);
		widgetList.add(createArtifactGrid());

		return widgetList;
	}
	
	/**
	 * Create a artifact grid, listing all artifacts associated to a selected project to be reverted
	 * @return
	 */
	private  ListGrid createArtifactGrid() {
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
		artifactGrid.setHeight(204);  
		artifactGrid.setShowAllRecords(true);  
		artifactGrid.setShowEmptyMessage(true);
		artifactGrid.setDataSource(RMSCommitDS.getInstance());	
		artifactGrid.setAutoFetchData(false);
		artifactGrid.setSelectionType(SelectionStyle.SIMPLE);  
		artifactGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		artifactGrid.setEmptyMessage(globalMsgBundle.message_noData());
		artifactGrid.setShowHeaderContextMenu(false);
		artifactGrid.setShowHeaderMenuButton(false);
		artifactGrid.setSaveLocally(true);
		
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
	
	/**
	 * Creates a label for associated commit revision
	 * 
	 * @return
	 */
	@Override
	protected void addCustomWidget(HLayout container) {
		revisionLabel = new Label();
		revisionLabel.setWidth100();
		revisionLabel.setHeight100();
		revisionLabel.setStyleName("ws-commit-revision-message");
		
		container.addMember(revisionLabel);
	}
	
	/**
	 * Fetch artifacts to revert
	 */
	private void getArtifactsToRevert() {
		RMSCommitDS.getInstance().clearRequestParameters();
		RMSCommitDS.getInstance().setHttpMethod(HttpMethod.POST);

		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		RMSCommitDS.getInstance().setAdditionalURLPath("projects/" + selectedProject);
		RMSCommitDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, selectedProject));
		RMSCommitDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_OPERATION_NAME, "Revert"));
		
		if (selectedArtifacts != null) {
			List<ListGridRecord> listOfRecords = new ArrayList<ListGridRecord>();
			
			for (String artifact : selectedArtifacts) {
				if (!artifact.trim().equals(selectedProject.trim())) {
					artifact = artifact.replace("$", "/");
					if (artifact.indexOf("/") != -1) {
						artifact = artifact.substring(artifact.indexOf("/"));
					}
					if (artifact.indexOf(".") != -1) {
						artifact = artifact.substring(0, artifact.indexOf("."));
					}

					ListGridRecord record = new ListGridRecord();
					record.setAttribute("artifactPath", artifact);
					
					listOfRecords.add(record);
				}
			}

			RMSCommitDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(listOfRecords.toArray(new ListGridRecord[listOfRecords.size()]), false)));			
		}
		
		cleanupCache(artifactGrid);
		artifactGrid.fetchData();
	}

	@Override
	public void onAction() {
		if (artifactGrid.getSelectedRecords().length == 0) {
			CustomSC.say(rmsMsgBundle.rmsCommit_selectRecord_message());
		} else {
			Map<String, Object> requestParameters = new HashMap<String,Object>();

			requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, WebStudio.get().getCurrentlySelectedProject());
			requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(artifactGrid.getSelectedRecords(), true));

			@SuppressWarnings("rawtypes")
			String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_PROJECT_REVERT_ARTIFACTS, requestParameters);

			ArtifactUtil.addHandlers(this);

			HttpRequest request = new HttpRequest();
			request.setMethod(HttpMethod.POST);
			request.setPostData(xmlData);

			request.submit(ServerEndpoints.RMS_POST_PROJECT_REVERT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject()));

			okButton.disable();
			cancelButton.setTitle(globalMsgBundle.button_finish());
		}
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_PROJECT_REVERT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			String selectedProject = WebStudio.get().getCurrentlySelectedProject();
			SelectItem projectList = new SelectItem();
			projectList.setValue(selectedProject);
			ProjectExplorerUtil.updateProjectExplorerTree(artifactGrid.getSelectedRecords(), projectList.getValueAsString());
			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("responseMessage").item(0).getFirstChild().getNodeValue();
			responseMessage = globalizeRevertSuccessMsg(responseMessage);
			revisionLabel.setContents(responseMessage);

			revertedRecords = artifactGrid.getSelectedRecords();
			artifactGrid.deselectAllRecords();
			for (ListGridRecord record : revertedRecords) {
				record.setEnabled(false);
			}

			ArtifactUtil.removeHandlers(this);
		}
	}
	
	private String globalizeRevertSuccessMsg(String responseMessage) {
		if (responseMessage == null) {
			return "";
		}
		return rmsMsgBundle.servermessage_revert_successful();
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_PROJECT_REVERT_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@Override
	public void preDestroy() {
		closeRevertedArtifacts();
	}
	
	/**
	 * Close the artifacts selected to be reverted
	 */
	private void closeRevertedArtifacts() {
		if (revertedRecords != null) {
			for (ListGridRecord gridRecord : revertedRecords) {
				NavigatorResource selectedResource = getResource(gridRecord);
				
				if (selectedResource.getType().equals("ruletemplateinstance") || selectedResource.getType().equals("rulefunctionimpl")) {
					Tab[] tabs = WebStudio.get().getEditorPanel().getTabs();
					for (Tab tab : tabs) {
						if (tab instanceof AbstractEditor) {
							if (selectedResource.equals(((AbstractEditor) tab).getSelectedResource())) {
								WebStudio.get().getEditorPanel().removeTab(tab);						
								break;
							}
						}
					}
				}
			}
		}	
		revertedRecords = null;
	}
	
	/**
	 * Create the resource for the selected Artifact
	 * 
	 * @param selectedArtifact
	 * @return
	 */
	private NavigatorResource getResource(ListGridRecord selectedArtifact) {
		String projectName = WebStudio.get().getCurrentlySelectedProject();
		String artifactPath = projectName + selectedArtifact.getAttributeAsString("artifactPath") + "."
				+ selectedArtifact.getAttributeAsString("artifactType");

		String artifactId = artifactPath.replace("/", "$");
		String artifactName = artifactId.substring(artifactId.lastIndexOf("$")+1, artifactId.length());
		String artifactParent = artifactId.substring(0, artifactId.lastIndexOf("$"));
		
		NavigatorResource resource = ProjectExplorerUtil.createProjectResource(artifactName, artifactParent, selectedArtifact.getAttributeAsString("artifactType"), artifactId, false);
		
		return resource;
	}
}
