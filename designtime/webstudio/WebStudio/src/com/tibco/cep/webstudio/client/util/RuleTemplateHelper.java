/**
 * 
 */
package com.tibco.cep.webstudio.client.util;

import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.addMarkers;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.removeMarker;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.i18n.DomainMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.DomainInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.ViewInfo;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.problems.RULE_ERROR_TYPES;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.RuleTemplateInstanceDataItem;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.model.rule.instance.BuilderSubClause;
import com.tibco.cep.webstudio.model.rule.instance.Command;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.FilterValue;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;
import com.tibco.cep.webstudio.model.rule.instance.RangeFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedFilterValue;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.RuleTemplateInstance;
import com.tibco.cep.webstudio.model.rule.instance.SingleFilter;
import com.tibco.cep.webstudio.model.rule.instance.impl.BuilderSubClauseImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.CommandImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.EmptyFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.MultiFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RangeFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.RelatedLinkImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SimpleFilterValueImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.SingleFilterImpl;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;

/**
 * Helper methods used during rule template or instance operations
 * @author Vikram Patil
 */
public class RuleTemplateHelper {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	private static RTIMessages rtiMsgBundle = (RTIMessages)I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);
	private static DomainMessages domainMsgBundle =(DomainMessages) I18nRegistry.getResourceBundle(I18nRegistry.DOMAIN_MESSAGES);
	
	private static WebStudioClientLogger logger = WebStudioClientLogger.getLogger(RuleTemplateHelper.class.getName());
	/**
	 * Saves the specified rule template instance
	 * @param ruleTemplateInstance
	 * @param selectedResource
	 * @param isViewTemplate
	 * @param request
	 * @param bindingInfoList 
	 */
	@SuppressWarnings("rawtypes")
	public static void saveRuleTemplateInstance(RuleTemplateInstance ruleTemplateInstance, NavigatorResource selectedResource, HttpRequest request, boolean isViewTemplate, List<BindingInfo> bindingInfoList, List<Command> commandList, boolean isMerge){
		RuleTemplateInstanceDataItem rtiDataItem = new RuleTemplateInstanceDataItem(ruleTemplateInstance, isMerge);
		rtiDataItem.setViewTemplate(isViewTemplate);
		rtiDataItem.setBindingInfoList(bindingInfoList);
		rtiDataItem.setCommandList(commandList);
		
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectName);
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, rtiDataItem);
		
		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_ARTIFACT_SAVE, requestParameters);
		
		if (request == null) request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);
		
		request.submit(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL());
	}
	
	/**
	 * Retrieves the selected rule template 
	 * @param selectedResource
	 */
	public static void getRuleTemplate(NavigatorResource selectedResource) {
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		String path = selectedResource.getId().substring(selectedResource.getId().indexOf("$"), selectedResource.getId().length()).replace("$", "/");
		String ruleTemplatePath = path.substring(path.indexOf("/"), path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());
		
		HttpRequest request = new HttpRequest();
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, ruleTemplatePath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, ARTIFACT_TYPES.valueOf(type.toUpperCase()).getValue()));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type));
		
		request.submit(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL());
	}
	
	/**
	 * Retrieve Symbols
	 * 
	 * @param docElement
	 * @return
	 */
	public static List<SymbolInfo> getSymbols(Element docElement) {
		List<SymbolInfo> symbols = new ArrayList<SymbolInfo>();
		
		NodeList artifactContentList = null;
		if (docElement.getElementsByTagName("artifactContent").getLength() > 0
				&& docElement.getElementsByTagName("artifactContent").item(0) != null) {
			artifactContentList = docElement.getElementsByTagName("artifactContent").item(0).getChildNodes();
		}
		else {
			artifactContentList = docElement.getChildNodes();
		}
		
		for (int j = 0; j < artifactContentList.getLength(); j++) {
			if (!artifactContentList.item(j).toString().trim().isEmpty()) {
				if (artifactContentList.item(j).getNodeName().equals("symbols")) {
					NodeList symbolInfoList = artifactContentList.item(j).getChildNodes();

					for (int i = 0; i < symbolInfoList.getLength(); i++) {
						if (!symbolInfoList.item(i).toString().trim().isEmpty()) {
							symbols.add(parseSymbolInfo(symbolInfoList.item(i)));
						}
					}
				}
			}
		}
		
		return symbols;
	}
	
	/**
	 * Retrieve Symbol Info objects
	 * 
	 * @param symbolInfo
	 * @return
	 */
	public static SymbolInfo parseSymbolInfo(Node symbolInfo) {
		SymbolInfo symbol = new SymbolInfo();
		NodeList symbolInfoDetails = symbolInfo.getChildNodes();

		for (int j = 0; j < symbolInfoDetails.getLength(); j++){
			if (!symbolInfoDetails.item(j).toString().trim().isEmpty()) {
				if (symbolInfoDetails.item(j).getNodeName().equals("symbolAlias")) symbol.setAlias(symbolInfoDetails.item(j).getFirstChild().getNodeValue());
				if (symbolInfoDetails.item(j).getNodeName().equals("type")) {
					String type = symbolInfoDetails.item(j).getFirstChild().getNodeValue();
					symbol.setType(type);
				}
				
				if (symbolInfoDetails.item(j).getNodeName().equals("domainInfo")) {
					NodeList domainNodeList = symbolInfoDetails.item(j).getChildNodes();
					DomainInfo domainInfo = parseDomainInfo(domainNodeList);
					if (domainInfo.getValues().size() > 0) {
						symbol.setDomainInfo(domainInfo);
					}
				}

				if (symbolInfoDetails.item(j).getNodeName().equals("symbolInfo")) {
					if (symbol.getContainedSymbols() == null) {
						symbol.setContainedSymbols(new ArrayList<SymbolInfo>());
					}
					symbol.getContainedSymbols().add(parseSymbolInfo(symbolInfoDetails.item(j)));
				}
			}
		}

		return symbol;
	}
	
	/**
	 * Parse Domain Info elements
	 * 
	 * @param domainNodeList
	 * @return
	 */
	private static DomainInfo parseDomainInfo(NodeList domainNodeList) {
		DomainInfo domainInfo = new DomainInfo();
		
		for (int k = 0; k < domainNodeList.getLength(); k++) {
			if (!domainNodeList.item(k).toString().trim().isEmpty()) {
				String domainNodeName = domainNodeList.item(k).getNodeName();
				
				if (domainNodeName.equals("dataType")) {
					domainInfo.setType(domainNodeList.item(k).getFirstChild().getNodeValue());
				} else if (domainNodeName.equals("singleEntry")) {
					NodeList domainValueElement = domainNodeList.item(k).getChildNodes();
					for (int l = 0; l < domainValueElement.getLength(); l++) {
						if (!domainValueElement.item(l).toString().trim().isEmpty() && domainValueElement.item(l).getNodeName().equals("value")) {
							domainInfo.addValue(k + "",
									domainValueElement.item(l)
									.getFirstChild()
									.getNodeValue());
						}
					}
				}
			}
		}

		return domainInfo;
	}
	
	/**
	 * Retrieve data on commands
	 * 
	 * @param docElement
	 * @return
	 */
	public static List<Command> getCommandInfo(Element docElement, RuleTemplateInstance selectedRuleTemplateInstance, Map<String, List<SymbolInfo>> commandSymbols){
		List<Command> commandList = new ArrayList<Command>();
		
		boolean bAddFilter = false;
		NodeList command = docElement.getElementsByTagName("commandInfo");
		for (int i=0 ;i<command.getLength();i++){
			NodeList commandInfoDetails = command.item(i).getChildNodes();
			
			bAddFilter = false;
			Command cInfo = new CommandImpl();
			for (int j = 0; j < commandInfoDetails.getLength(); j++) {
				if (!commandInfoDetails.item(j).toString().trim().isEmpty()) {
					String nodeName = commandInfoDetails.item(j).getNodeName();
					String nodeValue = (nodeName.equals("filter") || nodeName.equals("symbols"))?"":commandInfoDetails.item(j).getFirstChild().getNodeValue();

					if (nodeName.equals("commandAlias")) cInfo.setAlias(nodeValue);
					if (nodeName.equals("type")) cInfo.setType(nodeValue);
					if (nodeName.equals("actionType")) cInfo.setActionType(nodeValue);

					if (nodeName.equals("filter")) {
						if (cInfo.getSubClause() == null){
							BuilderSubClause builderSubClause = new BuilderSubClauseImpl();
							cInfo.setSubClause(builderSubClause);
						}
						cInfo.getSubClause().addFilter(getSingleFilter(commandInfoDetails.item(j)));
						bAddFilter = true;
					}
					
					if (nodeName.equals("symbols")) {
						List<SymbolInfo> symbols = new ArrayList<SymbolInfo>();
						NodeList symbolInfoList = commandInfoDetails.item(j).getChildNodes();
						
						for (int l=0; l < symbolInfoList.getLength(); l++) {
							if (!symbolInfoList.item(l).toString().trim().isEmpty()) {
								symbolInfoList = symbolInfoList.item(l).getChildNodes();
								break;
							}
						}

						for (int k = 0; k < symbolInfoList.getLength();k++) {
							if (!symbolInfoList.item(k).toString().trim().isEmpty() && symbolInfoList.item(k).getNodeName().equals("symbolInfo")) {
								symbols.add(parseSymbolInfo(symbolInfoList.item(k)));
							}
						}
						if (commandSymbols != null && commandSymbols.get(cInfo.getAlias()) == null) commandSymbols.put(cInfo.getAlias(), symbols);
					}
				}
			}
			commandList.add(cInfo);
			if (bAddFilter && selectedRuleTemplateInstance != null) selectedRuleTemplateInstance.getActions().addAction(cInfo);
		}
		
		return commandList;			
	}

	/**
	 * Retrieve data for single filter
	 * 
	 * @param filter
	 * @return
	 */
	public static SingleFilter getSingleFilter(Node filter) {
		SingleFilter singleFilter = new SingleFilterImpl();
		
		NodeList filterDetails = filter.getChildNodes();			
		for (int i = 0; i < filterDetails.getLength(); i++) {
			if (!filterDetails.item(i).toString().trim().isEmpty()) {
				String nodeName = filterDetails.item(i).getNodeName();
				String nodeValue = nodeName.equals("operator")|| nodeName.equals("filterId") ? filterDetails.item(i).getFirstChild().getNodeValue() : "";

				if (nodeName.equals("operator")) singleFilter.setOperator(nodeValue);
				if (nodeName.equals("filterId")) singleFilter.setFilterId(nodeValue);
				if (nodeName.equals("value")){
					singleFilter.setFilterValue(getFilterValue(filterDetails.item(i)));
				}
				if (nodeName.equals("link")){
					NodeList linkDetails = filterDetails.item(i).getChildNodes();

					RelatedLink relatedLink = new RelatedLinkImpl();
					for (int j = 0; j < linkDetails.getLength(); j++) {
						if (!linkDetails.item(j).toString().trim().isEmpty()){
							if (linkDetails.item(j).getNodeName().equals("name")){
								relatedLink.setLinkText(linkDetails.item(j).getFirstChild().getNodeValue());
							} else { 
								relatedLink.setLinkType(linkDetails.item(j).getFirstChild().getNodeValue());
							}
						}
					}
					singleFilter.addRelatedLink(relatedLink);
				}
			}
		}
		
		if (singleFilter.getOperator() != null) {
			if ((!singleFilter.getOperator().trim().equals(CommandOperator.SET_TO_FALSE.getValue()) 
					&& !singleFilter.getOperator().trim().equals(CommandOperator.SET_TO_TRUE.getValue())
					&& !singleFilter.getOperator().trim().equals(CommandOperator.SET_TO_TRUE.getValue())
					&& !singleFilter.getOperator().trim().equals(CommandOperator.SET_TO_NULL.getValue())
					&& !singleFilter.getOperator().trim().equals(FilterOperator.EQUALS_FALSE.getValue())
					&& !singleFilter.getOperator().trim().equals(FilterOperator.EQUALS_TRUE.getValue())
					&& !singleFilter.getOperator().trim().equals(FilterOperator.IS_NULL.getValue())
					&& !singleFilter.getOperator().trim().equals(FilterOperator.IS_NOT_NULL.getValue()))
					&& singleFilter.getFilterValue() instanceof EmptyFilterValueImpl) {
				singleFilter.setFilterValue(new SimpleFilterValueImpl());
			}
		}
		
		return singleFilter;
	}
	
	/**
	 * Retrieve data for filter value
	 * 
	 * @param filterValue
	 * @return
	 */
	public static FilterValue getFilterValue(Node filterValue) {
		FilterValue newFilterValue = null;

		if (filterValue.getChildNodes().getLength() > 1) {
			NodeList filterValueDetails = filterValue.getChildNodes();

			for (int i = 0; i < filterValueDetails.getLength(); i++) {
				if (!filterValueDetails.item(i).toString().trim().isEmpty()) {
					String nodeName = filterValueDetails.item(i).getNodeName();

					if (nodeName.equals("minValue")) {
						if (newFilterValue == null)
							newFilterValue = new RangeFilterValueImpl();
						((RangeFilterValue) newFilterValue).setMinValue(filterValueDetails.item(i).getFirstChild()
								.getNodeValue());
					} else if (nodeName.equals("maxValue")) {
						((RangeFilterValue) newFilterValue).setMaxValue(filterValueDetails.item(i).getFirstChild()
								.getNodeValue());
					} else if (nodeName.equals("link")) {
						if (newFilterValue == null)
							newFilterValue = new RelatedFilterValueImpl();
						NodeList linkDetails = filterValueDetails.item(i).getChildNodes();

						RelatedLink relatedLink = new RelatedLinkImpl();
						for (int j = 0; j < linkDetails.getLength(); j++) {
							if (!linkDetails.item(j).toString().trim().isEmpty()) {
								if (linkDetails.item(j).getNodeName().equals("name")) {
									relatedLink.setLinkText(linkDetails.item(j).getFirstChild().getNodeValue());
								} else if (linkDetails.item(j).getNodeName().equals("type")) {
									relatedLink.setLinkType(linkDetails.item(j).getFirstChild().getNodeValue());
								}
							}
						}
						((RelatedFilterValue) newFilterValue)
								.addLink(relatedLink);
					} else if (nodeName.equals("simple")) {
						String nodeValue = filterValueDetails.item(i).getFirstChild().getNodeValue();
						newFilterValue = new SimpleFilterValueImpl();
						((SimpleFilterValueImpl) newFilterValue).setValue(nodeValue);
					}
				}
			}
		}
		
		if (newFilterValue == null) {
			newFilterValue = new EmptyFilterValueImpl();
		}

		return newFilterValue;
	}
	
	/**
	 * Retrieve the data for condition filter
	 * 
	 * @param conditionElement
	 * @return
	 */
	public static MultiFilter getConditionFilter(Node conditionElement, boolean setMultiFilterType) {
		Node baseFilter = null;

		if (conditionElement != null) {
			NodeList filterElements = conditionElement.getChildNodes();

			for (int i=0; i<filterElements.getLength(); i++) {
				if (!filterElements.item(i).toString().trim().isEmpty()) {
					baseFilter = filterElements.item(i);
					break;				
				}
			}
		}
		
		return getMultiFilter(baseFilter, setMultiFilterType);
	}
	
	/**
	 * Retrieve data for Multi Filter
	 * 
	 * @param filter
	 * @return
	 */
	private static MultiFilter getMultiFilter(Node filter, boolean setMultiFilterType) {
		MultiFilter multiFilter;
		if (setMultiFilterType) {
			multiFilter = new MultiFilterImpl(WebStudio.get().getUserPreference().getRtiDefaultFilterType());
		}
		else {
			multiFilter = new MultiFilterImpl();	
		}
		

		if (filter != null) {
			NodeList conditionFilters = filter.getChildNodes();
			
			for (int i=0; i< conditionFilters.getLength(); i++) {
				if (!conditionFilters.item(i).toString().trim().isEmpty()) {
					String nodeName = conditionFilters.item(i).getNodeName();
					
					if (nodeName.equals("filterId")) {
						multiFilter.setFilterId(conditionFilters.item(i).getFirstChild().getNodeValue());
					} else if (nodeName.equals("matchType")) {
						multiFilter.setMatchType(conditionFilters.item(i).getFirstChild().getNodeValue());	
					} else {
						BuilderSubClause builderSubClause = multiFilter.getSubClause();
						Filter newFilter = null;
						if (isMultiFilter(conditionFilters.item(i).getChildNodes())) {
							newFilter = getMultiFilter(conditionFilters.item(i), false);
						} else {
							newFilter = getSingleFilter(conditionFilters.item(i));
						}
						builderSubClause.addFilter(newFilter);
					}
				}
			}
		}
		return multiFilter;
	}
	
	/**
	 * Check filter type
	 * 
	 * @param childNodes
	 * @return
	 */
	private static boolean isMultiFilter(NodeList childNodes) {
		boolean isMulti = false;
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (childNodes.item(i).getNodeName().equals("matchType")) {
				isMulti = true;
				break;
			}
		}

		return isMulti;
	}

	/**
	 * Retrieve View Info
	 * 
	 * @param docElement
	 * @return
	 */
	public static ViewInfo getViewInfo(Element docElement) {
		ViewInfo viewInfo = new ViewInfo();
		if (docElement.getElementsByTagName("htmlText").getLength() > 0) {
			Node htmlNode = docElement.getElementsByTagName("htmlText")
					.item(0).getFirstChild();
			if (htmlNode != null) {
				viewInfo.setHtml(htmlNode.getNodeValue());
			}
		}
		String html = viewInfo.getHtml();
		HashMap<String, String> styles = new HashMap<String, String>();
		if (html != null) {
			 styles = fetchCSS(viewInfo.getHtml());
			 String script = fetchScript(html);
			 if (script != null) {
				 viewInfo.setScript(script);	 
			 }
		}
		List<BindingInfo> bindingInfoList = new ArrayList<BindingInfo>();
		NodeList bindingList = docElement.getElementsByTagName("bindingInfo");
		for (int i = 0; i < bindingList.getLength(); i++) {
			NodeList bindingElements = bindingList.item(i).getChildNodes();
			String bindingId = null;
			BindingInfo bindingInfo = new BindingInfo();
			for (int j = 0; j < bindingElements.getLength(); j++) {
				if (!bindingElements.item(j).toString().trim().isEmpty()) {
					String nodeValue = null;
					String nodeName = bindingElements.item(j).getNodeName();
					if (!nodeName.equals("domainInfo")
							&& bindingElements.item(j).getFirstChild() != null)
						nodeValue = bindingElements.item(j).getFirstChild().getNodeValue();

					if (nodeName.equals("bindingId")) {
						bindingInfo.setId(nodeValue);
						bindingId = nodeValue;
					}
					if (nodeName.equals("type"))
						bindingInfo.setType(nodeValue);
					if (nodeName.equals("value"))
						bindingInfo.setValue(nodeValue);

					if (nodeName.equals("domainInfo")
							&& bindingElements.item(j).getChildNodes().getLength() > 0) {
						NodeList domainNodeList = bindingElements.item(j).getChildNodes();
						DomainInfo domainInfo = parseDomainInfo(domainNodeList);
						bindingInfo.setDomainInfo(domainInfo);
					}
				}
			}
			if (styles != null && styles.containsKey(bindingId)) {
				bindingInfo.setStyle(styles.get(bindingId));
			}
			bindingInfoList.add(bindingInfo);
		}

		viewInfo.setBindings(bindingInfoList);

		return viewInfo;
	}
	
	/**
	 * Method to validate Rule Template Instance
	 * 
	 * @param selectedRTI
	 * @param request
	 */
	public static void validateRuleTemplateInstance(NavigatorResource selectedRTI,HttpRequest request) {
		String uri = selectedRTI.getId().substring(0, selectedRTI.getId().indexOf(".")).replace("$", "/");
		String projectName = uri.substring(0, uri.indexOf("/"));
		String artifactPath = uri.substring(uri.indexOf("/"), uri.length());
		String type = selectedRTI.getId().substring(selectedRTI.getId().lastIndexOf(".")+1,selectedRTI.getId().length());
		
		if (request == null) {
			request = new HttpRequest();
		}
		
		request.clearParameters();
		request.setMethod(HttpMethod.GET);
		request.setPostData(null);
		
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_TYPE, ARTIFACT_TYPES.valueOf(type.toUpperCase())));
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, type));

		request.submit(ServerEndpoints.RMS_VALIDATE.getURL());
	}
	
	/**
	 * Method to post problems in Problems Pane
	 * 
	 * @param node
	 */
	public static void postProblems(Element node) {
		if (node.getElementsByTagName("projectName").getLength() > 0) {
			String projectName = node.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
			String artifactPath = node.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();
			
			List<ProblemRecord> errorRecordList = new ArrayList<ProblemRecord>();
			List<ProblemRecord> warningRecordList = new ArrayList<ProblemRecord>();
			
			getProblemsList(node, projectName, artifactPath, warningRecordList, errorRecordList);
			
			updateProblemRecords(warningRecordList, errorRecordList, true);
		}
	}
	
	/**
	 * Method to parse and display RTI validation errors.
	 * @param node
	 * @param projectName
	 * @param artifactPath
	 * @param errorrecords
	 * @param warnrecords
	 */
	public static void  getProblemsList(Element node, String projectName,
			String artifactPath, List<ProblemRecord> warningRecordList, List<ProblemRecord> errorRecordList) {
		
		String uri = projectName +  artifactPath;
		
		NodeList problemsNode = node.getElementsByTagName("problem");
		List<String> types = new ArrayList<String>();
		types.add(RULE_ERROR_TYPES.SEMANTIC_TYPE.getLiteral());
		types.add(RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral());
		removeMarker(uri, types, false);
		
		if (problemsNode.getLength() == 0) {
			updateProblemRecords(null, null, true);
		} 
	
		loadProblems(problemsNode, projectName, artifactPath, uri, warningRecordList, errorRecordList);
	}

	/**
	 * Method to display problems of an artifact in problems pane.
	 * @param problemsNode
	 * @param projectName
	 * @param artifactPath
	 * @param uri
	 */
	private static void loadProblems(NodeList problemsNode,
			String projectName, String artifactPath, String uri, List<ProblemRecord> warningRecordList, List<ProblemRecord> errorRecordList) {
		
		for (int cnt=0; cnt < problemsNode.getLength(); cnt++) {
			Node problem = problemsNode.item(cnt);
			NodeList problemChildNodes = problem.getChildNodes();
			
			String message = null, location = null;
			String problemType = null, errorCode = null;
			boolean isWarning = false;
			
			for (int nodeCount = 0; nodeCount < problemChildNodes.getLength(); nodeCount++) {
				Node problemChildNode = problemChildNodes.item(nodeCount);
				
				if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null) {			
					if (problemChildNode.getNodeName().equals("errorMessage")) {	
						message = problemChildNode.getFirstChild().getNodeValue(); 
						if (message.contains("Format")) { 
							String messageArray[] = message.split("Format");
							for (int i=0; i<messageArray.length; i++) {

								if (i == 0) {
									message = messageArray[i] + rtiMsgBundle.rtiValidation_missingActions_valueMismatch_Format();
								} else if (i < messageArray.length-1 && i > 0) {
									message += messageArray[i] + rtiMsgBundle.rtiValidation_missingActions_valueMismatch_Format();	
								} else{
									message += messageArray[i];
								}
							}
						} else {
							if (message.contains("Duplicate Values")) {
								message = message.replaceAll("Duplicate Values",  domainMsgBundle.msg_duplicate_domain_entries_format());
							}
						}
					} else if (problemChildNode.getNodeName().equals("location")) {
						location = problemChildNode.getFirstChild().getNodeValue();					 
					} else if (problemChildNode.getNodeName().equals("problemType")) {
						problemType = problemChildNode.getFirstChild().getNodeValue();	
					} else if (problemChildNode.getNodeName().equals("errorCode")) {
						errorCode = problemChildNode.getFirstChild().getNodeValue();	
					} else if (problemChildNode.getNodeName().equals("isWarning")) {
						isWarning = Boolean.valueOf(problemChildNode.getFirstChild().getNodeValue());
					}
				}
			}
			
			location = location != null ? location : "";
			if (errorCode.startsWith("1")) {
				location = "<b>" + rtiMsgBundle.rtiEditor_when() + "</b> "+location;
			} else if (errorCode.startsWith("2")) {
				location = "<b>" + rtiMsgBundle.rtiEditor_then() + "</b> "+location;
			}
			message = enhanceErrorMessage(message, errorCode);
			problemType = RULE_ERROR_TYPES.getByName(problemType).getLiteral();
			
			ProblemMarker problemMarker = new ProblemMarker(uri,
					projectName,
					artifactPath, 
					ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase(),
					problemType,
					location,
					message);
			
			int problemEvent = (isWarning) ? ProblemEvent.WARNING : ProblemEvent.ERROR;
			addMarkers(problemMarker, problemEvent, warningRecordList, errorRecordList);
		}
	}
	
	private static String enhanceErrorMessage(String message, String errorCode) {
		String staticText = "";
		if (errorCode.equals("211")) {
			staticText = message.substring(message.indexOf("(")-1, message.indexOf(")")+2);
			if (staticText.indexOf("modify") != -1) {
				staticText = staticText.replace("modify", rtiMsgBundle.rtiCommand_modify());
			} else if (staticText.indexOf("create") != -1) {
				staticText = staticText.replace("create", rtiMsgBundle.rtiCommand_create());
			} else if (staticText.indexOf("call") != -1) {
				staticText = staticText.replace("call", rtiMsgBundle.rtiCommand_call());
			}
			message = rtiMsgBundle.rtiValidation_missingActions() + staticText;
		} else if (errorCode.equals("112") || errorCode.equals("212")) { 
			staticText = message.substring(message.indexOf("'")-1, message.length());
			message = rtiMsgBundle.rtiValidation_missingActions_valueMismatch() + staticText;
		} else if (errorCode.equals("113") || errorCode.equals("213") || errorCode.equals("313")) {
			staticText = message.substring(message.indexOf("'")-1, message.length());
			message = rtiMsgBundle.rtiValidation_missingActions_typeMismatch() + staticText;			
		} else if (errorCode.equals("114") || errorCode.equals("214")) { 
			staticText = message.substring(message.indexOf("'")-1, message.length());
			message = rtiMsgBundle.rtiValidation_missingActions_operatorMismatch() + staticText;	
		} else if (errorCode.equals("311")) {
			staticText = message.substring(message.indexOf("(")-1, message.indexOf(")")+2);
			message = rtiMsgBundle.rtiValidation_missingBindings() + staticText;
		} else if (errorCode.equals("115")) {
			message = rtiMsgBundle.rtiValidation_missingConditions();
		}
		
		return message;
	}
	

	/**
	 * Get the type for the last link in condition
	 * 
	 * @param links
	 * @return
	 */
	public static String getLinkType(List<RelatedLink> links) {
		RelatedLink lastLink = links.get(links.size() - 1);
		
		String linkType = lastLink.getLinkType();
		if (linkType != null && linkType.indexOf(".") != -1) {
			linkType = linkType.substring(0, linkType.indexOf("."));
		}
		return linkType;
	}
	
	public static HashMap<String, String> getDisplayProperties(Element docElement) {
		HashMap<String, String> map = new HashMap<String, String>();
		NodeList propsList = docElement.getElementsByTagName("displayProperties");
		for (int i = 0; i < propsList.getLength(); i++) {
			NodeList propElements = propsList.item(i).getChildNodes();
			for (int j = 0; j < propElements.getLength(); j++) {
				Node item = propElements.item(j);
				if (!item.toString().trim().isEmpty()) {
					if (item.getNodeName().equals("displayProperty")) {
						processDisplayProperty(map, item);
					}
				}
			}
		}
		return map;
	}

	private static void processDisplayProperty(HashMap<String,String> map, Node displayItem) {
		NodeList propElements = displayItem.getChildNodes();
		String nodeKey = "";
		String nodeValue = null;
		for (int k = 0; k < propElements.getLength(); k++) {
			Node item = propElements.item(k);
			if (!item.toString().trim().isEmpty()) {
				if (item.getNodeName().equals("propKey")) {
					nodeKey = item.getFirstChild().getNodeValue();
				} else if (item.getNodeName().equals("propValue")) {
					nodeValue = URL.decode(item.getFirstChild().getNodeValue());
				}
			}
		}
		map.put(nodeKey, nodeValue);
	}
	
	/**
	 * Remove the filter <code>filterToRemove</code> from the specified Multifilter (looping recursively if necessary).
	 * @param multiFilter
	 * @param filterToRemove
	 * @return
	 */
	public static boolean removeFilter(MultiFilter multiFilter, Filter filterToRemove) {
		if (multiFilter.getSubClause().getFilters().contains(filterToRemove)) {
			multiFilter.getSubClause().removeFilter(filterToRemove);
			return true;
		}
		for (Filter filter : multiFilter.getSubClause().getFilters()) {
			if (filter instanceof MultiFilter) {
				if(removeFilter((MultiFilter)filter, filterToRemove)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *Fetch the CSS style from string containing binding tag. 
	 *It will return the key value pair of binding-id and respective CSS styles.
	 *@param html
	 *@return styles   
	 */
	private static HashMap<String, String> fetchCSS(String html) {
		HashMap<String, String> styles = new HashMap<String, String>();
		String bindingStartSplit[] = html.split("<binding");
		for (int i = 1; i < bindingStartSplit.length; i++) {
			
			String bindingEndSplit[] = bindingStartSplit[i].split("></binding")[0].split(",");
			if (bindingEndSplit.length == 2) {
				styles.put(bindingEndSplit[0].split("=")[1].replaceAll("\"","").trim(), bindingEndSplit[1].split("=")[1].replaceAll("\"","").trim());
			}
			else {
				styles.put(bindingEndSplit[0].split("=")[1].replaceAll("\"","").trim(), null);
			}		
		}
		return styles;
	}
	
	/**
	 * Fetch java script from the html text.
	 * @param html
	 * @return scriptBody
	 */
	private static String fetchScript(String html) {
		int startingIndex = html.indexOf("<script>");
		int endingIndex = html.indexOf("</script>");
		if (startingIndex >= 0 && endingIndex > startingIndex + 8) {
			return  html.substring(startingIndex + 8, endingIndex);		
		}
		return null;
	}
	
	/**
	 * Fetch artifact name from the artifact path.
	 * @param artifactPath
	 * @return artifactName
	 */
	public static String getArtifactName(String artifactPath) {
		String tokens[] = artifactPath.split("/");
		String artifactName = tokens[tokens.length-1];
		int lastIndexOfExtension = artifactName.lastIndexOf(".");
		if (lastIndexOfExtension > 0) {
			artifactName = artifactName.substring(0, lastIndexOfExtension);	
		}
		return artifactName;
	}
	
	/**
	 * Removes the script element with the given id, if present.
	 * 
	 * @param id
	 */
	public static void checkExistingScriptAndRemove(String id) {
		com.google.gwt.dom.client.Element existingSciptElement = Document.get().getElementById(id);
		com.google.gwt.dom.client.Element element = Document.get().getElementsByTagName("head").getItem(0);
		HeadElement head = HeadElement.as(element);
		if (existingSciptElement != null && head.isOrHasChild(existingSciptElement)) {
			existingSciptElement.removeFromParent();
		}
	}
}
