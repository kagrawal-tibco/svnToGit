package com.tibco.cep.runtime.service.tester.beunit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayBoolean;
import com.tibco.cep.runtime.model.element.PropertyArrayConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyArrayDouble;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;

/**
 * Helper class used in the BEUnit test framework to assist in the creation of 
 * Concepts and Events.  This supports JSON formatted strings as well as standard
 * Test Data files created through BusinessEvents Studio. 
 * @.category public-api
 * @author rhollom
 * 
 */
public class TestDataHelper {
	
	private enum Types {
		CONCEPT,
		EVENT,
		SCORECARD
	}
	
	TypeManager mgr;
	String projectFolder;
	TransformerFactory xmlOut = TransformerFactory.newInstance();
	Hashtable<String, Element> resources = new Hashtable<String, Element>();
	private static XPath _xPath = XPathFactory.newInstance().newXPath();

	/**
	 * Create a new instance of TestDataHelper
	 * @param projPath The path to the project
	 * @param mgr The TypeManager from the BETestEngine
	 * @.category public-api
	 */
	public TestDataHelper(String projPath, TypeManager mgr) {
		this.mgr = mgr;
		this.projectFolder = projPath;
	}

	/**
	 * Create a new instance of TestDataHelper
	 * @param engine The BETestEngine used during the unit test run
	 * @.category public-api
	 */
	public TestDataHelper(BETestEngine engine) {
		this.projectFolder = engine.getProjectLocation();
		this.mgr = engine.getTypeManager();
	}

	/**
	 * 
	 * @param eventURI The URI of the Event being created (i.e. '/Events/PromotionalEvent')
	 * @param json The JSON string describing the Event's properties.<br>For example:<br><code>"{\"PromotionalEvent\":{\"PriceAdjustment\":-250.0,\"SKU\":1234}}"</code>
	 * @return a newly created Event instance populated from the JSON string
	 * @throws Exception 
	 * @.category public-api
	 */
	public Event createEventFromJSON(String eventURI, String json) throws Exception {
		// use reflection here to avoid pulling in a ton of dependencies
		Class<?> clz = Class.forName("com.tibco.be.functions.event.EventHelper");
		Method method = clz.getMethod("createEventFromJSON", String.class, String.class);
		Object invoke = method.invoke(null, eventURI, json);
		return (Event) invoke;
	}
	
	/**
	 * 
	 * @param instanceURI The URI of the Concept being created (i.e. '/Concepts/Person')
	 * @param json The JSON string describing the Concept's properties.<br>For example:<br><code>"{\"Person\":{\"Age\":30,\"Name\":\"Jon\"}}"</code>
	 * @return a newly created Concept instance populated from the JSON string
	 * @throws Exception 
	 * @.category public-api
	 */
	public Concept createConceptFromJSON(String instanceURI, String json) throws Exception {
		// use reflection here to avoid pulling in a ton of dependencies
		Class<?> clz = Class.forName("com.tibco.be.functions.object.ObjectHelper");
		Method method = clz.getMethod("createInstanceFromJSON", String.class, String.class);
		Object invoke = method.invoke(null, instanceURI, json);
		return (Concept) invoke;
	}
	
	/**
	 * Creates a List of Concept instances from the specified test data file.
	 * If 'Use' is not selected for the test data row, the row will be ignored
	 * @param resourcePath The project relative path of the Concept data file (i.e. '/TestData/InventoryItem')
	 * @return The List of Concept instances created
	 * @throws Exception
	 * @.category public-api
	 */
	public List<Concept> createConceptsFromTestData(String resourcePath) throws Exception {
		List<Concept> results = new ArrayList<Concept>();

		Element root = getResource(resourcePath, Types.CONCEPT);
		String conceptUri = root.getAttribute("entityPath");
		if (conceptUri == null || conceptUri.length() == 0) {
			NamedNodeMap attributes = root.getFirstChild().getAttributes();
			for (int i = 0; i<attributes.getLength(); i++) {
				Node item = attributes.item(i);
				String nodeValue = item.getNodeValue();
				if (nodeValue != null && nodeValue.startsWith(Expecter.BE_NAMESPACE)) {
					conceptUri = nodeValue.substring(Expecter.BE_NAMESPACE.length());
					break;
				}
			}
		}
		NodeList concepts = root.getChildNodes();
		for (int i = 0; i < concepts.getLength(); i++) {
			Node concept = concepts.item(i);
			Node selectedNode = concept.getAttributes().getNamedItem("isSelected");
			if (selectedNode != null && "false".equals(selectedNode.getNodeValue())) {
				// item is not selected, do not include
				continue;
			}
			Concept c = createConceptShell(conceptUri);
			setExtId(c, (Element) concept);
			NodeList properties = concept.getChildNodes();
			for (int p = 0; p < properties.getLength(); p++) {
				setProperty(c, (Element) properties.item(p));
			}
			results.add(c);
		}
		return results;
	}
	
	/**
	 * Creates a List of Concept (Scorecard) instances from the specified test data file.
	 * If 'Use' is not selected for the test data row, the row will be ignored
	 * @param resourcePath The project relative path of the Concept/Scorecard data file (i.e. '/TestData/InventoryItem')
	 * @return The List of Concept (Scorecard) instances created
	 * @throws Exception
	 * @.category public-api
	 */
	public List<Concept> createScorecardsFromTestData(String resourcePath) throws Exception {
		List<Concept> results = new ArrayList<Concept>();

		Element root = getResource(resourcePath, Types.SCORECARD);//type null for scorecard
		String conceptUri = root.getAttribute("entityPath");
		if (conceptUri == null || conceptUri.length() == 0) {
			NamedNodeMap attributes = root.getFirstChild().getAttributes();
			for (int i = 0; i<attributes.getLength(); i++) {
				Node item = attributes.item(i);
				String nodeValue = item.getNodeValue();
				if (nodeValue != null && nodeValue.startsWith(Expecter.BE_NAMESPACE)) {
					conceptUri = nodeValue.substring(Expecter.BE_NAMESPACE.length());
					break;
				}
			}
		}
		NodeList concepts = root.getChildNodes();
		for (int i = 0; i < concepts.getLength(); i++) {
			Node concept = concepts.item(i);
			Node selectedNode = concept.getAttributes().getNamedItem("isSelected");
			if (selectedNode != null && "false".equals(selectedNode.getNodeValue())) {
				// item is not selected, do not include
				continue;
			}
			Concept c = createConceptShell(conceptUri);
			setExtId(c, (Element) concept);
			NodeList properties = concept.getChildNodes();
			for (int p = 0; p < properties.getLength(); p++) {
				setProperty(c, (Element) properties.item(p));
			}
			results.add(c);
		}
		return results;
	}

	/**
	 * Creates a List of Event instances from the specified test data file.
	 * If 'Use' is not selected for the test data row, the row will be ignored
	 * @param resourcePath The project relative path of the Event data file (i.e. '/TestData/PromotionalEvent')
	 * @return The List of Event instances created
	 * @throws Exception
	 * @.category public-api
	 */
	public List<SimpleEvent> createEventsFromTestData(String resourcePath) throws Exception {
		ArrayList<SimpleEvent> results = new ArrayList<SimpleEvent>();
		Element root = getResource(resourcePath, Types.EVENT);
		String uri = root.getAttribute("entityPath");
		if (uri == null || uri.length() == 0) {
			NamedNodeMap attributes = root.getFirstChild().getAttributes();
			for (int i = 0; i<attributes.getLength(); i++) {
				Node item = attributes.item(i);
				String nodeValue = item.getNodeValue();
				if (nodeValue != null && nodeValue.startsWith(Expecter.BE_NAMESPACE)) {
					uri = nodeValue.substring(Expecter.BE_NAMESPACE.length());
					break;
				}
			}
		}
		NodeList events = root.getChildNodes();
		for (int i = 0; i < events.getLength(); i++) {
			Node event = events.item(i);
			Node selectedNode = event.getAttributes().getNamedItem("isSelected");
			if (selectedNode != null && "false".equals(selectedNode.getNodeValue())) {
				// item is not selected, do not include
				continue;
			}
			SimpleEvent e = (SimpleEvent) this.mgr.createEntity(uri);
			setExtId(e, (Element) event);
			NodeList properties = event.getChildNodes();
			for (int p = 0; p < properties.getLength(); p++) {
				String nm = properties.item(p).getNodeName();
				if (nm.equalsIgnoreCase("payload")) {
					String payload = extractPayload(properties.item(p));
					e.setPayload(
							PayloadFactoryImpl.createPayload(this.mgr.getTypeDescriptor(uri), 1, payload.getBytes()));
				} else {
					String v = properties.item(p).getTextContent();
					if (v != null && v.length() > 0) {
						e.setProperty(nm, v);
					}
				}
			}
			results.add(e);
		}
		return results;
	}

	private String extractPayload(Node payload) throws TransformerException {
		StringWriter sw = new StringWriter();
		Transformer trans = this.xmlOut.newTransformer();
		trans.transform(new DOMSource(payload.getFirstChild()), new StreamResult(sw));
		return sw.toString();
	}

	private Concept createChildConcept(Element e) throws Exception {
		Element root = getResource(e.getAttribute("resourcePath"), Types.CONCEPT);
		int row = Integer.valueOf(e.getAttribute("rowNum")).intValue();
		String conceptUri = root.getAttribute("entityPath");

		NodeList concepts = root.getChildNodes();

		Node concept = concepts.item(row);
		Concept c = createConceptShell(conceptUri);
		setExtId(c, (Element) concept);
		NodeList properties = concept.getChildNodes();
		for (int p = 0; p < properties.getLength(); p++) {
			setProperty(c, (Element) properties.item(p));
		}
		return c;
	}

	private void setExtId(Entity c, Element e) {
		if (e.hasAttribute("extId")) {
			String v = e.getAttribute("extId");
			if (!v.trim().isEmpty()) {
				if ((c instanceof Concept)) {
					((Concept) c).setExtId(v.trim());
				} else {
					((SimpleEvent) c).setExtId(v.trim());
				}
			}
		}
	}

	private Concept createConceptShell(String uri) throws Exception {
		return (Concept) this.mgr.createEntity(uri);
	}

	private void setProperty(Concept c, Element e) throws DOMException, Exception {
		String pn = e.getNodeName();
		String v = e.getTextContent();

		Property p = c.getProperty(pn);
		if ((!(p instanceof Property.PropertyConcept)) && (v.isEmpty())) {
			return;
		}
		if ((p instanceof PropertyAtom)) {
			if ((p instanceof PropertyAtomString)) {
				((PropertyAtomString) p).setString(v);
			} else if ((p instanceof PropertyAtomBoolean)) {
				((PropertyAtomBoolean) p).setBoolean(Boolean.valueOf(v).booleanValue());
			} else if ((p instanceof PropertyAtomInt)) {
				((PropertyAtomInt) p).setInt(Integer.valueOf(v).intValue());
			} else if ((p instanceof PropertyAtomLong)) {
				((PropertyAtomLong) p).setLong(Long.valueOf(v).longValue());
			} else if ((p instanceof PropertyAtomDouble)) {
				((PropertyAtomDouble) p).setDouble(Double.valueOf(v).doubleValue());
			} else if ((p instanceof PropertyAtomDateTime)) {
				((PropertyAtomDateTime) p).setDateTime(toCalendar(v));
			} else if (((p instanceof Property.PropertyContainedConcept))
					&& (!e.getAttribute("resourcePath").isEmpty())) {
				c.setPropertyValue(pn, createChildConcept(e));
			}
		} else if ((p instanceof PropertyArrayString)) {
			((PropertyArrayString) p).add(v);
		} else if ((p instanceof PropertyArrayBoolean)) {
			((PropertyArrayBoolean) p).add(Boolean.valueOf(v));
		} else if ((p instanceof PropertyArrayInt)) {
			((PropertyArrayInt) p).add(Integer.valueOf(v));
		} else if ((p instanceof PropertyArrayLong)) {
			((PropertyArrayLong) p).add(Long.valueOf(v));
		} else if ((p instanceof PropertyArrayDouble)) {
			((PropertyArrayDouble) p).add(Double.valueOf(v));
		} else if ((p instanceof PropertyArrayDateTime)) {
			((PropertyArrayDateTime) p).add(toCalendar(v));
		} else if (((p instanceof PropertyArrayContainedConcept)) && (!e.getAttribute("resourcePath").isEmpty())) {
			((PropertyArrayConcept) p).add(createChildConcept(e));
		}
	}

	private Calendar toCalendar(String v) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(TestUtils.parseDateTime(v));
		return cal;
	}

	private Element getResource(String resourcePath, Types type) throws Exception {
		Element e = (Element) this.resources.get(resourcePath);
		if (e == null) {
			String path = null;
			boolean fromEar = new File(this.projectFolder).isFile();
			switch (type) {
			case CONCEPT:
				path = resourcePath + ".concepttestdata";
				break;

			case EVENT:
				path = resourcePath + ".eventtestdata";
				break;

			case SCORECARD:
				path = resourcePath + ".scorecardtestdata";
				break;
			}
			
			if (fromEar) {
				e = loadResourceFromEar(path);
			} else {
				e = parseXML(new FileInputStream(this.projectFolder + path), "testdata");
			}
			if (e == null) {
				throw new FileNotFoundException("Test data file '"+path+"' not found");
			}
			this.resources.put(resourcePath, e);
		}
		return e;
	}

	private Element loadResourceFromEar(String path) throws IOException {
		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}
		InputStream inputStream = null;
		ZipInputStream zipInputStream = null;
		ZipFile earFile = null;
		try {
			earFile = new ZipFile(this.projectFolder);
			ZipEntry entry = earFile.getEntry("Shared Archive.sar");
			if (entry != null) {
				inputStream = earFile.getInputStream(entry);
				zipInputStream = new ZipInputStream(inputStream);
				ZipEntry ze = null;
				while ((ze = zipInputStream.getNextEntry()) != null) {
					if (path.equals(ze.getName())) {
						ByteArrayInputStream bis = null;
						ByteArrayOutputStream bos = null;
						try {
							bos = new ByteArrayOutputStream();
							for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
								bos.write(c);
							}
							bis = new ByteArrayInputStream(bos.toByteArray());
							return parseXML(bis, "testdata");
						} catch (Exception e) {
						} finally {
							bis.close();
						}
					}
				}
			}
		} finally {
			earFile.close();
		}
		return null;
	}

	private Element parseXML(InputStream is, String root)
			throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
		Element rootEle = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(is);
			rootEle = (Element) _xPath.compile(root).evaluate(doc, XPathConstants.NODE);
		} finally {
			is.close();
		}
		return rootEle;
	}
}
