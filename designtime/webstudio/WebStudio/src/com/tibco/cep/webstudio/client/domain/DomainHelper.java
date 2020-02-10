package com.tibco.cep.webstudio.client.domain;

import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.addMarkers;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.removeMarker;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.studio.util.logger.problems.ProblemEvent;
import com.tibco.cep.webstudio.client.domain.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.webstudio.client.domain.model.Domain;
import com.tibco.cep.webstudio.client.domain.model.DomainEntry;
import com.tibco.cep.webstudio.client.domain.model.RangeEntry;
import com.tibco.cep.webstudio.client.domain.model.SingleEntry;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.i18n.DomainMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemMarker;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.problems.RULE_ERROR_TYPES;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;

public class DomainHelper {
	private static DomainMessages domainMessages = (DomainMessages)I18nRegistry.getResourceBundle(I18nRegistry.DOMAIN_MESSAGES);

	public static Domain loadDomain(Element docElement) {
		Domain domain = new Domain();

		Node artifactContentNode = docElement.getElementsByTagName("artifactContent").item(0);
		NodeList nodeList = artifactContentNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node propNode = nodeList.item(i);
			if (!propNode.toString().trim().isEmpty()) {
				Node childNode = propNode.getFirstChild();
				if (childNode != null) {
					if (propNode.getNodeName().equals("name"))
						domain.setName(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("folder"))
						domain.setFolder(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("description"))
						domain.setDescription(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("ownerProjectName"))
						domain.setOwnerProjectName(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("superDomainPath"))
						domain.setSuperDomainPath(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("dataType"))
						domain.setDataType(childNode.getNodeValue());
					else if (propNode.getNodeName().equals("singleEntry")) {
						DomainEntry domainEntry = getDomainEntry(propNode, false);
						domain.addDomainEntry(domainEntry);
					}
					else if (propNode.getNodeName().equals("rangeEntry")) {
						DomainEntry domainEntry = getDomainEntry(propNode, true);
						domain.addDomainEntry(domainEntry);
					}
				}	
			}
		}		
		return domain;				
	}
	
	private static DomainEntry getDomainEntry(Node entryNode, boolean isRangeEntry) {		
		NodeList nodeList = entryNode.getChildNodes();
		boolean lowerInclusive = false, upperInclusive = false;
		String description = null, value = null, lower = null, upper = null; 
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node propNode = nodeList.item(i);
			if (!propNode.toString().trim().isEmpty()) {
				Node textNode = propNode.getFirstChild();
				if (textNode != null) {
					if (propNode.getNodeName().equals("description")) {
						description = textNode.getNodeValue();
					} else if (propNode.getNodeName().equals("value")) {
						value = textNode.getNodeValue();
					} else if (propNode.getNodeName().equals("lower")) {
						lower = textNode.getNodeValue();
					} else if (propNode.getNodeName().equals("upper")) {
						upper = textNode.getNodeValue();
					} else if (propNode.getNodeName().equals("lowerInclusive")) {
						lowerInclusive = Boolean.valueOf(textNode.getNodeValue());
					} else if (propNode.getNodeName().equals("upperInclusive")) {
						upperInclusive = Boolean.valueOf(textNode.getNodeValue());
					}
				}	
			}
		}

		DomainEntry domainEntry = null;		
		if (isRangeEntry) {
			RangeEntry rangeEntry = new RangeEntry();
			rangeEntry.setLower(lower);
			rangeEntry.setUpper(upper);
			rangeEntry.setLowerInclusive(lowerInclusive);
			rangeEntry.setUpperInclusive(upperInclusive);
			domainEntry = rangeEntry;
		} else {
			SingleEntry singleEntry = new SingleEntry();
			singleEntry.setValue(value);
			domainEntry = singleEntry;
		}
		domainEntry.setDescription(description);

		return domainEntry;
	}

	public static String getDataTypeIcon(DOMAIN_DATA_TYPES data_types) {
		switch (data_types) {
		case INTEGER:
			return "iconInteger16.gif";
		case DOUBLE:
			return "iconReal16.gif";
		case LONG:
			return "iconLong16.gif";
		case BOOLEAN:
			return "iconBoolean16.gif";
		case DATE_TIME:
			return "iconDate16.gif";
		case STRING:
			return "iconString16.gif";
		default:
			break;
		}
		return null;
	}

	public static void saveDomain(Domain domain, NavigatorResource selectedResource, HttpRequest request) {
		DomainDataItem domainDataItem = new DomainDataItem(domain);
		
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME, projectName);
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, domainDataItem);
		
		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_POST_ARTIFACT_SAVE, requestParameters);
		
		if (request == null) request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);
		
		request.submit(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL());
	}
	
	/**
	 * Method to validate Domain Model
	 * 
	 * @param selectedDomain
	 * @param request
	 */
	public static void validateDomain(NavigatorResource selectedDomain,HttpRequest request) {
		String uri = selectedDomain.getId().substring(0, selectedDomain.getId().indexOf(".")).replace("$", "/");
		String projectName = uri.substring(0, uri.indexOf("/"));
		String artifactPath = uri.substring(uri.indexOf("/"), uri.length());
		String type = selectedDomain.getId().substring(selectedDomain.getId().lastIndexOf(".")+1,selectedDomain.getId().length());
		
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
	 * Method to parse and display Domain validation errors.
	 * @param node
	 * @param projectName
	 * @param artifactPath
	 * @param errorrecords
	 * @param warnrecords
	 * @return
	 */
	public static List<ProblemRecord> getProblemsList(Element node, String projectName,
			String artifactPath) {
		
		String uri = projectName +  artifactPath;
		
		NodeList problemsNode = node.getElementsByTagName("problem");
		List<String> types = new ArrayList<String>();
		types.add(RULE_ERROR_TYPES.SEMANTIC_TYPE.getLiteral());
		types.add(RULE_ERROR_TYPES.SYNTAX_TYPE.getLiteral());
		removeMarker(uri, types, false);
		
		if (problemsNode.getLength() == 0) {
			updateProblemRecords(null, null, true);
		} 
	
		List<ProblemRecord> problemRecordList = loadProblems(problemsNode,projectName,artifactPath,uri);

		return problemRecordList;
	}

	/**
	 * Method to display problems of an artifact in problems pane.
	 * @param problemsNode
	 * @param projectName
	 * @param artifactPath
	 * @param uri
	 * @return
	 */
	private static List<ProblemRecord> loadProblems(NodeList problemsNode,
			String projectName, String artifactPath,String uri) {
		List<ProblemRecord> problemRecordList = new ArrayList<ProblemRecord>();
		
		for(int cnt=0; cnt < problemsNode.getLength(); cnt++) {
			Node problem = problemsNode.item(cnt);
			NodeList problemChildNodes = problem.getChildNodes();
			
			String message = null, location = null;
			String problemType = null, errorCode = null;
			
			for (int nodeCount = 0; nodeCount < problemChildNodes.getLength(); nodeCount++) {
				Node problemChildNode = problemChildNodes.item(nodeCount);
				
				if (problemChildNode.getNodeType() == Node.ELEMENT_NODE && problemChildNode.getFirstChild() != null) {					
					if (problemChildNode.getNodeName().equals("errorMessage")) {
						message = problemChildNode.getFirstChild().getNodeValue(); 
					} else if (problemChildNode.getNodeName().equals("location")) {
						location = problemChildNode.getFirstChild().getNodeValue();					 
					} else if (problemChildNode.getNodeName().equals("problemType")) {
						problemType = problemChildNode.getFirstChild().getNodeValue();	
					} else if (problemChildNode.getNodeName().equals("errorCode")) {
						errorCode = problemChildNode.getFirstChild().getNodeValue();	
					}
				}
			}
			
			location = location != null ? location : "";
			message = enhanceErrorMessage(message, errorCode);
			problemType = RULE_ERROR_TYPES.getByName(problemType).getLiteral();
			
			ProblemMarker problemMarker = new ProblemMarker(uri,
					projectName,
					artifactPath, 
					ARTIFACT_TYPES.DOMAIN.getValue().toLowerCase(),
					problemType,
					location,
					message);
			addMarkers(problemMarker, ProblemEvent.ERROR, null, problemRecordList);
		}
		return problemRecordList;
	}
	
	private static String enhanceErrorMessage(String message, String errorCode) {
		String staticText = null;
		if (errorCode.equals("411")) {
			staticText = message.substring(message.indexOf("(")-1, message.indexOf(")")+1);
			message = domainMessages.msg_duplicate_domain_entries() + staticText;
		}
		
		return message;
	}
	
}
