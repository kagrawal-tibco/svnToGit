package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustActionColumnName;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustColumnName;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.ColumnType;
import com.tibco.cep.webstudio.client.decisiontable.model.Domain;
import com.tibco.cep.webstudio.client.decisiontable.model.DomainEntry;
import com.tibco.cep.webstudio.client.decisiontable.model.MetaData;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
import com.tibco.cep.webstudio.client.decisiontable.model.ParentArgumentResource;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;


/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableModelLoader {
	
	public static void load(Element docElement, Table table, boolean isNew, String implementsPath, String folder, boolean isDecisionTablePage) {
		if (isNew) {
			Node artifactNode = docElement.getElementsByTagName("artifactDetails").item(0);
			NodeList artifactChildNodes = artifactNode.getChildNodes();
			String name = null, projectName = null;
			for (int i = 0; i < artifactChildNodes.getLength(); i++) {
				Node artifactChildNode = artifactChildNodes.item(i);
				if (artifactChildNode.getNodeType() == Node.ELEMENT_NODE && artifactChildNode.getFirstChild() != null) {
					if (artifactChildNode.getNodeName().equalsIgnoreCase("name")) {
						name = artifactChildNode.getFirstChild().getNodeValue();
					} else if (artifactChildNode.getNodeName().equalsIgnoreCase("folder")) {
						if (folder == null) {
							folder = artifactChildNode.getFirstChild().getNodeValue();
						}	
					} else if (artifactChildNode.getNodeName().equalsIgnoreCase("projectName")) {
						projectName = artifactChildNode.getFirstChild().getNodeValue();
					}
				}	
			}
						
			table.setImplements(implementsPath);
			table.setFolder(folder);
			table.setProjectName(projectName);
			List<ArgumentData> argumentList = new ArrayList<ArgumentData>(); 
			for (int i = 0; i < artifactChildNodes.getLength(); i++) {
				Node artifactChildNode = artifactChildNodes.item(i);
				if (artifactChildNode.getNodeType() == Node.ELEMENT_NODE && artifactChildNode.getNodeName().equalsIgnoreCase("symbol")) {
					Node symbolNode = artifactChildNode;
					NodeList symbolChildNodes = symbolNode.getChildNodes();
					String alias = null, type = null, typeExtn = null, array = null;
					for (int j = 0; j < symbolChildNodes.getLength(); j++) {
						Node symbolChildNode = symbolChildNodes.item(j);
						if (symbolChildNode.getNodeType() == Node.ELEMENT_NODE && symbolChildNode.getFirstChild() != null) {
							if (symbolChildNode.getNodeName().equalsIgnoreCase("argumentAlias")) {
								alias = symbolChildNode.getFirstChild().getNodeValue();
							} else if (symbolChildNode.getNodeName().equalsIgnoreCase("path")) {
								type = symbolChildNode.getFirstChild().getNodeValue();
							} else if (symbolChildNode.getNodeName().equalsIgnoreCase("resourceType")) {
								typeExtn = symbolChildNode.getFirstChild().getNodeValue();
							} else if (symbolChildNode.getNodeName().equalsIgnoreCase("isArray")) {
								array = symbolChildNode.getFirstChild().getNodeValue();
							}							
						}
					}
					argumentList.add(new ArgumentData(type, alias, typeExtn, "BOTH", array));
				}
			}			
			table.setArguments(argumentList);
			
			List<MetaData> tableMetaDataList = new ArrayList<MetaData>();
			MetaData effMD = new MetaData("EffectiveDate", "DateTime", "");
			MetaData expMD = new MetaData("ExpiryDate", "DateTime", "");
			MetaData singleRowMD = new MetaData("SingleRowExecution", "Boolean", "false");
			MetaData priorityMD = new MetaData("Priority", "Integer", "5");
			
			tableMetaDataList.add(effMD);
			tableMetaDataList.add(expMD);
			tableMetaDataList.add(singleRowMD);
			tableMetaDataList.add(priorityMD);
			
			table.setMetaData(tableMetaDataList);
						
		} else {
			load(docElement, table, isDecisionTablePage);
		}
	}
	
	
	public static Domain getDomain(Element docElement) {
		Node rootNode = docElement.getElementsByTagName("domain").item(0);
		if (rootNode == null) {
			return null;
		}
		Domain domain = new Domain();
		NodeList entries = rootNode.getOwnerDocument().getElementsByTagName("entries");
		for (int i = 0 ; i < entries.getLength() ;  i++) {
			Node entry = entries.item(i);
			String type = entry.getAttributes().getNamedItem("type").getNodeValue();
			boolean single  = false;
			if (type.equalsIgnoreCase("Single")) {
				single = true;
			}
			String value = entry.getAttributes().getNamedItem("value").getNodeValue();
			String desc = entry.getAttributes().getNamedItem("description").getNodeValue();
			DomainEntry domainEntry = new DomainEntry(value, desc);
			if (!single) {
				//TODO for range values
			}
			domain.getEntries().add(domainEntry);
		}
		return domain;
	}
	
	/**
	 * @param docElement
	 * @param parent
	 * @return
	 */
	public static List<ArgumentResource> getArgumentProperties(Node argumentNode, ParentArgumentResource parent) {
		List<ArgumentResource> list =  new ArrayList<ArgumentResource>();
		NodeList argumentChildNodes = argumentNode.getChildNodes();
		for (int i = 0; i < argumentChildNodes.getLength(); i++) {
			Node argumentChildNode = argumentChildNodes.item(i);
			if (argumentChildNode.getNodeType() == Node.ELEMENT_NODE && argumentChildNode.getFirstChild() != null) {
				if (argumentChildNode.getNodeName().equalsIgnoreCase("property")) {
					Node propertyNode = argumentChildNode;
					String name = "", type = null, ownerPath = null, conceptTypePath = null;
					boolean isArray = false, hasAssociatedDomain = false;
					NodeList propertyChildNodes = propertyNode.getChildNodes();
					for (int j = 0; j < propertyChildNodes.getLength(); j++ ) {
						Node propertyChildNode = propertyChildNodes.item(j);
						if (propertyChildNode.getNodeType() == Node.ELEMENT_NODE && propertyChildNode.getFirstChild() != null) {
							if (propertyChildNode.getNodeName().equalsIgnoreCase("name")) {
								name = propertyChildNode.getFirstChild().getNodeValue();
							} else if (propertyChildNode.getNodeName().equalsIgnoreCase("type")) {
								type = propertyChildNode.getFirstChild().getNodeValue();
							} else if (propertyChildNode.getNodeName().equalsIgnoreCase("ownerPath")) {
								ownerPath = propertyChildNode.getFirstChild().getNodeValue();
							} else if (propertyChildNode.getNodeName().equalsIgnoreCase("isArray")) {
								isArray = Boolean.parseBoolean(propertyChildNode.getFirstChild().getNodeValue());
							} else if (propertyChildNode.getNodeName().equalsIgnoreCase("associatedDomain")) {
								hasAssociatedDomain = Boolean.parseBoolean(propertyChildNode.getFirstChild().getNodeValue());
							} else if (propertyChildNode.getNodeName().equalsIgnoreCase("conceptTypePath")) {
								conceptTypePath = propertyChildNode.getFirstChild().getNodeValue();
							}
						}
					}
					ArgumentResource resource = null;
					if (PROPERTY_TYPES.get(type) == PROPERTY_TYPES.CONCEPT || PROPERTY_TYPES.get(type) == PROPERTY_TYPES.CONCEPT_REFERENCE) {
						resource = new ParentArgumentResource(name, ownerPath, type);
						resource.setConceptTypePath(conceptTypePath);
					} else {
						resource = new ArgumentResource(name, type);
					}
					resource.setArray(isArray);
					resource.setOwnerPath(ownerPath);
					resource.setParent(parent);
					resource.setAssociatedDomain(hasAssociatedDomain);
					parent.getChildren().add(resource);
					list.add(resource);
				}
			}	
		}		
		return list;
	}

	public static List<ArgumentResource> getArguments(Element docElement) {
		List<ArgumentResource> list =  new ArrayList<ArgumentResource>();
		NodeList argumentNodeList = docElement.getElementsByTagName("argument");
		if (argumentNodeList != null) {
			for (int i = 0 ; i < argumentNodeList.getLength() ;  i++) {
				String argumentType = null;
				Node argumentNode = argumentNodeList.item(i);
				NodeList argumentChildNodes = argumentNode.getChildNodes();
				for (int j = 0; j < argumentChildNodes.getLength(); j++) {
					Node argumentChildNode = argumentChildNodes.item(j);
					if (argumentChildNode.getNodeType() == Node.ELEMENT_NODE && 
							argumentChildNode.getNodeName().equalsIgnoreCase("resourceType") && argumentChildNode.getFirstChild() != null) {
						argumentType = argumentChildNode.getFirstChild().getNodeValue();
					}
				}
				
				if ("CONCEPT".equalsIgnoreCase(argumentType) || "EVENT".equalsIgnoreCase(argumentType)) {
					String name = "", alias = "", direction = "", folder = "";
					boolean isArray = false;
					for (int j = 0; j < argumentChildNodes.getLength(); j++) {
						Node argumentChildNode = argumentChildNodes.item(j);
						if (argumentChildNode.getNodeType() == Node.ELEMENT_NODE && argumentChildNode.getFirstChild() != null) {
							if (argumentChildNode.getNodeName().equalsIgnoreCase("direction")) {
								direction = argumentChildNode.getFirstChild().getNodeValue();
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("path")) {
								folder = "/";
								name = argumentChildNode.getFirstChild().getNodeValue();
								int fileSeparatorIndx = name.lastIndexOf("/");
								if (fileSeparatorIndx > -1) {
									folder = name.substring(0, fileSeparatorIndx + 1);
									name = name.substring(fileSeparatorIndx + 1);
								}
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("argumentAlias")) {
								alias = argumentChildNode.getFirstChild().getNodeValue();
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("isArray")) {
								isArray = Boolean.parseBoolean(argumentChildNode.getFirstChild().getNodeValue());
							}
						}	
					}
					ParentArgumentResource parent = new ParentArgumentResource(name, folder, argumentType);
					parent.setAlias(alias);
					parent.setArray(isArray);
					getArgumentProperties(argumentNode, parent);					
					list.add(parent);
				} else if ("PRIMITIVE".equalsIgnoreCase(argumentType)) {
					String type = "", alias = "", direction = "";
					boolean isArray = false;
					for (int j = 0; j < argumentChildNodes.getLength(); j++) {
						Node argumentChildNode = argumentChildNodes.item(j);
						if (argumentChildNode.getNodeType() == Node.ELEMENT_NODE && argumentChildNode.getFirstChild() != null) {
							if (argumentChildNode.getNodeName().equalsIgnoreCase("direction")) {
								direction = argumentChildNode.getFirstChild().getNodeValue();
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("path")) {
								type = argumentChildNode.getFirstChild().getNodeValue();
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("argumentAlias")) {
								alias = argumentChildNode.getFirstChild().getNodeValue();
							} else if (argumentChildNode.getNodeName().equalsIgnoreCase("isArray")) {
								isArray = Boolean.parseBoolean(argumentChildNode.getFirstChild().getNodeValue());
							}
						}	
					}
					ArgumentResource resource = new ArgumentResource(alias, type);
					resource.setPrimitive(true);
					resource.setArray(isArray);
					list.add(resource);					
				}				
			}
		}						
		return list;
	}
	
	
	public static void load(Element docElement, Table table, boolean isDecisionTablePage) {
		Node rootNode = docElement.getElementsByTagName("artifactDetails").item(0);
		if (rootNode == null) {
			return;
		}		
	
		String tableName = docElement.getElementsByTagName("tableName").item(0).getFirstChild().getNodeValue();
		String projectName = docElement.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
		String folder = docElement.getElementsByTagName("folderPath").item(0).getFirstChild().getNodeValue();
		String rfimplements = docElement.getElementsByTagName("implementsPath").item(0).getFirstChild().getNodeValue();
		
		table.setName(tableName);
		table.setProjectName(projectName);
		table.setFolder(folder);
		table.setImplements(rfimplements);

		//Get Decision Table Page Rules		
		TableRuleSet dtRuleSet = getRuleSet(table, rootNode, "decisionTable", Table.DECISION_TABLE);
		if (dtRuleSet != null) {
			table.setDecisionTable(dtRuleSet);
		}
		
		if (!isDecisionTablePage) {	
			//Get Meta Data for Table
			table.setMetaData(getMetaData(rootNode));
			//Get Arguments
			table.setArguments(getArguments(rootNode));
			//Get Exception Table Rules			
			TableRuleSet expRuleSet = getRuleSet(table, rootNode, "exceptionTable", Table.EXCEPTION_TABLE);
			if (expRuleSet != null) {
				table.setExceptionTable(expRuleSet);
			}
		}	
	}

	public static void load(Node rootNode, Table table, boolean isDecisionTablePage) {
		if (rootNode == null) {
			return;
		}		
	
		String tableName = getChildNodeTextValue(rootNode, "tableName");
		String projectName = getChildNodeTextValue(rootNode, "projectName");
		String folder = getChildNodeTextValue(rootNode, "folderPath");
		String rfimplements = getChildNodeTextValue(rootNode, "implementsPath");
		
		table.setName(tableName);
		table.setProjectName(projectName);
		table.setFolder(folder);
		table.setImplements(rfimplements);

		//Get Decision Table Page Rules		
		TableRuleSet dtRuleSet = getRuleSet(table, rootNode, "decisionTable", Table.DECISION_TABLE);
		if (dtRuleSet != null) {
			table.setDecisionTable(dtRuleSet);
		}
		
		if (!isDecisionTablePage) {	
			//Get Meta Data for Table
			table.setMetaData(getMetaData(rootNode));
			//Get Arguments
			table.setArguments(getArguments(rootNode));
			//Get Exception Table Rules			
			TableRuleSet expRuleSet = getRuleSet(table, rootNode, "exceptionTable", Table.EXCEPTION_TABLE);
			if (expRuleSet != null) {
				table.setExceptionTable(expRuleSet);
			}
		}	
	}
	
	private static String getChildNodeTextValue(Node parent, String tagName) {
		String nodeValue = null;
		List<Node> nodeList = getChildNodesByName(parent, tagName);
		if (!nodeList.isEmpty() && nodeList.get(0).getFirstChild() != null) {
			nodeValue = nodeList.get(0).getFirstChild().getNodeValue();
		}
		return nodeValue;			
	}

	private static List<Node> getChildNodesByName(Node parent, String tagName) {
		List<Node> nodeList = new ArrayList<Node>();
		for (int j = 0; j < parent.getChildNodes().getLength(); j++) {								
			Node childNode = parent.getChildNodes().item(j);
			if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(tagName)) {
				nodeList.add(childNode);
			}
		}
		return nodeList;
	}
	
	private static Node getChildNodeByName(Node parent, String tagName) {
		Node childNode = null;
		List<Node> nodeList = getChildNodesByName(parent, tagName);
		if (!nodeList.isEmpty())
			childNode = nodeList.get(0);
		return childNode;
	}
	
	/**
	 * Fetching Arguments for the Declarations for the given node
	 * @param rootNode
	 * @return
	 */
	private static List<ArgumentData> getArguments(Node rootNode) {
		List<ArgumentData> argumentList = new ArrayList<ArgumentData>(); 
		Node argumentsNode = getChildNodeByName(rootNode, "arguments");
		NodeList argNodeList = argumentsNode.getChildNodes();
		for (int i = 0 ; i < argNodeList.getLength() ;  i++) {
			Node argumentNode = argNodeList.item(i);			
			if (argumentNode.getNodeName().equals("argument")) {
				NodeList propNodeList = argumentNode.getChildNodes();			
				String direction = "BOTH", propertyPath = null, resourceType = null, alias = null, isArray = null;
				for (int k = 0 ; k < propNodeList.getLength() ; k++) {
					Node propNode = propNodeList.item(k);
					if (propNode.getNodeType() == Node.ELEMENT_NODE && propNode.getFirstChild() != null) {
						if (propNode.getNodeName().equals("direction")) {
							direction = propNode.getFirstChild().getNodeValue();
						} else if (propNode.getNodeName().equals("path")) {
							propertyPath = propNode.getFirstChild().getNodeValue();
						} else if (propNode.getNodeName().equals("argumentAlias")) {
							alias = propNode.getFirstChild().getNodeValue();
						} else if (propNode.getNodeName().equals("resourceType")) {
							resourceType = propNode.getFirstChild().getNodeValue();
						} else if (propNode.getNodeName().equals("isArray")) {
							isArray = propNode.getFirstChild().getNodeValue();
						}
					}	
				}
				argumentList.add(new ArgumentData(propertyPath, alias, resourceType, direction, isArray));
			}	
		}
		return argumentList;
	}
	
	/**
	 * Fetching Decision/Exception Table Node, and corresponding columns and rules
	 * @param rootNode
	 * @param tag
	 * @param type
	 * @return
	 */
	private static TableRuleSet getRuleSet(Table table, Node rootNode, String tag, String type) {
		TableRuleSet dtruleset = new TableRuleSet(table, type);
		Node pagesNode = getChildNodeByName(rootNode, tag + "TotalPages");
		if (pagesNode != null && pagesNode.getFirstChild() != null) {
			int pages = Integer.parseInt(pagesNode.getFirstChild().getNodeValue());
			dtruleset.setPages(pages);
		}
		
		Node tablePageNode = getChildNodeByName(rootNode, tag + "Page");
		if (tablePageNode == null || tablePageNode.getChildNodes() == null) {
			return dtruleset;
		}

		Node tablelastRuleIdNode = getChildNodeByName(rootNode, tag + "LastRuleId");
		if (tablelastRuleIdNode != null && tablelastRuleIdNode.getFirstChild() != null) {
			int lastRuleId = Integer.parseInt(tablelastRuleIdNode.getFirstChild().getNodeValue());
			dtruleset.setLastRow(lastRuleId);
		}

		Node singlePageViewNode = getChildNodeByName(rootNode, tag + "SinglePageView");
		if (singlePageViewNode != null && singlePageViewNode.getFirstChild() != null) {
			boolean showAll = Boolean.parseBoolean(singlePageViewNode.getFirstChild().getNodeValue());
			dtruleset.setShowAll(showAll);
		}

		List<TableColumn> decisionTableColumnList =  new ArrayList<TableColumn>();
		Node tableColumnsNode = getChildNodeByName(rootNode, tag + "Columns");
		NodeList columnsNodeList = tableColumnsNode.getChildNodes();
		for (int i = 0; i < columnsNodeList.getLength(); i++) {
			Node columnNode = columnsNodeList.item(i);
			if (columnNode.getNodeName().equals("column")) {
				NodeList propertyNodeList = columnNode.getChildNodes();
				String columnId = null, name = null, propertyPath = "", propertyType = "0", columnType = null, columnAlias = null, defaultCellText = null;
				boolean associatedDM = false, isSubstitution = false, isArrayProperty = false;
				for (int j = 0; j < propertyNodeList.getLength(); j++) {
					Node propertyNode = propertyNodeList.item(j);
					if (propertyNode.getNodeType() == Node.ELEMENT_NODE && propertyNode.getFirstChild() != null) {
						if (propertyNode.getNodeName().equals("columnId")) {
							columnId = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("name")) {
							name = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("property")) {
							propertyPath = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("columnType")) {
							columnType = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("columnAlias")) {
							columnAlias = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("propertyType")) {
							propertyType = propertyNode.getFirstChild().getNodeValue();
						} else if (propertyNode.getNodeName().equals("isArrayProperty")) {
							isArrayProperty = Boolean.parseBoolean(propertyNode.getFirstChild().getNodeValue());
						} else if (propertyNode.getNodeName().equals("associatedDM")) {
							associatedDM = Boolean.parseBoolean(propertyNode.getFirstChild().getNodeValue());
						} else if (propertyNode.getNodeName().equals("isSubstitution")) {
							isSubstitution = Boolean.parseBoolean(propertyNode.getFirstChild().getNodeValue());
						} else if (propertyNode.getNodeName().equals("defaultCellText")) {
							defaultCellText = propertyNode.getFirstChild().getNodeValue();
						}
					}	
				}	
				name = adjustColumnName(name, false);
				if (columnType.trim().equals(ColumnType.ACTION.getName().trim())) {
					name = adjustActionColumnName(name);
				}
				String escapedColumnName = name;
				if (isSubstitution) {
					escapedColumnName = DecisionTableUtils.escapeColumnSubstitutionExpression(escapedColumnName);
				}
				// Fix for boolean array field
				decisionTableColumnList.add(new TableColumn(dtruleset, columnId, escapedColumnName, propertyPath, propertyType, columnType, columnAlias, associatedDM, isSubstitution, isArrayProperty, defaultCellText));
			}
		}
		
		dtruleset.setColumns(decisionTableColumnList);

		List<TableRule> tableRules = new ArrayList<TableRule>();
		NodeList pageChildNodes = tablePageNode.getChildNodes();
		if (pageChildNodes != null) {
			for (int i = 0; i < pageChildNodes.getLength() ; i++) {
				Node pageChildNode = pageChildNodes.item(i);
				
				if (pageChildNode.getNodeName().equals("pageNumber") && pageChildNode.getFirstChild() != null) {
					int pageNum = Integer.parseInt(pageChildNode.getFirstChild().getNodeValue());
					dtruleset.setCurrentPage(pageNum);
					continue;
				} else if (pageChildNode.getNodeName().equals("tableRule") || pageChildNode.getNodeName().equals("newTableRule")) {			
					Node ruleNode = pageChildNode;				
					NodeList ruleChildNodes = ruleNode.getChildNodes();

					String ruleId = null;
					TableRule tableRule = null;
					List<TableRuleVariable> condVars = new ArrayList<TableRuleVariable>();
					List<TableRuleVariable> actVars = new ArrayList<TableRuleVariable>();
					for (int j = 0; j < ruleChildNodes.getLength(); j++) {								
						Node ruleChildNode = ruleChildNodes.item(j);
						if (ruleChildNode.getNodeType() == Node.ELEMENT_NODE && ruleChildNode.getFirstChild() != null) {
							if (ruleChildNode.getNodeName().equals("ruleId")) {
								ruleId = ruleChildNode.getFirstChild().getNodeValue();
								tableRule = new TableRule(ruleId);
								if (ruleNode.getNodeName().equals("newTableRule")) {
									tableRule.setNewRule(true);
								}
								break;
							}
						}	
					}
					if (tableRule != null) {
						for (int j = 0; j < ruleChildNodes.getLength(); j++) {								
							Node ruleChildNode = ruleChildNodes.item(j);
							if (ruleChildNode.getNodeType() == Node.ELEMENT_NODE) {
								//Add Meta Data for Table Rule
								if (ruleChildNode.getNodeName().equals("metadata")) {
									tableRule.setMetaData(getMetaDataProperties(ruleChildNode));							
								} else if (ruleChildNode.getNodeName().equals("condition")) {
									condVars.add(getTableRuleVariable(ruleChildNode, tableRule));	
								} else if (ruleChildNode.getNodeName().equals("action")) {
									actVars.add(getTableRuleVariable(ruleChildNode, tableRule));
								}
							}	
						}					
						tableRule.setConditions(condVars);
						tableRule.setActions(actVars);
						tableRules.add(tableRule);
					}
				}	
			}
		}
		dtruleset.setTableRules(tableRules);		
		return dtruleset;
	}
	
	private static TableRuleVariable getTableRuleVariable(Node ruleVariableNode, TableRule tableRule) {
		String condId = null, columnId = null, expression = null, displayValue = "", comment = "";
		boolean enabled = true;
		NodeList ruleVariableChildNodes = ruleVariableNode.getChildNodes();
		for (int k = 0; k < ruleVariableChildNodes.getLength(); k++) {
			Node ruleVariableChildNode = ruleVariableChildNodes.item(k);
			if (ruleVariableChildNode.getNodeType() == Node.ELEMENT_NODE && ruleVariableChildNode.getFirstChild() != null) {
				if (ruleVariableChildNode.getNodeName().equals("varId")) {
					condId = ruleVariableChildNode.getFirstChild().getNodeValue();
				} else if (ruleVariableChildNode.getNodeName().equals("columnId")) {
					columnId = ruleVariableChildNode.getFirstChild().getNodeValue();
				} else if (ruleVariableChildNode.getNodeName().equals("expression")) {
					expression = ruleVariableChildNode.getFirstChild().getNodeValue();
				} else if (ruleVariableChildNode.getNodeName().equals("displayValue")) {
					displayValue = ruleVariableChildNode.getFirstChild().getNodeValue();
				} else if (ruleVariableChildNode.getNodeName().equals("isEnabled")) {
					enabled  = Boolean.parseBoolean(ruleVariableChildNode.getFirstChild().getNodeValue());
				} else if (ruleVariableChildNode.getNodeName().equals("comment")) {
					comment = ruleVariableChildNode.getFirstChild().getNodeValue();
				}
			}	
		}
		if (expression == null) {
			expression = "";
		}
		if (displayValue == null) {
			displayValue = "";
		}
		TableRuleVariable trv = new TableRuleVariable(condId, columnId, expression, enabled, comment, tableRule);
		return trv;
	}
	
	/**
	 * Get Meta Data for the given Node
	 * @param rootNode
	 * @return
	 */
	private static List<MetaData> getMetaData(Node rootNode) {
		List<MetaData> tableMetaDataList = new ArrayList<MetaData>();
		NodeList tableNodeList = rootNode.getChildNodes();
		for (int i = 0; i< tableNodeList.getLength();  i++) {
			Node node = tableNodeList.item(i);
			if (node.getNodeName().equals("metadata")) {
				tableMetaDataList = getMetaDataProperties(node);
				break;
			}
		}
		return tableMetaDataList;
	}			


	private static List<MetaData> getMetaDataProperties(Node metadataNode) {
		List<MetaData> tableMetaDataList = new ArrayList<MetaData>();
		NodeList propmd = metadataNode.getChildNodes();
		for (int j = 0; j < propmd.getLength();  j++) {
			Node propMdNode = propmd.item(j);
			if (propMdNode.getNodeName().equals("property")) {						
				NodeList propNodeList = propMdNode.getChildNodes();							
				String name = "", type = "", value = "";
				for (int k = 0; k < propNodeList.getLength();  k++) {	
					Node propNode = propNodeList.item(k);
					if (propNode.getNodeType() == Node.ELEMENT_NODE && propNode.getFirstChild() != null) {
						if (propNode.getNodeName().equals("name"))
							name = propNode.getFirstChild().getNodeValue();
						else if (propNode.getNodeName().equals("type"))
							type = propNode.getFirstChild().getNodeValue();
						else if (propNode.getNodeName().equals("value"))
							value = propNode.getFirstChild().getNodeValue();
					}
				}	
				tableMetaDataList.add(new MetaData(name, type, value));
			}
		}
		return tableMetaDataList;
	}			
	
}