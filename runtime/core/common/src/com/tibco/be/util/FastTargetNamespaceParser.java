package com.tibco.be.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FastTargetNamespaceParser extends DefaultHandler {
	
    public static final String NO_NAMESPACE_NS_PREFIX = "http://www.tibco.com/ns/no_namespace_schema_location";

	/**
	 * @param emfTnsCache
	 */
	public FastTargetNamespaceParser() {
	}

	private static final String SCHEMA_ATTR = "schema";
	private static final String ELEMENT_DECL = "element";
	private static final String TNS_ATTR = "targetNamespace";
	private static final String NAME_ATTR = "name";
	private List<String> targetNamespaces = new ArrayList<String>();
	private boolean inSchemaDef = false;
	private Stack<String> parentNodeStack = new Stack<String>();
	private String currentTNS = "";
	private HashMap<String, List<String>> elementsForTNS = new HashMap<String, List<String>>();
	
	public List<String> getTargetNamespaces() {
		return targetNamespaces;
	}

	@Override
	public void startElement(String uri, String localName,
			String qName, Attributes attributes)
			throws SAXException {
		if (!inSchemaDef && qName.endsWith(SCHEMA_ATTR)) {
			inSchemaDef = true;
			String targetNamespace = attributes.getValue(TNS_ATTR);
			if (targetNamespace == null) {
				targetNamespace = NO_NAMESPACE_NS_PREFIX;
			}
			targetNamespaces.add(targetNamespace);
			currentTNS = targetNamespace;
		} else if (inSchemaDef && qName.endsWith(ELEMENT_DECL)) {
			if (parentNodeStack.lastElement().endsWith(SCHEMA_ATTR)) {
				addElementDecl(attributes.getValue(NAME_ATTR));
			}
		}
		parentNodeStack.push(qName);
	}

	private void addElementDecl(String value) {
		List<String> list = elementsForTNS.get(currentTNS);
		if (list == null) {
			list = new ArrayList<String>();
			elementsForTNS.put(currentTNS, list);
		}
		list.add(value);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (inSchemaDef && qName.endsWith(SCHEMA_ATTR)) {
			inSchemaDef = false;
		}
		parentNodeStack.pop();
	}

	public HashMap<String,List<String>> getElementsForTNS() {
		return this.elementsForTNS;
	}

	
}