package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ArtifactLockDataItem;

public class ArtifactLockHelper implements HttpSuccessHandler, HttpFailureHandler {

	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(ArtifactLockHelper.class.getName());
	private static ArtifactLockHelper instance;
	private NavigatorResource[] artifactsToProcess;
	
	/**
	 * Create/Fetch the singleton instance
	 * 
	 * @return
	 */
	public static ArtifactLockHelper getInstance() {
		if (instance == null) {
			instance = new ArtifactLockHelper();
		}
		return instance;
	}

	/**
	 * Private constructor
	 */
	private ArtifactLockHelper() { }
	
	public void lockArtifacts(NavigatorResource[] artifactsToLock) {
		processArtifacts(ServerEndpoints.RMS_LOCK_ARTIFACT, artifactsToLock);	
	}

	public void unLockArtifacts(NavigatorResource[] artifactsToUnLock) {
		processArtifacts(ServerEndpoints.RMS_UNLOCK_ARTIFACT, artifactsToUnLock);		
	}

	private void processArtifacts(ServerEndpoints serverEndPoint, NavigatorResource[] artifactsToProcess) {
		this.artifactsToProcess = artifactsToProcess;
		String selectedProject = WebStudio.get().getCurrentlySelectedProject();
		
		if (artifactsToProcess != null && artifactsToProcess.length > 0) {
			List<RequestParameter> selectedArtifacts = new ArrayList<RequestParameter>(artifactsToProcess.length);
			String projectName = WebStudio.get().getCurrentlySelectedProject();			
			Map<String, Object> requestParameters = new HashMap<String, Object>();
			requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectName);

			for (int i = 0; i < artifactsToProcess.length; i++) {			
				String artifact = artifactsToProcess[i].getId().replace("$", "/");
				String fileExtension = null;
				if (!artifact.trim().equals(selectedProject.trim())) {
					if (artifact.indexOf("/") != -1) {
						artifact = artifact.substring(artifact.indexOf("/"));
					}
					if (artifact.indexOf(".") != -1) {
						fileExtension = artifact.substring(artifact.indexOf(".") + 1);
						artifact = artifact.substring(0, artifact.indexOf("."));
					}
					RequestParameter requestParameter = new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifact);
					requestParameter.add(RequestParameter.REQUEST_PARAM_TYPE, artifactsToProcess[i].getType());
					requestParameter.add(RequestParameter.REQUEST_PARAM_FILE_EXTN, fileExtension);
					requestParameter.add("lockRequestor", WebStudio.get().getUser().getUserName());
					selectedArtifacts.add(requestParameter);
				}
				requestParameters.put(RequestParameter.REQUEST_PARAM_ARTIFACTLIST, selectedArtifacts);
			}
			
			String xmlData = new SerializeArtifact().generateXML(serverEndPoint, requestParameters);
			ArtifactUtil.addHandlers(this);

			HttpRequest request = new HttpRequest();
			request.setMethod(HttpMethod.POST);
			request.setPostData(xmlData);

			request.submit(serverEndPoint.getURL());
		}	
	}
	
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_LOCK_ARTIFACT.getURL()) != -1) {
			ArtifactUtil.removeHandlers(this);

			boolean lockingEnabled = false;
			Element data = event.getData();
			NodeList artifactNodes = data.getElementsByTagName("artifactLockResponse");
			List<ArtifactLockDataItem> lockDataItems = new ArrayList<ArtifactLockDataItem>();
			for (int i = 0; i < artifactNodes.getLength(); i++) {
				Node artifactNode = artifactNodes.item(i);
				NodeList artifactChildNodes = artifactNode.getChildNodes();
				ArtifactLockDataItem lockDataItem = new ArtifactLockDataItem();
				String artifactPath = null, projectName= null, lockRequestor = null, lockOwner = null, artifactType = null, fileExtension = null;
				boolean lockAcquired = false;
				for (int j = 0; j < artifactChildNodes.getLength(); j++) {
					Node artifactChildNode = artifactChildNodes.item(j);
					if (!artifactChildNode.toString().trim().isEmpty()) {
						Node artifactChildTextNode = artifactChildNode.getFirstChild();
						if (artifactChildTextNode != null) {
							if (artifactChildNode.getNodeName().equals("lockingEnabled")) {
								lockingEnabled = Boolean.valueOf(artifactChildTextNode.getNodeValue());
							} else if (artifactChildNode.getNodeName().equals("artifactPath")) {
								artifactPath = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("projectName")) {
								projectName = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("requestor")) {
								lockRequestor = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("lockAcquired")) {
								lockAcquired = Boolean.valueOf(artifactChildTextNode.getNodeValue());
							} else if (artifactChildNode.getNodeName().equals("lockOwner")) {
								lockOwner = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("fileExtension")) {
								fileExtension = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("artifactType")) {
								artifactType = artifactChildTextNode.getNodeValue();
							}
						}	
					}	
				}
				lockDataItem.setArtifactPath(projectName + artifactPath);
				lockDataItem.setLockRequestor(lockRequestor);
				lockDataItem.setLockAcquired(lockAcquired);
				lockDataItem.setLockOwner(lockOwner);
				lockDataItem.setArtifactType(artifactType);
				lockDataItem.setFileExtension(fileExtension);
				lockDataItems.add(lockDataItem);
			}
			
			boolean isError = false;
			StringBuffer strBuff = new StringBuffer();
			if (lockingEnabled) {
				for (int i = 0; i < lockDataItems.size(); i++) {
					ArtifactLockDataItem lockDataItem = lockDataItems.get(i);
					String resourceId = lockDataItem.getArtifactPath().replace("/", "$") + CommonIndexUtils.DOT + lockDataItem.getFileExtension();
					for (int j = 0; j < this.artifactsToProcess.length; j++) {
						if (resourceId.equals(artifactsToProcess[j].getId())) {
							if (lockDataItem.isLockAcquired()) {
								artifactsToProcess[j].setLocked(true);
							} else {
								strBuff.append("\n");
								strBuff.append(lockDataItem.getArtifactPath() + " is locked by user " + lockDataItem.getLockOwner());
								isError = true;
							}
						}
					}		
				}
			} else {
				strBuff.append("\n");
				strBuff.append("Locking is not enabled");
				isError = true;								
			}
			if (isError)
				showError("Error acquiring lock : " + strBuff);

		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UNLOCK_ARTIFACT.getURL()) != -1) {
			boolean lockingEnabled = false;
			Element data = event.getData();
			NodeList artifactNodes = data.getElementsByTagName("artifactLockResponse");
			List<ArtifactLockDataItem> lockDataItems = new ArrayList<ArtifactLockDataItem>();
			for (int i = 0; i < artifactNodes.getLength(); i++) {
				Node artifactNode = artifactNodes.item(i);
				NodeList artifactChildNodes = artifactNode.getChildNodes();
				ArtifactLockDataItem lockDataItem = new ArtifactLockDataItem();
				String artifactPath = null, projectName= null, lockRequestor = null, lockOwner = null, artifactType = null, fileExtension = null;
				boolean lockReleased = false;
				for (int j = 0; j < artifactChildNodes.getLength(); j++) {
					Node artifactChildNode = artifactChildNodes.item(j);
					if (!artifactChildNode.toString().trim().isEmpty()) {
						Node artifactChildTextNode = artifactChildNode.getFirstChild();
						if (artifactChildTextNode != null) {
							if (artifactChildNode.getNodeName().equals("lockingEnabled")) {
								lockingEnabled = Boolean.valueOf(artifactChildTextNode.getNodeValue());
							} else if (artifactChildNode.getNodeName().equals("artifactPath")) {
								artifactPath = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("projectName")) {
								projectName = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("requestor")) {
								lockRequestor = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("lockReleased")) {
								lockReleased = Boolean.valueOf(artifactChildTextNode.getNodeValue());
							} else if (artifactChildNode.getNodeName().equals("lockOwner")) {
								lockOwner = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("fileExtension")) {
								fileExtension = artifactChildTextNode.getNodeValue();
							} else if (artifactChildNode.getNodeName().equals("artifactType")) {
								artifactType = artifactChildTextNode.getNodeValue();
							}
						}	
					}	
				}
				lockDataItem.setArtifactPath(projectName + artifactPath);
				lockDataItem.setLockRequestor(lockRequestor);
				lockDataItem.setLockReleased(lockReleased);
				lockDataItem.setLockOwner(lockOwner);
				lockDataItem.setArtifactType(artifactType);
				lockDataItem.setFileExtension(fileExtension);
				lockDataItems.add(lockDataItem);
			}

			boolean isError = false;
			StringBuffer strBuff = new StringBuffer();
			if (lockingEnabled) {
				for (int i = 0; i < lockDataItems.size(); i++) {
					ArtifactLockDataItem lockDataItem = lockDataItems.get(i);
					String resourceId = lockDataItem.getArtifactPath().replace("/", "$") + CommonIndexUtils.DOT + lockDataItem.getFileExtension();
					for (int j = 0; j < this.artifactsToProcess.length; j++) {
						if (resourceId.equals(artifactsToProcess[j].getId())) {
							if (lockDataItem.isLockReleased()) {
								artifactsToProcess[j].setLocked(false);
							} else {
								strBuff.append("\n");
								strBuff.append("User doesn't hold a lock on " + lockDataItem.getArtifactPath());
								artifactsToProcess[j].setLocked(false);
								isError = true;
							}
						}	
					}		
				}
			} else {
				strBuff.append("\n");
				strBuff.append("Locking is not enabled");
				isError = true;				
			}
			if (isError)
				showError("Error releasing lock : " + strBuff);			
		}
	}

}
