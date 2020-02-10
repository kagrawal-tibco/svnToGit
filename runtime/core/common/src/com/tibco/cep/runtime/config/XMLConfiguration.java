package com.tibco.cep.runtime.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 6:46 PM
*/
public class XMLConfiguration {
    static XPath xPath = XPathFactory.newInstance().newXPath();
    private Element contextElement;
    protected  String name = "";
    private Element rootElement;


    /**
     * Read the Configuration from an XML file
     *
     * @param xmlfile
     * @param rootName
     * @return
     * @throws Exception
     */
    public static XMLConfiguration readConfiguration(String xmlfile, String rootName) throws Exception
    {

        InputStream is = new FileInputStream(xmlfile);
        return readConfiguration(is, rootName);
    }

    public static XMLConfiguration newConfiguration(String name, Element contextElement, XMLConfiguration parentConfig)
    {
        return new XMLConfiguration(name, contextElement, parentConfig.getRootElement());
    }

    private XMLConfiguration(String name, Element contextElement, Element rootElement)  {
        this.contextElement = contextElement;
        this.rootElement = rootElement;
        this.name = name;
    }

    public Element getRootElement() {
        return this.rootElement;
    }



    public String getName() {
        return name;
    }


    /**
     * Return evaluating the expression from the current Context.
     * @param expr
     * @return String
     */
    public String getConfigValue(String expr) throws Exception {
        String ret = xPath.evaluate(expr, this.contextElement);
        return ret;
    }

    /**
     * Return evaluating the expression from the current Context.
     * If expression evaluate to null, default value is returned.
     * @param expr
     * @return String
     */
    public String getConfigValue(String expr, String defaultVal) throws Exception {
        String ret = xPath.evaluate(expr, this.contextElement);
        if(ret == null || ret.isEmpty()) {
        	return defaultVal;
        }
        return ret;
    }

    public Element getConfigElement(String expr) throws Exception
    {
        Element ret = (Element) xPath.evaluate(expr, this.contextElement, XPathConstants.NODE);
        return ret;
    }

    public List<Element> getConfigElements(String expr) throws Exception
    {
        NodeList nl = (NodeList) xPath.compile(expr).evaluate(this.contextElement, XPathConstants.NODESET);
        List<Element> elements = new ArrayList<Element>();
        for (int i=0; i < nl.getLength(); i++) {

            Node n = nl.item(i);
            if (n instanceof Element) {
                elements.add((Element)n);
            }
        }

        return elements;
    }


    /**
     * Return the element value for the context specified
     * @param context
     * @param expr
     * @return
     * @throws Exception
     */
    public String getConfigValue(Element context, String expr) throws Exception
    {
        String ret = xPath.evaluate(expr, context);

        return ret;
    }

    public String getConfigValue(Element context, String expr, String defaultVal) throws Exception
    {
        String ret = xPath.evaluate(expr, context);
        if(ret == null || ret.isEmpty()){
        	return defaultVal;
        }
        return ret;
    }

    public Element getConfigElement(Element context, String expr) throws Exception
    {
        Element ret = (Element) xPath.evaluate(expr, context, XPathConstants.NODE);
        return ret;
    }

    public List<Element> getConfigElements(Element context, String expr) throws Exception
    {
        if (context == null) context = this.contextElement;

        NodeList nl = (NodeList) xPath.compile(expr).evaluate(context, XPathConstants.NODESET);
        List<Element> elements = new ArrayList<Element>();
        for (int i=0; i < nl.getLength(); i++) {

            Node n = nl.item(i);
            if (n instanceof Element) {
                elements.add((Element)n);
            }
        }

        return elements;
    }


    public void discard() {

    }

    public static XMLConfiguration readConfiguration(InputStream stream, String rootName) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.parse(stream);
        Element rootEle = (Element) xPath.compile(rootName).evaluate(doc, XPathConstants.NODE);
        String name = xPath.compile(rootName + "/@name").evaluate(doc);
        return new XMLConfiguration(name, rootEle, rootEle);
    }
}
