package com.tibco.be.functions.object;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

public class XiContentHandler implements ContentHandler {
	
	Stack elements;
	
	XiNode node;
	
	XiContentHandler(XiNode node){
        this.elements = new Stack();
        this.node = node;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		XiNode instance = (XiNode) elements.peek();
        if (ch != null) {
        	instance.appendText(String.valueOf(ch, start, length));
        }
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		this.elements.pop();
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void startDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		XiNode instance = null;
		if(elements.isEmpty()){
			instance = node;
			this.elements.push(node);
		} else {
			instance = (XiNode) elements.peek();
			XiNode newInst = instance.appendElement(ExpandedName.makeName(uri, localName));
			elements.push(newInst);	
		}
		
		if(atts != null){
			int attLen = atts.getLength();
			for (int index = 0; index < attLen; index++) {
				String attLocalname= atts.getLocalName(index);
				String attURI = atts.getURI(index);
				String attVal = atts.getValue(index);
				ExpandedName eName = ExpandedName.makeName(attURI, attLocalname);
				instance.setAttributeStringValue(eName, attVal);
			}
		}
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
	}
}
