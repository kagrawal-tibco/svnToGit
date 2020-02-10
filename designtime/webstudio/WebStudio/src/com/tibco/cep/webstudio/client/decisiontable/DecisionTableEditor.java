package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.postProblems;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.saveDecisionTable;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.validateDecisionTable;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import java.util.List;

import com.google.gwt.xml.client.Element;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.diff.DiffHelper;
import com.tibco.cep.webstudio.client.diff.MergedDiffHelper;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.google.gwt.xml.client.Node;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableEditor extends AbstractDecisionTableEditor {

	private boolean incremental = false;
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	public DecisionTableEditor(NavigatorResource selectedRecord, boolean loadDataStartup) {
		this(selectedRecord, false, null, loadDataStartup, false, false, null);
	}

	public DecisionTableEditor(NavigatorResource selectedRecord, 
			                   boolean isReadOnly, 
			                   String revisionId, 
			                   boolean loadDataOnStartUp,
			                   boolean artifactVersionDiff,
			                   boolean artifactVersionMerge,
			                   List<RequestParameter> requestParams) {
		super(selectedRecord, isReadOnly, revisionId, loadDataOnStartUp, artifactVersionDiff);
		setMerge(artifactVersionMerge);
		setAdditionalRequestParams(requestParams);
		setShowLoading(false); //No Masking for now
		initialize();
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		boolean isNewArtifact = false;
		String message = "";
		if ((event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
				|| event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL()) != -1)
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=rulefunctionimpl") != -1) {
			if (currentPage == 0) {
				currentPage = 1;
				init(event.getData(), false, null, null, null);
			} else {
				init(event.getData(), false, null, null, null, true);
			}
			validEvent = true;
			if (!isReadOnly()) {
				ProjectExplorerUtil.toggleImportExportSelection(getSelectedResource());
			}
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL()) != -1) {
			validEvent = true;
			postSave();
			if (this.isNewArtifact()) {
				isNewArtifact = true;
				this.setNewArtifact(false);
			}
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_VALIDATE.getURL()) != -1) {
			validEvent = true;
			message = postProblems(event.getData(), table);
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_VERSION_DIFF.getURL()) != -1
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=rulefunctionimpl") != -1) {
			
			Element docElement = event.getData();
			if (isReadOnly()) {
				makeReadOnly();
			}
			Node previousVersionDocNode = DiffHelper.getPreviousVersionNode(docElement);
			Node currentVersionDocNode = DiffHelper.getCurrentVersionNode(docElement);
			if (previousVersionDocNode != null && currentVersionDocNode != null) {
				if (event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_DIFFMODE + "=1") != -1) {
					//DT diff for merged view.
					Node serverVersionDocNode = DiffHelper.getServerVersionNode(docElement);
					if (serverVersionDocNode != null) {
						Node resultant = MergedDiffHelper.processDTMergedDiff(previousVersionDocNode, currentVersionDocNode, serverVersionDocNode, modifications);
						init(resultant, null, false);
						validEvent = true;
					}
				}
				else {
					//DT diff functionality, compute and merge DT diff
					DiffHelper.processDTDiff(previousVersionDocNode, currentVersionDocNode, modifications);
					init(currentVersionDocNode, null, false);
					validEvent = true;
				}
			}
		}
		if (validEvent) {
			removeHandlers(this);
			if (!isNewArtifact) {
				indeterminateProgress(message, true);
			}
		}
	}

	protected void init(Element data, boolean isNew, String name, String implementsPath, String folder) {
		init(data, isNew, name, implementsPath, folder, false);
	}
	
	protected void init(Node data, String name, boolean isDecisionTablePage) {
		if (table == null)
			table = new Table();
		DecisionTableModelLoader.load(data, table, isDecisionTablePage);
		load(name, isDecisionTablePage);
	}

	private void init(Element data, boolean isNew, String name, String implementsPath, String folder, boolean isDecisionTablePage) {
		if (isNew || table == null) {
			table = new Table();
			isDecisionTablePage = false;
		}
		DecisionTableModelLoader.load(data, table, isNew, implementsPath, folder, isDecisionTablePage);
		load(name, isDecisionTablePage);
	}
		
	private void load(String name, boolean isDecisionTablePage) {
		if (table == null) {
			return;
		}
		if (name != null) {
			table.setName(name);
		}
		this.projectName = table.getProjectName(); 
		this.tableName = table.getName();
		this.folder = table.getFolder();
		this.rfimplements = table.getImplements();

		disableAllToolBar();
		
		if (isDecisionTablePage) {
			loadDecisionTable(false, true, false, null, null);
		} else {
			loadDeclarationTable();
			loadDecisionTable(true, false, false, null, null);
			loadExceptionTable(true, false, null, null);
		}
		
		loadDependencies(isDecisionTablePage);
		
		decisionTableUpdateprovider = new DecisionTableUpdateProvider(table);
		exceptionTableUpdateprovider = new DecisionTableUpdateProvider(table);
		
		if (isReadOnly()) {
			makeReadOnly();
		}
	}
	
	@Override
	public void onSave() {
		doSave();
	}
	
	@Override
	public void postSave() {
		super.postSave();
		enableValidate(!isNewArtifact());
		if (WebStudio.get().getEditorPanel().getLeftPane().isVisible() 
				&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane() != null 
				&& WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().isVisible()) {
			WebStudio.get().getEditorPanel().getDecisionTableAnalyzerPane().refresh(table);
		}
		if (WebStudio.get().getEditorPanel().getBottomPane().isVisible()) {
			propertyTab.refresh(this, table, null, null, true, false, false, true);
		}	
	}
	
	@Override
	public boolean onValidate() {
		validate();
		return true;
	}

	public void doSave() {
		addHandlers(this);
		saveDecisionTable(table, decisionTableUpdateprovider, incremental, isSyncMerge(), this.getSelectedResource(), this.request);
		setDecisionTablePageDirtyState(false);
	}
	
	@Override
	public void validate() {
		indeterminateProgress(globalMsgBundle.progressMessage_pleaseWait() + " " + globalMsgBundle.progressMessage_validatingDT(), false);
		addHandlers(this);
		validateDecisionTable(this.getSelectedResource(), getTable(), this.request);
	}
	
	public void createNewDecisionTable(Element vrfData, String name, String implementsPath, String folder) {
		init(vrfData, true, name, implementsPath, folder);
	}

	@Override
	public String getEditorUrl() {
		return null;
	}
	
	@Override
	public void setSaveConfirmationProperties() {
		setConfirmSaveTitle(dtMessages.dtsaveeditordialogtitle());
		setConfirmSaveDescription(dtMessages.dtsaveeditordialogdesc());
	}
}