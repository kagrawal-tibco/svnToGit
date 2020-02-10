/**
 * 
 */
package com.tibco.cep.studio.rms.cli;

import static com.tibco.cep.studio.rms.api.RMSOps.authenticate;
import static com.tibco.cep.studio.rms.api.RMSOps.commitArtifact;

import java.util.Map;


/**
 * @author aathalye
 *
 */
public class RMSCommitCLI extends RMSCLI {
	
	private final static String OPERATION_COMMIT = "commit";
	
	private final static String FLAG_COMMIT_HELP = "-h"; 	// Prints help
	
	private final static String FLAG_RMS_BASEURL = "-rmsBaseURL";	// RMS Base URL
	
	private final static String FLAG_RMS_USERNAME = "-rmsUsername";	// Username for logging in to RMS
	
	private final static String FLAG_RMS_PASSWORD = "-rmsPassword";	// Password for logging in to RMS.
	
	private final static String FLAG_STUDIO_PROJECT_PATH = "-studioProjPath"; // Path of new project in Studio.
	
	private final static String FLAG_ARTIFACT_PATH = "-artifactPath";	// Path of Artifact to commit relative to project.
	
	private final static String FLAG_ARTIFACT_TYPE = "-artifactType";	// Path of Artifact to commit relative to project.
	
	private final static String FLAG_CHECKIN_COMMENTS = "-checkinComments";	// Checkin Comments.
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getFlags()
	 */
	@Override
	public String[] getFlags() {
		return new String[] { 
			FLAG_RMS_BASEURL,	
			FLAG_RMS_USERNAME, 
			FLAG_RMS_PASSWORD, 
			FLAG_STUDIO_PROJECT_PATH,
			FLAG_ARTIFACT_PATH,
			FLAG_ARTIFACT_TYPE,
			FLAG_CHECKIN_COMMENTS
		};
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getHelp()
	 */
	@Override
	public String getHelp() {
		String helpMsg = "Usage: commit " + getUsageFlags() + "\n" +
			"where, \n" +
			"	-h (optional) prints this usage \n" +
			"	-rmsBaseURL (required) The base url for RMS. \n" +
			"	-rmsUsername (required) Login password for RMS. \n" +
			"	-rmsPassword (optional) Login Password for RMS. \n" +				
			"	-studioProjPath (required) Local Project Directory. \n" +
			"	-artifactPath (required) Path Of artifact (decision table as an example) to commit relative to project directory. \n" +
			"   -artifactType (required) Type of the artifact";
		return helpMsg;
	}
	
	@Override
	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_COMMIT_HELP);
		stringBuilder.append("]");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_STUDIO_PROJECT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_RMS_BASEURL);
		stringBuilder.append(" ");
		stringBuilder.append("<Base URL For RMS>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_RMS_USERNAME);
		stringBuilder.append(" ");
		stringBuilder.append("<RMS Login Username>");
		stringBuilder.append(" ");
		stringBuilder.append("[");
		stringBuilder.append(FLAG_RMS_PASSWORD);
		stringBuilder.append(" ");
		stringBuilder.append("<RMS Login Password>");
		stringBuilder.append("]");
		stringBuilder.append(FLAG_ARTIFACT_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Path relative to project path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_ARTIFACT_TYPE);
		stringBuilder.append("<decisiontable | domain | channel | concept | event | timeevent | metric>");
		return stringBuilder.toString();
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getOperationFlag()
	 */
	@Override
	public String getOperationFlag() {
		return OPERATION_COMMIT;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#getUsage()
	 */
	@Override
	public String getOperationName() {
		return ("RMS Commit");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation(java.util.Map)
	 */
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_COMMIT_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		System.out.println("Committing Artifact...");
		//Get all arguments
		String studioProjectPath = argsMap.get(FLAG_STUDIO_PROJECT_PATH);
		if (studioProjectPath == null) {
			System.out.println(getHelp());
			return false;
		}
		String baseURL = argsMap.get(FLAG_RMS_BASEURL);
		if (baseURL == null) {
			System.out.println(getHelp());
			return false;
		}
		String username = argsMap.get(FLAG_RMS_USERNAME);
		if (username == null) {
			System.out.println(getHelp());
			return false;
		}
		String password = argsMap.get(FLAG_RMS_PASSWORD);
		
		String artifactPath = argsMap.get(FLAG_ARTIFACT_PATH);
		if (artifactPath == null) {
			System.out.println(getHelp());
			return false;
		}
		String artifactType = argsMap.get(FLAG_ARTIFACT_TYPE);
		if (artifactType == null) {
			System.out.println(getHelp());
			return false;
		}
		String checkinComments = argsMap.get(FLAG_CHECKIN_COMMENTS);
		//Login first
		//Assuming it is successful, send commit request
		try {
			System.out.printf("Attempting to authenticate with username %s and password %s", username, password);
			authenticate(baseURL, username, password);
			System.out.println();
			System.out.printf("Login for username %s successful", username);
			System.out.println();
			System.out.println();
			System.out.printf("Attempting checkin of artifact %s", artifactPath);
			System.out.println();
			Object response = 
				commitArtifact(baseURL, username, studioProjectPath, artifactPath, artifactType, checkinComments);
			
			if (response instanceof String) {
				System.out.printf("Checkin for artifact %s successful..", artifactPath);
				System.out.println();
				System.out.printf("Request Id for this checkin %s..", response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
