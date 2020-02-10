package com.tibco.cep.webstudio.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.smartgwt.client.util.BooleanCallback;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
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
import com.tibco.cep.webstudio.client.portal.DASHBOARD_PORTLETS;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * Helper class to remove any selected artifacts
 * 
 * @author Vikram Patil
 */
public class ArtifactDeletionHelper implements HttpSuccessHandler, HttpFailureHandler {
	private static ArtifactDeletionHelper instance;
	private NavigatorResource[] artifactsToDelete;
	private static final String ERROR_CODE_DELETE_RESOURCE_ACCESS_DENIED = "ERR_1103";
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	/**
	 * Creating/fetching a singleton instance
	 * 
	 * @return
	 */
	public static ArtifactDeletionHelper getInstance() {
		if (instance == null) {
			instance = new ArtifactDeletionHelper();
		}
		return instance;
	}

	/**
	 * Private constructor to avoid public invocation
	 */
	private ArtifactDeletionHelper() {
	}

	/**
	 * Removes the selected artifacts i.e. artifacts/project itself
	 * 
	 * @param projectName
	 * @param artifactPath
	 */
	public void deleteArtifact(NavigatorResource[] artifactsToDelete) {
		this.artifactsToDelete = artifactsToDelete;
		final Map<String, Object> requestParameters = new HashMap<String, Object>();

		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, selectedProject);

		if (artifactsToDelete != null && artifactsToDelete.length > 0) {
			List<RequestParameter> selectedArtifacts = new ArrayList<RequestParameter>(artifactsToDelete.length);
			for (NavigatorResource resource : artifactsToDelete) {
				String artifact = resource.getId().replace("$", "/");

				if (!artifact.trim().equals(selectedProject.trim())) {
					if (artifact.indexOf("/") != -1) {
						artifact = artifact.substring(artifact.indexOf("/"));
					}
					if (artifact.indexOf(".") != -1) {
						artifact = artifact.substring(0, artifact.indexOf("."));
					}
					RequestParameter requestParameter = new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifact);
					requestParameter.add(RequestParameter.REQUEST_PARAM_TYPE, resource.getType());
					selectedArtifacts.add(requestParameter);
				}
			}
			requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, selectedArtifacts);

			String type = null;
			if (artifactsToDelete[0].getType().equals(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase())) {
				type = "Rule Template Instance";
			} else if (artifactsToDelete[0].getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase())) {
				type = "Decision Table";
			} else if (artifactsToDelete[0].getType().equals(ARTIFACT_TYPES.PROJECT.getValue().toLowerCase())) {
				type = "Project";
			} else {
				type = "Artifact";
			}
			
			String artifactName = artifactsToDelete[0].getName().indexOf(".") != -1 ? artifactsToDelete[0].getName().substring(0, artifactsToDelete[0].getName().indexOf(".")) : artifactsToDelete[0].getName();
			CustomSC.confirm(globalMsgBundle.text_confirm(), globalMsgBundle.message_confirm_deleteArtifact(type, artifactName), new BooleanCallback() {
				public void execute(Boolean value) {
					if (value != null && value) {
						deleteFromServer(requestParameters);
						// disable delete to make sure its not clicked again
						WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID, true);
					} else {
						WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_DELETE_ID);
					}
				}
			});
		}
	}

	/**
	 * Delete the artifact from the server
	 * @param requestParameters
	 */
	private void deleteFromServer(Map<String, Object> requestParameters) {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_DELETE_ARTIFACT, requestParameters);

		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.DELETE);
		request.setPostData(xmlData);

		request.submit(ServerEndpoints.RMS_DELETE_ARTIFACT.getURL());
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DELETE_ARTIFACT.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);

			if (artifactsToDelete != null && artifactsToDelete.length > 0) {
				WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow()
						.getArtifactTreeGrid();

				for (NavigatorResource resource : artifactsToDelete) {
					navGrid.removeRecord(resource.getId(), false);
					WebStudio.get().getPortalPage().removeArtifactFromGroupPortlet(resource.getId());
					
					ProjectExplorerUtil.removeArtifactFromEditor(resource);
					removeDashboardArtifacts(resource);
					ContentModelManager.getInstance().removeArtifactFromGroups(resource);
				}
				
				if (WebStudio.get().getEditorPanel().getTabs().length == 0) {
					WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_EXPORT_ID, true);
					WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_IMPORT_ID, true);
					WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
				}
				
				//disable toolbar options on deletion
				ProjectExplorerUtil.toggleToolbarOptions(null);

				// clear artifacts once done
				artifactsToDelete = null;
			}
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DELETE_ARTIFACT.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);
			
			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			String errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			if (ERROR_CODE_DELETE_RESOURCE_ACCESS_DENIED.equals(errorCode)) {      //Access denied to delete project resources.
				String projectName = responseMessage.substring(responseMessage.indexOf(":")+1, responseMessage.indexOf("No resources were deleted.")-2);
					responseMessage = rmsMsgBundle.servermessage_resourceDelete_denied(projectName);
				
			}
			CustomSC.say(responseMessage);
		}
	}
	
	
	/**
	 * Removes the deleted artifacts from the dashboard portlet.
	 * 
	 * @param resource
	 */
	private void removeDashboardArtifacts(NavigatorResource resource) {
		String artifactPath = resource.getId();
		
		String projectName, path;
		if (artifactPath.indexOf("$") == -1) {
			WebStudio.get().getPortalPage().removeFromAvailableProjectsPortlet(artifactPath);
			projectName = path = artifactPath;
		} else {
			projectName = artifactPath.substring(0, artifactPath.indexOf("$"));
			if (artifactPath.indexOf(".") != -1) {
				path = artifactPath.substring(artifactPath.indexOf("$"), artifactPath.indexOf(".")).replace("$", "/");
			} else {
				path = artifactPath.substring(artifactPath.indexOf("$")).replace("$", "/");
			}
		}

		WebStudio.get().getPortalPage().removeFromDashboardArtifactPortlet(projectName, path, DASHBOARD_PORTLETS.RECENTLY_OPENED);
	}
}
