package com.tibco.cep.webstudio.client.diff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;

/**
 * This class provides various helper methods required for diff functionality.
 * 
 * @author moshaikh
 * 
 */
public class DiffHelper {
	
	public static final String CURRENT_VERSION_TAG_NAME = "currentVersionContents";
	public static final String PREVIOUS_VERSION_TAG_NAME = "previousVersionContents";
	public static final String SERVER_VERSION_TAG_NAME = "serverContents";
	
	private static GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Enum denoting the DIFF processing type for DT - whether decisionTable or exceptionTable.
	 * @author moshaikh
	 *
	 */
	public static enum DTDiffType {
		DECISION_TABLE("decisionTable", "DT_"), EXCEPTION_TABLE("exceptionTable", "ET_");
		private String tagName;
		private String keyPrefix;
		private DTDiffType(String tagName, String keyPrefix) {
			this.tagName = tagName;
			this.keyPrefix = keyPrefix;
		}
		public String getTagName() {
			return this.tagName;
		}
		public String getKeyPrefix() {
			return this.keyPrefix;
		}
	}
	
	/**
	 * Processes diff between 2 versions of MultiFilter and also merges filters
	 * deleted from older version into newer version MultiFilter.
	 * 
	 * @param multiFilter_v1
	 * @param multiFilter_v2
	 */
	public static void processMultiFilterDiff(MultiFilter multiFilter_v1, MultiFilter multiFilter_v2,
			Map<String, ModificationEntry> modifications) {
		if (multiFilter_v2.getSubClause() != null
				&& multiFilter_v2.getSubClause().getFilters() != null) {
			processFiltersDiff(multiFilter_v1.getSubClause().getFilters(), multiFilter_v2.getSubClause().getFilters(), modifications);
		}
	}

	/**
	 * Process diff between the 2 versions of filter lists, also merges the
	 * deleted Filters into the new version(v2).<br/>
	 * This method currently assumes that all new Filters will always be added
	 * at the end of the respective list of filters.
	 * 
	 * @param filterList_v1
	 * @param filterList_v2
	 */
	/*public static void processFiltersDiff(List<Filter> filterList_v1, List<Filter> filterList_v2,
			Map<String, ModificationEntry> modifications) {
		/**
		 * Indexes of identified deleted filters (index in version1, index to be
		 * added to in version2).
		 */
	/*	Map<Integer, Integer> deletedFilterIndices = new LinkedHashMap<Integer, Integer>();
		int newPointer = 0;
		for (int i = 0; i < filterList_v1.size(); i++) {
			if (filterList_v1.get(i) != null && newPointer < filterList_v2.size()
					&& filterList_v2.get(newPointer) != null
					&& filterList_v1.get(i).getFilterId() != null && !"null".equals(filterList_v1.get(i).getFilterId())
					&& filterList_v1.get(i).getFilterId().equals(filterList_v2.get(newPointer).getFilterId())) {
				if (filterList_v1.get(i) instanceof MultiFilter
						&& filterList_v2.get(newPointer) instanceof MultiFilter) {// FilterId matched
					processMultiFilterDiff((MultiFilter) filterList_v1.get(i), (MultiFilter) filterList_v2.get(newPointer), modifications);
				} else {
					if (isFilterModified((SingleFilterImpl) filterList_v1.get(i), (SingleFilterImpl) filterList_v2.get(newPointer))) {// MODIFIED
						modifications.put(filterList_v2.get(newPointer).getFilterId(),
								new ModificationEntry(ModificationType.MODIFIED, filterList_v1.get(i), filterList_v2.get(newPointer)));
					}
				}
				newPointer++;
			} else {// FilterId didn't match, i.e DELETED from v1
				modifications.put(filterList_v1.get(i).getFilterId(), new ModificationEntry(ModificationType.DELETED));
				deletedFilterIndices.put(i, newPointer);
			}
		}
		// ADDED
		for (int i = newPointer; i < filterList_v2.size(); i++) {
			modifications.put(filterList_v2.get(i).getFilterId(), new ModificationEntry(ModificationType.ADDED));
		}

		/**
		 * This variable maintains count of additions to version2 list, since
		 * after each addition the index to-add-to will change.
		 */
	/*	int counter = 0;
		for (Entry<Integer, Integer> deletedFilter : deletedFilterIndices.entrySet()) {
			// Merge deleted Filters into version2 at proper index.
			filterList_v2.add(deletedFilter.getValue() + counter++, filterList_v1.get(deletedFilter.getKey()));
		}
	}*/
	
	public static void 	processFiltersDiff(List<Filter> filterList_v1, List<Filter> filterList_v2,
			Map<String, ModificationEntry> modifications) {
		ArrayList<String>  idList1 = getFilterIds(filterList_v1);
		ArrayList<String>  idList2 = getFilterIds(filterList_v2);
		createModificationList(idList2, idList1, filterList_v1, filterList_v2, modifications, false); //Add or drop
		createModificationList(idList1, idList2, filterList_v1, filterList_v2,  modifications, true); //Delete or Drag
		checkReordering(idList1, idList2, modifications);
    }
	
	private static void createModificationList(ArrayList<String> idList1, ArrayList<String> idList2, List<Filter> filterList_v1, List<Filter> filterList_v2, Map<String, ModificationEntry> modifications, boolean delete) {
		Map<Integer, Filter> deletedFilterIndices = new LinkedHashMap<Integer, Filter>();
		ArrayList<String> processedIds = new ArrayList<String>();
		for (int i = 0; i < idList1.size(); i++) {
			String filterId = idList1.get(i);
			String filterIdDnd = null;
			if (filterId.indexOf("_") > 0) {
				filterIdDnd = filterId.split("_")[0];
			} else {
				filterIdDnd = filterId + "_dnd";
			}
			if (!processedIds.contains(filterId) && !idList2.contains(filterId)) {
				Filter filter = null;
				if (delete) {
					filter = getFilterById(filterList_v1, filterId);
				} else {
					filter = getFilterById(filterList_v2, filterId);
				}
				updateModificationList(filter, modifications, filterId, delete);
				if (delete) {
					ModificationEntry me = modifications.get(filterId);
					ModificationEntry meDnd = modifications.get(filterIdDnd);
					if (me.getModificationType() == ModificationType.DELETED
							|| me.getModificationType() == ModificationType.DRAG_FROM) {
						deletedFilterIndices.put(i, getFilterById(filterList_v1, filterId));
					} else if (meDnd != null && meDnd.getModificationType() == ModificationType.DRAG_FROM) {
						deletedFilterIndices.put(i, getFilterById(filterList_v1, filterIdDnd));
					}
				}
			} else if (delete) {
				int index_1 = idList1.indexOf(filterId);
				int index_2 = idList2.indexOf(filterId);
				Filter filter1 = filterList_v1.get(index_1);
				Filter filter2 = filterList_v2.get(index_2);
				/*if (index_1 != index_2) { // Drag and Drop in same multi filter
											// i.e. at same level
					modifications.put(filterId, new ModificationEntry(ModificationType.DND));
				}*/
				if (filter1 instanceof MultiFilter && filter2 instanceof MultiFilter) {
					processMultiFilterDiff((MultiFilter) filter1, (MultiFilter) filter2, modifications);
				} else if (isFilterModified((SingleFilterImpl) filter1, (SingleFilterImpl) filter2)) {
					// Modified
					modifications.put(filter2.getFilterId(),
							new ModificationEntry(ModificationType.MODIFIED, filter1, filter2));
				}
			}
		}
		/**
		 * Adding deleted entries to filterlist_v2, to highlight it in red.
		 */
		if (delete) {
			for (Entry<Integer, Filter> entry : deletedFilterIndices.entrySet()) {
				filterList_v2.add(entry.getKey(), entry.getValue());
			}
		}
	}
	
	private static void checkReordering(ArrayList<String>  idList1, ArrayList<String>  idList2, Map<String, ModificationEntry> modifications) {
		if (idList1.size() == idList2.size() && idList1.containsAll(idList2)) {
			for (int i=0; i < idList1.size(); i++) {
				String filterId =  idList1.get(i);
				int index_1 = idList1.indexOf(filterId);
				int index_2 = idList2.indexOf(filterId);
				if (index_1 != index_2) {
					modifications.put(filterId, new ModificationEntry(ModificationType.DND));
				}
			}
		} 
	}
	
	private static void updateModificationList(Filter filter, Map<String, ModificationEntry> modifications, String filterId, boolean delete){
		ModificationType checkType = null;
		ModificationType oldType = null;
		ModificationType newType = null;
		ModificationType originalType = null;
		String filterIdDnd = null;
		if (filterId.indexOf("_") > 0) {
			filterIdDnd = filterId.split("_")[0];
		} else {
			filterIdDnd = filterId + "_dnd";
		}
		if (delete) {
			checkType = ModificationType.ADDED;
			newType = ModificationType.DRAG_TO;
			oldType = ModificationType.DRAG_FROM;
			originalType = ModificationType.DELETED;
		} else {
			checkType = ModificationType.DELETED;
			newType = ModificationType.DRAG_FROM;
			oldType = ModificationType.DRAG_TO;
			originalType = ModificationType.ADDED;
		}
		if (modifications.containsKey(filterId)) {
			ModificationEntry me = modifications.get(filterId);
			if (me.getModificationType() == checkType) {
				me.setModificationType(newType);
				modifications.put(filterId, me);
				ModificationEntry dragModificationEntry = new ModificationEntry(oldType);
				modifications.put(filterIdDnd, dragModificationEntry);
				filter.setFilterId(filterIdDnd);
			}
		} else {
			modifications.put(filterId, new ModificationEntry(originalType));
		}
		if (filter instanceof MultiFilter) {
			MultiFilter multiFilter = (MultiFilter) filter;
			ArrayList<Filter> childFilterList = (ArrayList<Filter>) multiFilter.getSubClause().getFilters();
			for (int i = 0; i < childFilterList.size(); i++) {
				Filter childFilter = childFilterList.get(i);
				updateModificationList(childFilter, modifications, childFilter.getFilterId(), delete);
			}
		}
	}

	private static Filter getFilterById(List<Filter> filterList, String id) {
		for(int i = 0; i < filterList.size(); i++) {
			String filterId = filterList.get(i).getFilterId();
			if(id.equals(filterId)){
				return  filterList.get(i);
			}
		}
		return null;
	}
	
	private static ArrayList<String> getFilterIds(List<Filter> filterList) {
		ArrayList<String> listOfIds = new ArrayList<String> ();
		for (int i = 0; i < filterList.size(); i++) {
			listOfIds.add(filterList.get(i).getFilterId());
		}
		return listOfIds;
	}

	/**
	 * Returns boolean indicating whether there was any modification between the
	 * 2 versions of the same filter.
	 * 
	 * @param filterV1
	 * @param filterV2
	 * @return
	 */
	private static boolean isFilterModified(SingleFilterImpl filterV1,
			SingleFilterImpl filterV2) {
		if (getFilterAsString(filterV1).equalsIgnoreCase(getFilterAsString(filterV2))) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the filter as simple string.
	 * 
	 * @param filter
	 * @return
	 */
	public static String getFilterAsString(SingleFilterImpl filter) {
		if (filter == null) {
			return "";
		}
		String links = "";
		if (filter.getLinks() != null) {
			for (int i=0; i<filter.getLinks().size(); i++) {
				links += (i==0 ? "" : "'s ") + filter.getLinks().get(i).getLinkText();
			}
		}
		String operator = filter.getOperator();
		String filterValue = "";
		if (filter.getFilterValue() instanceof SimpleFilterValueImpl) {
			SimpleFilterValueImpl sf = (SimpleFilterValueImpl) filter.getFilterValue();
			if (sf.getValue() != null) {
				filterValue = sf.getValue();
			}
		} else if (filter.getFilterValue() instanceof RelatedFilterValueImpl) {
			RelatedFilterValueImpl rf = (RelatedFilterValueImpl) filter.getFilterValue();
			for (int i = 0 ; i<rf.getLinks().size(); i++) {
				filterValue += (i==0 ? "" : "'s ") + rf.getLinks().get(i).getLinkText();
			}
		}
		return links + " " + operator + " " + filterValue;
	}
	
	/**
	 * Returns artifact's current version element.
	 * @param docElement
	 * @return
	 */
	public static Element getCurrentVersionElement(Element docElement) {
		return getVersionElementByName(docElement, CURRENT_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns artifact's previous version element.
	 * @param docElement
	 * @return
	 */
	public static Element getPreviousVersionElement(Element docElement) {
		return getVersionElementByName(docElement, PREVIOUS_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns artifact's server version element.
	 * @param docElement
	 * @return
	 */
	public static Element getServerVersionElement(Element docElement) {
		return getVersionElementByName(docElement, SERVER_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns artifact's current version element.
	 * @param docElement
	 * @return
	 */
	public static Node getCurrentVersionNode(Element docElement) {
		return getVersionNodeByName(docElement, CURRENT_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns artifact's previous version element.
	 * @param docElement
	 * @return
	 */
	public static Node getPreviousVersionNode(Element docElement) {
		return getVersionNodeByName(docElement, PREVIOUS_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns artifact's server version element.
	 * @param docElement
	 * @return
	 */
	public static Node getServerVersionNode(Element docElement) {
		return getVersionNodeByName(docElement, SERVER_VERSION_TAG_NAME);
	}
	
	/**
	 * Returns the specified version Element of the artifact.
	 * @param docElement
	 * @return
	 */
	private static Element getVersionElementByName(Element docElement, String elementName) {
		Node elementNode = getVersionNodeByName(docElement, elementName);
		if (elementNode == null) {
			return null;
		}
		Element currentElementContentDocElement = XMLParser.parse(elementNode.toString()).getDocumentElement();
		return currentElementContentDocElement;
	}
	
	/**
	 * Returns the specified version Element of the artifact.
	 * @param docElement
	 * @return
	 */
	private static Node getVersionNodeByName(Element docElement, String elementName) {
		Node versionContentElement = null;	
		NodeList nodes = docElement.getElementsByTagName(elementName);
		if (nodes != null && nodes.getLength() > 0) {
			versionContentElement = nodes.item(0);
		}
		return versionContentElement;
	}
	
	/**
	 * Process diff between the 2 versions of DT, also merges the
	 * deleted rules and columns into the new version(v2).<br/>
	 * 
	 * @param dt_v1
	 * @param dt_v2
	 */
	public static void processDTDiff(Node dt_v1, Node dt_v2, Map<String, ModificationEntry> modifications) {
		Map<String, ModificationEntry> tempModifications = new HashMap<String, ModificationEntry>();
		processDTDiffInternal(dt_v1, dt_v2, tempModifications, DTDiffType.DECISION_TABLE);
		for (Entry<String, ModificationEntry> entry : tempModifications.entrySet()) {
			modifications.put(DTDiffType.DECISION_TABLE.getKeyPrefix() + entry.getKey(), entry.getValue());
		}
		tempModifications .clear();
		processDTDiffInternal(dt_v1, dt_v2, tempModifications, DTDiffType.EXCEPTION_TABLE);
		for (Entry<String, ModificationEntry> entry : tempModifications.entrySet()) {
			modifications.put(DTDiffType.EXCEPTION_TABLE.getKeyPrefix() + entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * process DIFF for specified type - decisionTable/exceptionTable
	 * @param dt_v1
	 * @param dt_v2
	 * @param modifications
	 */
	static void processDTDiffInternal(Node dt_v1, Node dt_v2, Map<String, ModificationEntry> modifications, DTDiffType diffType) {
		mergeColumns(dt_v1, dt_v2, modifications, diffType);
		mergeRules(dt_v1, dt_v2, modifications, diffType);
		cleanUpModifications(modifications);
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
	 * Processes DT diff for added/deleted rules and modification within a rule and merges the deleted rules into v2.
	 * Also collects the changes in a Map(modifications) with key format as 'rule<rule_id>' OR 'ruleCell<cond/act_id>'.
	 * @param dt_v1
	 * @param dt_v2
	 * @param modifications
	 */
	private static void mergeRules(Node dt_v1, Node dt_v2, Map<String, ModificationEntry> modifications, DTDiffType diffType) {
		Node dtPage_v1 = getChildNodeByName(dt_v1, diffType.getTagName() + "Page");
		Node dtPage_v2 = getChildNodeByName(dt_v2, diffType.getTagName() + "Page");

		List<Node> rules_v1 = getChildNodesByName(dtPage_v1, "tableRule");
		List<Node> rules_v2 = getChildNodesByName(dtPage_v2, "tableRule");
		Map<String, Node> mergedNodes = new LinkedHashMap<String, Node>();
		mergeNodes(rules_v1, rules_v2, mergedNodes, "tableRule", "ruleId", modifications);
		for (int i = 0; i < rules_v2.size(); i++) {//Remove all rules from v2
			if ("tableRule".equals(rules_v2.get(i).getNodeName())) {
				dtPage_v2.removeChild(rules_v2.get(i));
			}
		}
		for (Entry<String, Node> entry : mergedNodes.entrySet()) {//Add merged rules to v2
			dtPage_v2.appendChild(entry.getValue());
		}
	}
	
	/**
	 * Processes DT diff for columns in DT and merges the deleted columns into v2.
	 * Also collects the changes in a Map(modifications) with key format as 'column<column_id>'.
	 * @param dt_v1
	 * @param dt_v2
	 * @param modifications
	 */
	private static void mergeColumns(Node dt_v1, Node dt_v2, Map<String, ModificationEntry> modifications, DTDiffType diffType) {
		Node dtColumns_v1 = getChildNodeByName(dt_v1, diffType.getTagName() + "Columns");
		List<Node> columnNodes_v1 = null;
		if (dtColumns_v1 != null) {
			columnNodes_v1 = getChildNodesByName(dtColumns_v1, "column");
		}
		Node dtColumns_v2 = getChildNodeByName(dt_v2, diffType.getTagName() + "Columns");
		List<Node> columnNodes_v2 = null;
		if (dtColumns_v2 != null) {
			columnNodes_v2 = getChildNodesByName(dtColumns_v2, "column");
		}
		Map<String, Node> mergedNodes = new LinkedHashMap<String, Node>();
		Set<String> deletedNodeIds = mergeNodes(columnNodes_v1, columnNodes_v2, mergedNodes, "column", "columnId", modifications);
		if (columnNodes_v2 != null) {
			for (int i = 0; i < columnNodes_v2.size(); i++) {//Remove columns from v2
				//if ("column".equals(columns_v2.get(i).getNodeName())) {
				dtColumns_v2.removeChild(columnNodes_v2.get(i));
				//}
			}
		}
		for (Entry<String, Node> entry : mergedNodes.entrySet()) {//Add merged columns to v2
			dtColumns_v2.appendChild(entry.getValue());
		}
		
		Node dtPage_v1 = getChildNodeByName(dt_v1, diffType.getTagName() + "Page");
		Node dtPage_v2 = getChildNodeByName(dt_v2, diffType.getTagName() + "Page");
		//Merge condition/action data for deleted columns in to respective rules.
		List<Node> rules_v1 = getChildNodesByName(dtPage_v1, "tableRule");
		List<Node> rules_v2 = getChildNodesByName(dtPage_v2, "tableRule");
		for (int i = 0; i < rules_v1.size(); i++) {
			Node ruleInV1 = rules_v1.get(i);
			NodeList trvs_v1 = ruleInV1.getChildNodes();
			for (int c = 0; c < trvs_v1.getLength(); c++) {
				Node trv_v1 = trvs_v1.item(c);
				if ("condition".equals(trv_v1.getNodeName()) || "action".equals(trv_v1.getNodeName())) {
					String cId = getChildNodeTextValue(trv_v1, "columnId");
					if (deletedNodeIds.contains(cId)) {
						//Add condition/action info to rule for missing columns in v2.
						Node ruleInV2 = getNodeById(getChildNodeTextValue(ruleInV1, "ruleId"), rules_v2);
						if (ruleInV2 != null)
							ruleInV2.appendChild(trv_v1);
					}
				}
			}
		}
	}
	
	/**
	 * Merges the two nodelists into a single one. Maintains the sequence of both lists while merging.
	 * @return ids of deleted nodes that were merged into v2.
	 */
	private static Set<String> mergeNodes(List<Node> v1, List<Node> v2, Map<String, Node> mergedNodes, String nodeName, String idElemName, Map<String, ModificationEntry> modifications) {
		Map<String, Node> nodes_v1 = new LinkedHashMap<String, Node>();
		Map<String, Node> nodes_v2 = new LinkedHashMap<String, Node>();
		Set<String> deletedNodeIds = new HashSet<String>();
		if (v1 != null) {
			for (int i = 0; i < v1.size(); i++) {
				if (!nodeName.equals(v1.get(i).getNodeName())) {
					continue;
				}
				String id = getChildNodeTextValue(v1.get(i), idElemName);
				nodes_v1.put(id, v1.get(i));
			}
		}
		if (v2 != null) {
			for (int i = 0; i < v2.size(); i++) {
				if (!nodeName.equals(v2.get(i).getNodeName())) {
					continue;
				}
				String id = getChildNodeTextValue(v2.get(i), idElemName);
				nodes_v2.put(id, v2.get(i));
			}
		}
		
		for (Entry<String, Node> entry : nodes_v1.entrySet()) {
			if (nodes_v2.get(entry.getKey()) == null) {
				mergedNodes.put(entry.getKey(), entry.getValue());
				deletedNodeIds.add(entry.getKey());
				modifications.put(nodeName + entry.getKey(), new ModificationEntry(ModificationType.DELETED));
				//REMOVED
			}
			else {
				mergedNodes.put(entry.getKey(), nodes_v2.get(entry.getKey()));
				if ("tableRule".equals(nodeName)) {
					//Will need change if supporting modify for columns
					processRuleModifications(entry.getValue(), nodes_v2.get(entry.getKey()), modifications);
				}
				else if ("column".equals(nodeName)) {
					Node col_v2 = nodes_v2.get(entry.getKey());
					Node col_v1 = entry.getValue();
					String columnName_v2 = col_v2 != null ? getChildNodeTextValue(col_v2, "name") : null;
					String columnName_v1 = col_v1 != null ? getChildNodeTextValue(col_v1, "name") : null;
					if ((columnName_v1 != null && !columnName_v1.equals(columnName_v2)) || (columnName_v2 != null && !columnName_v2.equals(columnName_v1))) {
						modifications.put(nodeName + entry.getKey(), new ModificationEntry(ModificationType.MODIFIED, columnName_v1, columnName_v2));
					}
				}
			}
		}
		for (Entry<String, Node> entry : nodes_v2.entrySet()) {
			if (mergedNodes.get(entry.getKey()) == null) {
				mergedNodes.put(entry.getKey(), entry.getValue());
				modifications.put(nodeName + entry.getKey(), new ModificationEntry(ModificationType.ADDED));
				//ADDED
			}
		}
		return deletedNodeIds;
	}
	
	/**
	 * Process diff between the 2 lists of RTIView BindingInfos.
	 * @param bindingInfos_v1
	 * @param bindingInfos_v2
	 * @param modifications
	 */
	public static void processRTIViewDiff(List<BindingInfo> bindingInfos_v1, List<BindingInfo> bindingInfos_v2, Map<BindingInfo, ModificationEntry> modifications) {
		Map<String, BindingInfo> bindings_v1 = new HashMap<String, BindingInfo>();
		for (BindingInfo v1 : bindingInfos_v1) {
			bindings_v1.put(v1.getId(), v1);
		}
		for (BindingInfo v2 : bindingInfos_v2) {
			BindingInfo v1 = bindings_v1.get(v2.getId());
			if (v1 != null && v2 != null 
					&& (v1.getValue() != null && !v1.getValue().equals(v2.getValue())) || (v2.getValue() != null && !v2.getValue().equals(v1.getValue()))) {
				modifications.put(v2, new ModificationEntry(ModificationType.MODIFIED, v1.getValue() == null ? "" : v1.getValue(), v2.getValue()));
			}
		}
	}
	
	private static Node getNodeById(String id, List<Node> nodes) {
		Node result = null;
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).toString().trim().isEmpty()) {
				continue;
			}
			String otherId = getChildNodeTextValue(nodes.get(i), "ruleId");
			if (otherId != null && id.equals(otherId)) {
				result = nodes.get(i);
			}
		}
		return result;
	}
	
	/**
	 * Processes modifications between two version of same(based on id) rule.
	 * Collects individual cell modifications in modifications map.
	 * 
	 * @param v1
	 * @param v2
	 * @param modifications
	 * @return
	 */
	private static void processRuleModifications(Node v1, Node v2, Map<String, ModificationEntry> modifications) {
		Map<String, Node> ruleCells_v1 = getChildrenWithIds(v1);
		Map<String, Node> ruleCells_v2 = getChildrenWithIds(v2);
		Set<String> mergedIds = new HashSet<String>();
		mergedIds.addAll(ruleCells_v1.keySet());
		mergedIds.addAll(ruleCells_v2.keySet());
		for (String id : mergedIds) {
			String previousValue = null;
			Node ruleCell_v1 = ruleCells_v1.get(id);
			Node ruleCell_v2 = ruleCells_v2.get(id);
			
			//Process rule variable modifications based on expr value.
			String expr_v1 = ruleCell_v1 == null ? null : getChildNodeTextValue(ruleCell_v1, "expression");
			String expr_v2 = ruleCell_v2 == null ? null : getChildNodeTextValue(ruleCell_v2, "expression");
			if ((expr_v1 != null && !expr_v1.equals(expr_v2)) || (expr_v2 != null && !expr_v2.equals(expr_v1))) {//MODIFIED
				previousValue = expr_v1 == null ? "\"\"" : expr_v1;
			}
			
			//Process rule variable modifications based on enabled or disabled.
			String isEnabled_v1 = ruleCell_v1 == null ? null : getChildNodeTextValue(ruleCell_v1, "isEnabled");
			String isEnabled_v2 = ruleCell_v2 == null ? null : getChildNodeTextValue(ruleCell_v2, "isEnabled");
			String enabled_v1 = (isEnabled_v1 == null || "true".equals(isEnabled_v1)) ? "Enabled" : "Disabled";
			String enabled_v2 = (isEnabled_v2 == null || "true".equals(isEnabled_v2)) ? "Enabled" : "Disabled";
			if (enabled_v1 != null && enabled_v2 != null && !enabled_v1.equals(enabled_v2)) {
				previousValue = ((previousValue == null) ? "" : previousValue + "<br/>") + "<i>Was previously " + enabled_v1 + "</i>";
			}
			
			if (previousValue != null) {
				modifications.put("ruleCell" + id, new ModificationEntry(ModificationType.MODIFIED, previousValue, expr_v2));
			}
			//else UNCHANGED
		}
	}
	
	/**
	 * Returns a map of (childID - childNode). Only considers children nodes that has id.
	 * @param node
	 * @return
	 */
	private static Map<String, Node> getChildrenWithIds(Node node) {
		List<Node> conditions = getChildNodesByName(node, "condition");
		List<Node> actions = getChildNodesByName(node, "action");
		Map<String, Node> map = new HashMap<String, Node>();
		for (int i = 0; i < conditions.size(); i++) {
			if (conditions.get(i).toString().trim().isEmpty()) {
				continue;
			}
			String conditionId = getChildNodeTextValue(conditions.get(i), "varId");
			if (conditionId != null)
				map.put(conditionId, conditions.get(i));
		}
		
		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).toString().trim().isEmpty()) {
				continue;
			}
			String actionId = getChildNodeTextValue(actions.get(i), "varId");
			if (actionId != null)
				map.put(actionId, actions.get(i));
		}
		return map;
	}
	
	/**
	 * Returns a legend that shows the colour coding used to show artifact diff.
	 * 
	 * @return
	 */
	public static Widget getDiffLegend() {
		HLayout layout = new HLayout();
		layout.setHeight("20px");
		layout.setWidth100();
		layout.setLayoutTopMargin(3);
		layout.setAlign(Alignment.RIGHT);
		InlineHTML html = new InlineHTML("<nobr>&nbsp;<span class=\"ws-diff-background-add\" style=\"width:10px; padding:0 10px 0 0; border: 1px solid #888;\"></span><span style=\"padding:0 5px 0 1px;\">" + globalMsg.diff_legend_added() + "</span>" +
				"<span class=\"ws-diff-background-remove\" style=\"width:10px; padding:0 10px 0 0; border: 1px solid #888;\"></span><span style=\"padding:0 5px 0 1px;\">" + globalMsg.diff_legend_deleted() + "</span>" +
				"<span class=\"ws-diff-background-modify\" style=\"width:10px; padding:0 10px 0 0; border: 1px solid #888;\"></span><span style=\"padding:0 3px 0 1px;\">" + globalMsg.diff_legend_modified() + "</span>" +
				"<span class=\"ws-diff-background-dnd-faint\" style=\"width:10px; padding:0 10px 0 0; border: 1px solid #888;\"></span><span style=\"padding:0 3px 0 1px;\">" + globalMsg.diff_legend_dnd()+ "</span>" +
				"</nobr>");
		
		layout.addMember(html);
		return layout.asWidget();
	}
	
	/**
	 * Finds the entries amongst the list that match the passed regular expression.
	 * @param keys
	 * @param regExp
	 * @return
	 */
	static List<String> findMatchingEntries(Collection<String> list, String regExp) {
		List<String> toBeRemoved = new ArrayList<String>();
		for (String key : list) {
			if (key.matches(regExp)) {
				toBeRemoved.add(key);
			}
		}
		return toBeRemoved;
	}
	
	/**
	 * Remove RuleVariable entries in modifications that are actually due to Column addition/deletion (and not actual RuleVar modification).
	 * @param modifications
	 */
	private static void cleanUpModifications(Map<String, ModificationEntry> modifications) {
		//Eg: In column addition case - while RuleVariable comparison it is detected as ruleVariable was previously null and now has a value.
		//This method removes such false positive entries from the modifications map.
		Set<String> columnEntryKeys = new HashSet<String>();
		columnEntryKeys.addAll(findMatchingEntries(modifications.keySet(), "column(\\d+)"));
		for (String key : columnEntryKeys) {
			if (modifications.get(key).getModificationType() == ModificationType.ADDED || 
					modifications.get(key).getModificationType() == ModificationType.DELETED) {//Column modification should be Added or deleted only
				String columnId = key.substring(6);//Index-6 to match Column<NUM> etc
				Set<String> ruleCellEntryKeys = new HashSet<String>();
				ruleCellEntryKeys.addAll(findMatchingEntries(modifications.keySet(), "ruleCell(\\d+)(_)" + columnId));
				for (String ruleCellEntryKey : ruleCellEntryKeys) {
					modifications.remove(ruleCellEntryKey);
				}
			}
		}
	}
}
