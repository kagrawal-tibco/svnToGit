package com.tibco.be.functions.trax.transform;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tibco.be.functions.trax.dom.builder.XMLNodeBuilder;
import com.tibco.be.functions.trax.dom.builder.impl.Array2XMLNodeBuilder;
import com.tibco.be.functions.trax.dom.builder.impl.ConceptXMLNodeBuilder;
import com.tibco.be.functions.trax.dom.builder.impl.GVXMLNodeBuilder;
import com.tibco.be.functions.trax.dom.builder.impl.SimpleEventXMLNodeBuilder;
import com.tibco.be.functions.trax.util.CalendarUtil;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class XSLTransformer {
	// XSLT string to Templates object map
	private static Map<String, Templates> templatesMap = new HashMap<String, Templates>();
	
	// Templates.toString+CurrentThread.Name is the key and Transformer instance is value 
	private static Map<String, Transformer> transformerMap = new HashMap<String, Transformer>(); 

	private static TransformerFactory transformerFactory;
	private static DocumentBuilderFactory documentBuilderFactory;

	static {
		init();
	}
	
	private static void init() {
		// Load from jre itself
		System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
		
		transformerFactory = TransformerFactory.newInstance();
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
	}
	
	protected Transformer getTransformer(String xslt)
			throws TransformerConfigurationException {
		Templates template = getTemplate(xslt);
		if(template == null){
			throw new RuntimeException("Can't create template for xslt");
		}
		String transformerKey = Thread.currentThread().getName().concat(template.toString());
		Transformer transformer = transformerMap.get(transformerKey);
		if(transformer == null){
			transformer = template.newTransformer();
			transformerMap.put(transformerKey, transformer);
		}
		return transformer;
	}

	private Templates getTemplate(String xslt)
			throws TransformerConfigurationException {
		Templates template = templatesMap.get(xslt);
		if(template == null){
			StreamSource xsltSource = new StreamSource(new StringReader(xslt));
			template = transformerFactory.newTemplates(xsltSource);
			templatesMap.put(xslt, template);
		}
		return template;
	}

	public Concept transform2Concept(String xslt, String ceptURI, String[] varNames, Object[] documents, Node gvNode, Node[] inXML){
		
		try {
			Transformer transformer = getTransformer(xslt);
			
			for (int i = 0; i < documents.length; i++) {
				Object object = documents[i];
				transformer.setParameter(varNames[i], object);
				// Did not work
				//transformer.setParameter(paramName, new InputSource(new ByteArrayInputStream(inXML[i].getBytes("UTF-8"))));
				//transformer.setParameter(paramName, new DOMSource(document));
			}
			
			for(int i = 0; i < inXML.length; i++) {
				String namespace = inXML[i].getNamespaceURI();
				String name = inXML[i].getLocalName();
				if(namespace != null && !namespace.isEmpty()){
					name = "{" + namespace + "}" + name;
				}
				transformer.setParameter(name, inXML[i]);
			}
			
			if(gvNode != null){
				transformer.setParameter(gvNode.getLocalName(), gvNode);
			}

			RuleSession session = RuleSessionManager.getCurrentRuleSession();
	        RuleServiceProvider provider = session.getRuleServiceProvider();
	        TypeManager manager = provider.getTypeManager();
			Concept rootConcept = createConcept(session, manager, ceptURI);
			ConceptContentHandler ci = new ConceptContentHandler(rootConcept, session);
			SAXResult result = new SAXResult(ci);
			
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			Document newDocument = builder.newDocument();
			DOMSource source = new DOMSource(newDocument);

			transformer.transform(source, result);
			
			return rootConcept;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public SimpleEvent transform2Event(String xslt, String eventURI, String[] varNames, Object[] documents, Node gvNode, Node[] nodes){
		
		try {
			Transformer transformer = getTransformer(xslt);
			
			for (int i = 0; i < documents.length; i++) {
				Object object = documents[i];
				transformer.setParameter(varNames[i], object);
			}
			
			if(gvNode != null){
				transformer.setParameter(gvNode.getLocalName(), gvNode);
			}
			
			for(int i = 0; i < nodes.length; i++) {
				String namespace = nodes[i].getNamespaceURI();
				String name = nodes[i].getNodeName();
				if(namespace != null && !namespace.isEmpty()){
					name = "{" + namespace + "}" + name;
				}
				transformer.setParameter(name, nodes[i]);
			}

			RuleSession session = RuleSessionManager.getCurrentRuleSession();
	        RuleServiceProvider provider = session.getRuleServiceProvider();
	        TypeManager manager = provider.getTypeManager();
	        Logger logger = session.getRuleServiceProvider().getLogger(EventContentHandler.class);
			SimpleEvent rootEvent = createEvent(session, manager, eventURI);
			EventContentHandler ci = new EventContentHandler(rootEvent, logger);
			SAXResult result = new SAXResult(ci);
			
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
			Document newDocument = builder.newDocument();
			DOMSource source = new DOMSource(newDocument); 

			transformer.transform(source, result);
			
			return rootEvent;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String transform2XML(String xslt, String ceptURI, String[] inXML){
		
		try {
			Transformer transformer = getTransformer(xslt);
			for (int i = 0; i < inXML.length; i++) {
				String document = inXML[i];
				DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
				Document newDocument = builder.parse(new ByteArrayInputStream(document.getBytes("UTF-8")));
				Element docElement = newDocument.getDocumentElement();
				String name = docElement.getLocalName();
				//String namespace = document.getNamespaceURI();
				//String paramName = "{" + namespace + "}" + name;
				String paramName = name;
				transformer.setParameter(paramName, docElement);
			}
			DOMSource source = new DOMSource();
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			transformer.transform(source, result);

			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String transform2XML_1(String xslt, String ceptURI, String[] inXMLParams){
		
		try {
			Transformer transformer = getTransformer(xslt);
			for (int i = 0; i < inXMLParams.length; i++) {
				String document = inXMLParams[i];
				DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
				Document newDocument = builder.parse(new ByteArrayInputStream(document.getBytes("UTF-8")));
				Element docElement = newDocument.getDocumentElement();
				String name = docElement.getLocalName();
				//String namespace = document.getNamespaceURI();
				//String paramName = "{" + namespace + "}" + name;
				String paramName = name;
				if(paramName.equals("globalVariables")){
					System.out.println("Len:"+docElement.getElementsByTagName("*").getLength());
					transformer.setParameter(paramName, docElement.getElementsByTagName("*"));
				} else {
					transformer.setParameter(paramName, docElement);
				}
			}
			DOMSource source = new DOMSource();
			
			StringWriter writer = new StringWriter();
			Result result = new StreamResult(writer);

			transformer.transform(source, result);

			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Concept createConcept(RuleSession session, TypeManager manager, String ceptURI) throws Exception{
		Class<?> clz = manager.getTypeDescriptor(ceptURI).getImplClass();
        Concept rootConcept = null;
        if (clz != null) {
            long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
            Constructor<?> cons = clz.getConstructor(new Class[]{long.class});
            rootConcept = (Concept) cons.newInstance(new Object[]{new Long(id)});
        }
        return rootConcept;
	}
	
	private SimpleEvent createEvent(RuleSession session, TypeManager manager, String eventURI) throws Exception{
		Class<?> clz = manager.getTypeDescriptor(eventURI).getImplClass();
        SimpleEvent evt = null;
        if (clz != null) {
            if(SimpleEvent.class.isAssignableFrom(clz)) {
                long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
                Constructor<?> cons = clz.getConstructor(new Class[] {long.class});
                evt = (SimpleEvent)cons.newInstance(new Object[] {new Long(id)});
            }
        }
        return evt;
	}
	
	protected static Object[] paramToXMLNode(Object[] xsltParams,
			String[] varName, boolean[] isArray, DocumentBuilder docBuilder) {
		if(xsltParams == null){
			return new Object[0];
		}
		for (int i=0; i< xsltParams.length; i++) {
			Object o = xsltParams[i];
			if (o instanceof String || o instanceof Integer
					|| o instanceof Long || o instanceof Double
					|| o instanceof Boolean) {
				//Do Nothing
			} else if(o instanceof Calendar){
				xsltParams[i] = CalendarUtil.toString((Calendar)o);
			} else {
				XMLNodeBuilder builder = getDocumentBuilder(o, isArray[i]);
				if(builder != null) {
					Document doc = docBuilder.newDocument();
					Node rootElement = builder.build(doc, o, varName[i]);
					doc.appendChild(rootElement);
					xsltParams[i] = rootElement;
				}
			}
		}
		return xsltParams;
	}

	protected static Node[] xmlStringToXMLNode(String[] xsltXMLParams,
			DocumentBuilder docBuilder) throws UnsupportedEncodingException,
			SAXException, IOException {
		Node[] xmlParams = {};
		if(xsltXMLParams != null){
			xmlParams = new Node[xsltXMLParams.length];
			for (int i = 0; i < xsltXMLParams.length; i++) {
				String xmlString = (String) xsltXMLParams[i];
				if(xmlString != null && !xmlString.isEmpty()){
					ByteArrayInputStream stream = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
					Document doc1 = docBuilder.parse(stream);
					xmlParams[i] = doc1;
				}
			}
		}
		return xmlParams;
	}
	
	private static XMLNodeBuilder getDocumentBuilder(Object clz, boolean isArray){
		if(isArray){
			return new Array2XMLNodeBuilder();
		}
		if(clz instanceof Concept){
			return new ConceptXMLNodeBuilder();
		} else if(clz instanceof SimpleEvent){
			return new SimpleEventXMLNodeBuilder();
		} else if(clz instanceof GlobalVariables){
			return new GVXMLNodeBuilder();
		}
		return null;
	}
	
	protected static DocumentBuilderFactory getDocumentBuilderFactory() {
		return documentBuilderFactory;
	}
}
