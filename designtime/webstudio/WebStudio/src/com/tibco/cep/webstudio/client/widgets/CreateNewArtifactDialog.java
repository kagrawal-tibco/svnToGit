package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getVirtualRuleFunction;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.isVirtual;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditorFactory;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor;
import com.tibco.cep.webstudio.client.groups.ContentModelManager;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.RuleTemplateInstanceNavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateNavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.process.ProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessEditorFactory;
import com.tibco.cep.webstudio.client.process.ProcessNavigatorResource;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog to create new Business Rule / Decision Table.
 * @author vdhumal
 *
 */
public class CreateNewArtifactDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {

	private static final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	private static final String ROOT_NODE = "Root";

	protected TextItem nameTextItem;
	protected TextItem parentFolderTextItem;
	protected TreeGrid folderTreeGrid;
	protected NavigatorResource selectedResource;
	protected DynamicForm fileForm;
	protected String projectName;
	protected String resourceType;
	private String newArtifactName;
	private String artifactExtn;
	private String parentFolderPath;
	private static final String ERROR_CODE_ADD_RESOURCE_ACCESS_DENIED = "ERR_1103";
	
	protected GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);

	public CreateNewArtifactDialog(NavigatorResource selectedResource, String resourceType, String artifactExtn) { 
		this(selectedResource, "", resourceType, artifactExtn);
		
		if (selectedResource instanceof RuleFunctionNavigatorResource) {
			this.setHeaderIcon(Page.getAppImgDir() + "icons/16/decisiontable.png");
			this.setTitle(globalMsgBundle.projectExplorer_new_dt());
		} else if (selectedResource instanceof RuleTemplateNavigatorResource) {
			this.setHeaderIcon(Page.getAppImgDir() + "icons/16/rulesTemplateInstance.png");
			this.setTitle(globalMsgBundle.projectExplorer_ruleTemplate());
		} else if(resourceType.equals("Process")){
			this.setHeaderIcon(Page.getAppImgDir() + "icons/16/appicon16x16.gif");
			this.setTitle(globalMsgBundle.projectExplorer_new_process());
		}
		
	} 

	public CreateNewArtifactDialog(NavigatorResource selectedResource, String operation, String resourceType, String artifactExtn) { 
		this.selectedResource = selectedResource;
		this.resourceType = resourceType;
		this.artifactExtn = artifactExtn;
		this.projectName = selectedResource.getId().substring(0, selectedResource.getId().indexOf("$"));
		this.setDialogWidth(400);
		this.setDialogHeight(250);
		this.setDialogTitle(operation + " " + this.resourceType);
		this.initialize();
		setIsModal(false);
		setShowModalMask(false);
	} 

	public String getNewArtifactName() {
		return newArtifactName;
	}

	public void setNewArtifactName(String newArtifactName) {
		this.newArtifactName = newArtifactName;
	}

	public String getParentFolderPath() {
		return parentFolderPath;
	}

	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		widgetList.add(createForm());
		return widgetList;
	}

	protected VLayout createForm() {
		VLayout container = new VLayout(5);
		container.setWidth100();
	
		fileForm = new DynamicForm();
		fileForm.setWidth100();
		fileForm.setNumCols(2);
		fileForm.setAlign(Alignment.CENTER);
					
		nameTextItem = new TextItem("artifactName", resourceType);
		nameTextItem.setAlign(Alignment.LEFT);
		nameTextItem.setWrapTitle(false);
		nameTextItem.setWidth(300);
		nameTextItem.setErrorIconSrc(Page.getAppImgDir() + "icons/16/error.png");
		nameTextItem.setShowErrorIcon(false);
		nameTextItem.setShowErrorText(false);
		nameTextItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				toggleOK();
				
			}
		});

		String baseArtifactFolderPath = selectedResource.getId().substring(this.selectedResource.getId().indexOf("$"), 
																			this.selectedResource.getId().lastIndexOf("$")).replace("$", "/");
		if (this.selectedResource.getId().indexOf("$") == this.selectedResource
				.getId().lastIndexOf("$")) {
			baseArtifactFolderPath = selectedResource
					.getId()
					.substring(this.selectedResource.getId().indexOf("$"),
							this.selectedResource.getId().length())
					.replace("$", "/");
		}
		parentFolderTextItem = new TextItem("parentPath", globalMsgBundle.createNew_resource_parentFolder());
		parentFolderTextItem.setValue(baseArtifactFolderPath);
		parentFolderTextItem.setAlign(Alignment.LEFT);
		parentFolderTextItem.setWrapTitle(false);
		parentFolderTextItem.setWidth(300);
		parentFolderTextItem.setErrorIconSrc(Page.getAppImgDir() + "icons/16/error.png");
		parentFolderTextItem.setShowErrorIcon(false);
		parentFolderTextItem.setShowErrorText(false);
		parentFolderTextItem.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				toggleOK();
				
			}
		});

		fileForm.setFields(nameTextItem, parentFolderTextItem);	
		container.addMember(fileForm);

		folderTreeGrid = new ProjectResourcesTreeGrid(projectName);
		folderTreeGrid.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				NavigatorResource record = (NavigatorResource) event.getRecord();				
				String folder = null;
				if (ARTIFACT_TYPES.PROJECT.getValue().equals(record.getType())) {
					folder = "/";
				} else {
					folder = record.getId().substring(record.getId().indexOf("$")).replace("$", "/");
				}	
				parentFolderTextItem.setValue(folder);
			}
		});
		container.addMember(folderTreeGrid);

		return container;
	}
	
	@Override
	public void onAction() {
		boolean isNameValid = false; 
		boolean isParentFolderValid = false; 
		String artifactName = nameTextItem.getValueAsString().trim();

		String folderPath = parentFolderTextItem.getValueAsString().trim();
		folderPath = folderPath.replace("\\", "/");
		if (!folderPath.startsWith("/")) {
			folderPath = "/" + folderPath;
		}
		if (folderPath.endsWith("/")) {
			folderPath = folderPath.substring(0, folderPath.lastIndexOf("/"));
		}
		
		String resourceId = projectName + folderPath.replace("/", "$") + "$" + artifactName + CommonIndexUtils.DOT 
																							+ CommonIndexUtils.RULE_FN_IMPL_EXTENSION;
		if(artifactExtn.equals(".beprocess")){
			 resourceId = projectName + folderPath.replace("/", "$") + "$" + artifactName + CommonIndexUtils.DOT 
						+ CommonIndexUtils.PROCESS_EXTENSION;
		}
		WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid(); 																							
		if (artifactName.isEmpty()) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_emptyResourceErrorMessage(resourceType));
		} else if (!ArtifactUtil.isValidArtifactName(artifactName)) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_invalidResourceErrorMessage(artifactName, resourceType));
		} else if (navGrid.resourceNameExits(resourceId)) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_existingResourceErrorMessage(resourceType));
		} else {
			isNameValid = true;
		}
		
		isParentFolderValid = true;
					
		if (isNameValid && isParentFolderValid) {
			ArtifactUtil.addHandlers(this);
			parentFolderPath = folderPath;
//			if (!navGrid.folderExits(parentFolderPath)) {
//				navGrid.getClickHandler().createNewFolder(folderPath.trim());
//			}
			if (artifactExtn.equals(".ruletemplateinstance")) {
				newArtifactName = artifactName + artifactExtn;
				RuleTemplateHelper.getRuleTemplate(selectedResource);
			} else if (artifactExtn.equals(".rulefunctionimpl")) {
				newArtifactName = artifactName + artifactExtn;
				getVirtualRuleFunction(selectedResource);
			}else if(artifactExtn.equals(".beprocess")){
				newArtifactName = artifactName + artifactExtn;	
				createNewProcess();	
			}
			WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID);
		}					
	}
	
	protected void toggleOK() {
		if(nameTextItem.getValue() != null && parentFolderTextItem.getValue() != null){
			okButton.enable();
		}
		else{
			okButton.disable();
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=ruletemplate&") != -1) {
			
			//String errorCode = event.getData().getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			if (event.getData().getElementsByTagName("errorCode").item(0) != null && ERROR_CODE_ADD_RESOURCE_ACCESS_DENIED.equals(event.getData().getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue())) {
				String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			    String projectname = responseMessage.substring(responseMessage.lastIndexOf("in project")+10);
				CustomSC.say(rmsMsgBundle.servermessage_RTIadd_denied(projectname));
			}else{
				createNewRuleTemplateInstance(event);
			}
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=rulefunction&") != -1) {	
			
			//String errorCode = event.getData().getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			if (event.getData().getElementsByTagName("errorCode").item(0) != null && ERROR_CODE_ADD_RESOURCE_ACCESS_DENIED.equals(event.getData().getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue())) {
				String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			    String projectname = responseMessage.substring(responseMessage.lastIndexOf("in project")+10);
				CustomSC.say(rmsMsgBundle.servermessage_DTadd_denied(projectname));
			}else{
				createNewDecisionTable(event);
			}
			validEvent = true;
		}
		WebStudio.get().getApplicationToolBar().enableButton(WebStudioToolbar.TOOL_STRIP_COMMIT_ID);
		if (validEvent) {
			removeHandlers(this);
			this.destroy();
		}	
	}
	/**
  	 *Create new Process
	 */
	private void createNewProcess() {
		if (this.newArtifactName == null) {
			removeHandlers(this);
			return;
		}
		String artifactName = nameTextItem.getValueAsString().trim();
		
		String parent = this.projectName
				+ this.parentFolderPath.replace("/", "$");
		String Id = parent + "$" + this.newArtifactName;
		ProcessNavigatorResource navigatorResource = new ProcessNavigatorResource(
				newArtifactName, parent, Id, ARTIFACT_TYPES.PROCESS.getValue(),
				Page.getAppImgDir() + "icons/16/appicon16x16.gif",
				ARTIFACT_TYPES.PROCESS);

		// update project explorer and any DT specific group/s
		ProjectExplorerUtil.updateGroups(navigatorResource, true);
		WebStudio.get().showWorkSpacePage();
		ProcessEditorFactory editorFactory = (ProcessEditorFactory) EditorFactory
				.getArtifactEditorFactory(navigatorResource);
		ProcessEditor editor = (ProcessEditor) editorFactory.createEditor(
				navigatorResource, false);
		editor.createNewProcess();
		editor.setNewArtifact(true);

		WebStudio.get().getEditorPanel().addTab(editor);

		WebStudio.get().getEditorPanel().selectTab(editor);
		WebStudio.get().setCurrentlySelectedArtifact(Id);
		this.newArtifactName = null;

		removeHandlers(this);
		this.destroy();
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;		
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1) {
			validEvent = true;
		}
		if (validEvent) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);		
			removeHandlers(this);		
		}
	}

	protected void createNewRuleTemplateInstance(HttpSuccessEvent event) {		
		String parentId = this.projectName + this.parentFolderPath.replace("/", "$");
		
		if (this.newArtifactName == null) {
			removeHandlers(this);
			return;
		}
		String Id = parentId + "$" + this.newArtifactName;
		RuleTemplateInstanceNavigatorResource navigatorResource = new RuleTemplateInstanceNavigatorResource(this.newArtifactName,
				parentId,
				this.selectedResource.getId(),
				Id,
				"ruletemplateinstance",
				Page.getAppImgDir() + "icons/16/rulesTemplateInstance.png",
				ARTIFACT_TYPES.RULETEMPLATEINSTANCE);

		WebStudio.get().showWorkSpacePage();

		RuleTemplateInstanceEditorFactory editorFactory = (RuleTemplateInstanceEditorFactory) EditorFactory
				.getArtifactEditorFactory(navigatorResource);
		RuleTemplateInstanceEditor editor = (RuleTemplateInstanceEditor) editorFactory
				.createEditor(navigatorResource, false);
		editor.setInitializing(true); // this avoids improperly marking the editor as dirty while creating a new RTI
		String implementsPath = this.selectedResource.getId()
				.substring(this.selectedResource.getId().indexOf("$"), this.selectedResource.getId().length())
				.replace("$", "/");
		String artifactPath = this.parentFolderPath + "/" + this.newArtifactName;
		
		// process ahead only if processing succeeds
		if (editor.processRuleTemplateInstance(event.getData(), null, null, implementsPath, artifactPath, true)) {
			// update project explorer and any RTI specific group/s
			ProjectExplorerUtil.updateGroups(navigatorResource, true);
			
			editor.setNewArtifact(true);

			WebStudio.get().getEditorPanel().addTab(editor);
			editor.save(); // persist the RTI on the server side
			ProjectExplorerUtil.toggleImportExportSelection(navigatorResource);
			editor.getRtiPropertyTab().showRTIPropertiesPane();
			WebStudio.get().setCurrentlySelectedArtifact(Id);
		} else {
			String artifactName = artifactPath.indexOf(".") != -1?artifactPath.substring(0, artifactPath.indexOf(".")) : artifactPath;
			ErrorMessageDialog.showError("Invalid Rule Template Instance (" + artifactName + "), no view defined.");
		}
		
		this.newArtifactName = null;
		editor.setInitializing(false);
		
	}
	
	protected void createNewDecisionTable(HttpSuccessEvent event) {
		if (this.newArtifactName == null) {
			removeHandlers(this);
			return;
		}
		
		if (isVirtual(event.getData())) {
			((RuleFunctionNavigatorResource)selectedResource).setVRF(true);
			selectedResource.setIcon(Page.getAppImgDir() + "icons/16/vrf_16x16.png");
			WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid().refresh();
		} else {
			ErrorMessageDialog.showError(dtMsgs.dtisvirtialerrordesc(selectedResource.getName()));
			removeHandlers(this);
			return;
		}
		
		String parent = this.projectName + this.parentFolderPath.replace("/", "$");
		String Id = parent + "$" + this.newArtifactName;
		DecisionTableNavigatorResource navigatorResource = new DecisionTableNavigatorResource(this.newArtifactName,
				parent,
				this.selectedResource.getId(),
				Id,
				"rulefunctionimpl",
				Page.getAppImgDir() + "icons/16/decisiontable.png",
				ARTIFACT_TYPES.RULEFUNCTIONIMPL);

		// update project explorer and any DT specific group/s
		ProjectExplorerUtil.updateGroups(navigatorResource, true);
		WebStudio.get().showWorkSpacePage();
		WebStudio.get().setCurrentlySelectedArtifact(Id);

		DecisionTableEditorFactory editorFactory = (DecisionTableEditorFactory) EditorFactory.getArtifactEditorFactory(navigatorResource);
		DecisionTableEditor editor = (DecisionTableEditor) editorFactory.createEditor(navigatorResource, false);

		String implementsPath = this.selectedResource.getId()
				.substring(this.selectedResource.getId().indexOf("$"), this.selectedResource.getId().indexOf(".rulefunction"))
				.replace("$", "/");
		String folder = this.parentFolderPath + "/";
		
		String decisionTableName = this.newArtifactName;
		if (decisionTableName.endsWith(".rulefunctionimpl")) {
			decisionTableName = decisionTableName.substring(0, decisionTableName.indexOf(".rulefunctionimpl"));
		}
		editor.createNewDecisionTable(event.getData(), decisionTableName, implementsPath, folder);

		editor.setNewArtifact(true);

		WebStudio.get().getEditorPanel().addTab(editor);
		ProjectExplorerUtil.toggleImportExportSelection(navigatorResource);
		
		this.newArtifactName = null;
		editor.doSave();
		
	}
			
}
