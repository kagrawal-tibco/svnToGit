package com.tibco.cep.studio.dashboard.utils;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class SystemElementsTemplate {

	private static SystemElementsTemplate instance;

	private static final synchronized SystemElementsTemplate getInstance() {
		if (instance == null) {
			instance = new SystemElementsTemplate();
		}
		return instance;
	}

	private InputSource inputSource;
	private Node rootNode;
	private XPath xPath;

	private SystemElementsTemplate() {
		URL seedDataFileURL = this.getClass().getResource("BEViewsSystemElement.xml");
		if (seedDataFileURL == null) {
			throw new IllegalStateException("could not find BEViewsSystemElement.xml");
		}
		try {
			inputSource = new InputSource(seedDataFileURL.openStream());
			XPathFactory factory = XPathFactory.newInstance();
			xPath = factory.newXPath();		
			rootNode = (Node) xPath.evaluate("configurations", inputSource, XPathConstants.NODE);
		} catch (IOException e) {
			throw new IllegalStateException("could not load BEViewsSystemElement.xml", e);
		} catch (XPathExpressionException e) {
			throw new IllegalStateException("could not search BEViewsSystemElement.xml for 'configurations' nodes", e);
		}
	}
	
	public static Node getRootNode() {
		return getInstance().rootNode;
	}
	
	public static Object get(Node node, String expression, QName returnType) throws XPathExpressionException{
		return getInstance().xPath.evaluate(expression, node, returnType);
	}
	
	public static String get(Node node, String expression) throws XPathExpressionException{
		return getInstance().xPath.evaluate(expression, node);
	}
	

}