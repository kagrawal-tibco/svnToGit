package com.tibco.cep.webstudio.client.process;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;


/**
 * 
 * @author sasahoo
 *
 */
public class ProcessEditor extends AbstractProcessEditor {

	public ProcessEditor(NavigatorResource record, 
			             boolean hasSections,
			             boolean isReadOnly, String revisionId, 
			             boolean loadDataAtStartup,
			             boolean versionDiffContent) {
		super(record, hasSections, isReadOnly, revisionId, loadDataAtStartup,
				versionDiffContent);
		setHasSections(false);
		//setShowLoading(false);
		initialize();
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		boolean isNewArtifact = false;
		String message = "";
		if ((event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
				|| event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL()) != -1)
				&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=beprocess") != -1) {
			init(event.getData(), false, null, null);
			validEvent = true;
			if (!isReadOnly()) {
				//TODO
			}
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL()) != -1) {
			validEvent = true;
			postSave();
			if (this.isNewArtifact()) {
				isNewArtifact = true;
				this.setNewArtifact(false);
			}
			
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_PROCESS_VALIDATE.getURL()) != -1) {
			validEvent = true;
		} 
		if (validEvent) {
			removeHandlers(this);
		/*	if (!isNewArtifact) {
				indeterminateProgress(message, true);
			}*/
		}
	}
	
	@Override
	public String getEditorUrl() {
		return null;
	}
	
	private void init(Element data, boolean isNew, String name, String artifactPath) {
		processArtifactPath = selectedRecord.getId().replace("$", "/");
		projectName = processArtifactPath.substring(0, processArtifactPath.indexOf("/"));
		artifactExtention = processArtifactPath.substring(processArtifactPath.indexOf(".") + 1, processArtifactPath.length());
		processArtifactPath = processArtifactPath.substring(processArtifactPath.indexOf("/"), processArtifactPath.indexOf("."));
		processName = selectedRecord.getName();
				
		Node processNode = data.getElementsByTagName("process").item(0);
		process = processNode.toString();
		
//		showProperties();
//		showPalette();
		
		populate(parentCanvas);
	}
	
	@Override
	public void postSave() {
		super.postSave();
	}
	

	@Override
	public void onFailure(HttpFailureEvent event) {
		super.onFailure(event);
	}
	@Override
	public void createNewProcess() {
		LoadingMask.showMask();
		processArtifactPath = selectedRecord.getId().replace("$", "/");
		projectName = processArtifactPath.substring(0, processArtifactPath.indexOf("/"));
		artifactExtention = processArtifactPath.substring(processArtifactPath.indexOf(".") + 1, processArtifactPath.length());
		processArtifactPath = processArtifactPath.substring(processArtifactPath.indexOf("/"), processArtifactPath.indexOf("."));
		processName = selectedRecord.getName();
		
//		showProperties();
//		showPalette();
		
		populate(parentCanvas);
	}
}
