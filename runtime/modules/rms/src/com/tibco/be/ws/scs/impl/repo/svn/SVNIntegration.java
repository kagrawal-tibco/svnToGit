package com.tibco.be.ws.scs.impl.repo.svn;

import static com.tibco.be.ws.scs.SCSConstants.LF;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.impl.repo.AbstractRepositoryIntegration;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * SVN integration class. This class is responsible in connecting to the SVN repository and 
 * executing various SVN commands in the background.
 * 
 * @author TIBCO
 */
public class SVNIntegration extends AbstractRepositoryIntegration {
	
	// SVN specific messages
	private static final String CHECKOUT_SUCCESS_MESSAGE = "Checked out revision";
	private static final String COMMITED_REVISION_STRING = "Committed revision";
	private static final String AUTHENTICATION_ERROR_CODE = "svn: E215004:";
	private static final String AUTHORIZATION_ERROR_CODE = "svn: E170001:";
	private static final String ADD_SUCCESS_CHARACTER = "A";
	private static final String DELETE_SUCCESS_CHARACTER = "D";
	private static final String STATUS_UNVERSIONED_CHARACTER = "?";
	private static final String REVERTED_SUCCESS_MESSAGE = "Reverted";
	private static final String UPDATE_SUCCESS_MESSAGE = "Updating";
	
	@Override
	protected void checkForAuthenticationFailure(String output) throws SCSException {
		if (output != null && !output.isEmpty() && (output.contains(AUTHENTICATION_ERROR_CODE) || output.contains(AUTHORIZATION_ERROR_CODE))) {
    		throw new SCSException("SCS Authentication failed");
    	}
	}
	
	private void addCredentials(List<String> command, String userName, String password, boolean nonInteractive) {
		command.add("--username");
		command.add(userName);
		command.add("--password");
		command.add(password);
		if (nonInteractive) command.add("--non-interactive");
	}
	
	@Override
	protected String[] list(String repoUrl, String userName, String password, boolean recursive, String projectName,
			String updatePath) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("list");
		command.add(repoUrl);
		
		if (projectName != null && !projectName.isEmpty()) {
			String subPath = repoUrl + "/" + projectName;
    		if (updatePath != null && !updatePath.isEmpty()) {
    			subPath += updatePath;
    		}
    		command.set(1, subPath);
    		command.add("-R");
		}
		
		addCredentials(command, userName, password, true);
		
 		String output = execCommand(command, "UTF-8", null);
 		
 		//Split on basis of \n
 		String[] allEntries = output.split(LF);
 		return allEntries;
	}

	@Override
	protected String content(String artifactPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("cat");
		command.add(artifactPath);
		
		addCredentials(command, userName, password, true);

		String output = execCommand(command, "UTF-8", null);
		return output;
	}

	@Override
	protected boolean checkout(String projectDirectory, String destinationDirectory, String projectName, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("checkout");
		command.add(projectDirectory);
		
		addCredentials(command, userName, password, true);
		
    	String commandOutput = execCommand(command, "UTF-8", new File(destinationDirectory));
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.indexOf(CHECKOUT_SUCCESS_MESSAGE) != -1) ? true : false;
	}

	@Override
	protected boolean add(String artifactPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("add");
		command.add(artifactPath);
		
		addCredentials(command, userName, password, false);
		
		String commandOutput = execCommand(command , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(ADD_SUCCESS_CHARACTER)) ? true : false;
	}

	@Override
	protected boolean delete(String artifactPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("delete");
		command.add(artifactPath);
		
		addCredentials(command, userName, password, true);
		
		String commandOutput = execCommand(command , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(DELETE_SUCCESS_CHARACTER)) ? true : false;
	}

	@Override
	protected String commit(String artifactPaths, String commitComments, String userName, String password)
			throws SCSException {
		
		List<String> command = new ArrayList<String>();
		command.add("commit");
		command.add("-m");
		command.add(commitComments);
		command.add(artifactPaths);
		
		addCredentials(command, userName, password, true);

		String commandOutput = execCommand(command , "UTF-8", null);
    	
    	if (commandOutput != null && !commandOutput.isEmpty()) {
    		int index = commandOutput.indexOf(COMMITED_REVISION_STRING);
    		String commitRevision = commandOutput.substring(index + COMMITED_REVISION_STRING.length() + 1, commandOutput.length() - 2);
    		return commitRevision;
    	} else {
    		LOGGER.log(Level.WARN, "Commit returned null, possibly there is no change in one of the artifacts[%s]", artifactPaths);
    	}
		return null;
	}

	@Override
	protected boolean revert(String artifactPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("revert");
		command.add(artifactPath);
		
		addCredentials(command, userName, password, false);
		
		String commandOutput = execCommand(command , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.indexOf(REVERTED_SUCCESS_MESSAGE) != -1) ? true : false;
	}

	@Override
	protected boolean status(String artifactPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("status");
		command.add(artifactPath);
		
		addCredentials(command, userName, password, false);
		
		String commandOutput = execCommand(command , "UTF-8", null);
		return  (commandOutput != null 
				&& !commandOutput.isEmpty() 
				&& commandOutput.startsWith(STATUS_UNVERSIONED_CHARACTER)) ? false : true;
	}

	@Override
	protected boolean update(String projectPath, String userName, String password) throws SCSException {
		List<String> command = new ArrayList<String>();
		command.add("update");
		command.add(projectPath);
		
		addCredentials(command, userName, password, false);
		
		String commandOutput = execCommand(command , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(UPDATE_SUCCESS_MESSAGE)) ? true: false;
	}
	
	 public static void main(String[] r) throws Exception {
//	    	String url = "https://svn.tibco.com/svn/be/trunk/experimental/projects";
//	    	String repoURL = "/Users/vpatil/Installations/tibco/BE5.4/01-06-17/be/5.4/rms/shared";
//	    	
//	    	String userName = "vpatil";
//	    	String password = "pwd";
//	    	System.out.println("Password - " + password);
//	    	
//	    	SVNIntegration scsIntegration = new SVNIntegration();
//	    	scsIntegration.setSCSCommandPath("svn");  	
//	    	
//	    	String[] projectList = scsIntegration.list(url, userName, password, false, null, null);
//	    	System.out.println("Total Project available - " + projectList.length);
//	    	
//	    	String[] artifactList = scsIntegration.list(url, userName, password, true, "CreditCardApplication", null);
//	    	for (String s : artifactList) {
//	    		System.out.println(s);
//	    	}
//	    	System.out.println("Total Artifacts for CreditCardApplication - " + artifactList.length);
//	    	
//	    	scsIntegration.checkout(url+"/CreditCardApplication", repoURL, userName, password);
//	    	
//	    	scsIntegration.add("/Users/vpatil/Installations/tibco/BE5.4/01-06-17/be/5.4/rms/shared/CreditCardApplication/Virtual_RF/Applicant_Simple_New.rulefunctionimpl", userName, password);
//	    	
//	    	scsIntegration.delete("/Users/vpatil/Installations/tibco/BE5.4/01-06-17/be/5.4/rms/shared/CreditCardApplication/Virtual_RF/Applicant_Simple.rulefunctionimpl", userName, password);
//	    	
//	    	scsIntegration.revert("/Users/vpatil/Installations/tibco/BE5.4/01-06-17/be/5.4/rms/shared/CreditCardApplication/Virtual_RF/Applicant_Simple.rulefunctionimpl", userName, password);
//	    	
//	    	scsIntegration.update("/Users/vpatil/Installations/tibco/BE5.4/01-06-17/be/5.4/rms/shared/CreditCardApplication", userName, password);
	    }
}
