package com.tibco.cep.webstudio.client.decisiontable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.types.PromptStyle;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RestDSErrorHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainDataSource extends RestDataSource {
	
	private static List<DomainDataSource> INSTANCES;
	private static DTMessages dtMsgBundle = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);

	private Map<String, Object> requestParams = new HashMap<String, Object>();
	
	public static DomainDataSource getInstance(String domainPath) {
		DomainDataSource cachedDSObject = null;
		if (INSTANCES != null) {
			cachedDSObject = getCachedDSObject("getDomain_DS_" + domainPath);
		} else {
			INSTANCES = new ArrayList<DomainDataSource>();
		}
		
		if (cachedDSObject == null) {
			cachedDSObject = new DomainDataSource("getDomain_DS_" + domainPath);
			INSTANCES.add(cachedDSObject);
		}		
		return cachedDSObject;
	}

	/**
	 * @param columns
	 * @param listFields
	 */
	public DomainDataSource(String id) {
		this.setID(id);
		this.setDataURL(ServerEndpoints.RMS_GET_DOMAINS.getURL());
		RPCManager.setHandleErrorCallback(new RestDSErrorHandler());

		DataSourceTextField valField = new DataSourceTextField("value", dtMsgBundle.dt_dataSource_textField_value());
		valField.setPrimaryKey(true);
		
		DataSourceTextField descField = new DataSourceTextField("description", dtMsgBundle.dt_dataSource_textField_description());

		setFields(valField, descField);
		
		setCacheAllData(true);
	}

	@Override
	protected Object transformRequest(DSRequest dsRequest) {
		dsRequest.setShowPrompt(true);
		dsRequest.setPromptStyle(PromptStyle.CURSOR);
		
		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put(RequestParameter.REQUEST_USER_NAME, WebStudio.get().getUser().getUserName());
		dsRequest.setHttpHeaders(headerParams);
		
		dsRequest.setHttpMethod(HttpMethod.GET.getValue());
		dsRequest.setParams(requestParams);
		
		return super.transformRequest(dsRequest);
	}
	
	/**
	 * check if a Data Source associated to a specific Id exists
	 * 
	 * @param dsID
	 * @return
	 */
	public static DomainDataSource getCachedDSObject(String dsID) {
		for (DomainDataSource domainDS : INSTANCES) {
			if (domainDS.getID().equals(dsID)) {
				return domainDS;
			}
		}
		return null;
	}
	
	public void setRequestParams(HashMap<String, Object> requestParams) {
		this.requestParams = requestParams;
	}

	public void addRequestParameters(RequestParameter parameter) {
		this.requestParams.putAll(parameter.getParameters());
	}

	public void clearRequestParameters() {
		this.requestParams.clear();
	}
	
}