package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;

/**
 * @author - apsharma
 */
public class ProjectValidationHelper implements HttpSuccessHandler, HttpFailureHandler{
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	private ProjectValidationHelper() {
		
	}
	
	private static ProjectValidationHelper projectValidationHelper;
	
	
	/**
	 * Method to get the instance of class ProjectValidationHelper
	 * @return Instance of class ProjectValidationHelper
	 */
	public static ProjectValidationHelper getInstance() {
		if(projectValidationHelper == null ) {
			projectValidationHelper = new ProjectValidationHelper();
		}
		return projectValidationHelper;
	}
	
	/**
	 * Method to validate given project.
	 * 
	 * @param projectName
	 */
	public void validateProject(String projectName) {
		//TODO: take from properties file
		indeterminateProgress(globalMsgBundle.progressMessage_pleaseWait() + " " + globalMsgBundle.progressMessage_validatingProject(), false);
		addHandlers(this);
		
		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		
		request.submit(ServerEndpoints.RMS_VALIDATE.getURL());
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		if ((event.getUrl().indexOf(ServerEndpoints.RMS_VALIDATE.getURL()) == -1)) {
			showError(responseMessage);
		}
		removeHandlers(this);
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		postProblems(event.getData());
		removeHandlers(this);
		indeterminateProgress("", true);
	}
	
	/**
	 * Method to parse and display project validation errors and warnings.
	 * @param docElement
	 */
	public void postProblems(Element docElement) {
		NodeList nodes = docElement.getElementsByTagName("record");
		List<ProblemRecord> warnrecords = new ArrayList<ProblemRecord>();
		List<ProblemRecord> errorrecords = new ArrayList<ProblemRecord>();
		for(int nodeCount = 0; nodeCount < nodes.getLength() ; nodeCount++) {
			Element node = (Element)nodes.item(nodeCount);

			String projectName;
			String artifactPath;
			String artifactType;

			if(node.getElementsByTagName("artifactType").item(0) != null){

				projectName = node.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
				artifactPath = node.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();
				artifactType = node.getElementsByTagName("artifactType").item(0).getFirstChild().getNodeValue();

				if(artifactType.equalsIgnoreCase("ruletemplateinstance")){
					RuleTemplateHelper.getProblemsList(node, projectName, artifactPath, warnrecords, errorrecords);

				}else{
					DecisionTableUtils.postProjectProblems(node, projectName, artifactPath,errorrecords,warnrecords);
				}
			}else{

				projectName = node.getElementsByTagName("ownerProjectName").item(0).getFirstChild().getNodeValue();
				artifactPath = "/";
				if (node.getElementsByTagName("folder") != null) {
					String folder = node.getElementsByTagName("folder").item(0).getFirstChild().getNodeValue();
					if (folder != null && !folder.isEmpty()) {
						artifactPath += folder + "/";
					}
				}
				artifactPath += node.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();

				RuleTemplateHelper.getProblemsList(node, projectName, artifactPath, warnrecords, errorrecords);

			}
		}
		updateProblemRecords(warnrecords,errorrecords, true);
	}
}
