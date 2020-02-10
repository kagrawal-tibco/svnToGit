package com.tibco.cep.studio.ui.editors.utils;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SimpleXMLTagParser extends DefaultHandler {

	private HashMap<String, String> tags = new HashMap<String, String>();
	private String currentEl;

	public Map<String, String> getTags() {
		return tags;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.currentEl = qName;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentEl != null) {
			String val = new String(ch, start, length).trim();
			this.tags.put(currentEl, val);
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		currentEl = null;
	}

}
