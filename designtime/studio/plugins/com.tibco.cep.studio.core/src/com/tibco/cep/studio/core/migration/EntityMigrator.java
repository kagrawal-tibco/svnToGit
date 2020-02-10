package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.IProgressMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.be.util.config.ConfigNS.Elements;

/*
@author ssailapp
@date Feb 16, 2011
 */

public class EntityMigrator extends DefaultStudioProjectMigrator {

	private static Transformer transformer;
	private LinkedHashMap<String, String> translationMap;
	private LinkedHashMap<String, String> entityProps;
	private LinkedHashMap<String,LinkedHashMap<String, String>> propProps;
	private String entityPath;
	protected String extension = "";
	
	public EntityMigrator() {
		initializePropertyNameTranslationMap();
	}
		
	private void initializePropertyNameTranslationMap() {
		translationMap = new LinkedHashMap<String, String>();
	
		// Backing Store
		//translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_TYPENAME, "Type Name");	// ignored
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_TABLENAME, Elements.TABLE_NAME.localName);
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE, Elements.ENABLED.localName);

		// Cache
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK, Elements.CHECK_FOR_VERSION.localName);
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_CONSTANT, Elements.CONSTANT.localName);
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_EVICTONUPDATE, Elements.EVICT_ON_UPDATE.localName);
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_ISCACHELIMITED, Elements.CACHE_LIMITED.localName);		
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE, Elements.PRE_LOAD_FETCH_SIZE.localName);
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_ALL, Elements.PRE_LOAD_ENABLED.localName);
		
		// Per property definitions
		translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH, Elements.MAX_SIZE.localName);
		//translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME, "Column Name");	// ignored
		//translationMap.put(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_NESTEDTABLENAME, "Nested Table Name");	// ignored
		
		// ******* Definitions, for alternate matching	*******
		// Backing Store
		translationMap.put("table name", Elements.TABLE_NAME.localName);
		//translationMap.put("type name", "Type Name");	// ignored
		translationMap.put("hasbackingstore", Elements.ENABLED.localName);
		translationMap.put("has backing store", Elements.ENABLED.localName);	//this is a variant, used in UI
		
		// Cache
		translationMap.put("check for version", Elements.CHECK_FOR_VERSION.localName);
		translationMap.put("constant", Elements.CONSTANT.localName);
		translationMap.put("evict from cache on update", Elements.EVICT_ON_UPDATE.localName);
		translationMap.put("is cache limited", Elements.CACHE_LIMITED.localName);
		translationMap.put("maximum records to load on recovery", Elements.PRE_LOAD_FETCH_SIZE.localName);
		translationMap.put("pre load on recovery", Elements.PRE_LOAD_ENABLED.localName);
		translationMap.put("preload on recovery", Elements.PRE_LOAD_ENABLED.localName);	//this is a variant, used in UI
		
		// Per property definitions
		translationMap.put("max length", Elements.MAX_SIZE.localName);
		translationMap.put("index", Elements.INDEX.localName);
		translationMap.put("reverse ref", Elements.REVERSE_REFERENCES.localName);
		//translationMap.put("Column Name", "Column Name");	// ignored
		//translationMap.put("Nested Table Name", "Nested Table Name");	// ignored
	}

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if (!extension.equalsIgnoreCase(ext)) {
			return;
		}
		
		entityPath = getEntityPath(file);
		monitor.subTask("- Converting entity file "+file.getName());
		processEntityFile(parentFile, file, monitor);
	}

	private String getEntityPath(File file) {
		String filePath = file.getAbsolutePath().replace('\\', '/');
		String projectPath = projectLocation.getAbsolutePath();
		projectPath = projectPath.replace('\\', '/');
		String entityPath = filePath.replaceFirst(projectPath, "");
		entityPath = entityPath.replaceAll("."+extension, "");
		return entityPath;
	}
	
	protected void processEntityFile(File parentFile, File file, IProgressMonitor monitor) {
		entityProps = new LinkedHashMap<String, String>();
		propProps = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		intializePropsFromEntity(file.getPath());
		updateCddFiles(monitor);
		transformEntityFile(parentFile, file);
	}
	
	public void intializePropsFromEntity(String filePath) {
		if (filePath == null || new File(filePath).length() == 0)
			return;
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath);
			Document doc = builder.parse(fis);
			loadModel(doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadModel(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList fileNodeList = root.getChildNodes();
        for (int n=0; n<fileNodeList.getLength(); n++) {
        	Node fileNode = fileNodeList.item(n);
        	if (isValidFileNode(fileNode, "extendedProperties")) {
	        	NodeList objPropNodeList = fileNode.getChildNodes();
	        	for (int op=0; op<objPropNodeList.getLength(); op++) {
	        		Node objPropNode = objPropNodeList.item(op);
	        		if (!isValidFileNode(objPropNode, "properties"))
	        			continue;
	        		NodeList valueNodeList = objPropNode.getChildNodes();
	            	for (int v=0; v<valueNodeList.getLength(); v++) {
	            		Node valueNode = valueNodeList.item(v);
	            		if (!isValidFileNode(valueNode, "value"))
	            			continue;
	            		NodeList simplePropNodeList = valueNode.getChildNodes();
	                	for (int sp=0; sp<simplePropNodeList.getLength(); sp++) {
	                		Node simplePropNode = simplePropNodeList.item(sp);
	                		if (!isValidFileNode(simplePropNode, "properties"))
	                			continue;
	                		processSimplePropertyNode(simplePropNode, entityProps);
	                	}
	            	}
	        	}
        	} else if (isValidFileNode(fileNode, "properties")) {
        		Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNode.getAttributes(), false);
        		String entityPropName = fileNodeAttrMap.get(Elements.NAME.localName);
        		LinkedHashMap<String, String> propPropsMap = new LinkedHashMap<String, String>();
        		NodeList extPropNodeList = fileNode.getChildNodes();
	        	for (int ep=0; ep<extPropNodeList.getLength(); ep++) {
	        		Node extPropNode = extPropNodeList.item(ep);
	        		if (!isValidFileNode(extPropNode, "extendedProperties"))
	        			continue;
	        		NodeList objPropNodeList = extPropNode.getChildNodes();
		        	for (int op=0; op<objPropNodeList.getLength(); op++) {
		        		Node objPropNode = objPropNodeList.item(op);
		        		if (!isValidFileNode(objPropNode, "properties"))
		        			continue;
		        		Map<String, String> objPropNodeAttrMap = processNodeAttributes(objPropNode.getAttributes(), false);
		        		String type = objPropNodeAttrMap.get("type");
		        		if (type != null && type.equals("designtime:SimpleProperty")) {
		        			processSimplePropertyNode(objPropNode, propPropsMap);
		        		} else {
			        		NodeList valueNodeList = objPropNode.getChildNodes();
			            	for (int v=0; v<valueNodeList.getLength(); v++) {
			            		Node valueNode = valueNodeList.item(v);
			            		if (!isValidFileNode(valueNode, "value"))
			            			continue;
			            		NodeList simplePropNodeList = valueNode.getChildNodes();
			                	for (int sp=0; sp<simplePropNodeList.getLength(); sp++) {
			                		Node simplePropNode = simplePropNodeList.item(sp);
			                		if (!isValidFileNode(simplePropNode, "properties"))
			                			continue;
			                		processSimplePropertyNode(simplePropNode, propPropsMap);
			                	}
			            	}
		        		}
		        	}
	        	}
	        	propProps.put(entityPropName, propPropsMap);
        	}
        }
	}
	
	private void processSimplePropertyNode(Node simplePropNode, LinkedHashMap<String, String> propsMap) {
		Map<String, String> simplePropNodeAttrMap = processNodeAttributes(simplePropNode.getAttributes(), false);
		String propName = simplePropNodeAttrMap.get("name");
		String propValue = simplePropNodeAttrMap.get("value");
		
		String transPropName = translationMap.get(propName);
		if (transPropName != null) {
			propsMap.put(transPropName, propValue);
		} else {
			if (propName.indexOf('[') != -1) {
				String toks[] = propName.split("\\[");
				propName = toks[0];
			}
			propName = propName.trim().toLowerCase();
			propName = translationMap.get(propName);
			if (propName != null) {
	    		propsMap.put(propName, propValue);
			}
		}
	}
	
	private LinkedHashMap<String, String> processNodeAttributes(NamedNodeMap attr, boolean isNodeName) {
		if (attr == null)
			return null;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int n=0; n<attr.getLength(); n++) {
			Node node = (Node) attr.item(n);
			String key = node.getLocalName();
			if (isNodeName)
				key = node.getNodeName();
			String value = node.getNodeValue();
			map.put(key, value);
		}
		return map;
	}
	
	private boolean isValidFileNode(Node node, String expectedName) {
		if (node==null)
			return false;
		String name = node.getLocalName();
		if (name==null)
			return false;
		String ignList[] = { "#text", "#comment" };
		for (String ign: ignList) {
			if (ign.equalsIgnoreCase(name)) {
				return false;
			}
		}
		if (expectedName != null && !expectedName.equals(name))
			return false;
		return true;
	}
	
	protected void transformEntityFile(File parentFile, File file) {
		Transformer transformer = getTransformer();
		if (transformer == null) {
			System.err.println("Could not initialize entity transformer, Entities not converted");
			return;
		}

		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(file);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			tmpFile = new File(file.getAbsolutePath()+"_tmp");
			// TODO : check whether this file already exists?
			os = new FileOutputStream(tmpFile);
			transformer.transform(new StreamSource(is), new StreamResult(os));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e2) {
			}
		}
		if (tmpFile != null) {
			if (file.exists()) {
				file.delete();
			}
			tmpFile.renameTo(file);
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
		}
	}

	private void updateCddFiles(IProgressMonitor monitor) {
		/*
		//Use value from "Preload Entities" for "Preload Handles" as well.
		String preloadEnabled = entityProps.get(Elements.PRE_LOAD_ENABLED.localName);
		if (preloadEnabled != null)
			entityProps.put(Elements.PRE_LOAD_HANDLES.localName, preloadEnabled);
		*/
		CddDomainObjectMigrator cddDomainObjectMigrator = new CddDomainObjectMigrator(entityPath, entityProps, propProps);
		cddDomainObjectMigrator.processDirectory(projectLocation, monitor);
	}
	
	private Transformer getTransformer() {
//		System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");	
		if (transformer == null) {
			InputStream inputStream = EntityMigrator.class.getResourceAsStream("Entity4ToEntity5.xslt");
			if (inputStream == null)
				return null;
			try {
				StreamSource streamSource = new StreamSource(inputStream);
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				transformer = templates.newTransformer();
				transformer.setParameter("indent", "yes");
			} catch (TransformerConfigurationException e1) {
				e1.printStackTrace();
			}
		}
		return transformer;
	}
	
	@Override
	public int getPriority() {
		return 2;
	}
}
