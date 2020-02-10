package com.tibco.cep.dashboard.common.utils;

public class XMLTextUtil {
	
	static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	static final String XML_ATTR_BGN = "=\"";
	static final String XML_ATTR_END = "\"";
	static final String XML_TAG_BGN = "<";
	static final String XML_TAG_END = ">";
	static final String XML_TAG_CLS = "</";
	static final String XML_TAG_FNS = "/>";
	static final String XML_CMT_BGN = "<!-- ";
	static final String XML_CMT_END = " -->";
	static final String SPACE = " ";

	public static String addHeader(String xmlString) {
		return addHeader(xmlString, XML_HEADER);
	}

	public static String addHeader(String xmlString, String header) {
		String xmlResult = null;
		if (xmlString.startsWith(header)) {
			xmlResult = new String(xmlString);
		} else {
			xmlResult = header.concat(xmlString);
		}
		return xmlResult;
	}

	public static String removeHeader(String xmlString) {
		return removeHeader(xmlString, XML_HEADER);
	}

	public static String removeHeader(String xmlString, String header) {
		String xmlResult = null;
		if (xmlString.startsWith(header)) {
			xmlResult = xmlString.substring(header.length());
		} else {
			xmlResult = new String(xmlString);
		}
		return xmlResult;
	}

	public static String header() {
		return XML_HEADER;
	}

	public static String comment(String comment) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_CMT_BGN).append(comment).append(XML_CMT_END);
		return sb.toString();
	}

	public static String openCloseTag(String tagname) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(XML_TAG_END);
		sb.append(XML_TAG_CLS).append(tagname).append(XML_TAG_END);
		return sb.toString();
	}

	public static String openCloseTag(String tagname, String nodevalue) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(XML_TAG_END);
		sb.append(nodevalue);
		sb.append(XML_TAG_CLS).append(tagname).append(XML_TAG_END);
		return sb.toString();
	}

	public static String openCloseTag(String tagname, String[] attributes, String[] values) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(SPACE);
		for (int i = 0; i < attributes.length; i++) {
			sb.append(attributes[i]);
			sb.append(XML_ATTR_BGN).append(getValueOf(values[i])).append(XML_ATTR_END);
			sb.append(SPACE);
		}
		sb.append(XML_TAG_FNS);
		return sb.toString();
	}

	public static String openTag(String tagname) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(SPACE);
		sb.append(XML_TAG_END);
		return sb.toString();
	}

	public static String openTag(String tagname, String attribute, String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(SPACE);
		if (attribute != null) {
			sb.append(attribute);
			sb.append(XML_ATTR_BGN).append(getValueOf(value)).append(XML_ATTR_END);
		}
		sb.append(XML_TAG_END);
		return sb.toString();
	}

	public static String openTag(String tagname, String[] attributes, String[] values) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname).append(SPACE);
		for (int i = 0; i < attributes.length; i++) {
			sb.append(attributes[i]);
			sb.append(XML_ATTR_BGN).append(getValueOf(values[i])).append(XML_ATTR_END);
			sb.append(SPACE);
		}
		sb.append(XML_TAG_END);
		return sb.toString();
	}

	public static String closeTag(String tagname) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_CLS).append(tagname).append(XML_TAG_END);
		return sb.toString();
	}

	public static String startTag(String tagname) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_BGN).append(tagname);
		return sb.toString();
	}

	public static String endTag(String tagname) {
		StringBuffer sb = new StringBuffer();
		sb.append(XML_TAG_CLS).append(tagname);
		return sb.toString();
	}

	private static String getValueOf(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}
}
