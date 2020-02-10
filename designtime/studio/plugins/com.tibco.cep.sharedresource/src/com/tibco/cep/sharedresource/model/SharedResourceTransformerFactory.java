package com.tibco.cep.sharedresource.model;

import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;

/*
@author ssailapp
@date Oct 5, 2009 12:00:20 PM
 */

public class SharedResourceTransformerFactory {

	public SharedResourceTransformerFactory() {
	}
	
	public void transform(String xmlFile, String xsltFile, OutputStream outFile) throws TransformerException {
	    javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(xmlFile);
	    javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(xsltFile);
	    javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(outFile);
	
	    // create an instance of TransformerFactory
	    javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
	    javax.xml.transform.Transformer trans = transFact.newTransformer(xsltSource);
	    trans.setOutputProperty(OutputKeys.INDENT, "yes");
	    trans.setOutputProperty(OutputKeys.METHOD, "xml");
	    trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");        
		trans.transform(xmlSource, result);
	}
}
