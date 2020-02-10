package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PersistenceUtil {

	public static Document getSaveDocument(String mainPath) {
		return getSaveDocument(mainPath, null, null);
	}
	
	public static Document getSaveDocument(String mainPath, String namespaceURI, String prefix) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document doc = parser.newDocument();
			Element root = null;
			if(namespaceURI != null && prefix != null){
				root = doc.createElementNS(namespaceURI, prefix + mainPath);
			}else{
				root = doc.createElement(mainPath);
			}
			doc.appendChild(root);
			return doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public static String getDocumentString(Document doc) {
    	StringWriter stw = new StringWriter();
		Result result = new StreamResult(stw);
    	boolean success = transformDocument(doc, result);
    	if (success)
    		return stw.toString();
    	return ("");
    }
    
    public static boolean writeFile(Document doc, String fileName) {
		Result result;
		if (fileName != null) {
			result = new StreamResult(new File(fileName));
		}
		else {
			result = new StreamResult(System.out);
		}
    	boolean success = transformDocument(doc, result);
    	return success;
    }
    
    private static boolean transformDocument(Document doc, Result result) {
    	if (doc == null)
    		return false;
    	try {
    		Source source = new DOMSource(doc);
			
    		// Write the DOM document to the file
    		TransformerFactory factory = TransformerFactory.newInstance();
    		//factory.setAttribute("indent-number", 4);
    		Transformer xformer = factory.newTransformer();
    		xformer.setOutputProperty(OutputKeys.INDENT, "yes");
    		xformer.setOutputProperty(OutputKeys.METHOD, "xml");
    		xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        xformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
}
