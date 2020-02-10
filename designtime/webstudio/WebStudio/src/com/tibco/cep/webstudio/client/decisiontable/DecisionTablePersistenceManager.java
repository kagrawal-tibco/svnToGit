package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustColumnName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.MetaData;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.decisiontable.model.TableColumn;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRule;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleSet;
import com.tibco.cep.webstudio.client.decisiontable.model.TableRuleVariable;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTablePersistenceManager {

	private static Document document;
	
	private static DecisionTablePersistenceManager manager;
	
	private static Node persistedRootNode;
	
	private DecisionTablePersistenceManager() {
		
	}
	
	/**
	 * @param rootDocument
	 * @param table
	 * @param decisionTableChangeModel
	 * @param incremental
	 * @return
	 */
	public static DecisionTablePersistenceManager getInstance(Document rootDocument, Node rootNode, Table table, 
																		DecisionTableUpdateProvider decisionTableChangeModel, boolean incremental, boolean isSyncMerge) {
		if (manager == null) {
			manager  = new DecisionTablePersistenceManager();
		}
		document =  rootDocument;
		persistedRootNode = rootNode;
		if (incremental) {
			//For Delta one
			processSaveModel(decisionTableChangeModel);
		} else {
			//For full one
			processSaveModel(table, isSyncMerge);
		}

		return manager;
	}

//--------------------------------------------------------FULL SAVE----------------------------------------------------------------------------------------	
	/**
	 * @param table
	 */
	public static void processSaveModel(Table table, boolean isSyncMerge) {
		//Table level
		Node artifactItemElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		persistedRootNode.appendChild(artifactItemElement);
		
		String decisionTablePath = table.getFolder() + table.getName();
		
		int extnIndex = decisionTablePath.lastIndexOf('.');
		if (extnIndex != -1) {
			decisionTablePath = decisionTablePath.substring(0, extnIndex);
		}
		
		Node artifactNameElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactNameText = document.createTextNode(decisionTablePath);
		artifactNameElement.appendChild(artifactNameText);
		artifactItemElement.appendChild(artifactNameElement);
		
		Node artifactTypeElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_TYPE_ELEMENT);
		Text artifactTypeText = document.createTextNode("rulefunctionimpl");
		artifactTypeElement.appendChild(artifactTypeText);
		artifactItemElement.appendChild(artifactTypeElement);
		
		Node artifactExtnElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_FILE_EXTN_ELEMENT);
		Text artifactExtnText = document.createTextNode("rulefunctionimpl");
		artifactExtnElement.appendChild(artifactExtnText);
		artifactItemElement.appendChild(artifactExtnElement);

		Node artifactImplsPathElement = document.createElement(XMLRequestBuilderConstants.RTI_IMPLEMENTS_PATH_ELEMENT);
		Text artifactImplsPathText = document.createTextNode(table.getImplements());
		artifactImplsPathElement.appendChild(artifactImplsPathText);
		artifactItemElement.appendChild(artifactImplsPathElement);

		Node isSyncMergeElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_IS_SYNC_MERGE);
		Text isSyncMergeText = document.createTextNode(String.valueOf(isSyncMerge));
		isSyncMergeElement.appendChild(isSyncMergeText);
		artifactItemElement.appendChild(isSyncMergeElement);

		Element artifactContentDataElement = document.createElement(XMLRequestBuilderConstants.DT_PAGES_SAVE_ELEMENT);
		//table metadata
		appendMetadata(artifactContentDataElement, table.getMetaData());
		//arguments
		appendArguments(artifactContentDataElement, table.getArguments());
		//Decision Table
		appendTableRuleSet(artifactContentDataElement, table.getDecisionTable(), "decisionTable");
		//Exception Table
		appendTableRuleSet(artifactContentDataElement, table.getExceptionTable(), "exceptionTable");
		
		Text artifactContentsText = document.createTextNode(artifactContentDataElement.toString());

		Element artifactContentElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_CONTENTS_ELEMENT);
		artifactContentElement.appendChild(artifactContentsText);
		artifactItemElement.appendChild(artifactContentElement);
	}
	
	/**
	 * @param parentElement
	 * @param list
	 */
	private static void appendArguments(Element parentElement, List<ArgumentData> argList) {
		Element argsElement = document.createElement("arguments");
		if (argList != null) {
			for (ArgumentData argData : argList) {
				Element argElement = document.createElement("argument");
	
				if (argData.getDirection() != null) {
					Element dirElement = document.createElement("direction");
					Text dirText = document.createTextNode(argData.getDirection());
					dirElement.appendChild(dirText);
					argElement.appendChild(dirElement);
				}
				
				Element pathElement = document.createElement("path");
				Text pathText = document.createTextNode(argData.getPropertyPath());
				pathElement.appendChild(pathText);
				argElement.appendChild(pathElement);
	
				Element aliasElement = document.createElement("argumentAlias");
				Text aliasText = document.createTextNode(argData.getAlias());
				aliasElement.appendChild(aliasText);
				argElement.appendChild(aliasElement);
	
				if (argData.getResourceType() != null) {
					Element resourceTypeElement = document.createElement("resourceType");
					Text resourceTypeText = document.createTextNode(argData.getResourceType());
					resourceTypeElement.appendChild(resourceTypeText);
					argElement.appendChild(resourceTypeElement);
				}

				if (argData.getArray() != null) {
					Element arrayElement = document.createElement("isArray");
					Text arrayText = document.createTextNode(argData.getArray());
					arrayElement.appendChild(arrayText);
					argElement.appendChild(arrayElement);
				}

				argsElement.appendChild(argElement);			
			}
		}	
		parentElement.appendChild(argsElement);		
	}
	
	/**
	 * @param parentElement
	 * @param ruleset
	 * @param tag
	 */
	private static void appendTableRuleSet(Element parentElement, TableRuleSet ruleset, String tag) {
		Element ruleSetElement = document.createElement(tag + "Page");
		if (ruleset != null) {
			Element pageNumElement = document.createElement("pageNumber");
			Text pageNumText = document.createTextNode(String.valueOf(ruleset.getCurrentPage()));
			pageNumElement.appendChild(pageNumText);
			ruleSetElement.appendChild(pageNumElement);
			
			if (ruleset.getTableRules() != null) {	
				for (TableRule rule : ruleset.getTableRules()) {
					Element ruleElement = null;
					if (rule.isNewRule()) {
						ruleElement = document.createElement("newTableRule");
					} else {
						ruleElement = document.createElement("tableRule");
					}
					
					Element ruleIdElement = document.createElement("ruleId");
					Text ruleIdText = document.createTextNode(rule.getId());
					ruleIdElement.appendChild(ruleIdText);
					ruleElement.appendChild(ruleIdElement);

					//rule metadata
					appendMetadata(ruleElement, rule.getMetaData());

					for (TableRuleVariable ruleVar : rule.getConditions()) {
						appendTableRuleVariable(ruleElement, rule, ruleVar, "condition");
					}
					for (TableRuleVariable ruleVar : rule.getActions()) {
						appendTableRuleVariable(ruleElement, rule, ruleVar, "action");
					}
					ruleSetElement.appendChild(ruleElement);
				}
			}
			if (ruleset.getColumns() != null) {
				//for columns
				Element colsElement = document.createElement(tag + "Columns");
				for (TableColumn column : ruleset.getColumns()) {
					Element colElement = document.createElement("column");

					Element columnIdElement = document.createElement("columnId");
					Text columnIdText = document.createTextNode(column.getId());
					columnIdElement.appendChild(columnIdText);
					colElement.appendChild(columnIdElement);
					
					String colName = adjustColumnName(column.getName(), true);
					Element nameElement = document.createElement("name");
					if (column.isSubstitution()) {
						colName = DecisionTableUtils.unEscapeColumnSubstitutionExpression(colName);
					}
					Text nameText = document.createTextNode(colName);
					nameElement.appendChild(nameText);
					colElement.appendChild(nameElement);

					if (column.getPropertyPath() != null) {	
						Element propertyElement = document.createElement("property");
						Text propertyText = document.createTextNode(column.getPropertyPath());
						propertyElement.appendChild(propertyText);
						colElement.appendChild(propertyElement);
					}
					
					Element columnTypeElement = document.createElement("columnType");
					Text columnTypeText = document.createTextNode(column.getColumnType());
					columnTypeElement.appendChild(columnTypeText);
					colElement.appendChild(columnTypeElement);

					if (column.getAlias() != null) {
						Element columnAliasElement = document.createElement("columnAlias");
						Text columnAliasText = document.createTextNode(column.getAlias());
						columnAliasElement.appendChild(columnAliasText);
						colElement.appendChild(columnAliasElement);					
					}
					
					Element isArrayPropertyElement = document.createElement("isArrayProperty");
					Text isArrayPropertyText = document.createTextNode(Boolean.toString(column.isArrayProperty()));
					isArrayPropertyElement.appendChild(isArrayPropertyText);
					colElement.appendChild(isArrayPropertyElement);					

					Element propertyTypeElement = document.createElement("propertyType");
					Text propertyTypeText = document.createTextNode(column.getPropertyType());
					propertyTypeElement.appendChild(propertyTypeText);
					colElement.appendChild(propertyTypeElement);					

					Element isSubstitutionElement = document.createElement("isSubstitution");
					Text isSubstitutionText = document.createTextNode(Boolean.toString(column.isSubstitution()));
					isSubstitutionElement.appendChild(isSubstitutionText);
					colElement.appendChild(isSubstitutionElement);					

					Element associatedDMElement = document.createElement("associatedDM");
					Text associatedDMText = document.createTextNode(String.valueOf(column.hasAssociatedDomain()));
					associatedDMElement.appendChild(associatedDMText);
					colElement.appendChild(associatedDMElement);
					
					Element defaultCellTextElement = document.createElement("defaultCellText");
					Text defaultCellTextText = document.createTextNode(String.valueOf(column.getDefaultCellText()));
					defaultCellTextElement.appendChild(defaultCellTextText);
					colElement.appendChild(defaultCellTextElement);
					
					colsElement.appendChild(colElement);
				}
				parentElement.appendChild(colsElement);
			}
		}
		parentElement.appendChild(ruleSetElement);
	}
	
	private static void appendTableRuleVariable(Element ruleElement, TableRule rule, TableRuleVariable ruleVar, String trvElementName) {
		Element trvElement = document.createElement(trvElementName);
		//ruleId
		Element ruleIdElement = document.createElement("ruleId");
		Text trvRuleIdText = document.createTextNode(rule.getId());
		ruleIdElement.appendChild(trvRuleIdText);
		trvElement.appendChild(ruleIdElement);
		//varId
		Element varIdElement = document.createElement("varId");
		Text varIdText = document.createTextNode(ruleVar.getId());
		varIdElement.appendChild(varIdText);
		trvElement.appendChild(varIdElement);
		//columnId
		Element columnIdElement = document.createElement("columnId");
		Text columnIdText = document.createTextNode(ruleVar.getColumnId());
		columnIdElement.appendChild(columnIdText);
		trvElement.appendChild(columnIdElement);
		//expression
		Element expressionElement = document.createElement("expression");
		Text expressionText = document.createTextNode(ruleVar.getExpression());
		expressionElement.appendChild(expressionText);
		trvElement.appendChild(expressionElement);

		if (!ruleVar.getComment().isEmpty()) {
			Element commentElement = document.createElement("comment");
			Text commentText = document.createTextNode(ruleVar.getComment());
			commentElement.appendChild(commentText);
			trvElement.appendChild(commentElement);
		}
//		if (!ruleVar.isEnabled()) {
			Element isEnabledElement = document.createElement("isEnabled");
			Text isEnabledText = document.createTextNode(Boolean.toString(ruleVar.isEnabled()));
			isEnabledElement.appendChild(isEnabledText);
			trvElement.appendChild(isEnabledElement);
//		}
		ruleElement.appendChild(trvElement);		
	}
	
	/**
	 * @param parentElement
	 * @param list
	 */
	private static void appendMetadata(Element parentElement, List<MetaData> list) {
		Element mdtElement = document.createElement("metadata");
		if (list != null) {
			for (MetaData md: list) {
				Element propElement = document.createElement("property");

				Element nameElement = document.createElement("name");
				Text nameText = document.createTextNode(md.getName());
				nameElement.appendChild(nameText);
				propElement.appendChild(nameElement);
				
				Element typeElement = document.createElement("type");
				Text typeText = document.createTextNode(md.getType());
				typeElement.appendChild(typeText);
				propElement.appendChild(typeElement);
				
				Element valElement = document.createElement("value");
				Text valText = document.createTextNode(md.getValue().toString());
				valElement.appendChild(valText);
				propElement.appendChild(valElement);
				
				mdtElement.appendChild(propElement);
			}
		}
		parentElement.appendChild(mdtElement);
	}

	/**
	 * @param parentElement
	 * @param list
	 */
	private static void appendRuleMetadata(Element parentElement, List<MetaData> list) {
		Element mdtElement = document.createElement("md");
		if (list != null) {
			for (MetaData md: list) {
				Element propElement = document.createElement("prop");
				propElement.setAttribute( "name", md.getName());
				propElement.setAttribute( "type", md.getType());
				propElement.setAttribute( "value", md.getValue().toString());
				mdtElement.appendChild(propElement);
			}
		}
		parentElement.appendChild(mdtElement);
	}
		
	//--------------------------------------------------------INCREMENTAL SAVE----------------------------------------------------------------------------------------
	
	/**
	 * @param decisionTableChangeModel
	 */
	public static void processSaveModel(DecisionTableUpdateProvider decisionTableChangeModel) {
		
		Map<String, TableRule> newRecords = decisionTableChangeModel.getNewRecords();

		Map<String, Set<String>> modifiedRecordColumns = decisionTableChangeModel.getModifiedRecordColumns();
		Map<String, TableRule> modifiedRecords = decisionTableChangeModel.getModifiedRecords();

		List<String> removedRecords = decisionTableChangeModel.getDeletedRecords();

		List<TableColumn> columns = decisionTableChangeModel.getTable().getDecisionTable().getColumns();
		
		Map<String, String> colIdNameMap = new HashMap<String, String>();
		
		Map<String, String> deletedColumns = decisionTableChangeModel.getDeletedColumns();
		List<TableColumn> newColumns = decisionTableChangeModel.getNewColumns();
		
		for (TableColumn column : columns) {
			colIdNameMap.put(column.getId(), column.getName());
		}
		
//		document = com.google.gwt.xml.client.XMLParser.createDocument();
		Element deltaElement = document.createElement("delta");
		document.appendChild(deltaElement);
		Element newElement = addCreatedElement(document, newRecords);
		deltaElement.appendChild(newElement);
		deltaElement.appendChild(addModifiedElement(document, colIdNameMap, modifiedRecordColumns, modifiedRecords, newRecords));
		
		Element deletedElement = addDeletedElement(document, removedRecords, modifiedRecords);
		deltaElement.appendChild(deletedElement);
		
		addUpdatedColumns(document, newElement, deletedElement, newColumns, deletedColumns);
		
		//Clear cache
		decisionTableChangeModel.getNewRecords().clear();
		decisionTableChangeModel.getModifiedRecordColumns().clear();
		decisionTableChangeModel.getModifiedRecords().clear();
		decisionTableChangeModel.getDeletedRecords().clear();
		decisionTableChangeModel.getNewColumns().clear();
		decisionTableChangeModel.getDeletedColumns().clear();
	}
	
	/**
	 * @param document
	 * @param newElementRoot
	 * @param deletedElementRoot
	 * @param newColumns
	 * @param deletedColumns
	 */
	private static void addUpdatedColumns(Document document, 
			                                   Element newElementRoot, 
			                                   Element deletedElementRoot, 
			                                   List<TableColumn> newColumns, 
			                                   Map<String, String> deletedColumns) {
		if (newColumns.size() > 0) {
			Element enewcolumns = document.createElement("columns");
			for (TableColumn col: newColumns) {
				Element enewcolumn = document.createElement("column");
				enewcolumn.setAttribute("id", col.getId());
				enewcolumn.setAttribute("name", col.getName());
				enewcolumn.setAttribute("columnType", col.getColumnType());
				enewcolumns.appendChild(enewcolumn);
			}
			newElementRoot.appendChild(enewcolumns);
		}
		if (deletedColumns.size() > 0) {
			Element edeletedcolumns = document.createElement("columns");
			for (String col: deletedColumns.keySet()) {
				Element edeletedcolumn = document.createElement("column");
				edeletedcolumn.setAttribute("id", col);
				edeletedcolumn.setAttribute("name", deletedColumns.get(col));
				edeletedcolumns.appendChild(edeletedcolumn);
			}
			deletedElementRoot.appendChild(edeletedcolumns);
		}
	}
	
	
	/**
	 * @param document
	 * @param newRecords
	 * @return
	 */
	private static Element addCreatedElement(Document document, Map<String, TableRule> newRecords) {
		Element createdElement = document.createElement("created");
		for (String ruleId : newRecords.keySet()) {
			TableRule trule = newRecords.get(ruleId);
			Element erule = document.createElement("rule");
			erule.setAttribute("id", ruleId);
		    for (TableRuleVariable ruleVar : trule.getConditions()) {
		    	Element econd = document.createElement("cond");
		    	econd.setAttribute("id", ruleVar.getId());
		    	econd.setAttribute("colId", ruleVar.getColumnId());
		    	econd.setAttribute("expr", ruleVar.getExpression());
		    	erule.appendChild(econd);
		    }
		    for (TableRuleVariable ruleVar : trule.getActions()) {
		    	Element econd = document.createElement("act");
		    	econd.setAttribute("id", ruleVar.getId());
		    	econd.setAttribute("colId", ruleVar.getColumnId());
		    	econd.setAttribute("expr", ruleVar.getExpression());
		    	erule.appendChild(econd);
		    }
		    createdElement.appendChild(erule);
		    if (trule.getMetaData() != null) {
		    	//TODO
		    }
		}
		return createdElement;
	}
	
	
	public Document getDocument() {
		return document;
	}

	
	/**
	 * @param document
	 * @param colIdNameMap
	 * @param modifiedRecordColumns
	 * @param modifiedRecords
	 * @param newRecords
	 * @return
	 */
	private static Element addModifiedElement(Document document, 
			                           Map<String, String> colIdNameMap,
			                           Map<String, Set<String>> modifiedRecordColumns, 
			                           Map<String, TableRule> modifiedRecords, 
			                           Map<String, TableRule> newRecords) {
		Element modifiedElement = document.createElement("modified");
		for (String ruleId : modifiedRecordColumns.keySet()) {
			if (newRecords.containsKey(ruleId)) {
				continue;
			}
			Set<String> cols = modifiedRecordColumns.get(ruleId);
			TableRule trule = modifiedRecords.get(ruleId);

			Element erule = document.createElement("rule");
			erule.setAttribute("id", ruleId);
		    for (TableRuleVariable ruleVar : trule.getConditions()) {
		    	Element econd = document.createElement("cond");
		    	String colid = ruleVar.getColumnId();
		    	String colName = colIdNameMap.get(colid);
		    	if (!cols.contains(colName)) {
		    		continue;
		    	}
		    	econd.setAttribute("id", ruleVar.getId());
		    	econd.setAttribute("colId", colid);
		    	econd.setAttribute("expr", ruleVar.getExpression());
		    	erule.appendChild(econd);
		    }
		    for (TableRuleVariable ruleVar : trule.getActions()) {
		    	Element eact = document.createElement("act");
		    	String colid = ruleVar.getColumnId();
		    	String colName = colIdNameMap.get(colid);
		    	if (!cols.contains(colName)) {
		    		continue;
		    	}
		    	eact.setAttribute("id", ruleVar.getId());
		    	eact.setAttribute("colId", colid);
		    	eact.setAttribute("expr", ruleVar.getExpression());
		    	erule.appendChild(eact);
		    }
		    modifiedElement.appendChild(erule);
		    if (trule.getMetaData() != null) {
		    	//TODO
		    }
		}
		return modifiedElement;
	}
	
	/**
	 * @param document
	 * @param removedRecords
	 * @param modifiedRecords
	 * @return
	 */
	private static Element addDeletedElement(Document document, 
									List<String> removedRecords, 
						            Map<String, TableRule> modifiedRecords 
						            ) {
		Element modifiedElement = document.createElement("deleted");
		for (String ruleId : removedRecords) {
			if (modifiedRecords.containsKey(ruleId)) {
				continue;
			}
			Element erule = document.createElement("rule");
			erule.setAttribute("id", ruleId);
			modifiedElement.appendChild(erule);
		}
		return modifiedElement;
	}
	
}
