/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.studio.rms.client.MethodBuilder;
import com.tibco.cep.studio.rms.core.utils.RMSConstants;
import com.tibco.cep.studio.rms.core.utils.RequestHeaders;

/**
 * @author aathalye
 *
 */
public class GetMethodBuilder implements MethodBuilder<HttpGet> {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GetMethodBuilder.class);

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.MethodBuilder#buildMethod(java.lang.String, java.lang.String, java.lang.String, com.tibco.cep.mgmtserver.rms.utils.RequestHeaders, org.apache.commons.httpclient.methods.RequestEntity)
	 */
	public HttpGet buildMethod(String url, String eventName,
			String eventNS, RequestHeaders reqHeaders,
			StringEntity requestEntity) {
		HttpGet method = new HttpGet(url);
		if (requestEntity != null) {
			throw new IllegalArgumentException("payload cannot be attached to GET requests");
		}
		if (eventName == null) {
			throw new IllegalArgumentException("Event name cannot be null");
		}
		if (eventNS == null) {
			eventNS = "";
		}
		
		// collection for request parameters
		List<NameValuePair> nvPairs = new ArrayList<NameValuePair>();
		
		NameValuePair nvp1 = new BasicNameValuePair(RMSConstants.MESSAGE_HEADER_NAME_PROPERTY, eventName);
		nvPairs.add(nvp1);
		NameValuePair nvp2 = new BasicNameValuePair(RMSConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY, eventNS);
		nvPairs.add(nvp2);
		
		//Get relevant request headers
		if (reqHeaders != null) {
			String username = reqHeaders.getUsername();
			if (username != null) {
				method.addHeader(RMSConstants.USERNAME_HEADER, urlEncodeString(username));
			}
			String password = reqHeaders.getPassword();
			if (password != null) {
				method.addHeader(RMSConstants.PASSWORD_HEADER, password);
			}
			String loginToken = reqHeaders.getLoginToken();
			if (loginToken != null) {
				method.addHeader(RMSConstants.LOGIN_TOKEN_HEADER, loginToken);
			}
			String requestedRole = reqHeaders.getRequestedRoleString();
			if (requestedRole != null) {
				method.addHeader(RMSConstants.REQUESTED_ROLE_HEADER, requestedRole);
			}
			String checkinRequestID = reqHeaders.getCheckinRequestID();
			if (checkinRequestID != null) {
				method.addHeader(RMSConstants.REQUEST_ID_HEADER, checkinRequestID);
			}
			String selectedProjectName = reqHeaders.getSelectedProject();
			if (selectedProjectName != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.SELECTED_PROJECT_HEADER, selectedProjectName);
				nvPairs.add(nvp);
			}
			String lckReqRes = reqHeaders.getLockResourceRequested();
			if (lckReqRes != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.REQ_RES_PATH_HEADER, lckReqRes);
				nvPairs.add(nvp);
			}
			String lckReqUser = reqHeaders.getLockResourceRequestor();
			if (lckReqUser != null) {
				method.addHeader(RMSConstants.LCK_REQ_RES_USER_HEADER, lckReqUser);
			}
			String implementedResource = reqHeaders.getImplementedResource();
			if (implementedResource != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.IMPLEMENTED_RESOURCE_HEADER, implementedResource);
				nvPairs.add(nvp);
			}
			String requestedResourceName = reqHeaders.getRequestedResourceName();
			if (requestedResourceName != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.REQ_RES_PATH_HEADER, requestedResourceName);
				nvPairs.add(nvp);
			}
			String extEntPath = reqHeaders.getExtEntityFileName();
			if (extEntPath != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.EXT_ENTITY_PATH_HEADER, extEntPath);
				nvPairs.add(nvp);
			}
			String updatePath = reqHeaders.getUpdatePath();
			if (updatePath != null) {
				NameValuePair nvp = new BasicNameValuePair("updatePath", updatePath);
				nvPairs.add(nvp);
			}
			String revisionId = reqHeaders.getRevisionId();
			if (revisionId != null) {
				method.addHeader("revisionId", revisionId);
			}
			String artifactPath = reqHeaders.getArtifactPath();
			if (artifactPath != null) {
				NameValuePair nvp = new BasicNameValuePair("artifactPath", artifactPath);
				nvPairs.add(nvp);
			}
			String patternId = reqHeaders.getPatternId();
			if (patternId != null) {
				method.addHeader("patternId", patternId);
			}
			String loggedInUserName = reqHeaders.getLoggedInUserName();
			if (loggedInUserName != null) {
				method.addHeader("loggedInUserName", urlEncodeString(loggedInUserName));
			}
			String delegationRoles = reqHeaders.getDelegationRolesString();
			if (delegationRoles != null) {
				method.addHeader("delegationRoles", delegationRoles);
			}
			String artifactExtension = reqHeaders.getArtifactExtension();
			if (artifactExtension != null) {
				method.addHeader("artifactExtension", artifactExtension);
			}
			boolean generateClassesOnly = reqHeaders.isGenerateClassesOnly();
			if (generateClassesOnly) {
				method.addHeader("buildClassesOnly", Boolean.toString(generateClassesOnly));
			}
			boolean isDecisionManager = reqHeaders.isStandaloneDecisionManager();
			if (isDecisionManager) {
				method.addHeader("isDecisionManagerClient", Boolean.toString(isDecisionManager));
			}
			long diffRevisionId1 = reqHeaders.getDiffRevisionId1();
			if (diffRevisionId1 != Long.MIN_VALUE) {
				method.addHeader("diffRevisionId1", Long.toString(diffRevisionId1));
			}
			long diffRevisionId2 = reqHeaders.getDiffRevisionId2();
			if (diffRevisionId2 != Long.MIN_VALUE) {
				method.addHeader("diffRevisionId2", Long.toString(diffRevisionId2));
			}
			String diffArtifactExtension1 = reqHeaders.getDiffArtifactExtension1();
			if (diffArtifactExtension1 != null) {
				method.addHeader("diffArtifactExtension1", diffArtifactExtension1);
			}
			String diffArtifactExtension2 = reqHeaders.getDiffArtifactExtension2();
			if (diffArtifactExtension2 != null) {
				method.addHeader("diffArtifactExtension2", diffArtifactExtension2);
			}
			String diffArtifactPath1 = reqHeaders.getDiffArtifactPath1();
			if (diffArtifactPath1 != null) {
				NameValuePair nvp = new BasicNameValuePair("diffArtifactPath1", diffArtifactPath1);
				nvPairs.add(nvp);
			}
			String diffArtifactPath2 = reqHeaders.getDiffArtifactPath2();
			if (diffArtifactPath2 != null) {
				NameValuePair nvp = new BasicNameValuePair("diffArtifactPath2", diffArtifactPath2);
				nvPairs.add(nvp);
			}
			boolean clearApprovedTasks = reqHeaders.isClearApprovedTasks();
			if (clearApprovedTasks){
				method.addHeader(RMSConstants.CLEAR_APPROVED_TASKS_HEADER, Boolean.toString(clearApprovedTasks));
			}
		}

		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			for (NameValuePair v : nvPairs) {
				uriBuilder.setParameter(v.getName(), v.getValue());
			}
			method.setURI(uriBuilder.build());
		} catch (URISyntaxException e) {
			LOGGER.log(Level.ERROR, "Error while parsing URL %s",url);
		}
		return method;
	}
	
	/**
	 * Returns a URL encoded form of the passed string. Returns the string as is if encoding fails.
	 * @param string
	 * @return
	 */
	public static String urlEncodeString(String string) {
		try {
			string = URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//return as is.
		}
		return string;
	}
}
