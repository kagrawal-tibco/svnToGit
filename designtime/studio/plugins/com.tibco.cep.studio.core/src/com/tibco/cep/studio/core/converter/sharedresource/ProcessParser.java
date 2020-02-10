package com.tibco.cep.studio.core.converter.sharedresource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ProcessParser {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			//args = new String [1];
			//args[0] = "E:\\Tibco\\Projects\\Pioneer\\Pioneer_20081107\\DesignerProj\\PESBEDM\\PESBEDM\\SharedProcesses\\LAEClient\\LAEProcessError.process";
			System.out.println("There is no file to parse >>");
			return;
		}
		String filePath = args[0];
		parse(new File(filePath));
		System.out.println("file parsed successfully>>>");
	}
	
	private static void parse(File file ){
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder docbuiBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docbuiBuilder.parse(file);
			if (doc == null) return;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			NamespaceContext namespaceContext = new NameSpaceContextImpl();
			((NameSpaceContextImpl)namespaceContext).addMapping("pd", "http://xmlns.tibco.com/bw/process/2003");
			((NameSpaceContextImpl)namespaceContext).addMapping("xsl", "http://www.w3.org/1999/XSL/Transform");
			
			((NameSpaceContextImpl)namespaceContext).addMapping("xsd", "http://www.w3.org/2001/XMLSchema");
			((NameSpaceContextImpl)namespaceContext).addMapping("ns", "http://xmlns.pioneerinvestments.com/schemas/BusinessServices/Common/LAE/Exception/ExceptionInfo.xsd");
			
			((NameSpaceContextImpl)namespaceContext).addMapping("ns2", "http://xmlns.pioneerinvestments.com/schemas/BusinessServices/Common/LAE/LAEHeader.xsd");
			((NameSpaceContextImpl)namespaceContext).addMapping("ns1", "http://xmlns.pioneerinvestments.com/schemas/BusinessServices/Common/LAE/Exception/ExceptionService.xsd");
			
			((NameSpaceContextImpl)namespaceContext).addMapping("ns3", "http://www.tibco.com/pe/EngineTypes");
			((NameSpaceContextImpl)namespaceContext).addMapping("tib", "http://www.tibco.com/bw/xslt/custom-functions");
			
			((NameSpaceContextImpl)namespaceContext).addMapping("ns12", "http://www.tibco.com/pe/WriteToLogActivitySchema");
			((NameSpaceContextImpl)namespaceContext).addMapping("pfx", "http://xmlns.pioneerinvestments.com/schemas/BusinessServices/Common/LAE/Exception/ErrorDetails.xsd");
			
			xpath.setNamespaceContext(namespaceContext);
			// parse start and end nodes
			
			String pdName = xpath.evaluate("/pd:ProcessDefinition/pd:name", doc);
			String description = xpath.evaluate("/pd:ProcessDefinition/pd:description", doc);
			String startName = xpath.evaluate("/pd:ProcessDefinition/pd:startName", doc);
			String startDescription = xpath.evaluate("/pd:ProcessDefinition/pd:startDescription", doc);
			
			String endName = xpath.evaluate("/pd:ProcessDefinition/pd:endName", doc);
			String endDescription = xpath.evaluate("/pd:ProcessDefinition/pd:endDescription", doc);
			
			// parse all activity nodes
			
			NodeList activityNodeList = (NodeList)xpath.evaluate("/pd:ProcessDefinition/pd:activity", doc,XPathConstants.NODESET);
			if (activityNodeList != null){
				int length = activityNodeList.getLength();
				for (int i = 0 ; i < length ; ++i){
					Node activityNode = activityNodeList.item(i);
					String activityName = xpath.evaluate("@name", activityNode);
					String activityDescription = xpath.evaluate("pd:description", activityNode);
					String type = xpath.evaluate("pd:type", activityNode);
					String resourceType = xpath.evaluate("pd:resourceType", activityNode);
				}
			}
			
			// parse all transition nodes
			
			NodeList transitionNodeList = (NodeList)xpath.evaluate("/pd:ProcessDefinition/pd:transition", doc,XPathConstants.NODESET);
			if (transitionNodeList != null){
				int length = transitionNodeList.getLength();
				for (int i = 0 ; i < length ; ++i){
					Node transitionNode = transitionNodeList.item(i);
					String transDescription =  xpath.evaluate("pd:description", transitionNode);
					String from = xpath.evaluate("pd:from", transitionNode);
					String to = xpath.evaluate("pd:to", transitionNode);
					String lineType = xpath.evaluate("pd:lineType", transitionNode);
					String lineColor = xpath.evaluate("pd:lineColor", transitionNode);
					String conditionType = xpath.evaluate("pd:conditionType", transitionNode);
					
				}
			}
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	static final class NameSpaceContextImpl implements NamespaceContext {
		private final Map<String, List<String>> prefixURIMap = new HashMap<String, List<String>>();

		public String getNamespaceURI(String prefix) {
			Set<Map.Entry<String, List<String>>> entrySet = prefixURIMap
					.entrySet();
			for (Map.Entry<String, List<String>> entry : entrySet) {
				String URI = entry.getKey();
				List<String> prefixList = entry.getValue();
				if (prefix != null) {
					if (prefix.equals(prefixList.get(0))) {
						return URI;
					}
				}

			}
			return null;
		}

		public String getPrefix(String namespaceURI) {
			if (prefixURIMap.get(namespaceURI) != null) {
				return prefixURIMap.get(namespaceURI).get(0);
			}
			return null;
		}

		public Iterator getPrefixes(String namespaceURI) {
			return prefixURIMap.get(namespaceURI).iterator();
		}

		public void addMapping(String prefix, String URI) {
			if (prefix == null || URI == null)
				throw new IllegalArgumentException(
						"prefix or namespace URI can not be null");
			if (prefixURIMap.get(URI) == null) {
				List<String> prefixList = new ArrayList<String>();
				prefixList.add(prefix);
				prefixURIMap.put(URI, prefixList);
			} else {
				List<String> prefixList = prefixURIMap.get(URI);
				prefixList.add(prefix);
			}
		}

	}


}
