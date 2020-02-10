/**
 * 
 */
package com.tibco.cep.studio.rms.api;

import static com.tibco.cep.security.util.AuthTokenUtils.AUTH_TOKEN_FILE;
import static com.tibco.cep.security.util.AuthTokenUtils.storeAuthToken;
import static com.tibco.cep.studio.rms.client.ArtifactsManagerClient.checkinArtifact;
import static com.tibco.cep.studio.rms.client.ArtifactsManagerClient.completeCheckinArtifact;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils.generatePatternId;
import static com.tibco.cep.studio.rms.core.utils.ArtifactsManagerUtils.getEncodedArtifactContents;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactsFactory;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.client.ArtifactsManagerClient;
import com.tibco.cep.studio.rms.model.ArtifactCommitCompletionMetaData;
import com.tibco.cep.studio.rms.model.ArtifactCommitMetaData;
import com.tibco.cep.util.StudioApi;


/**
 * @author aathalye
 *
 */
public class RMSOps {
	
	/**
	 * 
	 * @param baseURL
	 * @param username
	 * @param password
	 * @throws Exception if login failed.
	 */
	@StudioApi
	public static void authenticate(final String baseURL,
			                        final String username, 
			                        final String password) throws Exception {
		if (baseURL == null || username == null) {
			throw new IllegalArgumentException("Base URL and username are mandatory");
		}
		String tokenString  = ArtifactsManagerClient.authenticate(baseURL, username, password);
		if (tokenString  != null) {
			//Store the token in temp directory
			String tempDirLoc = System.getProperty("java.io.tmpdir");
			File tmpDir = new File(tempDirLoc);
			String authFileLocation = tmpDir.getAbsolutePath() + File.separator + AUTH_TOKEN_FILE;
			//Persist it
			AuthToken authToken = storeAuthToken(tokenString, authFileLocation);
			if (authToken == null) {
				throw new Exception("Authentication Failed");
			}
		} 
	}
	
	/**
	 * Commits an artifact to RMS.
	 * @param baseURL -> The base URL of RMS
	 * @param username -> The username for logging in.
	 * @param projectRootDir -> The local project directory (generally out of a checkout)
	 * @param artifactPath -> The path of artifact relative to project directory.
	 * @param artifactType -> The type of artifact specified by {@link ArtifactsType}
	 * @param checkinComments -> Checkin comments if present
	 * @return A request id upon successful checkin.
	 * @throws Exception
	 */
	@StudioApi
	public static Object commitArtifact(final String baseURL,
									    final String username,	
			                            final String projectRootDir,
			                            final String artifactPath,
			                            final String artifactType,
			                            final String checkinComments) throws Exception {
		//Generate a pattern id
		String patternId = generatePatternId(username);
		File projectFile = new File(projectRootDir);
		if (!projectFile.exists()) {
			throw new Exception("Project Directory " + projectRootDir + " does not exist");
		}
		if (!projectFile.isDirectory()) {
			throw new Exception("Project Directory " + projectRootDir + " does not exist");
		}
		
		final Artifact artifact = ArtifactsFactory.eINSTANCE.createArtifact();
		// Load contents
		artifact.setArtifactPath(artifactPath);
		ArtifactsType artifactsType = ArtifactsType.getByName(artifactType.toUpperCase());
		if (artifactsType == null) {
			throw new Exception("Illegal artifact type. Valid values are: decisiontable | domain | rule | rulefunction | concept | event | timeevent | scorecard | channel | metric");
		}
		String artifactExtension = artifactsType.getLiteral();
		artifact.setArtifactExtension(artifactExtension);
		//In this case selected project will be the same as the one from checkout
//		RMSRepo rmsRepo = RMSArtifactsSummaryManager.getInstance().getRMSRepoInfo(explorerProject.getName());
		//Load contents
		String artifactContents = getEncodedArtifactContents(null, projectRootDir, artifact);
		artifact.setArtifactContent(artifactContents);
		Object commitResponse = 
			sendCommitRequest(baseURL, username, projectFile, patternId, artifact);
		
		if (commitResponse != null) {
			//Send completion request
			return sendCommitCompletionRequest(baseURL, 
					                           username,
					                           projectFile,
					                           patternId,
					                           artifact,
					                           checkinComments);
		}
		return null;
	}
	
	/**
	 * Send individual checkin request
	 * @param baseURL
	 * @param username
	 * @param projectRootDir
	 * @param patternId
	 * @param artifactToCommit
	 * @return
	 * @throws Exception
	 */
	private static Object sendCommitRequest(String baseURL,
			                                String username,
			                                File projectRootDir,
			                                String patternId,
										    Artifact artifactToCommit) throws Exception {
		
		ArtifactCommitMetaData artifactCommitMetaData = new ArtifactCommitMetaData();
		artifactCommitMetaData.setArtifact(artifactToCommit);
		artifactCommitMetaData.setPatternId(patternId);
		//Always treat as new one.
		final ArtifactOperation artifactOperation = ArtifactOperation.ADD;
		artifactCommitMetaData.setArtifactOperation(artifactOperation);
		
		String projectName = projectRootDir.getName();
		Object response = checkinArtifact(username,
				                          projectName, 
				                          baseURL,
				                          artifactCommitMetaData);
		return response;
	}
	
	/**
	 * Send commit completion request
	 * @param baseURL
	 * @param username
	 * @param projectRootDir
	 * @param patternId
	 * @param artifactToCommit
	 * @param checkinComments
	 * @return
	 * @throws Exception
	 */
	private static Object sendCommitCompletionRequest(String baseURL,
								                      String username,
								                      File projectRootDir,
								                      String patternId,
										              Artifact artifactToCommit,
								                      String checkinComments) throws Exception {
		
		ArtifactCommitCompletionMetaData artifactCompletionCommitMetaData = new ArtifactCommitCompletionMetaData();
		artifactCompletionCommitMetaData.setPatternId(patternId);
		artifactCompletionCommitMetaData.setCheckinComments(checkinComments);
		List<Artifact> artifactsToCommit = new ArrayList<Artifact>(1);
		artifactsToCommit.add(artifactToCommit);
		artifactCompletionCommitMetaData.setArtifacts(artifactsToCommit);
		artifactCompletionCommitMetaData.setUsername(username);
		artifactCompletionCommitMetaData.setCheckinTime(new Date());
		
		String projectName = projectRootDir.getName();
		return completeCheckinArtifact(username,
				                       projectName,
				                       baseURL,
							           artifactCompletionCommitMetaData);
	}
	
}
