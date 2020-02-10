package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.RuleTemplateInstanceNavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * 
 * @author moshaikh
 *
 */
public class ArtifactRenameHelper implements HttpSuccessHandler, HttpFailureHandler {
	
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(ArtifactRenameHelper.class.getName());
	private static ArtifactRenameHelper instance;
	private NavigatorResource artifactToRename;
	private TreeNode parentNode;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private HttpRequest request;
	private ValueCallback valueCallback = null;
	private String artifactNewName;//in the form <artifactName>.<extension>
	
	private String fqpOldArtifactName;
	private String fqpNewArtifactName;
	
	// maintain a list of artifacts renames old name/new name
	private Map<String, String> renameArtifactMap = new HashMap<String,String>();
	
	public static ArtifactRenameHelper getInstance() {
		if (instance == null) {
			instance = new ArtifactRenameHelper();
			instance.request = new HttpRequest();
		}
		return instance;
	}
	
	/**
	 * Renames an artifact. Deletes the original artifact and creates a new one with its contents,
	 * @param artifactToRename
	 */
	public void renameArtifact(NavigatorResource artifactToRename) {
		final WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow()
				.getArtifactTreeGrid();
		
		this.artifactToRename = artifactToRename;
		this.parentNode = navGrid.getTree().getParent(artifactToRename);
		final String artifactCurrentName = artifactToRename.getId().substring(artifactToRename.getId().lastIndexOf('$') + 1, artifactToRename.getId().lastIndexOf('.'));
		
		valueCallback = new ValueCallback() {
			@Override
			public void execute(String value) {
				if (value == null) {//Cancelled
					return;
				}
				
				String artifactType = ArtifactRenameHelper.this.artifactToRename.getType();
				if (!ArtifactUtil.isValidArtifactName(value = value.trim())) {
					CustomSC.askforValue(globalMsgBundle.dialog_renameartifact_title(), globalMsgBundle.createNew_resource_invalidResourceErrorMessage(value, artifactType), valueCallback);
					return;
				}
				
				String artifactExtention = ArtifactRenameHelper.this.artifactToRename.getId().substring(ArtifactRenameHelper.this.artifactToRename.getId().lastIndexOf(".") + 1);
				String artifactPath = ArtifactRenameHelper.this.artifactToRename.getId().replace("$", "/");
				artifactPath = artifactPath.substring(artifactPath.indexOf("/"), artifactPath.indexOf("."));
				if (ArtifactRenameHelper.this.isExistingResource(value, artifactExtention, ArtifactRenameHelper.this.parentNode)) {
					CustomSC.askforValue(artifactPath, globalMsgBundle.createNew_resource_existingResourceErrorMessage(artifactType), valueCallback);
					return;
				}
				String projectName = ArtifactRenameHelper.this.artifactToRename.getId().substring(0, ArtifactRenameHelper.this.artifactToRename.getId().indexOf('$'));
				String artifactNewPath =  artifactPath.substring(0, artifactPath.lastIndexOf("/") + 1) + value;
				
				String implementsPath = null;
				if (ArtifactRenameHelper.this.parentNode instanceof NavigatorResource) {
					NavigatorResource parentResource = (NavigatorResource) ArtifactRenameHelper.this.parentNode;
					implementsPath = parentResource.getId().replace("$", "/");
					if (implementsPath.indexOf(".") != -1) {
						implementsPath = implementsPath.substring(implementsPath.indexOf("/"), implementsPath.indexOf("."));
					}
				}
				
				artifactNewName = value + "." + artifactExtention;
				ArtifactRenameHelper.this.request.clearRequestParameters();
				ArtifactRenameHelper.this.request.setMethod(HttpMethod.PUT);
				ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
				ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
				ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, artifactExtention));
				ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, artifactExtention));
				ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_ARTIFACT_RENAMETO_PATH, artifactNewPath));
				if (implementsPath != null) {
					ArtifactRenameHelper.this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_IMPLEMENTSPATH, implementsPath));
				}
				
				ArtifactRenameHelper.this.addNavigatorHandlers();
				logger.info("Renaming artifact '" + artifactPath + "' to '" + artifactNewPath + "'");
				request.submit(ServerEndpoints.RMS_PUT_ARTIFACT_RENAME.getURL());
				valueCallback = null;
				
				ArtifactRenameHelper.this.fqpOldArtifactName = projectName + "/" + artifactPath;
				ArtifactRenameHelper.this.fqpNewArtifactName = projectName + "/"  + artifactNewPath;
			}
		};
		CustomSC.askforValue(globalMsgBundle.dialog_renameartifact_title(), globalMsgBundle.dialog_renameartifact_message(artifactCurrentName),	valueCallback);
	}
	
	/**
	 * Checks if a resource with same name already exists.
	 * @param resourceName
	 * @param resourceExtn
	 * @param parent
	 * @return
	 */
	private boolean isExistingResource(String resourceName, String resourceExtn, TreeNode parent) {
		String parentId = null;
		if (parent instanceof NavigatorResource) {
			NavigatorResource parentResource = (NavigatorResource) parent;
			parentId = parentResource.getId();
//			if (!parentResource.getType().equals("ruletemplateinstance") && !parentResource.getType().equals("rulefunction")) {
//				parentId = (parentResource.getId().indexOf("$") != -1) ? parentResource.getId().substring(0, parentResource.getId().lastIndexOf("$")) : parentResource.getId();
//			}
		}
		if (parentId == null) {
			parentId = this.artifactToRename.getId().substring(0, this.artifactToRename.getId().lastIndexOf("$"));
		}
		
		String resourceId = parentId + "$" + resourceName + "." + resourceExtn;
		
		WebStudioNavigatorGrid navGrid = null;
		if (WebStudio.get().getHeader().isDashboardPageSelected()) {
			navGrid = WebStudio.get().getPortalPage().getGroupContentWindowtFromGroupPortlet().getArtifactTreeGrid();
		} else {
			navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
		}
		if (navGrid.resourceNameExits(resourceId)) {
			return true;
		}
		return false;
	}
	
	public void addNavigatorHandlers() {
		addHandlers(this);
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		Element docElement = event.getData();
		String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
		if(errorCode.equals("ERR_1146")){
			responseMessage = globalMsgBundle.artifactRename_errorMessage();
		}
		else if(errorCode.equals("ERR_1103")){
			
			String projectName = responseMessage.substring(responseMessage.indexOf(":")+1, responseMessage.indexOf("No resources were deleted.")-2);
			responseMessage = globalMsgBundle.artifactRename_errorMessage() + rmsMsgBundle.servermessage_resourceDelete_denied(projectName);
		}
		CustomSC.warn(responseMessage);
		removeHandlers(this);
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		Element docElement = event.getData();
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_PUT_ARTIFACT_RENAME.getURL()) != -1) {
			NavigatorResource navigatorResource = null;
			if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=" + CommonIndexUtils.RULE_FN_IMPL_EXTENSION + "&") != -1) {
				if (this.parentNode instanceof NavigatorResource) {
					NavigatorResource parentResource = (NavigatorResource) this.parentNode;
					String Id = parentResource.getId().substring(0, parentResource.getId().lastIndexOf("$")) + "$" + artifactNewName;
					navigatorResource = new DecisionTableNavigatorResource(artifactNewName, parentResource.getId(), Id,
							ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue(), ProjectExplorerUtil.getIcon(CommonIndexUtils.RULE_FN_IMPL_EXTENSION),
							ARTIFACT_TYPES.RULEFUNCTIONIMPL);
				}
				
			} else if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=" + CommonIndexUtils.RULE_TEMPLATE_INSTANCE_EXTENSION + "&") != -1) {
				if (this.parentNode instanceof NavigatorResource) {
					NavigatorResource parentResource = (NavigatorResource) this.parentNode;
					String parentId = parentResource.getId();
					navigatorResource = new RuleTemplateInstanceNavigatorResource(artifactNewName, parentId,
							parentResource.getId().substring(0, parentResource.getId().lastIndexOf("$")) + "$" + artifactNewName,
							ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue(), ProjectExplorerUtil.getIcon(CommonIndexUtils.RULE_TEMPLATE_INSTANCE_EXTENSION),
							ARTIFACT_TYPES.RULETEMPLATEINSTANCE);
				}
				
			} else if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=" + CommonIndexUtils.PROCESS_EXTENSION + "&") != -1) {
				String parentId = this.artifactToRename.getId().substring(0, this.artifactToRename.getId().lastIndexOf("$"));
				navigatorResource = new ProcessNavigatorResource(artifactNewName,
						parentId,
						parentId + "$" + artifactNewName,
						CommonIndexUtils.PROCESS_EXTENSION,
						ProjectExplorerUtil.getIcon(CommonIndexUtils.PROCESS_EXTENSION),
						ARTIFACT_TYPES.PROCESS);
				
			} else if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=" + CommonIndexUtils.DOMAIN_EXTENSION + "&") != -1) {
				String parentId = this.artifactToRename.getId().substring(0, this.artifactToRename.getId().lastIndexOf("$"));
				navigatorResource = new NavigatorResource(artifactNewName, parentId, parentId + "$" + artifactNewName, ARTIFACT_TYPES.DOMAIN.getValue(), ProjectExplorerUtil.getIcon(CommonIndexUtils.DOMAIN_EXTENSION), ARTIFACT_TYPES.DOMAIN);
			}
			
			if (navigatorResource != null) {
				ProjectExplorerUtil.updateGroups(artifactToRename, false);
				ProjectExplorerUtil.updateGroups(navigatorResource, true);
			}
			validEvent = true;
		}
		String responseMessage = docElement.getElementsByTagName("responseMessage").item(0).getFirstChild().getNodeValue();
		logger.info("Artifact rename completed, response - " + responseMessage);
		
		if(responseMessage.equals("Rename completed successfully.")){
			
			responseMessage = globalMsgBundle.artifactRename_successMessage();
		}
		CustomSC.say(globalMsgBundle.text_note(), responseMessage);
		
		if (validEvent) {
			removeHandlers(this);
		}
		
		// on successfull rename add the entries to the map
		addArtifactsToRenameMap(fqpOldArtifactName, fqpNewArtifactName);
	}
	
	public void addArtifactsToRenameMap(String oldName, String newName) {
		renameArtifactMap.put(oldName, newName);
	}
	
	public void removeArtifactsFromRenameMap(String oldName) {
		renameArtifactMap.remove(oldName);
	}
	
	public boolean checkArtifactExistsInRenameMap(String oldName) {
		return renameArtifactMap.containsKey(oldName);
	}
	
	public String getArtifactValueFromRenameMap(String oldName) {
		return renameArtifactMap.get(oldName);
	}
	
	public String getArtifactKeyFromRenameMap(String newName) {
		for (String key : renameArtifactMap.keySet()) {
			if (newName.equals(renameArtifactMap.get(key))) {
				return key;
			}
		}
		return null;
	}
}