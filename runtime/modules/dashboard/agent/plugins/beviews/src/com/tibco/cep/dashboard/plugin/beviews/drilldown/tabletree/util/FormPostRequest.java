package com.tibco.cep.dashboard.plugin.beviews.drilldown.tabletree.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;

/**
 * @author rajesh
 *
 */
public class FormPostRequest {

	private String actionUrl;
	private Map<String, String> params = new HashMap<String, String>();

	/**
	 *
	 */
	public FormPostRequest(String url) {
		this.actionUrl = url;
	}

	/**
	 *
	 */
	public FormPostRequest(String contextPath, String url) {
		this.actionUrl = contextPath + "/" + url;
	}

	/**
	 * @param expandParam
	 */
	public void addParameters(Map<String, Object> mapParams) {
		if (mapParams != null) {
			Iterator<String> itParamKeys = mapParams.keySet().iterator();
			while (itParamKeys.hasNext()) {
				String paramName = itParamKeys.next().toString();
				String paramValue = String.valueOf(mapParams.get(paramName));
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
		params.put(name, value);
	}

	/**
	 * Make a JSON text of this JSONObject. For compactness, no whitespace is added. If this would not result in a syntactically correct JSON text, then null will be returned instead.
	 * <p>
	 * Warning: This method assumes that the data structure is acyclical.
	 *
	 * @return a printable, displayable, portable, transmittable representation of the object, beginning with <code>{</code>&nbsp;<small>(left brace)</small> and ending with <code>}</code>&nbsp;<small>(right brace)</small>.
	 */
	public String toString() {
		try {
			Iterator<String> keys = params.keySet().iterator();
			StringBuffer sb = new StringBuffer("{");

			while (keys.hasNext()) {
				if (sb.length() > 1) {
					sb.append(',');
				}
				Object o = keys.next();
				sb.append(quote(o.toString()));
				sb.append(':');
				sb.append(params.get(o));
			}
			sb.append('}');
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Produce a string in double quotes with backslash sequences in all the right places. A backslash will be inserted within </, allowing JSON text to be delivered in HTML. In JSON text, a string cannot contain a control
	 * character or an unescaped quote or backslash.
	 *
	 * @param string
	 *            A String
	 * @return A String correctly formatted for insertion in a JSON text.
	 */
	public static String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}

		char b;
		char c = 0;
		int i;
		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);
		String t;

		sb.append('"');
		for (i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
				case '\\':
				case '"':
					sb.append('\\');
					sb.append(c);
					break;
				case '/':
					if (b == '<') {
						sb.append('\\');
					}
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				default:
					if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
						t = "000" + Integer.toHexString(c);
						sb.append("\\u" + t.substring(t.length() - 4));
					} else {
						sb.append(c);
					}
			}
		}
		sb.append('"');
		return sb.toString();
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
