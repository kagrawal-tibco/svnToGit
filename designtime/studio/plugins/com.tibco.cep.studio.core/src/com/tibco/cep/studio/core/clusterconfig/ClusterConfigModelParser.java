package com.tibco.cep.studio.core.clusterconfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.be.util.config.ConfigNS;
import com.tibco.be.util.config.ConfigNS.Attributes;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.be.util.config.cdd.BackingStoreConfig;
import com.tibco.cep.liveview.project.ILiveViewProjectGenerator;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.AgentClass;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.BackingStore;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.CacheOmSecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.Connection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectCompositeIndex;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.CacheOm.DomainObject.DomainObjectProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcAgent.AgentRulesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentBaseFunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentDestinationsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashInfProcQueryAgent.AgentFunctionsGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DashboardAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DbConcepts;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Destination;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.DestinationsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Function;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.FunctionsGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.InfAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.EntitySetConfig.EntityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LiveViewAgent.LDMConnection;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerAdhocConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfigs;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LogConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.Alert;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.ExecCommand;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.HealthLevel;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.ActionConfigList.ActionConfig.SendEmail;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.AlertConfigList.AlertConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.ChildClusterMember;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.Notification;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.MMAgent.RuleConfigList.ClusterMember.SetProperty.SetPropertyChildElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ObjectManagement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessAgent.AgentProcessesGrpElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessingUnit.ProcessingUnitSecurityConfig;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Property;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyElementList;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.PropertyGroup;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.QueryAgent;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Rule;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RuleElement;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.RulesGrp;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.SecurityConfig;
import com.tibco.cep.studio.core.util.PersistenceUtil;

/*
@author ssailapp
@date Nov 29, 2009 11:17:52 AM
 */

public class ClusterConfigModelParser {
	
	private static final String NAMESPACE = ConfigNS.URL;

	public static void loadModel(String filePath, ClusterConfigModelMgr modelmgr) {
		try {
			if (filePath == null || new File(filePath).length() == 0)
				return;
			FileInputStream fis = new FileInputStream(filePath);
			loadModel(fis, modelmgr);
		} catch (FileNotFoundException e) {
			StudioCorePlugin.log(e);
			e.printStackTrace();
		}
	}

	public static void loadModelFromString(String docString, ClusterConfigModelMgr modelmgr) {
		try {
			InputStream is = new ByteArrayInputStream(docString.getBytes("UTF-8"));
			loadModel(is, modelmgr);
		} catch (UnsupportedEncodingException e) {
			StudioCorePlugin.log(e);
			e.printStackTrace();
		}
	}
	
	private static void loadModel(InputStream is, ClusterConfigModelMgr modelmgr) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			loadModel(doc, modelmgr);
		} catch (ParserConfigurationException e) {
			StudioCorePlugin.log(e);
			e.printStackTrace();
		} catch (SAXException e) {
			StudioCorePlugin.log(e);
			e.printStackTrace();
		} catch (IOException e) {
			StudioCorePlugin.log(e);
			e.printStackTrace();
		}
	}
	
	private static void loadModel(Document doc, ClusterConfigModelMgr modelmgr) {
        Element root = doc.getDocumentElement();
        modelmgr.setRootAttributes(root.getAttributes());
        NodeList fileNodeList = root.getChildNodes();
        for (int n=0; n<fileNodeList.getLength(); n++) {
        	Node fileNode = fileNodeList.item(n);
        	if (!isValidFileNode(fileNode)) {
        		continue;
        	}
        	String fileNodeName = fileNode.getLocalName();
        	/*
        	if (fileNodeName.equals(Elements.REVISION.localName)) {
        		processRevision(modelmgr, fileNode);
        	} else */ 
        	if (fileNodeName.equals(Elements.NAME.localName)) {
        		processClusterName(modelmgr, fileNode);
        	} else if (fileNodeName.equals(Elements.MESSAGE_ENCODING.localName)) {
        		processMessageEncoding(modelmgr, fileNode);
        	} else if (fileNodeName.equals(Elements.LOG_CONFIGS.localName)) {
        		processLogConfigs(modelmgr, fileNode);
        	} else if (fileNodeName.equals(Elements.PROCESSING_UNITS.localName)) {
        		processProcessingUnits(modelmgr, fileNode);
        	} else if (fileNodeName.equals(LoadBalancerConfig.ELEMENT_LOAD_BALANCER_CONFIGS)) {
        		processLoadBalancerConfigs(modelmgr, fileNode);
        	} else if (fileNodeName.equals(Elements.PROPERTY_GROUP.localName)) {
        		processPropertyNodes(modelmgr, fileNode);
        	} else {
        		NodeList childFileNodeList = fileNode.getChildNodes();
        		processNode(fileNodeName, childFileNodeList, modelmgr, true);
        	}
        }
        postProcessModel(modelmgr);
	}
	
	private static void processNode(String path, NodeList fileNodeList, ClusterConfigModelMgr modelmgr, boolean root) {
        if (fileNodeList == null)
        	return;
		for (int n=0; n<fileNodeList.getLength(); n++) {
			if (root)
				modelmgr.initAttrPath();
			Node fileNode = fileNodeList.item(n);
        	if (fileNode == null || !isValidFileNode(fileNode))
        		continue;
        	String fileNodeName = fileNode.getLocalName();
        	String fileNodeValue = fileNode.getTextContent();
        	NamedNodeMap fileNodeAttr = fileNode.getAttributes();
        	Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNodeAttr);
        	if (fileNodeName != null && fileNodeValue != null) {
        		if (path.equals(Elements.OBJECT_MANAGEMENT.localName) && fileNodeName.equals(Elements.CACHE_MANAGER.localName)) {
        			processCacheOm(modelmgr, fileNode);
           		} else if (path.equals(Elements.OBJECT_MANAGEMENT.localName) && fileNodeName.equals(Elements.DB_CONCEPTS.localName)) {        			
        			processDbConcepts(modelmgr, fileNode);
           		} else if (path.equals(Elements.AGENT_CLASSES.localName) && fileNodeName.equals(Elements.MM_AGENT_CLASS.localName)) {
           			processMMAgentClass(modelmgr, fileNode);
           		/*
           		} else if (path.equals(Elements.AGENT_CLASSES.localName) && fileNodeName.equals(Elements.PROCESS_AGENT_CLASS.localName)) {
           			processProcessAgentClass(modelmgr, fileNode);
           		*/
          		} else if (fileNodeName.equals(Elements.DESTINATION.localName) && (path.startsWith("agent-classes.") && path.endsWith(".destinations"))) {
        			processAgentClassDestination(modelmgr, fileNode, fileNodeAttrMap);
        		} else if (path.startsWith("agent-classes.") && fileNodeName.equals(Elements.PROPERTY_GROUP.localName)) { 
        			processAgentClassPropertyNodes(modelmgr, fileNode);
        		} else {
	        		modelmgr.initializeModel(path, fileNodeAttrMap, fileNodeName, fileNodeValue);
	        		NodeList fileSubNodeList = fileNode.getChildNodes();
	        		String fileNodePath = path + "." + fileNodeName;
	        		processNode(fileNodePath, fileSubNodeList, modelmgr, false);
        		}
        	}
        }
	}
	
	private static void processClusterName(ClusterConfigModelMgr modelmgr, Node fileNode) {
		modelmgr.setClusterName(fileNode.getTextContent());
	}

	private static void processMessageEncoding(ClusterConfigModelMgr modelmgr, Node fileNode) {
		modelmgr.setMessageEncoding(fileNode.getTextContent());
	}

	private static void processLogConfigs(ClusterConfigModelMgr modelmgr, Node logConfigsNode) {
		NodeList logConfigNodeList = logConfigsNode.getChildNodes();
		for (int n=0; n<logConfigNodeList.getLength(); n++) {
			Node logConfigNode = logConfigNodeList.item(n);
			if (!isValidFileNode(logConfigNode))
				continue;
        	NamedNodeMap fileNodeAttr = logConfigNode.getAttributes();
        	Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNodeAttr);
        	String logConfigId = fileNodeAttrMap.get("id");
        	LogConfig logConfig = modelmgr.initLogConfig(logConfigId);
        	NodeList logConfigChdList = logConfigNode.getChildNodes();
    		for (int c=0; c<logConfigChdList.getLength(); c++) {
    			Node logConfigChdNode = logConfigChdList.item(c);
    			if (!isValidFileNode(logConfigChdNode))
    				continue;
    			String logConfigChdNodeName = logConfigChdNode.getLocalName();
    			if (logConfigChdNodeName.equals(Elements.ENABLED.localName)) {
    				logConfig.enabled = modelmgr.getBooleanValue(logConfigChdNode.getTextContent());
    			} else if (logConfigChdNodeName.equals(Elements.ROLES.localName)) {
    				logConfig.roles = logConfigChdNode.getTextContent();
    			} else if (logConfigChdNodeName.equals(Elements.FILES.localName)) {
    				processLogConfigChildNode(modelmgr, logConfig.files, logConfigChdNode);
    			} else if (logConfigChdNodeName.equals(Elements.LINE_LAYOUT.localName)) {
    				processLogConfigChildNode(modelmgr, logConfig.layout, logConfigChdNode);
    			} else if (logConfigChdNodeName.equals(Elements.TERMINAL.localName)) {
    				processLogConfigChildNode(modelmgr, logConfig.terminal, logConfigChdNode);
    			}
    		}
 		}
	}
	
	private static void processLogConfigChildNode(ClusterConfigModelMgr modelmgr, Map<String, String> logConfigMap, Node logConfigChdNode) {
		NodeList logConfigChdPropertyNodeList = logConfigChdNode.getChildNodes();
		for (int n=0; n<logConfigChdPropertyNodeList.getLength(); n++) {
			Node logConfigChdPropertyNode = logConfigChdPropertyNodeList.item(n);
			if (!isValidFileNode(logConfigChdPropertyNode))
				continue;
			String logConfigChdPropertyName = logConfigChdPropertyNode.getLocalName();
			String logConfigChdPropertyValue = logConfigChdPropertyNode.getTextContent();
			logConfigMap.put(logConfigChdPropertyName, logConfigChdPropertyValue);
		}
	}
	
	private static void processLoadBalancerConfigs(ClusterConfigModelMgr modelmgr, Node lbNode) {
		NodeList lbConfigsNodeList = lbNode.getChildNodes();
		for (int n=0; n<lbConfigsNodeList.getLength(); n++) {
			Node lbConfigNode = lbConfigsNodeList.item(n);
			if (!isValidFileNode(lbConfigNode))
				continue;
			if (lbConfigNode.getNodeName().equals(LoadBalancerConfig.ELEMENT_PAIR_CONFIGS)) {
				processLoadBalancerPairConfigs(modelmgr, lbConfigNode, modelmgr.getModel().loadBalancerPairConfigs);
			} else if (lbConfigNode.getNodeName().equals(LoadBalancerConfig.ELEMENT_ADHOC_CONFIGS)) {
				processLoadBalancerAdhocConfigs(modelmgr, lbConfigNode, modelmgr.getModel().loadBalancerAdhocConfigs);
			}
 		}
	}
	
	private static void processLoadBalancerPairConfigs(ClusterConfigModelMgr modelmgr, Node lbConfigsNode, LoadBalancerPairConfigs pairConfigs) {
		NodeList lbConfigNodeList = lbConfigsNode.getChildNodes();
		for (int n=0; n<lbConfigNodeList.getLength(); n++) {
			Node lbConfigNode = lbConfigNodeList.item(n);
			if (!isValidFileNode(lbConfigNode))
				continue;
			LoadBalancerPairConfig config = modelmgr.getModel().new LoadBalancerPairConfig();
			processLoadBalancerConfigNode(modelmgr, lbConfigNode, config);
			pairConfigs.configs.add(config);
		}
	}

	private static void processLoadBalancerAdhocConfigs(ClusterConfigModelMgr modelmgr, Node lbConfigsNode, LoadBalancerAdhocConfigs adhocConfigs) {
		NodeList lbConfigNodeList = lbConfigsNode.getChildNodes();
		for (int n=0; n<lbConfigNodeList.getLength(); n++) {
			Node lbConfigNode = lbConfigNodeList.item(n);
			if (!isValidFileNode(lbConfigNode))
				continue;
			LoadBalancerAdhocConfig config = modelmgr.getModel().new LoadBalancerAdhocConfig();
			processLoadBalancerConfigNode(modelmgr, lbConfigNode, config);
			adhocConfigs.configs.add(config);
		}
	}
	
	private static void processLoadBalancerConfigNode(ClusterConfigModelMgr modelmgr, Node lbConfigNode, LoadBalancerConfig config) {
		if (!isValidFileNode(lbConfigNode))
			return;
		NodeList propNodeList = lbConfigNode.getChildNodes();
		for (int j=0; j<propNodeList.getLength(); j++) {
			Node propNode = propNodeList.item(j);
			if (!isValidFileNode(propNode))
				continue;
			String name = propNode.getNodeName();
			if(name.equals("property-group")){
				processPropertyGrpNode(modelmgr, propNode,config.properties, null);
			}else{
				String value = propNode.getTextContent(); 
				config.values.put(name, value);
			}
		}
	}
	
	private static void processAgentClassDestination(ClusterConfigModelMgr modelmgr, Node node, Map<String, String> fileNodeAttrMap) {
		String agentId = modelmgr.getAttrPath()[0];
		DashInfProcQueryAgent agentClass = (DashInfProcQueryAgent) modelmgr.getAgentClass(agentId);
		if (agentClass == null)
			return;
		String epId = "Destination-" + agentClass.agentDestinationsGrpObj.agentDestinations.size(); //Default value
		Node idNode = node.getAttributes().getNamedItem("id");
		if (idNode != null)
			epId = idNode.getNodeValue();
		Destination destination = modelmgr.initDestination(agentClass, epId);
		NodeList fileSubNodeList = node.getChildNodes();
		for (int n=0; n<fileSubNodeList.getLength(); n++) {
			Node chdNode = fileSubNodeList.item(n);
			if (!isValidFileNode(chdNode))
				continue;
			String key = chdNode.getLocalName();
			String value = chdNode.getTextContent();
			destination.destinationVal.put(key, value);
		}
	}

	private static void processAgentClassPropertyNodes(ClusterConfigModelMgr modelmgr, Node fileNode) {
		String agentId = modelmgr.getAttrPath()[0];
		AgentClass agentClass = modelmgr.getAgentClass(agentId);
		if (agentClass == null)
			return;
		processPropertyGrpNode(modelmgr, fileNode, agentClass.properties, null);
	}
	
	private static void processMMAgentClass(ClusterConfigModelMgr modelmgr, Node mmAgentNode) {
    	NamedNodeMap fileNodeAttr = mmAgentNode.getAttributes();
    	Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNodeAttr);
    	String mmAgentId = fileNodeAttrMap.get("id");
    	MMAgent mmAgent = modelmgr.getModel().new MMAgent(mmAgentId); 
		if (mmAgent != null)
			modelmgr.getModel().agentClasses.add(mmAgent);
		
		NodeList mmNodeList = mmAgentNode.getChildNodes();
		for (int n=0; n<mmNodeList.getLength(); n++) {
			Node mmNode = mmNodeList.item(n);
			if (!isValidFileNode(mmNode))
				continue;
			String mmNodeName = mmNode.getLocalName();
			if (mmNodeName.equals(Elements.MM_INFERENCE_AGENT.localName)) {
				mmAgent.infAgentRef = mmNode.getTextContent();
			} else if (mmNodeName.equals(Elements.MM_QUERY_AGENT.localName)) {
				mmAgent.queryAgentRef = mmNode.getTextContent();
			} else if (mmNodeName.equals(Elements.ALERT_CONFIG_SET.localName)) {
	        	NodeList alertConfigNodeList = mmNode.getChildNodes();
	    		for (int c=0; c<alertConfigNodeList.getLength(); c++) {
	    			Node alertConfigNode = alertConfigNodeList.item(c);
	    			if (!isValidFileNode(alertConfigNode))
	    				continue;
	    			LinkedHashMap<String, String> alertConfigNodeAttrMap = processNodeAttributes(alertConfigNode.getAttributes());
	    			String id = alertConfigNodeAttrMap.get("alert-name");
	    			AlertConfig alertConfig = mmAgent.alertConfigList.new AlertConfig(mmAgent.alertConfigList, id);
	    			NodeList alertConfigChdNodeList = alertConfigNode.getChildNodes();
	    			for (int a=0; a<alertConfigChdNodeList.getLength(); a++) {
	    				Node alertConfigChdNode = alertConfigChdNodeList.item(a);
	    				if (!isValidFileNode(alertConfigChdNode))
	    					continue;
	    				String alertConfigChdNodeName = alertConfigChdNode.getLocalName();
	    				if (alertConfigChdNodeName.equals(Elements.CONDITION.localName)) {
	    	    			NodeList propertyNodeList = alertConfigChdNode.getChildNodes();
	    	    			for (int p=0; p<propertyNodeList.getLength(); p++) {
	    	    				Node propertyNode = propertyNodeList.item(p);
	    	    				if (!isValidFileNode(propertyNode))
	    	    					continue;
		    			    	NamedNodeMap propertyNodeAttr = propertyNode.getAttributes();
		    			    	LinkedHashMap<String, String> propertyNodeAttrMap = processNodeAttributes(propertyNodeAttr);
		    			    	copyMap(propertyNodeAttrMap, alertConfig.condition.values, new String[]{"path", "name", "threshold", "reference"});
	    	    			}
	    				} else if (alertConfigChdNodeName.equals(Elements.PROJECTION.localName)) {
	    	    			NodeList propertyNodeList = alertConfigChdNode.getChildNodes();
	    	    			for (int p=0; p<propertyNodeList.getLength(); p++) {
	    	    				Node propertyNode = propertyNodeList.item(p);
	    	    				if (!isValidFileNode(propertyNode))
	    	    					continue;
		    			    	NamedNodeMap propertyNodeAttr = propertyNode.getAttributes();
		    			    	LinkedHashMap<String, String> propertyNodeAttrMap = processNodeAttributes(propertyNodeAttr);
	    			    		alertConfig.projection.properties.add(propertyNodeAttrMap);
	    	    			}
	    				}
	    			}
	    			mmAgent.alertConfigList.alertConfigs.add(alertConfig);
	    		}
			} else if (mmNodeName.equals(Elements.RULE_CONFIG.localName)) {
	        	NodeList clusterMemberNodeList = mmNode.getChildNodes();
	    		for (int c=0; c<clusterMemberNodeList.getLength(); c++) {
	    			Node clusterMemberNode = clusterMemberNodeList.item(c);
	    			if (!isValidFileNode(clusterMemberNode))
	    				continue;
	    			NamedNodeMap clusterMemberNodeAttr = clusterMemberNode.getAttributes();
			    	Map<String, String> clusterMemberNodeAttrMap = processNodeAttributes(clusterMemberNodeAttr);
			    	String id = clusterMemberNodeAttrMap.get("member-name");
			    	ClusterMember clusterMember = mmAgent.ruleConfigList.new ClusterMember(mmAgent.ruleConfigList, id);
			    	clusterMember.path = clusterMemberNodeAttrMap.get("path");
		        	NodeList setPropertyNodeList = clusterMemberNode.getChildNodes();
		    		for (int p=0; p<setPropertyNodeList.getLength(); p++) {
		    			Node setPropertyNode = setPropertyNodeList.item(p);
		    			if (!isValidFileNode(setPropertyNode))
		    				continue;
		    			NamedNodeMap setPropertyNodeAttr = setPropertyNode.getAttributes();
				    	Map<String, String> setPropertyNodeAttrMap = processNodeAttributes(setPropertyNodeAttr);
		    			String propId = setPropertyNodeAttrMap.get("set-property-name");
		    			SetProperty setProperty = clusterMember.new SetProperty(clusterMember, propId);
				    	setProperty.name = setPropertyNodeAttrMap.get(Elements.NAME.localName);
				    	setProperty.value = setPropertyNodeAttrMap.get("value");
			        	NodeList setPropertyChdNodeList = setPropertyNode.getChildNodes();
			    		for (int s=0; s<setPropertyChdNodeList.getLength(); s++) {
			    			Node setPropertyChdNode = setPropertyChdNodeList.item(s);
			    			if (!isValidFileNode(setPropertyChdNode))
			    				continue;
			    			String propertyChdNodeName = setPropertyChdNode.getLocalName();
			    			if (propertyChdNodeName.equals("child-cluster-member")) {
			    				NamedNodeMap childNodeAttr = setPropertyChdNode.getAttributes();
						    	Map<String, String> childNodeAttrMap = processNodeAttributes(childNodeAttr);
						    	if (childNodeAttrMap.get("path") != null)
						    		setProperty.childClusterMember.path = childNodeAttrMap.get("path");
						    	if (childNodeAttrMap.get("tolerance") != null)
						    		setProperty.childClusterMember.tolerance = childNodeAttrMap.get("tolerance");
						    	processSetPropertyChildProperties(setProperty.childClusterMember, setPropertyChdNode);
			    				setProperty.setPropertyChild = setProperty.childClusterMember;
			    			} else if (propertyChdNodeName.equals("notification")) {
			    				NamedNodeMap childNodeAttr = setPropertyChdNode.getAttributes();
						    	Map<String, String> childNodeAttrMap = processNodeAttributes(childNodeAttr);
						    	if (childNodeAttrMap.get("range") != null)
						    		setProperty.notification.range = childNodeAttrMap.get("range");
						    	if (childNodeAttrMap.get("tolerance") != null)
						    		setProperty.notification.tolerance = childNodeAttrMap.get("tolerance");
			    				processSetPropertyChildProperties(setProperty.notification, setPropertyChdNode);
			    				setProperty.setPropertyChild = setProperty.notification;
			    			}
			    		}
			    		clusterMember.setProperties.add(setProperty);
		    		}
		    		mmAgent.ruleConfigList.clusterMembers.add(clusterMember);
	    		}
			} else if (mmNodeName.equals(Elements.MM_ACTION_CONFIG_SET.localName)) {
				NodeList actionConfigNodeList = mmNode.getChildNodes();
	    		for (int c=0; c<actionConfigNodeList.getLength(); c++) {
	    			Node actionConfigNode = actionConfigNodeList.item(c);
	    			if (!isValidFileNode(actionConfigNode))
	    				continue;
			    	Map<String, String> actionConfigNodeAttrMap = processNodeAttributes(actionConfigNode.getAttributes());
	    			String id = actionConfigNodeAttrMap.get("action-name");
	    			ActionConfig actionConfig = mmAgent.actionConfigList.new ActionConfig(mmAgent.actionConfigList, id);
	    			NodeList actionConfigChdNodeList = actionConfigNode.getChildNodes();
	    			for (int a=0; a<actionConfigChdNodeList.getLength(); a++) {
	    				Node actionConfigChdNode = actionConfigChdNodeList.item(a);
	    				if (!isValidFileNode(actionConfigChdNode))
	    					continue;
	    				String actionConfigChdNodeName = actionConfigChdNode.getLocalName();
	    				if (actionConfigChdNodeName.equals(Elements.MM_TRIGGER_CONDITION.localName)) {
	    	    			NodeList propertyNodeList = actionConfigChdNode.getChildNodes();
	    	    			for (int p=0; p<propertyNodeList.getLength(); p++) {
	    	    				Node propertyNode = propertyNodeList.item(p);
	    	    				if (!isValidFileNode(propertyNode))
	    	    					continue;
	    	    				String propertyNodeName = propertyNode.getLocalName();
		    			    	NamedNodeMap propertyNodeAttr = propertyNode.getAttributes();
		    			    	Map<String, String> propertyNodeAttrMap = processNodeAttributes(propertyNodeAttr);
	    	    				if (propertyNodeName.equals(Elements.MM_ALERT.localName)) {
	    	    					actionConfig.triggerCondition.alert.path = propertyNodeAttrMap.get("path");
	    	    					actionConfig.triggerCondition.alert.severity = propertyNodeAttrMap.get("severity");
	    	    					actionConfig.triggerCondition.trigger = actionConfig.triggerCondition.alert;
	    	    				} else if (propertyNodeName.equals(Elements.MM_HEALTH_LEVEL.localName)) {
	    	    					actionConfig.triggerCondition.healthLevel.path = propertyNodeAttrMap.get("path");
	    	    					actionConfig.triggerCondition.healthLevel.value = propertyNodeAttrMap.get("value");
	    	    					actionConfig.triggerCondition.trigger = actionConfig.triggerCondition.healthLevel;
	    	    				}
	    	    			}
	    				} else if (actionConfigChdNodeName.equals(Elements.MM_ACTION.localName)) {
	    	    			NodeList propertyNodeList = actionConfigChdNode.getChildNodes();
	    	    			for (int p=0; p<propertyNodeList.getLength(); p++) {
	    	    				Node propertyNode = propertyNodeList.item(p);
	    	    				if (!isValidFileNode(propertyNode))
	    	    					continue;
	    	    				String propertyNodeName = propertyNode.getLocalName();
		    			    	NamedNodeMap propertyNodeAttr = propertyNode.getAttributes();
		    			    	LinkedHashMap<String, String> propertyNodeAttrMap = processNodeAttributes(propertyNodeAttr);
	    	    				if (propertyNodeName.equals(Elements.MM_EXECUTE_COMMAND.localName)) {
	    	    					actionConfig.action.execCommand.command = propertyNodeAttrMap.get("command");
	    	    					actionConfig.action.actionElement = actionConfig.action.execCommand;
	    	    				} else if (propertyNodeName.equals(Elements.MM_SEND_EMAIL.localName)) {
	    	    					copyMap(propertyNodeAttrMap, actionConfig.action.sendEmail.values, new String[]{"to", "cc", "subject", "msg"});
	    	    					actionConfig.action.actionElement = actionConfig.action.sendEmail;
	    	    				}
	    	    			}
	    				}
	    			}
	    			mmAgent.actionConfigList.actionConfigs.add(actionConfig);
	    		}
			} else if (mmNodeName.equals(Elements.PROPERTY_GROUP.localName)) {
				processPropertyGrpNode(modelmgr, mmNode, mmAgent.properties, null);
			}
		}
	}
	
	private static void processProcessAgentClass(ClusterConfigModelMgr modelmgr, Node mmAgentNode) {
    	NamedNodeMap fileNodeAttr = mmAgentNode.getAttributes();
    	Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNodeAttr);
    	String processAgentId = fileNodeAttrMap.get("id");
    	ProcessAgent processAgent = modelmgr.getModel().new ProcessAgent(processAgentId); 
		if (processAgent != null)
			modelmgr.getModel().agentClasses.add(processAgent);
		
		NodeList procNodeList = mmAgentNode.getChildNodes();
		for (int n=0; n<procNodeList.getLength(); n++) {
			Node procNode = procNodeList.item(n);
			if (!isValidFileNode(procNode))
				continue;
			String procNodeName = procNode.getLocalName();
			if (procNodeName.equals(Elements.LOAD.localName)) {
				
			}
		}
	}
	
	private static void processSetPropertyChildProperties(SetPropertyChildElement childElement, Node setPropertyChdNode) {
    	NodeList propertyNodeList = setPropertyChdNode.getChildNodes();
		for (int m=0; m<propertyNodeList.getLength(); m++) {
			Node propertyNode = propertyNodeList.item(m);
			if (!isValidFileNode(propertyNode))
				continue;
			NamedNodeMap propertyNodeAttr = propertyNode.getAttributes();
	    	LinkedHashMap<String, String> propertyNodeAttrMap = processNodeAttributes(propertyNodeAttr);
	    	childElement.properties.add(propertyNodeAttrMap);
		}
	}
	
	private static void processProcessingUnits(ClusterConfigModelMgr modelmgr, Node procUnitsNode) {
		NodeList procUnitNodeList = procUnitsNode.getChildNodes();
		for (int n=0; n<procUnitNodeList.getLength(); n++) {
			Node procUnitNode = procUnitNodeList.item(n);
			if (!isValidFileNode(procUnitNode))
				continue;
        	NamedNodeMap fileNodeAttr = procUnitNode.getAttributes();
        	Map<String, String> fileNodeAttrMap = processNodeAttributes(fileNodeAttr);
        	String procUnitId = fileNodeAttrMap.get("id");
        	ProcessingUnit procUnit = modelmgr.initProcessingUnit(procUnitId);
        	NodeList procUnitChdList = procUnitNode.getChildNodes();
    		for (int c=0; c<procUnitChdList.getLength(); c++) {
    			Node procUnitChdNode = procUnitChdList.item(c);
    			if (!isValidFileNode(procUnitChdNode))
    				continue;
    			String procUnitChdNodeName = procUnitChdNode.getLocalName();
    			if (procUnitChdNodeName.equals(Elements.AGENTS.localName)) {
    				processProcUnitAgents(procUnit, procUnitChdNode);	
    			} else if (procUnitChdNodeName.equals(Elements.PROPERTY_GROUP.localName)) {
    				processProcUnitPropertyNodes(modelmgr, procUnit, procUnitChdNode);
    			} else if (procUnitChdNodeName.equals(Elements.LOGS.localName)) {
    				procUnit.logConfig = modelmgr.getLogConfig(procUnitChdNode.getTextContent());
    			} else if (procUnitChdNodeName.equals(Elements.HOT_DEPLOY.localName)) {
    				procUnit.hotDeploy = modelmgr.getBooleanValue(procUnitChdNode.getTextContent());
    			} else if (procUnitChdNodeName.equals(Elements.CACHE_STORAGE_ENABLED.localName)) {
    				procUnit.enableCacheStorage = modelmgr.getBooleanValue(procUnitChdNode.getTextContent());
    			} else if (procUnitChdNodeName.equals(Elements.DB_CONCEPTS.localName)) {
    				procUnit.enableDbConcepts = modelmgr.getBooleanValue(procUnitChdNode.getTextContent());
    			} else if (procUnitChdNodeName.equals(Elements.HTTP.localName)) {
    				processProcUnitHttpProperties(modelmgr, procUnit, procUnitChdNode);
    			} else if (procUnitChdNodeName.equals(Elements.SECURITY_CONFIG.localName)) {
    				processSecurityConfig(procUnitChdNode, procUnit.securityConfig, modelmgr);
    			}
    		}
 		}
	}
	
	private static void processProcUnitAgents(ProcessingUnit procUnit, Node agentsNode) {
		NodeList agentNodeList = agentsNode.getChildNodes();
		for (int n=0; n<agentNodeList.getLength(); n++) {
			Node agentNode = agentNodeList.item(n);
			if (!isValidFileNode(agentNode))
				continue;
			NodeList valNodeList = agentNode.getChildNodes();
			LinkedHashMap<String, String> agentVal = new LinkedHashMap<String, String>();
			for (int c=0; c<valNodeList.getLength(); c++) {
				Node valNode = valNodeList.item(c);
				if (!isValidFileNode(valNode))
					continue;
				String key = valNode.getLocalName();
				String value = valNode.getTextContent();
				agentVal.put(key, value);
			}
			procUnit.agentClasses.add(agentVal);
		}
	}
	
	private static void processProcUnitPropertyNodes(ClusterConfigModelMgr modelmgr, ProcessingUnit procUnit, Node fileNode) {
		processPropertyGrpNode(modelmgr, fileNode, procUnit.properties, null);
	}
	
	private static void processProcUnitHttpProperties(ClusterConfigModelMgr modelmgr, ProcessingUnit procUnit, Node httpNode) {
		NodeList httpPropNodeList = httpNode.getChildNodes();
		for (int n=0; n<httpPropNodeList.getLength(); n++) {
			Node httpPropNode = httpPropNodeList.item(n);
			if (!isValidFileNode(httpPropNode))
				continue;
			String key = httpPropNode.getLocalName();
			String value = httpPropNode.getTextContent();
			if (!key.equals(Elements.SSL.localName)) {
				procUnit.httpProperties.properties.put(key, value);
			} else {
				NodeList sslValNodeList = httpPropNode.getChildNodes();
				for (int c=0; c<sslValNodeList.getLength(); c++) {
					Node sslValNode = sslValNodeList.item(c);
					if (!isValidFileNode(sslValNode))
						continue;
					if (sslValNode.getLocalName().equals(Elements.PROTOCOLS.localName)) {
						processHttpPropertiesSslChildNode(modelmgr, procUnit.httpProperties.ssl.protocols, sslValNode);
					} else if (sslValNode.getLocalName().equals(Elements.CIPHERS.localName)) {
						processHttpPropertiesSslChildNode(modelmgr, procUnit.httpProperties.ssl.ciphers, sslValNode);
					} else {
						procUnit.httpProperties.ssl.properties.put(sslValNode.getLocalName(), sslValNode.getTextContent());
					}
				}
			}
		}
	}
	
	private static void processHttpPropertiesSslChildNode(ClusterConfigModelMgr modelmgr, ArrayList<String> sslChildList, Node httpSslChdNode) {
		NodeList httpSslChdPropertyNodeList = httpSslChdNode.getChildNodes();
		for (int n=0; n<httpSslChdPropertyNodeList.getLength(); n++) {
			Node httpSslChdPropertyNode = httpSslChdPropertyNodeList.item(n);
			if (!isValidFileNode(httpSslChdPropertyNode))
				continue;
			sslChildList.add(httpSslChdPropertyNode.getTextContent());
		}
	}

	private static void processCacheOm(ClusterConfigModelMgr modelmgr, Node cacheNode) {
		modelmgr.getModel().om.cacheOm = modelmgr.getModel().new CacheOm();
		modelmgr.getModel().om.activeOm = ObjectManagement.CACHE_MGR;
		CacheOm cacheOm = modelmgr.getModel().om.cacheOm;
		NodeList cacheNodeChdList = cacheNode.getChildNodes();
		for (int n=0; n<cacheNodeChdList.getLength(); n++) {
			Node cacheNodeChd = cacheNodeChdList.item(n);
			if (!isValidFileNode(cacheNodeChd))
				continue;
			String cacheNodeChdName = cacheNodeChd.getLocalName();
			if (cacheNodeChdName.equals(Elements.PROVIDER.localName)) {
				NodeList providerNodeChdList = cacheNodeChd.getChildNodes();
				for (int p=0; p<providerNodeChdList.getLength(); p++) {
					Node providerNodeChd = providerNodeChdList.item(p);
					if (!isValidFileNode(providerNodeChd, Elements.NAME.localName))
						continue;
					cacheOm.provider = providerNodeChd.getTextContent();
				}
			} else if (cacheNodeChdName.equals(Elements.OBJECT_TABLE.localName)) {
				NodeList objTableNodeChdList = cacheNodeChd.getChildNodes();
				for (int p=0; p<objTableNodeChdList.getLength(); p++) {
					Node objTableNodeChd = objTableNodeChdList.item(p);
					if (!isValidFileNode(objTableNodeChd, Elements.MAX_SIZE.localName))
						continue;
					cacheOm.objectTableSize = objTableNodeChd.getTextContent();
				}
			} else if (cacheNodeChdName.equals(Elements.BACKING_STORE.localName)) {
				NodeList bsNodeChdList = cacheNodeChd.getChildNodes();
				for (int b=0; b<bsNodeChdList.getLength(); b++) {
					Node bsNodeChd = bsNodeChdList.item(b);
					if (!isValidFileNode(bsNodeChd))
						continue;
					String bsNodeChdName = bsNodeChd.getLocalName();
					/*
					if (bsNodeChdName.equals(Elements.CACHE_ASIDE.localName) 
								|| bsNodeChdName.equals(Elements.ENFORCE_POOLS.localName)
								|| bsNodeChdName.equals(Elements.ENFORCE_POOLS.localName)) {
						cacheOm.bs.values.put(bsNodeChdName, new Boolean(bsNodeChd.getTextContent()).toString());
					} else if (bsNodeChdName.equals(Elements.CACHE_LOADER_CLASS.localName)
							|| bsNodeChdName.equals(Elements.STRATEGY.localName)
							|| bsNodeChdName.equals(Elements.TYPE.localName)
							|| bsNodeChdName.equals("data-store-path")
							|| bsNodeChdName.equals("persistence-policy")
							|| bsNodeChdName.equals(BackingStore.PERSISTENCE_OPTION)) {
						cacheOm.bs.values.put(bsNodeChdName, bsNodeChd.getTextContent());
					} else
						*/
					if (bsNodeChdName.equals(Elements.PRIMARY_CONNECTION.localName)) {
						cacheOm.bs.primaryConnection = cacheOm.new Connection(true);
						processCacheOmBsConnectionNode(bsNodeChd, cacheOm.bs.primaryConnection);
					} else {
						cacheOm.bs.values.put(bsNodeChdName, bsNodeChd.getTextContent());
					}
				}
			} else if (cacheNodeChdName.equals(Elements.DOMAIN_OBJECTS.localName)) {
				processCacheOmDomainObject(cacheNodeChd, cacheOm, modelmgr);
			} else if (cacheNodeChdName.equals(Elements.PROPERTY_GROUP.localName)) {
				processCachePropertyNodes(modelmgr, cacheNodeChd);
			} else if (cacheNodeChdName.equals(Elements.SECURITY_CONFIG.localName)) {
				processSecurityConfig(cacheNodeChd, cacheOm.securityConfig, modelmgr);
			} 
			else {
				cacheOm.values.put(cacheNodeChdName, cacheNodeChd.getTextContent());
			}
		}
	}
	
	private static void processCacheOmBsConnectionNode(Node bsNodeChd, Connection connection) {
		NodeList bsConNodeChdList = bsNodeChd.getChildNodes();
		for (int d=0; d<bsConNodeChdList.getLength(); d++) {
			Node bsConNodeChd = bsConNodeChdList.item(d);
			if (!isValidFileNode(bsConNodeChd))
				continue;
			String bsConNodeChdName = bsConNodeChd.getLocalName();
			connection.values.put(bsConNodeChdName, bsConNodeChd.getTextContent());
		}
	}
	
	private static void processCacheOmDomainObject(Node cacheNodeChd, CacheOm cacheOm, ClusterConfigModelMgr modelmgr) {
		NodeList doNodeChdList = cacheNodeChd.getChildNodes();
		cacheOm.domainObjects.domainObjOverrides.overrides.clear();
		for (int b=0; b<doNodeChdList.getLength(); b++) {
			Node doNodeChd = doNodeChdList.item(b);
			if (!isValidFileNode(doNodeChd))
				continue;
			String doNodeChdName = doNodeChd.getLocalName();
			if (doNodeChdName.equals(Elements.DOMAIN_OBJECT.localName)) {
				DomainObject domainObj = cacheOm.new DomainObject(); 
				NodeList doOverrideNodeChdList = doNodeChd.getChildNodes();
				for (int d=0; d<doOverrideNodeChdList.getLength(); d++) {
					Node doOverrideNodeChd = doOverrideNodeChdList.item(d);
					if (!isValidFileNode(doOverrideNodeChd))
						continue;
					String doOverrideNodeChdName = doOverrideNodeChd.getLocalName();
					if (doOverrideNodeChdName.equalsIgnoreCase(Elements.BACKING_STORE.localName)) {
						NodeList bsList = doOverrideNodeChd.getChildNodes();
						for (int v=0; v<bsList.getLength(); v++) {
							Node bsNodeChd = bsList.item(v);
							if (!isValidFileNode(bsNodeChd))
								continue;
							String bsNodeChdName = bsNodeChd.getLocalName();
							if (bsNodeChdName.equalsIgnoreCase(Elements.PROPERTIES.localName)){
								NodeList propList = bsNodeChd.getChildNodes();
								for (int p=0; p<propList.getLength(); p++) {
									Node propNodeChd = propList.item(p);
									if (!isValidFileNode(propNodeChd))
										continue;
									String propNodeChdName = propNodeChd.getLocalName();
									if (propNodeChdName.equalsIgnoreCase(Elements.PROPERTY.localName)) {
										DomainObjectProperty property = domainObj.new DomainObjectProperty();
										String propName = null;
										NodeList propValList = propNodeChd.getChildNodes();
										for (int pv=0; pv<propValList.getLength(); pv++) {
											Node propValNodeChd = propValList.item(pv);
											if (!isValidFileNode(propValNodeChd))
												continue;
											if (propValNodeChd.getLocalName().equalsIgnoreCase(Elements.NAME.localName))
												propName = propValNodeChd.getTextContent();
											else
												property.values.put(propValNodeChd.getLocalName(), propValNodeChd.getTextContent());
										}
										if (propName != null) {
											DomainObjectProperty storedProperty = domainObj.props.get(propName);
											if (storedProperty != null) {
												property.values.put(Elements.INDEX.localName, storedProperty.values.get(Elements.INDEX.localName));
											}
											domainObj.props.put(propName, property);
										}
									}
								}
							} else {
								domainObj.bs.values.put(bsNodeChdName, bsNodeChd.getTextContent());
							}
						}
					} else if (doOverrideNodeChdName.equalsIgnoreCase(Elements.INDEXES.localName)) {
						NodeList indexesList = doOverrideNodeChd.getChildNodes();
						for (int v=0; v<indexesList.getLength(); v++) {
							Node indexesNodeChd = indexesList.item(v);
							if (!isValidFileNode(indexesNodeChd))
								continue;
							String indexesNodeChdName = indexesNodeChd.getLocalName();
							if (indexesNodeChdName.equalsIgnoreCase(Elements.INDEX.localName)){
								NodeList indexList = indexesNodeChd.getChildNodes();
								for (int p=0; p<indexList.getLength(); p++) {
									Node propNodeChd = indexList.item(p);
									if (!isValidFileNode(propNodeChd))
										continue;
									String propNodeChdName = propNodeChd.getLocalName();
									if (propNodeChdName.equalsIgnoreCase(Elements.PROPERTY.localName)) {
										String propName = propNodeChd.getTextContent();
										DomainObjectProperty property = domainObj.props.get(propName);
										if (property == null)
											property = domainObj.new DomainObjectProperty();
										property.values.put(Elements.INDEX.localName, "true");
										domainObj.props.put(propName, property);
										//index.props.put(propNodeChd.getTextContent(), new Boolean(true));
									}
								}
							}
						}
					}else if (doOverrideNodeChdName.equalsIgnoreCase("encryption")) {
						NodeList encyptionList = doOverrideNodeChd.getChildNodes();
						for (int p=0; p<encyptionList.getLength(); p++) {
							Node propNodeChd = encyptionList.item(p);
							if (!isValidFileNode(propNodeChd))
								continue;
							String propNodeChdName = propNodeChd.getLocalName();
							if (propNodeChdName.equalsIgnoreCase(Elements.PROPERTY.localName)) {
								String propName = propNodeChd.getTextContent();
								DomainObjectProperty property = domainObj.props.get(propName);
								if (property == null)
									property = domainObj.new DomainObjectProperty();
								property.values.put("encryption", "true");
								domainObj.props.put(propName, property);
								//index.props.put(propNodeChd.getTextContent(), new Boolean(true));
							}
						}
                    } else if (doOverrideNodeChdName.equalsIgnoreCase(Elements.COMPOSITEINDEXES.localName)) {
                        NodeList compIndexNodeList = doOverrideNodeChd.getChildNodes();
                        for (int v = 0; v < compIndexNodeList.getLength(); v++) {
                            Node compIdxNodeChd = compIndexNodeList.item(v);
                            if (!isValidFileNode(compIdxNodeChd))
                                continue;
                            String compIdxNodeName = compIdxNodeChd.getLocalName();
                            if (compIdxNodeName.equalsIgnoreCase(Elements.COMPOSITEINDEX.localName)) {
                                DomainObjectCompositeIndex cmpIdx = domainObj.new DomainObjectCompositeIndex();
                                NodeList elementsList = compIdxNodeChd.getChildNodes();
                                for (int p = 0; p < elementsList.getLength(); p++) {
                                    Node elementNodeChd = elementsList.item(p);
                                    if (!isValidFileNode(elementNodeChd))
                                        continue;
                                    String elementNodeChdName = elementNodeChd.getLocalName();
                                    if (elementNodeChdName.equalsIgnoreCase(Elements.PROPERTIES.localName)) {
                                        String propName = null;
                                        NodeList propValList = elementNodeChd.getChildNodes();
                                        for (int pv = 0; pv < propValList.getLength(); pv++) {
                                            Node propValNodeChd = propValList.item(pv);
                                            if (!isValidFileNode(propValNodeChd))
                                                continue;
                                            propName = propValNodeChd.getTextContent();
                                            cmpIdx.values.add(propName);
                                        }
                                    } else if (elementNodeChdName.equalsIgnoreCase(Elements.NAME.localName)) {
                                        cmpIdx.name = elementNodeChd.getTextContent();
                                    } else if (elementNodeChdName.equalsIgnoreCase(Elements.TYPE.localName)) {
                                        cmpIdx.type = elementNodeChd.getTextContent();
                                    }
                                }
                                domainObj.compIdxList.add(cmpIdx);
                            }
                        }
                    }
					else {
						domainObj.values.put(doOverrideNodeChdName, doOverrideNodeChd.getTextContent());
						if (doOverrideNodeChdName.equals(Elements.URI.localName)) {		// As soon as we see URI, initialize the properties in Domain object
							//modelmgr.initializeDomainObject(domainObj);
						}
					}
				}
				modelmgr.initializeDomainObject(domainObj);
				cacheOm.domainObjects.domainObjOverrides.overrides.add(domainObj);
			} else {
				if (!doNodeChdName.equalsIgnoreCase("pre-load-caches")) {  // CR BE-9649
					cacheOm.domainObjects.domainObjDefault.values.put(doNodeChdName, doNodeChd.getTextContent());
				}
			}
		}
	}
	
	private static void processCachePropertyNodes(ClusterConfigModelMgr modelmgr, Node fileNode) {
    	ClusterConfigModel model = modelmgr.getModel();
		if (model.om != null) {
			model.om.cacheOm.cacheProps = model.new PropertyElementList();
			processPropertyGrpNode(modelmgr, fileNode, model.om.cacheOm.cacheProps, null);
		}
	}
	
	private static void processSecurityConfig(Node nodeChd, SecurityConfig securityConfig, ClusterConfigModelMgr modelmgr) {
		NodeList securityNodeChdList = nodeChd.getChildNodes();
		for (int b = 0; b < securityNodeChdList.getLength(); b++) {
			Node securityNodeChd = securityNodeChdList.item(b);
			if (!isValidFileNode(securityNodeChd))
				continue;
			String securityNodeChdName = securityNodeChd.getLocalName();
			if (securityNodeChdName.equals(Elements.ENABLED.localName) && securityConfig instanceof CacheOmSecurityConfig) {
				((CacheOmSecurityConfig)securityConfig).securityEnabled = Boolean.parseBoolean(securityNodeChd.getTextContent());
			} else if (securityNodeChdName.equals(Elements.SECURITY_ROLE.localName) 
														&& securityConfig instanceof ProcessingUnitSecurityConfig) {
				((ProcessingUnitSecurityConfig)securityConfig).securityRole = securityNodeChd.getTextContent();
			} else if (securityNodeChdName.equals(Elements.SECURITY_CONTROLLER.localName)) {
				NodeList securityControllerNodeChdList = securityNodeChd.getChildNodes();
				for (int d = 0; d < securityControllerNodeChdList.getLength(); d++) {
					Node securityControllerNodeChd = securityControllerNodeChdList.item(d);
					if (!isValidFileNode(securityControllerNodeChd))
						continue;
					securityConfig.securityControllerValues.put(securityControllerNodeChd.getLocalName(), securityControllerNodeChd.getTextContent());
					if (securityConfig instanceof ProcessingUnitSecurityConfig) {
						NamedNodeMap securityControllerNodeAttr = securityControllerNodeChd.getAttributes();
						Node overrideAttr = securityControllerNodeAttr.getNamedItem(Attributes.OVERRIDE.localName);
						if (overrideAttr != null)
							((ProcessingUnitSecurityConfig)securityConfig).securityControllerOverrides.put(securityControllerNodeChd.getLocalName(), new Boolean(overrideAttr.getNodeValue()));
					}
				}				
			} else if (securityNodeChdName.equals(Elements.SECURITY_REQUESTER.localName)) {
				NodeList securityRequestorNodeChdList = securityNodeChd.getChildNodes();
				for (int d = 0; d < securityRequestorNodeChdList.getLength(); d++) {
					Node securityRequestorNodeChd = securityRequestorNodeChdList.item(d);
					if (!isValidFileNode(securityRequestorNodeChd))
						continue;
					securityConfig.securityRequesterValues.put(securityRequestorNodeChd.getLocalName(), securityRequestorNodeChd.getTextContent());
					if (securityConfig instanceof ProcessingUnitSecurityConfig) {
						NamedNodeMap securityRequestorNodeAttr = securityRequestorNodeChd.getAttributes();
						Node overrideAttr = securityRequestorNodeAttr.getNamedItem(Attributes.OVERRIDE.localName);
						if (overrideAttr != null)
							((ProcessingUnitSecurityConfig)securityConfig).securityRequesterOverrides.put(securityRequestorNodeChd.getLocalName(), new Boolean(overrideAttr.getNodeValue()));
					}
				}				
			}
		}	
	}
	
	private static void processDbConcepts(ClusterConfigModelMgr modelmgr, Node fileNode) {
		DbConcepts dbConcepts = modelmgr.getModel().om.dbConcepts;
		NodeList dbNodeChdList = fileNode.getChildNodes();
		for (int b=0; b<dbNodeChdList.getLength(); b++) {
			Node dbNodeChd = dbNodeChdList.item(b);
			if (!isValidFileNode(dbNodeChd))
				continue;
			String dbNodeChdName = dbNodeChd.getLocalName();
			if (dbNodeChdName.equals(Elements.DB_URIS.localName)) {
				dbConcepts.dburis.clear();
				NodeList dbUriNodeChdList = dbNodeChd.getChildNodes();
				for (int d=0; d<dbUriNodeChdList.getLength(); d++) {
					Node dbUriNodeChd = dbUriNodeChdList.item(d);
					if (!isValidFileNode(dbUriNodeChd))
						continue;
					dbConcepts.dburis.add(dbUriNodeChd.getTextContent());
				}
			} else {
				dbConcepts.values.put(dbNodeChdName, dbNodeChd.getTextContent());
			}
		}
	}
	
	private static void processPropertyNodes(ClusterConfigModelMgr modelmgr, Node fileNode) {
    	ClusterConfigModel model = modelmgr.getModel();
		model.properties = model.new PropertyElementList();
		processPropertyGrpNode(modelmgr, fileNode, model.properties, null);
	}
	
	private static void processPropertyGrpNode(ClusterConfigModelMgr modelmgr, Node fileNode, PropertyElementList propList, PropertyGroup propGrp) {
		NamedNodeMap nodeAttr = fileNode.getAttributes();
    	Map<String, String> nodeAttrMap = processNodeAttributes(nodeAttr);
    	if (propGrp != null) {
	    	propGrp.name = nodeAttrMap.get("name");
    	}
		NodeList childNodesList = fileNode.getChildNodes();
		if (childNodesList != null) {
			for (int n=0; n<childNodesList.getLength(); n++) {
				Node cNode = childNodesList.item(n);
				if (!isValidFileNode(cNode))
					continue;
				NamedNodeMap cNodeAttr = cNode.getAttributes();
		    	Map<String, String> cNodeAttrMap = processNodeAttributes(cNodeAttr);
		    	String cNodeName = cNode.getLocalName();
				if (cNodeName.equals("property")) {
					Property cProp = modelmgr.getModel().new Property();
					for (Map.Entry<String, String> attr: cNodeAttrMap.entrySet()) {
						cProp.fieldMap.put(attr.getKey(), attr.getValue());
					}
					if (propGrp==null)
						propList.propertyList.add(cProp);
					else
						propGrp.propertyList.add(cProp);
				} else if (cNodeName.equals("property-group")) {
					PropertyGroup cPropGrp = modelmgr.getModel().new PropertyGroup();
					processPropertyGrpNode(modelmgr, cNode, propList, cPropGrp);
					if (propGrp==null)
						propList.propertyList.add(cPropGrp);
					else
						propGrp.propertyList.add(cPropGrp);
				}
			}
		}
	}

	private static void postProcessModel(ClusterConfigModelMgr modelmgr) {
		
	}
	
	private static LinkedHashMap<String, String> processNodeAttributes(NamedNodeMap attr) {
		return processNodeAttributes(attr, false);
	}
	
	private static LinkedHashMap<String, String> processNodeAttributes(NamedNodeMap attr, boolean isNodeName) {
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
	
	private static void copyMap(Map<String, String> srcMap, Map<String, String> destMap, String[] keys) {
		for (String key: keys) {
			if (srcMap.get(key) != null) {
				destMap.put(key, srcMap.get(key));
			}
		}
	}

	private static boolean isValidFileNode(Node node) {
		return isValidFileNode(node, null);
	}
	
	private static boolean isValidFileNode(Node node, String expectedName) {
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
	
	public static Document getSaveDocument(NamedNodeMap map) {
		Document doc = PersistenceUtil.getSaveDocument("cluster");
		Element root = doc.getDocumentElement();
		//root.setPrefix(prefix);
		root.setAttribute("xmlns", NAMESPACE);
		return doc;
	}
	
	public static void saveModelParts(Document doc, ClusterConfigModelMgr modelmgr) {
		Element root = doc.getDocumentElement();

		Element revNode = createDocElement(doc, Elements.REVISION.localName);
		createMapElementNode(doc, revNode, modelmgr.getRevisionMap());
		root.appendChild(revNode);
		
		Element nameNode = createDocElement(doc, Elements.NAME.localName);
		nameNode.setTextContent(modelmgr.getClusterName());
		root.appendChild(nameNode);
		
		Element msgEncodeNode = createDocElement(doc, Elements.MESSAGE_ENCODING.localName);
		msgEncodeNode.setTextContent(modelmgr.getMessageEncoding());
		root.appendChild(msgEncodeNode);
		
		Element objNode = createDocElement(doc, Elements.OBJECT_MANAGEMENT.localName);
		String omType = modelmgr.getOmMgr();
		if (omType.equals(ClusterConfigModel.ObjectManagement.MEMORY_MGR)) {
			Element omMgrNode = createDocElement(doc, Elements.MEMORY_MANAGER.localName);
			objNode.appendChild(omMgrNode);
		} else if (omType.equals(ClusterConfigModel.ObjectManagement.CACHE_MGR)) {
			Element cacheOmNode = createDocElement(doc, Elements.CACHE_MANAGER.localName);
			saveCacheOm(doc, cacheOmNode, modelmgr);
			objNode.appendChild(cacheOmNode);
		} 
//		else if (omType.equals(ClusterConfigModel.ObjectManagement.BDB_MGR)) { 
//			Element bdbOmNode = createDocElement(doc, Elements.BDB_MANAGER.localName);
//			createMapElementNode(doc, bdbOmNode, modelmgr.getBdbManagerProperties());
//			objNode.appendChild(bdbOmNode);
//		}
		Element dbNode = createDocElement(doc, Elements.DB_CONCEPTS.localName);
		Element dbUrisNode = createDocElement(doc, Elements.DB_URIS.localName);
		createListElementNode(doc, dbUrisNode, modelmgr.getModel().om.dbConcepts.dburis, Elements.URI.localName);
		dbNode.appendChild(dbUrisNode);
		createMapElementNode(doc, dbNode, modelmgr.getModel().om.dbConcepts.values);
		objNode.appendChild(dbNode);
		root.appendChild(objNode);
			
		Element rulesGrpNode = createDocElement(doc, Elements.RULESETS.localName);
		for (RuleElement ruleElement: modelmgr.getRulesGroup()) {
			if (ruleElement instanceof RulesGrp) {
				RulesGrp rulesGrp = (RulesGrp) ruleElement;
				Element rulesNode = createDocElement(doc, Elements.RULES.localName);
				rulesNode.setAttribute("id", rulesGrp.id);
				saveRules(doc, rulesNode, rulesGrp);
				rulesGrpNode.appendChild(rulesNode);
			} else if (ruleElement instanceof Rule) {
				Rule rule = (Rule) ruleElement;
				if (!rule.isRef)
					createTextElementNode(doc, rulesGrpNode, "uri", rule.uri);
				else
					createTextElementNode(doc, rulesGrpNode, "ref", rule.uri);
			}
		}
		root.appendChild(rulesGrpNode);

		Element functionsGrpNode = createDocElement(doc, Elements.FUNCTION_GROUPS.localName);
		for (FunctionElement functionElement: modelmgr.getFunctionsGroup(false)) {
			if (functionElement instanceof FunctionsGrp) {
				FunctionsGrp functionsGrp = (FunctionsGrp) functionElement;
				Element functionsNode = createDocElement(doc, Elements.FUNCTIONS.localName);
				functionsNode.setAttribute("id", functionsGrp.id);
				saveFunctions(doc, functionsNode, functionsGrp);
				functionsGrpNode.appendChild(functionsNode);
			} else if (functionElement instanceof Function) {
				Function function = (Function) functionElement;
				if (!function.isRef)
					createTextElementNode(doc, functionsGrpNode, "uri", function.uri);
				else
					createTextElementNode(doc, functionsGrpNode, "ref", function.uri);
			}
		}
		root.appendChild(functionsGrpNode);
		
		Element destinationsGrpNode = createDocElement(doc, Elements.DESTINATION_GROUPS.localName);
		for (DestinationElement destinationElement: modelmgr.getDestinationsGroup()) {
			if (destinationElement instanceof DestinationsGrp) {
				DestinationsGrp destinationsGrp = (DestinationsGrp) destinationElement;
				Element destinationsNode = createDocElement(doc, Elements.DESTINATIONS.localName);
				destinationsNode.setAttribute("id", destinationsGrp.id);
				saveDestinations(doc, destinationsNode, destinationsGrp);
				destinationsGrpNode.appendChild(destinationsNode);
			} else if (destinationElement instanceof Destination) {
				Destination destination = (Destination) destinationElement;
				if (!destination.isRef) {
					saveDestination(doc, destinationsGrpNode, destination);
				} else {
					createTextElementNode(doc, destinationsGrpNode, "ref", destination.id);
				}
			}
		}
		root.appendChild(destinationsGrpNode);		
		
		Element processesGrpNode = createDocElement(doc, ProcessesGrp.ELEMENT_PROCESS_GROUPS);
		for (ProcessElement processElement: modelmgr.getProcessesGroupList().processElements) {
			if (processElement instanceof ProcessesGrp) {
				ProcessesGrp processesGrp = (ProcessesGrp) processElement;
				Element processesNode = createDocElement(doc, ProcessesGrp.ELEMENT_PROCESSES);
				processesNode.setAttribute("id", processesGrp.id);
				saveProcesses(doc, processesNode, processesGrp);
				processesGrpNode.appendChild(processesNode);
			} else if (processElement instanceof Process) {
				Process process = (Process) processElement;
				if (!process.isRef)
					createTextElementNode(doc, processesGrpNode, "uri", process.uri);
				else
					createTextElementNode(doc, processesGrpNode, "ref", process.uri);
			}
		}
		root.appendChild(processesGrpNode);
		
		Element logNode = createDocElement(doc, Elements.LOG_CONFIGS.localName);
		for (LogConfig config: modelmgr.getLogConfigs()) {
			Element lcNode = createDocElement(doc, Elements.LOG_CONFIG.localName);
			lcNode.setAttribute("id", config.id);
			createBooleanElementNode(doc, lcNode, Elements.ENABLED.localName, config.enabled);
			Element rolesNode = createDocElement(doc, Elements.ROLES.localName);
			rolesNode.setTextContent(config.roles);
			lcNode.appendChild(rolesNode);
			Element filesNode = createDocElement(doc, Elements.FILES.localName);
			createMapElementNode(doc, filesNode, config.files);
			lcNode.appendChild(filesNode);
			Element termNode = createDocElement(doc, Elements.TERMINAL.localName);
			createMapElementNode(doc, termNode, config.terminal);
			lcNode.appendChild(termNode);
			Element layoutNode = createDocElement(doc, Elements.LINE_LAYOUT.localName);
			createMapElementNode(doc, layoutNode, config.layout);
			lcNode.appendChild(layoutNode);	
			logNode.appendChild(lcNode);
		}
		root.appendChild(logNode);
		
		Element agentsNode = createDocElement(doc, Elements.AGENT_CLASSES.localName);
		for (AgentClass agent: modelmgr.getAgentClasses()) {
			if (agent.type.equals(AgentClass.AGENT_TYPE_INFERENCE)) {
				Element infNode = saveInfAgentNode(doc, agent);
				agentsNode.appendChild(infNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_CACHE)) {
				Element cacheNode = saveCacheAgentNode(doc, agent);
				agentsNode.appendChild(cacheNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_QUERY)) {
				Element queryNode = saveQueryAgentNode(doc, agent);
				agentsNode.appendChild(queryNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_DASHBOARD)) {
				Element dashNode = saveDashAgentNode(doc, agent);
				agentsNode.appendChild(dashNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_MM)) {
				Element mmNode = saveMMAgentNode(doc, agent);
				agentsNode.appendChild(mmNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_PROCESS)) {
				Element processNode = saveProcessAgentNode(doc, agent);
				agentsNode.appendChild(processNode);
			} else if (agent.type.equals(AgentClass.AGENT_TYPE_LIVEVIEW)) {
				Element lvNode = saveLiveViewAgentNode(doc, agent, modelmgr.project, modelmgr.getModel().om.cacheOm);
				agentsNode.appendChild(lvNode);
			}
		}
		root.appendChild(agentsNode);
		
		Element procUnitsNode = createDocElement(doc, Elements.PROCESSING_UNITS.localName);
		for (ProcessingUnit procUnit: modelmgr.getProcessingUnits()) {
			Element procUnitNode = createDocElement(doc, Elements.PROCESSING_UNIT.localName);
			procUnitNode.setAttribute("id", procUnit.name);
			Element puAgentsNode = createDocElement(doc, Elements.AGENTS.localName);
			for (Map<String, String> puAgent: procUnit.agentClasses) {
				Element puAgentNode = createDocElement(doc, Elements.AGENT.localName);
				createTextElementNode(doc, puAgentNode, Elements.REF.localName, puAgent.get(ProcessingUnit.AGENT_REF));
				createTextElementNode(doc, puAgentNode, Elements.KEY.localName, puAgent.get(ProcessingUnit.AGENT_KEY));
				createTextElementNode(doc, puAgentNode, Elements.PRIORITY.localName, puAgent.get(ProcessingUnit.AGENT_PRIORITY));
				puAgentsNode.appendChild(puAgentNode);
			}
			procUnitNode.appendChild(puAgentsNode);
			createTextElementNode(doc, procUnitNode, Elements.LOGS.localName, modelmgr.getProcUnitLogConfig(procUnit));
			createBooleanElementNode(doc, procUnitNode, Elements.HOT_DEPLOY.localName, procUnit.hotDeploy);
			createBooleanElementNode(doc, procUnitNode, Elements.CACHE_STORAGE_ENABLED.localName, procUnit.enableCacheStorage);			
			createBooleanElementNode(doc, procUnitNode, Elements.DB_CONCEPTS.localName, procUnit.enableDbConcepts);
			
			/*
			Element httpNode = createDocElement(doc, Elements.HTTP.localName);
			createMapElementNode(doc, httpNode, procUnit.httpProperties.properties);
			Element sslNode = createDocElement(doc, Elements.SSL.localName);
			createMapElementNode(doc, sslNode, procUnit.httpProperties.ssl.properties);
			Element protocolsNode = createDocElement(doc, Elements.PROTOCOLS.localName);
			createListElementNode(doc, protocolsNode, procUnit.httpProperties.ssl.protocols, "protocol");
			sslNode.appendChild(protocolsNode);
			Element ciphersNode = createDocElement(doc, Elements.CIPHERS.localName);
			createListElementNode(doc, ciphersNode, procUnit.httpProperties.ssl.ciphers, "cipher");
			sslNode.appendChild(ciphersNode);
			httpNode.appendChild(sslNode);
			procUnitNode.appendChild(httpNode);
			*/
			
			PropertyElementList propList = modelmgr.getProcUnitProperties(procUnit);
			Element propsNode = savePropertyElementList(doc, propList);
			procUnitNode.appendChild(propsNode);

			if (ObjectManagement.CACHE_MGR.equals(modelmgr.getModel().om.activeOm)) {
				saveProcUnitSecurityConfig(doc, procUnitNode, procUnit.securityConfig, modelmgr.getModel().om.cacheOm.securityConfig);
			}	
			
			procUnitsNode.appendChild(procUnitNode);
		}
		root.appendChild(procUnitsNode);
		
		Element lbNode = createDocElement(doc, LoadBalancerConfig.ELEMENT_LOAD_BALANCER_CONFIGS);
		saveLoadBalancerConfigs(doc, lbNode, LoadBalancerConfig.ELEMENT_PAIR_CONFIGS, LoadBalancerConfig.ELEMENT_PAIR_CONFIG, modelmgr.getModel().loadBalancerPairConfigs.configs);
		saveLoadBalancerConfigs(doc, lbNode, LoadBalancerConfig.ELEMENT_ADHOC_CONFIGS, LoadBalancerConfig.ELEMENT_ADHOC_CONFIG, modelmgr.getModel().loadBalancerAdhocConfigs.configs);
		root.appendChild(lbNode);		
		
		PropertyElementList propList = modelmgr.getPropertyElementList();
		Element propsNode = savePropertyElementList(doc, propList);
		root.appendChild(propsNode);
	}

	private static void saveProcUnitSecurityConfig(Document doc, Element procUnitNode, ProcessingUnitSecurityConfig puSecurityConfig, 
																													CacheOmSecurityConfig cacheOmSecurityConfig) {
		if (cacheOmSecurityConfig.securityEnabled || !puSecurityConfig.securityControllerValues.isEmpty() 
																			|| !puSecurityConfig.securityRequesterValues.isEmpty()) {
			Element securityConfigNode = createDocElement(doc, Elements.SECURITY_CONFIG.localName);
			//Security Role
			createTextElementNode(doc, securityConfigNode, Elements.SECURITY_ROLE.localName, puSecurityConfig.securityRole);			

			//Controller
			Element securityControllerNode = createDocElement(doc, Elements.SECURITY_CONTROLLER.localName);
			createMapElementNodeWithOverrideAttr(doc, securityControllerNode, puSecurityConfig.securityControllerValues, puSecurityConfig.securityControllerOverrides);
			//saveSecurityController(doc, securityControllerNode, securityConfig);
			securityConfigNode.appendChild(securityControllerNode);
			
			//Requester
			Element securityRequestorNode = createDocElement(doc, Elements.SECURITY_REQUESTER.localName);
			createMapElementNodeWithOverrideAttr(doc, securityRequestorNode, puSecurityConfig.securityRequesterValues, puSecurityConfig.securityRequesterOverrides);
			//saveSecurityRequestor(doc, securityRequestorNode, securityConfig);
			securityConfigNode.appendChild(securityRequestorNode);
			procUnitNode.appendChild(securityConfigNode);
		}	
	}
	
	private static Element createDocElement(Document doc, String elementName) {
		Element element = doc.createElement(elementName);
		//element.setPrefix(prefix);
		return element;
	}
	
	private static void saveCacheOm(Document doc, Element cacheOmNode, ClusterConfigModelMgr modelmgr) { 
		CacheOm cacheOm = modelmgr.getModel().om.cacheOm; 
		
		Element providerNode = createDocElement(doc, Elements.PROVIDER.localName);
		createTextElementNode(doc, providerNode, Elements.NAME.localName, modelmgr.getProvider());
		cacheOmNode.appendChild(providerNode);

		createMapElementNode(doc, cacheOmNode, cacheOm.values);
		
		Element objTableNode = createDocElement(doc, Elements.OBJECT_TABLE.localName);
		createTextElementNode(doc, objTableNode, Elements.MAX_SIZE.localName, cacheOm.objectTableSize);
		cacheOmNode.appendChild(objTableNode);
		
		Element bsNode = createDocElement(doc, Elements.BACKING_STORE.localName);
		createMapElementNode(doc, bsNode, cacheOm.bs.values);
		if (cacheOm.bs.primaryConnection != null)
			saveBackingStoreConnectionNode(doc, bsNode, cacheOm.bs.primaryConnection, Elements.PRIMARY_CONNECTION.localName);
		cacheOmNode.appendChild(bsNode);

		Element dosNode = createDocElement(doc, Elements.DOMAIN_OBJECTS.localName);
		createMapElementNode(doc, dosNode, cacheOm.domainObjects.domainObjDefault.values);
		for (DomainObject domainObj: cacheOm.domainObjects.domainObjOverrides.overrides) {
			saveDomainObjectNode(doc, dosNode, cacheOm, domainObj);
		}
		cacheOmNode.appendChild(dosNode);
		
		saveCacheOmSecurityConfig(doc, cacheOmNode, cacheOm.securityConfig);
	}
	
	private static void saveCacheOmSecurityConfig(Document doc, Element cacheOmNode, CacheOmSecurityConfig cacheOmSecurityConfig) {
		if (cacheOmSecurityConfig.securityEnabled || !cacheOmSecurityConfig.securityControllerValues.isEmpty() 
																		|| !cacheOmSecurityConfig.securityRequesterValues.isEmpty()) {
			Element securityConfigNode = createDocElement(doc, Elements.SECURITY_CONFIG.localName);
			//Enabled
			createTextElementNode(doc, securityConfigNode, Elements.ENABLED.localName, Boolean.toString(cacheOmSecurityConfig.securityEnabled));
			//Controller
			Element securityControllerNode = createDocElement(doc, Elements.SECURITY_CONTROLLER.localName);
			createMapElementNode(doc, securityControllerNode, cacheOmSecurityConfig.securityControllerValues);
			securityConfigNode.appendChild(securityControllerNode);
			//Requester
			Element securityRequestorNode = createDocElement(doc, Elements.SECURITY_REQUESTER.localName);
			createMapElementNode(doc, securityRequestorNode, cacheOmSecurityConfig.securityRequesterValues);
			securityConfigNode.appendChild(securityRequestorNode);
			cacheOmNode.appendChild(securityConfigNode);
		}	
	}

	private static void createMapElementNodeWithOverrideAttr(Document doc, Node parentNode, Map<String, String> elementMap, Map<String, Boolean> overrideMap) {
		if (elementMap == null)
			return;
		for (Map.Entry<String, String> entry: elementMap.entrySet()) {
			Element node = createDocElement(doc, entry.getKey());
			node.setTextContent(entry.getValue());
			Boolean override = overrideMap.get(entry.getKey());
			if (override == null) override = false;
			node.setAttribute(Attributes.OVERRIDE.localName, Boolean.toString(override));
			parentNode.appendChild(node);
		}
	}

	private static void saveBackingStoreConnectionNode(Document doc, Element bsNode, Connection con, String conName) {
		Element conNode = createDocElement(doc, conName);
		createMapElementNode(doc, conNode, con.values);
		bsNode.appendChild(conNode);
	}

	private static void saveDomainObjectNode(Document doc, Element dosNode, CacheOm cacheOm, DomainObject domainObj) {
		Element doNode = createDocElement(doc, Elements.DOMAIN_OBJECT.localName);
		//createMapElementNode(doc, doNode, domainObj.values);
		for (Map.Entry<String, String> entry: domainObj.values.entrySet()) {
			if (entry.getValue().equals("default") || entry.getValue().equals("")) 
				continue;
			String defSetting = cacheOm.domainObjects.domainObjDefault.values.get(entry.getKey());
			if (defSetting == null || !defSetting.equals(entry.getValue())) {
				Element node = createDocElement(doc, entry.getKey());
				node.setTextContent(entry.getValue());
				doNode.appendChild(node);
			}
		}
		
		Element bsNode = createDocElement(doc, Elements.BACKING_STORE.localName);
		createMapElementNode(doc, bsNode, domainObj.bs.values);
		Element propsNode = createDocElement(doc, Elements.PROPERTIES.localName);
		for (Map.Entry<String, DomainObjectProperty> entry: domainObj.props.entrySet()) {
			Element propNode = createDocElement(doc, Elements.PROPERTY.localName);
			Element node = createDocElement(doc, Elements.NAME.localName);
			node.setTextContent(entry.getKey());
			propNode.appendChild(node);
			if (!entry.getValue().values.get(Elements.MAX_SIZE.localName).trim().equals(""))
				createTextElementNode(doc, propNode, Elements.MAX_SIZE.localName, entry.getValue().values.get(Elements.MAX_SIZE.localName));
			if (!entry.getValue().values.get(Elements.REVERSE_REFERENCES.localName).trim().equals("true"))
				createTextElementNode(doc, propNode, Elements.REVERSE_REFERENCES.localName, entry.getValue().values.get(Elements.REVERSE_REFERENCES.localName));
			propsNode.appendChild(propNode);
		}
		bsNode.appendChild(propsNode);
		doNode.appendChild(bsNode);

		Element indexesNode = createDocElement(doc, Elements.INDEXES.localName);
		Element indexNode = createDocElement(doc, Elements.INDEX.localName);
		for (Map.Entry<String, DomainObjectProperty> entry: domainObj.props.entrySet()) {
			if (entry.getValue().values.get(Elements.INDEX.localName).equals("true")) { // only if it is "true", we need to create the property node
				createTextElementNode(doc, indexNode, Elements.PROPERTY.localName, entry.getKey());
			}
		}
		indexesNode.appendChild(indexNode);
		doNode.appendChild(indexesNode);
		
		Element encryptedNode = createDocElement(doc, "encryption");
		for (Map.Entry<String, DomainObjectProperty> entry: domainObj.props.entrySet()) {
			if (entry.getValue().values.get("encryption").equals("true")) { // only if it is "true", we need to create the property node
				createTextElementNode(doc, encryptedNode, Elements.PROPERTY.localName, entry.getKey());
			}
		}
		doNode.appendChild(encryptedNode);
		
		Element compIndexesNode = createDocElement(doc, Elements.COMPOSITEINDEXES.localName);
        
        for (DomainObjectCompositeIndex compositeIndex: domainObj.compIdxList) {
            Element compIndexNode = createDocElement(doc, Elements.COMPOSITEINDEX.localName);
            createTextElementNode(doc, compIndexNode, Elements.NAME.localName,compositeIndex.name);
            propsNode = createDocElement(doc, Elements.PROPERTIES.localName);
            createListElementNode(doc, propsNode, (ArrayList<String>)compositeIndex.values, Elements.PROPERTY.localName);
            compIndexNode.appendChild(propsNode);
            compIndexesNode.appendChild(compIndexNode);
        }
        
        doNode.appendChild(compIndexesNode);
		dosNode.appendChild(doNode);
	}
	
	private static void saveRules(Document doc, Element rulesNode, RulesGrp rulesGrp) {
		for (RuleElement ruleElement: rulesGrp.rules) {
			if (ruleElement instanceof Rule) {
				Rule rule = (Rule) ruleElement;
				if (!rule.isRef)
					createTextElementNode(doc, rulesNode, "uri", rule.uri);
				else
					createTextElementNode(doc, rulesNode, "ref", rule.uri);
			} else {
				RulesGrp rulesGrpRef = (RulesGrp) ruleElement;
				createTextElementNode(doc, rulesNode, "id", rulesGrpRef.id);
			}
		}
	}
	
	private static void saveDestinations(Document doc, Node destinationsNode, DestinationsGrp destinationsGrp) {
		for (DestinationElement destinationElement: destinationsGrp.destinations) {
			if (destinationElement instanceof Destination) {
				Destination destination = (Destination) destinationElement;
				if (!destination.isRef)
					saveDestination(doc, destinationsNode, destination);
				else
					createTextElementNode(doc, destinationsNode, "ref", destination.id);
			} else {
				DestinationsGrp destinationsGrpRef = (DestinationsGrp) destinationElement;
				createTextElementNode(doc, destinationsNode, "id", destinationsGrpRef.id);
			}
		}
	}
	
	private static void saveDestination(Document doc, Node destinationsNode, Destination destination) {
		Element destinationNode = createDocElement(doc, Elements.DESTINATION.localName);
		destinationNode.setAttribute("id", destination.id);
		createMapElementNode(doc, destinationNode, destination.destinationVal);
		destinationsNode.appendChild(destinationNode);
	}
	
	private static void saveFunctions(Document doc, Element functionsNode, FunctionsGrp functionsGrp) {
		for (FunctionElement functionElement: functionsGrp.functions) {
			if (functionElement instanceof Function) {
				Function function = (Function) functionElement;
				if (!function.isRef)
					createTextElementNode(doc, functionsNode, "uri", function.uri);
				else
					createTextElementNode(doc, functionsNode, "ref", function.uri);
			} else {
				FunctionsGrp functionsGrpRef = (FunctionsGrp) functionElement;
				createTextElementNode(doc, functionsNode, "id", functionsGrpRef.id);
			}
		}
	}

	private static void saveProcesses(Document doc, Element processesNode, ProcessesGrp processesGrp) {
		for (ProcessElement processElement: processesGrp.processes) {
			if (processElement instanceof Process) {
				Process process = (Process) processElement;
				if (!process.isRef)
					createTextElementNode(doc, processesNode, "uri", process.uri);
				else
					createTextElementNode(doc, processesNode, "ref", process.uri);
			} else {
				ProcessesGrp processesGrpRef = (ProcessesGrp) processElement;
				createTextElementNode(doc, processesNode, "id", processesGrpRef.id);
			}
		}
	}
	
	private static Element saveInfAgentNode(Document doc, AgentClass agent) {
		InfAgent infAgent = ((InfAgent) agent);
		Element infNode = createDocElement(doc, Elements.INFERENCE_AGENT_CLASS.localName);
		infNode.setAttribute("id", agent.name);
		saveAgentRules(doc, infNode, infAgent.agentRulesGrpObj);
		saveAgentDestinations(doc, infNode, infAgent.agentDestinationsGrpObj);
		saveAgentFunctions(doc, infNode, infAgent, true);
		saveAgentFunctions(doc, infNode, infAgent, false);
		saveAgentLocalCache(doc, infNode, infAgent);
		saveAgentSharedQueue(doc, infNode, infAgent);
		saveAgentLoad(doc, infNode, infAgent.maxActive);
		createBooleanElementNode(doc, infNode, Elements.CONCURRENT_RTC.localName, infAgent.concRtc);
		createBooleanElementNode(doc, infNode, Elements.CHECK_FOR_DUPLICATES.localName, infAgent.checkForDuplicates);
		Element bwNode = createDocElement(doc, Elements.BUSINESSWORKS.localName);
		createTextElementNode(doc, bwNode, Elements.URI.localName, infAgent.bwRepoUrl);
		infNode.appendChild(bwNode);
		saveAgentProperties(doc, infNode, infAgent);
		return infNode;
	}
	
	private static Element saveCacheAgentNode(Document doc, AgentClass agent) {
		CacheAgent cacheAgent = ((CacheAgent) agent);
		Element cacheNode = createDocElement(doc, Elements.CACHE_AGENT_CLASS.localName);
		cacheNode.setAttribute("id", agent.name);
		saveAgentProperties(doc, cacheNode, cacheAgent);
		return cacheNode;
	}
	
	private static Element saveQueryAgentNode(Document doc, AgentClass agent) {
		QueryAgent queryAgent = (QueryAgent) agent;
		Element queryNode = createDocElement(doc, Elements.QUERY_AGENT_CLASS.localName);
		queryNode.setAttribute("id", agent.name);
		saveAgentDestinations(doc, queryNode, queryAgent.agentDestinationsGrpObj);
		saveAgentFunctions(doc, queryNode, queryAgent, true);
		saveAgentFunctions(doc, queryNode, queryAgent, false);
		saveAgentLocalCache(doc, queryNode, queryAgent);
		saveAgentSharedQueue(doc, queryNode, queryAgent);
		saveAgentLoad(doc, queryNode, queryAgent.maxActive);
		saveAgentProperties(doc, queryNode, queryAgent);
		return queryNode;
	}

	private static Element saveDashAgentNode(Document doc, AgentClass agent) {
		DashboardAgent dashAgent = (DashboardAgent) agent;
		Element dashNode = createDocElement(doc, Elements.DASHBOARD_AGENT_CLASS.localName);
		dashNode.setAttribute("id", agent.name);
		saveAgentRules(doc, dashNode, dashAgent.agentRulesGrpObj);
		saveAgentDestinations(doc, dashNode, dashAgent.agentDestinationsGrpObj);
		saveAgentFunctions(doc, dashNode, dashAgent, true);
		saveAgentFunctions(doc, dashNode, dashAgent, false);
		saveAgentProperties(doc, dashNode, dashAgent);
		return dashNode;
	}	
	
	private static Element saveMMAgentNode(Document doc, AgentClass agent) {
		MMAgent mmAgent = (MMAgent) agent;
		Element mmNode = createDocElement(doc, Elements.MM_AGENT_CLASS.localName);
		mmNode.setAttribute("id", agent.name);
		
		createTextElementNode(doc, mmNode, Elements.MM_INFERENCE_AGENT.localName, mmAgent.infAgentRef);
		createTextElementNode(doc, mmNode, Elements.MM_QUERY_AGENT.localName, mmAgent.queryAgentRef);

		Node alertConfigSetNode = createDocElement(doc, Elements.ALERT_CONFIG_SET.localName);
		for (AlertConfig alertConfig: mmAgent.alertConfigList.alertConfigs) {
			Element alertConfigNode = createDocElement(doc, Elements.ALERT_CONFIG.localName);
			alertConfigNode.setAttribute("alert-name", alertConfig.id);
			Element condNode = createDocElement(doc, Elements.CONDITION.localName);
			Element getPropNode = createDocElement(doc, Elements.GET_PROPERTY.localName);
			createMapElementAttributes(doc, getPropNode, alertConfig.condition.values);
			condNode.appendChild(getPropNode);
			alertConfigNode.appendChild(condNode);
			Element projNode = createDocElement(doc, Elements.PROJECTION.localName);
			for (LinkedHashMap<String, String> map: alertConfig.projection.properties) {
				Element setPropNode = createDocElement(doc, Elements.SET_PROPERTY.localName);
				createMapElementAttributes(doc, setPropNode, map);
				projNode.appendChild(setPropNode);
			}
			alertConfigNode.appendChild(projNode);
			alertConfigSetNode.appendChild(alertConfigNode);
		}
		mmNode.appendChild(alertConfigSetNode);
		
		Node ruleConfigSetNode = createDocElement(doc, Elements.RULE_CONFIG.localName);
		for (ClusterMember clusterMember: mmAgent.ruleConfigList.clusterMembers) {
			Element clusterMemberNode = createDocElement(doc, Elements.CLUSTER_MEMBER.localName);
			clusterMemberNode.setAttribute("path", clusterMember.path);
			clusterMemberNode.setAttribute("member-name", clusterMember.id);
			for (SetProperty setProperty: clusterMember.setProperties) {
				Element setPropNode = createDocElement(doc, Elements.SET_PROPERTY.localName);
				setPropNode.setAttribute(Elements.NAME.localName, setProperty.name);
				setPropNode.setAttribute("value", setProperty.value);
				setPropNode.setAttribute("set-property-name", setProperty.id);
				if (setProperty.setPropertyChild instanceof ChildClusterMember) {
					ChildClusterMember childClusterMember = (ChildClusterMember) setProperty.setPropertyChild;
					Element chdNode = createDocElement(doc, "child-cluster-member");
					chdNode.setAttribute("path", childClusterMember.path);
					chdNode.setAttribute("tolerance", childClusterMember.tolerance);
					for (LinkedHashMap<String, String> map: childClusterMember.properties) {
						Element propNode = createDocElement(doc, Elements.PROPERTY.localName);
						createMapElementAttributes(doc, propNode, map);
						chdNode.appendChild(propNode);
					}
					setPropNode.appendChild(chdNode);
				} else if (setProperty.setPropertyChild instanceof Notification) {
					Notification notification = (Notification) setProperty.setPropertyChild;
					Element chdNode = createDocElement(doc, "notification");
					chdNode.setAttribute("range", notification.range);
					chdNode.setAttribute("tolerance", notification.tolerance);
					for (LinkedHashMap<String, String> map: notification.properties) {
						Element propNode = createDocElement(doc, Elements.PROPERTY.localName);
						createMapElementAttributes(doc, propNode, map);
						chdNode.appendChild(propNode);
					}
					setPropNode.appendChild(chdNode);
				} 
				clusterMemberNode.appendChild(setPropNode);
			}
			ruleConfigSetNode.appendChild(clusterMemberNode);
		}
		mmNode.appendChild(ruleConfigSetNode);
			
		Node actionConfigSetNode = createDocElement(doc, Elements.MM_ACTION_CONFIG_SET.localName);
		for (ActionConfig actionConfig: mmAgent.actionConfigList.actionConfigs) {
			Element actionConfigNode = createDocElement(doc, Elements.MM_ACTION_CONFIG.localName);
			actionConfigNode.setAttribute("action-name", actionConfig.id);
			Element condNode = createDocElement(doc, Elements.MM_TRIGGER_CONDITION.localName);
			if (actionConfig.triggerCondition.trigger instanceof Alert) {
				Element alertNode = createDocElement(doc, Elements.MM_ALERT.localName);
				alertNode.setAttribute("path", ((Alert)actionConfig.triggerCondition.trigger).path);
				alertNode.setAttribute("severity", ((Alert)actionConfig.triggerCondition.trigger).severity);
				condNode.appendChild(alertNode);
			} else {
				Element healthNode = createDocElement(doc, Elements.MM_HEALTH_LEVEL.localName);
				healthNode.setAttribute("path", ((HealthLevel)actionConfig.triggerCondition.trigger).path);
				healthNode.setAttribute("value", ((HealthLevel)actionConfig.triggerCondition.trigger).value);
				condNode.appendChild(healthNode);
			}
			actionConfigNode.appendChild(condNode);
			Element actionNode = createDocElement(doc, Elements.MM_ACTION.localName);
			if (actionConfig.action.actionElement instanceof ExecCommand) {
				Element execNode = createDocElement(doc, Elements.MM_EXECUTE_COMMAND.localName);
				execNode.setAttribute("command", ((ExecCommand)actionConfig.action.actionElement).command);
				actionNode.appendChild(execNode);
			} else {
				Element emailNode = createDocElement(doc, Elements.MM_SEND_EMAIL.localName);
				createMapElementAttributes(doc, emailNode, ((SendEmail)actionConfig.action.actionElement).values);
				actionNode.appendChild(emailNode);
			}
			actionConfigNode.appendChild(actionNode);
			actionConfigSetNode.appendChild(actionConfigNode);
		}
		mmNode.appendChild(actionConfigSetNode);
		
		saveAgentProperties(doc, mmNode, mmAgent);
		
		return mmNode;
	}
	
	private static Element saveProcessAgentNode(Document doc, AgentClass agent) {
		ProcessAgent processAgent = ((ProcessAgent) agent);
		Element procNode = createDocElement(doc, Elements.PROCESS_AGENT_CLASS.localName);
		procNode.setAttribute("id", agent.name);
		saveAgentLoad(doc, procNode, processAgent.maxActive);

		Element procEngNode = createDocElement(doc, Elements.PROCESS_ENGINE.localName);
		saveAgentProcesses(doc, procEngNode, processAgent.agentProcessesGrpObj);
		saveAgentRules(doc, procEngNode, processAgent.agentRulesGrpObj);
		saveAgentDestinations(doc, procEngNode, processAgent.agentDestinationsGrpObj);
		saveAgentFunctions(doc, procEngNode, processAgent, true);
		saveAgentFunctions(doc, procEngNode, processAgent, false);
		saveAgentJobManager(doc, procEngNode, processAgent);

		Element infNode = createDocElement(doc, Elements.INFERENCE_ENGINE.localName);
		/*
		saveAgentLocalCache(doc, infNode, processAgent.infEngine);
		//saveAgentSharedQueue(doc, infNode, processAgent.infEngine);
		createBooleanElementNode(doc, infNode, Elements.CONCURRENT_RTC.localName, processAgent.infEngine.concRtc);
		createBooleanElementNode(doc, infNode, Elements.CHECK_FOR_DUPLICATES.localName, processAgent.infEngine.checkForDuplicates);
		*/
		procEngNode.appendChild(infNode);
		
		
		procNode.appendChild(procEngNode);

		saveAgentProperties(doc, procNode, processAgent);
		return procNode;
	}
	
	private static void saveAgentRules(Document doc, Element agentNode, AgentRulesGrp agentRulesGrp) {
		Element rulesNode = createDocElement(doc, Elements.RULES.localName);
		for (RuleElement ruleElement: agentRulesGrp.agentRules) {
		if (ruleElement instanceof Rule) {
			Rule rule = (Rule) ruleElement;
			if (rule != null) {
				if (!rule.isRef)
					createTextElementNode(doc, rulesNode, Elements.URI.localName, rule.uri);
				else
					createTextElementNode(doc, rulesNode, Elements.REF.localName, rule.uri);
			}
		} else if (ruleElement instanceof AgentRulesGrpElement) {
			AgentRulesGrpElement rulesGrpRef = (AgentRulesGrpElement) ruleElement;
			createTextElementNode(doc, rulesNode, Elements.REF.localName, rulesGrpRef.rulesGrp.id);
		}
		}
		agentNode.appendChild(rulesNode);
	}
	
	private static void saveAgentDestinations(Document doc, Element agentNode, AgentDestinationsGrp agentDestinationsGrp) {
		Element destinationsNode = createDocElement(doc, Elements.DESTINATIONS.localName);
		for (DestinationElement destinationElement: agentDestinationsGrp.agentDestinations) {
			if (destinationElement instanceof Destination) {
				Destination destination = (Destination) destinationElement;
				if (destination != null) {
					if (!destination.isRef) {
						saveDestination(doc, destinationsNode, destination);
					} else {
						createTextElementNode(doc, destinationsNode, Elements.REF.localName, destination.id);
					}
				}
			} else if (destinationElement instanceof AgentDestinationsGrpElement) {
				AgentDestinationsGrpElement destinationsGrpRef = (AgentDestinationsGrpElement) destinationElement;
				createTextElementNode(doc, destinationsNode, Elements.REF.localName, destinationsGrpRef.destinationsGrp.id);
			}
		}
		agentNode.appendChild(destinationsNode);
	}
	
	private static void saveAgentFunctions(Document doc, Element agentNode, DashInfProcQueryAgent agent, boolean isStartup) {
		Element funcNode;
		AgentBaseFunctionsGrp agentFunctionsGrp;
		if (isStartup) {
			funcNode = createDocElement(doc, Elements.STARTUP.localName);
			agentFunctionsGrp = agent.agentStartupFunctionsGrpObj;
		} else {
			funcNode = createDocElement(doc, Elements.SHUTDOWN.localName);
			agentFunctionsGrp = agent.agentShutdownFunctionsGrpObj;
		}
		for (FunctionElement functionElement: agentFunctionsGrp.agentFunctions) {
			if (functionElement instanceof Function) {
				Function function = (Function) functionElement;
				if (function != null) {
					if (!function.isRef)
						createTextElementNode(doc, funcNode, Elements.URI.localName, function.uri);
					else
						createTextElementNode(doc, funcNode, Elements.REF.localName, function.uri);
				}
			} else if (functionElement instanceof AgentFunctionsGrpElement) {
				AgentFunctionsGrpElement functionsGrpRef = (AgentFunctionsGrpElement) functionElement;
				createTextElementNode(doc, funcNode, Elements.REF.localName, functionsGrpRef.functionsGrp.id);
			}
		}
		agentNode.appendChild(funcNode);
	}
	
	private static void saveAgentProcesses(Document doc, Element agentNode, AgentProcessesGrp agentProcessesGrp) {
		Element processesNode = createDocElement(doc, Elements.PROCESS.localName);
		for (ProcessElement processElement: agentProcessesGrp.agentProcesses) {
			if (processElement instanceof Process) {
				Process process = (Process) processElement;
				if (process != null) {
					if (!process.isRef)
						createTextElementNode(doc, processesNode, Elements.URI.localName, process.uri);
					else
						createTextElementNode(doc, processesNode, Elements.REF.localName, process.uri);
				}
			} else if (processElement instanceof AgentProcessesGrpElement) {
				AgentProcessesGrpElement processesGrpRef = (AgentProcessesGrpElement) processElement;
				createTextElementNode(doc, processesNode, Elements.REF.localName, processesGrpRef.processesGrp.id);
			}
		}
		agentNode.appendChild(processesNode);
	}
	
	private static void saveAgentLocalCache(Document doc, Element agentNode, DashInfProcQueryAgent agent) {
		Element localCacheNode = createDocElement(doc, Elements.LOCAL_CACHE.localName);
		Node evictionNode = createDocElement(doc, Elements.EVICTION.localName);
		createTextElementNode(doc, evictionNode, Elements.MAX_SIZE.localName, agent.localCacheMaxSize);
		createTextElementNode(doc, evictionNode, Elements.MAX_TIME.localName, agent.localCacheEvictionTime);
		localCacheNode.appendChild(evictionNode);
		agentNode.appendChild(localCacheNode);
	}
	
	private static void saveAgentSharedQueue(Document doc, Element agentNode, DashInfProcQueryAgent agent) {
		Element sharedQueueNode = createDocElement(doc, Elements.SHARED_QUEUE.localName);
		createTextElementNode(doc, sharedQueueNode, Elements.SIZE.localName, agent.sharedQueueSize);
		createTextElementNode(doc, sharedQueueNode, Elements.WORKERS.localName, agent.sharedQueueWorkers);
		agentNode.appendChild(sharedQueueNode);
	}
	
	private static void saveAgentLoad(Document doc, Element agentNode, String maxActive) {
		Element loadNode = createDocElement(doc, Elements.LOAD.localName);
		createTextElementNode(doc, loadNode, Elements.MAX_ACTIVE.localName, maxActive);
		agentNode.appendChild(loadNode);
	}
	
	private static void saveAgentJobManager(Document doc, Element agentNode, ProcessAgent processAgent) {
		Element jobMgrNode = createDocElement(doc, Elements.JOB_MANAGER.localName);
		createTextElementNode(doc, jobMgrNode, "job-pool-queue-size", processAgent.jobPoolQueueSize);
		createTextElementNode(doc, jobMgrNode, "job-pool-thread-count", processAgent.jobPoolThreadCount);
		agentNode.appendChild(jobMgrNode);
	}
	
	private static void saveAgentProperties(Document doc, Element agentNode, AgentClass agent) {
		Element propsNode = savePropertyElementList(doc, agent.properties);
		agentNode.appendChild(propsNode);
	}
	
	private static Element savePropertyElementList(Document doc, PropertyElementList propList) {
		Element propsNode = createDocElement(doc, "property-group");
		if (propList == null)
			return propsNode;
		for (PropertyElement prop: propList.propertyList) {
			savePropertyElement(doc, propsNode, prop);
		}
		for (PropertyElement prop: propList.uiList) {
			savePropertyElement(doc, propsNode, prop);
		}
		return propsNode;
	}
	
	private static void savePropertyElement(Document doc, Element propsNode, PropertyElement prop) {
		if (prop instanceof Property) {
			Element propNode = createDocElement(doc, "property");
			createMapElementAttributes(doc, propNode, ((Property)prop).fieldMap);
			propsNode.appendChild(propNode);
		} else if (prop instanceof PropertyGroup) {
			Element propGrpNode = savePropertyGroup(doc, (PropertyGroup)prop);
			propsNode.appendChild(propGrpNode);
		}
	}
	
	private static Element savePropertyGroup(Document doc, PropertyGroup propGrp) {
		Element propGrpNode = createDocElement(doc, "property-group");
		propGrpNode.setAttribute("name", propGrp.name);
		propGrpNode.setAttribute("comment", propGrp.comment);
		for (PropertyElement prop: propGrp.propertyList) {
			if (prop instanceof Property) {
				Element propNode = createDocElement(doc, "property");
				createMapElementAttributes(doc, propNode, ((Property)prop).fieldMap);
				propGrpNode.appendChild(propNode);
			} else if (prop instanceof PropertyGroup) {
				Element chdPropGrpNode = savePropertyGroup(doc, (PropertyGroup)prop);
				propGrpNode.appendChild(chdPropGrpNode);
			}
		}
		return propGrpNode;
	}
	
	private static void saveLoadBalancerConfigs(Document doc, Element lbNode, String lbParentNodeName, String lbConfigNodeName, ArrayList<?> configs) {
		Element configsNode = createDocElement(doc, lbParentNodeName);
		for (Object config: configs) {
			Element configNode = createDocElement(doc, lbConfigNodeName);
			createMapElementNode(doc, configNode, ((LoadBalancerConfig)config).values);
			PropertyElementList propList = ((LoadBalancerConfig)config).properties;
			Element propsNode = savePropertyElementList(doc, propList);
			configNode.appendChild(propsNode);
			configsNode.appendChild(configNode);
		}
		lbNode.appendChild(configsNode);
	}
	
	private static Element saveLiveViewAgentNode(Document doc, AgentClass agent, IProject project, CacheOm cacheOm) {
		LiveViewAgent lvAgent = ((LiveViewAgent) agent);
		Element lvNode = createDocElement(doc, Elements.LIVEVIEW_AGENT_CLASS.localName);
		lvNode.setAttribute("id", agent.name);
		saveAgentLoad(doc, lvNode, lvAgent.maxActive);
		
		saveLDMConnectionNode(doc, lvNode, lvAgent.ldmConnection);
		savePublisherNode(doc, lvNode, lvAgent);
		try {
			saveEntityFilterSetNode(doc, lvNode, lvAgent.entitySetConfig, project, cacheOm);
		} catch (Exception exception) {
			StudioCorePlugin.log(new RuntimeException(exception));
		}
		saveAgentProperties(doc, lvNode, lvAgent);
		
		return lvNode;
	}
	
	private static void saveLDMConnectionNode(Document doc, Element parentNode, LDMConnection ldmConnection) {
		Element ldmConnectionNode = createDocElement(doc, Elements.LDM_CONNECTION.localName);
		
		createTextElementNode(doc, ldmConnectionNode, Elements.LDM_URL.localName, ldmConnection.ldmUrl);
		createTextElementNode(doc, ldmConnectionNode, Elements.SECURITY_USER_NAME.localName, ldmConnection.userName);
		createTextElementNode(doc, ldmConnectionNode, Elements.SECURITY_USER_PASSWORD.localName, ldmConnection.userPassword);
		createTextElementNode(doc, ldmConnectionNode, Elements.INITIAL_SIZE.localName, ldmConnection.initialSize);
		createTextElementNode(doc, ldmConnectionNode, Elements.MAX_SIZE.localName, ldmConnection.maxSize);
		
		parentNode.appendChild(ldmConnectionNode);
	}
	
	private static void savePublisherNode(Document doc, Element parentNode, LiveViewAgent lvAgent) {
		Element publisherNode = createDocElement(doc, Elements.PUBLISHER.localName);
		
		createTextElementNode(doc, publisherNode, Elements.PUBLISHER_QUEUE_SIZE.localName, lvAgent.publisherQueueSize);
		createTextElementNode(doc, publisherNode, Elements.PUBLISHER_THREAD_COUNT.localName, lvAgent.publisherThreadCount);
		
		parentNode.appendChild(publisherNode);
	}
	
	private static void saveEntityFilterSetNode(Document doc, Element parentNode, EntitySetConfig entitySetConfig, IProject project, CacheOm cacheOm) throws Exception {
		Element entitySetConfigNode = createDocElement(doc, Elements.ENTITY_SET.localName);

		createTextElementNode(doc, entitySetConfigNode, Elements.GENERATE_LV_FILES.localName, entitySetConfig.generateLVFiles);
		createTextElementNode(doc, entitySetConfigNode, Elements.OUTPUT_PATH.localName, entitySetConfig.outputPath);

		Map<String, Map<String,String>> entityUriToTrimmingMap = new HashMap<String, Map<String, String>>();
		for (EntityConfig ec : entitySetConfig.entityElements) {
			createEntityFilterNode(doc, entitySetConfigNode, ec, entityUriToTrimmingMap);
		}
		
		if (entityUriToTrimmingMap.size() > 0 && Boolean.valueOf(entitySetConfig.generateLVFiles) && entitySetConfig.outputPath != null && !entitySetConfig.outputPath.isEmpty() && project != null) {
			generateLVProject(entityUriToTrimmingMap, project, entitySetConfig.outputPath, isSharedNothingStorage(cacheOm), overideDomainModelMap(cacheOm));
		}
		
		parentNode.appendChild(entitySetConfigNode);
	}
	
	
	private static void generateLVProject(Map<String, Map<String,String>> entityUriToTrimmingMap, IProject project, String outputPath, boolean isSharedNothing, Map<String, Map<String, String>> overrideModelMap) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(ILiveViewProjectGenerator.BE_LIVEVIEW_EXTENSION_POINT_PROJECT_GENERATOR);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(ILiveViewProjectGenerator.BE_LIVEVIEW_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof ILiveViewProjectGenerator) {
				((ILiveViewProjectGenerator) o).generateLVConfigFiles(entityUriToTrimmingMap, project, outputPath, isSharedNothing, overrideModelMap);;
			}
		}	
	}
	
	private static void createEntityFilterNode(Document doc, Element parentNode, EntityConfig entityConfig, Map<String, Map<String,String>> entityUriToTrimmingMap) {
		Element entityConfigNode = createDocElement(doc, Elements.ENTITY.localName);
		entityConfigNode.setAttribute("id", entityConfig.id);
		
		createTextElementNode(doc, entityConfigNode, Elements.URI.localName, entityConfig.entityUri);
		createTextElementNode(doc, entityConfigNode, Elements.FILTER.localName, entityConfig.filter);
		createTextElementNode(doc, entityConfigNode, Elements.ENABLE_TRIMMING.localName, entityConfig.enableTableTrimming);
		createTextElementNode(doc, entityConfigNode, Elements.TRIMMING_FIELD.localName, entityConfig.trimmingField);
		createTextElementNode(doc, entityConfigNode, Elements.TRIMMING_RULE.localName, entityConfig.trimmingRule);
		
		Map<String, String> trimmingMap = new HashMap<String, String>();
		trimmingMap.put(Elements.ENABLE_TRIMMING.localName, entityConfig.enableTableTrimming);
		trimmingMap.put(Elements.TRIMMING_FIELD.localName, entityConfig.trimmingField);
		trimmingMap.put(Elements.TRIMMING_RULE.localName, entityConfig.trimmingRule);
		
		entityUriToTrimmingMap.put(entityConfig.entityUri, trimmingMap);
		
		parentNode.appendChild(entityConfigNode);
	}
	
	public static boolean isSharedNothingStorage(CacheOm cacheOm) {
		return BackingStoreConfig.PERSISTENCE_OPTION_SHARED_NOTHING.equals(cacheOm.bs.values.get(BackingStore.PERSISTENCE_OPTION));
	}
	
	public static Map<String, Map<String,String>> overideDomainModelMap(CacheOm cacheOm) {
		Map<String, Map<String,String>> domainModelMap = new HashMap<String, Map<String, String>>();
		
		for (DomainObject domainObj: cacheOm.domainObjects.domainObjOverrides.overrides) {
			Map<String, String> propMap = new HashMap<String, String>();
			for (Map.Entry<String, DomainObjectProperty> entry: domainObj.props.entrySet()) {
				String props = "";
				if (entry.getValue().values.get(Elements.REVERSE_REFERENCES.localName).trim().equals("true")) {
					props = Elements.REVERSE_REFERENCES.localName + ",";
				}
				if (entry.getValue().values.get(Elements.INDEX.localName).trim().equals("true")) {
					props += Elements.INDEX.localName;
				}
				
				if (!props.isEmpty()) {
					if (props.endsWith(",")) props = props.substring(0, props.length()-1);
					propMap.put(entry.getKey(), props);
				}
				
			}
			domainModelMap.put(domainObj.values.get(Elements.URI.localName), propMap);
		}
		
		return domainModelMap;
	}
		
	private static void createTextElementNode(Document doc, Node parentNode, String nodeName, String value) {
		Element node = createDocElement(doc, nodeName);
		node.setTextContent(value);
		parentNode.appendChild(node);
	}
	
	private static void createBooleanElementNode(Document doc, Node parentNode, String nodeName, boolean value) {
		createTextElementNode(doc, parentNode, nodeName, new Boolean(value).toString());
	}

	private static void createListElementNode(Document doc, Node parentNode, ArrayList<String> list, String key) {
		if (list == null)
			return;
		for (String entry: list) {
			Element node = createDocElement(doc, key);
			node.setTextContent(entry);
			parentNode.appendChild(node);
		}
	}
	
	private static void createMapElementNode(Document doc, Node parentNode, Map<String, String> map) {
		if (map == null)
			return;
		for (Map.Entry<String, String> entry: map.entrySet()) {
			Element node = createDocElement(doc, entry.getKey());
			node.setTextContent(entry.getValue());
			parentNode.appendChild(node);
		}
	}
	
	private static void createMapElementAttributes(Document doc, Element element, Map<String, String> map) {
		if (map == null)
			return;
		for (Map.Entry<String, String> entry: map.entrySet()) {
			element.setAttribute(entry.getKey(), entry.getValue());
		}
	}
}

