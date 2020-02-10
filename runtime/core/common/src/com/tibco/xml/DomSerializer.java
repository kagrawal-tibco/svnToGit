package com.tibco.xml;

import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Sep 28, 2008
 * Time: 1:12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomSerializer {
	
    /**
     * Prints a textual representation of a DOM object into a text string..
     *
     * @param node DOM object to parse.
     * @return String representation of <i>document</i>.
     */
    static public String serialize(Node node) {
        String result = serialize(node, new StringWriter());
        return result;
    }//toString()

     static public String serialize(Node node,Writer writer) {
        String result = serialize(node, "iso-8859-1", "yes", "xml", writer);
        return result;
    }

    /**
     * Prints a textual representation of a DOM object into a text string..
     * @param node DOM object to parse.
     * @param encoding encoding needed
     * @param indenting - valid values are "yes" | "no"
     * @param method - "xml, html, text"
     * @param writer - Writer to write to
     * @return String representation of <i>node</i>.
     */
    static public String serialize(Node node, String encoding, String indenting, String method, Writer writer) {
//    	String prevTransformer = System.getProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
//    	System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
        String prevTransformer = System.getProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
//        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");

    	String result = null;
        if (node != null) {
            StreamResult strResult = new StreamResult(writer);
            DOMSource source = new DOMSource(node);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try { 
            	tfac.setAttribute("indent-number", 4);
            } catch (IllegalArgumentException iae) {
            	iae.printStackTrace();
            }
            try {
                Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, encoding);
                t.setOutputProperty(OutputKeys.INDENT, indenting);
                t.setOutputProperty(OutputKeys.METHOD, method); //xml, html, text
                t.transform(source, strResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
        }

//        System.setProperty("javax.xml.transform.TransformerFactory", prevTransformer);
        return result;
    }

}

