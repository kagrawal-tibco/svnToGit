package com.tibco.cep.dashboard.psvr.biz;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @author RGupta
 * 
 */
public class XMLBizResponseImpl extends BaseBizResponse {

	private static final String CDATA_SECTION = "<![CDATA[{0}]]>";

	public XMLBizResponseImpl() {
		this(SUCCESS_STATUS, null);
	}

	public XMLBizResponseImpl(String status, String message) {
		this.status = status;
		this.message = message;
		this.attributes = new LinkedHashMap<String, String>();
		this.headers = new LinkedHashMap<String, String>();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buffer.append("<response>");
		buffer.append("<state>");
		buffer.append(status);
		buffer.append("</state>");
		if (message != null) {
			buffer.append("<message>");
			buffer.append(message);
			buffer.append("</message>");
		}

		Iterator<String> attributesIter = attributes.keySet().iterator();
		while (attributesIter.hasNext()) {
			String attributeName = attributesIter.next();
			buffer.append("<");
			buffer.append(attributeName);
			buffer.append(">");
			buffer.append(MessageFormat.format(CDATA_SECTION, getAttribute(attributeName)));
			buffer.append("</");
			buffer.append(attributeName);
			buffer.append(">");
		}
		buffer.append("</response>");
		return buffer.toString();
	}

	@SuppressWarnings("unused")
	private String escapeCDATATags(String str) {
		if (str.indexOf("<![CDATA[") != -1) {
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("<", "&lt;");
		}
		return str;
	}

}