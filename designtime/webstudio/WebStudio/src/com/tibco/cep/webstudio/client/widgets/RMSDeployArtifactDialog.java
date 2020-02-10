/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.addMarkers;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSManagedProjectDS;
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
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Dialog for providing hot deploy support for .
 * 
 * @author Vikram Patil
 */
public class RMSDeployArtifactDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler, ChangedHandler {

	private RMSMessages rmsMsgBundle = (RMSMessages) I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private SelectItem projectList;
	private String[] selectedArtifacts;
	private Label responseLabel;
	private CheckboxItem genClassItem;
	private CheckboxItem genDebugInfoItem;
	private CheckboxItem serviceGlobalVarItem;
	
	boolean genClassOnly = false;
	boolean genDebugInfo = true;
	boolean serviceGlobalVar = true;
	
    private String progressMsg = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
    		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" 
    + Canvas.imgHTML(Page.getAppImgDir() + "icons/16/gen_deploy_loading.gif") + "<br>" + rmsMsgBundle.rmsDeployable_generateInProgress() + "..";
    
    private static final String ERROR_CODE_GENERATE_DEPLOYABLE_ACCESS_DENIED = "ERR_1191";
	private static final String ERROR_CODE_GENERATE_DEPLOYABLE_CLASSES_FAILED = "ERR_1192";
	private static final String ERROR_CODE_GENERATE_DEPLOYABLE_EAR_FAILED = "ERR_1193";
	
	/**
	 * Default Constructor
	 * @param artifactPath
	 */
	public RMSDeployArtifactDialog(String[] selectedArtifacts) {
		this.selectedArtifacts = selectedArtifacts;
		this.setDialogWidth(500);
		this.setDialogHeight(200);
		this.setDialogTitle(this.rmsMsgBundle.rmsDeployable_title());
		this.setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "generatedeployable.gif");
		this.initialize();
		if (this.selectedArtifacts != null) {
			this.projectList.setValue(WebStudio.get().getCurrentlySelectedProject());
			this.okButton.enable();
		} else {
			this.projectList.setEmptyPickListMessage(globalMsgBundle.message_browseButton_emptyMessage());
		}
	}

	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		widgetList.add(this.createArtifactDeployForm());
		widgetList.add(this.createDebugInfoSection());
		widgetList.add(createResponseLabel());
		return widgetList;
	}

	/**
	 * Create form for deploying artifacts
	 * 
	 * @return
	 */
	private VLayout createArtifactDeployForm() {
		VLayout artifactDeployFormContainer = new VLayout(5);

		DynamicForm artifactDeployForm = new DynamicForm();
		artifactDeployForm.setWidth100();
		artifactDeployForm.setNumCols(2);
		artifactDeployForm.setColWidths("20%", "*");

		this.projectList = new SelectItem("projectName", this.rmsMsgBundle.rmsCheckout_projectList());
		this.projectList.setDisplayField("projectName");
		this.projectList.setOptionDataSource(RMSManagedProjectDS.getInstance());
		
		this.projectList.setWidth("100%");
		this.projectList.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {	
				if (projectList.getValue() != null) {
					okButton.enable();
				} else {
					okButton.disable();
				}
			}
		});

		artifactDeployForm.setItems(this.projectList);
		artifactDeployFormContainer.addMember(artifactDeployForm);
		return artifactDeployFormContainer;
	}

	/**
	 * Grid to display available artifacts based on type
	 * 
	 * @return
	 */
	private Canvas createDebugInfoSection() {
		
		DynamicForm form = new DynamicForm();  
		form.setWidth100();
		form.setColWidths("50%", "*");
		form.setIsGroup(true);
		form.setGroupTitle(this.rmsMsgBundle.rmsDeployable_additionalInfoTitle());
		
		genClassItem = new CheckboxItem();   
		genClassItem.setWidth(200);
		genClassItem.setTitle(this.rmsMsgBundle.rmsDeployable_classes());
		genClassItem.setLabelAsTitle(true);
		genClassItem.setValue(genClassOnly);
		genClassItem.addChangedHandler(this);
		
		genDebugInfoItem = new CheckboxItem();   
		genDebugInfoItem.setWidth(200);
		genDebugInfoItem.setTitle(this.rmsMsgBundle.rmsDeployable_debugInfo());
		genDebugInfoItem.setLabelAsTitle(true);
		genDebugInfoItem.setValue(genDebugInfo);
		genDebugInfoItem.addChangedHandler(this);
		
		serviceGlobalVarItem = new CheckboxItem();   
		serviceGlobalVarItem.setWidth(200);
		serviceGlobalVarItem.setTitle(this.rmsMsgBundle.rmsDeployable_globalVariables());
		serviceGlobalVarItem.setLabelAsTitle(true);
		serviceGlobalVarItem.setValue(serviceGlobalVar);
		serviceGlobalVarItem.addChangedHandler(this);
		
		form.setItems(genClassItem, genDebugInfoItem, serviceGlobalVarItem);
		
		return form;
	}
	
	private Label createResponseLabel() {
		responseLabel = new Label();
		responseLabel.setWidth100();
		responseLabel.setHeight(10);
		responseLabel.hide();
		return responseLabel;
	}
	
	@Override
	public void onAction() {
		responseLabel.setContents(progressMsg);
		responseLabel.show();
		
		okButton.disable();
		cancelButton.disable();

		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		ArtifactUtil.addHandlers(this);
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, this.projectList.getValueAsString()));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_GEN_DEBUG_INFO, this.genDebugInfo));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_GEN_GENERATE_CLASS_ONLY, this.genClassOnly));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_INCLUDE_SERVICE_GLOBAL_VAR, this.serviceGlobalVar));
		request.submit(ServerEndpoints.RMS_GENERATE_DEPLOYABLE.getURL("projects",this.projectList.getValueAsString()));
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GENERATE_DEPLOYABLE.getURL("projects",this.projectList.getValueAsString())) != -1) {
			ArtifactUtil.removeHandlers(this);
			Element docElement = event.getData();
			NodeList warnMsgsNodes = docElement.getElementsByTagName("errorMessage");
			String projectName = this.projectList.getValueAsString();
			List<ProblemRecord> warnrecords = new ArrayList<ProblemRecord>();
			for (int i = 0; i < warnMsgsNodes.getLength(); i++) {
				Node warnMsgNode = warnMsgsNodes.item(i);
				String warnMessage = warnMsgNode.getFirstChild().getNodeValue();
				if(warnMessage.equals("Project has no Decision Table / Rule Template Instance / Process definitions.") || warnMessage.contains("Project has no Decision Table / Rule Template Instance / Process definitions.")){
					warnMessage = rmsMsgBundle.servermessage_generateDeployable_noSupportedArtifacts();
				}
				ProblemMarker marker = new ProblemMarker("", projectName, "", "", "", "", warnMessage);
				addMarkers(marker, ProblemEvent.WARNING, warnrecords, null);
			}			
			updateProblemRecords(warnrecords, null, false);

			this.destroy();
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GENERATE_DEPLOYABLE.getURL("projects",this.projectList.getValueAsString())) != -1) {
			ArtifactUtil.removeHandlers(this);

			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			String errorCode = null;
			if(docElement.getElementsByTagName("errorCode") != null && docElement.getElementsByTagName("errorCode").getLength() > 0) {
				errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue();
			}
			setErrorMessage(responseMessage, errorCode);
			
			responseLabel.setStyleName("ws-response-message");
			
			cancelButton.enable();
		}
	}

	@Override
	public void onChanged(ChangedEvent event) {
		if (event.getSource() == genDebugInfoItem) {
			genDebugInfo = (Boolean)genDebugInfoItem.getValue();
		}
		if (event.getSource() == genClassItem) {
			genClassOnly = (Boolean)genClassItem.getValue();
		}
		if (event.getSource() == serviceGlobalVarItem) {
			serviceGlobalVar = (Boolean)serviceGlobalVarItem.getValue();
		}
	}
	
	/**
	 * Show Error message
	 * @param error
	 */
	public void setErrorMessage(String error, String errorCode) {
		if (ERROR_CODE_GENERATE_DEPLOYABLE_ACCESS_DENIED.equals(errorCode)) {//Access Denied to generate deployable for project [%s].
			RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*", "i");
			MatchResult matchResult = regexp.exec(error);
			if (matchResult != null && matchResult.getGroupCount() > 1) {
				String projectName = matchResult.getGroup(1);
				error = rmsMsgBundle.servermessage_generateDeployable_denied(projectName);
			}
		}
		else if (ERROR_CODE_GENERATE_DEPLOYABLE_CLASSES_FAILED.equals(errorCode)) {//Deployable generation Failed, Could not generate Classes for project [%s].
			RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*", "i");
			MatchResult matchResult = regexp.exec(error);
			if (matchResult != null && matchResult.getGroupCount() > 1) {
				String projectName = matchResult.getGroup(1);
				error = rmsMsgBundle.servermessage_generateDeployable_failedClasses(projectName);
			}
		}
		else if (ERROR_CODE_GENERATE_DEPLOYABLE_EAR_FAILED.equals(errorCode)) {//Deployable generation Failed, Could not generate EAR for project [%s].
			RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*", "i");
			MatchResult matchResult = regexp.exec(error);
			if (matchResult != null && matchResult.getGroupCount() > 1) {
				String projectName = matchResult.getGroup(1);
				error = rmsMsgBundle.servermessage_generateDeployable_failedEar(projectName);
			}
		}
		responseLabel.setContents(error);
		responseLabel.show();
	}
}
