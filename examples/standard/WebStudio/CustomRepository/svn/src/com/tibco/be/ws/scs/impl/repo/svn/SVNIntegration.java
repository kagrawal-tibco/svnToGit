package com.tibco.be.ws.scs.impl.repo.svn;

import static com.tibco.be.ws.scs.SCSConstants.LF;

import java.io.File;

import com.tibco.be.ws.scs.SCSException;
import com.tibco.be.ws.scs.impl.repo.AbstractRepositoryIntegration;

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
	private static final String AUTHENTICATION_ERROR_CODE = "svn: E170001:";
	private static final String ADD_SUCCESS_CHARACTER = "A";
	private static final String DELETE_SUCCESS_CHARACTER = "D";
	private static final String STATUS_UNVERSIONED_CHARACTER = "?";
	private static final String REVERTED_SUCCESS_MESSAGE = "Reverted";
	private static final String UPDATE_SUCCESS_MESSAGE = "Updating";
	
	@Override
	protected void checkForAuthenticationFailure(String output) throws SCSException {
		if (output == null || output.isEmpty() || output.contains(AUTHENTICATION_ERROR_CODE)) {
    		throw new SCSException("SCS Authentication failed");
    	}
	}
	
	@Override
	protected String[] list(String repoUrl, String userName, String password, boolean recursive, String projectName,
			String updatePath) throws SCSException {
		
		StringBuilder command = new StringBuilder("list ")
				.append(repoUrl);
		
		if (projectName != null && !projectName.isEmpty()) {
			command.append("/");
    		command.append(projectName);
    		if (updatePath != null && !updatePath.isEmpty()) {
    			command.append(updatePath);
    		}
    		command.append(" ");
    		command.append("-R");
		}
		command.append(" --username ")
			.append(userName)
			.append(" --password ")
			.append(password)
			.append(" --non-interactive");
		
 		String output = execCommand(command.toString(), "UTF-8", null);
 		
 		//Split on basis of \n
 		String[] allEntries = output.split(LF);
 		return allEntries;
	}

	@Override
	protected String content(String artifactPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("cat ")
				.append(artifactPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(" --non-interactive");

		String output = execCommand(command.toString(), "UTF-8", null);
		return output;
	}

	@Override
	protected boolean checkout(String projectDirectory, String destinationDirectory, String projectName, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("checkout ")
				.append(projectDirectory)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password)
				.append(" --non-interactive");
		
    	String commandOutput = execCommand(command.toString(), "UTF-8", new File(destinationDirectory));
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.indexOf(CHECKOUT_SUCCESS_MESSAGE) != -1) ? true : false;
	}

	@Override
	protected boolean add(String artifactPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("add ")
				.append(artifactPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password);
		
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(ADD_SUCCESS_CHARACTER)) ? true : false;
	}

	@Override
	protected boolean delete(String artifactPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("delete ")
				.append(artifactPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password)
				.append(" --non-interactive");
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(DELETE_SUCCESS_CHARACTER)) ? true : false;
	}

	@Override
	protected String commit(String artifactPaths, String commitComments, String userName, String password)
			throws SCSException {
		StringBuilder command = new StringBuilder("commit")
				.append(" -m ")
				.append("\"" + commitComments + "\" ")
				.append(artifactPaths)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password)
				.append(" --non-interactive");
    	
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
    	
    	if (commandOutput != null) {
    		int index = commandOutput.indexOf(COMMITED_REVISION_STRING);
    		String commitRevision = commandOutput.substring(index + COMMITED_REVISION_STRING.length() + 1, commandOutput.length() - 2);
    		return commitRevision;
    	}
		return null;
	}

	@Override
	protected boolean revert(String artifactPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("revert ")
				.append(artifactPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password);
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.indexOf(REVERTED_SUCCESS_MESSAGE) != -1) ? true : false;
	}

	@Override
	protected boolean status(String artifactPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("status ")
				.append(artifactPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password);
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
		return  (commandOutput != null 
				&& !commandOutput.isEmpty() 
				&& commandOutput.startsWith(STATUS_UNVERSIONED_CHARACTER)) ? false : true;
	}

	@Override
	protected boolean update(String projectPath, String userName, String password) throws SCSException {
		StringBuilder command = new StringBuilder("update ")
				.append(projectPath)
				.append(" --username ")
				.append(userName)
				.append(" --password ")
				.append(password);
		String commandOutput = execCommand(command.toString() , "UTF-8", null);
		return (commandOutput != null 
				&& !commandOutput.isEmpty()
				&& commandOutput.startsWith(UPDATE_SUCCESS_MESSAGE)) ? true: false;
	}
}
