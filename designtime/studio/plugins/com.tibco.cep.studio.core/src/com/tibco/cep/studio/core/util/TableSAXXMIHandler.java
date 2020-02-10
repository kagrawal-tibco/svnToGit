package com.tibco.cep.studio.core.util;

import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TableSAXXMIHandler extends SAXXMIHandler {

	private static final int NORMAL					= 0;
	private static final int PROCESSING_TABLE 		= 1;
	
	private int mode = NORMAL;

	public TableSAXXMIHandler(XMLResource xmiResource,
			XMLHelper helper, Map<?, ?> options) {
		super(xmiResource, helper, options);
	}

	@Override
	public void endDocument() {
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String name) {
		
		if (mode == NORMAL) {
			super.endElement(uri, localName, name);
		}
		if ("decisionTable" == name || "exceptionTable" == name) {
			mode = NORMAL;
		}
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if ("decisionTable" == name || "exceptionTable" == name) {
			mode = PROCESSING_TABLE;
		}
		if (mode == NORMAL) {
			super.startElement(uri, localName, name, attributes);
		}
	}

}
