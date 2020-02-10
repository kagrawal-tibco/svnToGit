package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.studio.util.logger.problems.ConflictingActionsProblemEvent;
import com.tibco.cep.studio.util.logger.problems.DuplicateRuleProblemEvent;
import com.tibco.cep.studio.util.logger.problems.MissingEqualsCriterionProblemEvent;
import com.tibco.cep.studio.util.logger.problems.OverlappingRangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RANGE_TYPE_PROBLEM;
import com.tibco.cep.studio.util.logger.problems.RangeProblemEvent;
import com.tibco.cep.studio.util.logger.problems.RuleCombinationProblemEvent;
import com.tibco.cep.studio.util.logger.problems.UncoveredDomainEntryProblemEvent;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;

/**
 * Class to handle Table Analyzer response
 * @author vdhumal
 */
public class DecisionTableAnalyzerResponseHandler {
	protected static DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	
	public static Map<String, List<ProblemEvent>> loadProblems(Element docElement) {
		Node rootNode = docElement.getElementsByTagName("artifactDetails").item(0);
	
		String projectName = docElement.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
		String tableName = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();

		Map<String, List<ProblemEvent>> pageToProblemsMap = new HashMap<String, List<ProblemEvent>>();
		NodeList problemEventsNode = rootNode.getOwnerDocument().getElementsByTagName("problem");
		for (int i = 0; i < problemEventsNode.getLength(); i++) {
			Node problemNode = problemEventsNode.item(i);					
			ProblemEvent problem = loadProblem(projectName, tableName, problemNode);

			String pageNum = null;
			NodeList problemChildNodes = problemNode.getChildNodes();
			for (int j = 0; j < problemChildNodes.getLength(); j++) {
				Node problemChildNode = problemChildNodes.item(j);
				if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null 
																					&& problemChildNode.getNodeName().equals("pageNum")) {					
					pageNum = problemChildNode.getFirstChild().getNodeValue();
					break;
				}
			}
			List<ProblemEvent> problemEvents = pageToProblemsMap.get(pageNum);
			if (problemEvents == null) {
				problemEvents = new ArrayList<ProblemEvent>();
				pageToProblemsMap.put(pageNum, problemEvents);
			}
			problemEvents.add(problem);
		}		
		return pageToProblemsMap;
	}
	
	private static ProblemEvent loadProblem(String projectName, String tableName, Node problemNode) {
		ProblemEvent problem = null;
		NodeList problemChildNodes = problemNode.getChildNodes();			
		String errorCode = null, message = null, details = null, problemType = null;
		String columnName = null, rangeType = null, rangeChar1 = null, rangeChar2 = null;
		int otherRuleId = -1, severity = -1, location = -1, criterion = 0;
		Object minValue = null, maxValue=null;
		for (int j = 0; j < problemChildNodes.getLength(); j++) {
			Node problemChildNode = problemChildNodes.item(j);
			if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null) {					
				if (problemChildNode.getNodeName().equals("errorCode")) {
					errorCode = problemChildNode.getFirstChild().getNodeValue();
				} else if (problemChildNode.getNodeName().equals("errorMessage")) {
					message = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("details")) {
					details = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("severity")) {
					try {
						severity = Integer.parseInt(problemChildNode.getFirstChild().getNodeValue());
					} catch (NumberFormatException nfe) { } //do nothing						
				} else if (problemChildNode.getNodeName().equals("location")) {
					try {
						location = Integer.parseInt(problemChildNode.getFirstChild().getNodeValue());
					} catch (NumberFormatException nfe) { } //do nothing						 
				} else if (problemChildNode.getNodeName().equals("otherRuleId")) {
					try {
						otherRuleId = Integer.parseInt(problemChildNode.getFirstChild().getNodeValue());
					} catch (NumberFormatException nfe) { }	//do nothing
				} else if (problemChildNode.getNodeName().equals("problemType")) {
					problemType = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("criterion")) {
					try {
						criterion = Integer.parseInt(problemChildNode.getFirstChild().getNodeValue());
					} catch (NumberFormatException nfe) { } //do nothing	
				} else if (problemChildNode.getNodeName().equals("minValue")) {
						String nodeValue=problemChildNode.getFirstChild().getNodeValue();
						try {										
							minValue = Integer.parseInt(nodeValue);
						}catch (NumberFormatException e) {
							minValue =  DecisionTableUtils.getDate(nodeValue);
						}
					
				} else if (problemChildNode.getNodeName().equals("maxValue")) {
					String nodeValue=problemChildNode.getFirstChild().getNodeValue();

					try {										
						maxValue = Integer.parseInt(nodeValue);
					}catch (NumberFormatException e) {
						maxValue =  DecisionTableUtils.getDate(nodeValue);
					}
				} else if (problemChildNode.getNodeName().equals("rangeTypeProblem")) {
					rangeType = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("columnName")) {
					columnName = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("rangeChar1")) {
					rangeChar1 = problemChildNode.getFirstChild().getNodeValue(); 
				} else if (problemChildNode.getNodeName().equals("rangeChar2")) {
					rangeChar2 = problemChildNode.getFirstChild().getNodeValue(); 
				}								
			}
		}	

		if("ConflictingActionsProblemEvent".equalsIgnoreCase(problemType)) {
			String customMessage = dtMessages.wsdtanalyzeconflictingActions(location, otherRuleId);
			problem = new ConflictingActionsProblemEvent(errorCode, customMessage, tableName, location, severity);
		} else if ("MissingEqualsCriterionProblemEvent".equalsIgnoreCase(problemType)) {
			String customMessage = dtMessages.wsdtanalyzemissingEqualsCriterion(columnName, criterion);
			problem = new MissingEqualsCriterionProblemEvent(errorCode, customMessage, tableName, Integer.valueOf(criterion), null, severity);
		} else if ("RangeProblemEvent".equalsIgnoreCase(problemType)) {
			RANGE_TYPE_PROBLEM rangeTypeProblem = RANGE_TYPE_PROBLEM.valueOf(rangeType);
			String customMessage = null;
			if (RANGE_TYPE_PROBLEM.RANGE_BETWEEN_PROBLEM.equals(rangeTypeProblem)) {
				if(minValue instanceof Date){
					minValue=DecisionTableUtils.getBEDate((Date) minValue);
				}
				if(maxValue instanceof Date){
					maxValue=DecisionTableUtils.getBEDate((Date) maxValue);
				}
				customMessage = dtMessages.wsdtanalyzeuncoveredBoundedRange(columnName,rangeChar1,minValue,maxValue, rangeChar2); 
			} else if (RANGE_TYPE_PROBLEM.RANGE_GREATER_THAN_PROBLEM.equals(rangeTypeProblem)) {
				if(minValue instanceof Date){
					minValue=DecisionTableUtils.getBEDate((Date) minValue);
				}
				customMessage = dtMessages.wsdtanalyzeuncoveredUnboundedRange(columnName, rangeChar1, minValue, rangeChar2);
			} else {
				if(minValue instanceof Date){
					minValue=DecisionTableUtils.getBEDate((Date) minValue);
				}
				customMessage = dtMessages.wsdtanalyzeuncoveredUnboundedRange(columnName, rangeChar1, minValue, rangeChar2);
			}			 
			problem = new RangeProblemEvent(projectName, errorCode, customMessage, details, tableName, minValue, maxValue, columnName, rangeTypeProblem, location, severity);
		} else if ("OverlappingRangeProblemEvent".equalsIgnoreCase(problemType)) {
			String customMessage = message;
			
			//Range (100, 922337] in column 'orderQuantity' overlaps with the following 2 ranges: 	(200, 922337] [400, 922337]	
			RegExp regexp = RegExp.compile("^Range (.*?) in column '(.*?)' overlaps with the following (.*?) ranges: (.*?)$", "i");
			MatchResult matchResult = regexp.exec(message);
			if (matchResult != null && matchResult.getGroupCount() > 4) {
				String range = matchResult.getGroup(1);
				String colName = matchResult.getGroup(2);
				String count = matchResult.getGroup(3);
				String overlappingRanges = matchResult.getGroup(4);
				customMessage = dtMessages.wsdtanalyzeRangeOverlapsRanges(range, colName, count, overlappingRanges);
			}
			
			//String customMessage = dtMessages.wsdtanalyzeoverlappingRange(param1, param2);
			problem = new OverlappingRangeProblemEvent(errorCode, customMessage, tableName, location, severity);
		} else if ("UncoveredDomainEntryProblemEvent".equalsIgnoreCase(problemType)) {
			problem = new UncoveredDomainEntryProblemEvent(errorCode, message, tableName, location, severity);
		} else if ("DuplicateRuleProblemEvent".equalsIgnoreCase(problemType)) {			
			String customMessage = dtMessages.wsdtanalyzeequalRules(location, otherRuleId);
			problem = new DuplicateRuleProblemEvent(errorCode, customMessage, tableName, otherRuleId, location, severity);
		} else if ("RuleCombinationProblemEvent".equalsIgnoreCase(problemType)) {
			String customMessage = dtMessages.wsdtanalyzeequalRules(location, otherRuleId);
			problem = new RuleCombinationProblemEvent(errorCode, customMessage, tableName, location, severity);
		}
		
		return problem;		
	}
	
	public static DecisionTableAnalyzerComponent loadAnalyzerComponentResponse(Element docElement) {
		Node rootNode = docElement.getElementsByTagName("artifactDetails").item(0);
		if (rootNode == null) {
			return null;
		}
		String projectName = docElement.getElementsByTagName("projectName").item(0).getFirstChild().getNodeValue();
		String artifactPath = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();
		
		DecisionTableAnalyzerComponent tableAnalyzerComponent = new DecisionTableAnalyzerComponent(projectName, artifactPath);
		
		NodeList filtersNode = docElement.getElementsByTagName("filter");
		for (int i = 0; i < filtersNode.getLength(); i++) {
			Node filterNode = filtersNode.item(i);					
			loadAnalyzerFilter(filterNode, tableAnalyzerComponent);
		}
		loadTestDataArtifactNames(docElement, projectName, tableAnalyzerComponent);
		
		return tableAnalyzerComponent;
	}
	
	private static void loadTestDataArtifactNames(Element docElement, String projectName, DecisionTableAnalyzerComponent tableAnalyzerComponent) {
		NodeList testDatArtifactNameElements = docElement.getElementsByTagName("artifactRecord");
		for (int i=0; i < testDatArtifactNameElements.getLength(); i++) {
			NodeList artifactDetails = testDatArtifactNameElements.item(i).getChildNodes();
			
			String artifactPath = null, artifactExtn = null, baseArtifactPath = null;
			for (int j = 0; j < artifactDetails.getLength(); j++) {
				if (!artifactDetails.item(j).toString().trim().isEmpty()) {
					if (artifactDetails.item(j).getNodeName().equals("artifactPath")) {
						artifactPath = artifactDetails.item(j).getFirstChild().getNodeValue();
					} else if (artifactDetails.item(j).getNodeName().equals("fileExtension")) {
						artifactExtn = artifactDetails.item(j).getFirstChild().getNodeValue();					
					} else if (artifactDetails.item(j).getNodeName().equals("baseArtifactPath")) {
						baseArtifactPath = artifactDetails.item(j).getFirstChild().getNodeValue();
					}
				}				
			}
			if (artifactPath != null) {
				artifactPath = artifactPath + "." + artifactExtn;
				ArtifactDetail artifactDetail = new ArtifactDetail(artifactPath, baseArtifactPath);
				tableAnalyzerComponent.addTestDataArtifact(artifactDetail);
			}	
		}		
	}

	public static void loadAnalyzerFilter(Node filterNode, DecisionTableAnalyzerComponent tableAnalyzerComponent) {
		DecisionTableAnalyzerComponent.ColumnFilter filter = null;
		NodeList filterChildNodes = filterNode.getChildNodes();
		String columnName = null;
		boolean isRange = false;		
		for (int i = 0; i < filterChildNodes.getLength(); i++) {
			Node filterChildNode = filterChildNodes.item(i);					
			if (filterChildNode.getNodeType() == Node.ELEMENT_NODE && filterChildNode.getFirstChild() != null) {					
				if (filterChildNode.getNodeName().equals("columnName")) {
					columnName = filterChildNode.getFirstChild().getNodeValue();
					filter = tableAnalyzerComponent.new ColumnFilter(columnName);
					tableAnalyzerComponent.addFilter(filter);
				} else if (filterChildNode.getNodeName().equals("isRangeFilter")) {
					isRange = Boolean.parseBoolean(filterChildNode.getFirstChild().getNodeValue()); 
				}
			}
		}
		
		if (filter != null) {
			Object minValue = Long.MIN_VALUE, maxValue = Long.MAX_VALUE;
			for (int i = 0; i < filterChildNodes.getLength(); i++) {
				Node filterChildNode = filterChildNodes.item(i);					
				if (filterChildNode.getNodeType() == Node.ELEMENT_NODE && filterChildNode.getFirstChild() != null) {									
					String nodeValue = filterChildNode.getFirstChild().getNodeValue();
					if (isRange) {
						if (filterChildNode.getNodeName().equals("minValue")) {
							try {
								if(nodeValue.contains(".")){
									minValue = Double.parseDouble(nodeValue);
								}else{
									minValue = Integer.parseInt(nodeValue);		
								}
							}catch (NumberFormatException e) {
								minValue =  DecisionTableUtils.getDate(nodeValue);								
							}
						} else if (filterChildNode.getNodeName().equals("maxValue")) {
							try {
								if(nodeValue.contains(".")){
									maxValue = Double.parseDouble(nodeValue);
								}else{
									maxValue = Integer.parseInt(nodeValue);	
								}
							}catch (NumberFormatException e) {
								maxValue =  DecisionTableUtils.getDate(nodeValue);								
							}
						}
						filter.setRange(minValue, maxValue);
					} else {
						if (filterChildNode.getNodeName().equals("value")) {
							filter.addItem(nodeValue);
						}					
					}
				}
			}	
		}		
	}
	
	public static Map<Integer, List<String>> loadCoverageResponse(Element docElement) {
		Map<Integer, List<String>> pageToCoverageMap = new HashMap<Integer, List<String>>();
		
		Node tableCoverageNode = docElement.getElementsByTagName("artifactDetails").item(0);
		if (tableCoverageNode == null) {
			return pageToCoverageMap;
		}
		
		NodeList pageNodes = docElement.getElementsByTagName("record");
		for (int i = 0; i < pageNodes.getLength(); i++) {
			Node pageNode = pageNodes.item(i);
			NodeList pageChildNodes = pageNode.getChildNodes();
			Integer pageNum = null;
			for (int j = 0; j < pageChildNodes.getLength(); j++) {
				Node pageChildNode = pageChildNodes.item(j);
				if (pageChildNode.getNodeType() == Node.ELEMENT_NODE && pageChildNode.getFirstChild() != null) {									
					if (pageChildNode.getNodeName().equals("recordId")) {
						try {
							pageNum = new Integer(pageChildNode.getFirstChild().getNodeValue()); 
							pageToCoverageMap.put(pageNum, new ArrayList<String>());
						} catch (NumberFormatException nfe) {
							//do nothing
						}
						break;
					}
				}	
			}
			if (pageNum != null) {
				for (int j = 0; j < pageChildNodes.getLength(); j++) {
					Node pageChildNode = pageChildNodes.item(j);
					if (pageChildNode.getNodeType() == Node.ELEMENT_NODE && pageChildNode.getFirstChild() != null) {									
						if (pageChildNode.getNodeName().equals("coveredRuleId")) {
							try {
								String ruleId = pageChildNode.getFirstChild().getNodeValue(); 
								List<String> ruleIdsList = pageToCoverageMap.get(pageNum);
								ruleIdsList.add(ruleId);
							} catch (NumberFormatException nfe) {
								//do nothing
							}
						}
					}	
				}
			}	
		}
		
		return pageToCoverageMap;
	}		
}
