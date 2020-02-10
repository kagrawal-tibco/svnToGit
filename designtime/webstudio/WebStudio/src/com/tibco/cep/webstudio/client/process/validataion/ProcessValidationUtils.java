/**
 * 
 */
package com.tibco.cep.webstudio.client.process.validataion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;

/**
 * @author dijadhav
 * 
 */
public class ProcessValidationUtils {
	public static List<ProblemRecord> getProblemRecord(String projectName,
			String processName, String processArtifactPath,
			String artifactExtention, List<Serializable> validationResult,
			ProcessMessages processMessages) {

		String uri = projectName + processArtifactPath;
		String process = processName.replace("." + artifactExtention, "")
				.trim();
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		List<ProblemRecord> errorrecords = new ArrayList<ProblemRecord>();
		for (Serializable serializable : validationResult) {
			if (serializable instanceof Validation) {
				Validation validation = (Validation) serializable;
				if (null != validation) {
					String message = "";
					String type = "";
					String resourceName = "";
					switch (validation.getValidationErrorType()) {

					case NO_RESOURCE:
						resourceName = validation.getLocation()
								.replace(process + ".", "").trim();
						message = processMessages.noResourceExist(resourceName);
						type = processMessages.resourceValidationProblem();
						break;
					case NO_START_EVENT:
						resourceName = validation.getLocation()
								.replace(process + ".", "").trim();
						message = processMessages.noStartEvent(resourceName);
						type = processMessages.incorrectProcessProblem();
						break;
					case NO_END_EVENT:
						resourceName = validation.getLocation()
								.replace(process + ".", "").trim();
						message = processMessages.noEndEvent(resourceName);
						type = processMessages.incorrectProcessProblem();
						break;
					case EMPTY_PROCESS:
						message = processMessages.noUniquiedError();
						type = processMessages.invalidProcessProblem();
						break;
					case NO_FORK_RULE_FUNCTION:
						message = processMessages.emptyForkRuleFunction();
						type = processMessages.resourceValidationProblem();
						break;
					case NO_JOIN_RULE_FUNCTION:
						message = processMessages.emptyJoinRuleFunction();
						type = processMessages.resourceValidationProblem();
						break;
					case NO_MERGE_EXPRESSION:
						message = processMessages.emptyMergeExpression();
						type = processMessages.resourceValidationProblem();
						break;
					case NO_KEY_EXPRESSION:
						message = processMessages.emptyKeyExpression();
						type = processMessages.resourceValidationProblem();
						break;
					case NO_END_POINT:
						message = processMessages.noEndpoint();
						type = processMessages.resourceValidationProblem();
						break;
					case NO_SOAP_ACTION:
						message = processMessages.noSoapAction();
						type = processMessages.resourceValidationProblem();
						break;
					default:
						break;
					}
					ProblemMarker problemMarker = new ProblemMarker(
							processName, uri, projectName, processArtifactPath,
							".beprocess", type, validation.getLocation(),
							message);

					problemMarker.getAttributes().put(ProblemMarker.SEVERITY,
							ProblemEvent.ERROR);					
					ProblemRecord problemrecord = new ProblemRecord(problemMarker);
					problemrecord.setAttribute(ProblemMarker.RESOURCE, (problemMarker.getResource() == null) ? "" : problemMarker.getResource());
					problemrecord.setAttribute("ProblemType", processMessages.validationError());
					problemrecord.setAttribute("#", "error.png");
					problemrecord.setAttribute("Extension", ".beprocess");
					errorrecords.add(problemrecord);
				}
			}
		}
		return errorrecords;
	}
}
