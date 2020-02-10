package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 *
 */
public class HREFLink {

	private String url;
	private List<String> params = new ArrayList<String>();

	/**
	 *
	 */
	public HREFLink(String url) {
		this.url = url;
	}

	/**
	 *
	 */
	public HREFLink(String contextPath, String url) {
		this.url = contextPath + "/" + url;
	}

	/**
	 * @param expandParam
	 */
	public HREFLink(Map<String, Object> mapParams) {
		if (mapParams != null) {
			Iterator<String> itParamKeys = mapParams.keySet().iterator();
			while (itParamKeys.hasNext()) {
				String paramName = itParamKeys.next().toString();
				String paramValue = mapParams.get(paramName).toString();
				addParameter(paramName, paramValue);
			}
		}

	}

	public HREFLink(HREFLink baseLink) {
	}

	/**
	 * @param expandParam
	 */
	public void addParameters(Map<String, Object> mapParams) {
		if (mapParams != null) {
			Iterator<String> itParamKeys = mapParams.keySet().iterator();
			while (itParamKeys.hasNext()) {
				String paramName = itParamKeys.next().toString();
				String paramValue = mapParams.get(paramName).toString();
				addParameter(paramName, paramValue);
			}
		}

	}

	public void addParameter(String name, int value) {
		addParameter(name, String.valueOf(value));
	}

	public void addParameter(String name, boolean value) {
		addParameter(name, String.valueOf(value));
	}

	public void addParameter(String name, String value) {
		StringBuffer buffer = new StringBuffer();
		// DOUBT I am not sure if I need to add the null value handling
		if (value != null) {
			String encodedValue = value;
			// try {
			// encodedValue = URLEncoder.encode(value, "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// encodedValue = value;
			// }
			buffer.append(name);
			buffer.append("=");
			buffer.append(encodedValue);
			params.add(buffer.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (url != null) {
			buffer.append(url);
		}
		if (params.isEmpty()) {
			return buffer.toString();
		}
		if (url != null && url.indexOf("?") == -1) {
			buffer.append("?");
		}
		for (Iterator<String> itParams = params.iterator(); itParams.hasNext();) {
			String param = itParams.next();
			buffer.append(param);
			if (itParams.hasNext()) {
				buffer.append("&");
			}
		}
		return buffer.toString();
	}

	/**
	 * @param request
	 * @param parametername
	 * @return
	 */
	public static String getDecodedParameterValue(BizSessionRequest request, String parametername) {
		String parameterValue = request.getParameter(parametername);
		if (parameterValue != null) {
			try {
				parameterValue = URLDecoder.decode(parameterValue, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return parameterValue;
	}
}
