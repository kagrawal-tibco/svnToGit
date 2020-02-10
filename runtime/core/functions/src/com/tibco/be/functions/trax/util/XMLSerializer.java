package com.tibco.be.functions.trax.util;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLSerializer {
	
	static TransformerFactory transFactory = TransformerFactory.newInstance();
	
	public static String serialize(Document doc) throws TransformerException{
		Transformer transformer = transFactory.newTransformer();
		DOMSource source = new DOMSource(doc.getDocumentElement());
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
		return writer.toString();
	}
}
