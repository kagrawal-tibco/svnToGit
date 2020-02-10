/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.rpc.RPCManager;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.request.model.RequestModelBuilder;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * This class provides support to intercept Request and inject required headers
 * and request parameters if any.Other DataSources should extend the
 * AbstractRestDataSource for Rest like behaviour.
 * 
 * @author Vikram Patil
 */
public abstract class AbstractRestDS<D extends IRequestDataItem, P extends IRequestProject<D>, Q extends IRequestData<D, P>, R extends IRequest<D, P, Q>>
		extends RestDataSource {

	private Map<String, String> headerParams = new HashMap<String, String>();
	private Map<String, Object> requestParams = new HashMap<String, Object>();

	private HttpMethod httpMethod = HttpMethod.GET;
	private String additionalURLPath;
	
	public AbstractRestDS(String dsID) {
		this.setID(dsID);
		if (this.getServerURL() != null) {
			this.setDataURL(this.getServerURL().getURL());
		}
		RPCManager.setHandleErrorCallback(new RestDSErrorHandler());
	}
	
	@Override
	protected Object transformRequest(DSRequest dsRequest) {
		// Avoid blocking the user during server call
		dsRequest.setShowPrompt(false);
		
		// set the DS request timeout
		dsRequest.setTimeout(WebStudio.getDSRequestTimeout());
		
		String additionalURLPath = getAdditionalURLPath();
		if (additionalURLPath != null) {
			dsRequest.setActionURL(this.getServerURL().getURL(additionalURLPath));
		}
		
		// Add logged-in user details
		this.headerParams.put(RequestParameter.REQUEST_USER_NAME, URL.encode(WebStudio.get().getUser().getUserName()));

		if (!this.headerParams.isEmpty()) {
			dsRequest.setHttpHeaders(this.headerParams);
		}

		if (this.httpMethod.equals(HttpMethod.GET)) {
			dsRequest.setHttpMethod(HttpMethod.GET.getValue());
			dsRequest.setParams(this.requestParams);
		} else {
			dsRequest.setHttpMethod(HttpMethod.POST.getValue());
			dsRequest.setUseSimpleHttp(true);

			String xmlData = RequestModelBuilder.<D, P, Q, R> buildXMLRequest(this.getServerURL(), this.requestParams);
			Map<String, String> paramData = new HashMap<String, String>();
			paramData.put(RequestParameter.REQUEST_PARAM_POST_DATA, xmlData);
			dsRequest.setData(paramData);

			dsRequest.setHttpHeaders(this.headerParams);
		}

		return super.transformRequest(dsRequest);
	}
	
	public void addHeaderParameter(String key, String value) {
		this.headerParams.put(key, value);
	}

	public void addRequestParameters(RequestParameter parameter) {
		this.requestParams.putAll(parameter.getParameters());
	}

	public void clearRequestParameters() {
		this.requestParams.clear();
	}

	public void clearHeaderParameters() {
		this.headerParams.clear();
	}

	public void clearAllParameters() {
		this.requestParams.clear();
		this.headerParams.clear();
	}

	public void setHeaderParams(HashMap<String, String> headerParams) {
		this.headerParams = headerParams;
	}

	public void setRequestParams(HashMap<String, Object> requestParams) {
		this.requestParams = requestParams;
	}

	public Map<String, Object> getRequestParams() {
		return this.requestParams;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getRecordNode() {
		return "/data/record";
	}

	public abstract ServerEndpoints getServerURL();
	
	public String getAdditionalURLPath() {
		return additionalURLPath;
	}
	
	public void setAdditionalURLPath(String additionalPath) {
		this.additionalURLPath = additionalPath;
	}
}
