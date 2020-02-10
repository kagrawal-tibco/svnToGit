package com.tibco.cep.sharedresource.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Dec 22, 2009 7:12:49 PM
 */

public abstract class AbstractSharedResourceModelParser {

	public void loadModel(String filePath, SharedResModelMgr modelmgr) {
		String sharedResFolder=null;
		if (filePath == null || new File(filePath).length() == 0){
			if(filePath !=null && filePath.contains(".projlib")){
				String sharedResFile= filePath.split("/")[(filePath.split("/")).length-1];
				sharedResFolder = filePath.split(".projlib")[0];
				filePath=getSharedResource(filePath,sharedResFolder,sharedResFile);
			}
			else{
				return;
			}
		}
		try {
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			fact.setNamespaceAware(true);
			DocumentBuilder builder = fact.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(filePath); 
			Document doc = builder.parse(fis);
			loadModel(doc, modelmgr);
			cleanTemporaryFiles(new File(sharedResFolder+"_temp"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanTemporaryFiles(File file){
    	if(file.isDirectory()){
    		if(file.list().length==0){
		   		file.delete();
		 	}else{
		 		String files[] = file.list();
		   	    for (String temp : files) {
		   	    	File fileDelete = new File(file, temp);
		   	    	cleanTemporaryFiles(fileDelete);
		   	    }
		    	if(file.list().length==0){
		            file.delete();
		        }
		    }
		 
    	}else{
    		file.delete();
    	}
	}
	
	public static String getSharedResource(String filePath, String sharedResFolder,String sharedResFile) {
		 byte[] buffer = new byte[1024];
	     try{
	    	File folder = new File(sharedResFolder+"_temp");
	    	if(!folder.exists()){
	    		folder.mkdir();
	    	}
	 
	    	ZipInputStream zis = new ZipInputStream(new FileInputStream(sharedResFolder+".projlib"));
	    	ZipEntry ze = zis.getNextEntry();
	    	String fileName=null;
	    	while(ze!=null){
	    	   fileName = ze.getName();
	    	   if(fileName.contains(sharedResFile)){
	    		   File newFile = new File(sharedResFolder+"_temp" + File.separator + fileName);
                   new File(newFile.getParent()).mkdirs();
                   FileOutputStream fos = new FileOutputStream(newFile);             
                   int len;
                   while ((len = zis.read(buffer)) > 0) {
                	   fos.write(buffer, 0, len);
                   }
                   fos.close();
                   break;
	    	   }
	            ze = zis.getNextEntry();
	    	}
	        zis.closeEntry();
	    	zis.close();
	    	return sharedResFolder+"_temp" + File.separator + fileName;
	     }catch(Exception e){
	    	 
	     }
		return sharedResFolder+"_temp";
	}

	public abstract void loadModel(Document doc, SharedResModelMgr modelmgr);
	
	public abstract void saveModelParts(Document doc, SharedResModelMgr modelmgr);
	
	protected static boolean isValidFileNode(Node node) {
		return (isValidFileNode(node.getNodeName()));
	}
	
	protected static boolean isValidFileNode(String name) {
		if (name==null)
			return false;
		String ignList[] = { "#text", "#comment" };
		for (String ign: ignList) {
			if (ign.equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}
	
	protected static Map<String, String> processNodeAttributes(NamedNodeMap attr) {
		if (attr == null)
			return null;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int n=0; n<attr.getLength(); n++) {
			Node node = (Node) attr.item(n);
			String key = node.getNodeName();
			String value = node.getNodeValue();
			map.put(key, value);
		}
		return map;
	}
	
	protected static boolean getBoolean(String str) {
		return (new Boolean(str).booleanValue());
	}
	
	protected static String getString(Boolean en) {
		return (new Boolean(en).toString());
	}
	
	public static Document getSaveDocument(NamedNodeMap map, String mainPath) {
		Document doc = PersistenceUtil.getSaveDocument(mainPath);
		Element root = doc.getDocumentElement();
		Map<String, String> attrMap = processNodeAttributes(map);
		if (attrMap != null) {
			for (Map.Entry<String, String> attr: attrMap.entrySet()) {
				root.setAttribute(attr.getKey(), attr.getValue());
			}
		}
		return doc;
	}
	
	public static Document getSaveDocument(NamedNodeMap map, String mainPath, String namespaceURI, String prefix) {
		Document doc = PersistenceUtil.getSaveDocument(mainPath, namespaceURI, prefix);
		Element root = doc.getDocumentElement();
		Map<String, String> attrMap = processNodeAttributes(map);
		if (attrMap != null) {
			for (Map.Entry<String, String> attr: attrMap.entrySet()) {
				root.setAttribute(attr.getKey(), attr.getValue());
			}
		}
		return doc;
	}

	protected static Element createTextElementNode(Document doc, Node parentNode, String nodeName, String value) {
		Element node = doc.createElement(nodeName);
		node.setTextContent(value);
		parentNode.appendChild(node);
		return node;
	}
	
	protected static void createMapNode(Document doc, Node parentNode, Map<String, Object> map) {
		if (map == null)
			return;
		for (Map.Entry<String, Object> entry: map.entrySet()) {
			Element node = doc.createElement(entry.getKey());
			node.setTextContent(entry.getValue().toString());
			parentNode.appendChild(node);
		}
	}
	
	protected static Node createMapElementNode(Document doc, Node parentNode, SharedResModelMgr modelmgr, String key) {
		return createTextElementNode(doc, parentNode, key, modelmgr.getStringValue(key));
	}
}
