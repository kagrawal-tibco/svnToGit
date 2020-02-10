/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
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
public class PostMethodBuilder implements MethodBuilder<HttpPost> {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(PostMethodBuilder.class);

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.MethodBuilder#buildMethod(java.lang.String, java.lang.String, com.tibco.cep.mgmtserver.rms.utils.RequestHeaders, org.apache.commons.httpclient.methods.RequestEntity)
	 */
	public HttpPost buildMethod(final String url,
			                      final String eventName, 
			                      String eventNS,
			                      final RequestHeaders reqHeaders, 
			                      final StringEntity requestEntity) {
		HttpPost method = new HttpPost(url);
		if (eventName == null) {
			throw new IllegalArgumentException("Event name cannot be null");
		}
		if (eventNS == null) {
			eventNS = "";
		}
		
		List<NameValuePair> nvPairs = new ArrayList<NameValuePair>();
		//Get relevant request headers
		if (reqHeaders != null) {
			String username = reqHeaders.getUsername();
			if (username != null) {
				method.addHeader(RMSConstants.USERNAME_HEADER, GetMethodBuilder.urlEncodeString(username));
			}
			String loginToken = reqHeaders.getLoginToken();
			if (loginToken != null) {
				method.addHeader(RMSConstants.LOGIN_TOKEN_HEADER, loginToken);
			}
			String ns = eventNS;
			if (ns != null) {
				method.addHeader(RMSConstants.MESSAGE_HEADER_NAMESPACE_PROPERTY, ns);
			}
			String nm = eventName;
			if (nm != null) {
				method.addHeader(RMSConstants.MESSAGE_HEADER_NAME_PROPERTY, nm);
			}
			String password = reqHeaders.getPassword();
			if (password != null) {
				method.addHeader(RMSConstants.PASSWORD_HEADER, password);
			}
			String selectedProjectName = reqHeaders.getSelectedProject();
			if (selectedProjectName != null) {
				NameValuePair nvp = new BasicNameValuePair(RMSConstants.SELECTED_PROJECT_HEADER, selectedProjectName);
				nvPairs.add(nvp);
			}
			String patternId = reqHeaders.getPatternId();
			if (patternId != null) {
				method.addHeader("patternId", patternId);
			}
			String requestedRole = reqHeaders.getRequestedRoleString();
			if (requestedRole != null) {
				method.addHeader(RMSConstants.REQUESTED_ROLE_HEADER, requestedRole);
			}
			String artifactPath = reqHeaders.getArtifactPath();
			if (artifactPath != null) {
				NameValuePair nvp = new BasicNameValuePair("artifactPath", artifactPath);
				nvPairs.add(nvp);
			}
			String loggedInUserName = reqHeaders.getLoggedInUserName();
			if (loggedInUserName != null) {
				method.addHeader("loggedInUserName", GetMethodBuilder.urlEncodeString(loggedInUserName));
			}
			boolean optimized = reqHeaders.getOptimized();
			method.addHeader(RMSConstants.OPTIMIZED_HEADER, Boolean.toString(optimized));
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
		
		if (requestEntity != null) {
			method.setEntity(requestEntity);
		}
		return method;
	}
}
