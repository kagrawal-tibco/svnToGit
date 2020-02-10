package com.tibco.be.functions.object;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XMLContentHandler implements ContentHandler {
	
	private StringBuilder writer;
	
	XMLContentHandler(StringBuilder builder){
		this.writer = builder;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String value = String.valueOf(ch, start, length);
		this.writer.append(handleXMLSpecialChars(value));
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if(name == null || "".equals(name)){
			this.writer.append("</").append(localName).append(">");
		} else {
			this.writer.append("</").append(name).append(":").append(localName).append(">");
		}
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		String value = String.valueOf(ch, start, length);
		this.writer.append(handleXMLSpecialChars(value));
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void startDocument() throws SAXException {
		this.writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		if(name == null || "".equals(name)){
			this.writer.append("<").append(localName);
		} else {
			this.writer.append("<").append(name).append(":").append(localName);
			this.writer.append(" xmlns:").append(name).append("=\"").append(uri).append("\"");
		}
		
		if(atts != null){
			int length = atts.getLength();
			for (int index = 0; index < length; index++) {
				String attName= atts.getQName(index);
				String attLocalname= atts.getLocalName(index);
				String attURI = atts.getURI(index);
				String attVal = atts.getValue(index);
				if(attName == null || "".equals(attName)){
					this.writer.append(" ").append(attLocalname).append("=\"").append(handleXMLSpecialChars(attVal)).append("\"");
				} else {
					this.writer.append(" xmlns:").append(attName).append("=\"").append(attURI).append("\"");
					this.writer.append(" ").append(attName).append(":").append(attLocalname).append("=\"").append(handleXMLSpecialChars(attVal)).append("\"");
				}
			}
		}
		this.writer.append(">");
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
	}

	// Replace XML special characters by their predefined entities 
	private static String handleXMLSpecialChars(String str){
		if(str == null || "".equals(str)){
			return str;
		}
		// this sequence is important, do not change
		String retStr = str;
		if (retStr.contains("&")) {
			retStr = retStr.replace("&", "&amp;");
		}
		if (retStr.contains("<")) {
			retStr = retStr.replace("<", "&lt;");
		}
		if (retStr.contains(">")) {
			retStr = retStr.replace(">", "&gt;");
		}
		if (retStr.contains("'")) {
			retStr = retStr.replace("'", "&apos;");
		}
		if (retStr.contains("\"")) {
			retStr = retStr.replace("\"", "&quot;");
		}
		return retStr;
	}
}
