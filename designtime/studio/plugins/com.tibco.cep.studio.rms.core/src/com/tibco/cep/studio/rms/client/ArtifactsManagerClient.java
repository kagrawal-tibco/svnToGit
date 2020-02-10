/**
 * 
 */
package com.tibco.cep.studio.rms.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.security.SecurityHelper;
import com.tibco.cep.security.tokens.AuthToken;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.client.builder.impl.GetMethodBuilder;
import com.tibco.cep.studio.rms.client.builder.impl.PostMethodBuilder;
import com.tibco.cep.studio.rms.client.builder.impl.RequestBuilderImpl;
import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSConstants;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.core.utils.RequestHeaders;
import com.tibco.cep.studio.rms.model.ArtifactAuditTrailRecord;
import com.tibco.cep.studio.rms.model.ArtifactCommitCompletionMetaData;
import com.tibco.cep.studio.rms.model.ArtifactCommitMetaData;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.model.ArtifactRevisionMetadata;
import com.tibco.cep.studio.rms.model.ArtifactStatusChangeDetails;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.AccessConfigRefreshResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactCheckinHistoryResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactCheckinResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactCommitCompletionResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactContentsResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactLockResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactReviewTaskDetailsResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactRevisionsCompareResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactStatusChangeResponseProcessorImpl;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactsAuditTrailResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactsRevisionContentsResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactsTasksListResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.AuthenticationResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.CommittedArtifactDetailsResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.GenerateDeployableResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.LogoutResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ProjectArtifactsNamesResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ProjectsListResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.WorkitemDelegationResponseProcessor;
import com.tibco.cep.studio.util.StudioConfig;
import com.tibco.net.mime.Base64Codec;

/**
 * @author aathalye
 *
 */
public class ArtifactsManagerClient {
	
	private static IResponseProcessor<Object, IResponse> start;
	
	private static String baseURL;
	
	static {
		baseURL = RMSUtil.buildRMSURL();
		if (baseURL == null || baseURL.length() == 0) {
			baseURL = "http://localhost:5000/Transports/Channels/AMS_CH_ArtifactCommunicationHTTPChannel/";
		}
		start = new AuthenticationResponseProcessor<Object, IResponse>();
		IResponseProcessor<Object, IResponse> checkoutResponseProcessor = 
			new ArtifactContentsResponseProcessor<Object, IResponse>();
		start.addProcessor(checkoutResponseProcessor);
		IResponseProcessor<Object, IResponse> projectsListResponseProcessor = 
			new ProjectsListResponseProcessor<Object, IResponse>();
		checkoutResponseProcessor.addProcessor(projectsListResponseProcessor);
		IResponseProcessor<Object, IResponse> artifactsListResponseProcessor = 
			new ProjectArtifactsNamesResponseProcessor<Object, IResponse>();
		projectsListResponseProcessor.addProcessor(artifactsListResponseProcessor);
		IResponseProcessor<Object, IResponse> checkinCompletionResponseProcessor = 
			new ArtifactCommitCompletionResponseProcessor<Object, IResponse>();
		artifactsListResponseProcessor.addProcessor(checkinCompletionResponseProcessor);
		IResponseProcessor<Object, IResponse> tasksIdsListResponseProcessor = 
			new ArtifactsTasksListResponseProcessor<Object, IResponse>();
		checkinCompletionResponseProcessor.addProcessor(tasksIdsListResponseProcessor);
		IResponseProcessor<Object, IResponse> taskDetailsResponseProcessor = 
			new ArtifactReviewTaskDetailsResponseProcessor<Object, IResponse>();
		tasksIdsListResponseProcessor.addProcessor(taskDetailsResponseProcessor);
		IResponseProcessor<Object, IResponse> auditTrailResponseProcessor = 
			new ArtifactsAuditTrailResponseProcessor<Object, IResponse>();
		taskDetailsResponseProcessor.addProcessor(auditTrailResponseProcessor);
		IResponseProcessor<Object, IResponse> lockResponseProcessor = 
			new ArtifactLockResponseProcessor<Object, IResponse>();
		auditTrailResponseProcessor.addProcessor(lockResponseProcessor);
		IResponseProcessor<Object, IResponse> artifactStatusChangeResponseProcessor = 
			new ArtifactStatusChangeResponseProcessorImpl<Object, IResponse>();
		lockResponseProcessor.addProcessor(artifactStatusChangeResponseProcessor);
		IResponseProcessor<Object, IResponse> committedArtifactDetailsResponseProcessor = 
			new CommittedArtifactDetailsResponseProcessor<Object, IResponse>();
		artifactStatusChangeResponseProcessor.addProcessor(committedArtifactDetailsResponseProcessor);
		IResponseProcessor<Object, IResponse> logoutResponseProcessor = 
			new LogoutResponseProcessor<Object, IResponse>();
		committedArtifactDetailsResponseProcessor.addProcessor(logoutResponseProcessor);
		IResponseProcessor<Object, IResponse> checkinResponseProcessor = 
			new ArtifactCheckinResponseProcessor<Object, IResponse>();
		logoutResponseProcessor.addProcessor(checkinResponseProcessor);
		IResponseProcessor<Object, IResponse> accessConfigRefreshProcessor = 
			new AccessConfigRefreshResponseProcessor<Object, IResponse>();
		checkinResponseProcessor.addProcessor(accessConfigRefreshProcessor);
		IResponseProcessor<Object, IResponse> generateDeployableResponseProcessor = 
			new GenerateDeployableResponseProcessor<Object, IResponse>();
		accessConfigRefreshProcessor.addProcessor(generateDeployableResponseProcessor);
		IResponseProcessor<Object, IResponse> checkinHistoryResponseProcessor = 
			new ArtifactCheckinHistoryResponseProcessor<Object, IResponse>();
		generateDeployableResponseProcessor.addProcessor(checkinHistoryResponseProcessor);
		IResponseProcessor<Object, IResponse> revisionDiffResponseProcessor = 
			new ArtifactRevisionsCompareResponseProcessor<Object, IResponse>();
		checkinHistoryResponseProcessor.addProcessor(revisionDiffResponseProcessor);
		IResponseProcessor<Object, IResponse> revisionContentsResponseProcessor = 
			new ArtifactsRevisionContentsResponseProcessor<Object, IResponse>();
		revisionDiffResponseProcessor.addProcessor(revisionContentsResponseProcessor);
		IResponseProcessor<Object, IResponse> workitemDelegationResponseProcessor = 
			new WorkitemDelegationResponseProcessor<Object, IResponse>();
		revisionContentsResponseProcessor.addProcessor(workitemDelegationResponseProcessor);
	}
	
	/**
	 * @param baseURL
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String authenticate(final String baseURL,
			                          final String username, 
			                          String password) throws Exception {

		if (username == null || username.length() == 0) {
			throw new IllegalArgumentException("Username is mandatory");
		}
		password = (password == null) ? "" : password;
		String encoded = Base64Codec.encodeBase64(password.getBytes("UTF-8"), true);
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setUsername(username);
		requestHeaders.setPassword(encoded);
		
		return (String)sendGetRequest(baseURL,
				requestHeaders,
				"AMS_DS_LoginDestination",
                RMSConstants.LOGIN_REQ_EVENT,
                "",
                OperationType.LOGIN,
                new NullProgressMonitor());
	}
	
	/**
	 * Fetch a list of Artifacts to checkout/update
	 * @param loggedInUser
	 * @param projectName
	 * @param updatePath -> The update case
	 * @param url
	 * @param isDM
	 * @return
	 * @throws Exception
	 */
	
	public static Object fetchAllArtifacts(String loggedInUser,
			                               String projectName, 
			                               String updatePath, 
			                               String url,
			                               boolean isDM) throws Exception {
		
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoggedInUserName(loggedInUser);
		//Is this DM?
		boolean isDecisionManager = isDM;
		if (isDecisionManager) {
			requestHeaders.setStandaloneDecisionManager(isDecisionManager);
		}
		requestHeaders.setSelectedProject(projectName);
		//Update case. If null this is equivalent to checkout
		requestHeaders.setUpdatePath(updatePath);
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendGetRequest(url,
				requestHeaders,
                "AMS_DS_RetrieveArtifactNamesDestination",
                "AMS_E_RetrieveArtifactNamesRequestEvent",
                "",
                OperationType.PROJECT_ARTIFACTS_LIST,
                new NullProgressMonitor());

	}
	
	/**
	 * Fetch latest Access Control Config file for the specified project.
	 * @param loggedInUser
	 * @param projectName
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Object refreshAccessConfig(String loggedInUser,
			                                 String projectName, 
			                                 String url) throws Exception {

		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoggedInUserName(loggedInUser);
		requestHeaders.setSelectedProject(projectName);
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendGetRequest(url, requestHeaders,
				"AMS_DS_AccessConfigFileRefreshDestination",
				"AMS_E_RefreshAccessConfigFileRequestEvent", "",
				OperationType.ACCESS_CONFIG_REFRESH,
				new NullProgressMonitor());

	}
	
	/**
	 * 
	 * @param loggedInUser
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Object performLogout(String loggedInUser,
			                           String url) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoggedInUserName(loggedInUser);
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendGetRequest(url,
							  requestHeaders,
				              "AMS_DS_LogoutDestination",
				              "AMS_DS_LogoutRequestEvent",
				              "",
				              OperationType.LOGOUT,
				              new NullProgressMonitor());
	}
	
	/**
	 * Generate deployable on RMS side. 
	 * <p>
	 * Typically used to build ear/generate Classes 
	 * </p>
	 * @param projectName
	 * @param version
	 * @param generateDebugInfo
	 * @param includeServiceVars
	 * @param generateClassesOnly
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static Object generateDeployable(String projectName,
			                                double version,
			                                boolean generateDebugInfo,
			                                boolean includeServiceVars,
			                                boolean generateClassesOnly,
			                                String url,
			                                IProgressMonitor monitor) throws Exception {
		
		monitor.beginTask("Processing Request Headers.", 10);
		monitor.subTask("Setting parameters.");

		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setArchiveVersion(version);
		requestHeaders.setGenerateDebugInfo(generateDebugInfo);
		requestHeaders.setIncludeServiceVars(includeServiceVars);
		requestHeaders.setSelectedProject(projectName);
		requestHeaders.setGenerateClassesOnly(generateClassesOnly);
		requestHeaders.setLoggedInUserName(AuthTokenUtils.getLoggedinUser());
		requestHeaders.setLoginToken(getAuthToken());
		
		monitor.worked(2);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		monitor.subTask("Request sent.");
		
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		
		Object object = sendGetRequest(url,
						               requestHeaders,
					                   "AMS_DS_GenerateDeployableDestination",
					                   "AMS_E_GenerateDeployableRequestEvent",
					                   "",
					                   OperationType.GENERATE_DEPLOYABLE, 
					                   new SubProgressMonitor(monitor, 8));
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		
		monitor.worked(8);
		monitor.subTask("Deployable Object fetched.");
		
		monitor.done();
		
		return object;
	}
	
	/**
	 * Fetch list of revision ids each mapping to a task, conditionally clear Approved rules
	 * @param url
	 * @param role -> Roles separated by <b>;</b>
	 * @param clearApprovedTasks -> whether to clear approved rules 
	 * @return
	 * @throws Exception
	 */
	
	public static ArtifactReviewTaskSummary fetchTasksList(String url, String role, boolean clearApprovedTasks) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setRequestedRoleString(role);
		requestHeaders.setClearApprovedTasks(clearApprovedTasks);
		return (ArtifactReviewTaskSummary)sendGetRequest(url,
							              requestHeaders,
							              "AMS_DS_FetchTasksListDestination",
							              "AMS_E_TaskListRequestEvent",
							              "",
							              OperationType.WORKLISTS_IDS,
							              new NullProgressMonitor());
	}
	

	/**
	 * Compare 2 revisions
	 * <p>
	 * Methodology:
	 * <li>
	 * If both revision ids are absent, compare local copy versus master copy
	 * specified by revision 2.
	 * </li>
	 * <p></p>
	 * <li>
	 * If any one set of parameters (with same suffix) are absent, compare revision specified by versus master copy
	 * </li>
	 * <p></p>
	 * <li>
	 * If parameters for both suffixes are specified, compare the 2 revisions. 
	 * </li>
	 * @param url
	 * @param projectName
	 * @param diffArtifactPath1
	 * @param diffArtifactExtension1
	 * @param diffArtifactPath2
	 * @param diffArtifactExtension2
	 * @param revisionId1
	 * @param revisionId2
	 * @return
	 * @throws Exception
	 */
	public static Object compareRevisions(String url, 
			                              String projectName, 
										  String diffArtifactPath1,	
										  String diffArtifactExtension1,
										  String diffArtifactPath2,	
										  String diffArtifactExtension2,
										  long revisionId1,
			                              long revisionId2) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setSelectedProject(projectName);
		requestHeaders.setDiffRevisionId1(revisionId1);
		requestHeaders.setDiffRevisionId2(revisionId2);
		requestHeaders.setDiffArtifactPath1(diffArtifactPath1);
		requestHeaders.setDiffArtifactPath2(diffArtifactPath2);
		requestHeaders.setDiffArtifactExtension1(diffArtifactExtension1);
		requestHeaders.setDiffArtifactExtension2(diffArtifactExtension2);
		return sendGetRequest(url,
							  requestHeaders,
							  "AMS_DS_ArtifactsDiffDestination",
							  "AMS_E_ArtifactsDiffRequestEvent",
							  "",
							  OperationType.COMPARE_REVISIONS,
							  new NullProgressMonitor());
	}
	
	/**
	 * Send request for locking an artifact
	 * @param url
	 * @param username -> Username requesting lock
	 * @param project -> The name of the project containing the artifact
	 * @param artifactPath -> Artifact to be locked
	 * @param requestComments -> Comments sent by lock requestor.
	 * @return Request id for lock or error message
	 * @throws Exception
	 */
	public static Object lockArtifact(String url, 
                                      String username,
                                      String project,
                                      String artifactPath,
                                      String requestComments) throws Exception {
		
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setUsername(username);
		requestHeaders.setSelectedProject(project);
		requestHeaders.setArtifactPath(artifactPath);
		
		StringEntity requestEntity = 
			new StringEntity(requestComments, ContentType.create("text/plain", "UTF-8"));
		
		return sendPostRequest(url,
				               requestHeaders,
				               requestEntity,
				               "AMS_DS_LockRequestDestination",
				               "AMS_E_ArtifactLockRequestEvent",
				               "",
				               OperationType.LCK_REQUEST);
	}
	
	/**
	 * Delegate workitem specified by taskId to one or more roles. 
	 * 
	 * @param url
	 * @param taskId
	 * @param delegationRoles
	 * @return a message indicating delegation to specified roles.
	 * @throws Exception
	 */
	public static Object delegateWorkItem(String url,
			                              String taskId,
			                              String[] delegationRoles) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setRevisionId(taskId);
		requestHeaders.setLoggedInUserName(AuthTokenUtils.getLoggedinUser());
		requestHeaders.setDelegationRolesString(BEStringUtilities.join(delegationRoles, ";"));
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendGetRequest(url,
	                          requestHeaders,
	                          "AMS_DS_WorkitemDelegationDestination",
	                          "",
	                          "AMS_E_WorkitemDelegationRequestEvent",
	                          OperationType.WORKITEM_DELEGATION,
	                          new NullProgressMonitor());
	}
	
	/**
	 * 
	 * @param url
	 * @param project
	 * @param artifactPath
	 * @return
	 * @throws Exception
	 */
	public static Object fetchHistory(String url, 
                                      String project,
                                      String artifactPath) throws Exception {
		
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setSelectedProject(project);
		requestHeaders.setArtifactPath(artifactPath);
		
		return sendGetRequest(url,
				              requestHeaders,
				              "AMS_DS_ArtifactCheckinHistoryDestination",
				              "AMS_E_ArtifactCheckinLogRequestEvent",
				              "",
				              OperationType.CHECKIN_HISTORY,
				              new NullProgressMonitor());
	}
	
	/**
	 * Fetch details of a revision id
	 * @param url
	 * @param loggedInUserName
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<CommittedArtifactDetails> fetchTaskDetails(String url, String loggedInUserName, String taskId) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setRevisionId(taskId);
		requestHeaders.setLoggedInUserName(loggedInUserName);
		requestHeaders.setLoginToken(getAuthToken());
		return (List<CommittedArtifactDetails>)sendGetRequest(url,
				              requestHeaders,
				              "AMS_DS_FetchTaskDetailsDestination",
				              "",
				              "AMS_E_TaskDetailsRequestEvent",
				              OperationType.WORK_DETAILS,
				              new NullProgressMonitor());

		
	}
	
	/**
	 * Fetch details of an artifact inside a revision.
	 * @param url
	 * @param loggedInUserName
	 * @param taskId
	 * @param artifactPath
	 * @return
	 * @throws Exception
	 */
	public static CommittedArtifactDetails refreshCommittedArtifactDetails(String url, 
			                                                String loggedInUserName, 
			                                                String taskId,
			                                                String artifactPath) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setRevisionId(taskId);
		requestHeaders.setLoggedInUserName(loggedInUserName);
		requestHeaders.setArtifactPath(artifactPath);
		requestHeaders.setLoginToken(getAuthToken());
		return (CommittedArtifactDetails)sendGetRequest(url,
				              requestHeaders,
				              "AMS_DS_RefreshCommittedArtifactDetailsDestination",
				              "",
				              "AMS_E_RefreshCommittedArtifactDetailsRequestEvent",
				              OperationType.COMMITTED_ARTIFACT_DETAILS,
				              new NullProgressMonitor());

		
	}
	
	/**
	 * Return list of projects served by RMS
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String[] fetchServedProjects(String url) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoginToken(getAuthToken());
		return (String[])sendGetRequest(url,
				                        requestHeaders,
				                        "AMS_DS_RetrieveProjectsListDestination",
				                        "AMS_E_ProjectsListRequestEvent",
				                        "",
				                        OperationType.PROJECTS_LIST,
				                        new NullProgressMonitor());
		
	}
	
	/**
	 * Change status of one or more artifacts
	 * 
	 * @param url
	 * @param loggedInUserName
	 * @param artifactsChangeSummary
	 * @throws Exception
	 */
	public static Object changeArtifactStatus(String url,
											  String loggedInUserName,
			                                  List<ArtifactStatusChangeDetails> artifactsChangeSummary) throws Exception {
		RequestBuilder requestbuilder = 
			new RequestBuilderImpl(OperationType.STATUS_CHANGE);
		
		String requestPart = 
			(String)requestbuilder.buildRequest(artifactsChangeSummary);
		
		
		StringEntity requestEntity = 
			new StringEntity(requestPart, ContentType.create("text/xml", "UTF-8"));
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoggedInUserName(loggedInUserName);
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendPostRequest(url,
		        requestHeaders,
		        requestEntity,
		        "AMS_DS_ArtifactStatusChangeDestination",
		        "",
		        "AMS_E_ArtifactStatusChangeRequestEvent",
		        OperationType.STATUS_CHANGE);
	}
	
	@SuppressWarnings("unchecked")
	public static List<ArtifactAuditTrailRecord> fetchAuditTrail(String url,
			                                                     String patternId,
			                                                     String artifactPath) throws Exception {
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setArtifactPath(artifactPath);
		requestHeaders.setPatternId(patternId);
		
		return (List<ArtifactAuditTrailRecord>)sendGetRequest(url,
												              requestHeaders,
												              "AMS_DS_AuditTrailDestination",
												              "",
												              "AMS_E_AuditTrailRequestEvent",
												              OperationType.AUDIT_TRAIL,
												              new NullProgressMonitor());
	}
	
	/**
	 * Fetch contents of an artifact from history view for a certain revision.
	 * @param url
	 * @param project
	 * @param artifactCommitMetaData
	 * @return
	 * @throws Exception
	 */
	public static ArtifactRevisionMetadata fetchRevisionArtifact(String url, 
                                                                 String project, 
                                                                 ArtifactRevisionMetadata artifactRevisionMetadata) throws Exception {
		RequestBuilder requestbuilder = 
			new RequestBuilderImpl(OperationType.REVISION_CONTENTS);
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setLoggedInUserName(AuthTokenUtils.getLoggedinUser());
		requestHeaders.setLoginToken(getAuthToken());
		String requestPart = 
			(String)requestbuilder.buildRequest(artifactRevisionMetadata);
		
		StringEntity requestEntity = 
			new StringEntity(requestPart, ContentType.create("text/xml", "UTF-8"));
		
		return (ArtifactRevisionMetadata)sendPostRequest(url,
				               requestHeaders,
		                       requestEntity,
		                       "AMS_DS_RetrieveRevisionArtifactDestination",
		                       "www.tibco.com/be/ontology/Approval/Events/ArtifactEvents/History/AMS_E_ArtifactContentsAtRevisionRequestEvent",
		                       "AMS_E_ArtifactContentsAtRevisionRequestEvent",
		                       OperationType.REVISION_CONTENTS);
	}
	
	/**
	 * Fetch contents of selected artifact upon checkout/update.
	 * @param loggedInUsername
	 * @param project
	 * @param url
	 * @param artifact -> The contents of which to fetch
	 * @return
	 * @throws Exception
	 */
	public static Artifact fetchSelectedArtifact(String loggedInUsername,
			                                     String project, 
			                                     String url, 
			                                     Artifact artifact) throws Exception {
		RequestBuilder requestbuilder = 
			new RequestBuilderImpl(OperationType.ARTIFACT_CONTENTS);
		
		String requestPart = 
			(String)requestbuilder.buildRequest(artifact);
		
		StringEntity requestEntity = 
			new StringEntity(requestPart, ContentType.create("text/xml", "UTF-8"));
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setSelectedProject(project);
		requestHeaders.setLoggedInUserName(loggedInUsername);
		requestHeaders.setLoginToken(getAuthToken());
		
		return (Artifact)sendPostRequest(url,
				        requestHeaders,
				        requestEntity,
				        "AMS_DS_RetrieveSelectedArtifactsDestination",
				        "",
				        "AMS_E_RetrieveSelectedArtifactsRequestEvent",
				        OperationType.ARTIFACT_CONTENTS);

	}
	
	/**
	 * 
	 * @param project
	 * @param url
	 * @param artifact -> The contents of which to fetch
	 * @return
	 * @throws Exception
	 */
	public static Object checkinArtifact(String loggedInUser,
			                             String project, 
			                             String url,
			                             ArtifactCommitMetaData artifactCommitMetaData) throws Exception {
		RequestBuilder requestbuilder = 
			new RequestBuilderImpl(OperationType.CHECKIN_ARTIFACT);
		
		String requestPart = 
			(String)requestbuilder.buildRequest(artifactCommitMetaData);
		
		StringEntity requestEntity = 
			new StringEntity(requestPart, ContentType.create("text/xml", "UTF-8"));
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setSelectedProject(project);
		requestHeaders.setLoggedInUserName(loggedInUser);
		requestHeaders.setPatternId(artifactCommitMetaData.getPatternId());
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendPostRequest(url,
				        requestHeaders,
				        requestEntity,
				        "AMS_DS_CommitArtifactsDestination",
				        "www.tibco.com/be/ontology/Approval/Events/ArtifactEvents/Commit/AMS_E_CheckinRequestEvent",
				        "AMS_E_CheckinRequestEvent",
				        OperationType.CHECKIN_ARTIFACT);
	}
	
	/**
	 * Send checkin completion request
	 * @param project
	 * @param url
	 * @param artifactCommitCompletionMetaData -> The contents of which to fetch
	 * @return
	 * @throws Exception
	 */
	public static Object completeCheckinArtifact(String loggedInUser,
			                                     String project, 
			                                     String url,
			                                     ArtifactCommitCompletionMetaData artifactCommitCompletionMetaData) throws Exception {
		
		RequestBuilder requestbuilder = 
			new RequestBuilderImpl(OperationType.CHECKIN_COMPLETE);
		
		String requestPart = 
			(String)requestbuilder.buildRequest(artifactCommitCompletionMetaData);
		
		StringEntity requestEntity = 
			new StringEntity(requestPart, ContentType.create("text/xml", "UTF-8"));
		RequestHeaders requestHeaders = new RequestHeaders();
		requestHeaders.setSelectedProject(project);
		requestHeaders.setLoggedInUserName(loggedInUser);
		requestHeaders.setPatternId(artifactCommitCompletionMetaData.getPatternId());
		requestHeaders.setLoginToken(getAuthToken());
		
		return sendPostRequest(url,
				        requestHeaders,
				        requestEntity,
				        "AMS_DS_CommitArtifactsDestination",
				        "www.tibco.com/be/ontology/Approval/Events/ArtifactEvents/Commit/AMS_E_CheckinCompletionEvent",
				        "AMS_E_CheckinCompletionEvent",
				        OperationType.CHECKIN_COMPLETE);
	}
	
//	public static void main(String[] r) throws Exception {
////		StudioConfig.init();
//		baseURL = "http://localhost:7777/Transports/Channels/AMS_CH_ArtifactCommunicationHTTPChannel/";
//		compareRevisions(baseURL, "CreditCardApplication", "/Virtual_RF/BankUser_VirtualRuleFunction", "rulefunction", "/Virtual_RF/BankUser_VirtualRuleFunction", "rulefunction", 10007, 10009);
////		fetchHistory(baseURL, "CreditCardApplication", "/Virtual_RF/Application_VirtualRuleFunction");
////		refreshAccessConfig("admin", "CreditCardApplication", baseURL);
////		generateDeployable("CreditCardApplication", 1.0, true, true, baseURL);
//	}
	
	private static Object sendPostRequest(String url,
                                          RequestHeaders requestHeaders,
                                          StringEntity requestEntity,
                                          String destinationName,
                                          String eventNS,
                                          String eventName,
                                          OperationType operationType) throws Exception {
		if (url == null) {
			url = baseURL;
		}
		String requestURL = RMSUtil.createRequestURL(url,
				null,
				destinationName);
		
		int timeoutValue = 
			Integer.parseInt(StudioConfig.getInstance().getProperty("rms.request.timeout", "0"));
		
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", timeoutValue);
		//Allow for query string/headers to contain non-ascii characters.
		client.getParams().setParameter("http.protocol.element-charset", "UTF-8");
		// Create a method instance.
		MethodBuilder<HttpPost> builder = new PostMethodBuilder();
				
		HttpPost method = builder.buildMethod(requestURL,
				eventName, eventNS, requestHeaders, requestEntity);
		try {
			// Execute the method.
			HttpResponse httpResponse = client.execute(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				// This should not come for get
//			}
			InputStream responseBody = httpResponse.getEntity().getContent();
			IResponse response = 
				start.processResponse(operationType, httpResponse, responseBody);
			if (response != null) {
				return response.getResponseObject();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return null;
	}
	
	private static Object sendGetRequest(String url,
			                             RequestHeaders requestHeaders,
			                             String destinationName,
			                             String eventNS,
			                             String eventName,
			                             OperationType operationType,
			                             IProgressMonitor monitor) throws Exception {
		if (url == null) {
			url = baseURL;
		}
		monitor.beginTask("Processing Request.", 10);
		monitor.subTask("Initialize HTTP client.");

		String property = StudioConfig.getInstance().getProperty("rms.request.timeout", "0");
		if (property == null) {
			property = "0";
		}
		int timeoutValue = 
			Integer.parseInt(property);
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter("http.socket.timeout", timeoutValue);
		monitor.worked(1);
		
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}

		monitor.subTask("Create request URL.");
		String requestURL = RMSUtil.createRequestURL(url,
													 null,
													 destinationName);
		monitor.worked(1);		
		
		monitor.subTask("Create methods for request URL.");
		// Create a method instance.
		MethodBuilder<HttpGet> builder = new GetMethodBuilder();
		HttpGet method = builder.buildMethod(requestURL,
				eventName, eventNS, requestHeaders, null);
		monitor.worked(1);	
		try {
			monitor.subTask("Receiving response.");
			// Execute the method.
			HttpResponse httpResponse = client.execute(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				// This should not come for get
//			}
			
			if (monitor.isCanceled()) {
				throw new CoreException(Status.CANCEL_STATUS);
			}
			
			InputStream responseBody = httpResponse.getEntity().getContent();
//			System.out.println(getStringFromInputStream(responseBody));
			
			IResponse response = start.processResponse(operationType, httpResponse, responseBody);
			if (response != null) {
				return response.getResponseObject();
			}
			monitor.worked(7);	
			monitor.subTask("Response received.");
		} catch (Exception e) {
			throw e;
			
		} finally {
			method.releaseConnection();
			monitor.done();
		}
		return null;
	}
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
 

	
	/**
	 * @return Auth Token of the loggedin User
	 * The token is encoded with Base64 & URL encoding
	 */
	private static String getAuthToken() {
		String tokenString = null;
		try {
			AuthToken token = AuthTokenUtils.getToken();
			tokenString = SecurityHelper.serializeAuthToken(token, false);
			byte[] bytes = tokenString.getBytes("UTF-8");
			tokenString = Base64Codec.encodeBase64(bytes);
			tokenString = URLEncoder.encode(tokenString, "UTF-8");
		} catch (Exception e) {
			RMSCorePlugin.log(e);
			return null;
		}
		
		return tokenString;
	}
}