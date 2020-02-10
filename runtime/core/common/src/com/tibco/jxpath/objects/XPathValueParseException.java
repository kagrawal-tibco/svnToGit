package com.tibco.jxpath.objects;

import com.tibco.xml.data.primitive.ExpandedName;

public class XPathValueParseException extends Exception {

	private String text;
	private ExpandedName name;
	private Exception wrappedException;

	public XPathValueParseException(String text, ExpandedName name,
			Exception e) {
		this.text = text;
		this.name = name;
		this.wrappedException = e;
	}

	public XPathValueParseException(String srcval) {
		this(srcval, null, null);
	}

	public XPathValueParseException(String collapsed, ExpandedName name) {
		this(collapsed, name, null);
	}

}
