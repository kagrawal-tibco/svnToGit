/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.FormErrorOrientation;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSCommitDS;
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
import com.tibco.cep.webstudio.client.util.ArtifactRenameHelper;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
/**
 * Dialog for committing a project managed under one of the configured RMS Server. 
 * 
 * @author Vikram Patil
 */
public class RMSCommitDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {
	
	private ListGrid artifactGrid;
	private TextAreaItem commit;
	private String[] selectedArtifacts;
	private Label revisionLabel;
	private DynamicForm commitForm;
	
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Default Constructor
	 */
	public RMSCommitDialog(String[] artifactPaths) {
		this.selectedArtifacts = artifactPaths;
		
		setDialogWidth(702);
		setDialogHeight(375);
		setDialogTitle(rmsMsgBundle.rmsCommit_title());		
		setDialogHeaderIcon(WebStudioMenubar.ICON_PREFIX + "commit" + WebStudioMenubar.ICON_SUFFIX);
		
		initialize();
		
		getCommitableArtifacts();
	}
	
	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();

		widgetList.add(createCommitForm());
		widgetList.add(createArtifactGrid());

		return widgetList;
	}
	
	/**
	 * Create a form to input the comments to be associated with the Committed files.
	 * @return
	 */
	private HLayout createCommitForm() {
		HLayout commitContainer = new HLayout();
		commitContainer.setWidth100();
		
		commitForm = new DynamicForm();
		commitForm.setTitleOrientation(TitleOrientation.TOP);
		
		commit = new TextAreaItem("comments", globalMsgBundle.text_comments() + " (" + globalMsgBundle.text_recommended() + ")");
		commit.setWidth(670);
		commit.setHeight(60);
//		commit.setRequired(true);
		commit.setErrorOrientation(FormErrorOrientation.RIGHT);
//		commit.addChangedHandler(new ChangedHandler() {
//			public void onChanged(ChangedEvent event) {
//				if (artifactGrid.getSelectedRecords().length > 0) {
//					okButton.enable();
//				} else {
//					okButton.disable();
//				}
//			}
//		});
		
		commitForm.setItems(new FormItem[]{commit});
		commitContainer.addMember(commitForm);
		
		return commitContainer;
	}
	
	/**
	 * Create a artifact grid, listing all artifacts associated to a selected project to be commited
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
		revisionLabel.setContents(rmsMsgBundle.rmsCommit_SCSCredentials_message());
		
		container.addMember(revisionLabel);
	}
	
	/**
	 * Fetch commitable artifacts
	 */
	private void getCommitableArtifacts() {
		RMSCommitDS.getInstance().clearRequestParameters();
		RMSCommitDS.getInstance().setHttpMethod(HttpMethod.POST);

		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		RMSCommitDS.getInstance().setAdditionalURLPath("projects/" + selectedProject);
		RMSCommitDS.getInstance().addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, selectedProject));
		
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
		
		artifactGrid.addDataArrivedHandler(new DataArrivedHandler() {
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				if (artifactGrid.getRecords().length > 0) {
					artifactGrid.selectAllRecords();
				}
			}
		});
		
		cleanupCache(artifactGrid);
		artifactGrid.fetchData();
	}
	
	@Override
	public void onAction() {
		if (artifactGrid.getSelectedRecords().length == 0) {
			CustomSC.say(rmsMsgBundle.rmsCommit_selectRecord_message());
		} else {
			if (commitForm.validate()) {
				String renameCaseCheck = checkForArtifactRenameCases();
				if (renameCaseCheck != null && !renameCaseCheck.isEmpty()) {
					CustomSC.warn(rmsMsgBundle.rmsCommit_RenameCheck_message() + renameCaseCheck);

				} else {
					Map<String, Object> requestParameters = new HashMap<String,Object>();

					requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, WebStudio.get().getCurrentlySelectedProject());
					requestParameters.put(RequestParameter.REQUEST_PARAM_COMMIT_COMMENTS, commit.getValueAsString());
					requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, getSelectedArtifactList(artifactGrid.getSelectedRecords(), true));

					@SuppressWarnings("rawtypes")
					String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_PROJECT_COMMITTED_ARTIFACTS, requestParameters);

					ArtifactUtil.addHandlers(this);

					HttpRequest request = new HttpRequest();
					request.setMethod(HttpMethod.POST);
					request.setPostData(xmlData);
					request.submit(ServerEndpoints.RMS_POST_PROJECT_COMMITTED_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject()));

					okButton.disable();
					cancelButton.setTitle(globalMsgBundle.button_finish());
				}
			}
		}
	}
	
	/**
	 * Check Rename cases while committing artifacts. Should be done in pairs of delete and add.
	 * 
	 * @return
	 */
	private String checkForArtifactRenameCases() {
		List<String> renamedArtifacts = new ArrayList<String>();
				
		String projectName = WebStudio.get().getCurrentlySelectedProject();
		
		String artifactPath = null;
		String nextArtifactPath = null;
		for(ListGridRecord gridRecord : artifactGrid.getSelectedRecords()) {
			artifactPath = projectName + "/" + gridRecord.getAttributeAsString("artifactPath");
			
			if (nextArtifactPath == null) {
				if (ArtifactRenameHelper.getInstance().checkArtifactExistsInRenameMap(artifactPath)) {
					nextArtifactPath = ArtifactRenameHelper.getInstance().getArtifactValueFromRenameMap(artifactPath);
					renamedArtifacts.add(artifactPath);
				} else if ((nextArtifactPath = ArtifactRenameHelper.getInstance().getArtifactKeyFromRenameMap(artifactPath)) != null) {
					renamedArtifacts.add(artifactPath);
				}
				
			} else {
				if (nextArtifactPath.equals(artifactPath)) {
					String artifactName = ArtifactRenameHelper.getInstance().getArtifactValueFromRenameMap(artifactPath);
					if (artifactName == null || artifactName.isEmpty()) {
						artifactName = ArtifactRenameHelper.getInstance().getArtifactKeyFromRenameMap(artifactPath);
					}
					renamedArtifacts.remove(artifactName);
					nextArtifactPath = null;
				}
			}
		}
		
		String renamedArtifactPairs = "";
		if (renamedArtifacts.size() > 0) {
			String otherArtifact = null;
			for (String artifact : renamedArtifacts) {
				otherArtifact = ArtifactRenameHelper.getInstance().getArtifactValueFromRenameMap(artifact);
				if (otherArtifact == null || otherArtifact.isEmpty()) {
					otherArtifact = ArtifactRenameHelper.getInstance().getArtifactKeyFromRenameMap(artifact);
				}
				
				if (otherArtifact != null && !otherArtifact.isEmpty()) {
					otherArtifact = otherArtifact.substring(otherArtifact.indexOf("/")+1, otherArtifact.length());
				}
				
				artifact = artifact.substring(artifact.indexOf("/")+1, artifact.length());
				renamedArtifactPairs += "[" + artifact + ", " +  otherArtifact + "],";
			}
			
			if (renamedArtifactPairs != null && !renamedArtifactPairs.isEmpty()) {
				renamedArtifactPairs = renamedArtifactPairs.substring(0, renamedArtifactPairs.length() - 1);
			}
		}
		
		return renamedArtifactPairs;
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_PROJECT_COMMITTED_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			
			Element docElement = event.getData();
			String responseMessage = docElement.getElementsByTagName("responseMessage").item(0).getFirstChild().getNodeValue();
			revisionLabel.setContents(getGlobalCommitSuccessMessage(responseMessage));
			
			ListGridRecord[] selectedRecords = artifactGrid.getSelectedRecords();
			artifactGrid.deselectAllRecords();
			
			String projectName = WebStudio.get().getCurrentlySelectedProject();
			
			String artifactPath = null;
			for (ListGridRecord record : selectedRecords) {
				artifactPath = projectName + "/" + record.getAttributeAsString("artifactPath");
				if (ArtifactRenameHelper.getInstance().checkArtifactExistsInRenameMap(artifactPath)) {
					ArtifactRenameHelper.getInstance().removeArtifactsFromRenameMap(artifactPath);
				}
				
				record.setEnabled(false);
			}
			commit.clearValue();
			
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	private String getGlobalCommitSuccessMessage(String message) {
		if (message == null) {
			return "";
		}
		RegExp regexp = RegExp.compile(".*\\[(.*?)\\].*", "i");//Checkin Sucessful with Revision Id [%s]
		MatchResult matchResult = regexp.exec(message);
		if (matchResult != null && matchResult.getGroupCount() > 1) {
			String revisionId = matchResult.getGroup(1);
			message = rmsMsgBundle.servermessage_checkin_successful(revisionId);
		}
		return message;
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_PROJECT_COMMITTED_ARTIFACTS.getURL("projects", WebStudio.get().getCurrentlySelectedProject())) != -1) {
			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
			showError(responseMessage);
			
			ArtifactUtil.removeHandlers(this);
		}
	}
}
