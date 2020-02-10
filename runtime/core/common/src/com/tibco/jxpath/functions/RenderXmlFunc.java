package com.tibco.jxpath.functions;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathFunctionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XElementWrapper;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectWrapper;
import com.tibco.jxpath.objects.XString;
import com.tibco.xml.XiNodeUtilities;
import com.tibco.xml.datamodel.XiNode;

/*
* Author: Ryan Hollom
*/
public class RenderXmlFunc implements Function {

    public static final Function FUNCTION = new RenderXmlFunc();
    public static final QName FUNCTION_NAME = new QName("render-xml");

    private RenderXmlFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1 && args.size() != 2 && args.size() != 3) throw new XPathFunctionException("Expecting 1, 2, or 3 arguments, received "+args.size());

        Object obj = args.get(0);
        boolean omitHeader = false;
        boolean prettyPrint = false;
        if (args.size() >= 2) {
        	omitHeader = args.get(1).bool();
        }
        if (args.size() == 3) {
        	prettyPrint = args.get(2).bool();
        }
        
        return serializeToXML(obj, omitHeader, prettyPrint);
    }

	private XObject serializeToXML(Object obj, boolean omitHeader,
			boolean prettyPrint) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = null;
			if (obj instanceof XObjectWrapper) {
				obj = ((XObjectWrapper) obj).object();
			}
			if (obj instanceof XElementWrapper) {
				obj = ((XElementWrapper) obj).object();
			}
			if (obj instanceof SimpleEvent) {
				doc = writeEventDocument((SimpleEvent)obj, omitHeader, prettyPrint, docBuilder);
			} else if (obj instanceof XiNode) {
				return new XString(XiNodeUtilities.toString((XiNode) obj, prettyPrint));
			}
			return writeDocument(doc, omitHeader, prettyPrint);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private XObject writeDocument(Document doc, boolean omitHeader, boolean prettyPrint) {
		try {
			String currFact = System.getProperty("javax.xml.transform.TransformerFactory");
			if (com.tibco.xml.parsers.xmlfactories.TransformerFactory.class.getCanonicalName().equals(currFact)) {
				// TODO : having an issue with tibco's TransformerFactory, use xalan
				System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
			}

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = null;
			if (omitHeader) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			}
			if (prettyPrint) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			}
			source = new DOMSource(doc);
			transformer.transform(source, result);
			return new XString(result.getWriter().toString());
		} catch(TransformerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private Document writeEventDocument(SimpleEvent event, boolean omitHeader,
			boolean prettyPrint, DocumentBuilder docBuilder) throws Exception {

		// root element
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("ev");
		rootElement.setAttribute("Id", String.valueOf(event.getId()));
		doc.appendChild(rootElement);

		// property elements
		String[] propertyNames = event.getPropertyNames();
		for (String propName : propertyNames) {
			Element propEl = doc.createElement(propName);
			Object propVal = event.getPropertyValue(propName);
			if (propVal instanceof Calendar) {
				Calendar c = (Calendar) propVal;
				String dtString = new XDateTime(c.getTimeInMillis(), c.getTimeZone()).castAsString();
				propEl.appendChild(doc.createTextNode(dtString));
			} else if (propVal != null) {
				String propStr = propVal.toString();
				propEl.appendChild(doc.createTextNode(propStr));
			}
            if (propVal != null) {
            	rootElement.appendChild(propEl);
            }
		}

		// payload node
		if (event.getPayload() instanceof XiNodePayload) {
			XiNode payloadNode = (XiNode) event.getPayload().getObject();
			Element payload = doc.createElement("payload");
			rootElement.appendChild(payload);
			
			
			String payloadText = XiNodeUtilities.toString(payloadNode);
			InputSource is = new InputSource(new StringReader(payloadText));
			Element pNode = docBuilder.parse(is).getDocumentElement();
			Node importNode = doc.importNode(pNode, true);
			payload.appendChild(importNode);
		}
		
		return doc;
	}
	
}
